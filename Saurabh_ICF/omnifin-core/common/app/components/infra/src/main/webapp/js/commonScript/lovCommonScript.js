function isInt(n) {
	   return n % 1 == 0;
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


function myMethod()
 {
	 //alert("my method");
	 //window.opener.document.getElementById("hname").value="12345";
	// alert("my method");
	// window.close();
 }
function totalRec()
{
	var loanId=window.opener.document.getElementById('lbxLoanNoHID').value ;
	var BPType=window.opener.document.getElementById('lbxBPType').value ;
	var contextPath =window.opener.document.getElementById('contextPath').value ;
	if(loanId !='' && BPType !='')
	{
		var address = contextPath+"/ajaxAction.do?method=getTotalReceivable";
		var data = "loanId="+loanId+"&BPType="+BPType;
		sendRequest(address,data);
		return true;
	}
	else
	{
		alert("Loan Account and Business Partner Type is required.");	
   	 	return false;
	}
}
function sendRequest(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethod(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethod(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			window.opener.document.getElementById('totalRecevable').value = trim(s1[0]);
			window.close();
	    }
	}
}
function calculateMaturityDateInDeal()
{
	
	var repaymentType = window.opener.document.getElementById('repaymentType').value;
	//alert(repaymentType);
	if(repaymentType=='N')
	{
		window.opener.document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(window.opener.document.getElementById('requestedLoanTenure').value); //or whatever offset
		var formatD=window.opener.document.getElementById("formatD").value;

		//alert(x);
		var currDate =   window.opener.document.getElementById('repayEffectiveDate').value;
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
		  
		  window.opener.document.getElementById('maturityDate1').value=ModDateStr;
	}
	
}

function getDefaultLoanDetail()
{
	  var scheme =window.opener.document.getElementById('lbxscheme').value ;
	  var contextPath =window.opener.document.getElementById('contextPath').value ;
	  if (scheme!= '')
	  {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getDefaultLoanDetail";
		var data = "scheme=" + scheme;
		send_BaseRate(address, data);
		return true;
	  }
	  else
	  {
    	 alert("Please Select List");	
    	 return false;
	  }
}

function send_BaseRate(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_BaseRate(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_BaseRate(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {	  
			//alert(trim(s1[12]));
			window.opener.document.getElementById('daysBasis').value = trim(s1[21]);
			if(trim(s1[21])!=''){
				var tenureInDay='';
				if(trim(s1[7])==''){
					tenureInDay=0;
				}
				if(trim(s1[21])=='A'){
				
				if(trim(s1[7])!=''){
				tenureInDay=trim(s1[7])*30.42;
				window.opener.document.getElementById('tenureInDay').value = tenureInDay.toFixed(0);
				}
				}
				else{
				tenureInDay=trim(s1[7])*30;
				window.opener.document.getElementById('tenureInDay').value = tenureInDay;
				}
			}
			
			
			if(trim(s1[12])=='I')
			{
			
				window.opener.document.getElementById("netLtvRow").style.display='';	
	 	    	window.opener.document.getElementById("noOfInstall").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("installments").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("cycleDate").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("frequency").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("installmentType").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("paymentMode").removeAttribute("disabled",true);
	 	    	window.opener.document.getElementById("instMode").removeAttribute("disabled",true);
	    	   	window.opener.document.getElementById('requestedLoanTenure').value=trim(s1[7]);
	    	   	window.opener.document.getElementById('marginPerc').value = trim(s1[0]);
	    	   	window.opener.document.getElementById('rateType').value = trim(s1[1]);
	    	   	window.opener.document.getElementById("assCost1").style.display='';
	    	   	window.opener.document.getElementById("assCost2").style.display='';
	    	   	window.opener.document.getElementById("noOfAsst1").style.display='';
	    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	    	   	
	    	   	window.opener.document.getElementById("marPer1").style.display='';
	    	   	window.opener.document.getElementById("marPer2").style.display='';
	    	   	window.opener.document.getElementById("marAmount1").style.display='';
	    	   	window.opener.document.getElementById("marAmount2").style.display='';
	    	   	window.opener.document.getElementById("noOfIns1").style.display='';
	    	   	window.opener.document.getElementById("noOfIns2").style.display='';
	    	   	window.opener.document.getElementById("frequnc1").style.display='';
	    	   	window.opener.document.getElementById("frequnc2").style.display='';
	    	   	window.opener.document.getElementById("installType1").style.display='';
	    	   	window.opener.document.getElementById("installType2").style.display='';
	    	   	window.opener.document.getElementById("insMod1").style.display='';
	    	   	window.opener.document.getElementById("insMod2").style.display='';
	    	   	window.opener.document.getElementById("repayMod1").style.display='';
	    	   	window.opener.document.getElementById("repayMod2").style.display='';
	    	   	window.opener.document.getElementById("noIns2").style.display='';
	    	   	window.opener.document.getElementById("noIns1").style.display='';
	    	   	window.opener.document.getElementById("noIns2").style.display='';
	    	   	window.opener.document.getElementById("cyclDate1").style.display='';
	    	   	window.opener.document.getElementById("cyclDate2").style.display='';
	    		window.opener.document.getElementById("nddheader").style.display='';
				window.opener.document.getElementById("ndd").style.display='';
				window.opener.document.getElementById("effDateHeader").style.display='';
				window.opener.document.getElementById("effDate").style.display='';
				/*window.opener.document.getElementById("interestCompoundingFrequency").removeAttribute("disabled",true);
				window.opener.document.getElementById("interestCalculationMethod").removeAttribute("disabled",true);
				window.opener.document.getElementById("interestFrequency").removeAttribute("disabled",true);*/
				window.opener.document.getElementById("insComFre1").style.display='';
				window.opener.document.getElementById("insComFre2").style.display='';
				window.opener.document.getElementById("insCalMet1").style.display='';
				window.opener.document.getElementById("insCalMet2").style.display='';
				window.opener.document.getElementById("insFre1").style.display='';
				window.opener.document.getElementById("insFre2").style.display='';
				
				
				//window.opener.document.getElementById("interestCalc").setAttribute("disabled",true);
				
    	    	
				window.opener.document.getElementById("int1").style.display='none';
				window.opener.document.getElementById("int2").style.display='none';
				
	    	   	if(trim(s1[1])=='E')	
	    	   	{
	    	   		window.opener.document.getElementById('baseRateType').value = trim(s1[3]);
	    	   		window.opener.document.getElementById('baseRate').value = trim(s1[4]);
	    	    	window.opener.document.getElementById('markUp').value=trim(s1[6])-trim(s1[4]);
	    	    	window.opener.document.getElementById('effectiveRate').value = trim(s1[6]);
	    	    	window.opener.document.getElementById('fixPriod').value = trim(s1[22]);
	    	    	
	    	    	window.opener.document.getElementById('minFlatRate').value = trim(s1[23]);
	    	    	window.opener.document.getElementById('maxFlatRate').value = trim(s1[24]);
	    	    	window.opener.document.getElementById('minEffectiveRate').value = trim(s1[25]);
	    	    	window.opener.document.getElementById('maxEffectiveRate').value = trim(s1[26]);
	    	    	window.opener.document.getElementById('defaultFlatRate').value = trim(s1[5]);
	    	    	window.opener.document.getElementById('defaultEffectiveRate').value = trim(s1[6]);
	    	    	
	    	    
	    	    	
	    	    	if(trim(s1[2])=='F')
	    	    	{
	    	    		window.opener.document.getElementById("type2").setAttribute("disabled",true);
						window.opener.document.getElementById("type1").removeAttribute("disabled",true);
						window.opener.document.getElementById("rateMethodType").value=trim(s1[2]);
						window.opener.document.getElementById('type1').checked=true;
	    	    	}
	    	    	else
	    	    	{
	    	    		window.opener.document.getElementById("type1").setAttribute("disabled",true);
						window.opener.document.getElementById("type2").removeAttribute("disabled",true);
						window.opener.document.getElementById("rateMethodType").value=trim(s1[2]);
	    	    		window.opener.document.getElementById('type2').checked=true;
	    	    	}
	    	   	}
	    	   	else
	    	   	{
	    	   		window.opener.document.getElementById("type1").removeAttribute("disabled",true);
	    	   		window.opener.document.getElementById("rateMethodType").value='F';
	    	   		window.opener.document.getElementById("type1").checked=true;
	    	   		window.opener.document.getElementById("type2").checked=false;
	    	   		window.opener.document.getElementById("baseRateType").value='';
	    	   		window.opener.document.getElementById("baseRate").value='';
	    	   		window.opener.document.getElementById("markUp").value='';
	    	   		window.opener.document.getElementById('fixPriod').value ='';
	    	   		window.opener.document.getElementById("type2").setAttribute("disabled",true);
	    	   		window.opener.document.getElementById("baseRateType").setAttribute("disabled",true);
	    	   		window.opener.document.getElementById("baseRate").setAttribute("disabled",true);
	    	   		window.opener.document.getElementById('effectiveRate').value = trim(s1[5]);
	    	   		window.opener.document.getElementById("fixPriod").setAttribute("readonly",true);
	    	   		
	    	   		window.opener.document.getElementById('minFlatRate').value = trim(s1[23]);
	    	    	window.opener.document.getElementById('maxFlatRate').value = trim(s1[24]);
	    	    	window.opener.document.getElementById('minEffectiveRate').value = trim(s1[25]);
	    	    	window.opener.document.getElementById('maxEffectiveRate').value = trim(s1[26]);
	    	    	
	    	    	window.opener.document.getElementById('defaultFlatRate').value = trim(s1[5]);
	    	    	window.opener.document.getElementById('defaultEffectiveRate').value = trim(s1[6]);
	    	   	}
	    	   	
	    	   	window.opener.document.getElementById('repaymentType').value = trim(s1[12]);
	    	   	window.opener.document.getElementById('showRepaymentType').value = "INSTALLMENT";
	    	   	window.opener.document.getElementById('frequency').value = trim(s1[8]);
	    	   	window.opener.document.getElementById('interestCompoundingFrequency').value = trim(s1[8]);
	    		window.opener.document.getElementById('interestFrequency').value = trim(s1[8]);
	    	   	calculateMaturityDateInDeal();
	    	   	var freqMonth;
	
	    	   	if(trim(s1[8])=='M')
	    	   	{
	    	   		freqMonth=1;
	    	   	}
	    	   	else if(trim(s1[8])=='B')
	    	   	{
	    	   		freqMonth=2;
	    	   	}
	    	   	else if(trim(s1[8])=='Q')
	    	   	{
	    	   		freqMonth=3;
	    	   	}
	    	   	else if(trim(s1[8])=='H')
	    	   	{
	    	   		freqMonth=6;
	    	   	}
	    	   	else if(trim(s1[8])=='Y')
	    	   	{
	    	   		freqMonth=12;
	    	   	}
	    	   	parsedFreq=parseInt(freqMonth);
	    	   	parseTenure=parseInt(trim(s1[7]));
				calInsat=parseTenure/parsedFreq;
		
				window.opener.document.getElementById('noOfInstall').value=calInsat;
				window.opener.document.getElementById('cycleDue2').innerHTML = trim(s1[27]);
				/*if(isInt(calInsat))
				{
					
					if(calInsat=='0')
					{
						alert("The Combination of tenure and frequency are incorrect");
						window.opener.document.getElementById('noOfInstall').value='';
						window.opener.document.getElementById('frequency').value='';
						window.opener.document.getElementById('requestedLoanTenure').value='';
					}
					else
					{
						window.opener.document.getElementById('noOfInstall').value=calInsat;
					}
				}
				else
				{
					alert("The Combination of tenure and frequency are incorrect");
					window.opener.document.getElementById('noOfInstall').value='';
					window.opener.document.getElementById('frequency').value='';
					window.opener.document.getElementById('requestedLoanTenure').value='';
				} */
				
				if(trim(s1[9])=='S')
					{
						window.opener.document.getElementById("interestCalculationMethod").removeAttribute("disabled",true);
					}
				else
					{
					window.opener.document.getElementById("interestCalculationMethod").value='D';
					window.opener.document.getElementById("interestCalculationMethod").setAttribute("disabled",true);
					}
				window.opener.document.getElementById('installmentType').value = trim(s1[9]);
				window.opener.document.getElementById('paymentMode').value = trim(s1[10]);
				window.opener.document.getElementById('instMode').value = trim(s1[11]);
				//alert(trim(s1[20]));
				window.opener.document.getElementById('productTypeFlag').value = trim(s1[20]);
				window.opener.document.getElementById('minMarginRate').value = trim(s1[14]);
				window.opener.document.getElementById('maxMarginRate').value = trim(s1[15]);
				window.opener.document.getElementById('minTenure').value = trim(s1[16]);
				window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
				window.opener.document.getElementById('minFinance').value = trim(s1[18]);
				window.opener.document.getElementById('maxFinance').value = trim(s1[19]);
				
				if(trim(s1[20])=='N')
				{
//sachin////					
					window.opener.document.getElementById("netLtvRow").style.display='none';	
					window.opener.document.getElementById('marginPerc').value = '';
					window.opener.document.getElementById("assetCost").setAttribute("disabled",true);
					window.opener.document.getElementById("assCost1").style.display='none';
					window.opener.document.getElementById("assCost2").style.display='none';
					window.opener.document.getElementById("noOfAsst1").style.display='none';
		    	   	window.opener.document.getElementById("noOfAsst2").style.display='none';	
					window.opener.document.getElementById("marginPerc").setAttribute("disabled",true);
					window.opener.document.getElementById("marPer1").style.display='none';
					window.opener.document.getElementById("marPer2").style.display='none';
					window.opener.document.getElementById("marginAmount").setAttribute("disabled",true);
					window.opener.document.getElementById("marAmount1").style.display='none';
					window.opener.document.getElementById("marAmount2").style.display='none';									
					// Start By Prashant
					window.opener.document.getElementById("ltvDesc").style.display='';
					window.opener.document.getElementById("ltvP").style.display='';
					// End By Prashant
					//amandeep starts
					window.opener.document.getElementById("creditPeriod").style.display='none';	
					window.opener.document.getElementById("creditPeriod1").style.display='none';
					//amandeep ends
					
				}
				else
				{
				
					window.opener.document.getElementById("assetCost").removeAttribute("disabled",true);
					window.opener.document.getElementById("assCost1").style.display='';
					window.opener.document.getElementById("assCost2").style.display='';
					window.opener.document.getElementById("noOfAsst1").style.display='';
		    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	
					window.opener.document.getElementById("marginPerc").removeAttribute("disabled",true);
					window.opener.document.getElementById("marPer1").style.display='';
					window.opener.document.getElementById("marPer2").style.display='';
					window.opener.document.getElementById("marginAmount").removeAttribute("disabled",true);
					window.opener.document.getElementById("marAmount1").style.display='';
					window.opener.document.getElementById("marAmount2").style.display='';
					// Start By Prashant
					window.opener.document.getElementById("ltvDesc").style.display='';
					window.opener.document.getElementById("ltvP").style.display='';
					// End By Prashant
				}
			}
			
			else  if(trim(s1[12])=='N')
			{	
				window.opener.document.getElementById("netLtvRow").style.display='none';	
				//amandeep starts
				window.opener.document.getElementById("creditPeriod").style.display='none';	
				window.opener.document.getElementById("creditPeriod1").style.display='none';
				//amandeep ends
				
				//@Surendra
				window.opener.document.getElementById('requestedLoanTenure').value=trim(s1[7]);
				window.opener.document.getElementById('marginPerc').value = trim(s1[0]);
				window.opener.document.getElementById('rateType').value = trim(s1[1]);
				window.opener.document.getElementById("insComFre1").style.display='none';
				window.opener.document.getElementById("insComFre2").style.display='none';
				window.opener.document.getElementById("insCalMet1").style.display='none';
				window.opener.document.getElementById("insCalMet2").style.display='none';
				window.opener.document.getElementById("insFre1").style.display='none';
				window.opener.document.getElementById("insFre2").style.display='none';
				if(trim(s1[1])=='E')	
				{
					window.opener.document.getElementById('baseRateType').value = trim(s1[3]);
					window.opener.document.getElementById('baseRate').value = trim(s1[4]);
					window.opener.document.getElementById('markUp').value=trim(s1[6])-trim(s1[4]);
					window.opener.document.getElementById('fixPriod').value = trim(s1[22]);
					
					
					window.opener.document.getElementById('effectiveRate').value = trim(s1[6]);
					if(trim(s1[2])=='F')
					{
						window.opener.document.getElementById("type2").setAttribute("disabled",true);
						window.opener.document.getElementById("type1").removeAttribute("disabled",true);
						window.opener.document.getElementById("rateMethodType").value=trim(s1[2]);
						window.opener.document.getElementById('type1').checked=true;
						
					}
					else
					{
						window.opener.document.getElementById("type1").setAttribute("disabled",true);
						window.opener.document.getElementById("type2").removeAttribute("disabled",true);
						window.opener.document.getElementById("rateMethodType").value=trim(s1[2]);
						window.opener.document.getElementById('type2').checked=true;
					}
				}
				else
				{
					window.opener.document.getElementById("type1").removeAttribute("disabled",true);
					window.opener.document.getElementById("rateMethodType").value='F';
					window.opener.document.getElementById("type1").checked=true;
					window.opener.document.getElementById("type2").checked=false;
					window.opener.document.getElementById("baseRateType").value='';
					window.opener.document.getElementById("baseRate").value='';
					window.opener.document.getElementById("markUp").value='';
					window.opener.document.getElementById("fixPriod").value='';
					window.opener.document.getElementById("type2").setAttribute("disabled",true);
					window.opener.document.getElementById("baseRateType").setAttribute("disabled",true);
					window.opener.document.getElementById("baseRate").setAttribute("disabled",true);
					window.opener.document.getElementById("fixPriod").setAttribute("readonly",true);
					window.opener.document.getElementById('effectiveRate').value = trim(s1[5]);
				}      	
		
				window.opener.document.getElementById('repaymentType').value = trim(s1[12]);
				window.opener.document.getElementById('showRepaymentType').value ="NON-INSTALLMENT";
				window.opener.document.getElementById('noOfInstall').value='';
				window.opener.document.getElementById("noOfInstall").setAttribute("disabled",true);
				window.opener.document.getElementById('installments').value='';
				window.opener.document.getElementById("installments").setAttribute("disabled",true);
				window.opener.document.getElementById('cycleDate').value='';
				window.opener.document.getElementById("cycleDate").setAttribute("disabled",true);
				window.opener.document.getElementById('frequency').value = '';
				window.opener.document.getElementById("frequency").setAttribute("disabled",true);	
				window.opener.document.getElementById('installmentType').value = '';
				window.opener.document.getElementById("installmentType").setAttribute("disabled",true);
				window.opener.document.getElementById('paymentMode').value = '';
				window.opener.document.getElementById("paymentMode").setAttribute("disabled",true);
				window.opener.document.getElementById('instMode').value = '';
				window.opener.document.getElementById("instMode").setAttribute("disabled",true);
				window.opener.document.getElementById('productTypeFlag').value = trim(s1[13]);
				window.opener.document.getElementById('minMarginRate').value = trim(s1[14]);
				window.opener.document.getElementById('maxMarginRate').value = trim(s1[15]);
				window.opener.document.getElementById('minTenure').value = trim(s1[16]);
				window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
				window.opener.document.getElementById('minFinance').value = trim(s1[18]);
				window.opener.document.getElementById('maxFinance').value = trim(s1[19]);
				window.opener.document.getElementById('minFlatRate').value = trim(s1[23]);
				window.opener.document.getElementById('maxFlatRate').value = trim(s1[24]);
				window.opener.document.getElementById('minEffectiveRate').value = trim(s1[25]);
				window.opener.document.getElementById('maxEffectiveRate').value = trim(s1[26]);
				//window.opener.document.getElementById("interestCalc").removeAttribute("disabled",true);
				window.opener.document.getElementById("int1").style.display='block';
				window.opener.document.getElementById("int2").style.display='block';
				
				if(trim(s1[20])=='N'||trim(s1[12])=='N')
				{
					window.opener.document.getElementById('marginPerc').value = '';
					window.opener.document.getElementById("assetCost").setAttribute("disabled",true);
					window.opener.document.getElementById("assCost1").style.display='none';
					window.opener.document.getElementById("assCost2").style.display='none';
					window.opener.document.getElementById("noOfAsst1").style.display='none';
		    	   	window.opener.document.getElementById("noOfAsst2").style.display='none';	
					window.opener.document.getElementById("marginPerc").setAttribute("disabled",true);
					window.opener.document.getElementById("marPer1").style.display='none';
					window.opener.document.getElementById("marPer2").style.display='none';
					window.opener.document.getElementById("marginAmount").setAttribute("disabled",true);
					window.opener.document.getElementById("marAmount1").style.display='none';
					window.opener.document.getElementById("marAmount2").style.display='none';
					window.opener.document.getElementById("noOfIns1").style.display='none';
					window.opener.document.getElementById("noOfIns2").style.display='none';
					window.opener.document.getElementById("frequnc1").style.display='none';
					window.opener.document.getElementById("frequnc2").style.display='none';
					window.opener.document.getElementById("installType1").style.display='none';
					window.opener.document.getElementById("installType2").style.display='none';
					window.opener.document.getElementById("insMod1").style.display='none';
					window.opener.document.getElementById("insMod2").style.display='none';
					window.opener.document.getElementById("repayMod1").style.display='none';
					window.opener.document.getElementById("repayMod2").style.display='none';
					window.opener.document.getElementById("noIns1").style.display='none';
					window.opener.document.getElementById("noIns2").style.display='none';
					window.opener.document.getElementById("cyclDate1").style.display='none';
					window.opener.document.getElementById("cyclDate2").style.display='none';
					window.opener.document.getElementById("nddheader").style.display='none';
					window.opener.document.getElementById("ndd").style.display='none';
					window.opener.document.getElementById("effDateHeader").style.display='none';
					window.opener.document.getElementById("effDate").style.display='none';
					// Start By Prashant
					window.opener.document.getElementById("ltvDesc").style.display='none';
					window.opener.document.getElementById("ltvP").style.display='none';
					// End By Prashant

				}
				else
				{
					window.opener.document.getElementById("assetCost").removeAttribute("disabled",true);
					window.opener.document.getElementById("assCost1").style.display='';
					window.opener.document.getElementById("assCost2").style.display='';
					window.opener.document.getElementById("noOfAsst1").style.display='';
		    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	
					window.opener.document.getElementById("marginPerc").removeAttribute("disabled",true);
					window.opener.document.getElementById("marPer1").style.display='';
					window.opener.document.getElementById("marPer2").style.display='';
					window.opener.document.getElementById("marginAmount").removeAttribute("disabled",true);
					window.opener.document.getElementById("marAmount1").style.display='';
					window.opener.document.getElementById("marAmount2").style.display='';
					window.opener.document.getElementById("noOfIns1").style.display='';
					window.opener.document.getElementById("noOfIns2").style.display='';
					window.opener.document.getElementById("frequnc1").style.display='';
					window.opener.document.getElementById("frequnc2").style.display='';
					window.opener.document.getElementById("installType1").style.display='';
					window.opener.document.getElementById("installType2").style.display='';
					window.opener.document.getElementById("insMod1").style.display='';
					window.opener.document.getElementById("insMod2").style.display='';
					window.opener.document.getElementById("repayMod1").style.display='';
					window.opener.document.getElementById("noIns2").style.display='';
					window.opener.document.getElementById("noIns1").style.display='';
					window.opener.document.getElementById("noIns2").style.display='';
					window.opener.document.getElementById("cyclDate1").style.display='';
					window.opener.document.getElementById("cyclDate2").style.display='';
					// Start By Prashant
					window.opener.document.getElementById("ltvDesc").style.display='';
					window.opener.document.getElementById("ltvP").style.display='';
					// End By Prashant
					
				}	
			}
			
			window.close();
			
			}
		}
	}

function generateValuesClosure(){
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 window.opener.document.getElementById('balancePrincipal').value='';  
		 window.opener.document.getElementById('secureDeposit').value='';
		 window.opener.document.getElementById('overdueInstallments').value='';
		 window.opener.document.getElementById('secureDepositInterest').value='';
		 window.opener.document.getElementById('interestTillDate').value='';
		 window.opener.document.getElementById('secureDepositTDS').value='';
		 window.opener.document.getElementById('unBilledInstallments').value='';
		 window.opener.document.getElementById('gapSDInterest').value='';
		 window.opener.document.getElementById('foreClosurePenalty').value='';
		 window.opener.document.getElementById('gapSDTDS').value='';
		 window.opener.document.getElementById('otherDues').value='';
		 window.opener.document.getElementById('otherRefunds').value='';
		 window.opener.document.getElementById('netReceivablePayable').value='';
		 window.opener.document.getElementById('interstTillLP').value='';
		 window.opener.document.getElementById('lppAmount').value='';
		 window.opener.document.getElementById('overduePrincipal').value='';
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveClosureValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendClosureid(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Please Select One Record");	
    	 return false;
     }
	 
}

function sendClosureid(address, data) {
	//alert("in sendClosureid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultClosure(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultClosure(request){
	
	//alert("in resultClosure id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('loanAc').value=trim(s1[0]);  
		window.opener.document.getElementById('loanDate').value=trim(s1[1]);
		window.opener.document.getElementById('customerName').value=trim(s1[2]);
		window.opener.document.getElementById('product').value=trim(s1[3]);
		window.opener.document.getElementById('scheme').value=trim(s1[4]);
		window.opener.document.getElementById('frequency').value=trim(s1[5]);
		window.opener.document.getElementById('originalTenure').value=trim(s1[6]);
		window.opener.document.getElementById('maturtyDate').value=trim(s1[7]);
		window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[8]);
		window.opener.document.getElementById('remainingInstallments').value=trim(s1[9]);
		window.opener.document.getElementById('remainingTenure').value=trim(s1[10]);
		//window.opener.document.getElementById('maxAdviceDate').value=trim(s1[21]);
		window.close();
	}
}
//Surendra Code for  MIC Start..
function generateValuesManualIntCalc(){
	
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 window.opener.document.getElementById('balancePrincipal').value=''; 
		 window.opener.document.getElementById('lastIntCalcDate').value=''; 	 
		 
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveManualIntCalcValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendManualIntCalcid(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Please Select One Record");	
    	 return false;
     }
	 
}

function sendManualIntCalcid(address, data) {
	//alert("in sendClosureid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultManualIntCalc(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultManualIntCalc(request){
	
	//alert("in resultClosure id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('loanAc').value=trim(s1[0]);  
		window.opener.document.getElementById('loanDate').value=trim(s1[1]);
		window.opener.document.getElementById('customerName').value=trim(s1[2]);
		window.opener.document.getElementById('product').value=trim(s1[3]);
		window.opener.document.getElementById('scheme').value=trim(s1[4]);
		window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[5]);
		window.opener.document.getElementById('repayEffectiveDate').value=trim(s1[6]);		
		if(trim(s1[7])=='00-00-0000'){
			window.opener.document.getElementById('lastAccrualDate').value='';	}	
		else window.opener.document.getElementById('lastAccrualDate').value=trim(s1[7]);
		window.opener.document.getElementById('roi').value=trim(s1[8]);
		window.opener.document.getElementById('lastIntCalcDate').value=trim(s1[9]);	
		window.opener.document.getElementById('balancePrincipal').value=trim(s1[10]);	
		window.close();
	}
}
//Surendra Code for MIC end....

function lovIndustryLead()
{
	var industry=window.opener.document.getElementById('industry').value;
	
	if(industry!='')
	{
		window.opener.document.getElementById('subIndustry').value='';
	}
	
	window.close();
}

function lovBranchLead()
{
	
	var branch=window.opener.document.getElementById('branch').value;
	if(branch!='')
	{
		window.opener.document.getElementById('relationshipManager').value='';
	}
	
	window.close();
}
function lovProductDeal()
{
	
	var product=window.opener.document.getElementById('product').value;
	if(product!='')
	{
		window.opener.document.getElementById('scheme').value='';
	}
	
	window.close();
}
function lovCountry()
{
	
	var country=window.opener.document.getElementById('country').value;
	if(country!='')
	{
		window.opener.document.getElementById('state').value='';
		window.opener.document.getElementById('dist').value='';
	}
	
	window.close();
}
function lovState()
{
	
	var state=window.opener.document.getElementById('state').value;
	if(state!='')
	{
		window.opener.document.getElementById('dist').value='';
	}
	
	window.close();
}
function lovcountry()
{
	
	var country=window.opener.document.getElementById('country').value;
	if(country!='')
	{
		window.opener.document.getElementById('state').value='';
		window.opener.document.getElementById('dist').value='';
	}
	
	window.close();
}
//abhimanyu ajax method starts here...........

function getWaiveOffList()
{
	
	   //alert("In getWaiveOffList");	
	   var chargeCode =window.opener.document.getElementById('lbxChargeCodeHID').value ;
	   var loanid =window.opener.document.getElementById('lbxLoanNoHID').value ;
	   var bp_type =window.opener.document.getElementById('businessPartnerType').value ;
	   var txn_Advice_Id =window.opener.document.getElementById('htxnAdviceId').value ;
	   
		 //alert("In getBaseRate"+scheme);
	   var contextPath =window.opener.document.getElementById('contextPath').value ;
	// alert("contextPath"+contextPath);
       if(chargeCode!= '' &&( loanid!='' || bp_type!='' || txn_Advice_Id!='' ) ) 
       {
		var address = contextPath+"/ajaxAction.do?method=getDefaultWaiveOffDetail";
		var data = "chargeCode="+chargeCode+","+loanid+","+bp_type+","+txn_Advice_Id ;
	
		//alert("address: "+address+"data: "+data);
		send_BaseRateWaiveOff(address, data);
		return true;
	    }
         else
         {
    	 alert("Please Select List");	
    	 return false;
          }
}

function send_BaseRateWaiveOff(address, data) {
	 //alert("send_BaseRateWaiveOff"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_BaseRateWaiveOff(request);
	};
      // alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_BaseRateWaiveOff(request){
    //alert(request)
	if ((request.readyState == 4) && (request.status == 200))
	{
		var str = request.responseText;
		//alert(str);
		
		var s1 = str.split("$:");
        
	    
		window.opener.document.getElementById("chargeAmount").value=trim(s1[0]);
    	window.opener.document.getElementById("taxAmount1").value=trim(s1[1]);
    	window.opener.document.getElementById("taxAmount2").value=trim(s1[2]);
    	window.opener.document.getElementById("adviceAmount").value=trim(s1[3]);
    	window.opener.document.getElementById("txnAdjustedAmount").value=trim(s1[4]);
    	window.opener.document.getElementById("balanceAmount").value=trim(s1[5]);
    	window.opener.document.getElementById("waiveOffAmountNotUsed").value=trim(s1[6]);
    	window.opener.document.getElementById("txnAdviceId").value=trim(s1[7]);
    	window.opener.document.getElementById("taxRate1").value=trim(s1[8]);
    	window.opener.document.getElementById("taxRate2").value=trim(s1[9]);
    	window.opener.document.getElementById("amountInProcess").value=trim(s1[10]);
    	   	
    	
    	window.opener.document.getElementById("waveAmountForTaxAmt1").value = "";
    	window.opener.document.getElementById("waveAmountForTaxAmt2").value = "";
    	window.opener.document.getElementById("totalWaveOffAmt").value ="";
    	window.opener.document.getElementById("ChargeNewAmount").value = "";
    	window.opener.document.getElementById("tax1NewAmt").value = "" ; 
    	window.opener.document.getElementById("tax2NewAmt").value = "" ; 
    	window.opener.document.getElementById("newAdviceAmt").value = "" ; 
    	window.opener.document.getElementById("newBalanceAmt").value = "" ; 
    	window.opener.document.getElementById("waivOffAmount").value = "" ; 
    	
    	window.opener.document.getElementById("adviceDtChngFlag").value='N';
    	//window.opener.document.getElementById("Save").removeAttribute("disabled","true");
    	//window.opener.document.getElementById("Save & Forward").removeAttribute("disabled","true");
    	window.close();
	    
	}
}
//nishant rai space start

function generateDealValuesCancellation()
{
	var dealId=window.opener.document.getElementById('lbxDealNo').value;
	if(dealId!='')
	{
		var contextPath=window.opener.document.getElementById("contextPath").value;
		var address = contextPath+"/ajaxAction.do?method=fetchDealCanData";
		var data = "dealId="+dealId;
		sendGenerateDealValuesCancellation(address,data);
		return true;
	}
	else
	{
		 alert("First select Deal Number.");
		 return false;
	}
}
function sendGenerateDealValuesCancellation(address, data) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultGenerateDealValuesCancellation(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultGenerateDealValuesCancellation(request){
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('dealDate').value=trim(s1[0]);
		window.opener.document.getElementById('appFormNumber').value=trim(s1[1]);
		window.opener.document.getElementById('product').value=trim(s1[2]);
		window.opener.document.getElementById('scheme').value=trim(s1[3]);
		window.opener.document.getElementById('assetCost').value=trim(s1[4]);
		window.opener.document.getElementById('loanAmount').value=trim(s1[5]);
		window.opener.document.getElementById('marginPercentage').value=trim(s1[6]);
		window.opener.document.getElementById('marginAmount').value=trim(s1[7]);
		window.opener.document.getElementById('rate').value=trim(s1[8]);
		window.opener.document.getElementById('tenure').value=trim(s1[9]);
		window.opener.document.getElementById('dealStatus').value=trim(s1[10]);
		window.opener.document.getElementById('frequency').value=trim(s1[11]);
		window.opener.document.getElementById('lbxProduct').value=trim(s1[12]);
		window.opener.document.getElementById('lbxScheme').value=trim(s1[13]);
		window.close();
	}
}
//nishant rai space end
//neeraj
function getGroupExposure()
{
	var contextPath=window.opener.document.getElementById("contextPath").value;
	var applicantId=window.opener.document.getElementById("applicantId").value;
	var hGroupId=window.opener.document.getElementById("hGroupId").value;
	var groupType=window.opener.document.getElementById("groupType").value;
	
	
	if(applicantId !='')
	{
		var address = contextPath+"/ajaxActionForCP.do?method=fetchExposureLimitByCustomer";
		var data = "customerId="+applicantId+"&hGroupId="+hGroupId+"&groupType="+groupType;
		window.opener.sendRequestFetchExposureLimitByCustomerId(address,data,'l');
		return true;
	}
	else
	{
		alert("Customer Id is required.");	
   	 	return false;
	}
	
}
function getKnockOffCancellationData()
{
	var loanId=window.opener.document.getElementById('lbxLoanID').value;
	var knockOffID=window.opener.document.getElementById('knockOffID').value;
	if(loanId!='' && knockOffID !='')
	{
		var contextPath=window.opener.document.getElementById("contextPath").value;
		window.opener.document.getElementById("knockOffCanNew").action = contextPath+"/knockOffCancellationDispatchAction.do?method=openKnockOffCancellationValues&loanId="+loanId+"&knockOffId="+knockOffID+"&val=N";
		window.opener.document.getElementById("knockOffCanNew").submit();
	}
	window.close();
}
//code added by neeraj

function getDefltCustDtl()
{
	 var customerID=window.opener.document.getElementById('customerID').value;
	 if(customerID!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/dedupe.do?method=getDefltCustDtl";
		 var data = "customerID="+customerID;
		 sendDefltCustDtl(address, data);
		 return true;
	 }
	 else
	 {
		 alert("First select Deal Number.");
		 return false;
	 }
}
function sendDefltCustDtl(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDefltCustDtl(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultDefltCustDtl(request){
	
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('panNo').value=trim(s1[0]); 
		window.opener.document.getElementById('regNo').value=trim(s1[1]);
		window.opener.document.getElementById('dlNo').value=trim(s1[2]);
		window.opener.document.getElementById('passportNo').value=trim(s1[3]);
		window.opener.document.getElementById('voterID').value=trim(s1[4]);
		window.opener.document.getElementById('phNo').value=trim(s1[5]);
        window.opener.document.getElementById('groupName').value=trim(s1[6]);
		window.close();
	}
}
//neeraj space end
//function getChargesDetail()
//{
//	 var lbxLoanNo=window.opener.document.getElementById('lbx_loan_from_id').value;
//	 if(lbxLoanNo!='')
//	 {
//		 var contextPath=window.opener.document.getElementById("contextPath").value;
//		 var address = contextPath+"/ajaxAction.do?method=getChargesDetail";
//		 var data = "loanId="+lbxLoanNo;
//		 sendGetChargesDetail(address, data);
//		 return true;
//	 }
//	 else
//	 {
//		 alert("First select Loan Number.");
//		 return false;
//	 }
//}
//function sendGetChargesDetail(address, data) {
//	//alert("in sendDisbursalId id");
//	var request = getRequestObject();
//	request.onreadystatechange = function () {
//		resultGetChargesDetail(request);	
//	};
//	request.open("POST", address, true);
//	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//	request.send(data);	
//}
//function resultGetChargesDetail(request){
//	
//	if ((request.readyState == 4) && (request.status == 200)) 
//	{
//		var str = request.responseText;
//		var s1 = str.split("$:");
//		window.opener.document.getElementById('adviceType1').value=trim(s1[0]);
//		window.opener.document.getElementById('adviceAmount1').value=trim(s1[1]);
//		window.opener.document.getElementById('adjustedAmount1').value=trim(s1[2]);
//		window.opener.document.getElementById('blanceAmount1').value=trim(s1[3]);
//		window.opener.document.getElementById('adviceType2').value=trim(s1[4]);
//		window.opener.document.getElementById('adviceAmount2').value=trim(s1[5]);
//		window.opener.document.getElementById('adjustedAmount2').value=trim(s1[6]);
//		window.opener.document.getElementById('blanceAmount2').value=trim(s1[7]);
//		window.opener.document.getElementById('adviceType3').value=trim(s1[8]);
//		window.opener.document.getElementById('adviceAmount3').value=trim(s1[9]);
//		window.opener.document.getElementById('adjustedAmount3').value=trim(s1[10]);
//		window.opener.document.getElementById('blanceAmount3').value=trim(s1[11]);		
//		window.close();
//	}
//}
//method added by manish
function deleteScheme()
{
	window.opener.document.getElementById('scheme').value='';
	window.opener.document.getElementById('lbxScheme').value='';
	window.close();
}
function calculatePDC()
{
	 var lbxLoanNo=window.opener.document.getElementById('lbxLoanNo').value;
	 if(lbxLoanNo!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=calculatePDC";
		 var data = "loanId="+lbxLoanNo;
		 sendCalculatePDC(address, data);
		 return true;
	 }
	 else
	 {
		 alert("First select Loan Number.");
		 return false;
	 }
}
function sendCalculatePDC(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCalculatePDC(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultCalculatePDC(request){
	
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('presented').value=trim(s1[0]); 
		window.opener.document.getElementById('toBePresented').value=trim(s1[1]);		
		window.close();
	}
}
//neeraj for copy default deposit detail in presentation batch process
function bankDetail()
{
	//alert("bankDetail");
	var batchId=window.opener.document.getElementById('lbxBatchNo').value;
	 if(batchId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/presentationBatchProcessDispatchAction.do?method=getBankDetail&batchId="+batchId;
		 var data = "batchId="+batchId;
		 sendBankDetail(address, data);
		 return true;
	 }
	 else
		 removeBankDetail();
}
function sendBankDetail(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultBankDetail(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultBankDetail(request){
	
	//alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");		
		window.opener.document.getElementById('batchNo').value=trim(s1[1]); 
		window.opener.document.getElementById('lbxBatchNo').value=trim(s1[0]); 
		window.opener.document.getElementById('status').value='Finalized'; 
		window.opener.document.getElementById('presentationDate').value=trim(s1[2]); 
		var prestDate= value=trim(s1[2]);
		var result=prestDate.substring(6)+"-"+prestDate.substring(3,5)+"-"+prestDate.substring(0,2);
		window.opener.document.getElementById("finalDate").value=result;
		window.opener.document.getElementById('bank').value=trim(s1[4]); 
		window.opener.document.getElementById('lbxBankID').value=trim(s1[3]); 
		window.opener.document.getElementById('branch').value=trim(s1[6]); 
		window.opener.document.getElementById('lbxBranchID').value=trim(s1[5]); 
		window.opener.document.getElementById('micr').value=trim(s1[7]); 
		window.opener.document.getElementById('ifscCode').value=trim(s1[8]); 
		window.opener.document.getElementById('bankAccount').value=trim(s1[9]); 
		
		window.opener.document.getElementById('totalNoInstrument').value=trim(s1[10]); 
		window.opener.document.getElementById('totalInstrumentAmount').value=trim(s1[11]); 
		
		window.close();
	}
}
function removeBankDetail()
{
	window.opener.document.getElementById('batchNo').value=""; 
	window.opener.document.getElementById('lbxBatchNo').value=""; 
	window.opener.document.getElementById('status').value=""; 
	window.opener.document.getElementById('presentationDate').value=""; 
	window.opener.document.getElementById("finalDate").value="";
	window.opener.document.getElementById('bank').value=""; 
	window.opener.document.getElementById('lbxBankID').value=""; 
	window.opener.document.getElementById('branch').value=""; 
	window.opener.document.getElementById('lbxBranchID').value=""; 
	window.opener.document.getElementById('micr').value=""; 
	window.opener.document.getElementById('ifscCode').value=""; 
	window.opener.document.getElementById('bankAccount').value=""; 
	
	window.opener.document.getElementById('totalNoInstrument').value=""; 
	window.opener.document.getElementById('totalInstrumentAmount').value=""; 
}
//neeraj for copy address
function copyAddress()
{
	 var addressId=window.opener.document.getElementById('addressId').value;
	 if(addressId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=copyAddress&addressId="+addressId;
		 var data = "addressId="+addressId;
		 sendCopyaddress(address, data);
		 return true;
	 }
	 else
		 removeRecord();
}
function sendCopyaddress(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCopyAddress(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultCopyAddress(request){
	
	//alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('addr_type').value=trim(s1[1]); 
		window.opener.document.getElementById('addr1').value=trim(s1[2]);  
		window.opener.document.getElementById('addr2').value=trim(s1[3]);  
		window.opener.document.getElementById('addr3').value=trim(s1[4]);  
		window.opener.document.getElementById('txtCountryCode').value=trim(s1[5]);  
		window.opener.document.getElementById('country').value=trim(s1[6]);  
		window.opener.document.getElementById('txtStateCode').value=trim(s1[7]);  
		window.opener.document.getElementById('state').value=trim(s1[8]);  
		window.opener.document.getElementById('tahsil').value=trim(s1[9]);  
		window.opener.document.getElementById('txtDistCode').value=trim(s1[10]);  
		window.opener.document.getElementById('dist').value=trim(s1[11]);  
		window.opener.document.getElementById('pincode').value=trim(s1[12]);  
		window.opener.document.getElementById('primaryPhoneNo').value=trim(s1[13]);  
		window.opener.document.getElementById('alternatePhoneNo').value=trim(s1[14]);  
		window.opener.document.getElementById('tollfreeNo').value=trim(s1[15]);  
		window.opener.document.getElementById('faxNo').value=trim(s1[16]);  
		window.opener.document.getElementById('landMark').value=trim(s1[17]);  
		window.opener.document.getElementById('noYears').value=trim(s1[18]);  
		window.opener.document.getElementById('noMonths').value=trim(s1[19]);  
		window.opener.document.getElementById('distanceBranch').value=trim(s1[20]);  
		if(trim(s1[21])=='Y')
			window.opener.document.getElementById('communicationAddress').checked=true;
		else
			window.opener.document.getElementById('communicationAddress').checked=false;
		window.opener.document.getElementById('addDetails').value=trim(s1[22]);  
		window.close();
	}
}
function removeRecord()
{
	window.opener.document.getElementById('addr_type').value=''; 
	window.opener.document.getElementById('addr1').value='';  
	window.opener.document.getElementById('addr2').value='';  
	window.opener.document.getElementById('addr3').value='';  
	window.opener.document.getElementById('txtCountryCode').value='';  
	window.opener.document.getElementById('country').value='';  
	window.opener.document.getElementById('txtStateCode').value='';  
	window.opener.document.getElementById('state').value='';  
	window.opener.document.getElementById('tahsil').value='';  
	window.opener.document.getElementById('txtDistCode').value='';  
	window.opener.document.getElementById('dist').value='';  
	window.opener.document.getElementById('pincode').value='';  
	window.opener.document.getElementById('primaryPhoneNo').value='';  
	window.opener.document.getElementById('alternatePhoneNo').value='';  
	window.opener.document.getElementById('tollfreeNo').value='';  
	window.opener.document.getElementById('faxNo').value='';  
	window.opener.document.getElementById('landMark').value='';  
	window.opener.document.getElementById('noYears').value='';  
	window.opener.document.getElementById('noMonths').value='';  
	window.opener.document.getElementById('distanceBranch').value=''; 
	window.opener.document.getElementById('communicationAddress').checked=false;
	window.opener.document.getElementById('addDetails').value='';  
	window.close();
	
}
function generateValuesDisbursal(){
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveDisbursalValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendDisbursalId(address, data);
		 return true;
	 }
	 
}

function sendDisbursalId(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDisbursal(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultDisbursal(request){
	
	//alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		if(str.length>120)
		{
			var s1 = str.split("$:");
			window.opener.document.getElementById('loanNo').value=trim(s1[0]);  
			window.opener.document.getElementById('customerName').value=trim(s1[1]);
			window.opener.document.getElementById('loanAmt').value=trim(s1[2]);
			window.opener.document.getElementById('loanApprovalDate').value=trim(s1[3]);
			window.opener.document.getElementById('product').value=trim(s1[4]);
			window.opener.document.getElementById('scheme').value=trim(s1[5]);
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[6]);
			window.opener.document.getElementById('proposedShortPayAmount').value=trim(s1[7]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[8]);
			window.opener.document.getElementById('adjustedShortPayAmount').value=trim(s1[9]);
			window.opener.document.getElementById('disbursalNo').value=trim(s1[10]);
			window.opener.document.getElementById('pdcDepositCount').value=trim(s1[11]);
			
			window.opener.document.getElementById('maxExpectedPayDate').value=trim(s1[15]);
			window.opener.document.getElementById('maxDisbursalDate').value=trim(s1[16]);
			window.opener.document.getElementById('currentMonthEMI').value="";
			window.opener.document.getElementById('preEMINextMonth').value="";
			
			//alert("trim(s1[12]) "+trim(s1[12]));
			if(trim(s1[12])=='I')
			{
				
				window.opener.document.getElementById("val").value="N";
				window.opener.document.getElementById("yes1").style.display="none";
				window.opener.document.getElementById("no1").style.display="";
				window.opener.document.getElementById("yes2").style.display="none";
				window.opener.document.getElementById("no2").style.display="";
				window.opener.document.getElementById("yes3").style.display="none";
				window.opener.document.getElementById("no3").style.display="";
				window.opener.document.getElementById("repayEffId").style.display='none';
				window.opener.document.getElementById("repayId").style.display='';
				//window.opener.document.getElementById("cycleDateFinal").style.display='none';
				//window.opener.document.getElementById("cycleDateI").style.display='';
				window.opener.document.getElementById("nextDueDate").value="";
				window.opener.document.getElementById("repayEff").value="";
				window.opener.document.getElementById("cycleDate").value='';

				
					window.opener.document.getElementById("repayEffId").removeAttribute("disabled","true");
				window.opener.document.getElementById("repayId").removeAttribute("disabled","true");
				
				window.opener.document.getElementById("repayIdBusDate").style.display='none';
				window.opener.document.getElementById("repayIdBusDate").setAttribute("disabled","true");
				window.opener.document.getElementById("repayEffDate").setAttribute("disabled","true");
				window.opener.document.getElementById("EMI").style.display='';
				
				window.opener.document.getElementById("expectedPmntDtDiv").value="";
				window.opener.document.getElementById("expectedPmntDtDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtDiv").style.display='none';
				window.opener.document.getElementById("expectedPmntDtInsDiv").removeAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtInsDiv").style.display='';
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").style.display='none';
				
				window.opener.document.getElementById("taLoanNoTrDiv").style.display='';
				
			}

			else
			{
				window.opener.document.getElementById("val").value="N";
				window.opener.document.getElementById("yes1").style.display="none";
				window.opener.document.getElementById("no1").style.display="";
				window.opener.document.getElementById("yes2").style.display="none";
				window.opener.document.getElementById("no2").style.display="";
				window.opener.document.getElementById("yes3").style.display="none";
				window.opener.document.getElementById("no3").style.display="";
				window.opener.document.getElementById("repayEffId").style.display='none';
				window.opener.document.getElementById("repayId").style.display='none';
				
				window.opener.document.getElementById("repayEffId").setAttribute("disabled","true");
				window.opener.document.getElementById("repayId").setAttribute("disabled","true");
				window.opener.document.getElementById("repayEffDate").setAttribute("disabled","true");
				//window.opener.document.getElementById("cycleDateFinal").style.display='none';
				//window.opener.document.getElementById("cycleDateI").style.display='';
				window.opener.document.getElementById("nextDueDate").value="";
				window.opener.document.getElementById("repayIdBusDate").style.display='';
				window.opener.document.getElementById("repayEffBusDate").value=trim(s1[14]);
				window.opener.document.getElementById("cycleDate").value='';
				window.opener.document.getElementById("EMI").style.display='none';
				
				window.opener.document.getElementById("expectedPmntDtDiv").value="";
				window.opener.document.getElementById("expectedPmntDtDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtDiv").style.display='none';
				window.opener.document.getElementById("expectedPmntDtInsDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtInsDiv").style.display='none';
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").removeAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").style.display='';
				
				window.opener.document.getElementById("taLoanNoTrDiv").style.display='none';
					//alert("trim(s1[14] : "+trim(s1[14]));
			}
		


			
			window.opener.document.getElementById('finDisb').innerHTML=trim(s1[13]);
			
			window.close();
		}
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+'/disbursalSearch.do?method=searchDisbursalMakerLnik';
			self.close();
		}
	}
}
/*Added by Arun for new Disbursal with payment*/
function generateValuesDisbursalWithPayment(){
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveDisbursalValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendDisbursalIdWithPayment(address, data);
		 return true;
	 }
	 
}

function getOtherLoanDetails(){
	//alert("hi");
	 var paymentFlag=document.getElementById('paymentFlag').value;
	 var loanId=document.getElementById('lbxLoanNoHID').value;
	 if(paymentFlag=='O'){
	 if(loanId!='')
	 {
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=getOtrherLoanDetails";
		 var data = "lbxLoanNoHID="+loanId;
		 sendOtherLoanDetails(address, data);
		 return true;
	 }
	 } 
}


function sendDisbursalIdWithPayment(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDisbursalWithPayment(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function sendOtherLoanDetails(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultOtherLoanDetails(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultDisbursalWithPayment(request){
	
	//alert("in resultDisbursal id");
	
	
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = trim(request.responseText);
		//alert("String:--->"+str);
		var correctLoan=false;
		if(str.length>120)
		{
			var s1 = str.split("$:");
			if(window.opener.document.getElementById('loanId1')!=null ){
			if( window.opener.document.getElementById('loanId1').value == window.opener.document.getElementById('lbxLoanNoHID').value){
				correctLoan=true;	
			}else{
				correctLoan=false;
			}
			}else{
				correctLoan=true;
			}
			if(correctLoan){
				
				
			window.opener.document.getElementById('loanNo').value=trim(s1[0]);  
			window.opener.document.getElementById('customerName').value=trim(s1[1]);
			window.opener.document.getElementById('loanAmt').value=trim(s1[2]);
			window.opener.document.getElementById('loanApprovalDate').value=trim(s1[3]);
			window.opener.document.getElementById('product').value=trim(s1[4]);
			window.opener.document.getElementById('scheme').value=trim(s1[5]);
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[6]);
			//window.opener.document.getElementById('proposedShortPayAmount').value=trim(s1[7]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[8]);
			//window.opener.document.getElementById('adjustedShortPayAmount').value=trim(s1[9]);
			window.opener.document.getElementById('disbursalNo').value=trim(s1[10]);
			window.opener.document.getElementById('pdcDepositCount').value=trim(s1[11]);
			
			//window.opener.document.getElementById('maxExpectedPayDate').value=trim(s1[15]);
			window.opener.document.getElementById('maxDisbursalDate').value=trim(s1[16]);
			window.opener.document.getElementById('disbursedAmountTemp').value=trim(s1[17]);
			window.opener.document.getElementById('revolvingFlag').value=trim(s1[18]);
			window.opener.document.getElementById('balancePrinc').value=trim(s1[19]);
			window.opener.document.getElementById('forwardedAmt').value=trim(s1[20]);
			
			window.opener.document.getElementById('currentMonthEMI').value="";
			window.opener.document.getElementById('preEMINextMonth').value="";
			var disbursedAmountTemp=trim(s1[17]);
// add by saorabh
			var prePaymentAmount = trim(s1[22]);
			var recStatusForPartPayment = trim(s1[23]);
			var interestDueDateDis = trim(s1[25]);
			window.opener.document.getElementById('hiddenIntDueDate').value=interestDueDateDis;
			if(recStatusForPartPayment=='1')
			{
			alert('Loan is pending for Part Payment');
			var contextPath=window.opener.document.getElementById("contextPath").value;
			window.opener.location.href = contextPath+'/disbursalSearch.do?method=searchDisbursalMakerLnik';
			}
//end by saorabh
			if(disbursedAmountTemp==""){
				disbursedAmountTemp="0";
			}
			var disbursedAmount=trim(s1[8]);
			if(disbursedAmount==""){
				disbursedAmount="0";
			}
			var loanAmount=trim(s1[2]);
			if(loanAmount==""){
				loanAmount="0";
			}
			var subAmount=trim(s1[19]);
			if(subAmount==""){
				subAmount="0";
			}
			var amount20=trim(s1[20]);
			if(amount20==""){
				amount20="0";
			}
			var sum=0;
			if(trim(s1[18])=='N'){
				sum=parseFloat(removeComma(loanAmount))-(parseFloat(removeComma(disbursedAmount))+parseFloat(removeComma(disbursedAmountTemp)));
				}
			// add by saorabh
			else if(trim(s1[18])=='Y'){
				sum=(parseFloat(removeComma(loanAmount))-(parseFloat(removeComma(disbursedAmount)))+parseFloat(removeComma(disbursedAmountTemp)))+parseFloat(removeComma(prePaymentAmount));
			}
			//end by saorabh
		else{
				sum=parseFloat(removeComma(loanAmount))-(parseFloat(subAmount)+parseFloat(removeComma(disbursedAmountTemp))+parseFloat(amount20));
			//alert('Saurabh 3 : '+ sum);
		}
			window.opener.document.getElementById('disbursalAmount').value=sum;
			//
			//alert("trim(s1[12]) "+trim(s1[12]));
			if(trim(s1[12])=='I')
			{
				
				window.opener.document.getElementById("val").value="N";
				window.opener.document.getElementById("yes1").style.display="none";
				window.opener.document.getElementById("no1").style.display="";
				window.opener.document.getElementById("yes2").style.display="none";
				window.opener.document.getElementById("no2").style.display="";
				window.opener.document.getElementById("yes3").style.display="none";
				window.opener.document.getElementById("no3").style.display="";
				window.opener.document.getElementById("repayEffId").style.display='none';
				window.opener.document.getElementById("repayId").style.display='';
				//window.opener.document.getElementById("cycleDateFinal").style.display='none';
				//window.opener.document.getElementById("cycleDateI").style.display='';
				window.opener.document.getElementById("nextDueDate").value="";
				window.opener.document.getElementById("repayEff").value="";
				window.opener.document.getElementById("cycleDate").value='';

				
					window.opener.document.getElementById("repayEffId").removeAttribute("disabled","true");
				window.opener.document.getElementById("repayId").removeAttribute("disabled","true");
				
				window.opener.document.getElementById("repayIdBusDate").style.display='none';
				window.opener.document.getElementById("repayIdBusDate").setAttribute("disabled","true");
				window.opener.document.getElementById("repayEffDate").setAttribute("disabled","true");
				//window.opener.document.getElementById("EMI").style.display='';
				//Code by arun
				
				
				window.opener.document.getElementById("penal1").style.display='none';
				window.opener.document.getElementById("penal2").style.display='block';
				/*window.opener.document.getElementById("expectedPmntDtDiv").value="";
				window.opener.document.getElementById("expectedPmntDtDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtDiv").style.display='none';
				window.opener.document.getElementById("expectedPmntDtInsDiv").removeAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtInsDiv").style.display='';
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").style.display='none';
				
				window.opener.document.getElementById("taLoanNoTrDiv").style.display='';*/
				
			}

			else
			{
				window.opener.document.getElementById("val").value="N";
				window.opener.document.getElementById("yes1").style.display="none";
				window.opener.document.getElementById("no1").style.display="";
				window.opener.document.getElementById("yes2").style.display="none";
				window.opener.document.getElementById("no2").style.display="";
				window.opener.document.getElementById("yes3").style.display="none";
				window.opener.document.getElementById("no3").style.display="";
				window.opener.document.getElementById("repayEffId").style.display='none';
				window.opener.document.getElementById("repayId").style.display='none';
				
				window.opener.document.getElementById("repayEffId").setAttribute("disabled","true");
				window.opener.document.getElementById("repayId").setAttribute("disabled","true");
				window.opener.document.getElementById("repayEffDate").setAttribute("disabled","true");
				//window.opener.document.getElementById("cycleDateFinal").style.display='none';
				//window.opener.document.getElementById("cycleDateI").style.display='';
				window.opener.document.getElementById("nextDueDate").value="";
				window.opener.document.getElementById("repayIdBusDate").style.display='';
				window.opener.document.getElementById("repayEffBusDate").value=trim(s1[14]);
				window.opener.document.getElementById("cycleDate").value='';
				//window.opener.document.getElementById("EMI").style.display='none';
			//Code by arun
				window.opener.document.getElementById("penal1").style.display='block';
				window.opener.document.getElementById("penal2").style.display='none';
			//	window.opener.document.getElementById("expectedPmntDtDiv").setAttribute("disabled","true");
				
				/*window.opener.document.getElementById("expectedPmntDtInsDiv").setAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtInsDiv").style.display='none';
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").removeAttribute("disabled","true");
				window.opener.document.getElementById("expectedPmntDtNonInsDiv").style.display='';
				
				window.opener.document.getElementById("taLoanNoTrDiv").style.display='none';*/
					//alert("trim(s1[14] : "+trim(s1[14]));
			}
		    window.opener.document.getElementById('finDisb').innerHTML=trim(s1[13]);
			}else{
				window.opener.document.getElementById('lbxLoanNoHID').value="";
				window.opener.document.getElementById('loanNo').value="";
				window.opener.document.getElementById('customerName').value="";
				alert("Please select same Loan ");
			}
			window.opener.document.getElementById('disbursalTo').value="";
			window.opener.document.getElementById('businessPartnerTypeDesc').value="";
			window.opener.document.getElementById('businessPartnerTypeDescCust').value="";
			window.opener.document.getElementById('netAmount').value=sum;
			window.opener.document.getElementById('totalPayable').value="";
			window.opener.document.getElementById('adjustTotalPayable').value="0.0";
			window.opener.document.getElementById('totalReceivable').value="";
			window.opener.document.getElementById('adjustTotalReceivable').value="0.0";
			window.opener.document.getElementById('totalReceivableCustomer').value="0.0";
			//alert("Amount In Process "+trim(s1[21]));
			window.opener.document.getElementById('amountInProcessLoan').value=trim(s1[22]);
			window.opener.document.getElementById('installmentType').value=trim(s1[21]);
			window.opener.document.getElementById('editDueDate').value=trim(s1[30]);
			window.opener.document.getElementById('interestCalculationMethod').value=trim(s1[26]);
			window.opener.document.getElementById('interestFrequency').value=trim(s1[27]);
			window.opener.document.getElementById('interestCompoundingFrequency').value=trim(s1[28]);
			window.opener.document.getElementById('tenureMonths').value=trim(s1[29]);
			
			//alert('tenureMonths'+trim(s1[29]));
			window.close();
		}
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+'/disbursalSearch.do?method=searchDisbursalMakerLnik';
			self.close();
		}
	}
}


function resultOtherLoanDetails(request){
	
	//alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		var s1 = str.split("$:");
   
        document.getElementById('lbxDealNo').value=trim(s1[0]);
		document.getElementById('searchLoanNo').value=trim(s1[1]);
		document.getElementById('searchCustomerName').value=trim(s1[2]);
				
	
	 window.close();
		}

}



/*Added by arun for new Disbursal with payment enda here*/
function clearSchemeValue()
{
	if(window.opener.document.getElementById('scheme')!='')
	{
		window.opener.document.getElementById('scheme').value='';
	}
	if(window.opener.document.getElementById('lbxscheme')!='')
	{
		window.opener.document.getElementById('lbxscheme').value='';
	}
	window.close();
}
//neeraj space for dump download
function generatField()
{
	var recordID=window.opener.document.getElementById("lbxRecordID").value;
	var contextPath=window.opener.document.getElementById("contextPath").value;
	window.opener.document.getElementById("dumpDownLoad").action = contextPath+"/dumpDownLoadDispatchAction.do?method=generatField&recordID="+recordID;
	window.opener.document.getElementById("dumpDownLoad").submit();
	window.close();
}
/*function clearDeal()
{
	if(window.opener.document.getElementById('loanNo').value!='')
	{
		//alert("Clear Deal No. when LoanNo.=: "+window.opener.document.getElementById('lbxLoanNoHID').value);
		window.opener.document.getElementById('dealNo').value='';
		window.opener.document.getElementById('lbxDealNo').value='';
		window.opener.document.getElementById('collateralId').value='';
		window.opener.document.getElementById('lbxCollateralId').value='';
		window.opener.document.getElementById('loanDiv').style.display='block';
		window.opener.document.getElementById('frst').style.display='none';
		window.opener.document.getElementById('dealDiv').style.display='none';
	}
	window.close();
}*/

function clearLoan()
{
	if(window.opener.document.getElementById('dealNo').value!='')
	{
		//alert("Clear Loan No. when DealNo=: "+window.opener.document.getElementById('lbxDealNo').value);
		window.opener.document.getElementById('loanNo').value='';
		window.opener.document.getElementById('lbxLoanNoHID').value='';
		window.opener.document.getElementById('collateralId').value='';
		window.opener.document.getElementById('lbxCollateralId').value='';
		window.opener.document.getElementById('dealDiv').style.display='block';
		window.opener.document.getElementById('frst').style.display='none';
		window.opener.document.getElementById('loanDiv').style.display='none';
	}
	window.close();
}

function clearDealForDocument()
{
	if(window.opener.document.getElementById('loanNo').value!='')
	{
		//alert("Clear Deal No. when LoanNo.=: "+window.opener.document.getElementById('lbxLoanNoHID').value);
		window.opener.document.getElementById('dealNo').value='';
		window.opener.document.getElementById('lbxDealNo').value='';
	}
	window.close();
}

function clearLoanForDocument()
{
	if(window.opener.document.getElementById('dealNo').value!='')
	{
		//alert("Clear Loan No. when DealNo=: "+window.opener.document.getElementById('lbxDealNo').value);
		window.opener.document.getElementById('loanNo').value='';
		window.opener.document.getElementById('lbxLoanNoHID').value='';
	}
	window.close();
}
//manisha ajax method starts here...........

function getChequeDetail()

{  //alert("ok");
	// alert("ok...")
      if (window.opener.document.getElementById("paymentMode").value=="")
		
	   {
	
		 alert("Please Select payment Mode");
		  return false;
	   }
	else if (window.opener.document.getElementById("bankAccount").value=="")
	   {
		 alert("Please Select bankAccount LOV");
		  return false;
	   }
     
	
	 else{
	 var bankAccountId=window.opener.document.getElementById('lbxbankAccountID').value;
	 
	
	 var accountType=window.opener.document.getElementById('paymentMode').value;
	 //alert(accountType);
	 if(accountType=='Q'||accountType=='D'||accountType=='N'||accountType=='R'){
		
		 accountType='B' ;
		
	 }
	 var basePath=window.opener.document.getElementById("contextPath").value;

	 var address = basePath+"/ajaxAction.do?method=chequeDetail&accountType="+accountType;
	
	   
			var data = "bankAccountId="+bankAccountId;
			
			send_idForCheque(address, data);
		 
	        return true;
	   }     
	  	
}
 function send_idForCheque(address, data) {
		var request = getRequestObject();
		request.onreadystatechange = function () {
			result_idForCheque(request);
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	function result_idForCheque(request){

		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			var s1 = str.split("$:");
       
            window.opener.document.getElementById('lbxBankID').value=trim(s1[0]);
			window.opener.document.getElementById('bank').value=trim(s1[1]);
			window.opener.document.getElementById('lbxBranchID').value=trim(s1[2]);
			window.opener.document.getElementById('branch').value=trim(s1[3]);
			window.opener.document.getElementById('micr').value=trim(s1[4]);
			window.opener.document.getElementById('ifsCode').value=trim(s1[5]);
					
		
		 window.close();
			}

		}
	function getChequeDetailR()

	{  
	      if (window.opener.document.getElementById("receiptMode").value=="")
			
		   {
		
			 alert("Please Select receiptMode");
			  return false;
		   }
		else if (window.opener.document.getElementById("bankAccount").value=="")
		   {
			 alert("Please Select bankAccount LOV");
			  return false;
		   }
	     
		
		 else{
		 var BankAccount=window.opener.document.getElementById('lbxbankAccountID').value;
		
		 var accountType=window.opener.document.getElementById('receiptMode').value;
		 if(accountType=='Q'||accountType=='D'||accountType=='N'||accountType=='R'||accountType=='S'){
			
			 accountType='B' ;
		 }
		
		 var basePath=window.opener.document.getElementById("contextPath").value;

		 var address = basePath+"/ajaxAction.do?method=chequeDetailR&accountType="+accountType;
		
		   
				var data = "BankAccount=" + BankAccount ;
				
				send_idForChequeR(address, data);
			 
		        return true;
		   }     
		  	
	}
	 function send_idForChequeR(address, data) {
			var request = getRequestObject();
			request.onreadystatechange = function () {
				result_idForChequeR(request);
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}
		function result_idForChequeR(request){

			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
	       
	            window.opener.document.getElementById('lbxBankID').value=trim(s1[0]);
				window.opener.document.getElementById('bank').value=trim(s1[1]);
				window.opener.document.getElementById('lbxBranchID').value=trim(s1[2]);
				window.opener.document.getElementById('branch').value=trim(s1[3]);
				window.opener.document.getElementById('micr').value=trim(s1[4]);
				window.opener.document.getElementById('ifsCode').value=trim(s1[5]);
						
			
			 window.close();
				}

			}
		
		function fnhide()
		{
			if(window.opener.document.getElementById("lbxAssetFlag").value=="NON-ASSET BASED")
			{
				window.opener.document.getElementById("minMarginRate").disabled=true;
				window.opener.document.getElementById("maxMarginRate").disabled=true;
				window.opener.document.getElementById("defaultMarginRate").disabled=true;
				window.close();
			}
			else
			{
				window.opener.document.getElementById("minMarginRate").disabled=false;
				window.opener.document.getElementById("maxMarginRate").disabled=false;
				window.opener.document.getElementById("defaultMarginRate").disabled=false;	
				window.close();
			}
			
		}


		function fndisable()
		{
			var lbxReason=window.opener.document.getElementById("lbxReason").value;
			//alert("lbxReason  :  "+lbxReason);
			if(lbxReason=="CHQBOU")
				window.opener.document.getElementById("chargeFlag").removeAttribute("disabled",true);
			else
				window.opener.document.getElementById("chargeFlag").setAttribute("disabled",true);
			window.close();
		}

		
		

		function setBranchId(){
			
			if(	window.opener.document.getElementById("lbxBranchId").value=='')
			{
				alert("Please select a branch");
				return false;
			}
			else
			{
				//alert("hi");
				 var branchId=window.opener.document.getElementById('lbxBranchId').value;
				 var currBranch=window.opener.document.getElementById('branchName').value;
				 if(branchId!='')
				 {
					 var contextPath=window.opener.document.getElementById("contextPath").value;
					 var address = contextPath+"/logindata.do?method=setbranch";
					 var data = "&lbxBranchId="+branchId+"&branchName="+currBranch;
					// alert("------------data------>"+data);
					 sendbranchId(address, data);
					 return true;
				 }
			}
			 
		}
		function sendbranchId(address, data) {
			//alert("in sendDisbursalId id"+address);
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultbranch(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultbranch(request){
			
			//alert("in resultDisbursal id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				//alert("String:--->"+str);
				if(str.length>0)
				{
					var s1 = str.split("$:");
					//alert(s1);
					window.opener.document.getElementById('branch').innerHTML=trim(s1[1]);  
				//	window.opener.location.reload(true);
					window.close();
				}
			
				
			}
		}
		 
	
		function getManualDetails()
		{
			  // alert("In getManualDetails");	
			   var chargeCode =window.opener.document.getElementById('lbxCharge').value ;
			   var lbxProductId =window.opener.document.getElementById('lbxProductId').value ;
			   var lbxLoanNoHID=window.opener.document.getElementById('lbxLoanNoHID').value ;
			   var lbxSchemeId=window.opener.document.getElementById('lbxSchemeId').value ;
				 //alert("In getBaseRate"+scheme);
			   
	        	window.opener.document.getElementById("adviceType").value="";
	        	window.opener.document.getElementById("chargeAmount").value="";
	        	window.opener.document.getElementById("tdsRate").value="";
	        	window.opener.document.getElementById("taxApplicable").value="";
	        	window.opener.document.getElementById("taxInclusive").value="";
	        	window.opener.document.getElementById("taxRate1").value="";
	        	window.opener.document.getElementById("taxRate2").value="";
	        	window.opener.document.getElementById("tdsApplicable").value="";
				window.opener.document.getElementById("taxAmount2").value="";
				window.opener.document.getElementById("taxAmount1").value="";
				window.opener.document.getElementById("adviceAmount").value="";
			   
			   var contextPath =window.opener.document.getElementById('contextPath').value ;
			// alert("contextPath"+contextPath);
		       if(chargeCode!= '' &&( lbxProductId!='' || lbxSchemeId!='') ) 
		       {
				var address = contextPath+"/ajaxAction.do?method=getDefaultManualDetail";
				var data = "chargeCode="+chargeCode+"&lbxProductId="+lbxProductId+"&lbxSchemeId="+lbxSchemeId+"&lbxLoanNoHID="+lbxLoanNoHID;
			
				//alert("address: "+address+"data: "+data);
				sendBaseRateManualAdvice(address, data);
				return true;
			    }
		         else
		         {
		    	 alert("Please Select List");	
		    	 return false;
		          }
		}

		function sendBaseRateManualAdvice(address, data) {
			// alert("sendBaseRateManualAdvice"+address);
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultBaseRateManualAdvice(request);
			};
		      // alert("send_countryDetail"+address);
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}
		function resultBaseRateManualAdvice(request){
		  //  alert(request)
			if ((request.readyState == 4) && (request.status == 200))
			{
				var str = request.responseText;
				//alert(trim(str));
				//alert(trim(str).length);
				if(trim(str).length>0)
				{
					var s1 = str.split("$:");
			        
					window.opener.document.getElementById("adviceType").value=trim(s1[0]);
					window.opener.document.getElementById("chargeAmount").value=trim(s1[1]);
					//window.opener.document.getElementById("adviceAmount").value=trim(s1[1]);
					window.opener.document.getElementById("tdsRate").value=trim(s1[2]);
					var taxApplicable=trim(s1[3]);
					window.opener.document.getElementById("taxApplicable").value=taxApplicable;
					window.opener.document.getElementById("minChargeAmount").value=trim(s1[9]);
					
					if(taxApplicable=='NO')
					{
						window.opener.document.getElementById("taxAmount1").setAttribute("readOnly","true");
						window.opener.document.getElementById("taxAmount2").setAttribute("readOnly","true");
						window.opener.document.getElementById("taxAmount1").value="";
						window.opener.document.getElementById("taxAmount2").value="";	
					}
					else
					{
						window.opener.document.getElementById("taxAmount1").removeAttribute("readOnly","true");
						window.opener.document.getElementById("taxAmount2").removeAttribute("readOnly","true");
					}					
			    	window.opener.document.getElementById("taxInclusive").value=trim(s1[4]);
			       	window.opener.document.getElementById("taxRate1").value=trim(s1[5]);
			    	window.opener.document.getElementById("taxRate2").value=trim(s1[6]);
			    	window.opener.document.getElementById("tdsApplicable").value=trim(s1[7]);
			    	window.opener.document.getElementById("chargeId").value=trim(s1[8]);
			    	window.opener.document.getElementById("adviceDtChngFlag").value='N';
			    	//window.opener.document.getElementById("save").removeAttribute("disabled","true");
			    	//window.opener.document.getElementById("saveFwd").removeAttribute("disabled","true");
										
					//Parvez starts
					var taxInclusive=trim(s1[4]);
					window.opener.document.getElementById("taxInclusive").value=taxInclusive;
					if(taxInclusive=='NO')
					{
			    	window.opener.document.getElementById("taxAmount1").value= parseFloat(trim(s1[5])*trim(s1[1])/100).toFixed(2);
					
					window.opener.document.getElementById("taxAmount2").value= parseFloat(trim(s1[6])*trim(s1[1])/100).toFixed(2);
					
					var chargeAmt =0;
					var taxAmt1 = 0;
					var taxAmt2 =0;
	
					if(window.opener.document.getElementById("chargeAmount").value!="")
					{
						chargeAmt=parseFloat(removeComma(window.opener.document.getElementById("chargeAmount").value));
					}
					if(window.opener.document.getElementById("taxAmount1").value!="")
					{
						taxAmt1=parseFloat(removeComma(window.opener.document.getElementById("taxAmount1").value));
					}
					if(window.opener.document.getElementById("taxAmount2").value!="")
					{
						taxAmt2=parseFloat(removeComma(window.opener.document.getElementById("taxAmount2").value));
					}
				
					window.opener.document.getElementById("adviceAmount").value=parseFloat(chargeAmt+taxAmt1+taxAmt2).toFixed(2);
					
						
				}else
				{
				
					var chargeAmt =0;
					var taxAmt1 = 0;
					var taxAmt2 =0;
	
					if(window.opener.document.getElementById("chargeAmount").value!="")
					{
						chargeAmt=parseFloat(removeComma(window.opener.document.getElementById("chargeAmount").value));
					}
					if(window.opener.document.getElementById("taxAmount1").value!="")
					{
						taxAmt1=parseFloat(removeComma(window.opener.document.getElementById("taxAmount1").value));
					}
					
					if(window.opener.document.getElementById("taxAmount2").value!="")
					{
						taxAmt2=parseFloat(removeComma(window.opener.document.getElementById("taxAmount2").value));
					}
					
				//	window.opener.document.getElementById("adviceAmount").value=chargeAmt+taxAmt1+taxAmt2;
					
					var chamt=parseFloat(window.opener.document.getElementById("chargeAmount").value*trim(s1[6]));
					var a=(window.opener.document.getElementById("chargeAmount").value*trim(s1[5]));
					var b=100+parseInt(trim(s1[5]))+parseInt(trim(s1[6]));
					var f=100+parseInt(trim(s1[5]))+parseInt(trim(s1[6]));
					
					window.opener.document.getElementById("taxAmount1").value=parseFloat(a/b);
					window.opener.document.getElementById("taxAmount2").value=parseFloat(chamt/f);
					
					window.opener.document.getElementById("taxAmount1").value=Math.ceil(Math.round(window.opener.document.getElementById("taxAmount1").value * 100) / 100);
					window.opener.document.getElementById("taxAmount2").value=Math.ceil(Math.round(window.opener.document.getElementById("taxAmount2").value * 100) / 100);
					
					window.opener.document.getElementById("adviceAmount").value= window.opener.document.getElementById("chargeAmount").value;
					//alert("adviceAmount :: "+window.opener.document.getElementById("adviceAmount").value);
					//alert("taxAmount1 :: "+window.opener.document.getElementById("taxAmount1").value);
					//alert("taxAmount2 :: "+window.opener.document.getElementById("taxAmount2").value);
					var c=parseFloat(window.opener.document.getElementById("adviceAmount").value - window.opener.document.getElementById("taxAmount1").value - window.opener.document.getElementById("taxAmount2").value);	
				
					window.opener.document.getElementById("chargeAmount").value=parseFloat(c).toFixed(2);
				}
				}
				
				//parvez work ends here
			    window.close();
			    
			    }
			
		}
		// ********* asesh workspace **********
        function clearChargeCode()
        {
        	
        	
        	window.opener.document.getElementById("chargeCode").value="";
        	window.opener.document.getElementById("lbxCharge").value="";
        	window.opener.document.getElementById("adviceType").value="";
        	window.opener.document.getElementById("chargeAmount").value="";
        	window.opener.document.getElementById("tdsRate").value="";
        	window.opener.document.getElementById("taxApplicable").value="";
        	window.opener.document.getElementById("taxInclusive").value="";
        	window.opener.document.getElementById("taxRate1").value="";
        	window.opener.document.getElementById("taxRate2").value="";
        	window.opener.document.getElementById("tdsApplicable").value="";
        	window.opener.document.getElementById("chargeId").value="";
			window.opener.document.getElementById("taxAmount2").value="";
			window.opener.document.getElementById("taxAmount1").value="";
			window.opener.document.getElementById("adviceAmount").value="";

        	window.close();

        }
        // ********* end workspace *************
		function generateValuesLoanForLiquidation()
		{
			//alert("hi");
			 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
			 if(loanId!='')
			 {
				 window.opener.document.getElementById('sdNo').value='';  
			 	 window.opener.document.getElementById('lbxSdNoHid').value='';  
			 	 window.opener.document.getElementById('sdAmount').value='';  
				 window.opener.document.getElementById('sdInterestType').value='';
				 window.opener.document.getElementById('sdInterestRate').value='';
				 window.opener.document.getElementById('sdCompoundingFreq').value='';
				 window.opener.document.getElementById('sdStartDate').value='';
				 window.opener.document.getElementById('sdMaturityDate').value='';
				 window.opener.document.getElementById('sdInterestAccrued').value='';
				 window.opener.document.getElementById('sdInterestAccruedDate').value='';
				 window.opener.document.getElementById('sdTDSRate').value='';
				 window.opener.document.getElementById('sdTDSDeducted').value='';
				 window.opener.document.getElementById('liquidationFlag').value='P';
				 window.opener.document.getElementById('liquidatedAmountPrincipal').value='';
				 window.opener.document.getElementById('liquidatedAmountInterest').value='';
				 
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveLoanForLiquidationValues";
				 var data = "lbxLoanNoHID="+loanId;
				 sendLoanForLiquidation(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}

		function sendLoanForLiquidation(address, data) {
			//alert("in sendClosureid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultLoanForLiquidation(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultLoanForLiquidation(request){
			
			//alert("in resultClosure id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
				window.opener.document.getElementById('product').value=trim(s1[0]);  
				window.opener.document.getElementById('scheme').value=trim(s1[1]);
				window.opener.document.getElementById('loanAmt').value=trim(s1[2]);
				window.opener.document.getElementById('loanApprovalDate').value=trim(s1[3]);
				window.opener.document.getElementById('loanStatus').value=trim(s1[4]);
				window.opener.document.getElementById('disbursalStatus').value=trim(s1[5]);
				window.close();
			}
		}
		
		function generateValuesLiquidation()
		{
			//alert("hi");
			 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
			 var sdId=window.opener.document.getElementById('lbxSdNoHid').value;
			 if(loanId!='')
			 {
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveLiquidationValues";
				 var data = "lbxLoanNoHID="+loanId+"&lbxSdNoHid="+sdId;
				 sendLiquidationId(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}

		function sendLiquidationId(address, data) {
			//alert("in sendClosureid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultLiquidation(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultLiquidation(request){
			
			//alert("in resultClosure id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				//alert("String:--->"+str.length);
				if(str.length>120)
				{
					var s1 = str.split("$:");
					window.opener.document.getElementById('sdAmount').value=trim(s1[0]);  
					window.opener.document.getElementById('sdInterestType').value=trim(s1[1]);
					window.opener.document.getElementById('sdInterestRate').value=trim(s1[2]);
					window.opener.document.getElementById('sdCompoundingFreq').value=trim(s1[3]);
					window.opener.document.getElementById('sdStartDate').value=trim(s1[4]);
					window.opener.document.getElementById('sdMaturityDate').value=trim(s1[5]);
					window.opener.document.getElementById('sdInterestAccrued').value=trim(s1[6]);
					window.opener.document.getElementById('sdTotalInterest').value=trim(s1[6]);
					window.opener.document.getElementById('sdInterestAccruedDate').value=trim(s1[7]);
					window.opener.document.getElementById('sdTDSRate').value=trim(s1[8]);
					window.opener.document.getElementById('sdTDSDeducted').value=trim(s1[9]);
					window.opener.document.getElementById('liquidatedAmountPrincipal').value=trim(s1[10]);
					window.opener.document.getElementById('liquidatedAmountInterest').value=trim(s1[11]);
					window.opener.document.getElementById('sdFinalInterest').value=trim(s1[12]);
					window.close();
				}
				
				else
				{
					var contextPath=window.opener.document.getElementById("contextPath").value;
					//alert(contextPath);
					var s1 = str.split("$:");
					alert(trim(s1[0]));
					window.opener.location.href = contextPath+'/sdLiquidationSearchBehind.do?method=sdLiquidationMakerSearch';
					self.close();
				}
			}
		}
		
		
		function generateValuesCancellation(){
			//alert("hi");
			 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
			 var businessDate=window.opener.document.getElementById('businessDate').value;
			 //alert(businessDate);
			 if(loanId!='')
			 {
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveCancellationValues";
				 var data = "lbxLoanNoHID="+loanId+"&businessDate="+businessDate;
				 sendCancellationid(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}

		function sendCancellationid(address, data) {
			//alert("in sendCancellationid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultCancellation(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultCancellation(request){
			
			//alert("in resultCancellation id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
				//alert(s1);
				window.opener.document.getElementById('loanAc').value=trim(s1[0]);  
				window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[1]);
				window.opener.document.getElementById('customerName').value=trim(s1[2]);
				window.opener.document.getElementById('loanDate').value=trim(s1[3]);
				window.opener.document.getElementById('loanAmt').value=trim(s1[4]);
				window.opener.document.getElementById('product').value=trim(s1[5]);
				window.opener.document.getElementById('scheme').value=trim(s1[6]);
				window.opener.document.getElementById('originalTenure').value=trim(s1[7]);
				window.opener.document.getElementById('frequency').value=trim(s1[8]);
				window.opener.document.getElementById('maturityDate').value=trim(s1[9]);
				window.opener.document.getElementById('rate').value=trim(s1[10]);
				window.opener.document.getElementById('billedPrincipal').value=trim(s1[11]);
				window.opener.document.getElementById('disbursalStatus').value=trim(s1[12]);
				window.opener.document.getElementById('disbursedAmount').value=trim(s1[13]);
				window.opener.document.getElementById('balancePrincipal').value=trim(s1[14]);
				window.opener.document.getElementById('overduePrincipal').value=trim(s1[15]);
				window.opener.document.getElementById('billedInstallments').value=trim(s1[16]);
				window.opener.document.getElementById('billedInstallmentAmount').value=trim(s1[17]);
				window.opener.document.getElementById('receivedInstallmentAmount').value=trim(s1[18]);
				window.opener.document.getElementById('remainingTenure').value=trim(s1[19]);
				window.close();
			}
		}

		function genrateAssetVerInitValues()
		{
			//alert("hi");
			 var loanId=window.opener.document.getElementById('lbxLoanNo').value;
			 if(loanId!='')
			 {
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveAssetInitValues";
				 var data = "lbxLoanNo="+loanId;
				 sendAssetInitId(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}
function sendAssetInitId(address, data) {
			//alert("in sendClosureid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultAssetInit(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultAssetInit(request){
			
			//alert("in resultClosure id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				window.opener.document.getElementById("gridList").innerHTML=str;
				window.close();
			}
		}
		
		function statusSDesc()
		{
			
			  var machineSupplier=window.opener.document.getElementById("lbxmachineSupplier").value;
			  //alert(machineSupplier);
			  if(machineSupplier=='1')
			  {
				  window.opener.document.getElementById("supplierDesc").value='';
				  window.opener.document.getElementById("supplierDesc").removeAttribute("readOnly","false");
				  window.opener.document.getElementById("supplierDesc").setAttribute("class","text");
				  window.opener.document.getElementById("supplierDesc").focus();
			  }
			  else
			  {
				  //alert(machineSupplier);
				  window.opener.document.getElementById("supplierDesc").setAttribute("readOnly","true");
				 // window.opener.document.getElementById("supplierDesc").readOnly=true;
			  }
			  window.close();
			   
		}
		
		function statusMDesc()
		{
			
			  var assetManufact=window.opener.document.getElementById("lbxmachineManufact").value;
			  if(assetManufact=='2')
			  {
				  window.opener.document.getElementById("assetManufactDesc").value='';
				  window.opener.document.getElementById("assetManufactDesc").removeAttribute("readOnly","true");
				  window.opener.document.getElementById("assetManufactDesc").setAttribute("class","text");
				  window.opener.document.getElementById("assetManufactDesc").focus();
			  }
			  else
			  {
				  window.opener.document.getElementById("assetManufactDesc").setAttribute("readOnly","true");
			  }
			  window.close();
		}
		
		
	
		
function checkDealSanctionVaildTill()
{
	//alert("hi");
	 var dealId=window.opener.document.getElementById('lbxDealNo').value;
	 var businessDate=window.opener.document.getElementById('bDate').value;
	 //alert(businessDate);
	 if(dealId!='')
	 {
		 var contextPath=window.opener.document.getElementById("basePath").value;
		 var address = contextPath+"/ajaxAction.do?method=checkDealSanctionVaildTill";
		 var data = "dealId="+dealId+"&businessDate="+businessDate;
		 sendCheckDealSanctionVaildTillId(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Please Select One Record");	
    	 return false;
     }
	 
}

function sendCheckDealSanctionVaildTillId(address, data) {
	//alert("in sendCheckDealSanctionVaildTillId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCheckDealSanctionVaildTill(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultCheckDealSanctionVaildTill(request){
	
	//alert("in resultCheckDealSanctionVaildTill ");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("str: "+trim(str));
		//alert("str length: "+str.length);
		if(trim(str)=='T')
		{
			window.close();
		}
		
		else
		{
			var contextPath=window.opener.document.getElementById("basePath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+"/JSP/CMJSP/loanDetails.jsp";
			
			
		}
	}
}


function generatePartPrepaymentValues()
{
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 window.opener.document.getElementById("partPaymentDate").value='';
		 window.opener.document.getElementById("lbxInstlNo").value='';
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retrivePartPrePaymentValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendPartPaymentid(address, data);
		 return true;
	 }
	 else
    {
   	 alert("Please Select One Record");	
   	 return false;
    }
	 
}

function sendPartPaymentid(address, data) {
	//alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultPartpayment(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultPartpayment(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		if(str.length>120)
		{
			var s1 = str.split("$:");
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[0]);  
			window.opener.document.getElementById('loanNo').value=trim(s1[1]);
			window.opener.document.getElementById('customerName').value=trim(s1[2]);
			window.opener.document.getElementById('product').value=trim(s1[3]);
			window.opener.document.getElementById('scheme').value=trim(s1[4]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[5]);
			window.opener.document.getElementById('outstandingLoanAmount').value=trim(s1[6]);
			window.opener.document.getElementById('partNextDueDate').value=trim(s1[7]);
			window.opener.document.getElementById('partPaymentSinceDisbursal').value=trim(s1[8]);
			window.opener.document.getElementById('lastPartPaymentDate').value=trim(s1[9]);
			window.opener.document.getElementById('installmentType').value=trim(s1[10]);
			window.opener.document.getElementById('lbxInstlNo').value=trim(s1[11]);
			window.opener.document.getElementById('partPrePaymentCal').value=trim(s1[12]);
			window.opener.document.getElementById('amount').value=trim(s1[13]);
// add by saorabh
			var recStatusForDisbursal = trim(s1[14]);
			//alert("recStatusForDisbursal : "+recStatusForDisbursal);
			if(recStatusForDisbursal==1)
				{
				alert('Loan is pending for Disbursal Approval');
				var contextPath=window.opener.document.getElementById("contextPath").value;
				window.opener.location.href = contextPath+'/partPrePaymentSearchBehind.do?method=partPrePaymetMakerSearch';
				}
			// end by saorabh
			if(trim(s1[10])=='I')
			{
				window.opener.document.getElementById("partPaymentDate").value=window.opener.document.getElementById("bDate").value;
				window.opener.document.getElementById('partPaymentParameter').removeAttribute("disabled","true");
				//window.opener.document.getElementById('partPaymentDateButton').removeAttribute("disabled","true");
				//window.opener.document.getElementById("partPaymentDate").removeAttribute("disabled","true");
			}
			else if(trim(s1[10])=='N')
			{
				window.opener.document.getElementById("partPaymentDate").value=window.opener.document.getElementById("bDate").value;
				window.opener.document.getElementById('partPaymentParameter').setAttribute("disabled","true");
				//window.opener.document.getElementById('partPaymentDateButton').setAttribute("disabled","true");
				//window.opener.document.getElementById("partPaymentDate").setAttribute("disabled","true");
			}
			//alert("s1[15]"+s1[15]);
			window.opener.document.getElementById('intsTypeE').innerHTML=trim(s1[15]);
			window.close();
		}
		
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+'/partPrePaymentSearchBehind.do?method=partPrePaymetMakerSearch';
			self.close();
		}
	}
}


function bringLoanDataForPool(){
	
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var lbxLoanNoHID = window.opener.document.getElementById("lbxLoanNoHID").value;		
	     var address = contextPath+"/poolIdMakerProcessAction.do?method=retriveValueByLoanforPool";
	     //alert("ok saveRecord"+address);
			var data = "lbxLoanNoHID=" + lbxLoanNoHID;
			send_id_pool(address,data);
		     return true;
	
	
}

function send_id_pool(address, data) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_id_pool(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}


function result_id_pool(request){
	//alert("In result Dues Refund");

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;		 
		var s1 = str.split("$:");
		window.opener.document.getElementById('loanID').value=trim(s1[0]);  
		window.opener.document.getElementById('loanProduct').value=trim(s1[1]);
		window.opener.document.getElementById('loanScheme').value=trim(s1[2]);
		window.opener.document.getElementById('loanCustomerID').value=trim(s1[3]);
		window.opener.document.getElementById('loanCustomerType').value=trim(s1[4]);
		window.opener.document.getElementById('loanCustomerCtegory').value=trim(s1[5]);
		window.opener.document.getElementById('loanCustomerConstituion').value=trim(s1[6]);
		window.opener.document.getElementById('loanCustomerBusinessSeg').value=trim(s1[7]);
		window.opener.document.getElementById('loanIndustry').value=trim(s1[8]);
		window.opener.document.getElementById('loanSubIndustry').value=trim(s1[9]);
		window.opener.document.getElementById('loanDisbursalDate').value=trim(s1[10]);
		window.opener.document.getElementById('loanMaturityDate').value=trim(s1[11]);
		window.opener.document.getElementById('loanTenure').value=trim(s1[12]);
		window.opener.document.getElementById('loanBalanceTenure').value=trim(s1[13]);
		window.opener.document.getElementById('loanInstallmentNum').value=trim(s1[14]);
		
		window.opener.document.getElementById('loanAdvEMINUm').value=trim(s1[15]);  
		window.opener.document.getElementById('loanInitRate').value=trim(s1[16]);
		window.opener.document.getElementById('loanDisbursalStatus').value=trim(s1[17]);
		window.opener.document.getElementById('loanNPAFlag').value=trim(s1[18]);
		window.opener.document.getElementById('loanDPD').value=trim(s1[19]);
		
		window.opener.document.getElementById('loanDPDString').value=trim(s1[20]);
		window.opener.document.getElementById('loanAssetCost').value=trim(s1[21]);
		window.opener.document.getElementById('loanAmount').value=trim(s1[22]);
		window.opener.document.getElementById('loanEMI').value=trim(s1[23]);
		window.opener.document.getElementById('loanAdvEMIAmount').value=trim(s1[24]);
		window.opener.document.getElementById('loanBalPrincipal').value=trim(s1[25]);
		window.opener.document.getElementById('loanOverduePrincipal').value=trim(s1[26]);
		window.opener.document.getElementById('loanReceivedPrincipal').value=trim(s1[27]);
		window.opener.document.getElementById('loanOverdueInstNo').value=trim(s1[28]);
		window.opener.document.getElementById('loanOverdueAmount').value=trim(s1[29]);

		window.opener.document.getElementById('loanBalnceInstNo').value=trim(s1[30]);
		window.opener.document.getElementById('loanBalInstlAmount').value=trim(s1[31]);
      window.close();
     
		
		
		
		
	}
	
}
function insnonins(){
	var contextPath=window.opener.document.getElementById("contextPath").value;
	 
	var lbxLoanNoHID = window.opener.document.getElementById("lbxLoanNoHID").value;	
	//alert(lbxLoanNoHID);
	var address = contextPath+"/instrumentCapProcessActionforNew.do?method=insNonIns";
    //alert("ok saveRecord"+address);
		var data = "lbxLoanNoHID="+lbxLoanNoHID;
		send_id_insnonins(address,data);
	     return true;
	
	
}
function send_id_insnonins(address, data) {
//	alert(address+""+data);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_id_insnonins(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}


function result_id_insnonins(request){
	 

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;	
	//	alert(str);
	   var s1 = str.split("$:");
		window.opener.document.getElementById('loanInstallmentMode').value=trim(s1[0]); 
		window.opener.document.getElementById('loanAdvanceInstallment').value=trim(s1[1]); 
		window.opener.document.getElementById('installmentType').value=trim(s1[2]);
		window.opener.document.getElementById('otherInstallmentCharges').value=trim(s1[3]);
		window.opener.document.getElementById('totalChargeInstallmentAmount').value=trim(s1[4]);
		window.opener.document.getElementById('repayment').value=trim(s1[5]);
      window.close();
		
	}
	
}

function clearBranchLovChild()
{
	//alert("In clearBranchLovChild");
	window.opener.document.getElementById("product").value="";
 	window.opener.document.getElementById("lbxProductID").value="";
	window.opener.document.getElementById("scheme").value="";
 	window.opener.document.getElementById("lbxscheme").value="";
 	window.close();
		
}

function clearProductLovChild()
{
	//alert("In clearProductLovChild");
	window.opener.document.getElementById("scheme").value="";
 	window.opener.document.getElementById("lbxscheme").value="";
 	window.close();	
}

function generateDeferralValues()
{
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveDeferralValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendDeferralid(address, data);
		 return true;
	 }
	 else
    {
   	 alert("Please Select One Record");	
   	 return false;
    }
	 
}

function sendDeferralid(address, data) {
	//alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDeferral(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultDeferral(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		if(str.length>220)
		{
			var s1 = str.split("$:");
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[0]);  
			window.opener.document.getElementById('loanNo').value=trim(s1[1]);
			window.opener.document.getElementById('customerName').value=trim(s1[2]);
			window.opener.document.getElementById('product').value=trim(s1[3]);
			window.opener.document.getElementById('scheme').value=trim(s1[4]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[5]);
			window.opener.document.getElementById('outstandingLoanAmount').value=trim(s1[6]);
			window.opener.document.getElementById('deferralsSinceDsibursal').value=trim(s1[7]);
			window.opener.document.getElementById('lastDeferralDate').value=trim(s1[8]);
			window.opener.document.getElementById('deferralParams').innerHTML=trim(s1[9]);
			window.close();
		}
		
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+'/deferralSearchBehind.do?method=deferralMakerSearch';
			self.close();
		}
	}
}

function ScoreChecked(){
	
	if(window.opener.document.getElementById('lbxColumn').value == 'int'){
		window.opener.document.getElementById('dataType1').checked = true;
		window.opener.document.getElementById('dataType2').checked = false;
		
	}else if(window.opener.document.getElementById('lbxColumn').value == 'varchar'){
		window.opener.document.getElementById('dataType1').checked = false;
		window.opener.document.getElementById('dataType2').checked = true;
	}else {
		window.opener.document.getElementById('dataType1').checked = false;
		window.opener.document.getElementById('dataType2').checked = true;
	}
	window.close();
}

function ScoreColumn(){
	
		window.opener.document.getElementById('lbxColumn').value = "";
		window.opener.document.getElementById('sourceColumn').value = "";
		window.close();

}

function generateRepricingValues()
{
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveRepricingValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendRepricingid(address, data);
		 return true;
	 }
	 else
    {
   	 alert("Please Select One Record");	
   	 return false;
    }
	 
}

function sendRepricingid(address, data) {
	//alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultRepricing(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultRepricing(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		if(str.length>300)
		{
			var s1 = str.split("$:");
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[0]);  
			window.opener.document.getElementById('loanNo').value=trim(s1[1]);
			window.opener.document.getElementById('customerName').value=trim(s1[2]);
			window.opener.document.getElementById('product').value=trim(s1[3]);
			window.opener.document.getElementById('scheme').value=trim(s1[4]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[5]);
			window.opener.document.getElementById('outstandingLoanAmount').value=trim(s1[6]);
			window.opener.document.getElementById('rePricingSinceDsibursal').value=trim(s1[7]);
			window.opener.document.getElementById('effectiveRate').value=trim(s1[8]);
			window.opener.document.getElementById('flatRate').value=trim(s1[9]);
			window.opener.document.getElementById('emi').value=trim(s1[10]);  
			window.opener.document.getElementById('lastRePricingDate').value=trim(s1[11]);
			window.opener.document.getElementById('rePricingParams').innerHTML=trim(s1[12]);
			

			window.close();
		}
		
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			//alert(contextPath);
			var s1 = str.split("$:");
			alert(trim(s1[0]));
			window.opener.location.href = contextPath+'/rePricingSearchBehind.do?method=rePricingMakerSearch';
			self.close();
		}
	}

}

function getLeadInfo()
{
	var lbxLeadNo=window.opener.document.getElementById('lbxLeadNo').value;
	//alert("getLeadInfo: lead No.  "+lbxLeadNo);
	//alert("hi");
	// var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(lbxLeadNo!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxActionForCP.do?method=retriveLeadInfo";
		 var data = "lbxLeadNo="+lbxLeadNo;
		 sendLeadInfo(address, data);
		 return true;
	 }
	 else
   {
  	 alert("Please Select One Record");	
  	 return false;
   }
	 
}

function sendLeadInfo(address, data) {
	//alert("in sendPartPaymentid id"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultLeadInfo(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultLeadInfo(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var s = request.responseText;
		
		if(s.length>0)
		{
			var s1 = s.split("$:");
/*			alert("String:--->"+s1[5]);*/
			window.opener.document.getElementById("leadGeneratorByDrop").setAttribute("disabled",true);
			window.opener.document.getElementById("leadGeneratorByDrop").style.display='none';
			
			window.opener.document.getElementById("leadGeneratorByBox").removeAttribute("disabled",true);
			window.opener.document.getElementById("leadGeneratorByDesc").removeAttribute("disabled",true);			
			window.opener.document.getElementById("leadGeneratorByBox").style.display='';
			window.opener.document.getElementById("leadGeneratorByDesc").style.display='';
			
			window.opener.document.getElementById("leadGeneratorByBox").value=trim(s1[0]);
			window.opener.document.getElementById("leadGeneratorByDesc").value=trim(s1[10]);
	
			window.opener.document.getElementById('source').value=trim(s1[7]);
			window.opener.document.getElementById('lovSourceDes').value=trim(s1[8]);
			window.opener.document.getElementById('sourcedesc').value=trim(s1[9]);
			window.opener.document.getElementById('lbxRelationship').value=trim(s1[3]);
			window.opener.document.getElementById('relationshipManager').value=trim(s1[4]);
			window.opener.document.getElementById('lbxareaCodeVal').value=trim(s1[5]);
			window.opener.document.getElementById('areaCodename').value=trim(s1[6]);
//			
//			
//			
//			
//			
//			if(trim(s1[0])=='VENDOR')
//			{
//				window.opener.document.getElementById("sourcedesc").value='';
//				window.opener.document.getElementById("vendorCode").value='';
//				window.opener.document.getElementById("lbxvendorCode").value='';
//				window.opener.document.getElementById("sourcedesc").setAttribute("readOnly", "true");
//				window.opener.document.getElementById("vendorCodeButton").removeAttribute("disabled", "true");
//				window.opener.document.getElementById("vendorCode").setAttribute("readOnly", "true");
//				window.opener.document.getElementById('source').value=trim(s1[0]);
//				window.opener.document.getElementById('sourcedesc').value=trim(s1[1]);
//				window.opener.document.getElementById('vendorCode').value=trim(s1[2]);
//				window.opener.document.getElementById('lbxvendorCode').value=trim(s1[2]);
//				
//				
//				window.opener.document.getElementById('relationshipManager').value=trim(s1[4]);
//				window.opener.document.getElementById('lbxRelationship').value=trim(s1[2]);
//				window.opener.document.getElementById('lbxareaCodeVal').value=trim(s1[5]);
//				window.opener.document.getElementById('areaCodename').value=trim(s1[6]);
//				
//				
//				
//				
//				
//			}
//			else
//			{
//				window.opener.document.getElementById("sourcedesc").value='';
//				window.opener.document.getElementById("vendorCode").value='';
//				window.opener.document.getElementById("lbxvendorCode").value='';
//				window.opener.document.getElementById("sourcedesc").removeAttribute("readOnly", "true");
//				window.opener.document.getElementById("vendorCodeButton").setAttribute("disabled", "true");
//				window.opener.document.getElementById("vendorCode").setAttribute("readOnly", "true");
//				window.opener.document.getElementById('source').value=trim(s1[0]);
//				window.opener.document.getElementById('sourcedesc').value=trim(s1[1]);
//				window.opener.document.getElementById('lbxRelationship').value=trim(s1[3]);
//				window.opener.document.getElementById('relationshipManager').value=trim(s1[4]);
//				window.opener.document.getElementById('lbxareaCodeVal').value=trim(s1[5]);
//				window.opener.document.getElementById('areaCodename').value=trim(s1[6]);
//				
//				//window.opener.document.getElementById('vendorCode').value=trim(s1[2]);
//			}
//			if(trim(s1[0])=='RM')
//			{
//				window.opener.document.getElementById("sourcedesc").value='';
//				window.opener.document.getElementById("vendorCode").value='';
//				window.opener.document.getElementById("lbxvendorCode").value='';
//				window.opener.document.getElementById('relationshipManager').value=trim(s1[4]);
//				window.opener.document.getElementById('lbxRelationship').value=trim(s1[2]);
//				window.opener.document.getElementById('lbxareaCodeVal').value=trim(s1[5]);
//				window.opener.document.getElementById('areaCodename').value=trim(s1[6]);
//				
//			}
			self.close();
		}
				
	}
}

//saurabh sub ledger report starts 
function getLegderDetail()
{
	//alert("in getLegderDetail method");
	var lbxSubLedgerId=window.opener.document.getElementById('lbxSubLedgerId').value;
	//alert("value of sub ledger::::::"+lbxSubLedgerId);

		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/glBillCaptureAjaxForSubLedgerReport.do?action=glBillCaptureAjaxForSubLedgerReport&subledgerID=lbxSubLedgerId";
		 var data = "lbxSubLedgerId="+lbxSubLedgerId;
		 sendLedgerInfo(address, data);

}

function sendLedgerInfo(address, data) {
	//alert("in sendLedgerInfo id"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultLedgerInfo(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultLedgerInfo(request){
	
	//alert("in resultLedgerInfo id"+request);
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		window.opener.document.getElementById("legderShow1").innerHTML=str;	    
		window.close();
		
	}
}

//saurabh sub ledger reports ends


function leadVendorDesc()
{
	
	  var vendor=window.opener.document.getElementById("lbxvendorCode").value;
	  //alert(vendor);
	  if(vendor=='1'|| vendor=='2')
	  {
		  window.opener.document.getElementById("sourcedesc").value='';
		  window.opener.document.getElementById("sourcedesc").removeAttribute("readOnly","false");
		  window.opener.document.getElementById("sourcedesc").setAttribute("class","text");
		  window.opener.document.getElementById("sourcedesc").focus();
	  }
	  else
	  {
		  //alert(machineSupplier);
		  window.opener.document.getElementById("sourcedesc").setAttribute("readOnly","true");
		 // window.opener.document.getElementById("supplierDesc").readOnly=true;
	  }
	  window.close();
	   
}


function clearBPDesc()
{
	window.opener.document.getElementById("businessPartnerType").value="";
 	window.opener.document.getElementById("hBPType").value="";
 	window.opener.document.getElementById("businessPartnerName").value="";
 	window.opener.document.getElementById("lbxBusinessPartnearHID").value="";
 	window.close();
}

function bpTypeNull(){
	window.opener.document.getElementById('businessPartnerType').value="";	
	window.opener.document.getElementById('lbxBPType').value="";
	window.opener.document.getElementById("businessPartnerName").value="";
 	window.opener.document.getElementById("lbxBPNID").value="";
 	window.opener.document.getElementById("payTo").value="";
	window.opener.document.getElementById("lbxpayTo").value="";
	window.opener.document.getElementById("payeeName").value="";
	//window.opener.document.getElementById("lbxpayeeName").value="";
 	window.close();
	 return true;
}
 

function getDefaultBusinessPartnerTypeReceipt()
{
	var lbxLoanNoHID=window.opener.document.getElementById('lbxLoanNoHID').value;
	var bpType=window.opener.document.getElementById('lbxBPType').value;
	 if(lbxLoanNoHID!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 //var address = contextPath+"/ajaxAction.do?method=getDefaultBusinessPartnerTypeReceipt";
		 //var data = "lbxLoanNoHID="+lbxLoanNoHID;
		 //sendGetDefaultBusinessPartnerTypeReceipt(address, data);
		 window.opener.location.href = contextPath+"/receiptMakerProcessAction.do?method=getDefaultBusinessPartnerTypeReceipt&lbxLoanNoHID="+lbxLoanNoHID+"&bpType="+bpType+"";
		 self.close();
		 return true;
	 }
	 else
	 {
		 alert("Please Select One Record");	
  	 	 return false;
	 }
	 
}

function getDefaultBusinessPartnerTypeCS()
{
	var lbxLoanNoHID=window.opener.document.getElementById('lbxLoanNoHID').value;
	var bpType='CS';
	 if(lbxLoanNoHID!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 //var address = contextPath+"/ajaxAction.do?method=getDefaultBusinessPartnerTypeReceipt";
		 //var data = "lbxLoanNoHID="+lbxLoanNoHID;
		 //sendGetDefaultBusinessPartnerTypeReceipt(address, data);
		 window.opener.location.href = contextPath+"/receiptMakerProcessAction.do?method=getDefaultBusinessPartnerTypeReceipt&lbxLoanNoHID="+lbxLoanNoHID+"&bpType="+bpType+"";
		 self.close();
		 return true;
	 }
	 else
	 {
		 alert("Please Select One Record");	
  	 	 return false;
	 }
	 
}
function getOldAssetsInGrid()
{
	var loanid=window.opener.document.getElementById('lbxLoanNoHID').value;
	var loanNo=window.opener.document.getElementById('loanAccountNumber').value;
	var custName=window.opener.document.getElementById('customerName').value;
	
	 if(loanid!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;		
		 window.opener.location.href = contextPath+"/assetProcessAction.do?method=onAssetInsuranceViewerMaker&loanId="+loanid+"&loanNo="+loanNo+"&custName="+custName;
		 self.close();
		 return true;
	 }
	 else
	 {
		 alert("Please Select One Record");	
  	 	 return false;
	 }	 
}
/* function sendGetDefaultBusinessPartnerTypeReceipt(address, data) {
	//alert("in sendPartPaymentid id"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_getDefaultBusinessPartnerTypeReceipt(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function result_getDefaultBusinessPartnerTypeReceipt(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var s = request.responseText;
		
		if(s.length>0)
		{
			var tdsreceiptStatus=window.opener.document.getElementById('tdsreceiptStatus').value;
			
			var s1 = s.split("$:");
			window.opener.document.getElementById('businessPartnerType').value="CUSTOMER";	
			window.opener.document.getElementById('lbxBPType').value="CS";
			window.opener.document.getElementById('lbxBPNID').value=trim(s1[0]);
	    	window.opener.document.getElementById('businessPartnerName').value=trim(s1[1]);
	    	window.opener.document.getElementById('loanRepaymentType').value=trim(s1[2]);
	    	window.opener.document.getElementById('totalRecevable').value=trim(s1[3]);
	    	if(trim(s1[2])=='I')
	    	{
	    		if(tdsreceiptStatus=='A' || tdsreceiptStatus=='I')
	    		{
	    			window.opener.document.getElementById('tdsAmount').value='0.00';
	    			window.opener.document.getElementById("tdsAmount").removeAttribute("readOnly",true);
	    		}
	    		else if(tdsreceiptStatus=='N' || tdsreceiptStatus=='B')
	    		{
	    			window.opener.document.getElementById('tdsAmount').value='0.00';
	    			window.opener.document.getElementById("tdsAmount").setAttribute("readOnly",true);
	    		}
	    	}
	    	else
	    	{
	    		if(tdsreceiptStatus=='A' || tdsreceiptStatus=='N')
	    		{
	    			window.opener.document.getElementById('tdsAmount').value='0.00';
	    			window.opener.document.getElementById("tdsAmount").removeAttribute("readOnly",true);
	    		}
	    		else if(tdsreceiptStatus=='I' || tdsreceiptStatus=='B')
	    		{
	    			window.opener.document.getElementById('tdsAmount').value='0.00';
	    			window.opener.document.getElementById("tdsAmount").setAttribute("readOnly",true);
	    		}
	    	}
	    	
	    	window.close();
		}
				
	}
} */

function payeeNameRefresh()
{
	window.opener.document.getElementById("payeeName").value="";
	window.opener.document.getElementById("lbxpayeeName").value="";
	var lbxpayTo=window.opener.document.getElementById("lbxpayTo").value;
	if(lbxpayTo=='OTH')
	{ 
		
		window.opener.document.getElementById("payeeName").removeAttribute("readOnly","true");
		window.opener.document.getElementById("payeeName").removeAttribute("tabIndex","-1");
		window.opener.document.getElementById("payeeNameButton").setAttribute("disabled","true");
		window.opener.document.getElementById("payeeNameButtonRSP").setAttribute("disabled","true");
		window.close();
	}
	else if(lbxpayTo=='RSP'){
		/*window.opener.document.getElementById("payeeName").value="";
		window.opener.document.getElementById("lbxpayeeName").value="";
		var lbxpayTo=window.opener.document.getElementById("lbxpayTo").value;*/
		
		
		if(lbxpayTo=='RSP')
		{
			//window.opener.document.getElementById("payeeName").removeAttribute("readOnly","true");
			window.opener.document.getElementById("payeeName").removeAttribute("tabIndex","-1");
			window.opener.document.getElementById("payeeNameButton").setAttribute("disabled","true");
			
			//window.opener.document.getElementById("payeeNameDiv").style.display='';
			window.opener.document.getElementById("payeeNameButton").style.display='none';
			window.opener.document.getElementById("payeeNameButtonRSP").style.display='';
			window.opener.document.getElementById("payeeNameButtonRSP").removeAttribute("disabled","true");
			window.close();
		}
		
			else
				window.close();
	}
	else 
	{
		window.opener.document.getElementById("payeeName").setAttribute("readOnly","true");
		window.opener.document.getElementById("payeeNameButton").removeAttribute("disabled","true");
		window.opener.document.getElementById("payeeName").setAttribute("tabIndex","-1");	
		window.opener.document.getElementById("payeeNameButton").style.display='';
		window.opener.document.getElementById("payeeNameButtonRSP").style.display='none';		
		
		if(lbxpayTo=='CS')
		{			
			var lbxLoanNoHID=window.opener.document.getElementById("lbxLoanNoHID").value;
			var contextPath=window.opener.document.getElementById("contextPath").value;
			var address = contextPath+"/ajaxAction.do?method=getPayeeName";
			var data = "lbxLoanNoHID="+lbxLoanNoHID;
			sendPayeeName(address, data);
		}
		 else
			window.opener.document.getElementById("payeeNameButton").style.display='';
			
		window.opener.document.getElementById("payeeNameButtonRSP").style.display='none';	
			window.close();
	}
	
	
}
function sendPayeeName(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultPayeeName(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultPayeeName(request)
{	
	if ((request.readyState == 4) && (request.status == 200)) 
	{	
		var str = request.responseText;
		var s1 = str.split("$:");
		window.opener.document.getElementById('lbxpayeeName').value=trim(s1[0]); 
		window.opener.document.getElementById('payeeName').value=trim(s1[1]);
		window.close();
	}
}

function documentCollectionDisableLoanLOV()
{
	 window.opener.document.getElementById("dealNoFromSButton").setAttribute("disabled", true);
	 window.opener.document.getElementById("dealNoToSButton").setAttribute("disabled", true);
	 window.close();
}

function documentCollectionDisabledDealLOV()
{
	 window.opener.document.getElementById("loanToButton").setAttribute("disabled", true);
	 window.opener.document.getElementById("loanFromButton").setAttribute("disabled", true);
	 window.close();
	}

//Abhimanyu
function changeParameterType()
{
//alert("ok");
	//opener.document.getElementById('errorsavemsg').style.display='none'; 
	var type=opener.document.getElementById('type').value;
	//alert(type);
	if(type=='N')
	{
		
		opener.document.getElementById('numericFrom').style.display='';
		opener.document.getElementById('numericTo').style.display='';		
		opener.document.getElementById('nfrom').style.display='';
		opener.document.getElementById('nto').style.display='';
		
		opener.document.getElementById('charecter').style.display='none';
		opener.document.getElementById('paramchar').style.display='none';
	}
	else if(type=='A')
	{
		opener.document.getElementById('charecter').style.display='';
		opener.document.getElementById('paramchar').style.display='';
		opener.document.getElementById('numericFrom').style.display='none';
		opener.document.getElementById('numericTo').style.display='none';
		opener.document.getElementById('nfrom').style.display='none';
		opener.document.getElementById('nto').style.display='none';
	}
	else
	{		
		opener.document.getElementById('numericFrom').style.display='';
		opener.document.getElementById('numericTo').style.display='';		
		opener.document.getElementById('nfrom').style.display='';
		opener.document.getElementById('nto').style.display='';
		opener.document.getElementById('charecter').style.display='';
		opener.document.getElementById('paramchar').style.display='';
		
		
		
	}
	//opener.document.getElementById('errormsg').style.display='none';
	//document.getElementById('parameterCode').value='';
	//document.getElementById('type').value='';
	opener.document.getElementById('numericFrom').value='';
	opener.document.getElementById('numericTo').value='';
	opener.document.getElementById('charecter').value='';
	//document.getElementById('score').value='';
	//alert("END");
	window.close();
	return true;
}
//function getParameterDef()
//{
//	//alert(window.closed);
//	
//	     var scorecardid=document.getElementById("scoreCardId").value;
//	     document.getElementById('errorsavemsg') .style.display='none'; 
//    	 var contextPath=document.getElementById("contextPath").value;
//    	 document.location.href=contextPath+"/parameterScoreBusiness.do?method=getParameterDef&scorecardid="+scorecardid;
//	
//    	//window.close();
// }

function getPayTo()
{
	var lbxBPType=opener.document.getElementById('lbxBPType').value;	
	
	window.opener.document.getElementById("taLoanNo").value='';
	window.opener.document.getElementById("lbxTaLoanNoHID").value='';
	window.opener.document.getElementById("taCustomerName").value='';
	window.opener.document.getElementById("taStatus").checked='';
	window.close();
	//if(lbxBPType=='CS' || lbxBPType=='SU' || lbxBPType=='MF')
	//{
		window.opener.document.getElementById("payToButton").removeAttribute("disabled", true);
	
		
	//}
	//else
	//{
		  opener.document.getElementById('payeeName').value='';
		 opener.document.getElementById('lbxpayeeName').value='';
		  opener.document.getElementById('payTo').value='';
		  opener.document.getElementById('lbxpayTo').value='';
		 // window.opener.document.getElementById("payToButton").setAttribute("disabled", true);
		 
		//window.opener.document.getElementById("payToButton").removeAttribute("disabled", true);
	//}
	window.close();

}

function sponsorbankcode_desc(){
	var GenericId=window.opener.document.getElementById('lbxGenericId').value;
	
	if(GenericId=='SPONSOR_BANK_CODE')
	{
		window.opener.document.getElementById('value').setAttribute("onkeypress","return isNumberKey(event);");
	}else
		window.opener.document.getElementById('value').removeAttribute("onkeypress","return isNumberKey(event);");
	}
	
//Limit Enhancement by Ankit




function generateAdditionalDisbValues()
{
	 //alert("generateAdditionalDisbValues");
	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	 if(loanId!='')
	 {
		 //window.opener.document.getElementById("partPaymentDate").value='';
		 window.opener.document.getElementById("lbxInstlNo").value='';
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveAddDisbValues";
		 var data = "lbxLoanNoHID="+loanId;
		 sendAddDisbid(address, data);
		 return true;
	 }
	 else
    {
   	 alert("Please Select One Record");	
   	 return false;
    }
	 
}

function sendAddDisbid(address, data) {
	//alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultAddDisb(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultAddDisb(request){
	
	//alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
	//	alert("String:--->"+str);
		if(trim(str).length>0)
		{
			var s1 = str.split("$:");
			window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[2]);  
			window.opener.document.getElementById('loanNo').value=trim(s1[3]);
			window.opener.document.getElementById('customerName').value=trim(s1[4]);
			window.opener.document.getElementById('product').value=trim(s1[5]);
			window.opener.document.getElementById('scheme').value=trim(s1[6]);
			window.opener.document.getElementById('sanctionedAmount').value=trim(s1[0]);
			window.opener.document.getElementById('utilizedAmount').value=trim(s1[1]);
			window.opener.document.getElementById('disbursedAmount').value=trim(s1[7]);
			window.opener.document.getElementById('outstandingLoanAmount').value=trim(s1[8]);
			//alert(trim(s1[9]));
			window.opener.document.getElementById('nextDueDate').value=trim(s1[9]);
			window.opener.document.getElementById('installmentType').value=trim(s1[10]);
			window.opener.document.getElementById('lbxInstlNo').value=trim(s1[11]);
			
			
			if(trim(s1[10])=='I')
			{
				//window.opener.document.getElementById("partPaymentDate").value=window.opener.document.getElementById("bDate").value;
				window.opener.document.getElementById('rescheduleParameter').removeAttribute("disabled","true");
				//window.opener.document.getElementById('partPaymentDateButton').removeAttribute("disabled","true");
				//window.opener.document.getElementById("partPaymentDate").removeAttribute("disabled","true");
			}
			else if(trim(s1[10])=='N')
			{
				//window.opener.document.getElementById("partPaymentDate").value=window.opener.document.getElementById("bDate").value;
				window.opener.document.getElementById('rescheduleParameter').setAttribute("disabled","true");
				//window.opener.document.getElementById('partPaymentDateButton').setAttribute("disabled","true");
				//window.opener.document.getElementById("partPaymentDate").setAttribute("disabled","true");
			}
			window.opener.document.getElementById('intsTypeVar').innerHTML=trim(s1[12]);
			//alert('intsTypeVar.....'+trim(s1[12]));
			window.close();
		}
	
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			alert("Additional Disbursal is Pending for this Loan");
			window.opener.location.href = contextPath+'/additionalDisbursalMaker.do?method=searchAdditionalDisbursalMaker';
			self.close();
		}
	}
}

function sponsorbankcode_desc(){
	var GenericId=window.opener.document.getElementById('lbxGenericId').value;
	
	if(GenericId=='SPONSOR_BANK_CODE')
	{
		window.opener.document.getElementById('value').setAttribute("onkeypress","return isNumberKey(event);");
	}else
	{
		window.opener.document.getElementById('value').removeAttribute("onkeypress","return isNumberKey(event);");
	}
	
}


//Manish Space
function genrateValuesForLimitEnhance()
{
	var loanId=window.opener.document.getElementById('lbxLoanNo').value;
	var lbxDealNo = window.opener.document.getElementById('lbxDealNo').value;
	
	if(lbxDealNo!==''){
			 var contextPath=window.opener.document.getElementById("contextPath").value;
			 var address = contextPath+"/ajaxActionForCP.do?method=retriveLimitEnhancementValues";
			 var data = "lbxDealNo="+lbxDealNo;
			 sendLimitEnhanceLoanId(address, data, 'D');
			 return true;
	}else{
		if(loanId!=='')
		{
			 var contextPath=window.opener.document.getElementById("contextPath").value;
			 var address = contextPath+"/ajaxActionForCP.do?method=retriveLimitEnhancementLoanValues";
			 var data = "lbxLoanNo="+loanId;
			 sendLimitEnhanceLoanId(address, data, 'L');
			 return true;
		 }
		 else
	     {
	    	 alert("Loan No is required.");	
	    	 return false;
	     }
	}
		
		
	
}
function sendLimitEnhanceLoanId(address, data, flag) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultLimitLoanEnhancement(request,flag);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultLimitLoanEnhancement(request,flag)
{
	//alert("in resultLimitLoanEnhancement");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");	
		
		//alert(trim(s1[3])+" \ "+trim(s1[4]));
		if(flag == 'L'){
			if(trim(s1[3])!='0')
		    {
		    	alert("Loan is Pending for Disbursal.");
		    	window.opener.document.getElementById("loanNo").value = "";
		    	window.opener.document.getElementById("lbxLoanNo").value = "";
		    	window.close();
		    	return false;
		    	
		    }
			if(trim(s1[4])!='0')
		    {
		    	alert("Loan is Pending for Termination.");
		    	window.opener.document.getElementById("loanNo").value = "";
		    	window.opener.document.getElementById("lbxLoanNo").value = "";
		    	window.close();
		    	return false;
		    }
			
			window.opener.document.getElementById("oldLoanAmount").value=trim(s1[0]);
		    window.opener.document.getElementById("totalDisbursedAmount").value=trim(s1[1]);
		    window.opener.document.getElementById("loanInsType").value=trim(s1[2]);
		    //alert(trim(s1[3]));
		    
		    if(trim(s1[2])=='N')
		    {
	    	  window.opener.document.getElementById("newLoanAmount").removeAttribute("readOnly","true");
	    	  window.opener.document.getElementById("loanAmountType").removeAttribute("disabled","true");
		    }
		    else
		    {
		    	window.opener.document.getElementById("newLoanAmount").setAttribute("readOnly","true");
		    	window.opener.document.getElementById("loanAmountType").setAttribute("disabled","true");
		    }
		}else{
	    	window.opener.document.getElementById("newLoanAmount").setAttribute("readOnly","true");
	    	
			window.opener.document.getElementById("oldSenAmount").value=trim(s1[0]);
			window.opener.document.getElementById("oldSenDate").value=trim(s1[1]);
		    window.opener.document.getElementById("oldSenValidTill").value=trim(s1[3]);
		}
	    window.close();
	}
}



//Manish Space for Update Asset Vehicle 
function getValuesForUpdateAsset()
{
	var loanId=window.opener.document.getElementById('lbxLoanId').value;
	var assetId=window.opener.document.getElementById('lbxAssetId').value;
	//alert("loanId:----"+loanId);
	//alert("assetId:----"+assetId);
	if(loanId!=='')
	{
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=getUpdateVehicleValues";
		 var data = "loanId="+loanId+"&assetId="+assetId;
		 sendUpdateAssetLoanId(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Loan No is required.");	
    	 return false;
     }
}

function sendUpdateAssetLoanId(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultUpdateAssetVehicle(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultUpdateAssetVehicle(request)
{
	//alert("Test 1");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		//alert("Test 2");
		var str = request.responseText;
		//alert("String"+str);
		if(trim(str).length>0)
		{
			var s1 = str.split("$:");
			//alert("Splited"+s1);
			window.opener.document.getElementById("assetNature").value=trim(s1[0]);
			window.opener.document.getElementById("assetsCollateralDesc").value=trim(s1[1]);
		    window.opener.document.getElementById("vehicleMake").value=trim(s1[2]);
		    window.opener.document.getElementById("vehicleModel").value=trim(s1[3]);
		    window.opener.document.getElementById("assetManufact").value=trim(s1[4]);
		    window.opener.document.getElementById("machineSupplier").value=trim(s1[5]);
		    window.opener.document.getElementById("usageType").value=trim(s1[6]);
		    window.opener.document.getElementById("txtStateCode").value=trim(s1[7]);
		    window.opener.document.getElementById("collateralSecurityMarginDF").value=trim(s1[8]);
		    window.opener.document.getElementById("collateralSecurityMargin").value=trim(s1[9]);
		    window.opener.document.getElementById("loanAmount").value=trim(s1[10]);
		    window.opener.document.getElementById("vehicleCost").value=trim(s1[11]);
		    window.opener.document.getElementById("vehicleDiscount").value=trim(s1[12]);
		    window.opener.document.getElementById("assetsCollateralValue").value=trim(s1[13]);
		    window.opener.document.getElementById("gridValue").value=trim(s1[14]);
		    window.opener.document.getElementById("valuationCost").value=trim(s1[15]);
		    window.opener.document.getElementById("securityTypes").value=trim(s1[16]);
		    
		    if(trim(s1[17])=='Y')
		    {
		    	window.opener.document.getElementById("assetStandard").checked=true;
		    }
		    else
		    {
		    	window.opener.document.getElementById("assetStandard").checked=false;
		    }
		    window.opener.document.getElementById("assetId").value=trim(s1[18]);
		    window.opener.document.getElementById("lbxAssetId").value=trim(s1[18]);
		    
		   // alert(trim(s1[19]));
		    window.opener.document.getElementById("vehicleYearOfManufact").value=trim(s1[19]);
		    window.opener.document.getElementById("vehicleRegNo").value=trim(s1[20]);
		    window.opener.document.getElementById("vehicleRegDate").value=trim(s1[21]);
		    window.opener.document.getElementById("vehicleChesisNo").value=trim(s1[22]);
		    window.opener.document.getElementById("insuranceAgency").value=trim(s1[23]);
		    window.opener.document.getElementById("vehicleInsureDate").value=trim(s1[24]);
		    window.opener.document.getElementById("vehicleOwner").value=trim(s1[25]);
		    window.opener.document.getElementById("engineNumber").value=trim(s1[26]);
		    window.opener.document.getElementById("idv").value=trim(s1[27]);
		    
		    window.opener.document.getElementById("vehicleInvoiceDate").value=trim(s1[28]);
		    window.opener.document.getElementById("invoiceNumber").value=trim(s1[29]);
		    window.opener.document.getElementById("rcReceived").value=trim(s1[30]);
		    window.opener.document.getElementById("rcReceivedDate").value=trim(s1[31]);
		    window.opener.document.getElementById("invoiceAmount").value=trim(s1[32]);
		    window.opener.document.getElementById("permitReceived").value=trim(s1[33]);
		    window.opener.document.getElementById("permitReceivedDate").value=trim(s1[34]);
		    window.opener.document.getElementById("permitExpiryDate").value=trim(s1[34]);
		
		    if(trim(s1[35])=='Y')
		    {
		    	window.opener.document.getElementById("invoiceUpdateCheckBox").checked=true;
		       	window.opener.document.getElementById("invoiceNumber").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleInvoiceDate").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("invoiceAmount").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleChesisNo").removeAttribute("readOnly","true");
		     	window.opener.document.getElementById("engineNumber").removeAttribute("readOnly","true");
		    }
		    else
		    {
		    	window.opener.document.getElementById("invoiceUpdateCheckBox").checked=false;
		    	window.opener.document.getElementById("invoiceNumber").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleInvoiceDate").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("invoiceAmount").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleChesisNo").setAttribute("readOnly","true");
		     	window.opener.document.getElementById("engineNumber").setAttribute("readOnly","true");
		    }
		    if(trim(s1[36])=='Y')
		    {
		    	window.opener.document.getElementById("rcUpdateCheckBox").checked=true;
		     	window.opener.document.getElementById("rcReceivedDate").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleRegNo").removeAttribute("readOnly","true");
		       	
		    	window.opener.document.getElementById("vehicleRegDate").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleYearOfManufact").removeAttribute("readOnly","true");
		       	
		    	window.opener.document.getElementById("vehicleOwner").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("permitReceivedDate").removeAttribute("readOnly","true");
		    }
		    else
		    {
		    	window.opener.document.getElementById("rcUpdateCheckBox").checked=false;
		    	window.opener.document.getElementById("rcReceivedDate").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleRegNo").setAttribute("readOnly","true");
		       	
		    	window.opener.document.getElementById("vehicleRegDate").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleYearOfManufact").setAttribute("readOnly","true");
		       	
		    	window.opener.document.getElementById("vehicleOwner").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("permitReceivedDate").setAttribute("readOnly","true");
		    }
		    if(trim(s1[37])=='Y')
		    {
		    	window.opener.document.getElementById("insuranceUpdateCheckBox").checked=true;
		    	window.opener.document.getElementById("insuranceAgency").removeAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleInsureDate").removeAttribute("readOnly","true");
		    	window.opener.document.getElementById("idv").removeAttribute("readOnly","true");
		       
		    }
		    else
		    {
		    	window.opener.document.getElementById("insuranceUpdateCheckBox").checked=false;
		    	window.opener.document.getElementById("insuranceAgency").setAttribute("readOnly","true");
		       	window.opener.document.getElementById("vehicleInsureDate").setAttribute("readOnly","true");
		    	window.opener.document.getElementById("idv").setAttribute("readOnly","true");
		    }
		
		    window.close();
		}
		else
		{
			alert("Incorrect Data");
			window.opener.document.getElementById("assetsCollateralDesc").value='';
			window.opener.document.getElementById("lbxAssetId").value='';
			window.opener.document.getElementById("assetId").value='';
			
			window.close();
		}
	}
	
}
//Manish Ends

//Richa Space for Repo Billing Approval
function getValuesForRepoBilling()
{
	var loanId=window.opener.document.getElementById('lbxDealNo').value;
	//alert("loanId:----"+loanId);
	//alert("assetId:----"+assetId);
	if(loanId!=='')
	{
		
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=getRepoBillingApprovalValues";
		 var data = "loanId="+loanId;
		 sendRepoApprovalLoanId(address, data);
		 
		 return true;
	 }
	 else
     {
    	 alert("Loan No is required.");	
    	 return false;
     }
}
function sendRepoApprovalLoanId(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultRepoBillingApproval(request);	
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultRepoBillingApproval(request)
{
	
	//alert("Test 1");
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		
		//alert("Test 2");
		var str = request.responseText;
		//alert("String"+str);
		if(trim(str).length>0)
		{
		
			var s1 = str.split("$:");
			//alert("Splited"+s1);
		 
		    if(trim(s1[0])=='A')
		    {
		    	
		    	window.opener.document.getElementById("billingStopped").checked=true;
		    }
		    else
		    {
		    	
		    	window.opener.document.getElementById("billingStopped").checked=false;
		    }
		    
		    if(trim(s1[1])=='A')
		    {
		    	
		    	window.opener.document.getElementById("interestStopped").checked=true;
		    }
		    else
		    {
		    	window.opener.document.getElementById("interestStopped").checked=false;
		    }
		    
		    if(trim(s1[2])=='A')
		    {
		    	
		    	window.opener.document.getElementById("SDInterest").checked=true;
		    }
		    else
		    {
		    	window.opener.document.getElementById("SDInterest").checked=false;
		    }
		   
		    window.close();
		}
		
	}
	
}
//Richa Ends

//Nishant space starts
function dealValuesForLimitEnhance(){
	//alert("IN alert");
	var dealId=window.opener.document.getElementById('lbxDealNo').value;
	//alert("dealId:----"+dealId);
	if(dealId!=='')
	{
		 window.opener.document.getElementById("oldLoanAmount").value='';
	     window.opener.document.getElementById("totalDisbursedAmount").value='';
	     window.opener.document.getElementById("loanInsType").value='';
	     window.opener.document.getElementById("loanNo").value='';
	     window.opener.document.getElementById("lbxLoanNo").value='';	  
	     window.opener.document.getElementById("newLoanAmount").setAttribute("readOnly","true"); 
	     window.opener.document.getElementById("loanAmountType").setAttribute("disabled","true");
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxActionForCP.do?method=retriveLimitEnhancementValues";
		 var data = "lbxDealNo="+dealId;
		 sendLimitEnhanceId(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Please Select One Record");	
    	 return false;
     }

}
//Nishant Space end
function sendLimitEnhanceId(address, data) {
	//alert("in sendClosureid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultLimitEnhancement(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultLimitEnhancement(request){
	
	//alert("in resultClosure id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("str:-----"+str);
		var s1 = str.split("$:");
	        // alert(s1);
		    
			window.opener.document.getElementById("oldSenAmount").value=trim(s1[0]);
	    	window.opener.document.getElementById("oldSenDate").value=trim(s1[1]);
	    	window.opener.document.getElementById("utilAmount").value=trim(s1[2]);
	    	window.opener.document.getElementById("oldSenValidTill").value=trim(s1[3]);
	    	window.opener.document.getElementById("addSenAmount").value=trim(s1[4]);
	    	window.opener.document.getElementById("applicationdate").value=trim(s1[5]);

		window.close();
	}
}

//vishal start
function generateRepayScheduleValues(){
	
	
	var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
	
	if(loanId!==''){
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveRepayScheduleValues&lbxLoanNoHID="+loanId;
		 var data = "lbxLoanNo="+loanId;
		 sendRepayScheduleId(address, data);
		 return true;
	 }
	 else
    {
   	 alert("Please Select One Record");	
   	 return false;
    }
}

function sendRepayScheduleId(address, data) {
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultRepaySchedule(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultRepaySchedule(request){
	
	
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("str:-----"+str);
		
	       // alert(s1);
		if(trim(str).length>0)
		{    
			var s1 = str.split("$:");
			window.opener.document.getElementById("product").value=trim(s1[3]);
	    	window.opener.document.getElementById("scheme").value=trim(s1[4]);
	    	window.opener.document.getElementById("disbursedAmount").value=trim(s1[5]);
	    	window.opener.document.getElementById("outstandingLoanAmount").value=trim(s1[6]);
	       	window.opener.document.getElementById("curentDueDate").value=trim(s1[7]);
	    	window.opener.document.getElementById("emi").value=trim(s1[9]);
	    	window.opener.document.getElementById("currerntDueDay").value=trim(s1[10]);
	    	window.opener.document.getElementById("repayEffDateAtDueDate").value=trim(s1[11]);
	    	window.opener.document.getElementById("loanFrequency").value=trim(s1[12]);
		    window.close();
	   }
		else
		{
			var contextPath=window.opener.document.getElementById("contextPath").value;
			alert("Due Date is Pending for this Loan");
			window.opener.location.href = contextPath+'/repayscheduleSearchBehind.do';
			self.close();
		}
	}
}
// vishal end
//kanika manual npa movement
function generateManualNpaValues(){
	//alert("hi");
	 var loanId=window.opener.document.getElementById('lbxLoanNo').value;
	 if(loanId == "")
		{
			alert("Please Enter Loan Account First");
			
			return false;
		}else{
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/manualNpaMovementMakerAdd.do?method=retriveDetailsforLoan&loanId="+loanId;
		 var data = "lbxLoanNo="+loanId;
		 sendmanualNpaId(address, data);
		 return true;
	 }
	 
}

function sendmanualNpaId(address, data) {
	//alert("in sendDisbursalId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultmanualNpa(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultmanualNpa(request){
	
	//alert("in resultDisbursal id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		if(str.length>0)
		{
			var s1 = str.split("$:");
			
		
			window.opener.document.getElementById('scheme').value=trim(s1[0]);
			window.opener.document.getElementById('product').value=trim(s1[1]);
		
			
			window.close();
		}
		
	}
}
//kanika code for lead deatils
function copyCustomerDetails()
{
	 var customerId=window.opener.document.getElementById('lbxCustomerId').value;
	 var addressId=window.opener.document.getElementById('addressId').value;
	 if(customerId!='')
	 {
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/leadCapturingBehindAction.do?method=searchCustomer&customerId="+customerId+"&addressId="+addressId;
		 var data = "customerId="+customerId;
		 sendCopyCustomer(address, data);
		 return true;
	 }
}
function sendCopyCustomer(address, data) {
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCopyCustomer(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function resultCopyCustomer(request){
	

	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;

		var s1 = str.split("$:");
		if(window.opener.document.getElementById("customerType").value=trim(s1[7])=='C'){
					
			window.opener.document.getElementById("individualfield").style.display='none';
			window.opener.document.getElementById("corporatefield").style.display='';
			window.opener.document.getElementById("corporateconstitution").style.display='';
			window.opener.document.getElementById("Individualconstitution").style.display='none';
			window.opener.document.getElementById("constitution").style.display='none';
			window.opener.document.getElementById("customerName").value=trim(s1[0]);
			window.opener.document.getElementById("hGroupId").value=trim(s1[1]);
			window.opener.document.getElementById("registrationNo").value=trim(s1[2]);
			window.opener.document.getElementById("custPan").value=trim(s1[3]);
			window.opener.document.getElementById("businessSegment").value=trim(s1[4]);
			window.opener.document.getElementById("lbxIndustry").value=trim(s1[5]);
			window.opener.document.getElementById("lbxSubIndustry").value=trim(s1[6]);
			window.opener.document.getElementById("customerType").value='C';
			window.opener.document.getElementById("customerType1").removeAttribute("disabled");
			window.opener.document.getElementById("customerType1").value='C';
			window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("corconstitution").value=trim(s1[8]);
			window.opener.document.getElementById("address1").value=trim(s1[12]);
			window.opener.document.getElementById("address2").value=trim(s1[13]);
			window.opener.document.getElementById("addresstype").value=trim(s1[14]);
			window.opener.document.getElementById("noOfYears").value=trim(s1[15]);
			window.opener.document.getElementById("pincode").value=trim(s1[16]);
			window.opener.document.getElementById("txtCountryCode").value=trim(s1[17]);
			window.opener.document.getElementById("txtStateCode").value=trim(s1[18]);
			window.opener.document.getElementById("txtDistCode").value=trim(s1[19]);
			window.opener.document.getElementById("email").value=trim(s1[20]);
			window.opener.document.getElementById("groupName").value=trim(s1[21]);
			window.opener.document.getElementById("country").value=trim(s1[22]);
			window.opener.document.getElementById("dist").value=trim(s1[23]);
			window.opener.document.getElementById("state").value=trim(s1[24]);
			window.opener.document.getElementById("industry").value=trim(s1[25]);
			window.opener.document.getElementById("subIndustry").value=trim(s1[26]);
            window.opener.document.getElementById("relationshipSince").value=trim(s1[27]);
            window.opener.document.getElementById("phoneOff").value=trim(s1[28]);
			window.opener.document.getElementById("firstName").value="";
			window.opener.document.getElementById("lastName").value="";
			window.opener.document.getElementById("custDOB").value="";
			window.opener.document.getElementById("custPanInd").value="";

		}else
			if(window.opener.document.getElementById("customerType").value=trim(s1[7])=='I'){
				window.opener.document.getElementById("corporatefield").style.display='none';
				window.opener.document.getElementById("individualfield").style.display='';
				window.opener.document.getElementById("corporateconstitution").style.display='none';
				window.opener.document.getElementById("Individualconstitution").style.display='';
				window.opener.document.getElementById("constitution").style.display='none';
				window.opener.document.getElementById("custPanInd").value=trim(s1[3]);
				window.opener.document.getElementById("customerType").value='I';
                window.opener.document.getElementById("customerType1").removeAttribute("disabled");
				window.opener.document.getElementById("customerType1").value='I';
				window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
				window.opener.document.getElementById("indconstitution").value=trim(s1[8]);
				window.opener.document.getElementById("firstName").value=trim(s1[9]);
				window.opener.document.getElementById("lastName").value=trim(s1[10]);
				window.opener.document.getElementById("custDOB").value=trim(s1[11]);
				window.opener.document.getElementById("address1").value=trim(s1[12]);
				window.opener.document.getElementById("address2").value=trim(s1[13]);
				window.opener.document.getElementById("addresstype").value=trim(s1[14]);
				window.opener.document.getElementById("noOfYears").value=trim(s1[15]);
				window.opener.document.getElementById("pincode").value=trim(s1[16]);
				window.opener.document.getElementById("txtCountryCode").value=trim(s1[17]);
				window.opener.document.getElementById("txtStateCode").value=trim(s1[18]);
				window.opener.document.getElementById("txtDistCode").value=trim(s1[19]);
				window.opener.document.getElementById("email").value=trim(s1[20]);
				window.opener.document.getElementById("country").value=trim(s1[22]);
				window.opener.document.getElementById("dist").value=trim(s1[23]);
				window.opener.document.getElementById("state").value=trim(s1[24]);
                window.opener.document.getElementById("relationshipSince").value=trim(s1[27]);
                window.opener.document.getElementById("phoneOff").value=trim(s1[28]);
				window.opener.document.getElementById("customerName").value="";
				window.opener.document.getElementById("hGroupId").value="";
				window.opener.document.getElementById("registrationNo").value="";
			
				window.opener.document.getElementById("businessSegment").value="";
				window.opener.document.getElementById("lbxIndustry").value="";
				window.opener.document.getElementById("lbxSubIndustry").value="";
				window.opener.document.getElementById("groupName").value="";
                window.opener.document.getElementById("custPan").value="";
			}else{
				window.opener.document.getElementById("corporateconstitution").style.display='none';
				window.opener.document.getElementById("Individualconstitution").style.display='none';
				window.opener.document.getElementById("customerType").value="";
				window.opener.document.getElementById("constitution").value="";
				window.opener.document.getElementById("firstName").value="";
				window.opener.document.getElementById("lastName").value="";
				window.opener.document.getElementById("custDOB").value="";
				window.opener.document.getElementById("address1").value="";
				window.opener.document.getElementById("address2").value="";
				window.opener.document.getElementById("addresstype").value="";
				window.opener.document.getElementById("noOfYears").value="";
				window.opener.document.getElementById("pincode").value="";
				window.opener.document.getElementById("txtCountryCode").value="";
				window.opener.document.getElementById("txtStateCode").value="";
				window.opener.document.getElementById("txtDistCode").value="";
				window.opener.document.getElementById("email").value="";
				window.opener.document.getElementById("country").value="";
				window.opener.document.getElementById("dist").value="";
				window.opener.document.getElementById("state").value="";
				window.opener.document.getElementById("customerName").value="";
				window.opener.document.getElementById("hGroupId").value="";
				window.opener.document.getElementById("registrationNo").value="";
				window.opener.document.getElementById("custPan").value="";
                window.opener.document.getElementById("custPanInd").value="";
				window.opener.document.getElementById("businessSegment").value="";
				window.opener.document.getElementById("lbxIndustry").value="";
				window.opener.document.getElementById("lbxSubIndustry").value="";
				window.opener.document.getElementById("groupName").value="";
				
			}
		window.close();
	}
}

function genrateBillDetail(){
	var partyId=window.opener.document.getElementById('lbxPartyCode').value;
	var headType=window.opener.document.getElementById('headType').value;
	//alert("loanId:----"+loanId);
	if(partyId!==''){
		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 var address = contextPath+"/glBillSettlementAjax.do?actionName=ajaxForbillDetail";
		 var data = "lbxPartyCode="+partyId+"&headType="+headType;
		 sendBillId(address, data);
		 return true;
	 }
	 else
     {
    	 alert("Please Select One Record");	
    	 return false;
     }

}
function sendBillId(address, data) {
	//alert("in sendClosureid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultBillDetail(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}
function resultBillDetail(request){
	
	//alert("in resultClosure id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		window.opener.document.getElementById("billgridList").innerHTML=str;
	    
		window.close();
	}
}
//Aditi welecome report--CM
function disabledate()
{
	var loanLovId=window.opener.document.getElementById('loanNumber').value;
	var reportName=window.opener.document.getElementById('reportName').value;	
	if(loanLovId!='' )
	{
		window.opener.document.getElementById("datewithcal").style.display="none";
		window.opener.document.getElementById("datewithoutcal").style.display="";
		window.opener.document.getElementById("daterange").style.display="none";
		window.opener.document.getElementById("daterangewithoutcal").style.display="";
						
	}
	else
	{
		window.opener.document.getElementById("datewithcal").style.display="";
		window.opener.document.getElementById("datewithoutcal").style.display="none";
		window.opener.document.getElementById("daterange").style.display="";
		window.opener.document.getElementById("daterangewithoutcal").style.display="none";
	}
	if(reportName=="welcome_letter_report")
	{
		window.opener.document.getElementById("FA").style.display="";
		window.opener.document.getElementById("TA").style.display="";
		window.opener.document.getElementById("FD").style.display="none";
		window.opener.document.getElementById("TD").style.display="none";
	}
	else
	{
		window.opener.document.getElementById("FA").style.display="none";
		window.opener.document.getElementById("TA").style.display="none";
		window.opener.document.getElementById("FD").style.display="";
		window.opener.document.getElementById("TD").style.display="";
	}
	window.close();
	
}


function setLedgerValue()
{
	var ledgerId = window.opener.document.getElementById("lbxLedgerId").value;
	//alert("Ledger Id: "+ledgerId);
	window.opener.document.getElementById("groupName").value=ledgerId;
	//alert("Ledger Id Set");
	window.close();
}


//Ankit ajax method starts here For GL...........

function getBankCode()
{
	//alert("Please Select Settlement Mode");
	//alert(window.opener.document.getElementById("paymentMode").value);
	if (window.opener.document.getElementById("paymentMode").value==""){
		 alert("Please Select Settlement Mode");
		 return false;
		 //window.close();
	 }else if (window.opener.document.getElementById("lbxbankAccID").value==""){
		 alert("Please Select Account No. LOV");
		 return false;
		// window.close();
	  }
    
	 var BankAccount=window.opener.document.getElementById('lbxbankAccID').value;
	 var accountType=window.opener.document.getElementById('paymentMode').value;
	//alert(accountType);
	 if(accountType=='Q'||accountType=='D'||accountType=='N'||accountType=='R'){
		 accountType='B' ;
	 }
	 var basePath=window.opener.document.getElementById("contextPath").value;

	  var address = basePath+"/glbillSetlementAjax.do?actionName=chequeDetail&accountType="+accountType;
	  var data = "BankAccount=" + BankAccount ;
	
	  send_BankCode(address, data);
       return true;
}
 function send_BankCode(address, data) {
		var request = getRequestObject();
		request.onreadystatechange = function () {
			result_BankCode(request);
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	function result_BankCode(request){

		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			var s1 = str.split("$:");
			window.opener.document.getElementById('lbxbankID').value=trim(s1[1]);
			window.opener.document.getElementById('lbxBranchID').value=trim(s1[2]);
			window.opener.document.getElementById('micr').value=trim(s1[3]);
			window.opener.document.getElementById('ifsCode').value=trim(s1[4]);
			window.close();
			}

		}
	
// Prashant start
	function getLtv()
	{
		//alert("Please Select getLtv");
		//alert(window.opener.document.getElementById("paymentMode").value);
	
	    
		 var assetNature=window.opener.document.getElementById('assetNature').value;
		 var txtProductCat=window.opener.document.getElementById('txtProductCat').value;
		 var makeModelId=window.opener.document.getElementById('make_model_id').value;
		 var vehicleYearOfManufact=window.opener.document.getElementById('vehicleYearOfManufact').value;
		 //alert(makeModelId);
	
		 var basePath=window.opener.document.getElementById("contextPath").value;

		  var address = basePath+"/relationalManagerAjaxAction.do?method=getLtvFromMakeModel";
		  var data = "assetNature="+assetNature+"&txtProductCat="+txtProductCat+"&makeModelId="+makeModelId+"&vehicleYearOfManufact="+vehicleYearOfManufact;
		  send_getLtv(address, data);
	       return true;
	}
	 function send_getLtv(address, data) {
			var request = getRequestObject();
			request.onreadystatechange = function () {
				result_getLtv(request);
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}
		function result_getLtv(request){

			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");	
				 if(trim(s1[0])==""){
						// window.opener.document.getElementById('collateralSecurityMargin').value="";
						 window.opener.document.getElementById('collateralSecurityMarginDF').value="";
						 window.opener.document.getElementById('txtStateCode').value="";
						 window.opener.document.getElementById('lbxmachineManufact').value="";
						 window.opener.document.getElementById('assetManufact').value="";
						 //window.opener.document.getElementById('gridValue').value="";
						 //window.opener.document.getElementById('assetState').value="";
			
				 } else{
						// window.opener.document.getElementById('collateralSecurityMargin').value=trim(s1[0]);
						 window.opener.document.getElementById('collateralSecurityMarginDF').value=trim(s1[0]);
						 window.opener.document.getElementById('txtStateCode').value=trim(s1[1]);
						 window.opener.document.getElementById('lbxmachineManufact').value=trim(s1[2]);
						 window.opener.document.getElementById('assetManufact').value=trim(s1[3]);		
						 window.opener.document.getElementById('gridValue').value=trim(s1[4]);
						 window.opener.document.getElementById('assetState').value=trim(s1[5]);
						 //window.opener.document.getElementById('assetState').value=trim(s1[5]);
					 }
				 window.close();
				}

			}
			
function disableDateForMaturity()
{
	var loanLovId=window.opener.document.getElementById('specificLoanNo').value;
	
	if(loanLovId!='' )
	{
		window.opener.document.getElementById("fromDatewithcal").style.display="none";
		window.opener.document.getElementById("fromDatewitoutcal").style.display="";
		window.opener.document.getElementById("toDatewithcal").style.display="none";
		window.opener.document.getElementById("toDatewithoutcal").style.display="";
		window.close();
	}
	else
	{
		window.opener.document.getElementById("fromDatewithcal").style.display="";
		window.opener.document.getElementById("fromDatewitoutcal").style.display="none";
		window.opener.document.getElementById("toDatewithcal").style.display="";
		window.opener.document.getElementById("toDatewithoutcal").style.display="none";
		window.close();
	}
}
//Anil Start

function totalRecOfNonEmiLoan()
	{
		var loanId=window.opener.document.getElementById('lbxLoanNo').value ;;
		var contextPath =window.opener.document.getElementById('contextPath').value ;
		
			var address = contextPath+"/ajaxAction.do?method=getChangeRateLoanDetail";
			var data = "loanId="+loanId;
			sendRequestNonEmiLoan(address,data);
			return true;
	
	
	}
	function sendRequestNonEmiLoan(address, data) 
	{
		
		var request = getRequestObject();
		request.onreadystatechange = function ()
		{
			resultNonEmiLoan(request);
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	function resultNonEmiLoan(request)
	{    
		if ((request.readyState == 4) && (request.status == 200)) 
		{
			var str = request.responseText;
			var s1 = str.split("$:");
			if(s1.length>0)
		    {
				//alert("ststus:-"+trim(s1[1]));
				if(trim(s1[1])=='true'){
				window.opener.document.getElementById('loanAmount').value = trim(s1[2]);
				window.opener.document.getElementById('balanceAmount').value = trim(s1[3]);
				window.opener.document.getElementById('loanFinalRate').value = trim(s1[4]);
				window.opener.document.getElementById('currentRateType').value = trim(s1[5]);
				window.opener.document.getElementById('loanRateType').value = trim(s1[6]);
				window.close();
				}else{
					var contextPath=window.opener.document.getElementById("contextPath").value;
					alert("Rate Change are Pending for this Loan.");
					window.opener.location.href = contextPath+'/changeRateMaker.do';
					self.close();
				}
		    }
			
		}
}
	
	function clearBranchChild()
	{
		window.opener.document.getElementById("areaCode").value="";
	 	window.opener.document.getElementById("lbxareaCodeVal").value="";
	 	window.close();	
	}
	
	//Aditi batch report--CM
	function disablePresentationDate()
		{

		var batchNo=window.opener.document.getElementById('batchNo').value;
		if(batchNo!='' )
		{
			window.opener.document.getElementById("pDatewithcal").style.display="none";
			window.opener.document.getElementById("pDatewithoutcal").style.display="";
			window.opener.document.getElementById("batchGenerates").setAttribute("disabled",true);
		}
		else
		{
			window.opener.document.getElementById("pDatewithcal").style.display="";
			window.opener.document.getElementById("pDatewithoutcal").style.display="none";
			window.opener.document.getElementById("batchGenerates").removeAttribute("disabled",true);
		}
		window.close();
		}


	
	

	
	function getProductManual()
	{
		var lbxLoanNoHID=window.opener.document.getElementById('lbxLoanNoHID').value ;;
		var contextPath =window.opener.document.getElementById('contextPath').value ;
		
			var address = contextPath+"/ajaxAction.do?method=getProductManualDetail";
			var data = "lbxLoanNoHID="+lbxLoanNoHID;
			sendProductManual(address,data);
			return true;
	}
	function sendProductManual(address, data) 
	{
		
		var request = getRequestObject();
		request.onreadystatechange = function ()
		{
			resultProductManual(request);
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	function resultProductManual(request)
	{    
		if ((request.readyState == 4) && (request.status == 200)) 
		{
			var str = request.responseText;
			var s1 = str.split("$:");
			if(s1.length>0)
		    {
				//alert(trim(s1[0]));
				// *********** asesh space ********
				window.opener.document.getElementById('lbxProductId').value = trim(s1[0]);
				window.opener.document.getElementById('lbxBusinessPartnearHID').value = "";
				window.opener.document.getElementById('businessPartnerType').value = "";
				window.opener.document.getElementById('businessPartnerName').value = "";
				window.opener.document.getElementById('businessPartnerTypeDesc').value = "";
				window.opener.document.getElementById("chargeCode").value="";
				window.opener.document.getElementById("lbxCharge").value="";
				window.opener.document.getElementById("adviceType").value="";
				window.opener.document.getElementById("chargeAmount").value="";
				window.opener.document.getElementById("tdsRate").value="";
				window.opener.document.getElementById("taxApplicable").value="";
				window.opener.document.getElementById("taxInclusive").value="";
				window.opener.document.getElementById("taxRate1").value="";
				window.opener.document.getElementById("taxRate2").value="";
				window.opener.document.getElementById("tdsApplicable").value="";
				window.opener.document.getElementById("chargeId").value="";

				//******** end ********************
				window.close();
				
		    }
			
		}

}


	// Ankit method for gl vouhcer Entry
	
	function setSubLedger()
		  {
			var id=window.opener.document.getElementById("valueCheck").value;
			//alert(id);
			var subID=window.opener.document.getElementById("lbxSubLedgerId").value;
			var dropCheck=window.opener.document.getElementById("dropCheck").value;
		  	var subledgerId = window.opener.document.getElementById("lbxSubledgerId"+id).value;
		  
		  	window.opener.document.getElementById("subLedger"+id).value=subledgerId;
		  if(dropCheck=='Y'){
		  	window.opener.document.getElementById("lbxSubledgerId"+id).value=subID;
		  }
		  	
		  	getLedgerBalance(id);
		  }

	function setLedgerIDValue()
	{
		var id=window.opener.document.getElementById("valueCheck").value;
		var dropCheck=window.opener.document.getElementById("dropCheck").value;
		if(dropCheck=='N'){
			var ledgerId = window.opener.document.getElementById("lbxLedgerId"+id).value;
			window.opener.document.getElementById("accountNo"+id).value=ledgerId;
		}
		else{
			var ledgerName = window.opener.document.getElementById("lbxLedgerId"+id).value;
			var ledgerId = window.opener.document.getElementById("accountNo"+id).value;
			window.opener.document.getElementById("accountNo"+id).value=ledgerName;
			window.opener.document.getElementById("lbxLedgerId"+id).value=ledgerId;
		}
		getGroupName(id);
	}

	function getGroupName(obj)
		{
		//alert('in group name');
			var contextPath=window.opener.document.getElementById("contextPath").value;
			var dropCheck=window.opener.document.getElementById("dropCheck").value;
			var strTmp="";
			
			if(dropCheck=='N'){
				strTmp=window.opener.document.getElementById("accountNo"+obj).value;
			}else{
				strTmp=window.opener.document.getElementById("lbxLedgerId"+obj).value;
			}
		 	var subLed="subLedger"+obj;
		 	var accountNo= strTmp;
		 	var aPosition = obj;
	 		 	
		 	if(strTmp==""){
		 		window.opener.document.getElementById(obj).value = '';
		 	}
		 	else{
		 		var url=contextPath+"/glVoucherAjaxAction.do";
				url=url+"?action=getGroupName&ledgerCode="+accountNo+"&id="+trim(accountNo)+"&ledid="+subLed+"&obj="+obj;
				getGrpName(url,obj);
			}
		}

	function getGrpName(url,obj){
		var request = getRequestObject();
		request.onreadystatechange = function () {
			groupNameSelected(request,obj);	
		};
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(obj);

	}
	function groupNameSelected(request,obj){
//		alert('groupNameSelected');
//		alert(request,obj);
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			var s1 = str.split("$:");
			 window.opener.document.getElementById("subLedgerDiv"+obj).innerHTML = s1[0];
			 window.opener.document.getElementById("ledgerRequired"+obj).value = s1[1];
			 window.opener.document.getElementById("ledgerSubType"+obj).value = s1[2];
	    	 getLedgerInfo(obj);   
		}
	 }

	function getLedgerInfo(id){
		//alert('getLedgerInfo');
		var contextPath=window.opener.document.getElementById("contextPath").value;
		var dropCheck=window.opener.document.getElementById("dropCheck").value;
		var accNo="";
		if(dropCheck=='N'){
			accNo=window.opener.document.getElementById("accountNo"+id).value;
		}else{
			accNo=window.opener.document.getElementById("lbxLedgerId"+id).value;
		}
	    if(accNo==""){
		   window.opener.document.getElementById("accountName1").value = '';
	 	}else{
				var url=contextPath+"/glVoucherAjaxAction.do";
				url=url+"?action=getLedgerInfo&ledgerCode="+accNo;
				
				ledgerSelected(url,id);
		 	}
	}

	function ledgerSelected(url, id) {
		var request = getRequestObject();
		request.onreadystatechange = function () {
			ledgerInfoSelected(request,id);	
		};
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(id);
		
	}

	function ledgerInfoSelected(request,id){
//		alert('ledgerInfoSelected');
//		alert(request,id);
		if ((request.readyState == 4) && (request.status == 200)) {
			 var str = request.responseText;
			 var str1=str.split("$:");
		         if(str1[0]=="Y"&&str1[1]=="Y"){
		        	 alert("Tax and TDS both are applicable for this Account No.");
		         }if(str1[0]=="Y"&&str1[1]=="N"){
		            alert("Tax is applicable for this Account No.");
		         }if(str1[0]=="N"&&str1[1]=="Y"){
		        	 alert("TDS is applicable for this Account No.");
		         }
	       getLedgerBalance(id); 
		}
	}

	function getLedgerBalance(id){
		//alert('getLedgerBalance');
		var contextPath=window.opener.document.getElementById("contextPath").value;
		var dropCheck=window.opener.document.getElementById("dropCheck").value;
		var accNo="";
		var subLedgerId="";
			if(dropCheck=='N'){
				accNo=window.opener.document.getElementById("accountNo"+id).value;
				subLedgerId=window.opener.document.getElementById("subLedger"+id).value;
			}else{
				accNo=window.opener.document.getElementById("lbxLedgerId"+id).value;
				subLedgerId=window.opener.document.getElementById("lbxSubledgerId"+id).value;
			}
		var moduleId=window.opener.document.getElementById("moduleId").value;
		var productId=window.opener.document.getElementById("productId").value;
		var departmentId=window.opener.document.getElementById("department").value;
		var branchID=window.opener.document.getElementById('branch').value;
		var voucherDate="";
		  if(window.opener.document.getElementById('from_datepicker')!=undefined || window.opener.document.getElementById('from_datepicker')!=null){
			  voucherDate=window.opener.document.getElementById("from_datepicker").value;
			}else{
				voucherDate=window.opener.document.getElementById("vDate").value;
			}
		  var voucherNo=window.opener.document.getElementById("voucherNo").value;
		  
				var url=contextPath+"/glVoucherAjaxAction.do";
				url=url+"?action=getLedgerBalance&ledgerCode="+accNo+"&subLedgerId="+subLedgerId+"&moduleId="+moduleId+"&productId="+productId+"&departmentId="+departmentId+"&voucherDate="+voucherDate+"&voucherNo="+voucherNo+"&branchID="+branchID;
				ledgerBSelected(url,id);
				 
	 }

	function ledgerBSelected(url, id) {
		var request = getRequestObject();
		request.onreadystatechange = function () {
			ledgerBalanceSelected(request, id);	
		};
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(id);
		
	}

	function ledgerBalanceSelected(request, id){
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			  var str1=str.split("$:");
	          window.opener.document.getElementById("ledgerType"+id).value=trim(str1[0]);
	          window.opener.document.getElementById("ledgerDrCrFlag"+id).value=trim(str1[1]);
	          window.opener.document.getElementById("ledgerBalance"+id).value=trim(str1[2]);
	          
	          window.close();
		}
	 }
	
	function GetXmlHttpObject()
		{
			var http_request = null;
			if (window.XMLHttpRequest) { // Mozilla, Safari,...
	        http_request = new XMLHttpRequest();
	        if (http_request.overrideMimeType) {
	            http_request.overrideMimeType('text/xml');
	        }
	    } else if (window.ActiveXObject) { // IE
	        try {
	            http_request = new ActiveXObject("Msxml2.XMLHTTP");
	            
	        } catch (e) {
	            try {
	            http_request = new ActiveXObject("Microsoft.XMLHTTP");
	           
	            } catch (e) {}
	        }
	    }
	    return http_request;
		}


	// end by Ankit

function getTotalReceived()
	{	
		var headType = window.opener.document.getElementById("headType").value;	
		var partyID = window.opener.document.getElementById("lbxPartyCode").value;	
		var contextPath =window.opener.document.getElementById("contextPath").value;
		xmlhttp1=GetXmlHttpObject();
		var url=contextPath+"/glBillReceiptSearch.do";
		url=url+"?actionName=getTotalReceiveAjax&headType="+headType+"&partyID="+partyID;
		xmlhttp1.onreadystatechange= function () {
			setResultAmount();	
		};
		xmlhttp1.open("GET",url,true);
		xmlhttp1.send(null);
	}
	function setResultAmount()
	{
		if (xmlhttp1.readyState == 4)
    	{
            if (xmlhttp1.status == 200)
            {   
	           // document.getElementById('productId'+count).options.length = 1;
            	var str=xmlhttp1.responseText;
            	//alert(str);
            	window.opener.document.getElementById('totalRecevable').value=str; 
            	window.close();
            } else
            {
                alert('There was a problem with the request.'+xmlhttp1.status+xmlhttp1.readyState);
                window.close();
	        }
        }
	}
// end by Ankit
	
	//ravi start-----------------------------------------------------------------------
	
	function setSupplierManufacturerIdInSession()
	{
		 var bpId=document.getElementById("lbxBusinessPartnearHID").value;
		 var bpType=document.getElementById("disbursalTo").value;
		 
			 var contextPath=document.getElementById("contextPath").value;
			 var address = contextPath+"/ajaxAction.do?method=setSuppManufIdInSess&bpId="+bpId+"&bpType="+bpType;
			 var data = "bpId="+bpId;
			 sendSupManufId(address, data);
			 return true;
		
		 
	}

	function sendSupManufId(address, data) {
		//alert("in sendDisbursalloanId id");
		var request = getRequestObject();
		request.onreadystatechange = function () {
			resultSendSupManufId(request);	
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
		
	}
	
	function resultSendSupManufId(request)
	{
		var s1;
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
				
			}
		window.close();
	}
	
	function clearTALoanLOV()
	{
		window.opener.document.getElementById("taLoanNo").value='';
		window.opener.document.getElementById("lbxTaLoanNoHID").value='';
		window.opener.document.getElementById("taCustomerName").value='';
		window.close();
	}
	
	//ravi end---------------------------------------------------------------------

	// Ankit for bill Cancillation
	
	function getBillDtl()
	{	
		var type = window.opener.document.getElementById("headType").value;
		var pId = window.opener.document.getElementById("partyCode").value;
		//alert(pId);
		if(type=='Payable'){
			window.opener.document.getElementById('type').value='P';
			window.opener.document.getElementById('hType').value='P';
		}else if(type=='Receivable'){
			window.opener.document.getElementById('type').value='R';
			window.opener.document.getElementById('hType').value='R';
		}
		window.opener.document.getElementById('pCode').value=pId;
		window.close();
	}
	
	function getInstrumentNo()
	{
//	alert('in group name');
		var contextPath =window.opener.document.getElementById("contextPath").value;
		var type = window.opener.document.getElementById("type").value;
		var pId = window.opener.document.getElementById("partyCode").value;
		var billID = window.opener.document.getElementById("lbxBillgenId").value;
		
		var url=contextPath+"/billCancellation.do?actionName=getBilldtlAjaxAction";
		var data="type="+type+"&pId="+pId+"&billId="+billID;
		getInsNo(url,data);
	}

	function getInsNo(url,data){
		var request = getRequestObject();
		request.onreadystatechange = function () {
			setInsNo(request);	
		};
		request.open("POST", url, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	
	}
	function setInsNo(request){
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			window.opener.document.getElementById('instrumentNo').value=trim(str);
			window.close()
		}
	 }
// end by Ankit
	//Surendra Added
	function clearBankLovChildNew(){
		//alert("In BANK JS");
		
		window.opener.document.getElementById("bankBranchName").value="";  
	 	window.opener.document.getElementById("lbxBranchID").value="";
	 	window.opener.document.getElementById("micrCode").value="";
	 	window.opener.document.getElementById("ifscCode").value="";
	 	window.close();

	}
	
		//Code by arun for Param Value in Scoring Master
		function enaDisIntStringField(){
			var count= window.opener.document.getElementById("checkIdVal").value;
			//alert("count:--"+count);
			
			var dataType=window.opener.document.getElementById("hidDataType"+count).value;
			//alert("dataType:--"+dataType);
			if(dataType=='INT'){
				window.opener.document.getElementById("from"+count).value='';
				window.opener.document.getElementById("to"+count).value='';
				window.opener.document.getElementById("charValue"+count).value='';
				window.opener.document.getElementById("from"+count).removeAttribute("readonly",true);
				window.opener.document.getElementById("to"+count).removeAttribute("readonly",true);
				window.opener.document.getElementById("charValue"+count).setAttribute("readonly",true);
			}else if(dataType=='STRING'){
				window.opener.document.getElementById("from"+count).value='';
				window.opener.document.getElementById("to"+count).value='';
				window.opener.document.getElementById("charValue"+count).value='';
				window.opener.document.getElementById("from"+count).setAttribute("readonly",true);
				window.opener.document.getElementById("to"+count).setAttribute("readonly",true);
				window.opener.document.getElementById("charValue"+count).removeAttribute("readonly",true);
			}else{
				alert("Data type can not be blank or Incorrect ");
			}
			window.close();
		}
	//Code by arun for Param Value in Scoring Master
		
//Surendra Code for Disbursal Cancellation Ajax Data	
		function generateDisbValuesCancellation(){
			//alert("hi");
			 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;			 
			 //alert(businessDate);
			 if(loanId!='')
			 {
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveDisbCancellationValues";
				 var data = "lbxLoanNoHID="+loanId;
				 sendDisbCancellationid(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}
		function sendDisbCancellationid(address, data) {
			//alert("in sendCancellationid id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultDisbCancellation(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultDisbCancellation(request){
			
			//alert("in resultCancellation id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
				//alert(s1);
				window.opener.document.getElementById('loanAc').value=trim(s1[0]);  
				window.opener.document.getElementById('lbxLoanNoHID').value=trim(s1[1]);
				window.opener.document.getElementById('customerName').value=trim(s1[2]);
				window.opener.document.getElementById('loanDate').value=trim(s1[3]);
				window.opener.document.getElementById('loanAmt').value=trim(s1[4]);
				window.opener.document.getElementById('product').value=trim(s1[5]);
				window.opener.document.getElementById('scheme').value=trim(s1[6]);				
				window.close();
			}
		}
		//Surendra Code for Disbursal Cancellation Ajax Data
		
//Anil - - Emi Calculator Code  Start
		
		function getDefaultLoanDetailForEmiCal()
		 {
			// alert("In getDefaultLoanDetailForEmiCal");
		 	  var scheme =window.opener.document.getElementById('lbxscheme').value ;
		 	  var contextPath =window.opener.document.getElementById('contextPath').value ;
		 	  if (scheme!= '')
		 	  {
		 		var address = contextPath+"/relationalManagerAjaxAction.do?method=getDefaultLoanDetail";
		 		var data = "scheme=" + scheme;
		 		send_BaseRate_Emi_Cal(address, data);
		 		return true;
		 	  }
		 	  else
		 	  {
		     	 alert("Please Select List");	
		     	 return false;
		 	  }
		 }

		 function send_BaseRate_Emi_Cal(address, data) 
		 {
		 	
		 	var request = getRequestObject();
		 	request.onreadystatechange = function () {
		 		result_BaseRate_Emi_Cal(request);
		 	};
		 	request.open("POST", address, true);
		 	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 	request.send(data);
		 }

		 function result_BaseRate_Emi_Cal(request)
		 {    
				if ((request.readyState == 4) && (request.status == 200)) 
				{
					var str = request.responseText;
					var s1 = str.split("$:");
					if(s1.length>0)
				    {	  
						window.opener.document.getElementById('daysBasis').value = trim(s1[21]);
						if(trim(s1[21])!=''){
							var tenureInDay='';
							if(trim(s1[7])==''){
								tenureInDay=0;
							}
							if(trim(s1[21])=='A'){
							
							if(trim(s1[7])!=''){
							tenureInDay=trim(s1[7])*30.42;
							window.opener.document.getElementById('tenureInDay').value = tenureInDay.toFixed(0);
							}
							}
							else{
							tenureInDay=trim(s1[7])*30;
							window.opener.document.getElementById('tenureInDay').value = tenureInDay;
							}
						}
						//alert("trim(s1[12])::::::::"+trim(s1[12]));
						if(trim(s1[12])=='I')
						{
							//alert("A");	
							window.opener.document.getElementById("noOfInstall").removeAttribute("disabled",true);
				 	    	window.opener.document.getElementById("installments").removeAttribute("disabled",true);
				 	    	window.opener.document.getElementById("cycleDate").removeAttribute("disabled",true);
				 	    	window.opener.document.getElementById("frequency").removeAttribute("disabled",true);
				 	    	window.opener.document.getElementById("installmentType").removeAttribute("disabled",true);
				 	    	window.opener.document.getElementById("instMode").removeAttribute("disabled",true);
				    	   	window.opener.document.getElementById('requestedLoanTenure').value=trim(s1[7]);
				    	   	window.opener.document.getElementById('marginPerc').value = trim(s1[0]);
				    	   	window.opener.document.getElementById('rateType').value = trim(s1[1]);
				    	   	window.opener.document.getElementById("assCost1").style.display='';
				    	   	window.opener.document.getElementById("assCost2").style.display='';
//				    	   	window.opener.document.getElementById("noOfAsst1").style.display='';
//				    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	
				    	   	window.opener.document.getElementById("marPer1").style.display='';
				    	   	window.opener.document.getElementById("marPer2").style.display='';
				    	   	window.opener.document.getElementById("marAmount1").style.display='';
				    	   	window.opener.document.getElementById("marAmount2").style.display='';
				    	   	window.opener.document.getElementById("noOfIns1").style.display='';
				    	   	window.opener.document.getElementById("noOfIns2").style.display='';
				    	   	window.opener.document.getElementById("frequnc1").style.display='';
				    	   	window.opener.document.getElementById("frequnc2").style.display='';
				    	   	window.opener.document.getElementById("installType1").style.display='';
				    	   	window.opener.document.getElementById("installType2").style.display='';
				    	   	window.opener.document.getElementById("insMod1").style.display='';
				    	   	window.opener.document.getElementById("insMod2").style.display='';
				    	   	window.opener.document.getElementById("noIns2").style.display='';
				    	   	window.opener.document.getElementById("noIns1").style.display='';
				    	   	window.opener.document.getElementById("cyclDate1").style.display='';
				    	   	window.opener.document.getElementById("cyclDate2").innerHTML=trim(s1[27]);
				    		window.opener.document.getElementById("nddheader").style.display='';
							window.opener.document.getElementById("ndd").style.display='';
							window.opener.document.getElementById("effDateHeader").style.display='';
							window.opener.document.getElementById("effDate").style.display='';
							window.opener.document.getElementById("int1").style.display='none';
							window.opener.document.getElementById("int2").style.display='none';
							window.opener.document.getElementById('requestedLoanAmount').value ='';
							
				    	   	if(trim(s1[1])=='E')	
				    	   	{
				    	    	window.opener.document.getElementById('effectiveRate').value = trim(s1[6]);
				    	    }
				    	   	else
				    	   	{
				    	   		window.opener.document.getElementById('effectiveRate').value = trim(s1[5]);
				    	   	}
				    	   	window.opener.document.getElementById('repaymentType').value = trim(s1[12]);
				    	   	window.opener.document.getElementById('showRepaymentType').value = "INSTALLMENT";
				    	   	window.opener.document.getElementById('frequency').value = trim(s1[8]);
				    	   	var freqMonth;
				
				    	   	if(trim(s1[8])=='M')
				    	   	{
				    	   		freqMonth=1;
				    	   	}
				    	   	else if(trim(s1[8])=='B')
				    	   	{
				    	   		freqMonth=2;
				    	   	}
				    	   	else if(trim(s1[8])=='Q')
				    	   	{
				    	   		freqMonth=3;
				    	   	}
				    	   	else if(trim(s1[8])=='H')
				    	   	{
				    	   		freqMonth=6;
				    	   	}
				    	   	else if(trim(s1[8])=='Y')
				    	   	{
				    	   		freqMonth=12;
				    	   	}
				    	   	parsedFreq=parseInt(freqMonth);
				    	   	parseTenure=parseInt(trim(s1[7]));
							calInsat=parseTenure/parsedFreq;
							window.opener.document.getElementById('noOfInstall').value=calInsat;
							window.opener.document.getElementById('installmentType').value = trim(s1[9]);
							window.opener.document.getElementById('instMode').value = trim(s1[11]);
							window.opener.document.getElementById('productTypeFlag').value = trim(s1[20]);
							window.opener.document.getElementById('assetFlag').value = trim(s1[20]);							
							window.opener.document.getElementById('minMarginRate').value = trim(s1[14]);
							window.opener.document.getElementById('maxMarginRate').value = trim(s1[15]);
							window.opener.document.getElementById('minTenure').value = trim(s1[16]);
							window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
							window.opener.document.getElementById('minFinance').value = trim(s1[18]);
							window.opener.document.getElementById('maxFinance').value = trim(s1[19]);
							if(trim(s1[20])=='N')
							{
								window.opener.document.getElementById("netLtvRow").style.display='none';
								window.opener.document.getElementById('marginPerc').value = '';
								window.opener.document.getElementById("assetCost").setAttribute("disabled",true);
								window.opener.document.getElementById("assCost1").style.display='none';
								window.opener.document.getElementById("assCost2").style.display='none';
//								window.opener.document.getElementById("noOfAsst1").style.display='none';
//					    	   	window.opener.document.getElementById("noOfAsst2").style.display='none';	
								window.opener.document.getElementById("marginPerc").setAttribute("disabled",true);
								window.opener.document.getElementById("marPer1").style.display='none';
								window.opener.document.getElementById("marPer2").style.display='none';
								window.opener.document.getElementById("marginAmount").setAttribute("disabled",true);
								window.opener.document.getElementById("marAmount1").style.display='none';
								window.opener.document.getElementById("marAmount2").style.display='none';
							}
							else
							{
								window.opener.document.getElementById("netLtvRow").style.display='';
								window.opener.document.getElementById('netLtv').value = '';
								window.opener.document.getElementById("assetCost").removeAttribute("disabled",true);
								window.opener.document.getElementById("assCost1").style.display='';
								window.opener.document.getElementById("assCost2").style.display='';
//								window.opener.document.getElementById("noOfAsst1").style.display='';
//					    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	
								window.opener.document.getElementById("marginPerc").removeAttribute("disabled",true);
								window.opener.document.getElementById("marPer1").style.display='';
								window.opener.document.getElementById("marPer2").style.display='';
								window.opener.document.getElementById("marginAmount").removeAttribute("disabled",true);
								window.opener.document.getElementById("marAmount1").style.display='';
								window.opener.document.getElementById("marAmount2").style.display='';
							}
						}
						else  if(trim(s1[12])=='N')
						{   
							//alert("B");
							window.opener.document.getElementById("netLtvRow").style.display='none';
//							window.opener.document.getElementById("netLtvRowTD").style.display='none';	
							window.opener.document.getElementById('requestedLoanTenure').value=trim(s1[7]);
							window.opener.document.getElementById('marginPerc').value = trim(s1[0]);
							window.opener.document.getElementById('rateType').value = trim(s1[1]);
							if(trim(s1[1])!='E')	
							{
							 window.opener.document.getElementById('effectiveRate').value = trim(s1[5]);
							}
							window.opener.document.getElementById('repaymentType').value = trim(s1[12]);
							window.opener.document.getElementById('showRepaymentType').value =  "NON-INSTALLMENT";
							window.opener.document.getElementById('noOfInstall').value='';
							window.opener.document.getElementById("noOfInstall").setAttribute("disabled",true);
							window.opener.document.getElementById('installments').value='';
							window.opener.document.getElementById("installments").setAttribute("disabled",true);
							window.opener.document.getElementById('cycleDate').value='';
							window.opener.document.getElementById("cycleDate").setAttribute("disabled",true);
							window.opener.document.getElementById('frequency').value = '';
							window.opener.document.getElementById("frequency").setAttribute("disabled",true);	
							window.opener.document.getElementById('installmentType').value = '';
							window.opener.document.getElementById("installmentType").setAttribute("disabled",true);
							window.opener.document.getElementById('instMode').value = '';
							window.opener.document.getElementById("instMode").setAttribute("disabled",true);
							window.opener.document.getElementById('productTypeFlag').value = trim(s1[13]);
							window.opener.document.getElementById('minMarginRate').value = trim(s1[14]);
							window.opener.document.getElementById('maxMarginRate').value = trim(s1[15]);
							window.opener.document.getElementById('minTenure').value = trim(s1[16]);
							window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
							window.opener.document.getElementById('minFinance').value = trim(s1[18]);
							window.opener.document.getElementById('maxFinance').value = trim(s1[19]);
							window.opener.document.getElementById("int1").style.display='';
							window.opener.document.getElementById("int2").style.display='';
							window.opener.document.getElementById('requestedLoanAmount').value ='';
							window.opener.document.getElementById('assetCost').value ='';
							if(trim(s1[20])=='N'||trim(s1[12])=='N')
							{
								window.opener.document.getElementById('marginPerc').value = '';
								window.opener.document.getElementById("assetCost").setAttribute("disabled",true);
								window.opener.document.getElementById("assCost1").style.display='none';
								window.opener.document.getElementById("assCost2").style.display='none';
//								window.opener.document.getElementById("noOfAsst1").style.display='none';
//					    	   	window.opener.document.getElementById("noOfAsst2").style.display='none';	
								window.opener.document.getElementById("marginPerc").setAttribute("disabled",true);
								window.opener.document.getElementById("marPer1").style.display='none';
								window.opener.document.getElementById("marPer2").style.display='none';
								window.opener.document.getElementById("marginAmount").setAttribute("disabled",true);
								window.opener.document.getElementById("marAmount1").style.display='none';
								window.opener.document.getElementById("marAmount2").style.display='none';
								window.opener.document.getElementById("noOfIns1").style.display='none';
								window.opener.document.getElementById("noOfIns2").style.display='none';
								window.opener.document.getElementById("frequnc1").style.display='none';
								window.opener.document.getElementById("frequnc2").style.display='none';
								window.opener.document.getElementById("installType1").style.display='none';
								window.opener.document.getElementById("installType2").style.display='none';
								window.opener.document.getElementById("insMod1").style.display='none';
								window.opener.document.getElementById("insMod2").style.display='none';
								window.opener.document.getElementById("noIns1").style.display='none';
								window.opener.document.getElementById("noIns2").style.display='none';
								window.opener.document.getElementById("cyclDate1").style.display='none';
								window.opener.document.getElementById("cyclDate2").style.display='none';
								window.opener.document.getElementById("nddheader").style.display='none';
								window.opener.document.getElementById("ndd").style.display='none';
								window.opener.document.getElementById("effDateHeader").style.display='none';
								window.opener.document.getElementById("effDate").style.display='none';
							}
							else
							{
								window.opener.document.getElementById("assetCost").removeAttribute("disabled",true);
								window.opener.document.getElementById("assCost1").style.display='';
								window.opener.document.getElementById("assCost2").style.display='';
//								window.opener.document.getElementById("noOfAsst1").style.display='';
//					    	   	window.opener.document.getElementById("noOfAsst2").style.display='';	
								window.opener.document.getElementById("marginPerc").removeAttribute("disabled",true);
								window.opener.document.getElementById("marPer1").style.display='';
								window.opener.document.getElementById("marPer2").style.display='';
								window.opener.document.getElementById("marginAmount").removeAttribute("disabled",true);
								window.opener.document.getElementById("marAmount1").style.display='';
								window.opener.document.getElementById("marAmount2").style.display='';
								window.opener.document.getElementById("noOfIns1").style.display='';
								window.opener.document.getElementById("noOfIns2").style.display='';
								window.opener.document.getElementById("frequnc1").style.display='';
								window.opener.document.getElementById("frequnc2").style.display='';
								window.opener.document.getElementById("installType1").style.display='';
								window.opener.document.getElementById("installType2").style.display='';
								window.opener.document.getElementById("insMod1").style.display='';
								window.opener.document.getElementById("insMod2").style.display='';
								window.opener.document.getElementById("noIns2").style.display='';
								window.opener.document.getElementById("noIns1").style.display='';
								window.opener.document.getElementById("cyclDate1").style.display='';
								window.opener.document.getElementById("cyclDate2").innerHTML=trim(s1[27]);
							}	
						}
						
						window.close();
						
						}
					}
		
		 }		
		 
		 //Anil - - Emi Calculator Code  End
		 
		 
		 function checkRelationshipManage(){
			 var relationManager=window.opener.document.getElementById('relationshipManager').value;
			 var lbxUserSearchId=window.opener.document.getElementById('lbxUserSearchId').value;
//				 alert("lbxUserSearchId::::"+lbxUserSearchId);
					 var contextPath=window.opener.document.getElementById("contextPath").value;
					 var address = contextPath+"/ajaxActionForCP.do?method=checkRelationshipManage";
					 var data = "relationManager="+relationManager+"&lbxUserSearchId="+lbxUserSearchId;
					 sendCheckRelationshipManage(address, data);
					 return true;
				 
			}
			function sendCheckRelationshipManage(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					resultCheckRelationshipManage(request);	
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
				
			}

			function resultCheckRelationshipManage(request){
				if ((request.readyState == 4) && (request.status == 200)) {
					var str = request.responseText;
					var s1 = str.split("$:");
					//alert("Length:::"+s1.length);
					if(s1.length>0){
					window.opener.document.getElementById('lbxRelationship').value=trim(s1[0]);
					window.opener.document.getElementById('relationshipManager').value=trim(s1[1]);
					}
					window.close();
				}
			}
		 
		 function valuationMeth(){
				var val1 =window.opener.document.getElementById("techValuation").value;
				var val2 = window.opener.document.getElementById("technicalValuation1").value;
				var val3 = window.opener.document.getElementById("technicalValuation2").value;
				var method = window.opener.document.getElementById("valuationMethodId").value;
				if(val1==''){
				val1=0;
				window.opener.document.getElementById("techValuation").value="0.00";
				}
				else{
				val1=removeComma(val1);
				}
				if(val2==''){
					val2=0;
					window.opener.document.getElementById("technicalValuation1").value="0.00";
					}
				else{
					val2=removeComma(val2);
					}
				if(val3==''){
					val3=0;
					window.opener.document.getElementById("technicalValuation2").value="0.00";
					}
				else{
					val3=removeComma(val3);
					}
				
				if(method=='LOT'){
				var val=Math.min(val1,val2,val3);
				window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
				window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
				}
				if(method=='AOT'){
					
					var val=((val1+val2+val3)/3);
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='AOFS'){
					
					var val=((val1+val2)/2);
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='AOST'){
					
					var val=((val2+val3)/2);
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='AOTF'){
					
					var val=((val1+val3)/2);
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='TV1'){
					
					var val=val1;
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='TV2'){
					
					var val=val2;
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='TV3'){
					
					var val=val3;
					window.opener.document.getElementById("valuationAmount").value=val.toFixed(2);
					window.opener.document.getElementById("assetsCollateralValue").value=val.toFixed(2);
					}
				if(method=='OTHER'){
					window.opener.document.getElementById("valuationAmount").value='';
					window.opener.document.getElementById("valuationAmount").removeAttribute("readonly",true);
					}
				window.close();
			}
		 
		 
		 // Anil Yadav code for file tracking
		 
		 function getDealNumber()
		 {
		 	 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
		 	 if(loanId!='')
		 	 {
		 		 var contextPath=window.opener.document.getElementById("contextPath").value;
		 		 var address = contextPath+"/ajaxAction.do?method=getDealNumberValues";
		 		 var data = "lbxLoanNoHID="+loanId;
		 		 sendgetDealNumber(address, data);
		 		 return true;
		 	 }
		 	 else
		     {
		    	 alert("Please Select One Record");	
		    	 window.opener.document.getElementById('deal_viewer').disabled=true;
		 		 window.opener.document.getElementById('loan_viewer').disabled=true;
		    	 return false;
		     }
		 	 
		 }

		 function sendgetDealNumber(address, data) {
		 	var request = getRequestObject();
		 	request.onreadystatechange = function () {
		 		resultgetDealNumber(request);	
		 	};
		 	request.open("POST", address, true);
		 	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 	request.send(data);
		 	
		 }

		 function resultgetDealNumber(request){
		 	if ((request.readyState == 4) && (request.status == 200)) {
		 		var str = request.responseText;
				//alert("s1......"+str.split("$:").length);
				if(str.split("$:").length>1)
			    {
					var s1 = str.split("$:");
		 			window.opener.document.getElementById('lbxDealNo').value=trim(s1[1]); 
		 			window.opener.document.getElementById('deal_viewer').disabled='';
		 			window.opener.document.getElementById('loan_viewer').disabled='';
			    }
					else{
						window.opener.document.getElementById('lbxDealNo').value ='';
						window.opener.document.getElementById('lbxLoanNoHID').value ='';
						window.opener.document.getElementById('loanNo').value ='';
						window.opener.document.getElementById('customerName').value ='';
			 			alert("File status of this loan is already captured");
			 		}
				window.close();
		 	}
		 }
		 
		 // End by Anil


		//Arun Code For 
		function formatNumberForLovSuccess(val, san)
		{
			
			if(val != ''){

				var expAllVal = val.split('.');
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
					window.opener.document.getElementById(dynaVal).value = '';
					window.opener.document.getElementById(dynaVal).value = finalValueForDisp;
				}
				if(val.indexOf(',') > 0){
					  	var origString = val;
							var inChar = ',';
							var newString = origString.split(inChar);
							newString = newString.join('');
						
							window.opener.document.getElementById(dynaVal).value = '';
							window.opener.document.getElementById(dynaVal).value = newString;
					//alert(newString);
					}
				
			} 
		}
		//Added by arun Starts here for new disbursal with payment 
		function validateTABalance(){
			var balAmount=window.opener.document.getElementById("balAmount").value;
			var netAmount=window.opener.document.getElementById("netAmount").value;	
			
			if(balAmount!=""&&netAmount!=""){
				var parseBalAmount=removeComma(balAmount)
				var parseNetAmount=removeComma(netAmount)
				if(parseNetAmount>parseBalAmount){
					alert("Balance Amount is not available. Please select other loan");
					window.opener.document.getElementById('taLoanNo').value="";
					window.opener.document.getElementById('lbxTaLoanNoHID').value="";
					window.opener.document.getElementById('taCustomerName').value="";
				}
			}
			window.close();
		}
		//Added by arun ends here for new disbursal with payment
		function removeUser()
		{
			window.opener.document.getElementById("UserIssue").value='';
			window.opener.document.getElementById("lbxUserId").value='';	
			window.close();
		}
		
		//Richa ajax method starts here For Repo...........

		function getRepoMarkingDetail()
		{
					    
			 var loanId=window.opener.document.getElementById('loanId').value;
			
		
			 var basePath=window.opener.document.getElementById("contextPath").value;

			  var address = basePath+"/repoMarkingMakerAjax.do?method=getDetailofLoanForRepoMarkingMaker";
			  var data = "loanId=" + loanId ;
			
			  send_RepoMarkingDetail(address, data);
		       return true;
		}
		 function send_RepoMarkingDetail(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					result_RepoMarkingDetail(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
			}
			function result_RepoMarkingDetail(request){

				if ((request.readyState == 4) && (request.status == 200)) {
					var str = request.responseText;
					var s1 = str.split("$:");
					window.opener.document.getElementById('branch').value=trim(s1[0]);
					window.opener.document.getElementById('product').value=trim(s1[1]);
					window.opener.document.getElementById('scheme').value=trim(s1[2]);
					window.opener.document.getElementById('loanAmount').value=trim(s1[3]);
					window.opener.document.getElementById('balancePrincipal').value=trim(s1[4]);
					window.opener.document.getElementById('overduePrincipal').value=trim(s1[5]);
					window.opener.document.getElementById('overdueAmount').value=trim(s1[6]);
					window.opener.document.getElementById('dpd').value=trim(s1[7]);
					window.close();
					}

				}

//saurabh changes starts
		function generateLinkValues(){		
			 var loanId=window.opener.document.getElementById('lbxLoanNoHID').value;
			 if(loanId!='')
			 {
				 window.opener.document.getElementById('loanAmount').value='';  
				 window.opener.document.getElementById('loanTenure').value='';
				 window.opener.document.getElementById('loanNoSave').value='';  
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/ajaxAction.do?method=retriveLinkLoanValues";
				 var data = "lbxLoanNoHID="+loanId;
				 sendLinkDtl(address, data);
				 return true;
			 }
			 else
		     {
		    	 alert("Please Select One Record");	
		    	 return false;
		     }
			 
		}

		function sendLinkDtl(address, data) {
			//alert("in sendLinkDtl id");
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultLinkLoans(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
			
		}

		function resultLinkLoans(request){
			
			//alert("in resultLinkLoans id");
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
							
				window.opener.document.getElementById('loanAmount').value=trim(s1[1]);  
				window.opener.document.getElementById('loanTenure').value=trim(s1[2]);
				window.close();
			}
		}
//saurabh changes ends
		
		function showHideDescLov()
		{
			var source=window.opener.document.getElementById("source").value;
			window.opener.document.getElementById("description").value='';
			if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING" || source == "TELECALLER"  ||source == "IGR")
			{
				window.opener.document.getElementById("srcLOV").style.display="";
				window.opener.document.getElementById("description").readOnly = true;
			}
			else
			{
				window.opener.document.getElementById("srcLOV").style.display="none";
				window.opener.document.getElementById("description").readOnly = false;
			}
			window.close();
		}
		
		function showHideSourceDescLov()
		{
			var source=window.opener.document.getElementById("source").value;
			window.opener.document.getElementById("sourcedesc").value='';
			if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING" || source == "TELECALLER")
			{
				window.opener.document.getElementById("srcLOV").style.display="";
				window.opener.document.getElementById("sourcedesc").readOnly = true;
			}
			else
			{
				window.opener.document.getElementById("srcLOV").style.display="none";
				window.opener.document.getElementById("sourcedesc").readOnly = false;
			}
			window.close();
		}
		
		//Prashant starts
		function getBranchIdMICR()
		{
			 var depositBankAccount=window.opener.document.getElementById('depositBankAccount').value;
			 var depositBankID=window.opener.document.getElementById('depositBankID').value;
			 var depositIfscCode=window.opener.document.getElementById('depositIfscCode').value;
			 var receiptMode=window.opener.document.getElementById('receiptMode').value;
			 if(receiptMode != 'C' && receiptMode != 'S')
				 receiptMode='B';
			 //alert(makeModelId);
		
			 var basePath=window.opener.document.getElementById("contextPath").value;

			  var address = basePath+"/ajaxAction.do?method=getBranchIdMicrReceipt";
			  var data = "depositBankAccount="+depositBankAccount+"&depositBankID="+depositBankID+"&depositIfscCode="+depositIfscCode+"&receiptMode="+receiptMode;
			  send_BranchIdMICR(address, data);
		       return true;
		}
		 function send_BranchIdMICR(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					result_BranchIdMICR(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
			}
			function result_BranchIdMICR(request){

				if ((request.readyState == 4) && (request.status == 200)) {
					var str = request.responseText;
					var s1 = str.split("$:");	  
							 window.opener.document.getElementById('depositBranchID').value=trim(s1[0]);
							 window.opener.document.getElementById('depositMicr').value=trim(s1[1]);
	
					
					 window.close();
					}

				}
		//Prashant ends
//Manish Shukla Starts
	 function showEmpannel()
	 {
		 	
		  var dealerType=window.opener.document.getElementById('lbxdealerType').value;
		  //alert("dealer Type: "+dealerType);
		  if(dealerType=='SU')
		  {
			  window.opener.document.getElementById("empanel1").style.display="";
			  window.opener.document.getElementById("empanel2").style.display="";
		  }
		  else
		  {
			  window.opener.document.getElementById("empanel1").style.display="none";
			  window.opener.document.getElementById("empanel2").style.display="none";
		  }
		 window.close();
	 
	 }
	 
	 function getVehicleDetailsForAsset()
		{			
		 		var lbxEntity=window.opener.document.getElementById('lbxEntity').value;
		 		var basePath=window.opener.document.getElementById("contextPath").value;	
		 		var address = basePath+"/assetProcessAction.do?method=getVehicleDetails";
		 		var data = "lbxEntity="+lbxEntity;
		 		getVehicleDetailsData(address, data);
		 		return true;
		}
		
	 function getVehicleDetailsData(address, data) 
		 {			
				var request = getRequestObject();
				request.onreadystatechange = function () {
					resultgetVehicleDetails(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
		}
		
	 function resultgetVehicleDetails(request)
		 {
			 if ((request.readyState == 4) && (request.status == 200)) 
				{				 
					var str = request.responseText;
					var s1 = str.split("$:");
					window.opener.document.getElementById('assetDesc').value=trim(s1[0]); 
					window.opener.document.getElementById('assetNature').value=trim(s1[1]);
					window.opener.document.getElementById('assetMake').value=trim(s1[2]);
					window.opener.document.getElementById('assetModel').value=trim(s1[3]);
					window.opener.document.getElementById('dealerName').value=trim(s1[4]);
					window.opener.document.getElementById('engineNumber').value=trim(s1[5]);
			        window.opener.document.getElementById('chasisNumber').value=trim(s1[6]);
			        window.opener.document.getElementById('registrationNumber').value=trim(s1[7]);
					window.close();
				}
			}
	//mradul ends ajax for asset insurance vehicle details
	 
	 //Nishant starts
		function getFleetData()
		{
			 var lbxLoanNoHid=window.opener.document.getElementById('lbxLoanNoHid').value;
			 var basePath=window.opener.document.getElementById("contextPath").value;
			 var address = basePath+"/ajaxActionForCP.do?method=getFleetDataList";
			 var data = "lbxLoanNoHid="+lbxLoanNoHid;
			 sendRequestFleetData(address, data);
		     return true;
		}
		 function sendRequestFleetData(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					resultMethodFleetData(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
			}
		function resultMethodFleetData(request){

			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
						 window.opener.document.getElementById('seasoning').value=trim(s1[0]);
						 window.opener.document.getElementById('currentPos').value=trim(s1[1]);
						 window.opener.document.getElementById('vehicleOwner').value=trim(s1[2]);
						 window.opener.document.getElementById('relationship').value=trim(s1[3]);
						 window.opener.document.getElementById('vehicleNo').value=trim(s1[4]);
						 window.opener.document.getElementById('vehicleModel').value=trim(s1[5]);
						 window.opener.document.getElementById('mfgYear').value=trim(s1[6]);
				
				 window.close();
				}

			}
		
	 //Nishant ends
		function getDefaultBusinessPartnerTypeVeh()
		{
			var lbxLoanNoHID=window.opener.document.getElementById('lbxLoanNoHID').value;
			var vehicleNo=window.opener.document.getElementById('vehicleNo').value;
			var bpType='CS';
			 if(lbxLoanNoHID!='' || vehicleNo!='')
			 {
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 window.opener.location.href = contextPath+"/receiptMakerProcessAction.do?method=getDefaultBusinessPartnerTypeReceipt&lbxLoanNoHID="+lbxLoanNoHID+"&bpType="+bpType+"&vehicleNo="+vehicleNo+"";
				 self.close();
				 return true;
			 }
			 else
			 {
				 alert("Please Select One Record");	
		  	 	 return false;
			 }
			 
		}
		function clearDealer()
		{
			window.opener.document.getElementById('dealerExecutive').value=''; 
			window.opener.document.getElementById('lbxDealerExecutive').value='';
			window.opener.document.getElementById('dealerManager').value='';
			window.opener.document.getElementById('lbxDealerManager').value='';
			window.close();
		}
		
	 //Nishant ends
		
		
//Ankit For bill capturing
		
		function getAdvPayTds()
		{
			 var suppId=window.opener.document.getElementById('lbxSupplier').value;
			 var basePath=window.opener.document.getElementById("contextPath").value;
			 var address = basePath+"/glBillCaptureAjax.do?action=getAdvPaymTds";
			 var data = "suppId=" + suppId ;
			
			  send_AdvPayTds(address, data);
		       return true;
		}
		 function send_AdvPayTds(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					result_AdvPayTds(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
			}
			function result_AdvPayTds(request){

				if ((request.readyState == 4) && (request.status == 200)) {
					var str = request.responseText;
					var s1 = str.split("$:");
					window.opener.document.getElementById('aviAdvPay').value=trim(s1[0]);
					window.opener.document.getElementById('aviAdvTds').value=trim(s1[1]);
					window.close();
					}

				}
				
	
			function payeeNameRefreshValue()
				{
				
	var lbxpayTo=document.getElementById("lbxpayTo").value;
	if(lbxpayTo=='OTH')
	{ 
		
		document.getElementById("payeeName").removeAttribute("readOnly","true");
		document.getElementById("payeeName").removeAttribute("tabIndex","-1");
		document.getElementById("payeeNameButton").setAttribute("disabled","true");
		document.getElementById("payeeNameButtonRSP").setAttribute("disabled","true");
		window.close();
	}
	else if(lbxpayTo=='RSP'){
		/*window.opener.document.getElementById("payeeName").value="";
		window.opener.document.getElementById("lbxpayeeName").value="";
		var lbxpayTo=window.opener.document.getElementById("lbxpayTo").value;*/
		
		
		if(lbxpayTo=='RSP')
		{
			//document.getElementById("payeeName").removeAttribute("readOnly","true");
			document.getElementById("payeeName").removeAttribute("tabIndex","-1");
			document.getElementById("payeeNameButton").setAttribute("disabled","true");
			
			//window.opener.document.getElementById("payeeNameDiv").style.display='';
			document.getElementById("payeeNameButton").style.display='none';
			document.getElementById("payeeNameButtonRSP").style.display='';
			window.close();
		}
		
			else
				window.close();
	}
	else 
	{
		document.getElementById("payeeName").setAttribute("readOnly","true");
		document.getElementById("payeeNameButton").removeAttribute("disabled","true");
		document.getElementById("payeeName").setAttribute("tabIndex","-1");	
		document.getElementById("payeeNameButton").style.display='';
		document.getElementById("payeeNameButtonRSP").style.display='none';		
		
		if(lbxpayTo=='CS')
		{			
			var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
			var contextPath=document.getElementById("contextPath").value;
			var address = contextPath+"/ajaxAction.do?method=getPayeeName";
			var data = "lbxLoanNoHID="+lbxLoanNoHID;
			sendPayeeName(address, data);
		}
		 else
			document.getElementById("payeeNameButton").style.display='';
			
		document.getElementById("payeeNameButtonRSP").style.display='none';	
			window.close();
	}
				}
			
			function validateTABalancePayment(){
				var balAmount=window.opener.document.getElementById("balAmount").value;
				var paymentAmount=window.opener.document.getElementById("paymentAmount").value;	
				
				if(paymentAmount=="")
					{
					alert("Please enter Payment amount first");
					window.opener.document.getElementById('taLoanNo').value="";
					window.opener.document.getElementById('lbxTaLoanNoHID').value="";
					window.opener.document.getElementById('taCustomerName').value="";
					window.close();
					}
				if(balAmount!=""&&paymentAmount!=""){
					var parseBalAmount=removeComma(balAmount)
					var parsePaymentAmount=removeComma(paymentAmount)
					if(parsePaymentAmount>parseBalAmount){
						alert("Balance Amount is not available. Please select other loan");
						window.opener.document.getElementById('taLoanNo').value="";
						window.opener.document.getElementById('lbxTaLoanNoHID').value="";
						window.opener.document.getElementById('taCustomerName').value="";
					}
				}
				window.close();
			}
				
		function getSiRdName()
		{
			
			window.opener.document.getElementById('lbxmachineSupplier').value="";
			window.opener.document.getElementById('machineSupplier').value="";
			window.close();
		}
					
					
					
		function calgoldOrnamnet()
	 {
	var grossWeight =window.opener.document.getElementById("grossWeight");
  var deduction = window.opener.document.getElementById("deduction");
  var netWeight = window.opener.document.getElementById("netWeight");
  var loanAmountEligible= window.opener.document.getElementById("loanAmountEligible");
	var goldOrnamentLTV= window.opener.document.getElementById("goldOrnamentLTV");
	var rateGoldOrnament= window.opener.document.getElementById("rateGoldOrnament");
	var netOrnamentValue= window.opener.document.getElementById("netOrnamentValue");
	
	
if(rateGoldOrnament==undefined ||rateGoldOrnament==null ||rateGoldOrnament.value=='' )
	 {
		 rateGoldOrnament=0; 
		
	 }else{
	  rateGoldOrnament= parseInt(window.opener.document.getElementById("rateGoldOrnament").value);
	 }
		
	if( netWeight==undefined || netWeight== null  ||netWeight.value=='')
	 {
		 netWeight=0; 
		
	 }else{
	  netWeight=parseInt(window.opener.document.getElementById("netWeight").value);
	 }
	if( goldOrnamentLTV==undefined || goldOrnamentLTV== null  ||goldOrnamentLTV.value=='')
	{
	   goldOrnamentLTV=0;
	 }else{
	  goldOrnamentLTV= parseInt(window.opener.document.getElementById("goldOrnamentLTV").value);
	 }
	 
	 if( grossWeight==undefined || grossWeight== null  ||grossWeight.value=='')
	 {
	   grossWeight=0;
	 }
	 else{
	  grossWeight= parseInt(window.opener.document.getElementById("grossWeight").value);
	 }
	 if( deduction==undefined || deduction== null  ||deduction.value=='')
	 {
	   deduction=0;
	 } else{
	  deduction= parseInt(window.opener.document.getElementById("deduction").value);
	 }
	  if( netOrnamentValue==undefined || netOrnamentValue== null  ||netOrnamentValue.value=='')
	  {
	   netOrnamentValue=0;
	 }else{
	  netOrnamentValue= parseInt(window.opener.document.getElementById("netOrnamentValue").value);
	 }
	 
	    
		if(grossWeight==0 && deduction>0)
       {
	       alert("Gross Weight is required");
	     window.close();
          }
     else{
    netWeight=grossWeight-deduction;
    window.opener.document.getElementById("netWeight").value=netWeight.toFixed(3);
    
		loanAmountEligible=rateGoldOrnament*goldOrnamentLTV*netWeight;
		window.opener.document.getElementById("loanAmountEligible").value=loanAmountEligible.toFixed(3);
		netOrnamentValue=rateGoldOrnament*netWeight;
		window.opener.document.getElementById("netOrnamentValue").value=netOrnamentValue.toFixed(3);
		window.close();

	 }
	}				
				

		//Rohit changes  end
		function clearStateLovChildNew(){		
			window.opener.document.getElementById("dist").value="";
		 	window.opener.document.getElementById("txtDistCode").value="";
		 	window.opener.document.getElementById("tahsil").value="";	 	
		 	window.close();	
		}
		 //amandeep ends

		function getCountryStateDistrictTahsilValue(){
			 var lbxPincode=window.opener.document.getElementById('lbxPincode').value;
			 var txnTahsilHID=window.opener.document.getElementById('txnTahsilHID').value;
			 window.opener.document.getElementById('tahsil4').value ="";
			 if( window.opener.document.getElementById('tahsil'))
				 window.opener.document.getElementById('tahsil').value ="";
			 window.opener.document.getElementById('txtDistCode').value="";
			 window.opener.document.getElementById('dist4').value="";
			 window.opener.document.getElementById('txtStateCode').value="";
			 window.opener.document.getElementById('state6').value="";
			 if(txnTahsilHID=='DUMMY')
				 {
				 window.opener.document.getElementById("lov1").style.display="none";
				 window.opener.document.getElementById("lov2").style.display="none";
				 window.opener.document.getElementById("lov3").style.display="";
				 window.opener.document.getElementById("lov4").style.display="";
				 window.opener.document.getElementById("lov5").style.display="none";
				 window.opener.document.getElementById("lov6").style.display="";
				 window.opener.document.getElementById('txnTahsilHID').value=txnTahsilHID;
				
				 }
			 
			 else
				 {
				 
				 window.opener.document.getElementById("lov1").style.display="";
				 window.opener.document.getElementById("lov2").style.display="";
				 window.opener.document.getElementById("lov3").style.display="none";
				 window.opener.document.getElementById("lov4").style.display="none";
				 window.opener.document.getElementById("lov5").style.display="";
				 window.opener.document.getElementById("lov6").style.display="none";
				 
				 
				 var basePath=window.opener.document.getElementById("contextPath").value;
				 var address = basePath+"/ajaxActionForCP.do?method=getCountryStateDistrictTahsilValue";
				 var data = "lbxPincode="+lbxPincode;
				 sendRequestCountryStateDistrictTahsilValue(address, data);
			     return true;
				 }
			 window.close();
		}
		 function sendRequestCountryStateDistrictTahsilValue(address, data) {
				var request = getRequestObject();
				request.onreadystatechange = function () {
					resultMethodCountryStateDistrictTahsilValue(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
			}
		function resultMethodCountryStateDistrictTahsilValue(request){

			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
				window.opener.document.getElementById('txnTahsilHID').value=trim(s1[1]);
				window.opener.document.getElementById('tahsil').value=trim(s1[2]);
				window.opener.document.getElementById('tahsilDesc').value=trim(s1[2]);
				window.opener.document.getElementById('txtDistCode').value=trim(s1[3]);
				window.opener.document.getElementById('dist').value=trim(s1[4]);
				window.opener.document.getElementById('txtStateCode').value=trim(s1[5]);
				window.opener.document.getElementById('state').value=trim(s1[6]);
				window.opener.document.getElementById('txtCountryCode').value=trim(s1[7]);
				window.opener.document.getElementById('country').value=trim(s1[8]);
				
				 window.close();
				}

			}
		//Rohit Changes Starts for ACH
		
		function fetchACHCustomerDetail ()
		{
			 var dealId=window.opener.document.getElementById('hidDealNo').value;
			 var basePath=window.opener.document.getElementById("contextPath").value;
			 var address = basePath+"/ajaxActionForCP.do?method=fetchACHCustomerDetail";
			 var data = "hidDealId=" + dealId;
			
			 send_ACHCustDetail(address, data);
		     return true;

		}
		function send_ACHCustDetail(address, data)
		{
				var request = getRequestObject();
				request.onreadystatechange = function () {
					result_ACHCustDetail(request);
				};
				request.open("POST", address, true);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.send(data);
		}

		function result_ACHCustDetail(request)
		{
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;
				var s1 = str.split("$:");
				
				window.opener.document.getElementById("dteDate").value=trim(s1[1]);
				window.opener.document.getElementById("txtWeHerebyAuthorize").value=trim(s1[2]);
				window.opener.document.getElementById("txtBankAccountNo").value=trim(s1[3]);
				window.opener.document.getElementById("hidBankName").value=trim(s1[4]);
				window.opener.document.getElementById("txtBankName").value=trim(s1[5]);
				window.opener.document.getElementById("hidBankBranchName").value=trim(s1[6]);
				window.opener.document.getElementById("txtBankBranchName").value=trim(s1[7]);
				window.opener.document.getElementById("txtMicr").value=trim(s1[8]);
				window.opener.document.getElementById("txtIfsc").value=trim(s1[9]);
				window.opener.document.getElementById("txtLoanAmount").value=trim(s1[10]);
				window.opener.document.getElementById("txtTotalAmount").value=trim(s1[11]);
				window.opener.document.getElementById("txtPhoneNo").value=trim(s1[12]);
				window.opener.document.getElementById("txtEmailId").value=trim(s1[13]);
				window.opener.document.getElementById("selFrequency").value=trim(s1[14]);
				window.opener.document.getElementById("dteFromDate").value=trim(s1[15]);
				window.opener.document.getElementById("dteToDate").value=trim(s1[16]);
				
				window.opener.document.getElementById("txtSponsorBankCode").value=trim(s1[17]);
				window.opener.document.getElementById("txtUtilityCode").value=trim(s1[18]);
				window.opener.document.getElementById("txtReferenceNo").value=trim(s1[19]);
				window.opener.document.getElementById("txtNameAccountHolder").value=window.opener.document.getElementById("txtCustomerName").value;

				window.close();
				}
		}
		//Rohit end	
		
		function getDefaultLoanFacilityDetail()
		{
			  var scheme =window.opener.document.getElementById('schemeId').value ;
			  var contextPath =window.opener.document.getElementById('contextPath').value ;
			  if (scheme!= '')
			  {
				var address = contextPath+"/relationalManagerAjaxAction.do?method=getDefaultLoanDetail";
				var data = "scheme=" + scheme;
				send_BaseRate_facility(address, data);
				return true;
			  }
			  else
			  {
		    	 alert("Please Select List");	
		    	 return false;
			  }
		}
		function send_BaseRate_facility(address, data) 
		{
			
			var request = getRequestObject();
			request.onreadystatechange = function () {
				result_BaseRate_facilty(request);
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}
		function result_BaseRate_facilty(request)
		{    
			if ((request.readyState == 4) && (request.status == 200)) 
			{
				var str = request.responseText;
				var s1 = str.split("$:");
				if(s1.length>0)
			    {	  
		
						
					window.opener.document.getElementById('interestRateMethod').value = trim(s1[2]);
			    	   	if(trim(s1[1])=='E')	
			    	   	{
			    	   		window.opener.document.getElementById('baseRateType').value = trim(s1[3]);
			    	   		window.opener.document.getElementById('baseRate').value = trim(s1[4]);
			    	   		window.opener.document.getElementById('rate').value = trim(s1[3]);
			    	    	window.opener.document.getElementById('markup').value=trim(s1[6])-trim(s1[4]);
			    	    	window.opener.document.getElementById('finalRate').value = trim(s1[6]);
			    	    	
			    	    	window.opener.document.getElementById("baseRateType").setAttribute("disabled",true);
			    	    	window.opener.document.getElementById('minFlatRate').value = trim(s1[23]);
			    	    	window.opener.document.getElementById('maxFlatRate').value = trim(s1[24]);
			    	    	window.opener.document.getElementById('minEffectiveRate').value = trim(s1[25]);
			    	    	window.opener.document.getElementById('maxEffectiveRate').value = trim(s1[26]);
			    	    	window.opener.document.getElementById('defaultFlatRate').value = trim(s1[5]);
			    	    	window.opener.document.getElementById('defaultEffectiveRate').value = trim(s1[6]);
			    	    	
			    	    
			    	    	
			    	    	
			    	   	}
			    	   	else
			    	   	{
			    	   		
			    	   		window.opener.document.getElementById("baseRateType").value='';
			    	   		window.opener.document.getElementById("baseRate").value='';
			    	   		window.opener.document.getElementById("markup").value='';
			    	   		window.opener.document.getElementById('rate').value = '';
			    	 
			    	   		window.opener.document.getElementById("baseRateType").setAttribute("disabled",true);
			    	   		window.opener.document.getElementById("baseRate").setAttribute("disabled",true);
			    	   		window.opener.document.getElementById('finalRate').value = trim(s1[5]);
			    	   	
			    	   		
			    	   		window.opener.document.getElementById('minFlatRate').value = trim(s1[23]);
			    	    	window.opener.document.getElementById('maxFlatRate').value = trim(s1[24]);
			    	    	window.opener.document.getElementById('minEffectiveRate').value = trim(s1[25]);
			    	    	window.opener.document.getElementById('maxEffectiveRate').value = trim(s1[26]);
			    	    	
			    	    	window.opener.document.getElementById('defaultFlatRate').value = trim(s1[5]);
			    	    	window.opener.document.getElementById('defaultEffectiveRate').value = trim(s1[6]);
			    	   	}
			    	   	
			    	  
			    	
			    	//   	calculateMaturityDateInDeal();
			    	   	var freqMonth;
			
			    	   	if(trim(s1[8])=='M')
			    	   	{
			    	   		freqMonth=1;
			    	   	}
			    	   	else if(trim(s1[8])=='B')
			    	   	{
			    	   		freqMonth=2;
			    	   	}
			    	   	else if(trim(s1[8])=='Q')
			    	   	{
			    	   		freqMonth=3;
			    	   	}
			    	   	else if(trim(s1[8])=='H')
			    	   	{
			    	   		freqMonth=6;
			    	   	}
			    	   	else if(trim(s1[8])=='Y')
			    	   	{
			    	   		freqMonth=12;
			    	   	}
			    	   	parsedFreq=parseInt(freqMonth);
			    	   	parseTenure=parseInt(trim(s1[7]));
						calInsat=parseTenure/parsedFreq;
				
						window.opener.document.getElementById('noOfInstl').value=calInsat;
						window.opener.document.getElementById('minMarginRate').value = trim(s1[14]);
						window.opener.document.getElementById('maxMarginRate').value = trim(s1[15]);
						window.opener.document.getElementById('minTenure').value = trim(s1[16]);
						window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
						window.opener.document.getElementById('minFinance').value = trim(s1[18]);
						window.opener.document.getElementById('maxFinance').value = trim(s1[19]);
						
					window.close();
					
					}
				}
			}
		//Rohit end	
		function CoApplovIndustryLead()
		{
			var industry=window.opener.document.getElementById('CoAppindustry').value;
			
			if(industry!='')
			{
				window.opener.document.getElementById('CoAppsubIndustry').value='';
			}
			
			window.close();
		}
		function GaurlovIndustryLead()
		{
			var industry=window.opener.document.getElementById('Gaurindustry').value;
			
			if(industry!='')
			{
				window.opener.document.getElementById('GaursubIndustry').value='';
			}
			
			window.close();
		}
		function copyAppCustomerDetails()
		{
			 var customerId=window.opener.document.getElementById('lbxCustomerId').value;
			 var addressId=window.opener.document.getElementById('addressId').value;
			 if(customerId!='')
			 {
				 customerId=customerId.substring(customerId.indexOf("/")).replace("/", "").trim();
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/preDealCapturingBehindAction.do?method=searchCustomer&customerId="+customerId+"&addressId="+addressId;
				 var data = "customerId="+customerId;
				 sendAppCopyCustomer(address, data);
				 return true;
			 }
		}
		function copyCoAppCustomerDetails()
		{
			 var customerId=window.opener.document.getElementById('coApplbxCustomerId').value;
			 var addressId=window.opener.document.getElementById('coAppaddressId').value;
			 if(customerId!='')
			 {
				 customerId=customerId.substring(customerId.indexOf("/")).replace("/", "").trim();
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/preDealCapturingBehindAction.do?method=searchCustomer&customerId="+customerId+"&addressId="+addressId;
				 var data = "customerId="+customerId;
				 sendCoAppCopyCustomer(address, data);
				 return true;
			 }
		}
		function copyGaurCustomerDetails()
		{
			 var customerId=window.opener.document.getElementById('gaurlbxCustomerId').value;
			 var addressId=window.opener.document.getElementById('gauraddressId').value;
			 if(customerId!='')
			 {
				 customerId=customerId.substring(customerId.indexOf("/")).replace("/", "").trim();
				 var contextPath=window.opener.document.getElementById("contextPath").value;
				 var address = contextPath+"/preDealCapturingBehindAction.do?method=searchCustomer&customerId="+customerId+"&addressId="+addressId;
				 var data = "customerId="+customerId;
				 sendGaurCopyCustomer(address, data);
				 return true;
			 }
		}
		function sendAppCopyCustomer(address, data) {
			
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultAppCopyCustomer(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);	
		}

		function sendCoAppCopyCustomer(address, data) {
			
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultCoAppCopyCustomer(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);	
		}
		function sendGaurCopyCustomer(address, data) {
			
			var request = getRequestObject();
			request.onreadystatechange = function () {
				resultGaurCopyCustomer(request);	
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);	
		}
		function resultAppCopyCustomer(request){
			

			if ((request.readyState == 4) && (request.status == 200)) 
			{
				var str = request.responseText;

				var s1 = str.split("$:");
				if(window.opener.document.getElementById("customerType").value=trim(s1[7])=='C'){
							
					window.opener.document.getElementById("individualfield").style.display='none';
					window.opener.document.getElementById("corporatefield").style.display='';
					window.opener.document.getElementById("corporateconstitution").style.display='';
					window.opener.document.getElementById("Individualconstitution").style.display='none';
					window.opener.document.getElementById("constitution").style.display='none';
					window.opener.document.getElementById("customerName").value=trim(s1[0]);
					window.opener.document.getElementById("hGroupId").value=trim(s1[1]);
					window.opener.document.getElementById("registrationNo").value=trim(s1[2]);
					window.opener.document.getElementById("custPan").value=trim(s1[3]);
					if(window.opener.document.getElementById("businessSegment"))
					window.opener.document.getElementById("businessSegment").value=trim(s1[4]);
					window.opener.document.getElementById("businessSegment1")
					window.opener.document.getElementById("businessSegment1").value=trim(s1[4]);
					window.opener.document.getElementById("lbxIndustry").value=trim(s1[5]);
					window.opener.document.getElementById("lbxSubIndustry").value=trim(s1[6]);
					window.opener.document.getElementById("customerType").value='C';
					window.opener.document.getElementById("customerType1").removeAttribute("disabled");
					window.opener.document.getElementById("customerType1").value='C';
					window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
					window.opener.document.getElementById("corconstitution").value=trim(s1[8]);
					window.opener.document.getElementById("address1").value=trim(s1[12]);
					window.opener.document.getElementById("address2").value=trim(s1[13]);
					window.opener.document.getElementById("addresstype")
					window.opener.document.getElementById("addresstype").value=trim(s1[14]);
					if(window.opener.document.getElementById("addresstype1"))
					window.opener.document.getElementById("addresstype1").value=trim(s1[14]);
					window.opener.document.getElementById("pincode").value=trim(s1[16]);
					window.opener.document.getElementById("txtCountryCode").value=trim(s1[17]);
					window.opener.document.getElementById("txtStateCode").value=trim(s1[18]);
					window.opener.document.getElementById("txtDistCode").value=trim(s1[19]);
					window.opener.document.getElementById("email").value=trim(s1[20]);
					window.opener.document.getElementById("groupName").value=trim(s1[21]);
					window.opener.document.getElementById("country").value=trim(s1[22]);
					window.opener.document.getElementById("dist").value=trim(s1[23]);
					window.opener.document.getElementById("state").value=trim(s1[24]);
					window.opener.document.getElementById("industry").value=trim(s1[25]);
					window.opener.document.getElementById("subIndustry").value=trim(s1[26]);
		            
		            window.opener.document.getElementById("phoneOff").value=trim(s1[28]);
		            window.opener.document.getElementById("address3").value=trim(s1[34]);
	                window.opener.document.getElementById("middleName").value="";
	                window.opener.document.getElementById("landmark").value=trim(s1[36]);
	                window.opener.document.getElementById("groupType").value=trim(s1[37]);
	                if(trim(s1[37])=='N'){
	                	window.opener.document.getElementById("groupText").style.display='';
	                	window.opener.document.getElementById("groupLov").style.display='none';
	                	window.opener.document.getElementById("groupName1").value=trim(s1[21]);
	                }else{
	                	window.opener.document.getElementById("groupText").style.display='none';
	                	window.opener.document.getElementById("groupLov").style.display='';
	                	window.opener.document.getElementById("groupName1").value="";
	                }
		            window.opener.document.getElementById("fatherName").value="";
		            window.opener.document.getElementById("aadhaar").value="";
		            window.opener.document.getElementById("voterId").value="";
		            window.opener.document.getElementById("passport").value="";
					window.opener.document.getElementById("firstName").value="";
					window.opener.document.getElementById("lastName").value="";
					window.opener.document.getElementById("custDOB").value="";
					window.opener.document.getElementById("custPanInd").value="";

				}else
					if(window.opener.document.getElementById("customerType").value=trim(s1[7])=='I'){
						window.opener.document.getElementById("corporatefield").style.display='none';
						window.opener.document.getElementById("individualfield").style.display='';
						window.opener.document.getElementById("corporateconstitution").style.display='none';
						window.opener.document.getElementById("Individualconstitution").style.display='';
						window.opener.document.getElementById("constitution").style.display='none';
						window.opener.document.getElementById("custPanInd").value=trim(s1[3]);
						window.opener.document.getElementById("customerType").value='I';
		                window.opener.document.getElementById("customerType1").removeAttribute("disabled");
						window.opener.document.getElementById("customerType1").value='I';
						window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
						window.opener.document.getElementById("indconstitution").value=trim(s1[8]);
						window.opener.document.getElementById("firstName").value=trim(s1[9]);
						window.opener.document.getElementById("lastName").value=trim(s1[10]);
						window.opener.document.getElementById("custDOB").value=trim(s1[11]);
						window.opener.document.getElementById("address1").value=trim(s1[12]);
						window.opener.document.getElementById("address2").value=trim(s1[13]);
						window.opener.document.getElementById("addresstype")
						window.opener.document.getElementById("addresstype").value=trim(s1[14]);
						if(window.opener.document.getElementById("addresstype1"))
						window.opener.document.getElementById("addresstype1").value=trim(s1[14]);
						window.opener.document.getElementById("pincode").value=trim(s1[16]);
						window.opener.document.getElementById("txtCountryCode").value=trim(s1[17]);
						window.opener.document.getElementById("txtStateCode").value=trim(s1[18]);
						window.opener.document.getElementById("txtDistCode").value=trim(s1[19]);
						window.opener.document.getElementById("email").value=trim(s1[20]);
						window.opener.document.getElementById("country").value=trim(s1[22]);
						window.opener.document.getElementById("dist").value=trim(s1[23]);
						window.opener.document.getElementById("state").value=trim(s1[24]);
		            
		                window.opener.document.getElementById("phoneOff").value=trim(s1[28]);
		                window.opener.document.getElementById("fatherName").value=trim(s1[29]);
		                window.opener.document.getElementById("aadhaar").value=trim(s1[30]);
		                window.opener.document.getElementById("voterId").value=trim(s1[31]);
		                window.opener.document.getElementById("passport").value=trim(s1[32]);
		                //
		                window.opener.document.getElementById("genderIndividual").value=trim(s1[33]);
		                window.opener.document.getElementById("address3").value=trim(s1[34]);
		                window.opener.document.getElementById("middleName").value=trim(s1[35]);
		                window.opener.document.getElementById("landmark").value=trim(s1[36]);
		                window.opener.document.getElementById("groupType").value="";
						window.opener.document.getElementById("customerName").value="";
						window.opener.document.getElementById("hGroupId").value="";
						window.opener.document.getElementById("registrationNo").value="";
					
						window.opener.document.getElementById("businessSegment").value="";
						window.opener.document.getElementById("lbxIndustry").value="";
						window.opener.document.getElementById("lbxSubIndustry").value="";
						window.opener.document.getElementById("groupName").value="";
		                window.opener.document.getElementById("custPan").value="";
					}else{
						window.opener.document.getElementById("corporateconstitution").style.display='none';
						window.opener.document.getElementById("Individualconstitution").style.display='none';
						window.opener.document.getElementById("customerType").value="";
						window.opener.document.getElementById("constitution").value="";
						window.opener.document.getElementById("firstName").value="";
						window.opener.document.getElementById("lastName").value="";
						window.opener.document.getElementById("custDOB").value="";
						window.opener.document.getElementById("address1").value="";
						window.opener.document.getElementById("address2").value="";
						window.opener.document.getElementById("addresstype").value="";
					
						window.opener.document.getElementById("pincode").value="";
						window.opener.document.getElementById("txtCountryCode").value="";
						window.opener.document.getElementById("txtStateCode").value="";
						window.opener.document.getElementById("txtDistCode").value="";
						window.opener.document.getElementById("email").value="";
						window.opener.document.getElementById("country").value="";
						window.opener.document.getElementById("dist").value="";
						window.opener.document.getElementById("state").value="";
						window.opener.document.getElementById("customerName").value="";
						window.opener.document.getElementById("hGroupId").value="";
						window.opener.document.getElementById("registrationNo").value="";
						window.opener.document.getElementById("custPan").value="";
		                window.opener.document.getElementById("custPanInd").value="";
						window.opener.document.getElementById("businessSegment").value="";
						window.opener.document.getElementById("lbxIndustry").value="";
						window.opener.document.getElementById("lbxSubIndustry").value="";
						window.opener.document.getElementById("groupName").value="";
						window.opener.document.getElementById("fatherName").value="";
		                window.opener.document.getElementById("aadhaar").value="";
		                window.opener.document.getElementById("voterId").value="";
		                window.opener.document.getElementById("passport").value="";
		                window.opener.document.getElementById("address3").value="";
		                window.opener.document.getElementById("middleName").value="";
		                window.opener.document.getElementById("landmark").value="";
		                window.opener.document.getElementById("groupType").value="";
					}
				custtypeAjax();
				window.close();
			}
		}


		function resultCoAppCopyCustomer(request){
			

			if ((request.readyState == 4) && (request.status == 200)) 
			{
				var str = request.responseText;

				var s1 = str.split("$:");
				if(window.opener.document.getElementById("copyAppCoapp")){
					window.opener.document.getElementById('copyAppCoapp').checked=false;
				}
				if(window.opener.document.getElementById("coAppcustomerType").value=trim(s1[7])=='C'){
							
					window.opener.document.getElementById("coAppindividualfield").style.display='none';
					window.opener.document.getElementById("coAppcorporatefield").style.display='';
					window.opener.document.getElementById("coAppcorporateconstitution").style.display='';
					window.opener.document.getElementById("coAppIndividualconstitution").style.display='none';
					window.opener.document.getElementById("coAppconstitution").style.display='none';
					window.opener.document.getElementById("coAppcustomerName").value=trim(s1[0]);
					window.opener.document.getElementById("coApphGroupId").value=trim(s1[1]);
					window.opener.document.getElementById("coAppregistrationNo").value=trim(s1[2]);
					window.opener.document.getElementById("coAppcustPan").value=trim(s1[3]);
					window.opener.document.getElementById("coAppbusinessSegment")
					window.opener.document.getElementById("coAppbusinessSegment").value=trim(s1[4]);
					window.opener.document.getElementById("coAppbusinessSegment1")
					window.opener.document.getElementById("coAppbusinessSegment1").value=trim(s1[4]);
					window.opener.document.getElementById("coApplbxIndustry").value=trim(s1[5]);
					window.opener.document.getElementById("coApplbxSubIndustry").value=trim(s1[6]);
					window.opener.document.getElementById("coAppcustomerType").value='C';
					window.opener.document.getElementById("coAppcustomerType1").removeAttribute("disabled");
					window.opener.document.getElementById("coAppcustomerType1").value='C';
					window.opener.document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
					window.opener.document.getElementById("coAppcorconstitution").value=trim(s1[8]);
					window.opener.document.getElementById("coAppaddress1").value=trim(s1[12]);
					window.opener.document.getElementById("coAppaddress2").value=trim(s1[13]);
					window.opener.document.getElementById("coAppaddresstype")
					window.opener.document.getElementById("coAppaddresstype").value=trim(s1[14]);
					if(window.opener.document.getElementById("coAppaddresstype1"))
					window.opener.document.getElementById("coAppaddresstype1").value=trim(s1[14]);
					window.opener.document.getElementById("coApppincode").value=trim(s1[16]);
					window.opener.document.getElementById("coApptxtCountryCode").value=trim(s1[17]);
					window.opener.document.getElementById("coApptxtStateCode").value=trim(s1[18]);
					window.opener.document.getElementById("coApptxtDistCode").value=trim(s1[19]);
					window.opener.document.getElementById("coAppemail").value=trim(s1[20]);
					window.opener.document.getElementById("coAppgroupName").value=trim(s1[21]);
					window.opener.document.getElementById("coAppcountry").value=trim(s1[22]);
					window.opener.document.getElementById("coAppdist").value=trim(s1[23]);
					window.opener.document.getElementById("coAppstate").value=trim(s1[24]);
					window.opener.document.getElementById("coAppindustry").value=trim(s1[25]);
					window.opener.document.getElementById("coAppsubIndustry").value=trim(s1[26]);
		            
		            window.opener.document.getElementById("coAppphoneOff").value=trim(s1[28]);
		            window.opener.document.getElementById("coAppaddress3").value=trim(s1[34]);
	                window.opener.document.getElementById("coAppmiddleName").value="";
	                window.opener.document.getElementById("coApplandmark").value=trim(s1[36]);
	                window.opener.document.getElementById("coAppgroupType").value=trim(s1[37]);
	                if(trim(s1[37])=='N'){
	                	window.opener.document.getElementById("coAppgroupText").style.display='';
	                	window.opener.document.getElementById("coAppgroupLov").style.display='none';
	                	window.opener.document.getElementById("coAppgroupName1").value=trim(s1[21]);
	                }else{
	                	window.opener.document.getElementById("coAppgroupText").style.display='none';
	                	window.opener.document.getElementById("coAppgroupLov").style.display='';
	                	window.opener.document.getElementById("coAppgroupName1").value="";
	                }
		            window.opener.document.getElementById("coAppfatherName").value="";
		            window.opener.document.getElementById("coAppaadhaar").value="";
		            window.opener.document.getElementById("coAppvoterId").value="";
		            window.opener.document.getElementById("coApppassport").value="";
					window.opener.document.getElementById("coAppfirstName").value="";
					window.opener.document.getElementById("coApplastName").value="";
					window.opener.document.getElementById("coAppcustDOB").value="";
					window.opener.document.getElementById("coAppcustPanInd").value="";

				}else
					if(window.opener.document.getElementById("coAppcustomerType").value=trim(s1[7])=='I'){
						window.opener.document.getElementById("coAppcorporatefield").style.display='none';
						window.opener.document.getElementById("coAppindividualfield").style.display='';
						window.opener.document.getElementById("coAppcorporateconstitution").style.display='none';
						window.opener.document.getElementById("coAppIndividualconstitution").style.display='';
						window.opener.document.getElementById("coAppconstitution").style.display='none';
						window.opener.document.getElementById("coAppcustPanInd").value=trim(s1[3]);
						window.opener.document.getElementById("coAppcustomerType").value='I';
		                window.opener.document.getElementById("coAppcustomerType1").removeAttribute("disabled");
						window.opener.document.getElementById("coAppcustomerType1").value='I';
						window.opener.document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
						window.opener.document.getElementById("coAppindconstitution").value=trim(s1[8]);
						window.opener.document.getElementById("coAppfirstName").value=trim(s1[9]);
						window.opener.document.getElementById("coApplastName").value=trim(s1[10]);
						window.opener.document.getElementById("coAppcustDOB").value=trim(s1[11]);
						window.opener.document.getElementById("coAppaddress1").value=trim(s1[12]);
						window.opener.document.getElementById("coAppaddress2").value=trim(s1[13]);
						window.opener.document.getElementById("coAppaddresstype")
						window.opener.document.getElementById("coAppaddresstype").value=trim(s1[14]);
						if(window.opener.document.getElementById("coAppaddresstype1"))
						window.opener.document.getElementById("coAppaddresstype1").value=trim(s1[14]);
						window.opener.document.getElementById("coApppincode").value=trim(s1[16]);
						window.opener.document.getElementById("coApptxtCountryCode").value=trim(s1[17]);
						window.opener.document.getElementById("coApptxtStateCode").value=trim(s1[18]);
						window.opener.document.getElementById("coApptxtDistCode").value=trim(s1[19]);
						window.opener.document.getElementById("coAppemail").value=trim(s1[20]);
						window.opener.document.getElementById("coAppcountry").value=trim(s1[22]);
						window.opener.document.getElementById("coAppdist").value=trim(s1[23]);
						window.opener.document.getElementById("coAppstate").value=trim(s1[24]);
		            
		                window.opener.document.getElementById("coAppphoneOff").value=trim(s1[28]);
		                window.opener.document.getElementById("coAppphoneOff").value=trim(s1[28]);
		                window.opener.document.getElementById("coAppfatherName").value=trim(s1[29]);
		                window.opener.document.getElementById("coAppaadhaar").value=trim(s1[30]);
		                window.opener.document.getElementById("coAppvoterId").value=trim(s1[31]);
		                window.opener.document.getElementById("coApppassport").value=trim(s1[32]);
		                window.opener.document.getElementById("coAppgenderIndividual").value=trim(s1[33]);
		                window.opener.document.getElementById("coAppaddress3").value=trim(s1[34]);
		                window.opener.document.getElementById("coAppmiddleName").value=trim(s1[35]);
		                window.opener.document.getElementById("coApplandmark").value=trim(s1[36]);
		                window.opener.document.getElementById("coAppgroupType").value="";
						window.opener.document.getElementById("coAppcustomerName").value="";
						if(window.opener.document.getElementById("coApphGroupId"))
						window.opener.document.getElementById("coApphGroupId").value="";
						window.opener.document.getElementById("coAppregistrationNo").value="";
					
						window.opener.document.getElementById("coAppbusinessSegment").value="";
						window.opener.document.getElementById("coApplbxIndustry").value="";
						window.opener.document.getElementById("coApplbxSubIndustry").value="";
						window.opener.document.getElementById("coAppgroupName").value="";
		                window.opener.document.getElementById("coAppcustPan").value="";
					}else{
						window.opener.document.getElementById("coAppcorporateconstitution").style.display='none';
						window.opener.document.getElementById("coAppIndividualconstitution").style.display='none';
						window.opener.document.getElementById("coAppcustomerType").value="";
						window.opener.document.getElementById("coAppconstitution").value="";
						window.opener.document.getElementById("coAppfirstName").value="";
						window.opener.document.getElementById("coApplastName").value="";
						window.opener.document.getElementById("coAppcustDOB").value="";
						window.opener.document.getElementById("coAppaddress1").value="";
						window.opener.document.getElementById("coAppaddress2").value="";
						window.opener.document.getElementById("coAppaddresstype").value="";
					
						window.opener.document.getElementById("coApppincode").value="";
						window.opener.document.getElementById("coApptxtCountryCode").value="";
						window.opener.document.getElementById("coApptxtStateCode").value="";
						window.opener.document.getElementById("coApptxtDistCode").value="";
						window.opener.document.getElementById("coAppemail").value="";
						window.opener.document.getElementById("coAppcountry").value="";
						window.opener.document.getElementById("coAppdist").value="";
						window.opener.document.getElementById("coAppstate").value="";
						window.opener.document.getElementById("coAppcustomerName").value="";
						window.opener.document.getElementById("coApphGroupId").value="";
						window.opener.document.getElementById("coAppregistrationNo").value="";
						window.opener.document.getElementById("coAppcustPan").value="";
		                window.opener.document.getElementById("coAppcustPanInd").value="";
						window.opener.document.getElementById("coAppbusinessSegment").value="";
						window.opener.document.getElementById("coApplbxIndustry").value="";
						window.opener.document.getElementById("coApplbxSubIndustry").value="";
						window.opener.document.getElementById("coAppgroupName").value="";
						window.opener.document.getElementById("coAppphoneOff").value="";
				        window.opener.document.getElementById("coAppfatherName").value="";
				            window.opener.document.getElementById("coAppaadhaar").value="";
				            window.opener.document.getElementById("coAppvoterId").value="";
				            window.opener.document.getElementById("coApppassport").value="";
				            window.opener.document.getElementById("coAppaddress3").value="";
			                window.opener.document.getElementById("coAppmiddleName").value="";
			                window.opener.document.getElementById("coApplandmark").value="";
			                window.opener.document.getElementById("coAppgroupType").value="";
						
					}
				coAppcusttypeAjax();
				window.close();
			}
		}

		function resultGaurCopyCustomer(request){
			

			if ((request.readyState == 4) && (request.status == 200)) 
			{
				var str = request.responseText;

				var s1 = str.split("$:");
				if(window.opener.document.getElementById("copyAppGaur")){
					window.opener.document.getElementById('copyAppGaur').checked=false;
				}
				if(window.opener.document.getElementById("gaurcustomerType").value=trim(s1[7])=='C'){
							
					window.opener.document.getElementById("gaurindividualfield").style.display='none';
					window.opener.document.getElementById("gaurcorporatefield").style.display='';
					window.opener.document.getElementById("gaurcorporateconstitution").style.display='';
					window.opener.document.getElementById("gaurIndividualconstitution").style.display='none';
					window.opener.document.getElementById("gaurconstitution").style.display='none';
					window.opener.document.getElementById("gaurcustomerName").value=trim(s1[0]);
					window.opener.document.getElementById("gaurhGroupId").value=trim(s1[1]);
					window.opener.document.getElementById("gaurregistrationNo").value=trim(s1[2]);
					window.opener.document.getElementById("gaurcustPan").value=trim(s1[3]);
					window.opener.document.getElementById("gaurbusinessSegment")
					window.opener.document.getElementById("gaurbusinessSegment").value=trim(s1[4]);
					window.opener.document.getElementById("gaurbusinessSegment1")
					window.opener.document.getElementById("gaurbusinessSegment1").value=trim(s1[4]);
					window.opener.document.getElementById("gaurlbxIndustry").value=trim(s1[5]);
					window.opener.document.getElementById("gaurlbxSubIndustry").value=trim(s1[6]);
					window.opener.document.getElementById("gaurcustomerType").value='C';
					window.opener.document.getElementById("gaurcustomerType1").removeAttribute("disabled");
					window.opener.document.getElementById("gaurcustomerType1").value='C';
					window.opener.document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
					window.opener.document.getElementById("gaurcorconstitution").value=trim(s1[8]);
					window.opener.document.getElementById("gauraddress1").value=trim(s1[12]);
					window.opener.document.getElementById("gauraddress2").value=trim(s1[13]);
					window.opener.document.getElementById("gauraddresstype")
					window.opener.document.getElementById("gauraddresstype").value=trim(s1[14]);
					if(window.opener.document.getElementById("gauraddresstype1"))
					window.opener.document.getElementById("gauraddresstype1").value=trim(s1[14]);
					window.opener.document.getElementById("gaurpincode").value=trim(s1[16]);
					window.opener.document.getElementById("gaurtxtCountryCode").value=trim(s1[17]);
					window.opener.document.getElementById("gaurtxtStateCode").value=trim(s1[18]);
					window.opener.document.getElementById("gaurtxtDistCode").value=trim(s1[19]);
					window.opener.document.getElementById("gauremail").value=trim(s1[20]);
					window.opener.document.getElementById("gaurgroupName").value=trim(s1[21]);
					window.opener.document.getElementById("gaurcountry").value=trim(s1[22]);
					window.opener.document.getElementById("gaurdist").value=trim(s1[23]);
					window.opener.document.getElementById("gaurstate").value=trim(s1[24]);
					window.opener.document.getElementById("gaurindustry").value=trim(s1[25]);
					window.opener.document.getElementById("gaursubIndustry").value=trim(s1[26]);
		            
		            window.opener.document.getElementById("gaurphoneOff").value=trim(s1[28]);
		            window.opener.document.getElementById("gauraddress3").value=trim(s1[34]);
	                window.opener.document.getElementById("gaurmiddleName").value="";
	                window.opener.document.getElementById("gaurlandmark").value=trim(s1[36]);
	                window.opener.document.getElementById("gaurgroupType").value=trim(s1[37]);
	                if(trim(s1[37])=='N'){
	                	window.opener.document.getElementById("gaurgroupText").style.display='';
	                	window.opener.document.getElementById("gaurgroupLov").style.display='none';
	                	window.opener.document.getElementById("gaurgroupName1").value=trim(s1[21]);
	                }else{
	                	window.opener.document.getElementById("gaurgroupText").style.display='none';
	                	window.opener.document.getElementById("gaurgroupLov").style.display='';
	                	window.opener.document.getElementById("gaurgroupName1").value="";
	                }
		            window.opener.document.getElementById("gaurfatherName").value="";
		            window.opener.document.getElementById("gauraadhaar").value="";
		            window.opener.document.getElementById("gaurvoterId").value="";
		            window.opener.document.getElementById("gaurpassport").value="";
					window.opener.document.getElementById("gaurfirstName").value="";
					window.opener.document.getElementById("gaurlastName").value="";
					window.opener.document.getElementById("gaurcustDOB").value="";
					window.opener.document.getElementById("gaurcustPanInd").value="";

				}else
					if(window.opener.document.getElementById("gaurcustomerType").value=trim(s1[7])=='I'){
						window.opener.document.getElementById("gaurcorporatefield").style.display='none';
						window.opener.document.getElementById("gaurindividualfield").style.display='';
						window.opener.document.getElementById("gaurcorporateconstitution").style.display='none';
						window.opener.document.getElementById("gaurIndividualconstitution").style.display='';
						window.opener.document.getElementById("gaurconstitution").style.display='none';
						window.opener.document.getElementById("gaurcustPanInd").value=trim(s1[3]);
						window.opener.document.getElementById("gaurcustomerType").value='I';
		                window.opener.document.getElementById("gaurcustomerType1").removeAttribute("disabled");
						window.opener.document.getElementById("gaurcustomerType1").value='I';
						window.opener.document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
						window.opener.document.getElementById("gaurindconstitution").value=trim(s1[8]);
						window.opener.document.getElementById("gaurfirstName").value=trim(s1[9]);
						window.opener.document.getElementById("gaurlastName").value=trim(s1[10]);
						window.opener.document.getElementById("gaurcustDOB").value=trim(s1[11]);
						window.opener.document.getElementById("gauraddress1").value=trim(s1[12]);
						window.opener.document.getElementById("gauraddress2").value=trim(s1[13]);
						window.opener.document.getElementById("gauraddresstype")
						window.opener.document.getElementById("gauraddresstype").value=trim(s1[14]);
						if(window.opener.document.getElementById("gauraddresstype1"))
						window.opener.document.getElementById("gauraddresstype1").value=trim(s1[14]);
						window.opener.document.getElementById("gaurpincode").value=trim(s1[16]);
						window.opener.document.getElementById("gaurtxtCountryCode").value=trim(s1[17]);
						window.opener.document.getElementById("gaurtxtStateCode").value=trim(s1[18]);
						window.opener.document.getElementById("gaurtxtDistCode").value=trim(s1[19]);
						window.opener.document.getElementById("gauremail").value=trim(s1[20]);
						window.opener.document.getElementById("gaurcountry").value=trim(s1[22]);
						window.opener.document.getElementById("gaurdist").value=trim(s1[23]);
						window.opener.document.getElementById("gaurstate").value=trim(s1[24]);
		            
		                window.opener.document.getElementById("gaurphoneOff").value=trim(s1[28]);
		                window.opener.document.getElementById("gaurfatherName").value=trim(s1[29]);
		                window.opener.document.getElementById("gauraadhaar").value=trim(s1[30]);
		                window.opener.document.getElementById("gaurvoterId").value=trim(s1[31]);
		                window.opener.document.getElementById("gaurpassport").value=trim(s1[32]);
		                window.opener.document.getElementById("gaurgenderIndividual").value=trim(s1[33]);
		                
		                window.opener.document.getElementById("gauraddress3").value=trim(s1[34]);
		                window.opener.document.getElementById("gaurmiddleName").value=trim(s1[35]);
		                window.opener.document.getElementById("gaurlandmark").value=trim(s1[36]);
		                window.opener.document.getElementById("gaurgroupType").value="";
						window.opener.document.getElementById("gaurcustomerName").value="";
						window.opener.document.getElementById("gaurhGroupId").value="";
						window.opener.document.getElementById("gaurregistrationNo").value="";
					
						window.opener.document.getElementById("gaurbusinessSegment").value="";
						window.opener.document.getElementById("gaurlbxIndustry").value="";
						window.opener.document.getElementById("gaurlbxSubIndustry").value="";
						window.opener.document.getElementById("gaurgroupName").value="";
		                window.opener.document.getElementById("gaurcustPan").value="";
					}else{
						window.opener.document.getElementById("gaurcorporateconstitution").style.display='none';
						window.opener.document.getElementById("gaurIndividualconstitution").style.display='none';
						window.opener.document.getElementById("gaurcustomerType").value="";
						window.opener.document.getElementById("gaurconstitution").value="";
						window.opener.document.getElementById("gaurfirstName").value="";
						window.opener.document.getElementById("gaurlastName").value="";
						window.opener.document.getElementById("gaurcustDOB").value="";
						window.opener.document.getElementById("gauraddress1").value="";
						window.opener.document.getElementById("gauraddress2").value="";
						window.opener.document.getElementById("gauraddresstype").value="";
					
						window.opener.document.getElementById("gaurpincode").value="";
						window.opener.document.getElementById("gaurtxtCountryCode").value="";
						window.opener.document.getElementById("gaurtxtStateCode").value="";
						window.opener.document.getElementById("gaurtxtDistCode").value="";
						window.opener.document.getElementById("gauremail").value="";
						window.opener.document.getElementById("gaurcountry").value="";
						window.opener.document.getElementById("gaurdist").value="";
						window.opener.document.getElementById("gaurstate").value="";
						window.opener.document.getElementById("gaurcustomerName").value="";
						window.opener.document.getElementById("gaurhGroupId").value="";
						window.opener.document.getElementById("gaurregistrationNo").value="";
						window.opener.document.getElementById("gaurcustPan").value="";
		                window.opener.document.getElementById("gaurcustPanInd").value="";
						window.opener.document.getElementById("gaurbusinessSegment").value="";
						window.opener.document.getElementById("gaurlbxIndustry").value="";
						window.opener.document.getElementById("gaurlbxSubIndustry").value="";
						window.opener.document.getElementById("gaurgroupName").value="";
						  window.opener.document.getElementById("gaurfatherName").value="";
				            window.opener.document.getElementById("gauraadhaar").value="";
				            window.opener.document.getElementById("gaurvoterId").value="";
				            window.opener.document.getElementById("gaurpassport").value="";
				            window.opener.document.getElementById("gauraddress3").value="";
			                window.opener.document.getElementById("gaurmiddleName").value="";
			                window.opener.document.getElementById("gaurlandmark").value="";
			                window.opener.document.getElementById("gaurgroupType").value="";
						
					}
				gaurcusttypeAjax();
				window.close();
			}
		}

		//Rohit changes  end
				function clearStateLovChildNew(){		
					window.opener.document.getElementById("dist").value="";
				 	window.opener.document.getElementById("txtDistCode").value="";
				 	window.opener.document.getElementById("tahsil").value="";	 	
				 	window.close();	
				}
				 //amandeep ends

				function getCountryStateDistrictTahsilValue(){
					 var lbxPincode=window.opener.document.getElementById('lbxPincode').value;
					 var txnTahsilHID=window.opener.document.getElementById('txnTahsilHID').value;
					 window.opener.document.getElementById('tahsil4').value ="";
					 if( window.opener.document.getElementById('tahsil'))
						 window.opener.document.getElementById('tahsil').value ="";
					 window.opener.document.getElementById('txtDistCode').value="";
					 window.opener.document.getElementById('dist4').value="";
					 window.opener.document.getElementById('txtStateCode').value="";
					 window.opener.document.getElementById('state6').value="";
					 if(txnTahsilHID=='DUMMY')
						 {
						 window.opener.document.getElementById("lov1").style.display="none";
						 window.opener.document.getElementById("lov2").style.display="none";
						 window.opener.document.getElementById("lov3").style.display="";
						 window.opener.document.getElementById("lov4").style.display="";
						 window.opener.document.getElementById("lov5").style.display="none";
						 window.opener.document.getElementById("lov6").style.display="";
						 window.opener.document.getElementById('txnTahsilHID').value=txnTahsilHID;
						
						 }
					 
					 else
						 {
						 
						 window.opener.document.getElementById("lov1").style.display="";
						 window.opener.document.getElementById("lov2").style.display="";
						 window.opener.document.getElementById("lov3").style.display="none";
						 window.opener.document.getElementById("lov4").style.display="none";
						 window.opener.document.getElementById("lov5").style.display="";
						 window.opener.document.getElementById("lov6").style.display="none";
						 
						 
						 var basePath=window.opener.document.getElementById("contextPath").value;
						 var address = basePath+"/ajaxActionForCP.do?method=getCountryStateDistrictTahsilValue";
						 var data = "lbxPincode="+lbxPincode;
						 sendRequestCountryStateDistrictTahsilValue(address, data);
					     return true;
						 }
					 window.close();
				}
				 function sendRequestCountryStateDistrictTahsilValue(address, data) {
						var request = getRequestObject();
						request.onreadystatechange = function () {
							resultMethodCountryStateDistrictTahsilValue(request);
						};
						request.open("POST", address, true);
						request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
						request.send(data);
					}
				function resultMethodCountryStateDistrictTahsilValue(request){

					if ((request.readyState == 4) && (request.status == 200)) {
						var str = request.responseText;
						var s1 = str.split("$:");
						window.opener.document.getElementById('txnTahsilHID').value=trim(s1[1]);
						window.opener.document.getElementById('tahsil').value=trim(s1[2]);
						window.opener.document.getElementById('tahsilDesc').value=trim(s1[2]);
						window.opener.document.getElementById('txtDistCode').value=trim(s1[3]);
						window.opener.document.getElementById('dist').value=trim(s1[4]);
						window.opener.document.getElementById('txtStateCode').value=trim(s1[5]);
						window.opener.document.getElementById('state').value=trim(s1[6]);
						window.opener.document.getElementById('txtCountryCode').value=trim(s1[7]);
						window.opener.document.getElementById('country').value=trim(s1[8]);
						
						 window.close();
						}
				}
					function getPreDealDefaultLoanDetail()
					{
						  var scheme =window.opener.document.getElementById('schemeId').value;
						
						  var contextPath =window.opener.document.getElementById('contextPath').value ;
						  if (scheme!= '')
						  {
							var address = contextPath+"/relationalManagerAjaxAction.do?method=getDefaultLoanDetail";
							var data = "scheme=" + scheme;
							send_BaseRate_PreDeal(address, data);
							return true;
						  }
						  else
						  {
					    	 alert("Please Select List");	
					    	 return false;
						  }
					}
					function send_BaseRate_PreDeal(address, data) 
					{
						
						var request = getRequestObject();
						request.onreadystatechange = function () {
							result_BaseRate_preDeal(request);
						};
						request.open("POST", address, true);
						request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
						request.send(data);
					}
					
					function result_BaseRate_preDeal(request)
					{    
						if ((request.readyState == 4) && (request.status == 200)) 
						{
							var str = request.responseText;
							var s1 = str.split("$:");
							if(s1.length>0)
						    {
								window.opener.document.getElementById('minTenure').value = trim(s1[16]);
								window.opener.document.getElementById('maxTenure').value = trim(s1[17]);
								window.opener.document.getElementById('loanTenure').value=trim(s1[7]);
							}
								
							window.close();
								
								}
					}
						//Pooja code for pincode
						function getCoAppCountryStateDistrictTahsilValue(){
							 var coApplbxPincode=window.opener.document.getElementById('coApplbxPincode').value;
							 var coApptxnTahsilHID=window.opener.document.getElementById('coApptxnTahsilHID').value;
							 window.opener.document.getElementById('coApptahsil4').value ="";
							 if( window.opener.document.getElementById('coApptahsil'))
								 window.opener.document.getElementById('coApptahsil').value ="";
							 window.opener.document.getElementById('coApptxtDistCode').value="";
							 window.opener.document.getElementById('coAppdist4').value="";
							 window.opener.document.getElementById('coApptxtStateCode').value="";
							 window.opener.document.getElementById('coAppstate6').value="";
							 if(coApptxnTahsilHID=='DUMMY')
								 {
								 window.opener.document.getElementById("coApplov1").style.display="none";
								 window.opener.document.getElementById("coApplov2").style.display="none";
								 window.opener.document.getElementById("coApplov3").style.display="";
								 window.opener.document.getElementById("coApplov4").style.display="";
								 window.opener.document.getElementById("coApplov5").style.display="none";
								 window.opener.document.getElementById("coApplov6").style.display="";
								 window.opener.document.getElementById('coApptxnTahsilHID').value=coApptxnTahsilHID;
								
								 }
							 
							 else
								 {
								 
								 window.opener.document.getElementById("coApplov1").style.display="";
								 window.opener.document.getElementById("coApplov2").style.display="";
								 window.opener.document.getElementById("coApplov3").style.display="none";
								 window.opener.document.getElementById("coApplov4").style.display="none";
								 window.opener.document.getElementById("coApplov5").style.display="";
								 window.opener.document.getElementById("coApplov6").style.display="none";
								 
								 
								 var basePath=window.opener.document.getElementById("contextPath").value;
								 var address = basePath+"/ajaxActionForCP.do?method=getCoAppCountryStateDistrictTahsilValue";
								 var data = "coApplbxPincode="+coApplbxPincode;
								 sendRequestCoAppCountryStateDistrictTahsilValue(address, data);
							     return true;
								 }
							 window.close();
						}
						 function sendRequestCoAppCountryStateDistrictTahsilValue(address, data) {
								var request = getRequestObject();
								request.onreadystatechange = function () {
									resultMethodCoAppCountryStateDistrictTahsilValue(request);
								};
								request.open("POST", address, true);
								request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
								request.send(data);
							}
						function resultMethodCoAppCountryStateDistrictTahsilValue(request){
							if ((request.readyState == 4) && (request.status == 200)) {
								var str = request.responseText;
								var s1 = str.split("$:");
								window.opener.document.getElementById('coApptxnTahsilHID').value=trim(s1[1]);
								window.opener.document.getElementById('coApptahsil').value=trim(s1[2]);
								window.opener.document.getElementById('coApptahsilDesc').value=trim(s1[2]);
								window.opener.document.getElementById('coApptxtDistCode').value=trim(s1[3]);
								window.opener.document.getElementById('coAppdist').value=trim(s1[4]);
								window.opener.document.getElementById('coApptxtStateCode').value=trim(s1[5]);
								window.opener.document.getElementById('coAppstate').value=trim(s1[6]);
								window.opener.document.getElementById('coApptxtCountryCode').value=trim(s1[7]);
								window.opener.document.getElementById('coAppcountry').value=trim(s1[8]);
								 window.close();
								 //alert("coApptxnTahsilHID:"+coApptxnTahsilHID+",coApptahsil:"+coApptahsil+",coApptahsilDesc:"+coApptahsilDesc+",coApptxtDistCode:"+coApptxtDistCode+",coAppdist:"+coAppdist",coApptxtStateCode"+coApptxtStateCode);
								}

							}
						//Pooja code for pincode
						function getGaurCountryStateDistrictTahsilValue(){
							 var gaurlbxPincode=window.opener.document.getElementById('gaurlbxPincode').value;
							 var coApptxnTahsilHID=window.opener.document.getElementById('gaurtxnTahsilHID').value;
							 window.opener.document.getElementById('gaurtahsil4').value ="";
							 if( window.opener.document.getElementById('gaurtahsil'))
								 window.opener.document.getElementById('gaurtahsil').value ="";
							 window.opener.document.getElementById('gaurtxtDistCode').value="";
							 window.opener.document.getElementById('gaurdist4').value="";
							 window.opener.document.getElementById('gaurtxtStateCode').value="";
							 window.opener.document.getElementById('gaurstate6').value="";
							 if(coApptxnTahsilHID=='DUMMY')
								 {
								 window.opener.document.getElementById("gaurlov1").style.display="none";
								 window.opener.document.getElementById("gaurlov2").style.display="none";
								 window.opener.document.getElementById("gaurlov3").style.display="";
								 window.opener.document.getElementById("gaurlov4").style.display="";
								 window.opener.document.getElementById("gaurlov5").style.display="none";
								 window.opener.document.getElementById("gaurlov6").style.display="";
								 window.opener.document.getElementById('gaurtxnTahsilHID').value=gaurtxnTahsilHID;
								
								 }
							 
							 else
								 {
								 
								 window.opener.document.getElementById("gaurlov1").style.display="";
								 window.opener.document.getElementById("gaurlov2").style.display="";
								 window.opener.document.getElementById("gaurlov3").style.display="none";
								 window.opener.document.getElementById("gaurlov4").style.display="none";
								 window.opener.document.getElementById("gaurlov5").style.display="";
								 window.opener.document.getElementById("gaurlov6").style.display="none";
								 
								 
								 var basePath=window.opener.document.getElementById("contextPath").value;
								 var address = basePath+"/ajaxActionForCP.do?method=getGaurCountryStateDistrictTahsilValue";
								 var data = "gaurlbxPincode="+gaurlbxPincode;
								 sendRequestGaurCountryStateDistrictTahsilValue(address, data);
							     return true;
								 }
							 window.close();
						}
						 function sendRequestGaurCountryStateDistrictTahsilValue(address, data) {
								var request = getRequestObject();
								request.onreadystatechange = function () {
									resultMethodGaurCountryStateDistrictTahsilValue(request);
								};
								request.open("POST", address, true);
								request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
								request.send(data);
							}
						function resultMethodGaurCountryStateDistrictTahsilValue(request){
							if ((request.readyState == 4) && (request.status == 200)) {
								var str = request.responseText;
								var s1 = str.split("$:");
								window.opener.document.getElementById('gaurtxnTahsilHID').value=trim(s1[1]);
								window.opener.document.getElementById('gaurtahsil').value=trim(s1[2]);
								window.opener.document.getElementById('gaurtahsilDesc').value=trim(s1[2]);
								window.opener.document.getElementById('gaurtxtDistCode').value=trim(s1[3]);
								window.opener.document.getElementById('gaurdist').value=trim(s1[4]);
								window.opener.document.getElementById('gaurtxtStateCode').value=trim(s1[5]);
								window.opener.document.getElementById('gaurstate').value=trim(s1[6]);
								window.opener.document.getElementById('gaurtxtCountryCode').value=trim(s1[7]);
								window.opener.document.getElementById('gaurcountry').value=trim(s1[8]);
								 window.close();
								 //alert("coApptxnTahsilHID:"+coApptxnTahsilHID+",coApptahsil:"+coApptahsil+",coApptahsilDesc:"+coApptahsilDesc+",coApptxtDistCode:"+coApptxtDistCode+",coAppdist:"+coAppdist",coApptxtStateCode"+coApptxtStateCode);
								}

							}
function lovCoAppIndustryLead()
{
var industry=window.opener.document.getElementById('coAppindustry').value;
														
if(industry!='')
{
window.opener.document.getElementById('coApplbxSubIndustry').value='';
window.opener.document.getElementById('coAppsubIndustry').value='';
}
														
window.close();
}
function lovGaurIndustryLead()
{
var industry=window.opener.document.getElementById('gaurindustry').value;
														
if(industry!='')
{
window.opener.document.getElementById('gaurlbxSubIndustry').value='';
window.opener.document.getElementById('gaursubIndustry').value='';
}
														
window.close();
}
function custtypeAjax(){
	var customerType=window.opener.document.getElementById("customerType").value;
	var lbxCustomerId=window.opener.document.getElementById("lbxCustomerId").value;
	var relationship=window.opener.document.getElementById("relationship").value;


		if(customerType=='C'){
			window.opener.document.getElementById("constitution").style.display='none';
			window.opener.document.getElementById("individualfield").style.display='none';
			window.opener.document.getElementById("corporatefield").style.display='';
			window.opener.document.getElementById("Individualconstitution").style.display='none';
			window.opener.document.getElementById("corporateconstitution").style.display='';
			window.opener.document.getElementById("firstName").value="";
			window.opener.document.getElementById("lastName").value="";
			window.opener.document.getElementById("middleName").value="";
			window.opener.document.getElementById("custDOB").value="";
			window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("customerName").setAttribute("readonly","true");
			window.opener.document.getElementById("groupType").setAttribute("readonly","true");
			window.opener.document.getElementById("groupName1").setAttribute("readonly","true");
			window.opener.document.getElementById("registrationNo").setAttribute("readonly","true");
			window.opener.document.getElementById("businessSegment1").setAttribute("disabled","true");
			window.opener.document.getElementById("industry").setAttribute("readonly","true");
			window.opener.document.getElementById("subIndustry").setAttribute("readonly","true");
			window.opener.document.getElementById("custPan").setAttribute("readonly","true");
			window.opener.document.getElementById("industryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("subIndustryButton").setAttribute("disabled","true");
			
			
			
		}else{
			
			window.opener.document.getElementById("constitution").style.display='none';
			window.opener.document.getElementById("corporatefield").style.display='none';
			window.opener.document.getElementById("individualfield").style.display='';
			window.opener.document.getElementById("Individualconstitution").style.display='';
			window.opener.document.getElementById("corporateconstitution").style.display='none';
			window.opener.document.getElementById("registrationNo").value="";
			window.opener.document.getElementById("businessSegment").value="";
			window.opener.document.getElementById("industry").value="";
			window.opener.document.getElementById("subIndustry").value="";
			window.opener.document.getElementById("lbxIndustry").value="";
			window.opener.document.getElementById("lbxSubIndustry").value="";
		
			
					
			window.opener.document.getElementById("customerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("firstName").setAttribute("readonly","true");
			window.opener.document.getElementById("middleName").setAttribute("readonly","true");
			window.opener.document.getElementById("lastName").setAttribute("readonly","true");
			window.opener.document.getElementById("custDOB").setAttribute("readonly","true");
			window.opener.document.getElementById("fatherName").setAttribute("readonly","true");
			
			window.opener.document.getElementById("aadhaar").setAttribute("readonly","true");
			window.opener.document.getElementById("passport").setAttribute("readonly","true");
			window.opener.document.getElementById("dlNumber").setAttribute("readonly","true");
			window.opener.document.getElementById("voterId").setAttribute("readonly","true");
			window.opener.document.getElementById("custPanInd").setAttribute("readonly","true");

		}
		
		window.opener.document.getElementById("addresstype1").setAttribute("disabled","true");
		window.opener.document.getElementById("address1").setAttribute("readonly","true");
		window.opener.document.getElementById("address2").setAttribute("readonly","true");
		window.opener.document.getElementById("address3").setAttribute("readonly","true");
		window.opener.document.getElementById("country").setAttribute("readonly","true");
		window.opener.document.getElementById("state").setAttribute("readonly","true");
		window.opener.document.getElementById("dist").setAttribute("readonly","true");
		window.opener.document.getElementById("tahsilDesc").setAttribute("readonly","true");
		window.opener.document.getElementById("pincode").setAttribute("readonly","true");
		window.opener.document.getElementById("phoneOff").setAttribute("readonly","true");
		window.opener.document.getElementById("email").setAttribute("readonly","true");
		window.opener.document.getElementById("landmark").setAttribute("readonly","true");
		if(window.opener.document.getElementById("countryButton")){
			window.opener.document.getElementById("countryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("stateButton").setAttribute("disabled","true");
			window.opener.document.getElementById("distButton").setAttribute("disabled","true");
			window.opener.document.getElementById("tahsilButton").setAttribute("disabled","true");
		}
		
	
}
function coAppcusttypeAjax(){
	var customerType=window.opener.document.getElementById("coAppcustomerType").value;
	var lbxCustomerId=window.opener.document.getElementById("coApplbxCustomerId").value;
	var relationship=window.opener.document.getElementById("coApprelationship").value;
	window.opener.document.getElementById("copyAppCoapp").setAttribute("disabled","true");

		if(customerType=='C'){
			window.opener.document.getElementById("coAppconstitution").style.display='none';
			window.opener.document.getElementById("coAppindividualfield").style.display='none';
			window.opener.document.getElementById("coAppcorporatefield").style.display='';
			window.opener.document.getElementById("coAppIndividualconstitution").style.display='none';
			window.opener.document.getElementById("coAppcorporateconstitution").style.display='';
			window.opener.document.getElementById("coAppfirstName").value="";
			window.opener.document.getElementById("coApplastName").value="";
			window.opener.document.getElementById("coAppmiddleName").value="";
			window.opener.document.getElementById("coAppcustDOB").value="";
			window.opener.document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("coAppcustomerName").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppgroupType").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppgroupName1").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppregistrationNo").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppbusinessSegment1").setAttribute("disabled","true");
			window.opener.document.getElementById("coAppindustry").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppsubIndustry").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppcustPan").setAttribute("readonly","true");
			window.opener.document.getElementById("industryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("subIndustryButton").setAttribute("disabled","true");
			
			
		}else{
			
			window.opener.document.getElementById("coAppconstitution").style.display='none';
			window.opener.document.getElementById("coAppcorporatefield").style.display='none';
			window.opener.document.getElementById("coAppindividualfield").style.display='';
			window.opener.document.getElementById("coAppIndividualconstitution").style.display='';
			window.opener.document.getElementById("coAppcorporateconstitution").style.display='none';
			window.opener.document.getElementById("coAppregistrationNo").value="";
			window.opener.document.getElementById("coAppbusinessSegment").value="";
			window.opener.document.getElementById("coAppindustry").value="";
			window.opener.document.getElementById("coAppsubIndustry").value="";
			window.opener.document.getElementById("coApplbxIndustry").value="";
			window.opener.document.getElementById("coApplbxSubIndustry").value="";
		
			
					
			window.opener.document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("coAppfirstName").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppmiddleName").setAttribute("readonly","true");
			window.opener.document.getElementById("coApplastName").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppcustDOB").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppfatherName").setAttribute("readonly","true");
			
			window.opener.document.getElementById("coAppaadhaar").setAttribute("readonly","true");
			window.opener.document.getElementById("coApppassport").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppdlNumber").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppvoterId").setAttribute("readonly","true");
			window.opener.document.getElementById("coAppcustPanInd").setAttribute("readonly","true");

		}
		
		window.opener.document.getElementById("coAppaddresstype1").setAttribute("disabled","true");
		window.opener.document.getElementById("coAppaddress1").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppaddress2").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppaddress3").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppcountry").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppstate").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppdist").setAttribute("readonly","true");
		window.opener.document.getElementById("coApptahsilDesc").setAttribute("readonly","true");
		window.opener.document.getElementById("coApppincode").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppphoneOff").setAttribute("readonly","true");
		window.opener.document.getElementById("coAppemail").setAttribute("readonly","true");
		window.opener.document.getElementById("coApplandmark").setAttribute("readonly","true");
		if(window.opener.document.getElementById("countryButton")){
			window.opener.document.getElementById("countryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("stateButton").setAttribute("disabled","true");
			window.opener.document.getElementById("distButton").setAttribute("disabled","true");
			window.opener.document.getElementById("tahsilButton").setAttribute("disabled","true");
		}
		
	
}
function gaurcusttypeAjax(){
	var customerType=window.opener.document.getElementById("gaurcustomerType").value;
	var lbxCustomerId=window.opener.document.getElementById("gaurlbxCustomerId").value;
	var relationship=window.opener.document.getElementById("gaurrelationship").value;
	window.opener.document.getElementById("copyAppGaur").setAttribute("disabled","true");

		if(customerType=='C'){
			window.opener.document.getElementById("gaurconstitution").style.display='none';
			window.opener.document.getElementById("gaurindividualfield").style.display='none';
			window.opener.document.getElementById("gaurcorporatefield").style.display='';
			window.opener.document.getElementById("gaurIndividualconstitution").style.display='none';
			window.opener.document.getElementById("gaurcorporateconstitution").style.display='';
			window.opener.document.getElementById("gaurfirstName").value="";
			window.opener.document.getElementById("gaurlastName").value="";
			window.opener.document.getElementById("gaurmiddleName").value="";
			window.opener.document.getElementById("gaurcustDOB").value="";
			window.opener.document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("gaurcustomerName").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurgroupType").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurgroupName1").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurregistrationNo").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurbusinessSegment1").setAttribute("disabled","true");
			window.opener.document.getElementById("gaurindustry").setAttribute("readonly","true");
			window.opener.document.getElementById("gaursubIndustry").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurcustPan").setAttribute("readonly","true");
			window.opener.document.getElementById("industryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("subIndustryButton").setAttribute("disabled","true");
			
			
			
		}else{
			
			window.opener.document.getElementById("gaurconstitution").style.display='none';
			window.opener.document.getElementById("gaurcorporatefield").style.display='none';
			window.opener.document.getElementById("gaurindividualfield").style.display='';
			window.opener.document.getElementById("gaurIndividualconstitution").style.display='';
			window.opener.document.getElementById("gaurcorporateconstitution").style.display='none';
			window.opener.document.getElementById("gaurregistrationNo").value="";
			window.opener.document.getElementById("gaurbusinessSegment").value="";
			window.opener.document.getElementById("gaurindustry").value="";
			window.opener.document.getElementById("gaursubIndustry").value="";
			window.opener.document.getElementById("gaurlbxIndustry").value="";
			window.opener.document.getElementById("gaurlbxSubIndustry").value="";
		
			
					
			window.opener.document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			window.opener.document.getElementById("gaurfirstName").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurmiddleName").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurlastName").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurcustDOB").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurfatherName").setAttribute("readonly","true");
			
			window.opener.document.getElementById("gauraadhaar").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurpassport").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurdlNumber").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurvoterId").setAttribute("readonly","true");
			window.opener.document.getElementById("gaurcustPanInd").setAttribute("readonly","true");

		}
		
		window.opener.document.getElementById("gauraddresstype1").setAttribute("disabled","true");
		window.opener.document.getElementById("gauraddress1").setAttribute("readonly","true");
		window.opener.document.getElementById("gauraddress2").setAttribute("readonly","true");
		window.opener.document.getElementById("gauraddress3").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurcountry").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurstate").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurdist").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurtahsilDesc").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurpincode").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurphoneOff").setAttribute("readonly","true");
		window.opener.document.getElementById("gauremail").setAttribute("readonly","true");
		window.opener.document.getElementById("gaurlandmark").setAttribute("readonly","true");
		if(window.opener.document.getElementById("countryButton")){
			window.opener.document.getElementById("countryButton").setAttribute("disabled","true");
			window.opener.document.getElementById("stateButton").setAttribute("disabled","true");
			window.opener.document.getElementById("distButton").setAttribute("disabled","true");
			window.opener.document.getElementById("tahsilButton").setAttribute("disabled","true");
		}
		
	
}						