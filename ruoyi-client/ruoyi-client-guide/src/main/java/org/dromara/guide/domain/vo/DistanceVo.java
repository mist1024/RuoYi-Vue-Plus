package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 距离计算
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DistanceVo extends GuideEntity {

    /**
     * 距离信息列表
     */
    @JsonProperty("results")
    private List<DistanceInfo> data;

    /**
     * 距离信息
     */
    @NoArgsConstructor
    @Data
    public static class DistanceInfo {

        /**
         * 起点坐标序列号（从1开始）
         */
        @JsonProperty("origin_id")
        private int originId;

        /**
         * 终点坐标序列号（从1开始）
         */
        @JsonProperty("dest_id")
        private int destId;

        /**
         * 路径距离，单位：米
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * 预计行驶时间，单位：秒
         */
        @JsonProperty("duration")
        private int duration;

        /**
         * 仅在出错时显示的信息，大部分情况下为"未知错误"
         */
        @JsonProperty("info")
        private String info;

        /**
         * 仅在出错时显示的错误码
         */
        @JsonProperty("code")
        private int code;
    }
}
