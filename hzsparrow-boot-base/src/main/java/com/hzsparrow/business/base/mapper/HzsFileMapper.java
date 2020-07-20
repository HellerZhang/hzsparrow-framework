package com.hzsparrow.business.base.mapper;

import com.hzsparrow.business.base.entity.HzsFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HzsFileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_file
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String hsfId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_file
     *
     * @mbg.generated
     */
    int insert(HzsFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_file
     *
     * @mbg.generated
     */
    HzsFile selectByPrimaryKey(String hsfId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_file
     *
     * @mbg.generated
     */
    List<HzsFile> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_file
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HzsFile record);

    int removeByRelationId(String relationId);

    List<HzsFile> findByRelationId(String relationId);

    int updateRelation(@Param("fileRegId") String fileRegId, @Param("fileRefFlag") String fileRefFlag, @Param("hsfId") String hsfId);
}