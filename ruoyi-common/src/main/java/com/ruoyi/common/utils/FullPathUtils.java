package com.ruoyi.common.utils;

public final class FullPathUtils {

    public static final int FULL_PATH_LENGTH = 30;
    public static final int FULL_PATH_LEVEL_LENGTH = 3;
    public static final char REPEAT_CHAR = '0';
    public static final char REPEAT_CHAR_MAX = '9';

    /**
     * 生成 FULL_PATH
     *
     * @param parentFullPath 父级full_path (可空)
     * @param maxFullPath    同级最大 full_path（可空）
     * @return
     */
    public static String genFullPath(String parentFullPath, String maxFullPath)
    {
        int maxNum = 0;
        String parentPath = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(maxFullPath))
        {
            int start = maxFullPath.length() - FULL_PATH_LEVEL_LENGTH;
            int end = maxFullPath.length() - 1;
            int temp = 0;
            while (start > -1)
            {
                temp = Integer.parseInt(maxFullPath.substring(start, end));
                if (temp > 0)
                {
                    parentPath = maxFullPath.substring(0, start);
                    break;
                }
                end = start;
                start -= FULL_PATH_LEVEL_LENGTH;
            }
            maxNum = temp;
        } else if (StringUtils.isNotBlank(parentFullPath))
        {
            parentPath = getParentPath(parentFullPath);
        }
        int repeat = FULL_PATH_LENGTH - parentPath.length() - FULL_PATH_LEVEL_LENGTH;
        String suffix = StringUtils.repeat(REPEAT_CHAR, repeat);
        return String.format("%s%03d%s", parentPath, ++maxNum, suffix);
    }

    /**
     * 根据FULL_PATH 获取最大 子FULL_PATH
     *
     * @param fullPath
     */
    public static String genMaxFullPath(String fullPath)
    {
        String parentPath = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(fullPath))
        {
            parentPath = getParentPath(fullPath);
        }
        int repeat = FULL_PATH_LENGTH - parentPath.length();
        String suffix = StringUtils.repeat(REPEAT_CHAR_MAX, repeat);
        return parentPath + suffix;
    }

    /**
     * 获取父 Path
     *
     * @param fullPath
     * @return
     */
    private static String getParentPath(String fullPath)
    {
        int temp;
        int start = fullPath.length() - FULL_PATH_LEVEL_LENGTH;
        int end = fullPath.length() - 1;
        while (start > -1)
        {
            temp = Integer.parseInt(fullPath.substring(start, end));
            if (temp > 0)
            {
                return fullPath.substring(0, end);
            }
            end = start;
            start -= FULL_PATH_LEVEL_LENGTH;
        }
        return StringUtils.EMPTY;
    }
}
