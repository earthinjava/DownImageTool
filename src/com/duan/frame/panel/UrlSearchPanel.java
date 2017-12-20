package com.duan.frame.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.duan.bean.DownBar;
import com.duan.frame.MainFrame;
import com.duan.parent.PageFrame;
import com.duan.parent.RButton;
import com.duan.utils.Constant;
import com.duan.utils.StrUtils;

public class UrlSearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;
	private JPanel taskPanel;
	private DownBar totalDownBar;
	private URL fistwaitDownLoadUrls;
	private String parseName;
	private PageFrame pageFrame;

	public UrlSearchPanel(MainFrame mainFrame, String parseName) {
		super();
		this.mainFrame = mainFrame;
		this.parseName = parseName;
		totalDownBar = new DownBar(20, 20, 360, 25);
		taskPanel = new JPanel();
		taskPanel.setBounds(10, 50, 375, 700);
		setLayout(null);
		taskPanel.setLayout(new FlowLayout());
		add(taskPanel);			
		setLocation(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
		setSize(400, 810);
		setVisible(true);
	}

	public DownBar getTotalDownBar() {
		return totalDownBar;
	}

	public void setTotalDownBar(DownBar totalDownBar) {
		this.totalDownBar = totalDownBar;
	}
	
	public String getParseName() {
		return parseName;
	}

	public void setParseName(String parseName) {
		this.parseName = parseName;
	}

	public void reFresh() {
		taskPanel.removeAll();
		revalidate();
		taskPanel.setLayout(new FlowLayout());
		for (int i = 0; i <= mainFrame.getWaitDownLoadUrls().size() - 1
				&& i <= 22; i++) {
			URL url = mainFrame.getWaitDownLoadUrls().get(i);
			JLabel name = new JLabel(StrUtils.getShortStr(url.toString(), 25));
			JButton delButton = new RButton("del");
			name.setPreferredSize(new Dimension(200, 25));
			delButton.setPreferredSize(new Dimension(60, 25));
			taskPanel.add(name);
			taskPanel.add(delButton);
			delButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainFrame.getWaitDownLoadUrls().remove(url);
					taskPanel.remove(name);
					taskPanel.remove(delButton);
					mainFrame.delOneTask();
				}
			});
		}
		this.setFocusable(false);
		setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {	
		super.paintComponent(g);
		Color color = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.drawRoundRect(1, 2, 393, 770, 15, 15);
		g.setColor(color);
		if (mainFrame.isFinishDown()) {
			totalDownBar.setProgressColor(Constant.FINISHI_COLOR);
		} 
		totalDownBar.setProgress(mainFrame.getProgress());
		totalDownBar.drawProgress(g, false, mainFrame.getTaskNumber(),
				mainFrame.getFinishedFilesPath().size());
		if (mainFrame.getWaitDownLoadUrls().size() != 0) {
			if (fistwaitDownLoadUrls != mainFrame.getWaitDownLoadUrls().get(0)) {
				fistwaitDownLoadUrls = mainFrame.getWaitDownLoadUrls().get(0);
				reFresh();
			}
		} else {
			taskPanel.removeAll();
		}

	}

	public PageFrame getPageFrame() {
		return pageFrame;
	}

	public void setPageFrame(PageFrame pageFrame) {
		this.pageFrame = pageFrame;
	}
	
}
