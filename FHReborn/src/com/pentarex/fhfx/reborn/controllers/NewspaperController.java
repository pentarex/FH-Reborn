package com.pentarex.fhfx.reborn.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.controlsfx.tools.Borders;

import com.pentarex.fh.api.NewsImplementation;
import com.pentarex.fh.api.beans.FullNewsBean;
import com.pentarex.fh.api.beans.NewsBean;
import com.pentarex.fhfx.reborn.utils.Session;
import com.pentarex.fhfx.reborn.utils.Utils;

public class NewspaperController implements Initializable {

	@FXML
	private VBox articlesPane;
	
	@FXML
	private ScrollPane scrollPane;

	private Session session = Session.getSession();
	private ResourceBundle resourceBundle;

	public void populateNews(List<NewsBean> news) {
		for (NewsBean articles : news) {
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			Label title = new Label(articles.getTitle());
			Hyperlink link = new Hyperlink(resourceBundle.getString("read.more"));
			link.setOnAction(event ->{ 
				openArticle(articles);
			});
			vbox.getChildren().addAll(title, link);
			Node wrappedBox = Borders.wrap(vbox).lineBorder().thickness(1).radius(5, 5, 5, 5).build().build();
			Utils.centerAnchor(vbox);
			articlesPane.getChildren().add(wrappedBox);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		resourceBundle = rb;
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
	}
	
	private void openArticle(NewsBean article){
		NewsImplementation newsFactory = new NewsImplementation();
		Task<FullNewsBean> task = newsFactory.getArticle(article);
		task.setOnSucceeded(event -> {
			try {
				FullNewsBean fnb = task.get();
				System.out.println(fnb.getDescription() + " " + fnb.getArticle());
				createArticleDialog(fnb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		new Thread(task).start();
	}
	
	private void createArticleDialog(FullNewsBean fullArticle){
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        ScrollPane scrollArticlePane = new ScrollPane();
        scrollArticlePane.setFitToHeight(true);
        scrollArticlePane.setFitToWidth(true);
        scrollArticlePane.setHbarPolicy(ScrollBarPolicy.NEVER);
        AnchorPane anchorArticlePane = new AnchorPane();
        
        VBox dialogVbox = new VBox(20);
        Utils.centerAnchor(dialogVbox);
        dialogVbox.setAlignment(Pos.CENTER);
        
        Label title = new Label(fullArticle.getTitle());
        Label article = new Label(fullArticle.getArticle());
        article.setWrapText(true);
        article.setTextAlignment(TextAlignment.JUSTIFY);
        
        dialogVbox.getChildren().addAll(title, article);
        anchorArticlePane.getChildren().add(dialogVbox);
        scrollArticlePane.setContent(anchorArticlePane);
        
        Scene dialogScene = new Scene(scrollArticlePane, 600, 500);
        dialog.setTitle(fullArticle.getTitle());
        dialog.setScene(dialogScene);
        dialog.show();
	}

}
