package com.haust.shop.db.dao;

import com.haust.shop.db.domain.DtsGoods;
import com.haust.shop.db.domain.DtsGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DtsGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    long countByExample(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int deleteByExample(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int insert(DtsGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int insertSelective(DtsGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsGoods selectOneByExample(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsGoods selectOneByExampleSelective(@Param("example") DtsGoodsExample example, @Param("selective") DtsGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsGoods selectOneByExampleWithBLOBs(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsGoods> selectByExampleSelective(@Param("example") DtsGoodsExample example, @Param("selective") DtsGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    List<DtsGoods> selectByExampleWithBLOBs(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    List<DtsGoods> selectByExample(DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    DtsGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsGoods record, @Param("example") DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") DtsGoods record, @Param("example") DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsGoods record, @Param("example") DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(DtsGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}