package com.newsoft.common.attach.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newsoft.common.attach.dao.FrameEditorAttachDAO;
import com.newsoft.common.attach.po.FrameEditorAttach;
import com.newsoft.common.attach.service.FrameEditorAttachService;
import com.newsoft.utils.FileTools;
import com.newsoft.utils.StringTools;
import com.newsoft.utils.UUIDTool;

/**
 * @author mengxw
 * 
 */
@Service
public class FrameEditorAttachServiceImpl implements FrameEditorAttachService {

	@Autowired
	private FrameEditorAttachDAO attachDAO;

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public String addAttach(FrameEditorAttach attach) throws Exception {
		attachDAO.addAttach(attach);
		return null;
	}

	public FrameEditorAttach getAttachById(String attachId) {
		return attachDAO.getAttachById(attachId);
	}

	/**
	 * 上传附件到数据库
	 */
	public String uploadAttachToDB(Map<String, MultipartFile> fileMap) throws Exception {
		String attachIds = "";
		for (Map.Entry<String, MultipartFile> f : fileMap.entrySet()) {
			MultipartFile file = f.getValue();
			String id = UUIDTool.getUUID();
			String originalFileName = file.getOriginalFilename();
			attachIds = StringTools.uniteTwoStringBySemicolon(attachIds, id);
			FrameEditorAttach attach = new FrameEditorAttach();
			attach.setAttachId(id);
			attach.setFileName(originalFileName);
			attach.setFileSize(file.getSize());
			attach.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
			attach.setContent(file.getBytes());
			attach.setFileType(1);
			attach.setCreateTime(new Date());
			addAttach(attach);
		}
		return attachIds.toString();
	}

	public void updateAttach(FrameEditorAttach attach) {
		attachDAO.updateAttach(attach);
	}

	public void downLoadAttach(HttpServletResponse response, FrameEditorAttach attach) throws Exception {
		OutputStream out = response.getOutputStream();
		String sExt = attach.getContentType();
		// 文件格式为pdf、doc、xml格式的直接用浏览器打开。其他提示下载或者打开
		if (sExt.equals("application/pdf") || sExt.equals("image/pjpeg") || sExt.equals("image/bmp") || sExt.equals("application/vnd.ms-powerpoint") || sExt.equals("text/plain") || sExt.equals("application/msword") || sExt.equals("text/plain") || sExt.equals("application/vnd.ms-excel")) {
			if (sExt.equals("application/pdf"))
				response.setContentType(sExt);
			else
				response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(attach.getFileName().getBytes("GBK"), "ISO8859_1"));
		} else {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(attach.getFileName().getBytes("GBK"), "ISO8859_1"));
		}
		if (attach.getFileType() == 1) {
			InputStream in = new ByteArrayInputStream(attach.getContent());
			FileTools.outputAttachFileStream(out, in);
		}
	}

	public String deleteAttachByIds(String objectId, String attachIds) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String deleteAttachById(String attachId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
