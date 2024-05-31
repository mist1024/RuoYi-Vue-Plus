package org.dromara.demo.client.gd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询天气返回对象
 *
 * @author ye
 */
@NoArgsConstructor
@Data
public class Weather {

    /**
     * status : 1
     * count : 1
     * info : OK
     * infocode : 10000
     * lives : [{"province":"北京","city":"朝阳区","adcode":"110105","weather":"阴","temperature":"32","winddirection":"西南","windpower":"4","humidity":"41","reporttime":"2021-05-22 15:34:11"}]
     */

    @JsonProperty("status")
    private String status;
    @JsonProperty("count")
    private String count;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("lives")
    private List<LivesDTO> lives;

    @NoArgsConstructor
    @Data
    public static class LivesDTO {
        /**
         * province : 北京
         * city : 朝阳区
         * adcode : 110105
         * weather : 阴
         * temperature : 32
         * winddirection : 西南
         * windpower : 4
         * humidity : 41
         * reporttime : 2021-05-22 15:34:11
         */

        @JsonProperty("province")
        private String province;
        @JsonProperty("city")
        private String city;
        @JsonProperty("adcode")
        private String adcode;
        @JsonProperty("weather")
        private String weather;
        @JsonProperty("temperature")
        private String temperature;
        @JsonProperty("winddirection")
        private String winddirection;
        @JsonProperty("windpower")
        private String windpower;
        @JsonProperty("humidity")
        private String humidity;
        @JsonProperty("reporttime")
        private String reporttime;
    }
}
