package com.pentarex.fhfx.reborn.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pentarex.fh.api.FHServicesImpl;
import com.pentarex.fh.api.beans.FullNewsBean;
import com.pentarex.fhfx.reborn.beans.ClassInfoBean;
import com.pentarex.fhfx.reborn.utils.ApplicationInfo;

public class Database {
	private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + ApplicationInfo.getDatabaseDIR());
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
        } catch (Exception e) {
			// if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("Connection: " + e.getMessage());
        }
        return connection;
    }
	
	public static void createDatabase() {
		Connection connection = null;
        Statement stmt = null;
        try {
        	connection = connect();
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30); // set timeout to 30 sec.
            String sql = "CREATE TABLE StudentInfo ('username' char, 'password' char, 'language' char)";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE ClassInfo ('course' char, 'year' number, 'groupName' char)";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE NewsInfo ('title' char, 'link' char, 'description' char, 'article' char, 'imageUrl' char)";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
			// if the error message is "out of memory",
            // it probably means no database file is found
        	//TODO LOG4J
        } finally {
        	try{
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
    }
	
	public static void createTableStudentInfo() {
		Connection connection = null;
        Statement stmt = null;
        try {
        	connection = connect();
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30); // set timeout to 30 sec.
            String sql = "CREATE TABLE StudentInfo ('username' char, 'password' char, 'language' char)";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
			// if the error message is "out of memory",
            // it probably means no database file is found
        	//TODO LOG4J
        } finally {
        	try{
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
    }
	
	public static void dropTableStudentInfo() {
		Connection connection = null;
        Statement stmt = null;
        try {
        	connection = connect();
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30); // set timeout to 30 sec.
            String sql = "DROP TABLE StudentInfo";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
			// if the error message is "out of memory",
            // it probably means no database file is found
        	//TODO LOG4J
        } finally {
        	try{
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
    }
	
	public static String wasStudentLogged(){
		String username = "";
		Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
        	connection = connect();
        	stmt = connection.createStatement();
        	String sql = "SELECT DISTINCT username, password FROM StudentInfo";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	username = rs.getString("username");
            }
        } catch (Exception e){
        	//TODO LOG4J
        } finally {
        	try{
        		if(rs != null) rs.close();
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
        return username;
	}
	
	public static boolean insertIntoStudentInfo(String username, String password, String language){
		boolean inserted = false;
		FHServicesImpl api = new FHServicesImpl();
		Connection connection = null;
		PreparedStatement pstmt = null;
		if(!api.isValidUser(username, password)) return inserted;
		try{
			String sql = "INSERT INTO StudentInfo (username, password, language) VALUES (?, ?, ?)";
			connection = connect();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, language);
			pstmt.execute();
			inserted = true;
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
        		if(pstmt != null) pstmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
		}
		return inserted;
	}
	
	public static void insertClassInfo(String course, String group, Integer year){
		dropAndCreateClassInfo();
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
        	connection = connect();
        	
        	course = (course != null) ? course : "";
        	group = (group != null) ? group : "";
        	year = (year != null) ? year : 0;
        	
            String QUERY = "INSERT INTO ClassInfo (course, groupName, year) VALUES(?,?,?)";
            pstmt = connection.prepareStatement(QUERY);
            pstmt.setString(1, course);
            pstmt.setString(2, group);
            pstmt.setInt(3, year);
            pstmt.execute();
		} catch (SQLException e) {
        	//TODO LOG4J
            e.printStackTrace();
        } finally {
        	try{
        		if(pstmt != null) pstmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
		
	}
	
	private static void dropAndCreateClassInfo(){
		Connection connection = null;
        Statement stmt = null;
        try {
        	connection = connect();
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30); // set timeout to 30 sec.
            String sql = "DROP TABLE ClassInfo";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE ClassInfo ('course' char, 'year' number, 'groupName' char)";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
			// if the error message is "out of memory",
            // it probably means no database file is found
        	//TODO LOG4J
        } finally {
        	try{
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
	}
	
	public static ClassInfoBean getClassInfo(){
		ClassInfoBean bean = new ClassInfoBean();
		Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
        	connection = connect();
        	stmt = connection.createStatement();
        	String sql = "SELECT * FROM ClassInfo";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	bean.setCourse(rs.getString("course"));
            	bean.setGroup(rs.getString("groupName"));
            	bean.setYear(rs.getInt("year"));
            }
        } catch (Exception e){
        	//TODO LOG4J
        } finally {
        	try{
        		if(rs != null) rs.close();
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
        return bean;
	}
	
	public static List<FullNewsBean> getNewsFromDatabase(){
		Connection connection = null;
		Statement stmt = null;
        ResultSet rs = null;
        List<FullNewsBean> newsList = new ArrayList<FullNewsBean>();
        try {
        	connection = connect();
            stmt = connection.createStatement();
            String sql = "SELECT *  FROM NewsInfo";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	FullNewsBean fnb = new FullNewsBean();
            	fnb.setTitle(rs.getString("title"));
            	fnb.setLink(rs.getString("link"));
            	fnb.setDescription(rs.getString("description"));
            	fnb.setArticle(rs.getString("article"));
                fnb.setImageUrl(rs.getString("imageUrl"));
                newsList.add(fnb);
            }
        } catch (SQLException e) {
        	//TODO LOG4J
            e.printStackTrace();
        } finally {
        	try{
        		if(rs != null) rs.close();
        		if(stmt != null) stmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
        }
		return newsList;
	}
	
	public static void insertNewsIntoDatabase(FullNewsBean fnb){
		Connection connection = null;
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO NewsInfo (title, link, description, article, imageUrl) VALUES (?, ?, ?, ?, ?)";
			connection = connect();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, fnb.getTitle());
			pstmt.setString(2, fnb.getLink());
			pstmt.setString(3, fnb.getDescription());
			pstmt.setString(4, fnb.getArticle());
			pstmt.setString(5, fnb.getImageUrl());
			pstmt.execute();
		} catch (Exception e){
			//TODO LOG4J
		} finally {
			try{
        		if(pstmt != null) pstmt.close();
        		if(connection != null) connection.close();
        	} catch (Exception e){
        		//TODO LOG4J
        	}
		}
	}
	
}
