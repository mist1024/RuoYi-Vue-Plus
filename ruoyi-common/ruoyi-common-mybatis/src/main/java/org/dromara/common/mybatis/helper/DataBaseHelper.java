package org.dromara.common.mybatis.helper;

import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.mybatis.enums.DataBaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库助手
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataBaseHelper {

    private static final DynamicRoutingDataSource DS = SpringUtils.getBean(DynamicRoutingDataSource.class);

    /**
     * 获取当前数据库类型
     */
    public static DataBaseType getDataBaseType() {
        DataSource dataSource = DS.determineDataSource();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            return DataBaseType.find(databaseProductName);
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static boolean isMySql() {
        return DataBaseType.MY_SQL == getDataBaseType();
    }

    public static boolean isOracle() {
        return DataBaseType.ORACLE == getDataBaseType();
    }

    public static boolean isPostgerSql() {
        return DataBaseType.POSTGRE_SQL == getDataBaseType();
    }

    public static boolean isSqlServer() {
        return DataBaseType.SQL_SERVER == getDataBaseType();
    }

    /**
     * 根据数据库类型生成用于判断值是否存在于以逗号分隔的字符串中的查询条件
     *
     * @param var1 要判断的值
     * @param var2 逗号分隔的字符串
     * @return 生成的查询条件字符串
     */
    public static String findInSet(Object var1, String var2) {
        DataBaseType dataBasyType = getDataBaseType();
        String var = Convert.toStr(var1);
        if (dataBasyType == DataBaseType.SQL_SERVER) {
            // 对于 SQL Server 数据库，使用 CHARINDEX 函数来判断
            // CHARINDEX 函数用于查找一个字符串在另一个字符串中的位置
            // 这里的查询条件表示查找 var1 是否存在于以逗号分隔的字符串 var2 中
            // charindex(',100,' , ',0,100,101,') <> 0
            return "charindex(',%s,' , ','+%s+',') <> 0".formatted(var, var2);
        } else if (dataBasyType == DataBaseType.POSTGRE_SQL) {
            // 对于 PostgreSQL 数据库，使用 POSITION 函数来判断
            // POSITION 函数用于在一个字符串中查找另一个字符串，并返回其位置
            // 这里的查询条件表示查找 var1 是否存在于以逗号分隔的字符串 var2 中
            // (select position(',100,' in ',0,100,101,')) <> 0
            return "(select position(',%s,' in ','||%s||',')) <> 0".formatted(var, var2);
        } else if (dataBasyType == DataBaseType.ORACLE) {
            // 对于 Oracle 数据库，使用 INSTR 函数来判断
            // INSTR 函数用于在一个字符串中查找另一个字符串，并返回其位置
            // 这里的查询条件表示查找 var1 是否存在于以逗号分隔的字符串 var2 中
            // instr(',0,100,101,' , ',100,') <> 0
            return "instr(','||%s||',' , ',%s,') <> 0".formatted(var2, var);
        }
        // 默认情况下（假设是 MySQL），使用 FIND_IN_SET 函数来判断
        // FIND_IN_SET 函数用于在一个以逗号分隔的字符串中查找一个值，并返回其位置
        // 这里的查询条件表示查找 var1 是否存在于以逗号分隔的字符串 var2 中
        // find_in_set(100 , '0,100,101')
        return "find_in_set('%s' , %s) <> 0".formatted(var, var2);
    }

    /**
     * 获取当前加载的数据库名
     */
    public static List<String> getDataSourceNameList() {
        return new ArrayList<>(DS.getDataSources().keySet());
    }
}
