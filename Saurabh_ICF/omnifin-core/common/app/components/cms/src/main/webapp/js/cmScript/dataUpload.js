function processFile(){
	var sourcepath=document.getElementById("path").value;
	var checkedChek=document.getElementsByName("chk");
	var fileChkName=document.getElementsByName("fileChkName");
	var selectDropDownArr=document.getElementsByName("selectDropDownArr");
	var authorRemarks=document.getElementsByName("authorRemarks");
	var id="";
	var fileNameCombine="";
	var selectDropDownArrvalue="";
	var count=0;
	var authorRemarksValue="";
	for(var i=0;i<checkedChek.length;i++){
		if(checkedChek[i].checked==true){
			
			id="checked";
			fileNameCombine +=fileChkName[i].value;
			authorRemarksValue=authorRemarks[i].value;
			selectDropDownArrvalue=selectDropDownArr[i].value;
			
			count++;
		}
	}
	
	if(id=="")
	 {
		 alert("Please Select At Least One File To Process");
	 	 return false;
	 }
	else if(count>1){
		 alert("Please Select Only One File To Process");
	 	 return false;
	 }else if(authorRemarksValue==""){
		 alert("Please Fill Author Remarks");
	 }
	 else
	 {	
		 if(selectDropDownArrvalue=="A"){	 
	     document.getElementById("dataUploadForm").action =sourcepath+"/mannualProcessFile.do?method=approvedFile&fileNameAll="+fileNameCombine+"&authorRemarks="+authorRemarksValue+"";
	     document.getElementById("processingImage").style.display = '';
	    document.getElementById("dataUploadForm").submit();
	 	return true;
	 	}
		 else if(selectDropDownArrvalue=="R"){
			 document.getElementById("dataUploadForm").action =sourcepath+"/mannualProcessFile.do?method=rejectFile&fileNameAll="+fileNameCombine+"&authorRemarks="+authorRemarksValue+"";
		     document.getElementById("processingImage").style.display = '';
		    document.getElementById("dataUploadForm").submit();
		 	return true;
		 }else{
			 document.getElementById("dataUploadForm").action =sourcepath+"/mannualProcessFile.do?method=sendBackFile&fileNameAll="+fileNameCombine+"&authorRemarks="+authorRemarksValue+"";
		     document.getElementById("processingImage").style.display = '';
		    document.getElementById("dataUploadForm").submit();
		 	return true;
		 }
	 }
}

function forwardFile(){
	var sourcepath=document.getElementById("contextPath").value;
	var checkedChek=document.getElementsByName("chk");
	var fileChkName=document.getElementsByName("fileChkName");
	
	var id="";
	var fileNameCombine="";
	
	var count=0;
	
	for(var i=0;i<checkedChek.length;i++){
		if(checkedChek[i].checked==true){
			
			id="checked";
			fileNameCombine +=fileChkName[i].value;
			
			count++;
		}
	}
	
	if(id=="")
	 {
		 alert("Please Select At Least One File To Process");
	 	 return false;
	 }
	else if(count>1){
		 alert("Please Select Only One File To Process");
	 	 return false;
	 }
	 else
	 {	
		 document.getElementById("DocUpload").action =sourcepath+"/dataUploadMakerProcessAction.do?method=makerForward&fileNameAll="+fileNameCombine+"";
		// document.getElementById("DocUpload").action =sourcepath+"/mannualProcessFile.do?fileNameAll="+fileNameCombine+"";	
	     document.getElementById("processingImage").style.display = '';
	    document.getElementById("DocUpload").submit();
	 	return true;
	 	}
}

function deleteFile(){
	var sourcepath=document.getElementById("contextPath").value;
	var checkedChek=document.getElementsByName("chk");
	var fileChkName=document.getElementsByName("fileChkName");
	
	var id="";
	var fileNameCombine="";
	
	var count=0;
	
	for(var i=0;i<checkedChek.length;i++){
		if(checkedChek[i].checked==true){
			
			id="checked";
			fileNameCombine +=fileChkName[i].value;
			
			count++;
		}
	}
	
	if(id=="")
	 {
		 alert("Please Select At Least One File To Process");
	 	 return false;
	 }
	else if(count>1){
		 alert("Please Select Only One File To Process");
	 	 return false;
	 }
	 else
	 {	
		 document.getElementById("DocUpload").action =sourcepath+"/dataUploadMakerProcessAction.do?method=makerDelete&fileNameAll="+fileNameCombine+"";
		// document.getElementById("DocUpload").action =sourcepath+"/mannualProcessFile.do?fileNameAll="+fileNameCombine+"";	
	     document.getElementById("processingImage").style.display = '';
	    document.getElementById("DocUpload").submit();
	 	return true;
	 	}
}

function showErrorInProcess(){
	
	var contextPath = document.getElementById("path").value;
	//$var importId = document.getElementById("importId").value;
	//$var error_flag = document.getElementById("error_flag").value;
	// alert(contextPath);
	// $var url= contextPath+"/mannualProcessFile.do?method=showErrorInProcess&importId="+importId;
	var url= contextPath+"/mannualProcessFile.do?method=showErrorInProcess";
	// alert("url: "+url);
	// window.open(url,'View
	// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
	// scrollbars=yes");
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) {
		mywindow.focus();
		return false;
	}
	return true;
}
