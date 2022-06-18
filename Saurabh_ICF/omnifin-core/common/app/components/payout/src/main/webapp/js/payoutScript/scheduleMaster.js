/*Author       :   Ritesh Srivastava
 Creation Date: 9 Aug 20012
*/
function fnSearchCodeScheduleMaster(msg){
	DisButClass.prototype.DisButMethod();
	
	if(document.getElementById("searchActivityId").value=="" 
		&& document.getElementById("searchStartDate").value==""
			&& document.getElementById("searchEndDate").value==""
				)
				
	{
     alert(msg);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("scheduleMasterSearch").action="scheduleMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("scheduleMasterSearch").submit();
    return true;
    }
}

function checkVariableDay(day,san){
	var dynaValue=san;
	var s=day;
	if(day<1){
		document.getElementById(dynaValue).value ='';
	}
	if(day>31){
		s = s.substring(0, s.length - 1);
		document.getElementById(dynaValue).value ='';
		document.getElementById(dynaValue).value =s;
	}
}
function checkVariableMonth(month,san){
	var dynaValue=san;
	var s=month;
	if(month<1){
		document.getElementById(dynaValue).value ='';
	}
	if(month>12){
		s = s.substring(0, s.length - 1);
		document.getElementById(dynaValue).value ='';
		document.getElementById(dynaValue).value =s;
	}
}
function addScheduleMaster(){
	var sourcepath=document.getElementById("path").value;
	document.getElementById("scheduleMasterSearch").action=sourcepath+"/scheduleMasterDispatch.do?method=openAddSchedule";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("scheduleMasterSearch").submit();
	return true;
//	document.getElementById("add").removeAttribute("disabled","true");
	     	DisButClass.prototype.EnbButMethod();
	return false;
}
function saveSchedule(){
	var msg1='',msg2='',msg3='',msg4='';
	var status=false;
	var startMonthParse=0;
	var endMonthParse=0;
	var startMonth=document.getElementById("startMonth").value;
	var endMonth=document.getElementById("endMonth").value;
	var startDateParse=0;
	var endDateParse=0;
	var startDate=document.getElementById("startDate").value;
	var endDate=document.getElementById("endDate").value;
	if(startMonth!=''){
		 startMonthParse=parseInt(startMonth);
	}
	if(endMonth!=''){
		endMonthParse=parseInt(endMonth);
	}
	if(document.getElementById("activityId").value==''){
		status=true;
		msg1='* Activity Code is required \n';
	} 
	if(document.getElementById("startDate").value==''){
		status=true;
		msg2='* Start Date  is required \n';
	}
	if(document.getElementById("startMonth").value==''){
		status=true;
		msg2='* Start Date  is required \n';
	}
	if(document.getElementById("endDate").value==''){
		status=true;
		msg4='* End Date is required \n';
	}
	if(document.getElementById("endMonth").value==''){
		status=true;
		msg4='* End Date is required \n';
	}
	if(!status){
		if(startMonthParse!=0 && endMonthParse!=0){
				if(startMonthParse==endMonthParse){
					if(document.getElementById("startDate").value>=document.getElementById("endDate").value){
						alert("Start Date Should Be Less Than End Date");
						return false;
					}
					
				}	
				
			/*	if(startMonthParse>endMonthParse){
					alert("Start Month Should Be Less Than End Month");
					return false;
				}*/	
		}
	}
	if(!status){
		startDateParse=parseInt(startDate);
		endDateParse=parseInt(endDate);
		if(!validateDate(startMonthParse, startDateParse) && !validateDate(endMonthParse, endDateParse))
		{
			alert("Invalid start day.\nInvalid end day.");
			return false;
		}
		if(!validateDate(startMonthParse, startDateParse))
		{
			alert("Invalid start day.\n df");
			return false;
		}
		if(!validateDate(endMonthParse, endDateParse))
		{
			alert("Invalid end day.");
			return false;
		}
	}
	
  if(status){
	  alert(msg1+msg2+msg3+msg4);
	  return false;
  }else{
	var sourcepath=document.getElementById("path").value;
	
	if(document.getElementById("modifyRecord").value=='I'){
		document.getElementById("scheduleMasterAdd").action=sourcepath+"/scheduleMasterDispatch.do?method=saveSchedule";	
	}else{
		document.getElementById("scheduleMasterAdd").action=sourcepath+"/scheduleMasterDispatch.do?method=updateSchedule";
	}
	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("scheduleMasterAdd").submit();
	return true;
  }
}

//Nishant space starts
function validateDate(month,day)
{
	if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
	{
		if(day > 31)
		{
			return false;
		}
	}
	else if(month==2)
	{
		if(day > 28)
		{
			return false;
		}
	}
	else if(month==4 || month==6 || month==9 || month==11)
	{
		if(day > 30)
		{
			return false;
		}
	}
	return true;
}
//Nishant space end