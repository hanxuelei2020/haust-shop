package com.haust.shop.order.consumer;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.haust.common.config.SystemConfig;
import com.haust.common.consts.RocketMqConstant;
import com.haust.common.type.NotifyType;
import com.haust.common.util.DateTimeUtil;
import com.haust.service.domain.order.*;
import com.haust.service.service.order.DtsGrouponService;
import com.haust.service.service.order.DtsOrderGoodsService;
import com.haust.service.service.order.DtsOrderService;
import com.haust.service.service.product.DtsGrouponRulesService;
import com.haust.service.service.third.NotifyService;
import com.haust.service.service.third.QCodeService;
import com.haust.shop.order.model.OrderPayInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


// 只用来模拟交易成功
@Component
@RocketMQMessageListener(topic = RocketMqConstant.ORDER_NOTIFY_TOPIC,consumerGroup = RocketMqConstant.ORDER_NOTIFY_TOPIC)
public class OrderPayConsumer implements RocketMQListener<OrderPayInfo> {
    private static final Logger logger = LoggerFactory.getLogger(OrderPayConsumer.class);

    @Autowired
    private DtsOrderService orderService;
    @Autowired
    private DtsOrderGoodsService orderGoodsService;
    @Autowired
    private SystemConfig systemConfig;
    @DubboReference
    private DtsGrouponRulesService grouponRulesService;
    @DubboReference
    private DtsGrouponService grouponService;
    @DubboReference
    private QCodeService qCodeService;
    @DubboReference
    private NotifyService notifyService;
    @Override
    public void onMessage(OrderPayInfo orderPayInfo) {
        logger.info("【请求开始】微信付款成功或失败回调...");

        logger.info("处理腾讯支付平台的订单支付：{}", JSONObject.toJSONString(orderPayInfo));

        String orderSn = String.valueOf(orderPayInfo.getOrderSn());
        String payId = orderPayInfo.getPayId();

        // 分转化成元
        String totalFee = BaseWxPayResult.fenToYuan(orderPayInfo.getTotalFee());
        DtsOrder order = orderService.findBySn(orderSn);
        if (order == null) {
            logger.error("微信付款成功或失败回调失败：{}", "订单不存在 sn=" + orderSn);
        }

        // 检查这个订单是否已经处理过
        if (OrderUtil.isPayStatus(order) && order.getPayId() != null) {
            logger.warn("警告：微信付款成功或失败回调：{}", "订单已经处理成功!");
        }

        // 检查支付订单金额
        if (!totalFee.equals(order.getActualPrice().toString())) {
            logger.error("微信付款成功或失败回调失败：{}", order.getOrderSn() + " : 支付金额不符合 totalFee=" + totalFee);
        }

        // 支付成功，有团购信息，更新团购信息
        DtsGroupon groupon = grouponService.queryByOrderId(order.getId());
        DtsGrouponRules grouponRules = null;
        if (groupon != null) {
            grouponRules = grouponRulesService.queryById(groupon.getRulesId());

            // 仅当发起者才创建分享图片
            if (groupon.getGrouponId() == 0) {
                BigDecimal actualPrice = new BigDecimal(order.getActualPrice().toString());
                String url = qCodeService.createGrouponShareImage(grouponRules.getGoodsName(), grouponRules.getPicUrl(),
                        groupon,actualPrice.add(grouponRules.getDiscount()),actualPrice);
                groupon.setShareUrl(url);
            }
            groupon.setPayed(true);
            if (grouponService.updateById(groupon) == 0) {
                logger.error("微信付款成功或失败回调失败：{}", "更新团购数据已失败!");
            }
        }

        /**
         * modify by CHENBO 2019-08-09 对于多店铺模式，支付成功后，如果这个订单包含多个商铺的商品，则考虑拆分订单
         * 1.原订单删除，需要按店铺拆成多个单，每个订单的订单序列码用 orderSn_1,orderSn_2,orderSn_3... 2.调整原订单商品表
         * dts_order_goods 中的订单编号 3.调整团购的订单编号，注意，不管是否为多商铺模式，每个团购商品只会归属于一个订单或一个子订单
         * 4.调整用户优惠券对应的订单，用户优惠券对应的订单可能会有多个，因为多店铺模式，每个大订单可能只有一个优惠券，会按实际收款金额比例进行平摊
         */
        List<DtsOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
        List<BrandOrderGoods> brandOrderGoodsList = divideMultiBrandOrderGoods(orderGoodsList);

        // 邮件通知的订单id
        String orderIds = "";

        if (systemConfig.isMultiOrderModel() && brandOrderGoodsList.size() > 1) {// 如果是多店铺模式，且这个订单确实含有多个店铺商品
            logger.info("需要进行拆单：主订单编号：【" + order.getId() + "】 支付编号【" + payId + "】");
            List<DtsOrder> dtsOrders = new ArrayList<DtsOrder>();

            /**
             * 记录一个临时的总订单支付金额 包含团购减免，即减免团购后的商品总价，多店铺需将所有商品相加
             * 用于核对拆单后的总价与原来订单是否一致，可用于日志警告。理论上拆单逻辑正确，价格应该一致
             */
            BigDecimal checkActualPrice = new BigDecimal(0.00);

            for (int i = 0; i < brandOrderGoodsList.size(); i++) {
                BrandOrderGoods bog = brandOrderGoodsList.get(i);
                DtsOrder childOrder = new DtsOrder();
                List<DtsOrderGoods> bandOrderGoodsList = bog.getOrderGoodsList();
                BigDecimal bandGoodsTotalPrice = new BigDecimal(0.00);
                BigDecimal bandFreightPrice = new BigDecimal(0.00);
                for (DtsOrderGoods orderGoods : bandOrderGoodsList) {// 循环店铺各自的购物商品
                    // 只有当团购规格商品ID符合才进行团购优惠
                    if (grouponRules != null && grouponRules.getGoodsId().equals(orderGoods.getGoodsId())) {
                        BigDecimal grouponPrice = grouponRules.getDiscount();
                        childOrder.setGrouponPrice(grouponPrice);
                        bandGoodsTotalPrice = bandGoodsTotalPrice.add(orderGoods.getPrice().subtract(grouponPrice)
                                .multiply(new BigDecimal(orderGoods.getNumber())));
                    } else {
                        bandGoodsTotalPrice = bandGoodsTotalPrice
                                .add(orderGoods.getPrice().multiply(new BigDecimal(orderGoods.getNumber())));
                    }
                }
                // 每个店铺都单独计算运费，满xxx则免运费，否则x元；
                if (bandGoodsTotalPrice.compareTo(systemConfig.getFreightLimit()) < 0) {
                    bandFreightPrice = systemConfig.getFreight();
                }

                childOrder.setGoodsPrice(bandGoodsTotalPrice);
                childOrder.setFreightPrice(bandFreightPrice);

                /**
                 * 核算实际价格和设置子订单的其他属性 按店铺子订单（商品总价与配送费总和）占大订单的 （商品总价与配送费总和） 的比例 分摊其他共用费用
                 */
                childOrder = copyfixedOrderAttr(order, childOrder);
                childOrder.setPayId(payId);
                childOrder.setPayTime(LocalDateTime.now());
                childOrder.setOrderStatus(OrderUtil.STATUS_PAY);
                childOrder.setOrderSn(order.getOrderSn() + "_" + i);
                childOrder.setUpdateTime(LocalDateTime.now());

                BigDecimal calcOldOrderPrice = order.getGoodsPrice().add(order.getFreightPrice());
                BigDecimal calcChildOrderPrice = childOrder.getGoodsPrice().add(childOrder.getFreightPrice());

                BigDecimal rate = calcChildOrderPrice.divide(calcOldOrderPrice, 8, BigDecimal.ROUND_UP);
                BigDecimal couponPrice = order.getCouponPrice().multiply(rate).setScale(2, BigDecimal.ROUND_UP);
                BigDecimal integralPrice = order.getIntegralPrice().multiply(rate).setScale(2, BigDecimal.ROUND_UP);
                BigDecimal settlementMoney = order.getSettlementMoney().multiply(rate).setScale(2,
                        BigDecimal.ROUND_DOWN);

                BigDecimal orderPrice = bandGoodsTotalPrice.add(bandFreightPrice).subtract(couponPrice);
                BigDecimal actualPrice = orderPrice.subtract(integralPrice);

                childOrder.setCouponPrice(couponPrice);
                childOrder.setIntegralPrice(integralPrice);
                childOrder.setSettlementMoney(settlementMoney);
                childOrder.setOrderPrice(orderPrice);
                childOrder.setActualPrice(actualPrice);

                checkActualPrice = checkActualPrice.add(actualPrice);
                dtsOrders.add(childOrder);
                // 添加订单表项
                logger.info(childOrder.toString());
                if (childOrder.getGrouponPrice() == null) {
                    childOrder.setGrouponPrice(new BigDecimal(0.00));
                }
                orderService.add(childOrder);
                Integer childOrderId = childOrder.getId();
                orderIds = orderIds + "," + childOrderId.intValue();
                for (DtsOrderGoods orderGoods : bandOrderGoodsList) {// 循环更新店铺各自的购物商品
                    orderGoods.setOrderId(childOrderId);
                    orderGoodsService.updateById(orderGoods);
                }
            }

            // 逻辑删除原订单
            order.setPayId(payId);
            order.setPayTime(LocalDateTime.now());
            order.setOrderStatus(OrderUtil.STATUS_PAY);
            order.setDeleted(true);
            orderService.updateWithOptimisticLocker(order);

            BigDecimal payTotalFee = new BigDecimal(totalFee);
            // 验证误差范围
            BigDecimal errorPrice = checkActualPrice.subtract(payTotalFee).abs();
            if (payTotalFee.compareTo(checkActualPrice) < 0 || errorPrice
                    .divide(payTotalFee, 6, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("0.005")) > 0) {
                logger.warn("拆单过程存在误差已超出千分之五，请联系技术人员核查比对：订单编号：【" + orderSn + "】");
            }

        } else {
            orderIds = orderIds + "," + order.getId().intValue();
            order.setPayId(payId);
            order.setPayTime(LocalDateTime.now());
            order.setOrderStatus(OrderUtil.STATUS_PAY);
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                // 这里可能存在这样一个问题，用户支付和系统自动取消订单发生在同时
                // 如果数据库首先因为系统自动取消订单而更新了订单状态；
                // 此时用户支付完成回调这里也要更新数据库，而由于乐观锁机制这里的更新会失败
                // 因此，这里会重新读取数据库检查状态是否是订单自动取消，如果是则更新成支付状态。
                order = orderService.findBySn(orderSn);
                int updated = 0;
                if (OrderUtil.isAutoCancelStatus(order)) {
                    order.setPayId(payId);
                    order.setPayTime(LocalDateTime.now());
                    order.setOrderStatus(OrderUtil.STATUS_PAY);
                    updated = orderService.updateWithOptimisticLocker(order);
                }

                // 如果updated是0，那么数据库更新失败
                if (updated == 0) {
                    logger.info(WxPayNotifyResponse.fail("更新数据已失效"));
                }
            }
        }

        // TODO 发送邮件和短信通知，这里采用异步发送
        // 订单支付成功以后，会发送短信给用户，以及发送邮件给管理员
        // notifyService.notifyMail("新订单通知", order.toString());
        notifyService.notifySslMail("新订单通知", OrderUtil.orderHtmlText(order, orderIds, orderGoodsList));
        // 这里微信的短信平台对参数长度有限制，所以将订单号只截取后6位，暂时屏蔽，因为已有微信模板提醒
        // notifyService.notifySmsTemplateSync(order.getMobile(),
        // NotifyType.PAY_SUCCEED, new String[]{orderSn.substring(8, 14)});

        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[] { order.getOrderSn(), order.getOrderPrice().toString(),
                DateTimeUtil.getDateTimeDisplayString(order.getAddTime()), order.getConsignee(), order.getMobile(),
                order.getAddress() };

        // notifyService.notifyWxTemplate(result.getOpenid(), NotifyType.PAY_SUCCEED,
        // parms, "pages/index/index?orderId=" + order.getId());
        // open id = orvOg5fPiHI68hPxL9L4sK_jIUKg
        notifyService.notifyWxTemplate(orderPayInfo.getOpenId(), NotifyType.PAY_SUCCEED, parms, "/pages/ucenter/order/order");

        logger.info("【请求结束】微信付款成功或失败回调:响应结果:{}", "处理成功!");
    }

    /**
     * 将订单中的商品按入驻店铺分离归类
     *
     * @param orderGoodsList
     * @return
     */
    private List<BrandOrderGoods> divideMultiBrandOrderGoods(List<DtsOrderGoods> orderGoodsList) {
        List<BrandOrderGoods> brandOrderGoodsList = new ArrayList<BrandOrderGoods>();
        for (int i = 0; i < orderGoodsList.size(); i++) {
            DtsOrderGoods dog = orderGoodsList.get(i);
            Integer brandId = dog.getBrandId();
            boolean hasExsist = false;
            for (int k = 0; k < brandOrderGoodsList.size(); k++) {
                if (brandOrderGoodsList.get(k).getBrandId().intValue() == dog.getBrandId().intValue()) {
                    brandOrderGoodsList.get(k).getOrderGoodsList().add(dog);
                    hasExsist = true;
                    break;
                }
            }
            if (!hasExsist) { // 还尚未加入，则需要查询品牌入驻商铺
                BrandOrderGoods bog = new BrandOrderGoods();
                bog.setBrandId(brandId);
                List<DtsOrderGoods> childOrderGoodslist = new ArrayList<DtsOrderGoods>();
                childOrderGoodslist.add(dog);
                bog.setOrderGoodsList(childOrderGoodslist);
                brandOrderGoodsList.add(bog);
            }
        }

        return brandOrderGoodsList;
    }

    /**
     * 将不变的订单属性复制到子订单
     *
     * @param order
     * @param childOrder
     * @return
     */
    private DtsOrder copyfixedOrderAttr(DtsOrder order, DtsOrder childOrder) {
        if (childOrder != null && order != null) {
            childOrder.setAddress(order.getAddress());
            childOrder.setAddTime(order.getAddTime());
            childOrder.setConsignee(order.getConsignee());
            childOrder.setMessage(order.getMessage());
            childOrder.setMobile(order.getMobile());
            childOrder.setUserId(order.getUserId());
        }
        return childOrder;
    }

}
