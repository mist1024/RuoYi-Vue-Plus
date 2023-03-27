package com.ruoyi.work.dto;

import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.vo.HisProcessVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HisProcessVoResultDto {
    private  String step;
    private List<HisProcess> hisProcessVoList;

}
