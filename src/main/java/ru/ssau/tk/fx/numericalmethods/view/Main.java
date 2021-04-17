package ru.ssau.tk.fx.numericalmethods.view;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.mariuszgromada.math.mxparser.*;
import ru.ssau.tk.fx.numericalmethods.model.BivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeBivariateFunction;

public class Main {
    public static void main(String[] args) {
        /*
        F f = new F();
        int params = 1;
        int order = 3;
        double xRealValue = -2.29;
        DerivativeStructure x = new DerivativeStructure(params, order, 0, xRealValue);
        DerivativeStructure y = f.value(x);
        System.out.println("y    = " + y.getValue());
        System.out.println("y'   = " + y.getPartialDerivative(1));
        System.out.println("y''  = " + y.getPartialDerivative(2));
        System.out.println("y''' = " + y.getPartialDerivative(3));

        Function f1 = new Function("f(x) =x");
        Argument x1 = new Argument("x = 1");
        Argument y1 = new Argument("y = 2");
        Expression e = new Expression("der(x^3 - 0.8*x^2 - 6.8*x + 0.7 , x)",x1);
        System.out.println(e.getExpressionString() + " = " + e.calculate());

        String f2 = "x^3";
        Function function = new Function("f", new UnivariateFunction(f2));
        Function functionD = new Function("f", new DerivativeFunction(f2));
        System.out.println("1: " + function.calculate(2));
        System.out.println("2: " + functionD.calculate(2));

         */

        String f3 = "x^3+y^2";
        Function function2 = new Function("f", new DerivativeBivariateFunction(f3));
        System.out.println("3: " + function2.calculate(2, 2, 2));

        String f4 = "x^3+y^2";
        Function function3 = new Function("f", new BivariateFunction(f4));
        System.out.println("4: " + function3.calculate(2, 2));
        double[][] matrixData2 = { {1d,2d}, {2d,5d}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);
    }
}
