package com.newsoft.common.attach;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.newsoft.common.attach.po.FrameEditorAttach;
import com.newsoft.common.attach.service.FrameEditorAttachService;
import com.newsoft.common.spring.BaseController;

/**
 * @author mengxw 通用附件标签的控制类
 */
@Controller
@RequestMapping("/editorAttach")
public class FrameEditorAttachController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(FrameEditorAttachController.class);

	@Autowired
	private FrameEditorAttachService attachService;

	
	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 * @param attachId
	 */
	@RequestMapping("/dowmloadAttach")
	public void dowmloadAttach(HttpServletRequest request,
			HttpServletResponse response, String attachId) {
		FrameEditorAttach attach = attachService.getAttachById(attachId);
		try {
			attachService.downLoadAttach(response, attach);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载编辑器图片附件出错：" + e.getMessage());
			logger.debug(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 编辑器上传图片
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/imageUpload")
	public void editorUpload(ModelMap modelMap,
			MultipartHttpServletRequest request, PrintWriter writer,HttpServletRequest servletRequest){
		Map<String, MultipartFile> fileMap = request.getFileMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String attachId = "";
		try {
			attachId = attachService.uploadAttachToDB(fileMap);
			map.put("msg", servletRequest.getContextPath() + "/editorAttach/dowmloadAttach?attachId=" + attachId);
			map.put("err", "");
		} catch (Exception e) {
			map.put("err", "上传图片失败");
			e.printStackTrace();
			logger.debug(e.getMessage(),e);
		}

		writer.write(JSONObject.fromObject(map).toString());
	}
	
}
