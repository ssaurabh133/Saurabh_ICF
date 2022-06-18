	function corporateClear()
{
	document.getElementById("firstName").value='';
	document.getElementById("middleName").value='';
	document.getElementById("lastName").value='';
	document.getElementById("genderIndividual").value='';
	document.getElementById("corporateCategory").value='';
	document.getElementById("constitution").value='';
	document.getElementById("fatherHusband").value='';
	document.getElementById("incorporationDate").value='';
	document.getElementById("currentAge").value='';
	document.getElementById("casteCategory").value='';
	document.getElementById("maritalStatus").value='';
	document.getElementById("institutionEmail").value='';
	document.getElementById("pan").value='';
	document.getElementById("drivingLicense").value='';
	document.getElementById("voterId").value='';
	document.getElementById("passport").value='';
	document.getElementById("aadhaar").value='';
	document.getElementById("other").value='';
	document.getElementById("relationShip").value='';
	document.getElementById("eduDetailInd").value='';
	
}	


    function saveIndividualDetails()
    {//alert("Test122");
    	DisButClass.prototype.DisButMethod();
    	if(!calculateCustomerAge()){
    		DisButClass.prototype.EnbButMethod();
    		return false;
    	}
    	var contextPath = document.getElementById("contextPath").value;    	
    	var corporateCode = document.getElementById("corporateCode");  	
    	var firstName = document.getElementById("firstName");
    	var lastName = document.getElementById("lastName");
    	var constitution = document.getElementById("constitution");
    	var incorporationDate = document.getElementById("incorporationDate");
    	
    	var pan = document.getElementById("pan");   //1
		var industry = document.getElementById("industry");//2
		var eduDetailInd = document.getElementById("eduDetailInd");//3
		var subIndustry = document.getElementById("subIndustry");//4
		var casteCategory= document.getElementById("casteCategory");//5
		/*var businessSegment = document.getElementById("businessSegment");*///6
		var voterId = document.getElementById("voterId");//7
		var aadhaar = document.getElementById("aadhaar");//8
		var passport = document.getElementById("passport");//9
		var other = document.getElementById("other");//10
		var drivingLicense = document.getElementById("drivingLicense");//11
		var hiApplType = document.getElementById("hiApplType").value;//12
		
    	var fatherHusband = document.getElementById("fatherHusband"); 
    	var corporateCategory = document.getElementById("corporateCategory");
    	var genderIndividual = document.getElementById("genderIndividual");
    	var maritalStatus = document.getElementById("maritalStatus");
    	var institutionEmail = document.getElementById("institutionEmail").value;
    	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
    	var groupType  =document.getElementById('groupType').value ;
		var groupName  =document.getElementById('groupName').value ;
		var source=document.getElementById('source').value ;
		
		var fatherHusband=document.getElementById('fatherHusband');
		var motherFName=document.getElementById('motherFName');
		var motherLName=document.getElementById('motherLName');
		var residentialStatus=document.getElementById('residentialStatus');
		var occpation=document.getElementById('occpation');
		var customerNamePrifx = document.getElementById('customerNamePrifx');
		
		var proofOfIdentityDocument = document.getElementById('proofOfIdentityDocument');
		var proofOfIdentityDocumentNo = document.getElementById('proofOfIdentityDocumentNo');
		var proofOfIdentityDocumentExpDate = document.getElementById('proofOfIdentityDocumentExpDate');
		var proofOfAddressDocument = document.getElementById('proofOfAddressDocument');
		var proofOfAddressDocumentNo = document.getElementById('proofOfAddressDocumentNo');
		var proofOfAddressDocumentExpDate = document.getElementById('proofOfAddressDocumentExpDate');
		var riskCategory = document.getElementById('riskCategory');
		var minorityCommunity = document.getElementById('minorityCommunity');
		var handiCapped = document.getElementById('handiCapped');
		
		if(riskCategory){
			riskCategory = riskCategory.value;
			if(riskCategory==''){
				alert("Risk Category is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		}
		if(minorityCommunity){
			minorityCommunity = minorityCommunity.value;
			if(minorityCommunity==''){
				alert("Minority Community is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		}
		if(handiCapped){
			handiCapped = handiCapped.value;
			if(handiCapped==''){
				alert("Handicapped is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		}
		if(casteCategory){
			casteCategory = casteCategory.value;
			if(casteCategory==''){
				alert("Category is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		}
		if(pan.value == '' && hiApplType == 'PRAPPL')
		{
			alert("Pan no is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		if(proofOfIdentityDocument.value == ''){
			alert("Proof Of Identity Document(CKYC) is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		if(proofOfIdentityDocumentNo.value == ''){
			alert("Proof Of Identity Document No.(CKYC) Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		if(proofOfIdentityDocument.value == 'PAN'){
			var proofOfIdentityDocumentNo = $("#proofOfIdentityDocumentNo").val();
	    if(proofOfIdentityDocumentNo !='')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(proofOfIdentityDocumentNo) || proofOfIdentityDocumentNo.length != 10) {
				alert("Invalid Pan No. in Proof Of Identity Document No.(CKYC)");
				$("#proofOfIdentityDocumentNo").attr("value","");
				$("#proofOfIdentityDocumentNo").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	    }
		if(proofOfIdentityDocument.value=='PP' ||proofOfIdentityDocument.value=='DL')
		{
			if(proofOfIdentityDocumentExpDate.value==''){
			alert("Proof Of Identity Document Exp Date(CKYC) Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		}
		if(proofOfAddressDocument.value == ''){
			alert("Proof Of Address Document(CKYC) Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		if(proofOfAddressDocumentNo.value == ''){
			alert("Proof Of Address Document No.(CKYC) Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		if(proofOfAddressDocument.value=='PP' ||proofOfAddressDocument.value=='DL')
		{
			if(proofOfAddressDocumentExpDate.value==''){
			alert("Proof Of Address Document Exp Date(CKYC) Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
		}
		//Code added by saurabh
		if(proofOfIdentityDocument.value==proofOfAddressDocument.value)
		{
		alert("Proof Of Identity Document and Proof Of Address Document should not be same.");
		DisButClass.prototype.EnbButMethod();
    		return false;
		}
		//code end by saurabh
		if(customerNamePrifx.value == ''){
			alert("Customer Name Prifx  Is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
			if(fatherHusband.value == ''){
				alert("Father's/Husband's Name Is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
			if(motherFName.value == ''){
				alert("Mother First Name Is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
			if(motherLName.value == ''){
				alert("Mother Last Name Is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
			if(residentialStatus.value == ''){
				alert("Residential Status Is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
			if(occpation.value == ''){
				alert("Occpation Is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		
		
		if(hiApplType!='COAPPL')
		{
			if(firstName.value == ''||lastName.value == ''||constitution.value == ''||incorporationDate.value == '' || fatherHusband.value == '' || corporateCategory.value == '' || genderIndividual.value == '' || maritalStatus.value == '' || institutionEmail == '' || (pan.value=='' && hiApplType == 'PRAPPL') ||industry.value=='' ||eduDetailInd.value=='' ||subIndustry.value=='' ||casteCategory.value=='' /*|| businessSegment.value==''*/)
		{
    		var msg= '';
    		if(firstName.value == '')
    			msg += '* First Name is required.\n';
    		if(lastName.value == '')
    			msg += '* Last Name is required.\n';
    		if(genderIndividual.value == '')
    			msg += '* Gender is required.\n';
    		if(corporateCategory.value == '')
    			msg += '* Individual Category is required.\n';
    		if(constitution.value == '')
    			msg += '* Constitution is required.\n';
    		if(incorporationDate.value == '')
    			msg += '* Birth Date is required.\n';
    		if(fatherHusband.value == '')
    			msg += '* Father/Husband Name is required.\n';
    		if(maritalStatus.value == '')
    			msg += '* Marital Status is required.\n';
    		/*if(aadhaar.value == '')
    			msg += '* Aadhaar / UID No is required.\n';*/
    		if(emailMandatoryFlag == 'Y')
    		{
    			if(institutionEmail == '')
        			msg += '* Email is required.\n';
    		}
    		if(msg.match("First")){
    			firstName.focus();
    		}else if(msg.match("Last")){
    			lastName.focus();
    		}else if(msg.match("Individual")){
    			corporateCategory.focus();
    		}else if(msg.match("Constitution")){
    			constitution.focus();
    		}else if(msg.match("Father")){
    			fatherHusband.focus();
    		}else if(msg.match("Birth")){
    			incorporationDate.focus();
			}else if(msg.match("Gender")){
				genderIndividual.focus();
			}else if(msg.match("Marital")){
				maritalStatus.focus();
			}/*else if(msg.match("Aadhaar")){
				aadhaar.focus();
			}*/
    		if(msg != '')
    		{
	    		alert(msg);
	    		//document.getElementById("save").removeAttribute("disabled","true");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
    		}
    	}
    	/*if((voterId.value.trim()=='' || voterId.value.trim()=='null') && (aadhaar.value=='' || aadhaar.value=='null') && (passport.value=='' || passport.value=='null') && (other.value==''|| other.value=='null') && (drivingLicense.value=='' || drivingLicense.value=='null'))
		{
			alert("Driving License/Voter ID/Aadhaar/UID No/PassPort/Other Number is required.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		}
    	if(hiApplType=='COAPPL')
		{
			
			
			if(firstName.value == ''||lastName.value == ''||constitution.value == ''||incorporationDate.value == '' || fatherHusband.value == '' || corporateCategory.value == '' || genderIndividual.value == '' || maritalStatus.value == '' || institutionEmail == '' ||industry.value=='' ||eduDetailInd.value=='' ||subIndustry.value=='' ||casteCategory.value=='' )
		{
    		var msg= '';
    		if(firstName.value == '')
    			msg += '* First Name is required.\n';
    		if(lastName.value == '')
    			msg += '* Last Name is required.\n';
    		if(genderIndividual.value == '')
    			msg += '* Gender is required.\n';
    		if(corporateCategory.value == '')
    			msg += '* Individual Category is required.\n';
    		if(constitution.value == '')
    			msg += '* Constitution is required.\n';
    		if(incorporationDate.value == '')
    			msg += '* Birth Date is required.\n';
    		if(fatherHusband.value == '')
    			msg += '* Father/Husband Name is required.\n';
    		if(maritalStatus.value == '')
    			msg += '* Marital Status is required.\n';
    		/*if(aadhaar.value == '')
    			msg += '* Aadhaar / UID No is required.\n';*/
    		if(emailMandatoryFlag == 'Y')
    		{
    			if(institutionEmail == '')
        			msg += '* Email is required.\n';
    		}
			if(pan.value == '' && hiApplType == 'PRAPPL')
    			msg += '* Pan Number is required.\n'; 
		
		
			/*if(businessSegment.value == '')
    			msg += '* Business Segment is required.\n';*/
			/* if(voterId.value == '' ||aadhaar.value == '' ||passport.value == ''||other.value == '')
    			msg += '* Voter ID/Aadhaar/UID No/PassPort/Other Number is required.\n'; */
			/* if(aadhaar.value == '')
    			msg += '* Aadhaar/UID No is required.\n';
			if(passport.value == '')
    			msg += '* PassPort Number is required.\n';
			if(other.value == '')
    			msg += '* Other No required.\n';
			 */
			
			
    		if(msg.match("First")){
    			firstName.focus();
    		}else if(msg.match("Last")){
    			lastName.focus();
    		}else if(msg.match("Individual")){
    			corporateCategory.focus();
    		}else if(msg.match("Constitution")){
    			constitution.focus();
    		}else if(msg.match("Father")){
    			fatherHusband.focus();
    		}else if(msg.match("Birth")){
    			incorporationDate.focus();
			}else if(msg.match("Gender")){
				genderIndividual.focus();
			}else if(msg.match("Marital")){
				maritalStatus.focus();
			}
			/*else if(msg.match("Aadhaar")){
				aadhaar.focus();
			}*/
			 else if(msg.match("Pan")){
				pan.focus();
			} else if(msg.match("Industry")){
				industry.focus();
			}else if(msg.match("Educational")){
				eduDetailInd.focus();
			}else if(msg.match("Sub-Industry")){
				subIndustry.focus();
			}else if(msg.match("Category")){
				casteCategory.focus();
			}/*else if(msg.match("Business Segment")){
				businessSegment.focus();
			} else if(msg.match("Voter ID Number")){
				voterId.focus();
			}else if(msg.match("Aadhaar/UID No")){
				aadhaar.focus();
			}else if(msg.match("PassPort Number")){
				passport.focus();
			}else if(msg.match("Other No")){
				other.focus();
			} */
			
			
    		if(msg != '')
    		{
	    		alert(msg);
	    		//document.getElementById("save").removeAttribute("disabled","true");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
    		}
    	}
    	/*if((voterId.value.trim()=='' || voterId.value.trim()=='null') && (aadhaar.value=='' || aadhaar.value=='null') && (passport.value=='' || passport.value=='null') && (other.value==''|| other.value=='null') && (drivingLicense.value=='' || drivingLicense.value=='null'))
		{
			alert("Driving License/Voter ID/Aadhaar/UID No/PassPort/Other Number is required.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		}
		
    	
    	if(validate("individualDetailForm")) {
      		var bypassDedupe = document.getElementById("bypassDedupe").value;
      		
      		var otherRelationShip=document.getElementById("otherRelationShip").value;
			var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
			
			if(otherRelationShip=='SU' && businessPartnerTypeDesc=='')
			{
				alert("Please enter supplier");
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("bpButton").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			if(otherRelationShip=='MF' && businessPartnerTypeDesc=='')
			{
				alert("Please enter manufacturer");
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("bpButton").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			/*if(groupType=='E' && groupName=='' && source!='ED')
			{
				alert("Please select Group Name");
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("bpButton").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/
      		
      		document.getElementById("individualDetailForm").action="idividualDetailActionPage.do?bypassDedupe="+bypassDedupe+"aadhaar="+aadhaar+"constitution="+constitution;
      		document.getElementById("processingImage").style.display = '';
 	  		document.getElementById("individualDetailForm").submit();
 	  		return true;
   		}else{
  			//document.getElementById("save").removeAttribute("disabled","true");
    		DisButClass.prototype.EnbButMethod();
   			return false;
   		}
   }
  
  
    
    // ------------------------ Validation -----------------------
    
       var ck_numeric = /^[A-Za-z0-9]{10,11}$/;
	   var ck_alphaNumeric = /^[A-Za-z0-9,\.-\/ _]{1,50}$/;
	   var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	   var san_url = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;
	   //var ck_alphaNumeric = /^[A-Za-z0-9,\.''_]{1,50}$/;


function validate(formName){

       // var pan = document.getElementById("pan").value;
	    var institutionEmail = document.getElementById("institutionEmail").value;
	     
	     
	    var errors = [];

	    /*if (!ck_numeric.test(pan)) {
	    	if(pan != ''){
		   	  errors[errors.length] = "* PAN No. should be 10 digits.";
	    	}
		 } */
	    var pan = $("#pan").val();
	    if(pan !='')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#pan").attr("value","");
				$("#pan").focus();
				return false;
			}
	    }
		var pass = $("#passport").val();
		/*if(pass != '')
		{
			var regex2 = /^[A-Z]{1}\d{7}$/;
			if (!regex2.test(pass) || pass.length != 8) {
				alert("Invalid Passport No.");
				$("#passport").attr("value","");
				$("#passport").focus();
				return false;
			}
		}*/
		 if(institutionEmail != "") { 
		    if (!san_email.test(institutionEmail)) {
			   	  errors[errors.length] = "* Institution Email is not valid.";
			 }
	   	}
	    
	    if (errors.length > 0) {
		     reportErrors(errors);
		     return false;
	    }else {
	    	return true;
	    }
	  }

	   function reportErrors(errors){
	    var msg = "";
	    for (var i = 0; i<errors.length; i++) {
	     var numError = i + 1;
	     msg += "\n" + errors[i];
	    }
	    if(msg.match("No.")){
	   	 document.getElementById('pan').focus();
	   	}else if(msg.match("Institution")){
	   		document.getElementById('institutionEmail').focus();
	   	}
	    alert(msg);
	    document.getElementById("save").removeAttribute("disabled","true");
	   }
	   
	   
	   function activeSupplier(val)
	   {
	   
	   	if(val=='CS')
	   	{
	   		document.getElementById("supplierDiv").style.display='none';
	   		document.getElementById("supplierLableDiv").style.display='none';
	   		document.getElementById("manufactLableDiv").style.display='none';
	   		document.getElementById("lbxBusinessPartnearHID").value='';
	   		document.getElementById("businessPartnerName").value='';
	   		document.getElementById("businessPartnerType").value='';
	   		document.getElementById("businessPartnerTypeDesc").value='';
	   		
	   		document.getElementById("lbxBusinessPartnearHID").setAttribute("disable","true");
	   		document.getElementById("businessPartnerName").setAttribute("disable","true");
	   		document.getElementById("businessPartnerType").setAttribute("disable","true");
	   		document.getElementById("businessPartnerTypeDesc").setAttribute("disable","true");
	   	}
	   	else if(val=='SU')
	   	{
	   		//alert("val su : "+val);
	   		document.getElementById("supplierDiv").style.display='';
	   		document.getElementById("supplierLableDiv").style.display='';
	   		document.getElementById("manufactLableDiv").style.display='none';
	   		
	   		document.getElementById("lbxBusinessPartnearHID").value='';
	   		document.getElementById("businessPartnerName").value='';
	   		document.getElementById("businessPartnerType").value='';
	   		document.getElementById("businessPartnerTypeDesc").value='';
	   		
	   		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerName").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerType").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
	   	}
	   	else
	   		
	   	{
	   		//alert("val mf : "+val);
	   		document.getElementById("supplierDiv").style.display='';
	   		document.getElementById("manufactLableDiv").style.display='';
	   		document.getElementById("supplierLableDiv").style.display='none';
	   		
	   		document.getElementById("lbxBusinessPartnearHID").value='';
	   		document.getElementById("businessPartnerName").value='';
	   		document.getElementById("businessPartnerType").value='';
	   		document.getElementById("businessPartnerTypeDesc").value='';
	   		
	   		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerName").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerType").removeAttribute("disable","true");
	   		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
	   	}
	  }

	   function groupSelectInMasterNew(){
		   
		  
			var groupType=document.getElementById("groupType").value;
			if(groupType=='E'){
				document.getElementById("groupLov").style.display="";
				document.getElementById("groupText").style.display="none";	
				document.getElementById("groupNameText").value='';
				document.getElementById("hGroupId").value='';
				
				//document.getElementById("groupNameText").setAttribute("disabled", true);
				document.getElementById("groupName").removeAttribute("disabled");
				document.getElementById("hGroupId").removeAttribute("disabled");
			

			}else if(groupType=='N'){
			
				
				document.getElementById("groupText").style.display="";
				document.getElementById("groupLov").style.display="none";
				
				document.getElementById("hGroupId").value='';
				document.getElementById("groupName").value='';
				//document.getElementById("groupNameText").removeAttribute("disabled", true);
				document.getElementById("groupName").setAttribute("disabled", true);
				document.getElementById("hGroupId").setAttribute("disabled", true);

				
				 return true;
			}else{
				document.getElementById("groupText").style.display="";
				document.getElementById("groupLov").style.display="none";
				
				
				
			}
		}
		
		function hideShowManFieldsForIndividual()
{
	var hiApplType = document.getElementById("hiApplType").value;
	if(hiApplType=='COAPPL')
		{
		 document.getElementById('hid').style.display="none";
		}
	else
		{
		document.getElementById('hid').style.display="";
		}
}
		
function enableCkycDoc(event){
	if(event=='Identity'){
		var proofOfIdentityDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfIdentityDocument!='')
			document.getElementById("proofOfIdentityDocumentNo").removeAttribute("disabled","true");
	}
	if(event=='Address'){
		var proofOfAddressDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfAddressDocument!='')
			document.getElementById("proofOfAddressDocumentNo").removeAttribute("disabled","true");
	}
	
}	
function panUIDFill(event){
	if(event=='Identity'){
		var proofOfIdentityDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfIdentityDocument!='')
			document.getElementById("proofOfIdentityDocumentNo").removeAttribute("disabled","true");
	}
	if(event=='Address'){
		var proofOfAddressDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfAddressDocument!='')
			document.getElementById("proofOfAddressDocumentNo").removeAttribute("disabled","true");
	}
	
}	
function panUIDFill(event){
	if(event=='Identity'){
		var proofOfIdentityDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfIdentityDocument=='PAN' || proofOfIdentityDocument=='UID'){
			if(proofOfIdentityDocument=='PAN'){
			document.getElementById("proofOfIdentityDocumentNo").value=document.getElementById("pan").value;
                       //document.getElementById("proofOfIdentityDocumentNo").readOnly = true;
                       document.getElementById("proofOfIdentityDocumentNo").setAttribute("readonly",true);
			
		}
		if(proofOfIdentityDocument=='UID'){
			document.getElementById("proofOfIdentityDocumentNo").value=document.getElementById("aadhaar").value;
					   document.getElementById("proofOfIdentityDocumentNo").setAttribute("readonly",true);
                      // document.getElementById("proofOfIdentityDocumentNo").readOnly = true;
			
		}
	}		
		else
		{
		document.getElementById("proofOfIdentityDocumentNo").value='';
		document.getElementById("proofOfIdentityDocumentNo").removeAttribute("readOnly");
		//document.getElementById("proofOfIdentityDocumentNo").readOnly = false;
		}
	}
	if(event=='Address'){
		var proofOfAddressDocument = document.getElementById("proofOfAddressDocument").value;
		
		if(proofOfAddressDocument=='UID'){
			document.getElementById("proofOfAddressDocumentNo").value=document.getElementById("aadhaar").value;
			document.getElementById("proofOfAddressDocumentNo").setAttribute("readonly",true);
			//document.getElementById("proofOfAddressDocumentNo").readOnly = true;			
		}	
		else
		{
		document.getElementById("proofOfAddressDocumentNo").value='';
		document.getElementById("proofOfAddressDocumentNo").removeAttribute("readOnly");
		//document.getElementById("proofOfAddressDocumentNo").readOnly = false;
		}
		
	}
}


/*	if(event=='Identity'){
		var proofOfIdentityDocument = document.getElementById("proofOfIdentityDocument").value;
		if(proofOfIdentityDocument=='PAN'){
			document.getElementById("pan").value = document.getElementById("proofOfIdentityDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("voterId").value = "";
			document.getElementById("passport").value = "";
		}
		if(proofOfIdentityDocument=='UID'){
			document.getElementById("aadhaar").value = document.getElementById("proofOfIdentityDocumentNo").value;
			document.getElementById("uIDAI").value = document.getElementById("proofOfIdentityDocumentNo").value;
			document.getElementById("voterId").value = "";
			document.getElementById("passport").value = "";
			document.getElementById("pan").value = "";
		}
		if(proofOfIdentityDocument=='VIC'){
			document.getElementById("voterId").value = document.getElementById("proofOfIdentityDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("passport").value = "";
		}
		if(proofOfIdentityDocument=='PP'){
			document.getElementById("passport").value = document.getElementById("proofOfIdentityDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("voterId").value = "";
			document.getElementById("pan").value = "";
		}
	}
	if(event=='Address'){
		var proofOfAddressDocument = document.getElementById("proofOfAddressDocument").value;
		if(proofOfAddressDocument=='PAN'){
			document.getElementById("pan").value = document.getElementById("proofOfAddressDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("voterId").value = "";
			document.getElementById("passport").value = "";
		}
		if(proofOfAddressDocument=='UID'){
			document.getElementById("aadhaar").value = document.getElementById("proofOfAddressDocumentNo").value;
			document.getElementById("uIDAI").value = document.getElementById("proofOfAddressDocumentNo").value;
			document.getElementById("uIDAI").value = "";
			document.getElementById("voterId").value = "";
			document.getElementById("passport").value = "";
		}
		if(proofOfAddressDocument=='VIC'){
			document.getElementById("voterId").value = document.getElementById("proofOfAddressDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("passport").value = "";
			document.getElementById("pan").value = "";
		}
		if(proofOfAddressDocument=='PP'){
			document.getElementById("passport").value = document.getElementById("proofOfAddressDocumentNo").value;
			document.getElementById("aadhaar").value = "";
			document.getElementById("uIDAI").value = "";
			document.getElementById("voterId").value = "";
			document.getElementById("pan").value = "";
		}
		
	}*/
	//Code added by saurabh
	function proofAddressValid()
{
var proofOfIdentityDocument = document.getElementById('proofOfIdentityDocument');
var proofOfAddressDocument = document.getElementById('proofOfAddressDocument');
		if(proofOfIdentityDocument.value==proofOfAddressDocument.value)
		{
		alert("Proof Of Identity Document and Proof Of Address Document should not be same.");
		DisButClass.prototype.EnbButMethod();
    		return false;
		}		
}
//code end by saurabh
function proofOfRelatedPerson(){
	var relatedPersonPrfIdntyDoc = document.getElementById('relatedPersonPrfIdntyDoc');
	if(relatedPersonPrfIdntyDoc=='UID'){
				   document.getElementById("relatedPersonPrfIdntyDocNo").setAttribute("readonly",true);
	}else{
		 document.getElementById("relatedPersonPrfIdntyDocNo").removeAttribute("readonly",true);
	}
}
function calculateCustomerAge(){

	var dobDate=document.getElementById("incorporationDate").value;
	var flag=checkDate('incorporationDate');
	if(flag)
	{
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(curDate,formatD.substring(2,3));
		var dt3=getDateObject(dobDate,formatD.substring(2,3));
		if(dt1<=dt3)
		{
			alert("Date of Birth should be less than Business date.");
			document.getElementById("incorporationDate").value='';
			document.getElementById("currentAge").value='';
			return false;
		}
		var curDay=parseFloat(curDate.substring(0,2));
		var dobDay=parseFloat(dobDate.substring(0,2));
		var curMonth=parseFloat(curDate.substring(3,5));
		var dobMonth=parseFloat(dobDate.substring(3,5));
		var curYear=parseFloat(curDate.substring(6));
		var dobYear=parseFloat(dobDate.substring(6));
		var resDay="";
		var resMonth="";
		var resYear="";
		if(curDay<dobDay)
		{
			curMonth=curMonth-1;
			curDay=curDay+30;			
		}
		resDay = curDay-dobDay;
		if(curMonth<dobMonth)
		{
			curYear=curYear-1;
			curMonth=curMonth+12;			
		}
		resMonth=curMonth-dobMonth;
		resYear=curYear-dobYear;
		
		var result="";
		if(resYear != 0)
			result=resYear+" Years ";
		if(resMonth  != 0)
			result=result+""+resMonth+" Months ";
		if(resDay != 0)
			result=result+""+resDay+" Days ";
		if(result=="")
			result="0 Days";
		//alert("result   : "+result);
		if(resYear<18){
			alert("Age Cannot be less than 18 years");
			document.getElementById("incorporationDate").value='';
			document.getElementById("currentAge").value='';
			return false;
		}
		if(resYear==85){
				if(resDay>0){
					alert("Age Cannot be greater than 85 years");	
					document.getElementById("incorporationDate").value='';
					document.getElementById("currentAge").value='';
					return false;
				}
		}
		if(resYear==85){
			if(resMonth>0){
					alert("Age Cannot be greater than 85 years");	
					document.getElementById("incorporationDate").value='';
					document.getElementById("currentAge").value='';
					return false;
			}
		}
		if(resYear>85){
			alert("Age Cannot be greater than 85 years");
			document.getElementById("incorporationDate").value='';
			document.getElementById("currentAge").value='';
			return false;
		}
		document.getElementById("currentAge").value=result;
		return true;
	}

}