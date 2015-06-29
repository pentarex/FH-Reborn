package com.pentarex.fhfx.reborn.utils;

import java.util.Locale;

import javafx.scene.layout.AnchorPane;


public class Session {
	private AnchorPane contentPane;
	private Locale locale;
	
	private static Session session = null;
	
	public static Session getSession(){
        if(session == null){
            session = new Session();
        }
        return session;
    }

	public AnchorPane getContentPane() {
		return contentPane;
	}

	public void setContentPane(AnchorPane contentPane) {
		this.contentPane = contentPane;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	
	
}
