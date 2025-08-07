import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		
		ServletContext ctx = getServletContext();
		String user = ctx.getInitParameter("user");
		String url = ctx.getInitParameter("url");
		String driver = ctx.getInitParameter("driver");
		String pass = ctx.getInitParameter("pass");
		

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,user,pass);

            PreparedStatement st = con.prepareStatement("SELECT * FROM Users WHERE Username = ? AND Password = ?");
            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) 
			{
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role))   //checking role of user
				{
					
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					session.setAttribute("password", password);
                    response.sendRedirect("menu.html");
					
                } else if ("student".equalsIgnoreCase(role)) 
				{
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					session.setAttribute("password", password);
					
                    response.sendRedirect("searchStudent");
                } else 
				{
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("Invalid role provided ");
					response.setHeader("Refresh","5;index.html");
                }
            } 
			else 
			{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("Wrong username or password\nPlease Check Your Username or Password");
				response.setHeader("Refresh","5;index.html");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("An error occurred" + e);
        } finally {
            out.close();
        }
    }
}
