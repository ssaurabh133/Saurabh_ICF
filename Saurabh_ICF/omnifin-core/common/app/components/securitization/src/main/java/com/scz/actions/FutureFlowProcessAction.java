package com.scz.actions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.ConnectionUploadDAO;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.dao.FutureFlowLoanUploadDAO;
import com.scz.daoHibImpl.FutureFlowLoanIDUploadHibImplService;
import com.scz.vo.FutureFlowLoanUploadVO;
import com.scz.vo.FutureFlowReportTempVO;

public class FutureFlowProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(FutureFlowProcessAction.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dbType=resource.getString("lbl.dbType");
	
	public ActionForward generateReportForFutureFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

		 logger.info("the method is in generateReportDesc");
			HttpSession session =  request.getSession();		
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId ="";
			String businessDate ="";
			String p_company_name ="";
		    int compid =0;
			String p_printed_by="";
			if(userobj!=null)
			{
				userId = userobj.getUserId();
			    businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
				p_company_name=userobj.getConpanyName();
				p_printed_by=userobj.getUserName();
				logger.info("company id is :: "+compid);
			}
			else{
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
			
			
			FutureFlowLoanUploadVO ffpuVO = new FutureFlowLoanUploadVO();
			DynaValidatorForm FutureFlowDynaValidatorFormBean=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(ffpuVO, FutureFlowDynaValidatorFormBean);
			
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2="";
			in.add(ffpuVO.getPoolID());
			in.add(DateFormator.dmyToYMD(ffpuVO.getMonth()));
			in.add(userId);
			out.add(s1);
			out.add(s2);
			logger.info("IN para " +in + "OUT------------"+out);
			try
			{
				logger.info("Before calling SCZ_Future_Flow_Report_Gen proc S1 and S2 ");
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Future_Flow_Report_Gen",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("After calling SCZ_Future_Flow_Report_Gen proc S1 and S2 "+s1+" - "+s2);
			}catch (Exception e) {
				logger.info("An Error..."+e);
			}
			
			Session session1 = HibernateSessionFactory.currentSession();
			Transaction transaction = session1.beginTransaction();
			if(s1.equalsIgnoreCase("S")){
				  Criteria criteria =  session1.createCriteria(FutureFlowReportTempVO.class);
				  criteria.add(Restrictions.and(Restrictions.eq("maker_id", userId), Restrictions.eq("report_Type", "INSTALMENT")));
				  List l = criteria.list();
				  mainList = new ArrayList(l);
			}
			logger.info("list is this ...."+mainList);
			
			HSSFWorkbook workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("INSTALMENT");
	        HSSFRow row1 = firstSheet.createRow(0);
	        
	       
	        
	        row1.createCell(0).setCellValue("Loan Number"); 
	        row1.createCell(1).setCellValue("Reference Number"); 
	        row1.createCell(2).setCellValue("Product"); 
	       	row1.createCell(3).setCellValue("Branche code");
	    	row1.createCell(4).setCellValue("Branche");
	    	row1.createCell(5).setCellValue("Future Cash flow");
	    	row1.createCell(6).setCellValue("tenure");
	    	
	    	String sb=DateFormator.dmyToMonths(ffpuVO.getMonth());
	    	int maxTenure = getMaxTenure(ffpuVO.getPoolID());
	    	int ten;
	    	for(ten=7;ten<=(maxTenure+7);ten++){
	    		row1.createCell(ten).setCellValue(sb.substring(3, 11));
	    		sb=DateFormator.increaseMonth(sb);
	    	}
	    	row1.createCell(ten).setCellValue("FR");
	    	HSSFRow row=null;
			int m=0,i=1;
			
			Iterator it = mainList.iterator();
			while(it.hasNext())
			{
				row= firstSheet.createRow(i);
			Object o = it.next();
			FutureFlowReportTempVO pdt = (FutureFlowReportTempVO)o;
			 	row.createCell(0).setCellValue(CommonFunction.checkNull(pdt.getLoan_no()));
				row.createCell(1).setCellValue(CommonFunction.checkNull(pdt.getReference_No()));
				row.createCell(2).setCellValue(CommonFunction.checkNull(pdt.getProduct()));
				row.createCell(3).setCellValue(CommonFunction.checkNull(pdt.getBranch_Code()));
				row.createCell(4).setCellValue(CommonFunction.checkNull(pdt.getBranch()));
				row.createCell(5).setCellValue(CommonFunction.checkNull(pdt.getFuture_Cash_flow()));
				row.createCell(6).setCellValue(CommonFunction.checkNull(pdt.getTenure()));
				ten=7;
				int no=1;
				for(ten=7;ten<=(maxTenure+7);ten++){
					String strMnt = "getMonth"+no;
					Method method = FutureFlowReportTempVO.class.getMethod(strMnt);
					String strMdVal = String.valueOf(method.invoke(pdt));
					System.out.println("Method : "+strMdVal);
					row.createCell(ten).setCellValue(strMdVal);
					no++;
				}
				row.createCell(ten).setCellValue(CommonFunction.checkNull(pdt.getFr()));
				i++;
				row=null;  
			}
			
			firstSheet = workbook.createSheet("PRINCIPLE");
	        row1 = firstSheet.createRow(0);
			
	        	if(s1.equalsIgnoreCase("S")){
					  Criteria criteria =  session1.createCriteria(FutureFlowReportTempVO.class);
					  criteria.add(Restrictions.and(Restrictions.eq("maker_id", userId), Restrictions.eq("report_Type", "PRINCIPLE")));
					  List l = criteria.list();
					  mainList = new ArrayList(l);
				}
				logger.info("list is this ...."+mainList);
			 
				row1.createCell(0).setCellValue("Loan Number"); 
		        row1.createCell(1).setCellValue("Reference Number"); 
		        row1.createCell(2).setCellValue("Product"); 
		       	row1.createCell(3).setCellValue("Branche code");
		    	row1.createCell(4).setCellValue("Branche");
		    	row1.createCell(5).setCellValue("Future Cash flow");
		    	row1.createCell(6).setCellValue("tenure");
		    	
		    	sb=DateFormator.dmyToMonths(ffpuVO.getMonth());
		    	maxTenure = getMaxTenure(ffpuVO.getPoolID());
		    	for(ten=7;ten<=(maxTenure+7);ten++){
		    		row1.createCell(ten).setCellValue(sb.substring(3, 11));
		    		sb=DateFormator.increaseMonth(sb);
		    	}
		    	row1.createCell(ten).setCellValue("FR");
		    	 row=null;
				 m=0;i=1;
				
				 it = mainList.iterator();
				while(it.hasNext())
				{
					row= firstSheet.createRow(i);
				Object o = it.next();
				FutureFlowReportTempVO pdt = (FutureFlowReportTempVO)o;
				 	row.createCell(0).setCellValue(CommonFunction.checkNull(pdt.getLoan_no()));
					row.createCell(1).setCellValue(CommonFunction.checkNull(pdt.getReference_No()));
					row.createCell(2).setCellValue(CommonFunction.checkNull(pdt.getProduct()));
					row.createCell(3).setCellValue(CommonFunction.checkNull(pdt.getBranch_Code()));
					row.createCell(4).setCellValue(CommonFunction.checkNull(pdt.getBranch()));
					row.createCell(5).setCellValue(CommonFunction.checkNull(pdt.getFuture_Cash_flow()));
					row.createCell(6).setCellValue(CommonFunction.checkNull(pdt.getTenure()));
					ten=7;
					int no=1;
					for(ten=7;ten<=(maxTenure+7);ten++){
						String strMnt = "getMonth"+no;
						Method method = FutureFlowReportTempVO.class.getMethod(strMnt);
						String strMdVal = String.valueOf(method.invoke(pdt));
						System.out.println("Method : "+strMdVal);
						row.createCell(ten).setCellValue(strMdVal);
						no++;
					}
					
					row.createCell(ten).setCellValue(CommonFunction.checkNull(pdt.getFr()));
					i++;
					row=null;  
				}
	        
				
			firstSheet = workbook.createSheet("INTEREST");
	        row1 = firstSheet.createRow(0);
			
				if(s1.equalsIgnoreCase("S")){
					  Criteria criteria =  session1.createCriteria(FutureFlowReportTempVO.class);
					  criteria.add(Restrictions.and(Restrictions.eq("maker_id", userId), Restrictions.eq("report_Type", "INTEREST")));
					  List l = criteria.list();
					  mainList = new ArrayList(l);
				}
				logger.info("list is this ...."+mainList);
			 
				row1.createCell(0).setCellValue("Loan Number"); 
		        row1.createCell(1).setCellValue("Reference Number"); 
		        row1.createCell(2).setCellValue("Product"); 
		       	row1.createCell(3).setCellValue("Branche code");
		    	row1.createCell(4).setCellValue("Branche");
		    	row1.createCell(5).setCellValue("Future Cash flow");
		    	row1.createCell(6).setCellValue("tenure");
		    	
		    	sb=DateFormator.dmyToMonths(ffpuVO.getMonth());
		    	maxTenure = getMaxTenure(ffpuVO.getPoolID());
		    	for(ten=7;ten<=(maxTenure+7);ten++){
		    		row1.createCell(ten).setCellValue(sb.substring(3, 11));
		    		sb=DateFormator.increaseMonth(sb);
		    	}
		    	row1.createCell(ten).setCellValue("FR");
		    	 row=null;
				 m=0;i=1;
				
				 it = mainList.iterator();
				while(it.hasNext())
				{
					row= firstSheet.createRow(i);
				Object o = it.next();
				FutureFlowReportTempVO pdt = (FutureFlowReportTempVO)o;
				 	row.createCell(0).setCellValue(CommonFunction.checkNull(pdt.getLoan_no()));
					row.createCell(1).setCellValue(CommonFunction.checkNull(pdt.getReference_No()));
					row.createCell(2).setCellValue(CommonFunction.checkNull(pdt.getProduct()));
					row.createCell(3).setCellValue(CommonFunction.checkNull(pdt.getBranch_Code()));
					row.createCell(4).setCellValue(CommonFunction.checkNull(pdt.getBranch()));
					row.createCell(5).setCellValue(CommonFunction.checkNull(pdt.getFuture_Cash_flow()));
					row.createCell(6).setCellValue(CommonFunction.checkNull(pdt.getTenure()));
					ten=7;
					int no=1;
					for(ten=7;ten<=(maxTenure+7);ten++){
						String strMnt = "getMonth"+no;
						Method method = FutureFlowReportTempVO.class.getMethod(strMnt);
						String strMdVal = String.valueOf(method.invoke(pdt));
						System.out.println("Method : "+strMdVal);
						row.createCell(ten).setCellValue(strMdVal);
						no++;
					}
					
					row.createCell(ten).setCellValue(CommonFunction.checkNull(pdt.getFr()));
					i++;
					row=null;  
				}
				
				HibernateSessionFactory.closeSession();
	        
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=FutureFlowReport.xls");
	            out1=response.getOutputStream();
	        	workbook.write(out1);
	         } 
	        catch (Exception e) 
	        {  e.printStackTrace();  } 
	        finally
	        {  
	            if (out != null) 
	            {  
	                try 
	                {  
	                	out1.flush();  
	                	out1.close();  
	                } 
	                catch (IOException e) 
	                {  e.printStackTrace();}  
	            }  
	        }
			return null;
	}
	
	
	 public static int getMaxTenure(int poolID){
		 int maxTenure=0;
		 String delQuery = "select max(loan_tenure) from cr_securitization_pool_dtl where pool_id='"+poolID+"'";
		 String a = ConnectionUploadDAO.singleReturn(delQuery);
		// maxTenure=Integer.parseInt(a);
		 return maxTenure;
	 }
	
	// Used for Loan Id Upload....
	/* public ActionForward uploadCSVLoanForReportGeneration(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In FutureFlowBehindAction   ");
		 HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			boolean status=false;			
			boolean uploadStatus=false;	
			String makerID =null;
			String businessDate =null;
			String strFlag=null;
			String flag=null;
			String filePathWithName=null;
			int currentPageLink = 0;
			
			if(userobj!=null){
				makerID = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}else{
				logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
		    ServletContext context = getServlet().getServletContext();
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

			logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
			
			if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
			}
			logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
			 
			FutureFlowLoanUploadVO ffpuVO = new FutureFlowLoanUploadVO();
		   DynaValidatorForm FutureFlowDynaValidatorFormBean=(DynaValidatorForm)form;	
		   org.apache.commons.beanutils.BeanUtils.copyProperties(ffpuVO, FutureFlowDynaValidatorFormBean);

		   FutureFlowLoanUploadDAO dao=new FutureFlowLoanIDUploadHibImplService();
		   
			    ffpuVO.setMakerID(makerID);
			    ffpuVO.setMakerDate(businessDate);
			    ffpuVO.setCurrentPageLink(currentPageLink);
			logger.info("Implementation class: "+dao.getClass());
		   
			if(!CommonFunction.checkNull(ffpuVO.getDocFile()).equalsIgnoreCase(""))
			{	    	
				// Getting the file path....
				uploadStatus=dao.docUploadForExcel(request,(FormFile)ffpuVO.getDocFile());
				
				filePathWithName=request.getAttribute("filePathWithName").toString();
				ffpuVO.setFileName(request.getAttribute("fileName").toString());
				ffpuVO.setDocPath(request.getAttribute("filePath").toString());
				ffpuVO.setFilePathWithName(filePathWithName);
				
				flag=(String)request.getAttribute("message");
				logger.info("flag .. "+flag);
				
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
				    request.setAttribute("sms","");
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
					request.setAttribute("smsno","");

				logger.info("uploadStatus .. "+uploadStatus);
					if(uploadStatus){	
						status=dao.uploadCsv_LoanId(request,response,ffpuVO.getDocFile().getFileName(),ffpuVO);	
						logger.info("status-----------------------"+status);
					}
			}
			request.setAttribute("msg", "Loan Id Upload Saved Successfully");
			return mapping.findForward("success");
	 }*/
}
