package com.duan.bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import com.duan.frame.MainFrame;
import com.duan.intface.DownFile;
import com.duan.intface.Parse;
import com.duan.parent.RButton;

public class ChildPage {

	private String title;
	private String url;
	private String previewImgUrl;
	private int pageNumber;
	private int pageIndex;
	private String previewImagePath;
	private List<String> imgUrlPaths;
	private JButton downButton;
	private List<String> downFilesPath;
	private int finishNumber;
	private int taskNumber;

	public List<String> getDownFilesPath() {
		return downFilesPath;
	}

	public void setDownFilesPath(List<String> downFilesPath) {
		this.downFilesPath = downFilesPath;
	}

	public ChildPage(String title, String url, String showImg, int pageNumber,
			int pageIndex) {
		this.title = title;
		this.url = url;
		this.pageNumber = pageNumber;
		this.pageIndex = pageIndex;
		imgUrlPaths=new ArrayList<String>();
		downFilesPath = new ArrayList<String>();
		setPreviewImgUrl(showImg);
	}

	public ChildPage(String title, String url) {
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreviewImgUrl() {
		return previewImgUrl;
	}

	public void setPreviewImgUrl(String previewImgUrl) {
		this.previewImgUrl = previewImgUrl;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<String> getImgUrlPaths() {
		return imgUrlPaths;
	}

	public void setImgUrlPaths(List<String> imgUrlPaths) {
		this.imgUrlPaths = imgUrlPaths;
		if (imgUrlPaths != null) {
			setTaskNumber(imgUrlPaths.size());
		}
	}	

	public JButton getDownButton() {
		if (downButton == null) {
			downButton = new RButton("下载");
		}
		return downButton;
	}

	public void setDownButton(JButton downButton) {
		this.downButton = downButton;
	}

	public synchronized void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public synchronized int getFinishNumber() {
		return finishNumber;
	}

	public synchronized void setFinishNumber(int finishNumber) {
		this.finishNumber = finishNumber;
	}

	public synchronized void giveUpTask() {
		if (taskNumber > 0) {
			taskNumber--;
		}
		showTaskProgress();
	}

	public void showTaskProgress() {
		if (finishNumber < taskNumber || taskNumber == 0) {
			downButton.setText(finishNumber + "/" + taskNumber);
		} else {
			downButton.setText("完成: " + finishNumber);
		}
	}

	public synchronized void finishTask(DownFile downFile) {
		downFilesPath.add(downFile.getPath());
		finishNumber++;
		showTaskProgress();
	}	
	
	public String getPreviewImagePath() {
		return previewImagePath;
	}

	public void setPreviewImagePath(String previewImagePath) {
		this.previewImagePath = previewImagePath;
	}

	public void downChildPage(Parse parse, MainFrame mainFrame) {		
		if (downButton.getText().equals("下载")) {
			if (getUrl() == null) {
				return;
			}
			// 获得paths
			if(imgUrlPaths.size()==0){		
				URL url;
				try {
					url = new URL(getUrl());
					setImgUrlPaths(parse.getPathsFromUrl(url));
				} catch (MalformedURLException e) {					
					mainFrame.getTaskJPanel().getMsgArea().append(getUrl()+"：子贴URL错误！\r\n");
				}
			}else {
				setImgUrlPaths(imgUrlPaths);
			}
			if (imgUrlPaths != null) {
				// 将下载url及子页添加到待下载队列中
				Map<URL, ChildPage> waitMap = mainFrame
						.getWaitDownLoadChildPage();
				for (String url : imgUrlPaths) {
					// 若添加成功，则返回null
					if (!waitMap.containsKey(url)) {
						URL u;
						try {
							u = new URL(url);							
							waitMap.put(u, this);
							mainFrame.getWaitDownLoadUrls().add(u);
							mainFrame.addTaskNumber(1);
							mainFrame.getTaskJPanel().notifyDownAdd();
						} catch (MalformedURLException e) {
							mainFrame.getTaskJPanel().getMsgArea().append(url+"：图片URL错误！\r\n");
							giveUpTask();
						}
					} else {
						giveUpTask();
					}
				}
				downButton.setText("等待" + imgUrlPaths.size());
				downButton.setEnabled(false);
			} else {
				mainFrame.getTaskJPanel().getMsgArea().append("无法解析到资源路径！\r\n");
				downButton.setText("无资源");
				downButton.setEnabled(false);
			}
		}
	}

}
