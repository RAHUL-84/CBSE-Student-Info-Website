import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="DownLoadScore", urlPatterns={"/downLoadScore"})
public class DownLoadScore extends HttpServlet 
{
    

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
        res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-Disposition","attachment;filename=result.xls");
        PrintWriter out = res.getWriter();

        ServletContext ctx = getServletContext();

        String driver = ctx.getInitParameter("driver");
        String url = ctx.getInitParameter("url");
        String user = ctx.getInitParameter("user");
        String pass = ctx.getInitParameter("pass");

        String studentId = req.getParameter("student_id");
         
		int id = 0;
        String name = "";
        int Hindi = 0;
        int English = 0;
        int Math = 0;
        int Science = 0;
        int Social_Science = 0;
        int Computer = 0;

        try {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url, user, pass);

            String sql = "select * from student_details WHERE student_id=?";
			PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1,Integer.parseInt(studentId));
            ResultSet rs = s.executeQuery();

            while (rs.next())
			{
				id = rs.getInt(1);
                name = rs.getString(2);
                Hindi = rs.getInt(3);
                English = rs.getInt(4);
                Math = rs.getInt(5);
                Science = rs.getInt(6);
                Social_Science = rs.getInt(7);
                Computer = rs.getInt(8);
            }

            out.println("id\tname\thindi\teng\tmath\tscience\tsocialScience\tcomputer");
            out.println(id + "\t" + name + "\t" + Hindi + "\t" + English + "\t" + Math + "\t" + Science + "\t" + Social_Science + "\t" + Computer);

            s.close();
            c.close();
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
    }
}
