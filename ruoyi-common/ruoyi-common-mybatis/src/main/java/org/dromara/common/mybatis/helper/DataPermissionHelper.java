package org.dromara.common.mybatis.helper;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.utils.reflect.ReflectUtils;

import java.util.*;
import java.util.function.Supplier;

/**
 * 数据权限助手
 *
 * @author Lion Li
 * @version 3.5.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unchecked cast")
public class DataPermissionHelper {

    private static final String DATA_PERMISSION_KEY = "data:permission";

    private static final ThreadLocal<Stack<Integer>> REENTRANT_IGNORE_PERMISSION = new ThreadLocal<>();

    private static Stack<Integer> getReentrantIgnorePermissionStack() {
        return Optional.ofNullable(REENTRANT_IGNORE_PERMISSION.get()).orElseGet(() -> {
            REENTRANT_IGNORE_PERMISSION.set(new Stack<>());
            return REENTRANT_IGNORE_PERMISSION.get();
        });
    }

    private static void clearReentrantIgnore() {
        REENTRANT_IGNORE_PERMISSION.remove();
    }

    private static void reentrantIgnoreIncrement() {
        Stack<Integer> reentrantStack = getReentrantIgnorePermissionStack();
        reentrantStack.push(reentrantStack.size() + 1);
    }
    /**
     * 从上下文中获取指定键的变量值，并将其转换为指定的类型
     *
     * @param key 变量的键
     * @param <T> 变量值的类型
     * @return 指定键的变量值，如果不存在则返回 null
     */
    public static <T> T getVariable(String key) {
        Map<String, Object> context = getContext();
        return (T) context.get(key);
    }

    /**
     * 向上下文中设置指定键的变量值
     *
     * @param key   要设置的变量的键
     * @param value 要设置的变量值
     */
    public static void setVariable(String key, Object value) {
        Map<String, Object> context = getContext();
        context.put(key, value);
    }

    /**
     * 获取数据权限上下文
     *
     * @return 存储在SaStorage中的Map对象，用于存储数据权限相关的上下文信息
     * @throws NullPointerException 如果数据权限上下文类型异常，则抛出NullPointerException
     */
    public static Map<String, Object> getContext() {
        SaStorage saStorage = SaHolder.getStorage();
        Object attribute = saStorage.get(DATA_PERMISSION_KEY);
        if (ObjectUtil.isNull(attribute)) {
            saStorage.set(DATA_PERMISSION_KEY, new HashMap<>());
            attribute = saStorage.get(DATA_PERMISSION_KEY);
        }
        if (attribute instanceof Map map) {
            return map;
        }
        throw new NullPointerException("data permission context type exception");
    }

    /**
     * 开启忽略数据权限(开启后需手动调用 {@link #disableIgnore()} 关闭)
     */
    public static void enableIgnore() {
        ThreadLocal<IgnoreStrategy> IGNORE_STRATEGY_LOCAL = (ThreadLocal<IgnoreStrategy>) ReflectUtils.getStaticFieldValue(ReflectUtils.getField(InterceptorIgnoreHelper.class, "IGNORE_STRATEGY_LOCAL"));
        IgnoreStrategy ignoreStrategy = IGNORE_STRATEGY_LOCAL.get();
        if (ignoreStrategy == null) {
            InterceptorIgnoreHelper.handle(IgnoreStrategy.builder().dataPermission(true).build());
        } else {
            if (!Boolean.TRUE.equals(ignoreStrategy.getDataPermission())) {
                ignoreStrategy.setDataPermission(true);
            }
        }
        reentrantIgnoreIncrement();
    }

    /**
     * 关闭忽略数据权限
     */
    public static void disableIgnore() {
        ThreadLocal<IgnoreStrategy> IGNORE_STRATEGY_LOCAL = (ThreadLocal<IgnoreStrategy>) ReflectUtils.getStaticFieldValue(ReflectUtils.getField(InterceptorIgnoreHelper.class, "IGNORE_STRATEGY_LOCAL"));
        IgnoreStrategy ignoreStrategy = IGNORE_STRATEGY_LOCAL.get();
        if (ignoreStrategy != null) {
            boolean noOtherIgnoreStrategy = !Boolean.TRUE.equals(ignoreStrategy.getTenantLine())
                && !Boolean.TRUE.equals(ignoreStrategy.getDynamicTableName())
                && !Boolean.TRUE.equals(ignoreStrategy.getBlockAttack())
                && !Boolean.TRUE.equals(ignoreStrategy.getIllegalSql())
                && CollectionUtil.isEmpty(ignoreStrategy.getOthers());
            Stack<Integer> reentrantStack = getReentrantIgnorePermissionStack();
            boolean empty = reentrantStack.isEmpty() || reentrantStack.pop() == 1;
            if (noOtherIgnoreStrategy && empty) {
                InterceptorIgnoreHelper.clearIgnoreStrategy();
            } else if (empty) {
                if (Boolean.TRUE.equals(ignoreStrategy.getDataPermission())) {
                    ignoreStrategy.setDataPermission(false);
                    clearReentrantIgnore();
                }
            }
        }
    }

    /**
     * 在忽略数据权限中执行
     * <p>禁止在忽略数据权限中执行忽略数据权限</p>
     *
     * @param handle 处理执行方法
     */
    public static void ignore(Runnable handle) {
        enableIgnore();
        try {
            handle.run();
        } finally {
            disableIgnore();
        }
    }

    /**
     * 在忽略数据权限中执行
     * <p>禁止在忽略数据权限中执行忽略数据权限</p>
     *
     * @param handle 处理执行方法
     */
    public static <T> T ignore(Supplier<T> handle) {
        enableIgnore();
        try {
            return handle.get();
        } finally {
            disableIgnore();
        }
    }

}
