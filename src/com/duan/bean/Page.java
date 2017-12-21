package com.duan.bean;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.duan.down.downloader.ImageDownLoad;
import com.duan.frame.ImageShowFrame;
import com.duan.frame.MainFrame;
import com.duan.intface.DownFile;
import com.duan.intface.Parse;
import com.duan.utils.DownFileUtils;
import com.duan.utils.StrUtils;
import com.duan.utils.UUIDutils;

public class Page {

	private int pageNumber;
	private String url;
	private List<ChildPage> childPages;
	private MainFrame mainFrame;
	private Parse parse;

	public Page(int pageNumber, List<ChildPage> childPages, String url, MainFrame mainFrame,
			Parse parse) {
		this.pageNumber = pageNumber;
		this.url = url;
		this.childPages = childPages;
		this.mainFrame = mainFrame;
		this.parse = parse;
	}

	class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image img;

		public ImagePanel(Image img) {
			this.img = img;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, 120 - 110, 140 - 120, this);
		}
	}

	/**
	 * 下载预览图
	 * 
	 * @author Administrator
	 *
	 */
	class DownImgThread extends Thread {
		private JPanel childJPanel;
		private ChildPage childPage;
		private Image img;

		public DownImgThread(JPanel childJPanel, ChildPage childPage) {
			this.childJPanel = childJPanel;
			this.childPage = childPage;
			String imgpath = childPage.getPreviewImagePath();
			if (imgpath != null) {
				img = new ImageIcon(imgpath).getImage();
			}
		}

		@Override
		public void run() {
			URL url = null;
			try {
				// 若没下载过预览图则重新下载
				if (img == null) {
					// 若是91网站没有预览图地址，则先解析地址，在获得预览图及下载地址
					if (childPage.getPreviewImgUrl() == null) {
						url = new URL(childPage.getUrl());
						List<String> imgPaths = parse.getPathsFromUrl(url);
						if (imgPaths != null && imgPaths.size() != 0) {
							childPage.setPreviewImgUrl(imgPaths.get(0));
							for (String path : imgPaths) {
								childPage.getImgUrlPaths().add(path);
							}
						}
					}
					// 若是桃花网站有预览图地址则直接下载预览图
					if (childPage.getPreviewImgUrl() != null) {
						url = new URL(childPage.getPreviewImgUrl());
						DownFile file = DownFileUtils.getDownFile(UUIDutils.getID(),
								mainFrame.getSavePath() + "/catitle", url);
						if (new ImageDownLoad().download(file)) {
							img = new ImageIcon(file.getPath()).getImage();
							childPage.setPreviewImagePath(file.getPath());
						}
					}

				}
				JPanel imgPanel = new ImagePanel(img);
				imgPanel.addMouseListener(new MouseAdapter() {
					private ImageShowFrame isf;

					@Override
					public void mouseClicked(MouseEvent e) {
						if (img != null) {
							if (isf == null ) {
								isf = new ImageShowFrame(childPage.getDownFilesPath());
								isf.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										isf.dispose();
										isf = null;
									}
								});
							} else {
								isf.dispose();
								isf = null;
							}
						}
					}
				});
				imgPanel.setSize(110, 130);
				imgPanel.setLocation(5, 20);
				childJPanel.add(imgPanel);
				childJPanel.repaint();
			} catch (MalformedURLException e1) {
				System.out.println("预览图url格式错误");
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void show(List<JPanel> childJPanels) {
		for (int i = 0; i < getChildPages().size(); i++) {
			new ThreadAddChildPage(childJPanels, i).start();
		}
	}

	class ThreadAddChildPage extends Thread {
		private int i;
		private List<JPanel> childJPanels;

		public ThreadAddChildPage(List<JPanel> childJPanels, int i) {
			this.i = i;
			this.childJPanels = childJPanels;
		}

		@Override
		public void run() {
			ChildPage cPage = getChildPages().get(i);
			// 2.1设置标题
			String title = cPage.getTitle();
			JLabel titleJLabel = new JLabel(StrUtils.getShortStr(title, 15), JLabel.CENTER);
			titleJLabel.setSize(120, 20);
			titleJLabel.setLocation(0, 0);
			titleJLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(cPage.getUrl()));
					} catch (IOException e1) {
					} catch (URISyntaxException e1) {
						mainFrame.getTaskJPanel().getMsgArea().append(cPage.getUrl() + "格式错误！\r\n");
					}
				}
			});
			childJPanels.get(i).add(titleJLabel);
			// 2.2下载预览图
			new DownImgThread(childJPanels.get(i), getChildPages().get(i)).start();
			// 2.3设置下载按钮
			JButton downButton = cPage.getDownButton();
			downButton.setSize(80, 20);
			downButton.setLocation(20, 160);
			cPage.setDownButton(downButton);
			childJPanels.get(i).add(downButton);
			// 点击下载按钮，执行下载事件
			downButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							if (downButton.getText().equals("下载")) {
								cPage.downChildPage(parse, mainFrame);
							}
						}
					}).start();
				}
			});
			childJPanels.get(i).repaint();

		}
	}

	/**
	 * 单线程下载所有
	 */
	public void downAll() {
		for (ChildPage cPage : childPages) {
			cPage.downChildPage(parse, mainFrame);
		}
	}

	public void downAllThread() {
		for (ChildPage cPage : childPages) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					cPage.downChildPage(parse, mainFrame);
				}
			}).start();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ChildPage> getChildPages() {
		return childPages;
	}

	public void setChildPages(List<ChildPage> childPages) {
		this.childPages = childPages;
	}

}
