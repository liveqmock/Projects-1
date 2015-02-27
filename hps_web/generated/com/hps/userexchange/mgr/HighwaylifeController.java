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
import com.hps.userexchange.mgr.Highwaylife;


@Controller
@RequestMapping("/highwaylife")
public class HighwaylifeController extends BaseController {
	@Autowired
	private HighwaylifeService highwaylifeService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/highwaylife/highwaylife_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/highwaylife/highwaylife_list";
	}
	
	@RequestMapping("/add_form")
	public String addHighwaylifeFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Highwaylife highwaylife = new Highwaylife();
		map.put("highwaylife", highwaylife);
		map.put("operation", "add");
		
		return "/hps/highwaylife/highwaylife_form"; 
	}

	@RequestMapping("/edit_form")
	public String editHighwaylifeFormPage(Map<String, Object> map, HttpServletRequest request, String highwaylifeId) {
		map.put("operation", "edit");
		Highwaylife highwaylife = highwaylifeService.getHighwaylifeById(highwaylifeId);		
		if (highwaylife != null) {
			map.put("highwaylife", highwaylife);
		}
		return "/hps/highwaylife/highwaylife_form";
	}

	@RequestMapping("/view_form")
	public String viewHighwaylifeFormPage(Map<String, Object> map,HttpServletRequest request, String highwaylifeId) {
		map.put("operation", "view");
		Highwaylife highwaylife = highwaylifeService.getHighwaylifeById(highwaylifeId);		
		if (highwaylife != null) {
			map.put("highwaylife", highwaylife);
		}
		return "/hps/highwaylife/highwaylife_form";
	}
	
	@RequestMapping("/delete")
	public void deleteHighwaylife(PrintWriter writer, String highwaylifeIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (highwaylifeIds == null || highwaylifeIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = highwaylifeIds.split(",");
		try {
			boolean result = highwaylifeService.deleteHighwaylifeById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadHighwaylifeListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, HighwaylifeDAO.class.getName(), "getHighwaylifeList",
				"countHighwaylife", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitHighwaylifeForm(PrintWriter writer, Highwaylife highwaylife, String operation) {
		try {
			if ("add".equals(operation)) {
				highwaylifeService.addHighwaylife (highwaylife);
			} else {
				highwaylifeService.updateHighwaylife (highwaylife);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
