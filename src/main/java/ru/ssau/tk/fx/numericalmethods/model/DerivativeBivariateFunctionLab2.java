package ru.ssau.tk.fx.numericalmethods.model;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.FunctionExtension;

public class DerivativeBivariateFunctionLab2 implements FunctionExtension {
    String function;
    double x;
    double y;
    int flag;
    String derivativeArgument;
    Argument xArgument;
    Argument yArgument;
    Expression e;

    public DerivativeBivariateFunctionLab2(String function) {
        this.function = function;
        xArgument = new Argument("x =  0");
        yArgument = new Argument("y = 0");
    }

    @Override
    public int getParametersNumber() {
        return 3;
    }

    @Override
    public void setParameterValue(int argumentIndex, double argumentValue) {
        if (argumentIndex == 0) x = argumentValue;
        if (argumentIndex == 1) y = argumentValue;
        if (argumentIndex == 2) flag = (int) argumentValue;


    }

    @Override
    public String getParameterName(int i) {
        if (i == 0) {
            return "x";
        }
        if (i == 1) {
            return "y";
        }
        if (i == 2) {
            return "flag";
        } else {
            return "null";
        }
    }

    @Override
    public double calculate() {
        if (flag == 1) {
            setDerivativeArgument("x");
            e = new Expression("der(" + function + ", " + derivativeArgument + ")", xArgument, yArgument);
            e.setArgumentValue("x", x);
            e.setArgumentValue("y", y);
            return e.calculate();
        }

        if (flag == 2) {
            setDerivativeArgument("y");
            e = new Expression("der(" + function + ", " + derivativeArgument + ")", xArgument, yArgument);
            e.setArgumentValue("x", x);
            e.setArgumentValue("y", y);
            return e.calculate();
        }
        return 0;
    }

    @Override
    public FunctionExtension clone() {
        return new DerivativeBivariateFunctionLab2(function);
    }

    public void setDerivativeArgument(String argument) {
        derivativeArgument = argument;
    }
}
