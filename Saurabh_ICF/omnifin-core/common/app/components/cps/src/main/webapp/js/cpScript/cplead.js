function leadchange(val) {
	if(document.getElementById("leadId").value == ""){
		if (val == 'RM') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';

		} else if (val == 'VENDOR') {
			
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			
		} else if (val == 'OTHERS') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = '';
			
		} else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			document.getElementById("others").style.display = 'none';
		}else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = '';
		}

	}else
		if (val == 'RM' || val == 'RO') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';

		} else if (val == 'VENDOR') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
		}else if (val == 'OTHERS') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = '';

		} else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
		}	
		else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = '';
		}
}

function leadempty(val) {
//	if(document.getElementById("leadId").value == ""){

		document.getElementById("customerIdButton").style.display='none';
		if (val == 'RM' || val == 'RO') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			document.getElementById("rmCode1").value = "";
			document.getElementById("rmName1").value = "";
			document.getElementById("contactnorm").value = "";
			document.getElementById("leadgenzonerm").value = "";
			document.getElementById("leadgencityrm").value = "";
			//document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");

		} else if (val == 'VENDOR') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			
			document.getElementById("vendorCode1").value = "";
			document.getElementById("vendorName1").value = "";
			document.getElementById("contactnovendor1").value = "";
//			document.getElementById("leadgenzonevendor").value = "";
			document.getElementById("leadgencityvendor").value = "";
			document.getElementById("vendorHead1").value = "";
			//document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");
			
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			document.getElementById("branchHead2").value = "";
			document.getElementById("branchName1").value = "";
			document.getElementById("contactnobranch").value = "";
			document.getElementById("branchHead1").value ="";
			document.getElementById("leadgencitybranch").value = "";
			//document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");
			//document.getElementById("branchHead1ForTele").style.display = '';
			//document.getElementById("branchHead1ForOth").style.display = 'none';
			
		}
		else if (val == 'OTHERS') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = '';
			//document.getElementById("branchCode2").value = "";
			document.getElementById("branchName1").value = "";
			document.getElementById("contactnobranch").value = "";
			document.getElementById("leadgencitybranch").value = "";
			document.getElementById("relationship").value = "New";
			//document.getElementById("branchHead1ForTele").style.display = 'none';
			//document.getElementById("branchHead1ForOth").style.display = '';
			document.getElementById("relationship").removeAttribute("disabled");
			
		}
		else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = 'none';
			document.getElementById("relationship").value = "";
			document.getElementById("relationship").removeAttribute("disabled");
		}else if (val == 'EXISTING'){
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = '';
			//document.getElementById("description").value = "";
			document.getElementById("relationship").value = "Existing";
			document.getElementById("relationship").setAttribute("disabled","true");
			document.getElementById("relationshipSince").removeAttribute("disabled");
			document.getElementById("customerIdButton").style.display='';
			
		}else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("others").style.display = 'none';
			//document.getElementById("genericDet").style.display = '';
			//document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");
			
		}
//	}

}

function diffLead(attr) {
	if (attr == 3000106) {
		document.getElementById("New").style.display = '';
//		document.getElementById("leadButton").style.display = 'none';
//		document.getElementById("dealButton").style.display = '';
	} else if (attr == 3000111) {
		document.getElementById("New").style.display = 'none';
//		document.getElementById("dealButton").style.display = 'none';
//		document.getElementById("leadButton").style.display = '';
	}
}

function hideorshow(san) {

	if (san == 'RM' || san == 'RO' ) {
		document.getElementById("rm").style.display = '';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("others").style.display = 'none';
		//document.getElementById("genericDet").style.display = 'none';

	} else if (san == 'VENDOR') {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = '';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("others").style.display = 'none';
		//document.getElementById("genericDet").style.display = 'none';

	} else if (san == 'BRANCH') {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = '';
		document.getElementById("others").style.display = 'none';
		//document.getElementById("genericDet").style.display = 'none';

	} 
	else if (san == 'OTHERS') {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("others").style.display = '';
		//document.getElementById("branchHead1ForTele").style.display = 'none';
		//document.getElementById("branchHead1ForOth").style.display = '';
		//document.getElementById("genericDet").style.display = 'none';

	}
	else if (san == 'Hide' || san == "") {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = 'none';
		//document.getElementById("genericDet").style.display = 'none';
	}else {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("others").style.display = 'none';
		//document.getElementById("genericDet").style.display = '';
	}

}

function caseChange(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

function numericOnly(e, Max){

	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 && e.keyCode != 39)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}

function newLead()
{
	//alert("save1");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("leadCapturing").action=sourcepath+"/leadCapturingBehind.do?method=leadEntry&leadId=NEW";
	document.getElementById("leadCapturing").submit();
	document.getElementById("processingImage").style.display = '';
	return true;
}

function diffLeadValidation(val){
	

	if((val == "RM" || val == 'RO') && (document.getElementById("rmCode1").value == "" || document.getElementById("rmName1").value == "" || document.getElementById("contactnorm").value == "" || document.getElementById("leadgenzonerm").value == "" )){
		var a = "";
		if(document.getElementById("rmCode1").value == "")
			var a= "* RM code is required \n";
		if(document.getElementById("rmName1").value == "")
			a += "* RM name is required \n";
		if(document.getElementById("contactnorm").value == "")
			a +="* Contact no. is required \n";
		if(document.getElementById("leadgenzonerm").value == "")
			a +="* Branch Name is required \n";
//		if(document.getElementById("leadgencityrm").value == "")
//			a +="* Lead Generation City is required \n";

		alert(a);
		if(document.getElementById("rmCode1").value == "")
		document.getElementById("rmButton").focus();
		
		else if(document.getElementById("rmName1").value == "")
		document.getElementById("rmName1").focus();
		else if(document.getElementById("contactnorm").value == "")
		document.getElementById("contactnorm").focus();
		else if(document.getElementById("leadgenzonerm").value == "")
		document.getElementById("leadgenzonerm").focus();
//		else if(document.getElementById("leadgencityrm").value == "")
//		document.getElementById("leadgencityrm").focus();
		
		return false;
	}else if(val == "VENDOR" && (document.getElementById("vendorCode1").value == "" || document.getElementById("vendorName1").value == "" || document.getElementById("vendorHead1").value == "" || document.getElementById("contactnovendor1").value == "" || document.getElementById("leadgenzonevendor").value == "" )){
		var b = "";
		if(document.getElementById("vendorCode1").value == ""){
			var b="* Vendor code is required \n";
		}
		if(document.getElementById("vendorName1").value == ""){
			b +="* Vendor name is required \n";
		}
		if(document.getElementById("vendorHead1").value == ""){
			b +="* Vendor executive name is required \n";
		}
		if(document.getElementById("contactnovendor1").value == ""){
			b +="* Contact no. is required \n";
		}
		if(document.getElementById("leadgenzonevendor").value == ""){
			b +="* Branch Name is required \n";
		}
//		if(document.getElementById("leadgencityvendor").value == ""){
//			b +="* Lead Generation City is required \n";
//		}
		alert(b);
		if(document.getElementById("vendorCode1").value == "")
		document.getElementById("vendorButton").focus();
		
		else if(document.getElementById("vendorName1").value == "")
		document.getElementById("vendorName1").focus();
		else if(document.getElementById("vendorHead1").value == "")
		document.getElementById("vendorHead1").focus();
		else if(document.getElementById("contactnovendor1").value == "")
		document.getElementById("contactnovendor1").focus();
		else if(document.getElementById("leadgenzonevendor").value == "")
		document.getElementById("leadgenzonevendor").focus();
//		else if(document.getElementById("leadgencityvendor").value == "")
//		document.getElementById("leadgencityvendor").focus();
		return false;
	}
	//else if(val == "BRANCH" && (document.getElementById("branchCode2").value == "" || document.getElementById("branchName2").value == "" || document.getElementById("branchHead1").value == "" || document.getElementById("contactnobranch").value == "" || document.getElementById("leadgencitybranch").value == "")){
	else if(val == "BRANCH" && (document.getElementById("branchName1").value == "" || document.getElementById("branchHead1").value == "" || document.getElementById("contactnobranch").value == "" )){
		var c = "";
//		if(document.getElementById("branchCode2").value == "")
//			var c ="* Branch code is required \n";
		if(document.getElementById("branchName1").value == "")
			c +="* Branch name is required \n";
		if(document.getElementById("branchHead1").value == "")
			c +="* Branch Executive name is required \n";
		if(document.getElementById("contactnobranch").value == "")
			c +="* Contact no. is required \n";
//		if(document.getElementById("leadgencitybranch").value == "")
//			c +="* Lead Generation City is required \n";
		alert(c);
//		if(document.getElementById("branchCode2").value == "")
//		document.getElementById("branchButton").focus();
		
//		else if(document.getElementById("branchName2").value == "")
		if(document.getElementById("branchName1").value == "")
		document.getElementById("branchName1").focus();
		
		else if(document.getElementById("branchHead1").value == "")
		document.getElementById("branchHead1").focus();
		else if(document.getElementById("contactnobranch").value == "")
		document.getElementById("contactnobranch").focus();
//		else if(document.getElementById("leadgencitybranch").value == "")
//		document.getElementById("leadgencitybranch").focus();
		return false;
	}
	else if(val == "OTHERS" && (document.getElementById("branchName1others").value == "" || document.getElementById("branchHead1others").value == "" || document.getElementById("contactnobranchothers").value == "" )){
		var c = "";
//		if(document.getElementById("branchCode2").value == "")
//			var c ="* Branch code is required \n";
		if(document.getElementById("branchName1others").value == "")
			c +="* Branch name is required \n";
		if(document.getElementById("branchHead1others").value == "")
			c +="* Branch Executive name is required \n";
		if(document.getElementById("contactnobranchothers").value == "")
			c +="* Contact no. is required \n";
//		if(document.getElementById("leadgencitybranchothers").value == "")
//			c +="* Lead Generation City is required \n";
		alert(c);
		 if(document.getElementById("branchName1others").value == "")
		document.getElementById("branchName1others").focus();
		
		else if(document.getElementById("branchHead1others").value == "")
		document.getElementById("branchHead1others").focus();
		else if(document.getElementById("contactnobranchothers").value == "")
		document.getElementById("contactnobranchothers").focus();
//		else if(document.getElementById("leadgencitybranchothers").value == "")
//		document.getElementById("leadgencitybranchothers").focus();
		return false;
	}else if((val != "RM" && val!= 'RO' && val != "VENDOR" && val != "BRANCH" && val != "OTHERS" && val != "") && (document.getElementById("description").value == "")){
			alert("* Description is required.");
			document.getElementById("description").focus();		
		return false;
	}else
		return true;
}

function saveNewLead(id)
{
	DisButClass.prototype.DisButMethod();
	var leadGenerator= document.getElementById("leadGenerator");
	
	var val= document.getElementById("leadGenerator").value;
	var sourcepath=document.getElementById("contextPath").value;
	var formName=document.getElementById("leadCapturingDetails");
	var relationship=document.getElementById("relationship");
	var relationshipSince=document.getElementById("relationshipSince");
	var customerName=document.getElementById("customerName");
	var addresstype=document.getElementById("addresstype");
	var contactPerson=document.getElementById("contactPerson");
	var personDesignation=document.getElementById("personDesignation");
	var address1=document.getElementById("address1");
	var country=document.getElementById("country");
	var state=document.getElementById("state");
	var dist=document.getElementById("dist");
	var pincode=document.getElementById("pincode");
	var phoneRes=document.getElementById("phoneRes");
	var phoneOff=document.getElementById("phoneOff");
	var email=document.getElementById("email");
	var altEmail=document.getElementById("altEmail");
	var noOfYears=document.getElementById("noOfYears");
	var industry=document.getElementById("industry");
	var subIndustry=document.getElementById("subIndustry");
	var product=document.getElementById("product");
	var scheme=document.getElementById("scheme");
	var loanAmount=document.getElementById("loanAmount");
	var loanTenure=document.getElementById("loanTenure");
	var loanPurpose=document.getElementById("loanPurpose");
	var customerType=document.getElementById("customerType");
	var groupName=document.getElementById("groupName");
	var groupName1=document.getElementById("groupName1");
	var groupType=document.getElementById("groupType");
	//var custPan=document.getElementById("custPan");
	//var custPanInd=document.getElementById("custPanInd");
	var businessSegment=document.getElementById("businessSegment");
	var corconstitution=document.getElementById("corconstitution");
	var indconstitution=document.getElementById("indconstitution");
    var firstName=document.getElementById("firstName");
	var lastName=document.getElementById("lastName");
	var fatherName=document.getElementById("fatherName");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,50}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
	}
	else{
		if(relationship.value==""  || customerType.value=="" ||  leadGenerator.value=="" || 
				address1.value=="" || country.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
				noOfYears.value=="" ||  product.value=="" || scheme.value=="" || loanAmount.value=="" || loanTenure.value=="" ||
				loanPurpose.value=="" ||addresstype.value=="" || val!="" || email.value=="")
		{
		
			if(emailMandatoryFlag == 'Y')
			{
				var manFields = " leadGenerator / relationship / customerType  / address1 / country / state / dist / pincode_int / phoneOff_int  / noOfYears /  product / scheme / loanAmount / loanTenure_int / loanPurpose / addresstype / email ";
			}
			else
			{
				var manFields = " leadGenerator / relationship / customerType  / address1 / country / state / dist / pincode_int / phoneOff_int  / noOfYears /  product / scheme / loanAmount / loanTenure_int / loanPurpose / addresstype ";
			}
			//start by sachin 
			
			if(val=="OTHERS" && val!="" && document.getElementById("leadgencitybranchothers").value == ""){
				manFields=manFields+"/leadgencitybranchothers";
			}
			if(val=="BRANCH" && val!="" && document.getElementById("leadgencitybranch").value == ""){
				manFields=manFields+"/leadgencitybranch";
			}
			if(val=="VENDOR" && val!="" && document.getElementById("leadgencityvendor").value == ""){
				manFields=manFields+"/leadgencityvendor";
			}
			if(val=="RM" || val=="RO" && val!="" && document.getElementById("leadgencityrm").value == ""){
				manFields=manFields+"/leadgencityrm";
			}
			
		//end by sachin	
				
			if(! validateForm('leadCapturingDetails', manFields)){
			
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}	
		if(customerType.value =='I'){
			if(firstName.value=="" || lastName.value=="" || indconstitution.value=="" || fatherName.value=="")
			{
				var other = "firstName_varchar / lastName__varchar  / indconstitution / fatherName";
				validateForm('leadCapturingDetails', other);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}else{ 
					if(!ck_alphaNumericc.test(firstName.value)){
					 alert("First Name is invalid");
						document.getElementById("firstName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }if(!ck_alphaNumericc.test(lastName.value)){
					 alert("Last Name is invalid");
						document.getElementById("lastName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }if(!ck_alphaNumericc.test(fatherName.value)){
					 alert("Father's/Husband's Name is invalid");
					 document.getElementById("fatherName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }
						
				}
		}
			
			if( email.value!=""  ){
				
			
				if(!valid_email.test(email.value)){
					alert("Invalid Email Id");
					document.getElementById("email").focus();
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			if( altEmail.value!=""  ){
				if(!valid_email.test(altEmail.value)) 	{
						alert("Invalid Alternate Email Id");
						document.getElementById("altEmail").focus();
						document.getElementById("Save").removeAttribute("disabled", "true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
			}
			
		
		
		
		if( phoneOff.value!=""  ){
			
			 if(!ck_numeric.test(phoneOff.value)){
				 alert("Mobile Number is invalid");
					document.getElementById("phoneOff").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
		}
		if( pincode.value!=""  ){
			
			 if(!ck_numericpincode.test(pincode.value)){
				 alert("Pincode is invalid");
					document.getElementById("pincode").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
		}
	/*	if( custPan.value!=""  ){
			if (!ck_panno.test(custPan.value)) {
				 alert(" PAN No. is invalid");
				 document.getElementById("custPan").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
			}
		if( custPanInd.value!=""  ){
			if (!ck_panno.test(custPanInd.value)) {
				 alert(" PAN No. is invalid");
				 document.getElementById("custPanInd").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
			} */
		var pan = $("#custPan").val();
	    if(pan != '')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#custPan").attr("value","");
				$("#custPan").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	    var pan = $("#custPanInd").val();
	    if(pan != '')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#custPanInd").attr("value","");
				$("#custPanInd").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	    if(customerType.value=='I')
	    {
		    var pass = $("#passport").val();
		    if(pass != '')
		    {
				var regex2 = /^[A-Z]{1}\d{7}$/;
				if (!regex2.test(pass) || pass.length != 8) {
					alert("Invalid Passport No.");
					$("#passport").attr("value","");
					$("#passport").focus();
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		    }
	    }
		 
		if(customerType.value=='C'){
			if(groupType.value=='N'){
				if(groupName1.value==""){
					alert("Group Name is mandatory");
					document.getElementById("groupName1").focus();
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}else if(groupType.value=='E'){
					if(groupName.value==""){
						alert("Group Name is mandatory");
						document.getElementById("groupButton").focus();
						document.getElementById("Save").removeAttribute("disabled", "true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
			}

			if( customerName.value=="" || industry.value=="" || subIndustry.value=="" ||groupType.value=="" ||businessSegment.value=="" || corconstitution.value==""){
			var other="	customerName / industry / subIndustry / groupType / businessSegment /corconstitution";
			validateForm('leadCapturingDetails', other);
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}else{ 
				if(!ck_alphaNumericc.test(customerName.value)){
				 alert("Customer Name is invalid");
					document.getElementById("customerName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if( personDesignation.value !=""){
				 if(!ck_alphaNumericc.test(personDesignation.value)){
				 alert("Person Designation is invalid");
					document.getElementById("personDesignation").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
			}
			 if( contactPerson.value!=""  ){
					var manFields = "contactPerson_varchar";
					 if(!ck_alphaNumericc.test(contactPerson.value)){
						 alert("Contact Person is invalid");
							document.getElementById("contactPerson").focus();
						 document.getElementById("Save").removeAttribute("disabled", "true");
						 DisButClass.prototype.EnbButMethod();
						return false;
					 }
				}
			 	
			}
		}

		document.getElementById("leadCapturingDetails").action=sourcepath+"/leadCapturingBehindAction.do?method=saveNewLead&saveForward="+id;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
	
	
	}
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveLeadDetails(id,fwdMsg)
{
	DisButClass.prototype.DisButMethod();
	if(id == "Forward")
	{
		if(!confirm(fwdMsg))	 
	    {
	       	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
	}
	var leadGenerator = document.getElementById("leadGenerator");
	var val= document.getElementById("leadGenerator").value;
	leadchange(document.getElementById('leadGenerator').value);
	var sourcepath=document.getElementById("contextPath").value;
	var formName=document.getElementById("leadCapturingDetails");
	var relationship=document.getElementById("relationship");
	var relationshipSince=document.getElementById("relationshipSince");
	var customerName=document.getElementById("customerName");
	var addresstype=document.getElementById("addresstype");
	var contactPerson=document.getElementById("contactPerson");
	var personDesignation=document.getElementById("personDesignation");
	var address1=document.getElementById("address1");
	var country=document.getElementById("country");
	var state=document.getElementById("state");
	var dist=document.getElementById("dist");
	var pincode=document.getElementById("pincode");
	var phoneRes=document.getElementById("phoneRes");
	var phoneOff=document.getElementById("phoneOff");
	var email=document.getElementById("email");
	var altEmail=document.getElementById("altEmail");
	var noOfYears=document.getElementById("noOfYears");
	var industry=document.getElementById("industry");
	var subIndustry=document.getElementById("subIndustry");
	var product=document.getElementById("product");
	var scheme=document.getElementById("scheme");
	var loanAmount=document.getElementById("loanAmount");
	var loanTenure=document.getElementById("loanTenure");
	var loanPurpose=document.getElementById("loanPurpose");
	var customerType=document.getElementById("customerType");
	var groupName=document.getElementById("groupName");
	var groupName1=document.getElementById("groupName1");
	
	var groupType=document.getElementById("groupType");
	//var custPan=document.getElementById("custPan");
	//var custPanInd=document.getElementById("custPanInd");
	var businessSegment=document.getElementById("businessSegment");
	var constitution=document.getElementById("constitution");
   var indconstitution=document.getElementById("indconstitution");
	
	var corconstitution=document.getElementById("corconstitution");
	var firstName=document.getElementById("firstName");
	var lastName=document.getElementById("lastName");
	var fatherName=document.getElementById("fatherName");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,50}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
    var ck_panno = /^[A-Za-z0-9]{10,11}$/;
    var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		if(relationship.value=="" || customerType.value=="" ||  leadGenerator.value==""  ||
				address1.value=="" || country.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
				noOfYears.value=="" || product.value=="" || scheme.value=="" || loanAmount.value=="" || 
				loanTenure.value=="" || loanPurpose.value=="" ||addresstype.value=="" || val!="" )
		{
			
			if(emailMandatoryFlag == 'Y')
			{
				var manFields = "leadGenerator / relationship / customerType  / address1 / country / state / dist / pincode_int / phoneOff_int  / noOfYears / product / scheme / loanAmount / loanTenure_int / loanPurpose / addresstype / email ";
			}
			else
			{
				var manFields = "leadGenerator / relationship / customerType  / address1 / country / state / dist / pincode_int / phoneOff_int  / noOfYears / product / scheme / loanAmount / loanTenure_int / loanPurpose / addresstype  ";
			}
//start by sachin 
			
			if(val=="OTHERS" && val!="" && document.getElementById("leadgencitybranchothers").value == ""){
				manFields=manFields+"/leadgencitybranchothers";
			}
			if(val=="BRANCH" && val!="" && document.getElementById("leadgencitybranch").value == ""){
				manFields=manFields+"/leadgencitybranch";
			}
			if(val=="VENDOR" && val!="" && document.getElementById("leadgencityvendor").value == ""){
				manFields=manFields+"/leadgencityvendor";
			}
			if(val=="RM" || val=="RO" && val!="" && document.getElementById("leadgencityrm").value == ""){
				manFields=manFields+"/leadgencityrm";
			}
			
		//end by sachin	
			
			if(! validateForm('leadCapturingDetails', manFields)){
			
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}	
		if(customerType.value =='I'){
			if(firstName.value=="" || lastName.value=="" ||indconstitution.value=="" || fatherName.value=="")
			{
				var other = "firstName_varchar / lastName__varchar / indconstitution / fatherName";
			
				validateForm('leadCapturingDetails', other);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}else{ 
					if(!ck_alphaNumericc.test(firstName.value)){
					 alert("First Name is invalid");
						document.getElementById("firstName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }if(!ck_alphaNumericc.test(lastName.value)){
					 alert("Last Name is invalid");
						document.getElementById("lastName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }if(!ck_alphaNumericc.test(fatherName.value)){
					 alert("Father's/Husband's Name is invalid");
					 document.getElementById("fatherName").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				 }
						
				}
		}

		if( email.value!=""  ){
		
			var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
			if(!valid_email.test(email.value)){
				alert("Invalid Email Id");
				document.getElementById("email").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		if( altEmail.value!=""  ){
			var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
			if(!valid_email.test(altEmail.value)) 	{
					alert("Invalid Alternate Email Id");
					document.getElementById("altEmail").focus();
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		}
			
			
		
		
		if( phoneOff.value!=""  ){
		
			 if(!ck_numeric.test(phoneOff.value)){
				 alert("Mobile Number is invalid");
					document.getElementById("phoneOff").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
		}
		if( pincode.value!=""  ){
			
			 if(!ck_numericpincode.test(pincode.value)){
				 alert("Pincode is invalid");
					document.getElementById("pincode").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
		}
	/*	if( custPan.value!=""  ){
		if (!ck_panno.test(custPan.value)) {
		  
		   	 alert(" PAN No. is invalid");
			 document.getElementById("custPan").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
		}
		
		if( custPanInd.value!=""  ){
			if (!ck_panno.test(custPanInd.value)) {
			  
			   	 alert(" PAN No. is invalid");
				 document.getElementById("custPanInd").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
			} */
		var pan = $("#custPan").val();
	    if(pan != '')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#custPan").attr("value","");
				$("#custPan").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	    var pan = $("#custPanInd").val();
	    if(pan != '')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#custPanInd").attr("value","");
				$("#custPanInd").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	    if(customerType.value=='I')
	    {
		    var pass = $("#passport").val();
		    if(pass != '')
		    {
				var regex2 = /^[A-Z]{1}\d{7}$/;
				if (!regex2.test(pass) || pass.length != 8) {
					alert("Invalid Passport No.");
					$("#passport").attr("value","");
					$("#passport").focus();
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		    }
	    }
		//alert("groupName1:::::"+groupName1);
		if(customerType.value=='C'){
			if(groupType.value=='N'){
				if(groupName1.value==""){
					alert("Group Name is mandatory");
					document.getElementById("groupName1").focus();
					 document.getElementById("Save").removeAttribute("disabled", "true");
					 DisButClass.prototype.EnbButMethod();
					return false;
				}
			}else if(groupType.value=='E'){
					if(groupName.value==""){
						alert("Group Name is mandatory");
						document.getElementById("groupButton").focus();
						 document.getElementById("Save").removeAttribute("disabled", "true");
						 DisButClass.prototype.EnbButMethod();
						return false;
					}
			}
			if( customerName.value=="" || industry.value=="" ||groupType.value=="" || subIndustry.value=="" ||businessSegment.value=="" ||corconstitution.value==""){
			var other="	customerName/ industry / groupType / subIndustry / businessSegment / corconstitution";
			validateForm('leadCapturingDetails', other);
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}else{ 
				if(!ck_alphaNumericc.test(customerName.value)){
				 alert("Customer Name is invalid");
					document.getElementById("customerName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if( personDesignation.value !=""){
				 if(!ck_alphaNumericc.test(personDesignation.value)){
				 alert("Person Designation is invalid");
					document.getElementById("personDesignation").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
			 }
			 if( contactPerson.value!=""  ){
					var manFields = "contactPerson_varchar";
					 if(!ck_alphaNumericc.test(contactPerson.value)){
						 alert("Contact Person is invalid");
							document.getElementById("contactPerson").focus();
						 document.getElementById("Save").removeAttribute("disabled", "true");
						 DisButClass.prototype.EnbButMethod();
						return false;
					 }
				}		
			}
		}

		document.getElementById("leadCapturingDetails").action=sourcepath+"/leadCapturingBehindAction.do?method=leadEntrySave&saveForward="+id;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
	

	}
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function validateForm(formName,manFields){
	var ck_numeric = /^[0-9,.]{1,50}$/;
	var ck_alphaNumeric = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,50}$/;
	
	var ck_mail= /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	
	var errors = [];
	var fieldValidate = [];
	var fields = [];
	var elem = document.getElementById(formName).elements;

	for(var i=0; i<= elem.length;i++){
		var str = '';
		if(!elem[i] || elem[i] == "undefined"  || elem[i].value == "undefined" || elem[i].name == (null || '')){
			continue;
			
		}
		//alert("elem[i]111111111"+elem[i].name);
		if(manFields.match(elem[i].name)){
			var matchField = manFields.match(elem[i].name);
				//alert(matchField+"------"+elem[i].value);
				if (elem[i].value == null || elem[i].value == "") {
					
				errors[errors.length] = elem[i].name;
			}else{
					if(manFields.match(elem[i].name+"_varchar")){
						 if (!ck_alphaNumeric.test(elem[i].value)) {
							 fields[fields.length] = elem[i].name;
							 }
					}else if(manFields.match(elem[i].name+"_int")){
						if (!ck_numeric.test(elem[i].value)) {
							fields[fields.length] = elem[i].name;
						}
					}else if(manFields.match(elem[i].name+"_mailId")){
						if (!ck_mail.test(elem[i].value)) {
							fieldValidate[fieldValidate.length] = elem[i].name;
							}
						}
					
					}
				 
		}else
			continue;
	}

	if (errors.length > 0) {
		  reportErrors(errors,matchField);
		  return false;
	 }
	
	if(fieldValidate.length != 0) {
		
		  reportValidate(fieldValidate,matchField);
		  return false;
	 }
		
		  return true;
	
	 
}

function reportErrors(errors, matchField) {
	var msg = "";
	var errorMsg = "";

	for ( var i = 0; i < errors.length; i++) {
		
		var numError = i + 1;
		msg += errors[i];
		
	}
	//sachin
	if (msg.match("leadgencitybranchothers")) {

		errorMsg += "* Lead Generation City is required  \n";
	}
	if (msg.match("leadgencitybranch")) {

		errorMsg += "* Lead Generation City is required  \n";
	}
	if (msg.match("leadgencityvendor")) {

		errorMsg += "* Lead Generation City is required  \n";
	}
	if (msg.match("leadgencityrm")) {

		errorMsg += "* Lead Generation City is required  \n";
	}
	//sachin
	if (msg.match(/leadGenerator(?!1)/g)) {
		errorMsg += "* Lead Generator is required \n";
	
	}
	if (msg.match(/relationship(?!Since)/gi)) {
		errorMsg += "* Relationship is required \n";
	}
	if (msg.match("relationshipSince")) {
		errorMsg += "* Relationship Since is required \n";
	}
	
	if (msg.match("customerType")) {
		errorMsg += "* Customer Type is required \n";
	
	}
	
	if (msg.match("contactPerson")) {
		errorMsg += "* Contact Person is required \n";
	}
	/*if (msg.match("personDesignation")) {
		errorMsg += "* Contact Person Designation is required \n";
	}*/
	if (msg.match("corconstitution")) {
		errorMsg += "* Constitution is required \n";
	}
	if (msg.match("indconstitution")) {
		errorMsg += "* Constitution is required \n";
	}
	
	if (msg.match("custPan") || msg.match("custPanInd")) {
		errorMsg += "* Pan is required \n";
	}
	if (msg.match("addresstype")) {
		errorMsg += "* Address Type is required \n";
	}
	if (msg.match("address1")) {
		errorMsg += "* Address1 is required \n";
	}

	if (msg.match("country")) {
		errorMsg += "* Country is required \n";
	}
	if (msg.match("state")) {
		errorMsg += "* State is required \n";
	}
	if (msg.match("dist")) {
		errorMsg += "* District is required \n";
	}
	if (msg.match("pincode")) {
		errorMsg += "* Pincode is required \n";
	}
	
	if (msg.match("phoneOff")) {
		errorMsg += "* Mobile Number is required \n";
	}
	if (msg.match("email")) {
		errorMsg += "* Email is required \n";
	}
	if (msg.match("noOfYears")) {
		errorMsg += "* No of Years is required \n";
	}
	if (msg.match("customerName")) {
		errorMsg += "* Customer Name is required \n";
	}
	
	if (msg.match("groupType")) {
		//errorMsg += "* Group Type is required \n";
		errorMsg += "* Group Name is required \n";
	}
	if (msg.match("registrationNo")) {
		errorMsg += "* Registration No is required \n";
	}
	if (msg.match("businessSegment")) {
		errorMsg += "* Business Segment is required \n";
	}
	if (msg.match(/industry/g)) {
		errorMsg += "* Industry is required \n";
	}
	if (msg.match("subIndustry")) {
		errorMsg += "* Sub Industry is required \n";
	}
	if (msg.match("product")) {
		errorMsg += "* Product is required \n";
	}
	if (msg.match("scheme")) {
		errorMsg += "* Scheme is required \n";
	}
	if (msg.match("loanAmount")) {
		errorMsg += "* Loan Amount is required \n";
	}
	if (msg.match("loanTenure")) {
		errorMsg += "* Loan Tenure is required \n";
	}
	if (msg.match("loanPurpose")) {
		errorMsg += "* Purpose is required \n";
	}
	
	if (msg.match("firstName")) {
		errorMsg += "* First Name  is required \n";
	}
	if (msg.match("lastName")) {
		errorMsg += "* Last Name  is required \n";
	}
	if (msg.match("fatherName")) {
		errorMsg += "* Father's/Husband's Name  is required \n";
	}
	if (msg.match("custDOB")) {

		errorMsg += "* Customer Date of Birth is required \n";
	}
	
	if (msg.match(/leadGenerator(?!1)/g)) {

		document.getElementById("leadGenerator").focus();
	}
	else if (msg.match(/relationship(?!Since)/gi)) {
		document.getElementById("relationship").focus();
	}
	else if (msg.match("relationshipSince")) {
		document.getElementById("relationship").focus();

	}
	else if (msg.match("customerType")) {
		document.getElementById("customerType").focus();
	}
	
	else if (msg.match("contactPerson")) {
		document.getElementById("contactPerson").focus();
	}
	/*else if (msg.match("personDesignation")) {
		document.getElementById("personDesignation").focus();
	}*/
	else if (msg.match("corconstitution")) {
		document.getElementById("corconstitution").focus();
	}
	else if (msg.match("indconstitution")) {
		document.getElementById("indconstitution").focus();
	}
	else if (msg.match("custPan")) {
		document.getElementById("custPan").focus();
	}
	else if (msg.match("custPanInd")) {
		document.getElementById("custPanInd").focus();
	}
	else if (msg.match("address1")) {
		document.getElementById("address1").focus();
	}

	else if (msg.match("country")) {
		document.getElementById("countryButton").focus();
	}
	else if (msg.match("state")) {
		document.getElementById("stateButton").focus();
	}
	else if (msg.match("dist")) {
		document.getElementById("distButton").focus();
	}
	else if (msg.match("pincode")) {
		document.getElementById("pincode").focus();
	}
	
	else if (msg.match("phoneOff")) {
		document.getElementById("phoneOff").focus();
	}
	
	else if (msg.match("noOfYears")) {
		document.getElementById("noOfYears").focus();
	}
	
	else if (msg.match("customerName")) {
		document.getElementById("customerName").focus();
	}
	else if (msg.match("groupType")) {
		document.getElementById("groupType").focus();
	}
	else if (msg.match("registrationNo")) {
		document.getElementById("registrationNo").focus();
	}

	else if (msg.match("businessSegment")) {
		document.getElementById("businessSegment").focus();
	}
	else if (msg.match(/industry/g)) {
		document.getElementById("industryButton").focus();
	}
	else if (msg.match("subIndustry")) {
		document.getElementById("subIndustryButton").focus();
	}
	else if (msg.match("firstName")) {
		document.getElementById("firstName").focus();
	}
	else if (msg.match("lastName")) {
		document.getElementById("lastName").focus();
	}
	else if (msg.match("fatherName")) {
		document.getElementById("fatherName").focus();
	}
	else if (msg.match("custDOB")) {
		document.getElementById("custDOB").focus();
	}
	
	else if (msg.match("product")) {
		document.getElementById("productButton").focus();
	}
	else if (msg.match("scheme")) {
		document.getElementById("schemeButton").focus();
	}
	else if (msg.match("loanAmount")) {
		document.getElementById("loanAmount").focus();
	}
	else if (msg.match("loanTenure")) {
		document.getElementById("loanTenure").focus();
	}
	else if (msg.match("loanPurpose")) {
		document.getElementById("loanPurpose").focus();
	}
	
	
	alert(errorMsg);

}

function reportValidate(fieldValidate, matchField) {
	var msg = "";
	var msgValidate = "";
	alert(fieldValidate.length);
	for ( var i = 0; i < fieldValidate.length; i++) {
		
		var numError = i + 1;
		msg += fieldValidate[i];
	}
	if (msg.match("email")) {
		msgValidate += "* Email id is not valid. \n";
	}
	if (msg.match("altEmail")) {
		msgValidate += "* Alternate Mail id is not valid. \n";
	}
	
//	if (msg.match(/relationship(?!Since)/gi)) {
//		document.getElementById("relationship").focus();
//	}
//	else if (msg.match("relationshipSince")) {
//		document.getElementById("relationship").focus();
//
//	}
	alert(msgValidate);

}

function saveAllocation(id)
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("branchDet").value == "" || document.getElementById("rmAllo").value == ""){
		var la = "";
		if(document.getElementById("branchDet").value == "")
			la += "* Lead Allocation Branch is required \n";
		if(document.getElementById("rmAllo").value == "")
			la += "* Lead Allocation RM is required";
		alert(la);
		if(document.getElementById("branchDet").value == "")
			document.getElementById("branchButton").focus();
		else if(document.getElementById("rmAllo").value == "")
			document.getElementById("leadButton").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var sourcepath=document.getElementById("contextPath").value;
	//if(validateLeadCapturingDynaValidatorForm(document.getElementById("leadAllocationDetails")))
	//{
		document.getElementById("leadAllocationDetails").action=sourcepath+"/leadAllocatedBehindAction.do?method=saveAllocation&Allocated="+id;
		document.getElementById("leadAllocationDetails").submit();
		document.getElementById("processingImage").style.display = '';
		return true;
	//}
}

function saveTrackingDetails(id,currBussDate){
	DisButClass.prototype.DisButMethod();
	var formatD=document.getElementById("formatD").value;
	var currentBusinessDate = currBussDate;
	currentBusinessDate = getDateObject(currentBusinessDate,formatD.substring(2, 3));
	var expectedLoginDate= document.getElementById("expectedLoginDate").value;
	expectedLoginDate = getDateObject(expectedLoginDate,formatD.substring(2, 3));
	
	var expectedDisbursalDate=document.getElementById("expectedDisbursalDate").value;
	expectedDisbursalDate=getDateObject(expectedDisbursalDate,formatD.substring(2, 3));
	
	if(document.getElementById("decision").value == "Rejected"){
		if(document.getElementById("decision").value == "" || document.getElementById("remarks").value == "" || document.getElementById("reasonDesc").value == ""){
			var lt = "";
			if(document.getElementById("decision").value == "")
				lt += "* Lead Decision is required \n";
			if(document.getElementById("remarks").value == "")
				lt += "* Remarks is required \n";
			if(document.getElementById("reasonDesc").value == "")
				lt += "* Reject Reason is required \n";
			alert(lt);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}else {
		if(document.getElementById("decision").value == "" || document.getElementById("remarks").value == "" || document.getElementById("expectedLoginDate").value == "" || document.getElementById("expectedDisbursalDate").value == ""){
			var lt = "";
			if(document.getElementById("expectedLoginDate").value == "")
				lt += "* Expected Login Date is required \n";
			if(document.getElementById("expectedDisbursalDate").value == "")
				lt += "* Expected Disbursal Date is required \n";
			if(document.getElementById("decision").value == "")
				lt += "* Lead Decision is required \n";
			if(document.getElementById("remarks").value == "")
				lt += "* Remarks is required \n";
			alert(lt);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if((expectedLoginDate < currentBusinessDate) || (expectedLoginDate >= expectedDisbursalDate))
		{
			var lt = "";
			if(expectedLoginDate < currentBusinessDate)
				lt += " Expected Login Date must be equal to or greater than Business Date \n";
			if(expectedLoginDate >= expectedDisbursalDate)
				lt += " Expected Disbursal Date must be greater than Expected Login Date";
			alert(lt);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("leadDecision").action=sourcepath+"/leadAllocatedBehindAction.do?method=saveTrackingDetails";
		document.getElementById("leadDecision").submit();
		document.getElementById("processingImage").style.display = '';
		return true;
}

function saveLeadNotepadData(alert1)
{
	DisButClass.prototype.DisButMethod();
	if(cpNoteCodeValidate()){
		
				var formatD=document.getElementById("formatD").value;
				var meetingDate = document.getElementById("date").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(meetingDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				var businessdate = document.getElementById("businessdate").value;
				var bDate=getDateObject(businessdate,formatD.substring(2, 3));
				if(dt1>bDate)
			    {
					alert("Note date should be less than or equal to business date.");
					document.getElementById("date").value='';
					document.getElementById("date").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
			    }
				if(followupDate!="")
				{
					if (dt2<dt1)
					{
						alert(alert1);
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}
				var contextPath =document.getElementById('contextPath').value ;
				document.getElementById("leadNotepadForm").action = contextPath+"/leadNotePadAction.do?method=saveNotepadData";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("leadNotepadForm").submit();
	
		return true;
	}else {
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
		
}

/*function cpfollowupValidate()
{	
		var alphaExp = /^[a-zA-Z]+$/;
		var msg1='',msg2='',msg3='',msg4='';
		
		if((document.getElementById("followupDate").value)=='')
		{
			msg1="* Follow Up Date is Required \n";
		}
		if((document.getElementById("followUpPerson").value)=='')
		{
			 msg2="* Follow Up Person is Required\n";
		}
		if((document.getElementById("followUpLocation").value)=='')
		{
			 msg3="* Follow Up Location is Required\n";
		}
		if((document.getElementById("followupRemarks").value)=='')
		{
			 msg4="* Follow Up Remarks is Required\n";
		}
		
		if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
		{
			alert(msg1+msg2+msg3+msg4);
		}
		
		if(document.getElementById("followupDate").value=="")
		{
			document.getElementById("followupDate").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			
		if(document.getElementById("followUpPerson").value =="")
		{
			document.getElementById("followUpPerson").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(document.getElementById("followUpLocation").value =="")
		{
			document.getElementById("followUpLocation").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}

		
		if(document.getElementById("followupRemarks").value =="")
		{
			document.getElementById("followupRemarks").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		return true;
}

function cpNoteCodeValidate(){
	
	 var noteCode = document.getElementById("noteCode");
	 var date = document.getElementById("date");
	 var meetingTime = document.getElementById("meetingTime");
	 var personMet = document.getElementById("personMet");
	 var meetingLocation = document.getElementById("meetingLocation");
	 var meetingRemarks = document.getElementById("meetingRemarks");
	 var followUp = document.getElementById("followUp");
	 var formatD=document.getElementById("formatD").value;
	 var businessdate = document.getElementById("businessdate").value;
	 var dt1=getDateObject(date.value,formatD.substring(2, 3));
	 var bDate=getDateObject(businessdate,formatD.substring(2, 3));
	 
	 	var a = "";
	 	if(noteCode.value  == "" || date.value == "" || personMet.value  == "" || meetingLocation.value  == "" || meetingRemarks.value  == "" || followUp.value  == ""){
	 		if(noteCode.value  == ""){
				a = "* Note Code is required \n";
			}
			if(date.value == ""){
				a += "* Date is required \n";
			}
			if(personMet.value == ""){
				a +="* Person met is required \n";
			}
			if(meetingLocation.value == ""){
				a +="* Meeting Location is required \n";
			}
			if(meetingRemarks.value == ""){
				a +="* Notes are required \n";
			}
			if(followUp.value == ""){
				a +="* Follow up is required \n";
			}
			
				  if(a.match(/Code/gi)){
					  	noteCode.focus();
			}else if(a.match(/Date/gi)){
						date.focus();
			}else if(a.match(/Person/gi)){
						personMet.focus();
			}else if(a.match(/Location/gi)){
						meetingLocation.focus();
			}else if(a.match(/are/gi)){
						meetingRemarks.focus();
			}else if(a.match(/Follow/gi)){
						followUp.focus();
			}
	
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false;
	 	}
	 	else if(date.value != ""){
	 		if(dt1>bDate)
		    {
				alert("Note date should be less than or equal to business date.");
				document.getElementById("date").value='';
				document.getElementById("date").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
		    }
	 		return true;
	 	}else
	 			return true;
} */

function cpNotepadDisable()
{
	// alert("TEST");
	if(document.getElementById("followUp").value == "N")
	{
		document.getElementById("showDate").style.display='none';
		document.getElementById("notshowDate").style.display='block';
		document.getElementById("followupTime").disabled=true;
		document.getElementById("followUpPerson").disabled=true;
		document.getElementById("followUpLocation").disabled=true;
		document.getElementById("followupRemarks").disabled=true;
	}
	else if(document.getElementById("followUp").value == "Y")
	{
		document.getElementById("showDate").style.display='block';
		document.getElementById("notshowDate").style.display='none';
		document.getElementById("followupTime").disabled=false;
		document.getElementById("followUpPerson").disabled=false;
		document.getElementById("followUpLocation").disabled=false;
		document.getElementById("followupRemarks").disabled=false;
	}
}

function cpNoteCodeValidate()
{
		var noteCode = document.getElementById("noteCode");
		var date = document.getElementById("date");
		var meetingTime = document.getElementById("meetingTime");
		var personMet = document.getElementById("personMet");
		var meetingLocation = document.getElementById("meetingLocation");
		var meetingRemarks = document.getElementById("meetingRemarks");
		var followUp = document.getElementById("followUp");
		var followupDate = document.getElementById("followupDate");
		var followUpPerson = document.getElementById("followUpPerson");
		var followUpLocation = document.getElementById("followUpLocation");
		var followupRemarks = document.getElementById("followupRemarks");
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(date.value,formatD.substring(2, 3));
		var businessdate = document.getElementById("businessdate").value;
		var bDate=getDateObject(businessdate,formatD.substring(2, 3));
	 
	 	var a = "";
	 if(followUp.value =='Y' || followUp.value=="")
	 {
	 	if(noteCode.value  == "" || date.value == "" || personMet.value  == "" || meetingLocation.value  == "" || meetingRemarks.value  == "" || followUp.value  == "" || followupDate.value=="" ||followUpPerson.value=="" ||followUpLocation.value=="" ||followupRemarks.value==""){
	 		if(noteCode.value  == ""){
				a = "* Note Code is required \n";
			}
			if(date.value == ""){
				a += "* Notes Date is required \n";
			}
			if(personMet.value == ""){
				a +="* Person met is required \n";
			}
			if(meetingLocation.value == ""){
				a +="* Meeting Location is required \n";
			}
			if(meetingRemarks.value == ""){
				a +="* Notes are required \n";
			}
			if(followUp.value == ""){
				a +="* Follow up is required \n";
			}
			
			if(followupDate.value=='')
			{
				a +="* Follow Up Date is Required \n";
			}
			if(followUpPerson.value=='')
			{
				a +="* Follow Up Person is Required\n";
			}
			if(followUpLocation.value=='')
			{
				a +="* Follow Up Location is Required\n";
			}
			if(followupRemarks.value=='')
			{
				a +="* Follow Up Remarks is Required\n";
			}
			
			if(noteCode.value==""){
					  	noteCode.focus();
			}else if(date.value == ""){
						date.focus();
			}else if(personMet.value==""){
						personMet.focus();
			}else if(meetingLocation.value==""){
						meetingLocation.focus();
			}else if(meetingRemarks.value==""){
						meetingRemarks.focus();
			}else if(followUp.value==""){
						followUp.focus();
			}else if(followupDate.value == ""){
						followupDate.focus();
			}else if(followUpPerson.value == ""){
						followUpPerson.focus();
			}else if(followUpLocation.value == ""){
						followUpLocation.focus();
			}else if(followupRemarks.value == ""){
						followupRemarks.focus();
			}
			
			alert(a);
			DisButClass.prototype.EnbButMethod();
			return false;
	 	}
	 	return true;
	 }	
	 else if(followUp.value =='N')
	 {
		 if(noteCode.value  == "" || date.value == "" || personMet.value  == "" || meetingLocation.value  == "" || meetingRemarks.value  == "" ){
		 		if(noteCode.value  == ""){
					a = "* Note Code is required \n";
				}
				if(date.value == ""){
					a += "* Notes Date is required \n";
				}
				if(personMet.value == ""){
					a +="* Person met is required \n";
				}
				if(meetingLocation.value == ""){
					a +="* Meeting Location is required \n";
				}
				if(meetingRemarks.value == ""){
					a +="* Notes are required \n";
				}
								
				if(a.match(/Code/gi)){
						  	noteCode.focus();
				}else if(a.match(/Date/gi)){
							date.focus();
				}else if(a.match(/Person/gi)){
							personMet.focus();
				}else if(a.match(/Location/gi)){
							meetingLocation.focus();
				}else if(a.match(/are/gi)){
							meetingRemarks.focus();
				}else if(a.match(/Follow/gi)){
							followUp.focus();
				}
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false;
		 	}
		 return true;
	 }
	else
	 	return true;
}

function notepadTime()
{
	var timeStr = document.getElementById("meetingTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('meetingTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('meetingTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('meetingTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('meetingTime').value='';
	return false;
	}
}

function followTime()
{
	var timeStr = document.getElementById("followupTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('followupTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('followupTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('followupTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('followupTime').value='';
	return false;
	}
}

function searchLead(stage)
{
	DisButClass.prototype.DisButMethod();
	//alert("In searchLead::::::");
	var leadNo=document.getElementById("leadno").value;
	var leadGenerationDate=document.getElementById("applicationdate").value;
	var customerName=document.getElementById("customername").value;
	//alert("In customerName::::::"+customerName);
		var product=document.getElementById("product").value;
	var rmName=document.getElementById("leadGenerator").value;
//	var branch=document.getElementById("branch").value;
//	var vendor=document.getElementById("vendor").value;
	var contextPath= document.getElementById("contextPath").value;
	var name=document.getElementById("reportingToUserId").value;
	//alert("newSearchDeal"+stage);
	if(name!=''|| leadNo!=''|| leadGenerationDate!=''||customerName!=''||product!=''||rmName!='')
	{
		if(leadNo != '' && customerName.length <= 3)
		{
			document.getElementById("leadCapturing").action=contextPath+"/leadCapturingSearchBehind.do?userId="+name;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("leadCapturing").submit();
			return true;
		}
		if(customerName !='' && customerName.length >= 3)
		{
			document.getElementById("leadCapturing").action=contextPath+"/leadCapturingSearchBehind.do?userId="+name;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("leadCapturing").submit();
			return true;
		}else if(customerName == '')
		{
			document.getElementById("leadCapturing").action=contextPath+"/leadCapturingSearchBehind.do?userId="+name;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("leadCapturing").submit();
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function leadTrack(stage)
{
	DisButClass.prototype.DisButMethod();
	var leadNo=document.getElementById("leadno").value;
	var leadGenerationDate=document.getElementById("applicationdate").value;
	var customerName=document.getElementById("customername").value;
	var product=document.getElementById("product").value;
	var rmName=document.getElementById("leadGenerator").value;
	var name=document.getElementById("reportingToUserId").value;
//	var branch=document.getElementById("branch").value;
//	var vendor=document.getElementById("vendor").value;
	var contextPath= document.getElementById("contextPath").value;
	//alert("newSearchDeal"+stage);
	if(name !='' || leadNo != '' || leadGenerationDate!=''||customerName!=''||product!=''||rmName!='')
	{
		if(leadNo != '' && customerName.length <= 3){
			document.getElementById("leadTracking").action=contextPath+"/leadTrackingSearch.do?method=searchDealCapturing&stage="+stage+"&userId="+name;
			document.getElementById("leadTracking").submit();
			document.getElementById("processingImage").style.display = '';
			return true;
		}
		if(customerName !='' && customerName.length >= 3)
		{
			document.getElementById("leadTracking").action=contextPath+"/leadTrackingSearch.do?method=searchDealCapturing&stage="+stage+"&userId="+name;
			document.getElementById("leadTracking").submit();
			document.getElementById("processingImage").style.display = '';
			return true;
		}else if(customerName == ''){
			document.getElementById("leadTracking").action=contextPath+"/leadTrackingSearch.do?method=searchDealCapturing&stage="+stage+"&userId="+name;
			document.getElementById("leadTracking").submit();
			document.getElementById("processingImage").style.display = '';
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function disableDate(){
	if(document.getElementById("decision").value == "Rejected")	{
		document.getElementById("expectedLoginDate").readOnly  = true;
		document.getElementById("expectedDisbursalDate").readOnly = true;
		document.getElementById("expectedLoginDate").value = '00-00-0000' ;
		document.getElementById("expectedDisbursalDate").value = '00-00-0000';
		document.getElementById("caseRejected").style.display = 'none';
		document.getElementById("leadRejected").style.display = '';
		
	}else {
		document.getElementById("expectedLoginDate").readOnly  = false;
		document.getElementById("expectedDisbursalDate").readOnly = false;
		document.getElementById("expectedLoginDate").value = '' ;
		document.getElementById("expectedDisbursalDate").value = '';
		document.getElementById("caseRejected").style.display = '';
		document.getElementById("leadRejected").style.display = 'none';
	}
}

function deleteLeadDetails(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var leadId=document.getElementById("leadId").value;
	agree=confirm("Are you sure,You want to delete this lead.Do you want to continue?");
	if (!(agree))
	{
    	document.getElementById("Save").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("leadCapturingDetails").action=sourcepath+"/leadCapturingBehindAction.do?method=deleteLead&leadId"+leadId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
}
	
	
}
function custtype(){
	var customerType=document.getElementById("customerType").value;
	if(customerType=='C'){
		document.getElementById("constitution").style.display='none';
		document.getElementById("individualfield").style.display='none';
		document.getElementById("corporatefield").style.display='';
		document.getElementById("Individualconstitution").style.display='none';
		document.getElementById("corporateconstitution").style.display='';
		document.getElementById("firstName").value="";
		document.getElementById("lastName").value="";
		document.getElementById("custDOB").value="";
		document.getElementById("contactPersonDesignation").style.display='';
	
	}else if(customerType=='I'){
		document.getElementById("constitution").style.display='none';
		document.getElementById("corporatefield").style.display='none';
		document.getElementById("individualfield").style.display='';
		document.getElementById("Individualconstitution").style.display='';
		document.getElementById("corporateconstitution").style.display='none';
		//document.getElementById("groupName").value="";
		document.getElementById("registrationNo").value="";
		document.getElementById("businessSegment").value="";
		document.getElementById("industry").value="";
		document.getElementById("subIndustry").value="";
		document.getElementById("lbxIndustry").value="";
		document.getElementById("lbxSubIndustry").value="";
		//document.getElementById("hGroupId").value="";
		document.getElementById("contactPersonDesignation").style.display='none';

	}else{
		document.getElementById("corporatefield").style.display='none';
		document.getElementById("individualfield").style.display='none';
		document.getElementById("contactPersonDesignation").style.display='none';
		document.getElementById("hGroupId").value="";
		document.getElementById("lbxIndustry").value="";
		document.getElementById("lbxSubIndustry").value="";
		document.getElementById("customerName").value="";
		document.getElementById("groupName").value="";
		document.getElementById("registrationNo").value="";
		document.getElementById("custPan").value="";
		document.getElementById("custPanInd").value="";
		document.getElementById("businessSegment").value="";
		document.getElementById("industry").value="";
		document.getElementById("subIndustry").value="";
		document.getElementById("firstName").value="";
		document.getElementById("lastName").value="";
		document.getElementById("custDOB").value="";
		
		document.getElementById("Individualconstitution").style.display='none';
		document.getElementById("corporateconstitution").style.display='none';
		document.getElementById("constitution").style.display='';
	
	}
}

function groupselect(){
	var groupType=document.getElementById("groupType").value;
	if(groupType=='E'){
		document.getElementById("groupLov").style.display="";
		document.getElementById("groupText").style.display="none";	
		document.getElementById("groupName1").setAttribute("disabled", true);
		document.getElementById("groupName").removeAttribute("disabled");
		document.getElementById("hGroupId").removeAttribute("disabled");
	}else if(groupType=='N'){
		document.getElementById("groupText").style.display="";
		document.getElementById("groupLov").style.display="none";
		document.getElementById("groupName1").removeAttribute("disabled", true);
		document.getElementById("groupName").setAttribute("disabled", true);
		document.getElementById("hGroupId").setAttribute("disabled", true);
		return true;
	}else{
		document.getElementById("groupText").style.display="none";
		document.getElementById("groupLov").style.display="none";
	}
}
function updateCust(val){
		document.getElementById("customerType").value = val;
	}
	function cleanUp(){
		//CLEAN UP CODE START
			document.getElementById("customerName").value = '';
     		document.getElementById("hGroupId").value = '';
     		document.getElementById("registrationNo").value = '';
     		document.getElementById("custPan").value = '';
     		document.getElementById("custPanInd").value = '';
     		document.getElementById("lbxIndustry").value = '';
     		document.getElementById("lbxSubIndustry").value = '';
     		document.getElementById("customerType").value = '';
     		document.getElementById("firstName").value = '';
     		document.getElementById("lastName").value = '';
     		document.getElementById("custDOB").value = '';
     	 	document.getElementById("address1").value = '';
     		document.getElementById("address2").value = '';
     		document.getElementById("addresstype").value = '';
     		document.getElementById("noOfYears").value = '';
        	document.getElementById("pincode").value = '';
		    document.getElementById("txtCountryCode").value = '';
		    document.getElementById("txtStateCode").value = '';
		    document.getElementById("txtDistCode").value = '';
		    document.getElementById("email").value = '';
			document.getElementById("groupName").value = '';
			document.getElementById("dist").value = '';
			document.getElementById("state").value = '';
			document.getElementById("industry").value = '';
			document.getElementById("subIndustry").value = '';
			document.getElementById("phoneOff").value = '';
			//CLEAN UP CODE END 
	}
	
	function checkcustDOB(custDOB)
	{
		var dobDate=document.getElementById("custDOB").value;
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(curDate,formatD.substring(2,3));
		var dt3=getDateObject(dobDate,formatD.substring(2,3));
		if(dt1<=dt3)
				{
					alert("Date of Birth should be less than Business date.");
					document.getElementById("custDOB").value='';
					return false;
				}
	}
	
	function showHideDescLov()
	{
		var source=document.getElementById("source").value;
		document.getElementById("description").value='';
		if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING")
		{
			document.getElementById("srcLOV").style.display="";
			document.getElementById("description").readOnly = true;
		}
		else
		{
			document.getElementById("srcLOV").style.display="none";
			document.getElementById("description").readOnly = false;
		}
	}
	
	function showHideDescLovOnLoad()
	{
		var source=document.getElementById("source").value;
		if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING")
		{
			document.getElementById("srcLOV").style.display="";
			document.getElementById("description").readOnly = true;
		}
		else
		{
			document.getElementById("srcLOV").style.display="none";
			document.getElementById("description").readOnly = false;
		}
	}

	function openDescLov()
	{
		var source=document.getElementById("source").value;
		if(source == "DEALER")
		{
			openLOVCommon(531,'leadCapturingDetails','description','','', '','','','lbxDescription');
		}
		else if(source == "CONNECTOR")
		{
			openLOVCommon(532,'leadCapturingDetails','description','','', '','','','lbxDescription');
		}
		else if(source == "DSA")
		{
			openLOVCommon(533,'leadCapturingDetails','description','','', '','','','lbxDescription');
		}
		else if(source == "EXISTING")
		{
			openLOVCommon(534,'leadCapturingDetails','lbxDescription','','', '','','','description');
		}
		else if(source == "TELECALLER")
		{
			openLOVCommon(570,'leadCapturingDetails','description','','', '','','','lbxDescription');
		}
	}