
function upperMe(compId) { 
    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
    return true;
}

function countryDetail(){
	
	var country =document.getElementById('country').value ;
	//alert("addressDetail"+country);
     if (country!= '') {
		var address = "customerAddressAjaxAction.do?method=getCountry";
		var data = "country=" + country;
		send_countryDetail(address, data);
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function send_countryDetail(address, data) {
	//alert("send_countryDetail"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_countryDetail(request);
	};
	//alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_countryDetail(request){

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert(str);
		//var s1 = str.split("$:");
		//alert(s1);
	  //  if(str.length>0)
	  //  {
	    	//alert(trim(str[0]));
	    	document.getElementById('statedetail').innerHTML = str;
	//	}
	}
}

function cityDetail()
{
	
	var country =document.getElementById('country').value ;
	var state =document.getElementById('state').value ;
	//alert("cityDetail"+state+"country :"+country);
     if (state!= '') {
		var address = "customerAddressAjaxAction.do?method=getCity";
		var data = "state="+state+"&country="+country;
		send_cityDetail(address, data);
		return true;
	}
     else
     {
    	// alert("Please Select List");	
    	 return false;
     }
}

function send_cityDetail(address, data) {
	//alert("send_relation_nominee");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_cityDetail(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_cityDetail(request){
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
//		alert(str);
		var s1 = str.split("$:");
		//alert(s1);
	    if(s1.length>0)
	    {
	    	//alert(trim(s1[0]));
	    	//alert(trim(s1[1]));
	    	//alert(trim(s1[2]));
	    	document.getElementById('cityID').innerHTML =trim(s1[0]);
	    	document.getElementById('textregion').value =trim(s1[1]);
	    	document.getElementById('region').value =trim(s1[2]);
		}
	}
}

function insert(mobileCheckPass)
{ 
	DisButClass.prototype.DisButMethod();
	
	var addr_type = document.getElementById('addr_type');
	var addr1 = document.getElementById('addr1');
	var country = document.getElementById('country');
	var state = document.getElementById('state');
	var dist = document.getElementById('dist');
	var pincode = document.getElementById('pincode');
	var noYears = document.getElementById('noYears');
	var primaryPhoneNo = document.getElementById('primaryPhoneNo').value;
	var alternatePhoneNo = document.getElementById('alternatePhoneNo').value;
	var tollfreeNo = document.getElementById('tollfreeNo').value;
	var faxNo = document.getElementById('faxNo').value;
	var mobileNo = document.getElementById('primaryPhoneNo');
	var stdNo = document.getElementById('stdNo').value;
	
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Mobile No. should be start from 6/7/8/9 ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	
	if(stdNo!=''){
		var str = stdNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a!="0"){
			var a = str.substring(0, 2);
			//alert(a);
			if(a!="91"){
				alert("STD code should be start from 0 or 91 ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
	}
	if(alternatePhoneNo!='' && stdNo==''){
		alert("STD No. is mandatory if Landline is captured");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(alternatePhoneNo=='' && stdNo!=''){
		alert("Landline is mandatory if STD No. is captured");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

	   var gst = $("#gstIn").val();
	    if(gst != '')
	    {
	    	//11aaaaa1111aaa1
			var regex1 = /\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}[Z]{1}[A-Z\d]{1}/;
		//	var regex1 = /\d{2}[A-Z]{5}\d{4}[A-Z]{1}\d{1}/;
			if (!regex1.test(gst) || gst.length != 15) {
				alert("Invalid GSTIN No.");
				$("#gstIn").attr("value","");
				$("#gstIn").focus();
			//	document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	
	if (trim(addr_type.value) == "" || trim(addr1.value) == "" || trim(country.value) == "" || trim(state.value) == "" || trim(dist.value) == "" || 
			trim(pincode.value) == "" || trim(noYears.value) == "" || trim(mobileNo.value) == "") {
		var a = "";
		if(trim(addr_type.value)  == ""){
			a= "* Address Type is required \n";
			
		}
		if(trim(addr1.value) == ""){
			a += "* Address Line1 is required \n";
	
		}
		if(trim(country.value) == ""){
			a += "* Country is required \n";
	
		}
		if(trim(state.value) == ""){
			a +="* State/Providence is required \n";
			
		}
		if(trim(dist.value)  == ""){
			a +="* District is required \n";
			
		}
		if(trim(pincode.value)  == ""){
			a +="* Pincode is required \n";
			
		}
		if(trim(mobileNo.value)  == ""){
			a +="* Mobile No. is required \n";
			
		}
		if(trim(noYears.value)  == ""){
			a +="* No of years at this address is required \n";
			
		}
		
		if(a.match(/Type/gi)){
			addr_type.focus();
		}else if(a.match(/Line1/gi)){
			addr1.focus();
		}else if(a.match(/Country/gi)){
			document.getElementById('countryButton').focus();
		}else if(a.match(/State/gi)){
			document.getElementById('stateButton').focus();
		}else if(a.match(/District/gi)){
			document.getElementById('districtButton').focus();
		}else if(a.match(/Pincode/gi)){
			pincode.focus();
		}else if(a.match(/Mobile/gi)){
			mobileNo.focus();
		}else if(a.match(/this/gi)){
			noYears.focus();
		}
		alert(a);
//		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		 
	}else{
		if(pincode.value != '' || primaryPhoneNo != '' || alternatePhoneNo != '' || tollfreeNo != '' || faxNo != ''){
			if(!validate("customerForm")){
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}
		
		
		
		var decide=confirm("Would You like to make it Communication Address!!");
		if(decide==true){
			var chTest=1;		
		}
		else {
			var chTest=0;		
		}
			if(document.getElementById("hiddenIndividualId").value!='')
			{
				document.getElementById("customerForm").action="customerAddressAction.do?method=save_individual_address&chTest="+chTest+"&mobileCheckPass="+mobileCheckPass;
				document.getElementById("processingImage").style.display = '';
			 	document.getElementById("customerForm").submit();
			 	
			}
			else 
			{
				document.getElementById("customerForm").action="customerAddressAction.do?method=save_address&chTest="+chTest+"&mobileCheckPass="+mobileCheckPass;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("customerForm").submit();
				
			 	
			}
	 	return true;
	}
}


function insertRef()
{ 
	DisButClass.prototype.DisButMethod();
	var firstName = document.getElementById('firstName');
	var lastName = document.getElementById('lastName');
	var knowingSince = document.getElementById('knowingSince');
	var relationshipS = document.getElementById('relationshipS');
	var primaryRefMbNo  = document.getElementById('primaryRefMbNo');
	var alternateRefPhNo = document.getElementById('alternateRefPhNo');
	var institutionEmail = document.getElementById('institutionEmail');
	
	if (trim(firstName.value) == "" || trim(lastName.value) == ""  || trim(knowingSince.value) == "" || trim(relationshipS.value) == "" || primaryRefMbNo.value == "") {
		var msg1 = "",msg3="",msg2="",msg4="",msg5="";
		if(trim(firstName.value)  == ""){
			msg1= "* First Name is required \n";
			
		}
		if(trim(lastName.value) == ""){
			msg2= "* Last Name is required \n";
	
		}
		if(trim(knowingSince.value)  == ""){
			msg3="* Knowing Since is required \n";
			
		}
		if(trim(relationshipS.value)  == ""){
			msg4="* Relationship is required \n";
		}
		if(trim(primaryRefMbNo.value)  == ""){
			msg5="* Mobile No. is required \n";
			
		}
		
		if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!='')
		{
			alert(msg1+msg2+msg3+msg4+msg5);
			if(msg1!='')
			{
				document.getElementById('firstName').focus();
			}
			else if(msg2!='')
			{
				document.getElementById('lastName').focus();
			}
			else if(msg3!='')
			{
				document.getElementById('knowingSince').focus();
			}
			else if(msg4!='')
			{
				document.getElementById('relationshipS').focus();
			}
			else if(msg5!='')
			{
				document.getElementById('primaryRefMbNo').focus();
			}
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		 
	}else{
			if(primaryRefMbNo.value != '' || alternateRefPhNo.value != '' || institutionEmail.value!=''){
				if(!validateRef("customerForm")){
//					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			if(document.getElementById("bp_id1").value!=''){
			document.getElementById("customerForm").action="customerAddressAction.do?method=save_individual_reference";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("customerForm").submit();
			}else{
			document.getElementById("customerForm").action="customerAddressAction.do?method=save_corporate_reference";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("customerForm").submit();
			}
		return true;
	}
}
//Rohit Change Starts fro Sarva Suraksha
function insertSarva()
{ 
	DisButClass.prototype.DisButMethod();
	var nominee = document.getElementById('nominee');
	var relationshipS = document.getElementById('relationshipS');
	var nomineeDOB  = document.getElementById('incorporationDate');

	
	
	if (trim(nominee.value) == ""|| trim(relationshipS.value) == "" || nomineeDOB.value == "") {
		var msg1 = "",msg3="",msg2="";
		if(trim(nominee.value)  == ""){
			msg1= "* Nominee Name is required \n";
			
		}
	
	
		if(trim(relationshipS.value)  == ""){
			msg2="* Relationship is required \n";
			
		}
		if(trim(nomineeDOB.value)  == ""){
			msg3="* Nominee DOB is required \n";
			
		}
		
		if(msg1!=''||msg2!=''||msg3!='')
		{
			alert(msg1+msg2+msg3);
			if(msg1!='')
			{
				document.getElementById('nominee').focus();
			}
			else if(msg2!='')
			{
				document.getElementById('relationshipS').focus();
			}
			else if(msg3!='')
			{
				document.getElementById('incorporationDate').focus();
			}
			
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		 
	}else{
			
			if(document.getElementById("bp_id1").value!=''){
			document.getElementById("customerForm").action="customerAddressAction.do?method=save_Sarva_Suraksha";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("customerForm").submit();
			}else{
			document.getElementById("customerForm").action="customerAddressAction.do?method=save_corporate_SravaSuraksha";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("customerForm").submit();
			}
		return true;
	}
}
//Rhit Changes end for sarva suraksha
function allClear()
{
	//alert("hello Clear");
	document.getElementById("addr_type").value='';
	document.getElementById("addr1").value='';
	document.getElementById("addr2").value='';
	document.getElementById("addr3").value='';
	document.getElementById("country").value='';
	document.getElementById("state").value='';
	//document.getElementById("reason").value='';
	document.getElementById("dist").value='';
	document.getElementById("pincode").value='';
	document.getElementById("primaryPhoneNo").value='';
	document.getElementById("alternatePhoneNo").value='';
	document.getElementById("tollfreeNo").value='';
	document.getElementById("faxNo").value='';landMark
	document.getElementById("landMark").value='';
	document.getElementById("noYears").value='';
	document.getElementById("noMonths").value='';
	document.getElementById("addDetails").value='';
	return false;
}

function allClearRef()
{
	//alert("hello Clear");
	document.getElementById('firstName').value='';
	document.getElementById('middleName').value='';
	document.getElementById('lastName').value='';
	document.getElementById('relationshipS').value='';
	document.getElementById('primaryRefMbNo').value='';
	document.getElementById('alternateRefPhNo').value='';
	document.getElementById('knowingSince').value='';
	document.getElementById('addRef').value='';
	return false;
}
function allClearSarva()
{
	
	document.getElementById('nominee').value='';
	document.getElementById('relationshipS').value='';
	document.getElementById('incorporationDate').value='';

	return false;
}

function stakeHolderClear()
{
	document.getElementById("sex").value='';
	document.getElementById("holderName").value='';
	document.getElementById("holderType").value='';
	document.getElementById("holdingPerc").value='';
	document.getElementById("dob").value='';
	document.getElementById("totalExp").value='';
	document.getElementById("dinNo").value='';
	document.getElementById("idNo").value='';
	document.getElementById("primaryPhone").value='';
	document.getElementById("alternatePhone").value='';
	document.getElementById("primaryEmail").value='';
	document.getElementById("alternateEmail").value='';
	document.getElementById("website").value='';
	document.getElementById("position").value='';
	document.getElementById("alternateStd").value='';
	document.getElementById("doj").value='';
	document.getElementById("compute").value='';
	
}

function deleteStake()
{
	    //alert("ok"); 
		
	    if(check())
	    {
			document.getElementById("StakeForm").action="deleteStakeHolderAction.do?method=delete_stakeHolder";
		 	document.getElementById("StakeForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    }
}
	
function creditClear()
{
	document.getElementById("institute").value='';
	document.getElementById("rating").value='';
	document.getElementById("creditDate").value='';
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

function checkDate1()
{
	var formatD=document.getElementById("formatD").value;
	var incDate = document.getElementById("incDate").value;
	var dob = document.getElementById("dob").value;
	var doj = document.getElementById("doj").value;
	
	if(incDate!='' && dob!='' && doj!='')
	{
		date1=getDateObject(dob,formatD.substring(2, 3));
		date2=getDateObject(doj,formatD.substring(2, 3));
		date3=getDateObject(incDate,formatD.substring(2, 3));
		if(date1<=date2 && date3<=date2)
		{
			return true;
		}
		else
		{
			alert("Please Select Joining Date Equal to or Greater than Incorporation Date and Date of Birth");
			document.getElementById("doj").value='';
			document.getElementById("dob").value='';
			return false;
		}
	}
}

function stakePerc()
{
	
	if(document.getElementById("holderType").value=='Share Holder')
	{
		document.getElementById("holdingPerc").removeAttribute('disabled','true');
		return true;
	}
	else
	{
		document.getElementById("holdingPerc").setAttribute('disabled','true');
		return false;
	}
}


function updateAddress(mobileCheckPass)
{   
	DisButClass.prototype.DisButMethod();
	
	var addr_type = document.getElementById('addr_type');
	var addr1 = document.getElementById('addr1');
	var country = document.getElementById('country');
	var state = document.getElementById('state');
	var dist = document.getElementById('dist');
	var pincode = document.getElementById('pincode');
	var noYears = document.getElementById('noYears');
	
	var primaryPhoneNo = document.getElementById('primaryPhoneNo').value;
	var alternatePhoneNo = document.getElementById('alternatePhoneNo').value;
	var tollfreeNo = document.getElementById('tollfreeNo').value;
	var faxNo = document.getElementById('faxNo').value;
	var mobileNo = document.getElementById('primaryPhoneNo');	
	var stdNo = document.getElementById('stdNo').value;
	
//alert(relation);
	if(primaryPhoneNo!=''){
		var str = primaryPhoneNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a=="6" || a=="7" || a=="8" || a=="9"){
		}else{
				alert("Mobile No. should be start from 6/7/8/9 ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
	if(stdNo!=''){
		var str = stdNo;
		var a = str.substring(0, 1);
		//alert(a);
		if(a!="0"){
			var a = str.substring(0, 2);
			//alert(a);
			if(a!="91"){
				alert("STD code should be start from 0 or 91 ");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
	}
	if(alternatePhoneNo!='' && stdNo==''){
		alert("STD No. is mandatory if Landline is captured");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(alternatePhoneNo=='' && stdNo!=''){
		alert("Landline is mandatory if STD No. is captured");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
			
	 var gst = $("#gstIn").val();
	    if(gst != '')
	    {
			var regex1 = /\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{1}[Z]{1}[A-Z\d]{1}/;
	    	//var regex1 = /\d{2}[A-Z]{5}\d{4}[A-Z]{3}\d{1}/;
			if (!regex1.test(gst) || gst.length != 15) {
				alert("Invalid GSTIN No.");
				$("#gstIn").attr("value","");
				$("#gstIn").focus();
			//	document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	    }
	
	
	//alert("11111"+trim(addr1.value)+"222   "+addr1.value+"333");
	
	if (trim(addr_type.value) == "" || trim(addr1.value) == "" || trim(country.value) == "" || trim(state.value) == "" || trim(dist.value) == "" || trim(pincode.value) == "" || trim(noYears.value) == "" || trim(mobileNo.value) == "") {
		var a = "";
		if(trim(addr_type.value)  == ""){
			a= "* Address Type is required \n";
			
		}
		if(trim(addr1.value) == ""){
			a += "* Address Line1 is required \n";
	
		}
		if(trim(country.value) == ""){
			a += "* Country is required \n";
	
		}
		if(trim(state.value) == ""){
			a +="* State/Providence is required \n";
			
		}
		if(trim(dist.value)  == ""){
			a +="* District is required \n";
			
		}
		if(trim(pincode.value)  == ""){
			a +="* Pincode is required \n";
			
		}
		if(trim(mobileNo.value)  == ""){
			a +="* Mobile No. is required \n";
			
		}
		if(trim(noYears.value)  == ""){
			a +="* No of years at this address is required \n";
			
		}
		
		if(a.match(/Type/gi)){
			addr_type.focus();
		}else if(a.match(/Line1/gi)){
			addr1.focus();
		}else if(a.match(/Country/gi)){
			document.getElementById('countryButton').focus();
		}else if(a.match(/State/gi)){
			document.getElementById('stateButton').focus();
		}else if(a.match(/District/gi)){
			document.getElementById('districtButton').focus();
		}else if(a.match(/Pincode/gi)){
			pincode.focus();
		}else if(a.match(/Mobile/gi)){
			mobileNo.focus();
		}else if(a.match(/this/gi)){
			noYears.focus();
		}
		
		alert(a);
//		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		if(pincode.value != '' || primaryPhoneNo != '' || alternatePhoneNo != '' || tollfreeNo != '' || faxNo != ''){
			if(!validate("customerForm")){
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}
			
		if(mobileCheckPass!='PASS')
		{
			var decide=confirm("Would You like to make it Communication Address!!");
			if(decide==true){
				var chTest=1;		
					}
			else {
				var chTest=0;		
			}
		}
		else{
			if(document.getElementById('communicationAddressCheckBox')==undefined || document.getElementById('communicationAddressCheckBox')==null)
			{
			var decide=document.getElementById("communicationAddress").value;
			if(decide=="on"){
				var chTest=1;		
					}
			else {
				var chTest=0;		
			}
			}
			else
				{
				var chTest=document.getElementById("communicationAddressCheckBox").value;
				}
		}
			var contextPath = document.getElementById("contextPath").value;
			document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=update_address&chTest="+chTest+"&mobileCheckPass="+mobileCheckPass;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("customerForm").submit();	
	 	
	 		return true;
	}
//	document.getElementById("save").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
}

function updateReference()
{   
	DisButClass.prototype.DisButMethod();
	var firstName = document.getElementById('firstName');
	var lastName = document.getElementById('lastName');
	var knowingSince = document.getElementById('knowingSince');
	var relationshipS = document.getElementById('relationshipS');
	var primaryRefMbNo  = document.getElementById('primaryRefMbNo');
	var alternateRefPhNo = document.getElementById('alternateRefPhNo');
	var institutionEmail = document.getElementById('institutionEmail');
	
	if (trim(firstName.value) == "" || trim(lastName.value) == ""  || trim(knowingSince.value) == "" || trim(relationshipS.value) == "" || primaryRefMbNo.value == "") {
		var msg1 = "",msg3="",msg2="",msg4="",msg5="";
		if(trim(firstName.value)  == ""){
			msg1= "* First Name is required \n";
			
		}
		if(trim(lastName.value) == ""){
			msg2= "* Last Name is required \n";
	
		}
		if(trim(knowingSince.value)  == ""){
			msg3="* Knowing Since is required \n";
			
		}
		if(trim(relationshipS.value)  == ""){
			msg4="* Relationship is required \n";
			
		}
		if(trim(primaryRefMbNo.value)  == ""){
			msg5="* Mobile No. is required \n";
			
		}
		
		if(msg1!=''||msg2!=''||msg3!=''||msg4!=''|| msg5!='')
		{
			alert(msg1+msg2+msg3+msg4+msg5);
			if(msg1!='')
			{
				document.getElementById('firstName').focus();
			}
			else if(msg2!='')
			{
				document.getElementById('lastName').focus();
			}
			else if(msg3!='')
			{
				document.getElementById('knowingSince').focus();
			}
			else if(msg4!='')
			{
				document.getElementById('relationshipS').focus();
			}
			else if(msg5!='')
			{
				document.getElementById('primaryRefMbNo').focus();
			}
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		
	}else{
		
		if(primaryRefMbNo.value != '' || alternateRefPhNo.value != '' || institutionEmail.value!=''){
			if(!validateRef("customerForm")){
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}			
		var contextPath = document.getElementById("contextPath").value;
		
		if(document.getElementById("bp_id1").value!='')
			{
		document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=update_Reference";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("customerForm").submit();
			}
		else{
		document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=update_corporate_Reference";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("customerForm").submit();
			}
	 	return true;
	}

}
// Rohit Changes  Starts for Sarva Suraksha
function updateSarvaSuraksha()
{   
	DisButClass.prototype.DisButMethod();
	var nominee = document.getElementById('nominee');
	var relationshipS = document.getElementById('relationshipS');
	var nomineeDOB  = document.getElementById('incorporationDate');
var refId= document.getElementById('refId').value;
	
	
	if (trim(nominee.value) == ""|| trim(relationshipS.value) == "" || nomineeDOB.value == "") {
		var msg1 = "",msg3="",msg2="";
		if(trim(nominee.value)  == ""){
			msg1= "* Nominee Name is required \n";
			
		}
	
	
		if(trim(relationshipS.value)  == ""){
			msg2="* Relationship is required \n";
			
		}
		if(trim(nomineeDOB.value)  == ""){
			msg3="* Nominee DOB is required \n";
			
		}
		
		if(msg1!=''||msg2!=''||msg3!='')
		{
			alert(msg1+msg2+msg3);
			if(msg1!='')
			{
				document.getElementById('nominee').focus();
			}
			else if(msg2!='')
			{
				document.getElementById('relationshipS').focus();
			}
			else if(msg3!='')
			{
				document.getElementById('incorporationDate').focus();
			}
			
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		
	}else{
		
				
		var contextPath = document.getElementById("contextPath").value;
		
		if(document.getElementById("bp_id1").value!='')
			{
		document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=update_SarvaSuraksha&refId="+refId;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("customerForm").submit();
			}
		else{
		document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=update_corporate_SarvaSuraksha&refId="+refId;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("customerForm").submit();
			}
	 	return true;
	}

}
//Rohit Changes end for Srava Suraksha

function deleteAddr()
{
	    //alert("ok"); 
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("customerForm").action="deleteAddressAction.do?method=delete_address";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("customerForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}

function deleteRef()
{
	    //alert("ok"); 
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("customerForm").action="deleteAddressAction.do?method=delete_reference";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("customerForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}


	
	//Ravindra 2 may 2011
//Rohit Changes for Sarva Suraksha Starts
function deleteSarvSurksha()
{
	    //alert("ok"); 
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("customerForm").action="deleteAddressAction.do?method=delete_SarvaSuraksha";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("customerForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}
//Rohit Changes for Sarva Suraksha end
	function modifyAddress(id)
	{	
		//alert("ok");
		var contextPath = document.getElementById("contextPath").value;
	    //if(checkBoxes())
	    //{	 
			document.getElementById("customerForm").action=contextPath+"/fetchCustomerAddressAction.do?method=fetchCustomerAddress&chk="+id;
		 	document.getElementById("customerForm").submit();
	    //}
	  
	}
	
	function modifyReference(id)
	{	
		//alert("ok");
		var contextPath = document.getElementById("contextPath").value;
	    //if(checkBoxes())
	    //{	 
			document.getElementById("customerForm").action=contextPath+"/fetchCustomerAddressAction.do?method=fetchIndividualReference&chk="+id;
		 	document.getElementById("customerForm").submit();
	    //}
	  
	}
	//Rohit Changes for Sava surakhsha Starts
		function modifySaravSuraksha(id)
	{	
		//alert("ok");
		var contextPath = document.getElementById("contextPath").value;
	    //if(checkBoxes())
	    //{	 
			document.getElementById("customerForm").action=contextPath+"/fetchCustomerAddressAction.do?method=fetchSarvaSuraksha&chk="+id;
		 	document.getElementById("customerForm").submit();
	    //}
	  
	}
		//Rohit Changes for Sava surakhsha Starts
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
	function clearCountryLovChildMaster()
	{
		window.opener.document.getElementById("state").value="";
	 	window.opener.document.getElementById("txtStateCode").value="";
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";	
	  	window.close();
	
	}
	function clearStateLovChildMaster()
	{
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";	 	
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
	
	function clearDistrictLovChild(){
		//alert("clearDistrictLovChild");
	 	window.opener.document.getElementById("tahsil").value="";
	 	window.opener.document.getElementById("tahsilDesc").value="";
	 	window.close();

	}
	
//Surendra Code Starts..
	
	function clearCountryLovChildNew()
	{		
		window.opener.document.getElementById("state").value="";
	 	window.opener.document.getElementById("txtStateCode").value="";
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";
	 	window.opener.document.getElementById("tahsil").value="";	 	
	 	window.close();	
	}
	
	function clearStateLovChildNew(){		
		window.opener.document.getElementById("dist").value="";
	 	window.opener.document.getElementById("txtDistCode").value="";
	 	window.opener.document.getElementById("tahsil").value="";	 	
	 	window.close();	
	}
	
	function clearDistrictLovChildNew(){		
		window.opener.document.getElementById("tahsil").value="";	 	
	 	window.close();

	}
		
//Surendra Code Ends.......	
	function clearBankBranchLovChild(){
		//alert("In Branch JS");
		
		window.opener.document.getElementById("branch").value="";
	 	window.opener.document.getElementById("lbxBranchID").value="";
	 	window.opener.document.getElementById("micr").value="";
	 	window.opener.document.getElementById("ifsCode").value="";
	 	window.close();

	}
	//Code for clear Tehsil LOV data  @Surendra
	function clearTahsilLovChild(){
		//alert("In Branch JS");
		
		window.opener.document.getElementById("tahsil").value="";
	 	window.opener.document.getElementById("txnTahsilHID").value="";
	 	window.close();

	}
	
	
	
	
	//-----------------------------------------VALIDATION--------------------------

	var ck_numeric = /^[A-Za-z0-9+-]{10,25}$/;
	var ck_pin = /^[A-Za-z0-9]{6,7}$/;
	 var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;


	function validate(formName){
		var pinCode = document.getElementById('pincode').value;
		var primaryPhoneNo = document.getElementById('primaryPhoneNo').value;
		var alternatePhoneNo = document.getElementById('alternatePhoneNo').value;
		var tollfreeNo = document.getElementById('tollfreeNo').value;
		var faxNo = document.getElementById('faxNo').value;
	  

	 var errors = [];
	 
	 if (!ck_pin.test(pinCode)) {
	  errors[errors.length] = "* Pin Code should be of 6 digits";
	 }
	  
	 if (!ck_numeric.test(primaryPhoneNo)) {
		 if(primaryPhoneNo != ''){
			 errors[errors.length] = "* Mobile No. not valid.";
		 }
	 }
	 /*if (!ck_numeric.test(alternatePhoneNo)) {
		 if(alternatePhoneNo != ''){
			 errors[errors.length] = "* Landline No. not valid.";
		 }
	 }*/

	 if (!ck_numeric.test(tollfreeNo)) {
		 if(tollfreeNo != ''){
			 errors[errors.length] = "* Toll Free No. not valid.";
		 }
	 }
	 if (!ck_numeric.test(faxNo)) {
		 if(faxNo != ''){
			 errors[errors.length] = "* Fax No. not valid.";
		 }
	}
	 
	 if (errors.length > 0) {
	  reportErrors(errors);
	  return false;
	 }
	 
	 return true;
	}
	
	function validateRef(formName)
	{
		var primaryRefMbNo  = document.getElementById('primaryRefMbNo').value;
		var alternateRefPhNo = document.getElementById('alternateRefPhNo').value;
		   var institutionEmail = document.getElementById("institutionEmail").value;
		var errors = [];
	 

	  
	 if (!ck_numeric.test(primaryRefMbNo)) {
		 if(primaryRefMbNo != ''){
			 errors[errors.length] = "* Mobile No. not valid.";
		 }
	 }
	 if(institutionEmail != "") { 
		    if (!san_email.test(institutionEmail)) {
			   	  errors[errors.length] = "* Institution Email is not valid.";
			 }
	   	}
	/* if (!ck_numeric.test(alternateRefPhNo)) {
		 if(alternateRefPhNo != ''){
			 errors[errors.length] = "* Landline No. not valid.";
		 }
	 }	 */
	 if (errors.length > 0) {
	  reportRefErrors(errors);
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
		 if(msg.match("Pin")){
			 document.getElementById('pincode').focus();
			}else if(msg.match("Mobile")){
				document.getElementById('primaryPhoneNo').focus();
			}else if(msg.match("Landline")){
				document.getElementById('alternatePhoneNo').focus();
			}else if(msg.match("Toll")){
				document.getElementById('tollfreeNo').focus();
			}else if(msg.match("Fax")){
				document.getElementById('faxNo').focus();
			}
		 alert(msg);
		 document.getElementById("save").removeAttribute("disabled","true");
		}

	function reportRefErrors(errors){
	 var msg = "";
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += "\n" + errors[i];
	 }
	 if(msg.match("Mobile")){
		 document.getElementById('primaryRefMbNo').focus();
		}else if(msg.match("Landline")){
			document.getElementById('alternateRefPhNo').focus()
		}else if(msg.match("Institution")){
	   		document.getElementById('institutionEmail').focus();
	   	}
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}
//Ritu Code Start
	function insertProfile()
	{ 
		DisButClass.prototype.DisButMethod();
		var customerName = document.getElementById('customerName');
		var summary = document.getElementById('customerProfile');	
		var summaryLength=summary.value.length;
	
		if(summaryLength>5000){
			alert("Summary Length is not greater than 5000 characters");
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
					document.getElementById('customerProfile').focus();
				}
				
//				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			 
		}else{
				
				if(document.getElementById("bp_id1").value!=''){
				document.getElementById("customerForm").action="customerAddressAction.do?method=save_individual_profile";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("customerForm").submit();
				}else{
				
				document.getElementById("customerForm").action="customerAddressAction.do?method=save_corporate_profile";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("customerForm").submit();
				}
			return true;
		}
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
	function relationShip(){
		var addId=document.getElementById('addId').value;
		var customerId=document.getElementById("bp_id").value;
		if(customerId==''){
			customerId=document.getElementById("bp_id1").value;
		}
		if(addId!=''){
			
			var contextPath =document.getElementById('contextPath').value ;
			var url=contextPath+"/customerAddressAction.do?method=openRelationShipTab&addId="+addId+"&customerId="+customerId+" ";
				newwindow=window.open(url,'relation','height=400,width=1100,toolbar=no,scrollbars=yes');
				if (window.focus) {newwindow.focus()}
				return false;
			}else{
			alert("Please select the address details");
			return false;
		}
	}
	function saveRelationDetails(){
		DisButClass.prototype.DisButMethod();
		var status=document.getElementsByName("relation");
		 for(i=1;i<=status.length;i++)
		 {
			if(document.getElementById("relation"+i).value==''){
				alert("Please capture relation for all Customers.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		 }
		 var addressId=document.getElementById("addressId").value;
		 var customerId=document.getElementById("bp_id").value;
			if(customerId==''){
				customerId=document.getElementById("bp_id1").value;
			}
		 	var res=document.getElementsByName("relation");
			var addId=document.getElementsByName("relationAddressId");
			var cusId=document.getElementsByName("relationCustomerId");
			
			var relationship='';
			var relationAddressId='';
			var relationCustomerId="";
//alert("status.length: "+status.length);
			for(var i=1; i<=status.length ; i++) 
			{
	//			alert("i: "+i);
				if(i==1){
					relationship = document.getElementById("relation"+i).value;
					relationAddressId=document.getElementById("relationAddressId"+i).value;	
					relationCustomerId=document.getElementById("relationCustomerId"+i).value;
				}else{
					relationship = relationship+","+document.getElementById("relation"+i).value;
					relationAddressId=relationAddressId+","+document.getElementById("relationAddressId"+i).value;	
					relationCustomerId=relationCustomerId+","+document.getElementById("relationCustomerId"+i).value;
				}
					
			}
			var contextPath = document.getElementById("contextPath").value;
			document.getElementById("customerForm").action=contextPath+"/customerAddressAction.do?method=saveRelationDetails&relationship="+relationship+"&relationAddressId="+relationAddressId+"&relationCustomerId="+relationCustomerId+"&customerId="+customerId+"&addressId="+addressId;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("customerForm").submit();	
	 	
	 		return true;
	}
	
	 //Shashank Starts For Mobile No. Verify
	function verifyMobile(){
		var addId=document.getElementById('addId').value;

		if(addId!=''){
		//alert(val);
		var primaryPhoneNo =document.getElementById('primaryPhoneNo').value ;
		if(primaryPhoneNo ==''){
			alert("Please save Mobile No. !");
			return false;
		}
		var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/mobileVerificationAction.do?method=openMobileVerification&primaryPhoneNo="+primaryPhoneNo+"&addId="+addId;
		//alert(url);
	newwindow=window.open(url,'Mobile','height=300,width=400,toolbar=no,scrollbars=yes');
	if (window.focus) {newwindow.focus()}
	return false;	
		}
		else{
		alert("Please select the address details");
		return false;
			}
	}
	function callVerifyOtp(){
		var verifyOtp =document.getElementById('verifyOtp').value ;	
		var addId=document.getElementById('addId').value;
		var primaryPhoneNo =document.getElementById('mobileNo').value ;
		//alert("verifyOtp  ::"+verifyOtp);
		if(verifyOtp==''){
			alert("Please Enter OTP ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("openMobileVerificationForm").action=contextPath+"/mobileVerificationAction.do?method=openMobileVerificationOtp&verifyOtp="+verifyOtp+"&primaryPhoneNo="+primaryPhoneNo+"&addId="+addId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("openMobileVerificationForm").submit();
	}
	function callReSendOtp(){
		
		var primaryPhoneNo =document.getElementById('mobileNo').value ;
		var addId=document.getElementById('addId').value;
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("openMobileVerificationForm").action=contextPath+"/mobileVerificationAction.do?method=resendOTP&primaryPhoneNo="+primaryPhoneNo+"&addId="+addId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("openMobileVerificationForm").submit();
	}
	 //Shashank Starts For E-mail Verify
	function verifyEmail(){
		var corporateCode=document.getElementById('corporateCode').value;

		if(corporateCode!=''){
		//alert(val);
		var institutionEmail =document.getElementById('institutionEmail').value ;
		if(institutionEmail ==''){
			alert("Please save Email !");
			return false;
		}
		
		var contextPath = document.getElementById("contextPath").value;
		document.getElementById("individualDetailForm").action=contextPath+"/mobileVerificationAction.do?method=openEmailVerification&corporateCode="+corporateCode;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("individualDetailForm").submit();
		}
		else{
		alert("Please save customer details First!");
		return false;
			}
	}
	function verifyEmailCorporate(){
		var corporateCode=document.getElementById('corporateCode').value;

		if(corporateCode!=''){
		//alert(val);
		var institutionEmail =document.getElementById('institutionEmail').value ;
		if(institutionEmail ==''){
			alert("Please save Email !");
			return false;
		}
		
		var contextPath = document.getElementById("contextPath").value;
		document.getElementById("corporateDetailForm").action=contextPath+"/mobileVerificationAction.do?method=openEmailVerificationCorporate&corporateCode="+corporateCode;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("corporateDetailForm").submit();
		}
		else{
		alert("Please save customer details First!");
		return false;
			}
	}
	