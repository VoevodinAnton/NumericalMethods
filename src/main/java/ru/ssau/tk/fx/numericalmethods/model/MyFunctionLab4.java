package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.util.FastMath;


public class MyFunctionLab4 implements BivariateFunction {

    @Override
    public double value(double x, double y) {
        return 4 * (FastMath.pow(x, 3) + 1) * FastMath.exp(-4 * x) * FastMath.pow(y, 2) - 4 * FastMath.pow(x, 3) * y;
    }
}
