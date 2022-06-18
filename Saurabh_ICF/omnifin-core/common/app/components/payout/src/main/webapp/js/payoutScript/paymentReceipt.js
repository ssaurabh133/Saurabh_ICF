
function fnSearchCode(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchBpName").value=="" && document.getElementById("searchPaymentType").value=='')
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("paymentReceiptSearch").action="paymentReceiptBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("paymentReceiptSearch").submit();
    return true;
    }
}
function fnSearchCodeAuthor(msg){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchBpName").value=="" && document.getElementById("searchPaymentType").value=='')
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("paymentReceiptAuthorSearch").action="paymentReceiptBehindAuthor.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("paymentReceiptAuthorSearch").submit();
    return true;
    }
}

function addPaymentReceipt(){
	//alert("In newAddActionCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("paymentReceiptSearch").action=sourcepath+"/paymentReceiptDispatch.do?method=openAddPaymentReceipt";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("paymentReceiptSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}

function savePaymentReceipt(){
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='';
	var status=false;
		
	if(document.getElementById("bpName").value==''){
		status=true;
		msg1='* BP Name is required \n';
	} 
	if(document.getElementById("paymentType").value==''){
		status=true;
		msg2='* Payment Type is required \n';
	}
	if(document.getElementById("paymentMode").value==''){
		status=true;
		msg3='* Payment mode is required \n ';
	}
	if(document.getElementById("paymentDate").value==''){
		status=true;
		msg4='* Payment Date  is required \n';
	}
	if(document.getElementById("paymentAmount").value==''){
		status=true;
		msg5='* Payment Amount  is required \n';
	}
	if(document.getElementById("makerRemark").value==''){
		status=true;
		msg6='* Maker Remark  is required \n';
	}
	if(document.getElementById("paymentMode").value!="" && document.getElementById("paymentMode").value!='C'){
	if(document.getElementById("instrumentNo").value==''){
		status=true;
		msg7='* Instrument No  is required \n';
	}
	if(document.getElementById("instrumentDate").value==''){
		status=true;
		msg8='* Instrument Date  is required \n';
	}
	}
  if(status){
	  alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("paymentReceiptAdd").action=sourcepath+"/paymentReceiptDispatch.do?method=savePaymentReceiptMaker";	
	}else{
		document.getElementById("paymentReceiptAdd").action=sourcepath+"/paymentReceiptDispatch.do?method=updatePaymentReceiptMaker";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("paymentReceiptAdd").submit();
	return true;
 }
}
function forwardPaymentReceipt(){
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("modifyRecord").value=='I'){
		alert('Please Save Payment/Receipt Detail First!');
		return false;
	}
	else if(document.getElementById("allocateAmount").value==''){
		alert('Please Allocate Payment/Receipt !');
		return false;
	}else{
	document.getElementById("paymentReceiptAdd").action=sourcepath+"/paymentReceiptDispatch.do?method=forwardPaymentReceiptMaker";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("paymentReceiptAdd").submit();
	return true;
	}
}

function allocatePaymentReceipt(makOrAuthor){
	var sourcepath=document.getElementById("path").value;
	var bpId=document.getElementById("lbxBpId").value;
	var paymentAmount=document.getElementById("paymentAmount").value;
	var paymentId=document.getElementById("paymentId").value;
	var allocateAmount=document.getElementById("allocateAmount").value;
	var tds=document.getElementById("tds").value;
	var totalAllocatedAmount=document.getElementById("totalAllocatedAmount").value;
	//alert("totalAllocatedAmount:-"+totalAllocatedAmount+" allocateAmount:---"+allocateAmount+" tds:-- "+tds);
	if(document.getElementById("lbxBpId").value==''){
		alert('Please Select Bp first!');
		return false;
	}else{
		var url ;
		if(makOrAuthor=="M"){
		 url =sourcepath+"/paymentReceiptDispatch.do?method=allocatePayableReceivableBehind&bpId="+bpId+"&paymentId="+paymentId+"&paymentAmount="+paymentAmount+"&authorFlag=M"+"&allocateAmount="+allocateAmount+"&tds="+tds+"&totalAllocatedAmount="+totalAllocatedAmount;
		}else{
			 url =sourcepath+"/paymentReceiptDispatch.do?method=allocatePayableReceivableBehind&bpId="+bpId+"&paymentId="+paymentId+"&paymentAmount="+paymentAmount+"&authorFlag=A"+"&allocateAmount="+allocateAmount+"&tds="+tds+"&totalAllocatedAmount="+totalAllocatedAmount;	
		}
		window.open(url,'PayableReceivable',"height=300,width=900,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		return true;
	}
}

function saveAllocatePaymentReceipt(){
	var sourcepath=document.getElementById("path").value;
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	var chkValue='';
	var totalAllocate=0;
	var status=false;
	var msg1='',msg2='',msg3='',msg4='',msg5='';
	var paymentAmount=document.getElementById('paymentAmount').value;
	if(paymentAmount==''){
		paymentAmount='0';
	}
	if(rowCount<2){
		//alert("Please Select Checkbox to save");
		msg1="There is nothing to save \n";
		status=true;
	}else{
		for(var i=1;i<rowCount;i++){
			if(document.getElementById('chk'+i).checked==true){
				chkValue=chkValue+i+",";
			var allocateAmount=	document.getElementById('totalAllocateAmount'+i).value;
			if(allocateAmount==''){
				allocateAmount='0';
			}
			var balAmount=	document.getElementById('balAmount'+i).value;
			if(balAmount==''){
				balAmount='0';
			}
			totalAllocate=totalAllocate+parseFloat(allocateAmount);
				if(document.getElementById('allocateAmount'+i).value==''){
					msg2="Total Allocate Amount can not be null \n";
					status=true;
				}
				if(parseFloat(removeComma(balAmount))<parseFloat(removeComma(allocateAmount))){
					msg3="Total Allocate Amount can not be greater then Balance Amount \n";
					status=true;
				}
				
				
			}
		
			}	
		if(chkValue==''){
			msg4="Please Select Checkbox to save \n";
			status=true;
		}
		/*if(parseFloat(paymentAmount)<parseFloat(totalAllocate)){
			msg5="Allocate Amount can not be greater then Payment Amount \n";
			status=true;
		}*/
		document.getElementById('chkValue').value=chkValue;
	}
	
	if(status){
	alert(msg1+msg2+msg3+msg4+msg5);	
	return false;
	}else{
		var totalAllocateAmount='';
		var tds='';
		var allocateAmount='';
		var totalAllocateStr="";
		var tdsStr="";
		var allocateStr="";
		var txnId='';
		var txnIdStr='';
		var grandtotal=0;
		for(var i=1;i<rowCount;i++){
			if(document.getElementById('chk'+i).checked==true){
			 totalAllocateAmount=	document.getElementById('totalAllocateAmount'+i).value;
			 tds=	document.getElementById('tds'+i).value;
			 allocateAmount=	document.getElementById('allocateAmount'+i).value;
			 txnId=document.getElementById('txnCaseId'+i).value;
			 allocateStr=allocateStr+txnId+"@"+allocateAmount+":";
			 tdsStr=tdsStr+txnId+"@"+tds+":";
			 totalAllocateStr=totalAllocateStr+txnId+"@"+totalAllocateAmount+":";
			 txnIdStr=txnIdStr+txnId+":";
			 grandtotal=grandtotal+parseFloat(removeComma(totalAllocateAmount));
			}
		}
		window.opener.document.getElementById('allocateAmount').value=allocateStr;
		window.opener.document.getElementById('tds').value=tdsStr;
		window.opener.document.getElementById('totalAllocatedAmount').value=totalAllocateStr;
		window.opener.document.getElementById('paymentAmount').value=grandtotal;
		//alert("End");
		/*document.getElementById("allocatePaymentReceipt").action=sourcepath+"/paymentReceiptDispatch.do?method=saveAllocatePaymentReceipt";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("allocatePaymentReceipt").submit();*/
		//window.opener.document.getElementById("allocatedFlag").value="Y";
		window.close();
		return true;
	}
	
}
function savePaymentReceptAuthor(){
	var sourcepath=document.getElementById("contextPath").value;
	if(document.getElementById("authorRemark").value=='P'||document.getElementById("authorRemark").value=='X'){
		alert("*Comment is required");
	return false;	
	}else{
		document.getElementById("paymentReceiptAuthorForm").action=sourcepath+"/paymentReceiptDispatch.do?method=savePaymentReceiptAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("paymentReceiptAuthorForm").submit();
		return true;	
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
function allocateAmountAndTDS(count){
	
	var tdsRate=document.getElementById("tdsRate").value
	//alert("tdsRate:-"+tdsRate);
	var totalAllocateAmount=document.getElementById("totalAllocateAmount"+count).value
	//alert("totalAllocateAmount:-"+totalAllocateAmount);
	if(totalAllocateAmount==''){
		totalAllocateAmount=0;
	}
	if(tdsRate==''){
		tdsRate=0;
	}
	var tds=parseFloat(removeComma(totalAllocateAmount))* parseFloat(removeComma(tdsRate))/100;
	var allocateAmount=parseFloat(totalAllocateAmount)-tds;
	document.getElementById("allocateAmount"+count).value='';
	document.getElementById("tds"+count).value='';
	document.getElementById("allocateAmount"+count).value=allocateAmount;
	document.getElementById("tds"+count).value=tds;
}

