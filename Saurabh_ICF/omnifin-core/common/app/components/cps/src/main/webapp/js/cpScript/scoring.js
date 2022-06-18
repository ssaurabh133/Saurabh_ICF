function onSearchScoring()
	{
		var dealNo=document.getElementById("dealNo").value;
		var applicationno=document.getElementById("applicationno").value;
		var applicationdate=document.getElementById("applicationdate").value;
		var customername=document.getElementById("customerName").value;
		var lbxProductID=document.getElementById("lbxProductID").value;
		var lbxscheme=document.getElementById("lbxscheme").value;
		var contextPath= document.getElementById("contextPath").value;
		var username=document.getElementById("reportingToUserId").value;
		
		//alert("customername"+customername.length);
		if(username!=''||dealNo!=''||applicationno!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!='')
		{
			if(customername!='' && customername.length>=3)
			{
				document.getElementById("commonForm").action=contextPath+"/scoringProcessAction.do?method=searchForScoring&userId="+username;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				
				return true;
			}
			else if(customername=='')
			{
				document.getElementById("commonForm").action=contextPath+"/scoringProcessAction.do?method=searchForScoring&userId="+username;;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				
				return true;
			}
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				document.getElementById("searchButton").removeAttribute("disabled","true");
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			return false;
		}
	}

     function onSaveofScoring(){
    	
    	  var scoringID= document.getElementById("scoringID").value;    	 
    	 var contextPath= document.getElementById("contextPath").value;
	document.getElementById("scoringForm").action=contextPath+"/scoringProcessAction.do?method=onSaveScore&scoringID="+scoringID;
	document.getElementById("scoringForm").submit();
	 return true;
	
}