package com.duan.intface;

import java.net.URL;
import java.util.List;

import com.duan.bean.Page;
import com.duan.frame.MainFrame;

/**
 * ��ͬ��վ�������ӿ�
 * @author Administrator
 *
 */
public interface Parse {	
	List<String> getPathsFromUrl(URL url);
	public Page getPage(int pageNumber,MainFrame mainFrame);
	
}
