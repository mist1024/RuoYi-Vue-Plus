package org.dromara.guide.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.guide.domain.GuideEntity;

import java.util.List;

/**
 * 提示信息
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class InputtipsVo extends GuideEntity {

    /**
     * 建议提示列表
     */
    @JsonProperty("tips")
    private List<Tip> data;

    /**
     * 建议提示
     */
    @NoArgsConstructor
    @Data
    public static class Tip {
        /**
         * 提示信息ID
         * 若数据为POI类型，则返回POI ID;若数据为bus类型，则返回bus id;若数据为busline类型，则返回busline id。
         */
        @JsonProperty("id")
        private String id;

        /**
         * 提示信息名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 所属区域
         * 省+市+区（直辖市为“市+区”）
         */
        @JsonProperty("district")
        private String district;

        /**
         * 区域编码
         * 六位区县编码
         */
        @JsonProperty("adcode")
        private String adcode;

        /**
         * 提示中心点坐标
         * 当搜索数据为busline类型时，此字段不返回
         */
        @JsonProperty("location")
        private String location;

        /**
         * 详细地址
         */
        @JsonProperty("address")
        private String address;
    }
}
