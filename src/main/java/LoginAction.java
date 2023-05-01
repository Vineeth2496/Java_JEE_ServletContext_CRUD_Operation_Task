

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

public class LoginAction extends GenericServlet {
	String driver, URL, User_name, Pass;
	public void init(ServletConfig sc) throws ServletException
	{
		ServletContext ctxt=sc.getServletContext();
		driver=ctxt.getInitParameter("driver");
		URL=ctxt.getInitParameter("URL");
		User_name=ctxt.getInitParameter("User_name");
		Pass=ctxt.getInitParameter("Pass");
	}
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
		String U_Username=request.getParameter("un");
		String U_Password=request.getParameter("pass");
		
		try
		{
		//	Class.forName(driver);
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// jdbcRowSet
			
			RowSetFactory rf=RowSetProvider.newFactory();
			JdbcRowSet jr=rf.createJdbcRowSet();
			
		//	jr.setUrl(URL);
		//	jr.setUsername(User_name);
		//	jr.setPassword(Pass);
			
			jr.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jr.setUsername("system");
			jr.setPassword("vine96");
			
			jr.setCommand("select Username, Password from amazon");
			
			jr.execute();
			
			//	data fetching from jr using getXXXX method
			
			Vector v=new Vector();
			
			for(;jr.next();)
			{
				
			
				v.add(jr.getString("Username"));
				v.add(jr.getString("Password"));
			}
			
			//	comparing DB data ui
			
			PrintWriter out=response.getWriter();
			
			if(v.contains(U_Username) && v.contains(U_Password))
			{
				out.println("Amazon Login Sucessfull");
				
			    out.println("Driver class name	:"+driver);
				out.println("URL				:"+URL);
				out.println("Username			:"+User_name);
				out.println("Password			:"+Pass);
				
			}
			else {
				out.println("Invalid Username or Password");
			}
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
