function saveNoticeChecker(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	if ((document.getElementById("decision").value!="A") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	 
	}else{
		document.getElementById("noticeCheckerAuthorForm").action="noticeCheckerDispatchAction.do?method=saveLegalNoticeInitiationCheckerDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("noticeCheckerAuthorForm").submit();
		return true;	
	}
}

function fnSearchNoticeChecker(val){
	
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo");
	var lbxLoanId = document.getElementById("lbxLoanId");
	var loanButton = document.getElementById("loanNoButton");
	var noticeDesc = document.getElementById("noticeDesc");
	var lbxNoticeCode = document.getElementById("lbxNoticeCode");
	var noticeButton = document.getElementById("noticeButton");

	
	if((loanNo.value=='' || lbxLoanId.value=='') && (noticeDesc.value=='' || lbxNoticeCode.value==''))
	{
		alert(val);
		loanButton.focus();
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("noticeCheckerForm").action="noticeCheckerBehind.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("noticeCheckerForm").submit();
	    return true;
	}
}

function hideAsterik(value){
	  
	  if(value!='A')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}

