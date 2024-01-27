package org.firstinspires.ftc.teamcode.utilities;

public class Pair <T, U> {

    public T first;
    public U second;

    public static Pair<Double, Double> make(double x, double y) {

        Pair <Double, Double> ret = new Pair<>();
        ret.first = x;
        ret.second = y;
        return ret;
    }

    public static Pair <Integer, Integer> make(int x, int y) {

        Pair <Integer, Integer> ret = new Pair<>();
        ret.first = x;
        ret.second = y;
        return ret;
    }
}