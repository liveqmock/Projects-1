package com.newsoft.common.attach.po;

import java.util.Date;

/**
 * 统一附件管理附件实体对象（表名：FRAME_ATTACH_Dir）
 * 
 * @author MyEclipse Persistence Tools
 */
public class AttachDir implements java.io.Serializable {
	private static final long serialVersionUID = -5109737001052391044L;

	// Fields
	private String attachId;
	private String fileName;
	private Long fileSize;
	private String contentType;
	private String fileDir;
	private Date createTime;

	// Constructors
	/** default constructor */
	public AttachDir() {
	}

	/** minimal constructor */
	public AttachDir(String attachId, String fileName, Long fileSize,
			String contentType, String fileDir) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.fileDir = fileDir;
	}

	/** full constructor */
	public AttachDir(String attachId, String fileName, Long fileSize,
			String contentType, String fileDir, Date createTime) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.fileDir = fileDir;
		this.createTime = createTime;
	}

	// Property accessors

	public String getAttachId() {
		return this.attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

}