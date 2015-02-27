package  ${params.packageName};

import java.util.List;

import com.newsoft.common.log.annotation.LogParam;
import com.newsoft.common.mybatis.BaseDAO;
import ${params.packageName}.${classNamePrefix};


public interface ${classNamePrefix}DAO extends BaseDAO {
	public List<${classNamePrefix}> get${classNamePrefix}List();

	public ${classNamePrefix} get${classNamePrefix}ById(String id);

	@LogParam(logDes = "Add ${classNamePrefix}", operateModule = "${classNamePrefix} Management")
	public int add${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception;
	
	@LogParam(logDes = "Update ${classNamePrefix}", operateModule = "${classNamePrefix} Management")
	public int update${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception;

	@LogParam(logDes = "Delete ${classNamePrefix}", operateModule = "${classNamePrefix} Management")
	public int delete${classNamePrefix}ById(String id) throws Exception;	
}
