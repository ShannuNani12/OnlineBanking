package Banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/View/CheckBalance")
public class GetCurrentBalance extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	PrintWriter out=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	String pin=(String) session.getAttribute("upi");
	System.out.println(req.getParameter("pin"));
	if(pin.equals(req.getParameter("pin")))
		{
		Connection connection;
		try {
		
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
			PreparedStatement ps1=connection.prepareStatement("select Balance from user where Name=? ");
			ps1.setString(1,(String) session.getAttribute("name"));
			ResultSet rs = ps1.executeQuery();
			rs.next();
			int amount=rs.getInt(1);
			out.println("<center><h1 style='color:red;'>Your Current Balance is "+amount+"/-</h1></center>");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	else out.println("<center><h1 style='color:red;'>Invalid UPI Pin</h1></center>");

}
}
