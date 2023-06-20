package org.dromara.common.mybatis.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.satoken.utils.LoginHelper;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author warden
 * @Date 2022/12/8 14:56
 */
@Slf4j
public class BeanUtil {
    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String NORMAL = "NORMAL";

    /**
     * 比对两条记录数据库字段是否相同，相同返回true，不同返回false
     *
     * @param compare
     * @param current
     * @return
     */
    public static boolean diffObject(Object compare, Object current) {
        List<Field> fieldList = new ArrayList<>();

        Field[] allFields = ReflectUtil.getFieldsDirectly(compare.getClass(), true);

        String[] dontCompareFields = {"createBy", "createTime", "updateBy", "updateTime", "params", "searchValue", "serialVersionUID"};
        List<String> dCList = new ArrayList<>();
        dCList.addAll(Arrays.asList(dontCompareFields));
        for (Field field : allFields) {
            if (dCList.contains(field.getName())) {
                continue;
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField == null || tableField.exist()) {
                fieldList.add(field);
            }
        }

        for (Field field : fieldList) {
            try {
                //抑制Java对其的检查
                field.setAccessible(true);

                //获取 object 中 field 所代表的属性值
                Object comp = field.get(compare);
                Object curr = field.get(current);
                if (comp != null && curr != null) {
                    if (!comp.toString().equals(curr.toString())) {
                        return false;
                    }
                } else {
                    if ((comp == null && curr != null) || (comp != null && curr == null)) {
                        return false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 比较原List与现List的差异，返回Map
     * Map的三个KEY值：ADD(新增)\REMOVE（删除）\NORMAL（相同）
     *
     * @param compareList 需要比较的list(页面传过来的）
     * @param currList    当前的list（当前数据库中的）
     * @return
     */
    public static Map<String, Set<Long>> diffList(Set<Long> compareList, Set<Long> currList) {

        if (CollectionUtil.isEmpty(compareList) && CollectionUtil.isEmpty(currList)) {
            return null;
        }

        Map<String, Set<Long>> result = new HashMap();

        result.put(ADD, new HashSet<Long>());
        result.put(REMOVE, new HashSet<Long>());
        result.put(NORMAL, new HashSet<Long>());

        if (CollectionUtil.isEmpty(compareList)) {
            result.put(REMOVE, currList);
        } else if (CollectionUtil.isEmpty(currList)) {
            result.put(ADD, compareList);
        } else {
            for (Long comStr : compareList) {
                if (currList.contains(comStr)) {
                    result.get(NORMAL).add(comStr);
                } else {
                    result.get(ADD).add(comStr);
                }
            }

            for (Long currStr : currList) {
                if (compareList.contains(currStr)) {
                    result.get(NORMAL).add(currStr);
                } else {
                    result.get(REMOVE).add(currStr);
                }
            }
        }
        return result;
    }

    public static Map diffList(List<Long> compareList, List<Long> currList) {
        Set<Long> compareSet = new HashSet<>();
        Set<Long> currSet = new HashSet<>();

        if (CollectionUtil.isNotEmpty(compareList)) {
            for (Long val : compareList) {
                compareSet.add(val);
            }
        }

        if (CollectionUtil.isNotEmpty(currList)) {
            for (Long val : currList) {
                currSet.add(val);
            }
        }

        return diffList(compareSet, currSet);

    }

    public static void fillValue(Object object) {
        if (object instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) object;
            Date current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                ? baseEntity.getCreateTime() : new Date();
            baseEntity.setCreateTime(current);
            baseEntity.setUpdateTime(current);
            Long userId = baseEntity.getCreateBy() != null
                ? baseEntity.getCreateBy() : getLoginUserId();
            // 当前已登录 且 创建人为空 则填充
            baseEntity.setCreateBy(userId);
            // 当前已登录 且 更新人为空 则填充
            baseEntity.setUpdateBy(userId);
        }
    }

    /**
     * 获取登录用户名
     */
    private static Long getLoginUserId() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return loginUser.getUserId();
    }
}
