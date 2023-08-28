package com.prash.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.prash.dao.UserDAO;
import com.prash.model.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public UserServlet() {
        super();
        this.userDAO=new UserDAO();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action=request.getServletPath();
	    
	    switch(action){
	    	case "/new":
	    		showForm(request,response);
	    		break;
	    	case "/insert":
			try {
				insertUser(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	case "delete":
			try {
				deleteUser(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	case "edit":
			try {
				showEditForm(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	case "update":
			try {
				updateUser(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    	default:
			try {
				listUser(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    		break;
	    }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("user-form.jsp").forward(request, response);
	}
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	     String name=request.getParameter("name");
	     String email=request.getParameter("email");
	     String country=request.getParameter("country");
	     User newuser=new User(name,email,country);
	     userDAO.insertUser(newuser);
	     response.sendRedirect("list");
	 
	}
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	      int id=Integer.parseInt(request.getParameter("id"));
	       userDAO.deleteUser(id);
	       response.sendRedirect("list");
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	     int id=Integer.parseInt(request.getParameter("id")); 
	     boolean existinguser=userDAO.deleteUser(id);
	     jakarta.servlet.RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
	     request.setAttribute("user", existinguser);
	     dispatcher.forward(request, response);
	}
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    int id=Integer.parseInt(request.getParameter("id"));
	    String name=request.getParameter("name");
	    String email=request.getParameter("email");
	    String country=request.getParameter("country");
	    
	    User user=new User(id,name,email,country);
	    userDAO.updateUser(user);
	    response.sendRedirect("list");
	}
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    List<User> listUser=userDAO.selectAllUsers(0);	
	    request.setAttribute("listUser", listUser);
	    jakarta.servlet.RequestDispatcher dispatcher=request.getRequestDispatcher("user-list.jsp");
	    dispatcher.forward(request, response);
	}
}



