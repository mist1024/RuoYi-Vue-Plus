package com.ruoyi.product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProItemPictureDTO {

    private String key;
    private String value;
    // 返回出参的时候将值设置成url
    private String url;
}
