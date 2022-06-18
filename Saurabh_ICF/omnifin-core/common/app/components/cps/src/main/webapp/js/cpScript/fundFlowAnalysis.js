

function saveBankAccountAnalysis()
{	
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	/*var dealNO=document.getElementById("dealNo").value;	*/
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
		
	//Sarvesh Added This Code Started
	var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
	if(lbxCustomerId==""){
		alert("Customer Name is Required");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	//Sarvesh Added This Code Ended
	
	 if(/*dealNO=="" || */ lbxDealNo=="" || bankName=="" || lbxBankID=="" ||bankBranch=="" ||lbxBranchID=="" || accountType=="" ||accountNo=="" ||credit=="" ||debit=="" || customerName=="" )
     { 
		 var c = "";
		 if(/*dealNO=="" ||*/lbxDealNo=="")
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
		 /*if(dealNO=="" ||lbxDealNo==""){			 
			if(document.getElementById("dealButton") != null || document.getElementById("dealButton") == ""){
			 document.getElementById("dealButton").focus();
			}
		 }*/
		 
		 DisButClass.prototype.EnbButMethod();
		 return false;
	  }	
	
	 else if(accountType==1 && (limitObligation=="" || limitObligation==null )){
		 alert("* Limit Obligation is required.");
		 document.getElementById("limitObligation").focus();
		 DisButClass.prototype.EnbButMethod();
		 return false;
		 
	 }
	 else
	 {
		//code added by neeraj tripathi start
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
		 //code added by neeraj tripathi end
	     document.getElementById("bankAccountAnalysisForm").action =contextPath+"/bankAccountAnalysisAction.do?method=saveBankAccountAnalysisDetails";
	     document.getElementById("processingImage").style.display = '';
	     document.getElementById("bankAccountAnalysisForm").submit();
		 return true;
	 }
	 DisButClass.prototype.EnbButMethod();
	return false;
}

function bankAccountAnalysisClear()
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

function bankAccClearWithoutLoanCust()
{
	document.getElementById("customerType").value='';
	document.getElementById("customerName").value='';
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
}


function forwardFundFlowDetails(fwdMsg)
{
	//alert("neeraj");
	DisButClass.prototype.DisButMethod();
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var dealId = document.getElementById('dealId').value ;
   // alert("dealID  :  "+dealId);
	var table = document.getElementById("gridTable");	
 	var rowCount = table.rows.length;
    // alert("rowCount:---"+rowCount);
 	
	if(dealId!=''&& rowCount>1){
		//alert("ok");
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("bankAccountAnalysisForm").action = sourcepath+"/forwardFundFlow.do";
	    document.getElementById("processingImage").style.display = '';
		document.getElementById("bankAccountAnalysisForm").submit();
	}
	else
	{
		alert("You can't move without saving before Bank Account Analysis Details.");
		DisButClass.prototype.EnbButMethod();
	}
	
}

function getBankAcAnalysis(id)
{
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("bankAccountAnalysisForm").action=contextPath+"/getBankAcAnalysisDetails.do?method=getBankAcAnalysis&bankAcAnalysisId="+id;
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

function editFundFlowAnalysis(id,no,custName)
{
//	document.location.href="fundFlowAnalysisNew.do&dealId="+id;
	custName = escape(custName);
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("fundFlowAnalysisSearchForm").action=sourcepath+"/editFlowAnalysis.do?dealId="+id+"&dealNo="+no+"&customerName="+custName;
	//alert(document.getElementById("fundFlowAnalysisSearchForm").action);
	document.getElementById("fundFlowAnalysisSearchForm").submit();
}



function updateBankAccountAnalysis(id)
{
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById('contextPath').value;
	/*var dealNO=document.getElementById("dealNo").value;	*/
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
	
	//Sarvesh Added This Code Started
	var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
	if(lbxCustomerId==""){
		alert("Customer Name is Required");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	//Sarvesh Added This Code Ended
	
	 if(/*dealNO=="" || */ lbxDealNo=="" || bankName=="" || lbxBankID=="" ||bankBranch=="" ||lbxBranchID=="" || accountType=="" ||accountNo=="" ||credit=="" ||debit=="" )
     { 
		 var c = "";
		 if(/*dealNO=="" ||*/lbxDealNo=="")
		c="* Deal No is required.\n"
		/*if(customerName=="")
		c+="* Customer Name is required.\n"*/
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
		 /*if(dealNO=="" ||lbxDealNo=="") 
			 document.getElementById("dealButton").focus();	*/
		 
		 DisButClass.prototype.EnbButMethod();
		 return false;
	  }		
	 else if(accountType==1 && (limitObligation=="" || limitObligation==null )){
		 alert("Please enter Limit Obligation Percentage.");
		 document.getElementById("limitObligation").focus();
		 DisButClass.prototype.EnbButMethod();
		 return false;
		 
	 }
	 else
     { 
		 //code added by neeraj tripathi start
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
		 //code added by neeraj tripathi end
		 document.getElementById("bankAccountAnalysisForm").action=contextPath+"/updateBankAcAnalysisDetails.do?method=updateBankAcAnalysis&bankAcAnalysisId="+id;
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("bankAccountAnalysisForm").submit();
		 return true;
 	}
	 DisButClass.prototype.EnbButMethod();
 	return false;
}
function removeComma(id)
{
    var str = id;
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++)
         stri = stri+arr[i];
    var amount = parseFloat(stri);
    return amount;
}

function searchFundFlowDetail()
{
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var username=document.getElementById("reportingToUserId").value;
		
	if(username!='' ||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("fundFlowAnalysisSearchForm").action=sourcepath+"/fundFlowAnalysisSearchAction.do?method=fundFlowAnalysisSearch&userId="+username;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fundFlowAnalysisSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function newfundFlow()
{ 
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	document.location.href="fundFlowAnalysisNew.do";
	DisButClass.prototype.EnbButMethod();
}

function saveSalesAnalysis()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value;
	/*var month=document.getElementById('month').value;*/
	var year=document.getElementById('year').value;

	var c="";
	if(year=="" )
	{
			c+="* Year is required.\n";
	
		alert(c);
		
		if(year=="" ){
			 document.getElementById("year").focus();
		
		
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	
	}	
	else{
		document.getElementById("salesAnalysisForm").action = contextPath+"/salesAnalysisAction.do?method=saveSalesAnalysisDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("salesAnalysisForm").submit();
		return true;
	}
	
	DisButClass.prototype.EnbButMethod();
	return false;
}


function updateSalesAnalysis(id)
{
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById('contextPath').value;
	var month=document.getElementById('month').value;
	var year=document.getElementById('year').value;
/*	var netsales=document.getElementById('netsales').value;*/
	var c="";
	if(month=="" ||year==""  )
	{
		if(month=="")
			c="* Month is required.\n";
		if(year=="" )
			c+="* Year is required.\n";
		/*if(netsales=="")
			c+="* Net Sales is required.\n";*/
		alert(c);
		if(month=="")
		 document.getElementById("month").focus();
		if(year=="" )
			 document.getElementById("year").focus();
	/*	if(netsales=="")
			 document.getElementById("netsales").focus();
		*/
		DisButClass.prototype.EnbButMethod();
		return false;
	}	
	else
	{
	document.getElementById("salesAnalysisForm").action=contextPath+"/updateSalesAnalysisDetail.do?method=updateSalesAnalysis&salesId="+id;
 	document.getElementById("processingImage").style.display = '';
	document.getElementById("salesAnalysisForm").submit();
 	return true;
	}
	
	DisButClass.prototype.EnbButMethod();
	return false;
}

function salesAnalysisClear()
{
	document.getElementById("year").value='';
	for(var i=1;i<=12;i++)
	{
		var netsales="netsales"+i;
		var avgsales="avgsales"+i;
		var interest="interest"+i;
		document.getElementById(netsales).value='';
		document.getElementById(avgsales).value='';
		document.getElementById(interest).value='';
	}
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

function getSalesAnalysis(id)
{
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("salesAnalysisForm").action=contextPath+"/getSalesAnalysisDetails.do?method=getSalesAnalysis&salesId="+id;
 	document.getElementById("salesAnalysisForm").submit();
}
function searchSalesAnalysis(id)
{

	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("salesAnalysisForm").action=contextPath+"/salesAnalysisBehindAction.do?method=salesAnalysisBehindDetail&year="+id;
 	document.getElementById("salesAnalysisForm").submit();
}

function deleteSalesAnalysis()
{
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("salesAnalysisForm").action="deleteSalesAnalysis.do?method=deleteSalesAnalysisDetails";
		 	document.getElementById("processingImage").style.display = '';
			document.getElementById("salesAnalysisForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}
function saveObligation()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value;
	var institutionName=document.getElementById('institutionName').value;
	var outstandingLoanAmount=document.getElementById('outstandingLoanAmount').value;
	var emiAmount=document.getElementById('emiAmount').value;
	var tenure=document.getElementById('tenure').value;
	var maturityDate=document.getElementById('maturityDate').value;
	var purpose=document.getElementById('purpose').value;
	var obligationType=document.getElementById('obligationType').value;	
	var financeAmount=document.getElementById('financeAmount').value;	
	var varificationMethod=document.getElementById('varificationMethod').value;	
	var flag = document.getElementById('LSD').value;
	var lastStatementDate;
	if(flag=='Y')
		lastStatementDate=document.getElementById('lastStatementDate1').value;
	else
		lastStatementDate=document.getElementById('lastStatementDate').value;	
	var ck_alphaNumericc = /^[A-Za-z0-9\_' .]{1,50}$/;
	var c="";
	
	var typeOfLoan = document.getElementById("typeOfLoan").value;	
	

	if( institutionName=="" || emiAmount=="" || tenure=="" || maturityDate=="" || lastStatementDate=="")
	{
		if(institutionName=="")
			c="* Institute Name is required.\n";		
		if( emiAmount=="")
			c+="* EMI Amount is required.\n";
		if(tenure=="")
			c+="* Remaining Tenure is required.\n";
		if(maturityDate=="")
			c+="* Maturity Date is required.\n";
		if(lastStatementDate=="")
			c+="* Last Statement Date is required.\n";
		alert(c);	
		
		if(maturityDate=="")
			document.getElementById("maturityDate").focus();		
		if(tenure=="")
			document.getElementById("tenure").focus();
		if( emiAmount=="")
			document.getElementById("emiAmount").focus();
		if(institutionName=="")
			document.getElementById("institutionName").focus();
		
		DisButClass.prototype.EnbButMethod();
		return false;
	
		
	}
	
	
	
	else if( institutionName=="" || lastStatementDate=="")
		{
			if(institutionName=="")
				c="* Institute Name is required.\n";		
			if(lastStatementDate=="")
				c+="* Last Statement Date is required.\n";
			alert(c);	
			
			if(institutionName=="")
				document.getElementById("institutionName").focus();
			if(lastStatementDate=="")
				document.getElementById("lastStatementDate").focus();
			
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	else if(institutionName!="" && !ck_alphaNumericc.test(institutionName))
	{
		alert("Institute Name is Invalid. ");
		document.getElementById("institutionName").focus();
		DisButClass.prototype.EnbButMethod();
		return false;	
	}	
	else
	{
		document.getElementById("obligationForm").action = contextPath+"/saveobligationDetail.do?method=saveObligationDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("obligationForm").submit();
		return true;
	}
	DisButClass.prototype.EnbButMethod();
	return false;
}
function updateObligation(id)
{
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById('contextPath').value;
	var institutionName=document.getElementById('institutionName').value;
	var outstandingLoanAmount=document.getElementById('outstandingLoanAmount').value;
	var emiAmount=document.getElementById('emiAmount').value;
	var tenure=document.getElementById('tenure').value;
	var maturityDate=document.getElementById('maturityDate').value;
	var purpose=document.getElementById('purpose').value;	
	var obligationType=document.getElementById('obligationType').value;	
	var financeAmount=document.getElementById('financeAmount').value;	
	var varificationMethod=document.getElementById('varificationMethod').value;	
	var lastStatementDate=document.getElementById('lastStatementDate1').value;
	var ck_alphaNumericc = /^[A-Za-z0-9\_' .]{1,50}$/;
	var c="";
	
	//Sarvesh Added This Code Started
	var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
	if(lbxCustomerId==""){
		alert("Customer Name is Required");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	//Sarvesh Added This Code Ended

	if( institutionName==""  || emiAmount=="" || tenure=="" || maturityDate=="" ||lastStatementDate=="")
	{
		if(institutionName=="")
			c="* Institute Name is required.\n";
		if( emiAmount=="")
			c+="* EMI Amount is required.\n";
		if(tenure=="")
			c+="* Remaining Tenure is required.\n";
		if(maturityDate=="")
			c+="* Maturity Date is required.\n";
		if(lastStatementDate=="")
			c+="* Last Statement Date is required.\n";
		alert(c);		
				
		if(maturityDate=="")
			document.getElementById("maturityDate").focus();		
		if(tenure=="")
			document.getElementById("tenure").focus();
		if( emiAmount=="")
			document.getElementById("emiAmount").focus();
		if(institutionName=="")
			document.getElementById("institutionName").focus();
		
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(institutionName!="" && !ck_alphaNumericc.test(institutionName)){
		alert("Institute Name is Invalid. ");
		document.getElementById("institutionName").focus();
		DisButClass.prototype.EnbButMethod();
		return false;	
	}	
	else
	{
	document.getElementById("obligationForm").action=contextPath+"/saveobligationDetail.do?method=updateObligationDetail&obligationId="+id;
 	document.getElementById("processingImage").style.display = '';
	document.getElementById("obligationForm").submit();
	return true;
	}
	DisButClass.prototype.EnbButMethod();
	return false;
}

function obligatoinClear()
{
	document.getElementById("institutionName").value='';
	document.getElementById("emiAmount").value='';
	document.getElementById("tenure").value='';
	document.getElementById("maturityDate").value='';
	document.getElementById("purpose").value='';
	document.getElementById("outstandingLoanAmount").value='';
	document.getElementById("lastMonth1").value='';
	document.getElementById("bounce1").value='';
	document.getElementById("lastMonth2").value='';
	document.getElementById("bounce2").value='';
	document.getElementById("lastMonth3").value='';
	document.getElementById("bounce3").value='';
	document.getElementById("lastMonth4").value='';
	document.getElementById("bounce4").value='';
	document.getElementById("lastMonth5").value='';
	document.getElementById("bounce5").value='';
	document.getElementById("lastMonth6").value='';
	document.getElementById("bounce6").value='';
	document.getElementById("lastMonth7").value='';
	document.getElementById("bounce7").value='';
	document.getElementById("lastMonth8").value='';
	document.getElementById("bounce8").value='';
	document.getElementById("lastMonth9").value='';
	document.getElementById("bounce9").value='';
	document.getElementById("lastMonth10").value='';
	document.getElementById("bounce10").value='';
	document.getElementById("lastMonth11").value='';
	document.getElementById("bounce11").value='';
	document.getElementById("lastMonth12").value='';
	document.getElementById("bounce12").value='';
}


function getObligation(id)
{
	
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("obligationForm").action=contextPath+"/getObligationDetails.do?method=getObligationDetails&obligationId="+id;
 	document.getElementById("obligationForm").submit();
}


function deleteObligation()
{
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("obligationForm").action="deleteObligation.do?method=deleteObligationDetails";
		 	document.getElementById("processingImage").style.display = '';
			document.getElementById("obligationForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}

function calculateInterest(){
	var currentval="";
	var previousval="";
	var interest=0;
	var i="";
	var netsales=document.getElementById("netsales");
	if(document.getElementById("netsales1").value == "")
	{
		var netsales1=document.getElementById("netsales1").value =0;
	}	
	else{
		var netsales1=removeComma(document.getElementById("netsales1").value);
	}
	if(document.getElementById("netsales2").value == "")
	{
		var netsales2=document.getElementById("netsales2").value =0;
	}	
	else{
		var netsales2=removeComma(document.getElementById("netsales2").value);
	}
	if(document.getElementById("netsales3").value == "")
	{
		var netsales3=document.getElementById("netsales3").value =0;
	}	
	else{
		var netsales3=removeComma(document.getElementById("netsales3").value);
	}
	if(document.getElementById("netsales4").value == "")
	{
		var netsales4=document.getElementById("netsales4").value =0;
	}	
	else{
		var netsales4=removeComma(document.getElementById("netsales4").value);
	}
if(document.getElementById("netsales5").value == "")
{
	var netsales5=document.getElementById("netsales5").value =0;
}	
else{
	var netsales5=removeComma(document.getElementById("netsales5").value);
	}
if(document.getElementById("netsales6").value == "")
{
	var netsales6=document.getElementById("netsales6").value =0;
}	
else{
	var netsales6=removeComma(document.getElementById("netsales6").value);
}
if(document.getElementById("netsales7").value == "")
{
	var netsales7=document.getElementById("netsales7").value =0;
}	
else{
	var netsales7=removeComma(document.getElementById("netsales7").value);
}
if(document.getElementById("netsales8").value == "")
{
	var netsales8=document.getElementById("netsales8").value =0;
}	
else{
	var netsales8=removeComma(document.getElementById("netsales8").value);
}
if(document.getElementById("netsales9").value == "")
{
	var netsales9=document.getElementById("netsales9").value =0;
}	
else{
	var netsales9=removeComma(document.getElementById("netsales9").value);
}
if(document.getElementById("netsales10").value == "")
{
	var netsales10=document.getElementById("netsales10").value =0;
}	
else{
	var netsales10=removeComma(document.getElementById("netsales10").value);
}if(document.getElementById("netsales11").value == "")
{
	var netsales11=document.getElementById("netsales11").value =0;
}	
else{
	var netsales11=removeComma(document.getElementById("netsales11").value);
}if(document.getElementById("netsales12").value == "")
{
	var netsales12=document.getElementById("netsales12").value =0;
}	
else{
	var netsales12=removeComma(document.getElementById("netsales12").value);
}
	
	var avgSales=(parseFloat(netsales1)+parseFloat(netsales2)+parseFloat(netsales3)+parseFloat(netsales4)+parseFloat(netsales5)+parseFloat(netsales6)+parseFloat(netsales7)+parseFloat(netsales8)+parseFloat(netsales9)+parseFloat(netsales10)+parseFloat(netsales11)+parseFloat(netsales12))/12;
//alert(avgSales);

		document.getElementById("avgsales1").value=avgSales;
		document.getElementById("avgsales2").value=avgSales;
		document.getElementById("avgsales3").value=avgSales;
		document.getElementById("avgsales4").value=avgSales;
		document.getElementById("avgsales5").value=avgSales;
		document.getElementById("avgsales6").value=avgSales;
		document.getElementById("avgsales7").value=avgSales;
		document.getElementById("avgsales8").value=avgSales;
		document.getElementById("avgsales9").value=avgSales;
		document.getElementById("avgsales10").value=avgSales;
		document.getElementById("avgsales11").value=avgSales;
		document.getElementById("avgsales12").value=avgSales;
	for(i=1;i<13;i++){
		//alert("i--------1---------->"+i);
		if(document.getElementById("netsales"+i).value == "")
			document.getElementById("netsales"+i).value =0;
		if(i>1){
			//alert("i--------2----->"+i);
			
				currentval=parseFloat(removeComma(document.getElementById("netsales"+i).value));
			
			//alert("currentval-->"+currentval);
			var m=i-1;
			//alert("m-->"+m);
			previousval=parseFloat(removeComma(document.getElementById("netsales"+m).value));
			//	alert("previousval-->"+previousval);
				interest=0;
			if(previousval==0){
				document.getElementById("interest"+i).value=currentval;
			}else{
				interest=((currentval-previousval)/previousval)*100;
				//alert("interest-->"+interest);
				document.getElementById("interest"+i).value=interest;
			}
				
		}else{
			document.getElementById("interest1").value=interest;
		}
	}
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


function checkRateInFundFlow(val)
{
	var rate = document.getElementById(val).value;
	 
// alert("Passed Value: "+rate);
if(rate=='')
	{
		rate=0;
		// alert("Please Enter the value");
	// return false;
		
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
		        document.getElementById(val).value="";
		// document.getElementById(val).focus;
		        return false;
	         }
	
}
function isAlphNumericKey(evt) 
{
    
var charCode = (evt.which) ? evt.which : event.keyCode;
//alert(charCode);
if (charCode > 31 && (charCode < 48 || charCode > 57)&& (charCode < 65 || charCode > 90)&& (charCode < 97 || charCode > 122)) {
	alert("Only Numeric and Alphanumeric are allowed here");
	return false;
}
return true;
}

function allowNegativeValue(val){
	var accountType=document.getElementById("accountType").value;
	var value=val;
	if(value!='' && value=='F'){
		document.getElementById("avgBankBalance").value="";
	}
	
	if(accountType=="1"){
		document.getElementById("forPositive").style.display="none";
		document.getElementById("forNegative").style.display="";		
	}
	else
	{
		document.getElementById("forPositive").style.display="";
		document.getElementById("forNegative").style.display="none";		
	}

}
function hideShowManFields()
{
	var typeOfLoan = document.getElementById("typeOfLoan").value;
	if(typeOfLoan=='CC/OD Loan')
		{
		 document.getElementById('hid').style.display="none";
		 document.getElementById('hid1').style.display="none";
		 document.getElementById('hid2').style.display="none";
		}
	else
		{
		document.getElementById('hid').style.display="";
		 document.getElementById('hid1').style.display="";
		 document.getElementById('hid2').style.display="";
		}
}

function generateReport()
{
var path=document.getElementById("path").value;
//var length=document.getElementById("stmtDay").options.length;
//var lbxdays =document.getElementById("lbxdays").value;
var reportName=document.getElementById("reportName").value;
var reporttype=document.getElementById("reporttype").value;
//var daysLength=lbxdays.split('|').lenth;
var msg="";


if(reportName=='')
{
	msg="* Please Select Report Name";
}
if(msg=='' && reporttype=='')
{
	msg="* Please Select Report Type";
}
/*if (msg=='' && reportName == 'Fund_Flow_Summary_Report') 
{

	if (length!= 3) {
		msg = "Please Select 3 Days";
	}
}*/
if(msg=='')
	{
	if(reporttype=='H')
		{
		document.getElementById("stmtDay").options.length = 0;
		lbxdays='';
		reportName='';
		reporttype='';
		var url=document.getElementById("SummaryReportForm").action=path+"/generateSummaryReport.do?method=openSummaryReport";
		window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );

		}
	else
		{
		//reportName='';
		//reporttype='';
		//document.getElementById("stmtDay").options.length = 0;
		//lbxdays='';
		document.getElementById("SummaryReportForm").action=path+"/generateSummaryReport.do?method=openSummaryReport";
		document.getElementById("SummaryReportForm").submit();
		}
	}
else
	{
	alert(msg);
	return false;
	}
}

//added by Pranaya - for Download/Upload Report
function generateFundFlowReport() 
{
	var caseId = document.getElementById('caseId').value;
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById('downloadUploadFundFlowReportForm').action = contextPath
			+ "/generateFundFlowReport.do?method=generateFundFlowReport&caseId="
			+ caseId;
	document.getElementById('downloadUploadFundFlowReportForm').submit();
}

function uploadFundFlowFile() 
{
	var caseId = document.getElementById('caseId').value;
	var contextPath = document.getElementById('contextPath').value;
	var fileName = document.getElementById('docFile').value;
	var ext=fileName.substring(fileName.lastIndexOf("xls"));
	if(ext.toUpperCase()=='XLS' || ext.toUpperCase()=='XLSX')
	{
		document.getElementById('downloadUploadFundFlowReportForm').action = contextPath
			+ "/uploadFundFlowAnalysisReportAction.do?method=uploadFundFlowAnalysisReport&caseId="
			+ caseId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById('downloadUploadFundFlowReportForm').submit();
	}
	else
	{
		alert('Please Select a .xls file');
		document.getElementById('docFile').focus();
		return false;
	}
}
//end by Pranaya










