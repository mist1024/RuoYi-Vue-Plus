package com.ruoyi.work.utils;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.work.domain.ActBusinessRuleVo;
import com.ruoyi.work.mapper.ActBusinessRuleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.CaseUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

@RequiredArgsConstructor
public class WorkUtils {

    private static final ActBusinessRuleMapper actBusinessRuleMapper=SpringUtils.getBean(ActBusinessRuleMapper.class);
    /**
     * 根据bean获取审核人员
     */
    public static ArrayList<String> getPersonByRule(Long id, Map paramMap){
        ArrayList<String> list = new ArrayList<>();
        //获取业务规则信息
        ActBusinessRuleVo actBusinessRuleVo = actBusinessRuleMapper.selectVoById(id);
        String param  =paramMap.get(actBusinessRuleVo.getParam()).toString();
        //方法名
        String methodName = actBusinessRuleVo.getMethod();
        //类名
        Object beanName = SpringUtils.getBean(actBusinessRuleVo.getBeanName());
        //值
        Class aClass;
        Object params="";
        switch (actBusinessRuleVo.getParamType()){
            case "String":
                aClass= String.valueOf(param).getClass();
                //将数值转类型
                params = String.valueOf(param);
                break;
            case "Integer":
                aClass= Integer.valueOf(param).getClass();
                //将数值转类型
                params = Integer.valueOf(param);
                break;
            case "Long":
                aClass= Long.valueOf(param).getClass();
                //将数值转类型
                params = Long.valueOf(param);
                break;
            case "Float":
                aClass= Float.valueOf(param).getClass();
                //将数值转类型
                params = Float.valueOf(param);
                break;
            case "Double":
                aClass= Double.valueOf(param).getClass();
                //将数值转类型
                params = Double.valueOf(param);
                break;
            default:
                aClass=String.valueOf(param).getClass();
                break;

        }
        //调用方法
        Method method = ReflectionUtils.findMethod(beanName.getClass(), methodName, aClass);
        Object obj = ReflectionUtils.invokeMethod(method, beanName,params);
        System.out.println("obj = " + obj.toString());
        String[] split = obj.toString().split(",");
        Collections.addAll(list,split);
        return list;
    }

    /**
     *根据业务id获取用户信息
     */
    public static Map<String,Object> getInfoToMap( String bean,String businessId){
        String s1 = CaseUtils.toCamelCase(bean,false,new char[]{'_'});
        System.out.println("s1 = " + s1);
        String s2 = s1+"ServiceImpl";
        //类名
        Object beanName = SpringUtils.getBean(s2);
        String me = "queryById";
        //调用方法
        Method method = ReflectionUtils.findMethod(beanName.getClass(), me, Long.class);
        Object obj = ReflectionUtils.invokeMethod(method, beanName,new Long(businessId));
        Map<String, Object> map = BeanUtil.beanToMap(obj);
        System.out.println("obj = " + obj);
        return map;

    }
}
