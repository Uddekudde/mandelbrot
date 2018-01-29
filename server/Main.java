package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Main {

    public static void main(String[] args) {
        try {
            new Main().startRegistry();
            Naming.rebind(Controller.SERVER_NAME_IN_REGISTRY, new Controller());
            System.out.println("Server is running.");
        } catch (MalformedURLException | RemoteException ex) {
            System.out.println("Could not start chat server.");
        }
    }

    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException registryNotYetRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }
}