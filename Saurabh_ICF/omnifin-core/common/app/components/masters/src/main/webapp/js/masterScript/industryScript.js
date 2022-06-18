function trim(str) {
	return ltrim(rtrim(str));
	}
function isWhitespace(charToCheck) 
{
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
}
function ltrim(str) 
{ 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
}
function rtrim(str) 
{
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
}


function newAddIndustry()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("industryMasterSearchForm").action=sourcepath+"/industryMasterSearch.do?method=openAddIndustry";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("industryMasterSearchForm").submit();
	
}
function saveIndustry()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
var industryDesc = document.getElementById("industryDesc");

	
	if(trim(industryDesc.value) == ''){
			var msg= '';
			if(trim(industryDesc.value) == '')
				msg += '* Dealer Description is required.\n';
			
			if(msg.match("Description")){
				industryDesc.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
		document.getElementById("industryMasterForm").action=sourcepath+"/industryMasterAdd.do?method=saveIndustryDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("industryMasterForm").submit();
		return true;
		}

}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("industryMasterForm").action=sourcepath+"/industryMasterSearch.do?method=openAddIndustry&industryDesc="+a;
	document.getElementById("industryMasterForm").submit();
	
}
function editIndustry(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("industryMasterSearchForm").action=sourcepath+"/industryMasterSearch.do?method=openEditIndustry&industryId="+b;
	document.getElementById("industryMasterSearchForm").submit();
	
}
function fnEditIndustry(b){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	var industryDesc = document.getElementById("industryDesc");

	
	if(trim(industryDesc.value) == ''){
			var msg= '';
			if(trim(industryDesc.value) == '')
				msg += '* Dealer Description is required.\n';
			
			if(msg.match("Description")){
				industryDesc.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
		    document.getElementById("industryMasterForm").action=sourcepath+"/industryMasterAdd.do?method=updateIndustry&industryId="+b;
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("industryMasterForm").submit();
		}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
		
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("industryId").value=='' && document.getElementById("industrySearchDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("industryMasterSearchForm").action="industryMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("industryMasterSearchForm").submit();
}