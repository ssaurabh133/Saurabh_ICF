function validateStationaryUpload()
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

function uploadStationary()
{
	//alert("uploadStationary");
	if(validateStationaryUpload())
	{
		var contextPath =document.getElementById('contextPath').value ;
		//alert("contextPath "+contextPath);
		document.getElementById("stationaryUpload").action =contextPath+"/stationaryUploadAtCM.do?method=stationaryUpload";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("stationaryUpload").submit();
	}
}

function generateStationaryErrorLog()
{
	//alert("generateStationaryErrorLog");
	var contextPath =document.getElementById('contextPath').value ;
	//alert("contextPath "+contextPath);
	document.getElementById("stationaryUpload").action =contextPath+"/stationaryUploadAtCM.do?method=generateErrorLog";
	//alert("ok");
	document.getElementById("stationaryUpload").submit();
}