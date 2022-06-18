package com.cm.dao;

import com.cm.actionform.ExcelSheetUploadFormCM;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.upload.FormFile;

public abstract interface ExcelSheetUploadDAOCM
{
  public static final String IDENTITY = "CMESU";

  public abstract boolean uploadExcel(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String saveVoucher(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract ArrayList reportAdHoc(ExcelSheetUploadFormCM paramExcelSheetUploadFormCM, String paramString);

  public abstract boolean docUploadForExcel(HttpServletRequest paramHttpServletRequest, FormFile paramFormFile);

  public abstract boolean uploadCsv(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean uploadCsv_recoveryUpload(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String recoveryUploadSave(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean docUploadForExcelForBankUpload(HttpServletRequest paramHttpServletRequest, FormFile paramFormFile);

  public abstract boolean uploadCsv_Stationary(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String saveStationary(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean uploadCsv_Bounce(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean checkPreviousFiles(String paramString);

  public abstract String saveReceipt(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean uploadCsv_Receipt(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String saveBounce(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String AllocationProcessUploadSave(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean uploadCsv_AllocationUpload(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract boolean uploadCsv_cmsUpload(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);

  public abstract String save_CmsUpload(HttpServletRequest paramHttpServletRequest, ExcelSheetUploadFormCM paramExcelSheetUploadFormCM);
  public boolean uploadCsv_MultipleBatchUploadDownload(
			HttpServletRequest request, HttpServletResponse response,
			String strFileName, ExcelSheetUploadFormCM excelForm);
	public String saveMultipleBatch(HttpServletRequest request,
			ExcelSheetUploadFormCM excelForm);
	public boolean readExcelforGenerateBacthUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName,
			ExcelSheetUploadFormCM excelForm);
	public boolean readExcelforSPDCUpload(HttpServletRequest request,
			HttpServletResponse response, String strFileName,
			ExcelSheetUploadFormCM excelForm);
	public String saveSPDCBatch(HttpServletRequest request,
			ExcelSheetUploadFormCM excelForm);
	public boolean uploadCsv_SPDCBatchUploadDownload(HttpServletRequest request,
			HttpServletResponse response, String strFileName,
			ExcelSheetUploadFormCM excelForm);
}