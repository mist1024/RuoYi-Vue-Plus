package org.dromara.question.domain.bo;

import org.dromara.question.domain.Rewards;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 奖品管理业务对象 f_rewards
 *
 * @author lvxudong
 * @date 2023-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Rewards.class, reverseConvertGenerate = false)
public class RewardsBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 奖励类型
     */
    @NotNull(message = "奖励类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer type;

    /**
     * 奖励名称
     */
    @NotBlank(message = "奖励名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 奖励图片
     */
    @NotBlank(message = "奖励图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String image;

    /**
     * 图片描述
     */
    @NotBlank(message = "图片描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String imgDescribe;


}
