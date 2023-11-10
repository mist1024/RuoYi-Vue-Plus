package com.go.view.admin.domain.vo;

import lombok.Data;

@Data
public class GoviewFileVo {

    private String id;
    private String fileName;
    private Integer fileSize;
    private String createTime;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 虚拟路径key
     */
    private String virtualKey;

    /**
     * 请求url
     */
    private String fileurl;

}
