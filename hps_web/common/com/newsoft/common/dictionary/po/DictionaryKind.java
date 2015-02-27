package com.newsoft.common.dictionary.po;

/**
 * 字典类别表（表名：FRAME_DICTIONARY_KIND）
 * 
 * @author MyEclipse Persistence Tools
 */

public class DictionaryKind implements java.io.Serializable {

	private static final long serialVersionUID = 1960668240533834756L;
	// Fields
	private Integer kindId;// 类别Id
	private String kindName;// 类别名称
	private String kindDes;// 类别描述
	private Integer parentId;// 父类别Id

	// Constructors

	/** default constructor */
	public DictionaryKind() {
	}

	/** full constructor */
	public DictionaryKind(Integer kindId, String kindName, String kindDes,
			Integer parentId) {
		this.kindId = kindId;
		this.kindName = kindName;
		this.kindDes = kindDes;
		this.parentId = parentId;
	}

	// Property accessors

	public Integer getKindId() {
		return this.kindId;
	}

	public void setKindId(Integer kindId) {
		this.kindId = kindId;
	}

	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getKindDes() {
		return this.kindDes;
	}

	public void setKindDes(String kindDes) {
		this.kindDes = kindDes;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}