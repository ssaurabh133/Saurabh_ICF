
function searchContactRecording(message){ 
    
    var contextPath=document.getElementById("contextPath").value;
//    alert("ok");
    if((document.getElementById("loanno").value =='') 
   		 && (document.getElementById("customername").value =='') 
   		 && (document.getElementById("product").value=='') 
   		 && (document.getElementById("dpd1").value=='') 
   		 && (document.getElementById("dpd2").value =='') 
   		 && (document.getElementById("queue").value=='')
   		 && (document.getElementById("pos1").value =='') 
   		 && (document.getElementById("pos2").value=='')
   		 && (document.getElementById("user").value=='')
   		 && (document.getElementById("custype").value=='')
   		 && (document.getElementById("balanceprincipal").value=='')
   		 && (document.getElementById("custcategory").value=='')
   		 && (document.getElementById("assignfrom").value=='')
   		 && (document.getElementById("assignto").value=='')
   		 && (document.getElementById("balanceprincipal").value=='')
   		 && (document.getElementById("lbxBranchId").value=='')
   		 && (document.getElementById("dueDate").value=='')
   		&& (document.getElementById("actionDue").value=='')){ 
   	   alert(message);
	document.getElementById("search").removeAttribute("disabled");
	return false;
}else {
	var assignFrom=document.getElementById("assignfrom").value;
	var assignto=document.getElementById("assignto").value;
	var formatD=document.getElementById("formatD").value;
	assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
	assignto=getDateObject(assignto,formatD.substring(2, 3));
	  
	 if(document.getElementById("dpd1").value=='.'){
		alert("DPD >= is not in correct Format!");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else if(document.getElementById("dpd2").value=='.'){
		alert("DPD <= is not in correct Format!");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else if(document.getElementById("pos1").value=='.'){
		alert("Over Due Amount <= is not in correct Format!");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else if(document.getElementById("pos2").value=='.'){
		alert("Over Due Amount >= is not in correct Format!");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else if(document.getElementById("balanceprincipal").value=='.'){
		alert("Balance Principal is not in correct Format!");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else if((document.getElementById("assignfrom").value !="")&&(document.getElementById("assignto").value !="")){
	if(assignFrom>assignto){
		alert(" Assign to date  must be greater than Assign from date ");
	    document.getElementById("search").removeAttribute("disabled");
		return false;
	  }
	}else{
	document.getElementById("searchFlag").value="Search";
	document.getElementById("contactRecordingSearch").action = contextPath+"/contactRecordingBehindAction.do";
	document.getElementById("contactRecordingSearch").submit();
		 return true;
	}
}
}
function openContactRecordingTrail(loanNo,exclationFlag)
{
	var contextPath=document.getElementById("contextPath").value;
	var url=contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&exclationFlag="+exclationFlag;
	popupWin=window.open(url,'ContactRecording','height=1200,width=1000,top=400,left=400, scrollbars=yes ').focus();
	if (window.focus) 
	   popupWin.focus();	
	
	/*var contextPath=document.getElementById("contextPath").value;
	document.getElementById("contactRecordingSearch").action = contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&exclationFlag="+exclationFlag;
	document.getElementById("contactRecordingSearch").submit();	*/
}
function enablePaymentDtl(){
	var actionCode=document.getElementById("actionCode").value;
	if(actionCode=="COLL"){
		document.getElementById("instrumentNo").value="";
		document.getElementById("paymentMode").value="";
		document.getElementById("receiptNo").value="";
		document.getElementById("instrumentDate").value="";
		document.getElementById("instrumentNo").removeAttribute("disabled");
		document.getElementById("paymentMode").removeAttribute("disabled");
		document.getElementById("receiptNo").removeAttribute("disabled");
		document.getElementById("instrumentDate").removeAttribute("disabled");
	}else{
		document.getElementById("instrumentNo").value="";
		document.getElementById("paymentMode").value="";
		document.getElementById("receiptNo").value="";
		document.getElementById("instrumentDate").value="";
		document.getElementById("instrumentNo").setAttribute("disabled",true);
		document.getElementById("paymentMode").setAttribute("disabled",true);
		document.getElementById("receiptNo").setAttribute("disabled",true);
		document.getElementById("instrumentDate").setAttribute("disabled",true);
		
	}
}

function openPopUpFollowUp(){
	var loanId=document.getElementById("loanId").value;
	var exclationFlag=document.getElementById("exclationFlag").value;
		 var url="contactRecordingDispatchAction.do?method=openFollowUpTrail&loanId="+loanId+"&exclationFlag="+exclationFlag;
	   newwindow= window.open(url,'name','height=400,width=700,top=200,left=250,scrollbars=no ');
	    if (window.focus) {newwindow.focus()}
	         return true;
		     
		 }
function saveFollowUpTrail(alert1)
{
	  DisButClass.prototype.DisButMethod();
	  var check=0;
	  var contextPath=document.getElementById("contextPath").value;
	  var actionCode=document.getElementById("actionCode").value;
	  var instrumentNo=document.getElementById("instrumentNo").value;
	  var paymentMode=document.getElementById("paymentMode").value;
	  var receiptNo=document.getElementById("receiptNo").value;
	  var instrumentDate=document.getElementById("instrumentDate").value;
	  var contactMode=document.getElementById("contactMode").value;
	  //var personContacted=document.getElementById("personContacted").value;
	  var personContact=document.getElementsByName("personContacted");
	  var	personContacted ='';
	  var remarks=document.getElementById("remarks").value;
	  var customerMemoLine=document.getElementById("customerMemoLine").value;
	  var actionAmount=document.getElementById("actionAmount").value;
	  var nxtActionDate=document.getElementById("nxtActionDate").value;
	  var businessDate=document.getElementById("businessDate").value;
	  var formatD=document.getElementById("businessDate").value;
	  
	  var msg1="",msg2="",msg3="",msg4="",msg5="",msg6="",msg7="",msg8="",msg9="",msg10="",msg11="",msg12="";
	  var i = 0;
	  for(i=0;i<personContact.length;i++){
		//  alert(personContact);
		  if(personContact.value!='')
			  personContacted = personContact.value;
	  }
	  if(actionCode==""){
		  msg1="* Action Code is required \n";
		  check=1;
	  }
	  if(actionCode=='COLL'){
		
		if(paymentMode==""){
			 msg3="* Payment Mode is required \n";
			  check=1;		  
		 }
		  if(instrumentNo=="" &&paymentMode!=="" && paymentMode!='C' ){
			  msg2="* Instrument No is required \n";
			  check=1;  
		  }
		if(receiptNo==""){
			 msg4="* Receipt No  is required \n";
			  check=1; 
		}
		if(instrumentDate==""){
			 msg5="* Instrument Date  is required \n";
			  check=1; 
		}
		if(actionAmount==""){
			 msg6="* Action Amount  is required \n";
			  check=1; 
		}
	  }
	  if(actionCode=='PTP'){
		  if(actionAmount==""){
				 msg6="* Action Amount  is required \n";
				  check=1; 
			}  
	  }
	  if(actionCode!='EC'){
	  if(contactMode==""){
			 msg7="* Contact Mode  is required \n";
			  check=1; 
		}
	  if(personContacted==""){
			 msg8="* Person Contacted  is required \n";
			  check=1; 
		}
	  }
	  if(!nxtActionDate==""){
		  nxtActionDate=getDateObject(nxtActionDate,formatD.substring(2, 3));
		  businessDate=getDateObject(businessDate,formatD.substring(2, 3));
		 
		  if(businessDate>nxtActionDate){
			  msg12="* Next Action date must be greater than Current Date \n";
			  check=1;
		  }
			 
		}
	  if(remarks==""){
			 msg9="* Remarks is required \n";
			  check=1; 
		}
	  if(remarks.length>1000){
		  msg10="* Remarks can be more then 1000 character \n";
		  check=1;   
	  }
	  if(customerMemoLine.length>1000){
		  msg11="* Customer Memo Line can not more then 1000 character \n";
		  check=1;   
	  }
	  if(check==1){
		  document.getElementById("Save").removeAttribute('disabled',false);  
		  alert(msg7+msg8+msg1+msg2+msg3+msg4+msg5+msg6+msg12+msg9+msg10+msg11);
		  DisButClass.prototype.EnbButMethod();
		  return false;
		 
	  }else{
	document.getElementById("contactRecFollowUpForm").action = contextPath+"/contactRecordingDispatchAction.do?method=saveFollowUpTrail";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("contactRecFollowUpForm").submit();		
	return true;
	  }
}


function displayAddress(){ 
	//alert("In displayAddress");
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/contactRecordingDispatchAction.do?method=showAddressDetails&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}


function paymentInfo()
{ 
	// alert("In paymentInfo");
	 var loanId=document.getElementById("loanId").value;
	 var contextPath=document.getElementById("contextPath").value;
	 var url=contextPath+"/collButtonAction.do?method=paymentDetails&loanId="+loanId+" ";
	 newWindow=window.open(url,'paymentInfo','height=300,width=1000,top=200,left=250,scrollbars=yes');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}

function bounceInfo(){
	//alert("In bounceInfo");
	var loanId=document.getElementById("loanId").value;
	var contextPath=document.getElementById("contextPath").value;
	var url=contextPath+"/collButtonAction.do?method=bounceDetails&loanId="+loanId+" ";
	newWindow=window.open( url,'bounceInfo','height=300,width=1000,top=200,left=250,scrollbars=yes');
	if (window.focus) 
	{
		 newWindow.focus()
	}
	return true;
}


function viewpayablerecievable(){
	//alert("In bounceInfo");
	var loanId=document.getElementById("loanId").value;
	var contextPath=document.getElementById("contextPath").value;
	var url=contextPath+"/collButtonAction.do?method=viewPaybleReceivable&loanId="+loanId;
	newWindow=window.open( url,'viewpayablerecievableInfo','height=300,width=1000,top=200,left=250,scrollbars=yes');
	if (window.focus) 
	{
		 newWindow.focus()
	}
	return true;
}

function openaddressJsp(){
	 var contextPath=document.getElementById("contextPath").value;
var loanId=document.getElementById("loanId").value;
newWindow=window.open(contextPath+'/collAddressBehindAction.do?loanId='+loanId,'openaddressJsp','height=380,width=1000,top=200,left=250, scrollbars=no');
    if(window.focus){
    	newWindow.focus();
    }
	return true;
}
function openFollowUpDtlPopUp(){
	var loanId=document.getElementById("loanId").value;
	  var contextPath=document.getElementById("contextPath").value;

	  newWindow=window.open('collButtonAction.do?method=followUpTrailDetailedData&loanid='+loanId,'showFollowUpTrailData','height=300,width=1000,top=200,left=250,scrollbars=no');

	
	  if(window.focus){
		  newWindow.focus();
	  }
	  return true;
}
function saveAddress(){
	  var contextPath=document.getElementById("contextPath").value;
	if(validateCollAddressValidatorForm(document.getElementById("customerForm")))
	 {	document.getElementById("customerForm").action = contextPath+"/collAddressAction.do?method=save_address";
	 		document.getElementById("customerForm").submit();	
		   return true;
	 }
	 else
		{
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}


}

function timeValidation(elementId)
{
	var timeStr = document.getElementById(elementId).value;
	if(timeStr.length<5)
	{
		document.getElementById(elementId).value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById(elementId).value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById(elementId).value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById(elementId).value='';
	return false;
	}
}
function getDateObject(dateString,dateSeperator)
{
		var dateParts = dateString.split(dateSeperator);
		var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
		return dateObject;

}
/*Supervisor Review Code starts here*/
 function searchSupervisorReview(message){ 
	    
	    var contextPath=document.getElementById("contextPath").value;
        //	    alert("ok");
	    if((document.getElementById("loanno").value =='') 
	   		 && (document.getElementById("customername").value =='') 
	   		 && (document.getElementById("product").value=='') 
	   		 && (document.getElementById("dpd1").value=='') 
	   		 && (document.getElementById("dpd2").value =='') 
	   		 && (document.getElementById("queue").value=='')
	   		 && (document.getElementById("pos1").value =='') 
	   		 && (document.getElementById("pos2").value=='')
	   		 && (document.getElementById("user").value=='')
	   		 && (document.getElementById("custype").value=='')
	   		 && (document.getElementById("custcategory").value=='')
	   		 && (document.getElementById("assignfrom").value=='')
	   		 && (document.getElementById("assignto").value=='')
	   		 && (document.getElementById("balanceprincipal").value=='')){ 
	   	   alert(message);
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else {
		var assignFrom=document.getElementById("assignfrom").value;
		var assignto=document.getElementById("assignto").value;
		var formatD=document.getElementById("formatD").value;
		assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
		assignto=getDateObject(assignto,formatD.substring(2, 3));
		  
		 if(document.getElementById("dpd1").value=='.'){
			alert("DPD >= is not in correct Format!");
			document.getElementById("search").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("dpd2").value=='.'){
			alert("DPD <= is not in correct Format!");
			document.getElementById("search").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("pos1").value=='.'){
			alert("Over Due Amount <= is not in correct Format!");
			document.getElementById("search").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("pos2").value=='.'){
			alert("Over Due Amount >= is not in correct Format!");
			document.getElementById("search").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("balanceprincipal").value=='.'){
			alert("Balance Principal is not in correct Format!");
			document.getElementById("search").removeAttribute("disabled");
			return false;
		}else if((document.getElementById("assignfrom").value !="")&&(document.getElementById("assignto").value !="")){
		if(assignFrom>assignto){
			alert(" Assign to date  must be greater than Assign from date ");
		    document.getElementById("search").removeAttribute("disabled");
			return false;
		  }
		}else {
	document.getElementById("searchFlag").value="Search";
	document.getElementById("supervisorReviewSearch").action = contextPath+"/supervisorReviwBehindAction.do";
	document.getElementById("supervisorReviewSearch").submit();
			 return true;
	}
 }
 }
 /* Supervisor Review Ends Here*/


function openForeClosurePopUp()
{
	var loanid=document.getElementById("loanId").value;		  
	var sourcepath=document.getElementById("contextPath").value;			   
	var defaultFormate=document.getElementById("defaultFormate").value;				
	if(defaultFormate=='H')
	{
		var url=sourcepath+"/searchCMBehindAction.do?method=openEarlyClosureDetail&loanId="+loanid+"&reportFormate=H";
		popupWin=window.open(url,'Closure_Detail','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
		if (window.focus) 
		   popupWin.focus();					
	}
	else
	{
		document.getElementById("contectRecordingForm").action=sourcepath+"/searchCMBehindAction.do?method=openEarlyClosureDetail&loanId="+loanid+"&reportFormate=P";
		document.getElementById("contectRecordingForm").submit();
	}
}

function showForeClosureData(){
	  
	var foreclosureType=document.getElementById("foreclosureType").value;
	var loanId=document.getElementById("loanNO").value;
	var loanNo=document.getElementById("loanNumber").value;
	  var contextPath=document.getElementById("contextPath").value;
	  document.getElementById("foreclosureForm").action=contextPath+"/collButtonAction.do?method=foreClosureData";
	 
	  document.getElementById("foreclosureForm").submit();
	return true;
}

function openRecordingTrailFromReview(loanNo,exclationFlag){
//	alert("Loan no :---"+loanNo);
	//alert("exclationFlag no :---"+exclationFlag);
	  var contextPath=document.getElementById("contextPath").value;
	document.getElementById("supervisorReviewSearch").action = contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&exclationFlag="+exclationFlag+"&superReview=superReview";
	document.getElementById("supervisorReviewSearch").submit();	
}
function openPopUpFollowUpReview(){
	var loanId=document.getElementById("loanId").value;
	var exclationFlag=document.getElementById("exclationFlag").value;
		 var url="contactRecordingDispatchAction.do?method=openFollowUpTrail&loanId="+loanId+"&superReview=superReview";
	   newwindow= window.open(url,'name','height=400,width=820,top=200,left=250,scrollbars=yes ');
	    if (window.focus) {newwindow.focus()}
	         return true;
}
function saveFollowUpTrailSR(alert1){
	  var contextPath=document.getElementById("contextPath").value;
	 var remarks=document.getElementById("remarks").value;
	 var msg1="";
	 var check=0;
	  if(remarks==""){
			 msg1="* Remarks is required \n";
			  check=1; 
		}
	  if(check==1){
		  document.getElementById("Save").removeAttribute('disabled',false);  
		  alert(msg1);
		  return false;
		 
	  }else{
	document.getElementById("contactRecFollowUpForm").action = contextPath+"/contactRecordingDispatchAction.do?method=saveFollowUpTrail";
	document.getElementById("contactRecFollowUpForm").submit();		
	return true;
	  }
}

function displayBankInfo(){
	//alert("In displayAddress");
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/collButtonAction.do?method=showBankInfo&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}

function displayGaurantorInfo(){
	//alert("In displayAddress");
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/collButtonAction.do?method=showGuarantorInfo&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}

function displaySupplierInfo(){
	//alert("In displayAddress");
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/collButtonAction.do?method=showSuplierInfo&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}
function statementOfAccount()
{
	var loanid=document.getElementById("loanId").value;		  
	var sourcepath=document.getElementById("contextPath").value;			   
	var defaultFormate=document.getElementById("defaultFormate").value;				
	if(defaultFormate=='H')
	{
		var url=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanid+"&source=NR&reportFormate=H";
		popupWin=window.open(url,'SOA Report','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
		if (window.focus) 
		   popupWin.focus();					
	}
	else
	{
		document.getElementById("contectRecordingForm").action=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanid+"&source=NR&reportFormate=P";
		document.getElementById("contectRecordingForm").submit();
	}
}

function viewRepaymentSchedule()
{	var id=document.getElementsByName("radioId");
	var loanid=document.getElementById("loanId").value;
	var contextPath=document.getElementById("contextPath").value;
	var url= contextPath+"/repaymentScheduleDisbursal.do?method=repaymentSchedule&loanId="+loanid;
	mywindow =openWindow(url); //window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) {
		mywindow.focus();
		return false;
	}
	return true;
}

/*Use This method as used fro refrance of case History   */
function openRecordingTrailCaseHistory(loanNo){
//	alert("Loan no :---"+loanNo);
	//alert("exclationFlag no :---"+exclationFlag);
	  var contextPath=document.getElementById("contextPath").value;
	document.getElementById("supervisorReviewSearch").action = contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&caseHistory=caseHistory";
	document.getElementById("supervisorReviewSearch").submit();	
}
/*Use This method as used fro refrance of case History   */
/*Code by arun on 21/06/2012 Starts here*/
function displayRefrenceInfo(){
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/collButtonAction.do?method=showRefrenceInfo&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}

function displayCoApplicantInfo(){
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/collButtonAction.do?method=showCoApplicantInfo&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}
/*Code by arun on 21/06/2012 Ends here*/
//start by sachin
function displayDocument(){
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/postDisbursalDocSearch.do?method=openPostDisbursalDocMaker&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}
function displayCibilInfo(){
	 var contextPath=document.getElementById("contextPath").value;

	 var loanId=document.getElementById("loanId").value;
	 var url=contextPath+"/cibilCustomer.do?method=cibilReportLoad&loanId="+loanId;

	 newWindow=window.open(url,'displayAddress','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
}

//end by sachin

//Changes Start for Add Resolution Screen
function openResolutionFollowUpDtl(actionId){
	
	//var FollowupId=parseInt(FollwupId);
	alert("Actionid:::::"+actionId);
	 var contextPath=document.getElementById("contextPath").value;
	 var url=contextPath+"/collResolutionAction.do?method=fetchActionResolution&actionId="+actionId;
	 newWindow=window.open(url,'resolutionDtl','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	 if (window.focus) 
	 {
		 newWindow.focus()
	 }
	 return true;
	 
	
}
function saveResolutionDtl()
{
	var contextPath=document.getElementById("contextPath").value;
	var resolStatus=document.getElementById("resolutionStatus").value;
	var resolDate=document.getElementById("resolutionDate").value;
	var resolRemarks=document.getElementById("resolutionRemarks").value;
	var resolType=document.getElementById("resolutionType").value;
	var followupId=document.getElementById("followupId").value;
	//var businessDate=document.getElementById("businessdate").value;
	//var formatD=document.getElementById("businessdate").value;
	var msg="";
	if (resolStatus=="")
		{
		msg=msg+"* Resolution Status Cannot be Blank."
		}
	if (resolDate=="")
	{
	msg=msg+"* Resolution Date Cannot be Blank."
	}
	if (resolRemarks=="")
	{
	msg=msg+"* Resolution Remarks Cannot be Blank."
	}
	if (resolType=="")
	{
	msg=msg+"* Resolution Type Cannot be Blank."
	}
//	if(!resolDate==""){
//		  resolDate=getDateObject(resolDate,formatD.substring(2, 3));
//		  businessDate=getDateObject(businessDate,formatD.substring(2, 3));
//		 
//		  if(businessDate>resolDate){
//			  msg="* Resolution Date must be greater than Current Date \n";
//			  //check=1;
//			  }
//	}
	
	if(msg!="")
		{
		alert(msg)
		return false;
		}
	else
		{
		
			document.getElementById("ResolutionActionForm").action=contextPath+"/followUpResolutionAction.do?method=saveActionResolutionDtl&followupId="+followupId;
			document.getElementById("ResolutionActionForm").submit();
			return true;
		
		}
}
//Changes End For add Resolution Screen

function getPDCSubmitCustomerName(){
//	alert("In getPDCSubmitCustomerName");
	var contextPath =document.getElementById('contextPath').value ;
	var submitBy= document.getElementById("applicantType").value;
	var lbxLoanNoHID=document.getElementById("loanId").value;
		var address = contextPath+"/getApplicantTypeInCollection.do?method=getNameOfPDCSubmitBy";
		var data = "lbxLoanNoHID="+lbxLoanNoHID+"&submitBy="+submitBy;
		send_PDCSubmitCustomerName(address,data);
				return true;		
}


function send_PDCSubmitCustomerName(address, data) {
	//alert(address+""+data);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_PDCSubmitCustomerName(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_PDCSubmitCustomerName(request){
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;	
	    var s1 = str.split("$:");
	   // alert("s1:::::"+s1.length)
	     document.getElementById('s4').innerHTML=str;
	     //bankDetailsRole();
	     if(document.getElementById("applicantType").value=="OTHER")
	     {
	    	 document.getElementById("s5").style.display="";
	    	 document.getElementById("s4").style.display="none";
	     }
	     else
	     {
	    	 document.getElementById("s4").style.display="";
	    	 document.getElementById("s5").style.display="none";
	     }
	}
	
}
function viewLegalInitiation(loanId,loanNo){
	
	var lbxLoanId=window.opener.document.getElementById("loanno").value;
	var contextPath=document.getElementById("contextPath").value;
	var url=contextPath+"/legalNoticeInitiationMaker.do?method=openLegalNoticeInitiationMakerView&lbxLoanId="+loanId;
	newWindow=window.open(url,'resolutionDtl','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	if (window.focus) 
	{
		newWindow.focus()
	}
	return true;
}
function saveLegalNoticeInitiationView(ufFlag){
	
	var reasonDesc= document.getElementById("reasonDesc").value;
	var lbxcustomerId= document.getElementById("lbxcustomerId").value;
	var makerRemarks= document.getElementById("makerRemarks").value;
	if(lbxcustomerId=='')
	{
		alert("Please first select Customer Name.")
		return false;
	}
	else if(makerRemarks=='')
	{
		alert("Maker Remarks Should not be blank.")
		return false;
	}
	else
	{
	document.getElementById("legalNoticeInitiationViewForm").action="legalNoticeInitiationMakerAdd.do?method=saveLegalNoticeInitiationView";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("legalNoticeInitiationViewForm").submit();
	return true;
	}
}