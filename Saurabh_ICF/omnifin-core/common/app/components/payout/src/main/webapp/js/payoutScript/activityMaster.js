
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchActivityCode").value=="" && document.getElementById("searchActivityDesc").value=='')
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("activityMasterSearch").action="activityMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("activityMasterSearch").submit();
    return true;
    }
}

function addActivityMaster(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("activityMasterSearch").action=sourcepath+"/activityMasterDispatch.do?method=openAddActivity";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("activityMasterSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveActivity(){
	var msg1='',msg2='',msg3='',msg4='';
	var status=false;
	if(document.getElementById("activityCode").value==''){
		status=true;
		msg1='* Activity Code is required \n';
	} 
	if(document.getElementById("activityDesc").value==''){
		status=true;
		msg2='* Activity Description is required \n';
	}
	if(document.getElementById("sourceId").value==''){
		status=true;
		msg3='* Source is required \n ';
	}
	if(document.getElementById("mappingCode").value==''){
		status=true;
		msg4='* Mapping Code is required \n';
	}
  if(status){
	  alert(msg1+msg2+msg3+msg4);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("activityMasterAdd").action=sourcepath+"/activityMasterDispatch.do?method=saveActivity";	
	}else{
		document.getElementById("activityMasterAdd").action=sourcepath+"/activityMasterDispatch.do?method=updateActivity";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("activityMasterAdd").submit();
	return true;
  }
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
