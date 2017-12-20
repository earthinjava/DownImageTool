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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import com.duan.intface.DownFile;
import com.duan.parent.MyFrame;
import com.duan.utils.Constant;

public class ImageShowFrame extends MyFrame  {

	private static final long serialVersionUID = -1130457258995441174L;
	private int width;
	private int height;
	private DownFile file;
	private Image image;
	private int screenWidth = (int) (Constant.SCREEN_WIDTH * 0.9);
	private int screenHeigth = (int) (Constant.SCREEN_HEIGHT * 0.9);
	private boolean isOpen;
	private JPanel showJPanel;	
	private Image  showImage;	
	
	/**
	 * page预览窗口的对象构造
	 * 
	 * @param image
	 */
	public ImageShowFrame(Image img,List<String> finishedFilesPath) {	
		showImage=img;
		image = img;		
		setSize(image);
		isOpen = true;
		setCenterLoaction();
		Container con = this.getContentPane();
		showJPanel = new ImagePanel();
		con.add(showJPanel);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		isOpen = true;
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isOpen = false;
			}
		});		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int number = finishedFilesPath.indexOf(getFile());
				if (e.getKeyCode() == KeyEvent.VK_RIGHT
						&& number < finishedFilesPath.size() - 1) {
					String imgPath=finishedFilesPath.get(number + 1);					
					refresh(imgPath);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT && number > 0) {
					String imgPath = finishedFilesPath.get(number - 1);
					refresh(imgPath);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE
						&& number < finishedFilesPath.size() - 1) {
					String imgPath=finishedFilesPath.get(number + 1);
					refresh(imgPath);
				}else if (e.getKeyCode() == KeyEvent.VK_LEFT && number == 0) {					
					refresh(showImage);
				}
			}
		});
	}
	
	/**
	 * 预览窗口的对象构造
	 * 
	 * @param image
	 */
	public ImageShowFrame(List<DownFile> finishedFiles) {
		this.file=finishedFiles.get(finishedFiles.size()-1);
		image = new ImageIcon(file.getPath()).getImage();		
		setSize(image);
		isOpen = true;
		setCenterLoaction();
		Container con = this.getContentPane();
		showJPanel = new ImagePanel();
		con.add(showJPanel);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		isOpen = true;
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isOpen = false;
			}
		});	
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int number = finishedFiles.indexOf(getFile());
				int sfWidth = getWidth();
				int mouseX = e.getX();
				if (mouseX >= sfWidth / 2 && number < finishedFiles.size() - 1) {
					DownFile nextfile = finishedFiles.get(number + 1);
					refresh(nextfile);
				} else if (mouseX <= sfWidth / 2 && number > 0) {
					DownFile lastfile = finishedFiles.get(number - 1);
					refresh(lastfile);
				}
			}

		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int number = finishedFiles.indexOf(getFile());
				if (e.getKeyCode() == KeyEvent.VK_RIGHT
						&& number < finishedFiles.size() - 1) {
					DownFile nextfile = finishedFiles.get(number + 1);
					refresh(nextfile);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT && number > 0) {
					DownFile lastfile = finishedFiles.get(number - 1);
					refresh(lastfile);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE
						&& number < finishedFiles.size() - 1) {
					DownFile nextfile = finishedFiles.get(number + 1);
					refresh(nextfile);
				}
			}
		});
	}

	/**
	 * pageFrame的预览
	 * 
	 * @param image
	 */
	public ImageShowFrame(Image image) {
		this.image = image;
		setSize(image);
		isOpen = true;
		setCenterLoaction();
		Container con = this.getContentPane();
		showJPanel = new ImagePanel();
		con.add(showJPanel);
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		isOpen = true;
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isOpen = false;
			}
		});
	}	

	/**
	 * 根据图片文件设置窗口大小
	 */
	private void setSize(Image image) {
		if (image != null) {
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);

			if (imgWidth <= 400 && imgHeight <= 400) {
				double zoom = ((400.00) / (double) (imgHeight)) < ((400.00) / (double) (imgWidth))
						? ((400.00) / (double) (imgHeight)) : ((400.00) / (double) (imgWidth));
				imgWidth *= zoom;
				imgHeight *= zoom;
			}

			if (imgWidth > screenWidth && imgHeight <= screenHeigth) {
				double zoom = ((screenWidth) / (double) (imgWidth));
				width = screenWidth;
				height = (int) (imgHeight * zoom);
			} else if (imgHeight > screenHeigth && imgWidth <= screenWidth) {
				double zoom = ((screenHeigth) / (double) (imgHeight));
				height = screenHeigth;
				width = (int) (imgWidth * zoom);
			} else if (imgHeight > screenHeigth && imgWidth > screenWidth) {
				double zoom = ((screenHeigth) / (double) (imgHeight)) < ((screenWidth)
						/ (double) (imgWidth)) ? ((screenHeigth) / (double) (imgHeight))
								: ((screenWidth) / (double) (imgWidth));
				width = (int) (imgWidth * zoom);
				height = (int) (imgHeight * zoom);
			} else {
				width = imgWidth;
				height = imgHeight;
			}
			setSize(width, height);
		}
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public DownFile getFile() {
		return file;
	}

	public void setFile(DownFile file) {
		this.file = file;
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

	/**
	 * 根据传入文件更新窗口
	 * 
	 * @param downFile
	 */
	public void refresh(DownFile downFile) {
		setFile(downFile);
		image = new ImageIcon(downFile.getPath()).getImage();
		setTitle(downFile.getName() + "   " + downFile.getTime() + "   " + downFile.getSizeKB());
		setSize(image);
		setCenterLoaction();
		repaint();
	}
	
	public void refresh(String imgPath) {		
		image = new ImageIcon(imgPath).getImage();
		setSize(image);
		setCenterLoaction();
		repaint();
	}
	
	public void refresh(Image img) {
		setSize(showImage);
		image =showImage;
		setCenterLoaction();
		repaint();
	}

	@Override
	public void dispose() {
		super.dispose();
		setOpen(false);		
	}

}
