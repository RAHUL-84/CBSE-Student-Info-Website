import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

@WebServlet(name="ViewStudents",urlPatterns={"/viewStudents"})

public class ViewStudents extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		ServletContext ctx = getServletContext();
		
		String driver = ctx.getInitParameter("driver");
		String url = ctx.getInitParameter("url");
		String user = ctx.getInitParameter("user");
		String pass = ctx.getInitParameter("pass");
		
		
		try
		{
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user,pass);
			
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Student_details");
            ResultSetMetaData rsmd = rs.getMetaData();
			
			out.println("<h2>Students and Thier Marks</h2>");
            
            out.println("<table bgcolor='#AFDDFF' border='1' width='200'>");
            out.println("<tr>");
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                out.println("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.println("</tr>");
            
            while (rs.next())
            {
                out.println("<tr>");
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
			out.println("<br>");
			out.println("<form action='menu.html' method='get'>");
			out.println("<input type='submit' value='Back Home'><br><br>");
			
        
			s.close();
			rs.close();
			c.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		out.println("</body></html>");
	}
}