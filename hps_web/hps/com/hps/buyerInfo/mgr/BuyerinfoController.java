package com.hps.buyerInfo.mgr;

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
@RequestMapping("/buyerinfo")
public class BuyerinfoController extends BaseController {
	@Autowired
	private BuyerinfoService buyerinfoService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/buyerinfo/buyerinfo_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/buyerinfo/buyerinfo_list";
	}
	
	@RequestMapping("/add_form")
	public String addBuyerinfoFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Buyerinfo buyerinfo = new Buyerinfo();
		map.put("buyerinfo", buyerinfo);
		map.put("operation", "add");
		
		return "/hps/buyerinfo/buyerinfo_form"; 
	}

	@RequestMapping("/edit_form")
	public String editBuyerinfoFormPage(Map<String, Object> map, HttpServletRequest request, String buyerinfoId) {
		map.put("operation", "edit");
		Buyerinfo buyerinfo = buyerinfoService.getBuyerinfoById(buyerinfoId);		
		if (buyerinfo != null) {
			map.put("buyerinfo", buyerinfo);
		}
		return "/hps/buyerinfo/buyerinfo_form";
	}

	@RequestMapping("/view_form")
	public String viewBuyerinfoFormPage(Map<String, Object> map,HttpServletRequest request, String buyerinfoId) {
		map.put("operation", "view");
		Buyerinfo buyerinfo = buyerinfoService.getBuyerinfoById(buyerinfoId);		
		if (buyerinfo != null) {
			map.put("buyerinfo", buyerinfo);
		}
		return "/hps/buyerinfo/buyerinfo_form";
	}
	
	@RequestMapping("/delete")
	public void deleteBuyerinfo(PrintWriter writer, String buyerinfoIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (buyerinfoIds == null || buyerinfoIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = buyerinfoIds.split(",");
		try {
			boolean result = buyerinfoService.deleteBuyerinfoById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadBuyerinfoListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, BuyerinfoDAO.class.getName(), "getBuyerinfoList",
				"countBuyerinfo", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitBuyerinfoForm(PrintWriter writer, Buyerinfo buyerinfo, String operation) {
		try {
			if ("add".equals(operation)) {
				buyerinfoService.addBuyerinfo (buyerinfo);
			} else {
				buyerinfoService.updateBuyerinfo (buyerinfo);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
