package com.haust.shop.admin.mapper;

import com.haust.service.domain.admin.DtsFeedback;
import com.haust.service.domain.admin.DtsFeedbackExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DtsFeedbackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    long countByExample(DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int deleteByExample(DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int insert(DtsFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int insertSelective(DtsFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsFeedback selectOneByExample(DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsFeedback selectOneByExampleSelective(@Param("example") DtsFeedbackExample example, @Param("selective") DtsFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DtsFeedback> selectByExampleSelective(@Param("example") DtsFeedbackExample example, @Param("selective") DtsFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    List<DtsFeedback> selectByExample(DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsFeedback selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DtsFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    DtsFeedback selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DtsFeedback selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DtsFeedback record, @Param("example") DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DtsFeedback record, @Param("example") DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DtsFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DtsFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DtsFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dts_feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}