function trim(str) {
	return ltrim(rtrim(str));
	}

	function isWhitespace(charToCheck) {
	var whitespaceChars = " \t\n\r\f";
	return (whitespaceChars.indexOf(charToCheck) != -1);
	}

	function ltrim(str) { 
	for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
	return str.substring(k, str.length);
	}
	function rtrim(str) {
	for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
	return str.substring(0,j+1);
	}

function newRatioAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	//document.location.href=sourcepath+"/addRatioDefinition.do?method=addRatioDefinition";
	document.getElementById("ratioDefinitionMasterMasterSearchForm").action=sourcepath+"/addRatioDefinition.do?method=addRatioDefinition";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ratioDefinitionMasterMasterSearchForm").submit();
	
   // document.getElementById("add").removeAttribute("disabled","true");
}
function saveRatioDefinition()
{
	DisButClass.prototype.DisButMethod();
	var expr = document.getElementById("expression").value;
	var sourcepath=document.getElementById("path").value;
	var paramCodes = document.getElementById("allParamCodes").value;
	paramCodes = paramCodes.substring(1,paramCodes.length);
	
	
	var flag = false;
    if(paramCodes!=''){
		var allParamCodes = paramCodes.split("|");
		}
	else{
		var allParamCodes =0;
		}
	//var slash=expr.indexOf("//");
	//alert(expr.indexOf("//"));
	if(expr.indexOf("//") < 0)
	{
	for(i=0;i< allParamCodes.length ;i++)
	{
	
		var pos = expr.indexOf(allParamCodes[i]);
		pos = pos+allParamCodes[i].length;
		var pos1=expr.indexOf(allParamCodes[i]);
		pos1=pos1-1;
		//alert("pos "+pos);
		//alert("char at pos "+expr.charAt(pos));
		var ch = trim(expr.charAt(pos));
		if(pos >= 0 && pos != expr.length)
		{
			
			if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
			{
			
			}
			else
			{
				alert("Invalid expression");
				DisButClass.prototype.EnbButMethod();
//				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		var ch = trim(expr.charAt(pos1));
		
		if(pos1 > 0 && pos1 <= expr.length)
		{
			//alert("pos "+pos1);
			//alert("char at pos "+ch);
			if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
			{
			
			}
			else
			{
				alert("Invalid expression");
				DisButClass.prototype.EnbButMethod();
//				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	}
	}
	else
	{
		alert("Invalid expression");
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
	
	if(validateRatioDefinitionMasterAddDynaValidatorForm(document.getElementById("ratioDefinitionMasterForm"))){
		
		document.getElementById("ratioDefinitionMasterForm").action=sourcepath+"/saveRatioDefinition.do?method=saveRatioDefinition";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("ratioDefinitionMasterForm").submit();
		return true;
	}
	else
	{
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function clearExpression()
{
	//alert("ok"+document.getElementById("flag").value);
	if(document.getElementById("flag").value=="true")
	{	//alert("ok");
		document.getElementById("expression").value='';
		document.getElementById("flag").value=false;
	}

}

function updateRatioDefinition(ratioCode)
{
	var sourcepath=document.getElementById("path").value;
	var expr = document.getElementById("expression").value;
	var paramCodes = document.getElementById("allParamCodes").value;
	//alert("expr--->"+expr);
	//paramCodes = expr.substring(1,expr.length);
	
	paramCodes = paramCodes.substring(1,paramCodes.length);
	
    if(paramCodes!=''){
		var allParamCodes = paramCodes.split("|");
		}
	else{
		var allParamCodes =0;
		}
	//alert("param codes "+allParamCodes);
	var flag = false;
	//alert("allParamCodes"+allParamCodes);
	if(expr.indexOf("//") < 0)
	{
	for(i=0;i< allParamCodes.length ;i++)
	{
	
		var pos = expr.indexOf(allParamCodes[i]);
		pos = pos+allParamCodes[i].length;
		var pos1=expr.indexOf(allParamCodes[i]);
		pos1=pos1-1;
		var ch = trim(expr.charAt(pos));
		
		//alert("ch--->>>>>>"+ch);
		if(pos >= 0 && pos != expr.length)
		{
			//alert("!="+pos);
			if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
			{
			
			}
			else
			{
				alert("Invalid expression");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
		
		var ch = trim(expr.charAt(pos1));
		
		if(pos1 > 0 && pos1 <= expr.length)
		{
			//alert("pos1 "+pos1);
			if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
			{
			
			}
			else
			{
				alert("Invalid expression");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	}
	
	}
	else
	{
		alert("Invalid expression");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	if(validateRatioDefinitionMasterAddDynaValidatorForm(document.getElementById("ratioDefinitionMasterForm")))
	{
		document.getElementById("ratioDefinitionMasterForm").action=sourcepath+"/saveRatioDefinition.do?method=updateRatioDefinition&ratioCode="+ratioCode;
		document.getElementById("ratioDefinitionMasterForm").submit();
		return true;
	}
	else
	{
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function ratioSearch(val){ 
	
	DisButClass.prototype.DisButMethod();
	    if(document.getElementById("ratioCode").value=='' && document.getElementById("ratioName").value=='')
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else
		{
	    	document.getElementById("ratioDefinitionMasterMasterSearchForm").action="ratioDefinitionMasterSearchAction.do";
	    	document.getElementById("processingImage").style.display = '';
		    document.getElementById("ratioDefinitionMasterMasterSearchForm").submit(); 
			return true;
		}
		
	}	

	function makeExpression(val)
	{
		var expr='';
		//alert("operator"+val);
		//alert("operator---->"+document.getElementById("operator").value);
		if(val=='parameterCode')
		{		
			//alert("parameterCode"+document.getElementById("parameterCode").value);
		//alert("allParamCodes"+document.getElementById("allParamCodes").value);
			document.getElementById("allParamCodes").value = document.getElementById("allParamCodes").value+"|"+document.getElementById("parameterCode").value;
			var expr=document.getElementById("expression").value;
			//alert("parameterCode"+document.getElementById("parameterCode").value);
			document.getElementById("expression").value = expr + document.getElementById("parameterCode").value;
			//alert("expression"+document.getElementById("expression").value);
		}
		else if(val=='operator')
		{
			var expr=document.getElementById("expression").value;
			//alert("operator---1-->"+document.getElementById("operator").value);
			//alert("expr"+expr);
			document.getElementById("expression").value = expr + document.getElementById("operator").value;
		}if(val=='ratioCode')
		{		
			//alert("parameterCode"+document.getElementById("parameterCode").value);
		//alert("allParamCodes"+document.getElementById("allParamCodes").value);
			document.getElementById("allParamCodes").value = document.getElementById("allParamCodes").value+"|"+document.getElementById("ratioCodeHid").value;
			var expr=document.getElementById("expression").value;
			//alert("parameterCode"+document.getElementById("parameterCode").value);
			document.getElementById("expression").value = expr + document.getElementById("ratioCodeHid").value;
			//alert("expression"+document.getElementById("expression").value);
		}
		else
		{
			var expr=document.getElementById("expression").value;
			document.getElementById("expression").value = expr + document.getElementById("constant").value;
		}
		
	}

	
	function clearRatioExpression()
	{
		document.getElementById("expression").value ="";
		document.getElementById("constant").value ="";
		document.getElementById("operator").value ="";
		document.getElementById("parameterCode").value ="";
		document.getElementById("allParamCodes").value ="";
		document.getElementById("ratioCodeList").value ="";
		document.getElementById("ratioCodeHid").value ="";
		
		
	}
	
	function getRatioFarmula()
	{
		//alert("hi");
		 var ratioCodeList=document.getElementById('ratioCodeList').value;
		//alert("ratioCodeList:----------"+ratioCodeList);
		// alert(dealId);
			 var contextPath=document.getElementById("path").value;
			 var address = contextPath+"/addRatioDefinition.do?method=getRatioFarmula";
			// alert(address);
			 var data = "ratioCode="+ratioCodeList;
			 sendForGetRatioFarmula(address, data);
						 
	}
	
	function sendForGetRatioFarmula(address, data) {
		//alert("in sendForgetCustomerName id");
		var request = getRequestObject();
		request.onreadystatechange = function () {
			resultRatioFarmula(request);	
		};
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
		
	}

	function resultRatioFarmula(request){
		
		//alert("in resultCustomerName id");
		 var expression=document.getElementById('expression').value;
		// alert("expression:--->"+expression);
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
		//	alert("str:--->"+str);
			document.getElementById('ratioCodeHid').value=trim(str);
			if(document.getElementById('ratioCodeHid').value!=''){
				makeExpression('ratioCode');
			}
			
		}
	}
	function getRequestObject() {
		  if (window.ActiveXObject) { 
			return(new ActiveXObject("Microsoft.XMLHTTP"));
		  } else if (window.XMLHttpRequest) {
			return(new XMLHttpRequest());
		  } else {
			return(null);
		  }
		}

	