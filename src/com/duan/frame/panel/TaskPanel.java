package com.duan.frame.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.duan.bean.ChildPage;
import com.duan.bean.DownBar;
import com.duan.down.core.DownThread;
import com.duan.frame.MainFrame;
import com.duan.parent.RButton;
import com.duan.utils.UUIDutils;

public class TaskPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private TaskListPanel tasklistPanel;
	private JPanel msgPanel;
	private JTextArea msgArea;
	private List<DownThread> downThreads;
	private MainFrame mainFrame;
	private ThreadAdd addDownThread;

	public TaskPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		downThreads = new CopyOnWriteArrayList<DownThread>();
		tasklistPanel = new TaskListPanel();
		tasklistPanel.setBounds(12, 0, 375, 390);
		msgPanel = new JPanel();
		msgPanel.setBounds(0, 390, 400, 90);
		msgArea = new JTextArea(4, 45);
		msgArea.setEditable(false);
		msgArea.setLineWrap(true);
		msgArea.setWrapStyleWord(true);
		msgArea.setFont(new Font("宋体", 10, 16));
		//tasklistPanel.setBackground(Color.CYAN);
		//msgPanel.setBackground(Color.GREEN);
		JScrollPane scrollPane = new JScrollPane(msgArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		tasklistPanel.setLayout(new FlowLayout());
		msgPanel.add(scrollPane);
		add(tasklistPanel);
		add(msgPanel);
		setSize(400, 480);
		// 信息始终显示最新
		msgArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				msgArea.setCaretPosition(msgArea.getText().length());

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				msgArea.setCaretPosition(msgArea.getText().length());

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				msgArea.setCaretPosition(msgArea.getText().length());

			}
		});		
		setVisible(true);
		addDownThread = new ThreadAdd();
		addDownThread.start();
	}

	/**
	 * 扫描下载线程是否未满
	 */
	class ThreadAdd extends Thread {
		@Override
		public synchronized void run() {
			while (true) {
				try {
					// 判断下载线程是否已满，未满则添加下载
					if (downThreads.size() < mainFrame.getDownNum()
							&& mainFrame.getWaitDownLoadUrls().size() != 0) {
						ChildPage childPage = mainFrame
								.getWaitDownLoadChildPage().get(
										mainFrame.getWaitDownLoadUrls().get(0));
						addDownThread(mainFrame.getWaitDownLoadUrls().get(0),
								UUIDutils.getID(), childPage);
						mainFrame.getWaitDownLoadUrls().remove(0);
					} else {
						ThreadAdd.this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 唤醒下载线程扫描
	 */
	public void notifyDownAdd() {
		synchronized (addDownThread) {
			addDownThread.notify();
		}
	}
	/**
	 * 移除一个下载
	 * @param down
	 */
	public  void removeDownThread(DownThread down) {
		downThreads.remove(down);
		tasklistPanel.remove(down.getBegButton());
		tasklistPanel.remove(down.getDelButton());
		tasklistPanel.remove(down.getNameLabel());
		for (int i = 0; i < downThreads.size(); i++) {
			int y = i * 30 + 5;
			DownThread downThread=downThreads.get(i);
			downThread.getNameLabel().setLocation(downThread.getNameLabel().getX(), y);
			downThread.getBegButton().setLocation(downThread.getBegButton().getX(), y);
			downThread.getDelButton().setLocation(downThread.getDelButton().getX(), y);
			downThread.getDownBar()
					.setY(downThread.getNameLabel().getY() + 2);
		}
		if (down.getDownBar().getProgress() < 1) {
			if (down.getFile() != null) {
				msgArea.append(down.getFile().getName() + "下载已删除！\r\n");
			}
		}
		mainFrame.getTaskJPanel().notifyDownAdd();	
		mainFrame.repaint();
	}

	public JTextArea getMsgArea() {
		return msgArea;
	}

	class TaskListPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (DownThread down : downThreads) {
				down.getDownBar().drawProgress(g, true);
				mainFrame.repaint();
			}
		}
	}

	public synchronized void addDownThread(URL url, String fileName,
			ChildPage childPage) {
		// 添加组件
		JButton begButton = new RButton("开始");
		begButton.setPreferredSize(new Dimension(65, 25));
		JButton delButton = new RButton("删除");
		delButton.setPreferredSize(new Dimension(65, 25));
		JLabel nameLabel = new JLabel();
		nameLabel.setPreferredSize(new Dimension(225, 25));
		int y = downThreads.size() * 30 + 5;
		nameLabel.setLocation(10, y);
		begButton.setLocation(237, y);
		delButton.setLocation(309, y);
		tasklistPanel.add(nameLabel);
		tasklistPanel.add(begButton);
		tasklistPanel.add(delButton);
		tasklistPanel.repaint();
		// 添加下载文件
		DownBar downBar = new DownBar(5, nameLabel.getY() + 2);
		DownThread downThread = new DownThread(downBar, fileName, url,
				begButton, delButton, nameLabel, mainFrame, childPage);
		// 开始下载线程
		downThreads.add(downThread);
		new Thread(downThread).start();
		begButton.setText("暂停");
		begButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (begButton.getText().equals("暂停")) {
					begButton.setText("开始");
					downThread.waitDown();
				} else if (begButton.getText().equals("开始")) {
					begButton.setText("暂停");
					downThread.startDown();
				} else if (begButton.getText().equals("重试")) {
					begButton.setText("暂停");
					downThread.reStartDown();
				}
			}
		});
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (DownThread down : downThreads) {
					if (down.getDelButton() == e.getSource()) {
						down.stopDown();
						if (down.isWait()) {
							down.startDown();
						}
						if (down != null) {
							removeDownThread(down);
							mainFrame.delOneTask();
						}
					}
				}
			}
		});		
	}

}
