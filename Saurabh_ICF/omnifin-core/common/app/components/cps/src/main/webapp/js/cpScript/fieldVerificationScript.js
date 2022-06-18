function eerajnewAdd()
{
	var sourcepath=document.getElementById("contextPath").value;

	//var url=sourcepath+"/fieldVarificationInitiationAction.do?method=addFieldVerification";
	var url=sourcepath+"/JSP/CPJSP/fieldVarificationInitiation.jsp";
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	return true;
	}

function modifyFieldVerif(dealId)
{

	otherWindows = new Array();
	curWin = 0;
	var sourcepath=document.getElementById("contextPath").value;

	var url=sourcepath+"/fieldVarificationInitiationAction.do?method=openFieldVerification&dealId="+dealId+"&txntype=FVI";
	location.href=url;
	//alert(url);
	//var url=sourcepath+"/JSP/CPJSP/fieldVarificationInitiation.jsp";
	//otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//	alert(otherWindows[curWin++]);
	return true;
}

function newAddCapture()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("fieldVarificationCapSearchForm").action=sourcepath+"/fieldVarificationAction.do?method=addFieldCapture";
	document.getElementById("fieldVarificationCapSearchForm").submit();
	return true;
    document.getElementById("add").removeAttribute("disabled","true");
    return false;
	
}

function popUpJspField(customer_id,ver_sub_type,verification_id,appType)
{ 
	otherWindows = new Array();
	curWin = 0;
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/open_fieldVarification_ppopup.do?method=openPopup&customer_id="+customer_id+"&ver_sub_type="+ver_sub_type+"&verificationId="+verification_id+"&appType="+appType;
	otherWindows[curWin++] =window.open(url,'name','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].moveTo(800,400);
	if (window.focus()) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	return true;
}
function addressPopupUpdate(customer_id,addressType)
{ 
	var contextPath =document.getElementById('contextPath').value ;
	var verificationId=document.getElementById('verificationId').value ;
	document.getElementById("fieldVerAddForm").action=contextPath+"/open_fieldVarification_ppopup.do?method=addressPopupUpdate&customer_id="+customer_id+"&verificationId="+verificationId+"&addressType="+addressType;
	document.getElementById("fieldVerAddForm").submit();
}


function addressPopupUpdateCM(customer_id,addressType)
{ 
	var contextPath =document.getElementById('contextPath').value ;
	var verificationId=document.getElementById('verificationId').value ;
	document.getElementById("fieldVerAddForm").action=contextPath+"/fieldVarificationComAction.do?method=addressComPopup&customer_id="+customer_id+"&verificationId="+verificationId+"&addressType="+addressType;
	document.getElementById("fieldVerAddForm").submit();
}
function default_capture_data(deal_id)
{ 
	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVarificationCapSearchForm").action=contextPath+"/fieldVarificationAction.do?method=addFieldCapture&dealId="+deal_id;
	document.getElementById("fieldVarificationCapSearchForm").submit();
}
function default_completion_data(deal_id)
{ 
	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVarificationComSearchForm").action=contextPath+"/fieldVarificationComAction.do?method=addFieldComCapture&dealId="+deal_id;
	document.getElementById("fieldVarificationComSearchForm").submit();
}
function globleCheckBox()
{
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chkCases');
	var zx=0;
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
}
function deleteRecords()
{ 
	DisButClass.prototype.DisButMethod();
	var verificationId=document.getElementById("verificationId").value;	
	var ch=document.getElementsByName("chkCases");
	var addressList=document.getElementsByName("chkCases");
	var customer_idList=document.getElementsByName("customer_idList");
	var customer_id="";
	var id="";
	var addressTypeList=new Array();
	var j=0;
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
		{
			addressTypeList[j]=ch[i].value;
			customer_id=customer_idList[i].value;
			j++;
			id=i;
		}
	}
	if(id.length==0)
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var contextPath =document.getElementById("contextPath").value ;
	document.getElementById("fieldVerAddForm").action=contextPath+"/open_fieldVarification_ppopup.do?method=deleteAddressFieldRecord&addressTypeList="+addressTypeList+"&customer_id="+customer_id+"&verificationId="+verificationId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVerAddForm").submit();
}
function popUpComJsp(customer_id,ver_sub_type)
{
	otherWindows = new Array();
	curWin = 0;
   	var contextPath =document.getElementById('path').value ;
	var url=contextPath+"/fieldVarificationComAction.do?method=openComPopup&customer_id="+customer_id+"&ver_sub_type="+ver_sub_type;
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	
	return true;
}
function onLoadJsp()
{
	document.getElementById("CPVNegReasonDesc").value="";
	document.getElementById("lbxReasonId").value="";
	document.getElementById("appraisalDate").value="";
}

function forwardVerificationCap(fwdMsg)
{
	DisButClass.prototype.DisButMethod();
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var businessDate =document.getElementById('businessDate').value;
	var appraisalDate =document.getElementById('appraisalDate').value;
	var appType =document.getElementById('appType').value;
	var fieldVerificationUniqueId =document.getElementById('fieldVerificationUniqueId').value;
	if(fieldVerificationUniqueId=='')
	{
		alert("Please first Save ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
	{
		alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var day1= appraisalDate.substring(0,2);
	var day2= businessDate.substring(0,2);
	var month1=appraisalDate.substring(3,5);
	var month2=businessDate.substring(3,5);
	var year1=appraisalDate.substring(6);
	var year2=businessDate.substring(6);
			
	if(parseInt(year1)>parseInt(year2))
	{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		 if(parseInt(year1)==parseInt(year2))
		 {
			if(parseInt(month1)>parseInt(month2))
			{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			else 
			{
				if(parseInt(month1)==parseInt(month2))
				{
					if(parseInt(day1)>parseInt(day2))
					{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
	 		}
		}									
	 }

 	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=forwardVerificationCap";
 	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVerificationForm").submit();
}
function downloadVarificationForm()
{
	
	var entityType =document.getElementById('entityType').value ;
	var entitySubType =document.getElementById('entitySubType').value ;	
	var verificationId =document.getElementById('verificationId').value ;
	var entId =document.getElementById('entId').value ;
	var verificationType =document.getElementById('varificationType').value ;
	var verificationSubType =document.getElementById('varificationSubType').value ;
	var source='';
	
	if(entitySubType=='ADDRESS')
		source='A';
	if(entityType=='PROPERTY')
		source='B';
	if(entityType=='VEHICLE')
		source='C';
	if(entityType=='MACHINE')
		source='D';
	if(entityType=='OTHERS')
		source='E';
	if(entityType=='BG')
		source='F';
	if(entityType=='DEBTOR')
		source='G';
	if(entityType=='FD')
		source='H';
	if(entityType=='SBLC')
		source='I';
	if(entityType=='SECURITIES')
		source='J';
	if(entityType=='STOCK')
		source='K';
	if(entityType=='INSURANCE')
		source='L';
	if(entityType=='MARKET')
		source='M';
	if(entityType=='INCOME')
		source='N';
	if(entityType=='APPLICANT' || entityType=='APPLICATION' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR' || entityType=='COLLATERAL' || entityType=='ASSET')
		source='P';
	if((entityType=='APPLICANT' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR') && (verificationType=='CONSTITUTION VERIFICATION' && verificationSubType=='INDIVIDUAL CONSTITUTION'))
		source='Q';
	if((entityType=='APPLICANT' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR') && (verificationType=='CONSTITUTION VERIFICATION' && verificationSubType=='CORPORATE CONSTITUTION'))
		source='R'; 
	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=downloadVarificationForm&verificationId="+verificationId+"&entId="+entId+"&source="+source;
 	document.getElementById("fieldVerificationForm").submit();
}
function saveFieldVarCap()   
 {
	    DisButClass.prototype.DisButMethod();
	
		var businessDate =document.getElementById('businessdate').value;
		var formatD=document.getElementById("formatD").value;
		var appType =document.getElementById('appType').value;
		
		var appraisalName =document.getElementById('appraisalName').value;
		var appraisalDate =document.getElementById('appraisalDate').value;
		var verificationMode =document.getElementById('verificationMode').value;
		if(document.getElementById('dealDate')){
		var dealDate =document.getElementById('dealDate').value;
		var dtappraisalDate = getDateObject(appraisalDate,formatD.substring(2, 3));
		var dtdealDate=getDateObject(dealDate,formatD.substring(2, 3));
		if(dealDate!=''){
		if(dtdealDate>dtappraisalDate)
		{
			alert("Appraisal Date cannot be Less than Deal Creation Date");
			document.getElementById("appraisalDate").value='';
			document.getElementById("appraisalDate").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		}
		}
		if(document.getElementById('personToMeet')){
		var personToMeet =document.getElementById('personToMeet').value;
		}
		if(document.getElementById('relationWithApplicant')){
		var relationWithApplicant =document.getElementById('relationWithApplicant').value;
		}
		if(document.getElementById('phone1')){
		var phone1 =document.getElementById('phone1').value;
		}
		var cpvStatus =document.getElementById('cpvStatus').value;
		var remarks =document.getElementById('remarks').value;
		if(document.getElementById('hidRcuStatusString')){
		var hidRcuStatusString=document.getElementById('hidRcuStatusString').value;
		}
		if(document.getElementById('hidRcuCommentString')){
		var hidRcuCommentString=document.getElementById('hidRcuCommentString').value;
		}
		if(document.getElementById('hidDOCIDString')){
			var hidDOCIDString=document.getElementById('hidDOCIDString').value;
		}
		if(document.getElementById('hidChildIDString')){
		var hidChildIDString=document.getElementById('hidChildIDString').value;
		}		//alert("remarks " +remarks);
		var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='', msg8='',msg9='',msg10='',msg11='',msg12='';
		if(appraisalName=='')
		{
			msg1="* Appraiser Name is required. \n";
		}
		if(appraisalDate=='')
		{
			msg2="* Appraiser Date is required. \n";
		}
		if(verificationMode=='')
		{
			msg3="* Verification Mode is required. \n";
		}
		if(personToMeet=='')
		{
			msg4="* Person To Meet is required. \n";
		}
		if(relationWithApplicant=='')
		{
			msg5="* Relation With Applicant is required. \n";
		}
		/*if(phone1=='')
		{
			msg6="* Mobile No is required. \n";
		}*/
		if(cpvStatus=='')
		{
			msg7="* CPV Status is required. \n";
		}
	
		if(remarks=='')
		{
			msg8="* Remark is required. \n";
		}
		//alert("msg8 " +msg8);	
		if(document.getElementById("gridTable")!=null && document.getElementById("gridTable")!=undefined)
		{
			
			var table = document.getElementById("gridTable");	
			var rowCount = table.rows.length;
			
			if(rowCount > 1){		
				for(var i=1;i<rowCount;i++){
				var verifQuestRequired=document.getElementById('verifQuestRequired'+i).value;
				var questionRemarks = document.getElementById('questionRemarks'+i).value;
			 	var verificationMethod = document.getElementById('verificationMethod'+i).value;
			 		if(questionRemarks=='' && verifQuestRequired=='YES'){
						msg9="* Query Remarks is required. \n";
			 		}
			 		if(verificationMethod=='' && verifQuestRequired=='YES'){
						msg10="* Verification Method is required. \n";
			 		}
			   }	
		      }
		}
//amandeep work starts for RCU
		if(document.getElementById("gridTableRCU")!=null && document.getElementById("gridTableRCU")!=undefined)
			{
				var table = document.getElementById("gridTableRCU");	
				var rowCount = table.rows.length;
				if(rowCount > 1)
					{
						for(var i=1;i<rowCount;i++)
						{
							var RCUStatus=document.getElementById('RCUStatus'+i).value;
							var RCURemarks = document.getElementById('RCURemarks'+i).value;
							if(RCUStatus=='SA' && trim(RCURemarks)=='')
								{
									msg11="* RCU Remarks is required against 'Sampling' RCU status. \n";
								}
							
							if(RCUStatus=='SC' && trim(RCURemarks)!='')
							{
								msg12="* RCU Remarks is not required against 'Screening' RCU status. \n";
							}
						}
					}
			
			}
		//amandeep work end for RCU
		if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg8!=''||msg9!=''||msg10!=''||msg11!=''||msg12!='')
		{
			alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9+msg10+msg11+msg12);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//alert("appraisalDate "+appraisalDate+" \n businessDate "+businessDate);
		var appraDate=getDateObject(appraisalDate,formatD.substring(2, 3));
		var bDate=getDateObject(businessDate,formatD.substring(2, 3));
		//alert("appraDate "+appraDate+" \n bDate "+bDate);
		if(appraDate>bDate)
		{
			alert("Appraisal Date cannot be greater than Business Date");
			document.getElementById("appraisalDate").value='';
			document.getElementById("appraisalDate").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		/*if(appraisalDate.substring(2,3)!="-" || appraisalDate.substring(2,3)!="-")
		{
			alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var day1= appraisalDate.substring(0,2);
		var day2= businessDate.substring(0,2);
		var month1=appraisalDate.substring(3,5);
		var month2=businessDate.substring(3,5);
		var year1=appraisalDate.substring(6);
		var year2=businessDate.substring(6);
				
		if(parseInt(year1)>parseInt(year2))
		{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			 if(parseInt(year1)==parseInt(year2))
			 {
				if(parseInt(month1)>parseInt(month2))
				{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				else 
				{
					if(parseInt(month1)==parseInt(month2))
					{
						if(parseInt(day1)>parseInt(day2))
						{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}				
		 		}
			}									
		 }*/
	
	 	var contextPath =document.getElementById('contextPath').value ;
	 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=saveFieldVarCap&appType="+appType+"&hidRcuStatusString="+hidRcuStatusString+"&hidDOCIDString="+hidDOCIDString+"&hidChildIDString="+hidChildIDString;
	 	document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVerificationForm").submit();
	
 }
function maxLength(Object, MaxLen)
{
  return (Object.value.length <= MaxLen);
}

function saveFieldVarCapAdd()  
{
	DisButClass.prototype.DisButMethod();
	var verificationId =document.getElementById("verificationId").value ;
	if(validateFieldVarAddCapturingForm(document.getElementById("fieldVerAddForm")))
	{
		var businessDate =document.getElementById('businessDate').value;
		var appraisalDate =document.getElementById('appraisalDate').value;
		if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
		{
			alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var day1= appraisalDate.substring(0,2);
		var day2= businessDate.substring(0,2);
		var month1=appraisalDate.substring(3,5);
		var month2=businessDate.substring(3,5);
		var year1=appraisalDate.substring(6);
		var year2=businessDate.substring(6);
				
		if(parseFloat(year1)>parseFloat(year2))
		{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1)>parseFloat(month2))
				{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
				DisButClass.prototype.EnbButMethod();
					return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1)>parseFloat(day2))
						{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}				
		 		}
			}									
		 }
		var remarks =document.getElementById('remarks').value ;
		if(remarks.length >1000)
		{
			alert("Data too long for Maker Remarks");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("fieldVerAddForm").action=contextPath+"/fieldVerAddAction.do?method=saveFieldVarCap&verificationId="+verificationId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVerAddForm").submit();
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function updateFieldVarCapAdd()   
{	
	DisButClass.prototype.DisButMethod();
	var businessDate =document.getElementById('businessDate').value;
	var appraisalDate =document.getElementById('appraisalDate').value;
	if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
	{
		alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var day1= appraisalDate.substring(0,2);
	var day2= businessDate.substring(0,2);
	var month1=appraisalDate.substring(3,5);
	var month2=businessDate.substring(3,5);
	var year1=appraisalDate.substring(6);
	var year2=businessDate.substring(6);
		
	if(parseFloat(year1)>parseFloat(year2))
	{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
	DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		 if(parseFloat(year1)==parseFloat(year2))
		 {
			if(parseFloat(month1)>parseFloat(month2))
			{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
			DisButClass.prototype.EnbButMethod();
				return false;
			}
			else 
			{
				if(parseFloat(month1)==parseFloat(month2))
				{
					if(parseFloat(day1)>parseFloat(day2))
					{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
					DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
	 		}
		}									
	 }
	var remarks =document.getElementById('remarks').value ;
	if(remarks.length >1000)
	{
		alert("Data too long for Maker Remarks");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var verificationId=document.getElementById("verificationId").value ;
	
	if(validateFieldVarAddCapturingForm(document.getElementById("fieldVerAddForm")))
	{	var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("fieldVerAddForm").action=contextPath+"/fieldVerAddAction.do?method=updateFieldVarCap&verificationId"+verificationId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVerAddForm").submit();	
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}		
}
function closeSelfWindow()   
{
	self.close();
}
function forwardData()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("fieldVerificationForward").action=contextPath+"/fieldVarificationAction.do?method=forwardFieldVarCap";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVerificationForward").submit();
		
}

function updateFieldVarCap()
{	
	var businessDate =document.getElementById('businessDate').value;
	var appraisalDate =document.getElementById('appraisalDate').value;
	if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
	{
		alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
		return false;
	}
	
	var day1= appraisalDate.substring(0,2);
	var day2= businessDate.substring(0,2);
	var month1=appraisalDate.substring(3,5);
	var month2=businessDate.substring(3,5);
	var year1=appraisalDate.substring(6);
	var year2=businessDate.substring(6);		
	if(parseFloat(year1)>parseFloat(year2))
	{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
		return false;
	}
	else
	{
		 if(parseFloat(year1)==parseFloat(year2))
		 {
			if(parseFloat(month1)>parseFloat(month2))
			{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
				return false;
			}
			else 
			{
				if(parseFloat(month1)==parseFloat(month2))
				{
					if(parseFloat(day1)>parseFloat(day2))
					{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
						return false;
					}
				}				
	 		}
		}									
	 }
	var remarks =document.getElementById('remarks').value ;
	if(remarks.length >1000)
	{
		alert("Data too long for Maker Remarks");
		return false;
	}
	if(validateFieldVarificationCapturingForm(document.getElementById("fieldVerificationUpdationForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("fieldVerificationUpdationForm").action=contextPath+"/fieldVerificationUpdate.do?method=updateFieldVarCap";
		document.getElementById("fieldVerificationUpdationForm").submit();
	}
		
}


function verificationType(){
//alert(document.getElementById("type").value);
	if(document.getElementById("type").value=="F"){
	document.getElementById("trade").style.display='none';
	document.getElementById("collateral").style.display='none';
	document.getElementById("field").style.display='block';
	}
	else if(document.getElementById("type").value=="T"){
	document.getElementById("field").style.display='none';
	document.getElementById("collateral").style.display='none';
	document.getElementById("trade").style.display='block';
	}
	else if(document.getElementById("type").value=="C"){
	document.getElementById("field").style.display='none';
	document.getElementById("trade").style.display='none';
	document.getElementById("collateral").style.display='block';
	}
	return true;
	
}

function verType(){
	if(!(document.getElementById("lbxDealNo").value==''))
	{
		document.getElementById("check").style.display='block';
		document.getElementById("checkType").style.display='none';
	}	
	else
	{
		document.getElementById("check").style.display='none';
		document.getElementById("checkType").style.display='block';
	}
}
function newSearchInitaition()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	var custName=document.getElementById("customerName").value;
	var prod=document.getElementById("product").value!='';
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	if(reportingToUserId!='' || document.getElementById("dealNo").value!='' || document.getElementById("customerName").value!=''|| document.getElementById("product").value!='' || document.getElementById("scheme").value!='')
	{
		if(custName!='' && custName.length<3)
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else 
		{
			document.getElementById("fieldVarificationSearchForm").action=sourcepath+"/fieldVarificationBehindAction.do?method=verificationInitCreditProcessing";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("fieldVarificationSearchForm").submit();
			return true;
		}
	}else{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}





function modifyBuyerSupplier(a)
{

	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("buyerSupplierSearchForm").action=sourcepath+"/buyerSuppMainAction.do?method=modifyBuyerSupplier&dealID="+a+" ";
	document.getElementById("buyerSupplierSearchForm").submit();
	
	return true;
}


function modifyBuyerSupplierAuthor(a,b,c,d,e)
{

	var sourcepath=document.getElementById("contextPath").value;

	
	document.getElementById("buyerSupplierSearchForm").action=sourcepath+"/buyerSuppMainAction.do?method=modifyBuyerSupplierAuthor&dealID="+a+"&dealNo="+b+"&custrName="+c+"&dealDate="+d+"&rmName="+e;
	document.getElementById("buyerSupplierSearchForm").submit();
	
	return true;
}


function newBuyerSupplierEntry()
{
	          var sourcepath=document.getElementById("contextPath").value;
		
			document.getElementById("buyerSupplierSearchForm").action=sourcepath+"/buyerSuppMainAction.do?method=newBuyerSupplierDetails";
			document.getElementById("buyerSupplierSearchForm").submit();
			
}



function newSearchCapture()
{
	DisButClass.prototype.DisButMethod();
	var customerName=document.getElementById("customerName").value;
	var name=document.getElementById("reportingToUserId").value;
	if(customerName!="")
	{
		if(customerName.length<3)
		{ alert("Please Enter atleast 3 characters of Customer Name ")
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;	
	if(name=='' && document.getElementById("dealNo").value=='' && document.getElementById("customerName").value=='' && document.getElementById("initiationDate").value=='' && document.getElementById("product").value=='' && document.getElementById("scheme").value=='')
	{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		document.getElementById("fieldVarificationCapSearchForm").action=sourcepath+"/fieldVarificationAction.do?method=searchFieldCapture&userId="+name;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVarificationCapSearchForm").submit();	
	}
}
function newSearchComCapture()
{
	DisButClass.prototype.DisButMethod();
	
	var customerName=document.getElementById("customerName").value;
	if(customerName!="")
	{
		if(customerName.length<3)
		{ 
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
	
	if(document.getElementById("dealNo").value=='' && document.getElementById("customerName").value=='' && document.getElementById("initiationDate").value=='' && document.getElementById("product").value=='' && document.getElementById("scheme").value=='')
	{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

	else{
		document.getElementById("fieldVarificationComSearchForm").action=sourcepath+"/fieldVarificationComAction.do?method=searchFieldComCapture";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVarificationComSearchForm").submit();
	
	}
}
function allChecked()
{
	//alert("ok");
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
 	var len=ch.length;
    //alert(c);
 	if(len>10){
 		len=10;
 	}
	if(c==true)
	{
		for(i=0;i<len;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<len;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
}
function saveInitiation()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	//alert("Test: "+document.getElementsByName("contact"));
	
		var entityId=document.getElementsByName('entityId');
//		var initiatedCount=document.getElementById("initiatedCount").value;
//		var count=parseInt(initiatedCount);
		//alert("count "+count);
		
		//alert("contact "+contact.length);
		var actionType='N';
		var actionTypeCount='N';
		for(var i=0;i<entityId.length;i++)
		{
			if((document.getElementById("verificationAction"+(1+i)).value=='I')||(document.getElementById("verificationAction"+(1+i)).value=='W'))
			{
				actionType='Y';
				
			}
			if((document.getElementById("verificationAction"+(1+i)).value=='I'))
			{
				actionTypeCount='Y';
				
			}
		  if((document.getElementById("appraiserType"+(1+i)).value=='INTERNAL') )
			{
				if((document.getElementById("verificationAction"+(1+i)).value=='I') &&  (document.getElementById("internalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select internalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			else if((document.getElementById("appraiserType"+(1+i)).value=='EXTERNAL') )
			{
				if((document.getElementById("verificationAction"+(1+i)).value=='I') &&  (document.getElementById("externalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select externalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}

				
		}
	
	/*	if(actionTypeCount=='N' && count==0)
		{
			alert("At least one initiate ");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		if(actionType=='N')
		{
			
			
				alert("Please at least one initiate or waive");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			
		}
	
	document.getElementById("fieldVarificationIniationForm").action=sourcepath+"/fieldVarificationInitiationAction.do?method=saveFieldVerInitiation";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVarificationIniationForm").submit();
	return true;
}
function disableAction(i)
{ 	
//	if(((document.getElementById("appraiserTypeBoth"+i).value)=="BOTH"))
//	{
		if(((document.getElementById("verificationAction"+i).value)=="W") ||((document.getElementById("verificationAction"+i).value)=="P") ){
			
			//document.getElementById("appraiserType"+i).disabled=true;
		
			document.getElementById("externalAppraiser"+i).value="";
			document.getElementById("externalAppUserId"+i).value="";
			document.getElementById("externalButton"+i).disabled=true;	
			
		
			document.getElementById("internalAppUserId"+i).value="";
			document.getElementById("internalAppraiser"+i).value="";
			document.getElementById("internalButton"+i).disabled=true;
			return true;
		}else{
			//document.getElementById("appraiserType"+i).disabled=false;
	//		alert(document.getElementById("appraiser"+i).value);
			if((document.getElementById("appraiserType"+i).value)=="INTERNAL"){
				document.getElementById("externalButton"+i).disabled=true;	
				document.getElementById("internalButton"+i).disabled=false;
				return true;
			}else{
				document.getElementById("externalButton"+i).style.display='';
				document.getElementById("externalButton"+i).disabled=false;
				return true;
			}
		}
	//}
/*	else if(((document.getElementById("appraiserTypeBoth"+i).value)=="INTERNAL"))
	{
		if(((document.getElementById("action"+i).value)=="W") ||((document.getElementById("action"+i).value)=="P") ){
			
			
			document.getElementById("internalAppraiser"+i).value="";
			document.getElementById("internalAppUserId"+i).value="";
			document.getElementById("internalButton"+i).disabled=true;
		
		
		}else{
			
			
			document.getElementById("internalAppraiser"+i).value="";
			document.getElementById("internalAppUserId"+i).value="";
			document.getElementById("internalButton"+i).disabled=false;
		
			
		}
	}
	else if(((document.getElementById("appraiserTypeBoth"+i).value)=="EXTERNAL"))
	{
		if(((document.getElementById("action"+i).value)=="W") ||((document.getElementById("action"+i).value)=="P") ){
			
		
			document.getElementById("externalAppraiser"+i).value="";
			document.getElementById("externalAppUserId"+i).value="";
			document.getElementById("externalButton"+i).disabled=true;	
		
		
		}else{
			
			document.getElementById("externalAppraiser"+i).value="";
			document.getElementById("externalAppUserId"+i).value="";
			document.getElementById("externalButton"+i).disabled=false;	
		
			
		}
	}*/
}

function disableAppraisal(i)
{ 	
	if((document.getElementById("appraiserType"+i).value)=="INTERNAL" ||(document.getElementById("appraiserType"+i).value)=="I"){
		document.getElementById("internalAppraiser"+i).disabled=false;
		document.getElementById("externalAppraiser"+i).disabled=true;
		document.getElementById("externalAppUserId"+i).disabled=true;
		document.getElementById("externalAppraiser"+i).value="";
		document.getElementById("externalAppUserId"+i).value="";
		document.getElementById("externalButton"+i).disabled=true;	
		document.getElementById("internalButton"+i).disabled=false;
		return true;
	}else{
		document.getElementById("internalAppraiser"+i).disabled=true;
		document.getElementById("externalAppUserId"+i).disabled=false;
		document.getElementById("internalAppUserId"+i).value="";
		document.getElementById("internalAppraiser"+i).value="";
		document.getElementById("externalAppraiser"+i).disabled=false;
		document.getElementById("internalButton"+i).disabled=true;
		document.getElementById("externalButton"+i).style.display='';
		document.getElementById("externalButton"+i).disabled=false;
	 //alert();
		return true;
	}
}

function modifyInitiation()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("lbxDealNo").value;
	var a = document.getElementsByName('a');
	
	var varIdList="";
	var actionList="";
	var appraiserList ="";
	var lbxUseIdList ="";
	var lbxetApprHIDList ="";
			
		for(var i=0;i<a.length;i++)
			{
			if((document.getElementById("action"+(1+i)).value=='I') && (document.getElementById("appraiser"+(1+i)).value!='') &&  (document.getElementById("internalAppraiser"+(1+i)).value=='' && document.getElementById("externalAppraiser"+(1+i)).value==''))
			{
			//	alert("in first alert");
				alert("Please Select appraiser/internalAppraiser/externalAppraiser");
				document.getElementById("modifyButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}

			varIdList=varIdList +document.getElementById("a"+(1+i)).value+":";
			actionList=actionList +document.getElementById("action"+(1+i)).value+":";
			appraiserList=appraiserList +document.getElementById("appraiser"+(1+i)).value+" : ";
			lbxUseIdList=lbxUseIdList +document.getElementById("lbxUseId"+(1+i)).value+" : ";
			lbxetApprHIDList=lbxetApprHIDList +document.getElementById("lbxetApprHID"+(1+i)).value+" : ";

			}
		
	document.getElementById("fieldVarificationIniationForm").action=sourcepath+"/fieldVarificationInitiationAction.do?method=modifyFieldVerInitiation&varId="+varIdList+"&action="+actionList+"&appraiser="+appraiserList+"&lbxUseId="+lbxUseIdList+"&lbxetApprHID="+lbxetApprHIDList;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVarificationIniationForm").submit();
	return true;
}


function caseChangeAN()
{
	 document.getElementById("appraisalName").value=(document.getElementById("appraisalName").value).toUpperCase();
     return ( document.getElementById("appraisalName").value).toUpperCase();

}
function caseChangePM()
{
	 document.getElementById("personToMeet").value=(document.getElementById("personToMeet").value).toUpperCase();
     return ( document.getElementById("personToMeet").value).toUpperCase();
}
function caseChangeRA()
{
	 document.getElementById("relationWithApplicant").value=(document.getElementById("relationWithApplicant").value).toUpperCase();
     return ( document.getElementById("relationWithApplicant").value).toUpperCase();
}

function saveFiedlCheck()
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("textarea").value=="")
	{
		alert("Please Enter Remarks");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		document.getElementById("textarea").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var contextPath =document.getElementById('path').value ;
		document.getElementById("tradeRemarksForm").action=contextPath+"/fieldVerComBody.do?method=updateFieldCheck";
		document.getElementById("tradeRemarksForm").submit();
		return true;
	}
}

function detailEntity(entityId,verificationId)
{
	otherWindows = new Array();
	curWin = 0;
   	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fieldVarificationComAction.do?method=openComPopup&entityId="+entityId+"&verificationId="+verificationId;
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	
	return true;
}

function viewCapturedVerification(fieldVerificationId)

{
	otherWindows = new Array();
	curWin = 0;
   	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fieldVarificationComAction.do?method=openPopupViewCapturedVerif&fieldVerificationId="+fieldVerificationId;
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	
	return true;
}


function selectValForSendback(decision){
	otherWindows = new Array();
	curWin = 0;
	if(decision == "S"){
   	var contextPath =document.getElementById('path').value ;
	var url=contextPath+"/fieldVerComBody.do?method=viewCompletionTab&decision=S";
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
		{
			otherWindows[curWin++].focus();
			return false;
		}
	}
	else
	{
		document.getElementById("verificationCapId").value='';
		window.close();
	}
	return true;
}

function addCapturedVerification()
{
	
	var flag=0;
	var strId="";
	var ch=document.getElementsByName('chk');
 	//alert("length...."+ch.length);
 	 for(i=0;i<ch.length;i++)
 		{
 		 
		   if(ch[i].checked==true)
			{
 			   id=ch[i].value;
 			   strId=strId+id+"/";
			   //alert(id);
			   flag=1;
 			}
 		  
 		}
 
 	 if(flag==0)
 	 {
 		 alert("Please select atleast one Record");
 	 }
 	 else
 	 {
 	//	var status=document.getElementById("rowNo").value;
// 		var def = 'def'+status;
// 		//alert("strId is "+strId);
// 		window.opener.document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' value='' type='text' class='text4'>";
// 		window.opener.document.getElementById("status"+status).value='R';
 		window.opener.document.getElementById("verificationCapId").value=strId;
 		window.close();
 		
 	 }
 	
}

function newLoanSearchInitaition()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	var custName=document.getElementById("customerName").value;
	var prod=document.getElementById("product").value!='';
	//var reportingToUserId=document.getElementById("reportingToUserId").value;
	if(document.getElementById("loanNo").value!='' || document.getElementById("customerName").value!=''|| document.getElementById("product").value!='' || document.getElementById("scheme").value!='')
	{
		if(custName!='' && custName.length<3)
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else 
		{
			document.getElementById("fieldVarificationSearchForm").action=sourcepath+"/fieldVarificationBehindAction.do?method=verificationInitCreditManagement";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("fieldVarificationSearchForm").submit();
			return true;
		}
	}else{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function openLinkVerificationInitAtCm(loanId)
{

	otherWindows = new Array();
	curWin = 0;
	var sourcepath=document.getElementById("contextPath").value;

	var url=sourcepath+"/fieldVarificationInitiationAction.do?method=openLinkVerificationInitAtCm&loanId="+loanId+" ";
	location.href=url;

	return true;
}
function saveInitiationAtCM()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	//alert("Test: "+document.getElementsByName("contact"));
	
		var entityId=document.getElementsByName('entityId');
//		var initiatedCount=document.getElementById("initiatedCount").value;
//		var count=parseInt(initiatedCount);
		//alert("count "+count);
		
		//alert("contact "+contact.length);
		var actionType='N';
		var actionTypeCount='N';
		for(var i=0;i<entityId.length;i++)
		{
			if((document.getElementById("action"+(1+i)).value=='I')||(document.getElementById("action"+(1+i)).value=='W'))
			{
				actionType='Y';
				
			}
			if((document.getElementById("action"+(1+i)).value=='I'))
			{
				actionTypeCount='Y';
				
			}
		  if((document.getElementById("appraiserType"+(1+i)).value=='INTERNAL') )
			{
				if((document.getElementById("action"+(1+i)).value=='I') &&  (document.getElementById("internalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select internalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			else if((document.getElementById("appraiserType"+(1+i)).value=='EXTERNAL') )
			{
				if((document.getElementById("action"+(1+i)).value=='I') &&  (document.getElementById("externalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select externalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}

				
		}
	
	/*	if(actionTypeCount=='N' && count==0)
		{
			alert("At least one initiate ");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		if(actionType=='N')
		{
			
			
				alert("Please at least one initiate or waive");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			
		}
	
	document.getElementById("fieldVarificationIniationForm").action=sourcepath+"/fieldVarificationInitiationAction.do?method=saveFieldVerInitiationAtCM";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVarificationIniationForm").submit();
	return true;
}

function saveFieldVarCapAtCM()   
{
		DisButClass.prototype.DisButMethod();
	
		var businessDate =document.getElementById('businessDate').value;
		
		var appType =document.getElementById('appType').value;
		
		var appraisalName =document.getElementById('appraisalName').value;
		var appraisalDate =document.getElementById('appraisalDate').value;
		var verificationMode =document.getElementById('verificationMode').value;
		
		if(document.getElementById('personToMeet')){
			var personToMeet =document.getElementById('personToMeet').value;
			}
			if(document.getElementById('relationWithApplicant')){
			var relationWithApplicant =document.getElementById('relationWithApplicant').value;
			}
			if(document.getElementById('phone1')){
			var phone1 =document.getElementById('phone1').value;
			}
		var cpvStatus =document.getElementById('cpvStatus').value;
		var remarks =document.getElementById('remarks').value;
	   	var entitySubType =document.getElementById('entitySubType').value ;
		var hidRcuStatusString='';
	   	var hidRcuCommentString='';
	   	var hidDOCIDString='';
	   	var hidChildIDString='';
	   	if(document.getElementById('hidRcuStatusString')){
			 hidRcuStatusString=document.getElementById('hidRcuStatusString').value;
			}
			if(document.getElementById('hidRcuCommentString')){
			 hidRcuCommentString=document.getElementById('hidRcuCommentString').value;
			}
			if(document.getElementById('hidDOCIDString')){
				 hidDOCIDString=document.getElementById('hidDOCIDString').value;
			}
			if(document.getElementById('hidChildIDString')){
			 hidChildIDString=document.getElementById('hidChildIDString').value;
			}
		var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='', msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='';
		if(appraisalName=='')
		{
			msg1="* Appraiser Name is required. \n";
		}
		if(appraisalDate=='')
		{
			msg2="* Appraiser Date is required. \n";
		}
		if(verificationMode=='')
		{
			msg3="* Verification Mode is required. \n";
		}
		if(personToMeet=='')
		{
			msg4="* Person To Meet is required. \n";
		}
		if(relationWithApplicant=='')
		{
			msg5="* Relation With Applicant is required. \n";
		}
		/*if(phone1=='')
		{
			msg6="* Mobile No is required. \n";
		}*/
		if(cpvStatus=='')
		{
			msg7="* CPV Status is required. \n";
		}
		if(remarks=='')
		{
			msg8="* Appraiser's Remark is required. \n";
		}
if(document.getElementById("gridTableRCU")!=null && document.getElementById("gridTableRCU")!=undefined)
		{
			var table = document.getElementById("gridTableRCU");	
			var rowCount = table.rows.length;
			if(rowCount > 1)
				{
					for(var i=1;i<rowCount;i++)
					{
						var RCUStatus=document.getElementById('RCUStatus'+i).value;
						var RCURemarks = document.getElementById('RCURemarks'+i).value;
						if(RCUStatus=='SA' && trim(RCURemarks)=='')
							{
								msg13="* RCU Remarks is required against 'Sampling' RCU status. \n";
							}
		
						if(RCUStatus=='SC' && trim(RCURemarks)!='')
						{
							msg14="* RCU Remarks is not required against 'Screening' RCU status. \n";
						}
					}
				}
		
		}
		if(entitySubType!=null && (entitySubType=="ASSET" || entitySubType=="COLLATERAL"))
		{
			var assetLocation =document.getElementById('assetLocation').value;
			var assetCondition =document.getElementById('assetCondition').value;
			if(assetLocation=="")
			{
				msg11="* Asset Location is required. \n";
			}
			if(assetCondition=="")
			{
				msg12="* Asset Condition is required. \n";
			}
		}
		
		if(document.getElementById("gridTable")!=null && document.getElementById("gridTable")!=undefined)
		{
			
			var table = document.getElementById("gridTable");	
			var rowCount = table.rows.length;
			
			if(rowCount > 1){		
				for(var i=1;i<rowCount;i++){
				var verifQuestRequired=document.getElementById('verifQuestRequired'+i).value;
				var questionRemarks = document.getElementById('questionRemarks'+i).value;
			 	var verificationMethod = document.getElementById('verificationMethod'+i).value;
			 		if(questionRemarks=='' && verifQuestRequired=='YES'){
						msg9="* Query Remarks is required. \n";
			 		}
			 		if(verificationMethod=='' && verifQuestRequired=='YES'){
						msg10="* Verification Method is required. \n";
			 		}
			   }	
		      }
		}
		if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg8!=''||msg9!=''||msg10!=''||msg11!=''||msg12!=''||msg13!=''||msg14!='')
		{
			alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9+msg10+msg11+msg12+msg13+msg14);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
		{
			alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var day1= appraisalDate.substring(0,2);
		var day2= businessDate.substring(0,2);
		var month1=appraisalDate.substring(3,5);
		var month2=businessDate.substring(3,5);
		var year1=appraisalDate.substring(6);
		var year2=businessDate.substring(6);
				
		if(parseInt(year1)>parseInt(year2))
		{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			 if(parseInt(year1)==parseInt(year2))
			 {
				if(parseInt(month1)>parseInt(month2))
				{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				else 
				{
					if(parseInt(month1)==parseInt(month2))
					{
						if(parseInt(day1)>parseInt(day2))
						{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}				
		 		}
			}									
		 }
	
	 	var contextPath =document.getElementById('contextPath').value ;
	 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=saveFieldVarCapAtCM&appType="+appType;
	 	document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVerificationForm").submit();
	
}

function detailEntityAtCM(entityId,verificationId)
{
	otherWindows = new Array();
	curWin = 0;
   	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fieldVarificationComAction.do?method=openComPopupAtCM&entityId="+entityId+"&verificationId="+verificationId;
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	
	return true;
}

function forwardVerificationCapAtCM(fwdMsg)
{
	DisButClass.prototype.DisButMethod();
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var businessDate =document.getElementById('businessDate').value;
	var appraisalDate =document.getElementById('appraisalDate').value;
	var appType =document.getElementById('appType').value;
	var fieldVerificationUniqueId =document.getElementById('fieldVerificationUniqueId').value;
	if(fieldVerificationUniqueId=='')
	{
		alert("Please first Save ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(appraisalDate.substring(2,3)!="-" ||appraisalDate.substring(2,3)!="-")
	{
		alert("Please inter 'Appraisal Date' in (DD-MM-YYYY) formate");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var day1= appraisalDate.substring(0,2);
	var day2= businessDate.substring(0,2);
	var month1=appraisalDate.substring(3,5);
	var month2=businessDate.substring(3,5);
	var year1=appraisalDate.substring(6);
	var year2=businessDate.substring(6);
			
	if(parseInt(year1)>parseInt(year2))
	{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		 if(parseInt(year1)==parseInt(year2))
		 {
			if(parseInt(month1)>parseInt(month2))
			{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			else 
			{
				if(parseInt(month1)==parseInt(month2))
				{
					if(parseInt(day1)>parseInt(day2))
					{	alert("'Appraisal Date' should be less than or equal to 'Business Date'");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
	 		}
		}									
	 }

 	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=forwardVerificationCapAtCM";
 	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVerificationForm").submit();
}

function downloadVarificationFormAtCM()
{

	var entityType =document.getElementById('entityType').value ;
	var entitySubType =document.getElementById('entitySubType').value ;	
	var verificationId =document.getElementById('verificationId').value ;
	var entId =document.getElementById('entId').value ;
	var verificationType =document.getElementById('varificationType').value ;
	var verificationSubType =document.getElementById('varificationSubType').value ;
	var source='';
	if(entitySubType=='ADDRESS')
		source='A';
	if(entityType=='PROPERTY')
		source='B';
	if(entityType=='VEHICLE')
		source='C';
	if(entityType=='MACHINE')
		source='D';
	if(entityType=='OTHERS')
		source='E';
	if(entityType=='BG')
		source='F';
	if(entityType=='DEBTOR')
		source='G';
	if(entityType=='FD')
		source='H';
	if(entityType=='SBLC')
		source='I';
	if(entityType=='SECURITIES')
		source='J';
	if(entityType=='STOCK')
		source='K';
	if(entityType=='INSURANCE')
		source='L';
	if(entityType=='MARKET')
		source='M';
	if(entityType=='INCOME')
		source='N';
	if(entityType=='APPLICANT' || entityType=='APPLICATION' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR' || entityType=='COLLATERAL' || entityType=='ASSET')
		source='P';
	if((entityType=='APPLICANT' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR') && (verificationType=='CONSTITUTION VERIFICATION' && verificationSubType=='INDIVIDUAL CONSTITUTION'))
		source='Q';
	if((entityType=='APPLICANT' || entityType=='CO-APPLICANT' || entityType=='GUARANTOR') && (verificationType=='CONSTITUTION VERIFICATION' && verificationSubType=='CORPORATE CONSTITUTION'))
		source='R'; 

	var contextPath =document.getElementById('contextPath').value ;
 	document.getElementById("fieldVerificationForm").action=contextPath+"/fieldVerificationSave.do?method=downloadVarificationFormAtCM&verificationId="+verificationId+"&entId="+entId+"&source="+source;
 	document.getElementById("fieldVerificationForm").submit();
}

function newSearchCaptureAtCM()
{
	DisButClass.prototype.DisButMethod();
	var customerName=document.getElementById("customerName").value;
	var name='';
	//var name=document.getElementById("reportingToUserId").value;
	if(customerName!="")
	{
		if(customerName.length<3)
		{ alert("Please Enter atleast 3 characters of Customer Name ")
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;	
	if(document.getElementById("loanNo").value=='' && document.getElementById("customerName").value=='' && document.getElementById("initiationDate").value=='' && document.getElementById("product").value=='' && document.getElementById("scheme").value=='')
	{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		document.getElementById("fieldVarificationCapSearchForm").action=sourcepath+"/fieldVarificationAction.do?method=searchFieldCaptureAtCM&userId="+name;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVarificationCapSearchForm").submit();	
	}
}

function newSearchCompletionCaptureAtCM()
{
	DisButClass.prototype.DisButMethod();
	
	var customerName=document.getElementById("customerName").value;
	if(customerName!="")
	{
		if(customerName.length<3)
		{ 
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
	
	if(document.getElementById("loanNo").value=='' && document.getElementById("customerName").value==''  && document.getElementById("product").value=='' && document.getElementById("scheme").value=='')
	{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

	else{
		document.getElementById("fieldVarificationComSearchForm").action=sourcepath+"/fieldVarificationComAction.do?method=searchVerificationCompletionAtCM";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fieldVarificationComSearchForm").submit();
	
	}
}

function viewCapturedVerificationAtCM(fieldVerificationId)

{
	otherWindows = new Array();
	curWin = 0;
   	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fieldVarificationComAction.do?method=openPopupViewCapturedVerifAtCM&fieldVerificationId="+fieldVerificationId;
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
	{
		otherWindows[curWin++].focus();
		return false;
	}
	
	return true;
}

function saveFiedlCheckAtCM()
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("textarea").value=="")
	{
		alert("Please Enter Remarks");
		document.getElementById("saveButton").removeAttribute("disabled","true");
		document.getElementById("textarea").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var contextPath =document.getElementById('path').value ;
		document.getElementById("tradeRemarksForm").action=contextPath+"/fieldVerComBody.do?method=updateFieldCheckAtCM";
		document.getElementById("tradeRemarksForm").submit();
		return true;
	}
}

function selectValForSendbackAtCM(decision){
	otherWindows = new Array();
	curWin = 0;
	if(decision == "S"){
   	var contextPath =document.getElementById('path').value ;
	var url=contextPath+"/fieldVerComBody.do?method=viewCompletionTabAtCM&decision=S";
	//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
	otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
	otherWindows[curWin++].focus();
	otherWindows[curWin++].moveTo(800,300);
	if (window.focus) 
		{
			otherWindows[curWin++].focus();
			return false;
		}
	}
	else
	{
		document.getElementById("verificationCapId").value='';
		window.close();
	}
	return true;
}

function enableOrDisableField()
{
   	var entitySubType =document.getElementById('entitySubType').value ;
   	var viewMode =document.getElementById('viewMode').value ;
   	if(entitySubType!=null && (entitySubType=="ASSET" || entitySubType=="COLLATERAL"))
   	{
		document.getElementById("seenAtAssetOrCollateral").style.display='';
		document.getElementById("seenAtAstOrCol").style.display='';
	    if(viewMode!=null && viewMode=="")
	    {
	    	document.getElementById("assetLocation").removeAttribute("disabled",true);
	    	document.getElementById("assetCondition").removeAttribute("disabled",true);
	    	document.getElementById("invoiceCollected").removeAttribute("disabled",true);
	    	document.getElementById("invoiceNumber").removeAttribute("disabled",true);
	    }
	    else
	    {
	    	document.getElementById("assetLocation").setAttribute("disabled",true);
	    	document.getElementById("assetCondition").setAttribute("disabled",true);
	    	document.getElementById("invoiceCollected").setAttribute("disabled",true);
	    	document.getElementById("invoiceNumber").setAttribute("disabled",true);
		
	    }
	
   	}
   	else
   	{
		document.getElementById("seenAtAssetOrCollateral").style.display='none';
		document.getElementById("seenAtAstOrCol").style.display='none';
		
		document.getElementById("assetLocation").setAttribute("disabled",true);
		document.getElementById("assetCondition").setAttribute("disabled",true);
		document.getElementById("invoiceCollected").setAttribute("disabled",true);
		document.getElementById("invoiceNumber").setAttribute("disabled",true);
   	}
	
}

function modifyVerificationCompletion(dealId)
{

	otherWindows = new Array();
	curWin = 0;
	var sourcepath=document.getElementById("contextPath").value;

	var url=sourcepath+"/varifComplFieldVerificationSaveAction.do?method=openVerificationCompletionModule&dealId="+dealId;
	location.href=url;
	//alert(url);
	//var url=sourcepath+"/JSP/CPJSP/fieldVarificationInitiation.jsp";
	//otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//	alert(otherWindows[curWin++]);
	return true;
}

function disableDecision(i)
{ 	

		if(document.getElementById("verfDecisionArray"+i).value=="" ){
	
			document.getElementById("externalAppraiser"+i).value="";
			document.getElementById("externalAppUserId"+i).value="";
			document.getElementById("externalButton"+i).disabled=true;	
			
		
			document.getElementById("internalAppUserId"+i).value="";
			document.getElementById("internalAppraiser"+i).value="";
			document.getElementById("internalButton"+i).disabled=true;
			return true;
		}else{

			if((document.getElementById("appraiserType"+i).value)=="INTERNAL"){
				document.getElementById("externalButton"+i).disabled=true;	
				document.getElementById("internalButton"+i).disabled=false;
				return true;
			}else{
				document.getElementById("externalButton"+i).style.display='';
				document.getElementById("externalButton"+i).disabled=false;
				return true;
			}
		}
	
}

function saveCompletionModule()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	//alert("Test: "+document.getElementsByName("contact"));
	
		var entityId=document.getElementsByName('entityId');
	//	var verfDecisionArray=document.getElementById("verfDecisionArray").value;
//		var count=parseInt(initiatedCount);
		//alert("count "+count);
		
		//alert("contact "+contact.length);
		var actionType='N';
		var actionTypeCount='N';
		for(var i=0;i<entityId.length;i++)
		{
			if((document.getElementById("action"+(1+i)).value=='I') && (document.getElementById("verfDecisionArray"+(1+i)).value!=''))
			{
				actionType='Y';
				
			}
			if((document.getElementById("action"+(1+i)).value=='I') && (document.getElementById("verfDecisionArray"+(1+i)).value!=''))
			{
				actionTypeCount='Y';
				
			}
		  if((document.getElementById("appraiserType"+(1+i)).value=='INTERNAL') )
			{
				if((document.getElementById("action"+(1+i)).value=='I' ) && (document.getElementById("verfDecisionArray"+(1+i)).value!='') &&  (document.getElementById("internalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select internalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			else if((document.getElementById("appraiserType"+(1+i)).value=='EXTERNAL') )
			{
				if((document.getElementById("action"+(1+i)).value=='I') && (document.getElementById("verfDecisionArray"+(1+i)).value!='') &&  (document.getElementById("externalAppraiser"+(1+i)).value==''))
				{
				
					alert("Please Select externalAppraiser");
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}

				
		}
	
		if(actionType=='N')
		{
			
			
				alert("Please at least one initiate or waive");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			
		}
	
	document.getElementById("fieldVarificationIniationForm").action=sourcepath+"/varifComplFieldVerificationSaveAction.do?method=saveVarifComplFieldVerification";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("fieldVarificationIniationForm").submit();
	return true;
}

function forwardCompletionModule()
{
	   DisButClass.prototype.DisButMethod();
	
	    var sourcepath=document.getElementById("contextPath").value;
		var entityId=document.getElementsByName('entityId');
		if(entityId.length==0)
		{
			
			if(confirm("Are you want to complete current stage ?"))
			{
				location.href=sourcepath+"/varifComplFieldVerificationSaveAction.do?method=forwardVarifComplFieldVerification";
				return true;
			}
			else
			{
				document.getElementById("forwardButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			location.href=sourcepath+"/varifComplFieldVerificationSaveAction.do?method=forwardVarifComplFieldVerification";
			return true;
		}
}

function parentDocWithRCU()
{
				var rcuStatusStr="";
				var rcuRemarksStr="";
				var DOCIDStr="";
				var firstParentId="";
				var secondParentId="";
				var delimeter='';
				var rcuChildStr="";
				var table = document.getElementById("gridTableRCU");	
				var rowCount = table.rows.length;
				if(rowCount > 1)
					{
						for(var i=1;i<rowCount;i++)
						{
							var RCUStatus=document.getElementById('RCUStatus'+i).value;
							var v_statusHidObj=document.getElementById('parentDocID'+i).value;
							var v_ChildHidObj=document.getElementById('childDocID'+i).value;
							var RCURemarks = document.getElementById('RCURemarks'+i).value;
							
							
							
									delimeter="~";
									rcuStatusStr=rcuStatusStr+delimeter+RCUStatus;
									rcuRemarksStr=rcuRemarksStr+RCURemarks+delimeter;
									DOCIDStr=DOCIDStr+delimeter+v_statusHidObj;
									rcuChildStr=rcuChildStr+v_ChildHidObj+delimeter;
									if (rcuStatusStr.substr(0,1) == "~") {
										rcuStatusStr = rcuStatusStr.substring(1);
									}
									/*if (rcuRemarksStr.substr(0,1) == "~") {
										rcuRemarksStr = rcuRemarksStr.substring(1);
									}*/
									if (DOCIDStr.substr(0,1) == "~") {
										DOCIDStr = DOCIDStr.substring(1);
									}
									
									/*if (rcuChildStr.substr(0,1) == "~") {
										rcuChildStr = rcuChildStr.substring(1);
									}*/
									document.getElementById('hidRcuStatusString').value='';
									document.getElementById('hidRcuCommentString').value='';
									document.getElementById('hidDOCIDString').value='';
									document.getElementById('hidChildIDString').value='';
									document.getElementById('hidRcuStatusString').value=rcuStatusStr;
									document.getElementById('hidRcuCommentString').value=rcuRemarksStr;
									document.getElementById('hidDOCIDString').value=DOCIDStr;
									document.getElementById('hidChildIDString').value=rcuChildStr;
									//alert(rcuStatusStr);
									//alert(rcuRemarksStr);
									//alert(DOCIDStr);
						}
					}
}
function disabledRemarks(){
	var rcuStatusStr="";
	var rcuRemarksStr="";
	var DOCIDStr="";
	var firstParentId="";
	var secondParentId="";
	var delimeter='';
	var rcuChildStr="";
	var table = document.getElementById("gridTableRCU");	
	var rowCount = table.rows.length;
	if(rowCount > 1)
		{
			for(var i=1;i<rowCount;i++)
			{
				var RCUStatus=document.getElementById('RCUStatus'+i).value;
				
				var RCURemarks = document.getElementById('RCURemarks'+i).value;
				if(document.getElementById('RCUStatus'+i).value=='SC'){
					document.getElementById('RCURemarks'+i).value=" ";
					document.getElementById('RCURemarks'+i).setAttribute("readonly",true);
				}else{
					
					document.getElementById('RCURemarks'+i).removeAttribute("readOnly","true");
				}
				
				
						
			}
		}
}