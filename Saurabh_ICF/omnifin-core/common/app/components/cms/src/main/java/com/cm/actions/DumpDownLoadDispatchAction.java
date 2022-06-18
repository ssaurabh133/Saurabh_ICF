
package com.cm.actions;

import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.cm.dao.DumpDownLoadDAO;
import com.cm.vo.DumpDownLoadVO;



public class DumpDownLoadDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DumpDownLoadDispatchAction.class.getName());
	
	public ActionForward generatField(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in generatField method of DumpDownLoadDispatchAction");
		HttpSession session =  request.getSession();
		//mradul changes starts
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		if(userobj!=null)
		{
			userId = userobj.getUserId();		
		}
		else
		{
			logger.info("here in generatField method of DumpDownLoadDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		int recordID=0;
		if (request.getParameter("recordID") != null ){
	    	if(!CommonFunction.checkNull(request.getParameter("recordID")).equalsIgnoreCase("")){
	      recordID = Integer.parseInt(request.getParameter("recordID"));
	    	}
	  }
		logger.info("Record Id Come from jsp  :  "+recordID);
		//DumpDownLoadDAO dao= new DumpDownLoadDAOImpl();
		DumpDownLoadDAO dao=(DumpDownLoadDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpDownLoadDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		 ArrayList list=new ArrayList();
		    if(recordID!=0){
		     list = dao.getGeneratFieldInformation(recordID);
		    }
		request.setAttribute("list",list);
		 if(list.size()>0){
		DumpDownLoadVO vo=(DumpDownLoadVO)list.get(0);
		String ParamKeyOne=vo.getParamKeyOne();
		String ParamKeyTwo=vo.getParamKeyTwo();
		String AsOnDate=vo.getAsOnDate();
		String query1=vo.getQuery1();
		String query2=vo.getQuery2();
		String query3=vo.getQuery3();
		String query4=vo.getQuery4();
		String dateRange=vo.getDateRange();
		
		
//		logger.info("ParamKeyOne  :  "+ParamKeyOne);
//		logger.info("ParamKeyTwo  :  "+ParamKeyTwo);
//		logger.info("ParamKeyOne.length()  : "+ParamKeyOne.length());
//		logger.info("ParamKeyTwo.length()  : "+ParamKeyTwo.length());
//		logger.info("AsOnDate  :  "+AsOnDate);
		if(ParamKeyOne.length()!=0)
		{	request.setAttribute("keyOnePr","Y");
			request.setAttribute("ParamKeyOne",ParamKeyOne);
		}
		else
			request.setAttribute("keyOnePr","N");
		if(ParamKeyTwo.length()!=0)
		{	
			request.setAttribute("keyTwoPr","Y");
			request.setAttribute("ParamKeyTwo",ParamKeyTwo); 
		}
		else
			request.setAttribute("keyTwoPr","N");
		
		if(query1.length()!=0)
		{
			request.setAttribute("query1","Y");
			request.setAttribute("query1Label",query1);
		}
		else
			request.setAttribute("query1","N");
		if(query2.length()!=0)
		{
			request.setAttribute("query2","Y");
			request.setAttribute("query2Label",query2);
		}
		else
			request.setAttribute("query2","N");
		if(query3.length()!=0)
		{
			request.setAttribute("query3","Y");
			request.setAttribute("query3Label",query3);
		}
		else
			request.setAttribute("query3","N");
		if(query4.length()!=0)
		{
			request.setAttribute("query4","Y");
			request.setAttribute("query4Label",query4);
		}
		else
			request.setAttribute("query4","N");
		
		request.setAttribute("AsOnDate",AsOnDate);
		request.setAttribute("dateRange",dateRange);
		vo=null;
		 }
		form.reset(mapping, request);
		
		userId=null;
		strFlag=null;
		dao=null;
		return mapping.findForward("success");
	}
	public ActionForward reportGenerator(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in reportGenerator method of DumpDownLoadDispatchAction");
		HttpSession session =  request.getSession();
		//mradul changes
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		if(userobj!=null)
		{
			userId = userobj.getUserId();		
		}
		else
		{
			logger.info("here in reportGenerator method of DumpDownLoadDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		DumpDownLoadVO vo= new DumpDownLoadVO();
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AssetMakerDynaValidatorForm);
		vo.setMaker_ID(userId);
		String recordDesc=CommonFunction.checkNull(vo.getRecordDesc()).trim();
		/*logger.info("Record ID            :   "+recordDesc);
		logger.info("Record Description   :   "+vo.getRecordDesc());
		logger.info("From Date            :   "+vo.getFromDate());
		logger.info("To Date              :   "+vo.getToDate());
		logger.info("param Val1           :   "+vo.getParamValueOne());
		logger.info("param Val1           :   "+vo.getParamValueTwo());
		logger.info("Maker ID             :   "+vo.getMaker_ID());
		logger.info("Query1               :   "+vo.getQuery1());
		logger.info("Query2               :   "+vo.getQuery2());
		logger.info("Query3               :   "+vo.getQuery3());
		logger.info("Query4               :   "+vo.getQuery4());*/
		DumpDownLoadDAO dao=(DumpDownLoadDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpDownLoadDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		try
		{
			if(CommonFunction.checkNull(recordDesc).equals("CGTMSE_DATA_RECORD") || CommonFunction.checkNull(recordDesc).equals("CALL_CENTER_DETAIL")){
				try 
				{
					String fromDate ="";
					String toDate = "";
					
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					String s1="";
					String s2="";
					if(CommonFunction.checkNull(recordDesc).equals("CALL_CENTER_DETAIL")){
						String fromDateArg[] = CommonFunction.checkNull(vo.getFromDate()).split("-");
			    		for(int i=fromDateArg.length-1; i>=0; i--){
			    			fromDate = fromDate+fromDateArg[i]+"-";
			    		}
			    		fromDate = fromDate.substring(0, fromDate.length()-1);
			    		
			    		String toDateArg[] = CommonFunction.checkNull(vo.getToDate()).split("-");
			    		for(int i = toDateArg.length-1;i>=0;i--){
			    			toDate = toDate+toDateArg[i]+"-";
			    		}
			    		toDate = toDate.substring(0, toDate.length()-1);
			    		
						in.add(fromDate);
						in.add(toDate);
						out.add(s1);
						out.add(s2);
						logger.info("INSERT_CALL_CENTER_DUMP ("+in.toString()+","+out.toString()+")");
						outMessages=(ArrayList) ConnectionDAO.callSP("INSERT_CALL_CENTER_DUMP",in,out);
					}else{
						in.add("CGTMSE");
						out.add(s1);
						out.add(s2);
						logger.info("CGTMSE_RECORD_GENERATOR ("+in.toString()+","+out.toString()+")");
						outMessages=(ArrayList) ConnectionDAO.callSP("CGTMSE_RECORD_GENERATOR",in,out);
					}
					
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
				    logger.info("s1  : "+s1);
				    logger.info("s2  : "+s2);	
				    if(s1.equalsIgnoreCase("S"))
					{
						logger.info("Procedure Error Message----"+s2);
					}
					else
					{
						logger.info("Procedure Error Message----"+s2);
					}		
				     in.clear();
				     in=null;
				     out.clear();
				     out=null;
				} catch (Exception e)
				{e.printStackTrace();}
			}
		ArrayList list=dao.reportGenerator(vo);
		logger.info("Report Size  :  "+list.size());
		int size=list.size();
		if(size==0)
		{
			request.setAttribute("error","error");
			return mapping.findForward("success");
		}
		//Nishant space starts
		else
		{
			logger.info("CSV FILE GENERATION STARTS...");
			recordDesc=recordDesc.replaceAll(" ", "_");
			logger.info("File name " + recordDesc);
			String fileName=recordDesc+".csv";
			StringBuffer fileNameFormat = new StringBuffer();
			response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			PrintWriter out = response.getWriter();
			ArrayList subList = new ArrayList();
			try{
				for (int i=0;i<size;i++){
					subList = (ArrayList) list.get(i);
					int subSize=subList.size();
						for(int j=0;j<subSize;j++){
							fileNameFormat.append('"');
							fileNameFormat.append(CommonFunction.checkNull(subList.get(j)));
							fileNameFormat.append('"');
							fileNameFormat.append(',');
						}
						fileNameFormat.append("\n");
				}
			out.write(fileNameFormat.toString());
			}catch(Exception e){
				logger.info("error is:--"+e);
			}finally{
			 subList.clear();
			 list.clear();
			 fileName=null;
			 fileNameFormat.setLength(0);
			 fileNameFormat=null;
			 out.flush();
			 out.close();
			 userId=null;
			 strFlag=null;
			}
			
		}
		//Nishant space ends
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			// Code to insert report name and access log starts
			userId = CommonFunction.checkNull(userobj.getUserId());
			String moduleID=null;
			List<Menu> modid=(List<Menu>) session.getAttribute("leftsidemenulist");
			if(modid!=null)
			{
		        	 moduleID = modid.get(0).getModuleID();
		        	logger.info("moduleID"+moduleID);
			}
			String pageid = CommonFunction.checkNull(session.getAttribute("functionId")).toString();
			String bDate = userobj.getBusinessdate();
			dao.saveFunctionLogData(userId, moduleID, pageid, bDate, "", "", CommonFunction.checkNull(vo.getRecordDesc()).trim(), "");
			// Code to insert report name and access log ends

		
		}
		return null;
		
	}
	
	//sidharth
	public ActionForward reportAdHoc(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in reportAdHoc method of DumpDownLoadDispatchAction");
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		if(userobj!=null)
		{
			userId = userobj.getUserId();		
		}
		else
		{
			logger.info("here in reportAdHoc method of DumpDownLoadDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		DumpDownLoadVO vo= new DumpDownLoadVO();
		DynaValidatorForm AssetMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, AssetMakerDynaValidatorForm);
		vo.setMaker_ID(userId);
		
		String query=vo.getQuery();
		logger.info("Query :  "+query);
		logger.info("Record ID  :   "+vo.getLbxRecordID());
		logger.info("Maker ID   :   "+vo.getMaker_ID());
		
		DumpDownLoadDAO dao=(DumpDownLoadDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpDownLoadDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList list=dao.reportAdHoc(vo);
		logger.info("Report Size  :  "+list.size());
		int size=list.size();
		if(size==0)
		{
			request.setAttribute("error","error");
			return mapping.findForward("hdHocSuccess");
		}
		else
		{
			ServletOutputStream out=response.getOutputStream();
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=Query_Dump.xls");
	 	    workbook.createSheet("MY SHEET_1", 0);		 
			WritableSheet excelSheet =workbook.getSheet(0);
			ArrayList subList = new ArrayList();
			for (int i=0;i<size;i++) 
			{
				subList = (ArrayList) list.get(i);
				int subSize=subList.size();
				for(int j=0;j<subSize;j++)
				excelSheet.addCell(new Label(j,i,CommonFunction.checkNull(subList.get(j))));
			}
			workbook.write();
			workbook.close(); 
			out.flush();
			out.close();
			return null;
		}
				
	
	}
}