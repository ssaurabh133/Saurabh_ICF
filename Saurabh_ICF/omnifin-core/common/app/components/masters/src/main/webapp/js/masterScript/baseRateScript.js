$(function() {
			var contextPath =document.getElementById('contextPath').value ;
		$("#effectiveFromDate").datepicker({
		format: "%Y-%m-%d %H:%i:%s %E %#",
        formatUtcOffset: "%: (%@)",
        placement: "inline",

		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1950:2010',
	     showOn: 'both',
		 buttonImage: contextPath+'/images/calendar.gif',
		 buttonImageOnly: true
		
			});
     	});

function trim(str) {
	return ltrim(rtrim(str));
	}

function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("baseRateMasterSearchForm").action=sourcepath+"/baseRateMasterSearch.do?method=openAddBaseRate";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("baseRateMasterSearchForm").submit();
	return true;
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	
}
function saveBaseRate()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	var baseRateType = document.getElementById("baseRateType");
	var effectiveFromDate = document.getElementById("effectiveFromDate");
	var baseRate = document.getElementById("baseRate");
	var baseRateDesc = document.getElementById("baseRateDesc");
	
	if(trim(baseRateType.value) == ''||trim(effectiveFromDate.value) == ''||trim(baseRate.value) == ''||trim(baseRateDesc.value) == ''){
		var msg= '';
		if(trim(baseRateType.value) == '')
			//msg += '* Base Rate Type is required.\n';
			msg += '* BPLR Type is required.\n';
		if(trim(effectiveFromDate.value) == '')
			msg += '* Effective From date is required.\n';
		if(trim(baseRate.value) == '')
			//msg += '* Base rate is required.\n';
		   msg += '* BPLR is required.\n';
		if(trim(baseRateDesc.value) == '')
			//msg += '* Base Rate Description is required.\n';
		   msg += '* BPLR Description is required.\n';
		
		if(msg.match("Type")){
			baseRateType.focus();
		}else if(msg.match("date")){
			effectiveFromDate.focus();
		}else if(msg.match(/rate/g)){
			baseRate.focus();
		}else if(msg.match("Description")){
			baseRateDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		var ck_numeric = /^[A-Za-z_0-9]{1,50}$/;
		var msg= '';
		if (!ck_numeric.test(baseRateType.value)) {
			
	    	if(trim(baseRateType.value) != ''){
		   	  msg += "* BPLR Type is invalid.\n";
		   	  baseRateType.focus();
	    	}		    	
	    	
	    	alert(msg);
	    	DisButClass.prototype.EnbButMethod();
	    	//document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
	    	}
		document.getElementById("baseRateMasterForm").action=sourcepath+"/baseRateMasterAdd.do?method=saveBaseRateDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("baseRateMasterForm").submit();
		return true;
	}

}

function saveGoldOrnament()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	var baseRateType = document.getElementById("baseRateType");
	var effectiveFromDate = document.getElementById("effectiveFromDate");
	var baseRate = document.getElementById("baseRate");
	var baseRateDesc = document.getElementById("baseRateDesc");
	
	if(trim(baseRateType.value) == ''||trim(effectiveFromDate.value) == ''||trim(baseRate.value) == ''||trim(baseRateDesc.value) == ''){
		var msg= '';
		if(trim(baseRateType.value) == '')
			//msg += '* Base Rate Type is required.\n';
			msg += '* BPLR Type is required.\n';
		if(trim(effectiveFromDate.value) == '')
			msg += '* Effective From date is required.\n';
		if(trim(baseRate.value) == '')
			//msg += '* Base rate is required.\n';
		   msg += '* BPLR is required.\n';
		if(trim(baseRateDesc.value) == '')
			//msg += '* Base Rate Description is required.\n';
		   msg += '* BPLR Description is required.\n';
		
		if(msg.match("Type")){
			baseRateType.focus();
		}else if(msg.match("date")){
			effectiveFromDate.focus();
		}else if(msg.match(/rate/g)){
			baseRate.focus();
		}else if(msg.match("Description")){
			baseRateDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		var ck_numeric = /^[A-Za-z_0-9]{1,50}$/;
		var msg= '';
		if (!ck_numeric.test(baseRateType.value)) {
			
	    	if(trim(baseRateType.value) != ''){
		   	  msg += "* BPLR Type is invalid.\n";
		   	  baseRateType.focus();
	    	}		    	
	    	
	    	alert(msg);
	    	DisButClass.prototype.EnbButMethod();
	    	//document.getElementById("save").removeAttribute("disabled","true");
	    	return false;
	    	}
		document.getElementById("baseRateMasterForm").action=sourcepath+"/baseRateMasterAdd.do?method=saveBaseRateDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("baseRateMasterForm").submit();
		return true;
	}

}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("baseRateMasterForm").action=sourcepath+"/baseRateMasterSearch.do?method=openAddBaseRate&baseRateType="+a;
	document.getElementById("baseRateMasterForm").submit();
	
}
function editBaseRate(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("baseRateMasterSearchForm").action=sourcepath+"/baseRateMasterSearch.do?method=openEditBaseRate&baseRateTypeSearch="+escape(b);
	document.getElementById("baseRateMasterSearchForm").submit();
	
	
}
function fnEditBaseRate(b){
	
	DisButClass.prototype.DisButMethod();	
	var baseRateDesc = document.getElementById("baseRateDesc");
	
	if(trim(baseRateDesc.value) == ''){
		var msg= '';
		
		if(trim(baseRateDesc.value) == '')
			msg += '* BPLR Description is required.\n';
		
		if(msg.match("Description")){
			baseRateDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
	    document.getElementById("baseRateMasterForm").action="baseRateMasterAdd.do?method=updateBaseRate&baseRateType="+escape(b);
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("baseRateMasterForm").submit();
		return true;
	}

}

function fnEditGoldOrnament(b){
	
	DisButClass.prototype.DisButMethod();	
	var baseRateDesc = document.getElementById("baseRateDesc");
	
	if(trim(baseRateDesc.value) == ''){
		var msg= '';
		
		if(trim(baseRateDesc.value) == '')
			msg += '* BPLR Description is required.\n';
		
		if(msg.match("Description")){
			baseRateDesc.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
	    document.getElementById("baseRateMasterForm").action="baseRateMasterAdd.do?method=updateBaseRate&baseRateType="+escape(b);
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("baseRateMasterForm").submit();
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
	    if(document.getElementById("baseRateTypeSearch").value=='' && document.getElementById("effectiveFromDateSearch").value==''&& document.getElementById("baseRateDescSearch").value=='')
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
            return false;
		}
	    else{
	    document.getElementById("baseRateMasterSearchForm").action="baseRateMasterBehind.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("baseRateMasterSearchForm").submit();
		return true;
	    }
	}	
		
	