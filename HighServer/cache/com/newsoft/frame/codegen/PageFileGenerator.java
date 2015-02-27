package com.newsoft.frame.codegen;

import com.newsoft.frame.codegen.parameter.ParamBundle;
import com.newsoft.frame.codegen.parameter.ParamContext;

/**
 * To generate the page file (jsp, js, etc.)
 * 
 * @author guohb
 * 
 */
public class PageFileGenerator extends AbstractSourceFileGenerator {

	@Override
	public void generateConcreteFiles(ParamContext paramContext) {
		String outputParentDir = paramContext.getPageOutputPath();

		for (ParamBundle paramBundle : paramContext.getParamBundles()) {
			if (paramBundle.getFieldList() == null || paramBundle.getFieldList().isEmpty()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Ignore this param bundle since no field list found.");
				}
				continue;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("To create page file source for param bundle: " + paramBundle);
			}

			// Create JSP file
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_PAGE_NAME_MAIN_JSP);
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_PAGE_NAME_FORM_JSP);
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_PAGE_NAME_LIST_JSP);

			// Create JS files
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_PAGE_NAME_FORM_JS);
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_PAGE_NAME_LIST_JS);

			if (logger.isDebugEnabled()) {
				logger.debug("Finished to create page file source at directory: " + outputParentDir);
			}
		}
	}

	private void createSingleFile(String outputParentDir, ParamBundle paramBundle, String templateName) {
		String fileNamePrefix = paramBundle.getModuleName();
		String filePath = FileHelper.getFilePath(outputParentDir, paramBundle.getPackageName());

		templateEngine.process(paramBundle, templateName,
				filePath + "/" + templateName.replaceAll("XXX", fileNamePrefix));
	}

}
