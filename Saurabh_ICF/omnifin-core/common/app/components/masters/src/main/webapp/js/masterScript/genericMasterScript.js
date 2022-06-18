function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("genericMasterSearch").action=sourcepath+"/genericMasterSearchDispatch.do?method=newSaveGenericMaster";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("genericMasterSearch").submit();
	
}

function fnSearch(val){ 

	DisButClass.prototype.DisButMethod();
    if(document.getElementById("genericSearchKey").value=='' && document.getElementById("searchDescription").value=='')
    {
    	alert(val);
    	DisButClass.prototype.EnbButMethod();
//    	document.getElementById("save").removeAttribute("disabled","true");
    	return false;
    }
    document.getElementById("genericMasterSearch").action="genericMasterBehind.do";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("genericMasterSearch").submit();
   
	
}

function modifyDetails(a,b,c)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("genericMasterSearch").action=sourcepath+"/genericMasterSearchDispatch.do?method=modifyDetails&genericSearchKey="+a+"&parentValue="+b+"&value="+c;
	 document.getElementById("processingImage").style.display = '';
	document.getElementById("genericMasterSearch").submit();
	return true;
}

function saveModifyDetails(a,b,c)
{

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if (validateGenericMasterAddDynaValidatorForm(document.getElementById("genericMasterForm"))){
		   document.getElementById("genericMasterForm").action=sourcepath+"/genericAction.do?method=saveModifyDetails";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("genericMasterForm").submit();
	   return true;
	   }
	else{
    	DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	  	
}

function IsNumber(inputNumber)
{
   return (new RegExp("^\\d+$").test(inputNumber));
}

function genericSave()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var val=document.getElementById('lbxGenericId').value;
	var val1=document.getElementById('genericval').value;
	var genericval=document.getElementById('genericval').value;
	var ck_alphaNumeric = /^[A-Za-z0-9-_]{1,50}$/;
	
	if (validateGenericMasterAddDynaValidatorForm(document.getElementById("genericMasterForm"))){
		
		
		
		if(val=="SPONSOR_BANK_CODE")
		{
			if(!IsNumber(val1))
			{
				alert("Please enter number only in value.");
			   	DisButClass.prototype.EnbButMethod();
//				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			
		}
		 if (!ck_alphaNumeric.test(genericval)) {
		    	
			   	alert(" Special characters not allowed for Value.");
			 	DisButClass.prototype.EnbButMethod();
//				document.getElementById("save").removeAttribute("disabled","true");
				return false;
		    	
			 }
		
			document.getElementById("genericMasterForm").action=sourcepath+"/genericAction.do?method=saveGenericMaster";
			document.getElementById("processingImage").style.display = '';
		   document.getElementById("genericMasterForm").submit();
		   
		   return true;
	   }
	
	else{
	 	DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	 	
}

function fnChangeCase(formName,fieldName)
{
	document.forms.formName.elements[fieldname];
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}




function gmChangeCase(fieldName)
{
    document.getElementById(fieldName).value=(document.getElementById(fieldName).value).toUpperCase();
    return (document.getElementById(fieldName).value).toUpperCase();

}
