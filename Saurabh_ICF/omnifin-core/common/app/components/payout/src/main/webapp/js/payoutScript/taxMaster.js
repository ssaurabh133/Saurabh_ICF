
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchActivityCode").value=="" &&
	  document.getElementById("searchTaxCode").value=="" &&
	  document.getElementById("searchTaxPer").value=="" && 
	  document.getElementById("state").value=='')
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("taxMasterSearch").action="taxMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("taxMasterSearch").submit();
    return true;
    }
}

function addTaxMaster(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("taxMasterSearch").action=sourcepath+"/taxMasterDispatch.do?method=openAddTax";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("taxMasterSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveTax(){
	var msg1='',msg2='',msg3='',msg4='';
	var status=false;
	if(document.getElementById("taxName").value==''){
		status=true;
		msg1='* Tax Description is required \n';
	} 
	
	if(document.getElementById("taxPer").value==''){
		status=true;
		msg2='* Tax Percentage is required \n';
	}
	if(document.getElementById("activityCode").value==''){
		status=true;
		msg3='* Activity  is required \n';
	}
	if(document.getElementById("state").value==''){
		status=true;
		msg4='* State is required \n ';
	}
	
  if(status){
	  alert(msg1+msg2+msg3+msg4);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("taxMasterAdd").action=sourcepath+"/taxMasterDispatch.do?method=saveTax";	
	}else{
		document.getElementById("taxMasterAdd").action=sourcepath+"/taxMasterDispatch.do?method=updateTax";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("taxMasterAdd").submit();
	return true;
  }
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
