

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class ForgetAction extends GenericServlet {
	
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
		String U_Email=request.getParameter("em");
		String U_Phoneno=request.getParameter("ph");
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
				
			
			jr.setCommand("select Email,Phoneno from amazon");
			
			jr.execute();
			
			//	data fetching from jr using getXXXX method
			
			Vector v=new Vector();
			
			for(;jr.next();)
			{
				v.add(jr.getString("Email"));
				v.add(jr.getString("Phoneno"));
			}
			
			//	comparing DB data ui
			
			PrintWriter out=response.getWriter();
			
			if(v.contains(U_Email) && v.contains(U_Phoneno))
			{
			//	out.println("Amazon Login Sucessfull");
				
			//	Class.forName(driver).newInstance();
			//	Connection con=DriverManager.getConnection(URL, User_name, Pass);
			//	System.out.println(con);		
				
				
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "vine96");
				System.out.println(con);		
				
				
				PreparedStatement ps=con.prepareStatement("update amazon set Username=?, Password=? where Email=?");
				
				ps.setString(1, U_Username);
				ps.setString(2, U_Password);
				ps.setString(3, U_Email);
				
				ps.addBatch();
				
				//	update batch wise record into DB
				
				int a[]=ps.executeBatch();
				
				for(int x:a)
				{
					con.commit();
					out.println("Username and Password is updated:	"+x);
					

				    out.println("Driver class name	:"+driver);
					out.println("URL				:"+URL);
					out.println("Username			:"+User_name);
					out.println("Password			:"+Pass);
				}
				
			}
			else {
				out.println("Invalid Email or Phoneno");
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
