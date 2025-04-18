package com.haust.shop.user.dao;

import com.haust.service.domain.user.DtsCollect;
import com.haust.service.domain.user.DtsCollectExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DtsCollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    long countByExample(DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int deleteByExample(DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int insert(DtsCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int insertSelective(DtsCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCollect selectOneByExample(DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCollect selectOneByExampleSelective(@Param("example") DtsCollectExample example, @Param("selective") DtsCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsCollect> selectByExampleSelective(@Param("example") DtsCollectExample example, @Param("selective") DtsCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    List<DtsCollect> selectByExample(DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCollect selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    DtsCollect selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsCollect selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsCollect record, @Param("example") DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsCollect record, @Param("example") DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_collect
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}