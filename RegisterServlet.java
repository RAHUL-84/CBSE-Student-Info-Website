import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import com.oreilly.servlet.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet 
{
    public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String path = getServletContext().getRealPath("image");
		MultipartRequest mpr = new MultipartRequest(req,path,500*1024*1024);
		
		String path1 = mpr.getOriginalFileName("file");//file is the req parameter name
		//System.out.println(path1);
		String path2 = path+"/"+path1;
		FileInputStream fin = new FileInputStream(path2);
		
		String user = mpr.getParameter("username");
		String pass = mpr.getParameter("password");
		String role = mpr.getParameter("role");
		
		System.out.println("Username: " + user);
		System.out.println("Password: " + pass);
		System.out.println("Role: " + role);
		
		ServletContext ctx = getServletContext();
		
		String driver = ctx.getInitParameter("driver");
        String url = ctx.getInitParameter("url");
        String user1 = ctx.getInitParameter("user");
        String pass1 = ctx.getInitParameter("pass");
		
		try
		{
			Class.forName(driver);
			Connection c = DriverManager.getConnection(url,user1,pass1);
			PreparedStatement ps = c.prepareStatement("insert into users values(?,?,?,?)");
			ps.setBinaryStream(4,fin,fin.available());
			ps.setString(1,user);
			ps.setString(2,pass);
			ps.setString(3,role);
			ps.executeUpdate();
			c.close();
		}
		catch(Exception e)
		{
		}
		out.println("<html><body>");
		out.println("<Center>");
		out.println("Registratered  successfully");
		out.println("</body></html>");
		res.setHeader("Refresh","4;index.html");
	}

}
