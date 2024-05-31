package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 三方数据空间检索结果
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class GeohubVo extends GuideEntity {

    /**
     * 符合关键字筛选条件的数据对象
     */
    @JsonProperty("objects")
    private GeohubResults data;

    /**
     * 符合关键字筛选条件的数据对象
     */
    @NoArgsConstructor
    @Data
    public static class GeohubResults {

        /**
         * 点/线/面对象
         */
        @JsonProperty("geometry")
        private Geometry geometry;

        /**
         * 属性
         */
        @JsonProperty("properties")
        private List<GeohubPrertie> properties;
    }

    /**
     * 点/线/面对象
     */
    @NoArgsConstructor
    @Data
    public static class Geometry {

        /**
         * 坐标信息
         */
        @JsonProperty("coordinates")
        private List<String> coordinates;

        /**
         * 类型：point/polyline/polygon/multipoint/multipolyline/multipolygon
         */
        @JsonProperty("type")
        private String type;
    }

    /**
     * 属性
     */
    @NoArgsConstructor
    @Data
    public static class GeohubPrertie {
        /**
         * 属性名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 属性值
         */
        @JsonProperty("value")
        private Object value;
    }

}
