package com.duan.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.duan.bean.Page;
import com.duan.factory.UrlParseFactory;
import com.duan.intface.Parse;
import com.duan.parent.PageFrame;
import com.duan.parent.RButton;

public class Porn91PageFrame extends PageFrame {

	private static final long serialVersionUID = 1L;
	private Map<Integer, Page> pages;
	private List<JPanel> childJPanels;
	private Parse parse;
	private int pageNumber;
	private MainFrame mainFrame;
	private boolean isNumber;
	private JPanel buttonPanel;
	private Page nowPage;

	public Porn91PageFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		isNumber = true;
		pages = new HashMap<Integer, Page>();
		childJPanels = new ArrayList<JPanel>();
		this.parse = new UrlParseFactory(mainFrame.getuPanel().getParseName()).createParse();
		pageNumber = 1;
		Container container = this.getContentPane();
		container.setLayout(null);
		JPanel showJPanel = new JPanel();
		showJPanel.setLayout(null);
		showJPanel.setSize(656, 750);
		showJPanel.setLocation(14, 5);
		//showJPanel.setBackground(Color.GREEN);
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 4; j++) {
				JPanel childJPanel = new JPanel();
				childJPanel.setSize(120, 180);
				childJPanel.setLocation(j * 134, i * 190);
				childJPanel.setLayout(null);
				showJPanel.add(childJPanel);
				//childJPanel.setBackground(Color.RED);
				childJPanels.add(childJPanel);
			}
		}
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setSize(656, 30);
		buttonPanel.setLocation(14, 765);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		JLabel pageNumberLabel = new JLabel(pageNumber + "", JLabel.CENTER);
		pageNumberLabel.setSize(80, 30);
		pageNumberLabel.setLocation((buttonPanel.getWidth()-pageNumberLabel.getWidth())/2, 0);
		JButton lastButton = new RButton("上一页");
		lastButton.setSize(80, 30);
		lastButton.setLocation(pageNumberLabel.getX()-150, 0);
		JButton nextButton = new RButton("下一页");
		nextButton.setSize(80, 30);
		nextButton.setLocation(pageNumberLabel.getX()+150, 0);
		JButton downAllButton = new RButton("下载全部");
		downAllButton.setSize(90, 30);
		downAllButton.setLocation(nextButton.getX()+110, 0);
		buttonPanel.add(lastButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(pageNumberLabel);
		buttonPanel.add(downAllButton);
		container.add(showJPanel);
		container.add(buttonPanel);
		setSize(688, 830);
		setResizable(false);
		setTitle("91Porn");
		setLocation(200, 30);
		mainFrame.setLocation(getX() + getWidth(), getY());
		setVisible(true);
		downAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(nowPage!=null){							
							nowPage.downAllThread();
						}
					}
				}).start();

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.showUPanel();
				mainFrame.setPageFrame(null);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				mainFrame.hideUPanel();
			}
		});
		// 翻页功能
		pageNumberLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonPanel.remove(pageNumberLabel);
				isNumber = false;
				JTextField pageField = new JTextField();
				pageField.setSize(40, 20);
				pageField.setHorizontalAlignment(JTextField.CENTER);
				pageField.setLocation((buttonPanel.getWidth()-pageField.getWidth())/2, 5);
				buttonPanel.add(pageField);
				pageField.requestFocus();
				pageField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyChar() == KeyEvent.VK_ENTER) {
							String p = pageField.getText().trim();
							if (p.length() != 0) {
								pageNumber = Integer.parseInt(p);
								if (pageNumber <= 1100) {
									buttonPanel.remove(pageField);
									pageNumberLabel.setText(Integer
											.toString(pageNumber));
									buttonPanel.add(pageNumberLabel);
									repaint();
									isNumber = true;
									for (JPanel j : childJPanels) {
										j.removeAll();
										revalidate();
									}
									new ThreadChangePage(pageNumber).start();
								}
							}
						}
					}
				});
			}
		});
		// 下一页按钮
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageNumber <= 1100) {
					++pageNumber;
					pageNumberLabel.setText(pageNumber + "");
					for (JPanel j : childJPanels) {
						j.removeAll();
						j.repaint();
					}
					if (!isNumber) {
						buttonPanel.removeAll();
						revalidate();
						buttonPanel.add(pageNumberLabel);
						isNumber = true;
					}
					new ThreadChangePage(pageNumber).start();
				}
			}
		});
		// 上一页按钮
		lastButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageNumber > 1) {
					--pageNumber;
					pageNumberLabel.setText(pageNumber + "");
					for (JPanel j : childJPanels) {
						j.removeAll();
						j.repaint();
					}
					if (!isNumber) {
						buttonPanel.removeAll();
						revalidate();
						buttonPanel.add(pageNumberLabel);
						isNumber = true;
					}
					new ThreadChangePage(pageNumber).start();
				}
			}
		});
	}

	/**
	 * 加载page页面
	 * 
	 */
	public void launch() {
		launch(1);
	}

	/**
	 * 加载page页面
	 * 
	 * @param pageNumber
	 */
	public void launch(int pageNumber) {
		// 1.获得page对象
		// 若加载过直接获得此page
		nowPage = pages.get(pageNumber);
		if (nowPage == null) {			
			nowPage = parse.getPage(pageNumber, mainFrame);
			pages.put(pageNumber, nowPage);
		}
		// 2.显示page内容
		nowPage.show(childJPanels);
	}

	/**
	 * 换页线程
	 * 
	 * @author Administrator
	 *
	 */
	class ThreadChangePage extends Thread {
		private int pageNumber;

		public ThreadChangePage(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		@Override
		public void run() {
			launch(pageNumber);
		}
	}



}
