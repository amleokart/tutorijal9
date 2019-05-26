package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Driver {
    private Integer ID = -1;
    private String name;
    private String surname;
    private String JMBG;
    private SimpleObjectProperty<LocalDate> birthdayDate = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> employmentDate = new SimpleObjectProperty<>();


    public Driver() {
        name = "NULL";
        surname = "NULL";
        JMBG = "NULL";
        birthdayDate.set(LocalDate.of(1,1,1));
        employmentDate.set(LocalDate.of(1,1,1));
    }

    public Driver(String name, String surname, String JMBG, LocalDate birthdayDate, LocalDate employmentDate) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        setBirthdayDate(birthdayDate);
        setEmploymentDate(employmentDate);
    }

    public Driver(Integer ID, String name, String surname, String JMBG, LocalDate birthdayDate, LocalDate employmentDate) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        setBirthdayDate(birthdayDate);
        setEmploymentDate(employmentDate);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJMBG() {
        return JMBG;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname() + " ( " + this.getJMBG() + " )";
    }

    public boolean equals(Driver d) {
        return (d.getJMBG().equals(this.getJMBG()));
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate.get();
    }

    public SimpleObjectProperty<LocalDate> birthdayDateProperty() {
        return birthdayDate;
    }

    private void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate.set(birthdayDate);
    }

    public LocalDate getEmploymentDate() {
        return employmentDate.get();
    }

    public SimpleObjectProperty<LocalDate> employmentDateProperty() {
        return employmentDate;
    }

    private void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate.set(employmentDate);
    }
}
