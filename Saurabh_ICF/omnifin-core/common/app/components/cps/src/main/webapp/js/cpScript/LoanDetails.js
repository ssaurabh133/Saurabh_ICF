function formatNumber(val, san)
{
	if(val != ''){

		var expAllVal = val.split(".");
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
		
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
		}
		
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			}else{
				finalValueForDisp = finalValueForDisp+'.00';
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
			return finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
					return newString;
			//alert(newString);
			}
	} 
}

function saveLoanDetail(formName) {
		
	DisButClass.prototype.DisButMethod();
	var rate = document.getElementById("rateType").value;
	var effectiveRate =document.getElementById("effectiveRate").value;
	var insType= document.getElementById("installmentType").value;
	var intCalMethod= document.getElementById("interestCalculationMethod").value;
	var grossBlock= document.getElementById("grossBlock").value;
	var netBlock= document.getElementById("netBlock").value;
	var intDueDate= "";
	
	var businessType = document.getElementById("businessType");
	
		if(businessType){
			businessType = businessType.value
			
			if(businessType==''){
				alert("Business Type Is Required!");
				//document.getElementById("requestedLoanAmount").value = '';
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
			}
		}
		
		
	 intDueDate= document.getElementById("interestDueDate").value;
	
	var effRate=0.00;
	if(effectiveRate!='')
	{
		effRate=removeComma(effectiveRate);
	}
	var minFlatRate =document.getElementById("minFlatRate").value;
	var maxFlatRate =document.getElementById("maxFlatRate").value;
	
	var minEffectiveRate =document.getElementById("minEffectiveRate").value;
	var maxEffectiveRate =document.getElementById("maxEffectiveRate").value;  
	var repaymentType = document.getElementById("repaymentType").value; 
	if(repaymentType != '' && repaymentType == 'I')
	{
		var creditPeriod =document.getElementById("creditPeriod").value;  //added by Richa
	}

	
    
	var minFlatR=0.00;
	if(minFlatRate!='')
	{
		minFlatR=removeComma(minFlatRate);
	}
	var maxFlatR=0.00;
	if(maxFlatRate!='')
	{
		maxFlatR=removeComma(maxFlatRate);
	}
	var minEffectiveR=0.00;
	if(minEffectiveRate!='')
	{
		minEffectiveR=removeComma(minEffectiveRate);
	}
	var maxEffectiveR=0.00;
	if(maxEffectiveRate!='')
	{
		maxEffectiveR=removeComma(maxEffectiveRate);
	}
	
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
			if(rate=='F')
			{
				if ((effRate < minFlatR) || (effRate > maxFlatR)) {
					alert("Please Insert Final Rate between " + minFlatR + " and " + maxFlatR);
					document.getElementById("effectiveRate").value = 0;
					var bseRate=0;
					if(document.getElementById("baseRate").value!='')
					{
						bseRate=parseFloat(document.getElementById("baseRate").value);
					}
					document.getElementById("markUp").value=parseFloat(document.getElementById("effectiveRate").value)-bseRate;
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			else
			{
				if ((effRate < minEffectiveR) || (effRate > maxEffectiveR)) {
					alert("Please Insert Final Rate between " + minEffectiveR + " and " + maxEffectiveR);
					document.getElementById("effectiveRate").value = 0;
					var bseRate=0;
					if(document.getElementById("baseRate").value!='')
					{
						bseRate=parseFloat(document.getElementById("baseRate").value);
					}
					
					document.getElementById("markUp").value=parseFloat(document.getElementById("effectiveRate").value)-bseRate;
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			
	}
		
		var product = document.getElementById("product");
		var scheme = document.getElementById("scheme");
		var requestedLoanAmount = document.getElementById("requestedLoanAmount");
		var requestedLoanTenure = document.getElementById("requestedLoanTenure");
		var tenureInDay = document.getElementById("tenureInDay").value;//added by richa
		var rateType = document.getElementById("rateType");
		var sectorType = document.getElementById("sectorType");
		var loanPurpose = document.getElementById("loanPurpose").value;
		//var loanPurposeValue = document.getElementById("loanPurposeValue").value;
	
	  var marginAmt = 0.00;
	  var loanAmt =0.00;
	  var assetCost =0.00;
	  var marginPrec = 0.00;
      //var productTypeFlag = document.getElementById("productTypeFlag").value; 
     //committed by amandeep
	  // var repaymentType = document.getElementById("repaymentType").value; 
      
      //amandeep ends
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
  		
  		var t1=document.getElementById("type1").checked;
  		var t2=document.getElementById("type2").checked;
  		 //add by vijendra singh
		var editDueDate= document.getElementById("editDueDate").value;		
		// end by vijendra singh
  		
  		//alert("repayDate: "+repayDate+" nextDate: "+nextDate);
	var msg1 = '', msg2 = '', msg3 = '', msg4 = '', msg5 = '', msg6 = '', msg7 = '', msg8 = '', msg9 = '', msg10 = '', msg11 = '', msg12 = '', msg13 = '', msg14 = '', msg15 = '', msg16 = '', msg17 = '', msg18 = '',msg19= '',msg20= '';

	if ((rate != '' && rate == 'E') && (t1 == false && t2==false)) {
		msg1 = "* Rate Method is Required \n";
	}
	if ((rate != '' && rate == 'E') && ((document.getElementById("baseRateType").value) == '')) {
		msg2 = "* Base Rate Type is Required\n";
	}
	if ((rate != '' && rate == 'E') && ((document.getElementById("baseRate").value) == '')) {
		msg3 = "* Base Rate is Required\n";
	}
	if (document.getElementById("effectiveRate").value == '') {
		msg4 = "* Final Rate is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("assetCost").value) == '')) {
		msg18 = "* Asset Cost is Required\n";

	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("noOfAsset").value) == '')) {
		msg5 = "* No Of Asset is Required\n";

	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginPerc").value) == '')) {
		msg6 = "* Margin Percentage is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && (productTypeFlag != '' && productTypeFlag != 'N') && ((document.getElementById("marginAmount").value) == '')) {
		msg7 = "* Margin Amount is Required\n";
	}
	if ( ((document.getElementById("repaymentType").value) == '')) { 
		msg8 = "* Repayment Type is Required \n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("installmentType").value) == '')) {
		msg9 = "* Installment Type is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("frequency").value) == '')) {
		msg10 = "* Frequency is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("noOfInstall").value) == '')) {
		msg11 = "* No of Installments is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("paymentMode").value) == '')) {
		msg12 = "* Re-Payment Mode is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I') && ((document.getElementById("cycleDate").value) == '')) {
		msg13 = "* Due Day is Required\n";
	}
	if ((repaymentType != '' && repaymentType == 'I')) {
		if (document.getElementById("nextDueDate").value == '')
			msg14 = "* Next Due Date is Required\n";
	}

	if ((repaymentType != '' && repaymentType == 'I')&& ((document.getElementById("instMode").value) == '')) {
		if (document.getElementById("instMode").value == '')
			msg16 = "* Installment Mode is Required\n";
	}
	if (document.getElementById("repayEffectiveDate").value == '' && repaymentType=='I'){
			msg17 = "* Repay Effective Date is Required\n";
	}
	if (document.getElementById("loanPurpose").value == '' && loanPurpose==''){
		msg19 = "* Loan Purpose is Required\n";
}	
//add by vijendra singh
if (editDueDate==' ') {
				msg20 += "* Edit Due Date is required \n";
			}
			// end by vijendra singh
	var contextPath = document.getElementById('contextPath').value;
	if (product.value == "" || scheme.value == "" || requestedLoanAmount.value == "" || requestedLoanTenure.value == "" || rateType.value == "" || sectorType.value == "" || loanPurpose.value == "") {
		var manFields = "product / scheme / requestedLoanAmount / requestedLoanTenure / rateType / sectorType / loanPurpose ";
		validateForm(formName, manFields);
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	//add by viendra singh             
	    if(editDueDate==' ')
		 {
           //alert(editDueDate);			
			 alert(msg20);
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
		 }		
		// end by vijendra singh
//	Surendra Code..
	if (repaymentType=='N'){
		
		if(document.getElementById("interestCalc").value==''){
		alert("* Interest Calculation From is Required\n");
		document.getElementById("interestCalc").focus();
		document.getElementById("Save").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		}
	
//		if(document.getElementById("LoanDetailsForm")){
		if (msg1 != '' || msg2 != '' || msg3 != '' || msg4 != '' || msg5 != '' || msg6 != '' || msg7 != '' || msg8 != '' || msg9 != ''
				|| msg10 != '' || msg11 != '' || msg12 != '' || msg13 != '' || msg14 != '' || msg15 != '' || msg16 != ''||msg17!=''||msg18!='' || msg19!='') 
		{
				alert(msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg16 + msg10 + msg11 + msg12 + msg13 + msg14 + msg15+msg17+msg18+msg19);
				document.getElementById("Save").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				
		} 
		else
		{
			var val = document.getElementById('showRepaymentType').value;
			if (val == 'INSTALLMENT') {
			var noOfInstall=document.getElementById("noOfInstall").value
			var installments=document.getElementById("installments").value;
			if(document.getElementById("installments").value==''){
				installments=0;
			}
			else{
				installments=parseInt(installments);
			}
			
			if(document.getElementById("instMode").value == 'A')
			{
				if(installments+1>noOfInstall){
				alert(" No of Installment should not be greater than total No. of Advance Installment ");
				document.getElementById("installments").value=0;
				document.getElementById("Save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			}
			if(document.getElementById("instMode").value == 'R')
			{
				if(installments>noOfInstall){
				alert(" No of Installment should not be greater than total No. of Advance Installment ");
				document.getElementById("installments").value=0;
				document.getElementById("Save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			}
			}
			if(document.getElementById("assetCost") != null){
				/*if(!getMarginAmount("marginPerc")){
					document.getElementById("Save").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}*/
				if ((productTypeFlag != '' && productTypeFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
	
					marginAmt = removeComma(document.getElementById("marginAmount").value);
					loanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
					assetCost = removeComma(document.getElementById("assetCost").value);
					marginPrec = removeComma(document.getElementById("marginPerc").value);
					var noOfAsset = document.getElementById("noOfAsset").value;
					
	//				if ((marginAmt > 0) && (marginPrec == 0)) {
	//					alert("Margin % can't be zero.");
	//					document.getElementById("Save").removeAttribute("disabled","true");
	//					DisButClass.prototype.EnbButMethod();
	//					return false;
	//				}
	
	//				if ((marginAmt + loanAmt) > assetCost) {
					if ((removeComma(document.getElementById("marginAmount").value) + removeComma(document.getElementById("requestedLoanAmount").value)) != assetCost){
						alert("Sum of Margin Amount and Loan Amount is greater than Asset Cost");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
	
										
					/*if (noOfAsset<1) {
						alert("Number of Assets should not be less than One.");
						document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}*/
	
				}
			}
		//commented by neeraj tripathi	
		/*if(repaymentType=='I')	
		{
			if(!isInt(calculateInstallment()))
			{
				
					alert("The Combination of tenure and frequency are incorrect");
					document.getElementById("noOfInstall").value='';
					document.getElementById("Save").removeAttribute("disabled","true");
				    DisButClass.prototype.EnbButMethod();
			    	return false;
			}
		}*/
		//richa changes starts
			if(parseFloat(creditPeriod)>parseFloat(tenureInDay)){
				alert(" Credit Period should be less than or equal to tenure in days ");
				document.getElementById("Save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			//Richa changes ends
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
			//neeraj's space start
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
				//var freqCalculateNextDueDate=document.getElementById('frequency').value;
				//calculateNextDueDate(freqCalculateNextDueDate);
				var diffDay=parseInt(daysBetweenDates(repayDate,nextDate));
				//alert("diffDay: "+diffDay);
				var diffDayCount= parseInt(document.getElementById("diffDayCount").value);
				//alert("diffDayCount: "+diffDayCount);
				if(diffDay<diffDayCount)
				{
					    alert("Difference between Repay Effective and Next Due Date should not be less than "+diffDayCount+" days");		
						document.getElementById("Save").removeAttribute("disabled","true");
					    DisButClass.prototype.EnbButMethod();
				    	return false;
				}
				
					//alert("repayDate: "+repayDate+" nextDate: "+nextDate);
				if(rateType.value=='F')
				{
					var noOfInstall=parseInt(document.getElementById("noOfInstall").value);
					if(noOfInstall==0)
					{
						alert("No of Installment can't Zero(0).");
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
					
					//alert("tenure : "+tenure);
					//alert("fre    : "+fre);
					var noOfInstallDF=parseInt(tenure/fre);
					//alert("noOfInstallDF  : "+noOfInstallDF);
					//alert("noOfInstall    : "+noOfInstall);
					if(noOfInstallDF>noOfInstall)
					{					
						if(confirm("Entered No of Installment is less than from Actual No of Installment ("+noOfInstallDF+")  as per selected frequency, Do you want to continue?")) 
						{
							document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessAction.do?method=saveLoan&interestDueDate="+intDueDate+"&grossBlock="+grossBlock+"&netBlock="+netBlock;
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
						alert("Entered No of Installment can't be greater than Actual no of Installment ("+noOfInstallDF+") as per selected frequency");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}				
			}
			
			//Ajay's space start
						var installmentType= document.getElementById("installmentType").value;
						if(installmentType=='S')
						{
							if(!datevalidate())
							{
								//alert("Please insert proper Interest Due Date ");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
			//Ajay's space end
						
						var interestdueDate= document.getElementById("interestDueDate").value;
					//	var interestStartDate = getDateObject(interestdueDate, formatD.substring(2, 3));
				  		
				  		var day3= interestdueDate.substring(0,2);
						
						var month3=interestdueDate.substring(3,5);
						
						var year3=interestdueDate.substring(6);
						
						
						
						
						
						// Code added for handling separate Interest Due Date
						if(installmentType=='S')
						{
							if(document.getElementById("interestDueDate").value == ''||  document.getElementById("interestDueDate").value ==null)
							{
								alert("Please insert proper Interest Start Date ");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							
							if(parseFloat(year1)==parseFloat(year3)){
								if(parseFloat(month1)>parseFloat(month3))
								{
									alert("Interest Start Date must be greater than Repay Effective Date.");
									document.getElementById("Save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								else
								{
									if(parseFloat(month1)==parseFloat(month3))
									if(parseFloat(day1)>parseFloat(day3))
									{
										alert("Interest Start Date must be greater than Repay Effective Date.");
										document.getElementById("Save").removeAttribute("disabled","true");
										DisButClass.prototype.EnbButMethod();
										return false;
									}
								}
							}
						
							
							if(document.getElementById("maturityDate1").value == "" || document.getElementById("maturityDate1").value ==null)
							{
								alert("Maturity Date cannot be empty");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}			
					
						
			//Ajay's space start
						var installmentType= document.getElementById("installmentType").value;
						if(installmentType=='S')
						{
							if(!validateInterestFrequency())
							{
								//alert("Please insert proper Interest Due Date ");
								DisButClass.prototype.EnbButMethod()
								return false;
							}
						}
			//Ajay's space end 
			//Ajay's space start
						var installmentType= document.getElementById("installmentType").value;
						if(installmentType=='E'|| installmentType=='G'  ||  installmentType=='P' ||installmentType=='Q' )
						{
							if(!validateIntCompoundingFrequency())
							{
								//alert("Please insert proper Interest Due Date ");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
						//Ajay's space start
						var installmentType= document.getElementById("installmentType").value;
						var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
						if(installmentType=='S' && interestCalculationMethod!="D")
						{
							if(!validateInterestDueDate())
							{
								//alert("Please insert proper Interest Due Date ");
								DisButClass.prototype.EnbButMethod()
								return false;
							}
						}
			//Ajay's space end 			
			//Ajay's space end 
			//neeraj's space end
			/*if(repayDate >= nextDate && repaymentType=='I')
			{
				alert("Next Due Date must be greater than Repay Effective Date.");
				//document.getElementById("nextDueDate").value='';
				//document.getElementById("repayEffectiveDate").value='';
				document.getElementById("Save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/
			
			document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessAction.do?method=saveLoan&interestDueDate="+intDueDate+"&grossBlock="+grossBlock+"&netBlock="+netBlock;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("LoanDetailsForm").submit();
			return true;
		}

}
function netLtvView() 
{
	var repaymentType = document.getElementById('repaymentType').value;
	var assetFlag = document.getElementById('assetFlag').value;
	if(repaymentType=='I' && assetFlag=='A')
		document.getElementById("netLtvRow").style.display='';
	else
		document.getElementById("netLtvRow").style.display='none';
}
function removeDueDate() {
	var val = document.getElementById('showRepaymentType').value;
	if (val == 'NON-INSTALLMENT') {
		document.getElementById("nddheader").style.display = 'none';
		document.getElementById("ndd").style.display = 'none';
		document.getElementById("effDateHeader").style.display = 'none';
		document.getElementById("effDate").style.display = 'none';
	} else {
		document.getElementById("nddheader").style.display = '';
		document.getElementById("ndd").style.display = '';
		document.getElementById("effDateHeader").style.display = '';
		document.getElementById("effDate").style.display = '';
	}

}

function checkDueDate(san) {
	var date = new Date();
	var formatD=document.getElementById("formatD").value;
	var dueDate = document.getElementById('cycleDate').value;
	var str = document.getElementById('nextDueDate').value;
	var nMonth = str.substring(3,5); //months from 1-12
	var nDay = str.substring(0,2);
	var nYear = str.substring(6);
	var comp = str.split("-", 1);
	comp = parseInt(comp, 10);
	
	if (dueDate == '') 
	{
		alert("First select Due Day");
		document.getElementById('nextDueDate').value = '';
	}else 
	{
		
	
		if(dueDate<=28)
		{
			if (comp != dueDate)
			{
			alert("Next Due Date must be equal to Due Day ie. " + dueDate);
			document.getElementById('nextDueDate').value = '';
			} else 
			{
			checkDate('nextDueDate');
		}
		}else if (parseFloat(nMonth)==2 )
		{
							
			if(((parseFloat(nYear) % 4 == 0) && ((parseFloat(nYear) % 100 != 0))) || ((parseFloat(nYear) % 400 == 0)))
			{
			
				if((dueDate==29 ||dueDate==30 || dueDate==31) &&  comp==29)
				{
				checkDate('nextDueDate');
				}
				else
				{
				alert("Next Due Date Day must be equal  29");
		document.getElementById('nextDueDate').value = '';
	}
			}else
			{
				if((dueDate==29 ||dueDate==30 || dueDate==31) &&  comp==28)
				{
				checkDate('nextDueDate');
				}
				else
				{
				alert("Next Due Date Day must be equal  28");
				document.getElementById('nextDueDate').value = '';
				}
			}
		}
		else if ( parseFloat(nMonth)==4 || parseFloat(nMonth)==6 || parseFloat(nMonth)==9 || parseFloat(nMonth)==11 )
		{
			if(dueDate==29 ||dueDate==30)
			{
				if (comp != dueDate)
				{
				 alert("Next Due Date must be equal to Due Day ie. " + dueDate);
				 document.getElementById('nextDueDate').value = '';
				}
			}
			else if( dueDate==31 &&  comp==30)
			{
				checkDate('nextDueDate');
			}
			else
			{
			alert("Next Due Date Day must be equal  30");
			document.getElementById('nextDueDate').value = '';
			}
		}
		else if (parseFloat(nMonth)==1 || parseFloat(nMonth)==3 || parseFloat(nMonth)==5 || parseFloat(nMonth)==7 || parseFloat(nMonth)==8 || parseFloat(nMonth)==10  || parseFloat(nMonth)==12)
		{
			if (comp != dueDate)
			{
			 alert("Next Due Date must be equal to Due Day ie. " + dueDate);
			 document.getElementById('nextDueDate').value = '';
			} else 
			{
				checkDate('nextDueDate');
			}
			
		}
	} 
		
	
}

function nullNextDue() {
	document.getElementById('nextDueDate').value = '';
	var interestCalculationMethod = document.getElementById('interestCalculationMethod').value;
	if(interestCalculationMethod=='D')
	{
	document.getElementById('interestDueDate').value = '';
	}


}

function checkNewRepay() {
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

function hideField()
{
	var repaymentType = document.getElementById("repaymentType").value; 
  	if(repaymentType=="N")
  	{
  		document.getElementById('nddheader').style.display='none';
  		document.getElementById('ndd').style.display='none';
  		document.getElementById('effDateHeader').style.display='none';
  		document.getElementById('effDate').style.display='none';
  	}
}



function calMarginAmount()
{
	
	//alert(iRate);
	
	var assetAmount=document.getElementById("assetCost").value;
	var reqLoanAmt=removeComma(document.getElementById("requestedLoanAmount").value);
	if(assetAmount!='' && document.getElementById("marginPerc").value!='')
	{
		//alert("In if "+assetAmount);
		var iLoanAmount=removeComma(assetAmount);
		var iRate=removeComma(document.getElementById("marginPerc").value);
		document.getElementById("marginAmount").value=roundNumber((iLoanAmount*iRate)/100, 2);
		if(document.getElementById("requestedLoanAmount").value=='')
		document.getElementById("requestedLoanAmount").value = (iLoanAmount - document.getElementById("marginAmount").value);
	}
	else
	{
	//	alert("In else "+assetAmount);
		document.getElementById("marginAmount").value='';
		document.getElementById("marginPerc").value='';
	}
	
}

function modifyDetail(dealLoanId)
{
	
	var contextPath =document.getElementById('contextPath').value ;
		
		document.getElementById("LoanDetailsForm").action=contextPath+"/loanBehindAction.do?method=fetchLoanDetail&dealLoanId="+dealLoanId;
	 	document.getElementById("LoanDetailsForm").submit();

	
}

function rangeAmtMar(){
	
	if(document.getElementById("assetCost").value == '' && document.getElementById("requestedLoanAmount").value == ''){
		document.getElementById("marginPerc").value = '';
		document.getElementById("marginAmount").value = '';
    	//document.getElementById("assetCost").focus();
		return false;
	}else{
		if(document.getElementById("requestedLoanAmount").value == ''){
			document.getElementById("marginPerc").value = '';
			document.getElementById("marginAmount").value = '';
	    	document.getElementById("requestedLoanAmount").focus();
	    	return false;
		}else if(document.getElementById("assetCost").value == ''){
			document.getElementById("marginPerc").value = '';
			document.getElementById("marginAmount").value = '';
	    	document.getElementById("assetCost").focus();
	    	return false;
		}else {
			var marginAm = removeComma(document.getElementById("assetCost").value) - removeComma(document.getElementById("requestedLoanAmount").value);
			var marginPercentage = marginAm * 100/removeComma(document.getElementById("assetCost").value);
			document.getElementById("marginPerc").value = marginPercentage.toFixed(2);
			document.getElementById("marginAmount").value = marginAm;
			return true;
		}
	}
}

function checkRate(val)

{
	

	var rate = document.getElementById(val).value;
	 
//alert("Passed Value: "+rate);
if(rate=='')
	{
		rate=0;
		//alert("Please Enter the value");
	//	return false;
		
	}
	    var intRate = parseFloat(rate);
	  //  alert(intRate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }

	        else
	           {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById(val).value="";
		//document.getElementById(val).focus;
		        return false;
	         }
	
}
//function added by neeraj
function checkNoOfAsset()
{	
	var noOfAsset=document.getElementById("noOfAsset").value;
	if(noOfAsset=='')
		document.getElementById("noOfAsset").value="1";		
}
function assetRangeAmount() {
	
	 if(document.getElementById("assetCost").value == ''){
		 if(document.getElementById("requestedLoanAmount").value == ''){
		 	document.getElementById("marginPerc").value = '';
			document.getElementById("marginAmount").value = '';
	    	document.getElementById("assetCost").focus();
	    	return false;
			 
		 }else{
	    	document.getElementById("marginPerc").value = '';
			document.getElementById("marginAmount").value = '';
	    	document.getElementById("assetCost").focus();
	    	return false;
		 }
		}else {
			if(document.getElementById("requestedLoanAmount").value == ''){
			 	document.getElementById("marginPerc").value = '';
				document.getElementById("marginAmount").value = '';
		    	//document.getElementById("requestedLoanAmount").focus();
		    	return false;
				 
			 }else{
				
				var marginAm = removeComma(document.getElementById("assetCost").value) - removeComma(document.getElementById("requestedLoanAmount").value);
				var marginPercentage = marginAm * (100/removeComma(document.getElementById("assetCost").value));
			
				document.getElementById("marginPerc").value = (marginPercentage).toFixed(2);
				document.getElementById("marginAmount").value = marginAm;
				return true;
			 }
		}
}

function calMarginPer(){
	if(document.getElementById("marginAmount").value == ''){
		return false;
	}
	if(removeComma(document.getElementById("assetCost").value) < removeComma(document.getElementById("marginAmount").value)){
		alert("Margin Amount can not be greater than Asset cost.");
		document.getElementById("marginAmount").value = '';
		document.getElementById("marginPerc").value = '';
		document.getElementById("marginAmount").focus();
		return false;
	}
	if(document.getElementById("assetCost").value == ''){
		alert("First insert Asset Cost.");
		document.getElementById("marginAmount").value = '';
		document.getElementById("assetCost").focus();
		return true;
	}else if(document.getElementById("assetCost").value != ''){
		var reqLoanAmt = removeComma(document.getElementById("assetCost").value) - removeComma(document.getElementById("marginAmount").value);
		var marginPercentage = removeComma(document.getElementById("marginAmount").value) * (100/removeComma(document.getElementById("assetCost").value));
		document.getElementById("marginPerc").value = (marginPercentage).toFixed(2);
		document.getElementById("requestedLoanAmount").value = reqLoanAmt;
		return true;
	}else{
		alert("First insert Asset Cost or Requested Loan Amount.");
		document.getElementById("assetCost").focus();
		document.getElementById("marginAmount").value = '';
		return true;
	}
}

function rangeTenure()
{
	var iDefValue=0;
	var iMinValue=0;
	var iMaxValue=0;
	if(document.getElementById("requestedLoanTenure").value!='') 
	{
		iDefValue=parseInt(document.getElementById("requestedLoanTenure").value);
	}
	if(document.getElementById("minTenure").value!='')
	{
		iMinValue=parseInt(document.getElementById("minTenure").value);
	}
	if(document.getElementById("maxTenure").value!='')
	{
		iMaxValue=parseInt(document.getElementById("maxTenure").value);
	}
	//alert("iDefValue: "+iDefValue+" iMinValue: "+iMinValue+" iMaxValue: "+iMaxValue);
	if((iDefValue<iMinValue)||(iDefValue>iMaxValue))
	{
		alert("Please Insert Tenure between "+iMinValue+" and "+iMaxValue);
		document.getElementById("requestedLoanTenure").value='';
		return false;
	}
	else
	{
		return true;
	}

}

function validateAssetCost()
{
	var staticVal = 0;
	var assetCost = 0;
    var reqLoanAmt = 0;
    if(document.getElementById("assetCost").value!='')
    {
    	assetCost = removeComma(document.getElementById("assetCost").value);
    }
    if(document.getElementById("requestedLoanAmount").value!='')
    {
    	reqLoanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
    }
//    alert("assetCost: "+assetCost);
//    alert("reqLoanAmt: "+reqLoanAmt);
    if(assetCost<reqLoanAmt && reqLoanAmt != '')
    {
    	alert("Asset Cost cannot be less than Requested Loan Amount");
    	document.getElementById("assetCost").value='';
    	document.getElementById("marginPerc").value = '';
		document.getElementById("marginAmount").value = '';
    	document.getElementById("assetCost").focus();
    	return false;
    }
}

function validateReqLAmt()
{

	var productTypeFlag = document.getElementById("productTypeFlag").value; 
	assetCost = removeComma(document.getElementById("assetCost").value);
	reqLoanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
//	if(document.getElementById("assetCost").value == ''){
//		if ((productTypeFlag != '' && productTypeFlag == 'A')) {
//			document.getElementById("requestedLoanAmount").value = '';
//			document.getElementById("assetCost").focus();
//			alert("First insert Asset Cost.");
//			return false;
//		}
//	}else 
	if(assetCost<reqLoanAmt && productTypeFlag=='A')
    {
    	alert("Asset Cost cannot be less than Requested Loan Amount");
    	document.getElementById("assetCost").value='';
    	document.getElementById("marginPerc").value = '';
		document.getElementById("marginAmount").value = '';
    	document.getElementById("assetCost").focus();
    	return false;
    }
	
	return true;

}

function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName("chk");
 	var zx=0;
	// alert(c);
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

function deleteLoanDetails()
{
		DisButClass.prototype.DisButMethod();
		 //alert("In deleteLoanDetail");
		 if(checkboxCheck(document.getElementsByName('chk')))
		 {
		   if(confirm("Are you want to delete product ?"))
		   {
			 var contextPath =document.getElementById('contextPath').value ;
			 document.getElementById("LoanDetailsForm").action = contextPath+"/loanBehindAction.do?method=deleteLoan";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("LoanDetailsForm").submit();
		   }else
			   document.getElementById("Delete").removeAttribute("disabled","true");
		   		DisButClass.prototype.EnbButMethod();
		 }
		 else
		 {
			 alert("Please Select atleast one Record");
			 document.getElementById("Delete").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 }
		
				
	
}

function calculateInstallment()
{
	var frequency= document.getElementById("frequency").value;
	var requestedLoanTenure= document.getElementById("requestedLoanTenure").value;
	var formatD=document.getElementById("formatD").value;
	var parseTenure=0;
	var freqMonth=0;
	if(frequency=='M')
	{
		freqMonth=1;
	}
	else if(frequency=='B')
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
		//alert("parsedFreq:----"+parsedFreq);
	}
	
	if(requestedLoanTenure!='')
	{
		parseTenure=parseInt(requestedLoanTenure);
		//alert("parseTenure:-----"+parseTenure);
	}
	
	//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
	var calInsat=parseInt(parseTenure/parsedFreq);
	if(calInsat==0)
		calInsat=1;
	//alert("calInsat:---"+calInsat);
	document.getElementById("noOfInstall").value=calInsat;
	document.getElementById("interestCompoundingFrequency").value="";
	return calInsat;
}

function isInt(n) {
	   return n % 1 == 0;
	}
function getDateObjectDueDate(dateString,dateSeperator)
{
	
    var dateParts = dateString.split(dateSeperator);
    var datePartObj=dateParts[1]+"/"+dateParts[0]+"/"+dateParts[2];
  //  alert("datePartObj::"+ datePartObj);
    var dateObject = new Date(dateParts[2], dateParts[1], dateParts[0]); // month is 0-based
   
	
	return dateObject;
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
	
	//alert("cYear::"+ cYear+ " cMonth::"+cMonth + " cDate::"+cDate);
	//Create Date Object
	//dtObject=new Date(cYear,cMonth,cDate);
	var datePartObj=cMonth+"/"+cDate+"/"+cYear;
	//alert("datePartObj::"+ datePartObj);
	//dtObject=new Date(cYear,cMonth,cDate);
	dtObject=new Date(datePartObj);
	//alert(dtObject);
	return dtObject;
}
/*
function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
    var dateParts = dateString.split(dateSeperator);
    var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
    /*
	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	//alert(cDate);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);
	//alert(cMonth);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	//alert(cYear);
	//cmonth1 = parseInt(cMonth)-1;
	//alert(cMonth-1);
	//Create Date Object
	dtObject=new Date(cYear,cMonth-1,cDate);
	
	//alert(dtObject);
	
	return dateObject;
}

*/

//Strat by sachin 
function calculateFinalRate()
{
	var markUp = document.getElementById("markUp").value;
	var baseRate = document.getElementById("baseRate").value;
	var finalRate = document.getElementById("effectiveRate").value;
	//alert("In calculateFinalRate markUp: "+markUp+"baseRate: "+baseRate);
	if(finalRate=="")
	{
		finalRate=0;
	}
	if(baseRate=="")
	{
		baseRate=0;
	}
//	effectiveRate=parseFloat(markUp)+parseFloat(baseRate);
	markUp=parseFloat(finalRate)-parseFloat(baseRate);
	document.getElementById("markUp").value=markUp.toFixed(7);
	document.getElementById("effectiveRate").value=finalRate.toFixed(7);
	
}
//end by sachin

function checkboxCheck(ch)
{

var zx=0;
   var flag=0;
   for(i=0;i<ch.length;i++)
	{
		
		if(ch[zx].checked==false)
		{
			flag=0;
		}
		else
		{
			flag=1;
			break;
		}
		zx++;
	}
	
	return flag;
}

function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function getMarginAmount(rr){
	
	//alert("getMarginAmount"+rr);
	var minPerc=0;
	var maxPerc=0;
	var defper =0;
	var productTypeFlag = document.getElementById("productTypeFlag").value;
	var repaymentType = document.getElementById("repaymentType").value;
	
	if(document.getElementById("minMarginRate").value!='')
	{
		minPerc=parseFloat(removeComma(document.getElementById("minMarginRate").value));
	}
	if(document.getElementById("maxMarginRate").value!='')
	{
		maxPerc=parseFloat(removeComma(document.getElementById("maxMarginRate").value));
	}
	if(document.getElementById(rr).value!='')
	{
		defper =parseFloat(removeComma(document.getElementById(rr).value));
	}
	//alert("ok"+minPerc+" "+maxPerc);
	var scheme=document.getElementById("scheme").value;
if(scheme!='')
{
	  var amount=document.getElementById("assetCost").value;
		//alert(amount);
		if(amount=="")
		{
			if((repaymentType!='' && repaymentType=='I') && (productTypeFlag != '' && productTypeFlag != 'N')){
				amount=0;
				document.getElementById(rr).value="";
				alert("Please Enter the Asset Cost");
				return false;
			}
		}
		else
		{
		   amount=removeComma(document.getElementById("assetCost").value);
		}
		
		//alert("defper "+defper+"minPerc "+minPerc+"maxPerc "+maxPerc);
		
	  if((defper>=minPerc)&&(defper<=maxPerc))	
	  {
		//alert("ok "+ rr);  
		if(checkRate(rr))
		{
			//alert("ok iffff "+ document.getElementById(rr).value);  
			var rate =0;
			var loanAm=parseFloat(amount);
			if(document.getElementById(rr).value!='')
			{
				rate= parseFloat(document.getElementById(rr).value);
			}
			
			document.getElementById("marginAmount").value=roundNumber((loanAm*rate)/100, 2)  ;
			 return true;
		}
	  }
	  else
	  {
		  alert("Please Enter the value from "+minPerc+" to "+maxPerc);
		  document.getElementById("marginAmount").value='';
		  document.getElementById(rr).value='';
		  document.getElementById(rr).focus();
		  return false;
	  }
	}
	else
	{
		  alert("Please Select the Scheme ");
		  document.getElementById("marginAmount").value='';
		  document.getElementById("assetCost").value='';
		  document.getElementById(rr).value='';
		  return false;
	}
	return true;
}

function getBaseRate()
{
	//alert("In getBaseRate");
	
		var baseRateType =document.getElementById('baseRateType').value ;

	var contextPath =document.getElementById('contextPath').value ;
	//alert("contextPath"+contextPath);
     if (baseRateType!= '') {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getBaseRateDetail";
		var data = "baseRateType="+baseRateType;
		//alert("address: "+address+"data: "+data);
		send_Rate(address,data);
		
		//alert("ok");
		
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
     
}

function send_Rate(address,data) 
{
	//alert("send_BaseRate"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_Rate(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_Rate(request){
	   //alert("status")
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			//alert(str);
			// var s1 = str.split("$:");
			// alert(s1);
		  // if(str.length>0)
		  // {
		    	// alert(trim(str[0]));
			
				document.getElementById('baseRate').value = trim(str);
				
				var markUp = document.getElementById("markUp").value;
				var baseRate = document.getElementById("baseRate").value;
				
				if(markUp=="")
				{
					markUp=0;
				}
				if(baseRate=="")
				{
					baseRate=0;
				}
	//			effectiveRate=parseFloat(removeComma(markUp))+parseFloat(removeComma(baseRate));
				effectiveRate=parseFloat(markUp)+parseFloat(baseRate);
				document.getElementById("effectiveRate").value=effectiveRate.toFixed(7);
			
				valuechange();
		// }
		}
	}
//method added by neeraj tripathi
function enableInstallNo()
{
	//alert("enableInstallNo");
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	//alert("rate  : "+rate);
	//alert("repaymentType  : "+repaymentType);
	if(rate!='' && repaymentType !='')
	if(repaymentType=='I')
	{
		if(rate=='F')
		{
			document.getElementById("noOfInstall").removeAttribute("readOnly");
			document.getElementById("noOfInstall").removeAttribute("tabIndex");
		}
		else
		{
			document.getElementById("noOfInstall").setAttribute("readOnly",true);
			document.getElementById("noOfInstall").setAttribute("tabIndex",-1);
		}
	}
}
function getFinalRate()
{
	//alert("In getFinalRate");
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var rateMethodType=document.getElementById("rateMethodType").value;
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			if(rateMethodType=='L')
			{
				document.getElementById("type1").removeAttribute("disabled",true);
				document.getElementById("type1").checked=false;
				document.getElementById("type2").removeAttribute("disabled",true);
				document.getElementById("type2").checked=true;				
				//document.getElementById("markUp").removeAttribute("disabled",true);
				//return true;
			}
			else if(rateMethodType=='F')
			{
				document.getElementById("type1").removeAttribute("disabled",true);
				document.getElementById("type1").checked=true;
				document.getElementById("type2").removeAttribute("disabled",true);
				document.getElementById("type2").checked=false;
				//document.getElementById("type2").removeAttribute("disabled",true);
				//document.getElementById("markUp").removeAttribute("disabled",true);
				//return true;
			}
			document.getElementById("baseRateType").removeAttribute("disabled",true);
			document.getElementById("baseRate").removeAttribute("disabled",true);
			document.getElementById("fixPriod").removeAttribute("readonly",true);
			var defEffect=0;
			if(document.getElementById("defaultEffectiveRate").value!='')
			{
				defEffect=parseFloat(document.getElementById("defaultEffectiveRate").value);
			}
			var baseR=0;
			if(document.getElementById("baseRate").value!='')
			{
				baseR=parseFloat(document.getElementById("baseRate").value);
			}
			document.getElementById("effectiveRate").value=document.getElementById("defaultEffectiveRate").value;
			document.getElementById("markUp").value=defEffect-baseR;
		}
		else
		{
			if(repaymentType=='N')
			{
				document.getElementById("rateType").value='E';
				alert("You can't Change Rate Type ");
			}
			else
			{
				document.getElementById("type1").removeAttribute("disabled",true);
				document.getElementById("type1").checked=true;
				document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("type2").checked=false;				
				document.getElementById("baseRateType").value='';
				document.getElementById("baseRate").value='';
				document.getElementById("markUp").value='';
				document.getElementById("effectiveRate").value='';
				document.getElementById("fixPriod").value='';
				//document.getElementById("type1").setAttribute("disabled",true);
				//document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				//document.getElementById("markUp").setAttribute("disabled",true);
				document.getElementById("fixPriod").setAttribute("readonly",true);
							
				document.getElementById("effectiveRate").value=document.getElementById("defaultFlatRate").value;
				document.getElementById("markUp").value=document.getElementById("defaultFlatRate").value;
			}
		}
		//code added by neeraj tripathi
		//alert("repaymentType : "+repaymentType);
		//alert("rate : "+rate);
		if(repaymentType !='N')
		{
			if(rate=='F')
			{
				document.getElementById("noOfInstall").removeAttribute("readOnly");
				document.getElementById("noOfInstall").removeAttribute("tabIndex");
			}
			else
			{
				var jspTenure = document.getElementById("requestedLoanTenure").value;
				var frequency=document.getElementById("frequency").value;
				if(jspTenure=="")
				{
					document.getElementById("requestedLoanTenure").value="1";
					jspTenure="1";				
				}
				if(frequency=="")
				{
					document.getElementById("frequency").value="M";
					frequency="M";
				}
				var tenure=parseInt(removeComma(jspTenure));
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
				var noOfInstallDF=tenure/fre;
				var result=parseInt(noOfInstallDF);
				if(result==0)
					result=1;
				document.getElementById("noOfInstall").value=result;
				//document.getElementById("noOfInstall").setAttribute("readOnly",true);
				document.getElementById("noOfInstall").setAttribute("tabIndex",-1);
			}
		}
		//tripathi's space end
		
	}
}

function validRepayDateInDeal()
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
		document.getElementById("interestDueDate").value='';
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

// ----------------------------------------validation---------------------------------

function validateForm(formName, manFields) {
	var ck_numeric = /^[0-9,.]{1,50}$/;
	var ck_alphaNumeric = /^[A-Za-z0-9,\. _]{1,50}$/;
	var ck_mail = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

	var errors = [];
	var reqFields = [];
	var matchField = "";
	var elem = document.getElementById(formName).elements;
	for ( var i = 0; i <= elem.length; i++) {
		var str = '';
		if (!elem[i] || elem[i] == "undefined" || elem[i].value == "undefined"
				|| elem[i].name == (null || '')) {
			continue;
		}
		if (manFields.match(elem[i].name)) {
			matchField = manFields.match(elem[i].name);
			if (elem[i].value == null || elem[i].value == "") {
				errors[errors.length] = elem[i].name;
			}

		} else {
			continue;
		}
	}
	if (errors.length > 0) {
		reportErrors(errors, matchField);
		return false;
	}else
		return true;
}

function reportErrors(errors, matchField) {
	var msg = "";
	var errorMsg = "";
	for ( var i = 0; i < errors.length; i++) {
		var numError = i + 1;
		msg += errors[i];
	}
	if (msg.match("product")) {
		errorMsg += "* Product is required \n";
	}
	if (msg.match("scheme")) {
		errorMsg += "* Scheme is required \n";
	}
	if (msg.match("requestedLoanAmount")) {
		errorMsg += "* Requested Loan Amount is required \n";
	}
	if (msg.match("requestedLoanTenure")) {
		errorMsg += "* Requested Loan Tenure is required \n";
	}
	if (msg.match("rateType")) {
		errorMsg += "* Rate Type is required \n";
	}
	if (msg.match("sectorType")) {
		errorMsg += "* Sector Type is required \n";
	}	
	if (msg.match("loanPurpose")) {
		errorMsg += "* Purpose is required \n";
	}

	if (msg.match("product")) {
		document.getElementById("productButton").focus();
	} else if (msg.match("scheme")) {
		document.getElementById("schemeButton").focus();
	} else if (msg.match("requestedLoanAmount")) {
		document.getElementById("requestedLoanAmount").focus();
	} else if (msg.match("requestedLoanTenure")) {
		document.getElementById("requestedLoanTenure").focus();
	} else if (msg.match("rateType")) {
		document.getElementById("rateType").focus();
	} else if (msg.match("sectorType")) {
		document.getElementById("sectorType").focus();
	} else if (msg.match("loanPurpose")) {
		document.getElementById("loanPurpose").focus();
	}
	
	alert(errorMsg);

}

function calLtvPerc()
{
	//alert("calLtvPerc: "+document.getElementById("repaymentType").value);
	var marginPerc=0.00;
	var hundred=removeComma('100.00');
	var ltvPerc=0.00;
	//alert(hundred);
	if(document.getElementById("marginPerc").value!='')
	{
		marginPerc=removeComma(document.getElementById("marginPerc").value);
		//alert(marginPerc);
		ltvPerc =hundred-marginPerc;
		//alert(ltvPerc);
		document.getElementById("ltvPerc").value=ltvPerc.toFixed(2);
		return true;
	}
	else if(document.getElementById("repaymentType").value=='N')
	{
		document.getElementById("ltvPerc").value='100.00';
		return true;
	}
	else
	{		
		document.getElementById("ltvPerc").value=ltvPerc.toFixed(2);
		return true;
	}
	
	
}

function sevenDecimalnumbersonlyForMarkup(e, san, Max){
	//alert(e.charCode+" e.keyCode: "+e.keyCode);
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+8;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 && unicode != 45)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}

function checkMarkUpRate(val)

{
	

	var rate = document.getElementById(val).value;
	 
//alert("Passed Value: "+rate);
if(rate=='')
	{
		rate=0;
		//alert("Please Enter the value");
	//	return false;
		
	}
	    var intRate = parseFloat(rate);
	  //  alert(intRate);
	    if(intRate<=100)
	      {
		    return true;
	       }

	        else
	           {
		        alert("Please Enter the value less than 100");
		        document.getElementById(val).value="";
		//document.getElementById(val).focus;
		        return false;
	         }
	
}

function refreshLoanDetail()
{
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("LoanDetailsForm").action = contextPath+ "/loanProcessAction.do?method=refreshLoanDetail";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("LoanDetailsForm").submit();
	return true;
}


function validateMaturityDate()
{
	//alert("in function ");
	var repaymentType = document.getElementById('repaymentType').value;
	var installmentType= document.getElementById('installmentType').value;
	var dueDay =  document.getElementById('cycleDate').value;
	//var OldMaturityDate= document.getElementById('OldMaturityDate').value;
	var UpdateDateStr1;
	
	
	
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
		var y= parseInt(document.getElementById('tenureInDay').value)
		var formatD=document.getElementById("formatD").value;

		//alert(x);
		var currDate =   document.getElementById('OldMaturityDate1').value;
		//alert("currDate "+currDate);
		//var y= parseInt(document.getElementById('tenureInDay').value)
	//	alert("Tenure in Day"+ y);
		//var currDate  = new Date(repayDate);
		  //alert(currDate);
		  var currDay   = currDate.substring(0,2);
		  //alert(currDay);
		  var currMonth = currDate.substring(3,5);
		 // alert(currMonth);
		  var currYear  = currDate.substring(6,10);
		 // alert(currYear);
		  var seprator = formatD.substring(2, 3);
		  if(seprator=='-')
		  {
			  seprator = '/';
			  UpdateDateStr1 = currMonth + seprator + currDay + seprator + currYear;
		  }
		  else
		  {
			  seprator = '/';
		    UpdateDateStr1 = currMonth + seprator + currDay + seprator + currYear;
		    //alert(UpdateDateStr);
			
		  }
		  var CurrentDate = new Date(UpdateDateStr1);
			//alert("CurrentDate "+CurrentDate);
		//	CurrentDate.setDate(CurrentDate.getDate()+y);
		   // alert(CurrentDate.getMonth());
		   modMonth=CurrentDate.getMonth();//+1;
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
		  
		  var userDefinedMaturityDate=document.getElementById('maturityDate1').value;
		  //alert("userDefinedMaturityDate "+userDefinedMaturityDate);
		  var sDay   = userDefinedMaturityDate.substring(0,2);
		  //alert(currDay);
		  var sMonth = userDefinedMaturityDate.substring(3,5);
		 // alert(currMonth);
		  var sYear  = userDefinedMaturityDate.substring(6,10);
		 // alert(currYear);
		  var seprator = formatD.substring(2, 3);
		  if(seprator=='-')
		  {
			  seprator = '/';
			  UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
		  }
		  else
		  {
			  seprator = '/';
		    UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
			
		  }
		    var userDefinedMaturityDate = new Date(UpdateDateStr);
		   //alert("987");
		/*if(userDefinedMaturityDate.getFullYear()!=CurrentDate.getFullYear())
			{	
				alert("User Can select maturity date according to tenure month and year");
				document.getElementById('maturityDate1').value=document.getElementById('OldMaturityDate1').value;;
			}
		if(userDefinedMaturityDate.getMonth()!=CurrentDate.getMonth())
			{
				alert("User Can select maturity date according to tenure month and year");
				document.getElementById('maturityDate1').value=document.getElementById('OldMaturityDate1').value;;
			}
		if(ParseInt(sDay)<0 && ParseInt(sDay)>31 )
			{
				alert("Select Date range between 1-31");
			}*/
			
	}
		  
	
}

/*function calculateMaturityDateInDeal()
{
	
	var repaymentType = document.getElementById('repaymentType').value;
	var installmentType= document.getElementById('installmentType').value;
	if(installmentType=='' || installmentType == null)
		{
		alert("First Select Installment Type");	
		document.getElementById('maturityDate1').value='';
   	 	return false;
		}
	//alert(repaymentType);
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		if(installmentType=='S'){
		var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
		var y= parseInt(document.getElementById('tenureInDay').value); //or whatever offset
		
		var formatD=document.getElementById("formatD").value;

		//alert(x);
		var currDate =   document.getElementById('repayEffectiveDate').value;
	
		//var currDate  = new Date(repayDate);
		  //alert(currDate);
		  var currDay   = currDate.substring(0,2);
		  //alert(currDay);
		  var currMonth = currDate.substring(3,5);
		 // alert(currMonth);
		  var currYear  = currDate.substring(6,10);
		 // alert(currYear);
		  var seprator = formatD.substring(2, 3);
		  if(seprator=='-')
			  seprator = '/';
		  else
			  seprator = '/';
		    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
		   // alert(UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);
			//alert(CurrentDate);
			CurrentDate.setDate(CurrentDate.getDate()+y);
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
		  document.getElementById('maturityDate').value=ModDateStr;
	}
		else
			{
			var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
			var formatD=document.getElementById("formatD").value;

			//alert(x);
			var currDate =   document.getElementById('repayEffectiveDate').value;
			//var currDate  = new Date(repayDate);
			  //alert(currDate);
			  var currDay   = currDate.substring(0,2);
			  //alert(currDay);
			  var currMonth = currDate.substring(3,5);
			 // alert(currMonth);
			  var currYear  = currDate.substring(6,10);
			 // alert(currYear);
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
			  document.getElementById('maturityDate').value=ModDateStr;
			
			}
	}
}*/

function getDueDayNextDueDate()
{
	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	 
	var contextPath =document.getElementById('contextPath').value ;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	
	if(repayEffectiveDate!='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayNextDueDateDetail";
		var data = "repayEffectiveDate="+repayEffectiveDate;
		sendRequestGetDueDayNextDueDate(address,data);
		return true;
	}
	else
	{
		alert("Repay Effective Date is required.");	
   	 	return false;
	}
}
function sendRequestGetDueDayNextDueDate(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodGetDueDayNextDueDate(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodGetDueDayNextDueDate(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		//alert(s1.length);
		if(s1.length>0)
	    {
			//alert(trim(s1[1]));
			//document.getElementById('frequency').value='M';
			var freq=document.getElementById('frequency').value;
			document.getElementById('cycleDate').value=trim(s1[0]);
			if(trim(s1[1])!='')
			{
				document.getElementById('nextDueDate').value=trim(s1[1]);
				calculateNextDueDate(freq);
			}
		   // getElementById('totalRecevable').value = trim(s1[0]);
			//window.close();
	    }
		
	}
}



function getDueDay()
{
	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	var cycleDate=document.getElementById('cycleDate').value;
	var contextPath =document.getElementById('contextPath').value ;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	
	if(repayEffectiveDate!='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayDetail";
		var data = "repayEffectiveDate="+repayEffectiveDate+"&cycleDate="+cycleDate;
		sendRequestDueDay(address,data);
		return true;
	}
	else
	{
		alert("Repay Effective Date is required.");	
   	 	return false;
	}
}
function sendRequestDueDay(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodDueDay(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodDueDay(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		//alert(s1.length);
		if(s1.length>0)
	    {
			//alert(trim(s1[1]));
			var freq=document.getElementById('frequency').value;
			if(trim(s1[1])!='')
			{
				document.getElementById('nextDueDate').value=trim(s1[1]);
				//alert("nextDueDate::"+ trim(s1[1]));
				calculateNextDueDate(freq);
			}
			
		   // getElementById('totalRecevable').value = trim(s1[0]);
			//window.close();
	    }
		
	}
}

function calculateNextDueDate(frequency)
{
    //alert(frequency);
	var frequency= frequency;
	var formatD=document.getElementById("formatD").value;
	var freqMonth=0;
//	if(frequency=='M')
//	{
//		freqMonth=1;
//	}
//	else 
	if(frequency=='B')
	{
		freqMonth=1;
	}
	else if(frequency=='Q')
	{
		freqMonth=2;
	}
	else if(frequency=='H')
	{
		freqMonth=5;
	}
	else if(frequency=='Y')
	{
		freqMonth=11;
	}
	
	if(frequency!='')
	{
		parsedFreq=parseInt(freqMonth);
		//alert("parsedFreq:----"+parsedFreq);
	}
	
	var repayEffectiveDate=document.getElementById("nextDueDate").value;
//	alert("repayEffectiveDate:: "+repayEffectiveDate);
	var d1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
//	var d1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
	//alert("d1:: "+d1);
//	alert("Next Due Date: "+repayEffectiveDate+" Month: "+d1.getMonth()+" parsedFreq: "+parsedFreq);
	
	d1.setMonth(d1.getMonth()+parsedFreq);
	var curr_date = d1.getDate();
	var curr_month = d1.getMonth();
//	alert("curr_month::"+ curr_month);
	var curr_year = d1.getFullYear();
	
/*	if(curr_month==00)
	{
		curr_month=12;
		curr_year=curr_year-1;
	}*/
	//alert("curr_date "+padding(curr_date)+"curr_month: "+padding(curr_month)+"curr_year: "+curr_year);
	document.getElementById("nextDueDate").value=padding(curr_date)+formatD.substring(2, 3)+padding(curr_month+1)+formatD.substring(2, 3)+curr_year;

}

function padding(number){

	return number < 10 ? "0"+number : number;

	}

function valuechange(){
//	alert("test1");
	var markUp = document.getElementById("markUp").value;
	var baseRate = document.getElementById("baseRate").value;
	var finalRate = document.getElementById("effectiveRate").value;
//	alert("In calculateFinalRate markUp: "+markUp+"baseRate: "+baseRate);
	if(finalRate=="" || finalRate == null)
	{
		finalRate=0;
	}
	if(baseRate=="" || baseRate== null)
	{
		baseRate=0;
	}
//	effectiveRate=parseFloat(markUp)+parseFloat(baseRate);
	markUp=parseFloat(0)-parseFloat(baseRate);
	//alert("markUp"+markUp);
	document.getElementById("markUp").value=markUp.toFixed(2);
	document.getElementById("effectiveRate").value="0.0000000";
	
	
}

function calcDay()
{
	   //alert("sacin");
	   var requestedLoanTenure= document.getElementById("requestedLoanTenure").value;
	   var daysBasis= document.getElementById("daysBasis").value;
	  // alert("daysBasis"+daysBasis);
	   
	   	if(requestedLoanTenure==''){
	   		requestedLoanTenure=0;
	   	}
	   	if(requestedLoanTenure!='')
	   	{
	   		requestedLoanTenure=parseInt(requestedLoanTenure);
	   		//alert("parseTenure:-----"+parseTenure);
	   	}
	   	if(daysBasis=='A'){
	 //  		alert("under a"+daysBasis);
	   		var day=requestedLoanTenure*30.42;
		   	document.getElementById("tenureInDay").value=day.toFixed(0); 
		   }
	   	else{
	   	var day=requestedLoanTenure*30;
	   	document.getElementById("tenureInDay").value=day;
	   	}
}

function calTenureMonthForMaturityDate()
{
	   //alert("test 123");
	   var requestedLoanTenure= document.getElementById("requestedLoanTenure").value;
	   var daysBasis= document.getElementById("daysBasis").value;
	   var formatD=document.getElementById("formatD").value;
	   var installmentType= document.getElementById("");
	  // alert("daysBasis"+daysBasis);	   
	   	if(requestedLoanTenure=='')
		{
	   		requestedLoanTenure=0;
	   	}
	   	if(requestedLoanTenure!='')
	   	{
	   		requestedLoanTenure=parseInt(requestedLoanTenure);
	   		//alert("parseTenure:-----"+parseTenure);
	   	}
	   	if(daysBasis=='A')
		{
	   		//alert("brijesh 123");
	   		//var day=requestedLoanTenure*30.42;
	   		var currDate =   document.getElementById('nextDueDate').value;
			var currDay   = currDate.substring(0,2);
			//alert(currDay);
			var currMonth = currDate.substring(3,5);
			// alert(currMonth);
			var currYear  = currDate.substring(6,10);
			// alert(currYear);
			var seprator = formatD.substring(2, 3);
			if(seprator=='-')
				seprator = '/';
			else
				seprator = '/';
				UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			// alert(UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);		
			var userDefinedMaturityDate=document.getElementById('maturityDate1').value;		  
			var sDay   = userDefinedMaturityDate.substring(0,2);
			//alert(currDay);
			var sMonth = userDefinedMaturityDate.substring(3,5);
			// alert(currMonth);
			var sYear  = userDefinedMaturityDate.substring(6,10);
			// alert(currYear);
			var seprator = formatD.substring(2, 3);
			if(seprator=='-')
				seprator = '/';
			else
				seprator = '/';
				UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
		    var userDefinedMaturityDate = new Date(UpdateDateStr);	
			var _MS_PER_DAY = 1000 * 60 * 60 * 24*30.42;

			//a and b are javascript Date objects				
			// Discard the time and time-zone information.
			//test it
			var a = new Date(userDefinedMaturityDate);
			//alert("a "+a);
			var b = new Date(CurrentDate);
			//alert("b "+b);
			var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
			var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
			var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
			var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
			var finalMonth=Math.abs(difference);//+1;
			//document.getElementById("tenureInDay").value=finalDays;
			//document.getElementById("requestedLoanTenure").value=finalMonth;
			//document.getElementById("noOfInstall").value=finalMonth;
			//temp start here
			//var loanTenure = document.getElementById("requestedLoanTenure").value;
			//var res = Math.floor(loanTenure*30.42);
		
			//temp start here
			var oldDate = document.getElementById('OldMaturityDate1').value;
			var oldDay = oldDate.substring(0,2);
			var oldMonth = oldDate.substring(3,5);
			var oldYear = oldDate.substring(6,10);
			//alert("oldDate "+oldDate);
			//alert("oldMonth "+oldMonth);
			//alert("oldYear "+oldYear);
			var newDate = document.getElementById('maturityDate1').value;
			var newDay = newDate.substring(0,2);
			var newMonth = newDate.substring(3,5);
			var newYear = newDate.substring(6,10);
			//alert("newDate "+newDate);
			//alert("newMonth "+newMonth);
			//alert("newYear "+newYear);
			
			var result = newDay-oldDay;
				//alert("result "+result);
			/*var finalDays2 = Math.floor(document.getElementById("requestedLoanTenure").value*30.42);
			//var brijesh = math.ceil(finalDays2+result);
			//alert("brijesh "+brijesh);
			document.getElementById("tenureInDay").value=finalDays2+result;
			if(newMonth<oldMonth)
			{
				alert("under the if month 365 ");
				var tenureDay = document.getElementById("tenureInDay").value;
				var tempMonth = oldMonth - newMonth;
				alert("tempMonth "+tempMonth);
				var calc = Math.floor(tenureDay/30)-tempMonth;
				alert("calc "+calc);
				document.getElementById("requestedLoanTenure").value=calc-tempMonth;
				document.getElementById("noOfInstall").value=calc-tempMonth;
				var temp = document.getElementById("requestedLoanTenure").value;
				//var res3 = newDay-oldDay;
				document.getElementById("tenureInDay").value=Math.ceil(temp*30.42);
			}
			//temp end here
			alert(" in the if with 1010");*/
			
			//20-08-2018
			if(newMonth == oldMonth && newYear == oldYear)
			{			
					//alert("under the if 78910 day basis A");
					/*document.getElementById("tenureMonths").value=finalMonth;//-1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//-1;	*/						
			}
			else if(newMonth<oldMonth)
			{
				//alert("under the if month  day basis A");
				if(newYear != oldYear)
				{
					document.getElementById("requestedLoanTenure").value=finalMonth;//+1;
					var temp123 = document.getElementById("requestedLoanTenure").value;
					document.getElementById("noOfInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30.42;
				}
				else
				{
					var tenureDay = document.getElementById("tenureInDay").value;
					var tempMonth = oldMonth - newMonth;
					//alert("tempMonth "+tempMonth);
					var calc = Math.floor(tenureDay/30.42);//-tempMonth;
					//alert("calc "+calc);
					document.getElementById("requestedLoanTenure").value=calc;//-tempMonth;
					document.getElementById("noOfInstall").value=calc;//-tempMonth;
					var newTempMonth = document.getElementById("noOfInstall").value;
					document.getElementById("tenureInDay").value=newTempMonth*30.42;					 
				}
				
				
			}
			else if(newMonth>oldMonth)
			{
				//alert("under the if month second  day basis A");	
				if(newYear != oldYear)
				{
					/*var a = document.getElementById("tenureMonths").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					alert("tempTenure "+c);
					document.getElementById("tenureMonths").value=c;
					document.getElementById("loanNoofInstall").value=c;*/
					document.getElementById("requestedLoanTenure").value=finalMonth;//+1;
					var temp123 = document.getElementById("requestedLoanTenure").value;
					document.getElementById("noOfInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30.42;
				}
				else
				{
					var a = document.getElementById("requestedLoanTenure").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					//alert("tempTenure "+c);
					document.getElementById("requestedLoanTenure").value=c;
					document.getElementById("noOfInstall").value=c;	
					var newTempMonth2 = document.getElementById("noOfInstall").value;
					document.getElementById("tenureInDay").value=newTempMonth2*30.42;					
				}
									
			}
			else if(newMonth != oldMonth && newYear != oldYear) 
			{	
				//alert("under the 23456  day basis A");
				var finalMonth=Math.abs(difference);
				if(finalMonth >= 48)
				{
					if(finalMonth >= 96)
					{ 
						
						var finalMonth=Math.abs(difference)-1;
					}
					else
					{
						
						var finalMonth=Math.abs(difference)-1;
					}
					
				}				
			
				
				document.getElementById("requestedLoanTenure").value=finalMonth;
				var temp123 = document.getElementById("requestedLoanTenure").value;
				document.getElementById("noOfInstall").value=finalMonth;
				document.getElementById("tenureInDay").value=temp123*30.42;		
			}
			else if(newYear != oldYear)
			{
				//alert("under the 2345678  day basis A");				
				var finalMonth=Math.abs(difference);
				if(finalMonth >= 48)
				{
					if(finalMonth >= 96 && finalMonth < 120)
					{
						
						var finalMonth=Math.abs(difference)-1;
					}
					else if(finalMonth >= 120 && finalMonth < 180)
					{
						
						var finalMonth=Math.abs(difference)-1;
						
					}
					else if(finalMonth >= 180)
					{
						
						var finalMonth=Math.abs(difference)-1;
						
					}
					else
					{
						
						var finalMonth=Math.abs(difference)-1;
					}
					
				}				
				document.getElementById("requestedLoanTenure").value=finalMonth+1;
				var temp123 = document.getElementById("requestedLoanTenure").value;
				document.getElementById("noOfInstall").value=finalMonth+1;
				document.getElementById("tenureInDay").value=temp123*30.42;
			}
			//changes end here
			var finalDays2 = Math.floor(document.getElementById("requestedLoanTenure").value*30.42);
			document.getElementById("tenureInDay").value=finalDays2+result;
			//temp end here
			//20-08-2018
					  
	   	}
	   	
	   	else
	   	{
			//	var day=requestedLoanTenure*30;
			var currDate =   document.getElementById('nextDueDate').value;
			var currDay   = currDate.substring(0,2);
			//alert(currDay);
			var currMonth = currDate.substring(3,5);
			// alert(currMonth);
			var currYear  = currDate.substring(6,10);
			// alert(currYear);
			var seprator = formatD.substring(2, 3);
			if(seprator=='-')
				seprator = '/';
			else
				seprator = '/';
			UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			// alert(UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);	
	
			var userDefinedMaturityDate=document.getElementById('maturityDate1').value;
			//alert("userDefinedMaturityDate2 "+userDefinedMaturityDate);	  
			var sDay   = userDefinedMaturityDate.substring(0,2);
			//alert(currDay);
			var sMonth = userDefinedMaturityDate.substring(3,5);
			// alert(currMonth);
			var sYear  = userDefinedMaturityDate.substring(6,10);
			// alert(currYear);
			var seprator = formatD.substring(2, 3);
			if(seprator=='-')
			{
				seprator = '/';
				UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
			}
			else
			{
				seprator = '/';
				UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
			}
			var userDefinedMaturityDate = new Date(UpdateDateStr);	
			var _MS_PER_DAY = 1000 * 60 * 60 * 24*30;		
			var a = new Date(userDefinedMaturityDate);
			//alert("a "+a);
			var b = new Date(CurrentDate);
			//alert("b "+b);
			var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
			var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
			var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
			var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
			var finalMonth=Math.abs(difference)+1;
			//document.getElementById("tenureInDay").value=finalDays;
			//document.getElementById("requestedLoanTenure").value=finalMonth;
			//document.getElementById("noOfInstall").value=finalMonth;
			var loanTenure = document.getElementById("requestedLoanTenure").value;
			var res = loanTenure*30;		
			//temp start here
			var oldDate = document.getElementById('OldMaturityDate1').value;
			//alert("oldDate "+oldDate);
			var oldDay = oldDate.substring(0,2);
			var oldMonth = oldDate.substring(3,5);
			var oldYear = oldDate.substring(6,10);
			//alert("oldYear "+oldYear);
			//alert("oldMonth "+oldMonth);
			var newDate = document.getElementById('maturityDate1').value;
			var newDay = newDate.substring(0,2);
			var newMonth = newDate.substring(3,5);
			var newYear = newDate.substring(6,10);
			//alert("newYear "+newYear); 
			//alert("newMonth "+newMonth);
			var res2 = newDay-oldDay;		
			document.getElementById("tenureInDay").value=res+res2;
			
			//20-08-2018
			if(newMonth == oldMonth && newYear == oldYear)
			{			
					//alert("under the if 78910");
					/*document.getElementById("tenureMonths").value=finalMonth;//-1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//-1;	*/						
			}
			else if(newMonth<oldMonth)
			{
				//alert("under the if month ");
				if(newYear != oldYear)
				{
					document.getElementById("requestedLoanTenure").value=finalMonth;//+1;
					var temp123 = document.getElementById("requestedLoanTenure").value;
					document.getElementById("noOfInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30;
				}
				else
				{
					var tenureDay = document.getElementById("tenureInDay").value;
					var tempMonth = oldMonth - newMonth;
					//alert("tempMonth "+tempMonth);
					var calc = Math.floor(tenureDay/30)-tempMonth;
					//alert("calc "+calc);
					document.getElementById("requestedLoanTenure").value=calc;//-tempMonth;
					document.getElementById("noOfInstall").value=calc;//-tempMonth;
					var newTempMonth = document.getElementById("noOfInstall").value;
					document.getElementById("tenureInDay").value=newTempMonth*30;
					 
				}
				
				
			}
			else if(newMonth>oldMonth)
			{
				//alert("under the if month second ");	
				if(newYear != oldYear)
				{
					/*var a = document.getElementById("tenureMonths").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					alert("tempTenure "+c);
					document.getElementById("tenureMonths").value=c;
					document.getElementById("loanNoofInstall").value=c;*/
					document.getElementById("requestedLoanTenure").value=finalMonth;//+1;
					var temp123 = document.getElementById("requestedLoanTenure").value;
					document.getElementById("noOfInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30;
				}
				else
				{
					var a = document.getElementById("requestedLoanTenure").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					//alert("tempTenure "+c);
					document.getElementById("requestedLoanTenure").value=c;
					document.getElementById("noOfInstall").value=c;	
					var newTempMonth2 = document.getElementById("noOfInstall").value;
					document.getElementById("tenureInDay").value=newTempMonth2*30;					
				}
									
			}
			else if(newMonth != oldMonth && newYear != oldYear) 
			{	
				//alert("under the 23456 ");
				var finalMonth=Math.abs(difference);
				if(finalMonth >= 48)
				{
					if(finalMonth >= 96)
					{ 
						
						var finalMonth=Math.abs(difference)-1;
					}
					else
					{
						
						var finalMonth=Math.abs(difference)-1;
					}
					
				}				
			
				
				document.getElementById("requestedLoanTenure").value=finalMonth;
				var temp123 = document.getElementById("requestedLoanTenure").value;
				document.getElementById("noOfInstall").value=finalMonth;
				document.getElementById("tenureInDay").value=temp123*30;		
			}
			else if(newYear != oldYear)
			{	
				//alert("under the 2345678 ");				
				var finalMonth=Math.abs(difference);
				if(finalMonth >= 12)
				{
					if(finalMonth >= 72 && finalMonth < 96)
					{
						
						var finalMonth=Math.abs(difference)-2;
					}
					if(finalMonth >= 96 && finalMonth <= 120)
					{
						
						var finalMonth=Math.abs(difference)-3;
					}
					else if(finalMonth > 120 && finalMonth < 144)
					{
						
						var finalMonth=Math.abs(difference)-2;
						
					}
					else if(finalMonth > 144 && finalMonth < 180)
					{
						
						var finalMonth=Math.abs(difference)-3;
						
					}
					else if(finalMonth >= 180)
					{
						
						var finalMonth=Math.abs(difference)-3;
						
					}
					else
					{
						
						var finalMonth=Math.abs(difference)-1;
					}
					
				}				
				document.getElementById("requestedLoanTenure").value=finalMonth+1;
				var temp123 = document.getElementById("requestedLoanTenure").value;
				document.getElementById("noOfInstall").value=finalMonth+1;
				document.getElementById("tenureInDay").value=temp123*30;
			}
		//20-08-2018				
		}
}
//Start here | Brijesh Pathak
function calTenureMonthForMaturityDateNextDueDate()
{
	  // alert("sachin456");
	   var nextDueDate= document.getElementById("nextDueDate").value;
	  // var daysBasis= document.getElementById("daysBasis").value;
	   var formatD=document.getElementById("formatD").value;
	   var installmentType= document.getElementById("installmentType");
           if(installmentType){
		   installmentType = installmentType.value;
	   }
	   var repayEffDate =   document.getElementById('repayEffectiveDate').value; 
	   var repayEffMonth = repayEffDate.substring(3,5);	 
	   //	var day=nextDueDate*30;
	   var currDate =   document.getElementById('nextDueDate').value;
		var currDay   = currDate.substring(0,2);
	  //alert(currDay);
	  var currMonth = currDate.substring(3,5);
		// alert(currMonth);
	  var currYear  = currDate.substring(6,10);
	 // alert(currYear);
	  var seprator = formatD.substring(2, 3);
	  if(seprator=='-')
		  seprator = '/';
	  else
		  seprator = '/';
	    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
	   // alert(UpdateDateStr);
	  var CurrentDate = new Date(UpdateDateStr);	
	
		var userDefinedMaturityDate=document.getElementById('maturityDate1').value;			  
	  var sDay   = userDefinedMaturityDate.substring(0,2);
	  //alert(currDay);
	  var sMonth = userDefinedMaturityDate.substring(3,5);
	 // alert(currMonth);
	  var sYear  = userDefinedMaturityDate.substring(6,10);
	 // alert(currYear);
	  var seprator = formatD.substring(2, 3);
	  if(seprator=='-')
		  seprator = '/';
	  else
		  seprator = '/';
	    UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
	    var userDefinedMaturityDate = new Date(UpdateDateStr);
	
		var _MS_PER_DAY = 1000 * 60 * 60 * 24*30;
		var a = new Date(userDefinedMaturityDate);
		var b = new Date(CurrentDate);
		var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
		var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
		var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
		var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
		var finalMonth=Math.abs(difference) 
		document.getElementById("tenureInDay").value=finalDays;
		//alert("in else");

                 if(installmentType=='S'){
		document.getElementById("requestedLoanTenure").value=finalMonth;
		document.getElementById("noOfInstall").value=finalMonth;
		//brijesh start here 22-08-2018
		if(currMonth > repayEffMonth && finalMonth > 72)
		{
			//alert("new alert");
			//document.getElementById("tenureInDay").value=finalDays;
			document.getElementById("requestedLoanTenure").value=finalMonth-1;
			document.getElementById("noOfInstall").value=finalMonth-1;



		}
			else if (currMonth == repayEffMonth && finalMonth>72)
			{
				//alert("new alert else");
				//document.getElementById("tenureInDay").value=finalDays;
				document.getElementById("requestedLoanTenure").value=finalMonth-1;
				document.getElementById("noOfInstall").value=finalMonth-1;
			}


}	
//end here 22-08-2018
}
/*function calTenureMonthForMaturityDateMaturityDate()
{
	   alert("test123");
	   var repayEffectiveDate= document.getElementById("repayEffectiveDate").value;
	  // var daysBasis= document.getElementById("daysBasis").value;
	   var formatD=document.getElementById("formatD").value;
	   var installmentType= document.getElementById("");
	
	

	   	
	   //	var day=nextDueDate*30;
	   	var currDate =   document.getElementById('repayEffectiveDate').value;


		var currDay   = currDate.substring(0,2);
	  //alert(currDay);
	  var currMonth = currDate.substring(3,5);
	 // alert(currMonth);
	  var currYear  = currDate.substring(6,10);
	 // alert(currYear);
	  var seprator = formatD.substring(2, 3);
	  if(seprator=='-')
		  seprator = '/';
	  else
		  seprator = '/';
	    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
	   // alert(UpdateDateStr);
		var CurrentDate = new Date(UpdateDateStr);
	
	
	var userDefinedMaturityDate=document.getElementById('maturityDate1').value;
	  
	  var sDay   = userDefinedMaturityDate.substring(0,2);
	  //alert(currDay);
	  var sMonth = userDefinedMaturityDate.substring(3,5);
	 // alert(currMonth);
	  var sYear  = userDefinedMaturityDate.substring(6,10);
	 // alert(currYear);
	  var seprator = formatD.substring(2, 3);
	  if(seprator=='-')
		  seprator = '/';
	  else
		  seprator = '/';
	    UpdateDateStr = sMonth + seprator + sDay + seprator + sYear;
	    var userDefinedMaturityDate = new Date(UpdateDateStr);
	
		var _MS_PER_DAY = 1000 * 60 * 60 * 24*30;	
		var a = new Date(userDefinedMaturityDate);
		var b = new Date(CurrentDate);
		var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
		var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
		var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
		var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
		var finalMonth=Math.abs(difference) 
		document.getElementById("tenureInDay").value=finalDays;
		//alert("in else");
		document.getElementById("requestedLoanTenure").value=finalMonth;
		document.getElementById("noOfInstall").value=finalMonth;
}*/
//start here | Brijesh Pathak
function calculateMaturityDateInDeal()
{
	//alert("In new function");
	var repaymentType = document.getElementById('repaymentType').value;
	var installmentType= document.getElementById('installmentType').value;
	var dueDay =  document.getElementById('cycleDate').value;
	//alert("dueDay "+dueDay);	
	if(installmentType=='' || installmentType == null)
	{
		//alert("First Select Installment Type");	
		document.getElementById('maturityDate1').value='';
   	 	return false;
	}
	//alert(repaymentType);
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		if(installmentType=='S')
		{
			var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
			var y= parseInt(document.getElementById('tenureInDay').value); //or whatever offset
			var nextDueDate = document.getElementById('nextDueDate').value;
			var daysBasis= document.getElementById("daysBasis").value;
			//alert ("nextDueDate "+nextDueDate);
			//alert("tenure"+x);
			//alert("tenure in day"+y);
			var formatD=document.getElementById("formatD").value;	
			//alert(x);
			//var currDate =   document.getElementById('repayEffectiveDate').value; //commented on 21-08-2018
			var currDate =   document.getElementById('nextDueDate').value; //added on 21-08-2018
			var repayEffDate =   document.getElementById('repayEffectiveDate').value; // added on 22-8-2018
			 var repayEffMonth = repayEffDate.substring(3,5);
			//var currDate  = new Date(repayDate);
		    //alert(currDate);
			var currDay   = currDate.substring(0,2);
			//alert("currDay"+currDay);
			var currMonth = currDate.substring(3,5);
			// alert(currMonth);
			var currYear  = currDate.substring(6,10);
			// alert(currYear);
			var seprator = formatD.substring(2, 3);
			if(seprator=='-')
			  seprator = '/';
			else
			  seprator = '/';
		    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
		    //alert("UpdateDateStr"+UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);
			//alert(" before if CurrentDate"+CurrentDate);
			//CurrentDate.setDate(CurrentDate.getDate());			
			var z=x*30;
			if(z==y)
			{
				//alert("first");
				CurrentDate.setMonth(CurrentDate.getMonth()+x);
				//alert("CurrentDate under condition "+CurrentDate);				
			}
			else 
			{
				//var day = (x-1)*30;
				 if(daysBasis=='A')
				{
					var day = (x-1)*30.42;
					day=Math.floor(day);
					//document.getElementById("tenureInDay").value=y.toFixed(0); 
				}
				else
				{
					var day = (x-1)*30;
					//document.getElementById("tenureInDay").value=y;
				}
				//alert("day "+day);
				var day1 = y-day;
				//alert("day1 "+day1);
				CurrentDate.setMonth(CurrentDate.getMonth()+(x-1));
				//alert("after month add"+CurrentDate);
				//alert("day before calc "+CurrentDate.getDate());
				//var compareDate=CurrentDate;
				var compareDate = new Date(CurrentDate);
				//alert("compareDate "+compareDate);
				CurrentDate.setDate(CurrentDate.getDate()+day1);
				//alert(day1+" after day add in compareDate "+CurrentDate);
				var nextDueDay   = nextDueDate.substring(0,2);
				//alert("nextDueDay "+nextDueDay);
				var nextDueMonth = nextDueDate.substring(3,5);
				//alert("nextDueMonth "+nextDueMonth);
				var nextDueYear = nextDueDate.substring(6,10);
				//alert("nextDueYear "+nextDueYear);
		
				//var res = new Date(nextDueDate.getFullYear(), nextDueDate.getMonth()+1, 0);
				var result = new Date(nextDueYear, nextDueMonth+1, 0);
				var res=result.getDate();
				//alert("res "+res);
				//res = res.getDate();
				var nextDay= nextDueDate.substring(0,2);
				//alert("nextDay1 "+nextDay);
				compareDate=new Date (compareDate.setDate(nextDay));
				//alert("nextDay2 "+compareDate.getDate());
				var comparelastDay = new Date(compareDate.getFullYear(), compareDate.getMonth() + 1, 0);
				//alert("last day "+comparelastDay);
				if (nextDay==res)
				{
				//campare day ka last day set 
					compareDate=new Date (compareDate.setDate(comparelastDay));	
					//alert("condition1 "+compareDate.getDate());					
				}
				else
				{
					//set cycle date in compare date 
					compareDate=new Date (compareDate.setDate(dueDay));	
					//alert("codition2 "+compareDate.getDate());	
				}
				if (compareDate>CurrentDate)
				{	
					
					document.getElementById('requestedLoanTenure').value=x;// -1;
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
					
					//alert("tenure : "+x);
					//alert("fre    : "+fre);
					var noOfInstallDF=parseInt(x/fre);
					//alert("noOfInstallDF  : "+noOfInstallDF);
					
					var noOfInstall = document.getElementById("noOfInstall").value; //= "noOfInstallDF";
					//alert("before change noOfInstall    : "+noOfInstall);
					document.getElementById("noOfInstall").value=x; //-1;
					//alert("after change noOfInstall    : "+noOfInstall);
					
				}
				
			}
			
			if(repayEffMonth == currMonth)
			{
				//alert("equal ");
				modMonth=CurrentDate.getMonth();//+1; //commented on 22-08-2018
				if(modMonth<=9)
				{
					modMonth="0"+modMonth;
				}
				modDay=CurrentDate.getDate();
				if(modDay<=9)
				{
					modDay="0"+modDay;
				}
				// alert("modMonth"+modMonth);
				ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
				// alert("first "+ModDateStr);
		  
				document.getElementById('maturityDate1').value=ModDateStr;	  
				//  document.getElementById('maturityDate').value=ModDateStr;		  
				document.getElementById('OldMaturityDate1').value=ModDateStr;
			}
			else
			{
				//alert("Not equal ");
				modMonth=CurrentDate.getMonth()-1;//+1; //commented on 22-08-2018
				if(modMonth<=9)
				{
					modMonth="0"+modMonth;
				}
				modDay=CurrentDate.getDate();
				if(modDay<=9)
				{
					modDay="0"+modDay;
				}
				// alert("modMonth"+modMonth);
				ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
				// alert("first "+ModDateStr);		  
				document.getElementById('maturityDate1').value=ModDateStr;	  
				//  document.getElementById('maturityDate').value=ModDateStr;		  
				document.getElementById('OldMaturityDate1').value=ModDateStr;
			} 		 
		}		
		else
		{
			var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
			var formatD=document.getElementById("formatD").value;
			//alert(x);
			var currDate =   document.getElementById('repayEffectiveDate').value;
			//var currDate =   document.getElementById('nextDueDate').value; //added on 21-08-2018
			//var currDate  = new Date(repayDate);
			 //alert(currDate);
			 var currDay   = currDate.substring(0,2);
			 //alert(currDay);
			 var currMonth = currDate.substring(3,5);
			 // alert(currMonth);
			 var currYear  = currDate.substring(6,10);
			 // alert(currYear);
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
			 // alert("second "+ModDateStr);
			  
			  document.getElementById('maturityDate1').value=ModDateStr;			 
			  document.getElementById('maturityDate').value=ModDateStr;
			  document.getElementById('OldMaturityDate1').value=ModDateStr;
		}
	}
}
//end here | Brijesh Pathak

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

function openPopForSectorDetails(val)
{
	
	var basePath=document.getElementById("contextPath").value;
	/*var dealNo=document.getElementById("dealNo").value;
	
	if(dealNo=='')
	{
		alert("Please Select Lead No.");
		return false;
	}
	else
	{*/
	
		 anotherWindows = new Array();
		    curWinAnother = 0;
		
		var url=basePath+"/loanProcessAction.do?method=openSectorType&val="+val;
		
		window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

		 return true;
	//}
}

function saveSectorTypeDetails(){
	
	DisButClass.prototype.DisButMethod();
	var agriDocs=document.getElementById("agriDocs");
	var dealId=document.getElementById("dealId").value;
	if(trim(agriDocs.value) == ''){
		 var msg= '';
		 if(trim(agriDocs.value) == '')
	 			msg += '* Please select agri documents.\n';
		 		
		if(msg.match("documents")){
			agriDocs.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 
	 }
	
	 if(dealId!=''){
 
		 document.getElementById("SectorTypeDetailsForm").action="loanProcessAction.do?method=saveSectorTypeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("SectorTypeDetailsForm").submit();

	return true;
}
	 else
	 {
		 alert('Please select deal No. first.');
		DisButClass.prototype.EnbButMethod();
		window.close();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}
//Richa changes starts
function calcMonth(){
	   var tenureInDay= document.getElementById("tenureInDay").value;
var dayBasis=document.getElementById("daysBasis").value;
	  // alert("daysBasis"+daysBasis);
	   
	   	if(tenureInDay==''){
	   		tenureInDay=0;
	   	}
	   	if(tenureInDay!='')
	   	{
	   		tenureInDay=parseInt(tenureInDay);
	   		//alert("parseTenure:-----"+parseTenure);
	   	}

if(dayBasis=="A"){
	   var month=tenureInDay/30.42;
	   }
else{
	   var month=tenureInDay/30;
}
	  // var monthVal = month.toFixed(2);
	   var monthVal= Math.ceil(month);
	   	document.getElementById("requestedLoanTenure").value=monthVal;
	 	document.getElementById("noOfInstall").value=monthVal;
	   	
	  
}
function editNoInstal()
{
	var installmentType= document.getElementById("installmentType").value;
	var frequency= document.getElementById("frequency").value;
	if(installmentType=='I')
		{
		document.getElementById("noOfInstall").removeAttribute("readonly",true);
		document.getElementById("noOfInstall").value=document.getElementById("requestedLoanTenure").value;
		}
	else
{
		document.getElementById("noOfInstall").setAttribute("readonly",true);
		}
	if(installmentType=='S')
		{
		document.getElementById("interestCalculationMethod").removeAttribute("disabled",true);
		document.getElementById("interestFrequency").removeAttribute("disabled",true);
		document.getElementById("interestDueDateHeader1").style.display='';
		document.getElementById("interestDueDateHeader2").style.display='';
		document.getElementById("SeparateInterestDate").style.display='';
		
		}
	else
		{
		document.getElementById("interestCalculationMethod").value='D';
		document.getElementById("interestCalculationMethod").setAttribute("disabled",true);
		document.getElementById("interestFrequency").value=frequency;
		document.getElementById("interestFrequency").setAttribute("disabled",true);
		document.getElementById("interestDueDateHeader1").style.display="none";
		document.getElementById("interestDueDateHeader2").style.display="none";
		document.getElementById("SeparateInterestDate").style.display='none';
		}
		
}

//Richa changes ends
function validateIntCompoundingFrequency()
{
	
	var installmentType= document.getElementById("installmentType").value;
			if(installmentType=='E'|| installmentType=='G'  ||  installmentType=='P' ||installmentType=='Q' )
			{
	var interestCompoundingFrequency= document.getElementById("interestCompoundingFrequency").value;
	var frequency= document.getElementById("frequency").value;
			if((frequency=='M' && (interestCompoundingFrequency=='M' || interestCompoundingFrequency=='')) || (frequency=='B' && (interestCompoundingFrequency=='B' ||interestCompoundingFrequency=='M' || interestCompoundingFrequency=='')) || 
				(frequency=='Q' && (interestCompoundingFrequency=='Q' ||interestCompoundingFrequency=='M' || interestCompoundingFrequency=='B' || interestCompoundingFrequency=='')) || 
				(frequency=='H' && (interestCompoundingFrequency=='H'||interestCompoundingFrequency=='M'||interestCompoundingFrequency=='B'|| interestCompoundingFrequency=='Q' || interestCompoundingFrequency==''))
			||(frequency=='Y' && (interestCompoundingFrequency=='Y'||interestCompoundingFrequency=='M'||interestCompoundingFrequency=='B'|| interestCompoundingFrequency=='Q' || interestCompoundingFrequency=='H' || interestCompoundingFrequency==''))		
	)
		return true;
	alert('Interest Compounding Frequency can not be grater than Loan Frequency');
			document.getElementById("interestCompoundingFrequency").value='';
	return false;
}
}
//Adding funtion AJAY
function datevalidate()
{
	var businessDate= document.getElementById("businessdate").value;
	var interestDueDate= document.getElementById("interestDueDate").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var iMonth = interestDueDate.substring(3,5); //months from 1-12
	var iDay = interestDueDate.substring(0,2);
	var iYear = interestDueDate.substring(6);
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var dateObj =  new Date(iYear,iMonth,iDay);
	// beging of day validation 
	
				var cycleDate= document.getElementById("cycleDate").value;
if(interestCalculationMethod=='D')
	{
				if(cycleDate=='')
		{
					alert('First select  Due Day ');
					document.getElementById("interestDueDate").value="";
		return false;
	}
	
				if(cycleDate!=parseFloat(iDay)  )
		{
				//	alert('iMonth::::'+iMonth);
				//	alert('iDay::::'+iDay);
				if((parseFloat(iMonth)==1 && parseFloat(iDay)!=31) ||
					(parseFloat(iMonth)==2 && (parseFloat(iDay)!=28 && parseFloat(iDay)!=29 )) ||	
					(parseFloat(iMonth)==3 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==4 && parseFloat(iDay)!=30) ||
					(parseFloat(iMonth)==5 && parseFloat(iDay)!=31) || 					
					(parseFloat(iMonth)==6 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==7 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==8 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==9 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==10 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==11 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==12 && parseFloat(iDay)!=31)  )
							{
			alert('Interest Due Date Must be same as Due Day ');
					document.getElementById("interestDueDate").value="";
			return false;
		}
	}
			
	}
	return true;
	
		
}


function dateValidate(interestMedCal,interestFrequency,interestDueDate,businessDate)
{
	var iMonth = interestDueDate.substring(3,5); //months from 1-12
	var iDay = interestDueDate.substring(0,2);
	var iYear = interestDueDate.substring(6);
	
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var div = '';
	if(interestFrequency=='Q')
	div=3;
	else if(interestFrequency=='H')
	div=6;
	else if(interestFrequency=='Y')
	div=12;
	
	 if(interestMedCal=='F')
		{
			if((parseFloat(iYear)==parseFloat(bYear)) )
			{	
				if(parseFloat(bMonth)>=parseFloat(iMonth))
					{
						//alert(' Interest Due month must be  month beginning after  financial quarter');
					return false;
					}
			}
			if(parseFloat(bMonth)<parseFloat(iMonth))
				{
			
					//alert('iMonth  '+iMonth);
					var testMonth=parseFloat(iMonth)%parseFloat(div);
					if(parseFloat(testMonth)!='01')
								{
									//alert(' Interest Due month must be  month beginning after  financial quarter');
									return false;
		}
							
				}		
			if((parseFloat(iYear)>parseFloat(bYear)) )
		{
					
					var testMonth=parseFloat(iMonth)%parseFloat(div);
					if(parseFloat(testMonth)!='01')
			{
									//alert(' Interest Due month must be  month beginning after  financial quarter');
					return false;
					}
			
			
			}
		}
return true;			
}
//Ajay funtion end here	
function setInterestDueDate()
 {
	var formatD = document.getElementById("formatD").value;
	var businessDate = document.getElementById("businessdate").value;
	var repayEffectiveDate = document.getElementById("repayEffectiveDate").value;
	var interestCalculationMethod = document.getElementById("interestCalculationMethod").value;
	var interestFrequency = document.getElementById("interestFrequency").value;
	var bMonth = businessDate.substring(3, 5); // months from 1-12
	var bDay = businessDate.substring(0, 2);
	var bYear = businessDate.substring(6);
	var iMonth = '';
	var iDay = '';
	var iYear = '';
	var rMonth = repayEffectiveDate.substring(3, 5); // months from 1-12
	var rDay = repayEffectiveDate.substring(0, 2);
	var rYear = repayEffectiveDate.substring(6);
	var div = '';
	if (interestFrequency == 'M')
		div = 1;
	if (interestFrequency == 'B')
		div = 2;
	if (interestFrequency == 'Q')
		div = 3;
	else if (interestFrequency == 'H')
		div = 6;
	else if (interestFrequency == 'Y')
		div = 12;
	var count = 0;
	// var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
	// var dt3=getDateObject(businessDate,formatD.substring(2, 3));
	// 
	// if(dt1<dt3)
	// {
	// document.getElementById("interestDueDate").value='';
	// }
	// else
	if (interestCalculationMethod == 'E') {

		iDay = '01';
		iMonth = parseFloat(rMonth) + div;
		iYear = parseFloat(rYear);
		// alert(parseFloat(rYear));
		if (iMonth > '12') {
			var quotient = parseInt(iMonth / 12);
			var rem = iMonth % 12;
			if (rem == 0) {

				iMonth = '1';
			}

			else {
				iMonth = rem;

			}
			iYear = iYear + quotient;
		}
		var iMonthStr = '';
		if (iMonth <= 9) {
			iMonthStr = iMonth.toString();
			iMonth = (0).toString() + iMonthStr;
			// alert('iMonth......'+iMonth);
		}

		var iDD = iDay + "-" + iMonth + "-" + iYear;

		document.getElementById("interestDueDate").value = iDD;

	}

	else if (interestCalculationMethod == 'F') {

		iDay = '01';
		if (interestFrequency == 'Q') {
			if (parseFloat(rMonth) < '04') {
				iMonth = '4';
				iYear = parseFloat(rYear);
				// alert(iMonth);
			} else if (parseFloat(rMonth) < '07') {
				iMonth = '7';
				iYear = parseFloat(rYear);
				// alert(iMonth);
			} else if (parseFloat(rMonth) < '10') {
				iMonth = '10';
				iYear = parseFloat(rYear);
				// alert(iMonth);
			} else if (parseFloat(rMonth) <= '12') {
				iMonth = '1';
				iYear = parseFloat(rYear) + 1;
				// alert(iMonth);
			}
		} else if (interestFrequency == 'H') {
			if (parseFloat(rMonth) < '04') {
				iMonth = '4';

				iYear = parseFloat(rYear);
				// alert(iMonth);
			} else if (parseFloat(rMonth) < '10') {
				iMonth = '10';
				iYear = parseFloat(rYear);
				// alert(iMonth);
			} else if (parseFloat(rMonth) <= '12') {
				iMonth = '4';
				iYear = parseFloat(rYear) + 1;
				// alert(iMonth);
			}
		} else if (interestFrequency == 'Y') {
			iMonth = '4';

			iYear = parseFloat(rYear) + 1;
			// alert(iMonth);
		} else if (interestFrequency == 'M') {
			iMonth = parseFloat(rMonth) + 1;
			iYear = parseFloat(rYear);
			if (iMonth > '12') {
				var quotient = parseInt(iMonth / 12);
				var rem = iMonth % 12;
				if (rem == 0) {
					iMonth = '1';
				} else {
					iMonth = rem;
				}

				iYear = iYear + quotient;
			}

		} else if (interestFrequency == 'B') {
			iMonth = parseFloat(rMonth) + 2;
			iYear = parseFloat(rYear);
			if (iMonth > '12') {

				var quotient = parseInt(iMonth / 12);
				var rem = iMonth % 12;
				if (rem == 0) {

					iMonth = '1';
				} else {

					iMonth = rem;
				}

				iYear = iYear + quotient;
			}
		}
		var iMonthStr = '';
		if (iMonth <= 9) {
			iMonthStr = iMonth.toString();
			iMonth = (0).toString() + iMonthStr;
			// alert('iMonth......'+iMonth);
		}

		var iDD = iDay + "-" + iMonth + "-" + iYear;
		// alert(iDD);
		document.getElementById("interestDueDate").value = iDD;
	}

}

function showInterestDueDate(flag)
{
	// alert('1');
	var installmentType=document.getElementById("installmentType").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	
	
	if(interestCalculationMethod=='D' && installmentType=='S')
	{
		//alert('2');
		document.getElementById("interestDueDate").style.display="";
		document.getElementById("interestDueDate").parentNode.childNodes[2].style.display="";
		if(flag)
		{
			//alert('3');
		document.getElementById("interestDueDate").value="";
		}
		//document.getElementById("interestDueDate").style.display="none";
		//document.getElementById("interestDueDate").parentNode.childNodes[2].style.display="none";
return true;			
}
	else if((interestCalculationMethod=='E'|| interestCalculationMethod =='F') && installmentType=='S')
	{
		//alert('4');
		//document.getElementById("interestDueDate").parentNode.childNodes[2].style.display="none";
		if(flag)
		{	
		document.getElementById("interestDueDate").value="";
		}
		//document.getElementById("interestDueDate").style.display="none";
		//document.getElementById("interestDueDate").style.display="";
		return true;
	}
	
}
//add by AJay for interestFrequency
function validateInterestFrequency()
{
	
	var installmentType= document.getElementById("installmentType").value;
			if(installmentType=='S' )
			{
			var interestCompoundingFrequency= document.getElementById("interestCompoundingFrequency").value;
			var interestFrequency= document.getElementById("interestFrequency").value;
			if(
			(interestCompoundingFrequency=='Y' && (interestFrequency=='Y'))||
			(interestCompoundingFrequency=='H' && (interestFrequency=='Y' || interestFrequency=='H')) ||
			(interestCompoundingFrequency=='Q' && (interestFrequency=='Y' ||interestFrequency=='H' || interestFrequency=='Q')) || 
			(interestCompoundingFrequency=='B' && (interestFrequency=='Y' ||interestFrequency=='H' || interestFrequency=='Q' || interestFrequency=='B')) || 
			(interestCompoundingFrequency=='M' && (interestFrequency=='Y'||interestFrequency=='H'||interestFrequency=='Q'|| interestFrequency=='B' || interestFrequency=='M'))||
			(interestCompoundingFrequency=='' && (interestFrequency=='Y'||interestFrequency=='H'||interestFrequency=='Q'|| interestFrequency=='B' || interestFrequency=='M' ))		
			)	
			return true;
			alert(' Interest Frequency can not be smaller than  Interest Compounding Frequency');
			document.getElementById("interestFrequency").value='';
			return false;
			}
}
//end by ajay 
//added for valid Interest Due Date by AJAY
function validateInterestDueDate()
{
	//alert('.......................');	
	var formatD=document.getElementById("formatD").value;
	var businessDate= document.getElementById("businessdate").value;
	var repayEffectiveDate= document.getElementById("repayEffectiveDate").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var interestDueDate= document.getElementById("interestDueDate").value;
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var iMonth=interestDueDate.substring(3,5);
	var iDay=interestDueDate.substring(0,2);
	var iYear=interestDueDate.substring(6);
	var rMonth = repayEffectiveDate.substring(3,5); //months from 1-12
	var rDay = repayEffectiveDate.substring(0,2);
	var rYear = repayEffectiveDate.substring(6);
	var msg="";
	var flag='';
		var iDD=  iDay+"-"+iMonth+"-"+iYear;
	//	alert('...interestDueDate...'+iDD+'   '+interestCalculationMethod);
		if(interestCalculationMethod!='D')
		{	
	if(interestCalculationMethod=='B' || interestCalculationMethod=='G')
	{
					if(parseFloat(iDay)>'1')
					{
						alert("Day must be First day of month.");
						var interestDueDate= document.getElementById("interestDueDate").value='';
						return false;
					}
	}
	if(interestCalculationMethod=='E' || interestCalculationMethod=='F')
	{
						if((parseFloat(iMonth)==1 && parseFloat(iDay)!=31) ||
					(parseFloat(iMonth)==2 && (parseFloat(iDay)!=28 && parseFloat(iDay)!=29 )) ||	
					(parseFloat(iMonth)==3 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==4 && parseFloat(iDay)!=30) ||
					(parseFloat(iMonth)==5 && parseFloat(iDay)!=31) || 					
					(parseFloat(iMonth)==6 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==7 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==8 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==9 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==10 && parseFloat(iDay)!=31) || 
					(parseFloat(iMonth)==11 && parseFloat(iDay)!=30) || 
					(parseFloat(iMonth)==12 && parseFloat(iDay)!=31)  )
					{
						alert("Day must be Last day of month.");
						var interestDueDate= document.getElementById("interestDueDate").value='';
						return false;
					}
	}
					if(parseFloat(iYear)<parseFloat(rYear))
					{
						alert("Interest Due Date can not be lesser then repay Effective Date.");
						var interestDueDate= document.getElementById("interestDueDate").value='';
						return false;
					}
				
				
				if(interestCalculationMethod=='F')
				{
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='M')
					{
						/*if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}*/
						if( parseFloat(iDay)<= parseFloat(rDay))
						{
							alert('Date should be Greater than Repayment Effective Date ');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
						
							
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='B')
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07' || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
									
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
							
					}
					//for idd greater year then red
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='B' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07'   || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
							if(parseFloat(iMonth)!='04')
							{
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
							}
							
					}
				}	
				if(interestCalculationMethod=='E')
				{
					if(parseFloat(iYear)==parseFloat(rYear))
					{
						/*if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}*/
						if( parseFloat(iDay)<= parseFloat(rDay) && parseFloat(iMonth)<= parseFloat(rMonth) )
						{
							alert('Date should be Greater than Repayment Effective Date ');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
						
							
					}	
				}	
				return true;
			}
}

//End by AJAY
//Rohit Chnages Starts///
function showDueDay(){
	var calcMethod=document.getElementById("interestCalculationMethod").value;
//	alert("calcMethod::::"+calcMethod)
		document.getElementById("cycleDate").value='';
	if(calcMethod=='B' || calcMethod=='G' )
	{
		document.getElementById("cycleDate").value='1';
	}
	if(calcMethod=='E' || calcMethod=='F' )
	{
		document.getElementById("cycleDate").value='31';
	}
	return true;
	
}

//Rohit end
function showValues(){
	var sector=document.getElementById("sectorType").value;
	if(sector!="PSL"){
		document.getElementById("grossBlock").value="";
		document.getElementById("netBlock").value="";
		document.getElementById("gblock").style.display="none";
		document.getElementById("gblock1").style.display="none";
		document.getElementById("nblock").style.display="none";
		document.getElementById("nblock1").style.display="none";
		
	}else{
		document.getElementById("gblock").style.display="";
		document.getElementById("gblock1").style.display="";
		document.getElementById("nblock").style.display="";
		document.getElementById("nblock1").style.display="";
	}
	
}
function sumRequestedAmt()
{
	var requestedLoamt=document.getElementById("requestedLoamt").value;
	var requestedLoanAmount=document.getElementById("requestedLoanAmount").value;
	var insurancePremium=0;
	
	 insurancePremium=Math.round(document.getElementById("insurancePremium").value);
	
	

	var amount=0;
	
	var reqloanAmt=0.00;
	if(requestedLoanAmount!='')
	{
		reqloanAmt=removeComma(requestedLoanAmount);
	}
	
	
	//alert(Math.round(insurancePremium)+reqloanAmtNew);
	
	 var reqloanAmtNew=0.00;
	if(requestedLoamt!='')
	{
		reqloanAmtNew=removeComma(requestedLoamt);
	}
	
	//alert('reqloanAmt::'+reqloanAmt);
	//alert('insurancePremium::'+insurancePremium);
	amount= (insurancePremium + reqloanAmtNew);
	//alert("amandeep hi::"+amount);
	document.getElementById("requestedLoanAmount").value=amount;
	
	}
//brijesh start here ||28-08-2018
function setMaturityTemp(){
	var maturityDate=document.getElementById('maturityDate1').value;	 
	if(maturityDate!= null ||maturityDate!= "")
	{
		 document.getElementById('OldMaturityDate1').value=maturityDate;
	}
	
	//  document.getElementById('maturityDate').value=ModDateStr;		  
	//  document.getElementById('OldMaturityDate1').value=ModDateStr;

}
//end here ||28-08-2018
/*Ankita Start For Co-Landing*/

function getbusinessbutton()
{
	var businessType=document.getElementById("businessType").value;
	if(businessType=='CP')
		{
		document.getElementById("partner_mode").style.display="";
		}
	else
		document.getElementById("partner_mode").style.display="none";
}

function getbusinessType()
{
	
	var dealId = document.getElementById("dealId").value;
	var dealLoanIds = document.getElementById("dealLoanIds").value;
	//alert("dealId: "+dealId);
	//alert("dealLoanIds: "+dealLoanIds);
	
	if(dealId=="" && dealLoanIds == "")
		{
		alert("Please save the loan details first and then select it.");
		document.getElementById("businessType").value="";
		return false;
		}
	var basePath=document.getElementById("contextPath").value;
	var businessType=document.getElementById("businessType").value;
	var oldPartnerType=document.getElementById("oldPartnerType").value;
	
	/*if(businessType!=oldPartnerType){
		alert("Please Save Loan Detail First.");
		return false;
	}*/
	
	//alert("businessType : "+businessType);
	if(businessType=='CB'|| businessType=='CP')
		{
		 anotherWindows = new Array();
		    curWinAnother = 0;
		
		var url=basePath+"/loanDetailCPProcessAction.do?method=openPartnerDetails&dealId="+dealId+"&businessType="+businessType;
		
		window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

		 return true;
		}
	return true;
}
//pooja code starts
function getPartnerDtl(id)
{
	
	var contextPath= document.getElementById("contextPath").value;
	var loanId=document.getElementById("loanId").value; 
	document.getElementById("partnerTypeDetailsForm").action=contextPath+"/loanDetailCPProcessAction.do?method=getPartnerDetails&id="+id+"&loanId="+loanId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("partnerTypeDetailsForm").submit();
	
	}
function check()
{
 //   alert("check function");
	var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[zx].checked==false)
		{
			flag=0;
		}
		else
		{
			
			flag=1;
			break;
		}
		zx++;
	}
	return flag;
}
function deletePartnerDetail()
{
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
	    	if(confirm("Do you want to delete the Record(s) ?"))
	    	{	
			
	    		var contextPath= document.getElementById("contextPath").value;
				document.getElementById("partnerTypeDetailsForm").action=contextPath+"/loanDetailCPProcessAction.do?method=deletePartnerDetails";
			 	document.getElementById("processingImage").style.display = '';
				document.getElementById("partnerTypeDetailsForm").submit();
	    	}
	    	else
	    	{
	    		DisButClass.prototype.EnbButMethod();
	    	}
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
	
}
//pooja code end

function calculatePercentage(){
	var loanAmount=document.getElementById('loanAmount').value;
	var partnerPercentage=document.getElementById('partnerPercentage').value;
	var partnerName=document.getElementById('partnerName').value;
	//alert("partnerName"+partnerName)
	var msg="";
	if(partnerName==""){
 		if(partnerName=="")
 			msg=msg+"*Plaese Select Partener Name\n";
 		alert(msg);
 		if(msg.match("Plaese Select"))
			document.getElementById("partnerName").focus();
		return false;
 	}
	else if(partnerPercentage=="")
		{
		document.getElementById('partnerPercentage').value='';
		alert("Please Select partner Percentage.");
	return false;
		}
	partnerLoanAmt=(loanAmount*partnerPercentage)/100;
	document.getElementById('partnerAmount').value=partnerLoanAmt;
	if(partnerPercentage>100)
		{
		document.getElementById('partnerAmount').value='';
		document.getElementById('partnerPercentage').value='';
		alert("Partner Percentage should not be greater than 100.");
		return false;
		}
	/*else{
	document.getElementById('partnerPercentage').value=partnerPercentage.toFixed(2);
	}*/
	
}

//pooja code starts for partner details
function checkBusinessPartner()
{
	var servicingPartnerFlag = document.getElementById("servicingPartnerFlag");
	var leadPartnerFlag = document.getElementById("leadPartnerFlag");
	var servicingFlagValue  = "N";
	if((servicingPartnerFlag.checked==true) || (servicingPartnerFlag.checked==false && leadPartnerFlag.checked==false))
	{
		//document.getElementById('effectiveRate').readOnly = true;
		servicingFlagValue= "Y";
	}
	else 
		{
		var rateType=document.getElementById("rateType").value;
		if(leadPartnerFlag.checked==true && rateType=="E"){
		document.getElementById('effectiveRate').readOnly = false;	
		}
		else{
		document.getElementById('effectiveRate').readOnly = true;
		}
		}
	
}

function enableDisableField(){
	var leadPartnerFlag = document.getElementById("leadPartnerFlag");
	if(leadPartnerFlag.checked==true)
	{
  		document.getElementById('displayField').style.display='';
		var rateType=document.getElementById("rateType").value;
			if(rateType=="E"){
				document.getElementById("partnerRate").value="";
				document.getElementById("partnerRate").setAttribute("readOnly",true);
				document.getElementById("effectiveRate").removeAttribute("readOnly",true);
				
			}
			if(rateType=="F"){
				document.getElementById("effectiveRate").value="";
				document.getElementById("effectiveRate").setAttribute("readOnly",true);
				document.getElementById("partnerRate").removeAttribute("readOnly",true);
				
			}
	}
	else
	{
		//document.getElementById('effectiveRate').readOnly = true;
  		document.getElementById('displayField').style.display='none';
  		document.getElementById('rateType').value='';
  		document.getElementById('partnerRate').value='';
  		
  		
	}

	
}

function changeOnFlatOrEffective(){
	var leadPartnerFlag = document.getElementById("leadPartnerFlag");
	if(leadPartnerFlag.checked==true)
	{
		var rateType=document.getElementById("rateType").value;
		if(rateType=="E"){
			document.getElementById("partnerRate").value="";
			document.getElementById("partnerRate").setAttribute("readOnly",true);
			document.getElementById("effectiveRate").removeAttribute("readOnly",true);
			
		}
		if(rateType=="F"){
			document.getElementById("effectiveRate").value="";
			document.getElementById("effectiveRate").setAttribute("readOnly",true);
			document.getElementById("partnerRate").removeAttribute("readOnly",true);
			
		}
			}
		
  		
  		
	}

//pooja code end 


function savePartnerDetails(){
	//alert("savePartnerDetails");
	DisButClass.prototype.DisButMethod();
	var basePath=document.getElementById("contextPath").value;
	//var lbxpartnerId=document.getElementById("lbxpartnerId").value; 
	//alert("lbxpartnerId"+lbxpartnerId);
	var loanId=document.getElementById("loanId").value; 
	//alert("loanId"+loanId);
	var effectiveRate="";
	if(document.getElementById("effectiveRate")){
		effectiveRate=document.getElementById("effectiveRate").value;	
	}
	//alert("partnerRate"+partnerRate);
	var partnerPercentage=document.getElementById("partnerPercentage").value;
	var partnerAmount=document.getElementById("partnerAmount").value;
	var servicingPartnerFlag = document.getElementById("servicingPartnerFlag");
	var leadPartnerFlag = document.getElementById("leadPartnerFlag");
	var servicingFlagValue  = "N";
	var leadPartnerFlagValue  = "N";
	var rateType="";
	var partnerRate="";
	//alert("partnerAmount"+partnerAmount);
	
	//add by saorabh 
// 	var fpr = parseFloat(partnerRate); // fpr =parseFloatRate
	var fpa = parseFloat(partnerPercentage);//fpa= parseFloatAmount
	if(document.getElementById("rateType")==null || document.getElementById("rateType")==undefined){
		rateType="";	
	}
	else{
		rateType=document.getElementById("rateType").value;
	}
	if(document.getElementById("partnerRate")==null || document.getElementById("partnerRate")==undefined){
		partnerRate="";	
	}
	else{
		partnerRate=document.getElementById("partnerRate").value;
	}

	if(document.getElementById("effectiveRate")==null || document.getElementById("effectiveRate")==undefined){
		effectiveRate="0";	
	}
	else if(parseFloat(removeComma(document.getElementById("effectiveRate").value))>100.00 ||  parseFloat(removeComma(document.getElementById("effectiveRate").value))<0.00)
		{
		alert('Effective rate should be 0 to 100 ');
		document.getElementById("effectiveRate").value='';
		document.getElementById("effectiveRate").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	
	if(fpa>100.00 || fpa<0.00)
	{
		alert('Partner Percentage should be 0 to 100 ');
	document.getElementById("partnerPercentage").focus();
	DisButClass.prototype.EnbButMethod();
	return false;
	}
	if(servicingPartnerFlag.checked==true)
		{
		servicingFlagValue= "Y";
		}
	
	if(leadPartnerFlag.checked==true)
	{
		leadPartnerFlagValue= "Y";
	}
	else
	{
	rateType="";
	}
	//end by saorabh
	if(loanId =="" ||  (effectiveRate =="" && rateType!="F") || partnerPercentage ==""  || partnerAmount=="" || (rateType=="F"  && partnerRate==""))
		{
		/*if(lbxpartnerId =="")
			{
			alert("Please select Partner Name.");
			DisButClass.prototype.EnbButMethod();
			return false;
			}*/
		if(loanId =="")
		{
		alert("Please select Loan No.");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(effectiveRate =="" && rateType!="F")
		{
		alert("Please select Effective Rate.");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(partnerPercentage =="")
		{
		alert("Please select Amount.");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(partnerAmount =="")
		{
		alert("Please enter partner loan Amount.");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		if(leadPartnerFlagValue=="Y")
			{
			var rateType=document.getElementById("rateType").value;
			if(rateType =="")
			{
			alert("Please Select Rate Type.");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			
			if(rateType=="F"){
				if(partnerRate =="")
				{
				alert("Please Select Flat Rate.");
				DisButClass.prototype.EnbButMethod();
				return false;
				}	
			}
			}
		
		}
		else if(leadPartnerFlagValue=="Y" && partnerRate!="" && (parseFloat(removeComma(partnerRate))>100.00 ||  parseFloat(removeComma(partnerRate))<0.00))
				{
				alert('Flat rate should be 0 to 100 ');
				document.getElementById("partnerRate").value="";
				DisButClass.prototype.EnbButMethod();
				return false;
				}
		else
		{
		document.getElementById("partnerTypeDetailsForm").action="loanDetailCPProcessAction.do?method=savePartnerDetails&servicingPartnerFlag="+servicingFlagValue+"&leadPartnerFlag="+leadPartnerFlagValue+"&loanId="+loanId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("partnerTypeDetailsForm").submit();
		return true;
		}
}
function checkPercentage(){
	var partnerName = document.getElementById("partnerName").value
	if(partnerName == "FB" ){
		document.getElementById("partnerPercentage").value  = "80";
	}else if(partnerName == "IND" ){
		document.getElementById("partnerPercentage").value  = "20";
	}
	calculatePercentage();
}
