package com.hps.report.mgr;

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


@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
	@Autowired
	private ReportService reportService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/report/report_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/report/report_list";
	}
	
	@RequestMapping("/add_form")
	public String addReportFormPage(Map<String, Object> map, HttpServletRequest request) {		
		ReportVo report = new ReportVo();
		map.put("report", report);
		map.put("operation", "add");
		
		return "/hps/report/report_form"; 
	}

	@RequestMapping("/edit_form")
	public String editReportFormPage(Map<String, Object> map, HttpServletRequest request, String reportId) {
		map.put("operation", "edit");
		ReportVo report = reportService.getReportById(reportId);		
		if (report != null) {
			map.put("report", report);
		}
		return "/hps/report/report_form";
	}

	@RequestMapping("/view_form")
	public String viewReportFormPage(Map<String, Object> map,HttpServletRequest request, String reportId) {
		map.put("operation", "view");
		ReportVo report = reportService.getReportById(reportId);		
		if (report != null) {
			map.put("report", report);
		}
		return "/hps/report/report_form";
	}
	
	@RequestMapping("/delete")
	public void deleteReport(PrintWriter writer, String reportIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (reportIds == null || reportIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = reportIds.split(",");
		try {
			boolean result = reportService.deleteReportById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadReportListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, ReportDAO.class.getName(), "getReportList",
				"countReport", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitReportForm(PrintWriter writer, ReportVo report, String operation) {
		try {
			if ("add".equals(operation)) {
				reportService.addReport (report);
			} else {
				reportService.updateReport (report);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
