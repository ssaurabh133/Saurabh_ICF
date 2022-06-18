	function fetchSubIndustry(){

	var industry=document.getElementById("industry").value;
//alert("fetchSubIndustry"+industry);
 if (industry!= '') {
	var address = "corporateDetailAjaxAction.do?method=displaySubIndustry";
	var data = "industry=" + industry;
	send_fetchSubIndustry(address, data);
	return true;
}
 else
 {
	 alert("Please Select List");	
	 return false;
 }
}

	function send_fetchSubIndustry(address, data) {
//alert("send_fetchSubIndustry"+address);
var request = getRequestObject();
request.onreadystatechange = function () {
	result_fetchSubIndustry(request);
};
//alert("send_countryDetail"+address);
request.open("POST", address, true);
request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
request.send(data);
}
	
	function result_fetchSubIndustry(request){

if ((request.readyState == 4) && (request.status == 200)) {
	var str = request.responseText;
	//alert(str);
	//var s1 = str.split("$:");
	//alert(s1);
  //  if(str.length>0)
  //  {
    	//alert(trim(str[0]));
    	document.getElementById('subIndustrydetail').innerHTML = str;
//	}
}
}

	function corporateClear()
{
	//alert("hello Clear");
	
	document.getElementById("corporateName").value='';
	//document.getElementById("middleName").value='';
	//document.getElementById("lastName").value='';
	//document.getElementById("customerCategory").value='';
	document.getElementById("incorporationDate").value='';
	document.getElementById("constitution").value='';
	//document.getElementById("registrationType").value='';
	document.getElementById("registrationNo").value='';
	document.getElementById("pan").value='';
	document.getElementById("vatRegNo").value='';
	document.getElementById("businessSegment").value='';
	document.getElementById("industry").value='';
	document.getElementById("subIndustry").value='';
	document.getElementById("institutionEmail").value='';
	document.getElementById("webAddress").value='';
	document.getElementById("referredBy").value='';
	//document.getElementById("blackListed").value='';
	//document.getElementById("reasonForBlackListed").value='';
	
	
}

	function upperMe(compId) { 
    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
    return true;
}
	
	function yearsInBusiness()
	{
		// alert('In yearsInBusiness!');		
	    var txtDateValue = document.getElementById('incorporationDate').value;
	    var bsnDate = document.getElementById('businessdate').value;
	    var year = txtDateValue.substring(6, 10);
	    var businessYear  = bsnDate.substring(6, 10);
	    document.getElementById('yearOfEstb').value=(businessYear-year);
	}

//Ravindra 02-may-2010.........................................................................
	function saveCorporateDetails(){

	DisButClass.prototype.DisButMethod();
	
	var contextPath = document.getElementById("contextPath").value;
	
	var corporateCode = document.getElementById("corporateCode");
	// Start by Prashant
	var groupType = document.getElementById("groupType").value;
	if(groupType=='E')
	{
		var groupName = document.getElementById("groupName");
	}
	else
	{
		var groupName = document.getElementById("groupNameText");
	}
	// End by Prashant
	var corporateName = document.getElementById("corporateName");
	var constitution = document.getElementById("constitution");
	var registrationNo = document.getElementById("registrationNo");
	var pan = document.getElementById("pan");
	var businessSegment = document.getElementById("businessSegment");
	var industry = document.getElementById("industry");
	var subIndustry = document.getElementById("subIndustry");
	var institutionEmail = document.getElementById("institutionEmail").value;
	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
	var corporateCategory = document.getElementById("corporateCategory");
	var incorporationDate = document.getElementById("incorporationDate");
	var natureOfBus = document.getElementById("natureOfBus");
	var hiApplType = document.getElementById("hiApplType").value;//12
	var riskCategory = document.getElementById('riskCategory');
	
	if(riskCategory){
		riskCategory = riskCategory.value;
		if(riskCategory==''){
			alert("Risk Category is Required!");
			DisButClass.prototype.EnbButMethod();
    		return false;
		}
	}
	
	
	
	if(constitution.value!='PROPRIETOR' && pan.value == ''){
		alert("Pan number is required!");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if(hiApplType!='COAPPL')
	{
		if(groupName.value == ''||corporateName.value == ''||constitution.value == ''||registrationNo.value == ''||businessSegment.value == ''||industry.value == ''||subIndustry.value == '' || institutionEmail == '' ||corporateCategory.value == '' ||(incorporationDate.value=='' || incorporationDate.value=='00-00-0000') || natureOfBus.value==''){
		var msg= '';
		var msg= '';
		if(groupName.value == '')
			msg += '* Group Name is required.\n';
		if(corporateName.value == '')
			msg += '* Customer Name is required.\n';
		/*if(corporateCategory.value == '')
			msg += '* Customer Category is required.\n';*/
		if(constitution.value == '')
			msg += '* Constitution is required.\n';
		if(registrationNo.value == '')
			msg += '* Registration No is required.\n';
		if(businessSegment.value == '')
			msg += '* Business Segment is required.\n';
		if(industry.value == '')
			msg += '* Industry is required.\n';
		if(subIndustry.value == '')
			msg += '* Sub-industry is required.\n';
		if(incorporationDate.value == '')
			msg += '* Date of Incorporation is required.\n';
	
		if(msg.match("Group")){
			document.getElementById("groupButton").focus();
		}else if(msg.match("Corporate")){
			corporateName.focus();
		}else if(msg.match("Customer")){
			corporateCategory.focus();
		}else if(msg.match("Constitution")){
			constitution.focus();
		}else if(msg.match("Registration")){
			registrationNo.focus();
		}else if(msg.match("Business")){
			businessSegment.focus();
		}else if(msg.match(/Industry/g)){
			document.getElementById("industryButton").focus();
		}else if(msg.match("Sub-industry")){
			document.getElementById("subIndustryButton").focus();
		}else if(msg.match("Incorporation")){
			incorporationDate.focus();
		}
		if(msg != '')
		{
			alert(msg);
	//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	}
	if(hiApplType=='COAPPL' )
	{
		
		
		if(groupName.value == ''||corporateName.value == ''||constitution.value == ''||registrationNo.value == ''/* ||pan.value == '' */||businessSegment.value == ''||industry.value == ''||subIndustry.value == '' || institutionEmail == '' ||corporateCategory.value == '' ||(incorporationDate.value=='' || incorporationDate.value=='00-00-0000') || natureOfBus.value==''){
		var msg= '';
		var msg= '';
		if(groupName.value == '')
			msg += '* Group Name is required.\n';
		if(corporateName.value == '')
			msg += '* Customer Name is required.\n';
	
		if(constitution.value == '')
			msg += '* Constitution is required.\n';
		if(registrationNo.value == '')
			msg += '* Registration No is required.\n';
		/* if(pan.value == '')
			msg += '* PAN is required.\n'; */
		if(businessSegment.value == '')
			msg += '* Business Segment is required.\n';
		if(industry.value == '')
			msg += '* Industry is required.\n';
		if(subIndustry.value == '')
			msg += '* Sub-industry is required.\n';
		if(incorporationDate.value == '')
			msg += '* Date of Incorporation is required.\n';
	
	

		if(msg.match("Group")){
			document.getElementById("groupButton").focus();
		}else if(msg.match("Corporate")){
			corporateName.focus();
		}else if(msg.match("Customer")){
			corporateCategory.focus();
		}else if(msg.match("Constitution")){
			constitution.focus();
		}else if(msg.match("corporateCategory")){
			corporateCategory.focus();
		}else if(msg.match("Registration")){
			registrationNo.focus();
		}/* else if(msg.match("PAN")){
			pan.focus();
		} */else if(msg.match("Business")){
			businessSegment.focus();
		}else if(msg.match(/Industry/g)){
			document.getElementById("industryButton").focus();
		}else if(msg.match("Sub-industry")){
			document.getElementById("subIndustryButton").focus();
		}else if(msg.match("Incorporation")){
			document.getElementById("incorporationDate").focus();
		}else if(msg.match("Nature of Business")){
			document.getElementById("natureOfBus").focus();
		}
		if(msg != '')
		{
			alert(msg);
	//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	}
  	if(validate(document.getElementById("corporateDetailForm"))) 
  	{
  		if(registrationNo.value != ''){
			if (!ck_alphaNumeric.test(registrationNo.value)) {
				registrationNo.value = '';
				alert("Registration No is not valid.");
//				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
		}
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
  		
  		document.getElementById("corporateDetailForm").action=contextPath+"/corporateDetailProcessAction.do?method=saveCorporateDetails&bypassDedupe="+bypassDedupe+"constitution="+constitution;
  		document.getElementById("processingImage").style.display = '';
 		document.getElementById("corporateDetailForm").submit();
 		return true;	
  	}
  	else
  	{
//  		document.getElementById("save").removeAttribute("disabled","true");
  		DisButClass.prototype.EnbButMethod();
  	  	return false; 
    }
} 

    function saveAndForwardCorpDetails()
    {
    	var contextPath = document.getElementById("contextPath").value;
    	//alert("in saveAndForwardCorpDetails");
    	document.getElementById("saveAndForward").action=contextPath+"/corporateDetailProcessAction.do?method=saveAndForwardCorpDetails";
    	document.getElementById("processingImage").style.display = '';
    	document.getElementById("saveAndForward").submit();
    }
    
    function updateCorporateDetails()
    {
    	DisButClass.prototype.DisButMethod();
    	
    	var contextPath = document.getElementById("contextPath").value;
    	var san_url = '|^http(s)?://[a-z0-9-]+(.[a-z0-9-]+)*(:[0-9]+)?(/.*)?$|i';

    	var corporateCode = document.getElementById("corporateCode");
    	// Start by Prashant
    	var groupType = document.getElementById("groupType").value;
    	if(groupType=='E')
    	{
    		var groupName = document.getElementById("groupName");
    	}
    	else
    	{
    		var groupName = document.getElementById("groupNameText");
    	}
    	// End by Prashant
    	var corporateName = document.getElementById("corporateName");
    	var constitution = document.getElementById("constitution");
    	var registrationNo = document.getElementById("registrationNo");
    	var pan = document.getElementById("pan");
    	var businessSegment = document.getElementById("businessSegment");
    	var industry = document.getElementById("industry");
    	var subIndustry = document.getElementById("subIndustry");
    	var institutionEmail = document.getElementById("institutionEmail").value;
    	var emailMandatoryFlag=document.getElementById("emailMandatoryFlag").value;
    	var riskCategory = document.getElementById('riskCategory');
    	var incorporationDate = document.getElementById('incorporationDate');
		
		if(riskCategory){
			riskCategory = riskCategory.value;
			if(riskCategory==''){
				alert("Risk Category is Required!");
				DisButClass.prototype.EnbButMethod();
	    		return false;
			}
		}
    	//var corporateCategory = document.getElementById("corporateCategory");
		//var institutionEmail = document.getElementById("institutionEmail");
    	if(constitution.value!='PROPRIETOR' && pan.value == ''){
    		alert("Pan number is required!");
    		DisButClass.prototype.EnbButMethod();
    		return false;
    	}
    	if(groupName.value == ''||corporateName.value == ''||constitution.value == ''||registrationNo.value == ''||businessSegment.value == ''||industry.value == ''||subIndustry.value == '' || institutionEmail == ''||incorporationDate.value == ''){
    		var msg= '';
    		if(groupName.value == '')
    			msg += '* Group Name is required.\n';
    		if(corporateName.value == '')
    			msg += '* Corporate Name is required.\n';
    	/*	if(corporateCategory.value == '')
    			msg += '* Customer Category is required.\n'; */
    		if(constitution.value == '')
    			msg += '* Constitution is required.\n';
    		if(registrationNo.value == '')
    			msg += '* Registration No is required.\n';
    		if(businessSegment.value == '')
    			msg += '* Business Segment is required.\n';
    		if(industry.value == '')
    			msg += '* Industry is required.\n';
    		if(subIndustry.value == '')
    			msg += '* Sub-industry is required.\n';
    		if(incorporationDate.value == '')
    			msg += '* Date of Incorporation is required.\n';
    		
    		if(msg.match("Group")){
    			document.getElementById("groupButton").focus();
    		}else if(msg.match("Corporate")){
    			corporateName.focus();
    		}else if(msg.match("Customer")){
    			corporateCategory.focus();
    		}else if(msg.match("Constitution")){
    			constitution.focus();
    		}else if(msg.match("Registration")){
    			registrationNo.focus();
    		}else if(msg.match("Business")){
    			businessSegment.focus();
    		}else if(msg.match(/Industry/g)){
    			document.getElementById("industryButton").focus();
    		}else if(msg.match("Sub-industry")){
    			document.getElementById("subIndustryButton").focus();
    		}else if(msg.match("Incorporation")){
    			incorporationDate.focus();
    		}
    		if(msg != '')
    		{
	    		alert(msg);
	//    		document.getElementById("save").removeAttribute("disabled","true");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
    		}
    	}
    	if(validate(document.getElementById("corporateDetailForm"))) 
      	{
    		if(registrationNo.value != ''){
    			if (!ck_alphaNumeric.test(registrationNo.value)) {
    				registrationNo.value = '';
    				alert("Registration No is not valid.");
//    				document.getElementById("save").removeAttribute("disabled","true");
    				DisButClass.prototype.EnbButMethod();
    				return false;
    				}
    		}
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
	    	document.getElementById("corporateDetailForm").action=contextPath+"/corporateDetailProcessAction.do?method=updateCorporateDetails&bypassDedupe="+bypassDedupe+"constitution="+constitution;
	    	document.getElementById("processingImage").style.display = '';
	    	document.getElementById("corporateDetailForm").submit();
	    	return true;
      	}
//    	document.getElementById("save").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
    	return false; 	 
    }
    
    function blacklist()
    {
    if(document.getElementById("blackListed").checked == false)
    {                        
    document.getElementById("reasonForBlackListed").disabled = true;
    document.getElementById("reasonForBlackListed").value="";
    }
    else if(document.getElementById("blackListed").checked == true)
    {
    document.getElementById("reasonForBlackListed").disabled = false;
    }
    }
    
    function urlValidateF(theurl){
    	var tomatch= /http:\/\/[A-Za-z0-9\.-]{3,}\.[A-Za-z]{3}/
    	if (tomatch.test(theurl)){
    		alert("valid");
    	return true;
    	} else {
    		alert("false");
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
    	var webAddress = document.getElementById("webAddress").value;
	     
	     
	    var errors = [];
	    var pan = $("#pan").val();
	    if(pan != '')
	    {
			var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
			if (!regex1.test(pan) || pan.length != 10) {
				alert("Invalid Pan No.");
				$("#pan").attr("value","");
				$("#pan").focus();
				return false;
			}
	    }
	    /*if (!ck_numeric.test(pan)) {
		   	  errors[errors.length] = "* PAN No. should be 10 digits.";
		 } */
		 if(institutionEmail != "") { 
		    if (!san_email.test(institutionEmail)) {
			   	  errors[errors.length] = "* Institution Email is not valid.";
			 }
	   	}
//		 if(webAddress != "") { 
//			    if (!san_url.test(webAddress)) {
//				   	  errors[errors.length] = "* Web address is not valid.";
//				 }
//		   	}
	    
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
	    if(msg.match("No.")){
	   	 document.getElementById('pan').focus();
	   	}else if(msg.match("Institution")){
	   		document.getElementById('institutionEmail').focus();
	   	}else if(msg.match("Web")){
	   		document.getElementById('webAddress').focus();
	   	}
	    alert(msg);
	    document.getElementById("save").removeAttribute("disabled","true");
	   }
	   
	// Start By Prashant   
	   function groupSelectInMaster(){
			var groupType=document.getElementById("groupType").value;
			if(groupType=='E'){
				document.getElementById("groupLov").style.display="";
				document.getElementById("groupText").style.display="none";	
				document.getElementById("groupNameText").value='';
				document.getElementById("hGroupId").value='';
				document.getElementById("groupName").value='';
				document.getElementById("groupNameText").setAttribute("disabled", true);
				document.getElementById("groupName").removeAttribute("disabled");
				document.getElementById("hGroupId").removeAttribute("disabled");
			

			}else if(groupType=='N'){
			
				
				document.getElementById("groupText").style.display="";
				document.getElementById("groupLov").style.display="none";
				document.getElementById("groupNameText").value='';
				document.getElementById("hGroupId").value='';
				document.getElementById("groupName").value='';
				document.getElementById("groupNameText").removeAttribute("disabled", true);
				document.getElementById("groupName").setAttribute("disabled", true);
				document.getElementById("hGroupId").setAttribute("disabled", true);

				
				 return true;
			}else{
				document.getElementById("groupText").style.display="none";
				document.getElementById("groupLov").style.display="none";
			}
		}
	   
	   function activeSupplier(val)
	   {
	   	//alert("val : "+val);
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
	   //Nishant space starts
	   function checkYear()
	   {
		   var year=document.getElementById("yearOfEstb").value;
		   if(!(year>=1900 && year<=2022))
		   {
			   alert("Invalid Year");
			   document.getElementById("yearOfEstb").value="";
			   document.getElementById('yearOfEstb').focus();
		   }
	   }
	   //Nishant space end
	   
	   
	   //ravi start
	   function saveBusiDesc()
		{ 
			DisButClass.prototype.DisButMethod();
			var summary = document.getElementById('businessDesc');	
			var summaryLength=summary.value.length;
			
			if(summaryLength>2000){
				alert("Description Length should be less than 2000 characters");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if (trim(summary.value) == "" ) {
				var msg1 = "";
				if(trim(summary.value)  == ""){
					msg1= "* Summary is required \n";
					
				}
				
				if(msg1!='')
				{
					alert(msg1);
					if(msg1!='')
					{
						document.getElementById('businessDesc').focus();
					}
					
//					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				 
			}else
			{
				document.getElementById("businessDescirptionForm").action="saveCorporateBusinessDescirption.do?method=saveCorporateBusinessDescirption";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("businessDescirptionForm").submit();
				return true;
			}
		}

	   //ravi end
	   
function hideShowManFieldsForCorporate()
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

function getIndutryPercent(){
	
		var natureOfBus=document.getElementById("natureOfBus").value;
		var lbxIndustry=document.getElementById("lbxIndustry").value;
		var contextPath = document.getElementById("contextPath").value;
		if(natureOfBus!=''){
			if(lbxIndustry!=''){
		var address = contextPath+"/ajaxAction.do?method=getIndustryPercent";
		var data = "natureOfBus="+natureOfBus+"&lbxIndustry="+lbxIndustry;
		sendRequestCDAccount(address,data);
		return true;
			}else{
				alert("Please select Industry first ");
			}
			
		}else{
			alert("Please select Nature of Business first ");
		}
		
}
function sendRequestCDAccount(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodCDAccount(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodCDAccount(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
				document.getElementById('industryPercent').value=trim(s1[0]);
	    }
		
	}
}