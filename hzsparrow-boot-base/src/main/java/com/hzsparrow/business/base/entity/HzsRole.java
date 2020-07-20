package com.hzsparrow.business.base.entity;

import java.io.Serializable;

public class HzsRole implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role.hsr_id
     *
     * @mbg.generated
     */
    private String hsrId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role.hsr_name
     *
     * @mbg.generated
     */
    private String hsrName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role.hsr_type
     *
     * @mbg.generated
     */
    private Integer hsrType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role.icon
     *
     * @mbg.generated
     */
    private String icon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role.sort_num
     *
     * @mbg.generated
     */
    private Integer sortNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hzs_role
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role.hsr_id
     *
     * @return the value of hzs_role.hsr_id
     *
     * @mbg.generated
     */
    public String getHsrId() {
        return hsrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role.hsr_id
     *
     * @param hsrId the value for hzs_role.hsr_id
     *
     * @mbg.generated
     */
    public void setHsrId(String hsrId) {
        this.hsrId = hsrId == null ? null : hsrId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role.hsr_name
     *
     * @return the value of hzs_role.hsr_name
     *
     * @mbg.generated
     */
    public String getHsrName() {
        return hsrName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role.hsr_name
     *
     * @param hsrName the value for hzs_role.hsr_name
     *
     * @mbg.generated
     */
    public void setHsrName(String hsrName) {
        this.hsrName = hsrName == null ? null : hsrName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role.hsr_type
     *
     * @return the value of hzs_role.hsr_type
     *
     * @mbg.generated
     */
    public Integer getHsrType() {
        return hsrType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role.hsr_type
     *
     * @param hsrType the value for hzs_role.hsr_type
     *
     * @mbg.generated
     */
    public void setHsrType(Integer hsrType) {
        this.hsrType = hsrType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role.icon
     *
     * @return the value of hzs_role.icon
     *
     * @mbg.generated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role.icon
     *
     * @param icon the value for hzs_role.icon
     *
     * @mbg.generated
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role.sort_num
     *
     * @return the value of hzs_role.sort_num
     *
     * @mbg.generated
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role.sort_num
     *
     * @param sortNum the value for hzs_role.sort_num
     *
     * @mbg.generated
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_role
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hsrId=").append(hsrId);
        sb.append(", hsrName=").append(hsrName);
        sb.append(", hsrType=").append(hsrType);
        sb.append(", icon=").append(icon);
        sb.append(", sortNum=").append(sortNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}