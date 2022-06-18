function uploadCSVLoanForBuyBack()
{
	if(validateDocUpload())
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("BuyBack").action =contextPath+"/buyBackProcessAction.do?method=uploadCSVForBuyBack";
		document.getElementById("BuyBack").submit();
		return true;
	}
}

function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
		DisButClass.prototype.EnbButMethod();
	    return false; 
	}
	var fup = document.getElementById('docFile');
	var file_Name = fup.value;
	var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
	if(ext == "csv" || ext == "CSV")
	{
		return true;
	} 
	else
	{
		alert("Upload CSV file only.");
		fup.focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}