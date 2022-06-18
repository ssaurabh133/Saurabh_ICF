function trim(str) {
	return ltrim(rtrim(str));
	}

function newAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("gcdGroupMasterSearch").action=sourcepath+"/gcdGroupMasterSearch.do?method=addGroupMasterDetails";
	document.getElementById("gcdGroupMasterSearch").submit();
	
}

function fnSearch(val){ 
	
    
    if(document.getElementById("groupSearchDescription").value==''&& document.getElementById("gcdgroupId").value=='')
    {
    	alert(val);
    		document.getElementById("save").removeAttribute("disabled","true");
      	return false;
    }
    document.getElementById("gcdGroupMasterSearch").action="gcdGroupMasterSearchBehind.do";
    document.getElementById("gcdGroupMasterSearch").submit();
	
}

function modifyDetails(b)
{
	//alert("GroupDescription... "+b);
	var sourcepath=document.getElementById("path").value;
	//alert("sourcepath"+sourcepath);
	document.getElementById("gcdGroupMasterSearch").action=sourcepath+"/gcdGroupMasterSearch.do?method=modifyDetails&gcdgroupId="+b;
	document.getElementById("gcdGroupMasterSearch").submit();
	return true;
}

function gcdGroupModify(b)
{
	var groupDescription = document.getElementById("groupDescription");
	var groupExposureLimit = document.getElementById("groupExposureLimit");

	
	if(trim(groupDescription.value) == ''||trim(groupExposureLimit.value) == ''){
			var msg= '';
			if(trim(groupDescription.value) == '')
				msg += '* Group Description is required.\n';
			if(trim(groupExposureLimit.value) == '')
				msg += '* Group Exposure Limit is required.\n';
			
			if(msg.match("Description")){
				groupDescription.focus();
			}else if(msg.match("Exposure")){
				groupExposureLimit.focus();
			}
			
			alert(msg);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
		   document.getElementById("gcdGroupMasterForm").action="gcdGroupMasterAction.do?method=saveModifyDetails&gcdgroupId="+b;
		   document.getElementById("gcdGroupMasterForm").submit();
		   return true;
	}
	  	
}

function gcdGroupSave()
{

	var groupDescription = document.getElementById("groupDescription");
	var groupExposureLimit = document.getElementById("groupExposureLimit");

	
	if(trim(groupDescription.value) == ''||trim(groupExposureLimit.value) == ''){
			var msg= '';
			if(trim(groupDescription.value) == '')
				msg += '* Group Description is required.\n';
			if(trim(groupExposureLimit.value) == '')
				msg += '* Group Exposure Limit is required.\n';
			
			if(msg.match("Description")){
				groupDescription.focus();
			}else if(msg.match("Exposure")){
				groupExposureLimit.focus();
			}
			
			alert(msg);
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
	
		   document.getElementById("gcdGroupMasterForm").action="gcdGroupMasterAction.do?method=saveGcdGroup";
		   document.getElementById("gcdGroupMasterForm").submit(); 
	   }
	 	
}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}