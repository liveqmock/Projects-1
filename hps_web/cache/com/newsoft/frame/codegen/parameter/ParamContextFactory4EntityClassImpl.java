package com.newsoft.frame.codegen.parameter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newsoft.frame.codegen.Configuration;
import com.newsoft.frame.codegen.FileHelper;

/**
 * Factory to create parameter context by the PO class.
 * 
 * @author guohb
 * 
 */
public class ParamContextFactory4EntityClassImpl extends AbstractParamContextFactoryImpl {

	@Override
	protected void fulfillContextOnDemand(ParamContext context, Configuration configuration) {
		String packageNames = configuration.getPackages();
		String entityClassNames = configuration.getEntityClasses();
		if (packageNames == null || StringUtils.isBlank(packageNames) || entityClassNames == null
				|| StringUtils.isBlank(entityClassNames)) {
			return;
		}

		String[] packageNameArray = packageNames.split(",");
		int count = packageNameArray.length;
		String[] entityNamesArray = entityClassNames.split(",");

		if (entityNamesArray.length != count) {
			logger.error("Entity class is not with the same size as the packages, please check the conf.properties");
			return;
		}

		List<ParamBundle> paramBundles = new ArrayList<ParamBundle>();
		for (int i = 0; i < count; i++) {
			ParamBundle paramBundle = new ParamBundle();
			paramBundle.setClassName(entityNamesArray[i]);
			paramBundle.setPackageName(packageNameArray[i]);
			paramBundle.setFieldList(extractFieldsByReflection(entityNamesArray[i]));
			String moduleName = FileHelper.getLastPackageName(paramBundle.getClassName());
			paramBundle.setModuleName(moduleName);
			paramBundle.setTableName(moduleName);

			paramBundles.add(paramBundle);
		}

		context.setParamBundles(paramBundles);

	}

	private List<Field> extractFieldsByReflection(String entityClass) {
		if (StringUtils.isBlank(entityClass)) {
			return null;
		}

		List<Field> fieldList = new ArrayList<Field>();
		try {
			Class<?> clazz = Class.forName(entityClass);
			java.lang.reflect.Field[] fieldArray = clazz.getDeclaredFields();

			if (fieldArray == null || fieldArray.length == 0) {
				logger.warn("Can't find declared fields by reflection for entityClass: " + entityClass);
				return null;
			}

			for (java.lang.reflect.Field fld : fieldArray) {
				if ("serialVersionUID".equalsIgnoreCase(fld.getName())) {
					// ignore this field
					continue;
				}

				Field field = new Field();
				field.setFieldName(fld.getName());
				field.setFieldType(fld.getType().getName());
				field.setColumnName(ParamHelper.getColumnName(field.getFieldName()));

				fieldList.add(field);
			}

		} catch (ClassNotFoundException e) {
			logger.error("Entity class not found with name: " + entityClass, e);
		}

		return fieldList;
	}

}
