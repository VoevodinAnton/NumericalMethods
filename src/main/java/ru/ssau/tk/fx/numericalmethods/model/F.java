package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class F implements UnivariateDifferentiableFunction {

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.pow(3).subtract(t.pow(2).multiply(0.8)).subtract(t.multiply(6.8)).add(0.7);
    }

    @Override
    public double value(double x) {
        return Math.pow(x, 3) - 0.8 * Math.pow(x, 2) - 6.8 * x + 0.7;
    }
}
