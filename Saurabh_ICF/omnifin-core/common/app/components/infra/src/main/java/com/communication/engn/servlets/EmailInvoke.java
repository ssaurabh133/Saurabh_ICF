package com.communication.engn.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class EmailInvoke extends HttpServlet 
{
	public void destroy()
	{
		super.destroy(); 
	}
	public void init() throws ServletException 
	{
		String path = getServletContext().getRealPath("/WEB-INF/template/Email_Template.html");
		MessageScheduler msgscheduler=new MessageScheduler();
		ServletConfig sc=getServletConfig();
		String hour=sc.getInitParameter("HOUR");
		String mimute=sc.getInitParameter("MINUTE");
		String second=sc.getInitParameter("SECOND");	
		msgscheduler.startMessageTask(path,hour,mimute,second);
		
	}
}
