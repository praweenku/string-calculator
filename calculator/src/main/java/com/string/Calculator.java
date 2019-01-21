package com.string;

import java.util.stream.Stream;

public class Calculator {
	public final String INVALID_EXPRESSION = "INVALID EXPRESSION";
public static void main(String[] args) {
	Calculator calc = new Calculator();
	System.out.println(calc.evaluate("8*+7"));
}
	public String calulate(String str) {
		String strReturn = "";
		
		try {
			strReturn = Double.toString(evaluate(str).evalulate());
		} catch (RuntimeException re) {
			//re.printStackTrace();
			strReturn = INVALID_EXPRESSION;
		}
		return strReturn;
	}

	public   Expression evaluate(final String str) {
		return new Object() {
			int pos = -1, ch;

			// if check pos+1 is smaller than string length ch is char at new
			// pos
			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			// skips 'spaces' and same operator if it comes together in sequence 
			// if above is true it move pointer to next char return true
			// else return false
			boolean escape(int charToEat) {
				while (ch == ' ')
					nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			Expression parse() {
				nextChar();
				Expression x = parseExpression();
				if (pos < str.length())
					throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			

			Expression parseExpression() {
				Expression x = parseMultiplicationOrDevision();
				for (;;) {
					if (escape('+')) { // addition
						Expression a = x, b = parseMultiplicationOrDevision();
						x = (() -> a.evalulate() + b.evalulate());
					} else if (escape('-')) { // subtraction
						Expression a = x, b = parseMultiplicationOrDevision(); 
						x = (() -> a.evalulate() - b.evalulate());

					} else {
						return x;
					}
				}
			}

			Expression parseMultiplicationOrDevision() {
				Expression x = parseFactor();
				
				for(;;){
					if (escape('*')) {
						Expression a = x, b = parseFactor(); // multiplication
						x = (() -> a.evalulate() * b.evalulate());
					} else if (escape('/')) {
						Expression a = x, b = parseFactor(); // division
						x = (() -> a.evalulate() / b.evalulate());
					} else
						return x;
				}
			}

			Expression parseFactor() {
				

				Expression x;
				int startPos = this.pos;
				if (escape('(')) { // parentheses
					x = parseExpression();
					escape(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') {
						nextChar();
					}
					double xx = Double.parseDouble(str.substring(startPos, this.pos));
					x = () -> xx;
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z')
						nextChar();
					String func = str.substring(startPos, this.pos);

					double xx = parseFactor().evalulate();
					if (func.equals("sqrt"))
						x = () -> Math.sqrt(xx);
					else if (func.equals("sin"))
						x = () -> Math.sin(Math.toRadians(xx));
					else if (func.equals("cos"))
						x = () -> Math.cos(Math.toRadians(xx));
					else if (func.equals("tan"))
						x = () -> Math.tan(Math.toRadians(xx));
					else
						throw new RuntimeException("Unknown function: " + func);

				}else {
					throw new RuntimeException("Unexpected: " + (char) ch);
				}

				if (escape('^')) {
					Expression a = x, b = parseFactor();
					System.out.println(x.evalulate());
					System.out.println(b.evalulate());
					x = () -> Math.pow(a.evalulate(), b.evalulate()); // exponentiation
				}
				return x;
			}
		}.parse();
}}
