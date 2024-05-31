package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

/**
 * 坐标转换结果
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class CoordinateConversionVo extends GuideEntity {

    /**
     * 转换之后的坐标。若有多个坐标，则用 “;” 进行区分和间隔
     */
    @JsonProperty("locations")
    private String locations;

}
