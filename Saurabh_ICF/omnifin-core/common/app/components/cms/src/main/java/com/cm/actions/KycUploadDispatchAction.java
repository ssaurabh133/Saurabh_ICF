	package com.cm.actions;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cm.actionform.KycUploadForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.actions.DocUpload;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


	public class KycUploadDispatchAction extends DispatchAction {
		private static final Logger logger = Logger.getLogger(KycUploadDispatchAction.class.getName());
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		
		public ActionForward openKycUploadFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
		{
			ServletContext context = getServlet().getServletContext();
			logger.info("In KycUploadDispatchAction.........");
			HttpSession session = request.getSession();
			boolean flag=false;
			String userId="";
			String branchId="";
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in KycUploadDispatchAction  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}else{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
			}
			Object sessionId = session.getAttribute("sessionID");
			String strFlag="";	
			if(sessionId!=null){
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			String functionId = CommonFunction.checkNull((String)session.getAttribute("functionId"));
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("500000120"))
			request.setAttribute("NACH", "NACH");
			
			logger.info("strFlag .............. "+strFlag);
			if(!strFlag.equalsIgnoreCase(""))
			{
				if(strFlag.equalsIgnoreCase("sameUserSession")){
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}else if(strFlag.equalsIgnoreCase("BODCheck")){
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
			}
		
			return mapping.findForward("openKycFile");
		}
		public ActionForward uploadKycFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
		{
			ServletContext context = getServlet().getServletContext();
			logger.info("In KycUploadDispatchAction.........");
			HttpSession session = request.getSession();
			boolean flag=false;
			String userId="";
			String branchId="";
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here in KycUploadDispatchAction  action the session is out----------------");
				return mapping.findForward("sessionOut");
			}else{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
			}
			Object sessionId = session.getAttribute("sessionID");
			String strFlag="";	
			if(sessionId!=null){
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			logger.info("strFlag .............. "+strFlag);
			if(!strFlag.equalsIgnoreCase(""))
			{
				if(strFlag.equalsIgnoreCase("sameUserSession")){
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}else if(strFlag.equalsIgnoreCase("BODCheck")){
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
			}
			String functionId = CommonFunction.checkNull((String)session.getAttribute("functionId"));
			KycUploadForm  kycUploadForm = (KycUploadForm ) form;
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("500000120")){
				request.setAttribute("NACH", "NACH");
				
				boolean uploadStatus = DocUpload.kycDocUpload(request,kycUploadForm.getDocFile(), userId);
				if(uploadStatus){
					String drivePath=ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'");
					String strPath = drivePath+"\\"+userId+"\\"+kycUploadForm.getDocFile().toString();
					File objFile1  = new File(strPath);
					try{
						ArrayList dataList=new ArrayList();
						dataList=new ArrayList();
						Workbook workbook = Workbook.getWorkbook(objFile1 );
						String[] sheetName=workbook.getSheetNames();
						Sheet sheet;
						Cell xlsCell;
						Cell[] cell;
						
						StringBuffer qry=new StringBuffer();
						
						sheet = workbook.getSheet(0);
						
						int maxRow=sheet.getRows();
						int maxCol=sheet.getColumns();
						String loanNumber = "";
						String UMRN = "";
						String Status = "";
						String finalResult = "";
						String finalResult1 = "";
						for(int j=1;j<maxRow;j++){
							xlsCell=sheet.getCell(1,j);
							loanNumber=CommonFunction.checkNull(xlsCell.getContents());
							
							xlsCell=sheet.getCell(16,j);
							Status=CommonFunction.checkNull(xlsCell.getContents());
							
							xlsCell=sheet.getCell(18,j);
							UMRN=CommonFunction.checkNull(xlsCell.getContents());
							
							boolean status = false;
							boolean status1 = false;
							String trackID=ConnectionDAO.singleReturn("select ACH_CAPTURING_ID from cr_ach_capturing_dtl WHERE LOAN_ID=(SELECT LOAN_ID FROM CR_LOAN_DTL WHERE LOAN_NO ='"+loanNumber+"')");
							String mTrackID=ConnectionDAO.singleReturn("select MAX(ACH_TRACKING_ID) from cr_ach_tracking_dtl WHERE ACH_CAPTURING_ID='"+trackID+"'");
							StringBuilder queryUpdate=new StringBuilder();
							ArrayList qryList=new ArrayList();
							queryUpdate.append("UPDATE cr_ach_capturing_dtl SET STATUS=?, UMRN=? WHERE LOAN_ID = (SELECT LOAN_ID FROM CR_LOAN_DTL WHERE LOAN_NO = ?)");
							//queryUpdate.append("UPDATE cr_ach_tracking_dtl SET STATUS=?, UMRN=? WHERE LOAN_ID = (SELECT LOAN_ID FROM CR_LOAN_DTL WHERE LOAN_NO = ?) AND ACH_TRACKING_ID='"+mTrackID+"'");
							PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
							
							if((CommonFunction.checkNull(Status)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((Status).trim());
							
							if((CommonFunction.checkNull(UMRN)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((UMRN).trim());
							
							if((CommonFunction.checkNull(loanNumber)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((loanNumber).trim());
							
							insertPrepStmtObject.setSql(queryUpdate.toString());
							qryList.add(insertPrepStmtObject);
							logger.info("insertPrepStmtObject-->"+insertPrepStmtObject.toString());
							queryUpdate=null;
							insertPrepStmtObject=null;
							
							try
							{
								status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
								logger.info("status-->"+status);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							if(status)
								finalResult = finalResult+"S,";
							else
								finalResult = finalResult+"F,";
							
							qryList = null;
						//}	
			         if(!CommonFunction.checkNull(finalResult).contains("F"))
			        	 request.setAttribute("sms", "upload");
			         else
			        	 request.setAttribute("sms", "Failed");
			       //pooja
						StringBuilder queryUpdate1=new StringBuilder();
						ArrayList qryList1=new ArrayList();
						//queryUpdate1.append("UPDATE cr_ach_capturing_dtl SET STATUS=?, UMRN=? WHERE LOAN_ID = (SELECT LOAN_ID FROM CR_LOAN_DTL WHERE LOAN_NO = ?)");
						queryUpdate1.append("UPDATE cr_ach_tracking_dtl SET STATUS=?, UMRN_NO=? WHERE  ACH_TRACKING_ID='"+mTrackID+"'");
						PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
						
						if((CommonFunction.checkNull(Status)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((Status).trim());
						
						if((CommonFunction.checkNull(UMRN)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((UMRN).trim());
						/*
						if((CommonFunction.checkNull(loanNumber)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((loanNumber).trim());*/
						
						insertPrepStmtObject1.setSql(queryUpdate1.toString());
						qryList1.add(insertPrepStmtObject1);
						logger.info("insertPrepStmtObject1-->"+insertPrepStmtObject1.toString());
						queryUpdate1=null;
						insertPrepStmtObject1=null;
						
						try
						{
							status1 =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
							logger.info("status-->"+status1);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						if(status1)
							finalResult1 = finalResult1+"S,";
						else
							finalResult1 = finalResult1+"F,";
						
						qryList1 = null;
			         if(!CommonFunction.checkNull(finalResult1).contains("F"))
			        	 request.setAttribute("sms", "upload");
			         else
			        	 request.setAttribute("sms", "Failed");
						//pooja
						}
					}catch(IOException e)
		              {
		                  e.printStackTrace();
		              }
				}
			}else{
				boolean uploadStatus = DocUpload.kycDocUpload(request,kycUploadForm.getDocFile(), userId);
				if(uploadStatus){
					String drivePath=ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'");
					String strPath = drivePath+"\\"+userId+"\\"+kycUploadForm.getDocFile().toString();
					File objFile1  = new File(strPath);
					try{
						ArrayList dataList=new ArrayList();
						dataList=new ArrayList();
						Workbook workbook = Workbook.getWorkbook(objFile1 );
						String[] sheetName=workbook.getSheetNames();
						Sheet sheet;
						Cell xlsCell;
						Cell[] cell;
						
						StringBuffer qry=new StringBuffer();
						
						sheet = workbook.getSheet(0);
						
						int maxRow=sheet.getRows();
						int maxCol=sheet.getColumns();
						String customerId = "";
						String kycNumber = "";
						String finalResult = "";
						for(int j=1;j<maxRow;j++){
							xlsCell=sheet.getCell(1,j);
							customerId=CommonFunction.checkNull(xlsCell.getContents());
							
							xlsCell=sheet.getCell(85,j);
							kycNumber=CommonFunction.checkNull(xlsCell.getContents());
							
							boolean status = false; 
							StringBuilder queryUpdate=new StringBuilder();
							ArrayList qryList=new ArrayList();
							queryUpdate.append("UPDATE CR_DEAL_CUSTOMER_M SET CKYC=? WHERE CUSTOMER_ID =?");
							PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
							
							if((CommonFunction.checkNull(kycNumber)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((kycNumber).trim());
							
							if((CommonFunction.checkNull(customerId)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((customerId).trim());
							
							insertPrepStmtObject.setSql(queryUpdate.toString());
							qryList.add(insertPrepStmtObject);
							logger.info("insertPrepStmtObject-->"+insertPrepStmtObject.toString());
							queryUpdate=null;
							insertPrepStmtObject=null;
							
							try
							{
								status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
								logger.info("status-->"+status);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							if(status)
								finalResult = finalResult+"S,";
							else
								finalResult = finalResult+"F,";
							
							qryList = null;
						}	
			         if(!CommonFunction.checkNull(finalResult).contains("F"))
			        	 request.setAttribute("sms", "upload");
			         else
			        	 request.setAttribute("sms", "Failed");
					}catch(IOException e)
		              {
		                  e.printStackTrace();
		              }
				}
		
			}
			
		return mapping.findForward("openKycFile");
		}
	}
			
			
			
			