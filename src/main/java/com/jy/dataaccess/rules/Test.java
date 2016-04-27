package com.jy.dataaccess.rules;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Created with IntelliJ IDEA.
 * User: wdong
 * Date: 13-4-7
 * Time: 下午9:33
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        //String[] array = {"test","abc"};
        List<String> array = new ArrayList<String>();
        array.add("test");
        array.add("abc");
        String exp = "#array.contains(#candidate.asin) && #candidate.type.equals('ITEM_LABEL_MISSING')  ? 'TRUE' : 'FALSE'";
        DefectCandidate candidate = new DefectCandidate();
        candidate.setAsin("test");
        candidate.setType("ITEM_LABEL_MISSING1");

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("candidate",candidate);
        context.setVariable("array",array);
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp);
        String flag = (String)expression.getValue(context);
        System.out.println(flag);
    }


}


