package com.cp.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.NhbMappingDAO;
import com.cp.vo.CollateralVo;
import com.cp.vo.NhbMappingVo;

public class NhbMappingDAOImpl implements NhbMappingDAO {
	private static final Logger logger = Logger.getLogger(NhbMappingDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
public ArrayList<NhbMappingVo> getSearchNhbMapping(NhbMappingVo vo,HttpServletRequest request) {
	logger.info("In getSearchNhbMapping...................Dao Impl");
		ArrayList<NhbMappingVo> list = new ArrayList<NhbMappingVo>();
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			NhbMappingVo fieldVo= (NhbMappingVo) vo;
			boolean appendSQL=false;
			
			
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			StringBuilder dealNo=new StringBuilder();
			StringBuilder nhbCategory=new StringBuilder();
			
			 dealNo.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim()));
			 nhbCategory.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNhbCategoryId())).trim()));
			
			bufInsSql.append(" select distinct d.DEAL_ID,d.DEAL_NO,GROUP_CONCAT(IFNULL(DESCRIPTION,'') separator '|') NHB_CATEGORY_DESC,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,GROUP_CONCAT(IFNULL(nhb.NHB_CATEGORY,'') separator '|')");
			bufInsSqlTempCount.append(" select   count(1) FROM (SELECT D.DEAL_ID");
			bufInsSql.append(" from cr_deal_dtl d  join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
			bufInsSqlTempCount.append(" from cr_deal_dtl d  join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
			bufInsSql.append("  join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
			bufInsSqlTempCount.append("  join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			
			bufInsSql.append("  join cr_deal_nhb_mapping_dtl  nhb on nhb.deal_id=d.deal_id "); 
			bufInsSqlTempCount.append("  join cr_deal_nhb_mapping_dtl nhb  on nhb.deal_id=d.deal_id "); 
			bufInsSql.append("  join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID join GENERIC_MASTER GM on GM.VALUE=nhb.NHB_CATEGORY AND GENERIC_KEY='NHB_CATEGORY' AND GM.REC_STATUS='A' Where d.REC_STATUS='A' and d.deal_id in(select deal_id from cr_deal_nhb_mapping_dtl ) "); 
			bufInsSqlTempCount.append("  join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID join GENERIC_MASTER GM on GM.VALUE=nhb.NHB_CATEGORY AND GENERIC_KEY='NHB_CATEGORY' AND GM.REC_STATUS='A' Where d.REC_STATUS='A'  "); 
			
//			if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")) &&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNhbCategoryId()).trim()).equalsIgnoreCase("")))
//			{
//				
//		   	  bufInsSqlTempCount.append(") as b");
//			}
			
			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
		        bufInsSql.append(" AND d.DEAL_ID='"+dealNo+"' ");
		        bufInsSqlTempCount.append(" AND d.DEAL_ID='"+dealNo+"' ");
		   	 	appendSQL=true;
		   	  
		     }
			 
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNhbCategoryId())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND nhb.NHB_CATEGORY='"+StringEscapeUtils.escapeSql(vo.getNhbCategoryId()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND nhb.NHB_CATEGORY='"+StringEscapeUtils.escapeSql(vo.getNhbCategoryId()).trim()+"'");
			   	  appendSQL=true;
			     }
			bufInsSql.append(" GROUP BY d.DEAL_ID ");
			 bufInsSqlTempCount.append(" GROUP BY d.DEAL_ID )AS b ");
			 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			 logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
			if((dealNo.toString().trim()==null && nhbCategory.toString().trim()==null ) || (dealNo.toString().trim().equalsIgnoreCase("") && nhbCategory.toString().trim().equalsIgnoreCase("") ) || fieldVo.getCurrentPageLink()>1)
			{
			
			 logger.info("current PAge Link no .................... "+fieldVo.getCurrentPageLink());
			if(fieldVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				 logger.info("startRecordIndex .................... "+startRecordIndex);
				 logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			}
			 logger.info("query : "+bufInsSql);
		     header = ConnectionDAO.sqlSelect(bufInsSql.toString());

						
			for(int i=0;i<header.size();i++){
	//			logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					
					fieldVo = new NhbMappingVo();
					
					fieldVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setDealNo("<a href=NhbMapping.do?method=openEditNhbMapping&dealId="
							+ CommonFunction.checkNull(header1.get(0)).toString()
							+ ">"
							+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					fieldVo.setNhbCategory((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setNhbCategoryId((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setTotalRecordSize(count);
					list.add(fieldVo);
				}
			}
			
			if(header.size()==0)
			{
//				fieldVo = new FieldVerificationVo();
//				fieldVo.setTotalRecordSize(count);
//				list.add(fieldVo);
				request.setAttribute("flag","yes");
				
			}
			 logger.info("getTotalRecordSize : "+fieldVo.getTotalRecordSize());
			 
			 dealNo=null;
			 nhbCategory=null;
			 bufInsSql=null;
			 bufInsSqlTempCount=null;
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 logger.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
@Override
public boolean insertNhbCategory(NhbMappingVo vo, String[] arrNhbCategoryId) {
NhbMappingVo fieldValue = (NhbMappingVo) vo;
boolean status =false;

logger.info("In insertNhbCategory...................Dao Impl");
ArrayList qryList = new ArrayList();
PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

try {
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder query = new StringBuilder();

		if(CommonFunction.checkNull(vo.getEditFlag()).equalsIgnoreCase("EDIT")){
		query.append("delete from cr_deal_nhb_mapping_dtl where DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo()).trim()+"'");
		logger.info("delete qyery"+query.toString());
		ArrayList list=new ArrayList();
		list.add(query);
		status=ConnectionDAO.sqlInsUpdDelete(list);
		logger.info("delete qyery status :"+status);
		}
		
		
		for (int i = 0; i < arrNhbCategoryId.length; i++) {
			
			insertPrepStmtObject = null;
			bufInsSql = null;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append("INSERT INTO cr_deal_nhb_mapping_dtl(DEAL_ID,NHB_CATEGORY,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//POA_CODE
			bufInsSql.append(" ?,");//COURT_NAME_CODE
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,"); //makerId
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE
			
			if (CommonFunction.checkNull(fieldValue.getLbxDealNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldValue.getLbxDealNo());
			
			
			logger.info("insertPOAMaster:::::::::::: "+ arrNhbCategoryId[i]);

			if (CommonFunction.checkNull(arrNhbCategoryId[i])
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(arrNhbCategoryId[i]);

			
				insertPrepStmtObject.addString("A");
			

			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
	
			

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveViability() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			logger.info("In saveViability ........ insert query: "+bufInsSql);			
					
			try
			{
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveViability.........insert status: "+status);
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			

	}

} catch (Exception e) {
	e.printStackTrace();
}

finally
{
	
	qryList.clear();
	qryList=null;
}

return status;

}
@Override
public ArrayList<NhbMappingVo> getNhbMapping(String dealId) {
	ArrayList<NhbMappingVo> list = new ArrayList<NhbMappingVo>();
	NhbMappingVo fieldVo=null;
	try
	{
		logger.info("getNhbMapping() method in nhbmappingdaoimpl");
		ArrayList header=null;
		boolean appendSQL=false;
		
		
		StringBuffer bufInsSql=new StringBuffer();
		
		bufInsSql.append(" select distinct d.DEAL_ID,d.DEAL_NO,GROUP_CONCAT(IFNULL(DESCRIPTION,'') separator '|') NHB_CATEGORY_DESC,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,GROUP_CONCAT(IFNULL(nhb.NHB_CATEGORY,'') separator '|')");
		bufInsSql.append(" from cr_deal_dtl d  join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
		bufInsSql.append("  join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
		bufInsSql.append("  join cr_deal_nhb_mapping_dtl  nhb on nhb.deal_id=d.deal_id "); 
		bufInsSql.append("  join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID join GENERIC_MASTER GM on GM.VALUE=nhb.NHB_CATEGORY AND GENERIC_KEY='NHB_CATEGORY' AND GM.REC_STATUS='A' Where d.REC_STATUS='A'  "); 
		 
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase("")))) {
	        bufInsSql.append(" AND d.DEAL_ID='"+dealId+"' ");
	   	  
	     }
		
		 logger.info("query : "+bufInsSql);
	     header = ConnectionDAO.sqlSelect(bufInsSql.toString());

					
		for(int i=0;i<header.size();i++){
//			logger.info("header: "+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				
				fieldVo = new NhbMappingVo();
				
				fieldVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
				fieldVo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
				fieldVo.setNhbCategory((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
				fieldVo.setNhbCategoryId((CommonFunction.checkNull(header1.get(6))).trim());
				list.add(fieldVo);
			}
		}
		 logger.info("getTotalRecordSize : "+fieldVo.getTotalRecordSize());
		 
		 bufInsSql=null;

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	 logger.info("Detail List when searchList is : "+list.size());
	
	return list;

}

public ArrayList<Object> getNhbDescription(String nhbId) {
	ArrayList list=new ArrayList();
	try{
		logger.info("getNhbDescription() method in nhbmappingdaoimpl");
		StringBuffer query=new StringBuffer();
		query.append("SELECT DESCRIPTION,VALUE FROM GENERIC_MASTER WHERE GENERIC_KEY='NHB_CATEGORY' AND REC_STATUS='A' AND VALUE IN("+nhbId+")");
		logger.info("In getNhbDescription  :  "+query);
	
	ArrayList collateralsAll = ConnectionDAO.sqlSelect(query.toString());
	query=null;
	for(int i=0;i<collateralsAll.size();i++){
		ArrayList sublist = (ArrayList) collateralsAll.get(i);
		if(sublist.size()>0){
			NhbMappingVo av=new NhbMappingVo();
		    av.setNhbCategory((CommonFunction.checkNull(sublist.get(0))).trim());
		    av.setNhbCategoryId((CommonFunction.checkNull(sublist.get(1))).trim());
			list.add(av);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
	
}


}
