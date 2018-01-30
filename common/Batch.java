/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import java.io.Serializable;

/**
 *-
 * @author Tobias
 */
public class Batch implements Serializable {
    public double startRe;
    public double startIm;
    public int height;
    public int width;
    public double offset;
    public int batchNr;
    public int iterations;

    public Batch(double startRe, double startIm, int height, int width, double offset, int iterations, int batchNr) {
        this.startRe = startRe;
        this.startIm = startIm;
        this.height = height;
        this.width = width;
        this.offset = offset;
        this.batchNr = batchNr;
        this.iterations = iterations;
    }
    
    
}
