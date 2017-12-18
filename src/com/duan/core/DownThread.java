package com.duan.core;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.duan.bean.ChildPage;
import com.duan.bean.DownBar;
import com.duan.download.ImageDownLoad;
import com.duan.download.VideoDownLoad;
import com.duan.frame.MainFrame;
import com.duan.intface.DownFile;
import com.duan.intface.DownLoad;
import com.duan.utils.Constant;
import com.duan.utils.StrUtils;

public class DownThread implements Runnable {

	private MainFrame mainFrame;
	private DownBar downBar;
	private JButton begButton;
	private JButton delButton;
	private DownFile file;
	private JLabel nameLabel;
	private DownLoad downLoad;
	private boolean wait;
	private String fileName;
	private String savePath;
	private URL url;
	private ChildPage childPage;	

	/**
	 * 手动添加下载的构造器
	 * 
	 * @param downBar
	 * @param fileName
	 * @param savePath
	 * @param url
	 * @param begButton
	 * @param delButton
	 * @param nameLabel
	 * @param mainFrame
	 */
	public DownThread(DownBar downBar, String fileName, URL url, JButton begButton,
			JButton delButton, JLabel nameLabel, MainFrame mainFrame, ChildPage childPage) {
		this.downBar = downBar;
		this.fileName = fileName;
		this.url = url;
		this.begButton = begButton;
		this.setDelButton(delButton);
		this.setNameLabel(nameLabel);
		this.mainFrame = mainFrame;
		this.childPage = childPage;
		savePath = mainFrame.getSavePath();
	}

	/**
	 * 初始化下载器
	 * 
	 * @param contentType
	 */
	private void setDownLoad(String contentType) {
		if (contentType != null && contentType.contains("video")) {
			downLoad = new VideoDownLoad();
		} else {
			downLoad = new ImageDownLoad();
		}
	}

	public void reStartDown() {
		downBar.setProgressColor(Constant.ISLOADING_COLOR);
		downBar.setProgress(0.00);
		new Thread(this).start();
	}

	/**
	 * 放弃下载
	 */
	public void stopDown() {
		if (downLoad != null) {
			downLoad.stopDown();
		}
	}

	public void startDown() {
		if (downLoad != null) {
			downLoad.startDown();
		}
	}

	public void waitDown() {
		if (downLoad != null) {
			downLoad.waitDown();
		}
	}

	private void finishDown() {
		try {
			begButton.setText("完成");
			if (childPage != null) {
				childPage.finishTask(file);
			}
			downBar.setProgressColor(Constant.FINISHI_COLOR);
			begButton.setEnabled(false);
			mainFrame.getFinishedFiles().add(file);
			Thread.sleep(200);
			mainFrame.getTaskJPanel().removeDownThread(this);
		} catch (InterruptedException e) {
		}
	}

	private void giveupDown() {
		mainFrame.getTaskJPanel().removeDownThread(this);
		mainFrame.delOneTask();
		if (childPage != null) {
			childPage.giveUpTask();
		}
		mainFrame.getTaskJPanel().getMsgArea()
				.append(StrUtils.getShortStr(url.toString(), 25) + ":无法下载！\r\n");
	}

	@Override
	public void run() {
		try {
			// 连接网络获得contenttype
			file = FileParse.getDownFile(fileName, savePath, url);
			nameLabel.setText(file.getSizeKB());
			// 无法获得文件删除此文件
			if (file == null) {
				giveupDown();
				return;
			}
			// 初始化下载器
			setDownLoad(file.getContentType());
			// 下载成功返回
			if (downLoad.download(file, downBar, mainFrame.getTaskJPanel().getMsgArea())) {
				finishDown();
				return;
			} else {
				begButton.setText("重试");
				downBar.setProgressColor(Constant.FAILD_COLOR);
				// 下载失败尝试重新连接
				int tryagain = 1;
				while (tryagain <= 3) {
					file = FileParse.getDownFile(fileName, savePath, url);
					nameLabel.setText(file.getSizeKB());
					// 无法获得文件删除此文件
					if (file == null) {
						giveupDown();
						return;
					}
					// 初始化下载器
					setDownLoad(file.getContentType());
					if (downLoad.download(file, downBar, mainFrame.getTaskJPanel().getMsgArea())) {
						finishDown();
						return;
					} else {
						if (file.getFile() != null) {
							file.getFile().delete();
						}
						tryagain++;
					}
				}
				Thread.sleep(1000);
				if (begButton.getText().equals("重试")) {
					giveupDown();
				}
			}
		} catch (InterruptedException e) {
			mainFrame.getTaskJPanel().getMsgArea().append(fileName + ":无法删除临时文件！\r\n");
		}
	}

	public DownBar getDownBar() {
		return downBar;
	}

	public void setDownBar(DownBar downBar) {
		this.downBar = downBar;
	}

	public JButton getDelButton() {
		return delButton;
	}

	public void setDelButton(JButton delButton) {
		this.delButton = delButton;
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}

	public DownFile getFile() {
		return file;
	}

	public void setFile(DownFile file) {
		this.file = file;
	}

	public JButton getBegButton() {
		return begButton;
	}

	public void setBegButton(JButton begButton) {
		this.begButton = begButton;
	}

	public DownLoad getDownLoad() {
		return downLoad;
	}

	public void setDownLoad(DownLoad downLoad) {
		this.downLoad = downLoad;
	}
}
