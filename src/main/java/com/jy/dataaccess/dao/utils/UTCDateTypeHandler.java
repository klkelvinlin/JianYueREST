package com.jy.dataaccess.dao.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Convert Date to UTC one.
 * 
 * @author wdong
 */
public class UTCDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        Calendar createTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        createTime.setTime(parameter);
        ps.setTimestamp(i, new Timestamp(createTime.getTime().getTime()));

    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        if (sqlTimestamp != null) {
            Calendar createTime = Calendar.getInstance(TimeZone.getDefault());
            createTime.setTime(new Date(sqlTimestamp.getTime()));
            return createTime.getTime();
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        if (sqlTimestamp != null) {
            Calendar createTime = Calendar.getInstance(TimeZone.getDefault());
            createTime.setTime(new Date(sqlTimestamp.getTime()));
            return createTime.getTime();
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        if (sqlTimestamp != null) {
            Calendar createTime = Calendar.getInstance(TimeZone.getDefault());
            createTime.setTime(new Date(sqlTimestamp.getTime()));
            return createTime.getTime();
        }
        return null;
    }

    

}
