package com.miaoxingservice.pic;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getImage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getImage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

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
        Statement statement = null;
        ResultSet resultSet = null;
        InputStream inputStream = null;
        req.setCharacterEncoding("UTF-8");
		String imgID = req.getParameter("imgID");
        try {
            statement = connection.createStatement();
            String sql = "select p.source from images p where id = " + imgID;
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
	            inputStream = resultSet.getBinaryStream("source");
	            DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
	            byte[] bytes = new byte[1024];
	            int len = 0;
	            while ((len = inputStream.read(bytes)) != -1) {
	                dos.write(bytes, 0, len);
	            }
	            inputStream.close();
	            dos.flush();
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
                    if (resultSet != null)
                        resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (statement != null)
                        if (statement != null)
                            try {
                                statement.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } finally {
                                if (connection != null)
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
