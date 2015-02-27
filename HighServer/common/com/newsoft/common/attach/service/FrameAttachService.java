package com.newsoft.common.attach.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.attach.po.AttachRelation;

/**
 * @author mengxw
 * 
 */
public interface FrameAttachService {

	/**
	 * 保存附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	public String addAttach(Attach attach) throws Exception;
	
	/**
	 * 上传附件到数据库
	 * @param fileMap
	 * @return
	 * @throws Exception
	 */
	public String uploadAttachToDB(Map<String, MultipartFile> fileMap) throws Exception;
	
	/**
	 * 上传附件到数据库
	 * @param fileMap
	 * @return
	 * @throws Exception
	 */
	public String uploadAttachToDBWithCut(Map<String, MultipartFile> fileMap) throws Exception;
	
	/**
	 * 上传附件到文件目录
	 * @param fileMap
	 * @return
	 * @throws Exception
	 */
	public String uploadAttachToDirectory(Map<String, MultipartFile> fileMap,String path);
	
	/**
	 * 上传附件到文件目录
	 * @param fileMap
	 * @return
	 * @throws Exception
	 */
	public String uploadAttachToDirectoryWithCut(Map<String, MultipartFile> fileMap,String path);

	/**
	 * 删除对象ID对应的附件列表
	 * 
	 * @param Long
	 *            attachId;
	 */
	public String deleteAttachByIds(String objectId,String attachIds) throws Exception;
	
	/**
	 * 通过主键删除一条附件
	 * 
	 * @param Long
	 *            attachIds;
	 */
	public String deleteAttachByIds(String attachIds) throws Exception;
	
	/**
	 * 删除附件关联信息
	 * 
	 * @param Long
	 *            attachId;
	 */
	public String deleteAttachRelation(AttachRelation attachRelation) throws Exception;


	/**
	 * 通过内容id获取该内容的所有附件信息，不包含附件具体内容（二进制内容）
	 * 
	 * @param informationId
	 * @return
	 */
	public List<Attach> getAttachListByObject(String object);


	/**
	 * 根据ID获取附件
	 * 
	 * @param attachId
	 *            附件id
	 * @return
	 */
	public Attach getAttachById(String attachId);
	
	/***
	 * 保存附件关联关系
	 * 
	 * @param attachRelation
	 * @throws Exception
	 */
	public String addAttachRelation(AttachRelation attachRelation) throws Exception;
	

	/**
	 * 
	 * @param fileIds ：附件的Id列表
	 * @param objectId：业务数据的主键
	 * @return
	 * @throws Exception
	 */
	public String addAttachRelation(String fileIds,String objectId) throws Exception;
	
	/**
	 * 删除附件的关联关系
	 * 
	 * @param objectId
	 * @throws Exception
	 */
	public void deleteAttachRelationByObjId(String objectId)
			throws Exception;

}
