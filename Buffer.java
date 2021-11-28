package calculator;

import javax.swing.JTextField;

public class Buffer extends JTextField implements Include{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String[] inputBuffer = new String[maxSize];
	public int ptr;
	Buffer(String text){
		super(text);
		ptr = 0;
	}
	public void setBuffer(String input) {
		switch(input) {
			case "AC" :
				ptr = 0;
				break;
			case "⬅" :
				ptr--;
				break;
			case "x^y" :
				inputBuffer[ptr++] = "^";
				break;
			case "x!" :
				inputBuffer[ptr++] = "!";
				break;
			default:
				inputBuffer[ptr++] = input;
				if(input == "sin" || input == "cos" || input == "tan" || input == "ln") {
					inputBuffer[ptr++] = "(";
				}
		}
	}
	public String[] getBuffer() {
		return inputBuffer;
	}
	public int getPtr() {
		return ptr;
	}
}
