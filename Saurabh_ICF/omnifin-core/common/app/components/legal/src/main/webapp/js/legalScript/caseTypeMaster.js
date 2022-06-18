function newAddCaseType(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("caseTypeMasterForm").action=sourcepath+"/caseTypeMaster.do?method=openAddCaseType";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseTypeMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();

	

}


function saveCaseType(){
	
	DisButClass.prototype.DisButMethod();
	
	var caseTypeCode = document.getElementById("caseTypeCode");
	var caseTypeDesc = document.getElementById("caseTypeDesc");
	
	 if(trim(caseTypeCode.value) == ''||trim(caseTypeDesc.value) == ''){
		 var msg= '';
		 if(trim(caseTypeCode.value) == '')
	 			msg += '* Case Type Code is required.\n';
 		if(trim(caseTypeDesc.value) == '')
 			msg += '* Case Type Description is required.\n';
 		
 		 		 		
 		if(msg.match("Code")){
 			caseTypeCode.focus();
 		}else if(msg.match("Description")){
 			caseTypeDesc.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }
	 else if(validate("caseTypeMasterForm")){
 
		 document.getElementById("caseTypeMasterForm").action="caseTypeMasterAdd.do?method=saveCaseTypeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("caseTypeMasterForm").submit();

	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}
function fnUpdateCaseType(){
	
	DisButClass.prototype.DisButMethod();
	var caseTypeCode = document.getElementById("caseTypeCode");
	var caseTypeDesc = document.getElementById("caseTypeDesc");
	
	if(trim(caseTypeCode.value) == ''||trim(caseTypeDesc.value) == ''){
		 var msg= '';
		 if(trim(caseTypeCode.value) == '')
	 			msg += '* Case Type Code is required.\n';
		if(trim(caseTypeDesc.value) == '')
			msg += '* Case Type Description is required.\n';
		
 		 		 		
 		if(msg.match("Code")){
 			caseTypeCode.focus();
 		}else if(msg.match("Description")){
 			caseTypeDesc.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
	 else if(validate("caseTypeMasterForm")){


			document.getElementById("caseTypeMasterForm").action="caseTypeMasterAdd.do?method=updateCase";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("caseTypeMasterForm").submit();

	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}


function fnSearchCaseType(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("caseTypeMasterForm").action="caseTypeMasterBehind.do";
	if(document.getElementById("caseTypeCode").value=='' && document.getElementById("caseTypeDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("caseTypeMasterForm").submit();
	return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;


function validate(formName){
	
	var caseTypeCode = document.getElementById("caseTypeCode");
	
  

 var errors = [];
 

 if (!ck_numeric.test(caseTypeCode.value)) {
	 if(trim(caseTypeCode.value) != ''){
	 	errors[errors.length] = "* Case Type Code is invalid.";
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
	 	if(msg.match("Case Type Code") ){
	 		caseTypeCode.focus();
		
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}




