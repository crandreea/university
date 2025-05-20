import complex_exp.ComplexExpression;
import parser_exp.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        try {
            ExpressionParser parser = new ExpressionParser();
            ComplexExpression expression = parser.parse(args); //parseaza expresia si calculeaza rezultatul
            System.out.println("Rezultat =  " + expression.execute());
        } catch (Exception e) {
            System.err.println("Eroare: " + e.getMessage());
        }
    }
}
