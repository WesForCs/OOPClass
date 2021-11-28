package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] contents = {"sin","cos","tan","(",")","ln","AC","⬅","%","/","x^y","7","8","9","*","x!","4","5","6","-","π","1","2","3","+","模式","e","0",".","="};
	private JButton buttons[] = new JButton[contents.length];
	private Screen result = new Screen("");
	private JLabel modeTxt = new JLabel("计算模式",JLabel.LEFT);
	public Window() {
		super("计算器");
		this.setLayout(null);
		result.setBounds(20,5,320,40);
		result.setHorizontalAlignment(JTextField.RIGHT);
		result.setEditable(false);
		modeTxt.setBounds(20,325,100,40);
		this.add(modeTxt);
		this.add(result);
		this.setResizable(false);
		this.setBounds(500,200,375,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		int x = 20,y = 55;
		for(int i = 0;i < buttons.length;i++) {
			buttons[i] = new JButton();
			buttons[i].setText(contents[i]);
			buttons[i].setBounds(x,y,60,40);
			if(x < 280) {
				x += 65;
			}else {
				x = 20;
				y += 45;
			}
			this.add(buttons[i]);
			this.repaint();
			buttons[i].addActionListener(this);
		}
	}
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if(label == "模式") {
			if(modeTxt.getText() == "计算模式") {
				modeTxt.setText("函数绘制模式");
				buttons[contents.length-1].setText("绘制");
				buttons[2].setText("x");
			}else {
				modeTxt.setText("计算模式");
				buttons[contents.length-1].setText("=");
				buttons[2].setText("tan");
			}
			result.setMode();
		}else {
			result.react(label);
		}		
		this.repaint();
	}
}
