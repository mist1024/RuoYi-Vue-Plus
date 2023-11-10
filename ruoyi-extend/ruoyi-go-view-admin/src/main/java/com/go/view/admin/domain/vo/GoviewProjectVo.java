package com.go.view.admin.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoviewProjectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	private String projectName;

	private Integer state;

	private Integer delFlag;

	private String indexImage;

	private String content;
}
