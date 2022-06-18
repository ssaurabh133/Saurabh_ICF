function saveLoanDetailForEmiCalc(formName) {
	DisButClass.prototype.DisButMethod();
	var effRate=0.00;
	var productTypeFlag = document.getElementById("productTypeFlag").value; 
	  
	if ((productTypeFlag != '' && productTypeFlag == 'A') || productTypeFlag == 'N' || productTypeFlag == 'Y') {
			
		 	var iDefValue = 0;
			if (document.getElementById("requestedLoanAmount").value != '') {
				iDefValue = parseFloat(removeComma(document.getElementById("requestedLoanAmount").value));
			}
			
			var iMinValue = 0;
		
			if (document.getElementById("minFinance").value != '') {
				iMinValue = parseFloat(removeComma(document.getElementById("minFinance").value));
			}
		
			var iMaxValue = 0;
		
			if (document.getElementById("maxFinance").value != '') {
				iMaxValue = parseFloat(removeComma(document.getElementById("maxFinance").value));
			}
					
			if (iDefValue == '0') {
				alert("Please Insert Requested Loan Amount between " + iMinValue+ " and " + iMaxValue);
				document.getElementById("requestedLoanAmount").value = '';
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			} 
			if (((iDefValue < iMinValue) || (iDefValue > iMaxValue)) && (iDefValue != '' && iMinValue != '' && iMaxValue != '')) {
				alert("Please Insert Requested Loan Amount between " + iMinValue + " and " + iMaxValue);
				document.getElementById("requestedLoanAmount").value = '';
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	}
		
		var product = document.getElementById("product");
		var scheme = document.getElementById("scheme");
		var requestedLoanAmount = document.getElementById("requestedLoanAmount");
		var requestedLoanTenure = document.getElementById("requestedLoanTenure");
		//var loanPurpose = document.getElementById("loanPurpose");
		var marginAmt = 0.00;
		var loanAmt =0.00;
		var assetCost =0.00;
		var repaymentType = document.getElementById("repaymentType").value; 
      	var repayEffectiveDate = document.getElementById("repayEffectiveDate").value;
  		var nextDueDate = document.getElementById("nextDueDate").value;
  		var formatD = document.getElementById("formatD").value;
  		var repayDate = getDateObject(repayEffectiveDate, formatD.substring(2, 3));
  		var nextDate = getDateObject(nextDueDate, formatD.substring(2, 3));
  		var day1= repayEffectiveDate.substring(0,2);
		var day2= nextDueDate.substring(0,2);
		var month1=repayEffectiveDate.substring(3,5);
		var month2=nextDueDate.substring(3,5);
		var year1=repayEffectiveDate.substring(6);
		var year2=nextDueDate.substring(6);
	    var msg1 = '', msg2 = '', msg3 = '', msg4 = '', msg5 = '', msg6 = '', msg7 = '', msg8 = '', msg9 = '', msg10 = '', msg11 = '', msg12 = '', msg13 = '', msg14 = '', msg15 = '', msg16 = '', msg17 = '';

	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("assetCost").value) == '')) {
		msg1 = "* Asset Cost is Required\n";

	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginPerc").value) == '')) {
		msg2 = "* Margin Percentage is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginAmount").value) == '')) {
		msg3 = "* Margin Amount is Required\n";
	}
	if ( ((document.getElementById("repaymentType").value) == '')) { 
		msg4 = "* Repayment Type is Required \n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("installmentType").value) == '')) {
		msg5 = "* Installment Type is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("frequency").value) == '')) {
		msg6 = "* Frequency is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("noOfInstall").value) == '')) {
		msg7 = "* No of Installments is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("cycleDate").value) == '')) {
		msg8 = "* Due Day is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I')) {
		if (document.getElementById("nextDueDate").value == '')
			msg9 = "* Next Due Date is Required\n";
	}

	if ((repaymentType != '' && repaymentType == 'I')&& ((document.getElementById("instMode").value) == '')) {
		if (document.getElementById("instMode").value == '')
			msg10 = "* Installment Mode is Required\n";
	}
	if (document.getElementById("repayEffectiveDate").value == '' && repaymentType=='I'){
			msg11 = "* Repay Effective Date is Required\n";
	}
	var contextPath = document.getElementById('contextPath').value;
	if (product.value == "" || scheme.value == "" || requestedLoanAmount.value == "" || requestedLoanTenure.value == "" || rateType.value == "" ) {
		var manFields = "product / scheme / requestedLoanAmount / requestedLoanTenure / rateType  ";
		validateForm(formName, manFields);
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

		if (msg1 != '' || msg2 != '' || msg3 != '' || msg4 != '' || msg5 != '' || msg6 != '' || msg7 != '' || msg8 != '' || msg9 != ''
				|| msg10 != '' || msg11!='') 
		{
				alert(msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg10 + msg11);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				
		} 
		else
		{
			if(document.getElementById("assetCost") != null){
				if ((productTypeFlag != '' && productTypeFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
	
					marginAmt = removeComma(document.getElementById("marginAmount").value);
					loanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
					assetCost = removeComma(document.getElementById("assetCost").value);
	
					if ((marginAmt + loanAmt) > assetCost) {
						alert("Sum of Margin Amount and Loan Amount is greater than Asset Cost");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
	
				}
			}
				
		if(!rangeTenure())
		{
						
				document.getElementById("Save").removeAttribute("disabled","true");
			    DisButClass.prototype.EnbButMethod();
		    	return false;
		}
		if(effRate<'0.0000000'||effRate<'0.00'||effRate<'0')
		{
			    alert("Final Rate Can not be less than 0.00 ");		
				document.getElementById("Save").removeAttribute("disabled","true");
			    DisButClass.prototype.EnbButMethod();
		    	return false;
		}
		
			if(repaymentType=='I')
			{
				if(parseFloat(year1)>parseFloat(year2))
				{
					alert("Next Due Date must be greater than Repay Effective Date.");
					document.getElementById("Save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				else
				{
					if(parseFloat(year1)==parseFloat(year2))
					if(parseFloat(month1)>parseFloat(month2))
					{
						alert("Next Due Date must be greater than Repay Effective Date.");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					else
					{
						if(parseFloat(month1)==parseFloat(month2))
						if(parseFloat(day1)>=parseFloat(day2))
						{
							alert("Next Due Date must be greater than Repay Effective Date.");
							document.getElementById("Save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}
				}
				if(rateType.value=='F')
				{
					var noOfInstall=parseInt(document.getElementById("noOfInstall").value);
					if(noOfInstall==0)
					{
						alert("No of Installment can’t Zero(0).");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					var tenure=parseInt(removeComma(requestedLoanTenure.value));
					var frequency=document.getElementById("frequency").value;
					var fre=1;
					if(frequency=="M")
						fre=1;
					if(frequency=="B")
						fre=2;
					if(frequency=="Q")
						fre=3;
					if(frequency=="H")
						fre=6;
					if(frequency=="Y")
						fre=12;
					
					var noOfInstallDF=parseInt(tenure/fre);
					if(noOfInstallDF>noOfInstall)
					{					
						if(confirm("Entered No of Installment is less than from Actual No of Installment ("+noOfInstallDF+")  as per selected frequency, Do you want to continue?")) 
						{
							document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessActionForChangeInst.do?method=saveLoanForEmiCalc";
							document.getElementById("processingImage").style.display = '';
							document.getElementById("LoanDetailsForm").submit();
							return true;
						}
						else
						{
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}
					else if(noOfInstallDF<noOfInstall)
					{
						alert("Entered No of Installment can’t be greater than Actual no of Installment ("+noOfInstallDF+") as per selected frequency");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
			}
			document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessActionForEmiCalc.do?method=saveLoanForEmiCalc";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("LoanDetailsForm").submit();
			return true;
		}

}


function updateLoanDetailForEmiCalc(formName){
	DisButClass.prototype.DisButMethod();
	var effRate=0.00;
	var productTypeFlag = document.getElementById("productTypeFlag").value; 
	  
	if ((productTypeFlag != '' && productTypeFlag == 'A') || productTypeFlag == 'N' || productTypeFlag == 'Y') {
			
		 	var iDefValue = 0;
			if (document.getElementById("requestedLoanAmount").value != '') {
				iDefValue = parseFloat(removeComma(document.getElementById("requestedLoanAmount").value));
			}
			
			var iMinValue = 0;
		
			if (document.getElementById("minFinance").value != '') {
				iMinValue = parseFloat(removeComma(document.getElementById("minFinance").value));
			}
		
			var iMaxValue = 0;
		
			if (document.getElementById("maxFinance").value != '') {
				iMaxValue = parseFloat(removeComma(document.getElementById("maxFinance").value));
			}
					
			if (iDefValue == '0') {
				alert("Please Insert Requested Loan Amount between " + iMinValue+ " and " + iMaxValue);
				document.getElementById("requestedLoanAmount").value = '';
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			} 
			if (((iDefValue < iMinValue) || (iDefValue > iMaxValue)) && (iDefValue != '' && iMinValue != '' && iMaxValue != '')) {
				alert("Please Insert Requested Loan Amount between " + iMinValue + " and " + iMaxValue);
				document.getElementById("requestedLoanAmount").value = '';
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	}
		
		var product = document.getElementById("product");
		var scheme = document.getElementById("scheme");
		var requestedLoanAmount = document.getElementById("requestedLoanAmount");
		var requestedLoanTenure = document.getElementById("requestedLoanTenure");
		//var loanPurpose = document.getElementById("loanPurpose");
		var marginAmt = 0.00;
		var loanAmt =0.00;
		var assetCost =0.00;
		var repaymentType = document.getElementById("repaymentType").value; 
      	var repayEffectiveDate = document.getElementById("repayEffectiveDate").value;
  		var nextDueDate = document.getElementById("nextDueDate").value;
  		var formatD = document.getElementById("formatD").value;
  		var repayDate = getDateObject(repayEffectiveDate, formatD.substring(2, 3));
  		var nextDate = getDateObject(nextDueDate, formatD.substring(2, 3));
  		var day1= repayEffectiveDate.substring(0,2);
		var day2= nextDueDate.substring(0,2);
		var month1=repayEffectiveDate.substring(3,5);
		var month2=nextDueDate.substring(3,5);
		var year1=repayEffectiveDate.substring(6);
		var year2=nextDueDate.substring(6);
	    var msg1 = '', msg2 = '', msg3 = '', msg4 = '', msg5 = '', msg6 = '', msg7 = '', msg8 = '', msg9 = '', msg10 = '', msg11 = '', msg12 = '', msg13 = '', msg14 = '', msg15 = '', msg16 = '', msg17 = '';

	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("assetCost").value) == '')) {
		msg1 = "* Asset Cost is Required\n";

	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginPerc").value) == '')) {
		msg2 = "* Margin Percentage is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginAmount").value) == '')) {
		msg3 = "* Margin Amount is Required\n";
	}
	if ( ((document.getElementById("repaymentType").value) == '')) { 
		msg4 = "* Repayment Type is Required \n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("installmentType").value) == '')) {
		msg5 = "* Installment Type is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("frequency").value) == '')) {
		msg6 = "* Frequency is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("noOfInstall").value) == '')) {
		msg7 = "* No of Installments is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("cycleDate").value) == '')) {
		msg8 = "* Due Day is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I')) {
		if (document.getElementById("nextDueDate").value == '')
			msg9 = "* Next Due Date is Required\n";
	}

	if ((repaymentType != '' && repaymentType == 'I')&& ((document.getElementById("instMode").value) == '')) {
		if (document.getElementById("instMode").value == '')
			msg10 = "* Installment Mode is Required\n";
	}
	if (document.getElementById("repayEffectiveDate").value == '' && repaymentType=='I'){
			msg11 = "* Repay Effective Date is Required\n";
	}
	var contextPath = document.getElementById('contextPath').value;
	if (product.value == "" || scheme.value == "" || requestedLoanAmount.value == "" || requestedLoanTenure.value == "" || rateType.value == "" ) {
		var manFields = "product / scheme / requestedLoanAmount / requestedLoanTenure / rateType  ";
		validateForm(formName, manFields);
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

		if (msg1 != '' || msg2 != '' || msg3 != '' || msg4 != '' || msg5 != '' || msg6 != '' || msg7 != '' || msg8 != '' || msg9 != ''
				|| msg10 != '' || msg11!='') 
		{
				alert(msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg10 + msg11);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				
		} 
		else
		{
			if(document.getElementById("assetCost") != null){
				if ((productTypeFlag != '' && productTypeFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
	
					marginAmt = removeComma(document.getElementById("marginAmount").value);
					loanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
					assetCost = removeComma(document.getElementById("assetCost").value);
	
					if ((marginAmt + loanAmt) > assetCost) {
						alert("Sum of Margin Amount and Loan Amount is greater than Asset Cost");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
	
				}
			}
				
		if(!rangeTenure())
		{
						
				document.getElementById("Save").removeAttribute("disabled","true");
			    DisButClass.prototype.EnbButMethod();
		    	return false;
		}
		if(effRate<'0.0000000'||effRate<'0.00'||effRate<'0')
		{
			    alert("Final Rate Can not be less than 0.00 ");		
				document.getElementById("Save").removeAttribute("disabled","true");
			    DisButClass.prototype.EnbButMethod();
		    	return false;
		}
		
			if(repaymentType=='I')
			{
				if(parseFloat(year1)>parseFloat(year2))
				{
					alert("Next Due Date must be greater than Repay Effective Date.");
					document.getElementById("Save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				else
				{
					if(parseFloat(year1)==parseFloat(year2))
					if(parseFloat(month1)>parseFloat(month2))
					{
						alert("Next Due Date must be greater than Repay Effective Date.");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					else
					{
						if(parseFloat(month1)==parseFloat(month2))
						if(parseFloat(day1)>=parseFloat(day2))
						{
							alert("Next Due Date must be greater than Repay Effective Date.");
							document.getElementById("Save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}
				}
				if(rateType.value=='F')
				{
					var noOfInstall=parseInt(document.getElementById("noOfInstall").value);
					if(noOfInstall==0)
					{
						alert("No of Installment can’t Zero(0).");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					var tenure=parseInt(removeComma(requestedLoanTenure.value));
					var frequency=document.getElementById("frequency").value;
					var fre=1;
					if(frequency=="M")
						fre=1;
					if(frequency=="B")
						fre=2;
					if(frequency=="Q")
						fre=3;
					if(frequency=="H")
						fre=6;
					if(frequency=="Y")
						fre=12;
					
					var noOfInstallDF=parseInt(tenure/fre);
					if(noOfInstallDF>noOfInstall)
					{					
						if(confirm("Entered No of Installment is less than from Actual No of Installment ("+noOfInstallDF+")  as per selected frequency, Do you want to continue?")) 
						{
							document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessActionForChangeInst.do?method=saveLoanForEmiCalc";
							document.getElementById("processingImage").style.display = '';
							document.getElementById("LoanDetailsForm").submit();
							return true;
						}
						else
						{
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}
					else if(noOfInstallDF<noOfInstall)
					{
						alert("Entered No of Installment can’t be greater than Actual no of Installment ("+noOfInstallDF+") as per selected frequency");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
			}
			document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessActionForEmiCalc.do?method=updateLoanForEmiCalc";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("LoanDetailsForm").submit();
			return true;
		}

}


function saveChargesForEmiCalc(){
	DisButClass.prototype.DisButMethod();
	var chMethod = document.getElementsByName('minChargeMethod');
	for(var i=0;i<chMethod.length;i++)
	{
		var chargeAmount="chargeAmount"+i;
		var loanAmount="loanAmount"+i;
		var marginAmount="marginAmount"+i;
		var calCulatedOn="calCulatedOn"+i;
		var chargeMethod="chargeMethod"+i;
		var finalAmount="finalAmount"+i;
		var minChargeCalculatedOn="minChargeCalculatedOn"+i;
		var minChargeMethod="minChargeMethod"+i;
		var taxsInclusive="taxsInclusive"+i;
		var taxtRat1="taxtRat1"+i;
		var taxtRat2="taxtRat2"+i;
		var dealChargeTaxApp="dealChargeTaxApp"+i;
		var dealChargeTdsApp="dealChargeTdsApp"+i;
		var dealChargeTaxAmount1="dealChargeTaxAmount1"+i;
		var dealChargeTaxAmount2="dealChargeTaxAmount2"+i;
		var dealChargeMinChargeAmount="dealChargeMinChargeAmount"+i;
		var dealChargeTdsRate="dealChargeTdsRate"+i;
		var dealChargeTdsAmount="dealChargeTdsAmount"+i;
		var dealChargeNetAmount="dealChargeNetAmount"+i;
		var chargeDesc="chargeDesc"+i;
		var description=document.getElementById(chargeDesc).value;
		
		
		//alert("description :"+description);
		if(document.getElementById(chargeAmount).value=='')
		{
			alert(description+"'s minimum charge amount can't be empty .");
			document.getElementById(finalAmount).value='';
			document.getElementById(chargeAmount).value='';
			document.getElementById("saveButton").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var chargeAmount1=0;
		var loanAmount1=0;
		var marginAmount1=0;
		var minChargeCalculatedOn1=0;
		var calCulatedOn1 =0;
		var calFinalAmount=0;
		var finalAmount1=0;
		var dealChargeMinChargeAmount1=0;
		var finalAmountT=0;
		
		if(document.getElementById(chargeAmount).value!='')
			chargeAmount1 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
		if(document.getElementById(loanAmount).value!='')
			loanAmount1 = parseFloat(removeComma(document.getElementById(loanAmount).value));
		if(document.getElementById(minChargeCalculatedOn).value!='')
			minChargeCalculatedOn1 =  parseFloat(removeComma(document.getElementById(minChargeCalculatedOn).value));
		if(document.getElementById(marginAmount).value!='')
			marginAmount1 =  parseFloat(removeComma(document.getElementById(marginAmount).value));
		if(document.getElementById(calCulatedOn).value!='')
			calCulatedOn1 =  parseFloat(removeComma(document.getElementById(calCulatedOn).value));
		if(document.getElementById(dealChargeMinChargeAmount).value!='')
			dealChargeMinChargeAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeMinChargeAmount).value));
		
		var chargeMethod1 = document.getElementById(chargeMethod).value;
		var minChargeMethod1=document.getElementById(minChargeMethod).value;

		if(chargeMethod1=='PERCENTAGE')
		{		
			if(minChargeCalculatedOn1>chargeAmount1)
			{
				alert(description+"'s Minimum Charge Percentage can't be less than "+minChargeCalculatedOn1);
				document.getElementById(chargeAmount).value='';
				document.getElementById("saveButton").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		 
		}
		else
		{
			if(minChargeCalculatedOn1>chargeAmount1)
			{
				alert(description+"'s minimum charge amount can't be less than "+minChargeCalculatedOn1);
				document.getElementById(finalAmount).value='';
				document.getElementById(chargeAmount).value='';
				document.getElementById("saveButton").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}		
		}
	}

	var chargeC101=document.getElementsByName('chargeCode');
	var chargeC102=document.getElementsByName('chargeCode');
	var finalCharge101=0.00;
	var finalCharge102=0.00;
	var marginAmount101=0.00;
	var marginAmount102=0.00;
	var flag101=false;
	var flag102=false;
	for(var i=0;i<chargeC101.length;i++)
	{
	   
		if(document.getElementById("chargeCode"+i).value=='101')
		{
			flag101=true;
			if(document.getElementById("finalAmount"+i).value!='')
				finalCharge101 =  parseFloat(removeComma(document.getElementById("finalAmount"+i).value));
			if(document.getElementById("marginAmount"+i).value!='')
				marginAmount101 =  parseFloat(removeComma(document.getElementById("marginAmount"+i).value));
			break;
		}
	}
	for(var i=0;i<chargeC102.length;i++)
	{
		if(document.getElementById("chargeCode"+i).value=='102')
		{
			flag102=true;
			if(document.getElementById("finalAmount"+i).value!='')
				finalCharge102 =  parseFloat(removeComma(document.getElementById("finalAmount"+i).value));
			if(document.getElementById("marginAmount"+i).value!='')
				marginAmount102 =  parseFloat(removeComma(document.getElementById("marginAmount"+i).value));
			break;
		}
	}
	
	if(flag101==true && flag102==true)
    {
		if(finalCharge101!=finalCharge102)
		{
			alert("Margin Money Received from customer is not equal to Margin Money Payable to Dealer");	
			document.getElementById("saveButton").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}		
	}
	else if(flag101==true || flag102==true)
	{
		alert("Margin Money Received from customer and Margin Money Payable to Dealer are required.");	
		document.getElementById("saveButton").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var contextPath =document.getElementById("contextPath").value;
	document.getElementById("ChrageForm").action=contextPath+"/chargesProcessActionForEmiCalc.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ChrageForm").submit();
}
	
	function refreshChargesForEmiCalc()
	{
		document.getElementById("refreshButton").removeAttribute("disabled","true");
		var contextPath =document.getElementById("contextPath").value;
		document.getElementById("ChrageForm").action=contextPath+"/chargeBehindAction.do?method=refreshChargeForEmiCalc";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("ChrageForm").submit();
	}
	
	
 function onSaveInstalForEmiCalc(){
	DisButClass.prototype.DisButMethod();
	
	var contextPath=document.getElementById("contextPath").value;
	var recoveryType=document.getElementById("recoveryType").value;
	var loanAmount=document.getElementById("loanAmount").value;
	// var fromInstallment=document.getElementById("fromInstallment").value;
	// var toInstallment=document.getElementById("toInstallment").value;
	var installmentType=document.getElementById("installmentType").value;
	var totalInstallment=document.getElementById("totalInstallment").value;
	var gridTable = document.getElementById('gridTable');
	var tableLength = gridTable.rows.length-1;
	
	  var sum=0;
	  var psum=0;
	  var isum=0;
	  
	for(var i=1;i<=tableLength;i++){
		
		
		var fromInstallment=document.getElementById("fromInstallment"+i).value;
	
		var toInstallment=document.getElementById("toInstallment"+i).value;
	    
		var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
		
		var principalAmount=document.getElementById("principalAmount"+i).value;
		
		var installmentAmount=document.getElementById("installmentAmount"+i).value;
	
		 if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount==""))
		 {
			 alert("Please fill all the fields ");
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	    if(fromInstallment==""){
	    	fromInstallment=0;
		      }		    	  
	    fromInstallment=removeComma(fromInstallment);
	    if(toInstallment==""){
	    	toInstallment=0;
		      }		    	  
	    toInstallment=removeComma(toInstallment);
	    
	    if(recoveryPercen==""){
	    	 alert("in id:---"+recoveryPercen);
	    	recoveryPercen=0;
		      }
		    	  
	    recoveryPercen=removeComma(recoveryPercen);

	    sum= sum + recoveryPercen;	
	    
	    if(principalAmount==""){
	    	principalAmount=0;
		      }
		    	  
	    principalAmount=removeComma(principalAmount);
	    
	    psum= psum + principalAmount * (toInstallment - fromInstallment + 1);	
	    
	    if(installmentAmount==""){
	    	installmentAmount=0;
		      }
		    	  
	    installmentAmount=removeComma(installmentAmount);
	    
	    isum= isum + installmentAmount* (toInstallment - fromInstallment + 1);
	    
	   

	    if(parseInt(document.getElementById("fromInstallment"+i).value) > parseInt(document.getElementById("toInstallment"+i).value))
		{	    	
			alert("From Installment should be less than or equal to Installment");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	 
		
		if(i<tableLength){	
			var k=i+1;
			
	if(parseInt(document.getElementById("toInstallment"+i).value) > parseInt(document.getElementById("fromInstallment"+k).value))
		{
			
			alert("next from Installment should be grtr than previous to Installment");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
	{
		
		alert("next from Installment should be equal to previous to Installment");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

		}
	
	}
	 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
	var lastToInstall=document.getElementById("toInstallment"+tableLength).value;	
	if(parseInt(lastToInstall)> parseInt(totalInstallment)){
		alert("no of  Installment should be equal to  Total Installment");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(parseInt(lastToInstall)< parseInt(totalInstallment)){
		alert("no of  Installment should be equal to  Total Installment");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(sum!=100 && recoveryType=='P')
	{
		alert("total recovery Percentage should be  equal to 100 %");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((psum>removeComma(loanAmount) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'))
	{
		alert("Principal Amount should be  equal to loan amount"+loanAmount);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if((isum<removeComma(loanAmount) && recoveryType=='F'))
	{
		alert("Sum of Installment Amount should be  equal to or greater loan amount "+loanAmount);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanProcess.do?method=saveInstallmentPlanForEmiCal&installmentType="+installmentType;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("installmentPlanForm").submit();
     return true;
}
 
 
 function saveSecurityDepositForEmiCalc()
 {
 	DisButClass.prototype.DisButMethod();
 	//alert("in saveSecurityDepositDetails"+calculateInterest());
 	var tenure= 0;
 	var internalTenure= 0;
 	if(document.getElementById("internalTenure").value!='')
 	{
 		internalTenure=removeComma(document.getElementById("internalTenure").value);
 	}
 	if(document.getElementById("tenure").value!='')
 	{
 		tenure=removeComma(document.getElementById("tenure").value);
 	}
 	var returnValue=calculateInterest();
 	var contextPath =document.getElementById("contextPath").value;
  	var relatedInterest=0;
  	if(document.getElementById("relatedInterest").value!='')
  	{
  		relatedInterest=removeComma(document.getElementById("relatedInterest").value);
  	}
	     if(checkValidateSecDep())
	     {
	       
	    	if(relatedInterest==returnValue)
	    	{
	    		if(document.getElementById("interestType3").checked==true && document.getElementById("compoundFrequency").value=='' )
	    		{
	    			alert("Please Select Compound Frequency");
	    			DisButClass.prototype.EnbButMethod();
	    			return false;
	    		}
	    		else
	    		{
	    			if(tenure<=internalTenure && tenure>0)
	    			{
		    			document.getElementById("securityForm").action=contextPath+"/securityDepositForEmiCalc.do";
		    			document.getElementById("processingImage").style.display = '';
				 	    document.getElementById("securityForm").submit();
				 	    return true;
	    			}
	    			else
	    			{
	    				alert("Tenure must be less than "+internalTenure+" and greater than 0");
	    				result=0;
	    				document.getElementById("tenure").value='';
	    			}
	    		}
	    		
	    	}
	    	else
	    	{
	    		alert("Please Refresh the Interest Payment Type");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    	}
	     
	     }
	     else
	     {
	    	 DisButClass.prototype.EnbButMethod();
	    	 return false;
	     }
 
 }
 
 
 
 function getFinalRateForEmiCalc()
 {
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var requestedLoanTenure=document.getElementById("requestedLoanTenure").value;
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			document.getElementById("noOfInstall").setAttribute("readOnly",true);
			if(requestedLoanTenure!=""){
				document.getElementById("noOfInstall").value=requestedLoanTenure;
			}
			return true;
		}
		else
		{
			if(repaymentType=='N')
			{
				document.getElementById("rateType").value='E';
				alert("You can't Change Rate Type ");
				return false;
			}
			else
			{
				//document.getElementById("effectiveRate").value='';
				document.getElementById("noOfInstall").value='';
				document.getElementById("noOfInstall").removeAttribute("readOnly",true);
				return false;
			}
		}
		
	}

} 
 
 
function addRowOtherChargePlanForEmiCalc(){
   				var table = document.getElementById("gridTable");
   				var rowCount = table.rows.length;
   				//alert("row count is --"+rowCount);
   				var psize= document.getElementById("psize").value;
   				//alert("psize111 --"+psize);
   				if(psize==""){
   					psize=rowCount;
   				}
   				//alert("psize222 --"+psize);
   				var row = table.insertRow(rowCount);
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+psize;
   				cell1.appendChild(element1);

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "fromInstall";
   				element2.id = "fromInstallment"+psize;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				element2.onkeypress= function(){
   					isNumberKey(document.getElementById("fromInstall"+psize).value,"fromInstall"+psize);
   				};
   				cell2.appendChild(element2);
   				
   				element2.onkeypress = function numbersonly(e){
						var dynaVal = this.id;
						document.getElementById(dynaVal).maxLength = 21;
						  var goods="0123456789.";
							    var key, keychar;
							    if (window.event)
							        key=window.event.keyCode;
							    else if (e)
							        key=e.which;
							    else
							        return true;
							    keychar = String.fromCharCode(key);
							    keychar = keychar.toLowerCase();
							    goods = goods.toLowerCase();
							    if (goods.indexOf(keychar) != -1)
							    {
							        goods="0123456789.";
							        return true;
							    }
							    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
							    {
							        goods="0123456789.";
							        return true;
							    }
							    return false;
						};
   				
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "toInstall";
   				element3.id = "toInstallment"+psize;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				element3.onkeypress= function(){
   					isNumberKey(document.getElementById("toInstall"+psize).value,"toInstall"+psize);
   				};
   				cell3.appendChild(element3);
   				element3.onkeypress = function numbersonly(e){
						var dynaVal = this.id;
						document.getElementById(dynaVal).maxLength = 21;
						  var goods="0123456789.";
							    var key, keychar;
							    if (window.event)
							        key=window.event.keyCode;
							    else if (e)
							        key=e.which;
							    else
							        return true;
							    keychar = String.fromCharCode(key);
							    keychar = keychar.toLowerCase();
							    goods = goods.toLowerCase();
							    if (goods.indexOf(keychar) != -1)
							    {
							        goods="0123456789.";
							        return true;
							    }
							    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
							    {
							        goods="0123456789.";
							        return true;
							    }
							    return false;
						};
   				
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "type";
   				element4.value = "FLAT";
   				element4.id = "type"+psize;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				element4.setAttribute('readonly','true' );
   				cell4.appendChild(element4);
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "amount";
   				element5.value = "0";
   				element5.id = "amount"+psize;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				//element5.setAttribute('readonly','true' );
   				element5.onblur= function(){
   					formatNumber(document.getElementById("amount"+psize).value,"amount"+psize);
   				};
   				element5.onfocus=function keyUpNumber(e){
   					   var val=this.value;
   					         if(val.indexOf(',') > 0){
   							var dynaVal = this.id;
   							var Max=18;
   							document.getElementById(dynaVal).maxLength = Max+3;
   							var origString = this.value ;
   							var inChar = ',';
   							var outChar = '.';
   							var newString = origString.split(outChar);
   							var newString = origString.split(inChar);
   							newString = newString.join('');
   							document.getElementById(dynaVal).value = '';
   							document.getElementById(dynaVal).value = newString;
   							}
   							};
   						element5.onkeypress = function numbersonly(e){
   						var dynaVal = this.id;
   						document.getElementById(dynaVal).maxLength = 21;
   						  var goods="0123456789.";
   							    var key, keychar;
   							    if (window.event)
   							        key=window.event.keyCode;
   							    else if (e)
   							        key=e.which;
   							    else
   							        return true;
   							    keychar = String.fromCharCode(key);
   							    keychar = keychar.toLowerCase();
   							    goods = goods.toLowerCase();
   							    if (goods.indexOf(keychar) != -1)
   							    {
   							        goods="0123456789.";
   							        return true;
   							    }
   							    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
   							    {
   							        goods="0123456789.";
   							        return true;
   							    }
   							    return false;
   						};
   				element5.onkeyup= function(){
   					checkNumber(document.getElementById("amount"+psize).value, this.event, 18,"amount"+psize);
   				};
   				cell5.appendChild(element5);
   					   			
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				var newdiv = document.createElement('span');
   				element6.type = "text";
   				element6.name = "chargeDesc";
   				element6.id = "chargeDesc"+psize;
   				
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				element6.setAttribute('readonly','true' );
   				cell6.appendChild(element6);
	   			var element8 = document.createElement("input");		
   				element8.type = "hidden";
   				element8.name = "chargehiddenFld";
   				element8.id = "chargehiddenFld"+psize;	
	   			newdiv.innerHTML ='<input type="button" value="" name="chargeButton" id="chargeButton" onclick="openLOVCommon(453,\'otherChargesPlanForm\',\'chargeDesc'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'chargehiddenFld'+psize+'\');closeLovCallonLov(\'chargehiddenFld'+psize+'\');" class="lovbutton"/>';
	   			cell6.appendChild(element8);
	   		    cell6.appendChild(newdiv);
	   		    
	   		 psize++;
	 		document.getElementById("psize").value=psize;
   			   		    
}


function removeOtherChargeRowForEmiCalc(){   
	//alert("removeOtherChargeRow");
	 var count=0;
   try
   {
	    var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	  	var psize=document.getElementById("psize").value;
		if(psize=="")
		{
			psize=rowCount;
		}
		document.getElementById("psize").value=psize;
		//alert("psize o del"+document.getElementById("psize").value);
	    for(var j=1;j<rowCount;j++)
	    {
	       var row1 = table.rows[j];
	       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
	     
	        if(chkbox1!=undefined ||chkbox1!=null) {
	    	 if(null != chkbox1 && true == chkbox1.checked) {
			 count=count+1;
	    	 }
			 }
	    }
	
		if(count==0)
		{
			alert("Please Select at least one row to delete");
		}
		else
		{
		    for(var i=1; i<rowCount; i++) {
		      var row = table.rows[i];
		      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
		        if(chkbox!=undefined ||chkbox!=null){
		        if(null != chkbox && true == chkbox.checked) {
		            table.deleteRow(i);
		            rowCount--;
		            i--;
		        }
		        }
		   //   }
		    }
		}
	}
   catch(e) 
   {
       alert(e);
   }
}



function saveOtherChargesForEmiCalc(){
 	DisButClass.prototype.DisButMethod();
 	
 	var contextPath=document.getElementById("contextPath").value;
 	var loanAmount=document.getElementById("loanAmount").value;
 	var installmentType=document.getElementById("installmentType").value;
 	var totalInstallment=document.getElementById("totalInstallment").value;
 	var gridTable = document.getElementById('gridTable');
 	var tableLength = gridTable.rows.length-1;
 	var rowCount = gridTable.rows.length;
 	var psize=document.getElementById("psize").value;
 	 if(psize=="")
 	 {
	 		psize=rowCount;
	 }
 	
 	 if(tableLength > 0)
 	 {
 	  var sum=0;
 	  var psum=0;
 	  var isum=0;
 	  var fromInstallment="";
 	  var toInstallment="";
 	  var type="";
 	  var amount="";
 	  var chargeDesc="";
 	  
 	  var fromInstall= document.getElementsByName("fromInstall");
 	  var toInstallment= document.getElementsByName("toInstall");
      var type= document.getElementsByName("type");
 	  var amount= document.getElementsByName("amount");
 	  var chargeDesc= document.getElementsByName("chargeDesc");
 	  for(var j=0;j<fromInstall.length;j++)
 	  {
 		if ((fromInstall[j].value=="")||(toInstallment[j].value=="")||(type[j].value=="" ) ||(amount[j].value=="")|| (chargeDesc[j].value==""))
		 {
			 alert("Please fill all the fields ");
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
 		
 		if(parseInt(fromInstall[j].value)>parseInt(toInstallment[j].value))
		{	    	
			alert("From Installment should be less than or equal to Installment");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
 		
 		if(parseInt(fromInstall[j].value)>=parseInt(toInstallment[j].value)){
 			alert("next from Installment should be greater than previous to Installment");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
 		}
 		if(j!=0)
 		{
	 		if(parseInt(fromInstall[j].value)<=parseInt(toInstallment[j-1].value)){
	 			alert("next from Installment should be greater than previous to Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
	 		}
 		}
 		
 		if(j==fromInstall.length)
 		{
	 		if(parseInt(toInstallment[j].value)> parseInt(totalInstallment)){
		 		alert("no of  Installment should be equal to Total Installment");
		 		document.getElementById("save").removeAttribute("disabled","true");
		 		DisButClass.prototype.EnbButMethod();
		 		return false;
		 	}
 		}
 	  }
 	 
 	
 	document.getElementById("otherChargesPlanForm").action = contextPath+"/saveOtherChargesPlanForEmiCalc.do?method=saveOtherChargesPlanForEmiCalc&installmentType="+installmentType;
 	document.getElementById("processingImage").style.display = '';
 	document.getElementById("otherChargesPlanForm").submit();
      return true;
 	 }
 	 else
 	 {
 		 alert("Please add one row at least");
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		 return false;
 	 }
 }
 
function validRepayDateForEmiCalc()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
    
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
 
    if(dt1<dt3)
	{
		msg="Please enter Repay Effective date greater than bussiness Date ";
		document.getElementById("repayEffectiveDate").value='';
	}

	if(msg!='')
	{
		alert(msg);
		return false;
	}
	else
	{
		return true;
	}
}


function calculateMaturityDateForEmiCalc()
{
	var repaymentType = document.getElementById('repaymentType').value;
	//alert(repaymentType);
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
		var formatD=document.getElementById("formatD").value;
		var currDate =   document.getElementById('repayEffectiveDate').value;
		var currDay   = currDate.substring(0,2);
		var currMonth = currDate.substring(3,5);
		var currYear  = currDate.substring(6,10);
		var seprator = formatD.substring(2, 3);
		  if(seprator=='-')
			  seprator = '/';
		  else
			  seprator = '/';
		    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
		   // alert(UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);
			//alert(CurrentDate);
			CurrentDate.setMonth(CurrentDate.getMonth()+x);
		   // alert(CurrentDate.getMonth());
		   modMonth=CurrentDate.getMonth()+1;
		   if(modMonth<=9)
		   {
			   modMonth="0"+modMonth;
		   }
		   modDay=CurrentDate.getDate();
		   if(modDay<=9)
		   {
			   modDay="0"+modDay;
		   }
		  // alert(modMonth);
		  ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
		  //alert(ModDateStr);
		  
		  document.getElementById('maturityDate1').value=ModDateStr;
	}
}


function checkNewRepayForEmiCalc() {
	// alert(document.getElementById("repayEffectiveDate").value);
	var str = document.getElementById("repayEffectiveDate").value
	var comp1 = str.split("-", 1);
	var comp2 = str.split("-", 2);
	var comp3 = str.split("-", 3);
	comp1 = parseInt(comp1, 10);
	comp1 = comp1 + 1;
	comp2 = parseInt(comp2, 10);
	comp3 = parseInt(comp3, 10);
	document.getElementById('checkNewRepay').value = 'comp1-comp2-comp3';
}

function getDueDayNextDueDateForEmiCalc()
{
	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	var contextPath =document.getElementById('contextPath').value ;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	if(repayEffectiveDate!='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayNextDueDateDetailForEmiCalc";
		var data = "repayEffectiveDate="+repayEffectiveDate;
		sendRequestGetDueDayNextDueDateForEmiCalc(address,data);
		return true;
	}
	else
	{
		alert("Repay Effective Date is required.");	
   	 	return false;
	}
}

function sendRequestGetDueDayNextDueDateForEmiCalc(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodGetDueDayNextDueDateForEmiCalc(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodGetDueDayNextDueDateForEmiCalc(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			var freq=document.getElementById('frequency').value;
			document.getElementById('cycleDate').value=trim(s1[0]);
			if(trim(s1[1])!='')
			{
				document.getElementById('nextDueDate').value=trim(s1[1]);
				calculateNextDueDateForEmiCalc(freq);
			}
	    }
		
	}
}


function getDueDayForEmiCalc()
{
	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	var cycleDate=document.getElementById('cycleDate').value;
	var contextPath =document.getElementById('contextPath').value ;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	if(repayEffectiveDate!='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayDetailForEmiCalc";
		var data = "repayEffectiveDate="+repayEffectiveDate+"&cycleDate="+cycleDate;
		sendRequestDueDayForEmiCalc(address,data);
		return true;
	}
	else
	{
		alert("Repay Effective Date is required.");	
   	 	return false;
	}
}
function sendRequestDueDayForEmiCalc(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodDueDayForEmiCalc(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodDueDayForEmiCalc(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			var freq=document.getElementById('frequency').value;
			if(trim(s1[1])!='')
			{
				document.getElementById('nextDueDate').value=trim(s1[1]);
				calculateNextDueDateForEmiCalc(freq);
			}
	    }
		
	}
}

function calculateNextDueDateForEmiCalc(frequency)
{
    //alert(frequency);
	var frequency= frequency;
	var formatD=document.getElementById("formatD").value;
	var freqMonth=0;
	if(frequency=='B')
	{
		freqMonth=2;
	}
	else if(frequency=='Q')
	{
		freqMonth=3;
	}
	else if(frequency=='H')
	{
		freqMonth=6;
	}
	else if(frequency=='Y')
	{
		freqMonth=12;
	}
	
	if(frequency!='')
	{
		parsedFreq=parseInt(freqMonth);
	}
	
	var repayEffectiveDate=document.getElementById("nextDueDate").value;
	var d1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
	d1.setMonth(d1.getMonth()+parsedFreq);
	var curr_date = d1.getDate();
	var curr_month = d1.getMonth();
	var curr_year = d1.getFullYear();
	if(curr_month==00)
	{
		curr_month=12;
		curr_year=curr_year-1;
	}
	document.getElementById("nextDueDate").value=padding(curr_date)+formatD.substring(2, 3)+padding(curr_month)+formatD.substring(2, 3)+curr_year;

}

function padding(number){

	return number < 10 ? "0"+number : number;

	}

function calcDay(){
	   var requestedLoanTenure= document.getElementById("requestedLoanTenure").value;
	   var daysBasis= document.getElementById("daysBasis").value;
	//   alert("daysBasis"+daysBasis);
	   
	   	if(requestedLoanTenure==''){
	   		requestedLoanTenure=0;
	   	}
	   	if(requestedLoanTenure!='')
	   	{
	   		requestedLoanTenure=parseInt(requestedLoanTenure);
	   		//alert("parseTenure:-----"+parseTenure);
	   	}
	   	if(daysBasis=='A'){
	   	//	alert("under a"+daysBasis);
	   		var day=requestedLoanTenure*30.42;
		   	document.getElementById("tenureInDay").value=day.toFixed(0); 
		   }
	   	else{
	   	var day=requestedLoanTenure*30;
	   	document.getElementById("tenureInDay").value=day;
	   	}
}
//function changeMonthInDay(){
//	   alert("2222daysBasis"+daysBasis);
//	   var tenureInDay= document.getElementById("tenureInDay").value;
//	   var daysBasis= document.getElementById("daysBasis").value;
//	   alert("daysBasis"+daysBasis);
//	   
//	   	if(tenureInDay==''){
//	   		tenureInDay=0;
//	   	}
//	   	if(tenureInDay!='')
//	   	{
//	   		tenureInDay=parseInt(tenureInDay);
//	   		//alert("parseTenure:-----"+parseTenure);
//	   	}
//	   	if(daysBasis=='A'){
//	   		var month=tenureInDay/30.42;
//		   	document.getElementById("requestedLoanTenure").value=month.toFixed(2);
//		   }
//	   	else{
//	   	var month=tenureInDay/30;
//	   	document.getElementById("requestedLoanTenure").value=month.toFixed(2);
//	   	}
//
//}

 
 