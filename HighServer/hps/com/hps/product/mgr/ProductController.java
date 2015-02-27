package com.hps.product.mgr;

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
import com.hps.product.mgr.Product;


@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
	@Autowired
	private ProductService productService;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/product/product_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/product/product_list";
	}
	
	@RequestMapping("/add_form")
	public String addProductFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Product product = new Product();
		map.put("product", product);
		map.put("operation", "add");
		
		return "/hps/product/product_form"; 
	}

	@RequestMapping("/edit_form")
	public String editProductFormPage(Map<String, Object> map, HttpServletRequest request, String productId) {
		map.put("operation", "edit");
		Product product = productService.getProductById(productId);		
		if (product != null) {
			map.put("product", product);
		}
		return "/hps/product/product_form";
	}

	@RequestMapping("/view_form")
	public String viewProductFormPage(Map<String, Object> map,HttpServletRequest request, String productId) {
		map.put("operation", "view");
		Product product = productService.getProductById(productId);		
		if (product != null) {
			map.put("product", product);
		}
		return "/hps/product/product_form";
	}
	
	@RequestMapping("/delete")
	public void deleteProduct(PrintWriter writer, String productIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (productIds == null || productIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = productIds.split(",");
		try {
			boolean result = productService.deleteProductById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadProductListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, ProductDAO.class.getName(), "getProductList",
				"countProduct", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitProductForm(PrintWriter writer, Product product, String operation) {
		try {
			if ("add".equals(operation)) {
				productService.addProduct (product);
			} else {
				productService.updateProduct (product);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
