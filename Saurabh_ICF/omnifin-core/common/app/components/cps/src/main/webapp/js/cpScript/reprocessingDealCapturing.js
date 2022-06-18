/*
Created By:- Abhishek Mathur
Date of Creation:- 14/10/2015
Purpose:- Rejected deals would be available at deal capturing stage for re-processing 
*/

function RejectedDealSearchForMaker()
{
		DisButClass.prototype.DisButMethod();
		var sourcePath=document.getElementById('contextPath').value;
		var dealId=document.getElementById('lbxDealNo').value;
		
		if(dealId=='')
			{
				alert("Please select Deal Number!!");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		else
			{
				document.getElementById("reprocessingDealCapturingAction").action=sourcePath+"/reprocessingDealCapturingAction.do?method=rejectedDealSearch&dealId="+dealId;
				document.getElementById("processingImage").style.display = '';
			    document.getElementById("reprocessingDealCapturingAction").submit();
			    return true;
			}
}

function RejectedDealSearchAuthor()
{
		DisButClass.prototype.DisButMethod();
		var sourcePath=document.getElementById('contextPath').value;
		var dealId=document.getElementById('lbxDealNo').value;
		
		if(dealId=='')
			{
				alert("Please select Deal Number!!");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		else
			{
				document.getElementById("reprocessingDealCapturingAction").action=sourcePath+"/reprocessingDealCapturingAction.do?method=rejectedDealSearchAuthor&dealId="+dealId;
				document.getElementById("processingImage").style.display = '';
			    document.getElementById("reprocessingDealCapturingAction").submit();
			    return true;
			}
}



function saveAndForward()
{
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	var dealId=document.getElementById('lbxDealNo').value;
	
	/*if(decision=='')
		{
			alert("Please Take Decision!!");
			DisButClass.prototype.EnbButMethod();
			return false;
		}*/
	/*else
		{*/
			document.getElementById("reprocessingDealCapturingAction").action=sourcePath+"/reprocessingDealCapturingAction.do?method=saveAndForward&dealId="+dealId;
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("reprocessingDealCapturingAction").submit();
		    return true;
		/*}*/
}

function savedecision(decision)
{
	DisButClass.prototype.DisButMethod();
	var sourcePath=document.getElementById('contextPath').value;
	var dealId=document.getElementById('lbxDealNo').value;
	var decision = document.getElementById("decision").value
	if(decision=='')
		{
			alert("Please Take Decision!!");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	else
		{
			document.getElementById("reprocessingDealCapturingAction").action=sourcePath+"/reprocessingDealCapturingAction.do?method=saveDecision&decision="+decision+"&dealId="+dealId;
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("reprocessingDealCapturingAction").submit();
		    return true;
		}
}
