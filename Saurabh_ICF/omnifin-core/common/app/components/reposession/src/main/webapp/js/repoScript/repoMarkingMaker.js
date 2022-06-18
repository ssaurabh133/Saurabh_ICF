function newAddRepoMarkingMaker(){
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("repoMarkingMakerForm").action=sourcepath+"/repoMarkingMakerDispatch.do?method=openAddRepoMarkingMaker";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoMarkingMakerForm").submit();
	return true;
}

function saveRepoMarkingMaker(){
	
	DisButClass.prototype.DisButMethod();
	
	var loanNo = document.getElementById("loanNo");
	var loanId = document.getElementById("loanId");
	var loanNoButton = document.getElementById("loanNoButton");
	
	var agency = document.getElementById("agency");
	var lbxAgencyId = document.getElementById("lbxAgencyId");
	var agencyButton = document.getElementById("agencyButton");
	
	var reasonDesc= document.getElementById("reasonDesc");
	var lbxReasonId= document.getElementById("lbxReasonId");
	var reasonButton= document.getElementById("reasonButton");
	
	var remarks= document.getElementById("remarks");
	
	 if(trim(loanNo.value)=='' || trim(loanId.value)=='' || trim(agency.value)=='' || trim(lbxAgencyId.value)=='' || trim(reasonDesc.value)=='' || trim(lbxReasonId.value)=='' || trim(remarks.value)==''){
		 
		 var msg= '';
		 
		 if(trim(loanNo.value) == '' || trim(loanId.value) == '' )
		 		msg += '* Loan No is required.\n';
		 
		 if(trim(reasonDesc.value) == '' || trim(lbxReasonId.value) == '' )
	 		msg += '* Reason is required.\n';
		 
		if(trim(remarks.value) == '')
 			msg += '* Remarks are required.\n';
	 		
		 if(trim(agency.value) == '' || trim(lbxAgencyId.value) == '' )
	 		msg += '* Agency is required.\n';
 		
 	
 		alert(msg);
 		
 		if(msg.match("Loan")){
 			loanNoButton.focus();
 		}
 		else if(msg.match("Reason")){
 			reasonButton.focus();
 		}
 		else if(msg.match("Remarks")){
 			remarks.focus();
 		}
 		else if(msg.match("Agency")){
 			agencyButton.focus();
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }else {
		 	
			document.getElementById("repoMarkingMakerForm").action="repoMarkingMakerDispatch.do?method=saveRepoMarkingMaker";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("repoMarkingMakerForm").submit();
			return true;
	}
	
}


function fnSearchRepoMarkingMaker(val){
	
	DisButClass.prototype.DisButMethod();
	
	document.getElementById("repoMarkingMakerForm").action="repoMarkingMakerDispatch.do?method=searchRepoMarkingMaker";
	
	if(document.getElementById("lbxLoanId").value=='' && document.getElementById("lbxProductSearchID").value=='')
	{
		alert(val);
		document.getElementById("loanNoButton").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("repoMarkingMakerForm").submit();
	return true;
	}
}

function fnUpdateRepoMarkingMaker(ufFlag){
	

	var agency = document.getElementById("agency");
	var lbxAgencyId = document.getElementById("lbxAgencyId");
	var agencyButton = document.getElementById("agencyButton");
	var reasonDesc= document.getElementById("reasonDesc");
	var lbxReasonId= document.getElementById("lbxReasonId");
	var reasonButton= document.getElementById("reasonButton");
	var remarks= document.getElementById("remarks");

	
	//for deciding update and forward
	document.getElementById("updateForwardFlag").value=ufFlag;
	
	if(trim(agency.value)=='' || trim(lbxAgencyId.value)=='' || trim(reasonDesc.value)=='' || trim(lbxReasonId.value)=='' || trim(remarks.value)==''){
		 
		 var msg= '';
		 
		 
		 if(trim(reasonDesc.value) == '' || trim(lbxReasonId.value) == '' )
	 		msg += '* Reason is required.\n';
		 
		if(trim(remarks.value) == '')
			msg += '* Remarks are required.\n';
	 		
		 if(trim(agency.value) == '' || trim(lbxAgencyId.value) == '' )
	 		msg += '* Agency is required.\n';
		
	
		alert(msg);
		
		
		if(msg.match("Reason")){
			reasonButton.focus();
		}
		else if(msg.match("Remarks")){
			remarks.focus();
		}
		else if(msg.match("Agency")){
			agencyButton.focus();
		}
		
		DisButClass.prototype.EnbButMethod();
		return false;
	 
	 }
	
		document.getElementById("repoMarkingMakerForm").action="repoMarkingMakerDispatch.do?method=updateRepoMarkingMaker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("repoMarkingMakerForm").submit();
}
