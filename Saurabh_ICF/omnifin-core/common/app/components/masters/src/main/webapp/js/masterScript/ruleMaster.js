
	function ruleSearch(val){ 
		
		DisButClass.prototype.DisButMethod();
	    if(document.getElementById("ruleCode").value=='' && document.getElementById("ruleName").value=='')
		{
			alert(val);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	    else
		{
	    	document.getElementById("ruleMasterSearchForm").action="ruleMasterSearchAction.do";
	    	document.getElementById("processingImage").style.display = '';
		    document.getElementById("ruleMasterSearchForm").submit(); 
			return true;
		}		
	}	
	
	function newRuleAdd()
	{
		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("path").value;
		document.getElementById("ruleMasterSearchForm").action=sourcepath+"/addRule.do?method=addRuleMaster";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("ruleMasterSearchForm").submit();
	}
	function saveRuleMaster()
	{
		DisButClass.prototype.DisButMethod();
		var expr = document.getElementById("expression").value;
		var sourcepath=document.getElementById("path").value;
//		
//		var paramCodes = document.getElementById("allParamCodes").value;
//		paramCodes = paramCodes.substring(1,paramCodes.length);
//		var allParamCodes = paramCodes.split("|");
//		//alert("param codes "+allParamCodes);
//		var flag = false;
//		for(i=0;i< allParamCodes.length ;i++)
//		{
//		
//			var pos = expr.indexOf(allParamCodes[i]);
//			pos = pos+allParamCodes[i].length;
//			var pos1=expr.indexOf(allParamCodes[i]);
//			pos1=pos1-1;
//			var ch = expr.charAt(pos).trim();
//			if(pos >= 0 && pos != expr.length)
//			{
//				
//				if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
//				{
//				
//				}
//				else
//				{
//					alert("Invalid expression");
//					document.getElementById("save").removeAttribute("disabled","true");
//					return false;
//				}
//			}
//			
//			var ch = expr.charAt(pos1).trim();
//			
//			if(pos1 > 0 && pos1 <= expr.length)
//			{
//				//alert("pos "+pos1);
//				//alert("char at pos "+ch);
//				if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
//				{
//				
//				}
//				else
//				{
//					alert("Invalid expression");
//					document.getElementById("save").removeAttribute("disabled","true");
//					return false;
//				}
//			}
//		}
		
		if(validateRuleMasterAddDynaValidatorForm(document.getElementById("ruleMasterForm")))
		{
			document.getElementById("ruleMasterForm").action=sourcepath+"/saveRuleMaster.do?method=saveRuleMaster";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("ruleMasterForm").submit();
			return true;
		}
		else
		{
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
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
	
	function updateRule(ruleCode)
	{
		DisButClass.prototype.DisButMethod();
		var ruleName=document.getElementById("ruleName").value;
		ruleName=ruleName.toUpperCase();
		document.getElementById("ruleName").value=ruleName;
		var sourcepath=document.getElementById("path").value;
		var expr = document.getElementById("expression").value;
		
//		var paramCodes = document.getElementById("allParamCodes").value;
//		paramCodes = expr.substring(1,expr.length);
//		var allParamCodes = paramCodes.split("|");
//		//alert("param codes "+allParamCodes);
//		var flag = false;
//		for(i=0;i< allParamCodes.length ;i++)
//		{
//		
//			var pos = expr.indexOf(allParamCodes[i]);
//			pos = pos+allParamCodes[i].length;
//			var pos1=expr.indexOf(allParamCodes[i]);
//			pos1=pos1-1;
//
//			var ch = expr.charAt(pos).trim();
//			if(pos >= 0 && pos != expr.length)
//			{
//				
//				if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
//				{
//				
//				}
//				else
//				{
//					alert("Invalid expression");
//					document.getElementById("save").removeAttribute("disabled","true");
//					return false;
//				}
//			}
//			
//			var ch = expr.charAt(pos1).trim();
//			
//			if(pos1 > 0 && pos1 <= expr.length)
//			{
//				//alert("pos "+pos1);
//				//alert("char at pos "+ch);
//				if(ch=='+' || ch=='-' || ch=='/' || ch=='*' || ch=='%' || ch==')' || ch=='(')
//				{
//				
//				}
//				else
//				{
//					alert("Invalid expression");
//					document.getElementById("save").removeAttribute("disabled","true");
//					return false;
//				}
//			}
//		}
		
		if(validateRuleMasterAddDynaValidatorForm(document.getElementById("ruleMasterForm")))
		{
			document.getElementById("ruleMasterForm").action=sourcepath+"/saveRuleMaster.do?method=updateRule&ruleCode="+ruleCode;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("ruleMasterForm").submit();
			return true;
		}
		else
		{
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	}
	function ruleMasterMakeExpression(val)
		{
			var expr='';
			alert(val);
			if(val=='parameterCode')
			{
				var param = "$"+document.getElementById("parameterCode").value+"$";
				alert(param);
				var expr=document.getElementById("expression").value;
				document.getElementById("expression").value = expr + param;
			}
//			else if(val=='operator')
//			{
//				var expr=document.getElementById("expression").value;
//				document.getElementById("expression").value = expr + document.getElementById("operator").value;
//			}
//			else
//			{
//				var expr=document.getElementById("expression").value;
//				document.getElementById("expression").value = expr + document.getElementById("constant").value;
//			}
			
		}

		
		function clearRatioExpression()
		{
			document.getElementById("expression").value ="";
			document.getElementById("constant").value ="";
			document.getElementById("operator").value ="";
			document.getElementById("parameterCode").value ="";
		}
		
		function ruleMasterMakeExpression(val)
		{
			var expr='';
			if(val=='parameterCode')
			{		
				var paramCode = "$"+document.getElementById("parameterCode").value+"$";
				

				document.getElementById("allParamCodes").value=document.getElementById("allParamCodes").value+"|"+paramCode;
				var expr=document.getElementById("expression").value;
				document.getElementById("expression").value = expr + paramCode;
			}
			else if(val=='operator')
			{
				var	expr=document.getElementById("expression").value;
				document.getElementById("expression").value = expr + document.getElementById("operator").value;
			}
			else
			{
				var	expr=document.getElementById("expression").value;
				document.getElementById("expression").value = expr + document.getElementById("constant").value;
			}
			
		}
		
		function checkDollar(val)
		{
			alert("val : "+val);
		}
			
	