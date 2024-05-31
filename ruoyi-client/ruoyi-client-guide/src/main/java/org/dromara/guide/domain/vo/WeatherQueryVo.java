package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 天气查询
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class WeatherQueryVo extends GuideEntity {

    /**
     * 实况天气数据信息
     */
    @JsonProperty("lives")
    private List<LiveWeather> lives;

    /**
     * 预报天气信息数据
     */
    @JsonProperty("forecast")
    private List<ForecastWeather> forecast;

    /**
     * 实况天气信息
     */
    @NoArgsConstructor
    @Data
    public static class LiveWeather {

        /**
         * 省份名
         */
        @JsonProperty("province")
        private String province;

        /**
         * 城市名
         */
        @JsonProperty("city")
        private String city;

        /**
         * 区域编码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 天气现象（汉字描述）
         */
        @JsonProperty("weather")
        private String weather;

        /**
         * 实时气温，单位：摄氏度
         */
        @JsonProperty("temperature")
        private String temperature;

        /**
         * 风向描述
         */
        @JsonProperty("winddirection")
        private String winddirection;

        /**
         * 风力级别，单位：级
         */
        @JsonProperty("windpower")
        private String windpower;

        /**
         * 空气湿度
         */
        @JsonProperty("humidity")
        private String humidity;

        /**
         * 数据发布的时间
         */
        @JsonProperty("reporttime")
        private String reporttime;

    }

    /**
     * 预报天气信息
     */
    @NoArgsConstructor
    @Data
    public static class ForecastWeather {

        /**
         * 城市名称
         */
        @JsonProperty("city")
        private String city;

        /**
         * 城市编码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 省份名称
         */
        @JsonProperty("province")
        private String province;

        /**
         * 预报发布时间
         */
        @JsonProperty("reporttime")
        private String reporttime;

        /**
         * 预报数据list结构，元素cast,按顺序为当天、第二天、第三天的预报数据
         */
        @JsonProperty("casts")
        private List<ForecastData> casts;

    }

    /**
     * 预报天气数据实体类
     */
    @NoArgsConstructor
    @Data
    public static class ForecastData {

        /**
         * 日期
         */
        @JsonProperty("date")
        private String date;

        /**
         * 星期几
         */
        @JsonProperty("week")
        private String week;

        /**
         * 白天天气现象
         */
        @JsonProperty("dayweather")
        private String dayweather;

        /**
         * 晚上天气现象
         */
        @JsonProperty("nightweather")
        private String nightweather;

        /**
         * 白天温度
         */
        @JsonProperty("daytemp")
        private String daytemp;

        /**
         * 晚上温度
         */
        @JsonProperty("nighttemp")
        private String nighttemp;

        /**
         * 白天风向
         */
        @JsonProperty("daywind")
        private String daywind;

        /**
         * 晚上风向
         */
        @JsonProperty("nightwind")
        private String nightwind;

        /**
         * 白天风力
         */
        @JsonProperty("daypower")
        private String daypower;

        /**
         * 晚上风力
         */
        @JsonProperty("nightpower")
        private String nightpower;

    }
}
