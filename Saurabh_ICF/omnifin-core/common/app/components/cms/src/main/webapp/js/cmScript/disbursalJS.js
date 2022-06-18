$(function() {
	$("#repayEffDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});


function newDisbursal()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=openNewDisbursal";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("disbursalSearchForm").submit();
}
/*Added By arun For New Disbursal with payment starts here*/
function newDisbursalPayment()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=openNewDisbursalWithPayment";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("disbursalSearchForm").submit();
}
/*Added By arun For New Disbursal with payment Ends here*/
function searchDisbursal(type)
{
	//alert(type);
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("customerName").value;
	var loanAmt = document.getElementById("loanAmt").value;
	var loanApprovalDate = document.getElementById("loanApprovalDate").value;
	var product = document.getElementById("product").value;
	var scheme = document.getElementById("scheme").value;
	var scheme = document.getElementById("reportingToUserId").value;
	var contextPath=document.getElementById("contextPath").value;
	
	if(type=='P')
	{
		if(document.getElementById("loanNo").value!='' || document.getElementById("customerName").value!='' || document.getElementById("loanAmt").value!='' 
			|| document.getElementById("loanApprovalDate").value!='' || document.getElementById("product").value!='' || document.getElementById("scheme").value!=''
				|| document.getElementById("reportingToUserId").value!='')
		{
			if(document.getElementById("loanNo").value!='' && document.getElementById("loanNo").value.length>=3)
			{
				 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("disbursalSearchForm").submit();
				return true;
			}else if(document.getElementById("loanNo").value =='')
			{
				 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("disbursalSearchForm").submit();
				return true;
			}
	
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				//document.getElementById("search").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}else
		{
			alert("Please Enter atleast one field");
			//document.getElementById("search").removeAttribute("disabled", "true");
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
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else if(document.getElementById("loanNo").value!='' || document.getElementById("customerName").value!='' || document.getElementById("loanAmt").value!='' 
			|| document.getElementById("loanApprovalDate").value!='' || document.getElementById("product").value!='' || document.getElementById("scheme").value!=''
				|| allBranches!=false || selectedBranch!='' || document.getElementById("reportingToUserId").value!='')
		{
			if(document.getElementById("loanNo").value!='' && document.getElementById("loanNo").value.length>=3)
			{
				 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("disbursalSearchForm").submit();
				return true;
			}else if(document.getElementById("loanNo").value =='')
			{
				 document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=searchDisbursal&type="+type;
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("disbursalSearchForm").submit();
				return true;
			}
	
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				//document.getElementById("search").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}else
		{
			alert("Please Enter atleast one field or select all branches");
			//document.getElementById("search").removeAttribute("disabled", "true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
}


function hideRepayDate()
{
	if(document.getElementById("finalDisbursal").checked==false)
	{
		document.getElementById("repayId").style.display="";
		document.getElementById("repayEffId").style.display="none";
	}		
	else
	{
		document.getElementById("repayId").style.display="none";
		document.getElementById("repayEffId").style.display="";
	}
	var val=document.getElementById('disbursalTo').value;
	if(val=='CS')
	{
		document.getElementById("customerLableDiv").style.display='';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
	}
	if(val=='SU')
	{
		document.getElementById("customerLableDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
	}
	if(val=='MF')
	{
		document.getElementById("customerLableDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='';
		document.getElementById("otherLableDiv").style.display='none';
	}
}
function saveDisbursalData(loanIDDisbursal)
{
	/*calculateEMI();*/
	DisButClass.prototype.DisButMethod();
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var disbursalDate= document.getElementById("disbursalDate").value;
	var maxDisbursalDate= document.getElementById("maxDisbursalDate").value;
	
	var disbursalDateObj="";
	var maxDisbursalDateObj="";
	
	if(loanId=="")
	{
		alert("First select Loan Number.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var repayMode = document.getElementById("repayMode").value;
	var check=document.getElementById("val").value;	
	var clickFlag=document.getElementById('EMIFlag').value;
	//alert("repayMode   : "+repayMode);
	//alert("loanId      : "+loanId);
	//alert("check       : "+check);
	//alert("clickFlag   : "+clickFlag);
	//if(repayMode=="I" && loanId !='' && check =='N'&& clickFlag=='F')
//	if(clickFlag=='F' && repayMode=='I')
//	{
//		alert("First calculate PRE-EMI.");
//		document.getElementById("save").removeAttribute("disabled","true");
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
	
	//ravi
	var expPayDtObj="";
	var expectedPaymentDate = document.getElementById("expectedPmntDt").value;

	var businessDateTemp=document.getElementById("businessDate").value;
	var formatDtemp=document.getElementById("formatD").value;
	var busDtObjtemp=getDateObject(businessDateTemp,formatDtemp.substring(2,3));
	//Arun
	var formatD=document.getElementById("formatD").value;
	var actualDate=document.getElementById("disbursalDate").value;
	var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
	if(maxDisbursalDate!="" && maxDisbursalDate!=null){
		maxDisbursalDateObj=getDateObject(maxDisbursalDate,formatD.substring(2, 3));
	}
	//Arun
	
	var maxExpectedPayDate = document.getElementById("maxExpectedPayDate").value;
	var maxExpPayDtObj=getDateObject(maxExpectedPayDate,formatDtemp.substring(2,3));
	if(repayMode=='I')
	{
		expectedPaymentDate=document.getElementById("expectedPmntDtIns").value;
		expPayDtObj=getDateObject(expectedPaymentDate,formatDtemp.substring(2,3));
		document.getElementById("expectedPmntDt").setAttribute("disabled","true");
		
		if(expectedPaymentDate=='')
		{
			alert("Please enter Expected Payment Date.");
			document.getElementById("expectedPmntDtIns").focus();
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//alert("repEfDtObj : "+repEfDtObj);
		//alert("busDtObj : "+busDtObj);
		if(expPayDtObj<busDtObjtemp)
		{
			alert("Expected Payment Date can't be less than Business Date.");
			document.getElementById("expectedPmntDtIns").focus();
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		//alert("expPayDtObj : "+expPayDtObj);
		//alert("maxExpPayDtObj : "+maxExpPayDtObj);
		
		if(expPayDtObj<maxExpPayDtObj)
		{
			alert("Expected Payment Date should be greater than previous disbursal expected payment date"+maxExpPayDtObj);
			document.getElementById("expectedPmntDtIns").focus();
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		document.getElementById("expectedPmntDt").removeAttribute("disabled","true");
	}
	
	if(check=="Y")
	{
		var nextDueDate=document.getElementById("nextDueDate").value;
		if(nextDueDate=="")
		{
			alert("Please enter Next Due Date");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var businessDate=document.getElementById("businessDate").value;
		var repayEffDate=document.getElementById("repayEffDate").value;
		if(repayMode=='N')
		{
			repayEffDate=document.getElementById("repayEffBusDate").value;
			document.getElementById("repayEffDate").setAttribute("disabled","true");
		}
		else
		{
			document.getElementById("repayEffDate").removeAttribute("disabled","true");
		}
		if(repayEffDate=='')
		{
			alert("Please enter Repay Effective Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var formatD=document.getElementById("formatD").value;
		var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
		var repEfDtObj=getDateObject(repayEffDate,formatD.substring(2,3));
		var nextDuDtObj=getDateObject(nextDueDate,formatD.substring(2,3));
		//alert("repEfDtObj : "+repEfDtObj);
		//alert("busDtObj : "+busDtObj);
		if(repEfDtObj<busDtObj)
		{
			alert("Repay Effective Date can't be less than Business Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(repEfDtObj>=nextDuDtObj)
		{
			alert("Next Due Date must be greater than Repay Effective Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		// Code By arun for Disbursal to be done on back date
		if(dtActual>nextDuDtObj)
		{
			alert("Next Due Date must be greater than Disbursal Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
				
	}
	document.getElementById("disbursalDescription").value=trim(document.getElementById("disbursalDescription").value);
	var des=document.getElementById("disbursalDescription").value;
	var len=des.length;
	if(len > 500)
	{
		alert("Maker Remark can not be more than 500 characters.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	//alert(actualDate);
	var businessDate= document.getElementById("businessDate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	//alert(businessDate);
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var dtLoanApproval = getDateObject(loanApprovalDate,formatD.substring(2, 3));
	var loanAmt = removeComma(document.getElementById("loanAmt").value);
	var disbursedAmt = removeComma(document.getElementById("disbursedAmount").value);
	var disbursalAmt = removeComma(document.getElementById("disbursalAmount").value);
	var shortPayDue = removeComma(document.getElementById("proposedShortPayAmount").value);
	var shortPay = removeComma(document.getElementById("shortPayAmount").value);
	var pdcDepositCount= document.getElementById("pdcDepositCount").value;
	
	//alert("ok"+pdcDepositCount);
	
	if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
	{
		if(document.getElementById("pdcDepositCount").value==0)
		{
			var pdcDeposited=true;
			if(repayMode=='I')
			{
				pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
				DisButClass.prototype.DisButMethod();
			}
		
		if(pdcDeposited)
		{
			if(document.getElementById("finalDisbursal").checked==true )
			{
				
				
					var repayEffDate= document.getElementById("repayEffDate").value;
					var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
					//alert(repayEffDate);
					
						//alert("cycleDate: "+cycleDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
					/*Commented By Arun for --Disbursal to be done on back date
					 * 
					 * 	if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").focus();
								return false;
							}
							
						}
						if(expPayDtObj>dtRepay)
						{
							alert("Expected Payment Date should be less than RepayEff Date.");
							document.getElementById("expectedPmntDtIns").focus();
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("shortPayAmount").value='';
								document.getElementById("shortPayAmount").focus();
								return false;
							}
							else
							{ 
								var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								var disbursalTo=document.getElementById("disbursalTo").value;
								var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
								
								if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
								{
									alert("Please enter supplier");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								
								if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
								{
									alert("Please enter manufacturer");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
						}
						else
						{	var sum=disbursedAmt+disbursalAmt;
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}

							if(confirm("Loan Amt is "+sum+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
						}
				
				
			}
			else
			{
				//alert("In Else");
				
					var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
					var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
					var loanAmt = removeComma(document.getElementById("loanAmt").value);
					var repayMode = document.getElementById("repayMode").value;
		     	//alert("alert disbursalAmount: "+disbursalAmount);
				//alert("alert disbursedAmount: "+disbursedAmount);
				//alert("alert loanAmt: "+loanAmt);
				//alert("alert repayMode: "+repayMode);
					if(repayMode=="I")
					{
						
						if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
						{
							alert("Please Check Final Disbursal check box");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
						if((disbursalAmount+disbursedAmount)>loanAmt)
						{
							
							alert("Disbursal Amount Can not more than "+(loanAmt-disbursedAmount));
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
					}
					if(document.getElementById("finalDisbursal").checked==true)
					{
						
						
							var repayEffDate= document.getElementById("repayEffDate").value;
							var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
							//alert(repayEffDate);
							if(repayEffDate=='')
							{
								alert("Repay Effective Date is Required");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								return false;
							}
						/*	Commented By Arun for --Disbursal to be done on back date 
						 * 
						 * if(dtActual<dtBusiness)
							{
								alert("Disbursal Date cannot be less than Business Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}*/
							if(dtActual>dtBusiness)
							{
								alert("Disbursal Date cannot be greater than Business Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtActual<dtLoanApproval)
							{
								alert("Disbursal Date cannot be less than Loan Approval Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								return false;
							}
							if(dtRepay<dtActual)
							{
								alert("Repayment Effective Date cannot be smaller than Disbursal Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").value='';
								document.getElementById("repayEffDate").focus();
								return false;
							}
							//Arun 
							if(maxDisbursalDateObj!=""){
								if(maxDisbursalDateObj>dtActual)
								{
									alert(" Disbursal Date should be greater than last disbursal Date");
									DisButClass.prototype.EnbButMethod();
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("repayEffDate").focus();
									return false;
								}
								
							}
							//Arun
							if(disbursedAmt == 0 && disbursalAmt == 0)
							{
								alert("Cannot Finalize Disbursal with Zero amount");
								DisButClass.prototype.EnbButMethod();
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
									DisButClass.prototype.EnbButMethod();
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("shortPayAmount").value='';
									document.getElementById("shortPayAmount").focus();
									return false;
								}
								else
								{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
										var disbursalTo=document.getElementById("disbursalTo").value;
								var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
								
								if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
								{
									alert("Please enter supplier");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								
								if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
								{
									alert("Please enter manufacturer");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
									if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
									{
										var contextPath=document.getElementById("contextPath").value;
										document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
										document.getElementById("processingImage").style.display = '';
										document.getElementById("disbursalMakerForm").submit();
									}
									else
									{
										document.getElementById("save").removeAttribute("disabled","true");
										DisButClass.prototype.EnbButMethod();
										return false;
									}
								}
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
									var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
												
					}
					else
					{
					/*Commented by arun for---
					 * 
					 * 	if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").focus();
								return false;
							}
							
						}
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Disbursal Amount should be greater than Zero");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalAmount").value='';
							document.getElementById("disbursalAmount").focus();
							return false;
						}
						else
						{
							if(repayMode=='N')
							{
								if(repayMode=='N')
								{
									repayEffDate1=document.getElementById("repayEffBusDate").value;
									document.getElementById("repayEffDate").setAttribute("disabled","true");
								}
								else
								{
									document.getElementById("repayEffDate").removeAttribute("disabled","true");
								}
							if(repayEffDate1=='')
							{
								alert("Please enter Repay Effective Date.");
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							var formatD=document.getElementById("formatD").value;
							var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
							var repEfDtObj=getDateObject(repayEffDate1,formatD.substring(2,3));
							
							if(repayMode=="N")
							{
								if(repEfDtObj<busDtObj)
								{
									alert("Repay Effective Date can't be less than Business Date.");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("repayEffBusDate").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
							
							}
							
							
							//if(repayMode=="N")
							//{
								var disbursalTo=document.getElementById("disbursalTo").value;
								var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
								
								if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
								{
									alert("Please enter supplier");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								
								if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
								{
									alert("Please enter manufacturer");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							//}
							document.getElementById('EMIFlag').value="F";
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
							document.getElementById("processingImage").style.display = '';
							document.getElementById("disbursalMakerForm").submit();
						}
					}
			
			}
		}
		else
		{
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
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
				//alert(repayEffDate);
				
					//alert("cycleDate: "+cycleDate);
					if(repayEffDate=='')
					{
						alert("Repay Effective Date is Required");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					/*Commented by Arun For
					 * 
					 * if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}*/
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtRepay<dtActual)
					{
						alert("Repayment Effective Date cannot be smaller than Disbursal Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("repayEffDate").focus();
						return false;
					}
					//Arun
					if(maxDisbursalDateObj!=""){
						if(maxDisbursalDateObj>dtActual)
						{
							alert(" Disbursal Date should be greater than last disbursal Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						
					}
					//Arun
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Cannot Finalize Disbursal with Zero amount");
						DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("shortPayAmount").value='';
							document.getElementById("shortPayAmount").focus();
							return false;
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}

							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
					}
					else
					{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
					var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
					
					if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
					{
						alert("Please enter supplier");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("bpButton").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					
					if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
					{
						alert("Please enter manufacturer");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("bpButton").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
						if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
						{
							document.getElementById('EMIFlag').value="F";
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
							document.getElementById("processingImage").style.display = '';
							document.getElementById("disbursalMakerForm").submit();
						}
						else
						{
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					}
					
					
					
			
			
		}
		// if not final disbursal
		else
		{
			
			
				var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
				var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
				var loanAmt = removeComma(document.getElementById("loanAmt").value);
				var repayMode = document.getElementById("repayMode").value;
	//			alert("alert disbursalAmount: "+disbursalAmount);
	//			alert("alert disbursedAmount: "+disbursedAmount);
	//			alert("alert loanAmt: "+loanAmt);
	//			alert("alert repayMode: "+repayMode);
				if(repayMode=="I")
				{
					if((disbursalAmount+disbursedAmount) == loanAmt && document.getElementById("finalDisbursal").checked==false)
					{
						alert("Please Check Final Disbursal check box");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
					if((disbursalAmount+disbursedAmount)>loanAmt)
					{
						
						alert("Disbursal Amount Can not more than "+(loanAmt-disbursedAmount));
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						return false;
					}
				}
				if(document.getElementById("finalDisbursal").checked==true)
				{
					
					
						var repayEffDate= document.getElementById("repayEffDate").value;
						var dtRepay = getDateObject(repayEffDate,formatD.substring(2, 3));
						//alert(repayEffDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							return false;
						}
						/*if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtActual<dtLoanApproval)
						{
							alert("Disbursal Date cannot be less than Loan Approval Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							return false;
						}
						if(dtRepay<dtActual)
						{
							alert("Repayment Effective Date cannot be smaller than Disbursal Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").value='';
							document.getElementById("repayEffDate").focus();
							return false;
						}
						//Arun
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffDate").focus();
								return false;
							}
							
						}
						//Arun
						if(disbursedAmt == 0 && disbursalAmt == 0)
						{
							alert("Cannot Finalize Disbursal with Zero amount");
							DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("shortPayAmount").value='';
								document.getElementById("shortPayAmount").focus();
								return false;
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									document.getElementById('EMIFlag').value="F";
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
						
					
				}
				else
				{
					/*commented By arun for--
					 * 
					 * if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}*/
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(dtActual<dtLoanApproval)
					{
						alert("Disbursal Date cannot be less than Loan Approval Date");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						return false;
					}
					if(maxDisbursalDateObj!=""){
						if(maxDisbursalDateObj>dtActual)
						{
							alert(" Disbursal Date should be greater than last disbursal Date");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("repayEffDate").focus();
							return false;
						}
						
					}
					if(disbursedAmt == 0 && disbursalAmt == 0)
					{
						alert("Disbursal Amount should be greater than Zero");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("disbursalAmount").value='';
						document.getElementById("disbursalAmount").focus();
						return false;
					}
					else
					{
						if(repayMode=='N')
						{
							repayEffDate1=document.getElementById("repayEffBusDate").value;
							document.getElementById("repayEffDate").setAttribute("disabled","true");
						}
						else
						{
							document.getElementById("repayEffDate").removeAttribute("disabled","true");
						}
						if(repayMode=="N")
						{
							if(repayEffDate1=='')
							{
								alert("Please enter Repay Effective Date.");
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							var formatD=document.getElementById("formatD").value;
							var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
							var repEfDtObj=getDateObject(repayEffDate1,formatD.substring(2,3));
						
						
							if(repEfDtObj<busDtObj)
							{
								alert("Repay Effective Date can't be less than Business Date.");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffBusDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
						
						//if(repayMode=="N")
						//{
							var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						//}	
						document.getElementById('EMIFlag').value="F";
						var contextPath=document.getElementById("contextPath").value;
						document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalData&loanIDDisbursal="+loanIDDisbursal+"&chk=F&nextDueDate="+nextDueDate;
						document.getElementById("processingImage").style.display = '';
						document.getElementById("disbursalMakerForm").submit();
					}
				}
		
		}
	}
}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function updateDisbursalBeforeSave()
{
	DisButClass.prototype.DisButMethod();
	alert("Please save the data First");
	DisButClass.prototype.EnbButMethod();
	return false;
}


function callRepayBeforeSave(alert1)
{
	alert(alert1);
}
function openDisbursalSchedule(alert1)
{
	//alert("Open Disbursal Schedule");
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var table = document.getElementById("listTable");	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
    }else{
    	loanId=document.getElementById("lbxLoanNoHID").value;
    }
	if (loanId=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/disbursalSearchBehind.do?method=openDisbursalSchedule&lbxloannohid="+loanId;
		//alert("url: "+url);
		//window.open(url,'Disbursal Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		
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
function viewReceivableDisbursal(alert1) 
{	
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var table = document.getElementById("listTable");	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
    }else
    {
    	loanId=document.getElementById("lbxLoanNoHID").value;
    }
	if (loanId=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		//alert("loanId: "+loanId);
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url= contextPath+"/viewReceivableDisbursalAction.do?loanId="+loanId;
		//alert("url: "+url);
		//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
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

function updateDisbursalData(recStatus)
{	
	//if(recStatus=='P'){
	/*calculateEMI();*/
	//}
	//alert("checked or not:---"+document.getElementById("finalDisbursal").checked);
	DisButClass.prototype.DisButMethod();
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var disbursalDate= document.getElementById("disbursalDate").value;
   var maxDisbursalDate= document.getElementById("maxDisbursalDate").value;
	
	var disbursalDateObj="";
	var maxDisbursalDateObj="";
	//var repayMode = document.getElementById("repayMode").value;
	//if(repayMode=="I" )
	//{
		var check=document.getElementById("val").value;	
		var clickFlag=document.getElementById('EMIFlag').value;
		//alert("check      : "+check);
		//alert("clickFlag  : "+clickFlag);
		//if(check =='N'&& clickFlag=='F')
		var repayMode=document.getElementById("repayMode").value;	
//		if(clickFlag=='F' && repayMode=='I')
//		{
//			alert("First calculate PRE-EMI.");
//			if(recStatus=="P")
//				document.getElementById("save").removeAttribute("disabled","true");
//			if(recStatus=="F")
//				document.getElementById("saveFwd").removeAttribute("disabled","true");
//			DisButClass.prototype.EnbButMethod();
//			return false;
//		}
	//}	
	//var check =document.getElementById("val").value;
		
		if(repayMode=='N')
		{
			repayEffDate=document.getElementById("repayEffBusDate").value;
			//document.getElementById("repayEffDate").setAttribute("disabled","true");
			
			//alert("repayEffBusDate "+document.getElementById("repayEffBusDate").value);
		}
		var expectedPaymentDate = "";//document.getElementById("expectedPmntDt").value;
		var expPayDtObj="";
		
		/*if(repayMode=='I')
		{
			expectedPaymentDate=document.getElementById("expectedPmntDtIns").value;
			//document.getElementById("expectedPmntDt").setAttribute("disabled","true");
			
			
			if(expectedPaymentDate=='')
			{
				alert("Please enter Expected Payment Date.");
				document.getElementById("expectedPmntDtIns").focus();
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			var businessDateTemp=document.getElementById("businessDate").value;
			var formatDtemp=document.getElementById("formatD").value;
			var busDtObjtemp=getDateObject(businessDateTemp,formatDtemp.substring(2,3));
			expPayDtObj=getDateObject(expectedPaymentDate,formatDtemp.substring(2,3));
			var maxExpectedPayDate = document.getElementById("maxExpectedPayDate").value;
			var maxExpPayDtObj=getDateObject(maxExpectedPayDate,formatDtemp.substring(2,3));
			
			if(expPayDtObj<busDtObjtemp)
			{
				alert("Expected Payment Date can't be less than Business Date.");
				document.getElementById("expectedPmntDtIns").focus();
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			if(expPayDtObj<maxExpPayDtObj)
			{
				alert("Expected Payment Date should be greater than previous disbursal expected payment date"+maxExpPayDtObj);
				document.getElementById("expectedPmntDtIns").focus();
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}*/
//		else
//		{
//			document.getElementById("expectedPmntDt").removeAttribute("disabled","true");
//		} 
		//Arun
		var formatD=document.getElementById("formatD").value;
		var actualDate=document.getElementById("disbursalDate").value;
		var dtActual=getDateObject(actualDate,formatD.substring(2, 3));
		if(maxDisbursalDate!="" && maxDisbursalDate!=null){
			maxDisbursalDateObj=getDateObject(maxDisbursalDate,formatD.substring(2, 3));
		}
		//Arun	
	if(check=="Y")
	{
		var nextDueDate =document.getElementById("nextDueDate").value;
		if(nextDueDate=='')
		{
			alert("Please enter Next Due Date");
			if(recStatus=="P")
				document.getElementById("save").removeAttribute("disabled","true");
			if(recStatus=="F")
				document.getElementById("saveFwd").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var businessDate=document.getElementById("businessDate").value;
		var repayEffDate=document.getElementById("repayEffDate").value;
		if(repayEffDate=='')
		{
			alert("Please enter Repay Effective Date.");
			if(recStatus=="P")
				document.getElementById("save").removeAttribute("disabled","true");
			if(recStatus=="F")
				document.getElementById("saveFwd").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var formatD=document.getElementById("formatD").value;
		var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
		var repEfDtObj=getDateObject(repayEffDate,formatD.substring(2,3));
		if(repEfDtObj<busDtObj)
		{
			alert("Repay Effective Date can't be less than Business Date.");
			if(recStatus=="P")
				document.getElementById("save").removeAttribute("disabled","true");
			if(recStatus=="F")
				document.getElementById("saveFwd").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var nextDuDtObj=getDateObject(nextDueDate,formatD.substring(2,3));
		if(repEfDtObj>=nextDuDtObj)
		{
			alert("Next Due Date must be greater than Repay Effective Date.");
			if(recStatus=="P")
				document.getElementById("save").removeAttribute("disabled","true");
			if(recStatus=="F")
				document.getElementById("saveFwd").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		// Code By arun for Disbursal to be done on back date
		if(dtActual>nextDuDtObj)
		{
			alert("Next Due Date must be greater than Disbursal Date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	document.getElementById("disbursalDescription").value=trim(document.getElementById("disbursalDescription").value);
	var des=document.getElementById("disbursalDescription").value;
	var len=des.length;
	if(len > 500)
	{
		alert("Maker Remark can not be more than 500 characters.");
		if(recStatus=="P")
			document.getElementById("save").removeAttribute("disabled","true");
		if(recStatus=="F")
			document.getElementById("saveFwd").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

	var businessDate= document.getElementById("businessDate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var dtLoanApproval = getDateObject(loanApprovalDate,formatD.substring(2, 3));
	var disbursedAmt = removeComma(document.getElementById("disbursedAmount").value);
	var loanAmt = removeComma(document.getElementById("loanAmt").value);
	var disbursalAmt = removeComma(document.getElementById("disbursalAmount").value);
	
	if(document.getElementById("pdcDepositCount").value==0)
	{
		var pdcDeposited=true;
		if(repayMode=='I')
		{
			pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
			DisButClass.prototype.DisButMethod();
		}
	//	var pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						/*Commented by arun
						 * 
						 * if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
						}
						if(expPayDtObj>dtRepay)
						{
							alert("Expected Payment Date should be less than RepayEff Date.");
							document.getElementById("expectedPmntDtIns").focus();
							document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							else
							{
								var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									document.getElementById('EMIFlag').value="F";
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						/*commented by arun
						 * 
						 * 	if(dtActual<dtBusiness)
							{
								alert("Disbursal Date cannot be less than Business Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}*/
							if(dtActual>dtBusiness)
							{
								alert("Disbursal Date cannot be greater than Business Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							if(maxDisbursalDateObj!=""){
								if(maxDisbursalDateObj>dtActual)
								{
									alert(" Disbursal Date should be greater than last disbursal Date");
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									document.getElementById("disbursalDate").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								
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
								DisButClass.prototype.EnbButMethod();
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
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								else
								{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
									
									var disbursalTo=document.getElementById("disbursalTo").value;
										var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
										
										if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
										{
											alert("Please enter supplier");
											document.getElementById("save").removeAttribute("disabled","true");
											document.getElementById("bpButton").focus();
											DisButClass.prototype.EnbButMethod();
											return false;
										}
										
										if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
										{
											alert("Please enter manufacturer");
											document.getElementById("save").removeAttribute("disabled","true");
											document.getElementById("bpButton").focus();
											DisButClass.prototype.EnbButMethod();
											return false;
										}

									if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+disbursedAmt+" and this loan has marked for fully disbursal, are you sure to save this information? "))
									{
										document.getElementById('EMIFlag').value="F";
										var contextPath=document.getElementById("contextPath").value;
										document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
										document.getElementById("processingImage").style.display = '';
										document.getElementById("disbursalMakerForm").submit();
									}
									else
									{
										if(recStatus=="P")
											document.getElementById("save").removeAttribute("disabled","true");
										if(recStatus=="F")
											document.getElementById("saveFwd").removeAttribute("disabled","true");
										DisButClass.prototype.EnbButMethod();
										return false;
									}
								}
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									document.getElementById('EMIFlag').value="F";
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
							
						
					}
					else
					{
						/*Commented By arun
						 * 
						 * if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						else
						{
							//alert("repayEffDate : "+repayEffDate);
							if(repayMode=='N')
							{
								repayEffDate1=document.getElementById("repayEffBusDate").value;
								//document.getElementById("repayEffDate").setAttribute("disabled","true");
							}
//							else
//							{
//								document.getElementById("repayEffDate").removeAttribute("disabled","true");
//							}
							if(repayMode=="N")
							{
								if(repayEffDate1=='')
								{
									alert("Please enter Repay Effective Date.");
									document.getElementById("save").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								var formatD=document.getElementById("formatD").value;
								var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
								var repEfDtObj=getDateObject(repayEffDate1,formatD.substring(2,3));
								
								
							
								if(repEfDtObj<busDtObj)
								{
									alert("Repay Effective Date can't be less than Business Date.");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("repayEffBusDate").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
							
							//if(repayMode=="N")
							//{
								var disbursalTo=document.getElementById("disbursalTo").value;
								var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
								
								if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
								{
									alert("Please enter supplier");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
								
								if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
								{
									alert("Please enter manufacturer");
									document.getElementById("save").removeAttribute("disabled","true");
									document.getElementById("bpButton").focus();
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							//}
							document.getElementById('EMIFlag').value="F";
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
							document.getElementById("processingImage").style.display = '';
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
					DisButClass.prototype.EnbButMethod();
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
			DisButClass.prototype.EnbButMethod();
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
				//alert(repayEffDate);
				if(validateDisbursedInitiationMakerDynaValidatorForm(document.getElementById("disbursalMakerForm")))
				{
					if(repayEffDate=='')
					{
						alert("Repay Effective Date is Required");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				/*Commented By arun
				 * 
				 * 	if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}*/
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					if(maxDisbursalDateObj!=""){
						if(maxDisbursalDateObj>dtActual)
						{
							alert(" Disbursal Date should be greater than last disbursal Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
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
						DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}

							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
					}
					else
					{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
						var disbursalTo=document.getElementById("disbursalTo").value;
					var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
					
					if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
					{
						alert("Please enter supplier");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("bpButton").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					
					if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
					{
						alert("Please enter manufacturer");
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("bpButton").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}

						if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
						{
							document.getElementById('EMIFlag').value="F";
							var contextPath=document.getElementById("contextPath").value;
							document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
							document.getElementById("processingImage").style.display = '';
							document.getElementById("disbursalMakerForm").submit();
						}
						else
						{
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
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
					DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
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
						//alert(repayEffDate);
						if(repayEffDate=='')
						{
							alert("Repay Effective Date is Required");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						/*Commented By arun
						 * 
						 * if(dtActual<dtBusiness)
						{
							alert("Disbursal Date cannot be less than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}*/
						if(dtActual>dtBusiness)
						{
							alert("Disbursal Date cannot be greater than Business Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(maxDisbursalDateObj!=""){
							if(maxDisbursalDateObj>dtActual)
							{
								alert(" Disbursal Date should be greater than last disbursal Date");
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								document.getElementById("disbursalDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
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
							DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							else
							{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
								var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
								if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
								{
									document.getElementById('EMIFlag').value="F";
									var contextPath=document.getElementById("contextPath").value;
									document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
									document.getElementById("processingImage").style.display = '';
									document.getElementById("disbursalMakerForm").submit();
								}
								else
								{
									if(recStatus=="P")
										document.getElementById("save").removeAttribute("disabled","true");
									if(recStatus=="F")
										document.getElementById("saveFwd").removeAttribute("disabled","true");
									DisButClass.prototype.EnbButMethod();
									return false;
								}
							}
						}
						else
						{var sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt);
							var disbursalTo=document.getElementById("disbursalTo").value;
						var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
						
						if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
						{
							alert("Please enter supplier");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
						{
							alert("Please enter manufacturer");
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("bpButton").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
							if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
							{
								document.getElementById('EMIFlag').value="F";
								var contextPath=document.getElementById("contextPath").value;
								document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
								document.getElementById("processingImage").style.display = '';
								document.getElementById("disbursalMakerForm").submit();
							}
							else
							{
								if(recStatus=="P")
									document.getElementById("save").removeAttribute("disabled","true");
								if(recStatus=="F")
									document.getElementById("saveFwd").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}											
					
				}
				else
				{
					/*if(dtActual<dtBusiness)
					{
						alert("Disbursal Date cannot be less than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}*/
					if(dtActual>dtBusiness)
					{
						alert("Disbursal Date cannot be greater than Business Date");
						if(recStatus=="P")
							document.getElementById("save").removeAttribute("disabled","true");
						if(recStatus=="F")
							document.getElementById("saveFwd").removeAttribute("disabled","true");
						document.getElementById("disbursalDate").focus();
						DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					if(maxDisbursalDateObj!=""){
						if(maxDisbursalDateObj>dtActual)
						{
							alert(" Disbursal Date should be greater than last disbursal Date");
							if(recStatus=="P")
								document.getElementById("save").removeAttribute("disabled","true");
							if(recStatus=="F")
								document.getElementById("saveFwd").removeAttribute("disabled","true");
							document.getElementById("disbursalDate").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
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
						DisButClass.prototype.EnbButMethod();
						return false;
					}
					else
					{
						if(repayMode=='N')
						{
							repayEffDate1=document.getElementById("repayEffBusDate").value;
							//document.getElementById("repayEffDate").setAttribute("disabled","true");
						}
//						else
//						{
//							document.getElementById("repayEffDate").removeAttribute("disabled","true");
//						}
						if(repayMode=="N")
						{
							if(repayEffDate1=='')
							{
								alert("Please enter Repay Effective Date.");
								document.getElementById("save").removeAttribute("disabled","true");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							var formatD=document.getElementById("formatD").value;
							var busDtObj=getDateObject(businessDate,formatD.substring(2,3));
							var repEfDtObj=getDateObject(repayEffDate1,formatD.substring(2,3));
							
						
						
							if(repEfDtObj<busDtObj)
							{
								alert("Repay Effective Date can't be less than Business Date.");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("repayEffBusDate").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
						
						//if(repayMode=="N")
						//{
							var disbursalTo=document.getElementById("disbursalTo").value;
							var businessPartnerTypeDesc=document.getElementById("businessPartnerTypeDesc").value;
							
							if(disbursalTo=='SU' && businessPartnerTypeDesc=='')
							{
								alert("Please enter supplier");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
							
							if(disbursalTo=='MF' && businessPartnerTypeDesc=='')
							{
								alert("Please enter manufacturer");
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("bpButton").focus();
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						//}
						document.getElementById('EMIFlag').value="F";
						var contextPath=document.getElementById("contextPath").value;
						document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalData&recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;
						document.getElementById("processingImage").style.display = '';
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
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		}
	}
}

function callRepay()
{

	var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;
	var loanDisbursalId = document.getElementById("loanDisbursalId").value;
	var finalDisbursal="N";
	var repayMode = document.getElementById("repayMode").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var recoveryType=document.getElementById("recoveryType").value;
	var disbursalFlag=document.getElementById("disbursalFlag").value;
	if((lbxLoanNoHID != "") && (repaymentType != 'I' || recoveryType != 'F' || disbursalFlag != 'F'))
	{
		if(repaymentType != 'I')
		{
			alert("New Repayment Schedule is only for installment based loan.");
			return false;
		}
		if(disbursalFlag != 'F')
		{
			alert("Loan Should Be Final Disbursed.");
			return false;
		}
	}
	if(repayMode=='I'){
	if(lbxLoanNoHID!='')
	{
		
		if(repayMode=='N'){
			finalDisbursal="N";
		}else{
			if(document.getElementById("finalDisbursal").checked==true){
				finalDisbursal="F";
			}else{
				finalDisbursal="P";	
			}
		}
		var contextPath = document.getElementById("contextPath").value;
		//var url= contextPath+"/repaymentScheduleBehindAction.do?loanId="+lbxLoanNoHID+"&finalDisbursal="+finalDisbursal;
		var url= contextPath+"/disbursalMaker.do?method=generateRepaymentSch&loanId="+lbxLoanNoHID+"&finalDisbursal="+finalDisbursal+"&loanDisbursalId="+loanDisbursalId;
		//window.open(url,'Repayment Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
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
	}else{
		return false;
	}
}
function checkDueDate(san)
{
			var date = new Date();
			var formatD=document.getElementById("formatD").value;
	   		var dueDate = document.getElementById('hiddenDueDate').value;
	   		var str = document.getElementById('nextDueDate').value;
			var nMonth = str.substring(3,5); //months from 1-12
			var nDay = str.substring(0,2);
			var nYear = str.substring(6);
	   		//alert(str);
	   		var day=str.substring(0,2);
	   		//alert("day: "+day+" dueDate: "+dueDate);
			//Day handling for single digit
			var dueDateStr = '';
				if (dueDate <= 9) {
					dueDateStr = dueDate.toString();
					dueDate = (0).toString() + dueDateStr;
					// alert('iMonth......'+iMonth);
				}
			//Day handling End
	   		if (dueDate == '') 
				{
					alert("First select Due Day");
					document.getElementById('nextDueDate').value = '';
				}
							else
		   		{
		
	
		if(dueDate<=28)
		{
			if (day != dueDate)
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
			
				if((dueDate==29 ||dueDate==30 || dueDate==31) &&  day==29)
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
				if((dueDate==29 ||dueDate==30 || dueDate==31) &&  day==28)
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
				if (day != dueDate)
				{
				 alert("Next Due Date must be equal to Due Day ie. " + dueDate);
				 document.getElementById('nextDueDate').value = '';
				}
			}
			else if( dueDate==31 &&  day==30)
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
			if (day != dueDate)
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

/*//ajay starts
function checkInterestDueDate(san)
{
			var date = new Date();
	   		var dueDate = document.getElementById('hiddenDueDate').value;
	   		var str = document.getElementById('interestDueDate').value;
	   		//alert(str);
	   		var day=str.substring(0,2);
	   		//alert("day: "+day+" dueDate: "+dueDate);
	   		if(dueDate != '')
	   		{
	   			if(parseFloat(dueDate) != parseFloat(day))
	   			{
		   			alert("Interest Due Date must be equal to Due Day ie. "+dueDate);
		   			document.getElementById('interestDueDate').value = '';	   		
		   		}
		   		else
		   		{
		   			checkDate('interestDueDate');
		   		}
	   		
	   		}
	   		else
	   		{
	   			alert("First select Due Day");
	   			document.getElementById('interestDueDate').value = '';
	   		}		   		
}
//ajay end*/
	   
	   
	   function nullNextDue(val){
	   //alert("nullNextDue"+val);
	   			document.getElementById('hiddenDueDate').value = val;
				//document.getElementById('nextDueDate').value = '';
	   			var formatD=document.getElementById("formatD").value;
	   			
	   			var repayEffDate = document.getElementById("repayEffDate").value;
	   			var nextDueDtObj=getDateObject(repayEffDate,formatD.substring(2,3));
	   			
	   			var nextDueDate="";
	   			var repayMonth=nextDueDtObj.getMonth()+1;
	   			var repayDate = nextDueDtObj.getDate();
	   			var repayYear = nextDueDtObj.getFullYear();
	   			var nextDt="";
	   			 
	   			if(val=='')
	   			{
	   				document.getElementById("nextDueDate").value=nextDueDate;
	   			}
	   			else
	   			{
	   			if(val<10)
   				{
   					nextDt="0"+val;
   				}
   				else
   				{
   					nextDt=val;
   				}
	   			if(repayDate >= val )
	   			{
	   				
	   				if(repayMonth==12)
	   				{
	   					repayMonth="1";
	   					repayYear=repayYear+1;
	   				}
	   				else
	   				{
	   					repayMonth=repayMonth+1;
	   				}
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				
	   				nextDueDate=nextDt+"-"+repayMonth+"-"+repayYear;
	   				
	   				
	   			}
	   			else if(repayDate <= val )
	   			{
	   				
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				nextDueDate=nextDt+"-"+repayMonth+"-"+repayYear;
	   				
	   			}
	   			else
	   			{
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				nextDueDate=nextDt+"-"+repayMonth+"-"+repayYear;
	   			}
	   			
	   			document.getElementById("nextDueDate").value=nextDueDate;
	   			}
	   }
	/*   // ajay starts
	   function nullInterestDue(val){
	   //alert("InterestDue"+val);
	   			document.getElementById('hiddenDueDate').value = val;
				//document.getElementById('nextDueDate').value = '';
	   			var formatD=document.getElementById("formatD").value;
	   			
	   			var repayEffDate = document.getElementById("repayEffDate").value;
	   			var interestDueDtObj=getDateObject(repayEffDate,formatD.substring(2,3));
	   			
	   			
				var  interestDueDate="";
	   			var repayMonth=interestDueDtObj.getMonth()+1;
	   			var repayDate = interestDueDtObj.getDate();
	   			var repayYear = interestDueDtObj.getFullYear();
	   			var interestDt="";
	   			 
	   			if(val=='')
	   			{
	   				document.getElementById("interestDueDate").value=interestDueDate;
	   			}
	   			else
	   			{
	   			if(val<10)
   				{
   					interestDt="0"+val;
   				}
   				else
   				{
   					interestDt=val;
   				}
	   			if(repayDate >= val )
	   			{
	   				
	   				if(repayMonth==12)
	   				{
	   					repayMonth="1";
	   					repayYear=repayYear+1;
	   				}
	   				else
	   				{
	   					repayMonth=repayMonth+1;
	   				}
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				
	   				interestDueDate=interestDt+"-"+repayMonth+"-"+repayYear;
	   				
	   				
	   			}
	   			else if(repayDate <= val )
	   			{
	   				
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				interestDueDate=interestDt+"-"+repayMonth+"-"+repayYear;
	   				
	   			}
	   			else
	   			{
	   				if(repayMonth<10)
	   				{
	   					repayMonth="0"+repayMonth;
	   				}
	   				
	   				interestDueDate=interestDt+"-"+repayMonth+"-"+repayYear;
	   			}
	   			
	   			document.getElementById("interestDueDate").value=interestDueDate;
	   			}
	   }
	   //ajay end*/
	   function showEffectiveDate()
	   {
	   	//alert("In showEffectiveDate");
		document.getElementById('currentMonthEMI').value="";
		document.getElementById('preEMINextMonth').value="";
		document.getElementById('EMIFlag').value="F";
		var installmentType=document.getElementById("installmentType").value;
		var interestCalculationMethod=document.getElementById("interestCalculationMethod").value;
		var interestFrequency=document.getElementById("interestFrequency").value;
		var interestCompoundingFrequency=document.getElementById("interestCompoundingFrequency").value;
	
//		document.getElementById('nextDueDate').value ="";
	   	if(document.getElementById("finalDisbursal").checked)
	   	{
	   		//alert("In if showEffectiveDate");
			document.getElementById("tenureMonths").style.display="";
	   		document.getElementById("loanCurtail").removeAttribute("disabled", "true");
	   		document.getElementById("loanCurtail").checked=true;
	   		document.getElementById("val").value="Y";
	   		document.getElementById("yes1").style.display="";
	   		document.getElementById("no1").style.display="none";
	   		document.getElementById("yes2").style.display="";
	   		document.getElementById("no2").style.display="none";
	   		document.getElementById("yes3").style.display="";
	   		document.getElementById("no3").style.display="none";
	   		
	   		document.getElementById("Iyes2").style.display='';
	   		document.getElementById("Ino2").style.display="none";
	   		document.getElementById("interestDueDateDis").value=document.getElementById("hiddenIntDueDate").value;
			document.getElementById("repayEffId").style.display='';
			document.getElementById("repayEffDate").removeAttribute("disabled", "true");
	   		document.getElementById("repayId").style.display='none';
	   		document.getElementById("cycleDateFinal").style.display='';
	   	    document.getElementById("cycleDateI").style.display='none';
	   		document.getElementById("generateRepaymentSch").removeAttribute("disabled", "true");
				
	   		
	   		var contextPath=document.getElementById("contextPath").value;
	   		var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;
	   		var address = contextPath+"/ajaxAction.do?method=retriveCycleDate";
	   		var data = "lbxLoanNoHID="+lbxLoanNoHID;
	   	//	sendCycleDateId(address, data);
	   		return true;
	   	}
	   	else
	   	{
	   		//alert("In else showEffectiveDate");
			document.getElementById("tenureMonths").style.display="none";
	   		document.getElementById("loanCurtail").checked=false;
	   		document.getElementById("loanCurtail").setAttribute("disabled", "true");
	   		document.getElementById("val").value="N";
	   		document.getElementById("yes1").style.display="none";
	   		document.getElementById("no1").style.display="";
	   		document.getElementById("yes2").style.display="none";
	   		document.getElementById("no2").style.display="";
	   		document.getElementById("yes3").style.display="none";
			document.getElementById("no3").style.display="";
	   		
			document.getElementById("Iyes2").style.display="none";
	   		document.getElementById("Ino2").style.display='';
	   		document.getElementById("interestDueDateDis").value='';
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
	   	//alert("in sendCycleId id");
	   	var request = getRequestObject();
	   	request.onreadystatechange = function () {
	   		resultCycleDate(request);	
	   	};
	   	request.open("POST", address, true);
	   	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   	request.send(data);
	   	
	   }

	   function resultCycleDate(request){
	   	
	   	//alert("in resultDisbursal id");
	   	if ((request.readyState == 4) && (request.status == 200)) {
	   		var str = request.responseText;
	   			var s1 = str.split("$:");
	   			document.getElementById('cycleDateFinal').innerHTML=trim(s1[0]);
	   	}
	   }
	   
	   
	   function saveDisbursalAuthor()
	   {
		if(trim(document.getElementById("comments").value).length>500) 
		{
			alert("Number of character can't be greater than 500.");
			return false;
		}
		DisButClass.prototype.DisButMethod();
	   	var contextPath=document.getElementById("contextPath").value;
	   	if(document.getElementById("comments").value==''&& !(document.getElementById("decision").value=='A')) //Edited by Nishant
	   	{
	   		alert("Comments is Required");
	   		document.getElementById("save").removeAttribute("disabled","true");
	   		document.getElementById("comments").focus();
	   		DisButClass.prototype.EnbButMethod();
	   		return false;
	   	}
	   	//Changes By Amit Starts
    	if(document.getElementById("disbAllowParameter").value=="DisbursalAllowedWithAlert")
	   	{
	   		alert("Disbursal Author Date is greater than Disbursal Maker Date");
	   	}
	   	if(document.getElementById("disbAllowParameter").value=="DisbursalNotAllowed" && document.getElementById("decision").value=="A")
	   	{
	   		alert("Disbursal Author Date is not equal to Disbursal Maker Date. Disbursal Authorization not Allowed!!!");
	   		document.getElementById("save").removeAttribute("disabled","true");
	   		DisButClass.prototype.EnbButMethod();
   			return false;
	   	}
	   	//Changes By Amit Ends
	   	if(document.getElementById("disbursalFinalFlagForAuthor").value=="F" && document.getElementById("decision").value=="A")
	   	{
	   		if(confirm("This loan has been marked for Full Disbursal. Are you sure to approve this information? "))
	   		{
	   			
	   			document.getElementById("disbursalAuthorForm").action = contextPath+"/disbursedInitiationAuthor.do?method=saveDisbursalAuthor";
	   			document.getElementById("processingImage").style.display = '';
	   			document.getElementById("disbursalAuthorForm").submit();
	   		}
	   		else
	   		{
	   			document.getElementById("save").removeAttribute("disabled","true");
	   			DisButClass.prototype.EnbButMethod();
	   			return false;
	   		}
	   	}
	   	else
	   	{
	   		document.getElementById("disbursalAuthorForm").action = contextPath+"/disbursedInitiationAuthor.do?method=saveDisbursalAuthor";
	   		document.getElementById("processingImage").style.display = '';
	   		document.getElementById("disbursalAuthorForm").submit();
	   		
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
	   
	  	   
	 function checkRepayEffectiveDate(ndd)
	  {
	   	//alert("check");
	   	var formatD=document.getElementById("formatD").value;
	   //	var effectiveDate = document.getElementById("effectiveDate").value;
	   	//alert("effectiveDate: "+effectiveDate);
	   	var nextDueDate=getDateObject(ndd,formatD.substring(2, 3));
	   	//alert("dt2: "+dt2);
	       var repayD = document.getElementById("repayEffDate").value;
	       //alert("matDate: "+matDate);
	       var repayDate = getDateObject(repayD, formatD.substring(2, 3));
	       //alert("maturityDate: "+maturityDate);
	      // var closureType = document.getElementById("closureType").value;
	       //alert("closureType: "+closureType);
	    //   if(closureType=='C')
	   //	{
	   		if(nextDueDate<=repayDate)
	   		{
	   			 alert("Next Due Date must be greater than Repay Effective Date. ");
	   			 document.getElementById("nextDueDate").focus();
	   			 document.getElementById("nextDueDate").value='';
	   			 return false;
	   		}
	   //	}
	       else
	        	return true;
	   }
	 
//Neeraj calculate EMI
//ajay starts for Interest Due Date 
/*function checkRepayEffectiveDateForIDD(ndd)
	  {
	   	//alert("check");
	   	var formatD=document.getElementById("formatD").value;
	   //	var effectiveDate = document.getElementById("effectiveDate").value;
	   	//alert("effectiveDate: "+effectiveDate);
	   	var interestDueDate=getDateObject(ndd,formatD.substring(2, 3));
	   	//alert("dt2: "+dt2);
	       var repayD = document.getElementById("repayEffDate").value;
	       //alert("matDate: "+matDate);
	       var repayDate = getDateObject(repayD, formatD.substring(2, 3));
	       //alert("maturityDate: "+maturityDate);
	      // var closureType = document.getElementById("closureType").value;
	       //alert("closureType: "+closureType);
	    //   if(closureType=='C')
	   //	{
	   		if(interestDueDate<=repayDate)
	   		{
	   			 alert("Interest Due Date must be greater than Repay Effective Date. ");
	   			 document.getElementById("interestDueDate").focus();
	   			 document.getElementById("interestDueDate").value='';
	   			 return false;
	   		}
	   //	}
	       else
	        	return true;
	   }
	 
//ajay ends

*/
function calculateEMI()
{	
	DisButClass.prototype.DisButMethod();
	
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var disbursalAmount=document.getElementById('disbursalAmount').value;
	var disbursalDate=document.getElementById('disbursalDate').value;
	var repayMode=document.getElementById("repayMode").value;
	var finalDisbursal=document.getElementById("finalDisbursal").checked;
	var repayEffDate="";
	if(repayMode=='N')
		repayEffDate=document.getElementById("repayEffBusDate").value;
	else
		repayEffDate=document.getElementById('repayEffDate').value;
	var flag=document.getElementById('finalDisbursal').checked;
	//alert("flag  :  "+flag);
	if(flag)
		disbursalStatus="F";
	else
	{
		disbursalStatus="P";
		repayEffDate="";		
	}
	var msg1="";
	var msg2="";
	var msg3="";
	if(loanId =="")
		msg1="*Loan Number is required.\n";
	if(disbursalAmount =="" && !finalDisbursal)
		msg2="*Current Disbursal Amount is required.\n";
	if(disbursalDate =="")
		msg3="*Disbursal Date is required.";
	if(loanId =="" ||disbursalAmount ==""||disbursalDate =="")
	{
		alert(msg1+""+msg2+""+msg3);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var disbsalAmount = removeComma(disbursalAmount);
		var disbursedAmount = removeComma(document.getElementById("disbursedAmount").value);
		var loanAmt = removeComma(document.getElementById("loanAmt").value);
		var repayMode = document.getElementById("repayMode").value;
		if(repayMode=="I")
		{		
			var disAmt=parseFloat(disbsalAmount)+parseFloat(disbursedAmount);
			if((disbsalAmount+disbursedAmount) == loanAmt && flag==false)
			{
				alert("Please Check Final Disbursal check box");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}		
		var contextPath=document.getElementById("contextPath").value;
		var address = contextPath+"/ajaxAction.do?method=calculateEMI&lbxLoanNoHID="+loanId+"&disbursalAmount="+disbursalAmount+"&disbursalDate="+disbursalDate+"&repayEffDate="+repayEffDate+"&disbursalStatus="+disbursalStatus;
		var data = "lbxLoanNoHID="+loanId;
		calculateEMIID(address, data);
		DisButClass.prototype.EnbButMethod();
		return true;
			//}
	}
	DisButClass.prototype.EnbButMethod();
}


function calculateEMIID(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		calculateEMIFinal(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}


function calculateEMIFinal(request)
{
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		document.getElementById('currentMonthEMI').value=trim(s1[0]);  
		document.getElementById('preEMINextMonth').value=trim(s1[1]);	
		document.getElementById('EMIFlag').value="T";
	}
}


function changeDisbursAmount()
{
	var loanId=document.getElementById('lbxLoanNoHID').value;
	if(loanId =="")
	{
		alert("Firset select Loan Number.");
		return false;
	}
	else
	{
		//var repayMode = document.getElementById("repayMode").value;
		//if(repayMode=="I")
		//{
			//if(!document.getElementById("finalDisbursal").checked)
			//{
				document.getElementById('EMIFlag').value="F";
				document.getElementById('currentMonthEMI').value="";  
				document.getElementById('preEMINextMonth').value="";	
			//}
		//}
	}
}


function deletedisbursalDetails(){
	var sourcepath=document.getElementById("contextPath").value;
	var id=document.getElementById("loandisbursalid").value;
	agree=confirm("Are you sure,You want to delete this disbursal.Do you want to continue?");
	if (!(agree))
	{
    	document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}else{
		
		document.getElementById("disbursalMakerForm").action=sourcepath+"/disbursalSearch.do?method=deleteDisbursal&loandisbursalid"+id;
		document.getElementById("disbursalMakerForm").submit();
		return true;
}
}

function activeSupplier(val)
{
	//alert("val : "+val);
	if(val=='CS')
	{
		document.getElementById("supplierDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		
		document.getElementById("lbxBusinessPartnearHID").setAttribute("disable","true");
		document.getElementById("businessPartnerName").setAttribute("disable","true");
		document.getElementById("businessPartnerType").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").setAttribute("disable","true");
	}
	else if(val=='SU')
	{
		//alert("val su : "+val);
		document.getElementById("supplierDiv").style.display='';
		document.getElementById("supplierLableDiv").style.display='';
		document.getElementById("manufactLableDiv").style.display='none';
		
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		
		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
		document.getElementById("businessPartnerName").removeAttribute("disable","true");
		document.getElementById("businessPartnerType").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
		
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';
	}
	else
		
	{
		//alert("val mf : "+val);
		document.getElementById("supplierDiv").style.display='';
		document.getElementById("manufactLableDiv").style.display='';
		document.getElementById("supplierLableDiv").style.display='none';
		
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		
		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
		document.getElementById("businessPartnerName").removeAttribute("disable","true");
		document.getElementById("businessPartnerType").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';
	}
}	

function getShortPayOnDisbursalTo(val)
{
	//alert("val getShortPayOnDisbursalTo:"+val);
	 var loanId=document.getElementById('lbxLoanNoHID').value;
	
	 if(loanId!='')
	 {
		 //alert("loanId :"+loanId);
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=getShortPayOnDisbursalTo&disbursalTo="+val;
		 var data = "lbxLoanNoHID="+loanId;
		 sendDisbursalloanId(address, data);
		 return true;
	 }
	 
}

function sendDisbursalloanId(address, data) {
	//alert("in sendDisbursalloanId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDisbursalTo(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultDisbursalTo(request)
{
	
	var s1;
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		s1 = str.split("$:");
		
		document.getElementById('adjustedShortPayAmount').value=trim(s1[0]);
		document.getElementById('proposedShortPayAmount').value=trim(s1[1]);
		
		}

}

function viewSpecialCondButton(alert1) 
{	
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var table = document.getElementById("listTable");	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value
    }
	if (loanId=="")
	{
		alert("First save the disbursal details.");	
		return false;
	}
	else
	{
		
		//alert("loanId: "+loanId);
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url= contextPath+"/viewSpecialConditionDisb.do?method=viewSpecialCondition&loanId="+loanId;
		//alert("url: "+url);
		//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
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
//method added by neeraj tripathi
function generateDisbursalVoucherReport()
{
	var forwardedLoanId=document.getElementById("forwardedLoanId").value;
	if(forwardedLoanId !='')
	{
		if(confirm("Do you want to generate Subsequent/Full disbursement Voucher Report."))
		{
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("disbursalSearchForm").action = contextPath+"/disbursalSearch.do?method=generateDisbursalVoucherReport&loanID="+forwardedLoanId;
			document.getElementById("disbursalSearchForm").submit();
		}						
	}
}

/*Added By arun For disbursal with PAyment*/

function saveDisbursalDataWithPayment(loanIDDisbursal)
{
	//alert("in the function");
	var maturityDate1= document.getElementById("maturityDate1").value;
	//alert("maturityDate1"+maturityDate1);
	var tenureMonths = document.getElementById("tenureMonths").value;
	//alert("AT save tenureMonths "+tenureMonths);
	calculateNetAmount();
	var repayMode= document.getElementById("repayMode").value;
	/*if(repayMode=='I'){
		calculateEMI();	
	}*/
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var disbursalDate= document.getElementById("disbursalDate").value;
	var maxDisbursalDate= document.getElementById("maxDisbursalDate").value;
	var businessDate=document.getElementById("bissdate").value;
	var disbursalAmount= document.getElementById("disbursalAmount").value;
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var adjustTotalReceivable= document.getElementById("adjustTotalReceivable").value;
	var totalReceivable= document.getElementById("totalReceivable").value;
	var adjustTotalPayable= document.getElementById("adjustTotalPayable").value;
	var totalPayable= document.getElementById("totalPayable").value;
	var finalDisbursal=document.getElementById("finalDisbursal").checked;
	var loanCurtail=document.getElementById("loanCurtail").checked;
	var totalReceivableCustomer= document.getElementById("totalReceivableCustomer").value;
	var disbursalTo=document.getElementById("disbursalTo").value;
	var amountInProcessLoan=document.getElementById("amountInProcessLoan").value;
	var totalReceivableCurrShortPay=document.getElementById("totalReceivableCurrShortPay").value;
	//alert(finalDisbursal);
	
	if(amountInProcessLoan==""){
		amountInProcessLoan="0";
	}else{
		amountInProcessLoan=removeComma(amountInProcessLoan);
	}
	
	if(totalReceivableCustomer==""){
		totalReceivableCustomer="0";
	}else{
		totalReceivableCustomer=removeComma(totalReceivableCustomer);
	}
	
	if(adjustTotalReceivable==""){
		adjustTotalReceivable="0";
	}else{
		adjustTotalReceivable=removeComma(adjustTotalReceivable);
	}
	if(totalReceivable==""){
		totalReceivable="0";
	}else{
		totalReceivable=removeComma(totalReceivable);
	}
	if(adjustTotalPayable==""){
		adjustTotalPayable="0";
	}else{
		adjustTotalPayable=removeComma(adjustTotalPayable);
	}
	if(totalPayable==""){
		totalPayable="0";
	}else{
		totalPayable=removeComma(totalPayable);
	}
	if(disbursalAmount==""){
		disbursalAmount="0";
	}else{
		disbursalAmount=removeComma(disbursalAmount);
	}
	var pdcDepositCount= document.getElementById("pdcDepositCount").value;
	if(pdcDepositCount==""){pdcDepositCount="0";}
	var disbursalDateObj="";
	var maxDisbursalDateObj="";
	var businessDateObj="";
	var loanApprovalDateObj="";
	var repEfDtObj="";
	var nextDuDtObj="";
	var saveStatus=true;
	var sum=0;
	var loanAmt = 0;
	var disbursedAmt = 0;
	var disbursalAmt = 0;
	//alert(document.getElementById(loanId1));
	var table = document.getElementById("listTable");	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	//alert("riw"+rowCount);
	
	if(finalDisbursal && amountInProcessLoan!=0)
	{
		alert("Some amount are in amount in process so first clear those tranctions");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}
	if((disbursalTo=="SU" || disbursalTo=="MF") && finalDisbursal && totalReceivableCustomer!=0)
	{
		alert("First receive Total Receivable of Customer.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}
	if(loanId=="")
	{
		alert("Please select Loan Number.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}else if(rowCount>1 ){
		if(document.getElementById("loanId1").value!=loanId){
			alert("You Can`t Choose different Loan No");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;}
	}else if(disbursalDate==""){
		alert("Disbursal Date is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;	
	}else if(disbursalAmount=="" && !finalDisbursal){
		alert("Current Disbursal Amount is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;		
	}else if(disbursalAmount=="" && finalDisbursal && document.getElementById("disbursedAmount").value==0){
		alert("Current Disbursal Amount is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;		
	}else if(parseFloat(adjustTotalReceivable)>parseFloat(totalReceivable)){
		alert("Adjust Total Receivable can't be greater than Total Receivable.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}else if(parseFloat(adjustTotalPayable)>parseFloat(totalPayable)){
		alert("Adjust Total Payable can't be greater than Total Payable.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}else if(parseFloat(adjustTotalReceivable)>parseFloat(disbursalAmount)+parseFloat(totalPayable)){
		alert("Adjust Total Receivable can't be greater than Disbursal Amount plus Total Payable .");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}
	//alert("Oepn for save");
		var formatD=document.getElementById("formatD").value;
		disbursalDateObj=getDateObject(disbursalDate,formatD.substring(2, 3));
		businessDateObj=getDateObject(businessDate,formatD.substring(2, 3));
		if(loanApprovalDate!=""){
		 loanApprovalDateObj=getDateObject(loanApprovalDate,formatD.substring(2, 3));	
		}
		loanAmt = removeComma(document.getElementById("loanAmt").value);
		disbursedAmt = document.getElementById("disbursedAmount").value;
		disbursalAmt = document.getElementById("disbursalAmount").value;
		disbursedAmountTemp = document.getElementById("disbursedAmountTemp").value;
		//alert("loanAmt "+loanAmt+"\ndisbursedAmt "+disbursedAmt+"\ndisbursalAmt"+disbursalAmt+"\ndisbursedAmountTemp"+disbursedAmountTemp);
		if(maxDisbursalDate!=""){
			maxDisbursalDateObj=getDateObject(maxDisbursalDate,formatD.substring(2, 3));
		}
		if(disbursedAmt==""){
			disbursedAmt="0";
		}else{
			disbursedAmt=removeComma(disbursedAmt);
		}
		if(disbursalAmt==""){
			disbursalAmt="0";
		}else{
			disbursalAmt=removeComma(disbursalAmt);
		}
		if(disbursedAmountTemp==""){
			disbursedAmountTemp="0";
		}else{
			disbursedAmountTemp=removeComma(disbursedAmountTemp);
		}
		 sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt)+parseFloat(disbursedAmountTemp);
		// alert("sum:----"+sum);
		/* if(parseFloat(disbursalAmt)== 0){
			 alert("Disbursal Amount can not be zero.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false; 
		 }else*/
	
		 if(disbursalDateObj>businessDateObj){
			alert("Disbursal Date can not greater than business date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;
		}else if (disbursalDateObj<loanApprovalDateObj){
			alert("Disbursal Date can not less than Loan Approval date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;	
		}else if(maxDisbursalDateObj!=""&& maxDisbursalDateObj>disbursalDateObj){
			
			alert(" Disbursal Date should be greater than last disbursal Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;		
		}/*else if(diffLoanTotalRec<sum){
			
			alert("First Adjust Total Receivable.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;			
		}*/
		 if(finalDisbursal && adjustTotalReceivable!=totalReceivable && totalReceivableCurrShortPay=='Y')
		 {
			    alert("First Adjust Total Receivable.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
		 }
//		else if(sum>parseFloat(loanAmt)){
//			alert("Sum of Disbursed and Current Disbursal Amount can not greater than Loan Amount.");
//			document.getElementById("save").removeAttribute("disabled","true");
//			DisButClass.prototype.EnbButMethod();
//			document.getElementById("disbursalAmount").focus();
//			saveStatus=false;
//			return false;	
//		}
		//code added by neeraj
		var revolvingFlag=document.getElementById("revolvingFlag").value;
		var balancePrinc=document.getElementById("balancePrinc").value;
		var forwardedAmt=document.getElementById("forwardedAmt").value;
		
		if(revolvingFlag=='N' && sum>parseFloat(loanAmt))
		{
			alert("Sum of Disbursed and Current Disbursal Amount can not greater than Loan Amount.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalAmount").focus();
			saveStatus=false;
			return false;	
		}
		if(revolvingFlag=='Y')
		{
			if(parseFloat(disbursalAmount)>parseFloat(loanAmt)-parseFloat(balancePrinc)-parseFloat(forwardedAmt))
			{
				alert("Sum of Disbursal,Balance Princpal and Forwarded Disburse amount can't be greater than Loan Amount.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false;	
			}
		}
		//neeraj space end
		// alert("loanAmt:----"+loanAmt);
		 if(sum==parseFloat(loanAmt)){
			//alert("In sum Amount.......");
			//alert("hahah:---"+document.getElementById("finalDisbursal"));
			//alert("hahah:---"+document.getElementById("finalDisbursal").checked);
			//alert("repayMode:----"+repayMode);
			if(document.getElementById("finalDisbursal")!=undefined 
					&& document.getElementById("finalDisbursal").checked==false && repayMode=='I'){
				alert("Please select Final Disbursal");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false;	
			}
				
		}
		//alert("repayMode:--"+document.getElementById("repayMode").value);
		//alert("cycleDate:--"+document.getElementById("cycleDate").value);
		
		if(document.getElementById("repayMode").value=='I'){
			//alert("finalDisbursal:-"+document.getElementById("finalDisbursal").checked);
			if(document.getElementById("finalDisbursal").checked==true ){
//				if(disbursedAmt == '0' && disbursalAmt == '0'){
//				    alert("Cannot Finalize Disbursal with Zero amount");
//					DisButClass.prototype.EnbButMethod();
//					document.getElementById("save").removeAttribute("disabled","true");
//					document.getElementById("disbursalAmount").value='';
//					document.getElementById("disbursalAmount").focus();
//					return false;
//				}else 
	
				
				var year= businessDateObj.getYear();
				var month = businessDateObj.getMonth();
				var day = businessDateObj.getDate();
				var thisMonthFirstDay = new Date(year, month, 1);
				//alert("thisMonthFirstDay: "+thisMonthFirstDay);
				var repayEffDate = document.getElementById("repayEffDate").value;
				var repayEffDateOb=getDateObject(repayEffDate,formatD.substring(2, 3));
				var repayEffDateMonth=repayEffDateOb.getMonth();
				var repayEffDateYear=repayEffDateOb.getYear();
				if(document.getElementById("repayEffDate").value==""){
					alert("Repay effective date is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("repayEffDate").focus();
					saveStatus=false;
					return false;	
				}else if(repayEffDateOb<loanApprovalDateObj){
					alert("Repay Effective Date can not less than Loan Approval Date");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}
				else if(repayEffDateMonth<month  && repayEffDateYear<=year){
					alert("Repay Effective Date can not less than first day business date ");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}
				else if(document.getElementById("hiddenDueDate").value==""){
					alert("Due Day is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}else if(document.getElementById("nextDueDate").value==""){
					alert("Next Due dete is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("nextDueDate").focus();
					saveStatus=false;
					return false;	
				}else{
					var repayEffDate=document.getElementById("repayEffDate").value;
					var nextDueDate=document.getElementById("nextDueDate").value;
					repEfDtObj=getDateObject(repayEffDate,formatD.substring(2, 3));
					nextDuDtObj=getDateObject(nextDueDate,formatD.substring(2, 3));
					if(repEfDtObj<disbursalDateObj)
					{
						alert("Repay Effective Date can't be less than Disbursal Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("repayEffDate").focus();
						saveStatus=false;
						return false;
					}
					if(repEfDtObj>=nextDuDtObj)
					{
						alert("Next Due Date must be greater than Repay Effective Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("nextDueDate").focus();
						saveStatus=false;
						return false;
					}
					if(disbursalDateObj>nextDuDtObj)
					{
						alert("Next Due Date must be greater than Disbursal Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("nextDueDate").focus();
						saveStatus=false;
						return false;
					}
				}
			}
		}else if(document.getElementById("repayMode").value=='N'){
			if(document.getElementById("repayEffBusDate")!=undefined 
					&& document.getElementById("repayEffBusDate").value==""){
				alert("Repay Effective Date is required.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("nextDueDate").focus();
				saveStatus=false;
				return false;
			  }else if(document.getElementById("repayEffBusDate").value!==""){
				var  repayEffBusDate=  document.getElementById("repayEffBusDate").value;
				var  repayEffBusDateObj=getDateObject(repayEffBusDate,formatD.substring(2, 3));
				 if(repayEffBusDateObj<disbursalDateObj){
				    alert("Repay Effective Date must be equal or greater than Disbursal Date.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("repayEffBusDate").focus();
					saveStatus=false;
					return false; 
				 } 
			  }
			if(document.getElementById("penalIntCalcDate")!=undefined 
					&& document.getElementById("penalIntCalcDate").value==""){
				alert("Penal Interest Calc Date is required.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("nextDueDate").focus();
				saveStatus=false;
				return false;
			}else if(document.getElementById("penalIntCalcDate").value!=""){
				var  penalIntCalcDate=  document.getElementById("penalIntCalcDate").value;
				var  penalIntCalcDateObj=getDateObject(penalIntCalcDate,formatD.substring(2, 3));
				
				 if(penalIntCalcDateObj<disbursalDateObj){
					    alert("Penal Interest Calc Date must be equal or greater than Disbursal Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("repayEffBusDate").focus();
						saveStatus=false;
						return false; 
					 } 
			}
			}
		
	 
	
	  
		if(document.getElementById("disbursalTo").value==""){
			alert("Please select Disbursal To.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}else if(document.getElementById("disbursalTo").value=='SU' && document.getElementById("businessPartnerTypeDesc").value==""){
			alert("Please select Dealer Name.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		
		}else if(document.getElementById("disbursalTo").value =='MF' && document.getElementById("businessPartnerTypeDesc").value==""){
			alert("Please select Manufacturer Name.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		
		}
		if(document.getElementById("paymentFlag").value=='P'){
			if(document.getElementById("paymentMode").value==""){
				alert("Payment Mode is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}if(document.getElementById("paymentDate").value==""){
				alert("Payment Date is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}if(document.getElementById("paymentDate").value!=""){
				var paymentDate=document.getElementById("paymentDate").value;
				var paymentDateObj=getDateObject(paymentDate,formatD.substring(2, 3));
				if(paymentDateObj>businessDateObj){
					alert("Payment Date can not greater than Business Date");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
					
			}if(document.getElementById("paymentAmount").value==""){
				alert("Payment Amount is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}if(document.getElementById("paymentAmount").value!=""){
			
				var disAmnt=document.getElementById("disbursalAmount").value;
				var totalPayable=document.getElementById("adjustTotalPayable").value;
				var totalReceivable=document.getElementById("adjustTotalReceivable").value;
				if(totalPayable==""){
					totalPayable="0.0";
				}
				if(totalReceivable==""){
					totalReceivable="0.0";
				}
				if(disAmnt==""){
					disAmnt="0.0";
				}
				var totDisbTotAdj=(removeComma(disAmnt)+removeComma(totalPayable))-(removeComma(totalReceivable));
				var payAmnt=removeComma(document.getElementById("paymentAmount").value);
				//alert("totDisbTotAdj:---"+totDisbTotAdj);
				if(payAmnt==0||payAmnt<1){
					alert("Payment Amount should be greater than zero ");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
				if(totDisbTotAdj<payAmnt){
					alert("Payment Amount should be less than or equal to  Net Amount");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
				//aditya starts
				if(document.getElementById("tdsAmount").value!="")
					{
					var tdsAmnt=removeComma(document.getElementById("tdsAmount").value);
					var totalAmt=tdsAmnt+payAmnt;
				if(totDisbTotAdj<totalAmt){
					alert("Sum of Payment Amount and TDS Amount should be less than or equal to  Net Amount");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
					}
				//aditya ends
								
			}if(document.getElementById("instrumentNumber").value==""){
				alert("Instrument Number  is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}if(document.getElementById("paymentMode").value!="C"&&document.getElementById("instrumentDate").value==""){
				alert("Instrument Date is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} 
			
			//Virender Start For RTGS/NEFT
			if(document.getElementById("paymentMode").value=="N" || document.getElementById("paymentMode").value=="R" ){
				if(document.getElementById("beneficiaryBankCode").value==""){
					alert("beneficiaryBankCode is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				} 
				if(document.getElementById("beneficiaryBankBranchName").value==""){
					alert("beneficiaryBankBranchName is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				} 
				if(document.getElementById("beneficiaryAccountNo").value==""){
					alert("beneficiaryAccountNo is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				} 
				if(document.getElementById("beneficiaryIfscCode").value==""){
					alert("beneficiaryIfscCode is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				} 
			}
			//Virender Code End
		
			if(document.getElementById("instrumentDate").value!=""){
				var paymentDate=document.getElementById("paymentDate").value;
				var instrumentDate=document.getElementById("instrumentDate").value
				var paymentDateObj=getDateObject(paymentDate,formatD.substring(2, 3));
				var instrumentDateObj=getDateObject(instrumentDate,formatD.substring(2, 3));
				if(instrumentDateObj<paymentDateObj){
					alert("Insterument date can not less than Payment Date");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
					
			}if(document.getElementById("bankAccount").value==""){
				alert("Bank Account is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
			
		   }else  if(document.getElementById("paymentFlag").value=='T'){
			
			 if(document.getElementById("taLoanNo").value==""){
					alert("TA Loan is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;
				
				}
		} else if(document.getElementById("adjustTotalPayable").value!="" ){
			var adj=removeComma(document.getElementById("adjustTotalPayable").value);
			var pay=removeComma(document.getElementById("totalPayable").value);
			if(adj>pay){
				alert("Adjust Payable amount can not be greater than total payable Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			}
			
		}else if(document.getElementById("adjustTotalReceivable").value!="" ){
			var adj=removeComma(document.getElementById("adjustTotalReceivable").value);
			var rec=removeComma(document.getElementById("totalReceivable").value);
			if(adj>rec){
				alert("Adjust Receivable amount can not be greater than Total Receivable Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			}
			
		}
		//Adding to validate Interest Due Date at disbursal by AJAY
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
		
		//End by Ajay
	//alert("saveStatus:---"+saveStatus);
	//return;
	if(saveStatus){
	if(document.getElementById("finalDisbursal")!=undefined 
				&& document.getElementById("finalDisbursal").checked==true ){
	  if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
			{
		  if(pdcDepositCount==0){
				var pdcDeposited=true;
				if(repayMode=='I'){
					pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
					DisButClass.prototype.DisButMethod();
				}
				
				if(pdcDeposited){	
				
				document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("disbursalMakerForm").submit();
				return true;
				}else{
				    document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		}else{
			
			document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("disbursalMakerForm").submit();	
			return true;
		   }
			}else{
			
			    document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;	
		}
			
		}else{
			 if(pdcDepositCount==0){
					var pdcDeposited=true;
					if(repayMode=='I'){
						pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
						DisButClass.prototype.DisButMethod();
					}
					
					if(pdcDeposited){		
					document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("disbursalMakerForm").submit();	
					return true;
					}else{
					    document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
			}else{
				
				document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal+"&maturityDate1="+maturityDate1+"&tenureMonths="+tenureMonths; //Added by Brijesh 23-08-2018 (tenureMonths);
				document.getElementById("processingImage").style.display = '';
				document.getElementById("disbursalMakerForm").submit();	
				return true;
			   }	
		}
			
	}
	
}
function updateDisbursalDataWithPayment(loanIDDisbursal,recStatus)
{
//	alert("updateDisbursalDataWithPayment");
	var repayMode= document.getElementById("repayMode").value;
	var tenureMonths = document.getElementById("tenureMonths").value;
	//alert("AT update tenureMonths "+tenureMonths);
	/*if(repayMode=='I'){
		calculateEMI();	
	}*/
	var contextPath=document.getElementById("contextPath").value;
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var disbursalDate= document.getElementById("disbursalDate").value;
	var maxDisbursalDate= document.getElementById("maxDisbursalDate").value;
	var businessDate= document.getElementById("businessdate").value;
	var disbursalAmount= document.getElementById("disbursalAmount").value;
	var loanApprovalDate= document.getElementById("loanApprovalDate").value;
	var adjustTotalReceivable= document.getElementById("adjustTotalReceivable").value;
	var totalReceivable= document.getElementById("totalReceivable").value;
	var adjustTotalPayable= document.getElementById("adjustTotalPayable").value;
	var totalPayable= document.getElementById("totalPayable").value;
	var finalDisbursal=document.getElementById("finalDisbursal").checked;
	var totalReceivableCustomer= document.getElementById("totalReceivableCustomer").value;
	var disbursalTo=document.getElementById("disbursalTo").value;
	var amountInProcessLoan=document.getElementById("amountInProcessLoan").value;
	var totalReceivableCurrShortPay=document.getElementById("totalReceivableCurrShortPay").value;
	//alert(finalDisbursal);
	
	if(amountInProcessLoan==""){
		amountInProcessLoan="0";
	}else{
		amountInProcessLoan=removeComma(amountInProcessLoan);
	}
	
	if(totalReceivableCustomer==""){
		totalReceivableCustomer="0";
	}else{
		totalReceivableCustomer=removeComma(totalReceivableCustomer);
	}
	
	
	if(adjustTotalReceivable==""){
		adjustTotalReceivable="0";
	}else{
		adjustTotalReceivable=removeComma(adjustTotalReceivable);
	}
	if(totalReceivable==""){
		totalReceivable="0";
	}else{
		totalReceivable=removeComma(totalReceivable);
	}
	if(adjustTotalPayable==""){
		adjustTotalPayable="0";
	}else{
		adjustTotalPayable=removeComma(adjustTotalPayable);
	}
	if(totalPayable==""){
		totalPayable="0";
	}else{
		totalPayable=removeComma(totalPayable);
	}
	if(disbursalAmount==""){
		disbursalAmount="0";
	}else{
		disbursalAmount=removeComma(disbursalAmount);
	}
	var pdcDepositCount= document.getElementById("pdcDepositCount1").value;
	if(pdcDepositCount==""){pdcDepositCount="0";}
	var disbursalDateObj="";
	var maxDisbursalDateObj="";
	var businessDateObj="";
	var loanApprovalDateObj="";
	var repEfDtObj="";
	var nextDuDtObj="";
	var saveStatus=true;
	var sum=0;
	var loanAmt = 0;
	var disbursedAmt = 0;
	var disbursalAmt = 0;
	document.getElementById("saveForwordFlag").value=recStatus;
	
	
	if(finalDisbursal && amountInProcessLoan!=0)
	{
		alert("Some amount are in amount in process so first clear those tranctions");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}
	if((disbursalTo=="SU" || disbursalTo=="MF") && finalDisbursal && totalReceivableCustomer!=0)
	{
		alert("First receive Total Receivable of Customer.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}
	if(loanId=="")
	{
		alert("Please select Loan Number.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	} if(disbursalDate==""){
		alert("Disbursal Date is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;	
	}else if(disbursalAmount=="" && !finalDisbursal){
		alert("Current Disbursal Amount is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;		
	}else if(disbursalAmount=="" && finalDisbursal && document.getElementById("disbursedAmount").value==0){
		alert("Current Disbursal Amount is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;		
	}else if(parseFloat(adjustTotalReceivable)>parseFloat(totalReceivable)){
		alert("Adjust Total Receivable can't be greater than Total Receivable.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}else if(parseFloat(adjustTotalPayable)>parseFloat(totalPayable)){
		alert("Adjust Total Payable can't be greater than Total Payable.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}else if(parseFloat(adjustTotalReceivable)>parseFloat(disbursalAmount)+parseFloat(totalPayable)){
		alert("Adjust Total Receivable can't be greater than Disbursal Amount plus Total Payable .");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;			
	}else{ 
		var formatD=document.getElementById("formatD").value;
		disbursalDateObj=getDateObject(disbursalDate,formatD.substring(2, 3));
		businessDateObj=getDateObject(businessDate,formatD.substring(2, 3));
		if(loanApprovalDate!=""){
		 loanApprovalDateObj=getDateObject(loanApprovalDate,formatD.substring(2, 3));	
		}
		loanAmt = removeComma(document.getElementById("loanAmt").value);
		disbursedAmt = document.getElementById("disbursedAmount").value;
		disbursalAmt = document.getElementById("disbursalAmount").value;
		disbursedAmountTemp = document.getElementById("disbursedAmountTemp").value;
		if(maxDisbursalDate!=""){
			maxDisbursalDateObj=getDateObject(maxDisbursalDate,formatD.substring(2, 3));
		}
		if(disbursedAmt==""){
			disbursedAmt="0";
		}else{
			disbursedAmt=removeComma(disbursedAmt);
		}
		if(disbursalAmt==""){
			disbursalAmt="0";
		}else{
			disbursalAmt=removeComma(disbursalAmt);
		}
		if(disbursedAmountTemp==""){
			disbursedAmountTemp="0";
		}else{
			disbursedAmountTemp=removeComma(disbursedAmountTemp);
		}
		 sum=parseFloat(disbursedAmt)+parseFloat(disbursalAmt)+parseFloat(disbursedAmountTemp);
		// alert("disbursedAmt:--"+disbursedAmt+"-disbursalAmt:--"+disbursalAmt+" --Sum:--"+sum);
		/* if(parseFloat(disbursalAmt)==0){
			 alert("Disbursal Amount can not be zero.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false; 
		 }else */
		 //alert("loanAmt: "+parseFloat(loanAmt)+"\ntotalReceivable "+parseFloat(totalReceivable)+" \n--Sum:--"+sum);
		 if(disbursalDateObj>businessDateObj){
			alert("Disbursal Date can not greater than business date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;
		}else if (disbursalDateObj<loanApprovalDateObj){
			alert("Disbursal Date can not less than Loan Approval date.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;	
		}else if(maxDisbursalDateObj!=""&& maxDisbursalDateObj>disbursalDateObj){
			
			alert(" Disbursal Date should be greater than last disbursal Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("disbursalDate").focus();
			saveStatus=false;
			return false;
		
		}else if(sum==parseFloat(loanAmt)){
			//alert("sum:---"+sum);
			//alert("loanAmt:---"+loanAmt);
			if(document.getElementById("finalDisbursal")!=undefined 
					&& document.getElementById("finalDisbursal").checked==false && repayMode=='I'){
				alert("Please select Final Disbursal");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false;	
			}
				
		} if(finalDisbursal && adjustTotalReceivable!=totalReceivable && totalReceivableCurrShortPay=='Y'){
		//else if((parseFloat(loanAmt)-parseFloat(totalReceivable)) < sum){
			alert("First Adjust Total Receivable.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;			
		}
		
		//code added by neeraj
		var revolvingFlag=document.getElementById("revolvingFlag").value;
		var balancePrinc=document.getElementById("balancePrinc").value;
		var forwardedAmt=document.getElementById("forwardedAmt").value;
		if(revolvingFlag=='N' && sum>parseFloat(loanAmt))
		{
			alert("Sum of Disbursed and Current Disbursal Amount can not greater than Loan Amount.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("disbursalAmount").focus();
			saveStatus=false;
			return false;	
		}
		if(revolvingFlag=='Y')
		{
			if(parseFloat(disbursalAmount)>parseFloat(loanAmt)-parseFloat(balancePrinc)-parseFloat(forwardedAmt))
			{
				alert("Sum of Disbursal,Balance Princpal and Forwarded Disburse amount can't be greater than Loan Amount.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false;	
			}
		}
		//neeraj space end
		if(document.getElementById("repayMode").value=='I'){
			if(document.getElementById("finalDisbursal")!=undefined 
					&& document.getElementById("finalDisbursal").checked==true ){
//				if(disbursedAmt == '0' && disbursalAmt == '0'){
//				    alert("Cannot Finalize Disbursal with Zero amount");
//					DisButClass.prototype.EnbButMethod();
//					document.getElementById("save").removeAttribute("disabled","true");
//					document.getElementById("disbursalAmount").value='';
//					document.getElementById("disbursalAmount").focus();
//					return false;
//				}
				var year= businessDateObj.getYear();
				var month = businessDateObj.getMonth();
				var day = businessDateObj.getDate();
				var thisMonthFirstDay = new Date(year, month, 1);
				//alert("thisMonthFirstDay: "+thisMonthFirstDay);
				var repayEffDate = document.getElementById("repayEffDate").value;
				var repayEffDateOb=getDateObject(repayEffDate,formatD.substring(2, 3));
				var repayEffDateMonth=repayEffDateOb.getMonth();
				var repayEffDateYear=repayEffDateOb.getYear();
				if(document.getElementById("repayEffDate").value==""){
					alert("Repay effective date is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("repayEffDate").focus();
					saveStatus=false;
					return false;	
				}else if(repayEffDateOb<loanApprovalDateObj){
					alert("Repay Effective Date can not less than Loan Approval Date");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}
				else if(repayEffDateMonth<month  && repayEffDateYear<=year){
					alert("Repay Effective Date can not less than first day business date ");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}
				else if(document.getElementById("hiddenDueDate").value==""){
					alert("Due Day is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("cycleDate").focus();
					saveStatus=false;
					return false;	
				}else if(document.getElementById("nextDueDate").value==""){
					alert("Next Due dete is required.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("nextDueDate").focus();
					saveStatus=false;
					return false;	
				}else{
					var repayEffDate=document.getElementById("repayEffDate").value;
					var nextDueDate=document.getElementById("nextDueDate").value;
					repEfDtObj=getDateObject(repayEffDate,formatD.substring(2, 3));
					nextDuDtObj=getDateObject(nextDueDate,formatD.substring(2, 3));
					if(repEfDtObj<disbursalDateObj)
					{
						alert("Repay Effective Date can't be less than Disbursal Date.");
  					    document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("repayEffDate").focus();
						saveStatus=false;
						return false;
					}
					if(repEfDtObj>=nextDuDtObj)
					{
						alert("Next Due Date must be greater than Repay Effective Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("nextDueDate").focus();
						saveStatus=false;
						return false;
					}
					if(disbursalDateObj>nextDuDtObj)
					{
						alert("Next Due Date must be greater than Disbursal Date.");
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("nextDueDate").focus();
						saveStatus=false;
						return false;
					}
				}
			}
		}else if(document.getElementById("repayMode").value=='N'){
		if(document.getElementById("repayEffBusDate")!=undefined 
				&& document.getElementById("repayEffBusDate").value==""){
			alert("Repay Effective Date is required.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("nextDueDate").focus();
			saveStatus=false;
			return false;
		  }else if(document.getElementById("repayEffBusDate").value!==""){
			var  repayEffBusDate=  document.getElementById("repayEffBusDate").value;
			var  repayEffBusDateObj=getDateObject(repayEffBusDate,formatD.substring(2, 3));
			 if(repayEffBusDateObj<disbursalDateObj){
			    alert("Repay Effective Date must be equal or greater than Disbursal Date.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("repayEffBusDate").focus();
				saveStatus=false;
				return false; 
			 } 
		  }
		if(document.getElementById("penalIntCalcDate")!=undefined 
				&& document.getElementById("penalIntCalcDate").value==""){
			alert("Penal Interest Calc Date is required.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("nextDueDate").focus();
			saveStatus=false;
			return false;
		}else if(document.getElementById("penalIntCalcDate").value!=""){
			var  penalIntCalcDate=  document.getElementById("penalIntCalcDate").value;
			var  penalIntCalcDateObj=getDateObject(penalIntCalcDate,formatD.substring(2, 3));
			
			 if(penalIntCalcDateObj<disbursalDateObj){
				    alert("Penal Interest Calc Date must be equal or greater than Disbursal Date.");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("repayEffBusDate").focus();
					saveStatus=false;
					return false; 
				 } 
		}
		}
	}

	if(document.getElementById("disbursalTo").value==""){
		alert("Please select Disbursal To.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}else if(document.getElementById("disbursalTo").value=='SU' && document.getElementById("businessPartnerTypeDesc").value==""){
		alert("Please select Dealer Name.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	
	}else if(document.getElementById("disbursalTo").value =='MF' && document.getElementById("businessPartnerTypeDesc").value==""){
		alert("Please select Manufacturer Name.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	
	}
	if(document.getElementById("paymentFlag").value=='P'){
		if(document.getElementById("paymentMode").value==""){
			alert("Payment Mode is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		} 
		if(document.getElementById("paymentDate").value==""){
			alert("Payment Date is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		} 
		if(document.getElementById("paymentAmount").value==""){
			alert("Payment Amount is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		} 
		if(document.getElementById("paymentAmount").value!=""){
			var payAmnt=removeComma(document.getElementById("paymentAmount").value);
			var disAmnt=document.getElementById("disbursalAmount").value;
			var totalPayable=document.getElementById("adjustTotalPayable").value;
			var totalReceivable=document.getElementById("adjustTotalReceivable").value;
			if(totalPayable==""){
				totalPayable="0.0";
			}
			if(totalReceivable==""){
				totalReceivable="0.0";
			}
			if(disAmnt==""){
				disAmnt="0.0";
			}
			var totDisbTotAdj=(removeComma(disAmnt)+removeComma(totalPayable))-(removeComma(totalReceivable));
			var payAmnt=removeComma(document.getElementById("paymentAmount").value);
			if(payAmnt==0||payAmnt<1){
				alert("Payment Amount should be greater than zero ");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
			if(totDisbTotAdj<payAmnt){
				alert("Payment Amount should be less than or equal to  Net Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
			//aditya starts
			if(document.getElementById("tdsAmount").value!="")
			{
				var tdsAmnt=removeComma(document.getElementById("tdsAmount").value);
				var totalAmt=tdsAmnt+payAmnt;
				if(totDisbTotAdj<totalAmt)
				{
					alert("Sum of Payment Amount and TDS Amount should be less than or equal to  Net Amount");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
			}
			//aditya ends
							
		} 
		if(document.getElementById("instrumentNumber").value==""){
			alert("Instrument Number  is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		} 
		if(document.getElementById("paymentMode").value!="C"&&document.getElementById("instrumentDate").value==""){
			alert("Instrument Date is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}   
		

		//Virender Start For RTGS/NEFT
		if(document.getElementById("paymentMode").value=="N" || document.getElementById("paymentMode").value=="R" ){
			if(document.getElementById("beneficiaryBankCode").value==""){
				alert("beneficiaryBankCode is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} 
			if(document.getElementById("beneficiaryBankBranchName").value==""){
				alert("beneficiaryBankBranchName is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} 
			if(document.getElementById("beneficiaryAccountNo").value==""){
				alert("beneficiaryAccountNo is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} 
			if(document.getElementById("beneficiaryIfscCode").value==""){
				alert("beneficiaryIfscCode is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} 
		}
		//Virender Code End
		
		if(document.getElementById("instrumentDate").value!=""){
			var paymentDate=document.getElementById("paymentDate").value;
			var instrumentDate=document.getElementById("instrumentDate").value
			var paymentDateObj=getDateObject(paymentDate,formatD.substring(2, 3));
			var instrumentDateObj=getDateObject(instrumentDate,formatD.substring(2, 3));
			if(instrumentDateObj<paymentDateObj){
				alert("Insterument date can not less than Payment Date");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
				
		}
		if(document.getElementById("bankAccount").value==""){
			alert("Bank Account is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}
		
	   }else  if(document.getElementById("paymentFlag").value=='T'){
		
		 if(document.getElementById("taLoanNo").value==""){
				alert("TA Loan is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			
			}
	} else if(document.getElementById("adjustTotalPayable").value!="" ){
		var adj=removeComma(document.getElementById("adjustTotalPayable").value);
		var pay=removeComma(document.getElementById("totalPayable").value);
		if(adj>pay){
			alert("Adjust Payable amount can not be greater than total payable Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}
		
	}else if(document.getElementById("adjustTotalReceivable").value!="" ){
		var adj=removeComma(document.getElementById("adjustTotalReceivable").value);
		var rec=removeComma(document.getElementById("totalReceivable").value);
		if(adj>rec){
			alert("Adjust Receivable amount can not be greater than Total Receivable Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}
		
	}
	var saveForwordFlag=recStatus;
	if(saveStatus){
	if(document.getElementById("finalDisbursal")!=undefined 
				&& document.getElementById("finalDisbursal").checked==true ){
	  if(confirm("Loan Amt is "+loanAmt+" & Total Disbursal Amt is "+sum+" and this loan has marked for fully disbursal, are you sure to save this information? "))
			{
		  if(pdcDepositCount==0){
				var pdcDeposited=true;
				if(repayMode=='I'){
					pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
					DisButClass.prototype.DisButMethod();
				}
				
				if(pdcDeposited){		
				document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal+"&recStatus="+saveForwordFlag;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("disbursalMakerForm").submit();	
				return true;
				}else{
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
				    return false;
				}
		}else{
			document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal+"&recStatus"+saveForwordFlag;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("disbursalMakerForm").submit();	
			return true;
		   }
		}else{
			
			    document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;	
		}
			
		}else{
			 if(pdcDepositCount==0){
					var pdcDeposited=true;
					if(repayMode=='I'){
						pdcDeposited = confirm("No Instruments have been captured for this Loan. Do you want to continue with Disbursal?");
						DisButClass.prototype.DisButMethod();
					}
					
					if(pdcDeposited){		
					document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal+"&recStatus"+saveForwordFlag;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("disbursalMakerForm").submit();	
					return true;
					}else{
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
					    return false;
					}
			}else{
				document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalDataWithPayment&loanIDDisbursal="+loanIDDisbursal+"&recStatus"+saveForwordFlag
				+"&tenureMonths"+tenureMonths;//Added by Brijesh 23-08-2018 (tenureMonths)
				document.getElementById("processingImage").style.display = '';
				document.getElementById("disbursalMakerForm").submit();	
				return true;
			   }	
		}
			
	}
}
function forwordDisbursal(){
	var forwordStatus=true;
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var loanId=document.getElementById('lbxLoanNoHID').value;
	var table = document.getElementById("listTable");	
	var savefwdFlagOnChangeDate=document.getElementById('fwdStatusFlag').value;
	var oldRepayEffDate = document.getElementById('oldRepayEffDate').value;
	//alert("oldRepayEffDate"+oldRepayEffDate);
	var repayEffDate = document.getElementById('repayEffDate').value;
	//alert("repayEffDate"+repayEffDate);
	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table==null||rowCount<1){
		alert("First save the disbursal details.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		forwordStatus=false;
		return false;	
    }else{
	var addDisbursalAmount=document.getElementsByName("addDisbursalAmount");
	var totalAddDisbursalAmount=0;
	var disAmount=0;
	var loanAmtF=document.getElementById('loanAmtF').value;
	var disbursedAmountF=document.getElementById('disbursedAmountF').value;
	var finDisbFlagCount=0;
	var finDisbFlagmul=0;
	if(loanAmtF==""){
		loanAmtF="0";
	}
  if(disbursedAmountF ==""){
	  disbursedAmountF="0";
	}
	var loanAmount=0;
	var disbursedAmount=0;
	
	loanAmount=parseFloat(loanAmtF);
	disbursedAmount=parseFloat(disbursedAmountF);
	
	
	
	if(loanId!=""){
		alert("First save then Forward");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		forwordStatus=false;
		return false;
	}
	if(table==null||rowCount<1){
			alert("First save the disbursal details.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			forwordStatus=false;
			return false;	
	}
	if(rowCount>0){
			for(var i=1;i<rowCount;i++){
				var strVarDisAmount=document.getElementById("addDisbursalAmount"+i).value;
				var finalDisb=document.getElementById("finDisbursal"+i).value;
				//alert("strVarDisAmount:----"+strVarDisAmount);
				if(strVarDisAmount==""){
					strVarDisAmount="0"
				}
				if(finalDisb=='F'){
					finDisbFlagCount++
				}
				if(finalDisb=='F'&& i!=rowCount-1){
					finDisbFlagmul++
				}
				totalAddDisbursalAmount=totalAddDisbursalAmount+removeComma(strVarDisAmount);
			}
		}
		var sum=totalAddDisbursalAmount+disbursedAmount;
		//alert("totalAddDisbursalAmount:-"+totalAddDisbursalAmount);
		//alert("disAmount:-"+disAmount);
//		if(sum>loanAmount){
//			alert("Sum of Disbursal Amount And Disbursed Amount should be equal to Loan Amount.");
//			document.getElementById("save").removeAttribute("disabled","true");
//			DisButClass.prototype.EnbButMethod();
//			forwordStatus=false;
//			return false;	
//		}
		//code added by neeraj
		//Nishant Space starts
		var recoveryType=document.getElementById("recoveryType").value;
		var disbursalFlag=document.getElementById("disbursalFlag").value;
		var repaymentType=document.getElementById("repaymentType").value;
		var installmentType=document.getElementById("installmentType").value;
		var checkNewInstAndRepay='N';
			
		if((repaymentType == 'I' && recoveryType == 'F' && disbursalFlag == 'F' && sum<loanAmount)||installmentType=='I')
		{
			checkNewInstAndRepay='Y';
		}
		//alert("checkNewInstAndRepay : " + checkNewInstAndRepay + "sum : " + sum + "loanAmount : " + loanAmount);
		
		//Nishant space ends
		var revolvingFlag=document.getElementById("revolvingFlag").value;
		var balancePrinc=document.getElementById("balancePrinc").value;
		var forwardedAmt=document.getElementById("forwardedAmt").value;
		if(revolvingFlag=='N' && sum>loanAmount)
		{
			alert("Sum of Disbursal Amount And Disbursed Amount should be equal to Loan Amount.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			forwordStatus=false;
			return false;	
		}
		if(revolvingFlag=='Y')
		{
			if(totalAddDisbursalAmount>loanAmount-parseFloat(balancePrinc)-parseFloat(forwardedAmt))
			{
				alert("Sum of Disbursal,Balance Princpal and Forwarded Disburse amount can't be greater than Loan Amount.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("disbursalAmount").focus();
				saveStatus=false;
				return false;	
			}
		}
		//neeraj space end
		
		
		
		
		if(finDisbFlagCount>1){
			alert("More than one Final disbursal are not allowed");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			forwordStatus=false;
			return false;		
		}
		if(finDisbFlagmul>0){
			alert("Last Disbursal should be final");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			forwordStatus=false;
			return false;	
		}
//start here | Brijesh Pathak
	/*if(oldRepayEffDate!=repayEffDate){
		alert("Plaes Save Installment Plan");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		forwordStatus=false;
		return false;	
	}*/
//end here | Brijesh Pathak
	/*	if(savefwdFlagOnChangeDate=='N')
			{
			alert("First save new Installment Plan");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			//forwordStatus=false;
			return false;
			}*/
		if(forwordStatus){
			
			document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=forwordDisbursalDataWithPayment&checkNewInstAndRepay="+checkNewInstAndRepay;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("disbursalMakerForm").submit();		
		}
	//submit();
	}
}

function openAddDetailPopUp(stage){
	if(stage=='S'){
		alert("First save then add Details!");
	}else{
		var loanId=document.getElementById('lbxLoanNoHID').value;	
		var disbursalNo=document.getElementById('disbursalNo').value;	
		var loanDisbursalId=document.getElementById('loanDisbursalId').value;
		var customerName=document.getElementById('customerName').value;
		var disbursalAmount=document.getElementById('disbursalAmount').value;
		
		
		//alert("loanId: "+loanId);
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url= contextPath+"/disbursalMaker.do?method=openAddDetailPopUp&loanId="+loanId+"&disbursalNo="+disbursalNo+"&loanDisbursalId="+loanDisbursalId+"&customerName="+customerName+"&disbursalAmount="+disbursalAmount;
		//alert("url: "+url);
		window.open(url,'addDetails','height=400,width=1000,top=200,left=250,resizable=1,scrollbars=1').focus();
		return true;
	}

}
function openEditDisbursalDetails(id,loanId,disNo,from){
	var loanDisbursalId=id;	
	var loan=loanId;	
	var disNo=disNo;	
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=openEditDisbursalDetails&loanDisbursalId="+loanDisbursalId+"&loanId"+loan+"&disbursalNo="+disNo+"&from="+from;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("disbursalMakerForm").submit();	
	//alert(contextPath);
	//var url= contextPath+"/disbursalMaker.do?method=openEditAddDetailsPopup&loanDisbursalAddId="+loanDisbursalAddId+"&customerName="+customerName+"&from="+from;
	//alert("url: "+url);
	//window.open(url,'addDetails','height=400,width=1000,top=200,left=250,resizable=1,scrollbars=1').focus();	
}

function displayHideTaPayment(id){
	//alert("in displayHideTaPayment");
	var paymentFlag=document.getElementById('paymentFlag').value;
	if(paymentFlag=='P'){
		document.getElementById('taLoanNo').value="";
		document.getElementById('taCustomerName').value="";
		
		document.getElementById('taLoanNo').setAttribute("disabled","disabled");
		document.getElementById('taloanButton').setAttribute("disabled","disabled");
		//taCustomerName
		document.getElementById('payTo').removeAttribute("disabled","disabled");
		document.getElementById('payeeName').removeAttribute("disabled","disabled");
		document.getElementById('payToButton').removeAttribute("disabled","disabled");
		document.getElementById('payeeNameButton').removeAttribute("disabled","disabled");
		document.getElementById('paymentMode').removeAttribute("disabled","disabled");
		document.getElementById('paymentDate').removeAttribute("disabled","disabled");
		document.getElementById('instrumentNumber').removeAttribute("disabled","disabled");
		document.getElementById('instrumentDate').removeAttribute("disabled","disabled");
		document.getElementById('bankAccount').removeAttribute("disabled","disabled");
		document.getElementById('bankAccountButton').removeAttribute("disabled","disabled");
		document.getElementById('paymentAmount').removeAttribute("disabled","disabled");
		document.getElementById('tdsAmount').removeAttribute("disabled","disabled");
		document.getElementById('remarks').removeAttribute("disabled","disabled");
	}else if(paymentFlag=='T'){
		document.getElementById('taLoanNo').removeAttribute("disabled","disabled");
		document.getElementById('taloanButton').removeAttribute("disabled","disabled");
		//taCustomerName
		document.getElementById('payTo').value="";
		document.getElementById('payeeName').value="";
		document.getElementById('payToButton').value="";
		document.getElementById('payeeNameButton').value="";
		document.getElementById('paymentMode').value="";
		document.getElementById('paymentDate').value="";
		document.getElementById('instrumentNumber').value="";
		document.getElementById('instrumentDate').value="";
		document.getElementById('bankAccount').value="";
		document.getElementById('bankAccountButton').value="";
		document.getElementById('paymentAmount').value="";
		document.getElementById('tdsAmount').value="";
		document.getElementById('remarks').value="";
		document.getElementById('bank').value="";
		document.getElementById('branch').value="";
		document.getElementById('micr').value="";	
		document.getElementById('ifsCode').value="";	
		
		document.getElementById('payTo').setAttribute("disabled","disabled");
		document.getElementById('payeeName').setAttribute("disabled","disabled");
		document.getElementById('payToButton').setAttribute("disabled","disabled");
		document.getElementById('payeeNameButton').setAttribute("disabled","disabled");
		document.getElementById('paymentMode').setAttribute("disabled","disabled");
		document.getElementById('paymentDate').setAttribute("disabled","disabled");
		document.getElementById('instrumentNumber').setAttribute("disabled","disabled");
		document.getElementById('instrumentDate').setAttribute("disabled","disabled");
		document.getElementById('bankAccount').setAttribute("disabled","disabled");
		document.getElementById('bankAccountButton').setAttribute("disabled","disabled");
		document.getElementById('paymentAmount').setAttribute("disabled","disabled");
		document.getElementById('tdsAmount').setAttribute("disabled","disabled");
		document.getElementById('remarks').setAttribute("disabled","disabled");	
	}else{
		document.getElementById('payTo').value="";
		document.getElementById('payeeName').value="";
		document.getElementById('payToButton').value="";
		document.getElementById('payeeNameButton').value="";
		document.getElementById('paymentMode').value="";
		document.getElementById('paymentDate').value="";
		document.getElementById('instrumentNumber').value="";
		document.getElementById('instrumentDate').value="";
		document.getElementById('bankAccount').value="";
		document.getElementById('bankAccountButton').value="";
		document.getElementById('paymentAmount').value="";
		document.getElementById('tdsAmount').value="";
		document.getElementById('remarks').value="";	
		document.getElementById('taLoanNo').value="";	
		document.getElementById('bank').value="";
		document.getElementById('branch').value="";
		document.getElementById('micr').value="";	
		document.getElementById('ifsCode').value="";	
		
			
		document.getElementById('payTo').setAttribute("disabled","disabled");
		document.getElementById('payeeName').setAttribute("disabled","disabled");
		document.getElementById('payToButton').setAttribute("disabled","disabled");
		document.getElementById('payeeNameButton').setAttribute("disabled","disabled");
		document.getElementById('paymentMode').setAttribute("disabled","disabled");
		document.getElementById('paymentDate').setAttribute("disabled","disabled");
		document.getElementById('instrumentNumber').setAttribute("disabled","disabled");
		document.getElementById('instrumentDate').setAttribute("disabled","disabled");
		document.getElementById('bankAccount').setAttribute("disabled","disabled");
		document.getElementById('bankAccountButton').setAttribute("disabled","disabled");
		document.getElementById('paymentAmount').setAttribute("disabled","disabled");
		document.getElementById('tdsAmount').setAttribute("disabled","disabled");
		document.getElementById('remarks').setAttribute("disabled","disabled");	
		document.getElementById('taLoanNo').setAttribute("disabled","disabled");
		document.getElementById('taloanButton').setAttribute("disabled","disabled");
		
		
	}
	

		
}

function saveDisbursalPaymentAddDetails(loanIDDisbursal)
{
   var saveStatus=true;	
   calculateNetAmount();
   DisButClass.prototype.DisButMethod();
	if(document.getElementById("disbursalTo").value==""){
		alert("Please select Disbursal To.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	}else if(document.getElementById("businessPartnerTypeDesc").value==""){
		alert("Please select Loan Number.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	
	}else if(document.getElementById("disbursalAmount").value==""){
		alert(" Disbursal Amount is required");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		saveStatus=false;
		return false;
	
	}
	if(document.getElementById("paymentFlag").checked==true){
		if(document.getElementById("paymentMode").value==""){
			alert("Payment Mode is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}else if(document.getElementById("paymentDate").value==""){
			alert("Payment Date is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}else if(document.getElementById("instrumentNumber").value==""){
			alert("Instrument Number  is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}else if(document.getElementById("paymentMode").value!="C"&&document.getElementById("instrumentDate").value==""){
			alert("Instrument Date is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		} else if(document.getElementById("bankAccount").value==""){
			alert("Bank Account is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}
		
	   }else  if(document.getElementById("taFlag").checked==true){
		
		 if(document.getElementById("taLoanNo").value==""){
				alert("TA Loan is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			
			}
	}else if(document.getElementById("disbursalAmount").value!="" 
		&& document.getElementById("disbursalAmount").value!="" ){
		var disbursedAmount = removeComma(document.getElementById("disbursalAmount").value);
		var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
		if(disbursalAmount>disbursalAmount){
			alert("Disbursal amount can not be greater than Current Dusbursal Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;	
		}
	} else if(document.getElementById("adjustTotalPayable").value!="" ){
		var adj=removeComma(document.getElementById("adjustTotalPayable").value);
		var pay=removeComma(document.getElementById("totalPayable").value);
		if(adj>pay){
			alert("Adjust Payable amount can not be greater than total payable Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}
		
	}else if(document.getElementById("adjustTotalReceivable").value!="" ){
		var adj=removeComma(document.getElementById("adjustTotalReceivable").value);
		var rec=removeComma(document.getElementById("totalReceivable").value);
		if(adj>rec){
			alert("Adjust Receivable amount can not be greater than Total Receivable Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}
		
	}
	if(saveStatus)
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("disbursalAddDetailsForm").action = contextPath+"/disbursalMaker.do?method=saveDisbursalPaymentAddDetails" 
				/* &recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;*/
		document.getElementById("processingImage").style.display = '';
		document.getElementById("disbursalAddDetailsForm").submit();	
		return true;
	}else{
		return false;
	}
	
}
function updateDisbursalPaymentAddDetails(loanIDDisbursal)
{
	   var saveStatus=true;	
	   calculateNetAmount();
	   DisButClass.prototype.DisButMethod();
		if(document.getElementById("disbursalTo").value==""){
			alert("Please select Disbursal To.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		}else if(document.getElementById("disbursalTo").value=='OTH' && document.getElementById("businessPartnerTypeDescCust").value==""){
			alert("Please select DisbursalTo Customer Name.");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		
		}else if(document.getElementById("disbursalAmount").value==""){
			alert(" Disbursal Amount is required");
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			saveStatus=false;
			return false;
		
		}
		if(document.getElementById("paymentFlag").checked==true){
			if(document.getElementById("paymentMode").value==""){
				alert("Payment Mode is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}else if(document.getElementById("paymentDate").value==""){
				alert("Payment Date is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}else if(document.getElementById("paymentDate").value!=""){
				var paymentDate=document.getElementById("paymentDate").value;
				var paymentDateObj=getDateObject(paymentDate,formatD.substring(2, 3));
				if(paymentDateObj>businessDateObj){
					alert("Payment Date can not greater than Business Date");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;	
				}
					
			}else if(document.getElementById("instrumentNumber").value==""){
				alert("Instrument Number  is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}else if(document.getElementById("paymentMode").value!="C"&&document.getElementById("instrumentDate").value==""){
				alert("Instrument Date is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			} else if(document.getElementById("bankAccount").value==""){
				alert("Bank Account is required");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
			
		   }else  if(document.getElementById("taFlag").checked==true){
			
			 if(document.getElementById("taLoanNo").value==""){
					alert("TA Loan is required");
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					saveStatus=false;
					return false;
				
				}
		}else if(document.getElementById("disbursalAmount").value!="" 
			&& document.getElementById("disbursalAmount").value!="" ){
			var disbursedAmount = removeComma(document.getElementById("disbursalAmount").value);
			var disbursalAmount = removeComma(document.getElementById("disbursalAmount").value);
			if(disbursalAmount>disbursalAmount){
				alert("Disbursal amount can not be greater than Current Dusbursal Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;	
			}
		} else if(document.getElementById("adjustTotalPayable").value!="" ){
			var adj=removeComma(document.getElementById("adjustTotalPayable").value);
			var pay=removeComma(document.getElementById("totalPayable").value);
			if(adj>pay){
				alert("Adjust Payable amount can not be greater than total payable Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			}
			
		}else if(document.getElementById("adjustTotalReceivable").value!="" ){
			var adj=removeComma(document.getElementById("adjustTotalReceivable").value);
			var rec=removeComma(document.getElementById("totalReceivable").value);
			if(adj>rec){
				alert("Adjust Receivable amount can not be greater than Total Receivable Amount");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				saveStatus=false;
				return false;
			}
			
		}
		if(saveStatus)
		{
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("disbursalAddDetailsForm").action = contextPath+"/disbursalMaker.do?method=updateDisbursalPaymentAddDetails" 
					/* &recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;*/
			document.getElementById("processingImage").style.display = '';
			document.getElementById("disbursalAddDetailsForm").submit();	
			return true;
		}else{
			return false;
		}

}
function activeSupplierForAddDetails(val)
{
	//alert("val : "+val);
	//document.getElementById('netAmount').value="0.0";
	document.getElementById('adjustTotalPayable').value="0.0";
	document.getElementById('adjustTotalReceivable').value="0.0";
	document.getElementById("businessPartnerName").setAttribute("disable","true");
	if(val=='CS')
	{
		document.getElementById("custDiv").style.display='';
		document.getElementById("businessPartnerTypeDescCust").removeAttribute("disabled","true");
		document.getElementById("businessPartnerTypeDescCust").readonly=true;
		
		document.getElementById("supplierDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
		document.getElementById("customerLableDiv").style.display='';
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDescCust").value=document.getElementById("customerName").value;
		
		document.getElementById("lbxBusinessPartnearHID").setAttribute("disable","true");
		document.getElementById("businessPartnerName").setAttribute("disable","true");
		document.getElementById("businessPartnerType").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").readonly=false;
	}
	else if(val=='SU')
	{
		//alert("val su : "+val);
		document.getElementById("custDiv").style.display='none';
		document.getElementById("businessPartnerTypeDescCust").setAttribute("disabled","true");
		document.getElementById("businessPartnerTypeDescCust").readonly=false;
		
		document.getElementById("supplierDiv").style.display='';
		document.getElementById("supplierLableDiv").style.display='';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
		document.getElementById("customerLableDiv").style.display='none';
		
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		
		
		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
		document.getElementById("businessPartnerName").removeAttribute("disable","true");
		document.getElementById("businessPartnerType").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
		
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';
	}else if(val=='OTH'){
		//alert("In oth");
		document.getElementById("custDiv").style.display='';
		document.getElementById("businessPartnerTypeDescCust").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDescCust").removeAttribute("readonly","true");
		document.getElementById("businessPartnerTypeDescCust").removeAttribute("disabled","true");
		document.getElementById("businessPartnerTypeDescCust").removeAttribute("readOnly","true");
		//document.getElementById("businessPartnerTypeDescCust").readonly=false;
		
		document.getElementById("supplierDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='';
		document.getElementById("customerLableDiv").style.display='none';
		
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		document.getElementById("businessPartnerTypeDescCust").value='';	
		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
		document.getElementById("businessPartnerName").setAttribute("disable","true");
		document.getElementById("businessPartnerType").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").setAttribute("readonly","true");
		document.getElementById("businessPartnerTypeDesc").readonly=false;
		
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';
	}
	else if(val=='MF'){
		//alert("val mf : "+val);
		document.getElementById("supplierDiv").style.display='';
		document.getElementById("custDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
		document.getElementById("customerLableDiv").style.display='none';
		
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		document.getElementById("businessPartnerTypeDescCust").value='';
		
		document.getElementById("lbxBusinessPartnearHID").removeAttribute("disable","true");
		document.getElementById("businessPartnerName").removeAttribute("disable","true");
		document.getElementById("businessPartnerType").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").removeAttribute("disable","true");
		document.getElementById("businessPartnerTypeDescCust").setAttribute("disabled","true");
		document.getElementById("businessPartnerTypeDescCust").readonly=true;
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';
	}else{
		document.getElementById("custDiv").style.display='';
		document.getElementById("supplierDiv").style.display='none';
		document.getElementById("manufactLableDiv").style.display='none';
		document.getElementById("supplierLableDiv").style.display='none';
		document.getElementById("otherLableDiv").style.display='none';
		document.getElementById("customerLableDiv").style.display='';
		document.getElementById("lbxBusinessPartnearHID").value='';
		document.getElementById("businessPartnerName").value='';
		document.getElementById("businessPartnerType").value='';
		document.getElementById("businessPartnerTypeDesc").value='';
		document.getElementById("businessPartnerTypeDescCust").value='';	
		document.getElementById("lbxBusinessPartnearHID").setAttribute("disable","true");
		document.getElementById("businessPartnerName").setAttribute("disable","true");
		document.getElementById("businessPartnerType").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDesc").setAttribute("disable","true");
		document.getElementById("businessPartnerTypeDescCust").setAttribute("disabled","true");
	}
}	
function calculateNetAmount(){
	var adjustTotalPayable=document.getElementById("adjustTotalPayable").value;
	var adjustTotalReceivable=document.getElementById("adjustTotalReceivable").value;
	var disbursalAmount=document.getElementById("disbursalAmount").value;
	
	var adjustTotalPayableFloat=0;
	var adjustTotalReceivableFloat=0;
	var disbursalAmountFloat=0;
	var netAmount=0;
	if(adjustTotalPayable==""){
		adjustTotalPayable="0";
	}
	if(adjustTotalReceivable==""){
		adjustTotalReceivable="0";
	}
	if(disbursalAmount==""){
		disbursalAmount="0";
	}
	adjustTotalPayableFloat=removeComma(adjustTotalPayable);
	adjustTotalReceivableFloat=removeComma(adjustTotalReceivable);
	disbursalAmountFloat=removeComma(disbursalAmount);
	
	//alert(adjustTotalPayable +"---"+adjustTotalReceivable +"---"+disbursalAmount);
	netAmount=(disbursalAmountFloat+adjustTotalPayableFloat)-adjustTotalReceivableFloat;
	//alert("netAmount:---------"+netAmount);
	document.getElementById("netAmount").value=netAmount;
}

function deleteAddDetail(){
	var flag=0;
	var checkbx=document.getElementsByName("chk");
	var checkedStr="";
//	alert("befo if :--"+checkbx.length);
	if(checkbx.length>0){
	for(var i=0;i<checkbx.length;i++){
		if(checkbx[i].checked==true){
			flag=1;
			checkedStr=checkedStr+checkbx[i].value+",";
		}
		
	}
	}
	//alert("checkedStr:-----"+checkedStr);
	if(flag==1){
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("disbursalMakerForm").action = contextPath+"/disbursalMaker.do?method=deleteAddDetail&checkedStr="+checkedStr ;
				/* &recStatus="+recStatus+"&chk=F&check="+check+"&nextDueDate="+nextDueDate;*/
		document.getElementById("processingImage").style.display = '';
		document.getElementById("disbursalMakerForm").submit();		
	}else{
		alert("Please Select a checkbox to delete add details");
	}
	
}

function getTotalPayableReceivable(val)
{
	//alert("val getShortPayOnDisbursalTo:"+val);
	 var loanId=document.getElementById('lbxLoanNoHID').value;
	var disTo=val;
	//alert(disTo);
	 if(loanId!='')
	 {
		 //alert("loanId :"+loanId);
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/disbursalMaker.do?method=getTotalPayableReceivable&disbursalTo="+disTo;
		 var data = "lbxLoanNoHID="+loanId;
		 sendDisbursalPayableReceivable(address, data);
		 return true;
	 }else{
		 alert("Please select Loan First");
		 document.getElementById('disbursalTo').value="";
	 }
	 
}

function sendDisbursalPayableReceivable(address, data) {
	//alert("in sendDisbursalloanId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultPayableReceivable(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultPayableReceivable(request)
{
	
	var s1;
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		s1 = str.split("$:");
		//alert("s1:---"+s1);
		document.getElementById('totalPayable').value=trim(s1[0]);
		document.getElementById('totalReceivable').value=trim(s1[1]);
		//alert("filed "+document.getElementById('totalReceivableCustomer').value);
		//alert("customer amount "+trim(s1[2]));
		document.getElementById('totalReceivableCustomer').value=trim(s1[2]);
		
		}

}

function allCheck(){
	var checkbx=document.getElementsByName("chk");
//	alert("befo if :--"+checkbx.length);
	if(checkbx.length>0){
		if(document.getElementById("allChk").checked==true){
	for(var i=0;i<checkbx.length;i++){
		checkbx[i].checked=true;
		}
	 }else{
		 for(var i=0;i<checkbx.length;i++){
				checkbx[i].checked=false;
			}
		}
	 }
}
function fnNull(){
    
    document.getElementById('instrumentNumber').value="";
    document.getElementById('instrumentDate').value=document.getElementById('businessdate').value ;
	 document.getElementById('bankAccount').value="";	
	 document.getElementById('lbxbankAccountID').value="";
	 document.getElementById('bank').value="";
	 document.getElementById('lbxBankID').value="";
	 document.getElementById('branch').value="";
	 document.getElementById('lbxBranchID').value="";
	 document.getElementById('micr').value="";
	 document.getElementById('ifsCode').value="";
	 return true;		 
}
function getAccountType(){
	 var accountType=document.getElementById('paymentMode').value;
	
	 if(accountType=='Q'||accountType=='D'||accountType=='N'||accountType=='R'){
		 
		 document.getElementById('lbxpaymentMode').value='B' ;
		
	 }else{
		 document.getElementById('lbxpaymentMode').value=accountType ;	 
	 }
}

function openOldInstallmentPlan(alert1)
{
	//alert("Open Disbursal Schedule");
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var table = document.getElementById("listTable");	
	var repaymentType=document.getElementById("repaymentType").value;
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
    }else{
    	loanId=document.getElementById("lbxLoanNoHID").value;
    }
	if (loanId=="")
	{
		alert(alert1);	
		return false;
	}
	else if(loanId != "" && repaymentType != 'I')
	{
		alert("Old Installment Plan is only for installment based loan.");
		return false;
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/disbursalSearchBehind.do?method=openOldInstallmentPlan&lbxloannohid="+loanId;
		//alert("url: "+url);
		//window.open(url,'Disbursal Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		
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

function openNewInstallmentPlan(alert1,stage)
{
	//alert("Open Disbursal Schedule");
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var disbId="";
	var table = document.getElementById("listTable");
	var recoveryType=document.getElementById("recoveryType").value;
	var disbursalFlag=document.getElementById("disbursalFlag").value;
	var repaymentType=document.getElementById("repaymentType").value;
	var rowCount = 0;
	//alert("recoveryType : " + recoveryType);
	//alert("disbursalFlag : " + disbursalFlag);
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
		if(stage=='DIM')
			disbId = document.getElementById("chk1").value;
		else
			disbId = document.getElementById("loanDisbursalId").value;
    }
	if (loanId=="")
	{
		alert("First save the disbursal details.");	
		return false;
	}
//	else if((loanId != "") && (repaymentType != 'I' || recoveryType != 'F' || disbursalFlag != 'F'))
	else if((loanId != "") && (repaymentType != 'I' || disbursalFlag != 'F'))
	{
		if(repaymentType != 'I')
		{
			alert("New Installment Plan is only for installment based loan.");
			return false;
		}
		//if(recoveryType != 'F' || disbursalFlag != 'F')
		if( disbursalFlag != 'F')
		{
			alert("Loan is Final Disbursed");
			return false;
		}
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/disbursalSearchBehind.do?method=openNewInstallmentPlan&lbxloannohid="+loanId+"&disbursalId="+disbId+"&stage="+stage;
		//alert("url: "+url);
		//window.open(url,'Disbursal Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		
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

function onSaveNewInstal()
{
	DisButClass.prototype.DisButMethod();
	
	var contextPath=document.getElementById("contextPath").value;
	var recoveryType=document.getElementById("recoveryType").value;
	var loanAmount=document.getElementById("loanAmount").value;
	var loanId=document.getElementById("lbxloannohid").value;
	var disbursalId=document.getElementById("disbursalId").value;
	var disbrsedAmount=document.getElementById("disbrsedAmount").value;
	var formatD=document.getElementById("formatD").value;
	var disbrsalAmount=document.getElementById("disbrsalAmount").value;
	var repayEffDate=document.getElementById("repayeffdate").value;
	var repayEffDateObj="";
	repayEffDateObj=getDateObject(repayEffDate,formatD.substring(2, 3));

	
	var dueDateObj="";

	// var fromInstallment=document.getElementById("fromInstallment").value;
	// var toInstallment=document.getElementById("toInstallment").value;
	var installmentType=document.getElementById("installmentType").value;
	var totalInstallment=document.getElementById("totalInstallment").value;
	var editDueDate= document.getElementById("editDueDate").value;

	var gridTable = document.getElementById('gridTable');
	var tableLength = gridTable.rows.length-1;
	//alert("disbursalId : " + disbursalId);

	  var sum=0;
	  var psum=0;
	  var isum=0;
	  var totalDisbursal=0;
	  if(disbrsedAmount != '' && disbrsalAmount !='')
		  totalDisbursal=parseFloat(disbrsedAmount) + parseFloat(disbrsalAmount);
	  else
		  totalDisbursal=parseFloat(disbrsalAmount);
	 // alert("totalDisbursal : " + totalDisbursal);
	 
	  
	  /******************************************************   */
	  
	  
	  if(editDueDate=='Y'){
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
			
			//alert("installmentType : " + installmentType);
			document.getElementById("installmentPlanForm").action = contextPath+"/disbursalSearchBehind.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&lbxloannohid="+loanId+"&disbursalId="+disbursalId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("installmentPlanForm").submit();
		     return true;
		  
	  }
	  
	  else {
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
			document.getElementById("installmentPlanForm").action = contextPath+"/disbursalSearchBehind.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&lbxloannohid="+loanId+"&disbursalId="+disbursalId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("installmentPlanForm").submit();
		     return true;
		  
	  }
	  
}
	  
	  /***********************************************************/
	  
	  
	/*for(var i=1;i<=tableLength;i++)
	{
		

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

	var lastToInstall=document.getElementById("toInstallment"+tableLength).value;	

if(parseInt(lastToInstall)> parseInt(totalInstallment)){
		alert("no of  Installment should be equal to  Total Installment");
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
		alert("total recovery Percentage should be  equal to 100 %");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((psum>totalDisbursal && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'))
	{
		alert("Principal Amount should be equal to disbursal amount: "+totalDisbursal);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if((isum<totalDisbursal && recoveryType=='F' && (installmentType=='E'||installmentType=='G')))
	{
		
		alert("Sum of Installment Amount should be equal to or greater than disbursal amount: "+totalDisbursal);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	//alert("installmentType : " + installmentType);
	document.getElementById("installmentPlanForm").action = contextPath+"/disbursalSearchBehind.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&lbxloannohid="+loanId+"&disbursalId="+disbursalId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("installmentPlanForm").submit();
     return true;
}
*/
function openOtherChargePlanAtDisbrsal(alert1)
{
	//alert("Open Disbursal Schedule");
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var disbId="";
	var table = document.getElementById("listTable");	
	var repaymentType=document.getElementById("repaymentType").value;
	var recoveryType=document.getElementById("recoveryType").value;
	var disbursalFlag=document.getElementById("disbursalFlag").value;
	
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
		disbId = document.getElementById("chk1").value;
    }else{
    	loanId=document.getElementById("lbxloannohid").value;
    }
	if (loanId=="")
	{
		alert(alert1);	
		return false;
	}
	else if((loanId != "") && (repaymentType != 'I' || disbursalFlag != 'F'))
	{
		if(repaymentType != 'I')
		{
			alert("Other Charge Plan is only for installment based loan.");
			return false;
		}
		if(disbursalFlag != 'F')
		{
			alert("Loan should be Final Disbursed");
			return false;
		}
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/disbursalSearchBehind.do?method=openOtherChargePlan&lbxloannohid="+loanId+"&disbursalId="+disbId;
		//alert("url: "+url);
		//window.open(url,'Disbursal Schedule','height=400,width=1000,top=200,left=250,scrollbars=yes');
		
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

function saveOtherChargesAtDisbursal()
{
	DisButClass.prototype.DisButMethod();
	
	var contextPath=document.getElementById("contextPath").value;
	var loanAmount=document.getElementById("loanAmount").value;
	var loanId=document.getElementById("lbxloannohid").value;
	var disbursalId=document.getElementById("disbursalId").value;
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
	  var toInstallment= document.getElementsByName("toInstall");
     var type= document.getElementsByName("type");
	  var amount= document.getElementsByName("amount");
	  var chargeDesc= document.getElementsByName("chargeDesc");
	  for(var j=0;j<fromInstall.length;j++)
	  {
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
		
		if (fromInstall.length>j+1)
		{
		
			if(parseInt(fromInstall[j+1].value)<=parseInt(toInstallment[j].value)){
			
	 			alert("Next From Installment should be greater than previous To Installment.");
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
	 		}
		}
 		if(parseInt(fromInstall[j].value)==0){
	 		alert("From Installment should be greater than zero");
	 		document.getElementById("save").removeAttribute("disabled","true");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;
	 	}
		var length=((fromInstall.length)-1);
		if(j==length)
		{
	 		if(parseInt(toInstallment[j].value)> parseInt(totalInstallment)){
	 			alert("To Installment should be less than or equal to Total Installment");
		 		document.getElementById("save").removeAttribute("disabled","true");
		 		DisButClass.prototype.EnbButMethod();
		 		return false;
		 	}
		}
	  }
	 
	
	document.getElementById("otherChargesPlanForm").action = contextPath+"/disbursalSearchBehind.do?method=saveOtherChargesPlanAtDisbursal&installmentType="+installmentType+"&lbxloannohid="+loanId+"&disbursalId="+disbursalId;
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
function viewRepaymentScheduleDisbursal()
{
	curWin = 0;
	otherWindows = new Array();
	var loanId="";	
	var table = document.getElementById("listTable");	
	var repaymentType=document.getElementById("repaymentType").value;
	var rowCount = 0;
	if(table!=null){
		rowCount = table.rows.length;
	}
	if(table!=null&&rowCount>1){
		
		loanId=document.getElementById("loanId1").value;
    }else{
    	loanId=document.getElementById("lbxLoanNoHID").value;
    }
	if (loanId=="")
	{
		alert(alert1);	
		return false;
	}
	else if(loanId != "" && repaymentType != 'I')
	{
		alert("Old Repay Schedule is only for installment based loan.");
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		// var loanId=document.getElementById('lbxloannohid').value;	
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



//Adding funtion AJAY
function datevalidate()
{
	var businessDate= document.getElementById("businessdate").value;
	var interestDueDate= document.getElementById("interestDueDateDis").value;
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

				//var cycleDate= document.getElementById("dueDay").value;
	var cycleDate= document.getElementsByName("hiddenDueDate")[0].value;
		if(interestCalculationMethod=='D')
		{
			if(cycleDate=='')
			{
				alert('First select  Due Day ');
				document.getElementById("interestDueDateDis").value="";
			return false;
		    }
			
			if(cycleDate!=parseFloat(iDay)  )
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
					alert('Interest Due Date Must be same as Due Day ');
				document.getElementById("interestDueDate").value="";
					return false;
				}
				}
	
}
return true;

		
}



//added for valid Interest Due Date by AJAY
function validateInterestDueDate()
{
	//alert('.......................');	
	var formatD=document.getElementById("formatD").value;
	var businessDate= document.getElementById("businessdate").value;
	var repayEffDate= document.getElementById("repayEffDate").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var interestDueDate= document.getElementById("interestDueDateDis").value;
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var iMonth=interestDueDate.substring(3,5);
	var iDay=interestDueDate.substring(0,2);
	var iYear=interestDueDate.substring(6);
	var rMonth = repayEffDate.substring(3,5); //months from 1-12
	var rDay = repayEffDate.substring(0,2);
	var rYear = repayEffDate.substring(6);
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
						var interestDueDate= document.getElementById("interestDueDateDis").value='';
						return false;
					}
				
				
				if(interestCalculationMethod=='F')
				{
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='M')
					{
						/*if(parseFloat(rMonth)> parseFloat(iMonth))		//added by brijesh*
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}*/
						//start here ||brijesh pathak ||28-8-2018
						if( parseFloat(iDay)<= parseFloat(rDay))
						{
							alert('Date should be Greater than Repayment Effective Date ');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}	
						//end here ||brijesh pathak ||28-02-2018
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='B')
					{
						if(parseFloat(rMonth)> parseFloat(iMonth))
						{
							alert('Please take  next  financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07' || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						if(parseFloat(rMonth)> parseFloat(iMonth))
						{
							alert('Please take  next  financial quarter month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						if(parseFloat(rMonth)> parseFloat(iMonth))
						{
							alert('Please take  next  financial half year  month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
									
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
							
					}
					//for idd greater year then red
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='B' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07'   || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
							if(parseFloat(iMonth)!='04')
							{
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
							}
							
					}
				}	
				if(interestCalculationMethod=='E')
				{
					if(parseFloat(iYear)==parseFloat(rYear))
					{
						/*if(parseFloat(rMonth)> parseFloat(iMonth))
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDateDis").value='';
							return false;
						}*/
						
							
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

// brijesh pathak ||start  here
	/*function generateNewInstallmentPlanPartPayment(type,stage)
	{
		 //alert("Inside generateInstallmentPlanPartPrepayment");
		 
		var loanId = document.getElementById("lbxLoanNoHID").value;
		var reschId = document.getElementById("reschId").value;
		alert("reschId "+reschId);
		if (reschId=='' || reschId=='null')
		{
			alert("Please Save First");	
			return false;
		}
		
		else
		{
			var contextPath = document.getElementById("contextPath").value;
			// alert(contextPath);
			var url="";
			if(type=='P')
				url= contextPath+"/installmentPlanViewPartPrePayment.do?method=viewNewInstallmentPlanPartPrepayment&loanId="+loanId+"&reschId="+reschId+"&stage="+stage+"&fromWhere=PartPrePayment";
				//url= contextPath+"/installmentPlanViewPartPrePayment.do?method=viewNewInstallmentPlanPartPrepayment&loanId="+loanId;
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

	}*/
/*function calculateMaturityDate()
{
	alert("abc123456");
	var repaymentType = document.getElementById('repaymentType').value;
	alert("123");
	if(repaymentType=='N')
	{
		alert("if");
		
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		alert("else");
		
		var x = parseInt(document.getElementById('tenureMonths').value); // or
																			// whatever
																			// offset
		var formatD=document.getElementById("formatD").value;
		
		// alert(x);
		var currDate =   document.getElementById('repayEffDate').value;
		
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
}*/
function calculateMaturityDateNew()
{
	//alert("under the function");
	var repaymentType = document.getElementById('repayMode').value;
	var editDueDate = document.getElementById('editDueDate').value;
	//alert("editDueDate "+editDueDate);	
	if(repaymentType=='N')
	{
		
		//alert("if");
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		//alert("else");
		var x = parseInt(document.getElementById('tenureMonths').value); // or
		//alert("tenure"+x);										
		var formatD=document.getElementById("formatD").value;		
		// alert(x);
		var currDate =   document.getElementById('repayEffDate').value;
		
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
	   	//var abc = getElementById('maturityDate1').value;
		//alert("abc"+abc);
		if (document.getElementById("finalDisbursal").checked && document.getElementById("installmentType").value=='S' && document.getElementById("editDueDate").value=='Y'){
			document.getElementById("MaturityView").style.display="";
			document.getElementById("MaturityView1").style.display="";
		}
		else{
			document.getElementById("MaturityView").style.display="none";
			document.getElementById("MaturityView1").style.display="none";
			document.getElementById("maturityDate1").value="";
		}
	}
}
function validateMaturityDateForDIS()
{
	
	var UpdateDateStr1;

		
		var formatD=document.getElementById("formatD").value;

		//alert(formatD);
		var currDate =   document.getElementById('OldMaturityDate1').value;
		
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
			  //alert("if");
			  UpdateDateStr1 = currMonth + seprator + currDay + seprator + currYear;
		  }
		  else
		  {
			  //alert("else");
			  seprator = '/';
		    UpdateDateStr1 = currMonth + seprator + currDay + seprator + currYear;
		    //alert(UpdateDateStr);
			
		  }
		  var CurrentDate = new Date(UpdateDateStr1);
		
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
			} */
			
	

}
function setMaturityTemp()
{
	//alert("in temp function");
	var maturityDate=document.getElementById('maturityDate1').value;	 
	/*if(maturityDate!= null ||maturityDate!= "")
	{
		document.getElementById('OldMaturityDate1').value=maturityDate;
	}*/
	document.getElementById('OldMaturityDate1').value=maturityDate;
	//  document.getElementById('maturityDate').value=ModDateStr;		  
	//  document.getElementById('OldMaturityDate1').value=ModDateStr;	
}
//code added on 23-08-2018
function calTenureMonthForMaturityDateNextDueDateDIS()
{
	//alert("sachin456");
	var nextDueDate= document.getElementById("nextDueDate").value;
	// var daysBasis= document.getElementById("daysBasis").value;
	var formatD=document.getElementById("formatD").value;
	//var installmentType= document.getElementById("");
	var repayEffDate =   document.getElementById('repayEffDate').value; // added on 23-08-2018
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
	//document.getElementById("tenureInDay").value=finalDays;
	//alert("in else");
	document.getElementById("tenureMonths").value=finalMonth;
	//document.getElementById("loanNoofInstall").value=finalMonth;
	//brijesh start here 22-08-2018
	if(currMonth > repayEffMonth && finalMonth > 72)
	{
		//alert("new alert");
		//document.getElementById("tenureInDay").value=finalDays;
		document.getElementById("tenureMonths").value=finalMonth-1;
		//document.getElementById("loanNoofInstall").value=finalMonth-1;
	}
	else if (currMonth == repayEffMonth && finalMonth>72)
	{
		//alert("new alert else");
		//document.getElementById("tenureInDay").value=finalDays;
		document.getElementById("tenureMonths").value=finalMonth-1;
		//document.getElementById("loanNoofInstall").value=finalMonth-1;
	}		
}

function calTenureMonthForMaturityDateDIS()
{
	//alert("brijesh at DIS");                             
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
				//document.getElementById("loanNoofInstall").value=finalMonth;//+1;
				//document.getElementById("tenureInDay").value=temp123*30.42;
			}
			else
			{
				var tenureDay = document.getElementById("tenureInDay").value;
				var tempMonth = oldMonth - newMonth;
				//alert("tempMonth "+tempMonth);
				var calc = Math.floor(tenureDay/30.42)-tempMonth;
				//alert("calc "+calc);
				document.getElementById("tenureMonths").value=calc-tempMonth;
				//document.getElementById("loanNoofInstall").value=calc-tempMonth;
				//var newTempMonth = document.getElementById("loanNoofInstall").value;
				//document.getElementById("tenureInDay").value=newTempMonth*30.42;					 
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
				//document.getElementById("loanNoofInstall").value=finalMonth;//+1;
				//document.getElementById("tenureInDay").value=temp123*30.42;
			}
			else
			{
				var a = document.getElementById("tenureMonths").value;				
				var b = newMonth-oldMonth;
				var c = parseInt(a) + parseInt(b);
				//alert("tempTenure "+c);
				document.getElementById("tenureMonths").value=c;
					//document.getElementById("loanNoofInstall").value=c;	
					//var newTempMonth2 = document.getElementById("loanNoofInstall").value;
					//document.getElementById("tenureInDay").value=newTempMonth2*30.42;					
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
					//alert("brijesh first");
					var finalMonth=Math.abs(difference)-1;
				}
				else
				{
					//alert("brijesh second");
					var finalMonth=Math.abs(difference)-1;
				}					
			}					
			document.getElementById("tenureMonths").value=finalMonth;
			var temp123 = document.getElementById("tenureMonths").value;
			//document.getElementById("loanNoofInstall").value=finalMonth;
			//document.getElementById("tenureInDay").value=temp123*30.42;		
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
			//document.getElementById("loanNoofInstall").value=finalMonth+1;
			//document.getElementById("tenureInDay").value=temp123*30.42;
		}
			//changes end here
		var finalDays2 = Math.floor(document.getElementById("tenureMonths").value*30.42);
		//var brijesh = math.ceil(finalDays2+result);
		//alert("brijesh "+brijesh);
		//document.getElementById("tenureInDay").value=finalDays2+result;
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
		//document.getElementById("tenureInDay").value=res+res2;
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
				//var temp123 = document.getElementById("tenureMonths").value;
				//document.getElementById("loanNoofInstall").value=finalMonth;//+1;
				//document.getElementById("tenureInDay").value=temp123*30;
			}
			else
			{
				var tenureDay = document.getElementById("tenureInDay").value;
				var tempMonth = oldMonth - newMonth;
				//alert("tempMonth "+tempMonth);
				var calc = Math.floor(tenureDay/30)-tempMonth;
				//alert("calc "+calc);
				document.getElementById("tenureMonths").value=calc-tempMonth;
				//document.getElementById("loanNoofInstall").value=calc-tempMonth;
				//var newTempMonth = document.getElementById("loanNoofInstall").value;
				//document.getElementById("tenureInDay").value=newTempMonth*30;					 
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
				//var temp123 = document.getElementById("tenureMonths").value;
				//document.getElementById("loanNoofInstall").value=finalMonth;//+1;
				//document.getElementById("tenureInDay").value=temp123*30;
			}
			else
			{
				var a = document.getElementById("tenureMonths").value;				
				var b = newMonth-oldMonth;
				var c = parseInt(a) + parseInt(b);
				//alert("tempTenure "+c);
				document.getElementById("tenureMonths").value=c;
				//document.getElementById("loanNoofInstall").value=c;	
				//var newTempMonth2 = document.getElementById("loanNoofInstall").value;
				//document.getElementById("tenureInDay").value=newTempMonth2*30;					
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
			//var temp123 = document.getElementById("tenureMonths").value;
			//document.getElementById("loanNoofInstall").value=finalMonth;
			//document.getElementById("tenureInDay").value=temp123*30;		
		}
		else if(newYear != oldYear)
		{
			//alert("under the 2345678 ");				
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
			//document.getElementById("loanNoofInstall").value=finalMonth+1;
			//document.getElementById("tenureInDay").value=temp123*30;
		}
		//temp end here				
	}
}
function calTenureMonthForMaturityDateDIS2()
{
	//alert("new alert at maturity");
	var nextDueDate= document.getElementById("nextDueDate").value;
	// var daysBasis= document.getElementById("daysBasis").value;
	var formatD=document.getElementById("formatD").value;
	//var installmentType= document.getElementById("");
	var repayEffDate =   document.getElementById('repayEffDate').value; // added on 23-08-2018
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
	//document.getElementById("tenureInDay").value=finalDays;
	//alert("in else");
	document.getElementById("tenureMonths").value=finalMonth;
	//document.getElementById("loanNoofInstall").value=finalMonth;
	//brijesh start here 22-08-2018
	if( finalMonth > 72 && finalMonth <144)
	{
		//document.getElementById("tenureInDay").value=finalDays;
		document.getElementById("tenureMonths").value=finalMonth-1;
		//document.getElementById("loanNoofInstall").value=finalMonth-1;
	}
	else if (currMonth == repayEffMonth && finalMonth > 72)
	{
		if ( finalMonth > 144 )//&& finalMonth <180)
		{
			//alert("new alert else second");
			//document.getElementById("tenureInDay").value=finalDays;
			document.getElementById("tenureMonths").value=finalMonth-2;			
			//document.getElementById("loanNoofInstall").value=finalMonth-1;
		}
		else
		{
			//alert("new alert else third");
			//document.getElementById("tenureInDay").value=finalDays;
			document.getElementById("tenureMonths").value=finalMonth-1;
			//document.getElementById("loanNoofInstall").value=finalMonth-1;
		}
	}		
}
//code end here on 23-08-2018
// brijesh pathak | ends here
