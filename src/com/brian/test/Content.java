package com.brian.test;

import java.util.List;

public class Content implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9007449814732521721L;
	private String author;
	private String id;
	private String title;
	private List<Pictures> pictures;
	private String path;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Pictures> getPictures() {
		return pictures;
	}

	public void setPictures(List<Pictures> pictures) {
		this.pictures = pictures;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
