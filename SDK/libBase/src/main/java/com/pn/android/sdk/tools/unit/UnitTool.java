package com.pn.android.sdk.tools.unit;

/**
 * 运算单位工具类
 */
public final class UnitTool {

    private static final Object lock = new Object();
    private static volatile UnitTool instance;

    private UnitTool() {
    }

    public static UnitTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new UnitTool();
                }
            }
        }
        return instance;
    }

    /**
     * 摄氏度转华氏度
     */
    public float c2f(float c) {
        float f = c * 9 / 5 + 32;
        return f;
    }

    //华氏度转摄氏度
    public float f2c(float f) {
        float c = (f - 32) * 5 / 9;
        return c;
    }

    public double km2mi(double km) {
        double mi = km * 0.621;
        return mi;
    }

    public double mi2km(double mi) {
        double km = mi / 0.621;
        return km;
    }

    public double l2gal(double l) {
        double gal = l * 0.264;
        return gal;
    }

    public double l100km2mpg(double l100km) {
        double mpg = 235 / l100km;
        return mpg;
    }

    public double mpg2l100km(double mpg) {
        double l100km = 235 / mpg;
        return l100km;
    }
}
