package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 【请填写功能名称】视图对象 user
 *
 * @author ruoyi
 * @date 2023-04-03
 */
@Data
@ExcelIgnoreUnannotated
public class UserVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 用户账号状态  1，未认定 2，认定中 ，3 未发卡，4 已发卡
     */
    @ExcelProperty(value = "用户账号状态  1，未认定 2，认定中 ，3 未发卡，4 已发卡")
    private Long status;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String loginName;

    /**
     * 登陆密码
     */
    @ExcelProperty(value = "登陆密码")
    private String password;

    /**
     * 卡号
     */
    @ExcelProperty(value = "卡号")
    private String cardNumber;

    /**
     * 认定类型
     */
    @ExcelProperty(value = "认定类型")
    private String type;

    /**
     * 卡有效期
     */
    @ExcelProperty(value = "卡有效期")
    private String validityDate;

    /**
     * 微信token
     */
    @ExcelProperty(value = "微信token")
    private String wxToken;

    /**
     * 登陆次数
     */
    @ExcelProperty(value = "登陆次数")
    private Long enterNumber;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Date lastTime;

    /**
     * 方向
     */
    @ExcelProperty(value = "方向")
    private Long direction;

    /**
     * 用户限制  1，正常 2，禁用
     */
    @ExcelProperty(value = "用户限制  1，正常 2，禁用")
    private Long jurisdiction;

    /**
     * 求职意向
     */
    @ExcelProperty(value = "求职意向")
    private String jobWanted;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Date registerTime;

    /**
     * 申报的微信token
     */
    @ExcelProperty(value = "申报的微信token")
    private String wxToken1;

    /**
     * 身份证
     */
    @ExcelProperty(value = "身份证")
    private String cardId;

    /**
     * 用户类型
     */
    private String userType;

    private String companyId;

}
