package ru.ssau.tk.fx.numericalmethods.controller;

import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;
import org.mariuszgromada.math.mxparser.Function;
import ru.ssau.tk.fx.numericalmethods.model.*;
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

    public double calculateHybridMethod(String expression, double xk) {
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


    public RealVector calculateNewtonMethod(String f1, String f2, double xValue, double yValue) {
        Function firstFunction = new Function("f1", new BivariateFunctionLab2(f1));
        Function secondFunction = new Function("f2", new BivariateFunctionLab2(f2));
        Function firstFunctionD = new Function("f1D", new DerivativeBivariateFunctionLab2(f1));
        Function secondFunctionD = new Function("f2D", new DerivativeBivariateFunctionLab2(f2));

        RealVector xkVector = new ArrayRealVector(new double[]{xValue, yValue}, false);
        RealVector xkNewVector;
        double distance = Double.MAX_VALUE;


        while (true) {
            RealVector functions = new ArrayRealVector(new double[]{firstFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1)), secondFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1))}, false);
            RealMatrix jacobian = new Array2DRowRealMatrix(new double[][]{{firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}, {secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}});
            RealMatrix jacobianInverse = new LUDecomposition(jacobian).getSolver().getInverse();
            xkNewVector = xkVector.subtract(jacobianInverse.operate(functions));
            iterationNewtonMethod++;
            if (Math.abs(xkVector.getEntry(0) - xkNewVector.getEntry(0)) < Constants.epsilon && Math.abs(xkVector.getEntry(1) - xkNewVector.getEntry(1)) < Constants.epsilon) {
                break;
            }
            if (xkNewVector.getDistance(xkVector) > distance) {
                throw new IllegalArgumentException();
            }
            distance = xkVector.getDistance(xkNewVector);
            xkVector = xkNewVector;
        }
        return xkNewVector;
    }

    public RealVector calculateModifiedNewtonMethod(String f1, String f2, double xValue, double yValue) {
        Function firstFunction = new Function("f1", new BivariateFunctionLab2(f1));
        Function secondFunction = new Function("f2", new BivariateFunctionLab2(f2));
        Function firstFunctionD = new Function("f1D", new DerivativeBivariateFunctionLab2(f1));
        Function secondFunctionD = new Function("f2D", new DerivativeBivariateFunctionLab2(f2));

        RealVector xkVector = new ArrayRealVector(new double[]{xValue, yValue}, false);
        RealVector xkNewVector;
        RealMatrix jacobian = new Array2DRowRealMatrix(new double[][]{{firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}, {secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}});
        RealMatrix jacobianInverse = new LUDecomposition(jacobian).getSolver().getInverse();

        double distance = Double.MAX_VALUE;
        while (true) {
            RealVector functions = new ArrayRealVector(new double[]{firstFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1)), secondFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1))}, false);

            xkNewVector = xkVector.subtract(jacobianInverse.operate(functions));
            iterationModifiedNewtonMethod++;
            if (Math.abs(xkVector.getEntry(0) - xkNewVector.getEntry(0)) < Constants.epsilon && Math.abs(xkVector.getEntry(1) - xkNewVector.getEntry(1)) < Constants.epsilon) {
                break;
            }
            if (xkNewVector.getDistance(xkVector) > distance) {
                throw new IllegalArgumentException();
            }
            distance = xkVector.getDistance(xkNewVector);
            xkVector = xkNewVector;
        }
        return xkNewVector;

    }

    public double getDiscrepancy(String f, double xValue, double yValue) {
        Function function = new Function("f", new BivariateFunctionLab2(f));
        return function.calculate(xValue, yValue);
    }


    public int getIterationNewtonMethod() {
        return iterationNewtonMethod;
    }

    public int getIterationModifiedNewtonMethod() {
        return iterationModifiedNewtonMethod;
    }


    public double calculateMNPlusOne(double a, double b) {
        DerivativeMyFunctionLab3 derivativeMyFunctionLab3 = new DerivativeMyFunctionLab3();

        double min = derivativeMyFunctionLab3.value(a);
        double shift = 0.000001;
        double tmpMin = 0.0;
        for (double i = a + shift; i < b; i += shift) {
            tmpMin = derivativeMyFunctionLab3.value(i);
            if (min > tmpMin)
                min = tmpMin;
        }

        double max = derivativeMyFunctionLab3.value(a);
        double tmpMax = 0.0;
        for (double i = a + shift; i < b; i += shift) {
            tmpMax = derivativeMyFunctionLab3.value(i);
            if (max < tmpMax)
                max = tmpMax;
        }
        return Math.max(Math.abs(max), Math.abs(min));
    }

    public double defineAStep(int n, double a, double b) {

        return FastMath.pow(Constants.epsilon * CombinatoricsUtils.factorial(n + 1) / calculateMNPlusOne(a, b), 0.25);
    }

    public double[] descretizeX(double a, double b, double h) {
        MyFunctionLab3 myFunctionLab3 = new MyFunctionLab3();
        int n = (int) Math.round((b - a) / h);
        double[] xArray = new double[n];

        double x = 0;

        for (int i = 0; i < n; i++) {
            xArray[i] = x;
            x += h;
        }

        return xArray;

    }

    public double[] descretizeY(double a, double b, double h) {
        MyFunctionLab3 myFunctionLab3 = new MyFunctionLab3();
        int n = (int) Math.round((b - a) / h);
        double x = 0;
        double[] yArray = new double[n];

        for (int i = 0; i < n; i++) {
            yArray[i] = myFunctionLab3.value(x);
            x += h;
        }
        return yArray;
    }

    public double interpolateWithLagrangePolynomial(double[] x, double[] y, double z) {
        double resultFunction = 0;
        for (int i = 0; i < x.length; i++) {
            double multiplier = 1;
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    multiplier *= (z - x[j]) / (x[i] - x[j]);
                }
            }
            resultFunction += y[i] * multiplier;
        }
        return resultFunction;
    }

    public double interpolateWithNewtonMethod(double z, double[] x, double[] y, double h) {
        double t = (z - x[0]) / h;
        double[] dy = new double[x.length - 1];
        for (int i = 0; i < x.length - 1; i++) {
            dy[i] = y[i + 1] - y[i];
        }

        double dy01 = dy[1] - dy[0];
        double dy12 = dy[2] - dy[1];
        double dy02 = dy12 - dy01;
        return y[0] + dy[0] * t + dy01 * t * (t - 1) / 2 + dy02 * t * (t - 1) * (t - 2) / 6;

    }

    public PolynomialSplineFunction interpolateLinearSpline(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new DimensionMismatchException(x.length, y.length);
        }

        if (x.length < 2) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS,
                    x.length, 2, true);
        }

        // Number of intervals.  The number of data points is n + 1.
        int n = x.length - 1;

        MathArrays.checkOrder(x);

        // Slope of the lines between the datapoints.
        final double[] m = new double[n];
        for (int i = 0; i < n; i++) {
            m[i] = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
        }

        final PolynomialFunction[] polynomials = new PolynomialFunction[n];
        final double[] coefficients = new double[2];
        for (int i = 0; i < n; i++) {
            coefficients[0] = y[i];
            coefficients[1] = m[i];
            polynomials[i] = new PolynomialFunction(coefficients);
        }

        return new PolynomialSplineFunction(x, polynomials);
    }

    public PolynomialSplineFunction interpolateParabolicSpline(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new DimensionMismatchException(x.length, y.length);
        }

        if (x.length < 3) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS,
                    x.length, 3, true);
        }

        // Number of intervals.  The number of data points is n + 1.
        final int n = x.length - 1;

        MathArrays.checkOrder(x);

        final double[] ak = new double[n + 1];
        final double[] bk = new double[n + 1];
        final double[] ck = new double[n + 1];

        ak[0] = y[0];
        for (int i = 1; i <= n; i++) {

            ak[i] = y[i - 1];
            bk[i] = bk[i - 1] + ck[i - 1] * (x[i] - x[i - 1]);
            ck[i] = (y[i] - ak[i] - bk[i] * (x[i] - x[i - 1])) / ((x[i] - x[i - 1]) * (x[i] - x[i - 1]));
        }
        final PolynomialFunction[] polynomials = new PolynomialFunction[n];
        final double[] coefficients = new double[3];
        for (int i = 0; i < n; i++) {
            coefficients[0] = ak[i + 1];
            coefficients[1] = bk[i + 1];
            coefficients[2] = ck[i + 1];
            polynomials[i] = new PolynomialFunction(coefficients);
        }
        return new PolynomialSplineFunction(x, polynomials);
    }


    public PolynomialSplineFunction interpolateCubicSpline(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new DimensionMismatchException(x.length, y.length);
        }

        if (x.length < 3) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS,
                    x.length, 3, true);
        }

        // Number of intervals.  The number of data points is n + 1.
        final int n = x.length - 1;

        MathArrays.checkOrder(x);

        // Differences between knot points
        final double[] h = new double[n];
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }

        final double[] mu = new double[n];
        final double[] z = new double[n + 1];
        mu[0] = 0d;
        z[0] = 0d;
        double g;
        for (int i = 1; i < n; i++) {
            g = 2d * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / g;
            z[i] = (3d * (y[i + 1] * h[i - 1] - y[i] * (x[i + 1] - x[i - 1]) + y[i - 1] * h[i]) /
                    (h[i - 1] * h[i]) - h[i - 1] * z[i - 1]) / g;
        }

        // cubic spline coefficients --  b is linear, c quadratic, d is cubic (original y's are constants)
        final double[] b = new double[n];
        final double[] c = new double[n + 1];
        final double[] d = new double[n];

        c[n] = 0d;
        c[0] = 0d;

        for (int j = n - 1; j > 0; j--) {
            c[j] = z[j] - mu[j] * c[j + 1];
        }

        for (int j = n - 1; j >= 0; j--) {
            b[j] = (y[j + 1] - y[j]) / h[j] - h[j] * (c[j + 1] + 2d * c[j]) / 3d;
            d[j] = (c[j + 1] - c[j]) / (3d * h[j]);
        }

        final PolynomialFunction[] polynomials = new PolynomialFunction[n];
        final double[] coefficients = new double[4];
        for (int i = 0; i < n; i++) {
            coefficients[0] = y[i];
            coefficients[1] = b[i];
            coefficients[2] = c[i];
            coefficients[3] = d[i];
            polynomials[i] = new PolynomialFunction(coefficients);
        }

        return new PolynomialSplineFunction(x, polynomials);
    }


    public double delta(BivariateFunction function, double x, double y, double h) {
        double f0 = h * function.value(x, y);
        double f1 = h * function.value(x + h / 2, y + f0 / 2);
        double f2 = h * function.value(x + h / 2, y + f1 / 2);
        double f3 = h * function.value(x + h, y + f2);
        return (f0 + 2 * f1 + 2 * f2 + f3) / 6;
    }

    public double defineStep(double h, double a, double b, double y0, double x0) {
        MyFunctionLab4 myFunction = new MyFunctionLab4();
        double h0 = h;
        double y1;
        double y2;
        double y2Double;
        double dyy2;
        int count = 0;
        while (true) {
            y1 = y0 + delta(myFunction, x0, y0, h0);
            y2 = y1 + delta(myFunction, x0 + h0, y1, h0);
            y2Double = y0 + delta(myFunction, x0, y0, 2 * h0);
            dyy2 = FastMath.abs(y2 - y2Double);
            if (dyy2 > Math.pow(10, -4)) {
                h0 = h0 / 2;
            } else {
                break;
            }
        }
        h0 = 2*h0;
        int n = (int) Math.round((b - a) / h0) + 1;
        if (n % 2 == 1) {
            n++;
        }
        h0 = (b - a) / n;
        return h0;
    }

    public double[] calculateRungeCutta(double[] x, double y0, double a, double b, double h) {
        MyFunctionLab4 myFunction = new MyFunctionLab4();
        int n = (int) Math.round((b - a) / h) + 1;
        double[] y = new double[n];
        y[0] = y0;
        for (int i = 1; i < n; i++) {
            y[i] = y[i - 1] + delta(myFunction, x[0] + (i - 1) * h, y[i - 1], h);
        }
        return y;
    }

    public double[] calculateEiler(double[] x, double y0, double a, double b, double h) {
        MyFunctionLab4 myFunction = new MyFunctionLab4();
        int n = (int) Math.round((b - a) / h) + 1;
        double[] y = new double[n];
        y[0] = y0;
        for (int i = 1; i < n; i++) {
            y[i] = y[i - 1] + h * myFunction.value(x[0] + (i - 1) * h, y[i - 1]);
        }
        return y;
    }


}

