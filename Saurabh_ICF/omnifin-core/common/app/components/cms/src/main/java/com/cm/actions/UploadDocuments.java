package com.cm.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;
import com.cm.vo.PresentationProcessVO;
import com.cm.vo.uploadVO;

public class UploadDocuments {
	private static final Logger logger = Logger.getLogger(UploadDocuments.class
			.getName());
	static ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.utill");
	static String dateFormatWithTime = resource
			.getString("lbl.dateWithTimeInDao");
	static String dateFormat = resource.getString("lbl.dateInDao");
	static String dbType = resource.getString("lbl.dbType");
	static String dbo = resource.getString("lbl.dbPrefix");

	static int count = 0;

	public static boolean docUpload(HttpServletRequest request, FormFile myFile) {
		boolean status = false;
		String fileName = null;
		String filePath = null;
		String message = null;
		File directory = null;
		File fileToCreate = null;
		FileOutputStream fileOutStream = null;
		StringBuilder query = new StringBuilder();
		String rpt = null;
		try {
			logger.info("docUpload().. ");
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'");
			rpt = ConnectionUploadDAO.singleReturn(query.toString());
			directory = new File(rpt + "/");

			fileName = myFile.getFileName();
			filePath = directory.getAbsolutePath();
			if (!CommonFunction.checkNull(fileName).equals("")) {
				// logger.info("Server path: filePath:==>>" +filePath);
				fileToCreate = new File(filePath, fileName);
				int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
				if (fileSize < 1048576) {
					fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
					message = "O";
					status = true;
				} else {
					message = "E";
					status = false;
				}
			} else {
				message = "S";
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("fileName", fileName);
			request.setAttribute("filePath", filePath);
			request.setAttribute("message", message);

			query = null;
			directory = null;
			fileToCreate = null;
			fileOutStream = null;
			myFile = null;
			rpt = null;
		}
		return status;
	}

	public static int countLine(String fileName) {
		logger.info("In countLine() method ");
		int rowTotalNum = 0;
		StringBuilder query = new StringBuilder();
		String rpt = null;
		File directory = null;
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'");
			rpt = ConnectionUploadDAO.singleReturn(query.toString());
			directory = new File(rpt + "/" + fileName);
			// logger.info("File Name......... ==>>"+directory);
			if (directory.exists()) {
				if (directory.length() > 0) {
					workbook = Workbook.getWorkbook(directory);
					sheet = workbook.getSheet(0);
					rowTotalNum = sheet.getRows();
					// logger.info("Total Lines............. vinod==>>"+rowTotalNum);
				} else {
					rowTotalNum = -1;
				}
			} else {
				rowTotalNum = -2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			rpt = null;
			directory = null;
			workbook = null;
			sheet = null;
			fileName = null;
		}
		return rowTotalNum;
	}

	public static boolean csvReadReceipt(HttpServletRequest request,
			HttpServletResponse response, String strFileName) throws Exception {
		logger.info("In csvReadReceipt method ");
		CallableStatement cst = null;
		HttpSession session = null;
		String makerID = null;
		ArrayList alFinalQry = new ArrayList();
		ArrayList alDeleteQuery = new ArrayList(1);
		int rowTotalNum = 0;
		int row = 0;
		int col = 0;
		String[][] rowVal = new String[1000][30];
		String line = null;
		String msg = null;
		String errorMsgHeader = null;
		String excep = null;
		boolean singleObject;
		File objFile1 = null;
		uploadVO vo = new uploadVO();
		BufferedReader bufRdr;
		boolean status = false;
		int intRem = 0;
		String userId = null;
		int companyId = 0;
		String bDate = null;
		Connection utilConn = ConnectionUploadDAO.getConnection();
		String s1 = null;
		String s2 = null;
		try {

			PrepStmtObject insertPrepStmtObject = null;
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");

			if (userobj != null) {
				userId = userobj.getUserId();
				companyId = userobj.getCompanyId();
				bDate = userobj.getBusinessdate();
			}

			makerID = CommonFunction.checkNull(userId);
			vo.setMakerId("" + userId);
			vo.setMakerDate(bDate);
			// logger.info("File Name in Process.."+strFileName);
			if (!strFileName.equals("")) {
				// logger.info(" strFileName is ==>>"+strFileName);
				String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
				String strPath = ConnectionUploadDAO.singleReturn(query);
				strPath = strPath + "/" + strFileName;
				objFile1 = new File(strPath);
				// logger.info(" strFile path... ==>>"+objFile1);
				// logger.info(" strFileName < 60 ==>> "+strFileName!=null?strFileName:strFileName.length()+"("+strFileName.length()+")");
				try {
					// Counting line......
					RandomAccessFile randFile = new RandomAccessFile(strPath,
							"r");
					long lastRec = randFile.length();
					randFile.close();
					FileReader fileRead = new FileReader(strPath);
					LineNumberReader lineRead = new LineNumberReader(fileRead);
					lineRead.skip(lastRec);
					rowTotalNum = lineRead.getLineNumber();
					fileRead.close();
					lineRead.close();
					// logger.info("Total Lines ==>>"+rowTotalNum);
					if (rowTotalNum - 1 > 1000) {
						request.setAttribute("maxCount", rowTotalNum - 1);
						// logger.info("Row can not be more than 1000");
						msg = "RE";
					}

					// Deleting data..start....
					// String strDeleteQuery =
					// "DELETE FROM cr_instrument_dtl_temp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
					// alDeleteQuery.add(strDeleteQuery);
					// boolean
					// status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
					// logger.info(" Deleting query is..... ==>>"+status1);
					// if(status1)
					// {
					// logger.info("Row is deleted....");
					// }
					// else
					// {
					// logger.info("Row is not deleted......");
					// }
					// Deleting data..end....
					// reading value...
					InputStreamReader is = new InputStreamReader(
							new FileInputStream(objFile1));
					bufRdr = new BufferedReader(is);
					line = bufRdr.readLine();
					// logger.info("data is...."+line);
					// spiliting value...

					String token = null;
					String previousTokenVal = null;

					do {
						previousTokenVal = "|";
						StringTokenizer st = new StringTokenizer(line, "|",
								true);
						while (st.hasMoreTokens()) {
							token = st.nextToken();
							if (token.equals(previousTokenVal)) {
								rowVal[row][col] = "";
							} else {
								if (token.equals("|")) {
									previousTokenVal = token;
									continue;
								} else {
									rowVal[row][col] = token;
								}
							}
							col++;
							previousTokenVal = token;
						}
						col = 0;
						intRem++;
						row++;
					} while ((line = bufRdr.readLine()) != null
							&& row <= rowTotalNum); // INSTRUMENT_TYPE|INSTRUMENT_NO|INSTRUMENT_DATE|INSTRUMENT_AMOUNT|BANK_ID|BRANCH_ID|BANK_ACCOUNT|STATUS|REASON_ID
					bufRdr.close();

				} catch (IOException e) {
					logger.error("In Exception Total Lines ==>>"
							+ e.getMessage());
				}

				// insert start.............
				// logger.info("Before IF------");

				if (!"LOAN_NO".equalsIgnoreCase(rowVal[0][0])
						|| !"RECEIPT_MODE".equalsIgnoreCase(rowVal[0][1])
						|| !"INSTRUMENT_NO".equalsIgnoreCase(rowVal[0][2])
						|| !"BANK_ID".equalsIgnoreCase(rowVal[0][3])
						|| !"BRANCH_ID".equalsIgnoreCase(rowVal[0][4])
						|| !"BANK_ACCOUNT".equalsIgnoreCase(rowVal[0][5])
						|| !"RECEIPT_DATE".equalsIgnoreCase(rowVal[0][6])
						|| !"INSTRUMENT_DATE".equalsIgnoreCase(rowVal[0][7])
						|| !"RECEIPT_AMOUNT".equalsIgnoreCase(rowVal[0][8])
						|| !"TDS_AMOUNT".equalsIgnoreCase(rowVal[0][9])
						|| !"RECEIPT_NO".equalsIgnoreCase(rowVal[0][10])
						|| !"MAKER_REMARKS".equalsIgnoreCase(rowVal[0][11])) {
					// logger.info("IN IF---->>>>>>");
					errorMsgHeader = "File format is not correct. ";
					excep = "File format is not correct. ";
					throw new Exception(excep);
				}
				try {
					String strCountDeleteQuery = ConnectionUploadDAO
							.singleReturn("SELECT COUNT(1) FROM cr_instrument_dtl_temp WHERE MAKER_ID = '"
									+ vo.getMakerId() + "' ");
					if (!"0".equalsIgnoreCase(strCountDeleteQuery)) {

						String strDeleteQuery = "DELETE FROM cr_instrument_dtl_temp WHERE MAKER_ID = '"
								+ vo.getMakerId() + "' ";
						alDeleteQuery.add(strDeleteQuery);
						boolean status1 = ConnectionUploadDAO
								.sqlInsUpdDelete(alDeleteQuery);
						strDeleteQuery = null;
						logger.info(" Deleting query is..... ==>>" + status1);
						// if(status1)
						// {
						// logger.info("Row is deleted....");
						// }
						// else
						// {
						// logger.info("Row is not deleted......");
						// }
					}
					for (int i = 1; i <= row - 1; i++) {

						StringBuffer bufInsSql = new StringBuffer();

						bufInsSql
								.append("insert into cr_instrument_dtl_temp(LOAN_NO,RECEIPT_MODE,INSTRUMENT_NO,BANK_ID,BRANCH_ID,BANK_ACCOUNT,RECEIPT_DATE,INSTRUMENT_DATE,RECEIPT_AMOUNT,TDS_AMOUNT,MAKER_REMARKS,RECEIPT_NO,MAKER_ID,SEQUENCE_NO)");
						bufInsSql.append(" values ( ");

						bufInsSql.append(" ?,");// LOAN_NO
						bufInsSql.append(" ?,");// RECEIPT_MODE
						bufInsSql.append(" ?,");// INSTRUMENT_NO
						bufInsSql.append(" ?,"); // BANK_ID
						bufInsSql.append(" ?,"); // BRANCH_ID
						bufInsSql.append(" ?,"); // BANK_ACCOUNT

						bufInsSql.append(" ?,");// RECEIPT_DATE

						bufInsSql.append(" ?,"); // INSTRUMENT_DATE
						bufInsSql.append(" ?,");// RECEIPT_AMOUNT

						bufInsSql.append(" ?,"); // TDS_AMOUNT
						bufInsSql.append(" ?,"); // MAKER_REMARKS
						bufInsSql.append(" ?,"); // RECIPT_NO
						bufInsSql.append(" ?,"); // maker id
						bufInsSql.append(" ?,"); // CHARGE_CODE_ID
						bufInsSql.append(" ? "); // SEQUENCE_NO
						bufInsSql.append(" )");

						// logger.info("IN IF---try...checking...-row and col.>>>>>>"+row+".."+col);
						// INSTRUMENT_TYPE|INSTRUMENT_NO|INSTRUMENT_DATE|INSTRUMENT_AMOUNT|BANK_ID|BRANCH_ID|BANK_ACCOUNT|STATUS|REASON_ID

						// String LOAN_NO=(String)rowVal[i][0];

						// String TXNIDmain =
						// "select loan_id from cr_loan_dtl where loan_no = '"+LOAN_NO+"'";
						// String TXNID =
						// ConnectionUploadDAO.singleReturn(TXNIDmain);//--------------

						// if(TXNID.equalsIgnoreCase("")){
						//
						// //validation for loan no exist
						// }

						// String BPTYPEmain =
						// "select BP_TYPE from business_partner_view where loan_id ='"+TXNID+"'";
						// String BPTYPE =
						// ConnectionUploadDAO.singleReturn(BPTYPEmain);//--------------

						// String BPIDmain =
						// "select BP_ID from business_partner_view where loan_id ='"+TXNID+"'";
						// String BPID =
						// ConnectionUploadDAO.singleReturn(BPIDmain);//--------------
						// logger.info("BPTYPE:  "+BPTYPE+" BPID: "+BPID);

						// String
						// RECEIPT_MODE=CommonFunction.checkNull(rowVal[i][1]);//----------------
						// String INSTRUMENT_TYPE = "R";//------------------

						// String PDC_FLAG = "P";//-----------
						// String
						// INSTRUMENT_NO=(String)rowVal[i][2];//-----------

						// String BANK_ID=(String)rowVal[i][3];//------------
						// String
						// BRANCH_ID=(String)rowVal[i][4];//---------------

						// String ISSUING_MICR_CODEmain =
						// "select BRANCH_MICR_CODE from COM_BANKBRANCH_M where rec_status='A' and bank_id='"+ISSUEING_BANK_ID+"' and bank_branch_id ='"+ISSUEING_BRANCH_ID+"'";
						// String ISSUING_MICR_CODE =
						// ConnectionUploadDAO.singleReturn(ISSUING_MICR_CODEmain);//--------------

						// String ISSUING_IFSC_CODEmain =
						// "select BRANCH_IFCS_CODE from COM_BANKBRANCH_M where rec_status='A' and bank_id='"+ISSUEING_BANK_ID+"' and bank_branch_id ='"+ISSUEING_BRANCH_ID+"'";
						// String ISSUING_IFSC_CODE =
						// ConnectionUploadDAO.singleReturn(ISSUING_IFSC_CODEmain);//--------------

						// String BANK_ACCOUNT=(String)rowVal[i][5];//-------

						// String RECEIVED_DATE=(String)rowVal[i][6];//-------

						// String INSTRUMENT_DATE=(String)rowVal[i][7];//-------

						// String
						// RECEIPT_AMOUNT=(String)rowVal[i][8];//-----------
						// String TDS_AMOUNT=(String)rowVal[i][9];//----------

						// String RECIPT_NO=(String)rowVal[i][10];//-----------
						// String
						// MAKER_REMARKS=(String)rowVal[i][11];//----------

						// String makerid=vo.getMakerId();

						Integer seq = new Integer(i);

						insertPrepStmtObject = new PrepStmtObject();

						if (CommonFunction.checkNull(rowVal[i][0])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][0]);

						if (CommonFunction.checkNull(rowVal[i][1])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][1]);

						if (CommonFunction.checkNull(rowVal[i][2])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][2]);

						if (CommonFunction.checkNull(rowVal[i][3])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][3]);

						if (CommonFunction.checkNull(rowVal[i][4])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][4]);

						if (CommonFunction.checkNull(rowVal[i][5])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][5]);

						if (CommonFunction.checkNull(rowVal[i][6])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][6]);

						if (CommonFunction.checkNull(rowVal[i][7])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][7]);

						if (CommonFunction.checkNull(rowVal[i][8])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][8]);

						if (CommonFunction.checkNull(rowVal[i][9])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][9]);

						if (CommonFunction.checkNull(rowVal[i][12])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][12]);

						if (CommonFunction.checkNull(rowVal[i][10])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][10]);

						if (CommonFunction.checkNull(vo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());

						if (CommonFunction.checkNull(rowVal[i][11])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][11]);

						if (CommonFunction.checkNull(seq).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString("" + seq);

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN csv() insert query1 ### "
								+ insertPrepStmtObject.printQuery());
						alFinalQry.add(insertPrepStmtObject);
						insertPrepStmtObject = null;
						bufInsSql = null;
						seq = null;
					} /* End For Loop */

					// if(alFinalQry.size()>0)
					// {
					status = ConnectionUploadDAO
							.sqlInsUpdDeletePrepStmt(alFinalQry);

					// }
					logger.info(" ****data insert ........==>> " + status);

					// insert end.............

					logger.info(" Before Procedure Call (Receipt_Bulk_Upload_Instrument)");
					cst = utilConn
							.prepareCall("call Receipt_Bulk_Upload_Instrument(?,"
									+ new UploadDocuments().dbo
									+ "str_to_date(?,'"
									+ new UploadDocuments().dateFormatWithTime
									+ "'),?,?,?,?,?,?,?)");
					// Calendar ca=Calendar.getInstance();
					// SimpleDateFormat fmt= new
					// SimpleDateFormat("yyyy-MM-dd ");
					// String ct=fmt.format(ca.getTime());
					// logger.info("Current time...is........."+ ct);
					cst.setString(1, "" + companyId);

					cst.setString(2, vo.getMakerDate());
					// logger.info("file name........."+ strFileName);
					cst.setString(3, strFileName);
					// logger.info("Maker id...is........."+ vo.getMakerId());
					cst.setString(4, new UploadDocuments().dateFormat);
					cst.setString(5, vo.getMakerId());
					cst.registerOutParameter(6, Types.CHAR);
					cst.registerOutParameter(7, Types.CHAR);
					cst.registerOutParameter(8, Types.CHAR);
					cst.registerOutParameter(9, Types.CHAR);
					int st = cst.executeUpdate();
					s1 = cst.getString(8);
					s2 = cst.getString(9);
					logger.info("Bulk_Upload_Instrument_Dtl OUT 1: " + s1);
					logger.info("Bulk_Upload_Instrument_Dtl OUT 2: " + s2);
					// int st=cst.executeUpdate();
					if ("S".equals(s1)) {
						status = true;
						for (int i = 1; i <= row - 1; i++) {
							// al=new ArrayList();
							// logger.info("IN csv() Loan No ### "+
							// rowVal[i][0]);
							String checkErrorQ = "select UPLOADED_FLAG from cr_instrument_dtl_temp where LOAN_NO='"
									+ rowVal[i][0] + "'";
							// al.add(checkErrorQ);
							String checkErrorStatus = ConnectionUploadDAO
									.singleReturn(checkErrorQ);
							checkErrorQ = null;
							// logger.info("checkErrorStatus: "+checkErrorStatus);
							if ("N".equalsIgnoreCase(checkErrorStatus)) {
								status = false;
								break;
							}
							checkErrorStatus = null;
						}
					} else {
						status = false;
					}

					logger.info(" After Procedure Call " + st);

				} /* End Inner Try */
				catch (IOException ex) {
					logger.error("IOException In Uploading File ==>>"
							+ ex.getMessage());
				}
			} else {
				msg = "NF";
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// objFile1.delete();
			ConnectionUploadDAO.closeConnection(utilConn, cst);
			alFinalQry.clear();
			alFinalQry = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			rowVal = null;
			line = null;
			userId = null;
			makerID = null;
			strFileName = null;
			request.setAttribute("msg", msg);
			s1 = null;
			s2 = null;
		}
		return status;
	}

	public static boolean csvRead(HttpServletRequest request,
			HttpServletResponse response, String strFileName) throws Exception {

		logger.info("In csvRead method ");
		CallableStatement cst = null;
		HttpSession session = null;
		String makerID = null;
		ArrayList alFinalQry = new ArrayList();
		ArrayList alDeleteQuery = new ArrayList(1);
		int rowTotalNum = 0;
		int row = 0;
		int col = 0;
		String[][] rowVal = new String[1000][30];
		String line = null;
		String msg = null;
		String errorMsgHeader = null;
		String excep = null;
		boolean singleObject = false;
		File objFile1 = null;
		uploadVO vo = new uploadVO();
		BufferedReader bufRdr;
		boolean status = false;
		int intRem = 0;
		int companyId = 0;
		String userId = null;
		String branchId = null;
		String bDate = null;
		String strPath = null;
		String s1 = null;
		String s2 = null;

		Connection utilConn = ConnectionUploadDAO.getConnection();
		try {

			PrepStmtObject insertPrepStmtObject = null;
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");

			if (userobj != null) {
				userId = userobj.getUserId();
				branchId = userobj.getBranchId();
				companyId = userobj.getCompanyId();
				bDate = userobj.getBusinessdate();
			}
			makerID = CommonFunction.checkNull(userId);
			vo.setMakerId("" + userId);
			vo.setMakerDate(bDate);
			// logger.info("File Name in Process.."+strFileName);
			if (!"".equals(strFileName)) {
				// logger.info(" strFileName is ==>>"+strFileName);
				String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
				strPath = ConnectionUploadDAO.singleReturn(query);
				query = null;
				strPath = strPath + "/" + strFileName;
				objFile1 = new File(strPath);
				// logger.info(" strFile path... ==>>"+objFile1);
				// logger.info(" strFileName < 60 ==>> "+strFileName!=null?strFileName:strFileName.length()+"("+strFileName.length()+")");
				try {
					// Counting line......
					RandomAccessFile randFile = new RandomAccessFile(strPath,
							"r");
					long lastRec = randFile.length();
					logger.info("csvRead():lastRec==>>" + lastRec);
					randFile.close();
					FileReader fileRead = new FileReader(strPath);
					LineNumberReader lineRead = new LineNumberReader(fileRead);
					lineRead.skip(lastRec);
					rowTotalNum = lineRead.getLineNumber();
					fileRead.close();
					lineRead.close();
					// logger.info("Total Lines ==>>"+rowTotalNum);
					if (rowTotalNum - 1 > 1000) {
						request.setAttribute("maxCount", rowTotalNum - 1);
						// logger.info("Row can not be more than 1000");
						msg = "RE";
					}

					// Deleting data..start....
					String strCountDeleteQuery = ConnectionUploadDAO
							.singleReturn("SELECT COUNT(1) FROM cheque_status_upload_tmp WHERE MAKER_ID = '"
									+ vo.getMakerId() + "' ");
					if (!"0".equalsIgnoreCase(strCountDeleteQuery)) {
						String strDeleteQuery = "DELETE FROM cheque_status_upload_tmp WHERE MAKER_ID = '"
								+ vo.getMakerId() + "' ";
						alDeleteQuery.add(strDeleteQuery);
						boolean status1 = ConnectionUploadDAO
								.sqlInsUpdDelete(alDeleteQuery);
						// logger.info(" Deleting query is..... ==>>"+status1);
						// if(status1)
						// {
						// logger.info("csvRead(): Row is deleted....");
						// }
						// else
						// {
						// logger.info("csvRead(): Row is not deleted......");
						// }

					}// Deleting data..end....
						// reading value...
					strCountDeleteQuery = null;
					InputStreamReader is = new InputStreamReader(
							new FileInputStream(objFile1));
					bufRdr = new BufferedReader(is);
					line = bufRdr.readLine();
					logger.info("csvRead():data in line:==>>" + line);
					// spiliting value...
					int colltest = 0;
					String token = null;
					String previousTokenVal = null;

					do {
						previousTokenVal = "|";
						StringTokenizer st = new StringTokenizer(line, "|",
								true);
						while (st.hasMoreTokens()) {
							token = st.nextToken();
							if (token.equals(previousTokenVal)) {
								rowVal[row][col] = "";
							} else {
								if (token.equals("|")) {
									previousTokenVal = token;
									continue;
								} else {
									rowVal[row][col] = token;
								}
							}
							col++;
							previousTokenVal = token;
						}
						col = 0;
						intRem++;
						row++;
					} while ((line = bufRdr.readLine()) != null
							&& row <= rowTotalNum); // INSTRUMENT_TYPE|INSTRUMENT_NO|INSTRUMENT_DATE|INSTRUMENT_AMOUNT|BANK_ID|BRANCH_ID|BANK_ACCOUNT|STATUS|REASON_ID
					bufRdr.close();

				} catch (IOException e) {
					logger.error("In Exception Total Lines ==>>"
							+ e.getMessage());
				}

				if (!"LOAN_NO".equalsIgnoreCase(rowVal[0][0])
						|| !"INSTRUMENT_TYPE".equalsIgnoreCase(rowVal[0][1])
						|| !"INSTRUMENT_NO".equalsIgnoreCase(rowVal[0][2])
						|| !"INSTRUMENT_DATE".equalsIgnoreCase(rowVal[0][3])
						|| !"INSTRUMENT_AMOUNT".equalsIgnoreCase(rowVal[0][4])
						|| !"BANK_ID".equalsIgnoreCase(rowVal[0][5])
						|| !"BRANCH_ID".equalsIgnoreCase(rowVal[0][6])
						|| !"BANK_ACCOUNT".equalsIgnoreCase(rowVal[0][7])
						|| !"STATUS".equalsIgnoreCase(rowVal[0][8])
						|| !"REASON_ID".equalsIgnoreCase(rowVal[0][9])) {
					// logger.info("IN IF- it is going to throw exception--->>>>>>");
					errorMsgHeader = "File format is not correct. ";
					excep = "File format is not correct. ";
					throw new Exception(excep);
				}
				try {

					for (int i = 1; i <= row - 1; i++) {

						StringBuffer bufInsSql = new StringBuffer();

						bufInsSql
								.append("insert into cheque_status_upload_tmp(loan_no,INSTRUMENT_TYPE,INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,BANK_ID,BRANCH_ID,BANK_ACCOUNT,sequence_no,REC_STATUS,REASON_ID,maker_id,maker_date)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");// loan_no
						bufInsSql.append(" ?,"); // INSTRUMENT_TYPE
						bufInsSql.append(" ?,"); // INSTRUMENT_NO
						bufInsSql.append(new UploadDocuments().dbo);
						bufInsSql.append("STR_TO_DATE(?,'"
								+ new UploadDocuments().dateFormatWithTime
								+ "'),"); // INSTRUMENT_DATE
						bufInsSql.append(" ?,"); // INSTRUMENT_AMOUNT
						bufInsSql.append(" ?,"); // BANK_ID
						bufInsSql.append(" ?,");// BRANCH_ID
						bufInsSql.append(" ?,"); // BANK_ACCOUNT
						bufInsSql.append(" ?,"); // sequence_no
						bufInsSql.append(" ?,"); // STATUS
						bufInsSql.append(" ?,"); // REASON_ID
						bufInsSql.append(" ?,"); // maker id
						bufInsSql.append(new UploadDocuments().dbo);
						bufInsSql.append("STR_TO_DATE(?,'"
								+ new UploadDocuments().dateFormatWithTime
								+ "') "); // maker_date
						bufInsSql.append(" )");
						// logger.info("IN IF---try...checking...-row and col.>>>>>>"+row+".."+col);
						// INSTRUMENT_TYPE|INSTRUMENT_NO|INSTRUMENT_DATE|INSTRUMENT_AMOUNT|BANK_ID|BRANCH_ID|BANK_ACCOUNT|STATUS|REASON_ID

						// String LOAN_NO=(String)rowVal[i][0];
						// String
						// INSTRUMENT_TYPE=CommonFunction.checkNull(rowVal[i][1]);
						// String INSTRUMENT_NO=(String)rowVal[i][2];
						// String INSTRUMENT_DATE=(String)rowVal[i][3];
						// String INSTRUMENT_AMOUNT=(String)rowVal[i][4];
						// String BANK_ID=(String)rowVal[i][5];
						// String BRANCH_ID=(String)rowVal[i][6];
						// String BANK_ACCOUNT=(String)rowVal[i][7];
						// String STATUS=(String)rowVal[i][8];
						// String REASON_ID=(String)rowVal[i][9];
						// String makerid=vo.getMakerId();
						// String makerdate=vo.getMakerDate();

						Integer seq = new Integer(i);

						insertPrepStmtObject = new PrepStmtObject();

						if (CommonFunction.checkNull(rowVal[i][0])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][0]);

						if (CommonFunction.checkNull(rowVal[i][1])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][1]);

						if (CommonFunction.checkNull(rowVal[i][2])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][2]);

						if (CommonFunction.checkNull(rowVal[i][3])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][3]);

						if (CommonFunction.checkNull(rowVal[i][4])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][4]);

						if (CommonFunction.checkNull(rowVal[i][5])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][5]);

						if (CommonFunction.checkNull(rowVal[i][6])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][6]);

						if (CommonFunction.checkNull(rowVal[i][7])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][7]);

						if (CommonFunction.checkNull(seq).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(seq.toString());

						if (CommonFunction.checkNull(rowVal[i][8])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][8]);

						if (CommonFunction.checkNull(rowVal[i][9])
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(rowVal[i][9]);

						if (CommonFunction.checkNull(vo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());

						if (CommonFunction.checkNull(vo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerDate());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN csv() insert query1 ### "
								+ insertPrepStmtObject.printQuery());
						alFinalQry.add(insertPrepStmtObject);
						bufInsSql = null;
						seq = null;
						insertPrepStmtObject = null;

					} /* End For Loop */

					// if(alFinalQry.size()>0)
					// {
					status = ConnectionUploadDAO
							.sqlInsUpdDeletePrepStmt(alFinalQry);

					// }
					logger.info(" ****data insert ........==>> " + status);

					// insert end.............

					// logger.info(" Before Procedure Call ");
					cst = utilConn
							.prepareCall("call Bulk_Upload_Instrument_Dtl(?,"
									+ new UploadDocuments().dbo
									+ "str_to_date(?,'"
									+ new UploadDocuments().dateFormatWithTime
									+ "'),?,?,?,?)");
					// Calendar ca=Calendar.getInstance();
					// SimpleDateFormat fmt= new
					// SimpleDateFormat("yyyy-MM-dd ");
					// String ct=fmt.format(ca.getTime());
					// logger.info("Current time...is........."+ ct);
					cst.setString(1, "" + companyId);
					cst.setString(2, vo.getMakerDate());
					// logger.info("file name........."+ strFileName);
					cst.setString(3, strFileName);
					// logger.info("Maker id...is........."+ vo.getMakerId());
					cst.setString(4, vo.getMakerId());
					cst.registerOutParameter(5, Types.CHAR);
					cst.registerOutParameter(6, Types.CHAR);
					int st = cst.executeUpdate();
					s1 = cst.getString(5);
					s2 = cst.getString(6);
					logger.info("Bulk_Upload_Instrument_Dtl OUT 1: " + s1);
					logger.info("Bulk_Upload_Instrument_Dtl OUT 2: " + s2);
					// int st=cst.executeUpdate();
					// ArrayList al=null;

					if ("S".equals(s1)) {
						status = true;
						for (int i = 1; i <= row - 1; i++) {
							// al=new ArrayList();
							logger.info("IN csv() Loan No ### " + rowVal[i][0]);
							String checkErrorQ = "select UPLOADED_FLAG from cheque_status_upload_tmp where LOAN_NO='"
									+ rowVal[i][0] + "'";
							// al.add(checkErrorQ);
							String checkErrorStatus = ConnectionUploadDAO
									.singleReturn(checkErrorQ);
							logger.info("checkErrorStatus: " + checkErrorStatus);
							if ("N".equalsIgnoreCase(checkErrorStatus)) {
								status = false;
								break;
							}
							checkErrorQ = null;
							checkErrorStatus = null;
						}
					} else {
						status = false;
					}

					logger.info(" After Procedure Call " + st);

				} /* End Inner Try */
				catch (IOException ex) {
					logger.error("IOException In Uploading File ==>>"
							+ ex.getMessage());
				}
			} else {
				msg = "NF";
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// objFile1.delete();
			request.setAttribute("msg", msg);
			alFinalQry.clear();
			alFinalQry = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			makerID = null;
			rowVal = null;
			line = null;
			userId = null;
			branchId = null;
			bDate = null;
			strPath = null;
			strFileName = null;
			objFile1 = null;
			bufRdr = null;
			s1 = null;
			s2 = null;
			vo = null;
			ConnectionUploadDAO.closeConnection(utilConn, cst);

		}
		return status;
	}

	public static String getPropertiesValue(String keyName) {
		String value = null;
		try {
			ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.utill");
			value = resource.getString(keyName);
		} catch (Exception e) {
			logger.error("In..getPropertiesValue UploadDocuments.java file"
					+ e.getMessage());
		}
		return value;
	}

	public static boolean readExcelforBounceUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName) throws Exception {
		logger.info("In readExcelforBounceUpload() method ");
		HttpSession session = null;
		ArrayList alFinalQry = new ArrayList();
		ArrayList alDeleteQuery = new ArrayList(1);
		ArrayList dataList = new ArrayList();
		ArrayList arr = new ArrayList();
		ArrayList subList = new ArrayList();
		int noOfRejectedRecord = 0;
		int noOfUploadedRecord = 0;
		File objFile1 = null;
		uploadVO vo = new uploadVO();
		boolean status = false;
		String msg = null;
		PrepStmtObject insertPrepStmtObject = null;
		StringBuilder query = new StringBuilder();
		StringBuilder strDeleteQuery = new StringBuilder();
		StringBuffer bufInsSql = new StringBuffer();
		String strPath = null;
		ArrayList<Object> in = new ArrayList<Object>();
		ArrayList<Object> out = new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1 = null;
		String s2 = null;
		try {
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			if (userobj != null) {
				vo.setMakerId(userobj.getUserId());
				vo.setMakerDate(userobj.getBusinessdate());
			}
			if (!"".equals(strFileName)) {
				// logger.info(" strFileName is ==>>"+strFileName);
				query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'");
				logger.info("In readExcelforBounceUpload(): query:==>>"
						+ query.toString());
				strPath = ConnectionUploadDAO.singleReturn(query.toString());
				strPath = strPath + "/" + strFileName;
				objFile1 = new File(strPath);
				String strCountDeleteQuery = ConnectionUploadDAO
						.singleReturn("SELECT COUNT(1) FROM cheque_status_upload_tmp WHERE MAKER_ID = '"
								+ vo.getMakerId() + "' ");
				if (!"0".equalsIgnoreCase(strCountDeleteQuery)) {
					strDeleteQuery
							.append("DELETE FROM cheque_status_upload_tmp WHERE MAKER_ID = '"
									+ vo.getMakerId() + "' ");
					logger.info("In readExcelforBounceUpload(): Delete query strDeleteQuery"
							+ strDeleteQuery.toString());
					alDeleteQuery.add(strDeleteQuery);
					boolean status1 = ConnectionUploadDAO
							.sqlInsUpdDelete(alDeleteQuery);
					logger.info(" Deleting query is..... ==>>" + status1);
					// if(status1)
					// {
					// logger.info("Row is deleted....");
					// }
					// else
					// {
					// logger.info("Row is not deleted......");
					// }
				}
				dataList = new ArrayList();
				Workbook workbook = Workbook.getWorkbook(new File(strPath));
				String sheetName[] = workbook.getSheetNames();
				Sheet sheet;
				Cell xlsCell;
				Cell[] cell;
				for (int p = 0; p < sheetName.length; p++) {
					sheet = workbook.getSheet(p);
					for (int i = 0; i < sheet.getRows(); i++) {
						cell = sheet.getRow(0);
						arr = new ArrayList();
						for (int j = 0; j < cell.length; j++) {
							xlsCell = sheet.getCell(j, i);
							arr.add(xlsCell.getContents().toString());
						}
						dataList.add(arr);
					}
				}

				bufInsSql
						.append("insert into cheque_status_upload_tmp(loan_no,INSTRUMENT_TYPE,INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,BANK_ID,BRANCH_ID,BANK_ACCOUNT,sequence_no,REC_STATUS,REASON_ID,maker_id,maker_date,ISSUING_BANK_ID,ISSUING_BRANCH_ID,ISSUING_BANK_ACCOUNT,INSTRUMENT_MODE,VALUE_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");// loan_no
				bufInsSql.append(" ?,"); // INSTRUMENT_TYPE
				bufInsSql.append(" ?,"); // INSTRUMENT_NO
				bufInsSql.append(" ?,"); // INSTRUMENT_DATE
				bufInsSql.append(" ?,"); // INSTRUMENT_AMOUNT
				bufInsSql.append(" ?,"); // BANK_ID
				bufInsSql.append(" ?,");// BRANCH_ID
				bufInsSql.append(" ?,"); // BANK_ACCOUNT
				bufInsSql.append(" ?,"); // sequence_no
				bufInsSql.append(" ?,"); // STATUS
				bufInsSql.append(" ?,"); // REASON_ID
				bufInsSql.append(" ?,"); // maker id
				bufInsSql.append(new UploadDocuments().dbo);
				bufInsSql.append("STR_TO_DATE(?,'"
						+ new UploadDocuments().dateFormatWithTime + "'), "); // maker_date
				bufInsSql.append(" ?,"); // ISSUING BANK
				bufInsSql.append(" ?,"); // ISSUING BRANCH
				bufInsSql.append(" ?,"); // ISSUING BANK ACCOUNT
				bufInsSql.append(" ?,"); // INSTRUMENT_MODE
				bufInsSql.append(" ?"); // VALUE_DATE
				bufInsSql.append(" )");

				for (int i = 1; i < dataList.size(); i++) {
					subList = (ArrayList) dataList.get(i);
					if (subList.size() > 0) {
						// String
						// LOAN_NO=CommonFunction.checkNull(subList.get(0));
						// String
						// INSTRUMENT_TYPE=CommonFunction.checkNull(subList.get(1));
						// String
						// instrumentMode=CommonFunction.checkNull(subList.get(2));
						// String
						// INSTRUMENT_NO=CommonFunction.checkNull(subList.get(3));
						// String
						// INSTRUMENT_DATE=CommonFunction.checkNull(subList.get(4));
						// String
						// INSTRUMENT_AMOUNT=CommonFunction.checkNull(subList.get(5));
						// String
						// BANK_ID=CommonFunction.checkNull(subList.get(6));
						// String
						// BRANCH_ID=CommonFunction.checkNull(subList.get(7));
						// String
						// BANK_ACCOUNT=CommonFunction.checkNull(subList.get(8));
						// String
						// STATUS=CommonFunction.checkNull(subList.get(9));
						// String
						// valueDate=CommonFunction.checkNull(subList.get(10));
						// String
						// issuingBank=CommonFunction.checkNull(subList.get(11));
						// String
						// issuingBranch=CommonFunction.checkNull(subList.get(12));
						// String
						// issuingAccount=CommonFunction.checkNull(subList.get(13));
						// String
						// REASON_ID=CommonFunction.checkNull(subList.get(14));
						// String makerid=vo.getMakerId();
						// String makerdate=vo.getMakerDate();

						// Integer seq=new Integer(i);

						insertPrepStmtObject = new PrepStmtObject();

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(0)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(0)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(1)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(1)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(3)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(3)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(4)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(4)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(5)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(5)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(6)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(6)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(7)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(7)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(8)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(8)));

						if (CommonFunction.checkNull(i).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addInt(i);

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(9)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(9)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(14)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else {
							String reasonIdQuery = "SELECT REASON_ID FROM  com_reason_m WHERE REASON_SHORT_CODE='"
									+ CommonFunction.checkNull(subList.get(14))
									+ "'";
							String reasonId = ConnectionUploadDAO
									.singleReturn(reasonIdQuery);
							insertPrepStmtObject.addString(reasonId); // REASON_ID
						}
						if (CommonFunction.checkNull(vo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());

						if (CommonFunction.checkNull(vo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerDate());
						// SAURABH CHANGES
						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(11)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(11)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(12)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(12)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(13)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(13)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(2)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(2)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(10)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(10)));

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN readExcelforBounceUpload() insert query1 ###:::::::::::: "
								+ insertPrepStmtObject.printQuery());
						alFinalQry.add(insertPrepStmtObject);
						insertPrepStmtObject = null;
					}
				}
				boolean insertStatus = false;
				insertStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alFinalQry);
				// logger.info("status::::::::::::::::::::::::::::::::::::::::::::::::"+status);
				// logger.info(" Before Procedure Call ");

				in.add(1);
				String makerDate = null;
				makerDate = CommonFunction.changeFormat(CommonFunction
						.checkNull(vo.getMakerDate()).trim());
				in.add(makerDate);
				in.add(strFileName);
				in.add(new UploadDocuments().dateFormat);
				in.add(vo.getMakerId());
				out.add(noOfRejectedRecord);
				out.add(noOfUploadedRecord);
				out.add(s1);
				out.add(s2);

				logger.info(" Bulk_Upload_Instrument_Dtl(" + in.toString()
						+ "," + out.toString() + ")");
				outMessages = (ArrayList) ConnectionUploadDAO.callSP(
						"Bulk_Upload_Instrument_Dtl", in, out);

				noOfRejectedRecord = Integer.parseInt((String) outMessages
						.get(0));
				noOfUploadedRecord = Integer.parseInt((String) outMessages
						.get(1));
				s1 = (String) outMessages.get(2);
				s2 = (String) outMessages.get(3);

				// logger.info("Bulk_Upload_Instrument_Dtl OUT s1: "+s1);
				// logger.info("Bulk_Upload_Instrument_Dtl OUT s2: "+s2);
				// logger.info("Bulk_Upload_Instrument_Dtl OUT noOfRejectedRecord: "+noOfRejectedRecord);
				// logger.info("Bulk_Upload_Instrument_Dtl OUT noOfUploadedRecord: "+noOfUploadedRecord);

				if (("".equals(s1) || "S".equals(s1)) && insertStatus) {
					status = true;
					for (int i = 1; i < dataList.size(); i++) {
						subList = (ArrayList) dataList.get(i);
						if (subList.size() > 0) {
							String checkErrorQ = "select UPLOADED_FLAG from cheque_status_upload_tmp where LOAN_NO='"
									+ subList.get(0)
									+ "'  and maker_id='"
									+ vo.getMakerId() + "' ";
							String checkErrorStatus = ConnectionUploadDAO
									.singleReturn(checkErrorQ);
							if (checkErrorStatus.equalsIgnoreCase("N")) {
								status = false;
								break;
							}
							checkErrorQ = null;
							checkErrorStatus = null;
						}
					}
				} else
					status = false;

			} else {
				msg = "NF";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// objFile1.delete();
			request.setAttribute("msg", msg);
			request.setAttribute("noOfRejectedRecord", noOfRejectedRecord);
			request.setAttribute("noOfUploadedRecord", noOfUploadedRecord);
			session = null;
			alFinalQry.clear();
			alFinalQry = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			dataList.clear();
			dataList = null;
			arr.clear();
			arr = null;
			subList.clear();
			subList = null;
			objFile1 = null;
			vo = null;
			insertPrepStmtObject = null;
			query = null;
			strDeleteQuery = null;
			bufInsSql = null;
			strPath = null;
			in.clear();
			in = null;
			out.clear();
			out = null;
			outMessages.clear();
			outMessages = null;
			s1 = null;
			s2 = null;
			strFileName = null;
		}
		return status;
	}

	public static boolean presentationUploadKtr(HttpServletRequest request,
			HttpServletResponse response, String strFileName) {

		logger.info("Inside  presentationUploadKtr() method.. ");

		boolean status = false;
		int companyId = 0;

		// StringBuffer session_id=null;
		StringBuffer dateCheck = null;
		StringBuffer userId = null;
		StringBuffer branchId = null;
		StringBuffer bDate = null;
		StringBuffer makerID = null;
		StringBuffer strDeleteQuery = null;

		HttpSession session = null;
		ArrayList alDeleteQuery = new ArrayList(1);

		// session_id=new StringBuffer(bankstUploadForm.getSessionId());
		try {

			dateCheck = new StringBuffer("");
			userId = new StringBuffer("");
			branchId = new StringBuffer("");
			bDate = new StringBuffer("");
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");

			if (userobj != null) {
				userId = new StringBuffer(userobj.getUserId());
				branchId = new StringBuffer(userobj.getBranchId());
				companyId = userobj.getCompanyId();
				bDate = new StringBuffer(userobj.getBusinessdate());
			}
			makerID = new StringBuffer(CommonFunction.checkNull(userId));
			// bankstUploadForm.setMakerId(""+userId);
			// bankstUploadForm.setMakerDate(bDate.toString());
			if (!strFileName.equals("")) {
				logger.info("In presentationUploadKtr(): strFileName ==>>"
						+ strFileName);

				try {
					String rpt1 = request.getContextPath();
					if (dbType.equalsIgnoreCase("MSSQL")) {
						rpt1 = rpt1 + "/KTR_UPLOAD/MSSQL_KTR";
					} else {
						rpt1 = rpt1 + "/KTR_UPLOAD/MYSQL_KTR";
					}

					strDeleteQuery = new StringBuffer(
							"DELETE FROM PRESENTATION_PROCESS_UPLOAD_TEMP WHERE MAKER_ID = '"
									+ userId + "' ");

					logger.info("In  presentationUploadKtr(): delete query of PRESENTATION_PROCESS_UPLOAD_TEMP:==>>"
							+ strDeleteQuery.toString());
					alDeleteQuery.add(strDeleteQuery.toString());
					boolean status1 = ConnectionUploadDAO
							.sqlInsUpdDelete(alDeleteQuery);

					if (status1) {
						logger.info("In presentationUploadKtr() Row is deleted....");
					} else {
						logger.info("In presentationUploadKtr() Row is not deleted......");
					}

					KettleEnvironment.init(false);
					EnvUtil.environmentInit();
					TransMeta transMeta = new TransMeta(
							rpt1.concat("\\presentationprocess-excel-uploading.ktr"));
					Trans trans = new Trans(transMeta);

					trans.execute(null); // You can pass arguments instead of
											// null.
					trans.waitUntilFinished();

					if (trans.getErrors() > 0) {

						status = false;
						throw new RuntimeException(
								"There were errors during transformation execution."
										+ trans.getErrors());
					} else {
						status = true;
					}

					transMeta.clearCaches();
					transMeta.clear();
					rpt1 = null;

				} catch (Exception e) {
					e.printStackTrace();

				} finally {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			request.setAttribute("dateCheck", dateCheck.toString());
			// session_id.setLength(0);
			dateCheck.setLength(0);
			userId.setLength(0);
			branchId.setLength(0);
			bDate.setLength(0);
			makerID.setLength(0);
			alDeleteQuery.clear();
			// session_id=null;
			userId = null;
			branchId = null;
			bDate = null;
			makerID = null;
			strDeleteQuery = null;
		}

		return status;
	}

	public static String readExcelforPresentationUpload(
			HttpServletRequest request, HttpServletResponse response,
			String strFileName, PresentationProcessVO vo) throws Exception {
		logger.info("In readExcelforPresentationUpload() method ");
		HttpSession session = null;
		ArrayList alDeleteQuery = new ArrayList(1);
		ArrayList dataList = new ArrayList();
		int rowTotalNum = 0;
		int row = 0;
		int col = 0;
		String msg = null;
		boolean singleObject;
		File objFile1 = null;
		int val = 0;
		boolean status = false;
		String result = null;
		StringBuilder strDeleteQuery = new StringBuilder();
		ArrayList<Object> in = new ArrayList<Object>();
		ArrayList<Object> out = new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1 = null;
		String s2 = null;
		String rejRecord = null;
		String upRecord = null;
		String date = null;
		String date1 = null;
		boolean insertStatus = false;

		try {
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			if (userobj != null) {
				vo.setMakerId(userobj.getUserId());
				vo.setMakerDate(userobj.getBusinessdate());
			}

			insertStatus = presentationUploadKtr(request, response, strFileName);

			if (insertStatus) {
				// logger.info(" before call Presentation_Process_Bulk_Upload() procedure ");
				in.add(1);
				date = CommonFunction.changeFormat(CommonFunction.checkNull(
						vo.getMakerDate()).trim());
				if (date != null)
					in.add(date.toString());
				date1 = CommonFunction.changeFormat(CommonFunction.checkNull(
						vo.getPresentationDate()).trim());
				if (date1 != null)
					in.add(date1.toString());
				in.add(vo.getLbxBankID());
				in.add(vo.getLbxBranchID());
				in.add(((CommonFunction.checkNull(vo.getBankAccount().trim()))));
				in.add(((CommonFunction.checkNull(vo.getMakerId().trim()))));
				in.add(val);
				out.add(rejRecord);
				out.add(upRecord);
				out.add(s1);
				out.add(s2);

				logger.info(" Presentation_Process_Bulk_Upload("
						+ in.toString() + "," + out.toString() + ")");
				outMessages = (ArrayList) ConnectionUploadDAO.callSP(
						"Presentation_Process_Bulk_Upload", in, out);

				rejRecord = (String) outMessages.get(0);
				upRecord = (String) outMessages.get(1);
				s1 = (String) outMessages.get(2);
				s2 = (String) outMessages.get(3);

				outMessages.clear();
				outMessages = null;

				// logger.info("s1  : "+s1);
				// logger.info("s2  : "+s2);
				// logger.info("rejRecord    : "+rejRecord);
				// logger.info("upRecord     : "+upRecord);
				if ("S".equals(s1)) {
					result = rejRecord + ":" + upRecord;
					status = true;
				} else if ("E".equals(s1)) {
					result = s2;
				}
				s1 = null;
				s2 = null;
				rejRecord = null;
				upRecord = null;
				// logger.info("result : "+result);
				// logger.info("status : "+status);
			}

			else {
				msg = "NF";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.setAttribute("msg", msg);
			session = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			dataList.clear();
			dataList = null;
			objFile1 = null;
			strDeleteQuery = null;
			in.clear();
			in = null;
			out.clear();
			out = null;
			outMessages.clear();
			outMessages = null;
			s1 = null;
			s2 = null;
			rejRecord = null;
			upRecord = null;
			date = null;
			date1 = null;
			strFileName = null;
			vo = null;
		}
		return result;
	}

	// method added by neeraj
	public static String readExcelforFinancialUpload(
			HttpServletRequest request, HttpServletResponse response,
			String strFileName, String makerName, String makerDate,
			String dealId, String source) throws Exception {
		String result = null;
		boolean status = false;
		logger.info("In readExcelforFinancialUpload() method of UploadDocuments. ");
		File objFile1 = null;
		String query = null;
		ArrayList alDeleteQuery = new ArrayList(1);
		boolean deleteStatus = false;
		PrepStmtObject insertPrepStmtObject = null;
		ArrayList dataList = new ArrayList();
		StringBuffer bufInsSql = null;
		PrepStmtObject stmt = null;
		ArrayList alFinalQry = new ArrayList();
		boolean insertStatus = false;
		int row = 0;
		String wrongcolCount = "Y";
		String maxRowCount = "Y";
		String minRowCount = "Y";
		String wrongData = "Y";
		try {
			if (!"".equals(strFileName)) {
				logger.info("In readExcelforFinancialUpload(): strFileName   :  "
						+ strFileName);
				query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
				String strPath = ConnectionUploadDAO.singleReturn(query);
				strPath = strPath + "/" + strFileName;
				objFile1 = new File(strPath);

				query = null;
				query = "DELETE FROM cr_financial_analysis_upload WHERE MAKER_ID='"
						+ CommonFunction.checkNull(makerName).trim()
						+ "' and DEAL_ID='"
						+ CommonFunction.checkNull(dealId).trim()
						+ "'  AND SOURCE_TYPE='"
						+ CommonFunction.checkNull(source).trim() + "' ";
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject.setSql(query);
				logger.info("In readExcelforFinancialUpload()  Delete Query   :  "
						+ insertPrepStmtObject.printQuery());
				alDeleteQuery.add(insertPrepStmtObject);
				deleteStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alDeleteQuery);
				logger.info("In readExcelforFinancialUpload()  Delete Status  :  "
						+ deleteStatus);
				if (deleteStatus) {
					Workbook workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[] = workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;
					sheet = workbook.getSheet(0);
					ArrayList arr = null;
					row = sheet.getRows();
					if (row > 1001) {
						maxRowCount = "Y";
						minRowCount = "N";
						wrongcolCount = "N";
						wrongData = "N";
					}
					if (row <= 1) {
						maxRowCount = "N";
						minRowCount = "Y";
						wrongcolCount = "N";
						wrongData = "N";
					}
					if (1 < row && row < 1001) {
						maxRowCount = "N";
						minRowCount = "N";
						wrongcolCount = "N";
						wrongData = "N";
					}
					if ("N".equals(maxRowCount) && "N".equals(minRowCount)) {
						wrongcolCount = "N";
						wrongData = "N";
						for (int i = 0; i < row; i++) {
							cell = sheet.getRow(i);
							arr = new ArrayList();
							int col = cell.length;
							if (col != 7) {
								wrongcolCount = "Y";
								break;
							}
							for (int j = 0; j < col; j++) {
								xlsCell = sheet.getCell(j, i);
								String val = xlsCell.getContents().toString();
								if (j > 1 && i != 0) {
									boolean numericChk = false;
									numericChk = isNumeric(val);
									val = null;
									if (!numericChk) {
										wrongData = "Y";
										break;
									}
								}
								arr.add(xlsCell.getContents().toString());
							}
							if (wrongcolCount == "Y" || wrongData == "Y")
								break;
							dataList.add(arr);

						}
					}// end of if(maxRowCount=="N" && minRowCount=="N")
					if (maxRowCount == "Y")
						result = "MXE";
					if (minRowCount == "Y")
						result = "MNE";
					if (wrongData == "Y")
						result = "NUE";
					if (wrongcolCount == "Y")
						result = "CLE";
					if ("N".equalsIgnoreCase(wrongData)
							&& "N".equalsIgnoreCase(wrongcolCount)) {
						for (int i = 1; i < dataList.size(); i++) {
							ArrayList subList = (ArrayList) dataList.get(i);
							// String DEAL_ID=CommonFunction.checkNull(dealId);
							// String
							// SOURCE_TYPE=CommonFunction.checkNull(source);
							// String
							// PARAMETER=CommonFunction.checkNull(subList.get(0));
							// String
							// PARAMETER_DESC=CommonFunction.checkNull(subList.get(1));
							// String
							// YEAR1=CommonFunction.checkNull(subList.get(2));
							// String
							// YEAR2=CommonFunction.checkNull(subList.get(3));
							// String
							// YEAR3=CommonFunction.checkNull(subList.get(4));
							// String
							// YEAR4=CommonFunction.checkNull(subList.get(5));
							// String
							// YEAR5=CommonFunction.checkNull(subList.get(6));
							// String
							// MAKER_ID=CommonFunction.checkNull(makerName);
							// String
							// MAKER_DATE=CommonFunction.checkNull(makerDate);

							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  DEAL_ID      : "+DEAL_ID);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  SOURCE_TYPE  : "+SOURCE_TYPE);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  PARAMETER    : "+PARAMETER);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  YEAR1        : "+YEAR1);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  YEAR2        : "+YEAR2);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  YEAR3        : "+YEAR3);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  YEAR4        : "+YEAR4);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  YEAR5        : "+YEAR5);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  MAKER_ID     : "+MAKER_ID);
							// logger.info("In readExcelforFinancialUpload() row"+i+1+"  MAKER_DATE   : "+MAKER_DATE);

							stmt = new PrepStmtObject();
							bufInsSql = new StringBuffer();
							bufInsSql
									.append("insert into cr_financial_analysis_upload(DEAL_ID,SOURCE_TYPE,PARAMETER,PARAMETER_DESC,YEAR1,YEAR2,YEAR3,YEAR4,YEAR5,MAKER_ID,MAKER_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,");// DEAL_ID
							bufInsSql.append(" ?, "); // SOURCE_TYPE
							bufInsSql.append(" ?,");// PARAMETER
							bufInsSql.append(" ?,");// PARAMETER_DESC
							bufInsSql.append(" ?,");// YEAR1
							bufInsSql.append(" ?,"); // YEAR2
							bufInsSql.append(" ?,"); // YEAR3
							bufInsSql.append(" ?,"); // YEAR4
							bufInsSql.append(" ?,");// YEAR5
							bufInsSql.append(" ?,"); // MAKER_ID
							bufInsSql.append(dbo);
							if (CommonFunction.checkNull(dbo).trim()
									.equalsIgnoreCase(""))
								bufInsSql
										.append(" DATE_ADD(STR_TO_DATE(?, '"
												+ dateFormatWithTime
												+ "'),INTERVAL CURTIME() HOUR_SECOND) )");// MAKER_DATE
							else
								bufInsSql
										.append("STR_TO_DATE(?,'"
												+ dateFormatWithTime
												+ "') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");// MAKER_DATE

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(dealId))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(dealId));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(source))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(source));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(0)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(0)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(1)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(1)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(2)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(2)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(3)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(3)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(4)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(4)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(5)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(5)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(6)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(6)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(makerName))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction
										.checkNull(makerName));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(makerDate))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction
										.checkNull(makerDate));

							stmt.setSql(bufInsSql.toString());
							logger.info("In readExcelforFinancialUpload()  Insert Query :  "
									+ stmt.printQuery());
							alFinalQry.add(stmt);
							bufInsSql = null;
							stmt = null;
							subList = null;
						}// end of for loop
						insertStatus = ConnectionUploadDAO
								.sqlInsUpdDeletePrepStmt(alFinalQry);
						logger.info("In readExcelforFinancialUpload()  Insert Status :  "
								+ insertStatus);
						if (insertStatus) {
							ArrayList<Object> in = new ArrayList<Object>();
							ArrayList<Object> out = new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							in.add(CommonFunction.checkNull(dealId));
							in.add(CommonFunction.checkNull(makerName));
							in.add(CommonFunction.checkNull(source));
							String s1 = null;
							String s2 = null;
							out.add(s1);
							out.add(s2);
							logger.info("financialAnalysisUppload("
									+ in.toString() + "," + out.toString()
									+ ")");
							outMessages = (ArrayList) ConnectionUploadDAO
									.callSP("financialAnalysisUppload", in, out);
							s1 = (String) outMessages.get(0);
							s2 = (String) outMessages.get(1);
							logger.info("In readExcelforFinancialUpload()  OUT s1 : "
									+ s1);
							logger.info("In readExcelforFinancialUpload()  OUT s2 : "
									+ s2);
							if (CommonFunction.checkNull(s1).trim()
									.equalsIgnoreCase("E"))
								result = "PDE" + "$" + s2;
							s1 = null;
							s2 = null;
							outMessages.clear();
							outMessages = null;
							in.clear();
							in = null;
							out.clear();
							out = null;
						}// end of if(insertStatus)
						else
							result = "PRE";
					}// end of if(wrongData=="N" && wrongcolCount=="N")
				}// end of if(deleteStatus)
				else
					result = "PRE";

			}// end of if(!strFileName.equals(""))
			else
				result = "FNE";
		} catch (Exception e) {
			result = "PRE";
			logger.error(e.getMessage());
		} finally {
			objFile1 = null;
			query = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			insertPrepStmtObject = null;
			dataList.clear();
			dataList = null;
			stmt = null;
			alFinalQry.clear();
			alFinalQry = null;
			wrongcolCount = null;
			maxRowCount = null;
			minRowCount = null;
			wrongData = null;
			strFileName = null;
			makerName = null;
			makerDate = null;
			dealId = null;
			source = null;

		}
		logger.error("In readExcelforFinancialUpload()  return Result :  "
				+ result);
		return result;
	}

	// MANISH SPACE STARTS
	public static String readExcelreteReviewUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName, String makerName,
			String makerDate) throws Exception {
		String result = null;
		boolean status = false;
		makerDate = CommonFunction.changeFormat(makerDate);
		logger.info("In readExcelreteReviewUpload() method of UploadDocuments. ");
		File objFile1 = null;
		String query = null;
		ArrayList alDeleteQuery = new ArrayList(1);
		boolean deleteStatus = false;
		PrepStmtObject insertPrepStmtObject = null;
		ArrayList dataList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject stmt = null;
		ArrayList alFinalQry = new ArrayList();
		boolean insertStatus = false;
		int row = 0;
		String wrongcolCount = "Y";
		String maxRowCount = "Y";
		String minRowCount = "Y";
		if (!"".equals(strFileName)) {
			logger.info("In readExcelreteReviewUpload(): strFileName   :  "
					+ strFileName);
			query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
			String strPath = ConnectionUploadDAO.singleReturn(query);
			strPath = strPath + "/" + strFileName;
			objFile1 = new File(strPath);
			try {
				query = null;
				query = "DELETE FROM CR_FLOATING_RATE_TMP WHERE MAKER_ID='"
						+ CommonFunction.checkNull(makerName).trim() + "' ";
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject.setSql(query);
				logger.info("In readExcelforFinancialUpload()  Delete Query   :  "
						+ insertPrepStmtObject.printQuery());
				alDeleteQuery.add(insertPrepStmtObject);
				deleteStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alDeleteQuery);
				logger.info("In readExcelforFinancialUpload()  Delete Status  :  "
						+ deleteStatus);
				if (deleteStatus) {
					Workbook workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[] = workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;
					sheet = workbook.getSheet(0);
					ArrayList arr = null;
					row = sheet.getRows();
					if (row > 1001) {
						maxRowCount = "Y";
						minRowCount = "N";
						wrongcolCount = "N";
					}
					if (row <= 1) {
						maxRowCount = "N";
						minRowCount = "Y";
						wrongcolCount = "N";

					}
					if (1 < row && row < 1001) {
						maxRowCount = "N";
						minRowCount = "N";
						wrongcolCount = "N";

					}
					if (maxRowCount == "N" && minRowCount == "N") {
						wrongcolCount = "N";
						for (int i = 0; i < row; i++) {
							cell = sheet.getRow(i);
							arr = new ArrayList();
							int col = cell.length;
							if (col != 5) {
								wrongcolCount = "Y";
								break;
							}
							for (int j = 0; j < col; j++) {
								xlsCell = sheet.getCell(j, i);
								arr.add(xlsCell.getContents().toString());
							}
							if (wrongcolCount == "Y")
								break;
							dataList.add(arr);

						}
					}// end of if(maxRowCount=="N" && minRowCount=="N")
					if (maxRowCount == "Y")
						result = "MXE";
					if (minRowCount == "Y")
						result = "MNE";
					if (wrongcolCount == "Y")
						result = "CLE";
					if (CommonFunction.checkNull(result).trim()
							.equalsIgnoreCase("")) {
						bufInsSql
								.append("INSERT INTO CR_FLOATING_RATE_TMP (SR_NO,LOAN_ID,EFFECTIVE_DATE,EFFECTIVE_RATE,REMARKS,REJECT_FLAG,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?, "); // SR_NO
						bufInsSql.append(" ?,");// LOAN_ID
						bufInsSql.append(" ?,");// EFFECTIVE_DATE
						bufInsSql.append(" ?,");// EFFECTIVE_RATE
						bufInsSql.append(" ?,"); // REMARKS
						bufInsSql.append(" ?,"); // REJECT_FLAG
						bufInsSql.append(" ?,"); // MAKER_ID
						bufInsSql.append(" ?)"); // MAKER_DATE
						for (int i = 1; i < dataList.size(); i++) {
							ArrayList subList = (ArrayList) dataList.get(i);
							// String
							// SR_NO=CommonFunction.checkNull(subList.get(0));
							// String
							// LOAN_ID=CommonFunction.checkNull(subList.get(1));
							// String
							// EFFECTIVE_DATE=CommonFunction.checkNull(subList.get(2));
							// String
							// EFFECTIVE_RATE=CommonFunction.checkNull(subList.get(3));
							// String
							// REMARKS=CommonFunction.checkNull(subList.get(4));

							stmt = new PrepStmtObject();
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(0)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(0)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(1)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(1)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(2)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(2)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(3)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(3)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(4)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(4)));
							stmt.addString("N");
							if (CommonFunction.checkNull(makerName)
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(makerName);

							stmt.addString(makerDate);

							stmt.setSql(bufInsSql.toString());
							logger.info("In readExcelfor rateReviewUpload()  Insert Query :  "
									+ stmt.printQuery());
							alFinalQry.add(stmt);
							stmt = null;
							subList = null;
						}// end of for loop
						insertStatus = ConnectionUploadDAO
								.sqlInsUpdDeletePrepStmt(alFinalQry);
						logger.info("In readExcelreteReviewUpload()  Insert Status :  "
								+ insertStatus);
						if (insertStatus) {
							ArrayList<Object> in = new ArrayList<Object>();
							ArrayList<Object> out = new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							in.add(CommonFunction.checkNull(makerName));
							in.add(makerDate);
							String s1 = null;
							String s2 = null;
							out.add(s1);
							out.add(s2);
							logger.info("Floating_Rate_Review(" + in.toString()
									+ "," + out.toString() + ")");
							outMessages = (ArrayList) ConnectionUploadDAO
									.callSP("Floating_Rate_Review", in, out);
							s1 = (String) outMessages.get(0);
							s2 = (String) outMessages.get(1);
							logger.info("In readExcelreteReviewUpload()  OUT s1 : "
									+ s1);
							logger.info("In readExcelreteReviewUpload()  OUT s2 : "
									+ s2);
							if (!CommonFunction.checkNull(s2).trim()
									.equalsIgnoreCase(""))
								result = "PDE" + "$" + s2;
							s1 = null;
							s2 = null;
							outMessages.clear();
							outMessages = null;
							in.clear();
							in = null;
							out.clear();
							out = null;
						}// end of if(insertStatus)
						else
							result = "PRE";
					}// end of if(wrongData=="N" && wrongcolCount=="N")
				}// end of if(deleteStatus)
				else
					result = "PRE";
			} catch (Exception e) {
				result = "PRE";
				e.printStackTrace();
			} finally {
				objFile1 = null;
				query = null;
				alDeleteQuery.clear();
				alDeleteQuery = null;
				insertPrepStmtObject = null;
				dataList.clear();
				dataList = null;
				stmt = null;
				alFinalQry.clear();
				alFinalQry = null;
				wrongcolCount = null;
				maxRowCount = null;
				minRowCount = null;
				strPath = null;
				strFileName = null;
				makerName = null;
				makerDate = null;
			}
		}// end of if(!strFileName.equals(""))
		else
			result = "FNE";
		logger.error("In readExcelforFinancialUpload()  return Result :  "
				+ result);
		return result;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

	public static boolean readExcelforReciptUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName, String branchId)
			throws Exception {
		logger.info("In readExcelforReciptUpload() method of UploadDocuments. ");
		// CallableStatement cst=null;
		HttpSession session = null;
		String makerID = null;
		ArrayList alFinalQry = new ArrayList();
		ArrayList alDeleteQuery = new ArrayList(1);
		ArrayList dataList = new ArrayList();
		ArrayList arr = new ArrayList();
		ArrayList subList = new ArrayList();
		int rowTotalNum = 0;
		int row = 0;
		int col = 0;
		String msg = null;
		boolean singleObject;
		File objFile1 = null;
		uploadVO vo = new uploadVO();
		boolean status = false;
		int intRem = 0;
		int noOfRejectedRecord = 0;
		int noOfUploadedRecord = 0;
		String strPath = null;
		String strCountDeleteQuery = null;
		PrepStmtObject insertPrepStmtObject = null;
		String branchShortCode = ConnectionUploadDAO
				.singleReturn("SELECT BRANCH_SHORT_CODE FROM COM_BRANCH_M WHERE BRANCH_ID='"
						+ CommonFunction.checkNull(branchId) + "'");
		try {
			session = request.getSession(false);
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");

			if (userobj != null) {
				vo.setMakerId(userobj.getUserId());
				vo.setMakerDate(userobj.getBusinessdate());
			}
			if (!strFileName.equals("")) {
				logger.info("In readExcelforReciptUpload(): strFileName ==>>"
						+ strFileName);
				String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
				strPath = ConnectionUploadDAO.singleReturn(query);
				strPath = strPath + "/" + strFileName;
				objFile1 = new File(strPath);
				try {
					strCountDeleteQuery = ConnectionUploadDAO
							.singleReturn("SELECT COUNT(1) FROM cr_instrument_dtl_temp WHERE MAKER_ID = '"
									+ vo.getMakerId() + "' ");

					if (!"0".equalsIgnoreCase(strCountDeleteQuery)) {
						String strDeleteQuery = "DELETE FROM cr_instrument_dtl_temp WHERE MAKER_ID = '"
								+ vo.getMakerId() + "' ";
						logger.info("In readExcelforReciptUpload(): delete query of cr_instrument_dtl_temp:==>>"
								+ strDeleteQuery);
						alDeleteQuery.add(strDeleteQuery);
						boolean status1 = ConnectionUploadDAO
								.sqlInsUpdDelete(alDeleteQuery);
						strDeleteQuery = null;
						logger.info("In readExcelforReciptUpload(): status1 ==>>"
								+ status1);
						// if(status1)
						// {
						// logger.info("Row is deleted....");
						// }
						// else
						// {
						// logger.info("Row is not deleted......");
						// }

					}
					dataList = new ArrayList();
					Workbook workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[] = workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;

					for (int p = 0; p < sheetName.length; p++) {
						sheet = workbook.getSheet(p);
						for (int i = 0; i < sheet.getRows(); i++) {
							cell = sheet.getRow(i);
							arr = new ArrayList();
							logger.info("In readExcelforReciptUpload(): :cell.length"
									+ cell.length);
							for (int j = 0; j < cell.length; j++) {
								xlsCell = sheet.getCell(j, i);
								logger.info("In readExcelforReciptUpload():xlsCell.getContents().toString() :"
										+ xlsCell.getContents().toString());
								arr.add(xlsCell.getContents().toString());
							}
							dataList.add(arr);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				logger.info("####### readExcelforBounceUpload() dataList.size() ###### "
						+ dataList.size());

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into cr_instrument_dtl_temp(LOAN_NO,DEFAULT_BRANCH,RECEIPT_MODE,INSTRUMENT_NO,BANK_ID,BRANCH_ID,BANK_ACCOUNT,RECEIPT_DATE,INSTRUMENT_DATE,RECEIPT_AMOUNT,TDS_AMOUNT,MAKER_REMARKS,RECEIPT_NO,MAKER_ID,CHARGE_CODE_ID,SEQUENCE_NO)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");// LOAN_NO
				bufInsSql.append(" ?, "); // DEFAULT_BRANCH
				bufInsSql.append(" ?,");// RECEIPT_MODE
				bufInsSql.append(" ?,");// INSTRUMENT_NO
				bufInsSql.append(" ?,"); // BANK_ID
				bufInsSql.append(" ?,"); // BRANCH_ID
				bufInsSql.append(" ?,"); // BANK_ACCOUNT
				bufInsSql.append(" ?,");// RECEIPT_DATE
				bufInsSql.append(" ?,"); // INSTRUMENT_DATE
				bufInsSql.append(" ?,");// RECEIPT_AMOUNT
				bufInsSql.append(" ?,"); // TDS_AMOUNT
				bufInsSql.append(" ?,"); // MAKER_REMARKS
				bufInsSql.append(" ?,"); // RECIPT_NO
				bufInsSql.append(" ?,"); // maker id
				bufInsSql.append(" ?,"); // CHARGE_CODE_ID
				bufInsSql.append(" ? "); // SEQUENCE_NO
				bufInsSql.append(" )");
				for (int i = 1; i < dataList.size(); i++) {
					subList = (ArrayList) dataList.get(i);
					// logger.info("In insert into readExcelforBounceUpload... size: ==>>"+subList.size());
					if (subList.size() == 14) {
						// String
						// LOAN_NO=CommonFunction.checkNull(subList.get(0));
						String receiptMode = CommonFunction.checkNull(subList
								.get(1));
						// String
						// INSTRUMENT_NO=CommonFunction.checkNull(subList.get(2));
						// String
						// BANK_ID=CommonFunction.checkNull(subList.get(3));
						// String
						// BRANCH_ID=CommonFunction.checkNull(subList.get(4));
						// String
						// BANK_ACCOUNT=CommonFunction.checkNull(subList.get(5));
						// String
						// RECEIPT_DATE=CommonFunction.checkNull(subList.get(6));
						// String
						// INSTRUMENT_DATE=CommonFunction.checkNull(subList.get(7));
						// String
						// RECEIPT_AMOUNT=CommonFunction.checkNull(subList.get(8));
						// String
						// TDS_AMOUNT=CommonFunction.checkNull(subList.get(9));
						// String
						// RECEIPT_NO=CommonFunction.checkNull(subList.get(10));
						String defaultBranch = CommonFunction.checkNull(subList
								.get(11));
						// String
						// MAKER_REMARKS=CommonFunction.checkNull(subList.get(12));

						// String makerid=vo.getMakerId();
						// String makerdate=vo.getMakerDate();
						if (defaultBranch.equalsIgnoreCase("")
								&& !receiptMode.equalsIgnoreCase("C")) {
							defaultBranch = branchShortCode;
						}
						Integer seq = new Integer(i);
						insertPrepStmtObject = new PrepStmtObject();

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(0)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(0)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(defaultBranch))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(defaultBranch));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(1)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(1)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(2)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(2)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(3)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(3)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(4)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(4)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(5)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(5)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(6)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(6)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(7)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(7)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(8)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(8)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(9)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(9)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(13)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(13)));

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(10)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(10)));

						if (CommonFunction.checkNull(vo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());

						if (CommonFunction.checkNull(
								CommonFunction.checkNull(subList.get(12)))
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction
									.checkNull(subList.get(12)));

						if (CommonFunction.checkNull(seq).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString("" + seq);

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN readExcelforBounceUpload() insert query1 ### "
								+ insertPrepStmtObject.printQuery());
						alFinalQry.add(insertPrepStmtObject);
						insertPrepStmtObject = null;
						subList = null;
					} else {
						msg = "IFM";
						request.setAttribute("msg", msg);
						return false;
					}
				}
				boolean insertStatus = false;
				insertStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alFinalQry);
				// neeraj

				logger.info(" Before Procedure Call       vinod .............. "
						+ insertStatus);
				ArrayList<Object> in = new ArrayList<Object>();
				ArrayList<Object> out = new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();

				in.add(1);
				String makerDate = null;
				in.add(CommonFunction.changeFormat(CommonFunction.checkNull(
						vo.getMakerDate()).trim()));
				in.add(strFileName);
				in.add(new UploadDocuments().dateFormat);
				in.add(vo.getMakerId());
				// in.add(branchShortCode);

				String s1 = null;
				String s2 = null;

				out.add(noOfRejectedRecord);
				out.add(noOfUploadedRecord);
				out.add(s1);
				out.add(s2);

				logger.info(" Receipt_Bulk_Upload_Instrument(" + in.toString()
						+ "," + out.toString() + ")");
				outMessages = (ArrayList) ConnectionUploadDAO.callSP(
						"Receipt_Bulk_Upload_Instrument", in, out);

				in.clear();
				in = null;
				out.clear();
				out = null;

				noOfRejectedRecord = Integer.parseInt((String) outMessages
						.get(0));
				noOfUploadedRecord = Integer.parseInt((String) outMessages
						.get(1));
				s1 = (String) outMessages.get(2);
				s2 = (String) outMessages.get(3);
				outMessages.clear();
				outMessages = null;

				logger.info("Bulk_Upload_Instrument_Dtl OUT s1: " + s1);
				logger.info("Bulk_Upload_Instrument_Dtl OUT s2: " + s2);
				logger.info("Bulk_Upload_Instrument_Dtl OUT noOfRejectedRecord: "
						+ noOfRejectedRecord);
				logger.info("Bulk_Upload_Instrument_Dtl OUT noOfUploadedRecord: "
						+ noOfUploadedRecord);

				if ("S".equals(s1) && insertStatus) {
					status = true;
					for (int i = 1; i < dataList.size(); i++) {
						subList = (ArrayList) dataList.get(i);
						logger.info("In insert into readExcelforBounceUpload()... size: ==>>"
								+ subList.size());
						if (subList.size() > 0) {
							logger.info("IN csv() Loan No ### "
									+ subList.get(0));
							String checkErrorQ = "select UPLOADED_FLAG from cr_instrument_dtl_temp where MAKER_ID='"
									+ vo.getMakerId()
									+ "' AND SEQUENCE_NO='"
									+ i + "' ";
							String checkErrorStatus = ConnectionUploadDAO
									.singleReturn(checkErrorQ);
							checkErrorQ = null;
							logger.info("checkErrorStatus: " + checkErrorStatus);
							if (checkErrorStatus.equalsIgnoreCase("N")) {
								logger.info("some data is not uploaded");
								status = false;
								break;
							}
							checkErrorStatus = null;
						}
					}
				} else {
					logger.info("out parameter of procedure is not 'S'");
					status = false;
				}
				s1 = null;
				s2 = null;
				logger.info("after procedure:status==>>" + status);
			} else {
				msg = "NF";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// objFile1.delete();
			request.setAttribute("noOfRejectedRecord", noOfRejectedRecord);
			request.setAttribute("noOfUploadedRecord", noOfUploadedRecord);
			dataList.clear();
			dataList = null;
			alDeleteQuery.clear();
			alDeleteQuery = null;
			arr.clear();
			arr = null;
			subList.clear();
			subList = null;
			msg = null;
			strFileName = null;
			strPath = null;
			strCountDeleteQuery = null;
			makerID = null;
			alFinalQry.clear();
			alFinalQry = null;
			objFile1 = null;
			vo = null;
		}
		return status;
	}

	// Nishant Space starts
	public static String readExcelStationaryUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName, String makerName,
			String makerDate) throws Exception {
		String result = null;
		boolean status = false;
		makerDate = CommonFunction.changeFormat(makerDate);
		logger.info("In readExcelStationaryUpload() method of UploadDocuments. ");
		File objFile1 = null;
		String query = null;
		ArrayList alDeleteQuery = new ArrayList(1);
		boolean deleteStatus = false;
		PrepStmtObject insertPrepStmtObject = null;
		ArrayList dataList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject stmt = null;
		ArrayList alFinalQry = new ArrayList();
		boolean insertStatus = false;
		int row = 0;
		String wrongcolCount = "Y";
		String maxRowCount = "Y";
		String minRowCount = "Y";
		if (!"".equals(strFileName)) {
			logger.info("In readExcelStationaryUpload(): strFileName   :  "
					+ strFileName);
			query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
			String strPath = ConnectionUploadDAO.singleReturn(query);
			strPath = strPath + "/" + strFileName;
			objFile1 = new File(strPath);
			try {
				query = null;
				query = "DELETE FROM GL_STATIONARY_UPLOAD_TMP WHERE MAKER_ID='"
						+ CommonFunction.checkNull(makerName).trim() + "' ";
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject.setSql(query);
				logger.info("In readExcelStationaryUpload()  Delete Query   :  "
						+ insertPrepStmtObject.printQuery());
				alDeleteQuery.add(insertPrepStmtObject);
				deleteStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alDeleteQuery);
				logger.info("In readExcelStationaryUpload()  Delete Status  :  "
						+ deleteStatus);
				if (deleteStatus) {
					Workbook workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[] = workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;
					sheet = workbook.getSheet(0);
					ArrayList arr = null;
					row = sheet.getRows();
					if (row > 1001) {
						maxRowCount = "Y";
						minRowCount = "N";
						wrongcolCount = "N";
					}
					if (row <= 1) {
						maxRowCount = "N";
						minRowCount = "Y";
						wrongcolCount = "N";

					}
					if (1 < row && row < 1001) {
						maxRowCount = "N";
						minRowCount = "N";
						wrongcolCount = "N";

					}
					if (maxRowCount == "N" && minRowCount == "N") {
						wrongcolCount = "N";
						for (int i = 0; i < row; i++) {
							cell = sheet.getRow(i);
							arr = new ArrayList();
							int col = cell.length;
							logger.info("No of column in excel..." + col);
							if (col != 8) {
								wrongcolCount = "Y";
								break;
							}
							for (int j = 0; j < col; j++) {
								xlsCell = sheet.getCell(j, i);
								arr.add(xlsCell.getContents().toString());
							}
							if (wrongcolCount == "Y")
								break;
							dataList.add(arr);

						}
					}// end of if(maxRowCount=="N" && minRowCount=="N")
					if (maxRowCount == "Y")
						result = "MXE";
					if (minRowCount == "Y")
						result = "MNE";
					if (wrongcolCount == "Y")
						result = "CLE";
					if (CommonFunction.checkNull(result).trim()
							.equalsIgnoreCase("")) {
						bufInsSql
								.append("INSERT INTO GL_STATIONARY_UPLOAD_TMP (STATIONARY_TYPE,BANK_ID,BOOK_NO,NO_OF_INSTRUMENTS_RECEIPTS,INSTRUMENT_RECEIPT_FORM,RECEIPT_INSTRUMENT_TO,ADDITION_DATE,STATUS,REJECT_FLAG,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?, ");// STATIONARY_TYPE
						bufInsSql.append(" ?,"); // BANK_ID
						bufInsSql.append(" ?,"); // BOOK_NO
						bufInsSql.append(" ?,"); // NO_OF_INSTRUMENTS_RECEIPTS
						bufInsSql.append(" ?,"); // INSTRUMENT_RECEIPT_FORM
						bufInsSql.append(" ?,"); // RECEIPT_INSTRUMENT_TO
						bufInsSql.append(" ?,"); // ADDITION_DATE
						bufInsSql.append(" ?,"); // STATUS
						bufInsSql.append(" ?,"); // REJECT_FLAG
						bufInsSql.append(" ?,"); // MAKER_ID
						bufInsSql.append(" ?)"); // MAKER_DATE
						for (int i = 1; i < dataList.size(); i++) {
							ArrayList subList = (ArrayList) dataList.get(i);

							stmt = new PrepStmtObject();
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(0)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(0)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(1)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(1)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(2)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(2)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(3)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(3)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(4)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(4)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(5)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(5)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(6)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(6)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(7)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(7)));
							stmt.addString("N");
							if (CommonFunction.checkNull(makerName)
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(makerName);

							stmt.addString(makerDate);

							stmt.setSql(bufInsSql.toString());
							logger.info("In readExcelfor readExcelStationaryUpload()  Insert Query :  "
									+ stmt.printQuery());
							alFinalQry.add(stmt);
							stmt = null;
							subList = null;
						}// end of for loop
						insertStatus = ConnectionUploadDAO
								.sqlInsUpdDeletePrepStmt(alFinalQry);
						logger.info("In readExcelStationaryUpload()  Insert Status :  "
								+ insertStatus);
						if (insertStatus) {
							ArrayList<Object> in = new ArrayList<Object>();
							ArrayList<Object> out = new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							in.add(CommonFunction.checkNull(makerName));
							in.add(makerDate);
							String s1 = null;
							String s2 = null;

							out.add(s1);
							out.add(s2);
							logger.info("STATIONARY_UPLOAD(" + in.toString()
									+ "," + out.toString() + ")");
							outMessages = (ArrayList) ConnectionUploadDAO
									.callSP("STATIONARY_UPLOAD", in, out);
							s1 = (String) outMessages.get(0);
							s2 = (String) outMessages.get(1);
							logger.info("In readExcelStationaryUpload()  OUT s1 : "
									+ s1);
							logger.info("In readExcelStationaryUpload()  OUT s2 : "
									+ s2);

							if (!CommonFunction.checkNull(s2).trim()
									.equalsIgnoreCase(""))
								result = "PDE" + "$" + s2;
							s1 = null;
							s2 = null;
							outMessages.clear();
							outMessages = null;
							in.clear();
							in = null;
							out.clear();
							out = null;
						}// end of if(insertStatus)
						else
							result = "PRE";
					}// end of if(wrongData=="N" && wrongcolCount=="N")
				}// end of if(deleteStatus)
				else
					result = "PRE";
			} catch (Exception e) {
				result = "PRE";
				e.printStackTrace();
			} finally {
				objFile1 = null;
				query = null;
				alDeleteQuery.clear();
				alDeleteQuery = null;
				insertPrepStmtObject = null;
				dataList.clear();
				dataList = null;
				stmt = null;
				alFinalQry.clear();
				alFinalQry = null;
				wrongcolCount = null;
				maxRowCount = null;
				minRowCount = null;
				strPath = null;
				strFileName = null;
				makerName = null;
				makerDate = null;
				bufInsSql = null;
			}
		}// end of if(!strFileName.equals(""))
		else
			result = "FNE";
		logger.error("In readExcelStationaryUpload()  return Result :  "
				+ result);
		return result;
	}

	public static String readExcelnotepadUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName, String makerName,
			String makerDate) throws Exception {
		String result = null;
		boolean status = false;
		makerDate = CommonFunction.changeFormat(makerDate);
		logger.info("In readExcelnotepadUpload() method of UploadDocuments. ");
		File objFile1 = null;
		String query = null;
		ArrayList alDeleteQuery = new ArrayList(1);
		boolean deleteStatus = false;
		PrepStmtObject insertPrepStmtObject = null;
		ArrayList dataList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject stmt = null;
		ArrayList alFinalQry = new ArrayList();
		boolean insertStatus = false;
		int row = 0;
		String wrongcolCount = "Y";
		String maxRowCount = "Y";
		String minRowCount = "Y";
		if (!"".equals(strFileName)) {
			query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
			String strPath = ConnectionUploadDAO.singleReturn(query);
			strPath = strPath + "/" + strFileName;
			objFile1 = new File(strPath);
			try {
				query = null;
				query = "DELETE FROM notepad_upload_tmp WHERE MAKER_ID='"
						+ CommonFunction.checkNull(makerName).trim() + "' ";
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject.setSql(query);
				logger.info("In notepad_upload_tmp()  Delete Query   :  "
						+ insertPrepStmtObject.printQuery());
				alDeleteQuery.add(insertPrepStmtObject);
				deleteStatus = ConnectionUploadDAO
						.sqlInsUpdDeletePrepStmt(alDeleteQuery);
				logger.info("In notepad_upload_tmp()  Delete Status  :  "
						+ deleteStatus);
				if (deleteStatus) {
					Workbook workbook = Workbook.getWorkbook(new File(strPath));
					String sheetName[] = workbook.getSheetNames();
					Sheet sheet;
					Cell xlsCell;
					Cell[] cell;
					sheet = workbook.getSheet(0);
					ArrayList arr = null;
					row = sheet.getRows();
					if (row > 1001) {
						maxRowCount = "Y";
						minRowCount = "N";
						wrongcolCount = "N";
					}
					if (row <= 1) {
						maxRowCount = "N";
						minRowCount = "Y";
						wrongcolCount = "N";

					}
					if (1 < row && row < 1001) {
						maxRowCount = "N";
						minRowCount = "N";
						wrongcolCount = "N";
					}
					if (maxRowCount == "N" && minRowCount == "N") {
						for (int i = 0; i < row; i++) {
							cell = sheet.getRow(i);
							arr = new ArrayList();
							int col = cell.length;

							if (col != 15) {
								wrongcolCount = "Y";
								break;
							}
							for (int j = 0; j < col; j++) {
								xlsCell = sheet.getCell(j, i);
								arr.add(xlsCell.getContents().toString());
							}
							if (wrongcolCount == "Y")
								break;
							dataList.add(arr);
						}
					}// end of if(maxRowCount=="N" && minRowCount=="N")
					if (maxRowCount == "Y")
						result = "MXE";
					if (minRowCount == "Y")
						result = "MNE";
					if (wrongcolCount == "Y")
						result = "CLE";
					if (CommonFunction.checkNull(result).trim()
							.equalsIgnoreCase("")) {
						bufInsSql
								.append("INSERT INTO notepad_upload_tmp (DEAL_NO,LOAN_NO,NOTE_CODE,MEETING_DATE_TIME,PERSON_MET,MEETING_LOCATION,MEETING_REMARKS,FOLLOW_UP_REQUIRED,FOLLOW_UP_DATE_TIME,FOLLOW_UP_PERSON,FOLLOW_UP_LOCATION,FOLLOW_UP_REMARKS,REJECT_REASON,MAKER_ID,MAKER_DATE,UPLOAD_FLAG)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?, ");// DEAL_NO
						bufInsSql.append(" ?, ");// LOAN_NO
						bufInsSql.append(" ?, ");// NOTE_CODE
						bufInsSql.append(" ?, "); // MEETING_DATE_TIME
						bufInsSql.append(" ?,");// PERSON_MET
						bufInsSql.append(" ?,");// MEETING_LOCATION
						bufInsSql.append(" ?,");// MEETING_REMARKS
						bufInsSql.append(" ?,"); // FOLLOW_UP_REQUIRED
						bufInsSql.append(" ?,"); // FOLLOW_UP_DATE_TIME
						bufInsSql.append(" ?,"); // FOLLOW_UP_PERSON
						bufInsSql.append(" ?,"); // FOLLOW_UP_LOCATION
						bufInsSql.append(" ?,"); // Follow_UP_Remarks
						bufInsSql.append(" ?,"); // REJECT_REASON
						bufInsSql.append(" ?,");// MAKER_ID
						bufInsSql.append(" ?,"); // MAKER_DATE
						bufInsSql.append(" ?)"); // UPLOAD_FLAG
						for (int i = 1; i < dataList.size(); i++) {
							ArrayList subList = (ArrayList) dataList.get(i);

							stmt = new PrepStmtObject();
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(0)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(0)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(1)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(1)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(2)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(2)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(3)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(3)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(4)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(4)));
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(5)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(5)));
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(6)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(6)));
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(7)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(7)));
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(8)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(8)));
							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(9)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(9)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(10)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(10)));

							if (CommonFunction.checkNull(
									CommonFunction.checkNull(subList.get(11)))
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(CommonFunction.checkNull(subList
										.get(11)));

							stmt.addString("N");
							if (CommonFunction.checkNull(makerName)
									.equalsIgnoreCase(""))
								stmt.addNull();
							else
								stmt.addString(makerName);

							stmt.addString(makerDate);
							stmt.addString("Y");

							stmt.setSql(bufInsSql.toString());

							logger.info("In readExcelnotepadUpload()  Insert Query :  "
									+ stmt.printQuery());
							alFinalQry.add(stmt);
							stmt = null;
							subList = null;
						}// end of for loop
						insertStatus = ConnectionUploadDAO
								.sqlInsUpdDeletePrepStmt(alFinalQry);
						logger.info("In readExcelnotepadUpload()  Insert Status :  "
								+ insertStatus);
						logger.info("In readExcelnotepadUpload() makerName :  "
								+ makerName);
						if (insertStatus) {
							ArrayList<Object> in = new ArrayList<Object>();
							ArrayList<Object> out = new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							in.add(CommonFunction.checkNull(makerName));
							in.add(makerDate);
							String rejectFlg = null;
							String uplodFlg = null;
							String s1 = null;
							String s2 = null;
							out.add(rejectFlg);
							out.add(uplodFlg);
							out.add(s1);
							out.add(s2);
							logger.info("Notepad Upload(" + in.toString() + ","
									+ out.toString() + ")");
							outMessages = (ArrayList) ConnectionUploadDAO
									.callSP("NOTEPAD_UPLOAD", in, out);
							rejectFlg = (String) outMessages.get(0);
							uplodFlg = (String) outMessages.get(1);
							s1 = (String) outMessages.get(2);
							s2 = (String) outMessages.get(3);
							logger.info("In readExcelnotepadUpload()  OUT rejectFlg : "
									+ rejectFlg);
							logger.info("In readExcelnotepadUpload()  OUT uplodFlg  : "
									+ uplodFlg);
							logger.info("In readExcelnotepadUpload()  OUT s1        : "
									+ s1);
							logger.info("In readExcelnotepadUpload()  OUT s2        : "
									+ s2);
							if (!CommonFunction.checkNull(s2).trim()
									.equalsIgnoreCase(""))
								result = "PDE" + "$" + s2;
							else {
								result = resource.getString("lbl.sms");
								if (CommonFunction.checkNull(rejectFlg).trim()
										.equalsIgnoreCase(""))
									rejectFlg = "0";
								int rej = Integer.parseInt(rejectFlg);
								if (CommonFunction.checkNull(uplodFlg).trim()
										.equalsIgnoreCase(""))
									uplodFlg = "0";
								int up = Integer.parseInt(uplodFlg);
								if (rej > 0)
									result = result + "Total rejected record-"
											+ rej
											+ "  and Total uploaded record-"
											+ up
											+ ", Please check Error log...";
							}
							s1 = null;
							s2 = null;
							outMessages.clear();
							outMessages = null;
							in.clear();
							in = null;
							out.clear();
							out = null;
						}// end of if(insertStatus)
						else
							result = "PRE";
					} else
						result = "PRE";
				}
			} catch (Exception e) {
				result = "PRE";
				e.printStackTrace();
			} finally {
				objFile1 = null;
				query = null;
				alDeleteQuery.clear();
				alDeleteQuery = null;
				insertPrepStmtObject = null;
				dataList.clear();
				dataList = null;
				stmt = null;
				alFinalQry.clear();
				alFinalQry = null;
				wrongcolCount = null;
				maxRowCount = null;
				minRowCount = null;
				strPath = null;
				strFileName = null;
				makerName = null;
				makerDate = null;
			}
		}// end of if(!strFileName.equals(""))
		else
			result = "FNE";
		logger.error("In readExcelnotepadUpload()  return Result :  " + result);
		return result;
	}
}
