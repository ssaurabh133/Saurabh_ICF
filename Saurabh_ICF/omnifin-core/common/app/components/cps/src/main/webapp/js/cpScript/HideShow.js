function leadchange(val) {
	if(document.getElementById("leadId").value == ""){

		
		if (val == 'RM') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';


		} else if (val == 'VENDOR') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("genericDet").style.display = 'none';
			
		} else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
		}else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = '';
		}

	}else
		if (val == 'RM') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';


		} else if (val == 'VENDOR') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			
			
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("genericDet").style.display = 'none';
			

		} else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
		}	
		else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = '';
		}
}

function leadempty(val) {
//	if(document.getElementById("leadId").value == ""){

		if (val == 'RM') {
			document.getElementById("rm").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			document.getElementById("rmCode1").value = "";
			document.getElementById("rmName1").value = "";
			document.getElementById("contactnorm").value = "";
			document.getElementById("leadgenzonerm").value = "";
			document.getElementById("leadgencityrm").value = "";
			document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");

		} else if (val == 'VENDOR') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = '';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			
			document.getElementById("vendorCode1").value = "";
			document.getElementById("vendorName1").value = "";
			document.getElementById("contactnovendor1").value = "";
			document.getElementById("leadgenzonevendor").value = "";
			document.getElementById("leadgencityvendor").value = "";
			document.getElementById("vendorHead1").value = "";
			document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");
			
		} else if (val == 'BRANCH') {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = '';
			document.getElementById("genericDet").style.display = 'none';
			
			document.getElementById("branchCode2").value = "";
			document.getElementById("branchName2").value = "";
			document.getElementById("contactnobranch").value = "";
			document.getElementById("branchHead1").value = "";
			document.getElementById("leadgencitybranch").value = "";
			document.getElementById("description").value = "";
			document.getElementById("relationship").value = "New";
			document.getElementById("relationship").removeAttribute("disabled");
			
		} else if (val == 'Hide' || val == "") {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = 'none';
			document.getElementById("relationship").value = "";
			document.getElementById("relationship").removeAttribute("disabled");
		}else if (val == 'EXISTING'){
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = '';
			document.getElementById("description").value = "";
			document.getElementById("relationship").value = "Existing";
			document.getElementById("relationship").setAttribute("disabled","true");
			document.getElementById("relationshipSince").removeAttribute("disabled");
			
		}else {
			document.getElementById("rm").style.display = 'none';
			document.getElementById("vendor").style.display = 'none';
			document.getElementById("branch").style.display = 'none';
			document.getElementById("genericDet").style.display = '';
			document.getElementById("description").value = "";
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

	if (san == 'RM') {
		document.getElementById("rm").style.display = '';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("genericDet").style.display = 'none';

	} else if (san == 'VENDOR') {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = '';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("genericDet").style.display = 'none';

	} else if (san == 'BRANCH') {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = '';
		document.getElementById("genericDet").style.display = 'none';

	} else if (san == 'Hide' || san == "") {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("genericDet").style.display = 'none';
	}else {
		document.getElementById("rm").style.display = 'none';
		document.getElementById("vendor").style.display = 'none';
		document.getElementById("branch").style.display = 'none';
		document.getElementById("genericDet").style.display = '';
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
	return true;
}

function diffLeadValidation(val){
	
	if(val == "RM" && (document.getElementById("rmCode1").value == "" || document.getElementById("rmName1").value == "" || document.getElementById("contactnorm").value == "" || document.getElementById("leadgenzonerm").value == "" || document.getElementById("leadgencityrm").value == "")){
		var a = "";
		if(document.getElementById("rmCode1").value == "")
			var a= "* RM code is required \n";
		if(document.getElementById("rmName1").value == "")
			a += "* RM name is required \n";
		if(document.getElementById("contactnorm").value == "")
			a +="* Contact no. is required \n";
		if(document.getElementById("leadgenzonerm").value == "")
			a +="* Branch Name is required \n";
		if(document.getElementById("leadgencityrm").value == "")
			a +="* Lead Generation City is required \n";
		alert(a);
		return false;
	}else if(val == "VENDOR" && (document.getElementById("vendorCode1").value == "" || document.getElementById("vendorName1").value == "" || document.getElementById("vendorHead1").value == "" || document.getElementById("contactnovendor1").value == "" || document.getElementById("leadgenzonevendor").value == "" || document.getElementById("leadgencityvendor").value == "")){
		var b = "";
		if(document.getElementById("vendorCode1").value == "")
			var b="* Vendor code is required \n";
		if(document.getElementById("vendorName1").value == "")
			b +="* Vendor name is required \n";
		if(document.getElementById("vendorHead1").value == "")
			b +="* Vendor executive name is required \n";
		if(document.getElementById("contactnovendor1").value == "")
			b +="* Contact no. is required \n";
		if(document.getElementById("leadgenzonevendor").value == "")
			b +="* Branch Name is required \n";
		if(document.getElementById("leadgencityvendor").value == "")
			b +="* Lead Generation City is required \n";
		alert(b);
		return false;
	}else if(val == "BRANCH" && (document.getElementById("branchCode2").value == "" || document.getElementById("branchName2").value == "" || document.getElementById("branchHead1").value == "" || document.getElementById("contactnobranch").value == "" || document.getElementById("leadgencitybranch").value == "")){
		var c = "";
		if(document.getElementById("branchCode2").value == "")
			var c ="* Branch code is required \n";
		if(document.getElementById("branchName2").value == "")
			c +="* Branch name is required \n";
		if(document.getElementById("branchHead1").value == "")
			c +="* Branch Executive name is required \n";
		if(document.getElementById("contactnobranch").value == "")
			c +="* Contact no. is required \n";
		if(document.getElementById("leadgencitybranch").value == "")
			c +="* Lead Generation City is required \n";
		alert(c);
		return false;
	}else
		return true;
}

function saveNewLead(id)
{
	var val = document.getElementById("leadGenerator").value;
	var sourcepath=document.getElementById("contextPath").value;
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
	}else{
	if(validateLeadCapturingDynaValidatorForm(document.getElementById("leadCapturingDetails")))
	{
		
		document.getElementById("leadCapturingDetails").action=sourcepath+"/leadCapturingBehindAction.do?method=saveNewLead&saveForward="+id;
		document.getElementById("leadCapturingDetails").submit();
		return true;
	
	}
	}
	}else
		return false;
}

function saveLeadDetails(id)
{
	var val = document.getElementById("leadGenerator").value;
	leadchange(document.getElementById('leadGenerator').value);
	var sourcepath=document.getElementById("contextPath").value;
	if(diffLeadValidation(val)){
	if(document.getElementById("status").value == '' && id == "Forward"){
		alert("You can't forward without saving");
	}else{
	if(validateLeadCapturingDynaValidatorForm(document.getElementById("leadCapturingDetails")))
	{
		
		document.getElementById("leadCapturingDetails").action=sourcepath+"/leadCapturingBehindAction.do?method=leadEntrySave&saveForward="+id;
		document.getElementById("leadCapturingDetails").submit();
		return true;
	
	}
	}
	}else
		return false;
}

function saveAllocation(id)
{
	if(document.getElementById("branchDet").value == "" || document.getElementById("rmAllo").value == ""){
		var la = "";
		if(document.getElementById("branchDet").value == "")
			la += "* Lead Allocation Branch is required \n";
		if(document.getElementById("rmAllo").value == "")
			la += "* Lead Allocation RM is required";
		alert(la);
		return false;
	}
	var sourcepath=document.getElementById("contextPath").value;
	if(validateLeadCapturingDynaValidatorForm(document.getElementById("leadAllocationDetails")))
	{
		document.getElementById("leadAllocationDetails").action=sourcepath+"/leadAllocatedBehindAction.do?method=saveAllocation&Allocated="+id;
		document.getElementById("leadAllocationDetails").submit();
		return true;
	}
}

function saveTrackingDetails(id,currBussDate){
	var formatD=document.getElementById("formatD").value;
	var currentBusinessDate = currBussDate;
	currentBusinessDate = getDateObject(currentBusinessDate,formatD.substring(2, 3));
	var expectedLoginDate= document.getElementById("expectedLoginDate").value;
	expectedLoginDate = getDateObject(expectedLoginDate,formatD.substring(2, 3));
	
	var expectedDisbursalDate=document.getElementById("expectedDisbursalDate").value;
	expectedDisbursalDate=getDateObject(expectedDisbursalDate,formatD.substring(2, 3));
	
	if(document.getElementById("decision").value == "Rejected"){
		if(document.getElementById("decision").value == "" || document.getElementById("remarks").value == ""){
			var lt = "";
			if(document.getElementById("decision").value == "")
				lt += "* Lead Decision is required \n";
			if(document.getElementById("remarks").value == "")
				lt += "* Maker remarks is required \n";
			alert(lt);
			return false;
		}
	}else {
		if(document.getElementById("decision").value == "" || document.getElementById("remarks").value == "" || document.getElementById("expectedLoginDate").value == "" || document.getElementById("expectedDisbursalDate").value == "" || (expectedLoginDate < currentBusinessDate)){
			var lt = "";
			if(document.getElementById("expectedLoginDate").value == "")
				lt += "* Expected Login Date is required \n";
			if(document.getElementById("expectedDisbursalDate").value == "")
				lt += "* Expected Disbursal Date is required \n";
			if(document.getElementById("decision").value == "")
				lt += "* Lead Decision is required \n";
			if(document.getElementById("remarks").value == "")
				lt += "* Maker remarks is required \n";
			if(expectedLoginDate < currentBusinessDate)
				lt += "* Expected Login Date must be equal to or greater than Business Date \n";
			if(expectedLoginDate > expectedDisbursalDate)
				lt += "* Expected Disbursal Date must be greater than Expected Login Date";
			alert(lt);
			return false;
		}
		if(expectedLoginDate > expectedDisbursalDate){
				alert("* Expected Disbursal Date must be greater than Expected Login Date");
				return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("leadDecision").action=sourcepath+"/leadAllocatedBehindAction.do?method=saveTrackingDetails";
		document.getElementById("leadDecision").submit();
		return true;
}

function saveLeadNotepadData(alert1)
{
//	alert("Save");
	if(validateLeadNotepadDynaValidatorForm(document.getElementById("leadNotepadForm")))
	{
		if(document.getElementById("followUp").value=='N')
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("leadNotepadForm").action = contextPath+"/leadNotePadAction.do?method=saveNotepadData";
			document.getElementById("leadNotepadForm").submit();
		}
		else if(document.getElementById("followUp").value=='Y')
		{
			if(cpNotepadValidate())
			{
				var formatD=document.getElementById("formatD").value;
				var meetingDate = document.getElementById("date").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(meetingDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				if (dt2<dt1)
				{
					alert(alert1);
					return false;
				}
				var contextPath =document.getElementById('contextPath').value ;
				document.getElementById("leadNotepadForm").action = contextPath+"/leadNotePadAction.do?method=saveNotepadData";
				document.getElementById("leadNotepadForm").submit();
			}
		}
		return true;
	}
}

function searchLead(stage)
{
	
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
			document.getElementById("leadCapturing").submit();
			return true;
		}
		if(customerName !='' && customerName.length >= 3)
		{
			document.getElementById("leadCapturing").action=contextPath+"/leadCapturingSearchBehind.do?userId="+name;
			document.getElementById("leadCapturing").submit();
			return true;
		}else if(customerName == '')
		{
			document.getElementById("leadCapturing").action=contextPath+"/leadCapturingSearchBehind.do?userId="+name;
			document.getElementById("leadCapturing").submit();
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		return false;
	}
}

function leadTrack(stage)
{
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
			return true;
		}
		if(customerName !='' && customerName.length >= 3)
		{
			document.getElementById("leadTracking").action=contextPath+"/leadTrackingSearch.do?method=searchDealCapturing&stage="+stage+"&userId="+name;
			document.getElementById("leadTracking").submit();
			return true;
		}else if(customerName == ''){
			document.getElementById("leadTracking").action=contextPath+"/leadTrackingSearch.do?method=searchDealCapturing&stage="+stage+"&userId="+name;
			document.getElementById("leadTracking").submit();
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		return false;
	}
}

function disableDate(){
	if(document.getElementById("decision").value == "Rejected")	{
		document.getElementById("expectedLoginDate").readOnly  = true;
		document.getElementById("expectedDisbursalDate").readOnly = true;
		document.getElementById("expectedLoginDate").value = null ;
		document.getElementById("expectedDisbursalDate").value = null;
		document.getElementById("caseRejected").style.display = 'none';
		
	}else {
		document.getElementById("expectedLoginDate").readOnly  = false;
		document.getElementById("expectedDisbursalDate").readOnly = false;
		document.getElementById("caseRejected").style.display = '';
	}
}

