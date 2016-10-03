package com.changwen.shiro.shiro_web.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 */
public class DbUtil {

	/**
	 * 获取数据库连接
	 */
	public Connection getCon() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shiro", "root", "123456");

//		Class.forName("oracle.jdbc.OracleDriver");
//		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "123456");
		return con;
	}

	/**
	 * 关闭数据库连接
	 */
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}
   // 测试
	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}
}
