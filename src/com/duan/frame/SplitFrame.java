package com.duan.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.duan.bean.DownBar;
import com.duan.down.core.ImageScanner;
import com.duan.parent.RButton;
import com.duan.utils.Constant;

public class SplitFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JLabel msgLabel;
	private DownBar downBar;
	private ImageScanner imageScanner;
	private List<File> filelist;
	private int splitNumber;
	
	public SplitFrame(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		setTitle("Split");
		splitNumber=1500;
		setSize(400, 100);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
		setResizable(false);
		Container container = getContentPane();
		container.setLayout(null);
		JPanel msgPanel = new JPanel();
		//msgPanel.setBackground(Color.RED);
		msgPanel.setSize(400, 30);
		msgPanel.setLocation(0, 5);
		msgLabel = new JLabel("文件扫描中......");
		msgLabel.setFont(new Font("宋体", 10, 16));
		msgLabel.setLocation(0, 0);
		msgLabel.setSize(400, 70);
		imageScanner = new ImageScanner(msgLabel);
		msgPanel.add(msgLabel);
		container.add(msgPanel);		
		JPanel proPanel=new ProPanel();
		proPanel.setSize(400, 30);
		proPanel.setLocation(0, 40);
		downBar = new DownBar(10, 0, 280, 25);
		downBar.setProgressColor(Constant.ISLOADING_COLOR);
		JButton splitButton = new RButton("Split");
		splitButton.setSize(70, 25);
		splitButton.setLocation(300, 0);
		proPanel.setLayout(null);
		proPanel.add(splitButton);
		//proPanel.setBackground(Color.GREEN);
		container.add(proPanel);
		setBackground(new Color(0, 255, 255));
		setVisible(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				start();
			}
		}).start();
		splitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (splitButton.getText().equals("Split")) {
					splitButton.setEnabled(false);
					new Thread(new Runnable() {						
						@Override
						public void run() {
							imageScanner.spiltFile(mainFrame.getSavePath(),filelist, downBar,splitNumber);
						}
					}).start();
				} 
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				mainFrame.setClearFrame(null);
			}
		});
	}

	public int getSplitNumber() {
		return splitNumber;
	}

	public void setSplitNumber(int splitNumber) {
		this.splitNumber = splitNumber;
	}

	public void start() {
		filelist = imageScanner.findFiles(mainFrame.getSavePath());
	}	
	
	class ProPanel extends JPanel{		
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {		
			super.paintComponent(g);
			downBar.drawProgress(g, false, false);
			repaint();
		}
	}

}
