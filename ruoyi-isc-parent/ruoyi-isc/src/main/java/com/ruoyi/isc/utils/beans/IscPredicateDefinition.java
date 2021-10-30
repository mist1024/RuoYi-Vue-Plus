package com.ruoyi.isc.utils.beans;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Wenchao Gong
 * @date 2021-09-28
 */
@Data
@Validated
public class IscPredicateDefinition {

    @NotNull
    private String name;

    private Map<String, String> args = new LinkedHashMap<>();
}
