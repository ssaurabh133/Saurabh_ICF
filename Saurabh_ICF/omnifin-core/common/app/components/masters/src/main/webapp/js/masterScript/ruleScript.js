function caseChange(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}


function ruleAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("ruleParameterSearch").action=sourcepath+"/ruleAddMasters.do?method=addRuleMasterDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ruleParameterSearch").submit();
	
}

function saveRule(){
//	var scoreAlert = "";
	DisButClass.prototype.DisButMethod();
	var parameterCode=document.getElementById("parameterCode").value;
	var paramName=document.getElementById("paramName").value;
	var sourceTable=document.getElementById("sourceTable").value;
	var sourceColumn=document.getElementById("sourceColumn").value;
	
//	alert("parameterCode : "+parameterCode);
//	alert("paramName : "+paramName);
//	alert("sourceTable : "+sourceTable);
//	alert("sourceColumn : "+sourceColumn);
	
	var sourcepath=document.getElementById("contextPath").value;
	if(parameterCode != "" && paramName != "" && sourceTable != "" && sourceColumn != "")
		{
			if(validate('ruleParameterAddEdit'))
			{
				document.getElementById("ruleParameterAddEdit").action=sourcepath+"/ruleParameterAdd.do?method=insertRuleMasterDetails";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("ruleParameterAddEdit").submit();
			
			}
			//Change start here by Nishant Rai
			else{
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			//Change end here by Nishant
		}else
			{
				var a = "";
				if(parameterCode  == "")
					var a= "* Parameter Code is required \n";
				if(paramName == "")
					a += "* Parameter Name is required \n";
				if(sourceTable == "")
					a +="* Source Table is required \n";
				if(sourceColumn  == "")
					a +="* Source Column is required \n";
				alert(a);
				DisButClass.prototype.EnbButMethod();
				return false;
			}

}

function searchRule()
{
	DisButClass.prototype.DisButMethod();
	var parameterCode=document.getElementById("parameterCode").value;
	var paramName=document.getElementById("paramName").value;
	if(parameterCode=="" && paramName=="")
	{
		alert("Please select at least one field.");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("search").removeAttribute("disabled","false");
		return false;
	}
	var sourcepath=document.getElementById("path").value;
	document.getElementById("ruleParameterSearch").action=sourcepath+"/ruleSearchMasters.do?method=ruleMasterList";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ruleParameterSearch").submit();
	
}

function editRule()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;	
//		var scoreAlert = "";
		var parameterCode=document.getElementById("parameterCode").value;
		var paramName=document.getElementById("paramName").value;
		var sourceTable=document.getElementById("sourceTable").value;
		var sourceColumn=document.getElementById("sourceColumn").value;
		
//		alert("parameterCode : "+parameterCode);
//		alert("paramName : "+paramName);
//		alert("sourceTable : "+sourceTable);
//		alert("sourceColumn : "+sourceColumn);
		
		var sourcepath=document.getElementById("contextPath").value;
		if(parameterCode != "" && paramName != "" && sourceTable != "" && sourceColumn != "")
			{
				if(validate('ruleParameterAddEdit'))
				{
					document.getElementById("ruleParameterAddEdit").action=sourcepath+"/ruleMastersUpdate.do?method=updateRuleDetails";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("ruleParameterAddEdit").submit();
					
				}
				//Change start here by Nishant Rai
				else{
					
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				//Change end here by Nishant
			}else
				{
					var a = "";
					if(parameterCode == "")
						var a= "* Parameter Code is required \n";
					if(paramName == "")
						a += "* Parameter Name is required \n";
					if(sourceTable == "")
						a +="* Source Table is required \n";
					if(sourceColumn == "")
						a +="* Source Column is required \n";
					alert(a);
					DisButClass.prototype.EnbButMethod();
					return false;
				}

}


function radioCheck(san){
	if(san == 'dataType1'){
		document.getElementById("dataType1").checked = true;
		document.getElementById("dataType2").checked = false;
		document.getElementById("dataType1").value = 'N';
			
	}else if(san == 'dataType2'){
		document.getElementById("dataType2").checked = true;
		document.getElementById("dataType1").checked = false;
		document.getElementById("dataType2").value = 'A';
		
	}
}

//-----------------------------------------VALIDATION--------------------------

var ck_parameterCode = /^[A-Za-z0-9_]{3,20}$/;
//var ck_sourceTable = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i  // /^[A-Za-z0-9_]{1,20}$/;
//var ck_sourceColumn =  /^[A-Za-z0-9_]{0,50}$/;  ///^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
var ck_paramName = /^[A-Za-z0-9_]{1,50}$/; ///^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i 



function validate(formName){
 var parameterCode = document.getElementById("parameterCode").value;
 var paramName = document.getElementById("paramName").value;
// var sourceTable = document.getElementById("sourceTable").value;
// var sourceColumn = document.getElementById("sourceColumn").value;
  
  
 var errors = [];
 
 if (!ck_parameterCode.test(parameterCode)) {
  errors[errors.length] = "* Parameter Code not valid";
 }
  
 if (!ck_paramName.test(paramName)) {
  errors[errors.length] = "* Parameter Name not valid.";
 }
// if (!ck_sourceTable.test(sourceTable)) {
//  errors[errors.length] = "* Source Table not valid.";
// }
//
// if (!ck_sourceColumn.test(sourceColumn)) {
//  errors[errors.length] = "* Source Column not valid.";
// }
 
 
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
 alert(msg);
 document.getElementById("save").removeAttribute("disabled","true");
}

addEvent(window, 'load', initForm);

var highlight_array = new Array();

function initForm(){
	browserDetect();
	initializeFocus();
	ifInstructs();
	showRangeCounters();
	initAutoResize();
	checkPaypal();
	checkMechanicalTurk();
}

function initializeFocus(){
	fields = getElementsByClassName(document, "*", "field");
	for(i = 0; i < fields.length; i++) {
		if(fields[i].type == 'radio' || fields[i].type == 'checkbox' || fields[i].type == 'file') {
			fields[i].onclick = function(){clearSafariRadios(); addClassName(this.parentNode.parentNode, "focused", true)};
			fields[i].onfocus = function(){clearSafariRadios(); addClassName(this.parentNode.parentNode, "focused", true)};
			highlight_array.splice(highlight_array.length,0,fields[i]);
		}
		if(fields[i].className.match('addr')){
			fields[i].onfocus = function(){clearSafariRadios();addClassName(this.parentNode.parentNode.parentNode, "focused", true)};
			fields[i].onblur = function(){removeClassName(this.parentNode.parentNode.parentNode, "focused")};
		}
		else {
			fields[i].onfocus = function(){clearSafariRadios();addClassName(this.parentNode.parentNode, "focused", true)};
			fields[i].onblur = function(){removeClassName(this.parentNode.parentNode, "focused")};
		}
	}
}

function initAutoResize() {
	//var par = window.location.href.toString();
	//parent.location = par+'#height='+document.body.offsetHeight;
}

function clearSafariRadios() {
	for(var i = 0; i < highlight_array.length; i++) {
		if(highlight_array[i].parentNode) {
			removeClassName(highlight_array[i].parentNode.parentNode, 'focused');
		}
	}
}

function ifInstructs(){
	var container = document.getElementById('public');
	if(container){
		removeClassName(container,'noI');
		var instructs = getElementsByClassName(document,"*","instruct");
		if(instructs == ''){
			addClassName(container,'noI',true);
		}
		if(container.offsetWidth <= 450){
			addClassName(container,'altInstruct',true);
		}
	}
}

function browserDetect(){
	var detect = navigator.userAgent.toLowerCase();
	var container = document.getElementsByTagName('html');
	if(detect.indexOf('safari') + 1){
		addClassName(container[0], 'safari', true);
	}
	if(detect.indexOf('firefox') + 1){
		addClassName(container[0], 'firefox', true);
	}
}

function checkPaypal() {
	if(document.getElementById('merchant')) {
		document.getElementById('merchantMessage').innerHTML = 'Your order is being processed. Please wait while the page redirects to the merchant checkout.';
		document.getElementById('merchantButton').style.display = 'none';
		document.getElementById('merchant').submit();
	}
}

function checkMechanicalTurk() {
	if(document.getElementById('mechanicalTurk')) {
		document.getElementById('merchantMessage').innerHTML = 'Your submission is being processed. You will be redirected shortly.';
		document.getElementById('merchantButton').style.display = 'none';
		document.getElementById('mechanicalTurk').submit();
	}
}

function showRangeCounters(){
	counters = getElementsByClassName(document, "em", "currently");
	for(i = 0; i < counters.length; i++) {
		counters[i].style.display = 'inline';
	}
}

function validateRange(ColumnId, RangeType) {
	if(document.getElementById('rangeUsedMsg'+ColumnId)) {
		var field = document.getElementById('Field'+ColumnId);
		var msg = document.getElementById('rangeUsedMsg'+ColumnId);

		switch(RangeType) {
			case 'character':
				msg.innerHTML = field.value.length;
				break;
				
			case 'word':
				var words = field.value.split(" ");
				var used = 0;
				for(i =0; i < words.length; i++) {
					if(words[i].replace(/\s+$/,"") != "") used++;
				}
				msg.innerHTML = used;
				break;
				
			case 'digit':
				msg.innerHTML = field.value.length;
				break;
		}
	}
}

/*--------------------------------------------------------------------------*/


function getElementsByClassName(oElm, strTagName, strClassName){
	var arrElements = (strTagName == "*" && oElm.all)? oElm.all : oElm.getElementsByTagName(strTagName);
	var arrReturnElements = new Array();
	strClassName = strClassName.replace(/\-/g, "\\-");
	var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");
	var oElement;
	for(var i=0; i<arrElements.length; i++){
		oElement = arrElements[i];		
		if(oRegExp.test(oElement.className)){
			arrReturnElements.push(oElement);
		}	
	}
	return (arrReturnElements)
}


function addClassName(objElement, strClass, blnMayAlreadyExist){
   if ( objElement.className ){
      var arrList = objElement.className.split(' ');
      if ( blnMayAlreadyExist ){
         var strClassUpper = strClass.toUpperCase();
         for ( var i = 0; i < arrList.length; i++ ){
            if ( arrList[i].toUpperCase() == strClassUpper ){
               arrList.splice(i, 1);
               i--;
             }
           }
      }
      arrList[arrList.length] = strClass;
      objElement.className = arrList.join(' ');
   }
   else{  
      objElement.className = strClass;
      }
}


function removeClassName(objElement, strClass){
   if ( objElement.className ){
      var arrList = objElement.className.split(' ');
      var strClassUpper = strClass.toUpperCase();
      for ( var i = 0; i < arrList.length; i++ ){
         if ( arrList[i].toUpperCase() == strClassUpper ){
            arrList.splice(i, 1);
            i--;
         }
      }
      objElement.className = arrList.join(' ');
   }
}


function addEvent( obj, type, fn ) {
  if ( obj.attachEvent ) {
    obj["e"+type+fn] = fn;
    obj[type+fn] = function() { obj["e"+type+fn]( window.event ) };
    obj.attachEvent( "on"+type, obj[type+fn] );
  } 
  else{
    obj.addEventListener( type, fn, false );	
  }
}




