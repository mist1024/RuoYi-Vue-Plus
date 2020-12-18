package com.ruoyi.winery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 灌溉方式状态枚举
 *
 * @author tottimctj
 */
@Getter
public enum IrrigationTypeEnum {

    /**
     * 数据当前状态
     */
    OVERFLOWING("OVERFLOWING", "漫灌"),
    SPRINKLER("SPRINKLER", "喷灌"),
    DRIP("DRIP", "滴灌");


    @EnumValue
    private final String value;

    @JsonValue
    private final String desc;

    IrrigationTypeEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static IrrigationTypeEnum parseEnum(String desc) {

        if (desc.equals(OVERFLOWING.desc)) {
            return OVERFLOWING;
        } else if (desc.equals(SPRINKLER.desc)) {
            return SPRINKLER;
        } else {
            return DRIP;
        }
    }

}