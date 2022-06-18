function trim(str) {
	return ltrim(rtrim(str));
	}


function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bankAccountMasterSearch").action=sourcepath+"/bankAccountMasterSearch.do?method=openAddBankAccount";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("bankAccountMasterSearch").submit();
	return true;
	document.getElementById("save").removeAttribute("disabled","true");
	return false;

}
function saveBankAccount()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	var bankCode = document.getElementById("bankCode");
	var bankBranchName = document.getElementById("bankBranchName");
	var accountNo = document.getElementById("accountNo");
	var glCode = document.getElementById("glCode");

	
	if(trim(bankCode.value) == ''||trim(bankBranchName.value) == ''||trim(accountNo.value) == '' || trim(glCode.value) == ''){
		var msg= '';
		if(trim(bankCode.value) == '')
			msg += '* Bank is required.\n';
		if(trim(bankBranchName.value) == '')
			msg += '* Branch Name is required.\n';
		if(trim(accountNo.value) == '')
			msg += '* Account No is required.\n';
		if(trim(glCode.value) == '')
			msg += '* GL Code is required.';
		
		if(msg.match("Bank")){
			document.getElementById("bankButton").focus();
		}else if(msg.match("Branch")){
			document.getElementById("bankBranchButton").focus();
		}else if(msg.match("Account")){
			accountNo.focus();
		}else if(msg.match("Code")){
			glCode.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		var ck_numeric = /^[A-Za-z0-9]{1,25}$/;
		if (!ck_numeric.test(accountNo.value)) {
	    	if(trim(accountNo.value) != ''){
		   	  alert("* Account No is invalid.");
		   	  accountNo.focus();
	    	}
	    	DisButClass.prototype.EnbButMethod();
	    	//document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
		 }
		document.getElementById("bankAccountMaster").action=sourcepath+"/bankAccountMasterAdd.do?method=saveBankAccountDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("bankAccountMaster").submit();
		return true;
	}

}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bankAccountMaster").action=sourcepath+"/bankAccountMasterSearch.do?method=openAddBankAccount&bankCode="+a;
	document.getElementById("bankAccountMaster").submit();
	
}
function editBankAccount(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bankAccountMasterSearch").action=sourcepath+"/bankAccountMasterSearch.do?method=openEditBankAccount&bankSearchCode="+b;
	document.getElementById("bankAccountMasterSearch").submit();

	
}

function fnEditBankAccount(){
	DisButClass.prototype.DisButMethod();
	var glCode = document.getElementById("glCode");
	var path = document.getElementById("path").value;
	if(trim(glCode.value) == ''){
		var msg= '';
		if(trim(glCode.value) == '')
			msg += '* GL Code is required.';
		if(msg.match("Code")){
			glCode.focus();
		}
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
    	document.getElementById("bankAccountMaster").action=path+"/bankAccountMasterAdd.do?method=updateBankAccount";
    	document.getElementById("processingImage").style.display = '';
    	document.getElementById("bankAccountMaster").submit();
    	return true;
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();


}



function fnSearch(val){ 
		DisButClass.prototype.DisButMethod();
	    if(document.getElementById("bankSearchCode").value=='' && document.getElementById("bankBranchSearchName").value=='')
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else{
	    document.getElementById("bankAccountMasterSearch").action="bankAccountMasterBehind.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("bankAccountMasterSearch").submit();
		return true;
	    }
	}	

//Manish Baranwal

function drawingData()
{
	var n=document.getElementById("drawingPower").value;	
	if(n<0 ||n>100||str.match(/^(100(\.0{1,2})?|([0-9]?[0-9](\.[0-9]{1,2})))$/)){
		alert("Drawing Power in 0 to 100 % range ");
		document.getElementById("drawingPower").value="";
		
	return false;	
	}
	else
	{
	
	    return true;
	}
		
}
//code end Manish Baranwal 

