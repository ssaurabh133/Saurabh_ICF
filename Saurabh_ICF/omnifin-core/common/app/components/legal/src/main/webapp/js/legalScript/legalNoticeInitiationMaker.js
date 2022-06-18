function newAddLegalNoticeInitiationMaker(){
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("legalNoticeInitiationMakerForm").action=sourcepath+"/legalNoticeInitiationMaker.do?method=openAddLegalNoticeInitiationMaker";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("legalNoticeInitiationMakerForm").submit();
	return true;
}


function saveLegalNoticeInitiationMaker(ufFlag){
	
		DisButClass.prototype.DisButMethod();
		var loanNo = document.getElementById("loanNo");
		var lbxLoanId = document.getElementById("lbxLoanId");
		var loanButton = document.getElementById("loanNoButton");
		var noticeDesc = document.getElementById("noticeDesc");
		var lbxNoticeCode = document.getElementById("lbxNoticeCode");
		var noticeButton = document.getElementById("noticeButton");
		var reasonDesc= document.getElementById("reasonDesc");
		var lbxcustomerId= document.getElementById("lbxcustomerId");
		var reasonButton= document.getElementById("reasonButton");
		var makerRemarks= document.getElementById("makerRemarks");
		var modeOfNotice= document.getElementById("modeOfNotice");
		var nameOfConciliator= document.getElementById("nameOfConciliator");
		var custButton= document.getElementById("custButton");
		document.getElementById("updateForwardFlag").value=ufFlag;
		
		 if(trim(loanNo.value)=='' ||trim(lbxcustomerId.value)=='' || trim(lbxLoanId.value)=='' || trim(noticeDesc.value)=='' || trim(lbxNoticeCode.value)=='' || trim(reasonDesc.value)==''  || trim(modeOfNotice.value)==''){
			 var msg= '';
	 		
			 
			 if(trim(loanNo.value) == '' || trim(lbxLoanId.value) == '')
		 			msg += '* Loan No. is required.\n';
			 
			 if(trim(lbxcustomerId.value) == '' )
		 			msg += '* Customer Id is required.\n';
			 
	 		if(trim(noticeDesc.value) == '' || trim(lbxNoticeCode.value) == '')
	 			msg += '* Notice is required.\n';
	 		
	 		if(trim(reasonDesc.value) == '' )
	 			msg += '* Reason is required.\n';
	 		
	 		/*if(trim(makerRemarks.value) == '')
	 			msg += '* Remarks is required.\n';*/
	 		
	 		if(trim(modeOfNotice.value) == '')
	 			msg += '* Mode of Notice required.\n';
	 		/*if(trim(nameOfConciliator.value) == '')
	 			msg += '* Name of Conciliator is required.\n';*/
	 		
	 		alert(msg);
	 		
	 		if(msg.match("Loan")){
	 			loanButton.focus();
	 		}else if(msg.match("Customer")){
	 			custButton.focus();
	 		}else if(msg.match("Notice")){
	 			noticeButton.focus();
	 		}else if(msg.match("Reason")){
	 			reasonButton.focus();
	 		}
	 		/*else if(msg.match("Remarks")){
	 			makerRemarks.focus();
	 		}*/
	 		else if(msg.match("Mode")){
	 			modeOfNotice.focus();
	 		}/*else if(msg.match("Name")){
	 			nameOfConciliator.focus();
	 		}*/
	 		
			DisButClass.prototype.EnbButMethod();
	 		//document.getElementById("save").removeAttribute("disabled","true");
	 		return false;
		 
		 }else {
	
			document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerAdd.do?method=saveLegalNoticeInitiationMaker";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalNoticeInitiationMakerForm").submit();
			return true;
	
		 }	
}

function fnUpdateLegalNoticeInitiationMaker(ufFlag){
	
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo");
	var lbxLoanId = document.getElementById("lbxLoanId");
	var loanButton = document.getElementById("loanNoButton");
	var noticeDesc = document.getElementById("noticeDesc");
	var lbxNoticeCode = document.getElementById("lbxNoticeCode");
	var noticeButton = document.getElementById("noticeButton");
	var reasonDesc= document.getElementById("reasonDesc");
	var lbxcustomerId= document.getElementById("lbxcustomerId");
	var reasonButton= document.getElementById("reasonButton");
	var custButton= document.getElementById("custButton");
	var modeOfNotice= document.getElementById("modeOfNotice");
	//var nameOfConciliator= document.getElementById("nameOfConciliator");
	//for deciding update and forward
	document.getElementById("updateForwardFlag").value=ufFlag;
	
	if(trim(loanNo.value)=='' || trim(lbxcustomerId.value)=='' || trim(lbxLoanId.value)=='' || trim(noticeDesc.value)=='' || trim(lbxNoticeCode.value)=='' || trim(reasonDesc.value)=='' || trim(modeOfNotice.value)=='' ){
		 var msg= '';
		
		 
		 if(trim(loanNo.value) == '' || trim(lbxLoanId.value) == '')
	 			msg += '* Loan No. is required.\n';
		 
		 if(trim(lbxcustomerId.value) == '')
	 			msg += '* Customer Id is required.\n';
		 
		if(trim(noticeDesc.value) == '' || trim(lbxNoticeCode.value) == '')
			msg += '* Notice is required.\n';
		
		if(trim(reasonDesc.value) == '' )
			msg += '* Reason is required.\n';
		
		/*if(trim(makerRemarks.value) == '')
			msg += '* Remarks is required.\n';*/
		
		if(trim(modeOfNotice.value) == '')
			msg += '* Mode of Notice required.\n';
		
		/*if(trim(nameOfConciliator.value) == '')
 			msg += '* Name of Conciliator is required.\n';*/
		
		alert(msg);
		
		if(msg.match("Loan")){
			loanButton.focus();
		}else if(msg.match("Customer")){
			custButton.focus();
		}else if(msg.match("Notice")){
			noticeButton.focus();
		}else if(msg.match("Reason")){
			reasonButton.focus();
		}
		/*else if(msg.match("Remarks")){
			makerRemarks.focus();
		}*/
		else if(msg.match("Mode")){
			modeOfNotice.focus();
		}/*else if(msg.match("Name")){
 			nameOfConciliator.focus();
 		}*/
		
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 
	 }
	
		document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerAdd.do?method=updateLegalNoticeInitiationMaker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalNoticeInitiationMakerForm").submit();
}


//parvez for clear 
function clearFnUpdateLegalNoticeInitiationMaker(){
	document.getElementById('loanNo').value = '';
	document.getElementById('lbxLoanId').value = '';
	document.getElementById('loanNoButton').value = '';
	
	document.getElementById('customerId').value = '';
	document.getElementById('lbxcustomerId').value = '';
	document.getElementById('custButton').value = '';
	
	document.getElementById('noticeDesc').value = '';
	document.getElementById('lbxNoticeCode').value = '';
	document.getElementById('noticeButton').value = '';
	
	document.getElementById('reasonDesc').value = '';
	document.getElementById('lbxReasonId').value = '';
	document.getElementById('reasonButton').value = '';
	
	document.getElementById('makerRemarks').value = '';
	document.getElementById('modeOfNotice').value = '';
	
}	

//parvez works ends here
function fnSearchLegalNoticeInitiationMaker(val){
	
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo");
	var lbxLoanId = document.getElementById("lbxLoanId");
	var loanButton = document.getElementById("loanNoButton");
	var noticeDesc = document.getElementById("noticeDesc");
	var lbxNoticeCode = document.getElementById("lbxNoticeCode");
	var noticeButton = document.getElementById("noticeButton");

	
	document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
	
	if((loanNo.value=='' || lbxLoanId.value=='') && (noticeDesc.value=='' || lbxNoticeCode.value==''))
	{
		alert(val);
		loanButton.focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("legalNoticeInitiationMakerForm").submit();
	return true;
	}
}

function validateTime(a)
{
//	 alert("ok");
	var timeStr=document.getElementById(a).value;
	
	var timePat = /^(\d{1,2}):(\d{2})?$/;
	if(timeStr.length<5)
	{
		document.getElementById(a).value='0'+timeStr;
	}
	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById(a).value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById(a).value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById(a).value='';
	return false;
	}

}

//parvez works starts here
function clearFnUpdateLegalNoticeInitiationMaker(){

	
	//document.getElementById('lbxLoanId').value = '';
	document.getElementById('noticeAmount').value = '';
	
	document.getElementById('noticeDesc').value = '';
	document.getElementById('lbxNoticeCode').value = '';
	document.getElementById('noticeButton').value = '';
	
	document.getElementById('reasonDesc').value = '';
	document.getElementById('lbxReasonId').value = '';
	document.getElementById('reasonButton').value = '';
	
	document.getElementById('makerRemarks').value = '';
	document.getElementById('modeOfNotice').value = '';
	document.getElementById('dateOfConciliation').value = '';
	document.getElementById('venueOfConciliation').value = '';
	document.getElementById('nameOfConciliator').value = '';
	document.getElementById('designationOfConciliator').value = '';
	
	
}	

//parvez works ends here