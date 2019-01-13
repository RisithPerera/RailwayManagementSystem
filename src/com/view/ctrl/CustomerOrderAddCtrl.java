/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CommuterClientImpl;
import com.base.client.impl2.ReservationClientImpl;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.State;
import com.manifest.View;
import com.model.child.Commuter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author RISITH-PC
 */
public class CustomerOrderAddCtrl implements Initializable {

    @FXML
    private TextField idText, amountText, customerText;

    @FXML
    private ChoiceBox<String> discountChoice;

    @FXML
    private TextField searchCustomerText;

    @FXML
    private Button addBtn;

    @FXML
    private Button printBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<CustomerOrderData> orderDataTable;

    @FXML
    private TableColumn<CustomerOrderData, String> amountCol, rateCol, discountCol, remainderCol;

    @FXML
    private Label totalAmountLabel, finalPriceLabel, discountLabel, pointsLabel;

    @FXML
    private ListView<CustomerTemp> customerListView, itemListView;

    private ObservableList<CustomerOrderData> customerOrderDataList;
    private ObservableList<CustomerTemp> customerTempList;

    private Commuter selectedCommuter;
    private CustomerOrder selectedCustomerOrder;
    private State.ControllerType controllerType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        remainderCol.setCellValueFactory(new PropertyValueFactory<>("remainder"));

        discountChoice.getItems().setAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        searchCustomer();
        setTableMenu();
    }

    @FXML
    private void showPurchasesBtnEvent(ActionEvent event) {
        try {
            MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_ORDER_VIEW));
        } catch (IOException ex) {
            Logger.getLogger(ClientsViewCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addBtnEvent(ActionEvent event) {
        CustomerOrderData customerOrderData = new CustomerOrderData();
        customerOrderData.setCustomerOrder(selectedCustomerOrder);
        customerOrderData.setAmount(Double.parseDouble(amountText.getText()));
        customerOrderData.setRate(Integer.parseInt(discountChoice.getSelectionModel().getSelectedItem()));

        customerOrderDataList.add(customerOrderData);
        updateOrderDataView();
        discountChoice.getSelectionModel().select(0);
        amountText.clear();
    }

    @FXML
    private void printBtnEvent(ActionEvent event) {
        try {
            createCustomerOrderAdd();
            clearFields();
            MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_ORDER_VIEW));
        } catch (IOException ex) {
            Logger.getLogger(CustomerOrderAddCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void customerListEvent(MouseEvent event) {
        if (!customerListView.getSelectionModel().isEmpty()) {
            selectedCommuter = customerListView.getSelectionModel().getSelectedItem().getCommuter();
            customerText.setText(selectedCommuter.getIdFullName());
        }
    }

    @FXML
    private void cancelBtnEvent(ActionEvent event) {
        clearFields();
        try {
            switch (controllerType) {
                case CUSTOMER_ORDER_ADD:
                    MainCtrl.getInstance().showContent(String.format(View.PATH, View.DASHBOARD));
                    break;
                case CUSTOMER_ORDER_UPDATE:
                    MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_ORDER_VIEW));
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(CustomerAddCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchCustomer() {
        customerTempList = FXCollections.observableArrayList();

        for (Commuter commuter : CommuterClientImpl.getInstance().getAll()) {
            customerTempList.add(new CustomerTemp(commuter));
        }

        customerListView.setItems(customerTempList);
        searchCustomerText.textProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                        if (oldVal != null && (newVal.toString().length() < oldVal.toString().length())) {
                            customerListView.setItems(customerTempList);
                        }

                        String[] parts = newVal.toString().toUpperCase().split(" ");

                        ObservableList<CustomerTemp> subentries = FXCollections.observableArrayList();
                        for (CustomerTemp entry : customerListView.getItems()) {
                            boolean match = true;
                            for (String part : parts) {
                                if (!entry.toString().toUpperCase().contains(part)) {
                                    match = false;
                                    break;
                                }
                            }

                            if (match) {
                                subentries.add(entry);
                            }
                        }
                        customerListView.setItems(subentries);
                    }
                }
        );
    }

    public void prepareCustomerOrderAddView(Commuter commuter) {
        try {
            controllerType = State.ControllerType.CUSTOMER_ORDER_ADD;
            printBtn.setText("Print");
            customerOrderDataList = FXCollections.observableArrayList();
            selectedCustomerOrder = new CustomerOrder();
            selectedCommuter = commuter;
            if (selectedCommuter == null) {
                customerText.clear();
            } else {
                customerText.setText(selectedCommuter.getIdFullName());
            }
            idText.setText(Integer.toString(ReservationClientImpl.getInstance().getNextId()));
            customerListView.getSelectionModel().select(null);
            updateOrderDataView();
        } catch (SQLException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
        } catch (ClassNotFoundException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    /*
    public void prepareCustomerOrderUpdateView(CustomerOrder customerOrder) {  
        try {
            controllerType = State.ControllerType.CUSTOMER_ORDER_UPDATE;
            printBtn.setText("Update");
            customerOrderDataList = CustomerOrderDataClientImpl.getInstance().getOrderData(customerOrder);
            selectedCustomerOrder = customerOrder;
            selectedCommuter = customerOrder.getCommuter();
            idText.setText(Integer.toString(customerOrder.getId()));
            customerText.setText(customerOrder.getCommuter().getIdFullName());
            updateOrderDataView();
        } catch (IOException ex) {
            Logger.getLogger(CustomerOrderAddCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */

    private void createCustomerOrderAdd() {
        try {
            selectedCustomerOrder.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            selectedCustomerOrder.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
            selectedCustomerOrder.setId(ReservationClientImpl.getInstance().getNextId());
            selectedCustomerOrder.setCommuter(selectedCommuter);
            if (ReservationClientImpl.getInstance().add(selectedCustomerOrder, customerOrderDataList)) {
                MessageBoxViewCtrl.display(Message.TITLE, String.format(Message.ADD, Data.CUSTOMER_ORDER));
            } else {
                MessageBoxViewCtrl.display(Message.TITLE, String.format(Message.UNSUCESS, Data.CUSTOMER_ORDER));
            }
        } catch (SQLException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(), e.getMessage());
        } catch (ClassNotFoundException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(), e.getMessage());
        }
    }

    /*
    private void createCustomerOrderUpdate() {
         try {
            selectedCustomerOrder.setCommuter(selectedCommuter);
            System.out.println("aaaaaa");
            ReservationClientImpl.getInstance().update(selectedCustomerOrder);
            System.out.println("bbbb");
            CustomerOrderDataClientImpl.getInstance().updateOrderData(selectedCustomerOrder, customerOrderDataList);
            System.out.println("ccccccc");
            MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UPDATE, Data.CUSTOMER_ORDER));
        } catch (IOException ex) {
            Logger.getLogger(CustomerOrderAddCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */

    private void clearFields() {
        customerOrderDataList.clear();
        selectedCustomerOrder = null;
        selectedCommuter = null;
        idText.clear();
        customerText.clear();
        amountText.clear();
        discountChoice.getSelectionModel().select(0);
        totalAmountLabel.setText(Integer.toString(0));
        finalPriceLabel.setText(Integer.toString(0));
        discountLabel.setText(Integer.toString(0));
        pointsLabel.setText(Integer.toString(0));
    }

    private void updateOrderDataView() {
        double amount = 0, discount = 0, points = 0;
        if (customerOrderDataList != null) {
            for (CustomerOrderData customerOrderData : customerOrderDataList) {
                amount += customerOrderData.getAmount();
                discount += customerOrderData.getDiscount();
                points += customerOrderData.getPoints();
            }
            orderDataTable.getItems().setAll(customerOrderDataList);
        }
        totalAmountLabel.setText(": " + Double.toString(amount));
        finalPriceLabel.setText(": " + Double.toString(amount - discount));
        discountLabel.setText(": " + Double.toString(discount));
        pointsLabel.setText(": " + Double.toString(points));
    }

    private void setTableMenu() {
        orderDataTable.setRowFactory((TableView<CustomerOrderData> tableView) -> {
            TableRow<CustomerOrderData> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteMenu = new MenuItem("Delete");

            deleteMenu.setOnAction((ActionEvent event) -> {
                customerOrderDataList.remove(orderDataTable.getSelectionModel().getSelectedItem());
                updateOrderDataView();
            });

            contextMenu.getItems().addAll(deleteMenu);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    private class CustomerTemp {
        private Commuter commuter;

        public CustomerTemp(Commuter commuter) {
            this.commuter = commuter;
        }

        public Commuter getCommuter() {
            return commuter;
        }

        public void setCommuter(Commuter commuter) {
            this.commuter = commuter;
        }

        @Override
        public String toString() {
            return commuter.getId() + ": " + commuter.getFullName() + ": " + commuter.getDistrict();
        }
    }
}