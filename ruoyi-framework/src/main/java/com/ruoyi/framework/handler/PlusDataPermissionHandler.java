package com.ruoyi.framework.handler;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.service.UserService;
import com.ruoyi.common.enums.DataScopeType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限过滤
 *
 * @author Lion Li
 */
@Slf4j
public class PlusDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScope dataScope = findDataScopeAnnotation(mappedStatementId);
        if (ObjectUtil.isNull(dataScope)) {
            return where;
        }
        SysUser currentUser = SpringUtils.getBean(UserService.class).selectUserById(SecurityUtils.getUserId());
        // 如果是超级管理员，则不过滤数据
        if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
            String dataScopeSql = dataScopeFilter(currentUser, dataScope);
            if (StringUtils.isNotBlank(dataScopeSql)) {
                try {
                    Expression expression = CCJSqlParserUtil.parseExpression(dataScopeSql);
                    return new AndExpression(where, expression);
                } catch (JSQLParserException e) {
                    throw new ServiceException("数据权限解析异常 => " + e.getMessage());
                }
            }
        }
        return where;
    }

    private DataScope findDataScopeAnnotation(String mappedStatementId) {
        StringBuilder sb = new StringBuilder(mappedStatementId);
        int index = sb.lastIndexOf(".");
        String clazzName = sb.substring(0, index);
        String methodName = sb.substring(index + 1, sb.length());
        Class<?> clazz = ClassUtil.loadClass(clazzName);
        List<Method> methods = Arrays.stream(ClassUtil.getDeclaredMethods(clazz))
            .filter(method -> method.getName().equals(methodName)).collect(Collectors.toList());
        DataScope dataScope = null;
        for (Method method : methods) {
            if (AnnotationUtil.hasAnnotation(method, DataScope.class)) {
                dataScope = AnnotationUtil.getAnnotation(method, DataScope.class);
                break;
            }
        }
        return dataScope;
    }

    /**
     * 数据范围过滤
     *
     * @param user      用户
     */
    public static String dataScopeFilter(SysUser user, DataScope annotation) {
        StringBuilder sqlString = new StringBuilder();

        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext();
        EvaluationContext context = new StandardEvaluationContext(new HashMap<>());
        context.setVariable("deptName", annotation.deptName());
        context.setVariable("userName", annotation.userName());
        context.setVariable("userId", user.getUserId());
        context.setVariable("deptId", user.getDeptId());


        for (SysRole role : user.getRoles()) {
            context.setVariable("roleId", role.getRoleId());
            DataScopeType type = DataScopeType.findCode(role.getDataScope());
            if (ObjectUtil.isNull(type)) {
                throw new ServiceException("角色数据范围异常 => " + role.getDataScope());
            }
            if (type == DataScopeType.DATA_SCOPE_ALL) {
                sqlString = new StringBuilder();
                break;
            }
            org.springframework.expression.Expression expression = parser.parseExpression(type.getSql(), parserContext);
            String sql = expression.getValue(context, String.class);
            sqlString.append(sql);
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            return sqlString.substring(4);
        }
        return "";
    }
}
