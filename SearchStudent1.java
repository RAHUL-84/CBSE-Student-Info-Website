import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SearchStudent1", urlPatterns={"/searchStudent1"})

public class SearchStudent1 extends HttpServlet
{
	InputStream f = null;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		//String password = (String) session.getAttribute("password");
		
		
		ServletContext ctx = getServletContext();
		String user = ctx.getInitParameter("user");
		String url = ctx.getInitParameter("url");
		String driver = ctx.getInitParameter("driver");
		String pass = ctx.getInitParameter("pass");
		out.println("<html><body>");
		out.println("<h2>Welcome "+username+"<h2>");
		out.println("<h2>Search Student By Student ID</h2>");
		out.println("<form action='searchStudent1' method='post'>");
		out.println("<label for='student_id'>Student ID</label>");
		out.println("<input type='text' id='student_id' name='student_id' required>");
		out.println("<input type='submit' value='Search'>");
		out.println("</form></body></html>");
		
		String std_id = req.getParameter("Std_Id");
		
		int stdId;
		 try {
			     stdId = Integer.parseInt(std_id);
			 
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,user,pass);

            PreparedStatement pst = con.prepareStatement("SELECT * FROM Student_Details WHERE Student_Id = ?");
            pst.setInt(1, stdId);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				
				out.println("<h2>Student Details:</h2>");
                out.println("<p>ID: " + rs.getInt(1) + "</p>");
                out.println("<p>Name: " + rs.getString(2) + "</p>");
                out.println("<p>Hindi: " + rs.getString(3) + "</p>");
				out.println("<p>English: "+ rs.getInt(4) + "</p>");
				out.println("<p>Math: "+ rs.getInt(5) + "</p>");
				out.println("<p>Science: "+ rs.getInt(6) +"</p>");
				out.println("<p>Social Science: "+rs.getInt(7) +"</p>");
				out.println("<p>Art: "+rs.getInt(8) +"</p>");
				
				
				
				 
                float totalMarks = rs.getInt(3) + rs.getInt(4) + rs.getInt(5) + rs.getInt(6) + rs.getInt(7) + rs.getInt(8);
                float percentage = (totalMarks / 600) * 100; // Assuming each subject is out of 100 marks
                out.println("<h2>Total Marks : "+totalMarks+"</h2>");
               
                out.println("<h2>Percentage: " + percentage + "%</h2>");
				
				out.println("<a href='searchStudent'>Back To Home</a>");
				
				out.println("<form action='downLoadScore' method='post'>");
			}
			else 
			{
                out.println("<h2>No student found with ID: " + stdId + "</h2>");
            }
			
			out.println("<input type='submit' value='DownloadScore'><br><br>");
			
			rs.close();
            pst.close();
            con.close();
		 }
		 catch (Exception e) 
		{
            e.printStackTrace();
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
		out.println("</body></html>");
    }
}