package com.hps.userexchange.mgr;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.utils.JSONTool;
import com.hps.userexchange.mgr.Userexchnage;


@Controller
@RequestMapping("/userexchnage")
public class UserexchnageController extends BaseController {
	@Autowired
	private UserexchnageService userexchnageService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/record/userexchnage/userexchnage_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/record/userexchnage/userexchnage_list";
	}
	
	@RequestMapping("/add_form")
	public String addUserexchnageFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Userexchnage userexchnage = new Userexchnage();
		map.put("userexchnage", userexchnage);
		map.put("operation", "add");
		
		return "/record/userexchnage/userexchnage_form"; 
	}

	@RequestMapping("/edit_form")
	public String editUserexchnageFormPage(Map<String, Object> map, HttpServletRequest request, String userexchnageId) {
		map.put("operation", "edit");
		Userexchnage userexchnage = userexchnageService.getUserexchnageById(userexchnageId);		
		if (userexchnage != null) {
			map.put("userexchnage", userexchnage);
		}
		return "/record/userexchnage/userexchnage_form";
	}

	@RequestMapping("/view_form")
	public String viewUserexchnageFormPage(Map<String, Object> map,HttpServletRequest request, String userexchnageId) {
		map.put("operation", "view");
		Userexchnage userexchnage = userexchnageService.getUserexchnageById(userexchnageId);		
		if (userexchnage != null) {
			map.put("userexchnage", userexchnage);
		}
		return "/record/userexchnage/userexchnage_form";
	}
	
	@RequestMapping("/delete")
	public void deleteUserexchnage(PrintWriter writer, String userexchnageIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userexchnageIds == null || userexchnageIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = userexchnageIds.split(",");
		try {
			boolean result = userexchnageService.deleteUserexchnageById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadUserexchnageListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, UserexchnageDAO.class.getName(), "getUserexchnageList",
				"countUserexchnage", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitUserexchnageForm(PrintWriter writer, Userexchnage userexchnage, String operation) {
		try {
			if ("add".equals(operation)) {
				userexchnageService.addUserexchnage (userexchnage);
			} else {
				userexchnageService.updateUserexchnage (userexchnage);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
