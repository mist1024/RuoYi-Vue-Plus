package com.ruoyi.winery.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 酒庄状态枚举
 *
 * @author tottimctj
 */
@Getter
public enum WineryStatusEnum {

    /**
     * 数据当前状态
     */
    PRODUCE("PRODUCE", "已建成投产"),
    BUILDING_PRODUCE("BUILDING_PRODUCE", "在建已投产"),
    BUILDING("BUILDING", "在建未投产"),
    STOP("STOP", "已停产");

    @EnumValue
    private final String value;

    @JsonValue
    private final String desc;

    WineryStatusEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static WineryStatusEnum parseEnum(String desc) {

        if (desc.equals(BUILDING.desc)) {
            return BUILDING;
        } else if (desc.equals(BUILDING_PRODUCE.desc)) {
            return BUILDING_PRODUCE;
        } else if (desc.equals(PRODUCE.desc)) {
            return PRODUCE;
        } else {
            return STOP;
        }
    }


}