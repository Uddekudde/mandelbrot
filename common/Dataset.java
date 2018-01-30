/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author udde
 */
public class Dataset implements Serializable {

    public ArrayList<Integer> data;
    public int batchNr;

    public Dataset(ArrayList<Integer> data, int batchNr){
        this.data = new ArrayList<>(data);
        this.batchNr = batchNr;
    }
}
