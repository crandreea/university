package expressions;

import complex_exp.ComplexExpression;
import domain.NumarComplex;

import java.util.List;

public class AddExpression extends ComplexExpression {

    public AddExpression(List<NumarComplex> args) {
        super(Operation.ADDITION, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2) {
        return c1.adunare(c2);
    }
}

