package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ResidentRepo;
import services.ResidentServices;

import java.time.LocalDate;
import java.util.List;

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

    private void showErrorAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void findResident(String info){
        if(info.isEmpty()){
            myTable.setItems(masterData);
        }
        List<Resident> residents = residentServices.findByContainInfo(info);
        ObservableList<Resident> items = FXCollections.observableArrayList(residents);
        myTable.setItems(items);
    }

    public void addResident(Resident resident){
        if(!residentServices.addResident(resident)){
            this.showErrorAlert("Không thể thêm cư dân !");
        }else{
            masterData.add(resident);
        }
    }

    public void deleteResident(Resident resident){
        if(!residentServices.deleteResident(resident)){
            this.showErrorAlert("Không thể xóa cư dân!");
        }else{
            masterData.remove(resident);
        }
    }
}
