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

function cleanField(val)
{
	if(val=='L')
	{
		document.getElementById('loanAccountNo').value="";
		document.getElementById('lbxLoanNoHID').value="";
		document.getElementById('hCommon').value="";
		document.getElementById('waveOffId').value="";
		document.getElementById('customerName').value="";
	}
	if(val=='B'||val=='L')
	{
		document.getElementById('businessPartnerTypeDesc').value="";
		document.getElementById('businessPartnerType').value="";
		document.getElementById('lbxBusinessPartnearHID').value="";
		document.getElementById('businessPartnerName').value="";
	}
	
	document.getElementById('chargeType').value="";
	document.getElementById('lbxChargeCodeHID').value="";
	document.getElementById('chargeDescription').value="";
	document.getElementById('chargeAmount').value="";
	document.getElementById('waivOffAmount').value="";
	document.getElementById('ChargeNewAmount').value="";
	document.getElementById('taxAmount1').value="";
	document.getElementById('waveAmountForTaxAmt1').value="";
	document.getElementById('tax1NewAmt').value="";
	document.getElementById('taxAmount2').value="";
	document.getElementById('waveAmountForTaxAmt2').value="";
	document.getElementById('tax2NewAmt').value="";
	document.getElementById('adviceAmount').value="";
	document.getElementById('totalWaveOffAmt').value="";
	document.getElementById('newAdviceAmt').value="";
	document.getElementById('txnAdjustedAmount').value="";
	document.getElementById('amountInProcess').value="";
	document.getElementById('balanceAmount').value="";
	document.getElementById('remarks').value="";
	document.getElementById('newBalanceAmt').value="";
}

function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('waiveOffMakerFormCSE').loanButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')	
     {
    	 document.getElementById('waiveOffMakerFormCSE').waivOffAmount.focus();
     }
   
     return true;
}	

function newAmount1()
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
	var taxRate1 =0.0000;
	var taxRate2 =0.0000;
	var newBalAmt=0.0000;
	var txnAdjustedAmount = 0.0000;
	var amountInProcess=0.0000;
	
	if(document.getElementById("waivOffAmount").value!="")
	{
		var wa=document.getElementById("waivOffAmount").value;
		if(wa!='')
			waivOffAmount=parseFloat(removeComma(wa));
		
		var chA=document.getElementById("chargeAmount").value;
		if(chA!='')
			chargeAmount=parseFloat(removeComma(chA));
		if(parseFloat(waivOffAmount) > parseFloat(chargeAmount))
		{
			alert("Waive-Off's Charge Amount can't be greater than Charge Amount.");
			document.getElementById("waivOffAmount").value="";
			document.getElementById("totalWaveOffAmt").value ="";
			document.getElementById("ChargeNewAmount").value = "";
			document.getElementById("tax1NewAmt").value = ""; 
			document.getElementById("tax2NewAmt").value = "" ; 
			document.getElementById("newAdviceAmt").value ="" ; 
			document.getElementById("newBalanceAmt").value =""; 
			return false;
		}		
		var tr1=document.getElementById("taxRate1").value;
		var tr2=document.getElementById("taxRate2").value;
		if(tr1!='')
			taxRate1 = parseFloat(removeComma(tr1));
		if(tr2!='')
			taxRate2 = parseFloat(removeComma(tr2));
		
		var aa=document.getElementById("adviceAmount").value;
		if(aa!='')
			adviceAmt=parseFloat(removeComma(aa));
		
		var ta1=document.getElementById("taxAmount1").value;
		if(ta1!='')
			taxAmt1 = parseFloat(removeComma(ta1));
		
		var ta2=document.getElementById("taxAmount2").value;
		if(ta2!='')
			taxAmt2 = parseFloat(removeComma(ta2));
				
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
			txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
		
		
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
			amountInProcess = parseFloat(removeComma(amtInproce));
		
		if(document.getElementById("waveAmountForTaxAmt1").value!='')
			waveAmountForTaxAmt1 = parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt1").value));
		
		if(document.getElementById("waveAmountForTaxAmt2").value!='')
			waveAmountForTaxAmt2 = parseFloat(removeComma(document.getElementById("waveAmountForTaxAmt2").value));
		
		
		ChargeNewAmount = chargeAmount - waivOffAmount;
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		tax1NewAmt = taxAmt1- waveAmountForTaxAmt1;
		tax2NewAmt = taxAmt2- waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
	    newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		document.getElementById("ChargeNewAmount").value = ChargeNewAmount;
		document.getElementById("tax1NewAmt").value = tax1NewAmt ; 
		document.getElementById("tax2NewAmt").value = tax2NewAmt ; 
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		document.getElementById("newBalanceAmt").value = newBalAmt; 
		var floatChargeNewAmount = (ChargeNewAmount).toFixed(2);
		formatNumber(floatChargeNewAmount,'ChargeNewAmount');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		formatNumber(floattotalWaiveOff,'totalWaveOffAmt');
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
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
		var wata1=document.getElementById("waveAmountForTaxAmt1").value;
		if(wata1!='')
			waveAmountForTaxAmt1 =parseFloat(removeComma(wata1));
		
		var tm1=document.getElementById("taxAmount1").value;
		if(tm1!='')
			taxAmt1 = parseFloat(removeComma(tm1));
		if(parseFloat(waveAmountForTaxAmt1)>parseFloat(taxAmt1))
		{
			alert("	Waive Off's Tax Amount1 can't be greater than Tax Amount1.");
			document.getElementById("waveAmountForTaxAmt1").value="";
			document.getElementById("waveAmountForTaxAmt2").value="";
			document.getElementById("totalWaveOffAmt").value ="";
			document.getElementById("tax1NewAmt").value = "" ; 
			document.getElementById("newAdviceAmt").value ="" ; 
			document.getElementById("newBalanceAmt").value =""; 
			return false;
		}
	//	tax2NewAmt
		if(document.getElementById("waveAmountForTaxAmt2").value=="")
			document.getElementById("waveAmountForTaxAmt2").value="0.00";
		
		var wata2=document.getElementById("waveAmountForTaxAmt2").value;
		if(wata2!='')
			waveAmountForTaxAmt2 =parseFloat(removeComma(wata2));
		
		var cha=document.getElementById("chargeAmount").value;
		if(cha!='')
			chargeAmount =parseFloat(removeComma(cha));
		
		var wam=document.getElementById("waivOffAmount").value;
		if(wam!='')
			waivOffAmount= parseFloat(removeComma(wam));
		
		var adm=document.getElementById("adviceAmount").value;
		if(adm!='')
			adviceAmt= parseFloat(removeComma(adm));
		
		var tm2=document.getElementById("taxAmount2").value;
		if(tm2!='')
			taxAmt2 = parseFloat(removeComma(tm2));
				
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
				txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
			
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
				amountInProcess = parseFloat(removeComma(amtInproce));
			
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		tax1NewAmt = taxAmt1- waveAmountForTaxAmt1;
		tax2NewAmt=taxAmt2-waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
		newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		document.getElementById("tax1NewAmt").value = tax1NewAmt ; 
		document.getElementById("tax2NewAmt").value = tax2NewAmt ; 
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		document.getElementById("newBalanceAmt").value =newBalAmt; 
		var floattax1NewAmt = (tax1NewAmt).toFixed(2);
		formatNumber(floattax1NewAmt,'tax1NewAmt');
		var floattax2NewAmt = (tax2NewAmt).toFixed(2);
		formatNumber(floattax2NewAmt,'tax2NewAmt');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
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
		var wata2=document.getElementById("waveAmountForTaxAmt2").value;
		if(wata2!='')
			waveAmountForTaxAmt2 =parseFloat(removeComma(wata2));
		
		var tam2=document.getElementById("taxAmount2").value;
		if(tam2!='')
			taxAmt2 = parseFloat(removeComma(tam2));
		if(parseFloat(waveAmountForTaxAmt2)> parseFloat(taxAmt2))
		{
			alert("	Waive Off's Tax Amount2 can't be greater than Tax Amount2.");
			document.getElementById("waveAmountForTaxAmt2").value="";
			document.getElementById("totalWaveOffAmt").value ="";
			document.getElementById("tax2NewAmt").value = "" ; 
			document.getElementById("newAdviceAmt").value ="" ; 
			document.getElementById("newBalanceAmt").value =""; 
			return false;
		}
		if(document.getElementById("waveAmountForTaxAmt1").value=="")
			document.getElementById("waveAmountForTaxAmt1").value=0.00;
		
		var wata1=document.getElementById("waveAmountForTaxAmt1").value;
		if(wata1!='')
			waveAmountForTaxAmt1 =removeComma(wata1);
		
		
		
		document.getElementById("waveAmountForTaxAmt2").value=waveAmountForTaxAmt2;

		var cha=document.getElementById("chargeAmount").value;
		if(cha!='')
			chargeAmount=parseFloat(cha);
		
		var wam=document.getElementById("waivOffAmount").value;
		if(wam!='')
			waivOffAmount=parseFloat(removeComma(wam));
		
		var adm=document.getElementById("adviceAmount").value;
		if(adm!='')
				adviceAmt=removeComma(adm);
		
		var tam1=document.getElementById("taxAmount1").value;
		if(tam1!='')
				taxAmt1 = parseFloat(removeComma(tam1));
		
		var txnAdAmt = document.getElementById("txnAdjustedAmount").value;
		if(txnAdAmt!='')
			txnAdjustedAmount = parseFloat(removeComma(txnAdAmt));
				
		var amtInproce = document.getElementById("amountInProcess").value;
		if(amtInproce!='')
				amountInProcess = parseFloat(removeComma(amtInproce));
				
		totalWaiveOff = waivOffAmount+waveAmountForTaxAmt1+waveAmountForTaxAmt2;
		
		tax2NewAmt = taxAmt2- waveAmountForTaxAmt2;
		newAdviceAmt = adviceAmt - totalWaiveOff;
		newBalAmt = ( newAdviceAmt - txnAdjustedAmount - amountInProcess );
		document.getElementById("totalWaveOffAmt").value =totalWaiveOff;
		document.getElementById("tax2NewAmt").value = tax2NewAmt ; 
		document.getElementById("newAdviceAmt").value = newAdviceAmt ; 
		document.getElementById("newBalanceAmt").value = newBalAmt; 
		var floattax2NewAmt = (tax2NewAmt).toFixed(2);
		formatNumber(floattax2NewAmt,'tax2NewAmt');
		var floattotalWaiveOff = (totalWaiveOff).toFixed(2);
		var floatnewAdviceAmt = (newAdviceAmt).toFixed(2);
		var floatnewBalAmt = (newBalAmt).toFixed(2);
		formatNumber(floatnewAdviceAmt,'newAdviceAmt');
		formatNumber(floattotalWaiveOff,'totalWaveOffAmt');
		formatNumber(floatnewBalAmt,'newBalanceAmt');
		return true;
	}
}
function saveWaiveOffCSE()
{
	DisButClass.prototype.DisButMethod();
	var balAmount=0.00;
	var totAmount=0.00;
	var newBlsAmt=0.00;
	var balanceAmount=document.getElementById("balanceAmount").value;
	var totalWaveOffAmt=document.getElementById("totalWaveOffAmt").value;
	var newBalanceAmt=document.getElementById("newBalanceAmt").value;
	var n = document.getElementById("new").value;
	if(balanceAmount!='')
		balAmount=parseFloat(removeComma(balanceAmount));
	
	if(document.getElementById("adviceDtChngFlag").value=='N')
	{
		if(newBalanceAmt!='')
		{
			newBlsAmt=parseFloat(removeComma(newBalanceAmt));
			if(parseFloat(newBlsAmt) < 0  )
			{
				 alert("Effective Balance Amount can't be Negative.");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			}
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
				  DisButClass.prototype.EnbButMethod();
				  return false;
			}
		  if(totAmount<=balAmount)
		  {
			  var contextPath=document.getElementById("contextPath").value;
			  document.getElementById("waiveOffMakerFormCSE").action = contextPath+"/waiveOffMakerDispatchAction.do?method=waiveOffMakerCSESave&new="+n;
			  document.getElementById("processingImage").style.display = '';
			  document.getElementById("waiveOffMakerFormCSE").submit();
			  return true;
		  }
		  else
		  {
			  alert("Waveoff Amount must be less than or equal to Balanced Amount");
			  DisButClass.prototype.EnbButMethod();
			  return false;
		  }
		 
		}else{		
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	else
	{
		 alert("Please select charge code again due to change in value date");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	}
	
}
function waiveOffMakerCSESaveForword()
{
	DisButClass.prototype.DisButMethod();
	var newBlsAmt=0.00;
	var balanceAmount=document.getElementById("balanceAmount").value;
	var newBalanceAmt=document.getElementById("newBalanceAmt").value;
	var totalWaveOffAmt=document.getElementById("totalWaveOffAmt").value;
	if(totalWaveOffAmt!='')
		totAmount=parseFloat(removeComma(totalWaveOffAmt));
	if(newBalanceAmt!='')
		newBlsAmt=parseFloat(removeComma(newBalanceAmt));
	
	if(validateWaiveOffMakerSaveDynaValidatorForm(document.getElementById("waiveOffMakerFormCSE")))
	{
		var contextPath=document.getElementById("contextPath").value;
		if(document.getElementById("waveOffId").value=='')
		{
			alert("Please Save First");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(parseFloat(newBlsAmt) <0)
		{
			 alert("Effective Balance Amount can't be Negative.");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		}
		if(totAmount <=0 )
		{
			alert("Waveoff Amount must be greater than zero");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			 //Ravi start
			
			    if(document.getElementById("loanRecStatus").value!='')
			    {
			    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
			    	{
			    		var status = confirm("Loan is on pending stage. Do you want to continue..");
			    		//alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("Forward").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}else if(document.getElementById("loanRecStatus").value=='C')
			    	{
			    		var status = confirm("Loan is close. Do you want to continue..");
			    		//alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("Forward").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}
			    	
			    }
			 			    //Ravi End
			
			document.getElementById("waiveOffMakerFormCSE").action = contextPath+"/waiveOffMakerDispatchAction.do?method=waiveOffMakerCSESaveForword";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("waiveOffMakerFormCSE").submit();
			return true;
		}
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function viewPayableWaiveOff(alert1) 
{	

	var loanId=document.getElementById('lbxLoanNoHID').value;
	var bpType=document.getElementById('lbxBusinessPartnearHID').value;
		
	
	if ((loanId=="") || (bpType==""))
		{
		DisButClass.prototype.DisButMethod();
			alert(alert1);
			document.getElementById("viewPay").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
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
		DisButClass.prototype.DisButMethod();
		alert(alert1);
			document.getElementById("viewRec").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
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
//function callNetReceivablePayable()
//{
//	var sum=0;
//	var balPrincipal = removeComma(document.getElementById("balancePrincipal").value);
//	//alert("balPrincipal: ",balPrincipal);
//	var overdueInstallments = removeComma(document.getElementById("overdueInstallments").value);
//	//alert("overdueInstallments: ",overdueInstallments);
//	var interestTillDate = removeComma(document.getElementById("interestTillDate").value);
//	//alert("interestTillDate: ",interestTillDate);
//	var unBilledInstallments = removeComma(document.getElementById("unBilledInstallments").value);
//	//alert("unBilledInstallments: ",unBilledInstallments);
//	if(document.getElementById("foreClosurePenalty").value=='')
//		document.getElementById("foreClosurePenalty").value=0;
//	var foreClosurePenalty = removeComma(document.getElementById("foreClosurePenalty").value);
//	//alert("foreClosurePenalty: ",foreClosurePenalty);
//	var otherDues = removeComma(document.getElementById("otherDues").value);
//	//alert("otherDues: ",otherDues);
//	var otherRefunds = removeComma(document.getElementById("otherRefunds").value);
//	//alert("otherRefunds: ",otherRefunds);
//	var lppAmount = removeComma(document.getElementById("lppAmount").value);
//	//alert("lppAmount: ",lppAmount);
//	var interstTillLP = removeComma(document.getElementById("interstTillLP").value);
//	//alert("interstTillLP: ",interstTillLP);
//	var netReceivablePayable=0;
//	netReceivablePayable = (balPrincipal+overdueInstallments+interestTillDate+unBilledInstallments+foreClosurePenalty+otherDues+lppAmount+interstTillLP)-otherRefunds;
//	//alert("netReceivablePayable: ",netReceivablePayable);
//	document.getElementById("netReceivablePayable").value = roundNumber(netReceivablePayable,2);
//}
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
function searchWaiveOff()
{
	DisButClass.prototype.DisButMethod();
	if( document.getElementById("loanAccountNo").value=="" && document.getElementById("customerName").value=="" && document.getElementById("businessPartnerType").value=="" && document.getElementById("businessPartnerName").value=="" && document.getElementById("adviceAmount").value=="" && document.getElementById("waivOffAmount").value==""  && document.getElementById("reportingToUserId").value=="" )
	{
		alert("Please Enter One Field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}	
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("waiveOffMakerForm").action = contextPath+"/waiveOffMakerAction.do?method=searchWaiveOff";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("waiveOffMakerForm").submit();
	}
} 
function openNewWaifOff(flag)
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
  document.getElementById("waiveOffMakerForm").action = contextPath+"/waiveOffMakerAction.do?method=openNewWaivOff&Flag="+flag;
  document.getElementById("processingImage").style.display = '';
  document.getElementById("waiveOffMakerForm").submit();
	
} 
function searchWaiveOffAuthor()
{
	DisButClass.prototype.DisButMethod();
	if( document.getElementById("loanAccountNo").value=="" && document.getElementById("customerName").value=="" && document.getElementById("businessPartnerType").value=="" && document.getElementById("businessPartnerName").value=="" && document.getElementById("adviceAmount").value=="" && document.getElementById("waivOffAmount").value==""&& document.getElementById("reportingToUserId").value=="" )
	{
	alert("Please Enter One Field");
	DisButClass.prototype.EnbButMethod();
	return false;
	}
	else
	{

	var contextPath=document.getElementById("contextPath").value;
  document.getElementById("waiveOffAuthorForm").action = contextPath+"/waiveOffAuthorAction.do?method=searchWaiveOffAuthor";
  document.getElementById("processingImage").style.display = '';
  document.getElementById("waiveOffAuthorForm").submit();
	}
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
function saveWaiveOffCSEAuthor()
{		
	DisButClass.prototype.DisButMethod();
	var newBalanceAmt =0.00;
	if((document.getElementById("remarks").value=="") && !(document.getElementById("decision").value=="A" ))
	{
		alert("Please Select the required field ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}
	else
	{
	var decision=document.getElementById("decision").value;
	var remarks = document.getElementById("remarks").value;
	//alert(decision);
	newBalanceAmt = document.getElementById("newBalanceAmt").value;
	var contextPath=document.getElementById("contextPath").value;
	//alert("newBalanceAmt "+newBalanceAmt);
	if(newBalanceAmt < 0 )
	{
		alert("Cannot approve this waive off due to negative balance amount");
	     DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
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
		
		document.getElementById("WaiveOffAuthorPage").action = contextPath+"/waiveOffAuthorDispatchAction.do?method=saveWaiveOffAuthor&decision="+decision+"&remarks="+remarks;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("WaiveOffAuthorPage").submit();
	}
	}
  
	
}

function deleteWaiveOff(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var waveOffId=document.getElementById("waveOffId").value;
	//alert("waveOffId......"+waveOffId);
	agree=confirm("Are you sure,You want to delete this lead.Do you want to continue?");
	if (!(agree))
	{
    	document.getElementById("Save").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("waiveOffMakerFormCSE").action=sourcepath+"/waiveOffMakerDispatchAction.do?method=deleteWaiveoff&waveOffId"+waveOffId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("waiveOffMakerFormCSE").submit();
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
 			//document.getElementById("Save").setAttribute("disabled","true");
 			//document.getElementById("Save & Forward").setAttribute("disabled","true");
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
    	 
    	// document.getElementById("Save").setAttribute("disabled","true");
    	//document.getElementById("Save & Forward").setAttribute("disabled","true");
      	return true;
     }
 }