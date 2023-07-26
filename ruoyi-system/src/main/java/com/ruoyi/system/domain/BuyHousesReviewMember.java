package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购房复审家属关系对象 buy_houses_review_member
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("buy_houses_review_member")
public class BuyHousesReviewMember extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 户口簿内页
     */
    private String insidepageUrl;
    /**
     * 购房申报id
     */
    private String buyHousesId;
    /**
     * 身份证正面
     */
    private String frontUrl;
    /**
     * 关系
     */
    private String relation;
    /**
     * 身份证背面
     */
    private String reverseUrl;
    /**
     * 房屋记录
     */
    private String homeRecordUrl;
    /**
     * 证件号
     */
    private String cardId;
    /**
     * 姓名
     */
    private String name;

    private Integer number;

}
