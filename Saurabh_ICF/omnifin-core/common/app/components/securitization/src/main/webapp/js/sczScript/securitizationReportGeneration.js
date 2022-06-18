function uploadCSVLoanForReportGeneration()
{
	
	if(validateDocUpload())
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("futureFlowReportGeneration").action =contextPath+"/futureFlowProcessAction.do?method=uploadCSVLoanForReportGeneration";
		document.getElementById("futureFlowReportGeneration").submit();
		return true;
	}
}

function generateReportForFutureFlow(){
	var poolID =document.getElementById("poolID").value ;
	var mm =document.getElementById("mm").value ;
	var year =document.getElementById("year").value ;
	var poolCreationDate1 =document.getElementById("poolCreationDate1").value ;
	var msg="";
	if(poolID=="" || mm=="" || year==""){
		if(poolID=='')
			msg=msg+"*Pool Name is required.\n";
		if(mm=='')
			msg=msg+"*Month is Required.\n";
		if(year=='')
			msg=msg+"*Year of Month is Required.\n";
	
		alert(msg); 
	
		if(msg.match("Pool Name"))
			document.getElementById("poolID").focus();
		else if(msg.match("Month"))
			document.getElementById("mm").focus();
		else if(msg.match("Year of Month"))
			document.getElementById("year").focus();
		return false;
	}
	//if(compareDate(poolCreationDate1,mm,year)){
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("futureFlowReportGeneration").action =contextPath+"/futureFlowProcessAction.do?method=generateReportForFutureFlow";
		document.getElementById("futureFlowReportGeneration").submit();
		return true;
	//}
}


function compareDate(date,m,y){
	var mm=parseInt(date.substring(3, 5));
	var year=parseInt(date.substring(6, 10));
	var selmm=parseInt(m);
	var selyear=parseInt(y);
	
	if(selyear>=year){
		if((selyear==year)&& (selmm>=mm)){
			return true;
		}else{
			return true;
		}
	}else{
		alert('Selected Year could not less than pool creation year');
		return false;
	}
	return false;
}


function generateReportForMonthlyCollection(){
	
	var month =document.getElementById('month11').value ;
	var poolID =document.getElementById('poolID').value ;
	var msg="";
	if(poolID=="" || month==""){
		if(poolID=='')
			msg=msg+"*Pool Name is required.\n";
		if(month=='')
			msg=msg+"*Month is Required.\n";
		
		alert(msg); 
	
		if(msg.match("Pool Name"))
			document.getElementById("poolID").focus();
		else if(msg.match("Month"))
			document.getElementById("month11").focus();
		
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/monthlyCollectionProcessAction.do?method=generateReport";
	document.getElementById("sourcingForm").submit();
	return true;
}

function generateMonthlyProcess(){
	
	var month =document.getElementById('month11').value ;
	var poolID =document.getElementById('poolID').value ;
	var msg="";
	if(poolID=="" || month==""){
		if(poolID=='')
			msg=msg+"*Pool Name is required.\n";
		if(month=='')
			msg=msg+"*Month is Required.\n";
		
		alert(msg); 
	
		if(msg.match("Pool Name"))
			document.getElementById("poolID").focus();
		else if(msg.match("Month"))
			document.getElementById("month11").focus();
		
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/monthlyProcessAction.do?method=generateMonthlyProcess";
	document.getElementById("sourcingForm").submit();
	return true;
}

function generateReportForMonthlyDistribution(){
	var reportType =document.getElementById('poolID').value ;
	if(reportType==""){
		alert('Please Select Pool ID first');
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/monthlyDistributionProcessAction.do?method=generateReport";
	document.getElementById("sourcingForm").submit();
	return true;
}

function checkValid(){
	var poolCreationDate1 =document.getElementById('poolCreationDate1').value ;
	var month =document.getElementById('month').value ;
	var msg="";
	if(poolCreationDate1=="" || month==""){
		if(poolCreationDate1=="")
			msg='*Please Select Pool ID first\n';
		if(month=="")
			msg=msg+'*Please Select Month first\n';
		alert(msg);
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/paymentAllocationProcessAction.do?method=checkValid";
	document.getElementById("sourcingForm").submit();
}

function checkValidForProcess(){
	var poolCreationDate1 =document.getElementById('poolCreationDate1').value ;
	var month =document.getElementById('month').value ;
	var msg="";
	if(poolCreationDate1=="" || month==""){
		if(poolCreationDate1=="")
			msg='*Please Select Pool ID first\n';
		if(month=="")
			msg=msg+'*Please Select Month first\n';
		alert(msg);
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/monthlyProcessAction.do?method=checkValid";
	document.getElementById("sourcingForm").submit();
}

function checkValidforMonthly(){
	var poolCreationDate1 =document.getElementById('poolCreationDate1').value ;
	var month =document.getElementById('month').value ;
	var msg="";
	if(poolCreationDate1=="" || month==""){
		if(poolCreationDate1=="")
			msg='*Please Select Pool ID first\n';
		if(month=="")
			msg=msg+'*Please Select Month first\n';
		alert(msg);
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/monthlyCollectionProcessAction.do?method=checkValid";
	document.getElementById("sourcingForm").submit();
}

function generateReportForPaymentAllocationDetails(){
	var poolID =document.getElementById('poolID').value ;
	var month =document.getElementById('month11').value ;
	if(poolID=="" || month==""){
		if(poolID=="")
			msg='*Please Select Pool ID first\n';
		if(month=="")
			msg=msg+'*Please Select Month first\n';
		alert(msg);
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/paymentAllocationProcessAction.do?method=generateReportForPaymentAllocation";
	document.getElementById("sourcingForm").submit();
	return true;
}

function generateReportForPaymentSchedule(){
	var poolID =document.getElementById('poolID').value ;
	var poolName =document.getElementById('poolName').value ;
	if(poolID=="" || poolName==""){
		alert('Please Select Pool ID first');
		return false;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("sourcingForm").action =contextPath+"/paymentScheduleProcessAction.do?method=generateReportForPaymentSchedule";
	document.getElementById("sourcingForm").submit();
	return true;
}

function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
		DisButClass.prototype.EnbButMethod();
	    return false; 
	}
	var fup = document.getElementById('docFile');
	var file_Name = fup.value;
	var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
	if(ext == "csv" || ext == "CSV")
	{
		return true;
	} 
	else
	{
		alert("Upload CSV file only.");
		fup.focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}