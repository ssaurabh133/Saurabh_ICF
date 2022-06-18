package com.genericUpload.daoImplMYSQL;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.exception.OmniFinMarkForRollbackException;
import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.beans.BatchIDAndUserIDBean;
import com.genericUpload.dao.GenericUploadDAO;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.dto.TempGenericProxyDTO;
import com.genericUpload.dto.VoucherUploadParametersDTO;
import com.genericUpload.vo.GenericUploadVO;
import com.genericUpload.vo.XMLBean;
import com.ibm.icu.text.DecimalFormat;
import com.login.roleManager.UserObject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.io.StreamUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class GenericUploadDAOImpl
  implements GenericUploadDAO
{
  private static final Logger logger = Logger.getLogger(GenericUploadDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");
  SimpleDateFormat dateFormatCheck = new SimpleDateFormat("dd-MM-yyyy");
  SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd'_'hh-mm-ss");
  String dbType = this.resource.getString("lbl.dbType");
  DecimalFormat myFormatter = new DecimalFormat("###,###.####");

  Connection con = null;
  PreparedStatement ptmt = null;
  ResultSet rs = null;
  Statement stmt = null;
  CallableStatement csmt = null;

  static ThreadLocal<BatchIDAndUserIDBean> local = new ThreadLocal();

  public int deleteBatch(String batch_id, String userId, XMLBean bean)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("delete from all_upload_summary where maker_id = ? and batch_id = ? and status in ('R','B','EK','EP')");
      logger.info("delete from all_upload_summary where maker_id = " + userId + " and batch_id = " + batch_id + " and status in ('R','B','EK','EP')");
      ps.setString(1, userId);
      ps.setString(2, batch_id);
      int i = ps.executeUpdate();
      logger.info("row deleted from all_upload_summary =" + i);
      if (i > 0)
      {
        PreparedStatement ps1 = this.con.prepareStatement("delete from " + bean.getTemp_table_name() + " where batch_id = ?");
        logger.info("delete from " + bean.getTemp_table_name() + " where batch_id = " + batch_id);

        ps1.setString(1, batch_id);
        int j = ps1.executeUpdate();
        logger.info("row deleted from " + bean.getTemp_table_name() + " =" + j);
        ps1.close();
      }

      ps.close();
      this.con.close();
      return i;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  public int forwardBatch(String batch_id, String userId)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();

      PreparedStatement qs = this.con.prepareStatement("select status from all_upload_summary where maker_id = ? and batch_id = ?");
      logger.info("check status for all_upload_summary ");
      qs.setString(1, userId);
      qs.setString(2, batch_id);
      ResultSet result = qs.executeQuery();
      if (result.next())
      {
        String status = result.getString(1).toString();
        if (status.equalsIgnoreCase("F"))
        {
          return 1;
        }

        PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'F' where maker_id = ? and batch_id = ? and status in ('R','B')");
        logger.info("inside forwardBatch:-update all_upload_summary set status = 'F' where maker_id = '" + userId + "' and batch_id ='" + batch_id + "' and status in ('R','B')");
        ps.setString(1, userId);
        ps.setString(2, batch_id);
        int i = ps.executeUpdate();
        ps.close();
        this.con.close();
        logger.info("i value for forward batch ===" + i);
        return i;
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  public ArrayList<GenericUploadVO> searchUploads(String user_id, XMLBean xmlbean)
  {
    logger.info("user_id ===" + user_id);
    logger.info("xmlbean ====" + xmlbean.getName());
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("select batch_id,upload_type,upload_date,maker_id,maker_date,no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = ? and status = 'f' and maker_id != ? ");
      logger.info("select batch_id,upload_type,upload_date,maker_id,maker_date,no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = " + xmlbean.getName() + " and status = 'f' and maker_id != " + user_id);
      ps.setString(1, xmlbean.getName());
      ps.setString(2, user_id);
      ResultSet result = ps.executeQuery();
      ArrayList list = new ArrayList();
      while (result.next())
      {
        GenericUploadVO vo = new GenericUploadVO();
        vo.setBatch_id(result.getString(1));
        vo.setUpload_type(result.getString(2));
        vo.setUpload_date(result.getString(3));
        vo.setMaker_id(result.getString(4));
        vo.setMaker_date(result.getString(5));
        vo.setNo_of_records(result.getString(6));
        vo.setSummary1(result.getString(7));
        vo.setSummary2(result.getString(8));
        vo.setFile_name(result.getString(9));
        list.add(vo);
      }
      ps.close();
      result.close();
      this.con.close();
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public ArrayList<GenericUploadVO> searchUploads(GenericUploadForm form, XMLBean bean)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      String query = "select batch_id,upload_type,upload_date,maker_id,maker_date,no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = '" + bean.getName() + "' and status = 'f' and maker_id != '" + form.getMakerId() + "'";
      if ((form.getBatch_id() != null) && (form.getBatch_id() != ""))
      {
        query = query + " and batch_id = '" + form.getBatch_id() + "'";
      }

      if ((form.getMaker_id() != null) && (form.getMaker_id() != ""))
      {
        query = query + " and maker_id = '" + form.getMaker_id() + "'";
      }

      if ((form.getMaker_date() != null) && (form.getMaker_date() != ""))
      {
        String d = form.getMaker_date();
        query = query + " and maker_date = str_to_date('" + d + "','%d-%m-%Y')";
      }

      if ((form.getUpload_date() != null) && (form.getUpload_date() != ""))
      {
        String d = form.getUpload_date();
        query = query + " and upload_date = str_to_date('" + d + "','%d-%m-%Y')";
      }

      if ((form.getFile_name() != null) && (form.getFile_name() != ""))
      {
        query = query + " and file_name = '" + form.getFile_name() + "'";
      }
      logger.info("SEARCH QUERY----------" + query);
      Statement statement = this.con.createStatement();
      ResultSet result = statement.executeQuery(query);
      ArrayList list = new ArrayList();
      while (result.next())
      {
        GenericUploadVO vo = new GenericUploadVO();
        vo.setBatch_id(result.getString(1));
        vo.setUpload_type(result.getString(2));
        vo.setUpload_date(result.getString(3));
        vo.setMaker_id(result.getString(4));
        vo.setMaker_date(result.getString(5));
        vo.setNo_of_records(result.getString(6));
        vo.setSummary1(result.getString(7));
        vo.setSummary2(result.getString(8));
        vo.setFile_name(result.getString(9));
        list.add(vo);
      }
      statement.close();
      result.close();
      this.con.close();
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }

  public int saveUploadSummary(String upload_Type, String upload_date, String maker_id, String file_name)
  {
    int output = 0;
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("insert into all_upload_summary (upload_type,upload_date,maker_id,maker_date,status,file_name)values(?,str_to_date(?,'%d-%m-%Y'),?,curdate(),?,?)");

      ps.setString(1, upload_Type);
      ps.setString(2, upload_date);
      ps.setString(3, maker_id);

      ps.setString(4, "P");
      ps.setString(5, file_name);

      int rows = ps.executeUpdate();

      if (rows > 0)
      {
        PreparedStatement ps1 = this.con.prepareStatement("select max(batch_id) from all_upload_summary");
        ResultSet result = ps1.executeQuery();
        while (result.next())
        {
          output = result.getInt(1);
        }
        logger.info("rows ===============" + rows + "  output=================" + output);
        ps1.close();
        result.close();
      }
      ps.close();
      this.con.close();

      return output;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return output;
  }

  public int updateUploadSummary(GenericUploadParametersDTO dto) throws Exception
  {
    int output = 0;
    this.con = ConnectionUploadDAO.getConnection();
    this.con.setAutoCommit(false);
    InitialContext ctx = new InitialContext();
    UserTransaction tx = (UserTransaction)ctx.lookup("java:comp/UserTransaction");
    try
    {
      tx.begin();
      String no_of_records = "";

      PreparedStatement ps = this.con.prepareStatement("select count(1) from " + dto.getTame_table_name() + " where batch_id = ?");

      ps.setString(1, dto.getBatch_id());
      ResultSet result = ps.executeQuery();
      while (result.next())
      {
        no_of_records = result.getString(1);
      }
      logger.info("no_of_records ------------------>" + no_of_records);

      PreparedStatement ps1 = this.con.prepareStatement("update all_upload_summary set status = ?,no_of_records = ? where batch_id = ?");
      ps1.setString(1, "F");
      ps1.setString(2, no_of_records);
      ps1.setString(3, dto.getBatch_id());
      output = ps1.executeUpdate();

      ps.close();
      result.close();
      ps1.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (this.con != null)
        {
          this.con.close();
        }
        this.con = ConnectionUploadDAO.getConnection();
        this.con.setAutoCommit(false);
        if (this.con != null)
        {
          String no_of_records = "";

          PreparedStatement ps = this.con.prepareStatement("select count(1) from " + dto.getTame_table_name() + " where batch_id = ?");

          ps.setString(1, dto.getBatch_id());
          ResultSet result = ps.executeQuery();
          while (result.next())
          {
            no_of_records = result.getString(1);
          }
          logger.info("no_of_records ------------------>" + no_of_records);

          PreparedStatement ps1 = this.con.prepareStatement("update all_upload_summary set status = ?,no_of_records = ? where batch_id = ?");
          ps1.setString(1, "F");
          ps1.setString(2, no_of_records);
          ps1.setString(3, dto.getBatch_id());
          output = ps1.executeUpdate();

          ps.close();
          result.close();
          ps1.close();
        }

      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

    }
    finally
    {
      tx.commit();
      this.con.commit();
      this.con.close();
    }

    return output;
  }

  public String updateBatchStatus(String batch_id, String userId)
  {
    try
    {
      logger.info("Author Initiated for batchID =" + batch_id);
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'W' where batch_id = ?");
      logger.info("query == update all_upload_summary set author_id = " + userId + ",author_date = curdate(),status = 'W' where batch_id = " + batch_id);
      ps.setString(1, userId);
      ps.setString(2, batch_id);

      int i = ps.executeUpdate();
      ps.close();
      this.con.close();
      return String.valueOf(i);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "";
  }

  public int sendBack(String batch_id, String userId, String comment)
  {
    try
    {
      logger.info("auther comment while sending back ===" + comment);
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'B',summary1 = ? where batch_id = ?");
      logger.info("update all_upload_summary set author_id =  " + userId + ",author_date = curdate(),status = 'B',summary1 = " + comment + " where batch_id = " + batch_id);
      ps.setString(1, userId);
      ps.setString(2, comment);
      ps.setString(3, batch_id);

      int i = ps.executeUpdate();
      ps.close();
      this.con.close();
      return i;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  public int reject(String batch_id, String userId, String comment)
  {
    try {
      logger.info("auther comment while rejecting ===" + comment);
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set author_id =  ?,author_date = curdate(),status = 'R',summary1 = ? where batch_id = ?");
      logger.info("update all_upload_summary set author_id =  " + userId + ",author_date = curdate(),status = 'B',summary1 = " + comment + " where batch_id = " + batch_id);
      ps.setString(1, userId);
      ps.setString(2, comment);
      ps.setString(3, batch_id);

      int i = ps.executeUpdate();
      ps.close();
      this.con.close();
      return i;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  public static BatchIDAndUserIDBean getBatchIDAndUserIDBean()
  {
    return (BatchIDAndUserIDBean)local.get();
  }

  public boolean copyExcelDataToTempTable(GenericUploadParametersDTO dto)
  {
    boolean status = false;
    try
    {
      logger.info("SMOOKS STARTED.....");
      logger.info("batchid ==" + dto.getBatch_id());
      logger.info("userId = " + dto.getUser_id());
      logger.info("filepath = " + dto.getExcelfilepath());

      BatchIDAndUserIDBean bean = new BatchIDAndUserIDBean();
      bean.setBatchid(dto.getBatch_id());
      bean.setUserid(dto.getUser_id());
      local.set(bean);

      Smooks smooks = new Smooks(dto.getSmooksfilepath());
      ExecutionContext executionContext = smooks.createExecutionContext();
      byte[] messageIn = StreamUtils.readStream(new FileInputStream(dto.getExcelfilepath()));
      smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), new Result[0]);
      logger.info("SMOOKS ENDED.....");
      status = true;
      logger.info("EXCEL DATA COPIED SECESSFULLY TO TEMP TABLE..........");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      status = false;
    }

    return status;
  }

  public boolean uploadExcel(HttpServletRequest request, HttpServletResponse response, String strFileName, ExcelSheetUploadForm excelForm)
  {
    logger.info("SessionId:=========================" + excelForm.getSessionId());
    logger.info("Inside  uploadExcel() method of ExcelSheetUploadDAO ");

    String session = CommonFunction.checkNull(excelForm.getSessionId());

    logger.info("============= session_id====================== " + session);

    StringBuffer debit = null;
    StringBuffer credit = null;
    StringBuffer dateCheck = null;
    Connection con = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;
    Statement stmt = null;

    StringBuffer makerID = null;

    ArrayList alDeleteQuery = new ArrayList(1);
    ArrayList dataList = new ArrayList();
    ArrayList arr = new ArrayList();
    ArrayList subList = new ArrayList();
    String[] sheetName = null;
    StringBuffer sNo = null;
    StringBuffer bID = null;
    StringBuffer mID = null;
    StringBuffer vDate = null;
    StringBuffer vType = null;
    StringBuffer pID = null;
    StringBuffer dID = null;
    StringBuffer chequeInNo = null;
    StringBuffer refereneNo = null;
    StringBuffer narration = null;
    StringBuffer sID = null;
    StringBuffer offBalance = null;
    StringBuffer mVoucher = null;
    StringBuffer reciptNO = null;
    StringBuffer ledgerId = null;
    StringBuffer subLedgerId = null;
    StringBuffer ledgerRemarks = null;
    StringBuffer seqNo = null;
    File objFile1 = null;

    boolean status = false;
    try
    {
      makerID = new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));

      if (!strFileName.equals(""))
      {
        logger.info("In uploadExcel(): strFileName ==>>" + strFileName);
        String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOUNCE_UPLOAD_PATH'";
        String strPath = ConnectionUploadDAO.singleReturn(query);
        strPath = strPath + "/" + strFileName;
        objFile1 = new File(strPath);
        try
        {
          String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '" + makerID + "' ";
          logger.info("In  uploadExcel(): delete query :-----" + strDeleteQuery);
          alDeleteQuery.add(strDeleteQuery);
          boolean status1 = ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
          logger.info("In readExcel for ExcelSheetUploadDao(): status1 ==>>" + status1);
          if (status1)
            logger.info("Row is deleted....");
          else {
            logger.info("Row is not deleted......");
          }

          dataList = new ArrayList();

          Workbook workbook = Workbook.getWorkbook(objFile1);
          sheetName = workbook.getSheetNames();

          for (int p = 0; p < sheetName.length; p++)
          {
            Sheet sheet = workbook.getSheet(p);
            if (sheet.getRows() <= 1000) {
              for (int i = 0; i < sheet.getRows(); i++)
              {
                Cell[] cell = sheet.getRow(i);
                arr = new ArrayList();
                for (int j = 0; j < cell.length; j++)
                {
                  Cell xlsCell = sheet.getCell(j, i);
                  arr.add(xlsCell.getContents().toString());
                }
                dataList.add(arr);
              }
            }
            else {
              dateCheck = new StringBuffer("Only 1000 of rows can upload at a time ");
            }
          }
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        con = ConnectionUploadDAO.getConnection();
        try
        {
          String query1 = "insert into temp_gl_voucher_upload  (Serial_no,VOUCHER_TYPE,VOUCHER_DATE,NARRATION,MODULE_ID,REFERENCE_NO,cheque_invoice_no,department, PRODUCT_ID,Manual_Voucher,STAGE_ID,off_balance,DR_AMOUNT,CR_AMOUNT,LEDGER_ID,SUBLEDGER_ID, SEQUENCE_NO,user_id,session_id,BRANCH_ID,RECIPT_NO,LEDGER_REMARKS) values (?,?,str_to_date(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

          ptmt = con.prepareStatement(query1);

          for (int i = 1; i < dataList.size(); i++)
          {
            subList = (ArrayList)dataList.get(i);
            if (subList.size() == 20)
            {
              sNo = new StringBuffer(CommonFunction.checkNull(subList.get(0).toString()).trim());
              bID = new StringBuffer(CommonFunction.checkNull(subList.get(1).toString()).trim());
              mID = new StringBuffer(CommonFunction.checkNull(subList.get(2).toString()).trim());
              vDate = new StringBuffer(CommonFunction.checkNull(subList.get(3).toString()).trim());
              vType = new StringBuffer(CommonFunction.checkNull(subList.get(4).toString()).trim());
              pID = new StringBuffer(CommonFunction.checkNull(subList.get(5).toString()).trim());
              dID = new StringBuffer(CommonFunction.checkNull(subList.get(6).toString()).trim());

              if (CommonFunction.checkNull(subList.get(6).toString()).equalsIgnoreCase(""))
                dID = new StringBuffer("0");
              else {
                dID = new StringBuffer(CommonFunction.checkNull(subList.get(6).toString()).trim());
              }

              chequeInNo = new StringBuffer(CommonFunction.checkNull(subList.get(7).toString()).trim());
              refereneNo = new StringBuffer(CommonFunction.checkNull(subList.get(8).toString()).trim());
              narration = new StringBuffer(CommonFunction.checkNull(subList.get(9).toString()).trim());
              sID = new StringBuffer(CommonFunction.checkNull(subList.get(10).toString()).trim());
              offBalance = new StringBuffer(CommonFunction.checkNull(subList.get(11).toString()).trim());
              mVoucher = new StringBuffer(CommonFunction.checkNull(subList.get(12).toString()).trim());

              reciptNO = new StringBuffer(CommonFunction.checkNull(subList.get(13).toString()).trim());
              ledgerId = new StringBuffer(CommonFunction.checkNull(subList.get(14).toString()).trim());
              subLedgerId = new StringBuffer(CommonFunction.checkNull(subList.get(15).toString()).trim());
              ledgerRemarks = new StringBuffer(CommonFunction.checkNull(subList.get(16).toString()).trim());
              debit = new StringBuffer(CommonFunction.checkNull(subList.get(17).toString()).trim());
              credit = new StringBuffer(CommonFunction.checkNull(subList.get(18).toString()).trim());

              seqNo = new StringBuffer(CommonFunction.checkNull(subList.get(19).toString()).trim());

              if ((sNo.toString().equalsIgnoreCase("")) || (bID.toString().equalsIgnoreCase("")) || (mID.toString().equalsIgnoreCase("")) || (vDate.toString().equalsIgnoreCase("")) || (vType.toString().equalsIgnoreCase("")) || (narration.toString().equalsIgnoreCase("")) || (offBalance.toString().equalsIgnoreCase("")) || (mVoucher.toString().equalsIgnoreCase("")) || (ledgerId.toString().equalsIgnoreCase("")) || (seqNo.toString().equalsIgnoreCase("")) || (debit.toString().equalsIgnoreCase("")) || (credit.toString().equalsIgnoreCase("")) || ((!mVoucher.toString().equalsIgnoreCase("")) && (!mVoucher.toString().equalsIgnoreCase("Y"))))
              {
                if (sNo.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill S.N");
                else if (bID.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill Branch_id");
                else if (mID.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill MODULE_ID");
                else if (vDate.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill VOUCHER_DATE");
                else if (vType.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill VOUCHER_TYPE");
                else if (narration.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill NARRATION");
                else if (offBalance.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill off_balance");
                else if (mVoucher.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill Manual_Voucher");
                else if (ledgerId.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill LEDGER_ID");
                else if (seqNo.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill SEQUENCE_NO");
                else if (debit.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill all DR_AMOUNT");
                else if (credit.toString().equalsIgnoreCase(""))
                  dateCheck = new StringBuffer("Please fill all CR_AMOUNT");
                else if ((!mVoucher.toString().equalsIgnoreCase("")) && (!mVoucher.toString().equalsIgnoreCase("Y"))) {
                  dateCheck = new StringBuffer("Please provide in Manual_Voucher only 'Y' flag");
                }
                status = false;
                break;
              }

              if (vDate.toString().trim().length() != this.dateFormatCheck.toPattern().length()) {
                dateCheck = new StringBuffer("In-valid Date,Provide date in dd/mm/yyyy Format.");
                status = false;
                break;
              }

              ptmt.setString(1, sNo.toString().trim());
              ptmt.setString(2, vType.toString().trim());
              ptmt.setString(3, vDate.toString().trim());
              ptmt.setString(4, narration.toString().trim());
              ptmt.setString(5, mID.toString().trim());
              ptmt.setString(6, refereneNo.toString().trim());
              ptmt.setString(7, chequeInNo.toString().trim());
              ptmt.setString(8, dID.toString().trim());
              ptmt.setString(9, pID.toString().trim());
              ptmt.setString(10, mVoucher.toString().trim());
              ptmt.setString(11, sID.toString().trim());
              ptmt.setString(12, offBalance.toString().trim());
              ptmt.setString(13, debit.toString().trim());
              ptmt.setString(14, credit.toString().trim());
              ptmt.setString(15, ledgerId.toString().trim());
              ptmt.setString(16, subLedgerId.toString().trim());
              ptmt.setString(17, seqNo.toString().trim());
              ptmt.setString(18, makerID.toString().trim());
              ptmt.setString(19, session.toString().trim());
              ptmt.setString(20, bID.toString().trim());
              ptmt.setString(21, reciptNO.toString().trim());
              ptmt.setString(22, ledgerRemarks.toString().trim());
              ptmt.addBatch();
            }

            if (sNo != null) {
              sNo.setLength(0);
              bID.setLength(0);
              mID.setLength(0);
              vDate.setLength(0);
              vType.setLength(0);
              pID.setLength(0);
              dID.setLength(0);
              chequeInNo.setLength(0);
              refereneNo.setLength(0);
              narration.setLength(0);
              sID.setLength(0);
              offBalance.setLength(0);
              mVoucher.setLength(0);
              ledgerId.setLength(0);
              subLedgerId.setLength(0);
              seqNo.setLength(0);
              debit.setLength(0);
              credit.setLength(0);
              reciptNO.setLength(0);
              ledgerRemarks.setLength(0);
              query = null;
            }
          }

          if ((dateCheck == null) && (CommonFunction.checkNull(dateCheck).equalsIgnoreCase("")))
          {
            int[] rows = ptmt.executeBatch();
            if (rows.length > 0) {
              status = true;
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
          dateCheck = new StringBuffer("Some problem in excel sheet at" + e.getMessage());
          logger.error(e);
        }
        finally {
          query = null;
          strPath = null;
          ConnectionUploadDAO.closeConnection(con, ptmt);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);
    }
    finally
    {
      if ((dateCheck != null) && (!CommonFunction.checkNull(dateCheck).equalsIgnoreCase(""))) {
        request.setAttribute("dateCheck", CommonFunction.checkNull(dateCheck.toString()));
      }

      makerID.setLength(0);
      debit.setLength(0);
      credit.setLength(0);
      ptmt = null;
      alDeleteQuery.clear();
      alDeleteQuery = null;
      dataList.clear();
      dataList = null;
      arr.clear();
      arr = null;
      subList.clear();
      subList = null;
      credit = null;
      sheetName = null;
      sNo = null;
      bID = null;
      mID = null;
      vDate = null;
      vType = null;
      pID = null;
      dID = null;
      chequeInNo = null;
      refereneNo = null;
      narration = null;
      sID = null;
      offBalance = null;
      mVoucher = null;
      ledgerId = null;
      subLedgerId = null;
      seqNo = null;
      debit = null;
      reciptNO.setLength(0);
      ledgerRemarks.setLength(0);
      reciptNO = null;
      ledgerRemarks = null;
      ConnectionUploadDAO.closeConnection(con, stmt, rs);
    }

    return status;
  }

  public ArrayList<GenericUploadVO> searchData(String user_id, XMLBean xmlbean)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("select batch_id,status,upload_type,DATE_FORMAT(upload_date,'" + this.dateFormat + "'),maker_id,DATE_FORMAT(maker_date,'" + this.dateFormat + "'),author_id,DATE_FORMAT(author_date,'" + this.dateFormat + "'),no_of_records,summary1,summary2,file_name from all_upload_summary where upload_type = ? and status in('P','B','R','W','EK','EP') and maker_id = ?");
      ps.setString(1, xmlbean.getName());
      ps.setString(2, user_id);

      ResultSet result = ps.executeQuery();
      ArrayList list = new ArrayList();
      while (result.next())
      {
        GenericUploadVO vo = new GenericUploadVO();
        vo.setBatch_id(result.getString(1));
        vo.setStatus(result.getString(2));
        vo.setUpload_type(result.getString(3));
        vo.setUpload_date(result.getString(4));
        vo.setMaker_id(result.getString(5));
        vo.setMaker_date(result.getString(6));
        vo.setAuthor_id(result.getString(7));
        vo.setAuthor_date(result.getString(8));
        vo.setNo_of_records(result.getString(9));
        vo.setSummary1(result.getString(10));
        vo.setSummary2(result.getString(11));
        vo.setFile_name(result.getString(12));
        list.add(vo);
      }

      ps.close();
      result.close();
      this.con.close();
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public String changeStatus(GenericUploadParametersDTO dto)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'A' where batch_id = ?");
      logger.info("update all_upload_summary set status = 'A' where batch_id = " + dto.getBatch_id());
      ps.setString(1, dto.getBatch_id());
      int i = ps.executeUpdate();
      if (i > 0)
      {
        PreparedStatement ps1 = this.con.prepareStatement("delete from " + dto.getTame_table_name() + " where batch_id = ?");
        logger.info("delete from " + dto.getTame_table_name() + " where batch_id =" + dto.getBatch_id());
        ps1.setString(1, dto.getBatch_id());
        int j = ps1.executeUpdate();
        ps1.close();
      }

      ps.close();
      this.con.close();

      return String.valueOf(i);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "";
  }

  public boolean changeStatusP_To_EK(GenericUploadParametersDTO dto) throws Exception
  {
    this.con = ConnectionUploadDAO.getConnection();
    this.con.setAutoCommit(false);
    InitialContext ctx = new InitialContext();
    UserTransaction tx = (UserTransaction)ctx.lookup("java:comp/UserTransaction");
    try
    {
      tx.begin();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'EK' where batch_id = ?");
      logger.info("update all_upload_summary set status = 'EK' where batch_id = " + dto.getBatch_id());
      ps.setString(1, dto.getBatch_id());
      int i = ps.executeUpdate();

      ps.close();

      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (this.con != null)
        {
          this.con.close();
        }
        this.con = ConnectionUploadDAO.getConnection();
        this.con.setAutoCommit(false);
        if (this.con != null)
        {
          PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'EK' where batch_id = ?");
          logger.info("update all_upload_summary set status = 'EK' where batch_id = " + dto.getBatch_id());
          ps.setString(1, dto.getBatch_id());
          int i = ps.executeUpdate();

          ps.close();

          return true;
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    finally
    {
      tx.commit();
      this.con.commit();
      this.con.close();
    }

    return false;
  }

  public boolean changeStatusP_To_EP(GenericUploadParametersDTO dto) throws Exception
  {
    this.con = ConnectionUploadDAO.getConnection();
    this.con.setAutoCommit(false);
    InitialContext ctx = new InitialContext();
    UserTransaction tx = (UserTransaction)ctx.lookup("java:comp/UserTransaction");
    try
    {
      tx.begin();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'EP' where batch_id = ?");
      logger.info("update all_upload_summary set status = 'EP' where batch_id = " + dto.getBatch_id());
      ps.setString(1, dto.getBatch_id());
      int i = ps.executeUpdate();
      ps.close();

      if (i > 0)
        return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (this.con != null)
        {
          this.con.close();
        }
        this.con = ConnectionUploadDAO.getConnection();
        this.con.setAutoCommit(false);
        if (this.con != null)
        {
          PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'EP' where batch_id = ?");
          logger.info("update all_upload_summary set status = 'EP' where batch_id = " + dto.getBatch_id());
          ps.setString(1, dto.getBatch_id());
          int i = ps.executeUpdate();
          ps.close();

          if (i > 0)
            return true;
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    finally
    {
      tx.commit();
      this.con.commit();
      this.con.close();
    }
    return false;
  }

  public boolean changeStatusF_To_EP(VoucherUploadParametersDTO dto)
  {
    try
    {
      this.con = ConnectionUploadDAO.getConnection();
      PreparedStatement ps = this.con.prepareStatement("update all_upload_summary set status = 'EP' where batch_id = ?");
      logger.info("update all_upload_summary set status = 'EP' where batch_id = " + dto.getBatch_id());
      ps.setString(1, dto.getBatch_id());
      int i = ps.executeUpdate();
      ps.close();
      this.con.close();
      if (i > 0)
        return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return false;
  }

  public String validateVouchersInBatch(GenericUploadParametersDTO dto)
  {
    String filename = dto.getFilename();

    String flag = null;
    this.con = ConnectionUploadDAO.getConnection();
    this.rs = null;
    String msg = null;
    try
    {
      this.csmt = this.con.prepareCall("{CALL GENERIC_UPLOAD_VALIDATION_AUTHOR(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?)}");
      this.csmt.setString(1, CommonFunction.checkNull(dto.getCompany_id()));
      this.csmt.setString(2, CommonFunction.checkNull(dto.getUser_id()));
      this.csmt.setString(3, CommonFunction.checkNull(dto.getBusiness_date()));
      this.csmt.setString(4, CommonFunction.checkNull(dto.getBranch_id()));
      this.csmt.setString(5, CommonFunction.checkNull(""));
      this.csmt.setString(6, filename);
      this.csmt.setString(7, dto.getBatch_id());
      this.csmt.setString(8, dto.getFunctionid());
      this.csmt.registerOutParameter(9, 12);
      this.csmt.registerOutParameter(10, 12);

      this.rs = this.csmt.executeQuery();

      flag = this.csmt.getString(9);
      msg = this.csmt.getString(10);

      logger.info("GENERIC_UPLOAD_VALIDATION_AUTHOR flag:----" + flag);
      logger.info("GENERIC_UPLOAD_VALIDATION_AUTHOR msg:----" + msg);

      logger.info("validation on author side GL_EXCELSHEET_VOUCHER_VALIDATION_AUTHOR");
    }
    catch (SQLException exp)
    {
      exp.printStackTrace();
      throw new OmniFinMarkForRollbackException(exp);
    } catch (Exception e) {
      e.printStackTrace();
      throw new OmniFinMarkForRollbackException(e);
    } finally {
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
    }

    return flag;
  }

  public String authorAndTransfer(TempGenericProxyDTO dto)
  {
    logger.info("1 Company_id= " + dto.getCompany_id());
    logger.info("2 getUser_id= " + dto.getUser_id());
    logger.info("3 Business_date= " + dto.getBusiness_date());
    logger.info("4 Branch_id()= " + dto.getBranch_id());
    logger.info("5 blank ");
    logger.info("6 Batch_id= " + dto.getBatch_id());
    logger.info("7 SerialNo()= " + dto.getSerialNo());
    logger.info("7 Temp table name==" + dto.getTame_table_name());

    String flag = null;
    this.con = ConnectionUploadDAO.getConnection();
    try
    {
      this.csmt = this.con.prepareCall("{CALL GENERIC_UPLOAD_AUTHOR(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?,?)}");

      this.csmt.setString(1, CommonFunction.checkNull(dto.getCompany_id()));
      this.csmt.setString(2, CommonFunction.checkNull(dto.getUser_id()));

      this.csmt.setString(3, CommonFunction.checkNull(dto.getBusiness_date()));
      this.csmt.setString(4, CommonFunction.checkNull(dto.getBranch_id()));

      this.csmt.setString(5, CommonFunction.checkNull(""));
      this.csmt.setString(6, dto.getFilename());

      this.csmt.setString(7, dto.getBatch_id());
      this.csmt.setString(8, dto.getFunctionid());

      this.csmt.setString(9, dto.getSerialNo());

      this.csmt.registerOutParameter(10, 12);
      this.csmt.registerOutParameter(11, 12);

      this.rs = this.csmt.executeQuery();

      flag = this.csmt.getString(10);
      String msg = this.csmt.getString(11);

      logger.info("GENERIC_UPLOAD_AUTHOR flag:----" + flag);
      logger.info("GENERIC_UPLOAD_AUTHOR msg:----" + msg);

      logger.info("inside upload in author side GENERIC_UPLOAD_AUTHOR");
    }
    catch (SQLException exp)
    {
      exp.printStackTrace();
      throw new OmniFinMarkForRollbackException(exp);
    } catch (Exception e) {
      e.printStackTrace();
      throw new OmniFinMarkForRollbackException(e);
    } finally {
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
    }

    return flag;
  }

  public ArrayList<String> getSerialNoFromTemp(GenericUploadParametersDTO dto)
  {
    ArrayList list = new ArrayList();

    this.con = ConnectionUploadDAO.getConnection();
    try
    {
      PreparedStatement ps = this.con.prepareStatement("select distinct serial_no from " + dto.getTame_table_name() + " where batch_id = ?");
      ps.setString(1, dto.getBatch_id());
      ResultSet result = ps.executeQuery();

      while (result.next())
      {
        list.add(result.getString(1));
      }
      result.close();
      this.con.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }

  public String saveVoucherForMDB(GenericUploadParametersDTO dto)
    throws Exception
  {
    String filename = dto.getFilename();

    String flag = null;
    this.con = ConnectionUploadDAO.getConnection();
    this.con.setAutoCommit(false);
    InitialContext ctx = new InitialContext();
    UserTransaction tx = (UserTransaction)ctx.lookup("java:comp/UserTransaction");
    this.rs = null;
    String msg = null;
    try
    {
      tx.begin();
      this.csmt = this.con.prepareCall("{CALL GENERIC_UPLOAD_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?)}");
      this.csmt.setString(1, CommonFunction.checkNull(dto.getCompany_id()));
      this.csmt.setString(2, CommonFunction.checkNull(dto.getUser_id()));
      this.csmt.setString(3, CommonFunction.checkNull(dto.getBusiness_date()));
      this.csmt.setString(4, CommonFunction.checkNull(dto.getBranch_id()));
      this.csmt.setString(5, CommonFunction.checkNull(""));
      this.csmt.setString(6, filename);
      this.csmt.setString(7, dto.getBatch_id());
      this.csmt.setString(8, dto.getFunctionid());
      this.csmt.registerOutParameter(9, 12);
      this.csmt.registerOutParameter(10, 12);

      this.rs = this.csmt.executeQuery();

      flag = this.csmt.getString(9);
      msg = this.csmt.getString(10);

      logger.info("GENERIC_UPLOAD_VALIDATION_MAKER FLAG:----" + flag);
      logger.info("GENERIC_UPLOAD_VALIDATION_MAKER MSG:----" + msg);
    }
    catch (SQLException exp)
    {
      exp.printStackTrace();
      try
      {
        if (this.con != null)
        {
          this.con.close();
        }
        this.con = ConnectionUploadDAO.getConnection();
        this.con.setAutoCommit(false);
        if (this.con != null)
        {
          this.csmt = this.con.prepareCall("{CALL GENERIC_UPLOAD_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?)}");
          this.csmt.setString(1, CommonFunction.checkNull(dto.getCompany_id()));
          this.csmt.setString(2, CommonFunction.checkNull(dto.getUser_id()));
          this.csmt.setString(3, CommonFunction.checkNull(dto.getBusiness_date()));
          this.csmt.setString(4, CommonFunction.checkNull(dto.getBranch_id()));
          this.csmt.setString(5, CommonFunction.checkNull(""));
          this.csmt.setString(6, filename);
          this.csmt.setString(7, dto.getBatch_id());
          this.csmt.setString(8, dto.getFunctionid());
          this.csmt.registerOutParameter(9, 12);
          this.csmt.registerOutParameter(10, 12);

          this.rs = this.csmt.executeQuery();

          flag = this.csmt.getString(9);
          msg = this.csmt.getString(10);

          logger.info("GENERIC_UPLOAD_VALIDATION_MAKER FLAG:----" + flag);
          logger.info("GENERIC_UPLOAD_VALIDATION_MAKER MSG:----" + msg);
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try
      {
        if (this.con != null)
        {
          this.con.close();
        }
        this.con = ConnectionUploadDAO.getConnection();
        this.con.setAutoCommit(false);
        if (this.con != null)
        {
          this.csmt = this.con.prepareCall("{CALL GENERIC_UPLOAD_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?)}");
          this.csmt.setString(1, CommonFunction.checkNull(dto.getCompany_id()));
          this.csmt.setString(2, CommonFunction.checkNull(dto.getUser_id()));
          this.csmt.setString(3, CommonFunction.checkNull(dto.getBusiness_date()));
          this.csmt.setString(4, CommonFunction.checkNull(dto.getBranch_id()));
          this.csmt.setString(5, CommonFunction.checkNull(""));
          this.csmt.setString(6, filename);
          this.csmt.setString(7, dto.getBatch_id());
          this.csmt.setString(8, dto.getFunctionid());
          this.csmt.registerOutParameter(9, 12);
          this.csmt.registerOutParameter(10, 12);

          this.rs = this.csmt.executeQuery();

          flag = this.csmt.getString(9);
          msg = this.csmt.getString(10);

          logger.info("GENERIC_UPLOAD_VALIDATION_MAKER FLAG:----" + flag);
          logger.info("GENERIC_UPLOAD_VALIDATION_MAKER MSG:----" + msg);
        }
      }
      catch (Exception exp)
      {
        exp.printStackTrace();
      }
    }
    finally {
      tx.commit();
      this.con.commit();
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
    }

    return flag;
  }

  public String saveVoucher(HttpServletRequest request, ExcelSheetUploadForm excelForm, String batch_id)
  {
    String filename = excelForm.getDocFile().getFileName();

    String flag = null;
    this.con = ConnectionUploadDAO.getConnection();
    this.rs = null;
    String s2 = null;
    String voucherNo = null;
    try
    {
      this.con.setAutoCommit(false);
      this.csmt = this.con.prepareCall("{CALL GL_EXCELSHEET_VOUCHER_VALIDATION_MAKER(?,?,STR_TO_DATE(?,'" + this.dateFormat + "'),?,?,?,?,?,?,?)}");
      this.csmt.setString(1, CommonFunction.checkNull(Integer.valueOf(excelForm.getCompanyId())));
      this.csmt.setString(2, CommonFunction.checkNull(excelForm.getMakerId()));
      this.csmt.setString(3, CommonFunction.checkNull(excelForm.getBusinessDate()));
      this.csmt.setString(4, CommonFunction.checkNull(excelForm.getBranchId()));
      this.csmt.setString(5, CommonFunction.checkNull(excelForm.getSessionId()));
      this.csmt.setString(6, filename);
      this.csmt.setString(7, batch_id);
      this.csmt.registerOutParameter(8, 12);
      this.csmt.registerOutParameter(9, 12);
      this.csmt.registerOutParameter(10, 12);

      this.rs = this.csmt.executeQuery();

      flag = this.csmt.getString(8);
      s2 = this.csmt.getString(9);
      voucherNo = this.csmt.getString(10);
      logger.info("flag:----" + flag);
      logger.info("s2:----" + s2);
      logger.info("voucherNo:----" + voucherNo);
      request.setAttribute("errorMsg", s2);
      this.con.commit();
    }
    catch (SQLException exp) {
      exp.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
      voucherNo = null;
      s2 = null;
    }
    return flag;
  }

  public ArrayList reportAdHoc(GenericUploadForm excelForm, XMLBean bean)
  {
    ArrayList resultList = new ArrayList();

    String query = bean.getTemp_table_dump_query() + "  '" + excelForm.getBatch_id() + "'";

    logger.info("To Open Output in reportAdHoc() Query is ==== " + query);
    try
    {
      resultList = ConnectionUploadDAO.sqlColumnWithResult(query);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      query = null;
    }
    logger.info("size" + resultList.size());
    return resultList;
  }

  public ArrayList downloadDump(String batch, XMLBean bean)
  {
    logger.info("inside downloadDump(String batch)");
    ArrayList resultList = new ArrayList();
    String query = bean.getMain_table_dump_query() + "  '" + batch + "' ";

    logger.info("dump query == " + query);
    try
    {
      resultList = ConnectionUploadDAO.sqlColumnWithResult(query);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    logger.info("size" + resultList.size());
    return resultList;
  }

  public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile)
  {
    boolean status = false;
    String fileName = "";
    String filePath = "";
    String message = "";
    String filePathWithName = "";
    String ktrpath = "";
    String fileNameChange = "";
    HttpSession session = request.getSession(false);
    UserObject sessUser = (UserObject)session.getAttribute("userobject");
    String user_Id = "";
    try {
      if (sessUser != null) {
        user_Id = sessUser.getUserId();
      }
      String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
      String ktrquery = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
      String rpt = ConnectionUploadDAO.singleReturn(query);
      ktrpath = ConnectionUploadDAO.singleReturn(ktrquery);
      ktrpath = ktrpath.concat("\\voucher-excel-uploading.ktr");
      query = null;
      logger.info("Directory location is --->" + rpt);
      File directory = new File(rpt + "/");
      boolean isDirectory = directory.isDirectory();
      logger.info("Directory--->" + isDirectory);

      Date dNow = new Date();
      fileNameChange = this.fileNameFormat.format(dNow);
      if (isDirectory) {
        fileName = docFile.getFileName();
        logger.info("fileName is --->" + fileName);
        fileName = user_Id.concat("_" + fileNameChange.concat(fileName));
        logger.info("changed fileName is --->" + fileName);
        filePath = directory.getAbsolutePath();
        filePathWithName = filePath.concat("\\").concat(fileName);
        if (!fileName.equals("")) {
          logger.info("Server path: filePath:==>>" + filePath);

          File fileToCreate = new File(filePath, fileName);
          int fileSize = docFile.getFileSize();
          logger.info("docUpload :Size of file==>> " + fileSize);

          if (fileSize < 26314400) {
            FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
            fileOutStream.write(docFile.getFileData());
            fileOutStream.flush();
            fileOutStream.close();
            message = "O";

            status = true;
          } else {
            message = "E";
            logger.info("File size exceeds the upper limit of 25 MB.");
            status = false;
          }
        } else {
          message = "S";

          status = false;
        }
      } else {
        message = "E";
        logger.info("File Directory is empty");
        status = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setAttribute("fileName", fileName);
    request.setAttribute("filePath", filePath);
    request.setAttribute("message", message);
    request.setAttribute("filePathWithName", filePathWithName);
    request.setAttribute("ktrpath", ktrpath);
    fileName = null;
    filePath = null;
    message = null;
    filePathWithName = null;
    fileNameChange = null;
    return status;
  }

  public boolean uploadCsv(HttpServletRequest request, HttpServletResponse response, String batch_id, String strFileName, ExcelSheetUploadForm excelForm)
  {
    logger.info("batch_id ===========================================" + batch_id);

    ArrayList alDeleteQuery = new ArrayList(1);
    StringBuffer makerID = null;
    boolean status = false;
    String fileNameWithPath = excelForm.getFilePathWithName();
    try {
      makerID = new StringBuffer(CommonFunction.checkNull(excelForm.getMakerId()));
      String query1 = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
      String rpt1 = ConnectionUploadDAO.singleReturn(query1);
      query1 = null;
      String strDeleteQuery = "DELETE FROM temp_gl_voucher_upload WHERE user_id = '" + makerID + "' and batch_id = '" + batch_id + "'";
      alDeleteQuery.add(strDeleteQuery);
      boolean status1 = ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);

      if (status1)
        logger.info("Row is deleted....");
      else {
        logger.info("Row is not deleted......");
      }

      KettleEnvironment.init(false);
      EnvUtil.environmentInit();
      TransMeta transMeta = new TransMeta(rpt1.concat("\\voucher-excel-uploading.ktr"));
      Trans trans = new Trans(transMeta);

      trans.setParameterValue("filePathWithName", fileNameWithPath);
      trans.setParameterValue("userID", makerID.toString());
      trans.setParameterValue("batch_id", batch_id);
      trans.execute(null);
      trans.waitUntilFinished();

      if (trans.getErrors() > 0) {
        status = false;
        throw new RuntimeException("There were errors during transformation execution." + trans.getErrors());
      }
      status = true;

      transMeta.clearCaches();
      transMeta.clear();
      rpt1 = null;
      trans.clearParameters();
      trans.cleanup();
      strDeleteQuery = null;
    }
    catch (Exception e)
    {
      logger.info("Problem is ---->" + e.getMessage());
    } finally {
      makerID = null;
      ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
    }

    return status;
  }

  public ArrayList downloadtemplate(String batch, XMLBean bean)
  {
    logger.info("inside downloadDump(String batch)");
    ArrayList resultList = new ArrayList();
    String query = " select Serial_No,LOAN_ID,VENDOR_NAME,UMRN_NO,FACILITY,ACH_STATUS,STATUS_DATE,REMARKS from cr_ach_tracking_dtl_temp where batch_id=0";

    logger.info("dump query == " + query);
    try
    {
      resultList = ConnectionUploadDAO.sqlColumnWithResult(query);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    logger.info("size" + resultList.size());
    return resultList;
  }

  public ArrayList downloadFormat(XMLBean xmlbean, String functionId)
  {
    logger.info("inside downloadFormat(String batch)");
    ArrayList resultList = new ArrayList();
    String query = "";

    if (functionId.equalsIgnoreCase("12001152"))
    {
      query = "select 'LOAN_ID','DUE_DATE','PDC_DATE','INSTRUMENT_MODE','INSTRUMENT_NO','INSTALLMENT_NO','PDC_ECS_AMOUNT','BANK','BRANCH','MICR','IFSC','BANK_ACCOUNT','PURPOSE','ECS_TRANSACTION_CODE','CUSTOMER_AC_TYPE','SPONSOR_BANK_BRANCH_CODE','UTILITY_NUMBER','DELETE_REASON' ";
    }
    else
    {
      query = "select 'SR_NO', 'LOAN_ID', 'EFFECTIVE_DATE', 'EFFECTIVE_RATE', 'OLD_EFFECTIVE_RATE', 'REMARKS', 'oLD_TENURE','markup_flag'";
    }

    logger.info("dump query == " + query);
    try
    {
      resultList = ConnectionUploadDAO.sqlSelect(query);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    logger.info("size" + resultList.size());
    return resultList;
  }
  
}