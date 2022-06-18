function newAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("roleMasterSearchForm").action=sourcepath+"/roleMasterSearch.do?method=openAddRole";
	document.getElementById("roleMasterSearchForm").submit();
	
}
function saveRole()
{
	
	var sourcepath=document.getElementById("path").value;
	if(validateRoleMasterAddDynaValidatorForm(document.getElementById("roleMasterForm"))){
	document.getElementById("roleMasterForm").action=sourcepath+"/roleMasterAdd.do?method=saveRoleDetails";
	document.getElementById("roleMasterForm").submit();
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
	document.getElementById("roleMasterForm").action=sourcepath+"/roleMasterSearch.do?method=openAddrole&roleDesc="+a;
	document.getElementById("roleMasterForm").submit();
	
}
function editRole(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("roleMasterSearchForm").action=sourcepath+"/roleMasterSearch.do?method=openEditRole&roleId="+b;
	document.getElementById("roleMasterSearchForm").submit();
	
	
}
function fnEditRole(b){
	if(validateRoleMasterAddDynaValidatorForm(document.getElementById("roleMasterForm"))){
    document.getElementById("roleMasterForm").action="roleMasterAdd.do?method=updateRole&roleId="+b;
	document.getElementById("roleMasterForm").submit();
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
		
		if(document.getElementById("roleId").value=='' && document.getElementById("roleSearchDesc").value=='')
		{
			alert(val);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		document.getElementById("roleMasterSearchForm").action="roleMasterBehind.do";
	    document.getElementById("roleMasterSearchForm").submit();
		
	}	
		
	