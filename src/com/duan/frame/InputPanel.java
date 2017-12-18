package com.duan.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.duan.core.UrlParse;
import com.duan.utils.RButton;

public class InputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JTextField pathField;
	private JButton addButton;
	private JTextArea msgArea;

	public InputPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.msgArea = mainFrame.getTaskJPanel().getMsgArea();
		setSize(375, 35);
		pathField = new JTextField(37);
		pathField.setFont(new Font("宋体", 10, 16));
		addButton = new RButton("添加");
		addButton.setPreferredSize(new Dimension(65, 25));
		setLayout(new FlowLayout());
		add(pathField);
		add(addButton);
		addButton.addActionListener(new AddButtonActionListener());
		// 回车按钮事件
		pathField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					addButton.doClick();
				}
			}
		});
	}

	private class AddButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathField.getText().trim();
			// 没打开解析器，正常下载
			if (mainFrame.getuPanel() == null) {
				if (path.length() != 0) {
					try {
						URL url = new URL(path);
						mainFrame.getWaitDownLoadUrls().add(url);
						mainFrame.addTaskNumber(1);
						mainFrame.getTaskJPanel().notifyDownAdd();
					} catch (MalformedURLException e1) {
						msgArea.append(pathField.getText().trim()
								+ "：URL格式错误！\r\n");
					}
					pathField.setText("");
					pathField.requestFocus();
				}
				// 打开解析器，解析下载
			} else {
				if (path.length() != 0) {
					// 若要求解析，则继续解析
					if (mainFrame.getPageFrame() == null
							&& path.equalsIgnoreCase("parse")) {
						pathField.setText("");
						mainFrame.hideUPanel();
						PageFrame pageFrame = new PageFrame(mainFrame);
						mainFrame.setPageFrame(pageFrame);
						new Thread(new Runnable() {
							@Override
							public void run() {
								pageFrame.launch();
							}
						}).start();
						pathField.setText("");
						pathField.requestFocus();
						// 直接解析
					} else {
						pathField.setText("");
						pathField.requestFocus();
						// 开启解析线程
						new Thread(new Runnable() {
							@Override
							public void run() {
								// 调用图片url解析器
								UrlParse urlParse = new UrlParse(msgArea,
										mainFrame.getuPanel().getParseName());
								List<URL> urlList = urlParse
										.getPathsFromUrl(path);
								if (urlList != null) {
									mainFrame.addTaskNumber(urlList.size());
									mainFrame.getWaitDownLoadUrls().addAll(
											urlList);
									mainFrame.getTaskJPanel().notifyDownAdd();
								} else {
									msgArea.append("无法解析到资源路径！\r\n");
								}
							}
						}).start();
					}
				}
			}
		}
	}

	public JTextField getPathField() {
		return pathField;
	}

	public void setPathField(JTextField pathField) {
		this.pathField = pathField;
	}
}
