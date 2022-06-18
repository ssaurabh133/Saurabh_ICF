function save(val) {
	var sourcepath=document.getElementById("contextPath").value;
		
	if((document.getElementById("loanno").value=='') && (document.getElementById("product").value=='') 
		&& (document.getElementById("customername").value=='' )&& (document.getElementById("dpd2").value=='') 
		&& (document.getElementById("dpd1").value=='' )	&&	(document.getElementById("queue").value=='') 
		&& (document.getElementById("pos2").value=='') &&	(document.getElementById("pos1").value=='') 
		&& (document.getElementById("user").value=='') 
//		&&	(document.getElementById("tos2").value=='') 
//		&& (document.getElementById("tos1").value=='') &&	(document.getElementById("npastage").value=='')
        && (document.getElementById("custype").value=='') &&	(document.getElementById("custcategory").value=='')
		&& (document.getElementById("balanceprincipal").value=='') && (document.getElementById("fromdate").value=='') 
		&& (document.getElementById("todate").value=='')&& (document.getElementById("fromDueDate").value=='')
		&& ((document.getElementById("branch").value=='Specific') && (document.getElementById("lbxBranchId").value==''))
		&& (document.getElementById("toDueDate").value==''))
		
	{
		alert(val);
	}
	else {
				
		var assignFrom=document.getElementById("fromdate").value;
		var assignto=document.getElementById("todate").value;
		var formatD=document.getElementById("formatD").value;
		assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
		assignto=getDateObject(assignto,formatD.substring(2, 3));
		var fromduedate=document.getElementById("fromDueDate").value;
		var toduedate=document.getElementById("toDueDate").value;
		fromduedate=getDateObject(fromduedate,formatD.substring(2, 3));
		toduedate=getDateObject(toduedate,formatD.substring(2, 3));
		var branch=document.getElementById("branch").value;
		var branch_name=document.getElementById("lbxBranchId").value;	
		if(branch=="Specific" && branch_name=="")
		{	
			alert("Branch Name is Required");
			document.getElementById("dealButton").focus();
			return false;
		}		
		
		if((document.getElementById("fromdate").value=='') || (document.getElementById("todate").value=='') || (document.getElementById("fromDueDate").value=='') || (document.getElementById("toDueDate").value==''))
		{
			if((document.getElementById("fromdate").value=='') && (document.getElementById("todate").value=='') && (document.getElementById("fromDueDate").value=='') && (document.getElementById("toDueDate").value==''))
			{
				alert("Either Select PPT Marking Date Range or PPT Due Date Range!");
				return false;
			}
			if((document.getElementById("fromdate").value!='') && (document.getElementById("todate").value==''))
			{
				alert("Please enter PPT Marking Date To");
				return false;
			}
			
			if((document.getElementById("fromdate").value=='') && (document.getElementById("todate").value!=''))
			{
				alert("Please enter PPT Marking Date From");
				return false;
			}
			if((document.getElementById("fromDueDate").value!='') && (document.getElementById("toDueDate").value==''))
			{
				alert("Please enter PPT Due Date To");
				return false;
			}
			
			if((document.getElementById("fromDueDate").value=='') && (document.getElementById("toDueDate").value!=''))
			{
				alert("Please enter PPT Due Date From");
				return false;
			}
			
			if(document.getElementById("dpd1").value=='.'){
				alert("DPD >= is not in correct Format!");
				document.getElementById("show").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("dpd2").value=='.'){
				alert("DPD <= is not in correct Format!");
				document.getElementById("show").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos1").value=='.'){
				alert("Over Due Amount <= is not in correct Format!");
				document.getElementById("show").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos2").value=='.'){
				alert("Over Due Amount >= is not in correct Format!");
				document.getElementById("show").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("balanceprincipal").value=='.'){
				alert("Balance Principal is not in correct Format!");
				document.getElementById("show").removeAttribute("disabled");
				return false;
			}else if((document.getElementById("fromdate").value !="")&&(document.getElementById("todate").value !="")){
				if(assignFrom>assignto){
				alert(" PTP Marking to date  must be greater than PTP Marking from date ");
			    document.getElementById("show").removeAttribute("disabled");
				return false;
			  }
				else {	
					//alert("In else Block");
					document.getElementById("paymentReportsForm").submit();
					Window.location.reload();

				}
			}
				else if((document.getElementById("fromDueDate").value !="")&&(document.getElementById("toDueDate").value !="")){
					if(fromduedate>toduedate){
					alert(" PTP Due To Date must be greater than PTP Due From Date ");
				    document.getElementById("show").removeAttribute("disabled");
					return false;
				  }
				else {	
					//alert("In else Block");
					document.getElementById("paymentReportsForm").submit();
					Window.location.reload();

				}
			}
			else {	
			document.getElementById("paymentReportsForm").submit();
			Window.location.reload();

		}
		}
		
		else
		{		
		

		 if(document.getElementById("dpd1").value=='.'){
			alert("DPD >= is not in correct Format!");
			document.getElementById("show").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("dpd2").value=='.'){
			alert("DPD <= is not in correct Format!");
			document.getElementById("show").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("pos1").value=='.'){
			alert("Over Due Amount <= is not in correct Format!");
			document.getElementById("show").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("pos2").value=='.'){
			alert("Over Due Amount >= is not in correct Format!");
			document.getElementById("show").removeAttribute("disabled");
			return false;
		}else if(document.getElementById("balanceprincipal").value=='.'){
			alert("Balance Principal is not in correct Format!");
			document.getElementById("show").removeAttribute("disabled");
			return false;
		}else if((document.getElementById("fromdate").value !="")&&(document.getElementById("todate").value !="")){
			if(assignFrom>assignto){
			alert(" PTP Marking to date  must be greater than PTP Marking from date ");
		    document.getElementById("show").removeAttribute("disabled");
			return false;
		  }
			else {	
				//alert("In else Block");
				document.getElementById("paymentReportsForm").submit();
				Window.location.reload();

			}
		}
			else if((document.getElementById("fromDueDate").value !="")&&(document.getElementById("toDueDate").value !="")){
				if(fromduedate>toduedate){
				alert(" PTP Due To Date must be greater than PTP Due From Date ");
			    document.getElementById("show").removeAttribute("disabled");
				return false;
			  }
			else {	
				//alert("In else Block");
				document.getElementById("paymentReportsForm").submit();
				Window.location.reload();

			}
		}
		else {	
		document.getElementById("paymentReportsForm").submit();
		Window.location.reload();

	}
}
}
}

function ClearPaymentField()
{
   // alert("In ClearPaymentField");
	document.getElementById("reportformat").selectedIndex=''
	document.getElementById("loanno").value=''
	document.getElementById("product").selectedIndex=''
	document.getElementById("customername").value=''
	document.getElementById("dpd2").value=''
	document.getElementById("dpd1").value=''
	document.getElementById("queue").value=''
	document.getElementById("pos2").value=''
	document.getElementById("pos1").value=''
	document.getElementById("user").value=''
    document.getElementById("custype").selectedIndex=''
	document.getElementById("custcategory").selectedIndex=''
	document.getElementById("balanceprincipal").value=''
	document.getElementById("fromdate").value=''
	document.getElementById("todate").value=''
	document.getElementById("fromDueDate").value=''
	document.getElementById("toDueDate").value=''
	
	return true;
}
function getDateObject(dateString,dateSeperator)
{
		var dateParts = dateString.split(dateSeperator);
		var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
		return dateObject;

}

function hideLovofBranch()
{
	var lovid=document.getElementById("branch").value;
	if(lovid=='All')
	{
		document.getElementById("dealButton").style.visibility="hidden";
		document.getElementById("branchid").value="";
		document.getElementById("lbxBranchId").value="";
		//document.getElementById("r10").style.display="none";	
		//document.getElementById("r11").style.display="";	
	}
	else
	{					
		document.getElementById("dealButton").style.visibility="visible";
		//document.getElementById("r10").style.display="";
		//document.getElementById("r11").style.display="none";
	}
		
}