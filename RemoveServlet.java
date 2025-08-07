import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;


@WebServlet("/removeStudent")
public class RemoveServlet extends HttpServlet 
{   
    public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the student ID from the form
        String stdIdStr = request.getParameter("StdId");

        if (stdIdStr == null || stdIdStr.trim().isEmpty()) {
            out.println("Student ID is required.");
            return;
        }

        int stdId;
        try {
            stdId = Integer.parseInt(stdIdStr);
        } catch (NumberFormatException e) {
            out.println("Invalid Student ID format.");
            return;
        }
		
		ServletContext ctx = getServletContext();
		
		String driver = ctx.getInitParameter("driver");
        String url = ctx.getInitParameter("url");
        String user = ctx.getInitParameter("user");
        String pass = ctx.getInitParameter("pass");

        // Database logic
        try {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url,user,pass);

            PreparedStatement ps = c.prepareStatement("DELETE FROM Student_Details WHERE Student_Id = ?");
            ps.setInt(1, stdId);

            int result = ps.executeUpdate();

            ps.close();
            c.close();
			
			
			
            if (result > 0) {
				out.println("<html><body><center>");
                out.println("<br><br><br><h2>Student Removed Successfully</h2>");
				
            } else {
                out.println("<br><br><br><h2>Failed To Remove Student <br> No matching record found</h2>");
				
            }
			response.setHeader("Refresh","3;RemoveStudent.html");
			out.println("</center></body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
