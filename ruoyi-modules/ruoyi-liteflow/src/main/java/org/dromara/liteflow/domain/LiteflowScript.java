package org.dromara.liteflow.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 脚本对象 liteflow_script
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("liteflow_script")
public class LiteflowScript extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 配置环境
     */
    private String applicationName;

    /**
     * 脚本ID
     */
    private String scriptId;

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 脚本语言类型（groovy | qlexpress | js | python | lua | aviator | java | kotlin）
     */
    private String scriptLanguage;

    /**
     * 脚本类型(script普通脚本节点，脚本里无需返回 switch_script选择脚本节点，脚本里需要返回选择的节点Id boolean_script条件脚本节点，脚本里需要返回true/false for_script数量循环节点，脚本里需要返回数值类型，表示循环次数)
     */
    private String scriptType;

    /**
     * 状态（0未激活 1激活）
     */
    private Integer scriptStatus;

    /**
     * 脚本内容
     */
    private String scriptData;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
