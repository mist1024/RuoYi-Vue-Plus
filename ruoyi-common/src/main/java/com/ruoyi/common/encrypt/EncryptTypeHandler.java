package com.ruoyi.common.encrypt;

import com.ruoyi.common.utils.spring.SpringUtils;
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
    private final IEncryptorManager encryptorManager = SpringUtils.getBean(IEncryptorManager.class);

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, encryptorManager.encrypt(parameter));
    }


    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return encryptorManager.decrypt(rs.getString(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return encryptorManager.decrypt(rs.getString(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return encryptorManager.decrypt(cs.getString(columnIndex));
    }
}
