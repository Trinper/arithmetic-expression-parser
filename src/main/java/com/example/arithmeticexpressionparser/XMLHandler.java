package com.example.arithmeticexpressionparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLHandler extends DefaultHandler {
    ArrayList<String> expressions = new ArrayList<>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("item")) {
            String str = attributes.getValue("str");
            if (ExpressionValidator.isMathExpression(str) && ExpressionValidator.isTrueMathExpression(str)) {
                expressions.add(str);
            }
        }
    }
    public ArrayList<String> getExpressions(){
        return this.expressions;
    }
}