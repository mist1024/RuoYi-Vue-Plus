package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 矩形区域内交通态势信息
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class RectangleVo extends GuideEntity {

    /**
     * 交通态势信息
     */
    @JsonProperty("trafficinfo")
    public Trafficinfo data;

    /**
     * 交通态势信息
     */
    @NoArgsConstructor
    @Data
    public static class Trafficinfo {

        /**
         * 路况综述
         */
        @JsonProperty("description")
        private String description;

        /**
         * 路况评价
         */
        @JsonProperty("evaluation")
        private Evaluation evaluation;

        /**
         * 道路列表
         */
        @JsonProperty("roads")
        private List<Road> roads;
    }

    /**
     * 路况评价
     */
    @NoArgsConstructor
    @Data
    public static class Evaluation {

        /**
         * 畅通所占百分比
         */
        @JsonProperty("expedite")
        private int expedite;

        /**
         * 缓行所占百分比
         */
        @JsonProperty("congested")
        private int congested;

        /**
         * 拥堵所占百分比
         */
        @JsonProperty("blocked")
        private int blocked;

        /**
         * 未知路段所占百分比
         */
        @JsonProperty("unknown")
        private int unknown;

        /**
         * 路况
         * 0：未知;1：畅通;2：缓行;3：拥堵
         */
        @JsonProperty("status")
        private int status;

        /**
         * 道路描述
         */
        @JsonProperty("description")
        private String roadDescription;
    }


    /**
     * 道路列表
     */
    @NoArgsConstructor
    @Data
    public static class Road {

        /**
         * 道路名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 路况
         * 0：未知;1：畅通;2：缓行;3：拥堵
         */
        @JsonProperty("status")
        private int status;

        /**
         * 方向描述
         */
        @JsonProperty("direction")
        private String direction;

        /**
         * 车行角度，判断道路正反向使用
         * 以正东方向为0度，逆时针方向为正，取值范围：[0,360]
         */
        @JsonProperty("angle")
        private int angle;

        /**
         * 负值表示反方向
         */
        @JsonProperty("lcodes")
        private int[] lcodes;

        /**
         * 平均速度
         * 单位：km/hr，四舍五入取整
         */
        @JsonProperty("speed")
        private int speed;

        /**
         * 道路坐标集，坐标集合
         * 经度和纬度使用","分隔，坐标之间使用";"分隔。例如：x1,y1;x2,y2
         */
        @JsonProperty("polyline")
        private String polyline;
    }
}
