package com.ruoyi.system.fantang.common;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.system.fantang.service.impl.FtConfigDaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

public class DinnerTypeUtils {

    public enum DinnerType {
        breakfast, lunch, dinner, notMatch;
    }

    public static DinnerTypeUtils instance;

    public static DinnerTypeUtils getInstance(IFtConfigDaoService configDaoService ) {
        if (instance == null) {
            instance = new DinnerTypeUtils(configDaoService );
        }
        return instance;
    }
    
    public void updateDinnerTypeUtils(String settings){
        String[] setting = settings.split(",");
        if (setting.length != 6) {
            return;
        }
        String today = DateUtil.today();
        this.breakfastStart = DateUtil.parse(today + " " + setting[0]);
        this.breakfastEnd = DateUtil.parse(today + " " + setting[1]);

        this.lunchStart = DateUtil.parse(today + " " + setting[2]);
        this.lunchEnd = DateUtil.parse(today + " " + setting[3]);

        this.dinnerStart = DateUtil.parse(today + " " + setting[4]);
        this.dinnerEnd = DateUtil.parse(today + " " + setting[5]);

    }

    private Date breakfastStart;
    private Date breakfastEnd;

    private Date lunchStart;
    private Date lunchEnd;

    private Date dinnerStart;
    private Date dinnerEnd;


    /**
     * 初始化用餐类型判断基础类，加载用餐时间参数
     * @param configDaoService
     */
    private DinnerTypeUtils(IFtConfigDaoService configDaoService) {

        Map<String, String> setting = configDaoService.getDinnerTimeSetting();
        String today = DateUtil.today();
        this.breakfastStart = DateUtil.parse(today + " " + setting.get("breakfastStart"));
        this.breakfastEnd = DateUtil.parse(today + " " + setting.get("breakfastEnd"));

        this.lunchStart = DateUtil.parse(today + " " + setting.get("lunchStart"));
        this.lunchEnd = DateUtil.parse(today + " " + setting.get("lunchEnd"));

        this.dinnerStart = DateUtil.parse(today + " " + setting.get("dinnerStart"));
        this.dinnerEnd = DateUtil.parse(today + " " + setting.get("dinnerEnd"));

    }

    /**
     * 根据当前时间判断现在的就餐类型，
     *
     * @return 返回就餐类型的枚举类
     */
    public DinnerType getDinnerType() {

        DateTime now = new DateTime(DateTime.now());
        if (now.isIn(this.breakfastStart, this.breakfastEnd)) {
            return DinnerType.breakfast;
        } else if (now.isIn(this.lunchStart, this.lunchEnd)) {
            return DinnerType.lunch;
        } else if (now.isIn(this.dinnerStart, this.dinnerEnd)) {
            return DinnerType.dinner;
        } else {
            return DinnerType.notMatch;
        }
    }

}
