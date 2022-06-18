function searchIndividualFinancialDetail()
	{
		var dealNo=document.getElementById("dealNo").value;
		var lbxProductID=document.getElementById("product").value;
		var customername=document.getElementById("customername").value;
		var lbxscheme=document.getElementById("scheme").value;
			
		if(dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
		{
			if(customername!='' && trim(customername).length<3)
			{
				alert("Please enter atleast three character");
				return false;
			}
			
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("individualFinancialAnalysisSearchForm").action=sourcepath+"/individualFinancialAnalysisSearchAction.do?method=individualFinancialAnalysisSearch";
			document.getElementById("individualFinancialAnalysisSearchForm").submit();
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	}

	

	function saveIndividualFinancialIncome()
	{
		var monthComma=document.getElementById('monthlyIncome').value;
		var yearComma=document.getElementById('annuallyIncome').value;
		var remarks=document.getElementById('remarks').value;
		var reloadFlag=document.getElementById('reloadFlag').value;
		/*if(reloadFlag!='Y')
			{
			alert('Please Reload First');
			return false;
			}*/
//		alert("monthComma  : "+monthComma);
//		alert("yearComma  : "+yearComma);
		if(monthComma=="")
		{
			monthComma="0";
			//document.getElementById('month1').value="0.00";
		}
		if(yearComma=="")
		{
			yearComma="0";	
			//document.getElementById('year1').value="0.00";
		}
		var month=removeComma(monthComma);
		var year=removeComma(yearComma);
//		alert("month  : "+month);
//		alert("year  : "+year);
		
		//Sarvesh Added This Code Started
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//Sarvesh Added This Code Ended
		else if(document.getElementById('monthlyIncome').value==""){
			alert("* Monthly Income is required");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(document.getElementById('annuallyIncome').value==""){
			alert("* Annually Income is required");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(document.getElementById('varificationMethod').value==""){
			alert("* Verification Method is required");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(remarks==""){
			alert("* Remark is required");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(parseFloat(year)<parseFloat(month)){
			alert("Annual Income should be greater than monthly income");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else if(remarks.length>1000){
			alert("Remarks can not have more than 1000 characters");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		if(document.getElementById('insertUpdateFlag').value =='I'){
			  document.getElementById("individualFinancialIncomeForm").action =contextPath+"/saveIndividualFinancialIncome.do?method=saveIndividualFinancialIncomeDetails";	
		}else{
			  document.getElementById("individualFinancialIncomeForm").action =contextPath+"/saveIndividualFinancialIncome.do?method=updateIndividualFinancialIncomeDetails";
		}
		   
		     document.getElementById("processingImage").style.display = '';
		     document.getElementById("individualFinancialIncomeForm").submit();
		}
	}
	
	function individualFinancialAnalysisForward()
	{
		DisButClass.prototype.DisButMethod();
//		var dealId = document.getElementById('dealId').value ;
//		if(dealId!='')
//		{
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("individualFinancialIncomeForm").action = sourcepath+"/forwardIndividualFinancialAnalysis.do";
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("individualFinancialIncomeForm").submit();
/*		}
		else
		{
			alert("You can't move without saving before Financial  Analysis Details.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		
	}
	
	function saveIndividualObligation()
	{
		var contextPath = document.getElementById('contextPath').value;
		DisButClass.prototype.DisButMethod();
		if(validateObligationDynaValidatorForm(document.getElementById("obligationForm")))
		{
			document.getElementById("obligationForm").action = contextPath+"/saveIndividualObligationDetail.do?method=saveIndividualObligationDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("obligationForm").submit();
			return true;
		}
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	function updateIndividualObligation(id)
	{
		var contextPath = document.getElementById('contextPath').value;
		DisButClass.prototype.DisButMethod();
		if(validateObligationDynaValidatorForm(document.getElementById("obligationForm")))
		{
		document.getElementById("obligationForm").action=contextPath+"/saveIndividualObligationDetail.do?method=updateIndividualObligationDetail&obligationId="+id;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("obligationForm").submit();
		return true;
		}
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	function IndividualObligatoinClear()
	{
		document.getElementById("institutionName").value='';
		document.getElementById("emiAmount").value='';
		document.getElementById("tenure").value='';
		document.getElementById("maturityDate").value='';
		document.getElementById("purpose").value='';
		document.getElementById("outstandingLoanAmount").value='';
		
	}
	
	function getIndividualObligation(id)
	{
		
		var contextPath = document.getElementById('contextPath').value;
		document.getElementById("obligationForm").action=contextPath+"/getIndividualObligationDetails.do?method=getIndividualObligationDetails&obligationId="+id;
	 	document.getElementById("obligationForm").submit();
	}
	
	function deleteIndividualObligation()
	{
		DisButClass.prototype.DisButMethod();
		var contextPath = document.getElementById('contextPath').value;
		    if(check())
		    {
		    	
				document.getElementById("obligationForm").action=contextPath+"/deleteIndividualObligation.do?method=deleteIndividualObligationDetails";
				document.getElementById("processingImage").style.display = '';
			 	document.getElementById("obligationForm").submit();
		    }
		    else
		    {
		    	alert("Please Select atleast one!!!");
		    	DisButClass.prototype.EnbButMethod();
		    	return false;
		    }
	}
	
	function editIndividualFinancialAnalysis(id,no,custName)
	{
		var sourcepath=document.getElementById("contextPath").value;
		document.location.href="editIndividualFinancialAnalysis.do?method=openEditIndividualFinancialAnalysis&dealId="+id+"&dealNo="+no+"&customerName="+custName;
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
	//Code By Arun For Individual Bank Account Analysis
	function saveBankAccountAnalysisIndividual()
	{	
		//alert("ok");
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		var dealNO=document.getElementById("dealNo").value;	
		var lbxDealNo=document.getElementById("lbxDealNo").value;	
		var bankName=document.getElementById("bankName").value;
		var lbxBankID=document.getElementById("lbxBankID").value;	
		var bankBranch=document.getElementById("bankBranch").value;
		var lbxBranchID=document.getElementById("lbxBranchID").value;	
		var accountType=document.getElementById("accountType").value;	
		var accountNo=document.getElementById("accountNo").value;
		var credit=document.getElementById("credit").value;
		var debit=document.getElementById("debit").value;
		var chequeBouncing=document.getElementById("chequeBouncing").value;
		var limitObligation=document.getElementById("limitObligation").value;
		var customerName=document.getElementById("customerName").value;
		var remarks=document.getElementById("remarks").value;
		
		
		 if(dealNO=="" ||  lbxDealNo=="" || bankName=="" || lbxBankID=="" ||bankBranch=="" ||lbxBranchID=="" || accountType=="" ||accountNo=="" ||credit=="" ||debit=="" || customerName=="" )
	     { 
			 var c = "";
			 if(dealNO=="" ||lbxDealNo=="")
			c="* Deal No is required.\n"
			if(customerName=="")
			c+="* Customer Name is required.\n"
			if(bankName=="" || lbxBankID=="")
			c+="* Bank Name is required.\n"
			if(bankBranch=="" ||lbxBranchID=="")
			c+="* Bank Branch Name is required.\n"
			if(accountType=="")
			c+="* Account Type is required.\n"
			if(accountNo=="")
			c+="* Account Number is required.\n"
			if(credit=="")
			c+="* Total Credit is required.\n"
			if(debit=="")
			c+="* Total debit is required.\n"
			
			
			alert(c);
			 if(debit=="")
				 document.getElementById("debit").focus();
			 if(credit=="")
				 document.getElementById("credit").focus();
			 if(accountNo=="")
				 document.getElementById("accountNo").focus();
			 if(accountType=="")
				 document.getElementById("accountType").focus();
			 if(bankBranch=="" ||lbxBranchID=="")
				 document.getElementById("bankBrnchButton").focus();
			 if(bankName=="" || lbxBankID=="")
				 document.getElementById("bankButton").focus();
			 if(dealNO=="" ||lbxDealNo==""){			 
				if(document.getElementById("dealButton") != null || document.getElementById("dealButton") == ""){
				 document.getElementById("dealButton").focus();
				}
			 }
			 
			 DisButClass.prototype.EnbButMethod();
			 return false;
		  }	
		
		 else if(accountType==1 && (limitObligation=="" || limitObligation==null )){
			 alert("* Limit Obligation is required.");
			 document.getElementById("limitObligation").focus();
			 DisButClass.prototype.EnbButMethod();
			 return false;
			 
		 }else if(remarks!=""&&remarks.length>1000){
				alert("Remarks can not have more than 1000 characters");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		 else
		 {
			
			 var highestBalance=document.getElementById("highestBalance").value;	
			 var lowestBalance=document.getElementById("lowestBalance").value;	
			 //alert("highestBalance   :  "+highestBalance);
			 //alert("lowestBalance    :  "+lowestBalance);
			 if((highestBalance=='' && lowestBalance !='')||(highestBalance!='' && lowestBalance ==''))
			 {
				 alert("Please enter Highest Balance and Lowest Balance Simultaneously.");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(highestBalance!='' && lowestBalance !='')
			 {
				var hBal=parseFloat(removeComma(highestBalance));
				var lBal=parseFloat(removeComma(lowestBalance));
				//alert("highestBalance   :  "+hBal);
				//alert("lowestBalance    :  "+lBal);
				if(lBal>hBal)
				{
					alert("Lowest Balance can't be greater than Highest Balance.");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			 }
			
		     //document.getElementById("bankAccountAnalysisForm").action =contextPath+"/bankAccountAnalysisAction.do?method=saveBankAccountAnalysisDetails";
			 document.getElementById("bankAccountAnalysisForm").action =contextPath+"/individualFinancialBankAccAnalysisAction.do?method=saveBankAccountAnalysisDetails";
		     document.getElementById("processingImage").style.display = '';
		     document.getElementById("bankAccountAnalysisForm").submit();
			 return true;
		 }
		 DisButClass.prototype.EnbButMethod();
		return false;
	}
	function updateBankAccountAnalysisIndividual(id)
	{
		DisButClass.prototype.DisButMethod();
		var contextPath = document.getElementById('contextPath').value;
		var dealNO=document.getElementById("dealNo").value;	
		var lbxDealNo=document.getElementById("lbxDealNo").value;	
		var bankName=document.getElementById("bankName").value;
		var lbxBankID=document.getElementById("lbxBankID").value;	
		var bankBranch=document.getElementById("bankBranch").value;
		var lbxBranchID=document.getElementById("lbxBranchID").value;	
		var accountType=document.getElementById("accountType").value;	
		var accountNo=document.getElementById("accountNo").value;
		var credit=document.getElementById("credit").value;
		var debit=document.getElementById("debit").value;
		var chequeBouncing=document.getElementById("chequeBouncing").value;
		var limitObligation=document.getElementById("limitObligation").value;
		var customerName=document.getElementById("customerName").value;
		var remarks=document.getElementById("remarks").value;
		
		
		 if(dealNO=="" ||  lbxDealNo=="" || bankName=="" || lbxBankID=="" ||bankBranch=="" ||lbxBranchID=="" || accountType=="" ||accountNo=="" ||credit=="" ||debit=="" )
	     { 
			 var c = "";
			 if(dealNO=="" ||lbxDealNo=="")
			c="* Deal No is required.\n"
			if(customerName=="")
			c+="* Customer Name is required.\n"
			if(bankName=="" || lbxBankID=="")
			c+="* Bank Name is required.\n"
			if(bankBranch=="" ||lbxBranchID=="")
			c+="* Bank Branch Name is required.\n"
			if(accountType=="")
			c+="* Account Type is required.\n"
			if(accountNo=="")
			c+="* Account Number is required.\n"
			if(credit=="")
			c+="* Total Credit is required.\n"
			if(debit=="")
			c+="* Total debit is required.\n"
			
			alert(c);
			 if(debit=="")
				 document.getElementById("debit").focus();
			 if(credit=="")
				 document.getElementById("credit").focus();
			 if(accountNo=="")
				 document.getElementById("accountNo").focus();
			 if(accountType=="")
				 document.getElementById("accountType").focus();
			 if(bankBranch=="" ||lbxBranchID=="")
				 document.getElementById("bankBrnchButton").focus();
			 if(bankName=="" || lbxBankID=="")
				 document.getElementById("bankButton").focus();
			 if(dealNO=="" ||lbxDealNo=="") 
				 document.getElementById("dealButton").focus();	
			 
			 DisButClass.prototype.EnbButMethod();
			 return false;
		  }		
		 else if(accountType==1 && (limitObligation=="" || limitObligation==null )){
			 alert("Please enter Limit Obligation Percentage.");
			 document.getElementById("limitObligation").focus();
			 DisButClass.prototype.EnbButMethod();
			 return false;
			 
		 }else if(remarks!=""&&remarks.length>1000){
				alert("Remarks can not have more than 1000 characters");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		 else
	     { 
			
			 var highestBalance=document.getElementById("highestBalance").value;	
			 var lowestBalance=document.getElementById("lowestBalance").value;	
			 //alert("highestBalance   :  "+highestBalance);
			 //alert("lowestBalance    :  "+lowestBalance);
			 if((highestBalance=='' && lowestBalance !='')||(highestBalance!='' && lowestBalance ==''))
			 {
				 alert("Please enter Highest Balance and Lowest Balance Simultaneously.");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(highestBalance!='' && lowestBalance !='')
			 {
				var hBal=parseFloat(removeComma(highestBalance));
				var lBal=parseFloat(removeComma(lowestBalance));
				//alert("highestBalance   :  "+hBal);
				//alert("lowestBalance    :  "+lBal);
				if(lBal>hBal)
				{
					alert("Lowest Balance can't be greater than Highest Balance.");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			 }
			
			 document.getElementById("bankAccountAnalysisForm").action=contextPath+"/individualFinancialBankAccAnalysisAction.do?method=updateBankAcAnalysis&bankAcAnalysisId="+id;
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("bankAccountAnalysisForm").submit();
			 return true;
	 	}
		 DisButClass.prototype.EnbButMethod();
	 	return false;
	}
	function getBankAcAnalysisIndividual(id)
	{
		var contextPath = document.getElementById('contextPath').value;
		document.getElementById("bankAccountAnalysisForm").action=contextPath+"/individualFinancialBankAccAnalysisAction.do?method=getBankAcAnalysis&bankAcAnalysisId="+id;
	 	document.getElementById("bankAccountAnalysisForm").submit();
	}


	function deleteBankAcAnalysis()
	{
			DisButClass.prototype.DisButMethod();
		    if(check())
		    {
		    	if(confirm("Are you sure to delete record ?"))
		    	{
					document.getElementById("bankAccountAnalysisForm").action="deleteBankAccountAnalysis.do?method=deleteBankAccountAnalysis";
				 	document.getElementById("processingImage").style.display = '';
					document.getElementById("bankAccountAnalysisForm").submit();
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


	function bankAccountAnalysisClearIndividual()
	{
		document.getElementById("dealNo").value='';
		document.getElementById("customerName").value='';
		document.getElementById("lbxDealNo").value='';
		document.getElementById("lbxBankID").value='';
		document.getElementById("bankName").value='';
		document.getElementById("lbxBranchID").value='';
		document.getElementById("bankBranch").value='';
		document.getElementById("accountType").value='';
		document.getElementById("accountNo").value='';
		document.getElementById("totalEMI").value='';
		document.getElementById("credit").value='';
		document.getElementById("debit").value='';
		document.getElementById("highestBalance").value='';
		document.getElementById("lowestBalance").value='';
		document.getElementById("checkBounceAmount").value='';
		document.getElementById("checkBounceFrequency").value='';
		document.getElementById("overLimitAmount").value='';
		document.getElementById("overLimitFrequency").value='';
		document.getElementById("month").value='';
		document.getElementById("year").value='';
		document.getElementById("chequeBouncing").value='';
		document.getElementById("limitObligation").value='';

	}
	function deleteIncomeDetails()
	{
			DisButClass.prototype.DisButMethod();
		    if(check())
		    {
		    	if(confirm("Are you sure to delete record ?"))
		    	{
					document.getElementById("bankAccountAnalysisForm").action="deleteBankAccountAnalysis.do?method=deleteBankAccountAnalysis";
				 	document.getElementById("processingImage").style.display = '';
					document.getElementById("bankAccountAnalysisForm").submit();
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
	function editIncomesDetails(financialId){
		var contextPath = document.getElementById('contextPath').value;
		var lbxDealNo=document.getElementById('lbxDealNo').value;
		document.getElementById("individualFinancialIncomeForm").action=contextPath+"/saveIndividualFinancialIncome.do?method=openEditIndividualFinancialIncomeDetails&financialId="+financialId+"&dealId="+lbxDealNo;
	 	document.getElementById("individualFinancialIncomeForm").submit();
	}
	function getCustomerName()
	{
		//alert("hi");
		 var customerType=document.getElementById('customerType').value;
		 var dealId=document.getElementById('lbxDealNo').value;
		// alert(customerType);
		// alert(dealId);
			 var contextPath=document.getElementById("contextPath").value;
			 var address = contextPath+"/saveIndividualFinancialIncome.do?method=getCustomerName";
			 var data = "customerType="+customerType+"&dealId="+dealId;
			 sendForGetCustomerName(address, data);
						 
	}
	
	function sendForGetCustomerName(address, data) {
		//alert("in sendForgetCustomerName id");
		var request = getRequestObject();
		request.onreadystatechange = function () {
			resultCustomerName(request);	
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
		
	}

	function resultCustomerName(request){
		
		//alert("in resultCustomerName id");
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			//alert("String:--->"+str);
			document.getElementById('customerNameDiv').innerHTML=trim(str);
			
		}
	}
	function getRequestObject() {
		  if (window.ActiveXObject) { 
			return(new ActiveXObject("Microsoft.XMLHTTP"));
		  } else if (window.XMLHttpRequest) {
			return(new XMLHttpRequest());
		  } else {
			return(null);
		  }
		}
	
	function reloadIndividualFinancialIncome()
	{
		var customerType=document.getElementById("customerType").value;
		var lbxCustomerId=document.getElementById("lbxCustomerId").value;
		var customerName=document.getElementById("customerName").value;
		var lbxCustomerRoleType=document.getElementById("lbxCustomerRoleType").value;
	
		if(customerType=='')
		{
			alert('Please Select  Applicant Type');
			return false;
		}
		else{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("individualFinancialIncomeForm").action=sourcepath+"/reloadIndividualFinancialAnalysis.do?method=reloadIndividualFinancialAnalysis&lbxCustomerId="+lbxCustomerId+"&customerName="+customerName+"&customerType="+customerType+"&lbxCustomerRoleType="+lbxCustomerRoleType;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("individualFinancialIncomeForm").submit();
		return true;
		}
	}