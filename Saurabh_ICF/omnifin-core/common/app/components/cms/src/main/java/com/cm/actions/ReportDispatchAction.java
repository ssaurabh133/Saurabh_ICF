package com.cm.actions;

import com.cm.actionform.ReportsForm;
import com.cm.dao.DownloadDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.ReportsDAO;
import com.cm.vo.ClosureVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import com.communication.engn.servlets.BulkEmail;

public class ReportDispatchAction extends Action
{
  private static final Logger logger = Logger.getLogger(ReportDispatchAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    logger.info("In ReportDispatchAction.........");
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
    String dbType = resource.getString("lbl.dbType");

    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String nhb_date = "";
    String date = "";
    String branchId = "";
    if (userobj != null)
    {
      userId = userobj.getUserId();
      nhb_date = userobj.getBusinessdate();
      date = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
    } else {
      logger.info(" in execute method of ReportsAction1 action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info(new StringBuilder().append("strFlag .............. ").append(strFlag).toString());
    if (!strFlag.equalsIgnoreCase(""))
    {
      if (strFlag.equalsIgnoreCase("sameUserSession"))
      {
        context.removeAttribute("msg");
        context.removeAttribute("msg1");
      }
      else if (strFlag.equalsIgnoreCase("BODCheck"))
      {
        context.setAttribute("msg", "B");
      }
      return mapping.findForward("logout");
    }

    if (isTokenValid(request))
    {
      try
      {
        ReportsForm af = (ReportsForm)form;

        String dateFormat = resource.getString("lbl.dateInDao");
        String reportName = af.getReportName();
        String reporttype = "";
        String bouceFlag = "P";
        if (af.getReportformat() == null)
          reporttype = "P";
        else
          reporttype = af.getReportformat();
        String reportype = "";
        reportype = af.getReportformat();
        String lbxLoanId = af.getLbxLoanId();
        String p_loan_no = af.getSpecificLoanNo();
        String p_deal = af.getSpecificDealNo();
        String loanFrom = af.getFrom();
        String loanTo = af.getTo();
        String p_loan_from = af.getLbx_loan_from_id();
        String p_loan_to = af.getLbx_loan_to_id();
        String p_branch_type = af.getBranch();
        String p_branch_id = af.getLbxBranchId();
        String fromDate = af.getFromDate();
        String toDate = af.getToDate();
        String asOnDate = af.getAsonDate();
        String loanType = af.getLoanno();
        String p_loan_id = af.getLbx_loan_from_id();
        String p_p_date = "";
        String fromDeal = af.getFromDeal();
        String p_deal_from = af.getLbxDealNo();
        String toDeal = af.getToDeal();
        String p_deal_to = af.getLbxDealTo();
        String p_printed_deal = "";
        String p_deal_id = "";
        String installmentDate = af.getInstallmentDate();
        String batchPurpose = af.getBatchPurpose();
        String rdp_loan_id = af.getLbxloannumber();
        String bank_id = af.getLbxBankID();
        String bank_name = af.getBankID();
        String fromInterestDate = af.getIntCerFromDate();
        String toInterestDate = af.getIntCerToDate();
        String p_address1 = "";
        String p_address2 = "";
        String p_msg = "";
        String p_phone = "";
        String p_fax = "";
        String p_email = "";
        String p_email1 = "";
        String group_name = af.getGroupNumber();
        String group_name1 = af.getLbxGroupID();
        String P_GROUP_ID_NEW = af.getLbxGroupID();
        String instrumentType = af.getInstrumentType();
        String presentation_date = af.getPresentationdate();
        String p_presentation_date = "";
        String p_pdc_flag = "";
        String sdfromDate = af.getFromdateforsd();
        String sdToDate = af.getTodateforsd();
        String sdAsonDate = af.getAsOnDateForSD();
        StringBuilder query = new StringBuilder();
        String p_date = "";
        String loan_date = "";
        String p_Approval_date_from = "";
        String p_Approval_date_to = "";
        String p_from_date = "";
        String p_to_date = "";
        String p_from_date1 = af.getDateFrom();
        String p_to_date1 = af.getDateTo();
        String p_from_installment_date = "";
        String p_to_installment_date = "";

        String p_rate_type = "";
        String p_headers = "";
        String p_amt_type = "";
        String p_interval_freq = "";
        String Loan_number = af.getLoanNumber();
        String welcome_loan_id = af.getLbx_loan_Number();
        String batchNo = af.getBatchNo();
        String generatesBatch = af.getBatchGenerates();
        String presentationDate = af.getPresentationDateForBatch();
        String instrumentMode = af.getInstrumentMode();
        String p_instrument_mode = "";

        String p_current_from_date = "";
        String p_current_to_date = "";
        String p_last_from_date = "";
        String p_last_to_date = "";
        String lbxDisbursalID = af.getLbxDisbursalID();
        String sponsorDate = af.getSponsorDate();
        String sponsor = af.getSponsor();
        String startDate = af.getIntCerFromDate();
        String end_Date = af.getIntCerToDate();
        String balConfFromDate = af.getBalConfFromDate();
        String balConfToDate = af.getBalConfToDate();
        String paymentMode = af.getPaymentMode();
        String chargesId = af.getChargesId();
        String customerRole = af.getCustomerRole();
        String cutOffFlag = af.getCutOffFlag();
        session.removeAttribute("passwordPdf");  //shubham
        String passwordPdf=af.getPasswordPdf();  //shubham
        session.setAttribute("passwordPdf", passwordPdf); //shubham
        String p_seq_no = "";
        String signatory = "";
        String soaFor = af.getSoaFor();
        String p_seq_no_noc = "";
        String SUBREPORT_DIR = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
        String sub_reports_location = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();

        String instrumentId = af.getInstrumentId();
        String logo_status = af.getCompanyLogo();
        String approval_level = af.getApprovalLevel();
        String productIds = af.getProductIds();
        String products = productIds;
        String p_actual_irr = "";
        String exitLoad = af.getExitLoad();
        String expectedIRR = af.getExpectedIRR();
        String foreClosureCharges = af.getForeClosureCharges();
        String schemeId = af.getLbxSchemeID();
        String simulationReportType = af.getSimulationReportType();
        String afc_received = "0";
        String chqBounceCharge = "0";
        String workingCapital = "0";
        String AFC_RATE = "0";
        String REBATE_PRECLOSURE = "0";
        logger.info(new StringBuilder().append("schemeId is ::  ").append(schemeId).append(" and simulationReportType is  ::").append(simulationReportType).toString());

        String p_received_date = af.getReceiptDate();
        String p_batch_no = af.getLbxBatchNO();
        String p_bank_id = af.getLbxBankID();
        String p_bankBranch_id = af.getLbxBranchID();
        String productCategory = af.getProducTCategory();
        String loanClass = af.getLoanClassification();

        String PRODUCT_CATEGORY = "";
        String instrumentsMode = af.getInstrumentsMode();
        String p_instruments_mode = "";

        String p_report_type = "";
        String reporttype1 = af.getReporttype12();
        String reporttype2 = af.getReporttype();
        String reporttype3 = af.getReporttype13();
        String duplicateOrOriginal = "";
        String bankAccount = af.getBankAccount();
        String lbxLoanNoHID = af.getLbxLoanNoHID();
        String lbxDealNoHID = af.getLbxDealNoHID();
        String ScovDcsn = af.getCovDcsn();
        String Sfrequency = af.getFrequency();
        logger.info(new StringBuilder().append("productCategory::").append(productCategory).toString());
        logger.info(new StringBuilder().append("loanClass::").append(loanClass).toString());

        String p_fromDate = null;
        String p_toDate = null;
        String reportSubType = af.getReportSubType();
        String p_record_type = "";
        Date r_date = null;
        Date lod_date = null;
        String lod_date1 = "";
        String cov_loan_id = "";
        String cov_deal_id = "";
        String frequency = "";
        String covDcsn = "";
        String loanstatus = af.getRstatus();
        if (reportName.equals("skewnessReport"))
        {
          products = products.replace('|', ',');
        }
        if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
        {
          String[] arr = productIds.split("\\|");
          if (arr.length > 0)
          {
            productIds = "";
            for (int i = 0; i < arr.length; i++)
              productIds = new StringBuilder().append(productIds).append("','").append(arr[i]).toString();
            productIds = new StringBuilder().append(productIds.substring(2)).append("'").toString();
          }
        }
        else {
          products = "All ";
        }
        if (!CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
        {
          logger.info(new StringBuilder().append("p_branch_id::").append(p_branch_id).toString());
          p_branch_id = new StringBuilder().append("").append(p_branch_id.replace("|", ",")).toString();
          logger.info(new StringBuilder().append("p_branch_id (new):::").append(p_branch_id).toString());
        }
        if (CommonFunction.checkNull(p_branch_id).trim().equalsIgnoreCase(""))
        {
          if (dbType.equalsIgnoreCase("MSSQL")) {
            logger.info(new StringBuilder().append("p_branch_id::").append(p_branch_id).toString());
            p_branch_id = "''";
            logger.info(new StringBuilder().append("p_branch_id (new):::").append(p_branch_id).toString());
          }
        }
        logger.info(new StringBuilder().append("p_branch_type").append(p_branch_type).toString());

        ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
        logger.info(new StringBuilder().append("Implementation class: ").append(dao.getClass()).toString());

        if (dbType.equalsIgnoreCase("MSSQL"))
        {
          SUBREPORT_DIR = new StringBuilder().append(SUBREPORT_DIR).append("MSSQLREPORTS\\").toString();
          sub_reports_location = new StringBuilder().append(sub_reports_location).append("MSSQLREPORTS\\").toString();
        }
        else
        {
          SUBREPORT_DIR = new StringBuilder().append(SUBREPORT_DIR).append("MYSQLREPORTS\\").toString();
          sub_reports_location = new StringBuilder().append(sub_reports_location).append("MYSQLREPORTS\\").toString();
        }

        if (CommonFunction.checkNull(customerRole).trim().equalsIgnoreCase("G")) {
          reportName = "AUwelcome_GurReport";
        }
        String allocationDetails = request.getParameter("report");

        String p_company_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/logo.bmp").toString();
        String p_barcode_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/barcode.gif").toString();
        String p_indian_rupee_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/rupee.bmp").toString();
        String p_imageBox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageBox.bmp").toString();
        String p_imageCheckbox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageCheckbox.bmp").toString();
        Map hashMap = new HashMap();
        int company_id = 0;
        String userID = "";
        String p_company_name = "";
        String userName = "";
        String p_business_date = "";
        if (userobj != null)
        {
          p_company_name = new StringBuilder().append(userobj.getConpanyName()).append(" ").toString();
          userName = new StringBuilder().append(userobj.getUserName()).append(" ").toString();
          p_business_date = userobj.getBusinessdate();
          company_id = userobj.getCompanyId();
          userID = userobj.getUserId();
        }
        else {
          logger.info(" in execute method of ReportsAction1  action the session is out----------------");
          return mapping.findForward("sessionOut");
        }
        String p_printed_date = p_business_date;
        String fromDate1 = null;
        String toDate1 = null;
        if (fromDate != null)
        {
          if (!fromDate.trim().equals(""))
            fromDate1 = fromDate;
          if (!toDate.trim().equals(""))
            toDate1 = toDate;
        }
        String p_p_date_from = "";
        String p_p_date_to = "";
        String p_as_on_date = "";
        if (fromDate != null)
        {
          if (!fromDate.trim().equals(""))
          {
            p_p_date_from = formate(fromDate);
            p_p_date_to = formate(toDate);
          }
          p_p_date = new StringBuilder().append(" From   ").append(p_p_date_from).append("   To   ").append(p_p_date_to).append(" ").toString();
          if (fromDate.length() == 0) {
            p_p_date = "All";
          }
        }
        if (asOnDate != null)
        {
          if (!asOnDate.trim().equals(""))
            p_as_on_date = formate(asOnDate);
        }
        if ((!CommonFunction.checkNull(installmentDate).equalsIgnoreCase("")) && (reportName.equalsIgnoreCase("balance_certificate_report")))
        {
          asOnDate = installmentDate;
          logger.info(new StringBuilder().append("As on date for bal conf report : ").append(asOnDate).toString());
        }
        String p_printed_loan = "";
        if (loanType != null)
        {
          if (loanType.equals("A"))
            p_printed_loan = "All ";
          else
            p_printed_loan = new StringBuilder().append("From   ").append(loanFrom).append("   To   ").append(loanTo).append(" ").toString();
        }
        logger.info(new StringBuilder().append("p_printed_loan :").append(p_printed_loan).toString());
        logger.info(new StringBuilder().append("p_p_date :").append(p_p_date).toString());

        if (CommonFunction.checkNull(allocationDetails).trim().equalsIgnoreCase("ad"))
        {
          p_loan_id = request.getParameter("loanId");
          reportName = "loan_allocation_detail";
          reporttype = "P";
        }
        String p_status = "";
        String status = "";

        if ((reportName.equals("cancellationReport")) || (reportName.equals("earlyClosureReport")) || (reportName.equals("maturityClousreReport")) || (reportName.equals("paymentReport")) || (reportName.equals("receiptReport")))
        {
          status = af.getStatus1();
          if (status.equals("All"))
            p_status = "All ";
          if (status.equals("A"))
            p_status = "Authorized ";
          if (status.equals("X"))
            p_status = "Cancel ";
          if (status.equals("F"))
            p_status = "Forward ";
          if (status.equals("P")) {
            p_status = "Pending ";
          }

        }

        if (reportName.equals("approved_rejected"))
        {
          status = af.getStatus2();
          if (status.equals("All"))
            p_status = "All ";
          if (status.equals("A"))
            p_status = "Approved ";
          if (status.equals("X"))
            p_status = "Cancelled ";
          if (status.equals("C"))
            p_status = "Closed ";
          if (status.equals("P")) {
            p_status = "Pending ";
          }
        }
        if ((reportName.equals("knockoff_1")) || (reportName.equals("manual_advice")) || (reportName.equals("waiveoff_pending_reject")))
        {
          status = af.getStatus4();
          if (status.equals("All"))
            p_status = "All ";
          if (status.equals("A"))
            p_status = "Approved ";
          if (status.equals("X"))
            p_status = "Deleted ";
          if (status.equals("P")) {
            p_status = "Pending ";
          }
        }

        if (reportName.equals("chequebouncemain"))
        {
          reporttype = "P";
        }
        String rec_status = "";
        if (reportName.equals("interest_due_report"))
        {
          status = af.getStatus5();
          if (status.equals("All"))
            p_status = "All ";
          if (status.equals("I"))
            p_status = "Installment ";
          if (status.equals("X")) {
            p_status = "Non-Installment ";
          }
          rec_status = af.getRecStatus();

          p_printed_deal = new StringBuilder().append("From   ").append(fromDeal).append("   To   ").append(toDeal).append(" ").toString();
        }

        if ((reportName.equals("customer_bank_branch_report")) || (reportName.equals("instrument_capturing_stage")))
        {
          p_loan_id = p_loan_from;
          if (p_loan_no.trim().length() == 0) {
            p_loan_no = "All";
          }
        }
        if ((reportName.equals("Recon_Reports")) || (reportName.equals("maturity_report")) || (reportName.equalsIgnoreCase("asset_master_report")))
        {
          if (CommonFunction.checkNull(p_loan_no).trim().length() == 0)
          {
            logger.info(new StringBuilder().append("p_from_date1 --------: ").append(p_from_date1).toString());
            logger.info(new StringBuilder().append("p_to_date2 ---------").append(p_to_date1).toString());
            p_from_date = p_from_date1;
            p_to_date = p_to_date1;
            logger.info(new StringBuilder().append("p_from_date ").append(p_from_date).append(" p_to_date ").append(p_to_date).toString());
            p_p_date = new StringBuilder().append(" From   ").append(p_from_date).append("   To   ").append(p_to_date).append(" ").toString();
            p_loan_no = "All";
          }
          else
          {
            loan_date = "L";
            p_p_date = "All";
          }

        }

        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("IKF_DUE_List_Report"))
        {
          p_branch_type = af.getBranch4();

          if (reporttype1.equalsIgnoreCase("all"))
          {
            reportName = "IKF_DUE_List_Report";
          }
          else
          {
            reportName = "IKF_DUE_List_Report_N";
          }

          hashMap.put("p_report_type", reporttype1);
        }

        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("IKF_Bucket_Wise_Report"))
        {
          p_branch_type = af.getBranch4();
          String as_on_date = CommonFunction.changeFormat(asOnDate);
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          in.add("-99999");
          in.add(as_on_date);
          try
          {
            logger.info(new StringBuilder().append("DPD_CALC_PROC_TEMP (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("DPD_CALC_PROC_TEMP", in, out);

            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E")) {
              logger.info("In DPD_CALC_PROC_TEMP reports can't be generate ");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

        }

        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("user_Detail_Report_all"))
        {
          p_branch_type = af.getBranch4();
        }

        String p_month = af.getStartPeriod();

        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("ikf_od_report"))
        {
          if ((reporttype2.equalsIgnoreCase("BWBS")) || (reporttype2.equalsIgnoreCase("BWES")) || (reporttype2.equalsIgnoreCase("ORD")))
          {
            ArrayList in = new ArrayList();
            ArrayList out = new ArrayList();
            ArrayList outMessages = new ArrayList();
            installmentDate = CommonFunction.changeFormat(installmentDate);
            String s1 = "";
            String s2 = "";
            in.add("0");
            in.add(p_month);
            in.add(p_branch_id);
            in.add(userId);
            try
            {
              logger.info(new StringBuilder().append("Due_list_summary (").append(in.toString()).append(",").append(out.toString()).toString());
              outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Due_list_summary", in, out);

              s1 = CommonFunction.checkNull(outMessages.get(0));
              s2 = CommonFunction.checkNull(outMessages.get(1));
              logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
              logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
              if (s1.equalsIgnoreCase("E")) {
                logger.info("In Due_list_summary reports can't be generate ");
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }

          }

          if (reporttype2.equalsIgnoreCase("BWBS"))
          {
            reportName = "OD_SummaryReportBranchwise";
          }
          else if (reporttype2.equalsIgnoreCase("BWES"))
          {
            reportName = "OD_SummaryReportExecutiveWise";
          }
          else
          {
            reportName = "OD_ReportDetail";
          }

        }

        String p_date1 = "";
        if (reportName.equals("deposited_instrument_bank_detail"))
        {
          p_loan_id = p_loan_from;
          if (p_loan_no.trim().length() == 0) {
            p_loan_no = "All";
          }
          p_date1 = installmentDate;
        }
        logger.info(new StringBuilder().append("p_date1 :").append(p_date1).toString());

        if (reportName.equals("loan_details_header"))
        {
          sub_reports_location = new StringBuilder().append(sub_reports_location).append("loanSubReports\\").toString();
          SUBREPORT_DIR = new StringBuilder().append(SUBREPORT_DIR).append("loanSubReports\\").toString();
          p_loan_id = p_loan_from;
          reporttype = "P";
        }

        if (reportName.equals("Business_Comparison_Report"))
        {
          p_current_from_date = fromDate1;
          p_current_to_date = toDate1;
          fromDate1 = CommonFunction.changeFormat(fromDate1);
          toDate1 = CommonFunction.changeFormat(toDate1);
          p_last_from_date = dao.getLastMonthDate(fromDate1);
          p_last_to_date = dao.getLastMonthDate(toDate1);
        }

        if (reportName.equals("Stationary_report"))
        {
          p_current_from_date = fromDate1;
          p_current_to_date = toDate1;
          fromDate1 = CommonFunction.changeFormat(fromDate1);
          toDate1 = CommonFunction.changeFormat(toDate1);
          p_last_from_date = dao.getLastMonthDate(fromDate1);
          p_last_to_date = dao.getLastMonthDate(toDate1);
        }

        if (reportName.equals("PaySlip(Receipt Upload)"))
        {
          logger.info(new StringBuilder().append("p_received_date:::").append(p_received_date).toString());
          logger.info(new StringBuilder().append("p_batch_no:::").append(p_batch_no).toString());
          logger.info(new StringBuilder().append("p_bank_id:::").append(p_bank_id).toString());
          logger.info(new StringBuilder().append("p_bankBranch_id:::").append(p_bankBranch_id).toString());
          logger.info(new StringBuilder().append("p_branch_id:::").append(p_branch_id).toString());

          p_received_date = CommonFunction.changeFormat(p_received_date);
        }

        if ((reportName.equals("tat_report")) || (reportName.equals("Booking_Report")) || (reportName.equals("Booking_Report_Monthly")) || (reportName.equals("transit_files_report")) || (reportName.equals("mis_Send_To_Store")) || (reportName.equals("hold_Marked_Cases")))
        {
          p_current_from_date = fromDate1;
          p_current_to_date = toDate1;
        }

        if (reportName.equals("CERSAI_entry"))
        {
          p_current_from_date = CommonFunction.changeFormat(fromDate1);
          p_current_to_date = CommonFunction.changeFormat(toDate1);
          reporttype = "E";
          hashMap.put("p_current_from_date", p_current_from_date);
          hashMap.put("p_current_to_date", p_current_to_date);
          logger.info(new StringBuilder().append("p_current_from_date : ").append(p_current_from_date).toString());
          logger.info(new StringBuilder().append("p_current_to_date : ").append(p_current_to_date).toString());
        }

        if (reportName.equals("Insurance"))
        {
          p_current_from_date = fromDate1;
          p_current_to_date = toDate1;
          reporttype = "E";

          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          in.add(userID);
          in.add(CommonFunction.changeFormat(p_current_from_date));
          in.add(CommonFunction.changeFormat(p_current_to_date));
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("NOMINEE_DETAILS (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("NOMINEE_DETAILS", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In NOMINEE_DETAILS reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          hashMap.put("p_current_from_date", p_current_from_date);
          hashMap.put("p_current_to_date", p_current_to_date);
          logger.info(new StringBuilder().append("p_current_from_date : ").append(p_current_from_date).toString());
          logger.info(new StringBuilder().append("p_current_to_date : ").append(p_current_to_date).toString());
        }
		//start by abhishek sharma
		if(reportName.equals("PRESENTAION_RECEIPT_STATUS" ))
		{
			p_current_from_date=fromDate1; // CommonFunction.changeFormat(fromDate1);					
			p_current_to_date=toDate1; //CommonFunction.changeFormat(toDate1);;
			if (reportype.equalsIgnoreCase("P"))
	            reporttype = "P";
	          else if (reportype.equalsIgnoreCase("E"))
	            reporttype = "E";
	          else if (reportype.equalsIgnoreCase("H")) {
	            reporttype = "H";
	          }
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(CommonFunction.changeFormat(p_current_from_date));
			in.add(CommonFunction.changeFormat(p_current_to_date));
			in.add(userID);
			out.add(s1);
			out.add(s2);
			try
			{
				logger.info("RPT_PRESENTAION_RECEIPT_REPORT ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("RPT_PRESENTAION_RECEIPT_REPORT",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
		        logger.info("s1  : "+s1);
		        logger.info("s2  : "+s2);	
		        if(s1.equalsIgnoreCase("E"))
		    	   logger.info("In RPT_PRESENTAION_RECEIPT_REPORT reports can't be generate ");
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}	
			hashMap.put("p_current_from_date",p_current_from_date);
			hashMap.put("p_current_to_date",p_current_to_date);
			logger.info("p_current_from_date : "+p_current_from_date) ;
			logger.info("p_current_to_date : "+p_current_to_date);
		}
		//end by abhishek sharma
		
	if ((reportName.equalsIgnoreCase("welcome_letter_report")) || (reportName.equalsIgnoreCase("AUwelcome_report")) || (reportName.equalsIgnoreCase("AUwelcome_GurReport")) || (reportName.equalsIgnoreCase("welcome_letter_report1")) || (reportName.equalsIgnoreCase("IKF_welcome_letter_report")) || (reportName.equalsIgnoreCase("welcome_letter_report_colanding")))
        {
          if (CommonFunction.checkNull(Loan_number).trim().length() == 0)
          {
            p_Approval_date_from = af.getApprovalDateFrom();
            p_Approval_date_to = af.getApprovalDateTo();
            PRODUCT_CATEGORY = productCategory;
            logger.info(new StringBuilder().append("p_Approval_date_from::").append(p_Approval_date_from).toString());
            logger.info(new StringBuilder().append("p_Approval_date_to::").append(p_Approval_date_to).toString());
            logger.info(new StringBuilder().append("in iffff PRODUCT_CATEGORY::").append(PRODUCT_CATEGORY).toString());
            if (reportName.equalsIgnoreCase("AUwelcome_GurReport"))
            {
              p_Approval_date_from = CommonFunction.changeFormat(p_Approval_date_from);
              p_Approval_date_to = CommonFunction.changeFormat(p_Approval_date_to);
            }
          }
          else
          {
            loan_date = "L";
            p_loan_id = Loan_number;
            logger.info("inside else for p_loan_id::"+p_loan_id);
			/*changes by shubham*/
			if(reportName.equalsIgnoreCase("welcome_letter_report") || reportName.equalsIgnoreCase("welcome_letter_report_colanding"))
			{
				p_loan_id=welcome_loan_id;
				logger.info("inside if condition for welcome letter p_loan_id-------::"+p_loan_id);
			}	
			/*end*/
            p_Approval_date_from = CommonFunction.changeFormat(nhb_date);
            p_Approval_date_to = CommonFunction.changeFormat(nhb_date);
          }
          p_date = p_printed_date;
          reporttype = "P";
          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();

          if (reportName.equalsIgnoreCase("welcome_letter_report1"))
          {
            query = new StringBuilder();
            query.append("select  CLD.LOAN_ID from cr_loan_dtl CLD where 1=1 ");
            if (CommonFunction.checkNull(loan_date).trim().equalsIgnoreCase("L"))
              query.append(new StringBuilder().append(" and CLD.LOAN_ID='").append(CommonFunction.checkNull(welcome_loan_id).trim()).append("'").toString());
            else
              query.append(new StringBuilder().append(" and DATE(CLD.LOAN_APPROVAL_DATE)>='").append(CommonFunction.changeFormat(p_Approval_date_from).trim()).append("' and DATE(CLD.LOAN_APPROVAL_DATE)<='").append(CommonFunction.changeFormat(p_Approval_date_to).trim()).append("'").toString());
          }
        }
        if ((reportName.equalsIgnoreCase("welcome_letter_report")) || (reportName.equalsIgnoreCase("welcome_letter_report_colanding"))) {
          p_date = p_printed_date;
          SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
          try {
            r_date = f1.parse(p_date);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy");
          String p_welcome_date = f2.format(r_date);
          logger.info(new StringBuilder().append("p_welcome_date---------- > ").append(p_welcome_date).toString());
          hashMap.put("p_welcome_date", p_welcome_date);
          if (reportName.equalsIgnoreCase("welcome_letter_report_colanding")) {
            p_company_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/ColandingLogo.jpg").toString();
          }

        }

        if (reportName.equalsIgnoreCase("forClosureMemoReport"))
        {
          p_loan_id = p_loan_from;
          p_date = p_printed_date;
          reporttype = "P";
          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if (reportName.equalsIgnoreCase("Foreclosure_One"))
        {
          p_loan_id = p_loan_from;
          p_date = p_printed_date;
          reporttype = "P";
          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if ((reportName.equalsIgnoreCase("Gold_loan")) || (reportName.equalsIgnoreCase("Gold_loan_Aruna")))
        {
          p_loan_id = p_loan_from;
          reporttype = "P";
          String result = dao.getSnoForGoldLoanReport(p_loan_id, userId, nhb_date);
          duplicateOrOriginal = result;
        }

        if (reportName.equalsIgnoreCase("Delivery_Order_Report"))
        {
          signatory = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/sign.bmp").toString();

          p_loan_id = p_loan_from;
          p_date = p_printed_date;
          reporttype = "P";
          ArrayList result = dao.getDONo(p_loan_id, userId, nhb_date);
          String doNo = "";
          String recordCase = "";
          if (result.size() > 0) {
            doNo = (String)result.get(0);
            recordCase = (String)result.get(1);
          }
          int length = CommonFunction.checkNull(doNo).trim().length();
          if (length > 0)
            for (int i = 0; i < 6 - length; i++)
              p_seq_no = new StringBuilder().append(p_seq_no).append("0").toString();
          p_seq_no = new StringBuilder().append("NO.  ").append(p_seq_no).append(doNo).append(" (").append(recordCase).append(") ").toString();

          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if (reportName.equalsIgnoreCase("GET_ACTUAL_IRR"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String cur_date = "";
          String s1 = "";
          String s2 = "";
          String actual_IRR = "";

          in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          in.add("LIM");
          cur_date = CommonFunction.changeFormat(nhb_date);
          logger.info(new StringBuilder().append("cur_date::::: ").append(cur_date).append(":::::::::nhb_date:::::::").append(nhb_date).toString());
          in.add(cur_date);
          if (CommonFunction.checkNull(foreClosureCharges).trim().equalsIgnoreCase(""))
            foreClosureCharges = "0";
          foreClosureCharges = foreClosureCharges.replace(",", "");
          if (CommonFunction.checkNull(exitLoad).trim().equalsIgnoreCase(""))
            exitLoad = "0";
          exitLoad = exitLoad.replace(",", "");
          in.add(foreClosureCharges);
          in.add(exitLoad);
          if (CommonFunction.checkNull(expectedIRR).trim().equalsIgnoreCase(""))
            expectedIRR = "0";
          in.add(expectedIRR);
          out.add(actual_IRR);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Get_Actual_IRR (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Get_Actual_IRR", in, out);
            actual_IRR = CommonFunction.checkNull(outMessages.get(0));
            s1 = CommonFunction.checkNull(outMessages.get(1));
            s2 = CommonFunction.checkNull(outMessages.get(2));
            if (CommonFunction.checkNull(actual_IRR).trim().equalsIgnoreCase(""))
              actual_IRR = "0.00";
            logger.info(new StringBuilder().append("actual_IRR  : ").append(actual_IRR).toString());
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Get Actual IRR can't be generate ");
              saveToken(request);
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          p_loan_id = p_loan_from;
          p_actual_irr = actual_IRR;
          reporttype = "P";
        }
        if ((reportName.equalsIgnoreCase("Sanction Letter Report")) || (reportName.equalsIgnoreCase("Sanctionletter(MM)")) || (reportName.equalsIgnoreCase("termSheet_Report")))
        {
          p_deal_id = p_deal_from;
          reporttype = "P";
          /*changes by shubham*/
			if((reportName.equalsIgnoreCase("Sanction Letter Report" )))
			{
				p_loan_id=p_deal_id;
				logger.info("shubham p_loan_id in report dispatch : " + p_loan_id);
				}
			/*end*/
        }

        if (reportName.equalsIgnoreCase("loan_dump_report"))
        {
          reporttype = "E";

          p_p_date_from = fromDate1;
          p_p_date_to = toDate1;

          logger.info(new StringBuilder().append(" p_p_date_from : ").append(p_p_date_from).toString());
          logger.info(new StringBuilder().append(" p_p_date_to : ").append(p_p_date_to).toString());
        }

        if (reportName.equalsIgnoreCase("interest_certificate"))
        {
          reporttype = "P";
        }
        if (reportName.equalsIgnoreCase("balance_certificate_report"))
        {
          reporttype = "P";
        }
        if ((reportName.equalsIgnoreCase("EMI_DUE_Report")) || (reportName.equalsIgnoreCase("ACH_TRANSACTION")))
        {
          if (CommonFunction.checkNull(p_loan_no).trim().length() == 0)
          {
            p_from_installment_date = p_from_date1;
            p_to_installment_date = p_to_date1;
            p_p_date = new StringBuilder().append(" From   ").append(p_from_installment_date).append("   To   ").append(p_to_installment_date).append(" ").toString();
            p_loan_no = "All";
          }
          else
          {
            loan_date = "L";
            p_p_date = "All";
          }

        }

        String p_billing_date = "";

        if (reportName.equalsIgnoreCase("billed_EMIs_report"))
        {
          p_loan_id = p_loan_from;
          if (p_loan_no.trim().length() == 0) {
            p_loan_no = "All";
          }

          if (!installmentDate.trim().equals("")) {
            p_billing_date = installmentDate;
          }
        }
        logger.info(new StringBuilder().append("p_billing_date :").append(p_billing_date).toString());
        String p_group_id = "";
        String p_group_name_name = "";
        if (reportName.equalsIgnoreCase("exposure_calculation"))
        {
          p_group_id = group_name1;
          if (group_name.trim().length() == 0) {
            p_group_name_name = "All";
          }
        }
        String p_date_1 = "";
        if (reportName.equalsIgnoreCase("Presentation_batch_report"))
        {
          p_date1 = installmentDate;
        }

        String p_report_name = "";
        if (reportName.equalsIgnoreCase("Aging_of_the_receivables"))
        {
          p_date1 = installmentDate;
          reportName = "Aging_of_the_receivables";
          p_report_name = "Aging Of The Receivables";
        }

        if (reportName.equalsIgnoreCase("DP_Statement"))
        {
          p_date1 = installmentDate;
          reportName = "Aging_of_the_receivables";
          p_report_name = "DP Statement";
        }
        if (reportName.equalsIgnoreCase("ACH_SLIP_REPORT")) {
          p_deal_id = p_loan_from;
          reporttype = "P";
        }

        if (reportName.equalsIgnoreCase("presentation_date_report"))
        {
          if (instrumentType.equals("All"))
            p_pdc_flag = "All";
          if (instrumentType.equals("Y"))
            p_pdc_flag = "YES";
          if (instrumentType.equals("N"))
            p_pdc_flag = "NO";
          if (instrumentsMode.equals("All"))
            p_instruments_mode = "All";
          if (instrumentsMode.equals("Q"))
            p_instruments_mode = "Q";
          if (instrumentsMode.equals("E")) {
            p_instruments_mode = "E";
          }
          p_presentation_date = presentation_date;
          p_Approval_date_from = af.getApprovalDateFrom();
          p_Approval_date_to = af.getApprovalDateTo();
        }

        if (reportName.equalsIgnoreCase("receiptReport"))
        {
          if (instrumentMode.equals("All"))
            p_instrument_mode = "All";
          if (instrumentMode.equals("Q"))
            p_instrument_mode = "CHEQUE";
          if (instrumentMode.equals("E"))
            p_instrument_mode = "ECS";
          if (instrumentMode.equals("C"))
            p_instrument_mode = "CASH";
          if (instrumentMode.equals("D"))
            p_instrument_mode = "DRAFT";
          if (instrumentMode.equals("N"))
            p_instrument_mode = "NEFT";
          if (instrumentMode.equals("R"))
            p_instrument_mode = "RTGS";
          if (instrumentMode.equals("O"))
            p_instrument_mode = "OTHERS";
          if (instrumentMode.equals("DIR")) {
            p_instrument_mode = "DIRECT DEBIT";
          }

        }

        if ((reportName.equals("repricing_report")) || (reportName.equals("deferal_Report")) || (reportName.equals("part_Prepayment_Report"))) {
          p_loan_id = rdp_loan_id;
        }
        String p_gaurantor = "";
        if (reportName.equals("rp_collateral_mony_receipt"))
        {
          StringBuilder bufInsSql = new StringBuilder();
          bufInsSql.append(new StringBuilder().append(" select gcd_customer_m.CUSTOMER_NAME from cr_loan_dtl left outer join cr_deal_customer_role on(cr_loan_dtl.LOAN_DEAL_ID=cr_deal_customer_role.DEAL_ID)left outer join gcd_customer_m on(cr_deal_customer_role.GCD_ID=gcd_customer_m.CUSTOMER_ID)where cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR' and cr_loan_dtl.loan_id='").append(p_loan_id).append("'").toString());

          logger.info(new StringBuilder().append("query :").append(bufInsSql.toString()).toString());
          try
          {
            ArrayList header = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
            bufInsSql = null;
            for (int i = 0; i < header.size(); i++)
            {
              ArrayList header1 = (ArrayList)header.get(i);
              if ((header1 != null) && (header1.size() > 0))
              {
                p_gaurantor = new StringBuilder().append(p_gaurantor).append(",").append(header1.get(0)).toString();
              }

              header1.clear();
              header1 = null;
            }
            p_gaurantor = new StringBuilder().append(p_gaurantor).append(" ").toString();
            p_gaurantor = p_gaurantor.substring(1);
            header.clear();
            header = null;
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        String p_coApplicant = "";
        if ((reportName.equals("letter_of_offer_cum_acceptance")) || (reportName.equals("Sanction Letter Report For X 10")))
        {
          p_deal_id = p_deal_from;
          reporttype = "P";
          p_coApplicant = dao.getCoApplicantName(p_deal_id);
          p_coApplicant = p_coApplicant.trim();
          p_gaurantor = dao.getGaurantorName(p_deal_id);
          p_gaurantor = p_gaurantor.trim();
          p_date = p_printed_date;
        }

        String p_sd_date_from = "";
        String p_sd_date_to = "";
        if (reportName.equals("SD_reports"))
        {
          String sdStatus = af.getStatus6();
          logger.info(new StringBuilder().append("sdStatus::::::").append(sdStatus).toString());
          if (dbType.equalsIgnoreCase("MSSQL"))
          {
            reportName = "MMD_report";
            query = new StringBuilder();
            query.append(new StringBuilder().append("SELECT CLD.LOAN_NO,CLD.LOAN_REFERENCE_NO,CONCAT(GCD.CUSTOMER_NAME,' ')CUSTOMER_NAME, ISNULL(CSD.SD_ID,0)SD_ID,CSD.SD_START_DATE, CSD.SD_MATURITY_DATE,ISNULL(CSLD.LIQUIDATION_AMOUNT,0)LIQUIDATION_AMOUNT,ISNULL(CSLD.LIQUIDATION_INTEREST,0)LIQUIDATION_INTEREST, CSD.TDS_DEDUCTED,ISNULL(CSAD.INTEREST_ACCRUED,0) as Int_accured, CSAD.TDS_DEDUCTED,ISNULL(CSD.SD_AMOUNT,0)SD_AMOUNT ,ISNULL(CSM.SD_TENURE,0)SD_TENURE,  CASE CSD.SD_INTEREST_TYPE WHEN 'C' THEN POWER(CSD.SD_AMOUNT*(1+(CSD.SD_INTEREST_RATE/((CASE ISNULL(CSD.SD_COMPOUNDING_FREQ,'Y') WHEN 'M' THEN 12 WHEN 'B' THEN 6 WHEN 'Q' THEN 4 WHEN 'H' THEN 2 WHEN 'Y' THEN 1 END)*100))),  (ISNULL(CSM.SD_TENURE,0)*(CASE ISNULL(CSD.SD_COMPOUNDING_FREQ,'Y') WHEN 'M' THEN 12 WHEN 'B' THEN 6 WHEN 'Q' THEN 4 WHEN 'H' THEN 2 WHEN 'Y' THEN 1 END)/12)-CSD.SD_AMOUNT )  WHEN 'S' THEN ISNULL((CSD.SD_AMOUNT*CSD.SD_INTEREST_RATE*ISNULL(CSM.SD_TENURE,0)/1200),0) ELSE 0 END AS SD_TOTAL_INTEREST ,CSD.SD_INTEREST_RATE SD_INTEREST_RATE,CONCAT(CPM.PRODUCT_DESC,' ') PRODUCT, CONCAT(CRSM.SCHEME_DESC,' ') SCHEME,ISNULL(CSD.SD_INTEREST_RATE,0)RATE_OF_INTEREST  FROM CR_LOAN_DTL CLD  JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID  INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID = CLD.LOAN_PRODUCT  INNER JOIN CR_SCHEME_M CRSM ON CRSM.SCHEME_ID = CLD.LOAN_SCHEME  LEFT JOIN CR_SD_DTL CSD ON CLD.LOAN_ID=CSD.LOAN_ID  LEFT JOIN CR_SD_M CSM ON CLD.LOAN_ID=CSM.TXN_ID AND CSM.TXN_TYPE='LIM'  LEFT JOIN  ( \t\tSELECT SUM(ISNULL(LIQUIDATION_AMOUNT,0.0))LIQUIDATION_AMOUNT,SUM(ISNULL(LIQUIDATION_INTEREST,0.0))LIQUIDATION_INTEREST,LOAN_ID,SD_ID  \t\tFROM CR_SD_LIQUIDATION_DTL  \t\tWHERE REC_STATUS='A' GROUP BY SD_ID,LOAN_ID  )CSLD ON  CSD.SD_ID=CSLD.SD_ID  LEFT JOIN  ( \t\tSELECT SD_ID,SUM(ISNULL(INTEREST_ACCRUED,0.0))INTEREST_ACCRUED , \t\tSUM(ISNULL(TDS_DEDUCTED,0.0))TDS_DEDUCTED  \t\tFROM\tCR_SD_ACCRUAL_DTL GROUP BY SD_ID  )CSAD ON (CSD.SD_ID=CSAD.SD_ID) WHERE 'A'='A'  and (('").append(CommonFunction.checkNull(p_branch_type).trim()).append("'='All' and CLD.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '").append(CommonFunction.checkNull(userId).trim()).append("')) or CLD.loan_BRANCH in(").append(CommonFunction.checkNull(p_branch_id).trim()).append(")) ").append(" and (('").append(CommonFunction.checkNull(productCategory).trim()).append("'='All' and 'A'='A' ) or (CLD.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory).trim()).append("')))").append(" and (('").append(CommonFunction.checkNull(loanClass).trim()).append("'='All' and 'A'='A') or (CLD.sale_off_flag='").append(CommonFunction.checkNull(loanClass).trim()).append("')) ").toString());
          }
          else
          {
            query = new StringBuilder();
            query.append(new StringBuilder().append("SELECT CLD.LOAN_NO,CLD.LOAN_REFERENCE_NO,CONCAT(GCD.CUSTOMER_NAME,' ')CUSTOMER_NAME,IFNULL(CSD.SD_ID,0)SD_ID,CSD.SD_START_DATE, CSD.SD_MATURITY_DATE,IFNULL(SDM.SD_INTEREST,0)SD_TOTAL_INTEREST,IFNULL(CSLD.LIQUIDATION_AMOUNT,0)LIQUIDATION_AMOUNT,IFNULL(CSLD.LIQUIDATION_INTEREST,0)LIQUIDATION_INTEREST, CSD.TDS_DEDUCTED,IFNULL(CSAD.INTEREST_ACCRUED,0) as Int_accured, CSAD.TDS_DEDUCTED,IFNULL(CSD.SD_AMOUNT,0)SD_AMOUNT,IFNULL(SDM.SD_TENURE,0)SD_TENURE   FROM CR_LOAN_DTL CLD  JOIN GCD_CUSTOMER_M GCD ON CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID  LEFT JOIN cr_sd_m SDM ON(SDM.TXN_ID=CLD.LOAN_ID AND SDM.TXN_TYPE='LIM') LEFT JOIN CR_SD_DTL CSD ON CLD.LOAN_ID=CSD.LOAN_ID  LEFT JOIN  ( \t\tSELECT SUM(IFNULL(LIQUIDATION_AMOUNT,0.0))LIQUIDATION_AMOUNT,SUM(IFNULL(LIQUIDATION_INTEREST,0.0))LIQUIDATION_INTEREST,LOAN_ID,SD_ID  \t\tFROM CR_SD_LIQUIDATION_DTL  \t\tWHERE REC_STATUS='A' GROUP BY SD_ID  )CSLD ON  CSD.SD_ID=CSLD.SD_ID  LEFT JOIN  ( \t\tSELECT SD_ID,SUM(IFNULL(INTEREST_ACCRUED,0.0))INTEREST_ACCRUED , \t\tSUM(IFNULL(TDS_DEDUCTED,0.0))TDS_DEDUCTED  \t\tFROM\tCR_SD_ACCRUAL_DTL GROUP BY SD_ID  )CSAD ON (CSD.SD_ID=CSAD.SD_ID) WHERE 'A'='A'  and (('").append(CommonFunction.checkNull(p_branch_type).trim()).append("'='All' and CLD.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '").append(CommonFunction.checkNull(userId).trim()).append("')) or CLD.loan_BRANCH in('").append(CommonFunction.checkNull(p_branch_id).trim()).append("')) ").append(" and (('").append(CommonFunction.checkNull(productCategory).trim()).append("'='All' and 'A'='A' ) or (CLD.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory).trim()).append("')))").append(" and (('").append(CommonFunction.checkNull(loanClass).trim()).append("'='All' and 'A'='A') or (CLD.sale_off_flag='").append(CommonFunction.checkNull(loanClass).trim()).append("')) ").toString());
          }

          if (!CommonFunction.checkNull(sdStatus).trim().equalsIgnoreCase("ALL"))
          {
            query.append(new StringBuilder().append(" AND CLD.REC_STATUS='").append(sdStatus).append("' ").toString());
          }
          if (!sdfromDate.trim().equalsIgnoreCase(""))
          {
            query.append(new StringBuilder().append(" AND  CSD.SD_START_DATE>='").append(CommonFunction.changeFormat(sdfromDate)).append("' and CSD.SD_START_DATE<='").append(CommonFunction.changeFormat(sdToDate)).append("' ").toString());
            p_sd_date_from = sdfromDate;
            p_sd_date_to = sdToDate;
            p_p_date = new StringBuilder().append(" From   ").append(p_sd_date_from).append("   To   ").append(p_sd_date_to).append(" ").toString();
          }
          logger.info(new StringBuilder().append("SD_reports Query    :  ").append(query.toString()).toString());
        }

        /*if (reportName.equalsIgnoreCase("statement_of_account"))
        {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(nhb_date);

          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);

          String source = request.getParameter("source");
          result = dao.generateSOA(company_id, p_loan_id, p_business_date, userID, source, fromDate, toDate, soaFor);
          if (result.size() > 0)
          {
            String stage = (String)result.get(0);
            if (CommonFunction.checkNull(stage).trim().equalsIgnoreCase("E"))
            {
              hashMap.clear();
              String error = (String)result.get(1);
              request.setAttribute("error", error);

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

              String eodList = ddao.getCutoffDate();

              request.setAttribute("eodList", eodList);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

            query = new StringBuilder();
            query.append(CommonFunction.checkNull(result.get(2)));
            logger.info(new StringBuilder().append("Statement of Account Query  :  ").append(query.toString()).toString());
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "Some exception occur,Please contact Administrator...");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
            int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
            String module_id = session.getAttribute("moduleID").toString();

            logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
            logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
            logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

            ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
            ArrayList product = dao.getProductName();
            ArrayList loanClassification = dao.getLoanClassification();

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);
            request.setAttribute("reportlist", report);
            request.setAttribute("productlist", product);
            request.setAttribute("loanClasslist", loanClassification);
            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String eodList = ddao.getCutoffDate();

            request.setAttribute("eodList", eodList);
            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

        }*/
        if(reportName.equalsIgnoreCase("statement_of_account")  )
		{
			ArrayList result=new ArrayList();
			p_business_date=CommonFunction.changeFormat(nhb_date);
        	fromDate=CommonFunction.changeFormat(fromDate);
        	toDate=CommonFunction.changeFormat(toDate);
			String source=request.getParameter("source");
			String reportPath="/reports"+File.separator;
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				reportPath=reportPath+"MSSQLREPORTS"+File.separator;
		}
			else
			{
				reportPath=reportPath+"MYSQLREPORTS"+File.separator;	
			}
			String path="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
			String rptPath=ConnectionDAO.singleReturn(path);
			String filename = new File(rptPath).getName();
			logger.info("filename--->"+filename);
			//Code for delete starts
	
	       // String path="D:\test"; 
	        File file = new File(rptPath);
	        File[] files = file.listFiles(); 
	        for (File f:files) 
	    	{
	    		if (f.isFile() && f.exists()) 
				{ 	
					f.delete();
					logger.info("successfully deleted");
				}
	    		else
				{
					logger.info("cant delete a file due to open or error");
				} 
	    	}
	        
			//Code for delete ends
			StatementOfAccountInBulkProcessAction soaBulkReport= new StatementOfAccountInBulkProcessAction();
			logger.info("LOAN NO IN statement_of_account"+p_loan_id+" And BRANCH ID IS =="+p_branch_id+" And Product ID=="+products+" and fromDate="+fromInterestDate+" and toDate="+toInterestDate );					
			String[] loanArr = soaBulkReport.getLoanList(p_loan_id, p_branch_id, products,fromInterestDate,toInterestDate,reportName,loanFrom,loanTo);
			// HIna changes here
			String ext="";
			int intLen =0;
			if(reporttype.equalsIgnoreCase("P"))
				ext="pdf";
			else if(reporttype.equalsIgnoreCase("E"))
				ext="xls";
			else if(reporttype.equalsIgnoreCase("H"))
				ext="html";
			if (loanArr!=null)
			{
				intLen = loanArr.length;
				logger.info("Size of loan list  ================================"+loanArr.length);
				logger.info("loanArray.size()=="+intLen); 
				String[] fileArray = new String[intLen];
				for(int i=0; i < intLen; i++)
				{
					String strTemp = loanArr[i];
					p_loan_id=strTemp.trim();							
					logger.info("Token value : -->"+strTemp);
					fileArray[i]=rptPath+"/"+p_loan_id+"_"+userID+"_"+p_business_date+"."+ext;
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
										    	
					session.setAttribute("source",source);
					result=dao.generateSOAForCancelLoan(company_id,p_loan_id,p_business_date,userID,source,fromDate,toDate,soaFor);
					query=new StringBuilder();
					query.append(CommonFunction.checkNull(result.get(2)));
					logger.info("Statement of Account Query  :  "+query.toString());
					
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_business_date);
					hashMap.put("p_user_id",userId);
					hashMap.put("p_printed_by",userName+" ");
					hashMap.put("query",query.toString());
					hashMap.put("p_company_logo",p_company_logo);	
					hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
					request.setAttribute("reporttype",reporttype);
					
					if( CommonFunction.checkNull(source).equalsIgnoreCase("S")){
								String eventName="STATEMENT_OF_ACCOUNT";
								new BulkEmail().sendManualMail(reportName,hashMap,p_loan_id,eventName,reportStream);
								
							}else{
					status=soaBulkReport.generateReport(request,response,query.toString(),hashMap,reportPath,reportName,reportStream,p_business_date,userID,p_loan_id,passwordPdf);
							}
					if(i==intLen-1){
						  request.setAttribute("msg","S");
						  hashMap.clear();
					}	
				} 
				if( !CommonFunction.checkNull(source).equalsIgnoreCase("S")){
				soaBulkReport.methodForZip(fileArray, response,  request, p_business_date, userID, reportName);
				}else{

					hashMap.clear();
					request.setAttribute("error", "Mail has been sent to primary Applicant");
					//logger.info("In Generate_SOA reports can't be generate ");		        	
					int role_id;
					role_id=Integer.parseInt(session.getAttribute("roleID").toString());
					
					ArrayList<ReportsForm> sponsor1=dao.getSponsorCode();
					ArrayList<ReportsForm> financeYear=dao.getfinanceYear();
					String maxDate=dao.getMaxDefaultDate();
					request.setAttribute("maxDate",maxDate);
					request.setAttribute("financeYear",financeYear);	
				
					request.setAttribute("sponsorlist",sponsor1);	
					DownloadDAO ddao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
					//logger.info("Implementation class: "+ddao.getClass()); 
					//String eodList=ddao.getCutoffDate(); // COMMENT BY SAORABH
					//logger.info("List Off Cut_Off_Date  :  "+eodList);
					//request.setAttribute("eodList",cutOffDate);
					String dateRengeLimit=dao.getDateRangeLimit();
					request.setAttribute("dateRengeLimit",dateRengeLimit);
					String DateRangeLimitSpecial=dao.getDateRangeLimitSpecial();
					request.setAttribute("DateRangeLimitSpecial",DateRangeLimitSpecial);
					ArrayList<ReportsForm> list1=dao.getReportFormat();
					request.setAttribute("list",list1);	
					saveToken(request);// Save Token Before Loading jsp in any case
					ddao=null;
					return mapping.findForward("errorInProcedure");
				
				}
			}
			
			else
			{
				hashMap.clear();
				request.setAttribute("error", "No Record found!!!");
				//logger.info("In Generate_SOA reports can't be generate ");		        	
				int role_id;
				role_id=Integer.parseInt(session.getAttribute("roleID").toString());
				
				ArrayList<ReportsForm> sponsor1=dao.getSponsorCode();
				ArrayList<ReportsForm> financeYear=dao.getfinanceYear();
				String maxDate=dao.getMaxDefaultDate();
				request.setAttribute("maxDate",maxDate);
				request.setAttribute("financeYear",financeYear);	
			
				request.setAttribute("sponsorlist",sponsor1);	
				DownloadDAO ddao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
				//logger.info("Implementation class: "+ddao.getClass()); 
				//String eodList=ddao.getCutoffDate(); // COMMENT BY SAORABH
				//logger.info("List Off Cut_Off_Date  :  "+eodList);
				//request.setAttribute("eodList",cutOffDate);
				String dateRengeLimit=dao.getDateRangeLimit();
				request.setAttribute("dateRengeLimit",dateRengeLimit);
				String DateRangeLimitSpecial=dao.getDateRangeLimitSpecial();
				request.setAttribute("DateRangeLimitSpecial",DateRangeLimitSpecial);
				ArrayList<ReportsForm> list1=dao.getReportFormat();
				request.setAttribute("list",list1);	
				saveToken(request);// Save Token Before Loading jsp in any case
				ddao=null;
				return mapping.findForward("errorInProcedure");
			}
			
			//download the SOA result
			return null;
		}
        if (reportName.equalsIgnoreCase("statement_of_account_for_cancel_loan"))
        {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(nhb_date);

          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);

          String source = request.getParameter("source");
          reportName = "statement_of_account";
          result = dao.generateSOAForCancelLoan(company_id, p_loan_id, p_business_date, userID, source, fromDate, toDate, soaFor);
          if (result.size() > 0)
          {
            String stage = (String)result.get(0);
            if (CommonFunction.checkNull(stage).trim().equalsIgnoreCase("E"))
            {
              hashMap.clear();
              String error = (String)result.get(1);
              request.setAttribute("error", error);

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

              String eodList = ddao.getCutoffDate();

              request.setAttribute("eodList", eodList);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

            query = new StringBuilder();
            query.append(CommonFunction.checkNull(result.get(2)));
            logger.info(new StringBuilder().append("Statement of Account Query  :  ").append(query.toString()).toString());
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "Some exception occur,Please contact Administrator...");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
            int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
            String module_id = session.getAttribute("moduleID").toString();

            logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
            logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
            logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

            ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
            ArrayList product = dao.getProductName();
            ArrayList loanClassification = dao.getLoanClassification();

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);
            request.setAttribute("reportlist", report);
            request.setAttribute("productlist", product);
            request.setAttribute("loanClasslist", loanClassification);
            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String eodList = ddao.getCutoffDate();

            request.setAttribute("eodList", eodList);
            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

        }

        if ((reportName.equalsIgnoreCase("NOC_report")) || (reportName.equalsIgnoreCase("NOC_report_asset_Funded")))
        {
          String checkLinkLoanFlag = dao.checkLinkLoanFlag();
          if (CommonFunction.checkNull(checkLinkLoanFlag).equalsIgnoreCase("Y")) {
            String result = "";

            ArrayList result1 = dao.getNocCount(p_loan_id, userId, nhb_date);

            String doNo = "";
            String recordCase = "";
            if (result1.size() > 0) {
              doNo = (String)result1.get(0);
              recordCase = (String)result1.get(1);
            }
            int length = CommonFunction.checkNull(doNo).trim().length();
            if (length > 0)
              for (int i = 0; i < 7 - length; i++)
                p_seq_no_noc = new StringBuilder().append(p_seq_no_noc).append("0").toString();
            p_seq_no_noc = new StringBuilder().append(p_seq_no_noc).append(doNo).toString();

            logger.info(new StringBuilder().append("leght:::::::::::::::").append(length).append("hhhh").append(doNo).append("allll:::").append(p_seq_no_noc).toString());

            session.setAttribute("noc_loan_id", p_loan_id);

            String source = request.getParameter("source");
            result = dao.checkLinkedLoan(p_loan_id);
            int countValue = Integer.parseInt(result);
            if (countValue > 0)
            {
              hashMap.clear();
              request.setAttribute("error", "Some Other Loans are active & Linked with this loan. ");
              logger.info("In noc  reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();

              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }

        }

        logger.info(new StringBuilder().append("installmentDate     :  ").append(installmentDate).toString());
        String p_OD_LPP = "";
        String as_on_date = "";
        String p_as_on_date_head = "";

        if (reportName.equalsIgnoreCase("OD_Disbursal_Wise_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);

          double roi = 0.0D;
          if (CommonFunction.checkNull(af.getRateInterest()).equalsIgnoreCase(""))
            roi = 0.0D;
          else
            roi = Double.parseDouble(CommonFunction.checkNull(af.getRateInterest()));
          logger.info(new StringBuilder().append("Rate at run time: ").append(roi).toString());
          in.add(Double.valueOf(roi));

          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Generate_Billing_OD_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Generate_Billing_OD_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Generate_Billing_OD_Report can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          if (reportName.trim().equalsIgnoreCase("OD_Calculation_Report"))
            reportName = "Rpt_od_lpp_calculation";
          p_OD_LPP = "OD";
          p_as_on_date_head = formate(installmentDate);
        }
        if (reportName.equalsIgnoreCase("OD_Calculation_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(Integer.valueOf(company_id));
          if (!CommonFunction.checkNull(p_loan_id).equalsIgnoreCase(""))
            in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          else
            in.add(p_loan_id);
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("GENERATE_BILLING_OD_CALCULATION_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("GENERATE_BILLING_OD_CALCULATION_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In GENERATE_BILLING_OD_CALCULATION_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;

          reportName = "OD_CALCULATION_REPORT";
          p_OD_LPP = "OD";
          as_on_date = installmentDate;
        }
        if (reportName.equalsIgnoreCase("LPP_calculation_report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);

          double roi = 0.0D;
          if (CommonFunction.checkNull(af.getRateInterest()).equalsIgnoreCase(""))
            roi = 0.0D;
          else
            roi = Double.parseDouble(CommonFunction.checkNull(af.getRateInterest()));
          logger.info(new StringBuilder().append("Rate at run time: ").append(roi).toString());
          in.add(Double.valueOf(roi));

          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("DPD_LPP_Calc_Proc_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("DPD_LPP_Calc_Proc_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In DPD_LPP_Calc_Proc_Report reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          reportName = "Rpt_od_lpp_calculation";
          p_OD_LPP = "LPP";
          p_as_on_date_head = formate(installmentDate);
        }

        if (reportName.equalsIgnoreCase("OD_SIMULATION_REPORT"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          p_rate_type = af.getInterestMethod();
          if (p_rate_type.equalsIgnoreCase("S"))
          {
            p_rate_type = "Simple Interest";
          }
          else
          {
            p_rate_type = "Compound Interest";
          }

          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          in.add(af.getInterestMethod());
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);
          double roi = 0.0D;
          if (CommonFunction.checkNull(af.getRateInterest()).equalsIgnoreCase(""))
            roi = 0.0D;
          else
            roi = Double.parseDouble(CommonFunction.checkNull(af.getRateInterest()));
          logger.info(new StringBuilder().append("Rate at run time: ").append(roi).toString());
          in.add(Double.valueOf(roi));
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Generate_Billing_OD_SIMULATION_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Generate_Billing_OD_SIMULATION_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Generate_Billing_OD_SIMULATION_Report can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          reportName = "OD_SIMULATION_REPORT";
          as_on_date = installmentDate;
        }

        if (reportName.equalsIgnoreCase("LPP_SIMULATION_REPORT"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          p_rate_type = af.getInterestMethod();
          if (p_rate_type.equalsIgnoreCase("S"))
          {
            p_rate_type = "Simple Interest";
          }
          else
          {
            p_rate_type = "Compound Interest";
          }
          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_id)));
          in.add(af.getInterestMethod());
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);
          double roi = 0.0D;
          if (CommonFunction.checkNull(af.getRateInterest()).equalsIgnoreCase(""))
            roi = 0.0D;
          else
            roi = Double.parseDouble(CommonFunction.checkNull(af.getRateInterest()));
          logger.info(new StringBuilder().append("Rate at run time: ").append(roi).toString());
          in.add(Double.valueOf(roi));
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Generate_Billing_LPP_SIMULATION_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Generate_Billing_LPP_SIMULATION_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Generate_Billing_LPP_SIMULATION_Report can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          reportName = "LPP_SIMULATION_REPORT";
          as_on_date = installmentDate;
        }

        if (reportName.equalsIgnoreCase("OD_Intrest_For_All_Loans"))
        {
          int b_scheme = 0;
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          p_rate_type = af.getInterestMethod();
          if (p_rate_type.equalsIgnoreCase("S"))
          {
            p_rate_type = "Simple Interest";
          }
          else
          {
            p_rate_type = "Compound Interest";
          }

          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(af.getInterestMethod());
          in.add(userID);
          in.add(simulationReportType);
          if (CommonFunction.checkNull(schemeId).toString().trim().equalsIgnoreCase(""))
            in.add(Integer.valueOf(b_scheme));
          else
            in.add(schemeId);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("PROC_FOR_CALLING_OD_SIMULATION_ALL_LOANS (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("PROC_FOR_CALLING_OD_SIMULATION_ALL_LOANS", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In PROC_FOR_CALLING_OD_SIMULATION_ALL_LOANS can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              return mapping.findForward("errorInProcedure");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          reportName = "OD_SIMULATION_REPORT_FOR_ALL_LOAN";
          as_on_date = installmentDate;
        }

        if (reportName.equalsIgnoreCase("Future_EMI_Receivable"))
        {
          String futureDatefrom = af.getFromDate();
          String futureDateto = af.getToDate();
          String Type = af.getDetailOrSum();

          p_interval_freq = af.getIntervalFreq();
          p_amt_type = af.getAmtType();

          String recev_comp = "";

          if (af.getDetailOrSum().equals("D"))
          {
            reportName = "Future_EMI_Receivable";
          }
          else
          {
            reportName = "Future_EMI_Receivable_Summery";
          }
          if (af.getAmtType().equals("P"))
          {
            recev_comp = "Only Principal";
          }
          if (af.getAmtType().equals("I"))
          {
            recev_comp = "Only Interest";
          }
          if (af.getAmtType().equals("T"))
          {
            recev_comp = "Total Instalment";
          }
          logger.info(new StringBuilder().append("recev_comp:::::").append(recev_comp).toString());
          p_from_date = CommonFunction.changeFormat(futureDatefrom);
          p_to_date = CommonFunction.changeFormat(futureDateto);
          p_headers = new StringBuilder().append("Future EMI Receivable From ").append(formate(futureDatefrom)).append(" To ").append(formate(futureDateto)).append(" (").append(recev_comp).append(") ").toString();
        }

        if (reportName.equalsIgnoreCase("Booking_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          p_current_from_date = CommonFunction.changeFormat(p_current_from_date);
          in.add(p_current_from_date);
          p_current_to_date = CommonFunction.changeFormat(p_current_to_date);
          in.add(p_current_to_date);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("GENERATE_BOOKING_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("GENERATE_BOOKING_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In GENERATE_BOOKING_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          reportName = "RPT_BOOKING_REPORT";
          reporttype = "E";
        }
        if (reportName.equalsIgnoreCase("Booking_Report_Monthly"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          p_current_from_date = CommonFunction.changeFormat(p_current_from_date);
          in.add(p_current_from_date);
          p_current_to_date = CommonFunction.changeFormat(p_current_to_date);
          in.add(p_current_to_date);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("GENERATE_BOOKING_REPORT_MONTHLY (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("GENERATE_BOOKING_REPORT_MONTHLY", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In GENERATE_BOOKING_REPORT_MONTHLY reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          reportName = "RPT_BOOKING_REPORT_MONTHLY";
          reporttype = "E";
        }

        if ((reportName.equalsIgnoreCase("foreClosure_simulation_report")) || (reportName.equalsIgnoreCase("AUforeClosure_simulation_report")))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_from)));
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);
          in.add(CommonFunction.checkNull(reportName).trim());
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Early_Closure_Detail_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Early_Closure_Detail_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Early_Closure_Detail_Report reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          reporttype = "P";

          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if (reportName.equalsIgnoreCase("forClosurereport"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(Integer.parseInt(p_loan_from)));
          as_on_date = CommonFunction.changeFormat(asOnDate);
          in.add(as_on_date);
          in.add(userID);
          in.add(CommonFunction.checkNull(reportName).trim());
          out.add(s1);
          out.add(s2);

          ArrayList sin = new ArrayList();
          ArrayList sout = new ArrayList();
          ArrayList sOutMessages = new ArrayList();
          String s3 = "";
          String s4 = "";
          sin.add(Integer.valueOf(company_id));
          sin.add(Integer.valueOf(Integer.parseInt(p_loan_from)));
          sin.add(as_on_date);
          sin.add(userID);
          sin.add(CommonFunction.checkNull(reportName).trim());
          sout.add(s3);
          sout.add(s4);
          ArrayList queryList = new ArrayList();
          StringBuilder queryforeclosure = new StringBuilder();
          queryforeclosure.append("delete from cr_foreclosure_dtl_temp;");
          queryList.add(queryforeclosure.toString());
          ConnectionDAO.sqlInsUpdDelete(queryList);

          String countL = ConnectionDAO.singleReturn(new StringBuilder().append("select count(1) from cr_post_loan_dtl_temp where PRIMARY_LOAN = '").append(p_loan_from).append("' and REC_STATUS='A' ").toString());
          logger.info(new StringBuilder().append("Query for link loans-->> ").append(countL.toString()).toString());
          int count = Integer.parseInt(countL);
          if (count > 0)
          {
            request.setAttribute("errorFore", "Some Loans are pending.");
          }

          try
          {
            logger.info(new StringBuilder().append("Early_Closure_Detail_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Early_Closure_Detail_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());

            logger.info(new StringBuilder().append("For_Closure_Report_Test (").append(sin.toString()).append(",").append(sout.toString()).toString());
            sOutMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("For_Closure_Report_Test", sin, sout);
            s3 = CommonFunction.checkNull(outMessages.get(0));
            s4 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s3  : ").append(s3).toString());
            logger.info(new StringBuilder().append("s4  : ").append(s4).toString());
            if ((s1.equalsIgnoreCase("E")) && (s3.equalsIgnoreCase("E")))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Early_Closure_Detail_Report reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              hashMap.put("p_company_name", p_company_name);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;
          reporttype = "P";

          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if (reportName.equalsIgnoreCase("OverDue_Summary_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          as_on_date = CommonFunction.changeFormat(asOnDate);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          if (reportype.equalsIgnoreCase("P"))
            reporttype = "P";
          else if (reportype.equalsIgnoreCase("E"))
            reporttype = "E";
          else if (reportype.equalsIgnoreCase("H")) {
            reporttype = "H";
          }

          try
          {
            logger.info(new StringBuilder().append("MIS_SUMMARY_GENERATION_FRONT_END (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("MIS_SUMMARY_GENERATION_FRONT_END", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());

            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In MIS_SUMMARY_GENERATION_FRONT_END reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              hashMap.put("p_company_name", p_company_name);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

          p_loan_id = p_loan_from;

          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();
        }

        if ((reportName.equalsIgnoreCase("Early_Closure_AFC_Report")) || (reportName.equalsIgnoreCase("Maturity_Closure_AFC_Report")))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(Integer.valueOf(company_id));
          as_on_date = CommonFunction.changeFormat(installmentDate);
          in.add(as_on_date);
          in.add(userID);
          in.add(Integer.valueOf(Integer.parseInt(p_loan_from)));
          if (reportName.equalsIgnoreCase("Early_Closure_AFC_Report"))
          {
            in.add("E");
          }
          else
          {
            in.add("M");
          }
          out.add(afc_received);
          out.add(chqBounceCharge);
          out.add(workingCapital);
          out.add(AFC_RATE);
          out.add(REBATE_PRECLOSURE);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("DPD_LPP_Calc_Proc_AFC (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("DPD_LPP_Calc_Proc_AFC", in, out);
            afc_received = CommonFunction.checkNull(outMessages.get(0));
            chqBounceCharge = CommonFunction.checkNull(outMessages.get(1));
            workingCapital = CommonFunction.checkNull(outMessages.get(2));
            AFC_RATE = CommonFunction.checkNull(outMessages.get(3));
            REBATE_PRECLOSURE = CommonFunction.checkNull(outMessages.get(4));
            s1 = CommonFunction.checkNull(outMessages.get(5));
            s2 = CommonFunction.checkNull(outMessages.get(6));

            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            logger.info(new StringBuilder().append("afc_received  : ").append(afc_received).toString());
            logger.info(new StringBuilder().append("chqBounceCharge  : ").append(chqBounceCharge).toString());
            logger.info(new StringBuilder().append("workingCapital  : ").append(workingCapital).toString());
            logger.info(new StringBuilder().append("AFC_RATE  : ").append(AFC_RATE).toString());
            logger.info(new StringBuilder().append("REBATE_PRECLOSURE  : ").append(REBATE_PRECLOSURE).toString());

            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In DPD_LPP_Calc_Proc_AFC reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          if (reportName.equalsIgnoreCase("Early_Closure_AFC_Report"))
          {
            p_headers = "Early Closure";
            p_rate_type = "E";
          }
          else
          {
            p_headers = "Maturity Closure";
            p_rate_type = "M";
          }

          p_loan_id = p_loan_from;
          reporttype = "P";
          ArrayList list = dao.getCompanyAddress(company_id);
          ReportsForm frm = new ReportsForm();
          frm = (ReportsForm)list.get(0);
          p_msg = frm.getMsg();
          p_address1 = new StringBuilder().append(frm.getAddress1()).append(" ").append(frm.getCity()).append(" ").append(frm.getPincode()).append(" ").append(frm.getPhone()).append(" ").append(frm.getFax()).toString();
          p_email = new StringBuilder().append(frm.getEmail()).append(" ").append(frm.getWebsite()).toString();
          p_email1 = frm.getEmail();

          if (reporttype3.equalsIgnoreCase("CA"))
          {
            reportName = "rpt_early_maturity_afc_report";
          }
          else
          {
            reportName = "Rpt_working_capital_interest_calculation";
          }

          logger.info(new StringBuilder().append("afc_received:---->").append(afc_received).toString());
          logger.info(new StringBuilder().append("chqBounceCharge:---->").append(chqBounceCharge).toString());
          logger.info(new StringBuilder().append("workingCapital:---->").append(workingCapital).toString());
          logger.info(new StringBuilder().append("AFC_RATE:---->").append(AFC_RATE).toString());
        }

        if (reportName.equalsIgnoreCase("pre_emi_to_be_accrued"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();

          String rtype = af.getReporttype11();
          asOnDate = CommonFunction.changeFormat(af.getAsonDate());
          String s1 = "";
          String s2 = "";
          in.add(Integer.valueOf(company_id));
          in.add(Integer.valueOf(0));
          in.add(Integer.valueOf(0));
          in.add("A");

          in.add(asOnDate);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("Pre_EMI_Billing_Report (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionDAO.callSP("Pre_EMI_Billing_Report", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In Early_Closure_Detail_Report reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              ddao = null;
              return mapping.findForward("errorInProcedure");
            }

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

        }

        logger.info(new StringBuilder().append("p_loan_id     : ").append(p_loan_id).toString());

        if ((reportName.equals("sanction_letter_after_loan")) || (reportName.equals("subsequentDisbursementVoucher")) || (reportName.equals("CVDisbursalVoucher")) || (reportName.equals("WelcomeLetter(MM)")) || (reportName.equals("margin_deposit_receipt(mm)")) || (reportName.equals("reset_letter(mm)")))
        {
          p_loan_id = p_loan_from;
          reporttype = "P";
        }

        if (reportName.equals("batch_report"))
        {
          if (!presentationDate.trim().equalsIgnoreCase(""))
          {
            String p_presentationDate;
            if (generatesBatch.equals("Y"))
            {
              if (dbType.equalsIgnoreCase("MSSQL"))
              {
                query = new StringBuilder();
                query.append(new StringBuilder().append("SELECT DISTINCT CGBD.BATCH_ID,CGBD.BATCH_NO,CGBD.PRESENTATION_DATE,CLD.LOAN_NO,CASE CPID.PDC_INSTRUMENT_MODE WHEN 'C' THEN 'CASH ' WHEN 'Q' THEN 'CHEQUE ' WHEN 'D' THEN 'DRAFT ' WHEN 'R' THEN 'RTGS ' WHEN 'N' THEN 'NEFT ' WHEN 'DIR' THEN 'DIRECT DEBIT ' WHEN 'E' THEN 'ECS ' WHEN 'H' THEN 'ACH ' WHEN 'O' THEN 'OTHERS ' END AS INSTRUMENT_MODE,CASE CPID.PDC_INSTRUMENT_TYPE WHEN 'P' THEN 'PAYABLE ' WHEN 'R' THEN 'RECEIVABLE ' WHEN 'K' THEN 'KNOCKOFF ' END AS INSTRUMENT_TYPE,CPID.PDC_INSTRUMENT_NO,CPID.PDC_INSTRUMENT_AMOUNT,CASE CPID.PDC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'A' THEN 'APPROVED ' WHEN 'H' THEN 'HOLD ' WHEN 'R' THEN 'RELEASED ' WHEN 'X' THEN 'DELETED ' WHEN 'B' THEN 'BOUNCED ' WHEN 'F' THEN 'FORWARDED ' WHEN 'L' THEN 'CANCELLED ' END AS PDC_STATUS,CPID.PDC_FLAG,CPID.PDC_INSTRUMENT_DATE ,CASE CGBD.REC_STATUS WHEN 'A' THEN 'APPROVED ' WHEN 'P' THEN 'PENDING FOR FINALIZED ' WHEN 'F' THEN 'FINALIZED ' END AS REC_STATUS  FROM CR_PDC_INSTRUMENT_DTL CPID join CR_LOAN_DTL CLD ON (CLD.LOAN_ID=CPID.PDC_LOAN_ID) LEFT OUTER JOIN cr_generate_batch_dtl CGBD ON  (CPID.PDC_INSTRUMENT_ID=CGBD.INSTRUMENT_ID)  WHERE CGBD.PRESENTATION_DATE='").append(CommonFunction.changeFormat(presentationDate)).append("' and ").append("(('").append(CommonFunction.checkNull(p_branch_type)).append("'='All' and CLD.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id= '").append(CommonFunction.checkNull(userId)).append("')) or CLD.loan_BRANCH in(").append(CommonFunction.checkNull(p_branch_id)).append(")) ").append("and (('").append(CommonFunction.checkNull(productCategory)).append("'='All' and 'A'='A' ) or (CLD.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory)).append("')))").append("and (('").append(CommonFunction.checkNull(loanClass)).append("'='All' and 'A'='A') or (CLD.sale_off_flag='").append(CommonFunction.checkNull(loanClass)).append("')) ").toString());
              }
              else
              {
                query = new StringBuilder();
                query.append(new StringBuilder().append("SELECT DISTINCT CGBD.BATCH_ID,CGBD.BATCH_NO,CGBD.PRESENTATION_DATE,CLD.LOAN_NO,CASE CPID.PDC_INSTRUMENT_MODE WHEN 'C' THEN 'CASH ' WHEN 'Q' THEN 'CHEQUE ' WHEN 'D' THEN 'DRAFT ' WHEN 'R' THEN 'RTGS ' WHEN 'N' THEN 'NEFT ' WHEN 'DIR' THEN 'DIRECT DEBIT ' WHEN 'E' THEN 'ECS ' WHEN 'H' THEN 'ACH ' WHEN 'O' THEN 'OTHERS ' END AS INSTRUMENT_MODE,CASE CPID.PDC_INSTRUMENT_TYPE WHEN 'P' THEN 'PAYABLE ' WHEN 'R' THEN 'RECEIVABLE ' WHEN 'K' THEN 'KNOCKOFF ' END AS INSTRUMENT_TYPE,CPID.PDC_INSTRUMENT_NO,CPID.PDC_INSTRUMENT_AMOUNT,CASE CPID.PDC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'A' THEN 'APPROVED ' WHEN 'H' THEN 'HOLD ' WHEN 'R' THEN 'RELEASED ' WHEN 'X' THEN 'DELETED ' WHEN 'B' THEN 'BOUNCED ' WHEN 'F' THEN 'FORWARDED ' WHEN 'L' THEN 'CANCELLED ' END AS PDC_STATUS,CPID.PDC_FLAG,CPID.PDC_INSTRUMENT_DATE ,CASE CGBD.REC_STATUS WHEN 'A' THEN 'APPROVED ' WHEN 'P' THEN 'PENDING FOR FINALIZED ' WHEN 'F' THEN 'FINALIZED ' END AS REC_STATUS  FROM CR_PDC_INSTRUMENT_DTL CPID join CR_LOAN_DTL CLD ON (CLD.LOAN_ID=CPID.PDC_LOAN_ID) LEFT OUTER JOIN cr_generate_batch_dtl CGBD ON  (CPID.PDC_INSTRUMENT_ID=CGBD.INSTRUMENT_ID)  WHERE CGBD.PRESENTATION_DATE='").append(CommonFunction.changeFormat(presentationDate)).append("' and ").append("(('").append(CommonFunction.checkNull(p_branch_type)).append("'='All' and CLD.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id= '").append(CommonFunction.checkNull(userId)).append("')) or (FIND_IN_SET(CLD.loan_BRANCH ,'").append(CommonFunction.checkNull(p_branch_id)).append("'))) ").append("and (('").append(CommonFunction.checkNull(productCategory)).append("'='All' and 'A'='A' ) or (CLD.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory)).append("')))").append("and (('").append(CommonFunction.checkNull(loanClass)).append("'='All' and 'A'='A') or (CLD.sale_off_flag='").append(CommonFunction.checkNull(loanClass)).append("')) ORDER BY CLD.LOAN_ID").toString());
              }

              logger.info(new StringBuilder().append("queryrr    :").append(query.toString()).toString());
              p_presentationDate = presentationDate;
            }
            else
            {
              query = new StringBuilder();
              query.append(new StringBuilder().append("SELECT DISTINCT CGBD.BATCH_ID,CGBD.BATCH_NO,CGBD.PRESENTATION_DATE,CLD.LOAN_NO,CASE CPID.PDC_INSTRUMENT_MODE WHEN 'C' THEN 'CASH ' WHEN 'Q' THEN 'CHEQUE ' WHEN 'D' THEN 'DRAFT ' WHEN 'R' THEN 'RTGS ' WHEN 'N' THEN 'NEFT ' WHEN 'DIR' THEN 'DIRECT DEBIT ' WHEN 'E' THEN 'ECS ' WHEN 'H' THEN 'ACH ' WHEN 'O' THEN 'OTHERS ' END AS INSTRUMENT_MODE,CASE CPID.PDC_INSTRUMENT_TYPE WHEN 'P' THEN 'PAYABLE ' WHEN 'R' THEN 'RECEIVABLE ' WHEN 'K' THEN 'KNOCKOFF ' END AS INSTRUMENT_TYPE,CPID.PDC_INSTRUMENT_NO,CPID.PDC_INSTRUMENT_AMOUNT,CPID.PDC_FLAG,CASE CPID.PDC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'A' THEN 'APPROVED ' WHEN 'H' THEN 'HOLD ' WHEN 'R' THEN 'RELEASED ' WHEN 'X' THEN 'DELETED ' WHEN 'B' THEN 'BOUNCED ' WHEN 'F' THEN 'FORWARDED ' WHEN 'L' THEN 'CANCELLED ' END AS PDC_STATUS,CPID.PDC_INSTRUMENT_DATE , CASE CGBD.REC_STATUS WHEN 'A' THEN 'APPROVED ' WHEN 'P' THEN 'PENDING FOR FINALIZED ' WHEN 'F' THEN 'FINALIZED ' END AS REC_STATUS FROM CR_PDC_INSTRUMENT_DTL CPID  join CR_LOAN_DTL CLD ON (CLD.LOAN_ID=CPID.PDC_LOAN_ID) LEFT OUTER JOIN cr_generate_batch_dtl CGBD ON  (CPID.PDC_INSTRUMENT_ID=CGBD.INSTRUMENT_ID)  WHERE CGBD.PRESENTATION_DATE <>'").append(CommonFunction.changeFormat(presentationDate)).append("' ORDER BY CLD.LOAN_ID").toString());

              logger.info(new StringBuilder().append("query11111...........    :").append(query.toString()).toString());
            }

          }
          else
          {
            query = new StringBuilder();
            query.append(new StringBuilder().append("SELECT  CGBD.BATCH_ID,CGBD.BATCH_NO,CPID.PRESENTATION_DATE,CLD.LOAN_NO, CASE CPID.PDC_INSTRUMENT_MODE WHEN 'C' THEN 'CASH ' WHEN 'Q' THEN 'CHEQUE ' WHEN 'D' THEN 'DRAFT ' WHEN 'R' THEN 'RTGS ' WHEN 'N' THEN 'NEFT ' WHEN 'DIR' THEN 'DIRECT DEBIT ' WHEN 'E' THEN 'ECS ' WHEN 'O' THEN 'OTHERS ' END AS INSTRUMENT_MODE, CASE CPID.PDC_INSTRUMENT_TYPE WHEN 'P' THEN 'PAYABLE ' WHEN 'R' THEN 'RECEIVABLE ' WHEN 'K' THEN 'KNOCKOFF ' END AS INSTRUMENT_TYPE,CPID.PDC_INSTRUMENT_NO,CPID.PDC_INSTRUMENT_AMOUNT,CPID.PDC_FLAG,CASE CPID.PDC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'A' THEN 'APPROVED ' WHEN 'H' THEN 'HOLD ' WHEN 'R' THEN 'RELEASED ' WHEN 'X' THEN 'DELETED ' WHEN 'B' THEN 'BOUNCED ' WHEN 'F' THEN 'FORWARDED ' WHEN 'L' THEN 'CANCELLED ' END AS PDC_STATUS,CPID.PDC_INSTRUMENT_DATE,CASE CGBD.REC_STATUS WHEN 'A' THEN 'APPROVED ' WHEN 'P' THEN 'PENDING FOR FINALIZED ' WHEN 'F' THEN 'FINALIZED ' END AS REC_STATUS  FROM cr_generate_batch_dtl CGBD  JOIN CR_PDC_INSTRUMENT_DTL CPID ON (CPID.PDC_INSTRUMENT_ID=CGBD.INSTRUMENT_ID) join  CR_LOAN_DTL CLD  ON (CLD.LOAN_ID=CPID.PDC_LOAN_ID)  WHERE CGBD.BATCH_NO='").append(batchNo).append("' ORDER BY CLD.LOAN_ID").toString());

            logger.info(new StringBuilder().append("query222222 :").append(query.toString()).toString());
          }
        }

        if (reportName.equals("ecs_notpad"))
        {
          String s1 = "";
          String s2 = "";
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          sponsorDate = CommonFunction.changeFormat(sponsorDate);
          in.add(sponsorDate);
          in.add(sponsor);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("ECS_NOTPAD_REPORT_GENERATOR (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("ECS_NOTPAD_REPORT_GENERATOR", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
            {
              hashMap.clear();
              request.setAttribute("error", s2);
              logger.info("In ECS_NOTPAD_REPORT_GENERATOR reports can't be generate ");

              int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
              int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
              String module_id = session.getAttribute("moduleID").toString();

              logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
              logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
              logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

              ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
              ArrayList product = dao.getProductName();
              ArrayList loanClassification = dao.getLoanClassification();
              ArrayList sponsor1 = dao.getSponsorCode();
              ArrayList financeYear = dao.getfinanceYear();
              String maxDate = dao.getMaxDefaultDate();
              request.setAttribute("maxDate", maxDate);
              request.setAttribute("financeYear", financeYear);
              request.setAttribute("reportlist", report);
              request.setAttribute("productlist", product);
              request.setAttribute("loanClasslist", loanClassification);
              request.setAttribute("sponsorlist", sponsor1);
              DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
              logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
              String eodList = ddao.getCutoffDate();
              logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
              request.setAttribute("eodList", eodList);
              String dateRengeLimit = dao.getDateRangeLimit();
              request.setAttribute("dateRengeLimit", dateRengeLimit);
              String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
              request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
              ArrayList list1 = dao.getReportFormat();
              request.setAttribute("list", list1);
              saveToken(request);
              return mapping.findForward("errorInProcedure");
            }

            reporttype = "T";
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
        if (reportName.equals("payable_receivable_payment"))
        {
          chargesId = chargesId.replace("|", "','");
          chargesId = new StringBuilder().append("'").append(chargesId).append("'").toString();
          if (dbType.equalsIgnoreCase("MSSQL")) {
            query = new StringBuilder();
            query.append(new StringBuilder().append("SELECT DISTINCT b.LOAN_NO, b.LOAN_REFERENCE_NO,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(CHARGE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"',''),'/','/ '),'?','? '),'.','. '),',',', ')CHARGE_DESC,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(INSTRUMENT_NO,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"',''),'/','/ '),'?','? '),'.','. '),',',', ')INSTRUMENT_NO,RECEIVED_DATE,INSTRUMENT_AMOUNT,PMNT_AMOUNT,PMNT_DATE,case A.REC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'F' THEN 'PENDING FOR APPROVAL 'WHEN 'X' THEN 'CANCEL 'WHEN 'A' THEN 'APPROVED 'WHEN 'D' THEN 'DEPOSITED 'WHEN 'B' THEN 'BOUNCED 'WHEN 'R' THEN 'REALISE 'END AS REC_STATUS,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(A.MAKER_REMARKS,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"',''),'/','/ '),'?','? '),'.','. '),',',', ')MAKER_REMARKS,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(A.AUTHOR_ID,' - ',isnull((SELECT USER_NAME FROM SEC_USER_M SEC WHERE SEC.USER_ID=A.AUTHOR_ID),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"',''),'/','/ '),'?','? '),'.','. '),',',', ')AUTHOR_ID,replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(A.MAKER_ID,' - ',isnull((SELECT USER_NAME FROM SEC_USER_M S WHERE S.USER_ID=A.MAKER_ID),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"',''),'/','/ '),'?','? '),'.','. '),',',', ')MAKER_ID,iif(dbo.DATE(A.MAKER_DATE)='0000-00-00',null,dbo.DATE(A.MAKER_DATE))MAKER_DATE, iif(dbo.DATE(A.AUTHOR_DATE)='0000-00-00',null,dbo.DATE(A.AUTHOR_DATE))AUTHOR_DATE FROM cr_instrument_dtl A JOIN cr_loan_dtl B ON A.TXNID=B.LOAN_ID JOIN CR_PMNT_DTL C ON C.INSTRUMENT_ID=A.INSTRUMENT_ID AND C.TXNADVICEID IN (SELECT TXNADVICE_ID FROM CR_TXNADVICE_DTL WHERE ADVICE_TYPE='").append(paymentMode).append("' AND CHARGE_CODE_ID IN (").append(chargesId).append("))").append("JOIN CR_TXNADVICE_DTL D ON D.TXNADVICE_ID=C.TXNADVICEID ").append("JOIN COM_CHARGE_CODE_M ON D.CHARGE_CODE_ID=CHARGE_CODE ").append("where  A.INSTRUMENT_TYPE='").append(paymentMode).append("'").append("and B.REC_STATUS NOT IN('X','L') ").append(" and (('").append(CommonFunction.checkNull(p_branch_type).trim()).append("'='All' and B.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '").append(CommonFunction.checkNull(userId).trim()).append("')) or B.loan_BRANCH in(").append(CommonFunction.checkNull(p_branch_id).trim()).append("))").append(" and (('").append(CommonFunction.checkNull(productCategory).trim()).append("'='All' and 'A'='A' ) or (B.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory).trim()).append("')))").append(" and (('").append(CommonFunction.checkNull(loanClass).trim()).append("'='All' and 'A'='A') or (B.sale_off_flag='").append(CommonFunction.checkNull(loanClass).trim()).append("')) ").append("and dbo.DATE(A.MAKER_DATE)>='").append(CommonFunction.changeFormat(fromDate)).append("' and dbo.DATE(A.MAKER_DATE)<='").append(CommonFunction.changeFormat(toDate)).append("'").toString());

            if (CommonFunction.checkNull(paymentMode).trim().equalsIgnoreCase("R"))
              query.append(" and A.REC_STATUS in ('D','B','R') ");
            else {
              query.append(" and A.REC_STATUS in ('C','S','R') ");
            }
            logger.info(new StringBuilder().append("payable_receivable_payment Query  for MSSQL:::::  ").append(query.toString()).toString());
          } else {
            query = new StringBuilder();
            query.append(new StringBuilder().append("SELECT DISTINCT b.LOAN_NO, b.LOAN_REFERENCE_NO, replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CHARGE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')CHARGE_DESC, replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(INSTRUMENT_NO,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')INSTRUMENT_NO, RECEIVED_DATE,INSTRUMENT_AMOUNT,PMNT_AMOUNT,PMNT_DATE, case A.REC_STATUS WHEN 'P' THEN 'PENDING ' WHEN 'F' THEN 'PENDING FOR APPROVAL ' WHEN 'X' THEN 'CANCEL ' WHEN 'A' THEN 'APPROVED ' WHEN 'D' THEN 'DEPOSITED ' WHEN 'B' THEN 'BOUNCED ' WHEN 'R' THEN 'REALISE ' END AS REC_STATUS, replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(A.MAKER_REMARKS,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')MAKER_REMARKS, replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(A.AUTHOR_ID,' - ',ifnull((SELECT USER_NAME FROM SEC_USER_M SEC WHERE SEC.USER_ID=A.AUTHOR_ID),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')AUTHOR_ID, replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(A.MAKER_ID,' - ',ifnull((SELECT USER_NAME FROM SEC_USER_M S WHERE S.USER_ID=A.MAKER_ID),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')MAKER_ID, if(date(A.MAKER_DATE)='0000-00-00',null,date(A.MAKER_DATE))MAKER_DATE, if(date(A.AUTHOR_DATE)='0000-00-00',null,date(A.AUTHOR_DATE))AUTHOR_DATE FROM cr_instrument_dtl A JOIN cr_loan_dtl B ON A.TXNID=B.LOAN_ID JOIN CR_PMNT_DTL C ON C.INSTRUMENT_ID=A.INSTRUMENT_ID AND C.TXNADVICEID IN (SELECT TXNADVICE_ID FROM CR_TXNADVICE_DTL WHERE ADVICE_TYPE='").append(paymentMode).append("' AND CHARGE_CODE_ID IN (").append(chargesId).append("))").append(" JOIN CR_TXNADVICE_DTL D ON D.TXNADVICE_ID=C.TXNADVICEID").append(" JOIN COM_CHARGE_CODE_M ON D.CHARGE_CODE_ID=CHARGE_CODE").append(" where  A.INSTRUMENT_TYPE='").append(paymentMode).append("'").append(" and IFNULL(B.REC_STATUS,'P') NOT IN('X','L') ").append(" and (('").append(CommonFunction.checkNull(p_branch_type).trim()).append("'='All' and B.loan_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl where  user_id=  '").append(CommonFunction.checkNull(userId).trim()).append("')) or B.loan_BRANCH in('").append(CommonFunction.checkNull(p_branch_id).trim()).append("'))").append(" and (('").append(CommonFunction.checkNull(productCategory).trim()).append("'='All' and 'A'='A' ) or (B.LOAN_PRODUCT_CATEGORY =('").append(CommonFunction.checkNull(productCategory).trim()).append("')))").append(" and (('").append(CommonFunction.checkNull(loanClass).trim()).append("'='All' and 'A'='A') or (B.sale_off_flag='").append(CommonFunction.checkNull(loanClass).trim()).append("')) ").append(" and date(A.MAKER_DATE)>='").append(CommonFunction.changeFormat(fromDate)).append("' and date(A.MAKER_DATE)<='").append(CommonFunction.changeFormat(toDate)).append("'").toString());

            if (CommonFunction.checkNull(paymentMode).trim().equalsIgnoreCase("R"))
              query.append(" and A.REC_STATUS in ('D','B','R')ORDER BY B.LOAN_ID,A.INSTRUMENT_NO,A.MAKER_DATE,CHARGE_CODE_ID");
            else {
              query.append(" and A.REC_STATUS in ('C','S','R')ORDER BY B.LOAN_ID,A.INSTRUMENT_NO,A.MAKER_DATE,CHARGE_CODE_ID");
            }
            logger.info(new StringBuilder().append("payable_receivable_payment Query  :  ").append(query.toString()).toString());
          }

        }

        if ((reportName.equals("NHB_Monthly_Report")) || ((!reportName.equalsIgnoreCase("ECS_Mandete_Cover_Letter_Header")) && (!reportName.equalsIgnoreCase("maturity_report")) && (!reportName.equalsIgnoreCase("maturityClousreReport"))) || (reportName.equals("ops_mis_report")))
        {
          p_from_date = CommonFunction.changeFormat(fromDate1);
          p_to_date = CommonFunction.changeFormat(toDate1);
        }

        if ((reportName.equals("Daily_Pledge_Report")) || (reportName.equals("releasereport")))
        {
          p_from_date = CommonFunction.changeFormat(fromDate1);
          p_to_date = CommonFunction.changeFormat(toDate1);
        }

        if (reportName.equalsIgnoreCase("incipient_report"))
        {
          if (reportName.equalsIgnoreCase("incipient_report"))
          {
            ArrayList in = new ArrayList();
            ArrayList out = new ArrayList();
            ArrayList outMessages = new ArrayList();
            installmentDate = CommonFunction.changeFormat(installmentDate);
            String s1 = "";
            String s2 = "";
            in.add(installmentDate);
            out.add(s1);
            out.add(s2);
            try
            {
              logger.info(new StringBuilder().append("INCIPIENT_REPORT_PROC (").append(in.toString()).append(",").append(out.toString()).toString());
              outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("INCIPIENT_REPORT_PROC", in, out);
              s1 = CommonFunction.checkNull(outMessages.get(0));
              s2 = CommonFunction.checkNull(outMessages.get(1));
              logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
              logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
              if (s1.equalsIgnoreCase("E")) {
                logger.info("In RPT_RECEIPT_REPORT reports can't be generate ");
              }
              else {
                query = new StringBuilder();
                query.append(new StringBuilder().append("select * from cr_incipient_tmp_dtl where date(MAKER_DATE)='").append(installmentDate).append("'").toString());
                logger.info(new StringBuilder().append("Incipient query in if : ").append(query.toString()).toString());
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }

          }

        }

        if (reportName.equalsIgnoreCase("receiptReport"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(p_p_date);
          in.add(CommonFunction.changeFormat(fromDate1));
          in.add(CommonFunction.changeFormat(toDate1));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(p_status);
          in.add(instrumentMode);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_RECEIPT_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_RECEIPT_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_RECEIPT_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("approved_rejected"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(p_p_date);
          in.add(CommonFunction.changeFormat(fromDate1));
          in.add(CommonFunction.changeFormat(toDate1));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(p_status);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_APPROVE_REJECTED_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_APPROVE_REJECTED_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_APPROVE_REJECTED_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("Delinquency_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(CommonFunction.changeFormat(asOnDate));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_DELINQUENCY_DUMP_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_DELINQUENCY_DUMP_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_DELINQUENCY_DUMP_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("earlyClosureReport"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(p_p_date);
          in.add(CommonFunction.changeFormat(fromDate1));
          in.add(CommonFunction.changeFormat(toDate1));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(p_status);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_EARLY_CLOSURE_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_EARLY_CLOSURE_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_EARLY_CLOSURE_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("exposure_calculation"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(group_name1);
          in.add(p_group_name_name);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_EXPOSURE_CALCULATION_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_EXPOSURE_CALCULATION_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_EXPOSURE_CALCULATION_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("Installment_Due_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(p_p_date);
          in.add(CommonFunction.changeFormat(fromDate1));
          in.add(CommonFunction.changeFormat(toDate1));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_INSTALLMENT_DUE_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_INSTALLMENT_DUE_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_INSTALLMENT_DUE_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("Installment_Received_Report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(p_p_date);
          in.add(CommonFunction.changeFormat(fromDate1));
          in.add(CommonFunction.changeFormat(toDate1));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_INSTALLMENT_RECEIVED_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_INSTALLMENT_RECEIVED_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_INSTALLMENT_RECEIVED_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("Installment_Recived_summary_rpt"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(CommonFunction.changeFormat(asOnDate));
          in.add(p_branch_type);
          in.add(p_branch_id);
          in.add(loanType);
          in.add(p_loan_from);
          in.add(p_loan_to);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_INSTALLMENT_RECEIVED_SUMMARY_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_INSTALLMENT_RECEIVED_SUMMARY_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_INSTALLMENT_RECEIVED_SUMMARY_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equals("DISBURSAL_ADVICE"))
        {
          p_loan_id = p_loan_from;
        }

        if (reportName.equals("RTGS_NEFT_From_Bank"))
        {
          p_Approval_date_from = af.getApprovalDateFrom();
          p_Approval_date_to = af.getApprovalDateTo();
          String ReportType = af.getRtgsType();

          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(userID);
          in.add(ReportType);
          in.add(CommonFunction.changeFormat(p_Approval_date_from));
          in.add(CommonFunction.changeFormat(p_Approval_date_to));
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RTGS_NEFT_FLAGING (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RTGS_NEFT_FLAGING", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RTGS_NEFT_FLAGING reports can't be generate ");
					     hashMap.put("rtgsType", ReportType);
            hashMap.put("p_business_date", p_business_date);
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

        }

        if (reportName.equalsIgnoreCase("disbursal_dump"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          in.add(userID);
          in.add(CommonFunction.changeFormat(fromDate));
          in.add(CommonFunction.changeFormat(toDate));
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("DISBURSAL_DUMP_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("DISBURSAL_DUMP_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In DISBURSAL_DUMP_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        if (reportName.equalsIgnoreCase("presentation_date_report"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";

          if (CommonFunction.checkNull(p_presentation_date).trim().equalsIgnoreCase(""))
            in.add("0000-00-00");
          else {
            in.add(CommonFunction.changeFormat(p_presentation_date));
          }
          if (CommonFunction.checkNull(p_Approval_date_from).trim().equalsIgnoreCase(""))
            in.add("0000-00-00");
          else
            in.add(CommonFunction.changeFormat(p_Approval_date_from));
          if (CommonFunction.checkNull(p_Approval_date_to).trim().equalsIgnoreCase(""))
            in.add("0000-00-00");
          else
            in.add(CommonFunction.changeFormat(p_Approval_date_to));
          in.add(p_instruments_mode);

          logger.info(new StringBuilder().append("the value of instrument mode for test ").append(p_instruments_mode).toString());

          in.add(instrumentType);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          try
          {
            logger.info(new StringBuilder().append("RPT_PRESENTATION_DATE_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
            outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("RPT_PRESENTATION_DATE_REPORT", in, out);
            s1 = CommonFunction.checkNull(outMessages.get(0));
            s2 = CommonFunction.checkNull(outMessages.get(1));
            logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
            logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
            if (s1.equalsIgnoreCase("E"))
              logger.info("In RPT_PRESENTATION_DATE_REPORT reports can't be generate ");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

        }

        if ((reportName.equals("login_breakup_report")) || (reportName.equals("DailyLoginReport")) || (reportName.equals("Login_Detail_MIS")) || (reportName.equals("cashTransactionReport")) || (reportName.equals("tat_report")))
        {
          fromDate1 = CommonFunction.changeFormat(fromDate1);
          toDate1 = CommonFunction.changeFormat(toDate1);
        }
        if (reportName.equalsIgnoreCase("DailyLoginReport"))
        {
          query = new StringBuilder();
          query.append(new StringBuilder().append(" select concat(br.BRANCH_DESC,' ')BRANCH_DESC,concat(PRODUCT_DESC,' ')PRODUCT_DESC ,if(dtl.id is null,0,1)id,ifnull(dtl.amt,0)amt ,'For the day ' header from cr_product_m left join (\t\tselect BRANCH_ID,BRANCH_DESC\t\tfrom com_branch_m\t\twhere BRANCH_ID in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("') and rec_status='A'").append(" )br on true").append(" left join").append(" (").append("\t\tselect a.deal_id id,DEAL_BRANCH branch_id,DEAL_PRODUCT product_id,ifnull(b.DEAL_LOAN_AMOUNT,0)amt").append("\t\tfrom cr_deal_dtl a").append("\t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)").append("\t\tjoin").append("\t\t(").append("\t\t\tselect a.DEAL_ID").append("\t\t\tfrom cr_deal_movement_dtl a").append("\t\t\tjoin").append("\t\t\t(").append("\t\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\t\twhere D.DEAL_STAGE_ID='DC' and D.DEAL_FORWARD_USER!='' ").append("\t\t\t\tgroup by deal_id").append("\t\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append("\t\t\twhere date(DEAL_FORWARDED)='").append(CommonFunction.changeFormat(nhb_date)).append("'").append("\t\t)fDate on(fDate.deal_id=b.DEAL_ID)").append("\t\twhere DEAL_BRANCH  in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append("  )dtl on(cr_product_m.product_id=dtl.product_id and br.BRANCH_ID=dtl.branch_id) where cr_product_m.REC_STATUS='A' ");

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append(" and cr_product_m.product_id in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append(" union all select concat(br.BRANCH_DESC,' ')BRANCH_DESC,concat(PRODUCT_DESC,' ')PRODUCT_DESC ,if(dtl.id is null,0,1)id,ifnull(dtl.amt,0)amt ,'MTD ' header from cr_product_m left join ( \t\tselect BRANCH_ID,BRANCH_DESC \t\tfrom com_branch_m \t\twhere BRANCH_ID in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("') and rec_status='A'").append(" )br on true").append(" left join").append(" (").append(" \t\tselect a.deal_id id,DEAL_BRANCH branch_id,DEAL_PRODUCT product_id,ifnull(b.DEAL_LOAN_AMOUNT,0)amt").append(" \t\tfrom cr_deal_dtl a").append(" \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)").append(" \t\tjoin").append(" \t\t(").append(" \t\t\tselect a.DEAL_ID").append(" \t\t\tfrom cr_deal_movement_dtl a").append(" \t\t\tjoin").append(" \t\t\t(").append(" \t\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append(" \t\t\t\tfrom cr_deal_movement_dtl D").append(" \t\t\t\twhere D.DEAL_STAGE_ID='DC' and D.DEAL_FORWARD_USER!='' ").append(" \t\t\t\tgroup by deal_id").append(" \t\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t\t\twhere date(DEAL_FORWARDED)>='").append(fromDate1).append("' and date(DEAL_FORWARDED)<='").append(toDate1).append("'").append(" \t\t)fDate on(fDate.deal_id=b.DEAL_ID)").append(" \t\twhere  DEAL_BRANCH  in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append("  )dtl on(cr_product_m.product_id=dtl.product_id and br.BRANCH_ID=dtl.branch_id) where cr_product_m.REC_STATUS='A' ");

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append(" and cr_product_m.product_id in(").append(productIds).append(")").toString());
        }
        if (reportName.equalsIgnoreCase("login_breakup_report"))
        {
          query = new StringBuilder();
          query.append(new StringBuilder().append(" select concat(com_branch_m.BRANCH_DESC,' ')BRANCH_DESC,  IFNULL(LOGIN.ID,0) LOGIN_ID,IFNULL(LOGIN.amt,0) LOGIN_AMT,  IFNULL(OPENING.ID,0) OPENING_ID,IFNULL(OPENING.amt,0) OPENING_AMT,  IFNULL(APPROVED.ID,0) APPROVED_ID,IFNULL(APPROVED.amt,0) APPROVED_AMT,  IFNULL(IN_PROCESS.ID,0) IN_PROCESS_ID,IFNULL(IN_PROCESS.amt,0) IN_PROCESS_AMT,  IFNULL(REJECTED.ID,0) REJECTED_ID,IFNULL(REJECTED.amt,0) REJECTED_AMT,  IFNULL(DISBURSEMENT.ID,0) DISBURSEMENT_ID,IFNULL(DISBURSEMENT.amt,0) DISBURSEMENT_AMT,  IFNULL(DROPPED.ID,0) DROPPED_ID,IFNULL(DROPPED.amt,0) DROPPED_AMT,  IFNULL(GAP.ID,0) GAP_ID,IFNULL(GAP.amt,0) GAP_AMT,  IFNULL(CANCELLED.ID,0) CANCELLED_ID,IFNULL(CANCELLED.amt,0) CANCELLED_AMT  from com_branch_m  LEFT JOIN  (  \tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \tfrom cr_deal_dtl a  \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin  \t(  \t\tselect a.DEAL_ID  \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)>='").append(fromDate1).append("' and date(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t)fDate on(fDate.deal_id=b.DEAL_ID) ").append("\tWHERE DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  AND DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \tgroup by DEAL_BRANCH  )LOGIN ON(com_branch_m.BRANCH_ID=LOGIN.branch_id)  LEFT JOIN  (  \tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \tfrom cr_deal_dtl a  \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_deal_termination_dtl C ON(A.DEAL_ID=C.DEAL_ID AND C.REC_STATUS='A')  \tWHERE DATE(c.AUTHOR_DATE)>='").append(fromDate1).append("' AND DATE(c.AUTHOR_DATE)<='").append(toDate1).append("' ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \tgroup by DEAL_BRANCH  )CANCELLED ON(com_branch_m.BRANCH_ID=CANCELLED.branch_id)  LEFT JOIN  (  \tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \tfrom cr_deal_dtl a  \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin  \t(  \t\tselect a.DEAL_ID  \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)<'").append(fromDate1).append("' ").append(" \t)fDate on(fDate.deal_id=b.DEAL_ID) ").append(" \tWHERE A.DEAL_ID NOT IN(select DEAL_ID from CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' AND DATE(APPROVAL_DATE)<'").append(fromDate1).append("') ").append(" \tAND A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)<'").append(fromDate1).append("') ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \tgroup by DEAL_BRANCH  )OPENING ON(com_branch_m.BRANCH_ID=OPENING.branch_id)  LEFT JOIN  (  \t\tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_SANCTION_AMOUNT,0))amt  \t\tfrom cr_deal_dtl a  \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \t\tjoin  \t\t(\t\t\t\tselect A.deal_id from  \t\t\tCR_DEAL_LEVEL_APPROVAL_DTL a  \t\t\tjoin  \t\t\t(  \t\t\t\tselect deal_id,min(deal_approval_id)deal_approval_id  \t\t\t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' and level_decision='A' ").append(" \t\t\t\tgroup by deal_id ").append(" \t\t\t)b on(a.deal_id=b.DEAL_ID and a.deal_approval_id=b.deal_approval_id) ").append(" \t\t\tand DATE(a.APPROVAL_DATE)>='").append(fromDate1).append("' and  DATE(a.APPROVAL_DATE)<='").append(toDate1).append("' ").append(" \t\t)APP on(APP.deal_id=A.DEAL_ID) ").append("   \tWHERE A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)>='").append(fromDate1).append("' AND DATE(AUTHOR_DATE)<='").append(toDate1).append("') ").append("\t\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tgroup by DEAL_BRANCH  )APPROVED ON(com_branch_m.BRANCH_ID=APPROVED.branch_id)  LEFT JOIN  (  \t\tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \t\tfrom cr_deal_dtl a  \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \t\tJOIN  \t\t(  \t\t\tselect a.DEAL_ID  \t\t\tfrom cr_deal_movement_dtl a  \t\t\tjoin  \t\t\t(  \t\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\t\tfrom cr_deal_movement_dtl D  \t\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\t\tgroup by deal_id  \t\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\t\twhere date(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t)QC ON(QC.DEAL_ID=A.DEAL_ID) ").append(" \t\tWHERE A.DEAL_ID NOT IN ").append(" \t\t( ").append(" \t\t\t\tselect A.deal_id from ").append(" \t\t\t\tCR_DEAL_LEVEL_APPROVAL_DTL a ").append(" \t\t\t\tjoin ").append(" \t\t\t\t( ").append(" \t\t\t\t\tselect deal_id,min(deal_approval_id)deal_approval_id ").append(" \t\t\t\t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' ").append(" \t\t\t\t\tgroup by deal_id ").append(" \t\t\t\t)b on(a.deal_id=b.DEAL_ID and a.deal_approval_id=b.deal_approval_id) ").append(" \t\t\t\tand  DATE(a.APPROVAL_DATE)<='").append(toDate1).append("' ").append("\t\t)").append(" \t\tAND A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)<='").append(toDate1).append("') ").append("\t    AND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tgroup by DEAL_BRANCH  )IN_PROCESS ON(com_branch_m.BRANCH_ID=IN_PROCESS.branch_id)  LEFT JOIN  (  \t\tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \t\tfrom cr_deal_dtl a  \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \t\tJOIN  \t\t(  \t\t\t\t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\t\t\t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\t\t\t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t\t\t\tGROUP BY DEAL_ID ").append(" \t\t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append(" \t\tWHERE  A.REC_STATUS='X' ").append("\t    AND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tgroup by DEAL_BRANCH  )REJECTED ON(com_branch_m.BRANCH_ID=REJECTED.branch_id)  LEFT JOIN  (  \t\tSELECT  COUNT(1)ID,SUM(P.AMT)AMT,LOAN_BRANCH branch_id  \t\tFROM  CR_LOAN_DTL L  \t\tJOIN  \t\t(  \t\t\t\tselect A.DEAL_ID  \t\t\t\tfrom cr_deal_dtl a  \t\t\t\tJOIN  \t\t\t\t(  \t\t\t\t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\t\t\t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\t\t\t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t\t\t\tGROUP BY DEAL_ID ").append(" \t\t\t\t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append(" \t\t\t\tWHERE  A.REC_STATUS='A' ").append(" \t\t)DEAL ON(DEAL.DEAL_ID=L.LOAN_DEAL_ID) ").append(" \t\tJOIN ").append(" \t\t( ").append(" \t\t\tselect  CLD.LOAN_ID,SUM(ifnull(CPD.PMNT_AMOUNT,0))AMT ").append(" \t\t\tfrom cr_loan_dtl CLD ").append(" \t\t\tjoin cr_txnadvice_dtl CTD on (CLD.loan_id=CTD.loan_id and CTD.CHARGE_CODE_ID=2 AND ADVICE_TYPE='P' and  CTD.REC_STATUS='A') ").append(" \t\t\tjoin cr_pmnt_dtl CPD on (CTD.TXNADVICE_ID=CPD.TXNADVICEID AND CPD.REC_STATUS <>'S') ").append(" \t\t\tJOIN CR_INSTRUMENT_DTL CID ON CID.TXNID=CLD.LOAN_ID AND CPD.INSTRUMENT_ID=CID.INSTRUMENT_ID ").append(" \t\t\twhere CID.INSTRUMENT_TYPE IN ('P','K') and IF(CID.INSTRUMENT_TYPE='K', CPD.REC_STATUS='A',CPD.REC_STATUS IN ('C','R')) ").append(" \t\t\tAND CLD.REC_STATUS='A' ").append(" \t\t\tAND  PMNT_DATE>='").append(fromDate1).append("' ").append(" \t\t\tGROUP BY CLD.LOAN_ID ").append(" \t\t)P ON (L.LOAN_ID=P.LOAN_ID) ").append("\t\tAND LOAN_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and LOAN_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tGROUP BY L.LOAN_BRANCH  )DISBURSEMENT ON(com_branch_m.BRANCH_ID=DISBURSEMENT.branch_id)  LEFT JOIN  (  \t\tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0))amt  \t\tfrom cr_deal_dtl a  \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \t\tJOIN  \t\t(  \t\t\tSELECT A.DEAL_ID FROM cr_deal_approval_dtl A  \t\t\tJOIN  \t\t\t(  \t\t\t\t\tSELECT DEAL_ID,MAX(DEAL_APPROVAL_ID)DEAL_APPROVAL_ID  \t\t\t\t\tFROM cr_deal_approval_dtl  \t\t\t\t\tWHERE APPROVAL_DECISION='X' AND APPROVAL_LEVEL=1  \t\t\t\t\tGROUP BY DEAL_ID  \t\t\t)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.DEAL_APPROVAL_ID)  \t\t\tWHERE DATE(APPROVAL_DATE)>='").append(fromDate1).append("' and  DATE(APPROVAL_DATE)<='").append(toDate1).append("' ").append(" \t\t\tAND REASON_ID=(SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='DROPPED_REASON_ID') ").append(" \t\t)APP ON(A.DEAL_ID=APP.DEAL_ID) ").append(" \t\tWHERE  A.REC_STATUS='X' ").append("\t\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tgroup by DEAL_BRANCH  )DROPPED ON(com_branch_m.BRANCH_ID=DROPPED.branch_id)  LEFT JOIN  (  \t\tselect count(1) id,DEAL_BRANCH branch_id,sum(ifnull(b.DEAL_LOAN_AMOUNT,0)-IFNULL(b.DEAL_SANCTION_AMOUNT,0))amt  \t\tfrom cr_deal_dtl a  \t\tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \t\tJOIN  \t\t(  \t\t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t\tGROUP BY DEAL_ID ").append(" \t\t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append(" \t\tWHERE  A.REC_STATUS='A' AND (IFNULL(b.DEAL_LOAN_AMOUNT,0)-IFNULL(b.DEAL_SANCTION_AMOUNT,0))>0 ").append("\t\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

          if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
            query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
          query.append(new StringBuilder().append("  \t\tgroup by DEAL_BRANCH  )GAP ON(com_branch_m.BRANCH_ID=GAP.branch_id)  WHERE com_branch_m.REC_STATUS='A'  AND com_branch_m.BRANCH_ID in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").append(" GROUP BY com_branch_m.BRANCH_ID ").toString());
        }

        if (reportName.equalsIgnoreCase("Login_Detail_MIS"))
        {
          String dealLoan = "Deal";
          String sanctionAmt = "Deal Sanction Amount";
          String loginBranch = CommonFunction.checkNull(af.getLoginBranchId()).trim();
          String bucket = af.getBucket();
          bucket = CommonFunction.checkNull(bucket).trim();
          if (CommonFunction.checkNull(bucket).trim().equalsIgnoreCase(""))
            bucket = " ";
          char val = bucket.charAt(0);
          switch (val)
          {
          case 'A':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tjoin  \t(  \t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED  \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)>='").append(fromDate1).append("' and date(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t)fDate on(fDate.deal_id=b.DEAL_ID) ").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append(" WHERE DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'B':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tjoin  \t(  \t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)<'").append(fromDate1).append("' ").append(" \t)fDate on(fDate.deal_id=b.DEAL_ID) ").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append(" \tWHERE A.DEAL_ID NOT IN(select DEAL_ID from CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' AND DATE(APPROVAL_DATE)<'").append(fromDate1).append("') ").append(" \tAND A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)<'").append(fromDate1).append("') ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'C':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tjoin  \t(\t\t\t\tselect A.deal_id,a.APPROVAL_DATE from  \t\t\tCR_DEAL_LEVEL_APPROVAL_DTL a  \t\t\tjoin  \t\t\t(  \t\t\t\tselect deal_id,min(deal_approval_id)deal_approval_id  \t\t\t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' and level_decision='A' ").append(" \t\t\t\tgroup by deal_id ").append(" \t\t\t)b on(a.deal_id=b.DEAL_ID and a.deal_approval_id=b.deal_approval_id) ").append(" \t\t\tand DATE(a.APPROVAL_DATE)>='").append(fromDate1).append("' and  DATE(a.APPROVAL_DATE)<='").append(toDate1).append("' ").append(" \t)APP on(APP.deal_id=A.DEAL_ID) ").append("\tleft join ").append("\t( ").append("\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED").append("\t\tfrom cr_deal_movement_dtl a").append("\t\tjoin").append("\t\t(").append("\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''").append("\t\t\tgroup by deal_id").append("\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t) fDate on(fDate.deal_id=a.DEAL_ID)").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append("   WHERE A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)>='").append(fromDate1).append("' AND DATE(AUTHOR_DATE)<='").append(toDate1).append("') ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'D':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,QC.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tJOIN  \t(  \t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t)QC ON(QC.DEAL_ID=A.DEAL_ID) ").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append(" \tWHERE A.DEAL_ID NOT IN ").append(" \t( ").append(" \t\t\tselect A.deal_id from ").append(" \t\t\tCR_DEAL_LEVEL_APPROVAL_DTL a ").append(" \t\t\tjoin ").append(" \t\t\t( ").append(" \t\t\t\tselect deal_id,min(deal_approval_id)deal_approval_id ").append(" \t\t\t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL where approval_level='").append(approval_level).append("' ").append(" \t\t\t\tgroup by deal_id ").append(" \t\t\t)b on(a.deal_id=b.DEAL_ID and a.deal_approval_id=b.deal_approval_id) ").append(" \t\t\tand  DATE(a.APPROVAL_DATE)<='").append(toDate1).append("' ").append("\t)").append(" \tAND A.DEAL_ID NOT IN(SELECT DEAL_ID FROM cr_deal_termination_dtl WHERE REC_STATUS='A' AND DATE(AUTHOR_DATE)<='").append(toDate1).append("') ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'E':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tjoin cr_deal_termination_dtl C ON(A.DEAL_ID=C.DEAL_ID AND C.REC_STATUS='A') \tleft join \t( \t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED\t\tfrom cr_deal_movement_dtl a\t\tjoin\t\t(\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id\t\t\tfrom cr_deal_movement_dtl D\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''\t\t\tgroup by deal_id\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID) \t) fDate on(fDate.deal_id=a.DEAL_ID) \tleft join  \t(  \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE  \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL  \t\twhere approval_level=1 and level_decision='A'  \t\tgroup by deal_id  \t)app on(a.deal_id=app.deal_id)    left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A')  \tWHERE DATE(c.AUTHOR_DATE)>='").append(fromDate1).append("' AND DATE(c.AUTHOR_DATE)<='").append(toDate1).append("' ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'F':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tJOIN  \t(  \t\t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t\tGROUP BY DEAL_ID ").append(" \t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append("\tleft join ").append("\t( ").append("\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED").append("\t\tfrom cr_deal_movement_dtl a").append("\t\tjoin").append("\t\t(").append("\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''").append("\t\t\tgroup by deal_id").append("\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t) fDate on(fDate.deal_id=a.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append(" \tWHERE  A.REC_STATUS='X' ").append("\tAND DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'G':
            dealLoan = "Loan";
            sanctionAmt = "Payment Amount";
            query = new StringBuilder();
            query.append(new StringBuilder().append(" SELECT L.LOAN_ID ID,L.LOAN_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,L.LOAN_LOAN_AMOUNT AMOUNT1,P.AMT AMOUNT2,  \t\tcase L.REC_STATUS when 'P' then 'PENDING ' when 'A' then 'ACTIVE ' when 'L' then 'CANCELLED ' when 'X' then 'REJECTED ' when 'C' then 'CLOSED ' end as STATUS,DEAL.DEAL_CURRENT_APPROVAL_LEVEL,       IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=DEAL.DEAL_ID)>0,DEAL.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \t\tFROM  CR_LOAN_DTL L  \t\tjoin gcd_customer_m cu on(L.loan_customer_id=cu.customer_id)       JOIN cr_deal_dtl DEAL ON(L.LOAN_DEAL_ID=DEAL.DEAL_ID) \t\tJOIN cr_product_m pr on(pr.PRODUCT_ID=L.LOAN_PRODUCT)  \t\tJOIN cr_scheme_m sc on(sc.SCHEME_ID=L.LOAN_SCHEME)  \t\tJOIN com_branch_m br on(br.BRANCH_ID=L.LOAN_BRANCH)  \t\tJOIN  \t\t(  \t\t\t\tselect A.DEAL_ID  \t\t\t\tfrom cr_deal_dtl a  \t\t\t\tJOIN  \t\t\t\t(  \t\t\t\t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\t\t\t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\t\t\t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\t\t\t\tGROUP BY DEAL_ID ").append(" \t\t\t\t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append(" \t\t\t\tWHERE  A.REC_STATUS='A' ").append(" \t\t)DEAL1 ON(DEAL1.DEAL_ID=L.LOAN_DEAL_ID) ").append(" \t\tJOIN ").append(" \t\t( ").append(" \t\t\tselect  CLD.LOAN_ID,SUM(ifnull(CPD.PMNT_AMOUNT,0))AMT ").append(" \t\t\tfrom cr_loan_dtl CLD ").append(" \t\t\tjoin cr_txnadvice_dtl CTD on (CLD.loan_id=CTD.loan_id and CTD.CHARGE_CODE_ID=2 AND ADVICE_TYPE='P' and  CTD.REC_STATUS='A') ").append(" \t\t\tjoin cr_pmnt_dtl CPD on (CTD.TXNADVICE_ID=CPD.TXNADVICEID AND CPD.REC_STATUS <>'S') ").append(" \t\t\tJOIN CR_INSTRUMENT_DTL CID ON CID.TXNID=CLD.LOAN_ID AND CPD.INSTRUMENT_ID=CID.INSTRUMENT_ID ").append(" \t\t\twhere CID.INSTRUMENT_TYPE IN ('P','K') and IF(CID.INSTRUMENT_TYPE='K', CPD.REC_STATUS='A',CPD.REC_STATUS IN ('C','R')) ").append(" \t\t\tAND CLD.REC_STATUS='A' ").append(" \t\t\tAND  PMNT_DATE>='").append(fromDate1).append("' ").append(" \t\t\tGROUP BY CLD.LOAN_ID ").append(" \t\t)P ON (L.LOAN_ID=P.LOAN_ID) ").append("\t\tleft join ").append("\t\t( ").append("\t\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED").append("\t\t\tfrom cr_deal_movement_dtl a").append("\t\t\tjoin").append("\t\t\t(").append("\t\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''").append("\t\t\t\tgroup by deal_id").append("\t\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t\t) fDate on(fDate.deal_id=DEAL.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(L.loan_deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=DEAL.deal_id and ter.rec_status='A') ").append("\twhere LOAN_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("') ").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and LOAN_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'H':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tJOIN  \t(  \t\tSELECT A.DEAL_ID FROM cr_deal_approval_dtl A  \t\tJOIN  \t\t(  \t\t\t\tSELECT DEAL_ID,MAX(DEAL_APPROVAL_ID)DEAL_APPROVAL_ID  \t\t\t\tFROM cr_deal_approval_dtl  \t\t\t\tWHERE APPROVAL_DECISION='X' AND APPROVAL_LEVEL=1  \t\t\t\tGROUP BY DEAL_ID  \t\t)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.DEAL_APPROVAL_ID)  \t\tWHERE DATE(APPROVAL_DATE)>='").append(fromDate1).append("' and  DATE(APPROVAL_DATE)<='").append(toDate1).append("' ").append(" \t\tAND REASON_ID=(SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='DROPPED_REASON_ID') ").append(" \t)APP ON(A.DEAL_ID=APP.DEAL_ID) ").append("\tleft join ").append("\t( ").append("\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED").append("\t\tfrom cr_deal_movement_dtl a").append("\t\tjoin").append("\t\t(").append("\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!='' ").append("\t\t\tgroup by deal_id").append("\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t) fDate on(fDate.deal_id=a.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app1 on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append("\twhere DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").append(" \tand  A.REC_STATUS='X' ").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'I':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tJOIN  \t(  \t\tSELECT DEAL_ID FROM cr_deal_movement_dtl  \t\tWHERE DEAL_STAGE_ID='UNC' AND REC_STATUS='A'  \t\tAND DATE(DEAL_FORWARDED)>='").append(fromDate1).append("' and  DATE(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t\tGROUP BY DEAL_ID ").append(" \t)UNC ON(A.DEAL_ID=UNC.DEAL_ID) ").append("\tleft join ").append("\t( ").append("\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED").append("\t\tfrom cr_deal_movement_dtl a").append("\t\tjoin").append("\t\t(").append("\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id").append("\t\t\tfrom cr_deal_movement_dtl D").append("\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''").append("\t\t\tgroup by deal_id").append("\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)").append(" \t) fDate on(fDate.deal_id=a.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append("\twhere DEAL_BRANCH in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").append(" \tand  A.REC_STATUS='A' AND (IFNULL(b.DEAL_LOAN_AMOUNT,0)-IFNULL(b.DEAL_SANCTION_AMOUNT,0))>0 ").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'J':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH) \tjoin\t(\t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED\t\tfrom cr_deal_movement_dtl a\t\tjoin\t\t(\t\t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id\t\t\t\tfrom cr_deal_movement_dtl D\t\t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''\t\t\t\tgroup by deal_id\t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)\t\twhere date(DEAL_FORWARDED)='").append(CommonFunction.changeFormat(nhb_date)).append("'").append("\t)fDate on(fDate.deal_id=b.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append("\twhere DEAL_BRANCH  in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          case 'K':
            query = new StringBuilder();
            query.append(new StringBuilder().append(" select a.DEAL_ID ID,a.DEAL_NO NO,concat(cu.CUSTOMER_NAME,' ')CUSTOMER_NAME,fDate.DEAL_RECEIVED QUALITY_CHECK_DC_DATE,concat(pr.PRODUCT_DESC,' ') PRODUCT,concat(sc.SCHEME_DESC,' ') SCHEME,concat(br.BRANCH_DESC,' ') BRANCH,b.DEAL_LOAN_AMOUNT AMOUNT1,IFNULL(b.DEAL_SANCTION_AMOUNT,0)AMOUNT2 , \tcase a.REC_STATUS when 'A' then 'APPROVED ' when 'X' then if((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,'REJECTED ','CANCELLED ')when 'P' then 'PENDING 'when 'F' then 'FORWARDED 'end as STATUS,DEAL_CURRENT_APPROVAL_LEVEL,   IF(app.APPROVAL_DATE IS NULL,IF((select count(1) from cr_deal_movement_dtl where REC_STATUS='A' and deal_id=a.DEAL_ID)>0,a.DEAL_APPROVAL_DATE,ter.AUTHOR_DATE),app.APPROVAL_DATE)APPROVAL_DATE   \tfrom cr_deal_dtl a  \tjoin cr_deal_customer_m cu on(a.deal_customer_id=cu.customer_id) \tjoin  cr_deal_loan_dtl b on(a.deal_id=b.deal_id)  \tjoin cr_product_m pr on(pr.PRODUCT_ID=b.DEAL_PRODUCT)  \tjoin cr_scheme_m sc on(sc.SCHEME_ID=b.DEAL_SCHEME)  \tjoin com_branch_m br on(br.BRANCH_ID=a.DEAL_BRANCH)  \tjoin  \t(  \t\tselect a.DEAL_ID,a.DEAL_FORWARDED DEAL_RECEIVED \t\tfrom cr_deal_movement_dtl a  \t\tjoin  \t\t(  \t\t\tselect deal_id,min(DEAL_MOVEMENT_ID) id  \t\t\tfrom cr_deal_movement_dtl D  \t\t\twhere D.DEAL_STAGE_ID='QC' and D.DEAL_FORWARD_USER!=''  \t\t\tgroup by deal_id  \t\t)b on(a.DEAL_MOVEMENT_ID=b.id and a.DEAL_ID=b.DEAL_ID)  \t\twhere date(DEAL_FORWARDED)>='").append(fromDate1).append("' and date(DEAL_FORWARDED)<='").append(toDate1).append("' ").append(" \t)fDate on(fDate.deal_id=b.DEAL_ID)").append(" \tleft join ").append(" \t( ").append(" \t\tselect deal_id,min(APPROVAL_DATE)APPROVAL_DATE ").append(" \t\tfrom CR_DEAL_LEVEL_APPROVAL_DTL ").append(" \t\twhere approval_level=1 and level_decision='A' ").append(" \t\tgroup by deal_id ").append(" \t)app on(a.deal_id=app.deal_id) ").append("   left join cr_deal_termination_dtl ter on(ter.deal_id=a.deal_id and ter.rec_status='A') ").append("\twhere DEAL_BRANCH  in(select BRANCH_ID from sec_user_branch_dtl where user_id='").append(userId).append("')").toString());

            if (!CommonFunction.checkNull(productIds).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  and DEAL_PRODUCT in(").append(productIds).append(")").toString());
            if (!CommonFunction.checkNull(loginBranch).trim().equalsIgnoreCase(""))
              query.append(new StringBuilder().append("  AND br.BRANCH_ID in(").append(loginBranch).append(")").toString()); break;
          }

          query.append(" Order by DEAL_RECEIVED ");
          hashMap.put("dealLoan", dealLoan);
          hashMap.put("sanctionAmt", sanctionAmt);
        }
        if (reportName.equals("termSheet_Report"))
        {
          ReportsDAO dao1 = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
          p_deal_id = dao1.getDealId(p_loan_from);
          reporttype = "P";
        }
        if (reportName.equalsIgnoreCase("Other_Debit_Charges_In_SOA"))
        {
          reporttype = "P";
        }

        String partnerShipType = af.getPartnerShipType();
        String partnerType = af.getPartnerType();
        String partnerCode = af.getPartnerCode();
        String partnerName = af.getPartnerName();
        String partnerId = af.getLbxPartnerId();
        String loan_NO = af.getLoan_NO();
        String loanID = af.getLbxloan_Id();

        if ((reportName.equals("NOC_Report_With_LOD")) || (reportName.equals("NOC_Report_WithOut_LOD")))
        {
          String loanId = p_loan_id;
          String loanDealId = ConnectionDAO.singleReturn(new StringBuilder().append("select loan_deal_id from cr_loan_dtl where loan_id='").append(loanId).append("' ").toString());
          int count = Integer.parseInt(ConnectionDAO.singleReturn(new StringBuilder().append("select count(REC_STATUS) from cr_loan_dtl where loan_deal_id='").append(loanDealId).append("' and rec_status not in('C','X','L') ").toString()));
          logger.info(new StringBuilder().append("noc count-->").append(count).toString());
          if (count > 0) {
            request.setAttribute("nocFail", "nocFail");
            return mapping.findForward("nocReportValidation");
          }
          reporttype = "P";
        }

        if ((!reportName.equalsIgnoreCase("EMI_DUE_Report")) && (!reportName.equalsIgnoreCase("ACH_TRANSACTION")) && (CommonFunction.checkNull(p_loan_no).trim().length() != 0))
        {
          p_loan_no = loan_NO;
        }

        if (reportName.equalsIgnoreCase("billed_EMIs_report"))
        {
          p_loan_id = p_loan_from;
          if (p_loan_no.trim().length() == 0) {
            p_loan_no = "All";
          }

          if (!installmentDate.trim().equals(""))
            p_billing_date = installmentDate;
        }
        if (reportName.equals("Repayment_Schedule_for_Partners"))
        {
          ArrayList in = new ArrayList();
          ArrayList out = new ArrayList();
          ArrayList outMessages = new ArrayList();
          String s1 = "";
          String s2 = "";
          in.add(partnerId);
          in.add(loanID);
          in.add(userID);
          out.add(s1);
          out.add(s2);
          logger.info(new StringBuilder().append(in).append(",").append(out).toString());
          outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("CONSORTIUM_REPAYMENT_SCHEDULE", in, out);
          s1 = CommonFunction.checkNull(outMessages.get(0));
          s2 = CommonFunction.checkNull(outMessages.get(1));
          logger.info(new StringBuilder().append("after calling of CONSORTIUM_REPAYMENT_SCHEDULE S1 and S2 ").append(s1).append("  ").append(s2).toString());
          p_loan_id = loanID;
          logger.info(new StringBuilder().append(p_loan_id).append(" loan _Id").toString());
        }

        if (reportName.equals("Partners_Payout_Report"))
        {
          as_on_date = dmyToYMD(asOnDate);
          if (!partnerType.equalsIgnoreCase("All")) {
            String[] ss = partnerId.split("\\|");
            partnerId = "";
            for (int i = 0; i < ss.length; i++) {
              partnerId = new StringBuilder().append(partnerId).append(ss[i]).append(",").toString();
            }
            partnerId = partnerId.substring(0, partnerId.length() - 1);
          } else {
            partnerId = "All";
          }
        }
        String bnk_acc = "";

        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("drawing_power_statement"))
        {
          logger.info(new StringBuilder().append("Report name is:").append(reportName).toString());
          logger.info(new StringBuilder().append("Bank id is:").append(bank_id).toString());
          logger.info(new StringBuilder().append("From date is:").append(sdfromDate).toString());
          logger.info(new StringBuilder().append("To date is:").append(sdToDate).toString());
          logger.info(new StringBuilder().append("Bank Account is:").append(bankAccount).toString());
          p_from_date = sdfromDate;
          p_to_date = sdToDate;
          bnk_acc = bankAccount;
        }

        if ((reportName.equals("Booking_Reports_For_Partners")) || (reportName.equals("Cumulative_RSP_Report_for_Payment")))
        {
          fromDate1 = dmyToYMD(fromDate);
          toDate1 = dmyToYMD(toDate);
          if (!partnerType.equalsIgnoreCase("All")) {
            String[] ss = partnerId.split("\\|");
            partnerId = "";
            for (int i = 0; i < ss.length; i++) {
              partnerId = new StringBuilder().append(partnerId).append(ss[i]).append(",").toString();
            }
            partnerId = partnerId.substring(0, partnerId.length() - 1);
          } else {
            partnerId = "All";
          }

        }

        if (reportName.equalsIgnoreCase("GST_Reports"))
        {
          logger.info("Inside GST_Reports");
          p_branch_type = af.getBranch4();
          p_branch_id = af.getLbxBranchId();
          loanType = af.getLoanno();
          p_loan_from = af.getLbx_loan_from_id();
          p_loan_to = af.getLbx_loan_to_id();
          p_fromDate = CommonFunction.changeFormat(af.getFromDate());
          p_toDate = CommonFunction.changeFormat(af.getToDate());

          if (reportSubType.equalsIgnoreCase("TA")) {
            reportName = "Tax_Invoice_report";
            p_record_type = "TAX INVOICE";
          }
          if (reportSubType.equalsIgnoreCase("CN")) {
            reportName = "Tax_Invoice_report";
            p_record_type = "CREDIT NOTE";
          }
          if (reportSubType.equalsIgnoreCase("BOS")) {
            reportName = "Tax_Invoice_report";
            p_record_type = "BILL OF SUPPLY";
          }
          if (reportSubType.equalsIgnoreCase("DN")) {
            reportName = "Tax_Invoice_report";
            p_record_type = "DEBIT NOTE";
          }
          if (reportSubType.equalsIgnoreCase("ARAP")) {
            reportName = "Tax_Invoice_report";
            p_record_type = "ARAP_TAX_INVOICE_NO";
          }

        }

        if (reportName.equalsIgnoreCase("PDC_Replinishment_letter"))
        {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(nhb_date);
          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);
          String source = request.getParameter("source");
          String reportPath = "/reports/";
          if (dbType.equalsIgnoreCase("MSSQL"))
          {
            reportPath = new StringBuilder().append(reportPath).append("MSSQLREPORTS/").toString();
          }
          else
          {
            reportPath = new StringBuilder().append(reportPath).append("MYSQLREPORTS/").toString();
          }
          String path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
          String rptPath = ConnectionDAO.singleReturn(path);
          String filename = new File(rptPath).getName();
          logger.info(new StringBuilder().append("filename--->").append(filename).toString());

          File file = new File(rptPath);
          File[] files = file.listFiles();
          for (File f : files)
          {
            if ((f.isFile()) && (f.exists()))
            {
              f.delete();
              logger.info("successfully deleted");
            }
            else
            {
              logger.info("cant delete a file due to open or error");
            }
          }

          StatementOfAccountInBulkProcessAction soaBulkReport = new StatementOfAccountInBulkProcessAction();
          logger.info(new StringBuilder().append("LOAN NO IN PDC EXHAUST").append(p_loan_id).append(" And BRANCH ID IS ==").append(p_branch_id).append(" And Product ID==").append(products).append(" and fromDate=").append(fromDate).append(" and toDate=").append(toDate).toString());
          String[] loanArr = soaBulkReport.getLoanList(p_loan_id, p_branch_id, products, fromDate, toDate, reportName, loanFrom, loanTo);

          String ext = "";
          int intLen = 0;
          if (reporttype.equalsIgnoreCase("P"))
            ext = "pdf";
          else if (reporttype.equalsIgnoreCase("E"))
            ext = "xls";
          else if (reporttype.equalsIgnoreCase("H"))
            ext = "html";
          if (loanArr != null)
          {
            intLen = loanArr.length;
            logger.info(new StringBuilder().append("Size of loan list  ================================").append(loanArr.length).toString());
            logger.info(new StringBuilder().append("loanArray.size()==").append(intLen).toString());
            String[] fileArray = new String[intLen];
            for (int i = 0; i < intLen; i++)
            {
              String strTemp = loanArr[i];
              p_loan_id = strTemp.trim();
              logger.info(new StringBuilder().append("Token value : -->").append(strTemp).toString());
              fileArray[i] = new StringBuilder().append(rptPath).append("/").append(p_loan_id).append("_").append(userID).append("_").append(p_business_date).append(".").append(ext).toString();
              InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());

              String q1 = "";

              query = new StringBuilder();
              q1 = new StringBuilder().append("select max(PDC_INSTRUMENT_DATE) from cr_pdc_instrument_dtl where pdc_loan_id='").append(p_loan_id).append("' and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q')").toString();
              String r1 = ConnectionDAO.singleReturn(q1);
              if (!CommonFunction.checkNull(r1).equalsIgnoreCase("")) {
                query.append("SELECT distinct CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
                query.append("concat(if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
                query.append("if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))) as addressLine1, ");
                query.append("concat(if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
                query.append("if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(TRIM(BOTH ',' from CDM1.DISTRICT_DESC),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))) as addressLine2, ");
                query.append("concat( ");
                query.append("if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(CSM1.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'- ')), ");
                query.append("if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.PINCODE),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
                query.append(") as addressLine3, ");
                query.append("if(CAM.PRIMARY_PHONE is null,if(CAM.ALTERNATE_PHONE is null,' ',concat(concat(CAM.ALTERNATE_PHONE,' '))), ");
                query.append("if(CAM.ALTERNATE_PHONE is null,concat(concat(CAM.PRIMARY_PHONE,' ')),concat( ");
                query.append("concat(concat(CAM.PRIMARY_PHONE,','),concat(CAM.ALTERNATE_PHONE,' '))))) as phone, ");
                query.append("ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE1),', '),'')as COMP_ADDRESS_LINE1,ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE2),', '),'')as COMP_ADDRESS_LINE2, ");
                query.append("ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE3),', '),'')as COMP_ADDRESS_LINE3,ifnull(concat(com.CITY,' - '),'') as comp_city,ifnull(concat(COM.PINCODE,','),'')as comp_PINCODE,ifnull(CCM2.COUNTRY_DESC,'') as comp_country,com.CIN,ifnull(COM.website,' ') as website,ifnull(com.email,' ')as email,ifnull(com.FAX,'')as FAX,ifnull(com.PHONE_NO,'')as PHONE_NO ");
                query.append(",(select Date_format(max(PDC_INSTRUMENT_DATE),' %M-%y') from cr_pdc_instrument_dtl where pdc_loan_id=CLD.LOAN_ID  and PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q') ) as PDC_MONTH,ifnull(com.COMPANY_SHORT_CODE,'') as COMPANY_SHORT_CODE,(SELECT DATE_FORMAT(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE' )AS lod_date1 ");
                query.append("FROM CR_LOAN_DTL CLD ");
                query.append(new StringBuilder().append("join cr_pdc_instrument_dtl PDC on pdc.pdc_loan_id=CLD.LOAN_ID and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q') and PDC_INSTRUMENT_DATE='").append(r1).append("' ").toString());
                query.append("left JOIN GCD_CUSTOMER_M GCD ON(CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID) ");
                query.append("left join com_address_m CAM on CAM.BPID=GCD.CUSTOMER_ID and COMMUNICATION_ADDRESS='Y' ");
                query.append("left join com_district_m CDM1 on (CAM.DISTRICT=CDM1.DISTRICT_ID) ");
                query.append("left join com_state_m CSM1 on (CAM.STATE=CSM1.STATE_ID) ");
                query.append("left join com_country_m CCM1 on (CAM.COUNTRY=CCM1.COUNTRY_ID) ");
                query.append("left join com_company_m com on true ");
                query.append("left join com_country_m CCM2 on (com.COUNTRY=CCM2.COUNTRY_ID) ");
                query.append(new StringBuilder().append("where 1=1 and CLD.loan_id='").append(p_loan_id).append("' and PDC_INSTRUMENT_DATE<='").append(fromDate).append("'  ").toString());
                logger.info(new StringBuilder().append("PDC Replinishment letter Query  :  ").append(query.toString()).toString());
              }
              else
              {
                hashMap.clear();
                request.setAttribute("error", "No Record found!!!");

                int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

                ArrayList sponsor1 = dao.getSponsorCode();
                ArrayList financeYear = dao.getfinanceYear();
                String maxDate = dao.getMaxDefaultDate();
                request.setAttribute("maxDate", maxDate);
                request.setAttribute("financeYear", financeYear);

                request.setAttribute("sponsorlist", sponsor1);
                DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

                String dateRengeLimit = dao.getDateRangeLimit();
                request.setAttribute("dateRengeLimit", dateRengeLimit);
                String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
                request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
                ArrayList list1 = dao.getReportFormat();
                request.setAttribute("list", list1);
                saveToken(request);
                ddao = null;
                return mapping.findForward("errorInProcedure");
              }

              hashMap.put("p_company_name", p_company_name);
              hashMap.put("p_printed_date", p_business_date);
              hashMap.put("p_user_id", userId);
              hashMap.put("p_printed_by", new StringBuilder().append(userName).append(" ").toString());
              hashMap.put("query", query.toString());
              hashMap.put("p_company_logo", p_company_logo);
              hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
              request.setAttribute("reporttype", reporttype);
              status = soaBulkReport.generateReport(request, response, query.toString(), hashMap, reportPath, reportName, reportStream, p_business_date, userID, p_loan_id,passwordPdf);

              if (i == intLen - 1) {
                request.setAttribute("msg", "S");
                hashMap.clear();
              }
            }

            soaBulkReport.methodForZip(fileArray, response, request, p_business_date, userID, reportName);
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "No Record found!!!");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);

            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

          return null;
        }

        if ((reportName.equalsIgnoreCase("Repricing_Reduction_ROI")) || (reportName.equalsIgnoreCase("Repricing_Inc_ROI")) || (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")))
        {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(nhb_date);
          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);
          String source = request.getParameter("source");
          String reportPath = "/reports/";
          if (dbType.equalsIgnoreCase("MSSQL"))
          {
            reportPath = new StringBuilder().append(reportPath).append("MSSQLREPORTS/").toString();
          }
          else
          {
            reportPath = new StringBuilder().append(reportPath).append("MYSQLREPORTS/").toString();
          }
          String path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
          String rptPath = ConnectionDAO.singleReturn(path);
          String filename = new File(rptPath).getName();
          logger.info(new StringBuilder().append("filename--->").append(filename).toString());

          File file = new File(rptPath);
          File[] files = file.listFiles();
          for (File f : files)
          {
            if ((f.isFile()) && (f.exists()))
            {
              f.delete();
              logger.info("successfully deleted");
            }
            else
            {
              logger.info("cant delete a file due to open or error");
            }
          }

          StatementOfAccountInBulkProcessAction soaBulkReport = new StatementOfAccountInBulkProcessAction();
          logger.info(new StringBuilder().append("LOAN NO IN PDC EXHAUST").append(p_loan_id).append(" And BRANCH ID IS ==").append(p_branch_id).append(" And Product ID==").append(products).append(" and fromDate=").append(fromDate).append(" and toDate=").append(toDate).toString());
          if (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")) {
            fromDate = asOnDate;
            p_as_on_date = asOnDate;
            hashMap.put("p_as_on_date", asOnDate);
          }
          String[] loanArr = soaBulkReport.getLoanList(p_loan_id, p_branch_id, products, fromDate, toDate, reportName, loanFrom, loanTo);

          String ext = "";
          int intLen = 0;
          if (reporttype.equalsIgnoreCase("P"))
            ext = "pdf";
          else if (reporttype.equalsIgnoreCase("E"))
            ext = "xls";
          else if (reporttype.equalsIgnoreCase("H"))
            ext = "html";
          if (loanArr != null)
          {
            intLen = loanArr.length;
            logger.info(new StringBuilder().append("Size of loan list  ================================").append(loanArr.length).toString());
            logger.info(new StringBuilder().append("loanArray.size()==").append(intLen).toString());
            String[] fileArray = new String[intLen];
            for (int i = 0; i < intLen; i++)
            {
              String strTemp = loanArr[i];
              p_loan_id = strTemp.trim();
              logger.info(new StringBuilder().append("Token value : -->").append(strTemp).toString());
              fileArray[i] = new StringBuilder().append(rptPath).append("/").append(p_loan_id).append("_").append(userID).append("_").append(p_business_date).append(".").append(ext).toString();
              InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());

              String q1 = "";
              if(reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy") || reportName.equalsIgnoreCase("Repricing_Reduction_ROI") || reportName.equalsIgnoreCase("Repricing_Inc_ROI")){
                query = new StringBuilder();
                q1 = new StringBuilder().append("select max(RESCH_ID) from cr_resch_dtl where resch_type in('R','F') and loan_id='").append(p_loan_id).append("' and rec_status='A' ").toString();
                String r1 = ConnectionDAO.singleReturn(q1);
                if (!CommonFunction.checkNull(r1).equalsIgnoreCase("")) {
                  query.append(" SELECT CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
                  query.append(" if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')) as addressLine, ");
                  query.append(" if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')) as addressLine1, ");
                  query.append(" concat(if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
                  query.append(" if(ifnull(CAM.LANDMARK,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CAM.LANDMARK),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))) as addressLine2, ");
                  query.append(" concat( ");
                  query.append(" if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CDM1.DISTRICT_DESC),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
                  query.append(" if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(CSM1.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
                  query.append(" if(ifnull(CCM1.country_desc,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(CCM1.country_desc,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
                  query.append(" if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
                  query.append(" (concat(ifnull(TRIM(BOTH ',' from CAM.PINCODE),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
                  query.append(" ) as addressLine3, ");
                  query.append(" if(ifnull(CAM.ALTERNATE_PHONE,'')='',CAM.PRIMARY_PHONE,concat(CAM.PRIMARY_PHONE,',',CAM.ALTERNATE_PHONE)) as phone, ");
                  query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE1),', '),'')as COMP_ADDRESS_LINE1,ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE2),', '),'')as COMP_ADDRESS_LINE2, ");
                  query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE3),', '),'')as COMP_ADDRESS_LINE3,ifnull(concat(com.CITY,' - '),'') as comp_city,ifnull(concat(COM.PINCODE,','),'')as comp_PINCODE,ifnull(CCM2.COUNTRY_DESC,'') as comp_country,com.CIN,ifnull(COM.website,' ') as website,ifnull(com.email,' ')as email,ifnull(com.FAX,'')as FAX,ifnull(com.PHONE_NO,'')as PHONE_NO ");
                  query.append(" ,ifnull(com.COMPANY_SHORT_CODE,'') as COMPANY_SHORT_CODE, ");
                  query.append(" date_format(max(CRD.RESCH_DATE),'%d %M %Y') as RateChangeDate,ifnull(round(CRD.OLD_RATE,2),'')as OLD_EFF_RATE, ");
                  query.append(new StringBuilder().append(" ifnull(round(CRD.NEW_RATE,2),'')as NEW_EFF_RATE,ifnull(max(CRDD.INSTL_NO),'')as NEW_TENURE,(select ifnull(max(INSTL_NO),'') from cr_repaysch_dtl_hst where   REPAY_HST_ID = (select max(REPAY_HST_ID)  from cr_repaysch_dtl_hst where loan_id = '").append(p_loan_id).append("') )as OLD_TENURE ").toString());
                  query.append(" ,ifnull(format(round(CRDD.INSTL_AMOUNT,2),2),'')as INSTL_AMOUNT, ");
                  query.append(" ifnull(date_format(CRD.RESCH_DATE,'%d/%m/%Y'),'')as BPLR_Change_Date,ifnull(round(CRD.NEW_BASE_RATE,2),'')as BPLR_RATE,if(CRD.NEW_MARKUP>0,concat('+',ifnull(round(CRD.NEW_MARKUP,2),'')),ifnull(round(CRD.NEW_MARKUP,2),''))as Mark_up,(SELECT DATE_FORMAT(PARAMETER_VALUE,'%d/%m/%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE' )AS p_printed_date,date_format(CLD.loan_agreement_date,'%d/%m/%Y')as loan_agreement_date ");
                  query.append(" FROM CR_LOAN_DTL CLD ");
                  query.append(new StringBuilder().append(" left join cr_resch_dtl CRD on CRD.LOAN_ID=CLD.LOAN_ID and  CRD.resch_id=(select max(resch_id) from cr_resch_dtl where resch_type in ('R','F') and loan_id='").append(p_loan_id).append("') ").toString());
					if(reportName.equalsIgnoreCase("Repricing_Reduction_ROI")){
						query.append(" and CRD.old_rate > CRD.new_rate");	
					}else if(reportName.equalsIgnoreCase("Repricing_Inc_ROI")){
						query.append(" and CRD.old_rate <= CRD.new_rate");	
					}else{
					query.append(" and CRD.old_rate <> CRD.new_rate");
					}
                  query.append(" left join cr_repaysch_dtl CRDD on CRDD.LOAN_ID=CRD.LOAN_ID and  CRDD.INSTL_no<>0  ");
                  query.append(" left JOIN GCD_CUSTOMER_M GCD ON(CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID) ");
                  query.append(" left join com_address_m CAM on CAM.BPID=GCD.CUSTOMER_ID and COMMUNICATION_ADDRESS='Y' ");
                  query.append(" left join com_district_m CDM1 on (CAM.DISTRICT=CDM1.DISTRICT_ID) ");
                  query.append(" left join com_state_m CSM1 on (CAM.STATE=CSM1.STATE_ID) ");
                  query.append(" left join com_country_m CCM1 on (CAM.COUNTRY=CCM1.COUNTRY_ID) ");
                  query.append(" left join com_company_m com on true ");
                  query.append(" left join com_country_m CCM2 on (com.COUNTRY=CCM2.COUNTRY_ID) ");
                  query.append(new StringBuilder().append(" where 1=1 and CLD.loan_id='").append(p_loan_id).append("' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')>='").append(fromDate).append("' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')<='").append(toDate).append("' ").toString());
                  logger.info(new StringBuilder().append("Repricing Letter Query  :  ").append(query.toString()).toString());
                  StringBuilder subquery3 = new StringBuilder();
                  subquery3.append("select ifnull(CLD.LOAN_NO,'')LOAN_NO,CLD.LOAN_id,ifnull(concat(GCM.CUSTOMER_NAME,' '),' ')as CUSTOMER_NAME,");
                  subquery3.append("CRD.INSTL_NO,CRD.INSTL_DATE,CRD.PRIN_OS,CRD.INSTL_AMOUNT EMI,CRD.PRIN_COMP,CRD.INT_COMP, ");
                  subquery3.append("ifnull(DATE_FORMAT(mst.PARAMETER_VALUE,'%d/%m/%Y'),'') as b_date ");
                  subquery3.append("from cr_loan_dtl CLD  ");
                  subquery3.append("join parameter_mst mst on 1=1 and mst.PARAMETER_KEY='BUSINESS_DATE' ");
                  subquery3.append("join gcd_customer_m GCM on(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID) ");
                  subquery3.append("join cr_repaysch_dtl CRD on (CLD.LOAN_ID=CRD.LOAN_ID) ");
                  subquery3.append(new StringBuilder().append(" where CLD.loan_id='").append(p_loan_id).append("' ").toString());
                  logger.info(new StringBuilder().append("Repricing Letter Query Repay Sch :  ").append(subquery3.toString()).toString());
                  hashMap.put("query", query.toString());
                  hashMap.put("subQuery3", subquery3.toString());
                }
                hashMap.put("p_company_name", p_company_name);
                hashMap.put("p_printed_date", p_business_date);
                hashMap.put("p_user_id", userId);
                hashMap.put("p_loan_id", p_loan_id);
                hashMap.put("p_printed_by", new StringBuilder().append(userName).append(" ").toString());
              //hashMap.put("query", query.toString());
              //  hashMap.put("subQuery3", subquery3.toString()); 
                hashMap.put("p_company_logo", p_company_logo);
                hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
                request.setAttribute("reporttype", reporttype);
                status = soaBulkReport.generateReport(request, response, query.toString(), hashMap, reportPath, reportName, reportStream, p_business_date, userID, p_loan_id,passwordPdf);
              }
              else
              {
                hashMap.clear();
                request.setAttribute("error", "No Record found!!!");

                int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

                ArrayList sponsor1 = dao.getSponsorCode();
                ArrayList financeYear = dao.getfinanceYear();
                String maxDate = dao.getMaxDefaultDate();
                request.setAttribute("maxDate", maxDate);
                request.setAttribute("financeYear", financeYear);

                request.setAttribute("sponsorlist", sponsor1);
                DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

                String dateRengeLimit = dao.getDateRangeLimit();
                request.setAttribute("dateRengeLimit", dateRengeLimit);
                String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
                request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
                ArrayList list1 = dao.getReportFormat();
                request.setAttribute("list", list1);
                saveToken(request);
                ddao = null;
                return mapping.findForward("errorInProcedure");
              }

              if (i == intLen - 1) {
                request.setAttribute("msg", "S");
                hashMap.clear();
              }
            }

            soaBulkReport.methodForZip(fileArray, response, request, p_business_date, userID, reportName);
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "No Record found!!!");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);

            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

          return null;
        }

        if (reportName.equalsIgnoreCase("Yearly_Interest_Certificate"))
        {
			ArrayList result=new ArrayList();
			p_business_date=CommonFunction.changeFormat(nhb_date);
        	fromDate=CommonFunction.changeFormat(fromDate);
        	toDate=CommonFunction.changeFormat(toDate);
			String source=request.getParameter("source");
			String reportPath="/reports"+File.separator;
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				reportPath=reportPath+"MSSQLREPORTS"+File.separator;
			}
			else
			{
				reportPath=reportPath+"MYSQLREPORTS"+File.separator;	
			}
			String path="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
			String rptPath=ConnectionDAO.singleReturn(path);
			String filename = new File(rptPath).getName();
			logger.info("filename--->"+filename);
			//Code for delete starts
	
	       // String path="D:\test"; 
	        File file = new File(rptPath);
	        File[] files = file.listFiles(); 
	        for (File f:files) 
	    	{
	    		if (f.isFile() && f.exists()) 
				{ 	
					f.delete();
					logger.info("successfully deleted");
				}
	    		else
				{
					logger.info("cant delete a file due to open or error");
				} 
	    	}
			//Code for delete ends
			StatementOfAccountInBulkProcessAction soaBulkReport= new StatementOfAccountInBulkProcessAction();
			logger.info("LOAN NO IN Yearly_Interest_Certificate"+p_loan_id+" And BRANCH ID IS =="+p_branch_id+" And Product ID=="+products+" and fromDate="+fromInterestDate+" and toDate="+toInterestDate );					
			String[] loanArr = soaBulkReport.getLoanList(p_loan_id, p_branch_id, products,fromInterestDate,toInterestDate,reportName,loanFrom,loanTo);
			// HIna changes here
			String ext="";
			int intLen =0;
			if(reporttype.equalsIgnoreCase("P"))
				ext="pdf";
			else if(reporttype.equalsIgnoreCase("E"))
				ext="xls";
			else if(reporttype.equalsIgnoreCase("H"))
				ext="html";
			if (loanArr!=null)
			{
				intLen = loanArr.length;
				logger.info("Size of loan list  ================================"+loanArr.length);
				logger.info("loanArray.size()=="+intLen); 
				String[] fileArray = new String[intLen];
				for(int i=0; i < intLen; i++)
				{
					String strTemp = loanArr[i];
					p_loan_id=strTemp.trim();							
					logger.info("Token value : -->"+strTemp);
					fileArray[i]=rptPath+"/"+p_loan_id+"_"+userID+"_"+p_business_date+"."+ext;
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
										    	
					
					String q1="";
						
							query=new StringBuilder();
						
							
						/*	
							query.append("SELECT CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
							query.append("concat(if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
							query.append("if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace  ");
							query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))) as addressLine1, ");
							query.append("concat(if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
							query.append("if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							query.append("(concat(ifnull(TRIM(BOTH ',' from CDM1.DISTRICT_DESC),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))) as addressLine2, ");
							query.append("concat( ");
							query.append("if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							query.append("(concat(ifnull(CSM1.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'- ')), ");
							query.append("if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.PINCODE),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
							query.append(") as addressLine3, ");
							query.append("if(CAM.PRIMARY_PHONE is null,if(CAM.ALTERNATE_PHONE is null,' ',concat(concat(CAM.ALTERNATE_PHONE,' '))), ");
							query.append("if(CAM.ALTERNATE_PHONE is null,concat(concat(CAM.PRIMARY_PHONE,' ')),concat( ");
							query.append("concat(concat(CAM.PRIMARY_PHONE,','),concat(CAM.ALTERNATE_PHONE,' '))))) as phone, ");*/
							query.append(" SELECT CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
							query.append(" if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE1,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))addressLine1, "); 
							query.append(" if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE2,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))addressLine2, ");
							query.append(" if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE3,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))addressLine3, ");
							query.append(" if(ifnull(CAM.landmark,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.landmark,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))landMark, ");
							query.append(" if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CSM1.STATE_DESC,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))state, ");
							query.append(" if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CDM1.DISTRICT_DESC,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))district, ");
							query.append(" if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.PINCODE,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))pincode, ");
							query.append(" if(ifnull(CAM.PRIMARY_PHONE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.PRIMARY_PHONE,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))PRIMARY_PHONE, ");
							query.append(" CP.product_desc as product_desc,CP.product_id as product_id, ");
							query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE1),', '),'')as COMP_ADDRESS_LINE1,ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE2),', '),'')as COMP_ADDRESS_LINE2, ");
							query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE3),', '),'')as COMP_ADDRESS_LINE3,ifnull(concat(com.CITY,' - '),'') as comp_city,ifnull(concat(COM.PINCODE,','),'')as comp_PINCODE,ifnull(CCM2.COUNTRY_DESC,'') as comp_country,com.CIN,ifnull(COM.website,' ') as website,ifnull(com.email,' ')as email,ifnull(com.FAX,'')as FAX,ifnull(com.PHONE_NO,'')as PHONE_NO ");
							query.append(",ifnull(com.COMPANY_SHORT_CODE,'') as COMPANY_SHORT_CODE, ");
							query.append(" REPLACE((SELECT GROUP_CONCAT(CUSTOMER_NAME,' |') FROM cr_loan_customer_role CLCR ");
							query.append(" JOIN gcd_customer_m GCM ON GCM.CUSTOMER_ID=CLCR.GCD_ID WHERE  LOAN_CUSTOMER_ROLE_TYPE='COAPPL' AND LOAN_ID=CLD.loan_id),'|','') AS COPPL,format(cld.loan_final_rate,2)as loan_final_rate,format(CLD.LOAN_LOAN_AMOUNT,2) as LOAN_LOAN_AMOUNT,CLD.loan_product as PRODUCT ");
							query.append(",DATE_FORMAT(STR_TO_DATE('"+fromInterestDate+"','%d-%m-%Y'),'%Y') as from_year , ");
							query.append(" DATE_FORMAT(STR_TO_DATE('"+toInterestDate+"' ,'%d-%m-%Y'),'%Y') as to_year, ");
							
							
							/*query.append(" format((ifnull(sum(INT_COMP_RECD),0))+(select ifnull(sum(ctd.txn_adjusted_amount),0) from cr_txnadvice_dtl CTD where CTD.LOAN_ID='"+p_loan_id+"' and ");
							query.append(" charge_code_id in('6','9') and txn_type in ('ECA','PEM') and  advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') and ");
							query.append(" advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as interest_component, ");*/
							
							query.append(" format((ifnull(sum(INT_COMP_RECD),0))+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
											+ " from cr_txnadvice_dtl CTD "
											+ " where CTD.LOAN_ID = '"+p_loan_id+"' and charge_code_id in('6','9') and txn_type in ('ECA','PEM') "
											+ " and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
											+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as interest_component,  ");
										
							
							
							/*query.append(" format(ifnull(sum(PRIN_COMP_RECD),0)+(select ifnull(sum(ctd.txn_adjusted_amount),0) from cr_txnadvice_dtl CTD where ");
							query.append(" CTD.LOAN_ID='"+p_loan_id+"' and charge_code_id in(18) and txn_type='ECA' and  advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') and ");
							query.append(" advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as  PRINCIPAL_component, ");*/
							
							query.append(" format(ifnull(sum(if(PRIN_COMP_RECD<0,0,PRIN_COMP_RECD)),0)+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
									+ " from cr_txnadvice_dtl CTD where CTD.LOAN_ID = '"+p_loan_id+"' and charge_code_id in(18) and txn_type='ECA' "
									+ " and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
									+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as PRINCIPAL_component, ");
							
							
							query.append(" (SELECT DATE_FORMAT(PARAMETER_VALUE,'%d/%m/%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE' )AS lod_date1, ");
							
							
							/*query.append(" format(ifnull(((ifnull(sum(PRIN_COMP_RECD),''))+((ifnull(sum(INT_COMP_RECD),0)+ ");
							query.append(" (select ifnull(sum(ctd.txn_adjusted_amount),0) from cr_txnadvice_dtl CTD ");
							query.append(" where CTD.LOAN_ID='"+p_loan_id+"' and ");
							query.append(" charge_code_id in('6','9','18') and txn_type in ('ECA','PEM') and ");
							query.append(" advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') and ");
							query.append(" advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y'))))),''),2)as Total ");*/
							
							
							query.append(" format(ifnull(((ifnull(sum(if(PRIN_COMP_RECD<0,0,PRIN_COMP_RECD)),''))+((ifnull(sum(INT_COMP_RECD),0)+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
									+ " from cr_txnadvice_dtl CTD where CTD.LOAN_ID = '"+p_loan_id+"'  and charge_code_id in('6','9','18') and "
									+ " txn_type in ('ECA','PEM') and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
									+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y'))))),''),2)as Total  ");
							
							
							query.append(" FROM CR_LOAN_DTL CLD ");
							query.append(" left join cr_repaysch_dtl CRD on CLD.LOAN_ID=CRD.LOAN_ID  and instl_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') and instl_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y') and instl_amount<>0  ");
							query.append(" left join cr_txnadvice_dtl CTD on CTD.LOAN_ID=CLD.loan_id and charge_code_id in ('6') and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y')  and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y') ");
							query.append(" left JOIN GCD_CUSTOMER_M GCD ON(CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID) ");
							query.append(" left join cr_product_m CP on (CLD.LOAN_PRODUCT= CP.product_id) and CP.rec_status='A' ");
							query.append(" left join com_address_m CAM on CAM.BPID=GCD.CUSTOMER_ID and COMMUNICATION_ADDRESS='Y' ");
							query.append(" left join com_district_m CDM1 on (CAM.DISTRICT=CDM1.DISTRICT_ID) ");
							query.append(" left join com_state_m CSM1 on (CAM.STATE=CSM1.STATE_ID) ");
							query.append(" left join com_country_m CCM1 on (CAM.COUNTRY=CCM1.COUNTRY_ID) ");
							query.append(" left join com_company_m com on true ");
							query.append(" left join com_country_m CCM2 on (com.COUNTRY=CCM2.COUNTRY_ID) ");
							query.append(" where 1=1 and  CLD.loan_id='"+p_loan_id+"' ");
							logger.info(" Yearly Interest Certificate Query  :  "+query.toString());
						
							StringBuilder subquery=new StringBuilder();
							subquery.append("select @a:=@a+1 as sno,cacm.PROPERTY_OWNER, ");
							subquery.append("CONCAT(concat(if(ifnull(cacm.Property_address,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.Property_address),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
							subquery.append("if(ifnull(cacm.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))), ");
							subquery.append("concat(if(ifnull(cacm.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
							subquery.append("), ");
							subquery.append("concat(if(ifnull(CDM.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(CDM.DISTRICT_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
							subquery.append("if(ifnull(CSM.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(CSM.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
							subquery.append("if(ifnull(CCM.COUNTRY_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(CCM.COUNTRY_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
							subquery.append("if(ifnull(cacm.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
							subquery.append("(concat(ifnull(cacm.PINCODE,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),' ')) ");
							subquery.append(")) as prop_address ");
							subquery.append("from (SELECT @a := 0) r,cr_asset_collateral_m cacm ");
							subquery.append("join cr_loan_collateral_m cdca on (cdca.assetid=cacm.asset_id) ");
							subquery.append("join cr_loan_dtl cdd on (cdca.Loan_id=cdd.loan_id) ");
							subquery.append("left outer join com_district_m CDM on (cacm.DISTRICT=CDM.DISTRICT_ID) ");
							subquery.append("left outer join com_state_m CSM on (cacm.STATE=CSM.STATE_ID) ");
							subquery.append("left outer join com_country_m CCM on (cacm.COUNTRY=CCM.COUNTRY_ID) ");
							subquery.append("where cdd.loan_id='"+p_loan_id+"' ");
							subquery.append("and ASSET_COLLATERAL_CLASS ='PROPERTY' order by cacm.asset_id");
							
							logger.info("Yearly Interest Certificate SubQuery  :  "+subquery.toString());
					
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_business_date);
					hashMap.put("p_user_id",userId);
					hashMap.put("p_printed_by",userName+" ");
					hashMap.put("query",query.toString());
					hashMap.put("subQuery",subquery.toString());
					hashMap.put("p_company_logo",p_company_logo);	
					hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
					request.setAttribute("reporttype",reporttype);
					
					if( CommonFunction.checkNull(source).equalsIgnoreCase("S")){
								String eventName="INTEREST_CERTIFICATE";
								new BulkEmail().sendManualMail(reportName,hashMap,p_loan_id,eventName,reportStream);
								
							}else{
					status=soaBulkReport.generateReport(request,response,query.toString(),hashMap,reportPath,reportName,reportStream,p_business_date,userID,p_loan_id,passwordPdf);
							}
					if(i==intLen-1){
						  request.setAttribute("msg","S");
						  hashMap.clear();
					}	
				} 
				if( !CommonFunction.checkNull(source).equalsIgnoreCase("S")){
				soaBulkReport.methodForZip(fileArray, response,  request, p_business_date, userID, reportName);
				}else{
				
					hashMap.clear();
					request.setAttribute("error", "Mail has been sent to primary Applicant");
					//logger.info("In Generate_SOA reports can't be generate ");		        	
					int role_id;
					role_id=Integer.parseInt(session.getAttribute("roleID").toString());
					
					ArrayList<ReportsForm> sponsor1=dao.getSponsorCode();
					ArrayList<ReportsForm> financeYear=dao.getfinanceYear();
					String maxDate=dao.getMaxDefaultDate();
					request.setAttribute("maxDate",maxDate);
					request.setAttribute("financeYear",financeYear);	
				
					request.setAttribute("sponsorlist",sponsor1);	
					DownloadDAO ddao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
					//logger.info("Implementation class: "+ddao.getClass()); 
					//String eodList=ddao.getCutoffDate(); // COMMENT BY SAORABH
					//logger.info("List Off Cut_Off_Date  :  "+eodList);
					//request.setAttribute("eodList",cutOffDate);
					String dateRengeLimit=dao.getDateRangeLimit();
					request.setAttribute("dateRengeLimit",dateRengeLimit);
					String DateRangeLimitSpecial=dao.getDateRangeLimitSpecial();
					request.setAttribute("DateRangeLimitSpecial",DateRangeLimitSpecial);
					ArrayList<ReportsForm> list1=dao.getReportFormat();
					request.setAttribute("list",list1);	
					saveToken(request);// Save Token Before Loading jsp in any case
					ddao=null;
					return mapping.findForward("errorInProcedure");
				
			}
			}
			
			else
			{
				hashMap.clear();
				request.setAttribute("error", "No Record found!!!");
				//logger.info("In Generate_SOA reports can't be generate ");		        	
				int role_id;
				role_id=Integer.parseInt(session.getAttribute("roleID").toString());
				
				ArrayList<ReportsForm> sponsor1=dao.getSponsorCode();
				ArrayList<ReportsForm> financeYear=dao.getfinanceYear();
				String maxDate=dao.getMaxDefaultDate();
				request.setAttribute("maxDate",maxDate);
				request.setAttribute("financeYear",financeYear);	
			
				request.setAttribute("sponsorlist",sponsor1);	
				DownloadDAO ddao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
				//logger.info("Implementation class: "+ddao.getClass()); 
				//String eodList=ddao.getCutoffDate(); // COMMENT BY SAORABH
				//logger.info("List Off Cut_Off_Date  :  "+eodList);
				//request.setAttribute("eodList",cutOffDate);
				String dateRengeLimit=dao.getDateRangeLimit();
				request.setAttribute("dateRengeLimit",dateRengeLimit);
				String DateRangeLimitSpecial=dao.getDateRangeLimitSpecial();
				request.setAttribute("DateRangeLimitSpecial",DateRangeLimitSpecial);
				ArrayList<ReportsForm> list1=dao.getReportFormat();
				request.setAttribute("list",list1);	
				saveToken(request);// Save Token Before Loading jsp in any case
				ddao=null;
				return mapping.findForward("errorInProcedure");
			}
			
			//download the SOA result
			return null;
		}
        if (reportName.equalsIgnoreCase("BOUNCE_LETTER")) {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(nhb_date);
          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);
          String source = request.getParameter("source");
          String reportPath = "/reports/";
          if (dbType.equalsIgnoreCase("MSSQL"))
          {
            reportPath = new StringBuilder().append(reportPath).append("MSSQLREPORTS/").toString();
          }
          else
          {
            reportPath = new StringBuilder().append(reportPath).append("MYSQLREPORTS/").toString();
          }
          String path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
          String rptPath = ConnectionDAO.singleReturn(path);
          String filename = new File(rptPath).getName();
          logger.info(new StringBuilder().append("filename--->").append(filename).toString());

          File file = new File(rptPath);
          File[] files = file.listFiles();
          for (File f : files)
          {
            if ((f.isFile()) && (f.exists()))
            {
              f.delete();
              logger.info("successfully deleted");
            }
            else
            {
              logger.info("cant delete a file due to open or error");
            }

          }

          StatementOfAccountInBulkProcessAction soaBulkReport = new StatementOfAccountInBulkProcessAction();
          logger.info(new StringBuilder().append("LOAN NO IN BOUNCE LETTER").append(p_loan_id).append(" And BRANCH ID IS ==").append(p_branch_id).append(" And Product ID==").append(products).append(" and fromDate=").append(fromDate).append(" and toDate=").append(toDate).toString());
          String[] loanArr = soaBulkReport.getLoanList(p_loan_id, p_branch_id, products, fromDate, toDate, reportName, loanFrom, loanTo);
          String FinalLoanId = "";

          String ext = "";
          int intLen = 0;
          if (reporttype.equalsIgnoreCase("P"))
            ext = "pdf";
          else if (reporttype.equalsIgnoreCase("E"))
            ext = "xls";
          else if (reporttype.equalsIgnoreCase("H"))
            ext = "html";
          if (loanArr != null)
          {
            intLen = loanArr.length;
            logger.info(new StringBuilder().append("Size of loan list  ================================").append(loanArr.length).toString());
            logger.info(new StringBuilder().append("loanArray.size()==").append(intLen).toString());
            String[] fileArray = new String[intLen];
            for (int i = 0; i < intLen; i++)
            {
              String strTemp = loanArr[i];
              p_loan_id = strTemp.trim();
              logger.info(new StringBuilder().append("Token value : -->").append(strTemp).toString());

              String companyId = "";
              if (userobj != null)
              {
                companyId = String.valueOf(userobj.getCompanyId());
                logger.info(new StringBuilder().append("companyId").append(companyId).toString());
              }

              String q1 = new StringBuilder().append("SELECT TXNID FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='").append(p_loan_id).append("' ").toString();
              String loanId = ConnectionDAO.singleReturn(q1);

              String effectiveDate = userobj.getBusinessdate();

              logger.info(new StringBuilder().append("Inside ReportDispatchAction... loan ID: ").append(loanId).append(" effectiveDate: ").append(effectiveDate).toString());

              EarlyClosureDAO earlyClosureDAO = (EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance("EARLY");
              logger.info(new StringBuilder().append("Implementation class: ").append(earlyClosureDAO.getClass().toString()).toString());

              ArrayList duesRefundsList = earlyClosureDAO.getDuesRefundsList(companyId, loanId, effectiveDate, "T", "closure");
              request.setAttribute("duesRefundsList", duesRefundsList);

              String penal_interest = ((ClosureVO)duesRefundsList.get(0)).getLppAmount();
              logger.info(new StringBuilder().append("Lpp Amount After Calling Procedure ").append(((ClosureVO)duesRefundsList.get(0)).getLppAmount().toString()).toString());

              fileArray[i] = new StringBuilder().append(rptPath).append("/").append(loanId).append("_").append(p_loan_id).append("_").append(userID).append("_").append(p_business_date).append(".").append(ext).toString();
              InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());

              query = new StringBuilder();
              query.append("SELECT Distinct CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
              query.append("concat(if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
              query.append("if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),' '))) as addressLine1, ");
              query.append("concat(if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
              query.append("if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(TRIM(BOTH ',' from CDM1.DISTRICT_DESC),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),' '))) as addressLine2, ");
              query.append("concat( ");
              query.append("if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(CSM1.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
              query.append("if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
              query.append("(concat(ifnull(TRIM(BOTH ',' from CAM.PINCODE),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
              query.append(") as addressLine3, ");
              query.append("if(CAM.PRIMARY_PHONE is null,if(CAM.ALTERNATE_PHONE is null,' ',concat(concat(CAM.ALTERNATE_PHONE,' '))), ");
              query.append("if(CAM.ALTERNATE_PHONE is null,concat(concat(CAM.PRIMARY_PHONE,' ')),concat( ");
              query.append("concat(concat(CAM.PRIMARY_PHONE,' '),concat(CAM.ALTERNATE_PHONE,' '))))) as phone, ");
              query.append("ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE1),' '),'')as COMP_ADDRESS_LINE1,ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE2),', '),'')as COMP_ADDRESS_LINE2, ");
              query.append("ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE3),', '),'')as COMP_ADDRESS_LINE3,ifnull(concat(com.CITY,' - '),'') as comp_city,ifnull(concat(COM.PINCODE,','),'')as comp_PINCODE,ifnull(CCM2.COUNTRY_DESC,'') as comp_country,com.CIN,ifnull(COM.website,' ') as website,ifnull(com.email,' ')as email,ifnull(com.FAX,'')as FAX,ifnull(com.PHONE_NO,'')as PHONE_NO ");
              query.append(", Date_format(max(CID.INSTRUMENT_DATE),'%M/%Y') as EMImonYr,Date_format(max(CID.INSTRUMENT_DATE),'%M ') as EMI_month, ");
              query.append("Date_format(max(CID.INSTRUMENT_DATE),'%d/%m/%Y') as EMIDate ");
              query.append(",round(txn.ADVICE_AMOUNT,2) as EMI_AMOUNT1, ");
              query.append("(select ifnull(round(sum(advice_amount-(TXN_ADJUSTED_AMOUNT+amount_in_process)),2),0 ) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in ('7','8','9') and advice_amount>(TXN_ADJUSTED_AMOUNT+amount_in_process))as EMI_AMOUNT, ");
              query.append(new StringBuilder().append("((select ifnull(round(sum(advice_amount-(TXN_ADJUSTED_AMOUNT+amount_in_process)),2),0 ) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in ('7','8','9'))+(select ifnull(round(sum(advice_amount-(TXN_ADJUSTED_AMOUNT+amount_in_process)),2),0) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in('110') and ADVICE_AMOUNT>(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS) )+ (select ifnull(round(sum(advice_amount-(TXN_ADJUSTED_AMOUNT+amount_in_process)),2),0) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in('109') and ADVICE_AMOUNT>TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS )+round(replace('").append(penal_interest).append("',',',''),2)) as Total_amount, ").toString());
              query.append("(select ifnull(round(sum(ADVICE_AMOUNT),2),0) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in('110') and ADVICE_AMOUNT>TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS ) as cheque_bounce_charges, ");
              query.append(new StringBuilder().append("((select ifnull(round(sum(advice_amount-(TXN_ADJUSTED_AMOUNT+amount_in_process)),2),0) from cr_txnadvice_dtl where loan_id=CLD.loan_id and CHARGE_CODE_ID in('109') and ADVICE_AMOUNT>TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS )+round(replace('").append(penal_interest).append("',',',''),2)) as penal_interest,round(replace('").append(penal_interest).append("',',',''),2) AS P1, ").toString());
              query.append("CID.REC_STATUS,(SELECT DATE_FORMAT(PARAMETER_VALUE,'%d/%m/%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE' )AS p_date, ");
              query.append("concat('INDOCF',replace(cld.loan_no,'-','')) as bnkNo ");
              query.append("FROM CR_LOAN_DTL CLD ");
              query.append("JOIN GCD_CUSTOMER_M GCD ON(CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID) ");
              query.append("join com_address_m CAM on GCD.CUSTOMER_ID=CAM.BPID   and CAM.COMMUNICATION_ADDRESS='Y' ");
              query.append("join cr_instrument_dtl CID on CLD.LOAN_ID=CID.TXNID  and CID.TXN_TYPE='LIM' ");
              query.append(new StringBuilder().append("left join cr_instrument_update_dtl IU on CID.INSTRUMENT_ID=IU.INSTRUMENT_ID and IU.CURRENT_STATUS = 'B' and IU.NEXT_STATUS = 'X'  and IU.INSTRUMENT_DATE>=str_to_date('").append(fromDate).append("','%d-%m-%Y') and IU.INSTRUMENT_DATE<=str_to_date('").append(toDate).append("','%d-%m-%Y') ").toString());
              query.append("join cr_pmnt_dtl_hst pmnt on pmnt.INSTRUMENT_ID=CID.INSTRUMENT_ID ");
              query.append("join cr_txnadvice_dtl txn on txn.TXNADVICE_ID=pmnt.TXNADVICEID and CHARGE_CODE_ID='7' ");
              query.append("left join com_district_m CDM1 on (CAM.DISTRICT=CDM1.DISTRICT_ID) ");
              query.append("left join com_state_m CSM1 on (CAM.STATE=CSM1.STATE_ID) ");
              query.append("left join com_country_m CCM1 on (CAM.COUNTRY=CCM1.COUNTRY_ID) ");
              query.append("left join com_company_m com on true ");
              query.append("left join com_country_m CCM2 on (com.COUNTRY=CCM2.COUNTRY_ID) ");
              query.append(new StringBuilder().append("where CID.INSTRUMENT_ID='").append(p_loan_id).append("' ; ").toString());

              logger.info(new StringBuilder().append("Repricing Letter Query  :  ").append(query.toString()).toString());

              hashMap.put("p_company_name", p_company_name);
              hashMap.put("p_printed_date", p_business_date);
              hashMap.put("p_user_id", userId);
              hashMap.put("p_printed_by", new StringBuilder().append(userName).append(" ").toString());
              hashMap.put("query", query.toString());
              hashMap.put("p_company_logo", p_company_logo);
              hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
              request.setAttribute("reporttype", reporttype);
              status = soaBulkReport.generateReport(request, response, query.toString(), hashMap, reportPath, reportName, reportStream, p_business_date, userID, p_loan_id,passwordPdf);
              bouceFlag = "Y";
              if (i == intLen - 1) {
                request.setAttribute("msg", "S");
                hashMap.clear();
                bouceFlag = "Y";
              }
            }

            soaBulkReport.methodForZip(fileArray, response, request, p_business_date, userID, reportName);
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "No Record found!!!");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);

            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

          return null;
        }

        if (reportName.equalsIgnoreCase("sanction_letter")) {
          p_loan_id = p_loan_from;
          reporttype = "P";
          String prodName = ConnectionDAO.singleReturn(new StringBuilder().append("select LOAN_PRODUCT from cr_loan_dtl where LOAN_id='").append(p_loan_id).append("' ").toString());
          if (prodName.equals("LAP")) {
            reportName = "sanction_letter_after_loan_LAP";
          }
          else if (prodName.equals("BIL"))
          {
            reportName = "sanction_letter_after_loan_BIL";
          }

        }

        if (reportName.equalsIgnoreCase("Covenant_Tracking_Report"))
        {
          p_printed_date = p_business_date;
          cov_deal_id = lbxDealNoHID;
          cov_loan_id = lbxLoanNoHID;
          frequency = Sfrequency;
          covDcsn = ScovDcsn;
          logger.info(new StringBuilder().append("deal id for covenant tracking report: ").append(cov_deal_id).toString());
          logger.info(new StringBuilder().append("loan id for covenant tracking report: ").append(cov_loan_id).toString());
          logger.info(new StringBuilder().append("Frequency is ").append(frequency).toString());
          logger.info(new StringBuilder().append("Covenant Decision is ").append(covDcsn).toString());

          String ext = "";
          int intLen = 0;
          if (reporttype.equalsIgnoreCase("P"))
            ext = "pdf";
          else if (reporttype.equalsIgnoreCase("E"))
            ext = "xls";
          else if (reporttype.equalsIgnoreCase("H"))
            ext = "html";
          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);
          logger.info("Covenant_Tracking_Report");
          hashMap.put("cov_deal_id", cov_deal_id);
          hashMap.put("cov_loan_id", cov_loan_id);
          hashMap.put("frequency", frequency);
          hashMap.put("covDcsn", covDcsn);
          hashMap.put("p_printed_date", p_printed_date);
          hashMap.put("p_company_logo", p_company_logo);
        }

        if (reportName.equalsIgnoreCase("bulk_rp_repayment_calculation")) {
          ArrayList result = new ArrayList();
          p_business_date = CommonFunction.changeFormat(p_printed_date);
          logger.info(new StringBuilder().append("p_business_date-->> ").append(p_printed_date).toString());
          fromDate = CommonFunction.changeFormat(fromDate);
          toDate = CommonFunction.changeFormat(toDate);
          String source = request.getParameter("source");
          String reportPath = "/reports/";
          if (dbType.equalsIgnoreCase("MSSQL"))
          {
            reportPath = new StringBuilder().append(reportPath).append("MSSQLREPORTS/").toString();
          }
          else
          {
            reportPath = new StringBuilder().append(reportPath).append("MYSQLREPORTS/").toString();
          }
          String path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
          String rptPath = ConnectionDAO.singleReturn(path);
          String filename = new File(rptPath).getName();
          logger.info(new StringBuilder().append("filename--->").append(filename).toString());

          File file = new File(rptPath);
          File[] files = file.listFiles();
          for (File f : files)
          {
            if ((f.isFile()) && (f.exists()))
            {
              f.delete();
              logger.info("successfully deleted");
            }
            else
            {
              logger.info("cant delete a file due to open or error");
            }

          }

          StatementOfAccountInBulkProcessAction soaBulkReport = new StatementOfAccountInBulkProcessAction();
          logger.info(new StringBuilder().append("LOAN NO IN Repayment Calculation").append(p_loan_id).append(" And BRANCH ID IS ==").append(p_branch_id).append(" And Product ID==").append(products).append(" and fromDate=").append(fromDate).append(" and toDate=").append(toDate).toString());

          String[] loanArr = soaBulkReport.getLoanList(loanstatus, p_branch_id, products, fromDate, toDate, reportName, loanFrom, loanTo);

          String FinalLoanId = "";

          String ext = "";
          int intLen = 0;
          if (reporttype.equalsIgnoreCase("P"))
            ext = "pdf";
          else if (reporttype.equalsIgnoreCase("E"))
            ext = "xls";
          else if (reporttype.equalsIgnoreCase("H"))
            ext = "html";
          if (loanArr != null)
          {
            intLen = loanArr.length;
            logger.info(new StringBuilder().append("Size of loan list  ================================").append(loanArr.length).toString());
            logger.info(new StringBuilder().append("loanArray.size()==").append(intLen).toString());
            String[] fileArray = new String[intLen];
            for (int i = 0; i < intLen; i++)
            {
              String strTemp = loanArr[i];
              p_loan_id = strTemp.trim();

              String companyId = "";
              if (userobj != null)
              {
                companyId = String.valueOf(userobj.getCompanyId());
              }

              fileArray[i] = new StringBuilder().append(rptPath).append("/").append(p_loan_id).append("_").append(userID).append("_").append(p_business_date).append(".").append(ext).toString();
              InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());

              query = new StringBuilder();

              hashMap.put("p_company_name", p_company_name);
              hashMap.put("p_printed_date", p_printed_date);
              hashMap.put("p_user_id", userId);
              hashMap.put("p_printed_by", new StringBuilder().append(userName).append(" ").toString());
              hashMap.put("query", query.toString());
              hashMap.put("p_company_logo", p_company_logo);
              hashMap.put("p_loanStatus", loanstatus);
              hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
              hashMap.put("p_loan_id", p_loan_id);
              hashMap.put("p_loanStatus", loanstatus);
              request.setAttribute("reporttype", reporttype);
              logger.info(new StringBuilder().append("loan_id-------->").append(p_loan_id).toString());
              logger.info(new StringBuilder().append("LOANSTATUS--->").append(loanstatus).toString());
              status = soaBulkReport.generateReport(request, response, query.toString(), hashMap, reportPath, reportName, reportStream, p_business_date, userID, p_loan_id,passwordPdf);
              bouceFlag = "Y";
              if (i == intLen - 1) {
                request.setAttribute("msg", "S");
                hashMap.clear();
                bouceFlag = "Y";
              }
            }

            soaBulkReport.methodForZip(fileArray, response, request, p_business_date, userID, reportName);
          }
          else
          {
            hashMap.clear();
            request.setAttribute("error", "No Record found!!!");

            int role_id = Integer.parseInt(session.getAttribute("roleID").toString());

            ArrayList sponsor1 = dao.getSponsorCode();
            ArrayList financeYear = dao.getfinanceYear();
            String maxDate = dao.getMaxDefaultDate();
            request.setAttribute("maxDate", maxDate);
            request.setAttribute("financeYear", financeYear);

            request.setAttribute("sponsorlist", sponsor1);
            DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");

            String dateRengeLimit = dao.getDateRangeLimit();
            request.setAttribute("dateRengeLimit", dateRengeLimit);
            String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
            request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
            ArrayList list1 = dao.getReportFormat();
            request.setAttribute("list", list1);
            saveToken(request);
            ddao = null;
            return mapping.findForward("errorInProcedure");
          }

          return null;
        }

        if (reportName.equals("LOD_Letter")) {
          int count = Integer.parseInt(ConnectionDAO.singleReturn(new StringBuilder().append("select count(DOC_DESC) from cr_document_dtl where TXN_TYPE='DC' and Doc_type='COLLATERAL' AND DOC_ID is null    and DOC_STATUS not in('R','D','W') and txnid='").append(p_loan_id).append("' ").toString()));
          logger.info(new StringBuilder().append("count-->").append(count).toString());
          if (count > 0) {
            request.setAttribute("lodFails", "lodFails");
            return mapping.findForward("lodReportValidation");
          }

          p_loan_id = p_loan_from;
          reporttype = "P";
          p_date = p_printed_date;
          SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
          try {
            lod_date = f1.parse(p_date);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy");
          lod_date1 = f2.format(lod_date);
          logger.info(new StringBuilder().append("lod_date1   : ").append(lod_date1).toString());
          hashMap.put("lod_date1", lod_date1);
        }

        hashMap.put("p_fromDate", p_fromDate);
        hashMap.put("p_toDate", p_toDate);
        hashMap.put("p_record_type", p_record_type);
        logger.info(new StringBuilder().append("Report Query   : ").append(query.toString()).toString());
        String functionId = CommonFunction.checkNull(session.getAttribute("functionId")).trim();
        hashMap.put("rpt_loan_id", lbxLoanId);
        hashMap.put("functionId", functionId);
        hashMap.put("PRODUCT_CATEGORY", PRODUCT_CATEGORY);
        hashMap.put("productCategory", productCategory);
        hashMap.put("loanClass", loanClass);
        logger.info(new StringBuilder().append("p_branch_id  : ").append(p_branch_id).toString());

        hashMap.put("products", products);
        hashMap.put("p_user_id", userId);
        hashMap.put("p_partner_name", partnerName);

        hashMap.put("p_partnerShipType", partnerShipType);
        hashMap.put("p_partnerCode", partnerId);
        logger.info(new StringBuilder().append("p_from_date:::").append(p_from_date).append("  p_amt_type::::").append("  p_interval_freq::::").append("  p_to_date::::").append("  p_branch_type::::").append(p_branch_type).append("  p_printed_by:::").append(userName).append(" p_branch:::").append(p_branch_id).toString());
        hashMap.put("p_indian_rupee_logo", p_indian_rupee_logo);
        hashMap.put("p_company_name", p_company_name);
        hashMap.put("p_loan_id", p_loan_id);
        hashMap.put("p_actual_irr", p_actual_irr);
        hashMap.put("p_status", p_status);
        hashMap.put("status", status);
        hashMap.put("p_p_date_from", p_p_date_from);
        hashMap.put("p_p_date_to", p_p_date_to);
        hashMap.put("p_as_on_date", p_as_on_date);
        hashMap.put("p_date", installmentDate);
        hashMap.put("p_business_date", p_business_date);
        hashMap.put("p_loan_no", p_loan_no);
        hashMap.put("p_printed_by", userName);
        hashMap.put("p_branch_type", p_branch_type);
        hashMap.put("p_branch", p_branch_id);
        hashMap.put("p_loan_no_type", loanType);
        hashMap.put("p_loan_from", p_loan_from);
        hashMap.put("p_loan_to", p_loan_to);
        hashMap.put("p_deal_from", p_deal_from);
        hashMap.put("p_deal_to", p_deal_to);
        if (reportName.equalsIgnoreCase("SME_Sanction_Letter_Report_For_CM"))
        {
          hashMap.put("p_printed_date", p_printed_date);
        }
        else
        {
          hashMap.put("p_printed_date", formate(p_printed_date));
        }
        hashMap.put("p_date_from", fromDate1);
        hashMap.put("p_date_to", toDate1);
        hashMap.put("p_loan_type", loanType);
        hashMap.put("p_p_date", p_p_date);
        hashMap.put("p_printed_loan", p_printed_loan);
        hashMap.put("p_printed_deal", p_printed_deal);
        hashMap.put("sub_reports_location", sub_reports_location);
        hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
        hashMap.put("p_deal_id", p_deal_id);
        hashMap.put("p_date_format", dateFormat);
        hashMap.put("p_as_on_date", asOnDate);
        hashMap.put("asOnDate", CommonFunction.changeFormat(asOnDate));
        hashMap.put("p_bank_id", bank_id);
        hashMap.put("p_bank_name", bank_name);
        hashMap.put("p_date1", p_date1);
        hashMap.put("p_user_id", userId);
        hashMap.put("p_gaurantor", p_gaurantor);
        hashMap.put("p_billing_date", p_billing_date);
        hashMap.put("p_date_1", p_date_1);
        hashMap.put("p_date", p_printed_date);
        hashMap.put("p_address1", p_address1);
        hashMap.put("p_msg", p_msg);
        hashMap.put("p_email", p_email);
        hashMap.put("p_email1", p_email1);
        hashMap.put("p_group_id", group_name1);
        hashMap.put("P_GROUP_ID_NEW", P_GROUP_ID_NEW);
        hashMap.put("p_presentation_date", p_presentation_date);
        hashMap.put("p_pdc_flag", instrumentType);
        hashMap.put("p_Approval_date_from", p_Approval_date_from);
        hashMap.put("p_Approval_date_to", p_Approval_date_to);
        hashMap.put("p_Loan_Number", welcome_loan_id);
        hashMap.put("loan_date", loan_date);
        hashMap.put("p_coApplicant", p_coApplicant);
        hashMap.put("query", query.toString());
        hashMap.put("p_from_date", p_from_date);
        hashMap.put("p_to_date", p_to_date);
        hashMap.put("p_from_installment_date", p_from_installment_date);
        hashMap.put("p_to_installment_date", p_to_installment_date);
        hashMap.put("p_seq_no_noc", p_seq_no_noc);
        hashMap.put("logo_status", logo_status);
        hashMap.put("p_batchPurpose", batchPurpose);
        if ((reportName.equalsIgnoreCase("foreClosure_simulation_report")) || (reportName.equalsIgnoreCase("AUforeClosure_simulation_report")) || (reportName.equalsIgnoreCase("receiptMemoReport")))
        {
          hashMap.put("IS_IGNORE_PAGINATION", Boolean.valueOf(true));
        }
        else if (reporttype.trim().equalsIgnoreCase("P"))
          hashMap.put("IS_IGNORE_PAGINATION", Boolean.valueOf(false));
        else {
          hashMap.put("IS_IGNORE_PAGINATION", Boolean.valueOf(true));
        }

        hashMap.put("p_instrument_mode", instrumentMode);
        hashMap.put("as_on_date", as_on_date);
        hashMap.put("p_as_on_date_head", p_as_on_date_head);
        hashMap.put("p_OD_LPP", p_OD_LPP);
        hashMap.put("p_effective_date", installmentDate);
        hashMap.put("p_current_from_date", p_current_from_date);

        hashMap.put("p_current_to_date", p_current_to_date);

        hashMap.put("p_last_from_date", p_last_from_date);
        hashMap.put("p_last_to_date", p_last_to_date);
        hashMap.put("disbursal_id", lbxDisbursalID);
        hashMap.put("p_report_name", p_report_name);
        hashMap.put("date_D", CommonFunction.changeFormat(installmentDate));
        hashMap.put("date", CommonFunction.changeFormat(installmentDate));
        hashMap.put("p_end_date", toDate1);
        hashMap.put("p_start_date", fromDate1);
        hashMap.put("p_rec_status", rec_status);
        hashMap.put("nhb_date", CommonFunction.changeFormat(nhb_date));
        hashMap.put("ecs_from_date", CommonFunction.changeFormat(fromDate1));
        hashMap.put("ecs_to_date", CommonFunction.changeFormat(toDate1));

        hashMap.put("p_rate_type", p_rate_type);
        hashMap.put("p_headers", p_headers);
        hashMap.put("p_amt_type", p_amt_type);
        hashMap.put("p_interval_freq", p_interval_freq);

        hashMap.put("intFromDate", startDate);
        hashMap.put("intToDate", end_Date);
        hashMap.put("p_balConfFromDate", balConfFromDate);
        hashMap.put("p_balConfToDate", balConfToDate);
        hashMap.put("p_seq_no", p_seq_no);
        hashMap.put("signatory", signatory);
        hashMap.put("p_instrumentID", instrumentId);
        hashMap.put("p_imageBox", p_imageBox);
        hashMap.put("p_imageCheckbox", p_imageCheckbox);
        hashMap.put("p_received_date", p_received_date);
        hashMap.put("p_batch_no", p_batch_no);

        hashMap.put("p_bankBranch_id", p_bankBranch_id);
        hashMap.put("p_bank_id", p_bank_id);
        hashMap.put("userId", userId);
        hashMap.put("date", date);
        hashMap.put("branchId", branchId);
        hashMap.put("O_AFC_RECEIVED", afc_received);
        hashMap.put("O_CHQ_BOUNCE_CHARGE", chqBounceCharge);
        hashMap.put("O_WORKING_CAPITAL", workingCapital);
        hashMap.put("O_AFC_RATE", AFC_RATE);
        hashMap.put("O_REBATE_P_CLOSURE", REBATE_PRECLOSURE);
        hashMap.put("P_Period_date", p_month);
        hashMap.put("p_duplicateOrOriginal", duplicateOrOriginal);
        hashMap.put("bnk_acc", bankAccount);

        if (reportName.equals("receiptMemoReport"))
        {
          reportName = dao.getReceiptReportName();
          if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("")) {
            reportName = "receiptMemoReport";
          }

          Writer writer = new QRCodeWriter();
          try
          {
            instrumentId = CommonFunction.checkNull(instrumentId).trim();
            BitMatrix bitMatrix = new Code128Writer().encode(instrumentId, BarcodeFormat.CODE_128, 150, 80, null);
            String imagePath = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/barcode.gif").toString();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File(imagePath)));
            if (reporttype.trim().equalsIgnoreCase("H"))
            {
              p_company_logo = "/OmniFin/reports/logo.bmp";
              p_barcode_logo = "/OmniFin/reports/barcode.gif";
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }

        }

        if (reportName.equalsIgnoreCase("INSTRUMENT_SWAPPING_REPORT"))
        {
          reporttype = "P";
        }
        hashMap.put("p_company_logo", p_company_logo);
        hashMap.put("p_barcode_logo", p_barcode_logo);

        int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
        int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
        String module_id = session.getAttribute("moduleID").toString();

        logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
        logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
        logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

        ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
        ArrayList product = dao.getProductName();
        ArrayList loanClassification = dao.getLoanClassification();
        ArrayList sponsor1 = dao.getSponsorCode();
        ArrayList financeYear = dao.getfinanceYear();
        String maxDate = dao.getMaxDefaultDate();
        request.setAttribute("maxDate", maxDate);
        request.setAttribute("financeYear", financeYear);
        request.setAttribute("reportlist", report);
        request.setAttribute("productlist", product);
        request.setAttribute("loanClasslist", loanClassification);
        request.setAttribute("sponsorlist", sponsor1);
        DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
        logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
        String eodList = ddao.getCutoffDate();
        logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
        request.setAttribute("eodList", eodList);
        ArrayList list1 = dao.getReportFormat();
        request.setAttribute("list", list1);
        resetToken(request);
        saveToken(request);

        session.removeAttribute("reportName");
        session.removeAttribute("reporttype");
        session.removeAttribute("hashMap");
        session.removeAttribute("p_loan_id");
        session.setAttribute("reportName", reportName);
        session.setAttribute("reporttype", reporttype);
        request.setAttribute("generateReport", "Y");
        session.setAttribute("p_loan_id", p_loan_id);
        session.setAttribute("hashMap", hashMap);
        String dateRengeLimit = dao.getDateRangeLimit();
        request.setAttribute("dateRengeLimit", dateRengeLimit);
        String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
        request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
        return mapping.findForward("errorInProcedure");
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }

    }
    else
    {
      logger.info("Dublicate Request: ");

      int role_id = Integer.parseInt(session.getAttribute("roleID").toString());
      ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
      logger.info(new StringBuilder().append("Implementation class: ").append(dao.getClass()).toString());
      int function_Id = Integer.parseInt(session.getAttribute("functionId").toString());
      String module_id = session.getAttribute("moduleID").toString();

      logger.info(new StringBuilder().append("Role_id is--->").append(role_id).toString());
      logger.info(new StringBuilder().append("function_id is--->").append(function_Id).toString());
      logger.info(new StringBuilder().append("module_Id is--->").append(module_id).toString());

      ArrayList report = dao.getReportNameForCp(role_id, function_Id, module_id);
      ArrayList product = dao.getProductName();
      ArrayList loanClassification = dao.getLoanClassification();
      ArrayList sponsor1 = dao.getSponsorCode();
      ArrayList financeYear = dao.getfinanceYear();
      String maxDate = dao.getMaxDefaultDate();
      request.setAttribute("maxDate", maxDate);
      request.setAttribute("financeYear", financeYear);
      request.setAttribute("reportlist", report);
      request.setAttribute("productlist", product);
      request.setAttribute("loanClasslist", loanClassification);
      request.setAttribute("sponsorlist", sponsor1);
      DownloadDAO ddao = (DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance("DOWNLOAD");
      logger.info(new StringBuilder().append("Implementation class: ").append(ddao.getClass()).toString());
      String eodList = ddao.getCutoffDate();
      logger.info(new StringBuilder().append("List Off Cut_Off_Date  :  ").append(eodList).toString());
      request.setAttribute("eodList", eodList);
      ArrayList list1 = dao.getReportFormat();
      request.setAttribute("list", list1);
      String dateRengeLimit = dao.getDateRangeLimit();
      request.setAttribute("dateRengeLimit", dateRengeLimit);
      String DateRangeLimitSpecial = dao.getDateRangeLimitSpecial();
      request.setAttribute("DateRangeLimitSpecial", DateRangeLimitSpecial);
      saveToken(request);
      return mapping.findForward("errorInProcedure");
    }
    return null;
  }

  public static String dmyToYMD(String D)
  {
    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = format1.parse(D);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String dateString = format2.format(date);
    return dateString;
  }

  String formate(String date)
  {
    String result = "";
    int m1 = Integer.parseInt(date.substring(3, 5));
    switch (m1) {
    case 1:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Jan-").append(date.substring(6)).toString();
      break;
    case 2:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Feb-").append(date.substring(6)).toString();
      break;
    case 3:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Mar-").append(date.substring(6)).toString();
      break;
    case 4:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Apr-").append(date.substring(6)).toString();
      break;
    case 5:
      result = new StringBuilder().append(date.substring(0, 2)).append("-May-").append(date.substring(6)).toString();
      break;
    case 6:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Jun-").append(date.substring(6)).toString();
      break;
    case 7:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Jul-").append(date.substring(6)).toString();
      break;
    case 8:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Aug-").append(date.substring(6)).toString();
      break;
    case 9:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Sep-").append(date.substring(6)).toString();
      break;
    case 10:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Oct-").append(date.substring(6)).toString();
      break;
    case 11:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Nov-").append(date.substring(6)).toString();
      break;
    case 12:
      result = new StringBuilder().append(date.substring(0, 2)).append("-Dec-").append(date.substring(6)).toString();
    }
    date = null;
    return result;
  }
}