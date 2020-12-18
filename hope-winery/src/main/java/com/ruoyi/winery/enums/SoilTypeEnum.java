package com.ruoyi.winery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 土壤类型状态枚举
 *
 * @author tottimctj
 */
@Getter
public enum SoilTypeEnum {

    /**
     * 数据当前状态
     */
    SAND("SAND", "沙石"),
    STONE("STONE", "砾石"),
    LIMESTONE("LIMESTONE", "石灰岩"),
    MIXING("MIXING", "混合");

    @EnumValue
    private final String value;

    @JsonValue
    private final String desc;

    SoilTypeEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SoilTypeEnum parseEnum(String desc) {

        if (desc.equals(SAND.desc)) {
            return SAND;
        } else if (desc.equals(STONE.desc)) {
            return STONE;
        } else if (desc.equals(LIMESTONE.desc)) {
            return LIMESTONE;
        } else {
            return MIXING;
        }
    }

}