package ba.unsa.etf.rs.tutorijal8;

//fx:controller="ba.unsa.etf.rs.tutorijal8.Controller"

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    public Button addbusButton;
    public Button deletebusButton;
    public Button exitbusButton;
    public TableColumn columnMaker;
    public TableColumn columnSeries;
    public TableColumn columnSeatNumber;
    public Button adddriverButton;
    public Button deletedriverButton;
    public Button exitdriverButton;
    public TableColumn columnName;
    public TableColumn columnSurname;
    public TableColumn columnPersonalIDNumber;
    public TableColumn columnEmploymentDate;
    public TextField nameDriver;
    public TextField surnameDriver;
    public TextField JMBGDriver;
    public DatePicker BirthdayDateDriver;
    public DatePicker EmploymentDateDriver;
    public TextField MakerBus;
    public TextField SeriesBus;
    public TextField SeatNumberBus;
    public TableView driverTable;
    public TableView busTable;
    private TransportDAO transportModel;

    public Controller(TransportDAO m) {
        transportModel = m;
    }

    private void povezivanje() {
        nameDriver.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentPersonDriver().getName()));
        surnameDriver.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentPersonDriver().getSurname()));
        JMBGDriver.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentPersonDriver().getJMBG()));
        BirthdayDateDriver.valueProperty().bindBidirectional(transportModel.getCurrentPersonDriver().birthdayDateProperty());
        EmploymentDateDriver.valueProperty().bindBidirectional(transportModel.getCurrentPersonDriver().employmentDateProperty());
        MakerBus.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentPersonBus().getMaker()));
        SeriesBus.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentPersonBus().getSeries()));
        SeatNumberBus.textProperty().bindBidirectional(new SimpleIntegerProperty(transportModel.getCurrentPersonBus().getSeatNumber()), new NumberStringConverter());
    }

    private void odvezivanje() {
        nameDriver.textProperty().unbindBidirectional(transportModel.getCurrentPersonDriver().getName());
        surnameDriver.textProperty().unbindBidirectional(transportModel.getCurrentPersonDriver().getSurname());
        JMBGDriver.textProperty().unbindBidirectional(transportModel.getCurrentPersonDriver().getJMBG());
        BirthdayDateDriver.valueProperty().unbindBidirectional(transportModel.getCurrentPersonDriver().birthdayDateProperty());
        EmploymentDateDriver.valueProperty().unbindBidirectional(transportModel.getCurrentPersonDriver().employmentDateProperty());
        MakerBus.textProperty().unbindBidirectional(transportModel.getCurrentPersonBus().getMaker());
        SeriesBus.textProperty().unbindBidirectional(transportModel.getCurrentPersonBus().getSeries());
        SeatNumberBus.textProperty().unbindBidirectional(transportModel.getCurrentPersonBus().getSeatNumber());
    }

    public void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<Driver, String>("Name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Driver, String>("Surname"));
        columnPersonalIDNumber.setCellValueFactory(new PropertyValueFactory<Driver, String>("jmb"));
        columnEmploymentDate.setCellValueFactory(new PropertyValueFactory<Driver, LocalDate>("birthday"));
        columnMaker.setCellValueFactory(new PropertyValueFactory<Driver, LocalDate>("hireDate"));
        columnSeries.setCellValueFactory(new PropertyValueFactory<Bus, String>("maker"));
        columnSeatNumber.setCellValueFactory(new PropertyValueFactory<Bus, String>("series"));
        povezivanje();
        driverTable.setItems(transportModel.getDriver());
        busTable.setItems(transportModel.getBus());
        driverTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Driver>() {
            @Override
            public void changed(ObservableValue<? extends Driver> observableValue, Driver staraOsoba, Driver novaOsoba) {
                if (staraOsoba != null) {
                    odvezivanje();
                }
                if (novaOsoba == null) {
                    nameDriver.setText("");
                    surnameDriver.setText("");
                    JMBGDriver.setText("");
                    BirthdayDateDriver.getEditor().setText("");
                    EmploymentDateDriver.getEditor().setText("");
                } else {
                    Driver driver = (Driver) driverTable.getSelectionModel().getSelectedItem();
                    odvezivanje();
                    transportModel.setCurrentPersonDriver(driver);
                    povezivanje();
                    driverTable.refresh();
                }
                driverTable.refresh();
            }
        });
        driverTable.requestFocus();
        driverTable.getSelectionModel().selectFirst();

        busTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Bus>() {
            @Override
            public void changed(ObservableValue<? extends Bus> observableValue, Bus staraOsoba, Bus novaOsoba) {
                if (staraOsoba != null) {
                    odvezivanje();
                }
                if (novaOsoba == null) {
                    MakerBus.setText("");
                    SeriesBus.setText("");
                    SeatNumberBus.setText("");
                } else {
                    Bus bus = (Bus) busTable.getSelectionModel().getSelectedItem();
                    odvezivanje();
                    transportModel.setCurrentPersonBus(bus);
                    povezivanje();
                    busTable.refresh();
                }
                busTable.refresh();
            }
        });
        busTable.requestFocus();
        busTable.getSelectionModel().selectFirst();
    }

    public void addNewDriver(ActionEvent actionEvent) {
        transportModel.addDriver();
        odvezivanje();
        transportModel.setCurrentPersonDriver(transportModel.getDriver().get(transportModel.getDriver().size()-1));
        povezivanje();
        driverTable.refresh();
    }

    public void deleteCurrentDriver(ActionEvent actionEvent) {
        transportModel.getDriver().removeAll((Driver) driverTable.getSelectionModel().getSelectedItem());
        driverTable.refresh();
    }

    public void exitDriverWindow(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void addNewBus(ActionEvent actionEvent) {
        transportModel.addBus();
        odvezivanje();
        transportModel.setCurrentPersonBus(transportModel.getBus().get(transportModel.getBus().size()-1));
        povezivanje();
        busTable.refresh();
    }

    public void deleteCurrentBus(ActionEvent actionEvent) {
        transportModel.getBus().removeAll((Bus) busTable.getSelectionModel().getSelectedItem());
        busTable.refresh();
        }

    public void exitBusWindow(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
