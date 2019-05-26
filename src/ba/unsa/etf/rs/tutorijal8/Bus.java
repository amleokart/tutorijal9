package ba.unsa.etf.rs.tutorijal8;

public class Bus {
    private Integer id;
    private String maker;
    private String series;
    private Integer seatNumber;
    private Integer driverNumber;
    private Driver DriverOne = null;
    private Driver DriverTwo = null;

    public Bus(){ }

    public void setDriverOne(Driver driverOne) {
        DriverOne=driverOne;
    }

    public void setDriverTwo(Driver driverTwo) {
        DriverTwo=driverTwo;
    }

    public Bus(String maker, String series, int seatNumber) {
        this.maker = maker;
        this.series = series;
        this.seatNumber = seatNumber;
    }

    public Bus(int id, String maker, String series, int seatNumber) {
        id = id;
        this.maker = maker;
        this.series = series;
        this.seatNumber = seatNumber;
    }

    public Bus(int id, String maker, String series, int seatNumber, Driver DriverOne, Driver DriverTwo) {
        id = id;
        this.maker = maker;
        this.series = series;
        this.seatNumber = seatNumber;
        this.DriverOne = DriverOne;
        this.DriverOne = DriverTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public Driver getDriverOne() {
        return DriverOne;
    }

    public Driver getDriverTwo() {
        return DriverTwo;
    }

    @Override
    public String toString () {
        String string = "";
        string += this.maker + " " + this.series + " ( seats: " + this.getSeatNumber() + " )";
        if (DriverOne != null) {
            string += DriverOne.toString();
        }
        if (DriverTwo != null) {
            string += DriverTwo.toString();
        }
        return string;
    }
}
