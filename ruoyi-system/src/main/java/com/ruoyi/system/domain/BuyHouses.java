package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 buy_houses
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Data
@TableName("buy_houses")
public class BuyHouses extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 户口簿内页/护照内页
     */
    private String insidepageUrl;
    /**
     * 身份证/护照
     */
    private String cardId;
    /**
     * 承诺书
     */
    private String commitmentUrl;
    /**
     * 单位地址
     */
    private String companyAddress;
    /**
     * 工作单位
     */
    private String companyName;
    /**
     *
     */
    private Date createTime;
    /**
     * 申明书
     */
    private String declarationUrl;
    /**
     * 企业所在地 1.新经济活力区 2.交子公园金融商务区 3.高新西区 4.生物城
     */
    private String district;
    /**
     * 学历
     */
    private String education;
    /**
     * 身份证正面
     */
    private String frontUrl;
    /**
     * 公园城市局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
    private String gyStatus;
    /**
     * 房屋记录
     */
    private String homeRecordUrl;
    /**
     * 户口簿主页
     */
    private String homepageUrl;
    /**
     * 劳动合同
     */
    private String laborContractUrl;
    /**
     * 企业营业执照
     */
    private String licenseUrl;
    /**
     * 婚姻状况 1.未婚 2.已婚 3.离异
     */
    private String maritalStatus;
    /**
     * 婚姻证明材料
     */
    private String maritalUrl;
    /**
     * 国籍
     */
    private String nationality;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 区域部门审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
    private String qyStatus;
    /**
     * 身份证背面
     */
    private String reverseUrl;
    /**
     * 性别
     */
    private String sex;
    /**
     * 社会事业局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
    private String shStatus;
    /**
     * 统一社会信用代码
     */
    private String socialCode;
    /**
     * 社保证明
     */
    private String socialSecurityUrl;
    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
    private String status;
    /**
     * 类型 A B C D
     */
    private String type;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 姓名
     */
    private String userName;
    /**
     *
     */
    private Date passTime;
    /**
     *
     */
    private String pictureInformationUrl;
    /**
     *
     */
    private String workAddress;

    private String processKey;

    private String processStatus;


    private Date updateTime;

    private String version;

    private String companyId;

    @TableField(exist = false)
    private List<BuyHousesMember> buyHousesMemberList=new ArrayList<>();

    private String apiKey;

    @TableField(exist = false)
    private String reply;

    private String step;

}
