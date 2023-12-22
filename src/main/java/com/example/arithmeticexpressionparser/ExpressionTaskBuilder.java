package com.example.arithmeticexpressionparser;

public class ExpressionTaskBuilder {
    private String expression;

    private ExpressionTaskBuilder() {
        // Private constructor to prevent direct instantiation
    }

    public static ExpressionTaskBuilder create() {
        return new ExpressionTaskBuilder();
    }

    public ExpressionTaskBuilder setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public ExpressionTask build() {
        if (expression == null) {
            throw new IllegalStateException("Expression not set");
        }
        return new ExpressionTask(expression);
    }
}