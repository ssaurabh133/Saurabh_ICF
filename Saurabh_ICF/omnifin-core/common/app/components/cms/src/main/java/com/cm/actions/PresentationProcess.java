package com.cm.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actionform.CMUploadForm;
import com.cm.dao.PresentationProcessDAO;
import com.cm.vo.PresentationProcessVO;

public class PresentationProcess  extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PresentationProcess.class.getName());	

	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	 
	 String dbType=resource.getString("lbl.dbType");
	
public ActionForward generateReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
{
		    logger.info("In generateReport()..Action. ");
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String p_printed_by="";
			String p_user_id="";
			String p_company_name="";
			String rptPath="";
			if(userobj!=null)
			{
				p_user_id=userobj.getUserId();
				p_company_name=userobj.getConpanyName();
				p_printed_by=userobj.getUserName();
			}else{
				logger.info("here in generateReport method of  PresentationProcess   action the session is out----------------");
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

				if(dbType.equalsIgnoreCase("MSSQL")){
					rptPath=rptPath+"MSSQLREPORTS/";
				}else{
					rptPath=rptPath+"MYSQLREPORTS/";
				}
				
			String p_company_logo="";
			
			String batch_id="";
			
			p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";

			PresentationProcessDAO dao=(PresentationProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(PresentationProcessDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			
			batch_id=dao.getMaxBatchId(p_user_id);
			
			Connection connectDatabase = ConnectionDAO.getConnection();		
			HashMap hashMap=new HashMap();
			
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_company_logo",p_company_logo);
			hashMap.put("p_user_id",p_user_id);
			hashMap.put("batch_id",batch_id);
			hashMap.put("p_printed_by",p_printed_by);
			
			logger.info("batch_id..."+batch_id);
			
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream("/reports/"+rptPath+"/presentaion_process_error_log.jasper");
			JasperPrint jasperPrint = null;
			try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					String excelReportFileName="presentaion_process_error_log.xls";		
					JExcelApiExporter exporterXLS = new JExcelApiExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
					response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
					response.setContentType("application/vnd.ms-excel");
					exporterXLS.exportReport();
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}	
				finally
				{
					rptPath=null;
					connectDatabase.close();
				}		
			return null;
		}
    
public ActionForward uploadFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
{
	logger.info("Inside uploadFile");
	HttpSession session = request.getSession();

	UserObject userobj=(UserObject)session.getAttribute("userobject");
	boolean uploadStatus=false;	
    boolean flag=false;
    int compid =0;
    String msg= null;		

	String branchId="";
	String businessDate="";
	String userId ="";
	
	if(userobj==null){
		logger.info("in uploadfile method of  PresentationProcess action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	Object sessionId = session.getAttribute("sessionID");
	//for check User session start
	ServletContext context = getServlet().getServletContext();
	String strFlag="";	
	if(sessionId!=null )
		strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
	
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
	
	session.removeAttribute("Processfile");

	
	logger.info(".. pass 01 ...");
    CMUploadForm excelForm = (CMUploadForm) form;
    
    logger.info(".. pass 02 ...");
    
    if(userobj!=null){
    	logger.info(".. pass 03 ...");
		businessDate=userobj.getBusinessdate();
		compid=userobj.getCompanyId();
		branchId=userobj.getBranchId();
		userId = userobj.getUserId();
	}
    logger.info(".. pass 04 ...");
	excelForm.setCompanyId(compid);
	excelForm.setMakerId(userId);
	excelForm.setBranchId(branchId);
	excelForm.setBusinessDate(businessDate);
	excelForm.setSessionId(sessionId.toString());
	
	logger.info(".. pass 05 ...");
    
    
		uploadStatus=docUploadForExcel(request,(FormFile)excelForm.getDocFile());
		String filePathWithName=request.getAttribute("filePathWithName").toString();
		logger.info(".. pass 06 ...");
		
		if(uploadStatus){			
			 excelForm.setFilePathWithName(filePathWithName);
			  flag=uploadCsv_PresentationProcess( request,response,excelForm.getDocFile().getFileName(),excelForm);	
			  logger.info("flag-----------------------"+flag);
		}
		
		if(!flag){
			  request.setAttribute("fieldUpdate", "Some problem in sheet format..");
		}else{
						
			 
			 msg=savePresentationProcess(request,excelForm);

			 logger.info("msg after save in action:--- "+msg);
			 
			 
			  if(CommonFunction.checkNull(msg).equalsIgnoreCase("S")){
				  request.setAttribute("errorMsg", "Data Upload Sucessfully.");
			  }else{
				  request.setAttribute("errorMsg", "Some problem in the Sheet.");
			  }
		}	 
		
		if("S".equalsIgnoreCase(msg))
		{
			 request.setAttribute("sms","");
		}else{
			 request.setAttribute("smsno","");
		}		  
	 
	excelForm.reset(mapping, request);
		
	return mapping.findForward("success");
}

	public ActionForward generateProcess(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In generateProcess() method....");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("in generateProcess method of  PresentationProcess action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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

		int companyId=0;
		if(userobj != null)
		  companyId=userobj.getCompanyId();
		DynaValidatorForm PresentationProcessDynaValidatorForm = (DynaValidatorForm)form;
		PresentationProcessVO presentationProcessVO=new PresentationProcessVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(presentationProcessVO, PresentationProcessDynaValidatorForm);
		presentationProcessVO.setCompanyID(companyId);
		String file=(String)session.getAttribute("Processfile");
		logger.info("startProcess() : file:==>>"+file);
		int count=UploadDocuments.countLine(file);
		logger.info("In startProcess(): count"+count);
		String result="";
		if(count>1000)
			 request.setAttribute("maxCount","");	
		if(count==0)
			 request.setAttribute("zeroCount","");	
		else
		{
			result=UploadDocuments.readExcelforPresentationUpload(request, response,file,presentationProcessVO);
			logger.info("startProcess():result:==>> "+result);
			if(result.trim().length() != 0)
			{
				String msg="";
				String[] temp;
				String delimiter = ":";
				temp = result.split(delimiter);
				int rej=Integer.parseInt(temp[0]);
				int up=Integer.parseInt(temp[1]);
				if(rej==0 && up != 0)
					msg="Presentation batch generated for "+up+" record.";
				if(up==0 && rej != 0)
					msg="Check Error Log.";
				if(rej !=0 && up != 0)
					msg="Partial upload Presentation batch generated for "+up+" records. Check Error Log";
				logger.info("No. of rejected record :  "+rej);
				logger.info("No. of uploaded record :  "+up);
				logger.info("msg "+msg);
				request.setAttribute("msg",msg);			
				request.setAttribute("inserted", "Done");
			}
			else
				request.setAttribute("noinserted", "Done");
			
		}
		session.removeAttribute("Processfile");
		return mapping.findForward("success");
	}


	public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile) {
		boolean status=false;

		String fileName="";
		String filePath="";
		String message="";
		String filePathWithName="";
		String fileNameChange="";
		String user_Id="";

		HttpSession session=request.getSession(false);
		UserObject sessUser = (UserObject) session.getAttribute("userobject");
		SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
		try{
			if(sessUser!=null){
				user_Id = sessUser.getUserId();
			}
			String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
			String rpt=ConnectionDAO.singleReturn(query);
			query=null;
			File directory = new File(rpt+"/");
			boolean isDirectory = directory.isDirectory();

			Date dNow = new Date( );
			
			
			fileNameChange=fileNameFormat.format(dNow);
			if(isDirectory){
				fileName    = docFile.getFileName();
				logger.info("fileName is --->"+fileName);
				fileName=user_Id.concat("_"+fileNameChange.concat(fileName));
				logger.info("changed fileName is --->"+fileName);	
				filePath = directory.getAbsolutePath();
				filePathWithName=filePath.concat("\\").concat(fileName);
					if(!fileName.equals("")){  
							logger.info("Server path: filePath:==>>" +filePath);
							//Create file
							File fileToCreate = new File(filePath, fileName);
							int fileSize = docFile.getFileSize(); //26314400 bytes = 25 MB
							logger.info("docUpload :Size of file==>> "+fileSize);

							if(fileSize<26314400){
									FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
									fileOutStream.write(docFile.getFileData());
									fileOutStream.flush();
									fileOutStream.close();
									message="O";
									
									status=true;	        		
								}else{
									message="E";
									logger.info("File size exceeds the upper limit of 25 MB.");
									status=false;
								}
					}else{
						message="S";
						//logger.info("Please select a File.");
			        	status=false;
					}
			}else{
				message="E";
				logger.info("File Directory is empty");
	        	status=false;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message",message);
		request.setAttribute("filePathWithName", filePathWithName);
		
		fileName=null;
		filePath=null;
		message=null;
		filePathWithName=null;
		fileNameChange=null;
		
		return status;		
	}


	public boolean uploadCsv_PresentationProcess(HttpServletRequest request,HttpServletResponse response,String strFileName,CMUploadForm  excelForm)
	{
		   ArrayList  alDeleteQuery  = new ArrayList(1);
		   String fileNameWithPath=excelForm.getFilePathWithName();

		   boolean status=false;
		   try {
			   
			   String query1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
				String rpt1=ConnectionUploadDAO.singleReturn(query1);
				query1=null;
				
			   	String strDeleteQuery = "DELETE FROM PRESENTATION_PROCESS_UPLOAD_TEMP WHERE MAKER_ID = '"+CommonFunction.checkNull(excelForm.getMakerId())+"' ";
			   	alDeleteQuery.add(strDeleteQuery);
			   	boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
				   	
		     	  if(status1) {
		     		  logger.info("IN uploadCsv_PresentationProcess() Row is deleted....");
		     	  }else{
		     		  logger.info("In uploadCsv_PresentationProcess() Row is not deleted......");
		     	  }
		     		     	  
		     	  KettleEnvironment.init(false); 
		     	  EnvUtil.environmentInit(); 
		     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\presentation_process_upload.ktr")); 
		     	  Trans trans = new Trans(transMeta); 

		     	// set parameter. same can be used inside steps in transformation 
			     	 trans.setParameterValue("filePathWithName",fileNameWithPath);
			     	 trans.setParameterValue("userID",CommonFunction.checkNull(excelForm.getMakerId()));
			     	 
		     	  trans.execute(null); // You can pass arguments instead of null. 
		     	  trans.waitUntilFinished(); 
		     	 
		     	  if (trans.getErrors() > 0 ){ 
		     		  status=false;
		     		  throw new RuntimeException( "There were errors during transformation execution."+trans.getErrors() ); 
		     	  } else{
		     		 status=true;
		     	  }
			   
		     	  	transMeta.clearCaches();
			     	transMeta.clear();
			     	rpt1=null;
			     	trans.clearParameters();
			     	trans.cleanup();
			     	strDeleteQuery=null;
		     	
	      } 
	      catch (Exception e) {
	    	  logger.info("In uploadCsv_PresentationProcess() Problem is ---->"+e.getMessage());
		} 

		return status;
	}
	
	public String savePresentationProcess(HttpServletRequest request,CMUploadForm excelForm) {
		   String flag=null;
		   int batchID = 0;
		 
		   String businessDate=CommonFunction.checkNull(excelForm.getBusinessDate());
		   String dueDate=CommonFunction.checkNull(excelForm.getPresentationDate());

		   batchID=Integer.parseInt(ConnectionDAO.singleReturn("select VALUE from generate_sequence_tbl where SEQ_KEY='PRESENTATION_FLAG'"));
		   
		   logger.info("In savePresentationProcess () businessDate  :  "+businessDate);
		   logger.info("In savePresentationProcess () dueDate  :  "+dueDate);

		   
		try { 	 
			ArrayList<Object> in =new ArrayList<Object>();
	 		ArrayList<Object> out =new ArrayList<Object>();
	 		ArrayList outMessages = new ArrayList();
	 		String s1 = "";
	 		String s2 = ""; 	
	 		int noOfRejectedRecord=0;
	 		int noOfUploadedRecord=0;
	 		businessDate=CommonFunction.changeFormat(CommonFunction.checkNull(businessDate).trim());
            dueDate= CommonFunction.changeFormat(CommonFunction.checkNull(dueDate).trim());
			
		   logger.info("In savePresentationProcess () changeFormat  :  "+dueDate);
			
			in.add(CommonFunction.checkNull(excelForm.getCompanyId()));
			in.add(businessDate);
			in.add(dueDate);
			in.add(CommonFunction.checkNull(excelForm.getLbxBankID()));
			in.add(CommonFunction.checkNull(excelForm.getLbxBranchID()));
			in.add(CommonFunction.checkNull(excelForm.getBankAccount()));
			in.add(CommonFunction.checkNull(excelForm.getMakerId()));
			in.add(batchID);

			out.add(noOfRejectedRecord);
			out.add(noOfUploadedRecord);
			out.add(s1);
		    out.add(s2);
			
		    logger.info("PRESENTATION_PROCESS_BULK_UPLOAD("+in.toString()+","+out.toString()+")");  
		    
	        outMessages=(ArrayList) ConnectionDAO.callSP("PRESENTATION_PROCESS_BULK_UPLOAD",in,out); 
	        logger.info("=== outMessages == "+outMessages);  
	        noOfRejectedRecord =Integer.parseInt((String)outMessages.get(0));
	 	    noOfUploadedRecord=Integer.parseInt((String)outMessages.get(1));
	        s1 = (String)outMessages.get(2);
	 	    s2 = (String)outMessages.get(3);
	 	    flag = s1;


	        logger.info("In savePresentationProcess() flag:----"+s1);
			logger.info("In savePresentationProcess() s2:----"+s2);
			 
			 request.setAttribute("errorMsg", s2);		

			 in.clear();
			 in=null;
			 out.clear();
			 out=null;      
			 s2=null;
			
			 outMessages.clear();
			 outMessages=null;

		} catch (SQLException exp){

			exp.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			   businessDate=null;
			   dueDate=null;
		}
		return flag;
	}
	
}
