package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 公交站信息
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class StopidVo extends GuideEntity {

    /**
     * 公交车站信息列表
     */
    @JsonProperty("busstops")
    private List<BusStop> data;

    /**
     * 公交车站信息
     */
    @NoArgsConstructor
    @Data
    public static class BusStop {

        /**
         * 公交站id
         */
        @JsonProperty("id")
        private String id;

        /**
         * 公交站名
         */
        @JsonProperty("name")
        private String name;

        /**
         * 经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 城市adcode
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 城市citycode
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 途径此站的公交路线列表
         */
        @JsonProperty("buslines")
        private List<BusLine> buslines;
    }

    /**
     * 公交路线信息类
     */
    @NoArgsConstructor
    @Data
    public static class BusLine {

        /**
         * 公交线路唯一id
         */
        @JsonProperty("id")
        private String id;

        /**
         * 公交线路途径此站的经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 线路名称
         */
        @JsonProperty("name")
        private String name;

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
    }

}
