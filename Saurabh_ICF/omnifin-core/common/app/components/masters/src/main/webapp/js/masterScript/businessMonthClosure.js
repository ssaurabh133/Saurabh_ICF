function newAdd(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("businessMonthSearchForm").action=sourcepath+"//businessMonthAdd.do?method=addBusinessMonth";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("businessMonthSearchForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod()
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}
function fnSaveBusinessMonth(){
	DisButClass.prototype.DisButMethod();
	var startDate=document.getElementById('startDate');
	var endDate=document.getElementById('endDate');
	var businessMonthss=document.getElementById('businessMonthss');
	if(trim(startDate.value) == '' || trim(endDate.value) == ''|| trim(businessMonthss.value) == ''){
		var msg= '';
			if(trim(businessMonthss.value) == '')
			msg += '* Business Month is required.\n';
		  	if(trim(startDate.value) == '')
			msg += '* Start Date is required.\n';
			if(trim(endDate.value) == '')
				msg += '* End Date is required.\n';
			if(msg.match("Business"))
				businessMonthss.focus();
			else if(msg.match("Start"))
				startDate.focus();
			else if(msg.match("End"))
				endDate.focus();
			
	alert(msg);
	DisButClass.prototype.EnbButMethod();
	return false;
	
		}else{
				document.getElementById("businessMonthAddForm").action="businessMonthAdd.do?method=saveBusinessMonthClosureDetails";	
				document.getElementById("processingImage").style.display = '';
				document.getElementById("businessMonthAddForm").submit();
				return true;
			}

     }

function fnEditBusinessMonthClosure(){
	DisButClass.prototype.DisButMethod()
	var startDate=document.getElementById('startDate1');
	var endDate=document.getElementById('endDate');
	var businessMonth=document.getElementById("businessMonth").value;
	//var businessMonth=document.getElementById('businessMonth');
	if(trim(startDate.value) == '' || trim(endDate.value) == ''){
		var msg= '';
		  	if(trim(startDate.value) == '')
			msg += '* Start Date is required.\n';
			if(trim(endDate.value) == '')
				msg += '* End Date is required.\n';
			if(msg.match("Start"))
				startDate.focus();
			else if(msg.match("End")){
				endDate.focus();
			}
			
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
		}else{
			document.getElementById("businessMonthAddForm").action="businessMonthAdd.do?method=updateBusinessMonthClosure&businessMonth="+businessMonth;	
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("businessMonthAddForm").submit();
			return true;
			}

}
function fnSearchBusinessClosure(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if(document.getElementById("businessMonthSearch").value==""&& document.getElementById("businessYearSearch").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
	document.getElementById("businessMonthSearchForm").action=sourcepath+"/closureMarketingBehindAction.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("businessMonthSearchForm").submit();
	return true;
	}
}

function validateClosureDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var startDate=document.getElementById("startDate").value;
	var endDate=document.getElementById("endDate").value; 
    var dt1=getDateObject(startDate,formatD.substring(2,3));
    var dt3=getDateObject(endDate,formatD.substring(2,3));
    if(dt3<dt1)
	{
		msg="End Date Can't be Less Than Start Date.";
		document.getElementById("endDate").value='';
	}

	if(msg!='')
	{
		alert(msg);
		return false;
	}
	else
	{
		return true;
	}
}

//code added by ashish
function setStartDate()
{
	var businessMonthss=document.getElementById('businessMonthss').value;
	var businessYear=document.getElementById('businessYear').value;
	 if(businessMonthss!=''&& businessYear!='')
	 {
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/businessMonthAdd.do?method=getStartDate";
		 var data = "businessMonthss="+businessMonthss+"&businessYear="+businessYear;
		 SendSetStartDate(address, data);
		 return true;
	 }
	 else
	 {
		 alert("Business Monthss,Business Year both are Required.");
		 return false;
	 }
}
function SendSetStartDate(address, data) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		ResultStartDate(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);	
}
function ResultStartDate(request)
{
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var businessMonth=trim(str);
		if(businessMonth=='')
		{
			alert('Previous Month is Not Defined.');
			document.getElementById('startDate').value='';
		}
		else
			document.getElementById('startDate').value=businessMonth;
		window.close();
	}
}
//ashish space end