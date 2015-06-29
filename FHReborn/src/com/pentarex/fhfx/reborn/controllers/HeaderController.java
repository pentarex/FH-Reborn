package com.pentarex.fhfx.reborn.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import org.controlsfx.dialog.LoginDialog;

import com.pentarex.fhfx.reborn.Main;
import com.pentarex.fhfx.reborn.persistence.Database;
import com.pentarex.fhfx.reborn.utils.ApplicationInfo;
import com.pentarex.fhfx.reborn.utils.InitApplication;
import com.pentarex.fhfx.reborn.utils.Session;


public class HeaderController implements Initializable{

	@FXML
	private Label usernameLabel;
	@FXML
	private Button signInOutButton;
	@FXML
	private Button settingsButton;
	
	private ResourceBundle resourceBundle;
	private String username = new String();
	private String password = new String();
	private Session session = Session.getSession();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		resourceBundle = rb;
		username = InitApplication.loggedOnce();
		if(!username.equals("")) {
			signInOutButton.setText(resourceBundle.getString("header.signOut"));
			usernameLabel.setText(username);
		}
		else signInOutButton.setText(resourceBundle.getString("header.signIn"));
		initializeEvents();
	}
	
	private void initializeEvents(){
		signInOutButton.setOnMouseEntered(event -> {
			signInOutButton.setStyle("-fx-underline: true;");
			signInOutButton.setCursor(Cursor.HAND);
		});
		signInOutButton.setOnMouseExited(event -> {
			signInOutButton.setStyle("-fx-underline: false;");
		});
		signInOutButton.setOnAction(event -> {
			if(signInOutButton.getText().equals(resourceBundle.getString("header.signIn"))){
				callLoginDialog("Enter your FH Credentials");
			} else {
				callLogout();
			}
		});
		
		
		settingsButton.setOnMouseEntered(event -> {
			settingsButton.setStyle("-fx-underline: true;");
			settingsButton.setCursor(Cursor.HAND);
		});
		settingsButton.setOnMouseExited(event -> {
			settingsButton.setStyle("-fx-underline: false;");
		});
		settingsButton.setOnAction(event -> {
			callSettingsDialog();
		});
	}
	
	private void callLoginDialog(String header){
		LoginDialog loginDialog = new LoginDialog(null, new Callback<Pair<String,String>,Void>() {
			@Override
			public Void call(Pair<String, String> param) {
				username = param.getKey();
				password = param.getValue();
				if(Database.insertIntoStudentInfo(username, password, session.getLocale().getLanguage())){
					signInOutButton.setText(resourceBundle.getString("header.signOut"));
					usernameLabel.setText(username);
				} else callLoginDialog("Bad Username or Password");
				return null;
			}
		});
		
		loginDialog.setHeaderText(header);
		loginDialog.show();
	}
	
	private void callLogout(){
		// implement logout + drop table + create new table
		Database.dropTableStudentInfo();
		Database.createTableStudentInfo();
		signInOutButton.setText(resourceBundle.getString("header.signIn"));
		usernameLabel.setText(resourceBundle.getString("header.username"));
	}
	
	private void callSettingsDialog(){
		
		final Stage dialog = new Stage();
		
		FXMLLoader fxmlLoader = new FXMLLoader();
    	
    	fxmlLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", session.getLocale()));
        Parent layout = null;
		try {
			layout = fxmlLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/SettingsDialog.fxml").openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Scene scene = new Scene(layout);
        dialog.setMinHeight(400);
        dialog.setMinWidth(500);
        
        dialog.setScene(scene);
        dialog.show();
        
	}
	
}