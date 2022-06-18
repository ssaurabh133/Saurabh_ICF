function newAddAdvocate(){
	
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("advocateMasterForm").action=sourcepath+"/advocateMaster.do?method=openAddAdvocate";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("advocateMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();

	return false;

}


function saveAdvocateMaster(){
	
	DisButClass.prototype.DisButMethod();
	
	var advocateCode = document.getElementById("advocateCode");
	var lbxUserId = document.getElementById("lbxUserId");
	var lawFirmFlag = document.getElementById("advocateTypeFlag");
	var individual = document.getElementById("advocateTypeFlag1");
	var advocateTypeFlag = document.getElementById("advocateTypeFlag");
	var lawFirmCode = document.getElementById("lawFirmCode");
	var branch = document.getElementById("lbxBranchIds");
	var caseTypeCode = document.getElementById("lbxCaseTypeCode");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");
	var country = document.getElementById("txtCountryCode");
	var state = document.getElementById("txtStateCode");
	var dist = document.getElementById("txtDistCode");
	var zipCode = document.getElementById("zipCode");
	var phone1 = document.getElementById("phone1");
	var phone2 = document.getElementById("phone2");
	var panNo = document.getElementById("panNo");
	var sapCode = document.getElementById("sapCode");
	var bankAccNo = document.getElementById("bankAccNo");
	var mobileNo = document.getElementById("mobileNo");
	var emailId = document.getElementById("emailId");
	var membershipLicNo = document.getElementById("membershipLicNo");
	var retainershipValidUpto = document.getElementById("retainershipValidUpto");
	var dateOfAppointment = document.getElementById("dateOfAppointment");
	var sourcepath=document.getElementById("contextPath").value;
	

	 if(trim(lbxUserId.value) == ''||trim(advocateCode.value) == ''
		 ||trim(lbxBranchIds.value) == ''||trim(lbxCaseTypeCode.value) == ''||trim(address1.value) == ''||(lawFirmFlag.checked == false && individual.checked == false)
		||(advocateTypeFlag.checked == true && trim(lawFirmCode.value)=='')
			 ||trim(address2.value) == ''||trim(txtCountryCode.value) == ''||trim(txtStateCode.value) == ''
				 ||trim(txtDistCode.value) == ''||trim(zipCode.value) == '' ||trim(panNo.value) == ''||trim(sapCode.value) == ''
						 ||trim(bankAccNo.value) == ''||trim(mobileNo.value) == ''||trim(emailId.value) == ''
							){
		 var msg= '';
 		
 		if(trim(lbxUserId.value) == '')
 			msg += '* Advocate Code is required.\n';
 		if(trim(advocateCode.value) == '')
 			msg += '* Advocate Description is required.\n';
 		if(lawFirmFlag.checked == false && individual.checked == false)
 			msg += '* Either select LawFirm or Individual.\n';
 		if(advocateTypeFlag.checked == true && trim(lawFirmCode.value)=='')
 			msg += '* Law Firm Code is required.\n';
 		if(trim(lbxBranchIds.value) == '')
 			msg += '* Branch is required.\n';
 		if(trim(lbxCaseTypeCode.value) == '')
 			msg += '* Case Type Code is required.\n';
 		if(trim(address1.value) == '')
 			msg += '* Address1 is required.\n';
 		if(trim(address2.value) == '')
 			msg += '* Address2 is required.\n';
 		if(trim(txtCountryCode.value) == '')
 			msg += '* Country is required.\n';
 		if(trim(txtStateCode.value) == '')
 			msg += '* State is required.\n';
 		if(trim(txtDistCode.value) == '')
 			msg += '* City is required.\n';
 		if(trim(zipCode.value) == '')
 			msg += '* Zip Code is required.\n';
 		if(trim(panNo.value) == '')
 			msg += '* Pan No. is required.\n';
 		if(trim(sapCode.value) == '')
 			msg += '* Sap Code is required.\n';
 		if(trim(bankAccNo.value) == '')
 			msg += '* Bank AccNo is required.\n';
 		if(trim(mobileNo.value) == '')
 			msg += '* Mobile No. is required.\n';
 		if(trim(emailId.value) == '')
 			msg += '* Email Id is required.\n';
 		/*if(trim(membershipLicNo.value) == '')
 			msg += '* Membership No is required.\n';
 		if(trim(retainershipValidUpto.value) == '')
 			msg += '* Retainership Valid Upto is required.\n';
 		if(trim(dateOfAppointment.value) == '')
 			msg += '* Date Of Appointment is required.\n';*/
 		 		 		
 		if(msg.match("Advocate Code")){
 			advocateCode.focus();
 		}else if(msg.match("Advocate Description")){
 			advocateDesc.focus();
 		}
 		else if(msg.match("Either")){
 			lawFirmFlag.focus();
 		}
 		else if(msg.match("Law Firm Code")){
 			lawFirmCode.focus();
		}
 		else if(msg.match("Branch")){
 			branch.focus();
 		}
 		else if(msg.match("Case Type Code")){
 			caseTypeCode.focus();
 		}
 		else if(msg.match("Address1")){
 			address1.focus();
 		}
 		else if(msg.match("Address2")){
 			address2.focus();
 		}
 		else if(msg.match("Country")){
 			country.focus();
 		}
 		else if(msg.match("State")){
 			state.focus();
 		}
 		else if(msg.match("City")){
 			dist.focus();
 		}
 		else if(msg.match("Zip Code")){
 			zipCode.focus();
 		}
 		else if(msg.match("Pan No.")){
 			panNo.focus();
 		}
 		else if(msg.match("Sap Code")){
 			sapCode.focus();
 		}
 		else if(msg.match("Bank")){
 			bankAccNo.focus();
 		}
 		else if(msg.match("Mobile No.")){
 			mobileNo.focus();
 		}
 		else if(msg.match("Email Id")){
 			emailId.focus();
 		}
 		/*else if(msg.match("Membership")){
 			membershipLicNo.focus();
 		}
 		else if(msg.match("Retainership")){
 			retainershipValidUpto.focus();
 		}
 		else if(msg.match("Date Of Appointment")){
 			dateOfAppointment.focus();
 		}*/
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
	 else if(validate("advocateMasterForm")){
		 
			document.getElementById("advocateMasterForm").action=sourcepath+"/advocateMasterAdd.do?method=saveAdvocateDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("advocateMasterForm").submit();
			return true;
	}
			 else
			 {
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled","true");
				return false;
			 }

		}

function fnUpdateAdvocate(){
	
	DisButClass.prototype.DisButMethod();
	var advocateCode = document.getElementById("advocateCode");
	var lbxUserId = document.getElementById("lbxUserId");
//	var lawFirmCode = document.getElementById("lawFirmCode");
	var branch = document.getElementById("lbxBranchIds");
	var caseTypeCode = document.getElementById("lbxCaseTypeCode");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");
	var country = document.getElementById("txtCountryCode");
	var state = document.getElementById("txtStateCode");
	var dist = document.getElementById("txtDistCode");
	var zipCode = document.getElementById("zipCode");
	
	var panNo = document.getElementById("panNo");
	var sapCode = document.getElementById("sapCode");
	var bankAccNo = document.getElementById("bankAccNo");
	var mobileNo = document.getElementById("mobileNo");
	var emailId = document.getElementById("emailId");
	var membershipLicNo = document.getElementById("membershipLicNo");
	var retainershipValidUpto = document.getElementById("retainershipValidUpto");
	var dateOfAppointment = document.getElementById("dateOfAppointment");
	var sourcepath=document.getElementById("contextPath").value;
	

	 if(trim(lbxUserId.value) == ''||trim(advocateCode.value) == ''
		 ||trim(lbxBranchIds.value) == ''||trim(lbxCaseTypeCode.value) == ''||trim(address1.value) == ''
			 ||trim(address2.value) == ''||trim(txtCountryCode.value) == ''||trim(txtStateCode.value) == ''
				 ||trim(txtDistCode.value) == ''||trim(zipCode.value) == ''||trim(panNo.value) == ''||trim(sapCode.value) == ''
						 ||trim(bankAccNo.value) == ''||trim(mobileNo.value) == ''||trim(emailId.value) == ''
							 ){
		 var msg= '';
 		
 		if(trim(lbxUserId.value) == '')
 			msg += '* Advocate Code is required.\n';
 		if(trim(advocateCode.value) == '')
 			msg += '* Advocate Description is required.\n';
 		if(trim(lbxBranchIds.value) == '')
 			msg += '* Branch is required.\n';
 		if(trim(lbxCaseTypeCode.value) == '')
 			msg += '* Case Type Code is required.\n';
 		if(trim(address1.value) == '')
 			msg += '* Address1 is required.\n';
 		if(trim(address2.value) == '')
 			msg += '* Address2 is required.\n';
 		if(trim(txtCountryCode.value) == '')
 			msg += '* Country is required.\n';
 		if(trim(txtStateCode.value) == '')
 			msg += '* State is required.\n';
 		if(trim(txtDistCode.value) == '')
 			msg += '* City is required.\n';
 		if(trim(zipCode.value) == '')
 			msg += '* Zip Code is required.\n';
 		
 		if(trim(panNo.value) == '')
 			msg += '* Pan No. is required.\n';
 		if(trim(sapCode.value) == '')
 			msg += '* Sap Code is required.\n';
 		if(trim(bankAccNo.value) == '')
 			msg += '* Bank AccNo. is required.\n';
 		if(trim(mobileNo.value) == '')
 			msg += '* Mobile No. is required.\n';
 		if(trim(emailId.value) == '')
 			msg += '* Email Id is required.\n';
 		if(trim(membershipLicNo.value) == '')
 			msg += '* Membership No. is required.\n';
 		if(trim(retainershipValidUpto.value) == '')
 			msg += '* Retainership Valid Upto is required.\n';
 		if(trim(dateOfAppointment.value) == '')
 			msg += '* Date Of Appointment is required.\n';
 		 		 		
 		if(msg.match("Advocate Code")){
 			advocateCode.focus();
 		}else if(msg.match("Advocate Description")){
 			advocateDesc.focus();
 		}
 		else if(msg.match("Branch")){
 			branch.focus();
 		}
 		else if(msg.match("Case Type Code")){
 			caseTypeCode.focus();
 		}
 		else if(msg.match("Address1")){
 			address1.focus();
 		}
 		else if(msg.match("Address2")){
 			address2.focus();
 		}
 		else if(msg.match("Country")){
 			country.focus();
 		}
 		else if(msg.match("State")){
 			state.focus();
 		}
 		else if(msg.match("City")){
 			dist.focus();
 		}
 		else if(msg.match("Zip")){
 			zipCode.focus();
 		}
 		
 		else if(msg.match("Pan")){
 			panNo.focus();
 		}
 		else if(msg.match("Sap")){
 			sapCode.focus();
 		}
 		else if(msg.match("Bank")){
 			bankAccNo.focus();
 		}
 		else if(msg.match("Mobile")){
 			mobileNo.focus();
 		}
 		else if(msg.match("Email")){
 			emailId.focus();
 		}
 		/*else if(msg.match("Membership")){
 			membershipLicNo.focus();
 		}
 		else if(msg.match("Retainership")){
 			retainershipValidUpto.focus();
 		}
 		else if(msg.match("Date Of Appointment")){
 			dateOfAppointment.focus();
 		}*/
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
			 else if(validate("advocateMasterForm")){
		 
				 document.getElementById("advocateMasterForm").action=sourcepath+"/advocateMasterAdd.do?method=updateAdvocate";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("advocateMasterForm").submit();

			return true;
	}
			 else
			 {
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled","true");
				return false;
			 }

		}

function fnSearchAdvocate(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("advocateMasterForm").action="advocateMasterBehind.do";
	if(document.getElementById("advocateCode").value=='' && document.getElementById("advocateDesc").value=='')
	{
		alert(val);
		document.getElementById("advocateCode").focus();
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("advocateMasterForm").submit();
	return true;
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


function removeAdvocate()
{
	
	document.getElementById("lbxlawFirmCode").value="";
	document.getElementById("lawFirmCode").value="";
	
}



function hideLovButton(value){
	
	  
	  if(value=='L')
	  {
		 
		  document.getElementById("lawFirmCodeButton").style.display ='';
	  }
	  else
	  {
		  
		  document.getElementById("lawFirmCodeButton").style.display ='none';
		  document.getElementById("lawFirmCode").value = ""
			  document.getElementById("lbxlawFirmCode").value = ""
	  }
		  
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;


function validate(formName){
	
	var emailId = document.getElementById("emailId");
	var mobileNo = document.getElementById("mobileNo");
	var panNo = document.getElementById("panNo");
	var zipCode = document.getElementById("zipCode");
	if((panNo.value).length != 10)
	{
		alert("* Pan No. should be 10 digits.");
		return false;
	}

 var errors = [];
 

 if (!san_email.test(emailId.value)) {
	 if(trim(emailId.value) != ''){
		 errors[errors.length] = "* Email Id is invalid.";
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
	if(str.length!='10'){
		errors[errors.length] = "* Zip Code should be 6 digits.";
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
	 	if(msg.match("Mobile No.") || msg.match("10") ){
	 		mobileNo.focus();
	 		
		}else if(msg.match("Email")){
			emailId.focus();
		}
	 	
		else if(msg.match("Pan No.") || msg.match("10") ){
			panNo.focus();
		}
	 	
		else if(msg.match("Zip Code") || msg.match("6") ){
			zipCode.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}


function compareTwoDates(format,date1,date2,message)
{
	
	 // alert("date1 : "+date1);
	 // alert("date2 : "+date2);
	
	var formatD=document.getElementById(format).value;
	
	var firstDate =document.getElementById(date1).value;
	var secondDate=document.getElementById(date2).value;
    
    var dt1=getDateObject(firstDate,formatD.substring(2, 3));
    var dt2=getDateObject(secondDate,formatD.substring(2, 3));
    
    //alert("dt1 : "+dt1);
    //alert("dt2 : "+dt2);

    
    if(dt1>dt2)
	{
		alert(message);
		document.getElementById(date2).value='';
		return false;
	}
    else
	{
		return true;
	}
	
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);	
	return dtObject;
}


