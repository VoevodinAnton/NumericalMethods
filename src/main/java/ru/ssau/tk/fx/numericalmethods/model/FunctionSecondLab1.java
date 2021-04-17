package ru.ssau.tk.fx.numericalmethods.model;

import org.apache.commons.math3.analysis.DifferentiableMultivariateFunction;
import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class FunctionSecondLab1 implements DifferentiableMultivariateVectorFunction {

    @Override
    public MultivariateMatrixFunction jacobian() {
        return null;
    }

    @Override
    public double[] value(double[] point) throws IllegalArgumentException {
        return new double[0];
    }
}
