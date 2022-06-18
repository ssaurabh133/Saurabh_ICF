	function saveBusinessActivity()
	{
		var noOfEmployees = document.getElementById('noOfEmployees');
		var contextPath = document.getElementById("contextPath").value;

			if(noOfEmployees.value!='')
			{
				var reg = new RegExp('^[0-9]+$');
				 if (!reg.test(noOfEmployees.value)) 
				 {
					 if(noOfEmployees.value != '')
					 {
						 alert("No Of Employees Should Be Numeric Only");
						 document.getElementById("save").removeAttribute("disabled","true");
						 return false;
					 }
				 }
			}
		
		
			if(document.getElementById("customerId").value=="")
			{
				document.getElementById("BusinessActivityForm").action=contextPath+"/corporateBusinessActivityDispatchAction.do?method=saveBusinessActivity";
	   			document.getElementById("processingImage").style.display = '';
	 			document.getElementById("BusinessActivityForm").submit();
	 			document.getElementById("save").removeAttribute("disabled","true");
	 			return true;
			}
			if(document.getElementById("customerId").value!="")
			{
				document.getElementById("BusinessActivityForm").action=contextPath+"/corporateBusinessActivityDispatchAction.do?method=updateBusinessActivity";
	   			document.getElementById("processingImage").style.display = '';
	 			document.getElementById("BusinessActivityForm").submit();
	 			document.getElementById("save").removeAttribute("disabled","true");
	 			return true;
			}
		}
