package com.pentarex.fhfx.reborn.utils;

public class ApplicationInfo {
	public static final String APPLICATION_NAME = "FH Reborn";
    public static final String APPLICATION_VERSION = "0.01";
    public static final String APPLICATION_STATUS = "beta";
    public static final String CSS = "/com/pentarex/fhfx/reborn/resources/css/styles.css";
	public static String APPLICATION_DIR;
	public static String DATABASE_DIR;
	
	public static String getApplicationDir(){
		String OS = Utils.getUserOS();
		if(OS.contains("windows")){
			APPLICATION_DIR = System.getProperty("user.home") + "\\FHReborn";
		} else {
			APPLICATION_DIR = System.getProperty("user.home") + "/FHReborn";
		}
		return APPLICATION_DIR;
	}
	
	public static String getDatabaseDIR(){
		String OS = Utils.getUserOS();
		if(OS.contains("windows")){
			DATABASE_DIR = APPLICATION_DIR + "\\database.db";
		} else {
			DATABASE_DIR = APPLICATION_DIR + "/database.db";
		}
		
		return DATABASE_DIR;
	}
}
