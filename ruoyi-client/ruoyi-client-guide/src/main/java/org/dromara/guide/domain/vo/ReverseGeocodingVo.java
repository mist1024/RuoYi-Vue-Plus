package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 逆地理编码
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class ReverseGeocodingVo extends GuideEntity {

    /**
     * 逆地理编码列表
     */
    @JsonProperty("regeocode")
    private Regeocode data;


    /**
     * 逆地理编码列表
     */
    @NoArgsConstructor
    @Data
    public class Regeocode {

        /**
         *
         */
        @JsonProperty("formatted_address")
        private String formattedAddress;

        /**
         * 地址元素列表
         */
        @JsonProperty("addressComponent")
        private AddressComponent addressComponent;

        /**
         * 社区信息列表
         */
        @JsonProperty("neighborhood")
        private Neighborhood neighborhood;

        /**
         * 楼信息列表
         */
        @JsonProperty("building")
        private Building building;

        /**
         * 门牌信息列表
         */
        @JsonProperty("streetNumber")
        private StreetNumber streetNumber;

        /**
         * 经纬度所属商圈列表
         */
        @JsonProperty("businessAreas")
        private List<BusinessArea> businessAreas;

        /**
         * poi信息列表
         */
        @JsonProperty("pois")
        private List<POI> pois;

        /**
         * 道路信息列表
         */
        @JsonProperty("roads")
        private List<Road> roads;

        /**
         * 道路交叉口列表
         */
        @JsonProperty("roadinters")
        private List<RoadIntersection> roadinters;

        /**
         * aoi信息列表
         */
        @JsonProperty("aois")
        private List<AOI> aois;

    }

    /**
     * 地址元素列表
     */
    @NoArgsConstructor
    @Data
    public static class AddressComponent {

        /**
         * 坐标点所在国家名称 例如：中国
         */
        @JsonProperty("country")
        private String country;

        /**
         * 坐标点所在省名称 例如：北京市
         */
        @JsonProperty("province")
        private String province;

        /**
         * 坐标点所在城市名称 请注意：当城市是省直辖县时返回为空，以及城市为北京、上海、天津、重庆四个直辖市时，该字段返回为空；省直辖县列表
         */
        @JsonProperty("city")
        private List<String> city;

        /**
         * 城市编码 例如：010
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 坐标点所在区 例如：海淀区
         */
        @JsonProperty("district")
        private String district;

        /**
         * 行政区编码 例如：110108
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息） 例如：燕园街道
         */
        @JsonProperty("township")
        private String township;

        /**
         * 乡镇街道编码 例如：110101001000
         */
        @JsonProperty("towncode")
        private String towncode;

    }

    /**
     * 社区信息列表
     */
    @NoArgsConstructor
    @Data
    public static class Neighborhood {

        /**
         * 社区名称 例如：北京大学
         */
        @JsonProperty("name")
        private String name;

        /**
         * POI类型 例如：科教文化服务;学校;高等院校
         */
        @JsonProperty("type")
        private String type;

    }

    /**
     * 楼信息列表
     */
    @NoArgsConstructor
    @Data
    public static class Building {

        /**
         * 建筑名称 例如：万达广场
         */
        @JsonProperty("name")
        private String name;

        /**
         * 类型 例如：科教文化服务;学校;高等院校
         */
        @JsonProperty("type")
        private String type;

    }

    /**
     * 门牌信息列表
     */
    @NoArgsConstructor
    @Data
    public static class StreetNumber {

        /**
         * 街道名称 例如：中关村北二条
         */
        @JsonProperty("street")
        private String street;

        /**
         * 门牌号 例如：3号
         */
        @JsonProperty("number")
        private String number;

        /**
         * 坐标点 经纬度坐标点：经度，纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 方向 坐标点所处街道方位
         */
        @JsonProperty("direction")
        private String direction;

        /**
         * 门牌地址到请求坐标的距离 单位：米
         */
        @JsonProperty("distance")
        private String distance;

    }

    /**
     * 商圈信息
     */
    @NoArgsConstructor
    @Data
    public static class BusinessArea {

        /**
         * 商圈中心点经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 商圈名称  例如：颐和园
         */
        @JsonProperty("name")
        private String name;

        /**
         * 商圈所在区域的adcode 例如：朝阳区/海淀区
         */
        @JsonProperty("id")
        private String id;

    }

    /**
     * poi信息
     */
    @NoArgsConstructor
    @Data
    public class POI {

        /**
         * poi的id
         */
        @JsonProperty("id")
        private String id;

        /**
         * poi点名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * poi类型
         */
        @JsonProperty("type")
        private String type;

        /**
         * 电话
         */
        @JsonProperty("tel")
        private String tel;

        /**
         * 该POI的中心点到请求坐标的距离 单位：米
         */
        @JsonProperty("direction")
        private String direction;

        /**
         * 方向 为输入点相对建筑物的方位
         */
        @JsonProperty("distance")
        private String distance;

        /**
         * poi地址信息
         */
        @JsonProperty("location")
        private String location;

        /**
         * 坐标点
         */
        @JsonProperty("address")
        private String address;

        /**
         *
         */
        @JsonProperty("poiweight")
        private String poiweight;

        /**
         * poi所在商圈名称
         */
        @JsonProperty("businessarea")
        private String businessarea;

    }

    /**
     * 道路信息
     */
    @NoArgsConstructor
    @Data
    public static class Road {

        /**
         * 道路id
         */
        @JsonProperty("id")
        private String id;

        /**
         * 道路名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 道路到请求坐标的距离 单位：米
         */
        @JsonProperty("direction")
        private String direction;

        /**
         * 方位 输入点和此路的相对方位
         */
        @JsonProperty("distance")
        private String distance;

        /**
         * 坐标点
         */
        @JsonProperty("location")
        private String location;

    }

    /**
     * 道路交叉口
     */
    @NoArgsConstructor
    @Data
    public static class RoadIntersection {

        /**
         * 交叉路口到请求坐标的距离 单位：米
         */
        @JsonProperty("direction")
        private String direction;

        /**
         * 方位 输入点相对路口的方位
         */
        @JsonProperty("distance")
        private String distance;

        /**
         * 路口经纬度
         */
        @JsonProperty("location")
        private String location;

        /**
         * 第一条道路id
         */
        @JsonProperty("first_id")
        private String firstId;

        /**
         * 第一条道路名称
         */
        @JsonProperty("first_name")
        private String firstName;

        /**
         * 第二条道路id
         */
        @JsonProperty("second_id")
        private String secondId;

        /**
         * 第二条道路名称
         */
        @JsonProperty("second_name")
        private String secondName;

    }

    /**
     * aoi信息
     */
    @NoArgsConstructor
    @Data
    public static class AOI {

        /**
         * 所属 aoi的id
         */
        @JsonProperty("id")
        private String id;

        /**
         * 所属 aoi 名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 所属 aoi 所在区域编码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 所属 aoi 中心点坐标
         */
        @JsonProperty("location")
        private String location;

        /**
         * 所属aoi点面积 单位：平方米
         */
        @JsonProperty("area")
        private String area;

        /**
         * 输入经纬度是否在aoi面之中  0，代表在aoi内其余整数代表距离AOI的距离
         */
        @JsonProperty("distance")
        private String distance;

        /**
         * 所属 aoi 类型
         */
        @JsonProperty("type")
        private String type;
    }
}
