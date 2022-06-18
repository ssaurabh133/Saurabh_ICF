function saveReopenClosedCaseData(){
	
	   DisButClass.prototype.DisButMethod();
		if(document.getElementById("comments").value=="")
		   {
			alert("* comments is required");
			document.getElementById("comments").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		   }
		else
		{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("legalReopenClosedCaseForm").action = basePath+"/legalReopenClosedCaseDispatchAction.do?method=saveReopenClosedCaseData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalReopenClosedCaseForm").submit();
		return true;
		 
		}
}
	
