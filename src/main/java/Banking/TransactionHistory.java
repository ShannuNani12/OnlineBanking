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
@WebServlet("/View/TransactionHistory")
public class TransactionHistory  extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	PrintWriter out=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
		PreparedStatement ps=connection.prepareStatement("select * from transactions where UpiId=?");
		ps.setInt(1,(int)session.getAttribute("userUpi"));
		System.out.println((int)session.getAttribute("userUpi"));
		ResultSet rs = ps.executeQuery();
		out.println("<html><body>");
		out.println("<br><br><br>");
		out.println("<center>");
	    out.println("<h1>TransactionHistory</h1>");
	    out.println("<br><br>");
	    out.println("<table style=\"border: 3px solid black;border-collapse: collapse;\">");
        out.println("<tr><th style=\"border: 2px solid black;colspan='2'\">Transaction</th><th style=\"border: 2px solid black;colspan='2'\">Name</th><th style=\"border: 2px solid black;colspan='2'\">DATE</th><th style=\"border: 2px solid black;colspan='2'\">TIME</th><th style=\"border: 2px solid black;colspan='2'\">Amount</th></tr>");
        if(rs.isBeforeFirst())
		{
			while(rs.next())
			{
				if(rs.getString("transaction").equals("debited"))
				{
				out.println("<tr>");
				out.println("<td style=\"border: 2px solid black; color:red;\">"+rs.getString("transaction")+"</td>");
				out.println("<td style=\"border: 2px solid black; color:red;\">"+rs.getString("To")+"</td>");
				out.println("<td style=\"border: 2px solid black; color:red;\">"+rs.getDate("Date")+"</td>");
				out.println("<td style=\"border: 2px solid black; color:red;\">"+rs.getTime("Time")+"</td>");
				out.println("<td style=\"border: 2px solid black; color:red;\">"+rs.getInt("Amount")+"</td>");
				out.println("</tr>");
				}
				else{
					out.println("<tr>");
					out.println("<td style=\"border: 2px solid black; color:green;\">"+rs.getString("transaction")+"</td>");
					out.println("<td style=\"border: 2px solid black; color:green;\">"+rs.getString("To")+"</td>");
					out.println("<td style=\"border: 2px solid black; color:green;\">"+rs.getDate("Date")+"</td>");
					out.println("<td style=\"border: 2px solid black; color:green;\">"+rs.getTime("Time")+"</td>");
					out.println("<td style=\"border: 2px solid black; color:green;\">"+rs.getInt("Amount")+"</td>");
					out.println("</tr>");
					}
				
			}
		}
	    out.println("</table>");
		out.println("</center>");
        out.println("</body></html>");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
