package com.ruoyi.common.core.redis;

import com.ruoyi.common.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 是否开启redis缓存 true开启 false关闭
     */
    @Value("${ruoyi.redisOpen: false}")
    private boolean open;

    @Around("execution(* com.ruoyi.common.core.redis.RedisCache.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                logger.error("redis error", e);
                throw new CustomException("Redis服务异常");
            }
        }
        return result;
    }
}

