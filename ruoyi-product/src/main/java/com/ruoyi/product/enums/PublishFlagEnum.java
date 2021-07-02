package com.ruoyi.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PublishFlagEnum {

    YES("1", "已发布"),
    NO("0", "未发布");

    private String value;

    private String description;
}
