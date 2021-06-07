package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class DerivativeMyFunctionLab3 implements UnivariateDifferentiableFunction {
    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return ((t.add(20)).pow(-5).multiply(-960*Math.PI));
    }

    @Override
    public double value(double x) {
        return -960*Math.PI / Math.pow((x+20),5);
    }
}
