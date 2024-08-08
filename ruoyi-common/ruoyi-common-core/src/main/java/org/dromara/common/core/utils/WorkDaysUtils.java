package org.dromara.common.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 工作日工具类
 *
 * @author AprilWind
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkDaysUtils {

    private static final Map<Integer, Integer[]> DAYS_IN_YEARS = new HashMap<>();

    /**
     * 根据开始日期和工作日数计算截止日期
     *
     * @param startDate 开始日期
     * @param workDays  工作日数
     * @return 截止日期
     */
    public static Date calculateEndDate(Date startDate, int workDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        // 获取开始日期的年份
        int startYear = getYear(startDate);
        //开始日期年份天
        Integer[] daysArray = daysInYear(startYear);
        int startIndex = getDayOfYearIndex(startDate);
        int endIndex = startIndex;
        // 循环找到指定的工作日数
        while (workDays > 0) {
            // 检查当前日期是否是工作日
            if (daysArray[endIndex] == 0) {
                workDays--;
            }
            // 结束条件：工作日数为零
            if (workDays <= 0) {
                break;
            }
            // 移动到下一天
            endIndex++;
            // 如果移动到下一年
            if (endIndex >= daysArray.length) {
                startYear++;
                daysArray = daysInYear(startYear);
                endIndex = 0;
                // 更新年份
                calendar.set(Calendar.YEAR, startYear);
            }
        }
        // 设置截止日期
        calendar.set(Calendar.DAY_OF_YEAR, endIndex + 1);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的工作日天数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 实际工作日天数
     */
    public static int calculateWorkDays(Date start, Date end) {
        if (start.after(end)) {
            throw new IllegalArgumentException("开始日期不能晚于结束日期");
        }
        int workDays = 0;

        // 获取开始和结束日期的年份
        int startYear = getYear(start);
        int endYear = getYear(end);

        // 如果在同一年
        if (startYear == endYear) {
            //开始日期年份天
            Integer[] daysArray = daysInYear(startYear);
            for (int i = getDayOfYearIndex(start); i <= getDayOfYearIndex(end); i++) {
                if (daysArray[i] == 0) {
                    workDays++;
                }
            }
        } else {//跨年
            // 处理开始年份
            Integer[] startYearDaysArray = daysInYear(startYear);
            int startIndex = getDayOfYearIndex(start);
            for (int i = startIndex; i < startYearDaysArray.length; i++) {
                if (startYearDaysArray[i] == 0) {
                    workDays++;
                }
            }

            // 处理结束年份
            Integer[] endYearDaysArray = daysInYear(endYear);
            int endIndex = getDayOfYearIndex(end);
            for (int i = 0; i <= endIndex; i++) {
                if (endYearDaysArray[i] == 0) {
                    workDays++;
                }
            }

            // 处理中间的完整年份
            for (int year = startYear + 1; year < endYear; year++) {
                Integer[] fullYearDaysArray = daysInYear(year);
                for (Integer integer : fullYearDaysArray) {
                    if (integer == 0) {
                        workDays++;
                    }
                }
            }
        }
        return workDays;
    }

    /**
     * 获取指定年份的天数数组。如果指定年份的数据不存在，则生成并缓存数据
     *
     * @param year 指定年份
     * @return 表示一年中每一天的状态数组，0 表示工作日，1 表示周末
     */
    private static Integer[] daysInYear(Integer year) {
        if (DAYS_IN_YEARS.containsKey(year)) {
            return DAYS_IN_YEARS.get(year);
        } else {
            // 如果年份数据不存在，则生成指定年份的数据
            Integer[] daysArray = generateDaysArray(year);
            DAYS_IN_YEARS.put(year, daysArray);
            return daysArray;
        }
    }

    /**
     * 根据指定年份生成天数数组，其中工作日用 0 表示，周末用 1 表示
     *
     * @param year 指定年份
     * @return 表示一年中每一天的状态数组
     */
    private static Integer[] generateDaysArray(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1);

        int daysInYear = getDaysInYear(year);
        Integer[] daysArray = new Integer[daysInYear];

        for (int i = 0; i < daysInYear; i++) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            daysArray[i] = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) ? 1 : 0;
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        //todo 更新节假日
        return updateDaysArray(daysArray, new ArrayList<>());
    }

    /**
     * 根据节假日下标更新 daysArray，将对应下标的值改为1
     *
     * @param daysArray 原始的天数数组
     * @param holidays  节假日日期列表
     * @return 更新后的天数数组
     */
    private static Integer[] updateDaysArray(Integer[] daysArray, List<Date> holidays) {
        if (holidays == null || holidays.isEmpty()) {
            return daysArray;
        }
        // 遍历节假日下标数组，将对应的 daysArray 元素设置为1
        for (Date holiday : holidays) {
            int index = getDayOfYearIndex(holiday);
            // 确保下标在有效范围内
            if (index >= 0 && index < daysArray.length) {
                daysArray[index] = 1;
            }
        }
        return daysArray;
    }

    /**
     * 获取日期在一年中的下标（天数）
     *
     * @param date 指定日期
     * @return 日期在该年份的下标（天数）
     */
    private static int getDayOfYearIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return dayOfYear - 1;
    }

    /**
     * 获取指定日期的年份
     *
     * @param date 指定日期
     * @return 年份
     */
    private static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定年份的天数
     *
     * @param year 指定年份
     * @return 年份中的天数
     */
    private static int getDaysInYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.DECEMBER, 31);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

}
