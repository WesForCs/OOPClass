package calculator;

import javax.swing.JFrame;

public class DrawWindow extends JFrame implements Include{
	private DrawGraph dg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DrawWindow(String[] buffer,int ptr){
		super("»æÖÆº¯ÊýÍ¼Ïñ");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		dg = new DrawGraph(buffer,ptr);
		getContentPane().add(dg);
	}
}
