
function fnSearchCode(val){
	//alert("In fnSearchCode");	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("codeSearchId").value=="" && document.getElementById("codeSearchDesc").value=='')
	{
     alert(val);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("actionCodeMaster").action="actionCodeMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("actionCodeMaster").submit();
    return true;
    }
	}


function newAddActionCode(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("actionCodeMaster").action=sourcepath+"/actionCodeMasterSearch.do?method=openAddActionCode";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("actionCodeMaster").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
	

}


function fnEditActionCode(codeId){
	//alert("In fnEditActionCode");
	if(validateActionCodeMasterAddDyanavalidatiorForm(document.getElementById("actionCodeMasterForm"))){
    document.getElementById("actionCodeMasterForm").action="actionCodeMasterAdd.do?method=updateActionCode&codeId="+codeId;
		document.getElementById("processingImage").style.display = '';
    document.getElementById("actionCodeMasterForm").submit();
	}
	else{
//		document.getElementById("save").removeAttribute("disabled","true");
		     	DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveActionCode()
{
	//alert("In saveActionCode");
	var sourcepath=document.getElementById("path").value;
	if(validateActionCodeMasterAddDyanavalidatiorForm(document.getElementById("actionCodeMasterForm"))){
	document.getElementById("actionCodeMasterForm").action=sourcepath+"/actionCodeMasterAdd.do?method=saveActionCodeDetails";
		document.getElementById("processingImage").style.display = '';
	document.getElementById("actionCodeMasterForm").submit();
	return true;
	}
	else{
//		document.getElementById("save").removeAttribute("disabled","true");
			     	DisButClass.prototype.EnbButMethod();
		return false;
	}
}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
