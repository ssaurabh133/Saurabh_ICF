function newAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("gcdGroupMasterSearch").action=sourcepath+"/gcdGroupMasterSearch.do?method=addGroupMasterDetails";
	document.getElementById("gcdGroupMasterSearch").submit();
	
}

function fnSearch(){ 
	//alert("fnSearch");

    document.getElementById("gcdGroupMasterSearch").action="gcdGroupMasterSearch.do?method=searchGroupDesc";
    document.getElementById("gcdGroupMasterSearch").submit();
	
}

function modifyDetails(b)
{
	//alert("GroupDescription... "+b);
	var sourcepath=document.getElementById("path").value;
	//alert("sourcepath"+sourcepath);
	document.getElementById("gcdGroupMasterSearch").action=sourcepath+"/gcdGroupMasterSearch.do?method=modifyDetails&groupDesc="+b;
	document.getElementById("gcdGroupMasterSearch").submit();
	return true;
}

function gcdGroupModify(b)
{
	//alert("In gcdGroupModify");
	
	//alert("In gcdGroupModify sourcepath" +document.getElementById("contextPath").value);

	//alert("ok");
	var sourcepath=document.getElementById("contextPath").value;
	if (validateGcdGroupMasterDynaValidatorForm(document.getElementById("gcdGroupMasterForm"))){
		
		   document.getElementById("gcdGroupMasterForm").action=sourcepath+"/gcdGroupMasterAction.do?method=saveModifyDetails&groupDesc="+b;
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("gcdGroupMasterForm").submit();
		   return true;
		   //alert("ok1");
	}
	  	
}

function gcdGroupSave()
{
	//alert("In gcdGroupSave");
	
	//alert("In gcdGroupSave sourcepath" +document.getElementById("contextPath").value);
	var sourcepath=document.getElementById("contextPath").value;
	if (validateGcdGroupMasterDynaValidatorForm(document.getElementById("gcdGroupMasterForm"))){
	
		   document.getElementById("gcdGroupMasterForm").action=sourcepath+"/gcdGroupMasterAction.do?method=saveGcdGroup";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("gcdGroupMasterForm").submit();
		   
		   return true;
	   }
	 	
}

function fnChangeCase(formName,fieldName)
{
	document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
	return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}