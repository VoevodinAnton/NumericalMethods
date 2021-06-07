package ru.ssau.tk.fx.numericalmethods.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DataLab4 {
    DoubleProperty x;
    DoubleProperty y;
    DoubleProperty RungeCutta;
    DoubleProperty Eiler;
    DoubleProperty errorRungeCutta;
    DoubleProperty errorEiler;

    public DataLab4(double x, double y, double rungeCutta, double eiler, double errorRungeCutta, double errorEiler) {
        this.x = new SimpleDoubleProperty(this, "x", x);
        this.y = new SimpleDoubleProperty(this, "y", y);
        RungeCutta = new SimpleDoubleProperty(this, "rungeCutta", rungeCutta);
        Eiler = new SimpleDoubleProperty(this, "eiler", eiler);
        this.errorRungeCutta = new SimpleDoubleProperty(this, "errorRungeCutta", errorRungeCutta);
        this.errorEiler = new SimpleDoubleProperty(this, "errorEiler", errorEiler);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getRungeCutta() {
        return RungeCutta.get();
    }

    public DoubleProperty rungeCuttaProperty() {
        return RungeCutta;
    }

    public void setRungeCutta(double rungeCutta) {
        this.RungeCutta.set(rungeCutta);
    }

    public double getEiler() {
        return Eiler.get();
    }

    public DoubleProperty eilerProperty() {
        return Eiler;
    }

    public void setEiler(double eiler) {
        this.Eiler.set(eiler);
    }

    public double getErrorRungeCutta() {
        return errorRungeCutta.get();
    }

    public DoubleProperty errorRungeCuttaProperty() {
        return errorRungeCutta;
    }

    public void setErrorRungeCutta(double errorRungeCutta) {
        this.errorRungeCutta.set(errorRungeCutta);
    }

    public double getErrorEiler() {
        return errorEiler.get();
    }

    public DoubleProperty errorEilerProperty() {
        return errorEiler;
    }

    public void setErrorEiler(double errorEiler) {
        this.errorEiler.set(errorEiler);
    }
}
