function startProcessLead()
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
			document.getElementById("DocUpload").action=sourcepath+"/leadUploadProcessAction.do?method=startProcessLead";
			 document.getElementById("processingImage").style.display = '';
		 	document.getElementById("DocUpload").submit();
		 	return true;
	}
	DisButClass.prototype.EnbButMethod();
}
