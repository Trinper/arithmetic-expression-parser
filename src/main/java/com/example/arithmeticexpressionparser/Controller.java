package com.example.arithmeticexpressionparser;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {
    private Model model;
    Controller(String args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
        model = new Model(args);
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
}
