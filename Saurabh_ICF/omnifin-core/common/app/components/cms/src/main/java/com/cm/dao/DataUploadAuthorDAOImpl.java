package com.cm.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.a3s.ImportServiceManager;
import com.cm.FtpToServer;

import com.cm.vo.AdditionalDisbursalSearchVO;
import com.cm.vo.CaseMarkingMakerVO;
import com.cm.vo.DataUploadVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CollateralVo;
import com.data.integration.servlet.PropertyHome;

public class DataUploadAuthorDAOImpl implements DataUploadAuthorDAO {

	private static final Logger logger = Logger
			.getLogger(DataUploadAuthorDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal = resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public ArrayList<DataUploadVO> searchUplodedDataList(DataUploadVO vo ) {
		logger
				.info("In searchUplodedDataList method of DataUploadAuthorDAOImpl");

		StringBuilder userName = new StringBuilder();

		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList detailList = new ArrayList();

		try {
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql
					.append("SELECT FILE_NAME,FILE_DESCRIPTION,TOTAL_TXN_AMOUNT,ROW_COUNT FROM import_mannual_file_dtl WHERE STATUS_FLAG='F' AND MAKER_ID<>'"+vo.getMakerId()+"'");
			bufInsSqlTempCount
					.append("SELECT COUNT(*) FROM import_mannual_file_dtl WHERE STATUS_FLAG='F' AND MAKER_ID<>'"+vo.getMakerId()+"'");

			String tmp = bufInsSql.toString();
			String tmp1 = bufInsSqlTempCount.toString();
			logger.info("In searchUplodedDataList() ## tmp ## " + tmp);
			logger.info("In searchUplodedDataList() ## tmp1 ## " + tmp1);

			searchlist = ConnectionDAO.sqlSelect(tmp);
			count = Integer.parseInt(ConnectionDAO
					.singleReturn(tmp1.toString()));

			logger.info("searchlist SIZE: " + searchlist.size());
			if(searchlist.size()>0){
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("searchUplodedDataList: "
							+ searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
	
						DataUploadVO dataUploadVO = new DataUploadVO();
						dataUploadVO.setFileName((CommonFunction.checkNull(data
								.get(0)).trim()));
						dataUploadVO.setFileDescription((CommonFunction
								.checkNull(data.get(1)).trim()));
						dataUploadVO.setTotalTaxAmount((CommonFunction
								.checkNull(data.get(2)).trim()));
						dataUploadVO.setRowCount((CommonFunction.checkNull(data
								.get(3)).trim()));
	
						dataUploadVO.setTotalRecordSize(count);
						detailList.add(dataUploadVO);
					}
	
				}
			}
			bufInsSql = null;
			bufInsSqlTempCount = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			userName = null;

		}

		return detailList;
	}

	@Override
	public boolean ftpData(String fileNameAll) {

		String check = "";
		boolean result = false;
		StringBuffer strbfr = null;

		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";

		String path = ConnectionDAO.singleReturn(query);
		logger.info("In docUpload(): path:==>>" + path);

		String ftpdata = "";
		String IP = "";
		String USERNAME = "";
		String PASSWORD = "";
		String FTP_INPUT_PATH = "";
		// import_ftp_dtl
		ArrayList searchlist = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();

		bufInsSql
				.append("SELECT IP,USERNAME,PASSWORD,FTP_INPUT_PATH FROM import_ftp_dtl WHERE ID='1'");
		String tmp = bufInsSql.toString();

		logger.info("In ftpData() ## tmp ## " + tmp);

		try {
			searchlist = ConnectionDAO.sqlSelect(tmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("searchUplodedDataList: "
					+ searchlist.get(i).toString());
			ftpdata = searchlist.get(i).toString();

			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {

				System.out.println("data list " + data.get(0));
				System.out.println("data list " + data.get(1));
				System.out.println("data list " + data.get(2));
				System.out.println("data list " + data.get(3));
				IP = (String) data.get(0);
				USERNAME = (String) data.get(1);
				PASSWORD = (String) data.get(2);
				FTP_INPUT_PATH = (String) data.get(3);
			} else {
				return false;
			}
		}

		strbfr = null;
		strbfr = new StringBuffer(path + "\\" + fileNameAll);
		try {
			new FtpToServer(strbfr, IP, USERNAME, PASSWORD, FTP_INPUT_PATH);
		} catch (Exception e) {
			check = "error";
			e.printStackTrace();
		}

		if (check == "") {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long searchCheckSumTable(String fileNameAll) {
		long count = 0;
		StringBuffer bufInsSqlTempCount = new StringBuffer();

		bufInsSqlTempCount
				.append("SELECT CHECKSUM FROM import_mannual_file_dtl WHERE FILE_NAME='"
						+ fileNameAll + "' AND STATUS_FLAG='F'");
		String tmp1 = bufInsSqlTempCount.toString();
		logger.info("In searchUplodedDataList() ## tmp1 ## " + tmp1);

		count = Long.parseLong(ConnectionDAO.singleReturn(tmp1.toString()));
		return count;
	}

	@Override
	public void updateFlag(String fileNameAll, String authorRemarks) {
		PrepStmtObject insPrepStmtObject = null;
		ArrayList qryList = new ArrayList();
		StringBuilder query = null;
		boolean status;
		insPrepStmtObject = new PrepStmtObject();
		query = new StringBuilder();

		query
				.append("UPDATE import_mannual_file_dtl SET STATUS_FLAG=? ,AUTHOR_REMARKS=? WHERE FILE_NAME=? AND STATUS_FLAG=?");

		insPrepStmtObject.addString("A");
		insPrepStmtObject.addString(authorRemarks);
		insPrepStmtObject.addString((CommonFunction.checkNull(fileNameAll)));
		insPrepStmtObject.addString("F");

		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: " + insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Query: after null" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			insPrepStmtObject = null;
		}
	}

	public void updateRejectFlag(String fileNameAll, String authorRemarks) {
		PrepStmtObject insPrepStmtObject = null;
		ArrayList qryList = new ArrayList();
		StringBuilder query = null;
		boolean status;
		insPrepStmtObject = new PrepStmtObject();
		query = new StringBuilder();

		query
				.append("UPDATE import_mannual_file_dtl SET STATUS_FLAG=?,AUTHOR_REMARKS=? WHERE FILE_NAME=? AND STATUS_FLAG=?");

		insPrepStmtObject.addString("X");
		insPrepStmtObject.addString(authorRemarks);
		insPrepStmtObject.addString((CommonFunction.checkNull(fileNameAll)));
		insPrepStmtObject.addString("F");

		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: " + insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Query: after null" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			insPrepStmtObject = null;
		}
	}

	public void updateSendBackFlag(String fileNameAll, String authorRemarks) {
		PrepStmtObject insPrepStmtObject = null;
		ArrayList qryList = new ArrayList();
		StringBuilder query = null;
		boolean status;
		insPrepStmtObject = new PrepStmtObject();
		query = new StringBuilder();

		query
				.append("UPDATE import_mannual_file_dtl SET STATUS_FLAG=?,AUTHOR_REMARKS=? WHERE FILE_NAME=? AND STATUS_FLAG=?");

		insPrepStmtObject.addString("P");
		insPrepStmtObject.addString(authorRemarks);
		insPrepStmtObject.addString((CommonFunction.checkNull(fileNameAll)));
		insPrepStmtObject.addString("F");

		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: " + insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Query: after null" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			insPrepStmtObject = null;
		}
	}

	@Override
	public ArrayList<String> searcProcessData(DataUploadVO vo) {
		logger.info("In searcProcessData method of DataUploadAuthorDAOImpl");

		StringBuilder userName = new StringBuilder();

		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList detailList = new ArrayList();

		try {
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql
					.append("SELECT FILE_NAME,FILE_DESCRIPTION,TOTAL_TXN_AMOUNT,ROW_COUNT,AUTHOR_REMARKS FROM import_mannual_file_dtl WHERE STATUS_FLAG='P' AND MAKER_ID='"
							+ vo.getMakerId() + "'");
			bufInsSqlTempCount
					.append("SELECT COUNT(*) FROM import_mannual_file_dtl WHERE STATUS_FLAG='P'");

			String tmp = bufInsSql.toString();
			String tmp1 = bufInsSqlTempCount.toString();
			logger.info("In searchUplodedDataList() ## tmp ## " + tmp);
			logger.info("In searchUplodedDataList() ## tmp1 ## " + tmp1);

			searchlist = ConnectionDAO.sqlSelect(tmp);
			count = Integer.parseInt(ConnectionDAO
					.singleReturn(tmp1.toString()));

			logger.info("searchlist SIZE: " + searchlist.size());
			if(searchlist.size()>0){
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("searchUplodedDataList: "
							+ searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
	
						DataUploadVO dataUploadVO = new DataUploadVO();
						dataUploadVO.setFileName((CommonFunction.checkNull(data
								.get(0)).trim()));
						dataUploadVO.setFileDescription((CommonFunction
								.checkNull(data.get(1)).trim()));
						dataUploadVO.setTotalTaxAmount((CommonFunction
								.checkNull(data.get(2)).trim()));
						dataUploadVO.setRowCount((CommonFunction.checkNull(data
								.get(3)).trim()));
						dataUploadVO.setAuthorRemarks((CommonFunction
								.checkNull(data.get(4)).trim()));
						dataUploadVO.setTotalRecordSize(count);
						detailList.add(dataUploadVO);
					}
	
				}
			}
			bufInsSql = null;
			bufInsSqlTempCount = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			userName = null;

		}

		return detailList;

	}

	public boolean checkDuplicateFile(String fileName) {

		boolean result = false;
		String count = null;
		StringBuffer bufInsSqlTempCount = new StringBuffer();

		bufInsSqlTempCount
				.append("SELECT count(*) FROM import_mannual_file_dtl WHERE FILE_NAME='"
						+ fileName + "' AND STATUS_FLAG='P'");
		String tmp1 = bufInsSqlTempCount.toString();
		logger.info("In searchUplodedDataList() ## tmp1 ## " + tmp1);

		count = ConnectionDAO.singleReturn(tmp1.toString());

		if (!count.equalsIgnoreCase("0")) {
			result = true;
		}
		return result;
	}

	public boolean docUpload(HttpServletRequest request, FormFile myFile) {
		boolean status = false;
		String fileName = "";
		String filePath = "";
		String message = "";
		FileOutputStream fileOutStream = null;
		File fileToCreate = null;
		try {
			String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";
			logger.info("In docUpload(): query:==>>" + query);

			String rpt = ConnectionDAO.singleReturn(query);
			logger.info("The name you have entered is a file : " + myFile);

			File directory = new File(rpt + "/");
			logger.info("docUpload:directory:==>" + directory);
			boolean isDirectory = directory.isDirectory();
			logger.info("vinod:docUpload:isDirectory:==>" + isDirectory);
			fileName = myFile.getFileName();
			// Get the servers upload directory real path name
			filePath = directory.getAbsolutePath();
			/* Save file on the server */
			if (!fileName.equals("")) {
				logger.info("Server path: filePath:==>>" + filePath);
				logger.info("filename :" + fileName);
				// Create file
				fileToCreate = new File(filePath, fileName);
				int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
				logger.info("docUpload :Size of file==>> " + fileSize);
				if (fileSize < 1048576) {
					fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
					message = "O";
					status = true;
				} else {
					message = "E";
					// logger.info("File size exceeds the upper limit of 1 MB.");
					status = false;
				}
			} else {
				message = "S";
				// logger.info("Please select a File.");
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutStream.flush();
				fileOutStream.close();
				fileOutStream = null;

				System.gc();
			} catch (IOException ie) {
				ie.printStackTrace();
			}

		}
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message", message);
		return status;
	}

	public long calculateCheckSum(String fileName) {

		boolean status = false;

		String filePath = "";
		String message = "";
		long checksumValue = 0;
		FileInputStream fis = null;
		CheckedInputStream cis = null;
		BufferedInputStream in = null;

		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";
		logger.info("In docUpload(): query:==>>" + query);

		String rpt = ConnectionDAO.singleReturn(query);

		File directory = new File(rpt + "/");
		logger.info("docUpload:directory:==>" + directory);
		boolean isDirectory = directory.isDirectory();
		logger.info("docUpload:isDirectory:==>" + isDirectory);
		// fileName = myFile.getFileName();
		// Get the servers upload directory real path name
		filePath = directory.getAbsolutePath();
		// String FilePathRev=filePath.replace('\\', '/');
		try {
			fis = new FileInputStream(new File(filePath, fileName));
			cis = new CheckedInputStream(fis, new CRC32());
			in = new BufferedInputStream(cis);
			while (in.read() != -1) {
			}
			checksumValue = cis.getChecksum().getValue();
			System.out.println("Checksum is: " + cis.getChecksum().getValue());
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		} finally {

			try {
				fis.close();
				fis = null;
				cis.close();
				cis = null;
				in.close();
				in = null;
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return checksumValue;
	}

	public boolean insertImportMannualFileDtl(HttpServletRequest request,
			FormFile myFile, long checkSumValue, int row_count,
			double total_txn_amount, DataUploadVO vo) {

		boolean status = false;
		String fileName = "";
		String filePath = "";
		String message = "";
		long checksumValue = 0;

		ArrayList list = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";
		logger.info("In docUpload(): query:==>>" + query);

		String rpt = ConnectionDAO.singleReturn(query);
		logger.info("The name you have entered is a file : " + myFile);
		// String path = getServlet().getServletContext().getRealPath("")+"/"+
		// myFile.getFileName();
		File directory = new File(rpt + "/");
		logger.info("docUpload:directory:==>" + directory);
		boolean isDirectory = directory.isDirectory();
		logger.info("docUpload:isDirectory:==>" + isDirectory);
		fileName = myFile.getFileName();
		// Get the servers upload directory real path name
		filePath = directory.getAbsolutePath();

		StringBuffer bufInsSql = new StringBuffer();

		bufInsSql
				.append("insert into import_mannual_file_dtl(FILE_NAME,FILE_DESCRIPTION,CHECKSUM,STATUS_FLAG,ROW_COUNT,TOTAL_TXN_AMOUNT,MAKER_ID,MAKER_DATE,CREATED_AT)");
		bufInsSql.append(" values ( ");

		bufInsSql.append(" ?,");// FILE_NAME
		bufInsSql.append(" ?,");// FILE_DESCRIPTION
		bufInsSql.append(" ?,");// CHECKSUM
		bufInsSql.append(" ?,");// STATUS_FLAG
		bufInsSql.append(" ?,");// ROW_COUNT
		bufInsSql.append(" ?,");// TOTAL_TXN_AMOUNT
		bufInsSql.append(" ?,");// MAKER_ID
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
				+ "'),INTERVAL CURTIME() HOUR_SECOND),");// MAKER_DATE
		bufInsSql.append("NOW() ");
		bufInsSql.append(" )");

		if (CommonFunction.checkNull(fileName).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(fileName);

		if (CommonFunction.checkNull(vo.getFileDescription()).equalsIgnoreCase(
				""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getFileDescription());

		if (CommonFunction.checkNull(checkSumValue).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addInt(checkSumValue);

		insertPrepStmtObject.addString("P");

		if (CommonFunction.checkNull(row_count).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addInt(row_count);

		if (CommonFunction.checkNull(total_txn_amount).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addFloat(total_txn_amount);

		if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerId());

		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate());

		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN csv() insert query1 ### "
				+ insertPrepStmtObject.printQuery());
		list.add(insertPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
		return status;
	}

	public Vector readFile(String fileName) {

		Vector cellVectorHolder = new Vector();
		FileInputStream arquivo = null;
		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";

		String rpt = ConnectionDAO.singleReturn(query);

		String file = rpt + "/" + fileName;

		// String file_new="E:\\TA_LOANS_20121103_01.xlsx";
		// String
		// file_test="C:\\Payout_File\\Mannual Process File\\TA_LOANS_20121103_01.xlsx";
		try {
			arquivo = new FileInputStream(file);

			HSSFWorkbook planilha = new HSSFWorkbook(arquivo);

			HSSFSheet aba = planilha.getSheetAt(0);
			Iterator rowIter = aba.rowIterator();

			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				Iterator cellIter = myRow.cellIterator();

				Vector cellStoreVector = new Vector();
				while (cellIter.hasNext()) {

					HSSFCell myCell = (HSSFCell) cellIter.next();
					cellStoreVector.addElement(myCell);
				}
				cellVectorHolder.addElement(cellStoreVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				arquivo.close();
				arquivo = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cellVectorHolder;
	}

	public double read(Vector dataHolder) {
		double total = 0.0;
		for (int i = 0; i < dataHolder.size(); i++) {
			Vector cellStoreVector = (Vector) dataHolder.elementAt(i);

			for (int j = 0; j < cellStoreVector.size(); j++) {
				HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
				if (j == 4 && i != 0) {
					total = total + Double.parseDouble(myCell.toString());

				}
				// String stringCellValue = myCell.toString();
				// System.out.print(stringCellValue + "\t");
			}
			System.out.println();
		}
		System.out.println("total" + total);
		return total;
	}

	@Override
	public boolean forwardFile(String fileName) {
		PrepStmtObject insPrepStmtObject = null;
		ArrayList qryList = new ArrayList();
		StringBuilder query = null;
		boolean status = false;
		insPrepStmtObject = new PrepStmtObject();
		query = new StringBuilder();

		query
				.append("UPDATE import_mannual_file_dtl SET STATUS_FLAG=? WHERE FILE_NAME=? AND STATUS_FLAG=?");

		insPrepStmtObject.addString("F");
		insPrepStmtObject.addString((CommonFunction.checkNull(fileName)));
		insPrepStmtObject.addString("P");

		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: " + insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Query: after null" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			insPrepStmtObject = null;
		}
		return status;
	}

	public boolean deleteFile(String fileName) {
		PrepStmtObject insPrepStmtObject = null;
		ArrayList qryList = new ArrayList();
		StringBuilder query = null;
		boolean status = false;
		insPrepStmtObject = new PrepStmtObject();
		query = new StringBuilder();

		query
				.append("UPDATE import_mannual_file_dtl SET STATUS_FLAG=? WHERE FILE_NAME=? AND STATUS_FLAG=?");

		insPrepStmtObject.addString("X");
		insPrepStmtObject.addString((CommonFunction.checkNull(fileName)));
		insPrepStmtObject.addString("P");

		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: " + insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		try {
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Query: after null" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			insPrepStmtObject = null;
		}
		return status;
	}

	@Override
	public boolean removeFile(String fileName) {

		File f1 = null;
		boolean success = false;
		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";

		String path = ConnectionDAO.singleReturn(query);
		logger.info("In docUpload(): path:==>>" + path);
		try {
			f1 = new File(path + "\\" + fileName);
			f1.setWritable(true);
			logger.info("file exits$$$$$$ " + f1.exists());
			success = f1.delete();
			logger.info("sucess$$$$$$$$$$$ " + success);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			f1 = null;

		}
		return success;
	}

	@Override
	public boolean moveFileToAuthorFold(String fileName) {
		File fileMaker = null;
		File fileAuthor = null;

		InputStream inStream = null;
		OutputStream outStream = null;

		boolean success = false;
		String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path'";
		String queryAuthor = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Mannual_Process_File_Path_Author'";

		String path = ConnectionDAO.singleReturn(query);
		String pathAuthor = ConnectionDAO.singleReturn(queryAuthor);
		logger.info("In docUpload(): path:==>>" + path);
		try {
			fileMaker = new File(path + "\\" + fileName);
			fileAuthor = new File(pathAuthor + "\\" + fileName);

			inStream = new FileInputStream(fileMaker);
			outStream = new FileOutputStream(fileAuthor);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}
			inStream.close();
			outStream.close();

			success = fileMaker.delete();
			logger.info("sucess$$$$$$$$$$$ " + success);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileMaker = null;

		}
		return success;
	}

	@Override
	public void callJobManually() throws Exception {
		String appHomePath;
		String configFileName = null;
		String adapterFlag = "1";
		final String[] configFile = new String[1];

		appHomePath = PropertyHome.getAppHome().getAbsolutePath();
		if (appHomePath == null || appHomePath.trim().isEmpty()) {
			System.err.println("No Application home defined");
			throw new ServletException("No Application home defined");
		}
		File appHomePathFile = new File(appHomePath);
		if (!appHomePathFile.exists() || !appHomePathFile.isDirectory()) {
			System.err.println("No valid application home defined - "
					+ appHomePathFile.getAbsolutePath());
			throw new ServletException("No valid Application home defined- "
					+ appHomePathFile.getAbsolutePath());
		}
		configFileName = "Config_Author.xml";
		File adapterConfFile = new File(appHomePathFile, configFileName);
		if (!adapterConfFile.exists() || !adapterConfFile.isFile()
				|| !adapterConfFile.canRead()) {
			System.err.println("No valid adapter conf file found - "
					+ adapterConfFile.getAbsolutePath());
			adapterFlag = "0";
		} else {
			configFile[0] = adapterConfFile.getAbsolutePath();
		}

		boolean startImportService = false;
		if (adapterFlag.equals("1")) {
			startImportService = true;

		}

		// Thread starts
		if (startImportService) {
			System.out.println("Starting adapter with configuration - "
					+ adapterConfFile.getAbsolutePath());

			new ImportServiceManager().sync(configFile);

		}

	}

	@Override
	public ArrayList<DataUploadVO> fetchErrorLog() {

		logger.info("In fetchErrorLog method of DataUploadAuthorDAOImpl");

		StringBuilder userName = new StringBuilder();

		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList<DataUploadVO> detailList = new ArrayList<DataUploadVO>();

		String query = "select Max(id) from import_loan_data_summary_log";

		String maxId = ConnectionDAO.singleReturn(query);

		String query_error_flag = "select O_ERROR_FLAG from import_loan_data_summary_log where id='"
				+ maxId + "'";

		String error_flag = CommonFunction.checkNull(ConnectionDAO
				.singleReturn(query_error_flag));

		if (error_flag == "") {
			logger.info("error flag is null" + error_flag + "$$$$$");

			try {
				boolean appendSQL = false;
				StringBuffer bufInsSql = new StringBuffer();

				bufInsSql
						.append("SELECT filename,error_fieldname, error_description FROM import_loan_data_error_log WHERE IMPORT_ID='"
								+ maxId + "'");

				String tmp = bufInsSql.toString();

				logger.info("In fetchErrorLog() ## tmp ## " + tmp);

				searchlist = ConnectionDAO.sqlSelect(tmp);

				logger.info("searchlist SIZE: " + searchlist.size());
				if(searchlist.size()>0){
					for (int i = 0; i < searchlist.size(); i++) {
						logger.info("fetchErrorLog: "
								+ searchlist.get(i).toString());
						ArrayList data = (ArrayList) searchlist.get(i);
						if (data.size() > 0) {
	
							DataUploadVO dataUploadVO = new DataUploadVO();
	
							dataUploadVO.setFileName((CommonFunction.checkNull(data
									.get(0)).trim()));
							dataUploadVO.setErrorFieldName((CommonFunction
									.checkNull(data.get(1)).trim()));
							dataUploadVO.setError_description((CommonFunction
									.checkNull(data.get(2)).trim()));
	
							detailList.add(dataUploadVO);
						}
	
					}
				}
				bufInsSql = null;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				userName = null;

			}
		} else {
			logger.info("error flag is not null" + error_flag + "$$$$$");

			try {
				boolean appendSQL = false;
				StringBuffer bufInsSql = new StringBuffer();

				bufInsSql
						.append("SELECT i.host_loan_no ,i.host_dealer_code ,i.host_dealer_bank_acount,i.txn_date ,i.txn_amount ,i.txn_flag ,"
								+ " e.xlsrownumber ,e.error_description FROM import_loan_data_tmp i inner join import_loan_data_error_log e on "
								+ "i.import_id=e.import_id and i.xlsrownumber=e.xlsrownumber WHERE i.import_id='"
								+ maxId
								+ "' AND i.xlsrownumber IN"
								+ "(SELECT DISTINCT xlsrownumber FROM import_loan_data_error_log WHERE import_id='"
								+ maxId + "')");

				String tmp = bufInsSql.toString();

				logger.info("In fetchErrorLog() ## tmp ## " + tmp);

				searchlist = ConnectionDAO.sqlSelect(tmp);

				logger.info("searchlist SIZE: " + searchlist.size());
				if(searchlist.size()>0){
					for (int i = 0; i < searchlist.size(); i++) {
						logger.info("fetchErrorLog: "
								+ searchlist.get(i).toString());
						ArrayList data = (ArrayList) searchlist.get(i);
						if (data.size() > 0) {
	
							DataUploadVO dataUploadVO = new DataUploadVO();
	
							dataUploadVO.setHost_loan_no((CommonFunction
									.checkNull(data.get(0)).trim()));
							dataUploadVO.setHost_dealer_code((CommonFunction
									.checkNull(data.get(1)).trim()));
							dataUploadVO.setHost_dealer_bank_acount((CommonFunction
									.checkNull(data.get(2)).trim()));
							dataUploadVO.setTxn_date((CommonFunction.checkNull(data
									.get(3)).trim()));
							dataUploadVO.setTxn_amount((CommonFunction
									.checkNull(data.get(4)).trim()));
							dataUploadVO.setTxn_flag((CommonFunction.checkNull(data
									.get(5)).trim()));
							dataUploadVO.setXlsrownumber((CommonFunction
									.checkNull(data.get(6)).trim()));
							dataUploadVO.setError_description((CommonFunction
									.checkNull(data.get(7)).trim()));
	
							detailList.add(dataUploadVO);
						}
	
					}
				}
				bufInsSql = null;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				userName = null;

			}
		}
		return detailList;
	}
}
