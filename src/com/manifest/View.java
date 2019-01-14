/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manifest;

/**
 *
 * @author risit
 */
public interface View {

    public static String PATH = "/com/view/fxml/%s.fxml";
    
    public static final String LOGIN = "login";    
    public static final String MAIN = "main";
    public static final String MESSAGEBOX = "messageBox";
    
    public static final String JOURNEY_VIEW = "journeyView";
    public static final String JOURNEY_ADD = "journeyAdd";
    public static final String TRAIN_VIEW = "trainView";
    public static final String TRAIN_ADD = "trainAdd";
    public static final String COMMUTER_VIEW = "commuterView";
    public static final String RESERVATION_VIEW = "reservationView";
    public static final String ABOUT = "about";
    
    public static final String IMAGE_ICON = "/com/view/image/Logo.png";
    public static final String IMAGE_ABOUT = "/com/view/image/About.png";

}
