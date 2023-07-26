package com.ruoyi.work.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class BusinessDTO implements Serializable {

    private String businessId;

    private Map params;

    private String processKey;


}
