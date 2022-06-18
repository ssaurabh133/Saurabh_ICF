var vehicleNoExpr_numeric=/^[a-zA-Z'0-9]{1,50}$/;
var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
function fnUpdateAsset(flag){
	
	DisButClass.prototype.DisButMethod();
	var saveflag= document.getElementById("saveflag").value;
	if(saveflag=='N' && flag=='F')
	{
		alert("Please Save First.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var contextPath= document.getElementById("contextPath").value;
	var loanId= document.getElementById("lbxLoanId").value;
	var vehicleYearOfManufact = document.getElementById("vehicleYearOfManufact");
	var saveFirst= document.getElementById("saveFirst").value;
	var lbxAssetId=document.getElementById("assetId").value;
	var businessdate=document.getElementById("businessdate").value;
	//Nishant space starts
	var invoiceUpdate=document.getElementById("invoiceUpdateCheckBox").checked;
	var invoiceNumber=document.getElementById("invoiceNumber").value;
	var vehicleInvoiceDate=document.getElementById("vehicleInvoiceDate").value;
	var invoiceAmount=document.getElementById("invoiceAmount").value;
	var vehicleChesisNo=document.getElementById("vehicleChesisNo").value;
	var engineNumber=document.getElementById("engineNumber").value;
	
	var rcUpdateCheckBox=document.getElementById("rcUpdateCheckBox").checked;
	var rcReceived=document.getElementById("rcReceived").value;
	var vehicleRegNo=document.getElementById("vehicleRegNo").value;
	var vehicleRegDate=document.getElementById("vehicleRegDate").value;
	var permitReceived=document.getElementById("permitReceived").value;
	var permitReceivedDate=document.getElementById("permitReceivedDate").value;
	var insuranceUpdateCheckBox=document.getElementById("insuranceUpdateCheckBox").checked;
	var insuranceAgency=document.getElementById("insuranceAgency").value;	//Nishant space ends
	// var vehicleInsurer=document.getElementById("vehicleInsurer").value;
	var vehicleInsureDate=document.getElementById("vehicleInsureDate").value;
	//Nishant space ends
	var msg= '';

	 if(loanId == '')
			msg += '* Loan No is required.\n';
	 if(lbxAssetId == '')
			msg += '* Vehicle Description  is required.\n';
	 
	 if(invoiceUpdate)	
	 {
		 /*if(invoiceNumber == '' && vehicleInvoiceDate==''  && invoiceAmount=='' && vehicleChesisNo=='' && engineNumber=='' )
				msg += '* At least one field of Invoice Update is required.\n';*/
		
		if(invoiceAmount=='')
			msg+= '* Invoice Amount is Required. \n';
		 
		 if(vehicleChesisNo=='')
				msg+= '* Vehicle Chesis Number is Required. \n';
		 
		 if(engineNumber=='')
				msg+= '* Vehicle Engine Number is Required. \n';
			}
	 if(rcUpdateCheckBox)
	 {
		 if(rcReceived == '')
				msg += '* RC Received is required.\n';
		 if(rcReceived == 'Y')
		 {
			 if(vehicleRegNo == '')
					msg += '* Registration Number is required.\n';
			 if(vehicleRegDate == '')
					msg += '* Registration Date is required.\n';
		 }
		 if(vehicleYearOfManufact.value == '')
				msg += '* Year of Manufacturing is required.\n';
		 if(permitReceived=='Y')
		 {
			 if(permitReceivedDate == '')
					msg += '* Permit Expiry Date is required.\n';
		 }
	 }
	 if(insuranceUpdateCheckBox)
	 {
		 if(insuranceAgency == '')
				msg += '* Insurer is required.\n';
		 if(vehicleInsureDate == '')
				msg += '* Insured Date is required.\n';
	 }
		if(msg!='')
		{
			
			if(msg.match("Loan")){
				document.getElementById("loanNoButton").focus();
	 		}
	 		else if(msg.match("Vehicle")){
//	 			document.getElementById("assetButton").focus();
	 		}else if(msg.match("Manufacturing")){
	 			vehicleYearOfManufact.focus();
	 		}
		
	 		alert(msg);
	 		DisButClass.prototype.EnbButMethod();
	 		return false;
 		}
		
		var monthManf=vehicleYearOfManufact.value.substring(0, 2);
		var yearManf=vehicleYearOfManufact.value.substring(3, 7);
		
		var monthBdate=businessdate.substring(3, 5);
		var yearBdate=businessdate.substring(6, 10);
		
		/*if(parseInt(monthManf)>parseInt(monthBdate) && parseInt(yearManf)<parseInt(yearBdate))
		{
			 alert("Year Of Manufacturing(MM-YYYY) can not greater that business date");
		 	 document.getElementById("vehicleYearOfManufact").value='';
		 	 DisButClass.prototype.EnbButMethod();
   	 	     return false;
		}
		if(parseInt(monthManf)<parseInt(monthBdate) && parseInt(yearManf)>parseInt(yearBdate))
		{
			 alert("Year Of Manufacturing(MM-YYYY) can not greater that business date");
		 	 document.getElementById("vehicleYearOfManufact").value='';
		 	 DisButClass.prototype.EnbButMethod();
   	 	     return false;
		}*/
		
 		  var vehicleRegNo=document.getElementById("vehicleRegNo");
		  if (!vehicleNoExpr_numeric.test(vehicleRegNo.value)) {
			 if(trim(vehicleRegNo.value) != ''){
			 	var msg = "* Registration Number is invalid.";
			 	alert(msg);
			 	 document.getElementById("vehicleRegNo").value='';
			 	 DisButClass.prototype.EnbButMethod();
	    	 	 return false;
			 }
		  }
		  
		  
		  var rcReceived=document.getElementById("rcReceived").value;
		    if(rcReceived=='Y')
		    {
		    	  var rcReceivedDate=document.getElementById("rcReceivedDate").value;
		    	  if(rcReceivedDate=='')
		    	  {
		    		  alert("RC Received Date is required.");
		    		  document.getElementById("rcReceivedDate").focus();
		    		  DisButClass.prototype.EnbButMethod();
		    	 	  return false;
		    	  }
		    	    var rcReceivedDate=document.getElementById("rcReceivedDate").value;
		    		var businessdate=document.getElementById("businessdate").value;
		    		var formatD=document.getElementById("formatD").value;
		    		
		    		var dtpermitReceivedDate=getDateObject(rcReceivedDate,formatD.substring(2, 3));
		    		var dtbusinessdate=getDateObject(businessdate,formatD.substring(2, 3));
		    		
		    		if(dtpermitReceivedDate>dtbusinessdate)
		    		{
		    			alert("RC Received Date must be less than or equal to Business Date");
		    			document.getElementById("rcReceivedDate").value='';
		    			document.getElementById("rcReceivedDate").focus();
		    		    DisButClass.prototype.EnbButMethod();
			    	 	return false;
		    		}
		    }
		    else
		    {
		    	 document.getElementById("rcReceivedDate").value='';
		    }

		    var permitReceived=document.getElementById("permitReceived").value;
		    if(permitReceived=='Y')
		    {
		    	  var permitReceivedDate=document.getElementById("permitReceivedDate").value;
		    	  if(permitReceivedDate=='')
		    	  {
		    		  alert("Permit Expiry Date is required.");
		    		  document.getElementById("permitReceivedDate").focus();
		    		  DisButClass.prototype.EnbButMethod();
		    	 	  return false;
		    	  }
		    	    var permitReceivedDate=document.getElementById("permitReceivedDate").value;
		    		var businessdate=document.getElementById("businessdate").value;
		    		var formatD=document.getElementById("formatD").value;
		    		
		    		var dtpermitReceivedDate=getDateObject(permitReceivedDate,formatD.substring(2, 3));
		    		var dtbusinessdate=getDateObject(businessdate,formatD.substring(2, 3));
		    		var permitExpiryDate= document.getElementById("permitExpiryDate").value;
		    		//alert("permitExpiryDate: "+permitExpiryDate+" dtpermitReceivedDate: "+dtpermitReceivedDate);
		    		if(permitExpiryDate!='')
		    		{
		    		
		    			var dtpermitExpiryDate=getDateObject(permitExpiryDate,formatD.substring(2, 3));
		    		
		    			if(dtpermitExpiryDate<dtpermitReceivedDate)
			    		{
			    			
			    			if(dtpermitReceivedDate<dtbusinessdate)
				    		{
			    				alert("Permit Expiry Date must be greater than Business Date");
				    			document.getElementById("permitReceivedDate").value='';
				    			document.getElementById("permitReceivedDate").focus();
				    		    DisButClass.prototype.EnbButMethod();
					    	 	return false;
				    		}
				    		
			    		}
		    		}
		    		else
		    		{
		    		
		    			if(dtpermitReceivedDate<dtbusinessdate)
			    		{
		    				alert("Permit Expiry Date must be greater than Business Date");
			    			document.getElementById("permitReceivedDate").value='';
			    			document.getElementById("permitReceivedDate").focus();
			    		    DisButClass.prototype.EnbButMethod();
				    	 	return false;
			    		}
		    		}
		    		
		    	  
		    		
		    }
		    else
		    {
		    	 document.getElementById("permitReceivedDate").value='';
		    }
		    //Nishant space starts
		    if(!invoiceUpdate && !rcUpdateCheckBox && !insuranceUpdateCheckBox)
		    {
		    	alert("At least one section must be checked to save the record.");
    		    DisButClass.prototype.EnbButMethod();
	    	 	return false;
		    }
		    //Nishant space ends
		    var invoiceAmount= removeComma(document.getElementById("invoiceAmount").value);
			var assetsCollateralValue= removeComma(document.getElementById("assetsCollateralValue").value);
			if(invoiceAmount!=assetsCollateralValue)
			{
				if(confirm("Vehicle Net Cost is not matching with Invoice amount. Do you want to continue?"))
				{

					
					document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+flag;

					document.getElementById("processingImage").style.display = '';
					document.getElementById("updateform").submit();
		        	return true;
				}
				else
				{
					document.getElementById("save").removeAttribute("disabled","true");
			 		DisButClass.prototype.EnbButMethod();
			 		return false;
				}
			}
			else
			{
				document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+flag;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("updateform").submit();
	        	return true;
			}

}
function saveUpdateAssetChecker()
{
	var decision = document.getElementById("authorDecission").value;
	var comment = document.getElementById("comments").value;
	if(comment=='')
	{
		alert("Comment is Required");
		return false;
	}
	document.getElementById("updateAssetAuthorFrame").action="updateAssetAuthorFrame.do?method=saveAuthorDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("updateAssetAuthorFrame").submit();
	return true;
}


function fnSearchUpdateAsset(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("updateAssetForm").action="updateAssetSearch.do";
	if(document.getElementById("lbxLoanId").value=='' && document.getElementById("asset").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("updateAssetForm").submit();
	return true;
	}
}
function fnSearchUpdateAuthorAsset(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("updateAssetAuthorForm").action="updateAssetAuthor.do";
	if(document.getElementById("lbxLoanId").value=='' && document.getElementById("asset").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("updateAssetAuthorForm").submit();
	return true;
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}




function validate(formName){
	
	var caseTypeCode = document.getElementById("caseTypeCode");
	
  

 var errors = [];
 

 if (!ck_numeric.test(caseTypeCode.value)) {
	 if(trim(caseTypeCode.value) != ''){
	 	errors[errors.length] = "* Case Type Code is invalid.";
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
	 	if(msg.match("Case Type Code") ){
	 		caseTypeCode.focus();
		
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}

function rcDateMandatory()
{
  var rcReceived=document.getElementById("rcReceived").value;
   if(rcReceived=='N')
  {
	  document.getElementById("rcReceivedDate").value='';

  }
   
}


function receivedDateMandatory()
{
  var permitReceived=document.getElementById("permitReceived").value;
   if(permitReceived=='N')
  {
	  document.getElementById("permitReceivedDate").value='';

  }
   
}

function saveAfterChange()
{
	document.getElementById("saveflag").value='N';	
}

function fnNewUpdateAsset()
{
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	document.getElementById("updateAssetForm").action="updateAssetEdit.do?method=openNewUpdateAsset";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("updateAssetForm").submit();
}


function invoiceUpdateActivate()
{
	//alert("invoiceUpdateActivate");
	//document.getElementById("invoiceNumber").value='';
	//document.getElementById("vehicleInvoiceDate").value='';
	//document.getElementById("invoiceAmount").value='';
	//document.getElementById("vehicleChesisNo").value='';
	//document.getElementById("engineNumber").value='';
	var invoiceUpdateCheckBox=document.getElementById('invoiceUpdateCheckBox');
	if(invoiceUpdateCheckBox.checked==true)
	{
		document.getElementById("invoiceNumber").removeAttribute("readOnly","true");
		document.getElementById("vehicleInvoiceDate").removeAttribute("readOnly","true");
		document.getElementById("invoiceAmount").removeAttribute("readOnly","true");
		document.getElementById("vehicleChesisNo").removeAttribute("readOnly","true");
		document.getElementById("engineNumber").removeAttribute("readOnly","true");
		
	}
	else
	{
		
		document.getElementById("invoiceNumber").setAttribute("readOnly","true");
		document.getElementById("vehicleInvoiceDate").setAttribute("readOnly","true");
		document.getElementById("invoiceAmount").setAttribute("readOnly","true");
		document.getElementById("vehicleChesisNo").setAttribute("readOnly","true");
		document.getElementById("engineNumber").setAttribute("readOnly","true");
	
	}
}

function rcUpdateActivate()
{
	//alert("rcUpdateActivate");
	//document.getElementById("rcReceived").value='';
	//document.getElementById("rcReceivedDate").value='';
	//document.getElementById("vehicleRegNo").value='';
	//document.getElementById("vehicleRegDate").value='';
	//document.getElementById("vehicleYearOfManufact").value='';
	
	//document.getElementById("vehicleOwner").value='';
	//document.getElementById("permitReceived").value='';
	//document.getElementById("permitReceivedDate").value='';
	//document.getElementById("permitExpiryDate").value='';
	
	var rcUpdateCheckBox=document.getElementById('rcUpdateCheckBox');
	if(rcUpdateCheckBox.checked==true)
	{
		document.getElementById("rcReceived").removeAttribute("readOnly","true");
		document.getElementById("rcReceivedDate").removeAttribute("readOnly","true");
		document.getElementById("vehicleRegNo").removeAttribute("readOnly","true");
		document.getElementById("vehicleRegDate").removeAttribute("readOnly","true");
		document.getElementById("vehicleYearOfManufact").removeAttribute("readOnly","true");
		
		document.getElementById("vehicleOwner").removeAttribute("readOnly","true");
		document.getElementById("permitReceived").removeAttribute("readOnly","true");
		document.getElementById("permitReceivedDate").removeAttribute("readOnly","true");
		document.getElementById("permitExpiryDate").removeAttribute("readOnly","true");
		document.getElementById("endorsementdate").removeAttribute("readOnly","true");
		
		
		
		
	}
	else
	{
		
		document.getElementById("rcReceived").setAttribute("readOnly","true");
		document.getElementById("rcReceivedDate").setAttribute("readOnly","true");
		document.getElementById("vehicleRegNo").setAttribute("readOnly","true");
		document.getElementById("vehicleRegDate").setAttribute("readOnly","true");
		document.getElementById("vehicleYearOfManufact").setAttribute("readOnly","true");
		document.getElementById("vehicleOwner").setAttribute("readOnly","true");
		document.getElementById("permitReceived").setAttribute("readOnly","true");
		document.getElementById("permitReceivedDate").setAttribute("readOnly","true");
		document.getElementById("permitExpiryDate").setAttribute("readOnly","true");
		
		
		
			
	}
}

function insuranceUpdateActivate()
{
	//alert("insuranceUpdateActivate");
	//document.getElementById("vehicleInsurer").value='';
	//document.getElementById("vehicleInsureDate").value='';
	//document.getElementById("idv").value='';
	
	
	var insuranceUpdateCheckBox=document.getElementById('insuranceUpdateCheckBox');
	if(insuranceUpdateCheckBox.checked==true)
	{
		document.getElementById("insuranceAgency").removeAttribute("readOnly","true");
		document.getElementById("vehicleInsureDate").removeAttribute("readOnly","true");
		document.getElementById("idv").removeAttribute("readOnly","true");
		document.getElementById("renewaldate").removeAttribute("readOnly","true");
		document.getElementById("premamt").removeAttribute("readOnly","true");
		document.getElementById("premdate").removeAttribute("readOnly","true");
	
	}
	else
	{
		
		document.getElementById("insuranceAgency").setAttribute("readOnly","true");
		document.getElementById("vehicleInsureDate").setAttribute("readOnly","true");
		document.getElementById("idv").setAttribute("readOnly","true");
		document.getElementById("renewaldate").removeAttribute("readOnly","true");
		document.getElementById("premamt").removeAttribute("readOnly","true");
		document.getElementById("premdate").removeAttribute("readOnly","true");
		
	}
}

function changeInvoiceUpdate()
{
	document.getElementById("saveflag").value='N';	
	var invoiceUpdateCheckBox=document.getElementById("invoiceUpdateCheckBox");
	if(invoiceUpdateCheckBox.checked==false)
	{
		document.getElementById("vehicleInvoiceDate").value ='';
		alert("First check Invoice Update");
	}
}


function changeRcUpdate()
{

	var rcUpdateCheckBox=document.getElementById("rcUpdateCheckBox");
	if(rcUpdateCheckBox.checked==false)
	{
		document.getElementById("rcReceived").value ='';
		document.getElementById("rcReceivedDate").value ='';
		document.getElementById("vehicleRegDate").value ='';
		document.getElementById("permitReceived").value ='';
		document.getElementById("permitReceivedDate").value ='';
		document.getElementById("permitExpiryDate").value ='';
		alert("First check RC Update");
	}
}
function changeInsuranceUpdate()
{

	var insuranceUpdateCheckBox=document.getElementById("insuranceUpdateCheckBox");
	if(insuranceUpdateCheckBox.checked==false)
	{
		document.getElementById("vehicleInsureDate").value ='';
		alert("First check Insurance Update");
	}
}

//Nishant space starts
function getPddUpdateData(val)
{
	var id='';
	if(val == 'INVOICE')
		id='invoiceUpdateCheckBox';
	else if(val == 'RC')
		id='rcUpdateCheckBox';
	else if(val == 'INSURANCE')
		id='insuranceUpdateCheckBox';
	
	var flag=document.getElementById(id).checked;
	var loanId= document.getElementById('lbxLoanId').value;
	var assetId=document.getElementById("assetId").value;
	var updateFlag='';
	var basePath=document.getElementById("contextPath").value;
	
	if(loanId != '' && assetId != '')
	{
		if(flag)
			updateFlag='Y';
		else
			updateFlag='N';
		
		var address = basePath+"/ajaxAction.do?method=getPDDUpdateData";
		var data = "updateVal="+val+"&assetId="+assetId+"&updateFlag="+updateFlag;
		sendRequestPDDUpdateData(address,data);
		return true;
	}
	else
	{
		alert("Loan No and Vehicle Description both are required.");
   	 	return true;
	}
}

function sendRequestPDDUpdateData(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodPDDUpdateData(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodPDDUpdateData(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
				if(trim(s1[20]) == 'INVOICE')
				{
					document.getElementById('invoiceNumber').value=trim(s1[0]);
					document.getElementById('vehicleInvoiceDate').value=trim(s1[1]);
					document.getElementById('invoiceAmount').value=trim(s1[2]);
					document.getElementById('vehicleChesisNo').value=trim(s1[3]);
					document.getElementById('engineNumber').value=trim(s1[4]);
				}
				else if(trim(s1[20]) == 'RC')
				{
					document.getElementById('rcReceived').value=trim(s1[5]);
					document.getElementById('rcReceivedDate').value=trim(s1[6]);
					document.getElementById('vehicleRegNo').value=trim(s1[7]);
					document.getElementById('vehicleRegDate').value=trim(s1[8]);
					document.getElementById('vehicleYearOfManufact').value=trim(s1[9]);
					document.getElementById('vehicleOwner').value=trim(s1[10]);
					document.getElementById('permitReceived').value=trim(s1[11]);
					document.getElementById('permitReceivedDate').value=trim(s1[12]);
					document.getElementById('endorsementdate').value=trim(s1[13]);
				}
				else if(trim(s1[20]) == 'INSURANCE')
				{
					document.getElementById('insuranceAgency').value=trim(s1[14]);
					document.getElementById('vehicleInsureDate').value=trim(s1[15]);
					document.getElementById('idv').value=trim(s1[16]);
					
					document.getElementById('renewaldate').value=trim(s1[17]);
					document.getElementById('premamt').value=trim(s1[18]);
					document.getElementById('premdate').value=trim(s1[19]);
				}
				
	    }
		
	}
}

function fnDeleteAsset(){
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var saveflag= document.getElementById("saveflag").value;
	var loanId= document.getElementById("lbxLoanId").value;
	var lbxAssetId=document.getElementById("assetId").value;
	//alert("saveflag......"+saveflag);
	if(saveflag=='N')
	{
		alert("Please Save First.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	agree=confirm("Are you sure,You want to delete this PDD.Do you want to continue?");
	if (!(agree))
	{	
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		document.getElementById("updateform").action="updateAssetEdit.do?method=deletePddAsset&loanId="+loanId+"&assetId="+lbxAssetId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("updateform").submit();
    	return true;
	}
}
//Nishant space ends