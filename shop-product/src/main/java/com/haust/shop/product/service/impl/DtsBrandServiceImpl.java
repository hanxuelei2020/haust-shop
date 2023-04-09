package com.haust.shop.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.product.DtsBrand;
import com.haust.service.domain.product.DtsBrandExample;
import com.haust.service.domain.product.DtsCategory;
import com.haust.service.service.product.DtsBrandService;
import com.haust.shop.product.mapper.DtsBrandMapper;
import com.haust.shop.product.mapper.DtsCategoryMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsBrandServiceImpl implements DtsBrandService {
	@Autowired
	private DtsCategoryMapper categoryMapper;
	
	@Autowired
	private DtsBrandMapper brandMapper;
	private DtsBrand.Column[] columns = new DtsBrand.Column[] { DtsBrand.Column.id, DtsBrand.Column.name,
			DtsBrand.Column.desc, DtsBrand.Column.picUrl, DtsBrand.Column.floorPrice };

	public List<DtsBrand> queryVO(int offset, int limit) {
		DtsBrandExample example = new DtsBrandExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause("add_time desc");
		PageHelper.startPage(offset, limit);
		return brandMapper.selectByExampleSelective(example, columns);
	}

	public int queryTotalCount() {
		DtsBrandExample example = new DtsBrandExample();
		example.or().andDeletedEqualTo(false);
		return (int) brandMapper.countByExample(example);
	}

	public DtsBrand findById(Integer id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	public List<DtsBrand> querySelective(String id, String name, Integer page, Integer size, String sort,
			String order) {
		DtsBrandExample example = new DtsBrandExample();
		DtsBrandExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(id)) {
			criteria.andIdEqualTo(Integer.valueOf(id));
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return brandMapper.selectByExample(example);
	}

	public int updateById(DtsBrand brand) {
		brand.setUpdateTime(LocalDateTime.now());
		return brandMapper.updateByPrimaryKeySelective(brand);
	}

	public void deleteById(Integer id) {
		brandMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsBrand brand) {
		brand.setAddTime(LocalDateTime.now());
		brand.setUpdateTime(LocalDateTime.now());
		brandMapper.insertSelective(brand);
	}

	public List<DtsBrand> all() {
		DtsBrandExample example = new DtsBrandExample();
		example.or().andDeletedEqualTo(false);
		return brandMapper.selectByExample(example);
	}
	
	public List<DtsBrand> getAdminBrands(Integer adminId) {
		if (adminId == null) {
			return null;
		}
		DtsBrandExample example = new DtsBrandExample();
		example.or().andDeletedEqualTo(false).andAdminIdEqualTo(adminId);
		return brandMapper.selectByExample(example);
	}

	/**
	 * 根据分类id获取分类名
	 * @param categoryId
	 * @return
	 */
	public String getBrandCategory(Integer categoryId) {
		DtsCategory dtsCategory = categoryMapper.selectByPrimaryKey(categoryId);
		return dtsCategory == null ? "综合" : dtsCategory.getName();
	}
	
}
