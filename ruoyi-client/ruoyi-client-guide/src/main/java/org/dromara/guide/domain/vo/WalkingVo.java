package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 步行路径规划
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class WalkingVo extends GuideEntity {

    /**
     * 路线信息列表
     */
    @JsonProperty("route")
    private List<Route> routeList;

    /**
     * 路线信息类
     */
    @NoArgsConstructor
    @Data
    public static class Route {
        /**
         * 起点坐标
         */
        @JsonProperty("origin")
        private String origin;

        /**
         * 终点坐标
         */
        @JsonProperty("destination")
        private String destination;

        /**
         * 步行方案列表
         */
        @JsonProperty("paths")
        private List<Path> pathList;

    }

    /**
     * 步行方案类
     */
    @NoArgsConstructor
    @Data
    public static class Path {
        /**
         * 起点和终点的步行距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 步行时间预计，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 步行结果列表
         */
        @JsonProperty("steps")
        private List<Step> stepList;


    }

    /**
     * 步行结果类
     */
    @NoArgsConstructor
    @Data
    public static class Step {
        /**
         * 路段步行指示
         */
        @JsonProperty("instruction")
        private String instruction;

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
         * 方向
         */
        @JsonProperty("orientation")
        private String orientation;

        /**
         * 此路段预计步行时间
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 此路段坐标点
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 步行主要动作
         */
        @JsonProperty("action")
        private String action;

        /**
         * 步行辅助动作
         */
        @JsonProperty("assistant_action")
        private String assistantAction;

        /**
         * 步行方式类型
         * 0，普通道路
         * <p>
         * 1，人行横道
         * <p>
         * 3，地下通道
         * <p>
         * 4，过街天桥
         * <p>
         * 5，地铁通道
         * <p>
         * 6，公园
         * <p>
         * 7，广场
         * <p>
         * 8，扶梯
         * <p>
         * 9，直梯
         * <p>
         * 10，索道
         * <p>
         * 11，空中通道
         * <p>
         * 12，建筑物穿越通道
         * <p>
         * 13，行人通道
         * <p>
         * 14，游船路线
         * <p>
         * 15，观光车路线
         * <p>
         * 16，滑道
         * <p>
         * 18，扩路
         * <p>
         * 19，道路附属连接线
         * <p>
         * 20，阶梯
         * <p>
         * 21，斜坡
         * <p>
         * 22，桥
         * <p>
         * 23，隧道
         * <p>
         * 30，轮渡
         */
        @JsonProperty("walk_type")
        private int walkType;
    }
}
