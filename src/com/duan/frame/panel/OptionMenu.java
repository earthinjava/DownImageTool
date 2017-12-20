package com.duan.frame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.duan.frame.ClearFrame;
import com.duan.frame.MainFrame;
import com.duan.frame.SplitFrame;

public class OptionMenu extends JMenuBar {

	private static final long serialVersionUID = -2325130940338460721L;
	private JMenu parseNaMenu;

	public OptionMenu(MainFrame mainFrame) {
		super();
		setSize(812, 20);
		setLayout(null);
		JMenu parseMenu = new JMenu("Parse");
		parseMenu.setLocation(0, 0);
		parseMenu.setSize(50, 20);
		JMenu clearMenu = new JMenu("Clear");
		clearMenu.setLocation(50, 0);
		clearMenu.setSize(50, 20);
		JMenu splitMenu = new JMenu("Split");
		splitMenu.setLocation(100, 0);
		splitMenu.setSize(50, 20);
		JMenu settingMenu = new JMenu("Setting");
		settingMenu.setLocation(150, 0);
		settingMenu.setSize(50, 20);
		JMenuItem taohuaMenuItem = new JMenuItem("TaoHua");
		JMenuItem pornMenuItem = new JMenuItem("91Porn");
		JMenuItem normalMenuItem = new JMenuItem("Normal");
		JMenuItem savePathMenuItem = new JMenuItem("savePath");
		JMenuItem splitNumberMenuItem = new JMenuItem("splitNumber");
		// JMenuItem pornMenuItem = new JMenuItem("91Porn");
		// JMenuItem normalMenuItem = new JMenuItem("Normal");
		parseMenu.add(taohuaMenuItem);
		parseMenu.add(pornMenuItem);
		parseMenu.add(normalMenuItem);
		settingMenu.add(savePathMenuItem);
		settingMenu.add(splitNumberMenuItem);
		add(parseMenu);
		add(clearMenu);
		add(splitMenu);
		add(settingMenu);

		savePathMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null && file.isDirectory()) {
					mainFrame.setSavePath(file.getAbsolutePath());
					mainFrame.getTaskJPanel().getMsgArea()
							.append("保存路径已更改为：\r\n" + mainFrame.getSavePath() + "\r\n");
				}
			}
		});
		
		splitNumberMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置分割数量
				/*JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null && file.isDirectory()) {
					mainFrame.setSavePath(file.getAbsolutePath());
					mainFrame.getTaskJPanel().getMsgArea()
							.append("保存路径已更改为：\r\n" + mainFrame.getSavePath() + "\r\n");
				}*/
			}
		});
		clearMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (mainFrame.getClearFrame() == null) {
					ClearFrame clearFrame = new ClearFrame(mainFrame);
					mainFrame.setClearFrame(clearFrame);
				} else {
					mainFrame.getClearFrame().dispose();
					mainFrame.setClearFrame(null);
				}
			}
		});

		splitMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (mainFrame.getSplitFrame() == null) {
					SplitFrame splitFrame = new SplitFrame(mainFrame);
					mainFrame.setSplitFrame(splitFrame);
				} else {
					mainFrame.getSplitFrame().dispose();
					mainFrame.setSplitFrame(null);
				}
			}
		});

		taohuaMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addUPanel("taohua");
			}
		});
		normalMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addUPanel("normal");
			}
		});
		pornMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addUPanel("91porn");
			}
		});

	}

	public JMenu getParseNaMenu() {
		return parseNaMenu;
	}

	public void setParseNaMenu(JMenu parseNaMenu) {
		this.parseNaMenu = parseNaMenu;
	}

}
