function openNewFileTracking()
{
DisButClass.prototype.DisButMethod();	
var contextPath=document.getElementById("contextPath").value;
document.getElementById("fileTrackingForm").action =contextPath+"/handSightingDispatchAction.do?method=openNewFileTracking";
document.getElementById("processingImage").style.display = '';
document.getElementById("fileTrackingForm").submit();
return true;
}

function fileTrackingSearch(mode)
{
	var msg='';
	var loanNoSearch=document.getElementById("loanNoSearch").value;
	var lbxLoanNoHIDSearch=document.getElementById("lbxLoanNoHIDSearch").value;
 
    if(lbxLoanNoHIDSearch!='' && loanNoSearch!='')
	{
    	var contextPath=document.getElementById("contextPath").value;
    	if(mode=='viewModeSearch'){
    		document.getElementById("fileTrackingForm").action =contextPath+"/handSightingDispatchAction.do?method=searchFileTrackingDataForView&lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch;
    	}else{
    		document.getElementById("fileTrackingForm").action =contextPath+"/handSightingBehindAction.do?lbxLoanNoHIDSearch="+lbxLoanNoHIDSearch+"&method="+mode;
    	}
		document.getElementById("processingImage").style.display = '';
		document.getElementById("fileTrackingForm").submit();
		return true;
	}
    else
	{
    	msg="Please select atleast one Loan No";
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
		document.getElementById("fileTrackingForm").action =contextPath+"/handSightingDispatchAction.do?method=saveFileTrackingRecord";
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
	    var remarks=document.getElementById("remarks").value;
	    var trackStatus=document.getElementById('fileTrackStatus').value;
	    
	    if(loanNo!='' && lbxLoanNoHID !='' && fileTrackReceivedDate!='' && remarks!='' && trackStatus!='P'){
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("fileTrackingForm").action =contextPath+"/handSightingDispatchAction.do?method=updateFileTrackingRecord";
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
			return false;
	    }
}
	
/*	 function checkFileStatus()
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
	 } */