function saveKycUpload(){
	DisButClass.prototype.DisButMethod();
	var files=document.getElementById("docFile").files;
	var contextPath = document.getElementById('contextPath').value;
	
	if(files.length==0){
	    alert("Please attach a file.");
	    document.getElementById("docFile").focus();
	    return false;
	}
	if(files.length>0){
		var processingFileName=files[0].name;
		var count=processingFileName.split('.').length-1;
		if(count>1){
		alert("Please provide right file name.");
		return false;
		}
		var ext=processingFileName.substring((processingFileName.lastIndexOf("."))+1);
		console.log("ext : "+ext+" processingFileName : "+processingFileName+" files.length :"+files.length );
		if(ext =='xls'|| ext =='XLS' ||ext =='xlsx' ||ext =='csv'){
				alert("action path is going to call.");
				document.getElementById("kycUploadDynaForm").action = contextPath+"/kycUpload.do?method=uploadKycFile&processingFileName="+processingFileName;
				document.getElementById("kycUploadDynaForm").submit();
				DisButClass.prototype.EnbButMethod();
				return true;
	    }else{
	    	alert("File type is not valid please select excel.");
			document.getElementById("docFile").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
}
