package  ${params.packageName};

import java.util.List;
import ${params.packageName}.${classNamePrefix};

public interface ${classNamePrefix}Service {

	public List<${classNamePrefix}> get${classNamePrefix}List();

	public ${classNamePrefix} get${classNamePrefix}ById(String id);

	public boolean add${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception;
	
	public boolean update${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception;

	public boolean delete${classNamePrefix}ById(String[] idArray) throws Exception;
}
