package com.duan.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class OptionMenu extends JMenuBar {

	private static final long serialVersionUID = -2325130940338460721L;
	private JMenu parseNaMenu;

	public OptionMenu(MainFrame mainFrame) {
		super();		
		setSize(812, 20);
		setLayout(null);
		JMenu pathMenu = new JMenu("Path");
		pathMenu.setLocation(0, 0);
		pathMenu.setSize(40, 20);
		JMenu parseMenu = new JMenu("Parse");
		parseMenu.setLocation(40, 0);
		parseMenu.setSize(50, 20);
		JMenu clearMenu = new JMenu("Clear");
		clearMenu.setLocation(90, 0);
		clearMenu.setSize(45, 20);
		JMenuItem taohuaMenuItem = new JMenuItem("TaoHua");
		JMenuItem pornMenuItem = new JMenuItem("91Porn");
		JMenuItem normalMenuItem = new JMenuItem("Normal");
		parseMenu.add(taohuaMenuItem);
		parseMenu.add(pornMenuItem);
		parseMenu.add(normalMenuItem);
		add(pathMenu);
		add(parseMenu);
		add(clearMenu);

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
