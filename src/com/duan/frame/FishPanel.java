package com.duan.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.duan.intface.DownFile;

public class FishPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// 下载完的列表
	private List<DownFile> finishFiles;
	// 预览窗口图片
	private Image showPanelimage;
	// 预览窗口
	private ImageShowFrame isf;

	public FishPanel(MainFrame mainFrame) {
		finishFiles = mainFrame.getFinishedFiles();
		setBackground(Color.WHITE);
		setSize(400, 290);
		// 鼠标单击打开关闭预览窗口事件
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (showPanelimage != null) {
					if (isf == null) {
						isf = new ImageShowFrame(finishFiles);
					} else {
						isf.dispose();
						isf = null;
					}
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (finishFiles.size() != 0) {
			showPanelimage = new ImageIcon(finishFiles.get(finishFiles.size() - 1).getPath())
					.getImage();
		}
		if (showPanelimage != null) {
			int width = showPanelimage.getWidth(null);
			int height = showPanelimage.getHeight(null);
			int showWidth = width;
			int showHeight = height;
			int x = 0;
			int y = 0;
			if (width > 400 && height <= 280) {
				double zoom = ((400.00) / (double) (width));
				showHeight = (int) (height * zoom);
			} else if (height > 280 && width <= 400) {
				double zoom = ((280.00) / (double) (height));
				showWidth = (int) (width * zoom);
			} else if (height > 280 && width > 400) {
				double zoom = ((280.00) / (double) (height)) < ((400.00) / (double) (width))
						? ((280.00) / (double) (height)) : ((400.00) / (double) (width));
				showHeight = (int) (height * zoom);
				showWidth = (int) (width * zoom);
			}
			if (showWidth <= 100 && showHeight <= 100) {
				double zoom = ((100.00) / (double) (showHeight)) < ((100.00) / (double) (showWidth))
						? ((100.00) / (double) (showHeight)) : ((100.00) / (double) (showWidth));
				showWidth *= zoom;
				showHeight *= zoom;
			}
			y = (280 - showHeight) / 2;
			x = (400 - showWidth) / 2;
			g.drawImage(showPanelimage, x, y, showWidth, showHeight, this);
		}
	}

}
