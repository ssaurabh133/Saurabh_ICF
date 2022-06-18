function searchLegalDeclineDispatchNoticeData(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("lbxLoanId").value=="" && document.getElementById("lbxNoticeCode").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();

	return false; 
	}
	else{
	document.getElementById("legalDeclineDispatchNoticeForm").action="legalDeclineDispatchNoticeBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("legalDeclineDispatchNoticeForm").submit();
	return true;
	}
	
}

function openEditLegalDeclineDispatchNotice(){
	
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
		document.getElementById("legalDeclineDispatchNoticeForm").action="noticeTypeMasterAdd.do?method=updateNotice";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalDeclineDispatchNoticeForm").submit();
	

}
function onSavelegalDeclineDispatchNotice(a){
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	//var reasonDesc = document.getElementById("reasonDesc");
	
	if ((document.getElementById("checkerDecision").value!="D")){
		if(trim(comments.value)=='' /*||trim(reasonDesc.value)==''*/){
			 var msg= '';
	 		
			 
			 if(trim(comments.value) == '')
		 			msg += '* Comments is required.\n';
			 
			/* if(trim(reasonDesc.value) == '')
		 			msg += '* Reason is required.\n';*/
	 		
	 		
	 		if(msg.match("Comments")){
	 			comments.focus();
	 	}
	 		/*else if(msg.match("Reason")){
	 			document.getElementById('reasonButton').focus();
	 		}*/
	 		alert(msg);
			DisButClass.prototype.EnbButMethod();
	 		//document.getElementById("save").removeAttribute("disabled","true");
	 		return false;
		}
		
			document.getElementById("legalDeclineDispatch").action="legalDeclineDispatchNoticeAction.do?method=saveLegalDeclineDispatchNotice";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalDeclineDispatch").submit();
			return true;
		
		
		 }else{
			document.getElementById("legalDeclineDispatch").action="legalDeclineDispatchNoticeAction.do?method=saveLegalDeclineDispatchNotice";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalDeclineDispatch").submit();
			return true;	
	}
}

function hideAsterik(value){
	  
	  if(value!='D')
	  {
		  document.getElementById("hideAsterik1").style.display ='';
		  document.getElementById("hideAsterik2").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik1").style.display ='none';
		  document.getElementById("hideAsterik2").style.display ='none';
	  }
		  
}
	
