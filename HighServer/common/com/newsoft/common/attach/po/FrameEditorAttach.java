package com.newsoft.common.attach.po;

import java.util.Date;

/**
 * 统一附件管理附件实体对象（表名：FRAME_ATTACH）
 * 
 * @author MyEclipse Persistence Tools
 */
public class FrameEditorAttach implements java.io.Serializable {
	private static final long serialVersionUID = -5109737001052391044L;

	// Fields
	private String attachId;
	private String fileName;
	private Long fileSize;
	private String contentType;
	private byte[] content;
	private Date createTime;
	/**
	 * 1.保存在服务器中。2.保存在磁盘中。3.保存在HDFS数据节点中  4.视频文件
	 */
	private int fileType;
	private String fileDir;
	private int pageSize;
	private String videoTime; // 视频时间
	
	private int videoViewCount;//视频浏览数
	private String userName; //上传用户
	
	public int getVideoViewCount() {
		return videoViewCount;
	}

	public void setVideoViewCount(int videoViewCount) {
		this.videoViewCount = videoViewCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//用于标示视频文件
	public static final  int VideoFileType= 4;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// Constructors
	/** default constructor */
	public FrameEditorAttach() {
	}

	/** minimal constructor */
	public FrameEditorAttach(String attachId, String fileName, Long fileSize,
			String contentType, byte[] content, int fileType, String fileDir,String videoTime) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.content = content;
		this.fileType = fileType;
		this.fileDir = fileDir;
		this.videoTime=videoTime;
	}

	/** full constructor */
	public FrameEditorAttach(String attachId, String fileName, Long fileSize,
			String contentType, byte[] content, Date createTime, int fileType,
			String fileDir,String videoTime) {
		this.attachId = attachId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.content = content;
		this.createTime = createTime;
		this.fileType = fileType;
		this.fileDir = fileDir;
		this.videoTime=videoTime;
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

	public String getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(String videoTime) {
		this.videoTime = videoTime;
	}

}