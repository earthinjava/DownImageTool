package com.duan.factory;

import com.duan.frame.MainFrame;
import com.duan.frame.Porn91PageFrame;
import com.duan.frame.TaoHuaPageFrame;
import com.duan.parent.PageFrame;

public class PageFrameFactory {
	//可以改为反射获取
	public static PageFrame creatPageFrame(MainFrame mainFrame) {
		if (mainFrame.getuPanel().getParseName().contains("taohua")) {
			return new TaoHuaPageFrame(mainFrame);
		} else if (mainFrame.getuPanel().getParseName().contains("porn")) {
			return new Porn91PageFrame(mainFrame);
		} else {
			return null;
		}
	}
}
