package com.newsoft.common.dictionary.po;

/**
 * 字典类型表（表名：FRAME_DICTIONARY_TYPE）
 * 
 * @author MyEclipse Persistence Tools
 */

public class DictionaryType implements java.io.Serializable {
	private static final long serialVersionUID = -5418554108468550617L;
	// Fields
	private Integer typeId;// 类型Id
	private Integer kindId;// 类别Id
	private String typeName;// 类型名称
	private String typeDes;// 类型描述
	private Integer sortIndex;// 排序号

	// Constructors

	/** default constructor */
	public DictionaryType() {
	}

	/** minimal constructor */
	public DictionaryType(Integer typeId, Integer kindId, String typeName) {
		this.typeId = typeId;
		this.kindId = kindId;
		this.typeName = typeName;
	}

	/** full constructor */
	public DictionaryType(Integer typeId, Integer kindId, String typeName,
			String typeDes, Integer sortIndex) {
		this.typeId = typeId;
		this.kindId = kindId;
		this.typeName = typeName;
		this.typeDes = typeDes;
		this.sortIndex = sortIndex;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDes() {
		return this.typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}

	public Integer getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getKindId() {
		return kindId;
	}

	public void setKindId(Integer kindId) {
		this.kindId = kindId;
	}

}