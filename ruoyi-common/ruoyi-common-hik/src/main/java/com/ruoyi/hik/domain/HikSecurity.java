package com.ruoyi.hik.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

@Data
public class HikSecurity {
    private HikSecurityUserCheck userCheck;

}
