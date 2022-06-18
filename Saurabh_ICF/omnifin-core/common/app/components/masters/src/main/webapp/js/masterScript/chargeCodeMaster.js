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

function chargeCodeSave()
{
	DisButClass.prototype.DisButMethod();
	var chargeDescription = document.getElementById("chargeDescription");
	var dueReceiptBasis = document.getElementById("dueReceiptBasis").value;
	//alert("dueReceiptBasis:::"+dueReceiptBasis);
	if(trim(dueReceiptBasis) == ""){
		alert ("Please enter Due/Receipt Basis");
		DisButClass.prototype.EnbButMethod();
		return false;
	}	
	if(trim(chargeDescription.value) == ''){
			var msg= '';
			if(trim(chargeDescription.value) == ''){
				msg += '* Charge Description is required.\n';
				chargeDescription.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("button").removeAttribute("disabled","true");
			return false;
		}else{
			 var sourcepath=document.getElementById("contextPath").value;
			 document.getElementById("chargeCodeMasterForm").action=sourcepath+"/chargeCodeAction.do?method=saveChargeCode";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("chargeCodeMasterForm").submit();
			 return true;
		}

}

function chargeCodeModify(b)
{
		var dueReceiptBasis = document.getElementById("dueReceiptBasis").value;
		//alert("dueReceiptBasis:::"+dueReceiptBasis);
		if(trim(dueReceiptBasis) == ""){
			alert ("Please enter Due/Receipt Basis");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("contextPath").value;

				document.getElementById("chargeCodeMasterForm").action=sourcepath+"/chargeCodeAction.do?method=saveModifyDetails&chargeCode="+b;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("chargeCodeMasterForm").submit();
				return true;
	
}


function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("chargeCodeMasterSearch").action=sourcepath+"/chargeCodeMasterSearch.do?method=addChargeCodeDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("chargeCodeMasterSearch").submit();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}

function modifyDetails(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("chargeCodeMasterSearch").action=sourcepath+"/chargeCodeMasterSearch.do?method=modifyDetails&chargeCode="+b;
	document.getElementById("chargeCodeMasterSearch").submit();
	
}

function fnSearch(val){

	DisButClass.prototype.DisButMethod();
	if(document.getElementById("chargeCode").value==""&&document.getElementById("chargeSearchDescription").value=="")
	{
     alert(val);
     DisButClass.prototype.EnbButMethod();
     //document.getElementById("save").removeAttribute("disabled","true");
	 return false; 
	}
	else{
	document.getElementById("chargeCodeMasterSearch").action="chargeCodeSearchBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("chargeCodeMasterSearch").submit();
	return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function disabletaxapp()
{
	
	var taxapp = document.getElementById("taxapp").value;
	var taxrate1= document.getElementById("taxrate1");
	var taxrate2= document.getElementById("taxrate2");
	
	 if(taxapp == 'Y')
	{
		document.getElementById("taxrate1").removeAttribute("disabled",true);
		document.getElementById("taxrate2").removeAttribute("disabled",true);
		
		
	}
	else if(taxapp == 'N')
	{
	document.getElementById("taxrate1").value="";
	document.getElementById("taxrate2").value="";

	document.getElementById("taxrate1").setAttribute("disabled",true);
	document.getElementById("taxrate2").setAttribute("disabled",true);
	}
	
	
	
	else
	{
    		 return true;
	}
}



