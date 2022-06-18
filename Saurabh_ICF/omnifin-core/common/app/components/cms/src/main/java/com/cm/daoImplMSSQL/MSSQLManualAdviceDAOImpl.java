package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.ManualAdviceDAO;
import com.cm.vo.ManualAdviceCreationVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.logger.LoggerMsg;

public class MSSQLManualAdviceDAOImpl implements ManualAdviceDAO {
	
	private static final Logger logger = Logger.getLogger(MSSQLManualAdviceDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	
	
	public ArrayList<ManualAdviceCreationVo> ManualAdviceMakerSearchDetail(ManualAdviceCreationVo vo,String author)
	{
		logger.info("In  ManualAdviceMakerSearchDetail(): ");
		StringBuilder loanNo = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder chargeCode = new StringBuilder();
		StringBuilder chargeAmount = new StringBuilder();


		String recStatus="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

			ArrayList<ManualAdviceCreationVo> detailListGrid=new ArrayList<ManualAdviceCreationVo>();
			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());

		  try{
			  ArrayList searchlist=new ArrayList();
		    logger.info("In ManualAdviceMakerSearchDetail....................");
		    StringBuffer bufInsSql = new StringBuffer();
		    StringBuffer bufTempCount= new StringBuffer();
		    loanNo.append(CommonFunction.checkNull((vo.getLbxLoanNoHID())));
		     businessPartnerType.append(CommonFunction.checkNull(StringEscapeUtils.escapeHtml(vo.getLbxBusinessPartnearHID()).trim()));
		  
		    chargeCode.append(CommonFunction.checkNull(StringEscapeUtils.escapeHtml(vo.getLbxCharge()).trim()));
		    chargeAmount.append(CommonFunction.checkNull(StringEscapeUtils.escapeHtml(vo.getChargeAmount()).trim()));

		    if(author!=null && author.equalsIgnoreCase("manualApprove"))
		    {
		    	recStatus="F";
		    }
		    else
		    {
		    	recStatus="P";
		    }  
		      bufInsSql.append("SELECT CLD.LOAN_ID,CLD.LOAN_NO,(SELECT distinct TOP 1 CUSTOMER_NAME ");
			  bufInsSql.append(" FROM gcd_customer_m WHERE CLD.LOAN_CUSTOMER_ID=CUSTOMER_ID ");
			  
			 
			  bufInsSql.append(")CUSTOMER_NAME, man.BPTYPE,(select distinct TOP 1 ");
			  bufInsSql.append("DESCRIPTION from generic_master where GENERIC_KEY='BPTYPE' AND VALUE=man.BPTYPE )BP_TYPE_DESC," +
				  		" man.BPID,(SELECT distinct TOP 1");
			  bufInsSql.append(" BP_NAME FROM business_partner_view WHERE  BP_ID=man.BPID and BP_TYPE=man.BPTYPE )BP_NAME," +
				  		" man.MANUAL_ADVICE_ID,ch.CHARGE_CODE,man.ORG_CHARGE_AMOUNT,ch.CHARGE_DESC, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=MAN.MAKER_ID) MAKER_ID" +
				  		" from cr_manual_advice_dtl man,COM_CHARGE_CODE_M ch,cr_loan_dtl CLD" +
				  		" where CLD.LOAN_ID=man.TXN_ID" +
				  		" AND man.CHARGE_CODE=ch.CHARGE_CODE" +
				  		" and man.REC_STATUS='"+recStatus+"' AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' "); 	
			  
			  bufTempCount.append("select count(1) from cr_manual_advice_dtl man ");
//			  if (!CommonFunction.checkNull(chargeCode).equals("")){
//			       bufTempCount.append("INNER JOIN COM_CHARGE_CODE_M ch ON man.CHARGE_CODE=ch.CHARGE_CODE " );
//			  }
			  bufTempCount.append("INNER JOIN cr_loan_dtl CLD ON CLD.LOAN_ID=man.TXN_ID AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' " );
	
					  
					  if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase(""))
						{
						  bufInsSql.append(" AND MAN.MAKER_ID='"+vo.getReportingToUserId()+"'");
						  bufTempCount.append(" AND MAN.MAKER_ID='"+vo.getReportingToUserId()+"'");
						}
					  else if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("manualApprove"))
						{
						  bufInsSql.append(" AND MAN.MAKER_ID!='"+vo.getUserId()+"'");
						  bufTempCount.append(" AND MAN.MAKER_ID!='"+vo.getUserId()+"'");
						}
					  bufTempCount.append(" where man.REC_STATUS='"+recStatus+"'  " );

			  
					  if (!loanNo.toString().equals("")) {
				        	bufInsSql.append(" AND man.TXN_ID= '"+loanNo+"' ");
				        	bufTempCount.append(" AND man.TXN_ID= '"+loanNo+"' ");
				      }
					  if (!businessPartnerType.toString().equals("")) {
			        	bufInsSql.append("AND man.BPID= '"+businessPartnerType+"'");
			        	bufTempCount.append("AND man.BPID= '"+businessPartnerType+"'");
			          }
					  if((!(CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))&& vo.getStage().equalsIgnoreCase("manualApprove"))) {
						  	bufInsSql.append(" AND MAN.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");	
						  	bufTempCount.append(" AND MAN.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
					      }

		    
		          else if (!chargeCode.toString().equals("")) {
		        	bufInsSql.append(" AND man.CHARGE_CODE ='"+chargeCode+"' ");
		        	bufTempCount.append(" AND man.CHARGE_CODE ='"+chargeCode+"' ");
		          }

		          else if (!chargeAmount.toString().equals("")) {
		        	bufInsSql.append(" AND man.ORG_CHARGE_AMOUNT = '"+chargeAmount+"' ");
		        	bufTempCount.append(" AND man.ORG_CHARGE_AMOUNT = '"+chargeAmount+"' ");
		          }
					  
					logger.info("Manual advice search query : " + bufInsSql.toString());  
					logger.info("Manual advice temp search query : " + bufTempCount.toString()); 
					count=Integer.parseInt(ConnectionDAO.singleReturn(bufTempCount.toString()));  
					
					
					  if(((loanNo.toString()).trim()==null && (businessPartnerType.toString()).trim()==null && chargeCode.toString()==null && (chargeAmount.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (businessPartnerType.toString()).trim().equalsIgnoreCase("") && (chargeCode.toString()).trim().equalsIgnoreCase("") && (chargeAmount.toString()).trim().equalsIgnoreCase(""))|| vo.getCurrentPageLink()>1)

						{
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}
		
						bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
			            bufInsSql.append(startRecordIndex);
			            bufInsSql.append(" ROWS FETCH next ");
			            bufInsSql.append(endRecordIndex);
			            bufInsSql.append(" ROWS ONLY "); 
						
						}
					    logger.info("ManualAdviceMakerSearchDetail query    ........"+bufInsSql.toString());
		    searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		    logger.info("ManualAdviceMakerSearchDetail search Data size is...."+searchlist.size());
		    
		    for(int i=0;i<searchlist.size();i++){
		    logger.info("ManualAdviceMakerSearchDetail search List "+searchlist.get(i).toString());
		    ArrayList data=(ArrayList)searchlist.get(i);

		    if(data.size()>0){
		    	ManualAdviceCreationVo ManualVO = new ManualAdviceCreationVo();
		  	  	logger.info("bp type 1111 ........................................... "+data.get(1));
		  	  logger.info("bp type 222......................................... "+data.get(2));
		  	logger.info("bp type 333........................................... "+data.get(3));
		    	ManualVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
		    	ManualVO.setLoanNo("<a href=\"#\" onclick=\"editMaualAdvice('"+((CommonFunction.checkNull(data.get(0)).trim()))+"','"+((CommonFunction.checkNull(data.get(7)).trim()))+"');\">" +
						 ((CommonFunction.checkNull(data.get(1)).trim()))+"</a>");

		  	  logger.info("setCustomerName:-------------"+data.get(2));
		  	ManualVO.setCustomersName((CommonFunction.checkNull(data.get(2)).trim()));
		  	ManualVO.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(3)).trim()));
		  	
		  	ManualVO.setBpTypeDesc((CommonFunction.checkNull(data.get(4)).trim()));
		  	logger.info("setBpTypeDesc........................................... "+data.get(4));
		  	ManualVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	
		  	ManualVO.setBpNameDesc((CommonFunction.checkNull(data.get(6)).trim()));
		  	
		  	ManualVO.setManaulId((CommonFunction.checkNull(data.get(7)).trim()));
		  	ManualVO.setChargeCodeDesc((CommonFunction.checkNull(data.get(8)).trim()));
		  	
		  	if(!CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase(""))
			{
				Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(9))).trim());  
				ManualVO.setChargeAmount(myFormatter.format(reconNum));
			}
		
		  	
		  	ManualVO.setChargeCodeDesc((CommonFunction.checkNull(data.get(10)).trim()));
		  	ManualVO.setTotalRecordSize(count);
		  	ManualVO.setReportingToUserId((CommonFunction.checkNull(data.get(11)).trim()));
		  	
		  	  detailListGrid.add(ManualVO);	
		     }

		      }

		}
		  catch(Exception e)
		  {
			e.printStackTrace();

		  }
		  finally
		  {
			  loanNo = null;
			  businessPartnerType = null;
			  chargeCode = null;
			  chargeAmount = null; 
		  }


		logger.info("detailListGrid in DAO IMPl "+detailListGrid.size());
		return  detailListGrid;	

	}
	
	public ArrayList<ManualAdviceCreationVo> getManualAdviceCreationMakerDetail(String id)
	{
		logger.info("In  getManualAdviceCreationMakerDetail(): ");
		ArrayList<ManualAdviceCreationVo> manualAdvicelist=new ArrayList<ManualAdviceCreationVo>();
		StringBuilder query = new StringBuilder();
		logger.info("id in getManualAdviceCreationMakerDetail............................................."+id);
		try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  
			  query.append("SELECT m.TXN_ID,(SELECT CUSTOMER_NAME FROM gcd_customer_m WHERE  CLD.LOAN_CUSTOMER_ID=CUSTOMER_ID)CUSTOMER_Name," +
			  		"m.BPTYPE,m.BPID,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(m.MANUAL_ADVICE_DATE,'"+dateFormat+"'),m.CHARGE_CODE,m.ORG_CHARGE_AMOUNT,m.ORG_TAX_AMOUNT1," +
			  		"m.ORG_TAX_AMOUNT2,m.ORG_TDS_AMOUNT,m.ADVICE_AMOUNT,m.TAX_RATE1,m.TAX_RATE2,m.TDS_RATE,ADVICE_TYPE,CASE WHEN m.TDS_APPLICABLE='Y' THEN 'YES' ELSE 'NO' END AS TDS_APPLICABLE ," +
			  		"CASE WHEN m.TAX_APPLICABLE='Y' THEN 'YES' ELSE 'NO' END AS TAX_APPLICABLE,CLD.LOAN_NO,gm.DESCRIPTION,bp.BP_NAME,ch.CHARGE_DESC,MANUAL_ADVICE_ID,REMARKS,CASE WHEN TAX_INCLUSIVE='Y' THEN 'YES' ELSE 'NO' END AS TAX_INCLUSIVE,MAKER_REMARKS,CLD.LOAN_PRODUCT,CLD.LOAN_SCHEME,CLD.REC_STATUS FROM cr_manual_advice_dtl m," +
			  		"cr_loan_dtl CLD,generic_master gm,business_partner_view bp,COM_CHARGE_CODE_M ch " +
			  		"WHERE  CLD.LOAN_ID=m.TXN_ID" +
			  		" and gm.VALUE=m.BPTYPE and bp.BP_ID=m.BPID and ch.CHARGE_CODE=m.CHARGE_CODE and MANUAL_ADVICE_ID="+id);
			  logger.info("queryn action ............................. "+query);
			mainlist=ConnectionDAO.sqlSelect(query.toString());


									
					for(int i=0;i<mainlist.size();i++){
				
				subList=(ArrayList)mainlist.get(i);
				
				if(subList.size()>0){
									
					ManualAdviceCreationVo manualAdviceCreationVo=new ManualAdviceCreationVo();
				
					manualAdviceCreationVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
					manualAdviceCreationVo.setCustomersName((CommonFunction.checkNull(subList.get(1)).trim()));
					
					manualAdviceCreationVo.setBusinessPartnerType((CommonFunction.checkNull(subList.get(2)).trim()));
					manualAdviceCreationVo.setLbxBusinessPartnearHID((CommonFunction.checkNull(subList.get(3)).trim()));
					manualAdviceCreationVo.setValueDate((CommonFunction.checkNull(subList.get(4)).trim()));
					manualAdviceCreationVo.setLbxCharge((CommonFunction.checkNull(subList.get(5)).trim()));
					
					
					  if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
							manualAdviceCreationVo.setChargeAmount(myFormatter.format(PremiumAmnt));
						}
					
					//manualAdviceCreationVo.setChargeAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());
							manualAdviceCreationVo.setTaxAmount1(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTaxAmount1(myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
							manualAdviceCreationVo.setTaxAmount2(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTaxAmount2(myFormatter.parse((CommonFunction.checkNull(subList.get(8)).trim())).toString());
					  
					  if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());
							manualAdviceCreationVo.setTdsAmount(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTdsAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(9)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
							manualAdviceCreationVo.setAdviceAmount(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setAdviceAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
							manualAdviceCreationVo.setTaxRate1(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTaxRate1(myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(12))).trim());
							manualAdviceCreationVo.setTaxRate2(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTaxRate2(myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim())).toString());
					  if(!CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(subList.get(13))).trim());
							manualAdviceCreationVo.setTdsRate(myFormatter.format(PremiumAmnt));
						}
					//manualAdviceCreationVo.setTdsRate(myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim())).toString());
					manualAdviceCreationVo.setAdviceType((CommonFunction.checkNull(subList.get(14)).trim()));
					manualAdviceCreationVo.setTdsApplicable((CommonFunction.checkNull(subList.get(15)).trim()));
					manualAdviceCreationVo.setTaxApplicable((CommonFunction.checkNull(subList.get(16)).trim()));
					
					manualAdviceCreationVo.setLoanNo((CommonFunction.checkNull(subList.get(17)).trim()));
					manualAdviceCreationVo.setBpTypeDesc((CommonFunction.checkNull(subList.get(18)).trim()));
					manualAdviceCreationVo.setBpNameDesc((CommonFunction.checkNull(subList.get(19)).trim()));
					manualAdviceCreationVo.setChargeCodeDesc((CommonFunction.checkNull(subList.get(20)).trim()));
					manualAdviceCreationVo.setManaulId((CommonFunction.checkNull(subList.get(21)).trim()));
					//logger.info("remarks ............................................"+subList.get(22).toString());
					manualAdviceCreationVo.setAuthorRemarks((CommonFunction.checkNull(subList.get(22)).trim()));
					manualAdviceCreationVo.setTaxInclusive((CommonFunction.checkNull(subList.get(23)).trim()));
					manualAdviceCreationVo.setRemarks((CommonFunction.checkNull(subList.get(24)).trim()));
					manualAdviceCreationVo.setLbxProductId((CommonFunction.checkNull(subList.get(25)).trim()));
					manualAdviceCreationVo.setLbxSchemeId((CommonFunction.checkNull(subList.get(26)).trim()));
					
					manualAdviceCreationVo.setLoanRecStatus((CommonFunction.checkNull(subList.get(27)).trim()));
					
					manualAdvicelist.add(manualAdviceCreationVo);

			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
			}
			logger.info("manualAdvicelist.size............................................."+manualAdvicelist.size());
			return manualAdvicelist;

	}
	public String manualAdviceAuthorDecision(Object ob)
	{
		logger.info("In  manualAdviceAuthorDecision(): ");
		String status = "";
		ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			StringBuilder date = new StringBuilder();
			StringBuilder s1 = new StringBuilder();
			StringBuilder s2 = new StringBuilder();
//			 String s1="";
//			 String s2="";
		//CallableStatement cst = null;
		int statusProc = 0;
		//Connection con=null;
		int manaulId =0;
		ManualAdviceCreationVo vo = (ManualAdviceCreationVo) ob;
		//con = ConnectionDAO.getConnection();
		try {

//			con.setAutoCommit(false);
			manaulId=Integer.parseInt((CommonFunction.checkNull(vo.getManaulId()).trim()));
				logger.info("manualAdviceAuthorDecision , getManaulId .............is.................... "+vo.getManaulId());
				logger.info("dateFormat................................................. "+dateFormat);
				logger.info("dateFormat................................................. "+vo.getAutherDate());			
//				cst = con.prepareCall("call Manual_Advice_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)");
				
				in.add(Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
				in.add(manaulId );
				date.append(CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAutherDate()).trim()));
		  	    if(date.toString() != null)
		  	    	in.add(date.toString());
//				cst.setString(3,(CommonFunction.checkNull(vo.getAutherDate()).trim()));
				in.add(CommonFunction.checkNull(vo.getAutherId()).trim());
				in.add (CommonFunction.checkNull(vo.getDecision()).trim());
				in.add(CommonFunction.checkNull(vo.getRemarks()).trim());
				out.add(s1.toString());
				 out.add(s2.toString());

				outMessages=(ArrayList) ConnectionDAO.callSP("Manual_Advice_Author",in,out);
				 s1.append(CommonFunction.checkNull(outMessages.get(0)));
				s2.append(CommonFunction.checkNull(outMessages.get(1)));
//				cst.registerOutParameter(7, Types.CHAR);
//				cst.registerOutParameter(8, Types.CHAR);
//				
//				statusProc = cst.executeUpdate();
//				logger.info("Status Proc: "+statusProc);
//				String s1 = cst.getString(7);
//				String s2 = cst.getString(8);
				logger.info("s1: " + s1);
				logger.info("s2: " + s2);
				
				if((s1.toString()).equalsIgnoreCase("S")){
					
						
					logger.info("After Proc inside If ");
					status="S";
//					con.commit();
				}else
				{
					status=(s2.toString());
//					con.rollback();
				}
		} catch (Exception e) {


			logger.info(e.getMessage());
		}
		finally
		{
			date = null;
			s1 = null;
			s2 =null;
		}

		logger.info("value of status ............................... "+status);
		return status;
		//boolean status=false;
		//try{
		// 
		//ArrayList queryList=new ArrayList();
		//
		//logger.info("manual  id......................."+manualAdviceCreationVo.getManaulId());
		//String query="update cr_manual_advice_dtl set REC_STATUS='"+manualAdviceCreationVo.getDecision()+"'" +
//				" ,REMARKS='"+manualAdviceCreationVo.getComments()+"' where MANUAL_ADVICE_ID='"+manualAdviceCreationVo.getManaulId()+"'";	
		//
		//queryList.add(query);
		//logger.info("In manualAdviceAuthorDecision"+query);	
		//
		//
		//status =ConnectionDAO.sqlInsUpdDelete(queryList);
		//logger.info("In manualAdviceAuthorDecision,,,,,"+status);
		//
		//}catch(Exception e){
//			e.printStackTrace();
		//}
		//
		//return status;
}
	public String saveManualAdviceCreationDetail(Object ob)
	{
		
		logger.info("In  saveManualAdviceCreationDetail(): ");

		ManualAdviceCreationVo mv = (ManualAdviceCreationVo)ob;
		String maxId = "";
		ArrayList qryList =  new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status=false;
//		String stat="";
		try{
		        bufInsSql.append("Insert into cr_manual_advice_dtl(TXN_TYPE,TXN_ID,BPTYPE,BPID,CHARGE_CODE,ORG_CHARGE_AMOUNT,ORG_TAX_AMOUNT1," +
		        		"ORG_TAX_AMOUNT2,ORG_TDS_AMOUNT,ADVICE_AMOUNT,TAX_RATE1,TAX_RATE2,TDS_RATE,ADVICE_TYPE," +
		        		"TDS_APPLICABLE,TAX_APPLICABLE,MAKER_REMARKS,REC_STATUS,TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS," +
		        		"TAX_INCLUSIVE,MANUAL_ADVICE_DATE,MAKER_ID,MAKER_DATE)");
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
				
				//bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'), " );
				//bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );//Advice date
				bufInsSql.append(" ?," );
				//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );
				//Saurabh space starts
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );
				}
				//Saurabh space ends

				bufInsSql.append(" )");
//				if (CommonFunction.checkNull(vo.getBusinessPartnerType())//BP_TYPE
//						.equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessPartnerType()).trim());
//				if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID())//,BP_ID
//						.equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();getLbxLoanNoHID
				
				
				insertPrepStmtObject.addString("LIM"); //TXN_TYPE
				if(CommonFunction.checkNull(mv.getLbxLoanNoHID()).equalsIgnoreCase(""))//TXN_ID
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((mv.getLbxLoanNoHID()).trim());
				
				logger.info("BusinessPartnerType........................ "+mv.getBusinessPartnerType());
				if(CommonFunction.checkNull(mv.getBusinessPartnerType()).equalsIgnoreCase(""))//BPTYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getBusinessPartnerType()).trim());
				if(CommonFunction.checkNull(mv.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))//BPID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getLbxBusinessPartnearHID()).trim());
				if(CommonFunction.checkNull(mv.getLbxCharge()).equalsIgnoreCase(""))//CHARGE_CODE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getLbxCharge()).trim());
				if(CommonFunction.checkNull(mv.getChargeAmount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getChargeAmount()).trim()).toString());
				if(CommonFunction.checkNull(mv.getTaxAmount1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getTaxAmount1()).trim()).toString());
				if(CommonFunction.checkNull(mv.getTaxAmount2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getTaxAmount2()).trim()).toString());
//				if(CommonFunction.checkNull(mv.getTdsAmount()).equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString((mv.getTdsAmount()).trim());
				insertPrepStmtObject.addString("0");
				if(CommonFunction.checkNull(mv.getAdviceAmount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getAdviceAmount()).trim()).toString());
				if(CommonFunction.checkNull(mv.getTaxRate1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getTaxRate1()).trim()).toString());
				
				if(CommonFunction.checkNull(mv.getTaxRate2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getTaxRate2()).trim()).toString());
				if(CommonFunction.checkNull(mv.getTdsRate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((mv.getTdsRate()).trim()).toString());
				
				
				
				if(CommonFunction.checkNull(mv.getAdviceType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getAdviceType()).trim());
//				{
//					if(mv.getAdviceType().trim().equalsIgnoreCase("RECEIVABLE"))
//					{
//						insertPrepStmtObject.addString(("R"));
//					}
//					else if(mv.getAdviceType().trim().equalsIgnoreCase("PAYABLE"))
//					{
//						insertPrepStmtObject.addString(("P"));
//					}
//				}
					//insertPrepStmtObject.addString((mv.getAdviceType()).trim());
				//if(CommonFunction.checkNull(mv.getTransactionDate()).equalsIgnoreCase(""))
				//	insertPrepStmtObject.addNull();
				//else
				//	insertPrepStmtObject.addString((mv.getTransactionDate()).trim());
				if(CommonFunction.checkNull(mv.getTdsApplicable()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				{
					if(mv.getTdsApplicable().trim().equalsIgnoreCase("YES"))
					{
						insertPrepStmtObject.addString(("Y"));
					}
					else if(mv.getTdsApplicable().trim().equalsIgnoreCase("NO"))
					{
						insertPrepStmtObject.addString(("N"));
					}

				}
					//insertPrepStmtObject.addString((mv.getTdsApplicable()).trim());
				if(CommonFunction.checkNull(mv.getTaxApplicable()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				{
					if(mv.getTaxApplicable().trim().equalsIgnoreCase("YES"))
					{
						insertPrepStmtObject.addString(("Y"));
					}
					else if(mv.getTaxApplicable().trim().equalsIgnoreCase("NO"))
					{
						insertPrepStmtObject.addString(("N"));
					}
				}
					//insertPrepStmtObject.addString((mv.getTaxApplicable()).trim());
				if(CommonFunction.checkNull(mv.getRemarks()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getRemarks()).trim());
					insertPrepStmtObject.addString("P");
					
					insertPrepStmtObject.addString("0");
					insertPrepStmtObject.addString("0");
					
				if(CommonFunction.checkNull(mv.getTaxInclusive()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				{
					if(mv.getTaxInclusive().trim().equalsIgnoreCase("YES"))
					{
						insertPrepStmtObject.addString(("Y"));
					}
					else if(mv.getTaxInclusive().trim().equalsIgnoreCase("NO"))
					{
						insertPrepStmtObject.addString(("N"));
					}
				}
					//insertPrepStmtObject.addString((mv.getTaxInclusive()).trim());	
				if (CommonFunction.checkNull(mv.getValueDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getValueDate()).trim());	//advice date	
				if (CommonFunction.checkNull(mv.getMakerId()).equalsIgnoreCase(""))//,,MAKER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getMakerId()).trim());
				if (CommonFunction.checkNull(mv.getMakerDate())//,MAKER_DATE
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((mv.getMakerDate()).trim());	
				
				insertPrepStmtObject.setSql(bufInsSql.toString());	  
	             logger.info("IN saveManualAdviceCreationDetail insert query1 ### "+insertPrepStmtObject.printQuery());
	             
	             qryList.add(insertPrepStmtObject);		           
	              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);    
	              logger.info("In Applicant......................"+status);
	              if(status)
				  {		
	            	  	 StringBuilder query3 = new StringBuilder();
						 query3.append("Select max(MANUAL_ADVICE_ID) from cr_manual_advice_dtl  WITH (ROWLOCK) ");
						  maxId = ConnectionDAO.singleReturn(query3.toString());
						 logger.info("maxId : "+maxId);
						 query3 = null;
				  }
																	
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return maxId;

	}
	public boolean updateManualAdviceCreationDetail(ManualAdviceCreationVo manualAdviceCreationVo,String manaulId,String forward)
	{
		logger.info("In  updateManualAdviceCreationDetail(): ");
		StringBuilder queryUpdate = new StringBuilder();

		boolean status=false;
			try{

		    ArrayList queryList=new ArrayList();
		    logger.info("forward .......................................... "+forward);
		    if(forward.equalsIgnoreCase("Forward"))
		    {
		    	manualAdviceCreationVo.setRecStatus("F");
		    }
		    else
		    {
		    	manualAdviceCreationVo.setRecStatus("P");
		    }
		    
		    if(manualAdviceCreationVo.getTaxAmount1().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTaxAmount1("0");
		    }
		    if(manualAdviceCreationVo.getTaxAmount2().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTaxAmount2("0");
		    }
		    if(manualAdviceCreationVo.getTaxRate1().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTaxRate1("0");
		    }
		    
		    if(manualAdviceCreationVo.getTaxRate2().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTaxRate2("0");
		    }
		    
		    if(manualAdviceCreationVo.getTdsRate().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTdsRate("0");
		    }
		    if(manualAdviceCreationVo.getTdsAmount().trim().toString()=="")
		    {
		    	manualAdviceCreationVo.setTdsAmount("0");
		    }
		    
		    
		    ArrayList qryListUp = new ArrayList();
			PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
			logger.info("In Update updateManualAdviceCreationDetail DAO IMPL ");
			
			
//			TXN_ID,BPTYPE,BPID,MANUAL_ADVICE_DATE,CHARGE_CODE,ORG_CHARGE_AMOUNT,ORG_TAX_AMOUNT1,ORG_TAX_AMOUNT2,ORG_TDS_AMOUNT,ADVICE_AMOUNT,
//			TAX_RATE1,TAX_RATE2,TDS_RATE,ADVICE_TYPE,TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,TDS_APPLICABLE,TAX_APPLICABLE,TAX_INCLUSIVE,
//			REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE
			
				queryUpdate.append(" update cr_manual_advice_dtl set TXN_ID=?,BPTYPE=?,BPID=?,CHARGE_CODE=?,ORG_CHARGE_AMOUNT=?," +
					"ORG_TAX_AMOUNT1=?,ORG_TAX_AMOUNT2=?,ADVICE_AMOUNT=?,TAX_RATE1=?,TAX_RATE2=?,TDS_RATE=?,ADVICE_TYPE=?," +
					"TDS_APPLICABLE=?,TAX_APPLICABLE=?,TAX_INCLUSIVE=?,MAKER_REMARKS=?," +
					"REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				//queryUpdate.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  ");
				//Saurabh space starts
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					queryUpdate.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				}
				else
				{
					queryUpdate.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  ");
				}
				//Saurabh space ends

				queryUpdate.append("where MANUAL_ADVICE_ID='"+manaulId+"' ");

			if (CommonFunction.checkNull(manualAdviceCreationVo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getLbxLoanNoHID()).trim()));
			
			if(CommonFunction.checkNull(manualAdviceCreationVo.getBusinessPartnerType()).equals(""))
				updatePrepStmtObject.addNull();
		    else
		    	updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getBusinessPartnerType()).trim()));
			
			if(CommonFunction.checkNull(manualAdviceCreationVo.getLbxBusinessPartnearHID()).equals(""))
				updatePrepStmtObject.addNull();
		    else
		    	updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getLbxBusinessPartnearHID()).trim()));

						
			if (CommonFunction.checkNull(manualAdviceCreationVo.getLbxCharge()).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getLbxCharge().trim()).trim()));
			
			
			if (CommonFunction.checkNull(manualAdviceCreationVo.getChargeAmount())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((manualAdviceCreationVo.getChargeAmount()).trim()).toString());
			
			
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxAmount1())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo.getTaxAmount1()).trim())).toString());
			
			
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxAmount2())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo.getTaxAmount2()).trim())).toString());
			if (CommonFunction.checkNull(manualAdviceCreationVo.getAdviceAmount())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo.getAdviceAmount()).trim())).toString());
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxRate1())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo.getTaxRate1()).trim())).toString());
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxRate2())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo.getTaxRate2()).trim())).toString());
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTdsRate())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(manualAdviceCreationVo
						.getTdsRate()).trim())).toString());
			if (CommonFunction.checkNull(manualAdviceCreationVo.getAdviceType())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getAdviceType()).trim()));
//			{
//				if(manualAdviceCreationVo.getAdviceType().trim().equalsIgnoreCase("RECEIVABLE"))
//				{
//					updatePrepStmtObject.addString(("R"));
//				}
//				else if(manualAdviceCreationVo.getAdviceType().trim().equalsIgnoreCase("PAYABLE"))
//				{
//					updatePrepStmtObject.addString(("P"));
//				}
//				
//			}
				
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTdsApplicable())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
			{
				if(manualAdviceCreationVo.getTdsApplicable().trim().equalsIgnoreCase("YES"))
				{
					updatePrepStmtObject.addString(("Y"));
				}
				else if(manualAdviceCreationVo.getTdsApplicable().trim().equalsIgnoreCase("NO"))
				{
					updatePrepStmtObject.addString(("N"));
				}
			}
				
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxApplicable())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
			{
				if(manualAdviceCreationVo.getTaxApplicable().trim().equalsIgnoreCase("YES"))
				{
					updatePrepStmtObject.addString(("Y"));
				}
				else if(manualAdviceCreationVo.getTaxApplicable().trim().equalsIgnoreCase("NO"))
				{
					updatePrepStmtObject.addString(("N"));
				}
			}
				
			if (CommonFunction.checkNull(manualAdviceCreationVo.getTaxInclusive())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
			{
				if(manualAdviceCreationVo.getTaxInclusive().trim().equalsIgnoreCase("YES"))
				{
					updatePrepStmtObject.addString(("Y"));
				}
				else if(manualAdviceCreationVo.getTaxInclusive().trim().equalsIgnoreCase("NO"))
				{
					updatePrepStmtObject.addString(("N"));
				}
			}
				
			if (CommonFunction.checkNull(manualAdviceCreationVo.getRemarks())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getRemarks()).trim()));
			if (CommonFunction.checkNull(manualAdviceCreationVo.getRecStatus())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getRecStatus()).trim()));
			if (CommonFunction.checkNull(manualAdviceCreationVo.getMakerId())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getMakerId()).trim()));
			if (CommonFunction.checkNull(manualAdviceCreationVo.getMakerDate())
					.equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(manualAdviceCreationVo.getMakerDate()).trim()));
			
			
			updatePrepStmtObject.setSql(queryUpdate.toString());

			logger.info("IN updateListOfvalue() insert query1 ### "+ updatePrepStmtObject.printQuery());
			qryListUp.add(updatePrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUp);
							
			
			logger.info("In updateListOfvalue......................"	+ status);
		    
				    
		    
		    
		    
		    
//			String query="update cr_manual_advice_dtl set TXN_ID='"+CommonFunction.checkNull((manualAdviceCreationVo.getLbxLoanNoHID()).trim())+"'," +
//					"BPTYPE='"+CommonFunction.checkNull((manualAdviceCreationVo.getBusinessPartnerType()).trim())+"'," +
//					"BPID='"+CommonFunction.checkNull((manualAdviceCreationVo.getLbxBusinessPartnearHID()).trim())+"'," +
//					"CHARGE_CODE='"+CommonFunction.checkNull((manualAdviceCreationVo.getLbxCharge()).trim())+"'," +
//					"ORG_CHARGE_AMOUNT="+CommonFunction.checkNull((manualAdviceCreationVo.getChargeAmount()).trim())+"," +
//					"ORG_TAX_AMOUNT1="+CommonFunction.checkNull((manualAdviceCreationVo.getTaxAmount1()).trim())+"," +
//					"ORG_TAX_AMOUNT2="+CommonFunction.checkNull((manualAdviceCreationVo.getTaxAmount2()).trim())+"," +
//					
//					"ADVICE_AMOUNT="+CommonFunction.checkNull((manualAdviceCreationVo.getAdviceAmount()).trim())+"," +
//					"TAX_RATE1="+CommonFunction.checkNull((manualAdviceCreationVo.getTaxRate1()).trim())+"," +
//					"TAX_RATE2="+CommonFunction.checkNull((manualAdviceCreationVo.getTaxRate2()).trim())+"," +
//					"TDS_RATE="+CommonFunction.checkNull((manualAdviceCreationVo.getTdsRate()).trim())+"," +
//					"ADVICE_TYPE='"+CommonFunction.checkNull((manualAdviceCreationVo.getAdviceType()).trim())+"'," +
//					"TDS_APPLICABLE='"+CommonFunction.checkNull((manualAdviceCreationVo.getTdsApplicable()).trim())+"'," +
//					"TAX_APPLICABLE='"+CommonFunction.checkNull((manualAdviceCreationVo.getTaxApplicable()).trim())+"'," +
//					"REMARKS='"+CommonFunction.checkNull((manualAdviceCreationVo.getRemarks()).trim())+"'," +
//					"REC_STATUS='"+CommonFunction.checkNull((manualAdviceCreationVo.getRecStatus()).trim())+"'," +
//					"TAX_INCLUSIVE='"+CommonFunction.checkNull((manualAdviceCreationVo.getTaxInclusive()).trim())+"'," +
//					"MAKER_ID='"+CommonFunction.checkNull((manualAdviceCreationVo.getMakerId()).trim())+"'," +
//					"MAKER_DATE='"+CommonFunction.checkNull((manualAdviceCreationVo.getMakerDate()).trim())+"'" +
//					" WHERE MANUAL_ADVICE_ID='"+manaulId+"' ";
	//	
//			queryList.add(query);
		
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				queryUpdate = null;
			}

			return status;

	}
	public ArrayList getManualAdviceDetailScheme(String charge_code_id,String productId, String schemeId,String loanAmount)
	{
		logger.info("In  getManualAdviceDetailScheme(): ");
		int charge_code_id1 = Integer.parseInt(charge_code_id);
		ManualAdviceCreationVo vo = new ManualAdviceCreationVo();
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		StringBuilder query1 = new StringBuilder();
		try {
//			 String query = "select if(CHARGE_TYPE='R','RECEIVABLE','PAYABLE'),if(TAX_APPLICABLE='Y','YES','NO'),if(TDS_APPLICABLE='Y','YES','NO'),TAX_RATE1,TAX_RATE2,TDS_RATE,if(TAX_INCLUSIVE='Y','YES','NO'),CHARGE_ID" +
//			 		" from com_charges_m where CHARGE_CODE="+ charge_code_id1	+ " and CHARGE_BP_TYPE='" + bp_type + "'";

			 query.append("SELECT DISTINCT ");
			 if(dbType.equalsIgnoreCase("MSSQL"))
			 {
			 query.append(" TOP 1 ");
			 }
			 query.append(" CHARGE_TYPE, ");
			 query.append("ISNULL(CHARGE_AMOUNT,0),");
			 query.append("ISNULL(TDS_RATE,0),CASE WHEN ");
			 query.append("ISNULL(TAX_APPLICABLE,'N')='N' THEN 'NO' ELSE 'YES' END AS TAX_APPLICABLE,CASE WHEN ");
			 query.append("ISNULL(TAX_INCLUSIVE,'N')='N' THEN 'NO' ELSE 'YES' END AS TAX_INCLUSIVE, ");
			 query.append("ISNULL(TAX_RATE1,0), ");
			 query.append("ISNULL(TAX_RATE2,0),CASE WHEN ");
			 query.append("ISNULL(TDS_APPLICABLE,'N')='N' THEN 'NO' ELSE 'YES' END AS  TDS_APPLICABLE,CHARGE_ID" +
		 		          " FROM COM_CHARGES_M A  WHERE PRODUCT_ID = '"+productId+"' AND ");
			 query.append("ISNULL(SCHEME_ID,0) = '"+schemeId+"' AND A.CHARGE_CODE = '"+charge_code_id+"' AND A.REC_STATUS = 'A' ");
			 if(!(dbType.equalsIgnoreCase("MSSQL")))
				{
				 query.append(" LIMIT 1 ");
				}

			 
			logger.info("getManualAdviceDetailScheme  " + query.toString());
			ArrayList schemedeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getLeadEntryList" + schemedeatail.size());
			
			query1.append("SELECT DISTINCT ");
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				query1.append(" TOP 1 ");
			}
			query1.append("CHARGE_TYPE, ");
			query1.append("ISNULL(CHARGE_AMOUNT,0),");
			query1.append("ISNULL(TDS_RATE,0),CASE WHEN ");
			query1.append("ISNULL(TAX_APPLICABLE,'N')='N' THEN 'NO' ELSE 'YES' END AS TAX_APPLICABLE,CASE WHEN ");
			query1.append("ISNULL(TAX_INCLUSIVE,'N')='N' THEN 'NO' ELSE 'YES' END AS TAX_INCLUSIVE, ");
			query1.append("ISNULL(TAX_RATE1,0), ");
			query1.append("ISNULL(TAX_RATE2,0),CASE WHEN ");
			query1.append("ISNULL(TDS_APPLICABLE,'N')='N' THEN 'NO' ELSE 'YES' END AS TDS_APPLICABLE,CHARGE_ID" +
	 		          " FROM COM_CHARGES_M A  WHERE PRODUCT_ID = '"+productId+"' AND A.CHARGE_CODE = '"+charge_code_id+"' AND A.REC_STATUS = 'A' ");
			
			
			if(!(dbType.equalsIgnoreCase("MSSQL")))
			{
				query1.append(" LIMIT 1 ");
			}

		 
		logger.info("getManualAdviceDetailScheme schemedeatailProduct " + query);
		ArrayList schemedeatailProduct = ConnectionDAO.sqlSelect(query1.toString());
		logger.info("schemedeatailProduct" + schemedeatailProduct.size());
			
	if(schemedeatail.size()>0)
    {
			for (int i = 0; i < schemedeatail.size(); i++) {

				ArrayList schemedeatail1 = (ArrayList) schemedeatail.get(i);
				for (int k = 0; k < schemedeatail1.size(); k++) {
					
					logger.info("getManualAdvice List size " + schemedeatail1.size());
					
					vo.setAdviceType((CommonFunction.checkNull(schemedeatail1.get(0)).trim()));
					vo.setChargeAmount((CommonFunction.checkNull(schemedeatail1.get(1)).trim()));
					if(!CommonFunction.checkNull(schemedeatail1.get(2)).equalsIgnoreCase(""))
		    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(2))).trim());
	    	    		vo.setTdsRate(myFormatter.format(reconNum));
		    		}
					vo.setTaxApplicable((CommonFunction.checkNull(schemedeatail1.get(3)).trim()));
					vo.setTaxInclusive((CommonFunction.checkNull(schemedeatail1.get(4)).trim()));	
					
					
					if(!CommonFunction.checkNull(schemedeatail1.get(5)).equalsIgnoreCase(""))
		    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(5))).trim());
	    	    		vo.setTaxRate1(myFormatter.format(reconNum));
		    		}
									
					if(!CommonFunction.checkNull(schemedeatail1.get(6)).equalsIgnoreCase(""))
		    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(6))).trim());
	    	    		vo.setTaxRate2(myFormatter.format(reconNum));
		    		}
					
					vo.setTdsApplicable((CommonFunction.checkNull(schemedeatail1.get(7)).trim()));
					vo.setChargeId((CommonFunction.checkNull(schemedeatail1.get(8)).trim()));
					
				}
					
				list.add(vo);
			}
   }
   else
   {
	   for (int i = 0; i < schemedeatail.size(); i++) {

			ArrayList schemedeatail1 = (ArrayList) schemedeatail.get(i);
			for (int k = 0; k < schemedeatail1.size(); k++) {
				
				logger.info("getManualAdvice List size " + schemedeatail1.size());
				
				vo.setAdviceType((CommonFunction.checkNull(schemedeatail1.get(0)).trim()));
				vo.setChargeAmount((CommonFunction.checkNull(schemedeatail1.get(1)).trim()));
				if(!CommonFunction.checkNull(schemedeatail1.get(2)).equalsIgnoreCase(""))
	    		{
   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(2))).trim());
   	    		vo.setTdsRate(myFormatter.format(reconNum));
	    		}
				vo.setTaxApplicable((CommonFunction.checkNull(schemedeatail1.get(3)).trim()));
				vo.setTaxInclusive((CommonFunction.checkNull(schemedeatail1.get(4)).trim()));	
				
				
				if(!CommonFunction.checkNull(schemedeatail1.get(5)).equalsIgnoreCase(""))
	    		{
   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(5))).trim());
   	    		vo.setTaxRate1(myFormatter.format(reconNum));
	    		}
								
				if(!CommonFunction.checkNull(schemedeatail1.get(6)).equalsIgnoreCase(""))
	    		{
   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(6))).trim());
   	    		vo.setTaxRate2(myFormatter.format(reconNum));
	    		}
				
				vo.setTdsApplicable((CommonFunction.checkNull(schemedeatail1.get(7)).trim()));
				vo.setChargeId((CommonFunction.checkNull(schemedeatail1.get(8)).trim()));
				
			}
				
			list.add(vo);
		}
    }

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			query = null;
		}
		return list;

	}
	//Ritu
	public boolean deleteManualAdvice(String id) {
		
		boolean status=false;
		ArrayList list=new ArrayList();
		StringBuilder query=new StringBuilder();
		try{
		query.append ("delete from cr_manual_advice_dtl where MANUAL_ADVICE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
		list.add(query.toString());
		logger.info("deletelead()     query------------------" + query.toString());
		status =ConnectionDAO.sqlInsUpdDelete(list);
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  return status;

	}



}
