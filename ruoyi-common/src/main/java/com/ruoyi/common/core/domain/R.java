package com.ruoyi.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
@ApiModel("请求响应对象")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int OK = 200;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    @ApiModelProperty("消息状态码")
    private int code;

    @ApiModelProperty("消息内容")
    private String msg;

    @ApiModelProperty("数据对象")
    private T data;

    public static <T> R<T> ok() {
        return restResult(OK, null,null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(OK, null,data);
    }

    public static <T> R<T> ok(String msg) {
        return restResult( OK, msg,null);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(OK, msg,data);
    }

    public static <T> R<T> fail() {
        return restResult( FAIL, null,null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult( FAIL, msg,null);
    }

    public static <T> R<T> fail(T data) {
        return restResult(FAIL, null,data);
    }

    public static <T> R<T> fail(String msg, T data) {
        return restResult(FAIL, msg,data);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult( code, msg,null);
    }

    private static <T> R<T> restResult(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
