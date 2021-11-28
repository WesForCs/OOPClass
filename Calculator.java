package calculator;

public class Calculator implements Include{
	private double[] numStack = new double[maxSize];
	private String[] opStack = new String[maxSize];
	private String[] chaStack = new String[maxSize];
	private int numPtr,opPtr,chaPtr;
	Calculator(){
		numPtr = 0;
		opPtr = 0;
		chaPtr = 0;
	}
	public double calculate(String[] buffer,int ptr) {
		for(int i = 0;i < ptr;i++) {
			if(checkNum(buffer[i])) {
				if(i+1 < ptr && (checkNum(buffer[i+1]) || buffer[i+1] == ".")) {
					String num = buffer[i];
					int end;
					for(int j = i;;) {
						if(j+1 < ptr && (checkNum(buffer[j+1]) || buffer[j+1] == ".")) {
							num = num + buffer[j+1];
							j++;
						}else {
							end = j;
							break;
						}
					}
					buffer[i] = num;
					for(int j = i + 1;(j+end-i) < ptr;j++) {
						buffer[j] = buffer[j+end-i];
					}
					ptr = ptr-(end-i);
				}
			}
		}
		for(int i = 0;i < ptr;i++) {
			if(buffer[i] == "¦Ð") {
				buffer[i] = Double.toString(pi);
			}
			if(buffer[i] == "e") {
				buffer[i] = Double.toString(e);
			}
		}
		for(int i = 0;i < ptr;i++) {
			if(buffer[i] == "sin" || buffer[i] == "cos" || buffer[i] == "tan" || buffer[i] == "ln") {
				int end;
				int total = 1;
				for(int j = i + 2;;j++) {
					if(buffer[j] == "(") {
						total++;
					}else if(buffer[j] == ")") {
						total--;
					}
					if(total == 0) {
						end = j;
						break;
					}
				}
				String[] buffer2 = new String[maxSize];
				for(int j = i + 1;j <= end;j++) {
					buffer2[j-i-1] = buffer[j];
				}
				double mid = calculate(buffer2,end-i);
				switch(buffer[i]) {
					case "sin" :
						mid = Math.sin(mid);
						break;
					case "cos" :
						mid = Math.cos(mid);
						break;
					case "tan" :
						mid = Math.tan(mid);
						break;
					case "ln" :
						mid = Math.log(mid);
				}
				buffer[i]=Double.toString(mid);
				for(int j = i + 1;(j+end-i) < ptr;j++) {
					buffer[j] = buffer[j+end-i];
				}
				ptr = ptr-(end-i);
			}
		}
		for(int i = 0;i < ptr;i++) {
			if(buffer[i] == "!") {
				if(buffer[i-1] != ")") {
					double mid = Double.parseDouble(buffer[i-1]);
					mid = factorial(mid);
					buffer[i-1] = Double.toString(mid);
					for(int j = i;j < ptr-1;j++) {
						buffer[j] = buffer[j+1];
					}
					ptr--;
					i--;
				}else {
					int begin;
					int total = 1;
					for(int j = i-2;;j--) {
						if(buffer[j] == "(") {
							total--;
						}else if(buffer[j] == ")") {
							total++;
						}
						if(total == 0) {
							begin = j;
							break;
						}
					}
					String[] buffer2 = new String[maxSize];
					for(int j = begin;j < i;j++) {
						buffer2[j-begin] = buffer[j];
					}
					double mid = calculate(buffer2,i-begin);
					mid = factorial(mid);
					buffer[begin]=Double.toString(mid);
					for(int j = begin + 1;(i+j-begin) < ptr;j++) {
						buffer[j] = buffer[i+j-begin];
					}
					ptr = ptr-(i-begin+1);
					i = begin;
				}
			}
		}
		for(int i = 0;i < ptr;i++) {
			if(buffer[i] == "-") {
				if(i == 0 || buffer[i-1] == "(") {
					if(Double.parseDouble(buffer[i+1]) >= 0) {
						buffer[i] = buffer[i] + buffer[i+1];
					}else {
						buffer[i] = Double.toString(-Double.parseDouble(buffer[i+1]));
					}
					for(int j = i + 1;j + 1 < ptr;j++) {
						buffer[j] = buffer[j+1];
					}
					ptr--;
				}
			}
		}
	    for(int i=0;i < ptr;i++){
	        if(buffer[i] == "("){
	            opStack[opPtr++] = buffer[i];
	        }else if(buffer[i] == ")"){
	            while (opStack[--opPtr] != "(")
	            {
	                chaStack[chaPtr++] = opStack[opPtr];
	            }
	        }else if(buffer[i] == "^"){
	            while (opPtr != 0 && opStack[opPtr-1] == "^")
	            {
	            	chaStack[chaPtr++] = opStack[--opPtr];
	            }
	            opStack[opPtr++] = buffer[i];
	        }else if(buffer[i] == "*" || buffer[i] == "/" || buffer[i] == "%"){
	            while (opPtr != 0 && (opStack[opPtr-1] == "*" || opStack[opPtr-1] == "/" || opStack[opPtr-1] == "%"))
	            {
	            	chaStack[chaPtr++] = opStack[--opPtr];
	            }
	            opStack[opPtr++] = buffer[i];
	        }else if(buffer[i] == "+" || buffer[i] == "-"){
	            while (opPtr != 0 && opStack[opPtr-1] != "(" && opStack[opPtr-1] != ")")
	            {
	            	chaStack[chaPtr++] = opStack[--opPtr];
	            }
	            opStack[opPtr++] = buffer[i];
	        }else{
	        	chaStack[chaPtr++] = buffer[i];
	        }
	    }
	    while (opPtr != 0){
	    	chaStack[chaPtr++] = opStack[--opPtr];
	    }
	    for(int i = 0;i < chaPtr;i++) {
	    	if(checkSym(chaStack[i])) {
	    		double op2 = numStack[--numPtr];
	    		double op1 = numStack[--numPtr];
	    		numStack[numPtr++] = operate(op1,op2,chaStack[i]);
	    	}else {
	    		numStack[numPtr++] = Double.parseDouble(chaStack[i]);
	    	}
	    }
		refresh();
		return numStack[0];
	}
	private double operate(double op1,double op2,String sym) {
		double res;
		switch(sym) {
			case "+" :
				res = op1+op2;
				break;
			case "-" :
				res = op1-op2;
				break;
			case "*" :
				res = op1*op2;
				break;
			case "/" :
				res = op1/op2;
				break;
			case "%" :
				res = (double)(((long)op1)%((long)op2));
				break;
			default :
				res = Math.pow(op1, op2);
		}
		return res;
	}
	private boolean checkNum(String checked) {
		if(checked == "0") {
			return true;
		}else if(checked == "1") {
			return true;
		}else if(checked == "2") {
			return true;
		}else if(checked == "3") {
			return true;
		}else if(checked == "4") {
			return true;
		}else if(checked == "5") {
			return true;
		}else if(checked == "6") {
			return true;
		}else if(checked == "7") {
			return true;
		}else if(checked == "8") {
			return true;
		}else if(checked == "9") {
			return true;
		}else {
			return false;
		}
	}
	private boolean checkSym(String checked) {
		if(checked == "(") {
			return true;
		}else if(checked == ")") {
			return true;
		}else if(checked == "%") {
			return true;
		}else if(checked == "/") {
			return true;
		}else if(checked == "^") {
			return true;
		}else if(checked == "*") {
			return true;
		}else if(checked == "-") {
			return true;
		}else if(checked == "+") {
			return true;
		}else {
			return false;
		}
	}
	private double factorial(double n) {
		long total = 1;
		for(long i = 1;i <= (long)n;i++) {
			total *= i;
		}
		return (double)total;
	}
	private void refresh() {
		numPtr = 0;
		opPtr = 0;
		chaPtr = 0;
	}
}
