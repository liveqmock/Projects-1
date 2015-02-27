package com.hps.goods.mgr;

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
@RequestMapping("/goods")
public class GoodsController extends BaseController {
	@Autowired
	private GoodsService goodsService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/goods/goods_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/goods/goods_list";
	}
	
	@RequestMapping("/add_form")
	public String addGoodsFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Goods goods = new Goods();
		map.put("goods", goods);
		map.put("operation", "add");
		
		return "/hps/goods/goods_form"; 
	}

	@RequestMapping("/edit_form")
	public String editGoodsFormPage(Map<String, Object> map, HttpServletRequest request, String goodsId) {
		map.put("operation", "edit");
		Goods goods = goodsService.getGoodsById(goodsId);		
		if (goods != null) {
			map.put("goods", goods);
		}
		return "/hps/goods/goods_form";
	}

	@RequestMapping("/view_form")
	public String viewGoodsFormPage(Map<String, Object> map,HttpServletRequest request, String goodsId) {
		map.put("operation", "view");
		Goods goods = goodsService.getGoodsById(goodsId);		
		if (goods != null) {
			map.put("goods", goods);
		}
		return "/hps/goods/goods_form";
	}
	
	@RequestMapping("/delete")
	public void deleteGoods(PrintWriter writer, String goodsIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (goodsIds == null || goodsIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = goodsIds.split(",");
		try {
			boolean result = goodsService.deleteGoodsById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadGoodsListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, GoodsDAO.class.getName(), "getGoodsList",
				"countGoods", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitGoodsForm(PrintWriter writer, Goods goods, String operation) {
		try {
			if ("add".equals(operation)) {
				goodsService.addGoods (goods);
			} else {
				goodsService.updateGoods (goods);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
