package com.brian.test;

import java.io.Serializable;

public class Pictures implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2492059556482745323L;

	private String description;

	private String imgPath;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
