/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

/**
 *
 * @author alfredo
 */
public class MultimedioFile {
	private String id;
	private String Name;
	private String mime;
	private long length;
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
		int extDot = name.lastIndexOf('.');
		if (extDot > 0) {
			String extension = name.substring(extDot + 1);
			if ("bmp".equalsIgnoreCase(extension)) {
				mime = "image/bmp";
			} else if ("jpg".equalsIgnoreCase(extension)) {
				mime = "image/jpeg";
			} else if ("jpeg".equalsIgnoreCase(extension)) {
				mime = "image/jpeg";
			} else if ("gif".equalsIgnoreCase(extension)) {
				mime = "image/gif";
			} else if ("png".equalsIgnoreCase(extension)) {
				mime = "image/png";
			} else {
				mime = "image/unknown";
			}
		}
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getMime() {
		return mime;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public String toString(){
		if(id == null){
			return "[NULL]";
		} else{
			return "["+Name+"] : "+mime+" ("+length+" bytes)";
		}
	}
}
