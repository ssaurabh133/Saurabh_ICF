function save() 
{
	var path=document.getElementById("path").value;
	var assignFrom=document.getElementById("fromdate").value;
	var assignto=document.getElementById("todate").value;
	var branch=document.getElementById("branch").value;
	var branch_name=document.getElementById("lbxBranchId").value;	
		
	
	//alert(path);
	if((document.getElementById("loanno").value=='') &&(document.getElementById("fromdate").value=='') &&(document.getElementById("todate").value=='') && (document.getElementById("user").value=='') && ((document.getElementById("branch").value=='Specific') && (document.getElementById("lbxBranchId").value=='')))
	{
	   alert(" Please Select At Least One Field");
	   return false;
	}
	if(assignFrom=="" && assignto=="")
	{
		alert("Please enter Date From and Date To");
		return false;
	}
	if(assignFrom!="" && assignto=="")
	{
		alert("Please enter Date To");
		return false;
	}
	
	if(assignFrom=="" && assignto!="")
	{
		alert("Please enter Date From");
		return false;
	}
	
	if(assignFrom!="" && assignto!="")
	{
		var formatD=document.getElementById("formatD").value;
		assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
		assignto=getDateObject(assignto,formatD.substring(2, 3));
		if(assignFrom>assignto)
		{
			alert("Date To must be greater than Date From");
			return false;
		}
	}
	if(branch=="Specific" && branch_name=="")
	{	
		alert("Branch Name is Required");
		document.getElementById("dealButton").focus();
		return false;
	}
	var a= document.getElementById("followUpTrailReportForm").action=path+"/followUpTrailDispatch.do?method=openReport";
	//alert(a);
	//document.getElementById("processingImage").style.display = '';
	document.getElementById("followUpTrailReportForm").submit();
	//return true;
	 	
}
function clearFields()
{
	document.getElementById("loanno").value="";
	document.getElementById("user").value="";
	document.getElementById("fromdate").value="";
	document.getElementById("todate").value="";
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
