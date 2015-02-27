package com.hps.userscore.mgr;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hps.userscore.mgr.Userscoredetail;
import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.utils.JSONTool;


@Controller
@RequestMapping("/userscoredetail")
public class UserscoredetailController extends BaseController {
	@Autowired
	private UserscoredetailService userscoredetailService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/record/userscoredetail/userscoredetail_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/record/userscoredetail/userscoredetail_list";
	}
	
	@RequestMapping("/add_form")
	public String addUserscoredetailFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Userscoredetail userscoredetail = new Userscoredetail();
		map.put("userscoredetail", userscoredetail);
		map.put("operation", "add");
		
		return "/record/userscoredetail/userscoredetail_form"; 
	}

	@RequestMapping("/edit_form")
	public String editUserscoredetailFormPage(Map<String, Object> map, HttpServletRequest request, String userscoredetailId) {
		map.put("operation", "edit");
		Userscoredetail userscoredetail = userscoredetailService.getUserscoredetailById(userscoredetailId);		
		if (userscoredetail != null) {
			map.put("userscoredetail", userscoredetail);
		}
		return "/record/userscoredetail/userscoredetail_form";
	}

	@RequestMapping("/view_form")
	public String viewUserscoredetailFormPage(Map<String, Object> map,HttpServletRequest request, String userscoredetailId) {
		map.put("operation", "view");
		Userscoredetail userscoredetail = userscoredetailService.getUserscoredetailById(userscoredetailId);		
		if (userscoredetail != null) {
			map.put("userscoredetail", userscoredetail);
		}
		return "/record/userscoredetail/userscoredetail_form";
	}
	
	@RequestMapping("/delete")
	public void deleteUserscoredetail(PrintWriter writer, String userscoredetailIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userscoredetailIds == null || userscoredetailIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = userscoredetailIds.split(",");
		try {
			boolean result = userscoredetailService.deleteUserscoredetailById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadUserscoredetailListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, UserscoredetailDAO.class.getName(), "getUserscoredetailList",
				"countUserscoredetail", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitUserscoredetailForm(PrintWriter writer, Userscoredetail userscoredetail, String operation) {
		try {
			if ("add".equals(operation)) {
				userscoredetailService.addUserscoredetail (userscoredetail);
			} else {
				userscoredetailService.updateUserscoredetail (userscoredetail);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
