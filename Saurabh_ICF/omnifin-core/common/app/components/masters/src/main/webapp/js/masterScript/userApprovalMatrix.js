function saveRecords(val)
{	
	DisButClass.prototype.DisButMethod();
	var userId=document.getElementById('userId').value ;
	var role=document.getElementById('role').value ;
	
	if(userId=="")
	 {
		 alert("User ID is required.");
		 DisButClass.prototype.EnbButMethod();
		// document.getElementById("save").removeAttribute("disabled","false");
	 		 return false;
	 }	
	if(role=="")
	 {
		 alert("User Role is required.");
		 DisButClass.prototype.EnbButMethod();
		 //document.getElementById("save").removeAttribute("disabled","false");
	 		 return false;
	 }
	if(role=='P') 
	{
		var subRuleType=document.getElementById('subRuleType').value ;
		if(subRuleType=="")
		 {
			 alert("Sub Role Type is required.");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }
	}
	if(role=='U')
	{
		var amountFrom=document.getElementById('amountFrom').value ;
		var amountTo=document.getElementById('amountTo').value ;
		
		 if(amountFrom==""  && amountTo=="")
		 {
			 alert("Amount range is required. ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }	
		 for(var i=0;i<amountFrom.length;i++)
		 {
			var vl=amountFrom.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount From'");
				DisButClass.prototype.EnbButMethod();
				 //document.getElementById("save").removeAttribute("disabled","false");
				return false;
			}
		 }
		for(var i=0;i<amountTo.length;i++)
		{
			var vl=amountTo.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount To'");
				DisButClass.prototype.EnbButMethod();
				 //document.getElementById("save").removeAttribute("disabled","false");
				return false;
			}
		}
		 if(amountFrom && amountTo=="")
		 {
			 alert("Plese enter amount for 'Amount From' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }
		 if(amountFrom=="" && amountTo)
		 {
			 alert("Plese enter amount for 'Amount To' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }
		var amFrList=amountFrom.split(",");
		var amToList=amountTo.split(",");
		amountFrom="";
		amountTo="";
		for(var i=0;i<amFrList.length;i++)
			amountFrom=amountFrom+amFrList[i];
		for(var i=0;i<amToList.length;i++)
			amountTo=amountTo+amToList[i];
		if(parseFloat(amountFrom) > parseFloat(amountTo))
		{
			alert("'Amount From' should be less than or equal to 'Amount To'");
			DisButClass.prototype.EnbButMethod();
			// document.getElementById("save").removeAttribute("disabled","false");
			return false;
		}
		document.getElementById('amountFrom').value=amountFrom;
		document.getElementById('amountTo').value=amountTo;
	}


	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalMatrix").action=contextPath+"/userApprovalMatrixAction.do?method=saveUserApprovalRecords&flag="+val;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalMatrix").submit();

}
function addApprovalRecords(val)
{	
	DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalMatrix").action=contextPath+"/userApprovalMatrixAction.do?method=addUserApprovalRecords&flag="+val;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalMatrix").submit();
}
function updateRecords(val)
{
	DisButClass.prototype.DisButMethod();
	var role=document.getElementById('role').value ;
	//alert("role:::::::::"+role);
	if(role == "Policy Approval")
	{
		var subRuleType=document.getElementById('subRuleTypeP').value ;
		//alert("subRuleType----------------------"+subRuleType);
		if(subRuleType == '' || subRuleType == null)
		 {
			 alert("Sub Role Type is required.");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }
	}
	if(role=="Under Writer")
	{
		var amountFrom=document.getElementById('amountFrom').value ;
		var amountTo=document.getElementById('amountTo').value ;
		
		for(var i=0;i<amountFrom.length;i++)
		{
			var vl=amountFrom.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount From'");
				DisButClass.prototype.EnbButMethod();
				// document.getElementById("update").removeAttribute("disabled","false");
				return false;
			}
		}
		for(var i=0;i<amountTo.length;i++)
		{
			var vl=amountTo.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount To'");
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("update").removeAttribute("disabled","false");
				return false;
			}
		}
		if(amountFrom==""  && amountTo=="")
		 {
			 alert("Amount range is required. ");
			 DisButClass.prototype.EnbButMethod();
			// document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }	 
		 if(amountFrom && amountTo=="")
		 {
			 alert("Plese enter amount for 'Amount To' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }
		 if(amountFrom=="" && amountTo)
		 {
			 alert("Plese enter amount for 'Amount From' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }
		
		var amFrList=amountFrom.split(",");
		var amToList=amountTo.split(",");
		amountFrom="";
		amountTo="";
		for(var i=0;i<amFrList.length;i++)
			amountFrom=amountFrom+amFrList[i];
		for(var i=0;i<amToList.length;i++)
			amountTo=amountTo+amToList[i];
		if(parseFloat(amountFrom) > parseFloat(amountTo))
		{
			alert("Amount From should be less than or equal to Amount To");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("update").removeAttribute("disabled","false");
			return false;
		}
		document.getElementById('amountFrom').value=amountFrom;
		document.getElementById('amountTo').value=amountTo;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalMatrix").action=contextPath+"/userApprovalMatrixAction.do?method=updateUserApprovedRecord&flag="+val;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalMatrix").submit();	
}
function searchApprovedRecords(val)
{
	DisButClass.prototype.DisButMethod();
	var lbxUserId =document.getElementById('lbxUserId').value ;
	var role =document.getElementById('role').value ;
	if(val=="Y")
	{
		var searchStatus = document.getElementById('statusCase').value ;
		if(lbxUserId=="" && role=="" && searchStatus=="")
		{
			alert("Plese select at least one field.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	else
	{
		if(lbxUserId=="" && role=="")
		{
			alert("Plese select at least one field.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalMatrix").action=contextPath+"/userApprovalMatrixAction.do?method=searchApprovedRecords&flag="+val;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalMatrix").submit();	
}
function viewBranchAccess()
{
	var userId=document.getElementById('lbxUserId').value ;
	if(userId=="")
	 {
		 alert("User ID is required.");
	 		 return false;
	 }	
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/userApprovalMatrixAction.do?method=viewBranchAccess&userId="+userId;
	mywindow=window.open(url,'name','height=400,width=600,top=200,left=250,scrollbars=yes' ).focus();
	mywindow.moveTo(800,300);
	if (window.focus) 
	{
		mywindow.focus();
		return false;
	}
	return true;
}
function viewProductAccess()
{
	var userId=document.getElementById('lbxUserId').value ;
	if(userId=="")
	 {
		 alert("User ID is required.");
	 		 return false;
	 }
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/userApprovalMatrixAction.do?method=viewProductAccess&userId="+userId;
	mywindow=window.open(url,'name','height=400,width=600,top=200,left=250,scrollbars=yes' ).focus();
	mywindow.moveTo(800,300);
	if (window.focus) 
	{
		mywindow.focus();
		return false;
	}
	return true;
}
function userRole(){
	
	if((document.getElementById("role").value) == "U")
	{
		document.getElementById("amount").style.display="";
		document.getElementById("forP").style.display="none";
	}
	if((document.getElementById("role").value) == "P")
	{
		document.getElementById("amount").style.display="none";
		document.getElementById("forP").style.display="";
	}
		
	
}

function updateBeforeSave()
{
	DisButClass.prototype.DisButMethod();
	alert("First Save Than Forward.");
	document.getElementById("forward").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
}

function forwardRecords()
{
	DisButClass.prototype.DisButMethod();
	var role=document.getElementById('role').value ;
	//alert("role:::::::::"+role);
	if(role == "Policy Approval")
	{
		var subRuleType=document.getElementById('subRuleTypeP').value ;
		//alert("subRuleType----------------------"+subRuleType);
		if(subRuleType == '' || subRuleType == null)
		 {
			 alert("Sub Role Type is required.");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("save").removeAttribute("disabled","false");
		 		 return false;
		 }
	}
	if(role=="Under Writer")
	{
		var amountFrom=document.getElementById('amountFrom').value ;
		var amountTo=document.getElementById('amountTo').value ;
		
		for(var i=0;i<amountFrom.length;i++)
		{
			var vl=amountFrom.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount From'");
				DisButClass.prototype.EnbButMethod();
				// document.getElementById("update").removeAttribute("disabled","false");
				return false;
			}
		}
		for(var i=0;i<amountTo.length;i++)
		{
			var vl=amountTo.charAt(i);
			if(vl=='%')
			{
				alert("Please enter valid amount in 'Amount To'");
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("update").removeAttribute("disabled","false");
				return false;
			}
		}
		if(amountFrom==""  && amountTo=="")
		 {
			 alert("Amount range is required. ");
			 DisButClass.prototype.EnbButMethod();
			// document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }	 
		 if(amountFrom && amountTo=="")
		 {
			 alert("Plese enter amount for 'Amount To' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }
		 if(amountFrom=="" && amountTo)
		 {
			 alert("Plese enter amount for 'Amount From' also... ");
			 DisButClass.prototype.EnbButMethod();
			 //document.getElementById("update").removeAttribute("disabled","false");
		 		 return false;
		 }
		
		var amFrList=amountFrom.split(",");
		var amToList=amountTo.split(",");
		amountFrom="";
		amountTo="";
		for(var i=0;i<amFrList.length;i++)
			amountFrom=amountFrom+amFrList[i];
		for(var i=0;i<amToList.length;i++)
			amountTo=amountTo+amToList[i];
		if(parseFloat(amountFrom) > parseFloat(amountTo))
		{
			alert("Amount From should be less than or equal to Amount To");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("update").removeAttribute("disabled","false");
			return false;
		}
		document.getElementById('amountFrom').value=amountFrom;
		document.getElementById('amountTo').value=amountTo;
	}
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalMatrix").action=contextPath+"/userApprovalMatrixAction.do?method=forwardUserApprovedRecord";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalMatrix").submit();	
}

function userApprovalAuthorSave()
{
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalAuthorForm").action=contextPath+"/userApprovalMatrixAction.do?method=saveApprovalAuthor";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalAuthorForm").submit();	
}
function searchAuthorRecords(val)
{
	DisButClass.prototype.DisButMethod();
	var lbxUserId =document.getElementById('lbxUserId').value ;
	var role =document.getElementById('role').value ;
	if(lbxUserId=="" && role=="")
	{
		alert("Plese select at least one field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("userApprovalAuthorSearch").action=contextPath+"/userApprovalAuthorBehindAction.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("userApprovalAuthorSearch").submit();	
}