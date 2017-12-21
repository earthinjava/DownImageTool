package com.duan.frame;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.duan.parent.RButton;

public class SettingFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public SettingFrame(MainFrame mainFrame) {
		setSize(400, 290);
		Container container = this.getContentPane();
		container.setLayout(null);
		JPanel savePathPanel = new JPanel();
		savePathPanel.setSize(getWidth(), 30);
		savePathPanel.setLocation(0, 0);
		// savePathPanel.setBackground(Color.RED);
		savePathPanel.setLayout(null);
		JLabel savePathLabel = new JLabel("SavePath:");
		savePathLabel.setSize(70, 30);
		savePathLabel.setLocation(0, 0);
		savePathPanel.add(savePathLabel);
		JTextField pathField = new JTextField();
		pathField.setFont(new Font("宋体", 10, 12));
		pathField.setText(mainFrame.getSavePath());
		pathField.setSize(240, 20);
		pathField.setLocation(70, 5);
		savePathPanel.add(pathField);
		JButton pathChangeButton = new RButton("修改");
		pathChangeButton.setSize(60, 20);
		pathChangeButton.setLocation(320, 5);
		savePathPanel.add(pathChangeButton);
		pathChangeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null && file.isDirectory()) {
					pathField.setText(file.getAbsolutePath());
				}
			}
		});

		JPanel downNumPanel = new JPanel();
		downNumPanel.setSize(getWidth(), 30);
		downNumPanel.setLocation(0, 40);
		// downNumPanel.setBackground(Color.RED);
		downNumPanel.setLayout(null);
		JLabel downNumLabel = new JLabel("DownNum:");
		downNumLabel.setSize(80, 30);
		downNumLabel.setLocation(0, 0);
		downNumPanel.add(downNumLabel);
		JSlider downNumberSlider = new JSlider(JSlider.HORIZONTAL, 1, 12, 12);
		downNumberSlider.setSize(240, 20);
		downNumberSlider.setLocation(70, 5);
		downNumPanel.add(downNumberSlider);
		JLabel downNumberLabel = new JLabel("12");
		downNumberLabel.setSize(30, 20);
		downNumberLabel.setLocation(330, 2);
		downNumPanel.add(downNumberLabel);
		downNumberSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				downNumberLabel.setText(downNumberSlider.getValue() + "");

			}
		});

		JPanel sizeDelPanel = new JPanel();
		sizeDelPanel.setSize(getWidth(), 30);
		sizeDelPanel.setLocation(0, 80);
		// sizeDelPanel.setBackground(Color.RED);
		sizeDelPanel.setLayout(null);
		JLabel sizeDelLabel = new JLabel("DelSize:");
		sizeDelLabel.setSize(70, 30);
		sizeDelLabel.setLocation(0, 0);
		sizeDelPanel.add(sizeDelLabel);
		JSlider sizeDelSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 30);
		sizeDelSlider.setSize(240, 20);
		sizeDelSlider.setLocation(70, 5);
		sizeDelPanel.add(sizeDelSlider);
		JLabel sizeDelLabel2 = new JLabel("30KB");
		sizeDelLabel2.setSize(30, 20);
		sizeDelLabel2.setLocation(330, 2);
		sizeDelPanel.add(sizeDelLabel2);
		sizeDelSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				sizeDelLabel2.setText(sizeDelSlider.getValue() + "KB");

			}
		});

		JPanel splitNumPanel = new JPanel();
		splitNumPanel.setSize(getWidth(), 30);
		splitNumPanel.setLocation(0, 120);
		// splitNumPanel.setBackground(Color.RED);
		splitNumPanel.setLayout(null);
		JLabel splitNumLabel = new JLabel("SplitNum:");
		splitNumLabel.setSize(70, 30);
		splitNumLabel.setLocation(0, 0);
		splitNumPanel.add(splitNumLabel);
		JSlider splitNumSlider = new JSlider(JSlider.HORIZONTAL, 1, 2000, 2000);
		splitNumSlider.setSize(240, 20);
		splitNumSlider.setLocation(70, 5);
		splitNumPanel.add(splitNumSlider);
		JLabel splitNumLabel2 = new JLabel("2000");
		splitNumLabel2.setSize(30, 20);
		splitNumLabel2.setLocation(330, 2);
		splitNumPanel.add(splitNumLabel2);
		splitNumSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				splitNumLabel2.setText(splitNumSlider.getValue() + "");

			}
		});

		JPanel porn91HostPanel = new JPanel();
		porn91HostPanel.setSize(getWidth(), 30);
		porn91HostPanel.setLocation(0, 160);
		// porn91HostPanel.setBackground(Color.RED);
		porn91HostPanel.setLayout(null);
		JLabel porn91HostLabel = new JLabel("91porn:");
		porn91HostLabel.setSize(70, 30);
		porn91HostLabel.setLocation(0, 0);
		porn91HostPanel.add(porn91HostLabel);
		JTextField porn91HostField = new JTextField();
		porn91HostField.setFont(new Font("宋体", 10, 16));
		porn91HostField.setText(
				mainFrame.getPorn91Host().substring(0, mainFrame.getPorn91Host().length() - 1));
		porn91HostField.setSize(240, 20);
		porn91HostField.setLocation(70, 5);
		porn91HostPanel.add(porn91HostField);
		JButton save1Button = new RButton("保存");
		save1Button.setSize(60, 20);
		save1Button.setLocation(320, 5);
		porn91HostPanel.add(save1Button);
		save1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new URL(porn91HostField.getText());
					mainFrame.setPorn91Host(porn91HostField.getText().trim() + "/");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		JPanel taoHuaHostPanel = new JPanel();
		taoHuaHostPanel.setSize(getWidth(), 30);
		taoHuaHostPanel.setLocation(0, 200);
		// taoHuaHostPanel.setBackground(Color.RED);
		taoHuaHostPanel.setLayout(null);
		JLabel taoHuaHostLabel = new JLabel("TaoHua:");
		taoHuaHostLabel.setSize(70, 30);
		taoHuaHostLabel.setLocation(0, 0);
		taoHuaHostPanel.add(taoHuaHostLabel);
		JTextField taoHuaHostField = new JTextField();
		taoHuaHostField.setFont(new Font("宋体", 10, 16));
		taoHuaHostField.setText(
				mainFrame.getTaoHuaHost().substring(0, mainFrame.getTaoHuaHost().length() - 1));
		taoHuaHostField.setSize(240, 20);
		taoHuaHostField.setLocation(70, 5);
		taoHuaHostPanel.add(taoHuaHostField);
		JButton save2Button = new RButton("保存");
		save2Button.setSize(60, 20);
		save2Button.setLocation(320, 5);
		taoHuaHostPanel.add(save2Button);
		save2Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new URL(taoHuaHostField.getText());
					mainFrame.setTaoHuaHost(taoHuaHostField.getText().trim() + "/");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});

		container.add(savePathPanel);
		container.add(downNumPanel);
		container.add(sizeDelPanel);
		container.add(splitNumPanel);
		container.add(porn91HostPanel);
		container.add(taoHuaHostPanel);
		JButton saveButton = new RButton("保存");
		saveButton.setSize(60, 30);
		saveButton.setLocation(100, 230);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setSavePath(pathField.getText().trim());
				mainFrame.setDownNum(downNumberSlider.getValue());
				mainFrame.setDelSize(sizeDelSlider.getValue());
				mainFrame.setSplitNum(splitNumSlider.getValue());
				save1Button.doClick();
				save2Button.doClick();
				dispose();
				mainFrame.getOptionMenu().setSettingFrame(null);
			}
		});

		JButton cancleButton = new RButton("取消");
		cancleButton.setSize(60, 30);
		cancleButton.setLocation(240, 230);
		cancleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				dispose();
				mainFrame.getOptionMenu().setSettingFrame(null);
			}
		});
		
		container.add(saveButton);
		container.add(cancleButton);
		setResizable(false);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2-100);		
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				mainFrame.getOptionMenu().setSettingFrame(null);
			}
		});
	}
}
