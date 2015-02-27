package com.hps.member.mgr;

import java.io.PrintWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.common.attach.service.FrameAttachService;
import com.newsoft.common.dictionary.service.DictionaryService;
import com.newsoft.common.page.PageService;
import com.newsoft.sysmanager.cache.UserCacheSupport;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.StringTools;

@RequestMapping("/h/m")
@Controller
public class FMemberController {

	Logger logger = LoggerFactory.getLogger(FMemberController.class);
	
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserMgrService userMgrService;
	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private PageService pageService;
	@Autowired
	private FrameAttachService attachService;
	@Autowired
	private UserCacheSupport userCache;
	
	@RequestMapping("/loadMemInfo")
	public void loadMemInfo(PrintWriter writer, Map<String, Object> map, String memId) {
		
		if (StringTools.isEmpty(memId)) {
			map.put("user", new UserVo());
			writer.write(JSONTool.toJson(map));
			return;
		}
		
		try {
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		

		writer.write(JSONTool.toJson(map));
	}
	
	
}
