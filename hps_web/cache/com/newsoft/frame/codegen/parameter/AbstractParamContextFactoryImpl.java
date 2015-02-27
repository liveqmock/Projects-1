package com.newsoft.frame.codegen.parameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newsoft.frame.codegen.Configuration;

/**
 * The abstract parameter context factory implementation.
 * 
 * @author guohb
 * 
 */
public abstract class AbstractParamContextFactoryImpl implements ParamContextFactory {

	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public ParamContext create() {
		Configuration configuration = Configuration.getDefault();
		if (configuration == null) {
			logger.error("Can't get default configuration!");
			return null;
		}

		ParamContext context = new ParamContext();
		context.setJavaOutputPath(configuration.getJavaOutputPath());
		context.setJavaTemplatePath(configuration.getJavaTemplatePath());
		context.setPageOutputPath(configuration.getPageOutputPath());
		context.setPageTemplatePath(configuration.getPageTemplatePath());

		fulfillContextOnDemand(context, configuration);

		if (logger.isInfoEnabled()) {
			logger.info("Succeed to create param context: " + context);
		}

		return context;
	}

	protected abstract void fulfillContextOnDemand(ParamContext context, Configuration configuration);

}
