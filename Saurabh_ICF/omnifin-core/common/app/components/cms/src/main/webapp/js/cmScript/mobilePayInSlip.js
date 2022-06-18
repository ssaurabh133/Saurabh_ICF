function mobilePayInSearch()
{
	var makerId=document.getElementById("lbxUserId").value;
	var fromDate=document.getElementById("payFromDate").value;
	var toDate=document.getElementById("payToDate").value;
	var contextPath =document.getElementById('contextPath').value ;
	var msg='';
	if(makerId=='' && fromDate=='' && toDate=='')
	{
		msg += '* Select at least one field for Search.\n';
	}
	if(msg !='')
	{
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			return false;	 
	}
	document.getElementById("mobilePayInSlip").action = contextPath+"/mobliePayInSlipAction.do?method=mobliePayInSlipSearchDtl";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("mobilePayInSlip").submit();
	return true;
}
function openMobileInstruementDtl(mobileId)
{
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/mobliePayInSlipAction.do?method=mobileInstruementDtl&mobileId="+mobileId;
	newWindow=window.open(url,'resolutionDtl','height=300,width=1000,top=200,left=250, scrollbars=yes ');
	if(window.focus)
	{
		newWindow.focus();
	}
	return false;
}
function openMobilePayInSlipPhotos(mobileId)
{
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/mobliePayInSlipAction.do?method=mobilePayInSlipPicture&mobileId="+mobileId;
	newWindow=window.open(url,'resolutionDtl','height=500,width=1000,top=200,left=250, scrollbars=yes');
	if(window.focus)
	{
		newWindow.focus();
	}	
	return false;
}
