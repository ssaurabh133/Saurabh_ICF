function trim(str) {
	return ltrim(rtrim(str));
	}

function newAddSubIndustry()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("subIndustryMasterSearchForm").action=sourcepath+"/subIndustryMasterSearch.do?method=openAddSubIndustry";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("subIndustryMasterSearchForm").submit();

}

function saveSubIndustry()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	var subIndustryDesc = document.getElementById("subIndustryDesc");
	var industryId = document.getElementById("industryId");

	
	if(trim(subIndustryDesc.value) == ''||trim(industryId.value) == ''){
			var msg= '';
			if(trim(subIndustryDesc.value) == '')
				msg += '* Sub industry Description is required.\n';
			if(trim(industryId.value) == '')
				msg += '* Industry is required.\n';
			
			if(msg.match("Description")){
				subIndustryDesc.focus();
			}else if(msg.match(/Industry/g)){
				document.getElementById("insusButton").focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			document.getElementById("subIndustryMasterForm").action=sourcepath+"/subIndustryMasterAdd.do?method=saveSubIndustryDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("subIndustryMasterForm").submit();
			return true;
		}
}

function newpage(a)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("subIndustryMasterForm").action=sourcepath+"/subIndustryMasterSearch.do?method=openAddSubIndustry&subIndustryDesc="+a;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("subIndustryMasterForm").submit();
	
}
function editSubIndustry(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("subIndustryMasterSearchForm").action=sourcepath+"/subIndustryMasterSearch.do?method=openEditSubIndustry&subIndustryId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("subIndustryMasterSearchForm").submit();
	
}
function fnEditSubIndustry(b){

	DisButClass.prototype.DisButMethod();
	var subIndustryDesc = document.getElementById("subIndustryDesc");
	var industryId = document.getElementById("industryId");

	
	if(trim(subIndustryDesc.value) == ''||trim(industryId.value) == ''){
			var msg= '';
			if(trim(subIndustryDesc.value) == '')
				msg += '* Sub industry Description is required.\n';
			if(trim(industryId.value) == '')
				msg += '* Industry is required.\n';
			
			if(msg.match("Description")){
				subIndustryDesc.focus();
			}else if(msg.match(/Industry/g)){
				document.getElementById("insusButton").focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			 document.getElementById("subIndustryMasterForm").action="subIndustryMasterAdd.do?method=updateSubIndustry&subIndustryId="+b;
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("subIndustryMasterForm").submit();
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
	if(document.getElementById("subIndustryId").value=='' && document.getElementById("subIndustrySearchDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");

	}
	document.getElementById("subIndustryMasterSearchForm").action="subIndustryMasterBehind.do";
	 document.getElementById("processingImage").style.display = '';
	document.getElementById("subIndustryMasterSearchForm").submit();

}