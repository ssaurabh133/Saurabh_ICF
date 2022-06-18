/*start  : Abhishek Mathur*/

package com.communication.engn.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;


public class MISDataGenerationAction extends DispatchAction {
	
	private static final Logger logger = Logger
			.getLogger(commonFunctionDaoImpl.class.getName());
	public ActionForward callSmsEmailTrigger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String reportPath="/reports/MYSQLREPORTS/";
		String reportName = "MIS_REPORT";
		
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		
		String sub_reports_location=getServlet().getServletContext().getRealPath("/")+"reports\\";
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
		sub_reports_location=sub_reports_location+"MYSQLREPORTS\\";
		SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
		String EventType = CommonFunction.checkNull((String)request.getParameter("eventType"));
		String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(date_sub(PARAMETER_VALUE, interval 1 day),'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY LIKE 'BUSINESS_DATE'"));
		
		Map reportParameterMap = new HashMap();
		StringBuffer outBuffer = new StringBuffer();
		
		reportParameterMap.put("sub_reports_location", sub_reports_location);
		reportParameterMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
		reportParameterMap.put("p_asonDate", bdate);
		
		generateHTMLFromReport(reportName, reportStream, reportParameterMap, outBuffer, EventType);
		
		System.out.println("MIS Sucessfully Sent mail to All Users");
		System.out.println("MIS Scheduler will call start Next day at same time: ==>> "+new Date());
			
		return null;
	}
	
	private void generateHTMLFromReport(String reportName,
			InputStream reportStream, Map reportParameterMap,
			StringBuffer outBuffer, String EventType) throws Exception{
		//Connection con = getTestConnection();
		new MISTriggerAction().generateHTMLFromReport(reportName, reportStream, reportParameterMap, outBuffer);
		System.out.println("outBuffer: "+outBuffer.toString());
		
		SmsVO vo = new SmsVO();
		String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY LIKE 'BUSINESS_DATE'"));
		vo.setbDate(bdate);
		vo.setStage("ONCLICK");
		
		if(CommonFunction.checkNull(EventType).equalsIgnoreCase("E"))
		vo.setEventName("MIS_Report");
		else if(CommonFunction.checkNull(EventType).equalsIgnoreCase("I"))
		vo.setEventName("MIS_Report_INTERNAL");	
		
		vo.setMessage(outBuffer.toString());
		String emailList = 	ConnectionDAO.singleReturn("SELECT VALUE FROM GENERIC_MASTER WHERE GENERIC_KEY = '"+vo.getEventName()+"' AND REC_STATUS = 'A'");
		
					logger.info("MIS_Report-->"+emailList);
					String qury2="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
	  	    		String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
	  	    		if(recemail.equalsIgnoreCase("A")){
	  	    			vo.setTemplate("E");
	  	    			vo.setEmailId(emailList);
	  	    			boolean status= new SmsDAOImpl().InsertData(vo);
	  	    			
	  	    			String EmailerListKey = "";
	  	    			if(CommonFunction.checkNull(EventType).equalsIgnoreCase("E"))
	  	    				EmailerListKey = "MIS_EMAIL";
	  	    			else if(CommonFunction.checkNull(EventType).equalsIgnoreCase("I"))
	  	    				EmailerListKey = "MIS_EMAIL_INTERNAL";
	  	    			
	  	    			String ToEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = '"+EmailerListKey+"' AND REC_STATUS = 'A' AND PARENT_VALUE = 'TO'");
	  	    			String CCEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = '"+EmailerListKey+"' AND REC_STATUS = 'A' AND PARENT_VALUE = 'CC'");
	  	    			boolean SendEmailStatus= new SmsDAOImpl().MultipleInToSendEmail(vo,ToEmailId,CCEmailId); 
	  	    			logger.info("Send Email -->"+SendEmailStatus);
	  	    			if(SendEmailStatus){
	  	    				StringBuffer updatLoan = new StringBuffer();
	  	    				ArrayList queryList=new ArrayList();
			            	updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = '"+vo.getEventName()+"'");
			            	PrepStmtObject prepStmt = new PrepStmtObject();
			            	prepStmt.setSql(updatLoan.toString());	
			            	queryList.add(prepStmt);		           
			            	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList); 
			            	logger.info("Update Event Flag : "+status);
	  	    			}
	  	    		}else
	  	    		{
	  	    			logger.info("Email is not active on event: '"+vo.getEventName()+"'");
	  	    		}
			 
		 
	//	con.close();
		
	}

	/*public static void main(String[] args) {
		MISDataGenerationAction gen = new MISDataGenerationAction();

		String reportName = "MIS_REPORT";
		//String testReportPath = "E:/workspaces/IndoStar/IndoStar_SME/INDOSTAR_SME_DEV/omnifin-core/common/app/components/reports/src/main/webapp/reports/MYSQLREPORTS/Dashboard/";
		String reportFilePath="C:/Users/abhishek.mathur/Desktop/IndoStarMIS/MIS_REPORT.jasper";
		
		Map reportParameterMap = new HashMap();

		InputStream reportStream = null;
		StringBuffer outBuffer = new StringBuffer();

		try {
			reportStream = new FileInputStream(new File(reportFilePath));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			gen.generateHTMLFromReport(reportName, reportStream, reportParameterMap, outBuffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("outBuffer: "+outBuffer.toString());
	}
	*/
	/*private Connection getTestConnection() throws Exception{

			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://192.168.1.197:3308/indostar_housing_uat14may","root","a3spl");  
	
		return con;
	}
	*/
	
}