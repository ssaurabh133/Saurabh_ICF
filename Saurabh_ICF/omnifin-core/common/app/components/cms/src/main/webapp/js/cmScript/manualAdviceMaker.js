
function showOnAdviceType()
{
	
	if(document.getElementById("adviceType").value=='PAYABLE')
	{
		
		document.getElementById("tdsApplicable").disabled=false;
		
	}
	else
	{
		document.getElementById("tdsRate").value='0';
		document.getElementById("tdsAmount").value='0';
		document.getElementById("tdsApplicable").value="";
		document.getElementById("tdsRate").readOnly=true;
		document.getElementById("tdsAmount").readOnly=true;
		document.getElementById("tdsApplicable").disabled=true;
	}
	
}
//method added by neraj
function TaxAmountView()
{
	var taxApplicable=document.getElementById("taxApplicable").value;
	if(taxApplicable=='NO')
	{
		document.getElementById("taxAmount1").setAttribute("readOnly","true");
		document.getElementById("taxAmount2").setAttribute("readOnly","true");						
	}
	else
	{
		document.getElementById("taxAmount1").removeAttribute("readOnly","true");
		document.getElementById("taxAmount2").removeAttribute("readOnly","true");
	}	
}

function showOnTdsApplicable()
{
	if(document.getElementById("tdsApplicable").value=='YES')
	{
		document.getElementById("tdsRate").readOnly=false;
		document.getElementById("tdsAmount").readOnly=false;
	}
	else
	{
		document.getElementById("tdsAmount").value='0';
		document.getElementById("tdsRate").value='0';
		//document.getElementById("tdsApplicable").value="";
		document.getElementById("tdsRate").readOnly=true;
		document.getElementById("tdsAmount").readOnly=true;
	}
}


function showOnTaxApplicable()
{
	if(document.getElementById("taxApplicable").value=='YES')
	{
		document.getElementById("taxRate1").readOnly=false;
		document.getElementById("taxAmount1").readOnly=false;
		
		document.getElementById("taxRate2").readOnly=false;
		document.getElementById("taxAmount2").readOnly=false;
	}
	else
	{
		document.getElementById("taxRate1").value='0';
		document.getElementById("taxAmount1").value='0';
		
		document.getElementById("taxRate2").value='0';
		document.getElementById("taxAmount2").value='0';
		
		document.getElementById("taxRate1").readOnly=true;
		document.getElementById("taxAmount1").readOnly=true;
		
		document.getElementById("taxRate2").readOnly=true;
		document.getElementById("taxAmount2").readOnly=true;
	}
}

function insert(btn)
{ 
	//alert("ok"+btn);
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById("contextPath").value;
	var adviceAmount=document.getElementById("adviceAmount").value;
	var taxAmount1=document.getElementById("taxAmount1").value;
	var taxAmount2=document.getElementById("taxAmount2").value;
	var minChargeAmount=document.getElementById("minChargeAmount").value;
	var origionalReversal = document.getElementById("origionalReversal");
	var orgAdviceId = document.getElementById("orgAdviceId").value;
	var lbxorgAdviceId = document.getElementById("lbxorgAdviceId").value;	
	//alert("adviceAmount::"+adviceAmount);
	if(btn=='Save')
	{
		if(document.getElementById("adviceDtChngFlag").value=='N')
		{
			var loanAccountNo = document.getElementById("loanAccountNo");
			var businessPartnerTypeDesc = document.getElementById("businessPartnerTypeDesc");
			var chargeCode = document.getElementById("chargeCode");
			var chargeAmount = document.getElementById("chargeAmount");
			var remarks = document.getElementById("remarks");
			
			 if(trim(loanAccountNo.value) == ''||trim(businessPartnerTypeDesc.value) == ''||trim(chargeCode.value) == ''||trim(chargeAmount.value) == ''||trim(origionalReversal.value)== ''){
				 var msg= '';
				 
				 if(trim(loanAccountNo.value) == '')
			 			msg += '* Loan Account Number is required.\n';
			 		if(trim(businessPartnerTypeDesc.value) == '')
			 			msg += '* Business Partner Type is required.\n';
			 		if(trim(chargeCode.value) == '')
			 			msg += '* Charge Code is required.\n';
			 		if(trim(chargeAmount.value) == '')
			 			msg += '* Charge Amount is required.\n';
					if(trim(origionalReversal.value) == '')
			 			msg += '* Original/Reversal is required.\n';					
			 				 		 		
			 		if(msg.match("Loan")){
			 			document.getElementById("loanNoButton").focus();
			 		}else if(msg.match("Partner")){
			 			document.getElementById("businessPartButton").focus();
			 		}else if(msg.match("Code")){
			 			document.getElementById("chargeButton").focus();
					}else if(msg.match("Origional")){
			 			document.getElementById("origionalReversal").focus();
					}else if(msg.match("Amount")){
			 			chargeAmount.focus();
			 		}
		 		
		 		alert(msg);
		 		DisButClass.prototype.EnbButMethod();
		 		document.getElementById("save").removeAttribute("disabled","true");
		 		return false;
			 
			 }
			 else if(trim(document.getElementById("origionalReversal").value)=="R" && trim(document.getElementById("orgAdviceId").value)=="")
			 {
				 	alert("Original Advice Id is required.");
					DisButClass.prototype.EnbButMethod();
			 		document.getElementById("save").removeAttribute("disabled","true");
			 		return false;
			 }else if(minChargeAmount>removeComma(document.getElementById("chargeAmount").value))
		 			{
			 			alert("Charge Amount cannot be less than Minimum Charge Amount.")
			 			DisButClass.prototype.EnbButMethod();
			 			document.getElementById("save").removeAttribute("disabled","true");
			 			return false;
		 			}	
				 else
					 {
					document.getElementById("manualAdviceCreationForm").action=contextPath+"/manualAdviceCreationMaker.do?method=saveManualAdviceCreation&adviceAmount="+adviceAmount+"&taxAmount1="+taxAmount1+"&taxAmount2="+taxAmount2+"&lbxorgAdviceId="+lbxorgAdviceId+"&origionalReversal="+origionalReversal.value+"&orgAdviceId="+orgAdviceId;
					//alert(document.getElementById("manualAdviceCreationForm").action);
					document.getElementById("processingImage").style.display = '';
				 	document.getElementById("manualAdviceCreationForm").submit();
				 	return true;
			}
		}
		else
		{
			alert("Please select charge again due to change of value date");
			DisButClass.prototype.EnbButMethod();
	 		document.getElementById("save").removeAttribute("disabled","true");
	 		return false;
		}
	}
	else
	{
		alert("Please Save manual Advice Detail First!!!");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	DisButClass.prototype.EnbButMethod();
	return false;
}

function update(btn,fwdMsg)
{ 
	 DisButClass.prototype.DisButMethod();
	if(btn=="Save & Forward")
	{
		btn="Forward";
	}
	
	var loanAccountNo = document.getElementById("loanAccountNo");
	var businessPartnerTypeDesc = document.getElementById("businessPartnerTypeDesc");
	var chargeCode = document.getElementById("chargeCode");
	var chargeAmount = document.getElementById("chargeAmount");
	var remarks = document.getElementById("remarks");
	
	var adviceAmount=document.getElementById("adviceAmount").value;
	var taxAmount1=document.getElementById("taxAmount1").value;
	var taxAmount2=document.getElementById("taxAmount2").value;
	var minChargeAmount=document.getElementById("minChargeAmount").value;
	var origionalReversal = document.getElementById("origionalReversal");
	var orgAdviceId = document.getElementById("orgAdviceId").value;
	var lbxorgAdviceId = document.getElementById("lbxorgAdviceId").value;
	 if(trim(loanAccountNo.value) == ''||trim(businessPartnerTypeDesc.value) == ''||trim(chargeCode.value) == ''||trim(chargeAmount.value) == ''||trim(origionalReversal.value)== ''){
		 var msg= '';
		 
		 if(trim(loanAccountNo.value) == '')
	 			msg += '* Loan Account Number is required.\n';
	 		if(trim(businessPartnerTypeDesc.value) == '')
	 			msg += '* Business Partner Type is required.\n';
	 		if(trim(chargeCode.value) == '')
	 			msg += '* Charge Code is required.\n';
	 		if(trim(chargeAmount.value) == '')
	 			msg += '* Charge Amount is required.\n';
	 		if(trim(origionalReversal.value) == '')
	 			msg += '* Origional/Reversal is required.\n';
	 		if(msg.match("Loan")){
	 			document.getElementById("loanNoButton").focus();
	 		}else if(msg.match("Partner")){
	 			document.getElementById("businessPartButton").focus();
	 		}else if(msg.match("Code")){
	 			document.getElementById("chargeButton").focus();
	 		}else if(msg.match("Origional")){
	 			document.getElementById("origionalReversal").focus();
	 		}else if(msg.match("Amount")){
	 			chargeAmount.focus();
	 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else if(trim(document.getElementById("origionalReversal").value)=="R" && trim(document.getElementById("orgAdviceId").value)=="")
		 {
		 	alert("Original Advice Id is required.");
			DisButClass.prototype.EnbButMethod();
	 		document.getElementById("save").removeAttribute("disabled","true");
	 		return false;
	 }else{
		    
		 //Ravi start
		 DisButClass.prototype.EnbButMethod();
		 
		   if(btn=='Forward') 
		   {
			   if(!confirm(fwdMsg))	 
			    {
			       	DisButClass.prototype.EnbButMethod();
			    	return false;
			    }
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById(btn).removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById(btn).removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		   }
		    //Ravi End
		   if(minChargeAmount>removeComma(document.getElementById("chargeAmount").value))
			{
			  
	 			alert("Charge Amount cannot be less than Minimum Charge Amount.")
	 			DisButClass.prototype.EnbButMethod();
	 			document.getElementById("save").removeAttribute("disabled","true");
	 			return false;
			}	
		   else
			   {
				document.getElementById("manualAdviceCreationForm").action="manualAdviceCreationMaker.do?method=updateManualAdviceCreation&forward="+btn+"&adviceAmount="+adviceAmount+"&taxAmount1="+taxAmount1+"&taxAmount2="+taxAmount2+"&lbxorgAdviceId="+lbxorgAdviceId+"&origionalReversal="+origionalReversal.value+"&orgAdviceId="+orgAdviceId;
				document.getElementById("processingImage").style.display = '';
			 	document.getElementById("manualAdviceCreationForm").submit();
			 	return true;
			   }
	}
	 DisButClass.prototype.EnbButMethod();
	return false;
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
   // alert(amount);
	return amount;
}

function totAdviceAmount()
{

	var chargeAmt =0;
	var taxAmt1 = 0;
	var taxAmt2 =0;
//	alert("alert:"+document.getElementById("taxInclusive").value);
if(document.getElementById("taxInclusive").value==undefined || document.getElementById("taxInclusive").value==null||document.getElementById("taxInclusive").value==""){
		
		if(document.getElementById("chargeAmount").value==undefined || document.getElementById("chargeAmount").value==null){
			chargeAmt=0;	
		}

		if(document.getElementById("taxAmount1").value==undefined || document.getElementById("taxAmount1").value==null){
			taxAmount1=0;	
		}
		if(document.getElementById("taxAmount2").value==undefined || document.getElementById("taxAmount2").value==null){
			taxAmt2=0;	
		}
		if(document.getElementById("taxRate1").value==undefined || document.getElementById("taxRate1").value==null){
			taxRate1=0;	
		}
		alert("Charge Definition not found.")
		document.getElementById("chargeAmount").value="";
		return false;
	}
	
	
	
	if(document.getElementById("taxInclusive").value!="")
	{
		taxInclusive=document.getElementById("taxInclusive").value;
	}
	document.getElementById("taxInclusive").value=taxInclusive;
	
	
	if(taxInclusive=='NO')
	{
			
	if(document.getElementById("chargeAmount").value!="")
	{
		chargeAmt=parseFloat(removeComma(document.getElementById("chargeAmount").value));
	}
	if(document.getElementById("taxAmount1").value!="")
	{
		taxAmt1=parseFloat(removeComma(document.getElementById("taxAmount1").value));
	}
	if(document.getElementById("taxAmount2").value!="")
	{
		taxAmt2=parseFloat(removeComma(document.getElementById("taxAmount2").value));
	}
	if(document.getElementById("taxRate1").value!="")
	{
		taxRate1=parseFloat(removeComma(document.getElementById("taxRate1").value));
	}
	
	document.getElementById("adviceAmount").value=chargeAmt+taxAmt1+taxAmt2;
	//alert("chargeAmt::"+chargeAmt);
	//alert("taxAmt1::"+taxAmt1);
	//alert("taxAmt2::"+taxAmt2);
	//alert("adviceAmount 1::"+document.getElementById("adviceAmount").value);
	document.getElementById("taxAmount1").value=parseFloat((removeComma(document.getElementById("taxRate1").value))*parseFloat(removeComma(document.getElementById("chargeAmount").value))/100).toFixed(2);
	document.getElementById("taxAmount2").value=parseFloat((removeComma(document.getElementById("taxRate2").value))*parseFloat(removeComma(document.getElementById("chargeAmount").value))/100).toFixed(2);
	
	var d=parseFloat(removeComma(document.getElementById("taxAmount1").value))+parseFloat(removeComma(document.getElementById("chargeAmount").value))+parseFloat(removeComma(document.getElementById("taxAmount2").value));
	document.getElementById("adviceAmount").value=parseFloat(d).toFixed(2);
	//alert("adviceAmount 2::"+document.getElementById("adviceAmount").value);
}
else{
	
	
	if(document.getElementById("chargeAmount").value!="")
	{
		chargeAmt=parseFloat(removeComma(document.getElementById("chargeAmount").value));
	}
	if(document.getElementById("taxAmount1").value!="")
	{
		taxAmount1=parseFloat(removeComma(document.getElementById("taxAmount1").value));
	}
	if(document.getElementById("taxAmount2").value!="")
	{
		taxAmt2=parseFloat(removeComma(document.getElementById("taxAmount2").value));
	}
	if(document.getElementById("taxRate1").value!="")
	{
		taxRate1=parseFloat(removeComma(document.getElementById("taxRate1").value));
	}
	//document.getElementById("adviceAmount").value=chargeAmt+taxAmt1+taxAmt2;
	
	var chamt=parseFloat(removeComma(document.getElementById("chargeAmount").value)*document.getElementById("taxRate2").value);
	var a=parseFloat(document.getElementById("chargeAmount").value*document.getElementById("taxRate1").value);
	var b=100+parseFloat(document.getElementById("taxRate1").value);
	var f=100+parseFloat(document.getElementById("taxRate2").value);
	document.getElementById("taxAmount1").value=parseFloat(a/b).toFixed(2);
	document.getElementById("taxAmount2").value=parseFloat(chamt/f).toFixed(2);
	var c=parseFloat(removeComma(document.getElementById("adviceAmount").value))-parseFloat(removeComma(document.getElementById("taxAmount1").value));
	document.getElementById("adviceAmount").value=document.getElementById("chargeAmount").value;
}
}


function manualMakersearch()
{
	DisButClass.prototype.DisButMethod();
	if( document.getElementById("loanAccountNo").value=="" && document.getElementById("customerName").value=="" && document.getElementById("businessPartnerType").value=="" && document.getElementById("businessPartnerName").value=="" && document.getElementById("chargeCode").value==""&& document.getElementById("reportingToUserId").value=="")
	{
		alert("Please Enter One Field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}	
	else
	{
		
		
	var basePath=document.getElementById("contextPath").value;
	
    document.getElementById("manualAdviceSerchForm").action=basePath+"/manualAdviceSearchProcessAction.do?method=searchDetail";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("manualAdviceSerchForm").submit();
    //alert("ok");
    return true;
 	}
		
}
function editMaualAdvice(loanId,manualId)
{
	
	//alert(loanId);
	//alert(manualId);
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("manualAdviceSerchForm").action=basePath+"/manualAdviceSearchProcessAction.do?method=EditManualAdviceMaker&loanId="+loanId+"&manualId="+manualId;
	document.getElementById("manualAdviceSerchForm").submit();
	//alert("ok");
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

function manualAdviceAuthor()
{
	 DisButClass.prototype.DisButMethod();
	if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
	   {
		alert("Please Select the required field ");
		 DisButClass.prototype.EnbButMethod();
		document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	   }
	else
	{
		
		var decision = document.getElementById("decision").value;
		//alert("descion "+decision);
		var basePath=document.getElementById("contextPath").value;
		
		//Ravi start
		 DisButClass.prototype.EnbButMethod();
		 
		  
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		//alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("Save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		 
		    //Ravi End
		
		document.getElementById("manualAdviceSerchForm").action = basePath+"/manualAdviceSearchProcessAction.do?method=manualAdviceAuthor&decision="+decision;
		document.getElementById("processingImage").style.display = '';
	     document.getElementById("manualAdviceSerchForm").submit();
	     return true;
	     
	   }
	

}
function deleteManualAdvice(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var manaulId=document.getElementById("manaulId").value;
	//alert("manaulId......"+manaulId);
	agree=confirm("Are you sure,You want to delete this ManualAdvice.Do you want to continue?");
	if (!(agree))
	{
		DisButClass.prototype.EnbButMethod();
    	document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("manualAdviceCreationForm").action=sourcepath+"/manualAdviceCreationMaker.do?method=deleteManualAdvice&manaulId"+manaulId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualAdviceCreationForm").submit();
		return true;
}
}


function checkValueDate()
{
	
 	var formatD=document.getElementById("formatD").value;
 	var valueDate = document.getElementById("valueDate").value;
 	var valDate=getDateObject(valueDate,formatD.substring(2, 3));
    var businessdate = document.getElementById("businessdate").value;
    var busDate = getDateObject(businessdate, formatD.substring(2, 3));
    
    //document.getElementById("adviceDtChngFlag").value='Y';
    
 	if(valDate>busDate)
 	{
 		alert("Value Date should be less than equal to Business Date");
 		document.getElementById("valueDate").value='';
 		document.getElementById("valueDate").focus();
 		//document.getElementById("save").setAttribute("disabled","true");
 		//document.getElementById("saveFwd").setAttribute("disabled","true");
 		 return false;
 	}
     else
     {
    	 var month = valDate.getMonth() + 1;
    	 var day = valDate.getDate();
    	 var year = valDate.getFullYear();
    	 //alert("month "+month);
    	 //alert("day "+day);
    	 //alert("year "+year);
    	 if(month<10)
    	 {
    		 month = "0"+month;
    	 }
    	 if(day<10)
    	 {
    		 day = "0"+day;
    	 }
    	 var dt = year+"-"+month+"-"+day;
    	 document.getElementById("initiationDateDbFormat").value=dt;    		 
    	 
    	// document.getElementById("save").setAttribute("disabled","true");
    	//document.getElementById("saveFwd").setAttribute("disabled","true");
      	return true;
     }
 }

//		Hina Changes regarding auto calculation of tax started

function calculateTax()
{
	var v_taxRate1 = document.getElementById("taxRate1").value;
	var v_taxRate2 = document.getElementById("taxRate2").value;
	var v_chargeAmount = removeComma(document.getElementById("chargeAmount").value);

	if (v_taxRate1 == "")
		v_taxRate1="0";
	if (v_taxRate2 == "")
		v_taxRate2="0";

	var v_tax_Inclusive = document.getElementById("taxInclusive").value;
	if (v_tax_Inclusive == "YES")	{
		document.getElementById("taxAmount1").value = parseFloat(v_chargeAmount)*parseFloat(v_taxRate1)/(100+parseFloat(v_taxRate1) + parseFloat(v_taxRate2));
		document.getElementById("taxAmount2").value = parseFloat(v_chargeAmount)*parseFloat(v_taxRate2)/(100+parseFloat(v_taxRate1) + parseFloat(v_taxRate2));
		document.getElementById("taxAmount1").value = Math.ceil(Math.round(document.getElementById("taxAmount1").value * 100) / 100);
		document.getElementById("taxAmount2").value = Math.ceil(Math.round(document.getElementById("taxAmount2").value * 100) / 100);
		document.getElementById("chargeAmount").value = parseFloat(document.getElementById("chargeAmount").value) - (parseFloat(document.getElementById("taxAmount1").value)+ parseFloat(document.getElementById("taxAmount2").value));
	}
	else if (v_tax_Inclusive == "NO") {
		document.getElementById("taxAmount1").value = parseFloat(v_chargeAmount)*parseFloat(v_taxRate1)/100;
		document.getElementById("taxAmount2").value = parseFloat(v_chargeAmount)*parseFloat(v_taxRate2)/100;
		document.getElementById("taxAmount1").value = Math.ceil(Math.round(document.getElementById("taxAmount1").value * 100) / 100);
		document.getElementById("taxAmount2").value = Math.ceil(Math.round(document.getElementById("taxAmount2").value * 100) / 100);
	}
}
//		Hina Changes regarding auto calculation of tax ended
function disableAdviceIdField()
{
	//alert("ok boss");
	var origionalReversal = document.getElementById("origionalReversal").value;
	var adviceId1 = document.getElementById("adviceId1");
	var adviceId2 = document.getElementById("adviceId2");
	var orgAdviceId = document.getElementById("orgAdviceId");
	if (origionalReversal == 'O')
	{
		document.getElementById("orgAdviceId").value="";
		document.getElementById("orgAdviceIdButton").style.display="none";
		document.getElementById("orgAdviceId").setAttribute("disabled",true);
		
	}
	else if (origionalReversal == 'R') {
		document.getElementById("orgAdviceIdButton").style.display="";
		document.getElementById("orgAdviceId").removeAttribute("disabled",true);
			
		
	}	
}
