package com.newsoft.frame.codegen;

import org.apache.commons.lang.StringUtils;

import com.newsoft.frame.codegen.parameter.ParamBundle;
import com.newsoft.frame.codegen.parameter.ParamContext;

/**
 * To generate the java source file.
 * 
 * @author guohb
 * 
 */
public class JavaFileGenerator extends AbstractSourceFileGenerator {

	@Override
	public void generateConcreteFiles(ParamContext paramContext) {
		String outputParentDir = paramContext.getJavaOutputPath();

		for (ParamBundle paramBundle : paramContext.getParamBundles()) {
			if (paramBundle.getFieldList() == null || paramBundle.getFieldList().isEmpty()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Ignore this param bundle since no field list found.");
				}
				continue;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("To create java source for param bundle: " + paramBundle);
			}

			// Create controller file
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_CONTROLLER);

			// Create service files
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_SERVICE);
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_SERVICE_IMPLEMENT);

			// Create dao files
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_DAO_JAVA);
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_DAO_XML);

			// Create entity class, optional
			createSingleFile(outputParentDir, paramBundle, Constant.TEMPLATE_NAME_PO);

			if (logger.isDebugEnabled()) {
				logger.debug("Finished to create java source at directory: " + outputParentDir);
			}
		}
	}

	private void createSingleFile(String outputParentDir, ParamBundle paramBundle, String templateName) {
		String fileNamePrefix = StringUtils.capitalize(paramBundle.getModuleName());
		String filePath = FileHelper.getFilePath(outputParentDir, paramBundle.getPackageName());

		templateEngine.process(paramBundle, templateName,
				filePath + "/" + templateName.replaceAll("XXX", fileNamePrefix));
	}

}
