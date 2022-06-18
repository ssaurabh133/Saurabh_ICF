function trim(str) {
	return ltrim(rtrim(str));
	}

function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("documentMasterSearchForm").action=sourcepath+"/documentMasterSearch.do?method=openAddDocument";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("documentMasterSearchForm").submit();
	
}
function saveDocument()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	var documentDesc = document.getElementById("documentDesc");
	
	if(trim(documentDesc.value) == ''){
		var msg= '';
		
		if(trim(documentDesc.value) == '')
			msg += '* Document Description is required.\n';
		
		if(msg.match("Description")){
			documentDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("documentMasterForm").action=sourcepath+"/documentMasterAdd.do?method=saveDocumentDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("documentMasterForm").submit();
		return true;
	}
}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("documentMasterForm").action=sourcepath+"/documentMasterSearch.do?method=openAddDocument&documentDesc="+a;
	document.getElementById("documentMasterForm").submit();
	
}
function editDocument(b)
{
	//alert("editDocument: "+b);
	var sourcepath=document.getElementById("path").value;
	document.getElementById("documentMasterSearchForm").action=sourcepath+"/documentMasterSearch.do?method=openEditDocument&documentId="+b;
	document.getElementById("documentMasterSearchForm").submit();
	return true;
	
}
function fnEditDocument(docId){
DisButClass.prototype.DisButMethod();	
var documentDesc = document.getElementById("documentDesc");
	
	if(trim(documentDesc.value) == ''){
		var msg= '';
		
		if(trim(documentDesc.value) == '')
			msg += '* Document Description is required.\n';
		
		if(msg.match("Description")){
			documentDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
	    document.getElementById("documentMasterForm").action="documentMasterAdd.do?method=updateDocument&docId="+docId;
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("documentMasterForm").submit();
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
		DisButClass.prototype.DisButMethod();
	    if(document.getElementById("documentId").value==''&& document.getElementById("documentSearchDesc").value=='')
	    {
	    	alert(val);
	    	DisButClass.prototype.EnbButMethod();
	    	//document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
	    }
	    document.getElementById("documentMasterSearchForm").action="documentMasterBehind.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("documentMasterSearchForm").submit();
		return true;
	}	
		
	