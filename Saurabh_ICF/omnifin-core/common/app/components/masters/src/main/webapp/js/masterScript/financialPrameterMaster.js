

function finAdd(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("financialPramForm").action=sourcepath+"/finPramMasterSearch.do?method=openAddFinPram";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("financialPramForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod()
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}

function fnFinSave()
{
	//sachin start
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var pramType=document.getElementById("pramType").value;
	var subType=document.getElementById("subType");
	
	if (document.getElementById("autoCalculated").checked==true){
		
	var expr = document.getElementById("finExpression").value;
	var sourcepath=document.getElementById("path").value;
//	var paramCodes = document.getElementById("finAllParamCodes").value;
	var finOperator = document.getElementById("finOperator").value;
	var parameterCode = document.getElementById("finParameterCode").value;
	var msg= '';
	if(expr=='' || finOperator=='' || parameterCode==''){
 		if(parameterCode == '')
 			msg += '* Parameter Code is required.\n';
 		if(finOperator == '')
 			msg += '* Operator is required.\n';
 		if(expr == '')
 			msg += '* Expression is required.\n';
 		 		
 		if(msg.match("Parameter")){
 			document.getElementById("finParameterCode").focus();
 		}else if(msg.match("Operator")){
 			document.getElementById("finOperator").focus();
 		}else if(msg.match("Expression")){
 			document.getElementById("finExpression").focus();
 		}
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	
	
	}
	
	//sachin end
	
	//alert(sourcepath);
	if(validateFinancialPramMasterAddDyanavalidatiorForm(document.getElementById("finPramForm"))){
		if(pramType=='B' && (subType.value==null || subType.value=='')){
			alert("Please select Sub Type");
			DisButClass.prototype.EnbButMethod()
			return false;
		}
		
		else{
		document.getElementById("finPramForm").action=sourcepath+"/finMasterAdd.do?method=saveFinPramDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("finPramForm").submit();
		return true;
		}
	}
	else
	{
		DisButClass.prototype.EnbButMethod()
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function fnFinEdit()
{

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var pramType=document.getElementById("pramType").value;
	var subType=document.getElementById("subType");
	if (document.getElementById("autoCalculated").checked==true){
		var expr = document.getElementById("finExpression").value;
		var sourcepath=document.getElementById("path").value;

		var finOperator = document.getElementById("finOperator").value;
		var parameterCode = document.getElementById("finParameterCode").value;
		var msg= '';
		if(expr=='' || finOperator=='' || parameterCode==''){
	 		if(parameterCode == '')
	 			msg += '* Parameter Code is required.\n';
	 		if(finOperator == '')
	 			msg += '* Operator is required.\n';
	 		if(expr == '')
	 			msg += '* Expression is required.\n';
	 		 		
	 		if(msg.match("Parameter")){
	 			document.getElementById("finParameterCode").focus();
	 		}else if(msg.match("Operator")){
	 			document.getElementById("finOperator").focus();
	 		}else if(msg.match("Expression")){
	 			document.getElementById("finExpression").focus();
	 		}
	 		alert(msg);
	 		document.getElementById("save").removeAttribute("disabled","true");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;
		}
		
		
		}


	if(validateFinancialPramMasterAddDyanavalidatiorForm(document.getElementById("finPramForm"))){
		if(pramType=='B' && (subType.value==null || subType.value=='')){
			alert("Please select Sub Type");
			DisButClass.prototype.EnbButMethod()
			return false;
		}
		else{
		document.getElementById("finPramForm").action=sourcepath+"/finMasterAdd.do?method=updateFinPramDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("finPramForm").submit();
		return true;
		}
	}
	else
	{
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}


function fnpramSearch(val){ 
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("pramSearchCode").value=='' && document.getElementById("pramSearchName").value==''&& document.getElementById("pramTypeSearch").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
    else
	{
    	document.getElementById("financialPramForm").action="financialPramBehind.do";
    	document.getElementById("processingImage").style.display = '';
	    document.getElementById("financialPramForm").submit(); 
		return true;
	}
	
}	

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}

//Start by sachin
function varifyValue(){
	var ascii=document.getElementById("pramCode").value;	
	var varify=ascii.charCodeAt(0);
	if(varify>=48 && varify<=57)
	{
		alert("First letter of Parameter code could not be Numeric");
		document.getElementById("pramCode").value="";
		return false;
		
	}
	else
	{
		return true;
		}
}
//end by sachin

function changePramType(){
	var pramType=document.getElementById("pramType").value;
	if(pramType=='B')
	{
	document.getElementById("subTypeLabel").style.display="";
	document.getElementById("subTypeValue").style.display="";
    }else{
	document.getElementById("subTypeLabel").style.display="none";
	document.getElementById("subTypeValue").style.display="none";
          }
}

function addField(){
	if (document.getElementById("autoCalculated").checked==true){
		document.getElementById("financialCapturing").style.display='';
		document.getElementById("saveButton").style.display='none';

	}
	else{
		document.getElementById("financialCapturing").style.display='none';
		document.getElementById("saveButton").style.display='';
	}

}
function clearRatioExpression()
{
	document.getElementById("finExpression").value ="";
	document.getElementById("finConstant").value ="";
	document.getElementById("finOperator").value ="";
	document.getElementById("finParameterCode").value ="";	
}

function makeExpression(val)
{
	var expr='';
	if(val=='finParameterCode')
	{		
		var expr=document.getElementById("finExpression").value;
		document.getElementById("finExpression").value = expr + document.getElementById("finParameterCode").value;
	}
	else if(val=='finOperator')
	{
		var expr=document.getElementById("finExpression").value;
		document.getElementById("finExpression").value = expr + document.getElementById("finOperator").value;
	}

	else
	{
		var expr=document.getElementById("finExpression").value;
		document.getElementById("finExpression").value = expr + document.getElementById("finConstant").value;
	}
	
}
function clearExpression()
{
	//alert("ok"+document.getElementById("flag").value);
	if(document.getElementById("flag").value=="true")
	{	//alert("ok");
		document.getElementById("finExpression").value='';
		document.getElementById("flag").value=false;
	}

}

