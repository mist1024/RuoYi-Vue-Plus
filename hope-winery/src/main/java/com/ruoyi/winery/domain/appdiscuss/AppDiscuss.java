package com.ruoyi.winery.domain.appdiscuss;

import com.baomidou.mybatisplus.annotation.IdType;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * app评论对象 app_discuss
 * 
 * @author ruoyi
 * @date 2021-01-08
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_discuss")
public class AppDiscuss implements Serializable {

private static final long serialVersionUID=1L;


    /** 表主键 */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 创建者 */
    private String createBy;

    /** 数据创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 数据更新时间 */
    private Date updateTime;

    /** 数据状态，ON为数据启用，OFF为数据停用但仍在前端显示，DEL对数据用户来说已经删除 */
    @Excel(name = "数据状态，ON为数据启用，OFF为数据停用但仍在前端显示，DEL对数据用户来说已经删除")
    private Integer state;

    /** 回复评论的id */
    @Excel(name = "回复评论的id")
    private String appDiscussReplyId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String appDiscussText;

    /** 评论的内容id */
    @Excel(name = "评论的内容id")
    private String appAssociationId;

    /** 评论的图片 */
    @Excel(name = "评论的图片")
    private String appDiscussImage;

//    /** 评论视频 */
//    @Excel(name = "评论视频")
//    private String appDiscussVideo;

    /** 评论人姓名 */
    @Excel(name = "评论人姓名")
    private String appDiscussExtraUsername;

    /** 评论人手机号码 */
    @Excel(name = "评论人手机号码")
    private String appDiscussExtraMobile;

    /** 评论推荐 */
    @Excel(name = "评论推荐")
    private Integer appDiscussRecommend;

    /** 评论类型 */
    @Excel(name = "评论类型")
    private Integer appDiscussType;

    /** 评论回复人 */
    @Excel(name = "评论回复人")
    private String appDiscussReplyUser;


    /** 一级评论 */
    @Excel(name = "评论回复人")
    @TableField(exist = false)
    private List<AppDiscuss> children;




}
