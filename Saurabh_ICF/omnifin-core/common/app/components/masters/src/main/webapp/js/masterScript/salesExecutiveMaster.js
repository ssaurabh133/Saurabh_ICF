//sachin balyan
function salesAddd(){
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("salesExecutiveMaster").action=sourcepath+"/salesExecutiveMasterAdd.do?method=addSalesExecutive";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("salesExecutiveMaster").submit();
	return true;
	
}
                            
function fnUpdateSalesExecutiveMaster(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	var businessPartnerName = document.getElementById("lbxBusinessPartnerId");
	var employeeType = document.getElementById("employeeType");
	var employeeName=document.getElementById('employeeName');	
	var lbxBankID=document.getElementById('lbxBankID');	
	var lbxBranchID=document.getElementById('lbxBranchID');	
	var bankAccountNo=document.getElementById('bankAccountNo');
	var ifscCode=document.getElementById('ifscCode');
	var micrCode=document.getElementById('micrCode');
	if(trim(businessPartnerName.value) == ''||trim(employeeType.value) == ''||trim(employeeName.value) == '' 
		||trim(lbxBankID.value) == ''||trim(lbxBranchID.value) == ''||trim(bankAccountNo.value) == ''||trim(ifscCode.value) == ''||trim(micrCode.value) == '')
	{
			var msg= '';
			if(trim(businessPartnerName.value) == '')
				msg += '* Business Partner Name is required.\n';
			if(trim(employeeType.value) == '')
				msg += '* Employee Type is required.\n';	
			if(trim(employeeName.value) == '')
				msg += '* Employee Name is required.\n';
			if(trim(lbxBankID.value) == '')
				msg += '* Bank Name is required.\n';
			if(trim(lbxBranchID.value) == '')
				msg += '* Bank Branch Name is required.\n';
			if(trim(bankAccountNo.value) == '')
				msg += '* Bank Account No is required.\n';
			if(trim(ifscCode.value) == '')
				msg += '* IFSC Code is required.\n';
			if(trim(micrCode.value) == '')
				msg += '* MICR Code is required.\n';
			
			if(msg.match("Partner")){
				document.getElementById("bpButton").focus();
			}else if(msg.match("Type")){
				employeeType.focus();
			}else if(msg.match("Name")){
				employeeName.focus();
			}else if(msg.match("Bank Name")){
				 document.getElementById("bankButton").focus();
			}else if(msg.match("Branch Name")){
				 document.getElementById("branchButton").focus();
			}else if(msg.match("Account")){
				bankAccountNo.focus();
			}else if(msg.match("IFSC")){
				ifscCode.focus();
			}else if(msg.match("MICR")){
				micrCode.focus();
			}
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
		
			if(trim(employeeName.value) != ''){
				if (!ck_alphaNumeric.test(employeeName.value)) {
					alert("* Employee Name is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }}
			if(trim(bankAccountNo.value) != ''){
				if (!ck_alphaNumeric.test(bankAccountNo.value)) {
					alert("* Bank Account No. is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }
			}
		}
	
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("salesExecutiveMasterForm").action=sourcepath+"/salesExecutiveMasterAdd.do?method=updateSalesExecutive";	
		document.getElementById("processingImage").style.display = '';
		document.getElementById("salesExecutiveMasterForm").submit();
		return true;
		
	}

  

function fnSaveSalesExecutiveMaster(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	var businessPartnerName = document.getElementById("lbxBusinessPartnerId");
	var employeeType = document.getElementById("employeeType");
	var employeeName=document.getElementById('employeeName');	
	var lbxBankID=document.getElementById('lbxBankID');	
	var lbxBranchID=document.getElementById('lbxBranchID');	
	var bankAccountNo=document.getElementById('bankAccountNo');
	var ifscCode=document.getElementById('ifscCode');
	var micrCode=document.getElementById('micrCode');
	if(trim(businessPartnerName.value) == ''||trim(employeeType.value) == ''||trim(employeeName.value) == '' 
		||trim(lbxBankID.value) == ''||trim(lbxBranchID.value) == ''||trim(bankAccountNo.value) == ''||trim(ifscCode.value) == ''||trim(micrCode.value) == '')
	{
			var msg= '';
			if(trim(businessPartnerName.value) == '')
				msg += '* Business Partner Name is required.\n';
			if(trim(employeeType.value) == '')
				msg += '* Employee Type is required.\n';	
			if(trim(employeeName.value) == '')
				msg += '* Employee Name is required.\n';
			if(trim(lbxBankID.value) == '')
				msg += '* Bank Name is required.\n';
			if(trim(lbxBranchID.value) == '')
				msg += '* Bank Branch Name is required.\n';
			if(trim(bankAccountNo.value) == '')
				msg += '* Bank Account No is required.\n';
			if(trim(ifscCode.value) == '')
				msg += '* IFSC Code is required.\n';
			if(trim(micrCode.value) == '')
				msg += '* MICR Code is required.\n';
			
			if(msg.match("Partner")){
				document.getElementById("bpButton").focus();
			}else if(msg.match("Type")){
				employeeType.focus();
			}else if(msg.match("Name")){
				employeeName.focus();
			}else if(msg.match("Bank Name")){
				 document.getElementById("bankButton").focus();
			}else if(msg.match("Branch Name")){
				 document.getElementById("branchButton").focus();
			}else if(msg.match("Account")){
				bankAccountNo.focus();
			}else if(msg.match("IFSC")){
				ifscCode.focus();
			}else if(msg.match("MICR")){
				micrCode.focus();
			}
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
		
			if(trim(employeeName.value) != ''){
				if (!ck_alphaNumeric.test(employeeName.value)) {
					alert("* Employee Name is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }}
			if(trim(bankAccountNo.value) != ''){
				if (!ck_alphaNumeric.test(bankAccountNo.value)) {
					alert("* Bank Account No. is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }
			}
		}
			
	document.getElementById("salesExecutiveMasterForm").action=sourcepath+"/salesExecutiveMasterAdd.do?method=saveSalesExecutive";	
    document.getElementById("processingImage").style.display = '';
	document.getElementById("salesExecutiveMasterForm").submit();
	return true;
	
  }

	
	function searchSalesExecutive(){
		
	
		var sourcepath=document.getElementById("contextPath").value;
		var lbxBusinessPartnerId=document.getElementById("lbxBusinessPartnerId").value;
		var employeeType=document.getElementById("employeeType").value;
		var employeeName=document.getElementById("employeeName").value;
		var bankAccountNo=document.getElementById("bankAccountNo").value;
		
		if(lbxBusinessPartnerId!=''||employeeType!=''||employeeName!=''||bankAccountNo!='')
		{
		
			document.getElementById("salesExecutiveMaster").action=sourcepath+"/salesExecutiveMaster.do?method=addSalesExecutive";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("salesExecutiveMaster").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
