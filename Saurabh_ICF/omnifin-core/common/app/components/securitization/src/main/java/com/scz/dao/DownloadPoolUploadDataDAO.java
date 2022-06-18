package com.scz.dao;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.scz.vo.DownloadPoolDataVO;

public interface DownloadPoolUploadDataDAO {
	public HSSFWorkbook downloadPoolData(DownloadPoolDataVO downloadPoolVO);

	public HSSFWorkbook downloadBankData(DownloadPoolDataVO downloadPoolVO);

	public HSSFWorkbook downloadRePayData(DownloadPoolDataVO downloadPoolVO);
}
