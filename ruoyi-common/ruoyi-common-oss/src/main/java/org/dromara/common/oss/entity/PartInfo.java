package org.dromara.common.oss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分片信息对象
 *
 * @author SunnyDeer0911
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer partNumber;

    private String eTag;
}
