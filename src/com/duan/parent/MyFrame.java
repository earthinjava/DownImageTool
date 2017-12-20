package com.duan.parent;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void follow(JFrame frame,String side) {
		if(this!=null){			
			int x = (int) frame.getLocation().getX();
			int y = (int) frame.getLocation().getY();
			int width=frame.getWidth();
			int height=frame.getHeight();		
			if(side.equalsIgnoreCase("right")){
				setLocation(x + width, y);
			}else if(side.equalsIgnoreCase("left")){
				setLocation(x - this.getWidth(), y);
			}else if(side.equalsIgnoreCase("up")){
				setLocation(x, y-this.getHeight());
			}else if(side.equalsIgnoreCase("down")){
				setLocation(x, y+height);
			}		
			this.setVisible(true);			
		}
	}	
}
