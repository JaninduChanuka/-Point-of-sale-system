package lk.ise.pos.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.pos.bo.BoFactory;
import lk.ise.pos.bo.custom.ItemBo;
import lk.ise.pos.dto.ItemDto;
import lk.ise.pos.entity.Item;
import lk.ise.pos.enums.BoType;
import lk.ise.pos.view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ItemFormController {
    public AnchorPane ItemFormContext;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView<ItemTM> tbl;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOption;
    public Button btn;
    public TextField txtSearch;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAll("");

        tbl.getSelectionModel()
                .selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setData(newValue);
                    }
                }));
    }

    private void setData(ItemTM newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        btn.setText("Update Item");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ItemFormContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml")))
        );
        stage.centerOnScreen();
    }

    public void saveItem(ActionEvent actionEvent) {
        ItemDto item = new ItemDto(
                txtCode.getText(), txtDescription.getText()
                , Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText())
        );

        if (btn.getText().equals("Save Item")) {
            try {
                if (itemBo.saveItem(item)) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                    loadAll("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went Wrong!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (itemBo.updateItem(item)) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
                    loadAll("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went Wrong!").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        clearData();
    }

    private void clearData() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    private void loadAll(String searchText) {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
        try {
            for (ItemDto item : itemBo.findAllItems()) {
                Button btn = new Button("Delete");

                ItemTM tm = new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice(), btn);

                btn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> type = alert.showAndWait();
                    if (type.get() == ButtonType.YES) {
                        try {
                            if (itemBo.deleteItem(item.getCode())) {
                                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                                loadAll("");
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Something went Wrong!").show();
                            }

                        } catch (ClassNotFoundException | SQLException ex) {
                            ex.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                tmList.add(tm);
            }
            tbl.setItems(tmList);

            FilteredList<ItemTM> filteredData = new FilteredList<>(tmList, b -> true);

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (item.getCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (item.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(item.getUnitPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(item.getQtyOnHand()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<ItemTM> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tbl.comparatorProperty());
            tbl.setItems(sortedData);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void newItem(ActionEvent actionEvent) {
        clearData();
        btn.setText("Save Item");
    }
}
