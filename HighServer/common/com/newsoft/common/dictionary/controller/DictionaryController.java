package com.newsoft.common.dictionary.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.dictionary.dao.DictionaryDAO;
import com.newsoft.common.dictionary.po.DictionaryKind;
import com.newsoft.common.dictionary.po.DictionaryType;
import com.newsoft.common.dictionary.service.DictionaryService;
import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.utils.JSONTool;

/**
 * 
 * @author mengxw
 *
 */
@Controller
@RequestMapping("/dictionaryMgr")
public class DictionaryController extends BaseController{
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PageService pageService;

	/**
	 * 模块的主地址解析
	 * 
	 * @return
	 */
	@RequestMapping
	public String mainView() {
		return "/frame/system/dictionaryMgr/main";
	}

	/**
	 * 视图列表分页显示字典类别
	 * 
	 * @return
	 */
	@RequestMapping("/kindTree")
	public String dictionaryKindTree() {
		return "/frame/system/dictionaryMgr/kindTree";
	}

	/**
	 * 类型列表显示
	 * 
	 * @return
	 */
	@RequestMapping("/typeListPage")
	public String dictionaryTypeListPage() {
		return "/frame/system/dictionaryMgr/typeList";
	}

	/**
	 * 加载字典类别树
	 * 
	 * @return
	 */
	@RequestMapping("/loadKindTree")
	public void loadKindTree(PrintWriter writer, String defaultOrgId, Integer id) {
		if (null == id) {
			id = 0;
		}
		List<DictionaryKind> list = dictionaryService
				.getDictionaryKindByParentId(id);
		// 封装成树所需的格式
		List<Map<String,Object>> treeList = new ArrayList<Map<String,Object>>();
		for (DictionaryKind kind : list) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", kind.getKindId());
			map.put("title", kind.getKindName());
			map.put("key", kind.getKindId());
			map.put("kindDes", kind.getKindDes());
			map.put("parentId", kind.getParentId());
			map.put("isLazy", hasChildren(kind.getKindId()));
			treeList.add(map);
		}
		writer.write(JSONTool.toJson(treeList));
	}

	/**
	 * 判断是否还有子类别
	 * 
	 * @param parentId
	 * @return
	 */
	private boolean hasChildren(Integer parentId) {
		List<DictionaryKind> list = dictionaryService
				.getDictionaryKindByParentId(parentId);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 加载字典类型列表数据
	 * 
	 * @param writer
	 * @param kindId
	 *            类别Id
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示行数
	 * @param defaultOrgId
	 *            当前用户登录公司
	 */
	@RequestMapping("/loadTypeListData")
	public void loadTypeListData(HttpServletRequest request,
			PrintWriter writer, String kindId, int page, int rows,
			String defaultOrgId) {
		String json = pageService.gridPageQuery(request, DictionaryDAO.class
				.getName(), "getDictionaryTypeByKindId",
				"countDictionaryTypeByKindId", kindId);
		writer.write(json);
	}

	/**
	 * 添加或更新一个子类别
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitDictionaryKind")
	public void submitDictionaryKind(PrintWriter writer, DictionaryKind kind) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (kind.getKindId() == null) {
				dictionaryService.addDictionaryKind(kind);
			} else {
				dictionaryService.updateDictionaryKind(kind);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
		}
		// 输出操作结果
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 删除类别
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDictionaryKind")
	public void deleteDictionaryKind(PrintWriter writer, Integer kindId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.deleteDictionaryKindById(kindId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
		}
		// 输出操作结果
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 添加或更新一个类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitDictionaryType")
	public void submitDictionaryType(PrintWriter writer, DictionaryType type,
			String operatorType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if ("add".equals(operatorType)) {
				if (dictionaryService.getDictionaryTypeByPK(type) != null) {
					map.put("success", false);
					map.put("msg", "添加的类型值已经存在,请修正");
				} else {
					dictionaryService.addDictionaryType(type);
					map.put("success", true);
				}
			} else {
				dictionaryService.updateDictionaryType(type);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.getStackTrace();
			map.put("success", false);
			map.put("msg", "提交失败,请检查输入项是否正确");
		}// 输出操作结果
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 删除类别
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDictionaryType")
	public void deleteDictionaryType(PrintWriter writer, String typeIds)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.deleteDictionaryTypeById(typeIds);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
		}
		// 输出操作结果
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 排序，上移（orderType=up）,下移（orderType=down）
	 * 
	 * @param typeId
	 * @param kindId
	 * @param orderType
	 *            up or down
	 */
	@RequestMapping("/sortIndex")
	public void sortIndex(String typeId, String kindId, String orderType,
			PrintWriter writer) {
		// 操作结果信息
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 执行排序，上移或下移
			boolean result = dictionaryService.sortIndex(typeId, kindId,
					orderType);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
		}
		// 输出操作结果
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 根据字典类别父Id获取JSON格式子节点
	 * 
	 * @param writer
	 * @param parentId
	 */
	@RequestMapping("/getDictionaryKindJsonByParentId")
	public void getDictionaryKindJsonByParentId(PrintWriter writer,
			Integer parentId) {
		List<DictionaryKind> list = dictionaryService
				.getDictionaryKindByParentId(parentId);
		writer.write(JSONTool.toJson(list));
	}

	/**
	 * 根据字典类别Id获取JSON格式字典类型
	 * 
	 * @param writer
	 * @param kindId
	 */
	@RequestMapping("/getDictionaryTypeJsonByKindId")
	public void getDictionaryTypeJsonByKindId(PrintWriter writer, Integer kindId) {
		List<DictionaryType> list = dictionaryService
				.getDictionaryTypeByKindId(kindId);
		writer.write(JSONTool.toJson(list));
	}

}
