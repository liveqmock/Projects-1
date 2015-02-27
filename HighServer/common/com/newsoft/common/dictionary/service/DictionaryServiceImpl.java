/**
 * 
 */
package com.newsoft.common.dictionary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.common.dictionary.dao.DictionaryDAO;
import com.newsoft.common.dictionary.po.DictionaryKind;
import com.newsoft.common.dictionary.po.DictionaryType;

/**
 * @author fengmq
 * 
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryDAO dictionaryDAO;

	/**
	 * 根据类别Id获取字典类型列表
	 * 
	 * @param kindId
	 *            类别Id
	 * @return 类别列表
	 */
	public List<DictionaryType> getDictionaryTypeByKindId(Integer kindId) {
		return dictionaryDAO.getDictionaryTypeByKindId(kindId);
	}

	/**
	 * 根据父类别Id获取字典类别列表
	 * 
	 * @param parentId
	 *            父类别Id
	 * @return 子类别列表
	 */
	public List<DictionaryKind> getDictionaryKindByParentId(Integer parentId) {
		return dictionaryDAO.getDictionaryKindByParentId(parentId);
	}

	/**
	 * 新增一个字典类别
	 * 
	 * @param kind
	 *            类别对象
	 * @throws Exception
	 */
	public void addDictionaryKind(DictionaryKind kind) throws Exception {
		dictionaryDAO.addDictionaryKind(kind);
	}

	/**
	 * 更新一个字典类别
	 * 
	 * @param kind
	 *            类别对象
	 * @throws Exception
	 */
	public void updateDictionaryKind(DictionaryKind kind) throws Exception {
		dictionaryDAO.updateDictionaryKind(kind);
	}

	/**
	 * 删除字典类别
	 * 
	 * @param kindId
	 *            类型Id
	 * @throws Exception
	 */
	public void deleteDictionaryKindById(Integer kindId) throws Exception {
		// 获取所有子类别
		List<DictionaryKind> kindList = getSubDictionaryKind(kindId,
				new ArrayList<DictionaryKind>());
		// 删除所遇子类别以及响应的类型
		for (DictionaryKind kind : kindList) {
			dictionaryDAO.deleteDictionaryTypeByKindId(kind.getKindId());
			dictionaryDAO.deleteDictionaryKindById(kind.getKindId());
		}
		// 当前类别和该类别下的类型
		dictionaryDAO.deleteDictionaryTypeByKindId(kindId);
		dictionaryDAO.deleteDictionaryKindById(kindId);
	}

	/**
	 * 递归获取待删除的所有子类别
	 * 
	 * @return
	 */
	private List<DictionaryKind> getSubDictionaryKind(Integer parentId,
			List<DictionaryKind> kindList) {
		List<DictionaryKind> list = dictionaryDAO
				.getDictionaryKindByParentId(parentId);
		if (list != null && list.size() > 0) {
			for (DictionaryKind kind : list) {
				getSubDictionaryKind(kind.getKindId(), kindList);
			}
			kindList.addAll(list);
		}
		return kindList;
	}

	/**
	 * 新增一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	public void addDictionaryType(DictionaryType type) throws Exception {
		dictionaryDAO.addDictionaryType(type);
	}

	/**
	 * 更新一个字典类型
	 * 
	 * @param type
	 *            类型对象
	 * @throws Exception
	 */
	public void updateDictionaryType(DictionaryType type) throws Exception {
		dictionaryDAO.updateDictionaryType(type);
	}

	/**
	 * 删除字典类型
	 * 
	 * @param kindId类别Id
	 * @throws Exception
	 */
	public void deleteDictionaryTypeById(String typeIds) throws Exception {
		String[] ids = typeIds.split(",");
		for (String typeId : ids) {
			dictionaryDAO.deleteDictionaryTypeById(Integer.parseInt(typeId));
		}
	}

	/**
	 * 根据主键获取DictionaryType，因DictionaryType对象为联合主键
	 * 
	 * @return
	 */
	public DictionaryType getDictionaryTypeByPK(DictionaryType type) {
		return dictionaryDAO.getDictionaryTypeByPK(type);
	}

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
			throws Exception {
		DictionaryType type = new DictionaryType();
		type.setKindId(Integer.parseInt(kindId));
		type.setTypeId(Integer.parseInt(typeId));
		// 获取当前类型
		DictionaryType curType = dictionaryDAO.getDictionaryTypeByPK(type);
		// 获取下一个待交换的类型
		DictionaryType dicType = getNextSortDictionaryType(curType, orderType);
		if (dicType == null) {
			return false;
		}
		int tempOrder = curType.getSortIndex();
		curType.setSortIndex(dicType.getSortIndex());
		dicType.setSortIndex(tempOrder);
		dictionaryDAO.updateDictionaryType(curType);
		dictionaryDAO.updateDictionaryType(dicType);
		return true;
	}

	/**
	 * 获取需要交换顺序的字典类型
	 * 
	 * @param type
	 * @param orderType
	 * @return
	 */
	private DictionaryType getNextSortDictionaryType(DictionaryType type,
			String orderType) {
		if (type == null) {
			return null;
		}
		if (!(orderType.equalsIgnoreCase("up") || orderType
				.equalsIgnoreCase("down"))) {
			return null;
		}
		List<DictionaryType> list = null;
		if (orderType.equalsIgnoreCase("up"))
			list = dictionaryDAO.getPrvDictionaryType(type);
		else {
			list = dictionaryDAO.getNextDictionaryType(type);
		}
		if (list == null || list.size() == 0) {
			return null;
		}
		DictionaryType upType = (DictionaryType) list.get(0);
		return upType;
	}

}
