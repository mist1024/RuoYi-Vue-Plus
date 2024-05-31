package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

/**
 * IP定位
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class IpLocationVo extends GuideEntity {

    /**
     * 省份名称，若为直辖市则显示直辖市名称；如果在局域网IP网段内，则返回“局域网”；非法IP以及国外IP则返回空
     */
    @JsonProperty("province")
    private String province;

    /**
     * 城市名称，若为直辖市则显示直辖市名称；如果为局域网网段内IP或者非法IP或国外IP，则返回空
     */
    @JsonProperty("city")
    private String city;

    /**
     * 城市的adcode编码
     */
    @JsonProperty("adcode")
    private String adcode;

    /**
     * 所在城市矩形区域范围，所在城市范围的左下右上对标对
     */
    @JsonProperty("rectangle")
    private String rectangle;

}
