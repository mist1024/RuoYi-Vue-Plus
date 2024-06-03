package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 地理编码
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class GeocodeVo extends GuideEntity {

    /**
     * 地理编码信息列表，结果对象列表
     */
    @JsonProperty("geocodes")
    private List<Geocode> data;

    /**
     * 地理编码信息列表
     */
    @NoArgsConstructor
    @Data
    public static class Geocode {

        /**
         * 国家，默认返回中国
         */
        @JsonProperty("country")
        private String country;

        /**
         * 地址所在的省份名，例如：北京市
         * 注意：中国的四大直辖市也算作省级单位
         */
        @JsonProperty("province")
        private String province;

        /**
         * 地址所在的城市名，例如：北京市
         */
        @JsonProperty("city")
        private String city;

        /**
         * 城市编码，例如：010
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 地址所在的区，例如：朝阳区
         */
        @JsonProperty("district")
        private String district;

        /**
         * 街道，例如：阜通东大街
         */
        @JsonProperty("street")
        private String street;

        /**
         * 门牌，例如：6号
         */
        @JsonProperty("number")
        private String number;

        /**
         * 区域编码，例如：110101
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 坐标点，经度，纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 匹配级别，参见地理编码匹配级别列表
         */
        @JsonProperty("level")
        private String level;
    }

}
