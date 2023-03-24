package com.ruoyi.work.dto;

import com.ruoyi.work.domain.vo.ProcessVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVoResultDto {
    private  String step;
    private List<ProcessVo> processVoList;

}
