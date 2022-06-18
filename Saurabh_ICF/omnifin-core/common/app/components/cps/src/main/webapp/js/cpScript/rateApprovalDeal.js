function searchrateApprovalMaker()
{
	var dealID=document.getElementById("dealId").value;
	var customerName=document.getElementById("customerName").value;
	if(dealID=='' && customerName=='')
	{
		alert('Please Enter atleast one field');
		return false;
	}
	else
	{
		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("rateApprovalMaker").action=contextPath+"/rateApprovalMaker.do?method=defaultRateApproval";
		document.getElementById("rateApprovalMaker").submit();
	}
}
function saveRateApprovalData()
{
	DisButClass.prototype.DisButMethod();
	var appUserId= document.getElementById("userName");
	var creditInsuranceCvr = document.getElementById("creditInsuranceCover");
	var dob = document.getElementById("dateOfBirth");
	var typeofcovererage = document.getElementById("typeOfCoverage");
	var sumAssuredLoan= document.getElementById("sumAsurLoanAmt");
	var csliPremiumAmt= document.getElementById("csliPremiumAmt");
	var creditInsTenor= document.getElementById("creditInsuranceTenor");
	var gInsurance= document.getElementById("generalInsurance");
	var csgiPremiumAmt= document.getElementById("csgiPremiumAmt");
	var anyOtherIns= document.getElementById("otherInsuranceSold");
	var ifYesOtherAmt= document.getElementById("productSold");
	var csoiPremiumAmt= document.getElementById("csoiPremiumAmt");
	var transactionFee= document.getElementById("transactionFee");
	var assetMachine= document.getElementById("assetMachine");
	var isThisABalanceTransfer= document.getElementById("isThisABalanceTransfer");
	var ifBt= document.getElementById("ifBt");
	var productCat= document.getElementById("productCat");
	var assetProperty=document.getElementById("assetProperty");   
	var payoutApplication=document.getElementById("payoutApplication");
	var payoutBeing=document.getElementById("payoutBeing");
	
	
	if (appUserId.value == '' || creditInsuranceCvr.value != 'N' || gInsurance.value != 'N' || anyOtherIns.value != 'N' || (productCat.value == 'TL' && isThisABalanceTransfer.value!='N') || ((assetProperty.value=="PROPERTY") && (payoutApplication.value == '' || payoutBeing.value == '')))
	{
		
		var msg= '';
		if(productCat.value=='TL'){
		if(isThisABalanceTransfer.value == ''|| isThisABalanceTransfer.value == 'Y')
		{
			if(isThisABalanceTransfer.value == '')
			{
				msg+= '* Is this a Balance Transfer (BT) is required.\n'
			}
			if(isThisABalanceTransfer.value == 'Y' && ifBt.value=='')
			{
				msg+= ' If BT - Existing ROI of the customer is required.\n'
					
			}
			
		}
		if(assetProperty.value=="PROPERTY" &&  payoutApplication.value == '') 
		{
			msg+= '* Payout % application Including Service Tax is required.\n'
		}
		if(assetProperty.value=="PROPERTY" &&  payoutBeing.value == '')
		{
			msg+= '* Payout * being given if different from above cell is required.\n'
		}
		}
		
		if(creditInsuranceCvr.value == ''|| creditInsuranceCvr.value == 'Y'){
		if(creditInsuranceCvr.value == '')
			msg += '* Credit Insurance Coverage is required.\n';
		
		if(typeofcovererage.value == '')
			msg += '* Type Of Coverage is required.\n';
		
		if(dob.value == '')
			msg += '* Date of Birth is required.\n';
		
		if(sumAssuredLoan.value == '')
			msg += '* Sum Of Assured Loan is required.\n';
		
		if(csliPremiumAmt.value == '')
			msg += '* CSLI Premium Amount is required.\n';
		
		if(creditInsTenor.value == '')
			msg += '* Credit Insurance  Tenor is required.\n';
		}
		if(gInsurance.value == ''||gInsurance.value == 'Y')
		{
		if(gInsurance.value == '')
			msg += '* General Insurance is required.\n';
		
		if(csgiPremiumAmt.value == '')
			msg += '* CSGI Premium Amount is required.\n';
		}
		if(anyOtherIns.value == ''||anyOtherIns.value == 'Y')
		{
			
		if(anyOtherIns.value == '')
			msg += '* Any Other Insurance is required.\n';
		
		if(csoiPremiumAmt.value == '')
			msg += '* CSOI Premium Amount is required.\n';
		
		if( assetMachine.value=='MACHINE')
		{
			if(transactionFee.value == ''){
			msg += '* Transaction Fee is required.\n';
			}
		}
		if(ifYesOtherAmt.value==''){
			msg +='Product sold is Required.\n';
		document.getElementById("productSold").value='';
		document.getElementById("productSold").focus();
		}
		}
		if(appUserId.value == '')
			msg += '* Approve by is required.\n';
		
		
		 if (msg.match("Is this a Balance")) {
			document.getElementById("isThisABalanceTransfer").focus();
		} else if (msg.match("If BT")) {
			document.getElementById("ifBt").focus();
		} else if (msg.match("Payout %")) {
			document.getElementById("payoutApplication").focus();
		} else if (msg.match("Payout * being given")) {
			document.getElementById("payoutBeing").focus();
		} else if (msg.match("Insurance Coverage")) {
			document.getElementById("creditInsuranceCover").focus();
		} else if (msg.match("Type Of Coverage")) {
			document.getElementById("typeOfCoverage").focus();
		} else if (msg.match("Birth")) {
			document.getElementById("dateOfBirth").focus();
		} else if (msg.match("Sum Of Assured")) {
			document.getElementById("sumAsurLoanAmt").focus();
		} else if (msg.match("CSLI Premium Amount")) {
			document.getElementById("csliPremiumAmt").focus();
		} else if (msg.match("Credit Insurance")) {
			document.getElementById("creditInsuranceTenor").focus();
		} else if (msg.match("General Insurance")) {
			document.getElementById("generalInsurance").focus();
		}else if (msg.match("CSGI Premium Amount")) {
			document.getElementById("csgiPremiumAmt").focus();
		}else if (msg.match("Any Other Insurance")) {
			document.getElementById("otherInsuranceSold").focus();
		}else if (msg.match("Product sold")) {
			document.getElementById("productSold").focus();
		}else if (msg.match("CSOI Premium Amount")) {
			document.getElementById("csoiPremiumAmt").focus();
		}else if (msg.match("Transaction Fee")) {
			document.getElementById("transactionFee").focus();
		}else if (msg.match("Approve by")) {
			document.getElementById("userName").focus();
		}

		if(msg.length!=0)
		{
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("saveButton").removeAttribute("disabled","true");
		return false;
		}
		else
		{
			if(creditInsuranceCvr.value != 'N' || creditInsuranceCvr.value == '')
			{
			var formatD=document.getElementById("formatD").value;
			var dobDate=getDateObject(dob.value,formatD.substring(2, 3));

			var businessDate= document.getElementById("businessdate").value;
			var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
		
			if(dobDate>dtBusiness)
				{
					alert("Date Of Birth cannot be greater than Business Date");
					document.getElementById("dateOfBirth").value='';
					document.getElementById("dateOfBirth").focus();
					DisButClass.prototype.EnbButMethod();
					document.getElementById("saveButton").removeAttribute("disabled","true");
					return false;
				}
			}
	
			var contextPath= document.getElementById("contextPath").value;
			document.getElementById("rateApprovalDetail").action=contextPath+"/rateApprovalMaker.do?method=saveRateApprovalData";
			document.getElementById("rateApprovalDetail").submit();
			return true;
		}
			
	}
	else{
		if(creditInsuranceCvr.value != 'N' || creditInsuranceCvr.value == '')
		{
		var formatD=document.getElementById("formatD").value;
		var dobDate=getDateObject(dob.value,formatD.substring(2, 3));

		var businessDate= document.getElementById("businessdate").value;
		var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	
		if(dobDate>dtBusiness)
			{
				alert("Date Of Birth cannot be greater than Business Date");
				document.getElementById("dateOfBirth").value='';
				document.getElementById("dateOfBirth").focus();
				DisButClass.prototype.EnbButMethod();
				document.getElementById("saveButton").removeAttribute("disabled","true");
				return false;
			}
		}


		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("rateApprovalDetail").action=contextPath+"/rateApprovalMaker.do?method=saveRateApprovalData";
		document.getElementById("rateApprovalDetail").submit();
		return true;
	
	}
	
}

function saveRateApprovalAuthor()
{
	DisButClass.prototype.DisButMethod();
	//alert("save Author");
	if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
	{
		alert("Please Select the required field ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}

	else
	{
		var rackRate=document.getElementById("rackRate").value;
		var flatRate=document.getElementById("flatRate").value;
		if(document.getElementById("decision").value=="A")
		{
			if(rackRate=='')
				rackRate='0';
			if(flatRate=='')
				flatRate='0';
			
			if(parseFloat(flatRate)<parseFloat(rackRate))
			{
				alert("Rate is below Rack Rate.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("Save").removeAttribute("disabled","true");
				return false;
			}
		}
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("rateApprovalAuthor").action = contextPath+"/rateApprovalChecker.do?method=saveRateApprovalAuthor";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("rateApprovalAuthor").submit();
	    return true;
	}
}

function searchrateApprovalAuthor()
{
	var dealID=document.getElementById("dealId").value;
	var customerName=document.getElementById("customerName").value;
	if(dealID=='' && customerName=='')
	{
		alert('Please Enter atleast one field');
		return false;
	}
	else
	{
		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("rateApprovalChecker").action=contextPath+"/rateApprovalChecker.do?method=defaultRateChecker";
		document.getElementById("rateApprovalChecker").submit();
	}
}

function disableFieldForLifeInsurance()
{
	var creditInsuranceCover=document.getElementById("creditInsuranceCover").value;
	if(creditInsuranceCover=='N')
	{
		document.getElementById("typeOfCoverage").setAttribute("disabled",true);
		document.getElementById("typeOfCoverage").value='';
		document.getElementById("newDataBirth").style.display='';
		document.getElementById("dataBirth").style.display='none';
		document.getElementById("sumAsurLoanAmt").setAttribute("disabled",true);
		document.getElementById("sumAsurLoanAmt").value='';
		document.getElementById("creditInsuranceTenor").setAttribute("disabled",true);
		document.getElementById("creditInsuranceTenor").value='';
		document.getElementById("csliPremiumAmt").setAttribute("disabled",true);
		document.getElementById("csliPremiumAmt").value='';
	}
	else
	{
		document.getElementById("typeOfCoverage").removeAttribute("disabled",true);
		document.getElementById("newDataBirth").style.display='none';
		document.getElementById("dataBirth").style.display='';
		document.getElementById("sumAsurLoanAmt").removeAttribute("disabled",true);
		document.getElementById("creditInsuranceTenor").removeAttribute("disabled",true);
		document.getElementById("csliPremiumAmt").removeAttribute("disabled",true);
		document.getElementById("dateOfBirth").value='';
	}		
}

function disableFieldForGeneralInsurance()
{
	var generalInsurance=document.getElementById("generalInsurance").value;
	if(generalInsurance=='N')
	{
		document.getElementById("csgiPremiumAmt").setAttribute("disabled",true);
		document.getElementById("csgiPremiumAmt").value='';		
	}
	else
	{
		document.getElementById("csgiPremiumAmt").removeAttribute("disabled",true);
	}
}

function disableFieldForOtherInsurance()
{
	var otherInsuranceSold=document.getElementById("otherInsuranceSold").value;	
	if(otherInsuranceSold=='N')
	{
		document.getElementById("productSold").setAttribute("disabled",true);
		document.getElementById("productSold").value='';
		document.getElementById("csoiPremiumAmt").setAttribute("disabled",true);
		document.getElementById("csoiPremiumAmt").value='';
		if(document.getElementById("assetMachine").value=='MACHINE')
		{
			document.getElementById("transactionFee").setAttribute("disabled",true);
			document.getElementById("transactionFee").value='';
		}
	}
	else
	{
		document.getElementById("productSold").removeAttribute("disabled",true);
		document.getElementById("csoiPremiumAmt").removeAttribute("disabled",true);
		if(document.getElementById("assetMachine").value=='MACHINE')
		{
			document.getElementById("transactionFee").removeAttribute("disabled",true);
		}
	}
		
}

function checkRateForRateApproval()

{
	var rate = document.getElementById("ifBt").value;
	 
	if(rate=='')
	{
		rate=0;	
	}
	    var intRate = parseFloat(rate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }

	        else
	           {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById("ifBt").value="";
		        return false;
	         }
	
}

function disableFieldForIsBalanceTransfer()
{
	var isThisABalanceTransfer=document.getElementById("isThisABalanceTransfer").value;	
	if(isThisABalanceTransfer=='Y')
	{
		document.getElementById("ifBt").removeAttribute("readonly",true);
		document.getElementById("ifBt").value='';

	}
	else
	{
		document.getElementById("ifBt").setAttribute("readonly",true);
		document.getElementById("ifBt").value='';
		
	}
		
}
function formatValue()
{
	var assetProperty=document.getElementById("assetProperty").value;
	if(assetProperty=='PROPERTY')
	{
		var payoutApplication=document.getElementById("payoutApplication").value;
		formatNumber(payoutApplication,'payoutApplication');
		var payoutBeing=document.getElementById("payoutBeing").value;
		formatNumber(payoutBeing,'payoutBeing');
	}
}
