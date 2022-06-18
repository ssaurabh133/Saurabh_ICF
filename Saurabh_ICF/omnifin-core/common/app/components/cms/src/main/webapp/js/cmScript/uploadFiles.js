

function startProcessManual()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("manualUpload").action=sourcepath+"/manualAdviceUploadStart.do?method=startProcess";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("manualUpload").submit();
		 	return true;
	}
}

function errorLogDownloadForManual()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("manualUpload").action=sourcepath+"/manualAdviceUploadStart.do?method=errorLogDownload";
 	document.getElementById("manualUpload").submit();
 	return true; 		
}

function startProcessNHB()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("nhbUpload").action=sourcepath+"/nhbUploadStart.do?method=startProcess";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("nhbUpload").submit();
		 	return true;
	}
}

function errorLogDownloadForNHB()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("nhbUpload").action=sourcepath+"/nhbUploadStart.do?method=errorLogDownload";
 	document.getElementById("nhbUpload").submit();
 	return true; 		
}

//Himanshu Verma		Changes for loan detail upload started

function startProcessLoanDetailUpload()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("loanDetailUpload").action=sourcepath+"/loanDetailUploadStart.do?method=startProcess";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("loanDetailUpload").submit();
		 	return true;
	}
}

function errorLogDownloadForLoanDetailUpload()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("loanDetailUpload").action=sourcepath+"/loanDetailUploadStart.do?method=errorLogDownload";
	document.getElementById("loanDetailUpload").submit();
	return true; 		
}

//Himanshu Verma		Changes for loan detail upload ended


//Added by Virender
function startLosUpload()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("losUpload").action=sourcepath+"/losUploadStart.do?method=startProcess";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("losUpload").submit();
		 	return true;
	}
}

function errorLogDownloadForLosUpload()
{
	var sourcepath=document.getElementById("contextPath").value;
	var uploadFlag=document.getElementById("uploadFlag").value;
	document.getElementById("losUpload").action=sourcepath+"/losUploadStart.do?method=errorLogDownload";
 	document.getElementById("losUpload").submit();
 	return true;
}

function exlsFileDownloadForLosUpload()
{
	
	
  	var sourcepath=document.getElementById("contextPath").value;
  	document.getElementById("losUpload").action=sourcepath+"/losUploadStart.do?method=xlsFileDownload";
//	document.getElementById("processingImage").style.display = '';
	document.getElementById("losUpload").submit();
	return true;
 
}
//Virender Code End