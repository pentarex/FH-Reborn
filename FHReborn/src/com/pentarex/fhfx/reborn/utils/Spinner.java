package com.pentarex.fhfx.reborn.utils;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

public class Spinner {

    public static BorderPane getSpinner(){
    	BorderPane bp = new BorderPane();
		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.setMaxSize(50, 50);
		bp.setCenter(progressIndicator);
		Utils.centerAnchor(bp);
    	return bp;
    }
}
