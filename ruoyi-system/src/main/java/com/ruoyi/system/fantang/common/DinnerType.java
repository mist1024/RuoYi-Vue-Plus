package com.ruoyi.system.fantang.common;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public enum DinnerType {
    breakfast,    lunch,    dinner,    notMatch;

    /**
     * 根据当前时间判断现在的就餐类型，
     * @return 返回就餐类型的枚举类
     */
    public static DinnerType GetDinnerType() {

        String today = DateUtil.today();
        Date breakfastStart = DateUtil.parse(today + " 07:30");
        Date breakfastEnd = DateUtil.parse(today + " 08:30");

        Date lunchStart = DateUtil.parse(today + " 11:30");
        Date lunchEnd = DateUtil.parse(today + " 12:30");

        Date dinnerStart = DateUtil.parse(today + " 17:00");
        Date dinnerEnd = DateUtil.parse(today + " 18:00");

        DateTime now = new DateTime(DateTime.now());
        if (now.isIn(breakfastStart, breakfastEnd) == true) {
            return DinnerType.breakfast;
        } else if (now.isIn(lunchStart, lunchEnd) == true) {
            return DinnerType.lunch;
        } else if (now.isIn(dinnerStart, dinnerEnd) == true){
            return DinnerType.dinner;
        }else {
            return DinnerType.notMatch;
        }
    }

    }
