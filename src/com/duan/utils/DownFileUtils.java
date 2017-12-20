package com.duan.utils;

import java.net.URL;
import java.net.URLConnection;

import com.duan.bean.ImageFile;
import com.duan.bean.VideoFile;
import com.duan.intface.DownFile;

public class DownFileUtils {

	public static DownFile getDownFile(String name, String path, URL url) {
		DownFile downFile;
		URLConnection con = UrlConn.getUrlConn(url);
		String contentType = null;		
		long fileSize = 0;
		if (contentType == null || fileSize == 0) {
			contentType = con.getContentType();
			fileSize = con.getContentLengthLong();
		}
		if (contentType == null) {
			name += ".jpg";
		} else {
			name += "." + getLastName(contentType);
		}
		if (contentType != null && contentType.contains("video")) {
			downFile = new VideoFile(name, path, url, con, contentType,
					fileSize);
		} else {
			downFile = new ImageFile(name, path, url, con, contentType,
					fileSize);
		}
		return downFile;
	}

	private static String getLastName(String contentType) {
		if (contentType != null && contentType.contains("video")) {
			return "mp4";
		} else if (contentType != null && contentType.contains("gif")) {
			return "gif";
		} else {
			return "jpg";
		}
	}

}
