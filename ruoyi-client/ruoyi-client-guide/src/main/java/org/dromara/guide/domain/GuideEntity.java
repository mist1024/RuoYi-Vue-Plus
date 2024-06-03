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
public class GuideEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果状态值（0表示失败；1表示成功）
     */
    @JsonProperty("status")
    private int status;

    /**
     * 返回状态说明（如果成功返回"ok"，失败返回错误原因，具体见错误码说明）
     */
    @JsonProperty("info")
    private String info;

    /**
     * 返回状态说明（10000代表正确，详情参阅info状态表）
     */
    @JsonProperty("infocode")
    private int infocode;

    /**
     * 返回结果数目
     */
    @JsonProperty("count")
    private int count;

}
