package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.xss.Xss;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.mybatis.core.domain.BaseEntity;

/**
 * 通知公告业务对象 sys_notice
 *
 * @author ruoyi
 * @date 2023-02-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeBo extends BaseEntity {

    /**
     * 公告ID
     */
    @NotNull(message = "公告ID不能为空", groups = { EditGroup.class })
    private Long noticeId;

    /**
     * 公告标题
     */
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 50, message = "公告标题不能超过{max}个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;


}
