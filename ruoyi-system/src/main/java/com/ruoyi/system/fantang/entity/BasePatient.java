package com.ruoyi.system.fantang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePatient {
    public String hospitalId;
    public String bedId;
    public String name;
    public String departName;
}
