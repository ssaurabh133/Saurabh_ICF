function numericOnlyAtReceipt(e, Max){

	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 && e.keyCode != 39)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
}
function getDateObject(dateString,dateSeperator)
    {
    	// This function return a date object after accepting
    	// a date string ans dateseparator as arguments
    	var curValue=dateString;
    	var sepChar=dateSeperator;
    	var curPos=0;
    	var cDate,cMonth,cYear;

    	// extract day portion
    	curPos=dateString.indexOf(sepChar);
    	cDate=dateString.substring(0,curPos);
    	
    	// extract month portion
    	endPos=dateString.indexOf(sepChar,curPos+1);			
    	cMonth=dateString.substring(curPos+1,endPos);

    	// extract year portion
    	curPos=endPos;
    	endPos=curPos+5;			
    	cYear=curValue.substring(curPos+1,endPos);
    	
    	// Create Date Object
    	dtObject=new Date(cYear,cMonth,cDate);	
    	return dtObject;
    }

	function fnSearch(alert1)
	{	
		DisButClass.prototype.DisButMethod();
		// if
		// ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("paymentAmount").value=="")&&(document.getElementById("instrumentNumber").value==""))

		if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")
		&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("paymentAmount").value=="")
		&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("paymentMode").value=="")&&(document.getElementById("reportingToUserId").value==""))

	{
		alert(alert1);
		document.getElementById("search").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("paymentMakerSearch").action=basePath+"/paymentMakerSearch.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("paymentMakerSearch").submit();
	 
	    return true;
     	}
	}
	function editDetail(a,b)
	{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("paymentMakerSearch").action=basePath+"/paymentCMProcessAction.do?method=openEditpaymentMaker&loanId="+a+"&instrumentID="+b;
		document.getElementById("paymentMakerSearch").submit();
		
	}
	function insertValue()
	{
		 document.getElementById("payeeName").value=(document.getElementById("payeeName").value).toUpperCase();
         document.getElementById("lbxpayeeName").value=document.getElementById("payeeName").value;
	}
	function otherMethod()
	{
		var lbxpayTo=document.getElementById("lbxpayTo").value;
		if(lbxpayTo=='OTH')
		{
			document.getElementById("payeeName").removeAttribute("readOnly","true");
			document.getElementById("payeeNameButton").setAttribute("disabled","true");
		}
		else
		{
			document.getElementById("payeeName").setAttribute("readOnly","true");
			document.getElementById("payeeNameButton").removeAttribute("disabled","true");
		}
	}
	function newAdd(){ 
		DisButClass.prototype.DisButMethod();		
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("paymentMakerSearch").action=basePath+"/paymentMakerNewAction.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("paymentMakerSearch").submit();
	    return true;
	}	

	 function removeComma(id)
	 {
	     var str = id;
	     var arr = str.split(',');
	     var stri = '';
	     for(i=0; i<arr.length; i++){
	         stri = stri+arr[i];
	     }   
	     var amount = parseFloat(stri);
	 	return amount;
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
	
	function fnReceiptNull(){
	     
		 var receiptMode =document.getElementById('receiptMode').value;
	     document.getElementById('instrumentNumber').value="";
	     if(receiptMode=='C'||receiptMode=='S')
	     {
	    	 document.getElementById('instrumentDate').value="";
	     }
	     else
	     {
	    	 document.getElementById('instrumentDate').value=document.getElementById('businessdate').value ;
	     }
	    
		 // document.getElementById('bankAccount').value="";
		 // document.getElementById('lbxbankAccountID').value="";
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
	
	function cashAccountDisable()
	{
	
		if((document.getElementById("receiptMode").value == "C")|| (document.getElementById("receiptMode").value == "S")|| (document.getElementById("receiptMode").value == "DIR"))
		{   		
			
			document.getElementById("disId").style.display='none';
			document.getElementById("disIdBranch").style.display='none';
			document.getElementById("disIdInsD").style.display='none';
			document.getElementById("disIdIns").style.display='inline';
			
			// document.getElementById("bankAccount").disabled=true;
			// document.getElementById("lbxbankAccountID").disabled=true;
			document.getElementById("instrumentNumber").disabled=true;
			document.getElementById("instrumentDateDis").disabled=true;
			document.getElementById("lbxBranchID").disabled=true;
			document.getElementById("lbxBankID").disabled=true;
		// document.getElementById("loanBankButton").style.display='none';
			// //document.getElementById("loanBranchButton").style.display='none';
		}
		else if((document.getElementById("receiptMode").value == "N")|| (document.getElementById("receiptMode").value == "R"))
		{
			
			document.getElementById("disIdBranch").style.display='none';
			// document.getElementById("bankAccount").disabled=true;
			// document.getElementById("lbxbankAccountID").disabled=true;
			// document.getElementById("bankAccount").disabled=true;
			// document.getElementById("lbxbankAccountID").disabled=true;
			document.getElementById("lbxBranchID").disabled=true;
			document.getElementById("instrumentNumber").disabled=false;
			document.getElementById("lbxBankID").disabled=false;
			document.getElementById("loanBankButton").style.display='';
			document.getElementById("disId").style.display='inline';
			document.getElementById("disIdInsD").style.display='inline';
			document.getElementById("disIdIns").style.display='none';
		}
		else{
			// disId
			// document.getElementById("showId").disabled=false;
			// document.getElementById("bankAccount").disabled=false;
			// document.getElementById("lbxbankAccountID").disabled=false;
			document.getElementById("instrumentNumber").disabled=false;
			document.getElementById("instrumentDate").disabled=false;	
			document.getElementById("lbxBranchID").disabled=false;
			document.getElementById("lbxBankID").disabled=false;
			document.getElementById("disId").style.display='inline';
			document.getElementById("disIdBranch").style.display='inline';
			document.getElementById("disIdInsD").style.display='inline';
			document.getElementById("disIdIns").style.display='none';
			document.getElementById("loanBankButton").style.display='';
			document.getElementById("loanBranchButton").style.display='';
		}
		
		
	}
	function getAccountTypeR(){
		 var accountType=document.getElementById('receiptMode').value;
		
		 if(accountType=='Q'||accountType=='D'||accountType=='N'||accountType=='R'){

			 
			 document.getElementById('lbxreceiptMode').value='B' ;
			
		 }else{
			 document.getElementById('lbxreceiptMode').value=accountType ;	 
		 }
	}
	function allocatePayable(alert1){
		
		
		otherWindows = new Array();
		
		if (document.getElementById("canForward").value!="")
		   {
		 var loanId=document.getElementById('lbxLoanNoHID').value;	
		 var bpType=document.getElementById('lbxBPType').value;
		 var bpId=document.getElementById('lbxBPNID').value;		 
		 var instrumentId=document.getElementById('instrumentID').value;
		 var paymentAmount=removeComma(document.getElementById('paymentAmount').value);
	
		 var  tdsAmount=document.getElementById('tdsAmount').value;
		 if(tdsAmount==""){
			 tdsAmount="0";
			// alert("tdsAmount in if:---"+tdsAmount);
			 }
		 else
		 {
		 tdsAmount=removeComma(document.getElementById('tdsAmount').value);
		
		 }
		 var amount = 0;
		amount = parseFloat(paymentAmount) + parseFloat(tdsAmount);
	
		var basePath=document.getElementById("contextPath").value;
		 otherWindows[curWin++] = window.open(basePath+'/paymentCMProcessAction.do?method=allocatePayble&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId+'&instrumentId='+instrumentId+'&amount='+amount+'&tdsAmount='+tdsAmount+'&paymentAmount='+paymentAmount,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
	 	return true;
		   }
		 else
		 {
		 DisButClass.prototype.DisButMethod(); 
		 alert(alert1);
		 document.getElementById("allocatePay").removeAttribute("disabled");
		 DisButClass.prototype.EnbButMethod();
		 return false;
		 }
	}
	function viewPayable(alert1) 
	{	
		
		var loanId=document.getElementById('lbxLoanNoHID').value;
		var bpType=document.getElementById('lbxBPType').value;
			
		
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
			curWin = 0;
			otherWindows = new Array();
			var loanId=document.getElementById('lbxLoanNoHID').value;
			var bpType=document.getElementById('lbxBPType').value;
			var bpId=document.getElementById('lbxBPNID').value;	
			var basePath=document.getElementById("contextPath").value;
			otherWindows[curWin++] =  window.open(basePath+'/paymentCMProcessAction.do?method=viewPayble&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			return true;
		}
	}
	
	
	
	function onViewPayableSave(alert1,alert3,alert4) {
		DisButClass.prototype.DisButMethod();
		 window.opener.document.getElementById("beforeForward").value="check"; 
		
		var paymentAmount=removeComma(document.getElementById('paymentAmount').value);
		
		// alert("paymentAmount"+paymentAmount);
		// var tdsAmount =
		// removeComma(document.getElementById('tdsAmount').value);
		var  tdsAmount=document.getElementById('tdsAmount').value;
		// alert("tdsAmount"+tdsAmount);
		 if(tdsAmount==""){
			 tdsAmount="0";
			
			 }
		 else
		 {
		 tdsAmount=removeComma(document.getElementById('tdsAmount').value);
		
		 }
		
	    var sum=0;
	    var tdsSum=0;
		var i;
		var gridTable = document.getElementById('gridTable');
		var tableLength = gridTable.rows.length-1;
		
		for(i=1;i<=tableLength;i++){
			
			var allotedAmount=document.getElementById('allotedAmount'+i).value;			 
			 if(allotedAmount==""){
			 allotedAmount=0;
		      }else{
		    	  
		    	  allotedAmount=removeComma(document.getElementById('allotedAmount'+i).value);
		    	// alert("allotedAmount---"+allotedAmount);
		      }
			 var balanceAmount=document.getElementById('balanceAmount'+i).value;			 
			 if(balanceAmount==""){
				 balanceAmount=0;
		      }else{
		    	  
		    	  balanceAmount=removeComma(document.getElementById('balanceAmount'+i).value);
		    // alert("balanceAmount---"+balanceAmount);
		      }
			 var tdsAllocatedAmount=document.getElementById('tdsAllocatedAmount'+i).value;
				// alert("tdsAllocatedAmount---"+tdsAllocatedAmount);
				 if(tdsAllocatedAmount==""){
				 tdsAllocatedAmount=0;

					 }
				 else{
					 tdsAllocatedAmount=removeComma(document.getElementById('tdsAllocatedAmount'+i).value);	
				 }
				 var tdsadviseAmount=document.getElementById('tdsadviseAmount'+i).value;
					// alert("tdsadviseAmount---"+tdsadviseAmount);
					 if(tdsadviseAmount==""){
						 tdsadviseAmount=0;

						 }
					 else{
						 tdsadviseAmount=removeComma(document.getElementById('tdsadviseAmount'+i).value);	
					 }
					 
		
		  if(allotedAmount > balanceAmount )
			{
			  
				alert(alert1);
				document.getElementById("save").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		
		}
		
		    
		for(i=1;i<=tableLength;i++){
		var allotedAmount=document.getElementById('allotedAmount'+i).value;
		// alert("allotedAmount---"+allotedAmount);
		 if(allotedAmount==""){
		 allotedAmount=0;
	      }else{
	    	  
	    	  allotedAmount=removeComma(document.getElementById('allotedAmount'+i).value);
	    	
	      }
		 		 
		 sum = (sum + allotedAmount);	
		
		 var tdsAllocatedAmount=document.getElementById('tdsAllocatedAmount'+i).value;
		// alert("tdsAllocatedAmount---"+tdsAllocatedAmount);
		 if(tdsAllocatedAmount==""){
		 tdsAllocatedAmount=0;

			 }
		 else{
			 tdsAllocatedAmount=removeComma(document.getElementById('tdsAllocatedAmount'+i).value);	
		 }
		tdsSum = (tdsSum + tdsAllocatedAmount);
		
		if(tdsAllocatedAmount > allotedAmount)
		{
			
			alert("TDS allocated amount should not be greater than Allocated amount");
		
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		}
		
		var sumcomp=0.00;
		var paymentAmountcomp=0.00;
		var tdsAmountcomp=0.00;
		var totam=0.00;
		sumcomp=sum.toFixed(2);
		paymentAmountcomp=paymentAmount.toFixed(2);
		tdsAmountcomp=tdsAmount.toFixed(2);
		if(paymentAmountcomp=='')
		{
			paymentAmountcomp=0.00;	
		}
		if(tdsAmountcomp=='')
		{
			tdsAmountcomp=0.00;	
		}

		totam=parseFloat(paymentAmountcomp)+parseFloat(tdsAmountcomp);

// alert("sum="+sumcomp+" payment amount="+paymentAmountcomp+"
// tdss="+tdsAmountcomp+"(paymentAmountcomp) + (tdsAmountcomp); "+totam);

		if(sumcomp != totam){
			
		alert(alert3);
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	    }
		
		else if(tdsSum != (tdsAmount)){
			alert(alert4);		
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		    }
		
		var basePath=document.getElementById("contextPath").value;		
	    document.getElementById("viewPayableForm").action = basePath+'/paymentCMProcessAction.do?method=inCloseViewPayable';
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("viewPayableForm").submit();
	    return true;
	}
	// metod added by neeraj tripathi
	function generatePaymentReport()
	{
		var forward=document.getElementById("forward").value;
		var frdLoanID=document.getElementById("frdLoanID").value;		
		if(forward=='Yes')
		{
			if(confirm("Do you want to generate Payment Memo Report."))
			{
				var basePath=document.getElementById("contextPath").value;
			    document.getElementById("paymentMakerSearch").action=basePath+"/paymentMemoGenerate.do?frdLoanID="+frdLoanID;
			    document.getElementById("paymentMakerSearch").submit();
			}						
		}
	}
	 function authorSearch(alert1){
		 DisButClass.prototype.DisButMethod();

		// if
		// ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("paymentAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("reportingToUserId").value==""))

		 if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")
				 &&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")
				 &&(document.getElementById("paymentAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("paymentMode").value=="")&&(document.getElementById("reportingToUserId").value==""))
			{
				alert(alert1);
				document.getElementById("search").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}else{
	 var basePath=document.getElementById("contextPath").value;
     document.getElementById("paymentMakerSearch").action=basePath+"/paymentAuthorProcessAction.do?method=authorsearchDetail";
     document.getElementById("processingImage").style.display = '';
     document.getElementById("paymentMakerSearch").submit();		
	 return true;
     }
	 }
	function receiptSearch(alert1){		
		DisButClass.prototype.DisButMethod();

		// if
		// ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("reportingToUserId").value==""))

		if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")
				&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")
				&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("receiptMode").value=="")&&(document.getElementById("reportingToUserId").value==""))

        
		{
			alert(alert1);
			document.getElementById("search").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("receiptMakerSearch").action=basePath+"/receiptMakerSearch.do?search=Y";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("receiptMakerSearch").submit();		
    return true;
	}
	}
	// method added by neeraj tripathi
	function generateReceiptReport()
	{
		var forward=document.getElementById("forward").value;
		var frdLoanID=document.getElementById("frdLoanID").value;
		var frdInstrumentID=document.getElementById("frdInstrumentID").value;
		var autoManualFlag=document.getElementById("autoManualFlag").value;
		if(forward=='Yes')
		{
			document.getElementById('forward').value="No";
			if(autoManualFlag=='A')
			{
				if(confirm("Do you want to generate Receipt Memo Report."))
				{
					var contextPath=document.getElementById("contextPath").value;
					var url=contextPath+"/generateRecptReport.do?method=generateRecptReport&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;
					var popupWin=window.open(url,'a','height=1,width=1,top=0,left=0').focus();					
				}
			}						
		}
	}
	/*function loadFancyBox(url)
	{
		$(document).ready(function () 
		{
			$.fancybox(
			{
			    'width': '100%',
			    'height': '100%',
			    'autoScale': true,
			    'transitionIn': 'fade',
			    'transitionOut': 'fade',
			    'type': 'iframe',
			    'href': url
			  });
		});
		return true;
	}
	function printFancyBox(iFrame)
	{
		if (window.frames[iFrame].innerHTML != "") 
		{
			window.frames[iFrame].focus();            
            try
            {
            	window.frames[iFrame].print();
            }
            catch(err)
            {} 
            finally
            {
            	//setTimeout(function(){window.parent.parent.location.reload()},2000);
            	parent.parent.topNav.reloadReceiptFrame('Receipt');
            }
        } 
		return true;
	}*/
        function newReceiptMaker(){ 
		
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("receiptMakerSearch").action=basePath+"/receiptMakerNewAction.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("receiptMakerSearch").submit();
	    return true;
	}	
 
	   function authoreditDetail(a,b,c)
	    {
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("paymentMakerSearch").action=basePath+"/paymentAuthorProcessAction.do?method=getDatatoApprove&loanId="+a+"&instrumentID="+b+"&businessPartnerType="+c;
		document.getElementById("paymentMakerSearch").submit();
	    }
	
       function getAuthorScreen(){ 
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("paymentAuthorForm").action=basePath+"/paymentAuthorProcessAction.do?method=authorScreen";
	    document.getElementById("paymentAuthorForm").submit();
	   return true;
	   }
       
	 function setText(text1,text2) {
     document.getElementById('dealId').value = text1    
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
	

		
	function onSave(alert1,alert2){

		DisButClass.prototype.DisButMethod();
	if ((document.getElementById("instrumentNumber").value!="")&&(document.getElementById("instrumentID").value!=""))
	{
	var formatD=document.getElementById("formatD").value;	
	var basePath=document.getElementById("contextPath").value;
	var taStatus=document.getElementById("taStatus").checked;
	var instrumentDate='';
	var dt1='';
	if(!taStatus)
	{
		instrumentDate = document.getElementById("instrumentDate").value;	
		dt1 = getDateObject(instrumentDate,formatD.substring(2, 3));
	}
	var paymentDate = document.getElementById("paymentDate").value;	
	var currDate = document.getElementById("businessDate").value;
	//var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
    var dt2=getDateObject(paymentDate,formatD.substring(2, 3));
    var dt3=getDateObject(currDate,formatD.substring(2, 3));
   
    var loanAccountNumber = document.getElementById("loanAccountNumber");
	var businessPartnerType = document.getElementById("businessPartnerType");
	var paymentMode = document.getElementById("paymentMode");
	var paymentDate = document.getElementById("paymentDate");
	var instrumentNumber = document.getElementById("instrumentNumber");
	if(!taStatus)
		instrumentDate = document.getElementById("instrumentDate");
	var bankAccount = document.getElementById("bankAccount");
	var paymentAmount = document.getElementById("paymentAmount");
	// Start By Prashant
		var payTo = document.getElementById("payTo").value;
		var payeeName = document.getElementById("payeeName").value;
	// End By Prashant
		var bpType=document.getElementById("lbxBPType").value;
		var taLoanNo=document.getElementById("taLoanNo").value;
		var cashPaymentLimit=document.getElementById("cashPaymentLimit").value;
		if(taStatus)
		{
			if(taLoanNo=='')	
			{
			alert("Please Select TA Loan No.");
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}
	 if(trim(loanAccountNumber.value) == ''||trim(businessPartnerType.value) == ''||trim(paymentMode.value) == ''||trim(paymentDate.value) == ''||
			 trim(instrumentNumber.value) == ''||
			 trim(bankAccount.value) == ''||trim(paymentAmount.value) == ''){
		 var msg= '';
		 
		 if(trim(loanAccountNumber.value) == '')
	 			msg += '* Loan Account Number is required.\n';
	 		if(trim(businessPartnerType.value) == '')
	 			msg += '* Business Partner Type is required.\n';
	 		if(trim(paymentMode.value) == '')
	 			msg += '* Payment Mode is required.\n';
	 		if(trim(paymentDate.value) == '')
	 			msg += '* Payment Date is required.\n';
	 		if(trim(instrumentNumber.value) == '')
	 			msg += '* Instrument Number / Ref no is required.\n';
	 		//alert(paymentMode.value);
	 		if(paymentMode.value!="C"){
	 		if(trim(instrumentDate.value) == '')
	 			msg += '* Instruments Date is required.\n';
	 		}
	 		if(trim(bankAccount.value) == '')
	 			msg += '* Bank Account is required.\n';
	 		if(trim(paymentAmount.value) == '')
	 			msg += '* Payment Amount is required.\n';
	 		if(msg.match("Loan")){
	 			document.getElementById("loanAccountButton").focus();
	 		}else if(msg.match("Partner")){
	 			document.getElementById("businessPartnerButton").focus();
	 		}else if(msg.match("Mode")){
	 			paymentMode.focus();
	 		}else if(msg.match(/Payment Date/g)){
	 			paymentDate.focus();
	 		}else if(msg.match("Ref")){
	 			instrumentNumber.focus();
	 		}else if(msg.match(/Instruments/g)){
	 			instrumentDate.focus();
	 		}else if(msg.match("Bank")){
	 			document.getElementById("bankAccountButton").focus();
	 		}else if(msg.match("Amount")){
	 			paymentAmount.focus();
	 		}
 		
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }else{
		 if (dt1>dt2)
		  {
		  alert(alert1);
		  document.getElementById("save").removeAttribute("disabled");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
	   /*
		 * if (dt2>dt3) { alert(alert2);
		 * document.getElementById("save").removeAttribute("disabled");
		 * DisButClass.prototype.EnbButMethod(); return false; }
		 */
	 // Start By Prashant
	    if (payTo!='' && payeeName=='')
		  {
			  alert("Payee Name is Required.");
			  document.getElementById("save").removeAttribute("disabled");
			  DisButClass.prototype.EnbButMethod();
			  return false;
		  }
	 // End By Prashant
	 
	    //Start by nishant
	    if(paymentMode.value=="C" && parseFloat(removeComma(paymentAmount.value))>parseFloat(cashPaymentLimit))
	    {
	    	alert("Total of cash payment amount exceeds the cash payment limit per loan (" + cashPaymentLimit + ").");
	    	document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
	    //end by nishant
	   
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
	    
	    document.getElementById("paymentMakerForm").action = basePath+"/paymentMakerForSaveAction.do?method=updateSavedData";
	   document.getElementById("processingImage").style.display = '';
	   document.getElementById("paymentMakerForm").submit();
	   return true;
	   
	 }
	}
	else
	   {
	var formatD=document.getElementById("formatD").value;	
	var basePath=document.getElementById("contextPath").value;
	var taStatus=document.getElementById("taStatus").checked;
	var instrumentDate='';
	var dt1='';
	if(!taStatus)
	{
		instrumentDate = document.getElementById("instrumentDate").value;
		dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	}
	var paymentDate = document.getElementById("paymentDate").value;
	var currDate = document.getElementById("businessDate").value;
	
    var dt2=getDateObject(paymentDate,formatD.substring(2, 3));
    var dt3=getDateObject(currDate,formatD.substring(2, 3));
    
    var loanAccountNumber = document.getElementById("loanAccountNumber");
	var businessPartnerType = document.getElementById("businessPartnerType");
	var paymentMode = document.getElementById("paymentMode");
	var paymentDate = document.getElementById("paymentDate");
	var instrumentNumber = document.getElementById("instrumentNumber");
	if(!taStatus)
		var instrumentDate = document.getElementById("instrumentDate");
	var bankAccount = document.getElementById("bankAccount");
	var paymentAmount = document.getElementById("paymentAmount");
	// Start By Prashant
	var payTo = document.getElementById("payTo").value;
	var payeeName = document.getElementById("payeeName").value;
	// End By Prashant
	var bpType=document.getElementById("lbxBPType").value;
	var taLoanNo=document.getElementById("taLoanNo").value;
	var cashPaymentLimit=document.getElementById("cashPaymentLimit").value;
	
	if(taStatus)
	{
		if(taLoanNo=='')	
		{
		alert("Please Select TA Loan No.");
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		else if(taLoanNo!='')
		{
			if(trim(loanAccountNumber.value) == ''||trim(businessPartnerType.value) == ''||trim(paymentDate.value) == ''||
					trim(paymentAmount.value) == ''){
				 var msg= '';
				 
				 if(trim(loanAccountNumber.value) == '')
			 			msg += '* Loan Account Number is required.\n';
			 		if(trim(businessPartnerType.value) == '')
			 			msg += '* Business Partner Type is required.\n';
			 		
			 		if(trim(paymentDate.value) == '')
			 			msg += '* Payment Date is required.\n';
			 		
			 		if(trim(paymentAmount.value) == '')
			 			msg += '* Payment Amount is required.\n';
			 			 		 		
			 		if(msg.match("Loan")){
			 			document.getElementById("loanAccountButton").focus();
			 		}else if(msg.match("Partner")){
			 			document.getElementById("businessPartnerButton").focus();
			 		}else if(msg.match(/Payment Date/g)){
			 			paymentDate.focus();
			 		}else if(msg.match("Amount")){
			 			paymentAmount.focus();
			 		}
				
				alert(msg);
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				
			 }
		}
	}
	else if(!taStatus)
	{
	if(trim(loanAccountNumber.value) == ''||trim(businessPartnerType.value) == ''||trim(paymentMode.value) == ''||trim(paymentDate.value) == ''||
			 trim(instrumentNumber.value) == ''||
			 trim(bankAccount.value) == ''||trim(paymentAmount.value) == ''){
		 var msg= '';
		 
		 if(trim(loanAccountNumber.value) == '')
	 			msg += '* Loan Account Number is required.\n';
	 		if(trim(businessPartnerType.value) == '')
	 			msg += '* Business Partner Type is required.\n';
	 		if(trim(paymentMode.value) == '')
	 			msg += '* Payment Mode is required.\n';
	 		if(trim(paymentDate.value) == '')
	 			msg += '* Payment Date is required.\n';
	 		if(trim(instrumentNumber.value) == '')
	 			msg += '* Instrument Number / Ref no is required.\n';
	 		// alert(paymentMode.value);
	 		if(paymentMode.value!="C"){
	 		if(trim(instrumentDate.value) == '')
	 			msg += '* Instruments Date is required.\n';
	 		}
	 		if(trim(bankAccount.value) == '')
	 			msg += '* Bank Account is required.\n';
	 		if(trim(paymentAmount.value) == '')
	 			msg += '* Payment Amount is required.\n';
	 			 		 		
	 		if(msg.match("Loan")){
	 			document.getElementById("loanAccountButton").focus();
	 		}else if(msg.match("Partner")){
	 			document.getElementById("businessPartnerButton").focus();
	 		}else if(msg.match("Mode")){
	 			paymentMode.focus();
	 		}else if(msg.match(/Payment Date/g)){
	 			paymentDate.focus();
	 		}else if(msg.match("Ref")){
	 			instrumentNumber.focus();
	 		}else if(msg.match(/Instruments/g)){
	 			instrumentDate.focus();
	 		}else if(msg.match("Bank")){
	 			document.getElementById("bankAccountButton").focus();
	 		}else if(msg.match("Amount")){
	 			paymentAmount.focus();
	 		}
		
		alert(msg);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		
	 }
	}
		if (dt1>dt2)
		  {
		  alert(alert1);
		  document.getElementById("save").removeAttribute("disabled");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
	    /*
		 * if (dt2>dt3) { alert(alert2);
		 * document.getElementById("save").removeAttribute("disabled");
		 * DisButClass.prototype.EnbButMethod(); return false; }
		 */
	// Start By Prashant
	    if (payTo!='' && payeeName=='')
		  {
			  alert("Payee Name is Required.");
			  document.getElementById("save").removeAttribute("disabled");
			  DisButClass.prototype.EnbButMethod();
			  return false;
		  }
	 // End By Prashant
	  //Start by nishant
	    if(paymentMode.value=="C" && parseFloat(removeComma(paymentAmount.value))>parseFloat(cashPaymentLimit))
	    {
	    	alert("Total of cash payment amount exceeds the cash payment limit per loan (" + cashPaymentLimit + ").");
	    	document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
	    //end by nishant
	    
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
	    
	 if(document.getElementById("instrumentID").value=='')
	   	document.getElementById("paymentMakerForm").action = basePath+"/paymentMakerForSaveAction.do?method=saveAllData";
	 else
		document.getElementById("paymentMakerForm").action = basePath+"/paymentMakerForSaveAction.do?method=updateSavedData";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("paymentMakerForm").submit();
	     return true;
	 	
	
	 }     
}

	
	
	   function onPaymentForward(alert1,alert2,alert3){
			
		   DisButClass.prototype.DisButMethod();
		    var basePath=document.getElementById("contextPath").value;
			var txnAdvicedID=document.getElementById("txnAdvicedID").value;
			var loanId=document.getElementById("lbxLoanNoHID").value;
			var instrumentno=document.getElementById("instrumentNumber").value;
			// Start By Prashant
			var payTo = document.getElementById("payTo").value;
			var payeeName = document.getElementById("payeeName").value;
			// End By Prashant
			// var pmntId=document.getElementById("hidPmntId").value;
			var paymentAmount=removeComma(document.getElementById('paymentAmount').value);
		
			 var  tdsAmount=document.getElementById('tdsAmount').value;
			 if(tdsAmount==""){
				 tdsAmount="0";			
				 }
			 else
			 {
			 tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
			 }
			var amount = (parseFloat(paymentAmount) +parseFloat(tdsAmount));
			var formatD=document.getElementById("formatD").value;
			var taStatus=document.getElementById("taStatus").checked;
			var instrumentDate='';
			var dt1='';
			if(!taStatus)
			{
				instrumentDate = document.getElementById("instrumentDate").value;
				dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
			}
			var paymentDate = document.getElementById("paymentDate").value;
			var currDate = document.getElementById("businessDate").value;
		    var dt2=getDateObject(paymentDate,formatD.substring(2, 3));
		    var dt3=getDateObject(currDate,formatD.substring(2, 3));
		    //Nishant space starts
		    var loanAccountNumber = document.getElementById("loanAccountNumber");
			var businessPartnerType = document.getElementById("businessPartnerType");
			var paymentDate = document.getElementById("paymentDate");
			var instrumentNumber = document.getElementById("instrumentNumber");
			var paymentAmount = document.getElementById("paymentAmount");
			var taLoanNo=document.getElementById("taLoanNo").value;
			var paymentMode = document.getElementById("paymentMode");
			var bankAccount = document.getElementById("bankAccount");
		    if(taStatus)
			{
				if(taLoanNo=='')	
				{
				alert("Please Select TA Loan No.");
				document.getElementById("save").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
				else if(taLoanNo!='')
				{
					if(trim(loanAccountNumber.value) == ''||trim(businessPartnerType.value) == ''||trim(paymentDate.value) == ''||
							trim(paymentAmount.value) == ''){
						 var msg= '';
						 
						 if(trim(loanAccountNumber.value) == '')
					 			msg += '* Loan Account Number is required.\n';
					 		if(trim(businessPartnerType.value) == '')
					 			msg += '* Business Partner Type is required.\n';
					 		
					 		if(trim(paymentDate.value) == '')
					 			msg += '* Payment Date is required.\n';
					 		
					 		if(trim(paymentAmount.value) == '')
					 			msg += '* Payment Amount is required.\n';
					 			 		 		
					 		if(msg.match("Loan")){
					 			document.getElementById("loanAccountButton").focus();
					 		}else if(msg.match("Partner")){
					 			document.getElementById("businessPartnerButton").focus();
					 		}else if(msg.match(/Payment Date/g)){
					 			paymentDate.focus();
					 		}else if(msg.match("Amount")){
					 			paymentAmount.focus();
					 		}
						
						alert(msg);
						document.getElementById("save").removeAttribute("disabled","true");
						DisButClass.prototype.EnbButMethod();
						return false;
						
					 }
				}
			}
		    else if(!taStatus)
			{
			if(trim(loanAccountNumber.value) == ''||trim(businessPartnerType.value) == ''||trim(paymentMode.value) == ''||trim(paymentDate.value) == ''||
					 trim(instrumentNumber.value) == ''||
					 trim(bankAccount.value) == ''||trim(paymentAmount.value) == ''){
				 var msg= '';
				 
				 if(trim(loanAccountNumber.value) == '')
			 			msg += '* Loan Account Number is required.\n';
			 		if(trim(businessPartnerType.value) == '')
			 			msg += '* Business Partner Type is required.\n';
			 		if(trim(paymentMode.value) == '')
			 			msg += '* Payment Mode is required.\n';
			 		if(trim(paymentDate.value) == '')
			 			msg += '* Payment Date is required.\n';
			 		if(trim(instrumentNumber.value) == '')
			 			msg += '* Instrument Number / Ref no is required.\n';
			 		if(paymentMode.value!="C"){
			 		if(trim(instrumentDate) == '')
			 			msg += '* Instruments Date is required.\n';
			 		}
			 		if(trim(bankAccount.value) == '')
			 			msg += '* Bank Account is required.\n';
			 		if(trim(paymentAmount.value) == '')
			 			msg += '* Payment Amount is required.\n';
			 			 		 		
			 		if(msg.match("Loan")){
			 			document.getElementById("loanAccountButton").focus();
			 		}else if(msg.match("Partner")){
			 			document.getElementById("businessPartnerButton").focus();
			 		}else if(msg.match("Mode")){
			 			paymentMode.focus();
			 		}else if(msg.match(/Payment Date/g)){
			 			paymentDate.focus();
			 		}else if(msg.match("Ref")){
			 			instrumentNumber.focus();
			 		}else if(msg.match(/Instruments/g)){
			 			instrumentDate.focus();
			 		}else if(msg.match("Bank")){
			 			document.getElementById("bankAccountButton").focus();
			 		}else if(msg.match("Amount")){
			 			paymentAmount.focus();
			 		}
				
				alert(msg);
				document.getElementById("save").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
				
			 }
			}
		    //Nishant space ends
		    
		    if (document.getElementById("canForward").value=="")
		      {	 
		    alert("Please save the data first");	
		    DisButClass.prototype.EnbButMethod();
		    return false;
		      }
			if((document.getElementById("paymentMakerForm"))){
				if (dt1>dt2)
				  {
				  alert(alert2);
				  document.getElementById("saveForward").removeAttribute("disabled");
				  DisButClass.prototype.EnbButMethod();
				  return false;
				  }
			   /*
				 * if (dt2>dt3) { alert(alert3);
				 * document.getElementById("saveForward").removeAttribute("disabled");
				 * DisButClass.prototype.EnbButMethod(); return false; }
				 */
			 // Start By Prashant
			    if (payTo!='' && payeeName=='')
				  {
					  alert("Payee Name is Required.");
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
				  }
			 // End By Prashant
			    
			    // Ravi start
			    
			    if(document.getElementById("loanRecStatus").value!='')
			    {
			    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
			    	{
			    		var status = confirm("Loan is on pending stage. Do you want to continue..");
			    		// alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("saveForward").removeAttribute("disabled");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}else if(document.getElementById("loanRecStatus").value=='C')
			    	{
			    		var status = confirm("Loan is close. Do you want to continue..");
			    		// alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("saveForward").removeAttribute("disabled");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}
			    	
			    }
			    
			    // Ravi End
			    
			    
			document.getElementById("paymentMakerForm").action=basePath+'/paymentMakerForSaveAction.do?method=paymentForwardCheck&loanId='+loanId+'&amount='+amount+'&tdsAmount='+tdsAmount;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("paymentMakerForm").submit();
		    return true;
			   }  
			else
			{
				document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		
				  		 
		}

	   function onAllocatedPayable() 
	  	{	
		  
		     var loanId=document.getElementById("lbxLoanNoHID").value;		    
			 var bpType=document.getElementById("businessPartnerType").value;	
			 var instrumentID=document.getElementById("instrumentID").value;
			 
	  		 var basePath=document.getElementById("contextPath").value;
	  		 window.open(basePath+'/paymentAuthorProcessAction.do?method=viewAllocatedPayable&loanId='+loanId+'&bpType='+bpType+'&instrumentID='+instrumentID,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
	  		 return true;
	  		
	  	}

	function onSaveOfAuthor(alert1){
		
		   DisButClass.prototype.DisButMethod();
			if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
			   {
				alert(alert1);
				document.getElementById("save").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			   }
			else
			{
			var basePath=document.getElementById("contextPath").value;
			 // Ravi start
		    
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("save").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("save").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		    
		    // Ravi End
			document.getElementById("paymentAuthorForm").action = basePath+"/paymentAuthorProcessAction.do?method=onSaveAuthor";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("paymentAuthorForm").submit();
			 return true;
			 
			   }
			   }     
	
	
     function onSaveReceipt(alert2,alert3)
      {
        DisButClass.prototype.DisButMethod();        	
	   	var formatD=document.getElementById("formatD").value;
		var basePath=document.getElementById("contextPath").value;
		var instrumentDate = document.getElementById("instrumentDate").value;
		var receiptDate = document.getElementById("receiptDate").value;
		var currDate = document.getElementById("businessDate").value;
		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
	    var receiptNoFlag=document.getElementById("receiptNoFlag").value;
	    var receiptNo=document.getElementById("receiptNo").value;
	    var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
	  
	    var cashDepositFlag = document.getElementById("cashDepositFlag").value;
		var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
		var depositBankAccount	= document.getElementById("depositBankAccount").value;
		var depositBankID	= document.getElementById("depositBankID").value;
		var depositBranchID	= document.getElementById("depositBranchID").value;
		var receiptMode	= document.getElementById("receiptMode").value;
		if(depositBankAccount==null || depositBankAccount=='undefined')
		{
			depositBankAccount='';
		}
		if(depositBankID==null || depositBankID=='undefined')
		{
			depositBankID='';
		}
		if(depositBranchID==null || depositBranchID=='undefined')
		{
			depositBranchID='';
		}
		if(document.getElementById("allocationGridReceipt").value=='Y')
		{
	
				if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
					
					if(receiptAmount=="0")
					{
			    		alert(" Receipt Amount must be greater than 0 ");
			    		document.getElementById("receiptAmount").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		 return false;
			    	}
					if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
			    	{
					 if (dt1>dt2)
					  {
					  alert(alert2);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
			    	}
				    if (dt2>dt3)
					  {
					  alert(alert3);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
					/*if(receiptNoFlag=='Y'&& receiptNo=='')
			    	{
		    			alert("Receipt No. is required");
		    			DisButClass.prototype.EnbButMethod();
		    			document.getElementById("save").removeAttribute("disabled");
		    			return false;
			    			
			    	}*/
					
					
					if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
				  	{
							alert("Receipt No. is required");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled");
							return false;
				  	}
				
				    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
					{
				    	    if((document.getElementById("instrumentNumber").value)==""){
				    		alert("Instrument Number / Ref no is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
			    	       if((document.getElementById("instrumentDate").value)==""){
				    		alert("Instrument date is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    		   if((document.getElementById("bank").value)==""){
				    		alert("Issuing Bank is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    				    		
					}
				    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    if(validateReceiptAllocationGrid())
					   {
					    	var repoFlagMarked= document.getElementById('repoFlagMarked').value;
						    if(repoFlagMarked=='A')
						    {
						    	if(confirm("Loan is marked as Repo.Do you want to proceed"))
						    	{
						    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
						    	}
						    	else
						    	{
						    		 DisButClass.prototype.EnbButMethod();
						    		 return false;
						    	}
						    }
						    else
						    {
						    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
						    }
							 
							 document.getElementById("processingImage").style.display = '';		
							 document.getElementById("receiptMakerForm").submit();
						     return true;
					   }
				}
				else
				{		
					document.getElementById("save").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		
		}
		else
		{
	
				if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
					
					if(receiptAmount=="0")
					{
			    		alert(" Allocation Amount must be greater than 0 ");
			    		document.getElementById("receiptAmount").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		 return false;
			    	}
					if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
			    	{
					 if (dt1>dt2)
					  {
					  alert(alert2);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
			    	}
				    if (dt2>dt3)
					  {
					  alert(alert3);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
				/*	if(receiptNoFlag=='Y'&& receiptNo=='')
			    	{
		    			alert("Receipt No. is required");
		    			DisButClass.prototype.EnbButMethod();
		    			document.getElementById("save").removeAttribute("disabled");
		    			return false;
			    			
			    	}*/
					if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
				  	{
							alert("Receipt No. is required");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled");
							return false;
				  	}
				    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
					{
				    	    if((document.getElementById("instrumentNumber").value)==""){
				    		alert("Instrument Number / Ref no is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
			    	       if((document.getElementById("instrumentDate").value)==""){
				    		alert("Instrument date is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    		   if((document.getElementById("bank").value)==""){
				    		alert("Issuing Bank is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
				    		alert("Issuing Branch is required  ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}*/
				    		/*
							 * if((document.getElementById("bankAccount").value =="") &&
							 * ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
							 * 
							 * alert("Please select bank Account ");
							 * document.getElementById("save").removeAttribute("disabled");
							 * DisButClass.prototype.EnbButMethod(); return false; }
							 */
				    		
					}
				    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    var repoFlagMarked= document.getElementById('repoFlagMarked').value;
				    if(repoFlagMarked=='A')
				    {
				    	if(confirm("Loan is marked as Repo.Do you want to proceed"))
				    	{
				    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
				    	}
				    	else
				    	{
				    		 DisButClass.prototype.EnbButMethod();
				    		 return false;
				    	}
				    }
				    else
				    {
				    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
				    }
				 
				 document.getElementById("processingImage").style.display = '';		
				 document.getElementById("receiptMakerForm").submit();
			     return true;
				}
				else
				{		
					document.getElementById("save").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
		  }

   }

      function savedReceiptDetail(a,b)
    {
	var basePath=document.getElementById("contextPath").value;
	
	document.getElementById("receiptMakerSearch").action=basePath+"/receiptMakerViewAction.do?method=savedDataOfReceipt&loanId="+a+"&instrumentID="+b;
	document.getElementById("receiptMakerSearch").submit();
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
     

     	function  onReceiptForwardCheck(alert2,alert3){
     		
     		DisButClass.prototype.DisButMethod();
     		var loanID=document.getElementById("lbxLoanNoHID").value;
     		var instrumentID=document.getElementById("instrumentID").value;
     		var basePath=document.getElementById("contextPath").value;
   		    var formatD=document.getElementById("formatD").value;	 
   		 	
   	 		var instrumentDate = document.getElementById("instrumentDate").value;
   	 		var receiptDate = document.getElementById("receiptDate").value;
   	 		var currDate = document.getElementById("businessDate").value;
   	 		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
   	 	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
   	 	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
   	 	    var receiptAmount=removeComma(document.getElementById('receiptAmount').value); 
   	 	    var  tdsAmount=document.getElementById('tdsAmount').value;
		    if(tdsAmount==""){
			 tdsAmount="0";			
			 }
		    else
		     {
		    tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
		     }
		// alert("receiptAmount"+receiptAmount);
		// alert("tdsAmount"+tdsAmount);
		var amount = (receiptAmount + tdsAmount);
		
		 
      if (document.getElementById("canForward").value=="")	
       {	 
			 alert("Please save the data first");	
		     DisButClass.prototype.EnbButMethod();	  
		     return false;
       	}
 
      if (document.getElementById("allocationGridReceipt").value=='Y')	
      {	
		 if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
			 if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
		    	{
			 if (dt1>dt2)
			  {
			  alert(alert2);
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
	    	}
			if (dt2>dt3)
			  {
			  alert(alert3);
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
			
			 // Ravi start
		    var loanRecStatus="";
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	loanRecStatus = document.getElementById("loanRecStatus").value;
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("saveForward").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("saveForward").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		    
		    // Ravi End
		      if (document.getElementById("loanBranch").value=='Y')	
		      {	 
		    	  var status = confirm("Login branch and loan branch is not same,Do you want to Forword.");
		    		if(!status)
		    		{
		    			document.getElementById("saveForward").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		      }
		      if(validateReceiptAllocationGrid())
			   {
					document.getElementById("receiptMakerForm").action=basePath+'/receiptMakerProcessAction.do?method=saveForwardReceiptTemp&amount='+amount+"&loanID="+loanID+"&instrumentID="+instrumentID+"&loanRecStatus="+loanRecStatus;
					document.getElementById("processingImage").style.display = '';
			 	    document.getElementById("receiptMakerForm").submit();
					return true;
			   }
	  
		  }
	 else{
		  DisButClass.prototype.EnbButMethod();
		  return false;
		 
	     }
      }
      else
      {
    	  if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
    		  
    			 if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
    		    	{
    				 if (dt1>dt2)
    				  {
    				  alert(alert2);
    				  DisButClass.prototype.EnbButMethod();
    				  return false;
    				  }
    		    	}
    				if (dt2>dt3)
    				  {
    				  alert(alert3);
    				  DisButClass.prototype.EnbButMethod();
    				  return false;
    				  }
    				
    				 // Ravi start
    			    var loanRecStatus="";
    			    if(document.getElementById("loanRecStatus").value!='')
    			    {
    			    	loanRecStatus = document.getElementById("loanRecStatus").value;
    			    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
    			    	{
    			    		var status = confirm("Loan is on pending stage. Do you want to continue..");
    			    		// alert("status :"+ status);
    			    		if(!status)
    			    		{
    			    			document.getElementById("saveForward").removeAttribute("disabled");
    							DisButClass.prototype.EnbButMethod();
    							return false;
    			    		}
    			    	}else if(document.getElementById("loanRecStatus").value=='C')
    			    	{
    			    		var status = confirm("Loan is close. Do you want to continue..");
    			    		// alert("status :"+ status);
    			    		if(!status)
    			    		{
    			    			document.getElementById("saveForward").removeAttribute("disabled");
    							DisButClass.prototype.EnbButMethod();
    							return false;
    			    		}
    			    	}
    			    	
    			    }
    			    
    			    // Ravi End
    			      if (document.getElementById("loanBranch").value=='Y')	
    			      {	 
    			    	  var status = confirm("Login branch and loan branch is not same,Do you want to Forword.");
    			    		if(!status)
    			    		{
    			    			document.getElementById("saveForward").removeAttribute("disabled");
    							DisButClass.prototype.EnbButMethod();
    							return false;
    			    		}
    			      }
    			     
    				document.getElementById("receiptMakerForm").action=basePath+'/receiptMakerProcessAction.do?method=saveForwardReceiptTemp&amount='+amount+"&loanID="+loanID+"&instrumentID="+instrumentID+"&loanRecStatus="+loanRecStatus;
    				document.getElementById("processingImage").style.display = '';
    		 	    document.getElementById("receiptMakerForm").submit();
    				return true;
    		  
    			  }
    		 else{
    			  DisButClass.prototype.EnbButMethod();
    			  return false;
    			 
    		     }
      }
  }
 
  
      
     	function validateNumber(value,id)
     	{
     		
     		var val="";
     		if(value.trim()==""){
     			val="0.00";
     			
     		}
     		else{
     			val=value;
     			
     		}     	
        	formatNumber(val,id);
     	}
        	function onViewReceivableSave(alert1)  {
        		
        		DisButClass.prototype.DisButMethod();
    	 // alert("alert1 : "+alert1);
    	 var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
  		 var  tdsAmount=document.getElementById('tdsAmount').value;
  		 if(tdsAmount=="")
  			 tdsAmount=0;
		 else
			 tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
		var tdsSum=0;
  		var sum=0;
  		var allocatedSum=0;
  		var i;
  		var gridTable = document.getElementById('gridTable');
  		var tableLength = gridTable.rows.length-1;
  		for(i=1;i<=tableLength;i++)
  		{
  
  			var allotedAmount=document.getElementById('allotedAmount'+i).value;			 
			 if(allotedAmount=="")
			  allotedAmount=0;
		     else
			  allotedAmount=removeComma(document.getElementById('allotedAmount'+i).value);		    	 
		    
			 var balanceAmount=document.getElementById('balanceAmount'+i).value;			 
			 if(balanceAmount=="")
			 	 balanceAmount=0;
		   	 else
			  	  balanceAmount=removeComma(document.getElementById('balanceAmount'+i).value);		    	  
		    	
			// alert("allotedAmount: "+allotedAmount+"balanceAmount:
			// "+balanceAmount);
			 allocatedSum = (allocatedSum + allotedAmount);  
  			if(allotedAmount > balanceAmount )
  			{ 				
  				alert("Allocated Amount should not be greater than Balance Amount");
  				document.getElementById("save").removeAttribute("disabled");
  				DisButClass.prototype.EnbButMethod();
  				return false;
  			}	
  		}  		
  		for(i=1;i<=tableLength;i++)
  		{  			
  			var allotedAmount=document.getElementById('allotedAmount'+i).value;  		  
  			if(allotedAmount=="")
  				allotedAmount=0;
  		    else
  		    	allotedAmount=removeComma(document.getElementById('allotedAmount'+i).value);
  		    		      
  			 sum = (sum + allotedAmount);	  			
  			 var tdsAllocatedAmount=document.getElementById('tdsAllocatedAmount'+i).value;  		    
  			 if(tdsAllocatedAmount=="")
  				 tdsAllocatedAmount=0;
 			 else
  				 tdsAllocatedAmount=removeComma(document.getElementById('tdsAllocatedAmount'+i).value);	
  			 
  			tdsSum = (tdsSum + tdsAllocatedAmount);  			
  			if(tdsAllocatedAmount > allotedAmount)
  			{  				
  				alert("TDS allocated amount should not be greater than Allocated amount");  			
  				document.getElementById("save").removeAttribute("disabled");
  				DisButClass.prototype.EnbButMethod();
  				return false;
  			}
  		}
  		var totalamt=receiptAmount + tdsAmount;
		// if(sum > (totalamt))
		// {
		// alert(alert1);
		// document.getElementById("save").removeAttribute("disabled");
		// return false;
		// }
		// if(totalamt > sum)
		// {
		// alert(alert1);
		// document.getElementById("save").removeAttribute("disabled");
		// return false;
		// }
  		
  		
  		  		
  		if(allocatedSum > (receiptAmount))
		{
			alert("Total Allocated Amount should be equal to or  less than Receipt Amount");		
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		} 
  		
		if(tdsSum != (tdsAmount))
		{
			alert("Total TDS Allocated Amount should be equal to  TDS Amount");		
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}  			
  		var basePath=document.getElementById("contextPath").value;  				
  	    document.getElementById("viewReceivableForm").action = basePath+'/receiptMakerViewAction.do?method=onSaveViewReceivable';
  	    document.getElementById("processingImage").style.display = ''; 
  	    document.getElementById("viewReceivableForm").submit();  	   
  	    return true;  	 
  	}
      
      function allocateReceivable(alert1){
    	  
    	  
		var sum =0;
		var amount=0;
		var tdsAmount=document.getElementById('tdsAmount').value;
		var receiptamount=document.getElementById('receiptAmount').value;
     	if(receiptamount!='' || receiptamount>0)
     		var receiptamount=removeComma(document.getElementById('receiptAmount').value); 	 
     	else
     		var receiptamount=0;
     	 if(tdsAmount!='' || tdsAmount>0)
     		var tdsAmount=removeComma(document.getElementById('tdsAmount').value);
    	else
    		var tdsAmount=0;
     	
     	// alert("receiptamount"+amount);
     // alert("tdsAmount"+tdsAmount);
     
    	sum =parseFloat(receiptamount) + parseFloat(tdsAmount);
    // alert("sum"+sum);
    	amount=sum;
    	if (document.getElementById("canForward").value==""){
    		DisButClass.prototype.DisButMethod();
    		alert(alert1);
  			document.getElementById("allocateRec").removeAttribute("disabled");
  			DisButClass.prototype.EnbButMethod();
  			return false;
    	}
    	else if(sum >0)
        {
        	if (document.getElementById("canForward").value!="")
      		{
      			
      		 var loanId=document.getElementById('lbxLoanNoHID').value;	
      		 var bpType=document.getElementById('lbxBPType').value;
      		 var bpId=document.getElementById('lbxBPNID').value;
      		 var instrumentId=document.getElementById('instrumentID').value;
     	
      		var basePath=document.getElementById("contextPath").value;
      		window.open(basePath+'/receiptMakerViewAction.do?method=allocateReceivableData&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId+'&instrumentId='+instrumentId+'&receiptamount='+receiptamount+'&tdsAmount='+tdsAmount+'&amount='+amount,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
      	 	return true;
      	 	  		 
      		   }
      		 else
      		 {
      			DisButClass.prototype.DisButMethod();
      			alert(alert1);
      			document.getElementById("allocateRec").removeAttribute("disabled");
      			DisButClass.prototype.EnbButMethod();
      			return false;
      		 }
        }
       else{
        	alert("Receipt amount and Tds amount sum should be greater than zero");
        	document.getElementById("allocateRec").setAttribute("disabled",true);
        	
        }
    
  	}
      /*
		 * function blankcanforward(){
		 * 
		 * document.getElementById("canForward").value="";
		 *  }
		 * 
		 * 
		 * 
		 * function onDataPresent() { var
		 * receiptNo=document.getElementById('receiptNo').value; var contextPath
		 * =document.getElementById('contextPath').value ;
		 * 
		 * if (receiptNo!= '') { var address =
		 * contextPath+"/receiptMakerAction.do?method=getReceiptAmountData"; var
		 * data = "receiptNo="+receiptNo; send_Rate(address,data); return true; }
		 * else { return false; } }
		 * 
		 * function send_Rate(address,data) { var request = getRequestObject();
		 * request.onreadystatechange = function () { result_Rate(request); };
		 * request.open("POST", address, true);
		 * request.setRequestHeader("Content-Type",
		 * "application/x-www-form-urlencoded"); request.send(data); }
		 * 
		 * function result_Rate(request) { if ((request.readyState == 4) &&
		 * (request.status == 200)) { var str = request.responseText; var s1 =
		 * str.split("$:");
		 * 
		 * if(s1.length>0) { document.getElementById('receiptAmountNew').value =
		 * trim(s1[1]); if (removeComma(trim(s1[1]))>0) {
		 * document.getElementById("receiptAmountNew").setAttribute("readOnly",true); }
		 * else {
		 * document.getElementById("receiptAmountNew").removeAttribute("readOnly",true); }
		 * document.getElementById('receiptAmount').value = trim(s1[2]); } } }
		 */
     function viewReceivable(alert1) 
  	  {	
    	 
  		if (((document.getElementById("lbxLoanNoHID").value)=="") || ((document.getElementById("businessPartnerType").value)==""))
  		{
  			 DisButClass.prototype.DisButMethod();
  			alert(alert1);	
  			DisButClass.prototype.EnbButMethod();
  			return false;
  		}
  		else
  		{
  			var loanId=document.getElementById('lbxLoanNoHID').value;
  			var bpType=document.getElementById('lbxBPType').value; 
  			var bpId=document.getElementById('lbxBPNID').value;	
  			var basePath=document.getElementById("contextPath").value;
  			window.open(basePath+'/receiptMakerViewAction.do?method=viewReceivable&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
  			return true;
  		}
  	}
  	
  	function receiptAuthorSearch(alert1){
  		DisButClass.prototype.DisButMethod();

  		// if
		// ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("reportingToUserId").value==""))

  		if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")
  				&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")
  				&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("receiptMode").value=="")&&(document.getElementById("reportingToUserId").value==""))


		{
			alert(alert1);
			document.getElementById("search").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
  		else{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("receiptMakerSearch").action=basePath+"/receiptAuthorProcessAction.do?method=receiptAuthorSearchDetail";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("receiptMakerSearch").submit();		
		return true;
  	}
  	}
  	function receiptAuthorSavedData(a,b,c)
	{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("receiptMakerSearch").action=basePath+"/receiptAuthorProcessAction.do?method=getReceipttoApprove&loanId="+a+"&instrumentID="+b+"&businessPartnerType="+c;
		document.getElementById("receiptMakerSearch").submit();
		
	}
  	
  	function receiptAuthorScreen(){ 
  			var basePath=document.getElementById("contextPath").value;
  		    document.getElementById("receiptAuthorForm").action=basePath+"/receiptAuthorProcessAction.do?method=receiptAuthorScreen";
  		    document.getElementById("receiptAuthorForm").submit();
  	}

  	function onAllocatedReceivable() 
  	{	
  		curWin = 0;
  		otherWindows = new Array();
  		
	     var loanId=document.getElementById("lbxLoanNoHID").value;		    
		 var bpType=document.getElementById("businessPartnerType").value;	
		 var instrumentID=document.getElementById("instrumentID").value;
  		 var basePath=document.getElementById("contextPath").value;
  		 otherWindows[curWin++] =window.open(basePath+'/receiptAuthorProcessAction.do?method=viewAllocatedReceivable&loanId='+loanId+'&bpType='+bpType+'&instrumentID='+instrumentID,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
  		
  		 return true;
  		
  	}
  	
  	 function onSaveReceiptAuthor(alert1){
  		 
  		
  		if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
		   {
  			DisButClass.prototype.DisButMethod();
			alert(alert1);
			document.getElementById("save").removeAttribute("disabled");	
			DisButClass.prototype.EnbButMethod();
			return false;
		   }
		else
		{
  		  var basePath=document.getElementById("contextPath").value;
  		  
  		 // Ravi start
		    
		    if(document.getElementById("loanRecStatus").value!='')
		    {
		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
		    	{
		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("save").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}else if(document.getElementById("loanRecStatus").value=='C')
		    	{
		    		var status = confirm("Loan is close. Do you want to continue..");
		    		// alert("status :"+ status);
		    		if(!status)
		    		{
		    			document.getElementById("save").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		    	}
		    	
		    }
		    
		    // Ravi End
  		  
  		  document.getElementById("receiptAuthorForm").action=basePath+"/receiptAuthorProcessAction.do?method=onSaveReceiptAuthor";
  		  document.getElementById("processingImage").style.display = '';
  		  document.getElementById("receiptAuthorForm").submit();

  		}
  	
  	 }
  	 
  	 function deletePayment(){
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var instrumentID=document.getElementById("instrumentID").value;
	// alert("loanId......"+loanId);
	agree=confirm("Are you sure,You want to delete this Payment.Do you want to continue?");
	if (!(agree))
	{		
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
    	return false;
	}else{
		document.getElementById("paymentMakerForm").action=sourcepath+"/paymentCMProcessAction.do?method=deletePayment&instrumentID"+instrumentID;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("paymentMakerForm").submit();
		return true;
}
	
	
}
  	function deleteReceipt(){
  		
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var instrumentID=document.getElementById("instrumentID").value;
	// alert("loanId......"+loanId);
	agree=confirm("Are you sure,You want to delete this Receipt.Do you want to continue?");
	if (!(agree))
	{	
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("receiptMakerForm").action=sourcepath+"/receiptMakerProcessAction.do?method=deleteReceipt&instrumentID"+instrumentID;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("receiptMakerForm").submit();
		return true;
}
	
	
}
  	
  	function onPaymentForwardWithoutCheck(alert1,alert2,alert3){
		
		   DisButClass.prototype.DisButMethod();
		    var basePath=document.getElementById("contextPath").value;
			var txnAdvicedID=document.getElementById("txnAdvicedID").value;
			var loanId=document.getElementById("lbxLoanNoHID").value;
			var instrumentno=document.getElementById("instrumentNumber").value;
			// Start By Prashant
			var payTo = document.getElementById("payTo").value;
			var payeeName = document.getElementById("payeeName").value;
			// End By Prashant
			// var pmntId=document.getElementById("hidPmntId").value;
			var paymentAmount=removeComma(document.getElementById('paymentAmount').value);
		
			 var  tdsAmount=document.getElementById('tdsAmount').value;
			 if(tdsAmount==""){
				 tdsAmount="0";			
				 }
			 else
			 {
			 tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
			 }
			var amount = (parseFloat(paymentAmount) +parseFloat(tdsAmount));
			var formatD=document.getElementById("formatD").value;
			var instrumentDate = document.getElementById("instrumentDate").value;
			var paymentDate = document.getElementById("paymentDate").value;
			var currDate = document.getElementById("businessDate").value;
			var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
		    var dt2=getDateObject(paymentDate,formatD.substring(2, 3));
		    var dt3=getDateObject(currDate,formatD.substring(2, 3));
		    
		    
		    var bpType=document.getElementById("lbxBPType").value;
			var taStatus=document.getElementById("taStatus").checked;
			var taLoanNo=document.getElementById("taLoanNo").value;

			if(taStatus)
			{
				if(taLoanNo=='')	
				{
				alert("Please Select TA Loan No.");
				document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			}
		    
		 
		    if (document.getElementById("canForward").value=="")
		      {	 
		    alert("Please save the data first");	
		    DisButClass.prototype.EnbButMethod();
		    return false;
		      }
			if((document.getElementById("paymentMakerForm"))){
				if (dt1>dt2)
				  {
				  alert(alert2);
				  document.getElementById("saveForward").removeAttribute("disabled");
				  DisButClass.prototype.EnbButMethod();
				  return false;
				  }
			   /*
				 * if (dt2>dt3) { alert(alert3);
				 * document.getElementById("saveForward").removeAttribute("disabled");
				 * DisButClass.prototype.EnbButMethod(); return false; }
				 */
			 // Start By Prashant
			    if (payTo!='' && payeeName=='')
				  {
					  alert("Payee Name is Required.");
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
				  }
			 // End By Prashant
			document.getElementById("paymentMakerForm").action=basePath+'/paymentMakerForSaveAction.do?method=paymentForwardedWithoutCheck&loanId='+loanId+'&amount='+amount+'&tdsAmount='+tdsAmount;
			// document.getElementById("processingImage").style.display = '';
			document.getElementById("paymentMakerForm").submit();
		    return true;
			   }  
			else
			{
				document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		
				  		 
		}
  	
  	function depositShowHide()
  	{
  		
  		var cashDepositFlag=document.getElementById("cashDepositFlag").value;
  		var nonCashDepositFlag=document.getElementById("nonCashDepositFlag").value;
  		

  		
  		var receiptMode=document.getElementById("receiptMode").value;
  		
  		if(receiptMode=="")
  			document.getElementById("bButton").style.display="none";
  		else if(cashDepositFlag=="Y" && nonCashDepositFlag=="Y")
  			document.getElementById("bButton").style.display="";
  		else if(cashDepositFlag=="Y" || nonCashDepositFlag=="Y")
  		{
  			if(cashDepositFlag=="Y" && receiptMode=="C")
  				document.getElementById("bButton").style.display="";
  			else if(cashDepositFlag=="Y" && receiptMode!="C")
  				document.getElementById("bButton").style.display="none";
  			else if(nonCashDepositFlag=="Y" && receiptMode=="C")
  				document.getElementById("bButton").style.display="none";
  			else if(nonCashDepositFlag=="Y" && receiptMode!="C")
  				document.getElementById("bButton").style.display="";
  		}
  		else if(cashDepositFlag=="N" && nonCashDepositFlag=="N")
  			document.getElementById("bButton").style.display="none";
  		
  		else if(cashDepositFlag=="N" || nonCashDepositFlag=="N"){
  			if(cashDepositFlag=="N" && receiptMode=="C")
  				document.getElementById("bButton").style.display="none";
  			else if(nonCashDepositFlag=="N" && receiptMode!="C")
  				document.getElementById("bButton").style.display="none";
  		}
   	}
  	
  	function openDepositBranchLov()
  	{
  		var receiptMode=document.getElementById("receiptMode").value;	
  		if(receiptMode=="C")
  			openLOVCommon(538,'receiptMakerForm','depositBankID','','temp', '','','getBranchIdMICR','temp','depositBankAccount','depositIfscCode');
  		else if(receiptMode=="S")
  			openLOVCommon(539,'receiptMakerForm','depositBankID','','temp', '','','getBranchIdMICR','temp','depositBankAccount','depositIfscCode');
  		else
  			openLOVCommon(537,'receiptMakerForm','depositBankID','','temp', '','','getBranchIdMICR','temp','depositBankAccount','depositIfscCode');
  	}

  	function addRow(val)
	{
	 // alert(val);
	  var ptable = document.getElementById('otherGridtable');
	  var lastElement = ptable.rows.length;
	  // var index=lastElement;
	  // document.getElementById("psize").value=1;
	 
      var pcheck = document.getElementById("pcheck").value; 
	  var psize= document.getElementById("psize").value;	
	if(psize==""){
		psize=lastElement;
	}

     // alert(index);
	 var row =ptable.insertRow(lastElement);
	 row.setAttribute('className','white1' );
     row.setAttribute('class','white1' );
			
      var cellCheck = row.insertCell(0);
  	  var element = document.createElement('input');
  	  element.type = 'checkbox';
  	  element.name = 'chk';
  	  element.id = 'chk'+psize;	 
	  if(val=="Edit"){
	  var ele=document.createElement('input');
	   ele.type = 'hidden';
	   ele.name = 'docCheckId';
	   ele.id = 'docCheckId'+psize;
	  }
	  // Lov start
      var allocChargeDesSelect = row.insertCell(1);
      var newdiv = document.createElement('span');
      var basePath= document.getElementById("basePath").value;
	  var allocChargeDes = document.createElement('input');
	  var lbxAllocChargeStringArray = document.createElement('input');
	  var temp = document.createElement('input');
	  allocChargeDes.type = 'text';
	  allocChargeDes.readOnly=true;
	  allocChargeDes.name = 'allocChargeDes';
	  allocChargeDes.id = 'allocChargeDes'+psize;
	  	  
	  lbxAllocChargeStringArray.type = 'hidden';
	  lbxAllocChargeStringArray.name = 'lbxAllocChargeStringArray';
	  lbxAllocChargeStringArray.id = 'lbxAllocChargeStringArray'+psize;
	  	  
	  temp.type = 'hidden';
	  temp.name = 'temp';
	  temp.id = 'temp'+psize;
	  
	  allocChargeDes.setAttribute('className','text');
	  allocChargeDes.setAttribute('class','text');
	  lbxAllocChargeStringArray.setAttribute('className','text');
	  lbxAllocChargeStringArray.setAttribute('class','text');
	  temp.setAttribute('className','text');
	  temp.setAttribute('class','text');
	     	
	 newdiv.innerHTML ='<img  src=\''+basePath+'/images/theme1/lov.gif\'   onclick="openLOVCommon(540,\'receiptMakerForm\',\'lbxAllocChargeStringArray'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'allocChargeDes'+psize+'\')" Class="lovimg" />';
	 allocChargeDesSelect.appendChild(allocChargeDes);
	 allocChargeDesSelect.appendChild(lbxAllocChargeStringArray);
	 allocChargeDesSelect.appendChild(temp);
	 allocChargeDesSelect.appendChild(newdiv);
	 
	 
	 // Lov end
      var balAmountSelect = row.insertCell(2);
      var newdiv = document.createElement('span');
      var basePath='<%= request.getContextPath()%>';
	  var otherAddBalanceAmount = document.createElement('input');
	  otherAddBalanceAmount.type = 'text';
	  otherAddBalanceAmount.readOnly=true;
	  otherAddBalanceAmount.name = 'allocOtherBalAmountStringArray';
	  otherAddBalanceAmount.id = 'otherAddBalanceAmount'+psize;
	  otherAddBalanceAmount.value = '0.00';
	  otherAddBalanceAmount.setAttribute('className','text');
	  otherAddBalanceAmount.setAttribute('class','text');
	  balAmountSelect.appendChild(otherAddBalanceAmount);
	  balAmountSelect.appendChild(newdiv);
   		
	  var allocAmountSelect = row.insertCell(3);
      var newdiv = document.createElement('span');
      var basePath='<%= request.getContextPath()%>';
	  var otherAddAllocateAmount = document.createElement('input');
	  otherAddAllocateAmount.type = 'text';
	  otherAddAllocateAmount.name = 'allocOtherAmountStringArray';
	  otherAddAllocateAmount.id = 'otherAddAllocateAmount'+psize;
	  otherAddAllocateAmount.value = '0.00';
	  otherAddAllocateAmount.setAttribute('className','text');
	  otherAddAllocateAmount.setAttribute('class','text');
	  otherAddAllocateAmount.setAttribute('onchange','getTotalAllocatedAmt();');
	  allocAmountSelect.appendChild(otherAddAllocateAmount);
	  allocAmountSelect.appendChild(newdiv);

	  var allocTdsAllAmtSelect = row.insertCell(4);
      var newdiv = document.createElement('span');
      var basePath='<%= request.getContextPath()%>';
	  var otherAddTDSAmount = document.createElement('input');
	  otherAddTDSAmount.type = 'text';
	  otherAddTDSAmount.name = 'allocTdsOtherAllAmtStringArray';
	  otherAddTDSAmount.id = 'otherAddTDSAmount'+psize;
	  otherAddTDSAmount.value = '0.00';
	  otherAddTDSAmount.setAttribute('className','text');
	  otherAddTDSAmount.setAttribute('class','text');
	  otherAddTDSAmount.setAttribute('onchange','getTotalTDSAmt();');
	  allocTdsAllAmtSelect.appendChild(otherAddTDSAmount);
	  allocTdsAllAmtSelect.appendChild(newdiv);

	  

	cellCheck.appendChild(element);	
	if(val=="Edit"){	
	cellCheck.appendChild(ele);	
	}
	docCodeSelect.appendChild(docCode);
	mandatorySelect.appendChild(mandatory);
	originalSelect.appendChild(original);	
	expFlagSlect.appendChild( expFlag);
	StatuSelect.appendChild(Statu);
		
	
	
	  // index++;
	  pcheck++;
	  psize++;	  
	  document.getElementById("psize").value=psize;
	  document.getElementById("pcheck").value=pcheck;
		  
	  }
  	
  	$(document).ready(function() {
        $("#btn").click(function() {
            $("table[id*=otherGridtable] input[type='checkbox']:checked").each(function(i, item) {
                $(item).closest("tr").remove();
            });
        });
    });

function removeRow(val) {
      var table = document.getElementById("otherGridtable");
         var rowCount = table.rows.length;
         // alert("Row Count"+rowCount)
     
     	
		var count=0;
		
	    var chk = document.getElementsByName("chk");
        for(var i = 0; i < chk.length; i++)
         {
     		if(chk[i+1] != null && chk[i+1].checked == true) 
            {
            	 table.deleteRow(i+1);
                // rowCount--;
                count++;
             
             } 
             
             
         }
         /*for(var i=1; i<rowCount; i++) {
// alert("i: "+i);
          alert("Row Count"+rowCount);
             var row = table.rows[i];
             alert("cell "+row.cells[0].childNodes[0]);
             var chkbox = row.cells[0].childNodes[0];
         alert("chkbox.checked: "+chkbox.checked);
             if(chkbox != null && chkbox.checked == true) 
             {
             	 table.deleteRow(i);
                 // rowCount--;
                 count++;
                 i--;
              }        
             else if(document.getElementById('chk1').checked)
             {
            	 table.deleteRow(i);
                 // rowCount--;
                 count++;
                 i--;
             }	
            }   */     

      if(count==0){
           alert(val);
         return false;
         }

      }


function allocateCharge()
{
	 DisButClass.prototype.DisButMethod();
	 var basePath=document.getElementById("contextPath").value;
	if (document.getElementById("instrumentID").value!="")
	 { 
		 document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=updateAllocationChargeReceipt";
		 document.getElementById("processingImage").style.display = '';		
		 document.getElementById("receiptMakerForm").submit();
		 return true;
	 }
	else
	{		
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

}
function validateReceiptAllocationGrid()  {
	
	DisButClass.prototype.DisButMethod();
// alert("alert1 : "+alert1);
	var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
	var  tdsAmount=document.getElementById('tdsAmount').value;
		 if(tdsAmount=="")
			 tdsAmount=0;
	 else
		 tdsAmount=removeComma(document.getElementById('tdsAmount').value);
		 
    var totalAmount=receiptAmount+tdsAmount;


	var allocChargeCodeArray = document.getElementsByName("allocChargeCodeArray");
	var allocAmountStringArray = document.getElementsByName("allocAmountStringArray");
	var allocTdsAllocatedAmountStringArray = document.getElementsByName("allocTdsAllocatedAmountStringArray");
	
	var j=0;
	var sum=0;
	var allocatedSum=0;
	var allocatedAmount=0;
	var allocatedTDSAmount=0;
	var tdsSum=0;
	
	for(var j = 0; j < allocChargeCodeArray.length; j++)
    {
				
		 var allocatedAmount=allocAmountStringArray[j].value;  		    
		 if(allocatedAmount=="")
			 allocatedAmount=0;
		 else
			 allocatedAmount=removeComma(allocAmountStringArray[j].value);
		
		 sum = (sum + allocatedAmount); 
		 
		 var allocatedTDSAmount=allocTdsAllocatedAmountStringArray[j].value;  		    
		 if(allocatedTDSAmount=="")
			 allocatedTDSAmount=0;
		 else
			 allocatedTDSAmount=removeComma(allocTdsAllocatedAmountStringArray[j].value);	
		 
		 tdsSum = (tdsSum + allocatedTDSAmount);  	
		 
		if(allocatedTDSAmount > allocatedAmount)
		{  				
			alert("TDS allocated amount should not be greater than Allocated amount");  			
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
	//alert("Alloted sum: "+sum+" tdsSum: "+tdsSum);
	
	var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
	var allocOtherAmountStringArray = document.getElementsByName("allocOtherAmountStringArray");
	var allocTdsOtherAllAmtStringArray = document.getElementsByName("allocTdsOtherAllAmtStringArray");
	var j=0;
	var otherSum=0;
	var otherTdsAmount=0;
	var otherTdsSum=0;
	for(var j = 0; j < lbxAllocChargeStringArray.length; j++)
    {
		if(lbxAllocChargeStringArray[j+1]!=null && lbxAllocChargeStringArray[j+1]!='undefined')
		{
    		if(lbxAllocChargeStringArray[j].value==lbxAllocChargeStringArray[j+1].value )
    		{
    			alert("Same Charge is not allowed");
    			document.getElementById("save").removeAttribute("disabled");
    			DisButClass.prototype.EnbButMethod();
    			return false;
    		}
		}
		if(lbxAllocChargeStringArray[j].value!='' && (allocOtherAmountStringArray[j].value=='' || allocOtherAmountStringArray[j].value=='0.00'))
		{
			alert("Allocated Amount can not be zero");
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		 var otherAllocateAmount=allocOtherAmountStringArray[j].value;  		    
		 if(otherAllocateAmount=="")
			 otherAllocateAmount=0;
		 else
			 otherAllocateAmount=removeComma(allocOtherAmountStringArray[j].value);
		
		 otherSum = (otherSum + otherAllocateAmount); 
		 
		 var otherTDSAmount=allocTdsOtherAllAmtStringArray[j].value;  		    
		 if(otherTDSAmount=="")
			 otherTDSAmount=0;
		 else
			 otherTDSAmount=removeComma(allocTdsOtherAllAmtStringArray[j].value);	
		 
		 otherTdsSum = (otherTdsSum + otherTDSAmount);  	
		 
		if(otherTDSAmount > otherAllocateAmount)
		{  				
			alert("TDS allocated amount should not be greater than Allocated amount");  			
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
	
	//alert("Other Alloted sum: "+sum+"  tdsSum: "+tdsSum);
	//alert("Other Alloted otherSum: "+otherSum+" other otherTdsSum: "+otherTdsSum);
	var totalAllocatedAmount=sum + otherSum;
	//alert("totalAllocatedAmount: "+totalAllocatedAmount+" totalAmount: "+totalAmount);
	if(totalAllocatedAmount != totalAmount)
	{
		alert("Total Allocated Amount should be equal to Receipt Amount");		
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	} 
	var totalTDSAmount=tdsSum + otherTdsSum;
	//alert("totalTDSAmount: "+totalTDSAmount+" tdsAmount: "+tdsAmount);
	if(totalTDSAmount != tdsAmount)
	{
		alert("Total TDS Allocated Amount should be equal to  TDS Amount");		
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}  			
		   
	return true;  	 
}

function onUpdateReceipt(alert2,alert3)
{
	    //alert("before onUpdateReceipt: ");
	 	DisButClass.prototype.DisButMethod();  
	 
	 	var allocGridFlag=document.getElementById("allocationGridReceipt").value;
	 	
	 	if(allocGridFlag=='Y' )
		{
	 			if (document.getElementById("instrumentID").value!="")
				{ 
					var formatD=document.getElementById("formatD").value;
					var basePath=document.getElementById("contextPath").value;
					var instrumentDate = document.getElementById("instrumentDate").value;
					var receiptDate = document.getElementById("receiptDate").value;
					var currDate = document.getElementById("businessDate").value;
					var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
					var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
					var dt3=getDateObject(currDate,formatD.substring(2, 3));
					var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
					var tdsAmount = removeComma(document.getElementById('tdsAmount').value);
					var receiptNoFlag=document.getElementById("receiptNoFlag").value;
					var receiptNo=document.getElementById("receiptNo").value;
					//var receiptAmountNew=removeComma(document.getElementById('receiptAmountNew').value);
					
					var cashDepositFlag = document.getElementById("cashDepositFlag").value;
					var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
					
					var depositBankAccount	= document.getElementById("depositBankAccount").value;
					
					var depositBankID	= document.getElementById("depositBankID").value;
					var depositBranchID	= document.getElementById("depositBranchID").value;
					if(depositBankAccount==null || depositBankAccount=='undefined')
					{
						depositBankAccount='';
					}
					if(depositBankID==null || depositBankID=='undefined')
					{
						depositBankID='';
					}
					if(depositBranchID==null || depositBranchID=='undefined')
					{
						depositBranchID='';
					}
					
					var receiptMode	= document.getElementById("receiptMode").value;
							
					if(tdsAmount==""){
						 tdsAmount=0.0000
						 }
					if(receiptAmount=="0")
					{
						alert("Allocation amount must be greater than 0 ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						 return false;
					}
					var amount=(parseFloat(receiptAmount) + parseFloat(tdsAmount))	
						   
					if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
						
						if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
						{
					    	 if (dt1>dt2)
							  {
							  alert(alert2);
							  document.getElementById("save").removeAttribute("disabled");
							  DisButClass.prototype.EnbButMethod();
							  return false;
							  }
						}
					
					    if (dt2>dt3)
						  {
						  alert(alert3);
						  document.getElementById("save").removeAttribute("disabled");
						  DisButClass.prototype.EnbButMethod();
						  return false;
						  }
					    
					/*	if(receiptNoFlag=='Y' && receiptNo=='')
						{
							alert("Receipt No. is required.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled");
							return false;
								
						}*/
						
						
						if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
					  	{
								alert("Receipt No. is required");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled");
								return false;
					  			
					  	}
	
					    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
						{
					    	
					    	 if((document.getElementById("instrumentNumber").value)==""){
						    		alert("Instrument Number / Ref no is required. ");
						    		document.getElementById("save").removeAttribute("disabled");
						    		DisButClass.prototype.EnbButMethod();
						    		return false;
						    		}
					    	 if((document.getElementById("instrumentDate").value)==""){
						    		alert("Instrument date is required. ");
						    		document.getElementById("save").removeAttribute("disabled");
						    		DisButClass.prototype.EnbButMethod();
						    		return false;
						    		}
					    		   if((document.getElementById("bank").value)==""){
							    		alert("Issuing Bank is required ");
							    		document.getElementById("save").removeAttribute("disabled");
							    		DisButClass.prototype.EnbButMethod();
							    		return false;
					    		}
					    		  
					    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
						    		alert("Issuing Branch is required. ");
						    		document.getElementById("save").removeAttribute("disabled");
						    		DisButClass.prototype.EnbButMethod();
						    		return false;
					    		}*/
					    		 /* if((document.getElementById("bankAccount").value=="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
							    		alert("Please select bank Account ");
							    		document.getElementById("save").removeAttribute("disabled");
							    		DisButClass.prototype.EnbButMethod();
							    		return false;
							    		}*/
					
						}
					    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
						{
							alert(" Please Select/Reselect Deposit Bank Account. ");
							document.getElementById("receiptAmount").removeAttribute("disabled");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
					    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
						{
							alert(" Please Select/Reselect Deposit Bank Account. ");
							document.getElementById("receiptAmount").removeAttribute("disabled");
							DisButClass.prototype.EnbButMethod();
							return false;
						}

					
					    if(validateReceiptAllocationGrid())
						   {
						    	var repoFlagMarked= document.getElementById('repoFlagMarked').value;
							    if(repoFlagMarked=='A')
							    {
							    	if (confirm("Loan is marked as Repo.Do you want to proceed"))
							    	{
							    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
							    	}
							    	else
							    	{
							    		 DisButClass.prototype.EnbButMethod();
							    		 return false;
							    	}
							    }
							    else
							    {
							    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
							    }
							
							document.getElementById("processingImage").style.display = '';
							document.getElementById("receiptMakerForm").submit();
						   }
					 return true;
					}
					else
					{
						document.getElementById("save").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
		     }
			else
			{
				DisButClass.prototype.DisButMethod();        	
			   	var formatD=document.getElementById("formatD").value;
				var basePath=document.getElementById("contextPath").value;
				var instrumentDate = document.getElementById("instrumentDate").value;
				var receiptDate = document.getElementById("receiptDate").value;
				var currDate = document.getElementById("businessDate").value;
				var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
			    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
			    var dt3=getDateObject(currDate,formatD.substring(2, 3));
			    var receiptNoFlag=document.getElementById("receiptNoFlag").value;
			    var receiptNo=document.getElementById("receiptNo").value;
			    var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
			    // var
				// receiptAmountNew=removeComma(document.getElementById('receiptAmountNew').value);
			    
			    var cashDepositFlag = document.getElementById("cashDepositFlag").value;
				var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
				var depositBankAccount	= document.getElementById("depositBankAccount").value;
				var depositBankID	= document.getElementById("depositBankID").value;
				var depositBranchID	= document.getElementById("depositBranchID").value;
				if(depositBankAccount==null || depositBankAccount=='undefined')
				{
					depositBankAccount='';
				}
				if(depositBankID==null || depositBankID=='undefined')
				{
					depositBankID='';
				}
				if(depositBranchID==null || depositBranchID=='undefined')
				{
					depositBranchID='';
				}
				var receiptMode	= document.getElementById("receiptMode").value;
				if(receiptAmount=="0")
				{
		    		alert(" Allocation Amount must be greater than 0 ");
		    		document.getElementById("receiptAmount").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		 return false;
		    	}
				
				
				if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
					if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
			    	{
					 if (dt1>dt2)
					  {
					  alert(alert2);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
			    	}
				    if (dt2>dt3)
					  {
					  alert(alert3);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
					/*if(receiptNoFlag=='Y'&& receiptNo=='')
			    	{
		    			alert("Receipt No. is required");
		    			DisButClass.prototype.EnbButMethod();
		    			document.getElementById("save").removeAttribute("disabled");
		    			return false;
			    			
			    	}*/
					if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
				  	{
							alert("Receipt No. is required");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled");
							return false;
				  			
				  	}
				    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
					{
				    	    if((document.getElementById("instrumentNumber").value)==""){
				    		alert("Instrument Number / Ref no is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
			    	       if((document.getElementById("instrumentDate").value)==""){
				    		alert("Instrument date is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    		   if((document.getElementById("bank").value)==""){
				    		alert("Issuing Bank is required. ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
				    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
				    		alert("Issuing Branch is required  ");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}*/
				    		/*
							 * if((document.getElementById("bankAccount").value =="") &&
							 * ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
							 * 
							 * alert("Please select bank Account ");
							 * document.getElementById("save").removeAttribute("disabled");
							 * DisButClass.prototype.EnbButMethod(); return false; }
							 */
				    		
					}
				    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}


				    if(validateReceiptAllocationGrid())
					   {
				    	var repoFlagMarked= document.getElementById('repoFlagMarked').value;
					    if(repoFlagMarked=='A')
					    {
					    	if (confirm("Loan is marked as Repo.Do you want to proceed"))
					    	{
					    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
					    	}
					    	else
					    	{
					    		 DisButClass.prototype.EnbButMethod();
					    		 return false;
					    	}
					    }
					    else
					    {
					    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
					    }
						 
						 document.getElementById("processingImage").style.display = '';		
						 document.getElementById("receiptMakerForm").submit();
					     return true;
					   }
				}
				else
				{		
					document.getElementById("save").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			
			 }
	
		  }
	 	else 
	 	{   //  For allocGridFlag 'N'

		 	if (document.getElementById("instrumentID").value!="")
			{ 
				var formatD=document.getElementById("formatD").value;
				var basePath=document.getElementById("contextPath").value;
				var instrumentDate = document.getElementById("instrumentDate").value;
				var receiptDate = document.getElementById("receiptDate").value;
				var currDate = document.getElementById("businessDate").value;
				var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
				var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
				var dt3=getDateObject(currDate,formatD.substring(2, 3));
				var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
				var tdsAmount = removeComma(document.getElementById('tdsAmount').value);
				var receiptNoFlag=document.getElementById("receiptNoFlag").value;
				var receiptNo=document.getElementById("receiptNo").value;
				//var receiptAmountNew=removeComma(document.getElementById('receiptAmountNew').value);
				
				var cashDepositFlag = document.getElementById("cashDepositFlag").value;
				var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
				var depositBankAccount	= document.getElementById("depositBankAccount").value;
				var depositBankID	= document.getElementById("depositBankID").value;
				var depositBranchID	= document.getElementById("depositBranchID").value;
				if(depositBankAccount==null || depositBankAccount=='undefined')
				{
					depositBankAccount='';
				}
				if(depositBankID==null || depositBankID=='undefined')
				{
					depositBankID='';
				}
				if(depositBranchID==null || depositBranchID=='undefined')
				{
					depositBranchID='';
				}
				var receiptMode	= document.getElementById("receiptMode").value;
						
				if(tdsAmount==""){
					 tdsAmount=0.0000
					 }
				if(receiptAmount=="0")
				{
					alert("Allocation amount must be greater than 0 ");
					document.getElementById("receiptAmount").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					 return false;
				}
				var amount=(parseFloat(receiptAmount) + parseFloat(tdsAmount))	
					   
				if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
					
					if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
					{
				    	 if (dt1>dt2)
						  {
						  alert(alert2);
						  document.getElementById("save").removeAttribute("disabled");
						  DisButClass.prototype.EnbButMethod();
						  return false;
						  }
					}
				
				    if (dt2>dt3)
					  {
					  alert(alert3);
					  document.getElementById("save").removeAttribute("disabled");
					  DisButClass.prototype.EnbButMethod();
					  return false;
					  }
				    
					/*if(receiptNoFlag=='Y' && receiptNo=='')
					{
						alert("Receipt No. is required.");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled");
						return false;
							
					}*/
					if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
				  	{
							alert("Receipt No. is required");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled");
							return false;
				  			
				  	}
				    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
					{
				    	
				    	 if((document.getElementById("instrumentNumber").value)==""){
					    		alert("Instrument Number / Ref no is required. ");
					    		document.getElementById("save").removeAttribute("disabled");
					    		DisButClass.prototype.EnbButMethod();
					    		return false;
					    		}
				    	 if((document.getElementById("instrumentDate").value)==""){
					    		alert("Instrument date is required. ");
					    		document.getElementById("save").removeAttribute("disabled");
					    		DisButClass.prototype.EnbButMethod();
					    		return false;
					    		}
				    		   if((document.getElementById("bank").value)==""){
						    		alert("Issuing Bank is required ");
						    		document.getElementById("save").removeAttribute("disabled");
						    		DisButClass.prototype.EnbButMethod();
						    		return false;
				    		}
				    		  
				    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
					    		alert("Issuing Branch is required. ");
					    		document.getElementById("save").removeAttribute("disabled");
					    		DisButClass.prototype.EnbButMethod();
					    		return false;
				    		}*/
				    		 /* if((document.getElementById("bankAccount").value=="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
						    		alert("Please select bank Account ");
						    		document.getElementById("save").removeAttribute("disabled");
						    		DisButClass.prototype.EnbButMethod();
						    		return false;
						    		}*/
				
					}
				    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
					{
						alert(" Please Select/Reselect Deposit Bank Account. ");
						document.getElementById("receiptAmount").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
					}

//				    if (document.getElementById("allocationGridReceipt").value=='Y')	
//				      {	
//				    	 var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
//					    	var allocOtherAmountStringArray = document.getElementsByName("allocOtherAmountStringArray");
//					    	var j=0;
//					    	var otherTdsAmount=0;
//					    	for(var j = 0; j < lbxAllocChargeStringArray.length; j++)
//					        {
//					    		if(lbxAllocChargeStringArray[j+1]!=null && lbxAllocChargeStringArray[j+1]!='undefined')
//					    		{
//						    		if(lbxAllocChargeStringArray[j].value==lbxAllocChargeStringArray[j+1].value )
//						    		{
//						    			alert("Same Charge is not allowed");
//						    			document.getElementById("saveForward").removeAttribute("disabled");
//						    			DisButClass.prototype.EnbButMethod();
//						    			return false;
//						    		}
//					    		}
//					    		if(lbxAllocChargeStringArray[j].value!='' && (allocOtherAmountStringArray[j].value=='' || allocOtherAmountStringArray[j].value=='0.00'))
//					    		{
//					    			alert("Allocated Amount can not be zero");
//					    			document.getElementById("saveForward").removeAttribute("disabled");
//					    			DisButClass.prototype.EnbButMethod();
//					    			return false;
//					    		}
//					        }
//				    	if(document.getElementById("showTotalAllocatedAmount")!='undefined' && document.getElementById("showTotalAllocatedAmount")!=null)
//				    	  {
//					    	  var receiptAmount = document.getElementById("receiptAmount").value;
//					    	  if(receiptAmount=='')
//					    	  {
//					    		  receiptAmount=0; 
//					    	  }
//					    	  else
//					    	  {
//					    		  receiptAmount=removeComma(receiptAmount);
//					    	  }
//				    	  
//				    		  var showTotalAllocatedAmount = document.getElementById("showTotalAllocatedAmount").value;
//					    	  if(showTotalAllocatedAmount=='')
//					    	  {
//					    		  showTotalAllocatedAmount=0; 
//					    	  }
//					    	  else
//					    	  {
//					    		  showTotalAllocatedAmount=removeComma(showTotalAllocatedAmount);
//					    	  } 	  
//					    	  
//					    		if(parseFloat(receiptAmount)!=parseFloat(showTotalAllocatedAmount))
//					    		{
//					    			alert("Receipt Amount should be equal total Allocated Amount");
//					    			document.getElementById("saveForward").removeAttribute("disabled");
//									DisButClass.prototype.EnbButMethod();
//									return false;
//					    		}
//				    	  }
//				    	
//				    	  if(document.getElementById("showTotalTdsAmount")!='undefined' && document.getElementById("showTotalTdsAmount")!=null)
//				    	  {
//				    		
//				    		 var tdsAmount = document.getElementById("tdsAmount").value;
//					    	  if(tdsAmount=='')
//					    	  {
//					    		  tdsAmount=0; 
//					    	  }
//					    	  else
//					    	  {
//					    		  tdsAmount=removeComma(tdsAmount);
//					    	  }
//					    	  
//					    	  var showTotalTdsAmount = document.getElementById("showTotalTdsAmount").value;
//					    	  if(showTotalTdsAmount=='')
//					    	  {
//					    		  showTotalTdsAmount=0; 
//					    	  }
//					    	  else
//					    	  {
//					    		  showTotalTdsAmount=removeComma(showTotalTdsAmount);
//					    	  } 	  
//					    	  
//					    		if(parseFloat(tdsAmount)!=parseFloat(showTotalTdsAmount))
//					    		{
//					    			alert("TDS Amount should be equal total TDS Allocated Amount");
//					    			document.getElementById("saveForward").removeAttribute("disabled");
//									DisButClass.prototype.EnbButMethod();
//									return false;
//					    		}
//				    	   }
//				      }
				    var repoFlagMarked= document.getElementById('repoFlagMarked').value;
				    if(repoFlagMarked=='A')
				    {
				    	if (confirm("Loan is marked as Repo.Do you want to proceed"))
				    	{
				    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
				    	}
				    	else
				    	{
				    		 DisButClass.prototype.EnbButMethod();
				    		 return false;
				    	}
				    }
				    else
				    {
				    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
				    }
				
				document.getElementById("processingImage").style.display = '';
				document.getElementById("receiptMakerForm").submit();
				 return true;
				}
				else
				{
					document.getElementById("save").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
	     }
		else
		{
			DisButClass.prototype.DisButMethod();        	
		   	var formatD=document.getElementById("formatD").value;
			var basePath=document.getElementById("contextPath").value;
			var instrumentDate = document.getElementById("instrumentDate").value;
			var receiptDate = document.getElementById("receiptDate").value;
			var currDate = document.getElementById("businessDate").value;
			var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
		    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
		    var dt3=getDateObject(currDate,formatD.substring(2, 3));
		    var receiptNoFlag=document.getElementById("receiptNoFlag").value;
		    var receiptNo=document.getElementById("receiptNo").value;
		    var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
		    // var
			// receiptAmountNew=removeComma(document.getElementById('receiptAmountNew').value);
		    
		    var cashDepositFlag = document.getElementById("cashDepositFlag").value;
			var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
			var depositBankAccount	= document.getElementById("depositBankAccount").value;
			var depositBankID	= document.getElementById("depositBankID").value;
			var depositBranchID	= document.getElementById("depositBranchID").value;
			if(depositBankAccount==null || depositBankAccount=='undefined')
			{
				depositBankAccount='';
			}
			if(depositBankID==null || depositBankID=='undefined')
			{
				depositBankID='';
			}
			if(depositBranchID==null || depositBranchID=='undefined')
			{
				depositBranchID='';
			}
			var receiptMode	= document.getElementById("receiptMode").value;
			if(receiptAmount=="0")
			{
	    		alert(" Allocation Amount must be greater than 0 ");
	    		document.getElementById("receiptAmount").removeAttribute("disabled");
	    		DisButClass.prototype.EnbButMethod();
	    		 return false;
	    	}
			
			
			if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
				if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
		    	{
				 if (dt1>dt2)
				  {
				  alert(alert2);
				  document.getElementById("save").removeAttribute("disabled");
				  DisButClass.prototype.EnbButMethod();
				  return false;
				  }
		    	}
			    if (dt2>dt3)
				  {
				  alert(alert3);
				  document.getElementById("save").removeAttribute("disabled");
				  DisButClass.prototype.EnbButMethod();
				  return false;
				  }
				/*if(receiptNoFlag=='Y'&& receiptNo=='')
		    	{
	    			alert("Receipt No. is required");
	    			DisButClass.prototype.EnbButMethod();
	    			document.getElementById("save").removeAttribute("disabled");
	    			return false;
		    			
		    	}*/
				if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
			  	{
						alert("Receipt No. is required");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled");
						return false;
			  			
			  	}
			    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
				{
			    	    if((document.getElementById("instrumentNumber").value)==""){
			    		alert("Instrument Number / Ref no is required. ");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}
		    	       if((document.getElementById("instrumentDate").value)==""){
			    		alert("Instrument date is required. ");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}
			    		   if((document.getElementById("bank").value)==""){
			    		alert("Issuing Bank is required. ");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}
			    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
			    		alert("Issuing Branch is required  ");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}*/
			    		/*
						 * if((document.getElementById("bankAccount").value =="") &&
						 * ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
						 * 
						 * alert("Please select bank Account ");
						 * document.getElementById("save").removeAttribute("disabled");
						 * DisButClass.prototype.EnbButMethod(); return false; }
						 */
			    		
				}
			    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
				{
					alert(" Please Select/Reselect Deposit Bank Account. ");
					document.getElementById("receiptAmount").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
				{
					alert(" Please Select/Reselect Deposit Bank Account. ");
					document.getElementById("receiptAmount").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					return false;
				}

//			    if (document.getElementById("allocationGridReceipt").value=='Y')	
//			      {	 
//			    	if(document.getElementById("showTotalAllocatedAmount")!='undefined' && document.getElementById("showTotalAllocatedAmount")!=null)
//			    	  {
//				    	  var receiptAmount = document.getElementById("receiptAmount").value;
//				    	  if(receiptAmount=='')
//				    	  {
//				    		  receiptAmount=0; 
//				    	  }
//				    	  else
//				    	  {
//				    		  receiptAmount=removeComma(receiptAmount);
//				    	  }
//			    	  
//			    		  var showTotalAllocatedAmount = document.getElementById("showTotalAllocatedAmount").value;
//				    	  if(showTotalAllocatedAmount=='')
//				    	  {
//				    		  showTotalAllocatedAmount=0; 
//				    	  }
//				    	  else
//				    	  {
//				    		  showTotalAllocatedAmount=removeComma(showTotalAllocatedAmount);
//				    	  } 	  
//				    	  
//				    		if(parseFloat(receiptAmount)!=parseFloat(showTotalAllocatedAmount))
//				    		{
//				    			alert("Receipt Amount should be equal total Allocated Amount");
//				    			document.getElementById("saveForward").removeAttribute("disabled");
//								DisButClass.prototype.EnbButMethod();
//								return false;
//				    		}
//			    	  }
//			    	
//			    	  if(document.getElementById("showTotalTdsAmount")!='undefined' && document.getElementById("showTotalTdsAmount")!=null)
//			    	  {
//			    		
//			    		 var tdsAmount = document.getElementById("tdsAmount").value;
//				    	  if(tdsAmount=='')
//				    	  {
//				    		  tdsAmount=0; 
//				    	  }
//				    	  else
//				    	  {
//				    		  tdsAmount=removeComma(tdsAmount);
//				    	  }
//				    	  
//				    	  var showTotalTdsAmount = document.getElementById("showTotalTdsAmount").value;
//				    	  if(showTotalTdsAmount=='')
//				    	  {
//				    		  showTotalTdsAmount=0; 
//				    	  }
//				    	  else
//				    	  {
//				    		  showTotalTdsAmount=removeComma(showTotalTdsAmount);
//				    	  } 	  
//				    	  
//				    		if(parseFloat(tdsAmount)!=parseFloat(showTotalTdsAmount))
//				    		{
//				    			alert("TDS Amount should be equal total TDS Allocated Amount");
//				    			document.getElementById("saveForward").removeAttribute("disabled");
//								DisButClass.prototype.EnbButMethod();
//								return false;
//				    		}
//			    	   }
//			      }
			    var repoFlagMarked= document.getElementById('repoFlagMarked').value;
			    if(repoFlagMarked=='A')
			    {
			    	if (confirm("Loan is marked as Repo.Do you want to proceed"))
			    	{
			    		document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
			    	}
			    	else
			    	{
			    		 DisButClass.prototype.EnbButMethod();
			    		 return false;
			    	}
			    }
			    else
			    {
			    	document.getElementById("receiptMakerForm").action = basePath+"/receiptMakerProcessAction.do?method=saveForReceipt";
			    }
			 
			 document.getElementById("processingImage").style.display = '';		
			 document.getElementById("receiptMakerForm").submit();
		     return true;
			}
			else
			{		
				document.getElementById("save").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		
		}
	  
	 	}
}

function getTotalAllocatedAmt()
{
	//alert("getTotalAllocatedAmt()---");
	var allocAmountStringArray = document.getElementsByName("allocAmountStringArray");
	document.getElementById("canForward").value="";
	var i=0;
	var allocAmount=0;
	for(var i = 0; i < allocAmountStringArray.length; i++)
    {
		if(allocAmountStringArray[i].value!='')
		{
			
			allocAmount = parseFloat(allocAmount)+removeComma(allocAmountStringArray[i].value);
		}
        
        
    }
	
	var allocOtherAmountStringArray = document.getElementsByName("allocOtherAmountStringArray");
	var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
	var j=0;
	var otherAllocAmount=0;
	for(var j = 0; j < allocOtherAmountStringArray.length; j++)
    {
		if(lbxAllocChargeStringArray[j].value!='')
		{
			if(allocOtherAmountStringArray[j].value!='')
			{
				otherAllocAmount = parseFloat(otherAllocAmount)+removeComma(allocOtherAmountStringArray[j].value);
			}
		}
		else
		{
	
			var k=parseInt(j)+1;
			document.getElementById("otherAddAllocateAmount"+k).value='0.00';
		}
        
    }
	//alert("allocAmount+otherAllocAmount  " + allocAmount+otherAllocAmount);
	
	var totAmount=parseFloat(allocAmount)+parseFloat(otherAllocAmount);
	document.getElementById("showTotalAllocatedAmount").value=totAmount;
	
}
function getTotalTDSAmt()
{
	//alert("getTotalTDSAmt()---");
	document.getElementById("canForward").value="";
	var allocTdsAllocatedAmountStringArray = document.getElementsByName("allocTdsAllocatedAmountStringArray");
	var i=0;
	var allocTdsAmount=0;
	for(var i = 0; i < allocTdsAllocatedAmountStringArray.length; i++)
    {
		if(allocTdsAllocatedAmountStringArray[i].value!='')
		{
		  allocTdsAmount =  parseFloat(allocTdsAmount)+removeComma(allocTdsAllocatedAmountStringArray[i].value);
		}
        
    }
	
	var allocTdsOtherAllAmtStringArray = document.getElementsByName("allocTdsOtherAllAmtStringArray");
	var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
	var j=0;
	var otherTdsAmount=0;
	for(var j = 0; j < allocTdsOtherAllAmtStringArray.length; j++)
    {
		if(lbxAllocChargeStringArray[j].value!='')
		{
			if(allocTdsOtherAllAmtStringArray[j].value!='')
			{
			  otherTdsAmount =  parseFloat(otherTdsAmount)+removeComma(allocTdsOtherAllAmtStringArray[j].value);
			}
		}
		else
		{
			    var k=parseInt(j)+1;
				document.getElementById("otherAddTDSAmount"+k).value='0.00';
		}
        
    }
	
	//alert(" allocTdsAmount+otherTdsAmount  " + allocTdsAmount+otherTdsAmount);

	var totTDSAmount=parseFloat(allocTdsAmount)+parseFloat(otherTdsAmount);
	document.getElementById("showTotalTdsAmount").value=totTDSAmount;
		
}

function getCashDepositAccount()
{
	var cashDepositFlag=document.getElementById("cashDepositFlag").value;
	var receiptMode=document.getElementById('receiptMode').value;
	var basePath=document.getElementById("contextPath").value;
	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	
	if(receiptMode=='C' && cashDepositFlag=='Y')
	{
		var address = basePath+"/ajaxAction.do?method=getCashDepositAccount";
		var data = "receiptMode="+receiptMode;
		sendRequestCDAccount(address,data);
		return true;
	}
	else
	{
		document.getElementById('depositBankID').value='';
		document.getElementById('depositBankAccount').value='';
		document.getElementById('depositIfscCode').value='';
		document.getElementById('depositBranchID').value='';
		document.getElementById('depositMicr').value='';
   	 	return true;
	}
}
function sendRequestCDAccount(address, data) 
{
	
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodCDAccount(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodCDAccount(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
				document.getElementById('depositBankID').value=trim(s1[0]);
				document.getElementById('depositBankAccount').value=trim(s1[1]);
				document.getElementById('depositIfscCode').value=trim(s1[2]);
				document.getElementById('depositBranchID').value=trim(s1[3]);
				document.getElementById('depositMicr').value=trim(s1[4]);
	    }
		
	}
}

function onSaveReceiptValidate()
{
    DisButClass.prototype.DisButMethod();        	
 	var formatD=document.getElementById("formatD").value;
	var basePath=document.getElementById("contextPath").value;
	var instrumentDate = document.getElementById("instrumentDate").value;
	var receiptDate = document.getElementById("receiptDate").value;
	var currDate = document.getElementById("businessDate").value;
	var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
	var dt3=getDateObject(currDate,formatD.substring(2, 3));
	var receiptNoFlag=document.getElementById("receiptNoFlag").value;
	var receiptNo=document.getElementById("receiptNo").value;
	var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
  // var
	// receiptAmountNew=removeComma(document.getElementById('receiptAmountNew').value);
  
  var cashDepositFlag = document.getElementById("cashDepositFlag").value;
	var nonCashDepositFlag = document.getElementById('nonCashDepositFlag').value;
	var depositBankAccount	= document.getElementById("depositBankAccount").value;
	var depositBankID	= document.getElementById("depositBankID").value;
	var depositBranchID	= document.getElementById("depositBranchID").value;
	var receiptMode	= document.getElementById("receiptMode").value;
	if(depositBankAccount==null || depositBankAccount=='undefined')
	{
		depositBankAccount='';
	}
	if(depositBankID==null || depositBankID=='undefined')
	{
		depositBankID='';
	}
	if(depositBranchID==null || depositBranchID=='undefined')
	{
		depositBranchID='';
	}
	
	if(receiptAmount=="0")
	{
		alert(" Allocation Amount must be greater than 0 ");
		document.getElementById("receiptAmount").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		 return false;
	}
	
	
	if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
		if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
  	{
		 if (dt1>dt2)
		  {
		  alert("Receipt Date should be greater than Instrument Date.");
		  document.getElementById("saveForward").removeAttribute("disabled");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
  	}
	    if (dt2>dt3)
		  {
		  alert("Receipt Date should be less than and equal to Business Date");
		  document.getElementById("saveForward").removeAttribute("disabled");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
	/*	if(receiptNoFlag=='Y'&& receiptNo=='')
  	{
			alert("Receipt No. is required");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("saveForward").removeAttribute("disabled");
			return false;
  			
  	}*/
		
		if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
	  	{
				alert("Receipt No. is required");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("saveForward").removeAttribute("disabled");
				return false;
	  			
	  	}

	    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
		{
	    	    if((document.getElementById("instrumentNumber").value)==""){
	    		alert("Instrument Number / Ref no is required. ");
	    		document.getElementById("saveForward").removeAttribute("disabled");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    		}
  	       if((document.getElementById("instrumentDate").value)==""){
	    		alert("Instrument date is required. ");
	    		document.getElementById("saveForward").removeAttribute("disabled");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    		}
	    		   if((document.getElementById("bank").value)==""){
	    		alert("Issuing Bank is required. ");
	    		document.getElementById("saveForward").removeAttribute("disabled");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    		}
	    		/*if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
	    		alert("Issuing Branch is required  ");
	    		document.getElementById("save").removeAttribute("disabled");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    		}*/
	    		/*
				 * if((document.getElementById("bankAccount").value =="") &&
				 * ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
				 * 
				 * alert("Please select bank Account ");
				 * document.getElementById("save").removeAttribute("disabled");
				 * DisButClass.prototype.EnbButMethod(); return false; }
				 */
	    		
		}
	    if(cashDepositFlag=="Y" && receiptMode=='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID=='') )
		{
			alert(" Please Select/Reselect Deposit Bank Account. ");
			document.getElementById("receiptAmount").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	    if(nonCashDepositFlag=="Y" && receiptMode!='C' && (depositBankAccount=='' || depositBankID=='' || depositBranchID==''))
		{
			alert(" Please Select/Reselect Deposit Bank Account. ");
			document.getElementById("receiptAmount").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}

//	    if (document.getElementById("allocationGridReceipt").value=='Y')	
//	      {
//	    	
//	    	var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
//	    	var allocOtherAmountStringArray = document.getElementsByName("allocOtherAmountStringArray");
//	    	var j=0;
//	    	var otherTdsAmount=0;
//	    	for(var j = 0; j < lbxAllocChargeStringArray.length; j++)
//	        {
//	    		if(lbxAllocChargeStringArray[j+1]!=null && lbxAllocChargeStringArray[j+1]!='undefined')
//	    		{
//		    		if(lbxAllocChargeStringArray[j].value==lbxAllocChargeStringArray[j+1].value )
//		    		{
//		    			alert("Same Charge is not allowed");
//		    			document.getElementById("receiptAmount").removeAttribute("disabled");
//		    			DisButClass.prototype.EnbButMethod();
//		    			return false;
//		    		}
//	    		}
//	    		if(lbxAllocChargeStringArray[j].value!='' && (allocOtherAmountStringArray[j].value=='' || allocOtherAmountStringArray[j].value=='0.00'))
//	    		{
//	    			alert("Allocated Amount can not be zero");
//	    			document.getElementById("receiptAmount").removeAttribute("disabled");
//	    			DisButClass.prototype.EnbButMethod();
//	    			return false;
//	    		}
//	    		
//	            
//	        }
//	    	
//	    	if(document.getElementById("showTotalAllocatedAmount")!='undefined' && document.getElementById("showTotalAllocatedAmount")!=null)
//	    	  {
//		    	  var receiptAmount = document.getElementById("receiptAmount").value;
//		    	  if(receiptAmount=='')
//		    	  {
//		    		  receiptAmount=0; 
//		    	  }
//		    	  else
//		    	  {
//		    		  receiptAmount=removeComma(receiptAmount);
//		    	  }
//	    	  
//	    		  var showTotalAllocatedAmount = document.getElementById("showTotalAllocatedAmount").value;
//		    	  if(showTotalAllocatedAmount=='')
//		    	  {
//		    		  showTotalAllocatedAmount=0; 
//		    	  }
//		    	  else
//		    	  {
//		    		  showTotalAllocatedAmount=removeComma(showTotalAllocatedAmount);
//		    	  } 	  
//		    	  
//		    		if(parseFloat(receiptAmount)!=parseFloat(showTotalAllocatedAmount))
//		    		{
//		    			alert("Receipt Amount should be equal total Allocated Amount");
//		    			document.getElementById("saveForward").removeAttribute("disabled");
//						DisButClass.prototype.EnbButMethod();
//						return false;
//		    		}
//	    	  }
//	    	
//	    	  if(document.getElementById("showTotalTdsAmount")!='undefined' && document.getElementById("showTotalTdsAmount")!=null)
//	    	  {
//	    		
//	    		 var tdsAmount = document.getElementById("tdsAmount").value;
//		    	  if(tdsAmount=='')
//		    	  {
//		    		  tdsAmount=0; 
//		    	  }
//		    	  else
//		    	  {
//		    		  tdsAmount=removeComma(tdsAmount);
//		    	  }
//		    	  
//		    	  var showTotalTdsAmount = document.getElementById("showTotalTdsAmount").value;
//		    	  if(showTotalTdsAmount=='')
//		    	  {
//		    		  showTotalTdsAmount=0; 
//		    	  }
//		    	  else
//		    	  {
//		    		  showTotalTdsAmount=removeComma(showTotalTdsAmount);
//		    	  } 	  
//		    	  
//		    		if(parseFloat(tdsAmount)!=parseFloat(showTotalTdsAmount))
//		    		{
//		    			alert("TDS Amount should be equal total TDS Allocated Amount");
//		    			document.getElementById("saveForward").removeAttribute("disabled");
//						DisButClass.prototype.EnbButMethod();
//						return false;
//		    		}
//	    	   }
//	      }
   return true;
	}
	else
	{		
		document.getElementById("saveForward").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

}

function  onReceiptForwardValidate(){
		
		DisButClass.prototype.DisButMethod();
		var loanID=document.getElementById("lbxLoanNoHID").value;
		var instrumentID=document.getElementById("instrumentID").value;
		var basePath=document.getElementById("contextPath").value;
	    var formatD=document.getElementById("formatD").value;	 
	 	
		var instrumentDate = document.getElementById("instrumentDate").value;
		var receiptDate = document.getElementById("receiptDate").value;
		var currDate = document.getElementById("businessDate").value;
		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
	    var receiptAmount=removeComma(document.getElementById('receiptAmount').value); 
	    var  tdsAmount=document.getElementById('tdsAmount').value;
    if(tdsAmount==""){
	 tdsAmount="0";			
	 }
    else
     {
    tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
     }
// alert("receiptAmount"+receiptAmount);
// alert("tdsAmount"+tdsAmount);
var amount = (receiptAmount + tdsAmount);

 



if(validateReceiptMakerDynaValidatorForm(document.getElementById("receiptMakerForm"))){
 if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
	{
	 if (dt1>dt2)
	  {
	  alert("Receipt Date should be greater than Instrument Date");
	  DisButClass.prototype.EnbButMethod();
	  return false;
	  }
	}
	if (dt2>dt3)
	  {
	  alert("Receipt Date should be less than and equal to Business Date");
	  DisButClass.prototype.EnbButMethod();
	  return false;
	  }
	
	 // Ravi start
    var loanRecStatus="";
    if(document.getElementById("loanRecStatus").value!='')
    {
    	loanRecStatus = document.getElementById("loanRecStatus").value;
    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
    	{
    		var status = confirm("Loan is on pending stage. Do you want to continue..");
    		// alert("status :"+ status);
    		if(!status)
    		{
    			document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
    		}
    	}else if(document.getElementById("loanRecStatus").value=='C')
    	{
    		var status = confirm("Loan is close. Do you want to continue..");
    		// alert("status :"+ status);
    		if(!status)
    		{
    			document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
    		}
    	}
    	
    }
    
    // Ravi End
      if (document.getElementById("loanBranch").value=='Y')	
      {	 
    	  var status = confirm("Login branch and loan branch is not same,Do you want to Forword.");
    		if(!status)
    		{
    			document.getElementById("saveForward").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
    		}
      }
//      if (document.getElementById("allocationGridReceipt").value=='Y')	
//      {	 
//    	  var receiptAmount = document.getElementById("receiptAmount").value;
//    	  if(receiptAmount=='')
//    	  {
//    		  receiptAmount=0; 
//    	  }
//    	  else
//    	  {
//    		  receiptAmount=removeComma(receiptAmount);
//    	  }
//    	  
//    	  var showTotalAllocatedAmount = document.getElementById("showTotalAllocatedAmount").value;
//    	  if(showTotalAllocatedAmount=='')
//    	  {
//    		  showTotalAllocatedAmount=0; 
//    	  }
//    	  else
//    	  {
//    		  showTotalAllocatedAmount=removeComma(showTotalAllocatedAmount);
//    	  } 	  
//    	  
//    		if(parseFloat(receiptAmount)!=parseFloat(showTotalAllocatedAmount))
//    		{
//    			alert("Receipt Amount should be equal total Allocated Amount");
//    			document.getElementById("saveForward").removeAttribute("disabled");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//    		}
//    		
//    		 var tdsAmount = document.getElementById("tdsAmount").value;
//	    	  if(tdsAmount=='')
//	    	  {
//	    		  tdsAmount=0; 
//	    	  }
//	    	  else
//	    	  {
//	    		  tdsAmount=removeComma(tdsAmount);
//	    	  }
//	    	  
//	    	  var showTotalTdsAmount = document.getElementById("showTotalTdsAmount").value;
//	    	  if(showTotalTdsAmount=='')
//	    	  {
//	    		  showTotalTdsAmount=0; 
//	    	  }
//	    	  else
//	    	  {
//	    		  showTotalTdsAmount=removeComma(showTotalTdsAmount);
//	    	  } 	  
//	    	  
//	    		if(parseFloat(tdsAmount)!=parseFloat(showTotalTdsAmount))
//	    		{
//	    			alert("TDS Amount should be equal total TDS Allocated Amount");
//	    			document.getElementById("saveForward").removeAttribute("disabled");
//					DisButClass.prototype.EnbButMethod();
//					return false;
//	    		}
//      }
	
	return true;

  }
else{
  DisButClass.prototype.EnbButMethod();
  return false;
 
 }
}

function validateReceiptAllocationGridSaveForward() {
	
	DisButClass.prototype.DisButMethod();
// alert("alert1 : "+alert1);
	var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
	var  tdsAmount=document.getElementById('tdsAmount').value;
		 if(tdsAmount=="")
			 tdsAmount=0;
	 else
		 tdsAmount=removeComma(document.getElementById('tdsAmount').value);
		 
    var totalAmount=receiptAmount+tdsAmount;


	var allocChargeCodeArray = document.getElementsByName("allocChargeCodeArray");
	var allocAmountStringArray = document.getElementsByName("allocAmountStringArray");
	var allocTdsAllocatedAmountStringArray = document.getElementsByName("allocTdsAllocatedAmountStringArray");
	
	var j=0;
	var sum=0;
	var allocatedSum=0;
	var allocatedAmount=0;
	var allocatedTDSAmount=0;
	var tdsSum=0;
	
	for(var j = 0; j < allocChargeCodeArray.length; j++)
    {
				
		 var allocatedAmount=allocAmountStringArray[j].value;  		    
		 if(allocatedAmount=="")
			 allocatedAmount=0;
		 else
			 allocatedAmount=removeComma(allocAmountStringArray[j].value);
		
		 sum = (sum + allocatedAmount); 
		 
		 var allocatedTDSAmount=allocTdsAllocatedAmountStringArray[j].value;  		    
		 if(allocatedTDSAmount=="")
			 allocatedTDSAmount=0;
		 else
			 allocatedTDSAmount=removeComma(allocTdsAllocatedAmountStringArray[j].value);	
		 
		 tdsSum = (tdsSum + allocatedTDSAmount);  	
		 
		if(allocatedTDSAmount > allocatedAmount)
		{  				
			alert("TDS allocated amount should not be greater than Allocated amount");  			
			document.getElementById("saveForward").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
	//alert("Alloted sum: "+sum+" tdsSum: "+tdsSum);
	
	var lbxAllocChargeStringArray = document.getElementsByName("lbxAllocChargeStringArray");
	var allocOtherAmountStringArray = document.getElementsByName("allocOtherAmountStringArray");
	var allocTdsOtherAllAmtStringArray = document.getElementsByName("allocTdsOtherAllAmtStringArray");
	var j=0;
	var otherSum=0;
	var otherTdsAmount=0;
	var otherTdsSum=0;
	for(var j = 0; j < lbxAllocChargeStringArray.length; j++)
    {
		if(lbxAllocChargeStringArray[j+1]!=null && lbxAllocChargeStringArray[j+1]!='undefined')
		{
    		if(lbxAllocChargeStringArray[j].value==lbxAllocChargeStringArray[j+1].value )
    		{
    			alert("Same Charge is not allowed");
    			document.getElementById("saveForward").removeAttribute("disabled");
    			DisButClass.prototype.EnbButMethod();
    			return false;
    		}
		}
		if(lbxAllocChargeStringArray[j].value!='' && (allocOtherAmountStringArray[j].value=='' || allocOtherAmountStringArray[j].value=='0.00'))
		{
			alert("Allocated Amount can not be zero");
			document.getElementById("saveForward").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		 var otherAllocateAmount=allocOtherAmountStringArray[j].value;  		    
		 if(otherAllocateAmount=="")
			 otherAllocateAmount=0;
		 else
			 otherAllocateAmount=removeComma(allocOtherAmountStringArray[j].value);
		
		 otherSum = (otherSum + otherAllocateAmount); 
		 
		 var otherTDSAmount=allocTdsOtherAllAmtStringArray[j].value;  		    
		 if(otherTDSAmount=="")
			 otherTDSAmount=0;
		 else
			 otherTDSAmount=removeComma(allocTdsOtherAllAmtStringArray[j].value);	
		 
		 otherTdsSum = (otherTdsSum + otherTDSAmount);  	
		 
		if(otherTDSAmount > otherAllocateAmount)
		{  				
			alert("TDS allocated amount should not be greater than Allocated amount");  			
			document.getElementById("saveForward").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
    }
	
	//alert("Other Alloted sum: "+sum+"  tdsSum: "+tdsSum);
	//alert("Other Alloted otherSum: "+otherSum+" other otherTdsSum: "+otherTdsSum);
	var totalAllocatedAmount=sum + otherSum;
	//alert("totalAllocatedAmount: "+totalAllocatedAmount+" totalAmount: "+totalAmount);
	if(totalAllocatedAmount != totalAmount)
	{
		alert("Total Allocated Amount should be equal to Receipt Amount");		
		document.getElementById("saveForward").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	} 
	var totalTDSAmount=tdsSum + otherTdsSum;
	//alert("totalTDSAmount: "+totalTDSAmount+" tdsAmount: "+tdsAmount);
	if(totalTDSAmount != tdsAmount)
	{
		alert("Total TDS Allocated Amount should be equal to  TDS Amount");		
		document.getElementById("saveForward").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}  			
		   
	return true;  	 
}

function receiptSaveForward()
{
	var basePath=document.getElementById("contextPath").value;
	if(onSaveReceiptValidate() && validateReceiptAllocationGridSaveForward())
	{
		if(onReceiptForwardValidate())
		{
			var loanID=document.getElementById("lbxLoanNoHID").value;
			var instrumentID=document.getElementById("instrumentID").value;
			var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
			var  tdsAmount=document.getElementById('tdsAmount').value;
		    if(tdsAmount==""){
		    	tdsAmount="0";			
			}
		    else
		    {
		    	tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
		    }
		    
		    var amount = (receiptAmount + tdsAmount);
		    var repoFlagMarked= document.getElementById('repoFlagMarked').value;
		    if(repoFlagMarked=='A')
		    {
		    	if(confirm("Loan is marked as Repo.Do you want to proceed"))
		    	{
		    	  document.getElementById("receiptMakerForm").action=basePath+'/receiptMakerProcessAction.do?method=saveForwardReceiptTemp';
		    	  document.getElementById("processingImage").style.display = '';		
				  document.getElementById("receiptMakerForm").submit();
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
		    	document.getElementById("receiptMakerForm").action=basePath+'/receiptMakerProcessAction.do?method=saveForwardReceiptTemp';
		    	document.getElementById("processingImage").style.display = '';		
				document.getElementById("receiptMakerForm").submit();
			    return true;
		    }
			
			
		}
	}
	return true;	
}

function enableTaLoanLov()
{
	var basePath=document.getElementById("contextPath").value;
	var bpType=document.getElementById("lbxBPType").value;
	var taStatus=document.getElementById("taStatus").checked;
	var taLoanNo=document.getElementById("taLoanNo").value;
	
	if(taStatus)
	{		
		
		
		if(bpType=='MF' || bpType=='SU')
		{
			document.getElementById('showTest').style.display="";
			document.getElementById('test').style.display="none";
			document.getElementById('taloanButton').removeAttribute("disabled","disabled");	
			document.getElementById('paymentMode').setAttribute("disabled","disabled");		
			document.getElementById('instrumentNumber').setAttribute("disabled","disabled");	
			document.getElementById('instrumentDate').setAttribute("disabled","disabled");	
			document.getElementById('bankAccount').setAttribute("disabled","disabled");	
			document.getElementById('bankAccountButton').setAttribute("disabled","disabled");
			document.getElementById('paymentMode').value='';		
			document.getElementById('instrumentNumber').value='';	
			document.getElementById('instrumentDate').value='';	
			document.getElementById('bankAccount').value='';
			document.getElementById('bank').value='';
			document.getElementById('branch').value='';
			document.getElementById('micr').value='';
			document.getElementById('ifsCode').value='';
			document.getElementById('lbxbankAccountID').value='';
			document.getElementById('lbxBankID').value='';
			document.getElementById('lbxBranchID').value='';		
		}else{
			document.getElementById('showTest').style.display="none";
			document.getElementById('test').style.display="";
			document.getElementById('taloanButton').setAttribute("disabled","disabled");
			document.getElementById('paymentMode').removeAttribute("disabled","disabled");		
			document.getElementById('instrumentNumber').removeAttribute("disabled","disabled");	
			document.getElementById('instrumentDate').removeAttribute("disabled","disabled");	
			document.getElementById('bankAccount').removeAttribute("disabled","disabled");
			document.getElementById('bankAccountButton').removeAttribute("disabled","disabled");
			
		}
	}
	else
	{
		document.getElementById('showTest').style.display="none";
		document.getElementById('test').style.display="";
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';		
		document.getElementById('taloanButton').setAttribute("disabled","disabled");	
		document.getElementById('paymentMode').removeAttribute("disabled","disabled");		
		document.getElementById('instrumentNumber').removeAttribute("disabled","disabled");	
		document.getElementById('instrumentDate').removeAttribute("disabled","disabled");	
		document.getElementById('bankAccount').removeAttribute("disabled","disabled");
		document.getElementById('bankAccountButton').removeAttribute("disabled","disabled");
	}	
}
function setSuppManufIdInSessForPayment()
{
			var contextPath=document.getElementById("contextPath").value;
			var bpType=document.getElementById('lbxBPType').value;
			var bpId=document.getElementById('lbxBPNID').value;
			
			// alert("bpId::::" + bpId + "bpType"+bpType);
			var address =contextPath+"/paymentCMProcessAction.do?method=setSuppManufIdInSessForPayment&bpId="+bpId+"&bpType="+bpType;				
			 var data = "bpId="+bpId;
			 sendSupManufId(address, data);
			 return true;	 
}
function sendSupManufId(address, data) {
	
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

function receiptShowHide()
	{
		
		var statusReceipt=document.getElementById("statusReceipt").value;
				
		if(document.getElementById("statusReceipt").value=='M')
	  	{
			document.getElementById('receiptNo').removeAttribute("readOnly","true");
		}
		else
		{
			 document.getElementById('receiptNo').setAttribute("readOnly","true");
			 document.getElementById('receiptNo').value="";	
		}
		
	}
//function clearTALoanLOV()
//{
//	window.opener.document.getElementById("taLoanNo").value='';
//	window.opener.document.getElementById("lbxTaLoanNoHID").value='';
//	window.opener.document.getElementById("taCustomerName").value='';
//	window.close();
//}


function openPayeNameLov()
{
	var basePath=document.getElementById("contextPath").value;
	var bpType=document.getElementById("lbxBPType").value;
	var taStatus=document.getElementById("taStatus").checked;
	var taLoanNo=document.getElementById("taLoanNo").value;
	
	if(taStatus)
	{		
		
		
		if(bpType=='RSP')
		{
			document.getElementById('showTest').style.display="";
			document.getElementById('test').style.display="none";
			document.getElementById('taloanButton').removeAttribute("disabled","disabled");	
			document.getElementById('paymentMode').setAttribute("disabled","disabled");		
			document.getElementById('instrumentNumber').setAttribute("disabled","disabled");	
			document.getElementById('instrumentDate').setAttribute("disabled","disabled");	
			document.getElementById('bankAccount').setAttribute("disabled","disabled");	
			document.getElementById('bankAccountButton').setAttribute("disabled","disabled");
			document.getElementById('paymentMode').value='';		
			document.getElementById('instrumentNumber').value='';	
			document.getElementById('instrumentDate').value='';	
			document.getElementById('bankAccount').value='';
			document.getElementById('bank').value='';
			document.getElementById('branch').value='';
			document.getElementById('micr').value='';
			document.getElementById('ifsCode').value='';
			document.getElementById('lbxbankAccountID').value='';
			document.getElementById('lbxBankID').value='';
			document.getElementById('lbxBranchID').value='';		
		}else{
			document.getElementById('showTest').style.display="none";
			document.getElementById('test').style.display="";
			document.getElementById('taloanButton').setAttribute("disabled","disabled");
			document.getElementById('paymentMode').removeAttribute("disabled","disabled");		
			document.getElementById('instrumentNumber').removeAttribute("disabled","disabled");	
			document.getElementById('instrumentDate').removeAttribute("disabled","disabled");	
			document.getElementById('bankAccount').removeAttribute("disabled","disabled");
			document.getElementById('bankAccountButton').removeAttribute("disabled","disabled");
			
		}
	}
	else
	{
		document.getElementById('showTest').style.display="none";
		document.getElementById('test').style.display="";
		document.getElementById("taLoanNo").value='';
		document.getElementById("lbxTaLoanNoHID").value='';
		document.getElementById("taCustomerName").value='';		
		document.getElementById('taloanButton').setAttribute("disabled","disabled");	
		document.getElementById('paymentMode').removeAttribute("disabled","disabled");		
		document.getElementById('instrumentNumber').removeAttribute("disabled","disabled");	
		document.getElementById('instrumentDate').removeAttribute("disabled","disabled");	
		document.getElementById('bankAccount').removeAttribute("disabled","disabled");
		document.getElementById('bankAccountButton').removeAttribute("disabled","disabled");
	}	
}
function payeeNameRsp()
{
	var lbxpayTo=document.getElementById("lbxpayTo").value;
	if(lbxpayTo=='RSP')
	{
		openLOVCommon(655,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');
	}
	else
		
		openLOVCommon(263,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');
}
