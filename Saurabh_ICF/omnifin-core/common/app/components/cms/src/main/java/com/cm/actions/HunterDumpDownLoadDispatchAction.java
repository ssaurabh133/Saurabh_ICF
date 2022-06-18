package com.cm.actions; 

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.DumpDownLoadDAO;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class HunterDumpDownLoadDispatchAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(DumpDownLoadDispatchAction.class.getName());

	public ActionForward hunterReportGenerator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long startTime = System.currentTimeMillis();

		logger.info("Virender Hunter");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = null;
		if (userobj != null) {
			userId = userobj.getUserId();
		} else {
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag = null;
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		//Main Logic Starts
		String hunterFormat = request.getParameter("formatVar");
		hunterFormat=hunterFormat.substring(0,3);
		logger.info("formatVar :" + hunterFormat);// CSV or XML
		String hunterCustTypeVar = request.getParameter("custTypeVar");
		hunterCustTypeVar=hunterCustTypeVar.substring(0,1);
		logger.info("custTypeVar :" + hunterCustTypeVar);// C or I

		ArrayList dealDtl = new ArrayList();
		ArrayList custDtl = new ArrayList();
		ArrayList HunterData = new ArrayList();

		DumpDownLoadDAO HunterObj = (DumpDownLoadDAO) DaoImplInstanceFactory.getDaoImplInstance(DumpDownLoadDAO.IDENTITY);
		dealDtl = HunterObj.fetchDealID(hunterCustTypeVar);// lists all deal id
		int dealIdSize = dealDtl.size();
		logger.info("dealIdSize :" + dealIdSize);//194

		// logic for CSV
		if (hunterFormat.equals("CSV")) {

			response.setContentType("text/csv"); 
			response.setHeader("Content-Disposition", "attachment; filename=HunterDumpDownload.csv"); 
			try {
				ServletOutputStream out = response.getOutputStream();//provides a stream to write binary data into the response

				if(hunterCustTypeVar.equals("I")){
					logger.info("Individual ");

					//for applicant 
					out.write(("IDENTIFIER").getBytes());
					out.write('|');
					out.write(("PRODUCT").getBytes());
					out.write('|');
					out.write(("CLASSIFICATION").getBytes());
					out.write('|');
					out.write(("DATE").getBytes());
					out.write('|');
					out.write(("APP_DTE").getBytes());
					out.write('|');
					out.write(("TERM").getBytes());
					out.write('|');
					out.write(("APP_VAL").getBytes());
					out.write('|');
					out.write(("ASS_ORIG_VAL").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MA_PAN").getBytes());
					out.write('|');
					out.write(("MA_FST_NME").getBytes());
					out.write('|');
					out.write(("MA_MID_NME").getBytes());
					out.write('|');
					out.write(("MA_LST_NME").getBytes());
					out.write('|');
					out.write(("MA_DOB").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_CA_ADD").getBytes());
					out.write('|');
					out.write(("MA_CA_CTY").getBytes());
					out.write('|');
					out.write(("MA_CA_STE").getBytes());
					out.write('|');
					out.write(("MA_CA_CTRY").getBytes());
					out.write('|');			
					out.write(("MA_CA_PIN").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_PMA_ADD").getBytes());
					out.write('|');
					out.write(("MA_PMA_CTY").getBytes());
					out.write('|');
					out.write(("MA_PMA_STE").getBytes());
					out.write('|');
					out.write(("MA_PMA_CTRY").getBytes());
					out.write('|');
					out.write(("MA_PMA_PIN").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_HT_TEL_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');				
					out.write(("MA_MT_TEL_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_BT_TEL_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_EMA_EMA_ADD").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_BNK_NM").getBytes());
					out.write('|');
					out.write(("MA_BNK_ACC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MA_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_EMP_ORG_NME").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');
					out.write(("MA_EMP_AD_ADD").getBytes());
					out.write('|');
					out.write(("MA_EMP_AD_CTY").getBytes());
					out.write('|');
					out.write(("MA_EMP_AD_STE").getBytes());
					out.write('|');
					out.write(("MA_EMP_AD_CTRY").getBytes());
					out.write('|');
					out.write(("MA_EMP_AD_PIN").getBytes());

					//for Co-Applicant
					for(int i=0;i<9;i++){
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_PAN").getBytes());
						out.write('|');
						out.write(("JA_FST_NME").getBytes());
						out.write('|');
						out.write(("JA_MID_NME").getBytes());
						out.write('|');
						out.write(("JA_LST_NME").getBytes());
						out.write('|');
						out.write(("JA_DOB").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_CA_ADD").getBytes());
						out.write('|');
						out.write(("JA_CA_CTY").getBytes());
						out.write('|');
						out.write(("JA_CA_STE").getBytes());
						out.write('|');
						out.write(("JA_CA_CTRY").getBytes());
						out.write('|');
						out.write(("JA_CA_PIN").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');					
						out.write(("JA_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("JA_ID_DOC_NO").getBytes());
					}

					out.write('\n');

					for (int i = 0; i < dealIdSize; i++) {

						String dealId = dealDtl.get(i).toString();
						dealId=dealId.replace("[","").replace("]","");
						custDtl = HunterObj.HunterCustCSV(dealId);					
						int custIdSize = custDtl.size();
						int HunterDataSize=0;

						for (int j = 0; j < custIdSize; j++) {
							int custFlag=0;
							String custId = custDtl.get(j).toString();
							custId=custId.replace("[","").replace("]","");

							if(j==0){
								//HunterData=HunterObj.fetchHunterData(dealId,custId);
								HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
							}
							else if(j>0 && j<10){
								//HunterData=HunterObj.fetchHunterCOAPPLData(dealId,custId);
								custFlag=1;
								HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
							}
							else{
								break;
							}
							HunterDataSize=HunterData.size();

							for(int x=0;x<HunterDataSize;x++){
								out.write(HunterData.get(x).toString().getBytes());
								out.write('|');
							}
							HunterData=null;
						}
						out.write('\n');
					}
					logger.info("CSV file created for Individual Customer Type...");
				}
				else{
					out.write(("IDENTIFIER").getBytes());
					out.write('|');
					out.write(("PRODUCT").getBytes());
					out.write('|');
					out.write(("CLASSIFICATION").getBytes());
					out.write('|');
					out.write(("DATE").getBytes());
					out.write('|');
					out.write(("APP_DTE").getBytes());
					out.write('|');
					out.write(("TERM").getBytes());
					out.write('|');
					out.write(("APP_VAL").getBytes());
					out.write('|');
					out.write(("ASS_ORIG_VAL").getBytes());

					//for corporate Type Customer
					for(int i=0;i<2;i++){
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("SME_ORG_NME").getBytes());
						out.write('|');
						out.write(("SME_TAN_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("MAC_ADD_ADD").getBytes());
						out.write('|');
						out.write(("MAC_ADD_CTY").getBytes());
						out.write('|');
						out.write(("MAC_ADD_STE").getBytes());
						out.write('|');
						out.write(("MAC_ADD_CTRY").getBytes());
						out.write('|');		
						out.write(("MAC_ADD_PIN").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("MAC_TEL_TEL_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("MAC_BNK_NM").getBytes());
						out.write('|');
						out.write(("MAC_BNK_ACC_NO").getBytes());
						out.write('|');
						out.write(("STATUS").getBytes());
						out.write('|');
						out.write(("MAC_EMA_EMA_ADD").getBytes());
					}
					//for Individual Type Customer -Main Applicant(PRAPPL)			
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_PAN").getBytes());
					out.write('|');	
					out.write(("MP_FST_NME").getBytes());
					out.write('|');	
					out.write(("MP_MID_NME").getBytes());
					out.write('|');	
					out.write(("MP_LST_NME").getBytes());		
					out.write('|');	
					out.write(("MP_DOB").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_CA_ADD").getBytes());
					out.write('|');	
					out.write(("MP_CA_CTY").getBytes());
					out.write('|');	
					out.write(("MP_CA_STE").getBytes());
					out.write('|');
					out.write(("MP_CA_CTRY").getBytes());
					out.write('|');	
					out.write(("MP_CA_PIN").getBytes());		
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_PMA_ADD").getBytes());
					out.write('|');	
					out.write(("MP_PMA_CTY").getBytes());
					out.write('|');	
					out.write(("MP_PMA_STE").getBytes());
					out.write('|');	
					out.write(("MP_PMA_CTRY").getBytes());
					out.write('|');	
					out.write(("MP_PMA_PIN").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());		
					out.write('|');	
					out.write(("MP_HT_TEL_NO").getBytes());
					out.write('|');		
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_MT_TEL_NO").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_EMA_EMA_ADD").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_BNK_NM").getBytes());
					out.write('|');	
					out.write(("MP_BNK_ACC_NO").getBytes());	
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_TYP").getBytes());
					out.write('|');
					out.write(("MP_ID_DOC_NO").getBytes());
					out.write('|');
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_TYP").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_NO").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_TYP").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_NO").getBytes());
					out.write('|');	
					out.write(("STATUS").getBytes());	
					out.write('|');	
					out.write(("MP_ID_DOC_TYP").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_NO").getBytes());	
					out.write('|');	
					out.write(("STATUS").getBytes());
					out.write('|');	
					out.write(("MP_ID_DOC_TYP").getBytes());	
					out.write('|');	
					out.write(("MP_ID_DOC_NO").getBytes());

					//for Individual Type Customer -Co-Applicant(COAPPL)
					for (int i = 0; i < 4; i++) {
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_PAN").getBytes());
						out.write('|');	
						out.write(("CP_FST_NME").getBytes());
						out.write('|');	
						out.write(("CP_MID_NME").getBytes());
						out.write('|');	
						out.write(("CP_LST_NME").getBytes());		
						out.write('|');	
						out.write(("CP_DOB").getBytes());
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_CA_ADD").getBytes());
						out.write('|');	
						out.write(("CP_CA_CTY").getBytes());
						out.write('|');	
						out.write(("CP_CA_STE").getBytes());
						out.write('|');	
						out.write(("CP_CA_CTRY").getBytes());
						out.write('|');	
						out.write(("CP_CA_PIN").getBytes());		
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_TYP").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_NO").getBytes());
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_TYP").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_NO").getBytes());
						out.write('|');	
						out.write(("STATUS").getBytes());		
						out.write('|');	
						out.write(("CP_ID_DOC_TYP").getBytes());
						out.write('|');
						out.write(("CP_ID_DOC_NO").getBytes());
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_TYP").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_NO").getBytes());
						out.write('|');	
						out.write(("STATUS").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_TYP").getBytes());
						out.write('|');	
						out.write(("CP_ID_DOC_NO").getBytes());						
					}
					out.write('\n');

					for (int i = 0; i < dealIdSize; i++) {

						String dealId = dealDtl.get(i).toString();
						dealId=dealId.replace("[","").replace("]","");

						custDtl=HunterObj.CorpHunterCustCSV(dealId);
						int custIdSize = custDtl.size();	

						for (int j = 0; j < custIdSize; j++) {
							boolean exitHuntFlag=true;
							int HunterDataSize=0;
							int custFlag=2;
							String custId = custDtl.get(j).toString();
							custId=custId.replace("[","").replace("]","");

							if(j==0){
								HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
							}
							else if(j==1){
								if(custId.equals("X")){
									exitHuntFlag=false;
									for(int l=0;l<16;l++){
										out.write('|');
									} 
								}
								else{
									custFlag=3;
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
								}
							}
							else if(j==2){
									custFlag=4;
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
							}
							else{
								custFlag=5;
								HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);
							}

							if(exitHuntFlag){
								HunterDataSize=HunterData.size();

								for(int x=0;x<HunterDataSize;x++){
									out.write(HunterData.get(x).toString().getBytes());
									out.write('|');
								}
								HunterData=null;
							}
						}
						out.write('\n');
					}
					logger.info("CSV file created for Corporate Customer Type...");
				}
				out.flush();
			} catch (Exception e) {
				log.error(e);
			}
		}
		// logic for XML
		else {
			//XML work start
			response.setContentType("text/xml"); 
			response.setHeader("Content-Disposition", "attachment; filename=HunterDumpDownload_Individual_XML.xml"); 
			try {
				ServletOutputStream out = response.getOutputStream();
				//int maxCustNo=HunterObj.fetchMaxCustNo(dealDtl,hunterCustTypeVar);// fetch maximum no of custId

				if(hunterCustTypeVar.equals("I")){
					logger.info("Individual");

					try{
						DocumentBuilderFactory docFac=DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuild=docFac.newDocumentBuilder();
						Document doc=docBuild.newDocument();

						String xsi="xmlns:xsi",xsd="xmlns:xsd";	
						Element batch=doc.createElement("BATCH");
						doc.appendChild(batch);
						batch.setAttribute(xsi,"http://www.w3.org/2001/XMLSchema-instance");
						batch.setAttribute(xsd,"http://www.w3.org/2001/XMLSchema");
						batch.setAttribute("xmlns","urn:mclsoftware.co.uk:hunterII");

						Element header=doc.createElement("HEADER");
						batch.appendChild(header);
						Element count=doc.createElement("COUNT");
						count.appendChild(doc.createTextNode(dealIdSize+""));
						header.appendChild(count);	
						Element originator=doc.createElement("ORIGINATOR");
						originator.appendChild(doc.createTextNode("IDF"));
						header.appendChild(originator);
						Element submissions=doc.createElement("SUBMISSIONS");
						batch.appendChild(submissions);

						for (int i = 0; i < dealIdSize; i++) {
							
							Element submission=doc.createElement("SUBMISSION");
							submissions.appendChild(submission);
							
							String dealId = dealDtl.get(i).toString();
							dealId=dealId.replace("[","").replace("]","");
							custDtl = HunterObj.HunterCustCSV(dealId);					
							int custIdSize = custDtl.size();
							int HunterDataSize=0;

							for (int j = 0; j < custIdSize; j++) {
								int custFlag=0;
								String custId = custDtl.get(j).toString();
								custId=custId.replace("[","").replace("]","");
								
								if(j==0){
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

									Element identifier=doc.createElement("IDENTIFIER");
									identifier.appendChild(doc.createTextNode(HunterData.get(0).toString()));
									submission.appendChild(identifier);
									Element product=doc.createElement("PRODUCT");
									product.appendChild(doc.createTextNode(HunterData.get(1).toString()));
									submission.appendChild(product);
									Element classification=doc.createElement("CLASSIFICATION");
									classification.appendChild(doc.createTextNode(HunterData.get(2).toString()));
									submission.appendChild(classification);
									Element date=doc.createElement("DATE");
									date.appendChild(doc.createTextNode(HunterData.get(3).toString()));
									submission.appendChild(date);
									Element app_dte=doc.createElement("APP_DTE");
									app_dte.appendChild(doc.createTextNode(HunterData.get(4).toString()));
									submission.appendChild(app_dte);
									Element term=doc.createElement("TERM");
									term.appendChild(doc.createTextNode(HunterData.get(5).toString()));
									submission.appendChild(term);
									Element app_val=doc.createElement("APP_VAL");
									app_val.appendChild(doc.createTextNode(HunterData.get(6).toString()));
									submission.appendChild(app_val);
									Element ass_orig_val=doc.createElement("ASS_ORIG_VAL");
									ass_orig_val.appendChild(doc.createTextNode(HunterData.get(7).toString()));
									submission.appendChild(ass_orig_val);
									Element ma=doc.createElement("MA");
									submission.appendChild(ma);		
									Element status=doc.createElement("STATUS");
									status.appendChild(doc.createTextNode(HunterData.get(8).toString()));
									ma.appendChild(status);
									Element pan=doc.createElement("PAN");
									pan.appendChild(doc.createTextNode(HunterData.get(9).toString()));
									ma.appendChild(pan);
									Element fst_nme=doc.createElement("FST_NME");
									fst_nme.appendChild(doc.createTextNode(HunterData.get(10).toString()));
									ma.appendChild(fst_nme);
									Element mid_nme=doc.createElement("MID_NME");
									mid_nme.appendChild(doc.createTextNode(HunterData.get(11).toString()));
									ma.appendChild(mid_nme);
									Element lst_nme=doc.createElement("LST_NME");
									lst_nme.appendChild(doc.createTextNode(HunterData.get(12).toString()));
									ma.appendChild(lst_nme);
									Element dob=doc.createElement("DOB");
									dob.appendChild(doc.createTextNode(HunterData.get(13).toString()));
									ma.appendChild(dob);
									Element ma_ca=doc.createElement("MA_CA");
									ma.appendChild(ma_ca);
									Element ma_ca_status=doc.createElement("STATUS");
									ma_ca_status.appendChild(doc.createTextNode(HunterData.get(14).toString()));
									ma_ca.appendChild(ma_ca_status);
									Element ma_ca_add=doc.createElement("ADD");
									ma_ca_add.appendChild(doc.createTextNode(HunterData.get(15).toString()));
									ma_ca.appendChild(ma_ca_add);
									Element ma_ca_city=doc.createElement("CTY");
									ma_ca_city.appendChild(doc.createTextNode(HunterData.get(16).toString()));
									ma_ca.appendChild(ma_ca_city);
									Element ma_ca_ste=doc.createElement("STE");
									ma_ca_ste.appendChild(doc.createTextNode(HunterData.get(17).toString()));
									ma_ca.appendChild(ma_ca_ste);
									Element ma_ca_ctry=doc.createElement("CTRY");
									ma_ca_ctry.appendChild(doc.createTextNode(HunterData.get(18).toString()));
									ma_ca.appendChild(ma_ca_ctry);
									Element ma_ca_pin=doc.createElement("PIN");
									ma_ca_pin.appendChild(doc.createTextNode(HunterData.get(19).toString()));
									ma_ca.appendChild(ma_ca_pin);
									Element ma_ht=doc.createElement("MA_HT");
									ma.appendChild(ma_ht);
									Element ma_ht_status=doc.createElement("STATUS");
									ma_ht_status.appendChild(doc.createTextNode(HunterData.get(20).toString()));
									ma_ht.appendChild(ma_ht_status);
									Element ma_ht_tel_no=doc.createElement("TEL_NO");
									ma_ht_tel_no.appendChild(doc.createTextNode(HunterData.get(21).toString()));
									ma_ht.appendChild(ma_ht_tel_no);
									Element ma_mt=doc.createElement("MA_MT");
									ma.appendChild(ma_mt);
									Element ma_mt_status=doc.createElement("STATUS");
									ma_mt_status.appendChild(doc.createTextNode(HunterData.get(22).toString()));
									ma_mt.appendChild(ma_mt_status);
									Element ma_mt_tel_no=doc.createElement("TEL_NO");
									ma_mt_tel_no.appendChild(doc.createTextNode(HunterData.get(23).toString()));
									ma_mt.appendChild(ma_mt_tel_no);
									Element ma_bt=doc.createElement("MA_BT");
									ma.appendChild(ma_bt);
									Element ma_bt_status=doc.createElement("STATUS");
									ma_bt_status.appendChild(doc.createTextNode(HunterData.get(24).toString()));
									ma_bt.appendChild(ma_bt_status);
									Element ma_bt_tel_no=doc.createElement("TEL_NO");
									ma_bt_tel_no.appendChild(doc.createTextNode(HunterData.get(25).toString()));
									ma_bt.appendChild(ma_bt_tel_no);
									Element ma_id=doc.createElement("MA_ID");
									ma.appendChild(ma_id);
									Element ma_id_status=doc.createElement("STATUS");
									ma_id_status.appendChild(doc.createTextNode(HunterData.get(26).toString()));
									ma_id.appendChild(ma_id_status);
									Element ma_id_doc_typ=doc.createElement("DOC_TYP");
									ma_id_doc_typ.appendChild(doc.createTextNode(HunterData.get(27).toString()));
									ma_id.appendChild(ma_id_doc_typ);
									Element ma_id_doc_no=doc.createElement("DOC_NO");
									ma_id_doc_no.appendChild(doc.createTextNode(HunterData.get(28).toString()));
									ma_id.appendChild(ma_id_doc_no);
									Element ma_emp=doc.createElement("MA_EMP");
									ma.appendChild(ma_emp);
									Element ma_emp_status=doc.createElement("STATUS");
									ma_emp_status.appendChild(doc.createTextNode(HunterData.get(29).toString()));
									ma_emp.appendChild(ma_emp_status);
									Element ma_emp_org_name=doc.createElement("ORG_NME");
									ma_emp_org_name.appendChild(doc.createTextNode(HunterData.get(30).toString()));
									ma_emp.appendChild(ma_emp_org_name);
									Element ma_emp_ad=doc.createElement("MA_EMP_AD");
									ma_emp.appendChild(ma_emp_ad);
									Element ma_emp_ad_status=doc.createElement("STATUS");
									ma_emp_ad_status.appendChild(doc.createTextNode(HunterData.get(31).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_status);
									Element ma_emp_ad_add=doc.createElement("ADD");
									ma_emp_ad_add.appendChild(doc.createTextNode(HunterData.get(32).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_add);
									Element ma_emp_ad_city=doc.createElement("CTY");
									ma_emp_ad_city.appendChild(doc.createTextNode(HunterData.get(33).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_city);
									Element ma_emp_ad_ste=doc.createElement("STE");
									ma_emp_ad_ste.appendChild(doc.createTextNode(HunterData.get(34).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_ste);
									Element ma_emp_ad_ctry=doc.createElement("CTRY");
									ma_emp_ad_ctry.appendChild(doc.createTextNode(HunterData.get(35).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_ctry);
									Element ma_emp_ad_pin=doc.createElement("PIN");
									ma_emp_ad_pin.appendChild(doc.createTextNode(HunterData.get(36).toString()));
									ma_emp_ad.appendChild(ma_emp_ad_pin);

								}
								else if(j>0 && j<10){
									custFlag=1;
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

									Element ja1=doc.createElement("JA");
									submission.appendChild(ja1);	
									Element ja1_status=doc.createElement("STATUS");
									ja1_status.appendChild(doc.createTextNode(HunterData.get(0).toString()));
									ja1.appendChild(ja1_status);
									Element ja1_pan=doc.createElement("PAN");
									ja1_pan.appendChild(doc.createTextNode(HunterData.get(1).toString()));
									ja1.appendChild(ja1_pan);
									Element ja1_fst_nme=doc.createElement("FST_NME");
									ja1_fst_nme.appendChild(doc.createTextNode(HunterData.get(2).toString()));
									ja1.appendChild(ja1_fst_nme);
									Element ja1_mid_nme=doc.createElement("MID_NME");
									ja1_mid_nme.appendChild(doc.createTextNode(HunterData.get(3).toString()));
									ja1.appendChild(ja1_mid_nme);
									Element ja1_lst_nme=doc.createElement("LST_NME");
									ja1_lst_nme.appendChild(doc.createTextNode(HunterData.get(4).toString()));
									ja1.appendChild(ja1_lst_nme);
									Element ja1_dob=doc.createElement("DOB");
									ja1_dob.appendChild(doc.createTextNode(HunterData.get(5).toString()));
									ja1.appendChild(ja1_dob);
									Element ja1_ca=doc.createElement("JA_CA");
									ja1.appendChild(ja1_ca);
									Element ja1_ca_status=doc.createElement("STATUS");
									ja1_ca_status.appendChild(doc.createTextNode(HunterData.get(6).toString()));
									ja1_ca.appendChild(ja1_ca_status);
									Element ja1_ca_add=doc.createElement("ADD");
									ja1_ca_add.appendChild(doc.createTextNode(HunterData.get(7).toString()));
									ja1_ca.appendChild(ja1_ca_add);
									Element ja1_ca_city=doc.createElement("CTY");
									ja1_ca_city.appendChild(doc.createTextNode(HunterData.get(8).toString()));
									ja1_ca.appendChild(ja1_ca_city);
									Element ja1_ca_ste=doc.createElement("STE");
									ja1_ca_ste.appendChild(doc.createTextNode(HunterData.get(9).toString()));
									ja1_ca.appendChild(ja1_ca_ste);
									Element ja1_ca_ctry=doc.createElement("CTRY");
									ja1_ca_ctry.appendChild(doc.createTextNode(HunterData.get(10).toString()));
									ja1_ca.appendChild(ja1_ca_ctry);
									Element ja1_ca_pin=doc.createElement("PIN");
									ja1_ca_pin.appendChild(doc.createTextNode(HunterData.get(11).toString()));
									ja1_ca.appendChild(ja1_ca_pin);
									Element ja1_id=doc.createElement("JA_ID");
									ja1.appendChild(ja1_id);
									Element ja1_id_status=doc.createElement("STATUS");
									ja1_id_status.appendChild(doc.createTextNode(HunterData.get(12).toString()));
									ja1_id.appendChild(ja1_id_status);
									Element ja1_id_doc_typ=doc.createElement("DOC_TYP");
									ja1_id_doc_typ.appendChild(doc.createTextNode(HunterData.get(13).toString()));
									ja1_id.appendChild(ja1_id_doc_typ);
									Element ja1_id_doc_no=doc.createElement("DOC_NO");
									ja1_id_doc_no.appendChild(doc.createTextNode(HunterData.get(14).toString()));
									ja1_id.appendChild(ja1_id_doc_no);

								}
								else{
									break;
								}
							}
						}
						TransformerFactory transFact=TransformerFactory.newInstance();
						Transformer tf=transFact.newTransformer();
						DOMSource src=new DOMSource(doc);
						StreamResult result=new StreamResult(response.getOutputStream());
						tf.transform(src, result);
					}
					catch(ParserConfigurationException pce){
					}
					catch(TransformerException te){
					}
				}
				else{
					logger.info("Corporate ");
					try{
						DocumentBuilderFactory docFac=DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuild=docFac.newDocumentBuilder();
						Document doc=docBuild.newDocument();

						Element batch=doc.createElement("BATCH");
						doc.appendChild(batch);
						batch.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
						batch.setAttribute("xmlns:xsd","http://www.w3.org/2001/XMLSchema");
						batch.setAttribute("xmlns","urn:mclsoftware.co.uk:hunterII");

						Element header=doc.createElement("HEADER");
						batch.appendChild(header);
						Element count=doc.createElement("COUNT");
						count.appendChild(doc.createTextNode(dealIdSize+""));
						header.appendChild(count);	
						Element originator=doc.createElement("ORIGINATOR");
						originator.appendChild(doc.createTextNode("IDF"));
						header.appendChild(originator);
						Element submissions=doc.createElement("SUBMISSIONS");
						batch.appendChild(submissions);

						for (int i = 0; i < dealIdSize; i++) {

							Element submission=doc.createElement("SUBMISSION");
							submissions.appendChild(submission);	

							String dealId = dealDtl.get(i).toString();
							dealId=dealId.replace("[","").replace("]","");
							custDtl=HunterObj.CorpHunterCustCSV(dealId);
							int custIdSize = custDtl.size();	

							for (int j = 0; j < custIdSize; j++) {
								int HunterDataSize=0;
								int custFlag=2;
								String custId = custDtl.get(j).toString();
								custId=custId.replace("[","").replace("]","");
								
								//for first Corporate
								if(j==0){								
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

									Element identifier=doc.createElement("IDENTIFIER");
									identifier.appendChild(doc.createTextNode(HunterData.get(0).toString()));
									submission.appendChild(identifier);
									Element product=doc.createElement("PRODUCT");
									product.appendChild(doc.createTextNode(HunterData.get(1).toString()));
									submission.appendChild(product);
									Element classification=doc.createElement("CLASSIFICATION");
									classification.appendChild(doc.createTextNode(HunterData.get(2).toString()));
									submission.appendChild(classification);
									Element date=doc.createElement("DATE");
									date.appendChild(doc.createTextNode(HunterData.get(3).toString()));
									submission.appendChild(date);
									Element app_dte=doc.createElement("APP_DTE");
									app_dte.appendChild(doc.createTextNode(HunterData.get(4).toString()));
									submission.appendChild(app_dte);
									Element term=doc.createElement("TERM");
									term.appendChild(doc.createTextNode(HunterData.get(5).toString()));
									submission.appendChild(term);
									Element app_val=doc.createElement("APP_VAL");
									app_val.appendChild(doc.createTextNode(HunterData.get(6).toString()));
									submission.appendChild(app_val);
									Element ass_orig_val=doc.createElement("ASS_ORIG_VAL");
									ass_orig_val.appendChild(doc.createTextNode(HunterData.get(7).toString()));
									submission.appendChild(ass_orig_val);
									Element sme=doc.createElement("SME");
									submission.appendChild(sme);		
									Element status=doc.createElement("STATUS");
									status.appendChild(doc.createTextNode(HunterData.get(8).toString()));
									sme.appendChild(status);
									Element org_name=doc.createElement("ORG_NME");
									org_name.appendChild(doc.createTextNode(HunterData.get(9).toString()));
									sme.appendChild(org_name);
									Element tan_no=doc.createElement("TAN_NO");
									tan_no.appendChild(doc.createTextNode(HunterData.get(10).toString()));
									sme.appendChild(tan_no);
									Element mac_add=doc.createElement("MAC_ADD");
									sme.appendChild(mac_add);
									Element mac_add_status=doc.createElement("STATUS");
									mac_add_status.appendChild(doc.createTextNode(HunterData.get(11).toString()));
									mac_add.appendChild(mac_add_status);
									Element mac_add_add=doc.createElement("ADD");
									mac_add_add.appendChild(doc.createTextNode(HunterData.get(12).toString()));
									mac_add.appendChild(mac_add_add);
									Element ma_add_city=doc.createElement("CTY");
									ma_add_city.appendChild(doc.createTextNode(HunterData.get(13).toString()));
									mac_add.appendChild(ma_add_city);
									Element ma_add_ste=doc.createElement("STE");
									ma_add_ste.appendChild(doc.createTextNode(HunterData.get(14).toString()));
									mac_add.appendChild(ma_add_ste);
									Element mac_add_ctry=doc.createElement("CTRY");
									mac_add_ctry.appendChild(doc.createTextNode(HunterData.get(15).toString()));
									mac_add.appendChild(mac_add_ctry);
									Element mac_add_pin=doc.createElement("PIN");
									mac_add_pin.appendChild(doc.createTextNode(HunterData.get(16).toString()));
									mac_add.appendChild(mac_add_pin);
									Element mac_tel=doc.createElement("MAC_TEL");
									sme.appendChild(mac_tel);
									Element mac_tel_status=doc.createElement("STATUS");
									mac_tel_status.appendChild(doc.createTextNode(HunterData.get(17).toString()));
									mac_tel.appendChild(mac_tel_status);
									Element mac_tel_tel_no=doc.createElement("TEL_NO");
									mac_tel_tel_no.appendChild(doc.createTextNode(HunterData.get(18).toString()));
									mac_tel.appendChild(mac_tel_tel_no);
									Element mac_bnk=doc.createElement("MAC_BNK");
									sme.appendChild(mac_bnk);
									Element mac_bnk_status=doc.createElement("STATUS");
									mac_bnk_status.appendChild(doc.createTextNode(HunterData.get(19).toString()));
									mac_bnk.appendChild(mac_bnk_status);
									Element mac_bnk_bnk_nm=doc.createElement("BNK_NM");
									mac_bnk_bnk_nm.appendChild(doc.createTextNode(HunterData.get(20).toString()));
									mac_bnk.appendChild(mac_bnk_bnk_nm);
									Element mac_bnk_acc_no=doc.createElement("ACC_NO");
									mac_bnk_acc_no.appendChild(doc.createTextNode(HunterData.get(21).toString()));
									mac_bnk.appendChild(mac_bnk_acc_no);
									Element mac_ema=doc.createElement("MAC_EMA");
									sme.appendChild(mac_ema);
									Element ma_bt_status=doc.createElement("STATUS");
									ma_bt_status.appendChild(doc.createTextNode(HunterData.get(22).toString()));
									mac_ema.appendChild(ma_bt_status);
									Element ma_bt_tel_no=doc.createElement("EMA_ADD");
									ma_bt_tel_no.appendChild(doc.createTextNode(HunterData.get(23).toString()));
									mac_ema.appendChild(ma_bt_tel_no);

								}

								//FOR 2ND Corporate
								else if(j==1){								
									if(custId.equals("X")){}
									else{
										custFlag=3;
										HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

										Element sme=doc.createElement("SME");
										submission.appendChild(sme);		
										Element status=doc.createElement("STATUS");
										status.appendChild(doc.createTextNode(HunterData.get(0).toString()));
										sme.appendChild(status);
										Element org_name=doc.createElement("ORG_NME");
										org_name.appendChild(doc.createTextNode(HunterData.get(1).toString()));
										sme.appendChild(org_name);
										Element tan_no=doc.createElement("TAN_NO");
										tan_no.appendChild(doc.createTextNode(HunterData.get(2).toString()));
										sme.appendChild(tan_no);
										Element mac_add=doc.createElement("MAC_ADD");
										sme.appendChild(mac_add);
										Element mac_add_status=doc.createElement("STATUS");
										mac_add_status.appendChild(doc.createTextNode(HunterData.get(3).toString()));
										mac_add.appendChild(mac_add_status);
										Element mac_add_add=doc.createElement("ADD");
										mac_add_add.appendChild(doc.createTextNode(HunterData.get(4).toString()));
										mac_add.appendChild(mac_add_add);
										Element ma_add_city=doc.createElement("CTY");
										ma_add_city.appendChild(doc.createTextNode(HunterData.get(5).toString()));
										mac_add.appendChild(ma_add_city);
										Element ma_add_ste=doc.createElement("STE");
										ma_add_ste.appendChild(doc.createTextNode(HunterData.get(6).toString()));
										mac_add.appendChild(ma_add_ste);
										Element mac_add_ctry=doc.createElement("CTRY");
										mac_add_ctry.appendChild(doc.createTextNode(HunterData.get(7).toString()));
										mac_add.appendChild(mac_add_ctry);
										Element mac_add_pin=doc.createElement("PIN");
										mac_add_pin.appendChild(doc.createTextNode(HunterData.get(8).toString()));
										mac_add.appendChild(mac_add_pin);
										Element mac_tel=doc.createElement("MAC_TEL");
										sme.appendChild(mac_tel);
										Element mac_tel_status=doc.createElement("STATUS");
										mac_tel_status.appendChild(doc.createTextNode(HunterData.get(9).toString()));
										mac_tel.appendChild(mac_tel_status);
										Element mac_tel_tel_no=doc.createElement("TEL_NO");
										mac_tel_tel_no.appendChild(doc.createTextNode(HunterData.get(10).toString()));
										mac_tel.appendChild(mac_tel_tel_no);
										Element mac_bnk=doc.createElement("MAC_BNK");
										sme.appendChild(mac_bnk);
										Element mac_bnk_status=doc.createElement("STATUS");
										mac_bnk_status.appendChild(doc.createTextNode(HunterData.get(11).toString()));
										mac_bnk.appendChild(mac_bnk_status);
										Element mac_bnk_bnk_nm=doc.createElement("BNK_NM");
										mac_bnk_bnk_nm.appendChild(doc.createTextNode(HunterData.get(12).toString()));
										mac_bnk.appendChild(mac_bnk_bnk_nm);
										Element mac_bnk_acc_no=doc.createElement("ACC_NO");
										mac_bnk_acc_no.appendChild(doc.createTextNode(HunterData.get(13).toString()));
										mac_bnk.appendChild(mac_bnk_acc_no);
										Element mac_ema=doc.createElement("MAC_EMA");
										sme.appendChild(mac_ema);
										Element ma_bt_status=doc.createElement("STATUS");
										ma_bt_status.appendChild(doc.createTextNode(HunterData.get(14).toString()));
										mac_ema.appendChild(ma_bt_status);
										Element ma_bt_tel_no=doc.createElement("EMA_ADD");
										ma_bt_tel_no.appendChild(doc.createTextNode(HunterData.get(15).toString()));
										mac_ema.appendChild(ma_bt_tel_no);
									}
								}

								//For Individual main Applicant
								else if(j==2){
									if(custId.equals("X")){}
									else{
										custFlag=4;
										HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

										Element mp=doc.createElement("MP");
										submission.appendChild(mp);	
										Element mp_status=doc.createElement("STATUS");
										mp_status.appendChild(doc.createTextNode(HunterData.get(0).toString()));
										mp.appendChild(mp_status);
										Element mp_pan=doc.createElement("PAN");
										mp_pan.appendChild(doc.createTextNode(HunterData.get(1).toString()));
										mp.appendChild(mp_pan);
										Element mp_fst_nme=doc.createElement("FST_NME");
										mp_fst_nme.appendChild(doc.createTextNode(HunterData.get(2).toString()));
										mp.appendChild(mp_fst_nme);
										Element mp_mid_nme=doc.createElement("MID_NME");
										mp_mid_nme.appendChild(doc.createTextNode(HunterData.get(3).toString()));
										mp.appendChild(mp_mid_nme);
										Element mp_lst_nme=doc.createElement("LST_NME");
										mp_lst_nme.appendChild(doc.createTextNode(HunterData.get(4).toString()));
										mp.appendChild(mp_lst_nme);
										Element mp_dob=doc.createElement("DOB");
										mp_dob.appendChild(doc.createTextNode(HunterData.get(5).toString()));
										mp.appendChild(mp_dob);
										Element mp_ca=doc.createElement("MP_CA");
										mp.appendChild(mp_ca);
										Element mp_ca_status=doc.createElement("STATUS");
										mp_ca_status.appendChild(doc.createTextNode(HunterData.get(6).toString()));
										mp_ca.appendChild(mp_ca_status);
										Element mp_ca_add=doc.createElement("ADD");
										mp_ca_add.appendChild(doc.createTextNode(HunterData.get(7).toString()));
										mp_ca.appendChild(mp_ca_add);
										Element mp_ca_city=doc.createElement("CTY");
										mp_ca_city.appendChild(doc.createTextNode(HunterData.get(8).toString()));
										mp_ca.appendChild(mp_ca_city);
										Element mp_ca_ste=doc.createElement("STE");
										mp_ca_ste.appendChild(doc.createTextNode(HunterData.get(9).toString()));
										mp_ca.appendChild(mp_ca_ste);
										Element mp_ca_ctry=doc.createElement("CTRY");
										mp_ca_ctry.appendChild(doc.createTextNode(HunterData.get(10).toString()));
										mp_ca.appendChild(mp_ca_ctry);
										Element mp_ca_pin=doc.createElement("PIN");
										mp_ca_pin.appendChild(doc.createTextNode(HunterData.get(11).toString()));
										mp_ca.appendChild(mp_ca_pin);
										Element mp_ht=doc.createElement("MP_HT");
										mp.appendChild(mp_ht);
										Element mp_ht_status=doc.createElement("STATUS");
										mp_ht_status.appendChild(doc.createTextNode(HunterData.get(12).toString()));
										mp_ht.appendChild(mp_ht_status);
										Element mp_ht_tel_no=doc.createElement("TEL_NO");
										mp_ht_tel_no.appendChild(doc.createTextNode(HunterData.get(13).toString()));
										mp_ht.appendChild(mp_ht_tel_no);
										Element mp_mt=doc.createElement("MP_MT");
										mp.appendChild(mp_mt);
										Element mp_mt_status=doc.createElement("STATUS");
										mp_mt_status.appendChild(doc.createTextNode(HunterData.get(14).toString()));
										mp_mt.appendChild(mp_mt_status);
										Element mp_mt_tel_no=doc.createElement("TEL_NO");
										mp_mt_tel_no.appendChild(doc.createTextNode(HunterData.get(15).toString()));
										mp_mt.appendChild(mp_mt_tel_no);
										Element mp_ema=doc.createElement("MP_EMA");
										mp.appendChild(mp_mt);
										Element mp_ema_status=doc.createElement("STATUS");
										mp_ema_status.appendChild(doc.createTextNode(HunterData.get(16).toString()));
										mp_ema.appendChild(mp_ema_status);
										Element mp_ema_ema_add=doc.createElement("EMA_ADD");
										mp_ema_ema_add.appendChild(doc.createTextNode(HunterData.get(17).toString()));
										mp_ema.appendChild(mp_ema_ema_add);
										Element mp_id=doc.createElement("MP_ID");
										mp.appendChild(mp_id);
										Element mp_id_status=doc.createElement("STATUS");
										mp_id_status.appendChild(doc.createTextNode(HunterData.get(18).toString()));
										mp_id.appendChild(mp_id_status);
										Element mp_id_doc_typ=doc.createElement("DOC_TYP");
										mp_id_doc_typ.appendChild(doc.createTextNode(HunterData.get(19).toString()));
										mp_id.appendChild(mp_id_doc_typ);
										Element mp_id_doc_no=doc.createElement("DOC_NO");
										mp_id_doc_no.appendChild(doc.createTextNode(HunterData.get(20).toString()));
										mp_id.appendChild(mp_id_doc_no);
									}
								}

								//for individual coapplicants
								else{
									custFlag=5;
									HunterData=HunterObj.fetchCorpHunterData(dealId,custId,custFlag,userId);

									Element cp=doc.createElement("CP");
									submission.appendChild(cp);	
									Element cp_status=doc.createElement("STATUS");
									cp_status.appendChild(doc.createTextNode(HunterData.get(0).toString()));
									cp.appendChild(cp_status);
									Element cp_pan=doc.createElement("PAN");
									cp_pan.appendChild(doc.createTextNode(HunterData.get(1).toString()));
									cp.appendChild(cp_pan);
									Element cp_fst_nme=doc.createElement("FST_NME");
									cp_fst_nme.appendChild(doc.createTextNode(HunterData.get(2).toString()));
									cp.appendChild(cp_fst_nme);
									Element cp_mid_nme=doc.createElement("MID_NME");
									cp_mid_nme.appendChild(doc.createTextNode(HunterData.get(3).toString()));
									cp.appendChild(cp_mid_nme);
									Element cp_lst_nme=doc.createElement("LST_NME");
									cp_lst_nme.appendChild(doc.createTextNode(HunterData.get(4).toString()));
									cp.appendChild(cp_lst_nme);
									Element cp_dob=doc.createElement("DOB");
									cp_dob.appendChild(doc.createTextNode(HunterData.get(5).toString()));
									cp.appendChild(cp_dob);
									Element cp_ca=doc.createElement("CP_CA");
									cp.appendChild(cp_ca);
									
									Element cp_ca_status=doc.createElement("STATUS");
									cp_ca_status.appendChild(doc.createTextNode(HunterData.get(6).toString()));
									cp_ca.appendChild(cp_ca_status);
									Element cp_ca_add=doc.createElement("ADD");
									cp_ca_add.appendChild(doc.createTextNode(HunterData.get(7).toString()));
									cp_ca.appendChild(cp_ca_add);
									Element cp_ca_city=doc.createElement("CTY");
									cp_ca_city.appendChild(doc.createTextNode(HunterData.get(8).toString()));
									cp_ca.appendChild(cp_ca_city);
									Element cp_ca_ste=doc.createElement("STE");
									cp_ca_ste.appendChild(doc.createTextNode(HunterData.get(9).toString()));
									cp_ca.appendChild(cp_ca_ste);
									Element cp_ca_ctry=doc.createElement("CTRY");
									cp_ca_ctry.appendChild(doc.createTextNode(HunterData.get(10).toString()));
									cp_ca.appendChild(cp_ca_ctry);
									Element cp_ca_pin=doc.createElement("PIN");
									cp_ca_pin.appendChild(doc.createTextNode(HunterData.get(11).toString()));
									cp_ca.appendChild(cp_ca_pin);
									Element cp_id=doc.createElement("CP_ID");
									cp.appendChild(cp_id);
									Element cp_id_status=doc.createElement("STATUS");
									cp_id_status.appendChild(doc.createTextNode(HunterData.get(12).toString()));
									cp_id.appendChild(cp_id_status);
									Element cp_id_doc_typ=doc.createElement("DOC_TYP");
									cp_id_doc_typ.appendChild(doc.createTextNode(HunterData.get(13).toString()));
									cp_id.appendChild(cp_id_doc_typ);
									Element cp_id_doc_no=doc.createElement("DOC_NO");
									cp_id_doc_no.appendChild(doc.createTextNode(HunterData.get(14).toString()));
									cp_id.appendChild(cp_id_doc_no);
								}
							}
						}
						TransformerFactory transFact=TransformerFactory.newInstance();
						Transformer tf=transFact.newTransformer();
						DOMSource src=new DOMSource(doc);
						StreamResult result=new StreamResult(response.getOutputStream());
						tf.transform(src, result);
					}
					catch(ParserConfigurationException pce){}
					catch(TransformerException te){}
				}
				out.flush();
			}catch (Exception e) {
				log.error(e);
			}
			//XML work end
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Took "+(endTime - startTime) + " ms");
		return null;
	}

}
