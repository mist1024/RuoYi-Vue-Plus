package com.ruoyi.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PictureTypeEnum {

    SWIPER(1, "轮播图"),
    COMMENT(3, "评论");
    private Integer value;

    private String description;


}
