package com.duan.bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import com.duan.core.UrlParse;
import com.duan.frame.MainFrame;
import com.duan.intface.DownFile;
import com.duan.intface.Parse;
import com.duan.utils.RButton;

public class ChildPage {

	private String title;
	private String url;
	private String previewImgUrl;
	private int pageNumber;
	private int pageIndex;
	private String previewImagePath;
	private List<URL> imgPaths;
	private JButton downButton;
	private List<DownFile> downFiles;
	private int finishNumber;
	private int taskNumber;

	public List<DownFile> getDownFiles() {
		return downFiles;
	}

	public void setDownFiles(List<DownFile> downFiles) {
		this.downFiles = downFiles;
	}

	public ChildPage(String title, String url, String showImg, int pageNumber,
			int pageIndex) {
		this.title = title;
		this.url = url;
		this.pageNumber = pageNumber;
		this.pageIndex = pageIndex;
		downFiles = new ArrayList<DownFile>();
		setPreviewImgUrl(showImg);
	}

	public ChildPage(String title, String url, String showImg, int pageNumber,
			int pageIndex, List<String> imgPaths) {
		this.title = title;
		this.url = url;
		this.pageNumber = pageNumber;
		this.pageIndex = pageIndex;
		downFiles = new ArrayList<DownFile>();
		setPreviewImgUrl(showImg);
		this.imgPaths = new ArrayList<URL>();
		System.out.println(this.title);
		System.out.println(this.url);
		if (imgPaths != null && imgPaths.size() != 0) {
			for (String path : imgPaths) {
				try {
					URL u = new URL(path);
					this.imgPaths.add(u);
					System.out.println(u);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
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

	public List<URL> getImgPaths() {
		return imgPaths;
	}

	public void setImgPaths(List<URL> imgPaths) {
		this.imgPaths = imgPaths;
		if (imgPaths != null) {
			setTaskNumber(imgPaths.size());
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
		downFiles.add(downFile);
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
			if(imgPaths.size()==0){				
				setImgPaths(new UrlParse(parse).getPathsFromUrl(getUrl()));
			}else {
				setImgPaths(imgPaths);
			}
			if (imgPaths != null) {
				// 将下载url及子页添加到待下载队列中
				Map<URL, ChildPage> waitMap = mainFrame
						.getWaitDownLoadChildPage();
				for (URL url : imgPaths) {
					// 若添加成功，则返回null
					if (!waitMap.containsKey(url)) {
						waitMap.put(url, this);
						mainFrame.getWaitDownLoadUrls().add(url);
						mainFrame.addTaskNumber(1);
						mainFrame.getTaskJPanel().notifyDownAdd();
					} else {
						giveUpTask();
					}
				}
				downButton.setText("等待" + imgPaths.size());
				downButton.setEnabled(false);
			} else {
				mainFrame.getTaskJPanel().getMsgArea().append("无法解析到资源路径！\r\n");
				downButton.setText("无资源");
				downButton.setEnabled(false);
			}
		}
	}

}
