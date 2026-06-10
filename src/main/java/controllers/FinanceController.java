package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.HouseRecei;
import models.Receivable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.HouseReceiRepo;
import repository.ReceivableRepo;
import services.HouseReceiServices;
import services.ReceivableServices;
import user.AuthManager;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller trang quản lý khoản thu (Finance).
 * Hiển thị tất cả khoản thu, cho phép tìm kiếm, lọc theo trạng thái thanh toán.
 */
public class FinanceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);
    private static final DateTimeFormatter DT_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    private final ReceivableServices receivableServices =
            new ReceivableServices(new ReceivableRepo());
    private final HouseReceiServices houseReceiServices =
            new HouseReceiServices(new HouseReceiRepo());

    private final ObservableList<HouseRecei> masterData = FXCollections.observableArrayList();

    /* ── Navbar ── */
    @FXML private VBox navbar;
    @FXML private NavbarController navbarController;

    /* ── Toolbar ── */
    @FXML private ComboBox<String> statusFilter;
    @FXML private TextField searchField;

    /* ── Table ── */
    @FXML private TableView<HouseRecei> houseReceiTable;
    @FXML private TableColumn<HouseRecei, String>   colHouseId;
    @FXML private TableColumn<HouseRecei, String>   colReceiName;
    @FXML private TableColumn<HouseRecei, Long>     colQuantity;
    @FXML private TableColumn<HouseRecei, Long>     colPrice;
    @FXML private TableColumn<HouseRecei, Instant>  colDeadline;
    @FXML private TableColumn<HouseRecei, Boolean>  colStatus;
    @FXML private TableColumn<HouseRecei, Void>     colAction;

    @FXML
    public void initialize() {
        // Kiểm tra quyền hạn
        if (!AuthManager.hasFinanceRole()) {
            logger.warn("User {} không có quyền truy cập Finance",
                    AuthManager.getCurrentUser().getUserId());
        }

        // Status filter
        statusFilter.getItems().addAll("Tất cả", "Chưa thanh toán", "Đã thanh toán");
        statusFilter.getSelectionModel().selectFirst();

        // Cấu hình cột
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("houseReg"));
        colHouseId.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(((models.HouseReg) item).getHouseId());
                }
            }
        });

        colReceiName.setCellValueFactory(new PropertyValueFactory<>("receivable"));
        colReceiName.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(((Receivable) item).getReceiName());
                }
            }
        });

        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("houseReg"));
        colPrice.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    HouseRecei hr = (HouseRecei) getTableRow().getItem();
                    if (hr != null && hr.getReceivable() != null) {
                        long price = hr.getReceivable().getPrice();
                        long total = price * hr.getQuantity();
                        setText(String.format("%,d VND", total));
                    }
                }
            }
        });

        colDeadline.setCellValueFactory(new PropertyValueFactory<>("payDeadline"));
        colDeadline.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Instant item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : DT_FMT.format(item));
            }
        });

        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : (item ? "✓ Đã thanh toán" : "✗ Chưa thanh toán"));
                setStyle(empty ? "" : (item ? "-fx-text-fill: green;" : "-fx-text-fill: red;"));
            }
        });

        setupActionCol();
        reloadAll();
    }

    private void setupActionCol() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnMark = new Button("Đánh dấu thanh toán");

            {
                btnMark.setStyle("-fx-background-color:#4CAF50;-fx-text-fill:white;-fx-font-size:12;");
                btnMark.setOnAction(e -> {
                    HouseRecei hr = getTableView().getItems().get(getIndex());
                    if (!hr.isStatus()) {
                        markAsPaid(hr);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HouseRecei hr = getTableView().getItems().get(getIndex());
                    btnMark.setDisable(hr.isStatus());
                    setGraphic(btnMark);
                }
            }
        });
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchField.getText().trim();
        String status = statusFilter.getValue();

        List<HouseRecei> result;
        if (query.isEmpty()) {
            result = houseReceiServices.findAll();
        } else {
            result = houseReceiServices.findByHouseId(query);
        }

        // Lọc theo status
        if (status != null && !status.equals("Tất cả")) {
            boolean paid = status.equals("Đã thanh toán");
            result = result.stream().filter(hr -> hr.isStatus() == paid).toList();
        }

        houseReceiTable.setItems(FXCollections.observableArrayList(result));
    }

    @FXML
    private void handleReload(ActionEvent event) {
        searchField.clear();
        statusFilter.getSelectionModel().selectFirst();
        reloadAll();
    }

    private void markAsPaid(HouseRecei houseRecei) {
        if (houseReceiServices.updateStatus(
                houseRecei.getHouseReg().getHouseId(),
                houseRecei.getReceivable().getReceiId(),
                true)) {
            houseReceiTable.refresh();
            showInfo("Thành công", "Đã đánh dấu khoản thu là thanh toán");
        } else {
            showError("Lỗi", "Không thể cập nhật trạng thái thanh toán");
        }
    }

    private void reloadAll() {
        masterData.setAll(houseReceiServices.findAll());
        houseReceiTable.setItems(masterData);
    }
}