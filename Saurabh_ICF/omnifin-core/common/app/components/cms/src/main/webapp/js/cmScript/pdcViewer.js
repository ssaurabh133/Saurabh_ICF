function searchPdcviewer(){

	DisButClass.prototype.DisButMethod();
	if( document.getElementById("loanNo").value=="" )
	{
		alert("Please select a Loan Number");	
		DisButClass.prototype.EnbButMethod()
//		document.getElementById("loanButton").focus();
		return false;
	}	
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("PdcViewerForm").action = contextPath+"/pdcViewerSearch.do";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("PdcViewerForm").submit();
	}
	
}