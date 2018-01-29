/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import common.MandelbrotServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Tobias
 */
public class Controller extends UnicastRemoteObject implements MandelbrotServer  {
    
    public Controller() throws RemoteException{
        
    }

}
