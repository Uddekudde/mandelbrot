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
import javax.imageio.ImageIO;

/**
 *
 * @author udde
 */
public class ImageHandler {

    ArrayList<Batch> batches;
    int batchHeight = 5;
    int batchAmount;
    HashMap<Integer, Dataset> datasets;
    ArrayList<Integer> result;

    int imageHeight = 3000;
    int imageWidth = 2000;
    int iterations = 1000;//1500;  
    double startRe = -0.141;//-2;
    double startIm = 0.8505;//-1.2
    double offset = -0.0005;//0.002;

    public ImageHandler() {
        batches = new ArrayList<>();
        datasets = new HashMap<>();
        result = new ArrayList<>();
        createBatches();
    }

    public void createBatches() {
        int height = imageHeight;
        int restHeight = height % batchHeight;
        height -= restHeight;
        int fullBatches = height / batchHeight;
        for (int i = 0; i < fullBatches; i++) {
            double currentHeightOffset = batchHeight * offset * i;
            batches.add(new Batch(startRe, startIm + currentHeightOffset, batchHeight, imageWidth, offset, iterations, i));
        }
        if (restHeight != 0) {
            batches.add(new Batch(startRe, startIm + (batchHeight * offset * fullBatches), restHeight, imageWidth, offset, iterations, fullBatches));
            batchAmount = fullBatches + 1;
        } else {
            batchAmount = fullBatches;
        }
        System.out.println(batches.size());
    }

    public boolean hasMoreBatches() {
        return !batches.isEmpty();
    }

    public Batch fetchBatch() {
        return batches.remove(0);
    }

    public void recieveDataset(Dataset dataset) {
        datasets.put(dataset.batchNr, dataset);
        System.out.println("dataset " + dataset.batchNr + " recieved");
    }

    public void checkBatches() {
        if (datasets.size() == batchAmount) {
            for (int i = 0; i < batchAmount; i++) {
                result.addAll(datasets.get(i).data);
            }
            renderImage();
        }
        System.out.println(datasets.size());
    }

    public void renderImage() {
        System.out.println("rendering image");
        Integer[] resultInt = new Integer[result.size()];
        resultInt = result.toArray(resultInt);
        int[] data = new int[resultInt.length];
        for (int i = 0; i < resultInt.length; i++) {
            data[i] = resultInt[i];
        }
        imageHeight = data.length / imageHeight;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        System.out.println(data.length);

        image.setRGB(0, 0, imageWidth, imageHeight, data, 0, imageWidth);
        File outputfile = new File("mandelbrot.png");
        try {
            ImageIO.write(image, "png", outputfile);
            System.out.println("mandelprot.png saved.");
        } catch (IOException ex) {
            System.out.println("could not save image");
        }
    }
}
