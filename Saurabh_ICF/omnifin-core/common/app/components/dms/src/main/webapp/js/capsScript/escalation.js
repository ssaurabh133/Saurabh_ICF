
function saveEscalationMatrix()
{
	var total = 0;
	var sourcepath=document.getElementById("contextPath").value;
	var i=0;
	var checkid=document.getElementsByName("checkId");
	for(i=0;i < checkid.length;i++)
	{
			if(checkid[i].checked == true){
				   total += 1;
				if(total>0 && document.getElementById("remarks").value !="")
					{                                                                        
						document.getElementById("escalationsMatrixForm").action=sourcepath+"/escalationsMatrix.do";
						document.getElementById("escalationsMatrixForm").submit();

						
					}
				}
	

	}
	if(total==0)
	{
		alert ("* Please select at least one checkbox!");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
	
	else if((document.getElementById("remarks").value == "") ||(document.getElementById("remarks").value == null))
	{
	   alert("* Please fill remarks!");
	   document.getElementById("save").removeAttribute("disabled","true");
	   return false;
	}
	

}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function escalationSearch(val){ 
    
	     var contextPath=document.getElementById("contextPath").value;
	     //alert("In escalationSearch()");
	     if((document.getElementById("loanno").value =='') 
	    		 && (document.getElementById("customername").value =='') 
	    		 && (document.getElementById("product").value=='') 
	    		 && (document.getElementById("dpd1").value=='') 
	    		 && (document.getElementById("dpd2").value =='') 
	    		 && (document.getElementById("queue").value=='')
	    		 && (document.getElementById("pos1").value =='') 
	    		 && (document.getElementById("pos2").value=='')
	    		 && (document.getElementById("custype").value=='')
	    		 && (document.getElementById("custcategory").value=='')
	    		 && (document.getElementById("queueDateFrom").value=='')
	    		 && (document.getElementById("queueDateTo").value=='')
	    		&& (document.getElementById("actionNotTaken").value=='')
	    		&& (document.getElementById("balanceprincipal").value==''))
	     { 
	    	   alert(val);
		       document.getElementById("search").removeAttribute("disabled");
		       return false;
	 }else {
			var assignFrom=document.getElementById("queueDateFrom").value;
			var assignto=document.getElementById("queueDateTo").value;
			var formatD=document.getElementById("formatD").value;
			assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
			assignto=getDateObject(assignto,formatD.substring(2, 3));
			  
			 if(document.getElementById("dpd1").value=='.'){
				alert("DPD >= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("dpd2").value=='.'){
				alert("DPD <= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos1").value=='.'){
				alert("Over Due Amount <= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos2").value=='.'){
				alert("Over Due Amount >= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("balanceprincipal").value=='.'){
				alert("Balance Principal is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if((document.getElementById("queueDateFrom").value !="")&&(document.getElementById("queueDateTo").value !="")){
			if(assignFrom>assignto){
				alert(" Queue date to  must be greater than Queue date from");
			    document.getElementById("search").removeAttribute("disabled");
				return false;
			  }
			}else {
		 //alert("In else Block");
	          document.getElementById("escalationsMatrixForm").action = contextPath+"/escalationsMatrixBehind.do?method=searchEscalationsMatrix";
	          document.getElementById("escalationsMatrixForm").submit();
	 		  return true;
	 }
	 }
}  

	     function getDateObject(dateString,dateSeperator)
	     {
	     		var dateParts = dateString.split(dateSeperator);
				var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
				return dateObject;

	     }
		
function openRecordingTrailCaseHistory(loanNo){
	//alert("Loan no :---"+loanNo);
	var contextPath=document.getElementById("contextPath").value;
	
	
	var contextPath=document.getElementById("contextPath").value;
	url= contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&caseHistory=caseHistory";
	newWindow=window.open(url,'name','height=700,width=1000,top=0,left=250,scrollbars=yes ');
	if (window.focus) 
    {
		newWindow.focus()
    }
	
}



function CompareDates()
{ 
	//alert("In CompareDates");
    var str1 = document.getElementById("queueDateFrom").value;
    var str2 = document.getElementById("queueDateTo").value;
    var dt1  = parseInt(str1.substring(0,2),10);
    var mon1 = parseInt(str1.substring(3,5),10);
    var yr1  = parseInt(str1.substring(6,10),10);
    var dt2  = parseInt(str2.substring(0,2),10);
    var mon2 = parseInt(str2.substring(3,5),10);
    var yr2  = parseInt(str2.substring(6,10),10);
    var QueueDateFrom = new Date(mon1, dt1, yr1);
    var QueueDateTo = new Date(mon2, dt2, yr2);
  
    if(QueueDateTo < QueueDateFrom)
    {
    	alert("Queue Date To cannot be greater than Queue Date From");
        return false;
    }
    else
    {
       // alert("Submitting ...");
        return true;
    }
}



function isNumberKey(evt) 
		{
		var charCode = (evt.which) ? evt.which : event.keyCode;

		if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric Value Allowed Here");
			return false;
		}
			return true;
		}
	

function selectAll()
{ 
	 var chkbox=document.getElementsByName('checkId')
	 if(document.getElementById('chkId').checked==true)
	 {
	  for(var i=0;i<chkbox.length;i++)
	    {
	      chkbox[i].checked=true;
	    }
	 }
	 else
	 {
	  for(var i=0;i<chkbox.length;i++)
	   {
	    chkbox[i].checked=false;
	   }
	 }
	 
}
	