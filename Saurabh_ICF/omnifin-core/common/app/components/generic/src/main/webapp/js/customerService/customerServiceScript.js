function loanSummaryViewer()
{		
	var loanId=document.getElementById('lbxLoanNoHIDmain').value;		
	var contextPath=document.getElementById("contextPath").value;
							
	var url= contextPath+"/viewLoanSummaryCustomerService.do?method=loanSummaryViewer&loanId="+loanId;
	mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	mywindow.moveTo(800,300);
	if (window.focus) {
		mywindow.focus();
		return false;
		}
		return true;
}


function statementOfAccountAtCustomerExposure(loanid)
{
	  
	var sourcepath=document.getElementById("contextPath").value;			   
	var defaultFormate='H';//document.getElementById("defaultFormate").value;				
	if(defaultFormate=='H')
	{
		var url=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanid+"&source=NR&reportFormate=H";
		popupWin=window.open(url,'SOA Report','height=1000,width=1000,top=400,left=400, scrollbars=yes ').focus();
		if (window.focus) 
		   popupWin.focus();					
	}
	else
	{
		document.getElementById("contectRecordingForm").action=sourcepath+"/searchCMBehindAction.do?method=generateSOA&loanId="+loanid+"&source=NR&reportFormate=P";
		document.getElementById("contectRecordingForm").submit();
	}
}

function openCustomerExposureAtCMCustomerService(type)
{
	var id=document.getElementsByName("radioId");
	var loanid=document.getElementsByName("lbxLoanNoHIDmain");			  
	var txnId ='';	
			   
	for(var i=0; i< id.length ; i++) 
	{				   
	   if(id[i].checked == true)
	   {					   
		   txnId = loanid[i].value;
	   
	   }				   
	}	
	//alert("loan id: "+txnId+" txnType: "+type);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/viewCustomerExposureBehindAction.do?method=openViewCustomerExposure&txnId="+txnId+"&txnType="+type+" ";

		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		return false;
		
}
