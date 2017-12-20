package com.duan.down.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import com.duan.bean.DownBar;
import com.duan.utils.UUIDutils;

public class ImageScanner {	
	private JLabel msgLabel;
	private Map<MdFile, File> saveFiles;
	private List<File> sameFiles;
	private List<File> smallFiles;
	
	public ImageScanner(JLabel msgLabel) {
		this.msgLabel=msgLabel;		
		sameFiles = new ArrayList<File>();
		smallFiles = new ArrayList<File>();
		saveFiles = new HashMap<MdFile, File>();		
	}
	
	
	public List<File> findFiles(String path){
		File file = new File(path);
		File[] files = file.listFiles();
		List<File> fileList=null;
		if(files!=null){			
			fileList = new ArrayList<>();
			for (File f : files) {
				if (!f.isDirectory()) {
					fileList.add(f);
					if(msgLabel!=null){					
						msgLabel.setText("扫描到文件" + fileList.size() + "个");
					}
				}
			}
			if (fileList.size() == 0) {		
				msgLabel.setText("文件夹为空！");			
			}
		}else{
			msgLabel.setText("文件夹不存在！");
		}
		return fileList;
	}
	
	public void spiltFile(String path,List<File> fileList, DownBar downBar,int number){		
		String SavePath=path+"/"+UUIDutils.getID();
		if(fileList!=null){			
			for(int i=0,j=0;i<fileList.size();i++,j++){
				if(j==number){
					SavePath=path+"/"+UUIDutils.getID();
					j=0;
				}
				File savePathFile=new File(SavePath);
				if(!savePathFile.exists()){
					savePathFile.mkdirs();
				}
				String fileName=fileList.get(i).getName();
				String newSave=SavePath+"/"+fileName;
				File newFile=new File(newSave);
				msgLabel.setText("正在移动第" + (i+1) + "个，共" + fileList.size()+ "个");
				fileList.get(i).renameTo(newFile);
				double progress=0.0;
				if(fileList.size()!=0){				
					progress = (double)(i+1) / (double) fileList.size();
				}
				downBar.setProgress(progress);
			}		
		}
	}
	
	public void findSameAndSmallFiles(List<File> fileList, DownBar downBar){	
		int i = 0;
		if(fileList!=null){
			for (File f : fileList) {
				i++;
				msgLabel.setText("正在比较第" + i + "个，共" + fileList.size()+ "个");
				double progress=0.0;
				if(fileList.size()!=0){				
					progress = (double) i / (double) fileList.size();
				}
				downBar.setProgress(progress);
				MdFile mdFile = this.getMD5(f);
				if (mdFile!=null) {	
					if(saveFiles.containsKey(mdFile)){
						sameFiles.add(f);
					}else if(mdFile.length<=1024*20){
						smallFiles.add(f);
					}else{
						saveFiles.put(mdFile, f);
					}				
				}
			}				
			msgLabel.setText("找到" + sameFiles.size() + "个重复文件，"+ smallFiles.size() + "个小文件！");		
		}
	}	
	
	
	
	public int delFiles( DownBar downBar) {		
		int delNumber=sameFiles.size()+smallFiles.size();
		int j=0;
		for (File df : sameFiles) {
			if(df.delete()){
				j++;
				msgLabel.setText("删除:" + j + "/" + delNumber);
			}			
			double progress=0.0;
			if(delNumber!=0){				
				progress = (double) j / (double) delNumber;
			}			
			downBar.setProgress(progress);
		}
		for (File df : smallFiles) {
			if(df.delete()){
				j++;
				msgLabel.setText("删除:" + j + "/" + delNumber);
			}			
			double progress=0.0;
			if(delNumber!=0){				
				progress = (double) j / (double) delNumber;
			}			
			downBar.setProgress(progress);
		}
		msgLabel.setText("清理完成，删除" + j + "个文件！");
		return j;
	}	
	
	
	
	private static String toHex(byte[] bytes) {
		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
	
	private MdFile getMD5(File file) {
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(file);
			byte[] b1 = new byte[1024];
			int length = 0;		
			StringBuffer sb = new StringBuffer();
			while ((length = fos.read(b1)) > 0) {
				sb.append(new String(b1, 0, length));				
			}
			// 2.判断图片MD5值
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes1 = md5.digest(sb.toString().getBytes("utf-8"));
			String mdString = toHex(bytes1);			
			return new MdFile(mdString, file.length());
		} catch (FileNotFoundException e) {
			System.out.println("文件路径错误！");
		} catch (IOException e) {
			System.out.println("文件读取失败！");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	class MdFile {
		public String mdString;
		public long length;

		public MdFile(String md, long length) {
			this.mdString = md;
			this.length = length;
		}

		@Override
		public boolean equals(Object obj) {
			if (length == ((MdFile) obj).length
					&& ((MdFile) obj).mdString.equals(mdString)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return mdString.hashCode();
		}
	}

}
