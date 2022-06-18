
function searchVerificationQuest(val){
	DisButClass.prototype.DisButMethod();
	
	if(document.getElementById("verificationSubType").value=='' && document.getElementById("verificationType").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("verificationQuestMasterForm").action="verificationQuestMasterBehindAction.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("verificationQuestMasterForm").submit();
	    return true;
	 }
}


function addVerificationQuest(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("verificationQuestMasterForm").action=sourcepath+"/verificationQuestProcessingMaster.do?method=openAddVerificationQuest";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("verificationQuestMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}

function saveVerificationQuestion(){
	
	DisButClass.prototype.DisButMethod();
	 var verificationType = document.getElementById("verificationType").value;
	 var verificationSubType=document.getElementById("verificationSubType").value;
	 var verificationQuest = document.getElementById("verificationQuest").value;
	  var DListValues ="";
	 var addrType=document.getElementById("addrType").value;
	 var qSequenceNo = document.getElementById("qSequenceNo").value;
	 var entityType = document.getElementById("entityType").value;
	 var entitySubType = document.getElementById("entitySubType").value;
	  var set=document.getElementById('product');
		/*alert("areaCodename-->"+areaCodename);*/
		var productCodelen = document.getElementById('product').options.length;
	 var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='';	
	
	 if(verificationType=='')
		 msg1="* Verification Type is required \n";
	 if(verificationSubType=='')
		 msg2="* Verification Sub Type is required \n";
	 if(entityType=='')
		 msg3="* Entity Type is required \n";
	 if(entitySubType=='')
		 msg4="* Entity Sub Type is required \n";
	 if(verificationQuest=='')
		 msg5="* Verification Question is required \n";
	 if(verificationSubType=='ADDRESS VERIFICATION'  && addrType=='')
		 msg6="* Address Type is required\n";
	 if(verificationSubType!='ADDRESS VERIFICATION' && addrType!='')
		 msg7="* Address Type should be blank.\n";
	 if(qSequenceNo=='')
		 msg8="* Sequence No is required \n";
 
	 if(msg1!=''||msg2!=''||msg3!='' ||msg4!='' ||msg5!='' ||msg6!='' ||msg7!='' ||msg8!='')
	 {
		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	
	  }else{
			if(productCodelen>0){
				for (var iter =0 ; iter < productCodelen; iter++)
			    {
		/*			alert("areaCodelen-->"+areaCodelen);*/
					DListValues = DListValues+set.options[iter].value+"/";
				/*	alert("DListValues"+DListValues);*/
			    } 
				}
    var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("verificationQuestMasterForm").action=sourcepath+"/verificationQuestProcessingMaster.do?method=saveVerificationQuestDetails&productId="+DListValues;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("verificationQuestMasterForm").submit();
	return true;
}	
}
/*
function getVerificationSubType()
{
	 var verificationType=document.getElementById("verificationType").value;
	 document.getElementById("contactVerificationSubType").value='';
	 document.getElementById("tecVerificationSubType").value='';
	 document.getElementById("tradeVerificationSubType").value='';
	 document.getElementById("incomeVerificationSubType").value='';
	 if(verificationType=='CONTACT VERIFICATION')
	 {
		 
		 document.getElementById("contactId").style.display='';
		 document.getElementById("tecId").style.display='none';
		 document.getElementById("tradeId").style.display='none';
		 document.getElementById("incomeId").style.display='none';
		 
	 }
	 else  if(verificationType=='TECHNICAL VERIFICATION')
	 {
		 document.getElementById("contactId").style.display='none';
		 document.getElementById("tecId").style.display='';
		 document.getElementById("tradeId").style.display='none';
		 document.getElementById("incomeId").style.display='none';
		
	 }
	 else  if(verificationType=='TRADE CHECK')
	 {
		 document.getElementById("contactId").style.display='none';
		 document.getElementById("tecId").style.display='none';
		 document.getElementById("tradeId").style.display='';
		 document.getElementById("incomeId").style.display='none';
		
	 }
	 else  if(verificationType=='INCOME VERIFICATION')
	 {
		 document.getElementById("contactId").style.display='none';
		 document.getElementById("tecId").style.display='none';
		 document.getElementById("tradeId").style.display='none';
		 document.getElementById("incomeId").style.display='';
		 
	 }
	
	 
}*/

function refreshVerificationQuestion()
{
	 var sourcepath=document.getElementById("contextPath").value;
	 location.href=sourcepath+"/verificationQuestMasterBehindAction.do";
	 
}



function chkProductType()
{
	if(document.getElementById("productType").value=='A')
		{
			document.getElementById("productButton1").style.display="none";
			document.getElementById("product").options.length = 0;
			document.getElementById("lbxproduct").value='';
			return true;
		}
	else if(document.getElementById("productType").value=='S')
	{
		document.getElementById("productButton1").style.display="";
		return true;
	}
	else 
	{
		document.getElementById("productButton1").style.display="none";
		document.getElementById("product").options.length = 0;
		document.getElementById("lbxproduct").value='';
		return true;
	}
}