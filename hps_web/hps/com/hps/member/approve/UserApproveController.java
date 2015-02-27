package com.hps.member.approve;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.attach.dao.FrameAttachDAO;
import com.newsoft.common.spring.BaseController;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.RoleMgrService;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.StringTools;

/**
 * 企业用户审批控制类
 * @author mengxw
 *
 */
@Controller
@RequestMapping("/hps/userMgr")
public class UserApproveController extends BaseController {
	@Autowired
	private UserMgrService userMgrService;
	@Autowired
	private RoleMgrService roleMgrService;
	@Autowired
	private FrameAttachDAO attachDAO;

	/**
	 * 模块的主地址解析
	 * 
	 * @return
	 */
	@RequestMapping
	public String mainView() {
		return "/hps/userapprove/main";
	}

	/**
	 * 视图列表分页显示用户页面
	 * 
	 * @return
	 */
	@RequestMapping("/userListPage")
	public String userListPage() {
		return "/hps/userapprove/userList";
	}

	/**
	 * 
	 * @param request
	 * @param writer
	 */
	@RequestMapping("/loadUserListData")
	public void loadUserListData(HttpServletRequest request, PrintWriter writer) {
		String json = userMgrService.loadApproveUserListData(request);
		writer.write(json);
	}
	
	/**
	 * 查看用户表单页面
	 * 
	 * @param map
	 * @param defaultOrgId
	 *            用户当前登录公司Id
	 * @param userId
	 *            用户Id
	 * @return
	 */
	@RequestMapping("/viewUserFormPage")
	public String viewOrgFormPage(Map<String, Object> map, String defaultOrgId, String userId) {
		// 根据用户ID获取用户信息
		UserVo user = userMgrService.getUserVoById(userId, defaultOrgId);
		user.setPwd(User.DISPLAYPWD);
		map.put("user", user);
		map.put("operation", "view");
		return "/hps/userapprove/userForm";
	}
	
	/**
	 * 审核用户
	 * @param writer
	 * @param userIds
	 * @param orgId
	 */
	@RequestMapping("/approveUser")
	public void approveUser(PrintWriter writer, String userIds, String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] idArr = userIds.split(",");
		try {
			userMgrService.removeUserFromOrgUnit(idArr, orgId);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "后台出错！");
		}
		writer.write(JSONTool.toJson(map));
	}

}
