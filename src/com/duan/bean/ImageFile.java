package com.duan.bean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.duan.intface.DownFile;
import com.duan.utils.UUIDutils;

public class ImageFile implements DownFile {

	private String name;
	private String path;
	private long size;
	private Date date;
	private URL url;
	private URLConnection con;
	private File file;
	private String contentType;
	
	public String getSizeKB() {
		return size / 1024 + "KB";
	}

	
	public ImageFile(String name, String path, URL url) {
		this.name = name;
		this.path = path + "/" + name;
		this.url = url;
		setDate(new Date());
	}

	public ImageFile(String name, String path, URL url, URLConnection con,
			String contentType, long fileSize) {
		this(name, path, url);
		this.con=con;
		this.contentType = contentType;
		this.size = fileSize;
	}

	@Override
	public String getShortName(int number) {
		if (name.length() >= number) {
			String shortName = new String();
			shortName = name.substring(0, 7) + "..."
					+ name.substring(name.length() - 8);
			return shortName;
		} else {
			return name;
		}
	}

	public String getTime() {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		return sf.format(date);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public URLConnection getCon() {
		return con;
	}

	public void setCon(URLConnection con) {
		this.con = con;
	}

	@Override
	public void getNewName() {
		name = UUIDutils.getID() + name.substring(name.lastIndexOf("."));
	}

	@Override
	public void creatNewFile() throws IOException {
		file = new File(path);
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		if (file.exists()) {
			getNewName();
		}
		file.createNewFile();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	@Override
	public void clear() {
		url=null;
		con=null;
		file=null;
	}
	
}
