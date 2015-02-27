package com.newsoft.frame.codegen.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a parameters bundle corresponding to an entity.
 * 
 * @author guohb
 * 
 */
public class ParamBundle {
	/**
	 * The output package name.
	 */
	private String packageName;

	/**
	 * The entity's table name.
	 */
	private String tableName;

	/**
	 * The primary key column name of the table.
	 */
	private String tablePKName;
	/**
	 * The PO class name
	 */
	private String className;

	/**
	 * The business module name, usually the same as lower case of entity class
	 * name.
	 */
	private String moduleName;

	/**
	 * The field list.
	 */
	private List<Field> fieldList = new ArrayList<Field>();

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the tablePKName
	 */
	public String getTablePKName() {
		return tablePKName;
	}

	/**
	 * @param tablePKName
	 *            the tablePKName to set
	 */
	public void setTablePKName(String tablePKName) {
		this.tablePKName = tablePKName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the fieldList
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}

	/**
	 * @param fieldList
	 *            the fieldList to set
	 */
	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParamBundle [packageName=" + packageName + ", tableName=" + tableName + ", tablePKName=" + tablePKName
				+ ", className=" + className + ", moduleName=" + moduleName + ", fieldList=" + fieldList + "]";
	}

}
