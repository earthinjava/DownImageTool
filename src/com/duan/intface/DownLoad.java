package com.duan.intface;

import javax.swing.JTextArea;

import com.duan.bean.DownBar;

public interface DownLoad {		
	public void stopDown();
	public void startDown();
	public void waitDown();
	public boolean download(DownFile file, DownBar downBar, JTextArea msgArea);	
	public boolean download(DownFile file);	
}
