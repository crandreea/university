package expressions;

import complex_exp.ComplexExpression;
import domain.NumarComplex;

import java.util.List;

public class SubsExpression extends ComplexExpression {

    public SubsExpression(List<NumarComplex> args) {

        super(Operation.SUBTRACTION, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2) {

        return c1.scadere(c2);
    }
}
