package com.hps.goods.mgr;

public class Goods{
	private String id;
	private String image;
	private String largeimage;
	private String title;
	private Integer score;
	private Integer type;
	private String detail;
	private String imagePath;
	private String imageName;
	private String largeImagePath;
	private String largeImageName;
		
	public String getId () {
		return id;
	}

	public void  setId(String id) {
		this.id = id;
	}
	
		public String getImage () {
		return image;
	}

	public void  setImage(String image) {
		this.image = image;
	}
	
		public String getLargeimage () {
		return largeimage;
	}

	public void  setLargeimage(String largeimage) {
		this.largeimage = largeimage;
	}
	
		public String getTitle () {
		return title;
	}

	public void  setTitle(String title) {
		this.title = title;
	}
	
		public Integer getScore () {
		return score;
	}

	public void  setScore(Integer score) {
		this.score = score;
	}
	
		public Integer getType () {
		return type;
	}

	public void  setType(Integer type) {
		this.type = type;
	}
	
		public String getDetail () {
		return detail;
	}

	public void  setDetail(String detail) {
		this.detail = detail;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getLargeImageName() {
		return largeImageName;
	}

	public void setLargeImageName(String largeImageName) {
		this.largeImageName = largeImageName;
	}
	
}