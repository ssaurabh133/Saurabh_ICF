function saveNotepadData(alert1)
{
	
	DisButClass.prototype.DisButMethod();
	if(cpNoteCodeValidate()){
		var formatD=document.getElementById("formatD").value;
		var businessDate =document.getElementById('businessdate').value;
		var meetingDate =document.getElementById('date').value;
		var meetDate=getDateObject(meetingDate,formatD.substring(2, 3));
		var bDate=getDateObject(businessDate,formatD.substring(2, 3));
		//alert("meetDate "+meetDate+" \n bDate "+bDate);
		if(meetDate>bDate)
		{
			alert("Note Date cannot be greater than Business Date");
			document.getElementById("date").value='';
			document.getElementById("date").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(document.getElementById("followUp").value=='N')
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("notepadForm").action = contextPath+"/notepad.do?method=saveNotepadData&status=np";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("notepadForm").submit();
		}
		else if(document.getElementById("followUp").value=='Y')
		{
			if(cpNotepadValidate())
			{
				var formatD=document.getElementById("formatD").value;
				var meetingDate = document.getElementById("date").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(meetingDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				
				if(dt2<bDate)
				{
					alert("Follow Up Date cannot be less than Business Date");
					document.getElementById("followupDate").value='';
					document.getElementById("followupDate").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				
				if (dt2<dt1)
				{
					alert(alert1);
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				var contextPath =document.getElementById('contextPath').value ;
				document.getElementById("notepadForm").action = contextPath+"/notepad.do?method=saveNotepadData&status=np";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("notepadForm").submit();
			}
		}
		return true;
	}else {
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
		
}

function cpNotepadValidate()
{
		var alphaExp = /^[a-zA-Z]+$/;
		var msg1='',msg3='',msg4='',msg5='';
		
		if((document.getElementById("followupDate").value)=='')
		{
			msg1="* Follow Up Date is Required \n";
		}
		if((document.getElementById("followUpPerson").value)=='')
		{
			 msg3="* Follow Up Person is Required\n";
		}
		if((document.getElementById("followUpLocation").value)=='')
		{
			 msg4="* Follow Up Location is Required\n";
		}
		if((document.getElementById("followupRemarks").value)=='')
		{
			 msg5="* Follow Up Remarks is Required\n";
		}
		
		if(msg1!=''||msg3!=''||msg4!=''||msg5!='')
		{
			alert(msg1+msg3+msg4+msg5);
		}
		
		if(document.getElementById("followupDate").value=="")
		{
			document.getElementById("followupDate").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
			
		if(document.getElementById("followUpPerson").value =="")
		{
			document.getElementById("followUpPerson").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(document.getElementById("followUpLocation").value =="")
		{
			document.getElementById("followUpLocation").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}

		
		if(document.getElementById("followupRemarks").value =="")
		{
			document.getElementById("followupRemarks").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		return true;
}




function cpNoteCodeValidate(){
	
	 var noteCode = document.getElementById("noteCode");
	 var date = document.getElementById("date");
	 var meetingTime = document.getElementById("meetingTime");
	 var personMet = document.getElementById("personMet");
	 var meetingLocation = document.getElementById("meetingLocation");
	 var meetingRemarks = document.getElementById("meetingRemarks");
	 var followUp = document.getElementById("followUp");
	 
	 	var a = "";
	 	if(noteCode.value  == "" || date.value == "" || meetingRemarks.value  == "" || followUp.value  == "" ){
	 		if(noteCode.value  == ""){
				a = "* Note Code is required \n";
			}
			if(date.value == ""){
				a += "* Note Date is required \n";
			}
			if(meetingRemarks.value == ""){
				a +="* Notes are required \n";
			}
			if(followUp.value == ""){
				a +="* Follow up is required \n";
			}
			
			
			
			if(document.getElementById("followUp").value=='Y' || document.getElementById("followUp").value==''){
			
//				var msg1='',msg3='',msg4='',msg5='';
				
				if((document.getElementById("followupDate").value)=='')
				{
					a +="* Follow Up Date is Required \n";
				}
				if((document.getElementById("followUpPerson").value)=='')
				{
					a +="* Follow Up Person is Required\n";
				}
				if((document.getElementById("followUpLocation").value)=='')
				{
					a +="* Follow Up Location is Required\n";
				}
				if((document.getElementById("followupRemarks").value)=='')
				{
					a +="* Follow Up Remarks is Required\n";
				}
			
			}
			
			
			
			if(noteCode.value==""){
			  	noteCode.focus();
			}else if(date.value==""){
				date.focus();
			}else if(meetingRemarks.value==""){
				meetingRemarks.focus();
			}else if(followUp.value==""){
				followUp.focus();
			}
			if(document.getElementById("followUp").value=='Y' || document.getElementById("followUp").value==''){
				if(noteCode.value==""){
				  	noteCode.focus();
				}else if(date.value==""){
					date.focus();
				}else if(meetingRemarks.value==""){
					meetingRemarks.focus();
				}else if(followUp.value==""){
					followUp.focus();
				}
				else if(document.getElementById("followupDate").value==""){
					document.getElementById("followupDate").focus();
				}else if(document.getElementById("followUpPerson").value ==""){
					document.getElementById("followUpPerson").focus();
				}else if(document.getElementById("followUpLocation").value ==""){
					document.getElementById("followUpLocation").focus();
				}else if(document.getElementById("followupRemarks").value ==""){
					document.getElementById("followupRemarks").focus();
				}	
			}
			
	
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false
	 	}else
	 			return true
}


function cpNotepadDisable()
{
	// alert("TEST");
	if(document.getElementById("followUp").value == "N")
	{
		document.getElementById("showDate").style.display='none';
		document.getElementById("notshowDate").style.display='block';
		document.getElementById("followupTime").disabled=true;
		document.getElementById("followUpPerson").disabled=true;
		document.getElementById("followUpLocation").disabled=true;
		document.getElementById("followupRemarks").disabled=true;
	}
	else if(document.getElementById("followUp").value == "Y")
	{
		document.getElementById("showDate").style.display='block';
		document.getElementById("notshowDate").style.display='none';
		document.getElementById("followupTime").disabled=false;
		document.getElementById("followUpPerson").disabled=false;
		document.getElementById("followUpLocation").disabled=false;
		document.getElementById("followupRemarks").disabled=false;
	}
}


function notepadTime()
{
	var timeStr = document.getElementById("meetingTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('meetingTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('meetingTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('meetingTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('meetingTime').value='';
	return false;
	}
}

function followTime()
{
	var timeStr = document.getElementById("followupTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('followupTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('followupTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('followupTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('followupTime').value='';
	return false;
	}
}