function SearchForVerification()
{
	var txnId = document.getElementById('lbxDealNo').value;	
	var customerName=document.getElementById('customerName').value;
	/*var docEntity= document.getElementById("docEntity").value;*/
	
	DisButClass.prototype.DisButMethod();
	if (txnId == '' &&  customerName == '' ) 
		{
			alert("Please Select Atleast one record");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	else
		{
			DisButClass.prototype.DisButMethod();
			/*alert("txnId="+txnId);
			alert("txnId="+customerName);
			alert("txnId="+docEntity);*/
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("ContentVerificationActionForm").action=sourcepath+"/contentverification.do?method=searchForVerification&dealId="+txnId+"&customerName="+customerName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("ContentVerificationActionForm").submit();			
			return true;
		}

}

function linkDeal1(id,applType,cust,custRef,dealId)
{
	
	
	var appType="";
	if(applType=="APPLICANT")
		appType="PRAPPL";
	else if(applType=="CO APPLICANT")
		appType="COAPPL";
	else if(applType=="GUARANTOR")
		appType="GUARANTOR";
	
		var sourcepath=document.getElementById("contextPath").value;															
		document.getElementById("ContentVerificationActionForm").action=sourcepath+"/contentverification.do?method=CaptureVerificationData&dealId="+id+"&docEntity="+applType+"&cust="+cust+"&custRef="+custRef+"&dealId="+dealId;
		document.getElementById("ContentVerificationActionForm").submit();
		return true;
       
       }
function changeData()
{
	/*alert("In changeData");*/
	var txnId = document.getElementById('lbxDealNo').value;	
	var custRef=document.getElementById('dealNo').value;
	var docType=document.getElementById('docType').value;
	var customerName=document.getElementById('customerName').value;
	var docEntity= document.getElementById("docEntity").value;
	DisButClass.prototype.DisButMethod();
	
	if (txnId == '' &&  customerName == ''   ) 
		{
			alert("Please Select customerReference No.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}	
		else
		{
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("captureverificationdate").action=sourcepath+"/captureverificationdate.do?method=CaptureVerificationData&dealId="+txnId+"&cust="+customerName+"&docEntity="+docEntity+"&custRef="+custRef+"&docType="+docType;
			document.getElementById("captureverificationdate").submit();
			return true;
		}
	
}


function captureFromAuthor(docId,docType,dealId)
{
		
		var caseId=document.getElementById("dealId").value;
		//alert("caseId="+caseId);
		DisButClass.prototype.DisButMethod();
		var path= document.getElementById("contextPath").value;
		
		//alert("dealId===="+dealId);
		 if(docType=='BALS')
			{
				var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType="+docType+"&docId="+docId+"&caseId="+caseId;
			}
		if(docType=='P&L')
			{
				var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType=PL&docId="+docId+"&caseId="+caseId;
			}
		if(docType=='BS')
			{
				var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType="+docType+"&docId="+docId+"&caseId="+caseId;
			}

			//window.child=window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=1');
			newwindow=window.open(url, "_blank", "resizable=1, height=600, width=1100, toolbar=0, scrollbars=1");
			DisButClass.prototype.EnbButMethod();
		return true;
}

function forwardDoc(dealId)
{
	var comments = document.getElementById("comments").value;
	var decision = document.getElementById("decision").value;
	if(decision=='P')
		var stage=document.getElementById("stage").value;
	else
		var stage = '';
	var msg = '';
	if(comments=='' || decision ==''  )
		{
		if(comments=='')
			msg=msg+"* Comments is required.\n";
		if(decision=='')
			msg=msg+"* Plese select decision.\n";
		
		
					
		alert(msg);
		
		if(msg.match("Comments"))
			document.getElementById("comments").focus();
		
		if(msg.match("decision"))
			document.getElementById("decision").focus();
		
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
		}
	else if(decision!='' && decision=='P' && stage=='')
		{
		alert("*Please Select Stage For Send Back.");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	else
		{
			
			
			DisButClass.prototype.DisButMethod();
			/*alert("In Save Button");
			alert("CheckFlag==============="+checkFlag);*/
			var decison= document.getElementById("decision").value;
			/*alert("decison:"+decison);*/
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("closureContentForm").action = contextPath+"/captureverificationdate.do?method=forwardContentVerification&decision="+decison+"&dealId="+dealId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("closureContentForm").submit();
			return true;
		}
}

/*function forwardDoc()
{

	
	DisButClass.prototype.DisButMethod();
	var decision=document.getElementById("userId").value;
	var comments=document.getElementById("customerName").value;
	var dealId = document.getElementById("dealId").value;

	if(userId!='' || customerName!='' )
	{
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("captureverificationdate").action=sourcepath+"/captureverificationdate.do?method=forwardContentVerification&dealId="+dealId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("captureverificationdate").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}


*/