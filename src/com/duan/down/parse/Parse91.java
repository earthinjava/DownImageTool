package com.duan.down.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.duan.bean.ChildPage;
import com.duan.bean.Page;
import com.duan.frame.MainFrame;
import com.duan.intface.Parse;
import com.duan.utils.Constant;
import com.duan.utils.UrlConn;

public class Parse91 implements Parse {
	private String htmlContent;

	/**
	 * 获得网页内容
	 * 
	 */
	private String getHtmlContent(URLConnection conn) {
		StringBuilder contextBuilder = new StringBuilder();
		InputStream is = null;
		int tryDownload = 1;
		while (tryDownload <= 3) {
			try {
				is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String msg = null;
				while ((msg = br.readLine()) != null) {
					contextBuilder.append(msg);
				}
				return new String(contextBuilder);
			} catch (IOException e) {
				tryDownload++;
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public List<String> getPathsFromUrl(URL url) {
		List<String> imgPaths = new ArrayList<String>();
		URLConnection con = UrlConn.getUrlConn(url);
		htmlContent = getHtmlContent(con);
		if (htmlContent != null && htmlContent.contains("<img")) {
			String[] paths = htmlContent.split("<img");
			for (String p : paths) {
				if (p.contains("file=")) {
					String pt = p.split("file=")[1];
					pt = pt.substring(1);
					pt = pt.substring(0, pt.indexOf("\""));
					if (!pt.contains("http:") && !pt.contains("https:")) {
						pt = Constant.PORN_91URL + pt;
					}
					imgPaths.add(pt);
				}
			}
			return imgPaths;
		} else {
			return null;
		}
	}

	@Override
	public Page getPage(int pageNumber, MainFrame mainFrame) {
		String url = Constant.PORN_91URL + "forumdisplay.php?fid=19&page=" + pageNumber;
		URLConnection con = UrlConn.getUrlConn(url);
		return new Page(pageNumber, getChildPages(con, pageNumber), url, mainFrame, this);
	}

	/**
	 * 
	 * @param con
	 * @param pageNumber
	 * @return
	 */
	public List<ChildPage> getChildPages(URLConnection con, int pageNumber) {
		List<ChildPage> childPages = new CopyOnWriteArrayList<ChildPage>();
		htmlContent = getHtmlContent(con);
		if (htmlContent != null && htmlContent.contains("<span id=\"thread_")) {
			String pg = htmlContent.substring(htmlContent.indexOf("<span id=\"thread_"));
			String[] cps = pg.split("<span id=\"thread_");
			for (String p : cps) {
				int index = 0;
				if (p.contains("<a")) {
					p = p.substring(p.indexOf("<a"));
					if (p.contains("href=")) {
						String href = p.substring(p.indexOf("href="));
						String title = p.substring(p.indexOf(">"));
						href = href.substring(6);
						href = href.substring(0, href.indexOf("\""));
						href = Constant.PORN_91URL + href;
						title = title.substring(1);
						title = title.substring(0, title.indexOf("</a>")).trim();
						// 先查找数据看能不能找到，找的则不在创建
						index++;
						ChildPage childPage = new ChildPage(title, href, null, pageNumber, index);
						childPages.add(childPage);
					}
				}
			}
		}
		return childPages;
	}
}
