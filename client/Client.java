/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.Batch;
import common.Dataset;
import common.MandelbrotServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import mandelbrot.Colors;
import mandelbrot.Complex;
import mandelbrot.Mandelbrot;

/**
 *
 * @author udde
 */
public class Client {

    MandelbrotServer server;
    private final String host = "127.0.0.1";
    boolean running;
    int[] data;

    private void lookupServer() throws NotBoundException, MalformedURLException,
            RemoteException {
        server = (MandelbrotServer) Naming.lookup(
                "//" + host + "/" + MandelbrotServer.SERVER_NAME_IN_REGISTRY);
    }

    private void run() {
        try {
            try {
                lookupServer();
                running = true;
            } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                System.out.println("Error connecting to server.");
            }
            Batch currentBatch;
            while (running) {
                currentBatch = server.getJob();
                System.out.println("batch "+currentBatch.batchNr+ " recieved.");
                if (currentBatch.batchNr == -1) {
                    running = false;
                    break;
                }
                data = new int[currentBatch.height * currentBatch.width];
                calculateBatch(currentBatch);
                ArrayList<Integer> dataList = new ArrayList<>();
                for(int pixel : data){
                dataList.add(pixel);
                }
                server.recieveResult(new Dataset(dataList, currentBatch.batchNr));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done.");
    }

    public void calculateBatch(Batch batch) {
        System.out.println("calculating batch, Height: "+batch.height);
        int pixel = 0;
        for (int y = 0; y < batch.height; y++) {
            for (int x = 0; x < batch.width; x++) {
                Complex current = new Complex(batch.startRe + (x * batch.offset), batch.startIm + (y * batch.offset));
                int depth = Mandelbrot.check(current, batch.iterations);
                int[] rgb = Colors.convert(depth, batch.iterations);
                data[pixel++] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();

    }

}
