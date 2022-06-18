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
		if(document.getElementById("rm")){
		document.getElementById("rm").style.display = 'none';
		}
		if(document.getElementById("vendor")){
		document.getElementById("vendor").style.display = 'none';
		}
		if(document.getElementById("branch")){
		document.getElementById("branch").style.display = 'none';
		}
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
	document.getElementById("leadCapturing").action=sourcepath+"/preDealCapturingBehind.do?method=leadEntry&leadId=NEW";
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
	var customerName=document.getElementById("customerName");
	var addresstype=document.getElementById("addresstype1");
	var address1=document.getElementById("address1");
	var country=document.getElementById("country");
	var state=document.getElementById("state");
	var dist=document.getElementById("dist");
	var pincode=document.getElementById("pincode");
	var phoneOff=document.getElementById("phoneOff");
	var email=document.getElementById("email");
	var industry=document.getElementById("industry");
	var subIndustry=document.getElementById("subIndustry");
	var product=document.getElementById("product");
	var scheme=document.getElementById("scheme");
	var loanAmount=document.getElementById("loanAmount");
	var loanTenure=document.getElementById("loanTenure");
	var customerType=document.getElementById("customerType1");
	var groupName=document.getElementById("groupName");
	var groupName1=document.getElementById("groupName1");
	var groupType=document.getElementById("groupType");
	var businessSegment=document.getElementById("businessSegment1");
	var corconstitution=document.getElementById("corconstitution");
	var indconstitution=document.getElementById("indconstitution");
    var firstName=document.getElementById("firstName");
	var lastName=document.getElementById("lastName");
	var fatherName=document.getElementById("fatherName");
	var lbxRelationship=document.getElementById("lbxRelationship").value;
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	/*Sanjay Kushwah added this 22032018*/
	var sourceList=document.getElementById("sourceList").value;
	var description=document.getElementById("description").value;
	/*Sanjay Kushwah added this 22032018*/
	// pooja changes start for application form no
	var applicationFormNo=document.getElementById("applicationFormNoRm").value;
	
	if(applicationFormNo==''){
		alert("Application Form No is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(lbxRelationship==''){
		alert("Relationship Manager/Team Leader is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	//pooja changes end for application form no
	if(addresstype.value==''){
		alert("Address Type is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var primaryPhoneNo=document.getElementById("phoneOff").value;
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Invalid Mobile No. ");
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	
	var lbxCustomerId= document.getElementById("lbxCustomerId").value;
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
	}
	else{
		if(customerType.value=="I"){
			if(!checkCurrentAge("custDOB")){
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			var genderIndividual=document.getElementById("genderIndividual").value;
			var passport=document.getElementById("passport").value;
			var aadhaar=document.getElementById("aadhaar").value;
			var appAadhaarInd=document.getElementById("aadhaar").value;
			var custPanInd=document.getElementById("custPanInd").value;
			var appCustPanInd=document.getElementById("custPanInd").value;
			var dlNumber=document.getElementById("dlNumber").value;
			var voterId=document.getElementById("voterId").value;
			//var appconSubprofile=document.getElementById("appconSubprofile").value;
			var indconstitution=document.getElementById("indconstitution").value;
			if(passport=="" && aadhaar=="" && custPanInd=="" && dlNumber=="" && voterId==""  ){
				alert("Please capture atleast one KYC");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			/*if(aadhaar==""){
				alert("Aadhar/UID No. is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}*/
			if(custPanInd==""){
				alert("Applicant Pan No. is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(indconstitution==""){
				alert("Applicant Constitution is required!");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			/*if(appconSubprofile==""){
				alert("Applicant Sub Profile is required!");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}*/
		}
		
		if(relationship.value==""  || customerType.value=="" ||  
				address1.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
				  product.value=="" || scheme.value=="" || loanAmount.value=="" || loanTenure.value=="" ||
				  val!="" || email.value==""  || sourceList=="" || description==""  )
		{
		
			if(emailMandatoryFlag == 'Y')
			{
				var manFields = " relationship / customerType  / address1 / state / dist / pincode_int / phoneOff_int  /   product / scheme / loanAmount / loanTenure_int  / email  /sourceList / description   ";
			}
			else
			{
				var manFields = " relationship / customerType  / address1 / state / dist / pincode_int / phoneOff_int  /   product / scheme / loanAmount / loanTenure_int /sourceList /description  ";
			}
			//start by sachin 
			
			if(val=="OTHERS" && val!="" ){
				manFields=manFields;
			}
			if(val=="BRANCH" && val!="" ){
				manFields=manFields;
			}
			if(val=="VENDOR" && val!=""){
				manFields=manFields ;
			}
			if(val=="RM" || val=="RO" && val!="" ){
				manFields=manFields;
			}
			
		//end by sachin	
				
			if(! validateForm('leadCapturingDetails', manFields)){
			
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			 /*Sanjay Kushwaha added this 22032018*/
			if((sourceList!='')&&(description=='')){
				alert("Description is required");
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			 /*Sanjay Kushwaha added this 22032018*/
			
		}	
		 var Apprelationshipvl=document.getElementById("relationship").value;
			
			
			if(Apprelationshipvl=="New"){
				var AppcustomerType1vl=document.getElementById("customerType1").value;
				if(AppcustomerType1vl=""){
					alert(" Please Select Applicant  Customer Type");

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
		
		var iDefValue=0;
		var iMinValue=0;
		var iMaxValue=0;
		if(document.getElementById("loanTenure").value!='') 
		{
			iDefValue=parseInt(document.getElementById("loanTenure").value);
		}
		if(document.getElementById("minTenure").value!='')
		{
			iMinValue=parseInt(document.getElementById("minTenure").value);
		}
		if(document.getElementById("maxTenure").value!='')
		{
			iMaxValue=parseInt(document.getElementById("maxTenure").value);
		}
		//alert("iDefValue: "+iDefValue+" iMinValue: "+iMinValue+" iMaxValue: "+iMaxValue);
		if((iDefValue<iMinValue)||(iDefValue>iMaxValue))
		{
			alert("Please Insert Tenure between "+iMinValue+" and "+iMaxValue);
			document.getElementById("loanTenure").value='';
			 DisButClass.prototype.EnbButMethod();
			return false;
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
			if(businessSegment.value==''){
				alert("Business Segment is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			if( customerName.value=="" ||groupType.value==""  || corconstitution.value==""){
			var other="	customerName /  groupType  /corconstitution";
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
			 }
			
			 	
			}
		}
var Apprelationshipvl=document.getElementById("relationship").value;
if(Apprelationshipvl=="New"){
	var AppcustomerType1vl=document.getElementById("customerType1").value;
	if(AppcustomerType1vl==""){
		alert(" Please Select Applicant  Customer Type");

		 DisButClass.prototype.EnbButMethod();
		return false;
	}
}
if(document.getElementById("customerType1").value=="C"){
if(document.getElementById("custPan").value=="")
{
alert("Applicant Pan No is required!");
document.getElementById("Save").removeAttribute("disabled", "true");
DisButClass.prototype.EnbButMethod();
return false;
}
}
if ($('#coAppStatus').is(":checked"))
{
	 
	var scoApprelationship = document.getElementById("coApprelationship").value;
	if(scoApprelationship=="New"){
		var scoAppcustomerType1=document.getElementById("coAppcustomerType1").value;
		if(scoAppcustomerType1==""){
		alert("* Please Select Co-Applicant Customer Type");

		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(scoAppcustomerType1=="C"){
		if(document.getElementById("coAppcustPan").value=="")
		{
		alert("CoApplicant Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
}
	if(scoApprelationship=="Existing"){
	if(scoAppcustomerType1=="C"){
		if(document.getElementById("coAppcustPan").value=="")
		{
		alert("CoApplicant Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
	}
} 
 
if ($('#gaurStatus').is(":checked"))
{
	
	var sgaurrelationship = document.getElementById("gaurrelationship").value;
	
	if(sgaurrelationship=="New"){
			var sgaurcustomerType1=document.getElementById("gaurcustomerType1").value;
			if(sgaurcustomerType1==""){
			alert("* Please Select Guarantor Customer Type");

			DisButClass.prototype.EnbButMethod();
			return false;
			}
			if(document.getElementById("gaurcustomerType1").value=="C")
			{
			if(document.getElementById("gaurcustPan").value=="")
			{
			alert("Guarantor Pan No is required!");
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			}
	}
	if(sgaurrelationship=="Existing")
		{
		if(document.getElementById("gaurcustomerType1").value=="C")
		{
		if(document.getElementById("gaurcustPan").value=="")
		{
		alert("Guarantor Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
		}
}

		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCapturingBehindAction.do?method=saveNewLead&saveForward="+id+"&lbxCustomerId="+lbxCustomerId+"&appCustPanInd="+appCustPanInd+"&appAadhaarInd="+appAadhaarInd+"&customer=app";
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
	var customerName=document.getElementById("customerName");
	var addresstype=document.getElementById("addresstype1");
	var address1=document.getElementById("address1");
	var country=document.getElementById("country");
	var state=document.getElementById("state");
	var dist=document.getElementById("dist");
	var pincode=document.getElementById("pincode");
	var phoneOff=document.getElementById("phoneOff");
	var email=document.getElementById("email");
	var industry=document.getElementById("industry");
	var subIndustry=document.getElementById("subIndustry");
	var product=document.getElementById("product");
	var scheme=document.getElementById("scheme");
	var loanAmount=document.getElementById("loanAmount");
	var loanTenure=document.getElementById("loanTenure");
	var customerType=document.getElementById("customerType1");
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
	var custDOB=document.getElementById("custDOB");
	var lbxRelationship=document.getElementById("lbxRelationship").value;
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
    var ck_panno = /^[A-Za-z0-9]{10,11}$/;
    var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
    /*Sanjay Kushwaha added this 22032018*/
    var sourceList=document.getElementById("sourceList").value;
	var description=document.getElementById("description").value;
	/*Sanjay Kushwaha added this 22032018*/
	// pooja changes start for application form no
	var applicationFormNo=document.getElementById("applicationFormNoRm").value;
	if(applicationFormNo==''){
		alert("Application Form No is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(lbxRelationship==''){
		alert("Relationship Manager/Team Leader is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(addresstype.value==''){
		alert("Address Type is mandatory");
		document.getElementById("Save").removeAttribute("disabled", "true");
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	//pooja changes end for application form no
	var primaryPhoneNo=document.getElementById("phoneOff").value;
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Invalid Mobile No. ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	var lbxCustomerId= document.getElementById("lbxCustomerId").value;
	var lbxCoAppCustomerId="";
	var lbxGaurCustomerId="";
	if(document.getElementById("coApplbxCustomerId"))
	var lbxCoAppCustomerId=document.getElementById("coApplbxCustomerId").value;
	if(document.getElementById("gaurlbxCustomerId"))
	var lbxGaurCustomerId=document.getElementById("gaurlbxCustomerId").value;
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		if(customerType.value=="I"){
			if(!checkCurrentAge("custDOB")){
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			var genderIndividual=document.getElementById("genderIndividual").value;
			var passport=document.getElementById("passport").value;
			var aadhaar=document.getElementById("aadhaar").value;
			var appAadhaarInd=document.getElementById("aadhaar").value;
			var custPanInd=document.getElementById("custPanInd").value;
			var appCustPanInd=document.getElementById("custPanInd").value;
			var dlNumber=document.getElementById("dlNumber").value;
			var voterId=document.getElementById("voterId").value;
			//var appconSubprofile=document.getElementById("appconSubprofile").value;
			var indconstitution=document.getElementById("indconstitution").value;
			if(passport=="" && aadhaar=="" && custPanInd=="" && dlNumber=="" && voterId==""  ){
				alert("Please capture atleast one KYC");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			/*if(aadhaar==""){
				alert("Applicant Aadhar/UID No. is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}*/
			if(appCustPanInd==""){
				alert("Applicant Pan No. is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(genderIndividual==""){
				alert("gender is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(indconstitution=="")
			{
			alert("Applicant Constitution is required!");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
			}
			/*if(appconSubprofile=="")
				{
				alert("Applicant Constitution Sub Profile is required!");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
				}*/

			
		}
		if(customerType.value==""){
			alert("customer Type is required");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(relationship.value=="" || customerType.value=="" ||  
				address1.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
				 product.value=="" || scheme.value=="" || loanAmount.value=="" || 
				loanTenure.value=="" ||  sourceList=="" || description=="")
		{
			
			if(emailMandatoryFlag == 'Y')
			{
				var manFields = "relationship / customerType  / address1 /  state / dist / pincode_int / phoneOff_int  / noOfYears / product / scheme / loanAmount / loanTenure_int / loanPurpose / email / sourceList / description  ";
			}
			else
			{
				var manFields = "relationship / customerType  / address1 /  state / dist / pincode_int / phoneOff_int  / noOfYears / product / scheme / loanAmount / loanTenure_int / loanPurpose  / sourceList  / description  ";
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
			/*if(val=="RM" || val=="RO" && val!="" && document.getElementById("leadgencityrm").value == ""){
				manFields=manFields+"/leadgencityrm";
			}*/
			
		//end by sachin	
			
			if(! validateForm('leadCapturingDetails', manFields)){
			
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			 
			 /*Sanjay Kushwaha added this 22032018*/
			if((sourceList!='')&&(description=='')){
				alert("Description is required");
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			
			
        
			 /*Sanjay Kushwaha added this 22032018*/
		}
		
		
		if(customerType.value =='I'){
			if(firstName.value=="" || lastName.value=="" ||indconstitution.value=="" || fatherName.value=="" || custDOB.value=="")
			{
				var other = "firstName_varchar / lastName__varchar / indconstitution / fatherName / custDOB";
			
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
			if(document.getElementById("custPan").value=="")
				{
				alert("Applicant pan no is mandatory");
				document.getElementById("custPan").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
				}
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
			if(businessSegment.value==''){
				alert("Business Segment is mandatory");
				document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			}
			if( customerName.value==""  ||groupType.value==""  ||corconstitution.value==""){
			var other="	customerName/ groupType / corconstitution";
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
			 }
					
			}
		}
		var Apprelationshipvl=document.getElementById("relationship").value;
		if(Apprelationshipvl=="New"){
			var AppcustomerType1vl=document.getElementById("customerType1").value;
			if(AppcustomerType1vl==""){
				alert(" Please Select Applicant Customer Type");

				 DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
		
		if ($('#coAppStatus').is(":checked"))
		{
		
			
			var scoApprelationship = document.getElementById("coApprelationship").value;
			if(scoApprelationship=="New"){
				var scoAppcustomerType1=document.getElementById("coAppcustomerType1").value;
				if(scoAppcustomerType1==""){
				alert("* Please Select Co-Applicant Customer Type");

				DisButClass.prototype.EnbButMethod();
				return false;
				}
		}
		 
		} 
		 
		if ($('#gaurStatus').is(":checked"))
		{
			var sgaurrelationship = document.getElementById("gaurrelationship").value;
			
			if(sgaurrelationship=="New"){
					var sgaurcustomerType1=document.getElementById("gaurcustomerType1").value;
					if(sgaurcustomerType1==""){
					alert("* Please Select Guarantor Customer Type");

					DisButClass.prototype.EnbButMethod();
					return false;
					}
			 
			}
		
		}


		

		var indConSubprofile= document.getElementById("indconstitution").value;
		
		var coAppcustPan=document.getElementById("coAppcustPan").value;
		var gaurcustPan=document.getElementById("gaurcustPan").value;
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCapturingBehindAction.do?method=leadEntrySave&saveForward="+id+"&lbxCoAppCustomerId="+lbxCoAppCustomerId+"&lbxGaurCustomerId="+lbxGaurCustomerId+"&appCustPanInd="+appCustPanInd+"&appAadhaarInd="+appAadhaarInd+"&lbxCustomerId="+lbxCustomerId+"&coAppcustPan="+coAppcustPan+"&gaurcustPan="+gaurcustPan+"&indConSubprofile="+indConSubprofile;
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
	if (msg.match("addresstype1")) {
		errorMsg += "* Address Type is required \n";
	}
	if (msg.match("address1")) {
		errorMsg += "* Address Line1 is required \n";
	}
	if (msg.match("coAppaddress1")) {
		errorMsg += "* Address Line1 is required \n";
	}
	if (msg.match("gauraddress1")) {
		errorMsg += "* Address Line1 is required \n";
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
	 /*Sanjay Kushwaha added this 22032018*/
	if (msg.match("sourceList")) {
		errorMsg += "* Source Type Desc is required \n";
	}
	if (msg.match("description")) {
		errorMsg += "* Source Description is required \n";
	}
	
	
	
	 /*Sanjay Kushwaha added this 22032018*/
	if (msg.match("loanPurpose")) {
		errorMsg += "* Purpose is required \n";
	}
	
	if (msg.match("firstName")) {
		errorMsg += "* Applicant First Name  is required \n";
	}
	if (msg.match("coAppfirstName")) {
		errorMsg += "* Co-Applicant First Name  is required \n";
	}
	if (msg.match("gaurfirstName")) {
		errorMsg += "* Guarantor First Name  is required \n";
	}
	if (msg.match("lastName")) {
		errorMsg += "* Applicant Last Name  is required \n";
	}
	if (msg.match("coApplastName")) {
		errorMsg += "* Co-Applicant Last Name  is required \n";
	}
	if (msg.match("gaurlastName")) {
		errorMsg += "* Guarantor Last Name  is required \n";
	}
	if (msg.match("fatherName")) {
		errorMsg += "* Applicant Father's/Husband's Name  is required \n";
	}
	if (msg.match("coAppfatherName")) {
		errorMsg += "* Co-Applicant Father's/Husband's Name  is required \n";
	}
	if (msg.match("gaurfatherName")) {
		errorMsg += "* Guarantor Father's/Husband's Name  is required \n";
	}
	if (msg.match("custDOB")) {

		errorMsg += "* Customer Date of Birth is required \n";
	}
	
	if (msg.match("coAppcustDOB")) {

		errorMsg += "* Co-Applicant Date of Birth is required \n";
	}
	
	if (msg.match("gaurcustDOB")) {

		errorMsg += "* Guarantor Date of Birth is required \n";
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
	/*else if (msg.match("customerType")) {
		document.getElementById("customerType").focus();
	}*/
	
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
	/*else if (msg.match("address1")) {
		document.getElementById("address1").focus();
	}*/

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
	
	else if (msg.match("coAppcustDOB")) {
		document.getElementById("coAppcustDOB").focus();
	}
	
	else if (msg.match("gaurcustDOB")) {
		document.getElementById("gaurcustDOB").focus();
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
	 /*Sanjay Kushwaha added this 22032018*/
	else if (msg.match("sourceList")) {
		document.getElementById("leadButton1").focus();
	}
	else if (msg.match("description")) {
		document.getElementById("leadButton1").focus();
	}
	 /*Sanjay Kushwaha added this 22032018*/
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
	var dealNo=document.getElementById("dealNo").value;
	if(dealNo==''){
		alert("Please save first then forward" );
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
		if(document.getElementById("decision").value == "" ){
			var lt = "";
			if(document.getElementById("decision").value == "")
				lt += "* Decision is required \n";
			alert(lt);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	
	var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCapturingBehindAction.do?method=saveTrackingDetails";
		document.getElementById("leadCapturingDetails").submit();
		document.getElementById("processingImage").style.display = '';
		
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
			document.getElementById("leadCapturing").action=contextPath+"/preDealCapturingSearchBehind.do?userId="+name;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("leadCapturing").submit();
			return true;
		}
		if(customerName !='' && customerName.length >= 3)
		{
			document.getElementById("leadCapturing").action=contextPath+"/preDealCapturingSearchBehind.do?userId="+name;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("leadCapturing").submit();
			return true;
		}else if(customerName == '')
		{
			document.getElementById("leadCapturing").action=contextPath+"/preDealCapturingSearchBehind.do?userId="+name;
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
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCapturingBehindAction.do?method=deleteLead&leadId"+leadId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
}
	
	
}
function custtype(){
	var customerType=document.getElementById("customerType").value;
	var lbxCustomerId=document.getElementById("lbxCustomerId").value;
	var relationship=document.getElementById("relationship").value;
	
	if(relationship=='Existing'){


		if(customerType=='C'){
			document.getElementById("constitution").style.display='none';
			document.getElementById("individualfield").style.display='none';
			document.getElementById("corporatefield").style.display='';
			document.getElementById("Individualconstitution").style.display='none';
			document.getElementById("corporateconstitution").style.display='';
			document.getElementById("firstName").value="";
			document.getElementById("lastName").value="";
			document.getElementById("middleName").value="";
			document.getElementById("custDOB").value="";
			document.getElementById("customerType1").setAttribute("disabled","true");
			document.getElementById("customerName").setAttribute("readonly","true");
			document.getElementById("groupType").setAttribute("readonly","true");
			document.getElementById("groupName1").setAttribute("readonly","true");
			document.getElementById("registrationNo").setAttribute("readonly","true");
			document.getElementById("businessSegment1").setAttribute("disabled","true");
			document.getElementById("industry").setAttribute("readonly","true");
			document.getElementById("subIndustry").setAttribute("readonly","true");
			document.getElementById("custPan").setAttribute("readonly","true");
			document.getElementById("industryButton").setAttribute("disabled","true");
			document.getElementById("subIndustryButton").setAttribute("disabled","true");
			
			
			
		}else{
			
			document.getElementById("constitution").style.display='none';
			document.getElementById("corporatefield").style.display='none';
			document.getElementById("individualfield").style.display='';
			document.getElementById("Individualconstitution").style.display='';
			document.getElementById("corporateconstitution").style.display='none';
			document.getElementById("registrationNo").value="";
			document.getElementById("businessSegment").value="";
			document.getElementById("industry").value="";
			document.getElementById("subIndustry").value="";
			document.getElementById("lbxIndustry").value="";
			document.getElementById("lbxSubIndustry").value="";
		
			
					
			document.getElementById("customerType1").setAttribute("disabled","true");
			document.getElementById("firstName").setAttribute("readonly","true");
			document.getElementById("middleName").setAttribute("readonly","true");
			document.getElementById("lastName").setAttribute("readonly","true");
			document.getElementById("custDOB").setAttribute("readonly","true");
			document.getElementById("fatherName").setAttribute("readonly","true");
			
			document.getElementById("aadhaar").setAttribute("readonly","true");
			document.getElementById("passport").setAttribute("readonly","true");
			document.getElementById("dlNumber").setAttribute("readonly","true");
			document.getElementById("voterId").setAttribute("readonly","true");
			document.getElementById("custPanInd").setAttribute("readonly","true");

		}
		
		document.getElementById("addresstype1").setAttribute("disabled","true");
		document.getElementById("address1").setAttribute("readonly","true");
		document.getElementById("address2").setAttribute("readonly","true");
		document.getElementById("address3").setAttribute("readonly","true");
		document.getElementById("country").setAttribute("readonly","true");
		document.getElementById("state").setAttribute("readonly","true");
		document.getElementById("dist").setAttribute("readonly","true");
		document.getElementById("tahsilDesc").setAttribute("readonly","true");
		document.getElementById("pincode").setAttribute("readonly","true");
		document.getElementById("phoneOff").setAttribute("readonly","true");
		document.getElementById("email").setAttribute("readonly","true");
		document.getElementById("landmark").setAttribute("readonly","true");
		if(document.getElementById("countryButton")){
		document.getElementById("countryButton").setAttribute("disabled","true");
		document.getElementById("stateButton").setAttribute("disabled","true");
		document.getElementById("distButton").setAttribute("disabled","true");
		document.getElementById("tahsilButton").setAttribute("disabled","true");
		}
		
	}else{
		
		document.getElementById("addresstype1").removeAttribute("disabled","true");
		document.getElementById("address1").removeAttribute("readonly","true");
		document.getElementById("address2").removeAttribute("readonly","true");
		document.getElementById("address3").removeAttribute("readonly","true");
		document.getElementById("country").removeAttribute("readonly","true");
		document.getElementById("state").removeAttribute("readonly","true");
		document.getElementById("dist").removeAttribute("readonly","true");
		document.getElementById("tahsilDesc").removeAttribute("readonly","true");
		document.getElementById("pincode").removeAttribute("readonly","true");
		document.getElementById("phoneOff").removeAttribute("readonly","true");
		document.getElementById("email").removeAttribute("readonly","true");
		document.getElementById("landmark").removeAttribute("readonly","true");
		if(document.getElementById("countryButton")){
			document.getElementById("countryButton").removeAttribute("disabled","true");
			document.getElementById("stateButton").removeAttribute("disabled","true");
			document.getElementById("distButton").removeAttribute("disabled","true");
			document.getElementById("tahsilButton").removeAttribute("disabled","true");
			}
	if(customerType=='C'){
		document.getElementById("constitution").style.display='none';
		document.getElementById("individualfield").style.display='none';
		document.getElementById("corporatefield").style.display='';
		document.getElementById("Individualconstitution").style.display='none';
		document.getElementById("corporateconstitution").style.display='';
		document.getElementById("firstName").value="";
		document.getElementById("middleName").value="";
		document.getElementById("lastName").value="";
		document.getElementById("custDOB").value="";
		
		document.getElementById("customerType1").removeAttribute("disabled","true");
		document.getElementById("customerName").removeAttribute("readonly","true");
		document.getElementById("groupType").removeAttribute("readonly","true");
		document.getElementById("groupName1").removeAttribute("readonly","true");
		document.getElementById("registrationNo").removeAttribute("readonly","true");
		document.getElementById("businessSegment1").removeAttribute("disabled","true");
		document.getElementById("industry").removeAttribute("readonly","true");
		document.getElementById("subIndustry").removeAttribute("readonly","true");
		document.getElementById("custPan").removeAttribute("readonly","true");
		document.getElementById("industryButton").removeAttribute("disabled","true");
		document.getElementById("subIndustryButton").removeAttribute("disabled","true");
		//document.getElementById("appconSubprofile").value="";
		/*document.getElementById("contactPersonDesignation").style.display='';*/
	
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
		
		document.getElementById("customerType1").removeAttribute("disabled","true");
		document.getElementById("firstName").removeAttribute("readonly","true");
		document.getElementById("middleName").removeAttribute("readonly","true");
		document.getElementById("lastName").removeAttribute("readonly","true");
		document.getElementById("custDOB").removeAttribute("readonly","true");
		document.getElementById("fatherName").removeAttribute("readonly","true");
		
		//document.getElementById("aadhaar").removeAttribute("readonly","true");
		document.getElementById("passport").removeAttribute("readonly","true");
		document.getElementById("dlNumber").removeAttribute("readonly","true");
		document.getElementById("voterId").removeAttribute("readonly","true");
		document.getElementById("custPanInd").removeAttribute("readonly","true");
		
		//document.getElementById("hGroupId").value="";
		/*document.getElementById("contactPersonDesignation").style.display='none';*/

	}else{
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
		document.getElementById("customerType").value="I";
		document.getElementById("customerType1").removeAttribute("disabled","true");
		document.getElementById("firstName").removeAttribute("readonly","true");
		document.getElementById("lastName").removeAttribute("readonly","true");
		document.getElementById("middleName").removeAttribute("readonly","true");
		document.getElementById("custDOB").removeAttribute("readonly","true");
		document.getElementById("fatherName").removeAttribute("readonly","true");
		
		//document.getElementById("aadhaar").removeAttribute("readonly","true");
		document.getElementById("passport").removeAttribute("readonly","true");
		document.getElementById("dlNumber").removeAttribute("readonly","true");
		document.getElementById("voterId").removeAttribute("readonly","true");
		document.getElementById("custPanInd").removeAttribute("readonly","true");
		//document.getElementById("hGroupId").value="";
		/*document.getElementById("contactPersonDesignation").style.display='none';*/
	}
	}
}

function coAppcusttype(){
	
	var customerType=document.getElementById("coAppcustomerType").value;
	var coApplbxCustomerId=document.getElementById("coApplbxCustomerId").value;
	var coApprelationship=document.getElementById("coApprelationship").value;
	
	if(coApprelationship=='Existing'){


		if(customerType=='C'){
			document.getElementById("coAppconstitution").style.display='none';
			document.getElementById("coAppindividualfield").style.display='none';
			document.getElementById("coAppcorporatefield").style.display='';
			document.getElementById("coAppIndividualconstitution").style.display='none';
			document.getElementById("coAppcorporateconstitution").style.display='';
			document.getElementById("coAppfirstName").value="";
			document.getElementById("coAppmiddleName").value="";
			document.getElementById("coApplastName").value="";
			document.getElementById("coAppcustDOB").value="";
			document.getElementById("coApprelationship1").setAttribute("disabled","true");
			document.getElementById("coAppcustomerIdButton").setAttribute("disabled","true");
			document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			document.getElementById("coAppcorporateconstitution").setAttribute("disabled","true");
			document.getElementById("coAppcustomerName").setAttribute("disabled","true");
			document.getElementById("coAppgroupType").setAttribute("disabled","true");
			document.getElementById("coAppgroupName1").setAttribute("disabled","true");
			document.getElementById("coAppregistrationNo").setAttribute("disabled","true");
			document.getElementById("coAppbusinessSegment").setAttribute("disabled","true");
			document.getElementById("coAppindustry").setAttribute("readonly","true");
			document.getElementById("coAppsubIndustry").setAttribute("readonly","true");
			document.getElementById("industryButton").setAttribute("disabled","true");
			document.getElementById("subIndustryButton").setAttribute("disabled","true");
			document.getElementById("coAppcustPan").setAttribute("disabled","true");
			
			
			
		}else{
			
			document.getElementById("coAppconstitution").style.display='none';
			document.getElementById("coAppcorporatefield").style.display='none';
			document.getElementById("coAppindividualfield").style.display='';
			document.getElementById("coAppIndividualconstitution").style.display='';
			document.getElementById("coAppcorporateconstitution").style.display='none';
			document.getElementById("coAppregistrationNo").value="";
			document.getElementById("coAppbusinessSegment").value="";
			document.getElementById("coAppindustry").value="";
			document.getElementById("coAppsubIndustry").value="";
			document.getElementById("coApplbxIndustry").value="";
			document.getElementById("coApplbxSubIndustry").value="";
		
			document.getElementById("coApprelationship1").setAttribute("disabled","true");
			document.getElementById("coAppcustomerIdButton").setAttribute("disabled","true");		
			document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			document.getElementById("coAppindconstitution").setAttribute("disabled","true");
			document.getElementById("coAppfirstName").setAttribute("disabled","true");
			document.getElementById("coAppmiddleName").setAttribute("disabled","true");
			document.getElementById("coApplastName").setAttribute("disabled","true");
			document.getElementById("coAppcustDOB").setAttribute("disabled","true");
			document.getElementById("coAppfatherName").setAttribute("disabled","true");
			document.getElementById("coAppgenderIndividual").setAttribute("disabled","true");
			document.getElementById("coAppaadhaar").setAttribute("disabled","true");
			document.getElementById("coApppassport").setAttribute("disabled","true");
			document.getElementById("coAppdlNumber").setAttribute("disabled","true");
			document.getElementById("coAppvoterId").setAttribute("disabled","true");
			document.getElementById("coAppcustPanInd").setAttribute("disabled","true");

		}
		document.getElementById("copyAppCoapp").setAttribute("disabled","true");
		document.getElementById("coAppaddresstype").setAttribute("disabled","true");
		document.getElementById("coAppaddress1").setAttribute("disabled","true");
		document.getElementById("coAppaddress2").setAttribute("disabled","true");
		document.getElementById("coAppaddress3").setAttribute("disabled","true");
		document.getElementById("coAppcountry").setAttribute("disabled","true");
		document.getElementById("coAppstate").setAttribute("disabled","true");
		document.getElementById("coAppdist").setAttribute("disabled","true");
		document.getElementById("coApptahsilDesc").setAttribute("disabled","true");
		document.getElementById("coApppincode").setAttribute("disabled","true");
		document.getElementById("coAppphoneOff").setAttribute("disabled","true");
		document.getElementById("coAppemail").setAttribute("disabled","true");
		document.getElementById("coApplandmark").setAttribute("disabled","true");
		if(document.getElementById("countryButton")){
			document.getElementById("countryButton").setAttribute("disabled","true");
			document.getElementById("stateButton").setAttribute("disabled","true");
			document.getElementById("distButton").setAttribute("disabled","true");
			document.getElementById("tahsilButton").setAttribute("disabled","true");
			}
		
		
	}else{
		document.getElementById("copyAppCoapp").removeAttribute("disabled","true");
		if(document.getElementById("countryButton")){
			document.getElementById("countryButton").removeAttribute("disabled","true");
			document.getElementById("stateButton").removeAttribute("disabled","true");
			document.getElementById("distButton").removeAttribute("disabled","true");
			document.getElementById("tahsilButton").removeAttribute("disabled","true");
			}
	if(customerType=='C'){
		document.getElementById("coAppconstitution").style.display='none';
		document.getElementById("coAppindividualfield").style.display='none';
		document.getElementById("coAppcorporatefield").style.display='';
		document.getElementById("coAppIndividualconstitution").style.display='none';
		document.getElementById("coAppcorporateconstitution").style.display='';
		document.getElementById("coAppfirstName").value="";
		document.getElementById("coAppmiddleName").value="";
		document.getElementById("coApplastName").value="";
		document.getElementById("coAppcustDOB").value="";
		document.getElementById("coApprelationship1").removeAttribute("disabled","true");
		document.getElementById("coAppcustomerIdButton").removeAttribute("disabled","true");
		document.getElementById("coAppcustomerType1").removeAttribute("disabled","true");
		document.getElementById("coAppcorporateconstitution").removeAttribute("disabled","true");
		document.getElementById("coAppcustomerName").removeAttribute("disabled","true");
		document.getElementById("coAppgroupType").removeAttribute("disabled","true");
		document.getElementById("coAppgroupName1").removeAttribute("disabled","true");
		document.getElementById("coAppregistrationNo").removeAttribute("disabled","true");
		document.getElementById("coAppbusinessSegment").removeAttribute("disabled","true");
		document.getElementById("coAppindustry").removeAttribute("readonly","true");
		document.getElementById("coAppsubIndustry").removeAttribute("readonly","true");
		document.getElementById("industryButton").removeAttribute("disabled","true");
		document.getElementById("subIndustryButton").removeAttribute("disabled","true");
		document.getElementById("coAppcustPan").removeAttribute("disabled","true");
		
	}else if(customerType=='I'){
		
		document.getElementById("coAppconstitution").style.display='none';
		document.getElementById("coAppcorporatefield").style.display='none';
		document.getElementById("coAppindividualfield").style.display='';
		document.getElementById("coAppIndividualconstitution").style.display='';
		document.getElementById("coAppcorporateconstitution").style.display='none';
		document.getElementById("coAppregistrationNo").value="";
		document.getElementById("coAppbusinessSegment").value="";
		document.getElementById("coAppindustry").value="";
		document.getElementById("coAppsubIndustry").value="";
		document.getElementById("coApplbxIndustry").value="";
		document.getElementById("coApplbxSubIndustry").value="";
	
				document.getElementById("coApprelationship1").removeAttribute("disabled","true");
				document.getElementById("coAppcustomerIdButton").removeAttribute("disabled","true");
				document.getElementById("coApprelationship1").removeAttribute("disabled","true");
				document.getElementById("coAppcustomerIdButton").removeAttribute("disabled","true");		
				document.getElementById("coAppcustomerType1").removeAttribute("disabled","true");
				document.getElementById("coAppindconstitution").removeAttribute("disabled","true");
				document.getElementById("coAppfirstName").removeAttribute("disabled","true");
				document.getElementById("coAppmiddleName").removeAttribute("disabled","true");
				document.getElementById("coApplastName").removeAttribute("disabled","true");
				document.getElementById("coAppcustDOB").removeAttribute("disabled","true");
				document.getElementById("coAppfatherName").removeAttribute("disabled","true");
				document.getElementById("coAppgenderIndividual").removeAttribute("disabled","true");
				//document.getElementById("coAppaadhaar").removeAttribute("disabled","true");
				document.getElementById("coApppassport").removeAttribute("disabled","true");
				document.getElementById("coAppdlNumber").removeAttribute("disabled","true");
				document.getElementById("coAppvoterId").removeAttribute("disabled","true");
				document.getElementById("coAppcustPanInd").removeAttribute("disabled","true");
				
				

	}else{
		
		document.getElementById("coAppconstitution").style.display='none';
		document.getElementById("coAppcorporatefield").style.display='none';
		document.getElementById("coAppindividualfield").style.display='';
		document.getElementById("coAppIndividualconstitution").style.display='';
		document.getElementById("coAppcorporateconstitution").style.display='none';
		document.getElementById("coAppregistrationNo").value="";
		document.getElementById("coAppbusinessSegment").value="";
		document.getElementById("coAppindustry").value="";
		document.getElementById("coAppsubIndustry").value="";
		document.getElementById("coApplbxIndustry").value="";
		document.getElementById("coApplbxSubIndustry").value="";
		document.getElementById("coAppcustomerType").value="I";
		
				document.getElementById("coApprelationship").removeAttribute("disabled","true");
		
				document.getElementById("coAppcustomerIdButton").removeAttribute("disabled","true");
				
				document.getElementById("coAppcustomerType1").removeAttribute("disabled","true");
				document.getElementById("coAppindconstitution").removeAttribute("disabled","true");
				document.getElementById("coAppfirstName").removeAttribute("disabled","true");
				document.getElementById("coAppmiddleName").removeAttribute("disabled","true");
				document.getElementById("coApplastName").removeAttribute("disabled","true");
				document.getElementById("coAppcustDOB").removeAttribute("disabled","true");
				document.getElementById("coAppfatherName").removeAttribute("disabled","true");
				document.getElementById("coAppcustDOB").removeAttribute("disabled","true");
				document.getElementById("coAppfatherName").removeAttribute("disabled","true");
				document.getElementById("coAppgenderIndividual").removeAttribute("disabled","true");
			//	document.getElementById("coAppaadhaar").removeAttribute("disabled","true");
				document.getElementById("coApppassport").removeAttribute("disabled","true");
				document.getElementById("coAppdlNumber").removeAttribute("disabled","true");
				document.getElementById("coAppvoterId").removeAttribute("disabled","true");
				document.getElementById("coAppcustPanInd").removeAttribute("disabled","true");
				document.getElementById("coAppaddresstype").removeAttribute("disabled","true");
				document.getElementById("coAppaddress1").removeAttribute("disabled","true");
				document.getElementById("coAppaddress2").removeAttribute("disabled","true");
				document.getElementById("coAppaddress3").removeAttribute("disabled","true");
				document.getElementById("coAppcountry").removeAttribute("disabled","true");
				document.getElementById("coAppstate").removeAttribute("disabled","true");
				document.getElementById("coAppdist").removeAttribute("disabled","true");
				document.getElementById("coApptahsilDesc").removeAttribute("disabled","true");
				document.getElementById("coApppincode").removeAttribute("disabled","true");
				document.getElementById("coAppphoneOff").removeAttribute("disabled","true");
				document.getElementById("coAppemail").removeAttribute("disabled","true");
				document.getElementById("coApplandmark").removeAttribute("disabled","true");
		
	}
	document.getElementById("coAppaddresstype").removeAttribute("disabled","true");
	document.getElementById("coAppaddress1").removeAttribute("disabled","true");
	document.getElementById("coAppaddress2").removeAttribute("disabled","true");
	document.getElementById("coAppaddress3").removeAttribute("disabled","true");
	document.getElementById("coAppcountry").removeAttribute("disabled","true");
	document.getElementById("coAppstate").removeAttribute("disabled","true");
	document.getElementById("coAppdist").removeAttribute("disabled","true");
	document.getElementById("coApptahsilDesc").removeAttribute("disabled","true");
	document.getElementById("coApppincode").removeAttribute("disabled","true");
	document.getElementById("coAppphoneOff").removeAttribute("disabled","true");
	document.getElementById("coAppemail").removeAttribute("disabled","true");
	document.getElementById("coApplandmark").removeAttribute("disabled","true");
}
}
function gaurcusttype(){
	
	
	var customerType=document.getElementById("gaurcustomerType").value;
	var gaurlbxCustomerId=document.getElementById("gaurlbxCustomerId").value;
	var gaurrelationship=document.getElementById("gaurrelationship").value;
	
	if(gaurrelationship=='Existing'){


		if(customerType=='C'){
			document.getElementById("gaurconstitution").style.display='none';
			document.getElementById("gaurindividualfield").style.display='none';
			document.getElementById("gaurcorporatefield").style.display='';
			document.getElementById("gaurIndividualconstitution").style.display='none';
			document.getElementById("gaurcorporateconstitution").style.display='';
			document.getElementById("gaurfirstName").value="";
			document.getElementById("gaurmiddleName").value="";
			document.getElementById("gaurlastName").value="";
			document.getElementById("gaurcustDOB").value="";
			document.getElementById("gaurrelationship1").setAttribute("disabled","true");
			document.getElementById("gaurcustomerIdButton").setAttribute("disabled","true");
			document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			document.getElementById("gaurcorporateconstitution").setAttribute("disabled","true");
			document.getElementById("gaurcustomerName").setAttribute("disabled","true");
			document.getElementById("gaurgroupType").setAttribute("disabled","true");
			document.getElementById("gaurgroupName1").setAttribute("disabled","true");
			document.getElementById("gaurregistrationNo").setAttribute("disabled","true");
			document.getElementById("gaurbusinessSegment").setAttribute("disabled","true");
			document.getElementById("gaurindustry").setAttribute("disabled","true");
			document.getElementById("gaursubIndustry").setAttribute("disabled","true");
			document.getElementById("gaurcustPan").setAttribute("disabled","true");
			
			
			
		}else{
			
			document.getElementById("gaurconstitution").style.display='none';
			document.getElementById("gaurcorporatefield").style.display='none';
			document.getElementById("gaurindividualfield").style.display='';
			document.getElementById("gaurIndividualconstitution").style.display='';
			document.getElementById("gaurcorporateconstitution").style.display='none';
			document.getElementById("gaurregistrationNo").value="";
			document.getElementById("gaurbusinessSegment").value="";
			document.getElementById("gaurindustry").value="";
			document.getElementById("gaursubIndustry").value="";
			document.getElementById("gaurlbxIndustry").value="";
			document.getElementById("gaurlbxSubIndustry").value="";
		
			document.getElementById("gaurrelationship1").setAttribute("disabled","true");
			document.getElementById("gaurcustomerIdButton").setAttribute("disabled","true");		
			document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			document.getElementById("gaurindconstitution").setAttribute("disabled","true");
			document.getElementById("gaurfirstName").setAttribute("disabled","true");
			document.getElementById("gaurmiddleName").setAttribute("disabled","true");
			document.getElementById("gaurlastName").setAttribute("disabled","true");
			document.getElementById("gaurcustDOB").setAttribute("disabled","true");
			document.getElementById("gaurfatherName").setAttribute("disabled","true");
			document.getElementById("gaurgenderIndividual").setAttribute("disabled","true");
			document.getElementById("gauraadhaar").setAttribute("disabled","true");
			document.getElementById("gaurpassport").setAttribute("disabled","true");
			document.getElementById("gaurdlNumber").setAttribute("disabled","true");
			document.getElementById("gaurvoterId").setAttribute("disabled","true");
			document.getElementById("gaurcustPanInd").setAttribute("disabled","true");

		}
		document.getElementById("copyAppGaur").setAttribute("disabled","true");
		document.getElementById("gauraddresstype").setAttribute("disabled","true");
		document.getElementById("gauraddress1").setAttribute("disabled","true");
		document.getElementById("gauraddress2").setAttribute("disabled","true");
		document.getElementById("gauraddress3").setAttribute("disabled","true");
		document.getElementById("gaurcountry").setAttribute("disabled","true");
		document.getElementById("gaurstate").setAttribute("disabled","true");
		document.getElementById("gaurdist").setAttribute("disabled","true");
		document.getElementById("gaurtahsilDesc").setAttribute("disabled","true");
		document.getElementById("gaurpincode").setAttribute("disabled","true");
		document.getElementById("gaurphoneOff").setAttribute("disabled","true");
		document.getElementById("gauremail").setAttribute("disabled","true");
		document.getElementById("gaurlandmark").setAttribute("disabled","true");
		if(document.getElementById("countryButton")){
			document.getElementById("countryButton").setAttribute("disabled","true");
			document.getElementById("stateButton").setAttribute("disabled","true");
			document.getElementById("distButton").setAttribute("disabled","true");
			document.getElementById("tahsilButton").setAttribute("disabled","true");
			}
		
		
	}else{
		document.getElementById("copyAppGaur").removeAttribute("disabled","true");
		if(document.getElementById("countryButton")){
			document.getElementById("countryButton").removeAttribute("disabled","true");
			document.getElementById("stateButton").removeAttribute("disabled","true");
			document.getElementById("distButton").removeAttribute("disabled","true");
			document.getElementById("tahsilButton").removeAttribute("disabled","true");
			}
if(customerType=='C'){
	
	document.getElementById("gaurconstitution").style.display='none';
	document.getElementById("gaurindividualfield").style.display='none';
	document.getElementById("gaurcorporatefield").style.display='';
	document.getElementById("gaurIndividualconstitution").style.display='none';
	document.getElementById("gaurcorporateconstitution").style.display='';
	document.getElementById("gaurfirstName").value="";
	document.getElementById("gaurmiddleName").value="";
	document.getElementById("gaurlastName").value="";
	document.getElementById("gaurcustDOB").value="";
	document.getElementById("gaurrelationship1").removeAttribute("disabled","true");
	if(document.getElementById("gaurcustomerIdButton"))
	document.getElementById("gaurcustomerIdButton").removeAttribute("disabled","true");
	
	document.getElementById("gaurcustomerType1").removeAttribute("disabled","true");
	
	
	document.getElementById("gaurcorporateconstitution").removeAttribute("disabled","true");
	document.getElementById("gaurcustomerName").removeAttribute("disabled","true");
	document.getElementById("gaurgroupType").removeAttribute("disabled","true");
	document.getElementById("gaurgroupName1").removeAttribute("disabled","true");
	document.getElementById("gaurregistrationNo").removeAttribute("disabled","true");
	document.getElementById("gaurbusinessSegment").removeAttribute("disabled","true");
	document.getElementById("gaurindustry").removeAttribute("disabled","true");
	document.getElementById("gaursubIndustry").removeAttribute("disabled","true");
	document.getElementById("gaurcustPan").removeAttribute("disabled","true");

}else if(customerType=='I'){
	
	document.getElementById("gaurconstitution").style.display='none';
	document.getElementById("gaurcorporatefield").style.display='none';
	document.getElementById("gaurindividualfield").style.display='';
	document.getElementById("gaurIndividualconstitution").style.display='';
	document.getElementById("gaurcorporateconstitution").style.display='none';
	document.getElementById("gaurregistrationNo").value="";
	document.getElementById("gaurbusinessSegment").value="";
	document.getElementById("gaurindustry").value="";
	document.getElementById("gaursubIndustry").value="";
	document.getElementById("gaurlbxIndustry").value="";
	document.getElementById("gaurlbxSubIndustry").value="";
	
			document.getElementById("gaurrelationship1").removeAttribute("disabled","true");
			if(document.getElementById("gaurcustomerIdButton"))
			document.getElementById("gaurcustomerIdButton").removeAttribute("disabled","true");
			
					
			document.getElementById("gaurcustomerType1").removeAttribute("disabled","true");
			document.getElementById("gaurindconstitution").removeAttribute("disabled","true");
			document.getElementById("gaurfirstName").removeAttribute("disabled","true");
			document.getElementById("gaurmiddleName").removeAttribute("disabled","true");
			document.getElementById("gaurlastName").removeAttribute("disabled","true");
			document.getElementById("gaurcustDOB").removeAttribute("disabled","true");
			document.getElementById("gaurfatherName").removeAttribute("disabled","true");
			document.getElementById("gaurgenderIndividual").removeAttribute("disabled","true");
			//document.getElementById("gauraadhaar").removeAttribute("disabled","true");
			document.getElementById("gaurpassport").removeAttribute("disabled","true");
			document.getElementById("gaurdlNumber").removeAttribute("disabled","true");
			document.getElementById("gaurvoterId").removeAttribute("disabled","true");
			document.getElementById("gaurcustPanInd").removeAttribute("disabled","true");
			
			


}else{

	document.getElementById("gaurconstitution").style.display='none';
	document.getElementById("gaurcorporatefield").style.display='none';
	document.getElementById("gaurindividualfield").style.display='';
	document.getElementById("gaurIndividualconstitution").style.display='';
	document.getElementById("gaurcorporateconstitution").style.display='none';
	document.getElementById("gaurregistrationNo").value="";
	document.getElementById("gaurbusinessSegment").value="";
	document.getElementById("gaurindustry").value="";
	document.getElementById("gaursubIndustry").value="";
	document.getElementById("gaurlbxIndustry").value="";
	document.getElementById("gaurlbxSubIndustry").value="";
	document.getElementById("gaurcustomerType").value="I";
			document.getElementById("gaurrelationship1").removeAttribute("disabled","true");

			document.getElementById("gaurcustomerIdButton").removeAttribute("disabled","true");
			
			document.getElementById("gaurcustomerType1").removeAttribute("disabled","true");
			document.getElementById("gaurindconstitution").removeAttribute("disabled","true");
			document.getElementById("gaurfirstName").removeAttribute("disabled","true");
			document.getElementById("gaurmiddleName").removeAttribute("disabled","true");
			document.getElementById("gaurlastName").removeAttribute("disabled","true");
			document.getElementById("gaurcustDOB").removeAttribute("disabled","true");
			document.getElementById("gaurfatherName").removeAttribute("disabled","true");
			document.getElementById("gaurgenderIndividual").removeAttribute("disabled","true");
			//document.getElementById("gauraadhaar").removeAttribute("disabled","true");
			document.getElementById("gaurpassport").removeAttribute("disabled","true");
			document.getElementById("gaurdlNumber").removeAttribute("disabled","true");
			document.getElementById("gaurvoterId").removeAttribute("disabled","true");
			document.getElementById("gaurcustPanInd").removeAttribute("disabled","true");
			document.getElementById("gauraddresstype").removeAttribute("disabled","true");
			document.getElementById("gauraddress1").removeAttribute("disabled","true");
			document.getElementById("gauraddress2").removeAttribute("disabled","true");
			document.getElementById("gauraddress3").removeAttribute("disabled","true");
			document.getElementById("gaurcountry").removeAttribute("disabled","true");
			document.getElementById("gaurstate").removeAttribute("disabled","true");
			document.getElementById("gaurdist").removeAttribute("disabled","true");
			document.getElementById("gaurtahsilDesc").removeAttribute("disabled","true");
			document.getElementById("gaurpincode").removeAttribute("disabled","true");
			document.getElementById("gaurphoneOff").removeAttribute("disabled","true");
			document.getElementById("gauremail").removeAttribute("disabled","true");
			document.getElementById("gaurlandmark").removeAttribute("disabled","true");

}
document.getElementById("gauraddresstype").removeAttribute("disabled","true");
document.getElementById("gauraddress1").removeAttribute("disabled","true");
document.getElementById("gauraddress2").removeAttribute("disabled","true");
document.getElementById("gauraddress3").removeAttribute("disabled","true");
document.getElementById("gaurcountry").removeAttribute("disabled","true");
document.getElementById("gaurstate").removeAttribute("disabled","true");
document.getElementById("gaurdist").removeAttribute("disabled","true");
document.getElementById("gaurtahsilDesc").removeAttribute("disabled","true");
document.getElementById("gaurpincode").removeAttribute("disabled","true");
document.getElementById("gaurphoneOff").removeAttribute("disabled","true");
document.getElementById("gauremail").removeAttribute("disabled","true");
document.getElementById("gaurlandmark").removeAttribute("disabled","true");
	}
}

function groupselect(){
	var groupType=document.getElementById("groupType").value;
	if(groupType=='E'){
		document.getElementById("groupLov").style.display="";
		document.getElementById("groupText").style.display="none";	
		document.getElementById("groupName1").setAttribute("disabled", true);
		document.getElementById("groupName").removeAttribute("disabled");
		document.getElementById("groupName").value="";
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

function coAppgroupselect(){
	var groupType=document.getElementById("coAppgroupType").value;
	if(groupType=='E'){
		document.getElementById("coAppgroupLov").style.display="";
		document.getElementById("coAppgroupText").style.display="none";	
		document.getElementById("coAppgroupName1").setAttribute("disabled", true);
		document.getElementById("coAppgroupName").removeAttribute("disabled");
		document.getElementById("coAppgroupName").value="";
		document.getElementById("coApphGroupId").removeAttribute("disabled");
	}else if(groupType=='N'){
		document.getElementById("coAppgroupText").style.display="";
		document.getElementById("coAppgroupLov").style.display="none";
		document.getElementById("coAppgroupName1").removeAttribute("disabled", true);
		document.getElementById("coAppgroupName").setAttribute("disabled", true);
		document.getElementById("coApphGroupId").setAttribute("disabled", true);
		return true;
	}else{
		document.getElementById("coAppgroupText").style.display="none";
		document.getElementById("coAppgroupLov").style.display="none";
	}
}
function gaurgroupselect(){
	var groupType=document.getElementById("gaurgroupType").value;
	if(groupType=='E'){
		document.getElementById("gaurgroupLov").style.display="";
		document.getElementById("gaurgroupText").style.display="none";	
		document.getElementById("gaurgroupName1").setAttribute("disabled", true);
		document.getElementById("gaurgroupName").removeAttribute("disabled");
		document.getElementById("gaurgroupName").value="";
		document.getElementById("gaurhGroupId").removeAttribute("disabled");
	}else if(groupType=='N'){
		document.getElementById("gaurgroupText").style.display="";
		document.getElementById("gaurgroupLov").style.display="none";
		document.getElementById("gaurgroupName1").removeAttribute("disabled", true);
		document.getElementById("gaurgroupName").setAttribute("disabled", true);
		document.getElementById("gaurhGroupId").setAttribute("disabled", true);
		return true;
	}else{
		document.getElementById("gaurgroupText").style.display="none";
		document.getElementById("gaurgroupLov").style.display="none";
	}
}

function updateCust(val){
		document.getElementById("customerType").value = val;
	}
function coAppupdateCust(val){
	document.getElementById("coAppcustomerType").value = val;
}
function gaurupdateCust(val){
	document.getElementById("gaurcustomerType").value = val;
}
	function cleanUp(){
		//CLEAN UP CODE START
			document.getElementById("lbxCustomerId").value = '';
			document.getElementById("addressId").value = '';
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
     		document.getElementById("address3").value = '';
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
			document.getElementById("fatherName").value = '';
			document.getElementById("aadhaar").value = '';
			//CLEAN UP CODE END 
	}
	function coAppcleanUp(){
		//CLEAN UP CODE START
			document.getElementById("coApplbxCustomerId").value = '';
			document.getElementById("coAppaddressId").value = '';
			document.getElementById("coAppcustomerName").value = '';
			if(document.getElementById("coApphGroupId"))
     		document.getElementById("coApphGroupId").value = '';
     		document.getElementById("coAppregistrationNo").value = '';
     		document.getElementById("coAppcustPan").value = '';
     		document.getElementById("coAppcustPanInd").value = '';
     		document.getElementById("coApplbxIndustry").value = '';
     		document.getElementById("coApplbxSubIndustry").value = '';
     		document.getElementById("coAppcustomerType").value = '';
     		document.getElementById("coAppfirstName").value = '';
     		document.getElementById("coApplastName").value = '';
     		document.getElementById("coAppcustDOB").value = '';
     	 	document.getElementById("coAppaddress1").value = '';
     		document.getElementById("coAppaddress2").value = '';
     		document.getElementById("coAppaddresstype").value = '';
     		document.getElementById("coAppaddress3").value = '';
        	document.getElementById("coApppincode").value = '';
		    document.getElementById("coApptxtCountryCode").value = '';
		    document.getElementById("coApptxtStateCode").value = '';
		    document.getElementById("coApptxtDistCode").value = '';
		    document.getElementById("coAppemail").value = '';
			document.getElementById("coAppgroupName").value = '';
			document.getElementById("coAppdist").value = '';
			document.getElementById("coAppstate").value = '';
			document.getElementById("coAppindustry").value = '';
			document.getElementById("coAppsubIndustry").value = '';
			document.getElementById("coAppphoneOff").value = '';
			//CLEAN UP CODE END 
	}
	function gaurcleanUp(){
		//CLEAN UP CODE START
			document.getElementById("gauraddressId").value = '';
			document.getElementById("gaurlbxCustomerId").value = '';
			document.getElementById("gaurcustomerName").value = '';
     		document.getElementById("gaurhGroupId").value = '';
     		document.getElementById("gaurregistrationNo").value = '';
     		document.getElementById("gaurcustPan").value = '';
     		document.getElementById("gaurcustPanInd").value = '';
     		document.getElementById("gaurlbxIndustry").value = '';
     		document.getElementById("gaurlbxSubIndustry").value = '';
     		document.getElementById("gaurcustomerType").value = '';
     		document.getElementById("gaurfirstName").value = '';
     		document.getElementById("gaurlastName").value = '';
     		document.getElementById("gaurcustDOB").value = '';
     	 	document.getElementById("gauraddress1").value = '';
     		document.getElementById("gauraddress2").value = '';
     		document.getElementById("gauraddresstype").value = '';
     		document.getElementById("gauraddress3").value = '';
        	document.getElementById("gaurpincode").value = '';
		    document.getElementById("gaurtxtCountryCode").value = '';
		    document.getElementById("gaurtxtStateCode").value = '';
		    document.getElementById("gaurtxtDistCode").value = '';
		    document.getElementById("gauremail").value = '';
			document.getElementById("gaurgroupName").value = '';
			document.getElementById("gaurdist").value = '';
			document.getElementById("gaurstate").value = '';
			document.getElementById("gaurindustry").value = '';
			document.getElementById("gaursubIndustry").value = '';
			document.getElementById("gaurphoneOff").value = '';
			document.getElementById("gaurfatherName").value = '';
			document.getElementById("gauraadhaar").value = '';
			if(document.getElementById("gaurrelationship").value=="Existing"){
				document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			}
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
	function coAppcheckcustDOB(custDOB)
	{
		var dobDate=document.getElementById("coAppcustDOB").value;
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(curDate,formatD.substring(2,3));
		var dt3=getDateObject(dobDate,formatD.substring(2,3));
		if(dt1<=dt3)
				{
					alert("Date of Birth should be less than Business date.");
					document.getElementById("coAppcustDOB").value='';
					return false;
				}
	}
	function gaurcheckcustDOB(custDOB)
	{
		var dobDate=document.getElementById("gaurcustDOB").value;
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(curDate,formatD.substring(2,3));
		var dt3=getDateObject(dobDate,formatD.substring(2,3));
		if(dt1<=dt3)
				{
					alert("Date of Birth should be less than Business date.");
					document.getElementById("gaurcustDOB").value='';
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
	
	function clearCountryLovChild()
	{
		//alert("clearCountryLovChild tahsilDesc");
		window.opener.document.getElementById("state").value="";
	 	window.opener.document.getElementById("txtStateCode").value="";
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";	
	 	window.opener.document.getElementById("tahsil").value="";
	 	window.opener.document.getElementById("tahsilDesc").value="";
	 	window.close();
	
	}
	function coAppclearCountryLovChild()
	{
		//alert("clearCountryLovChild tahsilDesc");
		window.opener.document.getElementById("coAppstate").value="";
	 	window.opener.document.getElementById("coApptxtStateCode").value="";
		window.opener.document.getElementById("coAppdist").value="";
	 	window.opener.document.getElementById("coApptxtDistCode").value="";	
	 	window.opener.document.getElementById("coApptahsil").value="";
	 	window.opener.document.getElementById("coApptahsilDesc").value="";
	 	window.close();
	
	}
	function gaurclearCountryLovChild()
	{
		//alert("clearCountryLovChild tahsilDesc");
		window.opener.document.getElementById("gaurstate").value="";
	 	window.opener.document.getElementById("gaurtxtStateCode").value="";
		window.opener.document.getElementById("gaurdist").value="";
	 	window.opener.document.getElementById("gaurtxtDistCode").value="";	
	 	window.opener.document.getElementById("gaurtahsil").value="";
	 	window.opener.document.getElementById("gaurtahsilDesc").value="";
	 	window.close();
	
	}
	function clearStateLovChild(){
		//alert("clearStateLovChild");
		
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";	 	
	 	window.opener.document.getElementById("tahsil").value="";
	 	window.opener.document.getElementById("tahsilDesc").value="";
	 	window.close();
	
	}
	function coAppclearStateLovChild(){
		//alert("clearStateLovChild");
		
		window.opener.document.getElementById("coAppdist").value="";
	 	window.opener.document.getElementById("coApptxtDistCode").value="";	 	
	 	window.opener.document.getElementById("coApptahsil").value="";
	 	window.opener.document.getElementById("coApptahsilDesc").value="";
	 	window.close();
	
	}
	function gaurclearStateLovChild(){
		//alert("clearStateLovChild");
		
		window.opener.document.getElementById("gaurdist").value="";
	 	window.opener.document.getElementById("gaurtxtDistCode").value="";	 	
	 	window.opener.document.getElementById("gaurtahsil").value="";
	 	window.opener.document.getElementById("gaurtahsilDesc").value="";
	 	window.close();
	
	}
	
	function clearDistrictLovChild(){
		//alert("clearDistrictLovChild");
	 	window.opener.document.getElementById("tahsil").value="";
	 	window.opener.document.getElementById("tahsilDesc").value="";
	 	window.close();

	}
	function coAppclearDistrictLovChild(){
		//alert("clearDistrictLovChild");
	 	window.opener.document.getElementById("coApptahsil").value="";
	 	window.opener.document.getElementById("coApptahsilDesc").value="";
	 	window.close();

	}
	function gaurclearDistrictLovChild(){
		//alert("clearDistrictLovChild");
	 	window.opener.document.getElementById("gaurtahsil").value="";
	 	window.opener.document.getElementById("gaurtahsilDesc").value="";
	 	window.close();

	}
	function gaurEnable(){
		var gaurStatus= document.getElementById("gaurStatus").checked;
		
		if(gaurStatus==false){
			
			document.getElementById("gaurrelationship").setAttribute("disabled","true");
			if(document.getElementById("gaurcustomerIdButton"))
			/*document.getElementById("gaur").style.display="";
			document.getElementById("gaur1").style.display="none";*/
			document.getElementById("gaurcustomerIdButton").setAttribute("disabled","true");
			document.getElementById("gaurcustomerIdButton").style.display="none"
			document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			document.getElementById("gaurindconstitution").setAttribute("disabled","true");
			document.getElementById("gaurfirstName").setAttribute("disabled","true");
			document.getElementById("gaurlastName").setAttribute("disabled","true");
			document.getElementById("gaurcustDOB").setAttribute("disabled","true");
			document.getElementById("gaurfatherName").setAttribute("disabled","true");
			document.getElementById("gaurpassport").setAttribute("disabled","true");
			document.getElementById("gaurdlNumber").setAttribute("disabled","true");
			document.getElementById("gaurvoterId").setAttribute("disabled","true");
			document.getElementById("gaurcustPanInd").setAttribute("disabled","true");
			document.getElementById("gauraddresstype").setAttribute("disabled","true");
			document.getElementById("gauraddress1").setAttribute("disabled","true");
			document.getElementById("gauraddress2").setAttribute("disabled","true");
			document.getElementById("gauraddress3").setAttribute("disabled","true");
			document.getElementById("gaurcountry").setAttribute("disabled","true");
			document.getElementById("gaurstate").setAttribute("disabled","true");
			document.getElementById("gaurdist").setAttribute("disabled","true");
			document.getElementById("gaurtahsilDesc").setAttribute("disabled","true");
			document.getElementById("gaurpincode").setAttribute("disabled","true");
			document.getElementById("gaurphoneOff").setAttribute("disabled","true");
			document.getElementById("gauremail").setAttribute("disabled","true");
			document.getElementById("gaurlandmark").setAttribute("disabled","true");
			document.getElementById("gaurgenderIndividual").setAttribute("disabled","true");
			document.getElementById("gauraadhaar").setAttribute("disabled","true");
			//document.getElementById("guarconSubprofile").setAttribute("disabled","true");
			document.getElementById("gaurrelationship").value="New";
			document.getElementById("gaurcustomerIdButton").setAttribute("disabled","true");
			document.getElementById("gaurcustomerType1").value="I";
			document.getElementById("gaurindconstitution").value="";
			document.getElementById("gaurfirstName").value="";
			document.getElementById("gaurlastName").value="";
			document.getElementById("gaurcustDOB").value="";
			document.getElementById("gaurfatherName").value="";
			document.getElementById("gaurpassport").value="";
			document.getElementById("gaurdlNumber").value="";
			document.getElementById("gaurvoterId").value="";
			document.getElementById("gaurcustPanInd").value="";
			document.getElementById("gauraddresstype").value="";
			document.getElementById("gauraddress1").value="";
			document.getElementById("gauraddress2").value="";
			document.getElementById("gauraddress3").value="";
			document.getElementById("gaurcountry").value="";
			document.getElementById("gaurstate").value="";
			document.getElementById("gaurdist").value="";
			document.getElementById("gaurtahsilDesc").value="";
			document.getElementById("gaurpincode").value="";
			document.getElementById("gaurphoneOff").value="";
			document.getElementById("gauremail").value="";
			document.getElementById("gaurlandmark").value="";
			document.getElementById("gaurgenderIndividual").value="";
			document.getElementById("gauraadhaar").value="";
			}else{
				
				document.getElementById("gaurrelationship").removeAttribute("disabled","true");
			//	document.getElementById("gaur").style.display="none";
			//	document.getElementById("gaur1").style.display="";
				//if(document.getElementById("gaurcustomerIdButton"))
				document.getElementById("gaurcustomerIdButton").removeAttribute("disabled","true");
				document.getElementById("gaurcustomerType1").removeAttribute("disabled","true");
				document.getElementById("gaurindconstitution").removeAttribute("disabled","true");
				document.getElementById("gaurfirstName").removeAttribute("disabled","true");
				document.getElementById("gaurlastName").removeAttribute("disabled","true");
				document.getElementById("gaurcustDOB").removeAttribute("disabled","true");
				document.getElementById("gaurfatherName").removeAttribute("disabled","true");
				document.getElementById("gaurpassport").removeAttribute("disabled","true");
				document.getElementById("gaurdlNumber").removeAttribute("disabled","true");
				document.getElementById("gaurvoterId").removeAttribute("disabled","true");
				document.getElementById("gaurcustPanInd").removeAttribute("disabled","true");
				document.getElementById("gauraddresstype").removeAttribute("disabled","true");
				document.getElementById("gauraddress1").removeAttribute("disabled","true");
				document.getElementById("gauraddress2").removeAttribute("disabled","true");
				document.getElementById("gauraddress3").removeAttribute("disabled","true");
				document.getElementById("gaurcountry").removeAttribute("disabled","true");
				document.getElementById("gaurstate").removeAttribute("disabled","true");
				document.getElementById("gaurdist").removeAttribute("disabled","true");
				document.getElementById("gaurtahsilDesc").removeAttribute("disabled","true");
				document.getElementById("gaurpincode").removeAttribute("disabled","true");
				document.getElementById("gaurphoneOff").removeAttribute("disabled","true");
				document.getElementById("gauremail").removeAttribute("disabled","true");
				document.getElementById("gaurlandmark").removeAttribute("disabled","true");
				document.getElementById("gaurgenderIndividual").removeAttribute("disabled","true");
				//document.getElementById("gauraadhaar").removeAttribute("disabled","true");
				//document.getElementById("guarconSubprofile").removeAttribute("disabled","true");
			}
		
	}
	function coAppEnable(){
		var coAppStatus= document.getElementById("coAppStatus").checked;
		//alert(coAppStatus);
		//alert(coAppStatus.checked);
		if(coAppStatus==false){
		//	document.getElementById("coApp").style.display="";
		//	document.getElementById("coApp1").style.display="none";
			document.getElementById("coApprelationship").value="New";
			//if(document.getElementById("coAppcustomerIdButton"))
			document.getElementById("coAppcustomerType1").value="I";
			document.getElementById("coAppindconstitution").value="";
			document.getElementById("coAppfirstName").value="";
			document.getElementById("coApplastName").value="";
			document.getElementById("coAppcustDOB").value="";
			document.getElementById("coAppfatherName").value="";
			document.getElementById("coApppassport").value="";
			document.getElementById("coAppdlNumber").value="";
			document.getElementById("coAppvoterId").value="";
			document.getElementById("coAppcustPanInd").value="";
			document.getElementById("coAppaddresstype").value="";
			document.getElementById("coAppaddress1").value="";
			document.getElementById("coAppaddress2").value="";
			document.getElementById("coAppaddress3").value="";
			document.getElementById("coAppcountry").value="";
			document.getElementById("coAppstate").value="";
			document.getElementById("coAppdist").value="";
			document.getElementById("coApptahsilDesc").value="";
			document.getElementById("coApppincode").value="";
			document.getElementById("coAppphoneOff").value="";
			document.getElementById("coAppemail").value="";
			document.getElementById("coApplandmark").value="";
			document.getElementById("coAppgenderIndividual").value="";
			document.getElementById("coAppaadhaar").value="";
			document.getElementById("coApprelationship").setAttribute("disabled","true");
			//if(document.getElementById("coAppcustomerIdButton"))
			document.getElementById("coAppcustomerIdButton").setAttribute("disabled","true");
			document.getElementById("coAppcustomerIdButton").style.display="none"
			document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			document.getElementById("coAppindconstitution").setAttribute("disabled","true");
			document.getElementById("coAppfirstName").setAttribute("disabled","true");
			document.getElementById("coApplastName").setAttribute("disabled","true");
			document.getElementById("coAppcustDOB").setAttribute("disabled","true");
			document.getElementById("coAppfatherName").setAttribute("disabled","true");
			document.getElementById("coApppassport").setAttribute("disabled","true");
			document.getElementById("coAppdlNumber").setAttribute("disabled","true");
			document.getElementById("coAppvoterId").setAttribute("disabled","true");
			document.getElementById("coAppcustPanInd").setAttribute("disabled","true");
			document.getElementById("coAppaddresstype").setAttribute("disabled","true");
			document.getElementById("coAppaddress1").setAttribute("disabled","true");
			document.getElementById("coAppaddress2").setAttribute("disabled","true");
			document.getElementById("coAppaddress3").setAttribute("disabled","true");
			document.getElementById("coAppcountry").setAttribute("disabled","true");
			document.getElementById("coAppstate").setAttribute("disabled","true");
			document.getElementById("coAppdist").setAttribute("disabled","true");
			document.getElementById("coApptahsilDesc").setAttribute("disabled","true");
			document.getElementById("coApppincode").setAttribute("disabled","true");
			document.getElementById("coAppphoneOff").setAttribute("disabled","true");
			document.getElementById("coAppemail").setAttribute("disabled","true");
			document.getElementById("coApplandmark").setAttribute("disabled","true");
			document.getElementById("coAppgenderIndividual").setAttribute("disabled","true");
			document.getElementById("coAppaadhaar").setAttribute("disabled","true");
			//document.getElementById("coAppconSubprofile").setAttribute("disabled","true");
			
			}else{
				document.getElementById("coApprelationship").removeAttribute("disabled","true");
			//	document.getElementById("coApp").style.display="none";
			//	document.getElementById("coApp1").style.display="";
				//if(document.getElementById("coAppcustomerIdButton"))
				document.getElementById("coAppcustomerIdButton").removeAttribute("disabled","true");
				document.getElementById("coAppcustomerType1").removeAttribute("disabled","true");
				document.getElementById("coAppindconstitution").removeAttribute("disabled","true");
				document.getElementById("coAppfirstName").removeAttribute("disabled","true");
				document.getElementById("coApplastName").removeAttribute("disabled","true");
				document.getElementById("coAppcustDOB").removeAttribute("disabled","true");
				document.getElementById("coAppfatherName").removeAttribute("disabled","true");
				document.getElementById("coApppassport").removeAttribute("disabled","true");
				document.getElementById("coAppdlNumber").removeAttribute("disabled","true");
				document.getElementById("coAppvoterId").removeAttribute("disabled","true");
				document.getElementById("coAppcustPanInd").removeAttribute("disabled","true");
				document.getElementById("coAppaddresstype").removeAttribute("disabled","true");
				document.getElementById("coAppaddress1").removeAttribute("disabled","true");
				document.getElementById("coAppaddress2").removeAttribute("disabled","true");
				document.getElementById("coAppaddress3").removeAttribute("disabled","true");
				document.getElementById("coAppcountry").removeAttribute("disabled","true");
				document.getElementById("coAppstate").removeAttribute("disabled","true");
				document.getElementById("coAppdist").removeAttribute("disabled","true");
				document.getElementById("coApptahsilDesc").removeAttribute("disabled","true");
				document.getElementById("coApppincode").removeAttribute("disabled","true");
				document.getElementById("coAppphoneOff").removeAttribute("disabled","true");
				document.getElementById("coAppemail").removeAttribute("disabled","true");
				document.getElementById("coApplandmark").removeAttribute("disabled","true");
				document.getElementById("coAppgenderIndividual").removeAttribute("disabled","true");
				//document.getElementById("coAppaadhaar").removeAttribute("disabled","true");
				//document.getElementById("coAppconSubprofile").removeAttribute("disabled","true");
			}
		
	}
	//Rohit Chnages for Dedupe Starts
	function callDedupeReferralPreDeal(type,txnId) {
			
		var preDealId=document.getElementById("leadId").value;
		var gen=document.getElementById("genderIndividual").value;
		
		if(gen== "")
		{
			
			alert("Please save Pre-Deal Detail data first!");
			
			}
	
		else
			{
		if(preDealId == "")
			{
		
			alert("Please save Pre-Deal Detail data first!");
			
			}
		else
			{
			//alert(val);
		if(txnId!=""){
			var contextPath =document.getElementById('contextPath').value ;
			var url=contextPath+"/dedupeReferalSearch.do?method=openModifyForAuthor&leadId="+txnId+"&txnType="+type+" ";
			//alert(url);
				newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
				if (window.focus) {newwindow.focus()}
				return false;
		}else{
			alert("Please save details first!!!!");
			return false;
		}
		
	}
    }
	}
	function callCibilPreDeal(type,txnId) {
      
		var preDealId=document.getElementById("leadId").value;
        var gen=document.getElementById("genderIndividual").value;
		
		if(gen== "")
		{
			
			alert("Please save Pre-Deal Detail data first!");
			
			}
		else
			{
		if(preDealId =="")
			{
			alert("Please save Pre-Deal Detail data first!");
			
			}
		else
			{
		
		//alert(val);
	if(txnId!=""){
		var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/preDealcibilVerificationDispatchAction.do?method=openPreDealData&leadId="+txnId+"&txnType="+type+" ";
		//alert(url);
			newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
			if (window.focus) {newwindow.focus()}
			return false;
	}else{
		alert("Please save details first!!!!");
		return false;
	}
	
		}
		}
	}
	function forwordCibilIniPreDeal()
	{
		//alert("Record Successfully forworded to Cibil");
		
		 
		DisButClass.prototype.DisButMethod();
		var leadId=document.getElementById("leadId").value;
		var id=document.getElementsByName("chkCases");
		var customerId ='';
		var contextPath=document.getElementById("contextPath").value;
		for(var i=0; i< id.length ; i++)
			{
		   if(id[i].checked == true)
			   {
			   var fname = document.getElementById("fname"+i).value;
		var dob = document.getElementById("dob"+i).value;
		var gender= document.getElementById("gender"+i).value;
		var state= document.getElementById("state"+i).value;
		var pincode= document.getElementById("pincode"+i).value;
		var pan= document.getElementById("pan"+i).value;
		var passport= document.getElementById("voterid"+i).value;
		var voterid= document.getElementById("passport"+i).value;
		
		var drivinglicence= document.getElementById("drivinglicence"+i).value;
		var uid= document.getElementById("uid"+i).value;
		 if(fname==''){
			  alert("Consumer First Name is Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
		 if(dob==''){
			  alert("Consumer Date of Birth Name is Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
		 if(gender==''){
			  alert("Consumer Gender is Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
		 if(state==''){
			  alert("Consumer State is Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
		 if(pincode==''){
			  alert("Pincode is Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
		 if(pan=="" && voterid=="" && passport=="" && drivinglicence=="" && uid=="" ){
			  alert(" Consumer Identity Missing : Unable to Send Record");
			  DisButClass.prototype.EnbButMethod();
			return false;
			 }
	 		  customerId =customerId+",'"+id[i].value+"'";
			   }
			}
		if(customerId=='')
			{
			alert("Please select atleast one record!!!!");
			 DisButClass.prototype.EnbButMethod();
				return false;
			}
		else{
			customerId=customerId.substring(1);
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("cibilVerification").action = contextPath+"/preDealcibilVerificationDispatchAction.do?method=cibilVerificationPreDealSave&leadId="+leadId+"&customerId="+customerId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("cibilVerification").submit();
		}
		
	}
	function viewPreDealCibilDone(){
		var id=document.getElementsByName("radioId");
		//alert('length : '+id.length);
		var cRes1=document.getElementsByName("cibilDone");
		var cbId=document.getElementsByName("lbxCibilId");
		var cusId=document.getElementsByName("customerId");
		var dealId=document.getElementById('leadId').value ;
		var cibilDone='';
		var customerId='';
		var cibilId="";
		var cibilResponse="";
		for(var i=0; i< id.length ; i++) 
		{
		//alert('inside for loop');
			if(id[i].checked == true)
			{
		//	alert('inside for if');
				//cibilId = id[i].value;
				cibilId = cbId[i].value
				cibilDone=cRes1[i].value;	
				customerId=cusId[i].value;
				cibilDone=document.getElementById('cibilDone'+i).value ;
				customerId=document.getElementById('customerId'+i).value ;
				//alert('inside for if :' +cibilDone+" , cibilId : "+cibilId);
			}
		}
		
		if(cibilDone=='N' || cibilDone=='')
		{
			alert("BUREAU NOT DONE ");
			return false;
		}
		else
		{
			
			
			
			var contextPath =document.getElementById('contextPath').value ;
			
			document.getElementById("cibilVerification").action = contextPath+"/preDealcibilVerificationDispatchAction.do?method=cibilPreDealReportGenerate&cibilID="+cibilId+"&cibilResponse="+cibilResponse+"&cibilDone="+cibilDone+"&customerId="+customerId+"&leadId="+dealId;
			document.getElementById("cibilVerification").submit();
			
		}
		
	}
	//Rohit Changes for Dedupe end
	/*function copyApplicantToCoAppAdd(){
		
		var check=document.getElementById('coAppStatus').checked;
		if(check){
		
		var copyAppCoapp=document.getElementById('copyAppCoapp').checked;
		
		if(copyAppCoapp==true){
		var addresstype=top.opener.location.document.getElementById('addresstype').value;
		var address1=top.opener.location.document.getElementById('address1').value;
		var address2=top.opener.location.document.getElementById('address2').value;
		var address3=top.opener.location.document.getElementById('address3').value;
		var country=top.opener.location.document.getElementById('country').value;
		var txtCountryCode=top.opener.location.document.getElementById('txtCountryCode').value;
		var state=top.opener.location.document.getElementById('state').value;
		var txtStateCode=top.opener.location.document.getElementById('txtStateCode').value;
		var dist=top.opener.location.document.getElementById('dist').value;
		var txtDistCode=top.opener.location.document.getElementById('txtDistCode').value;
		var tahsilDesc=top.opener.location.document.getElementById('tahsilDesc').value;
		var tahsil=top.opener.location.document.getElementById('tahsil').value;
		if(top.opener.location.document.getElementById('txnTahsilHID')){
		var txnTahsilHID=top.opener.location.document.getElementById('txnTahsilHID').value;
		}
		var pincode=top.opener.location.document.getElementById('pincode').value;
		var landmark=top.opener.location.document.getElementById('landmark').value;
		var phoneOff=top.opener.location.document.getElementById('phoneOff').value;
		document.getElementById('coAppaddresstype').value=addresstype;
		document.getElementById('coAppaddress1').value=address1;
		document.getElementById('coAppaddress2').value=address2;
		document.getElementById('coAppaddress3').value=address3;
		document.getElementById('coAppcountry').value=country;
		document.getElementById('coApptxtCountryCode').value=txtCountryCode;
		document.getElementById('coAppstate').value=state;
		document.getElementById('coApptxtStateCode').value=txtStateCode;
		document.getElementById('coAppdist').value=dist;
		document.getElementById('coApptxtDistCode').value=txtDistCode;
		document.getElementById('coApptahsilDesc').value=tahsilDesc;
		document.getElementById('coApptahsil').value=tahsil;
		if(document.getElementById('coApptxnTahsilHID')){
		document.getElementById('coApptxnTahsilHID').value=txnTahsilHID;
		}
		document.getElementById('coApppincode').value=pincode;
		document.getElementById('coApplandmark').value=landmark;
		document.getElementById('coAppphoneOff').value=phoneOff;
		}else{
			document.getElementById('coAppaddresstype').value="";
			document.getElementById('coAppaddress1').value="";
			document.getElementById('coAppaddress2').value="";
			document.getElementById('coAppaddress3').value="";
			document.getElementById('coAppcountry').value="";
			document.getElementById('coApptxtCountryCode').value="";
			document.getElementById('coAppstate').value="";
			document.getElementById('coApptxtStateCode').value="";
			document.getElementById('coAppdist').value="";
			document.getElementById('coApptxtDistCode').value="";
			document.getElementById('coApptahsilDesc').value="";
			document.getElementById('coApptahsil').value="";
			if(document.getElementById('coApptxnTahsilHID')){
			document.getElementById('coApptxnTahsilHID').value="";
			}
			document.getElementById('coApppincode').value="";
			document.getElementById('coApplandmark').value="";
			document.getElementById('coAppphoneOff').value="";
		}
		}else{
			
			alert("Please Enable Co-Applicant");
		return false;
		
		}
	}*/
	/*function copyApplicantToGaurAdd(){
	 
		var check=document.getElementById('gaurStatus').checked;
		if(check){
		 
		var copyAppGaur=document.getElementById('copyAppGaur').checked;
		if(copyAppGaur==true){
			document.getElementById('copyCoAppGaur').checked=false;
		var addresstype=document.getElementById('addresstype').value;
		var address1=document.getElementById('address1').value;
		var address2=document.getElementById('address2').value;
		var address3=document.getElementById('address3').value;
		var country=document.getElementById('country').value;
		var txtCountryCode=document.getElementById('txtCountryCode').value;
		var state=document.getElementById('state').value;
		var txtStateCode=document.getElementById('txtStateCode').value;
		var dist=document.getElementById('dist').value;
		var txtDistCode=document.getElementById('txtDistCode').value;
		var tahsilDesc=document.getElementById('tahsilDesc').value;
		var tahsil=document.getElementById('tahsil').value;
		if(document.getElementById('txnTahsilHID')){
		var txnTahsilHID=document.getElementById('txnTahsilHID').value;
		}
		var pincode=document.getElementById('pincode').value;
		var landmark=document.getElementById('landmark').value;
		var phoneOff=document.getElementById('phoneOff').value;
		
		document.getElementById('gauraddresstype').value=addresstype;
		document.getElementById('gauraddress1').value=address1;
		document.getElementById('gauraddress2').value=address2;
		document.getElementById('gauraddress3').value=address3;
		document.getElementById('gaurcountry').value=country;
		document.getElementById('gaurtxtCountryCode').value=txtCountryCode;
		document.getElementById('gaurstate').value=state;
		document.getElementById('gaurtxtStateCode').value=txtStateCode;
		document.getElementById('gaurdist').value=dist;
		document.getElementById('gaurtxtDistCode').value=txtDistCode;
		document.getElementById('gaurtahsilDesc').value=tahsilDesc;
		document.getElementById('gaurtahsil').value=tahsil;
		if(document.getElementById('gaurtxnTahsilHID')){
		document.getElementById('gaurtxnTahsilHID').value=txnTahsilHID;
		}
		document.getElementById('gaurpincode').value=pincode;
		document.getElementById('gaurlandmark').value=landmark;
		document.getElementById('gaurphoneOff').value=phoneOff;
		}else{
			document.getElementById('gauraddresstype').value="";
			document.getElementById('gauraddress1').value="";
			document.getElementById('gauraddress2').value="";
			document.getElementById('gauraddress3').value="";
			document.getElementById('gaurcountry').value="";
			document.getElementById('gaurtxtCountryCode').value="";
			document.getElementById('gaurstate').value="";
			document.getElementById('gaurtxtStateCode').value="";
			document.getElementById('gaurdist').value="";
			document.getElementById('gaurtxtDistCode').value="";
			document.getElementById('gaurtahsilDesc').value="";
			document.getElementById('gaurtahsil').value="";
			if(document.getElementById('gaurtxnTahsilHID')){
			document.getElementById('gaurtxnTahsilHID').value="";
			}
			document.getElementById('gaurpincode').value="";
			document.getElementById('gaurlandmark').value="";
			document.getElementById('gaurphoneOff').value="";
		}
		}else{
			
			alert("Please Enable Guarantor");
		return false;
		
		}
	}*/
	function copyCoApplicantToGaurAdd(){
		var check=document.getElementById('gaurStatus').checked;
		if(check){
		 
		var copyCoAppGaur=document.getElementById('copyCoAppGaur').checked;
		if(copyCoAppGaur==true){

		document.getElementById('copyAppGaur').checked=false;
		var addresstype=document.getElementById('coAppaddresstype').value;
		var address1=document.getElementById('coAppaddress1').value;
		var address2=document.getElementById('coAppaddress2').value;
		var address3=document.getElementById('coAppaddress3').value;
		var country=document.getElementById('coAppcountry').value;
		var txtCountryCode=document.getElementById('coApptxtCountryCode').value;
		var state=document.getElementById('coAppstate').value;
		var txtStateCode=document.getElementById('coApptxtStateCode').value;
		var dist=document.getElementById('coAppdist').value;
		var txtDistCode=document.getElementById('coApptxtDistCode').value;
		var tahsilDesc=document.getElementById('coApptahsilDesc').value;
		var tahsil=document.getElementById('coApptahsil').value;
		if(document.getElementById('coApptxnTahsilHID')){
		var coApptxnTahsilHID=document.getElementById('coApptxnTahsilHID').value;
		}
		var pincode=document.getElementById('coApppincode').value;
		var landmark=document.getElementById('coApplandmark').value;
		var phoneOff=document.getElementById('coAppphoneOff').value;
		document.getElementById('gauraddresstype').value=addresstype;
		document.getElementById('gauraddress1').value=address1;
		document.getElementById('gauraddress2').value=address2;
		document.getElementById('gauraddress3').value=address3;
		document.getElementById('gaurcountry').value=country;
		document.getElementById('gaurtxtCountryCode').value=txtCountryCode;
		document.getElementById('gaurstate').value=state;
		document.getElementById('gaurtxtStateCode').value=txtStateCode;
		document.getElementById('gaurdist').value=dist;
		document.getElementById('gaurtxtDistCode').value=txtDistCode;
		document.getElementById('gaurtahsilDesc').value=tahsilDesc;
		document.getElementById('gaurtahsil').value=tahsil;
		if(document.getElementById('gaurtxnTahsilHID')){
		document.getElementById('gaurtxnTahsilHID').value=coApptxnTahsilHID;
		}
		document.getElementById('gaurpincode').value=pincode;
		document.getElementById('gaurlandmark').value=landmark;
		document.getElementById('gaurphoneOff').value=phoneOff;
	}else{
		document.getElementById('gauraddresstype').value="";
		document.getElementById('gauraddress1').value="";
		document.getElementById('gauraddress2').value="";
		document.getElementById('gauraddress3').value="";
		document.getElementById('gaurcountry').value="";
		document.getElementById('gaurtxtCountryCode').value="";
		document.getElementById('gaurstate').value="";
		document.getElementById('gaurtxtStateCode').value="";
		document.getElementById('gaurdist').value="";
		document.getElementById('gaurtxtDistCode').value="";
		document.getElementById('gaurtahsilDesc').value="";
		document.getElementById('gaurtahsil').value="";
		if(document.getElementById('gaurtxnTahsilHID')){
		document.getElementById('gaurtxnTahsilHID').value="";
		}
		document.getElementById('gaurpincode').value="";
		document.getElementById('gaurlandmark').value="";
		document.getElementById('gaurphoneOff').value="";
	}
		
		}else{
			
			alert("Please Enable Guarantor");
		return false;
		
		}
	}
	// pooja code for CRIF
	function generateHistoricalData(){
		var leadId=document.getElementById("leadId").value;
		var LinkId=document.getElementById("LinkId").value;
		//alert("LinkId:"+LinkId);
		if(LinkId==''){
			alert("Please Select Report Name First");
		}
		else{
		var contextPath =document.getElementById('contextPath').value ;	
		document.getElementById("cibilVerification").action = contextPath+"/preDealcibilVerificationDispatchAction.do?method=preDealcibilHstReportGenerate&leadId="+leadId+"&LinkId="+LinkId;
		document.getElementById("cibilVerification").submit();
		}
	}
	 
/*function indConSubprofile(id)
{ 
	 
	    var indconstitution = document.getElementById(id).value;
	   
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/preDealCapturingBehindAction.do?method=getIndConSubprofile&indconstitution="+indconstitution+"&id="+id;
		 var data = "indconstitution="+indconstitution;
		 sendForGetIndConSubprofile(address, data,id);
					 
}*/
/*function sendForGetIndConSubprofile(address, data,id) {
	//alert("in sendForgetCustomerName id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultSubProfile(request,id);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}*/

/*function resultSubProfile(request,id){
	
	 
	//alert("in resultCustomerName id");
	if ((request.readyState == 4) && (request.status == 200)) {
		
		if(id==="indconstitution"){
		var str = request.responseText;
		//alert("String:--->"+str);
		document.getElementById('appconSubprofile').innerHTML=trim(str);
		}
		if(id==="coAppindconstitution"){
			var str = request.responseText;
			
			document.getElementById('coAppconSubprofile').innerHTML=trim(str);
			}
		if(id==="gaurindconstitution"){
			var str = request.responseText;
			
			document.getElementById('guarconSubprofile').innerHTML=trim(str);
			}
	}
	
}*/

// pooja code for multiple customers at single page bureau
function callPreDealCoappDtl(txnId,custType) {
	
	var preDealId=document.getElementById("leadId").value;
	var gen=document.getElementById("genderIndividual").value;
	
	if(gen== "")
	{
		
		alert("Please save Applicant Details first!");
		
		}

	else
		{
	if(preDealId == "")
		{
	
		alert("Please save Applicant Details first!");
		
		}
	else
		{
		//alert(val);
	if(txnId!=""){
		var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/preDealCoappSearch.do?leadId="+txnId+"&custType="+custType;
		//alert(url);
			newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
			if (window.focus) {newwindow.focus()}
			return false;
	}else{
		alert("Please save details first!!!!");
		return false;
	}
	
}
}
}
function callPreDealGurDtl(txnId,custType) {
	var preDealId=document.getElementById("leadId").value;
	var gen=document.getElementById("genderIndividual").value;
	
	if(gen== "")
	{
		
		alert("Please save Applicant Details first!");
		
		}

	else
		{
	if(preDealId == "")
		{
	
		alert("Please save Applicant Details first!");
		
		}
	else
		{
		//alert(val);
	if(txnId!=""){
		var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/preDealGurSearch.do?leadId="+txnId+"&custType="+custType;
		//alert(url);
			newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
			if (window.focus) {newwindow.focus()}
			return false;
	}else{
		alert("Please save details first!!!!");
		return false;
	}
	
}
}
}
function saveCoappLeadDetails(id,leadId){
	
	
	DisButClass.prototype.DisButMethod();

	var sourcepath=document.getElementById("contextPath").value;
	var formName=document.getElementById("leadCapturingDetails");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	var coApplbxCustomerId= document.getElementById("coApplbxCustomerId").value;

	var relationship=document.getElementById("coApprelationship1");
	var customerName=document.getElementById("coAppcustomerName");
	var addresstype=document.getElementById("coAppaddresstype1");
	var address1=document.getElementById("coAppaddress1");
	var country=document.getElementById("coAppcountry");
	var state=document.getElementById("coAppstate");
	var dist=document.getElementById("coAppdist");
	var pincode=document.getElementById("coApppincode");
	var phoneOff=document.getElementById("coAppphoneOff");
	var email=document.getElementById("coAppemail");
	var industry=document.getElementById("coAppindustry");
	var subIndustry=document.getElementById("coAppsubIndustry");
	var product=document.getElementById("coAppproduct");
	var scheme=document.getElementById("coAppscheme");
	var loanAmount=document.getElementById("coApploanAmount");
	var loanTenure=document.getElementById("coApploanTenure");
	var customerType=document.getElementById("coAppcustomerType");
	var groupName=document.getElementById("coAppgroupName");
	var groupName1=document.getElementById("coAppgroupName1");
	var groupType=document.getElementById("coAppgroupType");
	var businessSegment=document.getElementById("coAppbusinessSegment1");
	var corconstitution=document.getElementById("coAppcorconstitution");
	var indconstitution=document.getElementById("coAppindconstitution");
    var firstName=document.getElementById("coAppfirstName");
	var lastName=document.getElementById("coApplastName");
	var fatherName=document.getElementById("coAppfatherName");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	var primaryPhoneNo=document.getElementById("coAppphoneOff").value;
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Invalid Mobile No. ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	if(customerType.value=="I"){
		
		if(!checkCurrentAge("coAppcustDOB")){
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
		var genderIndividual=document.getElementById("coAppgenderIndividual").value;
		var passport=document.getElementById("coApppassport").value;
		var aadhaar=document.getElementById("coAppaadhaar").value;
		var coAppaadhaar=document.getElementById("coAppaadhaar").value;
		var custPanInd=document.getElementById("coAppcustPanInd").value;
		var coAppcustPanInd=document.getElementById("coAppcustPanInd").value;
		var dlNumber=document.getElementById("coAppdlNumber").value;
		var voterId=document.getElementById("coAppvoterId").value;
		var coAppindconstitution=document.getElementById("coAppindconstitution").value;
		
		if(passport=="" && aadhaar=="" && custPanInd=="" && dlNumber=="" && voterId==""  ){
			alert("Please capture atleast one KYC");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
		/*if(aadhaar==""){
			alert("Aadhar/UID No. is mandatory");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		
	}
	
	var msg1 = '', msg2 = '', msg3 = '', msg4 = '', msg5 = '', msg6 = '', msg7 = '', msg8 = '', msg9 = '', msg10 = '', msg11 = '', msg12 = '', msg13 = '', msg14 = '', msg15 = '', msg16 = '', msg17 = '', msg18 = '',msg19= '',msg20= '';
	if(relationship.value==""  || customerType.value=="" ||  
			address1.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
			 
			 addresstype.value=="" || email.value=="" || country.value=="" )
	{
	
			
			if(relationship.value==""){
				msg1="Relationship is mandatory.\n";
			}
			if(customerType.value==""){
				msg2="Customer Type is mandatory.\n";
			}
			if(address1.value==""){
				msg3="Address Line1 is mandatory.\n";
			}
			if(country.value==""){
				msg4="Country  is mandatory.\n";
			}
			if(state.value==""){
				msg5="State is mandatory.\n";
			}
			if(dist.value==""){
				msg6="District is mandatory.\n";
			}
			if(pincode.value==""){
				msg7="Pin Code is mandatory.\n";
			}
			if(phoneOff.value==""){
				msg8="Mobile no is mandatory.\n";
			}
			if(addresstype.value==""){
				msg9="Address Type is mandatory.\n";
			}
			if(email.value=="" && emailMandatoryFlag == 'Y'){
				msg10="E-Mail Id  is mandatory.\n";
			}
			
			
		
			if (msg1 != '' || msg2 != '' || msg3 != '' || msg4 != '' || msg5 != '' || msg6 != '' || msg7 != '' || msg8 != '' || msg9 != ''|| msg10 != '' ) 
		{
				alert(msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg10);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
		}
		
		
	}	
	if(customerType.value =='I'){
		if(firstName.value=="" || lastName.value=="" || indconstitution.value=="" || fatherName.value=="")
		{
			if(firstName.value==""){
				msg11="First Name is mandatory.\n";
			}
			if(lastName.value==""){
				msg12="Last Name is mandatory.\n";
			}
			if(indconstitution.value==""){
				msg13="Constitution  is mandatory.\n";
			}
			if(fatherName.value==""){
				msg14="Father's/Husband's Name is mandatory.\n";
			}
			
		
			if (msg11 != '' || msg12 != '' || msg13 != '' || msg14 != '' ) 
			{
					alert(msg11 + msg12 + msg13 + msg14 );
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			}else{ 
				if(!ck_alphaNumericc.test(firstName.value)){
				 alert("First Name is invalid");
					document.getElementById("coAppfirstName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if(!ck_alphaNumericc.test(lastName.value)){
				 alert("Last Name is invalid");
					document.getElementById("coApplastName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if(!ck_alphaNumericc.test(fatherName.value)){
				 alert("Father's/Husband's Name is invalid");
				 document.getElementById("coAppfatherName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
					
			}
	}
		
		if( email.value!=""  ){
			
		
			if(!valid_email.test(email.value)){
				alert("Invalid Email Id");
				document.getElementById("coAppemail").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}

	
	if( phoneOff.value!=""  ){
		
		 if(!ck_numeric.test(phoneOff.value)){
			 alert("Mobile Number is invalid");
				document.getElementById("coAppphoneOff").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
	}
	if( pincode.value!=""  ){
		
		 if(!ck_numericpincode.test(pincode.value)){
			 alert("Pincode is invalid");
				document.getElementById("coApppincode").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
	}

	var pan = $("#coAppcustPan").val();
    if(pan != '')
    {
		var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if (!regex1.test(pan) || pan.length != 10) {
			alert("Invalid Pan No.");
			$("#coAppcustPan").attr("value","");
			$("#coAppcustPan").focus();
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
    var pan = $("#coAppcustPanInd").val();
    if(pan != '')
    {
		var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if (!regex1.test(pan) || pan.length != 10) {
			alert("Invalid Pan No.");
			$("#coAppcustPanInd").attr("value","");
			$("#coAppcustPanInd").focus();
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
    if(customerType.value=='I')
    {
	    var pass = $("#coApppassport").val();
	    if(pass != '')
	    {
			var regex2 = /^[A-Z]{1}\d{7}$/;
			if (!regex2.test(pass) || pass.length != 8) {
				alert("Invalid Passport No.");
				$("#coApppassport").attr("value","");
				$("#coApppassport").focus();
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
				document.getElementById("coAppgroupName1").focus();
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

		if( customerName.value=="" || groupType.value=="" ||businessSegment.value==""
			|| corconstitution.value==""){
		
			if(customerName.value==""){
				msg11="Customer Name is mandatory.\n";
			}
			/*if(industry.value==""){
				msg12="Industry (Value Chain) is mandatory.\n";
			}
			if(subIndustry.value==""){
				msg13="Sub-Industry/Nature of Business  is mandatory.\n";
			}*/
			if(groupType.value==""){
				msg14="Group Name is mandatory.\n";
			}
			if(businessSegment.value==""){
				msg15="Business Segment  is mandatory.\n";
			}
			if(corconstitution.value==""){
				msg16="Constitution is mandatory.\n";
			}
			
		
			if (msg11 != '' || msg12 != '' || msg13 != '' || msg14 != '' || msg15 != '' || msg16 != '' ) 
			{
					alert(msg11 + msg12 + msg13 + msg14 + msg15 + msg16 );
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			
			
		}else{ 
			if(!ck_alphaNumericc.test(customerName.value)){
			 alert("Customer Name is invalid");
				document.getElementById("coAppcustomerName").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }		 	
		}
	}

	var scoApprelationship = document.getElementById("coApprelationship").value;
	if(scoApprelationship=="New"){
		var scoAppcustomerType1=document.getElementById("coAppcustomerType1").value;
		if(scoAppcustomerType1==""){
		alert("* Please Select Co-Applicant Customer Type");

		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(scoAppcustomerType1=="C"){
		if(document.getElementById("coAppcustPan").value=="")
		{
		alert("CoApplicant Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
}
	if(scoApprelationship=="Existing"){
	if(scoAppcustomerType1=="C"){
		if(document.getElementById("coAppcustPan").value=="")
		{
		alert("CoApplicant Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
	}
	
		if(document.getElementById("updateCustId").value){
		var updateCustId=document.getElementById("updateCustId").value;
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCoappSave.do?method=leadEntrySave&saveForward="+id+"&coApplbxCustomerId="+coApplbxCustomerId+"&coAppcustPanInd="+coAppcustPanInd+"&coAppaadhaar="+coAppaadhaar+"&customer=coapp&leadId="+leadId+"&updateCustId="+updateCustId;
		}else{
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCoappSave.do?method=saveNewLead&saveForward="+id+"&coApplbxCustomerId="+coApplbxCustomerId+"&coAppcustPanInd="+coAppcustPanInd+"&coAppaadhaar="+coAppaadhaar+"&customer=coapp&leadId="+leadId;
		}
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
	
}
function saveGurLeadDetails(id,leadId){	
	
	DisButClass.prototype.DisButMethod();
	var leadGenerator= document.getElementById("leadGenerator");
	
	var sourcepath=document.getElementById("contextPath").value;
	var formName=document.getElementById("leadCapturingDetails");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	
	var gaurlbxCustomerId= document.getElementById("gaurlbxCustomerId").value;
		

	var relationship=document.getElementById("gaurrelationship1");
	var customerName=document.getElementById("gaurcustomerName");
	var addresstype=document.getElementById("gauraddresstype1");
	var address1=document.getElementById("gauraddress1");
	var country=document.getElementById("gaurcountry");
	var state=document.getElementById("gaurstate");
	var dist=document.getElementById("gaurdist");
	var pincode=document.getElementById("gaurpincode");
	var phoneOff=document.getElementById("gaurphoneOff");
	var email=document.getElementById("gauremail");
	var industry=document.getElementById("gaurindustry");
	var subIndustry=document.getElementById("gaursubIndustry");
	var product=document.getElementById("gaurproduct");
	var scheme=document.getElementById("gaurscheme");
	var loanAmount=document.getElementById("gaurloanAmount");
	var loanTenure=document.getElementById("gaurloanTenure");
	var customerType=document.getElementById("gaurcustomerType");
	var groupName=document.getElementById("gaurgroupName");
	var groupName1=document.getElementById("gaurgroupName1");
	var groupType=document.getElementById("gaurgroupType");
	var businessSegment=document.getElementById("gaurbusinessSegment1");
	var corconstitution=document.getElementById("gaurcorconstitution");
	var indconstitution=document.getElementById("gaurindconstitution");
    var firstName=document.getElementById("gaurfirstName");
	var lastName=document.getElementById("gaurlastName");
	var fatherName=document.getElementById("gaurfatherName");
	var ck_alphaNumericc = /^[A-Za-z0-9\,.\&\ @\-\ ()]{1,250}$/;
	var ck_numeric = /^[0-9]{10,21}$/;
	var ck_numericpincode = /^[0-9]{6,7}$/;
	 var ck_panno = /^[A-Za-z0-9]{10,11}$/;
	var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	var primaryPhoneNo=document.getElementById("gaurphoneOff").value;
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Invalid Mobile No. ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	if(customerType.value=="I"){
		
		if(!checkCurrentAge("gaurcustDOB")){
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
		var genderIndividual=document.getElementById("gaurgenderIndividual").value;
		var passport=document.getElementById("gaurpassport").value;
		var aadhaar=document.getElementById("gauraadhaar").value;
		var gauraadhaar=document.getElementById("gauraadhaar").value;
		var custPanInd=document.getElementById("gaurcustPanInd").value;
		var gaurcustPanInd=document.getElementById("gaurcustPanInd").value;
		var dlNumber=document.getElementById("gaurdlNumber").value;
		var voterId=document.getElementById("gaurvoterId").value;
		var gaurindconstitution=document.getElementById("gaurindconstitution").value;
		if(passport=="" && aadhaar=="" && custPanInd=="" && dlNumber=="" && voterId==""  ){
			alert("Please capture atleast one KYC");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
	/*	if(aadhaar==""){
			alert("Aadhar/UID No. is mandatory");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		if(gaurindconstitution==""){
			alert("Guarantor Constitution is required!");
			document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var msg1 = '', msg2 = '', msg3 = '', msg4 = '', msg5 = '', msg6 = '', msg7 = '', msg8 = '', msg9 = '', msg10 = '', msg11 = '', msg12 = '', msg13 = '', msg14 = '', msg15 = '', msg16 = '', msg17 = '', msg18 = '',msg19= '',msg20= '';
	if(relationship.value==""  || customerType.value=="" ||  
			address1.value=="" || state.value=="" || dist.value=="" || pincode.value=="" ||  phoneOff.value==""  || 
			 
			 addresstype.value==""  || email.value=="" || country.value=="" )
	{
		
		
		if(relationship.value==""){
			msg1="Relationship is mandatory.\n";
		}
		if(customerType.value==""){
			msg2="Customer Type is mandatory.\n";
		}
		if(address1.value==""){
			msg3="Address Line1 is mandatory.\n";
		}
		if(country.value==""){
			msg4="Country  is mandatory.\n";
		}
		if(state.value==""){
			msg5="State is mandatory.\n";
		}
		if(dist.value==""){
			msg6="District is mandatory.\n";
		}
		if(pincode.value==""){
			msg7="Pin Code is mandatory.\n";
		}
		if(phoneOff.value==""){
			msg8="Mobile no is mandatory.\n";
		}
		if(addresstype.value==""){
			msg9="Address Type is mandatory.\n";
		}
		if(email.value=="" && emailMandatoryFlag == 'Y'){
			msg10="E-Mail Id  is mandatory.\n";
		}
		
		
	
		if (msg1 != '' || msg2 != '' || msg3 != '' || msg4 != '' || msg5 != '' || msg6 != '' || msg7 != '' || msg8 != '' || msg9 != ''|| msg10 != '' ) 
	{
			alert(msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg10);
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	
	}	
	if(customerType.value =='I'){
		if(firstName.value=="" || lastName.value=="" || indconstitution.value=="" || fatherName.value=="")
		{
			if(firstName.value==""){
				msg11="First Name is mandatory.\n";
			}
			if(lastName.value==""){
				msg12="Last Name is mandatory.\n";
			}
			if(indconstitution.value==""){
				msg13="Constitution  is mandatory.\n";
			}
			if(fatherName.value==""){
				msg14="Father's/Husband's Name is mandatory.\n";
			}
			
		
			if (msg11 != '' || msg12 != '' || msg13 != '' || msg14 != '' ) 
			{
					alert(msg11 + msg12 + msg13 + msg14 );
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			}else{ 
				if(!ck_alphaNumericc.test(firstName.value)){
				 alert("First Name is invalid");
					document.getElementById("gaurfirstName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if(!ck_alphaNumericc.test(lastName.value)){
				 alert("Last Name is invalid");
					document.getElementById("gaurlastName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }if(!ck_alphaNumericc.test(fatherName.value)){
				 alert("Father's/Husband's Name is invalid");
				 document.getElementById("gaurfatherName").focus();
				 document.getElementById("Save").removeAttribute("disabled", "true");
				 DisButClass.prototype.EnbButMethod();
				return false;
			 }
					
			}
	}
		
		if( email.value!=""  ){
			
		
			if(!valid_email.test(email.value)){
				alert("Invalid Email Id");
				document.getElementById("gauremail").focus();
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}

	
	if( phoneOff.value!=""  ){
		
		 if(!ck_numeric.test(phoneOff.value)){
			 alert("Mobile Number is invalid");
				document.getElementById("gaurphoneOff").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
	}
	if( pincode.value!=""  ){
		
		 if(!ck_numericpincode.test(pincode.value)){
			 alert("Pincode is invalid");
				document.getElementById("gaurpincode").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
	}

	var pan = $("#gaurcustPan").val();
    if(pan != '')
    {
		var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if (!regex1.test(pan) || pan.length != 10) {
			alert("Invalid Pan No.");
			$("#gaurcustPan").attr("value","");
			$("#gaurcustPan").focus();
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
    var pan = $("#gaurcustPanInd").val();
    if(pan != '')
    {
		var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if (!regex1.test(pan) || pan.length != 10) {
			alert("Invalid Pan No.");
			$("#gaurcustPanInd").attr("value","");
			$("#gaurcustPanInd").focus();
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
    if(customerType.value=='I')
    {
	    var pass = $("#gaurpassport").val();
	    if(pass != '')
	    {
			var regex2 = /^[A-Z]{1}\d{7}$/;
			if (!regex2.test(pass) || pass.length != 8) {
				alert("Invalid Passport No.");
				$("#gaurpassport").attr("value","");
				$("#gaurpassport").focus();
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
				document.getElementById("gaurgroupName1").focus();
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

		if( customerName.value=="" || groupType.value=="" ||businessSegment.value=="" || corconstitution.value==""){
		
			if(customerName.value==""){
				msg11="Customer Name is mandatory.\n";
			}
			/*if(industry.value==""){
				msg12="Industry (Value Chain) is mandatory.\n";
			}
			if(subIndustry.value==""){
				msg13="Sub-Industry/Nature of Business  is mandatory.\n";
			}*/
			if(groupType.value==""){
				msg14="Group Name is mandatory.\n";
			}
			if(businessSegment.value==""){
				msg15="Business Segment  is mandatory.\n";
			}
			if(corconstitution.value==""){
				msg16="Constitution is mandatory.\n";
			}
			
		
			if (msg11 != '' || msg12 != '' || msg13 != '' || msg14 != ''|| msg15 != ''|| msg16 != '' ) 
			{
					alert(msg11 + msg12 + msg13 + msg14+ msg15+ msg16 );
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			
			
		}else{ 
			if(!ck_alphaNumericc.test(customerName.value)){
			 alert("Customer Name is invalid");
				document.getElementById("gaurcustomerName").focus();
			 document.getElementById("Save").removeAttribute("disabled", "true");
			 DisButClass.prototype.EnbButMethod();
			return false;
		 }
		
		 	
		}
	}
	
	var sgaurrelationship = document.getElementById("gaurrelationship").value;
	
	if(sgaurrelationship=="New"){
			var sgaurcustomerType1=document.getElementById("gaurcustomerType1").value;
			if(sgaurcustomerType1==""){
			alert("* Please Select Guarantor Customer Type");

			DisButClass.prototype.EnbButMethod();
			return false;
			}
			if(document.getElementById("gaurcustomerType1").value=="C")
			{
			if(document.getElementById("gaurcustPan").value=="")
			{
			alert("Guarantor Pan No is required!");
			document.getElementById("Save").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			}
	}
	if(sgaurrelationship=="Existing")
		{
		if(document.getElementById("gaurcustomerType1").value=="C")
		{
		if(document.getElementById("gaurcustPan").value=="")
		{
		alert("Guarantor Pan No is required!");
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
		}
	
		if(document.getElementById("updateCustId").value){
		var updateCustId=document.getElementById("updateCustId").value;
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealGurSave.do?method=leadEntrySave&saveForward="+id+"&gaurlbxCustomerId="+gaurlbxCustomerId+"&gaurcustPanInd="+gaurcustPanInd+"&gauraadhaar="+gauraadhaar+"&customer=guar&leadId="+leadId+"&updateCustId="+updateCustId;
		}else{
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealGurSave.do?method=saveNewLead&saveForward="+id+"&gaurlbxCustomerId="+gaurlbxCustomerId+"&gaurcustPanInd="+gaurcustPanInd+"&gauraadhaar="+gauraadhaar+"&customer=guar&leadId="+leadId;
		}
		document.getElementById("processingImage").style.display = '';
		document.getElementById("leadCapturingDetails").submit();
		return true;
	}

function fetchCustDtl(customerId,leadId,custType){
	if(customerId){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if(custType=='COAPPL'){
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealCoappSave.do?method=fetchCustDtl&leadId="+leadId+"&customerId="+customerId+"&custType="+custType;
	}else if(custType=='GUARANTOR'){
		document.getElementById("leadCapturingDetails").action=sourcepath+"/preDealGurSave.do?method=fetchCustDtl&leadId="+leadId+"&customerId="+customerId+"&custType="+custType;	
	}
	document.getElementById("processingImage").style.display = '';
	document.getElementById("leadCapturingDetails").submit();
	return true;
	}
	else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function deleteCustomer(confirmStatus,leadId,custType)
{
	var contextPath =document.getElementById('contextPath').value ;
	DisButClass.prototype.DisButMethod();
if(checkApplicant(confirmStatus))
{
if(confirm("Are you sure to delete the record.")) 
{
	 if(custType=='COAPPL'){
		 document.getElementById("leadCapturingDetails").action=contextPath+"/preDealCoappSave.do?method=deleteCustomer&confirmStatus="+confirmStatus+"&leadId="+leadId+"&custType="+custType;
	 }else if(custType=='GUARANTOR'){
		 document.getElementById("leadCapturingDetails").action=contextPath+"/preDealGurSave.do?method=deleteCustomer&confirmStatus="+confirmStatus+"&leadId="+leadId+"&custType="+custType;
	 }
	  document.getElementById("processingImage").style.display = '';
	  document.getElementById("leadCapturingDetails").submit();
	  return true;
	  
}else{
	DisButClass.prototype.EnbButMethod();
	 return false;
}
}
else{
		alert("Please Select atleast one!!!");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function checkApplicant(confirmStatus)
{
	if(confirmStatus=='N')
	{
		var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
		{
			if(ch[zx].checked==false)
			{
				flag=0;
			}
			else
			{
				
				flag=1;
				break;
			}
			zx++;
		}
	}
	else
	{
		 flag=1;
	}
	return flag;
}
function copyApplicantToCoAppAdd()
{
	var copyAppCoapp=document.getElementById('copyAppCoapp').checked;
	
	if(copyAppCoapp==true){
		//document.getElementById('copyAppCoapp').checked=false;
	var leadId=document.getElementById('leadId').value;
	if(leadId!=='')
	{
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxActionForCP.do?method=getApplicantAddress";
		 var data = "leadId="+leadId;
		 sendCopyApplicant(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Lead ID is required.");	
    	 return false;
     }
	}else{
		document.getElementById('coAppaddresstype').value="";
		document.getElementById('coAppaddresstype1').value="";
		document.getElementById('coAppaddress1').value="";
		document.getElementById('coAppaddress2').value="";
		document.getElementById('coAppaddress3').value="";
		document.getElementById('coAppcountry').value="";
		document.getElementById('coApptxtCountryCode').value="";
		document.getElementById('coAppstate').value="";
		document.getElementById('coApptxtStateCode').value="";
		document.getElementById('coAppdist').value="";
		document.getElementById('coApptxtDistCode').value="";
		document.getElementById('coApptahsilDesc').value="";
		document.getElementById('coApptahsil').value="";
		if(document.getElementById('coApptxnTahsilHID')){
		document.getElementById('coApptxnTahsilHID').value="";
		}
		document.getElementById('coApppincode').value="";
		document.getElementById('coApplandmark').value="";
		document.getElementById('coAppphoneOff').value="";
		document.getElementById('coAppemail').value="";
	}
}

function sendCopyApplicant(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCopyApplicant(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultCopyApplicant(request)
{
	//alert("Test 1");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		//alert("Test 2");
		var str = request.responseText;
		//alert("String"+str);
		if(trim(str).length>0)
		{
			var s1 = str.split("$:");
			//alert("Splited"+s1);
			var addresstype=trim(s1[1]);
			var address1=trim(s1[2]);
			var address2=trim(s1[3]);
			var address3=trim(s1[4]);
			var txtCountryCode=trim(s1[5]);
			var country=trim(s1[6]);
			var txtStateCode=trim(s1[7]);
			var state=trim(s1[8]);
			var txtDistCode=trim(s1[9]);
			var dist=trim(s1[10]);
			var pincode=trim(s1[11]);
			var txnTahsilHID=trim(s1[12]);
			var tahsil=trim(s1[13]);
			var tahsilDesc=trim(s1[14]);
			var phoneOff=trim(s1[15]);
			var landmark=trim(s1[16]);
			var email=trim(s1[17]);
			document.getElementById('coAppaddresstype1').value=addresstype;
			document.getElementById('coAppaddresstype').value=addresstype;
			document.getElementById('coAppaddress1').value=address1;
			document.getElementById('coAppaddress2').value=address2;
			document.getElementById('coAppaddress3').value=address3;
			document.getElementById('coAppcountry').value=country;
			document.getElementById('coApptxtCountryCode').value=txtCountryCode;
			document.getElementById('coAppstate').value=state;
			document.getElementById('coApptxtStateCode').value=txtStateCode;
			document.getElementById('coAppdist').value=dist;
			document.getElementById('coApptxtDistCode').value=txtDistCode;
			document.getElementById('coApptahsilDesc').value=tahsilDesc;
			document.getElementById('coApptahsil').value=tahsil;
			if(document.getElementById('coApptxnTahsilHID')){
			document.getElementById('coApptxnTahsilHID').value=txnTahsilHID;
			}
			document.getElementById('coApppincode').value=pincode;
			document.getElementById('coApplandmark').value=landmark;
			document.getElementById('coAppphoneOff').value=phoneOff;
			document.getElementById('coAppemail').value=email;
			
			}
		
	}
	
}
function copyApplicantToGaurAdd()
{
	var copyAppGaur=document.getElementById('copyAppGaur').checked;
	if(copyAppGaur==true){
		//document.getElementById('copyAppGaur').checked=false;
	var leadId=document.getElementById('leadId').value;
	if(leadId!=='')
	{
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxActionForCP.do?method=getApplicantAddress";
		 var data = "leadId="+leadId;
		 sendCopyApplicantGaur(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Lead ID is required.");	
    	 return false;
     }
	}else{
		document.getElementById('gauraddresstype').value="";
		document.getElementById('gauraddresstype1').value="";
		document.getElementById('gauraddress1').value="";
		document.getElementById('gauraddress2').value="";
		document.getElementById('gauraddress3').value="";
		document.getElementById('gaurcountry').value="";
		document.getElementById('gaurtxtCountryCode').value="";
		document.getElementById('gaurstate').value="";
		document.getElementById('gaurtxtStateCode').value="";
		document.getElementById('gaurdist').value="";
		document.getElementById('gaurtxtDistCode').value="";
		document.getElementById('gaurtahsilDesc').value="";
		document.getElementById('gaurtahsil').value="";
		if(document.getElementById('gaurtxnTahsilHID')){
		document.getElementById('gaurtxnTahsilHID').value="";
		}
		document.getElementById('gaurpincode').value="";
		document.getElementById('gaurlandmark').value="";
		document.getElementById('gaurphoneOff').value="";
		document.getElementById('gauremail').value="";
	}
}

function sendCopyApplicantGaur(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCopyApplicantGaur(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultCopyApplicantGaur(request)
{
	//alert("Test 1");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		//alert("Test 2");
		var str = request.responseText;
		//alert("String"+str);
		if(trim(str).length>0)
		{
			var s1 = str.split("$:");
			//alert("Splited"+s1);
			
			var addresstype=trim(s1[1]);
			var address1=trim(s1[2]);
			var address2=trim(s1[3]);
			var address3=trim(s1[4]);
			var txtCountryCode=trim(s1[5]);
			var country=trim(s1[6]);
			var txtStateCode=trim(s1[7]);
			var state=trim(s1[8]);
			var txtDistCode=trim(s1[9]);
			var dist=trim(s1[10]);
			var pincode=trim(s1[11]);
			var txnTahsilHID=trim(s1[12]);
			var tahsil=trim(s1[13]);
			var tahsilDesc=trim(s1[14]);
			var phoneOff=trim(s1[15]);
			var landmark=trim(s1[16]);
			var email=trim(s1[17]);
			document.getElementById('gauraddresstype').value=addresstype;
			document.getElementById('gauraddresstype1').value=addresstype;
			document.getElementById('gauraddress1').value=address1;
			document.getElementById('gauraddress2').value=address2;
			document.getElementById('gauraddress3').value=address3;
			document.getElementById('gaurcountry').value=country;
			document.getElementById('gaurtxtCountryCode').value=txtCountryCode;
			document.getElementById('gaurstate').value=state;
			document.getElementById('gaurtxtStateCode').value=txtStateCode;
			document.getElementById('gaurdist').value=dist;
			document.getElementById('gaurtxtDistCode').value=txtDistCode;
			document.getElementById('gaurtahsilDesc').value=tahsilDesc;
			document.getElementById('gaurtahsil').value=tahsil;
			if(document.getElementById('gaurtxnTahsilHID')){
			document.getElementById('gaurtxnTahsilHID').value=txnTahsilHID;
			}
			document.getElementById('gaurpincode').value=pincode;
			document.getElementById('gaurlandmark').value=landmark;
			document.getElementById('gaurphoneOff').value=phoneOff;
			document.getElementById('gauremail').value=email;
			
			}
		
	}
	
}
function callIMDPreDeal(txnId){

if(txnId!=""){
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/imdMakerViewAction.do?method=savedDataOfReceipt&stage=DC&lbxDealNo="+txnId;
	//alert(url);
		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		return false;
}else{
	alert("Please save details first!!!!");
	return false;
}

}
function checkCurrentAge(dob){
	var dateString = document.getElementById(dob).value;
	var businessdate = document.getElementById("businessdate").value;
	
	var formatD=document.getElementById("formatD").value;
	var birthDate=getDateObject(dateString,formatD.substring(2,3));
	var today=getDateObject(businessdate,formatD.substring(2,3));
	
	if(dateString !="")
	{
	    
	    var age = today.getFullYear() - birthDate.getFullYear();
	    var m = today.getMonth() - birthDate.getMonth();
	    var da = today.getDate() - birthDate.getDate();
	    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
	        age--;
	    }
	    if(m<0){
	        m +=12;
	    }
	    if(da<0){
	        da +=30;
	    }

	  if(age < 18 || age > 85)
	{
	alert("Age is not less than 18 and greater than 85");
	DisButClass.prototype.EnbButMethod();
	return false;
	} 
	} else {
	alert("please provide your date of birth");
	return false;
	}
	return true;
}