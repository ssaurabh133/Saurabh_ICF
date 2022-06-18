package com.cm.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class KycUploadForm extends ActionForm 
{
	private FormFile docFile;
	
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
}

	