package com.ruoyi.common.core.domain.event;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 推送日志对象 push_log
 *
 * @author ruoyi
 * @date 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("push_log")
public class PushLogEvent extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 推送数据
     */
    private String pushData;
    /**
     * 返回结果
     */
    private String resultData;

}
