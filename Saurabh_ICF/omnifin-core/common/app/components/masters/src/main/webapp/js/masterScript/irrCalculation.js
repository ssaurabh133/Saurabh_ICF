function newAddIrrCal1()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("irrCalculationSearchForm").action=sourcepath+"/irrCalculation.do?method=irrCalculationMethod1";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("irrCalculationSearchForm").submit();
	return true;
}


function irrCalSave()
{
	DisButClass.prototype.DisButMethod();
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var irrType=document.getElementById("irrType").value;
	var lbxIrrChargeCode=document.getElementById('lbxIrrChargeCode').value;
	var msgMandatory = "";
	if(product=='')
		msgMandatory=msgMandatory+"*Product is mandatory.";
	if (scheme=='')
		msgMandatory=msgMandatory+"\n*Scheme is mandatory.";
	if (irrType=='')
		msgMandatory=msgMandatory+"\n*IrrType is mandatory.";	
	if (lbxIrrChargeCode=='')
		msgMandatory=msgMandatory+"\n*IrrChargeCode is mandatory.";		
	if(msgMandatory!='')
	{
		alert(msgMandatory);
		DisButClass.prototype.EnbButMethod();
		return false;
	
	}
	else
	{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("irrCalculationSearchForm").action=sourcepath+"/irrCalculation.do?method=irrCalculationSave";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("irrCalculationSearchForm").submit();		
		return true;		
	}	
}
function irrCalUpdate()
{
	DisButClass.prototype.DisButMethod();
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var irrType=document.getElementById("irrType").value;
	var lbxIrrChargeCode=document.getElementById('lbxIrrChargeCode').value;
	var msgMandatory = "";
	if(product=='')
		msgMandatory=msgMandatory+"*Product is mandatory.";
	if (scheme=='')
		msgMandatory=msgMandatory+"\n*Scheme is mandatory.";
	if (irrType=='')
		msgMandatory=msgMandatory+"\n*IrrType is mandatory.";	
	if (lbxIrrChargeCode=='')
		msgMandatory=msgMandatory+"\n*IrrChargeCode is mandatory.";		
	if(msgMandatory!='')
	{
		alert(msgMandatory);
		DisButClass.prototype.EnbButMethod();
		return false;	
	}
	else
	{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("irrCalculationSearchForm").action=sourcepath+"/irrCalculation.do?method=irrCalculationUpdate";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("irrCalculationSearchForm").submit();		
		return true;		
	}
}

function searchIrrCal(){
	
	
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById("contextPath").value;
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	if(product=='' && scheme=='')
	{
		alert("Please select at least one criteria");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	document.getElementById("irrCalculationSearchForm").action=contextPath+"/irrCalculation.do?method=irrCalculationSearch&product="+product+"&scheme="+scheme;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("irrCalculationSearchForm").submit();
	DisButClass.prototype.EnbButMethod();
	return true;
	
	
}


