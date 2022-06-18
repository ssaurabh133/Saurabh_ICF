function trim(str) {
	return ltrim(rtrim(str));
	}

function isWhitespace(charToCheck) 
{
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
}
function ltrim(str) 
{ 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
}
function rtrim(str) 
{
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);

}


function newAdd()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("autoAllocationDefinitionSearch").action=sourcepath+"/autoAllocationDefinitionAction.do?method=openAddAutoAllocationDefinition";
	document.getElementById("autoAllocationDefinitionSearch").submit();
	return true;
	
}
function saveRecords()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	
	var npaStage = document.getElementById("npaStage");
	var type = document.getElementById("type");
	
	var repayType = document.getElementById("repayType").value;
	
	if(repayType=='I')
	{
		var installmentCharges = document.getElementById("installmentCharges");
		var preEmiCharges = document.getElementById("preEmiCharges");
		var otherCharges = document.getElementById("otherCharges");
	if(trim(npaStage.value) == ''||trim(type.value) == '')
	{
			var msg= '';
			if(trim(npaStage.value) == '')
			{
				msg += '* NPA Code is required.\n';
				npaStage.focus();
			}
			if(trim(type.value) == '')
			{
				msg += '* Allocation Type is required.\n';
				type.focus();
			}
			alert(msg);
			
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	if(trim(installmentCharges.value) == ''&& trim(preEmiCharges.value) == '' && trim(otherCharges.value) == '')
	{	
			alert('Please Enter Installment Charges / PreEmi Charges / Other Charges.');
			
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	else 
	{
		
	 if (!ck_din.test(installmentCharges.value) && (installmentCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 installmentCharges.focus();
			return false;
	 }

	 if (!ck_din.test(preEmiCharges.value) && (preEmiCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 preEmiCharges.focus();
			return false;
	 }
	 if (!ck_din.test(otherCharges.value) && (otherCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 otherCharges.focus();
			return false;
	 }
		
		if((installmentCharges.value<1 || installmentCharges.value >3) && (installmentCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			installmentCharges.focus();
			return false;
		}
		else if((preEmiCharges.value<1 || preEmiCharges.value >3) && (preEmiCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			preEmiCharges.focus();
			return false;
		}
		else if((otherCharges.value<1 || otherCharges.value >3) && (otherCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			otherCharges.focus();
			return false;
		}
		
		if((installmentCharges.value == preEmiCharges.value) && (installmentCharges.value!='') && (preEmiCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if((installmentCharges.value == otherCharges.value) && (installmentCharges.value!='') && (otherCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if((preEmiCharges.value == otherCharges.value) && (preEmiCharges.value!='') && (otherCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
//		var diff = 0;
//		diff = Math.abs(installmentCharges.value-preEmiCharges.value);
//		if(diff > 1)
//		{
//			alert("Please enter subsequent priority for Principal and Interest");
//			DisButClass.prototype.EnbButMethod();
//			principalValue.focus();
//			return false;
//		}
		
			document.getElementById("autoAllocationDefinitionForm").action=sourcepath+"/autoAllocationDefinitionAction.do?method=saveAutoAllocationDefDetails";
			document.getElementById("autoAllocationDefinitionForm").style.display = '';
			document.getElementById("autoAllocationDefinitionForm").submit();
			return true;
		}
	}
	else if(repayType=='N')
		{
			var principalCharges = document.getElementById("principalCharges");
			var interestCharges = document.getElementById("interestCharges");
			var otherCharges = document.getElementById("otherChargesNon");   
			if(trim(npaStage.value) == ''||trim(type.value) == '')
			{
					var msg= '';
					if(trim(npaStage.value) == '')
					{
						msg += '* NPA Code is required.\n';
						npaStage.focus();
					}
					if(trim(type.value) == '')
					{
						msg += '* Allocation Type is required.\n';
						type.focus();
					}
					alert(msg);
					
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			if(trim(principalCharges.value) == '' && trim(interestCharges.value) == '' && trim(otherCharges.value) == '')
			{	
					alert('Please Enter Principal Charges / Interest Charges / Other Charges.');
					
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			else 
			{
				
			 if (!ck_din.test(principalCharges.value) && (principalCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 principalCharges.focus();
					return false;
			 }

			 if (!ck_din.test(interestCharges.value) && (interestCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 interestCharges.focus();
					return false;
			 }
				
			 if (!ck_din.test(otherCharges.value) && (otherCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 otherCharges.focus();
					return false;
			 }
				if((principalCharges.value<1 || principalCharges.value >3) && (principalCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					principalCharges.focus();
					return false;
				}
				if((interestCharges.value<1 || interestCharges.value >3) && (interestCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					interestCharges.focus();
					return false;
				}
				else if((otherCharges.value<1 || otherCharges.value >3) && (otherCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					otherCharges.focus();
					return false;
				}
				if((principalCharges.value == interestCharges.value) && (principalCharges.value!='') && (interestCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((principalCharges.value == otherCharges.value) && (principalCharges.value!='') && (otherCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((interestCharges.value == otherCharges.value) && (interestCharges.value!='') && (otherCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
					document.getElementById("autoAllocationDefinitionForm").action=sourcepath+"/autoAllocationDefinitionAction.do?method=saveAutoAllocationDefDetails";
					document.getElementById("autoAllocationDefinitionForm").style.display = '';
					document.getElementById("autoAllocationDefinitionForm").submit();
					return true;
				}
			
		}
	else
	{
		DisButClass.prototype.EnbButMethod();
		 alert("Please select Repay type");
		 document.getElementById("save").removeAttribute("disabled","true");
			return false;
	}
	
}


function updateRecords()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	
	var npaStage = document.getElementById("npaStage");
	var type = document.getElementById("type");
	var repayType = document.getElementById("repayTypeHid").value;
	if(repayType=='I')
	{
		var installmentCharges = document.getElementById("installmentCharges");
		var preEmiCharges = document.getElementById("preEmiCharges");
		var otherCharges = document.getElementById("otherCharges");
	if(trim(npaStage.value) == ''||trim(type.value) == '')
	{
			var msg= '';
			if(trim(npaStage.value) == '')
			{
				msg += '* NPA Code is required.\n';
				npaStage.focus();
			}
			if(trim(type.value) == '')
			{
				msg += '* Allocation Type is required.\n';
				type.focus();
			}
			alert(msg);
			
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	if(trim(installmentCharges.value) == ''&& trim(preEmiCharges.value) == '' && trim(otherCharges.value) == '')
	{	
			alert('Please Enter Installment Charges / PreEmi Charges / Other Charges.');
			
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	else 
	{
		
	 if (!ck_din.test(installmentCharges.value) && (installmentCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 installmentCharges.focus();
			return false;
	 }

	 if (!ck_din.test(preEmiCharges.value) && (preEmiCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 preEmiCharges.focus();
			return false;
	 }
	 if (!ck_din.test(otherCharges.value) && (otherCharges.value!='')) {
		 DisButClass.prototype.EnbButMethod();
		 alert("Please enter numbers only from 1 to 3");
		 otherCharges.focus();
			return false;
	 }
		
		if((installmentCharges.value<1 || installmentCharges.value >3) && (installmentCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			installmentCharges.focus();
			return false;
		}
		else if((preEmiCharges.value<1 || preEmiCharges.value >3) && (preEmiCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			preEmiCharges.focus();
			return false;
		}
		else if((otherCharges.value<1 || otherCharges.value >3) && (otherCharges.value!=''))
		{
			alert("Please enter from 1 to 3 only");
			DisButClass.prototype.EnbButMethod();
			otherCharges.focus();
			return false;
		}
		
		if((installmentCharges.value == preEmiCharges.value) && (installmentCharges.value!='') && (preEmiCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if((installmentCharges.value == otherCharges.value) && (installmentCharges.value!='') && (otherCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if((preEmiCharges.value == otherCharges.value) && (preEmiCharges.value!='') && (otherCharges.value!=''))
		{
			alert("Please enter unique priority for every component");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
//		var diff = 0;
//		diff = Math.abs(installmentCharges.value-preEmiCharges.value);
//		if(diff > 1)
//		{
//			alert("Please enter subsequent priority for Principal and Interest");
//			DisButClass.prototype.EnbButMethod();
//			principalValue.focus();
//			return false;
//		}
		
		document.getElementById("autoAllocationDefinitionForm").action=sourcepath+"/autoAllocationDefinitionAction.do?method=updateAutoAllocationDefDetails";
			document.getElementById("autoAllocationDefinitionForm").style.display = '';
			document.getElementById("autoAllocationDefinitionForm").submit();
			return true;
		}
	}
	else if(repayType=='N')
		{
			var principalCharges = document.getElementById("principalCharges");
			var interestCharges = document.getElementById("interestCharges");
			var otherCharges = document.getElementById("otherChargesNon");     
			if(trim(npaStage.value) == ''||trim(type.value) == '')
			{
					var msg= '';
					if(trim(npaStage.value) == '')
					{
						msg += '* NPA Code is required.\n';
						npaStage.focus();
					}
					if(trim(type.value) == '')
					{
						msg += '* Allocation Type is required.\n';
						type.focus();
					}
					alert(msg);
					
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			if(trim(principalCharges.value) == '' && trim(interestCharges.value) == '' && trim(otherCharges.value) == '')
			{	
					alert('Please Enter Principal Charges / Interest Charges / Other Charges.');
					
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			else 
			{
				
			 if (!ck_din.test(principalCharges.value) && (principalCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 principalCharges.focus();
					return false;
			 }

			 if (!ck_din.test(interestCharges.value) && (interestCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 interestCharges.focus();
					return false;
			 }
			 if (!ck_din.test(otherCharges.value) && (otherCharges.value!='')) {
				 DisButClass.prototype.EnbButMethod();
				 alert("Please enter numbers only from 1 to 3");
				 otherCharges.focus();
					return false;
			 }	
			 if((principalCharges.value<1 || principalCharges.value >3) && (principalCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					principalCharges.focus();
					return false;
				}
				if((interestCharges.value<1 || interestCharges.value >3) && (interestCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					interestCharges.focus();
					return false;
				}
				else if((otherCharges.value<1 || otherCharges.value >3) && (otherCharges.value!=''))
				{
					alert("Please enter from 1 to 3 only");
					DisButClass.prototype.EnbButMethod();
					otherCharges.focus();
					return false;
				}
				if((principalCharges.value == interestCharges.value) && (principalCharges.value!='') && (interestCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((principalCharges.value == otherCharges.value) && (principalCharges.value!='') && (otherCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((interestCharges.value == otherCharges.value) && (interestCharges.value!='') && (otherCharges.value!=''))
				{
					alert("Please enter unique priority for every component");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			
				document.getElementById("autoAllocationDefinitionForm").action=sourcepath+"/autoAllocationDefinitionAction.do?method=updateAutoAllocationDefDetails";
					document.getElementById("autoAllocationDefinitionForm").style.display = '';
					document.getElementById("autoAllocationDefinitionForm").submit();
					return true;
				}
			
		}
	else
	{
		DisButClass.prototype.EnbButMethod();
		 alert("Please select Repay type");
		 document.getElementById("save").removeAttribute("disabled","true");
			return false;
	}
}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}

function autoAllocationSearch(val){ 
	DisButClass.prototype.DisButMethod();
	    if(document.getElementById("npaStage").value=='' && document.getElementById("type").value=='')
		{
			alert(val);	
			//document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
				return false;
		}

	    document.getElementById("autoAllocationDefinitionSearch").action="autoAllocationDefinitionAction.do?method=searchAutoAllocationData";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("autoAllocationDefinitionSearch").submit();
		
	}	
		

//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[0-9_a-zA-Z']{1,25}$/;
var ck_din = /^[0-9]{1,10}$/;



function validate1(formName){
	
	var principalValue = document.getElementById("principalValue");
	var interestValue = document.getElementById("interestValue");
	var chargeValue = document.getElementById("chargeValue");
  
alert("principalValue "+principalValue);
alert("interestValue "+interestValue);
alert("chargeValue "+chargeValue);
 var errors = [];

 if (ck_din.test(principalValue.value)) {
	 if(trim(principalValue.value) != ''){
		 errors[errors.length] = "* Principal Value is invalid.";
	 }
 }

 if (ck_din.test(interestValue.value)) {
	 if(trim(interestValue.value) != ''){
		 errors[errors.length] = "* Interest Value is invalid.";
	 }
 }
 if (ck_din.test(chargeValue.value)) {
	 if(trim(chargeValue.value) != ''){
		 errors[errors.length] = "* Charge Value is invalid.";
	 }
 }
 
 if (errors.length > 0) {
  reportErrors(errors);
  return false;
 }
 
 return true;
}

function reportErrors(errors){
	 var msg = "";
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += "\n" + errors[i];
	 }
	 	if(msg.match("Stage")){
	 		npaStage.focus();
		}else if(msg.match("Sequence")){
			sequenceNo.focus();
		}else if(msg.match("Value")){
			npaCriteriaValue.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}


function activeDiv()
{
	
	var repayType = document.getElementById("repayType").value;
//	alert("repayType : "+repayType);
	if(repayType=='I')
	{
		 document.getElementById("installmentDiv").style.display='';
		 document.getElementById("nonInstallmentDiv").style.display='none';
		 return true;
	}
	else if(repayType=='N')
	{
		document.getElementById("nonInstallmentDiv").style.display='';
		document.getElementById("installmentDiv").style.display='none';
		return true;
	}
	else
	{
		document.getElementById("nonInstallmentDiv").style.display='none';
		document.getElementById("installmentDiv").style.display='none';
		return true;
	}
		
}