package calculator;

public class Screen extends Buffer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Screen(String text){
		super(text);
	}
	public void setMode() {
		ptr = 0;
		refreshScreen();
	}
	private void refreshScreen() {
		String display = "";
		for(int i = 0;i < ptr;i++) {
			display = display + inputBuffer[i];
		}
		this.setText(display);
	}
	public void react(String input) {
		if(input == "=") {
			Calculator cal = new Calculator();
			double res = cal.calculate(inputBuffer, ptr);
			inputBuffer[0] = Double.toString(res);
			ptr = 1;
		}else if(input == "»æÖÆ") {
			DrawWindow draw = new DrawWindow(inputBuffer,ptr);
			draw.setVisible(true);
		}else {
			setBuffer(input);
		}
		refreshScreen();
	}
}
