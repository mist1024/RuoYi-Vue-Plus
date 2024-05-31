package org.dromara.guide.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 高德基类
 *
 * @author AprilWind
 */
@Data
public class GuideErrEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     * 30001错误表示抓路失败。当传入点数较少或较稀疏时，可能会导致抓路失败。
     */
    @JsonProperty("errcode")
    private int errcode;

    /**
     * 错误详情
     */
    @JsonProperty("errdetail")
    private String errdetail;

    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String errmsg;

}
