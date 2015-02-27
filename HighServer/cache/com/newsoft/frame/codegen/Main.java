package com.newsoft.frame.codegen;

import org.apache.commons.lang.StringUtils;

import com.newsoft.frame.codegen.engine.TemplateEngine;
import com.newsoft.frame.codegen.engine.VelocityTemplateEngine;
import com.newsoft.frame.codegen.parameter.ParamContext;
import com.newsoft.frame.codegen.parameter.ParamContextFactory;
import com.newsoft.frame.codegen.parameter.ParamContextFactory4EntityClassImpl;
import com.newsoft.frame.codegen.parameter.ParamContextFactory4JdbcImpl;

/**
 * The main class to generate sample code.
 * 
 * @author guohb
 * 
 */
public class Main {

	public void createSourceFiles() {
		ParamContext paramContext = getParamContextFactory().create();
		if (paramContext == null) {
			System.err.println("Error: can't create param context!");
			return;
		}

		TemplateEngine templateEngine = getTemplateEngine();

		JavaFileGenerator javaFileGenerator = new JavaFileGenerator();
		javaFileGenerator.setTemplateEngine(templateEngine);
		javaFileGenerator.generate(paramContext);

		PageFileGenerator pageFileGenerator = new PageFileGenerator();
		pageFileGenerator.setTemplateEngine(templateEngine);
		pageFileGenerator.generate(paramContext);
	}

	private ParamContextFactory getParamContextFactory() {
		Configuration configuration = Configuration.getDefault();
		if (!StringUtils.isBlank(configuration.getTableNames())) {
			return new ParamContextFactory4JdbcImpl();
		}
		// Default policy is to create context from entity class name.
		return new ParamContextFactory4EntityClassImpl();
	}

	private TemplateEngine getTemplateEngine() {
		return new VelocityTemplateEngine();
	}

	/**
	 * The main method to create source file according to the templates.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Main().createSourceFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
