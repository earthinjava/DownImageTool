package com.duan.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

import com.duan.intface.Parse;
import com.duan.parse.NormalParse;
import com.duan.parse.Parse91;
import com.duan.parse.TaoHuaParse;
import com.duan.utils.StrUtils;

public class UrlParse {

	private JTextArea msgArea;
	private Parse parse;	

	public UrlParse(JTextArea msgArea, String parseName) {		
		this.msgArea = msgArea;
		// 多态获得不同 解析器
		parse = getParse(parseName);
	}

	public UrlParse(String parseName) {		
		parse = getParse(parseName);
	}
	
	public UrlParse(Parse parse) {		
		this.parse=parse;
	}

	public static Parse getParse(String parseName) {
		if (parseName.equalsIgnoreCase("taohua")) {
			return new TaoHuaParse();
		} else if (parseName.equalsIgnoreCase("91porn")) {
			return new Parse91();
		} else {
			return new NormalParse();
		}
	}

	public synchronized List<URL> getPathsFromUrl(String path) {
		List<URL> urlList = new ArrayList<URL>();		
		// 调用解析器获得资源地址
		URL u;
		try {
			u = new URL(path);
		} catch (MalformedURLException e1) {
			if (msgArea != null) {
				msgArea.append(path + "不是一个url格式！\r\n");
			}
			return null;
		}
		List<String> pathlist = parse.getPathsFromUrl(u);
		String shortPath = path;
		shortPath = StrUtils.getShortStr(path, 20);
		if (pathlist != null && pathlist.size() != 0) {
			if (msgArea != null) {
				msgArea.append("从" + shortPath + "获得" + pathlist.size() + "个如下资源：\r\n");
			}
			int i = 1;
			Iterator<String> iterator=pathlist.iterator();			
			while(iterator.hasNext()){
				String p=iterator.next();
				if (msgArea != null) {
					msgArea.append("  " + i + "." + StrUtils.getShortStr(p, 35) + "\r\n");
				}
				i++;
				URL url = null;
				try {
					url = new URL(p);
					urlList.add(url);
				} catch (MalformedURLException e) {
					if (msgArea != null) {
						msgArea.append(StrUtils.getShortStr(p, 35) + ":格式有误！\r\n");
					}
				}
			
			}			
			return urlList;
		} else {
			if (msgArea != null) {
				msgArea.append("从" + shortPath + "无法解析出资源!\r\n");
			}
		}
		return null;
	}

}
