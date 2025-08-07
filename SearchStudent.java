import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SearchStudent", urlPatterns={"/searchStudent"})

public class SearchStudent extends HttpServlet
{
	InputStream f = null;
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		
    
		/*String pass1 = req.getParameter("password");
		System.out.println("password :" + pass1);
		String user = req.getParameter("username");
		System.out.println("username :"+ user);*/
		
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		//System.out.println("username :"+username);
		
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","mca6");
			
			PreparedStatement ps = c.prepareStatement("select image from users where password=?");
			ps.setString(1,password);
			ResultSet rs = ps.executeQuery();
			String path = getServletContext().getRealPath("/");
			rs.next();
			f=rs.getBinaryStream("image");
			FileOutputStream f1 = new FileOutputStream(path+"\\"+"abc112"+".jpg");
			int i = 0; 
			while((i=f.read())!=-1)
			{
				f1.write(i);
			}
			out.println("<img src='abc112.jpg' width='160' height='170'>");
		}
		catch(Exception e)
		{
			out.println(e);
		}
		
    
		// Display the HTML form for searching students
		out.println("<html><body>");
		out.println("<h2>Welcome "+username+"<h2>");
		out.println("<h2>Search Student By Student ID</h2>");
		out.println("<form action='searchStudent' method='post'>");
		out.println("<label for='student_id'>Student ID</label>");
		out.println("<input type='text' id='student_id' name='student_id' required><br>");
		out.println("<input type='submit' value='Search'>");
		out.println("</form>");
		out.println("<form action='index.html' method='get'>");
	    out.println("<input type='submit' value='LogOut'>");
		out.println("</form>");
		out.println("</form></body></html>");
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String std_Id = req.getParameter("student_id");
		
		ServletContext  ctx = getServletContext();
		
		String driver = ctx.getInitParameter("driver");
		String url = ctx.getInitParameter("url");
		String user = ctx.getInitParameter("user");
		String pass = ctx.getInitParameter("pass");
		
		out.println("<html><body>");
		
        int stdId;
		//display data
		try
		{
			stdId = Integer.parseInt(std_Id);
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user,pass);
			
			String q = "select * from Student_details where student_id = ?";
			PreparedStatement ps = c.prepareStatement(q);
			ps.setInt(1,stdId);
			
			ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) 
			{
                out.println("<h2>Student Details:</h2>");
                out.println("<p>ID: " + resultSet.getInt(1) + "</p>");
                out.println("<p>Name: " + resultSet.getString(2) + "</p>");
                out.println("<p>Hindi: " + resultSet.getString(3) + "</p>");
				out.println("<p>English: "+ resultSet.getInt(4) + "</p>");
				out.println("<p>Math: "+ resultSet.getInt(5) + "</p>");
				out.println("<p>Science: "+ resultSet.getInt(6) +"</p>");
				out.println("<p>Social Science: "+resultSet.getInt(7) +"</p>");
				out.println("<p>Art: "+resultSet.getInt(8) +"</p>");
				
				
				
				 
                float totalMarks = resultSet.getInt(3) + resultSet.getInt(4) + resultSet.getInt(5) + resultSet.getInt(6) + resultSet.getInt(7) + resultSet.getInt(8);
                float percentage = (totalMarks / 600) * 100; // Assuming each subject is out of 100 marks
                
               
                out.println("<h2>Percentage: " + percentage + "%</h2>");
				
				out.println("<form action='searchStudent' method='get'>");
			    out.println("<input type='submit' value='Back To Home'>");
				out.println("</form>");
				
				
            }
			else 
			{
                out.println("<h2>No student found with ID: " + stdId + "</h2>");
            }
			
			out.println("<form action='downLoadScore' method='get'>");
			out.println("<input type='submit' value='DownloadScore'>");
            out.println("</form>");
            resultSet.close();
            ps.close();
            c.close();
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
		out.println("</body></html>");
    }
}
