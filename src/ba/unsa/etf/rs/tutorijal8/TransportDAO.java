package ba.unsa.etf.rs.tutorijal8;

import org.sqlite.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TransportDAO {
    private static TransportDAO instance;
    private Connection conn;
    private Driver driver;
    private PreparedStatement addBusU;
    private PreparedStatement addDriver;
    private PreparedStatement dodajVozacaBuseva;
    private PreparedStatement obrisiBus;
    private PreparedStatement obrisiVozaca;
    private PreparedStatement obrisiVozacaBuseva;
    private PreparedStatement getDriversU;
    private PreparedStatement deleteBusU;
    private PreparedStatement deleteDriverU;
    private PreparedStatement idBus;
    private PreparedStatement idDriver;
    private PreparedStatement getBusU;
    private PreparedStatement getDodjelaVozaci;

    public static TransportDAO getInstance() {
        if (instance == null)
            instance = new TransportDAO();
        return instance;
    }

    static {
        try {
            DriverManager.registerDriver(new JDBC());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TransportDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            obrisiBus = conn.prepareStatement("DELETE FROM Bus");
        } catch (SQLException e) {
            TestDatabase();
            e.printStackTrace();
        }
    }

    public void TestDatabase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ulaz.close();
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
