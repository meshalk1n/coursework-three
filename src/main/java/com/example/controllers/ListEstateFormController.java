package com.example.controllers;

import com.example.models.Estate;
import com.example.services.EstateService;
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
@FxmlView("/com/example/fxml/list-estate-form.fxml")
public class ListEstateFormController {

    private final EstateService estateService;

    private final FxWeaver fxWeaver;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn nameColumn;

    @FXML
    public TableColumn categoryColumn;

    @FXML
    public TableColumn costColumn;

    @FXML
    public TableColumn conditionColumn;

    @FXML
    public TableColumn acquisitionDateColumn;

    @FXML
    public TableColumn addedByUserColumn;

    @FXML
    public Button backButton;

    @FXML
    public TableColumn lastModifiedByColumn;

    @FXML
    public TextField searchField;

    @FXML
    public Button reportButton;

    @FXML
    public TextField searchCategoryField;

    @FXML
    public TextField searchCostField;

    @FXML
    public TextField searchConditionField;

    @FXML
    private TableView<Estate> tableView;

    @Autowired
    public ListEstateFormController(FxWeaver fxWeaver, EstateService estateService) {
        this.estateService = estateService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        // Связывание столбцов таблицы с полями объектов Estate
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        acquisitionDateColumn.setCellValueFactory(new PropertyValueFactory<>("acquisitionDate"));
        addedByUserColumn.setCellValueFactory(new PropertyValueFactory<>("addedByUser"));
        lastModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedBy"));


        // Загрузка пользователей при инициализации
        updateTableView();

        // Добавление слушателя изменений текста в поле поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEstate(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchEstate("");

        //--

        // Добавление слушателя изменений текста в поле поиска
        searchCategoryField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEstateCategory(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchEstateCategory("");

        //--

        // Добавление слушателя изменений текста в поле поиска
        searchCostField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                searchEstateCost(Integer.valueOf(newValue));
            } else {
                // Если поле поиска пустое, отобразите все пользователи
                updateTableView();
            }
        });


        //--

        // Добавление слушателя изменений текста в поле поиска
        searchConditionField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEstateCondition(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchEstateCondition("");
    }

    private void updateTableView() {
        List<Estate> estate = estateService.getAllEstate();
        tableView.getItems().setAll(estate);
    }

    @FXML
    public void back(){
        openReportForm();
    }

    private void openReportForm(){
        Parent view = fxWeaver.loadView(ReportsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void searchEstate(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<Estate> foundEstate = estateService.getEstateByNameContains(searchTerm);

            if (!foundEstate.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundEstate);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    private void searchEstateCategory(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<Estate> foundEstate = estateService.getEstateByCategoryContains(searchTerm);

            if (!foundEstate.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundEstate);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    private void searchEstateCost(Integer searchTerm) {
        if (searchTerm != null && searchTerm != 0) {
            List<Estate> foundEstate = estateService.getEstateByCost(searchTerm);

            if (!foundEstate.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundEstate);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    private void searchEstateCondition(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<Estate> foundEstate = estateService.getEstateByConditionContains(searchTerm);

            if (!foundEstate.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundEstate);
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
        ObservableList<Estate> estates = tableView.getItems();

        // Заполнение таблицы данными
        for (Estate estate : estates) {
            XWPFTableRow row = table.createRow();
            int numColumns = tableView.getColumns().size(); // Получаем количество столбцов
            for (int i = 0; i < numColumns; i++) {
                if (i >= row.getTableCells().size()) { // Создаем ячейку только если ее еще нет
                    row.addNewTableCell();
                }
                // Заполнение ячейки данными в зависимости от индекса i
                switch (i) {
                    case 0:
                        row.getCell(i).setText(String.valueOf(estate.getId()));
                        break;
                    case 1:
                        row.getCell(i).setText(estate.getName());
                        break;
                    case 2:
                        row.getCell(i).setText(estate.getCategory());
                        break;
                    case 3:
                        row.getCell(i).setText(String.valueOf(estate.getCost()));
                        break;
                    case 4:
                        row.getCell(i).setText(estate.getCondition());
                        break;
                    case 5:
                        row.getCell(i).setText(String.valueOf(estate.getAcquisitionDate()));
                        break;
                    case 6:
                        row.getCell(i).setText(estate.getAddedByUser());
                        break;
                    case 7:
                        row.getCell(i).setText(estate.getLastModifiedBy());
                    // Добавьте остальные поля по аналогии
                }
            }
        }
        // Сохранение документа Word
        try (FileOutputStream out = new FileOutputStream("Отчет по списку имущества.docx")) {
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
