package com.cm.actions;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

public class FileDownload extends DownloadAction{
 protected StreamInfo getStreamInfo(ActionMapping mapping,  ActionForm form,   HttpServletRequest request,  HttpServletResponse response) throws Exception {
	 	System.out.println("Ritesh  "+request.getParameter("fileName"));   
	 	String fileName=request.getParameter("fileName");
        String filePath=mapping.getParameter();
        response.setHeader("Content-disposition",   "attachment; filename=" + fileName);
  String contentType = "application/octet-stream";
  File file = new File(filePath+fileName);
  return new FileStreamInfo(contentType, file);
 }
}