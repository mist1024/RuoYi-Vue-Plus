package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 交通事件
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class TrafficVo {

    /**
     * 调用成功，常见代码：
     * <p>
     * 0：Successful.   成功
     */
    @JsonProperty("code")
    private int code;

    /**
     * 事件信息
     */
    @JsonProperty("data")
    private List<TrafficData> data;

    /**
     * 事件信息
     */
    @NoArgsConstructor
    @Data
    public static class TrafficData {

        /**
         * 事件简述
         */
        @JsonProperty("brief")
        private String brief;

        /**
         * 事件预计结束时间
         */
        @JsonProperty("endTime")
        private String endTime;

        /**
         * 事件描述
         */
        @JsonProperty("eventDesc")
        private String eventDesc;

        /**
         * 事件ID
         */
        @JsonProperty("eventID")
        private int eventID;

        /**
         * 事件类型
         */
        @JsonProperty("eventType")
        private int eventType;

        /**
         * 是否高速
         */
        @JsonProperty("expressway")
        private int expressway;

        /**
         * 线路坐标
         */
        @JsonProperty("lines")
        private String lines;

        /**
         * 发布方名称
         */
        @JsonProperty("nickName")
        private String nickName;

        /**
         * 是否权威发布
         */
        @JsonProperty("offcial")
        private int offcial;

        /**
         * 事件图片链接
         */
        @JsonProperty("picture")
        private String picture;

        /**
         * 道路名称
         */
        @JsonProperty("roadName")
        private String roadName;

        /**
         * 数据源编号
         */
        @JsonProperty("source")
        private int source;

        /**
         * 事件开始时间
         */
        @JsonProperty("startTime")
        private String startTime;

        /**
         * 事件最后更新时间
         */
        @JsonProperty("updateTime")
        private String updateTime;

        /**
         * 经度坐标
         */
        @JsonProperty("x")
        private double x;

        /**
         * 纬度坐标
         */
        @JsonProperty("y")
        private double y;
    }
}
