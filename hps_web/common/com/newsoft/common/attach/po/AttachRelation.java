package com.newsoft.common.attach.po;

/**
 * 统一附件管理附件与其它模块的关联表（表名：FRAME_ATTACH_RELATION）
 * 
 * @author MyEclipse Persistence Tools
 */

public class AttachRelation implements java.io.Serializable {
	private static final long serialVersionUID = -2891423727824939873L;
	// Fields
	private String objectId;
	private String attachId;
	private Integer attachType;

	// Constructors
	/** default constructor */
	public AttachRelation() {
	}

	/** full constructor */
	public AttachRelation(String objectId, String attachId,
			Integer attachType) {
		this.objectId = objectId;
		this.attachId = attachId;
		this.attachType = attachType;
	}

	// Property accessors

	public Integer getAttachType() {
		return this.attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

}