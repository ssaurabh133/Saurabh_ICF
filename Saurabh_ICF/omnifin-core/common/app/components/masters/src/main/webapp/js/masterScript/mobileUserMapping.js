function newAddMobileUser()
{
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("mobileUserMasterSearch").action=sourcepath+"/mobileUserMasterSearch.do?method=addmobileUser";
	document.getElementById("mobileUserMasterSearch").submit();
	
}

function mobileUserSave()
{
var ck_din = /^[0-9]*$/;
	var UserId = document.getElementById("userId");
	var Mobile = document.getElementById("mobile");
	var imeiNo = document.getElementById("imeiNo");
	var sourcepath=document.getElementById("contextPath").value;
	
	if(trim(UserId.value) == ''||trim(imeiNo.value) == ''|| trim(Mobile.value) == ''){
			var msg= '';
			if(trim(UserId.value) == '')
				msg += '* UserId is required.\n';
			if(trim(imeiNo.value) == '')
				msg += '* IMIE No. is required.\n';
			if(trim(Mobile.value) == '')
				msg += '* Mobile No. is required.\n';
			alert(msg);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	else if(!(Mobile.value.trim()).match(ck_din))
	{
		alert(" Mobile No. is invalid");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
		else if(Mobile.value.trim().length != 10)
		{
			alert(" Mobile no. should be of 10 digits");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	
		/*else if(!(imeiNo.value.trim()).match(ck_din))
		{
			alert(" IMIE No. is invalid");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}*/
		else if(imeiNo.value.trim().length > 50)
		{
			alert(" ImeiNo should be less than or equal to 50 digits");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	
	else{
	
		   document.getElementById("mobileUserMasterSearch").action=sourcepath+"/mobileUserMasterSearch.do?method=savemobileUser";
		   document.getElementById("mobileUserMasterSearch").submit(); 
	   }
}


	function searchUser(){ 
		var sourcepath=document.getElementById("contextPath").value;
		var UserId = document.getElementById("mobileUserId");
		var userName = document.getElementById("mobileUserNameSearch");
		if(trim(UserId.value) == ''&& trim(userName.value) == ''){
			
			alert("Please enter atleast one field");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
	    document.getElementById("mobileUserMasterSearch").action=sourcepath+"/mobileUserMasterSearch.do?method=searchmobileUser";
	    document.getElementById("mobileUserMasterSearch").submit();
		}
	}
	
	
	function mobileUserModify()
	{
		var ck_din = /^[0-9]*$/;
		var UserId = document.getElementById("userId");
		var imeiNo = document.getElementById("imeiNo");
		var Mobile = document.getElementById("mobile");
		var sourcepath=document.getElementById("contextPath").value;

		if(trim(UserId.value) == ''||trim(imeiNo.value) == ''|| trim(Mobile.value) == ''){
				var msg= '';
				if(trim(UserId.value) == '')
					msg += '* UserId is required.\n';
				if(trim(imeiNo.value) == '')
					msg += '* IMIE No. is required.\n';
				if(trim(Mobile.value) == '')
					msg += '* Mobile No. is required.\n';
				alert(msg);
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		else if(!(trim(Mobile.value)).match(ck_din))
		{
			alert(" Mobile No. is invalid");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
			else if ((Mobile.value.trim().length != 10))
			{
				alert(" Mobile no. should be of 10 digits");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		/*else if(!(imeiNo.value.trim()).match(ck_din))
		{
			alert(" IMIE No. is invalid");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}*/
		else if(imeiNo.value.trim().length > 50)
		{
			alert(" ImeiNo should be less than or equal to 50 digits");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		
	
		else{
		
			   document.getElementById("mobileUserMasterSearch").action=sourcepath+"/mobileUserMasterModify.do";
			   document.getElementById("mobileUserMasterSearch").submit(); 
		   }
	}
