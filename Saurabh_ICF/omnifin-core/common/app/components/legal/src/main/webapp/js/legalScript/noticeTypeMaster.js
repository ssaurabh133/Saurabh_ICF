function newAddNoticeType(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("noticeTypeMasterForm").action=sourcepath+"/noticeTypeMaster.do?method=openAddNoticeType";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("noticeTypeMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}


function savenoticeType(){
	

	
	DisButClass.prototype.DisButMethod();
	var noticeTypeCode = document.getElementById("noticeTypeCode");
	
	var noticeTypeDesc = document.getElementById("noticeTypeDesc");
	
	 if(trim(noticeTypeCode.value) == ''||trim(noticeTypeDesc.value) == ''){
		 var msg= '';
 		if(trim(noticeTypeCode.value) == '')
 			msg += '* Notice Type Code is required.\n';
 		if(trim(noticeTypeDesc.value) == '')
 			msg += '* Notice Type Description is required.\n';
 		 
 		if(msg.match("Code")){
 			noticeTypeCode.focus();
 		
 		}else if(msg.match("Description")){
 			noticeTypeDesc.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }
	 else if(validate("noticeTypeMasterForm")){
 
		 document.getElementById("noticeTypeMasterForm").action="noticeTypeMasterAdd.do?method=saveNoticeTypeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("noticeTypeMasterForm").submit();

	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}



function searchnoticeType(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("noticeTypeCode").value==""&& document.getElementById("noticeTypeDesc").value=="")
	{
     alert(val);

     document.getElementById("noticeTypeCode").focus();
     DisButClass.prototype.EnbButMethod();

	return false; 
	}
	else{
	document.getElementById("noticeTypeMasterForm").action="noticeTypeMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("noticeTypeMasterForm").submit();
	return true;
	}
	}


function fnEditNoticeType(){
	
	DisButClass.prototype.DisButMethod();
	var noticeTypeCode = document.getElementById("noticeTypeCode");
	var noticeTypeDesc = document.getElementById("noticeTypeDesc");
	
	
	 if(trim(noticeTypeCode.value) == ''||trim(noticeTypeDesc.value) == ''){
		 var msg= '';
 		if(trim(noticeTypeCode.value) == '')
 			msg += '* Notice Type Code is required.\n';
 		if(trim(noticeTypeDesc.value) == '')
 			msg += '* Notice Type Description is required.\n';
 		 
 		if(msg.match("Code")){
 			noticeTypeCode.focus();
 		
 		}else if(msg.match("Description")){
 			noticeTypeDesc.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 	
 		return false;
	 
	 }
	 else if(validate("noticeTypeMasterForm")){
 
		 document.getElementById("noticeTypeMasterForm").action="noticeTypeMasterAdd.do?method=updateNotice";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("noticeTypeMasterForm").submit();
	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
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
	
	var noticeTypeCode = document.getElementById("noticeTypeCode");
	
  

 var errors = [];
 

 if (!ck_numeric.test(noticeTypeCode.value)) {
	 if(trim(noticeTypeCode.value) != ''){
	 	errors[errors.length] = "* Notice Type Code is invalid.";
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
	 	if(msg.match("Notice") ){
	 		noticeTypeCode.focus();
		
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}






