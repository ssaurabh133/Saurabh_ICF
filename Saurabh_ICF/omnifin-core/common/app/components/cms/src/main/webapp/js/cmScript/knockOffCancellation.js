function searchKOCData()
{
	DisButClass.prototype.DisButMethod();
	var loanID=document.getElementById("lbxLoanID").value;
	var knockOffId=document.getElementById("knockOffID").value;
	if(loanID =='')
	{
		alert("First select Loan No.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("knockOffCancellationMaker").action = contextPath+"/knockOffCancellationDispatchAction.do?method=searchKOCData&knockOffId="+knockOffId;
    document.getElementById("processingImage").style.display = '';
    document.getElementById("knockOffCancellationMaker").submit();
}
function searchKOCAuthorData()
{
	DisButClass.prototype.DisButMethod();
	var loanID=document.getElementById("lbxLoanID").value;
	var knockOffId=document.getElementById("knockOffID").value;
	if(loanID =='')
	{
		alert("First select Loan No.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("knockOffCancellationAuthor").action = contextPath+"/knockOffCancellationDispatchAction.do?method=searchKOCAuthorData&knockOffId="+knockOffId;
    document.getElementById("processingImage").style.display = '';
    document.getElementById("knockOffCancellationAuthor").submit();
}

function knockOffCancellationMakerNew()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("knockOffCancellationMaker").action = contextPath+"/knockOffCancellationDispatchAction.do?method=newKnockOff";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("knockOffCancellationMaker").submit();
}
function cancelknockOff(val)
{
	DisButClass.prototype.DisButMethod();
	
	var loanID=document.getElementById("lbxLoanID").value;
	var knockOffID=document.getElementById("knockOffID").value;
	document.getElementById("makerRemarks").value=trim(document.getElementById("makerRemarks").value);
	var makerRemarks=document.getElementById("makerRemarks").value;
	if(loanID =='')
	{
		alert("First select Loan No.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
//	if(makerRemarks =='')
//	{
//		alert("Maker Remarks required.");
//		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
//		return false;
//	}		
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("knockOffCanNew").action = contextPath+"/knockOffCancellationDispatchAction.do?method=cancelknockOff&loanId="+loanID+"&knockOffId="+knockOffID+"&makerRemarks="+makerRemarks+"&val="+val;
    document.getElementById("processingImage").style.display = '';
    document.getElementById("knockOffCanNew").submit();
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
function kocAuthorSave()
{
	DisButClass.prototype.DisButMethod();
	if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
	{
		alert("Comments is required.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		var decision=document.getElementById("decision").value;
		var comments=document.getElementById("comments").value;
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("KOCAuthor").action = contextPath+"/knockOffCancellationDispatchAction.do?method=saveKOCAuthor&decision="+decision+"&remarks="+comments;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("KOCAuthor").submit();
	}
}
function deleteKnockOffCancellation(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var knockOffID=document.getElementById("knockOffID").value;
	//alert("knockOffID......"+knockOffID);
	agree=confirm("Are you sure,You want to delete this KnockOffCancellation.Do you want to continue?");
	if (!(agree))
	{
		DisButClass.prototype.EnbButMethod();
    	document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("knockOffCanNew").action=sourcepath+"/knockOffCancellationDispatchAction.do?method=deleteknockOffCancellation&knockOffID"+knockOffID;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("knockOffCanNew").submit();
		return true;
}
	
	
}
 