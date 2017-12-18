package com.duan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDutils {
	public static String getID(){
		
		Date date=new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");	
		String uuid=sf.format(date);
		uuid+=UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 4);
		return uuid;
	}	
}
