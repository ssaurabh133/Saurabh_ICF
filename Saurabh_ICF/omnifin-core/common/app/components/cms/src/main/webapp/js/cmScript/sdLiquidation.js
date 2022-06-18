function generateSDAccrual()
{   
	var sdNo=document.getElementById("sdNo").value;
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var msg1="";
	var msg2="";
	if(loanId == "")
		msg1="* Loan Number is required.\n";
	if(sdNo == "")
		msg2="* SD Number is required.";
	if(loanId == "" ||sdNo == "")
	{
		alert(msg1+""+msg2);
		document.getElementById("liquidationFlag").value='P';
		if(loanId == "")
			document.getElementById("loanLov").focus();
		if(loanId == "")
			document.getElementById("sdLov").focus();
		return false;
	}	
	var liquidationFlag = document.getElementById("liquidationFlag").value;
	if(liquidationFlag=='P')
	{
		var sdTotalInterest= document.getElementById("sdTotalInterest").value;
		document.getElementById("sdInterestAccrued").value=sdTotalInterest;
		document.getElementById("sdTotalInterest").style.display="";
		document.getElementById("sdFinalInterest").style.display="none";
		document.getElementById("partialI").style.display='block';
		document.getElementById("partialT").style.display='block';
		document.getElementById("finalI").style.display='none';
		document.getElementById("finalT").style.display='none';
		document.getElementById("liquidationAmountPrincipal").value='';
		document.getElementById("liquidationAmountInterest").value='';
		return true;
	}
	if(liquidationFlag=='F')
	{
		var sdFinalInterest= document.getElementById("sdFinalInterest").value;
		document.getElementById("sdInterestAccrued").value=sdFinalInterest;
		document.getElementById("sdTotalInterest").style.display="none";
		document.getElementById("sdFinalInterest").style.display="";
		document.getElementById("partialI").style.display='none';
		document.getElementById("partialT").style.display='none';
		var contextPath=document.getElementById("contextPath").value;
		var address = contextPath+"/ajaxAction.do?method=generateSDAccrual";
		var data = "lbxLoanNoHID="+loanId+"&sdNo="+sdNo;
		sendSDAccrualid(address, data); 
		return true;
	}
}
function generateSDAccrualEditable()
{ 
	var sdNo=document.getElementById("sdNo").value;
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var msg1="";
	var msg2="";
	if(loanId == "")
		msg1="* Loan Number is required.\n";
	if(sdNo == "")
		msg2="* SD Number is required.";
	if(loanId == "" ||sdNo == "")
	{
		alert(msg1+""+msg2);
		document.getElementById("liquidationFlag").value='P';
		if(loanId == "")
			document.getElementById("loanLov").focus();
		if(loanId == "")
			document.getElementById("sdLov").focus();
		return false;
	}	
	var liquidationFlag = document.getElementById("liquidationFlag").value;
	if(liquidationFlag=='P')
	{
		var sdTotalInterest= document.getElementById("sdTotalInterest").value;
		document.getElementById("sdInterestAccrued").value=sdTotalInterest;	
		document.getElementById("partialI").style.display='block';
		document.getElementById("partialT").style.display='block';
		document.getElementById("finalI").style.display='none';
		document.getElementById("finalT").style.display='none';
		document.getElementById("liquidationAmountPrincipal").value='';
		document.getElementById("liquidationAmountInterest").value='';
		return true;
	}
	if(liquidationFlag=='F')
	{
		var sdFinalInterest= document.getElementById("sdFinalInterest").value;
		document.getElementById("sdInterestAccrued").value=sdFinalInterest;
		document.getElementById("partialI").style.display='none';
		document.getElementById("partialT").style.display='none';
		var contextPath=document.getElementById("contextPath").value;
		var address = contextPath+"/ajaxAction.do?method=generateSDAccrual";
		var data = "lbxLoanNoHID="+loanId;
		sendSDAccrualid(address, data); 
		return true;
	}
}
function sendSDAccrualid(address, data) {
		var request = getRequestObject();
	request.onreadystatechange = function () {
		resultSDAccrual(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultSDAccrual(request){
	
		if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;		
		var s1 = str.split("$:");
		document.getElementById("finalI").innerHTML=trim(s1[0]);
		document.getElementById("finalT").innerHTML=trim(s1[1]);
		document.getElementById("finalI").style.display='block';
		document.getElementById("finalT").style.display='block';
		
		var liquidatedAmountPrincipal = removeComma(document.getElementById("liquidatedAmountPrincipal").value);
		var sdAmount = removeComma(document.getElementById("sdAmount").value);	
		var sAmount=parseFloat(sdAmount).toFixed(2);
		var liquidationAmountPrincipal = sAmount-liquidatedAmountPrincipal;
		document.getElementById("liquidationAmountPrincipal").value=parseFloat(liquidationAmountPrincipal ).toFixed(2);

		var sdInterestAccrued = removeComma(document.getElementById("sdInterestAccrued").value);
		var sInter=parseFloat(sdInterestAccrued).toFixed(2);
		var gapInterest = parseFloat(removeComma(document.getElementById("gapInterestF").value));
		var totInt=parseFloat(sInter)+parseFloat(gapInterest);
		var totIntrest=totInt.toFixed(2);
		var liquidatedAmountInterest = removeComma(document.getElementById("liquidatedAmountInterest").value);
		var liquidationAmountInterest =totIntrest-liquidatedAmountInterest;
		document.getElementById("liquidationAmountInterest").value =parseFloat(liquidationAmountInterest ).toFixed(2);
	}
	
}

function saveLiquidationData()
{
	DisButClass.prototype.DisButMethod();
	if(validateSDLiquidationMakerDynaValidatorForm(document.getElementById("sdLiquidationMakerForm")))
	{
		var liquidationAmountPrincipal = removeComma(document.getElementById("liquidationAmountPrincipal").value);
		//alert("liquidationAmountPrincipal: "+liquidationAmountPrincipal);
		var liquidatedAmountPrincipal = removeComma(document.getElementById("liquidatedAmountPrincipal").value);
		//alert("liquidatedAmountPrincipal: "+liquidatedAmountPrincipal);
		var liquidationAmountInterest = removeComma(document.getElementById("liquidationAmountInterest").value);
		//alert("liquidationAmountInterest: "+liquidationAmountInterest);
		var liquidatedAmountInterest = removeComma(document.getElementById("liquidatedAmountInterest").value);
		//alert("liquidatedAmountInterest: "+liquidatedAmountInterest);
		var sdAmount = removeComma(document.getElementById("sdAmount").value);
		//alert("sdAmount: "+sdAmount);
		var sdInterestAccrued = removeComma(document.getElementById("sdInterestAccrued").value);
		//alert("sdInterestAccrued: "+sdInterestAccrued);
		
		//alert("liquidationFlag: "+document.getElementById("liquidationFlag").value);
		var sumAmountPrinc=parseFloat(liquidationAmountPrincipal+liquidatedAmountPrincipal).toFixed(2);
		var sAmount=parseFloat(sdAmount).toFixed(2);
		
		//alert("sumAmountPrinc: "+sumAmountPrinc+" sAmount: "+sAmount+" status: "+parseFloat(sumAmountPrinc)>parseFloat(sAmount));
		
		var sumAmountInt=parseFloat(liquidationAmountInterest+liquidatedAmountInterest).toFixed(2);
		var sInter=parseFloat(sdInterestAccrued).toFixed(2);
		
		//alert("sumAmountInt: "+sumAmountInt+" sInter: "+sInter+" status: "+parseFloat(sumAmountInt)>parseFloat(sInter));
		
		if(document.getElementById("liquidationFlag").value=='P')
		{
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
			//	alert("liquidationAmountPrincipal: "+liquidationAmountPrincipal+" liquidatedAmountPrincipal: "+liquidatedAmountPrincipal+" sdAmount: "+sdAmount.toFixed(2));
				//alert("liquidationAmountPrincipal: "+(parseFloat(liquidationAmountPrincipal)+parseFloat(liquidatedAmountPrincipal))>parseFloat(sdAmount));
				
				if(parseFloat(sumAmountPrinc)>parseFloat(sAmount))
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal should be less than or equal to SD Amount.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else if(parseFloat(sumAmountInt)>parseFloat(sInter))
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest should be less than or equal to Sum of Total Interest Accrued.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=saveLiquidationData";
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
					
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		
		if(document.getElementById("liquidationFlag").value=='F')
		{
			var gapInterest = parseFloat(removeComma(document.getElementById("gapInterestF").value));
			var gapTDS = removeComma(document.getElementById("gapTDSF").value);
			//alert("gapInterest: "+gapInterest);
			//alert("a1 "+sInter+gapInterest);
			//alert(parseFloat(sInter)+parseFloat(gapInterest));
			var totInt=parseFloat(sInter)+parseFloat(gapInterest);
			var totIntrest=totInt.toFixed(2);
			//alert(totIntrest);
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
		
				if(parseFloat(sumAmountPrinc)!=parseFloat(sAmount))
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal must be equal to SD Amount for Final Liquidation.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				if(parseFloat(sumAmountInt)!=parseFloat(totIntrest))
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest must be equal to Sum of Total Interest Accrued & GAP Interest.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;
				}
				else
				{
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=saveLiquidationData";
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
					
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	}
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}
function updateDisbursalBeforeSave()
{
	alert("Please save the data First");
	return false;
}
function deleteLiquidationData()
{
	DisButClass.prototype.DisButMethod();
	if(confirm("Are you sure to delete the record.")) 
	  {
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=deleteLiquidationData";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("sdLiquidationMakerForm").submit();
		    return true;
	  }
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("delete").removeAttribute("disabled","true");
		return false;
	}
}

function updateLiquidationData(type)
{
	DisButClass.prototype.DisButMethod();
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y'&& type=='F')
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
			DisButClass.prototype.EnbButMethod();
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
					DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
		
		if (liquidationAmountPrincipal != null)
			liquidationAmountPrincipal = parseFloat(liquidationAmountPrincipal.toFixed(2));
		
		if (liquidatedAmountPrincipal != null)
			liquidatedAmountPrincipal = parseFloat(liquidatedAmountPrincipal.toFixed(2));
		
		if (liquidationAmountInterest != null)
			liquidationAmountInterest = parseFloat(liquidationAmountInterest.toFixed(2));
		
		if (liquidatedAmountInterest != null)
			liquidatedAmountInterest = parseFloat(liquidatedAmountInterest.toFixed(2));
		
		if (sdAmount != null)
			sdAmount = parseFloat(sdAmount.toFixed(2));
		
		if (sdInterestAccrued != null)
			sdInterestAccrued = parseFloat(sdInterestAccrued.toFixed(2));
		
		var sumAmountPrinc=parseFloat(liquidationAmountPrincipal+liquidatedAmountPrincipal).toFixed(2);
		var sAmount=parseFloat(sdAmount).toFixed(2);
		
		//alert("sumAmountPrinc: "+sumAmountPrinc+" sAmount: "+sAmount+" status: "+parseFloat(sumAmountPrinc)>parseFloat(sAmount));
		
		var sumAmountInt=parseFloat(liquidationAmountInterest+liquidatedAmountInterest).toFixed(2);
		var sInter=parseFloat(sdInterestAccrued).toFixed(2);
		
		//alert("liquidationAmountInterest: "+liquidationAmountInterest+" liquidatedAmountInterest: "+liquidatedAmountInterest);
		
		if(document.getElementById("liquidationFlag").value=='P')
		{
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
				if(parseFloat(sumAmountPrinc)>parseFloat(sAmount))
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal is greater than SD Amount.");
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
				else if(parseFloat(sumAmountInt)>parseFloat(sInter))
				{
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest is greater than Total Interest Accrued.");
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
					//alert("SAVE");
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=updateLiquidationData&type="+type;
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required.");
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
		
		else if(document.getElementById("liquidationFlag").value=='F')
		{
			var gapInterest = parseFloat(removeComma(document.getElementById("gapInterestF").value));
			
			//alert("gapInterest: "+gapInterest);
			//alert("a1 "+sInter+gapInterest);
			//alert(parseFloat(sInter)+parseFloat(gapInterest));
			var totInt=parseFloat(sInter)+parseFloat(gapInterest);
			var totIntrest=totInt.toFixed(2);
			
			if (gapInterest != null)
				gapInterest = parseFloat(gapInterest);
			
			if(document.getElementById("liquidationAmountPrincipal").value!='' || document.getElementById("liquidationAmountInterest").value!='')
			{
				if(parseFloat(sumAmountPrinc)>parseFloat(sAmount))
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal is greater than SD Amount.");
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
				else if(parseFloat(sumAmountPrinc)!=parseFloat(sAmount))
				{
					alert("Sum of Liquidation Amount Principal & Liquidated Amount Principal must be equal to SD Amount for Final Liquidation.");
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
			
				if(parseFloat(sumAmountInt)!=parseFloat(totIntrest))
				{
								
					alert("Sum of Liquidation Amount Interest & Liquidated Amount Interest must be equal Sum of Total Interest Accrued & GAP Interest.");
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
					//alert("UPDATE");
					var contextPath=document.getElementById("contextPath").value;
				    document.getElementById("sdLiquidationMakerForm").action = contextPath+"/sdLiquidationMaker.do?method=updateLiquidationData&type="+type;
				    document.getElementById("processingImage").style.display = '';
				    document.getElementById("sdLiquidationMakerForm").submit();
				}
			}
			else
			{
				alert("Either of Liquidation Amount Principal or Liquidation Amount Interest is Required.");
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

function searchLiquidation(type)
{
	//alert(type);
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
	    document.getElementById("liquidationSearchForm").action = contextPath+"/sdLiquidationSearch.do?method=searchLiquidation&type="+type;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("liquidationSearchForm").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
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

function newLiquidation()
{
	
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("liquidationSearchForm").action = contextPath+"/sdLiquidationSearch.do?method=openNewLiquidation";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("liquidationSearchForm").submit();
}
function saveLiquidationAuthor()
{
	DisButClass.prototype.DisButMethod();
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y'&& document.getElementById("decision").value == 'A')
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
			DisButClass.prototype.EnbButMethod();
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
					DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
			    			return false;
						}
					}
				}
			}
		}
	}
	
	if((document.getElementById("comments").value=='') && !(document.getElementById("decision").value=='A'))
	{
		alert("Please enter Remarks.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("sdLiquidationAuthorForm").action = contextPath+"/sdLiquidationAuthor.do?method=saveLiquidationAuthor";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("sdLiquidationAuthorForm").submit();
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


