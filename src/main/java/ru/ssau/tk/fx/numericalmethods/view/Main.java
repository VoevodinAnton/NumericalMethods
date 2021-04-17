package ru.ssau.tk.fx.numericalmethods.view;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import ru.ssau.tk.fx.numericalmethods.model.FirstLabFunction;

public class Main {
    public static void main(String[] args) {
        FirstLabFunction f = new FirstLabFunction();
        int params = 1;
        int order = 3;
        double xRealValue = -2.29;
        DerivativeStructure x = new DerivativeStructure(params, order, 0, xRealValue);
        DerivativeStructure y = f.value(x);
        System.out.println("y    = " + y.getValue());
        System.out.println("y'   = " + y.getPartialDerivative(1));
        System.out.println("y''  = " + y.getPartialDerivative(2));
        System.out.println("y''' = " + y.getPartialDerivative(3));




        double[][] matrixData2 = { {1d,2d}, {2d,5d}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);
    }
}
