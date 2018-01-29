/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetest;

/**
 *
 * @author Tobias
 */
public class Mandelbrot {

    public static int check(Complex c, int iterations) {
        Complex z0 = new Complex(0, 0);
        int curIter = 0;
        return test(curIter, z0, c, iterations);
    }

    public static int test(int curIter, Complex z0, Complex c, int iterations) {
        double abs = Complex.abs(Complex.add(Complex.square(z0), c));
        if (abs >= 2) {
            return curIter;
        }
        if (curIter == iterations - 1) {
            return 0;
        } else {
            return test(curIter + 1, Complex.add(Complex.square(z0), c), c, iterations);
        }
    }
}
