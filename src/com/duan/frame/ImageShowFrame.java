package com.duan.frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class ImageShowFrame extends JFrame {

	private static final long serialVersionUID = -1130457258995441174L;
	private int width;
	private int height;
	private Image image;
	private JPanel showJPanel;
	private int index;
	private double zoom;
	private boolean isbig;

	/**
	 * 预览窗口的对象构造
	 * 
	 * @param image
	 */
	public ImageShowFrame(List<String> finishedFilesPath) {
		image = new ImageIcon(finishedFilesPath.get(finishedFilesPath.size() - 1)).getImage();
		if (finishedFilesPath.size() != 0) {
			index = finishedFilesPath.size() - 1;
		}
		setSize(image);
		setCenterLoaction();
		Container con = this.getContentPane();
		showJPanel = new ImagePanel();
		con.add(showJPanel);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int sfWidth = getWidth();
					int mouseX = e.getX();
					if (mouseX >= sfWidth / 2 && index < finishedFilesPath.size() - 1) {
						String nextfilePath = finishedFilesPath.get(index + 1);
						index++;
						refresh(nextfilePath);
					} else if (mouseX <= sfWidth / 2 && index > 0) {
						String lastfile = finishedFilesPath.get(index - 1);
						index--;
						refresh(lastfile);
					}
				} else if (!isbig) {
					width *= 2;
					height *= 2;
					setSize(width, height);
					setCenterLoaction();
					isbig = true;
				} else if (isbig) {
					width = (width / 2);
					height = (height / 2);
					setSize(width, height);
					setCenterLoaction();
					isbig = false;
				}

			}

		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && index < finishedFilesPath.size() - 1) {
					String nextfile = finishedFilesPath.get(index + 1);
					index++;
					refresh(nextfile);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT && index > 0) {
					String lastfile = finishedFilesPath.get(index - 1);
					index--;
					refresh(lastfile);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE
						&& index < finishedFilesPath.size() - 1) {
					String nextfile = finishedFilesPath.get(index + 1);
					index--;
					refresh(nextfile);
				}
			}
		});
		setVisible(true);
	}

	/**
	 * 根据图片文件设置窗口大小
	 */
	private void setSize(Image image) {
		if (image != null) {
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			width = imgWidth;
			height = imgHeight;
			if (width <= 400 && height <= 400) {
				zoom = ((400.00) / (double) (imgHeight)) < ((400.00) / (double) (imgWidth))
						? ((400.00) / (double) (imgHeight)) : ((400.00) / (double) (imgWidth));
				width *= zoom;
				height *= zoom;
			}
			setSize(width, height);
		}
	}

	class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, width, height, this);
		}
	}

	/**
	 * 设置居中显示
	 */
	public void setCenterLoaction() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int ScreenWidth = (int) screenSize.getWidth();
		int ScreenHeight = (int) screenSize.getHeight();
		int x = (ScreenWidth - width) / 2;
		int y = (ScreenHeight - height) / 2;
		setLocation(x, y);
	}

	public void refresh(String imgPath) {
		image = new ImageIcon(imgPath).getImage();
		setSize(image);
		setCenterLoaction();
		isbig = false;
		repaint();
	}

}
