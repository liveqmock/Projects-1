package com.newsoft.common.attach.po;

import java.util.Date;

/**
 * 统一附件管理附件实体对象（表名：FRAME_ATTACH）
 * 
 * @author MyEclipse Persistence Tools
 */
public class Attach implements java.io.Serializable {
	private static final long serialVersionUID = -5109737001052391044L;

	// Fields
	private String attachId;
	private String fileName;
	private Long fileSize;
	private String contentType;
	private byte[] content;
	private Date createTime;
	private int fileType;
	private String fileDir;

	// Constructors
	/** default constructor */
	public Attach() {
	}

	/** minimal constructor */
	public Attach(String attachId, String fileName, Long fileSize,
			String contentType, byte[] content, int fileType, String fileDir) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.content = content;
		this.fileType = fileType;
		this.fileDir = fileDir;
	}

	/** full constructor */
	public Attach(String attachId, String fileName, Long fileSize,
			String contentType, byte[] content, Date createTime, int fileType,
			String fileDir) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.content = content;
		this.createTime = createTime;
		this.fileType = fileType;
		this.fileDir = fileDir;
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

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

}