package calculator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.Color;

public class DrawGraph extends JPanel implements Include{
	private int width,height,X,Y;
	private final int UnitLength = 100;
	private String[] expression;
	private int ptr;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DrawGraph(String[] expression,int ptr) {
		this.expression = expression;
		this.ptr = ptr;
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		width = this.getWidth();
		height = this.getHeight();
		X = width/2;
		Y = height/2;
		this.drawAxes(g);
		this.drawFunc(g);
	}
	private void drawAxes(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(0,Y,width,Y);
		g.drawLine(X,0,X,height);
		g.drawString("0",X + 2,Y + 12);
		for(int i = 1;i*UnitLength < width;i++) {
			g.drawLine(X + i*UnitLength,Y - 1,X + i*UnitLength,Y - 6);
			g.drawLine(X - i*UnitLength,Y - 1,X - i*UnitLength,Y - 6);
			g.drawString(String.valueOf(i),X + i*UnitLength - 3,Y + 12);
			g.drawString(String.valueOf(i*(-1)),X - i*UnitLength - 3,Y + 12);
			g.drawLine(X + 1,Y + i*UnitLength,X + 6,Y + i*UnitLength);
			g.drawLine(X + 1,Y - i*UnitLength,X + 6,Y - i*UnitLength);
			g.drawString(String.valueOf(i),X - 12,Y - i*UnitLength - 3);
			g.drawString(String.valueOf(i*(-1)),X - 12,Y + i*UnitLength - 3);
		}
	}
	private void drawFunc(Graphics g1) {
		Calculator cal = new Calculator();
		Point2D temp1,temp2;
		double x,y;
		Graphics2D g = (Graphics2D)g1;
		g.setColor(Color.BLACK);
		x = (-1.0) * X / UnitLength;
		y = cal.calculate(replace(expression,ptr,Double.toString(x)), ptr);
		temp1 = new Point2D.Double(alterX(x * UnitLength),alterY(y * UnitLength));
		for(int i = 0;i < width;i++) {
			x = x + 1.0/UnitLength;
			y = cal.calculate(replace(expression,ptr,Double.toString(x)), ptr);
			if(Math.abs(y) < Y) {
				temp2 = new Point2D.Double(alterX(x * UnitLength),alterY(y * UnitLength));
				g.draw(new Line2D.Double(temp1,temp2));
				temp1 = temp2;
			}
		}
	}
	private String[] replace(String[] expression,int ptr,String rep) {
		String[] res = new String[maxSize];
		for(int i = 0;i < ptr;i++) {
			if(expression[i] == "x") {
				res[i] = rep;
			}else {
				res[i] = expression[i];
			}
		}
		return res;
	}
	private double alterX(double x) {
		return x + X;
	}
	private double alterY(double y) {
		return (y - Y) * (-1);
	}
}
