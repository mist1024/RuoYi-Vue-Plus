package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 行政区域
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DistrictVo extends GuideEntity {

    /**
     * 建议结果列表
     */
    @JsonProperty("suggestion")
    private Suggestion suggestion;

    /**
     * 行政区列表
     */
    @JsonProperty("districts")
    private List<District> districts;

    /**
     * 建议结果
     */
    @NoArgsConstructor
    @Data
    public static class Suggestion {

        /**
         * 建议关键字列表
         */
        @JsonProperty("keywords")
        private List<String> keywords;

        /**
         * 建议城市列表
         */
        @JsonProperty("cities")
        private List<String> cities;
    }

    /**
     * 行政区信息
     */
    @NoArgsConstructor
    @Data
    public static class District {
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
         * 行政区名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 行政区边界坐标点
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 区域中心点
         */
        @JsonProperty("center")
        private String center;

        /**
         * 行政区划级别，国家、省份、市、区县、街道
         */
        @JsonProperty("level")
        private String level;

        /**
         * 下级行政区列表
         */
        @JsonProperty("districts")
        private List<District> districts;

    }
}
