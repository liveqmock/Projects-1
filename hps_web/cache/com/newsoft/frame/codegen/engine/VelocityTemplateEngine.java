package com.newsoft.frame.codegen.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.newsoft.frame.codegen.Configuration;
import com.newsoft.frame.codegen.Constant;
import com.newsoft.frame.codegen.FileHelper;
import com.newsoft.frame.codegen.parameter.ParamBundle;

/**
 * The template engine implementation based on Velocity API.
 * 
 * @author guohb
 * 
 */
public class VelocityTemplateEngine implements TemplateEngine {
	private Log logger = LogFactory.getLog(getClass());

	private VelocityEngine velocityEngine;

	@Override
	public void process(ParamBundle paramBundle, String templateName, String targetFilePath) {
		if (logger.isDebugEnabled()) {
			logger.debug("To process paramBundle: " + paramBundle + " , templateName: " + templateName
					+ " , targetFilePath: " + targetFilePath);
		}

		VelocityEngine engine = getVelocityEngine();
		if (engine == null) {
			logger.error("Failed to create VelocityEngine !");
			return;
		}

		OutputStreamWriter outputStreamWriter = createOutputStreamWriter(targetFilePath);
		if (outputStreamWriter == null) {
			return;
		}

		VelocityContext context = new VelocityContext();
		context.put("params", paramBundle);
		context.put("classNamePrefix", FileHelper.getJavaFilePrefix(paramBundle.getModuleName()));

		try {
			engine.mergeTemplate(templateName, Constant.TEMPLATE_FILE_ENCODING, context, outputStreamWriter);

			outputStreamWriter.flush();

		} catch (Exception e) {
			logger.error("Error when merge template", e);
			return;
		} finally {
			try {
				outputStreamWriter.close();
			} catch (IOException e) {
				logger.error("Error when close  outputStreamWriter", e);
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("Succeed to create source file from template: " + templateName);
		}

	}

	private OutputStreamWriter createOutputStreamWriter(String targetFilePath) {
		OutputStream out = null;
		try {
			FileHelper.createFileIfNotExist(targetFilePath);
			out = new FileOutputStream(targetFilePath);
		} catch (FileNotFoundException e) {
			logger.error("Can't find file: " + targetFilePath, e);
			return null;
		}

		OutputStreamWriter outputStreamWriter = null;
		try {
			outputStreamWriter = new OutputStreamWriter(out, Constant.TEMPLATE_FILE_ENCODING);
		} catch (Exception e) {
			logger.error("Error when create  outputStreamWriter", e);
		}
		return outputStreamWriter;
	}

	private VelocityEngine getVelocityEngine() {
		synchronized (this) {
			if (velocityEngine != null) {
				return velocityEngine;
			}

			Properties properties = new Properties();
			try {
				properties.load(this.getClass().getResourceAsStream("/velocity.properties"));
			} catch (IOException e) {
				logger.error("can't load configuration from velocity.properties", e);
				return null;
			}

			// Override the resource path configuration
			setCustomizedOptions(properties);

			velocityEngine = new VelocityEngine();
			velocityEngine.init(properties);

			if (logger.isInfoEnabled()) {
				logger.info("Succeed to init VelocityEngine instance: " + velocityEngine);
			}
		}

		return velocityEngine;
	}

	private void setCustomizedOptions(Properties properties) {
		Configuration configuration = Configuration.getDefault();
		String resourcePath = configuration.getJavaTemplatePath() + "," + configuration.getPageTemplatePath();
		properties.put("file.resource.loader.path", resourcePath);
	}

}
