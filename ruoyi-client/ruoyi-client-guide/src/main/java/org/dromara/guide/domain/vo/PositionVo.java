package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 智能硬件定位Vo
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class PositionVo extends GuideEntity {

    /**
     * 定位结果列表
     */
    @JsonProperty("result")
    private List<LocationItem> data;

    /**
     * 定位结果
     */
    @NoArgsConstructor
    @Data
    public static class LocationItem {

        /**
         * 定位类型
         * 0：没有得到定位结果；
         * 其他数字为：正常获取定位结果
         */
        @JsonProperty("type")
        private int type;

        /**
         * 手机 imei 号
         */
        @JsonProperty("imei")
        private String imei;

        /**
         * 定位经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 定位精度半径，单位：米
         */
        @JsonProperty("radius")
        private double radius;

        /**
         * 位置描述
         */
        @JsonProperty("desc")
        private String desc;

        /**
         * 国家
         */
        @JsonProperty("country")
        private String country;

        /**
         * 省份
         */
        @JsonProperty("province")
        private String province;

        /**
         * 城市
         */
        @JsonProperty("city")
        private String city;

        /**
         * 城市编码
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 区域编码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 道路名
         */
        @JsonProperty("road")
        private String road;

        /**
         * 定位附近的 poi 名称
         */
        @JsonProperty("poi")
        private String poi;

    }
}
