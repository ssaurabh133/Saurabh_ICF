function newAddStageType(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("stageTypeMasterForm").action=sourcepath+"/stageTypeMaster.do?method=openAddStageType";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stageTypeMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}


function saveStageType(){
	

	
	DisButClass.prototype.DisButMethod();
	var stageCode = document.getElementById("stageCode");
	var stageTypeDesc = document.getElementById("stageTypeDesc");
	var sequenceNumber = document.getElementById("sequenceNumber");
	var caseTypeDesc = document.getElementById("caseTypeDesc");
	var tat = document.getElementById("tat");//Rohit changes
	var path=document.getElementById("contextPath").value;
	var grideSize = document.getElementById("listSize").value;
	var productIds="";
	var paymentStageFlags="";
	var repetitiveFlags="";
	
	 if(trim(stageCode.value) == ''||trim(stageTypeDesc.value) == ''||trim(sequenceNumber.value) == ''
		 ||trim(caseTypeDesc.value) == '' || trim(tat.value)== ''){
		 var msg= '';
 		if(trim(stageCode.value) == '')
 			msg += '* Stage Code is required.\n';
 		if(trim(stageTypeDesc.value) == '')
 			msg += '* Stage Description is required.\n';
 		if(trim(sequenceNumber.value) == '')
 			msg += '* Sequence Number is required.\n';
 		if(trim(caseTypeDesc.value) == '')
 			msg += '* Case Type Description is required.\n';
 		if(trim(tat.value) == '')
 			msg += '*STAGE TAT is required.\n';
 		 		 		
 		if(msg.match("Code")){
 			stageCode.focus();
 		}
 		else if(msg.match("Description")){
 			stageTypeDesc.focus();
 		}
 		else if(msg.match("Sequence")){
 	 			sequenceNumber.focus();
 		}
 		else if(msg.match("Case")){
 	 			caseTypeDesc.focus();
 			
 		}
 		else if(msg.match("TAT")){
	 			tat.focus();
			
		}
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else if(validate("stageTypeMasterForm")){ 
		 
		 	for(var i=0;i<grideSize;i++)
		 	{
		 		
		 		
		 		if(document.getElementById("productIds"+i).checked==true)
		 		{
		 			
		 			productIds=productIds+document.getElementById("productIds"+i).value+"|";
		 			
		 			
		 			
		 			
		 			if(document.getElementById("paymentStages"+i).checked==true)
		 			{
		 				paymentStageFlags=paymentStageFlags+"Y"+"|";
		 			}
		 			else
		 			{
		 				paymentStageFlags=paymentStageFlags+"N"+"|";
		 			}
		 			if(document.getElementById("repetitives"+i).checked==true)
		 			{
		 				repetitiveFlags=repetitiveFlags+"Y"+"|";
		 			}
		 			else
		 			{
		 				repetitiveFlags=repetitiveFlags+"N"+"|";
		 			}
		 			
		 			
		 		}
		 	 	
		 	}
		 	
		 	
		 		
			document.getElementById("stageTypeMasterForm").action=path+"/stageTypeMasterAdd.do?method=saveStageTypeDetails&productIds="+productIds+"&paymentStageFlags="+paymentStageFlags+"&repetitiveFlags="+repetitiveFlags;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("stageTypeMasterForm").submit();
			return true;
		 }
 		 else
 		 {
 			DisButClass.prototype.EnbButMethod();
 			//document.getElementById("save").removeAttribute("disabled","true");
 			return false;
 		 }

	}

	 

function searchstageType(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("caseTypeDesc").value=="" && document.getElementById("stageProduct").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
	document.getElementById("stageTypeMasterForm").action=sourcepath+"/stageTypeMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stageTypeMasterForm").submit();
	return true;
	}
	}

	
	
function fnEditStageType(){
	
	DisButClass.prototype.DisButMethod();
	var stageCode = document.getElementById("stageCode");
	var stageTypeDesc = document.getElementById("stageTypeDesc");
	var sequenceNumber = document.getElementById("sequenceNumber");
	var caseTypeDesc = document.getElementById("caseTypeDesc");
	var tat = document.getElementById("tat");//Rohit changes
	var path=document.getElementById("contextPath").value;
	var grideSize = document.getElementById("listSize").value;
	var productIds = "";
	var paymentStageFlags ="";
	var repetitiveFlags = "";
	
	 if(trim(stageCode.value) == ''||trim(stageTypeDesc.value) == ''||trim(sequenceNumber.value) == ''
		 ||trim(caseTypeDesc.value) == ''  ||trim(tat.value) == ''){
		
		 var msg= '';
 		
		if(trim(stageCode.value) == '')
 			msg += '* Stage Code is required.\n';
 		if(trim(stageTypeDesc.value) == '')
 			msg += '* Stage Description is required.\n';
 		if(trim(sequenceNumber.value) == '')
 			msg += '* Sequence Number is required.\n';
 		if(trim(caseTypeDesc.value) == '')
 			msg += '* Case Type Description is required.\n';
 		if(trim(tat.value) == '')
 			msg += '* STAGE TAT is required.\n';
 		 		 		
 		if(msg.match("Code")){
 			stageCode.focus();
 		}
 		else if(msg.match("Description")){
 			stageTypeDesc.focus();
 		}
 		else if(msg.match("Sequence")){
 	 			sequenceNumber.focus();
 		}
 		else if(msg.match("Case")){
 	 				caseTypeDesc.focus();
 		}	
 		else if(msg.match("TAT")){
				tat.focus();
	}	
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 		
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
	 
	 for(var i=0;i<grideSize;i++)
	 	{
	 		if(document.getElementById("productIds"+i).checked==true)
	 		{
	 			productIds=productIds+document.getElementById("productIds"+i).value+"|";
	 			if(document.getElementById("paymentStages"+i).checked==true)
	 			{
	 				paymentStageFlags=paymentStageFlags+"Y"+"|";
	 			}
	 			else
	 			{
	 				paymentStageFlags=paymentStageFlags+"N"+"|";
	 			}
	 			if(document.getElementById("repetitives"+i).checked==true)
	 			{
	 				repetitiveFlags=repetitiveFlags+"Y"+"|";
	 			}
	 			else
	 			{
	 				repetitiveFlags=repetitiveFlags+"N"+"|";
	 			}
	 			
	 			
	 		}
	 	}	
	 
	 
		document.getElementById("stageTypeMasterForm").action=path+"/stageTypeMasterAdd.do?method=updateStage&productIds="+productIds+"&paymentStageFlags="+paymentStageFlags+"&repetitiveFlags="+repetitiveFlags;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("stageTypeMasterForm").submit();
	

}


function isNumberKey(evt) 
		{
		var charCode = (evt.which) ? evt.which : event.keyCode;

		if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric Value Allowed Here");
			return false;
		}
			return true;
		}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;

function validate(formName){
	
	var stageCode = document.getElementById("stageCode");
	

	var errors = [];
 
	if (!ck_numeric.test(stageCode.value)) {
		 if(trim(stageCode.value) != ''){
		 	errors[errors.length] = "* Stage Code is invalid.";
		 }
	 }


 
 if (errors.length > 0) {
  reportErrors(errors);
  return false;
 }
 
 return true;
}

function reportErrors(errors){
	 var msg = "";
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += "\n" + errors[i];
	 }
	 	if(msg.match("Stage")){
	 		stageCode.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}



function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}
