
function fnUpdateUser(){
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;	
	var user='';
	var newcheckval='';
	var chkcount=document.getElementsByName("chkCases");
	var sourcepath=document.getElementById("contextPath").value;
for(i=1	;i<rowCount;i++){

	if(document.getElementById('chkCases'+i).checked == true){
		
			newcheckval=newcheckval+(document.getElementById('chkCases'+i).value)+"/" ;	
}
	
		
}
if(newcheckval!=''){
    document.getElementById("hidCheck").value=newcheckval;
    document.getElementById("unlockUserMasterForm").action=sourcepath+"unlockUserUpdate.do?method=updateUser";
    document.getElementById("unlockUserMasterForm").submit();
    return true;
}else{
	alert("Please select a user to unlock");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
}	
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();


}



function fnSearch(){ 
	
	var userName=document.getElementById("userName").value;
	var sourcepath=document.getElementById("contextPath").value;
	if((document.getElementById("userName").value=='' )&&  (document.getElementById("userId").value=='' ))
		{
			alert("Please enter userId or userName");
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}else if((userName!='' ) && (userName.length>=3))
		{
	    document.getElementById("unlockUserMasterForm").action=sourcepath+"/unlockUser.do";
		    document.getElementById("unlockUserMasterForm").submit(); 
			return true;
		}
		else if(userName!='' ){
			alert("Please Enter atleast 3 characters of User Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
		else if(userId!='' ){
			 document.getElementById("unlockUserMasterForm").action=sourcepath+"/unlockUser.do";
			    document.getElementById("unlockUserMasterForm").submit(); 
				return true;
		}
	}	

