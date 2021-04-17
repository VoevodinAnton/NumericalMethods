package ru.ssau.tk.fx.numericalmethods.model;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.FunctionExtension;

public class BivariateFunction implements FunctionExtension {
    String function;
    double x;
    double y;
    Argument xArgument;
    Argument yArgument;
    Expression e;

    public BivariateFunction(String function) {
        this.function = function;
        xArgument = new Argument("x =  0");
        yArgument = new Argument("y = 0");
        e = new Expression(function, xArgument, yArgument);
    }

    @Override
    public int getParametersNumber() {
        return 2;
    }

    @Override
    public void setParameterValue(int argumentIndex, double argumentValue) {
        if (argumentIndex == 0) x = argumentValue;
        if (argumentIndex == 1) y = argumentValue;

    }

    @Override
    public String getParameterName(int i) {
        if (i == 0) {
            return "x";
        }
        if (i == 1) {
            return "y";
        } else {
            return "null";
        }
    }

    @Override
    public double calculate() {
        e.setArgumentValue("x", x);
        e.setArgumentValue("y", y);
        return e.calculate();
    }

    @Override
    public FunctionExtension clone() {
        return new BivariateFunction(function);
    }
}
