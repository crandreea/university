package parser_exp;

import complex_exp.ComplexExpression;
import domain.NumarComplex;
import expressions.Operation;
import factory_exp.ExpressionFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class ExpressionParser {

    private Operation parseOperation(String op) {
        if (Objects.equals(op, "+")) return Operation.ADDITION;
        else if (Objects.equals(op, "-")) return Operation.SUBTRACTION;
        else if (Objects.equals(op, "*")) return Operation.MULTIPLICATION;
        else if (Objects.equals(op, "/")) return Operation.DIVISION;
        else throw new IllegalArgumentException("operatie invalida");
    }

    private NumarComplex parseComplexNumber(String str) {
        str = str.replaceAll("i", ""); // de remove la i
        String[] parts = str.split("\\+|(?=-)"); // da split la fiecare + si inainte de fiecare -
        double im = 0;
        double re = 0;
        for (String part : parts) {
            if (part.contains("*")) { // partea im contine *
                im = Double.parseDouble(part.replace("*", "").trim());
            } else {
                re = Double.parseDouble(part.trim());
            }
        }


        return new NumarComplex(re, im);
    }

    private static final Pattern COMPLEX_NUMBER_PATTERN = Pattern.compile("([-+]?\\d+(\\.\\d+)?)([-+]?\\d+(\\.\\d+)?)?\\*?i");


    public ComplexExpression parse(String[] args) {
        List<NumarComplex> numerec = new ArrayList<>();
        Operation operation = null;
        List<String> listop = new ArrayList<>();

        for (String arg : args) {
            if (arg.matches("[+\\-*/]")) {
                listop.add(arg);
                operation = parseOperation(arg);
            } else {
                if (!COMPLEX_NUMBER_PATTERN.matcher(arg).matches()) {
                    throw new IllegalArgumentException("Input invalid: " + arg);
                }
                numerec.add(parseComplexNumber(arg));
            }
        }

        String op1 = listop.getFirst();
        for (String op : listop) {
            if (!op.equals(op1)) throw new IllegalArgumentException("Input invalid: " + op);
        }

        if (operation == null || numerec.size() < 2) {
            throw new IllegalArgumentException("Format expresie invalid!");
        }

        return ExpressionFactory.getInstance().createExpression(operation, numerec);
    }
}



