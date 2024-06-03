package org.dromara.demo.client.gd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ip获取位置传输对象
 *
 * @author ye
 */
@NoArgsConstructor
@Data
public class IPVO {

    @JsonProperty("status")
    private String status;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("province")
    private String province;
    @JsonProperty("city")
    private String city;
    @JsonProperty("district")
    private String district;
    @JsonProperty("isp")
    private String isp;
    @JsonProperty("location")
    private String location;
    @JsonProperty("ip")
    private String ip;
}
