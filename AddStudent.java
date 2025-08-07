import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
 
public class AddStudent extends HttpServlet 
{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //res.setContentType("text/html");
        ServletContext ctx = getServletContext();
        PrintWriter out = res.getWriter();

        
        String driver = ctx.getInitParameter("driver");
        String url = ctx.getInitParameter("url");
        String user = ctx.getInitParameter("user");
        String pass = ctx.getInitParameter("pass");
        
        String studentId = req.getParameter("studentId");
        String studentName = req.getParameter("studentName");
        String hindi = req.getParameter("hindi");
        String english = req.getParameter("english");
        String math = req.getParameter("mathematics");
        String science = req.getParameter("science");
        String socialScience = req.getParameter("socialScience");
        String computer = req.getParameter("computer");
		//System.out.println("Hindi :"+hindi);

        try 
        {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url, user, pass);

            String query = "INSERT INTO Student_details (student_Id, student_Name, hindi, english, math, science, socialScience, computer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, studentName);
            ps.setInt(3, Integer.parseInt(hindi));
            ps.setInt(4, Integer.parseInt(english));
            ps.setInt(5, Integer.parseInt(math));
            ps.setInt(6, Integer.parseInt(science));
            ps.setInt(7, Integer.parseInt(socialScience));
            ps.setInt(8, Integer.parseInt(computer));

            int result = ps.executeUpdate();
			out.println("<html><body>");
            if (result > 0) 
            {
                out.println("<h2><br><br><br><center>Student Added successfully</center></h2>");
            } 
            else
            {
                out.println("<h1>Failed to add record</h1>");
            }
			
            res.setHeader("Refresh","3;AddStudent.html");
            out.println("</body></html>");
        }
        catch (Exception e) 
        {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out); // Print stack trace for better debugging
        }  
    }
}
