package com.ruoyi.framework.handler;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.properties.EncryptFieldProperties;
import com.ruoyi.framework.manager.EncryptorManager;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 加解密字段处理
 *
 * @author 老马
 * @date 2023-01-06 10:46
 */
public class EncryptTypeHandler implements TypeHandler<String> {

    /**
     * 加解密管理者
     */
    private final EncryptorManager encryptorManager = SpringUtils.getBean(EncryptorManager.class);
    private final EncryptFieldProperties properties = SpringUtils.getBean(EncryptFieldProperties.class);

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, encryptorManager.encrypt(parameter, properties));
    }


    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return encryptorManager.decrypt(rs.getString(columnName), properties);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return encryptorManager.decrypt(rs.getString(columnIndex), properties);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return encryptorManager.decrypt(cs.getString(columnIndex), properties);
    }
}
