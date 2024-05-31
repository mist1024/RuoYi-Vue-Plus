package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 智能硬件定位Vo 2.0
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class Position2Vo extends GuideEntity {

    /**
     * 返回的定位结果列表
     */
    @JsonProperty("position")
    private List<Position> position;

    /**
     * 可选差异化结果返回
     */
    @JsonProperty("show_fields")
    private String showFields;

    /**
     * 结构化地址信息
     */
    @JsonProperty("formatted_address")
    private String formattedAddress;

    /**
     * 地址元素列表
     */
    @JsonProperty("addressComponent")
    private List<AddressComponent> addressComponent;

    /**
     * 定位结果
     */
    @NoArgsConstructor
    @Data
    public static class Position {

        /**
         * 定位结果坐标
         * 如：116.473168,39.993015
         */
        @JsonProperty("location")
        private String location;

        /**
         * 定位精度半径，单位：米
         */
        @JsonProperty("radius")
        private int radius;
    }

    /**
     * 地址元素
     */
    @NoArgsConstructor
    @Data
    public static class AddressComponent {

        /**
         * 国
         */
        @JsonProperty("country")
        private String country;

        /**
         * 省
         */
        @JsonProperty("province")
        private String province;

        /**
         * 市
         */
        @JsonProperty("city")
        private String city;

        /**
         * 区
         */
        @JsonProperty("district")
        private String district;

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
         * 附近街道名称
         */
        @JsonProperty("street")
        private String street;

        /**
         * 周边道路名称
         */
        @JsonProperty("road")
        private String road;

        /**
         * 周边 POI 名称
         */
        @JsonProperty("poi")
        private String poi;
    }
}
