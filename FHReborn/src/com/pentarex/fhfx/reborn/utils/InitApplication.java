package com.pentarex.fhfx.reborn.utils;

import java.io.File;

import javafx.scene.text.Font;

import com.pentarex.fhfx.reborn.persistence.Database;

public class InitApplication {

	public static void firstRun(){
		//Utils.getUserOS();
		Font.loadFont(InitApplication.class.getResource("/com/pentarex/fhfx/reborn/resources/fonts/KlaberFraktur.ttf").toExternalForm(), 24);
		checkFolder();
		checkDatabase();
	}
	
	private static void checkFolder(){
		File applicationDir = new File(ApplicationInfo.APPLICATION_DIR);
    	if(!applicationDir.exists()) {
    		applicationDir.mkdir();
    	}
	}
	
	private static void checkDatabase(){
		File databaseFile = new File(ApplicationInfo.DATABASE_DIR);
		if(!databaseFile.exists()){
			Database.createDatabase();
		}
	}
	
	public static String loggedOnce(){
		return Database.wasStudentLogged();
	}
	
	
}
