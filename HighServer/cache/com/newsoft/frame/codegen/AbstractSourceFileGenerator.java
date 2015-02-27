package com.newsoft.frame.codegen;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newsoft.frame.codegen.engine.TemplateEngine;
import com.newsoft.frame.codegen.parameter.ParamBundle;
import com.newsoft.frame.codegen.parameter.ParamContext;

/**
 * Define the abstract source file generator.
 * 
 * @author guohb
 * 
 */
public abstract class AbstractSourceFileGenerator implements SourceFileGenerator {

	protected Log logger = LogFactory.getLog(getClass());

	protected TemplateEngine templateEngine;

	@Override
	public void generate(ParamContext paramContext) {
		if (paramContext == null || templateEngine == null) {
			logger.error("paramContext is NULL or templateEngine is NULL");
			return;
		}

		List<ParamBundle> paramBundles = paramContext.getParamBundles();
		if (paramBundles == null || paramBundles.isEmpty()) {
			logger.error("paramBundles is NULL or empty");
			return;
		}

		generateConcreteFiles(paramContext);
	}

	/**
	 * Generate the concrete source files.
	 * 
	 * @param paramContext
	 */
	protected abstract void generateConcreteFiles(ParamContext paramContext);

	/**
	 * @param templateEngine
	 *            the templateEngine to set
	 */
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

}
