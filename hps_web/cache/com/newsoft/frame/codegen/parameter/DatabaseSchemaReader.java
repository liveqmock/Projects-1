package com.newsoft.frame.codegen.parameter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newsoft.frame.codegen.Configuration;

/**
 * To read the database scheme meta data.
 * 
 * @author guohb
 * 
 */
public class DatabaseSchemaReader {

	private Log logger = LogFactory.getLog(getClass());

	private Connection conn;

	public List<Field> parseTable(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			return null;
		}

		List<Field> fieldList = new ArrayList<Field>();
		try {
			Connection connection = getDBConnection();
			String querySql = "select * from " + tableName;

			PreparedStatement pstmt = connection.prepareStatement(querySql);
			ResultSetMetaData rsmd = pstmt.getMetaData();

			int size = rsmd.getColumnCount();

			for (int i = 1; i <= size; i++) {
				Field field = new Field();
				field.setColumnName(rsmd.getColumnName(i));
				field.setFieldName(ParamHelper.getFieldName(field.getColumnName()));

				String fieldType = sqlType2JavaType(rsmd.getColumnTypeName(i));
				field.setFieldType(fieldType);

				fieldList.add(field);
			}
		} catch (SQLException e) {
			logger.error("Error when parse the DB table schema", e);
			closeDBConnection();

			return null;
		}
		return fieldList;
	}

	public void closeDBConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Error when close DB connection", e);
			}
		}
	}

	private Connection getDBConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			return conn;
		}
		Configuration configuration = Configuration.getDefault();
		if (configuration.getJdbcUrl() == null || configuration.getDriverClass() == null
				|| configuration.getDbUserName() == null) {
			return null;
		}
		try {
			Class.forName(configuration.getDriverClass()).newInstance();
		} catch (Exception e) {
			logger.error("Error when load jdbc driver", e);
		}
		conn = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getDbUserName(),
				configuration.getDbPassword() == null ? "" : configuration.getDbPassword());

		if (logger.isDebugEnabled()) {
			logger.debug("Succeed to get a DB connection: " + conn);
		}

		return conn;
	}

	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("smallint")
				|| sqlType.equalsIgnoreCase("tinyint")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
			return "java.util.Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "java.sql.Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "java.sql.Clob";
		}
		return "String";
	}

}
