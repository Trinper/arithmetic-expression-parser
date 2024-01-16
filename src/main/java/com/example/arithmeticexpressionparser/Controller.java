package com.example.arithmeticexpressionparser;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {
    private final Model model;
    Controller(String args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
        model = new Model(args);
        outInput();
        outCorrectExpressions();
        StringBuilder text  = new StringBuilder();
        for(var exp: model.getMathExpressions()){
            ExpressionTask expression = ExpressionTaskBuilder.create()
                    .setExpression(exp)
                    .build();

            String ans = expression.calculate().toString();
            text.append(ans).append('\n');
            model.write(ans);
        }

        Main.ansTextArea.setText(text.toString());

    }

    public void outCorrectExpressions(){
        StringBuilder text  = new StringBuilder();
        for (var line: model.getMathExpressions()){
            text.append(line).append('\n');
        }

        Main.fileTextArea.setText(text.toString());
    }
    public void outInput(){
        StringBuilder text  = new StringBuilder();
        for (var line: model.getExpressions()){
            text.append(line).append('\n');
        }

        Main.inputTextArea.setText(text.toString());
    }
}
