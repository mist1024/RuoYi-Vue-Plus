package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 驾车路径规划
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DrivingV3Vo extends GuideEntity {

    /**
     * 驾车路径规划信息列表
     */
    @JsonProperty("route")
    private List<Route> data;

    /**
     * 驾车路径规划信息类
     */
    @NoArgsConstructor
    @Data
    public static class Route {

        /**
         * 起点坐标，规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801 经纬度小数点不超过6位
         */
        @JsonProperty("origin")
        private String origin;

        /**
         * 终点坐标，规则： lon，lat（经度，纬度）， “,”分割，如117.500244, 40.417801 经纬度小数点不超过6位
         */
        @JsonProperty("destination")
        private String destination;

        /**
         * 打车费用，单位：元，注意：extensions=all时才会返回
         */
        @JsonProperty("taxi_cost")
        private double taxiCost;

        /**
         * 驾车换乘方案列表
         */
        @JsonProperty("paths")
        private List<Path> pathList;

    }

    /**
     * 驾车换乘方案类
     */
    @NoArgsConstructor
    @Data
    public static class Path {
        /**
         * 行驶距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 预计行驶时间，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 导航策略
         */
        @JsonProperty("strategy")
        private String strategy;

        /**
         * 此导航方案道路收费，单位：元
         */
        @JsonProperty("tolls")
        private double tolls;

        /**
         * 限行结果，0 代表限行已规避或未限行，1 代表限行无法规避
         */
        @JsonProperty("restriction")
        private int restriction;

        /**
         * 红绿灯个数
         */
        @JsonProperty("traffic_lights")
        private int trafficLights;

        /**
         * 收费路段距离
         */
        @JsonProperty("toll_distance")
        private int tollDistance;

        /**
         * 导航路段列表
         */
        @JsonProperty("steps")
        private List<Step> stepList;

    }

    /**
     * 导航路段类
     */
    @NoArgsConstructor
    @Data
    public static class Step {
        /**
         * 行驶指示
         */
        @JsonProperty("instruction")
        private String instruction;

        /**
         * 方向
         */
        @JsonProperty("orientation")
        private String orientation;

        /**
         * 道路名称
         */
        @JsonProperty("road")
        private String road;

        /**
         * 此路段距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 此段收费，单位：元
         */
        @JsonProperty("tolls")
        private double tolls;

        /**
         * 收费路段距离，单位：米
         */
        @JsonProperty("toll_distance")
        private int tollDistance;

        /**
         * 主要收费道路
         */
        @JsonProperty("toll_road")
        private String tollRoad;

        /**
         * 此路段坐标点串，格式为坐标串，如：116.481247,39.990704;116.481270,39.990726
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 导航主要动作
         */
        @JsonProperty("action")
        private String action;

        /**
         * 导航辅助动作
         */
        @JsonProperty("assistant_action")
        private String assistantAction;

        /**
         * 驾车导航详细信息列表
         */
        @JsonProperty("tmcs")
        private List<Tmc> tmcList;
    }

    /**
     * 驾车导航详细信息类
     */
    @NoArgsConstructor
    @Data
    public static class Tmc {
        /**
         * 此段路的长度，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 此段路的交通情况，可能取值为未知、畅通、缓行、拥堵、严重拥堵
         */
        @JsonProperty("status")
        private String status;

        /**
         * 此段路的轨迹，规格：x1,y1;x2,y2
         */
        @JsonProperty("polyline")
        private String polyline;
    }
}
