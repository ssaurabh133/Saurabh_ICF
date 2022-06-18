function searchLimit()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	if(document.getElementById("dealNo").value=='' ){
		alert("* Please Select Deal Number");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		document.getElementById("searchLimitEnhancementForm").action=contextPath+"/limitEnhansmentBehind.do?method=limitMakerSearch&value=S";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("searchLimitEnhancementForm").submit();
		return true;
	}
}

function searchLimitAuthor()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	if(document.getElementById("dealNo").value=='' ){
		alert("* Please Select Deal Number");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else{
		document.getElementById("searchLimitEnhancementForm").action=contextPath+"/limitEnhansmentBehind.do?method=limitAuthorSearch&value=M";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("searchLimitEnhancementForm").submit();
		return true;
	}
}

function newLimitMaker()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	//alert(contextPath);
	document.getElementById("searchLimitEnhancementForm").action=contextPath+"/newLimitEnhancement.do?method=openLimitMaker";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("searchLimitEnhancementForm").submit();
	return true;
}
function checkLoanType()
{
	var lbxLoanNo=document.getElementById("lbxLoanNo").value;
	var dealNo = document.getElementById("dealNo").value;
	if(lbxLoanNo=="" && dealNo=="")
	{
		alert("First Select the Loan No.");
		document.getElementById("loanAmountType").value="";
		return false;
	}
}

function saveNewLimit()
{
	DisButClass.prototype.DisButMethod();
	var appDate=document.getElementById("applicationdate").value;
	var contextPath=document.getElementById("contextPath").value;
	var loanInsType=document.getElementById("loanInsType").value;
	var loanAmtType=document.getElementById("loanAmountType").value;
	var lbxLoanNo=document.getElementById("lbxLoanNo").value;
	var dealNo= document.getElementById("dealNo").value;
	if(loanAmtType!="")
	{
		if(lbxLoanNo=="" && dealNo=="")
		{
			alert("First Select the Loan No.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	
	//alert("loanInsType111="+loanInsType);
	if(loanInsType=='N')
	{	//alert("loanInsType222="+loanInsType);
		var loanAmountType=document.getElementById("loanAmountType").value;
		if(document.getElementById("lbxLoanNo").value!="" && loanAmountType=="")
		{
			alert("* Loan Amount Type is required.");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			
		var oldLoanAmount=removeComma(document.getElementById("oldLoanAmount").value);
		var newLoanAmount=removeComma(document.getElementById("newLoanAmount").value);
		var addSenAmount=removeComma(document.getElementById("addSenAmount").value);
		var oldSenAmount=removeComma(document.getElementById("oldSenAmount").value);
		var totalDisbursedAmount=removeComma(document.getElementById("totalDisbursedAmount").value);
		//alert("oldSenAmount="+oldSenAmount+"addSenAmount="+addSenAmount+"totalDisbursedAmount="+totalDisbursedAmount);
		var maxNewLoanAmount=oldLoanAmount+newLoanAmount;
		if(loanAmountType=='D')
		{
			 maxNewLoanAmount=oldLoanAmount-newLoanAmount;
		}
		var totalSanctionAmount=oldSenAmount+addSenAmount;
		
			
		//alert("maxNewLoanAmount "+maxNewLoanAmount);
		
		if(maxNewLoanAmount>totalSanctionAmount)
		{
			//alert("Modify>>old==="+oldSenAmt+"new=="+newlnAmt);
			alert("Loan Amount + Additional Loan amount should be less than equal to Old Sanction Amount + Additional Sanction Amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(totalDisbursedAmount>maxNewLoanAmount)
		{
			//alert("Modify>>dis==="+ttlDisAmt+"new=="+newlnAmt);
			alert("Disbursed Amount should be less than equal to Loan amount + Additional Loan amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		} 
	}
	
  if(document.getElementById("dealNo").value==""  || document.getElementById("makeRemarks").value==""  )
	{
		var a="";
		if(document.getElementById("dealNo").value==""){
			a="* Deal Number is required \n";
		}
	
		if(document.getElementById("makeRemarks").value==""){
			a +="* Maker Remarks is required \n";
		}
		alert(a);
		document.getElementById("saveButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
 
   if(!checkAppDate('applicationdate'))
	{
	    document.getElementById("saveButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
   
	var additionSanAmount="0";
	if(document.getElementById("addSenAmount").value=="")
	{
		additionSanAmount="0";
	}
	else
	{
		additionSanAmount=document.getElementById("addSenAmount").value;
	}
	additionSanAmount=removeComma(additionSanAmount);
	
	if (document.getElementById("lbxLoanNo").value=="" && (additionSanAmount==0  && document.getElementById("applicationdate").value==""))
	{
		alert("* Select Either Additional Sanction Amount or New Sanction Valid Till \n");
		document.getElementById("saveButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
 		
		
		document.getElementById("LimitMakerForm").action=contextPath+"/newLimitEnhancement.do?method=saveLimitMaker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("LimitMakerForm").submit();
		return true;
	
}


function saveForward()
{
	DisButClass.prototype.DisButMethod();
	alert("Please Save Limit First");
	document.getElementById("frowardButton").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
}

function modfySaveLimit()
{
	DisButClass.prototype.DisButMethod();
	var appDate=document.getElementById("applicationdate").value;
	var contextPath=document.getElementById("contextPath").value;


    var loanInsType=document.getElementById("loanInsType").value;
	
	//alert("loanInsType111="+loanInsType);
    if(loanInsType=='N')
	{	//alert("loanInsType222="+loanInsType);
		var loanAmountType=document.getElementById("loanAmountType").value;
		if(document.getElementById("lbxLoanNo").value!="" && loanAmountType=="")
		{
			alert("* Loan Amount Type is required.");
			document.getElementById("saveButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			
		var oldLoanAmount=removeComma(document.getElementById("oldLoanAmount").value);
		var newLoanAmount=removeComma(document.getElementById("newLoanAmount").value);
		var addSenAmount=removeComma(document.getElementById("addSenAmount").value);
		var oldSenAmount=removeComma(document.getElementById("oldSenAmount").value);
		var totalDisbursedAmount=removeComma(document.getElementById("totalDisbursedAmount").value);
		//alert("oldSenAmount="+oldSenAmount+"addSenAmount="+addSenAmount+"totalDisbursedAmount="+totalDisbursedAmount);
		var maxNewLoanAmount=oldLoanAmount+newLoanAmount;
		if(loanAmountType=='D')
		{
			 maxNewLoanAmount=oldLoanAmount-newLoanAmount;
		}
		var totalSanctionAmount=oldSenAmount+addSenAmount;
		
			
		//alert("maxNewLoanAmount "+maxNewLoanAmount);
		
		if(maxNewLoanAmount>totalSanctionAmount)
		{
			//alert("Modify>>old==="+oldSenAmt+"new=="+newlnAmt);
			alert("Loan Amount + Additional Loan amount should be less than equal to Old Sanction Amount + Additional Sanction Amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(totalDisbursedAmount>maxNewLoanAmount)
		{
			//alert("Modify>>dis==="+ttlDisAmt+"new=="+newlnAmt);
			alert("Disbursed Amount should be less than equal to Loan amount + Additional Loan amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		} 
	}
	if( document.getElementById("makeRemarks").value=="" )
	{
		alert("* Maker Remarks is required \n");
		document.getElementById("frowardButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if(!checkAppDate('applicationdate'))
	{
		document.getElementById("frowardButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var additionSanAmount="0";
	if(document.getElementById("addSenAmount").value=="")
	{
		additionSanAmount="0";
	}
	else
	{
		additionSanAmount=document.getElementById("addSenAmount").value;
	}
	additionSanAmount=parseFloat(additionSanAmount);
	
	if (document.getElementById("lbxLoanNo").value=="" && (additionSanAmount==0  && document.getElementById("applicationdate").value==""))
	{
		alert("* Select Either Additional Sanction Amount or New Sanction Valid Till \n");
		document.getElementById("frowardButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
		
		
		document.getElementById("LimitMakerForm").action=contextPath+"/newLimitEnhancement.do?method=modfySaveLimit";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("LimitMakerForm").submit();
		return true;
}

function savemodfyLimit()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var appDate=document.getElementById("applicationdate").value;
	
	var loanInsType=document.getElementById("loanInsType").value;
	
	//alert("loanInsType111="+loanInsType);
	if(loanInsType=='N')
	{	//alert("loanInsType222="+loanInsType);
		var loanAmountType=document.getElementById("loanAmountType").value;
		if(document.getElementById("lbxLoanNo").value!="" && loanAmountType=="")
		{
			alert("* Loan Amount Type is required.");
			document.getElementById("frowardButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			
		var oldLoanAmount=removeComma(document.getElementById("oldLoanAmount").value);
		var newLoanAmount=removeComma(document.getElementById("newLoanAmount").value);
		var addSenAmount=removeComma(document.getElementById("addSenAmount").value);
		var oldSenAmount=removeComma(document.getElementById("oldSenAmount").value);
		var totalDisbursedAmount=removeComma(document.getElementById("totalDisbursedAmount").value);
		//alert("oldSenAmount="+oldSenAmount+"addSenAmount="+addSenAmount+"totalDisbursedAmount="+totalDisbursedAmount);
		var maxNewLoanAmount=oldLoanAmount+newLoanAmount;
		if(loanAmountType=='D')
		{
			 maxNewLoanAmount=oldLoanAmount-newLoanAmount;
		}
		var totalSanctionAmount=oldSenAmount+addSenAmount;
		
			
		//alert("maxNewLoanAmount "+maxNewLoanAmount);
		
		if(maxNewLoanAmount>totalSanctionAmount)
		{
			//alert("Modify>>old==="+oldSenAmt+"new=="+newlnAmt);
			alert("Loan Amount + Additional Loan amount should be less than equal to Old Sanction Amount + Additional Sanction Amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(totalDisbursedAmount>maxNewLoanAmount)
		{
			//alert("Modify>>dis==="+ttlDisAmt+"new=="+newlnAmt);
			alert("Disbursed Amount should be less than equal to Loan amount + Additional Loan amount");
			document.getElementById("newLoanAmount").value='0';
			DisButClass.prototype.EnbButMethod();
			return false;
		} 
	}
	if( document.getElementById("makeRemarks").value=="" )
	{	
		alert("* Maker Remarks is required \n");
		document.getElementById("frowardButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(!checkAppDate('applicationdate'))
	{
		    document.getElementById("frowardButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
	}
	var additionSanAmount="0";
	if(document.getElementById("addSenAmount").value=="")
	{
		additionSanAmount="0";
	}
	else
	{
		additionSanAmount=document.getElementById("addSenAmount").value;
	}
	additionSanAmount=removeComma(additionSanAmount);
	
	if (document.getElementById("lbxLoanNo").value=="" && (additionSanAmount==0  && document.getElementById("applicationdate").value==""))
	{
		alert("* Select Either Additional Sanction Amount or New Sanction Valid Till \n");
		document.getElementById("frowardButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	 
		
	document.getElementById("LimitMakerForm").action=contextPath+"/newLimitEnhancement.do?method=modfyNewLimit";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("LimitMakerForm").submit();
	return true;
	
}

function saveAuthorRemarks()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("lbxDealNo").value;
	var loanId=document.getElementById("lbxLoanNo").value;
	var newLoanAmount=document.getElementById("newLoanAmount").value;
	//alert("in Save Action of js");
	
	if(document.getElementById("decison").value=="" || document.getElementById("textarea").value=="")
	{
	var a="";
	if(document.getElementById("decison").value==""){
		a="* Decision is required \n";
	}
	if(document.getElementById("textarea").value==""){
		a +="* Author Remarks is required \n";
	}
	alert(a);
	document.getElementById("save").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("LimitAuthorForm").action=contextPath+"/limitEnhanceAuthor.do?method=saveLimitAuthor&dealID="+dealId+"&loanID="+loanId+"&newLoanAmount="+newLoanAmount;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("LimitAuthorForm").submit();
		return true;
	}
}

function validateLimitDate()
{
	//alert("asdfasdfadfasdf");
	var msg='';
	var formatD=document.getElementById("formatD").value;

	var bDate=document.getElementById("businessdate").value;
	var appDate=document.getElementById("applicationdate").value;

    var dt1=getDateObject(appDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));

    if(appDate!=''){
    	
    	
    
    if(dt1<dt3)
	{
		msg="New Sanction Valid Till should be greater then bussiness Date";
		document.getElementById("applicationdate").value='';
		//return false;
	}
    }
	if(msg!='')
	{
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		
		return true;
	}
}

function searchDealInLimit()
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	var val="";

	if(document.getElementById("lbxDealNo").value==""){
	alert("Please Select Deal Number");
	DisButClass.prototype.EnbButMethod();
	return false;
	}
	
	else{
		val=document.getElementById("lbxDealNo").value;
	//	alert(val);
	var url=contextPath+"/dealCapturing.do?method=showDealLimitEnhancement&dealId="+val;
	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
	DisButClass.prototype.EnbButMethod();
	return true;
	}
}
//manish Shukla

function checkAppDate(txtDate)
{
    // define date string to test
    var txtDateValue = document.getElementById(txtDate).value;	
    var formatD=document.getElementById("formatD").value;// must be take this format from jsp
    if (isDate(txtDateValue,formatD) || txtDateValue == null || txtDateValue == ''|| txtDateValue != null || txtDateValue != '') 
    {
       if(validateLimitDate()){
    	  
    	   return true;}
       else
    	   return false;
    }
    else 
    {
    	
        alert('Invalid date format!');
        document.getElementById(txtDate).value='';
        DisButClass.prototype.EnbButMethod();
        return false;
    }
}
function keyUpNumber(val, e, Max, san){
	if(val.indexOf(',') > 0){
			var dynaVal = san;
			document.getElementById(dynaVal).maxLength = Max+3;
			var origString = val;
			var inChar = ',';
			var outChar = '.';
			var newString = origString.split(outChar);
			var newString = origString.split(inChar);
			newString = newString.join('');
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
	}
}
//Rohit Changes Starts for limit Enhancement
function openFacilityDetailsLimit() {
	var contextPath = document.getElementById('contextPath').value;
	if(document.getElementById("lbxDealNo").value==""){
		alert("Please Select Deal Number");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		
		else{
			val=document.getElementById("lbxDealNo").value;
	var url = contextPath
			+ "/facilityDetailsDispatch.do?method=openFacilityDetails&dealId="
			+ val + " ";
	newwindow = window.open(url, 'name',
			'height=400,width=1100,toolbar=no,scrollbars=yes');
	if (window.focus) {
		newwindow.focus()
	}
	return false;
}
}
//Rohit end