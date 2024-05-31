package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * AOI边界
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class PolylineVo extends GuideEntity {

    /**
     * aoi返回的详细数据列表
     */
    @JsonProperty("aois")
    private List<AOI> data;

    /**
     * aoi返回的详细数据
     */
    @NoArgsConstructor
    @Data
    public static class AOI {

        /**
         * aoi名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * aoi唯一标识
         */
        @JsonProperty("id")
        private String id;

        /**
         * aoi中心点经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 边界经纬度坐标串，以“_”分隔
         */
        @JsonProperty("polyline")
        private String polyline;

        /**
         * aoi所属分类
         */
        @JsonProperty("type")
        private String type;

        /**
         * aoi分类编码
         */
        @JsonProperty("typecode")
        private String typecode;

        /**
         * aoi所属省份
         */
        @JsonProperty("pname")
        private String pname;

        /**
         * aoi所属城市
         */
        @JsonProperty("cityname")
        private String cityname;

        /**
         * aoi所属区域
         */
        @JsonProperty("adname")
        private String adname;

        /**
         * aoi详细地址
         */
        @JsonProperty("address")
        private String address;

        /**
         * aoi所属省份编码
         */
        @JsonProperty("pcode")
        private String pcode;

        /**
         * aoi所属城市编码
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * aoi所属区域编码
         */
        @JsonProperty("adcode")
        private String adcode;

    }

}
