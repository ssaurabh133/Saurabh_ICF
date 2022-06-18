
 function fnSearch(alert1,type)
	{	
		if (type=='P')
		{
			var basePath=document.getElementById("contextPath").value;
			document.getElementById("nonEmiBasedMakerSearch").action=basePath+"/changeRateMaker.do?type="+type;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("nonEmiBasedMakerSearch").submit();
			return true;
		}
		else{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("nonEmiBasedMakerSearch").action=basePath+"/changeRateAuthor.do?method=OpenScreenToAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("nonEmiBasedMakerSearch").submit();
		return true;
		}
	}

function newNonEMILoan()
{ 
		DisButClass.prototype.DisButMethod();
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("nonEmiBasedMakerSearch").action=basePath+"/changeRateMakerNewAction.do?method=OpenNonEMILoanMaker";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("nonEmiBasedMakerSearch").submit();
	    return true;
	}	


function fnSave(alert1,alert2)
{                                          
	var lbxLoanId=document.getElementById("lbxLoanNo").value;
	var loanRateType=document.getElementById("loanRateType").value;
	var saveRecord=document.getElementById("saveRecord").value;
	if ((document.getElementById("loanNo").value=="")&&(document.getElementById("newEffectRate").value==""))
    {
		alert( alert1+"\n "+alert2 );
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if ((document.getElementById("loanNo").value==""))
    {
		alert(alert1);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if ((document.getElementById("newEffectRate").value==""))
    {
		alert(alert2);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	else{
		var basePath=document.getElementById("contextPath").value;
		if(saveRecord=='S'){
			document.getElementById("nonEmiBasedMaker").action=basePath+"/changeRateMakerNewAction.do?method=onSaveNonEMIBasedMaker&lbxLoanNo="+lbxLoanId+"&loanRateType="+loanRateType;
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("nonEmiBasedMaker").submit();
		    return true;	
		}
		else{
	    document.getElementById("nonEmiBasedMaker").action=basePath+"/changeRateMakerNewAction.do?method=UpdateNonEMILoanMaker&lbxLoanNo="+lbxLoanId;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("nonEmiBasedMaker").submit();
	    return true;
		}
	}
}

	
	function forwardBeforeSave(){
	     DisButClass.prototype.DisButMethod();
       	 alert("Please save the data First");
       	 DisButClass.prototype.EnbButMethod();
       	 return false;
        }
	
	
	function forward()
	{
	var basePath=document.getElementById("contextPath").value;
	var lbxLoanId=document.getElementById("lbxLoanNo").value;                                              
	document.getElementById("nonEmiBasedMaker").action=basePath+"/changeRateMakerNewAction.do?method=ForwardSaveNonEMILoanData&lbxLoanNo="+lbxLoanId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("nonEmiBasedMaker").submit();
    return true;
	}
	
	
	function saveAuthor(alert1)
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
		if((document.getElementById("comments").value==""))
			{
			alert(alert1);
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		
		else
		{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("changeRateAuthorForm").action=basePath+"/changeRateAuthorDispatch.do?method=SaveRateChangeData";
		document.getElementById("changeRateAuthorForm").style.display = '';
		document.getElementById("changeRateAuthorForm").submit();
	    return true;
		 }
	}
	
	
	function checkMaxLength(){
		var comment=document.getElementById("comments").value;
		if(comment.length>500){
			alert("Author Comments should be in between 0 to 500 Characters");
		}
	}
		