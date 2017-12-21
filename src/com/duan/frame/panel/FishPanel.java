package com.duan.frame.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.duan.frame.ImageShowFrame;
import com.duan.frame.MainFrame;

public class FishPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// 下载完的列表
	private List<String> finishFilesPath;
	// 预览窗口图片
	// private Image showPanelimage;
	// 预览窗口
	private ImageShowFrame isf;

	public FishPanel(MainFrame mainFrame) {
		finishFilesPath=mainFrame.getFinishedFilesPath();
		// setBackground(Color.BLACK);
		setSize(400, 260);
		// 鼠标单击打开关闭预览窗口事件
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isf == null) {
					isf = new ImageShowFrame(mainFrame.getFinishedFilesPath());
				} else {
					isf.dispose();
					isf = null;
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image showPanelimage = null;
		ImageIcon imageIcon=null;
		String finishPath =null;
		if (finishFilesPath.size() != 0) {
			finishPath = finishFilesPath.get(finishFilesPath.size() - 1);
			imageIcon=new ImageIcon(finishPath);			
			showPanelimage = imageIcon.getImage();
		}
		if (showPanelimage != null) {
			int width = showPanelimage.getWidth(null);
			int height = showPanelimage.getHeight(null);
			int showWidth = width;
			int showHeight = height;			
			double zoom = ((getHeight()) / (double) (height)) < ((getWidth()) / (double) (width))
					? ((getHeight()) / (double) (height)) : ((getWidth()) / (double) (width));
			showHeight = (int) (height * zoom);
			showWidth = (int) (width * zoom);
			if (showWidth <= 100 && showHeight <= 100) {
				zoom = ((100.00) / (double) (showHeight)) < ((100.00) / (double) (showWidth))
						? ((100.00) / (double) (showHeight)) : ((100.00) / (double) (showWidth));
				showWidth *= zoom;
				showHeight *= zoom;
			}
			int y = (getHeight() - showHeight) / 2;
			int x = (getWidth() - showWidth) / 2;
			g.drawImage(showPanelimage, x, y, showWidth, showHeight, this);
		}
		finishPath =null;
		showPanelimage = null;
		imageIcon=null;
	}

}
