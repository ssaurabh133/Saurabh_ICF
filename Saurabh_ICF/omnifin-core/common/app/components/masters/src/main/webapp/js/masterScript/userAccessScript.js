function newAddUserAccess()

{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userAccessMasterSearchForm").action=sourcepath+"/userAccessMasterSearch.do?method=openAddUserAccess";
	document.getElementById("userAccessMasterSearchForm").submit();
	
}
function saveUserAccess()
{
	//alert("saveUserAccess");
	var sourcepath=document.getElementById("contextPath").value;
    if(validateUserAccessMasterAddDynaValidatorForm(document.getElementById("userAccessMasterForm"))){
	document.getElementById("userAccessMasterForm").action=sourcepath+"/userAccessMasterAdd.do?method=saveUserAccessDetails";
	document.getElementById("userAccessMasterForm").submit();
	return true;
	}
	else{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userAccessMasterForm").action=sourcepath+"/userAccessMasterSearch.do?method=openAddUserAccess&userId="+a;
	document.getElementById("userAccessMasterForm").submit();
	
}
function editUserAccess(a,b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userAccessMasterSearchForm").action=sourcepath+"/userAccessMasterSearch.do?method=openEditUserAccess&userAccessId="+a+"&moduleSearchId="+b;
	document.getElementById("userAccessMasterSearchForm").submit();
	
	
}

function fnEditUserAccess(b){
	if(validateUserAccessMasterAddDynaValidatorForm(document.getElementById("userAccessMasterForm"))){
    document.getElementById("userAccessMasterForm").action="userAccessMasterAdd.do?method=updateUserAccess&userId="+b;
	document.getElementById("userAccessMasterForm").submit();
	}
	else{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
	//alert("in fnSearch");
	var sourcepath=document.getElementById("path").value;

	    
	    if(document.getElementById('userSearchId').value=='' &&document.getElementById('moduleSearchId').value==''&&document.getElementById('roleSearchId').value=='')
	    {
	    	alert(val);
	    	document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
	    }
	    document.getElementById("userAccessMasterSearchForm").action="userAccessMasterBehind.do";
	    document.getElementById("userAccessMasterSearchForm").submit();
	}	

function fnval()
{
	document.getElementById("roleId").value=="";
    return true;
}
		
	