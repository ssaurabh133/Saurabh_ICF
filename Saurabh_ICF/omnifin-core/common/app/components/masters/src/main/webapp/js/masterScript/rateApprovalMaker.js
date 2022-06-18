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

function addRateApprovalMaker(){ 
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("rateApprovalMakerForm").action=sourcepath+"/rateMatrixMakerDispatchAction.do?method=rateApprovalMakerAdd";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("rateApprovalMakerForm").submit();
	return true; 
}

function searchRateApprovalMaker(val){
	var searchStatus=document.getElementById("searchByStatus");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("rateApprovalMakerForm").action=sourcepath+"/rateMatrixMakerDispatchAction.do?method=rateApprovalMakerSearch";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("rateApprovalMakerForm").submit();
	return true;
	
}


function saveRateApproval(val)
{
		
	DisButClass.prototype.DisButMethod();
	var productId = document.getElementById("lbxProductID");
	var schemeId = document.getElementById("lbxScheme");
/*  var creditInsuranceCvr = document.getElementById("creditInsuranceCover");
	var dob = document.getElementById("dob");
	var typeofcovererage = document.getElementById("typeOfCoverage");
	var sumAssuredLoan= document.getElementById("sumAssuredToLoan");
	var csliPremiumAmt= document.getElementById("csliPremiumAmount");
	var creditInsTenor= document.getElementById("creditInsuranceTenor");
	var gInsurance= document.getElementById("generalInsurance");
	var csgiPremiumAmt= document.getElementById("csgiPremiumAmount");
	var anyOtherIns= document.getElementById("anyOtherInsurance");
	var ifYesOtherAmt= document.getElementById("ifyesOtherAmount");
	var csoiPremiumAmt= document.getElementById("csoiPremiumAmount");
	var transactionFee= document.getElementById("transactionFee");
*/
	var rackRate= document.getElementById("rackRate");
	var rackProcessingFee= document.getElementById("rackProcessingFee");
	
		
/*	if (trim(productId.value) == ''||trim(schemeId.value) == ''||trim(creditInsuranceCvr.value) == ''||trim(typeofcovererage.value) == ''
		||trim(dob.value) == ''||trim(sumAssuredLoan.value) == ''||trim(csliPremiumAmt.value) == ''||trim(creditInsTenor.value) == ''
		||trim(gInsurance.value) == ''||trim(csgiPremiumAmt.value) == ''||trim(anyOtherIns.value) == ''||trim(csoiPremiumAmt.value) == ''
		||trim(transactionFee.value) == ''||trim(rackRate.value) == ''||trim(rackProcessingFee.value) == '') */
	if (trim(productId.value) == ''||trim(schemeId.value) == ''||trim(rackRate.value) == ''||trim(rackProcessingFee.value) == '')
	{
		var msg= '';
		if(trim(productId.value) == '')
			msg += '* Product Id is required.\n';
		
		if(trim(schemeId.value) == '')
			msg += '* Scheme Id is Required.\n';
	/*	
		if(trim(creditInsuranceCvr.value) == '')
			msg += '* Credit Insurance Coverage is required.\n';
		
		if(trim(typeofcovererage.value) == '')
			msg += '* Type Of Coverage is required.\n';
		
		if(trim(dob.value) == '')
			msg += '* Date of Birth is required.\n';
		
		if(trim(sumAssuredLoan.value) == '')
			msg += '* Sum Of Assured Loan is required.\n';
		
		if(trim(csliPremiumAmt.value) == '')
			msg += '* CSLI-Premium Amount is required.\n';
		
		if(trim(creditInsTenor.value) == '')
			msg += '* Credit Insurance  Tenor is required.\n';
		
		if(trim(gInsurance.value) == '')
			msg += '* General Insurance is required.\n';
		
		if(trim(csgiPremiumAmt.value) == '')
			msg += '* CSGI Premium Amount is required.\n';
		
		if(trim(anyOtherIns.value) == '')
			msg += '* Any Other Insurance is required.\n';
		
		if(trim(csoiPremiumAmt.value) == '')
			msg += '* CSOI Premium Amount is required.\n';
		
		if(trim(transactionFee.value) == '')
			msg += '* Transaction Fee is required.\n';
		
	*/
		if(trim(rackRate.value) == '')
			msg += '* Rack Rate is required.\n';
		
		if(trim(rackProcessingFee.value) == '')
			msg += '* Rack Processing Fee is required.\n';
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		
		return false;
	}
	var formatD=document.getElementById("formatD").value;
//	var dobDate=getDateObject(dob.value,formatD.substring(2, 3));

	var businessDate= document.getElementById("businessdate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));

		/*	if(dobDate>dtBusiness)
				{
					alert("Date Of Birth cannot be greater than Business Date");
					document.getElementById("dob").value='';
					document.getElementById("dob").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
	
			if (anyOtherIns=='Y' && ifYesOtherAmt=='')
			{
				alert("Product sold is Required");
				document.getElementById("ifyesOtherAmount").value='';
				document.getElementById("ifyesOtherAmount").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		*/	
	var saveForwardFlag=val;
	//alert("saveForwardFlag "+saveForwardFlag);
	document.getElementById("saveForwardFlag").value=saveForwardFlag;
	
	//alert("saveForwardFlag "+document.getElementById("saveForwardFlag").value);
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("rateApprovalMakerForm").action=sourcepath+"/rateMatrixMakerDispatchAction.do?method=rateApprovalMakerSave";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("rateApprovalMakerForm").submit();
	return true;
	
}

function updateRateApproval(val){
	
	//  alert("updateRateApproval()");
	DisButClass.prototype.DisButMethod();
	var productId = document.getElementById("lbxProductID").value;
	var schemeId = document.getElementById("lbxScheme").value;
	/*
	var creditInsuranceCvr = document.getElementById("creditInsuranceCover").value;
	var dob = document.getElementById("dob").value;
	var typeofcovererage = document.getElementById("typeOfCoverage").value;
	var sumAssuredLoan= document.getElementById("sumAssuredToLoan").value;
	var csliPremiumAmt= document.getElementById("csliPremiumAmount").value;
	var creditInsTenor= document.getElementById("creditInsuranceTenor").value;
	var gInsurance= document.getElementById("generalInsurance").value;
	var csgiPremiumAmt= document.getElementById("csgiPremiumAmount").value;
	var anyOtherIns= document.getElementById("anyOtherInsurance").value;
	var ifYesOtherAmt= document.getElementById("ifyesOtherAmount").value;
	var csoiPremiumAmt= document.getElementById("csoiPremiumAmount").value;
	var transactionFee= document.getElementById("transactionFee").value;
	*/
	var rackRate= document.getElementById("rackRate").value;
	var rackProcessingFee= document.getElementById("rackProcessingFee").value;
		
		
	/*	if (trim(productId) == ''||trim(schemeId) == ''||trim(creditInsuranceCvr) == ''||trim(typeofcovererage) == ''
			||trim(dob) == ''||trim(sumAssuredLoan) == ''||trim(csliPremiumAmt) == ''||trim(creditInsTenor) == ''
			||trim(gInsurance) == ''||trim(csgiPremiumAmt) == ''||trim(anyOtherIns) == ''||trim(csoiPremiumAmt) == ''
			||trim(transactionFee) == ''||trim(rackRate) == ''||trim(rackProcessingFee) == '')  */
	if (trim(productId) == ''||trim(schemeId) == ''||trim(rackRate) == ''||trim(rackProcessingFee) == '') 
		{
			var msg= '';
			if(trim(productId) == '')
				msg += '* Product Id is required.\n';
			
			if(trim(schemeId) == '')
				msg += '* Scheme Id is Required.\n';
		/*	
			if(trim(creditInsuranceCvr) == '')
				msg += '* Credit Insurance Coverage is required.\n';
			
			if(trim(typeofcovererage) == '')
				msg += '* Type Of Coverage is required.\n';
			
			if(trim(dob) == '')
				msg += '* Date of Birth is required.\n';
			
			if(trim(sumAssuredLoan) == '')
				msg += '* Sum Of Assured Loan is required.\n';
			
			if(trim(csliPremiumAmt) == '')
				msg += '* CSLI-Premium Amount is required.\n';
			
			if(trim(creditInsTenor) == '')
				msg += '* Credit Insurance  Tenor is required.\n';
			
			if(trim(gInsurance) == '')
				msg += '* General Insurance is required.\n';
			
			if(trim(csgiPremiumAmt) == '')
				msg += '* CSGI Premium Amount is required.\n';
			
			if(trim(anyOtherIns) == '')
				msg += '* Any Other Insurance is required.\n';
			
			if(trim(csoiPremiumAmt) == '')
				msg += '* CSOI Premium Amount is required.\n';
			
			if(trim(transactionFee) == '')
				msg += '* Transaction Fee is required.\n';
		*/	
			if(trim(rackRate) == '')
				msg += '* Rack Rate is required.\n';
			
			if(trim(rackProcessingFee) == '')
				msg += '* Rack Processing Fee is required.\n';
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			
			return false;
		}
		var formatD=document.getElementById("formatD").value;
	
		//var dobDate=getDateObject(dob,formatD.substring(2, 3));

		var businessDate= document.getElementById("businessdate").value;
		var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));

		/*		if(dobDate>dtBusiness)
					{
						alert("Date Of Birth cannot be greater than Business Date");
						document.getElementById("dob").value='';
						document.getElementById("dob").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
		
		if (anyOtherIns=='Y' && ifYesOtherAmt=='')
		{
			alert("Product sold is Required");
			document.getElementById("ifyesOtherAmount").value='';
			document.getElementById("ifyesOtherAmount").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		*/
		var saveForwardFlag=val;
	//	alert("saveForwardFlag "+saveForwardFlag);
		document.getElementById("saveForwardFlag").value=saveForwardFlag
		
		// alert("saveForwardFlag "+document.getElementById("saveForwardFlag").value);
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("rateApprovalMakerForm").action=sourcepath+"/rateMatrixMakerDispatchAction.do?method=rateApprovalMakerUpdate";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rateApprovalMakerForm").submit();
		return true;
}

function saveRateApprovalChecker(){
	var decision= document.getElementById("decision").value;
	var comments = document.getElementById("comments").value;
	if(comments==''){
		  alert(" Comments  is  Required  ");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		 }
	   var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("rateApprovalCheckerDynaValidatorForm").action=sourcepath+"/rateMatrixCheckerDispatchAction.do?method=saveRateApprovalChecker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rateApprovalCheckerDynaValidatorForm").submit();
		return true;	
	
}

function hideAsterik(value){
	  
	  if(value!='A')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}

function productReadOnly()
{
	var otherInsuranceSold = document.getElementById("anyOtherInsurance").value;
	
	if(otherInsuranceSold =='N')
	{
		
		document.getElementById("ifyesOtherAmount").readOnly = true;
		document.getElementById("ifyesOtherAmount").value =" ";
	}
	else
	{
		
		document.getElementById("ifyesOtherAmount").readOnly = false;
	}
}
function checkRate()
{
	var rate = document.getElementById("sumAssuredToLoan").value;
	 if(rate=='')
	{
		rate=0;
		 alert("Please Enter the value");
	}
	    var intRate = parseFloat(rate);
	  // alert(intRate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }
	      else
	        {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById("sumAssuredToLoan").value ="0.00";
		
		        return false;
	         }
	  }

function checkRackProcessRate()
{

	var rate = document.getElementById("rackProcessingFee").value;
	 if(rate=='')
	{
		rate=0;
		 alert("Please Enter the value");
	}
	    var intRate = parseFloat(rate);
	  // alert(intRate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }
	      else
	        {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById("rackProcessingFee").value ="0.00";
		
		        return false;
	         }
	  
}

function checkRackRate()
{
	var rate = document.getElementById("rackRate").value;
	 if(rate=='')
	{
		rate=0;
		 alert("Please Enter the value");
	}
	    var intRate = parseFloat(rate);
	  // alert(intRate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }
	      else
	        {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById("rackRate").value ="0.00";
		
		        return false;
	         }
	  }