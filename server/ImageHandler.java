/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.Dataset;
import common.Batch;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author udde
 */
public class ImageHandler {

    HashMap<Integer, Batch> batches;
    ArrayList<Integer> batchNrs;
    int batchHeight = 5;
    int batchAmount;
    HashMap<Integer, Dataset> datasets;
    ArrayList<Integer> result;
    private boolean imageRendered = false;

    private final String FILENAME = "mandelbrot.png";

    int imageHeight = 3000;
    int imageWidth = 2000;
    int iterations = 1000;  
    double startRe = -0.141;//-2;
    double startIm = 0.8505;//-1.2
    double offset = -0.0005;//0.002;

    public ImageHandler() {
        batches = new HashMap<>();
        datasets = new HashMap<>();
        result = new ArrayList<>();
        batchNrs = new ArrayList<>();
        createBatches();
    }

    public void createBatches() {
        int height = imageHeight;
        int restHeight = height % batchHeight;
        height -= restHeight;
        int fullBatches = height / batchHeight;
        for (int i = 0; i < fullBatches; i++) {
            double currentHeightOffset = batchHeight * offset * i;
            batches.put(i, new Batch(startRe, startIm + currentHeightOffset, batchHeight, imageWidth, offset, iterations, i));
            batchNrs.add(i);
        }
        if (restHeight != 0) {
            batches.put(fullBatches, new Batch(startRe, startIm + (batchHeight * offset * fullBatches), restHeight, imageWidth, offset, iterations, fullBatches));
            batchNrs.add(fullBatches);
            batchAmount = fullBatches + 1;
        } else {
            batchAmount = fullBatches;
        }
        System.out.println("The image is split into "+batches.size()+" batches.");
    }

    public boolean hasMoreBatches() {
        return !batches.isEmpty();
    }

    public Batch fetchBatch() {
        if (!hasMoreBatches()) {
            return new Batch(0, 0, 0, 0, 0, 0, -1);
        } 
        Batch nextBatch = null;
        int batchNr;
        while (nextBatch == null) {
            batchNr = batchNrs.remove(0);
            nextBatch = batches.get(batchNr);
            if (nextBatch != null) {
                batchNrs.add(batchNr);
            }
        }
        return nextBatch;
    }

    public void recieveDataset(Dataset dataset) {
        batches.remove(dataset.batchNr);
        datasets.put(dataset.batchNr, dataset);
        System.out.println("dataset " + dataset.batchNr + " recieved");
        checkBatches();
    }

    public void checkBatches() {
        if ((datasets.size() == batchAmount) && !imageRendered) {
            batches.clear();
            imageRendered = true;
            for (int i = 0; i < batchAmount; i++) {
                result.addAll(datasets.get(i).data);
            }
            renderImage();
        }
    }

    public void renderImage() {
        System.out.println("rendering image");
        Integer[] resultInt = new Integer[result.size()];
        resultInt = result.toArray(resultInt);
        int[] data = new int[resultInt.length];
        for (int i = 0; i < resultInt.length; i++) {
            data[i] = resultInt[i];
        }
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        System.out.println(data.length);

        image.setRGB(0, 0, imageWidth, imageHeight, data, 0, imageWidth);
        File outputfile = new File(FILENAME);
        try {
            ImageIO.write(image, "png", outputfile);
            System.out.println(FILENAME + " saved.");
        } catch (IOException ex) {
            System.out.println("could not save image");
        }
        System.out.println("Done.");
    }
}
