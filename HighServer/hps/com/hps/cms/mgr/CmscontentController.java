package com.hps.cms.mgr;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.dictionary.service.DictionaryService;
import com.newsoft.common.page.PageService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.StringTools;
import com.newsoft.utils.UUIDTool;


@Controller
@RequestMapping("/cmscontent")
public class CmscontentController extends BaseController {
	@Autowired
	private CmscontentService cmscontentService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PageService pageService;

	@RequestMapping("/main")
	public String mainPage() {
		return "/hps/cmscontent/cmscontent_main";
	}

	@RequestMapping("/list")
	public String listPage() {
		return "/hps/cmscontent/cmscontent_list";
	}
	
	@RequestMapping("/add_form")
	public String addCmscontentFormPage(Map<String, Object> map, HttpServletRequest request) {		
		Cmscontent cmscontent = new Cmscontent();
		map.put("cmscontent", cmscontent);
		map.put("operation", "add");
		
		return "/hps/cmscontent/cmscontent_form"; 
	}

	@RequestMapping("/edit_form")
	public String editCmscontentFormPage(Map<String, Object> map, HttpServletRequest request, String cmscontentId) {
		map.put("operation", "edit");
		Cmscontent cmscontent = cmscontentService.getCmscontentById(cmscontentId);		
		if (cmscontent != null) {
			map.put("cmscontent", cmscontent);
		}
		return "/hps/cmscontent/cmscontent_form";
	}

	@RequestMapping("/view_form")
	public String viewCmscontentFormPage(Map<String, Object> map,HttpServletRequest request, String cmscontentId) {
		map.put("operation", "view");
		Cmscontent cmscontent = cmscontentService.getCmscontentById(cmscontentId);		
		if (cmscontent != null) {
			map.put("cmscontent", cmscontent);
		}
		return "/hps/cmscontent/viewCmsInfo";
	}
	
	@RequestMapping("/delete")
	public void deleteCmscontent(PrintWriter writer, String cmscontentIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (cmscontentIds == null || cmscontentIds.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "主键不能为空");
			return;
		}
				
		String[] idArray = cmscontentIds.split(",");
		try {
			boolean result = cmscontentService.deleteCmscontentById(idArray);
			map.put("success", result);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		writer.write(JSONTool.toJson(map));
	}	
	
	@RequestMapping("/load_list")
	public void loadCmscontentListData(HttpServletRequest request, PrintWriter writer) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = pageService.gridPageQuery(request, CmscontentDAO.class.getName(), "getCmscontentList",
				"countCmscontent", params);
		writer.write(json);
	}

	@RequestMapping("/submit_form")
	public void submitCmscontentForm(PrintWriter writer, Cmscontent cmscontent, String operation) {
		try {
			if (StringTools.isEmpty(cmscontent.getId())) {
				cmscontent.setAuthorid(SessionUtil.getSessionUserId());
				cmscontent.setAuthorname(SessionUtil.getSessionUser().getUserName());
				cmscontent.setCratetime(new Date());
				cmscontent.setId(UUIDTool.getUUID());
				
				cmscontentService.addCmscontent (cmscontent);
			} else {
				cmscontentService.updateCmscontent (cmscontent);				
			}
			 writer.write("{\"success\":true}");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
			writer.write("{\"success\":false}");
		}
	}
	
}
