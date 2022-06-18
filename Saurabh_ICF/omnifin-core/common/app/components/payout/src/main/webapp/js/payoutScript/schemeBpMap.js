
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchBpName").value=="" &&
	  document.getElementById("searchSchemeName").value=="" )
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("schemeBpMapSearch").action=sourcepath+"/schemeBpMapBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeBpMapSearch").submit();
    return true;
    }
}

function addSchemeBpMap(){
	//alert("In addSchemeBpMap");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("schemeBpMapSearch").action=sourcepath+"/schemeBpMapDispatch.do?method=openAddSchemeBpMap";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeBpMapSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function saveSchemeBpMap(){
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
	if(document.getElementById("schemeName").value==''){
		status=true;
		msg3='* Scheme is required \n';
	}
	if(document.getElementById("specificTar").checked==true){
		
	if(document.getElementById("specificTime").value==''){
		status=true;
		msg4='* Specific Time is required \n';
	}
	if(document.getElementById("specificTarOn").value==''){
		status=true;
		msg6='* Specific target on is required \n ';
	}
	if(document.getElementById("targetForSt").value==''){
		status=true;
		msg5='* Target is required \n ';
	}
	if(document.getElementById("commissionForTarget").value==''){
		status=true;
		msg6='* Commission For Target is required \n ';
	}
	
	}
	
  if(status){
	  alert(msg1+msg2+msg3+msg4+msg5+msg6);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("schemeBpMapAdd").action=sourcepath+"/schemeBpMapDispatch.do?method=saveSchemeBpMap";	
	}else{
		document.getElementById("schemeBpMapAdd").action=sourcepath+"/schemeBpMapDispatch.do?method=updateSchemeBpMap";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("schemeBpMapAdd").submit();
	return true;
  }
}

function disableSpecific(){
	if(document.getElementById("specificTar").checked==true){
		/*document.getElementById("tr1").disabled=false;
		document.getElementById("tr2").disabled=false;*/
		///document.getElementById("specificTime").value="";
		document.getElementById("specificTarOn").value="";
		document.getElementById("targetForSt").value="";
		document.getElementById("commissionForTarget").value="";
		//document.getElementById("specificTime").removeAttribute("disabled","disabled");
		document.getElementById("specificTarOn").removeAttribute("disabled","disabled");
		document.getElementById("targetForSt").removeAttribute("disabled",false);
		document.getElementById("commissionForTarget").removeAttribute("disabled",false);
	
	}else{
	
		/*document.getElementById("tr1").disabled=true;
		document.getElementById("tr1").setAttribute("disabled",true);
		document.getElementById("tr2").disabled=true;*/
		//document.getElementById("specificTime").value="";
		document.getElementById("specificTarOn").value="";
		document.getElementById("targetForSt").value="";
		document.getElementById("commissionForTarget").value="";
		//document.getElementById("specificTime").setAttribute("disabled",true);
		document.getElementById("specificTarOn").setAttribute("disabled",true);
		document.getElementById("targetForSt").setAttribute("disabled",true);
		document.getElementById("commissionForTarget").setAttribute("disabled",true);
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
