package org.dromara.guide.domain.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轨迹纠偏
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DrivingBo {

    /**
     * 经度
     * 小数点后最多6位
     */
    @JsonProperty("x")
    private double x;

    /**
     * 纬度
     * 小数点后最多6位
     */
    @JsonProperty("y")
    private double y;

    /**
     * 角度
     * 与正北方向的夹角,小数、整数均可
     * 需要注意的是，计算时需要参考到设备的角度来判别方向，如果 ag 参数是0，或者是不合法的参数，会比较大概率导致纠偏计算失败。
     */
    @JsonProperty("ag")
    private double ag;

    /**
     * 时间
     * tm以秒为单位，第一个采集点的tm值从1970年0点开始，其他采集点为与第一个采集点时间的差值
     */
    @JsonProperty("tm")
    private long tm;

    /**
     * 速度
     * 车辆/设备的移动速度，单位：km/h ,小数、整数均可，在计算纠偏时如果速度值不合理，会比较大概率导致纠偏计算失败。在使用时请传入合理的速度值。
     */
    @JsonProperty("sp")
    private double sp;

}
