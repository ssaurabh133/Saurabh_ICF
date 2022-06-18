function genrateBatch()
{
	DisButClass.prototype.DisButMethod();
	var paymentMode = document.getElementById("paymentMode");
	var clearingType = document.getElementById("clearingType");	
	var msg= '';
	 if(paymentMode.value==''||clearingType.value=='')
	 {		
		if(paymentMode.value == '')
			msg += '* Payment Mode is required.\n';
		if(clearingType.value=='')
			msg += '* Clearing Type is required.';
		if(msg.match("Payment"))
			paymentMode.focus();
		else if(msg.match("Clearing"))
			clearingType.focus();
		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 }
	 else
	 {
		 var contextPath= document.getElementById("contextPath").value;
		 document.getElementById("generateBatch").action=contextPath+"/generateBatchDispatchAction.do?method=generateBatch";
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("generateBatch").submit();	
	 }
	
}
function viewBatch()
{
	DisButClass.prototype.DisButMethod();
	var batchNo = document.getElementById("batchNo").value;
	var prestDate= document.getElementById("prestDate").value;
	var status = document.getElementById("status").value;	
	var msg="";
	if(batchNo=='')
		msg="*Batch No is required.\n";
	if(prestDate=='')
		msg=msg+"*Presentation Date is Required";
	if(msg!="")
	{
		alert(msg);
		if(msg.match("Batch"))
			document.getElementById("batchButton").focus();
		else if(msg.match("Presentation"))
			document.getElementById("prestDate").focus();
		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	else
	{
		 var contextPath= document.getElementById("contextPath").value;
		 document.getElementById("generateBatch").action=contextPath+"/generateBatchDispatchAction.do?method=viewBatch&status="+status;
         document.getElementById("processingImage").style.display = '';
		 document.getElementById("generateBatch").submit();	
	}	
}
function globleCheckBox()
{
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chkCases');
	var zx=0;
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
}
function finalizedBatch()
{
	 var batchNo= document.getElementById("gridBatchNo").value;
	 var contextPath= document.getElementById("contextPath").value;
	 document.getElementById("generateBatch").action=contextPath+"/generateBatchDispatchAction.do?method=finalizedBatch&batchNo="+batchNo;
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("generateBatch").submit();	
	
}
function deleatBatchRecord()
{
	DisButClass.prototype.DisButMethod();
	var ch=document.getElementsByName("chkCases");
	var status = document.getElementById("status").value;
	var batchIdList="";
	var noRecord=0;
	//alert("ch.length  :  "+ch.length);
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
		{
			batchIdList=batchIdList+",'"+ch[i].value+"'";
			noRecord++;
		}
	}
	if(noRecord==0)
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		batchIdList="("+batchIdList.substring(1)+")";
		//alert("batchIdList  :  "+batchIdList);
		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("generateBatch").action=contextPath+"/generateBatchDispatchAction.do?method=deleteBatch&batchIdList="+batchIdList+"&status="+status;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("generateBatch").submit();	
	}
}
function convertDate()
{
	document.getElementById("batchNo").value="";
	document.getElementById("lbxBatchNo").value="";
	document.getElementById("status").value="";	
	var prestDate= document.getElementById("prestDate").value;
	var result=prestDate.substring(6)+"-"+prestDate.substring(3,5)+"-"+prestDate.substring(0,2);
	document.getElementById("finalDate").value=result;
}
function changeDateFormate()
{
	var prestDate= document.getElementById("presentationDate").value;
	var result=prestDate.substring(6)+"-"+prestDate.substring(3,5)+"-"+prestDate.substring(0,2);
	document.getElementById("finalDate").value=result;
	document.getElementById('batchNo').value=""; 
	document.getElementById('lbxBatchNo').value=""; 
}
function generateProcess()
{
	DisButClass.prototype.DisButMethod();
	var presentationDate=document.getElementById("presentationDate");
	var bank=document.getElementById("bank");
	var branch=document.getElementById("branch");
	var bankAccount=document.getElementById("bankAccount");
	var batchNo=document.getElementById("batchNo");
	var msg="";
	if(batchNo.value =="")
		 msg="*Batch Number is required.";
	if(presentationDate.value =="")
		msg=msg+"*Presentation Date is required. \n";
	if(bank.value =="")
	   msg=msg+"*Deposit Bank ID is required. \n";
	if(branch.value =="")
	   msg=msg+"*Deposit Branch ID is required. \n";
	if(bankAccount.value =="")
	   msg=msg+"*Deposit Bank Account is required. \n";
	if(msg!="")
	{
		alert(msg);
		if(msg.match("Bank ID"))
			document.getElementById("bankButton").focus();
		else if(msg.match("Branch ID"))  
			document.getElementById("branchButton").focus();
		else if(msg.match("Batch Number"))
			document.getElementById("batchButton").focus();
		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	else
	{
		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("presentationBatch").action=contextPath+"/presentationBatchProcessDispatchAction.do?method=presentationProcess";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("presentationBatch").submit();	
	}
}
function generatePaySlip()
{
	var batchNo = document.getElementById("batchNo").value;
	var prestDate= document.getElementById("prestDate").value;
	var status = document.getElementById("status").value;	
	var msg="";
	if(batchNo=='')
		msg="*Batch No is required.\n";
	if(prestDate=='')
		msg=msg+"*Presentation Date is Required";
	if(msg!="")
	{
		alert(msg);
		if(msg.match("Batch"))
			document.getElementById("batchButton").focus();
		else if(msg.match("Presentation"))
			document.getElementById("prestDate").focus();
		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	else
	{
		 if(status!='Finalized')
		 {
			 alert("Please select Finalized Batch only.");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else if(status=='Finalized')
		 {
			 var contextPath=document.getElementById("contextPath").value;			   
			 var url = contextPath+"/generateBatchDispatchAction.do?method=openDepositPopup&batchNo="+batchNo+"&prestDate="+prestDate;
			 mywindow =window.open(url,'name','height=200,width=900,top=200,left=250,scrollbars=yes').focus();
			 otherWindows[curWin++]=window.open(url,'name','height=200,width=900,top=200,left=250,scrollbars=yes');
			 mywindow.moveTo(800,300);
			 if (window.focus) 
			 {
				mywindow.focus();
				return false;
			 }
			  return true;		   	
//			var contextPath= document.getElementById("contextPath").value;
//		 	document.getElementById("generateBatch").action=contextPath+"/generateBatchDispatchAction.do?method=generatePaySlip&batchNo="+batchNo;
//         	document.getElementById("generateBatch").submit();	
		 }
	}
}
function saveDepositBank()
{
	//alert("saveDepositBank");
	var bankID = document.getElementById("lbxBankID").value;
	var branchID = document.getElementById("lbxBranchID").value;
	var reportType=document.getElementById("reportType").value;
	var msg="";
	if(reportType==''){
		reportType='P';
	}
	if(bankID=='')
		msg="*Deposit Bank ID is required.\n";
	if(branchID=='')
		msg=msg+"*Deposit Branch ID is Required";
	if(msg!="")
	{
		alert(msg);
		if(msg.match("Bank"))
			document.getElementById("bankButton").focus();
		else if(msg.match("Branch"))
			document.getElementById("branchButton").focus();
		DisButClass.prototype.EnbButMethod();
 		return false;
	}
	else
	{
		var contextPath= document.getElementById("contextPath").value;
		document.getElementById("paySlip").action=contextPath+"/presentationBatchProcessDispatchAction.do?method=saveDepositBank";
        document.getElementById("paySlip").submit();	
	}
}

function calculateInstrumentNOAmount()
{
	var chkCases=document.getElementsByName("chkCases");
	var instrumentAmt=document.getElementsByName("instrumentAmt");
	
	var noRecord=0;
	var totalAmount=0;
	//alert("ch.length  :  "+ch.length);
	for(var i=0;i<chkCases.length;i++)
	{
		if(chkCases[i].checked == true)
		{
			totalAmount=totalAmount+removeComma(instrumentAmt[i].value);
			noRecord++;
		}
	} 
	
	document.getElementById("totalNoInstrument").value=noRecord;
	document.getElementById("totalInstrumentAmount").value=totalAmount;
}

function globalCalculateInstrumentNOAmount()
{
	var allchkd=document.getElementsByName("allchkd");
	var instrumentAmt=document.getElementsByName("instrumentAmt");
	
	var noRecord=0;
	var totalAmount=0;
	//alert("ch.length  :  "+ch.length);
	for(var i=0;i<allchkd.length;i++)
	{
		if(allchkd[i].checked == true)
		{
		
				totalAmount=totalAmount+removeComma(instrumentAmt[i].value);
				noRecord++;
		}
	} 
	
	document.getElementById("totalNoInstrument").value=noRecord;
	document.getElementById("totalInstrumentAmount").value=totalAmount;
	
}
function genrateMultiBatch(){
	var prestDate=document.getElementById("prestDate").value;
	var contextPath=document.getElementById("contextPath").value;	
	if(prestDate!=''){
	 var url = contextPath+"/glExcelSheetUploadCM.do?prestDate="+prestDate;
	 mywindow =openWindow(url);  
	 mywindow.moveTo(800,300);
	 if (window.focus) 
	 {
		mywindow.focus();
		return false;
	 }
	  return true;
	}else{
		alert("Please select Presentation Date.");
		return false;
	}
}

