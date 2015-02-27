package com.newsoft.common.dictionary.dao;

import java.util.List;

import com.newsoft.common.dictionary.po.DictionaryKind;
import com.newsoft.common.dictionary.po.DictionaryType;
import com.newsoft.common.log.LogParam;
import com.newsoft.common.mybatis.BaseDAO;

/**
 * 字典DAO接口
 * 
 * @author fengmq
 * 
 */
public interface DictionaryDAO extends BaseDAO {

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
	@LogParam(logDes = "添加字典类别", operateModule = "字典管理")
	public void addDictionaryKind(DictionaryKind kind) throws Exception;

	/**
	 * 更新一个字典类别
	 * 
	 * @param kind
	 *            类别对象
	 * @throws Exception
	 */
	@LogParam(logDes = "更新字典类别", operateModule = "字典管理")
	public void updateDictionaryKind(DictionaryKind kind) throws Exception;

	/**
	 * 删除一个字典类别
	 * 
	 * @param kindId
	 * @throws Exception
	 */
	@LogParam(logDes = "根据ID删除字典类别", operateModule = "字典管理")
	public void deleteDictionaryKindById(Integer kindId) throws Exception;

	/**
	 * 新增一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	@LogParam(logDes = "新增字典类型", operateModule = "字典管理")
	public void addDictionaryType(DictionaryType type) throws Exception;

	/**
	 * 更新一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	@LogParam(logDes = "更新字典类型", operateModule = "字典管理")
	public void updateDictionaryType(DictionaryType type) throws Exception;

	/**
	 * 根据类型Id删除一个字典类型
	 * 
	 * @param typeId类型Id
	 * @throws Exception
	 */
	@LogParam(logDes = "根据ID删除字典类型", operateModule = "字典管理")
	public void deleteDictionaryTypeById(Integer typeId) throws Exception;

	/**
	 * 根据类别Id删除字典类型
	 * 
	 * @param kindId类别Id
	 * @throws Exception
	 */
	@LogParam(logDes = "根据字典类别ID删除字典类型", operateModule = "字典管理")
	public void deleteDictionaryTypeByKindId(Integer kindId) throws Exception;

	/**
	 * 根据主键获取DictionaryType，因DictionaryType对象为联合主键
	 * 
	 * @return
	 */
	public DictionaryType getDictionaryTypeByPK(DictionaryType type);

	/**
	 * 获取排序号在当前之前的类型
	 * 
	 * @param type
	 * @return
	 */
	public List<DictionaryType> getPrvDictionaryType(DictionaryType type);

	/**
	 * 获取排序号在当前之后的类型
	 * 
	 * @param type
	 * @return
	 */
	public List<DictionaryType> getNextDictionaryType(DictionaryType type);
}
