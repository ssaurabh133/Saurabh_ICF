	function saveCustBankDetails()
	{
		
		var contextPath = document.getElementById("contextPath").value;
		//alert("Cust JS customer ID"+document.getElementById("customerId").value);	
		var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
		var accountNo=document.getElementById('accountNo');
		if(trim(accountNo.value) != ''){
			
			if (!ck_alphaNumeric.test(accountNo.value)) {
					alert("Account No  is invalid.");					
					return false;}
				 }
			if(document.getElementById("customerId").value=="")
			{
				document.getElementById("CustBankDetailsForm").action=contextPath+"/custBankDetailAction.do?method=saveCustBankDetails";
	   			document.getElementById("processingImage").style.display = '';
	 			document.getElementById("CustBankDetailsForm").submit();
	 			document.getElementById("save").removeAttribute("disabled","true");
	 			return true;
			}
			if(document.getElementById("customerId").value!="")
			{
				document.getElementById("CustBankDetailsForm").action=contextPath+"/custBankDetailAction.do?method=updateCustBankDetails";
	   			document.getElementById("processingImage").style.display = '';
	 			document.getElementById("CustBankDetailsForm").submit();
	 			document.getElementById("save").removeAttribute("disabled","true");
	 			return true;
			}
//		}
    
    }
	function removeSpaces1(string) {
		 return string.split(' ').join('');
		}
	function fnChangeCase(formName,fieldName)
	{
	    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
	    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

	}

