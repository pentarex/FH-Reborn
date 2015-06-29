package com.pentarex.fhfx.reborn.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Utils {
	public static void centerAnchor(Pane pane){
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }
	
	public static List<String> getDepartments(){
		List<String> departmentsList = new ArrayList<>();
		InputStream in = null;
		BufferedReader reader = null;
		try{
			in = Utils.class.getResourceAsStream("/com/pentarex/fhfx/reborn/resources/external/departments.txt");
	        reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	departmentsList.add(line);
	        }
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
				in.close();
				reader.close();
			} catch (Exception e1){
				
			}
			
		}
		return departmentsList;
	}
	
	public static void closeDialog(ActionEvent event){
		// close the dialog.
	    Node  source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	public static String getUserOS(){
		String OS = System.getProperty("os.name");
		return OS;
	}
	
}
