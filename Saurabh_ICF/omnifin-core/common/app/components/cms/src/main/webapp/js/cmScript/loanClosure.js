var global=0;
var effDate='Y';
function searchLoanClosure(type,status)
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("loanAc").value=="" && document.getElementById("customerName").value=="" && document.getElementById("reportingToUserId").value=="")
	{
		alert("Please Enter Loan Account No. or Customer Name");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		document.getElementById("loanAc").focus;
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		//alert("Type of Closure: "+type);
	    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=searchLoanData&type="+type+"&status="+status;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("closureSearchForm").submit();
	}
		
}

function openNewClosure(type)
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=openNewClosure&type="+type;
    document.getElementById("processingImage").style.display = '';
    document.getElementById("closureSearchForm").submit();
}

function parent_disable() {
	if( window.child && ! window.child.closed)
		 window.child.focus();
	}

function getDetails()
{
	
	//alert("get Details");
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	var closureType= document.getElementById("closureType").value;
	var initiationDate = document.getElementById("initDate").value;
	//alert(initiationDate);
	var effectiveDate = document.getElementById("effectiveDate").value;
	//alert(effectiveDate);
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
	
/*	else if (dt2<dt1)
	{
		  alert("Effective Date can not be less than Initiation Date");
		  document.getElementById("effectiveDate").focus();
		  return false;
	}*/
	else
	{
		global=1;
		effDate='Y';
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=retriveDuesRefundsValues&loanId="+loanId+"&effectiveDate="+effectiveDate+"&closureType="+closureType;
		 
		 var data = "lbxLoanNoHID=" + loanId;
		 sendDuesRefundid(address, data); 
		 return true;
	}
}
function sendDuesRefundid(address, data) {
	//alert("in sendClosureid ");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDuesRefund(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultDuesRefund(request){
	//alert("In result Dues Refund");

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
		document.getElementById('foreClosurePenaltyOld').value=trim(s1[8]);
		document.getElementById('gapSDTDS').value=trim(s1[9]);
		document.getElementById('otherDues').value=trim(s1[10]);
		document.getElementById('otherRefunds').value=trim(s1[11]);
		document.getElementById('interstTillLP').value=trim(s1[13]);
		document.getElementById('lppAmount').value=trim(s1[14]);
		document.getElementById('overduePrincipal').value=trim(s1[15]);
		document.getElementById('totalRecevable').value=trim(s1[18]);
		document.getElementById('totalRecevableOld').value=trim(s1[18]);
		document.getElementById('advanceEmiRefunds').value=trim(s1[19]);
		document.getElementById('totalPayable').value=trim(s1[20]);
		if(trim(s1[16])=="T")
			alert("Termination Lockin period still valid for this loan");
		if(trim(s1[17])!="")
			alert(trim(s1[17]));
		
		var totalPayable=parseFloat(removeComma(trim(s1[20])));
		var totalRecevable=parseFloat(removeComma(trim(s1[18])));
		var advanceEmiRefunds=parseFloat(removeComma(trim(s1[19])));
		
		
		var waiveOffAmount=parseFloat(removeComma(document.getElementById('waiveOffAmount').value));	
		var netReceivablePayable=parseFloat(totalRecevable-totalPayable-waiveOffAmount);		
		if(netReceivablePayable ==0)
			netReceivablePayable="0.00";		
		var str1=netReceivablePayable+"";
		var val=str1.indexOf(".");		
		if(val== -1)
			str1=str1+".000";		
		var nRPString=str1+"000";		
		str=nRPString.split(".");
		var num=str[0];
		var dec=str[1];		
		var last=parseInt(dec.substring(2,3));
		if(last >= 5)
			netReceivablePayable=netReceivablePayable+0.01;		
		str1=netReceivablePayable+"";
		val=str1.indexOf(".");		
		if(val== -1)
			str1=str1+".00";		
		nRPString=str1+"000";		
		str=nRPString.split(".");
		num=str[0];
		dec=str[1];
		var resultantRP=num+"."+dec.substring(0,2);		
		formatNumber(resultantRP,'netReceivablePayable');
     window.close();
	}
	
}

function saveClosureDetails()
{
	DisButClass.prototype.DisButMethod();
	if(global != 1 && effDate == 'N')
	{
		alert("First get Loan datails.");
		DisButClass.prototype.EnbButMethod();
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
			 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		if(totalRecevable = '')
		{
			 alert("Total Recevable is required");
			 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		if(otherDues = '')
		{
			 alert("Other Dues is required");
			 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
		
		if(parseFloat(waiveOffAmount)>parseFloat(totalRecevable))
	    {
	    	 alert("Waive off amount can't be greater than Total Recevable.");
	    	 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
	    }
		
	    if((parseFloat(totalRecevable)-parseFloat(totalPayable)) <0 && parseFloat(waiveOffAmount) >0)
	    {
	    	agree=confirm("Waive off amount is greater than Net receivable/ Payable amount.Do you want to continue?");
	    	if (!(agree))
	    	{
	    		DisButClass.prototype.EnbButMethod();
	    		document.getElementById("save").removeAttribute("disabled","true");
	    		return false;
	    	}
	    }
	    //commented by neeraj tripathi
	    /*if(parseFloat(otherDues)>0 && parseFloat(waiveOffAmount)>0)
		{
			alert("Please Waive Off Other Dues First");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("waiveOffAmount").value="0";
			return false;
		}*/
	    /*
	    if(parseFloat(year1)<parseFloat(year2))
		{	
			alert("Effective Date can't be less than Initiation Date");
			DisButClass.prototype.EnbButMethod();
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
					DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("effectiveDate").focus();
							return false;
						}
					}
				}
			}	
		}  */
	    global=0;
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("closureForm").action = contextPath+"/earlyClosure.do?method=saveClosureDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("closureForm").submit();
		return true;
		
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

function deleteClosureDetails()
{
	DisButClass.prototype.DisButMethod();
	if(confirm("Are you sure to delete the record.")) 
	  {
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("closureForm").action = contextPath+"/earlyClosure.do?method=deleteClosureDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("closureForm").submit();
		return true;
	  }
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("delete").removeAttribute("disabled","true");
		return false;
	}
}

function resetReceivable()
{
	var foreClosurePenaltyOld = document.getElementById("foreClosurePenaltyOld").value;
	var foreClosurePenalty = document.getElementById("foreClosurePenalty").value;
	var totalRecevable = document.getElementById("totalRecevableOld").value;
	var fCPenaltyOld=0.00;
	var fCPenalty=0.00;
	var totR=0.00;
	
	if(foreClosurePenaltyOld!='')
	{
		fCPenaltyOld=removeComma(foreClosurePenaltyOld);
	}
	if(foreClosurePenalty!='')
	{
		fCPenalty=removeComma(foreClosurePenalty);
	}
	if(totalRecevable!='')
	{
		totR=removeComma(totalRecevable);
	}
	
	var result=parseFloat(totR)-parseFloat(fCPenaltyOld)+parseFloat(fCPenalty);
	result=result.toFixed(2);
	result=result+"";
	formatNumber(result,'totalRecevable');
	var waiveOffAmount=document.getElementById('waiveOffAmount').value;
	calculatNetPayRes(waiveOffAmount);
}
function updateClosureDetails(type)
{
	DisButClass.prototype.DisButMethod();
	if(global != 1 && effDate == 'N')
	{
		alert("First get Loan datails.");
		if(type=='P')
		{
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		if(type=='F')
		{
			DisButClass.prototype.EnbButMethod();
			document.getElementById("saveForward").removeAttribute("disabled","true");
		}
		return false;
	}
	//code added by neeraj tripathi
	if(type=='F')
	{
		var earlyClosureFlag = document.getElementById("earlyClosureFlag").value;
		var waiveAllocated = document.getElementById("waiveAllocated").value;
		if(earlyClosureFlag=='Y')
		if(waiveAllocated=='N')
		{
			alert("First allocate Waive-Off amount for all charges.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	//neeraj space end
	var initiationDate = document.getElementById("initDate").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
    var matDate = document.getElementById("maturtyDate").value;
    var closureType = document.getElementById("closureType").value;
    var otherDues = removeComma(document.getElementById("otherDues").value);
    var waiveOffAmount = removeComma(document.getElementById("waiveOffAmount").value);
    var netReceivablePayable = removeComma(document.getElementById("netReceivablePayable").value);
    var totalRecevable = removeComma(document.getElementById("totalRecevable").value);
    var totalPayable = removeComma(document.getElementById("totalPayable").value);
    var netReceiveablePayableFlag = document.getElementById("netReceiveablePayableF").value;
    var fromdate=effectiveDate;
	var todate=initiationDate;
	var day1= fromdate.substring(0,2);
	var day2= todate.substring(0,2);
	var month1=fromdate.substring(3,5);
	var month2=todate.substring(3,5);
	var year1=fromdate.substring(6);
	var year2=todate.substring(6);
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	var realize=document.getElementById("realize").value;
	var realizeFlag=document.getElementById("realizeFlag").value;

    if(validateClosureDynaValidatorForm(document.getElementById("closureForm")))
	{
    	/*if(type=='F'&& earlyClosureFlag=='Y')
    	{

    		if(parseFloat(year1) != parseFloat(year2))
			{	
    			alert("Effective Date should be equal to Initiation Date");
    			DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								document.getElementById("saveForward").removeAttribute("disabled","true");
								document.getElementById("effectiveDate").focus();
								return false;
							}
						}
					}
				}	
			}
    	}*/
    	if(type=='F')
    	{

    		if(realize>0)
    		{
    			alert("Some Receipt/Payment are in Pending or Fowarding State.");
    			DisButClass.prototype.EnbButMethod();
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    			return false;
    			
    		}    		if(realize>0 && realizeFlag=='Y')
    		{
    			alert("Some Receipt in Process for Realize.");
    			DisButClass.prototype.EnbButMethod();
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    			return false;
    			
    		}
    	}
    	if(type=='F')
    	{
    		if(netReceiveablePayableFlag=='Y')
    		{
    			if(parseFloat(netReceivablePayable)!=0 )
	    		{
	    			alert("Net receivable/Payable amount should be equal to zero.");
	    			DisButClass.prototype.EnbButMethod();
	    			document.getElementById("saveForward").removeAttribute("disabled","true");
	    			return false;
	    		}
    		}
    		else
    		{
	    		if(parseFloat(netReceivablePayable)>0)
	    		{
	    			alert("Net receivable/Payable amount can't be greater than zero.");
	    			DisButClass.prototype.EnbButMethod();
	    			document.getElementById("saveForward").removeAttribute("disabled","true");
	    			return false;
	    		}
    		}
    	}
    		
    	if(parseFloat(waiveOffAmount)>parseFloat(totalRecevable))
        {
        	 alert("Waive off amount can't be greater than Total Recevable.");
        	 if(type=='P')
        	 {
        		 DisButClass.prototype.EnbButMethod();
 				document.getElementById("save").removeAttribute("disabled","true");
        	 }
 			 if(type=='F')
 			 {
 				DisButClass.prototype.EnbButMethod();
 				document.getElementById("saveForward").removeAttribute("disabled","true");
 			 }
 			return false;
        }
    	if((parseFloat(totalRecevable)-parseFloat(totalPayable)) <0 && parseFloat(waiveOffAmount) >0)
        {
        	agree=confirm("Waive off amount is greater than Net receivable/ Payable amount.Do you want to continue.");
        	if (!(agree))
        	{
        		if(type=='P')
        		{
        			DisButClass.prototype.EnbButMethod();
        			document.getElementById("save").removeAttribute("disabled","true");
        		}
        		if(type=='F')
        		{
        			DisButClass.prototype.EnbButMethod();
        			document.getElementById("saveForward").removeAttribute("disabled","true");
        		}
        		return false;
	    	}
        }
    	//commented by neeraj tripathi
    	/*if(parseFloat(otherDues)>0 && parseFloat(waiveOffAmount)>0)
		{
			alert("Please Waive Off Other Dues First");
			if(type=='P')
			{
				DisButClass.prototype.EnbButMethod();
    			document.getElementById("save").removeAttribute("disabled","true");
			}
    		if(type=='F')
    		{
    			DisButClass.prototype.EnbButMethod();
    			document.getElementById("saveForward").removeAttribute("disabled","true");
    		}
    		return false;
		}*/
    /*	if(type=='P')
    	{
    		if(parseFloat(year1)<parseFloat(year2))
			{	
    			alert("Effective Date can't be less than Initiation Date");
    			DisButClass.prototype.EnbButMethod();
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
						DisButClass.prototype.EnbButMethod();
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
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("effectiveDate").focus();
								return false;
							}
						}
					}
				}	
			}
    	}*/
    	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("closureForm").action = contextPath+"/earlyClosure.do?method=updateClosureDetails&type="+type;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("closureForm").submit();
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
			document.getElementById("saveForward").removeAttribute("disabled","true");
		}
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
//method added by neeraj tripathi
function allocateWaiveOff() 
{	
	//alert("In allocateWaiveOff");
	var saveFlag=document.getElementById('saveCompleted').value;
	if(saveFlag=='N')
	{
		alert("Please save the data First.");
		return false;
	}
	var loanId=document.getElementById('lbxLoanNoHID').value;	
	if (loanId=="")
	{
		alert("Loan Account No is required.");	
		return false;
	}
	var overdueInstallments=document.getElementById('overdueInstallments').value;
	if (overdueInstallments=="")
	{
		alert("First get Loan datails.");	
		return false;
	}
	overdueInstallments=removeComma(document.getElementById('overdueInstallments').value);
	var netReceivablePayable=parseFloat(removeComma(document.getElementById('netReceivablePayable').value));
	//alert("neeraj netReceivablePayable  :  "+netReceivablePayable);
	/*if(netReceivablePayable != 0)
	{
		alert("Net Receivable/Payable should be zero(0.00).")
		return false;
	}*/
	var terminationId=document.getElementById('terminationId').value;
	//alert("terminationId  :  "+terminationId);
	var waiveOffAmount=removeComma(document.getElementById('waiveOffAmount').value);	
	
	var change = document.getElementById('changeWaiveOff').value;
	//alert("change : "+change);
	curWin = 0;
	otherWindows = new Array();
	var contextPath = document.getElementById("contextPath").value;
	var url= contextPath+"/allocateWaiveOff.do?method=allocateWaiveOff&loanId="+loanId+"&waiveOffAmount="+waiveOffAmount+"&change="+change+"&terminationId="+terminationId; 		
	myWindow=window.open(url,'viewPayable',"height=400,width=1350,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
	otherWindows[curWin++] =window.open(url,'viewPayable',"height=400,width=1350,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
	if(window.focus) {
		mywindow.focus();
		return false;
	}
	return true;
}
//method added by neeraj tripathi
function calculateBalance(val)
{
	//alert("calculateBalance");
	var wivAmt="waiveOffAmount"+val;
	var values=document.getElementById(wivAmt).value;
	if(values=="")
	  document.getElementById(wivAmt).value="0.00";
	var amountDue=document.getElementsByName('amountDue');
	var waiveOffAmount=document.getElementsByName('waiveOffAmount');
	var balsAmt=document.getElementsByName('balsAmt');
	var due=parseFloat(removeComma(amountDue[val-1].value));
	var waive=parseFloat(removeComma(waiveOffAmount[val-1].value));
	var bls=parseFloat(removeComma(balsAmt[val-1].value));
	var id="balsAmt"+val;
	var balance=due-waive;
	balance=balance.toFixed(2);
	balance=balance+"";
	formatNumber(balance,id);
	
	var totalWaiveOff=0;
	var totalBAlance=0;
    // calculating total	
	for(var i=0;i<waiveOffAmount.length;i++)
	{
		totalWaiveOff=totalWaiveOff+parseFloat(removeComma(waiveOffAmount[i].value));
		totalBAlance=totalBAlance+parseFloat(removeComma(balsAmt[i].value));
	}
	totalWaiveOff=totalWaiveOff.toFixed(2);
	totalWaiveOff=totalWaiveOff+"";
	totalBAlance=totalBAlance.toFixed(2);
	totalBAlance=totalBAlance+"";
	formatNumber(totalWaiveOff,'totalWaiveOffAmount');
	formatNumber(totalBAlance,'totalBalsAmt');
}
//method added by neeraj tripathi
function saveAllocateWaiveOff()
{
	//alert("saveAllocateWaiveOff");	
	DisButClass.prototype.DisButMethod();
	var loanId=document.getElementById("loanId").value;
	var terminationId=document.getElementById("terminationId").value;
	//alert("terminationId  :  "+terminationId);	
	var orgWaiveAmt=document.getElementById("orgWaiveAmt").value;
	var chargeName=document.getElementsByName('chargeName');
	var waiveOffAmount=document.getElementsByName('waiveOffAmount');
	var balsAmt=document.getElementsByName('balsAmt');
	//alert("lbxapprovedBy  "+document.getElementById("lbxapprovedBy").value);
	//alert("approvedBy  "+document.getElementById("approvedBy").value);
	var approvedBy=document.getElementById("approvedBy").value;
	var lbxapprovedBy=document.getElementById("lbxapprovedBy").value;
	
	for(var i=0;i<balsAmt.length;i++)
	{
		var val=parseFloat(removeComma(balsAmt[i].value));
		if(val<0)
		{
			alert("Balance amount of any charges can't be negative.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var orgWaiveAmt=parseFloat(removeComma(document.getElementById("orgWaiveAmt").value));
	var totalWaiveOffAmount=parseFloat(removeComma(document.getElementById("totalWaiveOffAmount").value));
	if(orgWaiveAmt!=totalWaiveOffAmount)
	{
		alert("Sum of waive off amount of all charges should be equal to "+orgWaiveAmt);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var chargeList="";
	var waiveAmtList="";
	var balAmtList="";	
	for(var i=0;i<balsAmt.length;i++)
	{
		chargeList=chargeList+","+chargeName[i].value;
		waiveAmtList=waiveAmtList+","+removeComma(waiveOffAmount[i].value);
		balAmtList=balAmtList+","+removeComma(balsAmt[i].value);
	}
	chargeList=chargeList.substring(1);
	waiveAmtList=waiveAmtList.substring(1);
	balAmtList=balAmtList.substring(1);
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("allocateWaiveOffForm").action=contextPath+"/allocateWaiveOff.do?method=saveWaiveOffData&chargeList="+chargeList+"&waiveAmtList="+waiveAmtList+"&balAmtList="+balAmtList+"&loanId="+loanId+"&orgWaiveAmt="+orgWaiveAmt+"&terminationId="+terminationId+"&approvedBy="+approvedBy+"&lbxapprovedBy="+lbxapprovedBy;
    document.getElementById("processingImage").style.display='';
    document.getElementById("allocateWaiveOffForm").submit();
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
		//alert("loanId",loanId);
		var basePath=document.getElementById("contextPath").value;
		otherWindows[curWin++] =window.open(basePath+'/viewReceivableEarlyClousreAction.do?&loanId='+loanId,'viewReceivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		return true;
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

function searchLoanClosureAuthor(type,status)
{
	DisButClass.prototype.DisButMethod();
	var allBranches=document.getElementById("allBranches").checked;
	var selectedBranch=document.getElementById("lbxBranchId").value;
	if(allBranches==true && selectedBranch!='')
	{
		alert("Select either All Branch or Selective Branch.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
	if(allBranches==false && selectedBranch=='' && document.getElementById("loanAc").value=="" && document.getElementById("customerName").value==""&& document.getElementById("reportingToUserId").value=="")
	{
		
		alert("Please Enter Loan Account No. or Customer Name");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		document.getElementById("loanAc").focus;
		return false;

	}else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=searchLoanDataAuthor&type="+type+"&status="+status;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("closureSearchForm").submit();
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

function saveClosureAuthor()
{
	//alert("saveClosureAuthor..abc");
	DisButClass.prototype.DisButMethod();
	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y'&& document.getElementById("decision").value == 'A')
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
		alert("Comments is Required");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("closureAuthorForm").action = contextPath+"/author.do?method=saveClosureAuthor";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("closureAuthorForm").submit();
	}
}

function checkMaturityDateForMatClo()
{
	//alert("check");
	effDate='N';
	global=0;
	var formatD=document.getElementById("formatD").value;
	var effectiveDate = document.getElementById("effectiveDate").value;
	//alert("effectiveDate: "+effectiveDate);
	var dt2=getDateObject(effectiveDate,formatD.substring(2, 3));
	//alert("dt2: "+dt2);
    var matDate = document.getElementById("maturtyDate").value;
    //alert("matDate: "+matDate);
    var maturityDate = getDateObject(matDate, formatD.substring(2, 3));
    //alert("maturityDate: "+maturityDate);
    var closureType = document.getElementById("closureType").value;
    //alert("closureType: "+closureType);
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

function saveCancellationDetails()
{
	//alert("save");
	DisButClass.prototype.DisButMethod();
	var businessDate = document.getElementById("businessDate").value;
	if(validateCancellationMakerDynaValidatorForm(document.getElementById("cancellationForm")))
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("cancellationForm").action = contextPath+"/cancellation.do?method=saveCancellationDetails";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("cancellationForm").submit();
	    return true;
	}
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function deleteCancellationDetails()
{
	DisButClass.prototype.DisButMethod();
	if(confirm("Are you sure to delete the record.")) 
	  {
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("cancellationForm").action = contextPath+"/cancellation.do?method=deleteCancellationDetails";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("cancellationForm").submit();
	    return true;
	  }
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("delete").removeAttribute("disabled","true");
		return false;
	}
}

function updateCancellationDetails(type,fwdMsg)
{
	// alert("update");
	DisButClass.prototype.DisButMethod();
	if(type=='F')
	{
		if(!confirm(fwdMsg))	 
	    {
	       	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
	}

	var earlyClosureFlag=document.getElementById("checkFlag").value;
	if(earlyClosureFlag=='Y'&& type=='F')
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
					DisButClass.prototype.EnbButMethod();
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
							DisButClass.prototype.EnbButMethod();
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
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("cancellationForm").submit();
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
			document.getElementById("saveForward").removeAttribute("disabled","true");
		}
		return false;
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