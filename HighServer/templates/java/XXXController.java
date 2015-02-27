package ${params.packageName};

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
import ${params.packageName}.${classNamePrefix};


@Controller
@RequestMapping("/${params.moduleName}")
public class ${classNamePrefix}Controller extends BaseController {
	@Autowired
	private ${classNamePrefix}Service ${params.moduleName}Service;

	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/record/${params.moduleName}/${params.moduleName}_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/record/${params.moduleName}/${params.moduleName}_list";
	}
	
	@RequestMapping("/add_form")
	public String add${classNamePrefix}FormPage(Map<String, Object> map, HttpServletRequest request) {		
		${classNamePrefix} ${params.moduleName} = new ${classNamePrefix}();
		map.put("${params.moduleName}", ${params.moduleName});
		map.put("operation", "add");
		
		return "/record/${params.moduleName}/${params.moduleName}_form"; 
	}

	@RequestMapping("/edit_form")
	public String edit${classNamePrefix}FormPage(Map<String, Object> map, HttpServletRequest request, String ${params.moduleName}Id) {
		map.put("operation", "edit");
		${classNamePrefix} ${params.moduleName} = ${params.moduleName}Service.get${classNamePrefix}ById(${params.moduleName}Id);		
		if (${params.moduleName} != null) {
			map.put("${params.moduleName}", ${params.moduleName});
		}
		return "/record/${params.moduleName}/${params.moduleName}_form";
	}

	@RequestMapping("/view_form")
	public String view${classNamePrefix}FormPage(Map<String, Object> map,HttpServletRequest request, String ${params.moduleName}Id) {
		map.put("operation", "view");
		${classNamePrefix} ${params.moduleName} = ${params.moduleName}Service.get${classNamePrefix}ById(${params.moduleName}Id);		
		if (${params.moduleName} != null) {
			map.put("${params.moduleName}", ${params.moduleName});
		}
		return "/record/${params.moduleName}/${params.moduleName}_form";
	}
	
	@RequestMapping("/delete")
	public void delete${classNamePrefix}(PrintWriter writer, String ${params.moduleName}Ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (${params.moduleName}Ids == null || ${params.moduleName}Ids.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = ${params.moduleName}Ids.split(",");
		try {
			boolean result = ${params.moduleName}Service.delete${classNamePrefix}ById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void load${classNamePrefix}ListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, ${classNamePrefix}DAO.class.getName(), "get${classNamePrefix}List",
				"count${classNamePrefix}", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submit${classNamePrefix}Form(PrintWriter writer, ${classNamePrefix} ${params.moduleName}, String operation) {
		try {
			if ("add".equals(operation)) {
				${params.moduleName}Service.add${classNamePrefix} (${params.moduleName});
			} else {
				${params.moduleName}Service.update${classNamePrefix} (${params.moduleName});				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
