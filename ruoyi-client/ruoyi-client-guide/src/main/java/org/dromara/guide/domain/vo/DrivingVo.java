package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideErrEntity;

import java.util.List;

/**
 * 轨迹纠偏
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class DrivingVo extends GuideErrEntity {

    /**
     * 数据体
     */
    @JsonProperty("data")
    private DrivingData data;

    /**
     * 数据体实体类
     */
    @NoArgsConstructor
    @Data
    public static class DrivingData {

        /**
         * 总距离
         */
        @JsonProperty("distance")
        private double distance;

        /**
         * 返回坐标合集
         */
        @JsonProperty("points")
        private List<Point> points;

        /**
         * 坐标点实体类
         */
        @NoArgsConstructor
        @Data
        public static class Point {

            /**
             * 经度
             */
            @JsonProperty("x")
            private double x;

            /**
             * 纬度
             */
            @JsonProperty("y")
            private double y;
        }
    }
}
