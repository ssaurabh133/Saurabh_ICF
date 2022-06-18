function newAddCourtType(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	document.getElementById("courtTypeMasterForm").action=sourcepath+"/courtTypeMaster.do?method=openAddCourtType";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("courtTypeMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}


function saveCourtType(){
	
	DisButClass.prototype.DisButMethod();
	
	var courtTypeCode = document.getElementById("courtTypeCode");
	var courtTypeDesc = document.getElementById("courtTypeDesc");
	var path = document.getElementById("contextPath").value;
	 if(trim(courtTypeCode.value) == ''||trim(courtTypeDesc.value) == ''){
		 var msg= '';
 		if(trim(courtTypeCode.value) == '')
 			msg += '* Court Type Code is required.\n';
 		if(trim(courtTypeDesc.value) == '')
 			msg += '* Court Type Description is required.\n';
		
 		alert(msg);
 		
 		
 		 
 		if(msg.match("Code")){
 			courtTypeCode.focus();
 			DisButClass.prototype.EnbButMethod();
 	 		document.getElementById("save").removeAttribute("disabled","true");
 	 		
 			
 	 		return false;
 		}
 		
 		else if(msg.match("Description")){
 			courtTypeDesc.focus();
 			DisButClass.prototype.EnbButMethod();
 	 		document.getElementById("save").removeAttribute("disabled","true");
 	 		
 			
 	 		return false;	
 		}
 		
 		
		
	 
	 }else if(validate("courtTypeMasterForm")){
		
			document.getElementById("courtTypeMasterForm").action=path+"/courtTypeMasterAdd.do?method=saveCourtTypeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("courtTypeMasterForm").submit();
			return true;
	}
	
}


function fnSearchCourtType(val){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("courtTypeCode").value==""&& document.getElementById("courtTypeDesc").value=="")
	{
     alert(val);
     
     document.getElementById("courtTypeCode").focus();

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
		document.getElementById("courtTypeMasterForm").action=sourcepath+"/courtTypeMasterBehind.do";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("courtTypeMasterForm").submit();
	return true;
	}
}

function fnEditCourtType(){
	
	DisButClass.prototype.DisButMethod();
	var courtTypeCode = document.getElementById("courtTypeCode");
	var courtTypeDesc = document.getElementById("courtTypeDesc");
	var path = document.getElementById("contextPath").value;
	 if(trim(courtTypeCode.value) == ''||trim(courtTypeDesc.value) == ''){
		 var msg= '';
 		if(trim(courtTypeCode.value) == '')
 			msg += '* Court Type Code is required.\n';
 		if(trim(courtTypeDesc.value) == '')
 			msg += '* Court Type Description is required.\n';
 		 
 		alert(msg);
 		
 		
		 
 		if(msg.match("Code")){
 			courtTypeCode.focus();
 		}
 		
 		else if(msg.match("Description")){
 			courtTypeDesc.focus();
 		}
		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		
		
 		return false;
	 
	 }else {
		
			document.getElementById("courtTypeMasterForm").action=path+"/courtTypeMasterAdd.do?method=updateCourt";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("courtTypeMasterForm").submit();
			return true;
	}
	
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;

function validate(formName){
	
	var courtTypeCode = document.getElementById("courtTypeCode");
	

	var errors = [];
 
	if (!ck_numeric.test(courtTypeCode.value)) {
		 if(trim(courtTypeCode.value) != ''){
		 	errors[errors.length] = "* Court Type Code is invalid.";
			DisButClass.prototype.EnbButMethod();
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
	 	if(msg.match("Court Type Code")){
	 		courtTypeCode.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}



