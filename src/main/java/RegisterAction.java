

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

public class RegisterAction extends GenericServlet {
	String driver, URL, User_name, Pass;
	public void init(ServletConfig sc)
	{
		ServletContext ctxt=sc.getServletContext();
		driver=ctxt.getInitParameter("driver");
		URL=ctxt.getInitParameter("URL");
		User_name=ctxt.getInitParameter("User_name");
		Pass=ctxt.getInitParameter("Pass");
		
	}
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
	{
		String Firstname=request.getParameter("fn");
		String Lastname=request.getParameter("ln");
		String Email=request.getParameter("em");
		String Stdcode=request.getParameter("ph");
		String Phoneno=request.getParameter("no");
		String Username=request.getParameter("un");
		String Password=request.getParameter("pass");
		String Gender=request.getParameter("gm");
		try
		{
		//	Class.forName(driver).newInstance();
		//	Connection con=DriverManager.getConnection(URL, User_name, Pass);
		//	System.out.println(con);
			
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "vine96");
			System.out.println(con);
			
			PreparedStatement ps=con.prepareStatement("insert into amazon values(?,?,?,?,?,?,?,?)");
			ps.setString(1, Firstname);
			ps.setString(2, Lastname);
			ps.setString(3, Email);
			ps.setString(4, Stdcode);
			ps.setString(5, Phoneno);
			ps.setString(6, Username);
			ps.setString(7, Password);
			ps.setString(8, Gender);
			
			int i=ps.executeUpdate();
			
			PrintWriter out=response.getWriter();
			
			out.println("Amazon Account is registerd: "+"\n"+"Username: "+Username+"\n"+"Password: "+Password);
			
		    out.println("Driver class name	:"+driver);
			out.println("URL				:"+URL);
			out.println("Username			:"+User_name);
			out.println("Password			:"+Pass);		    
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void destroy()
	{
		
	}

}
