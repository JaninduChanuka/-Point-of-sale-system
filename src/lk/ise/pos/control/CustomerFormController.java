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
import lk.ise.pos.bo.custom.CustomerBo;
import lk.ise.pos.dto.CustomerDto;
import lk.ise.pos.entity.Customer;
import lk.ise.pos.enums.BoType;
import lk.ise.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerFormContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tbl;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public Button btn;
    public TextField txtSearch;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
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

    private void setData(CustomerTM newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        btn.setText("Update Customer");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
    }

    public void saveCustomer(ActionEvent actionEvent) {
        CustomerDto c1 = new CustomerDto(
                txtId.getText(), txtName.getText(), txtAddress.getText()
                , Double.parseDouble(txtSalary.getText())
        );
        if (btn.getText().equals("Save Customer")) {
            try {
                if (customerBo.saveCustomer(c1)) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                    loadAll("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went Wrong!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                if (customerBo.updateCustomer(c1)) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                    loadAll("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went Wrong!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        clearData();
    }

    private void clearData() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    private void loadAll(String searchText) {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        try {
            for (CustomerDto c : customerBo.findAllCustomers()) {
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(
                        c.getId(), c.getName(), c.getAddress(), c.getSalary(), btn
                );
                btn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> type = alert.showAndWait();
                    if (type.get() == ButtonType.YES) {
                        try {
                            if (customerBo.deleteCustomer(c.getId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
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

            FilteredList<CustomerTM> filteredData = new FilteredList<>(tmList, b -> true);

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (customer.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (customer.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (customer.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(customer.getSalary()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<CustomerTM> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tbl.comparatorProperty());
            tbl.setItems(sortedData);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void newCustomer(ActionEvent actionEvent) {
        clearData();
        btn.setText("Save Customer");
    }
}
