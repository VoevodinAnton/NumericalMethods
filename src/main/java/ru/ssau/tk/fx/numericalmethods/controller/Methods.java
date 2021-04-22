package ru.ssau.tk.fx.numericalmethods.controller;

import org.apache.commons.math3.linear.*;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Function;
import ru.ssau.tk.fx.numericalmethods.model.BivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeBivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeUnivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.UnivariateFunction;
import ru.ssau.tk.fx.numericalmethods.utils.Constants;

public class Methods {
    private int iterationHalfDivision = 0;
    private int iterationHybridMethod = 0;
    private int iterationNewtonMethod = 0;
    private int iterationModifiedNewtonMethod = 0;


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
            iterationHybridMethod++;
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

        }
        return rootHyb;
    }

    public int getIterationHybridMethod() {
        return iterationHybridMethod;
    }


    public RealVector calculateNewtonMethod(String f1, String f2, double xValue, double yValue) throws Exception{
        Function firstFunction = new Function("f1", new BivariateFunction(f1));
        Function secondFunction = new Function("f2", new BivariateFunction(f2));
        Function firstFunctionD = new Function("f1D", new DerivativeBivariateFunction(f1));
        Function secondFunctionD = new Function("f2D", new DerivativeBivariateFunction(f2));

        RealVector xkVector = new ArrayRealVector(new double[]{xValue, yValue}, false);
        RealVector xkNewVector;


        while (true) {
            RealVector functions = new ArrayRealVector(new double[]{firstFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1)), secondFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1))}, false);
            RealMatrix jacobian = new Array2DRowRealMatrix(new double[][]{{firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}, {secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}});
            RealMatrix jacobianInverse = new LUDecomposition(jacobian).getSolver().getInverse();
            xkNewVector = xkVector.subtract(jacobianInverse.operate(functions));
            iterationNewtonMethod++;
            if (Math.abs(xkVector.getEntry(0) - xkNewVector.getEntry(0)) < Constants.epsilon && Math.abs(xkVector.getEntry(1) - xkNewVector.getEntry(1)) < Constants.epsilon) {
                break;
            }
            xkVector = xkNewVector;
        }
        return xkNewVector;
    }


    public int getIterationNewtonMethod(){
        return iterationNewtonMethod;
    }

}

