package ru.ssau.tk.fx.numericalmethods.view;

import org.apache.commons.math3.linear.*;
import org.mariuszgromada.math.mxparser.*;
import ru.ssau.tk.fx.numericalmethods.model.BivariateFunction;
import ru.ssau.tk.fx.numericalmethods.model.DerivativeBivariateFunction;
import ru.ssau.tk.fx.numericalmethods.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



        String f3 = "x^3+y^2";
        Function function2 = new Function("f", new DerivativeBivariateFunction(f3));
        System.out.println("3: " + function2.calculate(2, 2, 2));

        String f4 = "x^3+y^2";
        Function function3 = new Function("f", new BivariateFunction(f4));
        System.out.println("4: " + function3.calculate(2, 2));
        double[][] matrixData2 = {{1d, 2d}, {2d, 5d}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);

         */


        String f1 = "y - x^3 + 3";
        String f2 = "y^2 - x - 3";
        double valueX = 3;
        double valueY = 3;

        Argument x = new Argument("x = 3");
        Expression definedExpression = new Expression("solve(sin(y) - 2*x - 1, y, -pi/2, pi/2)", x);
        System.out.println(definedExpression.calculate());


        String string = "aaaaa]";
        String[] strings = string.split("\\s*,\\s*");
        System.out.println(strings[0]);



        String str = "John Doe [      123456789]";
        String rx = "\\[(.*?)\\]";
        Pattern ptrn = Pattern.compile(rx);
        Matcher m = ptrn.matcher(str);
        if (m.find()){
            System.out.println(m.group(1));
        }


        /*
        Function firstFunction = new Function("f1", new BivariateFunction(f1));
        Function secondFunction = new Function("f2", new BivariateFunction(f2));
        Function firstFunctionD = new Function("f1D", new DerivativeBivariateFunction(f1));
        Function secondFunctionD = new Function("f2D", new DerivativeBivariateFunction(f2));

        RealVector xkVector = new ArrayRealVector(new double[]{valueX, valueY}, false);
        RealVector xkNewVector;


        while (true) {
            RealVector functions = new ArrayRealVector(new double[]{firstFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1)), secondFunction.calculate(xkVector.getEntry(0), xkVector.getEntry(1))}, false);
            System.out.println("functions:" + functions.toString());
            System.out.println("xkVector: " + xkVector);
            RealMatrix jacobian = new Array2DRowRealMatrix(new double[][]{{firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), firstFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}, {secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 1), secondFunctionD.calculate(xkVector.getEntry(0), xkVector.getEntry(1), 2)}});
            RealMatrix jacobianInverse = new LUDecomposition(jacobian).getSolver().getInverse();
            xkNewVector = xkVector.subtract(jacobianInverse.operate(functions));
            if (Math.abs(xkVector.getEntry(0) - xkNewVector.getEntry(0)) <  Constants.epsilon && Math.abs(xkVector.getEntry(1) - xkNewVector.getEntry(1)) < Constants.epsilon) {
                break;
            }
            xkVector = xkNewVector;
        }

        System.out.println(firstFunction.calculate(2.000000466, 2.2360680952));
        System.out.println(xkVector.getEntry(0));
        System.out.println(xkVector.getEntry(1));



         */



    }
}
