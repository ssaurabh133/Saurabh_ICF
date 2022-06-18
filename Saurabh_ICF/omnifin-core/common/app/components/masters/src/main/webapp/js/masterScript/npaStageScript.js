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


function newAdd()
{

	
	var sourcepath=document.getElementById("path").value;
	document.getElementById("npaStageMasterSearch").action=sourcepath+"/npaStageMasterSearch.do?method=openAddNPAStage";
	document.getElementById("npaStageMasterSearch").submit();
	return true;
	
}
function fnSave()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var productId=document.getElementById("productId").value;
	var npaStage = document.getElementById("npaStage");
	var sequenceNo = document.getElementById("sequenceNo");
	var npaCriteriaValue = document.getElementById("npaCriteriaValue");
	var npaCriteria = document.getElementById("npaCriteria");
	var moveToNext = document.getElementById("moveToNext");
	var moveToPrevious = document.getElementById("moveToPrevious");

	
	if(trim(productId) == ''||trim(npaStage.value) == ''||trim(sequenceNo.value) == ''||trim(npaCriteria.value) == ''||trim(npaCriteriaValue.value) == ''||trim(moveToNext.value) == ''||trim(moveToPrevious.value) == ''){
			var msg= '';
			if(trim(productId) == '')
				msg += '* Product is required.\n';
			if(trim(npaStage.value) == '')
				msg += '* NPA Code is required.\n';
			if(trim(sequenceNo.value) == '')
				msg += '* Sequence No. is required.\n';
			if(trim(npaCriteria.value) == '')
				msg += '* NPA Criteria is required.\n';
			if(trim(npaCriteriaValue.value) == '')
				msg += '* NPA Value is required.\n';
			if(trim(moveToNext.value) == '')
				msg += '* Next Stage Movement is required.\n';
			if(trim(moveToPrevious.value) == '')
				msg += '* Previous Stage Movement is required.\n';
			
			if(msg.match("Product")){
				npaStage.focus();
			}else if(msg.match("Code")){
				npaStage.focus();
			}else if(msg.match("Sequence")){
				sequenceNo.focus();
			}else if(msg.match("Criteria")){
				npaCriteria.focus();
			}else if(msg.match("Value")){
				npaCriteriaValue.focus();
			}else if(msg.match("Next")){
				moveToNext.focus();
			}else if(msg.match("Previous")){
				moveToPrevious.focus();
			}
			
			alert(msg);
			
			//document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
			}else if (validate("npaStageMaster")){
			
			document.getElementById("npaStageMaster").action=sourcepath+"/npaStageMasterAdd.do?method=saveNPAStageDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("npaStageMaster").submit();
			return true;
		}
}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("npaStageMaster").action=sourcepath+"/npaStageMasterSearch.do?method=openAddNPASatge&npaStage="+a;
	document.getElementById("npaStageMaster").submit();
	
}
function modifyDetails(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("npaStageMasterSearch").action=sourcepath+"/npaStageMasterSearch.do?method=openEditNPAStage&npaSearchStage="+b;
	document.getElementById("npaStageMasterSearch").submit();

	
}
function fnEdit(){
	DisButClass.prototype.DisButMethod();
	var productId=document.getElementById("productId").value;

	var npaStage = document.getElementById("npaStage");
	var sequenceNo = document.getElementById("sequenceNo");
	var npaCriteriaValue = document.getElementById("npaCriteriaValue");
	var npaCriteria = document.getElementById("npaCriteria");
	var moveToNext = document.getElementById("moveToNext");
	var moveToPrevious = document.getElementById("moveToPrevious");

	
	if(trim(productId) == ''||trim(npaStage.value) == ''||trim(sequenceNo.value) == ''||trim(npaCriteria.value) == ''||trim(npaCriteriaValue.value) == ''||trim(moveToNext.value) == ''||trim(moveToPrevious.value) == ''){
			var msg= '';
			if(trim(productId) == '')
				msg += '* Product is required.\n';
			if(trim(npaStage.value) == '')
				msg += '* NPA Code is required.\n';
			if(trim(sequenceNo.value) == '')
				msg += '* Sequence No. is required.\n';
			if(trim(npaCriteria.value) == '')
				msg += '* NPA Criteria is required.\n';
			if(trim(npaCriteriaValue.value) == '')
				msg += '* NPA Value is required.\n';
			if(trim(moveToNext.value) == '')
				msg += '* Next Stage Movement is required.\n';
			if(trim(moveToPrevious.value) == '')
				msg += '* Previous Stage Movement is required.\n';
			
			if(msg.match("Product")){
				npaStage.focus();
			}else if(msg.match("Code")){
				npaStage.focus();
			}else if(msg.match("Sequence")){
				sequenceNo.focus();
			}else if(msg.match("Criteria")){
				npaCriteria.focus();
			}else if(msg.match("Value")){
				npaCriteriaValue.focus();
			}else if(msg.match("Next")){
				moveToNext.focus();
			}else if(msg.match("Previous")){
				moveToPrevious.focus();
			}
			
			alert(msg);
			//document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else if (validate("npaStageMaster")){
		    document.getElementById("npaStageMaster").action="npaStageMasterAdd.do?method=updateNPAStage";
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("npaStageMaster").submit();
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
	    if(document.getElementById("npaSearchStage").value=='' && document.getElementById("sequenceNoSearch").value=='')
		{
			alert(val);	
			//document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
				return false;
		}

	    document.getElementById("npaStageMasterSearch").action="npaStageMasterBehind.do";
	    
	document.getElementById("processingImage").style.display = '';
	    document.getElementById("npaStageMasterSearch").submit();
		
	}	
		

//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[0-9_a-zA-Z']{1,25}$/;
var ck_din = /^[0-9]{1,10}$/;



function validate(formName){
	var npaStage = document.getElementById("npaStage");
	var sequenceNo = document.getElementById("sequenceNo");
	var npaCriteriaValue = document.getElementById("npaCriteriaValue");
  

 var errors = [];
 
 if (!ck_numeric.test(npaStage.value)) {
	 if(trim(npaStage.value) != ''){
	 	errors[errors.length] = "* NPA Stage is invalid.";
	 }
 }
 if (!ck_din.test(sequenceNo.value)) {
	 if(trim(sequenceNo.value) != ''){
		 errors[errors.length] = "* Sequence No. is invalid.";
	 }
}
 if (!ck_din.test(npaCriteriaValue.value)) {
	 if(trim(npaCriteriaValue.value) != ''){
		 errors[errors.length] = "* NPA Value is invalid.";
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
	 		npaStage.focus();
		}else if(msg.match("Sequence")){
			sequenceNo.focus();
		}else if(msg.match("Value")){
			npaCriteriaValue.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}
