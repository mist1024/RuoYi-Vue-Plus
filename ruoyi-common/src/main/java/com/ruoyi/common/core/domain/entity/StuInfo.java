package com.ruoyi.common.core.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StuInfo implements Serializable {

    /**
     * 头像
     */
    private String headImg;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 民族
     */
    private String nation;
    /**
     * 出生日期
     */
    private String birthDay;
    /**
     * 院校
     */
    private String university;
    /**
     * 院系
     */
    private String department;
    /**
     * 专业
     */
    private String domain;
    /**
     * 层次，如本科
     */
    private String level;
    /**
     * 班级
     */
    private String sClass;
    /**
     * 学号
     */
    private String stuNum;
    /**
     * 形式
     */
    private String form;
    /**
     * 入学时间
     */
    private String entranceDate;
    /**
     * 学制
     */
    private String lenOfSchooling;
    /**
     * 类型
     */
    private String type;
    /**
     * 学籍状态
     */
    private String status;
    /**
     * 毕业时间
     */
    private String graduationDate;
    /**
     * 学历证书编号
     */
    private String certificateNum;
    /**
     * 校长
     */
    private String president;


}
