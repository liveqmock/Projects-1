package $params.packageName;

import com.newsoft.common.mybatis.Table;

@Table(name="${params.tableName}", pk="${params.moduleName}_id")
public class ${classNamePrefix} implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** default constructor */
	public ${classNamePrefix}() {
	}

	#foreach ($fld in $params.fieldList)
   private $fld.fieldType $fld.fieldName;
	#end
	
	#foreach ($fld in $params.fieldList)	 
	public ${fld.fieldType} ${fld.gotterName} () {
		return $fld.fieldName;
	}

	public void  ${fld.setterName}(${fld.fieldType} ${fld.fieldName}) {
		this.$fld.fieldName = $fld.fieldName;
	}
	
	#end
}