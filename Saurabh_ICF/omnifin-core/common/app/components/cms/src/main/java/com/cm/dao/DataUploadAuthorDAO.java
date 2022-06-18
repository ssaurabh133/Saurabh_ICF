package com.cm.dao;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.cm.vo.DataUploadVO;

public interface DataUploadAuthorDAO {

ArrayList<DataUploadVO> searchUplodedDataList(DataUploadVO vo);

boolean ftpData(String FileNameAll);
boolean moveFileToAuthorFold(String FileNameAll);

public void callJobManually() throws Exception;

long searchCheckSumTable(String fileNameAll);
void updateFlag(String fileNameAll,String authorRemarks);
void updateRejectFlag(String fileNameAll,String authorRemarks);
void updateSendBackFlag(String fileNameAll,String authorRemarks);
ArrayList<String> searcProcessData(DataUploadVO vo);
boolean checkDuplicateFile(String fileName);
boolean docUpload(HttpServletRequest request, FormFile myFile);
public  long calculateCheckSum(String fileName) ;
public  boolean insertImportMannualFileDtl(HttpServletRequest request,FormFile myFile, long checkSumValue,int row_count,double total_txn_amount,DataUploadVO vo);
public  Vector readFile(String fileName) ;
public  double read(Vector dataHolder);
boolean forwardFile(String fileName);
boolean deleteFile(String fileName);
boolean removeFile(String fileName);

public ArrayList<DataUploadVO> fetchErrorLog();
}