package com.haust.shop.third.mapper;

import com.haust.service.domain.third.DtsStorage;
import com.haust.service.domain.third.DtsStorageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DtsStorageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    long countByExample(DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int deleteByExample(DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int insert(DtsStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int insertSelective(DtsStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsStorage selectOneByExample(DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsStorage selectOneByExampleSelective(@Param("example") DtsStorageExample example, @Param("selective") DtsStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsStorage> selectByExampleSelective(@Param("example") DtsStorageExample example, @Param("selective") DtsStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    List<DtsStorage> selectByExample(DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsStorage selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    DtsStorage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsStorage selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsStorage record, @Param("example") DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsStorage record, @Param("example") DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_storage
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}