package ru.ssau.tk.fx.numericalmethods.controller;

import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Function;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeUnivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.UnivariateFunction;
import ru.ssau.tk.fx.numericalmethods.utils.Constants;

public class Methods {
    private int iterationHalfDivision = 0;
    private int iterationHybridMethod = 0;


    public double calculateHalfDivision(String expression, double a, double b) {
        Function function = new Function("f", new UnivariateFunction(expression));
        double rootHD;
        double c;

        //метод половинного деления
        while (true) {
            c = (a + b) / 2;
            if (Math.abs(b - a) < 2 * Constants.epsilon) {
                rootHD = c;
                break;
            }
            if (Math.abs(function.calculate(c)) < Constants.delta) {
                rootHD = c;
                break;
            }
            if (function.calculate(a) * function.calculate(c) < 0) {
                b = c;
            } else {
                a = c;
            }
            iterationHalfDivision++;
        }

        return rootHD;

    }

    public int getIterationHalfDivision() {
        return iterationHalfDivision;
    }

    public double calculateHybridMethod(String expression, double xk) throws Exception {
        Function function = new Function("f", new UnivariateFunction(expression));
        Function functionD = new Function("f", new DerivativeUnivariateFunction(expression));
        double rootHyb;

        double xkNew;
        while (true) {

            xkNew = xk - function.calculate(xk) / functionD.calculate(xk);

            if (Math.abs(xkNew - xk) < 2 * Constants.epsilon) {
                rootHyb = xkNew;
                break;
            }
            if (Math.abs(function.calculate(xkNew)) < Constants.delta) {
                rootHyb = xkNew;
                break;
            }

            while (true) {
                if (Math.abs(function.calculate(xkNew)) < Math.abs(function.calculate(xk))) {
                    xk = xkNew;
                    break;
                } else {
                    xkNew = 1 / 2. * (xk + xkNew);
                }
            }
            iterationHybridMethod++;
        }
        return rootHyb;
    }

    public int getIterationHybridMethod() {
        return iterationHybridMethod;
    }
}

