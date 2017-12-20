package com.duan.frame;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.duan.parent.MyFrame;
import com.duan.parent.RButton;

public class SettingFrame extends MyFrame{	
	private static final long serialVersionUID = 1L;
	public SettingFrame(MainFrame mainFrame) {
		setSize(400, 480);
		Container container=this.getContentPane();
		container.setLayout(null);		
		JPanel savePathPanel=new JPanel();
		savePathPanel.setSize(getWidth(), 30);
		savePathPanel.setLocation(0, 0);
		//savePathPanel.setBackground(Color.RED);
		savePathPanel.setLayout(null);
		JLabel savePathLabel=new JLabel("SavePath:");
		savePathLabel.setSize(70, 30);
		savePathLabel.setLocation(0, 0);
		savePathPanel.add(savePathLabel);
		JTextField pathField=new JTextField();
	//	pathField.setText(mainFrame.getSavePath());
		pathField.setSize(220, 20);
		pathField.setLocation(70, 5);
		savePathPanel.add(pathField);
		JButton pathChangeButton=new RButton("修改");
		pathChangeButton.setSize(60, 20);
		pathChangeButton.setLocation(310, 5);
		savePathPanel.add(pathChangeButton);		
		
		JPanel downNumPanel=new JPanel();
		downNumPanel.setSize(getWidth(), 30);
		downNumPanel.setLocation(0, 40);
		//downNumPanel.setBackground(Color.RED);
		downNumPanel.setLayout(null);
		JLabel downNumLabel=new JLabel("DownNum:");
		downNumLabel.setSize(80, 30);
		downNumLabel.setLocation(0, 0);
		downNumPanel.add(downNumLabel);
		JSlider downNumberSlider=new JSlider(JSlider.HORIZONTAL, 1, 12, 12);
		downNumberSlider.setSize(240, 20);
		downNumberSlider.setLocation(70, 5);
		downNumPanel.add(downNumberSlider);
		JLabel downNumberLabel=new JLabel("12");
		downNumberLabel.setSize(30, 20);
		downNumberLabel.setLocation(330, 2);
		downNumPanel.add(downNumberLabel);	
		downNumberSlider.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				downNumberLabel.setText(downNumberSlider.getValue()+"");
				
			}
		});
		
		
		JPanel sizeDelPanel=new JPanel();
		sizeDelPanel.setSize(getWidth(), 30);
		sizeDelPanel.setLocation(0, 80);
		//sizeDelPanel.setBackground(Color.RED);
		sizeDelPanel.setLayout(null);
		JLabel sizeDelLabel=new JLabel("DelSize:");
		sizeDelLabel.setSize(70, 30);
		sizeDelLabel.setLocation(0, 0);
		sizeDelPanel.add(sizeDelLabel);	
		JSlider sizeDelSlider=new JSlider(JSlider.HORIZONTAL, 1, 50, 30);
		sizeDelSlider.setSize(240, 20);
		sizeDelSlider.setLocation(70, 5);
		sizeDelPanel.add(sizeDelSlider);
		JLabel sizeDelLabel2=new JLabel("30KB");
		sizeDelLabel2.setSize(30, 20);
		sizeDelLabel2.setLocation(330, 2);
		sizeDelPanel.add(sizeDelLabel2);	
		sizeDelSlider.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				sizeDelLabel2.setText(sizeDelSlider.getValue()+"KB");
				
			}
		});
		
		
		JPanel splitNumPanel=new JPanel();
		splitNumPanel.setSize(getWidth(), 30);
		splitNumPanel.setLocation(0, 120);
		splitNumPanel.setBackground(Color.RED);
		splitNumPanel.setLayout(null);
		JLabel splitNumLabel=new JLabel("SplitNum:");
		splitNumLabel.setSize(70, 30);
		splitNumLabel.setLocation(0, 0);
		splitNumPanel.add(splitNumLabel);		
		
		
		JPanel porn91HostPanel=new JPanel();
		porn91HostPanel.setSize(getWidth(), 30);
		porn91HostPanel.setLocation(0, 160);
		porn91HostPanel.setBackground(Color.RED);
		porn91HostPanel.setLayout(null);
		JLabel porn91HostLabel=new JLabel("91porn:");
		porn91HostLabel.setSize(70, 30);
		porn91HostLabel.setLocation(0, 0);
		porn91HostPanel.add(porn91HostLabel);	
		
		
		JPanel taoHuaHostPanel=new JPanel();
		taoHuaHostPanel.setSize(getWidth(), 30);
		taoHuaHostPanel.setLocation(0, 200);
		taoHuaHostPanel.setBackground(Color.RED);
		taoHuaHostPanel.setLayout(null);
		JLabel taoHuaHostLabel=new JLabel("TaoHua:");
		taoHuaHostLabel.setSize(70, 30);
		taoHuaHostLabel.setLocation(0, 0);
		taoHuaHostPanel.add(taoHuaHostLabel);	
		
		
		
		container.add(savePathPanel);
		container.add(downNumPanel);
		container.add(sizeDelPanel);
		container.add(splitNumPanel);
		container.add(porn91HostPanel);
		container.add(taoHuaHostPanel);
		JButton saveButton=new RButton("确认");
		saveButton.setSize(60, 30);
		saveButton.setLocation(40, 400);
		JButton cancleButton=new RButton("取消");
		cancleButton.setSize(60, 30);
		cancleButton.setLocation(180, 400);
		container.add(saveButton);
		container.add(cancleButton);
		setVisible(true);
	}
	public static void main(String[] args) {
		new SettingFrame(null);
	}
}
