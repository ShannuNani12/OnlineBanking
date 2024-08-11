package Banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/View/GetTransaction")
public class GetTransaction  extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	LocalTime time=LocalTime.now();
	LocalDate date=LocalDate.now();
	PrintWriter out=resp.getWriter();
	resp.setContentType("text/html");
	HttpSession session=req.getSession();
	String pin=(String) session.getAttribute("upi");
	System.out.println(req.getParameter("pin"));
	if(pin.equals(req.getParameter("pin")))
		{
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebanking?user=root&password=12345");
			PreparedStatement ps1=connection.prepareStatement("select Balance from user where Name=? ");
			ps1.setString(1,(String) session.getAttribute("name"));
			ResultSet rs = ps1.executeQuery();
			rs.next();
			System.out.println((String)session.getAttribute("Amount"));
			String amount1= (String)session.getAttribute("Amount");
			int amount = Integer.parseInt(amount1);
			if(rs.getInt(1)>(amount))
			{
				PreparedStatement ps2=connection.prepareStatement("update user set  Balance = Balance - ? where UpiId=? ");
				ps2.setInt(1,amount);
				ps2.setInt(2,(int) session.getAttribute("userUpi") );
				ps2.executeUpdate();
			    PreparedStatement ps3=connection.prepareStatement("update user set  Balance = Balance + ? where UpiId=? ");
			    ps3.setInt(1,amount);
				ps3.setInt(2,(int) session.getAttribute("getUserUpiID") );
				ps3.executeUpdate();
				PreparedStatement ps4=connection.prepareStatement("insert into transactions values(?,?,'debited',?,?,?,?)");
				ps4.setInt(1,(int) session.getAttribute("userUpi"));
				ps4.setString(2, (String) session.getAttribute("name"));
				ps4.setString(3,(String) session.getAttribute("getUser"));
				ps4.setDate(4,Date.valueOf(date));
				ps4.setTime(5,Time.valueOf(time));
				ps4.setInt(6, amount);
				ps4.executeUpdate();
//				UpiID, From, transaction, To, date, time, amount
				PreparedStatement ps5=connection.prepareStatement("insert into transactions values(?,?,'credited',?,?,?,?)");
				ps5.setInt(1,(int) session.getAttribute("getUserUpiID"));
				ps5.setString(2, (String) session.getAttribute("getUser"));
				ps5.setString(3,(String) session.getAttribute("name"));
				ps5.setDate(4,Date.valueOf(date));
				ps5.setTime(5,Time.valueOf(time));
				ps5.setInt(6, amount);
				ps5.executeUpdate();
				RequestDispatcher dispatcher=req.getRequestDispatcher("PaymentSuccessful.html");
				dispatcher.forward(req, resp);
			}
		
			else out.println("<center><h1 style='color:red;'>Insufficient Funds</h1></center>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	else out.println("<center><h1 style='color:red;'>Invalid Pin</h1></center>");
		
}
}
