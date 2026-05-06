package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ResidentRepo;
import services.ResidentServices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResidentTableController {
    private static final Logger logger  = LoggerFactory.getLogger(ResidentTableController.class);
    private final ResidentServices residentServices;
    private final ObservableList<Resident> masterData = FXCollections.observableArrayList();
    @FXML private TableView<Resident> myTable;
    @FXML private TableColumn<Resident, String> idCol;
    @FXML private TableColumn<Resident, String> nameCol;
    @FXML private TableColumn<Resident, LocalDate> birthCol;
    @FXML private TableColumn<Resident, String> teleCol;
    @FXML private TableColumn<Resident, Void> actionCol;

    public ResidentTableController() {
        ResidentRepo residentRepo = new ResidentRepo();
        residentServices = new ResidentServices(residentRepo);
    }

    @FXML
    public void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("residentId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        Callback<TableColumn<Resident, Void>, TableCell<Resident, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Resident, Void> call(final TableColumn<Resident, Void> param) {
                final TableCell<Resident, Void> cell = new TableCell<>() {
                    private final Button btn = new Button(">");
                    {
                        btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-cursor: hand;");
                        btn.setOnAction((ActionEvent event) -> {
                            Resident selectedResident = getTableView().getItems().get(getIndex());
                            openDetailScreen(selectedResident);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        masterData.addAll(residentServices.findAll());
    }

    private void openDetailScreen(Resident resident){
        logger.info("Opening details of resident: {}", resident.getName());
    }

    public void findResident(String info){
        if(info.isEmpty()){
            myTable.setItems(masterData);
        }
        String infoLowerCase = info.toLowerCase();
        boolean containNumber = infoLowerCase.matches(",*\\d.*");
        Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        Matcher dateMatcher = datePattern.matcher(infoLowerCase);
        if(dateMatcher.find()){
            String extractedDate = dateMatcher.group();
            logger.info("Chuỗi tìm thấy: {}", extractedDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate localDate = LocalDate.parse(extractedDate, formatter);
                logger.info("Convert date complete: {}", localDate);
                List<Resident> result = residentServices.findByDate(localDate);
                ObservableList<Resident> items = FXCollections.observableArrayList(result);
                myTable.setItems(items);
            }catch (DateTimeParseException e) {
                logger.error("Cannot convert to form of local date: {}", e.getMessage(), e);
            }
        } else if (containNumber) {
            List<Resident> result = residentServices.findByNumber(info);
            ObservableList<Resident> items = FXCollections.observableArrayList(result);
            myTable.setItems(items);
        } else{
            List<Resident> result = residentServices.findByName(info);
            ObservableList<Resident> items = FXCollections.observableArrayList(result);
            myTable.setItems(items);
        }
    }
}
