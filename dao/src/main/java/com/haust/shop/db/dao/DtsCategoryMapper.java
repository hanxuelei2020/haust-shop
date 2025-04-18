package com.haust.shop.db.dao;

import com.haust.shop.db.domain.DtsCategory;
import com.haust.shop.db.domain.DtsCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DtsCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    long countByExample(DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int deleteByExample(DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int insert(DtsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int insertSelective(DtsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCategory selectOneByExample(DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCategory selectOneByExampleSelective(@Param("example") DtsCategoryExample example, @Param("selective") DtsCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsCategory> selectByExampleSelective(@Param("example") DtsCategoryExample example, @Param("selective") DtsCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    List<DtsCategory> selectByExample(DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCategory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    DtsCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCategory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsCategory record, @Param("example") DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsCategory record, @Param("example") DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_category
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}