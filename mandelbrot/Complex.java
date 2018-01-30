/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;

/**
 *
 * @author Tobias
 */
public class Complex {

    double re;
    double im;

    public Complex(double real, double imaginary) {
        re = real;
        im = imaginary;
    }

    public Complex(Complex complex) {
        re = complex.re;
        im = complex.im;
    }

    public static Complex add(Complex first, Complex second) {
        return new Complex(first.re + second.re, first.im + second.im);
    }

    public static Complex square(Complex number) {
        return new Complex((number.re * number.re) - 
                (number.im * number.im), 2 * (number.im * number.re));
    }

    public static double abs(Complex number) {
        return Math.sqrt(Math.pow(number.re, 2) + Math.pow(number.im, 2));
    }
}
