package com.duan.intface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public interface DownFile {

	public String getContentType() ;
	public void setContentType(String contentType);
	public File getFile();
	public String getShortName(int number);
	public String getTime();
	public String getName();

	public void setName(String name);

	public String getPath();

	public void setPath(String path);

	public long getSize();

	public void setSize(long size);

	public Date getDate();
	public URL getUrl() ;
	public void setDate(Date date);

	public URLConnection getCon();

	public void setCon(URLConnection con);	

	public void creatNewFile() throws IOException;	
	public String getSizeKB();



	void getNewName();

}
