package com.cp.leadDaoImplMYSQL;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.VO.CustomerCategoryVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.daoImplMYSQL.CreditProcessingDAOImpl;
import com.cp.leadDao.LeadDao;
import com.cp.leadDao.PreDealDao;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.CreditProcessingLeadEntryVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.DedupeReferalDTO;
import com.cp.vo.LeadCaptureVo;
import com.cp.vo.LeadTrackingNotepadVo;
import com.cp.vo.LoanDetailVo;
import com.cp.vo.RefreshFlagVo;
import com.utils.async.LMSMessagingClient;

public class preDealDaoImp implements PreDealDao
{
	private static final Logger logger = Logger.getLogger(preDealDaoImp.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");

	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList getexistingData(LeadCaptureVo ob, String tracking) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
//		String leadno = "";
//		String product = "";
//		String schemeId = "";
//		String customername = "";
//		String branch = "";
//		String rmname = "";
//		String gendate = "";
		
		StringBuilder leadno = new StringBuilder();
		StringBuilder product = new StringBuilder();
		StringBuilder schemeId = new StringBuilder();
		StringBuilder customername = new StringBuilder();
		StringBuilder branch = new StringBuilder();
		StringBuilder rmname = new StringBuilder();
		StringBuilder gendate = new StringBuilder();
	
		StringBuilder status = new StringBuilder();
		
//		leadno = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim();
//		product = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim();
//		schemeId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim();
//		customername = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim();
//		branch = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim();
//		rmname = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim();
////		vendor = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getVendor())).trim();
//		gendate = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim();
		
		leadno.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim());
		product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim());
		schemeId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim());
		customername.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim());
		branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim());
		rmname.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim());
//		vendor = StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getVendor())).trim();
		gendate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim());
		
		status.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim());
		
	
		LeadCaptureVo LeadCaptureVo = (LeadCaptureVo) ob;
		ArrayList header = new ArrayList();
		ArrayList list = new ArrayList();
		
//		String products = CommonFunction.checkNull(ob.getProduct());
//		logger.debug("In getexistingData() products..........................." + products);

		try {
			//ArrayList header = null;
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTmp =  new StringBuffer();
			logger.info("In getexistingData..........................DAOImpl"+ ob.getLeadno()+""+schemeId);
			bufInsSql.append("select LEAD_ID,DATE_FORMAT(LEAD_GENERATION_DATE,'" +dateFormat+ "'),p.PRODUCT_DESC,CUSTOMER_NAME," +
							"bb.BRANCH_DESC,SCHEME,LEAD_GENERATED_RM,SOURCE_TYPE,bm.BRANCH_DESC,l.REC_STATUS  from cr_lead_dtl l  " +
							"left outer join com_branch_m bb on l.SOURCE_CODE=bb.BRANCH_ID  " +
							"left outer join com_branch_m bm on l.ALLOCATED_BRANCH=bm.BRANCH_ID " +
							"left outer join cr_product_m p on p.PRODUCT_ID=l.PRODUCT ");

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate()))
							.trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID()))
							.trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername()))
							.trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())
							.trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())
							.trim()).equalsIgnoreCase(""))) 
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator()))
						.trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus()))
								.trim().equalsIgnoreCase("")))){
				bufInsSql.append("WHERE DATE_FORMAT(LEAD_GENERATION_DATE) =STR_TO_DATE('"
								+ StringEscapeUtils.escapeSql(ob.getApplicationdate()).trim()
								+ "','"
								+ dateFormat
								+ "'"
								+ " and PRODUCT='"+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim()+ "'"
								+ " and CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim()+ "%'"
								+ " AND SOURCE_CODE='"+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()+ "' "
								+ " and SCHEME='"+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "'"
								+ " and SOURCE_TYPE like'%"+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() +"%'"
								+ "and l.REC_STATUS ="+ StringEscapeUtils.escapeSql(ob.getStatus()).trim());
								
			}

			if (((ob.getLeadno().equalsIgnoreCase("")))
					|| ((ob.getApplicationdate().equalsIgnoreCase("")))
					|| ((ob.getLbxProductID().equalsIgnoreCase("")))
					|| ((ob.getSchemeId().equalsIgnoreCase("")))
					|| ((ob.getCustomername().equalsIgnoreCase("")))
					|| ((ob.getLbxBranchId().equalsIgnoreCase("")))
					|| ((ob.getLeadGenerator().equalsIgnoreCase("")))) {
				appendSQL = true;
			}
			bufInsSqlTmp.append("SELECT COUNT(1) FROM cr_lead_dtl ");
			if (appendSQL) {
				bufInsSql.append(" WHERE ");
				bufInsSqlTmp.append(" WHERE ");
			}

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
				bufInsSqlTmp.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
				appendSQL = true;

			}
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" PRODUCT='"+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim() + "' AND");
				bufInsSqlTmp.append(" PRODUCT='"+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim() + "' AND");
				appendSQL = true;

			}
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" SCHEME='"+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "' AND");
				bufInsSqlTmp.append(" SCHEME='"+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "' AND");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim() + "%' AND");
				bufInsSqlTmp.append(" CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim() + "%' AND");
				appendSQL = true;

			}
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" SOURCE_CODE='"+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()+ "' AND");
				bufInsSqlTmp.append(" SOURCE_CODE='"+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()+ "' AND");
				appendSQL = true;

			}
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" DATE(LEAD_GENERATION_DATE) =STR_TO_DATE('"+ StringEscapeUtils.escapeSql(ob.getApplicationdate())
								.trim() + "','" + dateFormat + "') AND");
				bufInsSqlTmp.append(" DATE(LEAD_GENERATION_DATE) =STR_TO_DATE('"+ StringEscapeUtils.escapeSql(ob.getApplicationdate())
						.trim() + "','" + dateFormat + "') AND");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" SOURCE_TYPE like '%"+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() + "%' AND ");
				bufInsSqlTmp.append(" SOURCE_TYPE like '%"+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() + "%' AND ");
				appendSQL = true;

			}
//			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getVendor())).trim().equalsIgnoreCase("")))) {
//				bufInsSql.append(" SOURCE_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getVendor()).trim() + "%' AND " +
//						"SOURCE_TYPE ='VENDOR' AND");
//				bufInsSqlTmp.append(" SOURCE_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getVendor()).trim() + "%' AND " +
//				"SOURCE_TYPE ='VENDOR' AND");
//				appendSQL = true;
//
//			}

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" l.REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' AND");
				bufInsSqlTmp.append(" REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' AND");
				appendSQL = true;

			}
			
			logger.info("In appendSQL true---- " + appendSQL);
		
			if (appendSQL)
			{
				StringBuilder tmp=new StringBuilder();
				
				 tmp.append( bufInsSql.toString());
				 
				//String bufInsSqlTemp = bufInsSqlTmp.toString();
				StringBuilder bufInsSqlTemp=new StringBuilder();
				
				bufInsSqlTemp.append(bufInsSqlTmp.toString());
				
				logger.info("In appendSQL true----  in check index Of"+ tmp.lastIndexOf("AND") + "------"+ (tmp.length() - 3));
				
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3)) 
				{ 
					tmp.append((tmp).substring(0, (tmp.length() - 4)));
					
					bufInsSqlTemp.append((bufInsSqlTemp).substring(0, (bufInsSqlTemp.length() - 4)));
					
					header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp.toString()));
				} 
				else {
					header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
						}
				tmp=null;
				bufInsSqlTemp=null;
				
				}else {
					
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTmp.toString());
		            
					
					if((leadno.toString().trim()==null && product.toString().trim()==null && schemeId.toString().trim()==null && customername.toString().trim()==null && branch.toString().trim()==null && rmname.toString().trim()==null && gendate.toString().trim()==null) 
						|| (leadno.toString().trim().equalsIgnoreCase("") && product.toString().trim().equalsIgnoreCase("") && schemeId.toString().trim().equalsIgnoreCase("") && customername.toString().trim().equalsIgnoreCase("") && branch.toString().trim().equalsIgnoreCase("") && rmname.toString().trim().equalsIgnoreCase("") && gendate.toString().trim().equalsIgnoreCase("")) || LeadCaptureVo.getCurrentPageLink()>1)
					{
					
					logger.info("current PAge Link no .................... "+LeadCaptureVo.getCurrentPageLink());
					if(LeadCaptureVo.getCurrentPageLink()>1)
					{
						startRecordIndex = (LeadCaptureVo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					
					}
					logger.info("In getexistingData()  Query...tmp." + bufInsSql);
					
					header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				}

			
				LeadCaptureVo noteVo = null;
				int size=header.size();
				for (int i = 0; i < size; i++) {
					logger.info("getexistingData...Outer FOR loop "+ CommonFunction.checkNull(header.get(i)).toString());
					ArrayList data = (ArrayList) header.get(i);
					if (data.size() > 0) {
						noteVo = new LeadCaptureVo();
						if(tracking.equalsIgnoreCase("3000116")){
							noteVo.setLeadno("<a href=leadTrackingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
							if((CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase((CommonFunction.checkNull(data.get(8))).trim())){
								noteVo.setBranch((CommonFunction.checkNull(data.get(4))).trim());
							}else{
								noteVo.setBranch((CommonFunction.checkNull(data.get(8))).trim());
							}
						}else{
							noteVo.setLeadno("<a href=leadCapturingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
							if((CommonFunction.checkNull(data.get(6))).trim().equalsIgnoreCase("BRANCH")){
								noteVo.setBranch((CommonFunction.checkNull(data.get(4))).trim());
							}
						}
						
						noteVo.setApplicationdate((CommonFunction.checkNull(data.get(1))).trim());
						noteVo.setProduct((CommonFunction.checkNull(data.get(2))).trim());
						noteVo.setCustomername((CommonFunction.checkNull(data.get(3))).trim());
				
				
						noteVo.setLeadGenerator((CommonFunction.checkNull(data.get(6))).trim());
						noteVo.setRmname((CommonFunction.checkNull(data.get(7))).trim());
						noteVo.setTotalRecordSize(count);
						list.add(noteVo);
					}
					bufInsSql=null;
					bufInsSqlTmp=null;
					data.clear();
					data=null;
				}
				noteVo=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			leadno=null;
			product=null;
			schemeId=null;
			customername=null;
			branch=null;
			rmname=null;
			gendate=null;
			status=null;
			header.clear();
			header=null;
			LeadCaptureVo=null;

			
		}


		return list;
	}

	public ArrayList<CreditProcessingLeadDetailDataVo> getLeadCapturingDetailsList(String leadId) {
		ArrayList<CreditProcessingLeadDetailDataVo> list = new ArrayList<CreditProcessingLeadDetailDataVo>();

		try {
			
			StringBuilder query=new StringBuilder();
			query.append("SELECT distinct  l.LEAD_ID,l.SOURCE_TYPE,u.USER_NAME,l.EXISTING_CUSTOMER,l.RELATIONSHIP_SINCE,l.CUSTOMER_NAME,l.CONTACT_PERSON,  ");
			query.append("l.PERSON_DESIGNATION,l.ADDRESS_LINE1,l.ADDRESS_LINE2,l.ADDRESS_LINE3,c1.COUNTRY_DESC,st1.STATE_DESC,d1.DISTRICT_DESC,  ");
			query.append("l.PINCODE,l.PRIMARY_PHONE,l.ALTERNATE_PHONE,l.EMAIL_ID,l.ALTERNATE_MAIL_ID,p.PRODUCT_DESC,s.SCHEME_DESC,l.AMOUNT_REQUIRED,  ");
			query.append("l.LOAN_TENURE,l.LOAN_PURPOSE,l.REC_STATUS,l.COUNTRY,l.STATE,l.DISTRICT,l.PRODUCT,l.SCHEME,l.LANDMARK,l.NO_OF_YEARS  ");
			query.append(",im.INDUSTRY_DESC,sim.SUB_INDUSTRY_DESC,l.INDUSTRY_ID,l.SUB_INDUSTRY_ID,gm.description,DATE_FORMAT(l.LEAD_GENERATION_DATE,'%d-%m-%Y')  ");
			query.append(",p.PRODUCT_CATEGORY,l.CUSTOMER_TYPE,l.TURNOVER,l.ADDRESS_TYPE,l.GROUP_ID,l.CONSTITUTION,l.REGISTRATION_NO,l.PAN,l.BUSINESS_SEGMENT,l.first_name  ");
			query.append(",l.last_NAME,DATE_FORMAT(l.CUSTOMER_DOB,'%d-%m-%Y'),(select m.GROUP_DESC from gcd_group_m m where m.GROUP_ID=l.GROUP_ID) groupname,  ");
			query.append("l.GROUP_TYPE,l.GROUP_DESC,l.LOAN_TYPE,L.OTHER,L.NO_OF_MONTHS,L.OWNERSHIP,l.FATHER_HUSBAND_NAME,l.PASSPORT_NUMBER,l.DRIVING_LICENSE,l.VOTER_ID  ");
			query.append(",l.TAHSIL,l.SOURCE,gm.DESCRIPTION,l.SOURCE_DESCRIPTION,l.AREA_CODE,(SELECT AREA_CODE_NAME FROM com_areacode_m WHERE AREA_CODE=l.AREA_CODE) AS AREA_CODE_NAME  ");
			query.append(",ctm.DESCRIPTION,l.EDU_DETAIL,l.SECTOR_TYPE,  ");

			/*query.append("coapp.EXISTING_CUSTOMER,coapp.CUSTOMER_NAME,coapp.ADDRESS_LINE1,coapp.ADDRESS_LINE2,coapp.ADDRESS_LINE3,cc.COUNTRY_DESC,stt.STATE_DESC,dd.DISTRICT_DESC,  ");
			query.append("coapp.PINCODE,coapp.PRIMARY_PHONE,coapp.EMAIL_ID,coapp.COUNTRY,coapp.STATE,coapp.DISTRICT,coapp.LANDMARK,imm.INDUSTRY_DESC,simm.SUB_INDUSTRY_DESC,coapp.INDUSTRY_ID,  ");
			query.append("coapp.SUB_INDUSTRY_ID,coapp.CUSTOMER_TYPE,coapp.ADDRESS_TYPE,coapp.GROUP_ID,coapp.CONSTITUTION,coapp.REGISTRATION_NO,coapp.PAN,coapp.BUSINESS_SEGMENT,coapp.CUSTOMER_FNAME  ");
			query.append(",coapp.CUSTOMER_LNAME,DATE_FORMAT(coapp.CUSTOMER_DOB,'%d-%m-%Y'),(select mm.GROUP_DESC from gcd_group_m mm where mm.GROUP_ID=coapp.GROUP_ID) groupname,  ");
			query.append("coapp.GROUP_TYPE,coapp.GROUP_DESC,coapp.FATHER_HUSBAND_NAME,coapp.PASSPORT_NUMBER,coapp.DRIVING_LICENSE,coapp.VOTER_ID  ");
			query.append(",coapp.TAHSIL,ctmm.DESCRIPTION,  ");*/			
			query.append("l.GENDER,l.UID_NO,l.APPLICATION_FORM_NO,l.MIDDLE_NAME,app.CUSTOMER_ID,l.lead_RM,l.lead_ro,(SELECT user_name FROM SEC_USER_M WHERE USER_ID=l.LEAD_RM) AS LEAD_RManger,(SELECT user_name FROM SEC_USER_M WHERE USER_ID=l.LEAD_RO) AS LEAD_ROfficer,deal.deal_no,deal.deal_id ");
					query.append("from cr_lead_dtl l  "); 
					query.append("left  JOIN com_country_m c1 on c1.COUNTRY_ID = l.COUNTRY "); 
					query.append("left  JOIN com_district_m d1 on d1.DISTRICT_ID = l.DISTRICT ");   
					query.append("left  JOIN com_state_m st1 on l.STATE = st1.STATE_ID "); 
					query.append("left  join cr_lead_customer_m app on app.LEAD_ID=l.lead_id and app.CUSTOMER_ROLE_TYPE='PRAPPL'  ");
					query.append("left  JOIN com_country_m c on app.COUNTRY = c.COUNTRY_ID   ");
					query.append("left  JOIN sec_user_m u on l.LEAD_SERVICING_RM = u.USER_ID   ");
					query.append("left  JOIN com_district_m d on app.DISTRICT = d.DISTRICT_ID   ");
					query.append("left  JOIN com_state_m st on app.STATE = st.STATE_ID   ");
					query.append("left  JOIN cr_scheme_m s on l.SCHEME = s.SCHEME_ID   ");
					query.append("left  JOIN generic_master gm on l.source = gm.value and gm.generic_key='SOURCE_TYPE'  "); 
					query.append("left  JOIN cr_product_m p on l.PRODUCT = p.PRODUCT_ID   ");
					query.append("left  JOIN com_industry_m im on app.INDUSTRY_ID = im.INDUSTRY_ID  "); 
					query.append("left  JOIN com_sub_industry_m sim on app.SUB_INDUSTRY_ID = sim.sub_industry_id  ");  
					query.append("left join com_tehsil_m ctm on ctm.ID=l.TAHSIL   ");
					query.append("left join cr_deal_dtl deal on deal.lead_id=l.lead_id   ");

/*					query.append("left join cr_lead_customer_m coapp on coapp.LEAD_ID=l.lead_id and coapp.CUSTOMER_ROLE_TYPE='COAPPL'  ");
					query.append("left  JOIN com_country_m cc on coapp.COUNTRY = cc.COUNTRY_ID   ");
					query.append("left  JOIN com_district_m dd on coapp.DISTRICT = dd.DISTRICT_ID   ");
					query.append("left  JOIN com_state_m stt on coapp.STATE = stt.STATE_ID   ");
					query.append("left  JOIN com_industry_m imm on coapp.INDUSTRY_ID = imm.INDUSTRY_ID  "); 
					query.append("left  JOIN com_sub_industry_m simm on coapp.SUB_INDUSTRY_ID = simm.sub_industry_id  ");  
					query.append("left join com_tehsil_m ctmm on ctmm.ID=coapp.TAHSIL   ");*/

/*					query.append("left join cr_lead_customer_m gaur on gaur.LEAD_ID=l.lead_id and gaur.CUSTOMER_ROLE_TYPE='GUARANTOR'  ");
					query.append("left  JOIN com_country_m ccc on gaur.COUNTRY = ccc.COUNTRY_ID   ");
					query.append("left JOIN com_district_m ddd on gaur.DISTRICT = ddd.DISTRICT_ID   ");
					query.append("left  JOIN com_state_m sttt on gaur.STATE = sttt.STATE_ID   ");
					query.append("left  JOIN com_industry_m immm on gaur.INDUSTRY_ID = immm.INDUSTRY_ID  "); 
					query.append("left  JOIN com_sub_industry_m simmm on gaur.SUB_INDUSTRY_ID = simmm.sub_industry_id  ");  
					query.append("left join com_tehsil_m ctmmm on ctmmm.ID=gaur.TAHSIL   ");*/
					query.append("where l.LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId))+"'  ");

			logger.info("getLeadCapturingDetailsList" + query);
			
			CreditProcessingLeadDetailDataVo leadVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			query=null;
			int size=leaddeatail.size();
			
			for (int i = 0; i < size; i++) {

				
				ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
				if (size > 0) {
					
					leadVo = new CreditProcessingLeadDetailDataVo();
					
					leadVo.setLeadId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
					
					if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
						if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO")){
							leadVo.setLeadGenerator((CommonFunction.checkNull("RM / SALES EXEC")).trim());
							leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH")){
							leadVo.setLeadGenerator((CommonFunction.checkNull("TELE CALLER")).trim());
							leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS")){
							leadVo.setLeadGenerator((CommonFunction.checkNull("OTHERS")).trim());
							leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
							leadVo.setLeadGenerator((CommonFunction.checkNull("DEALER")).trim());
							leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						}
					}else{
						leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(36))).trim());
						leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					}
					
					leadVo.setServicingRm((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
					
					if(leaddeatail1.get(3).equals("N")){
						leadVo.setRelationship("New");
					}else if(leaddeatail1.get(3).equals("Y")){
						leadVo.setRelationship("Existing");
					}

					leadVo.setRelationshipSince((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
					
					leadVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
					leadVo.setContactPerson((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
					leadVo.setPersonDesignation((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
					leadVo.setAddress1((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
					leadVo.setAddress2((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
					leadVo.setAddress3((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
					leadVo.setCountry((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
					leadVo.setState((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
					leadVo.setDist((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
					leadVo.setPincode((CommonFunction.checkNull(leaddeatail1.get(14))).trim());
					leadVo.setPhoneOff((CommonFunction.checkNull(leaddeatail1.get(15))).trim());
					leadVo.setPhoneRes((CommonFunction.checkNull(leaddeatail1.get(16))).trim());
					leadVo.setEmail(CommonFunction.checkNull(leaddeatail1.get(17)).toString());
					leadVo.setAltEmail((CommonFunction.checkNull(leaddeatail1.get(18))).trim());
					leadVo.setProduct((CommonFunction.checkNull(leaddeatail1.get(19))).trim());
					leadVo.setScheme((CommonFunction.checkNull(leaddeatail1.get(20))).trim());
					
					if (!CommonFunction.checkNull(leaddeatail1.get(21)).equalsIgnoreCase("")) {
						Number reconNum = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(21))).trim());
						leadVo.setLoanAmount(myFormatter.format(reconNum));
						
					}
					leadVo.setLoanTenure((CommonFunction.checkNull(leaddeatail1.get(22))).trim());
					leadVo.setLoanPurpose((CommonFunction.checkNull(leaddeatail1.get(23))).trim());
					leadVo.setStatus((CommonFunction.checkNull(leaddeatail1.get(24))).trim());
					leadVo.setTxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(25))).trim());
					leadVo.setTxtStateCode((CommonFunction.checkNull(leaddeatail1.get(26))).trim());
					leadVo.setTxtDistCode((CommonFunction.checkNull(leaddeatail1.get(27))).trim());
					leadVo.setLbxProductID((CommonFunction.checkNull(leaddeatail1.get(28))).trim());
					leadVo.setSchemeId((CommonFunction.checkNull(leaddeatail1.get(29))).trim());
//					leadVo.setLbxRegionID((CommonFunction.checkNull(leaddeatail1.get(30))).trim());
					
					leadVo.setLandmark((CommonFunction.checkNull(leaddeatail1.get(30))).trim());					
					leadVo.setNoOfYears((CommonFunction.checkNull(leaddeatail1.get(31))).trim());

					
					leadVo.setIndustry((CommonFunction.checkNull(leaddeatail1.get(32))).trim());
					leadVo.setSubIndustry((CommonFunction.checkNull(leaddeatail1.get(33))).trim());
					
					leadVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(34))).trim());
					leadVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(35))).trim());
					leadVo.setApplicationdate((CommonFunction.checkNull(leaddeatail1.get(37))).trim());
					leadVo.setProductType((CommonFunction.checkNull(leaddeatail1.get(38))).trim());
					leadVo.setCustomerType((CommonFunction.checkNull(leaddeatail1.get(39))).trim());
					logger.info("CustomerType::::::::::::::"+(CommonFunction.checkNull(leaddeatail1.get(39))).trim());
					if(CommonFunction.checkNull(leadVo.getCustomerType()).equalsIgnoreCase("C") ){
					if(CommonFunction.checkNull(leaddeatail1.get(40)).equals("")){
						leadVo.setTurnOver("0");
					}
						else{
					Number turnover = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(40))).trim());
					leadVo.setTurnOver(myFormatter.format(turnover));
					}
				    }else{
					if(CommonFunction.checkNull(leaddeatail1.get(40)).equals("")){
						leadVo.setSalary("0");
					}
						else{
					Number salary = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(40))).trim());
					leadVo.setSalary(myFormatter.format(salary));
					}
					}
					
					leadVo.setAddresstype((CommonFunction.checkNull(leaddeatail1.get(41))).trim());
		
					leadVo.sethGroupId((CommonFunction.checkNull(leaddeatail1.get(42))).trim());
					
					leadVo.setContitutionCode((CommonFunction.checkNull(leaddeatail1.get(43))).trim());
					leadVo.setRegistrationNo((CommonFunction.checkNull(leaddeatail1.get(44))).trim());
					leadVo.setCustPan((CommonFunction.checkNull(leaddeatail1.get(45))).trim()); // Pan no for Corporate
					leadVo.setCustPanInd((CommonFunction.checkNull(leaddeatail1.get(45))).trim()); // Pan no for Individual
					leadVo.setBusinessSegment((CommonFunction.checkNull(leaddeatail1.get(46))).trim());
					leadVo.setFirstName((CommonFunction.checkNull(leaddeatail1.get(47))).trim());
					leadVo.setLastName((CommonFunction.checkNull(leaddeatail1.get(48))).trim());
					leadVo.setCustDOB((CommonFunction.checkNull(leaddeatail1.get(49))).trim());
					leadVo.setGroupName((CommonFunction.checkNull(leaddeatail1.get(50))).trim());
					leadVo.setGroupType((CommonFunction.checkNull(leaddeatail1.get(51))).trim());
					leadVo.setGroupDesc((CommonFunction.checkNull(leaddeatail1.get(52))).trim());
					leadVo.setLoanType((CommonFunction.checkNull(leaddeatail1.get(53))).trim());
					leadVo.setOtherDetails((CommonFunction.checkNull(leaddeatail1.get(54))).trim());
					leadVo.setNoMonths((CommonFunction.checkNull(leaddeatail1.get(55))).trim());
					leadVo.setOwner((CommonFunction.checkNull(leaddeatail1.get(56))).trim());
					
					leadVo.setFatherName((CommonFunction.checkNull(leaddeatail1.get(57))).trim());
					leadVo.setPassport((CommonFunction.checkNull(leaddeatail1.get(58))).trim());
					leadVo.setDlNumber((CommonFunction.checkNull(leaddeatail1.get(59))).trim());
					leadVo.setVoterId((CommonFunction.checkNull(leaddeatail1.get(60))).trim());
					leadVo.setTahsil((CommonFunction.checkNull(leaddeatail1.get(61))).trim());
					leadVo.setSource((CommonFunction.checkNull(leaddeatail1.get(62))).trim());
					leadVo.setSourceList((CommonFunction.checkNull(leaddeatail1.get(63))).trim());
					leadVo.setDescription((CommonFunction.checkNull(leaddeatail1.get(64))).trim());
					leadVo.setLbxareaCodeVal((CommonFunction.checkNull(leaddeatail1.get(65))).trim());
					leadVo.setAreaCodename((CommonFunction.checkNull(leaddeatail1.get(66))).trim());
					leadVo.setTahsilDesc((CommonFunction.checkNull(leaddeatail1.get(67))).trim());
					leadVo.setEduDetail((CommonFunction.checkNull(leaddeatail1.get(68))).trim());
					leadVo.setSectorType((CommonFunction.checkNull(leaddeatail1.get(69))).trim());
					
					/*if(CommonFunction.checkNull(leaddeatail1.get(70)).equals("N")){
						leadVo.setCoApprelationship("New");
					}else if(CommonFunction.checkNull(leaddeatail1.get(70)).equals("Y")){
						leadVo.setCoApprelationship("Existing");
					}else{
						leadVo.setCoApprelationship("New");
					}
					leadVo.setCoAppcustomerName((CommonFunction.checkNull(leaddeatail1.get(71))).trim());
					leadVo.setCoAppaddress1((CommonFunction.checkNull(leaddeatail1.get(72))).trim());
					leadVo.setCoAppaddress2((CommonFunction.checkNull(leaddeatail1.get(73))).trim());
					leadVo.setCoAppaddress3((CommonFunction.checkNull(leaddeatail1.get(74))).trim());
					leadVo.setCoAppcountry((CommonFunction.checkNull(leaddeatail1.get(75))).trim());
					leadVo.setCoAppstate((CommonFunction.checkNull(leaddeatail1.get(76))).trim());
					leadVo.setCoAppdist((CommonFunction.checkNull(leaddeatail1.get(77))).trim());
					leadVo.setCoApppincode((CommonFunction.checkNull(leaddeatail1.get(78))).trim());
					leadVo.setCoAppphoneOff((CommonFunction.checkNull(leaddeatail1.get(79))).trim());
					leadVo.setCoAppemail(CommonFunction.checkNull(leaddeatail1.get(80)).toString());
					leadVo.setCoApptxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(81))).trim());
					leadVo.setCoApptxtStateCode((CommonFunction.checkNull(leaddeatail1.get(82))).trim());
					leadVo.setCoApptxtDistCode((CommonFunction.checkNull(leaddeatail1.get(83))).trim());
					leadVo.setCoApplandmark((CommonFunction.checkNull(leaddeatail1.get(84))).trim());	
					leadVo.setCoAppindustry((CommonFunction.checkNull(leaddeatail1.get(85))).trim());
					leadVo.setCoAppsubIndustry((CommonFunction.checkNull(leaddeatail1.get(86))).trim());	
					leadVo.setCoApplbxIndustry((CommonFunction.checkNull(leaddeatail1.get(87))).trim());
					leadVo.setCoApplbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(88))).trim());
					leadVo.setCoAppcustomerType((CommonFunction.checkNull(leaddeatail1.get(89))).trim());
					leadVo.setCoAppaddresstype((CommonFunction.checkNull(leaddeatail1.get(90))).trim());
					leadVo.setCoApphGroupId((CommonFunction.checkNull(leaddeatail1.get(91))).trim());
					leadVo.setCoAppcontitutionCode((CommonFunction.checkNull(leaddeatail1.get(92))).trim());
					leadVo.setCoAppregistrationNo((CommonFunction.checkNull(leaddeatail1.get(93))).trim());
					leadVo.setCoAppcustPan((CommonFunction.checkNull(leaddeatail1.get(94))).trim()); 
					leadVo.setCoAppcustPanInd((CommonFunction.checkNull(leaddeatail1.get(94))).trim()); 
					leadVo.setCoAppbusinessSegment((CommonFunction.checkNull(leaddeatail1.get(95))).trim());
					leadVo.setCoAppfirstName((CommonFunction.checkNull(leaddeatail1.get(96))).trim());
					leadVo.setCoApplastName((CommonFunction.checkNull(leaddeatail1.get(97))).trim());
					leadVo.setCoAppcustDOB((CommonFunction.checkNull(leaddeatail1.get(98))).trim());
					leadVo.setCoAppgroupName((CommonFunction.checkNull(leaddeatail1.get(99))).trim());
					leadVo.setCoAppgroupType((CommonFunction.checkNull(leaddeatail1.get(100))).trim());
					leadVo.setCoAppgroupDesc((CommonFunction.checkNull(leaddeatail1.get(101))).trim());
					leadVo.setCoAppfatherName((CommonFunction.checkNull(leaddeatail1.get(102))).trim());
					leadVo.setCoApppassport((CommonFunction.checkNull(leaddeatail1.get(103))).trim());
					leadVo.setCoAppdlNumber((CommonFunction.checkNull(leaddeatail1.get(104))).trim());
					leadVo.setCoAppvoterId((CommonFunction.checkNull(leaddeatail1.get(105))).trim());
					leadVo.setCoApptahsil((CommonFunction.checkNull(leaddeatail1.get(106))).trim());
					leadVo.setCoApptahsilDesc((CommonFunction.checkNull(leaddeatail1.get(107))).trim());
					
					if(CommonFunction.checkNull(leaddeatail1.get(108)).equals("N")){
						leadVo.setGaurrelationship("New");
					}else if(CommonFunction.checkNull(leaddeatail1.get(108)).equals("Y")){
						leadVo.setGaurrelationship("Existing");
					}else{
						leadVo.setGaurrelationship("New");
					}
					leadVo.setGaurcustomerName((CommonFunction.checkNull(leaddeatail1.get(109))).trim());
					leadVo.setGauraddress1((CommonFunction.checkNull(leaddeatail1.get(110))).trim());
					leadVo.setGauraddress2((CommonFunction.checkNull(leaddeatail1.get(111))).trim());
					leadVo.setGauraddress3((CommonFunction.checkNull(leaddeatail1.get(112))).trim());
					leadVo.setGaurcountry((CommonFunction.checkNull(leaddeatail1.get(113))).trim());
					leadVo.setGaurstate((CommonFunction.checkNull(leaddeatail1.get(114))).trim());
					leadVo.setGaurdist((CommonFunction.checkNull(leaddeatail1.get(115))).trim());
					leadVo.setGaurpincode((CommonFunction.checkNull(leaddeatail1.get(116))).trim());
					leadVo.setGaurphoneOff((CommonFunction.checkNull(leaddeatail1.get(117))).trim());
					leadVo.setGauremail(CommonFunction.checkNull(leaddeatail1.get(118)).toString());
					leadVo.setGaurtxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(119))).trim());
					leadVo.setGaurtxtStateCode((CommonFunction.checkNull(leaddeatail1.get(120))).trim());
					leadVo.setGaurtxtDistCode((CommonFunction.checkNull(leaddeatail1.get(121))).trim());
					leadVo.setGaurlandmark((CommonFunction.checkNull(leaddeatail1.get(122))).trim());	
					leadVo.setGaurindustry((CommonFunction.checkNull(leaddeatail1.get(123))).trim());
					leadVo.setGaursubIndustry((CommonFunction.checkNull(leaddeatail1.get(124))).trim());	
					leadVo.setGaurlbxIndustry((CommonFunction.checkNull(leaddeatail1.get(125))).trim());
					leadVo.setGaurlbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(126))).trim());
					leadVo.setGaurcustomerType((CommonFunction.checkNull(leaddeatail1.get(127))).trim());
					leadVo.setGauraddresstype((CommonFunction.checkNull(leaddeatail1.get(128))).trim());
					leadVo.setGaurhGroupId((CommonFunction.checkNull(leaddeatail1.get(129))).trim());
					leadVo.setGaurcontitutionCode((CommonFunction.checkNull(leaddeatail1.get(130))).trim());
					leadVo.setGaurregistrationNo((CommonFunction.checkNull(leaddeatail1.get(131))).trim());
					leadVo.setGaurcustPan((CommonFunction.checkNull(leaddeatail1.get(132))).trim()); 
					leadVo.setGaurcustPanInd((CommonFunction.checkNull(leaddeatail1.get(132))).trim()); 
					leadVo.setGaurbusinessSegment((CommonFunction.checkNull(leaddeatail1.get(133))).trim());
					leadVo.setGaurfirstName((CommonFunction.checkNull(leaddeatail1.get(134))).trim());
					leadVo.setGaurlastName((CommonFunction.checkNull(leaddeatail1.get(135))).trim());
					leadVo.setGaurcustDOB((CommonFunction.checkNull(leaddeatail1.get(136))).trim());
					leadVo.setGaurgroupName((CommonFunction.checkNull(leaddeatail1.get(137))).trim());
					leadVo.setGaurgroupType((CommonFunction.checkNull(leaddeatail1.get(138))).trim());
					leadVo.setGaurgroupDesc((CommonFunction.checkNull(leaddeatail1.get(139))).trim());
					leadVo.setGaurfatherName((CommonFunction.checkNull(leaddeatail1.get(140))).trim());
					leadVo.setGaurpassport((CommonFunction.checkNull(leaddeatail1.get(141))).trim());
					leadVo.setGaurdlNumber((CommonFunction.checkNull(leaddeatail1.get(142))).trim());
					leadVo.setGaurvoterId((CommonFunction.checkNull(leaddeatail1.get(143))).trim());
					leadVo.setGaurtahsil((CommonFunction.checkNull(leaddeatail1.get(144))).trim());
					leadVo.setGaurtahsilDesc((CommonFunction.checkNull(leaddeatail1.get(145))).trim());
					
					leadVo.setCoAppgenderIndividual((CommonFunction.checkNull(leaddeatail1.get(148))).trim());
					leadVo.setCoAppaadhaar((CommonFunction.checkNull(leaddeatail1.get(149))).trim());
					leadVo.setGaurgenderIndividual((CommonFunction.checkNull(leaddeatail1.get(150))).trim());
					leadVo.setGauraadhaar((CommonFunction.checkNull(leaddeatail1.get(151))).trim());*/
					//pooja changes start for Application Form No and middle name 
					leadVo.setGenderIndividual((CommonFunction.checkNull(leaddeatail1.get(70))).trim());
					leadVo.setAadhaar((CommonFunction.checkNull(leaddeatail1.get(71))).trim());
					leadVo.setApplicationFormNoRm((CommonFunction.checkNull(leaddeatail1.get(72))).trim());
					leadVo.setMiddleName((CommonFunction.checkNull(leaddeatail1.get(73))).trim());
					/*leadVo.setCoAppmiddleName((CommonFunction.checkNull(leaddeatail1.get(154))).trim());
					leadVo.setGaurmiddleName((CommonFunction.checkNull(leaddeatail1.get(155))).trim());
					*/leadVo.setLbxCustomerId(CommonFunction.checkNull(leaddeatail1.get(74)).trim());
			        /*  leadVo.setCoApplbxCustomerId(CommonFunction.checkNull(leaddeatail1.get(157)).trim());
			          leadVo.setGaurlbxCustomerId(CommonFunction.checkNull(leaddeatail1.get(158)).trim());
*/
			         /* leadVo.setAppconSubprofile(CommonFunction.checkNull(leaddeatail1.get(159)).trim());
			          leadVo.setCoAppconSubprofile(CommonFunction.checkNull(leaddeatail1.get(160)).trim());
			          leadVo.setGuarconSubprofile(CommonFunction.checkNull(leaddeatail1.get(161)).trim());*/
					leadVo.setLbxRelationship(CommonFunction.checkNull(leaddeatail1.get(75)).trim());
			        leadVo.setLbxUserSearchId(CommonFunction.checkNull(leaddeatail1.get(76)).trim());
			        leadVo.setRelationshipManager(CommonFunction.checkNull(leaddeatail1.get(77)).trim());
			        leadVo.setGeneratedUser(CommonFunction.checkNull(leaddeatail1.get(78)).trim());
			        leadVo.setDealNo(CommonFunction.checkNull(leaddeatail1.get(79)).trim());
			        leadVo.setDupDealId(CommonFunction.checkNull(leaddeatail1.get(80)).trim());
					list.add(leadVo);
									}
				leaddeatail1.clear();
				leaddeatail1=null;
			}
			leadVo=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			leadId=null;
		}
		return list;

	}

	public boolean saveLeadDetailData(Object ob,String param) {
		CreditProcessingLeadDetailDataVo leadIdVo = (CreditProcessingLeadDetailDataVo) ob;
		int maxId = 0;
		
		StringBuilder dealIdStatus = new StringBuilder();

		StringBuilder san =new StringBuilder();
		StringBuilder leadStatus =new StringBuilder();
		String esclationFlag="N";
		String roId=null;
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		//String san = "";
		san.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadIdVo.getLeadGenerator())).trim());
		//String leadStatus = "";
		try {
			if (leadIdVo.getLeadId() != null) 
			{
				StringBuilder query=new StringBuilder();
			query.append("SELECT LEAD_ID FROM cr_lead_dtl WHERE LEAD_ID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadIdVo.getLeadId()).trim()));
				dealIdStatus.append(ConnectionDAOforEJB.singleReturn(query.toString()));
				query=null;
				
				StringBuilder roIdQuery=new StringBuilder();
				roIdQuery.append("SELECT RO_ID FROM cr_lead_dtl WHERE LEAD_ID="+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadIdVo.getLeadId()).trim()));
				logger.info("roIdQuery: "+roIdQuery.toString()+" lead generator "+leadIdVo.getLeadGenerator()+" action type: "+param);
				roId=ConnectionDAOforEJB.singleReturn(roIdQuery.toString());
					roIdQuery=null;
			 }
			StringBuffer bufInsSql =	new StringBuffer();
			if (dealIdStatus != null && !dealIdStatus.equals(""))
			{
				if(param.equalsIgnoreCase("Save")){
					leadStatus.append("P");
					bufInsSql.append("update cr_lead_dtl set ");
				}else if(param.equalsIgnoreCase("Forward")){
					
					if(leadIdVo.getLeadGenerator().equalsIgnoreCase("RM") ){
						leadStatus.append("L");
						bufInsSql.append("update cr_lead_dtl set LEAD_SERVICING_RM='"+leadIdVo.getRmCode1()+"',");
					}
					else if(leadIdVo.getLeadGenerator().equalsIgnoreCase("RO") && !CommonFunction.checkNull(roId).equalsIgnoreCase("")){
			
						 String reportingToROQuery="SELECT USER_REPORTING_TO FROM SEC_USER_M U "+
						                          " WHERE U.USER_ID='"+CommonFunction.checkNull(roId)+"' AND U.REC_STATUS='A' LIMIT 1";
						 logger.info("reportingToROQuery: "+reportingToROQuery);
						 String reportingToRO=ConnectionDAOforEJB.singleReturn(reportingToROQuery);
						 leadStatus.append("L");
						 bufInsSql.append("update cr_lead_dtl set LEAD_SERVICING_RM='"+CommonFunction.checkNull(reportingToRO)+"',");
						 reportingToROQuery=null;
						}
					else{
						leadStatus.append("F");
						bufInsSql.append("update cr_lead_dtl set ");
					}
				}
				// boolean status = false;
				
				bufInsSql.append(" EXISTING_CUSTOMER=?,RELATIONSHIP_SINCE=?,CUSTOMER_NAME=?,CONTACT_PERSON=?,"
						+ "PERSON_DESIGNATION=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?,STATE=?,DISTRICT=?,PINCODE=?,PRIMARY_PHONE =?,"
						+ "ALTERNATE_PHONE =?,EMAIL_ID =?,ALTERNATE_MAIL_ID=?,PRODUCT =?,SCHEME=?,AMOUNT_REQUIRED=?,"
						+ "LOAN_TENURE=?,LOAN_PURPOSE =?,REMARK =?,SOURCE_NAME =?,SOURCE_CODE =?,"
						+ "SOURCE_EXECUTIVE_NAME =?,SOURCE_EXECUTIVE_PHONE =?,"
						+ "ALLOCATED_BRANCH=?, LEAD_GENERATION_CITY =?,REC_STATUS ='"+leadStatus+"',LANDMARK=?,NO_OF_YEARS =?,INDUSTRY_ID=?,SUB_INDUSTRY_ID=?,MAKER_ID=?," +
								"MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),CUSTOMER_TYPE=?,TURNOVER=?,ADDRESS_TYPE=?," +
						"PAN=?,BUSINESS_SEGMENT=?,GROUP_ID=?,CONSTITUTION=?,REGISTRATION_NO=?,FIRST_NAME=?,LAST_NAME=?,CUSTOMER_DOB=STR_TO_DATE(?, '"+dateFormat+"'),GROUP_TYPE=?,GROUP_DESC=?,OTHER=?,LOAN_TYPE=?,NO_OF_MONTHS=?,OWNERSHIP=?, " +
								"FATHER_HUSBAND_NAME=?,PASSPORT_NUMBER=?, DRIVING_LICENSE=?, VOTER_ID=?,  TAHSIL=?, SOURCE=?, SOURCE_DESCRIPTION=?,AREA_CODE=?,ALLOCATION_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),ESCALATION_FLAG='"+esclationFlag+"',EDU_DETAIL=?,SECTOR_TYPE=?,LEAD_RM=?,LEAD_RO=?  where LEAD_ID=? ");

				logger.info("queryUpdate: " + bufInsSql.toString());

//				if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(creditVo.getLeadGenerator())).trim().equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(creditVo.getLeadGenerator()).trim());
				
			
				if(leadIdVo.getRelationship().equalsIgnoreCase("New")){
					if ((CommonFunction.checkNull(leadIdVo.getRelationship())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(("N").trim());
				}else {
						insertPrepStmtObject.addString(("Y").trim());
				}
				
				if ((CommonFunction.checkNull(leadIdVo.getRelationshipSince())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getRelationshipSince()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getCustomerName())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getCustomerName()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getContactPerson())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getContactPerson()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getPersonDesignation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getPersonDesignation().trim()));

				if ((CommonFunction.checkNull(leadIdVo.getAddress1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getAddress1().trim()));

				if (CommonFunction.checkNull((leadIdVo.getAddress2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getAddress2()).trim());
				if (CommonFunction.checkNull((leadIdVo.getAddress3())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getAddress3()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getTxtCountryCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getTxtCountryCode()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getTxtStateCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getTxtStateCode()).trim());

				if ((CommonFunction.checkNull(leadIdVo.getTxtDistCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getTxtDistCode()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getPincode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getPincode()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getPhoneOff())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getPhoneOff()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getPhoneRes())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getPhoneRes()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getEmail()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getAltEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getAltEmail()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLbxProductID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLbxProductID()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getSchemeId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getSchemeId()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLoanAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((leadIdVo.getLoanAmount().trim())).toString());
				if ((CommonFunction.checkNull(leadIdVo.getLoanTenure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLoanTenure()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLoanPurpose())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLoanPurpose()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getRemarks()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(leadIdVo.getRemarks()).trim()));
				
				
								if (san.toString().equalsIgnoreCase("BRANCH")) {
									
									if ((CommonFunction.checkNull(leadIdVo.getBranchName1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchName1()).trim());
									
									if ((CommonFunction.checkNull(leadIdVo.getBranchCode1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchCode1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getBranchHead1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchHead1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getContactnobranch())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((	leadIdVo.getContactnobranch()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLeadgenzonebranch())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLeadgenzonebranch()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLeadgencitybranch())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLeadgencitybranch()).trim());
									
								} else if (san.toString().equalsIgnoreCase("OTHERS")) {
									
									if ((CommonFunction.checkNull(leadIdVo.getBranchName1Other())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchName1Other()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getBranchCode1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchCode1()).trim());
									
									if ((CommonFunction.checkNull(leadIdVo.getBranchHead1Other())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getBranchHead1Other()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getContactnobranchOther())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getContactnobranchOther()).trim());
									
									
									if(!CommonFunction.checkNull(roId).equalsIgnoreCase(""))
									{
										
										if ((CommonFunction.checkNull(leadIdVo.getLbxRegionID2())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject.addNull();
										else
											insertPrepStmtObject.addString((leadIdVo.getLbxRegionID2()).trim());
										
									}
									else
									{
										if ((CommonFunction.checkNull(leadIdVo.getLeadgenzonebranch())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject.addNull();
										else
											insertPrepStmtObject.addString((leadIdVo.getLeadgenzonebranch()).trim());
										
									}
																		
									if ((CommonFunction.checkNull(leadIdVo.getLeadgencitybranchOther())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLeadgencitybranchOther()).trim());
									
								}else if (san.toString().equalsIgnoreCase("VENDOR")) {
									
									if ((CommonFunction.checkNull(leadIdVo.getVendorName1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getVendorName1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getVendorCode1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getVendorCode1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getVendorHead1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getVendorHead1()).trim());
									
									if ((CommonFunction.checkNull(leadIdVo.getContactnovendor())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getContactnovendor()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLeadgenzonevendor())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLbxRegionID1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLbxRegionID1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLeadgencityvendor()).trim());
									
								} else if(san.toString().equalsIgnoreCase("RM")||san.toString().equalsIgnoreCase("RO")){
									
									if ((CommonFunction.checkNull(leadIdVo.getRmName1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getRmName1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getRmCode1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getRmCode1()).trim());
				
									if ((CommonFunction.checkNull(leadIdVo.getRmHead1())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getRmHead1()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getContactnorm())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getContactnorm()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLbxRegionID2())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLbxRegionID2()).trim());
									if ((CommonFunction.checkNull(leadIdVo.getLeadgencityrm())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getLeadgencityrm()).trim());
								}else if(!san.toString().equalsIgnoreCase("BRANCH") && !san.toString().equalsIgnoreCase("VENDOR") && !san.toString().equalsIgnoreCase("RM") && !san.toString().equalsIgnoreCase("RO")  && !san.toString().equalsIgnoreCase("OTHERS")){
									if ((CommonFunction.checkNull(leadIdVo.getDescription())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((leadIdVo.getDescription()).trim());
									
									if ((CommonFunction.checkNull("")).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(("").trim());
				
									if ((CommonFunction.checkNull("")).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(("").trim());
									if ((CommonFunction.checkNull("")).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(("").trim());
									if ((CommonFunction.checkNull("")).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(("").trim());
									if ((CommonFunction.checkNull("")).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
									insertPrepStmtObject.addString(("").trim());
								}
				if ((CommonFunction.checkNull(leadIdVo.getLandmark())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLandmark().trim()).toString());
				if ((CommonFunction.checkNull(leadIdVo.getNoOfYears())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getNoOfYears()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLbxIndustry())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLbxIndustry()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLbxSubIndustry()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLbxSubIndustry()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getMakerId()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getApplicationdate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getApplicationdate()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getCustomerType()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getCustomerType()).trim());
				
			    if(leadIdVo.getCustomerType().equalsIgnoreCase("C")){
					if ((CommonFunction.checkNull(leadIdVo.getTurnOver()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((leadIdVo.getTurnOver().trim())).toString());
				}else{
				if ((CommonFunction.checkNull(leadIdVo.getSalary()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((leadIdVo.getSalary().trim())).toString());
				}
					
				if ((CommonFunction.checkNull(leadIdVo.getAddresstype()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getAddresstype()).trim());
				if(leadIdVo.getCustomerType().equalsIgnoreCase("C")){
				if ((CommonFunction.checkNull(leadIdVo.getCustPan()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getCustPan()).trim());
				}else{
					if ((CommonFunction.checkNull(leadIdVo.getCustPanInd()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getCustPanInd()).trim());	
					
				}
						if ((CommonFunction.checkNull(leadIdVo.getBusinessSegment()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getBusinessSegment()).trim());
				if ((CommonFunction.checkNull(leadIdVo.gethGroupId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.gethGroupId()).trim());
				if(leadIdVo.getCustomerType().equalsIgnoreCase("C")){
					if ((CommonFunction.checkNull(leadIdVo.getCorconstitution()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getCorconstitution()).trim());
				}else{
					if ((CommonFunction.checkNull(leadIdVo.getIndconstitution()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getIndconstitution()).trim());
				}
				if ((CommonFunction.checkNull(leadIdVo.getRegistrationNo()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getRegistrationNo()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getFirstName()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getFirstName()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLastName()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLastName()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getCustDOB()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("00-00-0000");
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getCustDOB()).trim());
				
				if(leadIdVo.getCustomerType().equalsIgnoreCase("C")){
					if ((CommonFunction.checkNull(leadIdVo.getGroupType()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getGroupType()).trim());
					
				}else
					insertPrepStmtObject.addNull();
				
				if(leadIdVo.getCustomerType().equalsIgnoreCase("C")){
					if(leadIdVo.getGroupType().equalsIgnoreCase("N")){
						if ((CommonFunction.checkNull(leadIdVo.getGroupName1()).trim()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getGroupName1()).trim());
					}else{
						
						insertPrepStmtObject.addNull();
					}
				}else{
					insertPrepStmtObject.addNull();
				}
				//Sanjog Changes Start here
				if ((CommonFunction.checkNull(leadIdVo.getOtherDetails()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getOtherDetails()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getLoanType()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLoanType()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getNoMonths()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getNoMonths()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getOwner()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getOwner()).trim());
				
				
				if ((CommonFunction.checkNull(leadIdVo.getFatherName()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getFatherName()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getPassport()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getPassport()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getDlNumber()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getDlNumber()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getVoterId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getVoterId()).trim());
				if ((CommonFunction.checkNull(leadIdVo.getTahsil()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getTahsil()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getSource()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getSource()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getDescription()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getDescription()).trim());
				
				//Sanjog Changes Start here
				//KANIKA CHANGES
				if ((CommonFunction.checkNull(leadIdVo.getLbxareaCodeVal()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLbxareaCodeVal()).trim());
				//KANIKA CHANGES END
				
				//Anil Changes Start
				if(san.toString().equalsIgnoreCase("RM")||san.toString().equalsIgnoreCase("RO")){
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getApplicationdate()).trim());
				}else{
					insertPrepStmtObject.addNull();
				}
				
				//Anil Changes End
				if ((CommonFunction.checkNull(leadIdVo.getEduDetail()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getEduDetail()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getSectorType()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getSectorType()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getLbxRelationship()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLbxRelationship()).trim());
				
				if ((CommonFunction.checkNull(leadIdVo.getLbxUserSearchId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(leadIdVo.getLbxUserSearchId()).trim());
				
				
				if ((CommonFunction.checkNull(leadIdVo.getLeadId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((leadIdVo.getLeadId()).trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveLeadDetailData() update query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				bufInsSql=null;
				insertPrepStmtObject=null;
				
				try {
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					qryList=null;
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (status) {
					maxId = Integer.parseInt(leadIdVo.getLeadId());
					
				}

			} else {
				saveNewLead(ob);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dealIdStatus=null;
			//queryStatus=null;
			//createNew=null;
		//	leadId=null;
			san=null;
			leadStatus=null;
			ob=null;
			param=null;
			roId=null;
			
		}

		return status;

	}
	
	public String saveNewLead(Object ob)
	{
		CreditProcessingLeadDetailDataVo creditVo = (CreditProcessingLeadDetailDataVo) ob;

		logger.info("In String saveNewLead(Object ob)....  "+creditVo.getLeadGenerator());

		String leadId=null;
		String s1=null;
		String s2=null;
       	String s3=null; 
      //	String procval=null;
		
	   	ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
			
	try {
			in.add((CommonFunction.checkNull(creditVo.getLeadGenerator()).trim()));//1
		if(creditVo.getRelationship().equalsIgnoreCase("New")){
			 in.add( (CommonFunction.checkNull("N").trim()));//2
			 in.add((CommonFunction.checkNull("0").trim()));//3
		}else{
			 in.add((CommonFunction.checkNull("Y").trim()));//2
			 if(CommonFunction.checkNull(creditVo.getRelationshipSince()).trim().equalsIgnoreCase("")){
				 in.add("0");
			 }else{
			 in.add((CommonFunction.checkNull(creditVo.getRelationshipSince()).trim()));//3
			 }
	       }
			if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 in.add(CommonFunction.checkNull(creditVo.getCustomerName()).trim());//4
			}else{
			in.add((CommonFunction.checkNull(creditVo.getFirstName()))+" "+(CommonFunction.checkNull(creditVo.getMiddleName()))+" "+(CommonFunction.checkNull(creditVo.getLastName())));
			}
		 in.add((CommonFunction.checkNull(creditVo.getContactPerson()).trim()));//5
		 in.add((CommonFunction.checkNull(creditVo.getPersonDesignation()).trim()));//6
		 in.add((CommonFunction.checkNull(creditVo.getAddress1()).trim()));//7
		 in.add((CommonFunction.checkNull(creditVo.getAddress2()).trim()));//8
		 in.add((CommonFunction.checkNull(creditVo.getAddress3()).trim()));//9
		 if(CommonFunction.checkNull(creditVo.getTxtCountryCode()).equalsIgnoreCase("")){
		 in.add("1");//10
		 }else{
		 in.add((CommonFunction.checkNull(creditVo.getTxtCountryCode()).trim()));//10
		 }
		 if(CommonFunction.checkNull(creditVo.getTxtStateCode()).equalsIgnoreCase("")){
			 String q1="SELECT STATE_ID FROM COM_DISTRICT_M WHERE DISTRICT_ID='"+CommonFunction.checkNull(creditVo.getTxtDistCode())+"' and rec_status='A' limit 1 ";
			 String state=ConnectionDAO.singleReturn(q1);
		 in.add((CommonFunction.checkNull(state).trim()));//11
		 }else{
		 in.add((CommonFunction.checkNull(creditVo.getTxtStateCode()).trim()));//11
		 }
		 in.add((CommonFunction.checkNull(creditVo.getTxtDistCode()).trim()));//12
		 in.add((CommonFunction.checkNull(creditVo.getPincode()).trim()));//13
		 in.add((CommonFunction.checkNull(creditVo.getPhoneOff()).trim()));//14
		 in.add((CommonFunction.checkNull(creditVo.getPhoneRes()).trim()));//15
		 in.add((CommonFunction.checkNull(creditVo.getEmail()).trim()));//16
		 in.add((CommonFunction.checkNull(creditVo.getAltEmail()).trim()));//17
		 in.add((CommonFunction.checkNull(creditVo.getLandmark()).trim()));//18
		 if(CommonFunction.checkNull(creditVo.getNoOfYears()).trim().equalsIgnoreCase("")){
			 in.add("0");
			 in.add("0");
		 }else{
		 in.add((CommonFunction.checkNull(creditVo.getNoOfYears()).trim()));//19
		 in.add((CommonFunction.checkNull(creditVo.getNoMonths()).trim()));//20   -June---Sanjog
		 }
		

		 in.add((CommonFunction.checkNull(creditVo.getOwner()).trim()));//21     -June---Sanjog
		 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 		if(!CommonFunction.checkNull(creditVo.getLbxIndustry()).equalsIgnoreCase(""))
		 in.add((CommonFunction.checkNull(creditVo.getLbxIndustry()).trim()));//22
		 		else
		 			in.add(0);
		 	}else{
		 		in.add(0);	
		 	}
		 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 		if(!CommonFunction.checkNull(creditVo.getLbxSubIndustry()).equalsIgnoreCase(""))
		 in.add((CommonFunction.checkNull(creditVo.getLbxSubIndustry()).trim()));//23
		 		else
		 			in.add(0);
		 	}else{
		 		in.add(0);	
		 	}
		 in.add((CommonFunction.checkNull(creditVo.getLbxProductID()).trim()));//24
		 in.add((CommonFunction.checkNull(creditVo.getSchemeId()).trim()));//25
		 //pooja
		 in.add(myFormatter.parse((CommonFunction.checkNull(creditVo.getLoanAmount()).trim())).doubleValue());//26
		 in.add((CommonFunction.checkNull(creditVo.getLoanTenure()).trim()));//27
		 in.add((CommonFunction.checkNull(creditVo.getLoanPurpose()).trim()));//28
	if(creditVo.getLeadGenerator().equalsIgnoreCase("RM")||creditVo.getLeadGenerator().equalsIgnoreCase("RO")){
		
		 in.add((CommonFunction.checkNull(creditVo.getRmName1()).trim()));//29
		 in.add((CommonFunction.checkNull(creditVo.getRmCode1()).trim()));//30
		 in.add((CommonFunction.checkNull(creditVo.getRmHead1()).trim()));//31
		 in.add((CommonFunction.checkNull(creditVo.getContactnorm()).trim()));//32
		 in.add((CommonFunction.checkNull(creditVo.getLbxRegionID2()).trim()));//33
		 in.add((CommonFunction.checkNull(creditVo.getLeadgencityrm()).trim()));//34
		 in.add((CommonFunction.checkNull(creditVo.getLbxRegionID2()).trim()));//35
     }else if(creditVo.getLeadGenerator().equalsIgnoreCase("BRANCH")){
    	 in.add((CommonFunction.checkNull(creditVo.getBranchName1()).trim()));//29
    	 in.add((CommonFunction.checkNull(creditVo.getLbxBranchId()).trim()));//30
    	 in.add((CommonFunction.checkNull(creditVo.getBranchHead1()).trim()));//31
		 in.add((CommonFunction.checkNull(creditVo.getContactnobranch()).trim())); //32
		 in.add((CommonFunction.checkNull(creditVo.getLbxBranchId()).trim())); //33
		 in.add((CommonFunction.checkNull(creditVo.getLeadgencitybranch()).trim())); //34
		 in.add((0)); //35
		 
	   } else if(creditVo.getLeadGenerator().equalsIgnoreCase("OTHERS")){
		 in.add((CommonFunction.checkNull(creditVo.getBranchName1Other()).trim())); //29
		 in.add((CommonFunction.checkNull(creditVo.getLbxBranchIdOther()).trim()));  //30
		 in.add((CommonFunction.checkNull(creditVo.getBranchHead1Other()).trim()));  //31
		 in.add((CommonFunction.checkNull(creditVo.getContactnobranchOther()).trim())); //32
		 in.add((CommonFunction.checkNull(creditVo.getLbxBranchIdOther()).trim())); // 33
		 in.add((CommonFunction.checkNull(creditVo.getLeadgencitybranchOther()).trim()));  //34
		 in.add((0));  //35
		 
	   } else if(creditVo.getLeadGenerator().equalsIgnoreCase("VENDOR")){
		 in.add((CommonFunction.checkNull(creditVo.getVendorName1()).trim())); //29
		 in.add( (CommonFunction.checkNull(creditVo.getVendorCode1()).trim())); //30
		 in.add((CommonFunction.checkNull(creditVo.getVendorHead1()).trim())); //31
		 in.add((CommonFunction.checkNull(creditVo.getContactnovendor()).trim())); //32
		 in.add((CommonFunction.checkNull(creditVo.getLbxRegionID2()).trim())); //33
		 in.add((CommonFunction.checkNull(creditVo.getLeadgencityvendor()).trim())); //34
		 in.add((CommonFunction.checkNull(creditVo.getLbxRegionID1()).trim())); //35
	  }else {
		  
		 
		 in.add((CommonFunction.checkNull(creditVo.getDescription()).trim())); //29
		 in.add((CommonFunction.checkNull("0").trim())); //30
		 in.add((CommonFunction.checkNull("0").trim())); //31
		 in.add((CommonFunction.checkNull("0").trim())); //32
		 in.add((CommonFunction.checkNull(creditVo.getLbxRegionID2()).trim())); //33
		 in.add((CommonFunction.checkNull("0").trim())); //34
		 in.add((0)); //35
	   }
	

		in.add((CommonFunction.checkNull("P").trim()));//34

		in.add((CommonFunction.changeFormat(creditVo.getApplicationdate())));//35
	 	in.add((CommonFunction.checkNull(creditVo.getMakerId())));//36
	 	in.add((CommonFunction.changeFormat(creditVo.getApplicationdate())));//37
	 	in.add((CommonFunction.checkNull(creditVo.getCustomerType())));//38

	 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 	if((CommonFunction.checkNull(creditVo.getTurnOver()).equals(""))){
	 		in.add("0.00");//39
	 	}else{
	 	 in.add(myFormatter.parse((CommonFunction.checkNull(creditVo.getTurnOver()).trim())).doubleValue());
	 	}
	 	}else{
	 		if((CommonFunction.checkNull(creditVo.getSalary()).equals(""))){
		 		in.add("0.00");//39
		 	}else{
		 	 in.add(myFormatter.parse((CommonFunction.checkNull(creditVo.getSalary()).trim())).doubleValue());
		 	}	
	 	}
	 
	 	in.add((CommonFunction.checkNull(creditVo.getAddresstype())));//40

	 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 		if(creditVo.getGroupType().equalsIgnoreCase("E")){
	 			in.add((CommonFunction.checkNull(creditVo.gethGroupId())));
	 		}else{
	 			in.add(0);
	 		}
	 	}else{
	 		in.add(0);	//41
	 	}

	 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 		in.add((CommonFunction.checkNull(creditVo.getCorconstitution())));
	 	}else{
	 		in.add((CommonFunction.checkNull(creditVo.getIndconstitution())));//42
	 	}

	 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 		in.add((CommonFunction.checkNull(creditVo.getRegistrationNo())));//43
	 	}else{
	 		in.add("");	
	 	}

	 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 		in.add((CommonFunction.checkNull(creditVo.getCustPan())));//44
	 	}else{
	 		in.add((CommonFunction.checkNull(creditVo.getCustPanInd())));//44	
	 	}
	 	
		if(creditVo.getCustomerType().equalsIgnoreCase("C")){
			in.add((CommonFunction.checkNull(creditVo.getBusinessSegment())));//45
		}else{
	 		in.add("");	
	 	}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
	 	in.add((CommonFunction.checkNull(creditVo.getFirstName())));//46
		}else{
			in.add("");	
		}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getMiddleName())));//46
		}else{
				in.add("");	
		}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
	 	in.add((CommonFunction.checkNull(creditVo.getLastName())));//47
	 	if ((CommonFunction.checkNull(creditVo.getCustDOB()).trim()).equalsIgnoreCase("")){
	 		in.add("00-00-0000");
	 	}else{
	 		in.add(CommonFunction.changeFormat(creditVo.getCustDOB()));//48

	 	}
			}else{
				in.add("");	
				in.add("00-00-0000");
			}
		
		in.add((CommonFunction.checkNull(creditVo.getGroupType())));//49
		if(creditVo.getCustomerType().equalsIgnoreCase("C")){
	 		if(creditVo.getGroupType().equalsIgnoreCase("N")){
	 			in.add((CommonFunction.checkNull(creditVo.getGroupName1())));//50
	 		}else{
	 			in.add("");
	 		}
	 	}else{
	 		in.add("");	
	 	}
		
//Sanjog Changes Start 21-june-2012
		
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getFatherName())));//51
				}else{
					in.add("");	
				}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getPassport())));//52
				}else{
					in.add("");	
				}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getDlNumber())));//53
				}else{
					in.add("");	
				}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getVoterId())));//54
				}else{
					in.add("");	
				}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getTxnTahsilHID())));//55
				}else{
					in.add("");	
				}
		if(creditVo.getCustomerType().equalsIgnoreCase("I")){
		 	in.add((CommonFunction.checkNull(creditVo.getEduDetail())));//55
				}else{
					in.add("");	
				}
		
		 in.add((CommonFunction.checkNull(creditVo.getOtherDetails()).trim()));//56
		 in.add((CommonFunction.checkNull(creditVo.getLoanType()).trim()));//57
		 in.add((CommonFunction.checkNull(creditVo.getSource()).trim()));//58
		 in.add((CommonFunction.checkNull(creditVo.getDescription()).trim()));//59
		 
//Sanjog Changes End 21-june-2012
		 //KANIKA CHANGES
		 in.add((CommonFunction.checkNull(creditVo.getLbxareaCodeVal()).trim()));//60
		 //KANIKA CHANGES END
		 in.add((CommonFunction.checkNull(creditVo.getSectorType()).trim()));//61
	 	  
	    	 in.add((CommonFunction.checkNull(creditVo.getGenderIndividual()).trim()));//62
	    	 in.add((CommonFunction.checkNull(creditVo.getAadhaar()).trim()));//63
	    	 //Rohit chnages starts for CoApplicant And Gaurator
	    	 if(CommonFunction.checkNull(creditVo.getCoApprelationship()).equalsIgnoreCase("New")){
				 in.add( (CommonFunction.checkNull("N").trim()));//64	
			}else{
				 in.add((CommonFunction.checkNull("Y").trim()));//64
		       }
	    	 if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
	    		 in.add(CommonFunction.checkNull(creditVo.getCoAppcustomerName()).trim());//65
	    			}else{
	    			in.add((CommonFunction.checkNull(creditVo.getCoAppfirstName()))+" "+(CommonFunction.checkNull(creditVo.getCoAppmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getCoApplastName())));//65
	    			}
	    	 in.add(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).trim());//66
	    	 in.add((CommonFunction.checkNull(creditVo.getCoAppaddress1()).trim()));//67
			 in.add((CommonFunction.checkNull(creditVo.getCoAppaddress2()).trim()));//68
			 in.add((CommonFunction.checkNull(creditVo.getCoAppaddress3()).trim()));//69
			 if(CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim().equalsIgnoreCase("")){
				 in.add("0"); 
			 }else{
			 in.add((CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim()));//70
			 }
			 if(CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim().equalsIgnoreCase("")){
				 in.add("0"); 
			 }else{
				 in.add((CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim()));//71
			 }
			 if(CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim().equalsIgnoreCase("")){
				 in.add("0");  
			 }else{		 
			 in.add((CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim()));//72
			 }
			 if(CommonFunction.checkNull(creditVo.getCoApppincode()).trim().equalsIgnoreCase("")){
				 in.add("0");  
			 }else{
			 in.add((CommonFunction.checkNull(creditVo.getCoApppincode()).trim()));//73
			 }
			
			 in.add((CommonFunction.checkNull(creditVo.getCoAppphoneOff()).trim()));//74
			 in.add((CommonFunction.checkNull(creditVo.getCoAppemail()).trim()));//75
			 in.add((CommonFunction.checkNull(creditVo.getCoApplandmark()).trim()));//76
			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		if(!CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).equalsIgnoreCase(""))
			 in.add((CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).trim()));//77
			 		else
			 			in.add(0);
			 	}else{
			 		in.add(0);	
			 	}
			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		if(!CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).equalsIgnoreCase(""))
			 in.add((CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).trim()));//78
			 		else
			 			in.add(0);
			 	}else{
			 		in.add(0);	
			 	}
			 	in.add((CommonFunction.checkNull(creditVo.getCoAppaddresstype())));//79

			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("E")){
			 			in.add((CommonFunction.checkNull(creditVo.getCoApphGroupId())));
			 		}else{
			 			in.add(0);
			 		}
			 	}else{
			 		in.add(0);	//80
			 	}

			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		in.add((CommonFunction.checkNull(creditVo.getCoAppcorconstitution())));
			 	}else{
			 		in.add((CommonFunction.checkNull(creditVo.getCoAppindconstitution())));//81
			 	}

			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		in.add((CommonFunction.checkNull(creditVo.getCoAppregistrationNo())));//82
			 	}else{
			 		in.add("");	
			 	}

			 	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		in.add((CommonFunction.checkNull(creditVo.getCoAppcustPan())));//83
			 	}else{
			 		in.add((CommonFunction.checkNull(creditVo.getCoAppcustPanInd())));//84
			 	}
			 	
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
					in.add((CommonFunction.checkNull(creditVo.getCoAppbusinessSegment())));//85
				}else{
			 		in.add("");	
			 	}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			 	in.add((CommonFunction.checkNull(creditVo.getCoAppfirstName())));//86
				}else{
					in.add("");	
				}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoAppmiddleName())));//86
				}else{
						in.add("");	
				}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			 	in.add((CommonFunction.checkNull(creditVo.getCoApplastName())));//87
			 	if ((CommonFunction.checkNull(creditVo.getCoAppcustDOB()).trim()).equalsIgnoreCase("")){
			 		in.add("00-00-0000");
			 	}else{
			 		in.add(CommonFunction.changeFormat(creditVo.getCoAppcustDOB()));//88

			 	}
					}else{
						in.add("");	
						in.add("00-00-0000");
					}
				
				in.add((CommonFunction.checkNull(creditVo.getCoAppgroupType())));//89
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("N")){
			 			in.add((CommonFunction.checkNull(creditVo.getCoAppgroupName1())));//90
			 		}else{
			 			in.add("");
			 		}
			 	}else{
			 		in.add("");	
			 	}
				
		//Sanjog Changes Start 21-june-2012
				
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoAppfatherName())));//91
						}else{
							in.add("");	
						}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoApppassport())));//92
						}else{
							in.add("");	
						}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoAppdlNumber())));//93
						}else{
							in.add("");	
						}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoAppvoterId())));//94
						}else{
							in.add("");	
						}
				if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getCoApptxnTahsilHID())));//95
						}else{
							in.add("");	
						}
				 in.add((CommonFunction.checkNull(creditVo.getCoAppgenderIndividual()).trim()));//96
		    	 in.add((CommonFunction.checkNull(creditVo.getCoAppaadhaar()).trim()));//97
				
		//guarantor
		    	 if(CommonFunction.checkNull(creditVo.getGaurrelationship()).equalsIgnoreCase("New")){
					 in.add( (CommonFunction.checkNull("N").trim()));//98
				}else{
					 in.add((CommonFunction.checkNull("Y").trim()));//98
			       }
		    	 if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		    		 in.add(CommonFunction.checkNull(creditVo.getGaurcustomerName()).trim());//99
		    			}else{
		    			in.add((CommonFunction.checkNull(creditVo.getGaurfirstName()))+" "+(CommonFunction.checkNull(creditVo.getGaurmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getGaurlastName())));//100
		    			}
		    	 in.add(CommonFunction.checkNull(creditVo.getGaurcustomerType()).trim());//101
		    	 in.add((CommonFunction.checkNull(creditVo.getGauraddress1()).trim()));//102
				 in.add((CommonFunction.checkNull(creditVo.getGauraddress2()).trim()));//103
				 in.add((CommonFunction.checkNull(creditVo.getGauraddress3()).trim()));//104
				 if(CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim().equalsIgnoreCase("")){
					 in.add("0"); 
				 }else{
				 in.add((CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim()));//70
				 }
				 if(CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim().equalsIgnoreCase("")){
					 in.add("0"); 
				 }else{
					 in.add((CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim()));//71
				 }
				 if(CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim().equalsIgnoreCase("")){
					 in.add("0");  
				 }else{		 
				 in.add((CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim()));//72
				 }
				 if(CommonFunction.checkNull(creditVo.getGaurpincode()).trim().equalsIgnoreCase("")){
					 in.add("0");  
				 }else{
				 in.add((CommonFunction.checkNull(creditVo.getGaurpincode()).trim()));//73
				 }
			
				 in.add((CommonFunction.checkNull(creditVo.getGaurphoneOff()).trim()));//109
				 in.add((CommonFunction.checkNull(creditVo.getGauremail()).trim()));//110
				 in.add((CommonFunction.checkNull(creditVo.getGaurlandmark()).trim()));//111
				 	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		if(!CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).equalsIgnoreCase(""))
				 in.add((CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).trim()));//112
				 		else
				 		in.add(0);		
				 	}else{
				 		in.add(0);	
				 	}
				 	//if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C"))
				 		  if (CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")) {
				 		        if (CommonFunction.checkNull(creditVo.getGaurhIndustry()).trim().equalsIgnoreCase(""))
				 		        {
				 		        	if(!CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).equalsIgnoreCase(""))
				 		          in.add(CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).trim());
				 		        	else
				 		        		in.add(0);		
				 		        }
				 		        else 
				 		        	if(!CommonFunction.checkNull(creditVo.getGaurhIndustry()).equalsIgnoreCase(""))
				 		        	in.add(CommonFunction.checkNull(creditVo.getGaurhIndustry()).trim());
				 		        	else
				 		        		in.add(0);	

				 		        logger.info(new StringBuilder().append("getGaurhIndustry()---->> ").append(creditVo.getGaurhIndustry()).toString());
				 		        logger.info(new StringBuilder().append("getGaurhIndustry()---->> ").append(creditVo.getGaurlbxSubIndustry()).toString());
				 		      } else{
				 	
				 		in.add(0);	
				 	}
				 	in.add((CommonFunction.checkNull(creditVo.getGauraddresstype())));//114

				 	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		if(creditVo.getGaurgroupType().equalsIgnoreCase("E")){
				 			in.add((CommonFunction.checkNull(creditVo.getGaurhGroupId())));
				 		}else{
				 			in.add(0);
				 		}
				 	}else{
				 		in.add(0);	//115
				 	}

				 	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		in.add((CommonFunction.checkNull(creditVo.getGaurcorconstitution())));
				 	}else{
				 		in.add((CommonFunction.checkNull(creditVo.getGaurindconstitution())));//116
				 	}

				 	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		in.add((CommonFunction.checkNull(creditVo.getGaurregistrationNo())));//117
				 	}else{
				 		in.add("");	
				 	}

				 	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		in.add((CommonFunction.checkNull(creditVo.getGaurcustPan())));//118
				 	}else{
				 		in.add((CommonFunction.checkNull(creditVo.getGaurcustPanInd())));//119
				 	}
				 	
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
						in.add((CommonFunction.checkNull(creditVo.getGaurbusinessSegment())));//120
					}else{
				 		in.add("");	
				 	}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getGaurfirstName())));//121
					}else{
						in.add("");	
					}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurmiddleName())));//121
					}else{
							in.add("");	
					}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
				 	in.add((CommonFunction.checkNull(creditVo.getGaurlastName())));//122
				 	if ((CommonFunction.checkNull(creditVo.getGaurcustDOB()).trim()).equalsIgnoreCase("")){
				 		in.add("00-00-0000");
				 	}else{
				 		in.add(CommonFunction.changeFormat(creditVo.getGaurcustDOB()));//123

				 	}
						}else{
							in.add("");	
							in.add("00-00-0000");
						}
					
					in.add((CommonFunction.checkNull(creditVo.getGaurgroupType())));//124
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
				 		if(creditVo.getGaurgroupType().equalsIgnoreCase("N")){
				 			in.add((CommonFunction.checkNull(creditVo.getGaurgroupName1())));//125
				 		}else{
				 			in.add("");
				 		}
				 	}else{
				 		in.add("");	
				 	}
					
			//Sanjog Changes Start 21-june-2012
					
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurfatherName())));//126
							}else{
								in.add("");	
							}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurpassport())));//127
							}else{
								in.add("");	
							}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurdlNumber())));//128
							}else{
								in.add("");	
							}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurvoterId())));//129
							}else{
								in.add("");	
							}
					if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
					 	in.add((CommonFunction.checkNull(creditVo.getGaurtxnTahsilHID())));//130
							}else{
								in.add("");	
							}
					 in.add((CommonFunction.checkNull(creditVo.getGaurgenderIndividual()).trim()));//131
			    	 in.add((CommonFunction.checkNull(creditVo.getGauraadhaar()).trim()));//132
	    	 if(!CommonFunction.checkNull(creditVo.getLeadId()).equalsIgnoreCase("")){
	    		 in.add((CommonFunction.checkNull(creditVo.getLeadId()).trim()));//133
	    	 }else{
	    		 in.add("0");
	    	 }
	    	 if (!CommonFunction.checkNull(creditVo.getLbxCustomerId()).equalsIgnoreCase("")){
	    		 String lbxCustomerId=creditVo.getLbxCustomerId();
	    		 if(lbxCustomerId.contains("/")){
	    			 lbxCustomerId=lbxCustomerId.substring(lbxCustomerId.indexOf("/")).replace("/", "").trim();
	    			 in.add(lbxCustomerId);
	    		 }else{
	    	        in.add(CommonFunction.checkNull(creditVo.getLbxCustomerId()).trim());
	    		 }
	    	 } else {
	    	        in.add("0");//134
	    	      }
	    	   
	    	 if(!CommonFunction.checkNull(creditVo.getCoApplbxCustomerId()).equalsIgnoreCase("")){
	    		 String coApplbxCustomerId=creditVo.getCoApplbxCustomerId();
	    		 if(coApplbxCustomerId.contains("/")){
	    			 coApplbxCustomerId=coApplbxCustomerId.substring(coApplbxCustomerId.indexOf("/")).replace("/", "").trim();
	    			 in.add(coApplbxCustomerId);
	    		 }else{
	    		 in.add((CommonFunction.checkNull(creditVo.getCoApplbxCustomerId()).trim()));//135
	    		 }
	    	 }
	    	 else{
	    		 in.add("0");
	    	 }
	    		 
	    	 if(!CommonFunction.checkNull(creditVo.getGaurlbxCustomerId()).equalsIgnoreCase("")){
	    		 String gaurlbxCustomerId=creditVo.getGaurlbxCustomerId();
	    		 if(gaurlbxCustomerId.contains("/")){
	    			 gaurlbxCustomerId=gaurlbxCustomerId.substring(gaurlbxCustomerId.indexOf("/")).replace("/", "").trim();
	    			 in.add(gaurlbxCustomerId);
	    		 }else{
	    		 in.add((CommonFunction.checkNull(creditVo.getGaurlbxCustomerId()).trim()));//136
	    		 }
	    	 } else{
	    		 in.add("0");
	    	 }
	    	 if(CommonFunction.checkNull(creditVo.getCoAppStatus1()).equalsIgnoreCase("T")){
	    		 in.add("T");//137
	    	 } else{
	    		 in.add("F");
	    	 }
	    	 if(CommonFunction.checkNull(creditVo.getGaurStatus1()).equalsIgnoreCase("T")){
	    		 in.add("T");//138
	    	 } else{
	    		 in.add("F");
	    	 }
	    	 in.add((CommonFunction.checkNull(creditVo.getApplicationFormNoRm()).trim()));  //pooja change for application form no//139

	         /*in.add(CommonFunction.checkNull(creditVo.getAppconSubprofile()));
	         logger.info(new StringBuilder().append("Applicant Sub profile--->> ").append(creditVo.getAppconSubprofile()).toString());
	         in.add(CommonFunction.checkNull(creditVo.getCoAppconSubprofile()));
	         logger.info(new StringBuilder().append("Co-Applicant Sub profile--->> ").append(creditVo.getCoAppconSubprofile()).toString());
	         in.add(CommonFunction.checkNull(creditVo.getGuarconSubprofile()));
	         logger.info(new StringBuilder().append("Gaur Sub profile--->> ").append(creditVo.getGuarconSubprofile()).toString());
*/
			   out.add("");	//140
		 		 out.add("");	//141
		    	 out.add("");	//142
	    	outMessages=(ArrayList) ConnectionDAOforEJB.callSP("PRE_DEAL_GENERATOR",in,out);
	    	s1=CommonFunction.checkNull(outMessages.get(1));
	    	s2=(CommonFunction.checkNull(outMessages.get(2)));
	    	s3=(CommonFunction.checkNull(outMessages.get(0)));
	    	leadId=s3;
	    	
	    	logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        logger.info("s3: "+s3);	  
	        
	        outMessages=null;
	        
	        if(s3.equalsIgnoreCase("S"))
	        {
	        	leadId=s2;
	        }
	        else if(s3.equalsIgnoreCase("E"))
	        {
	        	leadId=s1;
	        }
	        if(!CommonFunction.checkNull(leadId).equalsIgnoreCase("")){
	        	ArrayList lis=new ArrayList();
	        	String query="update cr_lead_dtl set Lead_RM='"+creditVo.getLbxRelationship()+"' , LEAD_RO='"+creditVo.getLbxUserSearchId()+"' where lead_id='"+leadId+"'";
	        	lis.add(query);
	        	logger.info("update Query : "+query);
	        	boolean st=ConnectionDAO.sqlInsUpdDelete(lis);
	        	logger.info("update Query status : "+st);
	        	
	        	 lis=new ArrayList();
	        	 query="";
	        	 query="update cr_deal_dtl set DEAL_RM='"+creditVo.getLbxRelationship()+"' , DEAL_RO='"+creditVo.getLbxUserSearchId()+"' where lead_id='"+leadId+"'";
	        	lis.add(query);
	        	logger.info("update Query : "+query);
	        	 st=ConnectionDAO.sqlInsUpdDelete(lis);
	        	logger.info("update Query status : "+st);
	        }
	        String q1="select max(DEAL_ID) FROM CR_DEAL_DTL WHERE LEAD_ID='"+leadId+"' limit 1";
	        String dealId=ConnectionDAO.singleReturn(q1);
	        String q2="select count(1) from cr_imd_dtl where deal_id='"+dealId+"'";
	        int count=Integer.parseInt(ConnectionDAO.singleReturn(q2));
	        if(count==0){
	        	String messag=new CreditProcessingDAOImpl().callProcedure("DC",dealId);
	        }
	        DedupeReferalDTO dto= new DedupeReferalDTO();// by rohit
			dto.setTxnType("LE");// by rohit
		 	dto.setTxnId(leadId);// by rohit
			LMSMessagingClient lms =new LMSMessagingClient();
			lms.sendObjectMessage(dto,"DedupeProcessingQueue");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		finally
		{
			in.clear();
			in=null;
			out.clear();
			out=null;
			outMessages=null;
			s1=null;
			s2=null;
			s3=null;
			creditVo=null;
			ob=null;
			
		}
		 
		return leadId;
	}
	
	public ArrayList<CreditProcessingLeadDetailDataVo> getLeadDetailList() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getLeadDetailList..........................DAOImpl");
			
			StringBuilder query=new StringBuilder();
			query.append("select distinct LEAD_GENERATED_RM from cr_lead_dtl");
			
			
			logger.info("In getLeadDetailList...............query...........DAOImpl"+ query.toString());
			LeadCaptureVo noteVO = null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size=product.size();
			query=null;
			for (int i = 0; i < size; i++) {
				ArrayList data = (ArrayList) product.get(i);
				if (data.size() > 0) {
					noteVO = new LeadCaptureVo();
					noteVO.setLeadGenerator((CommonFunction.checkNull(data.get(0))).trim());
					list.add(noteVO);
				}
				data=null;
			}
			product=null;
			noteVO=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Object> getRmDetail(String leadId) {
		ArrayList<Object> list = new ArrayList<Object>();
		String san = null;
		String rmResult =null;
		
		try {
			logger.info("ArrayList<Object> getRmDetail(String leadId)...");
			
			StringBuilder rmQuery =new StringBuilder();
			StringBuilder query =null;
			rmQuery.append("SELECT b.BRANCH_DESC from cr_lead_dtl l left outer join sec_user_m u on u.USER_ID=l.SOURCE_CODE " +
				"left outer join com_branch_m b on u.USER_DEF_BRANCH = b.BRANCH_ID where source_type IN ('RM','RO') and l.LEAD_ID ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId))+"'");
			logger.info("In getRmDetail() rmQuery query is... "+rmQuery.toString());
			rmResult=(ConnectionDAOforEJB.singleReturn(rmQuery.toString()));
			
			rmQuery=null;
			
			if (!CommonFunction.checkNull(leadId).equalsIgnoreCase(""))
			{			
				query =new StringBuilder();
				query.append("SELECT SOURCE_TYPE FROM cr_lead_dtl WHERE LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId))+"'");
				logger.info("In getRmDetail() SOURCE_TYPE query is... "+query.toString());
				san = ConnectionDAOforEJB.singleReturn(query.toString());
				query=null;
			}
			
			 query=new StringBuilder();
			
			 query.append("SELECT SOURCE_NAME,SOURCE_CODE,SOURCE_EXECUTIVE_NAME ,SOURCE_EXECUTIVE_PHONE,r.BRANCH_DESC,LEAD_GENERATION_CITY,l.ALLOCATED_BRANCH ,u.USER_DEF_BRANCH " +
							" from cr_lead_dtl l left outer JOIN com_branch_m r on l.ALLOCATED_BRANCH = r.BRANCH_ID " +
							" left outer JOIN sec_user_m u on l.SOURCE_CODE = u.USER_ID "
			
							+ " where LEAD_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)) + "'");

			
			logger.info("In getRmDetail() SOURCE_NAME query is... "+query.toString());

			CreditProcessingLeadDetailDataVo leadVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size =leaddeatail.size();
			query=null;
			
			for (int i = 0; i < size; i++) 
			{

				ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
				if (leaddeatail1.size() > 0) 
				{
					leadVo = new CreditProcessingLeadDetailDataVo();
					if(san.equalsIgnoreCase("RM") || san.equalsIgnoreCase("RO")){
						
					leadVo.setRmName1((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
					leadVo.setRmCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					leadVo.setRmHead1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
					leadVo.setContactnorm((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
					leadVo.setLeadgenzonerm((CommonFunction.checkNull(rmResult)).trim());
					leadVo.setLeadgencityrm((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
					leadVo.setLbxRegionID((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
					} 
					else if(san.equalsIgnoreCase("BRANCH"))
					   {
						
						leadVo.setBranchName1((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setBranchCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setBranchHead1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnobranch((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setLeadgenzonebranch((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
						leadVo.setLeadgencitybranch((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
						leadVo.setLbxRegionID2((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
						}
					else if(san.equalsIgnoreCase("OTHERS"))
					   {
						
						leadVo.setBranchName1Other((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setBranchCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setBranchHead1Other((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnobranchOther((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setLeadgenzonebranch((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
						leadVo.setLeadgencitybranchOther((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
						leadVo.setLbxRegionID2((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
						}
					else if(san.equalsIgnoreCase("VENDOR"))
					{
							leadVo.setVendorName1((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
							leadVo.setVendorCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
							leadVo.setVendorHead1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
							leadVo.setContactnovendor((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
							leadVo.setLeadgenzonevendor((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
							leadVo.setLeadgencityvendor((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
							leadVo.setLbxRegionID1((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
							}
					else if(san.equalsIgnoreCase("OTHERS"))
					{
						leadVo.setLeadGenerator(("OTHERS").trim());							
						leadVo.setLbxBranchIdOther((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setBranchName1Other((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setBranchHead1Other((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnobranchOther((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setLeadgencitybranchOther((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
					}
					else
							leadVo.setDescription((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
								

					list.add(leadVo);
					rmResult=null;
					leaddeatail1=null;

				}
			}
			leadVo=null;
			leaddeatail=null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			san=null;
			leadId=null;
			
		}
		return list;
	}

	public boolean saveAllocation(CreditProcessingLeadDetailDataVo leadIdVo) {
		{
			CreditProcessingLeadDetailDataVo creditVo = (CreditProcessingLeadDetailDataVo) leadIdVo;

			int maxId = 0;			
			boolean status = false;
			
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			try {
				logger.info("In saveAllocation (CreditProcessingLeadDetailDataVo leadIdVo) ... ");

				StringBuilder queryUpdate=new StringBuilder();
				queryUpdate.append("update cr_lead_dtl set ALLOCATED_BRANCH = ?,LEAD_SERVICING_RM=?, REC_STATUS = 'L',ALLOCATED_BY=?,ALLOCATION_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where LEAD_ID=? ");

				logger.info("queryUpdate saveAllocation: " + queryUpdate.toString());
				
				if ((CommonFunction.checkNull(creditVo.getLbxBranchId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getLbxBranchId()).trim());

				if ((CommonFunction.checkNull(creditVo.getLbxUserId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getLbxUserId()).trim());
				
				if ((CommonFunction.checkNull(creditVo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getMakerId()).trim());
							
				
				if ((CommonFunction.checkNull(creditVo.getApplicationdate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getApplicationdate()).trim());
							
				
				if ((CommonFunction.checkNull(creditVo.getLeadId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getLeadId()).trim());
				
				

				
				insertPrepStmtObject.setSql(queryUpdate.toString());
				logger.info("IN saveAllocation() update query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				queryUpdate=null;
				insertPrepStmtObject=null;
				
				try {
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					qryList=null;
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (status) {
					maxId = Integer.parseInt(creditVo.getLeadId());
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				leadIdVo=null;
				creditVo=null;	
				
			}

			return status;

		}

	}

	public ArrayList<Object> getAllocationDetail(String leadId) {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			
			StringBuilder query=new StringBuilder();
			
		query.append("select l.ALLOCATED_BRANCH,b.BRANCH_DESC,u.USER_NAME from cr_lead_dtl l " +
					"left outer join com_branch_m b on b.BRANCH_ID = l.ALLOCATED_BRANCH " +
					"left outer join sec_user_m u on u.USER_ID = l.LEAD_SERVICING_RM "
					+ "where LEAD_ID='"	+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)) + "'");

			logger.info("getAllocationDetail" + query.toString());

			CreditProcessingLeadDetailDataVo leadVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			int size =leaddeatail.size();
			for (int i = 0; i < size; i++) {


				ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
				if (leaddeatail1.size() > 0) {

					leadVo = new CreditProcessingLeadDetailDataVo();
					leadVo.setLbxBranchId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
					leadVo.setLbxUserId((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
					leadVo.setBranchDet((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					list.add(leadVo);
				}
				leaddeatail1=null;
			}
			leaddeatail=null;
			leadVo=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			leadId=null;
		}
		return list;
	}
		
	public boolean saveTrackingDetails(CreditProcessingLeadDetailDataVo leadIdVo) {
		{
			logger.info("In update saveTrackingDetails");
			CreditProcessingLeadDetailDataVo creditVo = (CreditProcessingLeadDetailDataVo) leadIdVo;

			int maxId = 0;
			String rpTo=null;
			
			boolean status = false;
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			try {
				

				StringBuilder queryUpdate=new StringBuilder();
				StringBuffer queryForRm=new StringBuffer();

				if(!(CommonFunction.checkNull(creditVo.getLeadId())).trim().equalsIgnoreCase("")){
					String leadID=creditVo.getLeadId();
					queryForRm.append("select USER_REPORTING_TO from sec_user_m  where user_id=(select LEAD_SERVICING_RM from cr_lead_dtl where lead_id='"+leadID+"')");
				}

				logger.info("In saveTrackingDetails() USER_REPORTING_TO::::" + queryForRm.toString());			
				
				ArrayList reportingTo = ConnectionDAOforEJB.sqlSelect(queryForRm.toString());			
				queryForRm = null;
				int size=reportingTo.size();
				
				for (int i = 0; i < size; i++) {
					
					ArrayList reportingTo1 = (ArrayList) reportingTo.get(i);
					if (reportingTo.size() > 0) {
						if((creditVo.getDecision()).trim().equalsIgnoreCase("Approved")){
							rpTo=CommonFunction.checkNull(reportingTo1.get(0)).trim();
						}					
					}
					reportingTo1= null;
				}
					reportingTo=null;

				/*sachin*/
				queryUpdate.append("update cr_lead_dtl set REC_STATUS = ?, EXPECTED_LOGIN_DATE=STR_TO_DATE(?,'"+dateFormat+"')," +
									" EXPECTED_DISBURSAL_DATE=STR_TO_DATE(?,'"+dateFormat+"'),AUTHOR_ID = ?," +
									" AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) , REMARK = ?,Portfolio_mgr= ?,REJECT_REASON=?  where LEAD_ID=? ");

				logger.info("In saveTrackingDetails() queryUpdate: " + queryUpdate.toString());

			
				if((creditVo.getDecision()).trim().equalsIgnoreCase("Approved")){
					if ((CommonFunction.checkNull(creditVo.getStatus())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(("A").trim());
				}else {
					if ((CommonFunction.checkNull(creditVo.getStatus())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(("X").trim());
				}

				if ((CommonFunction.checkNull(creditVo.getExpectedLoginDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getExpectedLoginDate()).trim());
				if ((CommonFunction.checkNull(creditVo.getExpectedDisbursalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getExpectedDisbursalDate()).trim());
				
				if ((CommonFunction.checkNull(creditVo.getAuthorId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getAuthorId()).trim()); // Author_id
				
				if ((CommonFunction.checkNull(creditVo.getApplicationdate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getApplicationdate()).trim()); // Author_date
				
				if ((CommonFunction.checkNull(creditVo.getRemarks())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getRemarks()).trim());
/*sachin*/		
				if ((CommonFunction.checkNull(rpTo)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((rpTo).trim());
				
/*sachin*/				
				if ((CommonFunction.checkNull(creditVo.getReasonId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getReasonId()).trim());
				
				if ((CommonFunction.checkNull(creditVo.getLeadId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((creditVo.getLeadId()).trim());
							
				insertPrepStmtObject.setSql(queryUpdate.toString());
				logger.info("IN saveTrackingDetails() update query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				queryUpdate=null;
				insertPrepStmtObject=null;
				
				try {
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					
					qryList=null;
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (status) {
					maxId = Integer.parseInt(creditVo.getLeadId());
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				
				leadIdVo=null;
				creditVo=null;
				rpTo=null;
			}

			return status;

		}

	}

	public ArrayList<Object> getTrackingDetail(String leadId) {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			
			StringBuilder query=new StringBuilder();
			
			query.append("select country,state from cr_lead_dtl where LEAD_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)) + "'");

			logger.info("In getTrackingDetail.country.. " + query.toString());
			
			CreditProcessingLeadDetailDataVo leadVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			
			query=null;
			int size= leaddeatail.size();
			for (int i = 0; i < size; i++) {

				ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
				if (leaddeatail1.size() > 0) {

					leadVo = new CreditProcessingLeadDetailDataVo();
					leadVo.setRemarks((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
					leadVo.setDecision((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					list.add(leadVo);
				}
				leaddeatail1=null;
			}
			leadVo=null;
			leaddeatail=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			leadId=null;
		}
		return list;
	}
	
	public boolean saveLeadNotepadData(Object ob) {
		
		logger.info("In saveLeadNotepadData...");
		LeadTrackingNotepadVo vo = (LeadTrackingNotepadVo) ob;
		boolean status = false;
		String meettime=null;
		String followuptime=null;
		StringBuilder metingdateTime=new StringBuilder();
		StringBuilder followupdateTime=new StringBuilder();
		StringBuilder commonId=new StringBuilder();
		if (vo.getLeadId() != null && !vo.getLeadId().equalsIgnoreCase("")) {
			commonId.append((vo.getLeadId()));
		} else {
			commonId.append((vo.getLeadId()));
		}
		if (CommonFunction.checkNull(vo.getMeetingTime()).trim().equalsIgnoreCase(""))
			meettime="";
		else{
			 meettime=vo.getMeetingTime().substring(0, 5);
		}
		if (CommonFunction.checkNull(vo.getFollowupTime()).trim().equalsIgnoreCase(""))
			followuptime="";
		else{
			followuptime=vo.getFollowupTime().substring(0, 5);
		}
	
		
		if (vo.getFollowUp().equalsIgnoreCase("Y")) {
			metingdateTime.append(vo.getMeetingDate() + " "+ meettime);
			followupdateTime.append(vo.getFollowupDate() + " "+ followuptime);
		} else if (vo.getFollowUp().equalsIgnoreCase("N")) {
			metingdateTime.append(vo.getMeetingDate() + " "+ meettime);
		}
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("insert into cr_notepad_dtl(TXN_TYPE,TXN_ID,NOTEPAD_DATE,NOTEPAD_CODE,NOTEPAD_COMMENTS," +
							"PERSON_MET,MEETING_LOCATION,FOLLOWUP_REQD,FOLLOWUP_DATE,FOLLOWUP_REMARKS,FOLLOWUP_PERSON," +
							"FOLLOWUP_LOCATION,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); // TXN_TYPE
			bufInsSql.append(" ?,"); // TXN_ID
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormatWithTime + "'),");  // Notepad_date
																				// or
																				// in
																				// this
																				// case
																				// meeting
																				// date.
			bufInsSql.append(" ?,"); // Notepad_code
			bufInsSql.append(" ?,"); // Notepad_comments
			bufInsSql.append(" ?,"); // Person_met
			bufInsSql.append(" ?,"); // Meeting_location
			bufInsSql.append(" ?,"); // followup_reqd
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormatWithTime + "'),"); // followup_date
			bufInsSql.append(" ?,"); // followup_remarks
			bufInsSql.append(" ?,"); // followup_person
			bufInsSql.append(" ?,"); // followup_location
			bufInsSql.append(" ?,"); // maker_id
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); // maker_date

			if ((CommonFunction.checkNull(vo.getTxnType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxnType()).trim());

			if ((CommonFunction.checkNull(commonId).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((commonId.toString()).trim()); // txn_id

			if ((CommonFunction.checkNull(metingdateTime)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((
						metingdateTime.toString()).trim());

			if ((CommonFunction.checkNull(vo.getNoteCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getNoteCode()).trim());

			if ((CommonFunction.checkNull(vo.getMeetingRemarks())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMeetingRemarks()).trim());

			if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getPersonMet()).trim());

			if ((CommonFunction.checkNull(vo.getMeetingLocation())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMeetingLocation()).trim());

			if ((CommonFunction.checkNull(vo.getFollowUp())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFollowUp()).trim());

			if ((CommonFunction.checkNull(followupdateTime)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((followupdateTime.toString()).trim());

			if ((CommonFunction.checkNull(vo.getFollowupRemarks())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFollowupRemarks()).trim());
			if ((CommonFunction.checkNull(vo.getFollowUpPerson())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFollowUpPerson()).trim());
			if ((CommonFunction.checkNull(vo.getFollowUpLocation())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFollowUpLocation()).trim());
			if ((CommonFunction.checkNull(vo.getUserId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getUserId()).trim()); // maker_id
			if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim()); // maker_date

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveLeadNotepadData() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			insertPrepStmtObject=null;
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveLeadNotepadData......................" + status);
			qryList=null;
			bufInsSql=null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			 metingdateTime = null;
			 followupdateTime =null;
			 commonId = null;
			 ob=null;
			 vo=null;
			 meettime=null;
			 followuptime=null;
		}
		return status;
	}

	public ArrayList<LeadCaptureVo> getData(Object ob) {
		
		logger.info("getData(Object ob) ");
		ArrayList list = new ArrayList();
		LeadCaptureVo vo = (LeadCaptureVo) ob;
		ArrayList list1=null;
		try {
			StringBuilder query=new StringBuilder();
			 query.append("select LEAD_ID,DATE_FORMAT(LEAD_GENERATION_DATE,'"
					+ dateFormat
					+ "'),PRODUCT,CUSTOMER_NAME,BRANCH_ID from cr_lead_dtl where LEAD_ID='"
					+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob))
					+ "' || BRANCH_ID='"
					+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob))
					+ "'");
			logger.info("getData query: "+query.toString());

			list1 = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			int size=list1.size();
			for (int i = 0; i < size; i++) {

				ArrayList list2 = (ArrayList) list1.get(i);
				if (list2.size() > 0) {
					vo = new LeadCaptureVo();
					vo.setLeadno(CommonFunction.checkNull(list2.get(0)));
					vo.setGendate(CommonFunction.checkNull(list2.get(1)).toString());
					vo.setProduct(CommonFunction.checkNull(list2.get(2)).toString());
					vo.setCustomername(CommonFunction.checkNull(list2.get(3)).toString());
					vo.setBranch(CommonFunction.checkNull(list2.get(4)).toString());
					list.add(vo);
				}
				list2=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			list1=null;
			ob=null;
			vo=null;
		}
		return list;
	}

	public ArrayList getLeadNoteCode(){
		
		 ArrayList list=new ArrayList();
		try{
			logger.info("In getLeadNoteCode........");
			
		     StringBuilder query=new StringBuilder();
			
			 query.append("select gm.value, gm.description from generic_master gm,generic_master_keys gmk where gm.generic_key='note_code'and gmk.generic_key='note_code' and gm.REC_STATUS='A' and gm.value!='PD' and gm.VALUE = 'LEADFOLL'");
			logger.info(" In getLeadNoteCode query: "+query.toString());
			CreditProcessingNotepadVo noteVO=null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size=product.size();
			query=null;
			
			for(int i=0;i<size;i++){
				
				ArrayList data=(ArrayList)product.get(i);
				if(data.size()>0){
					
					noteVO = new CreditProcessingNotepadVo();
					noteVO.setNoteCodeValue((CommonFunction.checkNull(data.get(0))).trim());
					noteVO.setNoteCodeDescription((CommonFunction.checkNull(data.get(1))).trim());
					list.add(noteVO);
				}
				data=null;
			}
			product=null;
			noteVO=null;
			}catch(Exception e){
				e.printStackTrace();
			}
		return list;
	}

	public ArrayList getLeadNotepadData(String txnid,String txnType) {
		ArrayList list=new ArrayList();
			try{
				logger.info("In getLeadNotepadData....Dao Impl");
								
				StringBuilder query=new StringBuilder();
				query.append("select distinct b.description ,DATE_FORMAT(notepad_date,'"+dateFormatWithTime+"'),person_met,meeting_location,if(followup_reqd='N','NO','YES') as followup_reqd,"+
							" DATE_FORMAT(followup_date,'"+dateFormatWithTime+"'),followup_location,notepad_comments,followup_remarks,FOLLOWUP_PERSON, " +
							" s.USER_NAME,DATE_FORMAT(a.maker_date,'"+dateFormatWithTime+"') from cr_notepad_dtl a,generic_master b,sec_user_m s,cr_lead_dtl l"
							+" where notepad_code=b.value and txn_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnid)).trim()+" and TXN_TYPE in ('LP','LT') " +
									" and a.MAKER_ID=s.USER_ID and a.notepad_code!='PD'");
			
			logger.info("In getLeadNotepadData: "+query.toString());
			
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size =product.size();
			query=null;

			for(int i=0;i<size;i++){
				
				ArrayList data=(ArrayList)product.get(i);
				LeadTrackingNotepadVo noteVO= new LeadTrackingNotepadVo();
				if(data.size()>0){
					noteVO.setNoteCodeDescription((CommonFunction.checkNull(data.get(0))).trim());
     				noteVO.setMeetingDate((CommonFunction.checkNull(data.get(1))).trim());
					noteVO.setPersonMet((CommonFunction.checkNull(data.get(2))).trim());
					noteVO.setMeetingLocation((CommonFunction.checkNull(data.get(3))).trim());
					noteVO.setFollowUp((CommonFunction.checkNull(data.get(4))).trim());
					noteVO.setFollowupDate((CommonFunction.checkNull(data.get(5))).trim());
					noteVO.setFollowUpLocation((CommonFunction.checkNull(data.get(6))).trim());
					noteVO.setMeetingRemarks((CommonFunction.checkNull(data.get(7))).trim());
					noteVO.setFollowupRemarks((CommonFunction.checkNull(data.get(8))).trim());
					noteVO.setFollowUpPerson((CommonFunction.checkNull(data.get(9))).trim());
					noteVO.setUserName((CommonFunction.checkNull(data.get(10))).trim());
					noteVO.setCreationDate((CommonFunction.checkNull(data.get(11))).trim());
					list.add(noteVO);
				}
				data=null;
				noteVO=null;
			}
			product=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				txnid=null;
				txnType=null;
			}
		return list;
	}
		
	public ArrayList existingUser(CreditProcessingLeadDetailDataVo cpLeadDetailVo, String userName) {
		CreditProcessingLeadDetailDataVo cpLeadVo = (CreditProcessingLeadDetailDataVo) cpLeadDetailVo;
		ArrayList list = new ArrayList();
		logger.info(" In existingUser BranchName1...."+CommonFunction.checkNull(cpLeadVo.getBranchName1()));
		try {
			
			
			StringBuilder query=new StringBuilder();
			query.append("select d.LEVEL_ID,u.USER_ID,USER_NAME,user_phone1,USER_DEF_BRANCH,b.BRANCH_DESC " +
							"from sec_user_m u left outer join com_branch_m b on b.BRANCH_ID=u.USER_DEF_BRANCH " +
							" left outer join sec_user_level_dtl  d on  u.USER_ID=d.USER_ID "+
							"where u.USER_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(userName)) + "' and d.LEVEL_ID = 'RM' ");

			
			logger.info(" existingUser get user Detail for RM" + query.toString());
			
			CreditProcessingLeadDetailDataVo leadVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size=leaddeatail.size();
			query=null;
			
			if(size == 0){
			
			 query=new StringBuilder();
			query.append("SELECT D.LEVEL_ID,U.USER_REPORTING_TO,V.USER_NAME,V.USER_PHONE1,V.USER_DEF_BRANCH,B.BRANCH_DESC  " );
			query.append(" FROM SEC_USER_M U   " );
			query.append(" LEFT JOIN SEC_USER_M V ON V.USER_ID=U.USER_REPORTING_TO " );
			query.append(" LEFT OUTER JOIN COM_BRANCH_M B ON B.BRANCH_ID=U.USER_DEF_BRANCH");
			query.append(" LEFT OUTER JOIN SEC_USER_LEVEL_DTL  D ON  U.USER_ID=D.USER_ID ");
			query.append(" WHERE U.USER_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(userName)) + "' AND D.LEVEL_ID = 'RO' ");

			
			logger.info(" existingUser get user Detail for RO " + query.toString());
			
			 leadVo = null;
			 leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			 size=leaddeatail.size();
			logger.info("existingUser user Detail size: " + size);
			
			query=null;
			
			if(size == 0){
				StringBuilder queryDSA=new StringBuilder();
				
				queryDSA.append("select cddm.Dealer_id,cddm.DEALER_DESC,su.USER_NAME from cr_dsa_dealer_m cddm " +
								"left outer join cr_dsa_dealer_user_m ddum on ddum.DEALER_ID=cddm.DEALER_ID " +
								"left join sec_user_m su on ddum.USER_ID=su.USER_ID " +
								"where bp_type='SU' and cddm.REC_STATUS<>'X' and ddum.USER_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(userName))+"'");
		
				logger.info("existingUser get from cr-dsa-dealer-master" + queryDSA.toString());
				leaddeatail = ConnectionDAOforEJB.sqlSelect(queryDSA.toString());
				queryDSA=null;
				size=leaddeatail.size();
				if(size== 0){
					StringBuilder queryTelecaller=new StringBuilder();
					queryTelecaller.append("select d.LEVEL_ID,u.USER_DEF_BRANCH,u.USER_ID,USER_NAME,user_phone1 " +
							"from sec_user_m u left outer join com_branch_m b on b.BRANCH_ID=u.USER_DEF_BRANCH " +
							" left outer join sec_user_level_dtl  d on  u.USER_ID=d.USER_ID "+
							"where u.USER_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(userName)) + "' and d.LEVEL_ID='BRANCH'");
					logger.info(" existingUser get from user-master......................................." + queryTelecaller.toString());
					leaddeatail = ConnectionDAOforEJB.sqlSelect(queryTelecaller.toString());
					queryTelecaller=null;
					 size=leaddeatail.size();
					if(size== 0){
						StringBuilder queryOthers=new StringBuilder();
						queryOthers.append("select USER_DEF_BRANCH,b.BRANCH_DESC,USER_NAME,user_phone1 " +
								"from sec_user_m u left outer join com_branch_m b on b.BRANCH_ID=u.USER_DEF_BRANCH " +
								"where USER_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(userName)) + "'");
						logger.info(" existingUser get from user-master..................." + queryOthers.toString());
						leaddeatail = ConnectionDAOforEJB.sqlSelect(queryOthers.toString());
						size=leaddeatail.size();
						queryOthers=null;
						for (int i = 0; i < size; i++) {
							ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
							if (leaddeatail1.size() > 0) {
						leadVo = new CreditProcessingLeadDetailDataVo();
						leadVo.setLeadGenerator(("OTHERS").trim());							
						leadVo.setLbxBranchIdOther((CommonFunction.checkNull(cpLeadVo.getBranchCode1())).trim());
						leadVo.setBranchName1Other((CommonFunction.checkNull(cpLeadVo.getBranchName1())).trim());
						leadVo.setBranchHead1Other((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnobranchOther((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
								
						list.add(leadVo);
							}
							leaddeatail1=null;
							leadVo=null;
						}
						leaddeatail=null;
					}
					else {
						for (int i = 0; i < size; i++) {
						ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
						if (leaddeatail1.size() > 0) {
							leadVo = new CreditProcessingLeadDetailDataVo();
							leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(0))).trim());	
							leadVo.setBranchCode1((CommonFunction.checkNull(cpLeadVo.getBranchCode1())).trim());
							leadVo.setBranchName1((CommonFunction.checkNull(cpLeadVo.getBranchName1())).trim());
							leadVo.setLbxbranchHead1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
							leadVo.setBranchHead1((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
							leadVo.setContactnobranch((CommonFunction.checkNull(leaddeatail1.get(4))).trim());

							list.add(leadVo);
						}
						leaddeatail1=null;
						leadVo=null;
						}
						leaddeatail=null;
				}
				}
				else{					
				for (int i = 0; i < size; i++) {
					
					ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
					if (leaddeatail1.size() > 0) {
						
						leadVo = new CreditProcessingLeadDetailDataVo();
						leadVo.setVendorCode1((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setVendorName1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setVendorHead1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setLeadGenerator(("VENDOR").trim());
						list.add(leadVo);
					}
					leaddeatail1=null;
					leadVo=null;
				}
				leaddeatail=null;
				}
				
			}else {	
				
				for (int i = 0; i < size; i++) {
	
					ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
					if (leaddeatail1.size() > 0) {
						leadVo = new CreditProcessingLeadDetailDataVo();
						
						leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setRmCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setRmName1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnorm((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setLbxRegionID((CommonFunction.checkNull(cpLeadVo.getBranchCode1())).trim());
						leadVo.setLeadgenzonerm((CommonFunction.checkNull(cpLeadVo.getBranchName1())).trim());
						list.add(leadVo);
					}
					leaddeatail1=null;
					leadVo=null;
				}
				leaddeatail=null;
			   }
			}
            else {	
				
				for (int i = 0; i < size; i++) {
	
					ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
					if (leaddeatail1.size() > 0) {
						leadVo = new CreditProcessingLeadDetailDataVo();
						
						leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.setRmCode1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setRmName1((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setContactnorm((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setLbxRegionID((CommonFunction.checkNull(cpLeadVo.getBranchCode1())).trim());
						leadVo.setLeadgenzonerm((CommonFunction.checkNull(cpLeadVo.getBranchName1())).trim());
						list.add(leadVo);
					}
					leaddeatail1=null;
					leadVo=null;
				}
				leaddeatail=null;
			}
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
		
			cpLeadDetailVo=null;
			userName=null;
			cpLeadVo=null;
		}
		return list;
	}
	
	public ArrayList<Object> getMaxLeadId(String userName) {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
			LeadTrackingNotepadVo noteVO= new LeadTrackingNotepadVo();
			
			query.append("select MAX(TXN_ID)+1 from cr_notepad_dtl");
			String leaddeatail = ConnectionDAOforEJB.singleReturn(query.toString());
			
			noteVO.setTxnId(CommonFunction.checkNull(leaddeatail));
			list.add(noteVO);
			query=null;
			noteVO=null;
			leaddeatail=null;			

		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			userName=null;
		}
		return list;
	}
		
	public ArrayList getCommonLeadData(LeadCaptureVo ob,String tracking) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		StringBuilder leadno=new StringBuilder();
		StringBuilder product=new StringBuilder();
		StringBuilder schemeId=new StringBuilder();
		StringBuilder customername=new StringBuilder();
		StringBuilder branch=new StringBuilder();
		StringBuilder rmname=new StringBuilder();
		StringBuilder vendor=new StringBuilder();
		StringBuilder status=new StringBuilder();
		StringBuilder gendate=new StringBuilder();

		ArrayList header = new ArrayList();
		ArrayList list = new ArrayList();

		boolean appendSQL = false;
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTmp =  new StringBuffer();		

		try {
			logger.info("In getCommonLeadData(LeadCaptureVo ob,String tracking) ... ");
			
			leadno.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim());
			product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getProduct())).trim());
			schemeId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim());
			customername.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim());
			branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim());
			rmname.append( StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim());
			gendate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim());
			status.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim());
			
			bufInsSql.append("select LEAD_ID,DATE_FORMAT(LEAD_GENERATION_DATE,'" +dateFormat+ "'),p.PRODUCT_DESC,CUSTOMER_NAME," +
							"bb.BRANCH_DESC,sm.SCHEME_DESC,LEAD_GENERATED_RM,SOURCE_TYPE,bm.BRANCH_DESC,CASE l.REC_STATUS WHEN 'L' THEN 'NOT INITIATED' WHEN 'T' THEN 'INITIATED' END AS REC_STATUS,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=L.MAKER_ID) MAKER_ID,CUSTOMER_TYPE ,concat(FIRST_NAME,' ',LAST_NAME),LOAN_TYPE,(select DATE_FORMAT(followup_date,'" +dateFormat+ "') from cr_notepad_dtl np where np.txn_id=l.LEAD_ID order by notepad_id desc limit 1) followup_date,ESCALATION_FLAG   from cr_lead_dtl l  " +
							"inner join com_branch_m bb on l.BRANCH_ID=bb.BRANCH_ID  " +
							"inner join cr_scheme_m sm on sm.SCHEME_ID=l.SCHEME " +
							"left outer join com_branch_m bm on l.ALLOCATED_BRANCH=bm.BRANCH_ID " +
							"inner join cr_product_m p on p.PRODUCT_ID=l.PRODUCT ");

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno()).trim()).equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId()).trim()).equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId()).trim()).equalsIgnoreCase(""))) 
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("")))){
					bufInsSql.append("WHERE DATE_FORMAT(LEAD_GENERATION_DATE) =STR_TO_DATE('"
									+ StringEscapeUtils.escapeSql(ob.getApplicationdate()).trim()
									+ "','"
									+ dateFormat
									+ "'"
									+ " and PRODUCT='"
									+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim()
									+ "'"
									+ " and CUSTOMER_NAME like'%"
									+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim()
									+ "%'"
									+ " AND SOURCE_CODE='"
									+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()
									+ "' "
									+ " and SCHEME='"
									+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "'"
									+ " and SOURCE_TYPE like'%"
									+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() +"%'"
									+ "and l.REC_STATUS ="+ StringEscapeUtils.escapeSql(ob.getStatus()).trim());
					
				}
				
				if (((ob.getLeadno().equalsIgnoreCase("")))
						|| ((ob.getApplicationdate().equalsIgnoreCase("")))
						|| ((ob.getLbxProductID().equalsIgnoreCase("")))
						|| ((ob.getCustomername().equalsIgnoreCase("")))
						|| ((ob.getLbxBranchId().equalsIgnoreCase("")))
						|| ((ob.getSchemeId().equalsIgnoreCase("")))
						|| ((ob.getLeadGenerator().equalsIgnoreCase("")))) {
					appendSQL = true;
				}
				bufInsSqlTmp.append("SELECT COUNT(1) FROM cr_lead_dtl ");
				if (appendSQL) {
					
					bufInsSql.append(" WHERE ");
					bufInsSqlTmp.append(" WHERE ");
				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
					bufInsSqlTmp.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
					appendSQL = true;

				}
				
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" PRODUCT='"+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim() + "' AND");
					bufInsSqlTmp.append(" PRODUCT='"+ StringEscapeUtils.escapeSql(ob.getLbxProductID()).trim() + "' AND");
					appendSQL = true;

				}
				
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" SCHEME='"+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "' AND");
					bufInsSqlTmp.append(" SCHEME='"+ StringEscapeUtils.escapeSql(ob.getSchemeId()).trim() + "' AND");
					appendSQL = true;

				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim() + "%' AND");
					bufInsSqlTmp.append(" CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(ob.getCustomername()).trim() + "%' AND");
					appendSQL = true;

				}
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" SOURCE_CODE='"+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()+ "' AND");
					bufInsSqlTmp.append(" SOURCE_CODE='"+ StringEscapeUtils.escapeSql(ob.getLbxBranchId()).trim()+ "' AND");
					appendSQL = true;

				}
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" DATE(LEAD_GENERATION_DATE) =STR_TO_DATE('"+ StringEscapeUtils.escapeSql(ob.getApplicationdate())
									.trim() + "','" + dateFormat + "') AND");
					bufInsSqlTmp.append(" DATE(LEAD_GENERATION_DATE) =STR_TO_DATE('"+ StringEscapeUtils.escapeSql(ob.getApplicationdate())
							.trim() + "','" + dateFormat + "') AND");
					appendSQL = true;
				}
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" SOURCE_TYPE like '%"+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() + "%' AND ");
					bufInsSqlTmp.append(" SOURCE_TYPE like '%"+ StringEscapeUtils.escapeSql(ob.getLeadGenerator()).trim() + "%' AND ");
					appendSQL = true;

				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("")))) {

					if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("L")){

						bufInsSql.append(" l.REC_STATUS IN('L','T') ");
						bufInsSqlTmp.append(" REC_STATUS IN('L','T') ");	
					}else{
						
					/*bufInsSql.append(" l.REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' ");
					bufInsSqlTmp.append(" l.REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' ");*/
					
					bufInsSql.append(" l.REC_STATUS ='A' and not exists (select ifnull(lead_id,'0') from cr_deal_dtl where lead_id=l.lead_id and rec_status is not null ) ");
					bufInsSqlTmp.append(" REC_STATUS ='A' and not exists (select ifnull(lead_id,'0') from cr_deal_dtl where lead_id=cr_lead_dtl.lead_id  and rec_status is not null ) ");
				          }
					appendSQL = true;

				}
				if(status.toString().equalsIgnoreCase("F")){
					if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
						
						/*bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");*/
						bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						appendSQL = true;

					}
					else {
						
							bufInsSql.append(" AND L.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
							bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						}
					}
					
				if(status.toString().equalsIgnoreCase("L") || status.toString().equalsIgnoreCase("T")){
					if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
						
						bufInsSql.append(" AND L.LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");
						bufInsSql.append(" AND l.ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						appendSQL = true;

					}
					else {
						bufInsSql.append("AND ((ESCALATION_FLAG='N' AND L.LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') OR (L.LEAD_SERVICING_RM IN (SELECT USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') AND ESCALATION_FLAG='Y') OR (RO_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"')) ");	
						bufInsSqlTmp.append("AND ((ESCALATION_FLAG='N' AND LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') OR (LEAD_SERVICING_RM IN (SELECT USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') AND ESCALATION_FLAG='Y') OR (RO_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"')) ");
						bufInsSql.append(" AND l.ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						}
					}
				
				if(status.toString().equalsIgnoreCase("P")){
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
					
					/*bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");
					*/bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
					appendSQL = true;

				}
				else{
					/*bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' ");
					*/bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
					appendSQL = true;

				}
				}

		
			if (appendSQL) 
			{

				StringBuilder tmp=new StringBuilder();
				tmp.append(bufInsSql.toString());
				tmp.append(" order by  l.REC_STATUS,followup_date,LEAD_ID ");
				StringBuilder bufInsSqlTemp=new StringBuilder();
				
				bufInsSqlTemp.append(bufInsSqlTmp.toString());
				
				
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3)) 
				{ 
					
					tmp.append((tmp).substring(0, (tmp.length() - 4)));
					bufInsSqlTemp.append((bufInsSqlTemp).substring(0, (bufInsSqlTemp.length() - 4)));
					logger.info(""+tmp.toString());
					header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
					
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp.toString()));
				} else {
							logger.info(""+tmp.toString());
							header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
							count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp.toString()));
						}
				
				tmp=null;
				bufInsSqlTemp=null;
			
				}
			else {
					logger.info("search Query...else-------." + bufInsSql.toString());
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTmp.toString());
		            
					
					/*if((leadno.toString().trim()==null && product.toString().trim()==null && schemeId.toString().trim()==null && customername.toString().trim()==null && branch.toString().trim()==null && rmname.toString().trim()==null && vendor.toString().trim()==null && gendate.toString().trim()==null) 
						|| (leadno.toString().trim().equalsIgnoreCase("") && product.toString().trim().equalsIgnoreCase("") && schemeId.toString().trim().equalsIgnoreCase("") && customername.toString().trim().equalsIgnoreCase("") && branch.toString().trim().equalsIgnoreCase("") && rmname.toString().trim().equalsIgnoreCase("") && vendor.toString().trim().equalsIgnoreCase("") && gendate.toString().trim().equalsIgnoreCase("")) || ob.getCurrentPageLink()>1)
					{*/
					
					logger.info("current PAge Link no .................... "+ob.getCurrentPageLink());
					if(ob.getCurrentPageLink()>1)
					{
						startRecordIndex = (ob.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					
					//}
					header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
	
				}

			bufInsSql=null;
			bufInsSqlTmp=null;
			int size=header.size();
			LeadCaptureVo noteVo = null;
				for (int i = 0; i < size; i++) {
					ArrayList data = (ArrayList) header.get(i);
					if (data.size() > 0) {
						noteVo = new LeadCaptureVo();
						noteVo.setLeadno("<a href=preDealCapturingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
						noteVo.setApplicationdate((CommonFunction.checkNull(data.get(1))).trim());
						noteVo.setProduct((CommonFunction.checkNull(data.get(2))).trim());
						if((CommonFunction.checkNull(data.get(11)).trim()).equalsIgnoreCase("C")){
						noteVo.setCustomername((CommonFunction.checkNull(data.get(3))).trim());
				
						}else{
							noteVo.setCustomername((CommonFunction.checkNull(data.get(12))).trim());
						}
						noteVo.setScheme((CommonFunction.checkNull(data.get(5))).trim());
						noteVo.setLeadGenerator((CommonFunction.checkNull(data.get(7))).trim());
						noteVo.setReportingToUserId((CommonFunction.checkNull(data.get(10))).trim());
						if((CommonFunction.checkNull(data.get(13))).trim().equalsIgnoreCase("NEW")){
							noteVo.setLoanType("NEW LOAN");
						}else if((CommonFunction.checkNull(data.get(13))).trim().equalsIgnoreCase("LOAN")){
							noteVo.setLoanType("REFINANCE LOAN");
						}else if((CommonFunction.checkNull(data.get(13))).trim().equalsIgnoreCase("USED")){
							noteVo.setLoanType("OLD LOAN");
						}
						noteVo.setStatus((CommonFunction.checkNull(data.get(9))).trim());
						noteVo.setFollowupDate((CommonFunction.checkNull(data.get(14))).trim());	
						noteVo.setTotalRecordSize(count);
						list.add(noteVo);
						
					}
					data=null;
					noteVo=null;

				}
				header=null;
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		leadno = null;
		 product = null;
		 schemeId = null;
		 customername = null;
		 branch = null;
		 rmname = null;
		 vendor = null;
		 gendate =null;
		 status =null;
		 ob=null;
		 tracking=null;
		}

		return list;
	}
	public ArrayList getCommonLeadDatafromjsp(LeadCaptureVo ob, String tracking) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		StringBuilder leadno=new StringBuilder();
		StringBuilder product=new StringBuilder();
		StringBuilder schemeId=new StringBuilder();
		StringBuilder customername=new StringBuilder();
		StringBuilder branch=new StringBuilder();
		StringBuilder rmname=new StringBuilder();
		StringBuilder vendor=new StringBuilder();
		StringBuilder status=new StringBuilder();
		StringBuilder gendate=new StringBuilder();
		
		logger.info("In getCommonLeadDatafromjsp.. "+ob.getReportingToUserId());
		
			
		leadno.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim());
		product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getProduct())).trim());
		schemeId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId())).trim());
		customername.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim());
		branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId())).trim());
		rmname.append( StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim());
		gendate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim());
		status.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim());
		
		LeadCaptureVo LeadCaptureVo = (LeadCaptureVo) ob;
		ArrayList header = new ArrayList();
		ArrayList list = new ArrayList();

		try {

				boolean appendSQL = false;
				StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSqlTmp =  new StringBuffer();
				bufInsSql.append("select LEAD_ID,DATE_FORMAT(LEAD_GENERATION_DATE,'" +dateFormat+ "'),p.PRODUCT_DESC,CUSTOMER_NAME," +
								"bb.BRANCH_DESC,sm.SCHEME_DESC,LEAD_GENERATED_RM,SOURCE_TYPE,bm.BRANCH_DESC,l.REC_STATUS,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=L.MAKER_ID) MAKER_ID   from cr_lead_dtl l  " +
								"left outer join com_branch_m bb on l.SOURCE_CODE=bb.BRANCH_ID  " +
								"left outer join cr_scheme_m sm on sm.SCHEME_ID=l.SCHEME " +
								"left outer join com_branch_m bm on l.ALLOCATED_BRANCH=bm.BRANCH_ID " +
								"left outer join cr_product_m p on p.PRODUCT_ID=l.PRODUCT ");

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno()).trim()).equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getApplicationdate())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxProductID())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getCustomername())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLbxBranchId()).trim()).equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getSchemeId()).trim()).equalsIgnoreCase(""))) 
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadGenerator())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("")))){
					bufInsSql.append(" WHERE l.REC_STATUS ="+ StringEscapeUtils.escapeSql(ob.getStatus()).trim());
				}
				if (((ob.getLeadno().equalsIgnoreCase("")))
						|| ((ob.getApplicationdate().equalsIgnoreCase("")))
						|| ((ob.getLbxProductID().equalsIgnoreCase("")))
						|| ((ob.getCustomername().equalsIgnoreCase("")))
						|| ((ob.getLbxBranchId().equalsIgnoreCase("")))
						|| ((ob.getSchemeId().equalsIgnoreCase("")))
						|| ((ob.getLeadGenerator().equalsIgnoreCase("")))) {
					appendSQL = true;
				}
				bufInsSqlTmp.append("SELECT COUNT(1) FROM cr_lead_dtl ");
				if (appendSQL) {
					bufInsSql.append(" WHERE ");
					bufInsSqlTmp.append(" WHERE ");
				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLeadno())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
					bufInsSqlTmp.append(" LEAD_ID='"+ StringEscapeUtils.escapeSql(ob.getLeadno()).trim()+ "' AND");
					appendSQL = true;

				}
				
				

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("")))) {
					if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getStatus())).trim().equalsIgnoreCase("L")){
						bufInsSql.append(" l.REC_STATUS IN('L','T') ");
						bufInsSqlTmp.append(" REC_STATUS IN('L','T') ");	
					}else{
						bufInsSql.append(" l.REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' ");
						bufInsSqlTmp.append(" REC_STATUS ='"+ StringEscapeUtils.escapeSql(ob.getStatus()).trim() + "' ");
				          }
					appendSQL = true;
				}
				if(status.toString().equalsIgnoreCase("F")){
					if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
						bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");
						bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						appendSQL = true;
					}
					else {
						
							bufInsSql.append(" AND L.MAKER_ID!='"+ob.getUserId()+"'");
							bufInsSqlTmp.append(" AND MAKER_ID!='"+ob.getUserId()+"'");
							bufInsSql.append(" AND L.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
							bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						}
					}
					
				if(status.toString().equalsIgnoreCase("L") || status.toString().equalsIgnoreCase("T")){
					if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
						bufInsSql.append(" AND L.LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");
						bufInsSql.append(" AND l.ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
						bufInsSqlTmp.append(" AND ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						appendSQL = true;
					}
					else {
						bufInsSql.append(" AND L.LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' OR  L.LEAD_SERVICING_RM IN (SELECT USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') AND ESCALATION_FLAG='Y' ");	
						bufInsSqlTmp.append(" AND LEAD_SERVICING_RM='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' OR  LEAD_SERVICING_RM IN (SELECT USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"') AND ESCALATION_FLAG='Y' ");
						
							bufInsSql.append(" AND l.ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
							bufInsSqlTmp.append(" AND ALLOCATED_BRANCH='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
						}
					}
				
				if(status.toString().equalsIgnoreCase("P")){
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getReportingToUserId()).trim()+"' ");
					bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
					appendSQL = true;
				}
				else{
					bufInsSql.append(" AND l.MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(ob.getUserId()).trim()+"' ");
					bufInsSql.append(" AND l.BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");	
					bufInsSqlTmp.append(" AND BRANCH_ID='"+StringEscapeUtils.escapeSql(ob.getBranchId()).trim()+"' ");
					appendSQL = true;
				}
				}
		
			if (appendSQL) 
			{
				StringBuilder tmp=new StringBuilder();
				 tmp.append(bufInsSql.toString());
				 
					StringBuilder bufInsSqlTemp=new StringBuilder();
			       bufInsSqlTemp.append(bufInsSqlTmp.toString());
				
				logger.info("In appendSQL true----  in check index Of"+ tmp.lastIndexOf("AND") + "------"+ (tmp.length() - 3));
				
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3)) 
				{ 
					
				
					tmp.append((tmp).substring(0, (tmp.length() - 4)));
					bufInsSqlTemp.append((bufInsSqlTemp).substring(0, (bufInsSqlTemp.length() - 4)));
					
					logger.info("fetchDealDetail Query...tmp." + tmp.toString());
					
					header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp.toString()));
				} else {
							logger.info("search Query...tmp.in else" + bufInsSqlTemp.toString());
							count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp.toString()));
							header = ConnectionDAOforEJB.sqlSelect(tmp.toString());
							
						}
				tmp=null;
				bufInsSqlTemp=null;
			
				}
			else {
				logger.info("search Query...else-------." + bufInsSql.toString());
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTmp.toString());
		            
			
					logger.info("current PAge Link no .................... "+LeadCaptureVo.getCurrentPageLink());
					if(LeadCaptureVo.getCurrentPageLink()>1)
					{
						startRecordIndex = (LeadCaptureVo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					
					}
					header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
	
			bufInsSql=null;
			bufInsSqlTmp=null;
			int size=header.size();
			LeadCaptureVo noteVo = null;
				for (int i = 0; i < size; i++) {
					ArrayList data = (ArrayList) header.get(i);
					if (data.size() > 0) {
						noteVo = new LeadCaptureVo();
						if(tracking.equalsIgnoreCase("3000116")){
							noteVo.setLeadno("<a href=leadTrackingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");

						}else{
							noteVo.setLeadno("<a href=leadCapturingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");

						}
						
						noteVo.setApplicationdate((CommonFunction.checkNull(data.get(1))).trim());
						noteVo.setProduct((CommonFunction.checkNull(data.get(2))).trim());
						noteVo.setCustomername((CommonFunction.checkNull(data.get(3))).trim());
				
				
						noteVo.setScheme((CommonFunction.checkNull(data.get(5))).trim());
						noteVo.setLeadGenerator((CommonFunction.checkNull(data.get(7))).trim());
						noteVo.setReportingToUserId((CommonFunction.checkNull(data.get(10))).trim());
						noteVo.setTotalRecordSize(count);
						list.add(noteVo);
					}
					data=null;
					noteVo=null;
				}
				header=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
		leadno = null;
		 product = null;
		 schemeId = null;
		 customername = null;
		 branch = null;
		rmname = null;
		 vendor = null;
		 gendate =null;
		 status =null;
		 LeadCaptureVo=null;
		 ob=null;
		 tracking=null;

		}

		return list;
	}

	
	public ArrayList<CreditProcessingLeadDetailDataVo> getSourceDetailList(String source) {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getSourceDetailList(String source).");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT M.VALUE,M.DESCRIPTION FROM generic_master M,generic_master_keys K WHERE K.GENERIC_KEY='SOURCE_TYPE' " +
						"AND K.GENERIC_KEY=M.GENERIC_KEY and m.REC_STATUS='A' and M.VALUE not like 'VENDOR' and M.VALUE not like 'BRANCH'");
			logger.info("In getSourceDetailList(String source)...."+ query.toString());
			LeadCaptureVo noteVO = null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query.toString());
			query=null;
			int size=product.size();
			for (int i = 0; i < size; i++) {
				ArrayList data = (ArrayList) product.get(i);
				if (data.size() > 0) {
					noteVO = new LeadCaptureVo();
					noteVO.setId((CommonFunction.checkNull(data.get(0))).trim());
					noteVO.setName((CommonFunction.checkNull(data.get(1))).trim());
					list.add(noteVO);
				}
				data=null;
				noteVO=null;
			}
			product=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			source=null;
		}
		return list;
	}
	public ArrayList<CreditProcessingLeadDetailDataVo> defaultcountry() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In defaultcountry()........");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
			logger.info("In defaultcountry...............query...........DAOImpl"+ query.toString());
			CreditProcessingLeadDetailDataVo vo = null;
			ArrayList country = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			int size=country.size();
			
			for (int i = 0; i < size; i++) {
				ArrayList data = (ArrayList) country.get(i);
				if (data.size() > 0) {
					vo = new CreditProcessingLeadDetailDataVo();
					vo.setDefaultcountryid((CommonFunction.checkNull(data.get(0))).trim());
					vo.setDefaultcountryname((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}
				data=null;
				vo=null;
			}
			country=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public boolean deletelead(String id) {
		boolean status=false;
		ArrayList list=new ArrayList();
		StringBuilder query=new StringBuilder();
		boolean status1=false;
		ArrayList list1=new ArrayList();
		StringBuilder query1=new StringBuilder();
		logger.info("In deletelead(String id): ");
		try{
		/*query.append ("delete from cr_lead_dtl where LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and REC_STATUS='P'");*/
		query.append ("delete from cr_lead_dtl where LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
		list.add(query.toString());
		logger.info("deletelead()query------------------" + query.toString());
		status =ConnectionDAOforEJB.sqlInsUpdDelete(list);
		
		query1.append ("delete from cr_lead_customer_m where LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and REC_STATUS='P'");
		list1.add(query1.toString());
		logger.info("deletelead()query------------------" + query1.toString());
		status1 =ConnectionDAOforEJB.sqlInsUpdDelete(list1);
		query=null;
		list=null;
		query1=null;
		list1=null;
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  finally{
		  id=null;
	  }
	  return status;
	
	}
	public ArrayList<CreditProcessingLeadDetailDataVo> addresstype() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In addresstype()........");
			StringBuilder query=new StringBuilder();
			
			 query.append("select  VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'");
			logger.info("In addresstype()...............query...........DAOImpl"+ query.toString());
			CreditProcessingLeadDetailDataVo vo = null;
			ArrayList address = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			int size=address.size();
			
			for (int i = 0; i < size; i++) {
				ArrayList data = (ArrayList) address.get(i);
				if (data.size() > 0) {
					vo = new CreditProcessingLeadDetailDataVo();
					vo.setAddresstypeid((CommonFunction.checkNull(data.get(0))).trim());
					vo.setAddresstypename((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}
				data=null;
				vo=null;
			}
			address=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public ArrayList<Object> getCorContitutionList(){
		
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In getCorContitutionList()........");
		try{
			StringBuffer bufInsSql =  new StringBuffer();
	
		
			bufInsSql.append("SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION' and PARENT_VALUE='CORP' and rec_status ='A'" );
		
		logger.info("in getContitutionList() of CorpotateDAOImpl Query :  "+bufInsSql.toString());
		CreditProcessingLeadDetailDataVo vo = null;
		ArrayList source = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		bufInsSql=null;
		int size=source.size();
		
		for(int i=0;i<size;i++){
			
			ArrayList subConstitution=(ArrayList)source.get(i);
			if(subConstitution.size()>0)
			{
				vo = new CreditProcessingLeadDetailDataVo();
				vo.setContitutionCode((CommonFunction.checkNull(subConstitution.get(0)).toString()));
				vo.setConstitution((CommonFunction.checkNull(subConstitution.get(1)).toString()));
				list.add(vo);
			}
			subConstitution=null;
		}
		source=null;
		vo=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
			
	}
	public ArrayList<Object> getContitutionList(){
		
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In getContitutionList()........");
		try{
			StringBuffer bufInsSql =  new StringBuffer();
	
		
				 bufInsSql.append("SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION' and rec_status ='A'");
		
		logger.info("in getContitutionList() of CorpotateDAOImpl Query :  "+bufInsSql.toString());
		CreditProcessingLeadDetailDataVo vo = null;
		ArrayList source = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		bufInsSql=null;
		int size=source.size();
	
		for(int i=0;i<size;i++){
			
			ArrayList subConstitution=(ArrayList)source.get(i);
			if(subConstitution.size()>0)
			{
				vo = new CreditProcessingLeadDetailDataVo();
				vo.setContitutionCode((CommonFunction.checkNull(subConstitution.get(0)).toString()));
				vo.setConstitution((CommonFunction.checkNull(subConstitution.get(1)).toString()));
				list.add(vo);
			}
			subConstitution=null;
		}
		source=null;
		vo=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
			
	}
	public ArrayList<Object> getIndContitutionList(){
		
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In getIndContitutionList()........");
		try{
			StringBuffer bufInsSql =  new StringBuffer();
	
		 bufInsSql.append("SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION' and PARENT_VALUE='INDV' and rec_status ='A'" );
			
		logger.info("in getContitutionList() of CorpotateDAOImpl Query :  "+bufInsSql.toString());
		CreditProcessingLeadDetailDataVo vo = null;
		ArrayList source = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		bufInsSql=null;
		int size=source.size();
		
		for(int i=0;i<size;i++){
			
			ArrayList subConstitution=(ArrayList)source.get(i);
			if(subConstitution.size()>0)
			{
				vo = new CreditProcessingLeadDetailDataVo();
				vo.setContitutionCode((CommonFunction.checkNull(subConstitution.get(0)).toString()));
				vo.setConstitution((CommonFunction.checkNull(subConstitution.get(1)).toString()));
				list.add(vo);
			}
			subConstitution=null;
		}

source=null;
		vo=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
			
	}
	//Saurabh Code Start here for Individual Constitution Sub Profile
/*public ArrayList<Object> getIndContitutionSubprofile(String indConSubprofile){
    ArrayList list = new ArrayList();
    logger.info("In getIndContitutionList()........");
    try {
      StringBuffer bufInsSql = new StringBuffer();

      logger.info(new StringBuilder().append("Saurabh indConSubprofile------- >> ").append(indConSubprofile).toString());
      bufInsSql.append(new StringBuilder().append("SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='IND_CONS_SUBPROFILE' and PARENT_VALUE='").append(indConSubprofile).append("' and rec_status ='A'").toString());

      logger.info(new StringBuilder().append("in getIndContitutionSubprofile of CorpotateDAOImpl Query :  ").append(bufInsSql.toString()).toString());
      CreditProcessingLeadDetailDataVo vo = null;
      ArrayList source = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
      bufInsSql = null;
      int size = source.size();

      for (int i = 0; i < size; i++)
      {
        ArrayList IndConstSubprofile = (ArrayList)source.get(i);
        if (IndConstSubprofile.size() > 0)
        {
          vo = new CreditProcessingLeadDetailDataVo();
          vo.setIndConstSubprofileCode(CommonFunction.checkNull(IndConstSubprofile.get(0)).toString());
          vo.setIndConstSubprofile(CommonFunction.checkNull(IndConstSubprofile.get(1)).toString());
          list.add(vo);
        }
        IndConstSubprofile = null;
      }
      source = null;
      vo = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }*/
	//Saurabh Code Ends
//Saurabh Code Start here for Individual Constitution Sub Profile
/*public ArrayList<Object> getIndConSubprofile(){
		
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In getIndContitutionList()........");
		try{
			StringBuffer bufInsSql =  new StringBuffer();
			
			logger.info("Saurabh getIndConSubprofile------- >> ");
		 bufInsSql.append("SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='IND_CONS_SUBPROFILE' and rec_status ='A'" );
			
		logger.info("in getIndContitutionSubprofile of CorpotateDAOImpl Query :  "+bufInsSql.toString());
		CreditProcessingLeadDetailDataVo vo = null;
		ArrayList source = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		bufInsSql=null;
		int size=source.size();
		
		for(int i=0;i<size;i++){
			
			ArrayList IndConstSubprofile=(ArrayList)source.get(i);
			if(IndConstSubprofile.size()>0)
			{
				vo = new CreditProcessingLeadDetailDataVo();
				vo.setIndConstSubprofileCode((CommonFunction.checkNull(IndConstSubprofile.get(0)).toString()));
				vo.setIndConstSubprofile((CommonFunction.checkNull(IndConstSubprofile.get(1)).toString()));
				list.add(vo);
			}
			IndConstSubprofile=null;
		}
		source=null;
		vo=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
			
	}*/
	//Saurabh Code Ends
	public ArrayList<Object> getBusinessSegmentList(){
			
			ArrayList<Object> list=new ArrayList<Object>();
			logger.info("In getBusinessSegmentList()........");
			try{
			
			String query="select value,description from generic_master where GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A'";
			logger.info("in getBusinessSegmentList() of CorpotateDAOImpl Query :  "+query);
			CreditProcessingLeadDetailDataVo vo = null;
			ArrayList source = ConnectionDAOforEJB.sqlSelect(query);
			query=null;
			int size=source.size();
			for(int i=0;i<size;i++){
				
				ArrayList subRegistration=(ArrayList)source.get(i);
				if(subRegistration.size()>0)
				{
					vo = new CreditProcessingLeadDetailDataVo();
					vo.setBusinessSegmentCode((CommonFunction.checkNull(subRegistration.get(0)).toString()));
					vo.setBusinessSegmentDesc((CommonFunction.checkNull(subRegistration.get(1)).toString()));
					list.add(vo);
				}
				subRegistration=null;
			}
			source=null;
			vo=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
			
		}
	
	public ArrayList<Object> getEduDetailList(){
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
		
		String query="select value,description from generic_master where GENERIC_KEY='EDU_DETAIL' and rec_status ='A'";
		logger.info("in getEduDetailList() of CorpotateDAOImpl Query :  "+query);
		CreditProcessingLeadDetailDataVo vo = null;
		ArrayList education = ConnectionDAOforEJB.sqlSelect(query);
		query=null;
		int size=education.size();
		for(int i=0;i<size;i++){
			
			ArrayList subEducation=(ArrayList)education.get(i);
			if(subEducation.size()>0)
			{
				vo = new CreditProcessingLeadDetailDataVo();
				vo.setEduDetailCode((CommonFunction.checkNull(subEducation.get(0)).toString()));
				vo.setEduDetailDesc((CommonFunction.checkNull(subEducation.get(1)).toString()));
				list.add(vo);
			}
			subEducation=null;
		}
		education=null;
		vo=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
		
	}
	

	public ArrayList<CreditProcessingLeadDetailDataVo> CustomerDetailsList(String customerId,String addressId,String bDate) {
			ArrayList<CreditProcessingLeadDetailDataVo> list = new ArrayList<CreditProcessingLeadDetailDataVo>();

			try {
				
				StringBuilder query=new StringBuilder();
				
			 query.append("select a.CUSTOMER_NAME,a.CUSTOMER_GROUP_ID,a.CUSTOMER_REGISTRATION_NO,a.CUSTMER_PAN,a.CUSTOMER_BUSINESS_SEGMENT," +
			 		"a.CUSTOMER_INDUSTRY,a.CUSTOMER_SUB_INDUSTRY,a.CUSTOMER_TYPE,a.CUSTOMER_CONSTITUTION,a.CUSTOMER_FNAME,a.CUSTOMER_LNAME," +
			 		"DATE_FORMAT(a.CUSTOMER_DOB,'"+dateFormat+"'),b.ADDRESS_LINE1,b.ADDRESS_LINE2,b.ADDRESS_TYPE,b.NO_OF_YEARS,b.PINCODE,b.COUNTRY,b.STATE,b.DISTRICT,"+
			 		"CUSTOMER_EMAIL,IF(a.CUSTOMER_GROUP_TYPE='N',a.CUSTOMER_GROUP_DESC,(select GROUP_DESC from gcd_group_m where GROUP_ID=a.CUSTOMER_GROUP_ID)) groupname, " +
			 		"(select COUNTRY_DESC from com_country_m where b.COUNTRY=COUNTRY_ID) country," +
			 		"(select DISTRICT_DESC from com_district_m where b.DISTRICT=DISTRICT_ID) district," +
			 		"(select STATE_DESC from com_state_m where b.STATE=STATE_ID) state," +
			 		"(select INDUSTRY_DESC from com_industry_m where a.CUSTOMER_INDUSTRY=INDUSTRY_ID) industry," +
			 		"(select SUB_INDUSTRY_DESC from com_sub_industry_m where a.CUSTOMER_SUB_INDUSTRY=sub_industry_id) subindustry," +
			 		"period_diff(date_format(STR_TO_DATE('"+bDate+"','%d-%m-%Y'), '%Y%m'), date_format(a.MAKER_DATE, '%Y%m')) as relationship_months,b.PRIMARY_PHONE,a.FATHER_HUSBAND_NAME,a.UID_NO,a.VOTER_ID,a.PASSPORT_NUMBER,a.gender,b.ADDRESS_LINE2,a.CUSTOMER_MNAME,b.landmark,a.CUSTOMER_GROUP_TYPE  "+
			 		" from cr_deal_customer_m a,cr_deal_address_m b where a.CUSTOMER_ID='"+CommonFunction.checkNull(customerId)+"' and b.BPID=a.CUSTOMER_ID and b.ADDRESS_ID='"+CommonFunction.checkNull(addressId)+"'");
						

				logger.info("CustomerDetailsList-------------" + query.toString());

				CreditProcessingLeadDetailDataVo leadVo = null;
				ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
				int size = leaddeatail.size();
				query=null;
				
				for (int i = 0; i < size; i++) {

					ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
					if (leaddeatail1.size() > 0) {
						leadVo = new CreditProcessingLeadDetailDataVo();
						
						leadVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
						leadVo.sethGroupId((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
						leadVo.setRegistrationNo((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
						leadVo.setCustPan((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setCustPanInd((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
						leadVo.setBusinessSegment((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
						leadVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
						leadVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
						leadVo.setCustomerType((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
						leadVo.setContitutionCode((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
						leadVo.setFirstName((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
						leadVo.setLastName((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
						leadVo.setCustDOB((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
						leadVo.setAddress1((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
						leadVo.setAddress2((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
						leadVo.setAddresstype((CommonFunction.checkNull(leaddeatail1.get(14))).trim());
						leadVo.setNoOfYears((CommonFunction.checkNull(leaddeatail1.get(15))).trim());
						leadVo.setPincode((CommonFunction.checkNull(leaddeatail1.get(16))).trim());
						leadVo.setTxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(17))).trim());
						leadVo.setTxtStateCode((CommonFunction.checkNull(leaddeatail1.get(18))).trim());
						leadVo.setTxtDistCode((CommonFunction.checkNull(leaddeatail1.get(19))).trim());
						leadVo.setEmail(CommonFunction.checkNull(leaddeatail1.get(20)).toString());
						leadVo.setGroupName(CommonFunction.checkNull(leaddeatail1.get(21)).toString());
						leadVo.setCountry(CommonFunction.checkNull(leaddeatail1.get(22)).toString());
						leadVo.setDist(CommonFunction.checkNull(leaddeatail1.get(23)).toString());
						
						leadVo.setState(CommonFunction.checkNull(leaddeatail1.get(24)).toString());	
						leadVo.setIndustry(CommonFunction.checkNull(leaddeatail1.get(25)).toString());
						leadVo.setSubIndustry(CommonFunction.checkNull(leaddeatail1.get(26)).toString());
				        leadVo.setRelationshipSince(CommonFunction.checkNull(leaddeatail1.get(27)).toString());
				        leadVo.setPhoneOff(CommonFunction.checkNull(leaddeatail1.get(28)).toString());
				        leadVo.setFatherName(CommonFunction.checkNull(leaddeatail1.get(29)).toString());
				        leadVo.setAadhaar(CommonFunction.checkNull(leaddeatail1.get(30)).toString());
				        leadVo.setVoterId(CommonFunction.checkNull(leaddeatail1.get(31)).toString());
				        leadVo.setPassport(CommonFunction.checkNull(leaddeatail1.get(32)).toString());
				        leadVo.setGenderIndividual(CommonFunction.checkNull(leaddeatail1.get(33)).toString());
				        leadVo.setAddress3(CommonFunction.checkNull(leaddeatail1.get(34)).toString());
				        leadVo.setMiddleName(CommonFunction.checkNull(leaddeatail1.get(35)).toString());
				        leadVo.setLandmark(CommonFunction.checkNull(leaddeatail1.get(36)).toString());
				        leadVo.setGroupType(CommonFunction.checkNull(leaddeatail1.get(37)).toString());
						list.add(leadVo);
					
					}
					leaddeatail1=null;
					leadVo=null;
				}
					leaddeatail=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				customerId=null;
				addressId=null;
				bDate=null;

			}
			return list;

		}


//----------------------------------Lead Entry For deal Processing-------------------------------------

	public ArrayList<Object> getLeadEntryList(String leadId) 
	{
			ArrayList<Object> list=new ArrayList<Object>();
			try{
				StringBuilder query=new StringBuilder();
			 query.append("SELECT DEAL_ID,DEAL_APPLICATION_FORM_NO,DATE_FORMAT(DEAL_DATE,'"+dateFormatWithTime+"'),DATE_FORMAT(DEAL_ENCODED_DATE,'"+dateFormatWithTime+"'), "+
	                     "DEAL_BRANCH,b.BRANCH_DESC,DEAL_INDUSTRY_ID,i.INDUSTRY_DESC,DEAL_SUB_INDUSTRY_ID,s.SUB_INDUSTRY_DESC,DEAL_SOURCE_TYPE,DEAL_SOURCE_NAME,DEAL_VENDOR_CODE,DEAL_RM,r.USER_NAME,c.DEALER_DESC,DEAL_NO,DEAL_CATEGORY,d.LEAD_ID,L.CUSTOMER_NAME "+
	                     ",d.REC_STATUS from cr_deal_dtl d "+
	                     "left join com_branch_m b on b.BRANCH_ID=d.DEAL_BRANCH "+
	                     "left join com_industry_m i on i.INDUSTRY_ID=d.DEAL_INDUSTRY_ID "+
	                     "left join com_sub_industry_m s on s.sub_industry_id=d.DEAL_SUB_INDUSTRY_ID "+
	                     "left join sec_user_m r on r.USER_ID=d.DEAL_RM "+
	                     "left join cr_dsa_dealer_m c on c.DEALER_ID=d.DEAL_VENDOR_CODE "+
	                     "left join cr_lead_dtl L on L.LEAD_ID=d.LEAD_ID "+
	                     "where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)).trim());
			
			logger.info("getLeadEntryList"+query.toString());
			CreditProcessingLeadEntryVo loanVo = null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size = leaddeatail.size();
			query=null;
			
			for(int i=0;i<size;i++){

				ArrayList leaddeatail1=(ArrayList)leaddeatail.get(i);
				if(leaddeatail1.size()>0)
				{
					loanVo = new CreditProcessingLeadEntryVo();
					loanVo.setDealId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
					loanVo.setApplicationFormNo((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					loanVo.setLeadDate((CommonFunction.checkNull(leaddeatail1.get(2))).trim().substring(0,10));
					loanVo.setLeadTime((CommonFunction.checkNull(leaddeatail1.get(2))).trim().substring(11,16));

					loanVo.setDateEncoded((CommonFunction.checkNull(leaddeatail1.get(3))).trim());

					loanVo.setLbxBranchId((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
					loanVo.setBranch((CommonFunction.checkNull(leaddeatail1.get(5))).trim());

					loanVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
					loanVo.setIndustry((CommonFunction.checkNull(leaddeatail1.get(7))).trim());

					loanVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
					loanVo.setSubIndustry((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
					loanVo.setSource((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
					loanVo.setSourcedesc((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
					
					loanVo.setLbxvendorCode((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
					loanVo.setLbxRelationship((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
					loanVo.setRelationshipManager(CommonFunction.checkNull(leaddeatail1.get(14)).toString());
					loanVo.setVendorCode((CommonFunction.checkNull(leaddeatail1.get(15))).trim());
					loanVo.setDealNo((CommonFunction.checkNull(leaddeatail1.get(16))).trim());
					loanVo.setDealCat((CommonFunction.checkNull(leaddeatail1.get(17))).trim());
					loanVo.setLeadNo((CommonFunction.checkNull(leaddeatail1.get(18))).trim());
					loanVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(19))).trim());
					loanVo.setRecStatus((CommonFunction.checkNull(leaddeatail1.get(20))).trim());
					list.add(loanVo);
				}
				leaddeatail1=null;
				loanVo=null;
			}
				leaddeatail=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
				leadId=null;
			}
			return list;
		}


	public int saveCPLeadEntry(String leadId)
	  {
		String dateTime=null;
		CreditProcessingLeadEntryVo creditVo = new CreditProcessingLeadEntryVo();
		int maxId=0;
		ArrayList<Object> list=new ArrayList<Object>();
		try
		{
		
			StringBuilder query=new StringBuilder();
			 query.append("SELECT DATE_FORMAT(LEAD_GENERATION_DATE,'"+dateFormatWithTime+"'),DATE_FORMAT(LEAD_GENERATION_DATE,'"+dateFormatWithTime+"'), "+
	                     "BRANCH_ID,INDUSTRY_ID,SUB_INDUSTRY_ID,SOURCE,SOURCE_DESCRIPTION,source_code,ifnull(LEAD_SERVICING_RM,d.maker_id),d.LEAD_ID,(select date_format(parameter_value,'%d-%m-%Y') from parameter_mst where parameter_key='BUSINESS_DATE'),d.MAKER_ID,d.rec_status from cr_LEAD_DTL d "+
	                     "where LEAD_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)).trim());
			
			logger.info("getLeadEntryList"+query.toString());
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			int size = leaddeatail.size();
			query=null;
			
			for(int i=0;i<size;i++){

				ArrayList leaddeatail1=(ArrayList)leaddeatail.get(i);
				if(leaddeatail1.size()>0)
				{
					creditVo = new CreditProcessingLeadEntryVo();
					creditVo.setLeadDate((CommonFunction.checkNull(leaddeatail1.get(0))).trim().substring(0,10));
					creditVo.setLeadTime((CommonFunction.checkNull(leaddeatail1.get(0))).trim().substring(11,16));
					creditVo.setDateEncoded((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					creditVo.setLbxBranchId((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
					creditVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
					creditVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
					creditVo.setSubIndustry((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
					creditVo.setSource((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
					creditVo.setSourcedesc((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
					creditVo.setLbxRelationship((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
					creditVo.setLbxLeadNo((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
					creditVo.setBussinessDate((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
					creditVo.setLeadGeneratorBy((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
					creditVo.setUserId((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
					creditVo.setRecStatus((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
					list.add(creditVo);
				}
			
			}
			
			
			
			
		if(CommonFunction.checkNull(creditVo.getLeadTime()).equalsIgnoreCase(""))
		{
			dateTime=creditVo.getLeadDate().trim();
		}
		else
		{
			dateTime=creditVo.getLeadDate().trim()+" "+creditVo.getLeadTime().trim().substring(0, 5);
		}
      
		logger.info("dateTime : "+dateTime);

		
		String dealNoStatus=null;
		int a[]=null;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		long vCode=0;
		if(creditVo.getLbxvendorCode()!=null && !creditVo.getLbxvendorCode().equalsIgnoreCase(""))
		{
			vCode=Long.parseLong(creditVo.getLbxvendorCode());
		}
		logger.info("In saveCPLeadEntry..vendor Code....."+vCode);
		
		 
		  
		    // asesh space start
			StringBuilder query4=new StringBuilder();
			StringBuffer query5=new StringBuffer();
			String dealPM="";
			query4.append("SELECT USER_REPORTING_TO FROM SEC_USER_M WHERE USER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(creditVo.getLbxRelationship()).trim())+"'");
			dealPM = ConnectionDAO.singleReturn(query4.toString());

			 StringBuilder query3=new StringBuilder();
			 query3.append("Select distinct max(DEAL_ID) from cr_deal_dtl for update");
			 ConnectionDAO.singleReturn(query3.toString());
			 
			 query3=null;
			logger.info("In insert saveCPLeadEntry");
			StringBuffer bufInsSql =	new StringBuffer();
						bufInsSql.append("insert into cr_deal_dtl(LEAD_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,DEAL_DATE,DEAL_ENCODED_DATE,DEAL_BRANCH,DEAL_INDUSTRY_ID,DEAL_SUB_INDUSTRY_ID,DEAL_SOURCE_TYPE,DEAL_SOURCE_NAME,DEAL_VENDOR_CODE,DEAL_RM,DEAL_CATEGORY,AREA_CODE,REC_STATUS,GENERATED_BY,DEALER_EXECUTIVE_NAME,DEALER_MANAGER_NAME,REFRESH_FLAG,DEAL_INITIATION_DATE,DEAL_RO,DEAL_PM,REFERRED_BY,FI_APPRAISER_NAME,FI_DECISION,FI_REMARKS,MAKER_REMARKS,CASE_VISITED_BY,DEAL_CURRENT_APPROVAL_LEVEL)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );//LEAD_ID
			bufInsSql.append(" ?," );//DEAL_NO
			bufInsSql.append(" ?," );//DEAL_APPLICATION_FORM_NO
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );//DEAL_DATE
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );//DEAL_ENCODED_DATE
			bufInsSql.append(" ?," );//DEAL_BRANCH
			bufInsSql.append(" ?," );//DEAL_INDUSTRY_ID
			bufInsSql.append(" ?," );//DEAL_SUB_INDUSTRY_ID
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
		/*	bufInsSql.append(" ?," );*/
			bufInsSql.append(" ?," );//
			bufInsSql.append(" ?," );//
          bufInsSql.append(" 'NNNNNNNNNNNNNNN'," );//REFRESH_FLAG
			/*bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," );//MAKER_DATE
*/			//Nishant space starts
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," );//DEAL_INITIATION_DATE
			//Nishant space end
			//Asesh space starts
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			//Asesh space end
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			//Asesh space end
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			//Amit kumar space end
			bufInsSql.append(" ?)" );
			
			if((CommonFunction.checkNull(creditVo.getLbxLeadNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxLeadNo()).trim());
			if((CommonFunction.checkNull(creditVo.getDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealNo()).trim());
			if((CommonFunction.checkNull(creditVo.getApplicationFormNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getApplicationFormNo()).trim());

			insertPrepStmtObject.addString((dateTime).trim());

			
			if((CommonFunction.checkNull(creditVo.getBussinessDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getBussinessDate()).trim());

			if((CommonFunction.checkNull(creditVo.getLbxBranchId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxBranchId()).trim());
			if((CommonFunction.checkNull(creditVo.getLbxIndustry())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxIndustry()).trim());
			if((CommonFunction.checkNull(creditVo.getLbxSubIndustry())).trim().equalsIgnoreCase(""))
			    insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxSubIndustry()).trim());
			if((CommonFunction.checkNull(creditVo.getSubIndustry())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSubIndustry()).trim());
			if((CommonFunction.checkNull(creditVo.getSource())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSource()).trim());

			insertPrepStmtObject.addString((""+vCode).trim());

			if((CommonFunction.checkNull(creditVo.getLbxRelationship())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxRelationship()).trim());
			
			if((CommonFunction.checkNull(creditVo.getDealCat())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealCat()).trim());
			//kanika code
			
			if((CommonFunction.checkNull(creditVo.getLbxareaCodeVal())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxareaCodeVal()).trim());
			//kanika code end
			if((CommonFunction.checkNull(creditVo.getRecStatus())).trim().equalsIgnoreCase("X"))
			insertPrepStmtObject.addString("F");//Status
			else
				insertPrepStmtObject.addString("P");//Status	
		// SAURABH 	
			if((CommonFunction.checkNull(creditVo.getLeadGeneratorBy())).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLeadGeneratorBy()).trim());//generated by
			
			//sAURABH

			/*if((CommonFunction.checkNull(creditVo.getUserId())).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getUserId()).trim());//maker Id
*/			
			if((CommonFunction.checkNull(creditVo.getDealerExecutive())).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealerExecutive()).trim());//DEALER_EXECUTIVE_NAME
			
			if((CommonFunction.checkNull(creditVo.getDealerManager())).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealerManager()).trim());//DEALER_MANAGER_NAME


		/*	if((CommonFunction.checkNull(creditVo.getBussinessDate())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getBussinessDate()));*/
			//Nishant space starts
			if((CommonFunction.checkNull(creditVo.getBussinessDate())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getBussinessDate()));
			//Nishant space end
			//Asesh space starts
			if((CommonFunction.checkNull(creditVo.getLbxUserSearchId())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxUserSearchId()));
			if(CommonFunction.checkNull(dealPM).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(dealPM);
			//Asesh space end
			
			if((CommonFunction.checkNull(creditVo.getReferredBy())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getReferredBy()));
			
			if((CommonFunction.checkNull(creditVo.getFiAppraiserName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getFiAppraiserName()).trim());
			
			if(CommonFunction.checkNull(creditVo.getFidecisionDeal()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(creditVo.getFidecisionDeal());
			
			if(CommonFunction.checkNull(creditVo.getFiRemarksDeal()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getFiRemarksDeal()).trim());
			
			if(CommonFunction.checkNull(creditVo.getMakerRemark()).trim().equalsIgnoreCase("")) 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getMakerRemark()).trim());
			
			if(CommonFunction.checkNull(creditVo.getCaseVisitedBy()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getCaseVisitedBy()).trim());
			
			if((CommonFunction.checkNull(creditVo.getRecStatus())).trim().equalsIgnoreCase("X"))
				insertPrepStmtObject.addString("1");//Status
				else
					insertPrepStmtObject.addNull();
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCPLeadEntry() insert query1 ### "+insertPrepStmtObject.printQuery());
			
			
			bufInsSql=null;
			
			qryList.add(insertPrepStmtObject);
			boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    logger.info("In saveCustomer......................"+status);
			if(status)
			{
				String preModuleQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PRE_DEAL_MODULE'";
				String preModuleQueryResult=ConnectionDAO.singleReturn(preModuleQuery);
				 query3=new StringBuilder();
				 query3.append("Select distinct max(DEAL_ID) from cr_deal_dtl ");
				  
				  StringBuilder id=new StringBuilder();
				  id.append(ConnectionDAO.singleReturn(query3.toString()));
				  maxId=Integer.parseInt(id.toString());
				    logger.info("In startDealNumberGenerator--  --->>");
				    startDealNumberGenerator(""+maxId);
					StringBuilder checkStageM=new StringBuilder();
					creditVo.setCompanyId("1");
					String q1="select rec_status from cr_lead_dtl where lead_id='"+leadId+"'";
					String recStatus=ConnectionDAO.singleReturn(q1);
					if(CommonFunction.checkNull(recStatus).equalsIgnoreCase("X")){
						ArrayList insertList=new ArrayList(); 
						String insertQuery="INSERT INTO CR_DEAL_LOAN_DTL(DEAL_ID,DEAL_PRODUCT_CATEGORY,DEAL_PRODUCT,DEAL_SCHEME,LOAN_TYPE,DEAL_TENURE,DEAL_LOAN_PURPOSE,DEAL_LOAN_AMOUNT,DEAL_LOAN_AMOUNT_NEW,REC_STATUS,MAKER_ID,MAKER_DATE ) "
								+ "SELECT "+maxId+",PRODUCT_CATEGORY,PRODUCT,SCHEME,LOAN_TYPE,LOAN_TENURE,LOAN_PURPOSE,AMOUNT_REQUIRED,AMOUNT_REQUIRED,'F' as REC_STATUS,a.MAKER_ID,a.MAKER_DATE  FROM CR_LEAD_DTL A JOIN CR_PRODUCT_M P ON A.PRODUCT=P.PRODUCT_ID WHERE LEAD_ID='"+leadId+"' ";
						insertList.add(insertQuery);
						logger.info("insertQuery: "+insertQuery);
						boolean insertStatus=ConnectionDAO.sqlInsUpdDelete(insertList);
						logger.info("insertStatus: "+insertStatus);
						ArrayList updateList=new ArrayList(); 
						String approvalLevel="1";
						if(insertStatus){
							String queryApprovalLevel="SELECT MIN(FINAL_APPROVAL_LEVEL) FROM CR_APPROVAL_LEVEL_M A JOIN CR_DEAL_LOAN_DTL B ON A.PRODUCT_ID=B.DEAL_PRODUCT AND A.SCHEME_ID=B.DEAL_SCHEME "
									+ " WHERE A.REC_STATUS='A' AND A.AMOUNT_FROM<=B.DEAL_LOAN_AMOUNT AND AMOUNT_TO>=B.DEAL_LOAN_AMOUNT "
									+ " AND B.DEAL_ID="+maxId+" ";
							 approvalLevel=ConnectionDAO.singleReturn(queryApprovalLevel);
							if(CommonFunction.checkNull(approvalLevel).equalsIgnoreCase("")){
								approvalLevel="1";
							}
						}
						String updateQuery="update cr_deal_dtl set Maker_id='"+creditVo.getUserId()+"',DEAL_CURRENT_APPROVAL_LEVEL='"+approvalLevel+"' WHERE DEAL_ID="+maxId+"";
						updateList.add(updateQuery);
						boolean updateStatus=ConnectionDAO.sqlInsUpdDelete(updateList);
					}else{
					
					if(CommonFunction.checkNull(preModuleQueryResult).equalsIgnoreCase("Y")){
						checkStageM.append(CommonFunction.stageMovement(creditVo.getCompanyId(), "PDC","I",""+maxId, "PDC", creditVo.getBussinessDate(),creditVo.getUserId()));
					}else{
				  checkStageM.append(CommonFunction.stageMovement(creditVo.getCompanyId(), "DC","I",""+maxId, "DC", creditVo.getBussinessDate(),creditVo.getUserId()));
					}
					}
				  logger.info("checkStageM : "+checkStageM);
				 
				 query3=null;
				 checkStageM=null;
				 id=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			
			
		}
		return maxId;

	}


    public ArrayList  searchCPGrid(LeadCaptureVo vo, String leadNo)
 	{
 	    StringBuilder customerName=new StringBuilder();
        ArrayList detailListGrid=new 	ArrayList();
 	try{
 		  ArrayList searchlist=new ArrayList();
 	      logger.info("In searchCPGrid....................");
 	      boolean appendSQL=false;
 	      StringBuffer bufInsSql =	new StringBuffer();
 	    
 	      bufInsSql.append("select LEAD_ID,CUSTOMER_NAME,p.PRODUCT_DESC,l.PRODUCT,sm.SCHEME_DESC,l.SCHEME,l.REC_STATUS " +
 	        					"from cr_lead_dtl l left outer join com_branch_m bb on l.SOURCE_CODE=bb.BRANCH_ID " +
 	        					"left outer join cr_scheme_m sm on sm.SCHEME_ID=l.SCHEME " +
 	        					"left outer join com_branch_m bm on l.ALLOCATED_BRANCH=bm.BRANCH_ID " +
 	        					"left outer join cr_product_m p on p.PRODUCT_ID=l.PRODUCT WHERE Lead_id='"+leadNo+"'");
 		     
 	    	logger.info("In searchCPGrid.. "+bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			bufInsSql=null;
			int size = searchlist.size();
			 for(int i=0;i<size;i++){

 	      ArrayList data=(ArrayList)searchlist.get(i);

 	      if(data.size()>0){
 	    	 LeadCaptureVo disVO = new LeadCaptureVo();
 	    	  
 	    	  disVO.setLeadno((CommonFunction.checkNull(data.get(0)).trim()));
 	    	  disVO.setCustomername((CommonFunction.checkNull(data.get(1)).trim()));
 	    	  disVO.setProduct((CommonFunction.checkNull(data.get(2)).trim()));
 	    	  disVO.setLbxProductID((CommonFunction.checkNull(data.get(3)).trim()));
 	    	  disVO.setScheme((CommonFunction.checkNull(data.get(4)).trim()));
 	    	  disVO.setStatus((CommonFunction.checkNull(data.get(6)).trim()));
 	    	  if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("A")){ 	    		
 	    	       disVO.setStatus("Approved");
 	    	      	  } 	    
 	    	  else if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("L")){
 	    		 disVO.setStatus("Allocated");
 	    	  } 	    	
 	    	  else if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("P")){
	    		 disVO.setStatus("Pending");
	    	  }
 	    	  else if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("F")){
	    		 disVO.setStatus("Forwarded");
	    	  }
  	    	 else if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("X")){
	    		 disVO.setStatus("Rejected");
	    	  }
  	    	else if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase("T")){
	    		 disVO.setStatus("Tracking");
	    	  }
 	    	detailListGrid.add(disVO);
 	    	disVO=null;
 	       }
 	      
	 	     data=null;
	 	     

 		      }
 	     searchlist=null;

 		}catch(Exception e){
 			e.printStackTrace();
 				}
 		finally
 		{
 			leadNo=null;
 			customerName=null;
 			vo=null;
 			leadNo=null;

 		}
 		return  detailListGrid;	
 	}

	
    
    @Override
	public ArrayList<Object> getDecisionList(String leadId) {
    	ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select DATE_FORMAT(EXPECTED_LOGIN_DATE,'"+dateFormat+"'),DATE_FORMAT(EXPECTED_DISBURSAL_DATE,'"+dateFormat+"'),REMARK,REC_STATUS from cr_lead_dtl where lead_id="+leadId);
			logger.info("In getDecisionList() query is.. "+query.toString());
			
			CreditProcessingLeadDetailDataVo noteVO=null;
			ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			int size = leaddeatail.size();
			
			noteVO = new CreditProcessingLeadDetailDataVo();
			for (int i = 0; i < size; i++) {

				ArrayList list2 = (ArrayList) leaddeatail.get(i);
				if (list2.size() > 0) {
					noteVO.setExpectedLoginDate(CommonFunction.checkNull(list2.get(0)));
					noteVO.setExpectedDisbursalDate(CommonFunction.checkNull(list2.get(1)));
					noteVO.setRemarks(CommonFunction.checkNull(list2.get(2)));
					noteVO.setDecision(CommonFunction.checkNull(list2.get(3)));
					list.add(noteVO);
				}
				list2=null;
			}
			noteVO=null;
			leaddeatail=null;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
 		{
			leadId=null;
 		}
		return list;
	}

    public ArrayList getLoanTypeList() {
    	ArrayList list = new ArrayList();
    	try {
    		logger.info("In getLoanTypeList.... ");
    		StringBuilder query=new StringBuilder();
    		
    		 query.append("SELECT M.VALUE,M.DESCRIPTION FROM generic_master M,generic_master_keys K WHERE K.GENERIC_KEY='LOAN_TYPE' " +
    					"AND K.GENERIC_KEY=M.GENERIC_KEY and m.REC_STATUS='A'");
    		logger.info("In getLoanTypeList .query."+ query.toString());
    		LoanDetailVo loanVo = null;
    		ArrayList product = ConnectionDAOforEJB.sqlSelect(query.toString());
    		
    		query=null;
    		int size =product.size();
    		for (int i = 0; i < size; i++) {

    			ArrayList data = (ArrayList) product.get(i);
    			if (data.size() > 0) {
    				loanVo = new LoanDetailVo();
    				loanVo.setLoanTypeID((CommonFunction.checkNull(data.get(0))).trim());
    				loanVo.setLoanTypeName((CommonFunction.checkNull(data.get(1))).trim());
    				list.add(loanVo);
    			}
    			data=null;
    			loanVo=null;

    		}
    			product=null;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return list;
    }
    
    public boolean updateLeadStatus(String leadId) {
		boolean status=false;
		ArrayList list=new ArrayList();
		StringBuilder query=new StringBuilder();
		try{
		query.append ("update cr_lead_dtl set REC_STATUS = 'T' where LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId).trim())+"' ");
		logger.info("In updateLeadStatus query... " + query.toString());

		list.add(query.toString());
		status =ConnectionDAOforEJB.sqlInsUpdDelete(list);
		query=null;
		list=null;
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  finally
		{
		  leadId=null;
		}
	  return status;
	
	}
    public ArrayList getSectorList() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='SECTOR_TYPE' and g.REC_STATUS='A'");
		logger.info("getSectorList"+query.toString());
		LoanDetailVo loanVo = null;
		ArrayList sectorList = ConnectionDAOforEJB.sqlSelect(query.toString());
	
		int size=sectorList.size();
		query=null;
		for(int i=0;i<size;i++){

			ArrayList sectorSubList=(ArrayList)sectorList.get(i);
			if(sectorSubList.size()>0)
			{
			    loanVo = new LoanDetailVo();
			    loanVo.setSectorId((CommonFunction.checkNull(sectorSubList.get(0))).trim());
			    loanVo.setSectorName((CommonFunction.checkNull(sectorSubList.get(1))).trim());
				list.add(loanVo);
			}
		}
		loanVo=null;
		sectorList.clear();
		sectorList=null;
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}
    
    public String getEmailMandatoryFlag() 
	{
		String flag=null;
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
    public ArrayList<Object> getGenderList(){
  	  
  	  logger.info("in getGenderList() method***********************");    	
      	ArrayList<Object>   list=new ArrayList<Object>();
      	//StringBuffer query=new StringBuffer();
      	try{
      		 String query=("select VALUE,DESCRIPTION  from GENERIC_MASTER WHERE GENERIC_KEY='GENDER_INDIVIDUAL' and REC_STATUS ='A'  order by length(Description) ");
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

public boolean startDealNumberGenerator(String dealId) {	
		
		logger.info("In startDealNumberGenerator  --->>");
		try{
			String generator=" select DEAL_NUMBER_GENERATOR("+CommonFunction.checkNull(dealId)+")";
			ConnectionDAO.singleReturn(generator);
			logger.info("startDealNumberGenerator  --->>"+generator);						
			generator=null;
			
			}catch(Exception e){
				e.printStackTrace();
			}
	return false;
}

@Override
public String moveDataLeadToDeal(String leadId, String dealId) {

	String s1=null;
	String s2=null;
   	String s3=null; 
	
   	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
		
try {
		in.add( (CommonFunction.checkNull(leadId).trim()));//1
		in.add((CommonFunction.checkNull(dealId).trim()));//2
	    out.add("");	//3
	    out.add("");	//4
    	outMessages=(ArrayList) ConnectionDAOforEJB.callSP("DEAL_GENERATOR",in,out);
    	s1=CommonFunction.checkNull(outMessages.get(0));
    	s2=(CommonFunction.checkNull(outMessages.get(1)));
    	
    	
    	logger.info("s1: "+s1);
        logger.info("s2: "+s2);
      
        
        outMessages=null;
       
       
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	finally
	{
		in.clear();
		in=null;
		out.clear();
		out=null;
		outMessages=null;
		s1=null;
		s2=null;
	
		
		
	}
	 
	return leadId;
}

//pooja code starts
public String saveCoappLead(Object ob)
{
	CreditProcessingLeadDetailDataVo creditVo = (CreditProcessingLeadDetailDataVo) ob;
	String result=null;
	String leadId=creditVo.getLeadId();
	String customerId=creditVo.getUpdateCustId();
	String count=CommonFunction.checkNull(ConnectionDAOforEJB.singleReturn("select ifnull(count(CUSTOMER_ID),0) from CR_LEAD_CUSTOMER_M where customer_id='"+customerId+"' and lead_id='"+leadId+"'"));
	ArrayList qryList = new ArrayList();
	boolean status = false;
	StringBuffer bufInsSql = new StringBuffer();
	if(CommonFunction.checkNull(customerId).equalsIgnoreCase("") || CommonFunction.checkNull(count).equalsIgnoreCase("0")){
    bufInsSql.append("INSERT INTO CR_LEAD_CUSTOMER_M(CUSTOMER_ROLE_TYPE,EXISTING_CUSTOMER,LEAD_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,"
    		+ "CUSTOMER_DOB,GROUP_ID,PAN,GROUP_TYPE,GROUP_DESC, REGISTRATION_NO,CONSTITUTION,BUSINESS_SEGMENT,INDUSTRY_ID,SUB_INDUSTRY_ID,EMAIL_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,"
    		+ "PASSPORT_NUMBER,UID_NO,GENDER,ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY, STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,MAKER_ID,MAKER_DATE,AUTHOR_ID,"
    		+ "AUTHOR_DATE,DEAL_CUSTOMER_ID)");
    bufInsSql.append(" values ( ");
    bufInsSql.append(" ?,"); //CUSTOMER_ROLE_TYPE
    bufInsSql.append(" ?,"); //EXISTING_CUSTOMER
    bufInsSql.append(" ?,"); //LEAD_ID
    bufInsSql.append(" ?,"); //CUSTOMER_NAME
    bufInsSql.append(" ?,"); //CUSTOMER_FNAME
    bufInsSql.append(" ?,"); //CUSTOMER_MNAME
    bufInsSql.append(" ?,"); //CUSTOMER_LNAME
    bufInsSql.append(" ?,"); //CUSTOMER_TYPE
    bufInsSql.append(" ?,"); //CUSTOMER_DOB
    bufInsSql.append(" ?,"); //GROUP_ID
    bufInsSql.append(" ?,"); //PAN
    bufInsSql.append(" ?,"); //GROUP_TYPE
    bufInsSql.append(" ?,"); //GROUP_DESC
    bufInsSql.append(" ?,"); //REGISTRATION_NO
    bufInsSql.append(" ?,"); //CONSTITUTION
    bufInsSql.append(" ?,"); //BUSINESS_SEGMENT
    bufInsSql.append(" ?,"); //INDUSTRY_ID
    bufInsSql.append(" ?,"); //SUB_INDUSTRY_ID
    bufInsSql.append(" ?,"); //EMAIL_ID
    bufInsSql.append(" ?,"); //FATHER_HUSBAND_NAME
    bufInsSql.append(" ?,"); //DRIVING_LICENSE
    bufInsSql.append(" ?,"); //VOTER_ID
    bufInsSql.append(" ?,"); //PASSPORT_NUMBER
    bufInsSql.append(" ?,"); //UID_NO
    bufInsSql.append(" ?,"); //GENDER
    bufInsSql.append(" ?,"); //ADDRESS_TYPE
    bufInsSql.append(" ?,"); //ADDRESS_LINE1
    bufInsSql.append(" ?,"); //ADDRESS_LINE2
    bufInsSql.append(" ?,"); //ADDRESS_LINE3
    bufInsSql.append(" ?,"); //COUNTRY
    bufInsSql.append(" ?,"); //STATE
    bufInsSql.append(" ?,"); //DISTRICT
    bufInsSql.append(" ?,"); //PINCODE
    bufInsSql.append(" ?,"); //TAHSIL
    bufInsSql.append(" ?,"); //PRIMARY_PHONE
    bufInsSql.append(" ?,"); //LANDMARK
    bufInsSql.append(" ?,"); //MAKER_ID
    bufInsSql.append(" ?,"); //MAKER_DATE
    bufInsSql.append(" ?,"); //AUTHOR_ID
    bufInsSql.append(" ?,"); //AUTHOR_DATE
    bufInsSql.append(" ? )"); //DEAL_CUSTOMER_ID
    
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    insertPrepStmtObject.addString("COAPPL");
    
    if(CommonFunction.checkNull(creditVo.getCoApprelationship()).equalsIgnoreCase("New")){
    	insertPrepStmtObject.addString("N");	
	}else{
		insertPrepStmtObject.addString("Y");
    }
    
    if(!CommonFunction.checkNull(leadId).equalsIgnoreCase("")){
    	 insertPrepStmtObject.addString(leadId);
	 }else{
		 insertPrepStmtObject.addString("0");
	 }
    
    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
    	 insertPrepStmtObject.addString(creditVo.getCoAppcustomerName().trim());
	}else{
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName()))+" "+(CommonFunction.checkNull(creditVo.getCoAppmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getCoApplastName())));//65
	}
    
    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
    	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName())));
	}else{
		insertPrepStmtObject.addString("");	
	}
    
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppmiddleName())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplastName())));
	}else{
		insertPrepStmtObject.addString("");	
	}
    
	if (CommonFunction.checkNull(creditVo.getCoAppcustomerType()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
    else {
        insertPrepStmtObject.addString(creditVo.getCoAppcustomerType().trim());
    }
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
	 	if ((CommonFunction.checkNull(creditVo.getCoAppcustDOB()).trim()).equalsIgnoreCase("")){
	 		insertPrepStmtObject.addString("0000-00-00");
	 	}else{
	 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getCoAppcustDOB()));
	 	}
	}else{
		insertPrepStmtObject.addString("0000-00-00");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("E")){
 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApphGroupId())));
 		}else{
 			insertPrepStmtObject.addString("0");
 		}
 	}else{
 		insertPrepStmtObject.addString("0");
 	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPan())));
 	}else{
 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPanInd())));
 	}
    
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupType())));
    
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("N")){
 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupName1())));
 		}else{
 			insertPrepStmtObject.addString("");
 		}
 	}else{
 		insertPrepStmtObject.addString("");	
 	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppregistrationNo())));
 	}else{
 		insertPrepStmtObject.addString("");	
 	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcorconstitution())));
 	}else{
 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppindconstitution())));
 	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppbusinessSegment())));
	}else{
		insertPrepStmtObject.addString("");	
 	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		if(!CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).equalsIgnoreCase("")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).trim()));
	}else{
		insertPrepStmtObject.addString("0");	
	}
	}else{
		insertPrepStmtObject.addString("0");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
		if(!CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).equalsIgnoreCase("")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).trim()));
	}else{
		insertPrepStmtObject.addString("0");	
	}
	}else{
		insertPrepStmtObject.addString("0");	
	}
	
	if (CommonFunction.checkNull(creditVo.getCoAppemail()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(creditVo.getCoAppemail().trim());
      }
        
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfatherName())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppdlNumber())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppvoterId())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppassport())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaadhaar())));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgenderIndividual()).trim()));
	}else{
		insertPrepStmtObject.addString("");	
	}
	
	if (CommonFunction.checkNull(creditVo.getCoAppaddresstype()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
    else {
        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddresstype())));
      }
	
	if (CommonFunction.checkNull(creditVo.getCoAppaddress1()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
    else {
        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress1())));
      }
	
	if (CommonFunction.checkNull(creditVo.getCoAppaddress2()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
    else {
        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress2())));
      }
	
	if (CommonFunction.checkNull(creditVo.getCoAppaddress3()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
    else {
        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress3())));
      }
	 
	 if(CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("1"); 
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0"); 
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0");  
	 }else{		 
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getCoApppincode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0");  
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppincode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxnTahsilHID())));//95
	}else{
		insertPrepStmtObject.addString("");	
	}
	 
	 if (CommonFunction.checkNull(creditVo.getCoAppphoneOff()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	 else {
	        insertPrepStmtObject.addString(creditVo.getCoAppphoneOff().trim());
	 }
	 
	 if (CommonFunction.checkNull(creditVo.getCoApplandmark()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	 else {
	        insertPrepStmtObject.addString(creditVo.getCoApplandmark().trim());
     }
				
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
	 
	 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
	 
	 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

	 if(!CommonFunction.checkNull(creditVo.getCoApplbxCustomerId()).equalsIgnoreCase("")){
		 String coApplbxCustomerId=creditVo.getCoApplbxCustomerId();
		 if(coApplbxCustomerId.contains("/")){
			 coApplbxCustomerId=coApplbxCustomerId.substring(coApplbxCustomerId.indexOf("/")).replace("/", "").trim();
			 insertPrepStmtObject.addString(coApplbxCustomerId);
		 }else{
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxCustomerId()).trim()));
		 }
	 }
	 else{
		 insertPrepStmtObject.addString("0");
	 }
	 
	 insertPrepStmtObject.setSql(bufInsSql.toString());
     logger.info(new StringBuilder().append("coapp insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
     qryList.add(insertPrepStmtObject);
     try
     {
       status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());

       String dealQuery="Select MAX(DEAL_ID) FROM CR_DEAL_DTL WHERE LEAD_ID='"+leadId+"' limit 1";
       String dealId=ConnectionDAO.singleReturn(dealQuery);
       String q1="Select last_Insert_id()";
       String newCustomerId=ConnectionDAO.singleReturn(q1);
       String q2="select IFNULL(DEAL_CUSTOMER_ID,0) FROM CR_LEAD_CUSTOMER_M WHERE CUSTOMER_ID='"+newCustomerId+"' ";
       String dealCustomerId=ConnectionDAO.singleReturn(q2);
       if(CommonFunction.checkNull(dealCustomerId).equalsIgnoreCase("0")){
    	  String insertQ1=" INSERT INTO CR_DEAL_CUSTOMER_M(CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_GROUP_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,GENDER,CIBIL_DONE,CIBIL_DATE,CIBIL_ID,CUSTOMER_MNAME,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,LEAD_CUSTOMER_ID) "
    	  		+ "SELECT CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,PAN,REGISTRATION_NO,CONSTITUTION,BUSINESS_SEGMENT,INDUSTRY_ID,SUB_INDUSTRY_ID,EMAIL_ID,GROUP_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,GENDER,CIBIL_DONE,date_format(CIBIL_DATE,'%Y-%m-%d'),CIBIL_ID,CUSTOMER_MNAME,GROUP_TYPE,GROUP_DESC,CUSTOMER_ID"
    	  		+ "	FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'"; 
    	  ArrayList lis=new ArrayList();
    	  lis.add(insertQ1);
    	  boolean st=ConnectionDAO.sqlInsUpdDelete(lis);
    	  String gcdQuery="Select last_Insert_id()";
    	  String gcdCustomerId=ConnectionDAO.singleReturn(gcdQuery);
    	  
    	  String insertQ2="INSERT INTO CR_DEAL_ADDRESS_M(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,PLOT_NO,FLOOR_NO,COMMUNICATION_ADDRESS,LEAD_CUSTOMER_ID)"
    	  		+ "SELECT ADDRESS_TYPE,'CS',"+gcdCustomerId+",ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,PLOT_NO,FLOOR_NO,'Y',CUSTOMER_ID "
    	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
    	  ArrayList lis1=new ArrayList();
    	  lis1.add(insertQ2);
    	  boolean st1=ConnectionDAO.sqlInsUpdDelete(lis1);
    	  
    	 
    	  
    	  String insertQ4="INSERT INTO CR_DEAL_CUSTOMER_ROLE(LEAD_ID,DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,LEAD_CUSTOMER_ID)"
    	  		+ "	SELECT LEAD_ID,"+dealId+",CUSTOMER_ROLE_TYPE,CUSTOMER_TYPE,EXISTING_CUSTOMER,"+gcdCustomerId+",'P',"+newCustomerId+" "
    	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
    	  ArrayList lis3=new ArrayList();
    	  lis3.add(insertQ4);
    	  boolean st3=ConnectionDAO.sqlInsUpdDelete(lis3);
    	  
	}else{
    	   
    	  
			  String insertQ4="INSERT INTO CR_DEAL_CUSTOMER_ROLE(LEAD_ID,DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,LEAD_CUSTOMER_ID)"
		    	  		+ "	SELECT LEAD_ID,"+dealId+",CUSTOMER_ROLE_TYPE,CUSTOMER_TYPE,EXISTING_CUSTOMER,"+dealCustomerId+",'P',"+newCustomerId+" "
		    	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
		    	  ArrayList lis3=new ArrayList();
		    	  lis3.add(insertQ4);
		    	  boolean st3=ConnectionDAO.sqlInsUpdDelete(lis3);
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
    
     
    

	}else{
		bufInsSql.append("update CR_LEAD_CUSTOMER_M set CUSTOMER_ROLE_TYPE=?,EXISTING_CUSTOMER=?,LEAD_ID=?,CUSTOMER_NAME=?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_TYPE=?, ");
		bufInsSql.append("CUSTOMER_DOB=?,GROUP_ID=?,PAN=?,GROUP_TYPE=?,GROUP_DESC=?, REGISTRATION_NO=?,CONSTITUTION=?,BUSINESS_SEGMENT=?,INDUSTRY_ID=?,SUB_INDUSTRY_ID=?,EMAIL_ID=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?, ");
		bufInsSql.append("PASSPORT_NUMBER=?,UID_NO=?,GENDER=?,ADDRESS_TYPE=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?, STATE=?,DISTRICT=?,PINCODE=?,TAHSIL=?,PRIMARY_PHONE=?,LANDMARK=?,MAKER_ID=?,MAKER_DATE=?,AUTHOR_ID=?, ");
		bufInsSql.append("AUTHOR_DATE=? where CUSTOMER_ID='"+customerId+"' ");
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	    insertPrepStmtObject.addString("COAPPL");
	    
	    if(CommonFunction.checkNull(creditVo.getCoApprelationship()).equalsIgnoreCase("New")){
	    	insertPrepStmtObject.addString("N");	
		}else{
			insertPrepStmtObject.addString("Y");
	    }
	    
	    if(!CommonFunction.checkNull(leadId).equalsIgnoreCase("")){
	    	 insertPrepStmtObject.addString(leadId);
		 }else{
			 insertPrepStmtObject.addString("0");
		 }
	    
	    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
	    	 insertPrepStmtObject.addString(creditVo.getCoAppcustomerName().trim());
		}else{
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName()))+" "+(CommonFunction.checkNull(creditVo.getCoAppmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getCoApplastName())));//65
		}
	    
	    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
	    	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName())));
		}else{
			insertPrepStmtObject.addString("");	
		}
	    
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppmiddleName())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplastName())));
		}else{
			insertPrepStmtObject.addString("");	
		}
	    
		if (CommonFunction.checkNull(creditVo.getCoAppcustomerType()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	    else {
	        insertPrepStmtObject.addString(creditVo.getCoAppcustomerType().trim());
	    }
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
		 	if ((CommonFunction.checkNull(creditVo.getCoAppcustDOB()).trim()).equalsIgnoreCase("")){
		 		insertPrepStmtObject.addString("0000-00-00");
		 	}else{
		 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getCoAppcustDOB()));
		 	}
		}else{
			insertPrepStmtObject.addString("0000-00-00");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
	 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("E")){
	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApphGroupId())));
	 		}else{
	 			insertPrepStmtObject.addString("0");
	 		}
	 	}else{
	 		insertPrepStmtObject.addString("0");
	 	}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPan())));
	 	}else{
	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPanInd())));
	 	}
	    
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupType())));
	    
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
	 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("N")){
	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupName1())));
	 		}else{
	 			insertPrepStmtObject.addString("");
	 		}
	 	}else{
	 		insertPrepStmtObject.addString("");	
	 	}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppregistrationNo())));
	 	}else{
	 		insertPrepStmtObject.addString("");	
	 	}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcorconstitution())));
	 	}else{
	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppindconstitution())));
	 	}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppbusinessSegment())));
		}else{
			insertPrepStmtObject.addString("");	
	 	}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			if(!CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).equalsIgnoreCase("")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).trim()));
		}else{
			insertPrepStmtObject.addString("0");	
		}
		}else{
			insertPrepStmtObject.addString("0");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){
			if(!CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).equalsIgnoreCase("")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).trim()));
		}else{
			insertPrepStmtObject.addString("0");	
		}
		}else{
			insertPrepStmtObject.addString("0");	
		}
		
		if (CommonFunction.checkNull(creditVo.getCoAppemail()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	      else {
	        insertPrepStmtObject.addString(creditVo.getCoAppemail().trim());
	      }
	        
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfatherName())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppdlNumber())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppvoterId())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppassport())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaadhaar())));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgenderIndividual()).trim()));
		}else{
			insertPrepStmtObject.addString("");	
		}
		
		if (CommonFunction.checkNull(creditVo.getCoAppaddresstype()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	    else {
	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddresstype())));
	      }
		
		if (CommonFunction.checkNull(creditVo.getCoAppaddress1()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	    else {
	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress1())));
	      }
		
		if (CommonFunction.checkNull(creditVo.getCoAppaddress2()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	    else {
	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress2())));
	      }
		
		if (CommonFunction.checkNull(creditVo.getCoAppaddress3()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	    else {
	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress3())));
	      }
		 
		 if(CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim().equalsIgnoreCase("")){
			 insertPrepStmtObject.addString("1"); 
		 }else{
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim()));
		 }
		 
		 if(CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim().equalsIgnoreCase("")){
			 insertPrepStmtObject.addString("0"); 
		 }else{
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim()));
		 }
		 
		 if(CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim().equalsIgnoreCase("")){
			 insertPrepStmtObject.addString("0");  
		 }else{		 
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim()));
		 }
		 
		 if(CommonFunction.checkNull(creditVo.getCoApppincode()).trim().equalsIgnoreCase("")){
			 insertPrepStmtObject.addString("0");  
		 }else{
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppincode()).trim()));
		 }
		 
		 if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){
			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxnTahsilHID())));//95
		}else{
			insertPrepStmtObject.addString("");	
		}
		 
		 if (CommonFunction.checkNull(creditVo.getCoAppphoneOff()).trim().equalsIgnoreCase(""))
		        insertPrepStmtObject.addNull();
		 else {
		        insertPrepStmtObject.addString(creditVo.getCoAppphoneOff().trim());
		 }
		 
		 if (CommonFunction.checkNull(creditVo.getCoApplandmark()).trim().equalsIgnoreCase(""))
		        insertPrepStmtObject.addNull();
		 else {
		        insertPrepStmtObject.addString(creditVo.getCoApplandmark().trim());
	     }
					
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
		 
		 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
		 
		 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

		
		 insertPrepStmtObject.setSql(bufInsSql.toString());
	     logger.info(new StringBuilder().append("coapp insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
	     qryList.add(insertPrepStmtObject);
	     logger.info(new StringBuilder().append("In coapp ........ insert query: ").append(bufInsSql).toString());
	     try
	     {
	       status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());

	       String dealQuery="Select MAX(DEAL_ID) FROM CR_DEAL_DTL WHERE LEAD_ID='"+leadId+"' limit 1";
	       String dealId=ConnectionDAO.singleReturn(dealQuery);
	       
	       String q2="select IFNULL(DEAL_CUSTOMER_ID,0) FROM CR_LEAD_CUSTOMER_M WHERE CUSTOMER_ID='"+customerId+"' ";
	       String dealCustomerId=ConnectionDAO.singleReturn(q2);
	       String dealCustomerQuery="select DEAL_CUSTOMER_ID FROM CR_DEAL_CUSTOMER_ROLE WHERE LEAD_ID='"+leadId+"' and LEAD_CUSTOMER_ID='"+customerId+"'";
	       String dealCustomer=ConnectionDAO.singleReturn(dealCustomerQuery);
	       if(CommonFunction.checkNull(dealCustomerId).equalsIgnoreCase("0")){
	    	   bufInsSql=new StringBuffer();
	    	   insertPrepStmtObject = new PrepStmtObject();
	    	   qryList=new ArrayList(); 
	    	   bufInsSql.append("update CR_DEAL_CUSTOMER_M set CUSTOMER_NAME=?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_TYPE=?,CUSTOMER_DOB=?,CUSTMER_PAN=?, ");
	    	   bufInsSql.append("CUSTOMER_REGISTRATION_NO=?,CUSTOMER_CONSTITUTION=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_INDUSTRY=?,CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_EMAIL=?,");
	    	   bufInsSql.append("CUSTOMER_GROUP_ID=?,CUSTOMER_GROUP_TYPE=?,CUSTOMER_GROUP_DESC=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,UID_NO=?,GENDER=? WHERE CUSTOMER_ID='"+dealCustomer+"'");

	    	   
	  	    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_NAME
	  	    	 insertPrepStmtObject.addString(creditVo.getCoAppcustomerName().trim());
	  		}else{
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName()))+" "+(CommonFunction.checkNull(creditVo.getCoAppmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getCoApplastName())));//65
	}
	  	    	  	    
	  	    if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_FNAME
	  	    	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfirstName())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  	    
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_MNAME
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppmiddleName())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_LNAME
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplastName())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  	    
	  		if (CommonFunction.checkNull(creditVo.getCoAppcustomerType()).trim().equalsIgnoreCase(""))//CUSTOMER_TYPE
	  	        insertPrepStmtObject.addNull();
	  	    else {
	  	        insertPrepStmtObject.addString(creditVo.getCoAppcustomerType().trim());
	  	    }
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_DOB
	  		 	if ((CommonFunction.checkNull(creditVo.getCoAppcustDOB()).trim()).equalsIgnoreCase("")){
	  		 		insertPrepStmtObject.addString("0000-00-00");
	  		 	}else{
	  		 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getCoAppcustDOB()));
	  		 	}
	  		}else{
	  			insertPrepStmtObject.addString("0000-00-00");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTMER_PAN
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPan())));
	  	 	}else{
	  	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcustPanInd())));
	  	 	}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_REGISTRATION_NO
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppregistrationNo())));
	  	 	}else{
	  	 		insertPrepStmtObject.addString("");	
	  	 	}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_CONSTITUTION
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppcorconstitution())));
	  	 	}else{
	  	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppindconstitution())));
	  	 	}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_BUSINESS_SEGMENT
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppbusinessSegment())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  	 	}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_INDUSTRY
	  			if(!CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).equalsIgnoreCase("")){
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxIndustry()).trim()));
	  			}else{
	  				insertPrepStmtObject.addString("0");
	  			}
	  		}else{
	  			insertPrepStmtObject.addString("0");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_SUB_INDUSTRY
	  			if(!CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).equalsIgnoreCase("")){
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApplbxSubIndustry()).trim()));
	  			}else{
	  				insertPrepStmtObject.addString("0");
	  			}
	  		}else{
	  			insertPrepStmtObject.addString("0");	
	  		}
	  		
	  		if (CommonFunction.checkNull(creditVo.getCoAppemail()).trim().equalsIgnoreCase(""))//CUSTOMER_EMAIL
	  	        insertPrepStmtObject.addNull();
	  	      else {
	  	        insertPrepStmtObject.addString(creditVo.getCoAppemail().trim());
	  	      }
	  	    
	  		
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_GROUP_ID
	  	 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("E")){
	  	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApphGroupId())));
	  	 		}else{
	  	 			insertPrepStmtObject.addString("0");
	  	 		}
	  	 	}else{
	  	 		insertPrepStmtObject.addString("0");
	  	 	}
	  		
	  		
	  	    
	  		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupType())));//CUSTOMER_GROUP_TYPE
	  	    
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_GROUP_DESC
	  	 		if(creditVo.getCoAppgroupType().equalsIgnoreCase("N")){
	  	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgroupName1())));
	  	 		}else{
	  	 			insertPrepStmtObject.addString("");
	  	 		}
	  	 	}else{
	  	 		insertPrepStmtObject.addString("");	
	  	 	}
	  		
	  		    
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//FATHER_HUSBAND_NAME
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppfatherName())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//DRIVING_LICENSE
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppdlNumber())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//VOTER_ID
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppvoterId())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//PASSPORT
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppassport())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//UID
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaadhaar())));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		
	  		if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//GENDER
	  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppgenderIndividual()).trim()));
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		insertPrepStmtObject.setSql(bufInsSql.toString());
	  		logger.info("In coapp.........insert status: "+insertPrepStmtObject.printQuery());
	  		qryList.add(insertPrepStmtObject);
	  		 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
	  		
		       bufInsSql=new StringBuffer();
	    	   insertPrepStmtObject = new PrepStmtObject();
	    	   qryList=new ArrayList(); 
	    	  
	    	   bufInsSql.append("UPDATE CR_DEAL_ADDRESS_M SET ADDRESS_TYPE=?,BPTYPE='CS',BPID=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?,STATE=?,DISTRICT=?,PINCODE=?,TAHSIL=?,PRIMARY_PHONE=?,LANDMARK=?,COMMUNICATION_ADDRESS='Y' WHERE BPID='"+dealCustomer+"'");
	    	  		
	    	  
	    	  if (CommonFunction.checkNull(creditVo.getCoAppaddresstype()).trim().equalsIgnoreCase(""))//ADDRESS_TYPE
		  	        insertPrepStmtObject.addNull();
		  	    else {
		  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddresstype())));
		  	      }
		  	
		  	        insertPrepStmtObject.addString((CommonFunction.checkNull(dealCustomer)));//BPID
		  	 
		  		if (CommonFunction.checkNull(creditVo.getCoAppaddress1()).trim().equalsIgnoreCase(""))//ADDRESS_LINE1
		  	        insertPrepStmtObject.addNull();
		  	    else {
		  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress1())));
		  	      }
		  		
		  		if (CommonFunction.checkNull(creditVo.getCoAppaddress2()).trim().equalsIgnoreCase(""))//ADDRESS_LINE2
		  	        insertPrepStmtObject.addNull();
		  	    else {
		  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress2())));
		  	      }
		  		
		  		if (CommonFunction.checkNull(creditVo.getCoAppaddress3()).trim().equalsIgnoreCase(""))//ADDRESS_LINE3
		  	        insertPrepStmtObject.addNull();
		  	    else {
		  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoAppaddress3())));
		  	      }
		  		 
		  		 if(CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim().equalsIgnoreCase("")){//COUNTRY
		  			 insertPrepStmtObject.addString("1"); 
		  		 }else{
		  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtCountryCode()).trim()));
		  		 }
		  		 
		  		 if(CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim().equalsIgnoreCase("")){//STATE
		  			 insertPrepStmtObject.addString("0"); 
		  		 }else{
		  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtStateCode()).trim()));
		  		 }
		  		 
		  		 if(CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim().equalsIgnoreCase("")){//DISTRICT
		  			 insertPrepStmtObject.addString("0");  
		  		 }else{		 
		  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxtDistCode()).trim()));
		  		 }
		  		 
		  		 if(CommonFunction.checkNull(creditVo.getCoApppincode()).trim().equalsIgnoreCase("")){//PINCODE
		  			 insertPrepStmtObject.addString("0");  
		  		 }else{
		  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApppincode()).trim()));
		  		 }
		  		 
		  		 if(CommonFunction.checkNull(creditVo.getCoAppcustomerType()).equalsIgnoreCase("I")){//TAHSIL
		  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getCoApptxnTahsilHID())));//95
		  		}else{
		  			insertPrepStmtObject.addString("");	
		  		}
		  		 
		  		 if (CommonFunction.checkNull(creditVo.getCoAppphoneOff()).trim().equalsIgnoreCase(""))//PRIMARY_PHONE
		  		        insertPrepStmtObject.addNull();
		  		 else {
		  		        insertPrepStmtObject.addString(creditVo.getCoAppphoneOff().trim());
		  		 }
		  		 
		  		 if (CommonFunction.checkNull(creditVo.getCoApplandmark()).trim().equalsIgnoreCase(""))//LANDMARK
		  		        insertPrepStmtObject.addNull();
		  		 else {
		  		        insertPrepStmtObject.addString(creditVo.getCoApplandmark().trim());
		  	     }
		  					
		  		insertPrepStmtObject.setSql(bufInsSql.toString());
		  		qryList.add(insertPrepStmtObject);
		  		logger.info("In coapp Address.........insert status: "+insertPrepStmtObject.printQuery());
		  		 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
		  		 
	    	  
	       }
	      
	     }
	     catch (Exception e) {
	       e.printStackTrace();
	     }
	}
         try
     {
   
       boolean stat=insertDedupeData(leadId);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     if(status){
    	 result=leadId;
     }else{
    	 result="";
     }
	 
	return result;
}

private boolean insertDedupeData(String leadId) {
	ArrayList list=new ArrayList();
	ArrayList list1=new ArrayList();
	boolean status=false;
	
	try{
		String deleteQuery="DELETE FROM CR_CUSTOMER_SEARCH_DTL WHERE LEAD_ID='"+leadId+"'";
		list.add(deleteQuery);
		status=ConnectionDAO.sqlInsUpdDelete(list);
		
		String insertQuery="INSERT INTO CR_CUSTOMER_SEARCH_DTL(CUSTOMER_ID,BPID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_CONSTITUTION ,CUSTOMER_EMAIL,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,PRIMARY_PHONE,MAKER_DATE,FIRST_NAME,LAST_NAME,DATE_OF_INCORPORATION,REGISTRATION_NUMBER,UID ,SOURCE_SYSTEM,RECORD_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,dedupe_remarks,dedupe_decision,ADDRESS_STRING,DISTRICT,PINCODE,STATE,LEAD_ID)"
				+ "select distinct A.CUSTOMER_ID,A.CUSTOMER_ID,A.CUSTOMER_NAME,A.CUSTOMER_TYPE,A.CUSTOMER_DOB,A.PAN,A.CONSTITUTION ,A.EMAIL_ID,A.FATHER_HUSBAND_NAME,A.DRIVING_LICENSE,A.VOTER_ID,A.PASSPORT_NUMBER,A.PRIMARY_PHONE,SYSDATE(),A.CUSTOMER_FNAME,A.CUSTOMER_LNAME,A.CUSTOMER_DOB,A.REGISTRATION_NO,A.UID_NO ,'I',NULL,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.ADDRESS_LINE1,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ','') ,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.ADDRESS_LINE2,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ',''),replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace"
				+ "(replace(concat(ifnull(A.ADDRESS_LINE3,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ',''), NULL , NULL, concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.ADDRESS_LINE1,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ',''),replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.ADDRESS_LINE2,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ',''),replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.ADDRESS_LINE3,''),' '),'!',''),'%',''),'(',''),')',''),'-',''),'{',''),'[',''),'}',''),']',''),':',''),';',''),'/',''),'?',''),'.',''),',',''),'&','AND'),' ','')),A.DISTRICT,A.PINCODE,A.STATE,e.LEAD_ID "
				+ "FROM CR_LEAD_CUSTOMER_M A "
				+ "JOIN CR_LEAD_DTL E ON A.LEAD_ID=E.LEAD_ID "
				+ "WHERE  E.LEAD_ID='"+leadId+"' ";
		logger.info("dedupe Insert Query for Lead: "+insertQuery);
		list1.add(insertQuery);
		status=ConnectionDAO.sqlInsUpdDelete(list1);
		logger.info("Status of dedupe Insert Query for Lead : "+status);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
	return status;
}



public String saveGuarLead(Object ob)
{
CreditProcessingLeadDetailDataVo creditVo = (CreditProcessingLeadDetailDataVo) ob;
String result=null;
String leadId=creditVo.getLeadId();
String customerId=creditVo.getUpdateCustId();
String count=CommonFunction.checkNull(ConnectionDAOforEJB.singleReturn("select ifnull(count(CUSTOMER_ID),0) from CR_LEAD_CUSTOMER_M where customer_id='"+customerId+"' and lead_id='"+leadId+"'"));
ArrayList qryList = new ArrayList();
boolean status = false;
StringBuffer bufInsSql = new StringBuffer();
if(CommonFunction.checkNull(customerId).equalsIgnoreCase("") || CommonFunction.checkNull(count).equalsIgnoreCase("0")){
bufInsSql.append("INSERT INTO CR_LEAD_CUSTOMER_M(CUSTOMER_ROLE_TYPE,EXISTING_CUSTOMER,LEAD_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,"
		+ "CUSTOMER_DOB,GROUP_ID,PAN,GROUP_TYPE,GROUP_DESC, REGISTRATION_NO,CONSTITUTION,BUSINESS_SEGMENT,INDUSTRY_ID,SUB_INDUSTRY_ID,EMAIL_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,"
		+ "PASSPORT_NUMBER,UID_NO,GENDER,ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY, STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,MAKER_ID,MAKER_DATE,AUTHOR_ID,"
		+ "AUTHOR_DATE,DEAL_CUSTOMER_ID)");
bufInsSql.append(" values ( ");
bufInsSql.append(" ?,"); //CUSTOMER_ROLE_TYPE
bufInsSql.append(" ?,"); //EXISTING_CUSTOMER
bufInsSql.append(" ?,"); //LEAD_ID
bufInsSql.append(" ?,"); //CUSTOMER_NAME
bufInsSql.append(" ?,"); //CUSTOMER_FNAME
bufInsSql.append(" ?,"); //CUSTOMER_MNAME
bufInsSql.append(" ?,"); //CUSTOMER_LNAME
bufInsSql.append(" ?,"); //CUSTOMER_TYPE
bufInsSql.append(" ?,"); //CUSTOMER_DOB
bufInsSql.append(" ?,"); //GROUP_ID
bufInsSql.append(" ?,"); //PAN
bufInsSql.append(" ?,"); //GROUP_TYPE
bufInsSql.append(" ?,"); //GROUP_DESC
bufInsSql.append(" ?,"); //REGISTRATION_NO
bufInsSql.append(" ?,"); //CONSTITUTION
bufInsSql.append(" ?,"); //BUSINESS_SEGMENT
bufInsSql.append(" ?,"); //INDUSTRY_ID
bufInsSql.append(" ?,"); //SUB_INDUSTRY_ID
bufInsSql.append(" ?,"); //EMAIL_ID
bufInsSql.append(" ?,"); //FATHER_HUSBAND_NAME
bufInsSql.append(" ?,"); //DRIVING_LICENSE
bufInsSql.append(" ?,"); //VOTER_ID
bufInsSql.append(" ?,"); //PASSPORT_NUMBER
bufInsSql.append(" ?,"); //UID_NO
bufInsSql.append(" ?,"); //GENDER
bufInsSql.append(" ?,"); //ADDRESS_TYPE
bufInsSql.append(" ?,"); //ADDRESS_LINE1
bufInsSql.append(" ?,"); //ADDRESS_LINE2
bufInsSql.append(" ?,"); //ADDRESS_LINE3
bufInsSql.append(" ?,"); //COUNTRY
bufInsSql.append(" ?,"); //STATE
bufInsSql.append(" ?,"); //DISTRICT
bufInsSql.append(" ?,"); //PINCODE
bufInsSql.append(" ?,"); //TAHSIL
bufInsSql.append(" ?,"); //PRIMARY_PHONE
bufInsSql.append(" ?,"); //LANDMARK
bufInsSql.append(" ?,"); //MAKER_ID
bufInsSql.append(" ?,"); //MAKER_DATE
bufInsSql.append(" ?,"); //AUTHOR_ID
bufInsSql.append(" ?,"); //AUTHOR_DATE
bufInsSql.append(" ? )"); //DEAL_CUSTOMER_ID
PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
insertPrepStmtObject.addString("GUARANTOR");

if(CommonFunction.checkNull(creditVo.getGaurrelationship()).equalsIgnoreCase("New")){
	insertPrepStmtObject.addString("N");	
}else{
	insertPrepStmtObject.addString("Y");
}

if(!CommonFunction.checkNull(leadId).equalsIgnoreCase("")){
	 insertPrepStmtObject.addString(leadId);
 }else{
	 insertPrepStmtObject.addString("0");
 }

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	 insertPrepStmtObject.addString(creditVo.getGaurcustomerName().trim());
}else{
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName()))+" "+(CommonFunction.checkNull(creditVo.getGaurmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getGaurlastName())));//65
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurmiddleName())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlastName())));
}else{
	insertPrepStmtObject.addString("");	
}

if (CommonFunction.checkNull(creditVo.getGaurcustomerType()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
else {
    insertPrepStmtObject.addString(creditVo.getGaurcustomerType().trim());
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
 	if ((CommonFunction.checkNull(creditVo.getGaurcustDOB()).trim()).equalsIgnoreCase("")){
 		insertPrepStmtObject.addString("0000-00-00");
 	}else{
 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getGaurcustDOB()));
 	}
}else{
	insertPrepStmtObject.addString("0000-00-00");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		if(creditVo.getGaurgroupType().equalsIgnoreCase("E")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurhGroupId())));
		}else{
			insertPrepStmtObject.addString("0");
		}
	}else{
		insertPrepStmtObject.addString("0");
	}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPan())));
	}else{
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPanInd())));
	}

insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupType())));

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		if(creditVo.getGaurgroupType().equalsIgnoreCase("N")){
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupName1())));
		}else{
			insertPrepStmtObject.addString("");
		}
	}else{
		insertPrepStmtObject.addString("");	
	}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurregistrationNo())));
	}else{
		insertPrepStmtObject.addString("");	
	}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcorconstitution())));
	}else{
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurindconstitution())));
	}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurbusinessSegment())));
}else{
	insertPrepStmtObject.addString("");	
	}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
if(!CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).equalsIgnoreCase("")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).trim()));
}else{
	insertPrepStmtObject.addString("0");	
}
}else{
	insertPrepStmtObject.addString("0");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
	if(!CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).equalsIgnoreCase("")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).trim()));
}else{
	insertPrepStmtObject.addString("0");	
}
}else{
	insertPrepStmtObject.addString("0");	
}

if (CommonFunction.checkNull(creditVo.getGauremail()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
  else {
    insertPrepStmtObject.addString(creditVo.getGauremail().trim());
  }
    
if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfatherName())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurdlNumber())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurvoterId())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpassport())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraadhaar())));
}else{
	insertPrepStmtObject.addString("");	
}

if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgenderIndividual()).trim()));
}else{
	insertPrepStmtObject.addString("");	
}

if (CommonFunction.checkNull(creditVo.getGauraddresstype()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
else {
    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddresstype())));
  }

if (CommonFunction.checkNull(creditVo.getGauraddress1()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
else {
    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress1())));
  }

if (CommonFunction.checkNull(creditVo.getGauraddress2()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
else {
    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress2())));
  }

if (CommonFunction.checkNull(creditVo.getGauraddress3()).trim().equalsIgnoreCase(""))
    insertPrepStmtObject.addNull();
else {
    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress3())));
  }
 
 if(CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim().equalsIgnoreCase("")){
	 insertPrepStmtObject.addString("1"); 
 }else{
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim()));
 }
 
 if(CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim().equalsIgnoreCase("")){
	 insertPrepStmtObject.addString("0"); 
 }else{
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim()));
 }
 
 if(CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim().equalsIgnoreCase("")){
	 insertPrepStmtObject.addString("0");  
 }else{		 
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim()));
 }
 
 if(CommonFunction.checkNull(creditVo.getGaurpincode()).trim().equalsIgnoreCase("")){
	 insertPrepStmtObject.addString("0");  
 }else{
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpincode()).trim()));
 }
 
 if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxnTahsilHID())));//95
}else{
	insertPrepStmtObject.addString("");	
}
 
 if (CommonFunction.checkNull(creditVo.getGaurphoneOff()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
 else {
        insertPrepStmtObject.addString(creditVo.getGaurphoneOff().trim());
 }
 
 if (CommonFunction.checkNull(creditVo.getGaurlandmark()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
 else {
        insertPrepStmtObject.addString(creditVo.getGaurlandmark().trim());
 }
			
 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
 
 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
 
 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

 if(!CommonFunction.checkNull(creditVo.getGaurlbxCustomerId()).equalsIgnoreCase("")){
	 String gaurlbxCustomerId=creditVo.getGaurlbxCustomerId();
	 if(gaurlbxCustomerId.contains("/")){
		 gaurlbxCustomerId=gaurlbxCustomerId.substring(gaurlbxCustomerId.indexOf("/")).replace("/", "").trim();
		 insertPrepStmtObject.addString(gaurlbxCustomerId);
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxCustomerId()).trim()));
	 }
 }
 else{
	 insertPrepStmtObject.addString("0");
 }
 
 insertPrepStmtObject.setSql(bufInsSql.toString());
 logger.info(new StringBuilder().append("Guarantor insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
 qryList.add(insertPrepStmtObject);
 logger.info(new StringBuilder().append("In Guarantor ........ insert query: ").append(bufInsSql).toString());

 try
 {
   status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
   logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
   
   String dealQuery="Select MAX(DEAL_ID) FROM CR_DEAL_DTL WHERE LEAD_ID='"+leadId+"' limit 1";
   String dealId=ConnectionDAO.singleReturn(dealQuery);
   String q1="Select last_Insert_id()";
   String newCustomerId=ConnectionDAO.singleReturn(q1);
   String q2="select IFNULL(DEAL_CUSTOMER_ID,0) FROM CR_LEAD_CUSTOMER_M WHERE CUSTOMER_ID='"+newCustomerId+"' ";
   String dealCustomerId=ConnectionDAO.singleReturn(q2);
   if(CommonFunction.checkNull(dealCustomerId).equalsIgnoreCase("0")){
	  String insertQ1=" INSERT INTO CR_DEAL_CUSTOMER_M(CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_NO,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_GROUP_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,GENDER,CIBIL_DONE,CIBIL_DATE,CIBIL_ID,CUSTOMER_MNAME,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,LEAD_CUSTOMER_ID) "
	  		+ "SELECT CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,PAN,REGISTRATION_NO,CONSTITUTION,BUSINESS_SEGMENT,INDUSTRY_ID,SUB_INDUSTRY_ID,EMAIL_ID,GROUP_ID,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,GENDER,CIBIL_DONE,date_format(CIBIL_DATE,'%Y-%m-%d'),CIBIL_ID,CUSTOMER_MNAME,GROUP_TYPE,GROUP_DESC,CUSTOMER_ID"
	  		+ "	FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'"; 
	  ArrayList lis=new ArrayList();
	  lis.add(insertQ1);
	  boolean st=ConnectionDAO.sqlInsUpdDelete(lis);
	  String gcdQuery="Select last_Insert_id()";
	  String gcdCustomerId=ConnectionDAO.singleReturn(gcdQuery);
	  
	  String insertQ2="INSERT INTO CR_DEAL_ADDRESS_M(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,PLOT_NO,FLOOR_NO,COMMUNICATION_ADDRESS,LEAD_CUSTOMER_ID)"
	  		+ "SELECT ADDRESS_TYPE,'CS',"+gcdCustomerId+",ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,TAHSIL,PRIMARY_PHONE,LANDMARK,PLOT_NO,FLOOR_NO,'Y',CUSTOMER_ID "
	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
	  ArrayList lis1=new ArrayList();
	  lis1.add(insertQ2);
	  boolean st1=ConnectionDAO.sqlInsUpdDelete(lis1);
	  
	  
	  
	  String insertQ4="INSERT INTO CR_DEAL_CUSTOMER_ROLE(LEAD_ID,DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,LEAD_CUSTOMER_ID)"
	  		+ "	SELECT LEAD_ID,"+dealId+",CUSTOMER_ROLE_TYPE,CUSTOMER_TYPE,EXISTING_CUSTOMER,"+gcdCustomerId+",'P',"+newCustomerId+" "
	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
	  ArrayList lis3=new ArrayList();
	  lis3.add(insertQ4);
	  boolean st3=ConnectionDAO.sqlInsUpdDelete(lis3);
	  
}else{
	   
	  
		  String insertQ4="INSERT INTO CR_DEAL_CUSTOMER_ROLE(LEAD_ID,DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,LEAD_CUSTOMER_ID)"
	    	  		+ "	SELECT LEAD_ID,"+dealId+",CUSTOMER_ROLE_TYPE,CUSTOMER_TYPE,EXISTING_CUSTOMER,"+dealCustomerId+",'P',"+newCustomerId+" "
	    	  		+ "FROM CR_LEAD_CUSTOMER_M WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+newCustomerId+"'";
	    	  ArrayList lis3=new ArrayList();
	    	  lis3.add(insertQ4);
	    	  boolean st3=ConnectionDAO.sqlInsUpdDelete(lis3);
   }
 }
 catch (Exception e) {
   e.printStackTrace();
 }

}else{
	bufInsSql.append("update CR_LEAD_CUSTOMER_M set CUSTOMER_ROLE_TYPE=?,EXISTING_CUSTOMER=?,LEAD_ID=?,CUSTOMER_NAME=?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_TYPE=?, ");
	bufInsSql.append("CUSTOMER_DOB=?,GROUP_ID=?,PAN=?,GROUP_TYPE=?,GROUP_DESC=?, REGISTRATION_NO=?,CONSTITUTION=?,BUSINESS_SEGMENT=?,INDUSTRY_ID=?,SUB_INDUSTRY_ID=?,EMAIL_ID=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?, ");
	bufInsSql.append("PASSPORT_NUMBER=?,UID_NO=?,GENDER=?,ADDRESS_TYPE=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?, STATE=?,DISTRICT=?,PINCODE=?,TAHSIL=?,PRIMARY_PHONE=?,LANDMARK=?,MAKER_ID=?,MAKER_DATE=?,AUTHOR_ID=?, ");
	bufInsSql.append("AUTHOR_DATE=? where CUSTOMER_ID='"+customerId+"' ");
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	insertPrepStmtObject.addString("GUARANTOR");

	if(CommonFunction.checkNull(creditVo.getGaurrelationship()).equalsIgnoreCase("New")){
		insertPrepStmtObject.addString("N");	
	}else{
		insertPrepStmtObject.addString("Y");
	}

	if(!CommonFunction.checkNull(leadId).equalsIgnoreCase("")){
		 insertPrepStmtObject.addString(leadId);
	 }else{
		 insertPrepStmtObject.addString("0");
	 }

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		 insertPrepStmtObject.addString(creditVo.getGaurcustomerName().trim());
	}else{
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName()))+" "+(CommonFunction.checkNull(creditVo.getGaurmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getGaurlastName())));//65
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurmiddleName())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlastName())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if (CommonFunction.checkNull(creditVo.getGaurcustomerType()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	else {
	    insertPrepStmtObject.addString(creditVo.getGaurcustomerType().trim());
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
	 	if ((CommonFunction.checkNull(creditVo.getGaurcustDOB()).trim()).equalsIgnoreCase("")){
	 		insertPrepStmtObject.addString("0000-00-00");
	 	}else{
	 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getGaurcustDOB()));
	 	}
	}else{
		insertPrepStmtObject.addString("0000-00-00");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
			if(creditVo.getGaurgroupType().equalsIgnoreCase("E")){
				insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurhGroupId())));
			}else{
				insertPrepStmtObject.addString("0");
			}
		}else{
			insertPrepStmtObject.addString("0");
		}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPan())));
		}else{
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPanInd())));
		}

	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupType())));

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
			if(creditVo.getGaurgroupType().equalsIgnoreCase("N")){
				insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupName1())));
			}else{
				insertPrepStmtObject.addString("");
			}
		}else{
			insertPrepStmtObject.addString("");	
		}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurregistrationNo())));
		}else{
			insertPrepStmtObject.addString("");	
		}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcorconstitution())));
		}else{
			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurindconstitution())));
		}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurbusinessSegment())));
	}else{
		insertPrepStmtObject.addString("");	
		}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		if(!CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).equalsIgnoreCase("")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).trim()));
	}else{
		insertPrepStmtObject.addString("0");	
	}
	}else{
		insertPrepStmtObject.addString("0");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){
		if(!CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).equalsIgnoreCase("")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).trim()));
	}else{
		insertPrepStmtObject.addString("0");	
	}
	}else{
		insertPrepStmtObject.addString("0");	
	}

	if (CommonFunction.checkNull(creditVo.getGauremail()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	  else {
	    insertPrepStmtObject.addString(creditVo.getGauremail().trim());
	  }
	    
	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfatherName())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurdlNumber())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurvoterId())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpassport())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraadhaar())));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgenderIndividual()).trim()));
	}else{
		insertPrepStmtObject.addString("");	
	}

	if (CommonFunction.checkNull(creditVo.getGauraddresstype()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	else {
	    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddresstype())));
	  }

	if (CommonFunction.checkNull(creditVo.getGauraddress1()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	else {
	    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress1())));
	  }

	if (CommonFunction.checkNull(creditVo.getGauraddress2()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	else {
	    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress2())));
	  }

	if (CommonFunction.checkNull(creditVo.getGauraddress3()).trim().equalsIgnoreCase(""))
	    insertPrepStmtObject.addNull();
	else {
	    insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress3())));
	  }
	 
	 if(CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("1"); 
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0"); 
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0");  
	 }else{		 
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getGaurpincode()).trim().equalsIgnoreCase("")){
		 insertPrepStmtObject.addString("0");  
	 }else{
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpincode()).trim()));
	 }
	 
	 if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){
		 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxnTahsilHID())));//95
	}else{
		insertPrepStmtObject.addString("");	
	}
	 
	 if (CommonFunction.checkNull(creditVo.getGaurphoneOff()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	 else {
	        insertPrepStmtObject.addString(creditVo.getGaurphoneOff().trim());
	 }
	 
	 if (CommonFunction.checkNull(creditVo.getGaurlandmark()).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	 else {
	        insertPrepStmtObject.addString(creditVo.getGaurlandmark().trim());
	 }
				
	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
	 
	 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

	 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getMakerId())));
	 
	 insertPrepStmtObject.addString((CommonFunction.changeFormat(creditVo.getApplicationdate())));

	 
	 insertPrepStmtObject.setSql(bufInsSql.toString());
	 logger.info(new StringBuilder().append("Guarantor insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
	 qryList.add(insertPrepStmtObject);
	 logger.info(new StringBuilder().append("In Guarantor ........ insert query: ").append(bufInsSql).toString());

 try
 {
   status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
   
       String dealQuery="Select MAX(DEAL_ID) FROM CR_DEAL_DTL WHERE LEAD_ID='"+leadId+"' limit 1";
       String dealId=ConnectionDAO.singleReturn(dealQuery);
       
       String q2="select IFNULL(DEAL_CUSTOMER_ID,0) FROM CR_LEAD_CUSTOMER_M WHERE CUSTOMER_ID='"+customerId+"' ";
       String dealCustomerId=ConnectionDAO.singleReturn(q2);
       String dealCustomerQuery="select DEAL_CUSTOMER_ID FROM CR_DEAL_CUSTOMER_ROLE WHERE LEAD_ID='"+leadId+"' and LEAD_CUSTOMER_ID='"+customerId+"'";
       String dealCustomer=ConnectionDAO.singleReturn(dealCustomerQuery);
       if(CommonFunction.checkNull(dealCustomerId).equalsIgnoreCase("0")){
    	   bufInsSql=new StringBuffer();
    	   insertPrepStmtObject = new PrepStmtObject();
    	   qryList=new ArrayList(); 
    	   bufInsSql.append("update CR_DEAL_CUSTOMER_M set CUSTOMER_NAME=?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_TYPE=?,CUSTOMER_DOB=?,CUSTMER_PAN=?, ");
    	   bufInsSql.append("CUSTOMER_REGISTRATION_NO=?,CUSTOMER_CONSTITUTION=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_INDUSTRY=?,CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_EMAIL=?,");
    	   bufInsSql.append("CUSTOMER_GROUP_ID=?,CUSTOMER_GROUP_TYPE=?,CUSTOMER_GROUP_DESC=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,UID_NO=?,GENDER=? WHERE CUSTOMER_ID='"+dealCustomer+"'");

    	   
  	    if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_NAME
  	    	 insertPrepStmtObject.addString(creditVo.getGaurcustomerName().trim());
  		}else{
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName()))+" "+(CommonFunction.checkNull(creditVo.getGaurmiddleName()))+" "+(CommonFunction.checkNull(creditVo.getGaurlastName())));//65
  		}
  	    	  	    
  	    if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_FNAME
  	    	insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfirstName())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  	    
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_MNAME
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurmiddleName())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_LNAME
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlastName())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  	    
  		if (CommonFunction.checkNull(creditVo.getGaurcustomerType()).trim().equalsIgnoreCase(""))//CUSTOMER_TYPE
  	        insertPrepStmtObject.addNull();
  	    else {
  	        insertPrepStmtObject.addString(creditVo.getGaurcustomerType().trim());
  	    }
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//CUSTOMER_DOB
  		 	if ((CommonFunction.checkNull(creditVo.getGaurcustDOB()).trim()).equalsIgnoreCase("")){
  		 		insertPrepStmtObject.addString("0000-00-00");
  		 	}else{
  		 		insertPrepStmtObject.addString(CommonFunction.changeFormat(creditVo.getGaurcustDOB()));
  		 	}
  		}else{
  			insertPrepStmtObject.addString("0000-00-00");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTMER_PAN
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPan())));
  	 	}else{
  	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcustPanInd())));
  	 	}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_REGISTRATION_NO
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurregistrationNo())));
  	 	}else{
  	 		insertPrepStmtObject.addString("");	
  	 	}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_CONSTITUTION
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurcorconstitution())));
  	 	}else{
  	 		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurindconstitution())));
  	 	}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_BUSINESS_SEGMENT
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurbusinessSegment())));
  		}else{
  			insertPrepStmtObject.addString("");	
  	 	}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_INDUSTRY
  			if(!CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).equalsIgnoreCase("")){
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxIndustry()).trim()));
  			}else{
  				insertPrepStmtObject.addString("0");
  			}
  		}else{
  			insertPrepStmtObject.addString("0");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_SUB_INDUSTRY
  			if(!CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).equalsIgnoreCase("")){
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurlbxSubIndustry()).trim()));
  			}else{
  				insertPrepStmtObject.addString("0");
  			}
  		}else{
  			insertPrepStmtObject.addString("0");	
  		}
  		
  		if (CommonFunction.checkNull(creditVo.getGauremail()).trim().equalsIgnoreCase(""))//CUSTOMER_EMAIL
  	        insertPrepStmtObject.addNull();
  	      else {
  	        insertPrepStmtObject.addString(creditVo.getGauremail().trim());
  	      }
  	    
  		
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_GROUP_ID
  	 		if(creditVo.getGaurgroupType().equalsIgnoreCase("E")){
  	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurhGroupId())));
  	 		}else{
  	 			insertPrepStmtObject.addString("0");
  	 		}
  	 	}else{
  	 		insertPrepStmtObject.addString("0");
  	 	}
  		
  		
  	    
  		insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupType())));//CUSTOMER_GROUP_TYPE
  	    
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("C")){//CUSTOMER_GROUP_DESC
  	 		if(creditVo.getGaurgroupType().equalsIgnoreCase("N")){
  	 			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgroupName1())));
  	 		}else{
  	 			insertPrepStmtObject.addString("");
  	 		}
  	 	}else{
  	 		insertPrepStmtObject.addString("");	
  	 	}
  		
  		    
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//FATHER_HUSBAND_NAME
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurfatherName())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//DRIVING_LICENSE
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurdlNumber())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//VOTER_ID
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurvoterId())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//PASSPORT
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpassport())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//UID
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraadhaar())));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		
  		if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//GENDER
  			insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurgenderIndividual()).trim()));
  		}else{
  			insertPrepStmtObject.addString("");	
  		}
  		insertPrepStmtObject.setSql(bufInsSql.toString());
  		qryList.add(insertPrepStmtObject);
  		logger.info("In coapp.........insert status: "+insertPrepStmtObject.printQuery());
  		 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
  		
	       bufInsSql=new StringBuffer();
    	   insertPrepStmtObject = new PrepStmtObject();
    	   qryList=new ArrayList(); 
    	  
    	   bufInsSql.append("UPDATE CR_DEAL_ADDRESS_M SET ADDRESS_TYPE=?,BPTYPE='CS',BPID=?,ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?,STATE=?,DISTRICT=?,PINCODE=?,TAHSIL=?,PRIMARY_PHONE=?,LANDMARK=?,COMMUNICATION_ADDRESS='Y' WHERE BPID='"+dealCustomer+"'");
    	  		
    	  
    	  if (CommonFunction.checkNull(creditVo.getGauraddresstype()).trim().equalsIgnoreCase(""))//ADDRESS_TYPE
	  	        insertPrepStmtObject.addNull();
	  	    else {
	  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddresstype())));
	  	      }
	  	
	  	        insertPrepStmtObject.addString((CommonFunction.checkNull(dealCustomer)));//BPID
	  	 
	  		if (CommonFunction.checkNull(creditVo.getGauraddress1()).trim().equalsIgnoreCase(""))//ADDRESS_LINE1
	  	        insertPrepStmtObject.addNull();
	  	    else {
	  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress1())));
	  	      }
	  		
	  		if (CommonFunction.checkNull(creditVo.getGauraddress2()).trim().equalsIgnoreCase(""))//ADDRESS_LINE2
	  	        insertPrepStmtObject.addNull();
	  	    else {
	  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress2())));
	  	      }
	  		
	  		if (CommonFunction.checkNull(creditVo.getGauraddress3()).trim().equalsIgnoreCase(""))//ADDRESS_LINE3
	  	        insertPrepStmtObject.addNull();
	  	    else {
	  	        insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGauraddress3())));
	  	      }
	  		 
	  		 if(CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim().equalsIgnoreCase("")){//COUNTRY
	  			 insertPrepStmtObject.addString("1"); 
	  		 }else{
	  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtCountryCode()).trim()));
	  		 }
	  		 
	  		 if(CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim().equalsIgnoreCase("")){//STATE
	  			 insertPrepStmtObject.addString("0"); 
	  		 }else{
	  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtStateCode()).trim()));
	  		 }
	  		 
	  		 if(CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim().equalsIgnoreCase("")){//DISTRICT
	  			 insertPrepStmtObject.addString("0");  
	  		 }else{		 
	  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxtDistCode()).trim()));
	  		 }
	  		 
	  		 if(CommonFunction.checkNull(creditVo.getGaurpincode()).trim().equalsIgnoreCase("")){//PINCODE
	  			 insertPrepStmtObject.addString("0");  
	  		 }else{
	  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurpincode()).trim()));
	  		 }
	  		 
	  		 if(CommonFunction.checkNull(creditVo.getGaurcustomerType()).equalsIgnoreCase("I")){//TAHSIL
	  			 insertPrepStmtObject.addString((CommonFunction.checkNull(creditVo.getGaurtxnTahsilHID())));//95
	  		}else{
	  			insertPrepStmtObject.addString("");	
	  		}
	  		 
	  		 if (CommonFunction.checkNull(creditVo.getGaurphoneOff()).trim().equalsIgnoreCase(""))//PRIMARY_PHONE
	  		        insertPrepStmtObject.addNull();
	  		 else {
	  		        insertPrepStmtObject.addString(creditVo.getGaurphoneOff().trim());
	  		 }
	  		 
	  		 if (CommonFunction.checkNull(creditVo.getGaurlandmark()).trim().equalsIgnoreCase(""))//LANDMARK
	  		        insertPrepStmtObject.addNull();
	  		 else {
	  		        insertPrepStmtObject.addString(creditVo.getGaurlandmark().trim());
	  	     }
	  					
	  		insertPrepStmtObject.setSql(bufInsSql.toString());
	  		qryList.add(insertPrepStmtObject);
	  		logger.info("In coapp Address.........insert status: "+insertPrepStmtObject.printQuery());
	  		 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		       logger.info(new StringBuilder().append("In coapp.........insert status: ").append(status).toString());
	  		 
    	  
       }
      
     }
     catch (Exception e) {
       e.printStackTrace();
     }
}
 try
 {
   boolean stat=insertDedupeData(leadId);
 }
 catch (Exception e) {
   e.printStackTrace();
 }
 if(status){
	 result=leadId;
 }else{
	 result="";
 }
 
return result;
}
//pooja code end
public ArrayList<CreditProcessingLeadDetailDataVo> getMulCustList(String leadId,String customerId,String customerType){
ArrayList<CreditProcessingLeadDetailDataVo> list = new ArrayList<CreditProcessingLeadDetailDataVo>();

try {
	
	StringBuilder query=new StringBuilder();
	
	query.append("SELECT distinct  l.LEAD_ID,l.SOURCE_TYPE,u.USER_NAME,l.EXISTING_CUSTOMER,l.RELATIONSHIP_SINCE,l.CUSTOMER_NAME,l.CONTACT_PERSON,  ");
	query.append("l.PERSON_DESIGNATION,l.ADDRESS_LINE1,l.ADDRESS_LINE2,l.ADDRESS_LINE3,c1.COUNTRY_DESC,st1.STATE_DESC,d1.DISTRICT_DESC,  ");
	query.append("l.PINCODE,l.PRIMARY_PHONE,l.ALTERNATE_PHONE,l.EMAIL_ID,l.ALTERNATE_MAIL_ID,p.PRODUCT_DESC,s.SCHEME_DESC,l.AMOUNT_REQUIRED,  ");
	query.append("l.LOAN_TENURE,l.LOAN_PURPOSE,l.REC_STATUS,l.COUNTRY,l.STATE,l.DISTRICT,l.PRODUCT,l.SCHEME,l.LANDMARK,l.NO_OF_YEARS  ");
	query.append(",l.INDUSTRY_ID,l.SUB_INDUSTRY_ID,gm.description,DATE_FORMAT(l.LEAD_GENERATION_DATE,'%d-%m-%Y')  ");
	query.append(",p.PRODUCT_CATEGORY,l.CUSTOMER_TYPE,l.TURNOVER,l.ADDRESS_TYPE,l.GROUP_ID,l.CONSTITUTION,l.REGISTRATION_NO,l.PAN,l.BUSINESS_SEGMENT,l.first_name  ");
	query.append(",l.last_NAME,DATE_FORMAT(l.CUSTOMER_DOB,'%d-%m-%Y'),(select m.GROUP_DESC from gcd_group_m m where m.GROUP_ID=l.GROUP_ID) groupname,  ");
	query.append("l.GROUP_TYPE,l.GROUP_DESC,l.LOAN_TYPE,L.OTHER,L.NO_OF_MONTHS,L.OWNERSHIP,l.FATHER_HUSBAND_NAME,l.PASSPORT_NUMBER,l.DRIVING_LICENSE,l.VOTER_ID  ");
	query.append(",l.TAHSIL,l.SOURCE,gm.DESCRIPTION,l.SOURCE_DESCRIPTION,l.AREA_CODE,(SELECT AREA_CODE_NAME FROM com_areacode_m WHERE AREA_CODE=l.AREA_CODE) AS AREA_CODE_NAME  ");
	query.append(",ctm.DESCRIPTION,l.EDU_DETAIL,l.SECTOR_TYPE,  ");
	query.append("coapp.EXISTING_CUSTOMER,coapp.CUSTOMER_NAME,coapp.ADDRESS_LINE1,coapp.ADDRESS_LINE2,coapp.ADDRESS_LINE3,cc.COUNTRY_DESC,stt.STATE_DESC,dd.DISTRICT_DESC,  ");
	query.append("coapp.PINCODE,coapp.PRIMARY_PHONE,coapp.EMAIL_ID,coapp.COUNTRY,coapp.STATE,coapp.DISTRICT,coapp.LANDMARK,imm.INDUSTRY_DESC,simm.SUB_INDUSTRY_DESC,coapp.INDUSTRY_ID,  ");
	query.append("coapp.SUB_INDUSTRY_ID,coapp.CUSTOMER_TYPE,coapp.ADDRESS_TYPE,coapp.GROUP_ID,coapp.CONSTITUTION,coapp.REGISTRATION_NO,coapp.PAN,coapp.BUSINESS_SEGMENT,coapp.CUSTOMER_FNAME  ");
	query.append(",coapp.CUSTOMER_LNAME,DATE_FORMAT(coapp.CUSTOMER_DOB,'%d-%m-%Y'),(select mm.GROUP_DESC from gcd_group_m mm where mm.GROUP_ID=coapp.GROUP_ID) groupname,  ");
	query.append("coapp.GROUP_TYPE,coapp.GROUP_DESC,coapp.FATHER_HUSBAND_NAME,coapp.PASSPORT_NUMBER,coapp.DRIVING_LICENSE,coapp.VOTER_ID  ");
	query.append(",coapp.TAHSIL,ctmm.DESCRIPTION,cc.COUNTRY_DESC,coapp.GENDER,coapp.UID_NO,coapp.CUSTOMER_MNAME, coapp.CUSTOMER_ID,l.lead_RM,l.lead_ro,(SELECT user_name FROM SEC_USER_M WHERE USER_ID=l.LEAD_RM) AS LEAD_RManger,(SELECT user_name FROM SEC_USER_M WHERE USER_ID=l.LEAD_RO) AS LEAD_ROfficer  ");
			query.append("from cr_lead_dtl l  "); 
			query.append("left  JOIN com_country_m c1 on c1.COUNTRY_ID = l.COUNTRY "); 
			query.append("left  JOIN com_district_m d1 on d1.DISTRICT_ID = l.DISTRICT ");   
			query.append("left  JOIN com_state_m st1 on l.STATE = st1.STATE_ID ");
			query.append("left  JOIN sec_user_m u on l.LEAD_SERVICING_RM = u.USER_ID   ");
			query.append("left  JOIN cr_scheme_m s on l.SCHEME = s.SCHEME_ID   ");
			query.append("left  JOIN generic_master gm on l.source = gm.value and gm.generic_key='SOURCE_TYPE'  "); 
			query.append("left  JOIN cr_product_m p on l.PRODUCT = p.PRODUCT_ID   ");
			query.append("left join com_tehsil_m ctm on ctm.ID=l.TAHSIL   ");
			query.append("left join cr_lead_customer_m coapp on coapp.LEAD_ID=l.lead_id   ");
			query.append("left  JOIN com_country_m cc on coapp.COUNTRY = cc.COUNTRY_ID   ");
			query.append("left  JOIN com_district_m dd on coapp.DISTRICT = dd.DISTRICT_ID   ");
			query.append("left  JOIN com_state_m stt on coapp.STATE = stt.STATE_ID   ");
			query.append("left  JOIN com_industry_m imm on coapp.INDUSTRY_ID = imm.INDUSTRY_ID  "); 
			query.append("left  JOIN com_sub_industry_m simm on coapp.SUB_INDUSTRY_ID = simm.sub_industry_id  ");  
			query.append("left join com_tehsil_m ctmm on ctmm.ID=coapp.TAHSIL   ");
			query.append("where l.LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId))+"' ");
			query.append(" and coapp.CUSTOMER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerId))+"' ");
	

	logger.info("getLeadCapturingDetailsList" + query);
	
	CreditProcessingLeadDetailDataVo leadVo = null;
	ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
	query=null;
	int size=leaddeatail.size();
	
	for (int i = 0; i < size; i++) {

		
		ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
		if (size > 0) {
			
			leadVo = new CreditProcessingLeadDetailDataVo();
			if (customerType.equalsIgnoreCase("COAPPL")){
			leadVo.setLeadId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
			
			if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
				if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO")){
					leadVo.setLeadGenerator((CommonFunction.checkNull("RM / SALES EXEC")).trim());
					leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH")){
					leadVo.setLeadGenerator((CommonFunction.checkNull("TELE CALLER")).trim());
					leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS")){
					leadVo.setLeadGenerator((CommonFunction.checkNull("OTHERS")).trim());
					leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
					leadVo.setLeadGenerator((CommonFunction.checkNull("DEALER")).trim());
					leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				}
			}else{
				leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(36))).trim());
				leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
			}
			
			leadVo.setServicingRm((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
			
			if(leaddeatail1.get(3).equals("N")){
				leadVo.setRelationship("New");
			}else if(leaddeatail1.get(3).equals("Y")){
				leadVo.setRelationship("Existing");
			}

			leadVo.setRelationshipSince((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
			
			leadVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
			leadVo.setContactPerson((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
			leadVo.setPersonDesignation((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
			leadVo.setAddress1((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
			leadVo.setAddress2((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
			leadVo.setAddress3((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
			leadVo.setCountry((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
			leadVo.setState((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
			leadVo.setDist((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
			leadVo.setPincode((CommonFunction.checkNull(leaddeatail1.get(14))).trim());
			leadVo.setPhoneOff((CommonFunction.checkNull(leaddeatail1.get(15))).trim());
			leadVo.setPhoneRes((CommonFunction.checkNull(leaddeatail1.get(16))).trim());
			leadVo.setEmail(CommonFunction.checkNull(leaddeatail1.get(17)).toString());
			leadVo.setAltEmail((CommonFunction.checkNull(leaddeatail1.get(18))).trim());
			leadVo.setProduct((CommonFunction.checkNull(leaddeatail1.get(19))).trim());
			leadVo.setScheme((CommonFunction.checkNull(leaddeatail1.get(20))).trim());
			
			if (!CommonFunction.checkNull(leaddeatail1.get(21)).equalsIgnoreCase("")) {
				Number reconNum = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(21))).trim());
				leadVo.setLoanAmount(myFormatter.format(reconNum));
				
			}
			leadVo.setLoanTenure((CommonFunction.checkNull(leaddeatail1.get(22))).trim());
			leadVo.setLoanPurpose((CommonFunction.checkNull(leaddeatail1.get(23))).trim());
			leadVo.setStatus((CommonFunction.checkNull(leaddeatail1.get(24))).trim());
			leadVo.setTxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(25))).trim());
			leadVo.setTxtStateCode((CommonFunction.checkNull(leaddeatail1.get(26))).trim());
			leadVo.setTxtDistCode((CommonFunction.checkNull(leaddeatail1.get(27))).trim());
			leadVo.setLbxProductID((CommonFunction.checkNull(leaddeatail1.get(28))).trim());
			leadVo.setSchemeId((CommonFunction.checkNull(leaddeatail1.get(29))).trim());
			leadVo.setLandmark((CommonFunction.checkNull(leaddeatail1.get(30))).trim());					
			leadVo.setNoOfYears((CommonFunction.checkNull(leaddeatail1.get(31))).trim());
			
			leadVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(32))).trim());
			leadVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(33))).trim());
			leadVo.setApplicationdate((CommonFunction.checkNull(leaddeatail1.get(35))).trim());
			leadVo.setProductType((CommonFunction.checkNull(leaddeatail1.get(36))).trim());
			leadVo.setCustomerType((CommonFunction.checkNull(leaddeatail1.get(37))).trim());
			logger.info("CustomerType::::::::::::::"+(CommonFunction.checkNull(leaddeatail1.get(37))).trim());
			if(CommonFunction.checkNull(leadVo.getCustomerType()).equalsIgnoreCase("C") ){
			if(CommonFunction.checkNull(leaddeatail1.get(38)).equals("")){
				leadVo.setTurnOver("0");
			}
				else{
			Number turnover = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(38))).trim());
			leadVo.setTurnOver(myFormatter.format(turnover));
			}
		    }else{
			if(CommonFunction.checkNull(leaddeatail1.get(38)).equals("")){
				leadVo.setSalary("0");
			}
				else{
			Number salary = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(38))).trim());
			leadVo.setSalary(myFormatter.format(salary));
			}
			}
			
			leadVo.setAddresstype((CommonFunction.checkNull(leaddeatail1.get(39))).trim());

			leadVo.sethGroupId((CommonFunction.checkNull(leaddeatail1.get(40))).trim());
			
			leadVo.setContitutionCode((CommonFunction.checkNull(leaddeatail1.get(41))).trim());
			leadVo.setRegistrationNo((CommonFunction.checkNull(leaddeatail1.get(42))).trim());
			leadVo.setCustPan((CommonFunction.checkNull(leaddeatail1.get(43))).trim()); // Pan no for Corporate
			leadVo.setCustPanInd((CommonFunction.checkNull(leaddeatail1.get(43))).trim()); // Pan no for Individual
			leadVo.setBusinessSegment((CommonFunction.checkNull(leaddeatail1.get(44))).trim());
			leadVo.setFirstName((CommonFunction.checkNull(leaddeatail1.get(45))).trim());
			leadVo.setLastName((CommonFunction.checkNull(leaddeatail1.get(46))).trim());
			leadVo.setCustDOB((CommonFunction.checkNull(leaddeatail1.get(47))).trim());
			leadVo.setGroupName((CommonFunction.checkNull(leaddeatail1.get(48))).trim());
			leadVo.setGroupType((CommonFunction.checkNull(leaddeatail1.get(49))).trim());
			leadVo.setGroupDesc((CommonFunction.checkNull(leaddeatail1.get(50))).trim());
			leadVo.setLoanType((CommonFunction.checkNull(leaddeatail1.get(51))).trim());
			leadVo.setOtherDetails((CommonFunction.checkNull(leaddeatail1.get(52))).trim());
			leadVo.setNoMonths((CommonFunction.checkNull(leaddeatail1.get(53))).trim());
			leadVo.setOwner((CommonFunction.checkNull(leaddeatail1.get(54))).trim());
			
			leadVo.setFatherName((CommonFunction.checkNull(leaddeatail1.get(55))).trim());
			leadVo.setPassport((CommonFunction.checkNull(leaddeatail1.get(56))).trim());
			leadVo.setDlNumber((CommonFunction.checkNull(leaddeatail1.get(57))).trim());
			leadVo.setVoterId((CommonFunction.checkNull(leaddeatail1.get(58))).trim());
			leadVo.setTahsil((CommonFunction.checkNull(leaddeatail1.get(59))).trim());
			leadVo.setSource((CommonFunction.checkNull(leaddeatail1.get(60))).trim());
			leadVo.setSourceList((CommonFunction.checkNull(leaddeatail1.get(61))).trim());
			leadVo.setDescription((CommonFunction.checkNull(leaddeatail1.get(62))).trim());
			leadVo.setLbxareaCodeVal((CommonFunction.checkNull(leaddeatail1.get(63))).trim());
			leadVo.setAreaCodename((CommonFunction.checkNull(leaddeatail1.get(64))).trim());
			leadVo.setTahsilDesc((CommonFunction.checkNull(leaddeatail1.get(65))).trim());
			leadVo.setEduDetail((CommonFunction.checkNull(leaddeatail1.get(66))).trim());
			leadVo.setSectorType((CommonFunction.checkNull(leaddeatail1.get(67))).trim());
			
			if(CommonFunction.checkNull(leaddeatail1.get(68)).equals("N")){
				leadVo.setCoApprelationship("New");
			}else if(CommonFunction.checkNull(leaddeatail1.get(68)).equals("Y")){
				leadVo.setCoApprelationship("Existing");
			}else{
				leadVo.setCoApprelationship("New");
			}
			leadVo.setCoAppcustomerName((CommonFunction.checkNull(leaddeatail1.get(69))).trim());
			leadVo.setCoAppaddress1((CommonFunction.checkNull(leaddeatail1.get(70))).trim());
			leadVo.setCoAppaddress2((CommonFunction.checkNull(leaddeatail1.get(71))).trim());
			leadVo.setCoAppaddress3((CommonFunction.checkNull(leaddeatail1.get(72))).trim());
			leadVo.setCoAppcountry((CommonFunction.checkNull(leaddeatail1.get(73))).trim());
			leadVo.setCoAppstate((CommonFunction.checkNull(leaddeatail1.get(74))).trim());
			leadVo.setCoAppdist((CommonFunction.checkNull(leaddeatail1.get(75))).trim());
			leadVo.setCoApppincode((CommonFunction.checkNull(leaddeatail1.get(76))).trim());
			leadVo.setCoAppphoneOff((CommonFunction.checkNull(leaddeatail1.get(77))).trim());
			leadVo.setCoAppemail(CommonFunction.checkNull(leaddeatail1.get(78)).toString());
			leadVo.setCoApptxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(79))).trim());
			leadVo.setCoApptxtStateCode((CommonFunction.checkNull(leaddeatail1.get(80))).trim());
			leadVo.setCoApptxtDistCode((CommonFunction.checkNull(leaddeatail1.get(81))).trim());
			leadVo.setCoApplandmark((CommonFunction.checkNull(leaddeatail1.get(82))).trim());	
			leadVo.setCoAppindustry((CommonFunction.checkNull(leaddeatail1.get(83))).trim());
			leadVo.setCoAppsubIndustry((CommonFunction.checkNull(leaddeatail1.get(84))).trim());	
			leadVo.setCoApplbxIndustry((CommonFunction.checkNull(leaddeatail1.get(85))).trim());
			leadVo.setCoApplbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(86))).trim());
			leadVo.setCoAppcustomerType((CommonFunction.checkNull(leaddeatail1.get(87))).trim());
			leadVo.setCoAppaddresstype((CommonFunction.checkNull(leaddeatail1.get(88))).trim());
			leadVo.setCoApphGroupId((CommonFunction.checkNull(leaddeatail1.get(89))).trim());
			leadVo.setCoAppcontitutionCode((CommonFunction.checkNull(leaddeatail1.get(90))).trim());
			leadVo.setCoAppregistrationNo((CommonFunction.checkNull(leaddeatail1.get(91))).trim());
			leadVo.setCoAppcustPan((CommonFunction.checkNull(leaddeatail1.get(92))).trim()); 
			leadVo.setCoAppcustPanInd((CommonFunction.checkNull(leaddeatail1.get(92))).trim()); 
			leadVo.setCoAppbusinessSegment((CommonFunction.checkNull(leaddeatail1.get(93))).trim());
			leadVo.setCoAppfirstName((CommonFunction.checkNull(leaddeatail1.get(94))).trim());
			leadVo.setCoApplastName((CommonFunction.checkNull(leaddeatail1.get(95))).trim());
			leadVo.setCoAppcustDOB((CommonFunction.checkNull(leaddeatail1.get(96))).trim());
			leadVo.setCoAppgroupName((CommonFunction.checkNull(leaddeatail1.get(97))).trim());
			leadVo.setCoAppgroupType((CommonFunction.checkNull(leaddeatail1.get(98))).trim());
			leadVo.setCoAppgroupDesc((CommonFunction.checkNull(leaddeatail1.get(99))).trim());
			leadVo.setCoAppfatherName((CommonFunction.checkNull(leaddeatail1.get(100))).trim());
			leadVo.setCoApppassport((CommonFunction.checkNull(leaddeatail1.get(101))).trim());
			leadVo.setCoAppdlNumber((CommonFunction.checkNull(leaddeatail1.get(102))).trim());
			leadVo.setCoAppvoterId((CommonFunction.checkNull(leaddeatail1.get(103))).trim());
			leadVo.setCoApptahsil((CommonFunction.checkNull(leaddeatail1.get(104))).trim());
			leadVo.setCoApptahsilDesc((CommonFunction.checkNull(leaddeatail1.get(105))).trim());
			leadVo.setGaurcountry((CommonFunction.checkNull(leaddeatail1.get(106))).trim());
			leadVo.setCoAppgenderIndividual((CommonFunction.checkNull(leaddeatail1.get(107))).trim());
			leadVo.setCoAppaadhaar((CommonFunction.checkNull(leaddeatail1.get(108))).trim());
			leadVo.setCoAppmiddleName((CommonFunction.checkNull(leaddeatail1.get(109))).trim());			
	        leadVo.setCoApplbxCustomerId(CommonFunction.checkNull(leaddeatail1.get(110)).trim());
	        leadVo.setUpdateCustId(CommonFunction.checkNull(leaddeatail1.get(110)).trim());
			}
			if (customerType.equalsIgnoreCase("GUARANTOR")){
				leadVo.setLeadId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
				
				if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
					if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RM") || CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("RO")){
						leadVo.setLeadGenerator((CommonFunction.checkNull("RM / SALES EXEC")).trim());
						leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("BRANCH")){
						leadVo.setLeadGenerator((CommonFunction.checkNull("TELE CALLER")).trim());
						leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("OTHERS")){
						leadVo.setLeadGenerator((CommonFunction.checkNull("OTHERS")).trim());
						leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					}else if(CommonFunction.checkNull(leaddeatail1.get(1)).equalsIgnoreCase("VENDOR")){
						leadVo.setLeadGenerator((CommonFunction.checkNull("DEALER")).trim());
						leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
					}
				}else{
					leadVo.setLeadGenerator((CommonFunction.checkNull(leaddeatail1.get(36))).trim());
					leadVo.setLeadGenerator1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				}
				
				leadVo.setServicingRm((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
				
				if(leaddeatail1.get(3).equals("N")){
					leadVo.setRelationship("New");
				}else if(leaddeatail1.get(3).equals("Y")){
					leadVo.setRelationship("Existing");
				}

				leadVo.setRelationshipSince((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
				
				leadVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
				leadVo.setContactPerson((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
				leadVo.setPersonDesignation((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
				leadVo.setAddress1((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
				leadVo.setAddress2((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
				leadVo.setAddress3((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
				leadVo.setCountry((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
				leadVo.setState((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
				leadVo.setDist((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
				leadVo.setPincode((CommonFunction.checkNull(leaddeatail1.get(14))).trim());
				leadVo.setPhoneOff((CommonFunction.checkNull(leaddeatail1.get(15))).trim());
				leadVo.setPhoneRes((CommonFunction.checkNull(leaddeatail1.get(16))).trim());
				leadVo.setEmail(CommonFunction.checkNull(leaddeatail1.get(17)).toString());
				leadVo.setAltEmail((CommonFunction.checkNull(leaddeatail1.get(18))).trim());
				leadVo.setProduct((CommonFunction.checkNull(leaddeatail1.get(19))).trim());
				leadVo.setScheme((CommonFunction.checkNull(leaddeatail1.get(20))).trim());
				
				if (!CommonFunction.checkNull(leaddeatail1.get(21)).equalsIgnoreCase("")) {
					Number reconNum = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(21))).trim());
					leadVo.setLoanAmount(myFormatter.format(reconNum));
					
				}
				leadVo.setLoanTenure((CommonFunction.checkNull(leaddeatail1.get(22))).trim());
				leadVo.setLoanPurpose((CommonFunction.checkNull(leaddeatail1.get(23))).trim());
				leadVo.setStatus((CommonFunction.checkNull(leaddeatail1.get(24))).trim());
				leadVo.setTxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(25))).trim());
				leadVo.setTxtStateCode((CommonFunction.checkNull(leaddeatail1.get(26))).trim());
				leadVo.setTxtDistCode((CommonFunction.checkNull(leaddeatail1.get(27))).trim());
				leadVo.setLbxProductID((CommonFunction.checkNull(leaddeatail1.get(28))).trim());
				leadVo.setSchemeId((CommonFunction.checkNull(leaddeatail1.get(29))).trim());
				leadVo.setLandmark((CommonFunction.checkNull(leaddeatail1.get(30))).trim());					
				leadVo.setNoOfYears((CommonFunction.checkNull(leaddeatail1.get(31))).trim());
				
				leadVo.setLbxIndustry((CommonFunction.checkNull(leaddeatail1.get(32))).trim());
				leadVo.setLbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(33))).trim());
				leadVo.setApplicationdate((CommonFunction.checkNull(leaddeatail1.get(35))).trim());
				leadVo.setProductType((CommonFunction.checkNull(leaddeatail1.get(36))).trim());
				leadVo.setCustomerType((CommonFunction.checkNull(leaddeatail1.get(37))).trim());
				logger.info("CustomerType::::::::::::::"+(CommonFunction.checkNull(leaddeatail1.get(37))).trim());
				if(CommonFunction.checkNull(leadVo.getCustomerType()).equalsIgnoreCase("C") ){
				if(CommonFunction.checkNull(leaddeatail1.get(38)).equals("")){
					leadVo.setTurnOver("0");
				}
					else{
				Number turnover = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(38))).trim());
				leadVo.setTurnOver(myFormatter.format(turnover));
				}
			    }else{
				if(CommonFunction.checkNull(leaddeatail1.get(38)).equals("")){
					leadVo.setSalary("0");
				}
					else{
				Number salary = myFormatter.parse((CommonFunction.checkNull(leaddeatail1.get(38))).trim());
				leadVo.setSalary(myFormatter.format(salary));
				}
				}
				
				leadVo.setAddresstype((CommonFunction.checkNull(leaddeatail1.get(39))).trim());

				leadVo.sethGroupId((CommonFunction.checkNull(leaddeatail1.get(40))).trim());
				
				leadVo.setContitutionCode((CommonFunction.checkNull(leaddeatail1.get(41))).trim());
				leadVo.setRegistrationNo((CommonFunction.checkNull(leaddeatail1.get(42))).trim());
				leadVo.setCustPan((CommonFunction.checkNull(leaddeatail1.get(43))).trim()); // Pan no for Corporate
				leadVo.setCustPanInd((CommonFunction.checkNull(leaddeatail1.get(43))).trim()); // Pan no for Individual
				leadVo.setBusinessSegment((CommonFunction.checkNull(leaddeatail1.get(44))).trim());
				leadVo.setFirstName((CommonFunction.checkNull(leaddeatail1.get(45))).trim());
				leadVo.setLastName((CommonFunction.checkNull(leaddeatail1.get(46))).trim());
				leadVo.setCustDOB((CommonFunction.checkNull(leaddeatail1.get(47))).trim());
				leadVo.setGroupName((CommonFunction.checkNull(leaddeatail1.get(48))).trim());
				leadVo.setGroupType((CommonFunction.checkNull(leaddeatail1.get(49))).trim());
				leadVo.setGroupDesc((CommonFunction.checkNull(leaddeatail1.get(50))).trim());
				leadVo.setLoanType((CommonFunction.checkNull(leaddeatail1.get(51))).trim());
				leadVo.setOtherDetails((CommonFunction.checkNull(leaddeatail1.get(52))).trim());
				leadVo.setNoMonths((CommonFunction.checkNull(leaddeatail1.get(53))).trim());
				leadVo.setOwner((CommonFunction.checkNull(leaddeatail1.get(54))).trim());
				
				leadVo.setFatherName((CommonFunction.checkNull(leaddeatail1.get(55))).trim());
				leadVo.setPassport((CommonFunction.checkNull(leaddeatail1.get(56))).trim());
				leadVo.setDlNumber((CommonFunction.checkNull(leaddeatail1.get(57))).trim());
				leadVo.setVoterId((CommonFunction.checkNull(leaddeatail1.get(58))).trim());
				leadVo.setTahsil((CommonFunction.checkNull(leaddeatail1.get(59))).trim());
				leadVo.setSource((CommonFunction.checkNull(leaddeatail1.get(60))).trim());
				leadVo.setSourceList((CommonFunction.checkNull(leaddeatail1.get(61))).trim());
				leadVo.setDescription((CommonFunction.checkNull(leaddeatail1.get(62))).trim());
				leadVo.setLbxareaCodeVal((CommonFunction.checkNull(leaddeatail1.get(63))).trim());
				leadVo.setAreaCodename((CommonFunction.checkNull(leaddeatail1.get(64))).trim());
				leadVo.setTahsilDesc((CommonFunction.checkNull(leaddeatail1.get(65))).trim());
				leadVo.setEduDetail((CommonFunction.checkNull(leaddeatail1.get(66))).trim());
				leadVo.setSectorType((CommonFunction.checkNull(leaddeatail1.get(67))).trim());
				
				if(CommonFunction.checkNull(leaddeatail1.get(68)).equals("N")){
					leadVo.setGaurrelationship("New");
				}else if(CommonFunction.checkNull(leaddeatail1.get(68)).equals("Y")){
					leadVo.setGaurrelationship("Existing");
				}else{
					leadVo.setGaurrelationship("New");
				}
				leadVo.setGaurcustomerName((CommonFunction.checkNull(leaddeatail1.get(69))).trim());
				leadVo.setGauraddress1((CommonFunction.checkNull(leaddeatail1.get(70))).trim());
				leadVo.setGauraddress2((CommonFunction.checkNull(leaddeatail1.get(71))).trim());
				leadVo.setGauraddress3((CommonFunction.checkNull(leaddeatail1.get(72))).trim());
				leadVo.setGaurcountry((CommonFunction.checkNull(leaddeatail1.get(73))).trim());
				leadVo.setGaurstate((CommonFunction.checkNull(leaddeatail1.get(74))).trim());
				leadVo.setGaurdist((CommonFunction.checkNull(leaddeatail1.get(75))).trim());
				leadVo.setGaurpincode((CommonFunction.checkNull(leaddeatail1.get(76))).trim());
				leadVo.setGaurphoneOff((CommonFunction.checkNull(leaddeatail1.get(77))).trim());
				leadVo.setGauremail(CommonFunction.checkNull(leaddeatail1.get(78)).toString());
				leadVo.setGaurtxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(79))).trim());
				leadVo.setGaurtxtStateCode((CommonFunction.checkNull(leaddeatail1.get(80))).trim());
				leadVo.setGaurtxtDistCode((CommonFunction.checkNull(leaddeatail1.get(81))).trim());
				leadVo.setGaurlandmark((CommonFunction.checkNull(leaddeatail1.get(82))).trim());	
				leadVo.setGaurindustry((CommonFunction.checkNull(leaddeatail1.get(83))).trim());
				leadVo.setGaursubIndustry((CommonFunction.checkNull(leaddeatail1.get(84))).trim());	
				leadVo.setGaurlbxIndustry((CommonFunction.checkNull(leaddeatail1.get(85))).trim());
				leadVo.setGaurlbxSubIndustry((CommonFunction.checkNull(leaddeatail1.get(86))).trim());
				leadVo.setGaurcustomerType((CommonFunction.checkNull(leaddeatail1.get(87))).trim());
				leadVo.setGauraddresstype((CommonFunction.checkNull(leaddeatail1.get(88))).trim());
				leadVo.setGaurhGroupId((CommonFunction.checkNull(leaddeatail1.get(89))).trim());
				leadVo.setGaurcontitutionCode((CommonFunction.checkNull(leaddeatail1.get(90))).trim());
				leadVo.setGaurregistrationNo((CommonFunction.checkNull(leaddeatail1.get(91))).trim());
				leadVo.setGaurcustPan((CommonFunction.checkNull(leaddeatail1.get(92))).trim()); 
				leadVo.setGaurcustPanInd((CommonFunction.checkNull(leaddeatail1.get(92))).trim()); 
				leadVo.setGaurbusinessSegment((CommonFunction.checkNull(leaddeatail1.get(93))).trim());
				leadVo.setGaurfirstName((CommonFunction.checkNull(leaddeatail1.get(94))).trim());
				leadVo.setGaurlastName((CommonFunction.checkNull(leaddeatail1.get(95))).trim());
				leadVo.setGaurcustDOB((CommonFunction.checkNull(leaddeatail1.get(96))).trim());
				leadVo.setGaurgroupName((CommonFunction.checkNull(leaddeatail1.get(97))).trim());
				leadVo.setGaurgroupType((CommonFunction.checkNull(leaddeatail1.get(98))).trim());
				leadVo.setGaurgroupDesc((CommonFunction.checkNull(leaddeatail1.get(99))).trim());
				leadVo.setGaurfatherName((CommonFunction.checkNull(leaddeatail1.get(100))).trim());
				leadVo.setGaurpassport((CommonFunction.checkNull(leaddeatail1.get(101))).trim());
				leadVo.setGaurdlNumber((CommonFunction.checkNull(leaddeatail1.get(102))).trim());
				leadVo.setGaurvoterId((CommonFunction.checkNull(leaddeatail1.get(103))).trim());
				leadVo.setGaurtahsil((CommonFunction.checkNull(leaddeatail1.get(104))).trim());
				leadVo.setGaurtahsilDesc((CommonFunction.checkNull(leaddeatail1.get(105))).trim());
				leadVo.setGaurcountry((CommonFunction.checkNull(leaddeatail1.get(106))).trim());
				leadVo.setGaurgenderIndividual((CommonFunction.checkNull(leaddeatail1.get(107))).trim());
				leadVo.setGauraadhaar((CommonFunction.checkNull(leaddeatail1.get(108))).trim());
				leadVo.setGaurmiddleName((CommonFunction.checkNull(leaddeatail1.get(109))).trim());			
		        leadVo.setGaurlbxCustomerId(CommonFunction.checkNull(leaddeatail1.get(110)).trim());
		        leadVo.setUpdateCustId(CommonFunction.checkNull(leaddeatail1.get(110)).trim());
		        
		        leadVo.setLbxRelationship(CommonFunction.checkNull(leaddeatail1.get(111)).trim());
		        leadVo.setLbxUserSearchId(CommonFunction.checkNull(leaddeatail1.get(112)).trim());
		        leadVo.setRelationshipManager(CommonFunction.checkNull(leaddeatail1.get(113)).trim());
		        leadVo.setGeneratedUser(CommonFunction.checkNull(leaddeatail1.get(114)).trim());
				}

			list.add(leadVo);
							}
		leaddeatail1.clear();
		leaddeatail1=null;
	}
	leadVo=null;
} catch (Exception e) {
	e.printStackTrace();
}
finally{
	leadId=null;
}
return list;

}
public ArrayList<CreditProcessingLeadDetailDataVo> getCoappDetailsList(String leadId,String custType){
ArrayList<CreditProcessingLeadDetailDataVo> list = new ArrayList<CreditProcessingLeadDetailDataVo>();

try {
	StringBuilder query=new StringBuilder();
	query.append("SELECT  customer_id,coapp.CUSTOMER_NAME,case coapp.CUSTOMER_TYPE when 'I' then 'INDIVIDUAL' when 'C' then 'CORPORATE' end as CUSTOMER_TYPE,case coapp.EXISTING_CUSTOMER when 'N' then 'NO' when 'Y' then 'Yes' end as EXISTING_CUSTOMER,coapp.CUSTOMER_TYPE from  cr_lead_customer_m coapp ");
	query.append("where coapp.LEAD_ID='"+leadId+"'  and coapp.CUSTOMER_ROLE_TYPE='"+custType+"'  ");
	logger.info("getCoappDetailsList" + query);
	
	CreditProcessingLeadDetailDataVo leadVo = null;
	ArrayList coappdeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
	query=null;
	int size=coappdeatail.size();
	for (int i = 0; i < size; i++) {
		ArrayList coappdeatail1 = (ArrayList) coappdeatail.get(i);
		if (size > 0) {
			leadVo = new CreditProcessingLeadDetailDataVo();
			if(custType.equalsIgnoreCase("COAPPL")){
			leadVo.setCustId((CommonFunction.checkNull(coappdeatail1.get(0))).trim());
			leadVo.setCustomerName((CommonFunction.checkNull(coappdeatail1.get(1))).trim());
			leadVo.setCoAppcustomerType((CommonFunction.checkNull(coappdeatail1.get(2))).trim());
			leadVo.setCoApprelationship((CommonFunction.checkNull(coappdeatail1.get(3))).trim());
			leadVo.setCoAppcustCode((CommonFunction.checkNull(coappdeatail1.get(4))).trim());
			}else if(custType.equalsIgnoreCase("GUARANTOR")){
				leadVo.setCustId((CommonFunction.checkNull(coappdeatail1.get(0))).trim());
				leadVo.setCustomerName((CommonFunction.checkNull(coappdeatail1.get(1))).trim());
				leadVo.setGaurcustomerType((CommonFunction.checkNull(coappdeatail1.get(2))).trim());
				leadVo.setGaurrelationship((CommonFunction.checkNull(coappdeatail1.get(3))).trim());
				leadVo.setGaurcustCode((CommonFunction.checkNull(coappdeatail1.get(4))).trim());
			}
			
			list.add(leadVo);
		}
		coappdeatail1.clear();
		coappdeatail1=null;
	}
	leadVo=null;
} catch (Exception e) {
	e.printStackTrace();
}
finally{
	leadId=null;
}
return list;
}

public boolean deleteCustomer(String[] customer_id,String leadId)
{
	boolean status = false;
	ArrayList qryList = new ArrayList();
	 try
	    {
		 for (int k = 0; k < customer_id.length; k++)
	      {
			 String q1="select DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where lead_id='"+leadId+"' and LEAD_CUSTOMER_ID='"+customer_id[k]+"' ";
			String existingCustomer=ConnectionDAO.singleReturn(q1);
			String q2="";
			
			 if(CommonFunction.checkNull(existingCustomer).equalsIgnoreCase("Y")){
				  q2="delete from cr_deal_customer_role where lead_id='"+leadId+"' and LEAD_CUSTOMER_ID='"+customer_id[k]+"' ";
				  ArrayList lis1=new ArrayList();
				  lis1.add(q2);
				  boolean st=ConnectionDAO.sqlInsUpdDelete(lis1);
			 }else{
				 String dealCustomerQuery="select DEAL_CUSTOMER_ID from cr_deal_customer_role where lead_id='"+leadId+"' and LEAD_CUSTOMER_ID='"+customer_id[k]+"' ";
				 String dealCustomerId=ConnectionDAO.singleReturn(dealCustomerQuery);
				q2="delete from cr_deal_customer_m where customer_id='"+dealCustomerId+"' ";
				ArrayList lis1=new ArrayList();
				lis1.add(q2);
				  boolean st=ConnectionDAO.sqlInsUpdDelete(lis1);
				String q3="delete from cr_deal_address_m where BPID='"+dealCustomerId+"' ";
				ArrayList lis2=new ArrayList();
				lis2.add(q3);
				st=ConnectionDAO.sqlInsUpdDelete(lis2);
				q2="";
					q2="delete from cr_deal_customer_role where lead_id='"+leadId+"' and LEAD_CUSTOMER_ID='"+customer_id[k]+"' ";
				   lis1=new ArrayList();
				  lis1.add(q2);
				   st=ConnectionDAO.sqlInsUpdDelete(lis1);
				
			 }
	        String query2 = "delete from cr_lead_customer_m where CUSTOMER_ID=?";
	        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	        if (CommonFunction.checkNull(customer_id[k]).trim().equalsIgnoreCase(""))
	          insertPrepStmtObject.addNull();
	        else
	          insertPrepStmtObject.addString(CommonFunction.checkNull(customer_id[k]).trim());
	        insertPrepStmtObject.setSql(query2.toString());

	        qryList.add(insertPrepStmtObject);
		 }
		 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	    }catch (Exception e) {
	        e.printStackTrace();
	    }return status;
}
// lead header
public ArrayList<CreditProcessingLeadDetailDataVo> getLeadHeader(String leadId) {
	ArrayList<CreditProcessingLeadDetailDataVo> list = new ArrayList<CreditProcessingLeadDetailDataVo>();

	try {
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT distinct  l.LEAD_ID,l.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,DATE_FORMAT(l.LEAD_GENERATION_DATE,'%d-%m-%Y'),p.PRODUCT_CATEGORY  ");
				query.append("from cr_lead_dtl l  "); 
				query.append("left  JOIN cr_scheme_m s on l.SCHEME = s.SCHEME_ID   ");
				query.append("left  JOIN cr_product_m p on l.PRODUCT = p.PRODUCT_ID   ");
				query.append("where l.LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId))+"'  ");

		logger.info("getLeadCapturingDetailsList" + query);
		
		CreditProcessingLeadDetailDataVo leadVo = null;
		ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
		query=null;
		int size=leaddeatail.size();
		
		for (int i = 0; i < size; i++) {

			
			ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
			if (size > 0) {
				
				leadVo = new CreditProcessingLeadDetailDataVo();
				
				leadVo.setLeadId((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
				leadVo.setCustomerName((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				
				leadVo.setProduct((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
				leadVo.setScheme((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
				
				leadVo.setApplicationdate((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
				leadVo.setProductType((CommonFunction.checkNull(leaddeatail1.get(5))).trim());

				list.add(leadVo);
								}
			leaddeatail1.clear();
			leaddeatail1=null;
		}
		leadVo=null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		leadId=null;
	}
	return list;

}

@Override
public ArrayList getCopyApplicantAddress(String leadId) {
	ArrayList list = new ArrayList();

	try {
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,COUNTRY_DESC,STATE,STATE_DESC,DISTRICT,DISTRICT_DESC,"); 
		query.append("PINCODE,TAHSIL,DESCRIPTION,PRIMARY_PHONE,LANDMARK,EMAIL_ID ");
		query.append("FROM CR_LEAD_CUSTOMER_M A ");
		query.append("LEFT JOIN COM_COUNTRY_M C ON C.COUNTRY_ID=A.COUNTRY ");
		query.append("LEFT JOIN COM_STATE_M S ON S.STATE_ID=A.STATE ");
		query.append("LEFT JOIN COM_DISTRICT_M D ON D.DISTRICT_ID=A.DISTRICT ");
		query.append("LEFT JOIN COM_TEHSIL_M T ON T.ID=A.TAHSIL ");
		query.append("WHERE LEAD_ID='"+leadId+"' AND CUSTOMER_ROLE_TYPE='PRAPPL'");

		logger.info("getCopyApplicantAddress" + query);
		
		CreditProcessingLeadDetailDataVo leadVo = null;
		ArrayList leaddeatail = ConnectionDAOforEJB.sqlSelect(query.toString());
		query=null;
		int size=leaddeatail.size();
		
		for (int i = 0; i < size; i++) {

			
			ArrayList leaddeatail1 = (ArrayList) leaddeatail.get(i);
			if (size > 0) {
				
				leadVo = new CreditProcessingLeadDetailDataVo();
				
				leadVo.setAddresstype((CommonFunction.checkNull(leaddeatail1.get(0))).trim());
				leadVo.setAddress1((CommonFunction.checkNull(leaddeatail1.get(1))).trim());
				leadVo.setAddress2((CommonFunction.checkNull(leaddeatail1.get(2))).trim());
				leadVo.setAddress3((CommonFunction.checkNull(leaddeatail1.get(3))).trim());
				leadVo.setTxtCountryCode((CommonFunction.checkNull(leaddeatail1.get(4))).trim());
				leadVo.setCountry((CommonFunction.checkNull(leaddeatail1.get(5))).trim());
				leadVo.setTxtStateCode((CommonFunction.checkNull(leaddeatail1.get(6))).trim());
				leadVo.setState((CommonFunction.checkNull(leaddeatail1.get(7))).trim());
				leadVo.setTxtDistCode((CommonFunction.checkNull(leaddeatail1.get(8))).trim());
				leadVo.setDist((CommonFunction.checkNull(leaddeatail1.get(9))).trim());
				leadVo.setPincode((CommonFunction.checkNull(leaddeatail1.get(10))).trim());
				leadVo.setTxnTahsilHID((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
				leadVo.setTahsil((CommonFunction.checkNull(leaddeatail1.get(11))).trim());
				leadVo.setTahsilDesc((CommonFunction.checkNull(leaddeatail1.get(12))).trim());
				leadVo.setPhoneOff((CommonFunction.checkNull(leaddeatail1.get(13))).trim());
				leadVo.setLandmark((CommonFunction.checkNull(leaddeatail1.get(14))).trim());
				leadVo.setEmail((CommonFunction.checkNull(leaddeatail1.get(15))).trim());

				list.add(leadVo);
								}
			leaddeatail1.clear();
			leaddeatail1=null;
		}
		leadVo=null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		leadId=null;
	}
	return list;

}
}

