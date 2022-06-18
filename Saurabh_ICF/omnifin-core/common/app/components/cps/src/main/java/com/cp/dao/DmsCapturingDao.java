package com.cp.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.PartnerCaptureVO;

public interface DmsCapturingDao {

	String IDENTITY="DMS"; 

 public Map<String,String> getDMSDetails(String txnType,String txnId,String entityType,String entityId,String loginUserId, String docId);
 public ArrayList<Object> fetchUploadDocDocWise(String docId,String bpId,String bpType,String DocType,String entityId);
 public boolean  masterChildDocs(PartnerCaptureVO sh);
 public ArrayList uploadDocumentChildDatafortempTable(Object ob);
 public ArrayList uploadDocumentChildData(Object sh);
 public String Internal_ExternalDocument();
 public ArrayList getPartnerDocuments(String commonId,String stage,String bpType);
 public String getDocumentDesc(String docId);
 public ArrayList getDealHeaderForCm(String dealId);
 public ArrayList getUploadUnderwritingDataForCm(String dealId);
 public ArrayList getUploadUnderwritingDataForCmCp(String dealId);


}



