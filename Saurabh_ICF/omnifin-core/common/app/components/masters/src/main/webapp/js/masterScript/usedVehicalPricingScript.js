function trim(str) {
	return ltrim(rtrim(str));
	}
function newAddVehicalPrising()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;

	document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingBehind.do?method=openAddProduct";
	document.getElementById("processingImage").style.display = '';
	
	document.getElementById("vehiclePricing").submit();
	
	document.getElementById("save").removeAttribute("disabled","true");
	return false;
}

function newAddVehicalPrising()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingAdd.do?method=openAddProduct";
	document.getElementById("processingImage").style.display='';
	document.getElementById("vehiclePricing").submit();
	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveUsedVehiclePricingAdd()
{

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var usedVehicleMakeSearch= document.getElementById("usedVehicleMakeSearch").value;
	var usedVehicleModelSearch= document.getElementById("usedVehicleModelSearch").value;
	var usedVehiclePrice= document.getElementById("usedVehiclePrice1").value;
	
	
	if(trim(usedVehicleMakeSearch) == '' || trim(usedVehicleModelSearch) =='' ||trim(usedVehiclePrice) == '')
		{
		var msg= '';
		if(trim(usedVehicleMakeSearch) == ''){
			msg += '* Make is required.\n';
		}
		if(trim(usedVehicleModelSearch) == ''){
			msg += '* Model  is required.\n';
		}
		if(trim(usedVehiclePrice) == ''){
			msg += '* Grid Value  is required.\n';
		}
		
		if(msg.match("make")){
			document.getElementById("vehicleMakeButton").focus();
			
		}else if(msg.match("model")){
			document.getElementById("usedVehicleModelSearch").focus();
		}else if(msg.match("grid")){
			document.getElementById("usedVehicleStateButton").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		}	
	else
	{
		document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingAdd.do?method=saveUsedVehiclePricingAdd";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("vehiclePricing").submit();
		return true;
	}
}

function getUsedVehiclePricingData()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;

	document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingBehind.do?method=getUsedVehiclePricingData";
	document.getElementById("processingImage").style.display = '';
	
	document.getElementById("vehiclePricing").submit();
	DisButClass.prototype.EnbButMethod();
	
	//document.getElementById("save").removeAttribute("disabled","true");
	return false;
}

function searchUsedVehiclePricing()
{
	DisButClass.prototype.DisButMethod();
	
	            var usedVehicleMakeSearch= document.getElementById("usedVehicleMakeSearch").value;
	            var usedVehicleModelSearch= document.getElementById("usedVehicleModelSearch").value;
	var sourcepath=document.getElementById("contextPath").value;
	
	if(trim(usedVehicleMakeSearch) == '' || trim(usedVehicleModelSearch) =='')
	{
		var msg= '';
		if(trim(usedVehicleMakeSearch) == ''){
			msg += '* Make is required.\n';
		}
		if(trim(usedVehicleModelSearch) == ''){
			msg += '* Model  is required.\n';
		}
		if(msg.match("make")){
			document.getElementById("vehicleMakeButton").focus();
			
		}else if(msg.match("model")){
			document.getElementById("usedVehicleModelSearch").focus();
	}
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
	else
	{

	document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingBehind.do?method=searchUsedVehiclePricing";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("vehiclePricing").submit();
	DisButClass.prototype.EnbButMethod();
	}

	return true;
}

function updateVehiclePricingdata()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var usedVehicleMakeSearch= document.getElementById("usedVehicleMakeSearch").value;
	var usedVehicleModelSearch= document.getElementById("usedVehicleModelSearch").value;
	var usedVehiclePrice= document.getElementById("usedVehiclePrice1").value;
	
	if(trim(usedVehicleMakeSearch) == '' || trim(usedVehicleModelSearch) =='' ||trim(usedVehiclePrice) == '')
		{
		var msg= '';
		if(trim(usedVehicleMakeSearch) == ''){
			msg += '* Make is required.\n';
		}
		if(trim(usedVehicleModelSearch) == ''){
			msg += '* Model  is required.\n';
		}
		if(trim(usedVehiclePrice) == ''){
			msg += '* Grid Value  is required.\n';
		}
		
		if(msg.match("make")){
			document.getElementById("vehicleMakeButton").focus();
			
		}else if(msg.match("model")){
			document.getElementById("usedVehicleModelSearch").focus();
		}else if(msg.match("grid")){
			document.getElementById("usedVehicleStateButton").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;	
		}	
	else
	{
		document.getElementById("vehiclePricing").action=sourcepath+"/usedVehiclePricingAdd.do?method=updateVehiclePricingdata";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("vehiclePricing").submit();
		return true;
	}
}
