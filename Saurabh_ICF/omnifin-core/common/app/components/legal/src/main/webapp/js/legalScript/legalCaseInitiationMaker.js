function saveLegalCaseInitiationMaker(){
	
	DisButClass.prototype.DisButMethod();
	//var caseTypeDesc = document.getElementById("caseTypeDesc");
	var lbxCaseTypeCode = document.getElementById("lbxCaseTypeCode");
	var caseTypeButton = document.getElementById("caseTypeButton");
	var initMakerImemo = document.getElementById("initMakerImemo");
	var reasonDesc= document.getElementById("reasonDesc");
	var lbxReasonId= document.getElementById("lbxReasonId");
	var reasonButton= document.getElementById("reasonButton");
	var initMakerRemarks= document.getElementById("initMakerRemarks");
	
	 if(trim(lbxCaseTypeCode.value)=='' || trim(reasonDesc.value)=='' ||trim(lbxReasonId.value)=='' || trim(initMakerRemarks.value)==''){
		 var msg= '';
 		
		 
		 if( trim(lbxCaseTypeCode.value) == '')
	 			msg += '* Case Type is required.\n';
 		
 		if(trim(reasonDesc.value) == '' || trim(lbxReasonId.value) == '')
 			msg += '* Reason is required.\n';
 		
 		if(trim(initMakerRemarks.value) == '')
 			msg += '* Remarks are required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("Case")){
 			caseTypeButton.focus();
 		}else if(msg.match("Reason")){
 			reasonButton.focus();
 		}
 		else if(msg.match("Remarks")){
 			initMakerRemarks.focus();
 		}
 		
 		
		DisButClass.prototype.EnbButMethod();
 		//document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else {
		 	
			document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=saveLegalCaseInitiationMaker";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalCaseInitiationMakerForm").submit();
			return true;
	}
	
}




/*function fnSearchCaseType(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=searchLegalCaseInitiationMaker&searchFlag=Y";
	if(document.getElementById("lbxLoanId").value=='')
	{
		alert(val);
		document.getElementById("loanNoButton").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCaseInitiationMakerForm").submit();
	return true;
	}
} */


function fnSearchCaseType1(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCaseFileMakerForm").action="legalCaseFileMakerDispatch.do?method=searchLegalCaseFileMaker&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
		//alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCaseFileMakerForm").submit();
		
	return true;
	}
}

function fnSearchCaseTypeInitiatioan(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=searchLegalCaseInitiationMaker&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
		//alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCaseInitiationMakerForm").submit();
		
	return true;
	}
}



function fnSearchCaseTypeAuthor(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCaseFileAuthorForm").action="legalCaseFileAuthorDispatch.do?method=searchLegalCaseFileAuthor&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
		//alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCaseFileAuthorForm").submit();
		
	return true;
	}
}



function fnSearchLegalCourt(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCourtProcessingMakerForm").action="legalCourtProcessingMakerDispatch.do?method=searchLegalCourtProcessingMaker&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
		//alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCourtProcessingMakerForm").submit();
		
	return true;
	}
}

function fnSearchLegalCourtAuthor(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCourtProcessingMakerForm").action="legalCourtProcessingAuthorDispatch.do?method=searchLegalCourtProcessingAuthor&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
	//	alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCourtProcessingMakerForm").submit();
		
	return true;
	}
}


function fnSearchLegalReOpen(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("legalCourtProcessingMakerForm").action="assignRejectCaseDispatchAction.do?method=searchAssignRejectCaseData&searchFlag=Y";
	
	
	if(document.getElementById("lbxLoanId").value=='')
	{
		//alert(val);
		//document.getElementById("loanNoButton").focus();
		//DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		//return false;
		alert("Please enter Loan Number ");
			DisButClass.prototype.EnbButMethod();
			return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalCourtProcessingMakerForm").submit();
		
	return true;
	}
}
