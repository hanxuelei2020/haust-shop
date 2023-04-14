// 本机开发API地址,填写网关地址
var WxApiRoot = 'http://192.168.88.128:9195';

module.exports = {
  IndexUrl: WxApiRoot + '/user/wx/home/index', //首页数据接口
  AuthLoginByWeixin: WxApiRoot + '/user/wx/auth/login_by_weixin', //微信登录
  AuthLoginByAccount: WxApiRoot + '/user/wx/auth/login', //账号登录
  AuthLogout: WxApiRoot + '/user/wx/auth/logout', //账号登出
  AuthRegister: WxApiRoot + '/user/wx/auth/register', //账号注册
  AuthReset: WxApiRoot + '/user/wx/auth/reset', //账号密码重置
  AuthRegisterCaptcha: WxApiRoot + '/user/wx/auth/regCaptcha', //验证码
  AuthBindPhone: WxApiRoot + '/user/wx/auth/bindPhone', //绑定微信手机号
  CreateShareImg: WxApiRoot + '/user/wx/agency/createShareImg', //创建分享海报
  CollectList: WxApiRoot + '/user/wx/collect/list', //收藏列表
  CollectAddOrDelete: WxApiRoot + '/user/wx/collect/addordelete', //添加或取消收藏
  CommentList: WxApiRoot + '/user/wx/comment/list', //评论列表
  CommentCount: WxApiRoot + '/user/wx/comment/count', //评论总数
  CommentPost: WxApiRoot + '/user/wx/comment/post', //发表评论
  AddressList: WxApiRoot + '/user/wx/address/list', //收货地址列表
  AddressDetail: WxApiRoot + '/user/wx/address/detail', //收货地址详情
  AddressSave: WxApiRoot + '/user/wx/address/save', //保存收货地址
  AddressDelete: WxApiRoot + '/user/wx/address/delete', //保存收货地址
  UserFormIdCreate: WxApiRoot + '/user/wx/formid/create', //用户FromId，用于发送模版消息
  UserIndex: WxApiRoot + '/user/wx/user/index', //个人页面用户相关信息
  BrokerageMain: WxApiRoot + '/user/wx/brokerage/main',//佣金收益主页面
  SettleOrderList: WxApiRoot + '/user/wx/brokerage/settleOrderList',//佣金收益主页面
  ApplyWithdrawal: WxApiRoot + '/user/wx/brokerage/applyWithdrawal',//佣金提现申请
  ExtractList: WxApiRoot + '/user/wx/brokerage/extractList',//佣金账号提现记录
  ApplyAgency: WxApiRoot + '/user/wx/user/applyAgency',//代理申请
  GetSharedUrl: WxApiRoot + '/user/wx/user/getSharedUrl', //获取推广二维码

  GoodsCount: WxApiRoot + '/product/wx/goods/count', //统计商品总数
  GoodsList: WxApiRoot + '/product/wx/goods/list', //获得商品列表
  GoodsCategory: WxApiRoot + '/product/wx/goods/category', //获得分类数据
  GoodsDetail: WxApiRoot + '/product/wx/goods/detail', //获得商品的详情
  GoodsRelated: WxApiRoot + '/product/wx/goods/related', //商品详情页的关联商品（大家都在看）
  CatalogList: WxApiRoot + '/product/wx/catalog/index', //分类目录全部分类数据接口
  CatalogCurrent: WxApiRoot + '/product/wx/catalog/current', //分类目录当前分类数据接口
  BrandList: WxApiRoot + '/product/wx/brand/list', //品牌列表
  BrandDetail: WxApiRoot + '/product/wx/brand/detail', //品牌详情
  TopicList: WxApiRoot + '/product/wx/topic/list', //专题列表
  TopicDetail: WxApiRoot + '/product/wx/topic/detail', //专题详情
  TopicRelated: WxApiRoot + '/product/wx/topic/related', //相关专题

  CartList: WxApiRoot + '/cart/wx/cart/index', //获取购物车的数据
  CartAdd: WxApiRoot + '/cart/wx/cart/add', // 添加商品到购物车
  CartFastAdd: WxApiRoot + '/cart/wx/cart/fastadd', // 立即购买商品
  CartUpdate: WxApiRoot + '/cart/wx/cart/update', // 更新购物车的商品
  CartDelete: WxApiRoot + '/cart/wx/cart/delete', // 删除购物车的商品
  CartChecked: WxApiRoot + '/cart/wx/cart/checked', // 选择或取消选择商品
  CartGoodsCount: WxApiRoot + '/cart/wx/cart/goodscount', // 获取购物车商品件数
  CartCheckout: WxApiRoot + '/cart/wx/cart/checkout', // 下单前信息确认

  SearchIndex: WxApiRoot + '/search/wx/search/index', //搜索关键字
  SearchResult: WxApiRoot + '/search/wx/search/result', //搜索结果
  SearchHelper: WxApiRoot + '/search/wx/search/helper', //搜索帮助
  SearchClearHistory: WxApiRoot + '/search/wx/search/clearhistory', //搜索历史清楚

  ExpressQuery: WxApiRoot + '/order/wx/order/expressTrace', //物流查询
  OrderSubmit: WxApiRoot + '/order/wx/order/submit', // 提交订单
  OrderPrepay: WxApiRoot + '/order/wx/order/prepay', // 订单的预支付会话
  OrderList: WxApiRoot + '/order/wx/order/list', //订单列表
  OrderDetail: WxApiRoot + '/order/wx/order/detail', //订单详情
  ExpressTrace: WxApiRoot + '/order/wx/order/expressTrace', //订单物流
  OrderCancel: WxApiRoot + '/order/wx/order/cancel', //取消订单
  OrderRefund: WxApiRoot + '/order/wx/order/refund', //退款取消订单
  OrderDelete: WxApiRoot + '/order/wx/order/delete', //删除订单
  OrderConfirm: WxApiRoot + '/order/wx/order/confirm', //确认收货
  OrderGoods: WxApiRoot + '/order/wx/order/goods', // 代评价商品信息
  OrderComment: WxApiRoot + '/order/wx/order/comment', // 评价订单商品信息

  FeedbackAdd: WxApiRoot + '/admin/wx/feedback/submit', //添加反馈
  FootprintList: WxApiRoot + '/admin/wx/footprint/list', //足迹列表
  FootprintDelete: WxApiRoot + '/admin/wx/footprint/delete', //删除足迹
  ArticleDetail: WxApiRoot + '/admin/wx/article/detail',//公告详情


  GroupOnList: WxApiRoot + '/groupon/wx/groupon/list', //团购列表
  GroupOn: WxApiRoot + '/groupon/wx/groupon/query', //团购API-查询
  GroupOnMy: WxApiRoot + '/groupon/wx/groupon/my', //团购API-我的团购
  GroupOnDetail: WxApiRoot + '/groupon/wx/groupon/detail', //团购API-详情
  GroupOnJoin: WxApiRoot + '/groupon/wx/groupon/join', //团购API-详情

  CouponList: WxApiRoot + '/coupon/wx/coupon/list', //优惠券列表
  CouponMyList: WxApiRoot + '/coupon/wx/coupon/mylist', //我的优惠券列表
  CouponSelectList: WxApiRoot + '/coupon/wx/coupon/selectlist', //当前订单可用优惠券列表
  CouponReceive: WxApiRoot + '/coupon/wx/coupon/receive', //优惠券领取
  CouponReceiveAll: WxApiRoot + '/coupon/wx/coupon/receiveAll', //优惠券领取
  CouponExchange: WxApiRoot + '/coupon/wx/coupon/exchange', //优惠券兑换
  GetUserCoupon: WxApiRoot + '/coupon/wx/coupon/getUserCoupon',//用户个人可领取优惠券查询

  RegionList: WxApiRoot + '/third/wx/region/list', //获取区域列表
  StorageUpload: WxApiRoot + '/third/wx/storage/upload' //图片上传,
};