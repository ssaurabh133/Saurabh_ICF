function fnSearch(val,val1,val2){ 
	    if(document.getElementById("roleId").value==''&& document.getElementById("moduleId").value=='')
		{
			alert(val);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("roleId").value=='')
		{
			alert(val1);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("moduleId").value=='')
		{
			alert(val2);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	    document.getElementById("roleAccessMasterForm").action="roleAccessMaster.do?method=searchRoleAccess";
	    document.getElementById("roleAccessMasterForm").submit();
	}


function saveRoleAccess(val,val1,val2)
{
	//alert("in saveRoleAccess");
	var sourcepath=document.getElementById("path").value;
	 if(document.getElementById("roleId").value==''&& document.getElementById("moduleId").value=='')
		{
			alert(val);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("roleId").value=='')
		{
			alert(val1);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("moduleId").value=='')
		{
			alert(val2);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else{
	document.getElementById("roleAccessMasterForm").action=sourcepath+"/roleAccessMaster.do?method=saveRoleAccessDetails";
	document.getElementById("roleAccessMasterForm").submit();
	return true;
	    }
}	
function disableSave()
{
   //alert("disableSave");
	document.getElementById("save").disabled=true;	
}

