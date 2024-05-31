package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 未来路径规划
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DrivingV4Vo extends GuideEntity {

    /**
     * 返回结果数据
     */
    @JsonProperty("data")
    private DrivingData data;

    /**
     * 返回结果数据
     */
    @NoArgsConstructor
    @Data
    public static class DrivingData {

        /**
         * 路径规划方案列表
         */
        @JsonProperty("paths")
        private List<Path> paths;

        /**
         * 不同时间的规划以及信息列表
         */
        @JsonProperty("time_infos")
        private List<TimeInfo> timeInfos;

    }

    /**
     * 路径规划方案
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
         * 红绿灯个数
         */
        @JsonProperty("traffic_lights")
        private int trafficLights;

        /**
         * 导航路段列表
         */
        @JsonProperty("steps")
        private List<Step> steps;
    }

    /**
     * 导航路段
     */
    @NoArgsConstructor
    @Data
    public static class Step {

        /**
         * 途径区域的区域代码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 道路名称
         */
        @JsonProperty("road")
        private String road;

        /**
         * 路段距离
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 道路属性字段，是否收费，0：不收费，1：收费。
         */
        @JsonProperty("toll")
        private int toll;

        /**
         * 路段坐标点串
         */
        @JsonProperty("polyline")
        private String polyline;

    }

    /**
     * 不同时间的规划以及信息
     */
    @NoArgsConstructor
    @Data
    public static class TimeInfo {

        /**
         * 出发时间，Unix 时间戳精确到毫秒
         */
        @JsonProperty("starttime")
        private long starttime;

        /**
         * 路线列表
         */
        @JsonProperty("elements")
        private List<Element> elements;

    }

    /**
     * 路线
     */
    @NoArgsConstructor
    @Data
    public static class Element {

        /**
         * 对应的路线
         */
        @JsonProperty("pathindex")
        private int pathindex;

        /**
         * 总时长，单位：分钟
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 总收费，单位：元
         */
        @JsonProperty("tolls")
        private double tolls;

        /**
         * 限行状态，0：代表限行已规避或未限行，1：代表限行无法规避
         */
        @JsonProperty("restriction")
        private int restriction;

        /**
         * 路况信息列表
         */
        @JsonProperty("tmcs")
        private List<Tmc> tmcs;

    }

    /**
     * 路况信息
     */
    @NoArgsConstructor
    @Data
    public static class Tmc {

        /**
         * 路况状态
         */
        @JsonProperty("status")
        private int status;

        /**
         * 路段坐标点
         */
        @JsonProperty("polyline")
        private String polyline;

    }

}
