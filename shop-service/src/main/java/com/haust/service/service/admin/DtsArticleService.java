package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsArticle;

import java.util.List;

public interface DtsArticleService {

	/**
	 * 根据ID查询文章
	 *
	 * @param id 文章ID
	 * @return 文章对象
	 */
	DtsArticle findById(Integer id);

	/**
	 * 分页查询文章列表
	 *
	 * @param offset 分页起始位置
	 * @param limit 分页大小
	 * @param sort 排序字段
	 * @param order 排序方式（升序/降序）
	 * @return 文章列表
	 */
	List<DtsArticle> queryList(int offset, int limit, String sort, String order);

	/**
	 * 根据文章标题判断文章是否存在
	 *
	 * @param title 文章标题
	 * @return true：文章存在，false：文章不存在
	 */
	boolean checkExistByTitle(String title);

	/**
	 * 添加文章
	 *
	 * @param article 文章对象
	 */
	void add(DtsArticle article);

	/**
	 * 根据条件查询文章列表
	 *
	 * @param title 文章标题（模糊匹配）
	 * @param page 分页起始页码
	 * @param size 分页大小
	 * @param sort 排序字段
	 * @param order 排序方式（升序/降序）
	 * @return 符合条件的文章列表
	 */
	List<DtsArticle> querySelective(String title, Integer page, Integer size, String sort, String order);

	/**
	 * 根据ID更新文章信息
	 *
	 * @param article 文章对象
	 * @return 更新成功的记录数
	 */
	int updateById(DtsArticle article);

	/**
	 * 根据ID删除文章（逻辑删除）
	 *
	 * @param id 文章ID
	 */
	void deleteById(Integer id);
}
