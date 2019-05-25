package ba.unsa.etf.rs.tutorijal8;

import org.sqlite.JDBC;

import java.io.*;
import java.net.URL;
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

    static {
        try {
            DriverManager.registerDriver(new JDBC());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SetupDatabase() {
        String sql="";
        URL x = getClass().getResource("/SetupDatabase.sql");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(x.getFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner entrance = new Scanner(bufferedReader);
            while(entrance.hasNextLine()){
                sql+=entrance.nextLine();
            }
            try {
                entrance.close();
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sql = sql.replace("\n"," ");
        sql = sql.replace(";","\n");
        String[] upiti = sql.split("\n");
        try {
            Statement statement = conn.createStatement();
            for (String upit : upiti){
                statement.execute(upit);
            }
        } catch (SQLException e) {

        }
    }

    public static TransportDAO getInstance() {
        if (instance == null)
            instance = new TransportDAO();
        return instance;
    }

    private void InitializeStatments() throws SQLException {
        addBus=conn.prepareStatement("INSERT INTO Bus VALUES (null, ?, ?, ?)");
        getBus=conn.prepareStatement("SELECT * FROM Bus");
        deleteBus=conn.prepareStatement("DELETE FROM Bus WHERE id=?");
        addDriver=conn.prepareStatement("INSERT INTO Driver VALUES (null, ?, ?, ?, ?, ?)");
        getDriver=conn.prepareStatement("SELECT * FROM Driver");
        deleteDriver=conn.prepareStatement("DELETE FROM Driver WHERE id=?");
        truncBusses=conn.prepareStatement("DELETE FROM Bus");
        truncDrivers=conn.prepareStatement("DELETE FROM Driver");
    }

    public void resetDatabase() {
        try {
            truncBusses.executeUpdate();
            truncDrivers.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Bus> getBusses() {
        ArrayList<Bus> busses = new ArrayList<Bus>();
        try {
            ResultSet resultSet = null;
            resultSet = getBus.executeQuery();
            Bus bus;
            while((bus = getBusFromResultSet(resultSet)) != null){
                busses.add(bus);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busses;
    }

    private Bus getBusFromResultSet(ResultSet resultSet) {
        Bus bus = null;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maker = resultSet.getString(2);
                String series = resultSet.getString(3);
                int seatNumber = resultSet.getInt(4);
                bus = new Bus(maker, series, seatNumber);
                bus.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Nije kreirana tabela Bus.");
        }
        return bus;
    }

    public ArrayList<Driver> getDrivers() {
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        try {
            ResultSet resultSet = null;
            resultSet = getDriver.executeQuery();
            Driver driver;
            while((driver = getDriverFromResultSet(resultSet)) != null){
                drivers.add(driver);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    private Driver getDriverFromResultSet(ResultSet resultSet) {
            Driver driver = null;
            try {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString(2);
                    String surname = resultSet.getString(3);
                    String JMBG = resultSet.getString(4);
                    LocalDate birthday = resultSet.getDate(5).toLocalDate();
                    LocalDate employmentDate = resultSet.getDate(6).toLocalDate();
                    driver = new Driver(name, surname, JMBG, birthday, employmentDate);
                    driver.setId(id);
                }
            } catch (SQLException e) {
                System.out.println("Nije kreirana tabela Driver.");
                e.printStackTrace();
            }
            return driver;
        }

    public void addBus(Bus bus) {
        try {
            addBus.setString(1, bus.getMaker());
            addBus.setString(2, bus.getSeries());
            addBus.setInt(3, bus.getSeatNumber());
            addBus.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Date convertToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public void deleteBus(Bus bus) {
        try {
            System.out.println(bus.getId());
            deleteBus.setInt(1, bus.getId());
            deleteBus.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDriver(Driver driver) {
        try {
            addDriver.setString(1, driver.getName());
            addDriver.setString(2, driver.getSurname());
            addDriver.setString(3, driver.getJMBG());
            addDriver.setDate(4, convertToDate(driver.getBirthday()));
            addDriver.setDate(5, convertToDate(driver.getEmploymentDate()));
            addDriver.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Vozač već postoji.");
        }
    }

    public void deleteDriver(Driver driver) {
        try {
            deleteDriver.setInt(1, driver.getId());
            deleteDriver.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
        if (which == 1) {
            bus.setDriverOne(driver);
        } else {
            bus.setDriverTwo(driver);
        }
    }
}
