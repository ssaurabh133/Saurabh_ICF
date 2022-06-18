// Dealer Master 

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

function isAlphNumericKey1(evt) 
{
    
var charCode = (evt.which) ? evt.which : event.keyCode;
//alert(charCode);
if (charCode > 31 && (charCode < 48 || charCode > 57)&& (charCode < 65 || charCode > 90)&& (charCode < 97 || charCode > 122)) {
	alert("Only Numeric and Alphanumeric are allowed here");
	return false;
}
return true;
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

//Reason Master 

function newAddReason(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/legalReasonMasterSearch.do?method=openAddReason";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reasonMaster").submit();
	return true;
}

function fnreasonSave(){
	
	DisButClass.prototype.DisButMethod();
	var reasonType = document.getElementById("reasonType");
	var reasonShortcode = document.getElementById("reasonShortcode");
	var reasonDes = document.getElementById("reasonDes");
	var notice=  document.getElementById("lbxNoticeCode");
	var caseType=  document.getElementById("lbxCaseTypeCode");
	
	if(trim(reasonType.value) == ''||trim(reasonDes.value) == ''||trim(reasonShortcode.value) == ''||trim(notice.value) == ''||trim(caseType.value) == ''){
		var msg= '';
		if(trim(reasonType.value) == '')
			msg += '* Reason Type is required.\n';
		if(trim(reasonShortcode.value) == '')
			msg += '* Reason Short Code is required.\n';
		if(trim(reasonDes.value) == '')
			msg += '* Reason Description is required.\n';
		if(trim(notice.value) == '')
			msg += '* Notice is required.\n';
		if(trim(caseType.value) == '')
			msg += '* caseType is required.\n';
		
		if(msg.match("Type")){
			document.getElementById("reasonTypeButton").focus();
		}else if(msg.match("Description")){
			reasonDes.focus();
		}
		else if(msg.match("Short")){
			reasonShortcode.focus();
		}
		else if(msg.match("Notice")){
			notice.focus();
		}
		else if(msg.match("caseType")){
			caseType.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("reasonMaster").action="legalReasonMasterAdd.do?method=saveReasonDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("reasonMaster").submit();
		return true;
	}
}

function fnSearchReason(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("reasonId").value==''&& document.getElementById("reasonSearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("reasonMaster").action="legalReasonMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reasonMaster").submit();
	
}

function newpage( a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/legalReasonMasterSearch.do?method=openAddReason&ReasonId="+a;
	document.getElementById("reasonMaster").submit();
	
}

function editpageReason(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/legalReasonMasterSearch.do?method=openEditReason&ReasonId="+b;
	document.getElementById("reasonMaster").submit();
}

function fnEditReason(){

	
	DisButClass.prototype.DisButMethod();
	var reasonType = document.getElementById("reasonType");
	var reasonShortcode = document.getElementById("reasonShortcode");
	var reasonDes = document.getElementById("reasonDes");
	var notice=  document.getElementById("lbxNoticeCode");
	var caseType=  document.getElementById("lbxCaseTypeCode");
	
	if(trim(reasonType.value) == ''||trim(reasonDes.value) == ''||trim(reasonShortcode.value) == ''||trim(notice.value) == ''||trim(caseType.value) == ''){
		var msg= '';
		if(trim(reasonType.value) == '')
			msg += '* Reason Type is required.\n';
		if(trim(reasonShortcode.value) == '')
			msg += '* Reason Short Code is required.\n';
		if(trim(reasonDes.value) == '')
			msg += '* Reason Description is required.\n';
		if(trim(notice.value) == '')
			msg += '* Notice is required.\n';
		if(trim(caseType.value) == '')
			msg += '* caseType is required.\n';
		
		if(msg.match("Type")){
		//	document.getElementById("reasonTypeButton").focus();
		}else if(msg.match("Description")){
			reasonDes.focus();
		}
		else if(msg.match("Short")){
			reasonShortcode.focus();
		}
		else if(msg.match("Notice")){
			notice.focus();
		}
		else if(msg.match("caseType")){
			caseType.focus();
		}
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("reasonMaster").action="legalReasonMasterAdd.do?method=updateReason";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("reasonMaster").submit();
		return true;
	}
}




function removeSpaces1(string) {
	 return string.split(' ').join('');
	}
	


function numericOnly(e, Max){
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 && e.keyCode != 39)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}

function onlyPercentage()
{
alert("Testing ");
var vatPercent = $("#vatPercent").val();
	    if(vatPercent !='')
	    {
			var regex1 = /^\d+(\.\d{1,7})?$/;  // /^\d{2}$/;
			if (!regex1.test(vatPercent) || vatPercent.length >10) {
				alert("Only Numeric is required.");
				$("#vatPercent").attr("value","");
				$("#vatPercent").focus();
				return false;
			}
	    }
}




