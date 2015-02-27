package com.newsoft.common.attach;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.attach.service.FrameAttachService;
import com.newsoft.common.spring.BaseController;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.utils.FileTools;
import com.newsoft.utils.StringTools;

/**
 * @author mengxw 通用附件标签的控制类
 */
@Controller
@RequestMapping("/attach")
public class FrameAttachController extends BaseController {

	@Autowired
	private FrameAttachService attachService;
	@Autowired
	private UserMgrDAO userMgrDAO;

	@Value("${imagePath}")
	private String imagePath = "";
	
	/**
	 * 上传附件
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttachToDB")
	public void processUploadDB(ModelMap modelMap,
			MultipartHttpServletRequest request, PrintWriter writer)
			throws Exception {
		String attachIds = "";
		Map<String, MultipartFile> fileMap = request.getFileMap();
		attachIds = attachService.uploadAttachToDB(fileMap);

		writer.write(attachIds.toString());
	}
	
	/**
	 * 上传附件
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttachToDBWithCut")
	public void uploadAttachToDBWithCut(ModelMap modelMap,
			MultipartHttpServletRequest request, PrintWriter writer)
			throws Exception {
//		String attachIds = "";
//		Map<String, MultipartFile> fileMap = request.getFileMap();
//		attachIds = attachService.uploadAttachToDBWithCut(fileMap);
//
//		writer.write(attachIds.toString());
		String attachIds = "";
		Map<String, MultipartFile> fileMap = request.getFileMap();

		String path = request.getSession().getServletContext().getRealPath("/");
		if (StringTools.isEmpty(imagePath)) {
			imagePath = path;
		}
		attachIds = attachService.uploadAttachToDirectoryWithCut(fileMap, imagePath);

		writer.write(attachIds.toString());
	}

	/**
	 * 上传附件到特定的目录
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttachToDir")
	public void processUploadDir(ModelMap modelMap,
			MultipartHttpServletRequest request, PrintWriter writer,
			String uploadType) throws Exception {
		String attachIds = "";
		// StringBuffer attachIds = new StringBuffer();
		Map<String, MultipartFile> fileMap = request.getFileMap();

		String path = request.getSession().getServletContext().getRealPath("/");
		attachIds = attachService.uploadAttachToDirectory(fileMap, path);

		writer.write(attachIds.toString());
	}
	

	/**
	 * 上传附件到特定的目录
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadImageAttachToDir")
	public void uploadImageAttachToDir(ModelMap modelMap,
			MultipartHttpServletRequest request, PrintWriter writer,
			String uploadType) throws Exception {
		String attachIds = "";
		Map<String, MultipartFile> fileMap = request.getFileMap();

		String path = request.getSession().getServletContext().getRealPath("/");
		if (StringTools.isEmpty(imagePath)) {
			imagePath = path;
		}
		attachIds = attachService.uploadAttachToDirectory(fileMap, imagePath);

		writer.write(attachIds.toString());
	}

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
		Attach attach = attachService.getAttachById(attachId);
		try {
			FileTools.downloadFile(response, attach);
		} catch (Exception e) {
			System.err.println("下载内容附件出错：" + e.getMessage());
		}
	}

	/**
	 * 预览图片的页面
	 * 
	 * @param request
	 * @param response
	 * @param attachId
	 */
	@RequestMapping("/previewImage")
	public String preViewImage(HttpServletRequest request,
			HttpServletResponse response, String attachIds) {
		attachIds = StringUtils.removeEndIgnoreCase(attachIds, ",");
		request.setAttribute("attachIds", attachIds);
		return "/frame/system/imgPreview";
	}
}
