package com.ruoyi.system.fantang.common;

import java.util.*;

public class NumberToList {

    public static Map<String, String> convertTo(float number, String  level) {
        String[] str = {"fen","jiao","yuan", "shi", "bai", "qian", "wan", "shiwan", "baiwan", "qianwan"};
        Map<String, String> map = new HashMap<>();
        // 最大转换20W以内的数值
        if (number >= 100000000 || number <= 0 ){
            return null;
        }
        long ret = (long) (number*100);
        boolean flag = false;
        boolean clear = false;

        for (String s : str) {
            switch (s) {
                case "fen":
                    map.put(s + level, String.valueOf(ret%10));
                    if (ret < 10) {
                        flag = true;
                    }
                    break;
                case "jiao":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 100) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 10) % 10));
                    }
                    break;
                case "yuan":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 1000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 100) % 10));
                    }
                    break;
                case "shi":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 10000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 1000) % 10));
                    }
                    break;
                case "bai":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 100000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 10000) % 10));
                    }
                    break;
                case "qian":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 1000000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 100000) % 10));
                    }
                    break;
                case "wan":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 10000000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 1000000) % 10));
                    }
                    break;
                case "shiwan":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 100000000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 10000000) % 10));
                    }
                    break;
                case "baiwan":
                    if (flag){
                        if (clear) {
                            map.put(s + level, " ");
                        } else {
                            map.put(s + level, "¥");
                            clear = true;
                        }
                    } else {
                        if (ret < 1000000000) {
                            flag = true;
                        }
                        map.put(s + level, String.valueOf((ret / 100000000) % 10));
                    }
                    break;
                default:
                    break;
            }
        }

//        flag = false;
//        for (String s : str) {
//            if (flag == false) {
//                if (Objects.equals(map.get(s + level), " ")) {
//                    flag = true;
//                    map.put(s + level, "￥");
//                }
//            }
//        }

        return map;
    }

    public static String convertToHan(float number, String  level) {
        Map<String, String> map = convertTo(number, level);
        String han[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String[] str = {"fen","jiao","yuan", "shi", "bai", "qian", "wan", "shiwan", "baiwan", "qianwan"};
        return String.format("%s仟%s佰%s拾%s万%s仟%s佰%s拾%s元%s角%s分",
                han[Integer.parseInt(map.get("qianwan"))],
                han[Integer.parseInt(map.get("baiwan"))],
                han[Integer.parseInt(map.get("shiwan"))],
                han[Integer.parseInt(map.get("wan"))],
                han[Integer.parseInt(map.get("qian"))],
                han[Integer.parseInt(map.get("bai"))],
                han[Integer.parseInt(map.get("shi"))],
                han[Integer.parseInt(map.get("yuan"))],
                han[Integer.parseInt(map.get("jiao"))],
                han[Integer.parseInt(map.get("fen"))]);
    }
}
