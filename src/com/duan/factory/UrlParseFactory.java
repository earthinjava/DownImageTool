package com.duan.factory;

import com.duan.down.parse.NormalParse;
import com.duan.down.parse.Parse91;
import com.duan.down.parse.TaoHuaParse;
import com.duan.frame.MainFrame;
import com.duan.intface.Parse;

public class UrlParseFactory {

	private Parse parse;

	public UrlParseFactory(MainFrame mainFrame) {
		String parseName = mainFrame.getuPanel().getParseName();
		if (parseName.equalsIgnoreCase("taohua")) {
			parse = new TaoHuaParse(mainFrame);
		} else if (parseName.equalsIgnoreCase("91porn")) {
			parse = new Parse91(mainFrame);
		} else {
			parse = new NormalParse();
		}
	}

	public Parse createParse() {
		return parse;
	}

}
