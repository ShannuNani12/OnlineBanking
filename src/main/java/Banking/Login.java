package Banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/View/Login")
public class Login  extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	PrintWriter out=resp.getWriter();
	resp.setContentType("text/html");
	System.out.println(req.getParameter("mail"));
	System.out.println(req.getParameter("password"));
	try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
	      PreparedStatement ps=connection.prepareStatement("select * from user where MobileNumber=? and Password=?");
	       ps.setString(1,req.getParameter("mail"));
	       ps.setString(2,req.getParameter("password"));
	       ResultSet rs = ps.executeQuery();
	       if(rs.isBeforeFirst())
	       {
	    	   rs.next();
	    	   HttpSession session =req.getSession();
	    	   session.setAttribute("name",rs.getString(2));
	    	   session.setAttribute("upi",rs.getString(6));
	    	   session.setAttribute("userUpi",rs.getInt(8));
	    	   out.println("<center><h1 style='color:red;'>Hello Mr."+session.getAttribute("name")+"</h1></center>");
	    	   RequestDispatcher dispatcher=req.getRequestDispatcher("HomePage.html");
	    	   dispatcher.include(req, resp);
	       }
	       else out.println("<center><h1 style='color:red;'>Invalid User</h1></center>");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
