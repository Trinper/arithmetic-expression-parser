package com.example.arithmeticexpressionparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTaskTest {

    @Test
    public void testGetText_NoExtraSpaces() {
        ExpressionTask expressionTask = new ExpressionTask("2+3*4");
        String text = expressionTask.getText();
        assertEquals("2+3*4", text);
    }
    @Test
    public void testGetText_ExtraSpaces() {
        ExpressionTask expressionTask = new ExpressionTask("   2 + 3 * 4   ");
        String text = expressionTask.getText();
        assertEquals("2 + 3 * 4", text);
    }
    @Test
    public void testGetText_MultipleSpaces() {
        ExpressionTask expressionTask = new ExpressionTask("2   +   3   *   4");
        String text = expressionTask.getText();
        assertEquals("2 + 3 * 4", text);
    }
    @Test
    public void testGetText_EmptyString() {
        ExpressionTask expressionTask = new ExpressionTask("");
        String text = expressionTask.getText();
        assertEquals("", text);
    }
    @Test
    public void testGetText_WhitespaceString() {
        ExpressionTask expressionTask = new ExpressionTask("     ");
        String text = expressionTask.getText();
        assertEquals("", text);
    }
    @Test
    public void testCalculate_SimpleExpression() {
        ExpressionTask expressionTask = new ExpressionTask("2 + 3 * 4");
        Double result = expressionTask.calculate();
        assertEquals(2 + 3 * 4, result, 0.0001);
    }
    @Test
    public void testCalculate_NegativeNumbers() {
        ExpressionTask expressionTask = new ExpressionTask("-2 + (-3) * 4");
        Double result = expressionTask.calculate();
        assertEquals(-2 + (-3) * 4, result, 0.0001);
    }
    @Test
    public void testCalculate_Functions() {
        ExpressionTask expressionTask = new ExpressionTask("sin(0.5) + cos(0.25)");
        Double result = expressionTask.calculate();
        assertEquals(Math.sin(0.5) + Math.cos(0.25), result, 0.000000001);
    }
    @Test
    public void testCalculate_Brackets() {
        ExpressionTask expressionTask = new ExpressionTask("(2 + 3) * 4");
        Double result = expressionTask.calculate();
        assertEquals((2 + 3) * 4, result, 0.0001);
    }
    @Test
    public void testCalculate_DivisionByZero() {
        ExpressionTask expressionTask = new ExpressionTask("2 / 0");
        try {
            expressionTask.calculate();
            fail("Expected ArithmeticException to be thrown");
        } catch (ArithmeticException e) {
            assertEquals("Division by zero is not allowed", e.getMessage());
        }
    }
}