function newAddLawFirm(){
	
	
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("lawFirmMasterForm").action=sourcepath+"/lawFirmMaster.do?method=openAddLawFirm";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("lawFirmMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}
 

// add by nazia --->>
function fnSearchLawFirm(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("lawFirmMasterForm").action="lawFirmMasterBehind.do";
	if(document.getElementById("lawFirmCode").value=='' && document.getElementById("lawFirmDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("lawFirmMasterForm").submit();
	return true;
	}
}



function fnUpdateLawFirm(){
	
	DisButClass.prototype.DisButMethod();
	var lawFirmCode = document.getElementById("lawFirmCode");
	var lawFirmDesc = document.getElementById("lawFirmDesc");
	var branchDesc = document.getElementById("lbxBranchIds");
	var constitutionOfFirm = document.getElementById("constitutionOfFirm");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");
	var country = document.getElementById("country");
	var state = document.getElementById("state");
	var dist = document.getElementById("dist");
	var zipCode = document.getElementById("zipCode");
	var phone1 = document.getElementById("phone1");
	var panNo = document.getElementById("panNo");
	var sapCode = document.getElementById("sapCode");
	var bankAccNo = document.getElementById("bankAccNo");
	var mobileNo = document.getElementById("mobileNo");
	var emailId = document.getElementById("emailId")
	var lbxBranchIds = document.getElementById("lbxBranchIds");
	var userButton1 = document.getElementById("userButton1");
	var txtCountryCode = document.getElementById("txtCountryCode");
	var countryButton = document.getElementById("countryButton");
	var stateButton = document.getElementById("stateButton");
	var txtStateCode = document.getElementById("txtStateCode");
	var cityButton = document.getElementById("cityButton");
	var txtDistCode = document.getElementById("txtDistCode");
	var dateOfAppointment = document.getElementById("dateOfAppointment");
	
	
	
	if(trim(lawFirmCode.value) == ''||trim(lawFirmDesc.value) == ''||trim(branchDesc.value) == ''
		 ||trim(constitutionOfFirm.value) == ''||trim(address1.value) == ''||trim(address2.value) == ''
			 ||trim(country.value) == ''||trim(state.value) == ''||trim(dist.value) == ''
				 ||trim(zipCode.value) == ''||trim(phone1.value) == ''||trim(panNo.value) == ''||trim(mobileNo.value) == ''||trim(emailId.value) == ''
					 ||trim(bankAccNo.value) == '' ||trim(lbxBranchIds.value) == ''||trim(txtCountryCode.value) == ''
						 ||trim(txtStateCode.value) == ''||trim(txtDistCode.value) == ''||trim(dateOfAppointment.value) == '')
	 {
		 var msg= '';
		 if(trim(lawFirmCode.value) == '')
	 			msg += '* Law Firm Code is required.\n';
		if(trim(lawFirmDesc.value) == '')
			msg += '* Description is required.\n';
		
		if(trim(branchDesc.value) == ''|| trim(lbxBranchIds.value) == '')
			msg += '* Branch is required.\n';
		
		if(trim(constitutionOfFirm.value) == '')
			msg += '* Constitution is required.\n';
		
		if(trim(address1.value) == '')
			msg += '* Address1 is required.\n';
		
		if(trim(address2.value) == '')
			msg += '* Address2 is required.\n';
		
		if(trim(country.value) == '' || trim(txtCountryCode.value) == '')
			msg += '* Country is required.\n';
		
		if(trim(state.value) == '' || trim(txtStateCode.value) == '')
			msg += '* State is required.\n';
		
		if(trim(dist.value) == '' || trim(txtDistCode.value) == '')
			msg += '* City is required.\n';
		
		if(trim(zipCode.value) == '')
			msg += '* ZipCode is required.\n';
		
		if(trim(phone1.value) == '')
			msg += '* Phone1 is required.\n';
		
		if(trim(panNo.value) == '')
			msg += '* Pan No. is required.\n';
		
		if(trim(mobileNo.value) == '')
			msg += '* Mobile No. is required.\n';
		
		if(trim(emailId.value) == '')
			msg += '* Email Id is required.\n';
		
		/*if(trim(sapCode.value) == '')
			msg += '* Sap Code is required.\n';*/
		
		if(trim(bankAccNo.value) == '')
			msg += '* Bank Account No is required.\n';
		
		if(trim(dateOfAppointment.value) == '')
			msg += '* Date Of Appointment is required.\n';
		

		if(msg.match("Code")){
			lawFirmCode.focus();
		}
		else if(msg.match("Description")){
			lawFirmDesc.focus();
		
		}else if(msg.match("Branch")){
			userButton1.focus();
			
		}else if(msg.match("Constitution")){
			constitutionOfFirm.focus();
			
		}else if(msg.match("Address1")){
			address1.focus();
			
		}else if(msg.match("Address2")){
			address2.focus();
			
		}else if(msg.match("Country")){
			countryButton.focus();
			
		}else if(msg.match("State")){
			stateButton.focus();
			
		}else if(msg.match("City")){
			cityButton.focus();
			
		}else if(msg.match("ZipCode")){
			zipCode.focus();
			
		}else if(msg.match("Phone1")){
			phone1.focus();
			
		}else if(msg.match("Pan")){
			panNo.focus();
			
		}
		
		else if(msg.match("Mobile")){
			mobileNo.focus();
			
		}
		else if(msg.match("Email")){
			emailId.focus();
			
		}
		
		/*else if(msg.match("SapCode")){
			sapCode.focus();
			
		}*/else if(msg.match("BankAccNo")){
			bankAccNo.focus();
		}
		else if(msg.match("dateOfAppointment")){
			dateOfAppointment.focus();
		}
		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else if(validate("lawFirmMasterForm")){
		
			document.getElementById("lawFirmMasterForm").action="lawFirmMasterAdd.do?method=updateLawFirm";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("lawFirmMasterForm").submit();
			return true;
	}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}




function saveLawFirm(){
	
	DisButClass.prototype.DisButMethod();
	var lawFirmCode = document.getElementById("lawFirmCode");
	var lawFirmDesc = document.getElementById("lawFirmDesc");
	var branchDesc = document.getElementById("lbxBranchIds");
	var constitutionOfFirm = document.getElementById("constitutionOfFirm");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");
	var country = document.getElementById("country");
	var state = document.getElementById("state");
	var dist = document.getElementById("dist");
	var zipCode = document.getElementById("zipCode");
	var phone1 = document.getElementById("phone1");
	var panNo = document.getElementById("panNo");
	var sapCode = document.getElementById("sapCode");
	var bankAccNo = document.getElementById("bankAccNo");
	var mobileNo = document.getElementById("mobileNo");
	var emailId = document.getElementById("emailId");
	var lbxBranchIds = document.getElementById("lbxBranchIds");
	var userButton1 = document.getElementById("userButton1");
	var txtCountryCode = document.getElementById("txtCountryCode");
	var countryButton = document.getElementById("countryButton");
	var stateButton = document.getElementById("stateButton");
	var txtStateCode = document.getElementById("txtStateCode");
	var cityButton = document.getElementById("cityButton");
	var txtDistCode = document.getElementById("txtDistCode");
	var dateOfAppointment = document.getElementById("dateOfAppointment");
	
	
	
	
	if(trim(lawFirmCode.value) == ''||trim(lawFirmDesc.value) == ''||trim(branchDesc.value) == ''
		 ||trim(constitutionOfFirm.value) == ''||trim(address1.value) == ''||trim(address2.value) == ''
			 ||trim(country.value) == ''||trim(state.value) == ''||trim(dist.value) == ''
				 ||trim(zipCode.value) == ''||trim(phone1.value) == ''||trim(panNo.value) == ''||trim(mobileNo.value) == ''||trim(emailId.value) == ''
					||trim(bankAccNo.value) == '' ||trim(lbxBranchIds.value) == ''||trim(txtCountryCode.value) == ''
						 ||trim(txtStateCode.value) == ''||trim(txtDistCode.value) == ''||trim(dateOfAppointment.value)=='')
	 {
		 var msg= '';
		 if(trim(lawFirmCode.value) == '')
	 			msg += '* Law Firm Code is required.\n';
		if(trim(lawFirmDesc.value) == '')
			msg += '* Description is required.\n';
		
		if(trim(branchDesc.value) == ''|| trim(lbxBranchIds.value) == '')
			msg += '* Branch is required.\n';
		
		if(trim(constitutionOfFirm.value) == '')
			msg += '* Constitution is required.\n';
		
		if(trim(address1.value) == '')
			msg += '* Address1 is required.\n';
		
		if(trim(address2.value) == '')
			msg += '* Address2 is required.\n';
		
		if(trim(country.value) == '' || trim(txtCountryCode.value) == '')
			msg += '* Country is required.\n';
		
		if(trim(state.value) == '' || trim(txtStateCode.value) == '')
			msg += '* State is required.\n';
		
		if(trim(dist.value) == '' || trim(txtDistCode.value) == '')
			msg += '* City is required.\n';
		
		if(trim(zipCode.value) == '')
			msg += '* Zip Code is required.\n';
		
		if(trim(phone1.value) == '')
			msg += '* Phone1 is required.\n';
		
		if(trim(panNo.value) == '')
			msg += '* Pan No. is required.\n';
		
		if(trim(mobileNo.value) == '')
			msg += '* Mobile No. is required.\n';
		
		if(trim(emailId.value) == '')
			msg += '* Email Id is required.\n';
		
		/*if(trim(sapCode.value) == '')
			msg += '* Sap Code is required.\n';*/
		
		if(trim(bankAccNo.value) == '')
			msg += '* Bank Account No is required.\n';
		
		if(trim(dateOfAppointment.value) == '')
			msg += '* Date Of Appointment is required.\n';


		if(msg.match("Code")){
			lawFirmCode.focus();
		}
		else if(msg.match("Description")){
			lawFirmDesc.focus();
		
		}else if(msg.match("Branch")){
			userButton1.focus();
			
		}else if(msg.match("Constitution")){
			constitutionOfFirm.focus();
			
		}else if(msg.match("Address1")){
			address1.focus();
			
		}else if(msg.match("Address2")){
			address2.focus();
			
		}else if(msg.match("Country")){
			countryButton.focus();
			
		}else if(msg.match("State")){
			stateButton.focus();
			
		}else if(msg.match("City")){
			cityButton.focus();
			
		}else if(msg.match("ZipCode")){
			zipCode.focus();
			
		}else if(msg.match("Phone1")){
			phone1.focus();
			
		}else if(msg.match("Pan")){
			panNo.focus();
			
		}
		
		else if(msg.match("Mobile")){
			mobileNo.focus();
			
		}
		else if(msg.match("Email")){
			emailId.focus();
			
		}
	/*	else if(msg.match("SapCode")){
			sapCode.focus();
			
		}*/else if(msg.match("BankAccNo")){
			bankAccNo.focus();
		}
		
	    else if(msg.match("dateOfAppointment")){
		 dateOfAppointment.focus();
		}
		 
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else if(validate("lawFirmMasterForm")){
		
			document.getElementById("lawFirmMasterForm").action="lawFirmMasterAdd.do?method=saveLawFirmDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("lawFirmMasterForm").submit();
			return true;
	}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}


function validateTime(a)
{
//	 alert("ok");
	var timeStr=document.getElementById(a).value;
	
	var timePat = /^(\d{1,2}):(\d{2})?$/;
	if(timeStr.length<5)
	{
		document.getElementById(a).value='0'+timeStr;
	}
	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById(a).value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById(a).value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById(a).value='';
	return false;
	}

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


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}



var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;



function validate(formName){
	var lawFirmCode = document.getElementById("lawFirmCode");
	var emailId = document.getElementById("emailId");
	var mobileNo = document.getElementById("mobileNo");
	var panNo = document.getElementById("panNo");
	var zipCode = document.getElementById("zipCode");
  

 var errors = [];
 
 if (!ck_numeric.test(lawFirmCode.value)) {
	 if(trim(lawFirmCode.value) != ''){
	 	errors[errors.length] = "* Law Firm Code is invalid.";
	 }
 }

 if (!san_email.test(emailId.value)) {
	 if(trim(emailId.value) != ''){
		 errors[errors.length] = "* Email Id is invalid.";
	 }
 } 
if(mobileNo.value!=''){
	var str=mobileNo.value;
	if(str.length!='10'){
		errors[errors.length] = "* Mobile No. should be 10 digits.";
	}
		 
	 }

if(panNo.value!=''){
	var str=panNo.value;
	if(str.length!='10'){
		errors[errors.length] = "* Pan No. should be 10 digits.";
	}
		 
	 }

if(zipCode.value!=''){
	var str=zipCode.value;
	if(str.length!='6'){
		errors[errors.length] = "* ZipCode should be 6 digits.";
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
	 	if(msg.match("Law")){
	 		lawFirmCode.focus();
		}else if(msg.match("Email") || msg.match("10") ){
			emailId.focus();
		}else if(msg.match("Mobile")){
			mobileNo.focus();
		}
	 	
		else if(msg.match("Pan") || msg.match("10") ){
			panNo.focus();
		}
	 	
		else if(msg.match("ZipCode") || msg.match("6") ){
			zipCode.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}

