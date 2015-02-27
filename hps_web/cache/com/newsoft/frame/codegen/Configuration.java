package com.newsoft.frame.codegen;

import java.io.IOException;
import java.util.Properties;

/**
 * Define the application configuration.
 * 
 * @author guohb
 * 
 */
public class Configuration {
	private static Configuration configuration;

	synchronized public static Configuration getDefault() {
		if (configuration != null) {
			return configuration;
		}

		Properties properties = new Properties();
		try {
			properties.load(Configuration.class.getResourceAsStream("/codegen.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		configuration = new Configuration();
		configuration.setJdbcUrl(properties.getProperty("jdbc.url"));
		configuration.setDriverClass(properties.getProperty("jdbc.driverClassName"));
		configuration.setDbUserName(properties.getProperty("jdbc.username"));
		configuration.setDbPassword(properties.getProperty("jdbc.password"));

		configuration.setEntityClasses(properties.getProperty("frame.code.generate.entity.class"));

		configuration.setTableNames(properties.getProperty("frame.code.generate.table.name"));

		String absolutePath = properties.getProperty("frame.code.generate.project.absolute.path");

		configuration.setJavaOutputPath(properties.getProperty("frame.code.generate.output.java.path", absolutePath
				+ "/generated"));

		configuration.setJavaTemplatePath(properties.getProperty("frame.code.generate.template.java.path", absolutePath
				+ "/templates/java"));

		configuration.setPackages(properties.getProperty("frame.code.generate.module.package.name"));

		configuration.setPageOutputPath(properties.getProperty("frame.code.generate.output.page.path", absolutePath
				+ "/generated"));

		configuration.setPageTemplatePath(properties.getProperty("frame.code.generate.template.page.path", absolutePath
				+ "/templates/page"));

		return configuration;
	}

	/**
	 * The database url for jdbc
	 */
	private String jdbcUrl;

	/**
	 * The jdbc driver class name
	 */
	private String driverClass;

	/**
	 * User name of the database
	 */
	private String dbUserName;

	/**
	 * Password of the database
	 */
	private String dbPassword;

	/**
	 * The java source templates directory
	 */
	private String javaTemplatePath;

	/**
	 * The java source output directory
	 */
	private String javaOutputPath;

	/**
	 * The page file (js, jsp) templates directory
	 */
	private String pageTemplatePath;

	/**
	 * The page file (js, jsp) output directory
	 */
	private String pageOutputPath;

	/**
	 * The packages names, separated by comma ','.
	 */
	private String packages;

	/**
	 * The table names, separated by comma ','.
	 */
	private String tableNames;

	/**
	 * The entity class names, separated by comma ','.
	 */
	private String entityClasses;

	/**
	 * @return the jdbcUrl
	 */
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * @param jdbcUrl
	 *            the jdbcUrl to set
	 */
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	/**
	 * @return the driverClass
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * @param driverClass
	 *            the driverClass to set
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * @return the dbUserName
	 */
	public String getDbUserName() {
		return dbUserName;
	}

	/**
	 * @param dbUserName
	 *            the dbUserName to set
	 */
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * @param dbPassword
	 *            the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * @return the javaTemplatePath
	 */
	public String getJavaTemplatePath() {
		return javaTemplatePath;
	}

	/**
	 * @param javaTemplatePath
	 *            the javaTemplatePath to set
	 */
	public void setJavaTemplatePath(String javaTemplatePath) {
		this.javaTemplatePath = javaTemplatePath;
	}

	/**
	 * @return the javaOutputPath
	 */
	public String getJavaOutputPath() {
		return javaOutputPath;
	}

	/**
	 * @param javaOutputPath
	 *            the javaOutputPath to set
	 */
	public void setJavaOutputPath(String javaOutputPath) {
		this.javaOutputPath = javaOutputPath;
	}

	/**
	 * @return the pageTemplatePath
	 */
	public String getPageTemplatePath() {
		return pageTemplatePath;
	}

	/**
	 * @param pageTemplatePath
	 *            the pageTemplatePath to set
	 */
	public void setPageTemplatePath(String pageTemplatePath) {
		this.pageTemplatePath = pageTemplatePath;
	}

	/**
	 * @return the pageOutputPath
	 */
	public String getPageOutputPath() {
		return pageOutputPath;
	}

	/**
	 * @param pageOutputPath
	 *            the pageOutputPath to set
	 */
	public void setPageOutputPath(String pageOutputPath) {
		this.pageOutputPath = pageOutputPath;
	}

	/**
	 * @return the packages
	 */
	public String getPackages() {
		return packages;
	}

	/**
	 * @param packages
	 *            the packages to set
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}

	/**
	 * @return the tableNames
	 */
	public String getTableNames() {
		return tableNames;
	}

	/**
	 * @param tableNames
	 *            the tableNames to set
	 */
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}

	/**
	 * @return the entityClasses
	 */
	public String getEntityClasses() {
		return entityClasses;
	}

	/**
	 * @param entityClasses
	 *            the entityClasses to set
	 */
	public void setEntityClasses(String entityClasses) {
		this.entityClasses = entityClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Configuration [jdbcUrl=" + jdbcUrl + ", driverClass=" + driverClass + ", dbUserName=" + dbUserName
				+ ", dbPassword=" + dbPassword + ", javaTemplatePath=" + javaTemplatePath + ", javaOutputPath="
				+ javaOutputPath + ", pageTemplatePath=" + pageTemplatePath + ", pageOutputPath=" + pageOutputPath
				+ ", packages=" + packages + ", tableNames=" + tableNames + ", entityClasses=" + entityClasses + "]";
	}

}
