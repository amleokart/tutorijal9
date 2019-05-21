package ba.unsa.etf.rs.tutorijal8;

import java.time.LocalDate;

public class Driver {
    private Integer id;
    private String name;
    private String surname;
    private String JMBG;
    private LocalDate birthday;
    private LocalDate employmentDate;

    public Driver() {
    }

    public Driver(Integer id, String name, String surname, String JMBG, LocalDate birthday, LocalDate employmentDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.birthday = birthday;
        this.employmentDate = employmentDate;
    }

    public Driver(int id , String name, String surname, String JMBG, LocalDate birthday, LocalDate employmentDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.birthday = birthday;
        this.employmentDate = employmentDate;
    }

    public Driver(String test, String testović, String s, LocalDate minusYears, LocalDate now) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public String toString() {
        return  " - (" + this.getName() + " " + this.getSurname() + " ( " + this.getJMBG() + " ))";
    }

    public boolean equals(Driver d) {
        return (d.getJMBG().equals(this.getJMBG()));
    }
}
