
function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}
function uploadDoc()
	{
				
		var dealNo= document.getElementById("dealNo").value;
		var caseId= document.getElementById("lbxDealNo").value;
		var docType= document.getElementById("docType").value;
		var docEntity= document.getElementById("docEntity").value;
		var formatType= document.getElementById("formatType").value;
		var bankName= document.getElementById("bankName").value;
		var bankBranch= document.getElementById("bankBranch").value;
		var accountType= document.getElementById("accountType").value;
		var accountNo= document.getElementById("accountNo").value;
		var fromdate= document.getElementById("fromdate").value;
		var todate= document.getElementById("todate").value;
		var docFile= document.getElementById("docFile").value;
		var bankStmtDateFormat= document.getElementById("bankStmtDateFormat").value;
		var financialYear= document.getElementById("financialYear").value;
	/*	document.getElementById("docFile").onchange = function() {
		    var fileName = this.value;
		    var fileExtension = fileName.substr(fileName.length - 4);

		    console.log(fileExtension);
		    if (fileExtension != ".xls") {
		        alert("That is not .xls file!");
		    }
		}*/
		
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(fromdate,formatD.substring(2,3));
		var dt2=getDateObject(todate,formatD.substring(2,3));
		var entityCustomerId= document.getElementById("entityCustomerId").value;
		var odccLimitAmount= document.getElementById("odccLimitAmount").value;
		var dateIncrementalOrder= document.getElementById("dateIncrementalOrder").value;
		var msg='';
	 	var contextPath =document.getElementById('contextPath').value ;
		DisButClass.prototype.EnbButMethod();
		
		var ext=docFile.substring(docFile.lastIndexOf(".")+1);
		//alert(ext);
		if(ext.toUpperCase()!='XLS' && ext.toUpperCase()!='XLSX' && ext.toUpperCase()!='PDF')
			{
			alert('Only .xls and .pdf file are allowed');
			document.getElementById('docFile').focus();
			return false;
			}
		
	if(docType == 'BS')
	  {
		if(!monthValidation(dt1,dt2))
		{
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if (bankStmtDateFormat == '' || dealNo== ''  || docType == '' || docEntity == '' || bankName == '' || 
				dateIncrementalOrder=='' || (odccLimitAmount=='' && accountType=='1') || accountType=='' ||accountNo=='' || fromdate == '' || todate == '' || docFile == ''||entityCustomerId=='' || dt2<dt1)
		{
			if(bankStmtDateFormat=='')
				msg=msg+"*Please Select Bank Stmt Upload Date Format.\n";
			if(dealNo=='')
			msg=msg+"*Application Ref Number  is required.\n";
			
			if(docType=='')
				msg=msg+"*Document Type is required.\n";
			
			if(docEntity=='')
				msg=msg+"*Entity Type is required.\n";
			
			if(bankName=='')
				msg=msg+"*Bank Name is required.\n";
			
			
			
			if(accountType=='')
				msg=msg+"*A/C Type is required.\n";
			
			if(accountNo=='')
				msg=msg+"*Account No  is required.\n";
			
			if(dateIncrementalOrder=='')
				msg=msg+"* Please Select Date Incremental Order.\n";
			
			if(odccLimitAmount=='' && accountType=='1')
				msg=msg+"* OD/CC Limit Amount  is required.\n";
			
			if(fromdate=='')
				msg=msg+"*From Date (DD-MM-YYYY) is required.\n";
			
			if(todate=='')
				msg=msg+"*To Date (DD-MM-YYYY) is required.\n";
			
			if(docFile=='')
				msg=msg+"*Please select a file.\n";
			
		
			if(entityCustomerId=='')
				msg=msg+"*Please select Entity Customer.\n";
			
			if(dt2<dt1)
				msg=msg+"*To Date can't be less than From Date.\n";
			if(accountNo.length < 5 && accountNo !='')
				msg=msg+"*Please enter greater than five digit a/c no.\n"
				
						
			alert(msg);
			
			if(msg.match("Bank Stmt Upload Date"))
				document.getElementById("bankStmtDateFormat").focus();
			
			if(msg.match("Application Ref Number"))
				document.getElementById("dealNo").focus();
			
			else if(msg.match("Document Type"))
				document.getElementById("docType").focus();
			
			else if(msg.match("Entity Type"))
				document.getElementById("docEntity").focus();
			
			else if(msg.match("Bank Name"))
				document.getElementById("bankName").focus();
			
			
			
			else if(msg.match("A/C Type"))
				document.getElementById("accountType").focus();
			
			else if(msg.match("Account No"))
				document.getElementById("accountType").focus();
			
			else if(msg.match("Date Incremental Order"))
				document.getElementById("dateIncrementalOrder").focus();
			
			else if(msg.match("CC Limit Amount"))
				document.getElementById("odccLimitAmount").focus();
			
			else if(msg.match("From Date (DD-MM-YYYY)"))
				document.getElementById("fromdate").focus();
			
			else if(msg.match("To Date (DD-MM-YYYY)"))
				document.getElementById("todate").focus();
			
			else if(msg.match("Browse File"))
				document.getElementById("docFile").focus();
			
			else if(msg.match("Entity Customer"))
				document.getElementById("entityCustomerId").focus();
			
			else if(msg.match("To Date can't be less"))
				document.getElementById("formatD").focus();
			else if(msg.match("Account No"))
				document.getElementById("accountNo").focus();
				
			/*var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(fromdate,formatD.substring(2,3));
			var dt2=getDateObject(todate,formatD.substring(2,3));
			if(dt2<dt1)
			{
				alert("To Date can't be less than From Date."); 
				return false;
			}*/
			
			
			
			
			/*DisButClass.prototype.EnbButMethod();
			return false;*/
		 			
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;

		}
		
	else 
		{
			
					DisButClass.prototype.DisButMethod();
					var sourcepath =document.getElementById('contextPath').value ;
					document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=uploadDocumentFile&docEntity="+docEntity;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("documentUploadMaker").submit();
					return true;
				
		}	
	}
	
	else if(docType == 'ANU')
	{
		return uploadFundFlowFile(caseId);
	}
	else if(docType=='CO')
	{
		
		if(docEntity == '' || entityCustomerId=='' || docFile=='' || fromdate == '' || todate == '')
		{
			if(docEntity=='')
				msg=msg+"*Entity Type is required.\n";
			
			if(entityCustomerId=='')
				msg=msg+"*Please select Entity Customer.\n";
			
			if(docFile=='')
				msg=msg+"*Please select a file.\n";
			
			if(fromdate=='')
				msg=msg+"*Please select From Date.\n";
			
			if(todate=='')
				msg=msg+"*Please Select To Date.\n";
			
			alert(msg);
			
			if(msg.match("Entity Type"))
				document.getElementById("docEntity").focus();
			else if(msg.match("Entity Type"))
				document.getElementById("docEntity").focus();
			
			else if(msg.match("select a file"))
				document.getElementById("docFile").focus();
			
			else if(msg.match("From Date"))
				document.getElementById("fromdate").focus();
			else if(msg.match("to Date"))
				document.getElementById("todate").focus();
		}
			
		else
			{
			
					DisButClass.prototype.DisButMethod();
					var sourcepath =document.getElementById('contextPath').value ;
					document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=uploadDocumentFile&docEntity="+docEntity;
					document.getElementById("processingImage").style.display = '';
					document.getElementById("documentUploadMaker").submit();
					return true;
			}	
		}
	else
		{
		
		if(!dateValidation(dt1,dt2,financialYear))
		{
			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			if (dealNo== ''  || docType == '' || docEntity == '' || formatType == '' || fromdate == '' || todate == '' || docFile == ''||entityCustomerId=='' || dt2<dt1 )
				{
					if(dealNo=='')
					msg=msg+"*Application Ref Number  is required.\n";
					
					if(docType=='')
						msg=msg+"*Document Type is required.\n";
					
					if(docEntity=='')
						msg=msg+"*Entity Type is required.\n";
					
					if(formatType=='')
						msg=msg+"*Document Format is required.\n";
					
					if(fromdate=='')
						msg=msg+"*From Date (DD-MM-YYYY) is required.\n";
					
					if(todate=='')
						msg=msg+"*To Date (DD-MM-YYYY) is required.\n";
					
					if(docFile=='')
						msg=msg+"*Please select a file.\n";
					
			
					if(entityCustomerId=='')
						msg=msg+"*Please select Entity Customer.\n";
					
					if(dt2<dt1)
						msg=msg+"*To Date can't be less than From Date.\n";
					
					if(accountNo.length < 5 && accountNo !='')
						msg=msg+"*Please enter greater than five digit a/c no.\n"
						
					alert(msg);
					
					if(msg.match("Application Ref Number"))
						document.getElementById("dealNo").focus();
					
					else if(msg.match("Document Type"))
						document.getElementById("docType").focus();
					
					else if(msg.match("Entity Type"))
						document.getElementById("docEntity").focus();
					
					else if(msg.match("Document Formate"))
						document.getElementById("formatType").focus();
					
					else if(msg.match("From Date (DD-MM-YYYY)"))
						document.getElementById("fromdate").focus();
					
					else if(msg.match("To Date (DD-MM-YYYY)"))
						document.getElementById("todate").focus();
					
					else if(msg.match("Browse File"))
						document.getElementById("docFile").focus();
					
					
					else if(msg.match("Entity Customer"))
						document.getElementById("entityCustomerId").focus();
					
					else if(msg.match("To Date can't be less"))
						document.getElementById("formatD").focus();
					else if(msg.match("Account No"))
				document.getElementById("accountNo").focus();
					
					
					/*var formatD=document.getElementById("formatD").value;
					var dt1=getDateObject(fromdate,formatD.substring(2,3));
					var dt2=getDateObject(todate,formatD.substring(2,3));
					if(dt2<dt1)
					{
						alert("To Date can't be less than From Date."); 
						return false;
					}*/
					
					
					
					
					DisButClass.prototype.EnbButMethod();
					return false;
				 			
					document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
		
				}
				
			else 
				{
					
							DisButClass.prototype.DisButMethod();
							var sourcepath =document.getElementById('contextPath').value ;
							document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=uploadDocumentFile&docEntity="+docEntity;
							document.getElementById("processingImage").style.display = '';
							document.getElementById("documentUploadMaker").submit();
							return true;
						
				}
			}
	
	
	
	}

function deleteUploadDoc(chk) 
{
DisButClass.prototype.DisButMethod();

var chk = "";
var ch=document.getElementsByName("chk");
	var noRecord=0;
	var chkbox="";
	for(var i=0;i<ch.length;i++)
	{
//	alert("a:     ch[i].checked"+ch[i].checked+"b:::ch[i].value; "+ch[i].value);
		if(ch[i].checked == true)
		{	
			chk=ch[i].value;
			if(chkbox!="")
				chkbox=chkbox+","+chk;
			else
				chkbox=chk;
			noRecord=i+1;
			//break;
		}
		}
	if(noRecord==0 )
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}




	var dealId=document.getElementById("lbxDealNo").value;
		
/*	var ch=document.getElementsByName("chk");
	alert("ch............."+ch.value);    
	for(var i=0;i<ch.length;i++)
	{
		alert("ch length ============="+ch.length);
			if(ch[i].checked == true)
			{																
				
				chk=ch[i].value; 
				
				alert("chk=============="+chk);
				
			}
			else
				{
				alert("chk:::::::=="+chk);
				break;
				}
			
	
			else
			{
				alert("Please Upload All Documents First");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	}*/
		
			/*alert("dealId ="+dealId);*/
			 
			DisButClass.prototype.DisButMethod();
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=deleteUploadDocData&chk="+chk+"&dealId="+dealId;
		 	document.getElementById("processingImage").style.display = '';
			document.getElementById("documentUploadMaker").submit();
		}



function downloadFile(fileName)
{
	//alert("download File: "+removeSplChar(fileName));

	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=downloadUnderwritingFile&fileName="+fileName;
	document.getElementById("documentUploadMaker").submit();															
	return true;
}

function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName("chk");
 	var zx=0;
	// alert(c);
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

function forwardDoc(msg)
{
	
	
	var txnId = document.getElementById('lbxDealNo').value;	
	var customerName=document.getElementById('customerName').value;
	var docEntity= document.getElementById('docEntity').value;

		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=forwardDocument&dealId="+txnId+"&customerName="+customerName+"&docEntity="+docEntity;
		
		document.getElementById("processingImage").style.display = '';
		document.getElementById("documentUploadMaker").submit();
		return true;
	
	
}

function makerSearch()
{
	var txnId = document.getElementById('lbxDealNo').value;	
	//var lbxDocId=document.getElementById('lbxDocId').value;
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
		document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=makerSearch&dealId="+txnId+"&customerName="+customerName+"&docEntity="+docEntity;
		document.getElementById("documentUploadMaker").submit();
		return true;
		}
}
function linkDeal(id,applType,cust,custRef)
{
	var appType="";
	if(applType=="APPLICANT")
		appType="PRAPPL";
	else if(applType=="CO APPLICANT")
		appType="COAPPL";
	else if(applType=="GUARANTOR")
		appType="GUARANTOR";
	
		var sourcepath=document.getElementById("contextPath").value;															
		document.getElementById("documentUploadMaker").action=sourcepath+"/documentUploadSearch.do?method=getDocumentForOCR&dealId="+id+"&docEntity="+applType+"&cust="+cust+"&custRef="+custRef;
		document.getElementById("documentUploadMaker").submit();
		return true;
       
       }
function newDeal()
{


		var sourcepath=document.getElementById("contextPath").value;															
		document.getElementById("documentUploadMaker").action=sourcepath+"/documentUploadSearch.do?method=documentUpload";
		document.getElementById("documentUploadMaker").submit();
		return true;
       
       }
function changeData()
{
	var txnId = document.getElementById('lbxDealNo').value;	
	var custRef=document.getElementById('dealNo').value;
//	alert(custRef);
	var customerName=document.getElementById('customerName').value;
	var docEntity= document.getElementById("docEntity").value;
	DisButClass.prototype.DisButMethod();
	if (txnId == '' &&  customerName == ''   ) {
		alert("Please Select customerReference No.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}	else
		{

	

		

		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=getDocumentForOCR&dealId="+txnId+"&cust="+customerName+"&docEntity="+docEntity+"&custRef="+custRef;
		document.getElementById("documentUploadMaker").submit();
		return true;
		}
	
}
function docView()
{
	
	var sourcepath=document.getElementById("contextPath").value;	
	/*alert("In docView");*/
	document.getElementById("documentUploadMaker").action=sourcepath+"/documentMaker.do?method=viewAllDocument";
	document.getElementById("documentUploadMaker").submit();
	return true;
}


function checkDate()
{
	var formatD=document.getElementById("formatD").value;
	var incDate = document.getElementById("incDate").value;
	var dob = document.getElementById("dob").value;
	var doj = document.getElementById("doj").value;
	
	if(incDate!='' && dob!='' && doj!='')
	{
		date1=getDateObject(dob,formatD.substring(2, 3));
		date2=getDateObject(doj,formatD.substring(2, 3));
		date3=getDateObject(incDate,formatD.substring(2, 3));
		if(date1<=date2 && date3<=date2)
		{
			return true;
		}
		else
		{
			alert("Please Select Joining Date Equal to or Greater than Incorporation Date and Date of Birth");
			document.getElementById("doj").value='';
			document.getElementById("dob").value='';
			return false;
		}
	}
}


function isNumberKey(evt)
{
     // alert(event.keyCode);
var charCode = (evt.which) ? evt.which : event.keyCode;
if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only Numeric allowed here");
		return false;
	}
		return true;
}

function getCustomerName()
{
	var txnId = document.getElementById('lbxDealNo').value;	
	var custRef=document.getElementById('dealNo').value;
	
	var customerName=document.getElementById('customerName').value;
	var docEntity= document.getElementById("docEntity").value;
	
	var sourcepath=document.getElementById("contextPath").value;
	
	if (txnId == '') {
		alert("Please Select customerReference No.");
		
		return false;
	}
	else if(docEntity!='' && docEntity!= 'undifined')
		{
		var address = sourcepath+"/ajaxAction.do?method=getCustomerNameForEntityType";
		var data = "caseId="+txnId+"&custRef="+custRef+"&entityType="+docEntity;
		sendRequest(address,data);
		return true;
		}
	else
		{
		alert("Please Select Doc Entity.");	
	 	return false;
		}
}



function getCustomerNameForFinancialAnalysis()
{
	var caseID = document.getElementById('dealId').value;	
	var custRef=document.getElementById('dealNo').value;
//	alert(custRef);
	
	var docEntity= document.getElementById("docEntity").value;
	
	var sourcepath=document.getElementById("contextPath").value;
	
	if (caseID == '') {
		alert("Please Select customerReference No.");
		
		return false;
	}
	else if(docEntity!='' && docEntity!= 'undifined')
		{
		var address = sourcepath+"/ajaxAction.do?method=getCustomerNameForEntityType";
		var data = "caseId="+caseID+"&custRef="+custRef+"&entityType="+docEntity;
		sendRequest(address,data);
		return true;
		}
	else
		{
		document.getElementById('entityCustomerId').value='';
		alert("Please Select Doc Entity.");	
	 	return false;
		}
}





function sendRequest(address, data) 
	{

		var request = getRequestObject();
		request.onreadystatechange = function () 
			{
			resultMethod(request);
			};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}

function resultMethod(request)
	{    
		if ((request.readyState == 4) && (request.status == 200)) 
			{
				var str = request.responseText;
				//var s1 = str.split("$:");
	
				if(str.length>0)
					{
						document.getElementById('customerDiv').innerHTML = str;
		
					}
			}
	}



function hideAsterik(docType){
	  
	
	var stage=document.getElementById("formatType").value;
	
	var stage=document.getElementById("accountType").value;
	var stage=document.getElementById("bankName").value;
	var stage=document.getElementById("bankBranch").value;
	var stage=document.getElementById("accountNo").value;
	var stage=document.getElementById("bankButton").value;
	var stage=document.getElementById("bankBrnchButton").value;
	
	  if(docType == 'BS' )
	  {  
		  document.getElementById("formatType").value='';
		  document.getElementById("formatType").setAttribute("disabled", "true");
		 /* document.getElementById("accountType").removeAttribute("disabled ", "false");*/
		  document.getElementById("bankName").removeAttribute("disabled", "false");
		  document.getElementById("bankBranch").removeAttribute("disabled", "false");
		  document.getElementById("accountNo").removeAttribute("disabled", "false");
		  document.getElementById("bankButton").removeAttribute("disabled", "false");
		  document.getElementById("bankBrnchButton").removeAttribute("disabled", "false");
		  document.getElementById("accountType").removeAttribute("disabled", "false");
		  
		  document.getElementById("docEntity").value='';
		  document.getElementById("entityCustomerId").value='';
		  
		  document.getElementById("entityCustomerId").removeAttribute("disabled", "false");
		  document.getElementById("docEntity").removeAttribute("disabled", "false");
		  document.getElementById("bankStmtDateFormat").removeAttribute("disabled", "false");
		  
		  document.getElementById("odccLimitAmount").removeAttribute("disabled", "false");
		  document.getElementById("dateIncrementalOrder").removeAttribute("disabled", "false");
		  
		  
	  }
	  else if(docType == 'BALS' || docType == 'P&L')
	  {
		  
		  
		  document.getElementById("accountType").value='';
		  document.getElementById("bankName").value='';
		  document.getElementById("bankBranch").value='';
		  document.getElementById("accountNo").value='';
		  document.getElementById("bankButton").value='';
		  document.getElementById("bankBrnchButton").value='';
		  document.getElementById("odccLimitAmount").value='';
		  document.getElementById("dateIncrementalOrder").value='';
		 
		  document.getElementById("bankName").setAttribute("disabled", "true");
		  document.getElementById("bankBranch").setAttribute("disabled", "true");
		  document.getElementById("accountNo").setAttribute("disabled", "true");
		  document.getElementById("bankButton").setAttribute("disabled", "true");
		  document.getElementById("bankBrnchButton").setAttribute("disabled", "true");
		  document.getElementById("accountType").setAttribute("disabled", "true");
		  
		  document.getElementById("formatType").removeAttribute("disabled", "false");
		  
		  document.getElementById("docEntity").value='';
		  document.getElementById("entityCustomerId").value='';
		  
		  document.getElementById("entityCustomerId").removeAttribute("disabled", "false");
		  document.getElementById("docEntity").removeAttribute("disabled", "false");
		  document.getElementById("bankStmtDateFormat").setAttribute("disabled", "true");
		  
		  document.getElementById("odccLimitAmount").setAttribute("disabled", "true");
		  document.getElementById("dateIncrementalOrder").setAttribute("disabled", "true");
	  }
	  else if (docType == 'CO'){
		
		  document.getElementById("odccLimitAmount").value='';
		  document.getElementById("dateIncrementalOrder").value='';
		  document.getElementById("accountType").value='';
		  document.getElementById("formatType").value='';
		  
		  document.getElementById("entityCustomerId").removeAttribute("disabled", "true");
		  document.getElementById("docEntity").removeAttribute("disabled", "true");
		  document.getElementById("formatType").setAttribute("disabled", "true");
		  document.getElementById("bankName").setAttribute("disabled", "true");
		  document.getElementById("bankBranch").setAttribute("disabled", "true");
		  document.getElementById("accountNo").setAttribute("disabled", "true");
		  document.getElementById("bankButton").setAttribute("disabled", "true");
		  document.getElementById("bankBrnchButton").setAttribute("disabled", "true");
		  document.getElementById("accountType").setAttribute("disabled", "true");
		  
		  
		  document.getElementById("bankStmtDateFormat").setAttribute("disabled", "true");
		  document.getElementById("odccLimitAmount").setAttribute("disabled", "true");
		  document.getElementById("dateIncrementalOrder").setAttribute("disabled", "true");
	  }
	  else if(docType == 'ANU')
		  {
		  document.getElementById("accountType").value='';
		  document.getElementById("bankName").value='';
		  document.getElementById("bankBranch").value='';
		  document.getElementById("accountNo").value='';
		  document.getElementById("bankButton").value='';
		  document.getElementById("bankBrnchButton").value='';
		  document.getElementById("formatType").value='';
		  document.getElementById("docEntity").value='';
		  document.getElementById("entityCustomerId").value='';
		  document.getElementById("odccLimitAmount").value='';
		  document.getElementById("dateIncrementalOrder").value='';
		  
		  document.getElementById("entityCustomerId").setAttribute("disabled", "true");
		  document.getElementById("docEntity").setAttribute("disabled", "true");
		  document.getElementById("formatType").setAttribute("disabled", "true");
		  document.getElementById("bankName").setAttribute("disabled", "true");
		  document.getElementById("bankBranch").setAttribute("disabled", "true");
		  document.getElementById("accountNo").setAttribute("disabled", "true");
		  document.getElementById("bankButton").setAttribute("disabled", "true");
		  document.getElementById("bankBrnchButton").setAttribute("disabled", "true");
		  document.getElementById("accountType").setAttribute("disabled", "true");
		  
		  document.getElementById("formatType").setAttribute("disabled", "true");
		  document.getElementById("bankStmtDateFormat").setAttribute("disabled", "true");
		  document.getElementById("odccLimitAmount").setAttribute("disabled", "true");
		  document.getElementById("dateIncrementalOrder").setAttribute("disabled", "true");
		  }
	  
}
//Query Initiation
function queryInitiation()
{
	var lbxDealNo=document.getElementById('lbxDealNo');
	var contextPath=document.getElementById("contextPath").value;
	var url=contextPath+"/queryBehind.do?method=showQueryDataFirst&dealId="+lbxDealNo;
	popupWin=window.open(url,'QueryInitiation','height=1200,width=1000,top=400,left=400, scrollbars=yes ').focus();
	if (window.focus) 
	   popupWin.focus();
	
}
function uploadFundFlowFile(caseId) {
	
	var contextPath = document.getElementById('contextPath').value;
	var fileName = document.getElementById('docFile').value;
	var ext=fileName.substring(fileName.lastIndexOf("xls"));
	if(ext.toUpperCase()=='XLS' || ext.toUpperCase()=='XLSX')
	{
		
		document.getElementById('documentUploadMaker').action = contextPath
			+ "/uploadBankAccountNumber.do?method=uploadBankAccount&caseId="+caseId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById('documentUploadMaker').submit();
	}
	else
	{
		alert('Please Select a .xls file');
		document.getElementById('docFile').focus();
		return false;
	}
	
	
}
function generateAccountUploadFormat() {
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById('documentUploadMaker').action = contextPath
			+ "/generateAccountUploadFtmt.do?method=generateAccountUploadFormat";
	document.getElementById('documentUploadMaker').submit();
	

}

function odocChange()
{
	var accountType = document.getElementById('accountType').value;
	if(accountType=='1')
		{
		  document.getElementById("odccLimitAmount").removeAttribute("disabled", "true");
		}
	else
		{
		 document.getElementById('odccLimitAmount').value='';
		 document.getElementById("odccLimitAmount").setAttribute("disabled", "true");
		}
}
function monthValidation(fromDate, toDate)
{
	 var fromMonth = fromDate.getMonth();
	 var toMonth = toDate.getMonth();
	 
	 var fromYear = fromDate.getFullYear();
	 var toYear = toDate.getFullYear();
	 var flag = false;
	 if(fromYear==toYear)
		 {
			 if((toMonth-fromMonth)<6)
				 {
				flag =  confirm("Are you sure to continue without six month banking upload file ");
				 return flag;
			 }
		 }
	 else
		 {
		 if(((11-fromMonth)+toMonth)<6)
			 {
			flag =  confirm("Are you sure to continue without six month banking upload file ");
			 return flag;
			 }
		 }
	
	
	 return true;
		 
}
