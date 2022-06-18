function trim(str) {
	return ltrim(rtrim(str));
	}

function ltrim(str) { 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
	}
	function rtrim(str) {
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
	}

function newAdd()
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("branchMasterSearchForm").action=sourcepath+"/branchMasterSearch.do?method=openAddBranch";
	document.getElementById("branchMasterSearchForm").submit();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;

}
function saveBranch()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var DListValues ="";
	var branchShortCode = document.getElementById("branchShortCode");
	var branchDesc = document.getElementById("branchDesc");
	var companyId = document.getElementById("companyId");
	var regionId = document.getElementById("regionId");
	var recVal = branchShortCode.value;
	var set=document.getElementById('areaCodename');
	var state=document.getElementById("state");
	/*alert("areaCodename-->"+areaCodename);*/
	var areaCodelen = document.getElementById('areaCodename').options.length;
	
	if(trim(branchShortCode.value) == '' || trim(recVal).length < 3 || trim(branchDesc.value) == ''||trim(companyId.value) == ''||trim(regionId.value) == ''||trim(state.value) == ''){
		var msg= '';
		if(trim(branchShortCode.value) == '')
			msg += '* Branch Short Code is required.\n';
		if(trim(branchDesc.value) == '')
			msg += '* Branch Description is required.\n';
		if(trim(companyId.value) == '')
			msg += '* Company is required.\n';
		if(trim(regionId.value) == '')
			msg += '* Region is required.\n';
		if(trim(recVal).length < 3 && branchShortCode.value != "")
			msg += '* Branch Short Code cannot be less than 3 characters.\n'; 
		if(trim(state.value) == '')
			msg += '* State is required.';
		
		
		if(msg.match("Short")){
			branchShortCode.focus();
		}else if(msg.match("Description")){
			branchDesc.focus();
		}else if(msg.match("Company")){
			document.getElementById("companyButton").focus();
		}else if(msg.match("Region")){
			document.getElementById("regionButton").focus();
		}else if(msg.match("characters")){
			branchShortCode.focus();
		}
		else if(msg.match("State is required")){
			document.getElementById("stateButton").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		if(areaCodelen>0){
		for (var iter =0 ; iter < areaCodelen; iter++)
	    {
/*			alert("areaCodelen-->"+areaCodelen);*/
			DListValues = DListValues+set.options[iter].value+"/";
		/*	alert("DListValues"+DListValues);*/
	    } 
		}
		document.getElementById("branchMasterForm").action=sourcepath+"/branchMasterAdd.do?method=saveBranchDetails&areaCode="+DListValues;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("branchMasterForm").submit();
		return true;
	}

}
function newpage(a)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("branchMasterForm").action=sourcepath+"/branchMasterSearch.do?method=openAddBranch&branchDesc="+a;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("branchMasterForm").submit();
	
}
function editBranch(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("branchMasterSearchForm").action=sourcepath+"/branchMasterSearch.do?method=openEditBranch&branchId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("branchMasterSearchForm").submit();
	
	
}
function fnEditBranch(){

	DisButClass.prototype.DisButMethod();
	var branchShortCode = document.getElementById("branchShortCode");
	var branchDesc = document.getElementById("branchDesc");
	var companyId = document.getElementById("companyId");
	var regionId = document.getElementById("regionId");
	var set=document.getElementById('areaCodename');
	var state=document.getElementById("state");
	var DListValues ="";
	/*alert("areaCodename-->"+areaCodename);*/
	var areaCodelen = document.getElementById('areaCodename').options.length;
	if(trim(branchShortCode.value) == ''||trim(branchDesc.value) == ''||trim(companyId.value) == ''||trim(regionId.value) == ''||trim(state.value)==''){
		var msg= '';
		if(trim(branchShortCode.value) == '')
			msg += '* Branch Short Code is required.\n';
		if(trim(branchDesc.value) == '')
			msg += '* Branch Description is required.\n';
		if(trim(companyId.value) == '')
			msg += '* Company is required.\n';
		if(trim(regionId.value) == '')
			msg += '* Region is required.\n';
		if(trim(state.value)=='')
			msg +='* State is required.\n';
		
		if(msg.match("Short")){
			branchShortCode.focus();
		}else if(msg.match("Description")){
			branchDesc.focus();
		}else if(msg.match("Company")){
			document.getElementById("companyButton").focus();
		}else if(msg.match("Region")){
			document.getElementById("regionButton").focus();
		}
		else if(msg.match("State is required")){
			document.getElementById("state").focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		if(areaCodelen>0){
			for (var iter =0 ; iter < areaCodelen; iter++)
		    {
				/*alert("areaCodelen-->"+areaCodelen);*/
//				alert("set-->"+set);
				DListValues = DListValues+set.options[iter].value+"/";
		/*		alert("DListValues"+DListValues);*/
		    } 
			}
		var strCode='';
		var count=0;
		if(DListValues.length>count){
		    while ( count<DListValues.length) {
		    	
		    				strCode=strCode+DListValues[count];
							count =count+1;	
							
		    	}
		    }
			document.getElementById("areaCodename").value=strCode;
		    var lbxareaCodeVal = document.getElementById("lbxareaCodeVal").value;
		  var areaCode = new Array();
		  areaCode=DListValues.split('/');
		 
	    document.getElementById("branchMasterForm").action="branchMasterAdd.do?method=updateBranch&areaCode="+DListValues;
	    document.getElementById("processingImage").style.display = '';
		document.getElementById("branchMasterForm").submit();
	}

}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("branchId").value=='' && document.getElementById("branchSearchDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		 document.getElementById("branchMasterSearchForm").action="branchMasterBehind.do";
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("branchMasterSearchForm").submit();
		 return true;
	}
	    
		
}	
function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("branchMasterSearchForm").action=sourcepath+"/branchMasterSearch.do?method=openAddBranch";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("branchMasterSearchForm").submit();
	
}
		
	