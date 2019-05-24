package ba.unsa.etf.rs.tutorijal8;

import org.sqlite.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TransportDAO {

    private static TransportDAO instance;
    private Connection conn;
    private static PreparedStatement addBus;
    private static PreparedStatement getBus;
    private static PreparedStatement deleteBus;
    private static PreparedStatement addDriver;
    private static PreparedStatement getDriver;
    private static PreparedStatement deleteDriver;
    private static PreparedStatement truncBusses;
    private static PreparedStatement truncDrivers;

    private TransportDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite::baza.db");
            TestDatabase();
            InitializeStatments();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  void TestDatabase() {
        try {
            Statement s=conn.createStatement();
            s.execute("SELECT * FROM Bus");
            System.out.println("Database exists.");
        } catch(SQLException e) {
            InitializeDatabase();
            e.printStackTrace();
        }
    }

    private void InitializeDatabase() {
        try {
            Scanner entrance=new Scanner(new FileReader("baza.db")).useDelimiter(";");
            while(entrance.hasNext()) {
                String nextStatment=entrance.next();
                while(!nextStatment.trim().isEmpty()) {
                    Statement s=conn.createStatement();
                    s.execute(nextStatment);
                }
            }
            System.out.println("Database initialization complete.");
        } catch (FileNotFoundException | SQLException e) {
            System.out.println("Error initializing database!");
            e.printStackTrace();
        }
    }

    private void InitializeStatments() {
    }


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
}
