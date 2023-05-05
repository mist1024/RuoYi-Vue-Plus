package com.ruoyi.work.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.work.domain.ActBusinessRuleParam;
import com.ruoyi.work.domain.vo.ActBusinessRuleVo;
import com.ruoyi.work.mapper.ActBusinessRuleMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.CaseUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkUtils {

    private static final ActBusinessRuleMapper actBusinessRuleMapper=SpringUtils.getBean(ActBusinessRuleMapper.class);
    /**
     * 根据bean获取审核人员
     */
    public static List<String> getPersonByRule(Long id, Map<String,Object> variables){
        //获取业务规则信息
        ActBusinessRuleVo businessRule = actBusinessRuleMapper.selectVoById(id);
        try {
            //返回值
            Object obj;
            //方法名称
            String methodName = businessRule.getMethod();
            //全类名
            Object beanName = SpringUtils.getBean(businessRule.getBeanName());
            if (StringUtils.isNotBlank(businessRule.getParam())) {
                List<ActBusinessRuleParam> businessRuleParams = JsonUtils.parseArray(businessRule.getParam(), ActBusinessRuleParam.class);
                Class[] paramClass = new Class[businessRuleParams.size()];
                List<Object> params = new ArrayList<>();
                for (int i = 0; i < businessRuleParams.size(); i++) {
                    if (variables.containsKey(businessRuleParams.get(i).getParam())) {
                        String variable = String.valueOf(variables.get(businessRuleParams.get(i).getParam()));
                        switch (businessRuleParams.get(i).getParamType()) {
                            case "String":
                                paramClass[i] = String.valueOf(variable).getClass();
                                params.add(String.valueOf(variable));
                                break;
                            case "Short":
                                paramClass[i] = Short.valueOf(variable).getClass();
                                params.add(Short.valueOf(variable));
                                break;
                            case "Integer":
                                paramClass[i] = Integer.valueOf(variable).getClass();
                                params.add(Integer.valueOf(variable));
                                break;
                            case "Long":
                                paramClass[i] = Long.valueOf(variable).getClass();
                                params.add(Long.valueOf(variable));
                                break;
                            case "Float":
                                paramClass[i] = Float.valueOf(variable).getClass();
                                params.add(Float.valueOf(variable));
                                break;
                            case "Double":
                                paramClass[i] = Double.valueOf(variable).getClass();
                                params.add(Double.valueOf(variable));
                                break;
                            case "Boolean":
                                paramClass[i] = Boolean.valueOf(variable).getClass();
                                params.add(Boolean.valueOf(variable));
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (ObjectUtil.isEmpty(paramClass) && CollectionUtil.isNotEmpty(businessRuleParams)) {
                    String variableParams = businessRuleParams.stream().map(ActBusinessRuleParam::getParam).collect(Collectors.joining(","));
                    throw new ServiceException("【" + variableParams + "】流程变量不存在");
                }
                Method method = ReflectionUtils.findMethod(beanName.getClass(), methodName, paramClass);
                assert method != null;
                obj = ReflectionUtils.invokeMethod(method, beanName, params.toArray());
            } else {
                Method method = ReflectionUtils.findMethod(beanName.getClass(), methodName);
                assert method != null;
                obj = ReflectionUtils.invokeMethod(method, beanName);
            }
            if (obj == null) {
                throw new ServiceException("任务环节未配置审批人,请确认传值是否正确,检查：【" + businessRule.getBeanName() + "】Bean容器中【" + methodName + "】方法");
            }
            String date = Convert.toStr(obj);
            if (ObjectUtil.isEmpty(date)){
                throw new ServiceException("设置审批人流程出错");
            }
            String strip = StringUtils.strip(obj.toString(), "[]");
            List<String> list = StringUtils.str2List(strip, ",", true, true);
            return list.stream().distinct().collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
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
        System.out.println("map = " + map);
        return map;

    }
}
