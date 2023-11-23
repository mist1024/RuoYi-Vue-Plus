package org.dromara.web.domain.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Gitee Star 事件
 *
 * @author Lion Li
 */
@Data
public class GiteeStarEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    private String accessToken;
}
