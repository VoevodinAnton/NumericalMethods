package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class FunctionSecondLab2 implements UnivariateDifferentiableFunction {
    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return null;
    }

    @Override
    public double value(double x) {
        return 0;
    }
}
