package com.duan.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	public static FileOutputStream getFos(String path) {
		FileOutputStream fos = null;
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			fos=new FileOutputStream(file);
			return fos;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeFos(fos);
		}
		return null;
	}

	public static void closeFos(FileOutputStream fos) {
		try {
			if (fos != null) {
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
