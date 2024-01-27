package org.firstinspires.ftc.teamcode.utilities;

public class Matrix {

    // data

    private int n;
    private double[][] a;
    private double[][] aux;

    public void set(int length) {
        n = length;
    }

    public void set(int i, int j, double val) {
        a[i][j] = val;
    }

    public double get(int i, int j) {
        return a[i][j];
    }

    public int getN() {
        return n;
    }

    // initializations

    public Matrix() {

        a = new double[51][51];
        aux = new double[51][51];
    }

    public Matrix(Matrix cpy) {
        n = cpy.getN();

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                a[i][j] = cpy.get(i, j);
    }

    // checks

    boolean isEqual(Matrix b) {

        if (b == null || n != b.getN())
            return false;

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                if (a[i][j] != b.get(i, j))
                    return false;

        return true;
    }

    // fix double function

    private double fix(double val) {

        String s = String.format("%.10f", val);
        String r = "";

        int i = 0;

        while (i < s.length() && s.charAt(i) != '.')
            r += s.charAt(i++);
        r += '.';
        i++;

        int cnt = 0;

        for (int j = 1; j <= 2 && i < s.length(); i++, j++) {
            r += s.charAt(i);
            if (s.charAt(i) == '9')
                cnt++;
        }

        if (cnt == 2) {
            if (val < 0)
                return Math.floor(val);
            return (Math.floor(val)) + 1;
        }

        val = Double.valueOf(r);
        if (val == 0)
            return 0;
        return val;
    }

    public Matrix fixAll() {

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                a[i][j] = fix(a[i][j]);

        return this;
    }

    // matrix operations and calculates

    public double determinant() {

        double r = 1;

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                aux[i][j] = a[i][j];

        for (int i = 1; i <= n; i++) {

            int index = i;

            while (index <= n && aux[index][i] == 0)
                index++;

            if (index == n + 1)
                return 0;

            if (index != i) {
                for (int j = 1; j <= n; j++) {
                    double val = aux[i][j];
                    aux[i][j] = aux[index][j];
                    aux[index][j] = val;
                }

                if (((index - i) & 1) == 1)
                    r = -1;
            }

            for (int k = i + 1; k <= n; k++) {

                double p = aux[k][i] / aux[i][i];

                for (int j = i; j <= n; j++)
                    aux[k][j] -= p * aux[i][j];
            }
        }

        for (int i = 1; i <= n; i++)
            r *= aux[i][i];

        return r;
    }

    public Matrix multi(Matrix b) {

        Matrix r = new Matrix();
        r.set(n);

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                for (int k = 1; k <= n; k++)
                    r.set(i, j, r.get(i, j) + a[i][k] * b.a[k][j]);

        return r;
    }

    public Matrix transpose() {

        Matrix r = new Matrix();
        r.set(n);

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                r.set(j, i, a[i][j]);

        return r;
    }

    public Matrix delete(int x, int y) {

        Matrix r = new Matrix();
        r.set(n - 1);
        int line = 1, col;

        for (int i = 1; i <= n; i++) {

            if (i == x)
                continue;

            col = 1;

            for (int j = 1; j <= n; j++)
                if (j != y)
                    r.set(line, col++, a[i][j]);

            line++;
        }

        return r;
    }

    public Matrix adjunct() {

        Matrix tr = this.transpose();
        Matrix r = new Matrix();
        r.set(n);

        int startSgn = 1;

        for (int i = 1; i <= n; i++) {
            int sgn = startSgn;

            for (int j = 1; j <= n; j++) {
                r.set(i, j, sgn * tr.delete(i, j).determinant());
                sgn *= -1;
            }

            startSgn *= -1;
        }

        return r;
    }

    public Matrix inverse() {

        double det = this.determinant();

        if (det == 0)
            return null;

        Matrix r = this.adjunct();

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                r.set(i, j, r.get(i, j) / det);

        return r;
    }

    public Matrix power(int x) {

        if (x < 0)
            return this.inverse().power(-x);

        if (x == 0) {

            Matrix ret = new Matrix();
            ret.set(n);

            for (int i = 1; i <= n; i++)
                ret.set(i, i, 1);

            return ret;
        }

        Matrix p = this.power(x >> 1);
        p = p.multi(p);

        if ((x & 1) == 1)
            p = p.multi(this);

        return p;
    }
}