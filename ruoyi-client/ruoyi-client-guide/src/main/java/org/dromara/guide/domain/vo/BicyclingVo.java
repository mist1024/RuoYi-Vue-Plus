package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideErrEntity;

import java.util.List;

/**
 * 骑行路径规划
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class BicyclingVo extends GuideErrEntity {

    /**
     * 数据体
     */
    @JsonProperty("data")
    private BicyclingData data;

    /**
     * 数据体实体类
     */
    @NoArgsConstructor
    @Data
    public static class BicyclingData {

        /**
         * 起点坐标，格式:X,Y
         */
        @JsonProperty("origin")
        private String origin;

        /**
         * 终点坐标，格式:X,Y
         */
        @JsonProperty("destination")
        private String destination;

        /**
         * 骑行方案列表信息
         */
        @JsonProperty("paths")
        private List<Path> paths;
    }

    /**
     * 骑行方案类
     */
    @NoArgsConstructor
    @Data
    public static class Path {

        /**
         * 起终点的骑行距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 起终点的骑行时间，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 具体骑行结果列表
         */
        @JsonProperty("steps")
        private List<Step> steps;
    }

    /**
     * 具体骑行结果类
     */
    @NoArgsConstructor
    @Data
    public static class Step {
        /**
         * 路段骑行指示，例如：“骑行54米右转”
         */
        @JsonProperty("instruction")
        private String instruction;

        /**
         * 此段路道路名称，可能为空
         */
        @JsonProperty("road")
        private String road;

        /**
         * 此段路骑行距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 此段路骑行方向，例如：“南”
         */
        @JsonProperty("orientation")
        private String orientation;

        /**
         * 此段路骑行耗时，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 此段路骑行的坐标点，格式：X,Y;X1,Y1;X2,Y2
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 此段路骑行主要动作，可能为空，也可能为左转、右转、向左前方行驶、向右前方行驶等
         */
        @JsonProperty("action")
        private String action;

        /**
         * 此段路骑行辅助动作，例如：“到达目的地”
         */
        @JsonProperty("assistant_action")
        private String assistantAction;
    }
}
