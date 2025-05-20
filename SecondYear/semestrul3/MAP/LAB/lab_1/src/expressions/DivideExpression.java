package expressions;

import complex_exp.ComplexExpression;
import domain.NumarComplex;

import java.util.List;

public class DivideExpression extends ComplexExpression {

    public DivideExpression(List<NumarComplex> args) {

        super(Operation.DIVISION, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2) {
        return c1.impartire(c2);
    }
}
