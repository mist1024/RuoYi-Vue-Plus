package com.ruoyi.isc.utils.beans;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wenchao Gong
 * @date 2021-09-28
 */
@Data
@Validated
public class IscRouteDefinition {

    private String id;

    @NotEmpty
    @Valid
    private List<IscPredicateDefinition> predicates = new ArrayList<>();

    @Valid
    private List<IscFilterDefinition> filters = new ArrayList<>();

    @NotNull
    private URI uri;

    private Map<String, Object> metadata = new HashMap<>();

    private int order = 0;
}
