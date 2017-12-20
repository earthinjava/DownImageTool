package com.duan.factory;

import com.duan.down.parse.NormalParse;
import com.duan.down.parse.Parse91;
import com.duan.down.parse.TaoHuaParse;
import com.duan.intface.Parse;

public class UrlParseFactory {

	private Parse parse;

	public UrlParseFactory(String parseName) {
		this.parse = getParse(parseName);
	}

	// 可以改为反射获取
	private Parse getParse(String parseName) {
		if (parseName.equalsIgnoreCase("taohua")) {
			return new TaoHuaParse();
		} else if (parseName.equalsIgnoreCase("91porn")) {
			return new Parse91();
		} else {
			return new NormalParse();
		}
	}

	public Parse createParse() {
		return parse;
	}

}
