package com.prash.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.prash.model.User;

public class UserDAO {

	private String jdbcURL="jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String username="root";
	private String password="prashant";
	
	public static final String Insert_Users_Query="INSERT INTO users"+"(name,email,country) VALUES "+"(?,?,?);";
    public static final String Select_Users_Query="SELECT * FROM users";
    public static final String Delete_Users_Query="DELETE FROM users WHERE id=?;";
    public static final String Update_Users_Query="UPDATE users SET name=?,email=?,country=? WHERE id=?;";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return connection;
    }


    
    public void insertUser(User user) throws SQLException {
    	try(Connection connection=getConnection()){
    		PreparedStatement preparedStatement=connection.prepareStatement(Insert_Users_Query);
    		preparedStatement.setString(1, user.getName());
    		preparedStatement.setString(2, user.getEmail());
    		preparedStatement.setString(3, user.getCountry());
    		preparedStatement.executeUpdate();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public boolean updateUser(User user) throws SQLException {
    	boolean rowupdated;
    	try(Connection connection=getConnection()){
    		PreparedStatement preparedStatement=connection.prepareStatement(Update_Users_Query);
    		
    		preparedStatement.setString(1, user.getName());
    		preparedStatement.setString(2, user.getEmail());
    		preparedStatement.setString(3, user.getCountry());
    		preparedStatement.setInt(4, user.getId());
    		rowupdated=preparedStatement.executeUpdate()>0;
    	}
    	return rowupdated;
    }
    public User selectUserByID(int id) {
    	User user=null;
    	try(Connection connection=getConnection()){
    		PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
    	    preparedStatement.setInt(1,id );
    	    System.out.println(preparedStatement);
    	    
    	    ResultSet rs=preparedStatement.executeQuery();
    	    while(rs.next()) {
    	    	String name=rs.getString("name");
    	    	String email=rs.getString("email");
    	    	String country=rs.getString("country");
    	    	user=new User(id,name,email,country);
    	    }
    	  
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	  return user;
    }
    public List<User> selectAllUsers() {
    	List<User> users=new ArrayList<>();
    	
    	try(Connection connection=getConnection()){
    		PreparedStatement preparedStatement=connection.prepareStatement(Select_Users_Query);
    	   
    	    System.out.println(preparedStatement);
    	    
    	    ResultSet rs=preparedStatement.executeQuery();
    	    while(rs.next()) {
    	    	int ID=rs.getInt("id");
    	    	String name=rs.getString("name");
    	    	String email=rs.getString("email");
    	    	String country=rs.getString("country");
    	    	users.add(new User(ID,name,email,country));
    	    }
    	  
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	  return users;
    }
    public boolean deleteUser(int id) throws SQLException {
    boolean rowDeleted=false;
    try(Connection connection=getConnection()){
    	PreparedStatement preparedStatement=connection.prepareStatement(Delete_Users_Query);
    	preparedStatement.setInt(1, id);
    	rowDeleted=preparedStatement.executeUpdate()>0;
    }catch(Exception e) {
    e.printStackTrace();
    }
    return rowDeleted;
    }

   
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.getConnection();
   
}
}

