package com.hps.level.mgr;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hps.level.mgr.Scorelevel;
import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.utils.JSONTool;


@Controller
@RequestMapping("/scorelevel")
public class ScorelevelController extends BaseController {
	@Autowired
	private ScorelevelService ScorelevelService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/scorelevel/scorelevel_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/scorelevel/scorelevel_list";
	}
	
	@RequestMapping("/add_form")
	public String addScorelevelFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Scorelevel Scorelevel = new Scorelevel();
		map.put("Scorelevel", Scorelevel);
		map.put("operation", "add");
		
		return "/hps/scorelevel/scorelevel_form"; 
	}

	@RequestMapping("/edit_form")
	public String editScorelevelFormPage(Map<String, Object> map, HttpServletRequest request, String ScorelevelId) {
		map.put("operation", "edit");
		Scorelevel Scorelevel = ScorelevelService.getScorelevelById(ScorelevelId);		
		if (Scorelevel != null) {
			map.put("Scorelevel", Scorelevel);
		}
		return "/hps/scorelevel/scorelevel_form";
	}

	@RequestMapping("/view_form")
	public String viewScorelevelFormPage(Map<String, Object> map,HttpServletRequest request, String ScorelevelId) {
		map.put("operation", "view");
		Scorelevel Scorelevel = ScorelevelService.getScorelevelById(ScorelevelId);		
		if (Scorelevel != null) {
			map.put("Scorelevel", Scorelevel);
		}
		return "/hps/scorelevel/scorelevel_form";
	}
	
	@RequestMapping("/delete")
	public void deleteScorelevel(PrintWriter writer, String ScorelevelIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (ScorelevelIds == null || ScorelevelIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = ScorelevelIds.split(",");
		try {
			boolean result = ScorelevelService.deleteScorelevelById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadScorelevelListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, ScorelevelDAO.class.getName(), "getScorelevelList",
				"countScorelevel", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitScorelevelForm(PrintWriter writer, Scorelevel Scorelevel, String operation) {
		try {
			if ("add".equals(operation)) {
				ScorelevelService.addScorelevel (Scorelevel);
			} else {
				ScorelevelService.updateScorelevel (Scorelevel);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
