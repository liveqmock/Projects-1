package com.newsoft.frame.codegen.engine;

import com.newsoft.frame.codegen.parameter.ParamBundle;

/**
 * Define the template engine.
 * 
 * @author guohb
 * 
 */
public interface TemplateEngine {

	/**
	 * Generate source file according to the context.
	 * 
	 * @param paramContext
	 * @param templateName
	 *            The template file name
	 * @param targetFile
	 *            The target source file path.
	 */
	void process(ParamBundle paramBundle, String templateName, String targetFilePath);
}
