package com.pentarex.fhfx.reborn.controllers;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import com.pentarex.fhfx.reborn.utils.Session;
import com.pentarex.fhfx.reborn.utils.Utils;


public class ContentController {

	FXMLLoader fxmlLoader;
	private Session session = Session.getSession();
	public void loadNews(){
		Runnable news = () -> {
			try{
				fxmlLoader = new FXMLLoader();
				fxmlLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", session.getLocale()));
				AnchorPane newsPane = (AnchorPane) fxmlLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/News.fxml").openStream());
				Utils.centerAnchor(newsPane);
				session.getContentPane().getChildren().add(newsPane);
			} catch (Exception e){
				//TODO log4j
				e.printStackTrace();
			}
		};
		Platform.runLater(news);
		
	}
}
