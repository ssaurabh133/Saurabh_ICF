function saveAction()
	{
		var docFile = document.getElementById('docFile').value;
		var txnId = document.getElementById('dealNo').value;	
		var lbxDocId=document.getElementById('lbxDocId').value;
		var customerName=document.getElementById('customerName').value;
		var docDes=document.getElementById('docDes').value;
		
		var txnType = 'DC';
		
		var fup = document.getElementById('docFile');
		var fileName = fup.value;
		var docEntity= document.getElementById("docEntity").value;
		   var msg='';
			var sourcepath =document.getElementById('contextPath').value ;
				DisButClass.prototype.EnbButMethod();
		if (txnId == '' ||  customerName == '' || fileName == '' || docDes=='' || docEntity=='' ) {
		
			 if(txnId=='')
					msg=msg+"*ReferenceNo  is required.\n";
				
				if(fileName=='')
					msg=msg+"*File Name is required\n";
				if(customerName=='')
					msg=msg+"*customerName is required.\n";
					if(docDes=='')
					msg=msg+"*DocumentType is required.\n";
					if(docEntity=='')
						msg=msg+"*EntityType is required.\n";
					
				alert(msg); 
				if(msg.match("ReferenceNo"))
					document.getElementById("dealNo").focus();
				else if(msg.match("File Name"))
					document.getElementById("docFile").focus();
				else if(msg.match("customerName"))
					document.getElementById("customerName").focus();
					else if(msg.match("DocumentType"))
					document.getElementById("docDes").focus();
					else if(msg.match("EntityType"))
						document.getElementById("docEntity").focus();
				
				
				DisButClass.prototype.EnbButMethod();
				return false;
		}
			else{	
		
		document.getElementById("DocumentActionUploadAuthorForm").action=sourcepath+"/documentUploadauthorForOcr.do?method=uploadDocumentFile";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("DocumentActionUploadAuthorForm").submit();
		return true;
		}
	}

function downloadFile(fileName)
	{
		/*alert("download File: ");*/
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("authormakerinfo").action=sourcepath+"/authormakerinfo.do?method=downloadUnderwritingFile&fileName="+fileName;
		document.getElementById("authormakerinfo").submit();
		return true;
	}

function allChecked()
{
	
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName("chk");
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

/*function linkDeal(id,applType,custRef,fileName)
{
	
		var sourcepath=document.getElementById("contextPath").value;															
		document.getElementById("DocumentActionUploadAuthorForm").action=sourcepath+"/authorForOcr.do?method=getDocumentForOCR&dealId="+id+"&docEntity="+applType+"&custRef="+custRef+"&fileName="+fileName;
		document.getElementById("DocumentActionUploadAuthorForm").submit();
		return true;
       
       }*/




function makerSearch()
{
	
	var txnId = document.getElementById('lbxDealNo').value;	
	var customerName=document.getElementById('customerName').value;
	var docEntity= document.getElementById("docEntity").value;
	DisButClass.prototype.DisButMethod();
	if (txnId == '' &&  customerName == ''  && docEntity=='' ) {
		alert("Please Select Atleast one record");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	
	
	else
		{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("DocumentActionUploadAuthorForm").action=sourcepath+"/authorForOcr.do?method=makerSearch1&dealId="+txnId+"&customerName="+customerName+"&docEntity="+docEntity;
		document.getElementById("DocumentActionUploadAuthorForm").submit();
		return true;
		}
}


function saveClosureAuthor(decision)
{
	
	var caseId=document.getElementById("dealId").value;
	var comments = document.getElementById("comments").value;
	var decision = document.getElementById("decision").value;
	if(decision=='P')
		var stage=document.getElementById("stage").value;
	else
		var stage = '';
	var msg = '';
	if(comments=='' || decision =='' )
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
			
			
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("closureAuthorForm").action = contextPath+"/authorForOcr.do?method=saveClosureAuthor&decision="+decison+"&caseId="+caseId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("closureAuthorForm").submit();
			return true;
		}
}


	function captureFromAuthor(docType,docId)
	{
		DisButClass.prototype.DisButMethod();
		//var docId= document.getElementById("docId").value;	
		
		
			var path= document.getElementById("contextPath").value;
			 if(docType=='BALS')
				{
					var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType="+docType+"&docId="+docId;
				}
			if(docType=='P&L')
				{
					var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType=PL&docId="+docId;
				}
			if(docType=='BS')
				{
					var url=path+"/authormakerinfo.do?method=captureDataForAuthor&pageStatuss=fromLeads&docType="+docType+"&docId="+docId;
				}

				window.child=window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=1');
				DisButClass.prototype.EnbButMethod();
			return true;
	}
	
	
function saveFinancialBalanceSheet(docId,docType)
{
	/*alert("In saveFinancialBalanceSheet.........");*/
	
	
	DisButClass.prototype.DisButMethod();
	//var docId= document.getElementById("docId").value;	
	/*alert("DocId======"+docId);
	alert("docType======"+docType);*/
	
	

	
	var contextPath =document.getElementById('contextPath').value ;
	var year1 = document.getElementsByName("year1");
	var year2 = document.getElementsByName("year2");
	var year3 = document.getElementsByName("year3");
	var year4 = document.getElementsByName("year4");
	var year5 = document.getElementsByName("year5");
	var yr01 = document.getElementById("yr01");
	var yr02 = document.getElementById("yr02");	var yr03 = document.getElementById("yr03");
	var yr04 = document.getElementById("yr04");
	var yr05 = document.getElementById("yr05");
	var msg="";
	var subType=document.getElementsByName('subType') ;
	 
	
	/*var r=confirm("sum of Sub Type Asset & Liability of year "+msg+" are not equal! Do you want to save... ");*/
	/*if (r==true)
	{*/																												
		document.getElementById("balancesheetforAuthor").action =contextPath+"/balancesheetforAuthor.do?method=saveFinancialBalanceSheetDetails&source=S&docType="+docType+"&docId="+docId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("balancesheetforAuthor").submit();
	//}
}	
	
function hideAsterik(decision){
	  
	var stage=document.getElementById("stage").value;
	
	  if(decision == 'A' || decision == 'X'|| decision == '')
	  {  
		  document.getElementById("stage").setAttribute("disabled", "true");
	  }
	  else
	  {  
		  document.getElementById("stage").removeAttribute("disabled", "true");
		  

		  
	  }
		  
}
	
	