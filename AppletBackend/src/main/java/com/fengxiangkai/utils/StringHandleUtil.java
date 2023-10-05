package com.fengxiangkai.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 主要用于数据库映射处理
 */
@MappedTypes(String[].class)
public class StringHandleUtil implements TypeHandler<String[]> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        // 将字符串数组转换为字符串，并设置为参数值
        String parameterString = String.join(",", parameter);
        ps.setString(i, parameterString);
    }

    @Override
    public String[] getResult(ResultSet rs, String columnName) throws SQLException {
        // 从结果集中获取字符串，再将其转换为字符串数组
        String resultString = rs.getString(columnName);
        System.out.println("####" + resultString);
        return resultString != null ? resultString.split(",") : null;
    }

    @Override
    public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        return new String[0];
    }

    @Override
    public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new String[0];
    }
}
