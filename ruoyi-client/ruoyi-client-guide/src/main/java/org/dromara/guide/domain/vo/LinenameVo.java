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
public class LinenameVo extends GuideEntity {

    /**
     * 公交路线的集合
     */
    @JsonProperty("buslines")
    private List<BusLine> data;

    /**
     * 公交路
     */
    @NoArgsConstructor
    @Data
    public static class BusLine {

        /**
         * 唯一id
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
         * 线路的坐标串
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * 城市的adcode
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 始发站
         */
        @JsonProperty("start_stop")
        private String startStop;

        /**
         * 终点站
         */
        @JsonProperty("end_stop")
        private String endStop;
    }
}
