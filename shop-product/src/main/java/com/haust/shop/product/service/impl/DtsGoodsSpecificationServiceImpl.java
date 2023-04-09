package com.haust.shop.product.service.impl;

import com.haust.service.domain.product.DtsGoodsSpecification;
import com.haust.service.domain.product.DtsGoodsSpecificationExample;
import com.haust.service.service.product.DtsGoodsSpecificationService;
import com.haust.shop.product.mapper.DtsGoodsSpecificationMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class DtsGoodsSpecificationServiceImpl implements DtsGoodsSpecificationService {
	@Autowired
	private DtsGoodsSpecificationMapper goodsSpecificationMapper;

	public List<DtsGoodsSpecification> queryByGid(Integer id) {
		DtsGoodsSpecificationExample example = new DtsGoodsSpecificationExample();
		example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
		return goodsSpecificationMapper.selectByExample(example);
	}

	public DtsGoodsSpecification findById(Integer id) {
		return goodsSpecificationMapper.selectByPrimaryKey(id);
	}

	public void deleteByGid(Integer gid) {
		DtsGoodsSpecificationExample example = new DtsGoodsSpecificationExample();
		example.or().andGoodsIdEqualTo(gid);
		goodsSpecificationMapper.logicalDeleteByExample(example);
	}

	public void add(DtsGoodsSpecification goodsSpecification) {
		goodsSpecification.setAddTime(LocalDateTime.now());
		goodsSpecification.setUpdateTime(LocalDateTime.now());
		goodsSpecificationMapper.insertSelective(goodsSpecification);
	}

	/**
	 * [ { name: '', valueList: [ {}, {}] }, { name: '', valueList: [ {}, {}] } ]
	 *
	 * @param id
	 * @return
	 */
	public Object getSpecificationVoList(Integer id) {
		List<DtsGoodsSpecification> goodsSpecificationList = queryByGid(id);

		Map<String, VO> map = new HashMap<>();
		List<VO> specificationVoList = new ArrayList<>();

		for (DtsGoodsSpecification goodsSpecification : goodsSpecificationList) {
			String specification = goodsSpecification.getSpecification();
			VO goodsSpecificationVo = map.get(specification);
			if (goodsSpecificationVo == null) {
				goodsSpecificationVo = new VO();
				goodsSpecificationVo.setName(specification);
				List<DtsGoodsSpecification> valueList = new ArrayList<>();
				valueList.add(goodsSpecification);
				goodsSpecificationVo.setValueList(valueList);
				map.put(specification, goodsSpecificationVo);
				specificationVoList.add(goodsSpecificationVo);
			} else {
				List<DtsGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
				valueList.add(goodsSpecification);
			}
		}

		return specificationVoList;
	}

	private class VO {
		private String name;
		private List<DtsGoodsSpecification> valueList;

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<DtsGoodsSpecification> getValueList() {
			return valueList;
		}

		public void setValueList(List<DtsGoodsSpecification> valueList) {
			this.valueList = valueList;
		}
	}

}
