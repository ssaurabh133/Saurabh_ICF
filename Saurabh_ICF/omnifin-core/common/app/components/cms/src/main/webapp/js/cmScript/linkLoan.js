function fnSearchLinkLoan(val){
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	if(document.getElementById("loanNo").value=='' && document.getElementById("customerName").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("linkLoanMakerSearchForm").action=sourcePath+"/linkLoanMakerSearch.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("linkLoanMakerSearchForm").submit();
	    return true;
	}
}

function newLinkLoan(){
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	document.getElementById("linkLoanMakerSearchForm").action=sourcePath+"/linkLoanMakerAdd.do?method=openLinkLoanAdd";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerSearchForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
	return false;

}
function getLinkLoanDetail(){
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	if(document.getElementById('lbxLoanNoHID').value==''){
		alert("Please Select Loan Number First");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
	document.getElementById("linkLoanMakerAddForm").action=sourcePath+"/linkLoanMakerAdd.do?method=getLinkLoanDetail";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerAddForm").submit();
	return true;
	}

}

function allChecked()
{
	
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('checkId');	

 	var zx=0;

	if(c==true)
	{	
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		
		for(i=0;i<ch.length;i++)
		{			
			ch[zx].checked=false;
			zx++;
		}
	}
	
}
function putLoanNo(){
	
	var loadId=document.getElementById('lbxLoanNoHIDAdd').value;
	var loadIdToGetDetail=document.getElementById('loanNoSave').value;
	var sourcePath=document.getElementById('contextPath').value;
	if(loadId==''){
		alert('Please Select  Loan Number');
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		if(loadIdToGetDetail==''){
			alert('Please Get Detail First ');
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	document.getElementById("linkLoanMakerAddForm").action=sourcePath+"/linkLoanMakerAdd.do?method=getLinkLoanDetail&flagAdd=Y";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerAddForm").submit();
	return true;
	}
	
}

function saveLinkLoan(){
	//alert("hiiiii");
	var loadId=document.getElementById('loanNoSave').value;
	var sourcePath=document.getElementById('contextPath').value;
	var loanNumber="";
	var primaryLoanNumberHid="";
	var loanNumberHid="";
	var custNameHid="";
	var loanAmtHid="";
	var loanTenureTabHid="";
	var loabBalPrinHid="";
	var loanStatusHid="";
	
	if(loadId==''){
		alert('Please Get Detail For Loan Number');
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		
		var listSize=document.getElementById("listSize").value;
		 //alert("There is no Data to Save."+listSize);
		if(listSize > 0){
			for (var i=0; i<listSize; i++)
			  {
				if(document.getElementById("checkId"+i).value!="")
		 		{
					var checkValue=document.getElementById("checkId"+i).checked;
					if(checkValue){
					loanNumber=loanNumber+document.getElementById("checkId"+i).value+"|";
					primaryLoanNumberHid=primaryLoanNumberHid+document.getElementById("primaryLoanNumberHid"+i).value+"|";
							
					}
	 			
		 		}
			  }
			
			//alert("value of all loans"+loanNumber);
			//alert("value of all primaryLoanNumberHid"+primaryLoanNumberHid);
	
	document.getElementById("linkLoanMakerAddForm").action=sourcePath+"/linkLoanMakerAdd.do?method=saveLinkLoanDetail&allLoanIds="+loanNumber+"&primaryLoanNumberHid="+primaryLoanNumberHid;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerAddForm").submit();
	return true;
	}
  else{
	  alert("There is no Data to Save.");
  	}
	}

}
function frowardLinkLoan(){
	//alert("hi");
	var sourcePath=document.getElementById('contextPath').value;
	document.getElementById("linkLoanMakerAddForm").action=sourcePath+"/linkLoanMakerAdd.do?method=forwardLinkLoanDetail";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerAddForm").submit();
	return true;
	
}
function fnSearchLinkLoanAuthor(val){
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	if(document.getElementById("loanNo").value=='' && document.getElementById("customerName").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("linkLoanAuthorSearchForm").action=sourcePath+"/linkLoanAuthorSearch.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("linkLoanAuthorSearchForm").submit();
	    return true;
	}
	
}
function saveLinkLoanAuthor(){
	if(document.getElementById("comments").value=='')
	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("linkLoanAuthorApproveForm").action = contextPath+"/linkLoanAuthorApprove.do?method=saveLinkLoanAuthor";
	    document.getElementById("linkLoanAuthorApproveForm").submit();
	}
	
}
function putLoanNoInUpdate(){	
	var loadId=document.getElementById('lbxLoanNoHIDAdd').value;
	var sourcePath=document.getElementById('contextPath').value;
	if(loadId==''){
		alert('Please Select  Loan Number');
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
	document.getElementById("linkLoanMakerAddForm").action=sourcePath+"/linkLoanMakerAdd.do?method=linkNewLoan";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("linkLoanMakerAddForm").submit();
	return true;
	}

	
}
function noChecked(){
	if(document.getElementById('allchkd').value)
		document.getElementById('allchkd').checked=true;
}
function frowardBeforeSave(){
	alert("Please Save the Data First");
}











