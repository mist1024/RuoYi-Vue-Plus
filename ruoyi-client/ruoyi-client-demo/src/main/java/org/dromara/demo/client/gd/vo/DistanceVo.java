package org.dromara.demo.client.gd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 距离信息
 *
 * @author ye
 */
@NoArgsConstructor
@Data
public class DistanceVo {

    @JsonProperty("status")
    private String status;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("count")
    private String count;
    @JsonProperty("results")
    private List<ResultsDTO> results;

    @NoArgsConstructor
    @Data
    public static class ResultsDTO {
        @JsonProperty("origin_id")
        private String originId;
        @JsonProperty("dest_id")
        private String destId;
        @JsonProperty("distance")
        private String distance;
        @JsonProperty("duration")
        private String duration;
    }
}
