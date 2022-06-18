// method for trade check capturing search

function newTradeCheckCapSearch(stage)
{
	DisButClass.prototype.DisButMethod();
	
	var dealNo=document.getElementById("dealNo").value;
	var applicationdate=document.getElementById("applicationdate").value;
	var customername=document.getElementById("customername").value;
	var lbxProductID=document.getElementById("lbxProductID").value;
	var lbxscheme=document.getElementById("lbxscheme").value;

	var sourcepath=document.getElementById("contextPath").value;
	var userName=document.getElementById("reportingToUserId").value;

	//var sourcepath=document.getElementById("contextPath").value;
	//var userName=document.getElementById("name").value;

	if(userName!='' || dealNo!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!='')
	{
		if(customername!='' && customername.length<3)
		{
			alert("Please Enter at least 3 characters in Customer Name");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			document.getElementById("tradeCheckCapForm").action=sourcepath+"/tradeCheckCapSearch.do?method=tradeCheckCapSearch&stage="+stage+"&userId="+userName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("tradeCheckCapForm").submit();
			return true;
		}
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

// Method for trade check completion search

function newTradeCheckComSearch(stage)
{
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var applicationdate=document.getElementById("applicationdate").value;
	var customername=document.getElementById("customername").value;
	var lbxProductID=document.getElementById("lbxProductID").value;
	var lbxscheme=document.getElementById("lbxscheme").value;
	var sourcepath=document.getElementById("contextPath").value;
	if(dealNo!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!='')
	{
		if(customername!='' && customername.length<3)
		{
			alert("Please Enter at least 3 characters in Customer Name");
			document.getElementById("searchComButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			document.getElementById("tradeCheckComForm").action=sourcepath+"/tradeCheckComSearch.do?method=tradeCheckComSearch&stage="+stage;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("tradeCheckComForm").submit();
			return true;
		}
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("searchComButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function popUpJsp(val1,val2,val3,val4,val5)
{
	var contextPath =document.getElementById('contextPath').value ;
	
	otherWindows = new Array();
	curWin = 0;
	
	if(val2=='Buyer')
	{
		//alert("in buyer----");
		var url=contextPath+"/JSP/CPJSP/tradeCheckBuyer.jsp?tradeCheck="+val2+"&dealNo="+val1+"&verId="+val3+"&entType="+val4+"&entID="+val5+"";
		//alert(url);
		otherWindows[curWin++] = window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
	}

	else if(val2=='Supplier')
	{
		//ert("in supplier----");
		var url=contextPath+"/JSP/CPJSP/tradeCheckSupplier.jsp?tradeCheck="+val2+"&dealNo="+val1+"&verId="+val3+"&entType="+val4+"&entID="+val5+"";
		//alert("url---"+url);
		otherWindows[curWin++] = window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
	}
	else if(val2=='Market')
	{
		var url=contextPath+"/JSP/CPJSP/MarketCheck.jsp?tradeCheck="+val2+"&dealNo="+val1+"&verId="+val3+"&entType="+val4+"&entID="+val5+"";
		otherWindows[curWin++] = window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
	}
}


function saveTradeBuyerDetails(val)
{
	DisButClass.prototype.DisButMethod();
	if(validateTradeCheckCapBuyerSupplierDynaValidatorForm(document.getElementById("tradeBuyerForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("tradeBuyerForm").action=contextPath+"/tradeBuyerDetails.do?method=tradeBuyerDetails&dealId="+val;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeBuyerForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveTradeSuplierDetails(val)
{
	DisButClass.prototype.DisButMethod();
	if(validateTradeCheckCapBuyerSupplierDynaValidatorForm(document.getElementById("tradeSuplierForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("tradeSuplierForm").action=contextPath+"/tradeBuyerDetails.do?method=tradeSuplierDetails&dealId="+val;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeSuplierForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveMarketDetails(val)
{
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	//alert(contextPath);
	if(validateTradeCheckCapMarketDynaValidatorForm(document.getElementById("tradeMarketForm")))
	{
		document.getElementById("tradeMarketForm").action=contextPath+"/tradeMarketDetails.do?method=tradeMarketDetails&dealId="+val;
		document.getElementById("processingImage").style.display = '';
		//alert(document.getElementById("tradeMarketForm").action);
		document.getElementById("tradeMarketForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function updatePopUpJsp(val1,val2,val3,val4)
{
	
	var contextPath =document.getElementById('contextPath').value ;
	//alert(val1);
	//alert(val2);
	//alert(val3);
	if(val2=='Buyer')
	{
		//alert("in buyer--:)'''''';;;;;;;;--");
		var url=contextPath+"/tradeBuyerDetails.do?method=SearchBuyerDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		//alert(url);
		window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
		//alert(window.child);
		return true;
		
	}

	else if(val2=='Supplier')
	{
		//("in supplier----");
		var url=contextPath+"/tradeBuyerDetails.do?method=SearchSuplierDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
		return true;
	}
	else if(val2=='Market')
	{
		var url=contextPath+"/tradeMarketDetails.do?method=SearchMarketDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
		return true;
	}
}


function modifyTradeBuyerDetails()
{
	DisButClass.prototype.DisButMethod();
	if(validateTradeCheckCapBuyerSupplierDynaValidatorForm(document.getElementById("tradeBuyerForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		var tradeCheckId=document.getElementById("tradeCheckId").value;
		//alert("tradeCheckId---"+tradeCheckId);
		document.getElementById("tradeBuyerForm").action=contextPath+"/tradeBuyerDetails.do?method=modifyTradeBuyerDetails&tradeCheckId="+tradeCheckId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeBuyerForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.DisButMethod();
		return false;
	}
}

function modifyTradeSuplierDetails(val)
{
	DisButClass.prototype.DisButMethod();
	if(validateTradeCheckCapBuyerSupplierDynaValidatorForm(document.getElementById("tradeSuplierForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		var tradeCheckId=document.getElementById("tradeCheckId").value;
		//alert("tradeCheckId---"+tradeCheckId);
		document.getElementById("tradeSuplierForm").action=contextPath+"/tradeBuyerDetails.do?method=modifyTradeSuplierDetails&tradeCheckId="+tradeCheckId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeSuplierForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function modifyTradeMarketDetails()
{
	DisButClass.prototype.DisButMethod();
	if(validateTradeCheckCapMarketDynaValidatorForm(document.getElementById("tradeMarketForm")))
	{
		var contextPath =document.getElementById('contextPath').value ;
		var tradeCheckId=document.getElementById("tradeCheckId").value;
		//alert("tradeCheckId---"+tradeCheckId);
		document.getElementById("tradeMarketForm").action=contextPath+"/tradeMarketDetails.do?method=modifyTradeMarketDetails&tradeCheckId="+tradeCheckId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeMarketForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function viewPopUpJsp(val1,val2,val3,val4)
{
	
	var contextPath =document.getElementById('contextPath').value ;
	//alert(val1);
	//alert(val2);
	//alert(val3);
	otherWindows = new Array();
	curWin = 0;
	if(val2=='Buyer')
	{
		//alert("in buyer--:)'''''';;;;;;;;--");
		var url=contextPath+"/tradeBuyerDetails.do?method=ViwBuyerDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		//alert(url);
		otherWindows[curWin++]=window.open(url,'name'+val2,'height=350,width=900,top=200,left=250,scrollbars=yes' );
		//alert(window.child);
		return true;
		
	}

	else if(val2=='Supplier')
	{
		//("in supplier----");
		var url=contextPath+"/tradeBuyerDetails.do?method=ViewSuplierDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		otherWindows[curWin++]=window.open(url,'name'+val2,'height=350,width=900,top=200,left=250,scrollbars=yes' );
		return true;
	}
	else if(val2=='Market')
	{
		var url=contextPath+"/tradeMarketDetails.do?method=ViewMarketDetails&dealNo="+val1+"&verId="+val3+"&entityID="+val4+"";
		otherWindows[curWin++]=window.open(url,'name'+val2,'height=350,width=900,top=200,left=250,scrollbars=yes' );
		return true;
	}
}

function forwardDetails(val,val1)
{
	//alert(val);
	DisButClass.prototype.DisButMethod();
	
	var contextPath =document.getElementById('contextPath').value ;
	//alert(contextPath);
	document.getElementById("tradeBuyerSupplierForm").action=contextPath+"/tradeBuyerDetails.do?method=updateStatus&dealId="+val+"&verificationID="+val1;
	document.getElementById("processingImage").style.display = '';
//	alert(document.getElementById("tradeBuyerSupplierForm").action);
	document.getElementById("tradeBuyerSupplierForm").submit();
	return true;
}

function saveTradeRemarks(val)
{
	DisButClass.prototype.DisButMethod();
	
	if(document.getElementById("textarea").value=="")
	{
		alert("Please Enter Remarks");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("textarea").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("tradeRemarksForm").action=contextPath+"/tradeCheck.do?method=updateTradeRemark&dealId="+val;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("tradeRemarksForm").submit();
		return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}


function getDateObject(dateString,dateSeperator)
{
	//alert("sadfasdfasfasd");
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);	
	return dtObject;
}

function validateLeadDateApp()
{
	//alert("asdfasdfadfasdf");
	var msg='';
	var formatD=document.getElementById("formatD").value;

	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("appraisalDate").value;

    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));

    
    if(dt1>dt3)
	{
		msg="Please enter Appraisal Date equal to bussiness Date";
		document.getElementById("appraisalDate").value='';
		//return false;
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