package com.ruoyi.system.fantang.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberToList {

    public static Map<String, String> convertTo(float number, int level) {
        String[] str = {"fen","jiao","yuan", "shi", "bai", "qian", "wan", "shiwan", "baiwan", "qianwan"};
        Map<String, String> map = new HashMap<>();
        // 最大转换20W以内的数值
        if (number >= 100000000 || number <= 0 )
            return null;
        for (String s : str) {
            switch (s) {
                case "fen":
                    map.put(s + String.valueOf(level), String.valueOf(number%10));
                    break;
                case "jiao":
                    map.put(s + String.valueOf(level), String.valueOf((number/10)%10));
                    break;
                case "yuan":
                    map.put(s + String.valueOf(level), String.valueOf((number/100)%10));
                    break;
                case "shi":
                    map.put(s + String.valueOf(level), String.valueOf((number/1000)%10));
                    break;
                case "bai":
                    map.put(s + String.valueOf(level), String.valueOf((number/10000)%10));
                    break;
                case "qian":
                    map.put(s + String.valueOf(level), String.valueOf((number/100000)%10));
                    break;
                case "wan":
                    map.put(s + String.valueOf(level), String.valueOf((number/1000000)%10));
                    break;
                case "shiwan":
                    map.put(s + String.valueOf(level), String.valueOf((number/10000000)%10));
                    break;
                case "baiwan":
                    map.put(s + String.valueOf(level), String.valueOf((number/100000000)%10));
                    break;
            }
        }
        return map;
    }
}
