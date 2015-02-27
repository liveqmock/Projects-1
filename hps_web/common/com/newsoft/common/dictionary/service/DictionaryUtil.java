/**
 * 
 */
package com.newsoft.common.dictionary.service;

import java.util.List;

import com.newsoft.common.dictionary.po.DictionaryKind;
import com.newsoft.common.dictionary.po.DictionaryType;
import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.utils.JSONTool;

/**
 * 字典工具类
 * 
 * @author fengmq
 * 
 */
public class DictionaryUtil {
	/**
	 * 通过类别Id获取字典类型
	 * 
	 * @param kindId
	 * @return 返回字典类型列表
	 */
	public static List<DictionaryType> getDictionaryType(Integer kindId) {
		DictionaryService dictionaryService = (DictionaryService) SpringBeanManager
				.getBean("DictionaryServiceImpl");
		return dictionaryService.getDictionaryTypeByKindId(kindId);
	}

	/**
	 * 通过类别Id获取字典类型
	 * 
	 * @param kindId
	 * @return 返回JSON格式字典类型
	 */
	public static String getDictionaryTypeJSON(Integer kindId) {
		List<DictionaryType> list = getDictionaryType(kindId);
		return JSONTool.toJson(list);
	}

	/**
	 * 根据类型主键获取类型对象(双主键)
	 * 
	 * @param kindId
	 *            类别Id
	 * @param typeId
	 *            类型Id
	 * @return
	 */
	public static DictionaryType getDictionaryTypeByPK(Integer kindId,
			Integer typeId) {
		DictionaryService dictionaryService = (DictionaryService) SpringBeanManager
				.getBean("DictionaryServiceImpl");
		DictionaryType type = new DictionaryType();
		type.setKindId(kindId);
		type.setTypeId(typeId);
		return dictionaryService.getDictionaryTypeByPK(type);
	}

	/**
	 * 通过父节点ID获取所有子类别列表
	 * 
	 * @param parentId
	 *            父节点Id
	 * @return
	 */
	public static List<DictionaryKind> getDictionaryKindByParentId(
			Integer parentId) {
		DictionaryService dictionaryService = (DictionaryService) SpringBeanManager
				.getBean("DictionaryServiceImpl");
		return dictionaryService.getDictionaryKindByParentId(parentId);
	}

	/**
	 * 新增一个字典类型
	 * 
	 * @param type
	 *            字典类型对象
	 * @return 新增成功返回true，新增失败返回false
	 */
	public static boolean addDictionaryType(DictionaryType type) {
		DictionaryService dictionaryService = (DictionaryService) SpringBeanManager
				.getBean("DictionaryServiceImpl");
		try {
			dictionaryService.addDictionaryType(type);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 新增一个字典子类别
	 * 
	 * @param kind
	 *            字典类别对象
	 * @return 新增成功返回true，新增失败返回false
	 */
	public static boolean addSubDictionaryKind(DictionaryKind kind) {
		DictionaryService dictionaryService = (DictionaryService) SpringBeanManager
				.getBean("DictionaryServiceImpl");
		if (kind.getParentId() == 0) {
			System.out.println("新增子类别失败，不允许新增根类别！");
			return false;
		}
		try {
			dictionaryService.addDictionaryKind(kind);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
