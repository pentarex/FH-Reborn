package com.pentarex.fhfx.reborn.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import com.google.common.collect.Multimap;
import com.pentarex.fh.api.FHServicesImpl;
import com.pentarex.fh.api.beans.MarksBean;
import com.pentarex.fh.api.beans.NewsBean;
import com.pentarex.fh.api.beans.ScheduleBean;
import com.pentarex.fhfx.reborn.utils.Session;
import com.pentarex.fhfx.reborn.utils.Spinner;
import com.pentarex.fhfx.reborn.utils.Utils;
import com.pentarex.weather.WeatherInfo;
import com.pentarex.weather.beans.WeatherBean;

public class NewsController  implements Initializable{

	@FXML
	private Label nameLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label degreesLabel;
	@FXML
	private ImageView weatherImage;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private AnchorPane contentPane;

	
	private Session session = Session.getSession();
	private ResourceBundle resourceBundle;
	private AnchorPane newspaperPane;
	private NewspaperController newspaperController;
	private List<NewsBean> news;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		resourceBundle = rb;
		initHeader();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		contentPane.getChildren().add(Spinner.getSpinner());
		Runnable loadNews = () -> {initNews();};
		Platform.runLater(loadNews);
		
	}
	
	private void initHeader(){
		WeatherInfo wi = new WeatherInfo();
		WeatherBean weatherBean = wi.getDailyWeather();
		if(weatherBean != null){
			weatherImage.setImage(new Image(weatherBean.getIcon()));
			degreesLabel.setText(weatherBean.getCity() + " " + weatherBean.getTemp() + "\u00B0");
		} else {
			degreesLabel.setText(resourceBundle.getString("news.noWeather"));
		}
		nameLabel.setStyle("-fx-font-family: 'Klaber Fraktur'; -fx-font-size: 64;");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd. MMM yyyy", session.getLocale());
		String formatted = dateFormat.format(new Date());
		dateLabel.setText(formatted);
	}
	
	
	private void initNews(){
		FHServicesImpl fhServices = new FHServicesImpl();
		news = fhServices.getNews();
		/*Multimap<String, MarksBean> map = fhServices.getMarks("hristovh14", "qwe789.321poi");
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while(iter.hasNext()){
			String semester = iter.next();
			Collection<MarksBean> marksList = map.get(semester);
			System.out.println(semester);
			for(MarksBean marks : marksList){
				System.out.println(marks.getTitle() + " " + marks.getGrade());
			}
		}*/
		initNewspaper();
	}
	
	private void initNewspaper(){
		FXMLLoader newspaperLayoutLoader = new FXMLLoader();
		contentPane.getChildren().removeAll(contentPane.getChildren());
		try {
			newspaperLayoutLoader.setResources(ResourceBundle.getBundle("com.pentarex.fhfx.reborn.i18n.FH", resourceBundle.getLocale()));
			newspaperPane = (AnchorPane) newspaperLayoutLoader.load(getClass().getResource("/com/pentarex/fhfx/reborn/resources/templates/Newspaper.fxml").openStream());
			Utils.centerAnchor(newspaperPane);
			contentPane.getChildren().add(newspaperPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		newspaperController = newspaperLayoutLoader.getController();
		session.setContentPane(newspaperPane);
		newspaperController.populateNews(news);
	}
	

}
