package ru.ssau.tk.fx.numericalmethods.model;

public class FirstLabFunction implements MathFunction{
    @Override
    public double apply(double x) {
        return Math.pow(x, 3) - 0.8 * Math.pow(x, 2) - 6.8 * x + 0.7;
    }
}
