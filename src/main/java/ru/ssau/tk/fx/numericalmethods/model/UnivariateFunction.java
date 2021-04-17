package ru.ssau.tk.fx.numericalmethods.model;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.FunctionExtension;

public class UnivariateFunction implements FunctionExtension {
    String function;
    double x;
    Argument x1;
    Expression e;


    public UnivariateFunction(String function) {
        this.function = function;
        x1 = new Argument("x =  0");
        e = new Expression(function,x1);
    }

    @Override
    public int getParametersNumber() {
        return 1;
    }

    @Override
    public void setParameterValue(int argumentIndex, double argumentValue) {
        if (argumentIndex == 0) x = argumentValue;
    }

    @Override
    public String getParameterName(int i) {
        if (i == 0){
            return "x";
        } else{
            return "null";
        }
    }

    @Override
    public double calculate() {
        e.setArgumentValue("x", x);
        return e.calculate();
    }


    @Override
    public FunctionExtension clone() {
        return new UnivariateFunction(function);
    }
}
