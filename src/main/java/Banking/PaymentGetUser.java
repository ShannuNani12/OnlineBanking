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
@WebServlet("/View/GetUser")
public class PaymentGetUser extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	PrintWriter out=resp.getWriter();
	String getUser = req.getParameter("getUser");
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	
	try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
		PreparedStatement ps=connection.prepareStatement("select * from user where MobileNumber=? ");
	       ps.setString(1,getUser);
	       ResultSet rs = ps.executeQuery();
	if(rs.isBeforeFirst())
	{
		rs.next();
		String name=rs.getString(2);
		 out.println("<center><h1 style='color:green;'>You Are Paying Mr."+name+"</h1></center>");
		 RequestDispatcher dispatcher=req.getRequestDispatcher("PaymentTransaction.html");
	  	   dispatcher.include(req, resp);
         session.setAttribute("getUser",name );
         session.setAttribute("getUserUpiID",rs.getInt(8) );
	}
	else 
		{ 
		out.println("<center><h1 style='color:red;'>Invalid User </h1></center>");
		 RequestDispatcher dispatcher=req.getRequestDispatcher("PaymentIncitation.html");
  	   dispatcher.include(req, resp);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
