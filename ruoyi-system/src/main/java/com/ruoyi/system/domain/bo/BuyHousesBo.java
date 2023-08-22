package com.ruoyi.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.DownloadGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.system.domain.BuyHousesMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】业务对象 buy_houses
 *
 * @author ruoyi
 * @date 2023-02-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BuyHousesBo extends BaseEntity {

    /**
     *
     */
//    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 户口簿内页/护照内页
     */
    @NotBlank(message = "户口簿内页/护照内页不能为空", groups = { AddGroup.class, EditGroup.class })
    private String insidepageUrl;

    /**
     * 身份证/护照
     */
    @NotBlank(message = "身份证/护照不能为空", groups = { AddGroup.class, EditGroup.class ,DownloadGroup.class})
    private String cardId;

    /**
     * 承诺书
     */
    @NotBlank(message = "承诺书不能为空", groups = { AddGroup.class, EditGroup.class })
    private String commitmentUrl;

    /**
     * 单位地址
     */
    @NotBlank(message = "单位地址不能为空", groups = { AddGroup.class, EditGroup.class, DownloadGroup.class })
    private String companyAddress;

    /**
     * 工作单位
     */
    @NotBlank(message = "工作单位不能为空", groups = { AddGroup.class, EditGroup.class , DownloadGroup.class})
    private String companyName;

    /**
     *
     */
//    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createTime;

    /**
     * 申明书
     */
    @NotBlank(message = "申明书不能为空", groups = { AddGroup.class, EditGroup.class })
    private String declarationUrl;

    /**
     * 企业所在地 1.新经济活力区 2.交子公园金融商务区 3.高新西区 4.生物城
     */
    @NotBlank(message = "企业所在地 1.新经济活力区 2.交子公园金融商务区 3.高新西区 4.生物城不能为空", groups = { AddGroup.class, EditGroup.class })
    private String district;

    /**
     * 学历
     */
    @NotBlank(message = "学历不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String education;

    /**
     * 身份证正面
     */
//    @NotBlank(message = "身份证正面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String frontUrl;

    /**
     * 公园城市局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @NotBlank(message = "公园城市局审核状态 1.待审核 2.退件 3.不通过 4.通过不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gyStatus;

    /**
     * 房屋记录
     */
    @NotBlank(message = "房屋记录不能为空", groups = { AddGroup.class, EditGroup.class })
    private String homeRecordUrl;

    /**
     * 户口簿主页
     */
//    @NotBlank(message = "户口簿主页不能为空", groups = { AddGroup.class, EditGroup.class })
    private String homepageUrl;

    /**
     * 劳动合同
     */
    @NotBlank(message = "劳动合同不能为空", groups = { AddGroup.class, EditGroup.class })
    private String laborContractUrl;

    /**
     * 企业营业执照
     */
    @NotBlank(message = "企业营业执照不能为空", groups = { AddGroup.class, EditGroup.class })
    private String licenseUrl;

    /**
     * 婚姻状况 1.未婚 2.已婚 3.离异
     */
    @NotBlank(message = "婚姻状况 1.未婚 2.已婚 3.离异不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maritalStatus;

    /**
     * 婚姻证明材料
     */
    @NotBlank(message = "婚姻证明材料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maritalUrl;

    /**
     * 国籍
     */
    @NotBlank(message = "国籍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nationality;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String phone;

    /**
     * 区域部门审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @NotBlank(message = "区域部门审核状态 1.待审核 2.退件 3.不通过 4.通过不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qyStatus;

    /**
     * 身份证背面
     */
//    @NotBlank(message = "身份证背面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reverseUrl;

    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String sex;

    /**
     * 社会事业局审核状态 1.待审核 2.退件 3.不通过 4.通过
     */
//    @NotBlank(message = "社会事业局审核状态 1.待审核 2.退件 3.不通过 4.通过不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shStatus;

    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "统一社会信用代码不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String socialCode;

    /**
     * 社保证明
     */
    @NotBlank(message = "社保证明不能为空", groups = { AddGroup.class, EditGroup.class })
    private String socialSecurityUrl;

    /**
     * 状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过
     */
//    @NotBlank(message = "状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 类型 A B C D
     */
    @NotBlank(message = "类型 A B C D不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String type;

    /**
     * 用户id
     */
//    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class,DownloadGroup.class })
    private String userName;

    /**
     *
     */
//    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date passTime;

    /**
     *人才影像卡
     */
//    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pictureInformationUrl;

    /**
     *工作地址
     */
    @NotBlank(message = "工作地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String workAddress;

    /**
     * 流程key
     */
    private String processKey;

    /**
     * 流程状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processStatus;

    @TableField(exist = false)
    private List<BuyHousesMember> buyHousesMemberList;


    private String version;

    private String companyId;

    @TableField(exist = false)
    private Long[] ids;

    private String apiKey;

    private String step;

}
