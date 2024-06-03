package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 公交路线信息
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class LineidVo extends GuideEntity {

    /**
     * 公交线路信息列表
     */
    @JsonProperty("buslines")
    private List<BusLine> data;

    /**
     * 公交线路信息类
     */
    @NoArgsConstructor
    @Data
    public static class BusLine {
        /**
         * 公交线路id
         */
        @JsonProperty("id")
        private String id;

        /**
         * 公交类型
         */
        @JsonProperty("type")
        private String type;

        /**
         * 线路名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 坐标串
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 城市的citycode
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 首发站
         */
        @JsonProperty("start_stop")
        private String startStop;

        /**
         * 末站
         */
        @JsonProperty("end_stop")
        private String endStop;

        /**
         * 首班车时间
         */
        @JsonProperty("start_time")
        private String startTime;

        /**
         * 末班车时间
         */
        @JsonProperty("end_time")
        private String endTime;

        /**
         * 线路 ui 颜色
         */
        @JsonProperty("uicolor")
        private String uiColor;

        /**
         * 线路详细时间
         */
        @JsonProperty("timedesc")
        private String timeDesc;

        /**
         * 全程里程，单位：公里
         */
        @JsonProperty("distance")
        private double distance;

        /**
         * 是否环线，0：否，1：是
         */
        @JsonProperty("loop")
        private int loop;

        /**
         * 线路状态，0：停运，1：正常，2：规划中，3：在建
         */
        @JsonProperty("status")
        private int status;

        /**
         * 反向线路id，如果有反向线路，则记录对向线路，如果没有反向线路，则记录自身
         */
        @JsonProperty("direc")
        private String direction;

        /**
         * 所属公司
         */
        @JsonProperty("company")
        private String company;

        /**
         * 线路长度，单位：公里
         */
        @JsonProperty("distance")
        private double lineDistance;

        /**
         * 起步价，单位：元
         */
        @JsonProperty("basic_price")
        private double basicPrice;

        /**
         * 全程票价，单位：元
         */
        @JsonProperty("total_price")
        private double totalPrice;

        /**
         * 矩形区域
         */
        @JsonProperty("bounds")
        private String bounds;

        /**
         * 途径站列表
         */
        @JsonProperty("busstops")
        private List<BusStop> busstops;
    }

    /**
     * 途径站信息类
     */
    @NoArgsConstructor
    @Data
    public static class BusStop {
        /**
         * 公交站ID
         */
        @JsonProperty("id")
        private String id;

        /**
         * 公交站名
         */
        @JsonProperty("name")
        private String name;

        /**
         * 公交站经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 公交站序号
         */
        @JsonProperty("sequence")
        private int sequence;
    }

}
