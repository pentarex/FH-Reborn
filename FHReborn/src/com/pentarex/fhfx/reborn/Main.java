/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentarex.fhfx.reborn;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.pentarex.fhfx.reborn.utils.ApplicationInfo;
import com.pentarex.fhfx.reborn.utils.InitApplication;
import com.pentarex.fhfx.reborn.utils.Session;

/**
 *
 * @author labnb057
 */
public class Main extends Application {
    
	private Session session = Session.getSession();
	private Scene scene;
	public static Stage stage;
	
    @Override
    public void start(Stage st) throws Exception {
    	stage = st;
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	Locale locale = new Locale("de");
    	session.setLocale(locale);
    	System.out.println(session.getLocale().getLanguage());
    	
    	fxmlLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", locale));
        Parent layout = fxmlLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Layout.fxml").openStream());
        
        scene = new Scene(layout);
        scene.getStylesheets().add(ApplicationInfo.CSS);
        stage.setMinHeight(800);
        stage.setMinWidth(900);
        
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void init(){
    	InitApplication.firstRun(); // Initialize the application
    }
    
    public void reload() {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader();
        	fxmlLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", session.getLocale()));
            Parent layout = fxmlLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Layout.fxml").openStream());
            scene = new Scene(layout);
            stage.setTitle("GUI");
//            stage.getIcons().add(icone);
            stage.setScene(scene);
            stage.show();
    	} catch (IOException ioe){
    		ioe.printStackTrace();
    	}
    	
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
