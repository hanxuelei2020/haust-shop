package com.haust.shop.db.dao;

import com.haust.shop.db.domain.DtsBrand;
import com.haust.shop.db.domain.DtsBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DtsBrandMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    long countByExample(DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int deleteByExample(DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int insert(DtsBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int insertSelective(DtsBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsBrand selectOneByExample(DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsBrand selectOneByExampleSelective(@Param("example") DtsBrandExample example, @Param("selective") DtsBrand.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsBrand> selectByExampleSelective(@Param("example") DtsBrandExample example, @Param("selective") DtsBrand.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    List<DtsBrand> selectByExample(DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsBrand selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsBrand.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    DtsBrand selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsBrand selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsBrand record, @Param("example") DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsBrand record, @Param("example") DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsBrand record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsBrandExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_brand
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}