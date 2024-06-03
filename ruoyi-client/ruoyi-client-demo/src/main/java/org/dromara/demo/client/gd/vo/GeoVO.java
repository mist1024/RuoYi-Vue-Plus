package org.dromara.demo.client.gd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 地理解析返回对象
 *
 * @author ye
 */
@NoArgsConstructor
@Data
public class GeoVO {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 1
     * geocodes : [{"formatted_address":"北京市朝阳区阜通东大街|6号","country":"中国","province":"北京市","citycode":"010","city":"北京市","district":"朝阳区","township":[],"neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"adcode":"110105","street":"阜通东大街","number":"6号","location":"116.483038,39.990633","level":"门牌号"}]
     */

    @JsonProperty("status")
    private String status;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("count")
    private String count;
    @JsonProperty("geocodes")
    private List<GeocodesDTO> geocodes;

    @NoArgsConstructor
    @Data
    public static class GeocodesDTO {
        /**
         * formatted_address : 北京市朝阳区阜通东大街|6号
         * country : 中国
         * province : 北京市
         * citycode : 010
         * city : 北京市
         * district : 朝阳区
         * township : []
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * adcode : 110105
         * street : 阜通东大街
         * number : 6号
         * location : 116.483038,39.990633
         * level : 门牌号
         */

        @JsonProperty("formatted_address")
        private String formattedAddress;
        @JsonProperty("country")
        private String country;
        @JsonProperty("province")
        private String province;
        @JsonProperty("citycode")
        private String citycode;
        @JsonProperty("city")
        private String city;
        @JsonProperty("district")
        private String district;
        @JsonProperty("township")
        private List<String> township;
        @JsonProperty("neighborhood")
        private NeighborhoodDTO neighborhood;
        @JsonProperty("building")
        private BuildingDTO building;
        @JsonProperty("adcode")
        private String adcode;
        @JsonProperty("street")
        private String street;
        @JsonProperty("number")
        private String number;
        @JsonProperty("location")
        private String location;
        @JsonProperty("level")
        private String level;

        @NoArgsConstructor
        @Data
        public static class NeighborhoodDTO {
            @JsonProperty("name")
            private List<String> name;
            @JsonProperty("type")
            private List<String> type;
        }

        @NoArgsConstructor
        @Data
        public static class BuildingDTO {
            @JsonProperty("name")
            private List<String> name;
            @JsonProperty("type")
            private List<String> type;
        }
    }
}
