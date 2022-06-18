function searchReassignCaseData(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("lbxLoanId").value==""&& document.getElementById("legalId").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();

	return false; 
	}
	else{
	document.getElementById("reassignCaseForm").action="reassignCaseBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reassignCaseForm").submit();
	return true;
	}
	}

function editReassignCase(){
	
	DisButClass.prototype.DisButMethod();
	var noticeDesc = document.getElementById("noticeDesc");
	var loanNo = document.getElementById("loanNo");
	
	 if(trim(noticeDesc.value) == ''||trim(loanNo.value) == ''){
		 var msg= '';
 		if(trim(noticeDesc.value) == '')
 			msg += '* Notice  Description is required.\n';
 		if(trim(loanNo.value) == '')
 			msg += '* loan No is required.\n';
 		 		 		
 		if(msg.match("Description")){
 			noticeTypeDesc.focus();
 		}else if(msg.match("Code")){
 			noticeTypeCode.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 	
 		return false;
	 
	 }
		document.getElementById("reassignCaseForm").action="reassignCaseAuthor.do?method=updateNotice";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("reassignCaseForm").submit();
	

}
function saveReassignCase(msg){

	DisButClass.prototype.DisButMethod();
	var remarks = document.getElementById("remarks");
	var advocateDesc = document.getElementById("advocateDesc");
	
	 if(trim(remarks.value)=='' ||trim(advocateDesc.value)==''){
		 var msg= '';
 		
		 
		 if(trim(remarks.value) == '')
	 			msg += '* Comments is required.\n';
		 
		 if(trim(advocateDesc.value) == '')
	 			msg += '* Advocate is required.\n';
 		
 		
 		if(msg.match("remarks")){
 			Comments.focus();
 	}
 		else if(msg.match("advocateDesc")){
 			Advocate.focus();
 		}
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else {
	document.getElementById("reassignCaseForm").action="reassignCaseAction.do?method=saveReassignCase";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reassignCaseForm").submit();
	return true;
	 }
	
}