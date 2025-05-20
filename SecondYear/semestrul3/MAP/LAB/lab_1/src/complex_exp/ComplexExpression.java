package complex_exp;

import domain.NumarComplex;
import expressions.Operation;

import java.util.List;

public abstract class ComplexExpression {
    private Operation operatie;
    private List<NumarComplex> args; //lista de nr complexe implicate in operatie

    public ComplexExpression(Operation operatie, List<NumarComplex> args) {
        this.operatie = operatie;
        this.args = args;
    }

    //TemplateMethodDesignPattern
    //metoda abstracta face o singura op intre doua nr complexe
    protected abstract NumarComplex executeOneOperation(NumarComplex c1, NumarComplex c2);

    //executa op pe lista de nr complexe
    public NumarComplex execute() {
        NumarComplex result = args.getFirst();

        for (int i = 1; i < args.size(); i++) {
            NumarComplex c = args.get(i);
            result = executeOneOperation(result, c);
        }

        return result;
    }

}
