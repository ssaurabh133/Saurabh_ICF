
function searchCaseMarkingMaker(msg){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchLoanNo").value=="" && document.getElementById("statusCase").value==''){
     alert(msg);
     DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}
	else{
	document.getElementById("caseMarkingSearchForm").action="caseMarkingMakerBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingSearchForm").submit();
    return true;
    }
}


function forwardRepoBillingApprovalDetails(msg){

	DisButClass.prototype.DisButMethod();
	if((!document.getElementById("searchLoanNo").value=="")&&(!document.getElementById("canForward").value=="")){
	document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=forwardRepoBillingApprovalMarkingDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoBillingApprovalForm").submit();
    return true;
	}
	else
	{
		alert("You can't move without saving .");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
    }


function insertRepoBillingApprovalDetails(){

	var sourcepath=document.getElementById("contextPath").value;

DisButClass.prototype.DisButMethod();
	
	var lbxDealNo = document.getElementById("lbxDealNo");
	
	 if(trim(lbxDealNo.value) == ''){
		 var msg= '';
		 if(trim(lbxDealNo.value) == '')
	 			msg += '* Loan No.is required.\n';
 			 		
 		if(msg.match("Loan")){
 			lbxDealNo.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }

	 else
	 {
		 document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=saveRepoBillingApprovalDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("repoBillingApprovalForm").submit();

	return true;
	 }
	}




function updateRepoBillingApprovalDetails(){

	var sourcepath=document.getElementById("contextPath").value;

DisButClass.prototype.DisButMethod();
	
	var lbxDealNo = document.getElementById("lbxDealNo");
	
	 if(trim(lbxDealNo.value) == ''){
		 var msg= '';
		 if(trim(lbxDealNo.value) == '')
	 			msg += '* Loan No.is required.\n';
 			 		
 		if(msg.match("Loan")){
 			lbxDealNo.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }

	 else
	 {
		 document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=updateRepoBillingApprovalDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("repoBillingApprovalForm").submit();

	return true;
	 }
	}

function newAddRepoBillingApproval(){
	
	
	DisButClass.prototype.DisButMethod();

	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("RepoBillingApprovalSearchForm").action=sourcepath+"/repoBillingApprovalMakerDispatch.do?method=newAddRepoBillingApproval";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("RepoBillingApprovalSearchForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();

	return false;

}

function saveRepoBillingApprovalChecker(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	if ((document.getElementById("decision").value!="A") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	 
	}else{
		document.getElementById("repoBillingApprovalAuthorForm").action="repoBillingApprovalAuthorDispatch.do?method=saveRepoBillingApprovalChecker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("repoBillingApprovalAuthorForm").submit();
		return true;	
	}
}

function disable()
{
	
	var caseStatusList=document.getElementsByName("caseStatus");
	for(var i=0; i< caseStatusList.length ; i++) 
	{		
		var val=caseStatusList[i].value;
		var agencyButton="agencyButton"+(i+1);		
		if(caseStatusList[i].value=='REPO')
			
			document.getElementById(agencyButton).style.display="";
		else
			document.getElementById(agencyButton).style.display="none";
	}	
}


function allChecked(){ 
	
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	var c = document.getElementById("allchkd").checked;
	var psize=document.getElementById("psize").value;
	
	if(psize==""){
		psize=lastElement;
	}
	if(c==true){
		for(i=1;i<psize;i++)
		{
			var ch1=document.getElementById('chk'+i);
			if(ch1!=undefined||ch1!=null){
				document.getElementById('chk'+i).checked=true;
				}		
	}
	}else{
		for(i=1;i<psize;i++)
		{
		var ch2=document.getElementById('chk'+i);
			if(ch2!=undefined||ch2!=null){
				document.getElementById('chk'+i).checked=false;
			}
		}
	}
}


function deleteCaseMarking(){
		
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var caseMarkingLoanId=document.getElementById("lbxDealNo").value;
	var statusCase=document.getElementById("statusCase").value;
	// alert("loanId......"+loanId);
	if(caseMarkingLoanId=='')
	{
		alert("Loan No is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	agree=confirm("Are you sure,You want to delete this case marking.Do you want to continue?");
	if (!(agree))
	{	
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		
		location.href=sourcepath+"/caseMarkingMakerDispatch.do?method=deleteCaseMarking&caseMarkingLoanId="+caseMarkingLoanId+"&statusCase="+statusCase;
		document.getElementById("processingImage").style.display = '';
	
		return true;
      }
	
}
function searchRepoBillingApprovalMaker(msg){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchLoanNo").value==""){
     alert(msg);
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("RepoBillingApprovalSearchForm").action="repoBillingApprovalMakerBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("RepoBillingApprovalSearchForm").submit();
    return true;
    }
}


function searchRepoBillingApprovalAuthor(msg){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchLoanNo").value==""){
     alert(msg);
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("RepoBillingApprovalSearchForm").action="repoBillingApprovalAuthorBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("RepoBillingApprovalSearchForm").submit();
    return true;
    }
}


function deleteRepoBillingApproval(){
	
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var repoBillingApprovalLoanId=document.getElementById("lbxDealNo").value;
	// alert("loanId......"+loanId);
	if(repoBillingApprovalLoanId=='')
	{
		alert("Loan No is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	agree=confirm("Are you sure,You want to delete.Do you want to continue?");
	if (!(agree))
	{	
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		
		location.href=sourcepath+"/repoBillingApprovalMakerDispatch.do?method=deleteRepoBillingApproval&repoBillingApprovalLoanId="+repoBillingApprovalLoanId;
		document.getElementById("processingImage").style.display = '';
	
		return true;
      }
	
}
/*function lovHideShow()
{
	
	var statusCase=document.getElementById("statusCase").value;
	
		if(statusCase=='A')
		{			
			document.getElementById("dealButton1").style.display='inline';
			document.getElementById("dealButton").style.display='none';
		}
		else
		{
			document.getElementById("dealButton1").style.display='none';
			document.getElementById("dealButton").style.display='';
		}
}*/