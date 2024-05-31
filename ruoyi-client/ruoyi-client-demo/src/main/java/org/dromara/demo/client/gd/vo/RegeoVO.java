package org.dromara.demo.client.gd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 逆地理返回传输对象
 *
 * @author ye
 */
@NoArgsConstructor
@Data
public class RegeoVO {

    /**
     * status : 1
     * regeocode : {"addressComponent":{"city":[],"province":"北京市","adcode":"110108","district":"海淀区","towncode":"110108015000","streetNumber":{"number":"5号","location":"116.310454,39.992734","direction":"东北","distance":"94.5489","street":"颐和园路"},"country":"中国","township":"燕园街道","businessAreas":[{"location":"116.303364,39.976410","name":"万泉河","id":"110108"},{"location":"116.314222,39.982490","name":"中关村","id":"110108"},{"location":"116.294214,39.996850","name":"西苑","id":"110108"}],"building":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},"neighborhood":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},"citycode":"010"},"formatted_address":"北京市海淀区燕园街道北京大学"}
     * info : OK
     * infocode : 10000
     */

    @JsonProperty("status")
    private String status;
    @JsonProperty("regeocode")
    private RegeocodeDTO regeocode;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;

    @NoArgsConstructor
    @Data
    public static class RegeocodeDTO {
        /**
         * addressComponent : {"city":[],"province":"北京市","adcode":"110108","district":"海淀区","towncode":"110108015000","streetNumber":{"number":"5号","location":"116.310454,39.992734","direction":"东北","distance":"94.5489","street":"颐和园路"},"country":"中国","township":"燕园街道","businessAreas":[{"location":"116.303364,39.976410","name":"万泉河","id":"110108"},{"location":"116.314222,39.982490","name":"中关村","id":"110108"},{"location":"116.294214,39.996850","name":"西苑","id":"110108"}],"building":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},"neighborhood":{"name":"北京大学","type":"科教文化服务;学校;高等院校"},"citycode":"010"}
         * formatted_address : 北京市海淀区燕园街道北京大学
         */

        @JsonProperty("addressComponent")
        private AddressComponentDTO addressComponent;
        @JsonProperty("formatted_address")
        private String formattedAddress;

        @NoArgsConstructor
        @Data
        public static class AddressComponentDTO {
            /**
             * city : []
             * province : 北京市
             * adcode : 110108
             * district : 海淀区
             * towncode : 110108015000
             * streetNumber : {"number":"5号","location":"116.310454,39.992734","direction":"东北","distance":"94.5489","street":"颐和园路"}
             * country : 中国
             * township : 燕园街道
             * businessAreas : [{"location":"116.303364,39.976410","name":"万泉河","id":"110108"},{"location":"116.314222,39.982490","name":"中关村","id":"110108"},{"location":"116.294214,39.996850","name":"西苑","id":"110108"}]
             * building : {"name":"北京大学","type":"科教文化服务;学校;高等院校"}
             * neighborhood : {"name":"北京大学","type":"科教文化服务;学校;高等院校"}
             * citycode : 010
             */

            @JsonProperty("city")
            private List<?> city;
            @JsonProperty("province")
            private String province;
            @JsonProperty("adcode")
            private String adcode;
            @JsonProperty("district")
            private String district;
            @JsonProperty("towncode")
            private String towncode;
            @JsonProperty("streetNumber")
            private StreetNumberDTO streetNumber;
            @JsonProperty("country")
            private String country;
            @JsonProperty("township")
            private String township;
            @JsonProperty("businessAreas")
            private List<BusinessAreasDTO> businessAreas;
            @JsonProperty("building")
            private BuildingDTO building;
            @JsonProperty("neighborhood")
            private NeighborhoodDTO neighborhood;
            @JsonProperty("citycode")
            private String citycode;

            @NoArgsConstructor
            @Data
            public static class StreetNumberDTO {
                /**
                 * number : 5号
                 * location : 116.310454,39.992734
                 * direction : 东北
                 * distance : 94.5489
                 * street : 颐和园路
                 */

                @JsonProperty("number")
                private String number;
                @JsonProperty("location")
                private String location;
                @JsonProperty("direction")
                private String direction;
                @JsonProperty("distance")
                private String distance;
                @JsonProperty("street")
                private String street;
            }

            @NoArgsConstructor
            @Data
            public static class BuildingDTO {
                /**
                 * name : 北京大学
                 * type : 科教文化服务;学校;高等院校
                 */

                @JsonProperty("name")
                private String name;
                @JsonProperty("type")
                private String type;
            }

            @NoArgsConstructor
            @Data
            public static class NeighborhoodDTO {
                /**
                 * name : 北京大学
                 * type : 科教文化服务;学校;高等院校
                 */

                @JsonProperty("name")
                private String name;
                @JsonProperty("type")
                private String type;
            }

            @NoArgsConstructor
            @Data
            public static class BusinessAreasDTO {
                /**
                 * location : 116.303364,39.976410
                 * name : 万泉河
                 * id : 110108
                 */

                @JsonProperty("location")
                private String location;
                @JsonProperty("name")
                private String name;
                @JsonProperty("id")
                private String id;
            }
        }
    }
}
