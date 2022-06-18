function openNewRepaySchedule()
		{
			DisButClass.prototype.DisButMethod();
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("repayscheduleSearchForm").action = contextPath+"/repayscheduleSearch.do?method=openNewRepaySchedule";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("repayscheduleSearchForm").submit();
		}


function generateRepayScheduleReschCharges(){
	
	//alert("in generateRepayScheduleReschCharges id");
	 var loanId=document.getElementById("lbxLoanNoHID").value;	 
	 var reschDate=document.getElementById("curentDueDate").value;	
	if(loanId==''){
		alert("LoanNo is required.");
		
		return false;
	}
	 
	 var partPaymentAmt=0;	
	
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=generateReschCharges";
		 var data = "lbxLoanNoHID="+loanId+"&partPaymentAmt="+partPaymentAmt+"&reschDate="+reschDate;
		 sendGenerateReschChargesId(address, data);
		 return true;

}
  function sendGenerateReschChargesId(address, data) {
	//alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultGenerateReschCharges(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
  }
  
  function resultGenerateReschCharges(request){
		
		//alert("in resultPartpayment id");
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			//alert("String:--->"+str);
			var s1 = str.split("$:");
			document.getElementById("reschCharges").value=trim(s1[0]);  
			//document.getElementById("interestForGapPeriod").value=trim(s1[1]);
		}
	}
  
  function checkDueDate(san)
  {         
	  
	        
  			var date = new Date();
  	   		var dueDate = document.getElementById('dueDay').value;
  	   		var str = document.getElementById('nextDueDate').value;
  	   		//alert(str);
  	   		var day=str.substring(0,2);
  	   		//alert("day: "+day+" dueDate: "+dueDate);
  	   		if(dueDate != '')
  	   		{
  	   			if(parseFloat(dueDate) != parseFloat(day))
  	   			{
  		   			alert("Next Due Date must be equal to Due Day ie. "+dueDate);
  		   			document.getElementById('nextDueDate').value = '';	   		
  		   		}
  		   		else
  		   		{
  		   			checkDate('nextDueDate');
  		   		}
  	   		
  	   		}
  	   		else
  	   		{
  	   			alert("First select Due Day");
  	   			document.getElementById('nextDueDate').value = '';
  	   		}		   		
  }
  
  function saveRepaySchedule(){
	  	
	  		DisButClass.prototype.DisButMethod();
	     var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='';
	     var formatD=document.getElementById("formatD").value;
	     var loanNo=document.getElementById("loanNo").value;
	     var deferralFromInstallment=document.getElementById("deferralFromInstallment").value;
	     var dueDay=document.getElementById("dueDay").value;
		 var nextDueDate=document.getElementById("nextDueDate").value;		
		 var makerRemarks=document.getElementById("makerRemarks").value;
		 var reschCharges=document.getElementById("reschCharges").value;
		 var curentDueDate=document.getElementById("curentDueDate").value;
		 var loanFrequency=document.getElementById("loanFrequency").value;
		 

		 
		 if(loanNo=='')
			 msg1='* Loan No is required \n';
		 if(deferralFromInstallment=='')
			 msg2='* Effective From Installment is required \n';
		 if(dueDay=='')
			 msg3='* Due day is required \n';
		 if(nextDueDate=='')
			 msg4='* Next due day is required \n';
		 if(reschCharges=='')
			 msg5='* Reschedule charge is required \n';
//		if(makerRemarks=='')
//			 msg6='* Maker Remark is required \n'; commented by Nishant
		 
		
		 if(loanNo==''||deferralFromInstallment==''||dueDay=='' ||nextDueDate=='' || reschCharges=='')
		 {
			 alert(msg1+""+msg2+""+msg3+""+msg4+""+msg5);
			if(document.getElementById("loanNo").value == "")
					document.getElementById("loanButton").focus();
			else if(document.getElementById("deferralFromInstallment").value == "")
				    document.getElementById("dueDateDateButton").focus();
			else if(document.getElementById("dueDay").value == "")
					document.getElementById("dueDay").focus();
			else if (document.getElementById("nextDueDate").value == "")
				    document.getElementById("nextDueDate").focus();
			else if (document.getElementById("reschCharges").value == "")
			    document.getElementById("reschCharges").focus();
			else if (document.getElementById("makerRemarks").value == "")
			    document.getElementById("makerRemarks").focus();
			
			DisButClass.prototype.EnbButMethod(); 
			document.getElementById("saveButton").removeAttribute("disabled","true");
			return false;
		 }

		 
		 if (deferralFromInstallment!='1')
		 {
		 if(loanFrequency=="M")
			 var sub=1;
		 else if (loanFrequency=="B")
			 var sub=2;
		 else if (loanFrequency=="Q")
			 var sub=4;
		 else if (loanFrequency=="H")
			 var sub=6;
		 else
			 var sub=12;
		 		 
		 var newDate=curentDueDate.substring(0,2);
		
		 if((curentDueDate.substring(3,5)-sub)<10 )
			 newDate=newDate+'-0'+(curentDueDate.substring(3,5)-sub);
		 else
			 newDate=newDate+'-'+(curentDueDate.substring(3,5)-sub);
			 
			 newDate=newDate+'-'+curentDueDate.substring(6,10);
		
		 //alert("new111111::::"+newDate);
		 var newDate=getDateObject(newDate,formatD.substring(2, 3));
		 var nextDate=getDateObject(nextDueDate,formatD.substring(2, 3));
		 if(newDate>=nextDate)
			{
				alert("Next Due Date cannot be less than or equal to Previous Current Due Date");
				document.getElementById("nextDueDate").value='';
				document.getElementById("nextDueDate").focus();
				DisButClass.prototype.EnbButMethod(); 
				return false;
			}
		 }
		 var basePath=document.getElementById("basePath").value;
		 document.getElementById("updateRepayScheduleForm").action = basePath+"/updateRepaySchedule.do?method=updateRepayScheduleData";
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("updateRepayScheduleForm").submit();
		 return true;
		 
		 
  }
  
  function validateRepaySchedule(){
	  
	  			DisButClass.prototype.DisButMethod();
	          var msg1='',msg2='',msg3=''

			 
			 var reschId=document.getElementById("reschId").value;  
	        
			 
			 if(reschId==null || reschId=='' || reschId=="null"){
				 alert("First save the Repay Shedule Data.");
				 DisButClass.prototype.EnbButMethod();
				 document.getElementById("forwardButton").removeAttribute("disabled","true");
				 return false; 
			 }else{			 
			 
				 var basePath=document.getElementById("basePath").value;
				 document.getElementById("updateRepayScheduleForm").action = basePath+"/updateRepaySchedule.do?method=farwordRepaySchedule";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("updateRepayScheduleForm").submit();
				 return true;
			 }
  }
  
  
  function searchRePpaySchedule(type)
	{
		//alert(type);
	  	DisButClass.prototype.DisButMethod();
		var loanNo = document.getElementById("loanNo").value;
		var custName = document.getElementById("customerName").value;
		var reportingToUserId = document.getElementById("reportingToUserId").value;
		if(loanNo=='' && custName==''&& reportingToUserId=='')
		{
			alert("Please Enter One value to Search.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("search").removeAttribute("disabled","true");
		}
		else
		{
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("repayscheduleSearchForm").action = contextPath+"/repayscheduleSearchBehind.do?";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("repayscheduleSearchForm").submit();
		    document.getElementById("search").removeAttribute("disabled","true");
		}	
	}
  
  
  function viewInstallmentPlanRepricing()
	{
		//alert("Inside viewInstallmentPlanRepricing");
		var loanId = document.getElementById("lbxLoanNoHID").value;
		if (loanId=='')
		{
			alert("Please Select Loan First");	
			return false;
		}
		else
		{
			var contextPath = document.getElementById("contextPath").value;
			//alert(contextPath);
			var url= contextPath+"/installmentPlanViewRepricing.do?method=viewOldInstallmentPlanRepricing&loanId="+loanId;
			//alert("url: "+url);
			//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			mywindow.moveTo(800,300);
			if (window.focus) {
				mywindow.focus();
				return false;
			}
			return true;
	   }
	}
  
  function viewRepaymentScheduleDisbursal()
  {
  	if (document.getElementById("lbxLoanNoHID").value=="")
  	{
  		alert("Please Select Loan First");	
  		return false;
  	}
  	else
  	{
  		curWin = 0;
  		otherWindows = new Array();
  		var loanId=document.getElementById('lbxLoanNoHID').value;	
  		var contextPath = document.getElementById("contextPath").value;
  		var url= contextPath+"/repaymentScheduleDisbursal.do?method=repaymentSchedule&loanId="+loanId; 
  		myWindow=window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
  		otherWindows[curWin++] =window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
  		if(window.focus) {
  			mywindow.focus();
  			return false;
  		}
  		return true;
     }
  }
//alert("neeraj tripathi");
  function deleteDueDateDetails()
  {
	  		DisButClass.prototype.DisButMethod();	
	  		if(confirm("Are you sure to delete the record.")) 
		    {
				 var basePath=document.getElementById("basePath").value;
				 document.getElementById("updateRepayScheduleForm").action = basePath+"/updateRepaySchedule.do?method=deleteRepayScheduleData";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("updateRepayScheduleForm").submit();
				 return true;
		    }
			else
			{
				DisButClass.prototype.EnbButMethod();
				document.getElementById("delete").removeAttribute("disabled","true");
				return false;
			}
			
	}
  function newRepaymentScheduleRepricing()
	{
		if (document.getElementById("lbxLoanNoHID").value=="")
		{
			alert("Please Select Loan First");	
			return false;
		}
		else
		{
			var loanId=document.getElementById('lbxLoanNoHID').value;
			var reschId=document.getElementById('reschId').value;
			
			//alert("--------->"+reschId);
			if(reschId==''){
				alert("First save the data.");
				return false;
			}
			//alert("reschId: "+reschId);
			var contextPath = document.getElementById("contextPath").value;
			//alert(contextPath);
			var url= contextPath+"/updateRepaySchedule.do?method=newRepayScheduleDueDate&loanId="+loanId+"&reschId="+reschId;
			//alert("url: "+url);
			//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
			mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
			mywindow.moveTo(800,300);
			if (window.focus) {
				mywindow.focus();
				return false;
			}
			return true;
	   }
	}
  
  function nullNextDue(){
	  
	    //alert("nullNextDue");
 		document.getElementById('nextDueDate').value = '';
 		document.getElementById('nextDueDate').focus();
 		return false;
 }
  
  function validateDate(val)
  {
	 
		  var repayEffDate=document.getElementById("repayEffDateAtDueDate").value;
		  var businessDate=document.getElementById("businessdate").value;
		  
//		  if(repayEffDate!='')
//		  {
			  
			  var formatD=document.getElementById("formatD").value;
			  //alert("validateDate: next due date "+val+"formatD: "+formatD+"curentDueDate : "+curentDueDate);
			  var dt1=getDateObject(val,formatD.substring(2, 3));
			  var dt2=getDateObject(repayEffDate,formatD.substring(2, 3));
			  var dt3=getDateObject(businessDate,formatD.substring(2, 3));
			  if(dt1<dt2)
			  {
				  alert("Next Due Date can not be less than Repayment Effective Date");
				  document.getElementById('nextDueDate').value='';
				  document.getElementById('nextDueDate').focus();
				  return false;
			  }
			  else if (dt1<=dt3)
			  {
				alert("Next Due Date can not be less than or equal to Business Date");  
				  document.getElementById('nextDueDate').value='';
				  document.getElementById('nextDueDate').focus();
				  return false;
			  }
				  
			  else
				  return true;
//	  }
//		  else
//		  {
//			  alert("Current Due Date is required");
//			  document.getElementById("curentDueDate").focus();
//			  return false;
//		  }
  }
  
  function saveDueDateAuthor()
  {
	  DisButClass.prototype.DisButMethod();
		var decision=document.getElementById("decision").value;
		if(decision=='A')
		{
			var businessDate=document.getElementById("businessDate").value;
			var makerDate=document.getElementById("makerDate").value;	
			var fromdate=businessDate;
			var todate=makerDate;
			var day1= fromdate.substring(0,2);
			var day2= todate.substring(0,2);
			var month1=fromdate.substring(3,5);
			var month2=todate.substring(3,5);
			var year1=fromdate.substring(6);
			var year2=todate.substring(6);
			if(parseFloat(year1) != parseFloat(year2))
			{	
				alert("Maker Date should be equal to Business Date.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				document.getElementById("comments").focus();
				return false;
			}
			else
			{
				 if(parseFloat(year1)==parseFloat(year2))
				 {
					if(parseFloat(month1) != parseFloat(month2))
					{	
						alert("Maker Date should be equal to Business Date.");
						DisButClass.prototype.EnbButMethod();
						document.getElementById("save").removeAttribute("disabled","true");
						document.getElementById("comments").focus();
		    			return false;
					}
					else 
					{
						if(parseFloat(month1)==parseFloat(month2))
						{
							if(parseFloat(day1) != parseFloat(day2))
							{	
								alert("Maker Date should be equal to Business Date.");
								DisButClass.prototype.EnbButMethod();
								document.getElementById("save").removeAttribute("disabled","true");
								document.getElementById("comments").focus();
				    			return false;
							}
						}
					}
				}
			}
		}
	  if(document.getElementById("comments").value=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant
  	{
  		alert("Comments is Required");
  		DisButClass.prototype.EnbButMethod();
  		document.getElementById("save").removeAttribute("disabled","true");
  		document.getElementById("comments").focus();
  		return false;
  	}
  	else
  	{
  		var contextPath=document.getElementById("contextPath").value;
  	    document.getElementById("approveDueDateAuthorForm").action = contextPath+"/dueDateAuthorProcessAction.do";
  	    document.getElementById("processingImage").style.display = '';
  	    document.getElementById("approveDueDateAuthorForm").submit();
  	    return true;
  	}
  }