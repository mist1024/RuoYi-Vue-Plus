package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.encrypt.EncryptTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试单表对象 test_demo
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "test_demo", autoResultMap = true)
public class TestDemo extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 排序号
     */
    @OrderBy(asc = false, sort = 1)
    private Integer orderNum;

    /**
     * key键
     */
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String testKey;

    /**
     * 值
     */
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String value;

    /**
     * 版本
     */
    @Version
    private Long version;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;

}
