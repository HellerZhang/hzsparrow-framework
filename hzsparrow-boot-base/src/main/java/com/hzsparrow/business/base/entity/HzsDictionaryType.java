package com.hzsparrow.business.base.entity;

import java.io.Serializable;

public class HzsDictionaryType implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary_type.bdt_id
     *
     * @mbg.generated
     */
    private String hsdtId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary_type.bdt_name
     *
     * @mbg.generated
     */
    private String hsdtName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary_type.bdt_type
     *
     * @mbg.generated
     */
    private Integer hsdtType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base_dictionary_type.sort_num
     *
     * @mbg.generated
     */
    private Integer sortNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table base_dictionary_type
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary_type.bdt_id
     *
     * @return the value of base_dictionary_type.bdt_id
     *
     * @mbg.generated
     */
    public String getHsdtId() {
        return hsdtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary_type.bdt_id
     *
     * @param hsdtId the value for base_dictionary_type.bdt_id
     *
     * @mbg.generated
     */
    public void setHsdtId(String hsdtId) {
        this.hsdtId = hsdtId == null ? null : hsdtId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary_type.bdt_name
     *
     * @return the value of base_dictionary_type.bdt_name
     *
     * @mbg.generated
     */
    public String getHsdtName() {
        return hsdtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary_type.bdt_name
     *
     * @param hsdtName the value for base_dictionary_type.bdt_name
     *
     * @mbg.generated
     */
    public void setHsdtName(String hsdtName) {
        this.hsdtName = hsdtName == null ? null : hsdtName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary_type.bdt_type
     *
     * @return the value of base_dictionary_type.bdt_type
     *
     * @mbg.generated
     */
    public Integer getHsdtType() {
        return hsdtType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary_type.bdt_type
     *
     * @param hsdtType the value for base_dictionary_type.bdt_type
     *
     * @mbg.generated
     */
    public void setHsdtType(Integer hsdtType) {
        this.hsdtType = hsdtType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base_dictionary_type.sort_num
     *
     * @return the value of base_dictionary_type.sort_num
     *
     * @mbg.generated
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base_dictionary_type.sort_num
     *
     * @param sortNum the value for base_dictionary_type.sort_num
     *
     * @mbg.generated
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base_dictionary_type
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hsdtId=").append(hsdtId);
        sb.append(", hsdtName=").append(hsdtName);
        sb.append(", hsdtType=").append(hsdtType);
        sb.append(", sortNum=").append(sortNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}