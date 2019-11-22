package com.hzsparrow.boot.base.entity;

import java.io.Serializable;

public class HzsRoleMenu implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role_menu.hsrm_id
     *
     * @mbg.generated
     */
    private String hsrmId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role_menu.hsr_id
     *
     * @mbg.generated
     */
    private String hsrId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hzs_role_menu.hsm_id
     *
     * @mbg.generated
     */
    private String hsmId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hzs_role_menu
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role_menu.hsrm_id
     *
     * @return the value of hzs_role_menu.hsrm_id
     *
     * @mbg.generated
     */
    public String getHsrmId() {
        return hsrmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role_menu.hsrm_id
     *
     * @param hsrmId the value for hzs_role_menu.hsrm_id
     *
     * @mbg.generated
     */
    public void setHsrmId(String hsrmId) {
        this.hsrmId = hsrmId == null ? null : hsrmId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role_menu.hsr_id
     *
     * @return the value of hzs_role_menu.hsr_id
     *
     * @mbg.generated
     */
    public String getHsrId() {
        return hsrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role_menu.hsr_id
     *
     * @param hsrId the value for hzs_role_menu.hsr_id
     *
     * @mbg.generated
     */
    public void setHsrId(String hsrId) {
        this.hsrId = hsrId == null ? null : hsrId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hzs_role_menu.hsm_id
     *
     * @return the value of hzs_role_menu.hsm_id
     *
     * @mbg.generated
     */
    public String getHsmId() {
        return hsmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hzs_role_menu.hsm_id
     *
     * @param hsmId the value for hzs_role_menu.hsm_id
     *
     * @mbg.generated
     */
    public void setHsmId(String hsmId) {
        this.hsmId = hsmId == null ? null : hsmId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hzs_role_menu
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hsrmId=").append(hsrmId);
        sb.append(", hsrId=").append(hsrId);
        sb.append(", hsmId=").append(hsmId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}