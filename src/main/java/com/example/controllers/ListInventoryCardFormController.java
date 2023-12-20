package com.example.controllers;

import com.example.models.Estate;
import com.example.models.InventoryCard;
import com.example.models.User;
import com.example.services.EstateService;
import com.example.services.InventoryCardService;
import com.example.services.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@FxmlView("/com/example/fxml/list-inventory-card-form.fxml")
public class ListInventoryCardFormController {

    private final FxWeaver fxWeaver;

    private final InventoryCardService inventoryCardService;

    private final UserService userService;

    private final EstateService estateService;


    @FXML
    public TextField searchField;

    @FXML
    public TableColumn estateColumn;

    @FXML
    public Button reportButton;

    @FXML
    public TextField searchLocationField;

    @FXML
    public TextField searchInventoryOfficerField;

    @FXML
    private TableView<InventoryCard> tableView;

    @FXML
    public Button backButton;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn inventoryDateColumn;

    @FXML
    public TableColumn inventoryOfficerColumn;

    @FXML
    public TableColumn notesColumn;

    @FXML
    public TableColumn locationColumn;

    @FXML
    public TableColumn statusColumn;

    @Autowired
    public ListInventoryCardFormController(FxWeaver fxWeaver, InventoryCardService inventoryCardService, UserService userService, EstateService estateService) {
        this.fxWeaver = fxWeaver;
        this.inventoryCardService = inventoryCardService;
        this.userService = userService;
        this.estateService = estateService;
    }

    //проверка
    @FXML
    public void initialize() {

        // Связывание столбцов таблицы с полями объектов
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        inventoryDateColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryDate"));
        inventoryOfficerColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryOfficer"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        estateColumn.setCellValueFactory(new PropertyValueFactory<>("estate"));

        // Загрузка пользователей при инициализации
        updateTableView();


        // Добавление слушателя изменений текста в поле поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchInventoryCord(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchInventoryCord("");

        //--

        // Добавление слушателя изменений текста в поле поиска
        searchLocationField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchInventoryCordLocation(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchInventoryCordLocation("");
    }

    private void updateTableView() {
        List<InventoryCard> inventoryCard = inventoryCardService.getAllInventoryCard();
        tableView.getItems().setAll(inventoryCard);
    }

    @FXML
    public void back() {
        openAssetsFor();
    }

    private void openAssetsFor(){
        Parent view = fxWeaver.loadView(AssetsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void searchInventoryCord(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<InventoryCard> foundInventoryCord = inventoryCardService.getEstateByStatusContains(searchTerm);

            if (!foundInventoryCord.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundInventoryCord);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    private void searchInventoryCordLocation(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<InventoryCard> foundInventoryCord = inventoryCardService.getEstateByLocationContains(searchTerm);

            if (!foundInventoryCord.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundInventoryCord);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    @FXML
    public void report() {
        XWPFDocument document = new XWPFDocument();

        // Создание новой таблицы в документе Word
        XWPFTable table = document.createTable();

        // Удаление первой пустой строки, созданной по умолчанию
        table.removeRow(0);

        // Добавление заголовков столбцов
        XWPFTableRow headerRow = table.createRow();
        for (TableColumn column : tableView.getColumns()) {
            XWPFTableCell cell = headerRow.addNewTableCell();
            cell.setText(column.getText());
        }

        // Получение данных из таблицы
        ObservableList<InventoryCard> inventoryCards = tableView.getItems();

        // Заполнение таблицы данными
        for (InventoryCard inventoryCard : inventoryCards) {
            XWPFTableRow row = table.createRow();
            int numColumns = tableView.getColumns().size(); // Получаем количество столбцов
            for (int i = 0; i < numColumns; i++) {
                if (i >= row.getTableCells().size()) { // Создаем ячейку только если ее еще нет
                    row.addNewTableCell();
                }
                // Заполнение ячейки данными в зависимости от индекса i
                switch (i) {
                    case 0:
                        row.getCell(i).setText(String.valueOf(inventoryCard.getId()));
                        break;
                    case 1:
                        row.getCell(i).setText(String.valueOf(inventoryCard.getInventoryDate()));
                        break;
                    case 2:
                        row.getCell(i).setText(inventoryCard.getInventoryOfficer());
                        break;
                    case 3:
                        row.getCell(i).setText(inventoryCard.getNotes());
                        break;
                    case 4:
                        row.getCell(i).setText(inventoryCard.getLocation());
                        break;
                    case 5:
                        row.getCell(i).setText(inventoryCard.getStatus());
                        break;
                    case 6:
                        row.getCell(i).setText(String.valueOf(inventoryCard.getEstate()));
                        break;
                        // Добавьте остальные поля по аналогии
                }
            }
        }
        // Сохранение документа Word
        try (FileOutputStream out = new FileOutputStream("Отчет по списку карточек учета имущества.docx")) {
            document.write(out);

            // Показываем всплывающее окно с сообщением об успешном создании отчета
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Отчет");
            alert.setHeaderText(null);
            alert.setContentText("Отчет был успешно сделан!");

            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
