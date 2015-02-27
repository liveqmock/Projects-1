package com.newsoft.frame.codegen.parameter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newsoft.frame.codegen.Configuration;

/**
 * Factory to create parameter context by the database table schema.
 * 
 * @author guohb
 * 
 */
public class ParamContextFactory4JdbcImpl extends AbstractParamContextFactoryImpl {

	@Override
	protected void fulfillContextOnDemand(ParamContext context, Configuration configuration) {
		String packageNames = configuration.getPackages();
		String tableNames = configuration.getTableNames();
		if (packageNames == null || StringUtils.isBlank(packageNames) || tableNames == null
				|| StringUtils.isBlank(tableNames)) {
			return;
		}

		String[] packageNameArray = packageNames.split(",");
		int count = packageNameArray.length;
		String[] tableNamesArray = tableNames.split(",");

		if (tableNamesArray.length != count) {
			logger.error("Table names is not with the same size as the packages, please check the conf.properties");
			return;
		}

		List<ParamBundle> paramBundles = new ArrayList<ParamBundle>();
		DatabaseSchemaReader databaseSchemaReader = new DatabaseSchemaReader();
		for (int i = 0; i < count; i++) {
			ParamBundle paramBundle = new ParamBundle();
			String tableName = tableNamesArray[i];
			paramBundle.setPackageName(packageNameArray[i]);
			paramBundle.setTableName(tableName.toLowerCase());

			String moduleName = ParamHelper.getFieldName(paramBundle.getTableName()).toLowerCase();
			paramBundle.setModuleName(moduleName);

			paramBundle.setFieldList(databaseSchemaReader.parseTable(tableName));

			paramBundles.add(paramBundle);
		}
		context.setParamBundles(paramBundles);

		// Close the connection
		databaseSchemaReader.closeDBConnection();
	}

}
