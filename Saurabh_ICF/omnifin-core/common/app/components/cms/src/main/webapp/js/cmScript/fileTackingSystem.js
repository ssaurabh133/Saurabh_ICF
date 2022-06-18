function openNewFileTracking()
{
	DisButClass.prototype.DisButMethod();	
	var contextPath=document.getElementById("contextPath").value;
	location.href =contextPath+"/fileTrackingDispatchAction.do?method=openNewBranchFileTracking";
	document.getElementById("processingImage").style.display = '';
	return true;
}

function branchFileTrackingSearch()
{
	var msg='';
	var lbxLoanNoHIDSearch=document.getElementById("lbxLoanNoHIDSearch").value;
	var fileTrackStatus=document.getElementById("fileTrackStatus").value;
    if(lbxLoanNoHIDSearch!=''||fileTrackStatus!='')
	{
    	var contextPath=document.getElementById("contextPath").value;
     	location.href =contextPath+"/fileTrackingBehindAction.do?method=branchFileTracking&lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch+"&fileTrackStatus="+fileTrackStatus;
  		document.getElementById("processingImage").style.display = '';
		return true;
	}
    else
	{
    	msg="Please select at least one field";
		alert(msg);
		return false;
	}
}


function validateFileTrackReceivedDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var bDate=document.getElementById("businessdate").value;
	var fileTrackReceivedDate=document.getElementById("fileTrackReceivedDate").value;
    var dt1=getDateObject(fileTrackReceivedDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
 
    if(dt1>dt3)
	{
		msg="Please Enter Received date less than or equal to bussiness Date";
		document.getElementById("fileTrackReceivedDate").value='';
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

	function fileTrackSave(){
	DisButClass.prototype.DisButMethod();
	var msg1='';
	var msg2='';
	var msg3='';
	var msg4='';
	var loanNo=document.getElementById("loanNo").value;
	var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
    var fileTrackReceivedDate=document.getElementById("fileTrackReceivedDate").value;
    var remarks=document.getElementById("remarks").value;
    var trackStatus=document.getElementById('fileTrackStatus').value;
    
    if(loanNo!='' && lbxLoanNoHID !='' && fileTrackReceivedDate!='' && remarks!='' && trackStatus!='P'){
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingDispatchAction.do?method=saveBranchFileTrackingRecord";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fileTrackingForm").submit();
		return true;
    }else{
		if(loanNo=='' || lbxLoanNoHID=='')
		msg1="* Please select Loan No \n";	
		if(fileTrackReceivedDate=='')
		msg2="* Please select Received Date \n";
		if(remarks=='')
		msg3="* Please fill Remarks \n";	
		if(trackStatus=='P')
		msg4="* File status can't be pending \n"
		alert(msg1+""+msg2+""+msg3+""+msg4);
		DisButClass.prototype.EnbButMethod();
		if(loanNo=='' && lbxLoanNoHID==''){
		document.getElementById("deal_viewer").disabled=true;
		document.getElementById("loan_viewer").disabled=true;
		}
		return false;
    }
}
	
	function fileTrackUpdate(){
		DisButClass.prototype.DisButMethod();
		var msg1='';
		var msg2='';
		var msg3='';
		var msg4='';
		var loanNo=document.getElementById("loanNo").value;
		var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
	    var fileTrackReceivedDate=document.getElementById("fileTrackReceivedDate").value;
	    var indexRefNo=document.getElementById("indexRefNo").value;
	    var trackStatus=document.getElementById('fileTrackStatus').value;
	    
	    if(loanNo!='' && lbxLoanNoHID !='' && fileTrackReceivedDate!='' && indexRefNo!='' && trackStatus!='P'){
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingDispatchAction.do?method=updateBranchFileTrackingRecord";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("fileTrackingForm").submit();
			return true;
	    }else{
			if(loanNo=='' || lbxLoanNoHID=='')
			msg1="* Please select Loan No \n";	
			if(fileTrackReceivedDate=='')
			msg2="* Please select Received Date \n";
			if(indexRefNo=='')
			msg3="* Please fill Index/ Ref. No \n";	
			if(trackStatus=='P')
			msg4="* File status can't be pending \n"
			alert(msg1+""+msg2+""+msg3+""+msg4);
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
}
	
	
	function operationFileTrackUpdate(){
		DisButClass.prototype.DisButMethod();
		var msg1='';
		var msg2='';
		var msg3='';
		var msg4='';
		
		var loanNo=document.getElementById("loanNo").value;
		var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
	    var fileTrackReceivedDate=document.getElementById("fileTrackReceivedDate").value;
	    var remarks=document.getElementById("remarks").value;
	    var trackStatus=document.getElementById('fileTrackStatus').value;
	    var lbxReasonId=document.getElementById('lbxReasonId').value;
	    var userButton1=document.getElementById('userButton1');
	   
	
	    if(loanNo!='' && lbxLoanNoHID !='' && fileTrackReceivedDate!='' && remarks!='' && trackStatus!='P' ){
			var contextPath=document.getElementById("contextPath").value;
			if(trackStatus=='H' && lbxReasonId==''){
				
				alert('Please select Reason');
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingDispatchAction.do?method=updateOperationFileTrackingRecord";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("fileTrackingForm").submit();
			return true;
	    }
	   else{
			if(loanNo=='' || lbxLoanNoHID=='')
			msg1="* Please select Loan No \n";	
			if(fileTrackReceivedDate=='')
			msg2="* Please select Received Date \n";
			if(remarks=='')
			msg3="* Please fill Remarks \n";	
			if(trackStatus=='P')
			msg4="* File status can't be pending \n"
			
			alert(msg1+""+msg2+""+msg3+""+msg4);
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
}
	
	 function checkFileStatus()
	 {
		 var loanId=document.getElementById('lbxLoanNoHID').value;
	 	 var trackStatus=document.getElementById('fileTrackStatus').value;
	 	 if(loanId!='')
	 	 {
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=checkFileStatus";
		 var data = "lbxLoanNoHID="+loanId;
		 sendcheckFileStatus(address, data);
		 return true;
	 	 }else{
	 		 alert("Please Select Loan No");
	 		 document.getElementById('fileTrackStatus').value='P'; 	
	 	 }
	 	 
	 }

	 function sendcheckFileStatus(address, data) {
	 	var request = getRequestObject();
	 	request.onreadystatechange = function () {
	 		resultcheckFileStatus(request);	
	 	};
	 	request.open("POST", address, true);
	 	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 	request.send(data);
	 	
	 }

	 function resultcheckFileStatus(request){
	 	if ((request.readyState == 4) && (request.status == 200)) {
	 		var str = request.responseText;
	 		var status=document.getElementById('fileTrackStatus').value;
			if(str.split("$:").length>1)
		    {
				var s1=str.split("$:");
				if(trim(s1[1])=='R'){
					 if(status=='P'){
						  alert("File status of this loan should be Received / Hold / Clear");
						  document.getElementById('fileTrackStatus').value=trim(s1[1]); 
						}else{
							 document.getElementById('fileTrackStatus').value=status; 
						}
				}else if(trim(s1[1])=='H'){
					if(status=='P' || status=='R'){
						  alert("File status of this loan should be Hold or Clear");
						  document.getElementById('fileTrackStatus').value=trim(s1[1]); 
						}else{
							 document.getElementById('fileTrackStatus').value=status; 
						}
				}
				else if(trim(s1[1])=='C'){
					if(status=='P' || status=='R' || status=='H'){
						  alert("File status of this loan can not be Received / Hold");
						  document.getElementById('fileTrackStatus').value=trim(s1[1]); 
						}else{
							 document.getElementById('fileTrackStatus').value=status; 
						}
				}
	 			
		    }
			else{
					if(!(status=='P' || status=='R')){
					  alert("File status of this loan should be Received");
					  document.getElementById('fileTrackStatus').value='R'; 
					}
		 		}
			window.close();
	 	}
	 }
	 
	 function operationFileTrackingSearch()
	 {
	 	var msg='';
	
	 	var lbxLoanNoHIDSearch=document.getElementById("lbxLoanNoHIDSearch").value;
	 	var fileTrackStatus=document.getElementById("fileTrackStatus").value;
	
	     if(lbxLoanNoHIDSearch!='' || fileTrackStatus!='')
	 	{
	     	var contextPath=document.getElementById("contextPath").value;
	       	location.href =contextPath+"/fileTrackingBehindAction.do?method=operationFileTracking&lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch+"&fileTrackStatus="+fileTrackStatus;
	       	document.getElementById("processingImage").style.display = '';
	  		return true;
	 	}
	     else
	 	{
	     	msg="Please select at least one field";
	 		alert(msg);
	 		return false;
	 	}
	 }
	 
	 function storeFileTrackingSearch()
	 {
	 	
	 	var lbxLoanNoHIDSearch=document.getElementById("lbxLoanNoHIDSearch").value;
	 	var fileTrackStatus=document.getElementById("fileTrackStatus").value;
	    if(lbxLoanNoHIDSearch!='' || fileTrackStatus!='')
	 	{
	     	var contextPath=document.getElementById("contextPath").value;
	     
	     	location.href =contextPath+"/fileTrackingBehindAction.do?method=storeFileTracking&lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch+"&fileTrackStatus="+fileTrackStatus;
	     	document.getElementById("processingImage").style.display = '';
	 		
	 		return true;
	 	}
	     else
	 	{
	   		alert("Please select at least one field");
	 		return false;
	 	}
	 }
	// Code Start by Anil for File Tracking

	 function dealViewerForFileTrack()
	 {
	 	   otherWindows = new Array();
	 	   curWin = 0;
	 	   var dealid=document.getElementById("lbxDealNo").value;
	 	   var dealIdMain ='';
	 	   var contextPath=document.getElementById("contextPath").value;
	 	   var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+dealid+"&status=UWA&fromCM=cm";
	 	   mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	 	   otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
	 	   mywindow.moveTo(800,300);
	 	   if (window.focus) 
	 	   {
	 			mywindow.focus();
	 			return false;
	 	   }
	 	   return true;
	 }


	 function loanViewerPresentationForFileTracking()
	 {
	    otherWindows = new Array();
	    curWin = 0;
	    var loanid=document.getElementById("lbxLoanNoHID").value;	
	    var loanIDMain ='';			 
	    var contextPath=document.getElementById("contextPath").value;			   
	    var url = contextPath+"/searchLoanDealBehindAction.do?loanId="+loanid+"&status=UWA&fromCM=cm";
	    mywindow =window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes').focus();
	    otherWindows[curWin++]=window.open(url,'name','height=400,width=1100,top=200,left=250,scrollbars=yes');
	    mywindow.moveTo(800,300);
	    if (window.focus) 
	    {
	 		mywindow.focus();
	 		return false;
	    }
	    return true;		   	
	 }

	 function fileTrackingSearch()
	 {
	 	
	 	
	 	var lbxLoanNoHIDSearch=document.getElementById("lbxLoanNoHIDSearch").value;
	  
		var fileTrackStatus=document.getElementById("fileTrackStatus").value;
		
	     if(lbxLoanNoHIDSearch!='' || fileTrackStatus!='')
	 	{
	     	var contextPath=document.getElementById("contextPath").value;
	     	location.href =contextPath+"/fileTrackingBehindAction.do?method=viewFileTracking&lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch+"&fileTrackStatus="+fileTrackStatus;
	     	document.getElementById("processingImage").style.display = '';
	 	
	 		return true;
	 	}
	     else
	 	{
	    	 alert("Please select at least one field");
	 		return false;
	 	}
	 }
	 function allChecked(){ 
		
			var ptable = document.getElementById('gridtable');
			var lastElement = ptable.rows.length;
			var c = document.getElementById("allchkd").checked;
			var psize=document.getElementById("psize").value;
			if(psize==""){
				psize=lastElement;
			}
			if(c==true){
				for(i=1;i<psize;i++)
				{
					var ch1=document.getElementById('chk'+i);
					if(ch1!=undefined||ch1!=null){
						document.getElementById('chk'+i).checked=true;
						}		
			}
			}else{
				for(i=1;i<psize;i++)
				{
				var ch2=document.getElementById('chk'+i);
					if(ch2!=undefined||ch2!=null){
						document.getElementById('chk'+i).checked=false;
					}
				}
			}
		}
	 function branchBulkFileTrackingSearch()
	 {
		
	 	var msg='';
	 	var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
	 	// alert("test"+lbxLoanNoHID);
	 	var lbxBranchId=document.getElementById("lbxBranchId").value;
	 	var stDate=document.getElementById("startDate").value;
	 	var edDate=document.getElementById("endDate").value;
	 	var allBranches=document.getElementById("allBranches").checked;
	 //	alert("allBranches  : "+allBranches);
	 	if(lbxBranchId!='' && allBranches==true)
	 	{
	 		alert("Select Either Branch or All Branch.");
	 		return false;
	 	}
	 	if(stDate!="" && edDate=="")
		{
			alert("Please enter To Date.");
			return false;
		}		
		if(stDate=="" && edDate!="")
		{
			alert("Please enter From Date ");
			return false;
		}
	 	
		if(stDate!="" && edDate!="")
		{
			var formatD=document.getElementById("formatD").value;
			stDate=getDateObject(stDate,formatD.substring(2, 3));
			edDate=getDateObject(edDate,formatD.substring(2, 3));
			if(stDate>edDate)
			{
				alert(" To Date Must be greater than From Date ");
				return false;
			}
		} 	
	    if(lbxLoanNoHID!=''||lbxBranchId!='' ||stDate!='' || edDate!='' || allBranches==true)
	 	{
	     	var contextPath=document.getElementById("contextPath").value;
		//	document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingBehindAction.do?method=branchBulkFileTracking&lbxLoanNoHID="+lbxLoanNoHID+"&lbxBranchId="+lbxBranchId+"&startDate="+startDate+"&endDate="+endDate+"&allBranches="+allBranches;
			document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingBehindAction.do?method=branchBulkFileTracking&search=Y";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("fileTrackingForm").submit();
			return true;			
	 	}
	    else
	 	{
	     	msg="Please select at least one Record.";
	 		alert(msg);
	 		return false;
	 	}
	 }
	 function bulkfileTrackSave(){
		
			DisButClass.prototype.DisButMethod();
			
			var ptable = document.getElementById('gridtable');
			var lastElement = ptable.rows.length;
			
			var psize=document.getElementById("psize").value;
			if(psize==""){
				psize=lastElement;
			}
			

			
			//alert(psize);
			
			var loanId = "";
			var receivedDate = "";
			var remarks = "";
			var checkedCase = "n";
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					checkedCase = "y";
				}
			}
			
			if(checkedCase =="n" )
			{
				alert("Please select at least one field.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					if(document.getElementById("lbxLoanNoHID"+i).value=="")
		 			{
						alert("Loan Id is blank");
						DisButClass.prototype.EnbButMethod();
						return false;
		 			}
		 			else
		 			{
		 				loanId=loanId+document.getElementById("lbxLoanNoHID"+i).value+"|";
		 			}
					if(document.getElementById("fileTrackReceivedDate"+i).value=="")
		 			{
						alert("Please enter Received Date.");
						document.getElementById("fileTrackReceivedDate"+i).focus();
						DisButClass.prototype.EnbButMethod();
						return false;
						
		 			}
		 			else
		 			{
		 				receivedDate=receivedDate+document.getElementById("fileTrackReceivedDate"+i).value+"|";
		 			}
					if(document.getElementById("remarks"+i).value=="")
		 			{
						remarks=remarks+"$"+"|";
		 			}
		 			else
		 			{
		 				remarks=remarks+document.getElementById("remarks"+i).value+"|";
		 			}
					
					
				}
			}
			

			
				var contextPath=document.getElementById("contextPath").value;
				document.getElementById("fileTrackingForm").action =contextPath+"/fileTrackingDispatchAction.do?method=saveBulkBranchFileTrackingRecord&loanId="+loanId+"&receivedDate="+receivedDate+"&remarks="+remarks;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("fileTrackingForm").submit();
				return true;
		    
		}
	 

	 
//		sachin balyan works starts here	 
	 function receivedAtOpsSearch()
	 {
		 	var msg='';
		 	var lbxLoanNoHID=document.getElementById("lbxLoanNoHIDSearch").value;
		 	// alert("test"+lbxLoanNoHID);
		 	var lbxBranchId=document.getElementById("lbxBranchId").value;
		 	var stDate=document.getElementById("startDate").value;
		 	var edDate=document.getElementById("endDate").value;
		 	var allBranches=document.getElementById("allBranches").checked;
		 //	alert("allBranches  : "+allBranches);
		 	if(lbxBranchId!='' && allBranches==true)
		 	{
		 		alert("Select Either Branch or All Branch.");
		 		return false;
		 	}
		 	if(stDate!="" && edDate=="")
			{
				alert("Please enter To Date.");
				return false;
			}		
			if(stDate=="" && edDate!="")
			{
				alert("Please enter From Date ");
				return false;
			}
		 	
			if(stDate!="" && edDate!="")
			{
				var formatD=document.getElementById("formatD").value;
				stDate=getDateObject(stDate,formatD.substring(2, 3));
				edDate=getDateObject(edDate,formatD.substring(2, 3));
				if(stDate>edDate)
				{
					alert(" To Date Must be greater than From Date ");
					return false;
				}
			} 	
		    if(lbxLoanNoHID!=''||lbxBranchId!='' ||stDate!='' || edDate!='' || allBranches==true)
		 	{
		    	//alert("this is not comng");
		     	var contextPath=document.getElementById("contextPath").value;
			//	document.getElementById("fileTrackingForm").action =contextPath+"/receivedAtOpsBehindAction.do? method=operationFileTrackingOps&lbxLoanNoHID="+lbxLoanNoHID+"&lbxBranchId="+lbxBranchId+"&startDate="+startDate+"&endDate="+endDate+"&allBranches="+allBranches;
				document.getElementById("fileTrackingBranch").action =contextPath+"/receivedAtOpsBehindAction.do?method=operationFileTrackingOps&search=Y";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("fileTrackingBranch").submit();
				return true;			
		 	}
		    else
		 	{
		     	msg="Please select at least one field";
		 		alert(msg);
		 		return false;
		 	}
		 }
	 
	    function receivedAtOpsSave(){
			
			DisButClass.prototype.DisButMethod();
			
			var ptable = document.getElementById('gridtable');
			var lastElement = ptable.rows.length;
			
			var psize=document.getElementById("psize").value;
			if(psize==""){
				psize=lastElement;
			}
			var loanId = "";
			var receivedDate = "";
			var remarks = "";
			var checkedCase = "n";
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					checkedCase = "y";
				}
			}
			
			if(checkedCase =="n" )
			{
				alert("Please select at least one field.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					if(document.getElementById("lbxLoanNoHID"+i).value=="")
		 			{
						alert("Loan Id is blank");
						DisButClass.prototype.EnbButMethod();
						return false;
		 			}
		 			else
		 			{
		 				loanId=loanId+document.getElementById("lbxLoanNoHID"+i).value+"|";
		 			}
					if(document.getElementById("fileTrackReceivedDate"+i).value=="")
		 			{
						alert("Please enter Received Date.");
						document.getElementById("fileTrackReceivedDate"+i).focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
		 			else
		 			{
		 				receivedDate=receivedDate+document.getElementById("fileTrackReceivedDate"+i).value+"|";
		 			}
					if(document.getElementById("remarks"+i).value=="")
		 			{
						remarks=remarks+"$"+"|";
		 			}
		 			else
		 			{
		 				remarks=remarks+document.getElementById("remarks"+i).value+"|";
		 			}
				}
			}
			    var contextPath=document.getElementById("contextPath").value;
				document.getElementById("fileTrackingBranch").action =contextPath+"/receivedAtOpsBehindAction.do?method=savefileTrackingOps&loanId="+loanId+"&receivedDate="+receivedDate+"&remarks="+remarks;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("fileTrackingBranch").submit();
				return true;
		}

	    //sachin for send to store
	    
	    function sendTOStoreSearch()
		 {
			 	var msg='';
			 	var lbxLoanNoHID=document.getElementById("lbxLoanNoHIDSearch").value;
			 	// alert("test"+lbxLoanNoHID);
			 	var lbxBranchId=document.getElementById("lbxBranchId").value;
			 	var stDate=document.getElementById("startDate").value;
			 	var edDate=document.getElementById("endDate").value;
			 	var allBranches=document.getElementById("allBranches").checked;
			 //	alert("allBranches  : "+allBranches);
			 	if(lbxBranchId!='' && allBranches==true)
			 	{
			 		alert("Select Either Branch or All Branch.");
			 		return false;
			 	}
			 	if(stDate!="" && edDate=="")
				{
					alert("Please enter To Date.");
					return false;
				}		
				if(stDate=="" && edDate!="")
				{
					alert("Please enter From Date ");
					return false;
				}
			 	
				if(stDate!="" && edDate!="")
				{
					var formatD=document.getElementById("formatD").value;
					stDate=getDateObject(stDate,formatD.substring(2, 3));
					edDate=getDateObject(edDate,formatD.substring(2, 3));
					if(stDate>edDate)
					{
						alert(" To Date Must be greater than From Date ");
						return false;
					}
				} 	
			    if(lbxLoanNoHID!=''||lbxBranchId!='' ||stDate!='' || edDate!='' || allBranches==true)
			 	{
			    	//alert("this is not comng");
			     	var contextPath=document.getElementById("contextPath").value;
				//	document.getElementById("fileTrackingForm").action =contextPath+"/receivedAtOpsBehindAction.do? method=operationFileTrackingOps&lbxLoanNoHID="+lbxLoanNoHID+"&lbxBranchId="+lbxBranchId+"&startDate="+startDate+"&endDate="+endDate+"&allBranches="+allBranches;
					document.getElementById("fileTrackingBranch").action =contextPath+"/SendTOStoreBehindAction.do?method=operationfileTrackingtostore&search=Y";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("fileTrackingBranch").submit();
					return true;			
			 	}
			    else
			 	{
			     	msg="Please select at least one field";
			 		alert(msg);
			 		return false;
			 	}
			 }
	    
	    

	    function sendTOStoreSave(){
			
			DisButClass.prototype.DisButMethod();
			
			var ptable = document.getElementById('gridtable');
			var lastElement = ptable.rows.length;
			
			var psize=document.getElementById("psize").value;
			if(psize==""){
				psize=lastElement;
			}
			var loanId = "";
			var receivedDate = "";
			var remarks = "";
			var checkedCase = "n";
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					checkedCase = "y";
				}
			}
			
			if(checkedCase =="n" )
			{
				alert("Please select at least one field.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			for(i=1;i<psize;i++)
			{
				if(document.getElementById('chk'+i).checked)
				{
					if(document.getElementById("lbxLoanNoHID"+i).value=="")
		 			{
						alert("Loan Id is blank");
						DisButClass.prototype.EnbButMethod();
						return false;
		 			}
		 			else
		 			{
		 				loanId=loanId+document.getElementById("lbxLoanNoHID"+i).value+"|";
		 			}
					if(document.getElementById("fileTrackReceivedDate"+i).value=="")
		 			{
						alert("Please enter Received Date.");
						document.getElementById("fileTrackReceivedDate"+i).focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
		 			else
		 			{
		 				receivedDate=receivedDate+document.getElementById("fileTrackReceivedDate"+i).value+"|";
		 			}
					if(document.getElementById("remarks"+i).value=="")
		 			{
						remarks=remarks+"$"+"|";
		 			}
		 			else
		 			{
		 				remarks=remarks+document.getElementById("remarks"+i).value+"|";
		 			}
				}
			}
			    var contextPath=document.getElementById("contextPath").value;
				document.getElementById("fileTrackingBranch").action =contextPath+"/SendTOStoreBehindAction.do?method=savefileTrackingtostore&loanId="+loanId+"&receivedDate="+receivedDate+"&remarks="+remarks;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("fileTrackingBranch").submit();
				return true;
		}
	    
	    function lovHideShow()
	    {
	    	
	    	var fileTrackStatus=document.getElementById("fileTrackStatus").value;
	    	
	    	
	    		if(fileTrackStatus=='H')
	    		{	
	    		
	    		document.getElementById("userButton1").style.visibility='visible';
	    		}
	    		else{
	    			
	    		document.getElementById("userButton1").style.visibility='hidden';
	    	}
	    }
	    
