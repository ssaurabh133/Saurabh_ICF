function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}

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

function saveCIBIL(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	var consumername = document.getElementById("consumername");
	var leadDate = document.getElementById("leadDate");
	var cibilCodes = document.getElementById("cibilCodes");
	var totalOverdueac = document.getElementById("totalOverdueac");
	var decison = document.getElementById("decison");
	var comment = document.getElementById("comment");
	
	 if(trim(consumername.value) == ''||trim(leadDate.value) == ''||trim(cibilCodes.value) == ''||trim(totalOverdueac.value) == ''||trim(decison.value) == ''||trim(comment.value) == ''){
		 var msg= '';
 		if(trim(consumername.value) == '')
 			msg += '* Consumer Name is required.\n';
 		if(trim(leadDate.value) == '')
 			msg += '* Date & Time is required.\n';
 		if(trim(cibilCodes.value) == '')
 			msg += '* CIBIL Score is required.\n';
 		if(trim(totalOverdueac.value) == '')
 			msg += '* Total Overdue Accounts is required.\n';
 		if(trim(decison.value) == '')
 			msg += '* Decision is required.\n';
 		if(trim(comment.value) == '')
 			msg += '* Comment is required.\n';
 		 		
 		if(msg.match("Consumer")){
 			consumername.focus();
 		}else if(msg.match("Date")){
 			leadDate.focus();
 		}else if(msg.match("CIBIL")){
 			cibilCodes.focus();
 		}else if(msg.match("Total")){
 			totalOverdueac.focus();
 		}else if(msg.match("Decision")){
 			decison.focus();
 		}else if(msg.match("Comment")){
 			comment.focus();
 		}
 		
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }else if(validate("consumermasterform")){
		document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerAdd.do?method=saveDocDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("consumermasterform").submit();
	}
}

function onSaveofPD(alert1)
{
	DisButClass.prototype.DisButMethod();
	var basePath=document.getElementById("contextPath").value;
	
	if(cpPDValidate())
	{
		if(document.getElementById("followUp").value=='N')
		{
			document.getElementById("pdForm").action=basePath+"/personalDiscussionForSaveAction.do";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("pdForm").submit();
		}
		else if(document.getElementById("followUp").value=='Y')
		{
			if(cpNotepadValidate())
			{
				var formatD=document.getElementById("formatD").value;
				var pdDate = document.getElementById("pdDate").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(pdDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				if (dt2<dt1)
				{
					alert(alert1);
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				document.getElementById("pdForm").action=basePath+"/personalDiscussionForSaveAction.do";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("pdForm").submit();
			}
		}
		return true;
	}
}

function cpPDValidate(){
	
	 var pdDate = document.getElementById("pdDate");
	 var personMet = document.getElementById("personMet");
	 var pdMeetingLocation = document.getElementById("pdMeetingLocation");
	 var pdRemark = document.getElementById("pdRemark");
	 var followUp = document.getElementById("followUp");
	 
	 	var a = "";
	 	if(trim(pdDate.value)  == "" || trim(personMet.value) == "" || trim(pdMeetingLocation.value)  == "" || trim(pdRemark.value)  == "" || trim(followUp.value)  == ""){
	 		if(trim(pdDate.value)  == ""){
				a = "* Personal Discussion Date Time is required \n";
			}
			if(trim(personMet.value) == ""){
				a += "* Person Met is required \n";
			}
			if(trim(pdMeetingLocation.value) == ""){
				a +="* Personal Discussion Meeting Location is required \n";
			}
			if(trim(pdRemark.value) == ""){
				a +="* Personal Discussion Remark is required \n";
			}
			if(trim(followUp.value) == ""){
				a +="* Follow up Required is required \n";
			}
			
			if(a.match(/Date/gi)){
				pdDate.focus();
			}else if(a.match(/Met/gi)){
				personMet.focus();
			}else if(a.match(/Meeting/gi)){
				pdMeetingLocation.focus();
			}else if(a.match(/Remark/gi)){
				pdRemark.focus();
			}else if(a.match(/Required/gi)){
				followUp.focus();
			}
	
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false
	 	}else
	 			return true
}

function cpNotepadValidate()
{
		var alphaExp = /^[a-zA-Z]+$/;
		var msg1='',msg3='',msg4='',msg5='';
		
		if((document.getElementById("followupDate").value)=='')
		{
			msg1="* Follow Up Date is Required \n";
		}
		
		if((document.getElementById("followUpPerson").value)=='')
		{
			 msg3="* Follow Up Person is Required\n";
		}
		if((document.getElementById("followUpLocation").value)=='')
		{
			 msg4="* Follow Up Location is Required\n";
		}
		if((document.getElementById("followupRemarks").value)=='')
		{
			 msg5="* Follow Up Remarks is Required\n";
		}
		
		if(msg1!=''||msg3!=''||msg4!=''||msg5!='')
		{
			alert(msg1+msg3+msg4+msg5);
			DisButClass.prototype.EnbButMethod();
		}
		
		if(document.getElementById("followupDate").value=="")
		{
			document.getElementById("followupDate").focus();
			return false;
		}
		
		
		if(document.getElementById("followUpPerson").value =="")
		{
			document.getElementById("followUpPerson").focus();
			return false;
		}
		
		if(document.getElementById("followUpLocation").value =="")
		{
			document.getElementById("followUpLocation").focus();
			return false;
		}

		
		if(document.getElementById("followupRemarks").value =="")
		{
			document.getElementById("followupRemarks").focus();
			
			return false;
		}
		return true;
}

function submitQueryData()
{
	
	DisButClass.prototype.DisButMethod();
	var formatD=document.getElementById("formatD").value;
	var sourcepath=document.getElementById("contextPath").value;
	var resolutionStatus=document.getElementById("resolutionStatus").value;
	var queryDate = document.getElementById("date").value;
	var resolutionDate = document.getElementById("to_datepicker").value;
	var queryTime = document.getElementById("queryTime").value;
	var resolutionTime = document.getElementById("resolutionTime").value;
	var businessdate = document.getElementById("businessdate").value
	//alert("queryTime: "+queryTime+" resolutionTime: "+resolutionTime);
	//alert("queryTime1: "+parseInt(queryTime.split(":")[0],10)+" resolutionTime1: "+parseInt(resolutionTime.split(":")[0],10));
	var today = new Date();
	var hours = today.getHours();
	var minutes = today.getMinutes();
	var systemTime=hours+":"+minutes;
	hours=(hours<=9)? ("0"+hours):hours;
	minutes=(minutes<=9)? ("0"+minutes):minutes;
	var sysTime=hours+":"+minutes;
	//alert("hours: "+hours+" minutes: "+minutes+" sysTime: "+sysTime);

	var dt1=getDateObject(queryDate,formatD.substring(2, 3));
    var dt2=getDateObject(resolutionDate,formatD.substring(2, 3));
    var bDate=getDateObject(businessdate,formatD.substring(2, 3));
   
	if(queryCaseNValidate())
	{
		if(parseInt(sysTime.split(":")[0],10) < parseInt(queryTime.split(":")[0],10))
		{
			alert("Resolution Time should not be greater than Query Time");
			document.getElementById("queryTime").value='';
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if((resolutionStatus)=='P')
		{
			
			 if(dt1>bDate)
			    {
					alert("Query Date cannot be greater than Bussiness Date.");
					document.getElementById("to_datepicker").value='';
					document.getElementById("to_datepicker").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
			    }
			
			var userId = document.getElementById("userId").value;
			if(userId=='')
			{
				alert("Initiate To is required");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			document.getElementById("queryForm").action=sourcepath+"/query.do?method=submitQueryData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("queryForm").submit();
			return true;
		}
		else if((resolutionStatus)=='R')
		{
			if(queryValidate())
			{
				if(dt2<dt1)
			    {
					alert("Resolution Date cannot be smaller than Query Date.");
					document.getElementById("to_datepicker").value='';
					document.getElementById("to_datepicker").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
			    }
				if( dt2<=dt1 && (parseInt(resolutionTime.split(":")[0],10) < parseInt(queryTime.split(":")[0],10)))
				{
					alert("Resolution Time should not be greater than Query Time");
					document.getElementById("resolutionTime").value='';
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
				document.getElementById("queryForm").action=sourcepath+"/query.do?method=submitQueryData";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("queryForm").submit();
				return true;
			}
			else
				DisButClass.prototype.EnbButMethod();
				return false;
		}
	}	
}

function updateQueryData()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var qyeryDate = document.getElementById("queryDate").value;
	var resolutionStatus = document.getElementById("resolutionStatus").value;
	var resolutionDate = document.getElementById("to_datepicker").value;
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(qyeryDate,formatD.substring(2, 3));
    var dt2=getDateObject(resolutionDate,formatD.substring(2, 3));
    
    var queryTime = document.getElementById("queryTime").value;
    var resolutionTime = document.getElementById("resolutionTime").value;
     //  alert("dt1 Time "+dt1.getTime());
  // alert("queryTime: "+queryTime+" resolutionTime: "+resolutionTime);
  // alert("queryTime1: "+parseInt(queryTime.split(":")[0],10)+" resolutionTime1: "+parseInt(resolutionTime.split(":")[0],10));
	if(queryValidate(resolutionStatus) && resolutionStatus=='R')
	{
		if(dt2<dt1)
	    {
			alert("Resolution Date cannot be smaller than Query Date.");
			document.getElementById("to_datepicker").value='';
			document.getElementById("to_datepicker").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
		
		if(dt2<=dt1 && (parseInt(resolutionTime.split(":")[0],10) < parseInt(queryTime.split(":")[0],10)))
		{
			alert("Resolution Time should not be greater than Query Time");
			document.getElementById("resolutionTime").value='';
			DisButClass.prototype.EnbButMethod();
			return false;
		}		
		document.getElementById("queryForm").action=sourcepath+"/query.do?method=updateQueryData";
		document.getElementById("queryForm").submit();
		return true;
	}
	if(resolutionStatus=='P')
	{
			var userId = document.getElementById("userId").value;
			if(userId=='')
			{
				alert("Initiate To is required");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			document.getElementById("queryForm").action=sourcepath+"/query.do?method=updateQueryData";
			document.getElementById("queryForm").submit();
			return true;
	}
  
}

function queryValidate(resolutionStatus)
{
	if(resolutionStatus=='R')
	{
		if(document.getElementById("to_datepicker").value=="")
		{
			alert("Resolution Date is Required \n");
			document.getElementById("to_datepicker").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(document.getElementById("resolutionRemarks").value=="")
		{
			alert("Resolution Remarks  is Required  \n");
			document.getElementById("resolutionRemarks").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}

	return true;
}

function queryCaseNValidate(){
	
	 var queryDate = document.getElementById("date");
	 var queryRemarks = document.getElementById("queryRemarks");
	 var resolutionStatus = document.getElementById("resolutionStatus");
	 
	 	var a = "";
	 	if(trim(queryDate.value)  == '' || trim(queryRemarks.value) == '' || trim(resolutionStatus.value)  == ''){
	 		if(trim(queryDate.value)  == ""){
				a = "* Query Date & Time is required \n";
			}
			if(trim(queryRemarks.value) == ""){
				a += "* Query Remarks is required \n";
			}
			if(trim(resolutionStatus.value) == ""){
				a +="* Resolution Status is required \n";
			}
			
			if(a.match(/Date/gi)){
				queryDate.focus();
			}else if(a.match(/Remarks/gi)){
				queryRemarks.focus();
			}else if(a.match(/Resolution/gi)){
				resolutionStatus.focus();
			}
	
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false
	 	}else
	 			return true
}
//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[A-Za-z0-9+-]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
var san_url = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;



function validate(formName){
	var cibilCodes = document.getElementById("cibilCodes").value;
	var totalOverdueac = document.getElementById("totalOverdueac").value;
	var totalAc = document.getElementById('totalAc').value;
	var noofEnquiry = document.getElementById('noofEnquiry').value;
  

 var errors = [];
 
 if (!ck_numeric.test(cibilCodes)) {
	 if(cibilCodes != ''){
	 	errors[errors.length] = "* CIBIL Score is invalid.";
	 }
 }
 if (!ck_numeric.test(totalAc)) {
	 if(totalAc != ''){
		 errors[errors.length] = "* Total Accounts is invalid.";
	 }
}
 if (!ck_numeric.test(totalOverdueac)) {
	 if(totalOverdueac != ''){
		 errors[errors.length] = "* Total Overdue Account is invalid.";
	 }
 }
 if (!ck_numeric.test(noofEnquiry)) {
	 if(noofEnquiry != ''){
		 errors[errors.length] = "* No.Of Enquiries is invalid.";
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
 if(msg.match("CIBIL")){
	 document.getElementById("cibilCodes").focus();
	}else if(msg.match("Accounts")){
		document.getElementById("totalAc").focus();
	}else if(msg.match("Overdue")){
		document.getElementById("totalOverdueac").focus();
	}else if(msg.match("Enquiries")){
		document.getElementById("noofEnquiry").focus();
	}
 
 alert(msg);
 document.getElementById("saveBuyer").removeAttribute("disabled","true");
}
// STARt BY PRASHANT	
function saveGroupExposure()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	
	// Start by Prashant
	
	var customerId = document.getElementById("customerId").value;
	var groupType = document.getElementById("groupType").value;
	if(groupType=='E')
	{
		var groupDescription=document.getElementById("groupDescription").value;
	}
	else
	{
		var groupDescription = document.getElementById("groupNameText").value;
	}
	// End by Prashant
	
	
	var groupExposureLimit=removeComma(document.getElementById("groupExposureLimit").value);
	
	var msg1='',msg2='',msg3='';
	
	
	if(customerId=='')
	{
		msg1="* Customer Id is Required \n";
	}
	if(groupDescription=='')
	{
		msg2="* Group Name is Required \n";
	}
	
	if(groupExposureLimit=='')
	{
		 msg3="* Exposure Limit is Required\n";
	}
	
	
	
	if(msg1!='' || msg2!=''||msg3!='')
	{
		alert(msg1+msg2+msg3);
		if(msg1!='')
		{
			document.getElementById("customerId").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(msg2!='')
		{
			document.getElementById("groupDescription").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(msg3!='')
		{
			document.getElementById("groupExposureLimit").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	
	}
	
	var gExpLimit=0.00;
	if(groupExposureLimit!='')
	{
		gExpLimit=parseFloat(groupExposureLimit);
	}
		
	//alert(gExpLimit);
	if(gExpLimit<=0.00)
	{
		 alert("Exposure Limit can't be less than or equal to zero");
		 document.getElementById("groupExposureLimit").focus();
		 DisButClass.prototype.EnbButMethod();
		 return false;
	}
	if(groupType='E')
	{
		var exposureAmt=removeComma(document.getElementById("exposureAmt").value);
		var amountFrom=removeComma(document.getElementById("amountFrom").value);
		var amountTo=removeComma(document.getElementById("amountTo").value);
		if(gExpLimit<parseFloat(exposureAmt))
		{
			 alert("Exposure Limit can't be less than Exposure Amount.");
			 document.getElementById("groupExposureLimit").focus();
			 DisButClass.prototype.EnbButMethod();
			 return false;
		}
		if(parseFloat(exposureAmt)<parseFloat(amountFrom) || parseFloat(exposureAmt)>parseFloat(amountTo))
		{
			alert("Redefined Exposure does not pertain to your limit.");
			 document.getElementById("groupExposureLimit").focus();
			 DisButClass.prototype.EnbButMethod();
			 return false;
		}
	}
	document.getElementById("groupExposureLimitForm").action=contextPath+"/underwritingGroupExposerProcess.do?method=saveGroupExposer";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("groupExposureLimitForm").submit();
	return true;
	
}

//Start By Prashant   
function groupSelectAtUnderWriter(){
		var groupType=document.getElementById("groupType").value;
		document.getElementById("groupNameText").value='';
		document.getElementById("hGroupId").value='';
		document.getElementById("groupDescription").value='';		
		document.getElementById("groupExposureLimit").value='0.00';
		document.getElementById("exposureCurrentScheme").value='0.00';			
		document.getElementById("customerTotalExposure").value='0.00';
		document.getElementById("exposureScheme").value='0.00';
		document.getElementById("productExposure").value='0.00';			
		document.getElementById("industryExposure").value='0.00';
		document.getElementById("subIndustryExposure").value='0.00';
		document.getElementById('loanBalansePrincipal').value = '0.00';
		document.getElementById('loanOverduePrincipal').value = '0.00';
		document.getElementById('sdAdviceAmount').value = '0.00';
		if(groupType=='E')
		{
			document.getElementById("groupNameText").setAttribute("disabled", true);
			document.getElementById("groupDescription").removeAttribute("disabled", true);
			document.getElementById("hGroupId").removeAttribute("disabled", true);
			document.getElementById("groupNameText").setAttribute("disabled", true);
			document.getElementById("groupLov").style.display="";
			document.getElementById("groupText").style.display="none";		
			document.getElementById('sdCharge').value = '0.00';
			document.getElementById('exposureAmt').value = '0.00';	
			document.getElementById('grossAmountLoan').value='0.00';	
		}
		else
		{		
			document.getElementById("groupNameText").removeAttribute("disabled", true);
			document.getElementById("groupDescription").setAttribute("disabled", true);
			document.getElementById("hGroupId").setAttribute("disabled", true);
			document.getElementById("groupNameText").removeAttribute("disabled", true);			
			document.getElementById("groupLov").style.display="none";
			document.getElementById("groupText").style.display="";	
			document.getElementById('sdCharge').value = document.getElementById('sdChargeTemp').value;
			document.getElementById('exposureAmt').value = document.getElementById('exposureAmtTemp').value;	
			document.getElementById('grossAmountLoan').value=document.getElementById('grossAmountLoanTemp').value;	
		}
		return true;
	}

function fetchExposureLimitByCustomerId(customerId){
	//alert("customerId: "+customerId);
	var contextPath=document.getElementById("contextPath").value;
	if(customerId !='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=fetchExposureLimitByCustomer";
		var data = "customerId="+customerId;
		sendRequestFetchExposureLimitByCustomerId(address,data,'g');
		return true;
	}
	else
	{
		alert("Customer Id is required.");	
   	 	return false;
	}
	
}

function sendRequestFetchExposureLimitByCustomerId(address, data,source) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodFetchExposureLimitByCustomerId(request,source);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodFetchExposureLimitByCustomerId(request,source)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			document.getElementById('customerId').value = trim(s1[0]);
			document.getElementById('customerName').value = trim(s1[1]);
			document.getElementById('groupType').value = trim(s1[2]);
			var includeExposure = document.getElementById('includeExposure').value;
			var exposureWithSD = document.getElementById('exposureWithSD').value;
			if(trim(s1[2])=='N')
			{
				document.getElementById("groupText").style.display="";
				document.getElementById("groupLov").style.display="none";
				document.getElementById("hGroupId").value='';
				document.getElementById("groupDescription").value='';
				document.getElementById("groupNameText").removeAttribute("disabled", true);
				document.getElementById("groupDescription").setAttribute("disabled", true);
				document.getElementById("hGroupId").setAttribute("disabled", true);
				document.getElementById('groupNameText').value = trim(s1[3]);				
				document.getElementById('groupExposureLimit').value = trim(s1[6]);
				document.getElementById('exposureCurrentScheme').value ='0.00';
				document.getElementById('customerTotalExposure').value = '0.00';
				document.getElementById('exposureScheme').value = '0.00';
				document.getElementById('productExposure').value = '0.00';
				document.getElementById('industryExposure').value = '0.00';
				document.getElementById('subIndustryExposure').value = '0.00';
				document.getElementById('loanBalansePrincipal').value = '0.00';
				document.getElementById('loanOverduePrincipal').value = '0.00';
				document.getElementById('sdAdviceAmount').value = '0.00';
				document.getElementById('sdCharge').value = document.getElementById('sdChargeTemp').value;
				document.getElementById('exposureAmt').value = document.getElementById('exposureAmtTemp').value;
				document.getElementById('grossAmountLoan').value = document.getElementById('grossAmountLoanTemp').value;
			}
			else
			{
				document.getElementById("groupLov").style.display="";
				document.getElementById("groupText").style.display="none";	
				document.getElementById("groupNameText").value='';
				document.getElementById("groupNameText").setAttribute("disabled", true);
				document.getElementById("groupDescription").removeAttribute("disabled");
				document.getElementById("hGroupId").removeAttribute("disabled");
				
				if(source!='l')
				{
					document.getElementById('groupDescription').value = trim(s1[5]);
					document.getElementById('hGroupId').value = trim(s1[4]);
				}
				
				document.getElementById('groupExposureLimit').value = trim(s1[6]);
				//document.getElementById('currentExposureLimit').value = trim(s1[11]);
				document.getElementById('exposureCurrentScheme').value = trim(s1[10]);
				document.getElementById('customerTotalExposure').value = trim(s1[9]);
				document.getElementById('exposureScheme').value = trim(s1[8]);
				document.getElementById('productExposure').value = trim(s1[7]);
				document.getElementById('industryExposure').value = trim(s1[12]);
				document.getElementById('subIndustryExposure').value = trim(s1[13]);
				document.getElementById('loanBalansePrincipal').value = trim(s1[14]);
				document.getElementById('loanOverduePrincipal').value = trim(s1[15]);
				document.getElementById('sdAdviceAmount').value = trim(s1[16]);
				document.getElementById('sdCharge').value = trim(s1[17]);
				document.getElementById('exposureAmt').value = trim(s1[18]);
				document.getElementById('grossAmountLoan').value = trim(s1[19]);
					
			}	
			closeAllLovCallUnloadBodyNew();
	    }
	}
}

function showQueryData(dealQueryId)
{
	// alert("Test");
	var sourcepath=document.getElementById("contextPath").value;
	location.href=sourcepath+"/queryBehind.do?method=showQueryData&dealQueryId="+dealQueryId;
	
	
}

function resultofQuery()
{
	//alert("resultofQuery");
	var resolutionStatus=document.getElementById("resolutionStatus").value;
	if((resolutionStatus)=='P')
	{
//		 document.getElementById("showDate").style.display='none';
//		 document.getElementById("notshowDate").style.display='block';
//		 document.getElementById("to_datepicker12").setAttribute("disabled","true");
//		 document.getElementById("resolutionTime").setAttribute("disabled","true");
//		 document.getElementById("resolutionRemarks").setAttribute("disabled","true");
		 document.getElementById("showDate").style.display='none';
		 document.getElementById("notshowDate").style.display='block';
		 document.getElementById("resolutionTime").setAttribute('readonly','true');
		 document.getElementById("resolutionTime").value='';
		 document.getElementById("resolutionRemarks").setAttribute('readonly','true');
		 document.getElementById("resolutionRemarks").value='';
		 document.getElementById("UserButton").removeAttribute('disabled','remove');
	}
	else
	{
		document.getElementById("userId").value='';
		document.getElementById("lbxUserId").value='';
		document.getElementById("showDate").style.display='block';
		document.getElementById("notshowDate").style.display='none';
		document.getElementById("resolutionTime").readOnly=false;
		document.getElementById("resolutionRemarks").readOnly=false;
		document.getElementById("to_datepicker").readOnly=false;
		document.getElementById("UserButton").setAttribute('disabled','true');
		
	}
	
}

function clearQuery()
{
   //alert("clearQuery");
	var sourcepath=document.getElementById("contextPath").value;
	location.href=sourcepath+"/queryBehind.do?method=showQueryDataFirst";
	
}

function validateQueryTime()
{
	var timeStr = document.getElementById("queryTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('queryTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('queryTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('queryTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('queryTime').value='';
	return false;
	}
}

function validateResolutionTime()
{
	var timeStr = document.getElementById("resolutionTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('resolutionTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('resolutionTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('resolutionTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('resolutionTime').value='';
	return false;
	}
}


function searchQueryResponse()
{
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var allBranches=document.getElementById("allBranches").checked;
	var userId="";
	//var userId=document.getElementById("reportingToUserId").value;	
	if(userId!=''||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='' ||allBranches!=false)
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("queryResponseSearchForm").action=sourcepath+"/querResponseSearchBehindAction.do?userId="+userId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("queryResponseSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function searchDealAtQueryResponse(val)
{
	var contextPath =document.getElementById('contextPath').value ;
	location.href=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
}
function saveQueryResponseApproval()
{

	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	
	var chkValue='';
	var status=false;
	var msg1='';	
	
	var contextPath = document.getElementById('contextPath').value;
	if(rowCount > 1 ){		
		for(var i=1;i<rowCount;i++){
		var resolutionRemarks = document.getElementById('resolutionRemarks'+i).value;
	 	var dealQueryId = document.getElementById('dealQueryResponseId'+i).value;
	 		if(dealQueryId!="" && resolutionRemarks=="" ){
				msg1="Remarks are required";
				status= true;
			}
	   }	
      }
	
	if(status)
	   {
				alert(msg1);	
				return false;
				
		}
	else
	{
		var contextPath = document.getElementById('contextPath').value;
	    document.getElementById("saveQueryResponseApprovalForm").action = contextPath+"/saveQueryResponseApproval.do?method=saveQueryResApproval";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("saveQueryResponseApprovalForm").submit();
		return true;
     }
}


function openStatus(val) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}
// END BY PRASHANT	


// Start by KK
function saveTermSheet()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;	
	var roi=document.getElementById("roi").value;
	var managementFee=document.getElementById("managementFee").value;
	var tenure=document.getElementById("tenure").value;
	var sdAmount=document.getElementById("sdAmount").value;
	var guaranteesPrsnl=document.getElementById("guaranteesPrsnl").value;
	var guaranteesCorp=document.getElementById("guaranteesCorp").value;
	var additionalCommitee=document.getElementById("additionalCommitee").value;
	var hypothecation=document.getElementById("hypothecation").value;
	var insurance=document.getElementById("insurance").value;
	var otherCondition=document.getElementById("otherCondition").value;
	var rocApplicable=document.getElementById("rocApplicable").value;
	var defaultLoanAmount=document.getElementById("defaultLoanAmount").value;
	var grossAmountLoan=document.getElementById("grossAmountLoan").value;
	if(roi =='' &&  managementFee=='' && tenure =='' && sdAmount =='' && guaranteesPrsnl =='' && guaranteesCorp =='' && additionalCommitee =='' && hypothecation =='' && insurance =='' && otherCondition ==''  && rocApplicable == '')
	{
		alert("Please capture atleast one field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		if(parseFloat(grossAmountLoan) > parseFloat(defaultLoanAmount))
		{
			alert("Loan gross amount should be less than or equal to loan amount.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var expAmt=document.getElementById("expAmt").value;
		if(expAmt=='')
			expAmt='0';
		var amountFrom=document.getElementById("amountFrom").value;
		if(amountFrom=='')
			amountFrom='0';
		var amountTo=document.getElementById("amountTo").value;
		if(amountTo=='')
			amountTo='0';
		var calculatedSD=removeComma(document.getElementById("calculatedSD").value);
		if(calculatedSD=='')
			calculatedSD='0';
		var includeExposure=document.getElementById("includeExposure").value;
		var exposureWithSd=document.getElementById("exposureWithSd").value;
		var loanAmt=parseFloat(grossAmountLoan);
		if(includeExposure='Y')
			loanAmt=loanAmt+parseFloat(expAmt);
		if(exposureWithSd='Y')
			loanAmt=loanAmt-parseFloat(calculatedSD);
		if(loanAmt=='')
			loanAmt='0'
		
		if(parseFloat(loanAmt)<parseFloat(amountFrom) || parseFloat(loanAmt)>parseFloat(amountTo))
		{
			alert("Redefined Exposure does not pertain to your limit.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		document.getElementById("termSheetForm").action=contextPath+"/underwritingTermSheetProcess.do?method=saveTermSheet";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("termSheetForm").submit();
		return true;
	}
}
//method added by neeraj
function calculateSdprct()
{
	//alert('calculateSdprct');
	if(checkRate('sdAmount'))
	{
		var sdAmount = document.getElementById("sdAmount").value.trim();
		var grossAmountLoan = document.getElementById("grossAmountLoan").value.trim()
		if(sdAmount=='')
		{
			document.getElementById("sdAmount").value="0";
			sdAmount="0";
		}
		if(grossAmountLoan=='')
		{
			document.getElementById("grossAmountLoan").value="0";
			grossAmountLoan="0";
		}
		sdAmount=removeComma(sdAmount);
		grossAmountLoan=removeComma(grossAmountLoan);
		var sd=parseFloat(sdAmount);
		var loan=parseFloat(grossAmountLoan);
		
		var calsd=(loan*sd)/100;
		var calculatedSD = (calsd).toFixed(2);
		formatNumber(calculatedSD,'calculatedSD');
	}
}
//method added by neeraj
function generateTermSheet()
{
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("termSheetForm").action=contextPath+"/underwritingTermSheetProcess.do?method=generateTermSheet";
	document.getElementById("termSheetForm").submit();
	return true;
}
function isNumberKey(evt) 
{

var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only numeric allowed here");
	return false;
}
	return true;
}


function imposeMaxLength(Object, MaxLen)
{
  if(Object.value.length > MaxLen)
  {
	  alert("Max Length can not be greater than "+MaxLen);
	  return false;
  }
  return true;
}

function isCharKey(evt){
	
	var keyCode = (evt.which) ? evt.which : event.keyCode;
	if ((keyCode < 65 || keyCode > 90) && (keyCode < 97 || keyCode > 123) && keyCode != 32 && keyCode != 8 && keyCode != 13)
	{
		alert("Only letters allowed here");
		return false;
	}
	return true;
}

//End by KK
//Amit Query Initiation Starts
function searchQueryInitiation()
{
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var allBranches=document.getElementById("allBranches").checked;
	var userId="";
	//var userId=document.getElementById("reportingToUserId").value;	
	if(userId!=''||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='' ||allBranches!=false)
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("querySearchForm").action=sourcepath+"/querySearchBehind.do?userId="+userId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("querySearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
// Amit Query Initiation Ends