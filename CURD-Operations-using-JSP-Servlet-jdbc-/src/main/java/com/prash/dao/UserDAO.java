package com.prash.dao;

public class UserDAO {

	private String jdbcURL="jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String username="root";
	private String password="";
	
	public static final String Insert_Users="INSERT INTO users"+"(name,email,country) VALUES "+"(?,?,?);";
    public static final String Select_Users="SELECT * FROM users";
    public static final String Delete_Users="DELETE FROM users WHERE id=?;";
    public static final String Update_Users="UPDATE users SET name=?,email=?,country=? WHERE id=?;";
}

