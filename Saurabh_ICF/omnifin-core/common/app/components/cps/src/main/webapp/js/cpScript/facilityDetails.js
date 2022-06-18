function fnSaveFacilityDetails()
{
//	   var specialCondition=document.getElementById("specialCondition").value;
	   DisButClass.prototype.DisButMethod();
	  if (validateFacilityDetailsSave()){
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("facilityDetailsForm").action=sourcepath+"/facilityDetails.do?method=saveFacilityDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("facilityDetailsForm").submit();
		return true;
	  }
	  else {
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
}
function fnUpdateFacilityDetails(){
//	   var specialCondition=document.getElementById("specialCondition").value;
	   DisButClass.prototype.DisButMethod();
	   var dealLoanId=document.getElementById("dealLoanId").value;
	   var updateFlag="Y";
	  // alert(dealLoanId);
	  if (validateFacilityDetailsSave()){
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("facilityDetailsForm").action=sourcepath+"/facilityDetails.do?method=saveFacilityDetails&dealLoanId="+dealLoanId+"&updateFlag="+updateFlag;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("facilityDetailsForm").submit();
		return true;
	  }
	  else {
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
}
function validateFacilityDetailsSave()
{
	//Sanjay Starts for Alert for Final Rate
	var rate = document.getElementById("interestRateType").value;
	var finalRate =document.getElementById("finalRate").value;
	var minFlatRate =document.getElementById("minFlatRate").value;
	var maxFlatRate =document.getElementById("maxFlatRate").value;
	var minEffectiveRate =document.getElementById("minEffectiveRate").value;
	var maxEffectiveRate =document.getElementById("maxEffectiveRate").value;   
	var effRate=0.00;
	var minFlatR=0.00;
	var maxFlatR=0.00;
	var minEffectiveR=0.00;
	var maxEffectiveR=0.00;
		if(finalRate!='')
		{
			effRate=removeComma(finalRate);
		}
		if(minFlatRate!='')
		{
			minFlatR=removeComma(minFlatRate);
		}
		if(maxFlatRate!='')
		{
			maxFlatR=removeComma(maxFlatRate);
		}
		if(minEffectiveRate!='')
		{
			minEffectiveR=removeComma(minEffectiveRate);
		}
		if(maxEffectiveRate!='')
		{
			maxEffectiveR=removeComma(maxEffectiveRate);
		}
		if(finalRate==""||parseFloat(finalRate)==0)
			{
			alert("Please Insert Final Rate");
			    document.getElementById("finalRate").value = 0;
				DisButClass.prototype.EnbButMethod();
				return false;
			}else{
		if(rate=='F'){
			if(parseFloat(minFlatR)!=0 && parseFloat(maxFlatR)!=0){
				if((parseFloat(effRate) < parseFloat(minFlatR)) || (parseFloat(effRate) > parseFloat(maxFlatR))) {
					alert("Please Insert Final Rate between " + minFlatR + " and " + maxFlatR);
					document.getElementById("finalRate").value = 0;
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
		}
		else
		{
			if(parseFloat(minEffectiveR)!=0 && parseFloat(maxEffectiveR)!=0) {
				if ((parseFloat(effRate) < parseFloat(minEffectiveR)) || (parseFloat(effRate) > parseFloat(maxEffectiveR))) {
					alert("Please Insert Final Rate between " + minEffectiveR + " and " + maxEffectiveR);
					document.getElementById("finalRate").value = 0;
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
		}
			}
		//Sanjay ends...
	
	var msgMandatory="";
	
	if (document.getElementById("productDesc").value == ""){
		msgMandatory += "* Product is Required" + "\n"; 
	}
	if (document.getElementById("schemeDesc").value == ""){
		msgMandatory += "* Scheme is Required" + "\n"; 
	}
	if (document.getElementById("loanAmount").value == ""){
		msgMandatory += "* Loan Amount is Required" + "\n"; 
	}
	if (document.getElementById("txtTenure").value == ""){
		msgMandatory += "* Tenure is Required" + "\n"; 
	}
	/*if (document.getElementById("insurancePremium").value == "" || document.getElementById("insurancePremium").value == "0"){
		msgMandatory += "* Insurance Premium is Required" + "\n"; 
	}*/
	if (msgMandatory!="")
	{
		alert(msgMandatory);
		return false;
	}
	return true;
}

function fnDeleteFacilityDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById("contextPath").value;
	var checkedDealLoanId = 0;
	checkedDealLoanId = document.getElementsByName("chk");
	
	if(parseInt(checkedDealLoanId.length)<=1){		
		alert("You can not delete product details.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if(check())
	 { 
		if(confirm("Are You Sure to Delete Facility Detail(s)?"))
		{
			document.getElementById("facilityDetailsForm").action=contextPath+"/facilityDetailsDispatch.do?method=deleteFacilityDetails";
			document.getElementById("processingImage").style.display = '';
	  		document.getElementById("facilityDetailsForm").submit();
	  		return true;
		}
		else
		{
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	  }
	   else
	   {
	   	alert("Please Select atleast one!!!");
	   	DisButClass.prototype.EnbButMethod();
	    return false;
	   }
}

function fnGetCharges(){
	
	var checkedDealLoanId = document.getElementsByName("chk");
	var dealLoanId = 0;
	for(i=0;i<checkedDealLoanId.length;i++)	{		
		if(checkedDealLoanId[i].checked==false)	{
			flag=0;
		}
		else{
			flag=1;
			dealLoanId=checkedDealLoanId[i].value;
			break;
		}		
	}
	if(dealLoanId<=0){
		
		alert("Please select product");	    
		return false;
	}
	    var contextPath = document.getElementById("contextPath").value;	  
		var url=contextPath+"/chargeBehindAction.do?method=chargeInDeal&dealLoanId="+dealLoanId+"&facilityFlag=Y";
		mywindowCharges =window.open(url,'Charges','height=400,width=1000,top=200,left=250, scrollbars=1').focus();
		mywindowCharges.moveTo(800,300);
		if (window.focus) {
			mywindowCharges.focus();
			return false;
		}
		return true;	
}

function fnGetInstallmentPlan(){
	
	var checkedDealLoanId = document.getElementsByName("chk");
	var dealLoanId = 0;
	for(i=0;i<checkedDealLoanId.length;i++)	{		
		if(checkedDealLoanId[i].checked==false)	{
			flag=0;
		}
		else{
			flag=1;
			dealLoanId=checkedDealLoanId[i].value;
			break;
		}		
	}	
   if(dealLoanId<=0){
		
		alert("Please select product");	    
		return false;
	}
	 
	    var contextPath = document.getElementById("contextPath").value;	  
		var url=contextPath+"/installmentPlanBehindAction.do?dealLoanId="+dealLoanId+"&facilityFlag=Y";
		mywindowInstallmentPlan =window.open(url,'InstallmentPlan','height=400,width=1000,top=200,left=250,scrollbars=1').focus();
		mywindowInstallmentPlan.moveTo(800,300);
		if (window.focus) {
			mywindowInstallmentPlan.focus();
			return false;
		}
		return true;	
}

function fnGetRepaymentSchedule(){
	
	var checkedDealLoanId = document.getElementsByName("chk");
	var dealLoanId = 0;
	for(i=0;i<checkedDealLoanId.length;i++)	{		
		if(checkedDealLoanId[i].checked==false)	{
			flag=0;
		}
		else{
			flag=1;
			dealLoanId=checkedDealLoanId[i].value;
			break;
		}		
	}	
	
   if(dealLoanId<=0){
		
		alert("Please select product");	    
		return false;
	}	    
	    var contextPath = document.getElementById("contextPath").value;	  
		var url=contextPath+"/repaymentScheduleBehindAction.do?dealLoanId="+dealLoanId+"&facilityFlag=Y";
		mywindowRepaymentSchedule =window.open(url,'RepaymentSchedule','height=400,width=1000,top=200,left=250,scrollbars=1').focus();
		mywindowRepaymentSchedule.moveTo(800,300);
		if (window.focus) {
			mywindowRepaymentSchedule.focus();
			return false;
		}
		return true;	
}

function fnCalculateInstNo(){


	var frequency= document.getElementById("installmentFrequency").value;
	var requestedLoanTenure= document.getElementById("txtTenure").value;
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
	calInsat=parseTenure/parsedFreq;
	//alert("calInsat:---"+calInsat);
	if(isInt(calInsat))
	{
		document.getElementById("noOfInstl").value=calInsat;
	}
	else
	{
		alert("The Combination of tenure and frequency are incorrect");
		document.getElementById("noOfInstl").value='';
	}

}

function fnValidateRateMethod()
{
	var intRateNethod = document.getElementById("interestRateMethod").value;
	var intRateType = document.getElementById("interestRateType").value;
	
	if(intRateNethod=='L')
	{
		document.getElementById("interestRateType").value='E';
		interestRateTypeChange();
	}
		
}
function interestRateTypeChange()
{
	var rateType = document.getElementById("interestRateType").value;
	var intRateNethod = document.getElementById("interestRateMethod").value;
	
//alert("rateType: "+rateType);
//alert("intRateNethod: "+intRateNethod);
	if(rateType!=null && rateType!='')
	{
		if(rateType=='E')
		{
			document.getElementById("baseRateType").setAttribute("disabled",true);
		}
//		if(intRateNethod=='L' && rateType=='F')
//		{
//			alert("Combination of Interest Rate Type & Interest Rate Method is Wrong");
//			document.getElementById("interestRateType").value='E';
//			return false;
//		}
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

function fnFinalRate()
{
	// alert("In getFinalRate");
	var rate = document.getElementById("interestRateType").value;
	var repaymentType=document.getElementById("dealRepaymentType").value;
	// alert("rate type:"+rate);
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			
			document.getElementById("interestRateMethod").removeAttribute("disabled",true);
			document.getElementById("interestRateMethod").value='L';
			//document.getElementById("type2").removeAttribute("disabled",true);
			document.getElementById("baseRateType").setAttribute("disabled",true);
			document.getElementById("baseRate").value=document.getElementById("rateBase").value;
			document.getElementById("baseRateType").value=document.getElementById("rate").value;
			document.getElementById("baseRate").removeAttribute("disabled",true);
			//document.getElementById("markUp").removeAttribute("disabled",true);
			return true;
		}
		else
		{
			//alert("asdasdsad: "+repaymentType);
			if(repaymentType=='N')
			{
				document.getElementById("interestRateType").value='E';
				alert("You can't Change Rate Type ");
				return false;
			}
			else
			{
				document.getElementById("interestRateMethod").setAttribute("disabled",true);
				document.getElementById("interestRateMethod").value='F';
				//document.getElementById("type2").checked=false;
				document.getElementById("baseRateType").value='';
				//document.getElementById("baseRate").value='';
				//document.getElementById("markup").value='';
				document.getElementById("finalRate").value='0.0000';
				
				//document.getElementById("type1").setAttribute("disabled",true);
				//document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				//document.getElementById("markUp").setAttribute("disabled",true);
				return false;
			}
		}
		
	}
}
function fnFinalRate1()
{
	// alert("In getFinalRate");
	var rate = document.getElementById("interestRateType").value;
	var repaymentType=document.getElementById("dealRepaymentType").value;
	// alert("rate type:"+rate);
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			
			document.getElementById("interestRateMethod").removeAttribute("disabled",true);
		//	document.getElementById("interestRateMethod").value='L';
			//document.getElementById("type2").removeAttribute("disabled",true);
			document.getElementById("baseRateType").setAttribute("disabled",true);
			document.getElementById("baseRate").value=document.getElementById("rateBase").value;
			document.getElementById("baseRateType").value=document.getElementById("rate").value;
			document.getElementById("baseRate").removeAttribute("disabled",true);
			//document.getElementById("markUp").removeAttribute("disabled",true);
			return true;
		}
		else
		{
			//alert("asdasdsad: "+repaymentType);
			if(repaymentType=='N')
			{
				document.getElementById("interestRateType").value='E';
				alert("You can't Change Rate Type ");
				return false;
			}
			else
			{
				document.getElementById("interestRateMethod").setAttribute("disabled",true);
			//	document.getElementById("interestRateMethod").value='F';
				//document.getElementById("type2").checked=false;
				document.getElementById("baseRate").value=document.getElementById("rateBase").value;
				
				//document.getElementById("type1").setAttribute("disabled",true);
				//document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				//document.getElementById("markUp").setAttribute("disabled",true);
				return false;
			}
		}
		
	}
}


function getBasefacilityRate()
{
	// alert("In getBaseRate");
	var baseRateType =document.getElementById('baseRateType').value ;
	var contextPath =document.getElementById('contextPath').value ;
	// alert("contextPath"+contextPath);
	if (baseRateType== '') 
    {
		baseRateType=document.getElementById('rate').value ;
    }
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

function calculateFacilityFinalRate()
{
	var markup = document.getElementById("markup").value;
	var finalRate = document.getElementById("finalRate").value;
	var baseRate = document.getElementById("baseRate").value;
	
//alert("In calculateFinalRate markUp: "+markup+"baseRate: "+baseRate+"finalRate:"+finalRate);
	if(finalRate=="")
	{
		
		finalRate=0;
		document.getElementById("finalRate").value=0.0000;
	}
	if(baseRate=="")
	{
		baseRate=0;
	
	}
	

	markup=parseFloat(finalRate)-parseFloat(baseRate);
	//alert("final rate is :"+markup);
	document.getElementById("markup").value=markup.toFixed(7);	
}

function fnClearFacilityDetails(){
	
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("facilityDetailsForm").action=sourcepath+"/facilityDetailsDispatch.do?method=openFacilityDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("facilityDetailsForm").submit();
		return true;
	 
}
function fnCalcTenure(){
	var minTenure = document.getElementById("minTenure").value;
	var maxTenure = document.getElementById("maxTenure").value;
	var txtTenure = document.getElementById("txtTenure").value;
 	parseMinTenure=parseInt(minTenure);
 	parseMaxTenure=parseInt(maxTenure);
 	parseTxtTenure=parseInt(txtTenure);
 	
 	if(parseTxtTenure<parseMinTenure  ){
 		alert("Tenure should be not less than "+parseMinTenure );
 		document.getElementById("txtTenure").value='';
 		return false
 	}
 	else if(parseTxtTenure>parseMaxTenure  ){
 		alert("Tenure should be not greater than "+parseMaxTenure );
 		document.getElementById("txtTenure").value='';
 		return false
 	}
 	else{
 		return true;
 	}
 	
}
function LoanAmountCkeck(){
	var iDefValue = 0;
	if (document.getElementById("loanAmount").value != '') {
		iDefValue = parseFloat(removeComma(document.getElementById("loanAmount").value));
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
		document.getElementById("loanAmount").value = '';
		DisButClass.prototype.EnbButMethod();
		return false;
	} 
	if (((iDefValue < iMinValue) || (iDefValue > iMaxValue)) && (iDefValue != '' && iMinValue != '' && iMaxValue != '')) {
		alert("Please Insert Requested Loan Amount between " + iMinValue + " and " + iMaxValue);
		document.getElementById("loanAmount").value = '';
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function finalRate(){
	var minPerc=0;
	var maxPerc=0;
	var defper =0;
	
	if(document.getElementById("minMarginRate").value!='')
	{
		minPerc=parseFloat(removeComma(document.getElementById("minMarginRate").value));
	}
	if(document.getElementById("maxMarginRate").value!='')
	{
		maxPerc=parseFloat(removeComma(document.getElementById("maxMarginRate").value));
	}
	if(document.getElementById("finalRate").value!='')
	{
		defper =parseFloat(removeComma(document.getElementById("finalRate").value));
	}
	//alert(defper+" "+minPerc+" "+maxPerc);
	 if((defper<=minPerc)||(defper>=maxPerc))	
	 {
		  alert("Please Enter the value from "+minPerc+" to "+maxPerc);
		  document.getElementById("finalRate").value='';
		  return false;
	  }
}
function fnUpdateFacilityLimitDetails(){
//	   var specialCondition=document.getElementById("specialCondition").value;
	   DisButClass.prototype.DisButMethod();
	   if (document.getElementById("loanAmount").value == ""){
			alert("* Loan Amount is Required"); 
			 DisButClass.prototype.EnbButMethod();
			  return false;
		}
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("facilityDetailsForm").action=sourcepath+"/facilityDetails.do?method=saveFacilityDetails&updateFlag='Y'";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("facilityDetailsForm").submit();
		return true;
	 
}