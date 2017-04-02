package com.miaoingservice.sharing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SharingDataService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATABASE_TO_CONNECT = "petty";
	private static final String MYSQL_USER = "root";
	private static final String MYSQL_PASSWORD = "miaoxingren.233";


	/**
	 * Constructor of the object.
	 */
	public SharingDataService() {
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
        String sql = null;
        try {
          	/*****1. 替换为你自己的数据库名（可从管理中心查看到）*****/
        	String driverName = "com.mysql.jdbc.Driver";
			/******2. 从环境变量里取出数据库连接需要的参数******/
          	String dbUrl = "jdbc:mysql://localhost:3306/"
            		+ DATABASE_TO_CONNECT + "?"
                    + "user=" + MYSQL_USER
                    + "&password=" + MYSQL_PASSWORD
                    + "&useUnicode=true&characterEncoding=UTF8";
          
			/******3. 接着连接并选择数据库名为databaseName的服务器******/
          	Class.forName(driverName);
			connection = DriverManager.getConnection(dbUrl);
          	stmt = connection.createStatement();
          	/*至此连接已完全建立，就可对当前数据库进行相应的操作了*/
			/**
			 * 4. 接下来就可以使用其它标准mysql函数操作进行数据库操作
			 */
			//创建一个数据库表
			/*
          	sql = "create table if not exists users(" + 
						"id int primary key auto_increment," + 
						"username text not null, " +
						"password text not null)";
			stmt.execute(sql);
			*/
          	req.setCharacterEncoding("UTF-8");
    		String limit = req.getParameter("limit");
    		System.out.println(limit);
			
			//检索数据
			sql = "select blogger,detail,likes,photo from sharings limit " + limit;
          	rs = stmt.executeQuery(sql);
          	JSONArray array = new JSONArray();
          	while (rs != null && rs.next()) {
          		JSONObject object = new JSONObject();
          		object.put("item_share_user", rs.getString("blogger"));
          		object.put("item_share_detail", rs.getString("detail"));
          		object.put("item_pet_like", rs.getString("likes"));
          		object.put("item_share_image_url", rs.getString("photo"));
          		array.add(object);
          	}
          	if (array.size() > 0) {
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("fetch_result", "no_error");
					map.put("data", array.toString());
					JSONObject jsonObject = JSONObject.fromObject(map);
					resp.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));
				    resp.setContentType("text/json; charset=UTF-8");
			    }catch (Exception e) {  
			        e.printStackTrace();
			    }
			} else {
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("fetch_result", "error");
					map.put("error_detail", "No more items.");
					JSONObject jsonObject = JSONObject.fromObject(map);
					resp.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));
				    resp.setContentType("text/json; charset=UTF-8");
			    }catch (Exception e) {  
			        e.printStackTrace();
			    }
			}
			if (rs != null) {  
				try {
					// 关闭记录集 
		            rs.close();   
		        } catch (SQLException e) {   
		            e.printStackTrace();   
		        }   
		    }   
		    if (stmt != null) {
		    	// 关闭声明   
		        try {   
		            stmt.close();   
		        } catch (SQLException e) {   
		            e.printStackTrace();   
		        }   
		    }   
		    if (connection != null){ 
		    	// 关闭连接对象   
		        try {   
		        	connection.close();   
		        } catch (SQLException e) {   
		            e.printStackTrace();   
		        }
		    } 
			
			/*
			//修改数据
			sql = "update users set username = 'miaomiaomiao' where username = 'miaoxingren'";
			stmt.execute(sql);
			
          	//删除数据
			sql = "delete from users where username = 'miaoxingren'";
			stmt.execute(sql);
          
          	//删除表
			sql = "drop table if exists users";
			stmt.execute(sql);
			*/
		} catch (Exception e) {
			e.printStackTrace(resp.getWriter());
		}

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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
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
