package com.newsoft.sysmanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.attach.dao.FrameAttachDAO;
import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.spring.BaseController;
import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.RoleMgrService;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.OperateImage;
import com.newsoft.utils.PasswordEncoderUtil;
import com.newsoft.utils.StringTools;

/**
 * 用户管理模块
 * 
 * @author fengmq
 * 
 */
@Controller
@RequestMapping("/sys/userMgr")
public class UserMgrController extends BaseController {
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
		return "/frame/system/userMgr/main";
	}

	/**
	 * 视图列表分页显示用户页面
	 * 
	 * @return
	 */
	@RequestMapping("/userListPage")
	public String userListPage() {
		return "/frame/system/userMgr/userList";
	}

	/**
	 * 分页获取用户列表
	 * 
	 * @param writer
	 *            输出流
	 * @param defaultOrgId
	 *            用户当前登录公司
	 * @param orgId
	 *            组织机构ID
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示行数
	 */
	@RequestMapping("/loadUserListData")
	public void loadUserListData(HttpServletRequest request, PrintWriter writer) {
		// 将page对象转换成grid所需的json格式数据
		String json = userMgrService.loadUserListData(request);
		writer.write(json);
	}

	/**
	 * 删除用户
	 * 
	 * @param writer
	 *            输出流
	 * @param userIds
	 *            用户Id
	 * @param orgId
	 *            当前选中的组织机构Id
	 * @throws IOException
	 */
	@RequestMapping("/deleteUser")
	public void deleteUser(PrintWriter writer, String userIds, String orgId) {
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

	/**
	 * 添加用户表单页面
	 * 
	 * @return
	 */
	@RequestMapping("/addUserFormPage")
	public String addUserFormPage(Map<String, Object> map) {
		UserVo user = new UserVo();
		user.setSex(true);
		user.setUserState(true);
		user.setDefaultOrgId("");
		user.setUserOrgIds("");
		user.setUserOrgNames("");
		map.put("user", user);
		map.put("operation", "add");

		return "/frame/system/userMgr/userForm";
	}

	/**
	 * 编辑用户表单页面
	 * 
	 * @param map
	 * @param defaultOrgId
	 *            当前登录用户所在组织
	 * @param userId
	 *            编辑的用户Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/editUserFormPage")
	public String editUserFormPage(Map<String, Object> map, String defaultOrgId, String userId, HttpServletRequest request) {
		UserVo user = userMgrService.getUserVoById(userId, defaultOrgId);
		user.setPwd(User.DISPLAYPWD);
		map.put("user", user);
		map.put("operation", "edit");
		return "/frame/system/userMgr/userForm";
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
		return "/frame/system/userMgr/userForm";
	}

	/**
	 * 组织机构表单提交处理
	 * 
	 * @param writer
	 *            输出流
	 * @param userVo
	 *            表单信息
	 */
	@RequestMapping("/submitUserForm")
	public void submitUserForm(PrintWriter writer, UserVo userVo) {
		try {
			// 如果用户ID不存在，说明是添加用户
			if (StringTools.isEmpty(userVo.getUserId())) {
				userMgrService.addUser(userVo);
			} else {
				// 更新用户
				userMgrService.updateUser(userVo);
			}
			writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}

	/**
	 * 验证用户账号是否有效
	 * 
	 * @param writer输出流
	 * @param userId
	 *            用户Id
	 * @param account
	 *            用户账号
	 */
	@RequestMapping("/validateAccount")
	public void validateAccount(PrintWriter writer, String userId, String account) {
		// text:有三种状态：1、不重复；0、没变；-1、重复
		User accountUser = userMgrService.getUserByAccount(account);
		// 验证结果
		Map<String, String> resultMap = new HashMap<String, String>();
		// 默认值
		resultMap.put("success", "0");
		resultMap.put("text", "此账号已经存在");
		if (accountUser == null) {
			resultMap.put("success", "1");
			resultMap.put("text", "账号可用");
		} else if (!StringTools.isEmpty(userId)) {
			User idUser = userMgrService.getUserById(userId);
			if (idUser.getUserId().equals(accountUser.getUserId())) {
				resultMap.put("success", "1");
				resultMap.put("text", "账号未变更");
			}
		}
		writer.write(JSONTool.toJson(resultMap));
	}

	/**
	 * 选择用户对话框使用
	 * 
	 * 根据组织机构Id加载用户
	 * 
	 * @param writer
	 * @param orgUnitId
	 *            组织机构Id
	 */
	@RequestMapping("/loadUserListJson")
	public void loadUserByOrgUnitId(PrintWriter writer, String orgUnitId) {
		List<UserVo> userList = userMgrService.getUserListByOrgId(orgUnitId);
		writer.write(JSONTool.toJson(userList));
	}

	/**
	 * 搜索用户、组织机构、角色
	 */
	@RequestMapping("/searchUserOrgRole")
	public void search(PrintWriter writer, String type, String orgId, String name) {
		String keyWord = "%" + name + "%";
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if ("user".equals(type)) {
			List<User> userList = userMgrService.searchUserByUserNameKeyWord(orgId, keyWord);
			for (User user : userList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("value", user.getUserId());
				map.put("text", user.getUserName());
				list.add(map);
			}
		} else if ("role".equals(type)) {
			List<Role> roleList = roleMgrService.searchRoleByNameKeyWord(orgId, keyWord);
			for (Role role : roleList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("value", role.getRoleId());
				map.put("text", role.getRoleName());
				list.add(map);
			}
		}
		String json = JSONArray.fromObject(list).toString();
		writer.write(json);
	}

	@RequestMapping("/getUserById")
	public void getUserById(PrintWriter writer, Map<String, Object> map, String userId) {
		// 根据用户ID获取用户信息
		if (StringTools.isEmpty(userId)) {
			map.put("user", SessionUtil.getSessionUser());
		} else {
			String[] userIdArray = userId.split(";");
			String userName = "";
			for (String uId : userIdArray) {
				User user = userMgrService.getUserById(uId);
				if ("".equals(userName)) {
					userName = user.getUserName();
				} else {
					userName = userName + "；" + user.getUserName();
				}
			}
			User userResult = new User();
			userResult.setUserName(userName);
			map.put("user", userResult);
		}

		writer.write(JSONTool.toJson(map));
	}

	@RequestMapping("/userPasswordPage")
	public String userPasswordPage() {
		return "frame/system/userMgr/pwdForm";
	}

	/**
	 * 修改用户密码
	 * 
	 * @param writer
	 * @param oldPwd
	 * @param newPwd
	 */
	@RequestMapping("/repasswordSubmit")
	public void repasswordSubmit(PrintWriter writer, String oldPwd, String newPwd) {
		String enOldPwd = PasswordEncoderUtil.encode(oldPwd);
		String enNewPwd = PasswordEncoderUtil.encode(newPwd);
		try {
			User user = SessionUtil.getSessionUser();
			if (!user.getPwd().equals(enOldPwd)) {
				writer.print(-1);
				return;
			}
			user.setPwd(enNewPwd);
			userMgrService.updateUser(user);
			writer.print(1);
		} catch (Exception e) {
			writer.print(0);
		}
	}

	/**
	 * 确定剪裁
	 */
	@RequestMapping("/cutImage")
	public void cutImage(PrintWriter writer, HttpServletRequest request, String fileId, int x, int y, int w, int h) {
		Attach attach = attachDAO.getAttachById(fileId);
		String savePath = attach.getFileDir() + attach.getFileName();
		OperateImage operateImage = new OperateImage(x, y, w, h);
		operateImage.setSrcpath(savePath);
		operateImage.setSubpath(attach.getFileDir() + attach.getFileName());
		String fileName = attach.getFileName();
		try {
			operateImage.cut(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
	}

}
