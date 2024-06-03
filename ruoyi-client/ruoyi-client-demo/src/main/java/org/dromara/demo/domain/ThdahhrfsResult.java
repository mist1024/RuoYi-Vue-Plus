package org.dromara.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 企业信息验证接口
 *
 * @author AprilWind
 */
@NoArgsConstructor
@Data
public class ThdahhrfsResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * true,false
     */
    @JsonProperty("isSuccess")
    private Boolean success;

    /**
     * 异常信息
     */
    @JsonProperty("exception")
    private String exception;

    /**
     * 结果
     */
    @JsonProperty("result")
    private String result;

}
