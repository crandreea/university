package factory_exp;

import complex_exp.ComplexExpression;
import domain.NumarComplex;
import expressions.*;

import java.util.List;

//Singleton Pattern - creates a single obj
public class ExpressionFactory {
    private static ExpressionFactory instance;

    private ExpressionFactory() {
    }

    //instantarea lui INSTANCE se face doar cand e cerut
    public static ExpressionFactory getInstance() {
        if (instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    //FactoryMethod Pattern - creates obj hiding the creation logic
    public ComplexExpression createExpression(Operation operation, List<NumarComplex> args) {
        if (operation == Operation.ADDITION)
            return new AddExpression(args);
        else if (operation == Operation.SUBTRACTION)
            return new SubsExpression(args);
        else if (operation == Operation.MULTIPLICATION)
            return new MultiExpression(args);
        else if (operation == Operation.DIVISION)
            return new DivideExpression(args);
        else
            throw new IllegalArgumentException("Operatie necunoscuta");
    }
}
