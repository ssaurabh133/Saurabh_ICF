function editLoanSearch(val)
{
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	//var product=document.getElementById("product").value;
	//var scheme=document.getElementById("scheme").value;	
	if(loanNo=='' && customerName=='' )
	{
		alert("Please fill atleast one field.");
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("editLoanDetailAction").action = contextPath+"/editLoanDetailAction.do?method=searchEditLoan&val="+val;
		document.getElementById("editLoanDetailAction").submit();
		return true;
	}
}
function newEditLoan()
{
	//alert("newEditLoan");
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("editLoanDetailAction").action = contextPath+"/editLoanDetailAction.do?method=newEditLoan";
	document.getElementById("editLoanDetailAction").submit();
	return true;
}