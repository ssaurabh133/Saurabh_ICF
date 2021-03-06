/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.dao.DisbursalInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.RepayScheduleVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;





/** 
 * MyEclipse Struts
 * Creation date: 05-20-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RepaymentScheduleBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(RepaymentScheduleBehindAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	StringBuffer bufInsUpdSql = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	String dateFormat=resource.getString("lbl.dateInDao");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session = request.getSession();
		//boolean flag=false;

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId=null;
		//String bgDate=null;
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			//bgDate=userobj.getBusinessdate();  
		}else{
			logger.info(" in execute method of RepaymentScheduleBehindAction action the session is out----------------");
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
		//code added by neeraj 
		
		
		String functionId=(String)session.getAttribute("functionId");
		String facilityFlag = CommonFunction.checkNull((String)request.getParameter("facilityFlag"));
		String dealLoanId = CommonFunction.checkNull((String)request.getParameter("dealLoanId"));
		
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123|| id==4000154||id==4000153||id==4001231||id==4001240||id==4000154)
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
		}
		
		if(id==3000951||id==3000209||id==3000294||id==3000296||id==3000297||id==3000298||id==3000953||id==3000953||id==3000954||id==4000154)
		{
			session.setAttribute("underWriter","underWriter");
			
		}
		
    	//neeraj space end 
		//DisbursalInitiationDAO detail = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO detail=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+detail.getClass()); 
		String dealId = "";
		String dealIdFrom="";
		if (session.getAttribute("dealId") != null)
		{
			dealId = session.getAttribute("dealId").toString();
			dealIdFrom="dealId";
		}
		else if (session.getAttribute("maxId") != null)
		{ 
			dealId = session.getAttribute("maxId").toString();
			dealIdFrom="maxId";
		}
		
		// Start Implementation for Multiple Product Concept --> Prashant Kumar
		
		
		if (!CommonFunction.checkNull((String)session.getAttribute("dealLoanId")).equalsIgnoreCase("") && !"Y".equalsIgnoreCase(facilityFlag)) {
			dealLoanId = session.getAttribute("dealLoanId").toString();
			facilityFlag = session.getAttribute("facilityFlag").toString();
		} else if ("Y".equalsIgnoreCase(facilityFlag)) {
			session.removeAttribute("dealLoanId");   
			session.removeAttribute("facilityFlag");	
			dealLoanId = request.getParameter("dealLoanId");
			session.setAttribute("dealLoanId",dealLoanId);				
			session.setAttribute("facilityFlag",facilityFlag);
		}

		logger.info("In RepaymentScheduleBehindAction dealId: "+dealId +" ::dealLoanId:: "+dealLoanId+" ::facilityFlag:: "+facilityFlag);
	// End Implementation for Multiple Product Concept--> Prashant Kumar 
		
		logger.info("In RepaymentScheduleBehindAction(execute) dealId: " + dealId);
		String loanId = "";
		if(session.getAttribute("loanId")!=null)
			loanId=session.getAttribute("loanId").toString();
		else if(session.getAttribute("maxIdInCM")!=null)
			loanId=session.getAttribute("maxIdInCM").toString();
		else if(request.getParameter("loanId")!=null)
			loanId=request.getParameter("loanId");
		
		logger.info("In RepaymentScheduleBehindAction loan id: " +loanId);
		if((loanId!=null && !loanId.equalsIgnoreCase("")))
		{
						
			String repayQ="select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID="+loanId;
		    logger.info("Repayment Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
			logger.info("Repayment Type:"+repayType);			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
				if(id==4000106||id==4000116||id==4000806||id==4000816||id==4000826||id==4000835||id==4000870)
				{
					
					logger.info("Repayment Type: b4 proc generateRepySchedule:");
					String resultproc=detail.generateRepySchedule(loanId,userId);
					logger.info("generateRepySchedule procedure status: "+resultproc);
					if(!CommonFunction.checkNull(resultproc).equalsIgnoreCase("S"))
					{					
						request.setAttribute("procval", resultproc);
					}
					else
					{
						ArrayList fromloanDtl=detail.getFromLoanDtl(loanId);
						ArrayList repShedule=detail.getRepaySched(loanId);
						request.setAttribute("fromloanDtl", fromloanDtl);
					//	logger.info("fromloanDtl In else:   "+fromloanDtl.size());
						request.setAttribute("repShedule", repShedule);
						//logger.info("repShedule In else :   "+repShedule.size());
						RefreshFlagVo vo1 = new RefreshFlagVo();
						logger.info("loanId:  "+loanId);
						if(!loanId.trim().equalsIgnoreCase(""))
						vo1.setRecordId(Integer.parseInt(loanId.trim()));
			    		vo1.setTabIndex(6);
			    		vo1.setModuleName("CM");
			    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
					}
					
					
				}
				else
				{	
				//	logger.info("Repayment Type:in IF");
					ArrayList fromloanDtl=detail.getFromLoanDtl(loanId);
					ArrayList repShedule=detail.getRepaySched(loanId);
					request.setAttribute("fromloanDtl", fromloanDtl);
				//	logger.info("fromloanDtl:   "+fromloanDtl.size());
					request.setAttribute("repShedule", repShedule);
				//	logger.info("repShedule:   "+repShedule.size());
				}
				session.setAttribute("repaymentCheck","Y");
				return mapping.findForward("success");
			}
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
				return mapping.findForward("backNonProductInLoan");
			}
			}
			
		}
		else if(loanId.equalsIgnoreCase("") && dealId.equalsIgnoreCase(""))
		{
			 request.setAttribute("back", "back");
			 if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
			 return mapping.findForward("backSuccess");
		}
		}
		
		if((dealId!=null && !dealId.equalsIgnoreCase("")))
		{
			String repayQ="";
			String resultproc="";
			ArrayList fromloanDtl=new ArrayList();
			ArrayList repShedule=new ArrayList();
			if ("Y".equalsIgnoreCase(facilityFlag)) {
				 repayQ="select DEAL_REPAYMENT_TYPE from CR_DEAL_FACILITY_DTL where DEAL_ID="+dealId+" AND DEAL_LOAN_ID = "+dealLoanId;
				}else{
				 repayQ="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID="+dealId;
				}
			
			logger.info("Repayment In deal Query: "+repayQ);
		    String repayType=ConnectionDAO.singleReturn(repayQ);
		//	logger.info("Repayment Type:"+repayType);			
			if(repayType!=null && repayType.equalsIgnoreCase("I"))
			{
				CreditProcessingDAO detail1=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		        logger.info("Implementation class: "+detail1.getClass()); 		// changed by asesh
		     	//CreditProcessingDAO detail1 = new CreditProcessingDAOImpl();
		        
		        if(id==3000206||id==3000217||id==500000123 || (id==3000297 && "Y".equalsIgnoreCase(facilityFlag)) || (id==3000296 && "Y".equalsIgnoreCase(facilityFlag)))
				{
		        	logger.info("Repayment Type: b4 proc generateRepySchedule:");
		        	if ("Y".equalsIgnoreCase(facilityFlag))
					{
						String refFlag = getFacilityFlag(dealLoanId, dealId, "3");
						resultproc = generateRepyScheduleInFacility(dealLoanId, userId);
				  } else {
		     		 resultproc=	detail1.generateRepyScheduleInDeal(dealId,userId);
				  }
					logger.info("generateRepyScheduleInDeal procedure status: "+resultproc);
					if(!CommonFunction.checkNull(resultproc).equalsIgnoreCase("S"))
					{
						request.setAttribute("resultproc", resultproc);
					}
					else{
						 
					if ("Y".equalsIgnoreCase(facilityFlag)) {
						 fromloanDtl=getFromLoanDtlInFacility(dealLoanId);
						 repShedule=getRepaySchedInFacility(dealLoanId);
					}else{
						fromloanDtl=detail1.getFromLoanDtlInDeal(dealId);
						 repShedule=detail1.getRepaySchedInDeal(dealId);
					}
							request.setAttribute("fromloanDtl", fromloanDtl);
							logger.info("fromloanDtl:   "+fromloanDtl.size());
							request.setAttribute("repShedule", repShedule);
							logger.info("repShedule:   "+repShedule.size());
							RefreshFlagVo vo1 = new RefreshFlagVo();
							if(!dealId.trim().equalsIgnoreCase(""))
							vo1.setRecordId(Integer.parseInt(dealId.trim()));
				    		vo1.setTabIndex(9);
				    		vo1.setModuleName("CP");
				    		if(!"Y".equalsIgnoreCase(facilityFlag)){
				    			 RefreshFlagValueInsert.updateRefreshFlag(vo1);
				    		}
			    		    request.setAttribute("dealId",dealId);
			    		    
					}
		    		
		     		
				}
		     	else
		     	{
		     		if ("Y".equalsIgnoreCase(facilityFlag)) {
			     		 fromloanDtl=getFromLoanDtlInFacility(dealLoanId);
						 repShedule=getRepaySchedInFacility(dealLoanId);
			     		}else{ 
			     			fromloanDtl=detail1.getFromLoanDtlInDeal(dealId);
			     			repShedule=detail1.getRepaySchedInDeal(dealId);
			     		}
					request.setAttribute("fromloanDtl", fromloanDtl);
					logger.info("fromloanDtl:   "+fromloanDtl.size());
					request.setAttribute("repShedule", repShedule);
					logger.info("repShedule:   "+repShedule.size());
		     		
				}
		     	session.setAttribute("repaymentCheck","Y");
			return mapping.findForward("success");
			
			}
			else
			{
				request.setAttribute("nonProduct", "nonProduct");
				if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
				return mapping.findForward("backNonProductInDeal");
			}
		}
		}
		else
		{
			 request.setAttribute("back", "back");
			 if("Y".equalsIgnoreCase(facilityFlag)){
					return mapping.findForward("success");
				}else{
			 return mapping.findForward("backDealSuccess");
		}
	}
	}
	
	public String getFacilityFlag(String dealLoanId, String dealId,String position) {
		String flag="";
		boolean Status=false;
		ArrayList list= new ArrayList();
		try{
		String query="select REFRESH_FLAG_FACILITY from cr_deal_facility_dtl where deal_id='"+dealId+"' and Deal_loan_ID='"+dealLoanId+"' ";
		String res=ConnectionDAO.singleReturn(query);
		
		if(CommonFunction.checkNull(res).equalsIgnoreCase("YYY")){
			flag="YYN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("NYY")){
			flag="NYN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("NNY")){
			flag="NNN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("NNN")){
			flag="NNN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("YNN")){
			flag="YNN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("YYN")){
			flag="YYN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("YNY")){
			flag="YNN";
		}
		if(CommonFunction.checkNull(res).equalsIgnoreCase("NYN")){
			flag="NYN";
		}
		
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("")){
			flag="NNN";
		}
	String  updateQuery="update CR_DEAL_FACILITY_DTL set REFRESH_FLAG_FACILITY='"+flag+"' where deal_id='"+dealId+"' and Deal_loan_ID='"+dealLoanId+"' ";
	list.add(updateQuery);
	logger.info("Refresh Flag Facility ::::: "+flag);
	logger.info("getFacilityFlag Refresh Flag Facility ::::: "+updateQuery);
	 Status=ConnectionDAO.sqlInsUpdDelete(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	return flag;
	}		
	public String generateRepyScheduleInFacility(String dealLoanId,String makerId) {
		boolean status=false;
		CallableStatement cst=null;
		Connection con=ConnectionDAO.getConnection();
		logger.info("dealLoanId id: "+dealLoanId);
		String procval="Noresult";
		try {
			//StringBuilder productQuery=new StringBuilder();
			// productQuery.append("select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			//logger.info("productQuery: "+productQuery);
		//	ArrayList productList=ConnectionDAO.sqlSelect(productQuery.toString());
			
			//productQuery=null;
			// for(int i=0;i<productList.size();i++)
			 // {
				//  ArrayList subproductList=(ArrayList)productList.get(i);
				 // if(subproductList.size()>0)
				 // {
					
					con.setAutoCommit(false);
					cst=con.prepareCall("call Generate_Repayment_schedule(?,?,?,?)");
					cst.setString(1, "FDC");
					cst.setString(2, dealLoanId);
					cst.registerOutParameter(3, Types.CHAR);
					cst.registerOutParameter(4, Types.CHAR);
					cst.executeUpdate();
					String s1= cst.getString(3);
					String s2 = cst.getString(4);
					logger.info("Output:"+s1+" And "+s2);
					if(s1!=null && s1.equalsIgnoreCase("S"))
					{
						status=true;
						con.commit();
						procval=s1;
					}
					else
					{
						procval=s2;
						con.rollback();
					}
					logger.info("status: "+status);
					logger.info("s2: "+s2);
				 // }
			 // }
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.info("Roll back in generateRepySchedule: "+e1);
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			try {
				con.commit();
				cst=null;
				con.close();
				dealLoanId=null;
				makerId=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return procval;
	}
	public ArrayList getFromLoanDtlInFacility(String dealLoanId) {
		ArrayList list = new ArrayList();

		logger.info("In getFromLoanDtlInDeal: dealLoanId "+dealLoanId);
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();

		try {
			StringBuilder query =new StringBuilder();
			//	Himanshu Verma		Adding NPV
			 query.append("select DEAL_RATE_METHOD,DEAL_FLAT_RATE,DEAL_EFF_RATE,DEAL_IRR1,DEAL_IRR2, ifnull(LOAN_NPV,'') " +
					" from CR_DEAL_FACILITY_DTL where DEAL_LOAN_ID="+dealLoanId);
			logger.info("Query in getFromLoanDtlInDeal-----" + query);
			StringBuilder bussIrrQ =new StringBuilder();
			 bussIrrQ.append("select DEAL_BUSINESS_IRR from CR_DEAL_FACILITY_DTL where DEAL_LOAN_ID='"+dealLoanId+"' ");
			logger.info("Query in getFromLoanDtlInDeal--DEAL_BUSINESS_IRR---" + bussIrrQ);
			String bussIrr=ConnectionDAO.singleReturn(bussIrrQ.toString());
			logger.info("Query in getFromLoanDtlInDeal--bussIrr---" + bussIrr);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			bussIrrQ=null;

			for (int i = 0; i < mainlist.size(); i++) {
				
				subList = (ArrayList) mainlist.get(i);
				RepayScheduleVo repvo = new RepayScheduleVo();
				if (subList.size()> 0) {
					
                    
					repvo.setLoanRateMethod((CommonFunction.checkNull(subList.get(0)).trim()));
					if(!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());  
	    	    		repvo.setFinalRate(myFormatter.format(reconNum));
    	    		}
					//repvo.setFinalRate((CommonFunction.checkNull(subList.get(1)).trim()));
					//logger.info("Final Rate: "+CommonFunction.checkNull(subList.get(1)));
					if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
	    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
    	    		}
					//repvo.setEffectiveRate((CommonFunction.checkNull(subList.get(2)).trim()));
					if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
	    	    		repvo.setMktIRR1(myFormatter.format(reconNum));
    	    		}
					//repvo.setMktIRR1((CommonFunction.checkNull(subList.get(3)).trim()));
					if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
	    	    		repvo.setMktIRR2(myFormatter.format(reconNum));
    	    		}
					//repvo.setMktIRR2((CommonFunction.checkNull(subList.get(4)).trim()));
					if((CommonFunction.checkNull(subList.get(0)).trim()).equalsIgnoreCase("E"))
                    {
						repvo.setFinalRate("");
                    }
					repvo.setBussIrr(bussIrr);

					//	Himanshu Verma		Adding NPV
					repvo.setTxtNpv(CommonFunction.checkNull(subList.get(5)).trim());
				}
				list.add(repvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			dealLoanId=null;
		}

		return list;
	}
	public ArrayList getRepaySchedInFacility(String dealLoanId) {
		ArrayList list = new ArrayList();

		logger.info("In getRepaySched:dealLoanId "+dealLoanId);
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();

		try {

			StringBuilder query =new StringBuilder();
			 query.append("select INSTL_NO,DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT," +
					" if(ADV_FLAG='Y','YES','NO'),PRIN_OS,OTHER_CHARGES from CR_DEAL_FACILITY_REPAYSCH_DTL where DEAL_LOAN_ID="+dealLoanId+" ORDER BY INSTL_NO ASC ");//Rohit changes deal_id to Deal_loan_id
			
			logger.info("Query in getRepaySched-----" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;

			for (int i = 0; i < mainlist.size(); i++) {
				subList = (ArrayList) mainlist.get(i);
				RepayScheduleVo repvo = new RepayScheduleVo();
				if (subList.size() > 0) {

					
					repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
					repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
					
					if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
	    	    		repvo.setInstAmount(myFormatter.format(reconNum));
    	    		}
					
					//repvo.setInstAmount((CommonFunction.checkNull(subList.get(2)).trim()));
					if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
	    	    		repvo.setPrinciple(myFormatter.format(reconNum));
    	    		}
					//repvo.setPrinciple((CommonFunction.checkNull(subList.get(3)).trim()));
					if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
	    	    		repvo.setInstCom(myFormatter.format(reconNum));
    	    		}
					//repvo.setInstCom((CommonFunction.checkNull(subList.get(4)).trim()));
					if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
	    	    		repvo.setExcess(myFormatter.format(reconNum));
    	    		}
					
					//repvo.setExcess((CommonFunction.checkNull(subList.get(5)).trim()));
					repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
					if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
	    	    		repvo.setPrinOS(myFormatter.format(reconNum));
    	    		}
					//repvo.setPrinOS((CommonFunction.checkNull(subList.get(7)).trim()));
					if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
	    	    		repvo.setOtherCharges(myFormatter.format(reconNum));
    	    		}
					else
					{
						repvo.setOtherCharges("0.00");
					}
					
					
				
				}
				list.add(repvo);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}