/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentarex.fhfx.reborn.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import com.pentarex.fhfx.reborn.utils.Session;
import com.pentarex.fhfx.reborn.utils.Utils;

/**
 * FXML Controller class
 *
 * @author labnb057
 */
public class LayoutController implements Initializable {
    @FXML
    private GridPane layoutTop;
    @FXML
    private AnchorPane layoutContent;
    
    private Session session = Session.getSession();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            Runnable topHeader = () -> {
            	try{
            		String menuCSS = getClass().getResource("/com/pentarex/fhfx/reborn/resources/css/menu.css").toExternalForm();
            		FXMLLoader fxmlTopLoader = new FXMLLoader();
                	fxmlTopLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", rb.getLocale()));
                    AnchorPane headerPane = (AnchorPane) fxmlTopLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Header.fxml").openStream());
                    AnchorPane menuPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Menu.fxml"));
                    GridPane.setRowIndex(headerPane, 0);
                    GridPane.setRowIndex(menuPane, 1);
                    menuPane.getStylesheets().add(menuCSS);
                    layoutTop.getChildren().addAll(headerPane, menuPane);
            	} catch(Exception e){
            		//TODO log4j
            		e.printStackTrace();
            	}
            };
            Platform.runLater(topHeader);
            
            Runnable content = () -> {
            	try{
            		FXMLLoader fxmlContentLoader = new FXMLLoader(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Content.fxml"));
                    fxmlContentLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", rb.getLocale()));
                    AnchorPane contentPane = (AnchorPane) fxmlContentLoader.load();
                    session.setContentPane(contentPane);
                    ContentController contentController = new ContentController();
                    fxmlContentLoader.setController(contentController);
                    Utils.centerAnchor(contentPane);
                    layoutContent.getChildren().add(contentPane);
                    contentController.loadNews();
            	} catch (Exception e){
                	//TODO LOG4J
                    e.printStackTrace();
                }
            };
            Platform.runLater(content);
    }    
    
}
