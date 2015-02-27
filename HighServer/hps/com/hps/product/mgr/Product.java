package com.hps.product.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name="product", pk="product_id")
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** default constructor */
	public Product() {
	}

	   private String id;
	   private String title;
	   private Integer protype;
	   private String fileid;
	   private Integer score;
	   private String creator;
	   private String creatorname;
	   private java.util.Date createtime;
		
		public String getId () {
		return id;
	}

	public void  setId(String id) {
		this.id = id;
	}
	
		public String getTitle () {
		return title;
	}

	public void  setTitle(String title) {
		this.title = title;
	}
	
		public Integer getProtype () {
		return protype;
	}

	public void  setProtype(Integer protype) {
		this.protype = protype;
	}
	
		public String getFileid () {
		return fileid;
	}

	public void  setFileid(String fileid) {
		this.fileid = fileid;
	}
	
		public Integer getScore () {
		return score;
	}

	public void  setScore(Integer score) {
		this.score = score;
	}
	
		public String getCreator () {
		return creator;
	}

	public void  setCreator(String creator) {
		this.creator = creator;
	}
	
		public String getCreatorname () {
		return creatorname;
	}

	public void  setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	
		public java.util.Date getCreatetime () {
		return createtime;
	}

	public void  setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
	}