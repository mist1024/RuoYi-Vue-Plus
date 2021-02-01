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
                        if (ret < 100) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/10)%10));
                    }
                    break;
                case "yuan":
                    if (flag) {
                        if (ret < 1000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/100)%10));
                    }
                    break;
                case "shi":
                    if (flag) {
                        if (ret < 10000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                            flag = true;
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/1000)%10));
                    }
                    break;
                case "bai":
                    if (flag) {
                        if (ret < 100000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/10000)%10));
                    }
                    break;
                case "qian":
                    if (flag) {
                        if (ret < 1000000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/100000)%10));
                    }
                    break;
                case "wan":
                    if (flag) {
                        if (ret < 10000000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s +level, String.valueOf((ret/1000000)%10));
                    }
                    break;
                case "shiwan":
                    if (flag) {
                        if (ret < 100000000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/10000000)%10));
                    }
                    break;
                case "baiwan":
                    if (flag) {
                        if (ret < 1000000000) {
                            map.put(s + level, "¥");
                            flag = true;
                        } else {
                            map.put(s + level, " ");
                        }
                    } else {
                        map.put(s + level, String.valueOf((ret/100000000)%10));
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
}
