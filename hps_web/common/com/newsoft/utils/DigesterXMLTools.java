package com.newsoft.utils;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author yuanbin
 * 
 */
public abstract class DigesterXMLTools {
	protected static final Log loger = LogFactory.getLog(DigesterXMLTools.class);

	public Object getXMLBean() {
		InputStream xmlConfigStream = getXMLInputStream();
		if (xmlConfigStream == null) {
			loger.error("找不到XML配置文档");
			return null;
		}

		Digester digester = createDigester();
		if (digester == null) {
			loger.error("创建Digester对象出错，可能是找不到Digester规则文档！");
			return null;
		}

		try {
			return digester.parse(xmlConfigStream);
		} catch (Exception e) {
			loger.error("创建Digester对象出错", e);
			return null;
		}
	}

	public Digester createDigester() {
		String digesteRulesXmlPath = getDigesteRulesXmlPath();
		if (digesteRulesXmlPath == null || StringUtils.isEmpty(digesteRulesXmlPath)) {
			return null;
		}

		URL digesterURL = this.getClass().getClassLoader().getResource(digesteRulesXmlPath);
		return DigesterLoader.createDigester(digesterURL);
	}

	protected abstract String getDigesteRulesXmlPath();

	protected abstract InputStream getXMLInputStream();

}
