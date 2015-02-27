package  ${params.packageName};

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${params.packageName}.${classNamePrefix};

@Service
public class ${classNamePrefix}ServiceImpl implements ${classNamePrefix}Service {
	private Logger logger = LoggerFactory.getLogger(${classNamePrefix}ServiceImpl.class);
	
	@Autowired
	private ${classNamePrefix}DAO ${params.moduleName}DAO;
	
	public List<${classNamePrefix}> get${classNamePrefix}List(){
		try{
		     return ${params.moduleName}DAO.get${classNamePrefix}List();
		}
		catch(Exception e){
			logger.error("Error when query ${classNamePrefix} list data",e);
		}
		return null;
	}

	public ${classNamePrefix} get${classNamePrefix}ById(String id){
	   try{
		 return ${params.moduleName}DAO.get${classNamePrefix}ById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query ${classNamePrefix} by id: "+id,e);		
		}
		return null;
	}

	public boolean add${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception {
		return ${params.moduleName}DAO.add${classNamePrefix}(${params.moduleName}) > 0;
	}
	
	public boolean update${classNamePrefix}(${classNamePrefix} ${params.moduleName}) throws Exception {
		 return ${params.moduleName}DAO.update${classNamePrefix}(${params.moduleName}) > 0;	    
	}

	public boolean delete${classNamePrefix}ById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = ${params.moduleName}DAO.delete${classNamePrefix}ById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}	
}
