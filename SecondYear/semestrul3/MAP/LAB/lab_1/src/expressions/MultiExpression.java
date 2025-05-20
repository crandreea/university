package expressions;

import complex_exp.ComplexExpression;
import domain.NumarComplex;

import java.util.List;

public class MultiExpression extends ComplexExpression {

    public MultiExpression(List<NumarComplex> args) {

        super(Operation.MULTIPLICATION, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2) {
        return c1.inmultire(c2);
    }
}


