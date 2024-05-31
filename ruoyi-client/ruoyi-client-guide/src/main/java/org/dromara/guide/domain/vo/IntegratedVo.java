package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 公交路径规划
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class IntegratedVo extends GuideEntity {

    /**
     * 公交换乘信息列表
     */
    @JsonProperty("route")
    private List<Route> routeList;

    /**
     * 公交换乘信息类
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
         * 起点和终点的步行距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 出租车费用，单位：元
         */
        @JsonProperty("taxi_cost")
        private double taxiCost;

        /**
         * 公交换乘方案列表
         */
        @JsonProperty("transits")
        private List<Transit> transitList;

    }

    /**
     * 公交换乘方案类
     */
    @NoArgsConstructor
    @Data
    public static class Transit {

        /**
         * 此换乘方案价格，单位：元
         */
        @JsonProperty("cost")
        private double cost;

        /**
         * 此换乘方案预期时间，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 是否是夜班车
         * 0：非夜班车；1：夜班车
         */
        @JsonProperty("nightflag")
        private int nightFlag;

        /**
         * 此方案总步行距离，单位：米
         */
        @JsonProperty("walking_distance")
        private int walkingDistance;

        /**
         * 换乘路段列表
         */
        @JsonProperty("segments")
        private List<Segment> segmentList;

    }

    /**
     * 换乘路段类
     */
    @NoArgsConstructor
    @Data
    public static class Segment {

        /**
         * 步行导航信息
         */
        @JsonProperty("walking")
        private List<Walking> walking;

        /**
         * 公交导航信息
         */
        @JsonProperty("bus")
        private List<Bus> bus;

        /**
         * 地铁入口
         */
        @JsonProperty("entrance")
        private List<Entrance> entrance;

        /**
         * 地铁出口
         */
        @JsonProperty("exit")
        private List<Entrance> exit;

        /**
         * 乘坐火车的信息
         */
        @JsonProperty("railway")
        private List<Railway> railway;
    }

    /**
     * 步行导航信息
     */
    @NoArgsConstructor
    @Data
    public static class Walking {
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
         * 起点和终点的步行距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 步行预计时间，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 步行路段列表
         */
        @JsonProperty("steps")
        private List<Step> stepList;

        /**
         * 步行路段类
         */
        @NoArgsConstructor
        @Data
        public static class Step {
            /**
             * 此段路的行走介绍
             */
            @JsonProperty("instruction")
            private String instruction;

            /**
             * 路的名字
             */
            @JsonProperty("road")
            private String road;

            /**
             * 此段路的距离，单位：米
             */
            @JsonProperty("distance")
            private int distance;

            /**
             * 此段路预计消耗时间，单位：秒
             */
            @JsonProperty("duration")
            private int duration;

            /**
             * 此段路的坐标
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
        }
    }

    /**
     * 公交导航信息
     */
    @NoArgsConstructor
    @Data
    public static class Bus {

        /**
         * 步行路段列表
         */
        @JsonProperty("buslines")
        private List<BusLine> busLines;

        /**
         * 公交路线类
         */
        @NoArgsConstructor
        @Data
        public static class BusLine {
            /**
             * 此段起乘站信息
             */
            @JsonProperty("departure_stop")
            private Stop departureStop;

            /**
             * 此段下车站
             */
            @JsonProperty("arrival_stop")
            private Stop arrivalStop;

            /**
             * 公交路线名称
             */
            @JsonProperty("name")
            private String name;

            /**
             * 公交路线id
             */
            @JsonProperty("id")
            private String id;

            /**
             * 公交类型
             */
            @JsonProperty("type")
            private String type;

            /**
             * 公交行驶距离，单位：米
             */
            @JsonProperty("distance")
            private int distance;

            /**
             * 公交预计行驶时间，单位：秒
             */
            @JsonProperty("duration")
            private int duration;

            /**
             * 此路段坐标集
             */
            @JsonProperty("polyline")
            private String polyline;

            /**
             * 首班车时间，格式如：0600，代表06：00
             */
            @JsonProperty("start_time")
            private String startTime;

            /**
             * 末班车时间，格式如：2300，代表23：00
             */
            @JsonProperty("end_time")
            private String endTime;

            /**
             * 此段途经公交站数
             */
            @JsonProperty("via_num")
            private int viaNum;

            /**
             * 此段途经公交站点列表
             */
            @JsonProperty("via_stops")
            private List<Stop> viaStops;

            /**
             * 公交站点类
             */
            public static class Stop {
                /**
                 * 途径公交站点信息
                 */
                @JsonProperty("name")
                private String name;

                /**
                 * 公交站点编号
                 */
                @JsonProperty("id")
                private String id;

                /**
                 * 公交站点经纬度
                 */
                @JsonProperty("location")
                private String location;
            }
        }
    }

    /**
     * 地铁出入口
     */
    @NoArgsConstructor
    @Data
    public static class Entrance {

        /**
         * 经纬度
         */
        @JsonProperty("location")
        private String location;

    }

    /**
     * 乘坐火车的信息
     */
    @NoArgsConstructor
    @Data
    public static class Railway {
        /**
         * 线路id编号
         */
        @JsonProperty("id")
        private String id;

        /**
         * 该线路车段耗时
         */
        @JsonProperty("time")
        private String time;

        /**
         * 线路名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 线路车次号
         */
        @JsonProperty("trip")
        private String trip;

        /**
         * 该item换乘段的行车总距离
         */
        @JsonProperty("distance")
        private String distance;

        /**
         * 线路车次类型
         */
        @JsonProperty("type")
        private String type;

        /**
         * 火车始发站信息
         */
        @JsonProperty("departure_stop")
        private StopInfo departureStop;

        /**
         * 火车到站信息
         */
        @JsonProperty("arrival_stop")
        private StopInfo arrivalStop;

        /**
         * 途径站点信息
         */
        @JsonProperty("via_stop")
        private List<StopInfo> viaStops;

        /**
         * 聚合的备选方案
         */
        @JsonProperty("alters")
        private List<AlternativeRoute> alters;

        /**
         * 仓位及价格信息
         */
        @JsonProperty("spaces")
        private List<SpaceInfo> spaces;


        /**
         * 火车到站信息
         */
        @NoArgsConstructor
        @Data
        public static class StopInfo {
            /**
             * 站点ID
             */
            @JsonProperty("id")
            private String id;

            /**
             * 站点名称
             */
            @JsonProperty("name")
            private String name;

            /**
             * 站点经纬度
             */
            @JsonProperty("location")
            private String location;

            /**
             * 站点所在城市的adcode
             */
            @JsonProperty("adcode")
            private String adcode;

            /**
             * 时间信息
             */
            @JsonProperty("time")
            private String time;

            /**
             * 是否为起始站或终点站，1表示是，0表示否
             */
            @JsonProperty("start")
            private int start;

            /**
             * 是否为终点站，1表示是，0表示否
             */
            @JsonProperty("end")
            private int end;

        }


        /**
         * 聚合的备选方案
         */
        @NoArgsConstructor
        @Data
        public static class AlternativeRoute {

            /**
             * 备选方案ID
             */
            @JsonProperty("id")
            private String id;

            /**
             * 备选线路名称
             */
            @JsonProperty("name")
            private String name;

        }

        /**
         * 仓位及价格信息
         */
        @NoArgsConstructor
        @Data
        public static class SpaceInfo {

            /**
             * 仓位编码
             */
            @JsonProperty("code")
            private String code;

            /**
             * 仓位费用
             */
            @JsonProperty("cost")
            private String cost;

        }
    }

}
