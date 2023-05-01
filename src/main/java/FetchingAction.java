

import java.io.IOException;
import java.io.PrintWriter;
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
public class FetchingAction extends GenericServlet {
	
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
		String U_Username=request.getParameter("view");
		
		try {
		/*	Class.forName(driver);
			RowSetFactory rf=RowSetProvider.newFactory();
			
			JdbcRowSet jr=rf.createJdbcRowSet();
			
			jr.setUrl(URL);
			jr.setUsername(User_name);
			jr.setPassword(Pass);
		*/	
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			RowSetFactory rf=RowSetProvider.newFactory();
			
			JdbcRowSet jr=rf.createJdbcRowSet();
			
			jr.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jr.setUsername("system");
			jr.setPassword("vine96");
		
			
			jr.setCommand("select * from amazon where Username=?");
			
			jr.setString(1, U_Username);
			
			jr.execute();
			
			PrintWriter out=response.getWriter();
			
			Vector v=new Vector();
			
			for(;jr.next();)
			{
				v.add(jr.getString("Username"));
				System.out.println(v);
				if(v.contains(U_Username))
				{
					out.println("<table border=1");
					out.println("<td>"+jr.getString(1)+"</td>");
					out.println("<td>"+jr.getString(2)+"</td>");
					out.println("<td>"+jr.getString(3)+"</td>");
					out.println("<td>"+jr.getString(4)+"</td>");
					out.println("<td>"+jr.getString(5)+"</td>");
					out.println("<td>"+jr.getString(6)+"</td>");
					out.println("<td>"+jr.getString(7)+"</td>");
					out.println("<td>"+jr.getString(8)+"</td>");
					out.println("</table>");
					
				    out.println("Driver class name	:"+driver);
					out.println("URL				:"+URL);
					out.println("Username			:"+User_name);
					out.println("Password			:"+Pass);			
				}
				else {
					out.println("User don't have an account");
				}
							
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void destroy() {
		
	}

}
