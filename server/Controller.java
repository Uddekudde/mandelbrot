/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.Dataset;
import common.Batch;
import common.MandelbrotServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Tobias
 */
public class Controller extends UnicastRemoteObject implements MandelbrotServer {

    ImageHandler handler;

    public Controller() throws RemoteException {
        handler = new ImageHandler();
    }

    @Override
    synchronized public Batch getJob() throws RemoteException {
            return handler.fetchBatch();

    }

    @Override
    synchronized public void recieveResult(Dataset dataset) throws RemoteException {
        handler.recieveDataset(dataset);
    }

}
