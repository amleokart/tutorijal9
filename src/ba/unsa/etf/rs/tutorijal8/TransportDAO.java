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
    private PreparedStatement addBus;
    private PreparedStatement addDriver;
    private PreparedStatement dodajVozacaBuseva;
    private PreparedStatement obrisiBus;
    private PreparedStatement obrisiVozaca;
    private PreparedStatement obrisiVozacaBuseva;
    private PreparedStatement getDriversU;
    private PreparedStatement deleteBusU;
    private PreparedStatement deleteDriverU;
    private PreparedStatement addBusU;
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TransportDAO(){
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
            while (ulaz.hasNext()){
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.charAt(sqlUpit.length() - 1 ) == ';' ){
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

    public ArrayList<Driver> getDrivers() {
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        ResultSet result = null;
        try {
            result = getDriversU.executeQuery();
            Driver driver;
            while (  ( driver = dajVozaceUpit(result) ) != null )
                drivers.add(driver);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
    private Driver dajVozaceUpit(ResultSet result) {
        Driver driver = null;
        try {
            if (result.next() ){
                int id = result.getInt("id");
                String name = result.getString("name");
                String surname = result.getString("surname");
                String jmb = result.getString("JMBG");
                LocalDate rodjendan = convertToLocalDateViaSqlDate(result.getDate("birthday"));
                LocalDate datum_zap = convertToLocalDateViaSqlDate(result.getDate("employmentDate"));
                driver = new Driver( name , surname , jmb , rodjendan , datum_zap);
                driver.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public ArrayList<Bus> getBusses() {
        ArrayList<Bus> buses = new ArrayList<>();
        try {
            ResultSet result = getBusU.executeQuery();
            while(result.next()) {
                Integer id = result.getInt(1);
                String maker = result.getString(2);
                String series = result.getString(3);
                int seatNumber = result.getInt(4);
                getDodjelaVozaci.setInt(1, id);

                ResultSet ResultatDrugi = getDodjelaVozaci.executeQuery();
                Driver driver;
                ArrayList<Driver> drivers = new ArrayList<Driver>();
                while (ResultatDrugi.next()) {
                    id = ResultatDrugi.getInt(1);
                    String name = ResultatDrugi.getString(2);
                    String surname = ResultatDrugi.getString(3);
                    String JMBG = ResultatDrugi.getString(4);
                    Date birthday = ResultatDrugi.getDate(5);
                    Date employmentDate = ResultatDrugi.getDate(6);
                    drivers.add(new Driver(id, name, surname, JMBG, birthday.toLocalDate(), employmentDate.toLocalDate()));
                }
                if (drivers.size() == 1) {
                    buses.add(new Bus(id, maker, series, seatNumber, drivers.get(0), null));
                }
                else if (drivers.size() == 2) {
                    buses.add(new Bus(id, maker, series, seatNumber, drivers.get(0), drivers.get(1)));
                }
                else {
                    buses.add(new Bus(id, maker, series, seatNumber, null, null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;

    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public void addDriver(Driver driver){
        try {
            ResultSet rs = idDriver.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addDriver.setInt(1, id);
            addDriver.setString(2, driver.getName());
            addDriver.setString(3, driver.getSurname());
            addDriver.setString(4 , driver.getJMBG());
            addDriver.setDate(5 , convertToDateViaSqlDate(driver.getBirthday()));
            addDriver.setDate(6 , convertToDateViaSqlDate(driver.getEmploymentDate()));
            addDriver.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Izuzetak: Vozač već postoji.");
        }
    }
    public void addBus(Bus bus) {
        try {
            ResultSet rs = idBus.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addBusU.setInt(1, id);
            addBusU.setString(2, bus.getMaker());
            addBusU.setString(3, bus.getSeries());
            addBusU.setInt(4, bus.getSeatNumber());
            addBusU.setInt(5,bus.getDriverNumber());
            addBusU.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBus(Bus bus) {
        try {
            deleteBusU.setInt(1, bus.getId());
            deleteBusU.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteDriver(Driver driver) {
        try {
            deleteDriverU.setInt(1, driver.getId());
            deleteDriverU.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetDatabase() {
        try {
            obrisiVozacaBuseva.executeUpdate();
            obrisiBus.executeUpdate();
            obrisiVozaca.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
        try {
            dodajVozacaBuseva.setInt(1 , bus.getId());
            dodajVozacaBuseva.setInt(2, driver.getId());
            dodajVozacaBuseva.executeUpdate();
            if(which == 1){
                bus.setDriverOne(driver);
            }
            if (which == 2){
                bus.setDriverTwo(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



    /*private void TestDatabase() {
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
    }*/
