
package com.gcd.daoImplMSSQL;

import java.sql.CallableStatement;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;











import org.apache.log4j.Logger;

import com.VO.ConstitutionVO;
import com.VO.CorporateBusinessActivityVO;
import com.VO.CreditRatingGridVo;
import com.VO.CreditRatingVo;
import com.VO.CustomerCategoryVO;
import com.VO.CustomerSaveVo;
import com.VO.DeatilOfCustomerAddress;
import com.VO.IndustryVO;
import com.VO.InstitutionNameVo;
import com.VO.RegistrationTypeVO;
import com.VO.StakeHolderTypeVo;
import com.VO.StakeHolderVo;
import com.VO.StakePositionVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.gcd.VO.CorporateDetailsVO;
import com.gcd.VO.ShowCustomerDetailVo;
import com.gcd.dao.CorporateDAO;

import org.apache.commons.lang.StringEscapeUtils;

public class MSSQLCorpotateDAOImpl implements CorporateDAO {
	private static final Logger logger = Logger.getLogger(MSSQLCorpotateDAOImpl.class.getName());	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	//String selectFrom = resource.getString("lbl.selectFrom");  
      Statement stmt=null;
      PreparedStatement pstmt=null;
      CallableStatement cstm=null;
      ResultSet rs=null;
      
      
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
	  	}
	  	catch(Exception e)
	  	{e.printStackTrace();}
	  	return list;
}
    
    public ArrayList<Object> getGenderList(){
	  
	  logger.info("in getGenderList() method***********************");    	
    	ArrayList<Object>   list=new ArrayList<Object>();
    	//StringBuffer query=new StringBuffer();
    	try{
    		 String query=("select VALUE,DESCRIPTION  from GENERIC_MASTER WHERE GENERIC_KEY='GENDER_INDIVIDUAL' and REC_STATUS ='A' ");
    		logger.info("query for gener:::::::::"+query);
    		ArrayList gender = ConnectionDAO.sqlSelect(query);
    		CustomerCategoryVO vo = null;
    		logger.info("sizew of gender list:::::::"+gender.size());
    		int size=gender.size();
	  		for(int i=0;i<size;i++)
	  		{
	  			ArrayList gender1=(ArrayList)gender.get(i);
	  			if(gender1.size()>0)
	  			{
	  				vo = new CustomerCategoryVO();
	  				vo.setGenderCode((CommonFunction.checkNull(gender1.get(0)).toString()));
	  				vo.setGenderDesc((CommonFunction.checkNull(gender1.get(1)).toString()));
	  				list.add(vo);	  				
	  			}    		
    		
	  		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		
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
    public ArrayList<Object> getEduDetail() 
    {
    	logger.info("in getEduDetail() of CorpotateDAOImpl. ");  		
		ArrayList<Object> list=new ArrayList<Object>();
	  	try
	  	{	  		
	  		String query="select value,description from generic_master where GENERIC_KEY='EDU_DETAIL' and rec_status ='A'";
	  		logger.info("in getEduDetail() of CorpotateDAOImpl Query :  "+query);
	  		CustomerCategoryVO vo = null;
	  		ArrayList source = ConnectionDAO.sqlSelect(query);
	  		logger.info("getEduDetail"+source.size());
	  		int size=source.size();
	  		for(int i=0;i<size;i++)
	  		{
	  			ArrayList subEduDetail=(ArrayList)source.get(i);
	  			if(subEduDetail.size()>0)
	  			{
	  				vo = new CustomerCategoryVO();
	  				vo.setEduDetailCode((CommonFunction.checkNull(subEduDetail.get(0)).toString()));
	  				vo.setEduDetailDesc((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
	  				list.add(vo);	  				
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
		
		
		ArrayList subRegistration=(ArrayList)source.get(i);
		if(subRegistration.size()>0)
		{
			
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
    //Nishant space starts
    public ArrayList<Object> getNatureOfBusinessList(){
    	
    	ArrayList<Object> list=new ArrayList<Object>();
    	try{
    	
    	String query="select value,description from generic_master where GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A'";
    	logger.info("in getNatureOfBusinessList() of CorpotateDAOImpl Query :  "+query);
    	RegistrationTypeVO vo = null;
    	ArrayList source = ConnectionDAO.sqlSelect(query);
    	logger.info("getNatureOfBusinessList "+source.size());
    	for(int i=0;i<source.size();i++){
    		
    		
    		ArrayList subRegistration=(ArrayList)source.get(i);
    		if(subRegistration.size()>0)
    		{
    			
    			vo = new RegistrationTypeVO();
    			vo.setNatureOfBusCode((CommonFunction.checkNull(subRegistration.get(0)).toString()));
    			vo.setNatureOfBusDesc((CommonFunction.checkNull(subRegistration.get(1)).toString()));
    			list.add(vo);
    		}
    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return list;
    	
    }
    //Nishant space end

    public ArrayList<Object> getContitutionList(){
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A'";
	logger.info("in getContitutionList() of CorpotateDAOImpl Query :  "+query);
	ConstitutionVO vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getCustomerCategoryList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
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

    public ArrayList<Object> getRegistrationTypeList(){
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_REG_TYPE' and rec_status ='A'";
	logger.info("in getRegistrationTypeList() of CorpotateDAOImpl Query :  "+query);
	RegistrationTypeVO vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getRegistrationTypeList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subRegistration=(ArrayList)source.get(i);
		if(subRegistration.size()>0)
		{
			
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


    public ArrayList<Object> getAddressList() {
	
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="select  VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'";
	logger.info("getAddressList"+query);
	RegistrationTypeVO vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getAddressList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subAddress=(ArrayList)source.get(i);
		if(subAddress.size()>0)
		{
			
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


    public ArrayList<Object> getCountryList() {
	
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="select COUNTRY_ID,COUNTRY_DESC from com_country_m";
	logger.info("getCountryList"+query);
	RegistrationTypeVO vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getCountryList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subCountry=(ArrayList)source.get(i);
		if(subCountry.size()>0)
		{
			
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


    public ArrayList<Object> getDetailAddressList(String addr) {
	
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="select  ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3 from com_address_m where ADDRESS_TYPE=?";
	logger.info("getDetailAddressList"+query);
	DeatilOfCustomerAddress vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getDetailAddressList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subDetailAddress=(ArrayList)source.get(i);
		if(subDetailAddress.size()>0)
		{
			
			vo = new DeatilOfCustomerAddress();
			vo.setAddr1((CommonFunction.checkNull(subDetailAddress.get(0)).toString()));
			vo.setAddr2((CommonFunction.checkNull(subDetailAddress.get(1)).toString()));
			vo.setAddr3((CommonFunction.checkNull(subDetailAddress.get(2)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
	
}


    public ArrayList<Object> getCountryDetail(String country) {
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="select STATE_ID,STATE_DESC from com_state_m where COUNTRY_ID="+country;
	logger.info("getCountryList  ...  "+query);
	DeatilOfCustomerAddress vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getCountryList"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subCountry=(ArrayList)source.get(i);
		if(subCountry.size()>0)
		{
			
			vo = new DeatilOfCustomerAddress();
			vo.setState_code((CommonFunction.checkNull(subCountry.get(0)).toString()));
			vo.setState_name((CommonFunction.checkNull(subCountry.get(1)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
}

public ArrayList<Object> getCityDetail(String country,String state) {
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
	
	String query="select DISTRICT_ID,DISTRICT_DESC from com_district_m where STATE_ID="+state;
	logger.info("getCityDetail"+query);
	DeatilOfCustomerAddress vo = null;
	ArrayList source = ConnectionDAO.sqlSelect(query);
	logger.info("getCityDetail"+source.size());
	for(int i=0;i<source.size();i++){
		
		
		ArrayList subCity=(ArrayList)source.get(i);
		if(subCity.size()>0)
		{
			
			vo = new DeatilOfCustomerAddress();
			vo.setDist_code((CommonFunction.checkNull(subCity.get(0)).toString()));
			vo.setDist_name((CommonFunction.checkNull(subCity.get(1)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
	
	
}


	public boolean saveCustomerAddress(Object ob) 
	{
		logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		ArrayList qryList = new ArrayList();
		String source=vo.getSource();
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
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
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
						insertPrepStmtObject.addString(""+myFormatter.parse(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
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
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
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
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomer......................"+status);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
				{
					ArrayList list=new ArrayList();
					ArrayList list2=new ArrayList();
					String q1=null,q2=null,q3=null;
					PrepStmtObject stmt = new PrepStmtObject();
					PrepStmtObject stmt2 = new PrepStmtObject();
					q1="select max(ADDRESS_ID) from com_address_m  WITH (ROWLOCK)  where BPID='"+vo.getBp_id()+"'";
					String addressId=ConnectionDAO.singleReturn(q1);
					q2="INSERT INTO com_address_m_edit select * from com_address_m WHERE ADDRESS_ID="+addressId;					
					stmt.setSql(q2);
					logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 for edit loan :  "+q2);
					list.add(stmt);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
					
					q3="delete from com_address_m WHERE ADDRESS_ID="+addressId;
					stmt2.setSql(q3);
					logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  Delete query :  "+q3);
					list2.add(stmt2);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
					list.clear();
					list=null;
					list2.clear();
					list2=null;
					q1=null;
					q2=null;
					q3=null;
					stmt=null;
					stmt2=null;					
				}
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}
	
	
	public boolean saveCustomerReference(Object ob) {
		logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		String source=vo.getSource();
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String customer="";
		if(!CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase("")&&CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase(""))
			customer=vo.getBp_id();
		if(!CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")&&CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
			customer=vo.getBp_id1();
		
		
		try
		{
			if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("insert into cr_deal_reference_m(BPID, BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
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
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
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
					
					if(CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddRef()).trim());
					
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
						bufInsSql.append("insert into com_reference_m_temp(REF_ID,BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS)");
			    		bufInsSql.append(" values ( ");
			    		bufInsSql.append(" ?," );
			    	}
			    	else
			    	{
			    		bufInsSql.append("insert into com_reference_m(BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
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
					bufInsSql.append(" ?, " );
					bufInsSql.append(" ? " );
			
					if(checkTable==0)
					{
						bufInsSql.append(" , ?," );	
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
						
						bufInsSql.append(" )" );
					}else
					{
						bufInsSql.append(" )" );
					}
					
					if(!vo.getStatusCase().equals(""))
			    	{
						insertPrepStmtObject.addInt(maxId);
			    	}
					insertPrepStmtObject.addString(customer); 
					
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
					
					if(CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getAddRef()).trim());
					
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
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomerReference......................"+status);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
			    {
			    	String q1="select max(ref_id) from com_reference_m  WITH (ROWLOCK)  where BPID="+customer;
			    	String ref_id=ConnectionDAO.singleReturn(q1);
			    	String q2="INSERT INTO com_reference_m_edit select * from com_reference_m WHERE ref_id="+ref_id;
			    	PrepStmtObject stmt = new PrepStmtObject();
			    	stmt.setSql(q2);
					ArrayList list =new ArrayList();
					list.add(stmt);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
					logger.info("In saveCustomerReference......................"+status);
					String q3="delete from com_reference_m WHERE ref_id="+ref_id;
			    	PrepStmtObject stmt2 = new PrepStmtObject();
			    	stmt2.setSql(q3);
					ArrayList list2 =new ArrayList();
					list2.add(stmt2);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);		    	
			    }
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}
	
	
	
	public boolean saveStakeHolder(Object ob,int id,String source) {
		
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
			bufInsSql.append("insert into cr_deal_customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,REC_STATUS,MAKER_ID,MAKER_DATE,CUSTOMER_ID,AUTHOR_ID,AUTHOR_DATE,STAKEHOLDER_PAN,ADDITIONAL_ID1,ADDITIONAL_ID2)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("?,");
			bufInsSql.append("?,");
			bufInsSql.append("? )");
			
						
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
			
			if ((CommonFunction.checkNull(vo.getMgmtPAN()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMgmtPAN()).trim());
			
			if ((CommonFunction.checkNull(vo.getAddId1()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId1()).trim());
			
			if ((CommonFunction.checkNull(vo.getAddId2()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId2()).trim());
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
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
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomer......................"+status);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
			    {
			    	
			    	String q1="select max(stakeholder_id) from customer_stakeholder_m  WITH (ROWLOCK)  where CUSTOMER_ID='"+id+"'";
			    	String stakID=ConnectionDAO.singleReturn(q1);
			    	String q2="INSERT INTO customer_stakeholder_m_edit select *  from customer_stakeholder_m WHERE STAKEHOLDER_ID="+stakID;
			    	PrepStmtObject stmt = new PrepStmtObject();
			    	stmt.setSql(q2);
			    	ArrayList list=new ArrayList();
			    	list.add(stmt);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
			    	logger.info("In Edit Insert Status  :   "+status);
			    	
			    	String q3="delete from customer_stakeholder_m WHERE STAKEHOLDER_ID="+stakID;
			    	PrepStmtObject stmt2 = new PrepStmtObject();
			    	stmt2.setSql(q3);
			    	ArrayList list2=new ArrayList();
			    	list2.add(stmt2);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
			    	
			    }
     		}
			}catch(Exception e){
				e.printStackTrace();
			}
		
			return status;
		
	}
	
	private java.sql.Date getSqlDate(String ddMMyyyy){
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		java.sql.Date sqlDate = null;
		try{
			sqlDate = new java.sql.Date(dateFormat.parse(ddMMyyyy).getTime());
		}catch(Exception e){
			logger.info("getSqlDate "+e.getMessage());
		}
		return sqlDate;
	
	}

	public boolean saveCreditRating(Object ob,int id,String source) {
		
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
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
								
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
			}
			else{
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"') )" );
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
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCreditRating......................"+status);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
			    {
			    	String q1="select max(customer_rating_id) from customer_rating_m  WITH (ROWLOCK)  where CUSTOMER_ID="+id;
			    	String ratingID=ConnectionDAO.singleReturn(q1);
			    	String query="INSERT INTO customer_rating_m_edit select * from customer_rating_m WHERE CUSTOMER_RATING_ID="+ratingID;
			    	PrepStmtObject stmt = new PrepStmtObject();
			    	stmt.setSql(query);
			    	ArrayList list=new ArrayList();
			    	list.add(stmt);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
			    	logger.info("Edit Insert Status  :  "+status);
			    	String query2="delete from customer_rating_m WHERE CUSTOMER_RATING_ID="+ratingID;
			    	PrepStmtObject stmt2 = new PrepStmtObject();
			    	stmt2.setSql(query2);
			    	ArrayList list2=new ArrayList();
			    	list2.add(stmt2);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);			    	
			    }
				
				
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		
			return status;

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
  			
  			
  			ArrayList subPosition=(ArrayList)source.get(i);
  			if(subPosition.size()>0)
  			{
  				
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
	
	
	
	
	public ArrayList<Object> getHolderTypeList() {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
  		try{
  		
  		String query="select value,description from generic_master where generic_key='STAKEHOLDER_TYPE' and rec_status ='A'";
  		logger.info("getHolderList"+query);
  		StakeHolderTypeVo vo = null;
  		ArrayList source = ConnectionDAO.sqlSelect(query);
  		logger.info("getHolderList"+source.size());
  		for(int i=0;i<source.size();i++){
  			
  			
  			ArrayList subHolder=(ArrayList)source.get(i);
  			if(subHolder.size()>0)
  			{
  				
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
  			
  			
  			ArrayList subHolder=(ArrayList)source.get(i);
  			if(subHolder.size()>0)
  			{
  				
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
		
	public int saveCorporateDetails(Object ob,String st,String dealId,String source)
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

						//bufInsSql1.append("CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,RELATIONSHIP)");

						bufInsSql1.append("CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,RELATIONSHIP,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID," +
								"NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS)");

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
						bufInsSql1.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						bufInsSql1.append("?, ");
						bufInsSql1.append(dbo);
						bufInsSql1.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						bufInsSql1.append("?, ");//FATHER_HUSBAND_NAME
						bufInsSql1.append("?, ");//CASTE_CATEGORY
						bufInsSql1.append("?, ");//MARITAL_STATUS
						bufInsSql1.append("?, ");//DRIVING_LICENSE
						bufInsSql1.append("?, ");//VOTER_ID

						bufInsSql1.append("?, ");//PASSPORT_NUMBER
						bufInsSql1.append("?, ");//UID_NO
						bufInsSql1.append("?, ");//OTHER_NO
						bufInsSql1.append("?, ");//EDU_DETAIL
						bufInsSql1.append("?, ");//CUSTOMER_GROUP_TYPE
						bufInsSql1.append("?,");//CUSTOMER_GROUP_DESC
						bufInsSql1.append("?, ");// RELATIONSHIP
						bufInsSql1.append("?, ");// GENDER
						bufInsSql1.append("?, ");//OTHOR_RELATIONSHIP_TYPE
						bufInsSql1.append("?, "); //OTHER_RELATIONSHIP_ID
						bufInsSql1.append("?, ");//NATURE_OF_BUSINESS
						bufInsSql1.append("?, ");//YEAR_OF_ESTBLISHMENT
						bufInsSql1.append("?, ");//SHOP_ESTABLISHMENT_NO
						bufInsSql1.append("?, ");//SALES_TAX_TIN_NO
						bufInsSql1.append("?, ");//DGFT_NO
						bufInsSql1.append("?, ");//NO_BV_YEARS
						bufInsSql1.append("? ) ");//NO_BV_MONTHS

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
						
						//Start by KK
						
						if(CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getAadhaar()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getOther()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getEduDetailInd()).trim());

						//End by KK	
				// START BY PRASHANT		
						if(CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getGroupType()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getGroupNameText()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getGroupNameText()).trim());
				// END BY PRASHANT		
						if(CommonFunction.checkNull(corporateDetailVo.getRelationShip()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getRelationShip()).trim());
						//------------------------------------------
					//SAURABH 
						if(CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getGenderIndividual()).trim());
						//RAVI start
						if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getOtherRelationShip()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getLbxBusinessPartnearHID()).trim());
						//RAVI end
						
						//Nishant starts
						if(CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getNatureOfBus()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getYearOfEstb()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getShopEstbNo()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getSalesTax()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getDgftNo()).trim());
						//Nishant End
						//KK Start
						if(CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getNoBVYears()).trim());
						if(CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
							insPrepStmtObject2.addNull();
						else
							insPrepStmtObject2.addString((corporateDetailVo.getNoBVMonths()).trim());
						//KK End
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
								//if( cv.getHiApplType()!=null && !cv.getHiApplType().equals("PRAPPL"))
								if(!CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase("PRAPPL"))
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
									bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
									
																		
																		
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
								//else if(cv.getHiApplType()!=null && cv.getHiApplType().equals("PRAPPL"))
								else if(CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase("PRAPPL"))
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
									 
						            if(status){
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
										bufInsSql2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
										
							           										
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
			bufInsSql.append("insert into gcd_customer_m(CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_STATUS,GROUP_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID," +
					"NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			bufInsSql.append("?, ");//FATHER_HUSBAND_NAME
			bufInsSql.append("?, ");//CASTE_CATEGORY
			bufInsSql.append("?, ");//MARITAL_STATUS
			bufInsSql.append("?, ");//DRIVING_LICENSE
			bufInsSql.append("?, ");//VOTER_ID
			bufInsSql.append("?, ");//PASSPORT_NUMBER
			bufInsSql.append("?, ");//UID_NO
			bufInsSql.append("?, ");//OTHER_NO
			bufInsSql.append("?, ");//EDU_DETAIL
			bufInsSql.append("?, ");// GENDER
			bufInsSql.append("?, ");//OTHOR_RELATIONSHIP_TYPE
			bufInsSql.append("?, "); //OTHER_RELATIONSHIP_ID
			bufInsSql.append("?, ");//NATURE_OF_BUSINESS
			bufInsSql.append("?, ");//YEAR_OF_ESTBLISHMENT
			bufInsSql.append("?, ");//SHOP_ESTABLISHMENT_NO
			bufInsSql.append("?, ");//SALES_TAX_TIN_NO
			bufInsSql.append("?, ");//DGFT_NO
			bufInsSql.append("?, ");//NO_BV_YEARS
			bufInsSql.append("? ) ");//NO_BV_MONTHS
			
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
			
			//Start by KK
			
			if(CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getAadhaar()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getOther()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getEduDetailInd()).trim());
			//End by KK
			
			//saurabh
			if(CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getGenderIndividual()).trim());
			
			//RAVI start
			if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getOtherRelationShip()).trim());
			
			if(CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getLbxBusinessPartnearHID()).trim());
			//RAVI end
			//logger.info("ffffffffffffffffffffffffffffffffff"+corporateDetailVo.getMakerDate());
			//logger.info("*********************************"+corporateDetailVo.getMakerId());
			
			//Nishant starts
			if(CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getNatureOfBus()).trim());
			if(CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getYearOfEstb()).trim());
			if(CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getShopEstbNo()).trim());
			if(CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getSalesTax()).trim());
			if(CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getDgftNo()).trim());
			//Nishant End
			//KK Start
			if(CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getNoBVYears()).trim());
			if(CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((corporateDetailVo.getNoBVMonths()).trim());
			//KK End
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveCorporateDetails() insert query1 ### "+insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				 boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				 String gcdID=ConnectionDAO.singleReturn("select max(CUSTOMER_ID) from gcd_customer_m  WITH (ROWLOCK) ");
				 maxId=Integer.parseInt(gcdID);
						
				 logger.info("IN saveCorporateDetails() gcdID ### "+gcdID);
				 
				    StringBuffer bufInsSqlLoan = new StringBuffer();
					PrepStmtObject insPrepStmtObjectLoan = new PrepStmtObject();
					ArrayList queryListLoan = new ArrayList();
					bufInsSqlLoan.append("Insert into cr_loan_customer_role(LOAN_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GCD_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					
					bufInsSqlLoan.append(" values( ");
				
					bufInsSqlLoan.append(" ?," );
			
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(" ?," );
					bufInsSqlLoan.append(dbo);
					bufInsSqlLoan.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					
					if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
						insPrepStmtObjectLoan.addNull();
					else
						insPrepStmtObjectLoan.addString((dealId).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getHiApplType()).trim().equalsIgnoreCase(""))
						insPrepStmtObjectLoan.addNull();
					else
						insPrepStmtObjectLoan.addString((corporateDetailVo.getHiApplType()).trim());
					
					if(st.equalsIgnoreCase("c"))
					 {
						insPrepStmtObjectLoan.addString("C");
					 }
					 else if(st.equalsIgnoreCase("I"))
					 {
						 insPrepStmtObjectLoan.addString("I");
					 }
					
					insPrepStmtObjectLoan.addString("N");	
					insPrepStmtObjectLoan.addString(gcdID);
					insPrepStmtObjectLoan.addString("P");
						
						if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
							insPrepStmtObjectLoan.addNull();
						else
							insPrepStmtObjectLoan.addString((corporateDetailVo.getMakerId()).trim());
						
						if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
							insPrepStmtObjectLoan.addNull();
						else
							insPrepStmtObjectLoan.addString((corporateDetailVo.getMakerDate()).trim());
//						insertPrepStmtObject2.addNull();
//						insertPrepStmtObject2.addNull();
					
					logger.info("In customer...........4444444.  "+corporateDetailVo.getCorporateName()+"   ..................Dao Impl"+corporateDetailVo.getFirstName());
					insPrepStmtObjectLoan.setSql(bufInsSqlLoan.toString());				  
					logger.info("IN customer insert at loan query1 ### ***********************"+insPrepStmtObjectLoan.printQuery());
					queryListLoan.add(insPrepStmtObjectLoan);		           
		            status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryListLoan);   
					
		        
			    logger.info("In saveCorporateDetails......................"+status);
				
				//code added by neeraj
	            if(status && CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
	            {
	            	ArrayList queryList=new ArrayList();
	            	ArrayList queryList2=new ArrayList();
	            	String q1=null,q5=null,q6=null,d1=null,d2=null,query=null;
	            	PrepStmtObject istmt = new PrepStmtObject();
	            	PrepStmtObject stmt = new PrepStmtObject();
	            	PrepStmtObject istmt2 = new PrepStmtObject();
	            	PrepStmtObject istmt3 = new PrepStmtObject();
	            	
	            	String roleID="";
	            	q5="SELECT MAX(LOAN_CUSTOMER_ROLE_ID) FROM cr_loan_customer_role WHERE LOAN_ID="+dealId;
	            	roleID=ConnectionDAO.singleReturn(q5);           
	            	q6="Insert Into cr_loan_customer_role_edit(LOAN_CUSTOMER_ROLE_ID,LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)SELECT LOAN_CUSTOMER_ROLE_ID,LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,'P',MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE FROM cr_loan_customer_role WHERE LOAN_CUSTOMER_ROLE_ID="+roleID;
	            	
	            	istmt.setSql(q6);	
	            	queryList.add(istmt);			            	
	            	q1="SELECT EDIT_REFRESH_FLAG FROM cr_loan_dtl WHERE LOAN_ID="+dealId;
	            	logger.info("IN saveCorporateDetails() of CorpotateDAOImpl  q1  :"+q1);
	            	String refreshFlag=ConnectionDAO.singleReturn(q1);
	            	if(CommonFunction.checkNull(refreshFlag).trim().equalsIgnoreCase(""))
	            		refreshFlag=" ";
	            	char fchar=refreshFlag.charAt(0);
	            	String refreshFlagNew=fchar+"NY";
	            	query="INSERT INTO gcd_customer_m_edit select * from gcd_customer_m WHERE CUSTOMER_ID="+gcdID;
	            	logger.info("IN saveCorporateDetails() of CorpotateDAOImpl  query  : "+query);
	            	stmt.setSql(query);	
	            	queryList.add(stmt);	
	            	StringBuffer updatLoan = new StringBuffer();
	            	updatLoan.append("update cr_loan_dtl set EDIT_REC_STATUS='P',EDIT_REFRESH_FLAG='"+refreshFlagNew+"'," +
	            			" EDIT_MAKER_ID=?," +
	            			" EDIT_MAKER_DATE="+dbo+"STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) " +
	            			" where loan_id="+dealId);
	            	PrepStmtObject prepStmt = new PrepStmtObject();
	            	if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
	            		prepStmt.addNull();
	            	else
	            		prepStmt.addString((corporateDetailVo.getMakerId()).trim());
	            	
	            	if(CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
	            		prepStmt.addNull();
	            	else
	            		prepStmt.addString((corporateDetailVo.getMakerDate()).trim());
	            	
	            	prepStmt.setSql(updatLoan.toString());	
	            	logger.info("IN saveCorporateDetails() of CorpotateDAOImpl  update query :  "+prepStmt.printQuery());
	            	queryList.add(prepStmt);		           
	            	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList); 	
	            	d1="delete from gcd_customer_m WHERE CUSTOMER_ID="+gcdID;	            	
	            	istmt2.setSql(d1);
	            	queryList2.add(istmt2);
	            	d2="delete from gcd_customer_m WHERE CUSTOMER_ID="+gcdID;
	            	istmt3.setSql(d2);	
	            	queryList2.add(istmt3);
	            	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList2);  	            	
	            	queryList.clear();
	            	queryList=null;
	            	queryList2.clear();
	            	queryList2=null;
	            	q1=null;q5=null;q6=null;d1=null;d2=null;query=null;
	            	istmt=null;
	            	stmt=null;
	            	istmt2=null;
	            	istmt3=null;
	            }
	            //neeraj space end
			  }
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
			return maxId;
	}
	
	public int setApproveStatus(int cId, String statusCase,String userId,String businessDate)
	{
		int status=0;
		boolean qrystatus=false;
			try{
				logger.info("CorporateDAOImpl setApproveStatus()");
				StringBuilder query = new StringBuilder();
				//String query="";
				if(statusCase.equalsIgnoreCase("Approved") || statusCase.equalsIgnoreCase("UnApproved"))
					
				{
					 query.append("update gcd_customer_m_temp set CUSTOMER_STATUS='F' where CUSTOMER_ID=?");
				}
				else
				{
					query.append("update gcd_customer_m set CUSTOMER_STATUS='A',MAKER_ID='"+CommonFunction.checkNull(userId)+"',MAKER_DATE=");
					query.append(dbo);
					query.append("STR_TO_DATE('"+CommonFunction.checkNull(businessDate)+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
					query.append(" where CUSTOMER_ID=?");
				}
				
				PrepStmtObject prepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				if(CommonFunction.checkNull(cId).equalsIgnoreCase(""))
					prepStmtObject.addNull();
				else
					prepStmtObject.addInt(cId);
				
				prepStmtObject.setSql(query.toString());
				qryList.add(prepStmtObject);
				logger.info("query"+query);
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
	
public ArrayList<Object> getIndustryList(){
	
	ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="SELECT INDUSTRY_ID,INDUSTRY_DESC from com_industry_m";
		logger.info("in getIndustryList() of CorpotateDAOImpl Query :  "+query);
		IndustryVO vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query);
		logger.info("getIndustryList"+source.size());
		for(int i=0;i<source.size();i++){
			
			
			ArrayList subIndustry=(ArrayList)source.get(i);
			if(subIndustry.size()>0)
			{
				
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


public ArrayList<Object> getSubIndustryList(String industry){
	
	ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="SELECT SUB_INDUSTRY_ID,SUB_INDUSTRY_DESC from com_sub_industry_m" +
     " where INDUSTRY_ID="+industry;
		logger.info("getSubIndustryList"+query);
		IndustryVO vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query);
		logger.info("getSubIndustryList"+source.size());
		for(int i=0;i<source.size();i++){
			
			
			ArrayList subIndustry=(ArrayList)source.get(i);
			if(subIndustry.size()>0)
			{
				
				vo = new IndustryVO();
				vo.setSubIndustryCode((CommonFunction.checkNull(subIndustry.get(0)).toString()));
				vo.setSubIndustryName((CommonFunction.checkNull(subIndustry.get(1)).toString()));
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	
	
}

//ravindra 1-april  neeraj 31-march-2012
public ArrayList<Object> getCustomerDetailList(String code,String name,String pageStatus, String recStatus, String statusCase, int currentPageLink) 
{
		logger.info("in getCustomerDetailList() of CorpotateDAOImpl");
		ArrayList<Object> list=new ArrayList<Object>();
		String cust_tableName="";
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		String query="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
		logger.info("code id ................................ "+code);
        logger.info("name ................................ "+name);
        logger.info("pageStatus... "+pageStatus+" recStatus: "+recStatus+" statusCase: "+statusCase);
       
		if((pageStatus!=null && pageStatus.equals("approve") || (statusCase!=null && statusCase.equals("UnApproved"))))
			cust_tableName="gcd_customer_m_temp";
		else
			cust_tableName="gcd_customer_m";
		
		String custStatus="";
		if(recStatus.equals("PC")||recStatus.equals("PI"))
			custStatus="P";
		else 
		{
			if(recStatus.equals("A"))
			{
				if(statusCase.equals("Approved"))
					custStatus="A";
				if(statusCase.equals("UnApproved"))
					custStatus="P";
				if(statusCase.equals(""))
					custStatus="F";
			}
		}
			
				
		try
		{
			
			bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT ");
			
			bufInsSql.append(" from "+cust_tableName+" where CUSTOMER_STATUS='"+custStatus+"'"); 
			bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from "+cust_tableName+" where CUSTOMER_STATUS='"+custStatus+"'");
			if(recStatus.equals("A") && statusCase.equals("Approved"))
			{
				bufInsSql.append(" and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )"); 
				bufInsSqlTempCount.append(" and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )");
			}
			if(!code.equalsIgnoreCase(""))
			{
				bufInsSql.append(" and CUSTOMER_ID="+code);
				bufInsSqlTempCount.append(" and CUSTOMER_ID="+code);
			}
			if(!name.equalsIgnoreCase(""))
			{
				bufInsSql.append(" and CUSTOMER_NAME like '%"+name+"%'");
				bufInsSqlTempCount.append(" and CUSTOMER_NAME like '%"+name+"%'");
			}
			if(recStatus.equals("PC"))
			{
				bufInsSql.append(" and CUSTOMER_TYPE='C'");
				bufInsSqlTempCount.append(" and CUSTOMER_TYPE='C'");
			}
			if(recStatus.equals("PI"))
			{
				bufInsSql.append(" and CUSTOMER_TYPE='I'");
				bufInsSqlTempCount.append(" and CUSTOMER_TYPE='I'");
			}
			bufInsSqlTempCount.append(" ) as b ");
			logger.info("current PAge Link no .................... "+currentPageLink);		
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY CUSTOMER_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
		//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
			logger.info("in getCustomerDetailList() of CorpotateDAOImpl  search query : "+bufInsSql.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("count   :      "+count);
			ArrayList customerDetailList = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size=customerDetailList.size();
			logger.info("Size  :  "+size);
			for(int i=0;i<size;i++)
			{
				ArrayList data=(ArrayList)customerDetailList.get(i);
				if(data.size()>0)	
				{
					ShowCustomerDetailVo show=new ShowCustomerDetailVo();
					if(recStatus.equals("PC"))
						show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType=C>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
					if(recStatus.equals("PI"))
						show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType=I>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
					if(recStatus.equals("A"))
						show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType="+(CommonFunction.checkNull(data.get(2)).trim())+"&recStatus=A>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
					//show.setId((CommonFunction.checkNull(data.get(0)).toString()));
					if(((CommonFunction.checkNull(data.get(2)).toString())).equals("C"))
					{
						show.setfCustType("CORPORATE");
						show.setName((CommonFunction.checkNull(data.get(1)).toString()));
					}
					else
					{
						show.setfCustType("INDIVIDUAL");
						show.setName((CommonFunction.checkNull(data.get(1)).toString()));
					}
					show.setType((CommonFunction.checkNull(data.get(2)).toString()));
					show.setCustContitution((CommonFunction.checkNull(data.get(3)).toString()));
					show.setBusinessSegment((CommonFunction.checkNull(data.get(4)).toString()));
					show.setTotalRecordSize(count);
					list.add(show);
				}
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		return list;	
	}
//neeraj code for default grid in gcd
public ArrayList<ShowCustomerDetailVo> getDefaultIndividualList(int currentPageLink)
{
	logger.info("in getDefaultIndividualList() of CorpotateDAOImpl().");
	ArrayList<ShowCustomerDetailVo> resultList=new ArrayList<ShowCustomerDetailVo>();
	ArrayList<Object> list=new ArrayList<Object>();
	StringBuffer bufInsSql = new StringBuffer();
	StringBuffer bufInsSqlTempCount = new StringBuffer();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	try
	{
		bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION from gcd_customer_m where CUSTOMER_STATUS='P' and CUSTOMER_TYPE='I'");
		bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION from gcd_customer_m where CUSTOMER_STATUS='P' and CUSTOMER_TYPE='I' ) as b");
		
		logger.info("current PAge Link no .................... "+currentPageLink);		
		if(currentPageLink>1)
		{
			startRecordIndex = (currentPageLink-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		bufInsSql.append(" ORDER BY CUSTOMER_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
	//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		
			
		logger.info("in getDefaultIndividualList() of CorpotateDAOImpl  Query : "+bufInsSql.toString());
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		list = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=list.size();
		logger.info("size  :  "+size);
		for(int i=0;i<size;i++)
		{
			ArrayList data=(ArrayList)list.get(i);
			if(data.size()>0)	
			{
				ShowCustomerDetailVo show=new ShowCustomerDetailVo();
				show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType=I&recStatus=PI>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
				show.setName((CommonFunction.checkNull(data.get(1)).toString()));
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("I"))
					show.setfCustType("INDIVIDUAL");
				show.setCustContitution((CommonFunction.checkNull(data.get(3)).toString()));
				show.setTotalRecordSize(count);
				resultList.add(show);
			}
			
		}
	}
	catch(Exception e)
	{e.printStackTrace();}
	return resultList;
}

public ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerAuthorList(int currentPageLink, String userId)
{
	logger.info("in getDefaultUpdateCustomerAuthorList() of CorpotateDAOImpl().");
	ArrayList<ShowCustomerDetailVo> resultList=new ArrayList<ShowCustomerDetailVo>();
	ArrayList<Object> list=new ArrayList<Object>();
	StringBuffer bufInsSql = new StringBuffer();
	StringBuffer bufInsSqlTempCount = new StringBuffer();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	try
	{
		bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m_temp where CUSTOMER_STATUS='F' AND ISNULL(MAKER_ID,'')!='"+CommonFunction.checkNull(userId)+"'");
		bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m_temp where CUSTOMER_STATUS='F' AND ISNULL(MAKER_ID,'')!='"+CommonFunction.checkNull(userId)+"') as b");
		
		logger.info("current PAge Link no .................... "+currentPageLink);		
		if(currentPageLink>1)
		{
			startRecordIndex = (currentPageLink-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		bufInsSql.append(" ORDER BY CUSTOMER_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");	
	//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		
			
		logger.info("in getDefaultUpdateCustomerAuthorList() of CorpotateDAOImpl  Query : "+bufInsSql.toString());
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		list = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=list.size();
		logger.info("size  :  "+size);
		for(int i=0;i<size;i++)
		{
			ArrayList data=(ArrayList)list.get(i);
			if(data.size()>0)	
			{
				ShowCustomerDetailVo show=new ShowCustomerDetailVo();
				show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType="+(CommonFunction.checkNull(data.get(2)).trim())+"&recStatus=A>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
				show.setName((CommonFunction.checkNull(data.get(1)).toString()));
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("C"))
					show.setfCustType("CORPORATE");
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("I"))
					show.setfCustType("INDIVIDUAL");
				show.setCustContitution((CommonFunction.checkNull(data.get(3)).toString()));
				show.setBusinessSegment((CommonFunction.checkNull(data.get(4)).toString()));
				show.setTotalRecordSize(count);
				resultList.add(show);
			}
			
		}
	}
	catch(Exception e)
	{e.printStackTrace();}
	return resultList;	
}
public ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerMakerList(int currentPageLink)
{
	logger.info("in getDefaultUpdateCustomerMakerList() of CorpotateDAOImpl().");
	ArrayList<ShowCustomerDetailVo> resultList=new ArrayList<ShowCustomerDetailVo>();
	ArrayList<Object> list=new ArrayList<Object>();
	StringBuffer bufInsSql = new StringBuffer();
	StringBuffer bufInsSqlTempCount = new StringBuffer();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	try
	{
		bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='A' and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )");
		bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='A' and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )) as b");
		
		logger.info("current PAge Link no .................... "+currentPageLink);		
		if(currentPageLink>1)
		{
			startRecordIndex = (currentPageLink-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		bufInsSql.append(" ORDER BY CUSTOMER_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
//		bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		
			
		logger.info("in getDefaultUpdateCustomerMakerList() of CorpotateDAOImpl  Query : "+bufInsSql.toString());
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		list = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=list.size();
		logger.info("size  :  "+size);
		for(int i=0;i<size;i++)
		{
			ArrayList data=(ArrayList)list.get(i);
			if(data.size()>0)	
			{
				ShowCustomerDetailVo show=new ShowCustomerDetailVo();
				show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType="+(CommonFunction.checkNull(data.get(2)).trim())+"&recStatus=A>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
				show.setName((CommonFunction.checkNull(data.get(1)).toString()));
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("C"))
					show.setfCustType("CORPORATE");
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("I"))
					show.setfCustType("INDIVIDUAL");
				show.setCustContitution((CommonFunction.checkNull(data.get(3)).toString()));
				show.setBusinessSegment((CommonFunction.checkNull(data.get(4)).toString()));
				show.setTotalRecordSize(count);
				resultList.add(show);
			}
			
		}
	}
	catch(Exception e)
	{e.printStackTrace();}
	return resultList;
}
public ArrayList<ShowCustomerDetailVo> getDefaultCorporateList(int currentPageLink)
{
	logger.info("in getDefaultIndividualList() of CorpotateDAOImpl().");
	ArrayList<ShowCustomerDetailVo> resultList=new ArrayList<ShowCustomerDetailVo>();
	ArrayList<Object> list=new ArrayList<Object>();
	StringBuffer bufInsSql = new StringBuffer();
	StringBuffer bufInsSqlTempCount = new StringBuffer();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	try
	{
		bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='P' and CUSTOMER_TYPE='C'");
		bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='P' and CUSTOMER_TYPE='C') as b");
		
		logger.info("current PAge Link no .................... "+currentPageLink);		
		if(currentPageLink>1)
		{
			startRecordIndex = (currentPageLink-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		bufInsSql.append(" ORDER BY CUSTOMER_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
	//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		
			
		logger.info("in getDefaultCorporateList() of CorpotateDAOImpl  Query : "+bufInsSql.toString());
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		list = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=list.size();
		logger.info("size  :  "+size);
		for(int i=0;i<size;i++)
		{
			ArrayList data=(ArrayList)list.get(i);
			if(data.size()>0)	
			{
				ShowCustomerDetailVo show=new ShowCustomerDetailVo();
				show.setId("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+(CommonFunction.checkNull(data.get(0)).trim())+"&cType=C&recStatus=PC>"+(CommonFunction.checkNull(data.get(0)).trim())+"</a>");
				show.setName((CommonFunction.checkNull(data.get(1)).toString()));
				if(((CommonFunction.checkNull(data.get(2)).toString())).equals("C"))
					show.setfCustType("CORPORATE");
				show.setCustContitution((CommonFunction.checkNull(data.get(3)).toString()));
				show.setBusinessSegment((CommonFunction.checkNull(data.get(4)).toString()));
				show.setTotalRecordSize(count);
				resultList.add(show);
			}
			
		}
	}
	catch(Exception e)
	{e.printStackTrace();}
	return resultList;
}

	public ArrayList<Object> getIndividualDetails(String code, Object pageStatus,String updateInMaker,String statusCase,String updateFlag,String pageStatuss,String source)  
	{
		logger.info("In getIndividualDetails() of CortateDAOImpl.");
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";
		StringBuilder query= new StringBuilder();
		StringBuilder str = new StringBuilder();
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			tableName="cr_deal_customer_m";
			try
			{
				String custStatus="";
				String OthRelType="";
				custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+code);
				OthRelType = ConnectionDAO.singleReturn("SELECT OTHOR_RELATIONSHIP_TYPE FROM cr_deal_customer_m WHERE CUSTOMER_ID="+code+"");
				
				
				if(OthRelType.equalsIgnoreCase("CS"))
				{
					str.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
					str.append(dbo);
					str.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,"+
					 		" PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,RELATIONSHIP,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID " +
					      " from "+tableName+" where CUSTOMER_ID="+code+"");
					
				}
				else
				{
					str.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
					str.append(dbo);
					str.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,"+
					 		" PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,RELATIONSHIP,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,d.dealer_desc " +
					      " from "+tableName+",cr_dsa_dealer_m d where CUSTOMER_ID="+code+" and OTHOR_RELATIONSHIP_TYPE=d.bp_type and OTHER_RELATIONSHIP_ID=d.dealer_id");
					
				}
				logger.info("customer status ................................................. "+custStatus);
				
				 logger.info("In getIndividualDetails() of CortateDAOImpl.  Query : "+str.toString());
				ArrayList individualDetails = ConnectionDAO.sqlSelect(str.toString());
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
						show.setAadhaar((CommonFunction.checkNull(data.get(19)).toString()));
						show.setOther((CommonFunction.checkNull(data.get(20)).toString()));
						show.setEduDetailInd((CommonFunction.checkNull(data.get(21)).toString()));
						show.setRelationShip((CommonFunction.checkNull(data.get(22)).toString()));
						show.setGenderIndividual((CommonFunction.checkNull(data.get(23)).toString()));
						show.setOtherRelationShip((CommonFunction.checkNull(data.get(24)).toString()));
						show.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(25)).toString()));
						if(OthRelType.equalsIgnoreCase("CS"))
						{
							show.setBusinessPartnerTypeDesc("");
						}
						else
						{
							show.setBusinessPartnerTypeDesc((CommonFunction.checkNull(data.get(26)).toString()));
						}
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
				{
						tableName="gcd_customer_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase(""))
							tableName="gcd_customer_m_edit";
				}
				try
				{	
					String OthRelType="";
					OthRelType = ConnectionDAO.singleReturn("SELECT OTHOR_RELATIONSHIP_TYPE FROM "+tableName+" WHERE CUSTOMER_ID="+code+"");
					
					
					if(!OthRelType.equalsIgnoreCase("")&&OthRelType.equalsIgnoreCase("CS"))
					{
						query.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
						query.append(dbo);
						query.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_STATUS,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID "+ 
						" ,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID  " +
				      "from "+tableName+" where CUSTOMER_ID="+code+"" );
					}
					else
					{
						query.append("select CUSTOMER_ID, CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_EMAIL,");
						query.append(dbo);
						query.append("DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"'),CUSTMER_PAN,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_CATEGORY,CUSTOMER_TYPE,CUSTOMER_STATUS,CUSTOMER_CONSTITUTION,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID "+ 
						" ,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,d.dealer_desc " +
				      "from "+tableName+",cr_dsa_dealer_m d where CUSTOMER_ID="+code+" and OTHOR_RELATIONSHIP_TYPE=d.bp_type and OTHER_RELATIONSHIP_ID=d.dealer_id" );
					}
					
					 logger.info("In getIndividualDetails() of CortateDAOImpl.  Query next: "+query);
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
							show.setAadhaar((CommonFunction.checkNull(data.get(20)).toString()));
							show.setOther((CommonFunction.checkNull(data.get(21)).toString()));
							show.setEduDetailInd((CommonFunction.checkNull(data.get(22)).toString()));
							show.setGenderIndividual((CommonFunction.checkNull(data.get(23)).toString()));
							show.setOtherRelationShip((CommonFunction.checkNull(data.get(24)).toString()));
							show.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(25)).toString()));
							if(OthRelType.equalsIgnoreCase("CS"))
							{
								show.setBusinessPartnerTypeDesc("");
							}
							else
							{
								show.setBusinessPartnerTypeDesc((CommonFunction.checkNull(data.get(26)).toString()));
							}
							list.add(show);
						}
					}
				}catch(Exception e)
				{e.printStackTrace();}
		    }
			return list;	
	}

	
	public ArrayList<Object> getReferenceAll(String code, Object pageStatus,String updateFlag,String source) {
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
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="com_reference_m_edit";
		}
		
		try{
				StringBuilder query = new StringBuilder();
				query.append("select REF_ID,(F_NAME+' '+ISNULL(M_NAME,'')+' '+L_NAME)NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER from "+tableName+"  where BPID="+code);
				logger.info("................. from "+tableName+"  table............................query "+query.toString());
				ArrayList addressAll = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getReferenceAll : : :  "+addressAll.size());
				for(int i=0;i<addressAll.size();i++){
					
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
	
	public ArrayList<Object> getAdressAll(String code, Object pageStatus,String updateFlag,String source) {
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
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="com_address_m_edit";
			}
		
		try{
			String query="SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC "+
				              "from "+tableName+" c,generic_master m,com_country_m p,com_district_m d,com_state_m s"+
	                           " where m.VALUE=c.ADDRESS_TYPE AND m.GENERIC_KEY='ADDRESS_TYPE' and P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID="+code;
			logger.info("...........................................................query "+query);
			ArrayList addressAll = ConnectionDAO.sqlSelect(query);
			logger.info("getAdressAll "+addressAll.size());
			for(int i=0;i<addressAll.size();i++){
				
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


	public boolean saveCorporateUpdate(Object ob, int id, String recStatus, String statusCase,String updateFlag,String pageStatuss,String source) 
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
					StringBuilder queryUpdate=new StringBuilder();
					customerType= ConnectionDAO.singleReturn("select CUSTOMER_TYPE from cr_deal_customer_m where CUSTOMER_ID="+id);
					PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
					ArrayList qryList2 = new ArrayList();
					logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
					queryUpdate.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"'),");
					queryUpdate.append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,");
					queryUpdate.append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,");
					queryUpdate.append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					
					queryUpdate.append("CUSTOMER_GROUP_ID=?,AUTHOR_ID=?,AUTHOR_DATE=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					queryUpdate.append("FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,UID_NO=?,OTHER_NO=?,EDU_DETAIL=?,CUSTOMER_GROUP_TYPE=?,CUSTOMER_GROUP_DESC=?,RELATIONSHIP=?,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?,NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=? where CUSTOMER_ID=?");
				
					
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
					
					//Start by KK
					
					if(CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getAadhaar()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getOther()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getEduDetailInd()).trim());
					//End by KK
				
					// Start by Prashant
					if(CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getGroupType()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getGroupNameText()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getGroupNameText()).trim());
					
					// End by Prashant
					
					if(CommonFunction.checkNull(corporateDetailVo.getRelationShip()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getRelationShip()).trim());
				// SAURABH 
					
					if(CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getGenderIndividual()).trim());
					
					//ravi start
					if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getOtherRelationShip()).trim());
					
					if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase("CS"))
					{
						insertPrepStmtObject3.addNull();
					}
					else
					{
						if(CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
							insertPrepStmtObject3.addNull();
						else
							insertPrepStmtObject3.addString((corporateDetailVo.getLbxBusinessPartnearHID()).trim());
						
					}
					//ravi end			
					//Nishant space starts
					if(CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getNatureOfBus()).trim());
					if(CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getYearOfEstb()).trim());
					if(CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getShopEstbNo()).trim());
					if(CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getSalesTax()).trim());
					if(CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getDgftNo()).trim());
					//Nishant space end
					//KK Start
					if(CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getNoBVYears()).trim());
					if(CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getNoBVMonths()).trim());
					//KK End
					insertPrepStmtObject3.addInt(id);
					
					logger.info("value of gender:::::::::::::"+CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()));
				
					insertPrepStmtObject3.setSql(queryUpdate.toString());
					logger.info("in saveCorporateUpdate() of CorpotateDAOImpl  Query  :"+insertPrepStmtObject3.printQuery());
					qryList2.add(insertPrepStmtObject3);
		            status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
				
				}		
				else
				{
					if(statusCase!=null && statusCase.length()>0)//&& statusCase.equals("UnApproved"))
						tableName="gcd_customer_m_temp";
					else
					{
						tableName="gcd_customer_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="gcd_customer_m_edit";
					}
					
					StringBuilder queryUpdate = new StringBuilder();
					customerType= ConnectionDAO.singleReturn("select CUSTOMER_TYPE from "+tableName+" where CUSTOMER_ID="+id);
					
					StringBuilder bufInsUpdSql=new StringBuilder();
					PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
					
					ArrayList qryList2 = new ArrayList();
					ArrayList insertUpdate = new ArrayList();
					if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")){
					bufInsUpdSql.append(" INSERT INTO GCD_CUSTOMER_HST ");
			          bufInsUpdSql.append(" ( ");
			          
			          bufInsUpdSql.append(" CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
					  bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
					  bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
					  bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
					  bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
					  bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
					  bufInsUpdSql.append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,MVMT_DATE,MVMT_BY,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,");
			         
			          bufInsUpdSql.append("NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS ) ");
			          
			          bufInsUpdSql.append(" SELECT CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
					  bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
					  bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
					  bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
					  bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
					  bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
					  bufInsUpdSql.append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,");
					  bufInsUpdSql.append(dbo);
					  bufInsUpdSql.append("STR_TO_DATE('"+CommonFunction.checkNull(corporateDetailVo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					  
					  bufInsUpdSql.append("'"+CommonFunction.checkNull(corporateDetailVo.getMakerId())+"',GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS ");
			          bufInsUpdSql.append(" FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID ='"+id+"' ");
			        
			         
			         insertPrepStmtObject3.setSql(bufInsUpdSql.toString());
			         logger.info("insert history :"+insertPrepStmtObject3.printQuery());
			         insertUpdate.add(insertPrepStmtObject3);
			        // qryList2.add(insertPrepStmtObject3);
			         boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);}
			         
					logger.info("CorporateDAOImpl saveCorporateUpdate().............recStatus.........................."+recStatus);
					if(tableName.equalsIgnoreCase("gcd_customer_m") || tableName.equalsIgnoreCase("gcd_customer_m_edit"))
					{				
						queryUpdate.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"'),");
						queryUpdate.append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,");
						queryUpdate.append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,");
						queryUpdate.append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						queryUpdate.append("AUTHOR_ID=?,AUTHOR_DATE=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						
						queryUpdate.append("FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? , UID_NO=?, OTHER_NO=? ,EDU_DETAIL=?,GROUP_ID=? ,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?");
						queryUpdate.append(",NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=? where CUSTOMER_ID=?");
				
					}
					else
					{
						queryUpdate.append("update "+tableName+" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=");
						queryUpdate.append(dbo);
						queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+"'),");
						queryUpdate.append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,");
						queryUpdate.append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,");
						queryUpdate.append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? , UID_NO=?, OTHER_NO=? , EDU_DETAIL=?,GROUP_ID=?,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?,NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=?, MAKER_ID=? where CUSTOMER_ID=?");
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
				
				if(tableName.equalsIgnoreCase("gcd_customer_m")|| tableName.equalsIgnoreCase("gcd_customer_m_edit"))
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
				
				//Start by KK
				
				if(CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getAadhaar()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getOther()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getEduDetailInd()).trim());
				
				//End by KK
				
				if(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.gethGroupId()).trim());
			
				//SAURABH 
								
				if(CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getGenderIndividual()).trim());
				
				//ravi start
				if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getOtherRelationShip()).trim());
				
				if(CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase("CS"))
				{
					insertPrepStmtObject3.addNull();
				}
				else
				{
					if(CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getLbxBusinessPartnearHID()).trim());
					
				}
				//ravi end
				//Nishant space starts
				if(CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getNatureOfBus()).trim());
				if(CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getYearOfEstb()).trim());
				if(CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getShopEstbNo()).trim());
				if(CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getSalesTax()).trim());
				if(CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getDgftNo()).trim());
				//Nishant space end
				//KK Start
				if(CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getNoBVYears()).trim());
				if(CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject3.addNull();
				else
					insertPrepStmtObject3.addString((corporateDetailVo.getNoBVMonths()).trim());
				if(tableName.equalsIgnoreCase("gcd_customer_m_temp"))
				{
					if(CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject3.addNull();
					else
						insertPrepStmtObject3.addString((corporateDetailVo.getMakerId()).trim());
				}
				//KK End
				
				insertPrepStmtObject3.addInt(id);
			    insertPrepStmtObject3.setSql(queryUpdate.toString());
				logger.info("query :"+insertPrepStmtObject3.printQuery());
				qryList2.add(insertPrepStmtObject3);
				logger.info("Ritu..............");
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
		   }			
		}catch(Exception e)
		{e.printStackTrace();}
	   return status;
	}

	private boolean checkStatus(int id)
	{	
		boolean flag=true;
		try{
			String query="select CUSTOMER_ID from gcd_customer_m_temp where CUSTOMER_ID="+id;
			flag = ConnectionDAO.checkStatus(query);
			if(flag){				
				flag=false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}

	
	//ravindra 1 april
public ArrayList<Object> getStates(String country) {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
  		try{
  		
  		String query="select distinct b.STATE_ID,b.STATE_DESC from com_address_m a left join com_state_m b on a.COUNTRY=b.COUNTRY_ID where a.BPID="+country;
  		logger.info("getStates: "+query);
  		DeatilOfCustomerAddress vo = null;
  		ArrayList source = ConnectionDAO.sqlSelect(query);
  		logger.info("getStates"+source.size());
  		for(int i=0;i<source.size();i++){
  			
  			
  			ArrayList subStates=(ArrayList)source.get(i);
  			if(subStates.size()>0)
  			{
  				
  				vo = new DeatilOfCustomerAddress();
  				vo.setState_code((CommonFunction.checkNull(subStates.get(0)).toString()));
  				vo.setState_name((CommonFunction.checkNull(subStates.get(1)).toString()));
  				list.add(vo);
  			}
  		}
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  		return list;
		
	}
	

	public ArrayList<Object> getCities(String country) {
		
		ArrayList<Object> list=new ArrayList<Object>();
  		try{
  		
  		String query="select distinct c.DISTRICT_ID,c.DISTRICT_DESC from com_address_m a left join com_state_m b on a.country=b.country_ID left join com_district_m c on a.STATE=c.STATE_ID "+
		"where a.BPID="+country;
  		logger.info("getCities"+query);
  		DeatilOfCustomerAddress vo = null;
  		ArrayList source = ConnectionDAO.sqlSelect(query);
  		logger.info("getCities"+source.size());
  		for(int i=0;i<source.size();i++){
  			
  			
  			ArrayList subCities=(ArrayList)source.get(i);
  			if(subCities.size()>0)
  			{
  				
  				vo = new DeatilOfCustomerAddress();
  				vo.setDist_code((CommonFunction.checkNull(subCities.get(0)).toString()));
  				vo.setDist_name((CommonFunction.checkNull(subCities.get(1)).toString()));
  				list.add(vo);
  			}
  		}
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  		return list;
			
	}


	public int updateCustomerAddress(Object ob, int id, int addId, String recStatus,String statusCase,String updateFlag,String pageStatuss,String source) 
	{
		logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		int status=0;
		boolean qryStatus= false;
		String tableName="";
		ArrayList qryList = new ArrayList();
		StringBuilder subQuery= new StringBuilder();
		try
		{
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				tableName="cr_deal_address_m";
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, ");
				query.append("DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?,LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				query.append("AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				query.append("TAHSIL=?,BRANCH_DISTANCE=?,COMMUNICATION_ADDRESS=? where ADDRESS_ID=?");
					
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr_type()).trim());
					
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
					
				if(vo.getBp_id()!="")
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
				else
					insertPrepStmtObject.addString((vo.getBp_id1()).trim());
							
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
				{
					tableName="com_address_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_address_m_edit";
				}
				
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				StringBuilder bufInsUpdSql=new StringBuilder();
				ArrayList qryList2 = new ArrayList();
				ArrayList insertUpdate = new ArrayList();
				if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")){
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
				  bufInsUpdSql.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				  
				  bufInsUpdSql.append("'"+CommonFunction.checkNull(vo.getMakerId())+"' ");
				  
		          bufInsUpdSql.append(" FROM com_address_m WHERE ADDRESS_ID ='"+addId+"' ");
		        
		         
		         insertPrepStmtObject.setSql(bufInsUpdSql.toString());
		         logger.info("insert history :"+insertPrepStmtObject.printQuery());
		         insertUpdate.add(insertPrepStmtObject);
		        // qryList2.add(insertPrepStmtObject3);
		         boolean status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);}
				if(tableName.equalsIgnoreCase("com_address_m")||tableName.equalsIgnoreCase("com_address_m_edit"))
				{
					subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
					subQuery.append(dbo);
					subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					subQuery.append("AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
					subQuery.append(dbo);
					subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
				}
				else
					subQuery.append("");
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, " +
			     		"ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, " +
			     		"DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, "+
			     		"LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,COMMUNICATION_ADDRESS=? "  +subQuery.toString()+",TAHSIL=?,BRANCH_DISTANCE=?  where ADDRESS_ID=?");
				
				
					
				if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddr_type()).trim());
				
				if(CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBp_type()).trim());
				
				if(vo.getBp_id()!="")
				{
					insertPrepStmtObject.addString((vo.getBp_id()).trim());
				}
				else
				{
					insertPrepStmtObject.addString((vo.getBp_id1()).trim());
				}
				
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
	
public int updateIndReference(Object ob, int id, int refId, String recStatus,String statusCase, String updateFlag, String pageStatuss,String source) {
		logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		int status=0;
		boolean qryStatus= false;
		String tableName="";
		ArrayList qryList = new ArrayList();
		StringBuilder subQuery= new StringBuilder();
		try
		{
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				tableName="cr_deal_reference_m";
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?,REF_ADDRESS=?,MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				query.append("AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
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
				
				if(CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddRef()).trim());
				
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
				{
					tableName="com_reference_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_reference_m_edit";
				}
				if(tableName.equalsIgnoreCase("com_reference_m") || tableName.equalsIgnoreCase("com_reference_m_edit"))
				{
					subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
					subQuery.append(dbo);
					subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
					subQuery.append("AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
					subQuery.append(dbo);
					subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
				}
				else
					subQuery.append("");	
				String query="update "+tableName+" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?,REF_ADDRESS=? "+subQuery.toString()+" where REF_ID=?";
				
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
				
				if(CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddRef()).trim());

				if(CommonFunction.checkNull(refId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(refId);
				
				insertPrepStmtObject.setSql(query.toString());
				logger.info("in saveCustomerReference() of CorpotateDAOImpl  Query  :  "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
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
		
	
	private boolean checkAddressStatus(int addId)
	{
		boolean flag=true;
		try{
			
			String query="select ADDRESS_ID from com_address_m_temp where ADDRESS_ID="+addId;
			flag = ConnectionDAO.checkStatus(query);
			logger.info("query of address id check ....................................................... "+query);
			if(flag){
				flag=false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}

	private boolean checkAddressMaster(int id)
	{
		boolean flag=false;
		try{
			
			String query="select CUSTOMER_ID from com_address_m where CUSTOMER_ID="+id;
			flag = ConnectionDAO.checkStatus(query);
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}
	
	
	public ArrayList<IndustryVO> getSubIndustryUpdateList(String codeId) {
		ArrayList<IndustryVO> list=new ArrayList<IndustryVO>();
		try{
			String query="select distinct b.SUB_INDUSTRY_ID,b.SUB_INDUSTRY_DESC from gcd_customer_m a left join com_sub_industry_m b on a.CUSTOMER_INDUSTRY=b.INDUSTRY_ID where a.CUSTOMER_ID="+codeId+"";
			ArrayList subIndustryUpdateList = ConnectionDAO.sqlSelect(query);
			logger.info("getSubIndustryUpdateList "+subIndustryUpdateList.size());
			for(int i=0;i<subIndustryUpdateList.size();i++){
				
				ArrayList data=(ArrayList)subIndustryUpdateList.get(i);
				if(data.size()>0)
				{
					IndustryVO industryndVO=new IndustryVO();
					industryndVO.setSubIndustryCode((CommonFunction.checkNull(data.get(0)).toString()));
					industryndVO.setSubIndustryName((CommonFunction.checkNull(data.get(1)).toString()));
					list.add(industryndVO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	return list;
	}

	public ArrayList<Object> getCorporateDetailAll(int code,Object pageStatus,String statusCase,String updateInMaker,String updateFlag,String pageStatuss,String source)
	{
		logger.info("In getCorporateDetailAll() of CorpotateDAOImpl.");
		ArrayList<Object> list=new ArrayList<Object>();	
		String tableName="";
		logger.info("pageStatuss: "+pageStatuss+" updateFlag: "+updateFlag);
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getCorporateDetailAll()......");
			tableName="cr_deal_customer_m";
			try
			{
				StringBuilder str= new StringBuilder();;
				String custStatus="";
				custStatus =  ConnectionDAO.singleReturn("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID="+code);
				
				String OthRelType="";
				OthRelType = ConnectionDAO.singleReturn("SELECT OTHOR_RELATIONSHIP_TYPE FROM cr_deal_customer_m WHERE CUSTOMER_ID="+code+"");
				
				
				if(CommonFunction.checkNull(OthRelType).equalsIgnoreCase("CS"))
				{
					str.append("select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
					str.append(dbo);
					str.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON," +
					" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,c.RELATIONSHIP,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS  from "+tableName+" c left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY where customer_id="+code+" ");
				}
				else
				{
					str.append("select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
					str.append(dbo);
					str.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON," +
					" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,c.RELATIONSHIP,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,d.dealer_desc,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS  from "+tableName+" c left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID inner join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID inner join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY,cr_dsa_dealer_m d  where customer_id="+code+" and c.OTHOR_RELATIONSHIP_TYPE=d.bp_type and c.OTHER_RELATIONSHIP_ID=d.dealer_id");
				}
				
				logger.info("in getCorporateDetailAll() of CorpotateDAOImpl Qiery : "+str.toString());
				ArrayList corporateDetailAll = ConnectionDAO.sqlSelect(str.toString());
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
						show.setGroupType((CommonFunction.checkNull(data.get(25))));
						show.setGroupNameText((CommonFunction.checkNull(data.get(26))));
						show.setRelationShip((CommonFunction.checkNull(data.get(27))));
						show.setOtherRelationShip((CommonFunction.checkNull(data.get(28)).toString()));
						show.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(29)).toString()));
						if(OthRelType.equalsIgnoreCase("CS"))
						{
							show.setBusinessPartnerTypeDesc("");
							//Nishant space starts
							show.setNatureOfBus((CommonFunction.checkNull(data.get(30)).toString()));
							show.setYearOfEstb((CommonFunction.checkNull(data.get(31)).toString()));
							show.setShopEstbNo((CommonFunction.checkNull(data.get(32)).toString()));
							show.setSalesTax((CommonFunction.checkNull(data.get(33)).toString()));
							show.setDgftNo((CommonFunction.checkNull(data.get(34)).toString()));
							show.setNoBVYears((CommonFunction.checkNull(data.get(35)).toString()));
							show.setNoBVMonths((CommonFunction.checkNull(data.get(36)).toString()));
							//Nishant space end
						}
						else
						{
							show.setBusinessPartnerTypeDesc((CommonFunction.checkNull(data.get(30)).toString()));
							//Nishant space starts
							show.setNatureOfBus((CommonFunction.checkNull(data.get(31)).toString()));
							show.setYearOfEstb((CommonFunction.checkNull(data.get(32)).toString()));
							show.setShopEstbNo((CommonFunction.checkNull(data.get(33)).toString()));
							show.setSalesTax((CommonFunction.checkNull(data.get(34)).toString()));
							show.setDgftNo((CommonFunction.checkNull(data.get(35)).toString()));
							show.setNoBVYears((CommonFunction.checkNull(data.get(36)).toString()));
							show.setNoBVMonths((CommonFunction.checkNull(data.get(37)).toString()));
							//Nishant space end
						}
						logger.info("**** : ");
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
			{
				tableName="gcd_customer_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="gcd_customer_m_edit";
			}
			try
			{
				String OthRelType="";
				StringBuilder query1= new StringBuilder();
				OthRelType = ConnectionDAO.singleReturn("SELECT OTHOR_RELATIONSHIP_TYPE FROM "+tableName+" WHERE CUSTOMER_ID="+code+"");
				
				
				if(OthRelType.equalsIgnoreCase("CS"))
				{
					query1.append("select  c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
					query1.append(dbo);
					query1.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,c.CUSTOMER_STATUS,"+
					" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS from "+tableName+" c,gcd_group_m g,com_industry_m i,com_sub_industry_m si where customer_id="+code+" and c.GROUP_ID=g.GROUP_ID and c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID and si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY");
				}
				else
				{
					query1.append("select  c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,");
					query1.append(dbo);
					query1.append("DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,c.CUSTOMER_STATUS,"+
					" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,d.dealer_desc,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS from "+tableName+" c,gcd_group_m g,com_industry_m i,com_sub_industry_m si,cr_dsa_dealer_m d where customer_id="+code+" and c.GROUP_ID=g.GROUP_ID and c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID and si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY and c.OTHOR_RELATIONSHIP_TYPE=d.bp_type and c.OTHER_RELATIONSHIP_ID=d.dealer_id");
		
				}
				
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
						show.setOtherRelationShip((CommonFunction.checkNull(data.get(26)).toString()));
						show.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(27)).toString()));
						if(OthRelType.equalsIgnoreCase("CS"))
						{
							show.setBusinessPartnerTypeDesc("");
							show.setNatureOfBus((CommonFunction.checkNull(data.get(28)).toString()));
							show.setYearOfEstb((CommonFunction.checkNull(data.get(29)).toString()));
							show.setShopEstbNo((CommonFunction.checkNull(data.get(30)).toString()));
							show.setSalesTax((CommonFunction.checkNull(data.get(31)).toString()));
							show.setDgftNo((CommonFunction.checkNull(data.get(32)).toString()));
							show.setNoBVYears((CommonFunction.checkNull(data.get(33)).toString()));
							show.setNoBVMonths((CommonFunction.checkNull(data.get(34)).toString()));
						}
						else
						{
							show.setBusinessPartnerTypeDesc((CommonFunction.checkNull(data.get(28)).toString()));
							show.setNatureOfBus((CommonFunction.checkNull(data.get(29)).toString()));
							show.setYearOfEstb((CommonFunction.checkNull(data.get(30)).toString()));
							show.setShopEstbNo((CommonFunction.checkNull(data.get(31)).toString()));
							show.setSalesTax((CommonFunction.checkNull(data.get(32)).toString()));
							show.setDgftNo((CommonFunction.checkNull(data.get(33)).toString()));
							show.setNoBVYears((CommonFunction.checkNull(data.get(34)).toString()));
							show.setNoBVMonths((CommonFunction.checkNull(data.get(35)).toString()));
						}
						list.add(show);
					}
                }		
			}catch(Exception e)
			{e.printStackTrace();}
		}
		return list;
	}

	public ArrayList<Object> getStakeDetails(int code,Object pageStatus,String statusCase,String updateFlag,String updateInMaker,String source) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";

		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry.,getStakeDetails().......");
			tableName="cr_deal_customer_stakeholder_m";
			try{
				StringBuilder query = new StringBuilder();
				query.append("SELECT distinct cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, s.STAKEHOLDER_ID, cs.STAKEHOLDER_PERCENTAGE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO, p.DESCRIPTION,");
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_stakeholder_m_edit";
				}
				else
				{
					tableName="customer_stakeholder_m_temp";
				}
				
			}
			else 
			{
				tableName="customer_stakeholder_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_stakeholder_m_edit";
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

	public int updateStakeHolder(Object ob, int id, int stackId, String recStatus,String statusCase,String updateFlag,String pageStatuss,String source) {
		
		StakeHolderVo vo = (StakeHolderVo)ob;
		//logger.info("value of  maker date in update"+vo.getMakerDate());
		//logger.info("value of  maker id in update"+vo.getMakerId());
		int status=0;
		boolean qryStatus=false;
		String tableName="";
		StringBuilder subQuery= new StringBuilder();
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
				" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?,STAKEHOLDER_JOINING_DATE= ");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), ELIGIBLE_FOR_COMPUTATION=?,");
				query.append("MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				query.append("AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
				query.append("STAKEHOLDER_PAN=?,ADDITIONAL_ID1=?,ADDITIONAL_ID2=? ");
				
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

				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()));	
				
				if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()));
				
				if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()));
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_stakeholder_m_edit";
				}
				

				if(tableName.equalsIgnoreCase("customer_stakeholder_m")){
				
                 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
                subQuery.append(dbo);
				subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
                 subQuery.append(",AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
                 subQuery.append(dbo);
				subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				subQuery.append(",STAKEHOLDER_PAN='"+CommonFunction.checkNull(vo.getMgmtPAN())+"',ADDITIONAL_ID1='"+CommonFunction.checkNull(vo.getAddId1())+"',ADDITIONAL_ID2='"+CommonFunction.checkNull(vo.getAddId2())+"'");
						
				 }else{
				 subQuery.append("");	
				 }
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set STAKEHOLDER_SALUTATION=?,");
				query.append(" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), ");
				query.append("STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, ");
				query.append("STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?,");
				query.append(" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?,STAKEHOLDER_JOINING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'), ELIGIBLE_FOR_COMPUTATION=?,STAKEHOLDER_PAN=?,ADDITIONAL_ID1=?,ADDITIONAL_ID2=? "+subQuery.toString()+"  where STAKEHOLDER_ID=?");
				
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
				
				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()));	
				
				if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()));
				
				if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()));
				
				if(CommonFunction.checkNull(stackId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(stackId);
	
				insertPrepStmtObject.setSql(query.toString());
				logger.info("IN updateStakeHolder() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
				}catch(Exception e){
					e.printStackTrace();
					}finally{
						subQuery.append("");
					}
				if(qryStatus)
				{
					status=1;
				}
			return status;	
	}

	private boolean checkStake(int stackId)
	{
		logger.info("stack id....................................................................... "+stackId);
		boolean flag=true;
		try{
			String query="select STAKEHOLDER_ID from customer_stakeholder_m_temp where STAKEHOLDER_ID="+stackId;
			flag = ConnectionDAO.checkStatus(query);
			if(flag){
				flag=false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}

	public ArrayList<Object> getRatingDetails(int code,Object pageStatus, String statusCase,String updateFlag,String updateInMaker,String cuaStatus,String source) {
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
				logger.info("In getRatingDetails()  Query  :  "+query);
				ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
				logger.info("getRatingDetails "+ratingDetails.size());
				for(int i=0;i<ratingDetails.size();i++){
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
			if(pageStatus!=null || updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_rating_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_rating_m_edit";
				}
				else
				{
					tableName="customer_rating_m_temp";
				}
				
			}
			else 
			{
				tableName="customer_rating_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_rating_m_edit";
			}
			// START BY PRASHANT
			if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
			{
				tableName="customer_rating_m_temp";
			}
			// END BY PRASHANT
			
		try{
			String query="SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING," +
					"r.RATING_DATE from "+tableName+" r, com_agency_m a " +
					"where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+code;
			logger.info("In getRatingDetails()  Query  :  "+query);
			ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
			logger.info("getRatingDetails "+ratingDetails.size());
			for(int i=0;i<ratingDetails.size();i++){
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

	public int updateCreditRating(Object ob, int id,int crId, String recStatus,String statusCase,String updateFlag,String pageStatuss,String source)
	{
		CreditRatingVo vo = (CreditRatingVo)ob;
		int status=0;
		boolean qryStatus = false;
		String tableName="";
		StringBuilder subQuery= new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList = new ArrayList();
		try{
		
			logger.info("updateCreditRating :"+id);
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				logger.info("In Credit Processing , Customer Entry..,updateCreditRating()......");
				tableName="cr_deal_customer_rating";
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set RATING_AGENCY=?, CUSTOMER_RATING=?,RATING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"'),MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				query.append("AUTHOR_ID=?,AUTHOR_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_rating_m_edit";
				}
				if(tableName.equalsIgnoreCase("customer_rating_m")||tableName.equalsIgnoreCase("customer_rating_m_edit")){
				 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
				 subQuery.append(dbo);
				subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
				 subQuery.append("AUTHOR_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',AUTHOR_DATE=");
				 subQuery.append(dbo);
				subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
				 }else{
				 subQuery.append("");	
				 }
				StringBuilder query = new StringBuilder();
				query.append("update "+tableName+" set RATING_AGENCY=?, CUSTOMER_RATING=?, RATING_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormat+"') "+subQuery.toString()+" where CUSTOMER_RATING_ID=?");
			
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
	
	private boolean checkRating(int crId)
	{
		boolean flag=true;
		try{
			String query="select CUSTOMER_RATING_ID from customer_rating_m_temp where CUSTOMER_RATING_ID="+crId;
			flag = ConnectionDAO.checkStatus(query);
			if(flag){
				flag=false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return flag;
	}
	
	private boolean checkRatingMaster(int id)
	{
		boolean flag=false;
		try{
			String query="select customer_id from gcd_credit_rating_detail_m where customer_id="+id;
			flag = ConnectionDAO.checkStatus(query);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		return flag;
	}

/*	public String moveFromGCD(String customerId,String applType,String dealId,String tableStatus,String userId,String bDate)
	{
		logger.info("In moveFromGCD");		
		CallableStatement cst=null;
		int statusProc=0;
		String appQ="";
		String procval="";
		boolean checkApp=false;
		if(applType!=null && applType.equalsIgnoreCase("PRAPPL"))
		{
			    appQ ="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId+" and DEAL_CUSTOMER_ID is not NULL";
				logger.info("In Cr_deal_dtl. query....................."+appQ);
				checkApp=ConnectionDAO.checkStatus(appQ);
		}		
		String q="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ID="+customerId+" and DEAL_ID="+dealId;		
		logger.info("Query: "+q);		
        boolean exist=ConnectionDAO.checkStatus(q);	    
        if(exist || checkApp)
            	statusProc=0;
        else
        {
        	     Connection con=ConnectionDAO.getConnection();
        	     try 
        	     {
        	    	 logger.info("Applicant Type in moveFromGCD:== "+applType+" customerId:  "+customerId+" tableStatus:  "+tableStatus+" userId: "+userId+" bDate:  "+bDate);
        	    	 con.setAutoCommit(false);
        	    	 logger.info("Gcd_Customer_Link Procedure------>");
        	    	 cst=con.prepareCall("call Gcd_Customer_Link(?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?)");
        	    	 cst.setString(1, dealId);
        	    	 cst.setString(2, customerId);
        	    	 cst.setString(3, applType);
        	    	 cst.setString(4, tableStatus);
        	    	 cst.setString(5, userId);
        	    	 System.out.println("\n neeraj bDate : "+bDate);
        	    	 cst.setString(6, bDate);
        	    	 cst.registerOutParameter(7, Types.CHAR);
        	    	 cst.registerOutParameter(8, Types.CHAR);
        	    	 statusProc=cst.executeUpdate();
			     	 logger.info("status of Gcd_Customer_Link Proc: "+statusProc);
			     	 String s1 = cst.getString(7);
			     	 String s2 = cst.getString(8);
			     	 if(s1.equalsIgnoreCase("S"))
			     	 {
			     		 statusProc=1;
			     		 procval=s2;
			     	 }else{
			     		 procval=s2;	
			     	 }
			     	 
			     	 
        	     }
        	     
        	     catch(Exception e)
        	     {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.info("Exception In approve: "+e);
		}
		finally{
			try {
				con.commit();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
        }
       
		return procval;	
	 }*/
	

	public String moveFromGCD(String customerId,String applType,String dealId,String tableStatus,String userId,String bDate,String paramString7,String paramString8)
	{
		logger.info("In moveFromGCD");		
		//CallableStatement cst=null;
		int statusProc=0;
		String appQ="";
		String procval="";
		boolean checkApp=false;
		if(applType!=null && applType.equalsIgnoreCase("PRAPPL"))
		{
			    appQ ="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId+" and DEAL_CUSTOMER_ID is not NULL";
				logger.info("In Cr_deal_dtl. query....................."+appQ);
				checkApp=ConnectionDAO.checkStatus(appQ);
				if(checkApp)
					return "More than one applicant can not be possible.";
		}		
		String q="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ID="+customerId+" and DEAL_ID="+dealId;		
		logger.info("Query: "+q);		
        boolean exist=ConnectionDAO.checkStatus(q);	    
        if(exist)
        {
            	return "Customer being added already exist in this deal.";
        }
        else
        {
        	ArrayList<Object> in =new ArrayList<Object>();
 			ArrayList<Object> out =new ArrayList<Object>();
 			ArrayList outMessages = new ArrayList();
 			String s1="";
 			String s2="";
 			try 
        	{
        	    logger.info("Applicant Type in moveFromGCD:== "+applType+" customerId:  "+customerId+" tableStatus:  "+tableStatus+" userId: "+userId+" bDate:  "+bDate);
        	    logger.info("Gcd_Customer_Link Procedure------>");
        	    in.add(dealId);
        	    in.add(customerId);
        	    in.add(applType);
        	    in.add(tableStatus);
        	    in.add(userId);
        	    String date=CommonFunction.changeFormat(bDate);
        	    if(date != null)
        	    	in.add(date);
        	    out.add(s1);
        	    out.add(s2);
        	    logger.info("Gcd_Customer_Link("+in.toString()+","+out.toString()+")");
        	    outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Customer_Link",in,out);
        	    s1=CommonFunction.checkNull(outMessages.get(0));
        	    s2=CommonFunction.checkNull(outMessages.get(1));
        	    logger.info("s1: "+s1);
			    logger.info("s2: "+s2);
			    procval=s2;	
        	}
 			catch(Exception e)
        	{
 				logger.info("Exception In approve: "+e);
        	}
 			finally
 			{
 				in=null;
 	 			out=null;
 	 			outMessages=null; 
			}	
		}
       	return procval;	
	 }

//	public String moveFromGCD(String customerId,String applType,String dealId,String tableStatus,String userId,String bDate)
//	{
//		logger.info("In moveFromGCD");		
//		CallableStatement cst=null;
//		int statusProc=0;
//		String appQ="";
//		String procval="";
//		boolean checkApp=false;
//		if(applType!=null && applType.equalsIgnoreCase("PRAPPL"))
//		{
//			    appQ ="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId+" and DEAL_CUSTOMER_ID is not NULL";
//				logger.info("In Cr_deal_dtl. query....................."+appQ);
//				checkApp=ConnectionDAO.checkStatus(appQ);
//		}		
//		String q="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ID="+customerId+" and DEAL_ID="+dealId;		
//		logger.info("Query: "+q);		
//        boolean exist=ConnectionDAO.checkStatus(q);	    
//        if(exist || checkApp)
//            	statusProc=0;
//        else
//        {
//        	     Connection con=ConnectionDAO.getConnection();
//        	     try 
//        	     {
//        	    	 logger.info("Applicant Type in moveFromGCD:== "+applType+" customerId:  "+customerId+" tableStatus:  "+tableStatus+" userId: "+userId+" bDate:  "+bDate);
//        	    	 con.setAutoCommit(false);
//        	    	 logger.info("Gcd_Customer_Link Procedure------>");
//        	    	 cst=con.prepareCall("call Gcd_Customer_Link(?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?)");
//        	    	 cst.setString(1, dealId);
//        	    	 cst.setString(2, customerId);
//        	    	 cst.setString(3, applType);
//        	    	 cst.setString(4, tableStatus);
//        	    	 cst.setString(5, userId);
//        	    	 cst.setString(6, bDate);
//        	    	 cst.registerOutParameter(7, Types.CHAR);
//        	    	 cst.registerOutParameter(8, Types.CHAR);
//        	    	 statusProc=cst.executeUpdate();
//			     	 logger.info("status of Gcd_Customer_Link Proc: "+statusProc);
//			     	 String s1 = cst.getString(7);
//			     	 String s2 = cst.getString(8);
//			     	 if(s1.equalsIgnoreCase("S"))
//			     	 {
//			     		 statusProc=1;
//			     		 procval=s2;
//			     	 }else{
//			     		 procval=s2;	
//			     	 }
//			     	 
//			     	 
//        	     }
//        	     
//        	     catch(Exception e)
//        	     {
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			logger.info("Exception In approve: "+e);
//		}
//		finally{
//			try {
//				con.commit();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//	
//		}
//        }
//       
//		return procval;	
//	 }
//


	public ArrayList<Object> getStakeDetailsAll(int id,String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) {
		
			 ArrayList<Object> list=new ArrayList<Object>();
			 String tableName="";
			 
			 if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
				{
				 logger.info("In Credit Processing , Customer Entry.,getStakeDetailsAll().......");
					tableName="cr_deal_customer_stakeholder_m";
					
					try{
						StringBuilder query = new StringBuilder();
						query.append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, ");
						query.append(dbo);
						query.append("DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
								" FROM "+tableName+" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' "+
								" where c.CUSTOMER_ID="+id);
						ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query.toString());
						logger.info("query "+query.toString());
					//added by neeraj tripathi start space
						StringBuilder perQuery = new StringBuilder();
						perQuery.append("select sum(");
						perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID="+id);
					 	logger.info("Query for getting total percentage : "+perQuery.toString());
					 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
					//end space
						logger.info("getStakeDetailsAll "+stakeDetailsAll.size());
						for(int i=0;i<stakeDetailsAll.size();i++){
							
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
							if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
								tableName="customer_stakeholder_m_edit";
						}
						else
						{
							tableName="customer_stakeholder_m_temp";
						}
					}
					else
					{
						tableName="customer_stakeholder_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="customer_stakeholder_m_edit";
					}
					
					// START BY PRASHANT
					if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
					{
						tableName="customer_stakeholder_m_temp";
					}
					// END BY PRASHANT
				
				try{
//					String query="SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
//					"  FROM "+tableName+" c,generic_master d where d.VALUE=c.stakeholder_position" +
//					"  and d.GENERIC_KEY='DESIGNATION' and c.CUSTOMER_ID="+id;
					
					StringBuilder query = new StringBuilder();
					query.append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE, ");
					query.append(dbo);
					query.append("DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"')" +
						" FROM "+tableName+" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' "+
						" where c.CUSTOMER_ID="+id);
					
					logger.info("Query for getting stack holder list  : "+query.toString());
					ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getStakeDetailsAll "+stakeDetailsAll.size());
					//added by neeraj tripathi start space
					StringBuilder perQuery = new StringBuilder();
					perQuery.append("select sum(");
					perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from "+tableName+" where CUSTOMER_ID="+id);
				 	logger.info("Query for getting total percentage : "+perQuery.toString());
				 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
				//end space
					for(int i=0;i<stakeDetailsAll.size();i++){
						
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


	public int deleteStakeHolderDetails(String holderid,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag,String source) 
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_stakeholder_m_edit";
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
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_stakeholder_m_edit";
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

	
	public ArrayList<InstitutionNameVo> getInstitutionName() {
		ArrayList<InstitutionNameVo> list=new ArrayList<InstitutionNameVo>();
		try{
		String query="select AGENCY_CODE,AGENCY_NAME from com_agency_m where AGENCY_TYPE='CR'";
		ArrayList institutionName = ConnectionDAO.sqlSelect(query);
		logger.info("getInstitutionName "+institutionName.size());
		for(int i=0;i<institutionName.size();i++){
			
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
	
	
	public int deleteSelectedCreditRating(String ratingId,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag,String source)
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
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_rating_m_edit";
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
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="customer_rating_m_edit";
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

	public ArrayList<Object> getAddressDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) 
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
					{
						tableName="com_address_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="com_address_m_edit";
					}
					else
					{
						tableName="com_address_m_temp";
					}
				}
				else 
				{
					tableName="com_address_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_address_m_edit";
				}
				
				
				// START BY PRASHANT
				if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
				{
					tableName="com_address_m_temp";
				}
				// END BY PRASHANT
				
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
	
	public ArrayList<Object> getRefrenceDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) {
		
		 String tableName="";
		 logger.info("In getRefrenceDetails() of CorpotateDAOImpl");
		 ArrayList<Object> list=new ArrayList<Object>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_reference_m";
			 try
			 {
					StringBuilder query = new StringBuilder();
					query.append("select REF_ID,(F_NAME+' '+ISNULL(M_NAME,'')+' '+L_NAME)NAME, RELATIONSHIP,(select DESCRIPTION from generic_master	where generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS from "+tableName+" where BPID="+code);
					logger.info("getRefrenceDetails query : : : : : : :  "+query.toString() );
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getRefrenceDetails "+addressDetails.size());
					for(int i=0;i<addressDetails.size();i++){
						
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setRefName((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setRelationshipS((CommonFunction.checkNull(data.get(2)).toString()));
							addr.setRelationShipDesc((CommonFunction.checkNull(data.get(3)).toString()));
							addr.setKnowingSince((CommonFunction.checkNull(data.get(4)).toString()));
							addr.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(5)).toString()));
							addr.setAlternateRefPhNo((CommonFunction.checkNull(data.get(6)).toString()));
							addr.setAddRef((CommonFunction.checkNull(data.get(7)).toString()));
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
					{
						tableName="com_reference_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="com_reference_m_edit";
					}
					else
					{
						tableName="com_reference_m_temp";
					}
				}
				else 
				{
					tableName="com_reference_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_reference_m_edit";
				}
				
				// START BY PRASHANT
				if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
				{
					tableName="com_reference_m_temp";
				}
				// END BY PRASHANT
				
				try
				{
					StringBuilder query = new StringBuilder();
					query.append("select REF_ID, (F_NAME+' '+ISNULL(M_NAME,'')+' '+L_NAME)NAME, RELATIONSHIP,(select DESCRIPTION from generic_master	where generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS from "+tableName+" where BPID="+code);
					logger.info("getRefrenceDetails query : : : : : : :  "+query.toString() );
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getRefrenceDetails "+addressDetails.size());
					for(int i=0;i<addressDetails.size();i++){
						
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setRefId((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setRefName((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setRelationshipS((CommonFunction.checkNull(data.get(2)).toString()));
							addr.setRelationShipDesc((CommonFunction.checkNull(data.get(3)).toString()));
							addr.setKnowingSince((CommonFunction.checkNull(data.get(4)).toString()));
							addr.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(5)).toString()));
							addr.setAlternateRefPhNo((CommonFunction.checkNull(data.get(6)).toString()));
							addr.setAddRef((CommonFunction.checkNull(data.get(7)).toString()));
							list.add(addr);
						}
					}
				 }catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}
	
	public int deleteCustomerAddress(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag,String source)
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_address_m_edit";
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
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="com_address_m_edit";
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
	
	
	public int deleteCustomerReference(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag,String source)
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
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="com_reference_m_edit";
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
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="com_reference_m_edit";
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


public ArrayList<Object> getcreditRatingDetails(int cid, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) {
	 ArrayList<Object> list=new ArrayList<Object>();
	 String tableName="";
	    
	 if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
		 logger.info("In Credit Processing , Customer Entry..,getcreditRatingDetails()......");
			tableName="cr_deal_customer_rating";
			
			try{
				StringBuilder query = new StringBuilder();
				query.append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,");
				query.append(dbo);
				query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"') from com_agency_m a,"+tableName+" r" +
						" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+cid);
				logger.info("In getcreditRatingDetails()  Query  :  "+query);
				ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getcreditRatingDetails "+creditRatingDetails.size());
				for(int i=0;i<creditRatingDetails.size();i++){
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
			if(updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_rating_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_rating_m_edit";
				}
				else
				{
					tableName="customer_rating_m_temp";
				}
				
			}
			else
			{
				tableName="customer_rating_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_rating_m_edit";
			}
			// START BY PRASHANT
			if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
			{
				tableName="customer_rating_m_temp";
			}
			// END BY PRASHANT
			
		try{
		StringBuilder query = new StringBuilder();
		query.append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,");
		query.append(dbo);
		query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"') from com_agency_m a,"+tableName+" r" +
				" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID="+cid);
		logger.info("In getcreditRatingDetails()  Query  :  "+query);
		ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getcreditRatingDetails "+creditRatingDetails.size());
		for(int i=0;i<creditRatingDetails.size();i++){
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

		public ArrayList<CustomerSaveVo> getCustomerAddressDetail(String addId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss,String cuaStatus,String source)
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
				{
					tableName="com_address_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="com_address_m_edit";
				}
				else
				{
						tableName="com_address_m_temp";
				}
			}
			else
			{
				tableName="com_address_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="com_address_m_edit";
			}
			// START BY PRASHANT
			if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
			{
				tableName="com_address_m_temp";
			}
			// END BY PRASHANT
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
	
		

		public ArrayList<CustomerSaveVo> getIndReferenceDetail(String addId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss,String source)
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
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="com_reference_m_edit";
		}
			try{
			String query="";
			query="select REF_ID, BPID, F_NAME, M_NAME, L_NAME, RELATIONSHIP,(select DESCRIPTION from generic_master	where generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER, REF_ADDRESS FROM "+tableName+ " WHERE  REF_ID="+addId;
			logger.info("getIndReferenceDetail query................. "+query);
			ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
			logger.info("getIndReferenceDetail "+customerAddressDetail.size());
			for(int i=0;i<customerAddressDetail.size();i++){
				
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
					customerSaveVo.setRelationShipDesc((CommonFunction.checkNull(data.get(6)).toString()));					
					customerSaveVo.setKnowingSince((CommonFunction.checkNull(data.get(7)).toString()));
					customerSaveVo.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(8)).toString()));
					customerSaveVo.setAlternateRefPhNo((CommonFunction.checkNull(data.get(9)).toString()));
					customerSaveVo.setAddRef((CommonFunction.checkNull(data.get(10)).toString()));
					list.add(customerSaveVo);
				}
			
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
	}
			
	public ArrayList<StakeHolderVo> getStackHolderDetail(String stackHolderId ,Object pageStatus,String statusCase,String updateInMaker,String updateFlag,String pageStatuss,String cuaStatus,String source)
	{
		ArrayList<StakeHolderVo> list=new ArrayList<StakeHolderVo>();
		String tableName="";
		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getStackHolderDetail()......");
			tableName="cr_deal_customer_stakeholder_m";
			try{
				StringBuilder query = new StringBuilder();
				query.append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE,cs.STAKEHOLDER_PERCENTAGE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, " +
								"cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO,cs.STAKEHOLDER_POSITION, ");
				query.append(dbo);
				query.append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"'), cs.ELIGIBLE_FOR_COMPUTATION," +
								" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL," +
								" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE,cs.STAKEHOLDER_PAN,cs.ADDITIONAL_ID1,cs.ADDITIONAL_ID2  FROM cr_deal_customer_stakeholder_m cs" +
								" WHERE cs.STAKEHOLDER_ID="+stackHolderId+"");
				logger.info("Query for getting stack holder list  :   "+query.toString());
				ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getStackHolderDetail "+stackHolderDetail.size());
			//added by neeraj tripathi start space
				String custQuery="select CUSTOMER_ID from cr_deal_customer_stakeholder_m where STAKEHOLDER_ID="+stackHolderId;
				logger.info("Query for getting customerId : "+custQuery);
				String cid=ConnectionDAO.singleReturn(custQuery);
				StringBuilder perQuery = new StringBuilder();
				perQuery.append("select sum(");
				perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID="+cid);
			 	logger.info("Query for getting total percentage : "+perQuery.toString());
			 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
			//end space
				
				for(int i=0;i<stackHolderDetail.size();i++){
					
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
					stakeHolderVo.setMgmtPAN((CommonFunction.checkNull(data.get(17)).toString()));
					stakeHolderVo.setAddId1((CommonFunction.checkNull(data.get(18)).toString()));
					stakeHolderVo.setAddId2((CommonFunction.checkNull(data.get(19)).toString()));
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_stakeholder_m_edit";
				}
				else
				{
					tableName="customer_stakeholder_m_temp";
				}
				
			}
			else
			{
				tableName="customer_stakeholder_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_stakeholder_m_edit";
			}
			// START BY PRASHANT
			if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
			{
				tableName="customer_stakeholder_m_temp";
			}
			// END BY PRASHANT
		try{
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE, cs.STAKEHOLDER_PERCENTAGE, ");
		query.append(dbo);
		query.append("DATE_FORMAT(cs.STAKEHOLDER_DOB,'"+dateFormat+"'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO, cs.STAKEHOLDER_POSITION, ");
		query.append(dbo);
		query.append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'"+dateFormat+"'), cs.ELIGIBLE_FOR_COMPUTATION, cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL, cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE,cs.STAKEHOLDER_PAN,cs.ADDITIONAL_ID1,cs.ADDITIONAL_ID2 FROM "+tableName+" cs WHERE cs.STAKEHOLDER_ID="+stackHolderId);
				
		logger.info("Query for getting stack holder list  :   "+query.toString());
		ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query.toString());
	//added by neeraj tripathi start space
		String custQuery="select CUSTOMER_ID from "+tableName+"  where STAKEHOLDER_ID="+stackHolderId;
		logger.info("Query for getting customerId : "+custQuery);
		String cid=ConnectionDAO.singleReturn(custQuery);
		StringBuilder perQuery = new StringBuilder();
		perQuery.append("select sum(");
		perQuery.append("ISNULL(STAKEHOLDER_PERCENTAGE,0)) from "+tableName+" where CUSTOMER_ID="+cid);
	 	logger.info("Query for getting total percentage : "+perQuery.toString());
	 	String percentage=ConnectionDAO.singleReturn(perQuery.toString());
	//end space
		logger.info("getStackHolderDetail "+stackHolderDetail.size());
		for(int i=0;i<stackHolderDetail.size();i++){
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
			stakeHolderVo.setMgmtPAN((CommonFunction.checkNull(data.get(17)).toString()));
			stakeHolderVo.setAddId1((CommonFunction.checkNull(data.get(18)).toString()));
			stakeHolderVo.setAddId2((CommonFunction.checkNull(data.get(19)).toString()));
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
	
	
	
	public ArrayList<CreditRatingVo> getCreditRatingDetail(String creditRatingId,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss,String source)
	{
		ArrayList<CreditRatingVo> list=new ArrayList<CreditRatingVo>();
		String tableName="";
		
		if((pageStatuss!=null && pageStatuss.equals("fromLeads"))  || (updateFlag!=null && updateFlag.equals("updateFlag")) ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getCreditRatingDetail()......");
			tableName="cr_deal_customer_rating";
			try{
				StringBuilder query = new StringBuilder();
				query.append("SELECT a.AGENCY_CODE, ");
				query.append(dbo);
				query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID " +
						"from "+tableName+" r, com_agency_m a " +
						"where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID="+creditRatingId);
				
				logger.info("In getCreditRatingDetail() Query :  "+query);	
						
				ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getCreditRatingDetail "+creditRatingDetail.size());
				for(int i=0;i<creditRatingDetail.size();i++){
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
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_rating_m_edit";
				}
				else
				{
					tableName="customer_rating_m_temp";
				}
				
			}
			else
			{
				tableName="customer_rating_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_rating_m_edit";
			}
	
		try{
		StringBuilder query = new StringBuilder();
		query.append("SELECT a.AGENCY_CODE, ");
		query.append(dbo);
		query.append("DATE_FORMAT(r.RATING_DATE,'"+dateFormat+"'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID " +
				"from "+tableName+" r, com_agency_m a " +
				"where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID="+creditRatingId);
		logger.info("In getCreditRatingDetail() Query :  "+query);	
		ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getCreditRatingDetail "+creditRatingDetail.size());
		for(int i=0;i<creditRatingDetail.size();i++){
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
	
	public boolean checkGoForApproval(String cId)
	{
		boolean flag=true;
		
		try
		{
			String query="Select CUSTOMER_ID from gcd_customer_m_temp where customer_id="+cId;
			logger.info("in checkGoForApproval() of CorpotateDAOImpl.");
			String custId=ConnectionDAO.singleReturn(query);
			if(custId != null)
			if(custId.length()>0)
			{
				logger.info("record present in temp table.....................................");
				flag=false;
			}
		  }catch(Exception e)
		  {e.printStackTrace();}
		logger.info("checkGoForApproval.............................................. "+flag);
		return flag;		
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
	
	public ArrayList<Object> getRoleListCorp(String dealId) {
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
		" where DEAL_ID='"+dealId+"' and DEAL_CUSTOMER_TYPE='C' order by m.description";
		logger.info("getRoleListCorp:Query "+query);		
		CreditProcessingCustomerEntryVo vo=null;
		ArrayList roleList = ConnectionDAO.sqlSelect(query);
		logger.info("getRoleListCorp "+roleList.size());
		for(int i=0;i<roleList.size();i++){
			
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
		" where DEAL_ID='"+dealId+"' order by m.description";
		logger.info("getRoleList:Query "+query);		
		CreditProcessingCustomerEntryVo vo=null;
		ArrayList roleList = ConnectionDAO.sqlSelect(query);
		logger.info("getRoleList "+roleList.size());
		for(int i=0;i<roleList.size();i++){
			
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



	public boolean deleteroleList(String[] roleId,String[] applType,String dealId) 
	{
		logger.info("deleteroleList() OF CorpotateDAOImpl");
	    boolean status=false;
		try
		{			
			ArrayList qryList = new ArrayList();
			for(int k=0;k<roleId.length;k++)
			{				 
				String query2="delete from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=?";
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(roleId[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(roleId[k])).trim());
				insertPrepStmtObject.setSql(query2.toString());
				logger.info("deleteroleList() OF CorpotateDAOImpl query   :   " +insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				String q1="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim();
				logger.info("In query : ="+q1);
				String id=CommonFunction.checkNull(ConnectionDAO.singleReturn(q1));
				logger.info("Customer ID : ="+id);				
				if(!id.equalsIgnoreCase(""))
				{			
					String q2="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_CUSTOMER_ID="+id;
					logger.info("q2 "+q2);
					String idInDeal=CommonFunction.checkNull(ConnectionDAO.singleReturn(q2));
					logger.info("Customer ID : ="+idInDeal);				
					if(idInDeal!=null && !idInDeal.equalsIgnoreCase(""))
					{
						String query1="update cr_deal_dtl set DEAL_CUSTOMER_ID=?,DEAL_CUSTOMER_TYPE=?,DEAL_EXISTING_CUSTOMER=? WHERE DEAL_ID=?";
						PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
		        	   		insertPrepStmtObject2.addNull();
		        	   	else
		        	   		insertPrepStmtObject2.addString((CommonFunction.checkNull(dealId)).trim());
		        	   	insertPrepStmtObject2.setSql(query1.toString());
		        	   	logger.info("update this query  :  " +insertPrepStmtObject2.printQuery());
		        	   	qryList.add(insertPrepStmtObject2);
					}
				}
			}
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Status of Updation is   ::  "+status);
			}
			catch(Exception e)
			{e.printStackTrace();}
			return status;
	}

	
public String approve(String id,String cusType,String mvmtBy,String pageInfo,String dealId) 
{
	    logger.info("in approve() of CorpotateDAOImpl");
		String s1="";
		String s2="";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		try{

			in.add(id);
     	    in.add(cusType);
     	    in.add(mvmtBy);
     	    out.add(s1);
     	    out.add(s2);
			logger.info("Gcd_Approve("+in.toString()+"+"+out.toString()+")");
		    outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Approve",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
    	    s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("out parameter in Apporve S1  :  "+s1);
			logger.info("out parameter in Apporve S2  :  "+s2);	

			
		}	
		catch(Exception e){
			e.printStackTrace();
		}
		
		return s2;	
	}

	public String checkCustomerStatus(int Id,String custType)
	{
		String addCheck="";
		String stackCheck="";
		String custStatus="";
		logger.info("deal Id in checkCustomerStatus()................. "+Id);
		logger.info("custType in checkCustomerStatus()................. "+custType);
		addCheck = ConnectionDAO.singleReturnFromMultipleRecords("Select COMMUNICATION_ADDRESS from cr_deal_address_m where BPID="+Id);
		if(custType!=null && custType.equalsIgnoreCase("C"))
		{
			logger.info("corporate...................................... ");
			stackCheck = ConnectionDAO.singleReturn("Select STAKEHOLDER_ID from cr_deal_customer_stakeholder_m where CUSTOMER_ID="+Id);
			if(addCheck!=null && stackCheck!=null)
			{
				if(addCheck.equals("Y") && stackCheck!=null)
				{
					custStatus="Approved";
				}
				else
				{
					custStatus="UnApproved";
				}
				
			}
			else
			{
				custStatus="UnApproved";
			}
		}
		else
		{
			logger.info("Individual...................................... ");
			if(addCheck!=null)
			{
				if(addCheck.equals("Y"))
				{
					custStatus="Approved";
				}
				else
				{
					custStatus="UnApproved";
				}
			}
			else
			{
				custStatus="UnApproved";
			}
		}
		logger.info("custStatus............................... "+custStatus);
		return custStatus;
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
	
	
	public boolean deleteCustomerDocs(String[] roleId, String[] applType,
			String dealId) {
		logger.info("deleteApplDocs for deleteApplDocs....roleId.."+roleId+" dealId "+dealId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
    	try
    	{
     	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<roleId.length;k++)
		{
			String deleteChatge="delete from cr_document_dtl where TXN_TYPE='DC' AND STAGE_ID='PRS' AND DOC_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" )";
			logger.info("delete query:deletedocs "+deleteChatge);
			qryList.add(deleteChatge);
	
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}
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
	public ArrayList<CustomerSaveVo> copyAddress(String addressId,String source) 
	{
		
		logger.info("In copyAddress()");
		ArrayList list = new ArrayList();
		String table="cr_deal_address_m";
		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("GCD"))
			table="com_address_m";
		try 
		{			
			StringBuilder query=new StringBuilder();			
			query.append(" select distinct a.ADDRESS_TYPE,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.COUNTRY," +
					" b.COUNTRY_DESC,a.STATE,c.STATE_DESC,a.TAHSIL,a.DISTRICT,d.DISTRICT_DESC,a.PINCODE,a.PRIMARY_PHONE,a.ALTERNATE_PHONE," +
					" a.TOLLFREE_NUMBER,a.FAX,a.LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS,a.ADDRESS_DETAIL " +
					" from "+table+" a join com_country_m b on(b.COUNTRY_ID=a.COUNTRY) join com_state_m c on(c.STATE_ID=a.STATE) " +
					" join com_district_m d on(d.DISTRICT_ID=a.DISTRICT) where a.ADDRESS_ID='"+addressId.trim()+"'");
			logger.info("In copyAddress()   query  :  "+ query);
			CustomerSaveVo vo = null;
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());			
			query=null;			
			logger.info("defaultcountry() " + mainList.size());
			int size=mainList.size();			
			for (int i=0;i<size;i++)
			{
				ArrayList data = (ArrayList) mainList.get(i);
				if (data.size() > 0) 
				{
					vo = new CustomerSaveVo();
					vo.setAddr_type((CommonFunction.checkNull(data.get(0))).trim());
					vo.setAddr1((CommonFunction.checkNull(data.get(1))).trim());
					vo.setAddr2((CommonFunction.checkNull(data.get(2))).trim());
					vo.setAddr3((CommonFunction.checkNull(data.get(3))).trim());
					vo.setTxtCountryCode((CommonFunction.checkNull(data.get(4))).trim());
					vo.setCountry((CommonFunction.checkNull(data.get(5))).trim());
					vo.setTxtStateCode((CommonFunction.checkNull(data.get(6))).trim());
					vo.setState((CommonFunction.checkNull(data.get(7))).trim());
					vo.setTahsil((CommonFunction.checkNull(data.get(8))).trim());
					vo.setTxtDistCode((CommonFunction.checkNull(data.get(9))).trim());
					vo.setDist((CommonFunction.checkNull(data.get(10))).trim());
					vo.setPincode((CommonFunction.checkNull(data.get(11))).trim());
					vo.setPrimaryPhoneNo((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAlternatePhoneNo((CommonFunction.checkNull(data.get(13))).trim());
					vo.setTollfreeNo((CommonFunction.checkNull(data.get(14))).trim());
					vo.setFaxNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setLandMark((CommonFunction.checkNull(data.get(16))).trim());
					vo.setNoYears((CommonFunction.checkNull(data.get(17))).trim());
					vo.setNoMonths((CommonFunction.checkNull(data.get(18))).trim());
					vo.setDistanceBranch((CommonFunction.checkNull(data.get(19))).trim());
					vo.setCommunicationAddress((CommonFunction.checkNull(data.get(20))).trim());
					vo.setAddDetails((CommonFunction.checkNull(data.get(21))).trim());
					list.add(vo);
				}
			}
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		return list;
	}
//Ritu
	public boolean saveCustomerProfile(Object ob,String id,String source) 
	{
		logger.info("In saveCustomerProfile() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try
		{
			if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("UPDATE cr_deal_customer_m SET CUSTOMER_PROFILE=? WHERE customer_id=?");
					
					if ((CommonFunction.checkNull(vo.getCustomerProfile()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCustomerProfile()).trim());
					logger.info("id......."+id);
					
					if ((CommonFunction.checkNull(id)).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(id));
					//---------------------------------------------------------
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveCustomer......................"+status);
			}
			else
			{
			    	
			    	//int maxId=0;
			    	StringBuffer bufInsSql =	new StringBuffer();
					//int checkTable=0;			    	
			    	if(!vo.getStatusCase().equals(""))
			    	{			    		
//			    		String query3="Select max(ADDRESS_ID) from com_address_m_temp";
//						String id = ConnectionDAO.singleReturn(query3);
//						logger.info("id: "+id);
//						if(CommonFunction.checkNull(id).equalsIgnoreCase(""))
//							 maxId=1;
//						else
//							 maxId=Integer.parseInt(id)+1;
//						checkTable++;
			    	
			    		bufInsSql.append("UPDATE gcd_customer_m_temp SET CUSTOMER_PROFILE=? WHERE customer_id=?");
			    		
			    		
			    	}
			    	else
			    	{
			    		String table="gcd_customer_m";
			    		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			    			table="gcd_customer_m_edit";
			    		bufInsSql.append("UPDATE "+table+" SET CUSTOMER_PROFILE=? WHERE customer_id=?");
			    	}
					
					
					if(CommonFunction.checkNull(vo.getCustomerProfile()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getCustomerProfile()).trim());
					
					if(CommonFunction.checkNull(id).equalsIgnoreCase(""))
						insertPrepStmtObject.addString(id); 
					else
						insertPrepStmtObject.addString(id);
					
					
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveCustomerAddress() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustomer......................"+status);
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}
//Ritu
	public ArrayList<Object> getProfileDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) 
	{
		
		 String tableName="";
		 logger.info("In getProfileDetails() of CorpotateDAOImpl");
		 ArrayList<Object> list=new ArrayList<Object>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_customer_m";
			 try
			 {
					String query="SELECT c.customer_id,c.customer_profile,CUSTOMER_NAME from "+tableName+" c "+
                      "where c.customer_id="+code;
					
					logger.info("in getAddressDetails() of CorpotateDAOImpl  Query : "+query);  				
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
					
					for(int i=0;i<addressDetails.size();i++)
					{
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo addr=new CustomerSaveVo();
							addr.setBp_id1((CommonFunction.checkNull(data.get(0)).toString()));
							addr.setCustomerProfile((CommonFunction.checkNull(data.get(1)).toString()));
							addr.setCustomerName((CommonFunction.checkNull(data.get(2)).toString()));
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
					{
						tableName="gcd_customer_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="gcd_customer_m_edit";
					}
					else
					{
						tableName="gcd_customer_m_temp";
					}
				}
				else 
				{
					tableName="gcd_customer_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="gcd_customer_m_edit";
				}
				
				// START BY PRASHANT
				if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
				{
					tableName="gcd_customer_m_temp";
				}
				// END BY PRASHANT
				
				try
				{
						String query="SELECT c.customer_id,c.customer_profile,c.CUSTOMER_NAME "+
			              " from "+tableName+" c where  c.customer_id="+code;
						
						logger.info("in getAddressDetails() of CorpotateDAOImpl  Query :   "+query);
						ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
			
						logger.info("getAddressDetails "+addressDetails.size());
						for(int i=0;i<addressDetails.size();i++)
						{
							ArrayList data=(ArrayList)addressDetails.get(i);
							if(data.size()>0)
							{
								CustomerSaveVo addr=new CustomerSaveVo();
								addr.setBp_id1((CommonFunction.checkNull(data.get(0)).toString()));
								logger.info("bp_id"+addr.getBp_id1());
								addr.setCustomerProfile((CommonFunction.checkNull(data.get(1)).toString()));
								addr.setCustomerName((CommonFunction.checkNull(data.get(2)).toString()));
								list.add(addr);
							}
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}
//Ritu
	
	public ArrayList<Object> getProfileAll(String code, Object pageStatus,String updateFlag,String source) {
		ArrayList<Object> list=new ArrayList<Object>();
		String tableName="";
		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		{
			logger.info("In Credit Processing , Customer Entry..,getProfileAll()......");
			logger.info("from deal info in update flag>>>>>>>>>>>>> "+updateFlag);
			tableName="cr_deal_customer_m";
		}
		else
		{
			logger.info("In GCD..,getProfileAll()......");
			tableName="gcd_customer_m";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				tableName="gcd_customer_m_edit";
		}
		
		try{
				String query="select customer_id,customer_profile from "+tableName+"  where customer_id="+code;
				logger.info("................. from "+tableName+"  table............................query "+query);
				ArrayList addressAll = ConnectionDAO.sqlSelect(query);
				logger.info("getReferenceAll : : :  "+addressAll.size());
				for(int i=0;i<addressAll.size();i++){
					
					ArrayList data=(ArrayList)addressAll.get(i);
					if(data.size()>0)
					{
						CustomerSaveVo addr=new CustomerSaveVo();
						addr.setBp_id1((CommonFunction.checkNull(data.get(0)).toString()));
						
						addr.setCustomerProfile((CommonFunction.checkNull(data.get(1)).toString()));
						
						list.add(addr);
					}	
					
				}
			}catch (Exception e) {
					e.printStackTrace();
			}
		
		return list;	
	}

	public ArrayList<Object> getRelationDeatil() {
		
		ArrayList<Object> mainList=new ArrayList<Object>();
		
		try{
		String query=("select VALUE,DESCRIPTION FROM generic_master  WHERE GENERIC_KEY='RELATION_TYPE'  AND REC_STATUS='A'");
		logger.info("query for the relation *****"+query);
		ArrayList list=ConnectionDAO.sqlSelect(query);
		//ArrayList list1=ConnectionDAO.sqlSelect(query);
		logger.info("size of tyhe list********"+list.size());
		//logger.info("size of tyhe list********"+list1.size());
		for(int i=0;i<list.size();i++){
			ArrayList data=(ArrayList)list.get(i);
			if(data.size()>0){
				CustomerSaveVo relatVO=new CustomerSaveVo();
				relatVO.setRelationCode(CommonFunction.checkNull(data.get(0)));
				relatVO.setRelationshipS(CommonFunction.checkNull(data.get(1)));
				mainList.add(relatVO);
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return mainList;
	}

	//Amit Starts Here
	
	public ArrayList<CorporateBusinessActivityVO> getBusinessActivity(int code,Object pageStatus, String statusCase,String updateFlag,String updateInMaker,String pageStatuss,String cuaStatus,String source)
	{
		ArrayList<CorporateBusinessActivityVO> list=new ArrayList<CorporateBusinessActivityVO>();
		String tableName="";
		logger.info("Value of Flag cid: "+code);
		logger.info("Value of Flag pageStatus: "+pageStatus);
		logger.info("Value of Flag statusCase: "+statusCase);
		logger.info("Value of Flag updateFlag: "+updateFlag);
		logger.info("Value of Flag updateInMaker: "+updateInMaker);
		logger.info("Value of Flag cuaStatus: "+cuaStatus);
		StringBuffer query = null;
		if(updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit"))|| (pageStatuss.equalsIgnoreCase("fromLeads")))
		{
			logger.info("In CorporateDAOImpl getBusinessActivity() for CP-->Cust Entry.......");
			tableName="cr_deal_customer_business_activity_m";
			try
			{
				query = new StringBuffer();
				query.append("SELECT CUSTOMER_ID, BRANDS, PRODUCT_SERVICES, NO_OF_EMPLOYEES, AUDITORS, CERTIFICATIONS, " +
						"AWARDS, ASSOCIATION_NAME, REC_STATUS FROM "+tableName+" WHERE CUSTOMER_ID="+code);
				logger.info("query: "+query.toString());
				ArrayList businessActivityDetails = ConnectionDAO.sqlSelect(query.toString());
				query = null;
				logger.info("getBusinessActivity "+businessActivityDetails.size());
				for(int i=0;i<businessActivityDetails.size();i++)
				{
					
					ArrayList data=(ArrayList)businessActivityDetails.get(i);
					if(data.size()>0)
					{
						CorporateBusinessActivityVO vo = new CorporateBusinessActivityVO();
						vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
						vo.setBrands(CommonFunction.checkNull(data.get(1)).toString());
						vo.setProductServices(CommonFunction.checkNull(data.get(2)).toString());
						vo.setNoOfEmployees(CommonFunction.checkNull(data.get(3)).toString());
						vo.setAuditors(CommonFunction.checkNull(data.get(4)).toString());
						vo.setCertifications(CommonFunction.checkNull(data.get(5)).toString());
						vo.setAwards(CommonFunction.checkNull(data.get(6)).toString());
						vo.setAssocoationMembershipName(CommonFunction.checkNull(data.get(7)).toString());
						vo.setRecStatus(CommonFunction.checkNull(data.get(8)).toString());
						list.add(vo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			logger.info("In GCD.,getBusinessActivity().......");
			if(pageStatus!=null || updateInMaker!=null && updateInMaker.equals("updateInMaker") || statusCase!=null && !statusCase.equals(""))
			{
				if(statusCase!=null && statusCase.equalsIgnoreCase("Approved") && (updateInMaker==null || updateInMaker.equals("")))
				{
					tableName="customer_business_activity_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_business_activity_m_edit";
				}
				else
				{
					tableName="customer_business_activity_temp";
				}
			}
			else 
			{
				tableName="customer_business_activity_m";
				if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
					tableName="customer_business_activity_m_edit";
			}
			if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
			{
				tableName="customer_business_activity_temp";
			}
			try
			{
				query = new StringBuffer();
				query.append("SELECT CUSTOMER_ID, BRANDS, PRODUCT_SERVICES, NO_OF_EMPLOYEES, AUDITORS, CERTIFICATIONS, " +
						"AWARDS, ASSOCIATION_NAME, REC_STATUS FROM "+tableName+" WHERE CUSTOMER_ID="+code);
				logger.info("query: "+query.toString());
				ArrayList businessActivityDetails = ConnectionDAO.sqlSelect(query.toString());
				query =null;
				logger.info("getBusinessActivity "+businessActivityDetails.size());
				for(int i=0;i<businessActivityDetails.size();i++)
				{
					
					ArrayList data=(ArrayList)businessActivityDetails.get(i);
					if(data.size()>0)
					{
						CorporateBusinessActivityVO vo = new CorporateBusinessActivityVO();
						vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
						vo.setBrands(CommonFunction.checkNull(data.get(1)).toString());
						vo.setProductServices(CommonFunction.checkNull(data.get(2)).toString());
						vo.setNoOfEmployees(CommonFunction.checkNull(data.get(3)).toString());
						vo.setAuditors(CommonFunction.checkNull(data.get(4)).toString());
						vo.setCertifications(CommonFunction.checkNull(data.get(5)).toString());
						vo.setAwards(CommonFunction.checkNull(data.get(6)).toString());
						vo.setAssocoationMembershipName(CommonFunction.checkNull(data.get(7)).toString());
						vo.setRecStatus(CommonFunction.checkNull(data.get(8)).toString());
						list.add(vo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	return list;	
	}
	
	public boolean saveBusinessActivity(CorporateBusinessActivityVO vo, int id,String source)
	{
		boolean status=false;
		ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			try{
				if((vo.getPageStat()!=null && vo.getPageStat().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
				{
					logger.info("In Credit Processing , Customer Entry.,saveBusinessActivity().......");
				

			logger.info("In insert saveBusinessActivity");
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("insert into cr_deal_customer_business_activity_m(CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS,MAKER_ID,MAKER_DATE)");
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
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
						
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(id);
			
			if(CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBrands()).trim());
			
			if(CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getProductServices()).trim());
			
			if(CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(Integer.parseInt((vo.getNoOfEmployees())));
			
			if(CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuditors()).trim());
			
			if(CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
			    insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCertifications()).trim());
			
			if(CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAwards()).trim());
			
			if(CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAssocoationMembershipName()).trim());
			
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
			
			//---------------------------------------------------------
			
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveBusinessActivity() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveBusinessActivity......................"+status);
				}	
					
			else
			{
				
				logger.info("In GCD.,saveBusinessActivity().......");
				logger.info("Ivo.getStatusCase()......"+vo.getStatusCase());
				int maxId=0;
				StringBuffer bufInsSql = new StringBuffer();
				int checkTable=0;
		    	if(!vo.getStatusCase().equals(""))
		    	{
		    		String query3="Select max(BUSINESS_ACTIVITY_ID) from customer_business_activity_temp  WITH (ROWLOCK) ";
					 String maxid = ConnectionDAO.singleReturn(query3);
					 if(maxid==null || maxid=="")
					 {
						 maxId=1;
					 }
					 else
					 {
						 maxId=Integer.parseInt(maxid)+1;
					 }
					 logger.info("maxId : "+maxId);
					 checkTable++;
					 bufInsSql.append("insert into customer_business_activity_temp(BUSINESS_ACTIVITY_ID,CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS)");
					 bufInsSql.append(" values ( ");
		    		 bufInsSql.append(" ?," );
		    	}
		    	else
		    	{
		    		bufInsSql.append("insert into customer_business_activity_m(CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
		    	}
		
			if(checkTable!=0)
			{
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?)" );
			}
			if(checkTable==0)
			{
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
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
			}
			if(!vo.getStatusCase().equals(""))
	    	{
				insertPrepStmtObject.addInt(maxId);
	    	}
			
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(id);
			
			if(CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBrands()).trim());
			
			if(CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getProductServices()).trim());
			
			if(CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(Integer.parseInt((vo.getNoOfEmployees())));
			
			if(CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuditors()).trim());
			
			if(CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
			    insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCertifications()).trim());
			
			if(CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAwards()).trim());
			
			if(CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAssocoationMembershipName()).trim());
			
				insertPrepStmtObject.addString("P");
				
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
			}
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveBusinessActivity() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveBusinessActivity......................"+status);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
			    {
			    	String q1="select max(BUSINESS_ACTIVITY_ID) from customer_business_activity_m  WITH (ROWLOCK)  where CUSTOMER_ID="+id;
			    	String businessID=ConnectionDAO.singleReturn(q1);
			    	String query="INSERT INTO customer_business_activity_m_edit select * from customer_business_activity_m where  BUSINESS_ACTIVITY_ID ="+businessID;
			    	PrepStmtObject stmt = new PrepStmtObject();
			    	stmt.setSql(query);
			    	ArrayList list=new ArrayList();
			    	list.add(stmt);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
			    	logger.info("Edit Insert Status  :  "+status);			    	
			    	String query2="delete from customer_business_activity_m where  BUSINESS_ACTIVITY_ID ="+businessID;
			    	PrepStmtObject stmt2 = new PrepStmtObject();
			    	stmt2.setSql(query2);
			    	ArrayList list2=new ArrayList();
			    	list2.add(stmt2);
			    	status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
			    	
			    }
     		}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
	}
	
	public int updateBusinessActivity(CorporateBusinessActivityVO vo, int id,String recStatus, String statusCase, String updateFlag,String pageStatuss,String source)
	{
		int status=0;
		boolean qryStatus=false;
		String tableName="";
		StringBuilder subQuery = new StringBuilder();
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			logger.info("updateBusinessActivity :"+id);
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				logger.info("In Credit Processing , Customer Entry.,updateBusinessActivity().......");
				StringBuilder query = new StringBuilder();
				query.append("update cr_deal_customer_business_activity_m set BRANDS=?," +
				" PRODUCT_SERVICES=?, NO_OF_EMPLOYEES=?, AUDITORS=?, CERTIFICATIONS=?, " +
				"AWARDS=?, ASSOCIATION_NAME=?, REC_STATUS=?,");
				query.append("MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
				query.append(" where customer_id=?");
				
				
				if(CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBrands()).trim());
				
				if(CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getProductServices()).trim());
				
				if(CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
				
				if(CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAuditors()).trim());
				
				if(CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCertifications()).trim());
				
				if(CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAwards()).trim());
				
				if(CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAssocoationMembershipName()).trim());
				
				if(CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRecStatus()).trim());
				
				if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(id);
				
				insertPrepStmtObject.setSql(query.toString());
		        logger.info("IN updateBusinessActivity() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
			else
			{
				logger.info("In GCD.,updateBusinessActivity().......");
				if(statusCase!=null && statusCase.length()>0) //&& statusCase.equals("UnApproved"))
				{
					tableName="customer_business_activity_temp";
				}
				else
				{
					tableName="customer_business_activity_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="customer_business_activity_m_edit";
				}
				

				if(tableName.equalsIgnoreCase("customer_business_activity_m")||tableName.equalsIgnoreCase("customer_business_activity_m_edit")){
				
                 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
                 subQuery.append(dbo);
                 subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
     			
				 }else{
				 subQuery.append("");	
				 }
				String query="update "+tableName+" set BRANDS=?, PRODUCT_SERVICES=?, NO_OF_EMPLOYEES=?, AUDITORS=?, " +
				" CERTIFICATIONS=?, AWARDS=?, ASSOCIATION_NAME=?, REC_STATUS=? "+subQuery+"  where CUSTOMER_ID=?";
				
				if(CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBrands()).trim());
				
				if(CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getProductServices()).trim());
				
				if(CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
				
				if(CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAuditors()).trim());
				
				if(CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCertifications()).trim());
				
				if(CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAwards()).trim());
				
				if(CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAssocoationMembershipName()).trim());
				
				insertPrepStmtObject.addString("P");
//				if(tableName.equalsIgnoreCase("customer_business_activity_m")){
//					if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((vo.getMakerId()).trim());
//					
//					if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((vo.getMakerDate()).trim());
//				}
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(id);
	
				insertPrepStmtObject.setSql(query.toString());
				logger.info("IN updateBusinessActivity() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
				}catch(Exception e){
					e.printStackTrace();
					}finally{
						subQuery.append("");
					}
				if(qryStatus)
				{
					status=1;
				}
			return status;	
	}
	
	
	public ArrayList<Object> getBusinessDescription(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source)
	{
		String tableName="";
		 logger.info("In getBusinessDescription() of CorpotateDAOImpl");
		 ArrayList<Object> list=new ArrayList<Object>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_customer_m";
			 try
			 {
					String query="SELECT c.customer_id,c.BUSINESS_DESC,CUSTOMER_NAME from "+tableName+" c "+
                     "where c.customer_id="+code;
					
					logger.info("in getBusinessDescription() of CorpotateDAOImpl  Query : "+query);  				
					ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
					
					for(int i=0;i<addressDetails.size();i++)
					{
						ArrayList data=(ArrayList)addressDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo vo=new CustomerSaveVo();
							vo.setBp_id1((CommonFunction.checkNull(data.get(0)).toString()));
							vo.setBusinessDesc((CommonFunction.checkNull(data.get(1)).toString()));
							vo.setCustomerName((CommonFunction.checkNull(data.get(2)).toString()));
							list.add(vo);
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
					{
						tableName="gcd_customer_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="gcd_customer_m_edit";
					}
					else
					{
						tableName="gcd_customer_m_temp";
					}
				}
				else
				{
					tableName="gcd_customer_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="gcd_customer_m_edit";
				}
				
				
				if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
				{
					tableName="gcd_customer_m_temp";
				}
				try
				{
						String query="SELECT c.customer_id,c.BUSINESS_DESC,c.CUSTOMER_NAME "+
			              " from "+tableName+" c where  c.customer_id="+code;
						
						logger.info("in getBusinessDescription() of CorpotateDAOImpl  Query :   "+query);
						ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
			
						logger.info("getBusinessDescription "+addressDetails.size());
						for(int i=0;i<addressDetails.size();i++)
						{
							ArrayList data=(ArrayList)addressDetails.get(i);
							if(data.size()>0)
							{
								CustomerSaveVo vo=new CustomerSaveVo();
								vo.setBp_id1((CommonFunction.checkNull(data.get(0)).toString()));
								vo.setBusinessDesc((CommonFunction.checkNull(data.get(1)).toString()));
								vo.setCustomerName((CommonFunction.checkNull(data.get(2)).toString()));
								list.add(vo);
							}
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}
	
	public boolean saveBusinessDescription(Object ob,String id,String source) 
	{
		logger.info("In saveBusinessDescription() of CorpotateDAOImpl.");			
		CustomerSaveVo vo = (CustomerSaveVo)ob;
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try
		{
			if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
			{
					StringBuffer bufInsSql =	new StringBuffer();
					bufInsSql.append("UPDATE cr_deal_customer_m SET BUSINESS_DESC=? WHERE customer_id=?");
					
					if ((CommonFunction.checkNull(vo.getBusinessDesc()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBusinessDesc()).trim());
					logger.info("id......."+id);
					
					if ((CommonFunction.checkNull(id)).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(id));
					//---------------------------------------------------------
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveCustomer......................"+status);
			}
			else
			{
			    	StringBuffer bufInsSql =	new StringBuffer();
						    	
			    	if(!vo.getStatusCase().equals(""))
			    	{			    		
			    		bufInsSql.append("UPDATE gcd_customer_m_temp SET BUSINESS_DESC=? WHERE customer_id=?");
			     	}
			    	else
			    	{
			    		String table="gcd_customer_m";
			    		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			    			table="gcd_customer_m_edit";
			    		bufInsSql.append("UPDATE "+table+" SET BUSINESS_DESC=? WHERE customer_id=?");
			    	}
					
					
					if(CommonFunction.checkNull(vo.getBusinessDesc()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getBusinessDesc()).trim());
					
					if(CommonFunction.checkNull(id).equalsIgnoreCase(""))
						insertPrepStmtObject.addString(id); 
					else
						insertPrepStmtObject.addString(id);
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveBusinessDescription() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveBusinessDescription......................"+status);
				
		}
	}catch(Exception e)
	{e.printStackTrace();}
	return status;
}

	@Override
	public String checkCustomerVerifInit(String[] roleId, String[] applType,String dealId) {
		
		logger.info("checkCustomerVerifInit for checkCustomerVerifInit....roleId.."+roleId+" dealId "+dealId);
		String status = "";
		String status1 = "";
    	try
    	{
     	
    
	
		for(int k=0;k<roleId.length;k++)
		{
			
			String checkVerifInitCountQuery="SELECT R.DEAL_CUSTOMER_ID FROM cr_deal_customer_role R " +
								" INNER JOIN cr_deal_verification_dtl  V ON V.DEAL_ID=R.DEAL_ID AND V.ENTITY_TYPE=R.DEAL_CUSTOMER_ROLE_TYPE " +
								" INNER JOIN  cr_deal_address_m A ON A.BPID=R.DEAL_CUSTOMER_ID AND A.BPTYPE='CS' AND A.address_id=V.ENTITY_ID" +
								" WHERE R.DEAL_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' AND  R.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"' ";
			logger.info("checkVerifInitCountQuery: "+checkVerifInitCountQuery);
			status=ConnectionDAO.singleReturn(checkVerifInitCountQuery);
			logger.info("checkVerifInitCount "+status);
			
			String checkVerifInitCustomerQuery="SELECT R.DEAL_CUSTOMER_ID FROM cr_deal_customer_role R " +
			" INNER JOIN cr_deal_verification_dtl  V ON V.DEAL_ID=R.DEAL_ID  " +
			" WHERE R.DEAL_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' AND  R.DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"' ";
               logger.info("checkVerifInitCustomerQuery: "+checkVerifInitCustomerQuery);
               status1=ConnectionDAO.singleReturn(checkVerifInitCustomerQuery);
              logger.info("checkVerifInitCount "+status1);
			
			if(!CommonFunction.checkNull(status).equalsIgnoreCase("")||!CommonFunction.checkNull(status1).equalsIgnoreCase(""))
			{
				String customerNameQuery="SELECT C.CUSTOMER_NAME FROM cr_deal_customer_m C WHERE CUSTOMER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim()+"' ";
				if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
				{
					customerNameQuery="SELECT C.CUSTOMER_NAME FROM cr_deal_customer_m C WHERE CUSTOMER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(status1)).trim()+"' ";
				}
				
				logger.info("customerNameQuery: "+customerNameQuery);
				status=ConnectionDAO.singleReturn(customerNameQuery);
				logger.info("customerName "+status);
				return status;
			}
			
		
		}
		
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}

	@Override
	public boolean deleteVerificationInitCustomer(String[] roleId,
			String[] applType, String dealId) {
		
		logger.info("deleteVerificationInitCustomer for deleteVerificationInitCustomer....roleId.."+roleId+" dealId "+dealId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
    	try
    	{
     	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<roleId.length;k++)
		{
			
			
			String updateFVIMovementQuery="update cr_deal_movement_dtl  set DEAL_FORWARDED=NULL,DEAL_FORWARD_USER=NULL where DEAL_STAGE_ID='FVI' AND REC_STATUS='A' and DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'";
			logger.info("updateFVIMovementQuery q1 "+updateFVIMovementQuery);
			qryList.add(updateFVIMovementQuery);
			
			String verifIdQuery="select VERIFICATION_ID from cr_deal_verification_dtl WHERE ENTITY_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID IN (SELECT address_id FROM cr_deal_address_m WHERE BPTYPE='CS' AND BPID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ))";
			logger.info("verifIdQuery  "+verifIdQuery);
			String verifId=ConnectionDAO.singleReturn(verifIdQuery);
			logger.info("verifId  "+verifId);
			
			String deleteVerifCapturing="delete from cr_field_verification_dtl  where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifId).trim())+"' ";
			logger.info("delete deleteVerifCapturing q2 "+deleteVerifCapturing);
			qryList.add(deleteVerifCapturing);
			
			String deleteVerification="delete from cr_deal_verification_dtl where ENTITY_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID IN (SELECT address_id FROM cr_deal_address_m WHERE BPTYPE='CS' AND BPID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ))";
			logger.info("delete deleteVerificationInitCustomer q3 "+deleteVerification);
			qryList.add(deleteVerification);
			
			String verifIdForCustQuery="select VERIFICATION_ID from cr_deal_verification_dtl WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"' and ENTITY_ID IN (select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' )";
			logger.info("verifIdForCustQuery  "+verifIdForCustQuery);
			String verifIdForCust=ConnectionDAO.singleReturn(verifIdForCustQuery);
			logger.info("verifIdForCust  "+verifIdForCust);
			
			String deleteVerifCapturingForCust="delete from cr_field_verification_dtl  where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())+"' ";
			logger.info("delete deleteVerifCapturingForCust q2 "+deleteVerifCapturingForCust);
			qryList.add(deleteVerifCapturingForCust);
			
			String deleteVerificationForCust="delete from cr_deal_verification_dtl where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())+"' ";
			logger.info("delete deleteVerificationForCust q3 "+deleteVerificationForCust);
			qryList.add(deleteVerificationForCust);
			
			
			
		
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}

	@Override
	public ArrayList<Object> getCustomerRoleList(String loanId,String source) {
		
		
		logger.info("id in getCustomerRoleList");
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In , credit management , getCustomerRoleList *************************************    :"+loanId);
		try{

			String roleTable="cr_loan_customer_role";
			String custTable="gcd_customer_m";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			{
				roleTable="cr_loan_customer_role_edit";
				custTable="gcd_customer_m_edit";
			}
		String query=" SELECT LOAN_CUSTOMER_ROLE_ID, LOAN_CUSTOMER_TYPE,GCD_ID,LOAN_EXISTING_CUSTOMER,"+
		" CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT " +
		" from "+roleTable+" c "+
		" left join generic_master m on c.LOAN_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
		" left join "+custTable+" d on c.GCD_ID=CUSTOMER_ID"+
		" where LOAN_ID='"+loanId+"'";
		logger.info("getCustomerRoleList:Query "+query);		
		CreditProcessingCustomerEntryVo vo=null;
		ArrayList roleList = ConnectionDAO.sqlSelect(query);
		logger.info("getCustomerRoleList "+roleList.size());
		for(int i=0;i<roleList.size();i++){
			
			ArrayList data=(ArrayList)roleList.get(i);
			if(data.size()>0)
			{
				vo =new CreditProcessingCustomerEntryVo();
				vo.setRoleId((CommonFunction.checkNull(data.get(0)).toString()));
				
				if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("C"))
				{
					vo.setApplicantCategory("CORPORATE");
				}
				else if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("I"))
				{
					vo.setApplicantCategory("INDIVIDUAL");
				}
				vo.setCustomerId((CommonFunction.checkNull(data.get(2)).toString()));
				if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("N"))
				{
					vo.setExistingCustomer("NO");
				}
				else if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("Y"))
				{
					vo.setExistingCustomer("YES");
				}
				
				vo.setCustomerName((CommonFunction.checkNull(data.get(4)).toString()));
				logger.info("Applicant Type: "+CommonFunction.checkNull(data.get(5)));
				vo.setApplicantType((CommonFunction.checkNull(data.get(5)).trim().toString()));
				
				if(!(CommonFunction.checkNull(data.get(6)).toString()).equalsIgnoreCase(""))
	    		{
	    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
	    			vo.setGuaranteeAmount(myFormatter.format(reconNum));
	    		}else
	    		{
	    			vo.setGuaranteeAmount("0.00");
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


	public String moveFromGCDATCM(String customerId,String applType,String loanId,String tableStatus,String userId,String bDate,String source,String souce1,String source2) 
	{
		logger.info("In moveFromGCDATCM");		
		int statusProc=0;
		String appQ="";
		String procval="";
		boolean checkApp=false;
		if(applType!=null && applType.equalsIgnoreCase("PRAPPL"))
		{
			    appQ ="select LOAN_CUSTOMER_ID from cr_loan_dtl where LOAN_ID="+loanId+" and LOAN_CUSTOMER_ID is not NULL";
				logger.info("In Cr_deal_dtl. query....................."+appQ);
				checkApp=ConnectionDAO.checkStatus(appQ);
				if(checkApp)
					return "More than one applicant can not be possible.";
		}	
		String table="cr_loan_customer_role";
		if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			table="cr_loan_customer_role_edit";
		
		String q="select GCD_ID from "+table+" where GCD_ID="+customerId+" and LOAN_ID="+loanId;	
		logger.info("Query : "+q);		
        boolean exist=ConnectionDAO.checkStatus(q);	    
        if(exist)
        {
            	return "Customer being added already exist in this deal.";
        }
        else
        {
        	ArrayList<Object> in =new ArrayList<Object>();
 			ArrayList<Object> out =new ArrayList<Object>();
 			ArrayList outMessages = new ArrayList();
 			String s1="";
 			String s2="";
 			try 
        	{
 				in.add(CommonFunction.checkNull(source).trim());
        	    in.add(loanId);
        	    in.add(customerId);
        	    in.add(applType);
        	    in.add(tableStatus);
        	    in.add(userId);
        	    String date=CommonFunction.changeFormat(bDate);
        	    if(date != null)
        	    	in.add(date);
        	    out.add(s1);
        	    out.add(s2);
        	    logger.info("Gcd_Customer_Link_AT_CM("+in.toString()+","+out.toString()+")");
        	    outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Customer_Link_AT_CM",in,out);
        	    s1=CommonFunction.checkNull(outMessages.get(0));
        	    s2=CommonFunction.checkNull(outMessages.get(1));
        	    logger.info("s1: "+s1);
			    logger.info("s2: "+s2);
			    procval=s2;	
        	}
 			catch(Exception e)
        	{
 				logger.info("Exception In approve: "+e);
        	}
 			finally
 			{
 				in=null;
 	 			out=null;
 	 			outMessages=null; 
			}	
		}
       	return procval;	
	}

	@Override
	public boolean deleteCustomerRoleAtCM(String[] roleId, String[] applType,String loanId,String source) {		
		logger.info("deleteCustomerRoleAtCM() OF CorpotateDAOImpl");
	    boolean status=false;
		try
		{			
			ArrayList qryList = new ArrayList();
			String table="cr_loan_customer_role";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				table="cr_loan_customer_role_edit";
			
			for(int k=0;k<roleId.length;k++)
			{				 
				String query2="delete from "+table+" where LOAN_CUSTOMER_ROLE_ID=?";
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(roleId[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(roleId[k])).trim());
				insertPrepStmtObject.setSql(query2.toString());
				logger.info("deleteroleList() OF CorpotateDAOImpl query   :   " +insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
			/*	String q1="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim();
				logger.info("In query : ="+q1);
				String id=CommonFunction.checkNull(ConnectionDAO.singleReturn(q1));
				logger.info("Customer ID : ="+id);				
				if(!id.equalsIgnoreCase(""))
				{			
					String q2="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_CUSTOMER_ID="+id;
					logger.info("q2 "+q2);
					String idInDeal=CommonFunction.checkNull(ConnectionDAO.singleReturn(q2));
					logger.info("Customer ID : ="+idInDeal);				
					if(idInDeal!=null && !idInDeal.equalsIgnoreCase(""))
					{
						String query1="update cr_deal_dtl set DEAL_CUSTOMER_ID=?,DEAL_CUSTOMER_TYPE=?,DEAL_EXISTING_CUSTOMER=? WHERE DEAL_ID=?";
						PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
		        	   		insertPrepStmtObject2.addNull();
		        	   	else
		        	   		insertPrepStmtObject2.addString((CommonFunction.checkNull(dealId)).trim());
		        	   	insertPrepStmtObject2.setSql(query1.toString());
		        	   	logger.info("update this query  :  " +insertPrepStmtObject2.printQuery());
		        	   	qryList.add(insertPrepStmtObject2);
					}
				}*/
			}
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Status of Updation is   ::  "+status);
			}
			catch(Exception e)
			{e.printStackTrace();}
			return status;
	}

	@Override
	public boolean saveCustomerFromDeal(String loanId,String source) {
		
		logger.info("In saveCustomerFromDeal mod"+loanId);
		
		
		ArrayList qryList=new ArrayList();
		ArrayList qryList1=new ArrayList();
		boolean status=false;
		try {
			  StringBuilder	bufInsSql=null;
			
			 
			  StringBuilder checkQ=null;
			  StringBuilder count =	new StringBuilder();
			
	
			checkQ=new StringBuilder();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

			String table="cr_loan_customer_role";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
				table="cr_loan_customer_role_edit";
			
				    StringBuilder qry=new StringBuilder();
					 qry.append("DELETE FROM "+table+" WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "'");
					 qryList1.add(qry);
					 ConnectionDAO.sqlInsUpdDelete(qryList1);
					 qry=null;
			
			    bufInsSql =	new StringBuilder();
				bufInsSql.append("insert into "+table+" (LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append("SELECT "+loanId+",GCD_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,STATUS,MAKER_ID,MAKER_DATE FROM cr_deal_customer_role WHERE DEAL_ID=(SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "')");
				
		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN saveCustomerFromDeal()del insert query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		checkQ=null;
		bufInsSql=null;
	


		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		
		checkQ=null;
		bufInsSql=null;
		count=null;
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	logger.info("In saveCustomerFromDeal......................"+status);
	return status;
		
	}
	public ArrayList<CustomerSaveVo> getCustBankDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag,String cuaStatus,String source) 
	{
		
		 String tableName="";
		 logger.info("In getCustBankDetails() of CorpotateDAOImpl");
		 ArrayList<CustomerSaveVo> list=new ArrayList<CustomerSaveVo>();
		 if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag") ||(updateFlag!=null && updateFlag.equals("notEdit")))
		 {
			 tableName="cr_deal_cust_bank_details_m";
			 try
			 {
					String query="SELECT b.CUSTOMER_ID,b.BANK_ID,b.BANK_BRANCH_ID,c.BANK_NAME ,d.BANK_BRANCH_NAME,b.BANK_ACCOUNT,d.BRANCH_MICR_CODE,d.BRANCH_IFCS_CODE   from "+tableName+" b "+"left join COM_BANK_M c on b.BANK_ID=c.BANK_ID left join COM_BANKBRANCH_M d on d.BANK_BRANCH_ID=b.BANK_BRANCH_ID "
                      +"where b.CUSTOMER_ID="+code;
					
					logger.info("in getCustBankDetails Query : "+query);  				
					ArrayList custBankDetails = ConnectionDAO.sqlSelect(query);
					
					for(int i=0;i<custBankDetails.size();i++)
					{
						ArrayList data=(ArrayList)custBankDetails.get(i);
						if(data.size()>0)
						{
							CustomerSaveVo vo=new CustomerSaveVo();
							vo.setCustomerId((CommonFunction.checkNull(data.get(0)).toString()));
							vo.setLbxBankID((CommonFunction.checkNull(data.get(1)).toString()));
							vo.setLbxBranchID((CommonFunction.checkNull(data.get(2)).toString()));
							vo.setBankCode((CommonFunction.checkNull(data.get(3)).toString()));
							vo.setBankBranchName((CommonFunction.checkNull(data.get(4)).toString()));
							vo.setAccountNo((CommonFunction.checkNull(data.get(5)).toString()));							
							vo.setMicrCode((CommonFunction.checkNull(data.get(6)).toString()));
							vo.setIfscCode((CommonFunction.checkNull(data.get(7)).toString()));
							
							list.add(vo);
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
					{
						tableName="cust_bank_details_m";
						if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
							tableName="cust_bank_details_m_edit";
					}
					else
						tableName="cust_bank_details_tmp";
				}
				else 
				{
					tableName="cust_bank_details_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="cust_bank_details_m_edit";
				}			
				
				if(CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
				{
					tableName="cust_bank_details_tmp";
				}
				
				
				try
				{
					logger.info("in Else getCustBankDetails tableName "+tableName);
					String query="SELECT b.CUSTOMER_ID,b.BANK_ID,b.BANK_BRANCH_ID,c.BANK_NAME ,d.BANK_BRANCH_NAME,b.BANK_ACCOUNT,d.BRANCH_MICR_CODE,d.BRANCH_IFCS_CODE   from "+tableName+" b "+"left join COM_BANK_M c on b.BANK_ID=c.BANK_ID left join COM_BANKBRANCH_M d on d.BANK_BRANCH_ID=b.BANK_BRANCH_ID "
                    +"where b.CUSTOMER_ID="+code;
						
						logger.info("in getCustBankDetails()Else Part :   "+query);
						ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
			
						logger.info("getAddressDetails "+addressDetails.size());
						for(int i=0;i<addressDetails.size();i++)
						{
							ArrayList data=(ArrayList)addressDetails.get(i);
							if(data.size()>0)
							{
								CustomerSaveVo vo=new CustomerSaveVo();
								vo.setCustomerId((CommonFunction.checkNull(data.get(0)).toString()));
								vo.setLbxBankID((CommonFunction.checkNull(data.get(1)).toString()));
								vo.setLbxBranchID((CommonFunction.checkNull(data.get(2)).toString()));
								vo.setBankCode((CommonFunction.checkNull(data.get(3)).toString()));
								vo.setBankBranchName((CommonFunction.checkNull(data.get(4)).toString()));
								vo.setAccountNo((CommonFunction.checkNull(data.get(5)).toString()));								
								vo.setMicrCode((CommonFunction.checkNull(data.get(6)).toString()));
								vo.setIfscCode((CommonFunction.checkNull(data.get(7)).toString()));
								list.add(vo);
							}
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		 return list;
	}
	public boolean saveCustBankDetails(CustomerSaveVo vo, int id,String source)
	{
		logger.info("DaoIMPL saveCustBankDetails "+vo.getPageStat());
		boolean status=false;
		ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			try{
				if((vo.getPageStat()!=null && vo.getPageStat().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
				{
					logger.info("At the CP-DC saveCustBankDetails");
				

			logger.info("In insert saveCustBankDetails");
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("insert into cr_deal_cust_bank_details_m(CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );		
			bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
						
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(id);
			
			if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
			
			if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
			
			if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAccountNo());		
			
			
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
			
			//---------------------------------------------------------
			
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveCustBankDetails() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    logger.info("In saveCustBankDetails......................"+status);
				}	
					
			else
			{
				
				logger.info("In GCD.,saveCustBankDetails().......");
				int maxId=0;
				StringBuffer bufInsSql = new StringBuffer();
				int checkTable=0;
				logger.info("vo.getStatusCase()"+vo.getStatusCase());
		    	if(!vo.getStatusCase().equals(""))
		    	{
		    		String query3="Select max(CUST_BANK_ID) from cust_bank_details_tmp  WITH (ROWLOCK) ";
					 String maxid = ConnectionDAO.singleReturn(query3);
					 if(maxid==null || maxid=="")
					 {
						 maxId=1;
					 }
					 else
					 {
						 maxId=Integer.parseInt(maxid)+1;
					 }
					 logger.info("maxId : "+maxId);
					 checkTable++;
					 bufInsSql.append("insert into cust_bank_details_tmp(CUST_BANK_ID,CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS)");
					 bufInsSql.append(" values ( ");
		    		// bufInsSql.append(" ?," );
		    	}
		    	else
		    	{
		    		bufInsSql.append("insert into cust_bank_details_m(CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
		    	}
		
			if(checkTable!=0)
			{
				logger.info("checkTable!=0"+checkTable);
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?)" );				
			}
			if(checkTable==0)
			{
				logger.info("checkTable==0"+checkTable);
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );		
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
			}
			if(!vo.getStatusCase().equals(""))
	    	{
				insertPrepStmtObject.addInt(maxId);
	    	}
			
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(id);
			
			if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
			
			if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
			
			if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAccountNo());	
			
				insertPrepStmtObject.addString("A");
				
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
			}
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN CustBankDeatil Save() insert query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
//				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
//				StringBuffer bufInsSql1 =	new StringBuffer();
//				bufInsSql1.append("insert into cust_bank_details_m(CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
//				bufInsSql1.append(" values ( ");
//				bufInsSql1.append(" ?," );
//				bufInsSql1.append(" ?," );
//				bufInsSql1.append(" ?," );
//				bufInsSql1.append(" ?," );
//				bufInsSql1.append(" ?," );	
//				bufInsSql1.append(" ?," );		
//				bufInsSql1.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))" );
//				
//				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addInt(id);
//				
//				if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addInt(0);
//				else
//					insertPrepStmtObject1.addInt(Integer.parseInt(vo.getLbxBankID()));
//				
//				if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addInt(0);
//				else
//					insertPrepStmtObject1.addInt(Integer.parseInt(vo.getLbxBranchID()));
//				
//				if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addString(vo.getAccountNo());	
//				
//				insertPrepStmtObject1.addString("A");
//				//---------------------------------------------------------
//				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
//				
//				if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
//				
//				insertPrepStmtObject1.setSql(bufInsSql1.toString());
//				logger.info("IN saveBusinessActivity() insert query2 ### "+insertPrepStmtObject1.printQuery());
//				qryList.add(insertPrepStmtObject1);
		        status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED") && status)
			    {
			    	String q1="select max(CUST_BANK_ID) from cust_bank_details_m  WITH (ROWLOCK)  where CUSTOMER_ID="+id;
			    	String bank_id=ConnectionDAO.singleReturn(q1);
			    	String q2="INSERT INTO cust_bank_details_m_edit select *  from cust_bank_details_m where CUST_BANK_ID="+bank_id;
			    	PrepStmtObject stmt = new PrepStmtObject();
			    	stmt.setSql(q2);
					ArrayList list =new ArrayList();
					list.add(stmt);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
					logger.info("In saveCustomerReference......................"+status);
					
					String q3="delete from cust_bank_details_m where CUST_BANK_ID="+bank_id;
			    	PrepStmtObject stmt2 = new PrepStmtObject();
			    	stmt2.setSql(q3);
					ArrayList list2 =new ArrayList();
					list2.add(stmt2);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);			    	
			    }
			    logger.info("In saveBusinessActivity......................"+status);
     		}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
	}
	
	public int updateCustBankDetails(CustomerSaveVo vo, int id,String recStatus, String statusCase, String updateFlag,String pageStatuss,String source)
	{
		int status=0;
		boolean qryStatus=false;
		String tableName="";
		StringBuilder subQuery = new StringBuilder();
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			logger.info("updateCustBankDetails id :"+id);
			if(pageStatuss!=null && pageStatuss.equals("fromLeads")||updateFlag!=null && updateFlag.equals("updateFlag"))
			{
				logger.info("In Credit Processing , Customer Entry.,updateCustBankDetails().......");
				StringBuilder query= new StringBuilder();
				query.append("update cr_deal_cust_bank_details_m set BANK_ID=?," +
				" BANK_BRANCH_ID=?, BANK_ACCOUNT=?, REC_STATUS=?, " +
				"MAKER_ID=?,MAKER_DATE=");
				query.append(dbo);
				query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
				query.append(" where customer_id=?");
				
				
				if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
				
				if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
				
				if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAccountNo());	
				
				if(CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRecStatus()).trim());
				
				if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
				
				if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(id);
				
				insertPrepStmtObject.setSql(query.toString());
		        logger.info("IN updateCustBankDetails() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
			else
			{
				logger.info("In GCD.,updateCustBankDetails().......");
				if(statusCase!=null && statusCase.length()>0) //&& statusCase.equals("UnApproved"))
				{
					tableName="cust_bank_details_tmp";
				}
				else
				{
					tableName="cust_bank_details_m";
					if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
						tableName="cust_bank_details_m_edit";
				}
				

				if(tableName.equalsIgnoreCase("cust_bank_details_m") || tableName.equalsIgnoreCase("cust_bank_details_m_edit")){
				
                 subQuery.append(",MAKER_ID='"+CommonFunction.checkNull(vo.getMakerId())+"',MAKER_DATE=");
                 subQuery.append(dbo);
                 subQuery.append("STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate())+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
     			
      	
				 }else{
				 subQuery.append("");	
				 }
				String query="update "+tableName+" set BANK_ID=?, BANK_BRANCH_ID=?, BANK_ACCOUNT=?,REC_STATUS=? "+subQuery+"  where CUSTOMER_ID=?";
				
				if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
				
				if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
				
				if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAccountNo());	
				
				insertPrepStmtObject.addString("A");
//				if(tableName.equalsIgnoreCase("cust_bank_details_m")){
//					if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((vo.getMakerId()).trim());
//					
//					if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
//						insertPrepStmtObject.addNull();
//					else
//						insertPrepStmtObject.addString((vo.getMakerDate()).trim());
//				}
				
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(id);
	
				insertPrepStmtObject.setSql(query.toString());
				logger.info("IN updateCustBankDetails() update query1 ### insertPrepStmtObject "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
//				PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
//				String query1="update cust_bank_details_m set BANK_ID=?, BANK_BRANCH_ID=?, BANK_ACCOUNT=?, REC_STATUS=?, " +
//				"MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " +
//				"where customer_id=?";			
//				
//				if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject.addInt(0);
//				else
//					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
//				
//				if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject.addInt(0);
//				else
//					insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
//				
//				if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString(vo.getAccountNo());	
//				
//				insertPrepStmtObject1.addString("P");
//				
//				if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addString((vo.getMakerId()).trim());
//				
//				if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addString((vo.getMakerDate()).trim());
//				
//				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject1.addNull();
//				else
//					insertPrepStmtObject1.addInt(id);
//				
//				insertPrepStmtObject1.setSql(query1.toString());
//		        logger.info("IN updateCustBankDetails() update query1 ###insertPrepStmtObject1 "+insertPrepStmtObject1.printQuery());
//				qryList.add(insertPrepStmtObject1);
//				
				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
				}catch(Exception e){
					e.printStackTrace();
					}finally{
						subQuery.append("");
					}
				if(qryStatus)
				{
					status=1;
				}
			return status;	
	}
	
	@Override
	public boolean deleteCustomerDocsAtCM(String[] roleId, String[] applType,String loanId,String source) {
		
		logger.info("deleteCustomerDocsAtCM for deleteCustomerDocsAtCM....roleId.."+roleId+" loanId "+loanId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
    	try
    	{
     	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
    	String table="cr_document_dtl";
    	if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
    		table="cr_document_dtl_edit";
		for(int k=0;k<roleId.length;k++)
		{
			String deleteChatge="delete from "+table+" where TXN_TYPE='LIM' AND STAGE_ID='PRD' AND DOC_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and ENTITY_ID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" )";
			logger.info("delete query:deletedocs "+deleteChatge);
			qryList.add(deleteChatge);
	
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
		
	}

	public boolean checkApplExistance(String[] roleId, String dealId) 
	{
		boolean status=false;
		int count =0;
		String role="";
		for(int i=0;i<roleId.length;i++)
			role="'"+roleId[i]+"',";
		role=role.substring(0,role.length()-1);			
		String query="select count(1) from cr_deal_customer_role where deal_customer_role_id in("+role+") and deal_id='"+dealId.trim()+"' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'";
		count=Integer.parseInt(ConnectionDAO.singleReturn(query));
		if(count>0)
			status=true;
		return status;
	}




	@Override
	public boolean updateCustomerPRSDocsAtCM(String[] roleId,
			String[] applType, String loanId) {
		
		
		logger.info("updateCustomerPRSDocsAtCM for updateCustomerPRSDocsAtCM....roleId.."+roleId+" loanId "+loanId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
    	try
    	{
     	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<roleId.length;k++)
		{
			String deleteChatge="update cr_document_dtl set REC_STATUS='X' where TXN_TYPE='DC' AND STAGE_ID='PRS' AND  TXNID=(select DISTINCT LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"') and ENTITY_ID=(SELECT DISTINCT DEAL_CUSTOMER_ID FROM cr_deal_customer_role WHERE GCD_ID=(select DISTINCT GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+") )";
			logger.info("delete query:deletedocs "+deleteChatge);
			qryList.add(deleteChatge);
	
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of updation docs prs is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}
	
	public ArrayList<CustomerSaveVo> copyAddressAtCM(String addressId) 
	{
		
		logger.info("In copyAddress()");
		ArrayList list = new ArrayList();
		try 
		{			
			StringBuilder query=new StringBuilder();			
			query.append(" select distinct a.ADDRESS_TYPE,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.COUNTRY," +
					" b.COUNTRY_DESC,a.STATE,c.STATE_DESC,a.TAHSIL,a.DISTRICT,d.DISTRICT_DESC,a.PINCODE,a.PRIMARY_PHONE,a.ALTERNATE_PHONE," +
					" a.TOLLFREE_NUMBER,a.FAX,a.LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS,a.ADDRESS_DETAIL " +
					" from com_address_m a join com_country_m b on(b.COUNTRY_ID=a.COUNTRY) join com_state_m c on(c.STATE_ID=a.STATE) " +
					" join com_district_m d on(d.DISTRICT_ID=a.DISTRICT) where a.ADDRESS_ID='"+addressId.trim()+"'");
			logger.info("In copyAddress()   query  :  "+ query);
			CustomerSaveVo vo = null;
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());			
			query=null;			
			logger.info("defaultcountry() " + mainList.size());
			int size=mainList.size();			
			for (int i=0;i<size;i++)
			{
				ArrayList data = (ArrayList) mainList.get(i);
				if (data.size() > 0) 
				{
					vo = new CustomerSaveVo();
					vo.setAddr_type((CommonFunction.checkNull(data.get(0))).trim());
					vo.setAddr1((CommonFunction.checkNull(data.get(1))).trim());
					vo.setAddr2((CommonFunction.checkNull(data.get(2))).trim());
					vo.setAddr3((CommonFunction.checkNull(data.get(3))).trim());
					vo.setTxtCountryCode((CommonFunction.checkNull(data.get(4))).trim());
					vo.setCountry((CommonFunction.checkNull(data.get(5))).trim());
					vo.setTxtStateCode((CommonFunction.checkNull(data.get(6))).trim());
					vo.setState((CommonFunction.checkNull(data.get(7))).trim());
					vo.setTahsil((CommonFunction.checkNull(data.get(8))).trim());
					vo.setTxtDistCode((CommonFunction.checkNull(data.get(9))).trim());
					vo.setDist((CommonFunction.checkNull(data.get(10))).trim());
					vo.setPincode((CommonFunction.checkNull(data.get(11))).trim());
					vo.setPrimaryPhoneNo((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAlternatePhoneNo((CommonFunction.checkNull(data.get(13))).trim());
					vo.setTollfreeNo((CommonFunction.checkNull(data.get(14))).trim());
					vo.setFaxNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setLandMark((CommonFunction.checkNull(data.get(16))).trim());
					vo.setNoYears((CommonFunction.checkNull(data.get(17))).trim());
					vo.setNoMonths((CommonFunction.checkNull(data.get(18))).trim());
					vo.setDistanceBranch((CommonFunction.checkNull(data.get(19))).trim());
					vo.setCommunicationAddress((CommonFunction.checkNull(data.get(20))).trim());
					vo.setAddDetails((CommonFunction.checkNull(data.get(21))).trim());
					list.add(vo);
				}
			}
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		return list;
	}

	@Override
	public boolean getFirstNameStatus(CustomerSaveVo vo) {
		
		logger.info("In getFirstNameStatus..");
		boolean checkApp=false;
		String bpId=null;
		if(CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
			bpId=vo.getBp_id1().trim(); 
		else
			bpId=vo.getBp_id().trim(); 
		if((vo.getPageStatus()!=null && vo.getPageStatus().equals("fromLeads")) || (vo.getUpdateFlag()!=null && vo.getUpdateFlag().length()>0))
		{
		    String appQ ="select F_NAME from cr_deal_reference_m where BPID="+bpId+" and BPTYPE = '"+vo.getBp_type()+"' AND F_NAME='"+vo.getFirstName()+"' ";
			logger.info("In getFirstNameStatus(cr_deal_reference_m) query....................."+appQ);
			checkApp=ConnectionDAO.checkStatus(appQ);
			appQ=null;
			bpId=null;
		}
		else
		{
			if(!vo.getStatusCase().equals(""))
	    	{
				    String appQ ="select F_NAME from com_reference_m_temp where BPID="+bpId+" and BPTYPE = '"+vo.getBp_type()+"' AND F_NAME='"+vo.getFirstName()+"' ";
					logger.info("In getFirstNameStatus(com_reference_m_temp) query....................."+appQ);
					checkApp=ConnectionDAO.checkStatus(appQ);
					appQ=null;
					bpId=null;
	    	}
			else
			{
				    String appQ ="select F_NAME from com_reference_m where BPID="+bpId+" and BPTYPE = '"+vo.getBp_type()+"' AND F_NAME='"+vo.getFirstName()+"' ";
					logger.info("In getFirstNameStatus(com_reference_m) query....................."+appQ);
					checkApp=ConnectionDAO.checkStatus(appQ);
					appQ=null;
					bpId=null;
			}
		}
		
		return checkApp;
	}

	//method added by neeraj tripathi
	public String getPanCondition() 
	{
		logger.info("in getPanCondition() ");
		StringBuilder bufInsSql =    new StringBuilder();	 
		bufInsSql.append(" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAN_NO_FLAG' ");
	    logger.info("Query  to panCondition    :  "+bufInsSql.toString());
	    String panCondition="";
	    try
	    {
	    	panCondition=ConnectionDAO.singleReturn(bufInsSql.toString());
	    }
	    catch(Exception e)
		{e.printStackTrace();}	
	    logger.info("panCondition   :   "+panCondition);
		return panCondition;
   }
	

	public String checkCustomerVerifInitAtCM(String[] roleId, String[] applType,String loanId) {
		
		logger.info("checkCustomerVerifInitAtCM for ..roleId.."+roleId+" loanId "+loanId);
		String status = "";
		String status1 = "";
		try
		{
	 	


		for(int k=0;k<roleId.length;k++)
		{
			
			String checkVerifInitCountQuery="SELECT R.GCD_ID FROM cr_loan_customer_role R " +
								" INNER JOIN cr_deal_verification_dtl  V ON V.LOAN_ID=R.LOAN_ID AND V.ENTITY_TYPE=R.LOAN_CUSTOMER_ROLE_TYPE " +
								" INNER JOIN  com_address_m A ON A.BPID=R.GCD_ID AND A.BPTYPE='CS' AND A.address_id=V.ENTITY_ID" +
								" WHERE R.LOAN_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' AND  R.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' ";
			logger.info("checkVerifInitCountQuery: "+checkVerifInitCountQuery);
			status=ConnectionDAO.singleReturn(checkVerifInitCountQuery);
			logger.info("checkVerifInitCount "+status);
			
			String checkVerifInitCustomerQuery="SELECT R.GCD_ID FROM cr_loan_customer_role R " +
			" INNER JOIN cr_deal_verification_dtl  V ON V.LOAN_ID=R.LOAN_ID  " +
			" WHERE R.LOAN_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' AND  R.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' ";
	           logger.info("checkVerifInitCustomerQuery: "+checkVerifInitCustomerQuery);
	           status1=ConnectionDAO.singleReturn(checkVerifInitCustomerQuery);
	          logger.info("checkVerifInitCount "+status1);
			
			if(!CommonFunction.checkNull(status).equalsIgnoreCase("")||!CommonFunction.checkNull(status1).equalsIgnoreCase(""))
			{
				String customerNameQuery="SELECT C.CUSTOMER_NAME FROM gcd_customer_m C WHERE CUSTOMER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim()+"' ";
				if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
				{
					customerNameQuery="SELECT C.CUSTOMER_NAME FROM gcd_customer_m C WHERE CUSTOMER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(status1)).trim()+"' ";
				}
				
				logger.info("customerNameQuery: "+customerNameQuery);
				status=ConnectionDAO.singleReturn(customerNameQuery);
				logger.info("customerName "+status);
				return status;
			}
			
		
		}
		
	    }
		catch(Exception e)
		{
		e.printStackTrace();
	}
	return status;
	}

	@Override
	public boolean deleteVerificationInitCustomerAtCM(String[] roleId,
			String[] applType, String loanId) {
		
		logger.info("deleteVerificationInitCustomerAtCM for deleteVerificationInitCustomer....roleId.."+roleId+" loanId "+loanId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
		try
		{
	 	qryList = new ArrayList();
		bufInsUpdSql = new StringBuffer();

		for(int k=0;k<roleId.length;k++)
		{
		
			String verifIdQuery="select VERIFICATION_ID from cr_deal_verification_dtl WHERE ENTITY_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and ENTITY_ID IN (SELECT address_id FROM com_address_m WHERE BPTYPE='CS' AND BPID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ))";
			logger.info("verifIdQuery  "+verifIdQuery);
			String verifId=ConnectionDAO.singleReturn(verifIdQuery);
			logger.info("verifId  "+verifId);
			
			String deleteVerifCapturing="delete from cr_field_verification_dtl  where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifId).trim())+"' ";
			logger.info("delete deleteVerifCapturing q2 "+deleteVerifCapturing);
			qryList.add(deleteVerifCapturing);
			
			String deleteVerification="delete from cr_deal_verification_dtl where ENTITY_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and ENTITY_ID IN (SELECT address_id FROM com_address_m WHERE BPTYPE='CS' AND BPID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ))";
			logger.info("delete deleteVerificationInitCustomer q3 "+deleteVerification);
			qryList.add(deleteVerification);
			
			String verifIdForCustQuery="select VERIFICATION_ID from cr_deal_verification_dtl WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and ENTITY_ID IN (select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+"' )";
			logger.info("verifIdForCustQuery  "+verifIdForCustQuery);
			String verifIdForCust=ConnectionDAO.singleReturn(verifIdForCustQuery);
			logger.info("verifIdForCust  "+verifIdForCust);
			
			String deleteVerifCapturingForCust="delete from cr_field_verification_dtl  where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())+"' ";
			logger.info("delete deleteVerifCapturingForCust q2 "+deleteVerifCapturingForCust);
			qryList.add(deleteVerifCapturingForCust);
			
			String deleteVerificationForCust="delete from cr_deal_verification_dtl where VERIFICATION_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())+"' ";
			logger.info("delete deleteVerificationForCust q3 "+deleteVerificationForCust);
			qryList.add(deleteVerificationForCust);
			
			
			
		
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
		catch(Exception e)
		{
		e.printStackTrace();
	}
	return status;
	}

	public String getEmailMandatoryFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_MANDATORY_FLAG'");
		logger.info("Query for getting EMAIL_MANDATORY_FLAG from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			query = null;
		}
		return flag;
	}
	
	public String getMobileNoStatus(String tableName, String mobileNo,
			String addresId,String bpId) {
		
		String queryCheckMobileNo=null;
		if(CommonFunction.checkNull(addresId).equalsIgnoreCase(""))
		{
			queryCheckMobileNo="Select count(1) from "+tableName+"  where PRIMARY_PHONE='"+CommonFunction.checkNull(mobileNo)+"' AND ISNULL(REC_STATUS,'A')<>'X'";
		}
		else
		{
			queryCheckMobileNo="Select count(1) from "+tableName+"  where PRIMARY_PHONE='"+CommonFunction.checkNull(mobileNo)+"' AND ADDRESS_ID<>'"+CommonFunction.checkNull(addresId)+"' AND BPTYPE='CS' AND ISNULL(REC_STATUS,'A')<>'X'";
		}
		logger.info("queryCheckMobileNo  :  "+queryCheckMobileNo);
		String countCheckMobileNo =ConnectionDAO.singleReturn(queryCheckMobileNo);
		queryCheckMobileNo=null;
		return countCheckMobileNo;
	}
@Override
	public ArrayList<Object> getSarvSurkshaDetails(String code,
			String statusCase, String updateInMaker, String pageStatuss,
			String updateFlag, String cuaStatus, String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getSarvSurakshaAll(String code, Object pageStatus,
			String updateFlag, String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deletesarvaSuraksha(String string, String updateInMaker,
			String statusCase, Object pageStatus, String pageStatuss,
			String updateFlag, String source) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean savesavaSuraksha(Object ob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updateSarvaSuraksha(Object ob, int id, int refId,
			String recStatus, String statusCase, String updateFlag,
			String pageStatuss, String source) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CustomerSaveVo> getSarvaSurakshaDetail(String addr_id,
			Object pageStatus, String statusCase, String updateInMaker,
			String updateFlag, String pageStatuss, String source) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean getExistingIMD(String dealId, String[] roleId){
	logger.info("In getExistingIMD ");
	String query = "select count(1) from cr_instrument_dtl where txnid="+CommonFunction.checkNull(dealId)+" and txn_type = 'DC' and rec_status in('P','F','A','D','R','B')";
	logger.info("In getExistingIMD query  :  "+query);
	String countExistingIMD =ConnectionDAO.singleReturn(query);
	String q1="";
	boolean status=false;
	int applCount = 0;
	for(int k=0;k<roleId.length;k++)
	{
		q1 = "select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim();
		String applType =ConnectionDAO.singleReturn(q1);
		if(CommonFunction.checkNull(applType).equalsIgnoreCase("PRAPPL"))
			applCount=applCount+1;
		logger.info("applType: "+applType);
		logger.info("applcount: "+applCount);
	}
	query=null;
	if(Integer.parseInt(countExistingIMD)>0 && applCount>0)
	{	
		status=true;
	}
	return status;
	}
@Override
	public boolean saveCustomerAddress1(Object ob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveCorporateDetails1(Object ob, String st, String dealId,
			int maxId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean saveCorporateUpdate1(Object ob, int id, String recStatus,
			String statusCase, String updateFlag, String pageStatuss,
			String source) {
		// TODO Auto-generated method stub
		return false;
	}
/*	@Override
	public ArrayList getriskCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public ArrayList getriskCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}
//Shashank Starts Here
	@Override
	public ArrayList<Object> getRelationShipFlagCorporate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Object> getRelationShipFlagIndividual() {
		// TODO Auto-generated method stub
		return null;
	}
//Shashank Ends Here

	@Override
	public ArrayList<Object> getCustomerDetails(String addId, String customerId,String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveRelationShipDetails(String addressId,String customerId,
			String relationship, String relationAddressId,
			String relationCustomerId,String tableName) {
		// TODO Auto-generated method stub
		return false;
	}

	//Shashank Starts Here
	@Override
	public String getValidation(String code, Object pageStatus, String updateInMaker, String statusCase, String updateFlag, String pageStatuss, String source){
		// TODO Auto-generated method stub
		return null;
	}
	//Shashank Ends Here
}
	//Amit Ends Here

