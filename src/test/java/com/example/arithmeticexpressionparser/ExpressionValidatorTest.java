package com.example.arithmeticexpressionparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionValidatorTest {

    @Test
    void isMathExpressionTest1() {
        assertTrue(ExpressionValidator.isMathExpression("1"));
    }
    @Test
    void isMathExpressionTest2() {
        assertTrue(ExpressionValidator.isMathExpression("                 12.321            "));
    }
    @Test
    void isMathExpressionTest3() {
        assertTrue(ExpressionValidator.isMathExpression("(sin(1 / 12) )"));
    }
    @Test
    void isMathExpressionTest4() {
        assertFalse(ExpressionValidator.isMathExpression("coc(sad))"));
    }
    @Test
    void isMathExpressionTest5() {
        assertFalse(ExpressionValidator.isMathExpression("1.. + 2"));
    }

    @Test
    void isTrueMathExpression1() {
        assertTrue(ExpressionValidator.isTrueMathExpression("1 + 2"));
    }
    @Test
    void isTrueMathExpression2() {
        assertTrue(ExpressionValidator.isTrueMathExpression("( 1.0 + (-2) )"));
    }
    @Test
    void isTrueMathExpression3() {
        assertFalse(ExpressionValidator.isTrueMathExpression("1 ++ 3"));
    }
    @Test
    void isTrueMathExpression4() {
        assertFalse(ExpressionValidator.isTrueMathExpression("(2 + ( 1 )"));
    }
    @Test
    void isTrueMathExpression5() {
        assertTrue(ExpressionValidator.isTrueMathExpression("(sin(1) / exp(1 + 4)) * cos(0)"));
    }
}