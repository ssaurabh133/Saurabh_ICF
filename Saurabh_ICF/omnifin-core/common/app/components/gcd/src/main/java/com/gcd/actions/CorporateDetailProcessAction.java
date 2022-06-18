package com.gcd.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.gcd.VO.CorporateDetailsVO;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;

public class CorporateDetailProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(CorporateDetailProcessAction.class.getName());
	public ActionForward saveCorporateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		
		
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}else{
				logger.info("here saveCorporateDetails method of CorporatedetailProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			logger.info("Implementation class: "+corporateDao.getClass());
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			ArrayList  riskCategoryList= corporateDao.getriskCategoryList();
			session.setAttribute("riskCategoryList", riskCategoryList);
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
			String source="NE";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);		
			if(funid==4000122 || funid==4000123)
				source="ED";
			//neeraj space end
		String pageStatuss="";
		if(session.getAttribute("pageStatuss")!=null)
		{
			pageStatuss= session.getAttribute("pageStatuss").toString();
		}
		logger.info("In CorporateDetailAction saveCorporateDetails() page status+++++++++++++++++ "+pageStatuss);
		
		
		
		DynaValidatorForm CorporateDetailsDynaValidatorForm = (DynaValidatorForm) form;
		CorporateDetailsVO corporateDetailVo=new CorporateDetailsVO();
	    org.apache.commons.beanutils.BeanUtils.copyProperties(corporateDetailVo, CorporateDetailsDynaValidatorForm);
	    
		corporateDetailVo.setMakerId(userId);
		corporateDetailVo.setMakerDate(bDate);
		corporateDetailVo.setPagestatus(pageStatuss);

		logger.info("user id-----------------------"+userId);
		logger.info("user id-----------------------"+bDate);
	
		
	    String regNoCheck =null;
	    String panCheck=null;
	    String VatRegNo = null;
	    String hGroupId="";
	    String dealId = "";
	    if(request.getParameter("hGroupId")!=null)
	    {
	    	hGroupId=request.getParameter("hGroupId");
	    }
	    corporateDetailVo.sethGroupId(hGroupId);
	    String tableName="";
	    if(pageStatuss!=null && pageStatuss.equals("fromLeads"))
		 {
			 tableName="cr_deal_customer_m";
		 }
	    else
		{
			tableName="gcd_customer_m";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="gcd_customer_m_edit";
		}
	   
	    logger.info("registration No............++++++++++++"+corporateDetailVo.getRegistrationNo());
	    corporateDetailVo.setBypassDedupe(CommonFunction.checkNull(request.getParameter("bypassDedupe")));
	    if(!corporateDetailVo.getBypassDedupe().equalsIgnoreCase("B"))
	    {
	    	logger.info("************ inside if for check dedupe ****************");
		    if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
		    {
		    	regNoCheck = ConnectionDAO.singleReturn("Select CUSTOMER_ID from gcd_customer_m where CUSTOMER_REGISTRATION_NO='"+StringEscapeUtils.escapeSql(corporateDetailVo.getRegistrationNo())+"'");
		    }
		   // panCheck = ConnectionDAO.singleReturn("Select CUSTOMER_ID from gcd_customer_m where  CUSTOMER_TYPE='C' and CUSTMER_PAN='"+StringEscapeUtils.escapeSql(corporateDetailVo.getPan())+"'");
		    String conCheck = request.getParameter("constitution");
		      String pan = corporateDetailVo.getPan();
		      logger.info("constitution---->" + conCheck + ",pan-->" + pan);
		      if ((!conCheck.equals("PROPRIETOR")) && (!pan.equals("")))
		      {
		        panCheck = ConnectionDAO.singleReturn("Select CUSTOMER_ID from gcd_customer_m where  CUSTMER_PAN='" + StringEscapeUtils.escapeSql(corporateDetailVo.getPan()) + "'");
		      }
		    if(request.getParameter("vatRegNo")!="")
		    {
		    	VatRegNo = ConnectionDAO.singleReturn("Select CUSTOMER_ID from gcd_customer_m where CUSTOMER_VAT_NO='"+StringEscapeUtils.escapeSql(corporateDetailVo.getVatRegNo())+"'");
		    }
		    else
		    {
		    	VatRegNo=null;
		    }
	    }
	    
	    if(regNoCheck==null && panCheck==null && VatRegNo==null)
	    {
	    	   
				
				if(session.getAttribute("dealId")!=null)
				{
					
					dealId=session.getAttribute("dealId").toString();
				}
				else if(session.getAttribute("maxId")!=null)
				{
					dealId=session.getAttribute("maxId").toString();
				}
				//logger.info("In CorporateDetailProcessAction execute id " +dealId); 
				    String loanId = "";
					
					if(session.getAttribute("loanId")!=null)
					{
						
						loanId=session.getAttribute("loanId").toString();
					}
					else if(session.getAttribute("maxIdInCM")!=null)
					{
						loanId =  session.getAttribute("maxIdInCM").toString();
					}
					
					
					//logger.info("In  loanId id " +loanId);  
				if(session.getAttribute("applType")!=null)
			    {
					corporateDetailVo.setApplType(session.getAttribute("applType").toString());
			    }
//				if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).equalsIgnoreCase(""))
//				{
//					corporateDetailVo.setFirstName(corporateDetailVo.getCorporateName());
//				}
				int corporateId=0;
				if(dealId!=null && !CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
				{
					corporateId =corporateDao.saveCorporateDetails(corporateDetailVo,"C",dealId,source);
				}
				else
				{
					corporateId =corporateDao.saveCorporateDetails(corporateDetailVo,"C",loanId,source);
				}
	    	   
	    	    String cusType="";
	    	   // String custStatus="";
	    	    if(session.getAttribute("cusType")!=null)
				{
					cusType=session.getAttribute("cusType").toString();
					logger.info("Customer type is ....................................."+cusType);
				}
	    	    logger.info("save part  .....");
		    if(corporateId>0)
		    {
		    	if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
		    	{
			    	if(pageStatuss!=null && pageStatuss.equals("fromLeads"))
					 {
			    		RefreshFlagVo vo = new RefreshFlagVo();
			    		vo.setRecordId(Integer.parseInt(dealId));
			    		vo.setTabIndex(2);
			    		vo.setModuleName("CP");
			    		vo.setCustomerType((String)session.getAttribute("applType"));
			    		vo.setCostomerID(corporateId+"");
			    		RefreshFlagValueInsert.updateRefreshFlag(vo);
						session.setAttribute("dealId", dealId);
					 }
			    	else if(loanId!=null && !loanId.equalsIgnoreCase(""))
			    	{
			    		RefreshFlagVo vo = new RefreshFlagVo();	 
			    		vo.setRecordId(Integer.parseInt(loanId));
			    		vo.setTabIndex(10);
			    		vo.setModuleName("CM");
			    		vo.setCustomerType((String)session.getAttribute("applType"));
			    		vo.setCostomerID(corporateId+"");
						RefreshFlagValueInsert.updateRefreshFlag(vo);
						session.setAttribute("loanId", loanId);
			    	}
		    	}
//		    	session.setAttribute("custIdInDealCap", corporateId);
		    	session.removeAttribute("roleList");
		    	if(dealId!=null && !CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
				{
		    		ArrayList<Object> roleList = corporateDao.getRoleList(dealId);
		    		session.setAttribute("roleList", roleList);
			        logger.info("role list size:"+roleList.size());
				}
		    	else
		    	{
		    		ArrayList<Object> roleList = corporateDao.getCustomerRoleList(loanId,source);
		    		session.setAttribute("roleList", roleList);
			        logger.info("role list size:"+roleList.size());
			        session.removeAttribute("pageStatuss");
			    	if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000106")||CommonFunction.checkNull(functionId).equalsIgnoreCase("4000122"))
					{
	    				 session.setAttribute("updateInLoan", "updateInLoan");
						 logger.info(" functionId:CUA "+functionId);
					}
	    			else
	    			{
	    				 session.removeAttribute("updateInLoan");
						 logger.info("functionId:CUA "+functionId);
	    			}
		    	}
		       
//		        custStatus=corporateDao.checkCustomerStatus(corporateId,cusType);
//		        session.setAttribute("custStatus", custStatus);
				session.removeAttribute("operation");
				session.removeAttribute("individualInfo");
				session.removeAttribute("addressInfo");
				session.removeAttribute("statesList");
				session.removeAttribute("detailList");
				session.removeAttribute("insert");
				session.removeAttribute("citiesList");
				session.removeAttribute("stakeDetails");
				session.removeAttribute("ratingList");
				session.removeAttribute("updateList");
				session.removeAttribute("approve");
				session.removeAttribute("update");
		       	session.removeAttribute("customerCategory");
				session.removeAttribute("constitutionlist");
				session.removeAttribute("registrationTypeList");
				session.removeAttribute("subIndustryList");
				session.removeAttribute("businessSegmentList");
				session.setAttribute("corporateId",corporateId);
				
				session.removeAttribute("idividualId");
				
				session.setAttribute("idividualId", "");
				session.removeAttribute("detailList");
				request.setAttribute("sms","S");
				request.setAttribute("status", "");
				if(session.getAttribute("pParentGroup")!=null)
				{
					session.removeAttribute("pParentGroup");
				}
				if(session.getAttribute("strParentOption")!=null)
				{
					session.removeAttribute("strParentOption");
				}
				 session.setAttribute("cusType", "C");
				return mapping.findForward("success");
		    }
		    else
		    {
		    	request.setAttribute("sms","exist");
		    }
	    }
	    else
	    {
	    	logger.info("Exist pan no.....");
	    	session.setAttribute("insert", "insert");
	    	ArrayList detailList = new ArrayList();
	    	logger.info("industry ****************************** "+corporateDetailVo.getIndustry());
	    	logger.info("industry h id****************************** "+corporateDetailVo.getLbxIndustry());
	    	logger.info("subindustry ****************************** "+corporateDetailVo.getSubIndustry());
	    	logger.info("sub industry id****************************** "+corporateDetailVo.getLbxSubIndustry());
	    	request.setAttribute("otherRelationShip", corporateDetailVo.getOtherRelationShip());
	    	if(corporateDetailVo.getBlackListed().equalsIgnoreCase("on"))
	    	{
	    		corporateDetailVo.setBlackListed("Y");
	    	}
	    	else
	    	{
	    		corporateDetailVo.setBlackListed("N");
	    	}
	    	
	    	detailList.add(corporateDetailVo);
	    	
	    	session.setAttribute("detailList", detailList);
	    	if(regNoCheck!=null && panCheck==null && VatRegNo==null)
	    	{
	    		request.setAttribute("sms","Reg");
	    	}
	    	else if(regNoCheck==null && panCheck!=null && VatRegNo==null)
	    	{
	    		request.setAttribute("sms","Pan");
	    	}
	    	else if(regNoCheck==null && panCheck==null && VatRegNo!=null)
	    	{
	    		request.setAttribute("sms","Vat");
	    	}
	    	else if(regNoCheck!=null && panCheck!=null && VatRegNo==null)
	    	{
	    		request.setAttribute("sms","RegAndPan");
	    	}
	    	else if(regNoCheck!=null && panCheck==null && VatRegNo!=null)
	    	{
	    		request.setAttribute("sms","RegAndVat");
	    	}
	    	else if(regNoCheck==null && panCheck!=null && VatRegNo!=null)
	    	{
	    		request.setAttribute("sms","PanAndVat");
	    	}
	    	else if(regNoCheck!=null && panCheck!=null && VatRegNo!=null)
	    	{
	    		request.setAttribute("sms","RegPanAndVat");
	    	}
	    	
	    }
	    session.setAttribute("cusType", "C");
	    return mapping.getInputForward();
	}

	
	public ActionForward updateCorporateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
			logger.info("In CorporateDetailAction updateCorporateDetails()");
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}else{
				logger.info("here updateCorporateDetails method of CorporatedetailProcessAction action the session is out----------------");
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

			//code added by neeraj
			String source="NE";
			String  tableName="gcd_customer_m";
			String functionId=(String)session.getAttribute("functionId");
			int funid=Integer.parseInt(functionId);		
			if(funid==4000122 || funid==4000123)
				source="ED";
			//neeraj space end
			CorporateDAO corporateDao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
			logger.info("Implementation class: "+corporateDao.getClass());
			
		int cId=0;
		String recStatus="";
		String statusCase="";
		String updateFlag="";
		String regNoCheck ="";
	    String panCheck="";
	    String VatRegNo = "";
	    String pageStatuss="";
	    String dealId = "";
	    ArrayList  riskCategoryList= corporateDao.getriskCategoryList();
		session.setAttribute("riskCategoryList", riskCategoryList);
		
		if(session.getAttribute("dealId")!=null)
		{
			
			dealId=session.getAttribute("dealId").toString();
		}
		else if(session.getAttribute("maxId")!=null)
		{
			dealId=session.getAttribute("maxId").toString();
		}
		if(session.getAttribute("pageStatuss")!=null)
		{
			pageStatuss= session.getAttribute("pageStatuss").toString();
		}
		
		if(session.getAttribute("corporateId")!=null)
		{
			cId = Integer.parseInt(session.getAttribute("corporateId").toString());
		}
		DynaValidatorForm CorporateDetailsDynaValidatorForm = (DynaValidatorForm) form;
		CorporateDetailsVO corporateDetailVo=new CorporateDetailsVO();
		String hGroupId="";
		corporateDetailVo.setDealId(dealId);
		corporateDetailVo.setMakerId(userId);
		corporateDetailVo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(corporateDetailVo, CorporateDetailsDynaValidatorForm);
		String reason=corporateDetailVo.getReasonForBlackListed();
		logger.info("Reason  :  "+reason);
	  
	    corporateDetailVo.setBypassDedupe(CommonFunction.checkNull(request.getParameter("bypassDedupe")));
	    logger.info("bypass Dedupe: "+CommonFunction.checkNull(request.getParameter("bypassDedupe")));
	   
	    if(request.getParameter("hGroupId")!=null)
	    {
	    	hGroupId = request.getParameter("hGroupId");
	    	
	    	corporateDetailVo.sethGroupId(hGroupId);
	    }
	    if(request.getParameter("customerStatus")!=null)
	    {
	    	corporateDetailVo.setPagestatus(request.getParameter("customerStatus"));
	    }

	    if(session.getAttribute("statusCase")!=null)
	    {
	    	statusCase=session.getAttribute("statusCase").toString();
	    }
	    if(session.getAttribute("updateFlag")!=null)
	    {
	    	updateFlag=session.getAttribute("updateFlag").toString();
	    		
	    }
	    if(session.getAttribute("recStatus")!=null)
	    {
	    	recStatus=session.getAttribute("recStatus").toString();
	    }
	    String cusType="";
	    int regNo=0;
	    int panNo=0;
	    int vatNo=0;
		if(session.getAttribute("cusType")!=null)
		{
			cusType=session.getAttribute("cusType").toString();
			
		}
		
		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")))
		{
			tableName="cr_deal_customer_m";
			
			String custStatus="";
			custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+cId);
			
			if(custStatus!=null && custStatus.equalsIgnoreCase("N"))
			{
				 if(!corporateDetailVo.getBypassDedupe().equalsIgnoreCase("B"))
				 {
				    logger.info("************ inside if for check dedupe 1 ****************");
				    if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
				    {
				    	regNoCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_REGISTRATION_NO='"+corporateDetailVo.getRegistrationNo()+"') t where t.CUSTOMER_ID<>"+cId+""));
				    }
				    String conCheck = request.getParameter("constitution");
			          String pan = corporateDetailVo.getPan();
			          logger.info("constitution---->" + conCheck + ",pan--->" + pan);
			          if ((!conCheck.equals("PROPRIETOR")) && (!pan.equals("")))
			          {
			            panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where  CUSTMER_PAN='" + corporateDetailVo.getPan() + "') t where t.CUSTOMER_ID<>" + cId + ""));
					
					//panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where  CUSTOMER_TYPE='C' and CUSTMER_PAN='"+corporateDetailVo.getPan()+"') t where t.CUSTOMER_ID<>"+cId+""));
					logger.info("pancheck in dedupe 1: "+panCheck);
			          }
				    if(CommonFunction.checkNull(request.getParameter("vatRegNo"))!="")
				    {
				    	VatRegNo = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_VAT_NO='"+corporateDetailVo.getVatRegNo()+"') t where t.CUSTOMER_ID<>"+cId+""));
				    	
				    }
				    else
				    {
				    	VatRegNo="";
				    }
				 }
				 if(panCheck.equals("0") || panCheck.equals(""))
			     {
					 panNo=0;
			     }
			     else
			     {
			    	 panNo=Integer.parseInt(panCheck);
			     }
				 
				 if(regNoCheck.equals("0") || regNoCheck.equals(""))
			     {
					 regNo=0;
			     }
			     else
			     {
			    	 regNo=Integer.parseInt(regNoCheck);
			     }
				 
				 if(VatRegNo.equals("0") || VatRegNo.equals(""))
			     {
			    	 vatNo=0;
			     }
			     else
			     {
			     	 vatNo=Integer.parseInt(VatRegNo);
			     }
			  
			}
			else
			{
				 logger.info("registration No...2........."+corporateDetailVo.getRegistrationNo());
				 if(!corporateDetailVo.getBypassDedupe().equalsIgnoreCase("B"))
				 {
					 
					
				    logger.info("************ inside else for check dedupe 2 ****************");
				    if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
				    {
				    	if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
					    {
				    		regNoCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_REGISTRATION_NO='"+corporateDetailVo.getRegistrationNo()+"') t where t.GCD_CUSTOMER_ID<>"+cId+""));
					    }
				    	 String conCheck = request.getParameter("constitution");
				            String pan = corporateDetailVo.getPan();
				            logger.info("constitution---->" + conCheck + ",pan--->" + pan);
				            if ((!conCheck.equals("PROPRIETOR")) && (!pan.equals("")))
				            {
				              panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTMER_PAN='" + corporateDetailVo.getPan() + "') t where t.GCD_CUSTOMER_ID<>" + cId + ""));
				              logger.info("pancheck-->: " + panCheck);
				            }
						
						//panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_TYPE='C' and CUSTMER_PAN='"+corporateDetailVo.getPan()+"') t where t.GCD_CUSTOMER_ID<>"+cId+""));
					}
				    if(CommonFunction.checkNull(request.getParameter("vatRegNo"))!="")
				    {
				    	VatRegNo = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_VAT_NO='"+corporateDetailVo.getVatRegNo()+"') t where t.GCD_CUSTOMER_ID<>"+cId+""));
				    	
				    }
				    else
				    {
				    	VatRegNo="";
				    }
				 }
				 
				 if(panCheck.equals("0") || panCheck.equals(""))
			     {
					 panNo=0;
			     }
			     else
			     {
			    	 panNo=Integer.parseInt(panCheck);
			     }
				 
				 if(regNoCheck.equals("0") || regNoCheck.equals(""))
			     {
					 regNo=0;
			     }
			     else
			     {
			    	 regNo=Integer.parseInt(regNoCheck);
			     }
				 
				 if(VatRegNo.equals("0") || VatRegNo.equals(""))
			     {
			    	 vatNo=0;
			     }
			     else
			     {
			     	 vatNo=Integer.parseInt(VatRegNo);
			     }
			}
		   		   		    
			session.removeAttribute("updateInLoan");	   		    
		    session.setAttribute("updateInDeal", "updateInDeal");
		    logger.info("updateInDeal ................................................. "+request.getAttribute("updateInDeal"));
		}
		else
		{
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000106"))
			{
				 session.setAttribute("updateInLoan", "updateInLoan");
				 logger.info("functionId:CUA "+functionId);
			}
			else
			{
				 session.removeAttribute("updateInLoan");
				 logger.info(" functionId:CUA "+functionId);
			}
			session.removeAttribute("updateInDeal");
			String updateInMaker="";
			if(session.getAttribute("updateInMaker")!=null)
			{
				updateInMaker = session.getAttribute("updateInMaker").toString();
			}
			if((updateInMaker!=null && !updateInMaker.equals("")) && (statusCase!=null && !statusCase.equals("")))
			{
				tableName="gcd_customer_m_temp";
			}
			else 
			{
				 if(statusCase!=null && statusCase.equals("UnApproved"))
				 {
					 tableName="gcd_customer_m_temp";
				 }
				 else
				 {
					 tableName="gcd_customer_m";
					 if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						 tableName="gcd_customer_m_edit";
				 }
			}
			logger.info("registration No...3........."+corporateDetailVo.getRegistrationNo());
			 if(!corporateDetailVo.getBypassDedupe().equalsIgnoreCase("B"))
			 {
			    logger.info("************ inside if for check dedupe 3 ****************");
			    if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
			    {
			    	if(!(corporateDetailVo.getRegistrationNo().equals("00000")))
				    {
			    		regNoCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_REGISTRATION_NO='"+corporateDetailVo.getRegistrationNo()+"') t where t.CUSTOMER_ID<>"+cId+""));
				    }
				    
					//panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_TYPE='C' and CUSTMER_PAN='"+corporateDetailVo.getPan()+"') t where t.CUSTOMER_ID<>"+cId+""));
			    }
			  /*  if(CommonFunction.checkNull(request.getParameter("vatRegNo"))!="")
			    {
			    	VatRegNo = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_VAT_NO='"+corporateDetailVo.getVatRegNo()+"') t where t.CUSTOMER_ID<>"+cId+""));
			    	
			    }*/
			    String conCheck = request.getParameter("constitution");
		          String pan = corporateDetailVo.getPan();
		          logger.info("constitution---->" + conCheck + ",pan--->" + pan);
		          if ((!conCheck.equals("PROPRIETOR")) && (!pan.equals("")))
		          {
		            panCheck = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTMER_PAN='" + corporateDetailVo.getPan() + "') t where t.CUSTOMER_ID<>" + cId + ""));
		            logger.info("pancheck in dedupe 1: " + panCheck);
		          }
			 
			 if (CommonFunction.checkNull(request.getParameter("vatRegNo")) != "")
		        {
		          VatRegNo = CommonFunction.checkNull(ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from gcd_customer_m where CUSTOMER_VAT_NO='" + corporateDetailVo.getVatRegNo() + "') t where t.CUSTOMER_ID<>" + cId + ""));
		        }
			    else
			    {
			    	VatRegNo="";
			    }
			 }
			 if(panCheck.equals("0") || panCheck.equals(""))
		     {
				 panNo=0;
		     }
		     else
		     {
		    	 panNo=Integer.parseInt(panCheck);
		     }
			 
			 if(regNoCheck.equals("0") || regNoCheck.equals(""))
		     {
				 regNo=0;
		     }
		     else
		     {
		    	 regNo=Integer.parseInt(regNoCheck);
		     }
			 
			 if(VatRegNo.equals("0") || VatRegNo.equals(""))
		     {
		    	 vatNo=0;
		     }
		     else
		     {
		     	 vatNo=Integer.parseInt(VatRegNo);
		     }
	    
	   
		}
		
		 logger.info("regNo :"+regNo);
		    logger.info("panNo :"+panNo);
		    logger.info("vatNo :"+vatNo);
		    String query ="";
	    if(regNo<1 && panNo<1 && vatNo <1)
	    {
	    	 
	    	query = "Select customer_id from gcd_customer_m_temp where customer_id="+cId;
	    	logger.info("query for check customer id in gcd_customer_m_temp :"+query);
	    	String custCheckInTemp = ConnectionDAO.singleReturn(query);
	    	logger.info("status for check customer id in gcd_customer_m_temp :"+custCheckInTemp+" statusCase: "+statusCase+" updateFlag: "+updateFlag+" recStatus: "+recStatus);
	    	String insertInTempFlag="";
	
	    	
	    	if((CommonFunction.checkNull(custCheckInTemp).equalsIgnoreCase("") && statusCase!=null && recStatus.equalsIgnoreCase("A")) || (CommonFunction.checkNull(custCheckInTemp).equalsIgnoreCase("") && updateFlag!=null && recStatus.equalsIgnoreCase("A")))
	    	{
	    		
	    		insertInTempFlag = corporateDao.insertAllIntoTempFromMainTable(cId+"",cusType);
	    		request.setAttribute("procval",insertInTempFlag);
	    		logger.info("if Group id from dyna:------ "+CorporateDetailsDynaValidatorForm.getString("hGroupId"));
	    		logger.info("if Group id from vo:------ "+corporateDetailVo.gethGroupId());
	    		boolean corporateId=corporateDao.saveCorporateUpdate(corporateDetailVo, cId, recStatus,statusCase,updateFlag,pageStatuss,source);
	    		session.removeAttribute("customerCategory");
	    		session.removeAttribute("constitutionlist");
	    		session.removeAttribute("registrationTypeList");
	    		session.removeAttribute("subIndustryList");
	    		if(corporateId)
	    		{
	    			request.setAttribute("sms","U");
	    		}
	    		else{
	    			request.setAttribute("sms","E");
	    		}
	    	
	    		logger.info("procval"+request.getAttribute("procval"));
	    		session.setAttribute("updateInMaker", "updateInMaker");
	    		
	    	}
	    	else
	    	{
	    		logger.info("else Group id from dyna:------ "+CorporateDetailsDynaValidatorForm.getString("hGroupId"));
	    		logger.info("else Group id from vo:------ "+corporateDetailVo.gethGroupId());
	    		boolean corporateId=corporateDao.saveCorporateUpdate(corporateDetailVo, cId, recStatus,statusCase,updateFlag,pageStatuss,source);
	    		if(corporateId)
	    		{
	    			request.setAttribute("sms","U");
	    		}else{
	    			request.setAttribute("sms","E");
	    		}
	    		
	    	}
	    	
	    	if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")))
			{
				if(session.getAttribute("dealId")!=null)
				{
					
					dealId=session.getAttribute("dealId").toString();
				}
				else if(session.getAttribute("maxId")!=null)
				{
					dealId=session.getAttribute("maxId").toString();
				}
				if(!dealId.equalsIgnoreCase("")){
	    		RefreshFlagVo vo = new RefreshFlagVo();
	    		vo.setRecordId(Integer.parseInt(dealId));
	    		vo.setTabIndex(2);
	    		vo.setModuleName("CP");
	    		String qu="select deal_customer_role_type from cr_deal_customer_role where deal_id='"+dealId+"' and deal_customer_id='"+corporateDetailVo.getCorporateCode()+"'";
	    		String applType=ConnectionDAO.singleReturn(qu);
	    		vo.setCustomerType(applType);
                vo.setCostomerID(corporateDetailVo.getCorporateCode());
	    		RefreshFlagValueInsert.updateRefreshFlag(vo);
				session.setAttribute("dealId", dealId);
				}
			 } 
	    	else
	    	{
	    		String loanId = "";
				
				if(session.getAttribute("loanId")!=null)
				{
					
					loanId=session.getAttribute("loanId").toString();
				}
				else if (session.getAttribute("maxIdInCM")!=null)
				{
					loanId =  session.getAttribute("maxIdInCM").toString();
				}
	    		if(!loanId.equalsIgnoreCase(""))
	    		{	
	    		RefreshFlagVo vo = new RefreshFlagVo();
	    		vo.setRecordId(Integer.parseInt(loanId));
	    		vo.setTabIndex(10);
	    		vo.setModuleName("CM");
	    		String qu="select loan_customer_role_type from cr_loan_customer_role where loan_id='"+loanId+"' and gcd_id='"+corporateDetailVo.getCorporateCode()+"'";
	    		String appype=ConnectionDAO.singleReturn(qu);
	    		vo.setCustomerType(appype);
	    		vo.setCostomerID(corporateDetailVo.getCorporateCode());
				RefreshFlagValueInsert.updateRefreshFlag(vo);
				session.setAttribute("loanId", loanId);	    		}

	    	}
//	    	String custStatus="";
//    	    custStatus=corporateDao.checkCustomerStatus(cId,cusType);
//	        session.setAttribute("custStatus", custStatus);
	    	return mapping.findForward("success");
	    }
		
	    else
	    {
	    	ArrayList detailList = new ArrayList();
	    	detailList.add(corporateDetailVo);
	    	session.removeAttribute("detailList");
	    	//System.out.println("\n\n\nneeraj   "+vo.getGroupName()+"\n\n");
	    	session.setAttribute("detailList", detailList);
	    	request.setAttribute("otherRelationShip", corporateDetailVo.getOtherRelationShip());
	    	if(regNo>=1 && panNo<1 && vatNo<1)
	    	{
	    		request.setAttribute("sms","Reg");
	    	}
	    	
	    	else if(regNo<1 && panNo>=1 && vatNo<1)
	    	{
	    		request.setAttribute("sms","Pan1");
	    	}
	    	else if(regNo<1 && panNo<1 && vatNo>=1)
	    	{
	    		request.setAttribute("sms","Vat");
	    	}
	    	else if(regNo>=1 && panNo>=1 && vatNo<1)
	    	{
	    		request.setAttribute("sms","RegAndPan");
	    	}
	    	else if(regNo>=1 && panNo<1 && vatNo>=1)
	    	{
	    		request.setAttribute("sms","RegAndVat");
	    	}
	    	else if(regNo<1 && panNo>=1 && vatNo>=1)
	    	{
	    		request.setAttribute("sms","PanAndVat");
	    	}
	    	 
	    }
	    session.setAttribute("cusType", "C");
		return mapping.getInputForward();
	}
	
	
	
}
