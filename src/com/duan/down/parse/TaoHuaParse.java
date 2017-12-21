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
import com.duan.utils.UrlConn;

public class TaoHuaParse implements Parse {

	private String htmlContent;
	private MainFrame mainFrame;
	public TaoHuaParse(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
	}
	
	/**
	 * ���html�ı�����
	 * 
	 */
	private String getHtmlContent(URLConnection conn) {
		StringBuilder contextBuilder = new StringBuilder();
		InputStream is = null;
		int tryDownload = 1;
		while (tryDownload <= 3) {
			try {
				is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is, "UTF-8"));
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
	 * ��������ַ�л��ͼƬurl
	 * 
	 */
	@Override
	public List<String> getPathsFromUrl(URL url) {
		List<String> imgPaths = new ArrayList<String>();
		URLConnection con = UrlConn.getUrlConn(url);
		// �����õ�htmlContent
		htmlContent = getHtmlContent(con);
		// �����õ�paths
		if (htmlContent != null && htmlContent.contains("<img")) {
			String[] paths = htmlContent.split("<img");
			for (String p : paths) {
				if (p.contains("file=")) {
					String pt = p.split("file=")[1];
					pt = pt.substring(1);
					pt = pt.substring(0, pt.indexOf("\""));
					if (!pt.contains("http:") && !pt.contains("https:")) {
						pt = pt.substring(1);
						pt = mainFrame.getTaoHuaHost()+ pt;
					}
					System.out.println(pt);
					imgPaths.add(pt);
				}
			}
			return imgPaths;
		} else {
			return null;
		}
	}

	/**
	 * ��ҳ�룬���ش�ҳ����Ϣ
	 * 
	 */
	public Page getPage(int pageNumber, MainFrame mainFrame) {
		String url = mainFrame.getTaoHuaHost()+"forum-42-" + pageNumber + ".html";
		URLConnection con = UrlConn.getUrlConn(url);
		return new Page(pageNumber, getChildPages(con, pageNumber), url,
				mainFrame, this);
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public List<ChildPage> getChildPages(URLConnection con, int pageNumber) {
		List<ChildPage> childPages = new CopyOnWriteArrayList<ChildPage>();
		htmlContent = getHtmlContent(con);
		if (htmlContent != null && htmlContent.contains("waterfall")) {
			String pg = htmlContent.substring(htmlContent.indexOf("waterfall"));
			pg = pg.substring(0, pg.indexOf("</ul>"));
			String[] cps = pg.split("<li");
			for (String p : cps) {
				int index = 0;
				if (p.contains("c cl")) {
					p = p.substring(p.indexOf("<a"));
					if (p.contains("src=")) {
						String showImgPath = p.substring(p.indexOf("src="));
						p = p.substring(0, p.indexOf(">"));
						String href = p.substring(p.indexOf("="));
						String title = p.substring(p.indexOf("title="));
						href = href.substring(2);
						href = href.substring(0, href.indexOf("\""));
						href = mainFrame.getTaoHuaHost() + href;
						title = title.substring(7);
						title = title.substring(0, title.indexOf("\""));
						// 先查找数据看能不能找到，找的则不在创建
						showImgPath = showImgPath.substring(5);
						index++;
						showImgPath = showImgPath.substring(0,
								showImgPath.indexOf("\""));
						ChildPage childPage = new ChildPage(title, href,
								showImgPath, pageNumber, index);
						childPages.add(childPage);
					}
				}
			}
		}
		return childPages;
	}
}
