package com.commonFunction.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.commonFunction.dao.commonDao;
import com.commonFunction.vo.ConstitutionVO;
import com.commonFunction.vo.CorporateDetailsVO;
import com.commonFunction.vo.CreditRatingGridVo;
import com.commonFunction.vo.CreditRatingVo;
import com.commonFunction.vo.CustomerCategoryVO;
import com.commonFunction.vo.CustomerSaveVo;
import com.commonFunction.vo.IndustryVO;
import com.commonFunction.vo.InstitutionNameVo;
import com.commonFunction.vo.RegistrationTypeVO;
import com.commonFunction.vo.StakeHolderTypeVo;
import com.commonFunction.vo.StakeHolderVo;
import com.commonFunction.vo.StakePositionVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.commonFunction.vo.ShowCustomerDetailVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;




public class MSSQLcommonFunctionDaoImpl implements commonDao
{
	private static final Logger logger = Logger.getLogger(MSSQLcommonFunctionDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");


	public ArrayList getAssetLoanDetailAmount(String dealId) 
{
	ArrayList list = new ArrayList();
	try 
	{
		logger.info("In getAssetLoanDetailAmount()");
		StringBuilder query=new StringBuilder();
		
		 query.append("select cr_product_m.REPAYMENT_TYPE,cr_product_m.ASSET_FLAG,cr_deal_loan_dtl.DEAL_ASSET_COST as astcstLD," +
		 		"  cr_deal_loan_dtl.DEAL_LOAN_AMOUNT as lonamtLD,ast.ac as astcstAD,ast.lm as lonamtAD from cr_deal_loan_dtl " +
		 		"  join cr_product_m on(cr_product_m.PRODUCT_ID=cr_deal_loan_dtl.DEAL_PRODUCT and cr_product_m.PRODUCT_CATEGORY=cr_deal_loan_dtl.DEAL_PRODUCT_CATEGORY) " +
		 		"  left outer join (select a.deal_id,sum(");
		 query.append("ISNULL(b.ASSET_COLLATERAL_VALUE,0)) as ac,sum(");
		 query.append("ISNULL(b.LOAN_AMOUNT,0)) as lm " +
		 		"  from cr_deal_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID)where b.ASSET_TYPE='ASSET' and b.ASSET_COLLATERAL_CLASS='VEHICLE' and a.DEAL_ID="+dealId.trim()+") " +
		 		"  as ast on(ast.deal_id=cr_deal_loan_dtl.DEAL_ID) where cr_deal_loan_dtl.DEAL_ID="+dealId.trim());
		logger.info("In getAssetLoanDetailAmount()  query   :   "+ query.toString());
		list = ConnectionDAO.sqlSelect(query.toString());
		
	}
	catch (Exception e) 
	{e.printStackTrace();}
	return list;
}



	//--------------------------Customer Detail in CP and Master---------------------
	
	
	public ArrayList<Object> getIndividualDetails(String code, Object pageStatus,String updateInMaker,String statusCase,String updateFlag,String pageStatuss)  
{
	logger.info("In getIndividualDetails() of CortateDAOImpl.");
	ArrayList<Object> list=new ArrayList<Object>();
	String tableName="";
	StringBuilder query=new StringBuilder();		//String query="";  asesh
	query.setLength(0);
	if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
	{
		tableName="cr_deal_customer_m";
		try
		{
			String custStatus="";
			custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+code);
			logger.info("customer status ................................................. "+custStatus);
			 query.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
			 query.append(dbo);
			 query.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER " +
			      "from "+tableName+" where CUSTOMER_ID="+code+"" );
			 logger.info("In getIndividualDetails() of CortateDAOImpl.  Query : "+query);
			ArrayList individualDetails = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getIndividualDetails "+individualDetails.size());
			int size=individualDetails.size();
			for(int i=0;i<size;i++)
			{
				ArrayList data=(ArrayList)individualDetails.get(i);
				if(data.size()>0)	
				{
					ShowCustomerDetailVo show=new ShowCustomerDetailVo();
					show.setCorporateCode((CommonFunction.checkNull(data.get(0)).toString()));
					show.setFirstName((CommonFunction.checkNull(data.get(1)).toString()));
					show.setMiddleName((CommonFunction.checkNull(data.get(2)).toString()));
					show.setLastName((CommonFunction.checkNull(data.get(3)).toString()));
					show.setInstitutionEmail((CommonFunction.checkNull(data.get(4)).toString()));
					show.setIncorporationDate((CommonFunction.checkNull(data.get(5)).toString()));
					show.setPan((CommonFunction.checkNull(data.get(6)).toString()));
					show.setReferredBy((CommonFunction.checkNull(data.get(7)).toString()));
					show.setBlackListed((CommonFunction.checkNull(data.get(8)).toString()));
					show.setReasonForBlackListed((CommonFunction.checkNull(data.get(9)).toString()));
					show.setCorporateCategory((CommonFunction.checkNull(data.get(10)).toString()));
					show.setCusType((CommonFunction.checkNull(data.get(11)).toString()));
					show.setConstitution((CommonFunction.checkNull(data.get(12)).toString()));
					show.setFatherHusband((CommonFunction.checkNull(data.get(13)).toString()));
					show.setCasteCategory((CommonFunction.checkNull(data.get(14)).toString()));
					show.setMaritalStatus((CommonFunction.checkNull(data.get(15)).toString()));
					show.setDrivingLicense((CommonFunction.checkNull(data.get(16)).toString()));
					show.setVoterId((CommonFunction.checkNull(data.get(17)).toString()));
					show.setPassport((CommonFunction.checkNull(data.get(18)).toString()));
					list.add(show);
				}
		    }
		}catch(Exception e)
		{e.printStackTrace();}			    
	}
	else
	{
			if(pageStatus!=null && pageStatus.equals("approve") || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || statusCase.equalsIgnoreCase("UnApproved"))
					tableName="gcd_customer_m_temp";
			else
					tableName="gcd_customer_m";
			try
			{	
				
				query.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
				query.append(dbo);
				query.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_STATUS,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER " +
				      "from "+tableName+" where CUSTOMER_ID="+code+"" );
				 logger.info("In getIndividualDetails() of CortateDAOImpl.  Query : "+query);
				ArrayList individualDetails = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getIndividualDetails "+individualDetails.size());
				int size=individualDetails.size();
				for(int i=0;i<size;i++)
				{
					ArrayList data=(ArrayList)individualDetails.get(i);
					if(data.size()>0)
					{
						ShowCustomerDetailVo show=new ShowCustomerDetailVo();
						
						show.setCorporateCode((CommonFunction.checkNull(data.get(0)).toString()));
						show.setFirstName((CommonFunction.checkNull(data.get(1)).toString()));
						show.setMiddleName((CommonFunction.checkNull(data.get(2)).toString()));
						show.setLastName((CommonFunction.checkNull(data.get(3)).toString()));
						show.setInstitutionEmail((CommonFunction.checkNull(data.get(4)).toString()));
						show.setIncorporationDate((CommonFunction.checkNull(data.get(5)).toString()));
						show.setPan((CommonFunction.checkNull(data.get(6)).toString()));
						show.setReferredBy((CommonFunction.checkNull(data.get(7)).toString()));
						show.setBlackListed((CommonFunction.checkNull(data.get(8)).toString()));
						show.setReasonForBlackListed((CommonFunction.checkNull(data.get(9)).toString()));
						show.setCorporateCategory((CommonFunction.checkNull(data.get(10)).toString()));
						show.setCusType((CommonFunction.checkNull(data.get(11)).toString()));
						show.setPagestatus((CommonFunction.checkNull(data.get(12)).toString()));
						show.setConstitution((CommonFunction.checkNull(data.get(13)).toString()));
						show.setFatherHusband((CommonFunction.checkNull(data.get(14)).toString()));
						show.setCasteCategory((CommonFunction.checkNull(data.get(15)).toString()));
						show.setMaritalStatus((CommonFunction.checkNull(data.get(16)).toString()));
						show.setDrivingLicense((CommonFunction.checkNull(data.get(17)).toString()));
						show.setVoterId((CommonFunction.checkNull(data.get(18)).toString()));
						show.setPassport((CommonFunction.checkNull(data.get(19)).toString()));
						list.add(show);
					}
				}
			}catch(Exception e)
			{e.printStackTrace();}
	    }
		return list;	
}


    public ArrayList<Object> getCustomerCategoryList() 
    {
    	logger.info("in getCustomerCategoryList() of CorpotateDAOImpl. ");  		
		ArrayList<Object> list=new ArrayList<Object>();
	  	try
	  	{	  		
	  		String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";
	  		logger.info("in getCustomerCategoryList() of CorpotateDAOImpl Query :  "+query);
	  		CustomerCategoryVO vo = null;
	  		ArrayList source = ConnectionDAO.sqlSelect(query);
	  		logger.info("getCustomerCategoryList"+source.size());
	  		int size=source.size();
	  		for(int i=0;i<size;i++)
	  		{
	  			ArrayList subCategory=(ArrayList)source.get(i);
	  			if(subCategory.size()>0)
	  			{
	  				vo = new CustomerCategoryVO();
	  				vo.setCustomerCategoryCode((CommonFunction.checkNull(subCategory.get(0)).toString()));
	  				vo.setCustomerCategoryDesc((CommonFunction.checkNull(subCategory.get(1)).toString()));
	  				list.add(vo);	  				
	  			}
	  		}
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}
	  		return list;
	  		
	      

}

    
    public ArrayList<Object> getCastCategoryList() 
    {
    	logger.info("in getCastCategoryList() of CorpotateDAOImpl. ");  		
		ArrayList<Object> list=new ArrayList<Object>();
	  	try
	  	{	  		
	  		String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CAST_CATEGORY' and rec_status ='A'";
	  		logger.info("in getCastCategoryList() of CorpotateDAOImpl Query :  "+query);
	  		CustomerCategoryVO vo = null;
	  		ArrayList source = ConnectionDAO.sqlSelect(query);
	  		logger.info(" in getCastCategoryList() size  : "+source.size());
	  		int size=source.size();
	  		for(int i=0;i<size;i++)
	  		{
	  			ArrayList subCategory=(ArrayList)source.get(i);
	  			if(subCategory.size()>0)
	  			{
	  				vo = new CustomerCategoryVO();
	  				vo.setCastCategoryCode((CommonFunction.checkNull(subCategory.get(0)).toString()));
	  				vo.setCastCategoryDesc((CommonFunction.checkNull(subCategory.get(1)).toString()));
	  				list.add(vo);	  				
	  			}
	  		}
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}
	  		return list;
}
    
    
	public ArrayList<Object> getIndividualContitutionList()
	{
		logger.info("in getIndividualContitutionList() of CorpotateDAOImpl");		
		ArrayList<Object> list=new ArrayList<Object>();
		try
		{		
			String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION' and PARENT_VALUE='INDV' and rec_status ='A'";
			logger.info("in getIndividualContitutionList() of CorpotateDAOImpl  Query : "+query);
			ConstitutionVO vo = null;
			ArrayList source = ConnectionDAO.sqlSelect(query);
			logger.info("Size : "+source.size());
			int size =source.size();
			for(int i=0;i<size;i++)
			{
				ArrayList subConstitution=(ArrayList)source.get(i);
				if(subConstitution.size()>0)
				{
					vo = new ConstitutionVO();
					vo.setContitutionCode((CommonFunction.checkNull(subConstitution.get(0)).toString()));
					vo.setConstitution((CommonFunction.checkNull(subConstitution.get(1)).toString()));
					list.add(vo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;			
	}
	
	
	public int setApproveStatus(int cId, String statusCase,String userId,String businessDate)
	{
		int status=0;
		boolean qrystatus=false;
			try{
				logger.info("CorporateDAOImpl setApproveStatus()");
				StringBuilder bufInsSql=new StringBuilder();
				if(statusCase.equalsIgnoreCase("Approved") || statusCase.equalsIgnoreCase("UnApproved"))
				{
					bufInsSql.append("update gcd_customer_m_temp set CUSTOMER_STATUS='F' where CUSTOMER_ID=?");
				}
				else
				{
					bufInsSql.append("update gcd_customer_m set CUSTOMER_STATUS='A',MAKER_ID='"+CommonFunction.checkNull(userId)+"'," +
						  "MAKER_DATE=");
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE('"+CommonFunction.checkNull(businessDate)+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append(" where CUSTOMER_ID=?");
				}
				PrepStmtObject prepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				if(CommonFunction.checkNull(cId).equalsIgnoreCase(""))
					prepStmtObject.addNull();
				else
					prepStmtObject.addInt(cId);
				logger.info(" query :"+bufInsSql.toString());
				prepStmtObject.setSql(bufInsSql.toString());
				qryList.add(prepStmtObject);
				qrystatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}catch(Exception e){
					e.printStackTrace();
				}
			if(qrystatus)
			{
				status=1;
			}
				return status;
		}


	public String insertAllIntoTempFromMainTable(String id,String cusType)
	{
		logger.info("in insertAllIntoTempFromMainTable() of CorpotateDAOImpl");
		int statusProc=0;
		int custID=Integer.parseInt(id.trim());
		String type=cusType.trim();
		String s1="";
		String s2="";
		String status="";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		try
		{
			logger.info("in procedure Gcd_Tmp_Insert------>");
			in.add(custID);
			in.add(type);
			out.add(s1);
     	    out.add(s2);
     	    logger.info("Gcd_Tmp_Insert("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Tmp_Insert",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
    	    s2=CommonFunction.checkNull(outMessages.get(1));
    	    if(s1.equalsIgnoreCase("S"))
    	    {
    	    	status=s1;
    	    }
    	    else
    	    {
    	    	status=s2;
    	    }
    	    logger.info("s1::::::::::::: "+s1);
		    logger.info("s2::::::::::::::::: "+s2);		    
		  
		}
		catch(Exception e)
		{e.printStackTrace();}		
		return status;	
	
	}
	

	public boolean saveCorporateUpdate(Object ob, int id, String recStatus, String statusCase,String updateFlag,String pageStatuss) 
	{
		logger.info("in saveCorporateUpdate() of CorpotateDAOImpl");
		
		CorporateDetailsVO corporateDetailVo=(CorporateDetailsVO)ob;		
		boolean status = false;
		String tableName="";
		String customerType="";
		
		try
		{				
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
					tableName="cr_deal_customer_m";
					String custStatus="";
					custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+id);
					logger.info("corporateDetailVo.getCorporateName(): " +StringEscapeUtils.escapeSql(corporateDetailVo.getCorporateName()));
					
					StringBuilder queryUpdat=new StringBuilder();
					customerType= ConnectionDAO.singleReturn("select CUSTOMER_TYPE from cr_deal_customer_m where CUSTOMER_ID="+id);
					PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
					ArrayList qryList2 = new ArrayList();
					logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
					queryUpdat.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
					queryUpdat.append(dbo);
					queryUpdat.append("STR_TO_DATE(?,'"+dateFormat+"')," +
								" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?," +
								" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?," +
								" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=");
					queryUpdat.append(dbo);
					queryUpdat.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					queryUpdat.append(",CUSTOMER_GROUP_ID=?,AUTHOR_ID=?,AUTHOR_DATE=");
					queryUpdat.append(dbo);
					queryUpdat.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					queryUpdat.append(",FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? where CUSTOMER_ID=?");
				
					
					if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim()));
						
					if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLastName()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getPan()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim()));
									
					if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim()));
				
					if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim()));
					
					if((CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim()).equalsIgnoreCase("on"))
							insertPrepStmtObject3.addString("Y");	
					else
							insertPrepStmtObject3.addString("N");
										
					if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim()));
					
					if(CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(customerType).trim()));
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerId()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerDate()).trim());
									
					if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim()));
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerId()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerDate()).trim());
									
					if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getFatherHusband()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getCasteCategory()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMaritalStatus()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getDrivingLicense()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getVoterId()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getPassport()).trim());
					
					insertPrepStmtObject3.addInt(id);
				
					insertPrepStmtObject3.setSql(queryUpdat.toString());
					logger.info("in saveCorporateUpdate() of CorpotateDAOImpl  Query  :"+insertPrepStmtObject3.printQuery());
					qryList2.add(insertPrepStmtObject3);
		            status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
				
				}		
				else
				{
					if(statusCase!=null && statusCase.length()>0)//&& statusCase.equals("UnApproved"))
						tableName="gcd_customer_m_temp";
					else
						tableName="gcd_customer_m";
					// asesh space start
					StringBuilder queryUpdate=new StringBuilder();
					customerType= ConnectionDAO.singleReturn("select CUSTOMER_TYPE from gcd_customer_m where CUSTOMER_ID="+id);
					
					StringBuilder bufInsUpdSql=new StringBuilder();
					PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
					
					ArrayList qryList2 = new ArrayList();
					ArrayList insertUpdate = new ArrayList();
					bufInsUpdSql.append(" INSERT INTO GCD_CUSTOMER_HST ");
			          bufInsUpdSql.append(" ( ");
			          
			          bufInsUpdSql.append(" CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
					  bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
					  bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
					  bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
					  bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
					  bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
					  bufInsUpdSql.append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,MVMT_DATE,MVMT_BY");
			         
			          bufInsUpdSql.append(" ) ");
			          
			          bufInsUpdSql.append(" SELECT CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
					  bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
					  bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
					  bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
					  bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
					  bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
					  bufInsUpdSql.append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,");
					  bufInsUpdSql.append(dbo);
					  bufInsUpdSql.append(" STR_TO_DATE('"+CommonFunction.checkNull(corporateDetailVo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					  bufInsUpdSql.append(",'"+CommonFunction.checkNull(corporateDetailVo.getMakerId())+"'");
			          bufInsUpdSql.append(" FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID ='"+id+"' ");
			        
			         
			         insertPrepStmtObject3.setSql(bufInsUpdSql.toString());
			         logger.info("insert history :"+insertPrepStmtObject3.printQuery());
			         insertUpdate.add(insertPrepStmtObject3);
			        // qryList2.add(insertPrepStmtObject3);
			         boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);
			         
					logger.info("CorporateDAOImpl saveCorporateUpdate().............recStatus.........................."+recStatus);
					if(tableName.equalsIgnoreCase("gcd_customer_m"))
					{				
						queryUpdate.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"')," +
									" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?," +
									" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?," +
									" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=");
						queryUpdate.append(dbo);
						queryUpdate.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
						queryUpdate.append(",AUTHOR_ID=?,AUTHOR_DATE=");
						queryUpdate.append(dbo);
						queryUpdate.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
							//queryUpdate.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");  //asesh space
						queryUpdate.append(",FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? ,GROUP_ID=? where CUSTOMER_ID=?");
				
					}
					else
					{
						queryUpdate.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"')," +
				     				" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?," +
				     				" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?," +
				     				" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? ,GROUP_ID=? where CUSTOMER_ID=?");
				
					}
				logger.info("in saveCorporateUpdate() of CorpotateDAOImpl update query : "+queryUpdate);
				
				
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim()));
					
				if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLastName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getPan()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim()));
				
				if((CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim()).equalsIgnoreCase("on"))
					insertPrepStmtObject3.addString("Y");	
				else
					insertPrepStmtObject3.addString("N");
							
				if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim()));
			
				if(CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((CommonFunction.checkNull(customerType).trim()));
				
				if(tableName.equalsIgnoreCase("gcd_customer_m"))
				{					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerId()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerDate()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerId()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerDate()).trim());
				}
				if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getFatherHusband()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getCasteCategory()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getMaritalStatus()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getDrivingLicense()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getVoterId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getPassport()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.gethGroupId()).trim());
				
				insertPrepStmtObject3.addInt(id);
			    insertPrepStmtObject3.setSql(queryUpdate.toString());
				logger.info("query :"+insertPrepStmtObject3.printQuery());
				qryList2.add(insertPrepStmtObject3);
				logger.info("Ritu..............");
				
				//---------Ritu Code Start----------
				//tableName="cr_deal_customer_m";
				StringBuilder queryUpdate1=new StringBuilder();
				PrepStmtObject insertPrepStmtObject6 = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
				queryUpdate1.append("update cr_deal_customer_m set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
				queryUpdate1.append(dbo);
				queryUpdate1.append("STR_TO_DATE(?,'"+dateFormat+"')," +
							" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?," +
							" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?," +
							" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=");
				queryUpdate1.append(dbo);
				queryUpdate1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				queryUpdate1.append(",CUSTOMER_GROUP_ID=?,AUTHOR_ID=?,AUTHOR_DATE=");
				queryUpdate1.append(dbo);
				queryUpdate1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				queryUpdate1.append(",FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? where GCD_CUSTOMER_ID=?");
			
				logger.info("asdfsadf:---"+queryUpdate1);
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim()));
					
				if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getLastName()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getPan()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim()));
								
				if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim()));
			
				if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim()));
				
				if((CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim()).equalsIgnoreCase("on"))
						insertPrepStmtObject6.addString("Y");	
				else
						insertPrepStmtObject6.addString("N");
									
				if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim()));
				
				if(CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(customerType).trim()));
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getMakerDate()).trim());
								
				if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim()));
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getMakerDate()).trim());
								
				if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getFatherHusband()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getCasteCategory()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getMaritalStatus()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getDrivingLicense()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getVoterId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject6.addNull();
				else
					insertPrepStmtObject6.addString((corporateDetailVo.getPassport()).trim());
				
				insertPrepStmtObject6.addInt(id);
				insertPrepStmtObject6.setSql(queryUpdate1.toString());
				logger.info("in saveCorporateUpdate() of CorpotateDAOImpl  Query  :"+insertPrepStmtObject6.printQuery());
				qryList2.add(insertPrepStmtObject6);
				//---------Ritu Code End------------
				//boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
		   }			
		}catch(Exception e)
		{e.printStackTrace();}
	   return status;
	}


	public int saveCorporateDetails(Object ob,String st,String dealId)
	{
		
		CorporateDetailsVO corporateDetailVo=(CorporateDetailsVO)ob;
		String fatherName=corporateDetailVo.getFatherHusband();
		logger.info("corporateDetailVo value of magker id +++++"+corporateDetailVo.getMakerId());
		logger.info("corporateDetailVo value of magker date +++++++++++++++++"+corporateDetailVo.getMakerDate());
		int rowcount=0;
		int maxId=0;
		boolean checkApp=false;
			PrepStmtObject insertPrepStmtObject =null;
			try{
			
				 if(corporateDetailVo.getPagestatus()!=null && corporateDetailVo.getPagestatus().equals("fromLeads"))
				 {
					 logger.info("In Credit Processing , Customer Entry..,saveCorporateDetails()......");
					logger.info("CorporateDAOImpl saveCorporateDetails()-----------from Deal Capturing------------"+corporateDetailVo.getPagestatus());
					 if(corporateDetailVo.getApplType()!=null && corporateDetailVo.getApplType().equalsIgnoreCase("PRAPPL"))
					 {
						    String checkQ ="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId+" and DEAL_CUSTOMER_ID is not NULL";
							logger.info("In Cr_deal_dtl. query....................."+checkQ);
							checkApp=ConnectionDAO.checkStatus(checkQ);
					 }
						logger.info("corporateDetailVo.getCorporateName(): " +corporateDetailVo.getCorporateName());
					if(!checkApp)
					{
						StringBuffer bufInsSql1 = new StringBuffer();
						PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
						ArrayList queryList = new ArrayList();
						bufInsSql1.append("Insert into cr_deal_customer_m (CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,");
						bufInsSql1.append("CUSTOMER_TYPE,CUSTOMER_GROUP_ID,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,");
						bufInsSql1.append("CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,");
						bufInsSql1.append("CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,");
						bufInsSql1.append("CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER)");
						bufInsSql1.append(" values (");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append(dbo);
						bufInsSql1.append("STR_TO_DATE(?,'"+dateFormat+"'), ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append("?, ");
						bufInsSql1.append(dbo);
						bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
						bufInsSql1.append("?, ");
						bufInsSql1.append(dbo);
						bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
						bufInsSql1.append("?, ");//FATHER_HUSBAND_NAME
						bufInsSql1.append("?, ");//CASTE_CATEGORY
						bufInsSql1.append("?, ");//MARITAL_STATUS
						bufInsSql1.append("?, ");//DRIVING_LICENSE
						bufInsSql1.append("?, ");//VOTER_ID
						bufInsSql1.append("? ) ");//PASSPORT_NUMBER
				

						if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getCorporateName()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getFirstName()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMiddleName()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getLastName()).trim());
						
						if(st.equalsIgnoreCase("C"))
						 {
							insPrepStmtObject2.addString("C");
						 }
						 else if(st.equalsIgnoreCase("I"))
						 {
							 insPrepStmtObject2.addString("I");
						 }
						
						
						if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.gethGroupId()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getIncorporationDate()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getPan()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getRegistrationType()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getRegistrationNo()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getVatRegNo()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getCorporateCategory()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getConstitution()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getBusinessSegment()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getLbxIndustry()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getLbxSubIndustry()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getInstitutionEmail()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getWebAddress()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getReferredBy()).trim());
						
						if((corporateDetailVo.getBlackListed()).equals("on"))
						 {
							insPrepStmtObject2.addString("Y");                           
						 }
						 else
						 {
							 insPrepStmtObject2.addString("N");
						 }
						
						if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getReasonForBlackListed()).trim());
						
						insPrepStmtObject2.addString("A");
						//------------------------------------------
						if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMakerId()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMakerDate()).trim());
						
						
						if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMakerId()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMakerDate()).trim());
						//neeraj
						
						if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getFatherHusband()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getCasteCategory()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getMaritalStatus()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getDrivingLicense()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getVoterId()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getPassport()).trim());
						
						//------------------------------------------
						insPrepStmtObject2.setSql(bufInsSql1.toString());
						logger.info("IN saveCorporateDetails() insert query1 ### "+insPrepStmtObject2.printQuery());
						queryList.add(insPrepStmtObject2);
						boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
						int s=0;
						if(status1)
						{
							s=1;
						}
					
						if(s>0)
						{
									
						 String query1="Select max(CUSTOMER_ID) from cr_deal_customer_m  WITH (ROWLOCK) ";
						 String strMax = ConnectionDAO.singleReturn(query1); 
						 maxId=Integer.parseInt(strMax);				 
						 logger.info("Max Id for deal Capturing:................. "+maxId);		
						 
					        CorporateDetailsVO cv = (CorporateDetailsVO)ob;
							
							ArrayList qryList = new ArrayList();
							StringBuffer bufInsSql = new StringBuffer();
							PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
							boolean status=false;
							String stat="";
						
							try{
								if( cv.getHiApplType()!=null && !cv.getHiApplType().equals("PRAPPL"))
								{
									logger.info("In saveCustomer deal Capturing Applicant Type...."+ cv.getHiApplType());
									
							        bufInsSql.append("Insert into cr_deal_customer_role(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,MAKER_ID,MAKER_DATE)");
									bufInsSql.append(" values ( ");
								
									bufInsSql.append(" ?," );
																	
									bufInsSql.append(" ?," );
									bufInsSql.append(" ?," );
									bufInsSql.append(" ?," );
									bufInsSql.append(" ?," );
									bufInsSql.append(" ?," );
									bufInsSql.append(" ?," );
									bufInsSql.append(dbo);
									bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
									
																		
									if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
										insertPrepStmtObject1.addNull();
									else
										insertPrepStmtObject1.addString((dealId).trim());
									
									if(CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase(""))
										insertPrepStmtObject1.addNull();
									else
										insertPrepStmtObject1.addString((cv.getHiApplType()).trim());
									
									
									if(st.equalsIgnoreCase("c"))
									 {
										insertPrepStmtObject1.addString("C");
										stat="C";
									 }
									 else if(st.equalsIgnoreCase("i"))
									 {
										 insertPrepStmtObject1.addString("I");
										 stat="I";
									 }
									    insertPrepStmtObject1.addString("N");
									
								
										insertPrepStmtObject1.addString(""+maxId);
										insertPrepStmtObject1.addString("P");
										
										if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((corporateDetailVo.getMakerId()).trim());
										
										if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((corporateDetailVo.getMakerDate()).trim());
										
//										insertPrepStmtObject1.addNull();
//										insertPrepStmtObject1.addNull();
									
																													
									 insertPrepStmtObject1.setSql(bufInsSql.toString());				  
									 
									  
						             logger.info("IN Applicant insert query1 ### "+insertPrepStmtObject1.printQuery());
						             
						             qryList.add(insertPrepStmtObject1);	
						             
						              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);    
																						
								}
								else if(cv.getHiApplType()!=null && cv.getHiApplType().equals("PRAPPL"))
								{
								    logger.info("==================In saveCorporateDetails update deal table===========  ");
								    PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
									ArrayList qryList2 = new ArrayList();
									String queryUpdate="update cr_deal_dtl set DEAL_CUSTOMER_ID=?," +
									" DEAL_CUSTOMER_TYPE=?, DEAL_EXISTING_CUSTOMER=? where DEAL_ID=?";
									
									if(CommonFunction.checkNull(maxId).trim().equalsIgnoreCase(""))
										insertPrepStmtObject3.addNull();
									else
										insertPrepStmtObject3.addInt(maxId);
									
									if(CommonFunction.checkNull(st.trim()).trim().equalsIgnoreCase(""))
										insertPrepStmtObject3.addNull();
									else
									{
										if(st.equalsIgnoreCase("c"))
										 {
											stat="C";
										 }
										 else if(st.equalsIgnoreCase("i"))
										 {
											stat="I";
										 }
										insertPrepStmtObject3.addString((stat.trim()).trim());
									}
										
									
									insertPrepStmtObject3.addString("N");
									
									if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
										insertPrepStmtObject3.addNull();
									else
										insertPrepStmtObject3.addString((dealId).trim());
									insertPrepStmtObject3.setSql(queryUpdate.toString());
						            qryList2.add(insertPrepStmtObject3);
						            status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
						            
						            logger.info("In saveCorporateDetails update deal table  "+status);
									 
										logger.info("In updateLoanEntry......cr_deal_dtl table...............................Dao Impl");
										
										ArrayList qryList3 = new ArrayList();
										logger.info("app type in dao imp ....11111................................. "+cv.getHiApplType());
										StringBuffer bufInsSql2 = new StringBuffer();
										PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
										if(st.equals("c"))
										 {
											st="C";
										 }
										else if(st.equals("I"))
										{
											st="I";
										}
										
							            bufInsSql2.append("Insert into cr_deal_customer_role(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,MAKER_ID,MAKER_DATE)");
																		
							            bufInsSql2.append(" values ( ");
									
							            bufInsSql2.append(" ?," );
								
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(" ?," );
							            bufInsSql2.append(dbo);
							            bufInsSql2.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");
										
										logger.info("app type in dao imp .......2222............................. "+cv.getHiApplType());
										if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
											insertPrepStmtObject2.addNull();
										else
											insertPrepStmtObject2.addString((dealId).trim());
										
										if(CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase(""))
											insertPrepStmtObject2.addNull();
										else
											insertPrepStmtObject2.addString((cv.getHiApplType()).trim());
										
										if(st.equalsIgnoreCase("c"))
										 {
											insertPrepStmtObject2.addString("C");
										 }
										 else if(st.equalsIgnoreCase("I"))
										 {
											 insertPrepStmtObject2.addString("I");
										 }
										
										    insertPrepStmtObject2.addString("N");	
											insertPrepStmtObject2.addString(""+maxId);
											insertPrepStmtObject2.addString("P");
											
											if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
												insertPrepStmtObject2.addNull();
											else
												insertPrepStmtObject2.addString((corporateDetailVo.getMakerId()).trim());
											
											if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
												insertPrepStmtObject2.addNull();
											else
												insertPrepStmtObject2.addString((corporateDetailVo.getMakerDate()).trim());
//											insertPrepStmtObject2.addNull();
//											insertPrepStmtObject2.addNull();
										
										logger.info("In saveCustomerEntry...........4444444.  "+cv.getCorporateName()+"   ..................Dao Impl"+cv.getFirstName());
										insertPrepStmtObject2.setSql(bufInsSql2.toString());				  
										logger.info("IN Applicant insert query1 ### ***********************"+insertPrepStmtObject2.printQuery());
							            qryList3.add(insertPrepStmtObject2);		           
							            status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList3);    
									}
																		
						              logger.info("In role table......................"+status);	
							}
							catch(Exception e){
								e.printStackTrace();
							}
					}
				 }
				else
				{
					maxId=0;
				}
				}
				else
				{
					logger.info("In GCD..,saveCorporateDetails()......");
			ArrayList qryList = new ArrayList();
			
			StringBuffer bufInsSql =	new StringBuffer();
			insertPrepStmtObject=new PrepStmtObject();
			bufInsSql.append("insert into gcd_customer_m(CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_STATUS,GROUP_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), " );
			bufInsSql.append("?, ");//FATHER_HUSBAND_NAME
			bufInsSql.append("?, ");//CASTE_CATEGORY
			bufInsSql.append("?, ");//MARITAL_STATUS
			bufInsSql.append("?, ");//DRIVING_LICENSE
			bufInsSql.append("?, ");//VOTER_ID
			bufInsSql.append("? ) ");//PASSPORT_NUMBER
	
			
			
			if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getCorporateName()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))	
			    insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getFirstName()).trim());
						
			if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMiddleName()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getLastName()).trim());
			
						
			if(CommonFunction.checkNull(st).trim().equalsIgnoreCase("c"))
				insertPrepStmtObject.addString("C");
			else
				insertPrepStmtObject.addString("I");
			
			if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
			    insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getIncorporationDate()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getPan()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getRegistrationType()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getRegistrationNo()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getVatRegNo()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getCorporateCategory()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getConstitution()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getBusinessSegment()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getLbxIndustry()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getLbxSubIndustry()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getInstitutionEmail()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getWebAddress()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getReferredBy()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim().equalsIgnoreCase("on"))
				insertPrepStmtObject.addString("Y");
			else
				insertPrepStmtObject.addString("N");
			
			if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getReasonForBlackListed()).trim());
			
			insertPrepStmtObject.addString("P");
			
			if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.gethGroupId()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMakerId()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMakerDate()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMakerId()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMakerDate()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getFatherHusband()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getCasteCategory()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getMaritalStatus()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getDrivingLicense()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getVoterId()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getPassport()).trim());
			
			//logger.info("ffffffffffffffffffffffffffffffffff"+corporateDetailVo.getMakerDate());
			//logger.info("*********************************"+corporateDetailVo.getMakerId());
			
			
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveCorporateDetails() insert query1 ### "+insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				 boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				 String gcdID=ConnectionDAO.singleReturn("select max(CUSTOMER_ID) from gcd_customer_m  WITH (ROWLOCK) ");
				
				 logger.info("IN saveCorporateDetails() gcdID ### "+gcdID);
				//-----------Ritu Code--------------
				
				StringBuffer bufInsSql1 = new StringBuffer();
				PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
				ArrayList queryList = new ArrayList();
				bufInsSql1.append("Insert into cr_deal_customer_m (GCD_CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,");
				bufInsSql1.append("CUSTOMER_TYPE,CUSTOMER_GROUP_ID,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,");
				bufInsSql1.append("CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,");
				bufInsSql1.append("CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,");
				bufInsSql1.append("CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER)");
				bufInsSql1.append(" values (");
				bufInsSql1.append("?, ");//GCD_CUSTOMER_ID
				bufInsSql1.append("?, ");//CUSTOMER_NAME
				bufInsSql1.append("?, ");//CUSTOMER_FNAME
				bufInsSql1.append("?, ");//CUSTOMER_MNAME
				bufInsSql1.append("?, ");//CUSTOMER_LNAME
				bufInsSql1.append("?, ");//CUSTOMER_TYPE
				bufInsSql1.append("?, ");//CUSTOMER_GROUP_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append("STR_TO_DATE(?,'"+dateFormat+"'), ");//CUSTOMER_DOB
				bufInsSql1.append("?, ");//CUSTMER_PAN
				bufInsSql1.append("?, ");//CUSTOMER_REGISTRATION_TYPE
				bufInsSql1.append("?, ");//CUSTOMER_REGISTRATION_NO
				bufInsSql1.append("?, ");//CUSTOMER_VAT_NO
				bufInsSql1.append("?, ");//CUSTOMER_CATEGORY
				bufInsSql1.append("?, ");//CUSTOMER_CONSTITUTION
				bufInsSql1.append("?, ");//CUSTOMER_BUSINESS_SEGMENT
				bufInsSql1.append("?, ");//CUSTOMER_INDUSTRY
				bufInsSql1.append("?, ");//CUSTOMER_SUB_INDUSTRY
				bufInsSql1.append("?, ");//CUSTOMER_EMAIL
				bufInsSql1.append("?, ");//CUSTOMER_WEBSITE
				bufInsSql1.append("?, ");//CUSTOMER_REFERENCE
				bufInsSql1.append("?, ");//CUSTOMER_BLACKLIST
				bufInsSql1.append("?, ");//CUSTOMER_BLACKLIST_REASON
				bufInsSql1.append("?, ");//REC_STATUS
				bufInsSql1.append("?, ");//MAKER_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );//MAKER_DATE
				bufInsSql1.append("?, ");//AUTHOR_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );//AUTHOR_DATE
				bufInsSql1.append("?, ");//FATHER_HUSBAND_NAME
				bufInsSql1.append("?, ");//CASTE_CATEGORY
				bufInsSql1.append("?, ");//MARITAL_STATUS
				bufInsSql1.append("?, ");//DRIVING_LICENSE
				bufInsSql1.append("?, ");//VOTER_ID
				bufInsSql1.append("? ) ");//PASSPORT_NUMBER
				
				
				
				insPrepStmtObject2.addString(gcdID);
				
				
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getCorporateName()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getFirstName()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMiddleName()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getLastName()).trim());
				
				if(st.equalsIgnoreCase("C"))
				 {
					insPrepStmtObject2.addString("C");
				 }
				 else if(st.equalsIgnoreCase("I"))
				 {
					 insPrepStmtObject2.addString("I");
				 }
				
				
				if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.gethGroupId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getIncorporationDate()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getPan()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getRegistrationType()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getRegistrationNo()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getVatRegNo()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getCorporateCategory()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getConstitution()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getBusinessSegment()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getLbxIndustry()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getLbxSubIndustry()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getInstitutionEmail()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getWebAddress()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getReferredBy()).trim());
				
				if((corporateDetailVo.getBlackListed()).equals("on"))
				 {
					insPrepStmtObject2.addString("Y");                           
				 }
				 else
				 {
					 insPrepStmtObject2.addString("N");
				 }
				
				if(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getReasonForBlackListed()).trim());
				
				insPrepStmtObject2.addString("A");
				//------------------------------------------
				if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMakerDate()).trim());
				
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMakerDate()).trim());
				//neeraj
				
				if(CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getFatherHusband()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getCasteCategory()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getMaritalStatus()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getDrivingLicense()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getVoterId()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((corporateDetailVo.getPassport()).trim());
				
				//------------------------------------------
				insPrepStmtObject2.setSql(bufInsSql1.toString());
				logger.info("IN saveCorporateDetails() insert query1 ### "+insPrepStmtObject2.printQuery());
				queryList.add(insPrepStmtObject2);
				
				//Ritu Code End
		      
				
		        boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		        
			    logger.info("In saveCorporateDetails......................"+status);
				if(status)
				{
					 String query3="Select max(CUSTOMER_ID) from gcd_customer_m  WITH (ROWLOCK) ";
					 String id = ConnectionDAO.singleReturn(query3);
					 maxId=Integer.parseInt(id);
					 logger.info("maxId : "+maxId);
				}
			  }
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
			return maxId;
	}
	

	public ArrayList<Object> getAddressDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag) 
	{
		
		 String tableName="";
		 logger.info("In getAddressDetails() of CorpotateDAOImpl");
		 ArrayList<Object> list=new ArrayList<Object>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_address_m";
			 try
			 {
					String query="SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC "+
		              "from "+tableName+" c,generic_master m,com_country_m p,com_district_m d,com_state_m s "+
                      "where  m.VALUE=c.ADDRESS_TYPE AND GENERIC_KEY='ADDRESS_TYPE' AND P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID="+code;
					
					logger.info("in getAddressDetails() of CorpotateDAOImpl  Query : "+query);  				
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
					
					for(int i=0;i<addressDetails.size();i++)
					{
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setBp_id((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setAddr_type((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setAddr1((CommonFunction.checkNull(data.get(2)).toString()));
							addr.setDist((CommonFunction.checkNull(data.get(3)).toString()));
							addr.setState((CommonFunction.checkNull(data.get(4)).toString()));
							addr.setCountry((CommonFunction.checkNull(data.get(5)).toString()));
							list.add(addr);
						}
					}
				}catch(Exception e)
				{e.printStackTrace();}	
				
			}
			else
			{
				if(updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
				{
					if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
						tableName="com_address_m";
					else
						tableName="com_address_m_temp";
				}
				else 
					tableName="com_address_m";
				try
				{
						String query="SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC "+
			              " from "+tableName+" c,generic_master m,com_country_m p,com_district_m d,com_state_m s "+
                           " where  m.VALUE=c.ADDRESS_TYPE AND GENERIC_KEY='ADDRESS_TYPE' AND P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT"+
                           	" and c.BPID="+code;
						
						logger.info("in getAddressDetails() of CorpotateDAOImpl  Query :   "+query);
						ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
			
						logger.info("getAddressDetails "+addressDetails.size());
						for(int i=0;i<addressDetails.size();i++)
						{
							ArrayList data=(ArrayList)addressDetails.get(i);
							if(data.size()>0)
							{
								CustomerSaveVo addr=new CustomerSaveVo();
								addr.setBp_id((CommonFunction.checkNull(data.get(0)).toString()));
								addr.setAddr_type((CommonFunction.checkNull(data.get(1)).toString()));
								addr.setAddr1((CommonFunction.checkNull(data.get(2)).toString()));
								addr.setDist((CommonFunction.checkNull(data.get(3)).toString()));
								addr.setState((CommonFunction.checkNull(data.get(4)).toString()));
								addr.setCountry((CommonFunction.checkNull(data.get(5)).toString()));
								list.add(addr);
							}
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}



	
	
	
	public String getPanNoRecord(String tableName, int panNo) {
		String panCheck=null;
		panCheck = ConnectionDAO.singleReturn("Select CUSTOMER_ID from "+tableName+" where CUSTMER_PAN='"+panNo+"'");
		logger.info("panCheck :-"+panCheck);
		return panCheck;
	}
	
	
	public String getCustomerRecord(int id) {
		String panCheck=null;
		panCheck = ConnectionDAO.singleReturn("Select customer_id from gcd_customer_m_temp where customer_id="+id);
		logger.info("panCheck :-"+panCheck);
		return panCheck;
	}
	
	public String getCustomerRecord(String id) {
		String panCheck=null;
		panCheck = ConnectionDAO.singleReturn("Select customer_id from gcd_customer_m_temp where customer_id="+id);
		logger.info("panCheck :-"+panCheck);
		return panCheck;
	}
	
	
	
	public String getExistingCustomerRecord(int id) {
		String panCheck=null;
		panCheck = ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+id);
		logger.info("panCheck :-"+panCheck);
		return panCheck;
	}

	//------------------------To get PAN No. for a customer--------------------------------
	@Override
	public String getCustomerCount(String tableName, String panNo, int id) {
		String custCount=null;
		custCount = ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from "+tableName+" where CUSTMER_PAN='"+panNo+"') t where t.CUSTOMER_ID<>"+id);
		logger.info("custCount :-"+custCount);
		return custCount;
	}
	
	//------------------------To get VAT Reg No. for a customer--------------------------------
	@Override
	public String getVatRegNo(String tableName, String vatRegNo, int id) {
		String getVatRegNo=null;
		getVatRegNo = ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from "+tableName+" where CUSTOMER_VAT_NO='"+vatRegNo+"') t where t.CUSTOMER_ID<>"+id+"");
		logger.info("custCount :-"+getVatRegNo);
		return getVatRegNo;
	}
	
	@Override
	public String getRegNoCheck(String tableName, String vatRegNo, int id) {
		String getVatRegNo=null;
		getVatRegNo = ConnectionDAO.singleReturn("Select count(t.CUSTOMER_ID) from (Select * from "+tableName+" where CUSTOMER_REGISTRATION_NO='"+vatRegNo+"') t where t.CUSTOMER_ID<>"+id+"");
		logger.info("custCount :-"+getVatRegNo);
		return getVatRegNo;
	}


	@Override
	public ArrayList<CustomerSaveVo> defaultcountry() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In defaultcountry()..........................DAOImpl");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
			logger.info("In defaultcountry...............query...........DAOImpl"+ query);
			CustomerSaveVo vo = null;
			ArrayList country = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			logger.info("defaultcountry() " + country.size());
			for (int i = 0; i < country.size(); i++) {
				logger.info("defaultcountry()...Outer FOR loop "+ CommonFunction.checkNull(country.get(i)).toString());
				ArrayList data = (ArrayList) country.get(i);
				if (data.size() > 0) {
					vo = new CustomerSaveVo();
					vo.setDefaultcountryid((CommonFunction.checkNull(data.get(0))).trim());
					vo.setDefaultcountryname((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	@Override
    public ArrayList<Object> getAddressList() {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="select  VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'";
		logger.info("getAddressList"+query);
		RegistrationTypeVO vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query);
		logger.info("getAddressList"+source.size());
		for(int i=0;i<source.size();i++){
			
			logger.info("getAddressList"+source.get(i).toString());
			ArrayList subAddress=(ArrayList)source.get(i);
			if(subAddress.size()>0)
			{
				logger.info("getAddressList"+subAddress.size());
				vo = new RegistrationTypeVO();
				vo.setRegistrationTypeCode((CommonFunction.checkNull(subAddress.get(0)).toString()));
				vo.setRegistrationTypeDesc((CommonFunction.checkNull(subAddress.get(1)).toString()));
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
			
	}


	@Override
	public ArrayList<Object> getAdressAll(String code, Object pageStatus,String updateFlag) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";
		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getAdressAll()......");
			logger.info("from deal info in update flag>>>>>>>>>>>>> "+updateFlag);
			tableName="cr_deal_address_m";
			try{
				String query="SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC "+
				              "from "+tableName+" c,generic_master m,com_country_m p,com_district_m d,com_state_m s "+
	                           "where m.VALUE=c.ADDRESS_TYPE AND m.GENERIC_KEY='ADDRESS_TYPE' and P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID="+code;
				logger.info("................. from deal table..........................................query "+query);
				ArrayList addressAll = ConnectionDAO.sqlSelect(query);
				logger.info("getAdressAll "+addressAll.size());
				for(int i=0;i<addressAll.size();i++){
					logger.info("getAdressAll...Outer FOR loop "+CommonFunction.checkNull(addressAll.get(i)).toString());
					ArrayList data=(ArrayList)addressAll.get(i);
					if(data.size()>0)
					{
						CustomerSaveVo addr=new CustomerSaveVo();
						addr.setAddId((CommonFunction.checkNull(data.get(0)).toString()));
						addr.setAddr_type((CommonFunction.checkNull(data.get(1)).toString()));
						addr.setAddr1((CommonFunction.checkNull(data.get(2)).toString()));
						addr.setDist((CommonFunction.checkNull(data.get(3)).toString()));
						addr.setState((CommonFunction.checkNull(data.get(4)).toString()));
						addr.setCountry((CommonFunction.checkNull(data.get(5)).toString()));
						list.add(addr);
					}	
					
				}
			}catch (Exception e) {
					e.printStackTrace();
				}
		}
		else
		{
			logger.info("In GCD..,getAdressAll()......");
			if(pageStatus!=null && pageStatus.equals("approve"))
			{
				tableName="com_address_m_temp";
			}
			else 
			{
				tableName="com_address_m";
			}
		
		try{
			String query="SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC "+
				              "from "+tableName+" c,generic_master m,com_country_m p,com_district_m d,com_state_m s"+
	                           " where m.VALUE=c.ADDRESS_TYPE AND m.GENERIC_KEY='ADDRESS_TYPE' and P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID="+code;
			logger.info("...........................................................query "+query);
			ArrayList addressAll = ConnectionDAO.sqlSelect(query);
			logger.info("getAdressAll "+addressAll.size());
			for(int i=0;i<addressAll.size();i++){
				logger.info("getAdressAll...Outer FOR loop "+CommonFunction.checkNull(addressAll.get(i)).toString());
				ArrayList data=(ArrayList)addressAll.get(i);
				if(data.size()>0)
				{
					CustomerSaveVo addr=new CustomerSaveVo();
					addr.setAddId((CommonFunction.checkNull(data.get(0)).toString()));
					addr.setAddr_type((CommonFunction.checkNull(data.get(1)).toString()));
					addr.setAddr1((CommonFunction.checkNull(data.get(2)).toString()));
					addr.setDist((CommonFunction.checkNull(data.get(3)).toString()));
					addr.setState((CommonFunction.checkNull(data.get(4)).toString()));
					addr.setCountry((CommonFunction.checkNull(data.get(5)).toString()));
					list.add(addr);
				
				}
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		}
		return list;	
	}


	@Override
    public ArrayList<Object> getCountryList() {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="select COUNTRY_ID,COUNTRY_DESC from com_country_m";
		logger.info("getCountryList"+query);
		RegistrationTypeVO vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query);
		logger.info("getCountryList"+source.size());
		for(int i=0;i<source.size();i++){
			
			logger.info("getCountryList"+source.get(i).toString());
			ArrayList subCountry=(ArrayList)source.get(i);
			if(subCountry.size()>0)
			{
				logger.info("getCountryList"+subCountry.size());
				vo = new RegistrationTypeVO();
				vo.setRegistrationTypeCode((CommonFunction.checkNull(subCountry.get(0)).toString()));
				vo.setRegistrationTypeDesc((CommonFunction.checkNull(subCountry.get(1)).toString()));
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
		
	}



	
	
	@Override
	public ArrayList getCustomerAddressDetail(String addId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss)
	{
		logger.info("In getCustomerAddressDetail() of CorpotateDAOImpl.");
		ArrayList<CustomerSaveVo> list=new ArrayList<CustomerSaveVo>();
		String tableName="";		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			tableName="cr_deal_address_m";
			try
			{
				String query="";
				query="SELECT distinct a.ADDRESS_TYPE VALUE, a.BPTYPE, a.BPID, a.ADDRESS_LINE1, a.ADDRESS_LINE2, a.ADDRESS_LINE3, " +
				"c.COUNTRY_ID,s.STATE_ID,d.DISTRICT_ID, a.PINCODE, a.PRIMARY_PHONE, a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER, a.FAX, " +
				"a.LANDMARK,a.NO_OF_YEARS, a.NO_OF_MONTHS,  a.ADDRESS_ID,a.COMMUNICATION_ADDRESS,c.COUNTRY_DESC,s.STATE_DESC," +
				"d.DISTRICT_DESC,a.ADDRESS_DETAIL,a.TAHSIL,a.BRANCH_DISTANCE " +
				"FROM "+tableName+" a " +
				"join com_country_m c on(a.COUNTRY=c.COUNTRY_ID and a.ADDRESS_ID="+addId+") " +
				"join com_state_m s on(a.STATE=s.STATE_ID) " +
				"join com_district_m d on(a.DISTRICT=d.DISTRICT_ID)";
				logger.info("In getCustomerAddressDetail() of CorpotateDAOImpl. Select Query  :  "+query);
				ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
				logger.info("getCustomerAddressDetail "+customerAddressDetail.size());
				int size=customerAddressDetail.size();
				for(int i=0;i<size;i++)
				{
					ArrayList data=(ArrayList)customerAddressDetail.get(i);
					if(data.size()>0)
					{
						CustomerSaveVo customerSaveVo=new CustomerSaveVo();					
						customerSaveVo.setAddr_type((CommonFunction.checkNull(data.get(0)).toString()));
						customerSaveVo.setBp_type((CommonFunction.checkNull(data.get(1)).toString()));
						customerSaveVo.setBp_id((CommonFunction.checkNull(data.get(2)).toString()));
						customerSaveVo.setAddr1((CommonFunction.checkNull(data.get(3)).toString()));
						customerSaveVo.setAddr2((CommonFunction.checkNull(data.get(4)).toString()));
						customerSaveVo.setAddr3((CommonFunction.checkNull(data.get(5)).toString()));
						customerSaveVo.setCountry((CommonFunction.checkNull(data.get(19)).toString()));
						customerSaveVo.setState((CommonFunction.checkNull(data.get(20)).toString()));
						customerSaveVo.setDist((CommonFunction.checkNull(data.get(21)).toString()));
						customerSaveVo.setPincode((CommonFunction.checkNull(data.get(9)).toString()));
						customerSaveVo.setPrimaryPhoneNo((CommonFunction.checkNull(data.get(10)).toString()));
						customerSaveVo.setAlternatePhoneNo((CommonFunction.checkNull(data.get(11)).toString()));
						customerSaveVo.setTollfreeNo((CommonFunction.checkNull(data.get(12)).toString()));
						customerSaveVo.setFaxNo((CommonFunction.checkNull(data.get(13)).toString()));
						customerSaveVo.setLandMark((CommonFunction.checkNull(data.get(14)).toString()));
						customerSaveVo.setNoYears((CommonFunction.checkNull(data.get(15)).toString()));
						customerSaveVo.setNoMonths((CommonFunction.checkNull(data.get(16)).toString()));
						customerSaveVo.setAddId((CommonFunction.checkNull(data.get(17)).toString()));
						customerSaveVo.setCommunicationAddress((CommonFunction.checkNull(data.get(18)).toString()));
						customerSaveVo.setTxtCountryCode((CommonFunction.checkNull(data.get(6)).toString()));
						customerSaveVo.setTxtStateCode((CommonFunction.checkNull(data.get(7)).toString()));
						customerSaveVo.setTxtDistCode((CommonFunction.checkNull(data.get(8)).toString()));
						customerSaveVo.setAddDetails((CommonFunction.checkNull(data.get(22)).toString()));
						customerSaveVo.setTahsil((CommonFunction.checkNull(data.get(23)).toString()));
						customerSaveVo.setDistanceBranch((CommonFunction.checkNull(data.get(24)).toString()));
						list.add(customerSaveVo);
					}
	
				}
			}catch(Exception e)
			{e.printStackTrace();}
	}
	else
	{
		if(pageStatus!=null && pageStatus.equals("approve") || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
		{
			if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
					tableName="com_address_m";
			else
					tableName="com_address_m_temp";
		}
		else
			tableName="com_address_m";
		try
		{
			String query="SELECT distinct a.ADDRESS_TYPE VALUE, a.BPTYPE, a.BPID, a.ADDRESS_LINE1, a.ADDRESS_LINE2, a.ADDRESS_LINE3, " +
			"c.COUNTRY_ID,s.STATE_ID,d.DISTRICT_ID, a.PINCODE, a.PRIMARY_PHONE, a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER, a.FAX, " +
			"a.LANDMARK,a.NO_OF_YEARS, a.NO_OF_MONTHS,  a.ADDRESS_ID,a.COMMUNICATION_ADDRESS,c.COUNTRY_DESC,s.STATE_DESC," +
			"d.DISTRICT_DESC,a.ADDRESS_DETAIL,a.TAHSIL,a.BRANCH_DISTANCE " +
			"FROM "+tableName+" a " +
			"join com_country_m c on(a.COUNTRY=c.COUNTRY_ID and a.ADDRESS_ID="+addId+") " +
			"join com_state_m s on(a.STATE=s.STATE_ID) " +
			"join com_district_m d on(a.DISTRICT=d.DISTRICT_ID)";
			 logger.info("In getCustomerAddressDetail() of CorpotateDAOImpl. Select Query  :  "+query);
			 ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
			 logger.info("getCustomerAddressDetail "+customerAddressDetail.size());
			 int size=customerAddressDetail.size();
			 for(int i=0;i<size;i++)
			 {
				ArrayList data=(ArrayList)customerAddressDetail.get(i);
				if(data.size()>0)
				{
					 CustomerSaveVo customerSaveVo=new CustomerSaveVo();			
					 customerSaveVo.setAddr_type((CommonFunction.checkNull(data.get(0)).toString()));
					 customerSaveVo.setBp_type((CommonFunction.checkNull(data.get(1)).toString()));
					 customerSaveVo.setBp_id((CommonFunction.checkNull(data.get(2)).toString()));
					 customerSaveVo.setAddr1((CommonFunction.checkNull(data.get(3)).toString()));							 
					 customerSaveVo.setAddr2((CommonFunction.checkNull(data.get(4)).toString()));
					 customerSaveVo.setAddr3((CommonFunction.checkNull(data.get(5)).toString()));
					 customerSaveVo.setCountry((CommonFunction.checkNull(data.get(19)).toString()));
					 customerSaveVo.setState((CommonFunction.checkNull(data.get(20)).toString()));
					 customerSaveVo.setDist((CommonFunction.checkNull(data.get(21)).toString()));
					 customerSaveVo.setPincode((CommonFunction.checkNull(data.get(9)).toString()));
					 customerSaveVo.setPrimaryPhoneNo((CommonFunction.checkNull(data.get(10)).toString()));
					 customerSaveVo.setAlternatePhoneNo((CommonFunction.checkNull(data.get(11)).toString()));
					 customerSaveVo.setTollfreeNo((CommonFunction.checkNull(data.get(12)).toString()));
					 customerSaveVo.setFaxNo((CommonFunction.checkNull(data.get(13)).toString()));
					 customerSaveVo.setLandMark((CommonFunction.checkNull(data.get(14)).toString()));
					 customerSaveVo.setNoYears((CommonFunction.checkNull(data.get(15)).toString()));
					 customerSaveVo.setNoMonths((CommonFunction.checkNull(data.get(16)).toString()));
					 customerSaveVo.setAddId((CommonFunction.checkNull(data.get(17)).toString()));
					 if((CommonFunction.checkNull(data.get(18)).toString()).equals(""))
					 	 customerSaveVo.setCommunicationAddress("N");
					 else
					 	 customerSaveVo.setCommunicationAddress((CommonFunction.checkNull(data.get(18)).toString()));
					 customerSaveVo.setTxtCountryCode((CommonFunction.checkNull(data.get(6)).toString()));
					 customerSaveVo.setTxtStateCode((CommonFunction.checkNull(data.get(7)).toString()));
					 customerSaveVo.setTxtDistCode((CommonFunction.checkNull(data.get(8)).toString()));
					 customerSaveVo.setAddDetails((CommonFunction.checkNull(data.get(22)).toString()));
					 customerSaveVo.setTahsil((CommonFunction.checkNull(data.get(23)).toString()));
					 customerSaveVo.setDistanceBranch((CommonFunction.checkNull(data.get(24)).toString()));
					 list.add(customerSaveVo);
				}
			 }
	
		}catch(Exception e)
		{e.printStackTrace();}
	
	}
	return list;
}


	public int updateCustomerAddress(Object ob, int id, int addId, String recStatus,String statusCase,String updateFlag,String pageStatuss) 
	{
		logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		int status=0;
		boolean qryStatus= false;
		String tableName="";
		ArrayList qryList = new ArrayList();
		StringBuilder subQuery=new StringBuilder();
		logger.info("Get bp id-"+vo.getBp_id()+"bp id 1-"+vo.getBp_id1());
		try
		{
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				tableName="cr_deal_address_m";
				StringBuilder query= new StringBuilder();
				query.append("update "+tableName+" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, " +
				     "ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, " +
				     "DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, "+
				     "LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,MAKER_ID=?," +
				     "MAKER_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(",TAHSIL=?,BRANCH_DISTANCE=?,COMMUNICATION_ADDRESS=? where ADDRESS_ID=?");
					
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr_type()).trim());
					
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
				else
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
							
				if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr1()).trim());
					
				if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr2()).trim());
					
				if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr3()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
					
				if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());
					
				if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());
					
				if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPincode()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryPhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternatePhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());
				
				if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getFaxNo()).trim());
				
				if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLandMark()).trim());
				
				if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getNoYears()).trim());
				
				if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getNoMonths()).trim());
				
				if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddDetails()).trim());
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()));
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
				
				if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCommunicationAddress()).trim());
				
				if(CommonFunction.checkNull(addId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(addId);
				insertPrepStmtObject.setSql(query.toString());
				logger.info("in updateCustomerAddress() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject.printQuery());
    			qryList.add(insertPrepStmtObject);
    			qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
			else
			{
				if(statusCase!=null && statusCase.length()>0) //&& statusCase.equals("UnApproved"))
					tableName="com_address_m_temp";
				else 
					tableName="com_address_m";
				
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				StringBuilder bufInsUpdSql=new StringBuilder();
				ArrayList qryList2 = new ArrayList();
				ArrayList insertUpdate = new ArrayList();
				bufInsUpdSql.append(" INSERT INTO com_address_hst ");
		        bufInsUpdSql.append(" ( ");
		          
		          bufInsUpdSql.append(" ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, COUNTRY, STATE, DISTRICT,");
		          bufInsUpdSql.append(" PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX, LANDMARK,NO_OF_YEARS,NO_OF_MONTHS,");
				  bufInsUpdSql.append("ADDRESS_DETAIL,COMMUNICATION_ADDRESS ,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ,TAHSIL,BRANCH_DISTANCE ,ADDRESS_ID,MVMT_DATE,MVMT_BY");
				  bufInsUpdSql.append(" ) ");
		          
				  bufInsUpdSql.append(" Select ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, COUNTRY, STATE, DISTRICT,");
		          bufInsUpdSql.append(" PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX, LANDMARK,NO_OF_YEARS,NO_OF_MONTHS,");
				  bufInsUpdSql.append("ADDRESS_DETAIL,COMMUNICATION_ADDRESS ,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ,TAHSIL,BRANCH_DISTANCE,ADDRESS_ID,");
				  bufInsUpdSql.append(dbo);
				  bufInsUpdSql.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				  bufInsUpdSql.append(",'"+CommonFunction.checkNull(vo.getMakerId())+"' ");
				  
		          bufInsUpdSql.append(" FROM com_address_m WHERE ADDRESS_ID ='"+addId+"' ");
		        
		         
		         insertPrepStmtObject.setSql(bufInsUpdSql.toString());
		         logger.info("insert history :"+insertPrepStmtObject.printQuery());
		         insertUpdate.add(insertPrepStmtObject);
		        // qryList2.add(insertPrepStmtObject3);
		         boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);
		         String space="";
				if(tableName.equalsIgnoreCase("com_address_m"))
				{
					subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"'," +
					"MAKER_DATE=");
					subQuery.append(dbo);
					subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					subQuery.append(",AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
					subQuery.append(dbo);
					subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				}
				else
					subQuery.setLength(0);				//subQuery="";     asesh	
				String query="update "+tableName+" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, " +
			     		"ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, " +
			     		"DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, "+
			     		"LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,COMMUNICATION_ADDRESS=? "  +subQuery+",TAHSIL=?,BRANCH_DISTANCE=?  where ADDRESS_ID=?";
				
				
					
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr_type()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
				else
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr1()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr2()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr3()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());
				
				if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPincode()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryPhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternatePhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());
				
				if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getFaxNo()).trim());
				
				if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLandMark()).trim());
				
				if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getNoYears()).trim());
				
				if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getNoMonths()).trim());
				
				if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddDetails()).trim());
				
				if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCommunicationAddress()).trim());
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()));
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
				
				if(CommonFunction.checkNull(addId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(addId);
			
				
				insertPrepStmtObject.setSql(query.toString());
				logger.info("in updateCustomerAddress() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//--------Ritu Code Start----------
				
				StringBuilder query1=new StringBuilder();
				query1.append("update cr_deal_address_m set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, " +
				     "ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, " +
				     "DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, "+
				     "LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,MAKER_ID=?," +
				     "MAKER_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append(",TAHSIL=?,BRANCH_DISTANCE=?,COMMUNICATION_ADDRESS=? where BPID=?");
					
				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
				
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAddr_type()).trim());
					
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getBp_type()).trim());
					
				if(vo.getBp_id()!="")
					insertPrepStmtObject1.addString((vo.getBp_id()).trim());
				else
					insertPrepStmtObject1.addString((vo.getBp_id1()).trim());
							
				if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAddr1()).trim());
					
				if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAddr2()).trim());
					
				if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAddr3()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getTxtCountryCode()).trim());
					
				if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getTxtStateCode()).trim());
					
				if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getTxtDistCode()).trim());
					
				if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPincode()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPrimaryPhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAlternatePhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getTollfreeNo()).trim());
				
				if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getFaxNo()).trim());
				
				if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getLandMark()).trim());
				
				if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getNoYears()).trim());
				
				if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getNoMonths()).trim());
				
				if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAddDetails()).trim());
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()));
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
				
				if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getCommunicationAddress()).trim());
				
//				if(CommonFunction.checkNull(addId).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addInt(addId);
				
				if(vo.getBp_id()!="")
				{
					insertPrepStmtObject1.addString((vo.getBp_id()).trim());
				}
				else
				{
					insertPrepStmtObject1.addString((vo.getBp_id1()).trim());
				}
				
				insertPrepStmtObject1.setSql(query1.toString());
				logger.info("in updateCustomerAddress() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject1.printQuery());
    			qryList.add(insertPrepStmtObject1);
				//--------Ritu Code End------------
				
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			subQuery=null;
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;	
	}



	
	
	@Override
	public String getAddIdCommAdd(String tableName, int id) {
		
		String commAddressCheck="";
		String query1="Select count(*) from (select * from "+tableName+" where BPID="+id+") t where t.COMMUNICATION_ADDRESS='Y'";
		logger.info("Query  :  "+query1);
		commAddressCheck=ConnectionDAO.singleReturn(query1);
			
		return commAddressCheck;
	}



	
	
	@Override
	public String getCountCommAdd(String tableName, int id) {
		
		String addressId = "";
		String query2="Select ADDRESS_ID from (select * from "+tableName+" where BPID="+id+") t where t.COMMUNICATION_ADDRESS='Y'";
		logger.info("Query  :  "+query2);
		addressId =ConnectionDAO.singleReturn(query2);
		return addressId;
	}
	

	public ArrayList<Object> getReferenceAll(String code, Object pageStatus,String updateFlag) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";
		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getReferenceAll()......");
			logger.info("from deal info in update flag>>>>>>>>>>>>> "+updateFlag);
			tableName="cr_deal_reference_m";
		}
		else
		{
			logger.info("In GCD..,getReferenceAll()......");
			tableName="com_reference_m";
		}
		
		try{
			    StringBuilder query=new StringBuilder();
				query.append("select REF_ID, concat(F_NAME,' ',");
				query.append("ISNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER from "+tableName+"  where BPID="+code);
				logger.info("................. from "+tableName+"  table............................query "+query);
				ArrayList addressAll = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getReferenceAll : : :  "+addressAll.size());
				for(int i=0;i<addressAll.size();i++){
					logger.info("getReferenceAll...Outer FOR loop "+CommonFunction.checkNull(addressAll.get(i)).toString());
					ArrayList data=(ArrayList)addressAll.get(i);
					if(data.size()>0)
					{
						CustomerSaveVo addr=new CustomerSaveVo();
						addr.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
						addr.setRefName((CommonFunction.checkNull(data.get(1)).toString()));
						addr.setRelationshipS((CommonFunction.checkNull(data.get(2)).toString()));
						addr.setKnowingSince((CommonFunction.checkNull(data.get(3)).toString()));
						addr.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(4)).toString()));
						addr.setAlternateRefPhNo((CommonFunction.checkNull(data.get(5)).toString()));
						list.add(addr);
					}	
					
				}
			}catch (Exception e) {
					e.printStackTrace();
			}
		
		return list;	
	}
	
	
	public ArrayList<Object> getRefrenceDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag) {
		
		 String tableName="";
		 logger.info("In getRefrenceDetails() of CorpotateDAOImpl");
		 ArrayList<Object> list=new ArrayList<Object>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_reference_m";
			 try
			 {
				 	StringBuilder query=new StringBuilder();
				 	query.append("select REF_ID, concat(F_NAME,' ',");
				 	query.append("ISNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER from "+tableName+" where BPID="+code);
					logger.info("getRefrenceDetails query : : : : : : :  "+query );
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getRefrenceDetails "+addressDetails.size());
					for(int i=0;i<addressDetails.size();i++){
						logger.info("getRefrenceDetails...FOR loop "+CommonFunction.checkNull(addressDetails.get(i)).toString());
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setRefName((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setRelationshipS((CommonFunction.checkNull(data.get(2)).toString()));
							addr.setKnowingSince((CommonFunction.checkNull(data.get(3)).toString()));
							addr.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(4)).toString()));
							addr.setAlternateRefPhNo((CommonFunction.checkNull(data.get(5)).toString()));
							list.add(addr);
						}
					}
				 }catch(Exception e)
				{e.printStackTrace();}	
				
			}
			else
			{
				if(updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
				{
					if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
						tableName="com_reference_m";
					else
						tableName="com_reference_m_temp";
				}
				else 
					tableName="com_reference_m";
				try
				{
					StringBuilder query=new StringBuilder();
					query.append("select REF_ID, concat(F_NAME,' ',");
					query.append("ISNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER from "+tableName+" where BPID="+code);
					logger.info("getRefrenceDetails query : : : : : : :  "+query );
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getRefrenceDetails "+addressDetails.size());
					for(int i=0;i<addressDetails.size();i++){
						logger.info("getRefrenceDetails...FOR loop "+CommonFunction.checkNull(addressDetails.get(i)).toString());
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setRefName((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setRelationshipS((CommonFunction.checkNull(data.get(2)).toString()));
							addr.setKnowingSince((CommonFunction.checkNull(data.get(3)).toString()));
							addr.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(4)).toString()));
							addr.setAlternateRefPhNo((CommonFunction.checkNull(data.get(5)).toString()));
							list.add(addr);
						}
					}
				 }catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}

	
	public ArrayList getIndReferenceDetail(String addId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss)
	{
	ArrayList<CustomerSaveVo> list=new ArrayList<CustomerSaveVo>();
	String tableName="";
	
	if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
	{
		logger.info("In Credit Processing , Customer Entry., getIndReferenceDetail()......");
		tableName="cr_deal_reference_m";
	}
	else
	{
		logger.info("In GCD., getIndReferenceDetail()......");
		tableName="com_reference_m";
	}
		try{
		String query="";
		query="select REF_ID, BPID, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER FROM "+tableName+ " WHERE  REF_ID="+addId;
		logger.info("getIndReferenceDetail query................. "+query);
		ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
		logger.info("getIndReferenceDetail "+customerAddressDetail.size());
		for(int i=0;i<customerAddressDetail.size();i++){
			logger.info("getIndReferenceDetail...FOR loop "+CommonFunction.checkNull(customerAddressDetail.get(i)).toString());
			ArrayList data=(ArrayList)customerAddressDetail.get(i);
			if(data.size()>0)
			{
				CustomerSaveVo customerSaveVo=new CustomerSaveVo();       
				customerSaveVo.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
				customerSaveVo.setBpId((CommonFunction.checkNull(data.get(1)).toString()));
				customerSaveVo.setFirstName((CommonFunction.checkNull(data.get(2)).toString()));
				customerSaveVo.setMiddleName((CommonFunction.checkNull(data.get(3)).toString()));
				customerSaveVo.setLastName((CommonFunction.checkNull(data.get(4)).toString()));
				customerSaveVo.setRelationshipS((CommonFunction.checkNull(data.get(5)).toString()));
				customerSaveVo.setKnowingSince((CommonFunction.checkNull(data.get(6)).toString()));
				customerSaveVo.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(7)).toString()));
				customerSaveVo.setAlternateRefPhNo((CommonFunction.checkNull(data.get(8)).toString()));
				list.add(customerSaveVo);
			}
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
}



    public ArrayList<Object> getBusinessSegmentList(){
    	
    	ArrayList<Object> list=new ArrayList<Object>();
    	try{
    	
    	String query="select value,description from generic_master where GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A'";
    	logger.info("in getBusinessSegmentList() of CorpotateDAOImpl Query :  "+query);
    	RegistrationTypeVO vo = null;
    	ArrayList source = ConnectionDAO.sqlSelect(query);
    	logger.info("getBusinessSegmentList"+source.size());
    	for(int i=0;i<source.size();i++){
    		
    		logger.info("getBusinessSegmentList"+source.get(i).toString());
    		ArrayList subRegistration=(ArrayList)source.get(i);
    		if(subRegistration.size()>0)
    		{
    			logger.info("getBusinessSegmentList"+subRegistration.size());
    			vo = new RegistrationTypeVO();
    			vo.setBusinessSegmentCode((CommonFunction.checkNull(subRegistration.get(0)).toString()));
    			vo.setBusinessSegmentDesc((CommonFunction.checkNull(subRegistration.get(1)).toString()));
    			list.add(vo);
    		}
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return list;
    	
    }




    public ArrayList<Object> getContitutionList(){
    	
    	ArrayList<Object> list=new ArrayList<Object>();
    	try{
    	
    	String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A'";
    	logger.info("in getContitutionList() of CorpotateDAOImpl Query :  "+query);
    	ConstitutionVO vo = null;
    	ArrayList source = ConnectionDAO.sqlSelect(query);
    	logger.info("getCustomerCategoryList"+source.size());
    	for(int i=0;i<source.size();i++){
    		
    		logger.info("getCustomerCategoryList"+source.get(i).toString());
    		ArrayList subConstitution=(ArrayList)source.get(i);
    		if(subConstitution.size()>0)
    		{
    			logger.info("getCustomerCategoryList"+subConstitution.size());
    			vo = new ConstitutionVO();
    			vo.setContitutionCode((CommonFunction.checkNull(subConstitution.get(0)).toString()));
    			vo.setConstitution((CommonFunction.checkNull(subConstitution.get(1)).toString()));
    			list.add(vo);
    		}
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return list;
    		
    }




	public ArrayList<Object> getCorporateDetailAll(int code,Object pageStatus,String statusCase,String updateInMaker,String updateFlag,String pageStatuss)
	{
		logger.info("In getCorporateDetailAll() of CorpotateDAOImpl.");
		ArrayList<Object> list=new ArrayList<Object>();	
		String tableName="";		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getCorporateDetailAll()......");
			tableName="cr_deal_customer_m";
			try
			{
				StringBuilder query1=new StringBuilder();	//String query1="";
				String custStatus="";
				custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+code);
				query1.append("select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
				query1.append(dbo);
				query1.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON," +
							" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC from "+tableName+" c,gcd_group_m g,com_industry_m i,com_sub_industry_m si where customer_id="+code+" and c.CUSTOMER_GROUP_ID=g.GROUP_ID and c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID and si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY");
      
				logger.info("in getCorporateDetailAll() of CorpotateDAOImpl Qiery : "+query1);
				ArrayList corporateDetailAll = ConnectionDAO.sqlSelect(query1.toString());
				logger.info("getCorporateDetailAll "+corporateDetailAll.size());
				int size=corporateDetailAll.size();
				for(int i=0;i<size;i++)
				{
					ArrayList data=(ArrayList)corporateDetailAll.get(i);
					if(data.size()>0)	
					{
						CorporateDetailsVO show=new CorporateDetailsVO();
						show.setCorporateCode((CommonFunction.checkNull(data.get(0)).toString()));
						if(!CommonFunction.checkNull(data.get(1).toString()).equalsIgnoreCase(""))
							show.setCorporateName((CommonFunction.checkNull(data.get(1)).toString()));
						else
							show.setCorporateName((CommonFunction.checkNull(data.get(2)).toString()));
						show.setCorporateCategory((CommonFunction.checkNull(data.get(11)).toString()));
						show.setIncorporationDate((CommonFunction.checkNull(data.get(6))));
						show.setConstitution((CommonFunction.checkNull(data.get(12)).toString()));
						show.setRegistrationType((CommonFunction.checkNull(data.get(8)).toString()));
						show.setRegistrationNo((CommonFunction.checkNull(data.get(9)).toString()));
						show.setPan((CommonFunction.checkNull(data.get(7)).toString()));
						show.setVatRegNo((CommonFunction.checkNull(data.get(10)).toString()));
						show.setLbxIndustry((CommonFunction.checkNull(data.get(14)).toString()));
						show.setLbxSubIndustry((CommonFunction.checkNull(data.get(15)).toString()));
						show.setBusinessSegment((CommonFunction.checkNull(data.get(13)).toString()));
						show.setInstitutionEmail((CommonFunction.checkNull(data.get(16)).toString()));
						show.setWebAddress((CommonFunction.checkNull(data.get(17)).toString()));
						show.setReferredBy((CommonFunction.checkNull(data.get(18)).toString()));
						show.setBlackListed((CommonFunction.checkNull(data.get(19)).toString()));
						show.setReasonForBlackListed((CommonFunction.checkNull(data.get(20)).toString()));
						show.sethGroupId((CommonFunction.checkNull(data.get(21)).toString()));
						show.setGroupName((CommonFunction.checkNull(data.get(22)).toString()));
						show.setIndustry((CommonFunction.checkNull(data.get(23)).toString()));
						show.setSubIndustry((CommonFunction.checkNull(data.get(24)).toString()));
						list.add(show);
					}			
				}
			}catch(Exception e)
			{e.printStackTrace();}
		}		
		else
		{
			if(pageStatus!=null && pageStatus.equals("approve") || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || statusCase.equalsIgnoreCase("UnApproved"))
				tableName="gcd_customer_m_temp";
			else
				tableName="gcd_customer_m";
			try
			{
				StringBuilder query1=new StringBuilder();		//String query1
				query1.append("select  c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
				query1.append(dbo);
				query1.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,c.CUSTOMER_STATUS," +
					" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC from "+tableName+" c,gcd_group_m g,com_industry_m i,com_sub_industry_m si where customer_id="+code+" and c.GROUP_ID=g.GROUP_ID and c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID and si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY");
				logger.info("in getCorporateDetailAll() of CorpotateDAOImpl Qiery : "+query1);
                ArrayList corporateDetailAll = ConnectionDAO.sqlSelect(query1.toString());
                logger.info("getCorporateDetailAll "+corporateDetailAll.size());
                int size=corporateDetailAll.size();
                for(int i=0;i<size;i++)
                {
					ArrayList data=(ArrayList)corporateDetailAll.get(i);
					if(data.size()>0)	
					{
						CorporateDetailsVO show=new CorporateDetailsVO();
						show.setCorporateCode((CommonFunction.checkNull(data.get(0))));
						show.setCorporateName((CommonFunction.checkNull(data.get(1))));
						show.setCorporateCategory((CommonFunction.checkNull(data.get(11))));
						show.setIncorporationDate((CommonFunction.checkNull(data.get(6))));
						show.setConstitution((CommonFunction.checkNull(data.get(12))));
						show.setRegistrationType((CommonFunction.checkNull(data.get(8))));
						show.setRegistrationNo((CommonFunction.checkNull(data.get(9))));
						show.setPan((CommonFunction.checkNull(data.get(7))));
						show.setVatRegNo((CommonFunction.checkNull(data.get(10))));
						show.setLbxIndustry((CommonFunction.checkNull(data.get(14))));
						show.setLbxSubIndustry((CommonFunction.checkNull(data.get(15))));
						show.setBusinessSegment((CommonFunction.checkNull(data.get(13))));
						show.setInstitutionEmail((CommonFunction.checkNull(data.get(16))));
						show.setWebAddress((CommonFunction.checkNull(data.get(17))));
						show.setReferredBy((CommonFunction.checkNull(data.get(18))));
						show.setBlackListed((CommonFunction.checkNull(data.get(19))));
						show.setReasonForBlackListed((CommonFunction.checkNull(data.get(20))));
						show.setPagestatus((CommonFunction.checkNull(data.get(21))));
						show.sethGroupId((CommonFunction.checkNull(data.get(22))));
						show.setGroupName((CommonFunction.checkNull(data.get(23))));
						show.setIndustry((CommonFunction.checkNull(data.get(24))));
						show.setSubIndustry((CommonFunction.checkNull(data.get(25))));
						list.add(show);
					}
                }		
			}catch(Exception e)
			{e.printStackTrace();}
		}
		return list;
	}




	public ArrayList<Object> getIndustryList(){
	
	ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="SELECT INDUSTRY_ID,INDUSTRY_DESC from com_industry_m";
		logger.info("in getIndustryList() of CorpotateDAOImpl Query :  "+query);
		IndustryVO vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query);
		logger.info("getIndustryList"+source.size());
		for(int i=0;i<source.size();i++){
			
			logger.info("getIndustryList"+source.get(i).toString());
			ArrayList subIndustry=(ArrayList)source.get(i);
			if(subIndustry.size()>0)
			{
				logger.info("getIndustryList"+subIndustry.size());
				vo = new IndustryVO();
				vo.setIndustryCode((CommonFunction.checkNull(subIndustry.get(0)).toString()));
				vo.setIndustryName((CommonFunction.checkNull(subIndustry.get(1)).toString()));
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}



	public ArrayList<Object> getRegistrationTypeList(){
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_REG_TYPE' and rec_status ='A'";
	logger.info("in getRegistrationTypeList() of CorpotateDAOImpl Query :  "+query);
	RegistrationTypeVO vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getRegistrationTypeList"+source.size());
	for(int i=0;i<source.size();i++){
		
		logger.info("getRegistrationTypeList"+source.get(i).toString());
		ArrayList subRegistration=(ArrayList)source.get(i);
		if(subRegistration.size()>0)
		{
			logger.info("getRegistrationTypeList"+subRegistration.size());
			vo = new RegistrationTypeVO();
			vo.setRegistrationTypeCode((CommonFunction.checkNull(subRegistration.get(0)).toString()));
			vo.setRegistrationTypeDesc((CommonFunction.checkNull(subRegistration.get(1)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
	
}


	
	public ArrayList<Object> getRoleList(String dealId) {
		logger.info("id in actiongetRoleList");
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In , credit processing , getRoleList() *************************************    :"+dealId);
		try{
//		String query=" SELECT DEAL_CUSTOMER_ROLE_ID,DEAL_CUSTOMER_ROLE_TYPE, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER,"+
//					" CUSTOMER_FNAME,m.description from cr_deal_customer_role c "+
//					" left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
//					" left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID"+
//					" where DEAL_ID="+dealId;
		
		String query=" SELECT DEAL_CUSTOMER_ROLE_ID, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER,"+
		" CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT from cr_deal_customer_role c "+
		" left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
		" left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID"+
		" where DEAL_ID='"+dealId+"'";
		logger.info("getRoleList:Query "+query);		
		CreditProcessingCustomerEntryVo vo=null;
		ArrayList roleList = ConnectionDAO.sqlSelect(query);
		logger.info("getRoleList "+roleList.size());
		for(int i=0;i<roleList.size();i++){
			logger.info("getRoleList...FOR loop "+CommonFunction.checkNull(roleList.get(i)).toString());
			ArrayList data=(ArrayList)roleList.get(i);
			if(data.size()>0)
			{
				vo =new CreditProcessingCustomerEntryVo();
				vo.setRoleId((CommonFunction.checkNull(data.get(0)).toString()));
				vo.setApplicantType((CommonFunction.checkNull(data.get(5)).toString()));
				if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("C"))
				{
					vo.setApplicantCategory("CORPORATE");
				}
				else if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("I"))
				{
					vo.setApplicantCategory("INDIVIDUAL");
				}
				
				if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("N"))
				{
					vo.setExistingCustomer("NO");
				}
				else if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("Y"))
				{
					vo.setExistingCustomer("YES");
				}
				
				vo.setCustomerName((CommonFunction.checkNull(data.get(4)).toString()));
				vo.setCustomerId((CommonFunction.checkNull(data.get(2)).toString()));
				
				if(!(CommonFunction.checkNull(data.get(6)).toString()).equalsIgnoreCase(""))
	    		{
	    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
	    			vo.setGuaranteeAmount(myFormatter.format(reconNum));
	    		}	
				
				//vo.setGuaranteeAmount();
				vo.setFlagForUpdate("updateFlag");
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}



	
	
	@Override
	public String getCustomerCount(String tableName, String panNo) {
		String custCount=null;
		custCount = ConnectionDAO.singleReturn("Select CUSTOMER_ID from "+tableName+" where CUSTMER_PAN='"+panNo+"'");
		logger.info("custCount :-"+custCount);
		return custCount;
	}



	
	
	@Override
	public String getRegNoCheck(String tableName, String regNo) {
		String getVatRegNo=null;
		getVatRegNo = ConnectionDAO.singleReturn("Select CUSTOMER_ID from "+tableName+" where CUSTOMER_VAT_NO='"+regNo+"'");
		logger.info("custCount :-"+getVatRegNo);
		return getVatRegNo;
	}



	
	
	@Override
	public String getVatRegNo(String tableName, String regNo) {
		String getVatRegNo=null;
		getVatRegNo = ConnectionDAO.singleReturn("Select CUSTOMER_ID from "+tableName+" where CUSTOMER_REGISTRATION_NO='"+regNo+"'");
		logger.info("custCount :-"+getVatRegNo);
		return getVatRegNo;
	}


	public ArrayList<Object> getStakeDetails(int code,Object pageStatus,String statusCase,String updateFlag,String updateInMaker) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";

		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry.,getStakeDetails().......");
			tableName="cr_deal_customer_stakeholder_m";
			try{
				StringBuilder query=new StringBuilder();		//String query
				query.append("SELECT distinct cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, s.STAKEHOLDER_ID, " +
				"cs.STAKEHOLDER_PERCENTAGE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, " +
				"cs.STAKEHOLDER_IDENTIFICATION_NO, p.DESCRIPTION,");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"') , cs.ELIGIBLE_FOR_COMPUTATION," +
				" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL," +
				" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE" +
				" FROM "+tableName+" cs, com_stakeholder_m s, generic_master p" +
				" WHERE cs.STAKEHOLDER_TYPE=s.STAKEHOLDER_ID and cs.STAKEHOLDER_POSITION=p.VALUE "+
				"and cs.CUSTOMER_ID="+code);
				logger.info("query "+query);
				ArrayList stakeDetails = ConnectionDAO.sqlSelect(query.toString());
				
				logger.info("getStakeDetails "+stakeDetails.size());
				for(int i=0;i<stakeDetails.size();i++){
					logger.info("getStakeDetails...FOR loop "+CommonFunction.checkNull(stakeDetails.get(i)).toString());
					ArrayList data=(ArrayList)stakeDetails.get(i);
					if(data.size()>0)
					{
						StakeHolderVo stakeHolderVo = new StakeHolderVo();
						stakeHolderVo.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
						stakeHolderVo.setSex((CommonFunction.checkNull(data.get(1)).toString()));
						stakeHolderVo.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
						stakeHolderVo.setHolderType((CommonFunction.checkNull(data.get(3)).toString()));
						stakeHolderVo.setHoldingPerc((CommonFunction.checkNull(data.get(4)).toString()));
						stakeHolderVo.setDob((CommonFunction.checkNull(data.get(5)).toString()));
						stakeHolderVo.setTotalExp((CommonFunction.checkNull(data.get(6)).toString()));
						stakeHolderVo.setDinNo((CommonFunction.checkNull(data.get(7)).toString()));
						stakeHolderVo.setIdNo((CommonFunction.checkNull(data.get(8)).toString()));
						stakeHolderVo.setPosition((CommonFunction.checkNull(data.get(9)).toString()));
						stakeHolderVo.setDoj((CommonFunction.checkNull(data.get(10)).toString()));
						stakeHolderVo.setCompute((CommonFunction.checkNull(data.get(11)).toString()));
						stakeHolderVo.setPrimaryPhone((CommonFunction.checkNull(data.get(12)).toString()));
						stakeHolderVo.setAlternatePhone((CommonFunction.checkNull(data.get(13)).toString()));
						stakeHolderVo.setPrimaryEmail((CommonFunction.checkNull(data.get(14)).toString()));
						stakeHolderVo.setAlternateEmail((CommonFunction.checkNull(data.get(15)).toString()));
						stakeHolderVo.setWebsite((CommonFunction.checkNull(data.get(16)).toString()));
						list.add(stakeHolderVo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else
		{
			logger.info("In GCD.,getStakeDetails().......");
			if(pageStatus!=null || updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_stakeholder_m";
				}
				else
				{
					tableName="customer_stakeholder_m_temp";
				}
				
			}
			else 
			{
				tableName="customer_stakeholder_m";
			}
			
		try{
			String query="SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, p.DESCRIPTION, " +
			"cs.STAKEHOLDER_PERCENTAGE, cs.STAKEHOLDER_DOB, cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, " +
			"cs.STAKEHOLDER_IDENTIFICATION_NO, p.DESCRIPTION, cs.STAKEHOLDER_JOINING_DATE, cs.ELIGIBLE_FOR_COMPUTATION," +
			" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL," +
			" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE" +
			" FROM "+tableName+" cs, generic_master p" +
			" WHERE p.GENERIC_KEY='DESIGNATION' and cs.STAKEHOLDER_POSITION=p.VALUE " +
			"and cs.CUSTOMER_ID="+code;
			ArrayList stakeDetails = ConnectionDAO.sqlSelect(query);
			logger.info("getStakeDetails "+stakeDetails.size());
			for(int i=0;i<stakeDetails.size();i++){
				logger.info("getStakeDetails...FOR loop "+CommonFunction.checkNull(stakeDetails.get(i)).toString());
				ArrayList data=(ArrayList)stakeDetails.get(i);
				if(data.size()>0)
				{
					StakeHolderVo stakeHolderVo = new StakeHolderVo();
					stakeHolderVo.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
					stakeHolderVo.setSex((CommonFunction.checkNull(data.get(1)).toString()));
					stakeHolderVo.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
					stakeHolderVo.setHolderType((CommonFunction.checkNull(data.get(3)).toString()));
					stakeHolderVo.setHoldingPerc((CommonFunction.checkNull(data.get(4)).toString()));
					stakeHolderVo.setDob((CommonFunction.checkNull(data.get(5)).toString()));
					stakeHolderVo.setTotalExp((CommonFunction.checkNull(data.get(6)).toString()));
					stakeHolderVo.setDinNo((CommonFunction.checkNull(data.get(7)).toString()));
					stakeHolderVo.setIdNo((CommonFunction.checkNull(data.get(8)).toString()));
					stakeHolderVo.setPosition((CommonFunction.checkNull(data.get(9)).toString()));
					stakeHolderVo.setDoj((CommonFunction.checkNull(data.get(10)).toString()));
					stakeHolderVo.setCompute((CommonFunction.checkNull(data.get(11)).toString()));
					stakeHolderVo.setPrimaryPhone((CommonFunction.checkNull(data.get(12)).toString()));
					stakeHolderVo.setAlternatePhone((CommonFunction.checkNull(data.get(13)).toString()));
					stakeHolderVo.setPrimaryEmail((CommonFunction.checkNull(data.get(14)).toString()));
					stakeHolderVo.setAlternateEmail((CommonFunction.checkNull(data.get(15)).toString()));
					stakeHolderVo.setWebsite((CommonFunction.checkNull(data.get(16)).toString()));
					list.add(stakeHolderVo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return list;	
	}

	
	public ArrayList<Object> getHolderTypeList() {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
  		try{
  		
  		String query="select value,description from generic_master where generic_key='STAKEHOLDER_TYPE' and rec_status ='A'";
  		logger.info("getHolderList"+query);
  		StakeHolderTypeVo vo = null;
  		ArrayList source = ConnectionDAO.sqlSelect(query);
  		logger.info("getHolderList"+source.size());
  		for(int i=0;i<source.size();i++){
  			
  			logger.info("getHolderList"+source.get(i).toString());
  			ArrayList subHolder=(ArrayList)source.get(i);
  			if(subHolder.size()>0)
  			{
  				logger.info("getHolderList"+subHolder.size());
  				vo = new StakeHolderTypeVo();
  				vo.setSTAKEHOLDER_ID((CommonFunction.checkNull(subHolder.get(0)).toString()));
  				vo.setSTAKEHOLDER_DESC((CommonFunction.checkNull(subHolder.get(1)).toString()));
  				list.add(vo);
  			}
  		}
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  		return list;
		
		
	}

	
	public ArrayList<Object> getSalutation() {
			
			
			ArrayList<Object> list=new ArrayList<Object>();
	  		try{
	  		
	  		String query="select value,description from generic_master where GENERIC_KEY='SALUTATION' and rec_status ='A'";
	  		logger.info("getSalutation"+query);
	  		StakeHolderTypeVo vo = null;
	  		ArrayList source = ConnectionDAO.sqlSelect(query);
	  		logger.info("getSalutation"+source.size());
	  		for(int i=0;i<source.size();i++){
	  			
	  			logger.info("getSalutation"+source.get(i).toString());
	  			ArrayList subHolder=(ArrayList)source.get(i);
	  			if(subHolder.size()>0)
	  			{
	  				logger.info("getSalutation"+subHolder.size());
	  				vo = new StakeHolderTypeVo();
	  				vo.setSALUTATION_ID((CommonFunction.checkNull(subHolder.get(0)).toString()));
	  				vo.setSALUTATION_DESC((CommonFunction.checkNull(subHolder.get(1)).toString()));
	  				list.add(vo);
	  			}
	  		}
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}
	  		return list;
			
			
	}
	
	
	public ArrayList<Object> getPositionList() {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
			try{
			
			String query="select value,description from generic_master where generic_key='DESIGNATION' and rec_status ='A'";
			logger.info("getPositionList"+query);
			StakePositionVo vo = null;
			ArrayList source = ConnectionDAO.sqlSelect(query);
			logger.info("getPositionList"+source.size());
			for(int i=0;i<source.size();i++){
				
				logger.info("getPositionList"+source.get(i).toString());
				ArrayList subPosition=(ArrayList)source.get(i);
				if(subPosition.size()>0)
				{
					logger.info("getPositionList"+subPosition.size());
					vo = new StakePositionVo();
					vo.setPosition_code((CommonFunction.checkNull(subPosition.get(0)).toString()));
					vo.setPosition_name((CommonFunction.checkNull(subPosition.get(1)).toString()));
					list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		
	}	
	
	
	public ArrayList<Object> getStakeDetailsAll(int id,String statusCase,String updateInMaker,String pageStatuss,String updateFlag) {
	
	 ArrayList<Object> list=new ArrayList<Object>();
	 String tableName="";
	 
	 if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
		 logger.info("In Credit Processing , Customer Entry.,getStakeDetailsAll().......");
			tableName="cr_deal_customer_stakeholder_m";
			
			try{
				StringBuilder query=new StringBuilder();		//String query;
				query.append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, "+ 
					" ");
				query.append(dbo);
				query.append("DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
						" FROM "+tableName+" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' "+
						" where c.CUSTOMER_ID="+id);
				ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query.toString());
				logger.info("query "+query);
			//added by neeraj tripathi start space
				StringBuilder perQuery=new StringBuilder();
				perQuery.append("select sum(");
				perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID="+id);
			 	logger.info("Query for getting total percentage : "+perQuery);
			 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
			//end space
				logger.info("getStakeDetailsAll "+stakeDetailsAll.size());
				for(int i=0;i<stakeDetailsAll.size();i++){
					logger.info("getStakeDetailsAll...FOR loop "+CommonFunction.checkNull(stakeDetailsAll.get(i)).toString());
					ArrayList data=(ArrayList)stakeDetailsAll.get(i);
					if(data.size()>0)
					{				
						StakeHolderVo addr=new StakeHolderVo();
						addr.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
						addr.setPosition((CommonFunction.checkNull(data.get(1)).toString()));
						addr.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
						
						if(!(CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(3)).toString()));
			    			addr.setHoldingPerc(myFormatter.format(reconNum));
			    		}	
					
						addr.setDoj((CommonFunction.checkNull(data.get(4)).toString()));
						addr.setPercentage(percentage);
						list.add(addr);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			logger.info("In GCD.,getStakeDetailsAll().......");
			if(updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_stakeholder_m";
				}
				else
				{
					tableName="customer_stakeholder_m_temp";
				}
			}
			else
			{
				tableName="customer_stakeholder_m";
			}
			
		
		try{
//			String query="SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
//			"  FROM "+tableName+" c,generic_master d where d.VALUE=c.stakeholder_position" +
//			"  and d.GENERIC_KEY='DESIGNATION' and c.CUSTOMER_ID="+id;
			
			StringBuilder query=new StringBuilder();		//String query;
			query.append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, "+ 
			" ");
			query.append(dbo);
			query.append("DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
				" FROM "+tableName+" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' "+
				" where c.CUSTOMER_ID="+id);
			
			logger.info("Query for getting stack holder list  : "+query);
			ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getStakeDetailsAll "+stakeDetailsAll.size());
			//added by neeraj tripathi start space
			StringBuilder perQuery=new StringBuilder();
			perQuery.append("select sum(");
			perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from "+tableName+" where CUSTOMER_ID="+id);
		 	logger.info("Query for getting total percentage : "+perQuery);
		 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
		//end space
			for(int i=0;i<stakeDetailsAll.size();i++){
				logger.info("getStakeDetailsAll...FOR loop "+CommonFunction.checkNull(stakeDetailsAll.get(i)).toString());
				ArrayList data=(ArrayList)stakeDetailsAll.get(i);
				if(data.size()>0)
				{		
					StakeHolderVo addr=new StakeHolderVo();
					addr.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
					addr.setPosition((CommonFunction.checkNull(data.get(1)).toString()));
					addr.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
					
					if(!(CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase(""))
		    		{
		    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(3)).toString()));
		    			addr.setHoldingPerc(myFormatter.format(reconNum));
		    		}	
					
					//addr.setHoldingPerc((CommonFunction.checkNull(data.get(3)).toString()));
					addr.setDoj((CommonFunction.checkNull(data.get(4)).toString()));
					addr.setPercentage(percentage);
					list.add(addr);
				}
			}
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
return list;
}





	@Override
	public String getDOBfromDCM(int id) {
		
		StringBuilder incDateQ=new StringBuilder();		//String incDateQ
		incDateQ.append("select ");
		incDateQ.append(dbo);
		incDateQ.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"')  from cr_deal_customer_m where CUSTOMER_ID="+id);
	 	String incDate=ConnectionDAO.singleReturn(incDateQ.toString());
		return incDate;
	}
	
	
	
	@Override
	public String getDOBfromGcdCM(int id) {
		StringBuilder incDateQ=new StringBuilder();		//String incDateQ
		incDateQ.append("select ");
		incDateQ.append(dbo);
		incDateQ.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"') from gcd_customer_m where CUSTOMER_ID="+id);
		String incDate=ConnectionDAO.singleReturn(incDateQ.toString());
		return incDate;
	}
	
	
	
	
	
	@Override
	public String getStackHolderDin(String tableName, String dinNo) {
		String result = ConnectionDAO.singleReturn("Select distinct STAKEHOLDER_NAME from "+tableName+" where STAKEHOLDER_DIN='"+dinNo+"'");
		return result;
	}
	
	
	
	@Override
	public String getStackHolderID(String tableName, String id) {
		String result = ConnectionDAO.singleReturn("Select distinct STAKEHOLDER_NAME from "+tableName+" where STAKEHOLDER_IDENTIFICATION_NO='"+id+"'");
		return result;
	}
	
	
	
	@Override
	public String getCustomerWithDoj(String tableName,String getDoj, int id) {
		// asesh space start
		StringBuilder query=new StringBuilder();
		query.append("select ");
		query.append("TOP 1 ");
		query.append("CUSTOMER_ID from "+tableName+" where ");
		query.append(dbo);
		query.append("STR_TO_DATE('"+getDoj+"','%d-%m-%Y') >= (select CUSTOMER_DOB from "+tableName+" where CUSTOMER_ID="+id+") ");
		// end asesh
		String dojCheck = ConnectionDAO.singleReturn(query.toString());
		return dojCheck;
	}
	
	
	public boolean saveStakeHolder(Object ob,int id) {
	
	StakeHolderVo vo = (StakeHolderVo)ob;
	//logger.info("value of make id is   -----"+vo.getMakerId());
	//logger.info("value of make date is   -----"+vo.getMakerDate());
	boolean status=false;
	ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			if((vo.getPageStat()!=null && vo.getPageStat().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
				logger.info("In Credit Processing , Customer Entry.,saveStakeHolder().......");
			

		logger.info("In insert saveStakeHolder");
		StringBuffer bufInsSql =	new StringBuffer();
		bufInsSql.append("insert into cr_deal_customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,REC_STATUS,MAKER_ID,MAKER_DATE,CUSTOMER_ID,AUTHOR_ID,AUTHOR_DATE)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)) ");
		
		
		if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getSex()).trim());
		
		if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHolderName()).trim());
		
		if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHolderType()).trim());
		
		if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHoldingPerc()).trim());
		
		if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDob()).trim());
		
		if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
		    insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getTotalExp()).trim());
		
		if(CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDinNo()).trim());
		
		if(CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getIdNo()).trim());
		
		if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPosition()).trim());
		
		if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDoj()).trim());
		
		if(CommonFunction.checkNull(vo.getCompute()).trim().equalsIgnoreCase("on"))
			insertPrepStmtObject.addString("Y");
		else
			insertPrepStmtObject.addString("N");
		
		if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPrimaryPhone()).trim());
		
		if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getAlternatePhone()).trim());
		
		if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPrimaryEmail()).trim());
		
		if(CommonFunction.checkNull(vo.getAlternateEmail()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getAlternateEmail()));
		
		if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getWebsite()).trim());
		
		insertPrepStmtObject.addString("A");
		//---------------------------------------------------------
		if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
		
		insertPrepStmtObject.addInt(id);
		
		if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		
	
		
		if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
		//---------------------------------------------------------
		
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
	        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    logger.info("In saveCustomer......................"+status);
			}	
				
		else
		{
			
			logger.info("In GCD.,saveStakeHolder().......");
			int maxId=0;
	    	
			StringBuffer bufInsSql =	new StringBuffer();
			int checkTable=0;
	    	
	    	if(!vo.getStatusCase().equals(""))
	    	{
	    			    		
	    		String query3="Select max(STAKEHOLDER_ID) from customer_stakeholder_m_temp  WITH (ROWLOCK) ";
				 String maxid = ConnectionDAO.singleReturn(query3);
				 if(maxid==null)
				 {
					 maxId=1;
				 }
				 else
				 {
					 maxId=Integer.parseInt(maxid)+1;
				 }
				 
				 logger.info("maxId : "+maxId);
				 checkTable++;
				 bufInsSql.append("insert into customer_stakeholder_m_temp(STAKEHOLDER_ID,STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,CUSTOMER_ID)");
				 bufInsSql.append(" values ( ");
	    		 bufInsSql.append(" ?," );
	    		
	    	}
	    	else
	    	{
	    		bufInsSql.append("insert into customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,CUSTOMER_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
	    	}
	
		
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		
		if(checkTable==0){
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");
		}
		else{
			bufInsSql.append(" ? )" );
		}
		if(!vo.getStatusCase().equals(""))
    	{
			insertPrepStmtObject.addInt(maxId);
    	}
		
		if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getSex()).trim());
		
		if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHolderName()).trim());
		
		if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHolderType()).trim());
		
		if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getHoldingPerc()).trim());
		
		if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDob()).trim());
		
		if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
		    insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getTotalExp()).trim());
		
		if(CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDinNo()).trim());
		
		if(CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getIdNo()).trim());
		
		if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPosition()).trim());
		
		if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDoj()).trim());
		
		if(CommonFunction.checkNull(vo.getCompute()).trim().equalsIgnoreCase("on"))
			insertPrepStmtObject.addString("Y");
		else
			insertPrepStmtObject.addString("N");
		
		if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPrimaryPhone()).trim());
		
		if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getAlternatePhone()).trim());
		
		if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getPrimaryEmail()).trim());
		
		if(CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getAlternateEmail()).trim());
		
		if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getWebsite()).trim());
		
			insertPrepStmtObject.addInt(id);
			
			if(checkTable==0){
			
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			}
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			//--------Ritu Code Start-----------
			PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
			StringBuffer bufInsSql1 =	new StringBuffer();
			bufInsSql1.append("insert into cr_deal_customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,");
			bufInsSql1.append("STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,");
			bufInsSql1.append("ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,");
			bufInsSql1.append("STAKEHOLDER_WEBSITE,REC_STATUS,MAKER_ID,MAKER_DATE,CUSTOMER_ID,AUTHOR_ID,AUTHOR_DATE)");
			bufInsSql1.append(" values ( ");
			bufInsSql1.append(" ?," );//STAKEHOLDER_SALUTATION
			bufInsSql1.append(" ?," );//STAKEHOLDER_NAME
			bufInsSql1.append(" ?," );//STAKEHOLDER_TYPE
			bufInsSql1.append(" ?," );//STAKEHOLDER_PERCENTAGE
			bufInsSql1.append(dbo);
			bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormat+"'),");//STAKEHOLDER_DOB
			bufInsSql1.append(" ?," );//STAKEHOLDER_EXPERIENCE
			bufInsSql1.append(" ?," );//STAKEHOLDER_DIN
			bufInsSql1.append(" ?," );//STAKEHOLDER_IDENTIFICATION_NO
			bufInsSql1.append(" ?," );//STAKEHOLDER_POSITION
			bufInsSql1.append(dbo);
			bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormat+"'),");//STAKEHOLDER_JOINING_DATE
			bufInsSql1.append(" ?," );//ELIGIBLE_FOR_COMPUTATION
			bufInsSql1.append(" ?," );//STAKEHOLDER_PRIMARY_PHONE
			bufInsSql1.append(" ?," );//STAKEHOLDER_ALTERNATE_PHONE
			bufInsSql1.append(" ?," );//STAKEHOLDER_PRIMARY_EMAIL
			bufInsSql1.append(" ?," );//STAKEHOLDER_ALTERNATE_EMAIL
			bufInsSql1.append(" ?," );//STAKEHOLDER_WEBSITE
			bufInsSql1.append(" ?," );//REC_STATUS
			bufInsSql1.append(" ?," );//MAKER_ID
			bufInsSql1.append(dbo);
			bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
			bufInsSql1.append(" ?," );//CUSTOMER_ID
			bufInsSql1.append(" ?," );//AUTHOR_ID
			bufInsSql1.append(dbo);
			bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");//AUTHOR_DATE
			
			
			if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getSex()).trim());
			
			if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getHolderName()).trim());
			
			if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getHolderType()).trim());
			
			if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getHoldingPerc()).trim());
			
			if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getDob()).trim());
			
			if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
			    insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getTotalExp()).trim());
			
			if(CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getDinNo()).trim());
			
			if(CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getIdNo()).trim());
			
			if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getPosition()).trim());
			
			if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getDoj()).trim());
			
			if(CommonFunction.checkNull(vo.getCompute()).trim().equalsIgnoreCase("on"))
				insertPrepStmtObject1.addString("Y");
			else
				insertPrepStmtObject1.addString("N");
			
			if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getPrimaryPhone()).trim());
			
			if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getAlternatePhone()).trim());
			
			if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getPrimaryEmail()).trim());
			
			if(CommonFunction.checkNull(vo.getAlternateEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getAlternateEmail()));
			
			if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getWebsite()).trim());
			
			insertPrepStmtObject1.addString("A");
			//---------------------------------------------------------
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getMakerId()).trim());
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			
			insertPrepStmtObject1.addInt(id);
			
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getMakerId()).trim());
			
		
			
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			//---------------------------------------------------------
			
				
				insertPrepStmtObject1.setSql(bufInsSql1.toString());
				logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject1.printQuery());
				qryList.add(insertPrepStmtObject1);
			//--------Ritu Code End----------
	        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    logger.info("In saveCustomer......................"+status);
 		}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return status;
	
}


	public ArrayList getStackHolderDetail(String stackHolderId ,Object pageStatus,String statusCase,String updateInMaker,String updateFlag,String pageStatuss)
	{
		ArrayList<StakeHolderVo> list=new ArrayList<StakeHolderVo>();
		String tableName="";
		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getStackHolderDetail()......");
			tableName="cr_deal_customer_stakeholder_m";
			try{
				StringBuilder query=new StringBuilder();		//String query
				query.append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE, " +
						"cs.STAKEHOLDER_PERCENTAGE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, " +
								"cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO,cs.STAKEHOLDER_POSITION, " +
								" ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"'), cs.ELIGIBLE_FOR_COMPUTATION," +
								" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL," +
								" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE FROM cr_deal_customer_stakeholder_m cs" +
								" WHERE cs.STAKEHOLDER_ID="+stackHolderId+"");
				logger.info("Query for getting stack holder list  :   "+query);
				ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getStackHolderDetail "+stackHolderDetail.size());
			//added by neeraj tripathi start space
				String custQuery="select CUSTOMER_ID from cr_deal_customer_stakeholder_m where STAKEHOLDER_ID="+stackHolderId;
				logger.info("Query for getting customerId : "+custQuery);
				String cid=ConnectionDAO.singleReturn(custQuery);
				StringBuilder perQuery=new StringBuilder();
				perQuery.append("select sum(");
				perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID="+cid);
			 	logger.info("Query for getting total percentage : "+perQuery);
			 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
			//end space
				
				for(int i=0;i<stackHolderDetail.size();i++){
					logger.info("getStackHolderDetail...FOR loop "+CommonFunction.checkNull(stackHolderDetail.get(i)).toString());
					ArrayList data=(ArrayList)stackHolderDetail.get(i);
					if(data.size()>0)
					{					
					StakeHolderVo stakeHolderVo = new StakeHolderVo();
					stakeHolderVo.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
					stakeHolderVo.setSex((CommonFunction.checkNull(data.get(1)).toString()));
					stakeHolderVo.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
					stakeHolderVo.setHolderType((CommonFunction.checkNull(data.get(3)).toString()));
					
					if(!(CommonFunction.checkNull(data.get(4)).toString()).equalsIgnoreCase(""))
		    		{
		    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(4)).toString()));
		    			stakeHolderVo.setHoldingPerc(myFormatter.format(reconNum));
		    		}	
									
					
					//stakeHolderVo.setHoldingPerc((CommonFunction.checkNull(data.get(4)).toString()));
					
					stakeHolderVo.setDob((CommonFunction.checkNull(data.get(5)).toString()));
					stakeHolderVo.setTotalExp((CommonFunction.checkNull(data.get(6)).toString()));
					stakeHolderVo.setDinNo((CommonFunction.checkNull(data.get(7)).toString()));
					
					stakeHolderVo.setIdNo((CommonFunction.checkNull(data.get(8)).toString()));
					stakeHolderVo.setPosition((CommonFunction.checkNull(data.get(9)).toString()));
					stakeHolderVo.setDoj((CommonFunction.checkNull(data.get(10)).toString()));
					stakeHolderVo.setCompute((CommonFunction.checkNull(data.get(11)).toString()));
					stakeHolderVo.setPrimaryPhone((CommonFunction.checkNull(data.get(12)).toString()));
					stakeHolderVo.setAlternatePhone((CommonFunction.checkNull(data.get(13)).toString()));
					stakeHolderVo.setPrimaryEmail((CommonFunction.checkNull(data.get(14)).toString()));
					stakeHolderVo.setAlternateEmail((CommonFunction.checkNull(data.get(15)).toString()));
					stakeHolderVo.setWebsite((CommonFunction.checkNull(data.get(16)).toString()));
					stakeHolderVo.setPercentage(percentage);
					list.add(stakeHolderVo);
				}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			logger.info("In GCD.,getStackHolderDetail()......");
			if(pageStatus!=null && pageStatus.equals("approve") || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
			{
				if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_stakeholder_m";
				}
				else
				{
					tableName="customer_stakeholder_m_temp";
				}
				
			}
			else
			{
				tableName="customer_stakeholder_m";
			}
		try{
		
		StringBuilder query=new StringBuilder();		//String query;
		query.append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE, cs.STAKEHOLDER_PERCENTAGE, ");
		query.append(dbo);
		query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO, cs.STAKEHOLDER_POSITION, ");
		query.append(dbo);
		query.append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"'), cs.ELIGIBLE_FOR_COMPUTATION, cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL, cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE FROM "+tableName+" cs WHERE cs.STAKEHOLDER_ID="+stackHolderId);
				
		logger.info("Query for getting stack holder list  :   "+query);
		ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query.toString());
	//added by neeraj tripathi start space
		String custQuery="select CUSTOMER_ID from "+tableName+"  where STAKEHOLDER_ID="+stackHolderId;
		logger.info("Query for getting customerId : "+custQuery);
		String cid=ConnectionDAO.singleReturn(custQuery);
		StringBuilder perQuery=new StringBuilder();
		perQuery.append("select sum(");
		perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from "+tableName+" where CUSTOMER_ID="+cid);
	 	logger.info("Query for getting total percentage : "+perQuery);
	 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
	//end space
		logger.info("getStackHolderDetail "+stackHolderDetail.size());
		for(int i=0;i<stackHolderDetail.size();i++){
			logger.info("getStackHolderDetail...FOR loop "+CommonFunction.checkNull(stackHolderDetail.get(i)).toString());
			ArrayList data=(ArrayList)stackHolderDetail.get(i);
			if(data.size()>0)
			{	
			
			StakeHolderVo stakeHolderVo = new StakeHolderVo();
			stakeHolderVo.setHolderid((CommonFunction.checkNull(data.get(0)).toString()));
			stakeHolderVo.setSex((CommonFunction.checkNull(data.get(1)).toString()));
			stakeHolderVo.setHolderName((CommonFunction.checkNull(data.get(2)).toString()));
			stakeHolderVo.setHolderType((CommonFunction.checkNull(data.get(3)).toString()));
			
			if(!(CommonFunction.checkNull(data.get(4)).toString()).equalsIgnoreCase(""))
    		{
    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(4)).toString()));
    			stakeHolderVo.setHoldingPerc(myFormatter.format(reconNum));
    		}
					
			//stakeHolderVo.setHoldingPerc((CommonFunction.checkNull(data.get(4)).toString()));
			stakeHolderVo.setDob((CommonFunction.checkNull(data.get(5)).toString()));
			stakeHolderVo.setTotalExp((CommonFunction.checkNull(data.get(6)).toString()));
			stakeHolderVo.setDinNo((CommonFunction.checkNull(data.get(7)).toString()));
			stakeHolderVo.setIdNo((CommonFunction.checkNull(data.get(8)).toString()));
			stakeHolderVo.setPosition((CommonFunction.checkNull(data.get(9)).toString()));
			stakeHolderVo.setDoj((CommonFunction.checkNull(data.get(10)).toString()));
			stakeHolderVo.setCompute((CommonFunction.checkNull(data.get(11)).toString()));
			stakeHolderVo.setPrimaryPhone((CommonFunction.checkNull(data.get(12)).toString()));
			stakeHolderVo.setAlternatePhone((CommonFunction.checkNull(data.get(13)).toString()));
			stakeHolderVo.setPrimaryEmail((CommonFunction.checkNull(data.get(14)).toString()));
			stakeHolderVo.setAlternateEmail((CommonFunction.checkNull(data.get(15)).toString()));
			stakeHolderVo.setWebsite((CommonFunction.checkNull(data.get(16)).toString()));
			stakeHolderVo.setPercentage(percentage);
			list.add(stakeHolderVo);
		}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return list;
	}



	
	@Override
	public String getCustomerFromGCD(String id) {
		 String query = "Select customer_id from gcd_customer_m_temp where customer_id= (select CUSTOMER_ID from customer_stakeholder_m where STAKEHOLDER_ID="+id+")";
			logger.info("query in fetchCustomerAddress .................................."+query);
			String checkFlag = ConnectionDAO.singleReturn(query);
			return checkFlag;
	}
	
	
	public int updateStakeHolder(Object ob, int id, int stackId, String recStatus,String statusCase,String updateFlag,String pageStatuss) {
		
		StakeHolderVo vo = (StakeHolderVo)ob;
		//logger.info("value of  maker date in update"+vo.getMakerDate());
		//logger.info("value of  maker id in update"+vo.getMakerId());
		int status=0;
		boolean qryStatus=false;
		String tableName="";
		//String subQuery=null;
		StringBuilder subQuery=null;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			logger.info("updateStakeHolder :"+id);
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				logger.info("In Credit Processing , Customer Entry.,updateStakeHolder().......");
				tableName="cr_deal_customer_stakeholder_m";
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set STAKEHOLDER_SALUTATION=?," +
				" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), " +
				"STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, " +
				"STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?," +
				" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?, " +
				"STAKEHOLDER_JOINING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), ELIGIBLE_FOR_COMPUTATION=?," +
				"MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(" where STAKEHOLDER_ID=?");
				
				
				if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getSex()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHolderName()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHolderType()).trim());
				
				if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHoldingPerc()).trim());
				
				if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDob()).trim());
				
				if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTotalExp()).trim());
				
				if(CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDinNo()).trim());
				
				if(CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getIdNo()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryPhone()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternatePhone()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternateEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getWebsite()).trim());
				
				if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPosition()).trim());
				
				if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDoj()).trim());
				
				if((vo.getCompute()).equals("on"))
				{
					insertPrepStmtObject.addString("Y");     
				}
				else
				{
					insertPrepStmtObject.addString("N");
				}
				
				//---------------
		    	

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).	trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).	trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));

				//---------------
				
				if(CommonFunction.checkNull(stackId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(stackId);
				
				insertPrepStmtObject.setSql(query.toString());
		        logger.info("IN updateStakeHolder() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
			else
			{
				logger.info("In GCD.,updateStakeHolder().......");
				if(statusCase!=null && statusCase.length()>0) //&& statusCase.equals("UnApproved"))
				{
					tableName="customer_stakeholder_m_temp";
				}
				else
				{
					tableName="customer_stakeholder_m";
				}
				

				if(tableName.equalsIgnoreCase("customer_stakeholder_m")){
				
                 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"'," +
						  "MAKER_DATE=");
                 subQuery.append(dbo);
                 subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
                 subQuery.append(",AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
                 subQuery.append(dbo);
                 subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 }else{
				 subQuery.setLength(0);	//subQuery="";	
				 }
				StringBuilder query=new StringBuilder();		//String query''	asesh space
				query.append("update "+tableName+" set STAKEHOLDER_SALUTATION=?," +
				" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), " +
				"STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, " +
				"STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?," +
				" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?, " +
				"STAKEHOLDER_JOINING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), ELIGIBLE_FOR_COMPUTATION=? "+subQuery+"  where STAKEHOLDER_ID=?");
				
				if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getSex()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHolderName()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHolderType()).trim());
				
				if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getHoldingPerc()).trim());
				
				if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDob()).trim());
				
				if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTotalExp()).trim());
				
				if(vo.getDinNo()==null)
				{
					insertPrepStmtObject.addNull();
				}
				else
				{
					insertPrepStmtObject.addString((vo.getDinNo()).trim());
				}
				
				if(vo.getIdNo()==null)
				{
					insertPrepStmtObject.addNull();
				}
				else
				{
					insertPrepStmtObject.addString((vo.getIdNo()).trim());
				}
				
				if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryPhone()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternatePhone()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternateEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getWebsite()).trim());
				
				if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPosition()).trim());
				
				if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDoj()).trim());
				
				if((vo.getCompute()).equals("on"))
				{
					insertPrepStmtObject.addString("Y");      
				}
				else
				{
					insertPrepStmtObject.addString("N");
				}
				
			
				
				if(CommonFunction.checkNull(stackId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(stackId);
	
				insertPrepStmtObject.setSql(query.toString());
				logger.info("IN updateStakeHolder() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//----------Ritu Code Start----------
				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
				StringBuilder query1=new StringBuilder();
				query1.append("update cr_deal_customer_stakeholder_m set STAKEHOLDER_SALUTATION=?," +
				" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=");
				query1.append(dbo);
				query1.append("STR_TO_DATE(?,'"+dateFormat+"'), " +
				"STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, " +
				"STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?," +
				" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?, " +
				"STAKEHOLDER_JOINING_DATE=");
				query1.append(dbo);
				query1.append("STR_TO_DATE(?,'"+dateFormat+"'), ELIGIBLE_FOR_COMPUTATION=?," +
				"MAKER_ID=?,MAKER_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append(" where CUSTOMER_ID=?");
				
				
				if(CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getSex()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getHolderName()).trim());
				
				if(CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getHolderType()).trim());
				
				if(CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getHoldingPerc()).trim());
				
				if(CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getDob()).trim());
				
				if(CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getTotalExp()).trim());
				
				if(CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getDinNo()).trim());
				
				if(CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getIdNo()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPrimaryPhone()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAlternatePhone()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPrimaryEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAlternateEmail()).trim());
				
				if(CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getWebsite()).trim());
				
				if(CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPosition()).trim());
				
				if(CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getDoj()).trim());
				
				if((vo.getCompute()).equals("on"))
				{
					insertPrepStmtObject1.addString("Y");     
				}
				else
				{
					insertPrepStmtObject1.addString("N");
				}
				
				//---------------
		    	

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).	trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
				else
				insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
				else
				insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).	trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
				insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
				else
				insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));

				//---------------
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addInt(id);
				
				insertPrepStmtObject1.setSql(query1.toString());
		        logger.info("IN updateStakeHolder() update query1 ### "+insertPrepStmtObject1.printQuery());
				qryList.add(insertPrepStmtObject1);
				//----------Ritu Code End------------
				
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
				}catch(Exception e){
					e.printStackTrace();
					}finally{
						subQuery.setLength(0);	//subQuery="";
					}
				if(qryStatus)
				{
					status=1;
				}
			return status;	
	}

	
	public ArrayList getInstitutionName() {
		ArrayList<InstitutionNameVo> list=new ArrayList<InstitutionNameVo>();
		try{
		String query="select AGENCY_CODE,AGENCY_NAME from com_agency_m where AGENCY_TYPE='CR'";
		ArrayList institutionName = ConnectionDAO.sqlSelect(query);
		logger.info("getInstitutionName "+institutionName.size());
		for(int i=0;i<institutionName.size();i++){
			logger.info("getInstitutionName...FOR loop "+CommonFunction.checkNull(institutionName.get(i)).toString());
			ArrayList data=(ArrayList)institutionName.get(i);
			if(data.size()>0)
			{
				InstitutionNameVo institutionNameVo=new InstitutionNameVo();
				institutionNameVo.setAgencyCode((CommonFunction.checkNull(data.get(0)).toString()));
				institutionNameVo.setAgencyDesc((CommonFunction.checkNull(data.get(1)).toString()));
				list.add(institutionNameVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList<Object> getcreditRatingDetails(int cid, String statusCase,String updateInMaker,String pageStatuss,String updateFlag) {
		 ArrayList<Object> list=new ArrayList<Object>();
		 String tableName="";
		    
		 if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
			{
			 logger.info("In Credit Processing , Customer Entry..,getcreditRatingDetails()......");
				tableName="cr_deal_customer_rating";
				
				try{
					StringBuilder query=new StringBuilder();		//String query
					query.append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING," +
							"");
					query.append(dbo);
					query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"') from com_agency_m a,"+tableName+" r" +
							" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+cid);
					ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getcreditRatingDetails "+creditRatingDetails.size());
					for(int i=0;i<creditRatingDetails.size();i++){
						logger.info("getcreditRatingDetails...FOR loop "+CommonFunction.checkNull(creditRatingDetails.get(i)).toString());
						ArrayList data=(ArrayList)creditRatingDetails.get(i);
						if(data.size()>0)
						{
							CreditRatingGridVo creditRatingGridVo=new CreditRatingGridVo();
							creditRatingGridVo.setRatingId((CommonFunction.checkNull(data.get(0)).toString()));
							creditRatingGridVo.setInstitute((CommonFunction.checkNull(data.get(1)).toString()));
							creditRatingGridVo.setAgencyCode((CommonFunction.checkNull(data.get(2)).toString()));
							creditRatingGridVo.setRating((CommonFunction.checkNull(data.get(3)).toString()));
							creditRatingGridVo.setCreditDate((CommonFunction.checkNull(data.get(4)).toString()));
							list.add(creditRatingGridVo);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		    
			else
			{
				logger.info("In GCD..,saveCreditRating()......");
				if(updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
				{
					if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
					{
						tableName="customer_rating_m";
					}
					else
					{
						tableName="customer_rating_m_temp";
					}
					
				}
				else
				{
					tableName="customer_rating_m";
				}
				
			try{
				StringBuilder query=new StringBuilder();		//String query
				query.append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING," +
					"");
				query.append(dbo);
				query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"') from com_agency_m a,"+tableName+" r" +
					" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+cid);
			ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getcreditRatingDetails "+creditRatingDetails.size());
			for(int i=0;i<creditRatingDetails.size();i++){
				logger.info("getcreditRatingDetails...FOR loop "+CommonFunction.checkNull(creditRatingDetails.get(i)).toString());
				ArrayList data=(ArrayList)creditRatingDetails.get(i);
				if(data.size()>0)
				{
					CreditRatingGridVo creditRatingGridVo=new CreditRatingGridVo();
					creditRatingGridVo.setRatingId((CommonFunction.checkNull(data.get(0)).toString()));
					creditRatingGridVo.setInstitute((CommonFunction.checkNull(data.get(1)).toString()));
					creditRatingGridVo.setAgencyCode((CommonFunction.checkNull(data.get(2)).toString()));
					creditRatingGridVo.setRating((CommonFunction.checkNull(data.get(3)).toString()));
					creditRatingGridVo.setCreditDate((CommonFunction.checkNull(data.get(4)).toString()));
					list.add(creditRatingGridVo);
				}
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			}
			return list;
		}

	public ArrayList<Object> getRatingDetails(int code,Object pageStatus, String statusCase,String updateFlag,String updateInMaker) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";
		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getRatingDetails()......");
			tableName="cr_deal_customer_rating";
			try{
				String query="SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING," +
						"r.RATING_DATE from "+tableName+" r, com_agency_m a " +
						"where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+code;
				ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
				logger.info("getRatingDetails "+ratingDetails.size());
				for(int i=0;i<ratingDetails.size();i++){
					logger.info("getRatingDetails...FOR loop "+CommonFunction.checkNull(ratingDetails.get(i)).toString());
					ArrayList data=(ArrayList)ratingDetails.get(i);
					if(data.size()>0)
					{
						CreditRatingGridVo creditRatingGridVo=new CreditRatingGridVo();
						creditRatingGridVo.setRatingId((CommonFunction.checkNull(data.get(0)).toString()));
						creditRatingGridVo.setInstitute((CommonFunction.checkNull(data.get(1)).toString()));
						creditRatingGridVo.setAgencyCode((CommonFunction.checkNull(data.get(2)).toString()));
						creditRatingGridVo.setRating((CommonFunction.checkNull(data.get(3)).toString()));
						creditRatingGridVo.setCreditDate((CommonFunction.checkNull(data.get(4)).toString()));
						list.add(creditRatingGridVo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else
		{
			logger.info("In GCD..,saveCreditRating()......*******************");
			if(pageStatus!=null || updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_rating_m";
				}
				else
				{
					tableName="customer_rating_m_temp";
				}
				
			}
			else 
			{
				tableName="customer_rating_m";
			}
			
			
		try{
			String query="SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING," +
					"r.RATING_DATE from "+tableName+" r, com_agency_m a " +
					"where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+code;
			ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
			logger.info("getRatingDetails "+ratingDetails.size());
			for(int i=0;i<ratingDetails.size();i++){
				logger.info("getRatingDetails...FOR loop "+CommonFunction.checkNull(ratingDetails.get(i)).toString());
				ArrayList data=(ArrayList)ratingDetails.get(i);
				if(data.size()>0)
				{
					CreditRatingGridVo creditRatingGridVo=new CreditRatingGridVo();
					creditRatingGridVo.setRatingId((CommonFunction.checkNull(data.get(0)).toString()));
					creditRatingGridVo.setInstitute((CommonFunction.checkNull(data.get(1)).toString()));
					creditRatingGridVo.setAgencyCode((CommonFunction.checkNull(data.get(2)).toString()));
					creditRatingGridVo.setRating((CommonFunction.checkNull(data.get(3)).toString()));
					creditRatingGridVo.setCreditDate((CommonFunction.checkNull(data.get(4)).toString()));
					list.add(creditRatingGridVo);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return list;	
	}

	public boolean saveCreditRating(Object ob,int id) {
		
		CreditRatingVo vo = (CreditRatingVo)ob;
		
		//logger.info("maker id in rating"+vo.getMakerId());
		//logger.info("maker date in rating"+vo.getMakerDate());
		boolean status=false;
		ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			try{

			if((vo.getPageStat()!=null && vo.getPageStat().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
				logger.info("In Credit Processing , Customer Entry..,saveCreditRating()......");
				
				logger.info("In insert saveCreditRating");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_deal_customer_rating(CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				insertPrepStmtObject.addInt(id);
				
				if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getInstitute()).trim());
				
				if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRating()).trim());
				
				if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCreditDate()).trim());
				
				insertPrepStmtObject.addString("A");
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				//---------------------------------------------------------
				
				
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveCreditRating......................"+status);
			}
						
			else
			{
				logger.info("In GCD..,saveCreditRating()......&&&&788999999999999999888");
				int maxId=0;
		    	
				StringBuffer bufInsSql =new StringBuffer();
		    	int checkTable=0;
		    	if(!vo.getStatusCase().equals(""))
		    	{
		    		
		    		String query3="Select max(CUSTOMER_RATING_ID) from customer_rating_m_temp  WITH (ROWLOCK) ";
					 String maxid = ConnectionDAO.singleReturn(query3);
					 logger.info("maxId : "+maxId);
					 if(CommonFunction.checkNull(maxid).equalsIgnoreCase(""))
					 {
						 maxId=1;
					 }
					 else
					 {
						 maxId=Integer.parseInt(maxid)+1;
					 }
					
					 checkTable++;
					bufInsSql.append("insert into customer_rating_m_temp(CUSTOMER_RATING_ID,CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE)");
					bufInsSql.append(" values ( ");
		    		bufInsSql.append(" ?," );
		    		
		    	}
		    	else
		    	{
		    		
		    		bufInsSql.append("insert into customer_rating_m(CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
		    		bufInsSql.append(" values ( ");
		    	}
				
			
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			if(checkTable==0){
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			}
			else{
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"') )" );
			}
			
			if(!vo.getStatusCase().equals(""))
	    	{
				
				insertPrepStmtObject.addInt(maxId);
	    	}
			
			insertPrepStmtObject.addInt(id);
			
			if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getInstitute()).trim());
			
			if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRating()).trim());
			
			if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCreditDate()).trim());
			
			if(checkTable==0){
			
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			
			if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			}
			
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//-------Ritu Code Start---------
				
				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
				StringBuffer bufInsSql1 = new StringBuffer();
				bufInsSql1.append("insert into cr_deal_customer_rating(CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql1.append(" values ( ");
				bufInsSql1.append(" ?," );//CUSTOMER_ID
				bufInsSql1.append(" ?," );//RATING_AGENCY
				bufInsSql1.append(" ?," );//CUSTOMER_RATING
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormat+"')," );//RATING_DATE
				bufInsSql1.append(" ?," );//REC_STATUS
				bufInsSql1.append(" ?," );//MAKER_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql1.append(" ?," );//AUTHOR_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				insertPrepStmtObject1.addInt(id);
				
				if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getInstitute()).trim());
				
				if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getRating()).trim());
				
				if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getCreditDate()).trim());
				
				insertPrepStmtObject1.addString("A");
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				//---------------------------------------------------------
				
				
					insertPrepStmtObject1.setSql(bufInsSql1.toString());
					logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject1.printQuery());
					qryList.add(insertPrepStmtObject1);
				//---------Ritu Code End---------
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCreditRating......................"+status);
				
				
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		
			return status;

	}
	
	
	public String getCustomerFromCRM(String id) {
			String query = "Select customer_id from gcd_customer_m_temp where customer_id= (select CUSTOMER_ID from customer_rating_m where CUSTOMER_RATING_ID="+id+")";
			logger.info("query in fetchCustomerAddress .................................."+query);
			String checkFlag = ConnectionDAO.singleReturn(query);
			return checkFlag;
	}
	
	
	public int updateCreditRating(Object ob, int id,int crId, String recStatus,String statusCase,String updateFlag,String pageStatuss)
	{
		CreditRatingVo vo = (CreditRatingVo)ob;
		int status=0;
		boolean qryStatus = false;
		String tableName="";
		StringBuilder subQuery=null;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList = new ArrayList();
		try{
		
			logger.info("updateCreditRating :"+id);
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				logger.info("In Credit Processing , Customer Entry..,updateCreditRating()......");
				tableName="cr_deal_customer_rating";
				StringBuilder query=new StringBuilder();
				query.append("update "+tableName+" set RATING_AGENCY=?, CUSTOMER_RATING=?, " +
				"RATING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'),MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(" where CUSTOMER_RATING_ID=?");
				
				if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getInstitute()).trim());
				
				if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRating()).trim());
				
				if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCreditDate()).trim());
				
				//---------------
		    	

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));

				//---------------
				
				if(CommonFunction.checkNull(crId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(crId);
		        
		        insertPrepStmtObject.setSql(query.toString());
		        logger.info("IN updateCreditRating() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
			else
			{
				logger.info("In GCD..,saveCreditRating()......method ");
				if(statusCase!=null && statusCase.length()>0)
				{
					tableName="customer_rating_m_temp";
				}
				else
				{
					tableName="customer_rating_m";
				}
				if(tableName.equalsIgnoreCase("customer_rating_m")){
				 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',"+
				          "MAKER_DATE=");
				subQuery.append(dbo); 
				subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				subQuery.append(",AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
				 subQuery.append(dbo);
					subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 }else{
					 subQuery.setLength(0);	 	//subQuery="";	
				 }
			String query="update "+tableName+" set RATING_AGENCY=?, CUSTOMER_RATING=?, " +
			"RATING_DATE=STR_TO_DATE(?,'"+dateFormat+"') "+subQuery+" where CUSTOMER_RATING_ID=?";
			
			if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getInstitute()).trim());
			
			if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRating()).trim());
			
			if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCreditDate()).trim());
			
	
			
			if(CommonFunction.checkNull(crId).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(crId);
			
	        
	        insertPrepStmtObject.setSql(query.toString());
	        logger.info("IN updateCreditRating() update query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			//--------Ritu Code Start--------
			PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
			StringBuilder query1=new StringBuilder();
			query1.append("update cr_deal_customer_rating set RATING_AGENCY=?, CUSTOMER_RATING=?, " +
			"RATING_DATE=");
			query1.append(dbo);
			query1.append("STR_TO_DATE(?,'"+dateFormat+"'),MAKER_ID=?,MAKER_DATE=");
			query1.append(dbo);
			query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			query1.append(",AUTHOR_ID=?,AUTHOR_DATE=");
			query1.append(dbo);
			query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			query1.append(" where CUSTOMER_ID=?");
			
			if(CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getInstitute()).trim());
			
			if(CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getRating()).trim());
			
			if(CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((vo.getCreditDate()).trim());
			
			//---------------
	    	

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject1.addNull();
			else
			insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
								

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject1.addNull();
			else
			insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
			
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
			insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
								

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject1.addNull();
			else
			insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));

			//---------------
			
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addInt(id);
	        
	        insertPrepStmtObject1.setSql(query1.toString());
	        logger.info("IN updateCreditRating() update query1 ### "+insertPrepStmtObject1.printQuery());
			qryList.add(insertPrepStmtObject1);
			//--------Ritu Code End----------
			qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;	
	}



	@Override
	public ArrayList getCreditRatingDetail(String creditRatingId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss)
	{
		ArrayList<CreditRatingVo> list=new ArrayList<CreditRatingVo>();
		String tableName="";
		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getCreditRatingDetail()......");
			tableName="cr_deal_customer_rating";
			try{
				StringBuilder query=new StringBuilder();		//String query
				query.append("SELECT a.AGENCY_CODE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID " +
						"from "+tableName+" r, com_agency_m a " +
						"where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID="+creditRatingId);
						
				ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getCreditRatingDetail "+creditRatingDetail.size());
				for(int i=0;i<creditRatingDetail.size();i++){
					logger.info("getCreditRatingDetail...FOR loop "+CommonFunction.checkNull(creditRatingDetail.get(i)).toString());
					ArrayList data=(ArrayList)creditRatingDetail.get(i);
					if(data.size()>0)
					{
					
						CreditRatingVo creditRatingVo=new CreditRatingVo();
						creditRatingVo.setInstitute((CommonFunction.checkNull(data.get(0)).toString()));
						creditRatingVo.setCreditDate((CommonFunction.checkNull(data.get(1)).toString()));
						creditRatingVo.setRating((CommonFunction.checkNull(data.get(2)).toString()));
						creditRatingVo.setcRatingId((CommonFunction.checkNull(data.get(3)).toString()));
						list.add(creditRatingVo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			
			logger.info("In GCD..,saveCreditRating()......");
			if(pageStatus!=null && pageStatus.equals("approve") || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
			{
				if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_rating_m";
				}
				else
				{
					tableName="customer_rating_m_temp";
				}
				
			}
			else
			{
				tableName="customer_rating_m";
			}
	
		try{
			StringBuilder query=new StringBuilder();		//String query	changes for using mssql	
		query.append("SELECT a.AGENCY_CODE, ");
		query.append(dbo);
		query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID " +
				"from "+tableName+" r, com_agency_m a " +
				"where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID="+creditRatingId);
		ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getCreditRatingDetail "+creditRatingDetail.size());
		for(int i=0;i<creditRatingDetail.size();i++){
			logger.info("getCreditRatingDetail...FOR loop "+CommonFunction.checkNull(creditRatingDetail.get(i)).toString());
			ArrayList data=(ArrayList)creditRatingDetail.get(i);
			if(data.size()>0)
			{
				CreditRatingVo creditRatingVo=new CreditRatingVo();
				creditRatingVo.setInstitute((CommonFunction.checkNull(data.get(0)).toString()));
				creditRatingVo.setCreditDate((CommonFunction.checkNull(data.get(1)).toString()));
				creditRatingVo.setRating((CommonFunction.checkNull(data.get(2)).toString()));
				creditRatingVo.setcRatingId((CommonFunction.checkNull(data.get(3)).toString()));
				list.add(creditRatingVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		}
		
		return list;
	}
	
	public boolean saveCustomerReference(Object ob) {
		logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try
		{
			if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("insert into cr_deal_reference_m(BPID, BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append(" )" );
			
		
					if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
					else
						insertPrepStmtObject.addString((vo.getBp_id()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
					if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getFirstName()).trim());
					
					if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMiddleName()).trim());
					
					if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull(); 
					else
						insertPrepStmtObject.addString((vo.getLastName()).trim());
					
					if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getRelationshipS()).trim());
					
					if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getKnowingSince()).trim());
					
					if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPrimaryRefMbNo()).trim());
					
					if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAlternateRefPhNo()).trim());
					
					insertPrepStmtObject.addString("A");
					
					//---------------------------------------------------------
					if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					
					if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					
					//---------------------------------------------------------
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveCustomerReference() of CorpotateDAOImpl  insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveCustomerReference......................"+status);
			}
			else
			{
			    	
			    	int maxId=0;
			    	StringBuffer bufInsSql =	new StringBuffer();
					int checkTable=0;			    	
			    	if(!vo.getStatusCase().equals(""))
			    	{			    		
			    		String query3="Select max(REF_ID) from com_reference_m_temp  WITH (ROWLOCK) ";
						String id = ConnectionDAO.singleReturn(query3);
						logger.info("id: "+id);
						if(CommonFunction.checkNull(id).equalsIgnoreCase(""))
							 maxId=1;
						else
							 maxId=Integer.parseInt(id)+1;
						checkTable++;
						bufInsSql.append("insert into com_reference_m_temp(REF_ID,BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER)");
			    		bufInsSql.append(" values ( ");
			    		bufInsSql.append(" ?," );
			    	}
			    	else
			    	{
			    		bufInsSql.append("insert into com_reference_m(BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
			    		bufInsSql.append(" values ( ");
			    	}
	
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ? " );
			
					if(checkTable==0)
					{
						bufInsSql.append(" , ?," );	
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
						bufInsSql.append(" )" );
					}else
					{
						bufInsSql.append(" )" );
					}
					
					if(!vo.getStatusCase().equals(""))
			    	{
						insertPrepStmtObject.addInt(maxId);
			    	}
					if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
					else
						insertPrepStmtObject.addString((vo.getBp_id()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
					if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getFirstName()).trim());
					
					if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMiddleName()).trim());
					
					if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull(); 
					else
						insertPrepStmtObject.addString((vo.getLastName()).trim());
					
					if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getRelationshipS()).trim());
					
					if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getKnowingSince()).trim());
					
					if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPrimaryRefMbNo()).trim());
					
					if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAlternateRefPhNo()).trim());
					
					if(checkTable==0)
					{					
						
						insertPrepStmtObject.addString("A");
						
						if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					}
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveCustomerReference() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//--------Ritu Code Start--------
				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
				StringBuffer bufInsSql1 =	new StringBuffer();
				bufInsSql1.append("insert into cr_deal_reference_m(BPID, BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, ");
				bufInsSql1.append("KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql1.append(" values ( ");
				bufInsSql1.append(" ?," );//BPID
				bufInsSql1.append(" ?," );//BPTYPE
				bufInsSql1.append(" ?," );//F_NAME
				bufInsSql1.append(" ?," );//M_NAME
				bufInsSql1.append(" ?," );//L_NAME
				bufInsSql1.append(" ?," );//RELATIONSHIP
				bufInsSql1.append(" ?," );//KNOWING_SINCE
				bufInsSql1.append(" ?," );//MOBILE_NUMBER
				bufInsSql1.append(" ?," );//LANDLINE_NUMBER
				bufInsSql1.append(" ?," );//REC_STATUS
				bufInsSql1.append(" ?," );//MAKER_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), " );;//MAKER_DATE
				bufInsSql1.append(" ?," );//AUTHOR_ID
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)" );//AUTHOR_DATE
				bufInsSql1.append(" )" );
		
	
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addString((vo.getBp_id1()).trim()); 
				else
					insertPrepStmtObject1.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getFirstName()).trim());
				
				if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMiddleName()).trim());
				
				if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull(); 
				else
					insertPrepStmtObject1.addString((vo.getLastName()).trim());
				
				if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getRelationshipS()).trim());
				
				if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getKnowingSince()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPrimaryRefMbNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAlternateRefPhNo()).trim());
				
				insertPrepStmtObject1.addString("A");
				
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				//---------------------------------------------------------
				
				insertPrepStmtObject1.setSql(bufInsSql1.toString());
				logger.info("IN saveCustomerReference() of CorpotateDAOImpl  insert query1 ### "+insertPrepStmtObject1.printQuery());
				qryList.add(insertPrepStmtObject1);
				//Ritu code end
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomerReference......................"+status);
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}
	
	public int updateIndReference(Object ob, int id, int refId, String recStatus,String statusCase, String updateFlag, String pageStatuss) {
		logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		int status=0;
		boolean qryStatus= false;
		String tableName="";
		ArrayList qryList = new ArrayList();
		StringBuilder subQuery=null;
		try
		{
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				tableName="cr_deal_reference_m";
				StringBuilder query=new StringBuilder();
				query.append("update "+tableName+" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?, " +
				"MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query.append(" where REF_ID=?");
					
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
											
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
				else
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getFirstName()).trim());
				
				if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMiddleName()).trim());
				
				if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull(); 
				else
					insertPrepStmtObject.addString((vo.getLastName()).trim());
				
				if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRelationshipS()).trim());
				
				if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getKnowingSince()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryRefMbNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternateRefPhNo()).trim());
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				//---------------
				
				if(CommonFunction.checkNull(refId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(refId);
				
				insertPrepStmtObject.setSql(query.toString());
				logger.info("in saveCustomerReference() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject.printQuery());
    			qryList.add(insertPrepStmtObject);
    			qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
			else
			{
				if(statusCase!=null && statusCase.length()>0) //&& statusCase.equals("UnApproved"))
					tableName="com_reference_m_temp";
				else 
					tableName="com_reference_m";
				if(tableName.equalsIgnoreCase("com_reference_m"))
				{
					subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"'," +
					"MAKER_DATE=");
					subQuery.append(dbo);
					subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					subQuery.append(","+"AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
					subQuery.append(dbo);
					subQuery.append(" STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				}
				else
					subQuery.setLength(0);		//subQuery="";	
				String query="update "+tableName+" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=? "+subQuery+" where REF_ID=?";
				
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
				else
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getFirstName()).trim());
				
				if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMiddleName()).trim());
				
				if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull(); 
				else
					insertPrepStmtObject.addString((vo.getLastName()).trim());
				
				if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRelationshipS()).trim());
				
				if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getKnowingSince()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPrimaryRefMbNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAlternateRefPhNo()).trim());

				if(CommonFunction.checkNull(refId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(refId);
				
				insertPrepStmtObject.setSql(query.toString());
				logger.info("in saveCustomerReference() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//---------Ritu Code Start-----------

				StringBuilder query1=new StringBuilder();
				query1.append("update cr_deal_reference_m set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?, " +
				"MAKER_ID=?,MAKER_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append(",AUTHOR_ID=?,AUTHOR_DATE=");
				query1.append(dbo);
				query1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				query1.append("where BPID=?");
					
				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
											
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addString((vo.getBp_id1()).trim()); 
				else
				insertPrepStmtObject1.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getFirstName()).trim());
				
				if(CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getMiddleName()).trim());
				
				if(CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull(); 
				else
					insertPrepStmtObject1.addString((vo.getLastName()).trim());
				
				if(CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getRelationshipS()).trim());
				
				if(CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getKnowingSince()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getPrimaryRefMbNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getAlternateRefPhNo()).trim());
				
				//---------------
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
									
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				//---------------
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addInt(id);
				
				insertPrepStmtObject1.setSql(query1.toString());
				logger.info("in saveCustomerReference() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject1.printQuery());
    			qryList.add(insertPrepStmtObject1);
				//---------Ritu Code End-------------
    			
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			subQuery=null;
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;	
		
	}
		
	public boolean saveCustomerAddress(Object ob) 
	{
		logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try
		{
			if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("insert into cr_deal_address_m(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,TAHSIL,BRANCH_DISTANCE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), " );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ? )" );
					
					
					if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr_type()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
					else
						insertPrepStmtObject.addString((vo.getBp_id()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr1()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr2()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr3()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());
					
					if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPincode()).trim());
					
					if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPrimaryPhoneNo()).trim());
					
					if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAlternatePhoneNo()).trim());
					
					if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());
					
					if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getFaxNo()).trim());
					
					if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLandMark()).trim());
					
					if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getNoYears()).trim());
									
					if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getCommunicationAddress()).trim());
					
					if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddDetails()).trim());
					
					if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getNoMonths()).trim());
					
					insertPrepStmtObject.addString("A");
					//---------------------------------------------------------
					if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					
					if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					
					if ((CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTahsil()).trim());
					
					if ((CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDistanceBranch()).trim());
					//---------------------------------------------------------
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveCustomer......................"+status);
			}
			else
			{
			    	
			    	int maxId=0;
			    	StringBuffer bufInsSql =	new StringBuffer();
					int checkTable=0;			    	
			    	if(!vo.getStatusCase().equals(""))
			    	{			    		
			    		String query3="Select max(ADDRESS_ID) from com_address_m_temp  WITH (ROWLOCK) ";
						String id = ConnectionDAO.singleReturn(query3);
						logger.info("id: "+id);
						if(CommonFunction.checkNull(id).equalsIgnoreCase(""))
							 maxId=1;
						else
							 maxId=Integer.parseInt(id)+1;
						checkTable++;
			    		bufInsSql.append("insert into com_address_m_temp(ADDRESS_ID,ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,TAHSIL,BRANCH_DISTANCE)");
			    		bufInsSql.append(" values ( ");
			    		bufInsSql.append(" ?," );
			    		
			    	}
			    	else
			    	{
			    		bufInsSql.append("insert into com_address_m(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,TAHSIL,BRANCH_DISTANCE)");
			    		bufInsSql.append(" values ( ");
			    	}
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
			
					if(checkTable==0)
					{
						bufInsSql.append(" ?," );	
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), " );
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );
					}
					else
					{
						bufInsSql.append(" ? ," );
					}
					bufInsSql.append(" ?," );
					bufInsSql.append(" ? )" );
					
					if(!vo.getStatusCase().equals(""))
			    	{
						insertPrepStmtObject.addInt(maxId);
			    	}
			
					if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr_type()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
					if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addString((vo.getBp_id1()).trim()); 
					else
						insertPrepStmtObject.addString((vo.getBp_id()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr1()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr2()).trim());
					
					if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddr3()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());
					
					if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());
					
					if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPincode().trim()));
					
					if(CommonFunction.checkNull(vo.getPrimaryPhoneNo().trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getPrimaryPhoneNo().trim()));
					
					if(CommonFunction.checkNull(vo.getAlternatePhoneNo().trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAlternatePhoneNo()).trim());
					
					if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());
					
					if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getFaxNo()).trim());
					
					if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLandMark()).trim());
					
					if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getNoYears()).trim());
					
					if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getCommunicationAddress()).trim());
					
					if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddDetails()).trim());
					
					if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getNoMonths()).trim());
					
					
					if(checkTable==0)
					{					
						if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId()).trim());
						
						if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					}
					if ((CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTahsil()).trim());
					
					if ((CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDistanceBranch()).trim());
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				//---------Ritu Code Start-------------
				//ArrayList queryList=new ArrayList();
				PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
				StringBuffer bufInsSql1 =	new StringBuffer();
				bufInsSql1.append("insert into cr_deal_address_m(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,TAHSIL,BRANCH_DISTANCE)");
				bufInsSql1.append(" values ( ");
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), " );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(dbo);
				bufInsSql1.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)," );
				bufInsSql1.append(" ?," );
				bufInsSql1.append(" ? )" );
				
				
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAddr_type()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getBp_type()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addString((vo.getBp_id1()).trim()); 
				else
					insPrepStmtObject2.addString((vo.getBp_id()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAddr1()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAddr2()).trim());
				
				if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAddr3()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
				    insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getTxtCountryCode()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getTxtStateCode()).trim());
				
				if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getTxtDistCode()).trim());
				
				if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getPincode()).trim());
				
				if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getPrimaryPhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAlternatePhoneNo()).trim());
				
				if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getTollfreeNo()).trim());
				
				if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getFaxNo()).trim());
				
				if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getLandMark()).trim());
				
				if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getNoYears()).trim());
								
				if(CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getCommunicationAddress()).trim());
				
				if(CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getAddDetails()).trim());
				
				if(CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getNoMonths()).trim());
				
				insPrepStmtObject2.addString("A");
				//---------------------------------------------------------
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString((vo.getMakerId()).trim());
				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
				
				if ((CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString(CommonFunction.checkNull(vo.getTahsil()).trim());
				
				if ((CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
					insPrepStmtObject2.addNull();
				else
					insPrepStmtObject2.addString(CommonFunction.checkNull(vo.getDistanceBranch()).trim());
				//---------------------------------------------------------
				
				insPrepStmtObject2.setSql(bufInsSql1.toString());
				logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### "+insPrepStmtObject2.printQuery());
				qryList.add(insPrepStmtObject2);
		        //status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				//Ritu Code End
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomer......................"+status);
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}



	@Override
	public String getCommAddress(String tableName, String bpId) {
		String query="Select COMMUNICATION_ADDRESS from "+tableName+" where BPID="+bpId;
		String commAddressCheck=ConnectionDAO.singleReturn(query);
		return commAddressCheck;
	}
	
	
	public int deleteCustomerAddress(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag)
	{
		
            int status=0;
            boolean qryStatus = false;
            String tableName="";
            ArrayList qryList = new ArrayList();
   		
		try{
			
			if(pageStatus!=null || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
			{
				logger.info("In Credit Processing, Deal Capturing , deleteCustomerAddress().............. ");
				if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="com_address_m";
				}
				
				else
				{
					tableName="com_address_m_temp";
				}
				
			}
			else if(pageStatuss!=null && pageStatuss.equals("fromLeads") ||  (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
			{
				tableName="cr_deal_address_m";
			}
			else 
			{
				tableName="com_address_m";
			}
			String query="delete from "+tableName+" where ADDRESS_ID=?";
			PrepStmtObject insPrepStmtObject = new PrepStmtObject();
			if(CommonFunction.checkNull(addr_id).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(addr_id)).trim());
			
			insPrepStmtObject.setSql(query.toString());
	        logger.info("IN deleteSelectedCreditRating() update query1 ### "+insPrepStmtObject.printQuery());
			qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Deletion Status :."+qryStatus);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(qryStatus)
			{
				status=1;
			}
			return status;
}
	
	
	public int deleteCustomerReference(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag)
	{
		

		
        int status=0;
        boolean qryStatus = false;
        String tableName="";
        ArrayList qryList = new ArrayList();
		
	try{
		
		if(pageStatus!=null || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
		{
			logger.info("In Credit Processing, Deal Capturing , deleteCustomerReference().............. ");
			if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
			{
				tableName="com_reference_m";
			}
			
			else
			{
				tableName="com_reference_m_temp";
			}
			
		}
		else if(pageStatuss!=null && pageStatuss.equals("fromLeads") ||  (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			tableName="cr_deal_reference_m";
		}
		else 
		{
			tableName="com_reference_m";
		}
		String query="delete from "+tableName+" where REF_ID=?";
		PrepStmtObject insPrepStmtObject = new PrepStmtObject();
		if(CommonFunction.checkNull(addr_id).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(addr_id)).trim());
		
		insPrepStmtObject.setSql(query.toString());
        logger.info("IN deleteCustomerReference() update query1 ### "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("Deletion Status :."+qryStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;
}

	public int deleteStakeHolderDetails(String holderid,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag) 
	 {
       int status=0;
       boolean qryStatus = false;
       String tableName="";
       ArrayList qryList = new ArrayList();
	try{
		if( pageStatus!=null || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
		{
			
			if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
			{
				tableName="customer_stakeholder_m";
			}
			else
			{
				tableName="customer_stakeholder_m_temp";
			}
			
		}
		else if(pageStatuss!=null && pageStatuss.equals("fromLeads") || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			tableName="cr_deal_customer_stakeholder_m";
		}
		else
		{
			tableName="customer_stakeholder_m";
		}
		String query="delete from "+tableName+" where STAKEHOLDER_ID=?";
		PrepStmtObject insPrepStmtObject = new PrepStmtObject();
		if(CommonFunction.checkNull(holderid).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(holderid)).trim());
		
		insPrepStmtObject.setSql(query.toString());
       logger.info("IN deleteStakeHolderDetails() update query1 ### "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("Deletion Status :."+qryStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;
	 }

	public int deleteSelectedCreditRating(String ratingId,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag)
	{
		
        int status=0;
        boolean qryStatus = false;
        String tableName="";
        ArrayList qryList = new ArrayList();
	try{
		if(pageStatus!=null || (updateInMaker!=null && updateInMaker.equals("updateInMaker")) || (statusCase!=null && !statusCase.equals("")))
		{
			if((statusCase!=null && statusCase.equalsIgnoreCase("Approved")) && (updateInMaker==null || updateInMaker.equals("")))
			{
				tableName="customer_rating_m";
			}
			else
			{
				tableName="customer_rating_m_temp";
			}
			
		}
		else if(pageStatuss!=null && pageStatuss.equals("fromLeads") || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			tableName="cr_deal_customer_rating";
		}
		else
		{
			tableName="customer_rating_m";
		}
		
		String query="delete from "+tableName+" where CUSTOMER_RATING_ID=?";
		PrepStmtObject insPrepStmtObject = new PrepStmtObject();
		if(CommonFunction.checkNull(ratingId).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(ratingId)).trim());
		
		insPrepStmtObject.setSql(query.toString());
        logger.info("IN deleteSelectedCreditRating() update query1 ### "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("Deletion Status :."+qryStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;
}



	@Override
	public String getParamValForInd() {
		String paramQ="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='APPLICANT_CATEGORY_INDIVIDUAL'";
		logger.info("paramQ: "+paramQ);
		String optionIndv=ConnectionDAO.singleReturn(paramQ);
		return optionIndv;
	}

	
}

