package com.newsoft.frame.codegen.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define the parameters context.
 * 
 * @author guohb
 * 
 */
public class ParamContext {

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
	 * The attribute map for extension.
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * The parameter bundles corresponding to the PO entities.
	 */
	private List<ParamBundle> paramBundles = new ArrayList<ParamBundle>();

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
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the paramBundles
	 */
	public List<ParamBundle> getParamBundles() {
		return paramBundles;
	}

	/**
	 * @param paramBundles
	 *            the paramBundles to set
	 */
	public void setParamBundles(List<ParamBundle> paramBundles) {
		this.paramBundles = paramBundles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParamContext [javaTemplatePath=" + javaTemplatePath + ", javaOutputPath=" + javaOutputPath
				+ ", pageTemplatePath=" + pageTemplatePath + ", pageOutputPath=" + pageOutputPath + ", attributes="
				+ attributes + ", paramBundles=" + paramBundles + "]";
	}

}
