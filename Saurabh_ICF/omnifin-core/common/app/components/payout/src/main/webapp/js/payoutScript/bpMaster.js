
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchActivityCode").value=="" &&
	  document.getElementById("searchBpName").value=="" )
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("bpSearch").action="bpMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bpSearch").submit();
    return true;
    }
}

function addBpMaster(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bpSearch").action=sourcepath+"/bpMasterDispatch.do?method=openAddBp";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bpSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveBP (){
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='',msg17='';
	var status=false;
	if(document.getElementById("bpName").value==''){
		status=true;
		msg1='* Business Partner name is required \n';
	} 
	if(document.getElementById("lbxActivityCode").value==''){
		status=true;
		msg2='* Activity  is required \n';
	}
	if(document.getElementById("bpAdd").value==''){
		status=true;
		msg3='* Business Partner address is required \n';
	}
	if(document.getElementById("country").value==''){
		status=true;
		msg4='* Business Partner Country is required \n';
	}
	if(document.getElementById("state").value==''){
		status=true;
		msg5='* Business Partner State is required \n ';
	}
	if(document.getElementById("dist").value==''){
		status=true;
		msg6='* Business Partner City is required \n ';
	}
	if(document.getElementById("bpMobile").value==''){
		status=true;
		msg7='* Business Partner Mobile is required \n ';
	}
	if(document.getElementById("cpName").value==''){
		status=true;
		msg8='* Contact Person Name is required \n ';
	}
	if(document.getElementById("cpAdd").value==''){
		status=true;
		msg9='* Contact Person Address is required \n ';
	}
	if(document.getElementById("cpCountry").value==''){
		status=true;
		msg10='* Contact Person Country is required \n ';
	}
	if(document.getElementById("cpState").value==''){
		status=true;
		msg11='* Contact Person State is required \n ';
	}
	if(document.getElementById("cpDist").value==''){
		status=true;
		msg12='* Contact Person City is required \n ';
	}
	if(document.getElementById("cpMobile").value==''){
		status=true;
		msg13='* Contact Person Mobile is required \n ';
	}
	if(document.getElementById("sourceId").value==''){
		status=true;
		msg14='* Mapping Source  is required \n ';
	}
	if(document.getElementById("mappingCode").value==''){
		status=true;
		msg15='* Mapping Code is required \n ';
	}
	if((document.getElementById("bpAdd").value).length>200){
		status=true;
		msg16='* Business Partner Address Should be less then 200 Charecter  \n ';
	}
	if((document.getElementById("cpAdd").value).length>200){
		status=true;
		msg17='* Contact Person Address Should be less then 200 Charecter \n ';
	}
  if(status){
	  alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9+msg10+msg11+msg12+msg13+msg14+msg15+msg16+msg17);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("bpMasterAdd").action=sourcepath+"/bpMasterDispatch.do?method=saveBP";	
	}else{
		document.getElementById("bpMasterAdd").action=sourcepath+"/bpMasterDispatch.do?method=updateBP";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bpMasterAdd").submit();
	return true;
  }
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
