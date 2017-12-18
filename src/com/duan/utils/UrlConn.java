package com.duan.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConn {
	public static URLConnection getUrlConn(String url) {
		try {
			return getUrlConn(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static URLConnection getUrlConn(URL url) {
		try {
			URLConnection urlConn = url.openConnection();
			urlConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)");
			urlConn.setRequestProperty("Accept-Language", "zh-cn");
			urlConn.setConnectTimeout(6000);
			urlConn.setReadTimeout(6000);
			urlConn.setRequestProperty("Connection", "close");
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);
			return urlConn;
		} catch (MalformedURLException e) {
			System.out.println("url∏Ò Ω¥ÌŒÛ£°");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
