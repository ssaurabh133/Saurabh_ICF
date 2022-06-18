package com.cp.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.cp.vo.CommonNotepadVo;
import com.cp.vo.HeaderInfoVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.NotepadDAO;
import com.logger.LoggerMsg;

public class NotepadDAOImpl implements NotepadDAO{
	private static final Logger logger = Logger.getLogger(NotepadDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	public ArrayList getDealListForNotePad(CommonNotepadVo vo,HttpServletRequest request) {
		{
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
//			String userName="";
			
			
			StringBuilder dealId = new StringBuilder(); 
			StringBuilder lonaNo = new StringBuilder(); 
			StringBuilder aDate = new StringBuilder(); 
			StringBuilder productId = new StringBuilder(); 
			StringBuilder custName = new StringBuilder(); 
			StringBuilder scheme = new StringBuilder(); 
//			String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
//			String lonaNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNoHID())).trim());
//			String aDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
//			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
//			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
//			String scheme = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
			
			
			dealId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim()));
			lonaNo.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNoHID())).trim()));
			aDate.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim()));
			custName.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim()));
			productId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim()));
			 scheme.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim()));
			
			
//			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}

			
			try
			{
				ArrayList header=null;
				CommonNotepadVo fetchVo= null;
				boolean appendSQL=false;
				StringBuilder bufInsSql=new StringBuilder();
				StringBuilder bufInsSqlTempCount = new StringBuilder();

				bufInsSql.append(" select distinct d.DEAL_ID,d.DEAL_NO,DATE_FORMAT(d.DEAL_INITIATION_DATE,'%d-%m-%Y'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC,DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'%d-%m-%Y') from cr_deal_dtl d");
				bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_deal_dtl d ");
				
				bufInsSql.append(" left join cr_deal_loan_dtl dl on d.DEAL_ID=dl.DEAL_ID  ");
				bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.DEAL_ID=dl.DEAL_ID  ");
				bufInsSql.append(" left join cr_deal_customer_m g on g.CUSTOMER_ID=d.DEAL_CUSTOMER_ID  ");
				bufInsSqlTempCount.append(" left join cr_deal_customer_m g on g.CUSTOMER_ID=d.DEAL_CUSTOMER_ID  ");
				bufInsSql.append(" left join cr_product_m p on dl.DEAL_PRODUCT=p.PRODUCT_ID  ");
				bufInsSqlTempCount.append(" left join cr_product_m p on dl.DEAL_PRODUCT=p.PRODUCT_ID  ");
				bufInsSql.append(" left join cr_scheme_m s on dl.DEAL_SCHEME=s.SCHEME_ID ");
				bufInsSqlTempCount.append(" left join cr_scheme_m s on dl.DEAL_SCHEME=s.SCHEME_ID ");
				
				
					if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxDealNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
					{
				   	  bufInsSql.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxDealNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND dl.DEAL_PRODUCT='"+vo.getLbxProductID()+"'AND dl.DEAL_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='A' AND");
				   	  bufInsSqlTempCount.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxDealNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND dl.DEAL_PRODUCT='"+vo.getLbxProductID()+"'AND dl.DEAL_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='A' AND");
					}
				   		
					if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxDealNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
						appendSQL=true;
					}
					
					if(appendSQL){
						logger.info("In Where Clause");
						bufInsSql.append(" WHERE  D.DEAL_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='F' AND");
						bufInsSqlTempCount.append(" WHERE  D.DEAL_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='F' AND");
					}
										
				if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
			        bufInsSql.append(" d.DEAL_ID='"+vo.getLbxDealNo()+"' AND");
			        bufInsSqlTempCount.append(" d.DEAL_ID='"+vo.getLbxDealNo()+"' AND");
			   	 appendSQL=true;
			   	  
			     }
				
				 if((!(vo.getLbxDealNoHID().equalsIgnoreCase("")))) {
			        bufInsSql.append(" d.DEAL_ID='"+vo.getLbxDealNoHID()+"' AND");
			        bufInsSqlTempCount.append(" d.DEAL_ID='"+vo.getLbxDealNoHID()+"' AND");
			   	 appendSQL=true;
			   	  
			     }
				 
							
				if((!(vo.getAgrementDate().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') AND");
			   	  bufInsSqlTempCount.append(" d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') AND");
			   	  appendSQL=true;
			     }
				
				if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND");
			   	  bufInsSqlTempCount.append(" g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND");
			   	  appendSQL=true;
			     }
				if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" dl.DEAL_PRODUCT='"+vo.getLbxProductID()+"' AND");
				   	  bufInsSqlTempCount.append(" dl.DEAL_PRODUCT='"+vo.getLbxProductID()+"' AND");
				   	  appendSQL=true;
				     }
				if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" dl.DEAL_SCHEME='"+vo.getLbxscheme()+"' AND");
				   	  bufInsSqlTempCount.append(" dl.DEAL_SCHEME='"+vo.getLbxscheme()+"' AND");
				   	  appendSQL=true;
				     }
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){
					logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					
			     logger.info("In getloanListForNotePad() ## tmp ## "+tmp);
			     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
			   
			     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3)){
			     logger.info("In appendSQL true----  in check index Of");
			     tmp = (tmp).substring(0,(tmp.length()-4));
			     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
			     logger.info("getloanListForNotePad Query...tmp."+tmp);
			     header = ConnectionDAO.sqlSelect(tmp);
			     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			     
			      }else
			      {
			     	  logger.info("getloanListForNotePad Query...tmp."+tmp);
			     	 header = ConnectionDAO.sqlSelect(tmp); 
			     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			       }
				 }else {
			    	  
						LoggerMsg.info("search Query...else-------." + bufInsSql);
						LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			            
						count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
						
						
						if((dealId.toString().trim()==null && lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
						{
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						
						}
						LoggerMsg.info("query : "+bufInsSql);
						
						header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					}

							
				for(int i=0;i<header.size();i++){
					//logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonNotepadVo();
						fetchVo.setModifyNo("<a href=notePadBehindInCPAction.do?dealId="
						+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&dealStatus=" 
						+ ">"
						+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
						
						fetchVo.setLbxDealNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
						fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
						fetchVo.setTotalRecordSize(count);
//						fetchVo.setReportingToUserId(userName);
						list.add(fetchVo);
					}
				}
				/*if( header.size()==0)
				{
					CommonNotepadVo fetchMVo = new CommonNotepadVo();
					fetchMVo.setTotalRecordSize(count);
					list.add(fetchMVo);
					request.setAttribute("flag","yes");
					//LoggerMsg.info("getTotalRecordSize : "+fetchMVo.getTotalRecordSize());
				}*/
				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dealId=null;
				lonaNo=null;
				aDate=null;
				productId=null;
				custName=null;
				scheme=null;
			}

			return list;
		}
	}
	public ArrayList getLeadListForNotePad(CommonNotepadVo vo,HttpServletRequest request) {
		{
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
					
			try
			{
				ArrayList header=null;
				CommonNotepadVo fetchVo= null;
				boolean appendSQL=false;
				StringBuilder bufInsSql=new StringBuilder();
				StringBuilder bufInsSqlTempCount = new StringBuilder();

				bufInsSql.append(" select distinct d.LEAD_ID,d.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC from cr_lead_dtl d ");
				bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_lead_dtl d ");
				
				bufInsSql.append(" left join cr_product_m p on d.PRODUCT=p.PRODUCT_ID  ");
				bufInsSqlTempCount.append(" left join cr_product_m p on d.PRODUCT=p.PRODUCT_ID  ");
				bufInsSql.append(" left join cr_scheme_m s on d.SCHEME=s.SCHEME_ID ");
				bufInsSqlTempCount.append(" left join cr_scheme_m s on d.SCHEME=s.SCHEME_ID ");
				
				   		
					if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxDealNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
						appendSQL=true;
					}
					
					if(appendSQL){
						logger.info("In Where Clause");
						bufInsSql.append(" WHERE  D.BRANCH_ID='"+vo.getBranchId()+"' AND");
						bufInsSqlTempCount.append(" WHERE  D.BRANCH_ID='"+vo.getBranchId()+"' AND");
					}
										
				if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
			        bufInsSql.append(" d.LEAD_ID='"+vo.getLbxDealNo()+"' AND");
			        bufInsSqlTempCount.append(" d.LEAD_ID='"+vo.getLbxDealNo()+"' AND");
			   	 appendSQL=true;
			   	  
			     }
				
				 if((!(vo.getLbxDealNoHID().equalsIgnoreCase("")))) {
			        bufInsSql.append(" d.LEAD_ID='"+vo.getLbxDealNoHID()+"' AND");
			        bufInsSqlTempCount.append(" d.LEAD_ID='"+vo.getLbxDealNoHID()+"' AND");
			   	 appendSQL=true;
			   	  
			     }
				 			
				if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" d.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND");
			   	  bufInsSqlTempCount.append(" d.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND");
			   	  appendSQL=true;
			     }
				if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" d.PRODUCT='"+vo.getLbxProductID()+"' AND");
				   	  bufInsSqlTempCount.append(" d.PRODUCT='"+vo.getLbxProductID()+"' AND");
				   	  appendSQL=true;
				     }
				if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" d.SCHEME='"+vo.getLbxscheme()+"' AND");
				   	  bufInsSqlTempCount.append(" d.SCHEME='"+vo.getLbxscheme()+"' AND");
				   	  appendSQL=true;
				     }
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){
					logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					
			     logger.info("In getloanListForNotePad() ## tmp ## "+tmp);
			     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
			   
			     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3)){
			     logger.info("In appendSQL true----  in check index Of");
			     tmp = (tmp).substring(0,(tmp.length()-4));
			     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
			     logger.info("getloanListForNotePad Query...tmp."+tmp);
			     header = ConnectionDAO.sqlSelect(tmp);
			     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			     
			      }else
			      {
			     	  logger.info("getloanListForNotePad Query...tmp."+tmp);
			     	 header = ConnectionDAO.sqlSelect(tmp); 
			     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			       }
				 }else {
			    	  
						LoggerMsg.info("search Query...else-------." + bufInsSql);
						LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			            
						count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						
						LoggerMsg.info("query : "+bufInsSql);
						
						header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					}

							
				for(int i=0;i<header.size();i++){
					//logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonNotepadVo();
						fetchVo.setModifyNo("<a href=notePadBehindInLeadAction.do?dealId="
						+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&dealStatus=" 
						+ ">"
						+ CommonFunction.checkNull(header1.get(0)).trim().toString() + "</a>");
						
						fetchVo.setLbxDealNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setTotalRecordSize(count);
//						fetchVo.setReportingToUserId(userName);
						list.add(fetchVo);
					}
				}

				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return list;
		}
	}

	public ArrayList getDealHeader(String dealId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query =new StringBuilder();
			 query.append(" select L.DEAL_ID,L.DEAL_NO,L.DEAL_CUSTOMER_ID,G.CUSTOMER_NAME,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y'),p.PRODUCT_DESC,s.SCHEME_DESC,D.DEAL_PRODUCT_CATEGORY from cr_deal_dtl L "+
						   
							" left join cr_deal_loan_dtl D on D.DEAL_ID=L.DEAL_ID"+
							" left join cr_product_m p on D.DEAL_PRODUCT=p.PRODUCT_ID"+
							" left join cr_scheme_m s on D.DEAL_SCHEME=s.SCHEME_ID "+
							 " left join cr_deal_customer_m G on G.CUSTOMER_ID=L.DEAL_CUSTOMER_ID "+
							" where L.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" limit 1");
			
			logger.info("getDealHeader Query::::::::::::::::::::::::::::::::::::"+query);
			
			HeaderInfoVo vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<header.size();i++){
			//	logger.info("header"+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new HeaderInfoVo();
					vo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
					vo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
					vo.setDealCustomerId((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setDealCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
					vo.setDealDate((CommonFunction.checkNull(header1.get(4))).trim());
					vo.setDealProduct((CommonFunction.checkNull(header1.get(5))).trim());
					vo.setDealScheme((CommonFunction.checkNull(header1.get(6))).trim());
					vo.setDealProductCat((CommonFunction.checkNull(header1.get(7))).trim());
					list.add(vo);
					vo=null;
				}
			}
			query=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			dealId=null;
		}

		return list;

	}
   
	public ArrayList getLeadHeader(String leadId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query =new StringBuilder();
			 query.append(" select L.LEAD_ID,L.CUSTOMER_NAME,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y'),p.PRODUCT_DESC,s.SCHEME_DESC,p.PRODUCT_CATEGORY from cr_lead_dtl L "+
							" left join cr_product_m p on L.PRODUCT=p.PRODUCT_ID"+
							" left join cr_scheme_m s on L.SCHEME=s.SCHEME_ID "+
							" where L.LEAD_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)).trim()+" limit 1");
			
			logger.info("getLeadHeader Query::::::::::::::::::::::::::::::::::::"+query);
			
			HeaderInfoVo vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<header.size();i++){
			//	logger.info("header"+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new HeaderInfoVo();
					vo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
					vo.setDealCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
					vo.setDealDate((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setDealProduct((CommonFunction.checkNull(header1.get(3))).trim());
					vo.setDealScheme((CommonFunction.checkNull(header1.get(4))).trim());
					vo.setDealProductCat((CommonFunction.checkNull(header1.get(5))).trim());
					list.add(vo);
				}
			}
			query=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;

	}
   
}
