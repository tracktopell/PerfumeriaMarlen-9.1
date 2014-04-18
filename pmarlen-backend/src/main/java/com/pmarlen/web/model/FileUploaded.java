package com.pmarlen.web.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * @author alfredo
 */
public class FileUploaded {
    private String name;
    private String mime;
    private long length;
    private byte[] data;
	private String id;
	private String pathDownloaded;
	private boolean selected;
	
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public String getName() {
        return name;
    }

	public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public void setName(String name) {
        this.name = name;
		mime = getAccordingMime(name);
    }
	
	public static String getAccordingMime(String fileName){
		int extDot = fileName.lastIndexOf('.');
		String mime =  null;
        if(extDot > 0){
            String extension = fileName.substring(extDot +1).toLowerCase();
			if("png".equals(extension)){
                mime="image/png";
            } else if("zip".equals(extension)){
                mime="application/zip";
            } else {
                mime = "application/octect-stream";
            }
        }
		return mime;
	}
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }
    
    public String getMime(){
        return mime;
    }

	public boolean isZipFile() {
		return mime.equalsIgnoreCase("application/zip");
	}

	public String getPathDownloaded() {
		return pathDownloaded;
	}

	public void setPathDownloaded(String pathDownloaded) {
		this.pathDownloaded = pathDownloaded;
	}
	
	public void downloadToPath() throws IOException{
		FileOutputStream fos = new FileOutputStream(pathDownloaded);
		
		fos.write(data);
		fos.flush();
		
		fos.close();
		data = null;
	}
	static final DecimalFormat df = new DecimalFormat("###,##0.00");
	
	public String getHumanLength(){
		
		if(length > 1048576) {
			return df.format(((double)length)/1048576.0)+"MB";
		} else {
			return df.format(((double)length)/1024.0)+"KB";
		}		
	}
	
	public String getFamily(){
		return  "image";
	}

	public boolean isAccepted() {
		return mime.startsWith("image")||mime.equals("application/zip");
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	
}	
