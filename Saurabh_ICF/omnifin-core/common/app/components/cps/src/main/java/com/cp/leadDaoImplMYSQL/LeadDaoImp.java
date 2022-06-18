package com.cp.leadDaoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.leadDao.LeadDao;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.CreditProcessingLeadEntryVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.LeadCaptureVo;
import com.cp.vo.LeadTrackingNotepadVo;
import com.cp.vo.LoanDetailVo;
import com.cp.vo.RefreshFlagVo;

public class LeadDaoImp implements LeadDao
{
	private static final Logger logger = Logger.getLogger(LeadDaoImp.class.getName());
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
			
		 query.append("SELECT distinct l.LEAD_ID,l.SOURCE_TYPE,u.USER_NAME,l.EXISTING_CUSTOMER,l.RELATIONSHIP_SINCE,l.CUSTOMER_NAME,l.CONTACT_PERSON,l.PERSON_DESIGNATION,"
					+ "l.ADDRESS_LINE1,l.ADDRESS_LINE2,l.ADDRESS_LINE3,c.COUNTRY_DESC,st.STATE_DESC,d.DISTRICT_DESC,l.PINCODE,l.PRIMARY_PHONE,"
					+ "l.ALTERNATE_PHONE,l.EMAIL_ID,l.ALTERNATE_MAIL_ID,p.PRODUCT_DESC,s.SCHEME_DESC,l.AMOUNT_REQUIRED,l.LOAN_TENURE,l.LOAN_PURPOSE,"
					+ "l.REC_STATUS,l.COUNTRY,l.STATE,l.DISTRICT,l.PRODUCT,l.SCHEME,l.LANDMARK,l.NO_OF_YEARS,"
					+ "im.INDUSTRY_DESC,sim.SUB_INDUSTRY_DESC,l.INDUSTRY_ID,l.SUB_INDUSTRY_ID,gm.description,DATE_FORMAT(l.LEAD_GENERATION_DATE,'" +dateFormat+ "')," +
							"p.PRODUCT_CATEGORY,l.CUSTOMER_TYPE,l.TURNOVER,l.ADDRESS_TYPE"
					+",l.GROUP_ID,l.CONSTITUTION,REGISTRATION_NO,l.PAN,l.BUSINESS_SEGMENT,l.FIRST_NAME,l.LAST_NAME,DATE_FORMAT(l.CUSTOMER_DOB,'"+dateFormat+"')," +
							"(select m.GROUP_DESC from gcd_group_m m where m.GROUP_ID=l.GROUP_ID) groupname,l.GROUP_TYPE,l.GROUP_DESC,l.LOAN_TYPE,L.OTHER,L.NO_OF_MONTHS,L.OWNERSHIP," +
							"l.FATHER_HUSBAND_NAME,l.PASSPORT_NUMBER,l.DRIVING_LICENSE,VOTER_ID,l.TAHSIL,l.SOURCE,gm.DESCRIPTION,l.SOURCE_DESCRIPTION,l.AREA_CODE," +
							"(SELECT AREA_CODE_NAME FROM com_areacode_m WHERE AREA_CODE=l.AREA_CODE) AS AREA_CODE_NAME,ctm.DESCRIPTION,l.EDU_DETAIL,l.SECTOR_TYPE"
					+ " from cr_lead_dtl l left outer JOIN com_country_m c on l.COUNTRY = c.COUNTRY_ID "
					+ "left outer JOIN sec_user_m u on l.LEAD_SERVICING_RM = u.USER_ID "
					+ "left outer JOIN com_district_m d on l.DISTRICT = d.DISTRICT_ID "
					+ "left outer JOIN com_state_m st on l.STATE = st.STATE_ID "
					+ "left outer JOIN cr_scheme_m s on l.SCHEME = s.SCHEME_ID "
					+ "left outer JOIN generic_master gm on l.source = gm.value and gm.generic_key='SOURCE_TYPE' "
					+ "left outer JOIN cr_product_m p on l.PRODUCT = p.PRODUCT_ID "
					+ "left outer JOIN com_industry_m im on l.INDUSTRY_ID = im.INDUSTRY_ID "
					+ "left outer JOIN com_sub_industry_m sim on l.SUB_INDUSTRY_ID = sim.sub_industry_id " +
					" left join com_tehsil_m ctm on ctm.ID=l.TAHSIL "
					+ "where LEAD_ID='"
					+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)) + "'");

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
								"FATHER_HUSBAND_NAME=?,PASSPORT_NUMBER=?, DRIVING_LICENSE=?, VOTER_ID=?,  TAHSIL=?, SOURCE=?, SOURCE_DESCRIPTION=?,AREA_CODE=?,ALLOCATION_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),ESCALATION_FLAG='"+esclationFlag+"',EDU_DETAIL=?,SECTOR_TYPE=?  where LEAD_ID=? ");

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
			 in.add((CommonFunction.checkNull(creditVo.getRelationshipSince()).trim()));//3
	       }
			if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 in.add(CommonFunction.checkNull(creditVo.getCustomerName()).trim());//4
			}else{
			in.add((CommonFunction.checkNull(creditVo.getFirstName()))+" "+(CommonFunction.checkNull(creditVo.getLastName())));
			}
		 in.add((CommonFunction.checkNull(creditVo.getContactPerson()).trim()));//5
		 in.add((CommonFunction.checkNull(creditVo.getPersonDesignation()).trim()));//6
		 in.add((CommonFunction.checkNull(creditVo.getAddress1()).trim()));//7
		 in.add((CommonFunction.checkNull(creditVo.getAddress2()).trim()));//8
		 in.add((CommonFunction.checkNull(creditVo.getAddress3()).trim()));//9
		 in.add((CommonFunction.checkNull(creditVo.getTxtCountryCode()).trim()));//10
		 in.add((CommonFunction.checkNull(creditVo.getTxtStateCode()).trim()));//11
		 in.add((CommonFunction.checkNull(creditVo.getTxtDistCode()).trim()));//12
		 in.add((CommonFunction.checkNull(creditVo.getPincode()).trim()));//13
		 in.add((CommonFunction.checkNull(creditVo.getPhoneOff()).trim()));//14
		 in.add((CommonFunction.checkNull(creditVo.getPhoneRes()).trim()));//15
		 in.add((CommonFunction.checkNull(creditVo.getEmail()).trim()));//16
		 in.add((CommonFunction.checkNull(creditVo.getAltEmail()).trim()));//17
		 in.add((CommonFunction.checkNull(creditVo.getLandmark()).trim()));//18
		 in.add((CommonFunction.checkNull(creditVo.getNoOfYears()).trim()));//19
		 in.add((CommonFunction.checkNull(creditVo.getNoMonths()).trim()));//20   -June---Sanjog

		 in.add((CommonFunction.checkNull(creditVo.getOwner()).trim()));//21     -June---Sanjog
		 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 in.add((CommonFunction.checkNull(creditVo.getLbxIndustry()).trim()));//22
		 	}else{
		 		in.add(0);	
		 	}
		 	if(creditVo.getCustomerType().equalsIgnoreCase("C")){
		 in.add((CommonFunction.checkNull(creditVo.getLbxSubIndustry()).trim()));//23
		 	}else{
		 		in.add(0);	
		 	}
		 in.add((CommonFunction.checkNull(creditVo.getLbxProductID()).trim()));//24
		 in.add((CommonFunction.checkNull(creditVo.getSchemeId()).trim()));//25
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
	 	if((creditVo.getTurnOver().equals(null))||(creditVo.getTurnOver().equals(""))){
	 		in.add("0.00");//39
	 	}else{
	 	 in.add(myFormatter.parse((CommonFunction.checkNull(creditVo.getTurnOver()).trim())).doubleValue());
	 	}
	 	}else{
	 		if((creditVo.getSalary().equals(null))||(creditVo.getSalary().equals(""))){
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
		 	in.add((CommonFunction.checkNull(creditVo.getTahsil())));//55
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
	 	     out.add("");	//62
	 		 out.add("");	//63
	    	 out.add("");	//64
	    	 
	    	outMessages=(ArrayList) ConnectionDAOforEJB.callSP("LEAD_GENERATOR",in,out);
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
						if(tracking.equalsIgnoreCase("3000116")){
								if(CommonFunction.checkNull(data.get(15)).equalsIgnoreCase("Y")){
								noteVo.setLeadno("<a href='leadTrackingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ "' style=color:red>"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
							}else{
								noteVo.setLeadno("<a href=leadTrackingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
							}
						
						}else{
							noteVo.setLeadno("<a href=leadCapturingBehind.do?leadId="+ (CommonFunction.checkNull(data.get(0))).trim()+ ">"+ (CommonFunction.checkNull(data.get(0))).trim() + "</a>");
					
						}
						
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
		logger.info("In deletelead(String id): ");
		try{
		query.append ("delete from cr_lead_dtl where LEAD_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and REC_STATUS='P'");
		list.add(query.toString());
		logger.info("deletelead()query------------------" + query.toString());
		status =ConnectionDAOforEJB.sqlInsUpdDelete(list);
		query=null;
		list=null;
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
			 		"CUSTOMER_EMAIL,(select GROUP_DESC from gcd_group_m where GROUP_ID=a.CUSTOMER_GROUP_ID) groupname, " +
			 		"(select COUNTRY_DESC from com_country_m where b.COUNTRY=COUNTRY_ID) country," +
			 		"(select DISTRICT_DESC from com_district_m where b.DISTRICT=DISTRICT_ID) district," +
			 		"(select STATE_DESC from com_state_m where b.STATE=STATE_ID) state," +
			 		"(select INDUSTRY_DESC from com_industry_m where a.CUSTOMER_INDUSTRY=INDUSTRY_ID) industry," +
			 		"(select SUB_INDUSTRY_DESC from com_sub_industry_m where a.CUSTOMER_SUB_INDUSTRY=sub_industry_id) subindustry," +
			 		"period_diff(date_format(STR_TO_DATE('"+bDate+"','%d-%m-%Y'), '%Y%m'), date_format(a.MAKER_DATE, '%Y%m')) as relationship_months,b.PRIMARY_PHONE "+
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


	public int saveCPLeadEntry(Object ob)
	  {
		String dateTime=null;
		CreditProcessingLeadEntryVo creditVo = (CreditProcessingLeadEntryVo)ob;
		if(CommonFunction.checkNull(creditVo.getLeadTime()).equalsIgnoreCase(""))
		{
			dateTime=creditVo.getLeadDate().trim();
		}
		else
		{
			dateTime=creditVo.getLeadDate().trim()+" "+creditVo.getLeadTime().trim().substring(0, 5);
		}
      
		logger.info("In saveCPLeadEntry() dateTime : "+dateTime);

		int maxId=0;
		String dealNoStatus=null;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		long vCode=0;
		if(creditVo.getLbxvendorCode()!=null && !creditVo.getLbxvendorCode().equalsIgnoreCase(""))
		{
			vCode=Long.parseLong(creditVo.getLbxvendorCode());
		}
		try
		{
		  if(creditVo.getDealNo()!=null && !CommonFunction.checkNull(creditVo.getDealNo()).equalsIgnoreCase(""))
		  {
			  StringBuilder query=new StringBuilder();
			  query.append("SELECT DEAL_ID FROM cr_deal_dtl WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(creditVo.getDealId()).trim())+"'");
			  dealNoStatus=ConnectionDAOforEJB.singleReturn(query.toString());
			  
			  query=null;
		  }

		if(dealNoStatus!=null && !dealNoStatus.equals(""))
		{
			boolean status = false;

			StringBuilder queryUpdate=new StringBuilder();
			 queryUpdate.append("update cr_deal_dtl set DEAL_APPLICATION_FORM_NO=?, DEAL_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),DEAL_ENCODED_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),DEAL_BRANCH=?," +
					" DEAL_INDUSTRY_ID=?,DEAL_SUB_INDUSTRY_ID=?, DEAL_SOURCE_TYPE=?,DEAL_SOURCE_NAME=?," +
					" DEAL_VENDOR_CODE=?,DEAL_RM=?,DEAL_CATEGORY=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where DEAL_ID=?");
			//PrepStmtObject prepStmt = new PrepStmtObject();
			if((CommonFunction.checkNull(creditVo.getApplicationFormNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getApplicationFormNo()).trim());
			
			if((CommonFunction.checkNull(dateTime)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
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
			
			if((CommonFunction.checkNull(creditVo.getSource())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSource()).trim());
			
			if((CommonFunction.checkNull(creditVo.getSourcedesc())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSourcedesc().trim()));
			
			if((CommonFunction.checkNull(vCode)).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(vCode);
			
			if(CommonFunction.checkNull((creditVo.getLbxRelationship())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxRelationship()).trim());
			
			
			if((CommonFunction.checkNull(creditVo.getDealCat())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealCat()).trim());
			
			if(CommonFunction.checkNull((creditVo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getMakerId()).trim());
			
			
			if((CommonFunction.checkNull(creditVo.getMakerDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getMakerDate()).trim());
			
			if((CommonFunction.checkNull(creditVo.getDealId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealId()).trim());

			insertPrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN saveCPLeadEntry() update query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			queryUpdate=null;
			insertPrepStmtObject=null;
			
			try
			{
				status =ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				qryList=null;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			

				if(status)
				{
					 maxId=Integer.parseInt(creditVo.getDealId());
				}

		}
		else
		{
		 
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("insert into cr_deal_dtl(LEAD_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,DEAL_DATE,DEAL_ENCODED_DATE,DEAL_BRANCH,DEAL_INDUSTRY_ID,DEAL_SUB_INDUSTRY_ID,DEAL_SOURCE_TYPE,DEAL_SOURCE_NAME,DEAL_VENDOR_CODE,DEAL_RM,DEAL_CATEGORY,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
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
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );

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
			if((CommonFunction.checkNull(creditVo.getSource())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSource()).trim());
			if((CommonFunction.checkNull(creditVo.getSourcedesc())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getSourcedesc()).trim());

			insertPrepStmtObject.addString((""+vCode).trim());

			if((CommonFunction.checkNull(creditVo.getLbxRelationship())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getLbxRelationship()).trim());
			
			if((CommonFunction.checkNull(creditVo.getDealCat())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getDealCat()).trim());

			insertPrepStmtObject.addString("P");//Status

			if((CommonFunction.checkNull(creditVo.getUserId())).trim().equals(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getUserId()).trim());//maker Id

			if((CommonFunction.checkNull(creditVo.getBussinessDate())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((creditVo.getBussinessDate()));

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCPLeadEntry() insert query1 ### "+insertPrepStmtObject.printQuery());
			
			
			bufInsSql=null;
			
			qryList.add(insertPrepStmtObject);
			insertPrepStmtObject = null;
			boolean status=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			qryList = null;
		    if(status)
			{
				StringBuilder query3=new StringBuilder();
				query3.append("Select distinct max(DEAL_ID) from cr_deal_dtl for update");
				  
				StringBuilder id=new StringBuilder();
				id.append(ConnectionDAOforEJB.singleReturn(query3.toString()));
				maxId=Integer.parseInt(id.toString());
				  RefreshFlagVo vo = new RefreshFlagVo();
					vo.setRecordId(maxId);
					vo.setModuleName("CP");
					vo.setFlagValue("NNNNNNNNNNNNNNN");
					
					StringBuilder checkStageM=new StringBuilder();
				    checkStageM.append(CommonFunction.stageMovement(creditVo.getCompanyId(), "DC","I",""+maxId, "DC", creditVo.getBussinessDate(),creditVo.getUserId()));
				 query3=null;
				 checkStageM=null;
				 id=null;
			}

		}


		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			dealNoStatus=null;
			ob=null;
			dateTime=null;
			creditVo=null;
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
    public String getPincodeFlag() 
    {
    	logger.info("In getPincodeFlag()");
    	String ediFlag="";
    	try
    	{
    		StringBuilder query=new StringBuilder();
    		query.append("select parameter_value from parameter_mst where parameter_key='ASSET_COLLATERAL_PROPERTY_PINCODE'" );
    		logger.info("In getPincodeFlag() query  :  "+query.toString());
    		ediFlag = ConnectionDAO.singleReturn(query.toString());
    		logger.info("In getPincodeFlag() ediFlag  :  "+ediFlag);
    	}
    	catch(Exception e)
    	{e.printStackTrace();}
    	return ediFlag;
    }
}

