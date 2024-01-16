package com.example.arithmeticexpressionparser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLHandler extends DefaultHandler {
    ArrayList<String> mathExpressions = new ArrayList<>();
    ArrayList<String> expressions = new ArrayList<>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("item")) {
            String str = attributes.getValue("str");
            expressions.add(str);
            if (ExpressionValidator.isMathExpression(str) && ExpressionValidator.isTrueMathExpression(str)) {
                mathExpressions.add(str);
            }
        }
    }
    public ArrayList<String> getExpressions(){
        return this.expressions;
    }
    public ArrayList<String> getMathExpressions(){ return this.mathExpressions; }
}