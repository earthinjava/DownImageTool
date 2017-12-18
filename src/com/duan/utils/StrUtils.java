package com.duan.utils;

public class StrUtils {
	public static String getShortStr(String str,int size){
		if(str.length()/2<=size&&str.length()>size){
			String shortStr=str.substring(0, size-3)+"...";
			return shortStr;
		}else if(str.length()/2>size){
			String shortStr=str.substring(0, (size-3)/2)+"..."+str.substring(str.length()-(size-3)/2-1);
			return shortStr;
		}else {
			return str;
		}		
	}
}
