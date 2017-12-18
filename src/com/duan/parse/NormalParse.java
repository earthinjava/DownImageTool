package com.duan.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.duan.bean.Page;
import com.duan.frame.MainFrame;
import com.duan.intface.Parse;
import com.duan.utils.UrlConn;

public class NormalParse implements Parse {
	
	private List<String> imgPaths;
	private String htmlContent;

	public NormalParse() {
		imgPaths = new ArrayList<String>();
		htmlContent=new String();
	}	
	
	private void getHtmlContent(URLConnection conn) {
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
				htmlContent =new String(contextBuilder);				
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
	}	
	
	private  void parseHtml() {		
		String[] beginImg = htmlContent.split("<img");
		for (int i = 0; i < beginImg.length; i++) {			
			if (i >= 1 && beginImg[i].contains("original=")&& !beginImg[i].contains("src=")) {
				String beginsrc = beginImg[i].substring(beginImg[i]
						.indexOf("original="));
				if (beginsrc.contains(">")) {
					beginsrc = beginsrc.substring(0, beginsrc.indexOf(">"));
					if (beginsrc.contains("\"")) {
						beginsrc = beginsrc
								.substring(beginsrc.indexOf("\"") + 1);
						if (beginsrc.contains("\"")) {
							beginsrc = beginsrc.substring(0,
									beginsrc.indexOf("\""));
							if (!beginsrc.contains("http")
									&& beginsrc.contains("//")) {
								beginsrc = "http:" + beginsrc;
								imgPaths.add(beginsrc);
							} else if (beginsrc.contains("https")
									|| beginsrc.contains("http")) {
								imgPaths.add(beginsrc);
							}
						}
					}

				}
			} else if (i >= 1 && beginImg[i].contains("src=")) {
				String beginsrc = beginImg[i].substring(beginImg[i]
						.indexOf("src="));
				if (beginsrc.contains(">")) {
					beginsrc = beginsrc.substring(0, beginsrc.indexOf(">"));
					if (beginsrc.contains("\"")) {
						beginsrc = beginsrc
								.substring(beginsrc.indexOf("\"") + 1);
						if (beginsrc.contains("\"")) {
							beginsrc = beginsrc.substring(0,
									beginsrc.indexOf("\""));
							if (!beginsrc.contains("http")
									&& beginsrc.contains("//")) {
								beginsrc = "http:" + beginsrc;
								imgPaths.add(beginsrc);
							} else if (beginsrc.contains("https")
									|| beginsrc.contains("http")) {
								imgPaths.add(beginsrc);
							}
						}
					}
				}
			} else if (i >= 1 && beginImg[i].contains("file=")) {
				String beginsrc = beginImg[i].substring(beginImg[i]
						.indexOf("file="));
				if (beginsrc.contains(">")) {
					beginsrc = beginsrc.substring(0, beginsrc.indexOf(">"));
					if (beginsrc.contains("\"")) {
						beginsrc = beginsrc
								.substring(beginsrc.indexOf("\"") + 1);
						if (beginsrc.contains("\"")) {
							beginsrc = beginsrc.substring(0,
									beginsrc.indexOf("\""));
							if (!beginsrc.contains("http")
									&& beginsrc.contains("//")) {
								beginsrc = "http:" + beginsrc;
								imgPaths.add(beginsrc);
							} else if (beginsrc.contains("https")
									|| beginsrc.contains("http")) {
								imgPaths.add(beginsrc);
							}
						}
					}
				}

			}
		}		
	}

	
	@Override
	public List<String> getPathsFromUrl(URL url) {				
		getHtmlContent(UrlConn.getUrlConn(url));		
		parseHtml();
		return imgPaths;	
	}

	@Override
	public Page getPage(int pageNumber,MainFrame mainFrame) {
		// TODO Auto-generated method stub
		return null;
	}

}
