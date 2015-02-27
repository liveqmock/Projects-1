package com.newsoft.common.attach.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.newsoft.common.attach.po.FrameEditorAttach;

/**
 * @author mengxw
 * 
 */
public interface FrameEditorAttachService {

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public String addAttach(FrameEditorAttach attach) throws Exception;

	/**
	 * 上传附件到数据库
	 * 
	 * @param fileMap
	 * @return
	 * @throws Exception
	 */
	public String uploadAttachToDB(Map<String, MultipartFile> fileMap)
			throws Exception;

	/**
	 * 
	 * 下载附件
	 * 
	 */
	public void downLoadAttach(HttpServletResponse response, FrameEditorAttach attach) throws Exception;
	
	/**
	 * 删除对象ID对应的附件列表
	 * 
	 * @param Long
	 *            attachId;
	 */
	public String deleteAttachByIds(String objectId, String attachIds)
			throws Exception;

	/**
	 * 通过主键删除一条附件
	 * 
	 * @param Long
	 *            attachIds;
	 */
	public String deleteAttachById(String attachId) throws Exception;


	/**
	 * 根据ID获取附件
	 * 
	 * @param attachId
	 *            附件id
	 * @return
	 */
	public FrameEditorAttach getAttachById(String attachId);


	public void updateAttach(FrameEditorAttach attach);
	

}
