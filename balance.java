package Transaction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/balance.do")
public class balance extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs1 = null;
		
		try {
			
			PrintWriter out=response.getWriter();
			
			Class.forName("com.mysql.jdbc.Driver");
			
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/table", "Student", "student");
			
			myStmt = myConn.createStatement();
			
			myRs1 = myStmt.executeQuery("select * from balance");
			
			myRs1.next();
			
			int one =myRs1.getInt("Your_account");
			
			int acc = Integer.parseInt(request.getParameter("acc_no"));
			
			int amount = Integer.parseInt(request.getParameter("amount"));
			
			out.println("<title>Account Info.</title>");
			out.println("<form action=\"trans.jsp\">");
			out.println("<input style=\"background-color:#6dd5ed; border-color:2px solid black; margin-left:47%; margin-top:2%; border-radius:3px;\""
					+ " type=\"submit\" value=\"Back\"/>");
			out.println("</form>");
			
			if(one<amount) {
				
				out.println("<p style=\"color:red; text-align:center;\">Your transaction is failed, due to low balance!</p>");
				out.println("<p style=\"color:red; text-align:center;\">Your account balance is '"+ one+"' </p>");
			}
			
			if(one>=amount) {
				
				int result=one-amount;
				
				myStmt.executeUpdate("update balance "
						+ "set Your_account="+result
						+ " where " + one+">"+result);
				
				out.println("<p style=\"color:red; text-align:center;\">Your transaction is success!</p>");
				out.println("<p style=\"color:red; text-align:center;\">Your account balance is '"+ result+"' </p>"); 
				
				myRs1 = myStmt.executeQuery("SELECT MAX(No) AS max FROM transaction");
				myRs1.next();
				
				int i=myRs1.getInt("max");
				
				myStmt.executeUpdate("insert into transaction "
						+ "values"
						+ "("+ ++i +","+acc+","+amount+","+"'"+java.time.LocalDate.now()+"'"+")");
				
			}
			
			myRs1 = myStmt.executeQuery("SELECT MAX(No) AS max FROM transaction");
			
			myRs1.next();
			
			int j=myRs1.getInt("max");
			
			myRs1 = myStmt.executeQuery("SELECT * FROM transaction ORDER BY No DESC LIMIT 5");
			
			myRs1.next();
			
			out.println("<p style=\"text-align:center;\"><b style=\"color:green;\">AccNo.&nbsp;&nbsp;</b>"+" "+"<b style=\"color:orange;\">Amount&nbsp;&nbsp;</b>"+" "+" <b style=\"color:purple;\">Date</b></p>");
			
			while(j>=1) {
				
				out.println("<p style=\"text-align:center;\"><b style=\"color:green;\">"+myRs1.getInt("accountno")+"</b>&nbsp;<b style=\"color:orange;\">"+myRs1.getInt("amount")+"</b>&nbsp;<b style=\"color:purple;\">"+myRs1.getDate("date")+"</b></p>");
				myRs1.next();
				j--;
				
			}	
			
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
