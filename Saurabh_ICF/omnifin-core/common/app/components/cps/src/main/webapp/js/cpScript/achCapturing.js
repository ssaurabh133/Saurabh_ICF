
 function achDecisionvalue()
 {
	var decisionvalue=document.getElementById("achDecision").value;
	
	if (decisionvalue=="C")
	{
		openLOVCommon(16105,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo');
		
	}
	if (decisionvalue=="M")
	{
		openLOVCommon(192033,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo');
		
	}
	if (decisionvalue=="X")
	{
		openLOVCommon(192033,'achCapturingSearchForm','txtDealNo','','', '','','fetchACHCustomerDetail','txtCustomerName','hidDealNo');
		
	}
	
 }
 
function searchACHData()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	if (document.getElementById("txtDealNo").value!="")
	{
		document.getElementById("achCapturingSearchForm").action = contextPath+"/achCapturingAction.do?method=searchACHCapturingRecords";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("achCapturingSearchForm").submit();
	}
	else{
		alert("Deal Number is required.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function newACHData()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("achCapturingSearchForm").action = contextPath+"/achCapturingAction.do?method=newACHRecord";
	document.getElementById("processingImage").style.display = '';
    document.getElementById("achCapturingSearchForm").submit();
}

function saveACHData()
{
	DisButClass.prototype.DisButMethod();
	var decisionvalue=document.getElementById("achDecision").value;
	var contextPath =document.getElementById('contextPath').value ;

	if (decisionvalue=="C")
	{ 
		if (validateACHSaveUpdateData("Save"))
	{
		document.getElementById("achCapturingSearchForm").action = contextPath+"/achCapturingAction.do?method=saveNewACHRecord";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("achCapturingSearchForm").submit();
	} else	{
		DisButClass.prototype.EnbButMethod();
	}
		
	}
	if (decisionvalue=="M"||decisionvalue=="X")
	{

		if (validateACHSaveUpdateData("Save"))
	{
		document.getElementById("achCapturingSearchForm").action = contextPath+"/achCapturingAction.do?method=udpateACHRecordForExist";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("achCapturingSearchForm").submit();
	} else	{
		DisButClass.prototype.EnbButMethod();
	}
		
	}
	
	
}


/*function saveACHTrackingData()
{
	alert("Save ACH Tracking Data");
}*/

function saveACHTrackingData()
{
	//sanjay sharma 
	var dt1="";
	var dt2="";
	var sentDt = document.getElementById("dteSentDateNonEditable").value;

	var receivedDt = document.getElementById("dteReceivedDate").value;
	var selACHStatus = document.getElementById("selACHStatus").value;
	if (selACHStatus == "RV" && sentDt != '' && receivedDt != '') {
			var formatD=document.getElementById("formatD").value;
			dt1=getDateObject(sentDt,formatD.substring(2,3));
			dt2=getDateObject(receivedDt,formatD.substring(2,3));
	}
 
	// end sanjay sharma
		
	if (!document.getElementById("hidAchCapturingId") || document.getElementById("hidAchCapturingId").value =="")
	{
		alert("Save ACH Capturing data first.");
	}
	else if(dt2<dt1)
			{
				DisButClass.prototype.DisButMethod();
				alert("Received date can't be less than sent date!"); 
				DisButClass.prototype.EnbButMethod();
			}
	else{
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		if (validateACHSaveUpdateData("SaveACHTrackingData"))
		{
			document.getElementById("achTrackingDataForm").action = contextPath+"/achCapturingAction.do?method=saveNewACHTrackingRecord";
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("achTrackingDataForm").submit();
		} else	{
			DisButClass.prototype.EnbButMethod();
		}
	}
	
}

function printACHData()
{
	if (!document.getElementById("hidDealNo"))
	{
		alert("Save ACH data first.");
	}
	else {
	var sourcepath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("hidDealNo").value;	
	var reportName=document.getElementById("reportName").value;	
	//alert(dealId);
	//alert("In open openCamReport"+undDealId);
	//lockButtonOnClick("achTrackingDataForm");
		document.getElementById("achCapturingSearchForm").action=sourcepath+"/CPReportsAction.do?dealId="+dealId+"&source=CP&reportName="+reportName;
		document.getElementById("achCapturingSearchForm").submit();
		Window.location.reload();
	}
}

function validateACHSaveUpdateData(action)
{
	var msgMandatory="";
	if (action=="Save")
	{
		if (document.getElementById("txtDealNo").value=="")
		{
			msgMandatory += "* Deal Number" + "\n";
		}
		
		if (document.getElementById("txtCustomerName").value=="")
		{
			msgMandatory += "* Customer Name" + "\n";
		}
		
		if (document.getElementById("dteDate").value=="")
		{
			msgMandatory += "* Date" + "\n";
		}
		if (document.getElementById("txtWeHerebyAuthorize").value=="")
		{
			msgMandatory += "* We Hereby Authorize" + "\n";
		}
		if (document.getElementById("txtBankAccountNo").value=="")
		{
			msgMandatory += "* Bank Account No" + "\n";
		}
		if (document.getElementById("txtBankName").value=="")
		{
			msgMandatory += "* Bank Name" + "\n";
		}
		if (document.getElementById("txtBankBranchName").value=="")
		{
			msgMandatory += "* Branch Name" + "\n";
		}
		if (document.getElementById("txtMicr").value=="")
		{
			msgMandatory += "* MICR Code" + "\n";
		}
		if (document.getElementById("txtIfsc").value=="")
		{
			msgMandatory += "* IFSC Code" + "\n";
		}
		if (document.getElementById("txtLoanAmount").value=="")
		{
			msgMandatory += "* Loan Amount" + "\n";
		}
		if (document.getElementById("txtTotalAmount").value=="")
		{
			msgMandatory += "* Amount" + "\n";
		}
		
		if (document.getElementById("txtPhoneNo").value=="")
		{
			msgMandatory += "* Phone no" + "\n";
		}
/*		if (document.getElementById("txtEmailId").value=="")
		{
			msgMandatory += "* E-Mail Id" + "\n";
		}*/
		if (document.getElementById("selFrequency").value=="")
		{
			msgMandatory += "* Frequency" + "\n";
		}
		/*if (document.getElementById("dteFromDate").value=="")
		{
			msgMandatory += "* From Date" + "\n";
		}
		if (document.getElementById("dteToDate").value=="")
		{
			msgMandatory += "* To Date" + "\n";
		}*/
		
		var email = document.getElementById("txtEmailId")
		if( email.value!=""  ){
			var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
			if(!valid_email.test(email.value)){
				msgMandatory += "* Invalid Email Id" + "\n";
			}
		}
		
		//	Check for from date to be less than to date
		fromDate = document.getElementById("dteFromDate").value;
		toDate = document.getElementById("dteToDate").value;
		if(fromDate!="" && toDate!="")
		{
			var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(fromDate,formatD.substring(2,3));
			var dt2=getDateObject(toDate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}
		}
	} else if (action=="SaveACHTrackingData") {
		
		if(document.getElementById("selACHStatus").value=="")
		{
			msgMandatory += "* ACH Status" + "\n";
		}
		if(document.getElementById("txtRemarks").value=="")
		{
			msgMandatory += "* Remarks " + "\n";
		}
		
		if (document.getElementById("selACHStatus").value=="SR")
		{
			if(document.getElementById("txtselVendorName").value=="")
			{
				msgMandatory += "* Vendor Name" + "\n";
			}
			if(document.getElementById("dteSentDate").value=="")
			{
				msgMandatory += "* Sent Date " + "\n";
			}
			if(document.getElementById("txtLotNo").value=="")
			{
				msgMandatory += "* Lot Number" + "\n";
			}
		}
		if (document.getElementById("selACHStatus").value=="RV")
		{
			if(document.getElementById("dteReceivedDate").value=="")
			{
				msgMandatory += "* Received Date " + "\n";
			}
			if(document.getElementById("selACHReceivedStatus").value=="")
			{
				msgMandatory += "* Received Status" + "\n";
			}
			if(document.getElementById("selACHReceivedStatus").value=="A" && document.getElementById("txtUMRN").value=="" )
			{
				msgMandatory += "* UMRN No is mandatory with status Approved" + "\n";
			}
			if(document.getElementById("selACHReceivedStatus").value=="A" && document.getElementById("txtUMRN").value.length!="20" )
			{
				msgMandatory += "* UMRN No should of 20 Digits" + "\n";
			}
		}
	}
	
	if (msgMandatory!="")
	{
		alert("Following fields are Mandatory : \n" + msgMandatory);
		return false;
	}
	return true;
}


function udpateACHData()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	if (validateACHSaveUpdateData("Save"))
	{
		document.getElementById("achCapturingSearchForm").action = contextPath+"/achCapturingAction.do?method=udpateACHRecord";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("achCapturingSearchForm").submit();
	} else	{
		DisButClass.prototype.EnbButMethod();
	}
}

function deleteACHData()
{

if (!document.getElementById("hidAchCapturingId"))
	{
		alert("Save ACH data first.");
	}
	else {
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var achCapturingId=document.getElementById("hidAchCapturingId").value;
	agree=confirm("Are You Sure,You Want To Delete This ACH. Do You Want To Continue?");
	if (!(agree))
	{
    	document.getElementById("Save").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("achCapturingSearchForm").action=sourcepath+"/achCapturingAction.do?method=deleteACHData&achCapturingId="+achCapturingId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("achCapturingSearchForm").submit();
		return true;
	}
}
}
function clearBankBranchValues()
{
	document.getElementById("txtBankBranchName").value = "";
	document.getElementById("hidBankBranchName").value = "";
}

function checkBankNameForBranch()
{
	if (document.getElementById("txtBankName").value=="")
	{
		alert("Select Bank Name First.");
		return false;
	} else {
		 openLOVCommon(16099,'achCapturingSearchForm','txtBankBranchName','txtBankName','hidBankBranchName', 'hidBankName','Please Select Bank','','txtMicr','txtIfsc','hidBankBranchName');
	}
}


function enableDisableFields()
{
	
	if (document.getElementById("selACHStatus").value == "SR")
	{
		
		document.getElementById("dteReceivedDate").style.display="none";
		document.getElementById("dteReceivedDate").value="";
		document.getElementById("dteReceivedDate").parentNode.childNodes[2].style.display="none";
		document.getElementById("dteReceivedDateNonEditable").style.display="";
		document.getElementById("txtUMRN").setAttribute("disabled","true");
		document.getElementById("selACHReceivedStatus").setAttribute("disabled","true");
		document.getElementById("btnVendor").style.display="";
		document.getElementById("dteSentDate").style.display="";
		document.getElementById("dteSentDate").value="";
		document.getElementById("dteSentDateNonEditable").style.display="none";
		document.getElementById("txtLotNo").removeAttribute("readonly","false");
		document.getElementById("dteSentDate").parentNode.childNodes[2].style.display="";
	}
	else if(document.getElementById("selACHStatus").value == "RV")
	 {
		document.getElementById("txtRemarks").value="";
		document.getElementById("txtLotNo").setAttribute("readonly","true");
		document.getElementById("btnVendor").style.display="none";
		document.getElementById("dteSentDate").style.display="none";
		//document.getElementById("dteSentDate").value="";
		document.getElementById("dteSentDate").parentNode.childNodes[2].style.display="none";
		document.getElementById("dteSentDateNonEditable").style.display="";
		document.getElementById("selACHReceivedStatus").removeAttribute("disabled","false");
		
		document.getElementById("txtUMRN").removeAttribute("disabled","false");
		document.getElementById("dteReceivedDate").style.display="";
		document.getElementById("dteReceivedDate").value="";
		document.getElementById("dteReceivedDate").parentNode.childNodes[2].style.display="";
		document.getElementById("dteReceivedDateNonEditable").style.display="none";
	 }else{
		 	document.getElementById("txtLotNo").setAttribute("readonly","true");
		 	document.getElementById("btnVendor").style.display="none";
			document.getElementById("dteSentDate").style.display="none";
			document.getElementById("dteSentDate").parentNode.childNodes[2].style.display="none";
			document.getElementById("dteSentDateNonEditable").style.display="";
			document.getElementById("selACHReceivedStatus").removeAttribute("disabled","false");
			document.getElementById("txtUMRN").setAttribute("disabled","true");
			document.getElementById("dteReceivedDate").style.display="none";
			document.getElementById("dteReceivedDate").value="";
			document.getElementById("dteReceivedDate").parentNode.childNodes[2].style.display="none";
			document.getElementById("dteReceivedDateNonEditable").style.display="";
			document.getElementById("selACHReceivedStatus").setAttribute("disabled","true");
	 }
}