package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateType;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流处理
 *
 * @author Lion Li
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String combineKey = getCombineKey(rateLimiter, point);
        try {
            RateType rateType = RateType.OVERALL;
            if (rateLimiter.limitType() == LimitType.CLUSTER) {
                rateType = RateType.PER_CLIENT;
            }
            long number = RedisUtils.rateLimiter(combineKey, rateType, count, time);
            if (number == -1) {
                String message = rateLimiter.message();
                if (StringUtils.startsWith(message, "{") && StringUtils.endsWith(message, "}")) {
                    message = MessageUtils.message(StringUtils.substring(message, 1, message.length() - 1));
                }
                throw new ServiceException(message);
            }
            log.info("限制令牌 => {}, 剩余令牌 => {}, 缓存key => '{}'", count, number, combineKey);
        } catch (Exception e) {
            if(e instanceof ServiceException){
                throw e;
            }else{
                throw new RuntimeException("服务器限流异常，请稍候再试");
            }
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        String key = rateLimiter.key();
        //获取方法(通过方法签名来获取)
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        if(StringUtils.startsWith(key,"#{") && StringUtils.endsWith(key,"}")){
            //获取参数值
            Object[] args = point.getArgs();
            //获取方法上参数的名称
            /**
             * 这里的DefaultParameterNameDiscoverers是Spring自带的解析器
             * 在SpringMvc获取参数的过程中也有用到
             */
            String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
            //使用Spring的el表达式解析
            SpelExpressionParser parser = new SpelExpressionParser();
            //注入到上下文对象中进行解析
            StandardEvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i],args[i]);
            }
            //定义解析模版
            TemplateParserContext parserContent = new TemplateParserContext();
            //解析返回给key
            try {
                key = parser.parseExpression(key,parserContent).getValue(context,String.class) + "";
            }catch (Exception e){
                throw new ServiceException("限流key解析异常!请联系管理员!");
            }
        }
        StringBuilder stringBuffer = new StringBuilder();
        if (rateLimiter.limitType() == LimitType.IP) {
            // 获取请求ip
            stringBuffer.append(ServletUtils.getClientIP()).append("-");
        } else if (rateLimiter.limitType() == LimitType.CLUSTER) {
            // 获取客户端实例id
            stringBuffer.append(RedisUtils.getClient().getId()).append("-");
        }
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        stringBuffer.append(":").append(key);
        return stringBuffer.toString();
    }
}
