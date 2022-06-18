function bulkReportGeneration()
{
	
	if(validateDocUpload())
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("bulkReportGeneration").action =contextPath+"/bulkReportGeneration.do?method=reportBulkGeneration";
	//	document.getElementById("processingImage").style.display = '';
		document.getElementById("bulkReportGeneration").submit();
	}
}
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
	    return false; 
	}
	var fup = document.getElementById('docFile');
	var file_Name = fup.value;
	var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
	if(ext == "xls" || ext == "XLS" || ext == "xlsx" || ext == "XLSX")
		return true;
	else
	{
		alert("Upload excel file only.");
		fup.value='';
		fup.focus();
		return false;
	}
}
function generateErrorLog()
{
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("bulkReportGeneration").action =contextPath+"/bulkReportGeneration.do?method=generateErrorLog";
	document.getElementById("bulkReportGeneration").submit();
}

function generateReportDesc()
{
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("bulkReportGeneration").action =contextPath+"/bulkReportGeneration.do?method=generateReportDesc";
	document.getElementById("bulkReportGeneration").submit();
}


