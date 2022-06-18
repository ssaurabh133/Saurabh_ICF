function trim(str)
{
	return ltrim(rtrim(str));
}

function searchManualDeviation()
{
	DisButClass.prototype.DisButMethod();
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var stage=document.getElementById("stage").value;
	if(product==''&& scheme==''&& stage=='')
	{
		alert(" Please select at least one field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("manualdeviationfrm").action=sourcepath+"/manualDeviationAddProcessAction.do?method=searchManualDeviation";
	document.getElementById("manualdeviationfrm").submit();
	return true;
	}
}

function manualDeviationAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("manualdeviationfrm").action=sourcepath+"/manualDeviationAddProcessAction.do?method=openManualDeviationAdd";
	document.getElementById("manualdeviationfrm").submit();
	return true;
}
function chkLength()
{
var data=document.getElementById("ruleDescription").value;
if(data.length>1000)
{
	alert("Rule Description Length Can Not Be Greater Than 1000 Char.");
}
}
function manualDeviationAddRow()
{
alert("Add Row");
return true;
}
function saveManulaDeviation()
{
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='';
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var stage=document.getElementById("stage").value;
	var subRuleType=document.getElementById("subRuleType").value;
	var approvalLevel=document.getElementById("approvalLevel").value;
	var ruleDescription=document.getElementById("ruleDescription").value;
	
	if(product=="")
	{
	   msg1="*Product description is required.\n";	
		
	}
	/*if(scheme=="")
	{
	    msg2="*Scheme description is required.\n";	
		
	}*/
	
	if(stage=="")
	{
		msg3="*Stage description is required.\n";		
		
	}
	if(approvalLevel=="")
	{
		msg7="*Approval Level is required.\n";		
		
	}
	if(subRuleType=="")
	{
		msg6="*Sub Rule Type is required.\n";		
		
	}
	if(ruleDescription=="")
	{
		msg4="*Rule Description is required.\n";			
	}
	if(ruleDescription.length>1000)
	{
		msg5="*Rule Description length can not be greater than 1000 char.\n";	
	}
	
	if(msg1!=''||msg3!=''||msg4!=''||msg5!=''||msg6!='' ||msg7!='')
	{
		alert(msg1+msg3+msg6+msg4+msg5+msg7);
		if(msg1!='')
		{
			document.getElementById('productButton').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('schemeButton').focus();
		}
		
		else if(msg3!='')
		{
			document.getElementById('schemeButton').focus();
		}
		else if(msg7!='')
		{
			document.getElementById('approvalLevel').focus();
		}
		else if(msg6!='')
		{
			document.getElementById('subRuleType').focus();
		}
		else if(msg4!='')
		{
			document.getElementById('ruleDescription').focus();
		}
		else if(msg5!='')
		{
			document.getElementById('ruleDescription').focus();
		}
		DisButClass.prototype.EnbButMethod();		
		return false;
	}	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("manualdeviationfrm").action=sourcepath+"/manualDeviationAddProcessAction.do?method=saveManualDeviation";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("manualdeviationfrm").submit();
}




function saveAgency()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	//alert(document.getElementById("userName").value);
	//alert(document.getElementById("lbxUserIds").value);
	
	
	var set=document.getElementById('userName');
	
	var branch = document.getElementById('userName').options.length;
	
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
	//	alert(DListValues);
    } 
	
	var agencyCode = document.getElementById("agencyCode");
	var agencyType = document.getElementById("agencyType");
	var agencyDesc = document.getElementById("agencyDesc");

	
	if(trim(agencyCode.value) == ''||trim(agencyType.value) == ''||trim(agencyDesc.value) == ''){
		var msg= '';
		if(trim(agencyCode.value) == '')
			msg += '* Agency Code is required.\n';
		if(trim(agencyType.value) == '')
			msg += '* Agency Type is required.\n';
		if(trim(agencyDesc.value) == '')
			msg += '* Agency Description is required.\n';
		
		if(msg.match("Code")){
			agencyCode.focus();
		}else if(msg.match("Type")){
			agencyType.focus();
		}else if(msg.match("Description")){
			agencyDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		var ck_numeric = /^[A-Za-z_ 0-9]{1,25}$/;
		if (!ck_numeric.test(agencyCode.value)) {
	    	if(trim(agencyCode.value) != ''){
		   	  alert("* Agency Code is invalid.");
		   	  agencyCode.focus();
	    	}
	    	DisButClass.prototype.EnbButMethod();
//	    	document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
		 }
		document.getElementById("agencyMasterForm").action=sourcepath+"/agencyMasterAdd.do?method=saveAgencyDetails&userlist="+DListValues;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("agencyMasterForm").submit();
		return true;
	}
}

function newpage(a)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("agencyMasterForm").action=sourcepath+"/agencyMasterSearch.do?method=openAddAgency&agencyCode="+a;
	 document.getElementById("processingImage").style.display = '';
	document.getElementById("agencyMasterForm").submit();
	
}

function editAgency(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("agencyMasterSearchForm").action=sourcepath+"/agencyMasterSearch.do?method=openEditAgency&agencySearchCode="+b;
	 document.getElementById("processingImage").style.display = '';
	document.getElementById("agencyMasterSearchForm").submit();

	
}

function fnEditAgency(b){
	//alert(document.getElementById("userName").value);
//	alert(document.getElementById("lbxUserIds").value);

	DisButClass.prototype.DisButMethod();
	var set=document.getElementById('userName');
	
	var branch = document.getElementById('userName').options.length;
	
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
	//	alert(DListValues);
    } 
	
	var agencyCode = document.getElementById("agencyCode");
	var agencyType = document.getElementById("agencyType");
	var agencyDesc = document.getElementById("agencyDesc");

	
	if(trim(agencyCode.value) == ''||trim(agencyType.value) == ''||trim(agencyDesc.value) == ''){
		var msg= '';
		if(trim(agencyCode.value) == '')
			msg += '* Agency Code is required.\n';
		if(trim(agencyType.value) == '')
			msg += '* Agency Type is required.\n';
		if(trim(agencyDesc.value) == '')
			msg += '* Agency Description is required.\n';
		
		if(msg.match("Code")){
			agencyCode.focus();
		}else if(msg.match("Type")){
			agencyType.focus();
		}else if(msg.match("Description")){
			agencyDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		var ck_numeric = /^[A-Za-z_0-9]{1,25}$/;
		if (!ck_numeric.test(agencyCode.value)) {
	    	if(trim(agencyCode.value) != ''){
		   	  alert("* Agency Code is invalid.");
		   	  agencyCode.focus();
	    	}
	    	DisButClass.prototype.EnbButMethod();
//	    	document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
		 }
	    document.getElementById("agencyMasterForm").action="agencyMasterAdd.do?method=updateAgency&agencyCode="+b+"&userlist="+DListValues;;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("agencyMasterForm").submit();
	    return true;
	
	}
}

function fnChangeCase(formName,fieldName)
{
	document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();


}

function fnSearch(val){ 
	
	DisButClass.prototype.DisButMethod();
	    if(document.getElementById("agencySearchCode").value=='' && document.getElementById("agencySearchDesc").value=='')
		{
			alert(val);

			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else
		{
	    	document.getElementById("agencyMasterSearchForm").action="agencyMasterBehind.do";
	    	document.getElementById("processingImage").style.display = '';
		    document.getElementById("agencyMasterSearchForm").submit(); 
			return true;
		}
		
	}	

function userMapping(){
	//alert(document.getElementById("agencyType").value)
	if(document.getElementById("agencyType").value=='EA')
	{
		document.getElementById("usrNameMapp").style.display='';
		document.getElementById("userMapping").style.display='';
	}else
	{
		document.getElementById("usrNameMapp").style.display='none';
		document.getElementById("userMapping").style.display='none';
		document.getElementById("userName").options.length = 0;
		document.getElementById("lbxUserIds").value='';
	}
}
