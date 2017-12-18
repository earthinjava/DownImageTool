package com.duan.download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.swing.JTextArea;

import com.duan.bean.DownBar;
import com.duan.intface.DownFile;
import com.duan.intface.DownLoad;

public class ImageDownLoad implements DownLoad {

	private boolean stop;
	private boolean wait;

	@Override
	public void stopDown() {
		stop = true;
	}

	@Override
	public synchronized void startDown() {
		wait = false;
		notify(); 
	}

	@Override
	public void waitDown() {
		wait = true;
	}

	@Override
	public synchronized boolean download(DownFile file, DownBar downBar, JTextArea msgArea) {
		InputStream is = null;
		FileOutputStream fos = null;
		double fileLength = file.getSize();
		double downLength = 0;
		int i = 1;
		URLConnection con = file.getCon();
		while (i <= 3) {
			try {
				is = con.getInputStream();
				file.creatNewFile();
				fos = new FileOutputStream(file.getFile());
				byte[] bytes = new byte[102400];
				int length;
				while (!stop && (length = is.read(bytes)) > 0) {
					if (wait && !Thread.interrupted()) {
						wait();
					}
					fos.write(bytes, 0, length);
					downLength += length;
					if (fileLength != 0 && downLength <= fileLength) {
						downBar.setProgress(downLength / fileLength);
					}
				}
				if (downLength < fileLength || downLength == 0) {
					msgArea.append(file.getFile().getName() + "文件未下载完\r\n");
					return false;
				} else {
					if(file.getFile()!=null&&file.getFile().getName()!=null){						
						msgArea.append(file.getFile().getName() + "下载完成\r\n");
					}
					return true;
				}
			} catch (IOException e) {
				msgArea.append(file.getName() + "进行第" + i + "次尝试！\r\n");
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				closeStream(fos, is);
			}
		}
		msgArea.append(file.getName() + "下载失败！\r\n");
		return false;
	}

	public void closeStream(FileOutputStream fos, InputStream is) {
		try {
			if (fos != null) {
				fos.close();
				fos = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (is != null) {
				is.close();
				is = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean download(DownFile file) {
		InputStream is = null;
		FileOutputStream fos = null;
		int i = 1;
		URLConnection con = file.getCon();
		while (i <= 3) {
			try {
				is = con.getInputStream();
				file.creatNewFile();
				fos = new FileOutputStream(file.getFile());
				byte[] bytes = new byte[102400];
				int length;
				while ((length = is.read(bytes)) > 0) {
					fos.write(bytes, 0, length);
				}
				return true;
			} catch (IOException e) {
				i++;
			} finally {
				closeStream(fos, is);
			}
		}
		return false;
	}

}
