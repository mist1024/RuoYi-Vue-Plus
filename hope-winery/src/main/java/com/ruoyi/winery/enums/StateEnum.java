package com.ruoyi.winery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据状态枚举
 *
 * @author zyArcher
 */
@Getter
public enum StateEnum {

    /**
     * 数据当前状态
     */
    OFF("OFF", "停用"),
    ON("ON", "启用"),
    DEL("DEL", "删除");

    @EnumValue
    private final String value;

    @JsonValue
    private final String desc;

    StateEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

}