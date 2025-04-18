package com.haust.shop.admin.mapper;

import com.haust.service.domain.admin.DtsAdmin;
import com.haust.service.domain.admin.DtsAdminExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DtsAdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    long countByExample(DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int deleteByExample(DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int insert(DtsAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int insertSelective(DtsAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsAdmin selectOneByExample(DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsAdmin selectOneByExampleSelective(@Param("example") DtsAdminExample example, @Param("selective") DtsAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsAdmin> selectByExampleSelective(@Param("example") DtsAdminExample example, @Param("selective") DtsAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    List<DtsAdmin> selectByExample(DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    DtsAdmin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsAdmin record, @Param("example") DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsAdmin record, @Param("example") DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}