package com.string;

import static org.junit.Assert.assertEquals;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

public class CalculatorTest 
{
    

    /**
     * Rigourous Test :-)
     */
	@org.junit.Test
    public void testCalulateValidExpr1()
    {
		Calculator calc = new Calculator();		
        assertEquals( calc.calulate("7+(6*5^2+3-4/2)"),"158.0" );
    }
	@org.junit.Test
    public void testCalulateValidExpr2()
    {
		Calculator calc = new Calculator();
        assertEquals( calc.calulate("(8*5/8)-(3/1)-5"),"-3.0" );
    }
	@org.junit.Test
    public void testCalulateInValidExpr1()
    {
		Calculator calc = new Calculator();
        assertEquals( calc.calulate("7+(67(56*2))"),"INVALID EXPRESSION" );
        assertEquals( calc.calulate("8*+7"),"INVALID EXPRESSION" );
    }
	@org.junit.Test
    public void testCalulateInValidExpr2()
    {
		Calculator calc = new Calculator();
        assertEquals( calc.calulate("8*+7"),"INVALID EXPRESSION" );
    }
}
