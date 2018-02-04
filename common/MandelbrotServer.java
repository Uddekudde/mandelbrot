/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Tobias
 */
public interface MandelbrotServer extends Remote {
    
    public static final String SERVER_NAME_IN_REGISTRY = "MANDELBROT_SERVER";
    
    Batch getJob() throws RemoteException;
    
    void recieveResult(Dataset dataset) throws RemoteException;
        
}
