package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtFaceEventDao;

import java.util.HashMap;
import java.util.List;

public interface IFtFaceEventService {
    List<FtFaceEventDao> selectByMap(HashMap<String, Object> map);
}
