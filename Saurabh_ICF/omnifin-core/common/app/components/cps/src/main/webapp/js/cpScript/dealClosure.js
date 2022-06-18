function searchDealClosure()
{
	//DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var dealNo = document.getElementById("dealNo").value;
	var product = document.getElementById("product").value;
	var customerName = document.getElementById("customerName").value;
	if(dealNo !='' || product !='' || customerName !=''){
		document.getElementById("dealCancellationForm").action=contextPath+"/dealClosureMakerBehindAction.do?method=dealCancellationMakerSearch&value=S";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("dealCancellationForm").submit();
		return true;
	}
	else{
		alert("* Please Select atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		//DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function openDealClosure()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("dealCancellationForm").action = contextPath+"/dealCancellationMaker.do?method=openNewDealClosure";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("dealCancellationForm").submit();
}

function saveNewDealClosure()
{
	//DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	
	if(document.getElementById("dealNo").value=="" || document.getElementById("reasonForClosure").value=="")
	{
	
	if(document.getElementById("dealNo").value=="" ){
		alert("* Deal Number is required \n");
	}
	
	if(document.getElementById("reasonForClosure").value=="" ){
		alert("* Reason For Closure is required \n");
	}

	document.getElementById("button").removeAttribute("disabled","true");
	//DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("cancellationForm").action=contextPath+"/dealCancellationMaker.do?method=saveDealCancellation";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("cancellationForm").submit();
		return true;
	}
}

function saveModfyDeal()
{
	//DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	
	if(document.getElementById("reasonForClosure").value=="" ){
		alert("* Reason For Closure is required \n");
		document.getElementById("save").removeAttribute("disabled","true");
		//DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("cancellationForm").action=contextPath+"/dealCancellationMaker.do?method=modfyNewDeal";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("cancellationForm").submit();
		return true;
	}
}

function modfyForwardDeal()
{
	//DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	//alert("in Save Action of js");
	
	if(document.getElementById("reasonForClosure").value=="" ){
		alert("* Reason For Closure is required \n");
		document.getElementById("saveForward").removeAttribute("disabled","true");
		//DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("cancellationForm").action=contextPath+"/dealCancellationMaker.do?method=modfyForwardDeal";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("cancellationForm").submit();
		return true;
	}
}

function searchCancellationAuthor()
{
	//DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var dealNo = document.getElementById("dealNo").value;
	var product = document.getElementById("product").value;
	var customerName = document.getElementById("customerName").value;
	if(dealNo !='' || product !='' || customerName !=''){
		document.getElementById("dealCancellationForm").action=contextPath+"/dealClosureMakerBehindAction.do?method=dealAuthorSearch&value=M";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("dealCancellationForm").submit();
		return true;
	}
	else{
		alert("* Please Select atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		//DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function dealAuthorRemarks()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	//alert("in Save Action of js" + dealId);
	
	if(document.getElementById("decison").value=="" || document.getElementById("textarea").value=="")
	{
	var a="";
	if(document.getElementById("decison").value==""){
		a="* Decision is required \n";
	}
	if(document.getElementById("textarea").value==""){
		a +="* Author Remarks is required \n";
	}
	alert(a);
	document.getElementById("save").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("dealAuthorForm").action=contextPath+"/dealCancellationAuthor.do?method=saveDealAuthor&dealID="+dealId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("dealAuthorForm").submit();
		return true;
	}
}

function updateDealBeforeSave()
{
	DisButClass.prototype.DisButMethod();
	alert("Please Save Deal Cancellation First");
	document.getElementById("frowardButton").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
}
