package org.dromara.question.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : lvxudong
 * @date : 2023/11/27 17:14
 * @enum : RewardsEnum
 * @description :
 **/
@Getter
@AllArgsConstructor
public enum RewardsEnum {

    /**
     * 正常
     */
    EXCHANGE_VOUCHER(1, "兑换券奖励"),
    /**
     * 停用
     */
    CHARACTERS(2, "文字奖励"),
    ;

    private final Integer code;
    private final String name;

    public static String getNameByValue(Integer value) {
        if (value == null) {
            return "";
        }

        return enumList.stream().filter(e -> e.getCode().equals(value)).findAny().map(RewardsEnum::getName).orElse("");
    }

    private static final List<RewardsEnum> enumList;

    static {
        enumList = new ArrayList<>();
        Collections.addAll(enumList, RewardsEnum.values());
    }

}
