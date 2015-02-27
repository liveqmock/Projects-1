/**
 * 
 */
package com.newsoft.common.dictionary.service;

import java.util.List;

import com.newsoft.common.dictionary.po.DictionaryKind;
import com.newsoft.common.dictionary.po.DictionaryType;

/**
 * @author fengmq
 * 
 */
public interface DictionaryService {
	/**
	 * 根据类别Id获取字典类型列表
	 * 
	 * @param kindId
	 *            类别Id
	 * @return 类别列表
	 */
	public List<DictionaryType> getDictionaryTypeByKindId(Integer kindId);

	/**
	 * 根据父类别Id获取字典类别列表
	 * 
	 * @param parentId
	 *            父类别Id
	 * @return 子类别列表
	 */
	public List<DictionaryKind> getDictionaryKindByParentId(Integer parentId);

	/**
	 * 新增一个字典类别
	 * 
	 * @param kind
	 *            类别对象
	 * @throws Exception
	 */
	public void addDictionaryKind(DictionaryKind kind) throws Exception;

	/**
	 * 更新一个字典类别
	 * 
	 * @param kind
	 *            类别对象
	 * @throws Exception
	 */
	public void updateDictionaryKind(DictionaryKind kind) throws Exception;

	/**
	 * 删除字典类别
	 * 
	 * @param kindId
	 * @throws Exception
	 */
	public void deleteDictionaryKindById(Integer kindId) throws Exception;

	/**
	 * 新增一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	public void addDictionaryType(DictionaryType type) throws Exception;

	/**
	 * 根据主键获取DictionaryType，因DictionaryType对象为联合主键
	 * 
	 * @return
	 */
	public DictionaryType getDictionaryTypeByPK(DictionaryType type);

	/**
	 * 更新一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	public void updateDictionaryType(DictionaryType type) throws Exception;

	/**
	 * 删除字典类型
	 * 
	 * @param typeIds类别Ids
	 * @throws Exception
	 */
	public void deleteDictionaryTypeById(String typeIds) throws Exception;

	/**
	 * 排序
	 * 
	 * @param typeId
	 *            类型ID
	 * @param kindId
	 *            类别Id
	 * @param orderType
	 *            排序类型
	 * @return
	 * @throws Exception
	 */
	public boolean sortIndex(String typeId, String kindId, String orderType)
			throws Exception;

}
