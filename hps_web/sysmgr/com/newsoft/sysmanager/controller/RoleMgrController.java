package com.newsoft.sysmanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.sysmanager.bean.ModelNameBean;
import com.newsoft.sysmanager.bean.OperatorBean;
import com.newsoft.sysmanager.bean.SystemNameBean;
import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.RoleOperator;
import com.newsoft.sysmanager.service.OperatorService;
import com.newsoft.sysmanager.service.RoleMgrService;
import com.newsoft.sysmanager.service.RoleOperatorService;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.StringTools;

/**
 * 角色管理模块
 * 
 * @author fengmq
 * 
 */
@Controller
@RequestMapping("/sys/roleMgr")
public class RoleMgrController extends BaseController {

	@Autowired
	private RoleMgrService roleMgrService;

	@Autowired
	private RoleOperatorService roleOperatorService;

	@Autowired
	private PageService pageService;

	@Autowired
	private OperatorService operatorService;

	@Autowired
	private CacheFacade cacheFacade;

	/**
	 * 模块的主地址解析
	 * 
	 * @return
	 */
	@RequestMapping
	public String mainView() {
		return "/frame/system/roleMgr/main";
	}

	/**
	 * 视图列表分页显示角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/roleListPage")
	public String roleListPage() {
		return "/frame/system/roleMgr/roleList";
	}

	/**
	 * 分页获取角色列表
	 * 
	 * @param writer
	 *            输出流
	 * @param orgId
	 *            组织机构ID
	 * @param defaultOrgId
	 *            当前登录组织机构Id
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示行数
	 */
	@RequestMapping("/loadRoleListData")
	public void loadRoleListData(HttpServletRequest request, PrintWriter writer, String orgId, String defaultOrgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		String json = pageService.gridPageQuery(request, RoleMgrDAO.class.getName(), "getRoleListByOrgId",
				"countRoleByOrgId", params);
		writer.write(json);
	}

	/**
	 * 删除角色
	 * 
	 * @param writer
	 *            输出流
	 * @param roleIds
	 *            角色Id
	 * @throws IOException
	 */
	@RequestMapping("/deleteRole")
	public void deleteRole(PrintWriter writer, String roleIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringTools.isEmpty(roleIds)) {
			map.put("success", false);
			map.put("msg", "参数不正确！");
		} else {
			String[] idArr = roleIds.split(",");
			try {
				boolean result = roleMgrService.deleteRoles(idArr);
				map.put("success", result);
				map.put("msg", "选择的角色包含有系统角色");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", "删除失败");
			}
		}
		writer.write(JSONTool.toJson(map));
	}

	/**
	 * 添加角色表单页面
	 * 
	 * @return
	 */
	@RequestMapping("/addRoleFormPage")
	public String addRoleFormPage(Map<String, Object> map) {
		RoleVo role = new RoleVo();
		role.setOrgId("");
		role.setRoleType(0);// 用户自定义角色
		role.setOrgName("");
		map.put("role", role);
		map.put("operation", "add");
		return "/frame/system/roleMgr/roleForm";
	}

	/**
	 * 编辑角色表单页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/editRoleFormPage")
	public String editRoleFormPage(String roleId, Map<String, Object> map, HttpServletRequest request) {
		RoleVo role = roleMgrService.getRoleVoByRoleId(roleId);
		map.put("role", role);
		map.put("operation", "edit");
		return "/frame/system/roleMgr/roleForm";
	}

	/**
	 * 查看角色表单页面
	 * 
	 * @param map
	 * @param roleId
	 *            角色Id
	 * @return
	 */
	@RequestMapping("/viewRoleFormPage")
	public String viewOrgFormPage(Map<String, Object> map, String roleId) {
		// 根据角色ID获取角色信息
		RoleVo role = roleMgrService.getRoleVoByRoleId(roleId);
		map.put("role", role);
		map.put("operation", "view");
		map.put("assigedOperations", getAssignedOperations(roleId));
		return "/frame/system/roleMgr/roleForm";
	}

	private String getAssignedOperations(String roleId) {
		List<SystemNameBean> systemNameBeanList = operatorService.getSystemNameBeanList();
		List<OperatorBean> operateList = new ArrayList<OperatorBean>();
		for (SystemNameBean systemNameBean : systemNameBeanList) {
			for (ModelNameBean modelNameBean : systemNameBean.getModelNameBeanList()) {
				for (OperatorBean operatorBean : modelNameBean.getOperatorBeanList()) {
					operateList.add(operatorBean);
				}
			}
		}

		// 遍历该角色已拥有的权限
		List<RoleOperator> roleOperatorList = roleOperatorService.getRoleOperatorByRoleId(roleId);
		StringBuilder s = new StringBuilder();
		for (RoleOperator roleOperator : roleOperatorList) {
			for (OperatorBean operatorBean : operateList) {
				if (roleOperator.getOperatorId().equals(operatorBean.getOperatorId())) {
					if (s.length() > 0) {
						s.append(";");
					}
					s.append(operatorBean.getOperatorName());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Found assigned operations: " + s);
		}
		return s.toString();
	}

	/**
	 * 组织机构表单提交处理
	 * 
	 * @param writer
	 *            输出流
	 * @param role
	 *            表单信息
	 */
	@RequestMapping("/submitRoleForm")
	public void submitRoleForm(PrintWriter writer, RoleVo role) {
		try {
			// 如果角色ID不存在，说明是添加角色
			if (StringTools.isEmpty(role.getRoleId())) {
				roleMgrService.addRole(role);
			} else {
				// 更新角色
				roleMgrService.updateRole(role);
			}
			writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}

	/**
	 * 加载角色
	 * 
	 * @param writer
	 * @param orgUnitId
	 *            组织Id
	 */
	@RequestMapping("/loadRolesByOrgUnitId")
	public void loadRolesByOrgUnitId(PrintWriter writer, String orgUnitId) {
		List<Role> roleList = roleMgrService.getRoleListByOrgId(orgUnitId);
		writer.write(JSONTool.toJson(roleList));
	}

	/** *******************************角色授权****************************** */
	/**
	 * 角色授权页面
	 * 
	 * @param writer
	 * @param roleId
	 *            角色Id
	 */
	@RequestMapping("/authorizePage")
	public String authorizePage(HttpServletRequest request, String roleId) {
		List<SystemNameBean> systemNameBeanList = operatorService.getSystemNameBeanList();
		// 角色已拥有的权限
		List<RoleOperator> roleOperatorList = roleOperatorService.getRoleOperatorByRoleId(roleId);
		// 获取用户有权限的操作
		List<RoleOperator> userRoleOperatorList = roleOperatorService.getRoleOperatorByUserId(SessionUtil
				.getSessionUserId());
		request.setAttribute("roleOperatorList", roleOperatorList);
		request.setAttribute("userRoleOperatorList", userRoleOperatorList);
		// 获取配置文件中的操作权限列表
		request.setAttribute("systemNameBeanList", systemNameBeanList);
		return "frame/system/roleMgr/authorize";
	}

	/**
	 * 接收角色授权的请求
	 * 
	 * @param writer
	 * @param roleId
	 *            角色Id
	 * @param operatorRes
	 *            操作资源Id
	 */
	@RequestMapping("/saveRoleAuthrize")
	public void saveRoleAuthrize(HttpServletResponse response, PrintWriter writer, String roleId, String[] operatorRes) {
		// 保存更改后的权限操作内容
		try {
			// 删除该角色下的所有的资源
			roleOperatorService.deleteRoleOperatorByRoleId(roleId);
			// 保存角色授权
			roleOperatorService.insertRoleOperator(roleId, operatorRes);

			// 刷新角色对应的操作缓存
			cacheFacade.discardRoleOperations(roleId);
			// 刷新URL和对应能访问的角色信息缓存
			cacheFacade.discardUrlMappingRoles();
		} catch (Exception e) {
			logger.error("error when update role's operations", e);
		}

		// 提交成功关闭窗口,并刷新
		try {
			response.setContentType("text/html");
			response.getWriter()
					.write("<script language='javascript'>try{window.opener.RoleManager.callBack();window.close();}catch(e){window.close();}</script>");
		} catch (IOException e) {
			logger.error("error when send response to client", e);
		}
	}
	
	@RequestMapping("/allrole")
	public void loadAllRole(PrintWriter writer) {
		List<Role> list = roleMgrService.getRoleListByOrgId("");
		writer.write(JSONTool.toJson(list).toString());
	}
}
