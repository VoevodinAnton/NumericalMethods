package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class MyFunctionLab3 implements UnivariateDifferentiableFunction {
    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.multiply(Math.PI).multiply((t.divide(2).add(10)).pow(-1));
    }

    @Override
    public double value(double x) {
        return Math.PI*x*Math.pow(10+x/2, -1);
    }
}
