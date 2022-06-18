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
function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
}
//method added by neeraj
function editLoanForward()
{
	DisButClass.prototype.DisButMethod();
	var dateChange=document.getElementById("dateChange").value;
	if(dateChange=='Y')
	{
		alert("First Save Loan Details.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		
		 var msg1='',msg2='';		
		 
		 var assetFlag= document.getElementById("assetFlag").value;
		 var loanAmount=removeComma(document.getElementById("loanAmount").value);
		 var sectorType=document.getElementById("sectorType").value;
		 var formNo=document.getElementById("formNo").value;


		 if(sectorType=='')
			 msg1='* Sector Type is required \n';
		 if(formNo=='')
			 msg2='* Application Form No is required \n';

		 
		 if(sectorType==''||formNo=='')
		 {
			 alert(msg1+""+msg2);
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
		 }
		if ((repaymentType != '' && repaymentType == 'I') && (assetFlag != '' && assetFlag != 'N') ){ 
			if((document.getElementById("assetCost").value) == '')
			{
				alert("* Asset Cost is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if((document.getElementById("noOfAsset").value) == '')
			{
				alert("* No of Asset is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
		if ((assetFlag != '' && assetFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
			
			var marginAmt = removeComma(document.getElementById("marginAmount").value);
			var assetCost = removeComma(document.getElementById("assetCost").value);
			var marginPrec = 0.00;
			marginPrec = removeComma(document.getElementById("margin").value);
			var noOfAsset = document.getElementById("noOfAsset").value;
			
//			if ((marginAmt > 0) && (marginPrec == 0)) {
//				alert("Margin % can't be zero.");
//				document.getElementById("saveButton").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//			}
			
			if (loanAmount>assetCost) {
				alert("Loan Amount should be equal or less than to Asset Cost");
				document.getElementById("assetCost").value= 0;
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if ((removeComma(document.getElementById("marginAmount").value) + removeComma(document.getElementById("loanAmount").value)) != assetCost)
		    {
				alert("Sum of Margin Amount and Loan Amount should be equal to Asset Cost");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			
			/*if (noOfAsset<1) {
				alert("Number of Assets should not be less than One.");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/

		}	
			
	var basePath=document.getElementById("basePath").value;
	document.getElementById("sourcingForm").action = basePath+"/editLoanDetailAction.do?method=editLoanForward";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("sourcingForm").submit();
	return true;
	}
}
function removeComma(id)
{
    var str = id;
    //alert(id);
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    var amount = parseFloat(stri);
    //alert(amount);
	return amount;
}
function trim(str) 
{
	return ltrim(rtrim(str));
}
function isWhitespace(charToCheck) 
{
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
}
function ltrim(str) 
{ 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
}
function rtrim(str) 
{
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
}
function newSearchLoan(stage)
{
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var loanNo=document.getElementById("loanNo").value;
	var agrementDate=document.getElementById("agrementDate").value;
	var customerName=document.getElementById("customerName").value;
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	var contextPath= document.getElementById("contextPath").value;
	
	
	if(dealNo!=''||loanNo!=''||agrementDate!=''||customerName!=''||product!=''||scheme!=''||reportingToUserId!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("CreditForm").action=contextPath+"/creditListAction.do?method=searchLoanDetail&stage="+stage;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("CreditForm").action=contextPath+"/creditListAction.do?method=searchLoanDetail&stage="+stage;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("searchButton").removeAttribute("disabled", "true");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("searchButton").removeAttribute("disabled", "true");
		return false;
	}
}

function newLoanInit()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("CreditForm").action = contextPath+"/loanInitBehindAction.do?method=openNewLoan";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("CreditForm").submit();
}

function productPopUp()
{	 
	var dealNo=document.getElementById("lbxDealNo").value;
	if(dealNo!='')
	{
		var basePath=document.getElementById("basePath").value;
	 	window.open(""+basePath+"/ajaxAction.do?method=openPopForProduct&dealNo="+dealNo+"",'ProductPopUP',"height=300,width=600,status=yes,toolbar=no,menubar=no,location=center");
	}
	else
	{
		alert("Please Select Deal No.");
	} 	
}
function checkLoanAmount()
{	
	var loanAmount = document.getElementById("loanAmount").value;
	var sanctionedLoanAmount=document.getElementById("sanctionedLoanAmount").value;
	var utilizeLoanAmount=document.getElementById("utilizeLoanAmount").value;
	var loanAm=0;
	var sanctionAm=0;
	var utilizeLA=0;
	var diff=0;	
	if(loanAmount!='')
		loanAm=parseFloat(removeComma(loanAmount));
	if(sanctionedLoanAmount!='')
		sanctionAm=parseFloat(removeComma(sanctionedLoanAmount));
	if(utilizeLoanAmount!='')
		utilizeLA=parseFloat(removeComma(utilizeLoanAmount));
	diff=parseFloat(sanctionAm)-parseFloat(utilizeLA);
	
	if(parseFloat(loanAm)> parseFloat(diff))
	{	alert("You can't enter Loan Amount more than "+diff);
		document.getElementById("loanAmount").value='';
		document.getElementById("sourcingForm").loanAmount.focus();
		return false;
	}
	return true;
}
function noChange()
{
	alert("You can not change the status");
	return false;
}
function nullNextDue(val)
{
	document.getElementById('hiddenDueDate').value = val;
	document.getElementById('nextDueDate').value = '';
}
	   
function checkInstallments()
{
	var loanNoofInstall=document.getElementById("loanNoofInstall").value;
	var installments=document.getElementById("installments").value;
	var total=0;
	var inst=0;
	if(loanNoofInstall!='')
	{
		total=parseInt(loanNoofInstall);
	}
	if(installments!='')
	{
		inst=parseInt(installments);
	}
	
	if(inst<total)
	{
		return true;
	}
	else
	{
		alert("You can't enter No of installment greater than Total installment");
		document.getElementById("installments").value='';
		return false;
	}
}

function validAggrDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var agreementDate=document.getElementById("agreementDate").value;
	var sanctionedDate=document.getElementById("sanctionedValidtill").value;
	var bDate=document.getElementById("bDate").value;
	var dt1=getDateObject(agreementDate,formatD.substring(2, 3));
    var dt2=getDateObject(sanctionedDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    if(dt1>dt2)
	{
    	msg="Please enter agreement date less than sanctioned Date ";
		document.getElementById("agreementDate").value='';
		//return false;
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
function validRepayDate1()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var agreementDate=document.getElementById("agreementDate").value;
	var bDate=document.getElementById("bDate").value;
	var repayDate=document.getElementById("repayEffectiveDateOneLoan").value;
    var dt1=getDateObject(repayDate,formatD.substring(2, 3));
    var dt2=getDateObject(bDate,formatD.substring(2, 3));
        
    if(dt1<dt2)
	{
		alert("Repay Effective date can't be less than Bussiness Date. ");
		document.getElementById("repayEffectiveDateOneLoan").value='';
		return false;
	}
	else
	{
		return true;
	}
}
// comment by vijendra singh
/*function calculateMaturityDate()
{	
	var repaymentType = document.getElementById('repaymentType').value;
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
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
		var CurrentDate = new Date(UpdateDateStr);
		CurrentDate.setMonth(CurrentDate.getMonth()+x);
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
		ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
		document.getElementById('maturityDate1').value=ModDateStr;
	}
}*/

//add by vijendra
/*function calculateMaturityDate()
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
		var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
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
//end  by vijendra
function calMaturityDate(val)
{	
	var repaymentType = document.getElementById('repaymentType').value;
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
		var formatD=document.getElementById("formatD").value;
		var currDate =   val;
		var currDay   = currDate.substring(0,2);
		var currMonth = currDate.substring(3,5);
		var currYear  = currDate.substring(6,10);
		var seprator = formatD.substring(2, 3);
		if(seprator=='-')
		  seprator = '/';
		else
		  seprator = '/';
		UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
		var CurrentDate = new Date(UpdateDateStr);
		CurrentDate.setMonth(CurrentDate.getMonth()+x);
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
		ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
		document.getElementById('maturityDate1').value=ModDateStr;
	}
}
function enableDisbursal()
{	
	if(document.getElementById("typeOfDisbursal").value=="M")
	{
		document.getElementById("disId").style.display="";
		document.getElementById("disSingle").style.display="none";
	}
	if(document.getElementById("typeOfDisbursal").value=="S")
	{
		document.getElementById("disId").style.display="none";
		document.getElementById("disSingle").style.display="";
	}	 
}
function saveLoanInCM()
{
	
	//alert(saveLoanInCM);showRepaymentTyperani
	     DisButClass.prototype.DisButMethod();
		 var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='';
		// ,msg11='';	// add msg11 by vijendra singh	
		 var loanAmount=document.getElementById("loanAmount").value;
		 var agreementDate=document.getElementById("agreementDate").value;		
		// var remarks=document.getElementById("remarks").value;
		 var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
		 var repaymentType = document.getElementById("repaymentType").value; 
		 var assetFlag= document.getElementById("assetFlag").value;
		 var sectorType=document.getElementById("sectorType").value;
		 var formNo=document.getElementById("formNo").value;
		 var rateType=document.getElementById("rateType").value;
		 var basePath=document.getElementById("basePath").value;
		 var businessType=document.getElementById("businessType").value;
		 if(document.getElementById("creditPeriod"))
		 var creditPeriod =document.getElementById("creditPeriod").value;  //added by Richa
		 var tenureInDay =document.getElementById("tenureInDay").value;  //added by Richa
		 var frequency =document.getElementById("frequency").value;//Added for Frequency mendetory
		 var grossBlock= document.getElementById("grossBlock").value;
		 var netBlock= document.getElementById("netBlock").value;
		 //add by vijendra singh
		 var editDueDate= document.getElementById("editDueDate").value;		
		// end by vijendra singh
		 
		 // alert("businessType "+businessType); 

//		 var showInterestCalc = document.getElementById("showInterestCalc").value;

		// var showInterestCalc = document.getElementById("showInterestCalc").value;		

		 var nextDueDate="";
		 var repayEffectiveDate="";
		 var dueDay="";
		 var insType= document.getElementById("installmentType").value;
		 var intCalMethod= document.getElementById("interestCalculationMethod").value;
		 var intDueDate= "";
//	
//	if(insType=='S' && intCalMethod=='D')
	 intDueDate= document.getElementById("interestDueDate").value;
//	if(insType=='S' && intCalMethod!='D')
//	{
//		intDueDate= document.getElementById("interestDueDate").value;
//	}
		 var val = document.getElementById('showRepaymentType').value;
		 var tenureMonths = "";
		 // add by saorabh on 5 sep 2014
		 if(businessType=='')
		 {
		 alert('* Business Type is required \n');
		 document.getElementById("businessType").focus();
		  DisButClass.prototype.EnbButMethod();
		 return false;
		 }
		 //end by saorabh
		 if(loanAmount=='')
			 msg1='* Loan Amount is required \n';
		 if(agreementDate=='')
			 msg4='* Agreement Date is required \n';
		 if(sectorType=='')
			 msg8='* Sector Type is required \n';		
		 if(formNo=='')
			 msg9='* Application Form No is required \n';
		 if(frequency=='')
			 msg10='* Frequency is required \n';
		/* if(remarks=='')
			 msg6='* Maker Remarks is required \n';*/
		// add by vijendra singh
			if (editDueDate==' ') {
				msg11 += "* Edit Due Date is required \n";
			}
			// end code vijendra
		 
		 if(repaymentType=='I')
		 {
			nextDueDate=document.getElementById("nextDueDate").value;			
			tenureMonths=document.getElementById("tenureMonths").value;
			if(oneDealOneLoan=="N")
			{
				repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
				dueDay=document.getElementById("dueDay").value;
			}
			if(oneDealOneLoan=="Y")
			{
				repayEffectiveDate=document.getElementById("repayEffectiveDateOneLoan").value;
				dueDay=document.getElementById("dueDayOneLoan").value;
			}
			if(dueDay=="")
				msg2='* Due Day is required \n';
			if(nextDueDate=="")
				msg3='* Next Due Date is required \n';
			if(repayEffectiveDate=="" && repaymentType=='I')
				msg5='* Repay Effective Date is required \n';
			if(tenureMonths =="")
				msg7='* Tenure Months is required \n';
			
		 }
		 		 
		 if(sectorType=='' ||formNo=='' || loanAmount=='' ||agreementDate==''  ||frequency==''  ||((repaymentType=='I') && (dueDay=="" ||nextDueDate==""||repayEffectiveDate=="" || tenureMonths=='')))
		 {
			 alert(msg1+""+msg2+""+msg3+""+msg4+""+msg5+""+msg7+""+msg8+""+msg9+""+msg10);
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
		 }
		if ((repaymentType != '' && repaymentType == 'I') && (assetFlag != '' && assetFlag != 'N') ){ 
			if((document.getElementById("assetCost").value) == '')
			{
				alert("* Asset Cost is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if((document.getElementById("noOfAsset").value) == '')
			{
				alert("* No of Asset is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		//add by viendra singh             
	    if(editDueDate==' ')
		 {
           //alert(editDueDate);			
			 alert(msg11);
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
		 }		
		// end by vijendra singh
		
		//richa changes starts
		if(parseFloat(creditPeriod) > parseFloat(tenureInDay)){
		alert(" Credit Period should be less than or equal to tenure in days ");
		document.getElementById("Save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	//Richa changes ends
	 		
		if ((assetFlag != '' && assetFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
			
			var marginAmt = removeComma(document.getElementById("marginAmount").value);
			var assetCost = removeComma(document.getElementById("assetCost").value);
			var marginPrec = 0.00;
			marginPrec = removeComma(document.getElementById("margin").value);
			var noOfAsset = document.getElementById("noOfAsset").value;
			
//			if ((marginAmt > 0) && (marginPrec == 0)) {
//				alert("Margin % can't be zero.");
//				document.getElementById("saveButton").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//			}

			if ((removeComma(document.getElementById("marginAmount").value) + removeComma(document.getElementById("loanAmount").value)) != assetCost) {
				alert("Sum of Margin Amount and Loan Amount should be equal to Asset Cost");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			/*if (noOfAsset<1) {
				alert("Number of Assets should not be less than One.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/

		}

		var sanctionedLoanAmount=document.getElementById("sanctionedLoanAmount").value;
		var utilizeLoanAmount=document.getElementById("utilizeLoanAmount").value;
		var loanAm=0.00;
		var sanctionAm=0.00;
		var utilizeLA=0.00;
		var diff=0.00;			
		if(loanAmount!='')
			loanAm=parseFloat(removeComma(loanAmount));
		if(sanctionedLoanAmount!='')
			sanctionAm=parseFloat(removeComma(sanctionedLoanAmount));
		if(utilizeLoanAmount!='')
			utilizeLA=parseFloat(removeComma(utilizeLoanAmount));
		
		diff=parseFloat(sanctionAm)-parseFloat(utilizeLA);		
		if(parseFloat(loanAm) > parseFloat(diff))
		{
			alert("You can't enter Loan Amount more than "+diff);
			document.getElementById("loanAmount").value='';
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("sourcingForm").loanAmount.focus();
			return false;
		}	 
//start by sachin
		if (val == 'INSTALLMENT' || repaymentType=='I') {
	   		var noOfInstall=document.getElementById("loanNoofInstall").value
			var installments=document.getElementById("installments").value;
			if(document.getElementById("installments").value==''){
				installments=0;
			}
			else{
				installments=parseInt(installments);
			}
			
			if(document.getElementById("dealInstallmentMode").value == 'A')
			{
				if(installments+1>noOfInstall){
				alert(" No of Installment should not be greater than total No. of Advance Installment ");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			}
			if(document.getElementById("dealInstallmentMode").value == 'R')
			{
				if(installments>noOfInstall){
				alert(" No of Installment should not be greater than total No. of Advance Installment ");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			}
			}

		
//end by sachin		
//starts by Nishant
		if(repaymentType=='I')
		{
			if(document.getElementById("tenureMonths").value == '0')
			{
				alert(" Tenure Months can not be 0.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
//End by Nishant		
		 if(repaymentType=='I')
		 {		
			 var day1= repayEffectiveDate.substring(0,2);
			 var month1=repayEffectiveDate.substring(3,5);
			 var year1=repayEffectiveDate.substring(6);
			 var day2= nextDueDate.substring(0,2);
			 var month2=nextDueDate.substring(3,5);
			 var year2=nextDueDate.substring(6);		
			 
//			 if(parseFloat(day2) != parseFloat(dueDay))
//			 {
//				alert("Next Due Date must be equal to Due Day ie."+dueDay);
//				DisButClass.prototype.EnbButMethod();
//				//document.getElementById("saveButton").removeAttribute("disabled","true");
//				return false;
//			 }
//			
			 if(parseFloat(year1)>parseFloat(year2))
			 {	
				alert("Next Due Date should be greater than Repay Effective Date");
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			 }
			 else
			 {
				if(parseFloat(year1)==parseFloat(year2))
				{
					if(parseFloat(month1)>parseFloat(month2))
					{	
						alert("Next Due Date should be greater than Repay Effective Date");
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("saveButton").removeAttribute("disabled","true");
						return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1)>=parseFloat(day2))
							{	
								alert("Next Due Date should be greater than Repay Effective Date");	
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("saveButton").removeAttribute("disabled","true");
								return false;
							}
						}
					}
				}
			 }			 
		 }

		 if(repaymentType=='N')

		 {
			 var showInterestCalc = document.getElementById("showInterestCalc").value;	
			 if(showInterestCalc==""||showInterestCalc==null)
			 {
				 alert("* Interest Calculation From is required \n");
				 DisButClass.prototype.EnbButMethod();		
					return false; 
			 }
		 }
	//Nishant space starts
		 if(rateType=='F')
			{
				var noOfInstall=parseInt(document.getElementById("loanNoofInstall").value);
				if(noOfInstall==0)
				{
					alert("No of Installment can’t Zero(0).");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				var tenure=parseInt(removeComma(document.getElementById("tenureMonths").value));
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
						document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
						 document.getElementById("processingImage").style.display = '';
						 document.getElementById("sourcingForm").submit();
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
				if(document.getElementById("interestDueDate").value == "" ||  document.getElementById("interestDueDate").value ==null)
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
		
			if(installmentType=='S')
			{
				/*if(document.getElementById("firstInterestDueDate").value == "" && document.getElementById("firstInterestDueDate").value ==null)
				{
					alert("Please insert proper First Interest Due Date ");
					DisButClass.prototype.EnbButMethod();
					return false;
				}*/
				if(document.getElementById("maturityDate1").value == "" && document.getElementById("maturityDate1").value ==null)
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
			//Ajay's space end 
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
	//Nishant space ends
	 document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails&interestDueDate="+intDueDate+"&grossBlock="+grossBlock+"&netBlock="+netBlock;
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("sourcingForm").submit();
	 return true;	
}


/*function validateMaturityDate(){
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
/*		    alert("userDefinedMaturityDate.getFullYear() "+ userDefinedMaturityDate.getFullYear());
		    alert("CurrentDate.getFullYear() "+ CurrentDate.getFullYear());
		    alert(" userDefinedMaturityDate.getMonth() "+  userDefinedMaturityDate.getMonth());
		    alert("CurrentDate.getMonth() "+ CurrentDate.getMonth());*/
		/*if(userDefinedMaturityDate.getFullYear()!=CurrentDate.getFullYear()  )
			{
			alert("User Can select maturity date according to tenure month and year");
			
			}
		if(userDefinedMaturityDate.getMonth()!=CurrentDate.getMonth())
			{
			alert("User Can select maturity date according to tenure month and year");
			}
		
		
		if(ParseInt(sDay)<0 && ParseInt(sDay)>31 )
			{
			alert("Select Date range between 1-31");
			}
		
			}
		  
		  
		  
	
}*/


function validateLoanInit(fwdMsg)
{   
	DisButClass.prototype.DisButMethod();
	var loanId=document.getElementById("loanId").value;
	var businessType=document.getElementById("businessType").value;
	if(loanId!='')
	{
		if(!confirm(fwdMsg))	 
	    {
	       	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
		//added by neeraj for re generate plan and repayment schedule when naxtduedate or repayeffective date changed
		var dateChange=document.getElementById("dateChange").value;
		if(dateChange=='Y')
		{
			alert("First Save Loan Details.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//tripathi's space end
		/*var pendingDisbursal = document.getElementById("pendingDisbursal").value;
		if(pendingDisbursal=='Y'){
			alert("First Approve Disbursal Details");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
		var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='';		
		var loanAmount=document.getElementById("loanAmount").value;
		var agreementDate=document.getElementById("agreementDate").value;		
		//var remarks=document.getElementById("remarks").value;
		var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
		var repaymentType = document.getElementById("repaymentType").value; 
		var nextDueDate="";
		var repayEffectiveDate="";
		var dueDay="";		 
		if(loanAmount=='')
			msg1='* Loan Amount is required \n';
		if(agreementDate=='')
			 msg4='* Agreement Date is required \n';
//		if(remarks=='')
//			 msg6='* Maker Remarks is required \n'; //Commented by Nishant Rai
			 
		if(repaymentType=='I')
		{
				nextDueDate=document.getElementById("nextDueDate").value;		
				
				if(oneDealOneLoan=="N")
				{
					repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
					dueDay=document.getElementById("dueDay").value;
				}
				if(oneDealOneLoan=="Y")
				{
					repayEffectiveDate=document.getElementById("repayEffectiveDateOneLoan").value;
					dueDay=document.getElementById("dueDayOneLoan").value;
				}
				if(dueDay=="")
					msg2='* Due Day is required \n';
				if(nextDueDate=="")
					msg3='* Next Due Date is required \n';
				if(repayEffectiveDate=="")
					msg5='* Repay Effective Date is required \n';
		}
		if(loanAmount=='' ||agreementDate=='' || ((repaymentType=='I')&& (dueDay=="" ||nextDueDate==""||repayEffectiveDate=="")))
		{
				 alert(msg1+""+msg2+""+msg3+""+msg4+""+msg5);
				 DisButClass.prototype.EnbButMethod();
				return false;
		}
		if(repaymentType=='I')
		{		
			 var day1= repayEffectiveDate.substring(0,2);
			 var month1=repayEffectiveDate.substring(3,5);
			 var year1=repayEffectiveDate.substring(6);
			 var day2= nextDueDate.substring(0,2);
			 var month2=nextDueDate.substring(3,5);
			 var year2=nextDueDate.substring(6);		
			 
//			 if(parseFloat(day2) != parseFloat(dueDay))
//			 {
//				alert("Next Due Date must be equal to Due Day ie."+dueDay);
//				DisButClass.prototype.EnbButMethod();
//				return false;
//			 }
			
			 if(parseFloat(year1)>parseFloat(year2))
			 {	
				alert("Next Due Date should be greater than Repay Effective Date");
				DisButClass.prototype.EnbButMethod();
				return false;
			 }
			 else
			 {
				if(parseFloat(year1)==parseFloat(year2))
				{
					if(parseFloat(month1)>parseFloat(month2))
					{	
						alert("Next Due Date should be greater than Repay Effective Date");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1)>=parseFloat(day2))
							{	
								alert("Next Due Date should be greater than Repay Effective Date");		
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
					}
				}
			 }		 
		 }

		 var assetFlag= document.getElementById("assetFlag").value;
		if ((assetFlag != '' && assetFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
		
			var marginAmt = removeComma(document.getElementById("marginAmount").value);
			var assetCost = removeComma(document.getElementById("assetCost").value);
			var marginPrec = 0.00;
			var marginPrec = removeComma(document.getElementById("margin").value);
			var noOfAsset = document.getElementById("noOfAsset").value;
		
//			if ((marginAmt > 0) && (marginPrec == 0)) {
//				alert("Margin % can't be zero.");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//			}
			if ((removeComma(document.getElementById("marginAmount").value) + removeComma(document.getElementById("loanAmount").value)) != assetCost)
			{
				alert("Sum of Margin Amount and Loan Amount should be equal to Asset Cost");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			/*if (noOfAsset<1) {
				alert("Number of Assets should not be less than One.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/

		}
		var basePath=document.getElementById("basePath").value;
		 document.getElementById("sourcingForm").action = basePath+"/loanDetailInCMBehindAction.do?method=stageForwardLoanInit";
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("sourcingForm").submit();
		/*var marginMoneyFlag = document.getElementById("marginMoneyFlag").value;
		if(marginMoneyFlag=='Y'){
			if(confirm("Margin Money details are pending,Do you want to continue ?")){
				var basePath=document.getElementById("basePath").value;
				 document.getElementById("sourcingForm").action = basePath+"/loanDetailInCMBehindAction.do?method=stageForwardLoanInit";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("sourcingForm").submit();
			}else{
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}else{*/
			var basePath=document.getElementById("basePath").value;
			 document.getElementById("sourcingForm").action = basePath+"/loanDetailInCMBehindAction.do?method=stageForwardLoanInit";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("sourcingForm").submit();
		/*}*/
	}
	else
	{
		alert("You can't move without saving before Loan Details.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}



function validRepayDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var agreementDate=document.getElementById("agreementDate").value;
	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
     
    if(dt1<dt3)
	{
		alert("Repay Effective date can't be less than Bussiness Date. ");
		document.getElementById("repayEffectiveDate").value='';
		return false;		
	}
	else
	{
		return true;
	}
}

function saveRecord()
{
	//alert("test");
	 var flag=0;
	 var ch=document.getElementsByName('dealLoanId');
	 for(i=0;i<ch.length;i++)
		{
		   if(ch[i].checked==true)
			{
			
			  id=ch[i].value;
			  flag=1;
			  break;	
			}
		  
		}
	 if(flag==0)
	 {
		 alert("Please Select one Record!!!");
		 return false;
	 }
	 else
	 {//alert("ok saveRecord");
		 var basePath=document.getElementById("basePath").value;
	     var address = basePath+"/ajaxAction.do?method=retriveValueByProduct";
	     //alert("ok saveRecord"+address);
			var data = "dealLoanId=" + id;
			send_id(address,data);
		 
	        return true;
	 }
	 
}
 function send_id(address, data) {
		var request = getRequestObject();
		request.onreadystatechange = function () {
			result_id(request);
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
 function result_id(request){
	  
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			//alert('...............');
			var s1 = str.split("$:");
		    //alert("s2 "+removeComma(trim(s1[2]))+"    S3  "+removeComma(trim(s1[3]))+"  s2-s3  "+(removeComma(trim(s1[2]))-removeComma(trim(s1[3]))));
			//alert(trim(s1[52]));
			//alert("trim(s1[85])"+trim(s1[85]));
			//alert("trim(s1[86])"+trim(s1[86]));
			window.opener.document.getElementById('tenureInDay').value=trim(s1[66]);
			window.opener.document.getElementById('daysBasis').value=trim(s1[65]);
	    	window.opener.document.getElementById('ltvPerc').value=trim(s1[53]);
			window.opener.document.getElementById('minFlatRate').value=trim(s1[54]);
			window.opener.document.getElementById('maxFlatRate').value=trim(s1[55]);
			window.opener.document.getElementById('minEffRate').value=trim(s1[56]);
			window.opener.document.getElementById('maxEffRate').value=trim(s1[57]);
			window.opener.document.getElementById('minTenure').value=trim(s1[58]);
			window.opener.document.getElementById('maxTenure').value=trim(s1[59]);
			window.opener.document.getElementById('totalVatAmt').value=trim(s1[72]);//Richa HP VAT TAX
			window.opener.document.getElementById('vatPercent').value=trim(s1[73]);//Saorabh HP VAT TAX
			window.opener.document.getElementById('serviceTax').value=trim(s1[74]);//saorabh HP VAT TAX
			window.opener.document.getElementById('serviceAmount').value=trim(s1[75]);//saorabh HP VAT TAX
			window.opener.document.getElementById('lbxareaCodeVal').value=trim(s1[60]);
			window.opener.document.getElementById('areaCodename').value=trim(s1[61]);
			window.opener.document.getElementById('creditPeriod').value=trim(s1[76]);
//Prashant 
			window.opener.document.getElementById('assetFlag').value=trim(s1[62]);
			window.opener.document.getElementById('grossBlock').value=trim(s1[81]);
			window.opener.document.getElementById('netBlock').value=trim(s1[82]);
			window.opener.document.getElementById('insurancePremium').value=trim(s1[85]);
			window.opener.document.getElementById('requestedLoamt').value=trim(s1[86]);
			window.opener.document.getElementById('editDueDate').value=trim(s1[87]); // Code added for Edit Due Date| Rahul papneja |30012018
			window.opener.document.getElementById('businessType1').value=trim(s1[88]);
			window.opener.document.getElementById('businessType').value=trim(s1[88]);

			if(removeComma(trim(s1[2]))<=removeComma(trim(s1[3])))
			{
				alert("You can not create new loan because sanctioned amount is fully utilized amount");
				window.opener.document.getElementById('dealNo').value='';
				window.opener.document.getElementById('lbxDealNo').value='';
				window.opener.document.getElementById('showProduct').value='';
				window.opener.document.getElementById('product').value='';
				window.opener.document.getElementById('customerName').value='';
				window.close();
			}
			else
			{
				
			window.opener.document.getElementById('product').value=trim(s1[0]);
			window.opener.document.getElementById('scheme').value=trim(s1[1]);
			window.opener.document.getElementById('sanctionedLoanAmount').value=trim(s1[2]);
			window.opener.document.getElementById('utilizeLoanAmount').value=trim(s1[3]);
			window.opener.document.getElementById('sanctionedValidtill').value=trim(s1[4]);
			window.opener.document.getElementById('assetCost').value=trim(s1[5]);
			window.opener.document.getElementById('margin').value=trim(s1[6]);
			window.opener.document.getElementById('marginAmount').value=trim(s1[7]);
			if(trim(s1[3])!='' && trim(s1[2])!='')
			{
				window.opener.document.getElementById('loanAmount').value=removeComma(trim(s1[2]))-removeComma(trim(s1[3]));
			}
			else
			{
				window.opener.document.getElementById('loanAmount').value=trim(s1[2]);
				window.opener.document.getElementById('utilizeLoanAmount').value='0';
			}
			window.opener.document.getElementById('tenureMonths').value=trim(s1[9]);
			
			//alert(s1[3]);
			
			if(trim(s1[10])=='E')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				//window.opener.document.getElementById('showRateType').value='EFFECTIVE RATE';
				window.opener.document.getElementById('fixed').checked=true;	
				
			}
			else if(trim(s1[10])=='F')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				//window.opener.document.getElementById('showRateType').value='FLAT RATE';
				window.opener.document.getElementById('fixed').checked=true;	
				//window.opener.document.getElementById("fixed").setAttribute("disabled",true);
			}
			else if(trim(s1[10])=='P')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				//window.opener.document.getElementById('showRateType').value='Per Thousands Per Month';
			}
			window.opener.document.getElementById('rateMethod').value=trim(s1[11]);
			if(trim(s1[11])=="F"){
				
				window.opener.document.getElementById("floating").setAttribute("disabled",true);
				window.opener.document.getElementById("fixed").removeAttribute("disabled",true);
				window.opener.document.getElementById('fixed').checked=true;
			}else if(trim(s1[11])=="L")
			{
				window.opener.document.getElementById("fixed").setAttribute("disabled",true);
				window.opener.document.getElementById("floating").removeAttribute("disabled",true);
				window.opener.document.getElementById('floating').checked=true;
			}
			window.opener.document.getElementById('baseRateType').value=trim(s1[12]);
			window.opener.document.getElementById('baseRate').value=trim(s1[13]);
			window.opener.document.getElementById('markUp').value=trim(s1[14]);
			window.opener.document.getElementById('effectiveRate').value=trim(s1[15]);
			window.opener.document.getElementById('fixPriod').value=trim(s1[69]);
			window.opener.document.getElementById('noOfAsset').value=trim(s1[70]);
			window.opener.document.getElementById('formNo').value=trim(s1[71]);
			window.opener.document.getElementById('validteFormNo').value='Y';
				
			if(trim(s1[16])=='I')
			{
//				window.opener.document.getElementById('netLtv').value=trim(s1[68]);
//				window.opener.document.getElementById("netLtvRow").style.display='';
				window.opener.document.getElementById('repaymentType').value=trim(s1[16]);
				window.opener.document.getElementById('showRepaymentType').value='INSTALLMENT';
				window.opener.document.getElementById('nddheader').style.display='';
				window.opener.document.getElementById('ndd').style.display='';
				window.opener.document.getElementById('effDateHeader').style.display='';
				window.opener.document.getElementById('effDate').style.display='';
				window.opener.document.getElementById('cycleDate1').style.display='';
				window.opener.document.getElementById('cycleDate2').style.display='';
				window.opener.document.getElementById('interestCompoundingFrequency').value=trim(s1[77]);
				window.opener.document.getElementById('interestCalculationMethod').value=trim(s1[78]);
				window.opener.document.getElementById('interestFrequency').value=trim(s1[79]);
				
				
			}
			else if(trim(s1[16])=='N')
			{
				window.opener.document.getElementById("netLtvRow").style.display='none';
				window.opener.document.getElementById('repaymentType').value=trim(s1[16]);
				window.opener.document.getElementById('showRepaymentType').value='NON-INSTALLMENT';
				window.opener.document.getElementById('nddheader').style.display='none';
				window.opener.document.getElementById('ndd').style.display='none';
				window.opener.document.getElementById('effDateHeader').style.display='none';
				window.opener.document.getElementById('effDate').style.display='none';
				window.opener.document.getElementById('cycleDate1').style.display='none';
				window.opener.document.getElementById('cycleDate2').style.display='none';
				window.opener.document.getElementById("creditPeriod").style.display='none';
				window.opener.document.getElementById("creditPeriod1").style.display='none';

			}
			window.opener.document.getElementById('installmentType').value=trim(s1[17]);
			window.opener.document.getElementById('showInstallmentType').value=trim(s1[67]);
			//alert(s1[17]);
			if(trim(s1[17])=='S')
					{
						
					window.opener.document.getElementById("interestCalculationMethod").removeAttribute("disabled",true);
					window.opener.document.getElementById("interestFrequency").removeAttribute("disabled",true);
					
					window.opener.document.getElementById('interestDueDate').value=trim(s1[80]);
					window.opener.document.getElementById("interestDueDateHeader1").style.display='';
					window.opener.document.getElementById("interestDueDateHeader2").style.display='';
					window.opener.document.getElementById("SeparateInterestDate").style.display='';
						
					//alert(s1[80]);
					}
			
//			if(trim(s1[17])=='E')
//			{
//				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
//				window.opener.document.getElementById('showInstallmentType').value='Eq. INSTALLMENT';
//				
//			}
//			else if(trim(s1[17])=='G')
//			{
//				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
//				window.opener.document.getElementById('showInstallmentType').value='Gr. INSTALLMENT';
//			}
//			if(trim(s1[17])=='P')
//			{
//				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
//				window.opener.document.getElementById('showInstallmentType').value='Eq. PRINCIPAL';
//				
//			}
//			else if(trim(s1[17])=='Q')
//			{
//				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
//				window.opener.document.getElementById('showInstallmentType').value='Gr. PRINCIPAL1';
//			}
//			else if(trim(s1[17])=='R')
//			{
//				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
//				window.opener.document.getElementById('showInstallmentType').value='Gr. PRINCIPAL2';
//			}
//		
			if(trim(s1[18])=='M')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				//window.opener.document.getElementById('showFrequency').value='MONTHLY';
				
			}
			else if(trim(s1[18])=='B')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				//window.opener.document.getElementById('showFrequency').value='BIMONTHLY';
			}
			else if(trim(s1[18])=='Q')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				//window.opener.document.getElementById('showFrequency').value='QUARTERLY';
			}
			else if(trim(s1[18])=='H')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				//window.opener.document.getElementById('showFrequency').value='HALFYERALY';
			}
			else if(trim(s1[18])=='Y')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				//window.opener.document.getElementById('showFrequency').value='YEARLY';
			}
			//alert(trim(s1[19]))
			if(trim(s1[19])=='P')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='PDC';
				
			}
			else if(trim(s1[19])=='E')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='ECS';
			}
			else if(trim(s1[19])=='O')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='OTC';
				
			}
			else if(trim(s1[19])=='D')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='DIRECT DEBIT';
			}
			else if(trim(s1[19])=='N')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='NPDC';
			}
			else if(trim(s1[19])=='R')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='RPDC';
			}
			else if(trim(s1[19])=='S')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='ESCROW';
			}
			
			//alert(s1[22]);
			if(trim(s1[22])=='A')
			{
				window.opener.document.getElementById('dealInstallmentMode').value=trim(s1[22]);
				window.opener.document.getElementById('showInstallmentMode').value='ADVANCE';
				
			}
			else if(trim(s1[22])=='R')
			{
				window.opener.document.getElementById('dealInstallmentMode').value=trim(s1[22]);
				window.opener.document.getElementById('showInstallmentMode').value='ARREAR';
			}
			
			//window.opener.document.getElementById('dealLoanId').value=trim(s1[20]);
			//window.opener.document.getElementById('dealNo').value=trim(s1[21]);
			window.opener.document.getElementById('loanBranch').value=trim(s1[25]);
			window.opener.document.getElementById('loanIndustry').value=trim(s1[26]);
			window.opener.document.getElementById('loanSubIndustry').value=trim(s1[27]);
			window.opener.document.getElementById('loanCustomerId').value=trim(s1[28]);
			window.opener.document.getElementById('loanCustomerType').value=trim(s1[29]);
			window.opener.document.getElementById('loanCustomerExisting').value=trim(s1[30]);
			//alert(trim(s1[31]));
			window.opener.document.getElementById('showProduct').value=trim(s1[31]);
			window.opener.document.getElementById('showScheme').value=trim(s1[32]);
			
			
			window.opener.document.getElementById('productCat').value=trim(s1[33]);
			//alert(trim(s1[35]));
			//code added by neerak tripathi
			if(trim(s1[16])=='I')
			if(trim(s1[10])=='F')
			{
				window.opener.document.getElementById('loanNoofInstall').removeAttribute("readOnly");
				window.opener.document.getElementById('loanNoofInstall').removeAttribute("tabIndex");
			}
			else
			{   
				window.opener.document.getElementById('loanNoofInstall').setAttribute("readOnly",true);
				window.opener.document.getElementById('loanNoofInstall').setAttribute("tabIndex",-1);
			}
			//tripathi's space end
			window.opener.document.getElementById('loanNoofInstall').value=trim(s1[34]);
			window.opener.document.getElementById('utilizeLoanAmount').value=trim(s1[35]);
			window.opener.document.getElementById('productType').value=trim(s1[36]);
			window.opener.document.getElementById('sanctionedDate').value=trim(s1[37]);
			window.opener.document.getElementById('sectorType').value=trim(s1[38]);
			window.opener.document.getElementById('showSectorType').value=trim(s1[39]);
			window.opener.document.getElementById('loanDealId').value=trim(s1[40]);
				
			window.opener.document.getElementById('installments').value=trim(s1[41]);
			
			if(trim(s1[16])=='I')
			{
				
					
					window.opener.document.getElementById("dueDay").removeAttribute("disabled",true);
					window.opener.document.getElementById('cycleDue1').innerHTML = trim(s1[83]);//Richa HP VAT TAX
					window.opener.document.getElementById('dueDay').value=trim(s1[42]);
				
				//window.opener.document.getElementById("typeOfDisbursal").removeAttribute("disabled",true);
				//window.opener.document.getElementById("noOfDisbursalText").removeAttribute("disabled",true);
				// Start By Prashant
				if(trim(s1[62])=='A')
				{
//sachin////////////////					
					window.opener.document.getElementById('netLtv').value=trim(s1[68]);
					window.opener.document.getElementById("netLtvRow").style.display='';
					window.opener.document.getElementById("asset1").style.display='';
					window.opener.document.getElementById("asset2").style.display='';
					window.opener.document.getElementById("assetCT1").style.display='';
					window.opener.document.getElementById("assetCT2").style.display='';
					window.opener.document.getElementById("marg1").style.display='';
					window.opener.document.getElementById("marg2").style.display='';
					window.opener.document.getElementById("margAmount1").style.display='';
					window.opener.document.getElementById("margAmount2").style.display='';
				}
				else
				{
//sachin////////////////
					window.opener.document.getElementById("netLtvRow").style.display='none';
					window.opener.document.getElementById("asset1").style.display='none';
					window.opener.document.getElementById("asset2").style.display='none';
					window.opener.document.getElementById("assetCT1").style.display='none';
					window.opener.document.getElementById("assetCT2").style.display='none';
					window.opener.document.getElementById("marg1").style.display='none';
					window.opener.document.getElementById("marg2").style.display='none';
					window.opener.document.getElementById("margAmount1").style.display='none';
					window.opener.document.getElementById("margAmount2").style.display='none';
				}
				// End By Prashant
				window.opener.document.getElementById("instType1").style.display='';
				window.opener.document.getElementById("instType2").style.display='';
				window.opener.document.getElementById("freq1").style.display='';
				window.opener.document.getElementById("freq2").style.display='';
				window.opener.document.getElementById("repayMode1").style.display='';
				window.opener.document.getElementById("repayMode2").style.display='';
				window.opener.document.getElementById("instMode1").style.display='';
				window.opener.document.getElementById("instMode2").style.display='';
				window.opener.document.getElementById("totalIns1").style.display='';
				window.opener.document.getElementById("totalIns2").style.display='';
				window.opener.document.getElementById("noIns1").style.display='';
				window.opener.document.getElementById("noIns2").style.display='';
				window.opener.document.getElementById("cycleDate1").style.display='';
				window.opener.document.getElementById("cycleDate2").style.display='';
				//window.opener.document.getElementById("tyeOfDis1").style.display='';
				//window.opener.document.getElementById("tyeOfDis2").style.display='';
				//window.opener.document.getElementById("noOfDis1").style.display='';
				//window.opener.document.getElementById("noOfDis2").style.display='';
				window.opener.document.getElementById("pdo1").style.display='';
				window.opener.document.getElementById("pdo2").style.display='';
				window.opener.document.getElementById("repaySmk1").style.display='';
				window.opener.document.getElementById("repaySmk2").style.display='';
				window.opener.document.getElementById("insuranceBy1").style.display='';
				window.opener.document.getElementById("insuranceBy2").style.display='';
				//$urendra
				window.opener.document.getElementById("int1").style.display='none';
				window.opener.document.getElementById("int2").style.display='none';
				//@Surendra
				// Start By Prashant
				//alert(trim(s1[62]));
				if(trim(s1[62])=='A')
				{
					window.opener.document.getElementById("ltvDesc").style.display='';
					window.opener.document.getElementById("ltvP").style.display='';
				}
				else
				{
					window.opener.document.getElementById("ltvDesc").style.display='none';
					window.opener.document.getElementById("ltvP").style.display='none';
				}
				// End By Prashant
			}
			else if(trim(s1[16])=='N')
			{
				window.opener.document.getElementById('dueDay').value='';
				//window.opener.document.getElementById('typeOfDisbursal').value='';
				//window.opener.document.getElementById('noOfDisbursalText').value='';
				//window.opener.document.getElementById("typeOfDisbursal").setAttribute("disabled",true);
				//window.opener.document.getElementById("noOfDisbursalText").setAttribute("disabled",true);
				window.opener.document.getElementById("dueDay").setAttribute("disabled",true);
				window.opener.document.getElementById("asset1").style.display='none';
				window.opener.document.getElementById("asset2").style.display='none';
				window.opener.document.getElementById("assetCT1").style.display='none';
				window.opener.document.getElementById("assetCT2").style.display='none';
				window.opener.document.getElementById("marg1").style.display='none';
				window.opener.document.getElementById("marg2").style.display='none';
				window.opener.document.getElementById("margAmount1").style.display='none';
				window.opener.document.getElementById("margAmount2").style.display='none';
				window.opener.document.getElementById("instType1").style.display='none';
				window.opener.document.getElementById("instType2").style.display='none';
				window.opener.document.getElementById("freq1").style.display='none';
				window.opener.document.getElementById("freq2").style.display='none';
				window.opener.document.getElementById("repayMode1").style.display='none';
				window.opener.document.getElementById("repayMode2").style.display='none';
				window.opener.document.getElementById("instMode1").style.display='none';
				window.opener.document.getElementById("instMode2").style.display='none';
				window.opener.document.getElementById("totalIns1").style.display='none';
				window.opener.document.getElementById("totalIns2").style.display='none';
				window.opener.document.getElementById("noIns1").style.display='none';
				window.opener.document.getElementById("noIns2").style.display='none';
				window.opener.document.getElementById("cycleDate1").style.display='none';
				window.opener.document.getElementById("cycleDate2").style.display='none';
				//window.opener.document.getElementById("tyeOfDis1").style.display='none';
				//window.opener.document.getElementById("tyeOfDis2").style.display='none';
				//window.opener.document.getElementById("noOfDis1").style.display='none';
				//window.opener.document.getElementById("noOfDis2").style.display='none';
				window.opener.document.getElementById("pdo1").style.display='none';
				window.opener.document.getElementById("pdo2").style.display='none';
				window.opener.document.getElementById("repaySmk1").style.display='none';
				window.opener.document.getElementById("repaySmk2").style.display='none';
				window.opener.document.getElementById("insuranceBy1").style.display='none';
				window.opener.document.getElementById("insuranceBy2").style.display='none';
				window.opener.document.getElementById("insComFre1").style.display='none';
				window.opener.document.getElementById("insComFre2").style.display='none';
				window.opener.document.getElementById("insCalMet1").style.display='none';
				window.opener.document.getElementById("insCalMet2").style.display='none';
				window.opener.document.getElementById("insFre1").style.display='none';
				window.opener.document.getElementById("insFre2").style.display='none';
				// Start By Prashant
				
					window.opener.document.getElementById("ltvDesc").style.display='none';
					window.opener.document.getElementById("ltvP").style.display='none';
				
				// End By Prashant
				//$urendra..
					window.opener.document.getElementById("int1").style.display='block';
					window.opener.document.getElementById("int2").style.display='block';
					window.opener.document.getElementById('interestCalc').value=trim(s1[63]);
					window.opener.document.getElementById('showInterestCalc').value=trim(s1[64]);
				//@Surendra
	
					window.opener.document.getElementById("creditPeriod").style.display='none';
					window.opener.document.getElementById("creditPeriod1").style.display='none';
			}
			
			
			window.opener.document.getElementById('dealIrr1').value=trim(s1[43]);
			
			window.opener.document.getElementById('dealIrr2').value=trim(s1[44]);
			window.opener.document.getElementById('iir2').value=trim(s1[44]);
		
			window.opener.document.getElementById('dealEffectiveRate').value=trim(s1[45]);
			window.opener.document.getElementById('sanctionDate').value=trim(s1[46]);
			window.opener.document.getElementById('dealFlatRate').value=trim(s1[47]);
			window.opener.document.getElementById('oneDealOneLoan').value=trim(s1[48]);
			window.opener.document.getElementById('maturityDate1').value=trim(s1[49]);
			window.opener.document.getElementById('repayEffectiveDate').value=trim(s1[50]);
			window.opener.document.getElementById('nextDueDate').value=trim(s1[51]);
			window.opener.document.getElementById('agreementDate').value=trim(s1[52]);
			window.opener.document.getElementById('defaultNextDate').value=trim(s1[51]);
			window.opener.document.getElementById('defaultRepayDate').value=trim(s1[50]);
			
		
			
			if(trim(s1[48])=='Y'){
				
				//window.opener.document.getElementById('loanAmount').setAttribute("readOnly","true");
//				window.opener.document.getElementById('repayEffectiveDate').value="";
				window.opener.document.getElementById('repayEffectiveDate').setAttribute("disabled","true");
//				window.opener.document.getElementById('dueDay').value="";
				window.opener.document.getElementById('mulLoanRepay').style.display="none";
				window.opener.document.getElementById('mulLoanCycle').style.display="none";
				window.opener.document.getElementById('oneLoanRepay').style.display="";
				window.opener.document.getElementById('oneLoanCycle').style.display="";
				window.opener.document.getElementById('repayEffectiveDateOneLoan').value=trim(s1[50]);
				
			
				window.opener.document.getElementById("dueDay").setAttribute("disabled",true);
				window.opener.document.getElementById("dueDayOneLoan").removeAttribute("disabled",true);
				window.opener.document.getElementById('cycleDue2').innerHTML = trim(s1[84]);//Richa HP VAT TAX
		    	window.opener.document.getElementById('dueDayOneLoan').value=trim(s1[42]);
		    	
		    	
				
			}
			
	        window.close();
			}
		}
	}
	function setText(text1,text2) {
		

     
        document.getElementById('leadNo').value = text1;
     
       
 }
function clearLoan(){
	document.getElementById('loanNo').value = '';
	document.getElementById('lbxLoanNoHID').value = '';
	document.getElementById('customerName').value = '';
}	
function validateFormNo()
{
	var contextPath=document.getElementById("contextPath").value;
	var loanDealId=document.getElementById("loanDealId").value;
	var formNo=document.getElementById("formNo").value;
	var address = contextPath+"/ajaxAction.do?method=validateFormNo";
	var data = "loanDealId="+loanDealId+"&formNo="+formNo;
	sendValidateFormNo(address, data);
	return true;
}
function sendValidateFormNo(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultValidateFormNo(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultValidateFormNo(request)
{
	if ((request.readyState == 4) && (request.status == 200)) 
	{	
		var str = request.responseText;
		var s1 = str.split("$:");
		if(trim(s1[1])!='Y')
		{
			alert("Duplicate Application Form No.")
			document.getElementById('formNo').value=''; 
			document.getElementById('validteFormNo').value='N';
		}
		else
		document.getElementById('validteFormNo').value='Y'; 
	}
}

function getFinalRateInCM()
{
	//alert("In getFinalRateInCM");
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var rateMethodType=document.getElementById("rateMethod").value;
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			if(rateMethodType=='L')
			{
				document.getElementById("fixed").setAttribute("disabled",true);
				document.getElementById("fixed").checked=false;
				document.getElementById("floating").removeAttribute("disabled",true);
				document.getElementById("floating").checked=true;				
				//document.getElementById("markUp").removeAttribute("disabled",true);
				//return true;
			}
			else if(rateMethodType=='F')
			{
				document.getElementById("fixed").removeAttribute("disabled",true);
				document.getElementById("fixed").checked=true;
				document.getElementById("floating").setAttribute("disabled",true);
				document.getElementById("floating").checked=false;
				//document.getElementById("type2").removeAttribute("disabled",true);
				//document.getElementById("markUp").removeAttribute("disabled",true);
				//return true;
			}
			document.getElementById("baseRateType").removeAttribute("disabled",true);
			document.getElementById("baseRate").removeAttribute("disabled",true);
			document.getElementById("fixPriod").removeAttribute("readonly",true);
			//document.getElementById("loanNoofInstall").setAttribute("readonly",true);
		/*	var defEffect=0;
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
			document.getElementById("markUp").value=defEffect-baseR; */
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
				document.getElementById("fixed").removeAttribute("disabled",true);
				document.getElementById("fixed").checked=true;
				document.getElementById("floating").setAttribute("disabled",true);
				document.getElementById("floating").checked=false;				
				document.getElementById("baseRateType").value='';
				document.getElementById("baseRate").value='';
				document.getElementById("markUp").value='0.00';
				document.getElementById("effectiveRate").value='0.00';
				document.getElementById("fixPriod").value='';
				//document.getElementById("type1").setAttribute("disabled",true);
				//document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				//document.getElementById("markUp").setAttribute("disabled",true);
				document.getElementById("fixPriod").setAttribute("readonly",true);
				document.getElementById("loanNoofInstall").removeAttribute("readonly",true);			
				//document.getElementById("effectiveRate").value=document.getElementById("defaultFlatRate").value;
				//document.getElementById("markUp").value=document.getElementById("defaultFlatRate").value;
			}
		}
		//code added by neeraj tripathi
		//alert("repaymentType : "+repaymentType);
		//alert("rate : "+rate);
	/*	if(repaymentType !='N')
		{
			if(rate=='F')
			{
				document.getElementById("noOfInstall").removeAttribute("readOnly");
				document.getElementById("noOfInstall").removeAttribute("tabIndex");
			}
			else
			{
			//	var jspTenure = document.getElementById("requestedLoanTenure").value;
				var frequency=document.getElementById("frequency").value;
			/*	if(jspTenure=="")
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
				document.getElementById("noOfInstall").setAttribute("readOnly",true);
				document.getElementById("noOfInstall").setAttribute("tabIndex",-1); 
			}
		} */
		//tripathi's space end
		
	}
}

function getBaseRateInCM()
{
	//alert("In getBaseRateInCM ");
	
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

function getFinalRateInCMOnLoad()
{
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var rateMethodType=document.getElementById("rateMethod").value;
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			if(rateMethodType=='L')
			{
				document.getElementById("fixed").removeAttribute("disabled",true);
				document.getElementById("fixed").checked=false;
				document.getElementById("floating").removeAttribute("disabled",true);
				document.getElementById("floating").checked=true;				
			}
			else if(rateMethodType=='F')
			{
				document.getElementById("fixed").removeAttribute("disabled",true);
				document.getElementById("fixed").checked=true;
				document.getElementById("floating").removeAttribute("disabled",true);
				document.getElementById("floating").checked=false;
			}
			document.getElementById("baseRateType").removeAttribute("disabled",true);
			document.getElementById("baseRate").removeAttribute("disabled",true);
			document.getElementById("fixPriod").removeAttribute("readonly",true);
			//document.getElementById("loanNoofInstall").setAttribute("readonly",true);
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
				document.getElementById("fixed").removeAttribute("disabled",true);
				document.getElementById("fixed").checked=true;
				document.getElementById("floating").setAttribute("disabled",true);
				document.getElementById("floating").checked=false;				
				document.getElementById("baseRateType").value='';
				document.getElementById("baseRate").value='';
				document.getElementById("fixPriod").value='';
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				document.getElementById("fixPriod").setAttribute("readonly",true);
				document.getElementById("loanNoofInstall").removeAttribute("readonly",true);
				//document.getElementById("floating").checked=false;			
			
			}
		}
		
		
	}
}

function calculateInstallmentInCM()
{
	var frequency= document.getElementById("frequency").value;
	var requestedLoanTenure= document.getElementById("tenureMonths").value;
	//var formatD=document.getElementById("formatD").value;
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
	document.getElementById("loanNoofInstall").value=calInsat;
	document.getElementById("interestCompoundingFrequency").value="";
	return calInsat;
}

function getDueDayInCM()
{
	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	var cycleDate=document.getElementById('dueDay').value;
	var contextPath =document.getElementById('contextPath').value ;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	
	if(repayEffectiveDate!='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayDetail";
		var data = "repayEffectiveDate="+repayEffectiveDate+"&cycleDate="+cycleDate;
		sendRequestDueDayInCM(address,data);
		return true;
	}
	else
	{
		alert("Repay Effective Date is required.");	
   	 	return false;
	}
}
function sendRequestDueDayInCM(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodDueDayInCM(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodDueDayInCM(request)
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
				calculateNextDueDateInCM(freq);
			}
			
		   // getElementById('totalRecevable').value = trim(s1[0]);
			//window.close();
	    }
		
	}
}

function calculateNextDueDateInCM(frequency)
{
     
	var frequency= frequency;
	var formatD=document.getElementById("formatD").value;
	var freqMonth=0;
	if(frequency=='M')
	{
		freqMonth=1;
	}
	else 
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
	//	alert(freqMonth);
		parsedFreq=parseInt(freqMonth);
		//alert("parsedFreq:----"+parsedFreq);
	}
	
	var repayEffectiveDate=document.getElementById("nextDueDate").value;
	var d1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
	//alert("Next Due Date: "+repayEffectiveDate+"Month: "+d1.getMonth()+"parsedFreq: "+parsedFreq);
	d1.setMonth(d1.getMonth()+parsedFreq);
	var curr_date = d1.getDate();
	var curr_month = d1.getMonth();
	var curr_year = d1.getFullYear();
	if(curr_month==00)
	{
		curr_month=12;
		curr_year=curr_year-1;
	}
	//alert("curr_date "+padding(curr_date)+"curr_month: "+padding(curr_month)+"curr_year: "+curr_year);
	document.getElementById("nextDueDate").value=padding(curr_date)+formatD.substring(2, 3)+padding(curr_month)+formatD.substring(2, 3)+curr_year;

}

function dealViewerLoanInitiation()
{
	
	   var contextPath=document.getElementById("contextPath").value;

	   var dealId=document.getElementById("lbxDealNo").value;
	   if(dealId!=null && dealId!='')
	   {
		   var url=contextPath+"/loanInitBehindAction.do?method=viewDealLoanInitiation&dealId="+dealId;
		   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		   otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
		   mywindow.moveTo(800,300);
		   if (window.focus) 
		   {
				mywindow.focus();
				//return false;
		   }
	   }
	   else
	   {
		   alert("Deal No is required.");
	   }
	 //  return true;
}

function saveSectorTypeDetail(){
	
	DisButClass.prototype.DisButMethod();
	var dealId=document.getElementById("dealId").value;
	var agriDocs=document.getElementById("agriDocs");
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
 
		 document.getElementById("SectorTypeDetailsForm").action="loanDetailCMProcessAction.do?method=saveSectorTypeDetails";
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

function openPopForSectorDetails(val)
{
	
	var basePath=document.getElementById("contextPath").value;
	var loanDealId=document.getElementById("loanDealId").value;
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
		
		var url=basePath+"/loanDetailCMProcessAction.do?method=openSectorType&dealId="+loanDealId+"&val="+val;
		
		window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

		 return true;
	//}
}
function businessbutton()
{
	var businessType=document.getElementById("businessType").value;
	if(businessType=='CP')
	{
	document.getElementById("partner_mode").style.display="";
	}
else
	document.getElementById("partner_mode").style.display="none";

	}
function getbusinessbutton()
{
	var loanId=document.getElementById("loanId").value;
	/*if(loanId=="")
		{
		alert("Please save the loan first and then select it.");
		document.getElementById("businessType").value="";
		return false;
		}*/
	var businessType=document.getElementById("businessType").value;
	//alert("businessType : "+businessType);
	if(businessType=='CP')
		{
		document.getElementById("partner_mode").style.display="";
		}
	else
		document.getElementById("partner_mode").style.display="none";
	}
/*function getbusinessloan()
{
	var loanId=document.getElementById("loanId").value;
	if(loanId=="")
		{
		alert("Please save the loan first and then select it.");
		document.getElementById("businessType").value="";
		return false;
		}
	return true;
	}*/
function getbusinessType()
{
	/*var loanId=document.getElementById("loanId").value;
	if(loanId=="")
		{
		alert("Please save the loan first and then select it.");
		document.getElementById("businessType").value="";
		return false;
		}
	var basePath=document.getElementById("contextPath").value;
	var loanDealId=document.getElementById("loanDealId").value;
	var businessType=document.getElementById("businessType").value;
	var oldPartnerType=document.getElementById("oldPartnerType").value;
	
	if(businessType!=oldPartnerType){
		alert("Please Save Loan Detail First.");
		return false;
	}
	
	//alert("businessType : "+businessType);
	if(businessType=='CB'|| businessType=='CP')
		{
		 anotherWindows = new Array();
		    curWinAnother = 0;
		
		var url=basePath+"/loanDetailCMProcessAction.do?method=openPartnerDetails&dealId="+loanDealId+"&businessType="+businessType;
		
		window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

		 return true;
		}
	return true;*/
	
	
	var dealId = document.getElementById("loanDealId").value;
	//alert("dealId: "+dealId);
	//alert("dealLoanIds: "+dealLoanIds);
	
	if(dealId=="")
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
	if(businessType=='CP')
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

function savePartnerDetails(){
	
	DisButClass.prototype.DisButMethod();
	var basePath=document.getElementById("contextPath").value;
	var lbxpartnerId=document.getElementById("lbxpartnerId").value; 
	//alert("lbxpartnerId"+lbxpartnerId);
	var loanId=document.getElementById("loanId").value; 
	//alert("loanId"+loanId);
	var effectiveRate=document.getElementById("effectiveRate").value;
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
		effectiveRate="";	
	}
	else if(parseFloat(removeComma(document.getElementById("effectiveRate").value))>100.00 ||  parseFloat(removeComma(document.getElementById("effectiveRate").value))<0.00)
		{
		alert('Effective rate should be 0 to 100 ');
		document.getElementById("effectiveRate").value='';
		document.getElementById("effectiveRate").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	
//	if(fpr>100.00 || fpr<0.00)
//		{
//		alert('Flate rate should be 0 to 100 ');
//		document.getElementById("partnerRate").focus();
//		DisButClass.prototype.EnbButMethod();
//		return false;
//		}
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
	if(lbxpartnerId =="" || loanId =="" ||  (effectiveRate =="" && rateType!="F") || partnerPercentage ==""  || partnerAmount=="" || (rateType=="F"  && partnerRate==""))
		{
		if(lbxpartnerId =="")
			{
			alert("Please select Partner Name.");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
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
		document.getElementById("partnerTypeDetailsForm").action="loanDetailCMProcessAction.do?method=savePartnerDetails&id="+lbxpartnerId+"&servicingPartnerFlag="+servicingFlagValue+"&leadPartnerFlag="+leadPartnerFlagValue;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("partnerTypeDetailsForm").submit();
		return true;
		}
}
function getPartnerDtl(id)
{
	
	var contextPath= document.getElementById("contextPath").value;
	document.getElementById("partnerTypeDetailsForm").action=contextPath+"/loanDetailCMProcessAction.do?method=getPartnerDetails&id="+id;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("partnerTypeDetailsForm").submit();
	
	}

function calculateAmount()
{
 	var loanAmount=document.getElementById('loanAmount').value;
 	var partnerName=document.getElementById('partnerName').value;
 	var partnerPercentage=document.getElementById('partnerPercentage').value;
 	var msg="",partnerLoanAmt=0;
 	if(partnerName==""|| partnerPercentage==""){
 		if(partnerName=="")
 			msg=msg+"*Plaese Select Partener Name\n";
 		if(partnerPercentage=="")
 			msg=msg+"*Partner Percentage is mandatory\n";
 		
 		alert(msg);
 		
 		if(msg.match("Plaese Select"))
			document.getElementById("partnerName").focus();
		else if(msg.match("Partner Percentage"))
			document.getElementById("partnerPercentage").focus();
		return false;
 	}else{
 		partnerLoanAmt=(loanAmount*partnerPercentage)/100;
 		document.getElementById('partnerAmount').value=partnerLoanAmt;
 	}
 	return true;
}

function calculatePercentage()
{
 	var loanAmount=document.getElementById('loanAmount').value;
 	var partnerName=document.getElementById('partnerName').value;
 	var partnerAmount=document.getElementById('partnerAmount').value;
 	var msg="",partnerPercentage=0.0000;
 	if(partnerName==""){
 		if(partnerName=="")
 			msg=msg+"*Plaese Select Partener Name\n";
 		document.getElementById('partnerAmount').value='';
 		alert(msg);
 		if(msg.match("Plaese Select"))
			document.getElementById("partnerName").focus();
		return false;
 	}
	else if(partnerAmount=="")
 		{
 		document.getElementById('partnerAmount').value='';
 		alert("Please Select Partner Amount.");
		return false;
 		}
	else if(parseFloat(removeComma(partnerAmount))>parseFloat(removeComma(loanAmount)))
 		{
 		document.getElementById('partnerAmount').value='';
 		alert("Partner Amount should be equal or smaller than Loan Amount.");
		return false;
 		}
 	else 
 		{
		partnerAmount=parseFloat(removeComma(partnerAmount));
		loanAmount=parseFloat(removeComma(loanAmount));
 		partnerPercentage=(partnerAmount*100)/loanAmount;
 		partnerPercentage=Math.round(partnerPercentage * 100) / 100;
 		if(partnerPercentage>100)
 			{
 	 		document.getElementById('partnerAmount').value='';
 	 		document.getElementById('partnerPercentage').value='';
 			alert("Partner Percentage should be greater than Loan Amount.");
 			return false;
 			}
 		else{
 		document.getElementById('partnerPercentage').value=partnerPercentage.toFixed(2);
 		}
 		}
 	return true;
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
					document.getElementById("partnerTypeDetailsForm").action=contextPath+"/loanDetailCMProcessAction.do?method=deletePartnerDetails";
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
	function allChecked()
	{
		//alert("ok");
		var c = document.getElementById("allchk").checked;
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
	
	
	
	function checkBusinessPartner()
	{
		var servicingPartnerFlag = document.getElementById("servicingPartnerFlag");
		var leadPartnerFlag = document.getElementById("leadPartnerFlag");
		var servicingFlagValue  = "N";
		if((servicingPartnerFlag.checked==true) || (servicingPartnerFlag.checked==false && leadPartnerFlag.checked==false))
		{
			document.getElementById('effectiveRate').readOnly = true;
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
			document.getElementById('effectiveRate').readOnly = true;
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
			
			function enableDisableFieldForRsp(){
				var leadPartnerFlag = document.getElementById("leadPartnerFlag");
				if(leadPartnerFlag.checked==true)
				{
			  		document.getElementById('displayField').style.display='';
			  		document.getElementById('displayField1').style.display='';
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
					document.getElementById('effectiveRate').readOnly = true;
			  		document.getElementById('displayField').style.display='none';
			  		document.getElementById('displayField1').style.display='none';
			  		document.getElementById('rateType').value='';
			  		document.getElementById('partnerRate').value='';
			  		document.getElementById('businessType').value='';
			  		
			  		
				}

				
			}			
	
			function getPartnerDtlForRsp(id)
			{
				
				var contextPath= document.getElementById("contextPath").value;
				document.getElementById("partnerTypeDetailsForm").action=contextPath+"/repaymentServiceActionForPartnerDetail.do?method=getPartnerDetailsForRsp&id="+id;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("partnerTypeDetailsForm").submit();
				
				}
			
			function deletePartnerDetailForRsp()
			{
					DisButClass.prototype.DisButMethod();
				    if(check())
				    {
				    	if(confirm("Do you want to delete the Record(s) ?"))
				    	{	
						
				    		var contextPath= document.getElementById("contextPath").value;
							document.getElementById("partnerTypeDetailsForm").action=contextPath+"/repaymentServiceActionForPartnerDetail.do?method=deletePartnerDetailsForRsp";
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
			
			function savePartnerDetailsForRsp(){
				
				DisButClass.prototype.DisButMethod();
				var basePath=document.getElementById("contextPath").value;
				var lbxpartnerId=document.getElementById("lbxpartnerId").value; 
				//alert("lbxpartnerId"+lbxpartnerId);
				var loanId=document.getElementById("loanId").value; 
				//alert("loanId"+loanId);
				var effectiveRate=document.getElementById("effectiveRate").value;
				//alert("partnerRate"+partnerRate);
				var partnerPercentage=document.getElementById("partnerPercentage").value;
				var partnerAmount=document.getElementById("partnerAmount").value;
				var servicingPartnerFlag = document.getElementById("servicingPartnerFlag");
				var leadPartnerFlag = document.getElementById("leadPartnerFlag");
				var businessType = "";
				var servicingFlagValue  = "N";
				var leadPartnerFlagValue  = "N";
				var rateType="";
				var partnerRate="";
				//alert("partnerAmount"+partnerAmount);
				
				//add by saorabh 
//			 	var fpr = parseFloat(partnerRate); // fpr =parseFloatRate
				if(document.getElementById("businessType")==null || document.getElementById("rateType")==businessType){
					businessType="";	
				}
				else{
					businessType=document.getElementById("businessType").value;
				}
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
					effectiveRate="";	
				}
				else if(parseFloat(removeComma(document.getElementById("effectiveRate").value))>100.00 ||  parseFloat(removeComma(document.getElementById("effectiveRate").value))<0.00)
					{
					alert('Effective rate should be 0 to 100 ');
					document.getElementById("effectiveRate").value='';
					document.getElementById("effectiveRate").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
					}
				
//				if(fpr>100.00 || fpr<0.00)
//					{
//					alert('Flate rate should be 0 to 100 ');
//					document.getElementById("partnerRate").focus();
//					DisButClass.prototype.EnbButMethod();
//					return false;
//					}
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
				if(lbxpartnerId =="" || loanId =="" ||  (effectiveRate =="" && rateType!="F") || partnerPercentage ==""  || partnerAmount=="" || (rateType=="F"  && partnerRate==""))
					{
					if(lbxpartnerId =="")
						{
						alert("Please select Partner Name.");
						DisButClass.prototype.EnbButMethod();
						return false;
						}
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
					document.getElementById("partnerTypeDetailsForm").action=basePath+"/repaymentServiceActionForPartnerDetail.do?method=savePartnerDetailsForRsp&id="+lbxpartnerId+"&servicingPartnerFlag="+servicingFlagValue+"&leadPartnerFlag="+leadPartnerFlagValue+"&businessType="+businessType;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("partnerTypeDetailsForm").submit();
					return true;
					}
			}
			
			function forwordPartnerDetailsForRsp()
			{
							DisButClass.prototype.DisButMethod();
		
				    		var contextPath= document.getElementById("contextPath").value;
							document.getElementById("partnerTypeDetailsForm").action=contextPath+"/repaymentServiceActionForPartnerDetail.do?method=forwardPartnerDetailsForRsp";
						 	document.getElementById("processingImage").style.display = '';
							document.getElementById("partnerTypeDetailsForm").submit();
				    	
				
			}		
function saveLoanInCMForEdit()
{
	     DisButClass.prototype.DisButMethod();
		 var msg1='',msg2='';		
 
		 var assetFlag= document.getElementById("assetFlag").value;
		 var loanAmount=removeComma(document.getElementById("loanAmount").value);
		 var sectorType=document.getElementById("sectorType").value;
		 var formNo=document.getElementById("formNo").value;


		 if(sectorType=='')
			 msg1='* Sector Type is required \n';
		 if(formNo=='')
			 msg2='* Application Form No is required \n';

		 
		 if(sectorType==''||formNo=='')
		 {
			 alert(msg1+""+msg2);
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
		if ((repaymentType != '' && repaymentType == 'I') && (assetFlag != '' && assetFlag != 'N') ){ 
			if((document.getElementById("assetCost").value) == '')
			{
				alert("* Asset Cost is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if((document.getElementById("noOfAsset").value) == '')
			{
				alert("* No of Asset is Required.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		
		if ((assetFlag != '' && assetFlag == 'A') && ((document.getElementById("assetCost").value) != '')) {
			
			var marginAmt = removeComma(document.getElementById("marginAmount").value);
			var assetCost = removeComma(document.getElementById("assetCost").value);
			var marginPrec = 0.00;
			marginPrec = removeComma(document.getElementById("margin").value);
			var noOfAsset = document.getElementById("noOfAsset").value;
			
//			if ((marginAmt > 0) && (marginPrec == 0)) {
//				alert("Margin % can't be zero.");
//				document.getElementById("saveButton").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//			}
			
			if (loanAmount>assetCost) {
				alert("Loan Amount should be equal or less than to Asset Cost");
				document.getElementById("assetCost").value= 0;
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if ((removeComma(document.getElementById("marginAmount").value) + removeComma(document.getElementById("loanAmount").value)) != assetCost)
		    {
				alert("Sum of Margin Amount and Loan Amount should be equal to Asset Cost");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			
			/*if (noOfAsset<1) {
				alert("Number of Assets should not be less than One.");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}*/

		}
		
	var basePath=document.getElementById("basePath").value;
	document.getElementById("sourcingForm").action = basePath+"/editLoanDetailAction.do?method=editLoanSave";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("sourcingForm").submit();
	 return true;	
}

function calMarginPercentage()
{
	
	//alert(iRate);
	
	var assetAmount=document.getElementById("assetCost").value;
	var reqLoanAmt=removeComma(document.getElementById("loanAmount").value);
	if(assetAmount!='' && document.getElementById("marginAmount").value!='')
	{
		//alert("In if "+assetAmount);
		var iAssetAmount=removeComma(assetAmount);
		var iMargingAmount=removeComma(document.getElementById("marginAmount").value);
		
		if(iAssetAmount<iMargingAmount) 
		{
			alert("Margin Amount should be equal or less than to Asset Cost");
			document.getElementById("marginAmount").value='0';
			document.getElementById("margin").value='0';
			return false;
		}
		
		document.getElementById("margin").value=roundNumber((iMargingAmount*100)/reqLoanAmt, 2);
		document.getElementById("assetCost").value = (iAssetAmount - removeComma(document.getElementById("marginAmount").value));
		if(document.getElementById("assetCost").value<reqLoanAmt)
		{
			alert("Loan Amount should be equal or less than to Asset Cost");
			document.getElementById("assetCost").value='0';
			return false;
		}

		
	}
	else
	{
	//	alert("In else "+assetAmount);
		alert("* Asset Cost is Required.")
		document.getElementById("marginAmount").value='0';
		document.getElementById("margin").value='0';
	}
	
}	

function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}
function sumRequestedAmt1()
{
	var requestedLoamt=document.getElementById("requestedLoamt").value;
	var requestedLoanAmount=document.getElementById("loanAmount").value;
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
	document.getElementById("loanAmount").value=amount;
	
}
function calTenureMonthForMaturityDate()
{
	   //alert("sacin");                             
	   var requestedLoanTenure= document.getElementById("tenureMonths").value;  // change  by vijendra tenureMonths ->requestedLoanTenure
	   var daysBasis= document.getElementById("daysBasis").value;
	   var formatD=document.getElementById("formatD").value;
	   //var installmentType= document.getElementById("installmentType").value;	
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
			//alert("under a"+daysBasis);
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
			{
			  seprator = '/';
			  UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			}
			else
			{
				seprator = '/';
				UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			}
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
			var _MS_PER_DAY = 1000 * 60 * 60 * 24*30.42;
			//a and b are javascript Date objects				
			//test it
			var a = new Date(userDefinedMaturityDate);
			var b = new Date(CurrentDate);
			var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
			var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
			var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
			var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
			var finalMonth=Math.abs(difference)+1; 
			//document.getElementById("tenureInDay").value=finalDays;
			//document.getElementById("tenureMonths").value=finalMonth; // change  by vijendra tenureMonths ->requestedLoanTenure
			//document.getElementById("loanNoofInstall").value=finalMonth;	// uncommented on 9-8-18	
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
			//changes for flexibility date ||20-08-2018
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
					document.getElementById("tenureMonths").value=finalMonth;//+1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30.42;
				}
				else
				{
					var tenureDay = document.getElementById("tenureInDay").value;
					var tempMonth = oldMonth - newMonth;
					//alert("tempMonth "+tempMonth);
					var calc = Math.floor(tenureDay/30.42)-tempMonth;
					//alert("calc "+calc);
					document.getElementById("tenureMonths").value=calc-tempMonth;
					document.getElementById("loanNoofInstall").value=calc-tempMonth;
					var newTempMonth = document.getElementById("loanNoofInstall").value;
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
					document.getElementById("tenureMonths").value=finalMonth;//+1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30.42;
				}
				else
				{
					var a = document.getElementById("tenureMonths").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					//alert("tempTenure "+c);
					document.getElementById("tenureMonths").value=c;
					document.getElementById("loanNoofInstall").value=c;	
					var newTempMonth2 = document.getElementById("loanNoofInstall").value;
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
			
				
				document.getElementById("tenureMonths").value=finalMonth;
				var temp123 = document.getElementById("tenureMonths").value;
				document.getElementById("loanNoofInstall").value=finalMonth;
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
						
						var finalMonth=Math.abs(difference)-2;
						
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
				document.getElementById("tenureMonths").value=finalMonth+1;
				var temp123 = document.getElementById("tenureMonths").value;
				document.getElementById("loanNoofInstall").value=finalMonth+1;
				document.getElementById("tenureInDay").value=temp123*30.42;
			}
			//changes end here
			var finalDays2 = Math.floor(document.getElementById("tenureMonths").value*30.42);			
			document.getElementById("tenureInDay").value=finalDays2+result;
			//temp end here
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
			//a and b are javascript Date objects	
			// Discard the time and time-zone information.
			//test it
			var a = new Date(userDefinedMaturityDate);
			var b = new Date(CurrentDate);
			var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
			var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
			var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
			var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
			var finalMonth=Math.abs(difference)+1; 
			//document.getElementById("tenureInDay").value=finalDays; // commented on 9-8-18
			//document.getElementById("tenureMonths").value=finalMonth;// change  by vijendra tenureMonths ->requestedLoanTenure //  commented on 9-8-18
			var loanTenure = document.getElementById("tenureMonths").value;
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
			//document.getElementById("tenureInDay").value=res+res2; //commented on 29-8-2018
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
					document.getElementById("tenureMonths").value=finalMonth;//+1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30;
				}
				else
				{
					var tenureDay = document.getElementById("tenureInDay").value;
					var tempMonth = oldMonth - newMonth;
					//alert("tempMonth "+tempMonth);
					var calc = Math.floor(tenureDay/30)-tempMonth;
					//alert("calc "+calc);
					document.getElementById("tenureMonths").value=calc;//-tempMonth;
					document.getElementById("loanNoofInstall").value=calc;//-tempMonth;
					var newTempMonth = document.getElementById("loanNoofInstall").value;
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
					document.getElementById("tenureMonths").value=finalMonth;//+1;
					var temp123 = document.getElementById("tenureMonths").value;
					document.getElementById("loanNoofInstall").value=finalMonth;//+1;
					document.getElementById("tenureInDay").value=temp123*30;
				}
				else
				{
					var a = document.getElementById("tenureMonths").value;				
					var b = newMonth-oldMonth;
					var c = parseInt(a) + parseInt(b);
					//alert("tempTenure "+c);
					document.getElementById("tenureMonths").value=c;
					document.getElementById("loanNoofInstall").value=c;	
					var newTempMonth2 = document.getElementById("loanNoofInstall").value;
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
			
				
				document.getElementById("tenureMonths").value=finalMonth;
				var temp123 = document.getElementById("tenureMonths").value;
				document.getElementById("loanNoofInstall").value=finalMonth;
				document.getElementById("tenureInDay").value=temp123*30;		
			}
			else if(newYear != oldYear)
			{
				//alert("under the 2345678 ");				
				var finalMonth=Math.abs(difference);
				if(finalMonth >= 12)
				{
					if(finalMonth >= 72 && finalMonth <= 120)
					{
						
						var finalMonth=Math.abs(difference)-2;
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
				document.getElementById("tenureMonths").value=finalMonth+1;
				var temp123 = document.getElementById("tenureMonths").value;
				document.getElementById("loanNoofInstall").value=finalMonth+1;
				document.getElementById("tenureInDay").value=temp123*30;
			}
			//temp end here
	   	}
}


function calculateMaturityDateInLoan()
{
	//alert("In new function");
	var repaymentType = document.getElementById('repaymentType').value;
	var installmentType= document.getElementById('installmentType').value;
	var dueDay =  document.getElementById('dueDay').value;
	//alert("dueDay "+dueDay);	
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
		if(installmentType=='S')
		{
			var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
			var y= parseInt(document.getElementById('tenureInDay').value); //or whatever offset
			var daysBasis= document.getElementById("daysBasis").value;
			var requestedLoanTenure  = parseInt(document.getElementById('tenureMonths').value);
			var nextDueDate = document.getElementById('nextDueDate').value;
			//alert ("nextDueDate "+nextDueDate);
			//alert("tenure"+x);
			//alert("tenure in day"+y);
			var formatD=document.getElementById("formatD").value;
			//alert(x);
			//var currDate =   document.getElementById('repayEffectiveDate').value; // commented on 22-0-2018
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
			{
				seprator = '/';			  
				UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			}
		  else
			{
			  seprator = '/';
				UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			}
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
					document.getElementById('tenureMonths').value=x-1;
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
					
					var noOfInstall = document.getElementById("loanNoofInstall").value; //= "noOfInstallDF";
					//alert("before change noOfInstall    : "+noOfInstall);
					document.getElementById("loanNoofInstall").value=x-1;
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
				//alert("first "+daysBasis);	
				document.getElementById('maturityDate1').value=ModDateStr;	  
				//document.getElementById('maturityDate').value=ModDateStr;		  
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
			var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
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
			{
				seprator = '/';
				UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			}
			else
			{
				seprator = '/';
			    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
			   // alert(UpdateDateStr);
			 }
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
function calcDay()
{
	//alert("sacin");
	var requestedLoanTenure= document.getElementById("tenureMonths").value;
	var daysBasis= document.getElementById("daysBasis").value;
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
		//alert("under a"+daysBasis);
	   	var day=requestedLoanTenure*30.42;
		day=Math.floor(day);
		document.getElementById("tenureInDay").value=day.toFixed(0); 
	}
	else
	{
	   	var day=requestedLoanTenure*30;
	   	document.getElementById("tenureInDay").value=day;
	}
}
function validateMaturityDate()
{
	//alert("in function ");
	var repaymentType = document.getElementById('repaymentType').value;
	var installmentType= document.getElementById('installmentType').value;
	var dueDay =  document.getElementById('dueDay').value;
	//var OldMaturityDate= document.getElementById('OldMaturityDate').value;
	var UpdateDateStr1;	
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{	
		var x = parseInt(document.getElementById('tenureMonths').value); //or whatever offset
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
			}
			*/
			
	}
		  
	
}
function setMaturityTemp()
{
	//alert("in temp function");		
	var maturityDate=document.getElementById('maturityDate1').value;	 
	if(maturityDate!= null ||maturityDate!= "")
	{
		document.getElementById('OldMaturityDate1').value=maturityDate;
	}
	document.getElementById('OldMaturityDate1').value=maturityDate;
	//  document.getElementById('maturityDate').value=ModDateStr;		  
	//  document.getElementById('OldMaturityDate1').value=ModDateStr;	
}
function calTenureMonthForMaturityDateNextDueDate()
{
	//alert("sachin456");
	var nextDueDate= document.getElementById("nextDueDate").value;
	// var daysBasis= document.getElementById("daysBasis").value;
	var formatD=document.getElementById("formatD").value;
	//var installmentType= document.getElementById("");
	var repayEffDate =   document.getElementById('repayEffectiveDate').value; // added on 22-08-2018
	var repayEffMonth = repayEffDate.substring(3,5); // added on 22-08-2018	   	
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
	{
		 seprator = '/';
		UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
	}
	else
	{
		 seprator = '/';
	    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
	}
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
	var b = new Date(CurrentDate);
	var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
	var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
	var finalDays=Math.abs((utc2 - utc1)/(1000 * 60 * 60 * 24));
	var difference = Math.floor((utc2 - utc1) / _MS_PER_DAY);
	var finalMonth=Math.abs(difference) 
	document.getElementById("tenureInDay").value=finalDays;
	//alert("in else");
	document.getElementById("tenureMonths").value=finalMonth;
	document.getElementById("loanNoofInstall").value=finalMonth;
	//brijesh start here 22-08-2018
	if(currMonth > repayEffMonth && finalMonth > 72)
	{
		//alert("new alert");
		//document.getElementById("tenureInDay").value=finalDays;
		document.getElementById("tenureMonths").value=finalMonth-1;
		document.getElementById("loanNoofInstall").value=finalMonth-1;
	}
	else if (currMonth == repayEffMonth && finalMonth>72)
	{
		//alert("new alert else");
		//document.getElementById("tenureInDay").value=finalDays;
		document.getElementById("tenureMonths").value=finalMonth-1;
		document.getElementById("loanNoofInstall").value=finalMonth-1;
	}		
	//end here 22-08-2018
}

