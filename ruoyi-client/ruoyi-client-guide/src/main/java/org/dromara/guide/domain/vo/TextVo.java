package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 搜索POI
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class TextVo extends GuideEntity {

    /**
     * 建议提示列表
     */
    @JsonProperty("suggestion")
    private Suggestion suggestion;

    /**
     * POI信息列表
     */
    @JsonProperty("pois")
    private List<Poi> pois;

    /**
     * 基于城市的建议实体类
     */
    @NoArgsConstructor
    @Data
    public static class Suggestion {

        /**
         * 关键字
         */
        @JsonProperty("keywords")
        private List<String> keywords;

        /**
         * 城市建议列表
         */
        @JsonProperty("cities")
        private List<City> cities;
    }

    /**
     * 城市信息
     */
    @NoArgsConstructor
    @Data
    public static class City {

        /**
         * 城市名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 关键字数目
         */
        @JsonProperty("num")
        private int num;

        /**
         * 该城市的citycode
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 该城市的adcode
         */
        @JsonProperty("adcode")
        private String adcode;
    }

    /**
     * POI信息实体类
     */
    @NoArgsConstructor
    @Data
    public static class Poi {

        /**
         * 唯一ID
         */
        @JsonProperty("id")
        private String id;

        /**
         * 父POI的ID
         * 当前POI如果有父POI，则返回父POI的ID。可能为空
         */
        @JsonProperty("parent")
        private String parent;

        /**
         * 名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 兴趣点类型
         * 顺序为大类、中类、小类
         * 例如：餐饮服务;中餐厅;特色/地方风味餐厅
         */
        @JsonProperty("type")
        private String type;

        /**
         * 兴趣点类型编码
         * 例如：050118
         */
        @JsonProperty("typecode")
        private String typecode;

        /**
         * 行业类型
         */
        @JsonProperty("biz_type")
        private String bizType;

        /**
         * 地址
         */
        @JsonProperty("address")
        private String address;

        /**
         * 经纬度
         * 格式：X,Y
         */
        @JsonProperty("location")
        private String location;

        /**
         * 离中心点距离
         * 单位：米；仅在周边搜索的时候有值返回
         */
        @JsonProperty("distance")
        private int distance;

        /**
         * POI的电话
         */
        @JsonProperty("tel")
        private String tel;

        /**
         * 邮编
         * extensions=all时返回
         */
        @JsonProperty("postcode")
        private String postcode;

        /**
         * POI的网址
         * extensions=all时返回
         */
        @JsonProperty("website")
        private String website;

        /**
         * POI的电子邮箱
         * extensions=all时返回
         */
        @JsonProperty("email")
        private String email;

        /**
         * POI所在省份编码
         * extensions=all时返回
         */
        @JsonProperty("pcode")
        private String pcode;

        /**
         * POI所在省份名称
         * 若是直辖市的时候，此处直接显示市名，例如北京市
         */
        @JsonProperty("pname")
        private String pname;

        /**
         * 城市编码
         * extensions=all时返回
         */
        @JsonProperty("citycode")
        private String citycode;

        /**
         * 城市名
         * 若是直辖市的时候，此处直接显示市名，例如北京市
         */
        @JsonProperty("cityname")
        private String cityname;

        /**
         * 区域编码
         * extensions=all时返回
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 区域名称
         * 区县级别的返回，例如朝阳区
         */
        @JsonProperty("adname")
        private String adname;

        /**
         * POI的入口经纬度
         * extensions=all时返回，也可用作于POI的到达点；
         */
        @JsonProperty("entr_location")
        private String entrLocation;

        /**
         * POI的出口经纬度
         * 目前不会返回内容；
         */
        @JsonProperty("exit_location")
        private String exitLocation;

        /**
         * POI导航id
         * extensions=all时返回
         */
        @JsonProperty("navi_poiid")
        private String naviPoiid;

        /**
         * 地理格ID
         * extensions=all时返回
         */
        @JsonProperty("gridcode")
        private String gridcode;

        /**
         * 别名
         * extensions=all时返回
         */
        @JsonProperty("alias")
        private String alias;

        /**
         * 停车场类型
         * 仅在停车场类型POI的时候显示该字段
         * 展示停车场类型，包括：地下、地面、路边
         * extensions=all的时候显示
         */
        @JsonProperty("parking_type")
        private String parkingType;

        /**
         * 该POI的特色内容
         * 主要出现在美食类POI中，代表特色菜
         * 例如“烤鱼,麻辣香锅,老干妈回锅肉”
         * extensions=all时返回
         */
        @JsonProperty("tag")
        private String tag;

        /**
         * 是否有室内地图标志
         * 1，表示有室内相关数据
         * 0，代表没有室内相关数据
         * extensions=all时返回
         */
        @JsonProperty("indoor_map")
        private int indoorMap;

        /**
         * 团购数据
         * 此字段逐渐废弃
         */
        @JsonProperty("groupbuy_num")
        private int groupbuyNum;

        /**
         * 所属商圈
         * extensions=all时返回
         */
        @JsonProperty("business_area")
        private String businessArea;

        /**
         * 优惠信息数目
         * 此字段逐渐废弃
         */
        @JsonProperty("discount_num")
        private int discountNum;

        /**
         * 室内地图相关数据
         * 当 indoor_map=0 时，字段为空
         * extensions=all时返回
         */
        @JsonProperty("indoor_data")
        private List<IndoorData> indoorData;

        /**
         * 深度信息
         * extensions=all时返回
         */
        @JsonProperty("biz_ext")
        private List<BizExt> bizExt;

        /**
         * 照片相关信息
         */
        @JsonProperty("photos")
        private List<Photo> photos;
    }


    /**
     * 室内地图相关数据
     */
    @NoArgsConstructor
    @Data
    public static class IndoorData {
        /**
         * 当前POI的父级POI
         * 如果当前POI为建筑物类POI，则cpid为自身POI ID；
         * 如果当前POI为商铺类POI，则cpid为其所在建筑物的POI ID
         */
        @JsonProperty("cpid")
        private String cpid;

        /**
         * 楼层索引
         * 一般会用数字表示，例如8
         */
        @JsonProperty("floor")
        private int floor;

        /**
         * 所在楼层
         * 一般会带有字母，例如F8
         */
        @JsonProperty("truefloor")
        private String trueFloor;
    }

    /**
     * 深度信息
     */
    @NoArgsConstructor
    @Data
    public static class BizExt {
        /**
         * 评分
         * 仅存在于餐饮、酒店、景点、影院类POI之下
         */
        @JsonProperty("rating")
        private double rating;

        /**
         * 人均消费
         * 仅存在于餐饮、酒店、景点、影院类POI之下
         */
        @JsonProperty("cost")
        private double cost;

        /**
         * 是否可订餐
         * 仅存在于餐饮相关POI之下（此字段逐渐废弃）
         */
        @JsonProperty("meal_ordering")
        private boolean mealOrdering;

        /**
         * 是否可选座
         * 仅存在于影院相关POI之下（此字段逐渐废弃）
         */
        @JsonProperty("seat_ordering")
        private boolean seatOrdering;

        /**
         * 是否可订票
         * 仅存在于景点相关POI之下（此字段逐渐废弃）
         */
        @JsonProperty("ticket_ordering")
        private boolean ticketOrdering;

        /**
         * 是否可以订房
         * 仅存在于酒店相关POI之下（此字段逐渐废弃）
         */
        @JsonProperty("hotel_ordering")
        private boolean hotelOrdering;
    }

    /**
     * 照片相关信息
     */
    @NoArgsConstructor
    @Data
    public static class Photo {
        /**
         * 图片介绍
         */
        @JsonProperty("title")
        private String title;

        /**
         * 具体链接
         */
        @JsonProperty("url")
        private String url;
    }
}
