package com.miaoxingservice.pic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
public class ImageInsert {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String user = "root";
        String password = "miaoxingren.233";
        String url = "jdbc:mysql://localhost:3306/petty?characterEncoding=utf-8";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        InputStream inputStream = null;
        try {
        	/*
	        for (int i = 0; i < 20; i++) {
	        	String path = "D:\\saveimg\\img" + Integer.toString(i) + ".jpg";
		        inputStream = ImageUtil.getImageByte(path);
	            String sql = "insert into images(id,name,source) values(?,?,?)";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setInt(1, i+1);
	            preparedStatement.setString(2, "img" + Integer.toString(i));
	            preparedStatement.setBinaryStream(3, inputStream,
	                    inputStream.available());
	            preparedStatement.execute();
		    }
		    */
        	
        	/*
        	for (int i = 1; i <= 19; i += 2) {
	        	//String path = "http://172.18.143.246:8080/miaoxingservice/getImage?imgID=";
        		String path = "http://192.168.23.1:8080/miaoxingservice/getImage?imgID=";
        		String sql = "insert into matchings(name,loc,motto,likes,photo,matching_photo) values(?,?,?,?,?,?)";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, "Maru" + Integer.toString(i));
	            preparedStatement.setString(2, "Japan" + Integer.toString(i));
	            preparedStatement.setString(3, "This is a cat." + Integer.toString(i));
	            preparedStatement.setInt(4, i);
	            preparedStatement.setString(5, path + Integer.toString(i));
	            preparedStatement.setString(6, path + Integer.toString(i+1));
	            preparedStatement.execute();
		    }
		    */
        	
        	for (int i = 1; i <= 20; i++) {
	        	//String path = "http://172.18.143.246:8080/miaoxingservice/getImage?imgID=";
        		String path = "http://192.168.23.1:8080/miaoxingservice/getImage?imgID=";
        		String sql = "insert into sharings(blogger,detail,likes,photo) values(?,?,?,?)";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, "Neiko" + Integer.toString(i));
	            preparedStatement.setString(2, "This is a cat." + Integer.toString(i));
	            preparedStatement.setInt(3, i+(int) new Random().nextInt(50));
	            preparedStatement.setString(4, path + Integer.toString(i));
	            //preparedStatement.execute();
		    }
        	
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
