package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 购房家属关系业务对象 buy_houses_member
 *
 * @author ruoyi
 * @date 2023-03-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BuyHousesMemberBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 户口簿内页
     */
    @NotBlank(message = "户口簿内页不能为空", groups = { AddGroup.class, EditGroup.class })
    private String insidepageUrl;

    /**
     * 购房申报id
     */
    @NotNull(message = "购房申报id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String buyHousesId;

    /**
     * 身份证正面
     */
    @NotBlank(message = "身份证正面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String frontUrl;

    /**
     * 关系
     */
    @NotBlank(message = "关系不能为空", groups = { AddGroup.class, EditGroup.class })
    private String relation;

    /**
     * 身份证背面
     */
    @NotBlank(message = "身份证背面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String reverseUrl;

    /**
     * 房屋记录
     */
    @NotBlank(message = "房屋记录不能为空", groups = { AddGroup.class, EditGroup.class })
    private String homeRecordUrl;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;


}
