
function onSaveAssignRejectCase(a){
	DisButClass.prototype.DisButMethod();
	var lbxUserId = document.getElementById("lbxUserId");
	var advocateDesc = document.getElementById("advocateDesc");
	var advocateButton = document.getElementById("advocateButton");
	var reject = document.getElementById("reject");
	var initMakerImemo = document.getElementById("initMakerImemo");
	var reasonDesc = document.getElementById("reasonDesc");
	var lbxReasonId = document.getElementById("lbxReasonId");
	var reasonButton = document.getElementById("reasonButton");
	var comments = document.getElementById("comments");	
	var lbxPoa=  document.getElementById("lbxPoa");
	var poaDesc = document.getElementById("poaDesc");
	var poaButton = document.getElementById("poaButton");
	
	//alert((trim(lbxUserId.value)=='' ||  trim(advocateDesc.value)==''));
	
	//alert(reject.checked==false);
	//alert(((trim(lbxUserId.value)=='' ||  trim(advocateDesc.value)=='') && reject.checked==false));
	//alert(trim(initMakerImemo.value)=='');
	//alert(trim(reasonDesc.value)=='');
	
	
	
	//alert(((trim(lbxUserId.value)=='' ||  trim(advocateDesc.value)=='') && reject.checked==false) || trim(initMakerImemo.value)=='' || trim(reasonDesc.value)=='' || trim(lbxReasonId.value)=='' ||trim(comments.value)=='' );
	
	 if(((trim(lbxUserId.value) == '' || trim(advocateDesc.value) == '') && reject.checked == false) || (trim(lbxUserId.value) != '' && trim(advocateDesc.value) != '' && (trim(initMakerImemo.value) == '' || (trim(poaDesc.value) == '' && trim(lbxPoa.value) == '')) ) || (reject.checked==true  && trim(reasonDesc.value) == '') || (reject.checked==true && trim(comments.value) == '') ){
		 var msg= '';
		 
		 if((trim(lbxUserId.value) == '' || trim(advocateDesc.value) == '') && reject.checked == false)
	 			msg += '* Either assign or reject case.\n';
		 
		 if(trim(lbxUserId.value) != '' && trim(advocateDesc.value) != '' && trim(initMakerImemo.value) == '')
	 			msg += '* I-Memo is required.\n';
		 
		 if(trim(lbxUserId.value) != '' && trim(advocateDesc.value) != '' && ( trim(poaDesc.value) == '' && trim(lbxPoa.value) == ''))
				msg += '* POA are required.\n';
		 
			 if(reject.checked==true  && trim(reasonDesc.value) == '')
	 			msg += '* Reason is required.\n';
		 
		 if(reject.checked==true && trim(comments.value) == '')
	 			msg += '* Comments are required.\n';
		
	 		
		 
		 alert(msg);
 		
		if(msg.match("Either")){
			advocateButton.focus();
	 	}
		else if(msg.match("I-MEMO")){
			initMakerImemo.focus();
 		}
 		else if(msg.match("Reason")){
 			reasonButton.focus();
 		}
 		else if(msg.match("Comments")){
 			comments.focus();
 		}	else if(msg.match("POA")){
 			poaButton.focus();
 		}
		
//		alert("lbxUserId "+lbxUserId.value);
//		alert("advocateDesc "+advocateDesc.value);
//		alert("reject "+reject.checked);
//		alert("initMakerImemo "+initMakerImemo.value);
//		alert("reasonDesc "+reasonDesc.value);
//		alert("lbxReasonId "+lbxReasonId.value);
//		alert("comments "+comments.value);
 		
		DisButClass.prototype.EnbButMethod();
 		//document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else {

	document.getElementById("assignRejectCaseAuthorForm").action="assignRejectCaseDispatchAction.do?method=saveAssignRejectCaseDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("assignRejectCaseAuthorForm").submit();
	return true;
	
	 }
}


function fnSearchAssignRejectCase(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("assignRejectCaseSearchForm").action="assignRejectCaseBehind.do";
	if(document.getElementById("lbxLoanId").value==''&& document.getElementById("lbxProductSearchID").value=="" && document.getElementById("lbxBranchId").value=="" && document.getElementById("lbxscheme").value=="" && document.getElementById("legalId").value=="" && document.getElementById("lbxCaseTypeCode").value=="")
	{
		alert(val);
		document.getElementById("advocateButton").focus();
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("assignRejectCaseSearchForm").submit();
	return true;
	}
}

function removeAdvocate()
{
	
	document.getElementById("lbxUserId").value="";
	document.getElementById("advocateDesc").value="";
	
}

function removeRejection()
{
	var choice = document.getElementById("reject");
	choice.checked = false;

}

function hideAsterik(value){
	  
	  if(value=='R')
	  {
		  document.getElementById("hideAsterik1").style.display ='none';
		  document.getElementById("hideAsterik2").style.display ='';
		  document.getElementById("hideAsterik3").style.display ='';
		  document.getElementById("hideAsterik4").style.display ='none';
	  }
	  else
	  {
		  document.getElementById("hideAsterik1").style.display ='';
		  document.getElementById("hideAsterik2").style.display ='none';
		  document.getElementById("hideAsterik3").style.display ='none';
		  document.getElementById("hideAsterik4").style.display ='';
	  }
		  
}

