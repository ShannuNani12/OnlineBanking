package Banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/View/Account")
public class AccountCreation extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	PrintWriter out=resp.getWriter();
	resp.setContentType("text/html");
	
	Random random=new Random();
	try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
	PreparedStatement ps=connection.prepareStatement("insert into user values(?,?,?,?,?,?,10000,?)");
	ps.setString(1,req.getParameter("Mobile"));
	ps.setString(2,req.getParameter("name"));
	ps.setString(3,req.getParameter("acc"));
	ps.setString(4,req.getParameter("accNo"));
	ps.setString(5,req.getParameter("password"));
	ps.setString(6,req.getParameter("upi"));
	ps.setInt(7,random.nextInt(10000,99999));
	ps.executeLargeUpdate();
	
	RequestDispatcher dispatcher=req.getRequestDispatcher("LoginPage.html");
	dispatcher.include(req, resp);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
