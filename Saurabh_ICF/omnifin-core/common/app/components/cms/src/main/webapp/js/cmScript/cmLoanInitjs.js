
var global=0;
function checkRate(val)
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
//method added by neraj tripathi
function recoveryPercent()
{//alert("recoveryPercent");
	var installmentType=document.getElementById('installmentType').value;
	var recoveryType=document.getElementById('recoveryType').value;
	//alert("installmentType  : "+installmentType);
	//alert("recoveryType  : "+recoveryType);
	if((installmentType=='E'||installmentType=='P')&& recoveryType=='P')
	document.getElementById('recoveryPercen1').value="100";	
}
function productPopUp(){
	 
	var dealNo=document.getElementById("lbxDealNo").value;
	// alert(dealNo);
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

function allocationDetails()
		{
			var id=document.getElementsByName("radioId");
			var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
			var loanIDMain ='';			 
			var sourcepath=document.getElementById("contextPath").value;			   
			for(var i=0; i< id.length ; i++)
			{				   
			   if(id[i].checked == true){					   
			   loanIDMain = loanid[i].value;					   	   
				   }				   
			}			
			document.getElementById("searchForCMForm").action=sourcepath+"/searchCMBehindAction.do?method=generateAllocationDetail&report=ad&loanId="+loanIDMain;
			document.getElementById("searchForCMForm").submit();	   	
		}

function saveRecord()
{
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
	 {// alert("ok saveRecord");
		 var basePath=document.getElementById("basePath").value;
	     var address = basePath+"/ajaxAction.do?method=retriveValueByProduct";
	     // alert("ok saveRecord"+address);
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
     
			var s1 = str.split("$:");
		    // alert("s2 "+removeComma(trim(s1[2]))+" S3
			// "+removeComma(trim(s1[3]))+" s2-s3
			// "+(removeComma(trim(s1[2]))-removeComma(trim(s1[3]))));
		
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
			window.opener.document.getElementById('utilizeLoanAmount').value=trim(s1[3])
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
			
			// alert(s1[3]);
			
			if(trim(s1[10])=='E')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				window.opener.document.getElementById('showRateType').value='EFFECTIVE RATE';
				window.opener.document.getElementById('fixed').checked=true;	
				
			}
			else if(trim(s1[10])=='F')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				window.opener.document.getElementById('showRateType').value='FLAT RATE';
				window.opener.document.getElementById('fixed').checked=true;	
				// window.opener.document.getElementById("fixed").setAttribute("disabled",true);
			}
			else if(trim(s1[10])=='P')
			{
				window.opener.document.getElementById('rateType').value=trim(s1[10]);
				window.opener.document.getElementById('showRateType').value='Per Thousands Per Month';
			}
			
			if(trim(s1[11])=="F"){
				window.opener.document.getElementById('fixed').checked=true;	
			}else if(trim(s1[11])=="L")
			{
				window.opener.document.getElementById('floating').checked=true;
			}
			window.opener.document.getElementById('baseRateType').value=trim(s1[12]);
			window.opener.document.getElementById('baseRate').value=trim(s1[13]);
			window.opener.document.getElementById('markUp').value=trim(s1[14]);
			window.opener.document.getElementById('effectiveRate').value=trim(s1[15]);
			
				
			if(trim(s1[16])=='I')
			{
				window.opener.document.getElementById('repaymentType').value=trim(s1[16]);
				window.opener.document.getElementById('showRepaymentType').value='INSTALLMENT';
				window.opener.document.getElementById('nddheader').style.display='';
				window.opener.document.getElementById('ndd').style.display='';
				window.opener.document.getElementById('effDateHeader').style.display='';
				window.opener.document.getElementById('effDate').style.display='';
				window.opener.document.getElementById('cycleDate1').style.display='';
				window.opener.document.getElementById('cycleDate2').style.display='';
				
			}
			else if(trim(s1[16])=='N')
			{
				window.opener.document.getElementById('repaymentType').value=trim(s1[16]);
				window.opener.document.getElementById('showRepaymentType').value='NON-INSTALLMENT';
				window.opener.document.getElementById('nddheader').style.display='none';
				window.opener.document.getElementById('ndd').style.display='none';
				window.opener.document.getElementById('effDateHeader').style.display='none';
				window.opener.document.getElementById('effDate').style.display='none';
				window.opener.document.getElementById('cycleDate1').style.display='none';
				window.opener.document.getElementById('cycleDate2').style.display='none';
			}
			
			if(trim(s1[17])=='E')
			{
				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
				window.opener.document.getElementById('showInstallmentType').value='Eq. INSTALLMENT';
				
			}
			else if(trim(s1[17])=='G')
			{
				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
				window.opener.document.getElementById('showInstallmentType').value='Gr. INSTALLMENT';
			}
			if(trim(s1[17])=='P')
			{
				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
				window.opener.document.getElementById('showInstallmentType').value='Eq. PRINCIPAL';
				
			}
			else if(trim(s1[17])=='Q')
			{
				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
				window.opener.document.getElementById('showInstallmentType').value='Gr. PRINCIPAL1';
			}
			else if(trim(s1[17])=='R')
			{
				window.opener.document.getElementById('installmentType').value=trim(s1[17]);
				window.opener.document.getElementById('showInstallmentType').value='Gr. PRINCIPAL2';
			}
		
			if(trim(s1[18])=='M')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				window.opener.document.getElementById('showFrequency').value='MONTHLY';
				
			}
			else if(trim(s1[18])=='B')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				window.opener.document.getElementById('showFrequency').value='BIMONTHLY';
			}
			else if(trim(s1[18])=='Q')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				window.opener.document.getElementById('showFrequency').value='QUARTERLY';
			}
			else if(trim(s1[18])=='H')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				window.opener.document.getElementById('showFrequency').value='HALFYERALY';
			}
			else if(trim(s1[18])=='Y')
			{
				window.opener.document.getElementById('frequency').value=trim(s1[18]);
				window.opener.document.getElementById('showFrequency').value='YEARLY';
			}
			// alert(trim(s1[19]))
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
			if(trim(s1[19])=='O')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='OTC';
				
			}
			else if(trim(s1[19])=='D')
			{
				window.opener.document.getElementById('repaymentMode').value=trim(s1[19]);
				window.opener.document.getElementById('showRepaymentMode').value='DIRECT DEBIT';
			}
			
			// alert(s1[22]);
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
			
			// window.opener.document.getElementById('dealLoanId').value=trim(s1[20]);
			// window.opener.document.getElementById('dealNo').value=trim(s1[21]);
			window.opener.document.getElementById('loanBranch').value=trim(s1[25]);
			window.opener.document.getElementById('loanIndustry').value=trim(s1[26]);
			window.opener.document.getElementById('loanSubIndustry').value=trim(s1[27]);
			window.opener.document.getElementById('loanCustomerId').value=trim(s1[28]);
			window.opener.document.getElementById('loanCustomerType').value=trim(s1[29]);
			window.opener.document.getElementById('loanCustomerExisting').value=trim(s1[30]);
			// alert(trim(s1[31]));
			window.opener.document.getElementById('showProduct').value=trim(s1[31]);
			window.opener.document.getElementById('showScheme').value=trim(s1[32]);
			
			
			window.opener.document.getElementById('productCat').value=trim(s1[33]);
			// alert(trim(s1[35]));
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
				window.opener.document.getElementById('dueDay').value=trim(s1[42]);
				//window.opener.document.getElementById("typeOfDisbursal").removeAttribute("disabled",true);
				//window.opener.document.getElementById("noOfDisbursalText").removeAttribute("disabled",true);
				window.opener.document.getElementById("asset1").style.display='';
				window.opener.document.getElementById("asset2").style.display='';
				window.opener.document.getElementById("assetCT1").style.display='';
				window.opener.document.getElementById("assetCT2").style.display='';
				window.opener.document.getElementById("marg1").style.display='';
				window.opener.document.getElementById("marg2").style.display='';
				window.opener.document.getElementById("margAmount1").style.display='';
				window.opener.document.getElementById("margAmount2").style.display='';
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
				window.opener.document.getElementById("tyeOfDis1").style.display='';
				window.opener.document.getElementById("tyeOfDis2").style.display='';
				window.opener.document.getElementById("noOfDis1").style.display='';
				window.opener.document.getElementById("noOfDis2").style.display='';
				window.opener.document.getElementById("pdo1").style.display='';
				window.opener.document.getElementById("pdo2").style.display='';
				window.opener.document.getElementById("repaySmk1").style.display='';
				window.opener.document.getElementById("repaySmk2").style.display='';
				window.opener.document.getElementById("insuranceBy1").style.display='';
				window.opener.document.getElementById("insuranceBy2").style.display='';
			}
			else 	if(trim(s1[16])=='N')
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
				window.opener.document.getElementById("tyeOfDis1").style.display='none';
				window.opener.document.getElementById("tyeOfDis2").style.display='none';
				window.opener.document.getElementById("noOfDis1").style.display='none';
				window.opener.document.getElementById("noOfDis2").style.display='none';
				window.opener.document.getElementById("pdo1").style.display='none';
				window.opener.document.getElementById("pdo2").style.display='none';
				window.opener.document.getElementById("repaySmk1").style.display='none';
				window.opener.document.getElementById("repaySmk2").style.display='none';
				window.opener.document.getElementById("insuranceBy1").style.display='none';
				window.opener.document.getElementById("insuranceBy2").style.display='none';
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
				
				window.opener.document.getElementById('loanAmount').setAttribute("readOnly","true");
// window.opener.document.getElementById('repayEffectiveDate').value="";
				window.opener.document.getElementById('repayEffectiveDate').setAttribute("disabled","true");
// window.opener.document.getElementById('dueDay').value="";
				window.opener.document.getElementById('mulLoanRepay').style.display="none";
				window.opener.document.getElementById('mulLoanCycle').style.display="none";
				window.opener.document.getElementById('oneLoanRepay').style.display="";
				window.opener.document.getElementById('oneLoanCycle').style.display="";
				window.opener.document.getElementById('repayEffectiveDateOneLoan').value=trim(s1[50]);
				window.opener.document.getElementById('dueDayOneLoan').value=trim(s1[42]);
			}
			
	        window.close();
			}
		}
	}
	function setText(text1,text2) {
		

     
        document.getElementById('leadNo').value = text1;
     
       
 }
function retrieveValue()
{
	var basePath=document.getElementById("basePath").value;
	
     document.getElementById("productPopUpForm").action = basePath+"/loanDetailCMProcessAction.do?method=retriveValueByProduct";
     document.getElementById("productPopUpForm").submit();

	 return true;
 
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





function enableDisbursal(){
	
	if(document.getElementById("typeOfDisbursal").value=="M")
	{
		document.getElementById("disId").style.display="";
		document.getElementById("disSingle").style.display="none";
		
	}
  if(document.getElementById("typeOfDisbursal").value=="S")
  {
		document.getElementById("disId").style.display="none";
		document.getElementById("disSingle").style.display="";
		// document.getElementById("noOfDisbursal").value='1';
	
	}
	 
}
	
//function saveLoanInCM()
//{
//	var val = document.getElementById('showRepaymentType').value;
//	var dealNo=document.getElementById("dealNo").value;
//	var showProduct=document.getElementById("showProduct").value;
//	var msg5='',msg6='';
//	
//	if(dealNo=='' || showProduct==''){
//	if(dealNo==''){
//		 msg5='* Deal No. is required \n';
//		 count=1;
//	 }else{
//		 msg5='';
//		}
//	 if(showProduct==''){
//		 msg6='* Product is required \n';
//		 count=1;
//	 }else{
//		 msg6='';
//	 }
//	 
//	 if(msg5 != ''){
//		 document.getElementById("dealButton").focus();
//	 }else if(msg6 != ''){
//		 document.getElementById("pbutton").focus();
//	 }
//	 
//	 alert(msg5+msg6);
//	 return false;
//	 
//	}
//	
//	if(val=='INSTALLMENT')
//	{
//		var nextDueDate=document.getElementById("nextDueDate").value;
//		var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
//		var nextDate="";
//		var repay="";
//		var dueDay="";
//		if(oneDealOneLoan=="N")
//		{
//			rePayDate=document.getElementById("repayEffectiveDate").value;
//			dueDay=document.getElementById("dueDay").value;
//		}
//		if(oneDealOneLoan=="Y")
//		{
//			rePayDate=document.getElementById("repayEffectiveDateOneLoan").value;
//			dueDay=document.getElementById("dueDayOneLoan").value;
//		}
//		if(nextDueDate=="")
//		{
//			alert("Next Due Date is required.");
//			document.getElementById("saveButton").removeAttribute("disabled","true");
//			return false;
//		}
//		var day2= nextDueDate.substring(0,2);
//		if(parseFloat(day2) != parseFloat(dueDay))
//		{
//			alert("Next Due Date must be equal to Due Day ie."+dueDay);
//			document.getElementById("saveButton").removeAttribute("disabled","true");
//			return false;
//		}
//		if(rePayDate=="")
//		{
//			alert("Repay Effective Date is required.");
//			document.getElementById("saveButton").removeAttribute("disabled","true");
//			return false;
//		}
//		var day1= rePayDate.substring(0,2);
//	
//		var month1=rePayDate.substring(3,5);
//		var month2=nextDueDate.substring(3,5);
//		var year1=rePayDate.substring(6);
//		var year2=nextDueDate.substring(6);		
//		if(parseFloat(year1)>parseFloat(year2))
//		{	
//			alert("Next Due Date should be greater than Repay Effective Date");
//			document.getElementById("saveButton").removeAttribute("disabled","true");
//			return false;
//		}
//		else
//		{
//			if(parseFloat(year1)==parseFloat(year2))
//			{
//				if(parseFloat(month1)>parseFloat(month2))
//				{	
//					alert("Next Due Date should be greater than Repay Effective Date");
//					document.getElementById("saveButton").removeAttribute("disabled","true");
//					return false;
//				}
//				else 
//				{
//					if(parseFloat(month1)==parseFloat(month2))
//					{
//						if(parseFloat(day1)>=parseFloat(day2))
//						{	
//							alert("Next Due Date should be greater than Repay Effective Date");		
//							document.getElementById("saveButton").removeAttribute("disabled","true");
//							return false;
//						}
//					}
//				}
//			}
//		}
//	}
// 
//	 var basePath=document.getElementById("basePath").value;
//	 var repaymentType=document.getElementById("repaymentType").value; 
//	/* var typeOfDisbursal = '';
//	 
//	 if(repaymentType=='I')
//	 {
//	 typeOfDisbursal =document.getElementById("typeOfDisbursal").value;
//	 }*/
//	
//	 
//	 if(validateLaonInitForm())
//	 {
//		
//		 if(checkLoanAmount())
//		 {
//			
//			 if(repaymentType=='I')
//			 {
//				 var dueDay='';
//				 if(document.getElementById("oneDealOneLoan").value=='Y'){
//				 dueDay=document.getElementById("dueDayOneLoan").value;  
//				 }else{
//				 dueDay=document.getElementById("dueDay").value; 
//				 }
//				
//				 if(dueDay=='')
//				 {
//					 alert("Due day is required");
//					 document.getElementById("saveButton").removeAttribute("disabled","true");
//					 return false;
//				 }
//				 else
//				 {
//					 
//					 document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//					 document.getElementById("sourcingForm").submit();
//					 return true;
//				 }
//			 }
//			 else
//			 {
//				 document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//				 document.getElementById("sourcingForm").submit();
//				 return true;
//			 }
//		 }
//	 }
//	 else
//	 {
//		 document.getElementById("saveButton").removeAttribute("disabled","true");
//		 return false;
//	 }
//}


function addCollateralDetails()
{
	curWin = 0;
	otherWindows = new Array();
	// alert("ok");
	var contextPath = document.getElementById("contextPath").value;
	var url=contextPath+"/collateralBehindAction.do?method=collateralBehindDetail&cmStatus=CM";
	otherWindows[curWin++] =window.open(url,'addCollateralUpdate','height=400,width=1000,top=200,left=250,scrollbars=yes');
}

function selectCollateralDetails()
{
	// alert("ok");
	var contextPath = document.getElementById("contextPath").value;
	var url=contextPath+"/existAssetsActionBehind.do?method=openExistingAsset&openTyp=L";
	window.open(url,'selectCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
}

function saveCollateralDetails()
{
	// alert("ok1");
	if(checkboxCheck(document.getElementsByName("chk")))
	{
		// alert("ok2");
    // var contextPath = document.getElementById("contextPath").value;
      document.getElementById("selectForm").action = "selectProcessAction.do";
      document.getElementById("selectForm").submit();
      
	}
	else
	{
		alert("Please Select AtLeast one Record");
	}
    
}

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

function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chk');
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

function allCheckedMultiSelLov()
{
	// alert("ok");
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('checkboxBtn');
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

function deleteDetails()
{
	// alert("ok1");
	if(checkboxCheck(document.getElementsByName("chk")))
	{
		// alert(document.getElementsByName("chk").value);
       var contextPath = document.getElementById("contextPath").value;
      document.getElementById("collateralForm").action = contextPath+"/assetCMCollateralProcess.do";
      document.getElementById("collateralForm").submit();
      
	}
	else
	{
		alert("Please Select AtLeast one Record");
	}
}

function collateralView(acType,type,primaryId)
{ 
 // alert("acType : "+acType);
// alert("type:---"+type);
// alert("primaryId:---"+primaryId);
	var url='';
	if(type)
	{
		if(type=='MACHINE')
		{
			url="showAssetCollateral.do?method=getMachine&acType="+acType;
		}
		else if(type=='PROPERTY')
		{
			url="showAssetCollateral.do?method=getProperty&acType="+acType;
			
		}
		else if(type=='VEHICLE')
		{
			url="showAssetCollateral.do?method=getVehicle&acType="+acType;
		}
		else if(type=='OTHERS')
		{
			
			url="showAssetCollateral.do?method=getOther&acType="+acType;
			
	    }	
		else if(type=='FD')
		{
			
			url="showAssetCollateral.do?method=getFixedDeposit";
			
		}
		else if(type=='SBLC')
		{
			
			url="showAssetCollateral.do?method=getSBLC";
			
		}
		else if(type=='SECURITIES')
		{
			url="showAssetCollateral.do?method=getSecurities";
		}
		else if(type=='STOCK')
		{
			url="showAssetCollateral.do?method=getStock";
		}
		else if(type=='DEBTOR')
		{
			url="showAssetCollateral.do?method=getDebtor";
		}
		else if(type=='BG')
		{
			url="showAssetCollateral.do?method=getBg";
		}
		else if(type=='INSURANCE')
		{
			url="showAssetCollateral.do?method=getInsurance";
		}
		else if(type=='INVOICE')
		{
			url="collateralAssetInvoiceAction.do?method=fetchAssetInvoiceDtl";
		}
		else if(type=='GOLD')
		{
			url="showAssetCollateral.do?method=getGoldOrnament";
		}
		
		
		curWin = 0;
		otherWindows = new Array();
        var contextPath =document.getElementById('contextPath').value ;    
     	var completeURL = contextPath+"/"+url+"&primaryId="+primaryId+"&type="+type+"&acType="+acType;
     	// alert(completeURL);neerajname showCollateral
     	// window.open(completeURL,'showCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
		mywindow =window.open(completeURL,'showCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
		otherWindows[curWin++] = window.open(completeURL,'showCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function getDetails()
{
	
	// alert("get Details");
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	var closureType= document.getElementById("closureType").value;
	var initiationDate = document.getElementById("initDate").value;
	// alert(initiationDate);
	var effectiveDate = document.getElementById("effectiveDate").value;
	// alert(effectiveDate);
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(initiationDate,formatD.substring(2, 3));
    var dt2=getDateObject(effectiveDate,formatD.substring(2, 3));
	
	if(loanId == "")
	{
		alert("Please Enter Loan Account First");
		document.getElementById("loanAc").focus();
		return false;
	}
	else if(effectiveDate=='')
	{
		alert("Please Enter Effective Date");
		document.getElementById("effectiveDate").focus();
		return false;
	}
	
	else if (dt2<dt1)
	{
		  alert("Effective Date can not be less than Initiation Date");
		  document.getElementById("effectiveDate").focus();
		  return false;
	}
	else
	{
		global=1;
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveDuesRefundsValues&loanId="+loanId+"&effectiveDate="+effectiveDate+"&closureType="+closureType;
		 
		 var data = "lbxLoanNoHID=" + loanId;
		 sendDuesRefundid(address, data); 
		 return true;
	}
}
function sendDuesRefundid(address, data) {
	// alert("in sendClosureid ");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDuesRefund(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultDuesRefund(request){
	// alert("In result Dues Refund");

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		  
		var s1 = str.split("$:");
		document.getElementById('balancePrincipal').value=trim(s1[0]);  
		document.getElementById('secureDeposit').value=trim(s1[1]);
		document.getElementById('overdueInstallments').value=trim(s1[2]);
		document.getElementById('secureDepositInterest').value=trim(s1[3]);
		document.getElementById('interestTillDate').value=trim(s1[4]);
		document.getElementById('secureDepositTDS').value=trim(s1[5]);
		document.getElementById('unBilledInstallments').value=trim(s1[6]);
		document.getElementById('gapSDInterest').value=trim(s1[7]);
		document.getElementById('foreClosurePenalty').value=trim(s1[8]);
		document.getElementById('gapSDTDS').value=trim(s1[9]);
		document.getElementById('otherDues').value=trim(s1[10]);
		document.getElementById('otherRefunds').value=trim(s1[11]);
		document.getElementById('netReceivablePayable').value=trim(s1[12]);
		document.getElementById('interstTillLP').value=trim(s1[13]);
		document.getElementById('lppAmount').value=trim(s1[14]);
		document.getElementById('overduePrincipal').value=trim(s1[15]);
		document.getElementById('totalRecevable').value=trim(s1[18]);
		document.getElementById('totalPayable').value=trim(s1[11]);
		
// var
// totalRecevable=parseFloat(trim(s1[0]))+parseFloat(trim(s1[2]))+parseFloat(trim(s1[4]))+parseFloat(trim(s1[6]))+parseFloat(trim(s1[8]))+parseFloat(trim(s1[10]))+parseFloat(trim(s1[13]))+parseFloat(trim(s1[14]));
// alert()
// document.getElementById('totalRecevable').value=totalRecevable;
		if(trim(s1[16])=="T")
			alert("Termination Lockin period still valid for this loan");
		if(trim(s1[17])!="")
			alert(trim(s1[17]));
		
     window.close();
	}
	
}

function getDetailsfornew()
{
	
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	var closureType= document.getElementById("closureType").value;
	var initiationDate = document.getElementById("initDate").value;
	// alert(initiationDate);
	var effectiveDate = document.getElementById("effectiveDate").value;
	// alert(effectiveDate);
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(initiationDate,formatD.substring(2, 3));
    var dt2=getDateObject(effectiveDate,formatD.substring(2, 3));
	
	if(loanId == "")
	{
		alert("Please Enter Loan Account First");
		document.getElementById("loanAc").focus();
		return false;
	}
	else if(effectiveDate=='')
	{
		alert("Please Enter Effective Date");
		document.getElementById("effectiveDate").focus();
		return false;
	}
	
	else if (dt2<dt1)
	{
		  alert("Effective Date can not be less than Initiation Date");
		  document.getElementById("effectiveDate").focus();
		  return false;
	}
	else
	{
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/searchCMBehindAction.do?method=getDuesRefundsValues&loanId="+loanId+"&effectiveDate="+effectiveDate+"&closureType="+closureType;
		
		 var data = "lbxLoanNoHID=" + loanId;
		 sendDuesRefundid1(address, data); 
		 return true;
	}
}
function sendDuesRefundid1(address, data) {

	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDuesRefund1(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultDuesRefund1(request){
	

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		  
		var s1 = str.split("$:");
		document.getElementById('balancePrincipal').value=trim(s1[0]);  
		document.getElementById('secureDeposit').value=trim(s1[1]);
		document.getElementById('overdueInstallments').value=trim(s1[2]);
		document.getElementById('secureDepositInterest').value=trim(s1[3]);
		document.getElementById('interestTillDate').value=trim(s1[4]);
		document.getElementById('secureDepositTDS').value=trim(s1[5]);
		document.getElementById('unBilledInstallments').value=trim(s1[6]);
		document.getElementById('gapSDInterest').value=trim(s1[7]);
		document.getElementById('foreClosurePenalty').value=trim(s1[8]);
		document.getElementById('gapSDTDS').value=trim(s1[9]);
		document.getElementById('otherDues').value=trim(s1[10]);
		document.getElementById('otherRefunds').value=trim(s1[11]);
		document.getElementById('netReceivablePayable').value=trim(s1[12]);
		document.getElementById('interstTillLP').value=trim(s1[13]);
		document.getElementById('lppAmount').value=trim(s1[14]);
		document.getElementById('overduePrincipal').value=trim(s1[15]);
		document.getElementById('advanceEmiRefunds').value=trim(s1[18]);
		if(trim(s1[16])=="T")
			alert("Termination Lockin period still valid for this loan");
		if(trim(s1[17])!="")
			alert(trim(s1[17]));
		document.getElementById('waiveOffAmount').value='0.00';
 
	}
	
}

function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

// function callNetReceivablePayable()
// {
// var sum=0;
// var balPrincipal =
// removeComma(document.getElementById("balancePrincipal").value);
// //alert("balPrincipal: ",balPrincipal);
// var overdueInstallments =
// removeComma(document.getElementById("overdueInstallments").value);
// //alert("overdueInstallments: ",overdueInstallments);
// var interestTillDate =
// removeComma(document.getElementById("interestTillDate").value);
// //alert("interestTillDate: ",interestTillDate);
// var unBilledInstallments =
// removeComma(document.getElementById("unBilledInstallments").value);
// //alert("unBilledInstallments: ",unBilledInstallments);
// if(document.getElementById("foreClosurePenalty").value=='')
// document.getElementById("foreClosurePenalty").value=0;
// var foreClosurePenalty =
// removeComma(document.getElementById("foreClosurePenalty").value);
// //alert("foreClosurePenalty: ",foreClosurePenalty);
// var otherDues = removeComma(document.getElementById("otherDues").value);
// //alert("otherDues: ",otherDues);
// var otherRefunds =
// removeComma(document.getElementById("otherRefunds").value);
// //alert("otherRefunds: ",otherRefunds);
// var lppAmount = removeComma(document.getElementById("lppAmount").value);
// //alert("lppAmount: ",lppAmount);
// var interstTillLP =
// removeComma(document.getElementById("interstTillLP").value);
// //alert("interstTillLP: ",interstTillLP);
// var netReceivablePayable=0;
// netReceivablePayable =
// (balPrincipal+overdueInstallments+interestTillDate+unBilledInstallments+foreClosurePenalty+otherDues+lppAmount+interstTillLP)-otherRefunds;
// //alert("netReceivablePayable: ",netReceivablePayable);
// document.getElementById("netReceivablePayable").value =
// roundNumber(netReceivablePayable,2);
// }


function saveClosureDetails()
{
	if(global != 1)
	{
		alert("First get Loan datails.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var initiationDate = document.getElementById("initDate").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	var otherDues = removeComma(document.getElementById("otherDues").value);
    var waiveOffAmount = removeComma(document.getElementById("waiveOffAmount").value);
    var netReceivablePayable = removeComma(document.getElementById("netReceivablePayable").value);
    var totalRecevable = removeComma(document.getElementById("totalRecevable").value);
    var totalPayable = removeComma(document.getElementById("totalPayable").value);
    var fromdate=effectiveDate;
	var todate=effectiveDate;
	var day1= fromdate.substring(0,2);
	var day2= todate.substring(0,2);
	var month1=fromdate.substring(3,5);
	var month2=todate.substring(3,5);
	var year1=fromdate.substring(6);
	var year2=todate.substring(6);    
	if(validateClosureDynaValidatorForm(document.getElementById("closureForm")))
	{
		
		if(waiveOffAmount = '')
		{
			 alert("Waive off amount is required");
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		if(totalRecevable = '')
		{
			 alert("Total Recevable is required");
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		if(otherDues = '')
		{
			 alert("Other Dues is required");
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		
		if(parseFloat(waiveOffAmount)>parseFloat(totalRecevable))
	    {
	    	 alert("Waive off amount can't be greater than Total Recevable.");
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
	    }
		
	    if((parseFloat(totalRecevable)-parseFloat(totalPayable)) <0 && parseFloat(waiveOffAmount) >0)
	    {
	    	agree=confirm("Waive off amount is greater than Net receivable/ Payable amount.Do you want to continue?");
	    	if (!(agree))
	    	{
	        	document.getElementById("save").removeAttribute("disabled","true");
	    		return false;
	    	}
	    }
	    if(parseFloat(otherDues)>0 && parseFloat(waiveOffAmount)>0)
		{
			alert("Please Waive Off Other Dues First");
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("waiveOffAmount").value="0";
			return false;
		}
	    if(parseFloat(year1)<parseFloat(year2))
		{	
			alert("Effective Date can't be less than Initiation Date");
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("effectiveDate").focus();
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1)< parseFloat(month2))
				{	
					alert("Effective Date can't be less than Initiation Date");
					document.getElementById("save").removeAttribute("disabled","true");
					document.getElementById("effectiveDate").focus();
					return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) < parseFloat(day2))
						{	
							alert("Effective Date can't be less than Initiation Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("effectiveDate").focus();
							return false;
						}
					}
				}
			}	
		}
	    global=0;
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("closureForm").action = contextPath+"/earlyClosure.do?method=saveClosureDetails";
		document.getElementById("closureForm").submit();
		return true;
		
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
}


function noChange()
{
	alert("You can not change the status");
	return false;
}

function validateLoanInit()
{   
	var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
	var defaultNextDate=document.getElementById("defaultNextDate").value;
	var defaultRepayDate=document.getElementById("defaultRepayDate").value;
	var nextDueDate=document.getElementById("nextDueDate").value;
	var plan=document.getElementById("plan").value;
	var repayment=document.getElementById("repayment").value;
	var rePayDate="";	
	var dueDay="";
	if(nextDueDate=="")
	{
		alert("Next Due Date is required.");
		document.getElementById("saveButton").removeAttribute("disabled","true");
		return false;
	}
	var day2= nextDueDate.substring(0,2);
	if(oneDealOneLoan=='Y')
	{
		dueDay=document.getElementById("dueDayOneLoan").value;
		rePayDate=document.getElementById("repayEffectiveDateOneLoan").value;	
		if(rePayDate=="")
		{
			alert("Repay Effective Date is required.");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			return false;
		}
		if((nextDueDate != defaultNextDate) ||(rePayDate != defaultRepayDate))
		{
			if(plan == "N" &&  repayment == "N")
			{	alert("first save the installment plan and generate Repayment.");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
			if(repayment == "N")
			{	alert("first generate Repayment.");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
			if(plan == "N" )
			{	alert("first save the installment plan .");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
		}		
	}
	else
	{
		dueDay=document.getElementById("dueDay").value;
		rePayDate=document.getElementById("repayEffectiveDate").value;
		if(rePayDate=="")
		{
			alert("Repay Effective Date is required.");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			return false;
		}
	}
	if(parseFloat(day2) != parseFloat(dueDay))
	{
		alert("Next Due Date must be equal to Due Day ie."+dueDay);
		document.getElementById("saveButton").removeAttribute("disabled","true");
		return false;
	}
	var day1= rePayDate.substring(0,2);	
	var month1=rePayDate.substring(3,5);
	var month2=nextDueDate.substring(3,5);
	var year1=rePayDate.substring(6);
	var year2=nextDueDate.substring(6);
	if(parseFloat(year1)>parseFloat(year2))
	{	
		alert("Next Due Date should be greater than Repay Effective Date");
		document.getElementById("saveButton").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		 if(parseFloat(year1)==parseFloat(year2))
		 {
			if(parseFloat(month1)>parseFloat(month2))
			{	
				alert("Next Due Date should be greater than Repay Effective Date");
				document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
			else 
			{
				if(parseFloat(month1)==parseFloat(month2))
				{
					if(parseFloat(day1)>=parseFloat(day2))
					{	
						alert("Next Due Date should be greater than Repay Effective Date");		
						document.getElementById("saveButton").removeAttribute("disabled","true");
						return false;
					}
				}
			}
		}
	}	
	var loanId=document.getElementById("loanId").value;
	if(loanId!='')
	{
		if(checkLoanAmount())
		{
			 var basePath=document.getElementById("basePath").value;
			 document.getElementById("sourcingForm").action = basePath+"/loanDetailInCMBehindAction.do?method=stageForwardLoanInit";
			document.getElementById("sourcingForm").submit();
		}
		 else
		 {
			 return false;
		 }
	
	}
	else
	{
		alert("You can't move without saving before Loan Details.");
	}
}


function newLoanInit()
{
	// alert("ok");
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("CreditForm").action = contextPath+"/loanInitBehindAction.do?method=openNewLoan&loanStatus=NEW&loanId=";
    document.getElementById("CreditForm").submit();
}



function saveCancellationDetails()
{
	// alert("save");
	var businessDate = document.getElementById("businessDate").value;
	if(validateCancellationMakerDynaValidatorForm(document.getElementById("cancellationForm")))
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("cancellationForm").action = contextPath+"/cancellation.do?method=saveCancellationDetails";
	    document.getElementById("cancellationForm").submit();
	    return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}


function searchLoanClosure(type,status)
{
	if(document.getElementById("loanAc").value=="" && document.getElementById("customerName").value=="" && document.getElementById("reportingToUserId").value=="")
	{
		alert("Please Enter Loan Account No. or Customer Name");
		document.getElementById("search").removeAttribute("disabled","true");
		document.getElementById("loanAc").focus;
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		// alert("Type of Closure: "+type);
	    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=searchLoanData&type="+type+"&status="+status;
	    document.getElementById("closureSearchForm").submit();
	}
		
}


function openNewClosure(type)
{
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=openNewClosure&type="+type;
    document.getElementById("closureSearchForm").submit();
}

function searchLoanClosureAuthor(type,status)
{
	if(document.getElementById("loanAc").value=="" && document.getElementById("customerName").value==""&& document.getElementById("reportingToUserId").value=="")
	{
		alert("Please Enter Loan Account No. or Customer Name");
		document.getElementById("search").removeAttribute("disabled","true");
		document.getElementById("loanAc").focus;
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=searchLoanDataAuthor&type="+type+"&status="+status;
	    document.getElementById("closureSearchForm").submit();
	}
		
}

function checkMaturityDateForMatClo()
{
	// alert("check");
	var formatD=document.getElementById("formatD").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	// alert("effectiveDate: "+effectiveDate);
	var dt2=getDateObject(effectiveDate,formatD.substring(2, 3));
	// alert("dt2: "+dt2);
    var matDate = document.getElementById("maturtyDate").value;
    // alert("matDate: "+matDate);
    var maturityDate = getDateObject(matDate, formatD.substring(2, 3));
    // alert("maturityDate: "+maturityDate);
    var closureType = document.getElementById("closureType").value;
    // alert("closureType: "+closureType);
    if(closureType=='C')
	{
		if(dt2<maturityDate)
		{
			 alert("Effective Date can not be less than Maturity Date");
			 document.getElementById("effectiveDate").focus();
			 document.getElementById("effectiveDate").value='';
			 return false;
		}
	}
    else
    	return true;
}


function updateClosureDetails(type)
{
	
	var initiationDate = document.getElementById("initDate").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
    var matDate = document.getElementById("maturtyDate").value;
    var closureType = document.getElementById("closureType").value;
    var otherDues = removeComma(document.getElementById("otherDues").value);
    var waiveOffAmount = removeComma(document.getElementById("waiveOffAmount").value);
    var netReceivablePayable = removeComma(document.getElementById("netReceivablePayable").value);
    var totalRecevable = removeComma(document.getElementById("totalRecevable").value);
    var totalPayable = removeComma(document.getElementById("totalPayable").value);
    var fromdate=effectiveDate;
	var todate=initiationDate;
	var day1= fromdate.substring(0,2);
	var day2= todate.substring(0,2);
	var month1=fromdate.substring(3,5);
	var month2=todate.substring(3,5);
	var year1=fromdate.substring(6);
	var year2=todate.substring(6);

    if(validateClosureDynaValidatorForm(document.getElementById("closureForm")))
	{
    	if(parseFloat(waiveOffAmount)>parseFloat(totalRecevable))
        {
        	 alert("Waive off amount can't be greater than Total Recevable.");
        	 if(type=='P')
 				document.getElementById("save").removeAttribute("disabled","true");
 			 if(type=='F')
 				document.getElementById("saveForward").removeAttribute("disabled","true");
 			return false;
        }
    	if((parseFloat(totalRecevable)-parseFloat(totalPayable)) <0 && parseFloat(waiveOffAmount) >0)
        {
        	agree=confirm("Waive off amount is greater than Net receivable/ Payable amount.Do you want to continue.");
        	if (!(agree))
        	{
        		if(type=='P')
        			document.getElementById("save").removeAttribute("disabled","true");
        		if(type=='F')
        			document.getElementById("saveForward").removeAttribute("disabled","true");
        		return false;
	    	}
        }
    	if(parseFloat(otherDues)>0 && parseFloat(waiveOffAmount)>0)
		{
			alert("Please Waive Off Other Dues First");
			if(type=='P')
    			document.getElementById("save").removeAttribute("disabled","true");
    		if(type=='F')
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    		return false;
		}
    	if(type=='P')
    	{
    		if(parseFloat(year1)<parseFloat(year2))
			{	
    			alert("Effective Date can't be less than Initiation Date");
    			document.getElementById("save").removeAttribute("disabled","true");
    			document.getElementById("effectiveDate").focus();
    			return false;
			}
			else
			{
				 if(parseFloat(year1)==parseFloat(year2))
				 {
					if(parseFloat(month1)< parseFloat(month2))
					{	
						alert("Effective Date can't be less than Initiation Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("effectiveDate").focus();
						return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1) < parseFloat(day2))
							{	
								alert("Effective Date can't be less than Initiation Date");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("effectiveDate").focus();
								return false;
							}
						}
					}
				}	
			}
    	}
    	if(type=='F')
    	{
    		if(parseFloat(netReceivablePayable)>0)
    		{
    			alert("Net receivable/Payable amount can't be greater than zero.");
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    			return false;
    		}
    		if(parseFloat(year1) != parseFloat(year2))
			{	
    			alert("Effective Date should be equal to Initiation Date");
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    			document.getElementById("effectiveDate").focus();
    			return false;
			}
			else
			{
				 if(parseFloat(year1)==parseFloat(year2))
				 {
					if(parseFloat(month1) !=  parseFloat(month2))
					{	
						alert("Effective Date should be equal to Initiation Date");
						document.getElementById("saveForward").removeAttribute("disabled","true");
						document.getElementById("effectiveDate").focus();
						return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1) != parseFloat(day2))
							{	
								alert("Effective Date should be equal to Initiation Date");
								document.getElementById("saveForward").removeAttribute("disabled","true");
								document.getElementById("effectiveDate").focus();
								return false;
							}
						}
					}
				}	
			}
    	}    	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("closureForm").action = contextPath+"/earlyClosure.do?method=updateClosureDetails&type="+type;
		document.getElementById("closureForm").submit();
		
	}
	else
	{
		if(type=='P')
			document.getElementById("save").removeAttribute("disabled","true");
		if(type=='F')
			document.getElementById("saveForward").removeAttribute("disabled","true");
		return false;
	}
	
}


function updateCancellationDetails(type,fwdMsg)
{
	// alert("update");
	if(type=='F')
	{
		if(!confirm(fwdMsg))	 
	    {
	       	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
	}
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y')
	{
		var businessDate=document.getElementById("businessDate1").value;
		var makerDate=document.getElementById("makerDate").value;	
		var fromdate=businessDate;
		var todate=makerDate;
		var day1= fromdate.substring(0,2);
		var day2= todate.substring(0,2);
		var month1=fromdate.substring(3,5);
		var month2=todate.substring(3,5);
		var year1=fromdate.substring(6);
		var year2=todate.substring(6);
		if(parseFloat(year1) != parseFloat(year2))
		{	
			alert("Maker Date should be equal to Business Date.");
			document.getElementById("saveForward").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1) != parseFloat(month2))
				{	
					alert("Maker Date should be equal to Business Date.");
	    			document.getElementById("saveForward").removeAttribute("disabled","true");
	    			return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) != parseFloat(day2))
						{	
							alert("Maker Date should be equal to Business Date.");
			    			document.getElementById("saveForward").removeAttribute("disabled","true");
			    			return false;
						}
					}
				}
			}
		}
	}
	var businessDate = document.getElementById("businessDate").value;
	if(validateCancellationMakerDynaValidatorForm(document.getElementById("cancellationForm")))
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("cancellationForm").action = contextPath+"/cancellation.do?method=updateCancellationDetails&type="+type;
	    document.getElementById("cancellationForm").submit();
	    return true;
	 }
	else
	{
		if(type=='P')
			document.getElementById("save").removeAttribute("disabled","true");
		if(type=='F')
			document.getElementById("saveForward").removeAttribute("disabled","true");
		return false;
	}
}

function viewLoanDetails()
{
	var loanId = document.getElementById("lbxLoanNoHID").value;
	if(loanId=='')
	{
		alert("Please select Loan First");
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		
		var basePath = document.getElementById("contextPath").value;
		var url = basePath+"/loanDetailsForClosure.do?loanId="+loanId;
		myWindow=window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
		otherWindows[curWin++] =window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		if(window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
		}
}


function saveClosureAuthor()
{
	// alert("saveClosureAuthor..abc");
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y')
	{
		var businessDate=document.getElementById("businessDate").value;
		var makerDate=document.getElementById("makerDate").value;	
		var fromdate=businessDate;
		var todate=makerDate;
		var day1= fromdate.substring(0,2);
		var day2= todate.substring(0,2);
		var month1=fromdate.substring(3,5);
		var month2=todate.substring(3,5);
		var year1=fromdate.substring(6);
		var year2=todate.substring(6);
		if(parseFloat(year1) != parseFloat(year2))
		{	
			alert("Maker Date should be equal to Business Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1) != parseFloat(month2))
				{	
					alert("Maker Date should be equal to Business Date.");
	    			document.getElementById("save").removeAttribute("disabled","true");
	    			return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) != parseFloat(day2))
						{	
							alert("Maker Date should be equal to Business Date.");
			    			document.getElementById("save").removeAttribute("disabled","true");
			    			return false;
						}
					}
				}
			}
		}
	}		
	if(document.getElementById("comments").value=='')
	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("closureAuthorForm").action = contextPath+"/author.do?method=saveClosureAuthor";
	    document.getElementById("closureAuthorForm").submit();
	}
}



// Abhimanyu javascript method....

function isNumberKey(evt) 
{

var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only Numbers Allowed Here");
	return false;
}
	return true;
}
function isCharKey(evt) 
{

var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode < 65 || charCode > 90 && charCode < 97 || charCode > 122)
{
	alert("Only Character allowed here");
	return false;
}
	return true;
}


function openNewWaifOff(flag)
{
	
	var contextPath=document.getElementById("contextPath").value;
  document.getElementById("waiveOffMakerForm").action = contextPath+"/waiveOffMakerAction.do?method=openNewWaivOff&Flag="+flag;
  document.getElementById("waiveOffMakerForm").submit();
	
} 
function searchWaiveOff()
{

// alert("Ok.");
	if( document.getElementById("loanAccountNo").value=="" && document.getElementById("customerName").value=="" && document.getElementById("businessPartnerType").value=="" && document.getElementById("businessPartnerName").value=="" && document.getElementById("adviceAmount").value=="" && document.getElementById("waivOffAmount").value==""  && document.getElementById("reportingToUserId").value=="" )
	{
	alert("Please Enter One Field.");	
	return false;
	}	
	else
	{
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("waiveOffMakerForm").action = contextPath+"/waiveOffMakerAction.do?method=searchWaiveOff";
  document.getElementById("waiveOffMakerForm").submit();
	}
} 
function searchWaiveOffAuthor()
{

	if( document.getElementById("loanAccountNo").value=="" && document.getElementById("customerName").value=="" && document.getElementById("businessPartnerType").value=="" && document.getElementById("businessPartnerName").value=="" && document.getElementById("adviceAmount").value=="" && document.getElementById("waivOffAmount").value==""&& document.getElementById("reportingToUserId").value=="" )
	{
	alert("Please Enter One Field");
	return false;
	}
	else
	{

	var contextPath=document.getElementById("contextPath").value;
  document.getElementById("waiveOffAuthorForm").action = contextPath+"/waiveOffAuthorAction.do?method=searchWaiveOffAuthor";
  document.getElementById("waiveOffAuthorForm").submit();
	}
} 

function saveWaiveOffCSE()
{
	var balAmount=0.00;
	var totAmount=0.00;
	var balanceAmount=document.getElementById("balanceAmount").value;
	var totalWaveOffAmt=document.getElementById("totalWaveOffAmt").value;
	var n = document.getElementById("new").value;

	// alert(n);
	if(balanceAmount!='')
	{
		balAmount=parseFloat(removeComma(balanceAmount));
	}
	if(totalWaveOffAmt!='')
	{
		totAmount=parseFloat(removeComma(totalWaveOffAmt));
	}
	if(validateWaiveOffMakerSaveDynaValidatorForm(document.getElementById("waiveOffMakerFormCSE")))
	{
		if(totAmount <=0 )
		{
			  alert("Waveoff Amount must be greater than zero");
			  return false;
		}
	  if(totAmount<=balAmount)
	  {
		  var contextPath=document.getElementById("contextPath").value;
		  document.getElementById("waiveOffMakerFormCSE").action = contextPath+"/waiveOffMakerDispatchAction.do?method=waiveOffMakerCSESave&new="+n;
		  document.getElementById("waiveOffMakerFormCSE").submit();
		  return true;
	  }
	  else
	  {
		  alert("Waveoff Amount must be less than or equal to Balanced Amount");
		  return false;
	  }
	 
	}
}

function waiveOffMakerCSESaveForword()
{
	// alert("ok"+document.getElementById("totalWaveOffAmt").value);
	var totalWaveOffAmt=document.getElementById("totalWaveOffAmt").value;
	if(totalWaveOffAmt!='')
	{
		totAmount=parseFloat(removeComma(totalWaveOffAmt));
	}
	if(validateWaiveOffMakerSaveDynaValidatorForm(document.getElementById("waiveOffMakerFormCSE")))
	{
	var contextPath=document.getElementById("contextPath").value;
	if(document.getElementById("waveOffId").value=='')
	{
		alert("Please Save First");
		return false;
	}
	if(totAmount <=0 )
	{
		  alert("Waveoff Amount must be greater than zero");
		  return false;
	}
	else
	{
	  document.getElementById("waiveOffMakerFormCSE").action = contextPath+"/waiveOffMakerDispatchAction.do?method=waiveOffMakerCSESaveForword";
	  document.getElementById("waiveOffMakerFormCSE").submit();
	  return true;
	}
	}
}


function saveWaiveOffCSEAuthor()
{	
	var newBalanceAmt =0.00;
	if(validateWaiveOffAuthorDynaValidatorForm(document.getElementById("WaiveOffAuthorPage")))
	{
	var decision=document.getElementById("decision").value;
	var remarks = document.getElementById("remarks").value;
	// alert(decision);
	newBalanceAmt = document.getElementById("newBalanceAmt").value;
	var contextPath=document.getElementById("contextPath").value;
	// alert("newBalanceAmt "+newBalanceAmt);
	if(newBalanceAmt < 0 )
	{
		alert("Cannot approve this waive off due to negative balance amount");
		return false;
	}
	else
	{
		document.getElementById("WaiveOffAuthorPage").action = contextPath+"/waiveOffAuthorDispatchAction.do?method=saveWaiveOffAuthor&decision="+decision+"&remarks="+remarks;
		document.getElementById("WaiveOffAuthorPage").submit();
	}
  
	}
}

function getDateObject(dateString,dateSeperator)
{
	// This function return a date object after accepting
	// a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate=0,cMonth=0,cYear=0;

	// extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	// alert(cDate);
	
	// extract month portion
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);
	// alert(cMonth);

	// extract year portion
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	// alert(cYear);
	// cmonth1 = parseInt(cMonth)-1;
	// alert(cMonth-1);
	// Create Date Object
	dtObject=new Date(cYear,cMonth-1,cDate);
	
	// alert(dtObject);
	
	return dtObject;
}


function validMaturity()
{
	// alert("ok+"+document.getElementById("fdBookDate").value);

	var startDate = document.getElementById("startDate").value;
	var maturityDate =document.getElementById("maturityDate").value;
	
	 dt1=getDateObject(startDate,"-");
     dt2=getDateObject(maturityDate,"-");

	if (dt1>dt2)
	  {
	  alert("Start Date can not be less than Mature Date");
	  document.getElementById("maturityDate").value='';
	  return false;
	  }
	else
	{// alert("ok");
		return true;
	}
}
function compFq()
{
	// alert("compFq");
	if(document.getElementById("interestType3").checked)
	{
		document.getElementById("compoundFrequency").removeAttribute("disabled","true");
	}
	else
	{
		document.getElementById("compoundFrequency").setAttribute("disabled","true");
	}
}

function calculateInterest()
{
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
	
	if(tenure<=internalTenure && tenure>0)
	{
	var securityAmount= document.getElementById("securityAmount").value;
	var interestRate= document.getElementById("interestRate").value;
	// alert(interestRate);
	var result=0;
	
	if(securityAmount!=''&& interestRate!=''&& tenure!='')
	{
		sdAmount=parseFloat(removeComma(securityAmount));
		rate = parseFloat(removeComma(interestRate));
		ten= parseInt(tenure);
	}
	else
	{
		sdAmount=0;
		rate=0;
		ten=0;
	}
	
	// alert("sdAmount: "+sdAmount+"rate: "+rate+"tenure: "+ten);
		
		
	if(document.getElementById("interestType1").checked)
	{
		document.getElementById("relatedInterest").value=0;
		document.getElementById("compoundFrequency").value='';
		document.getElementById("interestRate").value='0.00';
		document.getElementById("interestRate").setAttribute("readOnly","true");
	}
	else if(document.getElementById("interestType2").checked)
	{
		var sint=(sdAmount*rate*ten)/(12*100);
		result =sint.toFixed(4);
		document.getElementById("relatedInterest").value=result;
		document.getElementById("compoundFrequency").value='';
		document.getElementById("interestRate").removeAttribute("readOnly");
		
	}
	else if(document.getElementById("interestType3").checked)
	{
		var frequency= document.getElementById("compoundFrequency").value;
		var freq=0;
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
			freq= parseInt(12/freqMonth);
		}
		
		/*
		 * var mathPow = parseFloat(Math.pow((rate/100),(ten/freq)));
		 * alert(mathPow.toString()); var iAmount =parseFloat(sdAmount*mathPow);
		 */
		var iAmount = parseFloat((sdAmount * Math.pow(1+(rate/(freq*100)),(ten*freq/12))) - sdAmount);
		
		// alert(iAmount);
		result = iAmount.toFixed(4);
		document.getElementById("relatedInterest").value=result;
		document.getElementById("interestRate").removeAttribute("readOnly");
		
		
	}
	  return result;
	}
	else
	{
		alert("Tenure must be less than "+internalTenure+" and greater than 0");
		result=0;
		document.getElementById("tenure").value='';
		return result;
	}
}


function roundVal(val){
	var dec = 6;
	var result = Math.round(val*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

/*
 * function roundVal(val) { alert(val); var tem='', z, d, s= val.toString(), x=
 * s.match(/^(\d+)\.(\d+)[eE]([-+]?)(\d+)$/); if(x){ d= x[2]; z= (x[3]== '-')?
 * x[4]-1: x[4]-d.length; while(z--)tem+='0'; if(x[3]== '-'){ return
 * '0.'+tem+x[1]+d; } return x[1]+d+tem; } return s; }
 */
// Amit starts

function newDisbursal()
{
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=openNewDisbursal";
    document.getElementById("disbursalSearchForm").submit();
}


function searchDisbursal(type)
{
	// alert(type);
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("customerName").value;
	var loanAmt = document.getElementById("loanAmt").value;
	var loanApprovalDate = document.getElementById("loanApprovalDate").value;
	var product = document.getElementById("product").value;
	var scheme = document.getElementById("scheme").value;
	var scheme = document.getElementById("reportingToUserId").value;
	var contextPath=document.getElementById("contextPath").value;
	
	if(loanNo!='' || custName!='' || loanAmt!='' || loanApprovalDate!='' || product!='' || scheme!=''|| reportingToUserId!='')
	{
		if(custName!='' && custName.length>=3)
		{
			 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("disbursalSearchForm").submit();
			return true;
		}else if(custName=='')
		{
			 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("disbursalSearchForm").submit();
			return true;
		}

		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function viewReceivableDisbursal(alert1) 
{	
	curWin = 0;
	otherWindows = new Array();
	
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		var loanId=document.getElementById('lbxLoanNoHID').value;	
		// alert("loanId: "+loanId);
		var contextPath = document.getElementById("contextPath").value;
		// alert(contextPath);
		var url= contextPath+"/viewReceivableDisbursalAction.do?loanId="+loanId;
		// alert("url: "+url);
		// window.open(url,'View
		// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
		// scrollbars=yes");
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
		otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250, scrollbars=yes');
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}
function viewRepaymentScheduleDisbursal()
{
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert("Please Select Loan First");	
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		var loanId=document.getElementById('lbxLoanNoHID').value;	
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/repaymentScheduleDisbursal.do?method=repaymentSchedule&loanId="+loanId; 
		myWindow=window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
		otherWindows[curWin++] =window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		if(window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}

function viewRepaymentSchedule()
{	var id=document.getElementsByName("radioId");
	var loanid=document.getElementsByName("lbxLoanNoHIDmain");
	var loanIDMain ='';
	var contextPath=document.getElementById("contextPath").value;
	for(var i=0; i< id.length ; i++)
	{				   
		if(id[i].checked == true){
			loanIDMain = loanid[i].value;
		}				   
	}			
	var url= contextPath+"/repaymentScheduleDisbursal.do?method=repaymentSchedule&loanId="+loanIDMain;
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) {
		mywindow.focus();
		return false;
	}
	return true;
}


function viewOldRepaymentSchedule()
{	
	var id=document.getElementsByName("radioId");
	var loanid=document.getElementsByName("lbxLoanNoHIDmain");
	var loanIDMain ='';
	var contextPath=document.getElementById("contextPath").value;
	for(var i=0; i< id.length ; i++)
	{				   
		if(id[i].checked == true){
			loanIDMain = loanid[i].value;
		}				   
	}			
	var url= contextPath+"/repaymentScheduleDisbursal.do?method=oldRepaymentSchedule&loanId="+loanIDMain;
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) 
	{
		mywindow.focus();
		return false;
	}
	return true;
}

function earlyClosureDetails()
{
	var id=document.getElementsByName("radioId");
	var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
	var loanIDMain ='';	
	var sourcepath=document.getElementById("contextPath").value;			   
	for(var i=0; i< id.length ; i++) 
	{				   
	   if(id[i].checked == true)
	   {					   
		   loanIDMain = loanid[i].value;
	   }				   
	}	
	if(document.getElementById("rvFlag").value=="YES")
	{
		var warning=confirm("Not allowed to generate FC, please contact Central OPS Team. Do you want to continue?");
		if(!warning)
		{
			return false;
		}
	}
	var earlyClosureType=document.getElementById("earlyClosureType").value;	
	if(earlyClosureType=='Y')
	{
		var url= sourcepath+"/earlyClosureDetail.do?method=openEarlyClosureDetail&loanId="+loanIDMain;
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		otherWindows = new Array();
	    curWin = 0;
	    otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
		mywindow.moveTo(800,300);
		if (window.focus) 
		{
			mywindow.focus();
			return false;
		}
	}
	else
	{
		var defaultFormate=document.getElementById("defaultFormate").value;	
		if(defaultFormate=='H')
		{
			var url=sourcepath+"/searchCMBehindAction.do?method=openEarlyClosureDetail&loanId="+loanIDMain+"&reportFormate=H";
			popupWin=window.open(url,'Closure_Detail','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
			if (window.focus) 
			   popupWin.focus();					
		}
		else
		{
			document.getElementById("searchForCMForm").action=sourcepath+"/searchCMBehindAction.do?method=openEarlyClosureDetail&loanId="+loanIDMain+"&reportFormate=P";
			document.getElementById("searchForCMForm").submit();
		}
	}
	
	
}
function saveDisbursalData(loanIDDisbursal)
{
	// alert("saveDisbursalData"+loanIDDisbursal);
	var check=document.getElementById("val").value;
	
	if(check=="Y")
	{
		var nextDueDate=document.getElementById("nextDueDate").value;
		if(nextDueDate=="")
		{
			alert("Please enter Next Due Date");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	}
	var des=document.getElementById("disbursalDescription").value;
	var len=des.length;
	if(len > 500)
	{
		alert("Maker Remark is too long.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	// alert("ok");
	// alert(loanIDDisbursal);
	var formatD=document.getElementById("formatD").value;
	var actualDate=document.getElementById("disbursalDate").value;
	var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
	// alert(actualDate);
	var businessDate= document.getElementById("businessDate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	// alert(businessDate);
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var dtLoanApproval = getDateObject(loanApprovalDate,formatD.substring(2, 3));
	var loanAmt = removeComma(document.getElementById("loanAmt").value);
	var disbursedAmt = removeComma(document.getElementById("disbursedAmount").value);
	var disbursalAmt = removeComma(document.getElementById("disbursalAmount").value);
	var shortPayDue = removeComma(document.getElementById("proposedShortPayAmount").value);
	var shortPay = removeComma(document.getElementById("shortPayAmount").value);
	var pdcDepositCount= document.getElementById("pdcDepositCount").value;
	// alert("ok"+pdcDepositCount);
	if(document.getElementById("pdcDepositCount").value==0)
	{
		var pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
		if(pdcDeposited)
		{
			if(document.getElementById("finalDisbursal").checked==true )
			{
				
				
					var repayEffDate= document.getElementById("repayEffDate").value;
					var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
					// alert(repayEffDate);
					if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
					{
						// alert("cycleDate: "+cycleDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
						{
							if(shortPayDue>shortPay)
							{
								alert("Current Short Pay Amount cannot be less than Total Receivables");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("shortPayAmount").value='';
								document.getElementById("shortPayAmount").focus();
								return false;
							}
							else
							{ 
								var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									return false;
								}
							}
						}
						else
						{	var sum=disbursedAmt+disbursalAmt;
							if(confirm("Loan Amt is "+sum+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
							}
							
						}
					}
					else
					{
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
				
			}
			else
			{
				// alert("In Else");
				if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
				{
					var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
					var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
					var loanAmt = removeComma(document.getElementById("loanAmt").value);
					var repayMode = document.getElementById("repayMode").value;
		     	// alert("alert disbursalAmount: "+disbursalAmount);
				// alert("alert disbursedAmount: "+disbursedAmount);
				// alert("alert loanAmt: "+loanAmt);
				// alert("alert repayMode: "+repayMode);
					if(repayMode=="I")
					{
						
						if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
						{
							alert("Please Check Final Disbursal check box");
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
						if((disbursalAmount+disbursedAmount)>loanAmt)
						{
							
							alert("Disbursal Amount Can not more than "+(loanAmt-disbursedAmount));
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
					}
					if(document.getElementById("finalDisbursal").checked==true)
					{
						
						
							var repayEffDate= document.getElementById("repayEffDate").value;
							var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
							// alert(repayEffDate);
							if(repayEffDate=='')
							{
								alert("Repay Effective Date is Required");
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
							}
							if(dtActual<dtBusiness)
							{
								alert("Disbursal Date cannot be less than Business Date");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtActual>dtBusiness)
							{
								alert("Disbursal Date cannot be greater than Business Date");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtActual<dtLoanApproval)
							{
								alert("Disbursal Date cannot be less than Loan Approval Date");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtRepay<dtActual)
							{
								alert("Repayment Effective Date cannot be smaller than Disbursal Date");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").value='';
								document.getElementById("repayEffDate").focus();
								return false;
							}
							if(disbursedAmt == 0 && disbursalAmt == 0)
							{
								alert("Cannot Finalize Disbursal with Zero amount");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalAmount").value='';
								document.getElementById("disbursalAmount").focus();
								return false;
							}
							
							if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
							{
								if(shortPayDue>shortPay)
								{
									alert("Current Short Pay Amount cannot be less than Total Receivables");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("shortPayAmount").value='';
									document.getElementById("shortPayAmount").focus();
									return false;
								}
								else
								{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
									if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
									{
										var contextPath=document.getElementById("contextPath").value;
										document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
										document.getElementById("disbursalMakerForm").submit();
									}
									else
									{
										document.getElementById("save").removeAttribute("disabled","true");
										return false;
									}
								}
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									return false;
								}
							}
												
					}
					else
					{
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Disbursal Amount should be greater than Zero");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						else
						{
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
							document.getElementById("disbursalMakerForm").submit();
						}
					}
				}
				else
				{
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
			}
		}
		else
		{
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	}
	
	// Start disbursal if PDCs have been captured
	else
	{
		// Check for final Disbursal
		if(document.getElementById("finalDisbursal").checked==true)
		{
			
			
				var repayEffDate= document.getElementById("repayEffDate").value;
				var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
				// alert(repayEffDate);
				if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
				{
					// alert("cycleDate: "+cycleDate);
					if(repayEffDate=='')
					{
						alert("Repay Effective Date is Required");
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtRepay<dtActual)
					{
						alert("Repayment Effective Date cannot be smaller than Disbursal Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("repayEffDate").focus();
						return false;
					}
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Cannot Finalize Disbursal with Zero amount");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalAmount").value='';
						document.getElementById("disbursalAmount").focus();
						return false;
					}
					
					if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
					{
						if(shortPayDue>shortPay)
						{
							alert("Current Short Pay Amount cannot be less than Total Receivables");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("shortPayAmount").value='';
							document.getElementById("shortPayAmount").focus();
							return false;
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
							}
						}
					}
					else
					{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
						if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
						{
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
							document.getElementById("disbursalMakerForm").submit();
						}
						else
						{
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
					}
					
					
					
				}
				else
				{
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
			
		}
		// if not final disbursal
		else
		{
			
			if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
			{
				var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
				var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
				var loanAmt = removeComma(document.getElementById("loanAmt").value);
				var repayMode = document.getElementById("repayMode").value;
	// alert("alert disbursalAmount: "+disbursalAmount);
	// alert("alert disbursedAmount: "+disbursedAmount);
	// alert("alert loanAmt: "+loanAmt);
	// alert("alert repayMode: "+repayMode);
				if(repayMode=="I")
				{
					if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
					{
						alert("Please Check Final Disbursal check box");
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
					if((disbursalAmount+disbursedAmount)>loanAmt)
					{
						
						alert("Disbursal Amount Can not more than "+(loanAmt-disbursedAmount));
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
				}
				if(document.getElementById("finalDisbursal").checked==true)
				{
					
					
						var repayEffDate= document.getElementById("repayEffDate").value;
						var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
						// alert(repayEffDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").value='';
							document.getElementById("repayEffDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						
						if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
						{
							if(shortPayDue>shortPay)
							{
								alert("Current Short Pay Amount cannot be less than Total Receivables");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("shortPayAmount").value='';
								document.getElementById("shortPayAmount").focus();
								return false;
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
							}
						}
						
					
				}
				else
				{
					if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Disbursal Amount should be greater than Zero");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalAmount").value='';
						document.getElementById("disbursalAmount").focus();
						return false;
					}
					else
					{
						var contextPath=document.getElementById("contextPath").value;
						document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
						document.getElementById("disbursalMakerForm").submit();
					}
				}
			}
			else
			{
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	}
}

function updateDisbursalData(recStatus)
{
	
	var nextDueDate =document.getElementById("nextDueDate").value;
	var check =document.getElementById("val").value;
	if(check=="Y" && nextDueDate=="")
	{
		alert("Please enter Next Due Date");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var des=document.getElementById("disbursalDescription").value;
	var len=des.length;
	if(len > 500)
	{
		alert("Maker Remark is too long.");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	var formatD=document.getElementById("formatD").value;
	var actualDate=document.getElementById("disbursalDate").value;
	var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
	var businessDate= document.getElementById("businessDate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var dtLoanApproval = getDateObject(loanApprovalDate,formatD.substring(2, 3));
	var disbursedAmt = removeComma(document.getElementById("disbursedAmount").value);
	var loanAmt = removeComma(document.getElementById("loanAmt").value);
	var disbursalAmt = removeComma(document.getElementById("disbursalAmount").value);
	
	if(document.getElementById("pdcDepositCount").value==0)
	{
		var pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
		if(pdcDeposited)
		{
			if(document.getElementById("finalDisbursal").checked==true)
			{
				
				
					var repayEffDate= document.getElementById("repayEffDate").value;
					var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
					var proposedShortPayAmount = removeComma(document.getElementById("proposedShortPayAmount").value);
					var adjustedShortPayAmount = removeComma(document.getElementById("adjustedShortPayAmount").value);
					var shortPayAmount = removeComma(document.getElementById("shortPayAmount").value);
					if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
					{
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							return false;
						}
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						
						if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
						{
							if((shortPayAmount)<proposedShortPayAmount)
							{
								alert("Current Short Pay Amount cannot be less than Total Receivables");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
						}
						
					}
					else
					{
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						return false;
					}
				
			}
			else
			{
				if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
				{
					var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
					var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
					var loanAmt = removeComma(document.getElementById("loanAmt").value);
					var repayMode = document.getElementById("repayMode").value;
					
					if(repayMode=="I")
					{	
						if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
						{
							alert("Please Check Final Disbursal check box");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							return false;
						}
					}
					if(document.getElementById("finalDisbursal").checked==true)
					{
						
						
							var repayEffDate= document.getElementById("repayEffDate").value;
							var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
							var proposedShortPayAmount = removeComma(document.getElementById("proposedShortPayAmount").value);
							var adjustedShortPayAmount = removeComma(document.getElementById("adjustedShortPayAmount").value);
							var shortPayAmount = removeComma(document.getElementById("shortPayAmount").value);
							if(repayEffDate=='')
							{
								alert("Repay Effective Date is Required");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
							if(dtActual<dtBusiness)
							{
								alert("Disbursal Date cannot be less than Business Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtActual>dtBusiness)
							{
								alert("Disbursal Date cannot be greater than Business Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtActual<dtLoanApproval)
							{
								alert("Disbursal Date cannot be less than Loan Approval Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtRepay<dtActual)
							{
								alert("Repayment Effective Date cannot be smaller than Disbursal Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").focus();
								return false;
							}
							if(disbursedAmt == 0 && disbursalAmt == 0)
							{
								alert("Cannot Finalize Disbursal with Zero amount");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalAmount").value='';
								document.getElementById("disbursalAmount").focus();
								return false;
							}
							if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
							{
								if((shortPayAmount)<proposedShortPayAmount)
								{
									alert("Current Short Pay Amount cannot be less than Total Receivables");
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									return false;
								}
								else
								{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
									if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+disbursedAmt+" and this loan has marked for fully disbursal, are you sure to save this information? "))
									{
										var contextPath=document.getElementById("contextPath").value;
										document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
										document.getElementById("disbursalMakerForm").submit();
									}
									else
									{
										if(recStatus=="P")
											document.getElementById("save").removeAttribute("disabled","true");
										if(recStatus=="F")
											document.getElementById("saveFwd").removeAttribute("disabled","true");
										return false;
									}
								}
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									return false;
								}
							}
							
						
					}
					else
					{
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Disbursal Amount should be greater than Zero");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						else
						{
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
							document.getElementById("disbursalMakerForm").submit();
						}
					}
				}
				else
				{
					if(recStatus=="P")
						document.getElementById("save").removeAttribute("disabled","true");
					if(recStatus=="F")
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
			}
		}
		else
		{
			if(recStatus=="P")
				document.getElementById("save").removeAttribute("disabled","true");
			if(recStatus=="F")
				document.getElementById("saveFwd").removeAttribute("disabled","true");
			return false;
		}
	}
	
	// Start disbursal if PDCs have been captured
	else
	{
		// Check for final Disbursal
		if(document.getElementById("finalDisbursal").checked==true)
		{
			
			
				var repayEffDate= document.getElementById("repayEffDate").value;
				var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
				var proposedShortPayAmount = removeComma(document.getElementById("proposedShortPayAmount").value);
				var adjustedShortPayAmount = removeComma(document.getElementById("adjustedShortPayAmount").value);
				var shortPayAmount = removeComma(document.getElementById("shortPayAmount").value);
				// alert(repayEffDate);
				if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
				{
					if(repayEffDate=='')
					{
						alert("Repay Effective Date is Required");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						return false;
					}
					if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtRepay<dtActual)
					{
						alert("Repayment Effective Date cannot be smaller than Disbursal Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("repayEffDate").focus();
						return false;
					}
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Cannot Finalize Disbursal with Zero amount");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalAmount").value='';
						document.getElementById("disbursalAmount").focus();
						return false;
					}
					
					if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
					{
						if((shortPayAmount)<proposedShortPayAmount)
						{
							alert("Current Short Pay Amount cannot be less than Total Receivables");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							return false;
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
						}
					}
					else
					{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
						if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
						{
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
							document.getElementById("disbursalMakerForm").submit();
						}
						else
						{
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							return false;
						}
					}
					
					
				}
				else
				{
					if(recStatus=="P")
						document.getElementById("save").removeAttribute("disabled","true");
					if(recStatus=="F")
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
			
		}
		// Update for non final Disbursal
		else
		{
			if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
			{
				var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
				var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
				var loanAmt = removeComma(document.getElementById("loanAmt").value);
				var repayMode = document.getElementById("repayMode").value;
				
				if(repayMode=="I")
				{	
					if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
					{
						alert("Please Check Final Disbursal check box");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						return false;
					}
				}
				if(document.getElementById("finalDisbursal").checked==true)
				{
					
					
						var repayEffDate= document.getElementById("repayEffDate").value;
						var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
						var proposedShortPayAmount = removeComma(document.getElementById("proposedShortPayAmount").value);
						var adjustedShortPayAmount = removeComma(document.getElementById("adjustedShortPayAmount").value);
						var shortPayAmount = removeComma(document.getElementById("shortPayAmount").value);
						// alert(repayEffDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							return false;
						}
						if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						if(document.getElementById("totalReceivableCurrShortPay").value=='Y')
						{
							if((shortPayAmount)<proposedShortPayAmount)
							{
								alert("Current Short Pay Amount cannot be less than Total Receivables");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								return false;
							}
						}
											
					
				}
				else
				{
					if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Disbursal Amount should be greater than Zero");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalAmount").value='';
						document.getElementById("disbursalAmount").focus();
						return false;
					}
					else
					{
						var contextPath=document.getElementById("contextPath").value;
						document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
						document.getElementById("disbursalMakerForm").submit();
					}
				}
			}
			else
			{
				if(recStatus=="P")
					document.getElementById("save").removeAttribute("disabled","true");
				if(recStatus=="F")
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				return false;
			}
		}
	}
}

function callRepayBeforeSave(alert1)
{
	alert(alert1);
}

function updateDisbursalBeforeSave()
{	
	alert("Please save the data First");
	return false;	
	
}

function saveDisbursalAuthor()
{
	
	
	var contextPath=document.getElementById("contextPath").value;
	if(document.getElementById("comments").value=='')
	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
// if(document.getElementById("disbursalFinalFlagForAuthor").value=="F" &&
// document.getElementById("decision").value=="A")
// {
// if(confirm("This loan has marked for fully disbursal, are you sure to approve
// these information? "))
// {
//			
// document.getElementById("disbursalAuthorForm").action =
// contextPath+"/disbursedInitiationAuthor.do?method=saveDisbursalAuthor";
// document.getElementById("disbursalAuthorForm").submit();
// }
// else
// {
// document.getElementById("save").removeAttribute("disabled","true");
// return false;
// }
// }
// else
// {
		document.getElementById("disbursalAuthorForm").action = contextPath+"/disbursedInitiationAuthor.do?method=saveDisbursalAuthor";
		document.getElementById("disbursalAuthorForm").submit();
		
// }
}

function openDisbursalSchedule()
{
	// alert("Open Disbursal Schedule");
	curWin = 0;
	otherWindows = new Array();
	
	var loanId = document.getElementById("lbxLoanNoHID").value;
	if(loanId=="")
	{
		alert("Plaese Select Loan No. First");
		return false;
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/disbursalSearchBehind.do?method=openDisbursalSchedule&lbxloannohid="+loanId;
		// alert("url: "+url);
		// window.open(url,'Disbursal
		// Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		otherWindows[curWin++] = window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		
		return true;
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
	{
		loanAm=parseFloat(removeComma(loanAmount));
	}
	if(sanctionedLoanAmount!='')
	{
		sanctionAm=parseFloat(removeComma(sanctionedLoanAmount));
	}
	if(utilizeLoanAmount!='')
	{
		utilizeLA=parseFloat(removeComma(utilizeLoanAmount));
	}
	diff=sanctionAm-utilizeLA;
	// alert("loanAmount "+loanAmount+" sanctionedLoanAmount
	// "+sanctionedLoanAmount+" Difference "+diff);
	if(loanAm<=diff)
	{
		return true;
	}
	else
	{
		alert("You can't enter Loan Amount more than "+diff);
		document.getElementById("loanAmount").value='';
		document.getElementById("sourcingForm").loanAmount.focus();
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
   // alert("bDate: "+bDate+"Converted Bussiness Date:
	// "+dt3+"formatD.substring(2, 3): "+formatD.substring(2, 3));
  // alert("validAggrDate: agreementDate(dt1) "+dt1+"sanctionedDate(dt2):
	// "+dt2+"bDate(dt3): "+dt3);
    
   /*
	 * if(dt1<dt3) { msg="Please enter agreement date greater than bussiness
	 * Date "; document.getElementById("agreementDate").value=''; //return
	 * false; }
	 */
	if(dt1>dt2)
	{
    	msg="Please enter agreement date less than sanctioned Date ";
		document.getElementById("agreementDate").value='';
		// return false;
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


function newLiquidation()
{
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("liquidationSearchForm").action = contextPath+"/sdLiquidationSearch.do?method=openNewLiquidation";
    document.getElementById("liquidationSearchForm").submit();
}


function searchLiquidation(type)
{
	// alert(type);
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("customerName").value;
	var reportingToUserId = document.getElementById("reportingToUserId").value;
	if(loanNo=='' && custName=='' && reportingToUserId=='')
	{
		alert("Please Enter One value to Search.");
		document.getElementById("search").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("liquidationSearchForm").action = contextPath+"/sdLiquidationSearch.do?method=searchLiquidation&type="+type;
	    document.getElementById("liquidationSearchForm").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
	}
		
}

function generateSDAccrual()
{   
	var liquidationFlag = document.getElementById("liquidationFlag").value;
	if(liquidationFlag=='P')
	{
		document.getElementById("partialI").style.display='block';
		document.getElementById("partialT").style.display='block';
		document.getElementById("finalI").style.display='none';
		document.getElementById("finalT").style.display='none';
	}
	if(liquidationFlag=='F')
	{
		document.getElementById("partialI").style.display='none';
		document.getElementById("partialT").style.display='none';
		var loanId = document.getElementById("lbxLoanNoHID").value;
		if(loanId == "")
		{
			alert("Please Enter Loan No. First");
			document.getElementById("loanLov").focus();
			return false;
		}
		else
		{
			 var contextPath=document.getElementById("contextPath").value;
			 var address = contextPath+"/ajaxAction.do?method=generateSDAccrual";
			 var data = "lbxLoanNoHID="+loanId;
			 // alert("address: "+address);
			 sendSDAccrualid(address, data); 
			 return true;
		}
	}
}

function sendSDAccrualid(address, data) {
	// alert("in sendSDAccrualid ");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultSDAccrual(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultSDAccrual(request){
	// alert("In result SDAccrual");

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		  
		var s1 = str.split("$:");
		document.getElementById("finalI").innerHTML=trim(s1[0]);
		document.getElementById("finalT").innerHTML=trim(s1[1]);
		document.getElementById("finalI").style.display='block';
		document.getElementById("finalT").style.display='block';
		window.close();
	}
	
}

function saveLiquidationData()
{
	if(validateSDLiquidationMakerDynaValidatorForm(document.getElementById("sdLiquidationMakerForm")))
	{
		var liquidationAmountPrincipal = removeComma(document.getElementById("liquidationAmountPrincipal").value);
		// alert("liquidationAmountPrincipal: "+liquidationAmountPrincipal);
		var liquidatedAmountPrincipal = removeComma(document.getElementById("liquidatedAmountPrincipal").value);
		// alert("liquidatedAmountPrincipal: "+liquidatedAmountPrincipal);
		var liquidationAmountInterest = removeComma(document.getElementById("liquidationAmountInterest").value);
		// alert("liquidationAmountInterest: "+liquidationAmountInterest);
		var liquidatedAmountInterest = removeComma(document.getElementById("liquidatedAmountInterest").value);
		// alert("liquidatedAmountInterest: "+liquidatedAmountInterest);
		var sdAmount = removeComma(document.getElementById("sdAmount").value);
		// alert("sdAmount: "+sdAmount);
		var sdInterestAccrued = removeComma(document.getElementById("sdInterestAccrued").value);
		// alert("sdInterestAccrued: "+sdInterestAccrued);
		
		// alert("liquidationFlag:
		// "+document.getElementById("liquidationFlag").value);
		if(document.getElementById("liquidationFlag").value=='P')
		{
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
				if((liquidationAmountPrincipal+liquidatedAmountPrincipal)>sdAmount)
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal should be less than or equal to SD Amount");
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else if((liquidationAmountInterest+liquidatedAmountInterest)>sdInterestAccrued)
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest should be less than or equal to Sum of Total Interest Accrued");
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=saveLiquidationData";
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
					
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		
		if(document.getElementById("liquidationFlag").value=='F')
		{
			var gapInterest = removeComma(document.getElementById("gapInterestF").value);
			var gapTDS = removeComma(document.getElementById("gapTDSF").value);
			// alert("gapInterest: "+gapInterest);
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
		
				if((liquidationAmountPrincipal+liquidatedAmountPrincipal)!=sdAmount)
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal must be equal to SD Amount for Final Liquidation");
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				if((liquidationAmountInterest+liquidatedAmountInterest)!= (sdInterestAccrued+gapInterest))
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest can't be greater than Sum of Total Interest Accrued & GAP Interest");
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=saveLiquidationData";
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
					
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function removeComma(id)
{
    var str = id;
  //   alert(id);
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    var amount = parseFloat(stri);
  //   alert(amount);
	return amount;
}

function updateLiquidationData(type)
{
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y')
	{
		var businessDate=document.getElementById("businessDate1").value;
		var makerDate=document.getElementById("makerDate").value;	
		var fromdate=businessDate;
		var todate=makerDate;
		var day1= fromdate.substring(0,2);
		var day2= todate.substring(0,2);
		var month1=fromdate.substring(3,5);
		var month2=todate.substring(3,5);
		var year1=fromdate.substring(6);
		var year2=todate.substring(6);
		if(parseFloat(year1) != parseFloat(year2))
		{	
			alert("Maker Date should be equal to Business Date.");
			document.getElementById("saveFwd").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1) != parseFloat(month2))
				{	
					alert("Maker Date should be equal to Business Date.");
					document.getElementById("saveFwd").removeAttribute("disabled","true");
	    			return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) != parseFloat(day2))
						{	
							alert("Maker Date should be equal to Business Date.");
			    			document.getElementById("saveFwd").removeAttribute("disabled","true");
			    			return false;
						}
					}
				}
			}
		}
	}
	
	if(validateSDLiquidationMakerDynaValidatorForm(document.getElementById("sdLiquidationMakerForm")))
	{
		var liquidationAmountPrincipal = removeComma(document.getElementById("liquidationAmountPrincipal").value);
		var liquidatedAmountPrincipal = removeComma(document.getElementById("liquidatedAmountPrincipal").value);
		var liquidationAmountInterest = removeComma(document.getElementById("liquidationAmountInterest").value);
		var liquidatedAmountInterest = removeComma(document.getElementById("liquidatedAmountInterest").value);
		var sdAmount = removeComma(document.getElementById("sdAmount").value);
		var sdInterestAccrued = removeComma(document.getElementById("sdInterestAccrued").value);
		
		if(document.getElementById("liquidationFlag").value=='P')
		{
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
				if((liquidationAmountPrincipal+liquidatedAmountPrincipal)>sdAmount)
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal is greater than SD Amount");
					if(type=='P')
						document.getElementById("save").removeAttribute("disabled","true");
					else if(type=='F')
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				else if((liquidationAmountInterest+liquidatedAmountInterest)>sdInterestAccrued)
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest is greater than Total Interest Accrued");
					if(type=='P')
						document.getElementById("save").removeAttribute("disabled","true");
					else if(type=='F')
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					// alert("SAVE");
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=updateLiquidationData&type="+type;
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required");
				if(type=='P')
					document.getElementById("save").removeAttribute("disabled","true");
				else if(type=='F')
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				return false;
			}
		}
		
		if(document.getElementById("liquidationFlag").value=='F')
		{
			var gapInterest = removeComma(document.getElementById("gapInterestF").value);
			// alert("gapInterest: "+gapInterest);
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
				if((liquidationAmountPrincipal+liquidatedAmountPrincipal)>sdAmount)
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal is greater than SD Amount");
					if(type=='P')
						document.getElementById("save").removeAttribute("disabled","true");
					else if(type=='F')
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				else if((liquidationAmountPrincipal+liquidatedAmountPrincipal)!=sdAmount)
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal must be equal to SD Amount for Final Liquidation");
					if(type=='P')
						document.getElementById("save").removeAttribute("disabled","true");
					else if(type=='F')
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				else if((liquidationAmountInterest+liquidatedAmountInterest)!= (sdInterestAccrued+gapInterest))
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest is greater than Sum of Total Interest Accrued & GAP Interest");
					if(type=='P')
						document.getElementById("save").removeAttribute("disabled","true");
					else if(type=='F')
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					// alert("UPDATE");
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=updateLiquidationData&type="+type;
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required");
				if(type=='P')
					document.getElementById("save").removeAttribute("disabled","true");
				else if(type=='F')
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				return false;
			}
		}
		
	}
	else
	{
		if(type=='P')
			document.getElementById("save").removeAttribute("disabled","true");
		else if(type=='F')
			document.getElementById("saveFwd").removeAttribute("disabled","true");
		return false;
	}
}

function saveLiquidationAuthor()
{
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y')
	{
		var businessDate=document.getElementById("businessDate1").value;
		var makerDate=document.getElementById("makerDate").value;	
		var fromdate=businessDate;
		var todate=makerDate;
		var day1= fromdate.substring(0,2);
		var day2= todate.substring(0,2);
		var month1=fromdate.substring(3,5);
		var month2=todate.substring(3,5);
		var year1=fromdate.substring(6);
		var year2=todate.substring(6);
		if(parseFloat(year1) != parseFloat(year2))
		{	
			alert("Maker Date should be equal to Business Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1) != parseFloat(month2))
				{	
					alert("Maker Date should be equal to Business Date.");
					document.getElementById("save").removeAttribute("disabled","true");
	    			return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) != parseFloat(day2))
						{	
							alert("Maker Date should be equal to Business Date.");
							document.getElementById("save").removeAttribute("disabled","true");
			    			return false;
						}
					}
				}
			}
		}
	}
	
	if(document.getElementById("comments").value=='')
	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("sdLiquidationAuthorForm").action = contextPath+"/sdLiquidationAuthor.do?method=saveLiquidationAuthor";
	    document.getElementById("sdLiquidationAuthorForm").submit();
	}
}
// Amit ends


// abhimanyu
function startProcess()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("DocUpload").action=sourcepath+"/uploadAction.do?method=startProcess";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("DocUpload").submit();
		 	return true;
	}
}



function startProcessReceipt()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("docFile").focus(); 
		return false;
	}
	else
	{
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("DocUpload").action=sourcepath+"/reciptUploadProcessAction.do?method=startProcessReceipt";
			 document.getElementById("processingImage").style.display = '';
		 	document.getElementById("DocUpload").submit();
		 	return true;
	}
	DisButClass.prototype.EnbButMethod();
}



function uploadSummary()
{

 	var contextPath = document.getElementById("contextPath").value;
	var url=contextPath+"/uploadAction.do?method=uploadsummary";
	window.open(url,'selectCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
	return true;

}
function errorLogDownload()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("DocUpload").action=sourcepath+"/uploadAction.do?method=errorLogDownload";
 	document.getElementById("DocUpload").submit();
 	return true;
 		
}

function errorLogDownloadReceipt()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("DocUpload").action=sourcepath+"/reciptUploadProcessAction.do?method=errorLogDownloadReceipt";
 	document.getElementById("DocUpload").submit();
 	return true;
 		
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
	// alert("newSearchLoan"+stage);
	if(stage=='P')
	{
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
				document.getElementById("button").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("button").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	else
	{
		var allBranches=document.getElementById("allBranches").checked;
		var selectedBranch=document.getElementById("lbxBranchId").value;
		if(allBranches==true && selectedBranch!='')
		{
			alert("Select either All Branch or Selective Branch.");
			document.getElementById("button").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(dealNo!=''||loanNo!=''||agrementDate!=''||customerName!=''||product!=''||scheme!=''||reportingToUserId!='' || allBranches!=false || selectedBranch!='')
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
				document.getElementById("button").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field or select all branches.");
			document.getElementById("button").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
}

function callRepay()
{
	// alert("In callRepay");
	var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;

	if(lbxLoanNoHID!='')
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/repaymentScheduleBehindAction.do?loanId="+lbxLoanNoHID;
		// window.open(url,'Repayment
		// Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
	}
	else
	{
		alert("Please Select Loan First");
	}
}

function showEffectiveDate()
{
	// alert("In showEffectiveDate");
	if(document.getElementById("finalDisbursal").checked)
	{
		// alert("In if showEffectiveDate");
		document.getElementById("val").value="Y";
		document.getElementById("yes1").style.display="";
		document.getElementById("no1").style.display="none";
		document.getElementById("yes2").style.display="";
		document.getElementById("no2").style.display="none";
		document.getElementById("yes3").style.display="";
		document.getElementById("no3").style.display="none";
		document.getElementById("repayEffId").style.display='';
		document.getElementById("repayId").style.display='none';
		document.getElementById("cycleDateFinal").style.display='';
		document.getElementById("cycleDate").value='';
		document.getElementById("cycleDateI").style.display='none';
		document.getElementById("generateRepaymentSch").removeAttribute("disabled", "true");
		
		var contextPath=document.getElementById("contextPath").value;
		var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;
		var address = contextPath+"/ajaxAction.do?method=retriveCycleDate";
		var data = "lbxLoanNoHID="+lbxLoanNoHID;
		sendCycleDateId(address, data);
		return true;
	}
	else
	{
		// alert("In else showEffectiveDate");
		document.getElementById("val").value="N";
		document.getElementById("yes1").style.display="none";
		document.getElementById("no1").style.display="";
		document.getElementById("yes2").style.display="none";
		document.getElementById("no2").style.display="";
		document.getElementById("yes3").style.display="none";
		document.getElementById("no3").style.display="";
		document.getElementById("repayEffId").style.display='none';
		document.getElementById("repayId").style.display='';
		document.getElementById("cycleDateFinal").style.display='none';
		document.getElementById("cycleDateI").style.display='';
		document.getElementById("nextDueDate").value="";
		document.getElementById("repayEff").value="";
		document.getElementById("cycleDate").value='';
		document.getElementById("generateRepaymentSch").setAttribute("disabled", "true");
	
	}
}

function sendCycleDateId(address, data) {
	// alert("in sendCycleId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCycleDate(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultCycleDate(request){
	
	// alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
			var s1 = str.split("$:");
			document.getElementById('cycleDateFinal').innerHTML=trim(s1[0]);
	}
}
// ............Abhimanyu process......
function chkEndOfDay()
{		
		var c = document.getElementById("chk").checked;
		var ch=document.getElementsByName('chk');
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
function checkProcess()
{  
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

function endOfDayStartprocess()
{
	var bodFlag= document.getElementById("bodFlag").value;
	var bDate=document.getElementById("bDate").value;
	if(bodFlag=='E')
	{
	if(checkProcess())
	{	
		var status = confirm("SOME USERS ARE STILL LOGGED IN. DO YOU WANT TO CONTINUE?");
		if(status==true)
		{
		  var contextPath=document.getElementById("contextPath").value;
		  document.getElementById("endOfDayForm").action = contextPath+"/endOfDay.do?method=endOfProcess";
		  document.getElementById("endOfDayForm").submit();
		  return true;
		}
		else
		{
			// alert("First Logout all users to run EOD Process");
			return false;
		}
	}
	else
	{
		alert("Please select one then start process.");
		return false;
	}
	}
	else{
		alert("Please start BOD process.");
		return false;
	}
}
function processError(pname)
{
	// alert(pname);
	var contextPath = document.getElementById("contextPath").value;
	var url=contextPath+"/endOfDay.do?method=errorLink&pname="+pname;
    window.open(url,'selectCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
	return true;
}

function refreshProcess(timeoutPeriod)
{	
setTimeout("callEodBodProcess();",timeoutPeriod);
}

function callEodBodProcess()
{
		pathRefresh();
		refreshProcess(5000);
}

function pathRefresh()
{

		var contextPath=document.getElementById("contextPath").value;
		var address = contextPath+"/endOfDay.do?method=refreshGrid";	
		send_Process(address);
		 return true;
		
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

function send_Process(address) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_Process(request);
	};

	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(null);
}
function result_Process(request){
   // alert(request)
	if ((request.readyState == 4) && (request.status == 200))
	{
		var str = request.responseText;
		//alert("in refresh grid success:----");
		document.getElementById('revisedcontainer').innerHTML =str;
		
	    }
}
//Code By arun For Starts here
function submitAjaxAfterConfirm(){
	//alert("In submitAjaxAfterConfirm......");
	var contextPath=document.getElementById("contextPath").value;
	var address = contextPath+"/endOfDay.do?method=endOfProcessAfterConfirm";	
	send_ProcessAjaxAfterConfirm(address);
	 return true;
}
function send_ProcessAjaxAfterConfirm(address) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_ProcessAjaxAfterConfirm(request);
	};

	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(null);
}
function result_ProcessAjaxAfterConfirm(request){
   // alert(request)
	var contextPath=document.getElementById("contextPath").value;
	if ((request.readyState == 4) && (request.status == 200))
	{
		var strn = request.responseText;
		//trim(s1[0]);
		//alert(trim(strn));
		//alert("in ajax ProcessAjaxAfterConfirm :---");
		var msg="L";
		document.getElementById('revisedcontainer').innerHTML =strn;
		if(msg=='L')
		{
			alert('EOD Process completed!');
			parent.location=contextPath+"/logoff.do?stopUpdateQuery=";
	    }
	    			
	    }
}
//Code By arun For Starts here
function vaildateDocStage()
{
	var dealId = document.getElementById("lbxDealNo").value;
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var stage = document.getElementById("docStage").value;
	if(dealId != '')
	{
		if(stage=='PRS')
		{
			return true;
		}
		else
		{
			alert("Document Stage Invalid for Deal");
			document.getElementById("docStage").value='';
			return false;
		}
	}
// if(loanId!='')
// {
// if(stage!='PRS')
// {
// return true;
// }
// else
// {
// alert("Document Stage Invalid for Loan");
// document.getElementById("docStage").value='';
// return false;
// }
// }
}

function searchPostDisbursalDoc(type,alert1)
{
	// alert("ok");
	DisButClass.prototype.DisButMethod();
	var searchStatusPDD='';
	if(document.getElementById("searchStatusPDD")==null || document.getElementById("searchStatusPDD").value=="")
		searchStatusPDD='';
	else
		searchStatusPDD=document.getElementById("searchStatusPDD").value;
	if(document.getElementById("dealNo").value=="" && document.getElementById("loanNo").value=="" )
	{
		alert(alert1);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
	if(document.getElementById("docStage").value=="")
	{
		alert("Please Select Document Stage");
		DisButClass.prototype.EnbButMethod();
		//ocument.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("postDisbursalDocSearchForm").action = contextPath+"/postDisbursalDocSearch.do?method=searchPostDisbursalDocMaker&type="+type+"&searchStatus="+searchStatusPDD;
	    document.getElementById("processingImage").style.display = '';
		document.getElementById("postDisbursalDocSearchForm").submit();
	}
}


function savePODdocAuthor()
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("comments").value!="")
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("PODAuthorForm").action = contextPath+"/authorPOD.do?method=savePODAuthor";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("PODAuthorForm").submit();
	}
	else
	{
		alert("Comments is Required");
		//document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

}
	function viewPayableEarly(alert1) 
	{	
		if (document.getElementById("lbxLoanNoHID").value=="")
		{
			alert(alert1);	
			return false;
		}
		else
		{
			curWin = 0;
			otherWindows = new Array();
			
			var loanId=document.getElementById('lbxLoanNoHID').value;		
			var basePath=document.getElementById("contextPath").value;
			otherWindows[curWin++] =window.open(basePath+'/viewPayableEarlyClousreAction.do?&loanId='+loanId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			return true;
		}
	}
	
	function viewReceivableEarly(alert1) 
	{	
		if (document.getElementById("lbxLoanNoHID").value=="")
		{
			alert(alert1);	
			document.getElementById("recieveButton").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			curWin = 0;
			otherWindows = new Array();
			
			var loanId=document.getElementById('lbxLoanNoHID').value;	
			// alert("loanId",loanId);
			var basePath=document.getElementById("contextPath").value;
			otherWindows[curWin++] =window.open(basePath+'/viewReceivableEarlyClousreAction.do?&loanId='+loanId,'viewReceivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			return true;
	   }
	}
function calculateMaturityDate()
{
	
	var repaymentType = document.getElementById('repaymentType').value;
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('tenureMonths').value); // or
																			// whatever
																			// offset
		var formatD=document.getElementById("formatD").value;

		// alert(x);
		var currDate =   document.getElementById('repayEffectiveDate').value;
		// var currDate = new Date(repayDate);
		  // alert(currDate);
		  var currDay   = currDate.substring(0,2);
		  // alert(currDay);
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
			// alert(CurrentDate);
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
		 // alert(ModDateStr);
		  
		  document.getElementById('maturityDate1').value=ModDateStr;
	}
}

function viewReport(alert1) 
{	
	if (document.getElementById("generateBankingDate").value=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("generateReport").action = contextPath+"/generateReport.do?method=generateReport";
	    document.getElementById("generateReport").submit();
   }
}



function newAmount1()
{
	
	// waveAmount1 waivOffAmount chargeAmount
	var chargeAmount =0.0000; 
	var waivOffAmount =0.0000; 
	var adviceAmt=0.0000;
	var waveAmountForTaxAmt1=0.0000;
	var waveAmountForTaxAmt2=0.0000;
	var taxAmt1=0.0000;
	var taxAmt2=0.0000;
	var ChargeNewAmount=0.0000;
	var tax1NewAmt=0.0000;
	var tax2NewAmt=0.0000;
	var totalWaiveOff =0.0000;
	var newAdviceAmt = 0.0000;
	var taxRate1 =0.0000;
	var taxRate2 =0.0000;
	var newBalAmt=0.0000;
	var txnAdjustedAmount = 0.0000;
	var amountInProcess=0.0000;
	
	if(document.getElementById("waivOffAmount").value!="")
	{
		// taxRate1 = removeComma(document.getElementById("taxRate1").value);
		// taxRate2 = removeComma(document.getElementById("taxRate2").value);
		
		var tr1=document.getElementById("taxRate1").value;
		var tr2=document.getElementById("taxRate2").value;
		if(tr1!='')
		{
			taxRate1 = parseFloat(removeComma(tr1));
		}
		if(tr2!='')
		{
			taxRate2 = parseFloat(removeComma(tr2));
		}
		
		var chA=document.getElementById("chargeAmount").value;
		if(chA!='')
		{
			chargeAmount=parseFloat(removeComma(chA));
			
		}
		
		var wa=document.getElementById("waivOffAmount").value;
		if(wa!='')
		{
			waivOffAmount=parseFloat(removeComma(wa));
		}
		var aa=document.getElementById("adviceAmount").value;
		if(aa!='')
		{
			adviceAmt=parseFloat(removeComma(aa));
		}
		var ta1=document.getElementById("taxAmount1").value;
		if(ta1!='')
		{
			taxAmt1 = parseFloat(removeComma(ta1));
		}
		var ta2=document.getElementById("taxAmount2").value;
		if(ta2!='')
		{
			taxAmt2 = parseFloat(removeComma(ta2));
		}
		
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
		{
			txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
		}
		
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
		{
			amountInProcess = parseFloat(removeComma(amtInproce));
		}

		if(document.getElementById("waveAmountForTaxAmt1").value!='')
		{
			waveAmountForTaxAmt1 = parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt1").value));
		}
		if(document.getElementById("waveAmountForTaxAmt2").value!='')
		{
			waveAmountForTaxAmt2 = parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt2").value));
		}
		
		// waveAmountForTaxAmt1 =
		// parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt1").value));
		// waveAmountForTaxAmt2 =
		// parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt2").value));
// waveAmountForTaxAmt1 = waivOffAmount * taxRate1/100;
// waveAmountForTaxAmt2 = waivOffAmount * taxRate2/100;
		ChargeNewAmount = chargeAmount - waivOffAmount;
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		
		tax1NewAmt = taxAmt1- waveAmountForTaxAmt1;
		tax2NewAmt = taxAmt2- waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
	    
		newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
// document.getElementById("waveAmountForTaxAmt1").value = waveAmountForTaxAmt1;
// document.getElementById("waveAmountForTaxAmt2").value = waveAmountForTaxAmt2;
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		document.getElementById("ChargeNewAmount").value = ChargeNewAmount;
		document.getElementById("tax1NewAmt").value = tax1NewAmt ; 
		document.getElementById("tax2NewAmt").value = tax2NewAmt ; 
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		// alert("newBalAmt : "+newBalAmt);
		document.getElementById("newBalanceAmt").value = newBalAmt; 
		var floatChargeNewAmount = (ChargeNewAmount).toFixed(2);
		formatNumber(floatChargeNewAmount,'ChargeNewAmount');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		formatNumber(floattotalWaiveOff,'totalWaveOffAmt');
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
		// alert(floatChargeNewAmount);
		formatNumber(floatnewAdviceAmt,'newAdviceAmt');
		formatNumber(floatnewBalAmt,'newBalanceAmt');
		
	}
	
}
function newAmount2()
{
	var chargeAmount =0.0000; 
	var waivOffAmount =0.0000; 
	var adviceAmt=0.0000;
	var waveAmountForTaxAmt1=0.0000;
	var waveAmountForTaxAmt2=0.0000;
	var taxAmt1=0.0000;
	var taxAmt2=0.0000;
	var ChargeNewAmount=0.0000;
	var tax1NewAmt=0.0000;
	var tax2NewAmt=0.0000;
	var totalWaiveOff =0.0000;
	var newAdviceAmt = 0.0000;
	var txnAdjustedAmount = 0.0000;
	var amountInProcess=0.0000;
	var newBalAmt=0.0000;
	
	if(document.getElementById("waveAmountForTaxAmt1").value!="")
	{
		if(document.getElementById("waveAmountForTaxAmt2").value=="")
		{
			document.getElementById("waveAmountForTaxAmt2").value=0;
		}

		
		// waveAmountForTaxAmt1
		// =removeComma(document.getElementById("waveAmountForTaxAmt1").value);
		// waveAmountForTaxAmt2
		// =removeComma(document.getElementById("waveAmountForTaxAmt2").value);
		// chargeAmount=
		// removeComma(document.getElementById("chargeAmount").value);
		// waivOffAmount=
		// removeComma(document.getElementById("waivOffAmount").value);
		// adviceAmt=
		// removeComma(document.getElementById("adviceAmount").value);
		// taxAmt1 = removeComma(document.getElementById("taxAmount1").value);
		// taxAmt2 = removeComma(document.getElementById("taxAmount2").value);

		var wata1=document.getElementById("waveAmountForTaxAmt1").value;
		if(wata1!='')
		{
			waveAmountForTaxAmt1 =parseFloat(removeComma(wata1));
		}
		var wata2=document.getElementById("waveAmountForTaxAmt2").value;
		if(wata2!='')
		{
			waveAmountForTaxAmt2 =parseFloat(removeComma(wata2));
		}
		// waveAmountForTaxAmt2
		// =parseFloat(document.getElementById("waveAmountForTaxAmt2").value);
		var cha=document.getElementById("chargeAmount").value;
		if(cha!='')
		{
			chargeAmount =parseFloat(removeComma(cha));
		}
		// chargeAmount= parseFloat();
		var wam=document.getElementById("waivOffAmount").value;
		if(wam!='')
		{
			waivOffAmount= parseFloat(removeComma(wam));
		}
		var adm=document.getElementById("adviceAmount").value;
		if(adm!='')
		{
			adviceAmt= parseFloat(removeComma(adm));
		}
		var tm1=document.getElementById("taxAmount1").value;
		if(tm1!='')
		{
			taxAmt1 = parseFloat(removeComma(tm1));
		}
		var tm2=document.getElementById("taxAmount2").value;
		if(tm2!='')
		{
			taxAmt2 = parseFloat(removeComma(tm2));
		}
		
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
		{
			txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
		}
		
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
		{
			amountInProcess = parseFloat(removeComma(amtInproce));
		}
		// taxAmt2 = parseFloat(document.getElementById("taxAmount2").value);

		// ChargeNewAmount = chargeAmount - waivOffAmount;
	
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		
		tax1NewAmt = taxAmt1- waveAmountForTaxAmt1;
		// tax2NewAmt = taxAmt2- waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
		newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		// document.getElementById("ChargeNewAmount").value = ChargeNewAmount;
		document.getElementById("tax1NewAmt").value = tax1NewAmt ; 
		// document.getElementById("tax2NewAmt").value = tax2NewAmt ;
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		document.getElementById("newBalanceAmt").value =newBalAmt; 
		var floattax1NewAmt = (tax1NewAmt).toFixed(2);
		// alert(floatChargeNewAmount);
		formatNumber(floattax1NewAmt,'tax1NewAmt');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
		// alert(floatChargeNewAmount);
		formatNumber(floatnewAdviceAmt,'newAdviceAmt');
		formatNumber(floattotalWaiveOff,'totalWaveOffAmt');
		formatNumber(floatnewBalAmt,'newBalanceAmt');
		return true;
	}

}


function newAmount3()
{
	
	var chargeAmount =0.0000; 
	var waivOffAmount =0.0000; 
	var adviceAmt=0.0000;
	var waveAmountForTaxAmt1=0.0000;
	var waveAmountForTaxAmt2=0.0000;
	var taxAmt1=0.0000;
	var taxAmt2=0.0000;
	var ChargeNewAmount=0.0000;
	var tax1NewAmt=0.0000;
	var tax2NewAmt=0.0000;
	var totalWaiveOff =0.0000;
	var newAdviceAmt = 0.0000;
	var txnAdjustedAmount = 0.0000;
	var amountInProcess=0.0000;
	var newBalAmt=0.0000;
	
	if(document.getElementById("waveAmountForTaxAmt2").value!="")
	{
		if(document.getElementById("waveAmountForTaxAmt1").value=="")
		{
			document.getElementById("waveAmountForTaxAmt1").value=0;
		}

		// waveAmountForTaxAmt1
		// =removeComma(document.getElementById("waveAmountForTaxAmt1").value);
		// waveAmountForTaxAmt2
		// =removeComma(document.getElementById("waveAmountForTaxAmt2").value);

		var wata1=document.getElementById("waveAmountForTaxAmt1").value;
		if(wata1!='')
		{
			waveAmountForTaxAmt1 =removeComma(wata1);
		}
		var wata2=document.getElementById("waveAmountForTaxAmt2").value;
		if(wata2!='')
		{
			waveAmountForTaxAmt2 =removeComma(wata2);
		}
		// waveAmountForTaxAmt2
		// =parseFloat(document.getElementById("waveAmountForTaxAmt2").value);
		

		document.getElementById("waveAmountForTaxAmt2").value=waveAmountForTaxAmt2;

		// chargeAmount=removeComma(document.getElementById("chargeAmount").value);
		// waivOffAmount=removeComma(document.getElementById("waivOffAmount").value);
		// adviceAmt=removeComma(document.getElementById("adviceAmount").value);
		// taxAmt1 = removeComma(document.getElementById("taxAmount1").value);
		// taxAmt2 = removeComma(document.getElementById("taxAmount2").value);

		var cha=document.getElementById("chargeAmount").value;
		if(cha!='')
		{
			chargeAmount=parseFloat(cha);
		}
		var wam=document.getElementById("waivOffAmount").value;
		if(wam!='')
		{
			waivOffAmount=parseFloat(removeComma(wam));
		}
		var adm=document.getElementById("adviceAmount").value;
		if(adm!='')
		{
			adviceAmt=removeComma(adm);
		}
		var tam1=document.getElementById("taxAmount1").value;
		if(tam1!='')
		{
			taxAmt1 = parseFloat(removeComma(tam1));
		}
		var tam2=document.getElementById("taxAmount2").value;
		if(tam2!='')
		{
			taxAmt2 = removeComma(tam2);
		}
		
		
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
		{
			txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
		}
		
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
		{
			amountInProcess = parseFloat(removeComma(amtInproce));
		}
		// taxAmt2 = parseFloat(document.getElementById("taxAmount2").value);

		// ChargeNewAmount = chargeAmount - waivOffAmount;
		
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		
		// tax1NewAmt = taxAmt1- waveAmountForTaxAmt1;
		tax2NewAmt = taxAmt2- waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
		newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		// document.getElementById("ChargeNewAmount").value = ChargeNewAmount;
		// document.getElementById("tax1NewAmt").value = tax1NewAmt ;
		document.getElementById("tax2NewAmt").value = tax2NewAmt ; 
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		document.getElementById("newBalanceAmt").value = newBalAmt; 
		var floattax2NewAmt = (tax2NewAmt).toFixed(2);
		// alert(floatChargeNewAmount);
		formatNumber(floattax2NewAmt,'tax2NewAmt');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
		
		// alert(floatChargeNewAmount);
		formatNumber(floatnewAdviceAmt,'newAdviceAmt');
		formatNumber(floattotalWaiveOff,'totalWaveOffAmt');
		formatNumber(floatnewBalAmt,'newBalanceAmt');
		return true;
	}

}
	
	function setZero()
	{	
		if(document.getElementById("totalWaveOffAmt").value<=0)
		{
			document.getElementById("totalWaveOffAmt").value =0;
		}
		if(document.getElementById("ChargeNewAmount").value<=0)
		{
			document.getElementById("ChargeNewAmount").value = 0;
		}
		if(document.getElementById("tax1NewAmt").value<=0)
		{
			document.getElementById("tax1NewAmt").value = 0;
		}
		if(document.getElementById("tax2NewAmt").value<=0)
		{
			document.getElementById("tax2NewAmt").value = 0;
		}
		if(document.getElementById("newAdviceAmt").value<=0)
		{
			document.getElementById("newAdviceAmt").value = 0;
		}
		if(document.getElementById("waveAmountForTaxAmt1").value<=0)
		{
			document.getElementById("waveAmountForTaxAmt1").value = 0;
		}
		if(document.getElementById("waveAmountForTaxAmt2").value<=0)
		{
			document.getElementById("waveAmountForTaxAmt2").value = 0;
		}
		
	}
	
	
	function beginingOfDayStartprocess()
	{
		DisButClass.prototype.DisButMethod();
		var bodFlag= document.getElementById("bodFlag").value;
		var strFlag= document.getElementById("startFlag").value='Y';
				
		if(bodFlag=='B')
		{
			if(checkProcess())
			{	
				// alert(document.getElementById("beginingOfDayForm"));
				  var contextPath=document.getElementById("contextPath").value;
				  document.getElementById("beginingOfDayForm").action = contextPath+"/beginingOfDay.do?method=beginingOfProcess";
				  document.getElementById("imgBar").style.display = '';
				  document.getElementById("beginingOfDayForm").submit();
				  return true;
			}
			else
			{
				alert("Please select one then start process.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
		else{
			alert("Please start EOD process.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}

	function processBodError(pname)
	{
		// alert(pname);
		var contextPath = document.getElementById("contextPath").value;
		var url=contextPath+"/beginingOfDay.do?method=errorLink&pname="+pname;
	    window.open(url,'selectCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
		return true;
	}
	
	function refreshBodProcess(timeoutPeriod)
	{  	
	  setTimeout("callBodProcess();",timeoutPeriod);
	}

	
	function callBodProcess()
	{
		pathBodRefresh();
		refreshBodProcess(5000);
	}

	function pathBodRefresh()
	{
			var strFlag=document.getElementById("startFlag").value;
			var contextPath=document.getElementById("contextPath").value;
			var address = contextPath+"/beginingOfDay.do?method=refreshGrid&startFlag="+strFlag;	
			send_Process(address);
			//return true;
			
	}

	function makeDisbursal()
	{
			// alert("makeDisbursal");
			var contextPath = document.getElementById("contextPath").value;
			var noOfDisbursal = document.getElementById("noOfDisbursal").value;
		if(noOfDisbursal!='')
		{
			var url=contextPath+"/JSP/CMJSP/makeDisbural.jsp?noOfDisbursal="+noOfDisbursal;
		    window.open(url,'makeDisbursal','height=400,width=1000,top=200,left=250,scrollbars=yes');
			return true;
		}
		else
		{
			alert("Please Select No of Disbursal");
			return false;
		}
 }
	
	
// manas
	
	
	function deletePoolID(){
		
		DisButClass.prototype.DisButMethod();
		var contextPath=document.getElementById("contextPath").value;
		 var poolIDList=document.getElementsByName('poolIDList');		
		 var loanIDList=document.getElementsByName('loanIDList');
		 var poolID = document.getElementById("poolId").value;
		 
		   	var flag=0;
		   	var poolIDLIST='';
		   	var loanIDLIST='';
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 for(i=0;i<ch.length;i++)
		   		{
		   		   if(ch[i].checked==true)
		   			{
		   			
		   			poolIDLIST = poolIDLIST + poolIDList[i].value + "/";  
		   			
		   			loanIDLIST = loanIDLIST + loanIDList[i].value + "/";
		   			
		   			  flag=1;
		   			 	
		   			}
		   		  
		   		}
		   	 if(flag==0)
		   	 {
		   		 alert("Please select one row");
		   		DisButClass.prototype.EnbButMethod();
//		   		document.getElementById("Delete").removeAttribute("disabled");
		   		 return false;
		   	 }
		   		 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=deletePoolID&loanIDLIST="+loanIDLIST+"&poolIDLIST="+poolIDLIST+"&poolID="+poolID+"&actionType=maker";
		   	     document.getElementById("sourcingForm").submit();
		   	     return true;
		
	}
	
function deletePoolIDEdit()
{
		
	      DisButClass.prototype.DisButMethod();
		var contextPath=document.getElementById("contextPath").value;
		 var poolIDList=document.getElementsByName('poolIDList');		
		 var loanIDList=document.getElementsByName('loanIDList');
		 var poolID = document.getElementById("poolId").value;
		 
		   	var flag=0;
		   	var poolIDLIST='';
		   	var loanIDLIST='';
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 for(i=0;i<ch.length;i++)
		   		{
		   		   if(ch[i].checked==true)
		   			{
		   			
		   			poolIDLIST = poolIDLIST + poolIDList[i].value + "/";  
		   			
		   			loanIDLIST = loanIDLIST + loanIDList[i].value + "/";
		   			
		   			  flag=1;
		   			 	
		   			}
		   		  
		   		}
		   	 if(flag==0)
		   	 {
		   		 alert("Please select one row");
		   		DisButClass.prototype.EnbButMethod();
//		   		document.getElementById("Delete").removeAttribute("disabled");
		   		 return false;
		   	 }
		   		 	
		   		 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessActionNew.do?method=deletePoolIdAddEdit&loanIDLIST="+loanIDLIST+"&poolIDLIST="+poolIDLIST+"&poolID="+poolID+"&actionType=edit";
//		   		 DisButClass.prototype.EnbButMethod();
		   		document.getElementById("processingImage").style.display = '';
		   	     document.getElementById("sourcingForm").submit();
		   	     return true;
		
	}
	
	
	

	
	
	function ecsFunction(){
	
		if((document.getElementById("instrumentType").value) == "E"){
			
			document.getElementById("A").style.display="none";
			document.getElementById("B").style.display="none";
			document.getElementById("C").style.display="";
			document.getElementById("D").style.display="";
// document.getElementById("E").style.display="";
// document.getElementById("F").style.display="";
			document.getElementById("G").style.display="";
			document.getElementById("H").style.display="";
			document.getElementById("I").style.display="";
			document.getElementById("J").style.display="";
			document.getElementById("Y").style.display="none";
			document.getElementById("Z").style.display="none";
			
			document.getElementById("startingChequeNo").value="";
			document.getElementById("startingChequeNo").readOnly=true;
			document.getElementById("endingChequeNo").value="";
			document.getElementById("endingChequeNo").readOnly=true;
			document.getElementById("fromInstallment").readOnly=false;
			document.getElementById("toInstallment").readOnly=false;
			document.getElementById("fromInstallment").value="";
			document.getElementById("toInstallment").value="";
			document.getElementById("purpose").value="";
			
			
		}
       if((document.getElementById("instrumentType").value) == "Q"){
			
    	   document.getElementById("A").style.display="";
    	   document.getElementById("B").style.display="";
    	   document.getElementById("Y").style.display="";
    	   document.getElementById("Z").style.display="";
    		document.getElementById("C").style.display="none";
			document.getElementById("D").style.display="none";
// document.getElementById("E").style.display="none";
// document.getElementById("F").style.display="none";
			document.getElementById("G").style.display="none";
			document.getElementById("H").style.display="none";
			document.getElementById("I").style.display="none";
			document.getElementById("J").style.display="none";
			document.getElementById("startingChequeNo").readOnly=false;
			document.getElementById("endingChequeNo").readOnly=false;
		}
		
	}
	
	function idHide(){
		
		document.getElementById("A").style.display="";
 	    document.getElementById("B").style.display="";
 		 document.getElementById("C").style.display="none";
			document.getElementById("D").style.display="none";
			document.getElementById("E").style.display="none";
			document.getElementById("F").style.display="none";
			document.getElementById("G").style.display="none";
			document.getElementById("H").style.display="none";
			document.getElementById("I").style.display="none";
			document.getElementById("J").style.display="none";	
		
	}
// function checkZeroAmount()
// {
// var instrumentAmount =
// removeComma(document.getElementById("instrumentAmount").value);
// if(instrumentAmount == 0)
// {
// alert("Instrument Amount must be greater than Zero");
// document.getElementById("instrumentAmount").value='';
// document.getElementById("instrumentAmount").focus();
// return false;
// }
// }
	
	
	
	
	
	function saveInstrumentAuthor(){
		   // alert("saveInstrumentAuthor");
		   		
		   	var contextPath=document.getElementById("contextPath").value;
		   // alert("contextPath"+contextPath);
		   	var loanID=document.getElementById("loanID").value;
		   	
		   	if(validateInstrumentCapturingAuthorValidatorForm(document.getElementById("sourcingForm")))
		   	{
		   		if((document.getElementById("comments").value.length) > 1000){
		   			alert("You are requested to enter only 1000 characters.");
		   			
		   			document.getElementById("save").removeAttribute("disabled");
			   		return false;
		   		}
		   		
		   		document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionAuthor.do?method=saveAuthor&loanID="+loanID;
		   		 document.getElementById("sourcingForm").submit();
		   		 return true;
		   	}
		   	else
		   	{
		   		document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	}
		   	 
		   }

	function saveholdInstrumentAuthor(comments,decision){

		DisButClass.prototype.DisButMethod();	
		var contextPath=document.getElementById("contextPath").value;
		
		if(document.getElementById("comments").value==""&& !(document.getElementById("decision").value=='A')) //Edited by Nishant
			{
			   alert(comments);
			   DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
			   return false;
			   
		   }
		
		if((document.getElementById("comments").value.length) > 1000){
   			alert("You are requested to enter only 1000 characters.");
   			DisButClass.prototype.EnbButMethod();
   			//document.getElementById("save").removeAttribute("disabled");
	   		return false;
   		}
	 if(document.getElementById("decision").value==""){
		   alert(decision);
		   DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled");
		   return false;
		   
	 }
	 
			document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=saveholdInstrumentAuthor";
			document.getElementById("processingImage").style.display = ''; 
			document.getElementById("sourcingForm").submit();
			 return true;
		
		 
	}

	function savereleaseInstrumentAuthor(comment,decision){
		   
		   DisButClass.prototype.DisButMethod();
		   var contextPath=document.getElementById("contextPath").value;
		   
		   if(document.getElementById("comments").value=="" && !(document.getElementById("decision").value=='L')) //Edited by Nishant
			   {
		   	   alert(comment);
		   	   DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	   return false;
		   	   
		      }
		   
		   if((document.getElementById("comments").value.length) > 1000){
	   			alert("You are requested to enter only 1000 characters.");
	   			DisButClass.prototype.EnbButMethod();
	   			//document.getElementById("save").removeAttribute("disabled");
		   		return false;
	   		}
		   if(document.getElementById("decision").value==""){
		      alert(decision);
		      DisButClass.prototype.EnbButMethod();
		      //document.getElementById("save").removeAttribute("disabled");
		      return false;
		      
		   }
		   
		   	document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=savereleaseInstrumentAuthor";
		   	document.getElementById("processingImage").style.display = ''; 
		   	document.getElementById("sourcingForm").submit();
		   	 return true;
		   
		    
		   }

	function generateValues(){
		// alert("hi");
		 var flag=0;
		 var ch=document.getElementsByName('lbxLoanNoHID');
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
		 {
			 var contextPath=document.getElementById("contextPath").value;
			 var address = contextPath+"/ajaxAction.do?method=retriveCutInsValues";
		   
			 var data = "lbxLoanNoHID=" + id;
			 sendid(address, data);
			 
		        return true;
		 }
		 
	}

	function sendid(address, data) {
		   // alert("inb send id");
		   	var request = getRequestObject();
		   	request.onreadystatechange = function () {
		   		result(request);
		   	};
		   	request.open("POST", address, true);
		   	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		   	request.send(data);
		   }
		   function result(request){
		   
		   	if ((request.readyState == 4) && (request.status == 200)) {
		   		var str = request.responseText;
		   		  
		   		var s1 = str.split("$:");
		   		  
		   		window.opener.document.getElementById('totalInstallments').value=trim(s1[0]);
		   		window.opener.document.getElementById('installmentAmount').value=trim(s1[1]);
		   		window.opener.document.getElementById('customerName').value=trim(s1[2]);
		   		window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[3]);
		   		window.opener.document.getElementById('loanAccNo').value=trim(s1[4]);
		            window.close();
		   	}
		   	
		   }


		   function savenForPDCHoldInstrument(alert1,alert2,fwdMsg){
			   DisButClass.prototype.DisButMethod();
			   if(!confirm(fwdMsg))	 
			    {
			       	DisButClass.prototype.EnbButMethod();
			    	return false;
			    }
			   	var contextPath=document.getElementById("contextPath").value;
			   	var loanid = document.getElementById("lbxLoanNoHID").value;
			   	
			   
			   	var flag=0;
			   	var toggle=0;
			   	
			   	 var ch=document.getElementsByName('chk');
			   	 var holdReason= document.getElementsByName('holdReason');
			   	 var status = document.getElementsByName('status');
			   	 var instrumentID=document.getElementsByName('instrumentID');
			   	 var checked="";
			   	 var unchecked="";
			   	 var checkedhold="";
			   	 var uncheckedhold="";
			   	 var checkedStatus=""
			   	 var uncheckedStatus=""
			   	 var newStatus="";
			   	 var instrumentid="";
			   		
			   		 for(i=0;i<ch.length;i++){
			   			 
			   			 if(ch[i].checked==true){
			   				ch[i].value='H'; 
			   				 
			   			 }else{
			   				 ch[i].value='A';  
			   			 }
			   			
			   			
			   			if(ch[i].value==status[i].value){
			   				id=ch[i].value;
			   				  unchecked = unchecked + id +"/";
			   				  
			   				  if(holdReason[i].value==''){
			   					  uncheckedhold = uncheckedhold + "test" +"/";
			   					  
			   				  }else{
			   				  
			   					  uncheckedhold=uncheckedhold + holdReason[i].value +"/";
			   					 
			   				  }
			   				  uncheckedStatus = uncheckedStatus + status[i].value +"/";
			   				  
			   			
			   			}else{
			   				  instrumentid = instrumentid + instrumentID[i].value +"/";
			   				  
			   				  newStatus=newStatus + ch[i].value +"/";
			   				
			   				  
			   				  if(holdReason[i].value==''){
			   					  
			   					alert("Please enter the hold/unhold reason");  
			   					DisButClass.prototype.EnbButMethod();
			   					//document.getElementById("savenfor").removeAttribute("disabled");
			   				  return false;
			   				 
			   				  }else{
			   				  checkedhold = checkedhold + holdReason[i].value +"/";
			   				 
			   				  }
			   				  checkedStatus = checkedStatus + status[i].value +"/";
			   				  toggle=1;
			   				  
			   			}
			   				 
			   		 }
			   	 
			   
			   	 
			   	 if(toggle==0)
			   	 {
			   		 alert(alert2);
			   		 DisButClass.prototype.EnbButMethod();
			   		//document.getElementById("savenfor").removeAttribute("disabled");
			   		 return false;
			   	 }
			   	  
			   	
			   		 document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=savenForPDCHoldInstrument&loanID="+loanid+"&checkedhold="+checkedhold+"&checkedStatus="+checkedStatus+"&instrumentid="+instrumentid+"&newStatus="+newStatus;
			   	     document.getElementById("processingImage").style.display = '';
			   		 document.getElementById("sourcingForm").submit();
			   	     return true;
			   
			   }
		   
		   
		   function savenForReleaseInstrument(alert1,fwdMsg){
			DisButClass.prototype.DisButMethod();
			if(!confirm(fwdMsg))	 
		    {
		       	DisButClass.prototype.EnbButMethod();
		    	return false;
		    }
		   	var contextPath=document.getElementById("contextPath").value;
		   	var loanid = document.getElementById("lbxLoanNoHID").value;
		   	
		   
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 var holdReason= document.getElementsByName('holdReason');
		   	 var status = document.getElementsByName('status');
		   	 var instrumentID=document.getElementsByName('instrumentID');
		   	 var checked="";
		   	 var unchecked="";
		   	 var checkedhold="";
		   	 var uncheckedhold="";
		   	 var checkedStatus=""
		   	 var uncheckedStatus=""
		   	 var newStatus="";
		   	 var instrumentid="";
		   	 
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				ch[i].value='L'; 
		   				 flag=1;
		   				 instrumentid = instrumentid + instrumentID[i].value +"/";
		   				  
		   				  newStatus=newStatus + ch[i].value +"/";
		   				  
		   				  if(holdReason[i].value==''){
		   		          alert("Please enter the release reason");	
		   		          DisButClass.prototype.EnbButMethod();
		   		       //document.getElementById("savenfor").removeAttribute("disabled");
				   		 return false;  
		   				  }else{
		   				  checkedhold = checkedhold + holdReason[i].value +"/";
		   				 
		   				  }
		   				  checkedStatus = checkedStatus + status[i].value +"/";
		   			 }
		   				 
		   		 }
		   	 
		   	 
		   	 if(flag==0)
		   	 {
		   		 alert(alert1);
		   		 DisButClass.prototype.EnbButMethod();
		   		//document.getElementById("savenfor").removeAttribute("disabled");
		   		 return false;
		   	 }
		   
		   	
		   		 document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=savenForPDCReleaseInstrument&loanID="+loanid+"&checkedhold="+checkedhold+"&checkedStatus="+checkedStatus+"&instrumentid="+instrumentid+"&newStatus="+newStatus;
		   	     document.getElementById("processingImage").style.display = '';
		   		 document.getElementById("sourcingForm").submit();
		   	     return true;
		   
		   }
		   
		   function searchInstrument(){
			    
		   	     var contextPath=document.getElementById("contextPath").value;
		   	     var reportingToUserId=document.getElementById("reportingToUserId").value;
		   // alert("ok");
		   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
		   	   
		   		 alert("Please select atleast one criteria");
		   		document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	 }else {
		   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchInstrument";
		   	 document.getElementById("sourcingForm").submit();
		   	 		 return true;
		   	 }
		   }

		   
		   
		   function saveLoanInPoolID(){
			    
		   	 var contextPath=document.getElementById("contextPath").value;
		   	 var poolID=document.getElementById("poolID").value;		   
			 if((document.getElementById("loanAccNo").value =='')){
			   	   
		   		 alert("Please select Loan Number Lov");
		   		document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	 }else {
		   	 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=saveLoanInPoolID&poolID="+poolID;
		   	 document.getElementById("sourcingForm").submit();
		   	 
		   	 return true;
		   	 }
		   }
		   
		   
		   
		   function searchDeleteMaker(){
			    DisButClass.prototype.DisButMethod();
		   	     var contextPath=document.getElementById("contextPath").value;

		   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		   	   
		   		 alert("Please select atleast one criteria");
		   		 DisButClass.prototype.EnbButMethod();
		   		//document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	  
		   	 }else {
		   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchDeleteMaker";
		   	 document.getElementById("processingImage").style.display = '';
		   	 document.getElementById("sourcingForm").submit();
		   	 		 return true;
		   	 }
		   }
		   
		   function searchDeleteAuthor(){
			    DisButClass.prototype.DisButMethod();
		   	     var contextPath=document.getElementById("contextPath").value;

		   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value=='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		   	   
		   		 alert("Please select atleast one criteria");
		   		 DisButClass.prototype.EnbButMethod();
		   		//document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	 }else {
		   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchDeleteAuthor";
		   	 document.getElementById("processingImage").style.display = '';
		   	 document.getElementById("sourcingForm").submit();
		   	 		 return true;
		   	 }
		   }

		   function searchInstrumentAuthor(){
		   	 
		       var contextPath=document.getElementById("contextPath").value;
		       var reportingToUserId=document.getElementById("reportingToUserId").value;
		   // alert("ok");
		   if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
		     
		   	 alert("Please select atleast one criteria");
		   	document.getElementById("save").removeAttribute("disabled");
		   	return false;
		    
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchInstrumentAuthor";
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }
		   
		   function searchholdInstrumentMaker(alert1){
			   DisButClass.prototype.DisButMethod();	 
		       var contextPath=document.getElementById("contextPath").value;
		       // var
				// reportingToUserId=document.getElementById("reportingToUserId").value;
		   
		   if((document.getElementById("loanAccNo").value =='')  && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		     
		   	 alert(alert1);
		   	 DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	return false;
		    
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchHoldInstrumentMaker";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }
		   
		   function searchreleaseMaker(alert1){
			   	DisButClass.prototype.DisButMethod(); 
		       var contextPath=document.getElementById("contextPath").value;
		       // var
				// reportingToUserId=document.getElementById("reportingToUserId").value;
		   
		   if((document.getElementById("loanAccNo").value =='')  && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		     
		   	 alert(alert1);
		   	 DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	return false;
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchReleaseInstrumentMaker";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }
		   

		   function searchholdInstrumentAuthor(alert1){
		   	 DisButClass.prototype.DisButMethod();
		       var contextPath=document.getElementById("contextPath").value;
		       // var
				// reportingToUserId=document.getElementById("reportingToUserId").value;
		   
		   if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') &&  (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		     
		   	 alert(alert1);
		   	 DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	return false;
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchHoldInstrumentAuthor";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }
		   
		   
		   function searchreleaseAuthor(){
		   	 DisButClass.prototype.DisButMethod();
		       var contextPath=document.getElementById("contextPath").value;
		       // var
				// reportingToUserId=document.getElementById("reportingToUserId").value;
		   
		   if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
		     
		   	 alert("Please select atleast one criteria");
		   	 DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	return false;
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchReleaseInstrumentAuthor";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }





		   function searchIndiHoldInstrument(){
			   DisButClass.prototype.DisButMethod();	 
		       var contextPath=document.getElementById("contextPath").value;
		       var id=document.getElementById('lbxLoanNoHID').value;
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchIndiHoldInstrument&loanID="+id;
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   
		   }
		   
		   function searchIndiReleaseInstrument(){
			   DisButClass.prototype.DisButMethod();	 	 
		       var contextPath=document.getElementById("contextPath").value;
		       var id=document.getElementById('lbxLoanNoHID').value;
		       
		   document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=searchIndiReleaseInstrument&loanID="+id;
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   
		   }
		   function searchIndiDeleteInstrument(){
			   DisButClass.prototype.DisButMethod();	 
		       var contextPath=document.getElementById("contextPath").value;
		       var id=document.getElementById('lbxLoanNoHID').value;
		   document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchIndiDeleteInstrument&loanID="+id;
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   
		   }


		   function newInstrument(){
		   	
		   	var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=newInstrument";
		   	document.getElementById("sourcingForm").submit();
		   	     return true;
		   	
		   }

		   function newPoolIdMaker(){
			   
			   DisButClass.prototype.DisButMethod();
			   	var contextPath=document.getElementById("contextPath").value;
			   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=newPoolIdMaker";
			   	document.getElementById("processingImage").style.display = '';
			   	document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   
		   function searchForPoolIdMaker(){
			 
			   DisButClass.prototype.DisButMethod();
			   if((document.getElementById("poolID").value =='') && (document.getElementById("poolName").value =='') && (document.getElementById("poolCreationDate").value=='') && (document.getElementById("cutOffDate").value=='') && (document.getElementById("instituteID").value =='') && (document.getElementById("userName").value =='')){
				     
				   	 alert("Please select atleast one criteria");
				   	DisButClass.prototype.EnbButMethod();
//				   	document.getElementById("search").removeAttribute("disabled");
				   	return false;
				   }else {
			   	var contextPath=document.getElementById("contextPath").value;
			   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=searchPoolIdMaker";
			   	document.getElementById("processingImage").style.display = '';
			   	document.getElementById("sourcingForm").submit();
			   	     return true;
				   }
			   }
		   function editPoolDetail(a)
			{
			  
				var basePath=document.getElementById("contextPath").value;
				document.getElementById("sourcingForm").action=basePath+"/poolIdMakerProcessAction.do?method=getPoolSearchedData&poolID="+a;
				document.getElementById("sourcingForm").submit();
				
			}
           function forwardOnNew(){
        	   DisButClass.prototype.DisButMethod();
        	   alert("Please save the data first");
        	   DisButClass.prototype.EnbButMethod();
        	   return false;
           }
		   
		   function forwardPoolIdMaker(){
			   	
			   	var contextPath=document.getElementById("contextPath").value;
			   	var poolID = document.getElementById("poolID").value;		
			   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=forwardPoolIdMaker&poolID="+poolID;
			   	document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   
		   function searchForPoolIdAuthor(){
			   
			    DisButClass.prototype.DisButMethod();
		   	    var contextPath=document.getElementById("contextPath").value;
			   	document.getElementById("sourcingForm").action = contextPath+"/poolIdAuthorProcessAction.do?method=searchPoolIdAuthor";
			   	document.getElementById("processingImage").style.display = '';
			   	document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   function errorLogPoolIdMaker(){
			   
			   var sourcepath=document.getElementById("contextPath").value;
				document.getElementById("sourcingForm").action=sourcepath+"/poolCreationProcessAction.do?method=errorLogPoolIdMaker";
			 	document.getElementById("sourcingForm").submit();
			 	return true;
			 		
			   
		   }
		  
			function onSaveOfPoolIdAuthor(alert1){
				DisButClass.prototype.DisButMethod();
			 	var poolID = document.getElementById("poolID").value;
				// var LoanNoHID =
				// document.getElementById("lbxLoanNoHID").value;
				if((document.getElementById("comments").value=="") || (document.getElementById("decision").value=="" ))
				   {
					alert(alert1);
					DisButClass.prototype.EnbButMethod();
//					 document.getElementById("save").removeAttribute("disabled");
					return false;
				   }
				else{	
				var basePath=document.getElementById("contextPath").value;
				document.getElementById("poolAuthorForm").action = basePath+"/poolIdAuthorProcessAction.do?method=onSavePoolIdAuthor&poolID="+poolID;
				document.getElementById("processingImage").style.display = '';
			    document.getElementById("poolAuthorForm").submit();
				 return true;		 
				
				}
				   } 
			
			 function searchForPoolIdAddEdit(){
				 
				 DisButClass.prototype.DisButMethod();
 if((document.getElementById("poolID").value =='') && (document.getElementById("poolName").value =='') && (document.getElementById("poolCreationDate").value=='') && (document.getElementById("cutOffDate").value=='') && (document.getElementById("instituteID").value =='')){
				     
				   	 alert("Please select atleast one criteria");
				   	DisButClass.prototype.EnbButMethod();
//				   	document.getElementById("search").removeAttribute("disabled");
				   	return false;
				   }else {
				   	var contextPath=document.getElementById("contextPath").value;
				   	document.getElementById("sourcingForm").action = contextPath+"/poolIdAddEditProcessAction.do?method=searchPoolIdAddEdit";
				   	document.getElementById("processingImage").style.display = '';
				   	document.getElementById("sourcingForm").submit();
				   	     return true;
				   }
				   }
			 
			 function poolAddEditDetail(a)
				{
				   
					var basePath=document.getElementById("contextPath").value;
					document.getElementById("sourcingForm").action=basePath+"/poolIdAddEditProcessAction.do?method=searchedPoolEditData&poolID="+a;
					document.getElementById("sourcingForm").submit();
					
				}
			 
			 function onAddPoolIDEdit()
			   {  
			   	
			     var contextPath=document.getElementById("contextPath").value; 
			      var poolID= document.getElementById("poolID").value; 
			      if(!window.child || window.child.close){
			       window.child = window.open(""+contextPath+"/poolIdAddEditProcessAction.do?method=openWindowPoolIDEdit&poolID="+poolID+"",'PoolPopUP',"height=500,width=950,status=yes,toolbar=no,menubar=no,location=center");
			   }
			   }
			 
			 function saveLoanInPoolIDEdit(){
				   
			   	 var contextPath=document.getElementById("contextPath").value;
			   	 var poolID=document.getElementById("poolID").value;	
			   	 var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
				 if((document.getElementById("loanAccNo").value =='')){
				   	   
			   		 alert("Please select Loan Number Lov");
			   		document.getElementById("save").removeAttribute("disabled");
			   		return false;
			   	 }else {
			   	 document.getElementById("sourcingForm").action = contextPath+"/poolIdAddEditProcessAction.do?method=saveLoanInPoolIDEdit&poolID="+poolID+"&lbxLoanNoHID="+lbxLoanNoHID;
			   
			   	 document.getElementById("sourcingForm").submit();
			   	 
			   	 return true;
			   	 }
			   }
			 
		   function editInstrument(id){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   // alert("id"+id);
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=updateInstrument&loanID="+id;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   function editHoldInstrument(id){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   // alert("id"+id);
			   		 document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=updateHoldInstrument&loanID="+id;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   
		   
		   
		   
		   function loanAccountPopUp(){
		   	 
		   	var contextPath=document.getElementById("contextPath").value;
		    	window.open(""+contextPath+"/instrumentCapProcessActionforNew.do?method=openPopForLoanAccount",'LoanAccountPopUP',"height=300,width=600,status=yes,toolbar=no,menubar=no,location=center");
		    	
		   }
		   function deletePDCInstrument(alert1){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   	var loanid = document.getElementById("lbxLoanNoHID").value;
			   
			   	var flag=0;
			   	 var ch=document.getElementsByName('chk');
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
			   		 alert(alert1);
			   		document.getElementById("del").removeAttribute("disabled");
			   		 return false;
			   	 }
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=deleteInstrument&id="+loanid;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
		   
		   function editInstrumentAuthor(id){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   // alert("id"+id);
			   		
			   	document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=instrumentAuthor&id="+id;
			       document.getElementById("sourcingForm").submit();
			       return true;
			   }


		   function editHoldInstrumentAuthor(id){
		   
		   	var contextPath=document.getElementById("contextPath").value;
		   // alert("contextPath"+contextPath);
		   // alert("id"+id);
		   		
		   	document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=holdInstrumentAuthor&id="+id;
		       document.getElementById("sourcingForm").submit();
		       return true;
		   }

		   function editReleaseInstrumentAuthor(id){
		   
		   	var contextPath=document.getElementById("contextPath").value;
		   // alert("contextPath"+contextPath);
		   // alert("id"+id);
		   		
		   	document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=editReleaseInstrumentAuthor&id="+id;
		       document.getElementById("sourcingForm").submit();
		       return true;
		   }
		   
		   function editReleaseInstrumentMaker(id){
		   
		   	var contextPath=document.getElementById("contextPath").value;
		   // alert("contextPath"+contextPath);
		   // alert("id"+id);
		   		
		   	document.getElementById("sourcingForm").action = contextPath+"/holdInstrumentBankingMakerMain.do?method=editReleaseInstrumentMaker&loanID="+id;
		       document.getElementById("sourcingForm").submit();
		       return true;
		   }


		   
		   function validate(){
		   	
		   	
		   	if((parseInt(document.getElementById("totalInstallments").value)) < (parseInt(document.getElementById("toInstallment").value)) ){
		   		alert("Installments should not be exceeded to total installments.");
		   		document.getElementById("toInstallment").value = "";
		   	
		   		return false;
		   	}
		   	
		   
		   	if((parseInt(document.getElementById("fromInstallment").value)) > (parseInt(document.getElementById("toInstallment").value ))){
		   		alert("From Installment should not be greater than To Installment.");
		   		document.getElementById("toInstallment").value ="";
		   		
		   		return false;
		   	}
		   	
		   	
		   }

		   function validateChqIns(){
			   	
			   	if(document.getElementById("purpose").value == "Installments"){
			   	
			   	var toInstallment = parseInt(document.getElementById("toInstallment").value);
			   	var fromInstallment = parseInt(document.getElementById("fromInstallment").value); 
			   	var startingChequeNo = parseInt(document.getElementById("startingChequeNo").value); 
			   	var endingChequeNo = parseInt(document.getElementById("endingChequeNo").value);
			   	
			   	
			   	if(endingChequeNo < startingChequeNo ){
			   		alert("Statrting Cheque no cannot be greater than Ending Cheque.");
			   		return false;
			   			
			   	}
			   	 
			   	if((document.getElementById("fromInstallment").value) == '' && (document.getElementById("toInstallment").value) == '' ) {
			   		
			   		alert("Enter From and To Installments");
			   		document.getElementById("endingChequeNo").value="";
			   		return false;
			   	}
			   	
			      
			       
			   	if((toInstallment - fromInstallment) != (endingChequeNo - startingChequeNo) ){
			   		
			   		alert("Cheques are not equivallent to Installments.");
			   		document.getElementById("endingChequeNo").value="";
			   		document.getElementById("endingChequeNo").focus();
			   		return false;
			   		
			   	}
			   	}else{
			   		if(endingChequeNo < startingChequeNo ){
			   			alert("Statrting Cheque no cannot be greater than Ending Cheque.");
			   			return false;
			   				
			   		}
			   	}
			}


		   function disableFromTo()
		   {
		   	
		   	
		   	if((document.getElementById("purpose").value)=="PDC"  || (document.getElementById("purpose").value)=="" ){
		   		
		   		
		   		document.getElementById("fromInstallment").readOnly=false;
		   		document.getElementById("fromInstallment").value='';
		   		document.getElementById("toInstallment").readOnly=false;
		   		document.getElementById("toInstallment").value='';
		   		return true;
		   		
		   	}else{
		   		
		   		document.getElementById("fromInstallment").readOnly=true;
		   		document.getElementById("fromInstallment").value=0;
		   		document.getElementById("toInstallment").readOnly=true;
		   		document.getElementById("toInstallment").value=0;
		   		return true;
		   	}
		   	
		   }   
		   
		   
		   function addPoolID()
		   {  
		   	
		     var contextPath=document.getElementById("contextPath").value; 
		      var poolID= document.getElementById("poolID").value; 
		      if(!window.child || window.child.close){
		       window.child = window.open(""+contextPath+"/poolIdMakerProcessAction.do?method=openWindowForaddPoolID&poolID="+poolID+"",'PoolPopUP',"height=500,width=950,status=yes,toolbar=no,menubar=no,location=center");
		   }
		   }
		   
		   function fnDetailsforLinkedlan(id,date,installmentAmount,instrumentAmount,instrumentID,installmentNo)
		   {  
		   	
		    	var contextPath=document.getElementById("contextPath").value; 
		      var loanAccNo= document.getElementById("loanAccNo").value; 
		      var totalInstallments=document.getElementById("totalInstallments").value;
		      var restAllocatedAmount = removeComma(instrumentAmount) - removeComma(installmentAmount);
		   	
		      if(!window.child || window.child.close){
		       window.child = window.open(""+contextPath+"/instrumentCapProcessActionforNew.do?method=openWindowForLnkedLoan&id="+id+"&installmentAmount="+installmentAmount+"&date="+date+"&loanAccNo="+loanAccNo+"&installmentNo="+installmentNo+"&instrumentAmount="+instrumentAmount+"&instrumentID="+instrumentID+"&restAllocatedAmount="+restAllocatedAmount+"",'ProductPopUP',"height=250,width=950,status=yes,toolbar=no,menubar=no,location=center");
		   }
		   }

		   function closeLOVWindow()
		   {
		       if (openLOVWindows && !openLOVWindows.closed) 
		         openLOVWindows.close();
		   }

		   function saveLinkedLoan()
		   {
		   	var allotedAmountAdd=0;
		   	var contextPath=document.getElementById("contextPath").value; 
		   	var instrumentAmount = removeComma(document.getElementById("instrumentAmount").value); 
		   	var instrumentID = document.getElementById("instrumentID").value; 
		   	
		   	var loanId = "";
		   	var date ="";
		   	var totalInstallments="";
		   	var installmentAmountMain="";
		   	var allotedAmount="";
		   	
		    var allotedAmount1 = document.getElementById("allotedAmount1").value;
		  	var allotedAmount2=document.getElementById("allotedAmount2").value;
			var allotedAmount3=document.getElementById("allotedAmount3").value;
		    var installmentNo="";
		   	
		   	
		   	
		   	
		  
		   if(document.getElementById("lbxLoanNoHID").value == ""){
			   alert("Please select atleast one loan.");
			   return false;
		   }
		   	
		  if(document.getElementById("allotedAmount1").value == ""){
			  alert("Please allocate the amount");
			  return false;
		  }
		   	
		   if(document.getElementById("lbxLoanNoHID1").value != ""){
			   
			   if(document.getElementById("allotedAmount2").value == ""){
				   alert("Please allocate the amount");
				   return false;
			   }
		   }
		   	
	       if(document.getElementById("lbxLoanNoHID2").value != ""){
			   
			   if(document.getElementById("allotedAmount3").value == ""){
				   alert("Please allocate the amount");
				   return false;
			   }
		   }
		       if(allotedAmount1){
		   		
		   	 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount1) ;
		   	}
		   	
		   	if(allotedAmount2){
		   			
		   		 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount2) ;
		   	}
		   
		   	if(allotedAmount3){
		   		
		   		 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount3) ;
		   	}
		   	
		 
		  
		       if((document.getElementById("lbxLoanNoHID").value) != ''){
		   		
		   		loanId = loanId + document.getElementById("lbxLoanNoHID").value + "/";
		   		
		   	}
		     if((document.getElementById("lbxLoanNoHID1").value) != ''){
		   		
		   		loanId = loanId + document.getElementById("lbxLoanNoHID1").value + "/";
		   		
		   	}
		   	
		     if((document.getElementById("lbxLoanNoHID2").value) != ''){
		   		
		   		loanId = loanId + document.getElementById("lbxLoanNoHID2").value + "/";
		   		
		   	}
		   	
		  

		       if((document.getElementById("date").value) != ''){
		   		
		       	date = date + document.getElementById("date").value + "/";
		   		
		   	}
		     if((document.getElementById("date1").value) != ''){
		   		
		   	  date = date + document.getElementById("date1").value + "/";
		   		
		   	}
		   	
		     if((document.getElementById("date2").value) != ''){
		   		
		   	  date = date + document.getElementById("date2").value + "/";
		   		
		   	}
		
		   	
		   	
		       if((document.getElementById("installmentNo").value) != ''){
		   		
		       	installmentNo = installmentNo + document.getElementById("installmentNo").value + "/";
		   		
		   	}
		     if((document.getElementById("installmentNo1").value) != ''){
		   		
		   	  installmentNo = installmentNo + document.getElementById("installmentNo1").value + "/";
		   		
		   	}
		   	
		     if((document.getElementById("installmentNo2").value) != ''){
		   		
		   	  installmentNo = installmentNo + document.getElementById("installmentNo2").value + "/";
		   		
		   	}
		   	
		   	
		   	
		   	
		   	
		   	    if((document.getElementById("installmentAmount").value) != ''){
		   			
		   	    	installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount").value + "/";
		   			
		   		}
		   	  if((document.getElementById("installmentAmount1").value) != ''){
		   			
		   		  installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount1").value + "/";
		   			
		   		}
		   		
		   	  if((document.getElementById("installmentAmount2").value) != ''){
		   			
		   		  installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount2").value + "/";
		   			
		   		}
		   	

		   	    if((document.getElementById("allotedAmount1").value) != ''){
		   			
		   	    	allotedAmount = allotedAmount + document.getElementById("allotedAmount1").value + "/";
		   			
		   		}
		   	  if((document.getElementById("allotedAmount2").value) != ''){
		   			
		   		  allotedAmount = allotedAmount + document.getElementById("allotedAmount2").value + "/";
		   			
		   		}
		   		
		   	  if((document.getElementById("allotedAmount3").value) != ''){
		   			
		   		  allotedAmount = allotedAmount + document.getElementById("allotedAmount3").value + "/";
		   			
		   		}
		   	
		   	  
		   	 
		   	  
		   	  
		   	  
		   	 var restAllocatedAmount =  removeComma(document.getElementById("restAllocatedAmount").value);
		   	 
		   	 
		   	 
		   	 if( (document.getElementById("datemain").value) != (document.getElementById("date").value)){
		   		 alert("Date would be same");
		   		 return false;
		   		 
		   	 }
		   	 
		   	 if((document.getElementById("date1").value) != ""){
		   		 
		   	if( (document.getElementById("datemain").value) != (document.getElementById("date1").value)){
		   		 alert("Date would be same");
		   		 return false;
		   		 
		   	 }
		   	
		   	 }
		   	 if((document.getElementById("date2").value) != ""){
		   	
		   	if( (document.getElementById("datemain").value) != (document.getElementById("date2").value)){
		   		 alert("Date would be same");
		   		 return false;
		   	} 
		   	 }
		   	 
		   	 if((restAllocatedAmount) != (allotedAmountAdd)){
		   		 
		   		 alert("Allocated amount is not equal to amount left.");
		   		 return false;
		   		 
		   	 }
		   	
		 
		   	
		   	
		  document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=saveForLinkedLan&loanId="+loanId+"&date="+date+"&installmentNo="+installmentNo+"&installmentAmountMain="+installmentAmountMain+"&allotedAmount="+allotedAmount+"&instrumentID="+instrumentID;
		  document.getElementById("sourcingForm").submit();

		       
		       return true;
		 
		     
		   }
		   function searchChequeStatus(){
			   DisButClass.prototype.DisButMethod();
			  
			   	var contextPath=document.getElementById("contextPath").value;
			   	var txnType=document.getElementById("txnType").value;
			   	
			   	if(((document.getElementById("txnType").value) == 'LIM') && ((document.getElementById("lbxLoanNoHID").value) == '') && ((document.getElementById("instrumentDate").value) == '') && ((document.getElementById("instrumentNo").value) == '') && ((document.getElementById("receivedDate").value) == ''))
			   	{
		   			alert("Loan Number / Instrument No / Instrument Date / Receipt/Payment Date is required.");
		   			DisButClass.prototype.EnbButMethod();
		   			return false;
			   	}
			   	if(((document.getElementById("txnType").value) == 'DC') && ((document.getElementById("lbxDealNo").value) == '') && ((document.getElementById("instrumentDate").value) == '') && ((document.getElementById("instrumentNo").value) == '') && ((document.getElementById("receivedDate").value) == ''))
			   	{
		   			alert("Deal Number / Instrument No / Instrument Date /  Receipt/Payment Date is required.");
		   			DisButClass.prototype.EnbButMethod();
		   			return false;
			   	}		   		
			   	if((document.getElementById("instrumentType").value) == 'P'){
			   		
			   		if(((document.getElementById("chequeStatus").value) == 'D') || ((document.getElementById("chequeStatus").value) == 'B')){
			   			alert("Deposit and Bounce are not allowed if you choose Payment type");
			   			DisButClass.prototype.EnbButMethod();
			   			return false;
			   			
			   		}
			   		
			   		if(((document.getElementById("paymentMode").value) == 'B') || ((document.getElementById("paymentMode").value) == 'E')){
			   			alert("Direct Debit and ECS are not allowed if you choose Payment type");
			   			DisButClass.prototype.EnbButMethod();
			   			return false;
			   			
			   		}
			   		
			   		
			   	}
	         if((document.getElementById("instrumentType").value) == 'R'){
			   		
			   		if(((document.getElementById("chequeStatus").value) == 'S') || ((document.getElementById("chequeStatus").value) == 'C')){
			   			alert("Sent to customer and Stop Payment are not allowed if you choose Receipt type");
			   			DisButClass.prototype.EnbButMethod();
			   			return false;
			   			
			   		}
			   		

// if(((document.getElementById("paymentMode").value) == 'R') ||
// ((document.getElementById("paymentMode").value) == 'N')){
// alert("RTGS and NEFT are not allowed if you choose Receipt type");
// return false;
//			   			
// }
//			   		


			   		
			   	}
			   
			    	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=searchChequesByPayment&txnType="+txnType;
			    	document.getElementById("processingImage").style.display='block'; 
			    	document.getElementById("sourcingForm").submit();
			   
			   		 
		
			   	     return true;
			   		
			   	
			   }
		   
		   function chequeStatusforloanViewer()
		   {
			   var id=document.getElementsByName("radioId");
			   var loanid=document.getElementsByName("lbxLoanNoHIDmain");
			   var dealid=document.getElementsByName("lbxDealNomain");
			   var loanNo=document.getElementsByName("loanNumbermain");
			   var cuName=document.getElementsByName("customerNamemain");
			   var loanIDMain ='';
			   var dealIdMain ='';
			   var loan ='';
			   var name ='';
			   
			   var contextPath=document.getElementById("contextPath").value;
			   
			   for(var i=0; i< id.length ; i++)
			   {				   
				   if(id[i].checked == true){
					   
					   loanIDMain = loanid[i].value;
					   dealIdMain = dealid[i].value;
					   loan=loanNo[i].value;
					   name=cuName[i].value;
					  				   	   
				   }				   
			   }			
			   var url= contextPath+"/searchCMBehindAction.do?method=chequeStatusForLoanViewer&loanId="+loanIDMain+"&loanNumber="+loan+"&customerName="+name;
			   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			   mywindow.moveTo(800,300);
			   if (window.focus) 
			   {
					mywindow.focus();
					return false;
			   }
			   return true;
		}
		   
		   function dealViewer()
		   {
			   otherWindows = new Array();
			   curWin = 0;
			   var id=document.getElementsByName("radioId");
			   var dealid=document.getElementsByName("lbxDealNomain");
			   var dealIdMain ='';
			   var contextPath=document.getElementById("contextPath").value;

			   for(var i=0; i< id.length ; i++)
			   {
		   
				   if(id[i].checked == true){
					   dealIdMain = dealid[i].value;

				   }				   
			   }
			   var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+dealIdMain+"&status=UWA&fromCM=cm";
			   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			   otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
			   mywindow.moveTo(800,300);
			   if (window.focus) 
			   {
					mywindow.focus();
					return false;
			   }
			   return true;
		  }
		   
		   function searchchequeStatusforloanViewer()
		   {  
			   var loanId=document.getElementById("loanId").value;
			   var loan=document.getElementById("loanAccNo").value;
			   var name=document.getElementById("customerName").value;
			   var contextPath=document.getElementById("contextPath").value;
			   document.getElementById("sourcingForm").action = contextPath+"/searchCMBehindAction.do?method=searchChequeStatusForLoanViewer&loanId="+loanId+"&loanNumber="+loan+"&customerName="+name;
			  document.getElementById("sourcingForm").submit();
		   	     return true;
			}
	   function searchChequeStatuss(id){
			 
			   	var contextPath=document.getElementById("contextPath").value;
			   			   
					document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=searchChequesByPayment&page="+id;
					document.getElementById("sourcingForm").submit();
			   	     return true;
			   		
			   	
			   }
	   
	  
	   function sentToCustomerChequeStatus(){
		   
		   DisButClass.prototype.DisButMethod();
		   	var contextPath=document.getElementById("contextPath").value;
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 var pdcFlag = document.getElementById("pdcFlag").value;
		   	 var instrumentType = document.getElementById("instrumentType").value;
		   	 var instrumentNo= document.getElementsByName('instrumentNo1');
		   	
		   	 var date = document.getElementsByName('date1');
		   	 var instrumentAmount=document.getElementsByName('instrumentAmount1');	
		   	 var status=document.getElementsByName('status1');
		     var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
		   	 var lbxBPNID= document.getElementsByName('lbxBPNID1');
		   	 var lbxBankID = document.getElementsByName('lbxBankID1');
		   	 var lbxBranchID=document.getElementsByName('lbxBranchID1');
		   	 var tdsAmount=document.getElementsByName('tdsAmount1');
		   	var lbxReasonHID=document.getElementsByName('lbxReasonHID');
		   	var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
		   
		   	var checkedinstrumentNo="";
		   	var checkeddate="";
		   	var checkedinstrumentAmount="";
		   	
		   	var checkedstatus="";
		   	var checkedlbxBPTypeHID="";
		   	var checkedlbxBPNID="";
		   	var checkedlbxBankID="";
		   	var checkedlbxBranchID="";
		   	var instrumentID="";
		   	var tdsAmountList="";
		   	var checkedlbxReasonHID="";
		   	var buttonstatus="STC";
		   	var pdcInstrumentIdList ="";

			var depositBankIdList ="";
			var depositBranchIdList="";
			var depositMicrCodeList="";
			var depositIfscCodeList="";
			var depositBankAccountList ="";
			var reasonRemarksList ="";
			
			var depositBankId = document.getElementsByName('depositBankId');
		    var depositBranchId = document.getElementsByName('depositBranchId');
		    var depositMicrCode = document.getElementsByName('depositMicrCode');
		    var depositIfscCode = document.getElementsByName('depositIfscCode');
		    var depositBankAccount = document.getElementsByName('depositBankAccount'); 
		    var reasonRemarks = document.getElementsByName('reasonRemarks');
		   	
		   	
		 // Start change here by Prashant
			var valueDate = document.getElementsByName('valueDate');
			var checkedvalueDate="";
			var chvalueDate="";
			var hideDate = document.getElementsByName('hideDate');
			var checkedhideDate="";
			
			 // End change here by Prashant
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				instrumentID=instrumentID + ch[i].value + "/";
		   				
						depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
		   				depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
		   				depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
		   				depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
		   				depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   				
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				 
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				//alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
		   				if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
		  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
		   				else
		   				reasonRemarksList=reasonRemarksList+"/";
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if((status[i].value) == 'A'){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
						alert("You can send it to the customer, only if it is Approved.");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("sentToCustomer").removeAttribute("disabled","true");
						return false;
						}
							
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
			   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
			   				}else{
			   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
			   				}
		   				
		   				if(lbxReasonHID[i].value==''){
		   					checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
			   				 
			   				  }else{
			   					checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
			   				 
			   				  }
		   			// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   			   // alert("Value Date: "+chvalueDate+" Hidden Date: "+checkedhideDate+" businessdate: "+businessdate);
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Payment Date of Instrument.");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant	   				
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("sentToCustomer").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	 
		   	 
		   
		   	 
		   	var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
		   	
		   	 if(confrm){
		   		 
		   	//Ravi Start
				  
				    if(document.getElementById("loanRecStatus").value!='')
				    {
				    	DisButClass.prototype.EnbButMethod();
				    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
				    	{
				    		var status = confirm("Loan is on pending stage. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("sentToCustomer").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}else if(document.getElementById("loanRecStatus").value=='C')
				    	{
				    		var status = confirm("Loan is close. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("sentToCustomer").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}
				    	
				    }
				  
				    //Ravi End
		   // alert("pdcInstrumentIdList: "+pdcInstrumentIdList);
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
			document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("sentToCustomer").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	 
		   	 
		   	
		  }
  function realizeChequeStatus()  {
		   
	  DisButClass.prototype.DisButMethod();
	  
		   	var contextPath=document.getElementById("contextPath").value;
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 var pdcFlag = document.getElementById("pdcFlag").value;
		   	 var instrumentType = document.getElementById("instrumentType").value;
		   	 var instrumentNo= document.getElementsByName('instrumentNo1');
		   	 var date = document.getElementsByName('date1');
		   	 var instrumentAmount=document.getElementsByName('instrumentAmount1');	
		   	 var status=document.getElementsByName('status1');
		     var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
		   	 var lbxBPNID= document.getElementsByName('lbxBPNID1');
		   	 var lbxBankID = document.getElementsByName('lbxBankID1');
		   	 var lbxBranchID=document.getElementsByName('lbxBranchID1');
		   	 var tdsAmount=document.getElementsByName('tdsAmount1');

		   	var lbxReasonHID=document.getElementsByName('lbxReasonHID');
		   	var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
		   	
		   	var checkedinstrumentNo="";
		   	var checkeddate="";
		   	var checkedinstrumentAmount="";
		   	
		   	var checkedstatus="";
		   	var checkedlbxBPTypeHID="";
		   	var checkedlbxBPNID="";
		   	var checkedlbxBankID="";
		   	var checkedlbxBranchID="";
			var instrumentID="";
		   	var tdsAmountList="";
		   	var checkedlbxReasonHID="";
		   	var buttonstatus="RBP";
		   	var pdcInstrumentIdList ="";
		   	
		   	
		   	
			var depositBankIdList ="";
			var depositBranchIdList="";
			var depositMicrCodeList="";
			var depositIfscCodeList="";
			var depositBankAccountList ="";
			var reasonRemarksList ="";

			var reasonRemarks = document.getElementsByName('reasonRemarks');
			var depositBankId = document.getElementsByName('depositBankId');
		    var depositBranchId = document.getElementsByName('depositBranchId');
		    var depositMicrCode = document.getElementsByName('depositMicrCode');
		    var depositIfscCode = document.getElementsByName('depositIfscCode');
		    var depositBankAccount = document.getElementsByName('depositBankAccount');
		   	
		   	
		   	
		    // Start change here by Prashant
			var valueDate = document.getElementsByName('valueDate');
			var checkedvalueDate="";
			var chvalueDate="";
			var hideDate = document.getElementsByName('hideDate');
			var checkedhideDate="";
			
			 // End change here by Prashant
		   	
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
                    instrumentID=instrumentID + ch[i].value + "/";
		   				
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				
		   				
						depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
		   				depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
		   				depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
		   				depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
		   				depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   				
		   				
		   				 
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				// alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if((status[i].value) == 'C'){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
						alert("You can realize, only if it's status is Sent to Customer");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("realize").removeAttribute("disabled","true");
						return false;
						}
							
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
			  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
			   			else
			   				reasonRemarksList=reasonRemarksList+"/";

		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
			   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
			   				}else{
			   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
			   				}
		   				
		   				if(lbxReasonHID[i].value==''){
		   					checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
			   				 
			   				  }else{
			   					checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
			   				 
			   				  }
		   			// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Received/Deposit Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant   				
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("realize").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	
		  
		   	 
		   	var confrm = confirm("Do you want to continue ?");
		   	 if(confrm){
		   	 
		   		 
		   	//Ravi Start
				  
				    if(document.getElementById("loanRecStatus").value!='')
				    {
				    	DisButClass.prototype.EnbButMethod();
				    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
				    	{
				    		var status = confirm("Loan is on pending stage. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("sentToCustomer").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}else if(document.getElementById("loanRecStatus").value=='C')
				    	{
				    		var status = confirm("Loan is close. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("sentToCustomer").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}
				    	
				    }
				  
				    //Ravi End
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
		   	document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("realize").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   
		   }
	   
  function stopPaymentChequeStatus()  {
		   
	  DisButClass.prototype.DisButMethod();
		   	var contextPath=document.getElementById("contextPath").value;
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	var pdcFlag = document.getElementById("pdcFlag").value;
		   	 var instrumentType = document.getElementById("instrumentType").value;
		   	 var instrumentNo= document.getElementsByName('instrumentNo');
		   	 var date = document.getElementsByName('date1');
		   	 var instrumentAmount=document.getElementsByName('instrumentAmount1');	
		   	 var status=document.getElementsByName('status1');
		     var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
		   	 var lbxBPNID= document.getElementsByName('lbxBPNID1');
		   	 var lbxBankID = document.getElementsByName('lbxBankID1');
		   	 var lbxBranchID=document.getElementsByName('lbxBranchID1');
		   	 var tdsAmount=document.getElementsByName('tdsAmount1');
		   	var lbxReasonHID=document.getElementsByName('lbxReasonHID');
		   	var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
		   	
		   	var checkedinstrumentNo="";
		   	var checkeddate="";
		   	var checkedinstrumentAmount="";
		   	var checkedlbxReasonHID="";
		   	var checkedstatus="";
		   	var checkedlbxBPTypeHID="";
		   	var checkedlbxBPNID="";
		   	var checkedlbxBankID="";
		   	var checkedlbxBranchID="";
		   	var instrumentID="";
		   	var tdsAmountList="";
		   	var pdcInstrumentIdList ="";
		   	
		 	var buttonstatus="STP";
			var depositBankIdList ="";
			var depositBranchIdList="";
			var depositMicrCodeList="";
			var depositIfscCodeList="";
			var depositBankAccountList ="";
			var reasonRemarksList ="";

			var reasonRemarks = document.getElementsByName('reasonRemarks');
			
			var depositBankId = document.getElementsByName('depositBankId');
		    var depositBranchId = document.getElementsByName('depositBranchId');
		    var depositMicrCode = document.getElementsByName('depositMicrCode');
		    var depositIfscCode = document.getElementsByName('depositIfscCode');
		    var depositBankAccount = document.getElementsByName('depositBankAccount');
		 	
		 	
		    // Start change here by Prashant
			var valueDate = document.getElementsByName('valueDate');
			var checkedvalueDate="";
			var chvalueDate="";
			var hideDate = document.getElementsByName('hideDate');
			var checkedhideDate="";
			
			 // End change here by Prashant
		 	
		 	
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				 instrumentID=instrumentID + ch[i].value + "/";
			   				
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				 
		   				
						depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
						 
		   				depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
		   				 
		   				depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
		   				 
		   				depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
		   				 
		   				depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   				 
		   				 
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				// alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if((status[i].value) == 'C'){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
						alert("You can stop payment, only if it's status is Sent to Customer");	
						DisButClass.prototype.EnbButMethod();
						document.getElementById("stopPayment").removeAttribute("disabled","true");
						return false;
						}
							
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				
		   				if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
			  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
			   			else
			   				reasonRemarksList=reasonRemarksList+"/";


		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
			   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
			   				}else{
			   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
			   				}
		   				
		   				if(lbxReasonHID[i].value==''){
		   					checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
			   				 
			   				  }else{
			   					checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
			   				 
			   				  }
		   			// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Received/Deposit Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant  				
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("stopPayment").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
		   	 if(confrm){
		   		 
		   	//Ravi Start
				  
				    if(document.getElementById("loanRecStatus").value!='')
				    {
				    	DisButClass.prototype.EnbButMethod();
				    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
				    	{
				    		var status = confirm("Loan is on pending stage. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("stopPayment").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}else if(document.getElementById("loanRecStatus").value=='C')
				    	{
				    		var status = confirm("Loan is close. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("stopPayment").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}
				    	
				    }
				  
				    //Ravi End
		   	 
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
			document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("stopPayment").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   
		   }
  function cancelChequeStatus()  
  {
	  DisButClass.prototype.DisButMethod();
	  
	  var contextPath=document.getElementById("contextPath").value;
	  var flag=0;
	  var ch=document.getElementsByName('chk');
	  var pdcFlag = document.getElementById("pdcFlag").value;
	  var instrumentType = document.getElementById("instrumentType").value;
	  var instrumentNo= document.getElementsByName('instrumentNo1');
	  var date = document.getElementsByName('date1');
	  var instrumentAmount=document.getElementsByName('instrumentAmount1');	
	  var status=document.getElementsByName('status1');
	  var chequeStatus=document.getElementById('chequeStatus').value;
	  var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
	  var lbxBPNID= document.getElementsByName('lbxBPNID1');
	  var lbxBankID = document.getElementsByName('lbxBankID1');
	  var lbxBranchID=document.getElementsByName('lbxBranchID1');
	  var tdsAmount=document.getElementsByName('tdsAmount1');
	  var lbxReasonHID=document.getElementsByName('lbxReasonHID');
	  var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
	  var paymentMode = document.getElementById("paymentMode").value;
	  
	  var checkedinstrumentNo="";
	  var checkeddate="";
	  var checkedinstrumentAmount="";	  
	  var checkedstatus="";
	  var checkedlbxBPTypeHID="";
	  var checkedlbxBPNID="";
	  var checkedlbxBankID="";
	  var checkedlbxBranchID="";
	  var instrumentID="";
	  var tdsAmountList="";
	  var checkedlbxReasonHID="";
	  var buttonstatus="CBP";
	  var pdcInstrumentIdList ="";	   	
	  var depositBankIdList ="";
	  var depositBranchIdList="";
	  var depositMicrCodeList="";
	  var depositIfscCodeList="";
	  var depositBankAccountList ="";	
	  var reasonRemarksList ="";
	  var depositBankId = document.getElementsByName('depositBankId');
	  var depositBranchId = document.getElementsByName('depositBranchId');
	  var depositMicrCode = document.getElementsByName('depositMicrCode');
	  var depositIfscCode = document.getElementsByName('depositIfscCode');
	  var reasonRemarks = document.getElementsByName('reasonRemarks');
	  
	  var depositBankAccount = document.getElementsByName('depositBankAccount');
	  
	  // Start change here by Prashant
		var valueDate = document.getElementsByName('valueDate');
		var checkedvalueDate="";
		var chvalueDate="";
		var hideDate = document.getElementsByName('hideDate');
		var checkedhideDate="";
		
		 // End change here by Prashant
	  
	  for(i=0;i<ch.length;i++)
	  {
		  if(ch[i].checked==true)
		  {
			  instrumentID=instrumentID + ch[i].value + "/";
			  tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
			  depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
			  depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
			  depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
			  depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
			  depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   	  checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   	  checkeddate = checkeddate + date[i].value + "/";
		   	  checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
		   	  reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
		   	  
		   	  if(instrumentType=='P' && (paymentMode=='Q'||paymentMode=='C') && reasonRemarks[i].value=='')
		   	  {
		   		alert("Reason Reamrks is required.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("cancel").removeAttribute("disabled","true");
				return false;
		   	  }
			  
		   	  if(((status[i].value) == 'A') || ((status[i].value) == 'C') || ((status[i].value) == 'S')|| ((status[i].value) == 'R'))
		   	  		checkedstatus = checkedstatus + status[i].value + "/";
			  else
			  {
					alert("You can cancel, only if it's status is Sent to Customer,Stop Payment,Approved,Realized");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cancel").removeAttribute("disabled","true");
					return false;
			  }
		   	  checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
		   	  checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
		   	  checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
		   	  checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   	  if(pdcInstrumentId[i].value=='')
		   		  pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
		   	  else
		   		  pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
		   	  if(lbxReasonHID[i].value=='')
		   		  checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
		   	  else
		   		  checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
		   // Start change here by Prashant		   				
 				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
 				chvalueDate= valueDate[i].value;
 				checkedhideDate = hideDate[i].value;
 				var businessdate=document.getElementById("businessdate").value;
 				var formatD=document.getElementById("formatD").value;
 				
 				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
 			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
 			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
 				if(dt1<dt2)
 				{
 					alert("Value Date can not be less than Payment/Deposit Date of Instrument.");
 					DisButClass.prototype.EnbButMethod();
 			   		document.getElementById("bounce").removeAttribute("disabled","true");
 			   		return false;
 				}
// 				alert("chequeStatus::::::"+chequeStatus);
 				if(dt1>dt3 && chequeStatus !='A')
 				{
 					alert("Value Date can not be greater than Bussiness Date");
 					DisButClass.prototype.EnbButMethod();
 			   		document.getElementById("bounce").removeAttribute("disabled","true");
 			   		return false;
 				}
 				//alert("checkedvalueDate: "+checkedvalueDate);
// End change here by Prashant
		   	  
		   	  flag=1;
		  }
		}
		if(flag==0)
		{
		   	alert("Please select One record");
		   	DisButClass.prototype.EnbButMethod();
		   	document.getElementById("cancel").removeAttribute("disabled","true");
		   	return false;
		}
		var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
		if(confrm)
		{	   	 
			
			//Ravi Start
			  
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	DisButClass.prototype.EnbButMethod();
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("cancel").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("cancel").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		  
		    //Ravi End
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
		   	document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	    return true;
	   	}
		else
		{
			DisButClass.prototype.EnbButMethod();
		   		document.getElementById("cancel").removeAttribute("disabled","true");
		   		 return false;
		}
	}
  
  function depositChequeStatus()
  {
	//  alert("neeraj");
	  
	  		DisButClass.prototype.DisButMethod();
	  
		   	var contextPath=document.getElementById("contextPath").value;
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 var pdcFlag = document.getElementById("pdcFlag").value;
		   	 var instrumentType = document.getElementById("instrumentType").value;
		   	 var instrumentNo= document.getElementsByName('instrumentNo1');
		   	 var date = document.getElementsByName('date1');
		   	 var instrumentAmount=document.getElementsByName('instrumentAmount1');	
		   	 var status=document.getElementsByName('status1');
		     var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
		   	 var lbxBPNID= document.getElementsByName('lbxBPNID1');
		   	 var lbxBankID = document.getElementsByName('lbxBankID1');
		   	 var lbxBranchID=document.getElementsByName('lbxBranchID1');
		   	 var tdsAmount=document.getElementsByName('tdsAmount1');
		   	 var lbxReasonHID=document.getElementsByName('lbxReasonHID');
		   	 var instMode=document.getElementsByName('instrumentMode');
		   	var loanRecStatus = document.getElementById("loanRecStatus").value;

		   	var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
		    // Start change here by Prashant
			var valueDate = document.getElementsByName('valueDate');
			var checkedvalueDate="";
			var chvalueDate="";
			var hideDate = document.getElementsByName('hideDate');
			var checkedhideDate="";
			
			 // End change here by Prashant
		   	
		   	var checkedinstrumentNo="";
		   	var checkeddate="";
		   	var checkedinstrumentAmount="";
		   	var pdcInstrumentIdList ="";
		   	var checkedstatus="";
		   	var checkedlbxBPTypeHID="";
		   	var checkedlbxBPNID="";
		   	var checkedlbxBankID="";
		   	var checkedlbxBranchID="";
		   	var instrumentID="";
			var checkedlbxReasonHID="";
		   	var tdsAmountList="";
		   	var mode="";
		   
// var depositBankIdList ="";
// var depositBranchIdList="";
// var depositMicrCodeList="";
// var depositIfscCodeList="";
// var depositBankAccountList ="";
//			
// var depositBankId = document.getElementsByName('depositBankId');
// var depositBranchId = document.getElementsByName('depositBranchId');
// var depositMicrCode = document.getElementsByName('depositMicrCode');
// var depositIfscCode = document.getElementsByName('depositIfscCode');
// var depositBankAccount = document.getElementsByName('depositBankAccount');
		   	
		   	
		   	
		   	
		   	
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				instrumentID=instrumentID + ch[i].value + "/";
		   				
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				

// depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
// depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
// depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
// depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
// depositBankAccountList = depositBankAccountList + depositBankAccount[i].value
// + "/";
		   				
		   				
		   				
		   				
		   				
		   			// alert("instrumentNo[i]"+instrumentNo[i].value);
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				mode =instMode[0].value;
		   				
		   				// alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if(((status[i].value) == 'A') || ((status[i].value) == 'B')){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
							alert("You can deposit, only if it is Approved or Bounce.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("deposit").removeAttribute("disabled","true");
							return false;
						}
						
						if(lbxReasonHID[i].value==''){
		   					checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
			   				 
			   				  }else{
			   					checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
			   				 
			   				  }
						
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
			   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
			   				}else{
			   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
			   				}
		   				 
		   			// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Receipt Date of Instrument.");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant		   				
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("deposit").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	// alert("neeraj  checkedlbxBankID   :  "+checkedlbxBankID);
		   //	alert("neeraj  checkedlbxBranchID  :  "+checkedlbxBranchID);
			//alert("neeraj  mode  :  "+mode);
		   	checkedinstrumentNo = escape( checkedinstrumentNo );
		   	window.open(""+contextPath+"/chequeStatusAction.do?method=openPopForDepositCheque&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&mode="+mode+"&checkedvalueDate="+checkedvalueDate+"&loanRecStatus="+loanRecStatus+"",'ProductPopUP',"height=250,width=700,status=yes,toolbar=no,menubar=no,location=center");
		   	DisButClass.prototype.EnbButMethod();
// document.getElementById("sourcingForm").action =
// contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus;
// document.getElementById("sourcingForm").submit();
// return true;
		  }
  
  function realizeReceiptChequeStatus()  {
		   
	  DisButClass.prototype.DisButMethod();
	  
				var contextPath=document.getElementById("contextPath").value;
				var flag=0;
				var pdcFlag = document.getElementById("pdcFlag").value;
			   	 var instrumentType = document.getElementById("instrumentType").value;
				var ch=document.getElementsByName('chk');
				var instrumentNo= document.getElementsByName('instrumentNo1');
				var date = document.getElementsByName('date1');
				var instrumentAmount=document.getElementsByName('instrumentAmount1');	
				var status=document.getElementsByName('status1');
				var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
				var lbxBPNID= document.getElementsByName('lbxBPNID1');
				var lbxBankID = document.getElementsByName('lbxBankID1');
				var lbxBranchID=document.getElementsByName('lbxBranchID1');
				var tdsAmount=document.getElementsByName('tdsAmount1');
				var lbxReasonHID=document.getElementsByName('lbxReasonHID');
				var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
				
				
			    
				var checkedinstrumentNo="";
				var checkeddate="";
				var checkedinstrumentAmount="";
				var checkedlbxReasonHID="";
				var checkedstatus="";
				var checkedlbxBPTypeHID="";
				var checkedlbxBPNID="";
				var checkedlbxBankID="";
				var checkedlbxBranchID="";
				var instrumentID="";
				var tdsAmountList="";
				var buttonstatus="RBP";
				var pdcInstrumentIdList ="";
				
				var depositBankIdList ="";
				var depositBranchIdList="";
				var depositMicrCodeList="";
				var depositIfscCodeList="";
				var depositBankAccountList ="";	
				var reasonRemarksList ="";

				var reasonRemarks = document.getElementsByName('reasonRemarks');
				var depositBankId = document.getElementsByName('depositBankId');
			    var depositBranchId = document.getElementsByName('depositBranchId');
			    var depositMicrCode = document.getElementsByName('depositMicrCode');
			    var depositIfscCode = document.getElementsByName('depositIfscCode');
			    var depositBankAccount = document.getElementsByName('depositBankAccount');
			    
			 // Start change here by Prashant
				var valueDate = document.getElementsByName('valueDate');
				var checkedvalueDate="";
				var chvalueDate="";
				var hideDate = document.getElementsByName('hideDate');
				var checkedhideDate="";
				
				 // End change here by Prashant
				
			    // alert(ch.length);
				
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				instrumentID=instrumentID + ch[i].value + "/";
		   				// alert("instrumentID-----"+instrumentID);
		   				depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
		   				// alert("depositBankIdList-----"+depositBankIdList);
		   				depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
		   				// alert("depositBranchIdList-----"+depositBranchIdList);
		   				depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
		   				// alert("depositMicrCodeList-----"+depositMicrCodeList);
		   				depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
		   				// alert("depositIfscCodeList-----"+depositIfscCodeList);
		   				depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   				// alert("depositBankAccountList-----"+depositBankAccountList);
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				// alert("tdsAmountList-----"+tdsAmountList);
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				
		   				// alert("checkedinstrumentNo-----"+checkedinstrumentNo);
		   				// alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
		   				
		   				// alert("checkeddate-----"+checkeddate);
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
		   				
		   				// alert("checkedinstrumentAmount-----"+checkedinstrumentAmount);
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if((status[i].value) == 'D'){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
						alert("You can realize, only if it's status is Deposit");	
						DisButClass.prototype.EnbButMethod();
						document.getElementById("realize").removeAttribute("disabled","true");
						return false;
						}
							
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
		   				
		   				// alert("checkedlbxBPTypeHID-----"+checkedlbxBPTypeHID);
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
		   				
		   				// alert("checkedlbxBPNID-----"+checkedlbxBPNID);
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
		   				
		   				// alert("checkedlbxBankID-----"+checkedlbxBankID);
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				
		   				// alert("checkedlbxBranchID-----"+checkedlbxBranchID);
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				
		   				// alert(pdcInstrumentId[i].value+"-----pdcInstrumentId[i].value");
		   				
		   				if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
			  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
			   			else
			   				reasonRemarksList=reasonRemarksList+"/";


		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
		   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
		   				}else{
		   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
		   				}
		   				
		   				// alert(pdcInstrumentIdList+"-----pdcInstrumentIdList");
		   				
		   				if(lbxReasonHID[i].value==''){
		   					checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
			   				 
			   				  }else{
			   					checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
			   				 
			   				  }
		   			// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Received/Deposit Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("realize").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	var confrm = confirm("Do you want to continue ?");
		   	 if(confrm){
		   		 
		   	//ravi
				    if(document.getElementById("loanRecStatus").value!='')
				    {
				    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
				    	{
				    		var status = confirm("Loan is on pending stage. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("realize").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}else if(document.getElementById("loanRecStatus").value=='C')
				    	{
				    		var status = confirm("Loan is close. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("realize").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}
				    	
				    }
				 
				    //Ravi End
			checkedinstrumentNo = escape( checkedinstrumentNo );
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
		   	document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("realize").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   
		   }
  function bounceChequeStatus()  {
		   
	  DisButClass.prototype.DisButMethod();
					var contextPath=document.getElementById("contextPath").value;
					var flag=0;
					
					var ch=document.getElementsByName('chk');
					var pdcFlag = document.getElementById("pdcFlag").value;
				   	 var instrumentType = document.getElementById("instrumentType").value;
					var instrumentNo= document.getElementsByName('instrumentNo1');
					var date = document.getElementsByName('date1');
					var instrumentAmount=document.getElementsByName('instrumentAmount1');	
					var status=document.getElementsByName('status1');
					var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
					var lbxBPNID= document.getElementsByName('lbxBPNID1');
					var lbxBankID = document.getElementsByName('lbxBankID1');
					var lbxBranchID=document.getElementsByName('lbxBranchID1');
					var tdsAmount=document.getElementsByName('tdsAmount1');
					var lbxReasonHID=document.getElementsByName('lbxReasonHID');
					var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
					
					var depositBankIdList ="";
					var depositBranchIdList="";
					var depositMicrCodeList="";
					var depositIfscCodeList="";
					var depositBankAccountList ="";
					
					var depositBankId = document.getElementsByName('depositBankId');
				    var depositBranchId = document.getElementsByName('depositBranchId');
				    var depositMicrCode = document.getElementsByName('depositMicrCode');
				    var depositIfscCode = document.getElementsByName('depositIfscCode');
				    var depositBankAccount = document.getElementsByName('depositBankAccount');
				    
					var checkedinstrumentNo="";
					var checkeddate="";
					var checkedinstrumentAmount="";
					var checkedlbxReasonHID="";
					var checkedstatus="";
					var checkedlbxBPTypeHID="";
					var checkedlbxBPNID="";
					var checkedlbxBankID="";
					var checkedlbxBranchID="";
					var instrumentID="";
					var pdcInstrumentIdList ="";
					var tdsAmountList="";
							   	
					var buttonstatus="BBR";
					var reasonRemarksList ="";

					var reasonRemarks = document.getElementsByName('reasonRemarks');
				    // Start change here by Prashant
					var valueDate = document.getElementsByName('valueDate');
					var checkedvalueDate="";
					var chvalueDate="";
					var hideDate = document.getElementsByName('hideDate');
					var checkedhideDate="";
					
					 // End change here by Prashant
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				instrumentID=instrumentID + ch[i].value + "/";
		   				
		   				tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";
		   				 
		   				
		   				depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
		   				depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
		   				depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
		   				depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
		   				depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/";
		   				
		   				
		   				
		   				
		   				checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
		   				// alert("checkedinstrumentNo"+checkedinstrumentNo);
		   				
		   				checkeddate = checkeddate + date[i].value + "/";
						// alert("checkeddate"+checkeddate);
						
		   				checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
						// alert("checkedinstrumentAmount"+checkedinstrumentAmount);
						
						if(((status[i].value) == 'D') || ((status[i].value) == 'R')){
		   				checkedstatus = checkedstatus + status[i].value + "/";
						// alert("checkedstatus"+checkedstatus);
						}else{
						alert("You can bounce, only if it's status is Deposit");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("bounce").removeAttribute("disabled","true");
						return false;
						}
							
						
		   				checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
						// alert("checkedlbxBPTypeHID"+checkedlbxBPTypeHID);
						
		   				checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
						// alert("checkedlbxBPNID"+checkedlbxBPNID);
						
		   				checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
						// alert("checkedlbxBankID"+checkedlbxBankID);
						
		   				checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
		   				// alert("checkedlbxBranchID"+checkedlbxBranchID);
		   				if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
			  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
			   			else
			   				reasonRemarksList=reasonRemarksList+"/";
		   				
		   				if(pdcInstrumentId[i].value==''){
		   					
			   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
			   				}else{
			   				pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
			   				}
		   				
		   				if(((lbxReasonHID[i].value) == '')){
							alert("Select Reason");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("bounce").removeAttribute("disabled","true");
			   				return false;
							// alert("checkedstatus"+checkedstatus);
							}else{
								checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value + "/";
						
							}
// Start change here by Prashant		   				
		   				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
		   				chvalueDate= valueDate[i].value;
		   				checkedhideDate = hideDate[i].value;
		   				var businessdate=document.getElementById("businessdate").value;
		   				var formatD=document.getElementById("formatD").value;
		   				
		   				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
		   			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
		   			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
		   				if(dt1<dt2)
		   				{
		   					alert("Value Date can not be less than Received/Deposit Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				if(dt1>dt3)
		   				{
		   					alert("Value Date can not be greater than Bussiness Date");
		   					DisButClass.prototype.EnbButMethod();
		   			   		document.getElementById("bounce").removeAttribute("disabled","true");
		   			   		return false;
		   				}
		   				//alert("checkedvalueDate: "+checkedvalueDate);
	// End change here by Prashant	   				
		    			  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("bounce").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	
		   	var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
		   	 if(confrm){
		   		 
		   	//ravi
				    if(document.getElementById("loanRecStatus").value!='')
				    {
				    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
				    	{
				    		var status = confirm("Loan is on pending stage. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("bounce").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}else if(document.getElementById("loanRecStatus").value=='C')
				    	{
				    		var status = confirm("Loan is close. Do you want to continue..");
				    		//alert("status :"+ status);
				    		if(!status)
				    		{
				    			document.getElementById("bounce").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
				    		}
				    	}
				    	
				    }
				 
				    //Ravi End
			checkedinstrumentNo = escape( checkedinstrumentNo ); 
		   	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
		   	document.getElementById("processingImage").style.display='block';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		DisButClass.prototype.EnbButMethod();
		   		document.getElementById("bounce").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   }
  
  
  
  function cancelReceiptChequeStatus()  
  {
	DisButClass.prototype.DisButMethod();
	
	var contextPath=document.getElementById("contextPath").value;
	var flag=0;
	var ch=document.getElementsByName('chk');
   	var pdcFlag = document.getElementById("pdcFlag").value;
   	var instrumentType = document.getElementById("instrumentType").value;
   	var instrumentNo= document.getElementsByName('instrumentNo1');
   	var date = document.getElementsByName('date1');
   	var instrumentAmount=document.getElementsByName('instrumentAmount1');	
   	var status=document.getElementsByName('status1');
    var lbxBPTypeHID=document.getElementsByName('lbxBPTypeHID1');
   	var lbxBPNID= document.getElementsByName('lbxBPNID1');
   	var lbxBankID = document.getElementsByName('lbxBankID1');
   	var lbxBranchID=document.getElementsByName('lbxBranchID1');
   	var tdsAmount=document.getElementsByName('tdsAmount1');
   	var lbxReasonHID=document.getElementsByName('lbxReasonHID');
   	var pdcInstrumentId = document.getElementsByName('pdcInstrumentId');
   	var checkedinstrumentNo="";
   	var checkeddate="";
   	var checkedinstrumentAmount="";
   	var pdcInstrumentIdList ="";
   	var checkedstatus="";
   	var checkedlbxBPTypeHID="";
   	var checkedlbxBPNID="";
   	var checkedlbxBankID="";
   	var checkedlbxBranchID="";
   	var instrumentID="";
   	var checkedlbxReasonHID="";
   	var tdsAmountList="";		   			   	
   	var buttonstatus="CBP";
	var depositBankIdList ="";
	var depositBranchIdList="";
	var depositMicrCodeList="";
	var depositIfscCodeList="";
	var depositBankAccountList ="";
	var reasonRemarksList ="";

	var reasonRemarks = document.getElementsByName('reasonRemarks');
	var depositBankId = document.getElementsByName('depositBankId');
    var depositBranchId = document.getElementsByName('depositBranchId');
    var depositMicrCode = document.getElementsByName('depositMicrCode');
    var depositIfscCode = document.getElementsByName('depositIfscCode');
    var depositBankAccount = document.getElementsByName('depositBankAccount');	   	
    // Start change here by Prashant
	var valueDate = document.getElementsByName('valueDate');
	var checkedvalueDate="";
	var chvalueDate="";
	var hideDate = document.getElementsByName('hideDate');
	var checkedhideDate="";
	
	 // End change here by Prashant
    for(i=0;i<ch.length;i++)
    {
   		if(ch[i].checked==true)
   		{			 
			instrumentID=instrumentID + ch[i].value + "/";			
			tdsAmountList=tdsAmountList + tdsAmount[i].value + "/";			 
			depositBankIdList = depositBankIdList + depositBankId[i].value + "/";
			depositBranchIdList = depositBranchIdList + depositBranchId[i].value + "/";
			depositMicrCodeList = depositMicrCodeList + depositMicrCode[i].value + "/";
			depositIfscCodeList = depositIfscCodeList + depositIfscCode[i].value + "/";
			depositBankAccountList = depositBankAccountList + depositBankAccount[i].value + "/"; 				
   			
			checkedinstrumentNo = checkedinstrumentNo + instrumentNo[i].value + "/";
   			checkeddate = checkeddate + date[i].value + "/";
			checkedinstrumentAmount = checkedinstrumentAmount + instrumentAmount[i].value + "/";
			if(((status[i].value) == 'D') || ((status[i].value) == 'R') || ((status[i].value) == 'B') || ((status[i].value) == 'A'))
				checkedstatus = checkedstatus + status[i].value + "/";
			else
			{
				alert("You can cancel, only if it's status is Deposit,Realized,Bounce,Approved");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("cancel").removeAttribute("disabled","true");
				return false;
			}
			checkedlbxBPTypeHID = checkedlbxBPTypeHID + lbxBPTypeHID[i].value + "/";
			checkedlbxBPNID = checkedlbxBPNID + lbxBPNID[i].value + "/";
			checkedlbxBankID = checkedlbxBankID + lbxBankID[i].value + "/";
			checkedlbxBranchID = checkedlbxBranchID + lbxBranchID[i].value + "/";
			
			if(reasonRemarks[i].value!=null && reasonRemarks[i].value!="")
  		   	  	reasonRemarksList=reasonRemarksList+reasonRemarks[i].value + "/";
   			else
   				reasonRemarksList=reasonRemarksList+"/";
			
   			if(pdcInstrumentId[i].value=='')
   				pdcInstrumentIdList = pdcInstrumentIdList + "qqq" + "/";	
	   		else
	   			pdcInstrumentIdList = pdcInstrumentIdList + pdcInstrumentId[i].value + "/";
	   		if(lbxReasonHID[i].value=='')
	   			checkedlbxReasonHID = checkedlbxReasonHID + "test" +"/"; 
	   		else
	   			checkedlbxReasonHID = checkedlbxReasonHID + lbxReasonHID[i].value +"/";
	   	// Start change here by Prashant		   				
				checkedvalueDate = checkedvalueDate + valueDate[i].value + "/";
				chvalueDate= valueDate[i].value;
				checkedhideDate = hideDate[i].value;
				var businessdate=document.getElementById("businessdate").value;
				var formatD=document.getElementById("formatD").value;
				
				var dt1=getDateObject(chvalueDate,formatD.substring(2, 3));
			    var dt2=getDateObject(checkedhideDate,formatD.substring(2, 3));
			    var dt3=getDateObject(businessdate,formatD.substring(2, 3));
				if(dt1<dt2)
				{
					alert("Value Date can not be less than Received/Deposit Date of Instrument.");
					DisButClass.prototype.EnbButMethod();
			   		document.getElementById("bounce").removeAttribute("disabled","true");
			   		return false;
				}
				if(dt1>dt3)
				{
					alert("Value Date can not be greater than Bussiness Date");
					DisButClass.prototype.EnbButMethod();
			   		document.getElementById("bounce").removeAttribute("disabled","true");
			   		return false;
				}
				//alert("checkedvalueDate: "+checkedvalueDate);
// End change here by Prashant		   		
	   		flag=1;
    	}
   	}
   	if(flag==0)
   	{
   	  alert("Please select One record");
   	DisButClass.prototype.EnbButMethod();
   	  document.getElementById("cancel").removeAttribute("disabled","true");
   	  return false;
   	}
   	var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
   	if(confrm)
   	{
   	//Ravi Start
		  
	    if(document.getElementById("loanRecStatus").value!='')
	    {
	    	DisButClass.prototype.EnbButMethod();
	    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
	    	{
	    		var status = confirm("Loan is on pending stage. Do you want to continue..");
	    		//alert("status :"+ status);
	    		if(!status)
	    		{
	    			document.getElementById("cancel").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
	    		}
	    	}else if(document.getElementById("loanRecStatus").value=='C')
	    	{
	    		var status = confirm("Loan is close. Do you want to continue..");
	    		//alert("status :"+ status);
	    		if(!status)
	    		{
	    			document.getElementById("cancel").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
	    		}
	    	}
	    	
	    }
	  
	    //Ravi End
	    checkedinstrumentNo = escape( checkedinstrumentNo );
   		document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=mainChequeStatus&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&depositBankIdList="+depositBankIdList+"&depositBranchIdList="+depositBranchIdList+"&depositMicrCodeList="+depositMicrCodeList+"&depositIfscCodeList="+depositIfscCodeList+"&depositBankAccountList="+depositBankAccountList+"&checkedvalueDate="+checkedvalueDate+"&reasonRemarksList="+reasonRemarksList;
   		document.getElementById("processingImage").style.display='block';
   		document.getElementById("sourcingForm").submit();
   		return true;
   	}
   	else
   	{
   		DisButClass.prototype.EnbButMethod();
   		document.getElementById("cancel").removeAttribute("disabled","true");
   		return false;
   	}
}
function branchLOV()
{
	mode=document.getElementById("mode").value;
	if(mode=='C')
	{
		document.getElementById("cash").style.display="inline";
		document.getElementById("adjustment").style.display="none";
		document.getElementById("other").style.display="none";
	}
	else if(mode=='S')
	{
		document.getElementById("cash").style.display="none";
		document.getElementById("adjustment").style.display="inline";
		document.getElementById("other").style.display="none";
	}
	else 
	{
		document.getElementById("cash").style.display="none";
		document.getElementById("adjustment").style.display="none";
		document.getElementById("other").style.display="inline";
	}
}
	   function saveDepositCheque()  {
			   
		   DisButClass.prototype.DisButMethod();
				var contextPath=document.getElementById("contextPath").value;
				
				if(document.getElementById("lbxBankID").value==''||document.getElementById("lbxBankID").value==null||document.getElementById("lbxBankID").value=='undefined'){
					alert("Select Deposit Bank");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(document.getElementById("lbxBranchID").value=='' || document.getElementById("lbxBranchID").value==null || document.getElementById("lbxBranchID").value=='undefined'){
					alert("Please Select/Reselect Deposit Branch");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
				var lbxBankID = document.getElementById("lbxBankID").value;
				var lbxBranchID = document.getElementById("lbxBranchID").value; 
				var micr = document.getElementById("micr").value;
				var ifscCode = document.getElementById("ifscCode").value;
				var bankAccount = document.getElementById("bankAccount").value;
				var checkedinstrumentNo=document.getElementById("checkedinstrumentNo").value;
				var checkeddate=document.getElementById("checkeddate").value;
				var checkedinstrumentAmount=document.getElementById("checkedinstrumentAmount").value;
				var checkedlbxReasonHID=document.getElementById("checkedlbxReasonHID").value;
				var checkedstatus=document.getElementById("checkedstatus").value;
				var checkedlbxBPTypeHID=document.getElementById("checkedlbxBPTypeHID").value;
				var checkedlbxBPNID=document.getElementById("checkedlbxBPNID").value;
				var checkedlbxBankID=document.getElementById("checkedlbxBankID").value;
				var checkedlbxBranchID=document.getElementById("checkedlbxBranchID").value;
				var instrumentID=document.getElementById("instrumentID").value;
				var tdsAmountList=document.getElementById("tdsAmountList").value;	
				var pdcInstrumentIdList = document.getElementById("pdcInstrumentIdList").value;
			 
				var pdcFlag=document.getElementById("pdcFlag").value;	
				var instrumentType = document.getElementById("instrumentType").value;
				var mode = document.getElementById("mode").value;
				//alert("mode   :  "+mode)
// Start change here by Prashant				
				var checkedvalueDate = document.getElementById("checkedvalueDate").value;
				
				
				//alert("checkedvalueDate   :  "+checkedvalueDate);
// End change here by Prashant				
				var oldBankId=checkedlbxBankID.split('/');
				var oldBranchId=checkedlbxBranchID.split('/');
				if(mode=="DIR")
				for(i=0; i<oldBankId.length-1; i++)
				{
					//alert("oldBankId["+i+"]  :  "+oldBankId[i]+"     lbxBankID  :  "+lbxBankID);
					if(oldBankId[i]==''||oldBranchId[i]=='')
						continue;
					if(oldBankId[i]==lbxBankID)
					{
						//alert("oldBranchId["+i+"]  :  "+oldBranchId[i]+"     lbxBranchID  :  "+lbxBranchID);
						if(oldBranchId[i]!=lbxBranchID)
						{
							
							alert("For Direct Debit,Deposited Bank and Branch should be same as Issuing Bank and Branch.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						else
							continue;
					}
					else
					{
						alert("For Direct Debit,Deposited Bank and Branch should be same as Issuing Bank and Branch.");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}
				var buttonstatus="DBR";
		
				var confrm = confirm("Voucher Posting for the selected record/s will happen as on mentioned value date/s. Do you want to proceed?");
			   	 if(confrm){
			   		 
			   		//ravi
					    if(document.getElementById("loanRecStatus").value!='')
					    {
					    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
					    	{
					    		var status = confirm("Loan is on pending stage. Do you want to continue..");
					    		//alert("status :"+ status);
					    		if(!status)
					    		{
					    			document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
					    		}
					    	}else if(document.getElementById("loanRecStatus").value=='C')
					    	{
					    		var status = confirm("Loan is close. Do you want to continue..");
					    		//alert("status :"+ status);
					    		if(!status)
					    		{
					    			document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
					    		}
					    	}
					    	
					    }
					 
					    //Ravi End
					checkedinstrumentNo = escape( checkedinstrumentNo );
			   	 	document.getElementById("sourcingForm").action = contextPath+"/chequeStatusAction.do?method=saveDepositCheque&checkedinstrumentNo="+checkedinstrumentNo+"&checkeddate="+checkeddate+"&checkedinstrumentAmount="+checkedinstrumentAmount+"&checkedstatus="+checkedstatus+"&checkedlbxBPTypeHID="+checkedlbxBPTypeHID+"&checkedlbxBPNID="+checkedlbxBPNID+"&checkedlbxBankID="+checkedlbxBankID+"&checkedlbxBranchID="+checkedlbxBranchID+"&instrumentID="+instrumentID+"&tdsAmountList="+tdsAmountList+"&buttonstatus="+buttonstatus+"&checkedlbxReasonHID="+checkedlbxReasonHID+"&lbxBankID="+lbxBankID+"&lbxBranchID="+lbxBranchID+"&micr="+micr+"&ifscCode="+ifscCode+"&bankAccount="+bankAccount+"&pdcInstrumentIdList="+pdcInstrumentIdList+"&pdcFlag="+pdcFlag+"&instrumentType="+instrumentType+"&checkedvalueDate="+checkedvalueDate;
			   	    document.getElementById("processingImage").style.display = '';
			   	   // alert("method : "+document.getElementById("sourcingForm").method);
			   		document.getElementById("sourcingForm").submit();
			   		
			   		     return true;
		   	     
			   	 }else{
			   		DisButClass.prototype.EnbButMethod();
			   		 return false;
			   	 }
				


	}

	    
	   
	   function div(){
	 	  if((document.getElementById("instrumentType").value)=='P'){
	 	  
	 	 document.getElementById("ractive").style.display="none"; 
	 	 document.getElementById("pactive").style.display="block"; 
	 	 document.getElementById("PActive").style.display="block"; 
	 	 document.getElementById("RActive").style.display="none";
	 	 document.getElementById("ractive").style.value="";
	    	 document.getElementById("RActive").style.value="";	 
	 	 
	 	  }else{
	 		  
	 		  document.getElementById("pactive").style.display="none"; 
	 	    	 document.getElementById("ractive").style.display="block"; 
	 	    	 document.getElementById("PActive").style.display="none"; 
	 	    	 document.getElementById("RActive").style.display="block";
	 	    	 document.getElementById("pactive").style.value="";
	 	       	 document.getElementById("PActive").style.value="";	
	 	  }
	   }
	   
	   function pageAssetVeri(id){
			 
			   	var contextPath=document.getElementById("contextPath").value;
			   			   
					document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInitiation.do";
					document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
	   
   
	   function editDeleteInstrumentMaker(id){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   // alert("id"+id);
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=editDeleteInstrumentMaker&loanID="+id;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
	   
	   function editDeleteInstrumentAuthor(id){
			   
			   	var contextPath=document.getElementById("contextPath").value;
			   // alert("contextPath"+contextPath);
			   // alert("id"+id);
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=editDeleteInstrumentAuthor&loanID="+id;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	
			   }
	   
	   
	   function savenForPDCDeleteInstrument(alert1,alert2,fwdMsg){
		        DisButClass.prototype.DisButMethod();
		        
			   	var contextPath=document.getElementById("contextPath").value;
			   	var loanid = document.getElementById("lbxLoanNoHID").value;
			   	if(!confirm(fwdMsg))	 
			    {
			       	DisButClass.prototype.EnbButMethod();
			    	return false;
			    }
			   
			   	var flag=0;
			   	var toggle=0;
			   	
			   	 var ch=document.getElementsByName('chk');
			   	 var holdReason= document.getElementsByName('holdReason');
			   	 var status = document.getElementsByName('status');
			   	 var instrumentID=document.getElementsByName('instrumentID');
			   	 var checked="";
			   	 var unchecked="";
			   	 var checkedhold="";
			   	 var uncheckedhold="";
			   	 var checkedStatus=""
			   	 var uncheckedStatus=""
			   	 var newStatus="";
			   	 var instrumentid="";
			   		
			   		 for(i=0;i<ch.length;i++){
			   			 
			   			 if(ch[i].checked==true){
			   				ch[i].value='X'; 
			   				 flag=1;
			   				instrumentid = instrumentid + instrumentID[i].value +"/";
			   				  
			   				  
			   				  newStatus=newStatus + ch[i].value +"/";
			   				
			   				  
			   				  if(holdReason[i].value==''){
			   					  alert("Please insert Delete Reason");
			   					  DisButClass.prototype.EnbButMethod();
			   					  //document.getElementById("savenfor").removeAttribute("disabled", "true");

			   				 return false;
			   				  }else{
			   				  checkedhold = checkedhold + holdReason[i].value +"/";
			   				
			   				  }
			   				  checkedStatus = checkedStatus + status[i].value +"/";
			   				 
			   			 }
			   			
			   				 
			   		 }
			   	 
			   	 if(flag==0)
			   	 {
			   		 alert(alert1);
			   		 DisButClass.prototype.EnbButMethod();
			   		//document.getElementById("savenfor").removeAttribute("disabled","true");
			   		 return false;
			   	 }
			   	 
			   	  
			   	
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savenForPDCDeleteInstrument&loanID="+loanid+"&checkedhold="+checkedhold+"&checkedStatus="+checkedStatus+"&instrumentid="+instrumentid+"&newStatus="+newStatus;
			   	     document.getElementById("processingImage").style.display = '';
			   		 document.getElementById("sourcingForm").submit();
			   	     return true;
			   
			   }
	   function editDeleteInstrumentAuthor(id){
		   
		   	var contextPath=document.getElementById("contextPath").value;
		   // alert("contextPath"+contextPath);
		   // alert("id"+id);
		   		
		   	document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=editDeleteInstrumentAuthor&id="+id;
		       document.getElementById("sourcingForm").submit();
		       return true;
		   }
	   
	   function savedeleteInstrumentAuthor(comments,decision){

			DisButClass.prototype.DisButMethod();
	  		var contextPath=document.getElementById("contextPath").value;
	  		
	  		if(document.getElementById("comments").value=="" && !(document.getElementById("decision").value=='X')) //Edited by Nishant
	  		{
	  			   alert(comments);
	  			   DisButClass.prototype.EnbButMethod();
	  			 //document.getElementById("save").removeAttribute("disabled");
	  			   return false;
	  			   
	  		   }
	  		
	  		if((document.getElementById("comments").value.length) > 1000){
	   			alert("You are requested to enter only 1000 characters.");
	   			DisButClass.prototype.EnbButMethod();
	   			//document.getElementById("save").removeAttribute("disabled");
		   		return false;
	   		}
	  	   if(document.getElementById("decision").value==""){
	  		   alert(decision);
	  		   DisButClass.prototype.EnbButMethod();
	  		 //document.getElementById("save").removeAttribute("disabled");
	  		   return false;
	  		   
	  	   }	
	  	   
	  	        document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savedeleteInstrumentAuthor";
	  			document.getElementById("processingImage").style.display = ''; 
	  	        document.getElementById("sourcingForm").submit();
	  			 return true;
	  		
	  	}
	   // neerajgenerateReport()
	   function generateProcess()
	   {
		   var presentationDate=document.getElementById("presentationDate").value;
		   var bank=document.getElementById("bank").value;
		   var branch=document.getElementById("branch").value;
		   var bankAccount=document.getElementById("bankAccount").value;
		   var msg1="";
		   var msg2="";
		   var msg3="";
		   var msg4="";
		   if(presentationDate =="")
			   msg1="*Presentation Date is required \n";
		   if(bank =="")
			   msg2="*Deposit Bank ID is required \n";
		   if(branch =="")
			   msg3="*Deposit Branch ID is required \n";
		   if(bankAccount =="")
			   msg4="*Deposit Bank Account is required \n";
		  		   
		   if(presentationDate =="" ||bank =="" || branch =="" || bankAccount =="")
		   {
			   alert(msg1+""+msg2+""+msg3+""+msg4);
			   document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   }
		   if(document.getElementById("txnfile").value=="NoFile")
		   {
			   alert("Please upload file to start process.");
			   document.getElementById("docFile").focus(); 
			   document.getElementById("save").removeAttribute("disabled");
			   return false;
		   }
		   var contextPath = document.getElementById("contextPath").value;
		   document.getElementById("sourcingForm").action = contextPath+"/presentaionProcessMain.do?method=generateProcess";
		   document.getElementById("sourcingForm").submit();
		   return true;
	   }
function  generateReport()
{
   var contextPath = document.getElementById("contextPath").value;
   document.getElementById("sourcingForm").action = contextPath+"/presentaionProcessMain.do?method=generateReport";
   document.getElementById("sourcingForm").submit();
}


	  
	   
	// manas ends here
function deleteNoDisb()
{
	var disNo="";
	if(checkboxCheck(document.getElementsByName("chk")))
	{
	  var disbNo=document.getElementsByName("chk");
	  for(i=0;i<disbNo.length;i++)
	  {
		 if(disbNo[i].checked==true) 
		 {
			 disNo+=disbNo[i].value+"|"; 
		 }
		 
	  }
	  alert(disNo);  
      var contextPath = document.getElementById("contextPath").value;
      document.location.href=contextPath+"/loanNoOfDisbProcess.do?method=deleteNumberDisb&disNo="+disNo;
         
	}
	else
	{
		alert("Please Select AtLeast one Record");
	}
}
/*
function saveDisbInCM()

{
 //alert("saveDisbInCM");
DisButClass.prototype.DisButMethod();
 var contextPath = document.getElementById("contextPath").value;
 var formatD=document.getElementById("formatD").value;
// var noOfDisbursal=document.getElementById("disbNoInLoan").value;	
 //var noOfDisb=document.getElementById("noOfDis");
 //var noOfDisb1=noOfDisb.rows.length-1;	
 var agrementDate = document.getElementById("agrDateDisb").value;	
 var repayEffectiveDate = document.getElementById("repayEffDateDisb").value;	
 var dt1=getDateObject(agrementDate,formatD.substring(2, 3));	 
 var dt2=getDateObject(repayEffectiveDate,formatD.substring(2, 3));	    
 var loanAmount=removeComma(document.getElementById("loanAmount").value);
// var disbDate = document.getElementById("disbDate1").value;
// var dt3=getDateObject(disbDate,formatD.substring(2, 3));
 var table = document.getElementById("gridTable");	
 var rowCount = table.rows.length;
 var sum=0;
 if(rowCount > 1){
	 for(var k=1;k<rowCount;k++)
	 {
		 
	 disbDate = document.getElementById("dateOfDisbursal"+k).value;	
	 disDesc = document.getElementById("descOfDisbursal"+k).value;
	 amount = document.getElementById("amountOfDisbursal"+k).value;
	 
	 if(amount==""){
		 amount=0;
	      }else{
	    	  
	    	  amount=removeComma(document.getElementById('amountOfDisbursal'+k).value);
	    	
	      }
	
	    sum = (sum) + (amount);
	    
	    if ((disbDate=="")||(disDesc=="")||(amount==""))
		 {
			 alert("Please fill all the fields ");
			 // document.getElementById("save").removeAttribute("disabled");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	    
	}
 }
	
   var dt3=getDateObject(disbDate,formatD.substring(2, 3));
   
  
   
    /* if(noOfDisbursal!=noOfDisb1)
	{
		alert("no of disbursal should be equal to Disbursal no");
		// document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
     
	 if (dt1>dt3)
	  {
	  alert("Disbursal Date should be greater than agrementDate");
	  // document.getElementById("save").removeAttribute("disabled");
	  DisButClass.prototype.EnbButMethod();
	  return false;
	  }
	 if (dt3>dt2)
	  {
	  alert("Disbursal Date should be less than repayEffectiveDate");
	  // document.getElementById("save").removeAttribute("disabled");
	  DisButClass.prototype.EnbButMethod();
	  return false;
	  }
	 if (sum!=loanAmount)
	  {
		  alert("Amount should be equal to LoanAmount");
		  // document.getElementById("save").removeAttribute("disabled");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
	 var rowCount1=rowCount-1;	
	 //alert("rowCount: "+rowCount1);
	 if(rowCount1 >1){
		 for(var k=1;k<rowCount1;k++)
		 {
			 var DisbDate = document.getElementById("dateOfDisbursal"+k).value;	
			 var disbDate1 = document.getElementById("dateOfDisbursal"+(k+1)).value;	
			 var dt4=getDateObject(DisbDate,formatD.substring(2, 3));
			 var dt5=getDateObject(disbDate1,formatD.substring(2, 3));
			 if(dt5 < dt4){			 
			 alert("next Disbursal date should be greater than previous Disbursal date");
			 // document.getElementById("save").removeAttribute("disabled");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	
	    }
	 }
	
 document.getElementById("NoOfDisbForm").action = contextPath+"/loanNoOfDisbProcess.do?method=saveNumberDisb";
 document.getElementById("processingImage").style.display = '';
 document.getElementById("NoOfDisbForm").submit();
}*/

function saveDisbInCM()

{
 
DisButClass.prototype.DisButMethod();
 var contextPath = document.getElementById("contextPath").value;
 var formatD=document.getElementById("formatD").value;

 
 var agrementDate = document.getElementById("agrDateDisb").value;	
 var repayEffectiveDate = document.getElementById("repayEffDateDisb").value;	
 var dt1=getDateObject(agrementDate,formatD.substring(2, 3));	 
 var dt2=getDateObject(repayEffectiveDate,formatD.substring(2, 3));	
    
 var loanAmount=removeComma(document.getElementById("loanAmount").value);

 
 var table = document.getElementById("gridTable");	
 var rowCount = table.rows.length;
 var disbDate='';	
 var disDesc='';
 var amount='';
 var noDisb='';
 var nextNoDisb='';
 var diffNoDisb=0;
    var noOfDisbursal=document.getElementsByName("noOfDisbursal");
	var dateOfDisbursal=document.getElementsByName("dateOfDisbursal");
	var descOfDisbursal=document.getElementsByName("descOfDisbursal");
	var amountOfDisbursal=document.getElementsByName("amountOfDisbursal");
 var sum=0;
 if(rowCount > 1){
	
	
	 if(noOfDisbursal.length>1)
	 {
		 for(var i=0;i<noOfDisbursal.length-1;i++) {
			 
			 
			 noDisb=noOfDisbursal[i].value;	
			 nextNoDisb=noOfDisbursal[i+1].value;
			 diffNoDisb=parseInt(nextNoDisb)-parseInt(noDisb);
			
			 if(parseInt(nextNoDisb)==0)
			 {
				 alert("zero is not allowed ");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(diffNoDisb!=1)
			 {
				 alert("No of disbursal must be in sequence ");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			
		 }
	 }
	 else
	 {
		 if(parseInt(noOfDisbursal[0].value)==0)
		 {
			 alert("zero is not allowed ");
			  DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	 }
	 
	 
	 for(var k=0;k<dateOfDisbursal.length;k++) {
		 
	 disbDate = dateOfDisbursal[k].value;	
	 disDesc =descOfDisbursal[k].value;
	 amount =amountOfDisbursal[k].value;
	 
	 if(amount==""){
		 amount=0;
	      }else{
	     amount=removeComma(amountOfDisbursal[k].value);
	     }
	
	    sum = (sum) + (amount);
	    
	    if ((disbDate=="")||(disDesc=="")||(amount==""))
		 {
			 alert("Please fill all the fields ");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	    var dt3=getDateObject(disbDate,formatD.substring(2, 3));
	    
	       
	    if (dt1>dt3)
		  {
		  alert("Disbursal Date should be greater than agrementDate");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
		 if (dt3>dt2)
		  {
		  alert("Disbursal Date should be less than repayEffectiveDate");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
		
	    if(k<dateOfDisbursal.length-1){
	    
	    	 var DisbDate = dateOfDisbursal[k].value;	
			 var disbDate1 = dateOfDisbursal[k+1].value;	
			 var dt4=getDateObject(DisbDate,formatD.substring(2, 3));
			 var dt5=getDateObject(disbDate1,formatD.substring(2, 3));
			
			 if(dt5 < dt4){			 
			 alert("next Disbursal date should be greater than previous Disbursal date");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }	
	    }
	}
 }
 
	 if (sum!=loanAmount)
	  {
		  alert("Amount should be equal to LoanAmount");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
	
	 var rowCount1=rowCount-1;	
	 	 
	 document.getElementById("NoOfDisbForm").action = contextPath+"/loanNoOfDisbProcess.do?method=saveNumberDisb";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("NoOfDisbForm").submit();
}



function eodBodFinished(count)
{ // alert(count);
   if(count=0)
   {
	refreshBodProcess(5000);
	count++;
   }
	
}
function eodRefreshProcess(count)
{ // alert(count);
   if(count=0)
   {
	 refreshProcess(5000);
	count++;
   }
	
}


function disableIntExt()
{
//
// if((document.getElementById("appType").value)==""){
//  		
// alert("Please select Type.");
// return false;
//  		
// }
//
// if((document.getElementById("appraiser").value)==""){
//  		
// alert("Please select Appraiser");
// return false;
//  		
// }

	 	
	if((document.getElementById("appraiser").value)=="I"){
	// alert(document.getElementById("lovImg"));
		document.getElementById("internalAppraiser").disabled=false;
		document.getElementById("externalAppraiser").disabled=true;
		document.getElementById("lbxextApprHID").disabled=true;
		document.getElementById("externalAppraiser").value="";
		document.getElementById("lbxextApprHID").value="";
		document.getElementById("externalButton").disabled=true;	
		document.getElementById("internalButton").disabled=false;
		return true;
		
	}else{

		document.getElementById("internalAppraiser").disabled=true;
		document.getElementById("lbxextApprHID").disabled=false;
		document.getElementById("lbxUserId").value="";
		document.getElementById("internalAppraiser").value="";
		document.getElementById("externalAppraiser").disabled=false;
		document.getElementById("internalButton").disabled=true;
		document.getElementById("externalButton").style.display='';
		document.getElementById("externalButton").disabled=false;
	 // alert();
		return true;
	}
}



function saveAssetVerification()  {

	 
		if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
	   		
			alert("*Internal Appraiser is required");
			document.getElementById("assetButton").removeAttribute("disabled","true");
			return false;	
	   	}
		else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
				   		
					alert("*External Appraiser is required");
					document.getElementById("assetButton").removeAttribute("disabled","true");
					return false;	
			   	}
		   
		   	var contextPath=document.getElementById("contextPath").value;
		   	var flag=0;
		   	
		   	 var ch=document.getElementsByName('chk');
		   	 
		   	 var loanNo= document.getElementById('loanNo').value;
		   	 var assetID1 = document.getElementsByName('assetID1');
		   	 var assetDescription1=document.getElementsByName('assetDescription1');	
		   	 
		    var loanid=document.getElementById('loanidval');
		     
		   	var loanIDList="";
		   	var loanAccNoList="";
		   	var assetIDList="";
		   	var assetDescriptionList ="";
		   	var loanNolist="";
		   	
		    for(i=0;i<ch.length;i++){
		   			 
		   			 if(ch[i].checked==true){
		   				 
		   				loanIDList=loanIDList + loanid.value + "/";
		   			
		   				assetIDList = assetIDList + assetID1[i].value + "/";
		   			    assetDescriptionList = assetDescriptionList + assetDescription1[i].value + "/";
						  flag=1;
		    		
		    			}
		   				 
		   		 }
		 
		   	 if(flag==0)
		   	 {
		   		 alert("Please select One record");
		   		 document.getElementById("assetButton").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   	var confrm = confirm("Do you want to continue ?");
		   	 if(confrm){
		   	 
		   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=saveAssetVerification&loanIDList="+loanIDList+"&assetIDList="+assetIDList+"&assetDescriptionList="+assetDescriptionList+"&loanNo="+loanNo;
		
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
	   	     
		   	 }else{
		   		document.getElementById("assetButton").removeAttribute("disabled","true");
		   		 return false;
		   	 }
		   
		   }


function searchAssetVerRepCap(val) 
{	
	DisButClass.prototype.DisButMethod();
	var loanNo=document.getElementById("loanAccNo").value;
	var customerName=document.getElementById("customerName").value;
	var contextPath= document.getElementById("contextPath").value;
	var reportingToUserId= document.getElementById("reportingToUserId").value;
	// alert("newSearchLoan"+stage);
	if(loanNo!=''||customerName!='' || reportingToUserId != '')
	{
		if(customerName!='' && customerName.length>=3)
		{
			 document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=searchAssetVerRepCap";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("sourcingForm").submit();
			 return true;
		}
		else if(customerName=='')
		{
			 document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=searchAssetVerRepCap";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("sourcingForm").submit();
			 return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			// document.getElementById("SearchButton").removeAttribute("disabled","true");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		// document.getElementById("SearchButton").removeAttribute("disabled","true");
		return false;
	}
}


function saveAssetRepCap()  {
			
	   		DisButClass.prototype.DisButMethod();
			var formatD=document.getElementById("formatD").value;
			var actualDate=document.getElementById("visitDate").value;
			var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
			// alert(actualDate);
			var businessDate= document.getElementById("businessDate").value;
			var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
			// alert(businessDate);
		   	var contextPath=document.getElementById("contextPath").value;
		   	var assetVerificationID = document.getElementById("assetVerificationID").value;
		   	
		   	if(validateAssetVerificationInitiationMainDynaValidatorForm(document.getElementById("sourcingForm")))
		   	{
		   	if(dtActual>dtBusiness)
			{
				alert("Visit Date cannot be greater than Business Date");
				DisButClass.prototype.EnbButMethod();
				// document.getElementById('saveButton').removeAttribute("disabled","true");
				document.getElementById("visitDate").focus();
				return false;
			}
		   	
		   	else if(document.getElementById("invoiceCollected").value=='Y' && document.getElementById("invoiceNumber").value=='')
		   	{
		   		alert("* Invoice Number is required");
		   		// document.getElementById('saveButton').removeAttribute("disabled","true");
		   		DisButClass.prototype.EnbButMethod();
		   		return false;
		   	}
		   	else{
		   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInitMain.do?method=saveAssetRepCap&assetVerificationID="+assetVerificationID;
			document.getElementById("processingImage").style.display = '';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
		   	}
		   	}
		   	else{
		   		// document.getElementById('saveButton').removeAttribute("disabled","true");
		   		DisButClass.prototype.EnbButMethod();
		   		return false;
		   	}
		   }


function forwardAssetRepCap(fwdMsg)  {
	   DisButClass.prototype.DisButMethod();
	   if(!confirm(fwdMsg))	 
	    {
	       	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
		var formatD=document.getElementById("formatD").value;
		var actualDate=document.getElementById("visitDate").value;
		var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
		// alert(actualDate);
		var businessDate= document.getElementById("businessDate").value;
		var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
		// alert(businessDate);
	   	var contextPath=document.getElementById("contextPath").value;
	   	var assetVerificationID = document.getElementById("assetVerificationID").value;
	   	
	   	if(validateAssetVerificationInitiationMainDynaValidatorForm(document.getElementById("sourcingForm")))
	   	{
	   	if(dtActual>dtBusiness)
		{
			alert("Visit Date cannot be greater than Business Date");
			DisButClass.prototype.EnbButMethod();
			// document.getElementById('forwardButton').removeAttribute("disabled","true");
			document.getElementById("visitDate").focus();
			return false;
		}
	   	
		else if(document.getElementById("invoiceCollected").value=='Y' && document.getElementById("invoiceNumber").value=='')
	   	{
	   		alert("* Invoice Number is required");
	   		DisButClass.prototype.EnbButMethod();
	   		// document.getElementById('forwardButton').removeAttribute("disabled","true");
	   		return false;
	   	}
	   	
	   	else {
	   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInitMain.do?method=forwardAssetRepCap&assetVerificationID="+assetVerificationID;
		document.getElementById("processingImage").style.display = '';
	   	document.getElementById("sourcingForm").submit();
	     return true;
	   	}
	   	}
	   	else{
	   		// document.getElementById('forwardButton').removeAttribute("disabled","true");
	   		DisButClass.prototype.EnbButMethod();
	   		return false;
	   	}
	   }

function searchAssetVerRepComplete(val) 
{
	DisButClass.prototype.DisButMethod();
	var loanNo=document.getElementById("loanAccNo").value;
	var customerName=document.getElementById("customerName").value;
	var contextPath= document.getElementById("contextPath").value;
	var reportingToUserId= document.getElementById("reportingToUserId").value;
	// alert("newSearchLoan"+stage);
	if(loanNo!=''||customerName!=''||reportingToUserId!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=searchAssetVerRepComplete";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=searchAssetVerRepComplete";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("SearchButton").removeAttribute("disabled","true");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("SearchButton").removeAttribute("disabled","true");
		return false;
	}
}

function saveAssetCapRepAuthor(assetVerificationID)  {
	 DisButClass.prototype.DisButMethod();
	 if((document.getElementById("comments").value)=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant
		 {
		 alert("Please Select Comments");
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
	 
	   	var contextPath=document.getElementById("contextPath").value;
			var assetVerificationID=document.getElementById("assetVerificationID").value;
			// alert("assetVerificationID"+assetVerificationID);
		   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=saveAssetCapRepAuthor&assetVerificationID="+assetVerificationID;
			document.getElementById("processingImage").style.display = '';
		   	document.getElementById("sourcingForm").submit();
	   	     return true;
		  
		   }



function newAssetVerificationInit()
{
var contextPath=document.getElementById("contextPath").value;
document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInit.do?method=assetVerificationNew";
document.getElementById("sourcingForm").submit();
return true;
}

function assetVerificationInitSearch()
{
	
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	var contextPath=document.getElementById("contextPath").value;
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	
	if(reportingToUserId!=''||loanNo!=''||customerName!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("save").removeAttribute("disabled", "true");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}
}

//
//
// function assetVerificationInitSearch()
// {
// if(document.getElementById("loanNo").value=='' &&
// document.getElementById("customerName").value==''){
// alert("*Please Selecet at least one field");
// return false;
// }
// else
// {
// var contextPath=document.getElementById("contextPath").value;
// document.getElementById("sourcingForm").action
// =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
// document.getElementById("sourcingForm").submit();
// return true;
// }
// }


function forwardAssetVerification()  {


if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
		alert("*Internal Appraiser is required");
		document.getElementById("assetForwardButton").removeAttribute("disabled","true");
		return false;	
		}
		else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){ 		
			alert("*External Appraiser is required");
			document.getElementById("assetForwardButton").removeAttribute("disabled","true");
			return false;	
	   	}
		else if(document.getElementById("recStatus").value=="")
		{
			alert("You can't move without saving before Asset Verification Initiation.");
			document.getElementById("assetForwardButton").removeAttribute("disabled","true");
			return false;	
		}

	var contextPath=document.getElementById("contextPath").value;
	var flag=0;
	
	 var ch=document.getElementsByName('chk');
	 
	 var loanAccNo1= document.getElementsByName('loanAccNo1');
	 var assetID1 = document.getElementsByName('assetID1');
	 var assetDescription1=document.getElementsByName('assetDescription1');	
	 
 
  
	var loanIDList="";
	var loanAccNoList="";
	var assetIDList="";
	var assetDescriptionList ="";
	
	
 for(i=0;i<ch.length;i++){
			 
			 if(ch[i].checked==true){
				 
				loanIDList=loanIDList + ch[i].value + "/";
  
	

				assetIDList = assetIDList + assetID1[i].value + "/";

				assetDescriptionList = assetDescriptionList + assetDescription1[i].value + "/";
				 
			 
 			  flag=1;
 		
 			}
				 
		 }

	 if(flag==0)
	 {
		 alert("Please select One record");
		 document.getElementById("assetForwardButton").removeAttribute("disabled","true");
		 return false;
	 }
	var confrm = confirm("Do you want to continue ?");
	 if(confrm){
	 
	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=forwardAssetVerification&loanIDList="+loanIDList+"&assetIDList="+assetIDList+"&assetDescriptionList="+assetDescriptionList;
	document.getElementById("sourcingForm").submit();
     return true;
     
	 }else{
		document.getElementById("assetForwardButton").removeAttribute("disabled","true");
		 return false;
	 }

}

function modifyAssetVerification()  {

	if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
   		
		alert("*Internal Appraiser is required");
		document.getElementById("assetButton").removeAttribute("disabled","true");
		return false;	
   	}
	else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
			   		
				alert("*External Appraiser is required");
				document.getElementById("assetButton").removeAttribute("disabled","true");
				return false;	
		   	}
	if((document.getElementById("appraiser").value)=="Internal"){
		var appraisertype='I';
	}else{
		var appraisertype='EA';
	}
	
	   var loanNo=document.getElementById("loanNo").value;
	if(document.getElementById("internalAppraiser").value!=""){
		Appraiserid=document.getElementById("internalAppraiser").value;
	}else{
		Appraiserid=document.getElementById("externalAppraiser").value
	}
	if(document.getElementById("lbxUserId").value!=""){
		Appraiser=document.getElementById("lbxUserId").value;
	}else{
		Appraiser=document.getElementById("lbxextApprHID").value
	}
	
	   	var contextPath=document.getElementById("contextPath").value;
	   	var flag=0;
	   	
	   	 var ch=document.getElementsByName('chk');
	   	 
	   	 var loanAccNo1= document.getElementsByName('loanAccNo1');
	   	 var assetID1 = document.getElementsByName('assetID1');
	   	 var assetDescription1=document.getElementsByName('assetDescription1');	
	   	 
	    var loanid=document.getElementById('loanidval').value;
	    
	   	var loanIDList="";
	   	var loanAccNoList="";
	   	var assetIDList="";
	   	var assetDescriptionList ="";
	   	
	   	
	    for(i=0;i<ch.length;i++){
	   			 
	   			 if(ch[i].checked==true){
	   				 
	   				assetIDList = assetIDList + assetID1[i].value + "/";
	   			     
	   				flag=1;
	    		
	    			}
	   				 
	   		 }
	 
	   	 if(flag==0)
	   	 {
	   		 alert("Please select One record");
	   		 document.getElementById("assetButton").removeAttribute("disabled","true");
	   		 return false;
	   	 }
	   	var confrm = confirm("Do you want to continue ?");
	   	 if(confrm){
	   	 
	   		 document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=modifyAssetVerification&loanId="+loanid+"&assetIDList="+assetIDList+"&appraisertype="+appraisertype+"&Appraiserid="+Appraiserid+"&Appraiser="+Appraiser+"&loanNo="+loanNo;
	   		
	   		 document.getElementById("sourcingForm").submit();
		
   	     return true;
   	     
	   	 }else{
	   		document.getElementById("assetButton").removeAttribute("disabled","true");
	   		 return false;
	   	 }
	   
	   }


function modifyforwardAssetVerification(a,b,c,d)  {
	 var flag=0;
	var appraiserid="";
	
		
if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
	
	alert("*Internal Appraiser is required");
	document.getElementById("assetForwardButton").removeAttribute("disabled","true");
	return false;	
}
else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
		   		
			alert("*External Appraiser is required");
			document.getElementById("assetForwardButton").removeAttribute("disabled","true");
			return false;	
	   	}
	if(c=="Internal"){
	var appraiser="I";
	appraiserid=d;
}else{
	var appraiser="EA";
	appraiserid=b;
}


	var contextPath=document.getElementById("contextPath").value;
	
	
	 var ch=document.getElementsByName('chk');
	 

	 var assetID1 = document.getElementsByName('assetID1');
	 var assetIDList="";
	 for(i=0;i<ch.length;i++){
			   			 
		 if(ch[i].checked==true){
			
			 assetIDList = assetIDList + assetID1[i].value + "/";
			
			flag=1;
		 }
	 }
	 if(flag==0)
	 {
		 alert("Please select One record");
		 document.getElementById("assetForwardButton").removeAttribute("disabled","true");
		 return false;
	 }
	var confrm = confirm("Do you want to continue ?");
	 if(confrm){
		
	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=modifyForwardAssetVerification&loanId="+a+"&appraiserid="+appraiserid+"&appraiser="+appraiser+"&assetIDList="+assetIDList;
	
		 document.getElementById("sourcingForm").submit();
     return true;

	 }else{
		 document.getElementById("assetForwardButton").removeAttribute("disabled","true");
		 return false;
	 }

}

function validRepayDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var agreementDate=document.getElementById("agreementDate").value;
	// var sanctionedDate=document.getElementById("sanctionedValidtill").value;
	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
// var dt1=getDateObject(agreementDate,formatD.substring(2, 3));
// var dt2=getDateObject(sanctionedDate,formatD.substring(2, 3));
// var dt3=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    // alert("repayEffectiveDate: "+repayEffectiveDate+"Converted Bussiness
	// Date: "+dt3+"formatD.substring(2, 3): "+formatD.substring(2, 3));
  // alert("validAggrDate: agreementDate(dt1) "+dt1+"sanctionedDate(dt2):
	// "+dt2+"bDate(dt3): "+dt3);
 
    
    if(dt1<dt3)
	{
		msg="Please enter Repay Effective date greater than bussiness Date ";
		document.getElementById("repayEffectiveDate").value='';
		// return false;
	}

// if(dt1>dt3)
// {
// msg="Please enter Repay Effective date greater than Agrement Date ";
// document.getElementById("repayEffectiveDate").value='';
// //return false;
// }
//
// if(dt2<dt3)
// {
// msg="Please enter Repay Effective less than sanctioned Date ";
// document.getElementById("repayEffectiveDate").value='';
// //return false;
// }
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
	// var sanctionedDate=document.getElementById("sanctionedValidtill").value;
	var bDate=document.getElementById("bDate").value;
	var repayDate=document.getElementById("repayEffectiveDateOneLoan").value;
// var dt1=getDateObject(agreementDate,formatD.substring(2, 3));
// var dt2=getDateObject(sanctionedDate,formatD.substring(2, 3));
// var dt3=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    
    var dt1=getDateObject(repayDate,formatD.substring(2, 3));
    var dt2=getDateObject(bDate,formatD.substring(2, 3));
    // alert("repayEffectiveDate: "+repayEffectiveDate+"Converted Bussiness
	// Date: "+dt3+"formatD.substring(2, 3): "+formatD.substring(2, 3));
  // alert("validAggrDate: agreementDate(dt1) "+dt1+"sanctionedDate(dt2):
	// "+dt2+"bDate(dt3): "+dt3);
 
    
    if(dt1<dt2)
	{
		msg="Please enter Repay Effective date greater than bussiness Date ";
		document.getElementById("repayEffectiveDateOneLoan").value='';
		// return false;
	}

// if(dt1>dt3)
// {
// msg="Please enter Repay Effective date greater than Agrement Date ";
// document.getElementById("repayEffectiveDate").value='';
// //return false;
// }
//
// if(dt2<dt3)
// {
// msg="Please enter Repay Effective less than sanctioned Date ";
// document.getElementById("repayEffectiveDate").value='';
// //return false;
// }
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

function onSaveInstal()
{
	//alert("vss");
	
	DisButClass.prototype.DisButMethod();
	
	var contextPath=document.getElementById("contextPath").value;
	var recoveryType=document.getElementById("recoveryType").value;
	var loanAmount=document.getElementById("loanAmount").value;
	var formatD=document.getElementById("formatD").value;
	//add by vijendra
	var frequency=document.getElementById("frequency").value;
	var maxDate=document.getElementById("maxDate").value;
	//end by vijendra
	var repayEffDateObj="";
	var dueDateObj="";
	var insNextDueDateObj="";
	// var fromInstallment=document.getElementById("fromInstallment").value;
	// var toInstallment=document.getElementById("toInstallment").value;
	var installmentType=document.getElementById("installmentType").value;
	var totalInstallment=document.getElementById("totalInstallment").value;
	var repayEffDate=document.getElementById("repayeffdate").value;
	repayEffDateObj=getDateObject(repayEffDate,formatD.substring(2, 3));
	var gridTable = document.getElementById('gridTable');
	var tableLength = gridTable.rows.length-1;
	var editDueDate=document.getElementById("editDueDate").value;
	var insNextDueDate=document.getElementById("insNextDueDate").value;
	insNextDueDateObj=getDateObject(insNextDueDate,formatD.substring(2,3));

	
	  var sum=0;
	  var psum=0;
	  var isum=0;

	  // Vishal changes start
	  
	  if(editDueDate=='Y')
		  {
			for(var i=1;i<=tableLength;i++)
			{
				var fromInstallment1=document.getElementById("fromInstallment1").value;
				var fromInstallment=document.getElementById("fromInstallment"+i).value;
			
				var toInstallment=document.getElementById("toInstallment"+i).value;
			    
				var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
				
				var principalAmount=document.getElementById("principalAmount"+i).value;
				
				var installmentAmount=document.getElementById("installmentAmount"+i).value;
				
				var dueDate=document.getElementById("dueDate"+i).value;
				dueDateObj=getDateObject(dueDate,formatD.substring(2, 3));

				 if(recoveryType=='F' && (fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")||(dueDate==""))
				 {
					 alert("Please fill all the fields ");
					 document.getElementById("save").removeAttribute("disabled","true");
					 DisButClass.prototype.EnbButMethod();
					 return false;
				 }
				 
				 if(fromInstallment==0){
					 alert("From Installment cannot be Zero");
					 document.getElementById("save").removeAttribute("disabled","true");
					 DisButClass.prototype.EnbButMethod();
					 return false;
					      }	
				 
				 
				 if(fromInstallment1!=1){
					 alert("First Installment should start from 1");
					 document.getElementById("save").removeAttribute("disabled","true");
					 DisButClass.prototype.EnbButMethod();
					 return false;
					      }	
				 
				 if(i==1)
					 {
					 var instDueDate= document.getElementById("dueDate"+i).value;
					 var insNextDueDate= document.getElementById("insNextDueDate").value;
					 var dueDay= instDueDate.substring(0,2);
					 var instDay= insNextDueDate.substring(0,2);
					 var dueMonth=instDueDate.substring(3,5);
					 var instMonth=insNextDueDate.substring(3,5);
					 var dueYear=instDueDate.substring(6);
					 var instYear=insNextDueDate.substring(6);
			
					 if(parseFloat(dueYear)!=parseFloat(instYear))
					 				{
					 					alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
					 					document.getElementById("save").removeAttribute("disabled","true");
					 					DisButClass.prototype.EnbButMethod();
					 					return false;
					 				}
					 				else
					 				{
					 					if(parseFloat(dueYear)==parseFloat(instYear))
					 					if(parseFloat(dueMonth)!=parseFloat(instMonth))
					 					{
					 						alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
					 						document.getElementById("save").removeAttribute("disabled","true");
					 						DisButClass.prototype.EnbButMethod();
					 						return false;
					 					}
					 					else
					 					{
					 						if(parseFloat(dueMonth)==parseFloat(instMonth))
					 						if(parseFloat(dueDay)!=parseFloat(instDay))
					 						{
					 							alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
					 							document.getElementById("save").removeAttribute("disabled","true");
					 							DisButClass.prototype.EnbButMethod();
					 							return false;
					 						}
					 					}
					 				} 
				
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
				 else{   	  
					recoveryPercen=removeComma(recoveryPercen);
				 }		
			    sum= sum + recoveryPercen;
				// add by vijendra singh || allow till for decimal
				sum=parseFloat(sum.toFixed(4));													    
			   // end by vijendra singh
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
			    
			   
		
			    if(parseInt(document.getElementById("fromInstallment"+i).value) != parseInt(document.getElementById("toInstallment"+i).value))
				{	    	
					alert("From Installment should be equal to To Installment");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			 
				
				if(i<tableLength){	
					var k=i+1;
			if(parseInt(document.getElementById("toInstallment"+i).value) >= parseInt(document.getElementById("fromInstallment"+k).value))
				{
					
					alert("Next From Installment should be greater than previous To Installment");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			
				 var dueDateOne= document.getElementById("dueDate"+i).value;
				 var dueDateTwo= document.getElementById("dueDate"+k).value;
				 var day1= dueDateOne.substring(0,2);
				 var day2= dueDateTwo.substring(0,2);
				 var month1=dueDateOne.substring(3,5);
				 var month2=dueDateTwo.substring(3,5);
				 var year1=dueDateOne.substring(6);
				 var year2=dueDateTwo.substring(6);
		
				 if(parseFloat(year1)>parseFloat(year2))
				 				{
				 					alert("Next due date should be greater than previous due date.");
				 					document.getElementById("save").removeAttribute("disabled","true");
				 					DisButClass.prototype.EnbButMethod();
				 					return false;
				 				}
				 				else
				 				{
				 					if(parseFloat(year1)==parseFloat(year2))
				 					if(parseFloat(month1)>parseFloat(month2))
				 					{
				 						alert("Next due date should be greater than previous due date.");
				 						document.getElementById("save").removeAttribute("disabled","true");
				 						DisButClass.prototype.EnbButMethod();
				 						return false;
				 					}
				 					else
				 					{
				 						if(parseFloat(month1)==parseFloat(month2))
				 						if(parseFloat(day1)>=parseFloat(day2))
				 						{
				 							alert("Next due date should be greater than previous due date.");
				 							document.getElementById("save").removeAttribute("disabled","true");
				 							DisButClass.prototype.EnbButMethod();
				 							return false;
				 						}
				 					}
				 				} 		
		
				}
			
			}
			 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
			var maxDueDateObj="";
			var maxDateObj="";
			var lastToInstall=document.getElementById("toInstallment"+tableLength).value;

			var maxDueDate=document.getElementById("dueDate"+tableLength).value;
			maxDueDateObj=getDateObject(maxDueDate,formatD.substring(2, 3));
			var maxDate=document.getElementById("maxDate").value;
			maxDateObj=getDateObject(maxDate,formatD.substring(2, 3));
			if(maxDueDateObj>maxDateObj)
				{
				alert('Last Due date must be equal to '+ maxDate );
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			if(maxDueDateObj<maxDateObj)
			{
			alert('Last Due date must be equal to '+ maxDate );
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}

			if(parseInt(lastToInstall)> parseInt(totalInstallment)){
				alert("No of  Installment should be equal to  Total Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(parseInt(lastToInstall)< parseInt(totalInstallment)){
				alert("No of  Installment should be equal to  Total Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(sum!=100 && recoveryType=='P')
			{
				alert("Total recovery Percentage should be  equal to 100 %");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			if(((psum>removeComma(loanAmount)||psum<removeComma(loanAmount)) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'||installmentType=='I' || installmentType=='S'))
			{
				alert("Principal Amount should be equal to loan amount: "+loanAmount);
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			if((isum<removeComma(loanAmount) && recoveryType=='F' && (installmentType=='E'||installmentType=='G')))
			{
				
				alert("Sum of Installment Amount should be equal to or greater than loan amount: "+loanAmount);
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanProcess.do?method=saveInstallmentPlan&installmentType="+installmentType+"&editDueDate="+editDueDate+"&frequency="+frequency+"&maxDate="+maxDate;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("installmentPlanForm").submit();
		     return true;
		  		
		  }
	  // Vishal changes end
	  
	  	  else
		  {
			  for(var i=1;i<=tableLength;i++)
				{
					var fromInstallment1=document.getElementById("fromInstallment1").value;
					var fromInstallment=document.getElementById("fromInstallment"+i).value;
				
					var toInstallment=document.getElementById("toInstallment"+i).value;
				    
					var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
					
					var principalAmount=document.getElementById("principalAmount"+i).value;
					
					var installmentAmount=document.getElementById("installmentAmount"+i).value;
					if(installmentType=='I')
					{
					var dueDate=document.getElementById("dueDate"+i).value;
					dueDateObj=getDateObject(dueDate,formatD.substring(2, 3));
					}
					 if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")&& installmentType!='I')
					 {
						 alert("Please fill all the fields ");
						 document.getElementById("save").removeAttribute("disabled","true");
						 DisButClass.prototype.EnbButMethod();
						 return false;
					 }
					 if(recoveryType=='F' && installmentType=='I' && (fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")||(dueDate==""))
					 {
						 alert("Please fill all the fields ");
						 document.getElementById("save").removeAttribute("disabled","true");
						 DisButClass.prototype.EnbButMethod();
						 return false;
					 }
					 
					 if(fromInstallment==0){
						 alert("From Installment cannot be Zero");
						 document.getElementById("save").removeAttribute("disabled","true");
						 DisButClass.prototype.EnbButMethod();
						 return false;
						      }	
					 
					 
					 if(fromInstallment1!=1){
						 alert("First Installment should start from 1");
						 document.getElementById("save").removeAttribute("disabled","true");
						 DisButClass.prototype.EnbButMethod();
						 return false;
						      }	
					 
					 if(installmentType=='I')
						 {
						 	if(dueDateObj<repayEffDateObj)
							 {
							 alert("Due Date cannot be less than RepayEffective Date.");
							 document.getElementById("save").removeAttribute("disabled","true");
							 DisButClass.prototype.EnbButMethod();
							 return false;
							 }
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
				  // add by vijendra singh || allow till for decimal
				   sum=parseFloat(sum.toFixed(4));					  
				 // end by vijendra singh
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
						
						alert("Next from Installment should be greater than previous to Installment");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
				{
					
					alert("Next from Installment should be equal to previous to Installment");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				 if(installmentType=='I')
				 {
				
					 var dueDateOne= document.getElementById("dueDate"+i).value;
					 var dueDateTwo= document.getElementById("dueDate"+k).value;
					 var day1= dueDateOne.substring(0,2);
					 var day2= dueDateTwo.substring(0,2);
					 var month1=dueDateOne.substring(3,5);
					 var month2=dueDateTwo.substring(3,5);
					 var year1=dueDateOne.substring(6);
					 var year2=dueDateTwo.substring(6);
			
					 if(parseFloat(year1)>parseFloat(year2))
					 				{
					 					alert("Next due date should be greater than previous due date.");
					 					document.getElementById("save").removeAttribute("disabled","true");
					 					DisButClass.prototype.EnbButMethod();
					 					return false;
					 				}
					 				else
					 				{
					 					if(parseFloat(year1)==parseFloat(year2))
					 					if(parseFloat(month1)>parseFloat(month2))
					 					{
					 						alert("Next due date should be greater than previous due date.");
					 						document.getElementById("save").removeAttribute("disabled","true");
					 						DisButClass.prototype.EnbButMethod();
					 						return false;
					 					}
					 					else
					 					{
					 						if(parseFloat(month1)==parseFloat(month2))
					 						if(parseFloat(day1)>=parseFloat(day2))
					 						{
					 							alert("Next due date should be greater than previous due date.");
					 							document.getElementById("save").removeAttribute("disabled","true");
					 							DisButClass.prototype.EnbButMethod();
					 							return false;
					 						}
					 					}
					 				} 
			
				 }
			
					}
				
				}
				 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
				var maxDueDateObj="";
				var maxDateObj="";
				var lastToInstall=document.getElementById("toInstallment"+tableLength).value;
				 if(installmentType=='I')
				 {
				var maxDueDate=document.getElementById("dueDate"+tableLength).value;
				maxDueDateObj=getDateObject(maxDueDate,formatD.substring(2, 3));
				var maxDate=document.getElementById("maxDate").value;
				maxDateObj=getDateObject(maxDate,formatD.substring(2, 3));
				if(maxDueDateObj>maxDateObj)
					{
					alert('Last Due date cannot be greater than repayeffective date + tenure ');
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
					}
				 }
				if(parseInt(lastToInstall)> parseInt(totalInstallment)){
					alert("No of  Installment should be equal to  Total Installment");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(parseInt(lastToInstall)< parseInt(totalInstallment)){
					alert("No of  Installment should be equal to  Total Installment");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(sum!=100 && recoveryType=='P')
				{
					alert("Total recovery Percentage should be  equal to 100 %");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(((psum>removeComma(loanAmount)||psum<removeComma(loanAmount)) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'||installmentType=='I' || installmentType=='S'))
				{
					alert("Principal Amount should be equal to loan amount: "+loanAmount);
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
				if((isum<removeComma(loanAmount) && recoveryType=='F' && (installmentType=='E'||installmentType=='G')))
				{
					
					alert("Sum of Installment Amount should be equal to or greater than loan amount: "+loanAmount);
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanProcess.do?method=saveInstallmentPlan&installmentType="+installmentType+"&editDueDate="+editDueDate+"&frequency="+frequency+"&maxDate="+maxDate;;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("installmentPlanForm").submit();
			     return true;
		  }

}


//Added By Rahul papneja

var addMonths = (function() {
	  var counts = {
	    normal: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
	    leap:   [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
	  };
	  return function(startDate, months)
	  { 
			var endYear = startDate.getFullYear() + Math.ceil((months + startDate.getMonth()+1) / 12)-1 ;
			 //alert("endYear:::"+endYear);
			var yearType = ((endYear % 4 == 0) && (endYear % 100 != 0)) || (endYear % 400 == 0) ? 'leap' : 'normal';
			var endMonth = (startDate.getMonth() + months) % 12;
			var endDate = Math.min(startDate.getDate(), counts[yearType][endMonth]);
			 //alert("yearType::::"+ yearType);
			return new Date(endYear, endMonth, endDate);
			 //alert("Method::::"+ new Date( endMonth+"/"+endDate+"/"+endYear));
			//  return new Date( endMonth+"/"+endDate+"/"+endYear);
	  };
	}());
//start here brijesh pathak
var addMonths2 = (function() {
	  var counts = {
	    normal: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
	    leap:   [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
	  };
	  return function(startDate, months)
	  { 
			var endYear = startDate.getFullYear() + Math.ceil((months + startDate.getMonth()+1) / 12)-1 ;
			// alert("endYear:::"+endYear);
			var yearType = ((endYear % 4 == 0) && (endYear % 100 != 0)) || (endYear % 400 == 0) ? 'leap' : 'normal';
			var endMonth = (startDate.getMonth() + months) % 12;
			//alert("endMonth "+endMonth);
			//var endDate = Math.max(startDate.getDate(), counts[yearType][endMonth]);
			if(yearType=='leap' && endMonth==1)
			{
				//alert("first");
				var endDate=startDate.setDate(29);	
				var  endDate = Math.max(startDate.getDate(), counts[yearType][endMonth]);
			}
			else
			{  
				if(endMonth==1)
				{	//alert("second");
					var endDate=startDate.setDate(28);	
					var  endDate = Math.max(startDate.getDate(), counts[yearType][endMonth]);
				}
				else
				{
					//alert("third");
					var  endDate = Math.max(startDate.getDate(), counts[yearType][endMonth]);
				}
			}
			//var  endDate = Math.max(startDate.getDate(), counts[yearType][endMonth]);
			return new Date(endYear, endMonth, endDate);
			// alert("Method::::"+ new Date( endMonth+"/"+endDate+"/"+endYear));
			//  return new Date( endMonth+"/"+endDate+"/"+endYear);
	  };
	}());
//end here brijesh pathak
// Ends Here
// manisha
//start By vijendra 
function autoDueDate(){

var noOfInstall = parseInt(document.getElementById("totalInstallment").value);

var frequency = document.getElementById("frequency").value;
var insNextDueDate = document.getElementById("insNextDueDate").value;
//alert("insNextDueDate "+insNextDueDate);
var installmentType = document.getElementById("installmentType").value;
var maxDate = document.getElementById("maxDate").value;
var insNextDueDate1 = insNextDueDate.split("-");
//alert("Year::"+insNextDueDate1[2] + " Month ::"+ insNextDueDate1[1]+" Day::"+insNextDueDate1[0]);
//var d = new Date(insNextDueDate1[2], insNextDueDate1[1], insNextDueDate1[0]);
var partDate=insNextDueDate1[1]+"/"+insNextDueDate1[0]+"/"+insNextDueDate1[2];
var d= new Date(partDate);
//alert("D::"+d);
var currDay   = insNextDueDate.substring(0,2);
//alert("currDay "+currDay);
document.getElementById("dueDate1").value=insNextDueDate;   
var c;
for(var i=2;i<=noOfInstall;i++)
{        
	if(frequency=='M')
	{	
		if(currDay==30)
		{
		c=addMonths2(d,i-1);
		//alert("C :"+c +" i::"+i);
		}
		else
		{
			c=addMonths(d,i-1);
		//alert("C :"+c +" i::"+i);
		}
  
    var curr_date = c.getDate();
    var curr_month = c.getMonth(); //Months are zero based
    var curr_year = c.getFullYear();
   		
	}
	else if(frequency=='B')
	{
   /* if(i==2)
		d.setMonth(d.getMonth() +1);
    else
		d.setMonth(d.getMonth() +2);			
   
   var curr_date = d.getDate();
   var curr_month = d.getMonth()+1; //Months are zero based
   var curr_year = d.getFullYear();
  */
		
		c=addMonths(d,(i-1)*2);
		//alert("C :"+c +" i::"+i);
		
  
    var curr_date = c.getDate();
    var curr_month = c.getMonth(); //Months are zero based
    var curr_year = c.getFullYear();
		
		
		
	}
	else if(frequency=='Q')
	{
		
		
		c=addMonths(d,(i-1)*3);
		//alert("C :"+c +" i::"+i);
		
  
    var curr_date = c.getDate();
    var curr_month = c.getMonth(); //Months are zero based
    var curr_year = c.getFullYear();
		
	
	/* if(i==2)
		d.setMonth(d.getMonth() +2);
    else
		d.setMonth(d.getMonth() +3);
	
    var curr_date = d.getDate();
    var curr_month = d.getMonth()+1; //Months are zero based
    var curr_year = d.getFullYear();*/
	}
	else if(frequency=='H')
	{ 
		
		c=addMonths(d,(i-1)*6);
		//alert("C :"+c +" i::"+i);
		
  
    var curr_date = c.getDate();
    var curr_month = c.getMonth(); //Months are zero based
    var curr_year = c.getFullYear();
		

/*   if(i==2)
		d.setMonth(d.getMonth() +5);
    else
		d.setMonth(d.getMonth() +6);			
   
    var curr_date = d.getDate();
    var curr_month = d.getMonth()+1; //Months are zero based
    var curr_year = d.getFullYear();*/
 	
	}
   else if(frequency=='Y')
   {
	   c=addMonths(d,(i-1)*12);
		//alert("C :"+c +" i::"+i);
		
 
   var curr_date = c.getDate();
   var curr_month = c.getMonth(); //Months are zero based
   var curr_year = c.getFullYear();
		
    
	/*if(i==2)
		d.setMonth(d.getMonth() +11);
    else
		d.setMonth(d.getMonth() +12);			
    
	var curr_date = d.getDate();
    var curr_month = d.getMonth()+1; //Months are zero based
    var curr_year = d.getFullYear();*/
   
   }			
	
   if(i==noOfInstall)
	 document.getElementById("dueDate"+i).value=maxDate;  
   else
   document.getElementById("dueDate"+i).value=padding(curr_date)+"-" +padding(curr_month+1)+"-"+curr_year;

 }

}
function padding(number){

	return number < 10 ? "0"+number : number;

	}
// To insert new row
function addROW(){
	
	
	var installmentType = document.getElementById("installmentType").value;
 	var recoveryType = document.getElementById("recoveryType").value;
   	var princAm =	 document.getElementsByName("principalAmount"); 
   	var installmentAmount =	 document.getElementsByName("installmentAmount"); 
	var recoveryPer =	 document.getElementsByName("recoveryPer"); 
	var dueDate= document.getElementsByName("dueDate");
	var editDueDate= document.getElementById("editDueDate").value;

	// alert("addROW");
	if(recoveryType=='F')
   	{
   		if(installmentType=='P'||installmentType=='Q' || installmentType=='S')
   		{
   				var table = document.getElementById("gridTable");

   				var rowCount = table.rows.length;
   				var row = table.insertRow(rowCount);
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+rowCount;
   				cell1.appendChild(element1);
   				if(editDueDate=='Y')
					{

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "dueDate";
   				element2.id = "dueDate"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				element2.setAttribute('readonly','true' );
   				cell2.appendChild(element2);
   				$(function() {
   					var contextPath =document.getElementById('contextPath').value ;
   					//$("#recievedDate"+i).datepicker({
   					$("#dueDate"+rowCount).datepicker({
   					format: "%Y-%m-%d %H:%i:%s %E %#",
   					formatUtcOffset: "%: (%@)",
   					placement: "inline",
   		 			changeMonth: true,
   		 			changeYear: true,
   		 			yearRange: '1900:+10',
   		 			showOn: 'both',
   					buttonImage: contextPath+'/images/theme1/calendar.gif',
   					buttonImageOnly: true,
   			        dateFormat: document.getElementById("formatD").value,
   					defaultDate: document.getElementById("businessdate").value

   					});
   				});
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "fromInstall";
   				element3.id = "fromInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "toInstall";
   				element4.id = "toInstallment"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				cell4.appendChild(element4);
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "recoveryPer";
   				element5.value = "0";
   				element5.id = "recoveryPercen"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				element5.setAttribute('readonly','true' );
   				cell5.appendChild(element5);
   				
   				
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "principalAmount";
   				element6.value = "0";
   				element6.id = "principalAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				//element6.setAttribute('readonly','true');
   				cell6.appendChild(element6);
   				
   				
   				var cell7 = row.insertCell(6);
   				var element7 = document.createElement("input");
   				element7.type = "text";
   				element7.name = "installmentAmount";
   				element7.value = "0";
   				element7.id = "installmentAmount"+rowCount;
   				element7.setAttribute('class','text');
   				element7.setAttribute('className','text' );
   				element7.setAttribute('readonly','true' );
   				element7.onblur= function(){
   					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
   				};
   				element7.onfocus=function keyUpNumber(e){
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
   						element7.onkeypress = function numbersonly(e){
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
   				element7.onkeyup= function(){
   					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
   				};
   				cell7.appendChild(element7);
   				
   				// Vishal changes end
   					}
   				
   				else
   					{
   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "fromInstall";
   				element2.id = "fromInstallment"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				cell2.appendChild(element2);
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "toInstall";
   				element3.id = "toInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "recoveryPer";
   				element4.value = "0";
   				element4.id = "recoveryPercen"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				element4.setAttribute('readonly','true' );
   				cell4.appendChild(element4);
   				
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "principalAmount";
   				element5.id = "principalAmount"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				cell5.appendChild(element5);
   				
   				
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "installmentAmount";
   				element6.value = "0";
   				element6.id = "installmentAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				element6.setAttribute('readonly','true' );
   				element6.onblur= function(){
   					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
   				};
   				element6.onfocus=function keyUpNumber(e){
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
   						element6.onkeypress = function numbersonly(e){
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
   				element6.onkeyup= function(){
   					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
   				};
   				cell6.appendChild(element6);
   					}
   				
   			}
	   		else
	   		{
	   			var table = document.getElementById("gridTable");

   				var rowCount = table.rows.length;
   				var row = table.insertRow(rowCount);
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+rowCount;
   				cell1.appendChild(element1);
   				
   			// Vishal changes start
   				if(editDueDate=='Y'||installmentType=='I'||installmentType=='J')
   					{
   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "dueDate";
   				element2.id = "dueDate"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				element2.setAttribute('readonly','true' );
   				cell2.appendChild(element2);
   				$(function() {
   					var contextPath =document.getElementById('contextPath').value ;
   					//$("#recievedDate"+i).datepicker({
   					$("#dueDate"+rowCount).datepicker({
   					format: "%Y-%m-%d %H:%i:%s %E %#",
   					formatUtcOffset: "%: (%@)",
   					placement: "inline",
   		 			changeMonth: true,
   		 			changeYear: true,
   		 			yearRange: '1900:+10',
   		 			showOn: 'both',
   					buttonImage: contextPath+'/images/theme1/calendar.gif',
   					buttonImageOnly: true,
   			        dateFormat: document.getElementById("formatD").value,
   					defaultDate: document.getElementById("businessdate").value

   					});
   				});
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "fromInstall";
   				element3.id = "fromInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "toInstall";
   				element4.id = "toInstallment"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				cell4.appendChild(element4);
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "recoveryPer";
   				element5.value = "0";
   				element5.id = "recoveryPercen"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				element5.setAttribute('readonly','true' );
   				cell5.appendChild(element5);
   				
   				if(installmentType=='I'||installmentType=='J')
   					{
				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "principalAmount";
   				element6.value = "0";
   				element6.id = "principalAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				//element6.setAttribute('readonly','true' );
   				cell6.appendChild(element6);
   				
   				
   				var cell7 = row.insertCell(6);
   				var element7 = document.createElement("input");
   				element7.type = "text";
   				element7.name = "installmentAmount";
   				element7.value = "0";
   				element7.id = "installmentAmount"+rowCount;
   				element7.setAttribute('class','text');
   				element7.setAttribute('className','text' );
   				element7.setAttribute('readonly','true');
   				element7.onblur= function(){
   					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
   				};
   				element7.onfocus=function keyUpNumber(e){
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
   						element7.onkeypress = function numbersonly(e){
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
   				element7.onkeyup= function(){
   					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
   				};
   				cell7.appendChild(element7);
   					}
   				else
   					{
   					var cell6 = row.insertCell(5);
   	   				var element6 = document.createElement("input");
   	   				element6.type = "text";
   	   				element6.name = "principalAmount";
   	   				element6.value = "0";
   	   				element6.id = "principalAmount"+rowCount;
   	   				element6.setAttribute('class','text');
   	   				element6.setAttribute('className','text' );
   	   				element6.setAttribute('readonly','true' );
   	   				cell6.appendChild(element6);
   	   				
   	   				
   	   				var cell7 = row.insertCell(6);
   	   				var element7 = document.createElement("input");
   	   				element7.type = "text";
   	   				element7.name = "installmentAmount";
   	   				element7.value = "0";
   	   				element7.id = "installmentAmount"+rowCount;
   	   				element7.setAttribute('class','text');
   	   				element7.setAttribute('className','text' );
   	   				//element7.setAttribute('readonly','true');
   	   				element7.onblur= function(){
   	   					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
   	   				};
   	   				element7.onfocus=function keyUpNumber(e){
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
   	   						element7.onkeypress = function numbersonly(e){
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
   	   				element7.onkeyup= function(){
   	   					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
   	   				};
   	   				cell7.appendChild(element7);
   					}
   				
   					}	
   				// Vishal changes end
   				else
   					{

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "fromInstall";
   				element2.id = "fromInstallment"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				cell2.appendChild(element2);
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "toInstall";
   				element3.id = "toInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "recoveryPer";
   				element4.value = "0";
   				element4.id = "recoveryPercen"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				element4.setAttribute('readonly','true' );
   				cell4.appendChild(element4);
   				
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "principalAmount";
   				element5.value = "0";
   				element5.id = "principalAmount"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				element5.setAttribute('readonly','true' );
   				cell5.appendChild(element5);
   				
   				
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "installmentAmount";
   				element6.id = "installmentAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				element6.onblur= function(){
   					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
   				};
   				element6.onfocus=function keyUpNumber(e){
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
   						element6.onkeypress = function numbersonly(e){
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
   				element6.onkeyup= function(){
   					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
   				};
   				cell6.appendChild(element6);
   					}
	   		}
   		}
	else
	{
		var table = document.getElementById("gridTable");

			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			row.setAttribute('className','white1' );
		    row.setAttribute('class','white1' );
			var cell1 = row.insertCell(0);
			var element1 = document.createElement("input");
			element1.type = "checkbox";
			element1.name = "chk";
			element1.id = "chk"+rowCount;
			cell1.appendChild(element1);

			// Vishal changes start
			
			if(installmentType=='J')
				{
				var cell2 = row.insertCell(1);
				var element2 = document.createElement("input");
				element2.type = "text";
				element2.name = "dueDate";
				element2.id = "dueDate"+rowCount;
				element2.setAttribute('class','text');
				element2.setAttribute('className','text' );
				element2.setAttribute('readonly','true' );
				cell2.appendChild(element2);
				$(function() {
   					var contextPath =document.getElementById('contextPath').value ;
   					//$("#recievedDate"+i).datepicker({
   					$("#dueDate"+rowCount).datepicker({
   					format: "%Y-%m-%d %H:%i:%s %E %#",
   					formatUtcOffset: "%: (%@)",
   					placement: "inline",
   		 			changeMonth: true,
   		 			changeYear: true,
   		 			yearRange: '1900:+10',
   		 			showOn: 'both',
   					buttonImage: contextPath+'/images/theme1/calendar.gif',
   					buttonImageOnly: true,
   			        dateFormat: document.getElementById("formatD").value,
   					defaultDate: document.getElementById("businessdate").value

   					});
   				});
				
				var cell3 = row.insertCell(2);
				var element3 = document.createElement("input");
				element3.type = "text";
				element3.name = "fromInstall";
				element3.id = "fromInstallment"+rowCount;
				element3.setAttribute('class','text');
				element3.setAttribute('className','text' );
				cell3.appendChild(element3);
				
				var cell4 = row.insertCell(3);
				var element4 = document.createElement("input");
				element4.type = "text";
				element4.name = "toInstall";
				element4.id = "toInstallment"+rowCount;
				element4.setAttribute('class','text');
				element4.setAttribute('className','text' );
				cell4.appendChild(element4);
				
				var cell5 = row.insertCell(4);
				var element5 = document.createElement("input");
				element5.type = "text";
				element5.name = "recoveryPer";
				element5.value = "0";
				element5.id = "recoveryPercen"+rowCount;
				element5.setAttribute('class','text');
				element5.setAttribute('className','text' );
				
				cell5.appendChild(element5);
				
				
				var cell6 = row.insertCell(5);
				var element6 = document.createElement("input");
				element6.type = "text";
				element6.name = "principalAmount";
				element6.value = "0";
				element6.id = "principalAmount"+rowCount;
				element6.setAttribute('class','text');
				element6.setAttribute('className','text' );
				element6.setAttribute('readonly','true' );
				cell6.appendChild(element6);
				
				
				var cell7 = row.insertCell(6);
				var element7 = document.createElement("input");
				element7.type = "text";
				element7.name = "installmentAmount";
				element7.value = "0";
				element7.id = "installmentAmount"+rowCount;
				element7.setAttribute('class','text');
				element7.setAttribute('className','text' );
				element7.setAttribute('readonly','true' );
				element7.onblur= function(){
						formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
					};
					element7.onfocus=function keyUpNumber(e){
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
							element7.onkeypress = function numbersonly(e){
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
					element7.onkeyup= function(){
						checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
					};
				cell7.appendChild(element7);
				
				}
				// Vishal changes end
			else
				{

			var cell2 = row.insertCell(1);
			var element2 = document.createElement("input");
			element2.type = "text";
			element2.name = "fromInstall";
			element2.id = "fromInstallment"+rowCount;
			element2.setAttribute('class','text');
			element2.setAttribute('className','text' );
			cell2.appendChild(element2);
			
			var cell3 = row.insertCell(2);
			var element3 = document.createElement("input");
			element3.type = "text";
			element3.name = "toInstall";
			element3.id = "toInstallment"+rowCount;
			element3.setAttribute('class','text');
			element3.setAttribute('className','text' );
			cell3.appendChild(element3);
			
			var cell4 = row.insertCell(3);
			var element4 = document.createElement("input");
			element4.type = "text";
			element4.name = "recoveryPer";
			element4.value = "0";
			element4.id = "recoveryPercen"+rowCount;
			element4.setAttribute('class','text');
			element4.setAttribute('className','text' );
			
			cell4.appendChild(element4);
			
			
			var cell5 = row.insertCell(4);
			var element5 = document.createElement("input");
			element5.type = "text";
			element5.name = "principalAmount";
			element5.value = "0";
			element5.id = "principalAmount"+rowCount;
			element5.setAttribute('class','text');
			element5.setAttribute('className','text' );
			element5.setAttribute('readonly','true' );
			cell5.appendChild(element5);
			
			
			var cell6 = row.insertCell(5);
			var element6 = document.createElement("input");
			element6.type = "text";
			element6.name = "installmentAmount";
			element6.value = "0";
			element6.id = "installmentAmount"+rowCount;
			element6.setAttribute('class','text');
			element6.setAttribute('className','text' );
			element6.setAttribute('readonly','true' );
			element6.onblur= function(){
					formatNumber(document.getElementById("installmentAmount"+rowCount).value,"installmentAmount"+rowCount);
				};
				element6.onfocus=function keyUpNumber(e){
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
						element6.onkeypress = function numbersonly(e){
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
				element6.onkeyup= function(){
					checkNumber(document.getElementById("installmentAmount"+rowCount).value, this.event, 18,"installmentAmount"+rowCount);
				};
			cell6.appendChild(element6);
				}
	}
   		
	

}
// To remove a row

function removeRow() {   
	 var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	 var chk =document.getElementsByName("chk"); 
	 var count=0;
	 for(var j=1;j<rowCount;j++){
		 if(document.getElementById("chk"+j).checked==true){
			 count=count+1;
			 }
	 }
	
if(count==0){
	alert("Please Select at least one row to delete");
}else{

 
  
  for(var i=1; i<rowCount; i++) {
  	var row = table.rows[i];
      var chkbox = row.cells[0].childNodes[0];
       if(null != chkbox && true == chkbox.checked) 
      	{
      	 table.deleteRow(i);
          rowCount--;
          i--;
      }               
  }
}
}

function InvoColl()
{
	if((document.getElementById("invoiceCollected").value)=="Y"){
		document.getElementById("invoiceNumber").disabled=false;
		return true;	
	}else{
		document.getElementById("invoiceNumber").disabled=true;
		document.getElementById("invoiceNumber").value='';
		return true;
	}
}
function viewPayableWaiveOff(alert1) 
{	
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var bpType=document.getElementById('lbxBusinessPartnearHID').value;
		
	
	if ((loanId=="") || (bpType==""))
		{
			alert(alert1);
			document.getElementById("viewPay").removeAttribute("disabled");
			return false;
		}
	else
	{
		var loanId=document.getElementById('lbxLoanNoHID').value;
		var bpType=document.getElementById('businessPartnerType').value;
		var bpId=document.getElementById('lbxBusinessPartnearHID').value;	
		var basePath=document.getElementById("contextPath").value;
		window.open(basePath+'/waiveOffProcessAction.do?method=viewPaybleWaiveOff&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		return true;
	}
}
		
		function viewReceivableWaiveOff(alert1) 
		{	
			var loanId=document.getElementById('lbxLoanNoHID').value;
			var bpType=document.getElementById('lbxBusinessPartnearHID').value;
				
			
			if ((loanId=="") || (bpType==""))
				{
					alert(alert1);
					document.getElementById("viewRec").removeAttribute("disabled");
					return false;
				}
			else
			{
				var loanId=document.getElementById('lbxLoanNoHID').value;
				var bpType=document.getElementById('businessPartnerType').value;
				var bpId=document.getElementById('lbxBusinessPartnearHID').value;	
				var basePath=document.getElementById("contextPath").value;
				window.open(basePath+'/waiveOffProcessAction.do?method=ViewReceivableWaiveOff&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
				return true;
			}
		}
		function assetORcollatLoan()
		{
			var assetCollateralType=document.getElementById('assetCollateralType').value;

			if(assetCollateralType=='ASSET')
			{
				document.getElementById("assetCollateral").value=''	
				document.getElementById("assetCollateral").removeAttribute("disabled", "true");
				// document.getElementById('assetClass').removeAttribute("disabled",
				// "true");
				// document.getElementById('collateralClass').setAttribute("disabled",
				// "true");
				// document.getElementById('collateralClass').value='';
				
			 
			}
			else if(assetCollateralType=='COLLATERAL')
			{
				document.getElementById("assetCollateral").value=''	
		        document.getElementById("assetCollateral").removeAttribute("disabled", "true");
			  // document.getElementById('collateralClass').removeAttribute("disabled",
				// "true");
			  // document.getElementById('assetClass').setAttribute("disabled",
				// "true");
		      // document.getElementById('assetClass').value='';
			
			 
			}
		}
		

		function getCollateral(type)
		{
			
				var loanId=document.getElementById("loanId").value;
				
				var contextPath =document.getElementById('contextPath').value ;						
				var url=contextPath+"/existAssetsInCMBehindAction.do?method=openExistingAsset&loanId="+loanId;
			    window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
				   
			
			
		}
		function upperMe(compId) { 
		    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
		    return true;
		}		
		function onsearchCM()
		{
			// alert(DisButClass.prototype.MyProperty);
			DisButClass.prototype.DisButMethod();
			var loanNo=document.getElementById("loanNo").value;
			var dealNo=document.getElementById("dealNo").value;
			var customerName=document.getElementById("customerName").value;
			var panNo=document.getElementById("panNo").value;
			var appFormNo=document.getElementById("appFormNo").value;
			var mbNumber=document.getElementById("mbNumber").value;
			var drivingLic=document.getElementById("drivingLic").value;
			var voterId=document.getElementById("voterId").value;
			var vehicleNo=document.getElementById("vehicleNo").value;
			
			if (loanNo=="" && dealNo=="" && customerName=="" && panNo=="" && appFormNo=="" && mbNumber=="" && drivingLic=="" && voterId=="" && vehicleNo=="")
			{	alert("Please fill at least on field ");
				document.getElementById("search").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			else
			{
				var basePath=document.getElementById("contextPath").value;
			    document.getElementById("searchForCMForm").action=basePath+"/searchCMBehindAction.do?method=getSearchData";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("searchForCMForm").submit();		 
			    return true;
		    }
		}
		function enableButton()
		{	
			document.getElementById("deal_viewer").removeAttribute("disabled","true");
			var id=document.getElementsByName("radioId");
			var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
			var loanIDMain ='';			 
			for(var i=0; i< id.length ; i++)
			{				   
				   if(id[i].checked == true){					   
					   loanIDMain = loanid[i].value;					   	   
				   }				   
			}			
			if(loanIDMain!="")
			{	
				document.getElementById("loan_viewer").removeAttribute("disabled","true");
				document.getElementById("pdc_status").removeAttribute("disabled","true");
				document.getElementById("cheque_status").removeAttribute("disabled","true");
				document.getElementById("repayment_schedule").removeAttribute("disabled","true");
				document.getElementById("statement_of_ac").removeAttribute("disabled","true");
				document.getElementById("early_closer_detail").removeAttribute("disabled","true");
				document.getElementById("old_repayment_schedule").removeAttribute("disabled","true");
				document.getElementById("allocation_detail").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
			}
			else
			{
				document.getElementById("loan_viewer").setAttribute("disabled","disabled");
				document.getElementById("pdc_status").setAttribute("disabled","disabled");
				document.getElementById("cheque_status").setAttribute("disabled","disabled");
				document.getElementById("repayment_schedule").setAttribute("disabled","disabled");
				document.getElementById("statement_of_ac").setAttribute("disabled","disabled");
				document.getElementById("early_closer_detail").setAttribute("disabled","disabled");
				document.getElementById("old_repayment_schedule").setAttribute("disabled");
				document.getElementById("allocation_detail").setAttribute("disabled","disabled");
				DisButClass.prototype.DisButMethod();
			}
			
			
		}
		
		function loanViewerPresentation()
		{
				otherWindows = new Array();
			   curWin = 0;
			   var id=document.getElementsByName("radioId");
			   var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
			   var loanIDMain ='';			 
			   var contextPath=document.getElementById("contextPath").value;			   
			   for(var i=0; i< id.length ; i++)
			   {				   
				   if(id[i].checked == true){					   
					   loanIDMain = loanid[i].value;					   	   
				   }				   
			   }			
			   var url = contextPath+"/searchLoanDealBehindAction.do?loanId="+loanIDMain;
			   mywindow =window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes').focus();
			   otherWindows[curWin++]=window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes');
			   mywindow.moveTo(800,300);
			   if (window.focus) 
			   {
					mywindow.focus();
					return false;
			   }
			   return true;		   	
		}
		function statementOfAccount()
		{
			var id=document.getElementsByName("radioId");
			var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
			var loanIDMain ='';	
			var hiddenRecStatus=document.getElementsByName("hiddenRecStatus");			  
			var recStatus ='';	
			var sourcepath=document.getElementById("contextPath").value;			   
			for(var i=0; i< id.length ; i++) 
			{				   
			   if(id[i].checked == true)
			   {					   
				   loanIDMain = loanid[i].value;
				   recStatus = hiddenRecStatus[i].value;				   
			   }				   
			}
			//alert("hiddenRecStatus  "+hiddenRecStatus);
			//alert("recStatus  "+recStatus);
			if(document.getElementById("rvFlag").value=="YES")
			{
				var warning=confirm("Not allowed to generate SOA, please contact Central OPS Team. Do you want to continue?");
				if(!warning)
				{
					return false;
				}
			}
			if(recStatus=='Cancelled')
			{
				alert("SOA can not be generate for cancelled loan.");
			}
			else
			{
				var defaultFormate=document.getElementById("defaultFormate").value;				
				if(defaultFormate=='H')
				{
					var url=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanIDMain+"&source=NR&reportFormate=H";
					popupWin=window.open(url,'SOA Report','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
					if (window.focus) 
					   popupWin.focus();	
					return true;					 
				}
				else
				{
					document.getElementById("searchForCMForm").action=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanIDMain+"&source=NR&reportFormate=P";
					document.getElementById("searchForCMForm").submit();
				}
			}
		}
		/*shivesh*/
		function closedRepayment()
		{
			var id=document.getElementsByName("radioId");
			var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
			var loanIDMain ='';	
			var hiddenRecStatus=document.getElementsByName("hiddenRecStatus");			  
			var recStatus ='';	
			var sourcepath=document.getElementById("contextPath").value;			   
			for(var i=0; i< id.length ; i++) 
			{				   
			   if(id[i].checked == true)
			   {					   
				   loanIDMain = loanid[i].value;
				   recStatus = hiddenRecStatus[i].value;				   
			   }				   
			}	
			
			if(recStatus!='Closed')
			{
				alert("Repayment for Closed cases can not be generate for active loan.");
			}
			else
				{
				var defaultFormate=document.getElementById("defaultFormate").value;				
				if(defaultFormate=='H')
				{
					var url=sourcepath+"/searchCMBehindAction.do?method=generateCloserepayment&loanId="+loanIDMain+"&source=NR&reportFormate=H";
					popupWin=window.open(url,'Close Repayment','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
					if (window.focus) 
					   popupWin.focus();	
					return true;					 
				}
				else
				{
					document.getElementById("searchForCMForm").action=sourcepath+"/searchCMBehindAction.do?method=generateCloserepayment&loanId="+loanIDMain+"&source=NR&reportFormate=P";
					document.getElementById("searchForCMForm").submit();
				}
				}
		}
		/*shivesh*/
		
		function pdcStatusForLoanViewer()
		{		
			   var id=document.getElementsByName("radioId");
			   var loanid=document.getElementsByName("lbxLoanNoHIDmain");
			   var loanNo=document.getElementsByName("loanNumbermain");
			   var cuName=document.getElementsByName("customerNamemain");
			   var loanIDMain ='';
			   var loan ='';
			   var name ='';			   
			   var contextPath=document.getElementById("contextPath").value;			   
			   for(var i=0; i< id.length ; i++)
			   {				   
				   if(id[i].checked == true){					   
					   loanIDMain = loanid[i].value;
					   loan=loanNo[i].value;
					   name=cuName[i].value;				  				   	   
				   }				   
			   }			
			   var url= contextPath+"/searchCMBehindAction.do?method=pdcStatusPresentation&loanID="+loanIDMain+"&loanNumberCM="+loan+"&customerNameCM="+name;
			   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			   otherWindows = new Array();
			   curWin = 0;
			   otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
			   mywindow.moveTo(800,300);
			   if (window.focus) 
			   {
					mywindow.focus();
					return false;
			   }
			   return true;
		}
		
		function editDetailCM(a,b)
		{
			var basePath=document.getElementById("contextPath").value;
			document.getElementById("paymentMakerSearch").action=basePath+"/paymentCMProcessAction.do?method=openEditpaymentMaker&loanId="+a+"&instrumentID="+b;
			document.getElementById("paymentMakerSearch").submit();
			
		}
		function getColletralDetail()
		{
			 var basePath=document.getElementById("contextPath").value;
			 var loanId=document.getElementById('loanId').value;
							
			document.getElementById("ExistAssetForm").action= basePath+"/existAssetsInCMBehindAction.do?method=colletralDetail&loanId="+loanId;
			document.getElementById("ExistAssetForm").submit();
		}

		
		function saveAssetCollateralInCM()
		{
			 // alert("In saveAssetCollateral");
				if(checkboxCheck(document.getElementsByName("chk")))
				{
					
					 var contextPath =document.getElementById('contextPath').value ;
					 document.getElementById("ExistAssetForm").action = contextPath+"/existAssetsActionInCM.do";
					 document.getElementById("ExistAssetForm").submit();
			   
				}
				else
				{
					alert("Please Select AtLeast one Record");
					document.getElementById("button").removeAttribute("disabled","true");
				}
		}
		
		function openNewPartPrePayment()
		{
			DisButClass.prototype.DisButMethod();
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("partPrePaymentSearchForm").action = contextPath+"/partPrePaymentSearch.do?method=openNewPartPrepayment";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("partPrePaymentSearchForm").submit();
		}
		
		function savePartPrePayment()
		{
			DisButClass.prototype.DisButMethod();
			var cal = document.getElementById("partPrePaymentCal").value;
			var installmentType=document.getElementById("installmentType").value;
			if(validatePartPrePaymentMakerDynaValidatorForm(document.getElementById("partPrepaymentMakerForm")))
			{
				
				
			/*	if(cal >0 )
				{
					if( !confirm("Receivable/s are pending for this loan. Do you want to continue?"))	 
				    {
				       	DisButClass.prototype.EnbButMethod();
				    	return false;
				    }
					//alert("Receivable/s are pending for this loan. Do you want to continue?");
				}*/
				var outstandingLoanAmount = removeComma(document.getElementById("outstandingLoanAmount").value);
				var partPaymentCharges = removeComma(document.getElementById("reschCharges").value);
				var partPaymentAmount = removeComma(document.getElementById("partPaymentAmount").value);
				var aamount = removeComma(document.getElementById("amount").value);
				//alert("okkkkk"+aamount);
				//alert("okkkkk"+partPaymentAmount);
				if(partPaymentAmount<=0)
				{
					alert("Part Payment Amount must be greater than zero");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				if(partPaymentAmount> aamount)
				{
					alert("Part Payment Amount should not be greater than payable amount");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				
				if(partPaymentAmount>=outstandingLoanAmount && installmentType=='I')
				{
					alert("Part Payment Amount cannot be greater than or equal to Outstanding Loan Amount");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				if(partPaymentAmount>outstandingLoanAmount && installmentType=='N')
				{
					alert("Part Payment Amount cannot be greater than Outstanding Loan Amount");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				if(partPaymentCharges<0)
				{
					alert("Part Payment Charges cannot be less than zero");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("partPrepaymentMakerForm").action = contextPath+"/partPrePaymentMaker.do?method=savePartPrePaymentMaker";
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("partPrepaymentMakerForm").submit();
				    return true;
				}
			}
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		function searchPartPrePayment(type)
		{
			// alert(type);
			DisButClass.prototype.DisButMethod();
			var loanNo = document.getElementById("loanNo").value;
			var custName = document.getElementById("customerName").value;
			var reportingToUserId = document.getElementById("reportingToUserId").value;
			if(loanNo=='' && custName==''&& reportingToUserId=='')
			{
				alert("Please Enter One value to Search.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("search").removeAttribute("disabled","true");
			}
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("partPrePaymentSearchForm").action = contextPath+"/partPrePaymentSearch.do?method=searchPartPrePayment&type="+type;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("partPrePaymentSearchForm").submit();
			    document.getElementById("search").removeAttribute("disabled","true");
			}
				
		}
		
		function updatePartPrePaymentData(type,fwdMsg)
		{
			DisButClass.prototype.DisButMethod();
			if(type=='F')
				{
					if( !confirm(fwdMsg))	 
				    {
				       	DisButClass.prototype.EnbButMethod();
				    	return false;
				    }
				}
			
			var cal = document.getElementById("partPrePaymentCal").value;
			var realize=document.getElementById("realize").value;
			var realizeFlag=document.getElementById("realizeFlag").value;
			if(validatePartPrePaymentMakerDynaValidatorForm(document.getElementById("partPrepaymentMakerForm")))
			{
				
				/*if(cal >0 )
				{
					if( !confirm("Receivable/s are pending for this loan. Do you want to continue?"))	 
				    {
				       	DisButClass.prototype.EnbButMethod();
				    	return false;
				    }
				}*/
				if(realize >0 && realizeFlag=='Y')
		    	{
	    			alert("Some Receipt in Process for Realize.");
	    			DisButClass.prototype.EnbButMethod();
	    			document.getElementById("saveFwd").removeAttribute("disabled","true");
	    			return false;
		    			
		    	}
				var outstandingLoanAmount = removeComma(document.getElementById("outstandingLoanAmount").value);
				var partPaymentCharges = removeComma(document.getElementById("reschCharges").value);
				var partPaymentAmount = removeComma(document.getElementById("partPaymentAmount").value);
				var aamount = removeComma(document.getElementById("amount").value);
				
				//alert("all is well");
				if(partPaymentAmount<=0)
				{
					alert("Part Payment Amount must be greater than zero");
					if(type=='P')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
					}
					else if(type=='F')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					}
					return false;
				}
				if (type=='F'){
				var businessDate= document.getElementById("businessdate").value;
				var partPaymentDate= document.getElementById("partPaymentDate").value;
				var maxInstallmentDate= document.getElementById("maxInstallmentDate").value;
				var formatD=document.getElementById("formatD").value;
				var partPaymentDateObj=getDateObject(partPaymentDate,formatD.substring(2, 3));
				var businessDateObj=getDateObject(businessDate,formatD.substring(2, 3));
				var maxInstallmentDateObj=getDateObject(maxInstallmentDate,formatD.substring(2, 3));
				if(partPaymentDateObj<maxInstallmentDateObj){
					alert("Part Payment Date should not be less then last billing Date");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				
				if(businessDateObj<partPaymentDateObj){
					alert("Part Payment Date should not be greater then bussiness Date");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
					return false;
				}
				}
				
				if(partPaymentAmount> aamount)
				{
					alert("Part Payment Amount should not be greater than payable amount");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}

				
				if(partPaymentAmount> outstandingLoanAmount)
				{
					alert("Part Payment Amount cannot be greater than Outstanding Loan Amount");
					if(type=='P')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
					}
					else if(type=='F')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					}
					return false;
				}
				if(partPaymentCharges<0)
				{
					alert("Part Payment Charges cannot be less than zero");
					if(type=='P')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
					}
					else if(type=='F')
					{
						DisButClass.prototype.EnbButMethod();
						document.getElementById("saveFwd").removeAttribute("disabled","true");
					}
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("partPrepaymentMakerForm").action = contextPath+"/partPrePaymentMaker.do?method=updatePartPrePaymentMaker&type="+type;
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("partPrepaymentMakerForm").submit();
				    return true;
				}
			}
			else
			{
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}
				else if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
			}
		}
		
		function savePartPrePaymentAuthor()
		{
			DisButClass.prototype.DisButMethod();
			var decision=document.getElementById("decision").value;
			if(decision=='A')
			{
				var businessDate=document.getElementById("businessDate").value;
				var makerDate=document.getElementById("makerDate").value;	
				var fromdate=businessDate;
				var todate=makerDate;
				var day1= fromdate.substring(0,2);
				var day2= todate.substring(0,2);
				var month1=fromdate.substring(3,5);
				var month2=todate.substring(3,5);
				var year1=fromdate.substring(6);
				var year2=todate.substring(6);
				if(parseFloat(year1) != parseFloat(year2))
				{	
					alert("Maker Date should be equal to Business Date.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					document.getElementById("comments").focus();
					return false;
				}
				else
				{
					 if(parseFloat(year1)==parseFloat(year2))
					 {
						if(parseFloat(month1) != parseFloat(month2))
						{	
							alert("Maker Date should be equal to Business Date.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("comments").focus();
			    			return false;
						}
						else 
						{
							if(parseFloat(month1)==parseFloat(month2))
							{
								if(parseFloat(day1) != parseFloat(day2))
								{	
									alert("Maker Date should be equal to Business Date.");
									DisButClass.prototype.EnbButMethod();
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("comments").focus();
					    			return false;
								}
							}
						}
					}
				}
			}
			if(document.getElementById("comments").value=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant

			{
				alert("Comments is Required");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("comments").focus();
				return false;
			}

	
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("partPrePaymentAuthorForm").action = contextPath+"/partPrePaymentAuthor.do?method=savePartPrePaymentAuthor";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("partPrePaymentAuthorForm").submit();
			    return true;
			}
		}
		
		function changeInstlDatePartPayment()
		{
			 var partPaymentParameter=document.getElementById("partPaymentParameter").value;
			 if(partPaymentParameter=='T')
			 {
				 document.getElementById('partPaymentDate').value=document.getElementById('bDate').value;
			 }
			 if(partPaymentParameter=='I')
			 {
				 document.getElementById('partPaymentDate').value=document.getElementById('nextDueDate').value;
			 }
		}
		
		function newRepaymentSchedulePartPayment()
		{
			if (document.getElementById("lbxLoanNoHID").value=="")
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				if(document.getElementById("disbStatus").value=='P')
					{
					alert("this is partially disbursed case");
					return false;
					}
				var loanId=document.getElementById('lbxLoanNoHID').value;
				var reschId=document.getElementById('reschId').value;
				var instlNo=document.getElementById('lbxInstlNo').value;
				// alert("loanId: "+loanId+"instlNo: "+instlNo+"reschId:
				// "+reschId);
				// alert("reschId: "+reschId);
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url= contextPath+"/newRepaymentScheduleDeferral.do?method=newRepaymentSchedulePartPayment&loanId="+loanId+"&reschId="+reschId+"&instlNo="+instlNo;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function generateReschCharges()
		{
			// alert("hi");
			 var loanId=document.getElementById("lbxLoanNoHID").value;
			 var partPaymentAmt=removeComma(document.getElementById("partPaymentAmount").value);
			 var reschDate=document.getElementById("partPaymentDate").value;
			 if(reschDate!='')
			 {
				 var contextPath=document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=generateReschCharges";
				 var data = "lbxLoanNoHID="+loanId+"&partPaymentAmt="+partPaymentAmt+"&reschDate="+reschDate;
				 sendGenerateReschChargesId(address, data);
				 return true;
			 }
			 else
		    {
			   	alert("Please Enter Part Payment Date");
			   	document.getElementById("partPaymentAmount").value="";
			   	document.getElementById("partPaymentDateButton").focus();
			   	return false;
		    }
			 
		}
		
		function generateDeferralReschCharges()
		{
			// alert("hi");
			 var loanId=document.getElementById("lbxLoanNoHID").value;
			 // alert("loanId: "+loanId);
			 var reschDate=document.getElementById("reschDate").value;
			 // alert("reschDate: "+reschDate);
			 var deferralFromInstallment = document.getElementById("deferralFromInstallment").value;
			// alert("deferralFromInstallment: "+deferralFromInstallment);
			 var partPaymentAmt=0;
			// alert("partPaymentAmt: "+partPaymentAmt);
			 if(deferralFromInstallment!='')
			 {
				 var contextPath=document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=generateReschCharges";
				 var data = "lbxLoanNoHID="+loanId+"&partPaymentAmt="+partPaymentAmt+"&reschDate="+reschDate;
				 sendGenerateReschChargesId(address, data);
				 return true;
			 }
			 else
		    {
			   	alert("Please select Deferral From Installment");
			   	document.getElementById("partPaymentDateButton").focus();
			   	return false;
		    }
			 
		}
		
		function generateRepricingReschCharges()
		{
			// alert("hi");
			 var loanId=document.getElementById("lbxLoanNoHID").value;
			 // alert("loanId: "+loanId);
			 var reschDate=document.getElementById("reschDate").value;
			 // alert("reschDate: "+reschDate);
			 var rePricingFromInstallment = document.getElementById("rePricingFromInstallment").value;
			// alert("deferralFromInstallment: "+deferralFromInstallment);
			 var partPaymentAmt=0;
			// alert("partPaymentAmt: "+partPaymentAmt);
			 if(rePricingFromInstallment!='')
			 {
				 var contextPath=document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=generateReschCharges";
				 var data = "lbxLoanNoHID="+loanId+"&partPaymentAmt="+partPaymentAmt+"&reschDate="+reschDate;
				 sendGenerateReschChargesId(address, data);
				 return true;
			 }
			 else
		    {
			   	alert("Please select Repricing From Installment");
			   	document.getElementById("partPaymentDateButton").focus();
			   	return false;
		    }
			 
		}

		function sendGenerateReschChargesId(address, data) {
			// alert("in sendPartPaymentid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultGenerateReschCharges(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultGenerateReschCharges(request){
			
			// alert("in resultPartpayment id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				// alert("String:--->"+str);
				var s1 = str.split("$:");
				document.getElementById("reschCharges").value=trim(s1[0]);  
				document.getElementById("interestForGapPeriod").value=trim(s1[1]);
			}
		}
		
		function openNewdeferral()
		{
			// alert("openNewDeferral");
			DisButClass.prototype.DisButMethod();
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("deferralSearchForm").action = contextPath+"/deferralSearch.do?method=openNewDeferral";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("deferralSearchForm").submit();
		}
		
		function saveDeferral()
		{
			// alert("save");
			DisButClass.prototype.DisButMethod();
			if(validateDeferralMakerDynaValidatorForm(document.getElementById("deferralMakerForm")))
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("deferralMakerForm").action = contextPath+"/deferralMaker.do?method=saveDeferralMaker";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("deferralMakerForm").submit();
			    return true;
			}
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		function searchDeferral(type)
		{
			// alert(type);
			DisButClass.prototype.DisButMethod();
			var loanNo = document.getElementById("loanNo").value;
			var custName = document.getElementById("customerName").value;
			var reportingToUserId = document.getElementById("reportingToUserId").value;
			if(loanNo=='' && custName=='' && reportingToUserId=='')
			{
				alert("Please Enter One value to Search.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("search").removeAttribute("disabled","true");
			}
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("deferralSearchForm").action = contextPath+"/deferralSearch.do?method=searchDeferral&type="+type;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("deferralSearchForm").submit();
			    document.getElementById("search").removeAttribute("disabled","true");
			}
				
		}
		
		function updateDeferral(type,fwdMsg)
		{
			// alert("save");
			if(type=='F')
			{
				if(!confirm(fwdMsg))	 
			    {
			       	DisButClass.prototype.EnbButMethod();
			    	return false;
			    }
			}
			DisButClass.prototype.DisButMethod();
			if(validateDeferralMakerDynaValidatorForm(document.getElementById("deferralMakerForm")))
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("deferralMakerForm").action = contextPath+"/deferralMaker.do?method=updateDeferralMaker&type="+type;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("deferralMakerForm").submit();
			    return true;
			}
			else
			{
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}
				if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
			}
		}
		
		function saveDeferralAuthor()
		{
			DisButClass.prototype.DisButMethod();
			var decision=document.getElementById("decision").value;
			if(decision=='A')
			{
				var businessDate=document.getElementById("businessDate").value;
				var makerDate=document.getElementById("makerDate").value;	
				var fromdate=businessDate;
				var todate=makerDate;
				var day1= fromdate.substring(0,2);
				var day2= todate.substring(0,2);
				var month1=fromdate.substring(3,5);
				var month2=todate.substring(3,5);
				var year1=fromdate.substring(6);
				var year2=todate.substring(6);
				if(parseFloat(year1) != parseFloat(year2))
				{	
					alert("Maker Date should be equal to Business Date.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					document.getElementById("comments").focus();
					return false;
				}
				else
				{
					 if(parseFloat(year1)==parseFloat(year2))
					 {
						if(parseFloat(month1) != parseFloat(month2))
						{	
							alert("Maker Date should be equal to Business Date.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("comments").focus();
			    			return false;
						}
						else 
						{
							if(parseFloat(month1)==parseFloat(month2))
							{
								if(parseFloat(day1) != parseFloat(day2))
								{	
									alert("Maker Date should be equal to Business Date.");
									DisButClass.prototype.EnbButMethod();
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("comments").focus();
					    			return false;
								}
							}
						}
					}
				}
			}
			if(document.getElementById("comments").value=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant
 
			{
				alert("Comments is Required");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("comments").focus();
				return false;
			}
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("deferralAuthorForm").action = contextPath+"/deferralAuthor.do?method=saveDeferralAuthor";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("deferralAuthorForm").submit();
			    return true;
			}
		}
		
		function newRepaymentScheduleDeferral()
		{
			if (document.getElementById("lbxLoanNoHID").value=="")
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var loanId=document.getElementById('lbxLoanNoHID').value;
				var reschId=document.getElementById('reschId').value;
				// alert("reschId: "+reschId);
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url= contextPath+"/newRepaymentScheduleDeferral.do?method=newRepaymentScheduleDeferral&loanId="+loanId+"&reschId="+reschId;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function openNewRePricing()
		{
			// alert("openNewRePricing");
			DisButClass.prototype.DisButMethod();
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("rePricingSearchForm").action = contextPath+"/rePricingSearch.do?method=openNewRePricing";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("rePricingSearchForm").submit();
		}
		
		function changeInterestRateMethod()
		{
			var intRateNethod = document.getElementById("interestRateMethod").value;
			var intRateType = document.getElementById("interestRateType").value;
			
			if(intRateNethod=='L')
			{
				document.getElementById("interestRateType").value='E';
				repricingInterestRateTypeChange();
			}
				
		}
		
		function changeRepricingCondition()
		{
			var rePricingCondition = document.getElementById("rePricingCondition").value;
			if(rePricingCondition=='T')
			{
				document.getElementById("installmentFrequency").setAttribute("disabled",true);
				document.getElementById("installmentType").setAttribute("disabled",true);
				document.getElementById("tenure").setAttribute("readonly",true);
			}
			if(rePricingCondition=='E')
			{
				document.getElementById("installmentFrequency").removeAttribute("disabled",true);
				document.getElementById("installmentType").removeAttribute("disabled",true);
				document.getElementById("tenure").removeAttribute("readOnly");
			}
		}
		
		function getBaseRate()
		{
			// alert("In getBaseRate");
			var baseRateType =document.getElementById('baseRateType').value ;
			var contextPath =document.getElementById('contextPath').value ;
			// alert("contextPath"+contextPath);
		     if (baseRateType!= '') 
		     {
				var address = contextPath+"/relationalManagerAjaxAction.do?method=getBaseRateDetail";
				var data = "baseRateType="+baseRateType;
				// alert("address: "+address+"data: "+data);
				send_Rate(address,data);
				// alert("ok");
				return true;
			}
		    else
		    {
		    	 alert("Base Rate Type is Required");	
		    	 return false;
		    }
		}

		function send_Rate(address,data) 
		{
			// alert("send_BaseRate"+address);
			var request = getRequestObject();
			request.onreadystatechange = function () {
				result_Rate(request);
			};
			
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}
		function result_Rate(request)
		{
			if ((request.readyState == 4) && (request.status == 200))
			{
				var str = request.responseText;				    
				document.getElementById('baseRate').value = trim(str);
				var markup = document.getElementById("markup").value;
				// alert("markup :"+markup);
				var baseRate = document.getElementById("baseRate").value;
				// alert("baseRate :"+baseRate);
				if(markup=="")
				{
					markup=0;
				}
				if(baseRate=="")
				{
					baseRate=0;
				}
				var finalRate=removeComma(markup)+removeComma(baseRate);
				// alert("finalRate: "+finalRate);
				document.getElementById("finalRate").value=finalRate.toFixed(7);
			}
		}
		
		function calculateFinalRate()
		{
			var markup = document.getElementById("markup").value;
			var finalRate = document.getElementById("finalRate").value;
			var baseRate = document.getElementById("baseRate").value;
// alert("In calculateFinalRate markUp: "+markup+"baseRate: "+baseRate);
			if(finalRate=="")
			{
				markup=0;
			}
			if(baseRate=="")
			{
				baseRate=0;
			}
			
		
			markup=parseFloat(finalRate)-parseFloat(baseRate);
			//alert("final rate is :"+markup);
			document.getElementById("markup").value=markup.toFixed(7);	
		}
		
		function repricingInterestRateTypeChange()
		{
			var rateType = document.getElementById("interestRateType").value;
			var intRateNethod = document.getElementById("interestRateMethod").value;
			
// alert("rateType: "+rateType);
// alert("intRateNethod: "+intRateNethod);
			if(rateType!=null && rateType!='')
			{
				if(rateType=='E')
				{
					document.getElementById("baseRateType").removeAttribute("disabled",true);
				}
//				if(intRateNethod=='L' && rateType=='F')
//				{
//					alert("Combination of Interest Rate Type & Interest Rate Method is Wrong");
//					document.getElementById("interestRateType").value='E';
//					return false;
//				}
				if(intRateNethod=='F' && rateType=='F')
				{
					document.getElementById("baseRateType").value='';
					document.getElementById("markup").value='';
					document.getElementById("finalRate").value='';
					document.getElementById("baseRateType").setAttribute("disabled",true);
					document.getElementById("baseRate").value='0.0';
				}
			}
		}
		
		function saveRepricing()
		{
			// alert("save");
			DisButClass.prototype.DisButMethod();
			
			var rateType1 = document.getElementById("interestRateType").value;
			var intRateNethod1 = document.getElementById("interestRateMethod").value;
			var rePricingDate='';
			if(document.getElementById("repricingDate").value==""){
				alert("Please capture repricing date.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
				
			}
			else{
				 rePricingDate=document.getElementById("repricingDate").value;
				
			}
			
			
			
		
		/*	if((intRateNethod1=='L' && rateType1=='F')||(intRateNethod1=='F' && rateType1=='E'))
							{
								alert("Combination of Interest Rate Type & Interest Rate Method is Wrong");
								//document.getElementById("interestRateType").value='E';
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
		}*/

			if(validateRePricingMakerDynaValidatorForm(document.getElementById("rePricingMakerForm")))
			{
				
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("rePricingMakerForm").action = contextPath+"/rePricingMaker.do?method=saveRepricingMaker&rePricingDate="+rePricingDate;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("rePricingMakerForm").submit();
			    return true;
	
			}
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			}
		
		function searchRePricing(type)
		{
			// alert(type);
			DisButClass.prototype.DisButMethod();
			var loanNo = document.getElementById("loanNo").value;
			var custName = document.getElementById("customerName").value;
			var reportingToUserId = document.getElementById("reportingToUserId").value;
			if(loanNo=='' && custName==''&& reportingToUserId=='')
			{
				alert("Please Enter One value to Search.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("search").removeAttribute("disabled","true");
			}
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("rePricingSearchForm").action = contextPath+"/rePricingSearch.do?method=searchRepricing&type="+type;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("rePricingSearchForm").submit();
			    document.getElementById("search").removeAttribute("disabled","true");
			}	
		}
		
		function updateRepricing(type,fwdMsg)
		{
			// alert("save");
			DisButClass.prototype.DisButMethod();
			if(type=='F')
			{
				if(!confirm(fwdMsg))	 
			    {
			       	DisButClass.prototype.EnbButMethod();
			    	return false;
			    }
			}
			var rateType1 = document.getElementById("interestRateType").value;
			var intRateNethod1 = document.getElementById("interestRateMethod").value;
			var instmntSaveFlag = document.getElementById("instmntSaveFlag").value;
			var rePricingCondition = document.getElementById("rePricingCondition").value;
			
			var rePricingDate="";
			if(document.getElementById("repricingDate").value==""){
				alert("Please capture repricing date.");
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}					
				if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
				
			}
			else{
				 rePricingDate=document.getElementById("repricingDate").value;
				
			}
			
			
			var businessDate= document.getElementById("businessdate").value;
			var repricingDate= document.getElementById("repricingDate").value;
			var maxInstallmentDate= document.getElementById("maxInstallmentDate").value;
			var formatD=document.getElementById("formatD").value;
			var repricingDateObj=getDateObject(repricingDate,formatD.substring(2, 3));
			var businessDateObj=getDateObject(businessDate,formatD.substring(2, 3));
			var maxInstallmentDateObj=getDateObject(maxInstallmentDate,formatD.substring(2, 3));
			if(repricingDateObj<=maxInstallmentDateObj){
				alert("Repricing Date should not be less then last billing Date");
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}					
				if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
			}
			
			if(businessDateObj<repricingDateObj){
				alert("Repricing Date should not be greater then bussiness Date");
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}					
				if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
			}
			
		/*
			if(partPaymentAmount> aamount)
			{
				alert("Part Payment Amount should not be greater than payable amount");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			
			*/
			if(instmntSaveFlag !='Y' && type=='F' && rePricingCondition!='T')
			{
				alert("First Save the New Installment Plan.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("saveFwd").removeAttribute("disabled","true");
				return false;
			}
			
			/*if((intRateNethod1=='L' && rateType1=='F')||(intRateNethod1=='F' && rateType1=='E'))
							{
								alert("Combination of Interest Rate Type & Interest Rate Method is Wrong");
								//document.getElementById("interestRateType").value='E';
								
								if(type=='P'){
									DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								}
								if(type=='F'){
									DisButClass.prototype.EnbButMethod();
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								}
								return false;
		}*/
			if(validateRePricingMakerDynaValidatorForm(document.getElementById("rePricingMakerForm")))
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("rePricingMakerForm").action = contextPath+"/rePricingMaker.do?method=updateRepricingMaker&type="+type+"&rePricingDate="+rePricingDate;
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("rePricingMakerForm").submit();
			    return true;
			}
			else
			{
				if(type=='P')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
				}					
				if(type=='F')
				{
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveFwd").removeAttribute("disabled","true");
				}
				return false;
			}
		}

		function saveRepricingAuthor()
		{
			DisButClass.prototype.DisButMethod();
			var decision=document.getElementById("decision").value;
			if(decision=='A')
			{
				var businessDate=document.getElementById("businessDate").value;
				var makerDate=document.getElementById("makerDate").value;	
				var fromdate=businessDate;
				var todate=makerDate;
				var day1= fromdate.substring(0,2);
				var day2= todate.substring(0,2);
				var month1=fromdate.substring(3,5);
				var month2=todate.substring(3,5);
				var year1=fromdate.substring(6);
				var year2=todate.substring(6);
				if(parseFloat(year1) != parseFloat(year2))
				{	
					alert("Maker Date should be equal to Business Date.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					document.getElementById("comments").focus();
					return false;
				}
				else
				{
					 if(parseFloat(year1)==parseFloat(year2))
					 {
						if(parseFloat(month1) != parseFloat(month2))
						{	
							alert("Maker Date should be equal to Business Date.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("comments").focus();
			    			return false;
						}
						else 
						{
							if(parseFloat(month1)==parseFloat(month2))
							{
								if(parseFloat(day1) != parseFloat(day2))
								{	
									alert("Maker Date should be equal to Business Date.");
									DisButClass.prototype.EnbButMethod();
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("comments").focus();
					    			return false;
								}
							}
						}
					}
				}
			}
			if(document.getElementById("comments").value=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant
 
			{
				alert("Comments is Required");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("comments").focus();
				return false;
			}
			else
			{
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("rePricingAuthorForm").action = contextPath+"/rePricingAuthor.do?method=saveRepricingAuthor";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("rePricingAuthorForm").submit();
			    return true;
			}
		}
		// neeraj tripathi for delete method start ;
		
		function deleteRecord()
		{
			DisButClass.prototype.DisButMethod();
			if(confirm("Are you sure to delete the record.")) 
		    {
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("rePricingMakerForm").action = contextPath+"/rePricingMaker.do?method=deleteRepricingMaker";
			    DisButClass.prototype.EnbButMethod();
			    document.getElementById("rePricingMakerForm").submit();
			    return true;
		    }
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("delete").removeAttribute("disabled","true");
				return false;
			}
			
		}
		function deleteDeferralDetails()
		{
			DisButClass.prototype.DisButMethod();
			if(confirm("Are you sure to delete the record.")) 
		    {
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("deferralMakerForm").action = contextPath+"/deferralMaker.do?method=deleteDeferralData";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("deferralMakerForm").submit();
			    return true;
		    }
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("delete").removeAttribute("disabled","true");
				return false;
			}
			
		}
		function deletePartPaymentDetails()
		{
			DisButClass.prototype.DisButMethod();
			if(confirm("Are you sure to delete the record.")) 
		    {
				var contextPath=document.getElementById("contextPath").value;
			    document.getElementById("partPrepaymentMakerForm").action = contextPath+"/partPrePaymentMaker.do?method=deletePartPrePaymentMaker";
			    document.getElementById("processingImage").style.display = '';
			    document.getElementById("partPrepaymentMakerForm").submit();
			    return true;
		    }
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("delete").removeAttribute("disabled","true");
				return false;
			}
			
		}
		// neeraj tripathi for delete method end ;
		
		function newRepaymentScheduleRepricing()
		{
			if (document.getElementById("lbxLoanNoHID").value=="")
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var loanId=document.getElementById('lbxLoanNoHID').value;
				var reschId=document.getElementById('reschId').value;
				var rePricingCondition=document.getElementById('rePricingCondition').value;
				// alert("reschId: "+reschId+" Parameter "+rePricingCondition);
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url= contextPath+"/newRepaymentScheduleDeferral.do?method=newRepaymentScheduleRepricing&loanId="+loanId+"&reschId="+reschId+"&rePricingCondition="+rePricingCondition;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function viewInstallmentPlanRepricing()
		{
			// alert("Inside viewInstallmentPlanRepricing");
			var loanId = document.getElementById("lbxLoanNoHID").value;
			if (loanId=='')
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url= contextPath+"/installmentPlanViewRepricing.do?method=viewOldInstallmentPlanRepricing&loanId="+loanId;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function viewInstallmentPlanPartPayment()
		{
			// alert("Inside viewInstallmentPlanPartPayment");
			var loanId = document.getElementById("lbxLoanNoHID").value;
			if (loanId=='')
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url= contextPath+"/installmentPlanViewRepricing.do?method=viewOldInstallmentPlanPartPayment&loanId="+loanId;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function generateInstallmentPlanPartPayment(type)
		{
			// alert("Inside generateInstallmentPlanPartPayment");
			var loanId = document.getElementById("lbxLoanNoHID").value;
			var reschId = document.getElementById("reschId").value;
			var installNo=document.getElementById("lbxInstlNo").value;
			// alert(installNo);
			if (loanId=='')
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url="";
				if(type=='P')
					url= contextPath+"/installmentPlanViewRepricing.do?method=viewNewInstallmentPlanPartPayment&loanId="+loanId+"&reschId="+reschId+"&installNo="+installNo;
				if(type=='F')
					url= contextPath+"/installmentPlanViewRepricing.do?method=viewNewInstallmentPlanPartPaymentAuthor&loanId="+loanId+"&reschId="+reschId;
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function generateInstallmentPlanRepricing(type)
		{
			// alert("Inside generateInstallmentPlanRepricing");
			var loanId = document.getElementById("lbxLoanNoHID").value;
			var reschId = document.getElementById("reschId").value;
			if (loanId=='')
			{
				alert("Please Select Loan First");	
				return false;
			}
			else
			{
				var contextPath = document.getElementById("contextPath").value;
				// alert(contextPath);
				var url="";
				if(type=='P')
					url= contextPath+"/installmentPlanViewRepricing.do?method=viewNewInstallmentPlanRepricing&loanId="+loanId+"&reschId="+reschId+"&fromWhere=repricing";
				if(type=='F')
					url= contextPath+"/installmentPlanViewRepricing.do?method=viewNewInstallmentPlanRepricingAuthor&loanId="+loanId+"&reschId="+reschId+"&fromWhere=repricing";
				// alert("url: "+url);
				// window.open(url,'View
				// Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center,
				// scrollbars=yes");
				mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				mywindow.moveTo(800,300);
				if (window.focus) {
					mywindow.focus();
					return false;
				}
				return true;
		   }
		}
		
		function saveNewInstallmentPlanRescheduling()
		{
			var contextPath=document.getElementById("contextPath").value;
			var loanId = document.getElementById("loanId").value;
			var reschId = document.getElementById("reschId").value;
			var recoveryType=document.getElementById("recoveryType").value;
			// alert("recoveryType: "+recoveryType);
			var loanAmount=document.getElementById("loanAmount").value;
			// alert("loanAmount: "+loanAmount);
			var installmentType=document.getElementById("installmentType").value;
			// alert("installmentType: "+installmentType);
			var totalInstallment=document.getElementById("totalInstallment").value;
			// alert("totalInstallment: "+totalInstallment);
			var gridTable = document.getElementById('gridTable');
			var tableLength = gridTable.rows.length-1;
			// alert("tableLength: "+tableLength);
			var sum=0;
			var psum=0;
			var isum=0;
			for(var i=1;i<=tableLength;i++)
			{
				var fromInstallment=document.getElementById("fromInstallment"+i).value;
				// alert("fromInstallment: "+fromInstallment);
				var toInstallment=document.getElementById("toInstallment"+i).value;
				// alert("toInstallment: "+toInstallment);
				var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
				// alert("recoveryPercen: "+recoveryPercen);
				var principalAmount=document.getElementById("principalAmount"+i).value;
				// alert("principalAmount: "+principalAmount);
				var installmentAmount=document.getElementById("installmentAmount"+i).value;
				// alert("installmentAmount: "+installmentAmount);
				if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount==""))
				{
					 alert("Please fill all the fields ");
					 document.getElementById("save").removeAttribute("disabled","true");
					 return false;
				}
			    if(fromInstallment=="")
			    {
			    	fromInstallment=0;
				}
			    else
			    	fromInstallment=removeComma(fromInstallment);
			    if(toInstallment=="")
			    {
			    	toInstallment=0;
				}
			    else
			    	toInstallment=removeComma(toInstallment);
			    
			    if(recoveryPercen=="")
			    {
			    	recoveryPercen=0;
				}
			    else    	  
			    	recoveryPercen=removeComma(recoveryPercen);

			    sum= sum + recoveryPercen;
			    // alert("sum: "+sum);
			    
			    if(principalAmount=="")
			    {
			    	principalAmount=0;
				}
			    else	  
			    	principalAmount=removeComma(principalAmount);
			    
			    psum= psum + principalAmount * (toInstallment - fromInstallment + 1);
			    // alert("psum: "+psum);
			    
			    if(installmentAmount=="")
			    {
			    	installmentAmount=0;
				}
			    else    	  
			    	installmentAmount=removeComma(installmentAmount);
			    
			    isum= isum + installmentAmount* (toInstallment - fromInstallment + 1);
			    // alert("isum: "+isum);
			    
			   

			    if(parseInt(document.getElementById("fromInstallment"+i).value) > parseInt(document.getElementById("toInstallment"+i).value))
				{	    	
					alert("From Installment should be less than or equal to To Installment ");
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
			 
				
				if(i<tableLength)
				{	
					var k=i+1;
					
					if(parseInt(document.getElementById("toInstallment"+i).value) > parseInt(document.getElementById("fromInstallment"+k).value))
					{
						alert("Next From Installment should be greater than Previous To Installment");
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
					if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
					{
				
						alert("Next From Installment should be equal to Previous To Installment");
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}

				}
			
			}
			
			var lastToInstall=document.getElementById("toInstallment"+tableLength).value;
			// alert("lastToInstall: "+lastToInstall);
			if(document.getElementById("fromInstallment1").value != '1')
			{
				alert("First Installment should start from 1");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			
			if(parseInt(lastToInstall)> parseInt(totalInstallment)){
				alert("No. of  Installment should be equal to Total Installment "+totalInstallment);
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(parseInt(lastToInstall)< parseInt(totalInstallment)){
				alert("No. of  Installment should be equal to Total Installment "+totalInstallment);
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(sum!=100 && recoveryType=='P')
			{
				alert("Total Recovery Percentage should be  equal to 100 %");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((psum>removeComma(loanAmount) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'))
			{
				alert("Principal Amount should be equal to  Loan Balance Principal Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			
			if((isum<removeComma(loanAmount) && recoveryType=='F'))
			{
				alert("Installment Amount should be equal to or greater than Loan Balance Principal Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			else
			{
				document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanRepricingProcess.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&loanId="+loanId+"&reschId="+reschId;
				document.getElementById("installmentPlanForm").submit();
			    return true;
			}
		}
		
	/*	function saveNewInstallmentPlanRescheduling()
		{
			//alert("vss");
			
			DisButClass.prototype.DisButMethod();
			
			var contextPath=document.getElementById("contextPath").value;
			var recoveryType=document.getElementById("recoveryType").value;
			var loanAmount=document.getElementById("loanAmount").value;
			var formatD=document.getElementById("formatD").value;
			var loanId = document.getElementById("loanId").value;
			var reschId = document.getElementById("reschId").value;
			//add by vijendra
			var frequency=document.getElementById("frequency").value;
			var maxDate=document.getElementById("maxDate").value;
			//end by vijendra
			var repayEffDateObj="";
			var dueDateObj="";
			var insNextDueDateObj="";
			// var fromInstallment=document.getElementById("fromInstallment").value;
			// var toInstallment=document.getElementById("toInstallment").value;
			var installmentType=document.getElementById("installmentType").value;
			var totalInstallment=document.getElementById("totalInstallment").value;
			var repayEffDate=document.getElementById("repayeffdate").value;
			repayEffDateObj=getDateObject(repayEffDate,formatD.substring(2, 3));
			var gridTable = document.getElementById('gridTable');
			var tableLength = gridTable.rows.length-1;
			var editDueDate=document.getElementById("editDueDate").value;
			var insNextDueDate=document.getElementById("insNextDueDate").value;
			insNextDueDateObj=getDateObject(insNextDueDate,formatD.substring(2,3));

			
			  var sum=0;
			  var psum=0;
			  var isum=0;

			  // Vishal changes start
			  
			  if(editDueDate=='Y')
				  {
					for(var i=1;i<=tableLength;i++)
					{
						var fromInstallment1=document.getElementById("fromInstallment1").value;
						var fromInstallment=document.getElementById("fromInstallment"+i).value;
					
						var toInstallment=document.getElementById("toInstallment"+i).value;
					    
						var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
						
						var principalAmount=document.getElementById("principalAmount"+i).value;
						
						var installmentAmount=document.getElementById("installmentAmount"+i).value;
						
						var dueDate=document.getElementById("dueDate"+i).value;
						dueDateObj=getDateObject(dueDate,formatD.substring(2, 3));

						 if(recoveryType=='F' && (fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")||(dueDate==""))
						 {
							 alert("Please fill all the fields ");
							 document.getElementById("save").removeAttribute("disabled","true");
							 DisButClass.prototype.EnbButMethod();
							 return false;
						 }
						 
						 if(fromInstallment==0){
							 alert("From Installment cannot be Zero");
							 document.getElementById("save").removeAttribute("disabled","true");
							 DisButClass.prototype.EnbButMethod();
							 return false;
							      }	
						 
						 
						 if(fromInstallment1!=1){
							 alert("First Installment should start from 1");
							 document.getElementById("save").removeAttribute("disabled","true");
							 DisButClass.prototype.EnbButMethod();
							 return false;
							      }	
						 
						 if(i==1)
							 {
							 var instDueDate= document.getElementById("dueDate"+i).value;
							 var insNextDueDate= document.getElementById("insNextDueDate").value;
							 var dueDay= instDueDate.substring(0,2);
							 var instDay= insNextDueDate.substring(0,2);
							 var dueMonth=instDueDate.substring(3,5);
							 var instMonth=insNextDueDate.substring(3,5);
							 var dueYear=instDueDate.substring(6);
							 var instYear=insNextDueDate.substring(6);
					
							 if(parseFloat(dueYear)!=parseFloat(instYear))
							 				{
							 					alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
							 					document.getElementById("save").removeAttribute("disabled","true");
							 					DisButClass.prototype.EnbButMethod();
							 					return false;
							 				}
							 				else
							 				{
							 					if(parseFloat(dueYear)==parseFloat(instYear))
							 					if(parseFloat(dueMonth)!=parseFloat(instMonth))
							 					{
							 						alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
							 						document.getElementById("save").removeAttribute("disabled","true");
							 						DisButClass.prototype.EnbButMethod();
							 						return false;
							 					}
							 					else
							 					{
							 						if(parseFloat(dueMonth)==parseFloat(instMonth))
							 						if(parseFloat(dueDay)!=parseFloat(instDay))
							 						{
							 							alert("First Due Date should be equal to Next Due Date "+insNextDueDate);
							 							document.getElementById("save").removeAttribute("disabled","true");
							 							DisButClass.prototype.EnbButMethod();
							 							return false;
							 						}
							 					}
							 				} 
						
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
						 else{   	  
							recoveryPercen=removeComma(recoveryPercen);
						 }		
					    sum= sum + recoveryPercen;
						// add by vijendra singh || allow till for decimal
						sum=parseFloat(sum.toFixed(4));													    
					   // end by vijendra singh
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
					    
					   
				
					    if(parseInt(document.getElementById("fromInstallment"+i).value) != parseInt(document.getElementById("toInstallment"+i).value))
						{	    	
							alert("From Installment should be equal to To Installment");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					 
						
						if(i<tableLength){	
							var k=i+1;
					if(parseInt(document.getElementById("toInstallment"+i).value) >= parseInt(document.getElementById("fromInstallment"+k).value))
						{
							
							alert("Next From Installment should be greater than previous To Installment");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					
						 var dueDateOne= document.getElementById("dueDate"+i).value;
						 var dueDateTwo= document.getElementById("dueDate"+k).value;
						 var day1= dueDateOne.substring(0,2);
						 var day2= dueDateTwo.substring(0,2);
						 var month1=dueDateOne.substring(3,5);
						 var month2=dueDateTwo.substring(3,5);
						 var year1=dueDateOne.substring(6);
						 var year2=dueDateTwo.substring(6);
				
						 if(parseFloat(year1)>parseFloat(year2))
						 				{
						 					alert("Next due date should be greater than previous due date.");
						 					document.getElementById("save").removeAttribute("disabled","true");
						 					DisButClass.prototype.EnbButMethod();
						 					return false;
						 				}
						 				else
						 				{
						 					if(parseFloat(year1)==parseFloat(year2))
						 					if(parseFloat(month1)>parseFloat(month2))
						 					{
						 						alert("Next due date should be greater than previous due date.");
						 						document.getElementById("save").removeAttribute("disabled","true");
						 						DisButClass.prototype.EnbButMethod();
						 						return false;
						 					}
						 					else
						 					{
						 						if(parseFloat(month1)==parseFloat(month2))
						 						if(parseFloat(day1)>=parseFloat(day2))
						 						{
						 							alert("Next due date should be greater than previous due date.");
						 							document.getElementById("save").removeAttribute("disabled","true");
						 							DisButClass.prototype.EnbButMethod();
						 							return false;
						 						}
						 					}
						 				} 		
				
						}
					
					}
					 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
					var maxDueDateObj="";
					var maxDateObj="";
					var lastToInstall=document.getElementById("toInstallment"+tableLength).value;

					var maxDueDate=document.getElementById("dueDate"+tableLength).value;
					maxDueDateObj=getDateObject(maxDueDate,formatD.substring(2, 3));
					var maxDate=document.getElementById("maxDate").value;
					maxDateObj=getDateObject(maxDate,formatD.substring(2, 3));
					if(maxDueDateObj>maxDateObj)
						{
						alert('Last Due date must be equal to '+ maxDate );
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
						}
					if(maxDueDateObj<maxDateObj)
					{
					alert('Last Due date must be equal to '+ maxDate );
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
					}

					if(parseInt(lastToInstall)> parseInt(totalInstallment)){
						alert("No of  Installment should be equal to  Total Installment");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					if(parseInt(lastToInstall)< parseInt(totalInstallment)){
						alert("No of  Installment should be equal to  Total Installment");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					if(sum!=100 && recoveryType=='P')
					{
						alert("Total recovery Percentage should be  equal to 100 %");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					if(((psum>removeComma(loanAmount)||psum<removeComma(loanAmount)) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'||installmentType=='I' || installmentType=='S'))
					{
						alert("Principal Amount should be equal to loan amount: "+loanAmount);
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					
					if((isum<removeComma(loanAmount) && recoveryType=='F' && (installmentType=='E'||installmentType=='G')))
					{
						
						alert("Sum of Installment Amount should be equal to or greater than loan amount: "+loanAmount);
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanRepricingProcess.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&loanId="+loanId+"&reschId="+reschId+"&editDueDate="+editDueDate+"&frequency="+frequency+"&maxDate="+maxDate;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("installmentPlanForm").submit();
				     return true;
				  		
				  }
			  // Vishal changes end
			  
			  	  else
				  {
					  for(var i=1;i<=tableLength;i++)
						{
							var fromInstallment1=document.getElementById("fromInstallment1").value;
							var fromInstallment=document.getElementById("fromInstallment"+i).value;
						
							var toInstallment=document.getElementById("toInstallment"+i).value;
						    
							var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
							
							var principalAmount=document.getElementById("principalAmount"+i).value;
							
							var installmentAmount=document.getElementById("installmentAmount"+i).value;
							if(installmentType=='I')
							{
							var dueDate=document.getElementById("dueDate"+i).value;
							dueDateObj=getDateObject(dueDate,formatD.substring(2, 3));
							}
							 if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")&& installmentType!='I')
							 {
								 alert("Please fill all the fields ");
								 document.getElementById("save").removeAttribute("disabled","true");
								 DisButClass.prototype.EnbButMethod();
								 return false;
							 }
							 if(recoveryType=='F' && installmentType=='I' && (fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")||(dueDate==""))
							 {
								 alert("Please fill all the fields ");
								 document.getElementById("save").removeAttribute("disabled","true");
								 DisButClass.prototype.EnbButMethod();
								 return false;
							 }
							 
							 if(fromInstallment==0){
								 alert("From Installment cannot be Zero");
								 document.getElementById("save").removeAttribute("disabled","true");
								 DisButClass.prototype.EnbButMethod();
								 return false;
								      }	
							 
							 
							 if(fromInstallment1!=1){
								 alert("First Installment should start from 1");
								 document.getElementById("save").removeAttribute("disabled","true");
								 DisButClass.prototype.EnbButMethod();
								 return false;
								      }	
							 
							 if(installmentType=='I')
								 {
								 	if(dueDateObj<repayEffDateObj)
									 {
									 alert("Due Date cannot be less than RepayEffective Date.");
									 document.getElementById("save").removeAttribute("disabled","true");
									 DisButClass.prototype.EnbButMethod();
									 return false;
									 }
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
						  // add by vijendra singh || allow till for decimal
						   sum=parseFloat(sum.toFixed(4));					  
						 // end by vijendra singh
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
								
								alert("Next from Installment should be greater than previous to Installment");
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
						{
							
							alert("Next from Installment should be equal to previous to Installment");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						 if(installmentType=='I')
						 {
						
							 var dueDateOne= document.getElementById("dueDate"+i).value;
							 var dueDateTwo= document.getElementById("dueDate"+k).value;
							 var day1= dueDateOne.substring(0,2);
							 var day2= dueDateTwo.substring(0,2);
							 var month1=dueDateOne.substring(3,5);
							 var month2=dueDateTwo.substring(3,5);
							 var year1=dueDateOne.substring(6);
							 var year2=dueDateTwo.substring(6);
					
							 if(parseFloat(year1)>parseFloat(year2))
							 				{
							 					alert("Next due date should be greater than previous due date.");
							 					document.getElementById("save").removeAttribute("disabled","true");
							 					DisButClass.prototype.EnbButMethod();
							 					return false;
							 				}
							 				else
							 				{
							 					if(parseFloat(year1)==parseFloat(year2))
							 					if(parseFloat(month1)>parseFloat(month2))
							 					{
							 						alert("Next due date should be greater than previous due date.");
							 						document.getElementById("save").removeAttribute("disabled","true");
							 						DisButClass.prototype.EnbButMethod();
							 						return false;
							 					}
							 					else
							 					{
							 						if(parseFloat(month1)==parseFloat(month2))
							 						if(parseFloat(day1)>=parseFloat(day2))
							 						{
							 							alert("Next due date should be greater than previous due date.");
							 							document.getElementById("save").removeAttribute("disabled","true");
							 							DisButClass.prototype.EnbButMethod();
							 							return false;
							 						}
							 					}
							 				} 
					
						 }
					
							}
						
						}
						 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
						var maxDueDateObj="";
						var maxDateObj="";
						var lastToInstall=document.getElementById("toInstallment"+tableLength).value;
						 if(installmentType=='I')
						 {
						var maxDueDate=document.getElementById("dueDate"+tableLength).value;
						maxDueDateObj=getDateObject(maxDueDate,formatD.substring(2, 3));
						var maxDate=document.getElementById("maxDate").value;
						maxDateObj=getDateObject(maxDate,formatD.substring(2, 3));
						if(maxDueDateObj>maxDateObj)
							{
							alert('Last Due date cannot be greater than repayeffective date + tenure ');
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
							}
						 }
						if(parseInt(lastToInstall)> parseInt(totalInstallment)){
							alert("No of  Installment should be equal to  Total Installment");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(parseInt(lastToInstall)< parseInt(totalInstallment)){
							alert("No of  Installment should be equal to  Total Installment");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(sum!=100 && recoveryType=='P')
						{
							alert("Total recovery Percentage should be  equal to 100 %");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(((psum>removeComma(loanAmount)||psum<removeComma(loanAmount)) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'||installmentType=='I' || installmentType=='S'))
						{
							alert("Principal Amount should be equal to loan amount: "+loanAmount);
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if((isum<removeComma(loanAmount) && recoveryType=='F' && (installmentType=='E'||installmentType=='G')))
						{
							
							alert("Sum of Installment Amount should be equal to or greater than loan amount: "+loanAmount);
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						document.getElementById("installmentPlanForm").action = contextPath+"/installmentPlanRepricingProcess.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&loanId="+loanId+"&reschId="+reschId+"&editDueDate="+editDueDate+"&frequency="+frequency+"&maxDate="+maxDate;
						document.getElementById("processingImage").style.display = '';
						document.getElementById("installmentPlanForm").submit();
					     return true;
				  }

		} */
		
		
		function calculateInstallmentNoInRepricing()
		{
			var installmentFrequency = document.getElementById("installmentFrequency").value;
			var tenure = document.getElementById("tenure").value;
			var parseTenure=0;
			var freqMonth=0;
			if(installmentFrequency=='M')
			{
				freqMonth=1;
			}
			else if(installmentFrequency=='B')
			{
				freqMonth=2;
			}
			else if(installmentFrequency=='Q')
			{
				freqMonth=3;
			}
			else if(installmentFrequency=='H')
			{
				freqMonth=6;
			}
			else if(installmentFrequency=='Y')
			{
				freqMonth=12;
			}
			
			if(installmentFrequency!='')
			{
				parsedFreq=parseInt(freqMonth);
			}
			
			if(tenure!='')
			{
				parseTenure=parseInt(tenure);
			}
			
			// alert("parsedFreq "+parsedFreq+" & parseTenure "+parseTenure);
			calInsat=parseTenure/parsedFreq;
			// alert("calInsat: "+calInsat);
			if(isInt(calInsat))
			{
				document.getElementById("noOfInstl").value=calInsat;
			}
			else
			{
				alert("The Combination of Tenure and Frequency are Incorrect");
				document.getElementById("noOfInstl").value='';
				document.getElementById("tenure").value='';
				document.getElementById("tenure").focus();
				return false;
			}
		}
		
		function calculateMaturityDateInRepricing()
		{
			var x = parseInt(document.getElementById('tenure').value); // or
																		// whatever
																		// offset
			var formatD=document.getElementById("formatD").value;
	
			// alert(x);
			var currDate =   document.getElementById('reschDate').value;
			
			if(document.getElementById('tenure').value==""||currDate==""){
				// alert("Please select Tenure and Installment Date ");
			}else{
			// var currDate = new Date(repayDate);
			  // alert(currDate);
			  var currDay   = currDate.substring(0,2);
			  // alert(currDay);
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
				// alert(CurrentDate);
				CurrentDate.setMonth(CurrentDate.getMonth()+x);
			    //alert(CurrentDate.getMonth());
			   //modMonth=CurrentDate.getMonth()+1;
			   modMonth=CurrentDate.getMonth();
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
			  // alert(ModDateStr);
			  
			  document.getElementById('maturityDate').value=ModDateStr;
			}
		}
		
		function saveInstrument(){		
			
			
	
			var contextPath=document.getElementById("contextPath").value;
			var toInstallment = parseInt(document.getElementById("toInstallment").value);
			
			var fromInstallment = parseInt(document.getElementById("fromInstallment").value); 
			var instDiffCount=parseInt(document.getElementById("installmentDiff").value);
			
			
			var startingChequeNo = parseFloat(document.getElementById("startingChequeNo").value); 
			var endingChequeNo = parseFloat(document.getElementById("endingChequeNo").value);
			var installments =	 document.getElementsByName("installments");
			var instrumentAmountAll = document.getElementsByName("instrumentAmountAll");
			var installmentAmountAll = document.getElementsByName("installmentAmountAll");
			
			
			var diffOfInstumentNo=endingChequeNo-startingChequeNo;
			
			
			
			var a =toInstallment - fromInstallment;
		
			var totalInstallments = parseInt(document.getElementById("totalInstallments").value);
			
			
			if(validateInstrumentCapturingMakerValidatorForm(document.getElementById("sourcingForm")))
			{
				
				
				if(document.getElementById("instrumentType").value == "Q"){
					
			
					
					
					 if((document.getElementById("purpose").value) == 'INSTALLMENT' ) {	
						 
						 
						 
			   if(removeComma(document.getElementById("installmentAmount").value) <  removeComma(document.getElementById("instrumentAmount").value)){
					
					alert("You can disburse your amount to another loan as your PDC Amount is higher than Installment Amount");
					
				}
			 }
			   
			   	  // alert(installments.length);
			   // if(installments.length == 0){
			   		 // alert(fromInstallment);
			   		// alert(document.getElementById("loanInstallmentMode").value);
			   		 if((document.getElementById("loanInstallmentMode").value)=='A'){
			   			 // alert(document.getElementById("loanInstallmentMode").value);
			   			if(fromInstallment == 1){
			   				alert("Your first installment is already received.Please generate it from second.");
			   				document.getElementById("fromInstallment").value='';
			   				document.getElementById("fromInstallment").focus();
			   				document.getElementById("save").removeAttribute("disabled");
							return false;
			   			}
			   			 
			   			var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
			   			// alert("loanAdvanceInstallment--"+loanAdvanceInstallment);
			   			
			   			if(loanAdvanceInstallment != 0){
			   			// if(totalInstallments == toInstallment){
			   					
			   				if(totalInstallments < (toInstallment + loanAdvanceInstallment)){
			   					alert(+loanAdvanceInstallment+" Advance Installment/s has already been received.");
			   					document.getElementById("save").removeAttribute("disabled");
								return false;
			   				// }
			   				}
			   				
			   			}
			   			
			   			
			   			
			   		 }
			   		// alert((document.getElementById("loanInstallmentMode").value));
			   		if((document.getElementById("loanInstallmentMode").value)=='R'){
			   			
			   			var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
			   		// alert("loanAdvanceInstallment--"+loanAdvanceInstallment);
			   			
			   			if(loanAdvanceInstallment != 0){
			   				
			   			// if(totalInstallments == toInstallment){
			   				
			   				if(totalInstallments < (toInstallment + loanAdvanceInstallment)){
			   					alert(+loanAdvanceInstallment+" Advance Installment/s has already been received.");
			   					document.getElementById("save").removeAttribute("disabled");
								return false;
			   				}
			   			// }
			   				
			   			}
			   			
			   		}
			   		 
			   	 // }
				
					 
					 
			   
			   if((document.getElementById("purpose").value) == '' ) {
					
					alert("Enter Purpose");
					document.getElementById("purpose").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				}
				
			   
				if(document.getElementById("purpose").value == "PDC"){		
					
					
	              if(fromInstallment == 0 ) {
						
						alert("From Installment should be greater than 0");
						document.getElementById("fromInstallment").focus();
						document.getElementById("fromInstallment").value="";
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
			        if((document.getElementById("fromInstallment").value) == '' ) {
						
						alert("Enter From Installment");
						document.getElementById("fromInstallment").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
			      
			        if((document.getElementById("toInstallment").value) == '' ) {
						
						alert("Enter To Installments");
						document.getElementById("toInstallment").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
	               if((document.getElementById("startingChequeNo").value) == '' ) {
						
						alert("Enter Starting Instrument No");
						document.getElementById("startingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
			        if((document.getElementById("endingChequeNo").value) == '' ) {
						
						alert("Enter Ending Instrument No");
						document.getElementById("endingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					} 
			        
	               if((document.getElementById("startingChequeNo").value.length) < 6 ) {
						
						alert("Minimum length of Starting Instrument No should be 6.");
						document.getElementById("startingChequeNo").value="";
						document.getElementById("startingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
	               
	               if((document.getElementById("endingChequeNo").value.length) < 6 ) {
						
						alert("Minimum length of Ending Instrument No should be 6.");
						document.getElementById("endingChequeNo").value="";
						document.getElementById("endingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
				if(endingChequeNo<startingChequeNo){
							
							alert(" Ending Instrument No must be greater than Starting Instrument No.");
							document.getElementById("endingChequeNo").value="";
							document.getElementById("endingChequeNo").focus();
							document.getElementById("save").removeAttribute("disabled");
							return false;
						}
			        
			        
			        
					if(endingChequeNo >= startingChequeNo ){
// alert("Starting Cheque no. cannot be greater than Ending Cheque no.");
// document.getElementById("endingChequeNo").value="";
// document.getElementById("endingChequeNo").focus();
// document.getElementById("save").removeAttribute("disabled");
// return false;
						
					if((toInstallment - fromInstallment) != (endingChequeNo - startingChequeNo) ){
						
						alert("Cheques are not equivallent to Installments.");
						document.getElementById("endingChequeNo").value="";
						document.getElementById("startingChequeNo").value="";
						document.getElementById("startingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
						
					}
					}
				
					
					if(endingChequeNo <= startingChequeNo ){
// alert("Starting Cheque no. cannot be greater than Ending Cheque no.");
// document.getElementById("endingChequeNo").value="";
// document.getElementById("endingChequeNo").focus();
// document.getElementById("save").removeAttribute("disabled");
// return false;
						
					if((toInstallment - fromInstallment) != (startingChequeNo - endingChequeNo) ){
						
						alert("Cheques are not equivallent to Installments.");
						document.getElementById("endingChequeNo").value="";
						document.getElementById("startingChequeNo").value="";
						document.getElementById("startingChequeNo").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
						
					}
					}
					if((document.getElementById("instrumentAmount").value) == '' ) {
						
						alert("Enter Instrument Amount");
						document.getElementById("instrumentAmount").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					if((document.getElementById("customeracType").value) == '' ) {
						
						alert("Select Customer A/C Type");
						document.getElementById("customeracType").focus();
						document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
					var customerAcType = parseInt(document.getElementById("customeracType").value);

					if(customerAcType != 13 ) {
							
							alert("Warning !" +
								" Capturing of PDC allowed from CC/OD A/C type 13");
							// document.getElementById("customeracType").focus();
							
							
						}
					
			
					var instrumentNumberAll = document.getElementsByName("startingChequeNoAll");
					
					for(var i=startingChequeNo;i<=endingChequeNo;i++){	
						
						for(var j=0;j< instrumentNumberAll.length;j++){
							
							if((parseInt(instrumentNumberAll[j].value)) == i){
								alert("Instrument no "+instrumentNumberAll[j].value+" has already been Captured for this loan.");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
							
							
						}
						
						
						
					}
					
				
					 var flag =0;
					 var add = 0;
					// alert("br for"+a);
					 var purposeListall = document.getElementsByName("purposeAll");
					 
				for(var i=0;i<installments.length;i++){	
						
					if((purposeListall[i].value) == 'INSTALLMENT'){
					
					
					
					if((installments[i].value) != (fromInstallment)){
						
						if(a > 0){										
							if((installments[i].value) == (fromInstallment + 1 )){
								
							flag = 0;
							add = 0;
							a = a-1;
							// alert("a"+a);
							fromInstallment = fromInstallment + 1;
							// alert("fromInstallment"+fromInstallment);
							}
						}
						
						
					}
					
					
					if((installments[i].value) == (fromInstallment)  ){
						
						if(flag != 0){
// alert("add------"+add);
// alert(removeComma(instrumentAmountAll[i].value) + add );
							
							if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value) + add )){
								alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
								document.getElementById("save").removeAttribute("disabled");
								return false;							
							}
							else{
								 
							// var cnfrm =confirm("Do you want to generate PDC
							// for same installment----"+installments[i].value);
								
									// if(cnfrm){
										flag = flag + 1;
// alert("flag---"+flag);
										 add = add + removeComma(instrumentAmountAll[i].value);
// alert("add----"+add);
									// }else{
								// document.getElementById("save").removeAttribute("disabled");
							// return false;
								// }
								} 
						}else{
						  
						if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value))){
							
							alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
							document.getElementById("save").removeAttribute("disabled");
							return false;
						}else{
							 
					  var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);
					
						if(cnfrm){
							flag = flag + 1;
// alert("flag---"+flag);
							 add = add + removeComma(instrumentAmountAll[i].value);
// alert("add----"+add);
						}else{
							document.getElementById("save").removeAttribute("disabled");
						return false;	
						}
					} 
				}
						
				}
				}	
			}
			
				
				
		}else{
			
			
			 if((document.getElementById("startingChequeNo").value) == '' ) {
					
					alert("Enter Starting Instrument No");
					document.getElementById("startingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				}
				
		        if((document.getElementById("endingChequeNo").value) == '' ) {
					
					alert("Enter Ending Instrument No");
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				} 
		        
		        
		        if((document.getElementById("startingChequeNo").value.length) < 6 ) {
					
					alert("Minimum length of Starting Instrument No should be 6.");
					document.getElementById("startingChequeNo").value="";
					document.getElementById("startingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				}
               
               if((document.getElementById("endingChequeNo").value.length) < 6 ) {
					
					alert("Minimum length of Ending Instrument No should be 6.");
					document.getElementById("endingChequeNo").value="";
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				} 
		        
       		
				if(endingChequeNo<startingChequeNo){
					
					alert(" Ending Instrument No must be greater than Starting Instrument No.");
					document.getElementById("endingChequeNo").value="";
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				}
		        
		        
		        
		        
		        
		        
	        if((document.getElementById("instrumentAmount").value) == '' ) {
					
					alert("Enter Instrument Amount");
					document.getElementById("instrumentAmount").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;
				}
		        
		        
		        
			
			if((document.getElementById("customeracType").value) == '' ) {
				
				alert("Select Customer A/C Type");
				document.getElementById("customeracType").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			
		if((document.getElementById("clearingType").value) == '' ) {
				
				alert("Select Clearing Type");
				document.getElementById("clearingType").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			
			
			if((document.getElementById("purpose").value) == 'INDGUAR' ) {
				
			
				var customerAcType = parseInt(document.getElementById("customeracType").value);
				
			 	if(diffOfInstumentNo > instDiffCount){
					alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;				
					}

				if(customerAcType != 10 ) {
						
						alert("Warning !" +
							" Capturing of PDC allowed from Saving A/C type 10");					 				
						
					}

			 
			
			
				
			}
			
			if((document.getElementById("purpose").value) == 'CORGUAR' ) {
				
				
				var customerAcType = parseInt(document.getElementById("customeracType").value);
				
			  	if(diffOfInstumentNo > instDiffCount){
					alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;				
					}

				if(customerAcType == 10 ) {
						
						alert("Warning !" +
							" Capturing of PDC allowed from Current or CC/OD A/C type 11 or 13");
						
					}
			
				
			}
			
	         if((document.getElementById("purpose").value) == 'INS' ) {
				
				
				var customerAcType = parseInt(document.getElementById("customeracType").value);
				
			  	if(diffOfInstumentNo > instDiffCount){
					alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;				
					}

				if(customerAcType != 13 ) {
						
						alert("Warning !" +
							" Capturing of PDC allowed from CC/OD A/C type 13");
						
					}
			
				
			}
			
	         if((document.getElementById("purpose").value) == 'SEC' ) {
	 			
	 			
	 			var customerAcType = parseInt(document.getElementById("customeracType").value);
	 			
	 		  	if(diffOfInstumentNo > instDiffCount){
					alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;				
					}

	 			if(customerAcType != 13 ) {
	 					
	 					alert("Warning !" +
	 						" Capturing of PDC allowed from CC/OD A/C type 13");
	 					
	 				}
	 		
	 			
	 		}
			
	         if((document.getElementById("purpose").value) == 'WHLS' ) {
	  			
	  			
	  			var customerAcType = parseInt(document.getElementById("customeracType").value);
	  			
	  		  	if(diffOfInstumentNo > instDiffCount){
					alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
					document.getElementById("endingChequeNo").focus();
					document.getElementById("save").removeAttribute("disabled");
					return false;				
					}

	  			if(customerAcType != 13 ) {
	  					
	  					alert("Warning !" +
	  						" Capturing of PDC allowed from CC/OD A/C type 13");
	  					
	  				}
	  	
	  			
	  		}
			
			
			
			
		}
	             }else{
		
		 if((document.getElementById("fromInstallment").value) == '' ) {
				
				alert("Enter From Installment");
				document.getElementById("fromInstallment").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
		 if((document.getElementById("fromInstallment").value) == 0 ) {
				
				alert("From Installment should be greater than 0");
				document.getElementById("fromInstallment").focus();
				document.getElementById("fromInstallment").value="";
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
	     if((document.getElementById("toInstallment").value) == '' ) {
				
				alert("Enter To Installments");
				document.getElementById("toInstallment").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}

	     if((document.getElementById("instrumentAmount").value) == '' ) {
				
				alert("Enter Instrument Amount");
				document.getElementById("instrumentAmount").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
	     if((document.getElementById("ecsTransactionCode").value) == '' ) {					
				
				alert("Select ECS Transaction Code");
				document.getElementById("ecsTransactionCode").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			
	  if((document.getElementById("customeracType").value) == '' ) {
				
				alert("Select Customer A/C Type");
				document.getElementById("customeracType").focus();
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
	  
	 
	  
	  
	  if((document.getElementById("spnserBnkBrncCode").value) == '' ) {
			
			alert("Select Sponsor Bank Branch Code");
			document.getElementById("spnserBnkBrncCode").focus();
			document.getElementById("save").removeAttribute("disabled");
			return false;
		}
		
	if((document.getElementById("utilityNo").value) == '' ) {
			
			alert("Select Utility no");
			document.getElementById("utilityNo").focus();
			document.getElementById("save").removeAttribute("disabled");
			return false;
		}
		


	if(installments.length == 0){
			 // alert(fromInstallment);
			// alert(document.getElementById("loanInstallmentMode").value);
			 if((document.getElementById("loanInstallmentMode").value)=='A'){
				 // alert(document.getElementById("loanInstallmentMode").value);
				if(fromInstallment == 1){
					alert("Your first installment is already received.Please generate it from second.");
					document.getElementById("fromInstallment").value='';
					document.getElementById("fromInstallment").focus();
					document.getElementById("save").removeAttribute("disabled");
				return false;
				}
				 
			 }
		  } 


	var customerAcType = parseInt(document.getElementById("customeracType").value);

	if(customerAcType != 13 ) {
			
			alert("Warning !" +
				" Capturing of ECS allowed from CC/OD A/C type 13");
			// document.getElementById("customeracType").focus();
			
			
		}


	var flag =0;
	var add = 0;

	for(var i=0;i<installments.length;i++){		

	if((installments[i].value) == (fromInstallment) ){
		
		if(flag != 0){
// alert("add------"+add);
// alert(removeComma(instrumentAmountAll[i].value) + add );
			
			if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value) + add )){
				alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
				document.getElementById("save").removeAttribute("disabled");
				return false;							
			}
			else{
				 
				  var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);
				
					if(cnfrm){
						flag = flag + 1;
// alert("flag---"+flag);
						 add = add + removeComma(instrumentAmountAll[i].value);
// alert("add----"+add);
					}else{
						document.getElementById("save").removeAttribute("disabled");
					return false;	
					}
				} 
		}else{
		  
		if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value))){
			
			alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
			document.getElementById("save").removeAttribute("disabled");
			return false;
		}else{
			 
	 var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);

		if(cnfrm){
			flag = flag + 1;
// alert("flag---"+flag);
			 add = add + removeComma(instrumentAmountAll[i].value);
// alert("add----"+add);
		}else{
			document.getElementById("save").removeAttribute("disabled");
		return false;	
		}
	} 
	}
		
	}

	}
		
		
	}
			
				var id =document.getElementById("lbxLoanNoHID").value;
				 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessAction.do?method=generatePDC&id="+id;
			     document.getElementById("sourcingForm").submit();
			     return true;
	 }
			else
			{
				document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			
	
		}
		
		   function savenForPDCInstrument(alert1){
			   	var contextPath=document.getElementById("contextPath").value;
	             			   	var id=document.getElementById('lbxLoanNoHID').value;
			   
			   	var ch = document.getElementsByName("chk");
			   	var pdcDate = document.getElementsByName("pdcDate");
			   	
// alert(pdcDate);
			   	var dateOne = document.getElementsByName("dateOne");
			   	var formatD = document.getElementById("formatD").value;
			   	var seprator = formatD.substring(2, 3);
			   	var checked ="";
			   	var checkedDate="";
// alert(ch.length);
			   	for(i=0;i<ch.length;i++){

// alert("pdc---"+pdcDate[i].value);
// alert("date1---"+dateOne[i].value);
			   	
			   		if(getDateObject(pdcDate[i].value, seprator) > getDateObject(dateOne[i].value, seprator)){
			   			
			   			alert("PDC Date should be equal to or less than Due Date.");
			   			document.getElementById("savenfor").removeAttribute("disabled");
			   			return false;
			   			
			   		}
		   		
		   				   checked =  checked + ch[i].value +"/";
// alert("checked"+checked);
		   				  
		   				checkedDate = checkedDate + pdcDate[i].value +"/";
// alert("checkedDate"+checkedDate);
		   			
		   			}
			   	
// alert("---"+document.getElementById('lbxLoanNoHID').value);
	
			   	
			   	
			   
			   	
			   	var totalInstallmentsA = parseInt(document.getElementById("totalInstallments").value);
			   	var pdcPartialForward = document.getElementById("pdcPartialForward").value;
			   	var installments =	 document.getElementsByName("installments");
			   	var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
			   	// alert("pdcPartialForward--"+pdcPartialForward);
			   
			   	var flag = 0;
			   	var flagg = 0;
			   	if(pdcPartialForward == 'N'){
			   		
			   		if((document.getElementById("loanInstallmentMode").value)=='A'){
			   			
			   		for(var i=0;i<installments.length;i++){
			   			
			   			var a = parseInt(installments[i].value);
			   			// alert("aaaaaaaa--"+a);
			   			// alert("aaaaaaaa--"+totalInstallmentsA);
			   			
			   			
			   			if( a <= totalInstallmentsA){
			   				
                       if(a!=flag){
                       	
                         flag =parseInt(installments[i].value);
                         
                         flagg = flagg + 1;
			   				// flag = flag+ 1;
			   				// alert("flag==="+flag);
			   				// alert("flagg==="+flagg);
			   				}
			   				
			   			}
			   					   			
			   		}
			   		
			   		if((totalInstallmentsA) != (flagg + 1 + loanAdvanceInstallment )){
			   		
			   			alert("Please capture PDC/ECS for all installments");
			   			document.getElementById("savenfor").removeAttribute("disabled");
			   			return false;
			   		}
			   		
			   		}else{
			   			
			   			for(var i=0;i<installments.length;i++){
				   			
				   			var a = parseInt(installments[i].value);
				   			// alert("aaaaaaaa--"+a);
				   			// alert("aaaaaaaa--"+totalInstallmentsA);
				   			
				   			
				   			if( a <= totalInstallmentsA){
				   				
	                        if(a!=flag){
	                        	
	                          flag =parseInt(installments[i].value);
	                          
	                          flagg = flagg + 1;
				   				// flag = flag+ 1;
				   				// alert("flag==="+flag);
				   				// alert("flagg==="+flagg);
				   				}
				   				
				   			}
				   					   			
				   		}
				   		
				   		if((totalInstallmentsA) != (flagg + loanAdvanceInstallment)){
				   		
				   			alert("Please capture PDC/ECS for all installments");
				   			document.getElementById("savenfor").removeAttribute("disabled");
				   			return false;
				   		}
			   			
			   		}
			   	}
			   	
			   	
			   	
			   	
	if(pdcPartialForward == 'N'){
		
			   		
	        var installments =	 document.getElementsByName("installments");
			var instrumentAmountAll = document.getElementsByName("instrumentAmountAll");
			var installmentAmountAll = document.getElementsByName("installmentAmountAll");
			
			var instrumentAmountAdd=0;
			var installmentAmountAdd=0;			
			 var purposeListAll = document.getElementsByName("purposeAll");
			 var flag =0;
			 var add = 0;
			 var k=0;
			 
		for(var i=0;i<installments.length;i++){
			
			if((purposeListAll[i].value) == 'INSTALLMENT'){
			var j=installments[k].value;
			var intallAmmount=removeComma(installmentAmountAll[k].value);
			
			if(i<installments.length-1){
				// alert("value of i :-"+i);
			if(removeComma(installments[i].value)==removeComma(installments[i+1].value) ){
			// alert(installments[i].value +" and "+ installments[i+1].value+
			// "Are equal and i is"+i);
			
				instrumentAmountAdd = instrumentAmountAdd + removeComma(instrumentAmountAll[i].value);
				// alert(i+" instrumentAmountAdd--in if main
				// -"+instrumentAmountAdd);
				
			}else{
				instrumentAmountAdd=instrumentAmountAdd + removeComma(instrumentAmountAll[i].value);
				// alert(i+" instrumentAmountAdd--in else before if
				// -"+instrumentAmountAdd);
				// alert(i+" installmentAmountAll--in else before if
				// -"+installmentAmountAll[i].value);
				if(removeComma(installmentAmountAll[i].value)<=instrumentAmountAdd){
				// alert(" intallAmmount ids eqal to sum of insrtment ammont");
					
				}else{
					alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
					document.getElementById("savenfor").removeAttribute("disabled");
			   		return false;
				}
				k=i+1;
				// alert("Value of k:-"+k);
				instrumentAmountAdd=0;
			}
		
			}
			if(i == installments.length-1 ){
				// alert(i +" i is and length is:-" +installments.length);
				if(installments.length>1){
				if(removeComma(installments[i].value)!=removeComma(installments[i-1].value) ){
				if(removeComma(installmentAmountAll[i].value)>removeComma(instrumentAmountAll[i].value)){
					alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
					document.getElementById("savenfor").removeAttribute("disabled");
			   		return false;
				}
			}
				}else{
					if(removeComma(installmentAmountAll[i].value)>removeComma(instrumentAmountAll[i].value)){
						alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
						document.getElementById("savenfor").removeAttribute("disabled");
				   		return false;
					}
				}
			}
			
		}	
		}
		
			   	}
			   	
			  	
	        var purposeList = document.getElementsByName("purposeAll");
	        var instrumentTypeList = document.getElementsByName("instrumentTypeAll");
	        var ecsFlag=0;
	        var purposeFlag=0;
	        var wholeFlag=0;
	        var corGurantee=0;
	        var indGuarantee=0;
	        var insurance=0;
	        var installmenttt=0;
	        
	            for(var i =0;i<purposeList.length;i++){
	            	
	            	if((instrumentTypeList[i].value) == 'E'){
	            		ecsFlag=1;
	            		}
	            	
	            	if((purposeList[i].value) == 'SECURITY'){
	            		purposeFlag=1;
	            	}
	            	
	            	if((purposeList[i].value) == 'WHOLE AMOUNT'){
	            		wholeFlag=1;
	            	}
	            	
	            	if((purposeList[i].value) == 'COR GUARANTEE'){
	            		corGurantee=1;
	            	}
	            	
	            	if((purposeList[i].value) == 'IND GUARANTEE'){
	            		indGuarantee=1;
	            	}
	            	
	            	if((purposeList[i].value) == 'INSURANCE'){
	            		insurance=1;
	            	}
	            	if((purposeList[i].value) == 'INSTALLMENT'){
	            		installmenttt=1;
	            	}
	            	
	            	
	            }
	
	         
	            if(ecsFlag == 1){
	            	
	            	if(purposeFlag != 1){
	            		alert("Please Capture Security PDC and Whole Amount PDC. ");
	            		document.getElementById("savenfor").removeAttribute("disabled");
	            		return false;
	            	}
	            	
	            	if(wholeFlag != 1){
	            		alert("Please Capture Security PDC and Whole Amount PDC. ");
	            		document.getElementById("savenfor").removeAttribute("disabled");
	            		return false;
	            	}
	            	
	            }
	    
	            if(purposeFlag == 1){
	            	
	            	if(installmenttt != 1){
	            		alert("Please Capture PDC/ECS. ");
	            		document.getElementById("savenfor").removeAttribute("disabled");
	            		return false;
	            	}
	            	
	            	
	            }
	            
               if(wholeFlag == 1){
	            	
	            	if(installmenttt != 1){
	            		alert("Please Capture PDC/ECS. ");
	            		document.getElementById("savenfor").removeAttribute("disabled");
	            		return false;
	            	}
	            	
	            	
	            }

             if(corGurantee == 1){
	
	                if(installmenttt != 1){
		 		alert("Please Capture PDC/ECS. ");
		 		document.getElementById("savenfor").removeAttribute("disabled");
		 		return false;
		 	}
		 	
		 	
             }

			 if(indGuarantee == 1){
			 	
			 	if(installmenttt != 1){
			 		alert("Please Capture PDC/ECS. ");
			 		document.getElementById("savenfor").removeAttribute("disabled");
			 		return false;
			 	}
			 	
			 	
			 }

				 if(insurance == 1){
				 	
				 	if(installmenttt != 1){
				 		alert("Please Capture PDC/ECS. ");
				 		document.getElementById("savenfor").removeAttribute("disabled");
				 		return false;
				 	}
				 	
				 	
				 }
				 
            	
	
			   	if((document.getElementById('lbxLoanNoHID').value =='')){
			   		alert(alert1);
			   		document.getElementById("savenfor").removeAttribute("disabled");
			   		return false;
			   	}
			   else{
			  // alert("checked"+checked);
			 // alert("checkedDate"+checkedDate);
			 // alert("id"+id);
			   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savenForPDCInstrument&id="+id+"&checkedDate="+checkedDate+"&checked="+checked;
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			   	}
			   }
		
		 function  changeRecoveryType()
		 {
			// alert("changeRecoveryType");
			 	var installmentType = document.getElementById("installmentType").value;
			 	var recoveryType = document.getElementById("recoveryType").value;
			   	var princAm =	 document.getElementsByName("principalAmount"); 
			   	var installmentAmount =	 document.getElementsByName("installmentAmount"); 
				var recoveryPer =	 document.getElementsByName("recoveryPer"); 
			   	if(recoveryType=='F')
			   	{
			   		if(installmentType=='P'||installmentType=='Q' || installmentType=='S' || installmentType=='J')
			   		{
			   			for(var i =1;i<=princAm.length;i++)
			   			{
			   				document.getElementById("principalAmount"+i).removeAttribute("readonly","true");
			   				document.getElementById("principalAmount"+i).setAttribute("class","text");
			   				document.getElementById("installmentAmount"+i).value=0;
			   				document.getElementById("installmentAmount"+i).setAttribute("readonly","true");
			   				document.getElementById("recoveryPercen"+i).value=0;
			   				document.getElementById("recoveryPercen"+i).setAttribute("readonly","true");
			   			}
			   		}
			   		else if(installmentType=='I'){
			   			return true;
			   		}
			   		else
			   		{
			   			for(var i=1;i<=princAm.length;i++)
			   			{
			   				// alert("changeRecoveryType "+"principalAmount"+i);
			   				document.getElementById("principalAmount"+i).value=0;
			   				document.getElementById("principalAmount"+i).setAttribute("readonly","true");
			   				// alert(document.getElementById("installmentAmount"+i));
			   				document.getElementById("installmentAmount"+i).removeAttribute("readonly","true");
			   				document.getElementById("installmentAmount"+i).setAttribute("class","text");
			   			    document.getElementById("recoveryPercen"+i).value=0;
			   				document.getElementById("recoveryPercen"+i).setAttribute("readonly","true");
			   				
			   			}
			   		}
			   			
			   	}
			   	else
			   	{
			   		
			   		if((installmentType=='E'||installmentType=='P')&& recoveryType=='P')
			   		document.getElementById('recoveryPercen1').value="100";
			   			for(var i=1;i<=princAm.length;i++)
			   			{
			   				document.getElementById("principalAmount"+i).value=0;
			   				document.getElementById("principalAmount"+i).setAttribute("readonly","true");
			   				document.getElementById("principalAmount"+i).setAttribute("class","readonly");
			   				document.getElementById("installmentAmount"+i).value=0;
			   				document.getElementById("installmentAmount"+i).setAttribute("readonly","true");
			   				document.getElementById("installmentAmount"+i).setAttribute("class","readonly");
							document.getElementById("recoveryPercen"+i).removeAttribute("readonly","true");
			   				document.getElementById("recoveryPercen"+i).setAttribute("class","text");
			   			}
			   		
			   	}
		 }
		 
function saveNotepadDataInCm(alert1)
{
	// alert("Save");
	if(validateNotepadDynaValidatorForm(document.getElementById("notepadForm")))
	{
		if(document.getElementById("followUp").value=='N')
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("notepadForm").action = contextPath+"/notepadInCMProcessAction.do?method=saveNotepadDataInCM";
			document.getElementById("notepadForm").submit();
		}
		else if(document.getElementById("followUp").value=='Y')
		{
			if(cpNotepadValidate())
			{
				var formatD=document.getElementById("formatD").value;
				var meetingDate = document.getElementById("date").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(meetingDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				if (dt2<dt1)
				{
					alert(alert1);
					return false;
				}
				var contextPath =document.getElementById('contextPath').value ;
				document.getElementById("notepadForm").action = contextPath+"/notepadInCMProcessAction.do?method=saveNotepadDataInCM";
				document.getElementById("notepadForm").submit();
			}
		}
		return true;
	}
}

function searchLoanForNotePad()
{
	DisButClass.prototype.DisButMethod();
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	var contextPath= document.getElementById("contextPath").value;
	// alert("searchLoanForNotePad loanNo "+loanNo);
	if(loanNo!=''||customerName!=''||product!=''||scheme!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("CreditFormInNotePad").action=contextPath+"/searchNotePadBehindInCM.do";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditFormInNotePad").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("CreditFormInNotePad").action=contextPath+"/searchNotePadBehindInCM.do";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditFormInNotePad").submit();
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
 function validateLaonInitForm(){
	 var msg1='',msg2='',msg3='',msg4='';
	 var loanAmount=document.getElementById("loanAmount").value;
	 var agreementDate=document.getElementById("agreementDate").value;
	 var repayEffectiveDate="";
	 var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
	 var remarks=document.getElementById("remarks").value;
	 var count=0;
	 if(oneDealOneLoan=='Y'){
		 repayEffectiveDate=document.getElementById("repayEffectiveDateOneLoan").value;
	 }else{
		 repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
	 }
	 if(loanAmount==''){
		 msg1='* Loan Amount is required \n';
		 count=1;
	 }else{
		 msg1='';
		}
	 if(agreementDate==''){
		 msg2='* Agreement Date is required \n';
		 count=1;
	 }else{
		 msg2='';
	 }
	 if(repayEffectiveDate==''){
		 msg3='* Repay Effective Date is required \n';
		 count=1;
	 }else{
		 msg3='';
		}
	 if(remarks==''){
		 msg4='* Remark is required \n';
		 count=1;
	 }else{
		 msg4='';
	 }
	 
	 
	 if(count==1){
		 alert(msg1 +msg2 +msg3 +msg4);
		 return false;
	 }else{
		 return true; 
	 }
 }

 function viewReceivablePartPrePayment(alert1) 
	{	
		var loanId=document.getElementById('lbxLoanNoHID').value;
		
		if(loanId=="")
			{
				alert(alert1);
				document.getElementById("viewRec").removeAttribute("disabled");
				return false;
			}
		else
		{
			var loanId=document.getElementById('lbxLoanNoHID').value;
			var basePath=document.getElementById("contextPath").value;
			window.open(basePath+'/partPrePaymentViewReceivable.do?method=ViewReceivablePartPrePayment&loanId='+loanId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			return true;
		}
	}
 
 function deleteassetverificationid(){
		var basePath=document.getElementById("contextPath").value;
	 var ch=document.getElementsByName('chk');
   	 var assetID1 = document.getElementsByName('assetID1');
   	var loanid=document.getElementById('loanidval').value;
    var flag=0;
   	var loanIDList="";
   	var loanAccNoList="";
   	var assetIDList="";
   	var assetDescriptionList ="";
   	var loanNolist="";
	if((document.getElementById("appraiser").value)=="Internal"){
		var appraisertype='I';
	}else{
		var appraisertype='EA';
	}
	
	var contextPath=document.getElementById("contextPath").value;
	if(document.getElementById("lbxUserId").value!=""){
		appraiserid=document.getElementById("lbxUserId").value;
	}else{
		appraiserid=document.getElementById("lbxextApprHID").value
	}
    for(i=0;i<ch.length;i++){
   			 
   			 if(ch[i].checked==true){
   				
   				assetIDList = assetIDList + assetID1[i].value + "/";
   				flag=1;
    		}
   				 
   		 }
 
   	 if(flag==0)
   	 {
   		 alert("Please select One record");
   		 document.getElementById("assetButton").removeAttribute("disabled","true");
   		 return false;
   	 }
   	var confrm = confirm("Do you want to continue ?");
   	 if(confrm){
   	 
   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInitSearch.do?method=deleteasset&loanid="+loanid+"&assetIDList="+assetIDList+"&appraiserid="+appraiserid+"&apprtype="+appraisertype;

   	document.getElementById("sourcingForm").submit();
	     return true;
	     
   	 }else{
   		document.getElementById("assetButton").removeAttribute("disabled","true");
   		 return false;
   	 }
 }

 function viewPayableLoanViewer(alert1) 
{	
	   
	   var id=document.getElementsByName("radioId");
	  var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
	   var loanIDMain ='';			 
	   var contextPath=document.getElementById("contextPath").value;
	   if(id.length>0){
	   for(var i=0; i< id.length ; i++)
	   {				   
		   if(id[i].checked == true){					   
			   loanIDMain = loanid[i].value;		
			   curWin = 0;
				otherWindows = new Array();
					
				var basePath=document.getElementById("contextPath").value;
				otherWindows[curWin++] =window.open(basePath+'/viewPayableEarlyClousreAction.do?&loanId='+loanIDMain,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
				return true;
		   }
		   
	   }
	   }else{
		   alert(alert1);
		   return false;
	   }
}
 function viewReceivableLoanViewer(alert1) 
{	
	 var id=document.getElementsByName("radioId");
	  var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
	   var loanIDMain ='';			 
	   var contextPath=document.getElementById("contextPath").value;	
	   if(id.length>0){
	   for(var i=0; i< id.length ; i++)
	   {				   
		   if(id[i].checked == true){					   
			   loanIDMain = loanid[i].value;		
			   curWin = 0;
				otherWindows = new Array();
				var basePath=document.getElementById("contextPath").value;
			otherWindows[curWin++] =window.open(basePath+'/viewReceivableEarlyClousreAction.do?&loanId='+loanIDMain,'viewReceivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			return true;
	   }
	   }
	   }else{
		   alert(alert1);
		   return false;
	   }
}
 
 function newSearchDeviationLoan(stage)
 {
 	DisButClass.prototype.DisButMethod();
 	var dealNo=document.getElementById("dealNo").value;
 	var loanNo=document.getElementById("loanNo").value;
 	var agrementDate=document.getElementById("agrementDate").value;
 	var customerName=document.getElementById("customerName").value;
 	var product=document.getElementById("product").value;
 	var scheme=document.getElementById("scheme").value;
 	//var reportingToUserId=document.getElementById("reportingToUserId").value;
 	var contextPath= document.getElementById("contextPath").value;
 	
 	
 	if(dealNo!=''||loanNo!=''||agrementDate!=''||customerName!=''||product!=''||scheme!='')
 	{
 		if(customerName!='' && customerName.length>=3)
 		{
 			document.getElementById("CreditForm").action=contextPath+"/creditListAction.do?method=searchLinkForDeviationAuthor&stage="+stage;
 			document.getElementById("processingImage").style.display = '';
 			document.getElementById("CreditForm").submit();
 			return true;
 		}
 		else if(customerName=='')
 		{
 			document.getElementById("CreditForm").action=contextPath+"/creditListAction.do?method=searchLinkForDeviationAuthor&stage="+stage;
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


 //ravi start
 
 function addRowOtherChargePlan()
 {
		
		// alert("addROW");
		//if(recoveryType=='F')
	   	//{
	   		//if(installmentType=='P'||installmentType=='Q')
	   		//{
	 			   				
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
	   				element1.value = "0";
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
	   				
//		   			var element7 = document.createElement("input");		
//		   			element7.type = "hidden";
//		   			element7.name = "lbxCharge";
//		   			element7.id = "lbxCharge";	
		   			
		   			var element8 = document.createElement("input");		
	   				element8.type = "hidden";
	   				element8.name = "chargehiddenFld";
	   				element8.id = "chargehiddenFld"+psize;	
		   			newdiv.innerHTML ='<input type="button" value="" name="chargeButton" id="chargeButton" onclick="openLOVCommon(453,\'otherChargesPlanForm\',\'chargeDesc'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'chargehiddenFld'+psize+'\');closeLovCallonLov(\'chargehiddenFld'+psize+'\');" class="lovbutton"/>';
		   			//<html:button property="chargeButton" styleId="chargeButton" onclick="openLOVCommon(159,'otherChargesPlanForm','chargeCode','businessPartnerTypeDesc|loanAccountNo','lbxCharge', 'businessPartnerType|lbxLoanNoHID','Select BP Type LOV First|Select Loan Account LOV First','','chargeAmount');;closeLovCallonLov('businessPartnerTypeDesc');" value=" " styleClass="lovbutton"></html:button>
		   			
		   			//cell6.appendChild(element7);
		   			cell6.appendChild(element8);
		   		    cell6.appendChild(newdiv);
		   		    
		   		 psize++;
		 		document.getElementById("psize").value=psize;
	   			   		    
	}
 
 
 function saveOtherCharges()
 {
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
 	  var chkBx= document.getElementsByName("chk");
 	 
 	  var toInstallment= document.getElementsByName("toInstall");
      var type= document.getElementsByName("type");
 	  var amount= document.getElementsByName("amount");
 	  var chargeDesc= document.getElementsByName("chargeDesc");
 	  var val=0;
 	  for(var j=0;j<chkBx.length;j++)
	  {
 		 if(fromInstall[j].value!='' && chkBx[j].checked==false)
 		 {
 			alert("Please Check CheckBok.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
 		 }
 		 if(chkBx[j].checked == true)
		 {
 			val=val+1;
 	 		if ((fromInstall[j].value=="")||(toInstallment[j].value=="")||(type[j].value=="" ) ||(amount[j].value=="")|| (chargeDesc[j].value==""))
 			 {
 				 alert("Please fill all the fields ");
 				 document.getElementById("save").removeAttribute("disabled","true");
 				 DisButClass.prototype.EnbButMethod();
 				 return false;
 			 }
 	 		
 	 		if(parseInt(fromInstall[j].value)>parseInt(toInstallment[j].value))
 			{	    	
 	 			alert("From Installment should be less than or equal to To Installment.");
 				document.getElementById("save").removeAttribute("disabled","true");
 				DisButClass.prototype.EnbButMethod();
 				return false;
 			}
 	 		if(parseInt(fromInstall[j].value)==0){
 		 		alert("From Installment should be greater than zero");
 		 		document.getElementById("save").removeAttribute("disabled","true");
 		 		DisButClass.prototype.EnbButMethod();
 		 		return false;
 		 	}
 	 		if(parseInt(toInstallment[j].value)> parseInt(totalInstallment))
 	 		{
			 		alert("To Installment should be less than or equal to Total Installment");
			 		document.getElementById("save").removeAttribute("disabled","true");
			 		DisButClass.prototype.EnbButMethod();
			 		return false;
			} 	 		
 	 		for(var i=j+1;i<chkBx.length;i++)
 	 		{
 	 			if(chkBx[i].checked == true)
 	 			{
 	 				if(fromInstall.length>=i)
 	 	 	 		{ 	 	 	 		
 	 	 	 			if(parseInt(fromInstall[i].value)<=parseInt(toInstallment[j].value))
 	 	 	 			{	 	 	 			
 	 	 		 			alert("Next From Installment should be greater than previous To Installment.");
 	 	 					document.getElementById("save").removeAttribute("disabled","true");
 	 	 					DisButClass.prototype.EnbButMethod();
 	 	 					return false;
 	 	 		 		}
 	 	 	 		}
 	 			}
 	 		}
 	 	}
	  }
 	  
// 	  for(var j=0;j<fromInstall.length;j++)
// 	  {
// 		if ((fromInstall[j].value=="")||(toInstallment[j].value=="")||(type[j].value=="" ) ||(amount[j].value=="")|| (chargeDesc[j].value==""))
//		 {
//			 alert("Please fill all the fields ");
//			 document.getElementById("save").removeAttribute("disabled","true");
//			 DisButClass.prototype.EnbButMethod();
//			 return false;
//		 }
// 		
// 		if(parseInt(fromInstall[j].value)>parseInt(toInstallment[j].value))
//		{	    	
// 			alert("From Installment should be less than or equal to To Installment.");
//			document.getElementById("save").removeAttribute("disabled","true");
//			DisButClass.prototype.EnbButMethod();
//			return false;
//		}
// 		
// 		if (fromInstall.length>j+1)
// 		{
// 		
// 			if(parseInt(fromInstall[j+1].value)<=parseInt(toInstallment[j].value)){
// 			
//	 			alert("Next From Installment should be greater than previous To Installment.");
//				document.getElementById("save").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//	 		}
// 		}
// 		if(parseInt(fromInstall[j].value)==0){
//	 		alert("From Installment should be greater than zero");
//	 		document.getElementById("save").removeAttribute("disabled","true");
//	 		DisButClass.prototype.EnbButMethod();
//	 		return false;
//	 	}
// 		 var length=((fromInstall.length)-1);
// 		if(j==length)
// 		{	
//	 		if(parseInt(toInstallment[j].value)> parseInt(totalInstallment)){
//		 		alert("To Installment should be less than or equal to Total Installment");
//		 		document.getElementById("save").removeAttribute("disabled","true");
//		 		DisButClass.prototype.EnbButMethod();
//		 		return false;
//		 	}
// 		}
// 	  }
 	 
 	
 	document.getElementById("otherChargesPlanForm").action = contextPath+"/saveOtherChargesPlan.do?method=saveOtherChargesPlan&installmentType="+installmentType+"&checkedVal="+val;
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
 
 function removeOtherChargeRow() 
 {
	 	var chkBx= document.getElementsByName("chk");
	 	var markRow='';
	 	var mark='';
	 	for(var j=0;j<chkBx.length;j++)
		{
	 		 if(chkBx[j].checked==true)
	 		 {
	 			markRow=markRow+",'"+chkBx[j].value+"'";
	 			mark='M';
	 		 }
		}
	 	if(mark!='')
	 	{
	 		markRow=markRow.substring(1);	
	 		var contextPath=document.getElementById("contextPath").value;
	 		document.getElementById("otherChargesPlanForm").action = contextPath+"/saveOtherChargesPlan.do?method=deleteOtherChargesPlan&checkedRow="+markRow;
	 		document.getElementById("processingImage").style.display = '';
	 		document.getElementById("otherChargesPlanForm").submit();
	 		return true;
	 	}	 	
 }

 //ravi end

 
 //Manisha Code for Copy Remark to All Textbox Starts Here
 
 function copyToAll()

 { 	 
 	var table = document.getElementById("table1");	
	var rowCount = table.rows.length;

	var status=false;
	 	if(rowCount<2){
		//alert("Please Select Checkbox to save");
		msg1="There is nothing to copy \n";
		status=true;
	}else{
		
		for(var i=1;i<rowCount;i++){
	 	document.getElementById('holdReason'+i).value=document.getElementById('holdReason1').value;                	
 	   status=true;
		}		
		
 }
 }
  //Manisha Code for Copy Remark to All Textbox Ends Here
 


 function removeRowDisbSche() {
		//alert("removeallocationRow");
		 var count=0;
	    try {
	    var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	  	var psize=document.getElementById("psize").value;
		if(psize==""){
			psize=rowCount;
		}
		document.getElementById("psize").value=psize;
	    //alert("rowCount:--"+rowCount);
		if(rowCount==2){
			
			alert("One row is mandatory");
			
		}
		else
		{
	     for(var j=1;j<rowCount;j++){
	    
	       var row1 = table.rows[j];
	      //  var chkbox1 = row1.cells[0].childNodes[0];
	       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
	     
	        if(chkbox1!=undefined ||chkbox1!=null) {
	    	 if(null != chkbox1 && true == chkbox1.checked) {
			 count=count+1;
	    	 }
			 }
	    }
		
	if(count==0){
	alert("Please Select at least one row to delete");
	}else{
	    for(var i=1; i<rowCount; i++) {
	      var row = table.rows[i];
	  //  var chkbox = row.cells[0].childNodes[0];
	      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
	        if(chkbox!=undefined ||chkbox!=null){
	        if(null != chkbox && true == chkbox.checked) {
	     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
	       if(document.getElementById("chk"+i).checked==true){*/
	            table.deleteRow(i);
	            rowCount--;
	            i--;
	        }
	        }
	   //   }
	     }
	     }
	  }	
	}catch(e) {
	        alert(e);
	    }
	   
	}
 /*

 function removeRowDisbSche() {   
	 var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	 var chk =document.getElementsByName("chk"); 
	 var count=0;
	 for(var j=1;j<rowCount;j++){
		 if(document.getElementById("chk"+j).checked==true){
			 count=count+1;
			 }
	 }
	
if(count==0){
	alert("Please Select at least one row to delete");
}else{


 
 for(var i=1; i<rowCount; i++) {
 	var row = table.rows[i];
     var chkbox = row.cells[0].childNodes[0];
      if(null != chkbox && true == chkbox.checked) 
     	{
     	 table.deleteRow(i);
         rowCount--;
         i--;
     }               
 }
}
}
*/
function checkDateAtDisbSch(txtDate)
{
    // define date string to test
    var txtDateValue = document.getElementById(txtDate).value;		    
    var formatD=document.getElementById("formatD").value;// must be take this format from jsp
    if (isDate(txtDateValue,formatD) || txtDateValue == null || txtDateValue == '') 
    {
       return true;
    }
    else 
    {
        alert('Invalid date format!');
        document.getElementById(txtDate).value='';
        return false;
    }
}

function addROWAtDisbSchCM(){
	


	// alert("addROW");

	var table = document.getElementById("gridTable");

		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		
		var psize= document.getElementById("psize").value;

		if(psize==""){
			psize=rowCount;
		}
		
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
		element2.name = "noOfDisbursal";
		element2.id = "noOfDisbursal"+psize;
		element2.setAttribute('class','text');
		element2.setAttribute('className','text' );
		//element2.setAttribute('value',psize);
		//element2.setAttribute('readOnly','true' );
		cell2.appendChild(element2);
		
		var cell3 = row.insertCell(2);
		var element3 = document.createElement("input");
		element3.type = "text";
		element3.name = "dateOfDisbursal";
		element3.id = "dateOfDisbursal"+psize;
		element3.setAttribute('class','text');
		element3.setAttribute('className','text' );
		element3.onchange= function(){
			var e = psize -1;
			checkDateAtDisbSch("dateOfDisbursal"+e);
		};
		cell3.appendChild(element3);
		
		var cell4 = row.insertCell(3);
		var element4 = document.createElement("input");
		element4.type = "text";
		element4.name = "descOfDisbursal";
		element4.id = "descOfDisbursal"+psize;
		element4.setAttribute('class','text');
		element4.setAttribute('className','text' );
		element4.setAttribute('maxlength','100');	
		cell4.appendChild(element4);
		
		
		var cell5 = row.insertCell(4);
		var element5 = document.createElement("input");
		element5.type = "text";
		element5.name = "amountOfDisbursal";
		element5.id = "amountOfDisbursal"+psize;
		element5.setAttribute('class','text');
		element5.setAttribute('className','text' );
	
		element5.onblur= function(){
			formatNumber(document.getElementById("amountOfDisbursal"+psize).value,"amountOfDisbursal"+psize);
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
			checkNumber(document.getElementById("amountOfDisbursal"+psize).value, this.event, 18,"amountOfDisbursal"+psize);
		};
		
		
		cell5.appendChild(element5);
		psize++;
		document.getElementById("psize").value=psize;

   
}

// Code Start by Anil for File Tracking

function dealViewerForFileTrack()
{
	   otherWindows = new Array();
	   curWin = 0;
	   var dealid=document.getElementById("lbxDealNo").value;
	   var dealIdMain ='';
	   var contextPath=document.getElementById("contextPath").value;
	   var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+dealid+"&status=UWA&fromCM=cm";
	   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	   otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
	   mywindow.moveTo(800,300);
	   if (window.focus) 
	   {
			mywindow.focus();
			return false;
	   }
	   return true;
}


function loanViewerPresentationForFileTracking()
{
   otherWindows = new Array();
   curWin = 0;
   var loanid=document.getElementById("lbxLoanNoHID").value;	
   var loanIDMain ='';			 
   var contextPath=document.getElementById("contextPath").value;			   
   var url = contextPath+"/searchLoanDealBehindAction.do?loanId="+loanid+"&status=UWA&fromCM=cm";
   mywindow =window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes').focus();
   otherWindows[curWin++]=window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes');
   mywindow.moveTo(800,300);
   if (window.focus) 
   {
		mywindow.focus();
		return false;
   }
   return true;		   	
}
function showHideImd()
{
	
	var contextPath=document.getElementById("contextPath").value;	
	var txnType=document.getElementById("txnType").value;
	location.href=contextPath+"/changeScreenChequeStatusAction.do?method=changeScreenUpdateChequeStatus&txnType="+txnType;
	
}
// End By Anil

//method added by manish
function uploadRateReview()
{
	//alert("uploadBalanceSheet");
	if(validateDocUpload())
	{
		var contextPath =document.getElementById('contextPath').value ;
		//alert("contextPath "+contextPath);
		document.getElementById("reteReview").action =contextPath+"/rateReviewUpload.do?method=rateReviewUpload";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("reteReview").submit();
	}
}
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
	    return false; 
	}
	var fup = document.getElementById('docFile');
	var file_Name = fup.value;
	var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
	if(ext == "xls" || ext == "XLS" || ext == "xlsx" || ext == "XLSX")
		return true;
	else
	{
		alert("Upload excel file only.");
		fup.value='';
		fup.focus();
		return false;
	}
}
function generateRateReviewErrorLog()
{
	//alert("generaterateReviewErrorLog");
	var contextPath =document.getElementById('contextPath').value ;
	//alert("contextPath "+contextPath);
	document.getElementById("reteReview").action =contextPath+"/rateReviewUpload.do?method=generateErrorLog";
	//alert("ok");
	document.getElementById("reteReview").submit();
}

function assetInsuranceViewer()
{		
	var loanid=document.getElementById('lbxLoanNoHIDmain').value;		
	var contextPath=document.getElementById("contextPath").value;
							
	var url= contextPath+"/assetProcessAction.do?method=onAssetInsuranceViewer&loanId="+loanid;
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) {
		mywindow.focus();
		return false;
		}
		return true;
}

function approvedOrDraft()
{
	var searchStatusPDD=document.getElementById('searchStatusPDD').value;
	if(searchStatusPDD=="P")
	{
		document.getElementById("dealButtonForDraftStatus").style.display='';
		document.getElementById("loanButtonForDraftStatus").style.display='';
		document.getElementById("dealButton").style.display='none';
		document.getElementById("loanButton").style.display='none';
    	document.getElementById("dealButton").setAttribute("disabled",true);
    	document.getElementById("loanButton").setAttribute("disabled",true);
    	document.getElementById("dealButtonForDraftStatus").removeAttribute("disabled",true);
    	document.getElementById("loanButtonForDraftStatus").removeAttribute("disabled",true);
	}
	else
	{
		document.getElementById("dealButtonForDraftStatus").style.display='none';
		document.getElementById("loanButtonForDraftStatus").style.display='none';
		document.getElementById("dealButton").style.display='';
		document.getElementById("loanButton").style.display='';
    	document.getElementById("dealButtonForDraftStatus").setAttribute("disabled",true);
    	document.getElementById("loanButtonForDraftStatus").setAttribute("disabled",true);
    	document.getElementById("dealButton").removeAttribute("disabled",true);
    	document.getElementById("loanButton").removeAttribute("disabled",true);
	}
		
	   
}
	function SearchLoanForRepaymentService()
	{
		DisButClass.prototype.DisButMethod();
		
//		var dealNo=document.getElementById("dealNo").value;
		var loanNo=document.getElementById("loanNo").value;
//		var agrementDate=document.getElementById("agrementDate").value;
		var customerName=document.getElementById("customerName").value;
		var product=document.getElementById("product").value;
		var scheme=document.getElementById("scheme").value;
//		var reportingToUserId=document.getElementById("reportingToUserId").value;
		var contextPath= document.getElementById("contextPath").value;
		// alert("newSearchLoan"+stage);
		var allBranches=document.getElementById("allBranches").checked;
		var selectedBranch=document.getElementById("lbxBranchId").value;
		if(allBranches==true && selectedBranch!='')
		{
			alert("Select either All Branch or Selective Branch.");
			document.getElementById("button").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}

		else if(loanNo!=''||customerName!=''||product!=''||scheme!='' || allBranches!=false || selectedBranch!='')
			{
				if(customerName!='' && customerName.length>=3)
				{
					document.getElementById("RepaymentServiceForm").action=contextPath+"/repaymentServiceAction.do?method=searchLinkForRepaymentService";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("RepaymentServiceForm").submit();
					return true;
				}
				else if(customerName=='')
				{
					document.getElementById("RepaymentServiceForm").action=contextPath+"/repaymentServiceAction.do?method=searchLinkForRepaymentService";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("RepaymentServiceForm").submit();
					return true;
				}
				else
				{
					alert("Please Enter atleast 3 characters of Customer Name ");
					document.getElementById("button").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
			}
			else
			{
				alert("Please Enter atleast one field or select all branches.");
				document.getElementById("button").removeAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		
	}
	
	
	
	function onRSPSaveInstal()
	{
		DisButClass.prototype.DisButMethod();
		
		var contextPath=document.getElementById("contextPath").value;
		var recoveryType=document.getElementById("recoveryType").value;
		var loanAmount=document.getElementById("loanAmount").value;
		var formatD=document.getElementById("formatD").value;
		var repayEffDateObj="";
		var dueDateObj="";
		// var fromInstallment=document.getElementById("fromInstallment").value;
		// var toInstallment=document.getElementById("toInstallment").value;
		var installmentType=document.getElementById("installmentType").value;
		var totalInstallment=document.getElementById("totalInstallment").value;
		var repayEffDate=document.getElementById("repayeffdate").value;
		repayEffDateObj=getDateObject(repayEffDate,formatD.substring(2, 3));
		var gridTable = document.getElementById('gridTable');
		var tableLength = gridTable.rows.length-1;
		
		  var sum=0;
		  var psum=0;
		  var isum=0;
		  
		for(var i=1;i<=tableLength;i++)
		{
			var fromInstallment1=document.getElementById("fromInstallment1").value;
			var fromInstallment=document.getElementById("fromInstallment"+i).value;
		
			var toInstallment=document.getElementById("toInstallment"+i).value;
		    
			var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
			
			var principalAmount=document.getElementById("principalAmount"+i).value;
			
			var installmentAmount=document.getElementById("installmentAmount"+i).value;
			if(installmentType=='I')
			{
			var dueDate=document.getElementById("dueDate"+i).value;
			dueDateObj=getDateObject(dueDate,formatD.substring(2, 3));
			}
			 if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")&& installmentType!='I')
			 {
				 alert("Please fill all the fields ");
				 document.getElementById("save").removeAttribute("disabled","true");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(recoveryType=='F' && installmentType=='I' && (fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount=="")||(dueDate==""))
			 {
				 alert("Please fill all the fields ");
				 document.getElementById("save").removeAttribute("disabled","true");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 
			 if(fromInstallment==0){
				 alert("From Installment cannot be Zero");
				 document.getElementById("save").removeAttribute("disabled","true");
				 DisButClass.prototype.EnbButMethod();
				 return false;
				      }	
			 
			 
			 if(fromInstallment1!=1){
				 alert("First Installment should start from 1");
				 document.getElementById("save").removeAttribute("disabled","true");
				 DisButClass.prototype.EnbButMethod();
				 return false;
				      }	
			 
			 if(installmentType=='I')
				 {
				 	if(dueDateObj<repayEffDateObj)
					 {
					 alert("Due Date cannot be less than RepayEffective Date.");
					 document.getElementById("save").removeAttribute("disabled","true");
					 DisButClass.prototype.EnbButMethod();
					 return false;
					 }
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
				
				alert("Next from Installment should be grtr than previous to Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
		{
			
			alert("Next from Installment should be equal to previous to Installment");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		 if(installmentType=='I')
		 {
		
			 var dueDateOne= document.getElementById("dueDate"+i).value;
			 var dueDateTwo= document.getElementById("dueDate"+k).value;
			 var day1= dueDateOne.substring(0,2);
				var day2= dueDateTwo.substring(0,2);
				var month1=dueDateOne.substring(3,5);
				var month2=dueDateTwo.substring(3,5);
				var year1=dueDateOne.substring(6);
				var year2=dueDateTwo.substring(6);

			 if(parseFloat(year1)>parseFloat(year2))
			 				{
			 					alert("Next due date should be greater than previous due date.");
			 					document.getElementById("save").removeAttribute("disabled","true");
			 					DisButClass.prototype.EnbButMethod();
			 					return false;
			 				}
			 				else
			 				{
			 					if(parseFloat(year1)==parseFloat(year2))
			 					if(parseFloat(month1)>parseFloat(month2))
			 					{
			 						alert("Next due date should be greater than previous due date.");
			 						document.getElementById("save").removeAttribute("disabled","true");
			 						DisButClass.prototype.EnbButMethod();
			 						return false;
			 					}
			 					else
			 					{
			 						if(parseFloat(month1)==parseFloat(month2))
			 						if(parseFloat(day1)>=parseFloat(day2))
			 						{
			 							alert("Next due date should be greater than previous due date.");
			 							document.getElementById("save").removeAttribute("disabled","true");
			 							DisButClass.prototype.EnbButMethod();
			 							return false;
			 						}
			 					}
			 				} 

		 }

			}
		
		}
		 //alert("Loan Amount: "+loanAmount+" isum: "+isum);
		var maxDueDateObj="";
		var maxDateObj="";
		var lastToInstall=document.getElementById("toInstallment"+tableLength).value;
		 if(installmentType=='I')
		 {
		var maxDueDate=document.getElementById("dueDate"+tableLength).value;
		maxDueDateObj=getDateObject(maxDueDate,formatD.substring(2, 3));
		var maxDate=document.getElementById("maxDate").value;
		maxDateObj=getDateObject(maxDate,formatD.substring(2, 3));
		if(maxDueDateObj>maxDateObj)
			{
			alert('Last Due date cannot be greater than repayeffective date + tenure ');
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		 }
		if(parseInt(lastToInstall)> parseInt(totalInstallment)){
			alert("No of  Installment should be equal to  Total Installment");
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
			alert("Total recovery Percentage should be  equal to 100 %");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(((psum>removeComma(loanAmount)||psum<removeComma(loanAmount)) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'||installmentType=='I' || installmentType=='S'))
		{
			alert("Principal Amount should be equal to loan amount: "+loanAmount);
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		
		document.getElementById("RspInstallmentPlanForm").action = contextPath+"/installmentPlanProcess.do?method=saveRSPInstallmentPlan&installmentType="+installmentType;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("RspInstallmentPlanForm").submit();
	     return true;
	}


	function viewRspRepaymentSchedule()
	{	
		
		var fundingType="";
		if(document.getElementById("hiddenfundingType")==null || document.getElementById("hiddenfundingType")==undefined ){
			fundingType="";		
		}
		else{
			fundingType=document.getElementById("hiddenfundingType").value;
		}
		if(fundingType=="CP" || fundingType=="CB")
			{
		var id=document.getElementsByName("radioId");
		var loanid=document.getElementsByName("lbxLoanNoHIDmain");
		var loanIDMain ='';
		var contextPath=document.getElementById("contextPath").value;
		for(var i=0; i< id.length ; i++)
		{				   
			if(id[i].checked == true){
				loanIDMain = loanid[i].value;
			}				   
		}			
		var url= contextPath+"/repaymentServiceAction.do?method=viewGenerateRepaymentSchedule&loanId="+loanIDMain;
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
			}
		else {
			return false;
		}
	}
	//Rohit Changes Strats for ACH
	function achStatusForLoanViewer()
	{
		   otherWindows = new Array();
		   curWin = 0;
		   var id=document.getElementsByName("radioId");
		   var dealid=document.getElementsByName("lbxLoanNoHIDmain");
		   var dealIdMain ='';
		   var contextPath=document.getElementById("contextPath").value;

		   for(var i=0; i< id.length ; i++)
		   {

			   if(id[i].checked == true){
				   dealIdMain = dealid[i].value;

			   }				   
		   }
		   var url=contextPath+"/achCapturingAction.do?method=openModifyACHCapturing&dealId="+dealIdMain+"&status=UWA";
		   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			mywindow.moveTo(800,300);
			if (window.focus) {
				mywindow.focus();
				return false;
			}
		   return true;
	}
	//Rohit End
//start:added by indrajeet
function fnSaveSblGbl(){
	var comments=document.getElementById("comments").value;
	var decision=document.getElementById("decision").value;
	if(comments==''){
		alert('comments is required');
	}if(decision==''){
		alert('decision is required');
	}if(comments !="" && decision != ""){
		 var contextPath=document.getElementById("contextPath").value;
	 document.getElementById("sblAuthorForm").action = contextPath+"/sblGblMakerSearch.do?method=savesblAuthorDetails";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("sblAuthorForm").submit();
	}
}function fnSearchAuthorSblGbl(){
	var loanNo=document.getElementById("lbxLoanNoHID").value;
	var disbursalId=document.getElementById("disbursalId").value;
	var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value
 	 if(lbxLoanNoHID=="")
		{
		 alert('loan no is required');
		 return false;
		}
    if(disbursalId=="")
		{
   	 alert('disbursal no is required');	
   	 return false;
		}
    if(loanNo !="" && disbursalId != ""){
		 var contextPath=document.getElementById("contextPath").value;
	 document.getElementById("CreditForm").action = contextPath+"/sblGblMakerSearch.do?method=sblGblAuthorSearch&lbxLoanNoHID="+loanNo+"&disbursalId="+disbursalId;
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("CreditForm").submit();
	}
}
//end:added by indrajeet
