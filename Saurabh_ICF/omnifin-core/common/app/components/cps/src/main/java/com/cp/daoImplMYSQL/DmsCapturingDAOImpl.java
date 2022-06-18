package com.cp.daoImplMYSQL;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.cp.dao.DmsCapturingDao;
import java.sql.ResultSet;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.VO.CustomerSaveVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
//import com.cp.dao.PartnerCapturingDAO;
import com.cp.vo.ConsumerVo;
import com.cp.vo.DealMovementVo;
import com.cp.vo.DocumentsVo;
import com.cp.vo.PartnerCaptureVO;
//import com.cp.vo.ProjectCaptureVO;
import com.cp.vo.UnderwritingDocUploadVo;
//import com.logger.LoggerMsg;
import org.apache.log4j.Logger;
import java.util.*;
import java.sql.PreparedStatement;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.HeaderInfoVo;
public class DmsCapturingDAOImpl implements DmsCapturingDao 
{
	 private static final Logger logger = Logger.getLogger(DmsCapturingDAOImpl.class.getName());
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
		String dateFormat=resource.getString("lbl.dateInDao");
		int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
		
		

public ArrayList fetchUploadDocDocWise(String docId,String bpId,String bpType,String DocType,String entityId)
	{
	
	 
		ArrayList list=new ArrayList();
		ArrayList data=null;
		String TXN_TYPE="DC";
		try{
			//logger.info("In getUploadUnderwritingData..........................DAOImpl"+dealId);
			StringBuilder query=new StringBuilder();
			 /*query.append(" SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,S.STAGE_DESC,A.TXN_TYPE "
			 		+ " FROM CR_DOCUMENT_UPLOAD A "
			 		+ " JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID "
			 		+ " JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE "
			 		+ " where txn_id='"+bpId+"' and DOCUMENT_ID='"+docId+"'");*/
			
			query.append(" SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,'AG',A.TXN_TYPE,A.DOCUMENT_ID,c.DOC_DESC,IFNULL(A.DMS_ID,0)DMS_ID,A.DMS_DOC_URL  "
			 		+ " FROM CR_UPLOADED_DOCUMENTS A "
			 		+ " JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID "
			 		+ " LEFT JOIN CR_DOCUMENT_CHILD_M C ON C.DOC_CHILD_ID=A.CHILD_DOC_ID"
			 		+ " where txn_id='"+bpId+"' and a.DOC_ID='"+docId+"' and DOC_TYPE = '"+DocType+"'and A.DOCUMENT_STATUS='A' ");//Changes Add for Solve Entity id Issue
			//Changes Start For Solve Entity Id issue
			if(StringUtils.equalsIgnoreCase(DocType, "BUL") || StringUtils.equalsIgnoreCase(DocType, "DSA") || StringUtils.equalsIgnoreCase(DocType, "PRJ") || StringUtils.equalsIgnoreCase(DocType, "AG"))
			{
				TXN_TYPE=DocType;
			}
			else
			{
				TXN_TYPE=bpType;
			}
			query.append(" and TXN_TYPE='"+TXN_TYPE+"'");
			
			if(!CommonFunction.checkNull(entityId).equalsIgnoreCase("") && !StringUtils.equalsIgnoreCase(DocType, "APPL")){
				query.append(" and ENTITY_ID='"+entityId+"' ");
			}
			//Changes End For Solve Entity Id issue
			
			logger.info("In fetchUploadDoc...............query...........DAOImpl"+query);
			UnderwritingDocUploadVo uwDocVo = null;
			ArrayList product = ConnectionDAO.sqlSelect(query.toString());
			//logger.info("getUploadUnderwritingData size of list Product "+product.size());
			query=null;
			for(int i=0;i<product.size();i++){
				
				data=(ArrayList)product.get(i);
				if(data.size()>0)	{
					uwDocVo=new UnderwritingDocUploadVo();
					uwDocVo.setTxnId((CommonFunction.checkNull(data.get(0))).trim());
					uwDocVo.setFileName(CommonFunction.checkNull(data.get(1)).trim());
					uwDocVo.setDocDescription((CommonFunction.checkNull(data.get(2))).trim());
					uwDocVo.setUserName((CommonFunction.checkNull(data.get(3))).trim());
					uwDocVo.setUploadedState((CommonFunction.checkNull(data.get(5))).trim());
					uwDocVo.setTxnType((CommonFunction.checkNull(data.get(5))).trim());
					uwDocVo.setDocId((CommonFunction.checkNull(data.get(6))).trim());
					uwDocVo.setChildDocDesc((CommonFunction.checkNull(data.get(7))).trim());
					uwDocVo.setDmsDocId((CommonFunction.checkNull(data.get(8))).trim());
					uwDocVo.setDmsDocUrl((CommonFunction.checkNull(data.get(9))).trim());
					

					list.add(uwDocVo);
					uwDocVo=null;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			data=null;

		}

		return list;
	}

public boolean masterChildDocs(PartnerCaptureVO sh) {
	boolean status = false;
	String CheckChildDocs = "select DOC_CHILD_ID from cr_document_child_m where doc_id =  '"+sh.getDocId()+"' limit 1";
	try{
		CheckChildDocs = ConnectionDAO.singleReturn(CheckChildDocs);
		if(!CommonFunction.checkNull(CheckChildDocs).equalsIgnoreCase(""))
			status = true;
	}catch(Exception e){
		e.printStackTrace();
	}
	return status;
}

public ArrayList uploadDocumentChildDatafortempTable(Object ob) {
	ArrayList list = new ArrayList(); 
	PartnerCaptureVO uwDocVo = (PartnerCaptureVO)ob;
	String Doc_Id=uwDocVo.getDocId();
	String docChildIds = "";
	try{
		docChildIds = "select doc_child_ids from cr_document_dtl_temp where doc_id = '"+Doc_Id+"' and txnid = '"+uwDocVo.getBpId()+"' and txn_type = '"+uwDocVo.getBpType()+"' and DOC_TYPE = '"+uwDocVo.getDocType()+"' "  ;
		docChildIds = ConnectionDAO.singleReturn(docChildIds);
		if(!CommonFunction.checkNull(docChildIds).equalsIgnoreCase("")){
			docChildIds = docChildIds.replace("|", ",");
			docChildIds = (String)docChildIds.subSequence(0, docChildIds.length()-1);
		}
		else{
			docChildIds = new StringBuilder().append("select doc_child_ids from cr_document_dtl where doc_id = '").append(Doc_Id).append("' and txnid = '").append(uwDocVo.getBpId()).append("' and txn_type = '").append(uwDocVo.getBpType()).append("' and DOC_TYPE = '").append(uwDocVo.getDocType()).append("' ").toString();
        docChildIds = ConnectionDAO.singleReturn(docChildIds);
        if (!CommonFunction.checkNull(docChildIds).equalsIgnoreCase("")) {
            docChildIds = docChildIds.replace("|", ",");
            docChildIds = (String)docChildIds.subSequence(0, docChildIds.length() - 1);
          }else{
			docChildIds = "0";
          }
		}
		String query="SELECT DOC_CHILD_ID,doc_desc from cr_document_child_m where doc_id='"+Doc_Id+"' and rec_status='A' AND DOC_CHILD_ID IN ("+docChildIds+")" ;
		logger.info("Query    :-   "+query.toString());
		ArrayList ChildData = ConnectionDAO.sqlSelect(query);
		logger.info("gerApprovalData "+ChildData.size());
		for(int i=0;i<ChildData.size();i++){
		
			ArrayList data=(ArrayList)ChildData.get(i);
			if(data.size()>0)
			{
				PartnerCaptureVO addr=new PartnerCaptureVO();
				  addr.setDocChildId((CommonFunction.checkNull(data.get(0)).toString())); 
					addr.setDocumentDesc((CommonFunction.checkNull(data.get(1)).toString())); 
					list.add(addr);
			}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return list;
	
 
	}

public ArrayList uploadDocumentChildData(Object ob) {
	ArrayList list = new ArrayList(); 
	PartnerCaptureVO uwDocVo = (PartnerCaptureVO)ob;
	String Doc_Id=uwDocVo.getDocId();
	String docChildIds = "";
	try{
		docChildIds = "select doc_child_ids from cr_document_dtl where doc_id = '"+Doc_Id+"' and txnid = '"+uwDocVo.getBpId()+"' and txn_type = '"+uwDocVo.getBpType()+"' and DOC_TYPE = '"+uwDocVo.getDocType()+"' "  ;
		docChildIds = ConnectionDAO.singleReturn(docChildIds);
		if(!CommonFunction.checkNull(docChildIds).equalsIgnoreCase("")){
			docChildIds = docChildIds.replace("|", ",");
			docChildIds = (String)docChildIds.subSequence(0, docChildIds.length()-1);
		}
		else
			docChildIds = "0";
		
		String query="SELECT DOC_CHILD_ID,doc_desc from cr_document_child_m where doc_id='"+Doc_Id+"' and rec_status='A' AND DOC_CHILD_ID IN ("+docChildIds+")" ;
		logger.info("Query    :-   "+query.toString());
		ArrayList ChildData = ConnectionDAO.sqlSelect(query);
		logger.info("gerApprovalData "+ChildData.size());
		for(int i=0;i<ChildData.size();i++){
		
			ArrayList data=(ArrayList)ChildData.get(i);
			if(data.size()>0)
			{
				PartnerCaptureVO addr=new PartnerCaptureVO();
				  addr.setDocChildId((CommonFunction.checkNull(data.get(0)).toString())); 
					addr.setDocumentDesc((CommonFunction.checkNull(data.get(1)).toString())); 
					list.add(addr);
			}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return list;
	
 
	}
public String Internal_ExternalDocument()
{
	String INT_EXT_document = "";
	try{
		 INT_EXT_document="select parameter_value from parameter_mst where parameter_key='INTERNAL_DOCUMENT'";
		logger.info("Query    :-   "+INT_EXT_document);
		INT_EXT_document= ConnectionDAO.singleReturn(INT_EXT_document);
		logger.info("INT_EXT_document :  "+INT_EXT_document);
	}catch(Exception e){
		e.printStackTrace();
	}
	return INT_EXT_document;
}

public ArrayList getPartnerDocuments(String commonId,String stage, String bpType) {
	ArrayList<Object> list=new ArrayList<Object>();
	try{
		
		
		StringBuilder query=new StringBuilder();	
	    query.append("  SELECT TXN_DOC_ID,d.DOC_ID,d.DOC_DESC,DATE_FORMAT(DOC_RECEIVED_DATE,'"+dateFormat+"'),"+ 
				 "	DATE_FORMAT(DOC_DEFFRED_DATE,'"+dateFormat+"'),DATE_FORMAT(DOC_EXPIRY_DATE,'"+dateFormat+"'), "+
				 "	DOC_MANDATORY,DOC_ORIGINAL,DOC_STATUS,DOC_REMARKS,cdm.Doc_Expiry_Flag,DOC_CHILD_FLAG,DOC_CHILD_IDS,d.ENTITY_ID,cdm.DOC_EXPIRY_DAYS "+
				 "	from cr_document_dtl d,cr_document_m cdm "+
				 "	where  TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(commonId)).trim()+"' and TXN_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType)).trim()+"' "
				 		/*+ " and STAGE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(stage)).trim()+"' "*/
				 				+ " and d.doc_id=cdm.doc_id ");
	logger.info("getPartnerDocuments Queryl: "+query);
	PartnerCaptureVO docsVo = null;
	ArrayList docsdeatail = ConnectionDAO.sqlSelect(query.toString());
	logger.info("getPartnerDocuments OUTER ARRAYLIST SIZE: "+docsdeatail.size());
	
	
	
	for(int i=0;i<docsdeatail.size();i++){

		logger.info("getPartnerDocuments"+docsdeatail.get(i).toString());
		ArrayList docsdeatail1=(ArrayList)docsdeatail.get(i);
		if(docsdeatail1.size()>0)
		{
			logger.info("getPartnerDocuments INNNER ARRAYLIST SIZE: "+docsdeatail1.size());
			docsVo = new PartnerCaptureVO();
			docsVo.setDocId((CommonFunction.checkNull(docsdeatail1.get(0))).trim());
			docsVo.setRealDocId((CommonFunction.checkNull(docsdeatail1.get(1))).trim());
			docsVo.setDocDesc((CommonFunction.checkNull(docsdeatail1.get(2))).trim());
			docsVo.setRecieveDate((CommonFunction.checkNull(docsdeatail1.get(3))).trim());
			logger.info("recieve date:"+CommonFunction.checkNull(docsdeatail1.get(3)));
			docsVo.setDeferDate((CommonFunction.checkNull(docsdeatail1.get(4))).trim());
			docsVo.setExpirDate((CommonFunction.checkNull(docsdeatail1.get(5))).trim());

			if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("Y"))
			{
				docsVo.setMandatory("MANDATORY");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(6)).trim()).equals("N"))
			{
				docsVo.setMandatory("NON-MANDATORY");
			}

			if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("Y"))
			{
				docsVo.setOriginal("ORIGINAL");
			}
			else if((CommonFunction.checkNull(docsdeatail1.get(7)).trim()).equals("N"))
			{
				docsVo.setOriginal("PHOTOCOPY");
			}

			docsVo.setDocStatus((CommonFunction.checkNull(docsdeatail1.get(8))).trim());
			docsVo.setRemark((CommonFunction.checkNull(docsdeatail1.get(9))).trim());
			docsVo.setExpirFlag((CommonFunction.checkNull(docsdeatail1.get(10))).trim());
			docsVo.setDocChildFlag((CommonFunction.checkNull(docsdeatail1.get(11))).trim());
			docsVo.setDocChildId((CommonFunction.checkNull(docsdeatail1.get(12))).trim());
			docsVo.setEntityId((CommonFunction.checkNull(docsdeatail1.get(13))).trim());
			docsVo.setDaysExp((CommonFunction.checkNull(docsdeatail1.get(14))).trim());
				list.add(docsVo);
		}
		query=null;
	}

	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		commonId=null;
		stage=null;
		bpType=null;
	}
	return list;
}
public Map<String,String> getDMSDetails(String txnType,String txnId,String entityType,String entityId,String loginUserId, String docId){
	 
	Map<String, String> response=new HashMap<String,String>();
	StringBuilder query=new StringBuilder();
	StringBuilder query2=new StringBuilder();
	PreparedStatement  preparedStatement= null;
	PreparedStatement  preparedStatement2= null;
	Connection con=null;
	ResultSet resultSet=null;
	ResultSet resultSet2=null;
	try{
		if(StringUtils.equalsIgnoreCase(txnType, "DC") || StringUtils.equalsIgnoreCase(txnType, "LIM"))
		{
			query.append(" SELECT CDD.DEAL_APPLICATION_FORM_NO,CBM.BRANCH_DESC DEAL_BRANCH,CPM.PRODUCT_DESC,CSM.SCHEME_DESC,CDD.DMS_DOC_NUMBER "
					+ " FROM CR_DEAL_DTL CDD "
					+ " JOIN CR_DEAL_LOAN_DTL CDLD ON(CDLD.DEAL_ID=CDD.DEAL_ID) "
					+ " JOIN CR_PRODUCT_M CPM ON(CPM.PRODUCT_ID=CDLD.DEAL_PRODUCT) "
					+ " JOIN CR_SCHEME_M CSM ON(CSM.SCHEME_ID=CDLD.DEAL_SCHEME) "
					+ " JOIN COM_BRANCH_M CBM ON (CBM.BRANCH_ID=CDD.DEAL_BRANCH)");
			if(StringUtils.equalsIgnoreCase(txnType, "LIM")){	
				query.append(" JOIN CR_LOAN_DTL CLD ON(CLD.LOAN_DEAL_ID=CDD.DEAL_ID)"
				+ " WHERE CLD.LOAN_ID='"+txnId+"'");
			}else{
				query.append(" WHERE CDD.DEAL_ID='"+txnId+"'");
			}
			logger.info("getDMSDetails Query :"+ query);
			con=ConnectionDAO.getConnection();
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()){
				
			 
				query2.setLength(0);
				response.put("applicationFormNo", resultSet.getString("DEAL_APPLICATION_FORM_NO"));
				response.put("branch", resultSet.getString("DEAL_BRANCH"));
				response.put("product", resultSet.getString("PRODUCT_DESC"));
				response.put("scheme", resultSet.getString("SCHEME_DESC"));
				response.put("uploadedBy", loginUserId);
				response.put("txnType", txnType);
				response.put("txnId", txnId);
				response.put("entityType", entityType);
				response.put("entityId", entityId);
				response.put("childDocId", "");
				response.put("documentTypeName", "");
				response.put("docId",docId);
				response.put("documentTypeName", getDocumentDesc(docId));
				
				if(StringUtils.equalsIgnoreCase(entityType, "APPL") || StringUtils.isBlank(entityType) || StringUtils.equalsIgnoreCase(entityType, "DRF")){
					response.put("dmsNumber", resultSet.getString("DMS_DOC_NUMBER"));
					response.put("entityDesc", resultSet.getString("PRODUCT_DESC")+"/"+resultSet.getString("SCHEME_DESC"));
					response.put("panNumber", "");
					response.put("aadharNumber", "");
					response.put("email", "");
					response.put("mobileNumber", "");
					response.put("dob", "");
					
				}
				if(StringUtils.equalsIgnoreCase(entityType, "PRAPPL") || StringUtils.equalsIgnoreCase(entityType, "COAPPL") || StringUtils.equalsIgnoreCase(entityType, "GUARANTOR")){
					query2.append(" SELECT CDCM.DMS_DOC_NUMBER,CDCM.CUSTOMER_NAME,CDCM.CUSTMER_PAN,CDCM.UID_NO,CDCM.CUSTOMER_EMAIL,CD.PRIMARY_PHONE,CDCM.CUSTOMER_DOB "
							+ " FROM CR_DEAL_CUSTOMER_ROLE CDCR "
							+ " JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=CDCR.DEAL_CUSTOMER_ID AND CDCR.DEAL_CUSTOMER_ROLE_TYPE='"+entityType+"') "
							+  " JOIN  cr_deal_address_m CD ON ( cd.bpid=CDCM.customer_id and  communication_address ='Y') "
							+ " WHERE CDCR.DEAL_ID='"+txnId+"' AND CDCM.CUSTOMER_ID='"+entityId+"'");
					logger.info("getDMSDetails Query2 :"+ query2);
					preparedStatement2 = con.prepareStatement(query2.toString());
					resultSet2 = preparedStatement2.executeQuery();
					while(resultSet2.next()){
						response.put("dmsNumber", resultSet2.getString("DMS_DOC_NUMBER"));
						response.put("entityDesc", resultSet2.getString("CUSTOMER_NAME"));
						response.put("panNumber", resultSet2.getString("CUSTMER_PAN"));
						response.put("aadharNumber", resultSet2.getString("UID_NO"));
						response.put("email", resultSet2.getString("CUSTOMER_EMAIL"));
						response.put("mobileNumber", resultSet2.getString("PRIMARY_PHONE"));
						response.put("dob", resultSet2.getString("CUSTOMER_DOB"));
					}
				}
				if(StringUtils.equalsIgnoreCase(entityType, "ASSET")){
					query2.append(" SELECT CACM.DMS_DOC_NUMBER,CACM.ASSET_COLLATERAL_DESC  "
							+ " FROM CR_DEAL_COLLATERAL_M COL "
							+ " JOIN CR_ASSET_COLLATERAL_M CACM ON(COL.ASSETID=CACM.ASSET_ID) "
							+ " WHERE COL.DEAL_ID='"+txnId+"' AND CACM.ASSET_ID='"+entityId+"' ");
					logger.info("getDMSDetails Query2 :"+ query2);
					preparedStatement2 = con.prepareStatement(query2.toString());
					resultSet2 = preparedStatement2.executeQuery();
					while(resultSet2.next()){
						response.put("dmsNumber", resultSet2.getString("DMS_DOC_NUMBER"));
						response.put("entityDesc", resultSet2.getString("ASSET_COLLATERAL_DESC"));
						response.put("panNumber", "");
						response.put("aadharNumber", "");
						response.put("email", "");
						response.put("mobileNumber", "");
						response.put("dob", "");
					}
				}
				if(StringUtils.equalsIgnoreCase(entityType, "BOUNCE")){
					response.put("entityDesc", "IMD Bounce Approval");
					response.put("documentTypeName", "IMD Bounce Approval Upload");
					response.put("dmsNumber",resultSet.getString("DMS_DOC_NUMBER"));
					response.put("panNumber", "");
					response.put("aadharNumber", "");
					response.put("email", "");
					response.put("mobileNumber", "");
					response.put("dob", "");
				}
				if(StringUtils.equalsIgnoreCase(entityType, "LEGAL")){
					response.put("entityDesc", "LEGAL NOTICE");
					response.put("documentTypeName", "LEGAL NOTICE UPLOAD");
					response.put("dmsNumber",resultSet.getString("DMS_DOC_NUMBER"));
					response.put("panNumber", "");
					response.put("aadharNumber", "");
					response.put("email", "");
					response.put("mobileNumber", "");
					response.put("dob", "");
				}
				if(StringUtils.equalsIgnoreCase(entityType, "LVI") || StringUtils.equalsIgnoreCase(entityType, "TVI") || StringUtils.equalsIgnoreCase(entityType, "RVI") || StringUtils.equalsIgnoreCase(entityType, "FVI") || StringUtils.equalsIgnoreCase(entityType, "FVILM") || StringUtils.equalsIgnoreCase(entityType, "RVILM") || StringUtils.equalsIgnoreCase(entityType, "TVILM") || StringUtils.equalsIgnoreCase(entityType, "LVILM")){
				 
					query2.append(" SELECT a.DMS_DOC_NUMBER "
							+ " FROM cr_field_verification_dtl a "
							+ " join cr_deal_verification_dtl b on a.VERIFICATION_ID=b.VERIFICATION_ID"
							+ " WHERE  a.FIELD_VERIFICATION_ID='"+entityId+"'");
					if(StringUtils.equalsIgnoreCase(entityType, "FVILM") || StringUtils.equalsIgnoreCase(entityType, "RVILM") || StringUtils.equalsIgnoreCase(entityType, "TVILM") || StringUtils.equalsIgnoreCase(entityType, "LVILM")){
						query.append(" and b.LOAN_ID='"+txnId+"'");
					}else{
						query.append(" and CDD.DEAL_ID='"+txnId+"'");
					}
					logger.info("getDMSDetails Query:"+ query2);
					con=ConnectionDAO.getConnection();
					preparedStatement2 = con.prepareStatement(query2.toString());
					resultSet2 = preparedStatement2.executeQuery();
					while(resultSet2.next()){
						response.put("documentTypeName", "Additional Documents");
						if(StringUtils.equalsIgnoreCase(entityType, "LVI")){
							response.put("entityDesc", "Legal Verification");
							response.put("documentTypeName", "Legal Verification Upload");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "TVI")){
							response.put("entityDesc", "Technical Verification");
							response.put("documentTypeName", "Technical Verification Upload");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "RVI")){
							response.put("entityDesc", "RCU Verification");
							response.put("documentTypeName", "RCU Verification Upload");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "FVI")){
							response.put("entityDesc", "Field Verification");
							response.put("documentTypeName", "Field Verification Upload");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "FVILM")){
							response.put("entityDesc", "Field Verification(Credit Management)");
							response.put("documentTypeName", "Field Verification Upload(Credit Management)");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "RVILM")){
							response.put("entityDesc", "RCU Verification(Credit Management)");
							response.put("documentTypeName", "RCU Verification Upload(Credit Management)");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "TVILM")){
							response.put("entityDesc", "Technical Verification(Credit Management)");
							response.put("documentTypeName", "Technical Verification Upload(Credit Management)");
						}
						if(StringUtils.equalsIgnoreCase(entityType, "LVILM")){
							response.put("entityDesc", "Legal Verification(Credit Management)");
							response.put("documentTypeName", "Legal Verification Upload(Credit Management)");
						}
						response.put("panNumber", "");
						response.put("aadharNumber", "");
						response.put("email", "");
						response.put("mobileNumber", "");
						response.put("dob", "");
						response.put("dmsNumber", resultSet2.getString("DMS_DOC_NUMBER"));
					}
				}
			}
		}
		if(StringUtils.equalsIgnoreCase(txnType, "DSA")|| StringUtils.equalsIgnoreCase(txnType, "BUL") || StringUtils.equalsIgnoreCase(txnType, "AG")){
			query.append(" SELECT BP.BP_ID AS BP_ID,BP.BP_TYPE AS BP_TYPE,BP.BP_NAME AS BP_NAME,BRANCH.BRANCH_DESC AS BRANCH_DESC,IFNULL(BP.PAN_NO,'')PAN_NO,BP.DMS_DOC_NUMBER AS DMS_DOC_NUMBER "
					+ " FROM CR_BP_DTL BP"
					+ " JOIN COM_BRANCH_M BRANCH ON (BRANCH.BRANCH_ID=BP.BP_BRANCH)"
					+ " WHERE BP.BP_ID='"+txnId+"'");
			logger.info("getDMSDetails Query :"+ query);
			con=ConnectionDAO.getConnection();
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				response.put("applicationFormNo", "");
				response.put("branch", resultSet.getString("BRANCH_DESC"));
				response.put("product", "");
				response.put("scheme", "");
				response.put("uploadedBy", loginUserId);
				response.put("txnType", txnType);
				response.put("txnId", txnId);
				response.put("entityType", txnType);
				response.put("entityId", "0");
				response.put("childDocId", "");
				response.put("docId",docId);
				response.put("documentTypeName", getDocumentDesc(docId));
				response.put("dmsNumber", resultSet.getString("DMS_DOC_NUMBER"));
				response.put("entityDesc", resultSet.getString("BP_NAME"));
				response.put("panNumber", resultSet.getString("PAN_NO"));
				response.put("aadharNumber", "");
				response.put("email", "");
				response.put("mobileNumber", "");
				response.put("dob", "");
			}
		}
		if(StringUtils.equalsIgnoreCase(txnType, "PRJ")){
			query.append(" SELECT PROJECT.PROJECT_ID AS BP_ID,'PRJ' AS BP_TYPE,PROJECT.PROJECT_NAME AS PROJECT_NAME,BRANCH.BRANCH_DESC AS BRANCH_DESC,PROJECT.DMS_DOC_NUMBER AS DMS_DOC_NUMBER "
					+ " FROM CR_BP_PROJECT_DTL PROJECT"
					+ " JOIN COM_BRANCH_M BRANCH ON (BRANCH.BRANCH_ID=PROJECT.BRANCH)"
					+ " WHERE PROJECT.PROJECT_ID='"+txnId+"'");
			logger.info("getDMSDetails Query :"+ query);
			con=ConnectionDAO.getConnection();
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				response.put("applicationFormNo", "");
				response.put("branch", resultSet.getString("BRANCH_DESC"));
				response.put("product", "");
				response.put("scheme", "");
				response.put("uploadedBy", loginUserId);
				response.put("txnType", txnType);
				response.put("txnId", txnId);
				response.put("entityType", txnType);
				response.put("entityId","0");
				response.put("childDocId", "");
				response.put("docId",docId);
				response.put("documentTypeName", getDocumentDesc(docId));
				response.put("dmsNumber", resultSet.getString("DMS_DOC_NUMBER"));
				response.put("entityDesc", resultSet.getString("PROJECT_NAME"));
				response.put("panNumber","");
				response.put("aadharNumber", "");
				response.put("email", "");
				response.put("mobileNumber", "");
				response.put("dob", "");
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		if (preparedStatement2 != null) {
			try {
				preparedStatement2.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		if (resultSet2 != null) {
			try {
				resultSet2.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		if(con != null) {
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
	}
	return response;
}
public String getDocumentDesc(String docId){
	logger.info("Method for getInvoiceFileDtl");
	StringBuilder query=new StringBuilder();
	PreparedStatement  preparedStatement= null;
	Connection con=null;
	ResultSet resultSet=null;
	String documentDesc="";
	if(StringUtils.isBlank(docId)){
		docId="0";
	}
	docId=docId.trim();
	try{
		query.append("SELECT DOC_DESC FROM CR_DOCUMENT_M WHERE DOC_ID='"+docId+"'");
		logger.info("getDocumentDesc Query :"+ query);
		con=ConnectionDAO.getConnection();
		preparedStatement = con.prepareStatement(query.toString());
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			documentDesc=resultSet.getString("DOC_DESC");
		}
		if(StringUtils.isBlank(documentDesc)){
			documentDesc="Additional Documents";
		}
	}catch(Exception e){
		documentDesc="Additional Documents";
		e.printStackTrace();
	}finally{
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
		
		if(con != null) {
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
	}
	return documentDesc;
}
public ArrayList getDealHeader(String id)
{
	
	
	logger.info("ShyamaK"+id);
	ArrayList list=new ArrayList();
	try
	{
		StringBuilder query=new StringBuilder();
		 query.append(" select d.deal_id, deal_no,d.DEAL_CUSTOMER_ID,deal.CUSTOMER_NAME,DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY,d.REC_STATUS,S.MIN_AMT_FIN,S.MAX_AMT_FIN  from cr_deal_dtl d "+
					    " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID"+
						" left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
						" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"+
						" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+
						" where d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+" limit 1");
		
		logger.info("getDealHeader Query: "+query.toString());
		
		HeaderInfoVo vo= null;
		ArrayList header = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		int size=header.size();
		for(int i=0;i<size;i++){
			
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
				vo.setSchemeMinAmt((CommonFunction.checkNull(header1.get(9))).trim());
				vo.setSchemeMaxAmt((CommonFunction.checkNull(header1.get(10))).trim());
				list.add(vo);
				vo=null;
			}
			header1.clear();
			header1=null;
		}
		header.clear();
		header=null;
		id=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	return list;
}
public ArrayList getUploadUnderwritingData(String dealId)
{
	ArrayList list=new ArrayList();
	ArrayList data=null;
	try{
			StringBuilder query=new StringBuilder(""
			+ " SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,S.STAGE_DESC,"
			+ " case A.DOC_Type when 'APPL' THEN 'APPLICATION'  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO APPLICANT' "
			+ " WHEN 'GUARANTOR' THEN 'GUARANTOR' WHEN 'ASSET' THEN 'ASSET' "
			+ " WHEN 'COLLATERAL' THEN 'COLLATERAL' WHEN 'CIBIL' THEN 'CUSTOMER' WHEN 'LVI' THEN 'LEGAL VERIFICATION' "
			+ " WHEN 'TVI' THEN 'TECHNICAL VERIFICATION' WHEN 'RVI' THEN 'RCU VERIFICATION' "
			+ " WHEN 'FVI' THEN 'FIELD VERIFICATION' WHEN 'FVILM' THEN 'FIELD VERIFICATION(CREDIT MANAGEMENT)' "
			+ " WHEN 'RVILM' THEN 'RCU VERIFICATION(CREDIT MANAGEMENT)' "
			+ " WHEN 'TVILM' THEN 'TECHNICAL VERIFICATION(CREDIT MANAGEMENT)' WHEN 'LVILM' THEN 'LEGAL VERIFICATION(CREDIT MANAGEMENT)' "
			+ " ELSE (CASE WHEN GM.DESCRIPTION IS NULL THEN 'NA' ELSE GM.DESCRIPTION END) END AS DOC_TYPE "
			+ " ,CASE WHEN A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL') THEN IFNULL(CDCM.CUSTOMER_NAME,'') "
			+ " WHEN A.DOC_TYPE IN('APPL','',NULL) THEN 'APPLICATION' "
			+ " WHEN A.DOC_TYPE='LVI' THEN 'LEGAL VERIFICATION(CP)'"
			+ " WHEN A.DOC_TYPE='TVI' THEN 'TECHNICAL VERIFICATION(CP)'"
			+ " WHEN A.DOC_TYPE='RVI' THEN 'RCU VERIFICATION(CP)'"
			+ " WHEN A.DOC_TYPE='FVI' THEN 'FIELD VERIFICATION(CP)'"
			+ " WHEN A.DOC_TYPE='LVILM' THEN 'LEGAL VERIFICATION(CM)'"
			+ " WHEN A.DOC_TYPE='TVILM' THEN 'TECHNICAL VERIFICATION(CM)'"
			+ " WHEN A.DOC_TYPE='RVILM' THEN 'RCU VERIFICATION(CM)'"
			+ " WHEN A.DOC_TYPE='FVILM' THEN 'FIELD VERIFICATION(CM)'"
			+ " WHEN A.DOC_TYPE='CROP' THEN 'CROP'"
			+ " WHEN A.DOC_TYPE='N' THEN 'NA'"
			+ " ELSE "
			+ " 	CASE IFNULL(GM.PARENT_VALUE,'N')"
			+ " 	WHEN GM.PARENT_VALUE='OVI' THEN 'OFFICE VERIFICATION'"
			+ " 	WHEN GM.PARENT_VALUE='RVI' THEN 'CURRENT ADDRESS VERIFICATION'"
			+ " 	WHEN GM.PARENT_VALUE='TVI' THEN 'TECHNICAL VERIFICATION'"
			+ " 	ELSE 'NA'"
			+ " 	END"
			+ " END ENTITY_NAME,"
			+ " IFNULL(CDM.DOC_DESC,'')DOCUMENT_TYPE,IFNULL(child.DOC_DESC,'')DOCUMENT_NAME,IFNULL(DMS_ID,0),DMS_DOC_URL,"
			+ " A.DOCUMENT_ID,A.DMS_DOC_NUMBER,DATE_FORMAT(A.UPLOADED_DATE,'"+dateFormatWithTime+"')UPLOADED_DATE,1 CAT "
			+ " FROM CR_UPLOADED_DOCUMENTS A "
			+ " JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID AND IFNULL(A.DOC_TYPE,'') NOT IN('PD')"
			+ " LEFT JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE"
			+ " LEFT JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=A.ENTITY_ID AND A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL')) "
			+ " LEFT JOIN CR_DOCUMENT_M CDM ON (CDM.DOC_ID=A.DOC_ID) "
			+ " LEFT JOIN CR_DOCUMENT_CHILD_M CHILD ON (CHILD.DOC_CHILD_ID=A.CHILD_DOC_ID)"
			+ " LEFT JOIN generic_master GM ON(GM.generic_key='IMAGE_UPLOAD_TYPE' AND GM.VALUE=A.DOC_Type)"
			+ " where txn_id='"+dealId+"' and TXN_TYPE='DC' and A.DOCUMENT_STATUS='A'"
			+ " UNION "
			+ " SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC,B.USER_NAME,S.STAGE_DESC,'PERSONAL DISCUSSION'DOC_Type,"
			+ " CDCM.CUSTOMER_NAME ENTITY_NAME,IFNULL(CDM.DOC_NAME,'')DOCUMENT_TYPE,''DOCUMENT_NAME,IFNULL(DMS_ID,0)DMS_ID,"
			+ " DMS_DOC_URL,A.DOCUMENT_ID,A.DMS_DOC_NUMBER,DATE_FORMAT(A.UPLOADED_DATE,'"+dateFormatWithTime+"')UPLOADED_DATE,2 CAT"
			+ " FROM CR_UPLOADED_DOCUMENTS A "
			+ " JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID AND A.DOC_TYPE='PD'"
			+ " LEFT JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE "
			+ " join cr_pd_documents_dtl pd on(pd.id=A.ENTITY_ID)"
			+ " join cr_pd_customer_dtl pdcust on(pdcust.PD_CUSTOMER_ID=pd.PD_CUSTOMER_ID)"
			+ " JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=pdcust.GCD_ID) "
			+ " LEFT JOIN cr_pd_document_m CDM ON (CDM.DOC_ID=pd.DOCUMENTS_ID) "
			+ " where A.txn_id='"+dealId+"' and TXN_TYPE='DC' and A.DOCUMENT_STATUS='A'"
			+ " ORDER BY CAT,DOC_TYPE,ENTITY_NAME,UPLOADED_DATE");
		
		logger.info("In getUploadUnderwritingData Document List Query : "+query);
		UnderwritingDocUploadVo uwDocVo = null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());
		//logger.info("getUploadUnderwritingData size of list Product "+product.size());
		query=null;
		for(int i=0;i<product.size();i++){
			
			 data=(ArrayList)product.get(i);
			if(data.size()>0)	{
				uwDocVo=new UnderwritingDocUploadVo();
				uwDocVo.setDealId((CommonFunction.checkNull(data.get(0))).trim());
				uwDocVo.setFileName(CommonFunction.checkNull(data.get(1)).trim());
				uwDocVo.setDocDescription((CommonFunction.checkNull(data.get(2))).trim());
				uwDocVo.setUserName((CommonFunction.checkNull(data.get(3))).trim());
				uwDocVo.setUploadedState((CommonFunction.checkNull(data.get(4))).trim());
				uwDocVo.setDocTypeDesc((CommonFunction.checkNull(data.get(5))).trim());
				uwDocVo.setEntityName((CommonFunction.checkNull(data.get(6))).trim());
				uwDocVo.setDocumentType((CommonFunction.checkNull(data.get(7))).trim());
				uwDocVo.setDocumentName((CommonFunction.checkNull(data.get(8))).trim());
				uwDocVo.setDmsDocId((CommonFunction.checkNull(data.get(9))).trim());
				uwDocVo.setDmsDocUrl((CommonFunction.checkNull(data.get(10))).trim());
				uwDocVo.setLbxDocId((CommonFunction.checkNull(data.get(11))).trim());
				uwDocVo.setDmsDocNumber((CommonFunction.checkNull(data.get(12))).trim());
				uwDocVo.setUploadedDate((CommonFunction.checkNull(data.get(13))).trim());
				list.add(uwDocVo);
				uwDocVo=null;
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		
		data=null;
		
	}

	return list;
}
public ArrayList getDealHeaderForCm(String id)
{
	ArrayList list=new ArrayList();
	try
	{
		StringBuilder query=new StringBuilder();
		query.append(" select distinct cld.LOAN_ID,cld.LOAN_NO,cld.LOAN_REFERENCE_NO,gcd.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,cld.LOAN_PRODUCT_CATEGORY,DATE_FORMAT(cld.LOAN_INITIATION_DATE,'"+dateFormat+"') from cr_loan_dtl cld "+
			    "left join cr_product_m p on cld.loan_product=p.PRODUCT_ID  left join gcd_customer_m gcd on gcd.CUSTOMER_ID=cld.loan_CUSTOMER_ID"+
				" left join cr_scheme_m s on cld.loan_SCHEME=s.SCHEME_ID "+
				" where cld.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+" limit 1");			
		logger.info("getDealHeader Query: "+query);
		
		HeaderInfoVo vo= null;
		ArrayList header = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		for(int i=0;i<header.size();i++){
			
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				vo = new HeaderInfoVo();
				vo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
				vo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
				//vo.setDealCustomerId((CommonFunction.checkNull(header1.get(2))).trim());
				vo.setDealCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
				//vo.setDealDate((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setDealProduct((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setDealScheme((CommonFunction.checkNull(header1.get(5))).trim());
				vo.setDealProductCat((CommonFunction.checkNull(header1.get(6))).trim());
				vo.setDealDate((CommonFunction.checkNull(header1.get(7))).trim());
				list.add(vo);
				vo=null;
			}
			header1=null;
		}
		header=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return list;
}
public ArrayList getUploadUnderwritingDataForCm(String dealId)
{
	ArrayList list=new ArrayList();
	ArrayList data=null;
	try{
		StringBuilder query=new StringBuilder(""
				+ " SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,S.STAGE_DESC,"
				+ " case A.DOC_Type when 'APPL' THEN 'APPLICATION'  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO APPLICANT' "
				+ " WHEN 'GUARANTOR' THEN 'GUARANTOR' WHEN 'ASSET' THEN 'ASSET' "
				+ " WHEN 'COLLATERAL' THEN 'COLLATERAL' WHEN 'CIBIL' THEN 'CUSTOMER' WHEN 'LVI' THEN 'LEGAL VERIFICATION' "
				+ " WHEN 'TVI' THEN 'TECHNICAL VERIFICATION' WHEN 'RVI' THEN 'RCU VERIFICATION' "
				+ " WHEN 'FVI' THEN 'FIELD VERIFICATION' WHEN 'FVILM' THEN 'FIELD VERIFICATION(CREDIT MANAGEMENT)' "
				+ " WHEN 'RVILM' THEN 'RCU VERIFICATION(CREDIT MANAGEMENT)' "
				+ " WHEN 'TVILM' THEN 'TECHNICAL VERIFICATION(CREDIT MANAGEMENT)' WHEN 'LVILM' THEN 'LEGAL VERIFICATION(CREDIT MANAGEMENT)' "
				+ " ELSE (CASE WHEN GM.DESCRIPTION IS NULL THEN 'NA' ELSE GM.DESCRIPTION END) END AS DOC_TYPE "
						+ " ,CASE WHEN A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL') THEN IFNULL(CDCM.CUSTOMER_NAME,'') "
				+ " WHEN A.DOC_TYPE IN('APPL','',NULL) THEN 'APPLICATION' "
				+ " WHEN A.DOC_TYPE='LVI' THEN 'LEGAL VERIFICATION(CP)'"
				+ " WHEN A.DOC_TYPE='TVI' THEN 'TECHNICAL VERIFICATION(CP)'"
				+ " WHEN A.DOC_TYPE='RVI' THEN 'RCU VERIFICATION(CP)'"
				+ " WHEN A.DOC_TYPE='FVI' THEN 'FIELD VERIFICATION(CP)'"
				+ " WHEN A.DOC_TYPE='LVILM' THEN 'LEGAL VERIFICATION(CM)'"
				+ " WHEN A.DOC_TYPE='TVILM' THEN 'TECHNICAL VERIFICATION(CM)'"
				+ " WHEN A.DOC_TYPE='RVILM' THEN 'RCU VERIFICATION(CM)'"
				+ " WHEN A.DOC_TYPE='FVILM' THEN 'FIELD VERIFICATION(CM)'"
				+ " WHEN A.DOC_TYPE='CROP' THEN 'CROP'"
				+ " WHEN A.DOC_TYPE='N' THEN 'NA'"
				+ " ELSE "
				+ " 	CASE IFNULL(GM.PARENT_VALUE,'N')"
				+ " 	WHEN GM.PARENT_VALUE='OVI' THEN 'OFFICE VERIFICATION'"
				+ " 	WHEN GM.PARENT_VALUE='RVI' THEN 'CURRENT ADDRESS VERIFICATION'"
				+ " 	WHEN GM.PARENT_VALUE='TVI' THEN 'TECHNICAL VERIFICATION'"
				+ " 	ELSE 'NA'"
				+ " 	END"
				+ " END ENTITY_NAME,"
				+ " IFNULL(CDM.DOC_DESC,'')DOCUMENT_TYPE,IFNULL(child.DOC_DESC,'')DOCUMENT_NAME,IFNULL(DMS_ID,0),DMS_DOC_URL,"
				+ " A.DOCUMENT_ID,A.DMS_DOC_NUMBER,DATE_FORMAT(A.UPLOADED_DATE,'"+dateFormatWithTime+"')UPLOADED_DATE,A.TXN_TYPE,1 CAT "
				+ " FROM CR_UPLOADED_DOCUMENTS A "
				+ " JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID AND IFNULL(A.DOC_TYPE,'') NOT IN('PD') "
				+ " LEFT JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE "
				+ " LEFT JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=A.ENTITY_ID AND A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL')) "
				+ " LEFT JOIN CR_DOCUMENT_M CDM ON (CDM.DOC_ID=A.DOC_ID) "
				+ " LEFT JOIN CR_DOCUMENT_CHILD_M CHILD ON (CHILD.DOC_CHILD_ID=A.CHILD_DOC_ID)"
				+ " LEFT JOIN generic_master GM ON(GM.generic_key='IMAGE_UPLOAD_TYPE' AND GM.VALUE=A.DOC_Type)"
				+ " where txn_id='"+dealId+"' and TXN_TYPE='LIM' ");
		logger.info("In getUploadUnderwritingDataForCm...............query...........DAOImpl"+query);
		UnderwritingDocUploadVo uwDocVo = null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());
		//logger.info("getUploadUnderwritingData size of list Product "+product.size());
		query=null;
		for(int i=0;i<product.size();i++){
			
			data=(ArrayList)product.get(i);
			if(data.size()>0)	{
				uwDocVo=new UnderwritingDocUploadVo();
				uwDocVo.setTxnId((CommonFunction.checkNull(data.get(0))).trim());
				uwDocVo.setFileName(CommonFunction.checkNull(data.get(1)).trim());
				uwDocVo.setDocDescription((CommonFunction.checkNull(data.get(2))).trim());
				uwDocVo.setUserName((CommonFunction.checkNull(data.get(3))).trim());
				uwDocVo.setUploadedState((CommonFunction.checkNull(data.get(4))).trim());
				uwDocVo.setDocTypeDesc((CommonFunction.checkNull(data.get(5))).trim());
				uwDocVo.setEntityName((CommonFunction.checkNull(data.get(6))).trim());
				uwDocVo.setDocumentType((CommonFunction.checkNull(data.get(7))).trim());
				uwDocVo.setDocumentName((CommonFunction.checkNull(data.get(8))).trim());
				uwDocVo.setDmsDocId((CommonFunction.checkNull(data.get(9))).trim());
				uwDocVo.setDmsDocUrl((CommonFunction.checkNull(data.get(10))).trim());
				uwDocVo.setLbxDocId((CommonFunction.checkNull(data.get(11))).trim());
				uwDocVo.setDmsDocNumber((CommonFunction.checkNull(data.get(12))).trim());
				uwDocVo.setUploadedDate((CommonFunction.checkNull(data.get(13))).trim());
				uwDocVo.setTxnType(CommonFunction.checkNull(data.get(14)).trim());
				list.add(uwDocVo);
				uwDocVo=null;
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		data=null;

	}

	return list;
}
public ArrayList getUploadUnderwritingDataForCmCp(String dealId)
{
	ArrayList list=new ArrayList();
	ArrayList data=null;
	try{
		String deaId = ConnectionDAO.singleReturn("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+dealId+"'");
		StringBuilder query=new StringBuilder();
		/*query.append("SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,S.STAGE_DESC,A.TXN_TYPE, "
			+ " case A.DOC_Type when 'APPL' THEN 'APPLICATION'  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' WHEN 'CROP' THEN 'CROP' ELSE '' END AS DOC_TYPE "
			+ " ,IFNULL(CDCM.CUSTOMER_NAME,'')ENTITY_NAME,IFNULL(CDM.DOC_DESC,'')DOCUMENT_TYPE,IFNULL(CHILD.DOC_DESC,'')DOCUMENT_NAME,IFNULL(DMS_ID,0),DMS_DOC_URL,A.DOCUMENT_ID "
			+ " FROM CR_UPLOADED_DOCUMENTS A "
			+ "	JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE "
			+ " LEFT JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=A.ENTITY_ID) "
			+ " LEFT JOIN CR_DOCUMENT_M CDM ON (CDM.DOC_ID=A.DOC_ID) "
			+ " LEFT JOIN CR_DOCUMENT_CHILD_M CHILD ON (CHILD.DOC_CHILD_ID=A.CHILD_DOC_ID) "
			+ "where txn_id='"+deaId+"' and TXN_TYPE='DC' and A.DOCUMENT_STATUS='A'");*/
		query.append("SELECT A.TXN_ID, A.FILE_NAME, A.DOCUMENT_DESC, B.USER_NAME,S.STAGE_DESC,A.TXN_TYPE,");
		query.append(" case A.DOC_Type when 'APPL' THEN 'APPLICATION'  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO APPLICANT'"); 
		query.append(" WHEN 'GUARANTOR' THEN 'GUARANTOR' WHEN 'ASSET' THEN 'ASSET' ");
		query.append(" WHEN 'COLLATERAL' THEN 'COLLATERAL' WHEN 'CIBIL' THEN 'CUSTOMER' WHEN 'LVI' THEN 'LEGAL VERIFICATION'"); 
		query.append(" WHEN 'TVI' THEN 'TECHNICAL VERIFICATION' WHEN 'RVI' THEN 'RCU VERIFICATION' ");
		query.append(" WHEN 'FVI' THEN 'FIELD VERIFICATION' WHEN 'FVILM' THEN 'FIELD VERIFICATION(CREDIT MANAGEMENT)' ");
		query.append(" WHEN 'RVILM' THEN 'RCU VERIFICATION(CREDIT MANAGEMENT)' ");
		query.append(" WHEN 'TVILM' THEN 'TECHNICAL VERIFICATION(CREDIT MANAGEMENT)' WHEN 'LVILM' THEN 'LEGAL VERIFICATION(CREDIT MANAGEMENT)'"); 
		query.append(" ELSE (CASE WHEN GM.DESCRIPTION IS NULL THEN 'NA' ELSE GM.DESCRIPTION END) END AS DOC_TYPE ");
		query.append(" ,CASE WHEN A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL') THEN IFNULL(CDCM.CUSTOMER_NAME,'') ");
		query.append(" WHEN A.DOC_TYPE IN('APPL','',NULL) THEN 'APPLICATION' ");
		query.append(" WHEN A.DOC_TYPE='LVI' THEN 'LEGAL VERIFICATION(CP)'");
		query.append(" WHEN A.DOC_TYPE='TVI' THEN 'TECHNICAL VERIFICATION(CP)'");
		query.append(" WHEN A.DOC_TYPE='RVI' THEN 'RCU VERIFICATION(CP)'");
		query.append(" WHEN A.DOC_TYPE='FVI' THEN 'FIELD VERIFICATION(CP)'");
		query.append(" WHEN A.DOC_TYPE='LVILM' THEN 'LEGAL VERIFICATION(CM)'");
		query.append(" WHEN A.DOC_TYPE='TVILM' THEN 'TECHNICAL VERIFICATION(CM)'");
		query.append(" WHEN A.DOC_TYPE='RVILM' THEN 'RCU VERIFICATION(CM)'");
		query.append(" WHEN A.DOC_TYPE='FVILM' THEN 'FIELD VERIFICATION(CM)'");
		query.append(" WHEN A.DOC_TYPE='CROP' THEN 'CROP'");
		query.append(" WHEN A.DOC_TYPE='N' THEN 'NA'");
		query.append(" ELSE ");
		query.append(" CASE IFNULL(GM.PARENT_VALUE,'N')");
		query.append(" WHEN GM.PARENT_VALUE='OVI' THEN 'OFFICE VERIFICATION'");
		query.append(" WHEN GM.PARENT_VALUE='RVI' THEN 'CURRENT ADDRESS VERIFICATION'");
		query.append(" WHEN GM.PARENT_VALUE='TVI' THEN 'TECHNICAL VERIFICATION'");
		query.append("ELSE 'NA'");
		query.append("  END");
		query.append(" END ENTITY_NAME,");
		query.append("  IFNULL(CDM.DOC_DESC,'')DOCUMENT_TYPE,IFNULL(child.DOC_DESC,'')DOCUMENT_NAME,IFNULL(DMS_ID,0),DMS_DOC_URL,");
		query.append(" A.DOCUMENT_ID,A.DMS_DOC_NUMBER,DATE_FORMAT(A.UPLOADED_DATE,'"+dateFormatWithTime+"')UPLOADED_DATE,1 CAT ");
		query.append(" FROM CR_UPLOADED_DOCUMENTS A ");
		query.append(" JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID AND IFNULL(A.DOC_TYPE,'') NOT IN('PD')");
		query.append(" LEFT JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE");
		query.append(" LEFT JOIN CR_DEAL_CUSTOMER_M CDCM ON(CDCM.CUSTOMER_ID=A.ENTITY_ID AND A.DOC_TYPE IN('PRAPPL','COAPPL','GUARANTOR','CIBIL'))"); 
		query.append(" LEFT JOIN CR_DOCUMENT_M CDM ON (CDM.DOC_ID=A.DOC_ID) ");
		query.append(" LEFT JOIN CR_DOCUMENT_CHILD_M CHILD ON (CHILD.DOC_CHILD_ID=A.CHILD_DOC_ID)");
		query.append(" LEFT JOIN generic_master GM ON(GM.generic_key='IMAGE_UPLOAD_TYPE' AND GM.VALUE=A.DOC_Type)");
		query.append(" where txn_id='"+deaId+"' and TXN_TYPE='DC' ");
	
		logger.info("In getUploadUnderwritingDataForCmCp...............query...........DAOImpl"+query);
		UnderwritingDocUploadVo uwDocVo = null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());
		query=null;
		for(int i=0;i<product.size();i++){
		
			data=(ArrayList)product.get(i);
			if(data.size()>0)	{
				uwDocVo=new UnderwritingDocUploadVo();
				uwDocVo.setTxnId((CommonFunction.checkNull(data.get(0))).trim());
				uwDocVo.setFileName(CommonFunction.checkNull(data.get(1)).trim());
				uwDocVo.setDocDescription((CommonFunction.checkNull(data.get(2))).trim());
				uwDocVo.setUserName((CommonFunction.checkNull(data.get(3))).trim());
				uwDocVo.setUploadedState((CommonFunction.checkNull(data.get(4))).trim());
				uwDocVo.setTxnType((CommonFunction.checkNull(data.get(5))).trim());
				uwDocVo.setDocTypeDesc((CommonFunction.checkNull(data.get(6))).trim());
				uwDocVo.setEntityName((CommonFunction.checkNull(data.get(7))).trim());
				uwDocVo.setDocumentType((CommonFunction.checkNull(data.get(8))).trim());
				uwDocVo.setDocumentName((CommonFunction.checkNull(data.get(9))).trim());
				uwDocVo.setDmsDocId((CommonFunction.checkNull(data.get(10))).trim());
				uwDocVo.setDmsDocUrl((CommonFunction.checkNull(data.get(11))).trim());
				uwDocVo.setLbxDocId((CommonFunction.checkNull(data.get(12))).trim());
				uwDocVo.setDmsDocNumber((CommonFunction.checkNull(data.get(13))).trim());
				uwDocVo.setUploadedDate((CommonFunction.checkNull(data.get(14))).trim());
				list.add(uwDocVo);
				uwDocVo=null;
			}
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
	
		
		data=null;
	}
	return list;
}
}
 


