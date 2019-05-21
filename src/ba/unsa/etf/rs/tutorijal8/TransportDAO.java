package ba.unsa.etf.rs.tutorijal8;

import java.util.ArrayList;

public class TransportDAO {
    private static TransportDAO instance;
    
    public static TransportDAO getInstance() {
        if (instance == null)
            instance = new TransportDAO();
        return instance;
    }

    public void deleteBus(Bus bus) {
    }

    public void resetDatabase() {
    }

    public ArrayList<Driver> getDrivers() {
        return null;
    }

    public ArrayList<Bus> getBusses() {
        return null;
    }

    public void addDriver(Driver driver) {
    }
    
    public void addBus (Bus bus) {
    }

    public void deleteDriver(Driver driver) {
    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
    }
}
