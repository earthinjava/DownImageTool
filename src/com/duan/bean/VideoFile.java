package com.duan.bean;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import com.duan.intface.DownFile;

public class VideoFile implements DownFile {

	public VideoFile(String name, String path, URL url, URLConnection con) {
		// TODO Auto-generated constructor stub
	}
	

	public VideoFile(String name, String path, URL url, URLConnection con,
			String contentType, long fileSize) {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSize(long size) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public URLConnection getCon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCon(URLConnection con) {
		// TODO Auto-generated method stub

	}

	@Override
	public void creatNewFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getShortName(int number) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getSizeKB() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public URL getUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setContentType(String contentType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getNewName() {
		// TODO Auto-generated method stub
		
	}

}
