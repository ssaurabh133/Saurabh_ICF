function trim(str) {
return ltrim(rtrim(str));
}
function isWhitespace(charToCheck) {
var whitespaceChars = " \t\n\r\f";
return (whitespaceChars.indexOf(charToCheck) != -1);
}

function ltrim(str) { 
for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
return str.substring(k, str.length);
}
function rtrim(str) {
for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
return str.substring(0,j+1);
}

function bankBranchSubmit()
{
	DisButClass.prototype.DisButMethod();
	var bankBranchName = document.getElementById("bankBranchName");
	var bank = document.getElementById("bank");
	

	//if(trim(bankBranchCode.value) == ''||trim(bankBranchName.value) == ''||trim(bank.value) == ''){
	if(trim(bankBranchName.value) == ''||trim(bank.value) == ''){
		var msg= '';
	//	if(trim(bankBranchCode.value) == '')
	//		msg += '* Bank Branch code is required.\n';
		if(trim(bankBranchName.value) == '')
			msg += '* Bank Branch Name is required.\n';
		if(trim(bank.value) == '')
			msg += '* Bank is required.\n';
		
		if(msg.match(/code/g)){
		//	bankBranchCode.focus();
		}else if(msg.match("Name")){
			bankBranchName.focus();
		}else if(trim(bank.value)!=''){
			bank.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else if(validate("bankBranchMasterForm")){
		
		   document.getElementById("bankBranchMasterForm").action="bankbranchAction.do?method=saveBankBranch";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("bankBranchMasterForm").submit();
	
	 }
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		return false;
	 }

}

function bankBranchModify(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;

	var bankBranchCode = document.getElementById("bankbranchSearchId").value;
	var bankBranchName = document.getElementById("bankBranchName");
	var bank = document.getElementById("bank");
	/*var branchMICRCode = document.getElementById("branchMICRCode");
	var branchIFCSCode = document.getElementById("branchIFCSCode");*/

	
	// if(trim(bankBranchCode.value) == ''||trim(bankBranchName.value) == ''||trim(bank.value) == ''){
	if(trim(bankBranchName.value) == ''||trim(bank.value) == ''){
		var msg= '';
	/*	if(trim(bankBranchCode.value) == '')
			msg += '* Bank Branch code is required.\n';
	*/	if(trim(bankBranchName.value) == '')
			msg += '* Bank Branch Name is required.\n';
		if(trim(bank.value) == '')
			msg += '* Bank is required.\n';
		
		
		if(msg.match(/code/g)){
		//	bankBranchCode.focus();
		}else if(msg.match("Name")){
			bankBranchName.focus();
		}else if(trim(bank.value)!=''){
			bank.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else if(validate("bankBranchMasterForm")){
		
		   document.getElementById("bankBranchMasterForm").action=sourcepath+"/bankbranchAction.do?method=saveModifyDetails&bankBranchId="+bankBranchCode;
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("bankBranchMasterForm").submit();
		   return true;
	 }
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		return false;
	 }  	
}


function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	document.getElementById("bankBranchMasterSearch").action=sourcepath+"/bankbranchMasterSearch.do?method=addBranchDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bankBranchMasterSearch").submit();
	
	document.getElementById("save").removeAttribute("disabled","true");
	return false;
		
}


function modifyDetails(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bankBranchMasterSearch").action=sourcepath+"/bankbranchMasterSearch.do?method=modifyDetails&bankbranchSearchCode="+b;
	document.getElementById("bankBranchMasterSearch").submit();
	
}

function fnSearch(val,val1){
	DisButClass.prototype.DisButMethod();
	var branchMICRCode = document.getElementById("branchMICRCode").value;
	var branchIFCSCode = document.getElementById("branchIFCSCode").value;
	
	if(document.getElementById("bankBranchSearchCode").value=="" && document.getElementById("bankBranchSearchName").value=="" && document.getElementById("branchMICRCode").value=="" && document.getElementById("branchIFCSCode").value=="")
	{
     alert(val);
     DisButClass.prototype.EnbButMethod();
     //document.getElementById("save").removeAttribute("disabled","true");
	 return false; 
	}
	/*else if(document.getElementById("bankBranchSearchName").value=="")
	{
     alert(val1);
     DisButClass.prototype.EnbButMethod();
     //document.getElementById("save").removeAttribute("disabled","true");
	 return false; 
	}*/
	else{
	document.getElementById("bankBranchMasterSearch").action="bankBranchSearchBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bankBranchMasterSearch").submit();
	return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}


function clearStateLovChild()
{
	alert("ok");
	window.opener.document.getElementById("dist").value="";
 	window.opener.document.getElementById("txtDistCode").value="";
 	window.close();	
}

function clearCountryLovChild()
{
	alert("ok");
	window.opener.document.getElementById("state").value="";
 	window.opener.document.getElementById("txtStateCode").value="";
	window.opener.document.getElementById("dist").value="";
 	window.opener.document.getElementById("txtDistCode").value="";
 	window.close();
		
}



//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;



function validate(formName){
	var bankBranchCode = document.getElementById("bankBranchCode");
	var branchMICRCode = document.getElementById("branchMICRCode");
	var branchIFCSCode = document.getElementById("branchIFCSCode");
	var email = document.getElementById("email");
  

 var errors = [];
 /*
 if (!ck_numeric.test(bankBranchCode.value)) {
	 if(trim(bankBranchCode.value) != ''){
	 	errors[errors.length] = "* Bank Branch Code is invalid.";
	 }
 }
 */
 if (!ck_numeric.test(branchMICRCode.value)) {
	 if(trim(branchIFCSCode.value) != ''){
		 errors[errors.length] = "* Branch MICR Code is invalid.";
	 }
}
 if (!ck_numeric.test(branchIFCSCode.value)) {
	 if(trim(branchIFCSCode.value) != ''){
		 errors[errors.length] = "* Branch IFCS Code is invalid.";
	 }
 }

 if (!san_email.test(email.value)) {
	 if(trim(email.value) != ''){
		 errors[errors.length] = "* E-Mail Id is invalid.";
	 }
	 
if(branchIFCSCode.value!=''){
	var str=branchIFCSCode.value;
	if(str.length!='11'){
		errors[errors.length] = "* IFSC code should be 11 digits.";
	}
		 
	 }

if(branchMICRCode.value!=''){
	var str=branchMICRCode.value;
	if(str.length!='9'){
		errors[errors.length] = "* MICR code should be 9 digits.";
	}
		 
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
 	if(msg.match("Bank")){
 		bankBranchCode.focus();
	}else if(msg.match("MICR")|| msg.match("9")){
		branchMICRCode.focus();
	}else if(msg.match("IFCS") || msg.match("11") ){
		branchIFCSCode.focus();
	}else if(msg.match("E-Mail")){
		email.focus();
	}
 
 alert(msg);
 document.getElementById("save").removeAttribute("disabled","true");
}

function isNumberKey(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only Numeric Value Allowed Here");
	return false;
}
	return true;
}
