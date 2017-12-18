		package com.duan.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.duan.utils.Constant;

public class DownBar {

	private double progress;
	private int width;
	private int height;
	private int x;
	private int y;	
	private Color progressColor;	
	
	public DownBar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;			
		progressColor = Constant.ISLOADING_COLOR;
	}

	public DownBar(int x, int y) {
		this.x = x;
		this.y = y;
		width = 225;
		height = 21;
		progressColor = Constant.ISLOADING_COLOR;
	}

	/**
	 * �����Լ���
	 * @param g
	 * @param �Ƿ�һ��ʼ�ͻ������ο�
	 */
	public void drawProgress(Graphics g,boolean begDraw) {
		Color color = g.getColor();
		if(begDraw){
			g.drawRoundRect(x, y, width, height, 6, 6);
		}else if (progress != 0 && progress != 1) {
			g.drawRoundRect(x, y, width, height, 6, 6);
		}
		g.setColor(progressColor);		
		g.fillRoundRect(x , y , (int) (progress * width), height,
				6, 6);		
		g.setColor(color);
		Font font=g.getFont();
		g.setFont(new Font("宋体", 10, 16));
		String proString=(int)(progress*100)+"%";
		g.drawString(proString, x+width-50, y+height-5);
		g.setFont(font);
	}
	
	public void drawProgress(Graphics g,boolean begDraw,boolean drawText) {
		Color color = g.getColor();
		if(begDraw){
			g.drawRoundRect(x, y, width, height, 6, 6);
		}else if (progress != 0 ) {
			g.drawRoundRect(x, y, width, height, 6, 6);
		}
		g.setColor(progressColor);
		g.fillRoundRect(x, y, (int) (progress * width), height,
				6, 6);		
		g.setColor(color);
		Font font=g.getFont();
		g.setFont(new Font("宋体", 10, 16));
		String proString=(int)(progress*100)+"%";
		if (progress != 0 ) {
			g.drawString(proString, x+width-50, y+height-5);
		}
		g.setFont(font);
	}

	public void drawProgress(Graphics g,boolean begDraw,int taskNumber,int finishNumber) {
		Color color = g.getColor();
		if(begDraw){
			g.drawRoundRect(x, y, width, height, 6, 6);
		}else if (progress != 0 && progress != 1) {
			g.drawRoundRect(x, y, width, height, 6, 6);
		}
		g.setColor(progressColor);
		g.fillRoundRect(x, y, (int) (progress * width), height ,
				6, 6);		
		g.setColor(color);
		Font font=g.getFont();
		g.setFont(new Font("宋体", 10, 16));
		String proString;
		if(progress!=1){
			proString=(int)(progress*100)+"%";
		}else{
			proString="完成";
		}
		g.drawString(proString, x+width-50, y+height-5);		
		String number=finishNumber+"/"+taskNumber;
		g.drawString(number, x+10, y+height-5);		
		g.setFont(font);		
	}
	
	
	public synchronized double getProgress() {
		return progress;
	}

	public synchronized void setProgress(double progress) {
		this.progress = progress;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getProgressColor() {
		return progressColor;
	}

	public void setProgressColor(Color progressColor) {
		this.progressColor = progressColor;
	}

}
