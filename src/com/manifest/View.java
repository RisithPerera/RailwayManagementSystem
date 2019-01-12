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
    
    public static final String JOURNEY = "dashboard";
    public static final String TRAIN = "customerAdd";
    public static final String CLIENTS = "clientsView";
    public static final String RESERVATION = "customerOrderAdd";
    public static final String ABOUT = "about";
    
    public static final String IMAGE_ICON = "/com/view/image/Logo.png";
    public static final String IMAGE_ABOUT = "/com/view/image/About.png";
}
