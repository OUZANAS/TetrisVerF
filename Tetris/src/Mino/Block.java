package Mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {

	public int x,y;
	public static int SIZE=30;
	public Color c;
	
	public Block(Color c) {
		this.c=c;
	}
	public void draw(Graphics2D q2) {
		int margine =2;
		q2.setColor(c);
		q2.fillRect(x +margine, y +margine, SIZE -(margine*2), SIZE-(margine*2));
		
	}
}
