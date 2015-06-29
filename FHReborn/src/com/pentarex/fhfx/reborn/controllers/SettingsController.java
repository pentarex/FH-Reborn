package com.pentarex.fhfx.reborn.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import com.pentarex.fhfx.reborn.Main;
import com.pentarex.fhfx.reborn.beans.ClassInfoBean;
import com.pentarex.fhfx.reborn.persistence.Database;
import com.pentarex.fhfx.reborn.utils.InitApplication;
import com.pentarex.fhfx.reborn.utils.Session;
import com.pentarex.fhfx.reborn.utils.Utils;

public class SettingsController implements Initializable{

	@FXML
	private ComboBox<Integer> yearDropdown;
	@FXML
	private ComboBox<String> courseDropdown;
	@FXML
	private ComboBox<String> groupDropdown;
	@FXML
	private ComboBox<String> languageDropdown;
	
	private ResourceBundle resourceBundle;
	private Session session = Session.getSession();
	
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		resourceBundle = rb;
		String username = InitApplication.loggedOnce();
		if(username.equals("")) {
			yearDropdown.setPromptText(resourceBundle.getString("settings.login"));
			yearDropdown.setDisable(true);
			courseDropdown.setPromptText(resourceBundle.getString("settings.login"));
			courseDropdown.setDisable(true);
			groupDropdown.setPromptText(resourceBundle.getString("settings.login"));
			groupDropdown.setDisable(true);
		} else {
			
			List<String> languagesList = new ArrayList<>();
			languagesList.add(resourceBundle.getString("settings.language.en"));
			languagesList.add(resourceBundle.getString("settings.language.de"));
			languageDropdown.setItems(FXCollections.observableArrayList(languagesList));
			
			List<String> departmentsList = Utils.getDepartments();
			courseDropdown.setItems(FXCollections.observableArrayList(departmentsList));
			
			List<Integer> years = new ArrayList<>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			for(int prevYear = (year - 5); prevYear <= year; prevYear++){
				years.add(prevYear);
			}
			years.add(year + 1);
			yearDropdown.setItems(FXCollections.observableArrayList(years));
			
			List<String> groups = new ArrayList<>();
			groups.add("G1");
			groups.add("G2");
			groups.add("G3");
			groupDropdown.setItems(FXCollections.observableArrayList(groups));
			
			ClassInfoBean classInfoBean = Database.getClassInfo();
			if(classInfoBean.getYear() != 0){
				courseDropdown.setPromptText(classInfoBean.getCourse());
				groupDropdown.setPromptText(classInfoBean.getGroup());
				yearDropdown.setPromptText(String.valueOf(classInfoBean.getYear()));
			}
			
			
		}
		
	}
	
	@FXML
	private void onSaveButtonClicked(ActionEvent event){
		String language = languageDropdown.getSelectionModel().getSelectedItem();
		
		System.out.println(language);
		if(language != null){
			Locale locale = session.getLocale();
			if(language.equals(resourceBundle.getString("settings.language.en"))) locale = new Locale("en");
			else if(language.equals(resourceBundle.getString("settings.language.de"))) locale = new Locale("de");
			session.setLocale(locale);
			
			Main main = new Main();
			Main.stage.close();
			main.reload();
		}
		String course = courseDropdown.getSelectionModel().getSelectedItem();
		String group = groupDropdown.getSelectionModel().getSelectedItem();
		Integer year = yearDropdown.getSelectionModel().getSelectedItem();
		Database.insertClassInfo(course, group, year);
		Utils.closeDialog(event);
	}
	
	@FXML
	private void onCancelButtonClicked(ActionEvent event){
		Utils.closeDialog(event);
	}
	
	

}
