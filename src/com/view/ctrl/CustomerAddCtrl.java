/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CommuterClientImpl;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.State;
import com.manifest.View;
import com.model.child.Commuter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author RISITH-PC
 */
public class CustomerAddCtrl implements Initializable {

    @FXML
    private TextField idText, fNameText, lNameText, streetText, cityText;
  
    @FXML
    private TextField nicNoText, licNoText, teleNoText, whatsappText, viberText, emailText;

    @FXML
    private ComboBox<String> yearCombo, monthCombo, dayCombo;

    @FXML
    private DatePicker issueDateText, expDateText;
    
    @FXML
    private Button saveBtn;
    
    @FXML
    private ChoiceBox<String> districtChoice;

    /**
     * Initializes the controller class.
     */
    
    State.ControllerType controllerType;
    private Commuter selectedCommuter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        districtChoice.getItems().setAll(Data.DISTRICTS);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 90; i <= currentYear; i++) {
            yearCombo.getItems().add(Integer.toString(i));
        }
        monthCombo.getItems().setAll(Data.MONTHS);
        dayCombo.getItems().setAll(Data.DAYS);

    }    

    @FXML
    private void showCustomerBtnEvent(ActionEvent event) {
        clearFields();
        try {
            MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_VIEW));
        } catch (IOException ex) {
            Logger.getLogger(ClientsViewCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveBtnEvent(ActionEvent event){
        switch(controllerType){
            case CUSTOMER_ADD :
                createCustomerAdd();
                break;
            case CUSTOMER_UPDATE :
                createCustomerUpdate();
        } 
        clearFields();
    }

    @FXML
    private void cancelBtnEvent(ActionEvent event) {
        clearFields();
        try {
            switch(controllerType){
                case CUSTOMER_ADD :
                    MainCtrl.getInstance().showContent(String.format(View.PATH, View.DASHBOARD));                    
                    break;
                case CUSTOMER_UPDATE :        
                    MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_VIEW));
                    break;
            }  
        } catch (IOException ex) {
               Logger.getLogger(CustomerAddCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void prepareCustomerAddView() { 
        controllerType = State.ControllerType.CUSTOMER_ADD;
        selectedCommuter = null;
        saveBtn.setText("Save");

        try {
            idText.setText(Integer.toString(CommuterClientImpl.getInstance().getNextId()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        districtChoice.getSelectionModel().select(0);
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        issueDateText.setValue(localDate);
        expDateText.setValue(localDate.plusDays(180));     
    }
    
    public void prepareCustomerUpdateView(Commuter commuter) {
        controllerType = State.ControllerType.CUSTOMER_UPDATE;
        selectedCommuter = commuter;
        saveBtn.setText("Update"); 

        idText.setText(Integer.toString(commuter.getId()));
        fNameText.setText(commuter.getFName());
        lNameText.setText(commuter.getLName());
        streetText.setText(commuter.getStreet());
        cityText.setText(commuter.getCity());
        districtChoice.getSelectionModel().select(commuter.getDistrict());
        yearCombo.getSelectionModel().select(Integer.toString(LocalDate.parse(commuter.getDob()).getYear()));
        monthCombo.getSelectionModel().select(LocalDate.parse(commuter.getDob()).getMonthValue() - 1);
        dayCombo.getSelectionModel().select(LocalDate.parse(commuter.getDob()).getDayOfMonth() - 1);
        nicNoText.setText(commuter.getNicNo());
        licNoText.setText(commuter.getLicNo());
        teleNoText.setText(commuter.getTeleNo());
        whatsappText.setText(commuter.getWhatsappNo());
        viberText.setText(commuter.getViberNo());
        emailText.setText(commuter.getEmail());
        issueDateText.setValue(LocalDate.parse(commuter.getIssueDate(), DateTimeFormatter.ISO_DATE));
        expDateText.setValue(LocalDate.parse(commuter.getExpireDate(), DateTimeFormatter.ISO_DATE));
    }
    
    private void createCustomerAdd() {
        selectedCommuter = new Commuter();
        selectedCommuter.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        selectedCommuter.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        selectedCommuter.setId(Integer.parseInt(idText.getText()));
        selectedCommuter.setFName(fNameText.getText());
        selectedCommuter.setLName(lNameText.getText());
        selectedCommuter.setStreet(streetText.getText());
        selectedCommuter.setCity(cityText.getText());
        selectedCommuter.setDistrict(districtChoice.getSelectionModel().getSelectedItem());
        selectedCommuter.setDob(yearCombo.getSelectionModel().getSelectedItem() + "-" + String.format("%02d", monthCombo.getSelectionModel().getSelectedIndex() + 1) + "-" + dayCombo.getSelectionModel().getSelectedItem());
        selectedCommuter.setNicNo(nicNoText.getText());
        selectedCommuter.setLicNo(licNoText.getText());
        selectedCommuter.setTeleNo(teleNoText.getText());
        selectedCommuter.setWhatsappNo(whatsappText.getText());
        selectedCommuter.setViberNo(viberText.getText());
        selectedCommuter.setEmail(emailText.getText());
        selectedCommuter.setIssueDate(issueDateText.getValue().toString());
        selectedCommuter.setExpireDate(expDateText.getValue().toString());
        selectedCommuter.setPoints(0);
        try {
            if(CommuterClientImpl.getInstance().add(selectedCommuter)){
                MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.ADD, Data.CUSTOMER));
                MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_VIEW));
            }else{
                MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UNSUCESS, Data.CUSTOMER));
            }
        } catch (IOException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
        } catch (SQLException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
        } catch (ClassNotFoundException e) {
            MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    private void createCustomerUpdate() {
        if(selectedCommuter != null){
            selectedCommuter.setFName(fNameText.getText());
            selectedCommuter.setLName(lNameText.getText());
            selectedCommuter.setStreet(streetText.getText());
            selectedCommuter.setCity(cityText.getText());
            selectedCommuter.setDistrict(districtChoice.getSelectionModel().getSelectedItem());
            selectedCommuter.setDob(yearCombo.getSelectionModel().getSelectedItem() + "-" + String.format("%02d", monthCombo.getSelectionModel().getSelectedIndex() + 1) + "-" + dayCombo.getSelectionModel().getSelectedItem());
            selectedCommuter.setNicNo(nicNoText.getText());
            selectedCommuter.setLicNo(licNoText.getText());
            selectedCommuter.setTeleNo(teleNoText.getText());
            selectedCommuter.setWhatsappNo(whatsappText.getText());
            selectedCommuter.setViberNo(viberText.getText());
            selectedCommuter.setEmail(emailText.getText());
            selectedCommuter.setIssueDate(issueDateText.getValue().toString());
            selectedCommuter.setExpireDate(expDateText.getValue().toString());
            try {
                if(CommuterClientImpl.getInstance().update(selectedCommuter)){
                    MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UPDATE, Data.CUSTOMER));
                    MainCtrl.getInstance().showContent(String.format(View.PATH, View.CUSTOMER_VIEW));
                }else{
                    MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UNSUCESS, Data.CUSTOMER));
                }
            } catch (IOException e) {
                MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
            } catch (SQLException e) {
                MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
            } catch (ClassNotFoundException e) {
                MessageBoxViewCtrl.displayError(e.getClass().getSimpleName(),e.getMessage());
            }
        }else{
            MessageBoxViewCtrl.display(Message.TITLE,"Selected Commuter Is Null Object");
        }

    }
    
    private void clearFields(){
        selectedCommuter = null;
        fNameText.clear();
        lNameText.clear();
        streetText.clear();
        cityText.clear();
        districtChoice.getSelectionModel().select(0);
        nicNoText.clear();
        licNoText.clear();
        teleNoText.clear();
        whatsappText.clear();
        viberText.clear();
        emailText.clear();
        issueDateText.setValue(null);
        expDateText.setValue(null);
    }

}
