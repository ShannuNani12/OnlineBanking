package Banking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/View/Money")
public class PaymentPassword extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	PrintWriter out=resp.getWriter();
	String getUser = req.getParameter("getUser");
	HttpSession session=req.getSession();
	session.setAttribute("Amount",req.getParameter("money"));
	 RequestDispatcher dispatcher=req.getRequestDispatcher("PaymentPassword.html");
	  dispatcher.forward(req, resp);
}
}
