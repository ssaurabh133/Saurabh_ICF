function schemeDetailsSave(val,val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11,val12,val13,val14,val15,val16,val17,val18)
{
	//alert("testMMM insert mode");
	DisButClass.prototype.DisButMethod();
		var msg='',msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='',msg17='',msg18='';
		var sourcepath=document.getElementById("contextPath").value;

		var productId = document.getElementById("productId");
		var schemeDesc = document.getElementById("schemeDesc");
		var customerExposureLimit = document.getElementById("customerExposureLimit");
		var minAmountFin = document.getElementById("minAmountFin");
		var maxAmountFin = document.getElementById("maxAmountFin");
		var rateType = document.getElementById("rateType");
		var baseRateType = document.getElementById("baseRateType");
		var minEffRate=document.getElementById("minEffRate");
		var maxEffRate=document.getElementById("maxEffRate");
		var minFlatRate=document.getElementById("minFlatRate");
		var maxFlatRate=document.getElementById("maxFlatRate");
		var minTenure=document.getElementById("minTenure");
		var maxTenure=document.getElementById("maxTenure");
		var rateMethod=document.getElementById("rateMethod");
		var rateMethod1=document.getElementById("rateMethod1");
		var frequency= document.getElementById("repaymentFreq").value;
		var tenure= document.getElementById("defTenure").value;
		var validityDays= document.getElementById("validityDays").value;
		var expiryDate= document.getElementById("expiryDate").value;
		var branchId= document.getElementById("lbxBranchIds").value;
		var allselection= document.getElementById("allselection").checked;
		var singleselection= document.getElementById("singleselection").checked;
		//alert("allselection>="+allselection+"singleselection=>"+singleselection);
		
		if(allselection==false && singleselection==false)
		{
			alert("Please Select Branch Access.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(singleselection==true && branchId=='')
		{
		    alert("Please Select at Least one branch.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(allselection)
			allselection='A';
		else
			allselection='S';
		//alert("all value>>>"+allselection);
		//alert("branch value"+branchId);

		var rateMethodValue="";
		var parseTenure=1;
		var freqMonth=1;
		if(frequency=='M')
		{
			freqMonth=1;
		}
		else if(frequency=='B')
		{
			freqMonth=2;
		}
		else if(frequency=='Q')
		{
			freqMonth=3;
		}
		else if(frequency=='H')
		{
			freqMonth=6;
		}
		else if(frequency=='Y')
		{
			freqMonth=12;
		}
		
		if(frequency!='')
		{
			parsedFreq=parseInt(freqMonth);
			//alert("parsedFreq:----"+parsedFreq);
		}
		
		if(tenure!='')
		{
			parseTenure=parseInt(tenure);
			//alert("parseTenure:-----"+parseTenure);
		}
		
		//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
		calInsat=parseTenure/parsedFreq;
//		alert("calInsat:---"+calInsat);
	if(document.getElementById("reschAllowed").checked==true && document.getElementById("reschLockinPeriod").value=="" )
	{
	
		msg=val;
	}
	if(document.getElementById("reschAllowed").checked==true && document.getElementById("reschLockinPeriod").value=="" && document.getElementById("minimumGapResch").value=="")
	{
	
		msg1=val1;
	}
	if(document.getElementById("reschAllowed").checked==true && document.getElementById("minPeriodResch").value=="")
	{
	
		msg2=val2;
	}
	if(document.getElementById("reschAllowed").checked==true && document.getElementById("numberReschAllowedYear").value=="")
	{
	
		msg3=val3;
	}
	if(document.getElementById("reschAllowed").checked==true && document.getElementById("numberReschAllowedTotal").value=="")
	{
	
		msg4=val4;
	}
	if(msg!=''||msg1!=''||msg2!=''||msg3!=''|| msg4!='')
	{
		alert(msg+'\n'+msg1+'\n'+msg2+'\n'+msg3+'\n'+msg4);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
		
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("defrLockinPeriod").value=="" )
	{
	
		msg5=val5;
		
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("minimumGapDefr").value=="" )
	{
	
		msg6=val6;
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("maximumDefrMonthsAllowed").value=="" )
	{
	
		msg7=val7;
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("maximumDefrMonthsTotal").value=="" )
	{
	
		msg8=val8;
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("numberDefrAllowedYear").value=="" )
	{
	
		msg9=val9;
	}
	if(document.getElementById("deferralAllowed").checked==true && document.getElementById("numberDefrAllowedTotal").value=="" )
	{
	
		msg10=val10;
	}
	if(msg5!=''||msg6!=''||msg7!=''||msg8!=''||msg9!=''||msg10!='')
	{
		alert(msg5+'\n'+msg6+'\n'+msg7+'\n'+msg8+'\n'+msg9+'\n'+msg10);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
		
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("prepayLockinPeriod").value=="" )
	{
	
		msg11=val11;
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("minimumGapPrepay").value=="" )
	{
	
		msg12=val12;
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("minimumPrepayPercent").value=="" )
	{
	
		msg13=val13;
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("maximumPrepayPercent").value=="" )
	{
	
		msg14=val14;
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("numberPrepayAllowedYear").value=="" )
	{
	
		msg15=val15;
	}
	if(document.getElementById("prepayAllowed").checked==true && document.getElementById("numberPrepayAllowedTotal").value=="" )
	{
	
		msg16=val16;
	}
	if(msg11!=''||msg12!=''||msg13!=''||msg14!=''||msg15!=''||msg16!='')
	{
		alert(msg11+'\n'+msg12+'\n'+msg13+'\n'+msg14+'\n'+msg15+'\n'+msg16);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
		
	}
	if(document.getElementById("terminationAllowed").checked==true && document.getElementById("terminationLockinPeriod").value=="" )
	{
	
		msg17=val17;
	}
	if(document.getElementById("terminationAllowed").checked==true && document.getElementById("minimumGapBetPrepayAndTer").value=="" )
	{
	
		msg18=val18;
	}

	if(msg17!='' ||msg18!='')
	{
		alert(msg17+"\n"+msg18);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
		
	}
	else if(productId.value != "" && schemeDesc.value != "" && customerExposureLimit.value != "" && minAmountFin.value != "" 
		&& maxAmountFin.value != "" && rateType.value!="" && minEffRate.value!="" && maxEffRate.value!=="" 
			&& minFlatRate.value!=="" && maxFlatRate.value!=="" && minTenure.value!=="" && maxTenure.value!=="" && validityDays !="" && expiryDate!="")
	{
	
		if(rateType.value=='E'){
			var b="";
			 if(!(rateMethod.checked ||rateMethod1.checked)) 
			 {	
					 b += "* Rate Method is required \n";
					 if(baseRateType.value  == ""){
						 b +="* Base Rate Type is required \n";						
							} 
					alert(b);
					 DisButClass.prototype.EnbButMethod();
					 return false;
		        }
			 if(((rateMethod.checked ||rateMethod1.checked))&& baseRateType.value  == ""){
					b += "* Base Rate Type is required \n";	
					alert(b);
					 DisButClass.prototype.EnbButMethod();
					 return false;
				 }
			 if(rateMethod.checked) 
			 {	
				 rateMethodValue=rateMethod.value;				 
			 }
			 else{
				 rateMethodValue=rateMethod1.value; 				
			 }
			 
		}
	   
		if(!isInt(calInsat))
		{
			var agree=confirm("The Combination of tenure and frequency are incorrect");
			if(!agree){
				document.getElementById("repaymentFreq").value='';
				document.getElementById("defTenure").value=''; 
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		}
		if(minTenure.value==0)
		{
			alert("* Minimum Tenure should be greater than 0.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(validate("CrSchemeMasterForm"))
		{
				document.getElementById("CrSchemeMasterForm").action=sourcepath+"/crSchemeMasterAction.do?method=saveSchemeCode&rateMethod="+rateMethodValue+"&allselection="+allselection;
			    document.getElementById("processingImage").style.display = '';
				document.getElementById("CrSchemeMasterForm").submit();
			   return true;

		}
	}else
		{
			var a = "";
			if(productId.value  == ""){
				a= "* Product Id is required \n";
				
			}
			if(schemeDesc.value == ""){
				a += "* Scheme Description is required \n";
		
			}
			if(customerExposureLimit.value == ""){
				a +="* Customer Exposure Limit is required \n";
				
			}
			if(minAmountFin.value  == ""){
				a +="* Min amount Fin is required \n";
				
			}
			if(maxAmountFin.value  == ""){
				a +="* Max amount Fin is required \n";
				
			}
			
			if(rateType.value  == ""){
				a +="* Rate Type is required \n";
				
			}
			 if(rateType.value=='E'){
				 if(!(rateMethod.checked ||rateMethod1.checked)) 
				 {	 
				 a +="* Rate Method is required \n";
				 if(baseRateType.value  == ""){
				 a +="* Base Rate Type is required \n";						
					} 
		       }	
			 }
			
			if(minEffRate.value  == ""){
				a +="* Minimum EFF Rate is required \n";
				
			}
			if(maxEffRate.value  == ""){
				a +="* Maximum EFF Rate is required \n";
				
			}
			if(minFlatRate.value  == ""){
				a +="* Minimum Flat Rate is required \n";
				
			}
			if(maxFlatRate.value  == ""){
				a +="* Maximum Flat Rate is required \n";
				
			}
			if(minTenure.value  == ""){
				a +="* Minimum Tenure is required \n";
				
			}
			if(maxTenure.value  == ""){
				a +="* Maximum Tenure is required \n";
				
			}
			if(validityDays  == ""){
				a +="* Validity Days is required \n";
			}
			if(expiryDate  == ""){
				a +="* Expiry Date is required \n";
			}
			if(a.match(/Product/gi)){
				document.getElementById("productButton").focus();
			}else if(a.match(/Scheme/gi)){
				schemeDesc.focus();
			}else if(a.match(/Customer/gi)){
				customerExposureLimit.focus();
			}else if(a.match(/Min/gi)){
				minAmountFin.focus();
			}else if(a.match(/Max/gi)){
				maxAmountFin.focus();
			}
			
			alert(a);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		


	
}
function chkAllEdit()
{
	//alert("test 1");
	
	document.getElementById("userButton1").style.display="none";
	//document.getElementById("userButtonEdit").style.display="none";
	document.getElementById("branchDesc").options.length = 0;
	document.getElementById("lbxBranchIds").value='';
	
	return true;
}
function chkSelective()
{
	document.getElementById("userButton1").style.display="";
	return true;
}
function chkSelectiveEdit()
{
	//alert("test 2");
	document.getElementById("userButton1").style.display="";
	//document.getElementById("userButtonEdit").style.display="";
	document.getElementById("branchDesc").options.length = '';
	document.getElementById("lbxBranchIds").value='';
	return true;
}
function chkAll()
{
	//alert("test 3");
document.getElementById("userButton1").style.display="none";
document.getElementById("branchDesc").options.length = 0;
document.getElementById("lbxBranchIds").value='';
return true;
}

function schemeDetailsModify(val,val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11,val12,val13,val14,val15,val16,val17,val18,b)
	{
		//alert("testM update mode");
		DisButClass.prototype.DisButMethod();
		var msg='',msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='',msg17='',msg18='';
		var sourcepath=document.getElementById("contextPath").value;
		
		var productId = document.getElementById("productId");
		var schemeDesc = document.getElementById("schemeDesc");
		var customerExposureLimit = document.getElementById("customerExposureLimit");
		var minAmountFin = document.getElementById("minAmountFin");
		var maxAmountFin = document.getElementById("maxAmountFin"); 
		var rateType = document.getElementById("rateType");
		var baseRateType = document.getElementById("baseRateType");
		var minEffRate=document.getElementById("minEffRate");
		var maxEffRate=document.getElementById("maxEffRate");
		var minFlatRate=document.getElementById("minFlatRate");
		var maxFlatRate=document.getElementById("maxFlatRate");
		var minTenure=document.getElementById("minTenure");
		var maxTenure=document.getElementById("maxTenure");
		var rateMethod=document.getElementById("rateMethod");
		var rateMethod1=document.getElementById("rateMethod1");
		var frequency= document.getElementById("repaymentFreq").value;
		var tenure= document.getElementById("defTenure").value;
		var validityDays= document.getElementById("validityDays").value;
		var expiryDate= document.getElementById("expiryDate").value;
		var branchId= document.getElementById("lbxBranchIds").value;
		var allselection= document.getElementById("allselection").checked;
		var singleselection= document.getElementById("singleselection").checked;
		
		
		//alert("allselection>="+allselection+"singleselection=>"+singleselection);
		
		if(allselection==false && singleselection==false)
		{
			alert("Please Select Branch Access.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(singleselection==true && branchId=='')
		{
		    alert("Please Select at Least one branch.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
					
		if(allselection)
			allselection='A';
		else
			allselection='S';
		
		//alert("branch value"+branchId);
			var parseTenure=1;
			var freqMonth=1;
			if(frequency=='M')
			{
				freqMonth=1;
			}
			else if(frequency=='B')
			{
				freqMonth=2;
			}
			else if(frequency=='Q')
			{
				freqMonth=3;
			}
			else if(frequency=='H')
			{
				freqMonth=6;
			}
			else if(frequency=='Y')
			{
				freqMonth=12;
			}
			
			if(frequency!='')
			{
				parsedFreq=parseInt(freqMonth);
				//alert("parsedFreq:----"+parsedFreq);
			}
			
			if(tenure!='')
			{
				parseTenure=parseInt(tenure);
				//alert("parseTenure:-----"+parseTenure);
			}
			
			//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
			calInsat=parseTenure/parsedFreq;
//			alert("calInsat:---"+calInsat);
		if(document.getElementById("reschAllowed").checked==true && document.getElementById("reschLockinPeriod").value=="" )
		{
		
			msg=val;
		}
		if(document.getElementById("reschAllowed").checked==true && document.getElementById("reschLockinPeriod").value=="" && document.getElementById("minimumGapResch").value=="")
		{
		
			msg1=val1;
		}
		if(document.getElementById("reschAllowed").checked==true && document.getElementById("minPeriodResch").value=="")
		{
		
			msg2=val2;
		}
		if(document.getElementById("reschAllowed").checked==true && document.getElementById("numberReschAllowedYear").value=="")
		{
		
			msg3=val3;
		}
		if(document.getElementById("reschAllowed").checked==true && document.getElementById("numberReschAllowedTotal").value=="")
		{
		
			msg4=val4;
		}
		if(msg!=''||msg1!=''||msg2!=''||msg3!=''|| msg4!='')
		{
			alert(msg+'\n'+msg1+'\n'+msg2+'\n'+msg3+'\n'+msg4);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("defrLockinPeriod").value=="" )
		{
		
			msg5=val5;
			
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("minimumGapDefr").value=="" )
		{
		
			msg6=val6;
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("maximumDefrMonthsAllowed").value=="" )
		{
		
			msg7=val7;
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("maximumDefrMonthsTotal").value=="" )
		{
		
			msg8=val8;
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("numberDefrAllowedYear").value=="" )
		{
		
			msg9=val9;
		}
		if(document.getElementById("deferralAllowed").checked==true && document.getElementById("numberDefrAllowedTotal").value=="" )
		{
		
			msg10=val10;
		}
		if(msg5!=''||msg6!=''||msg7!=''||msg8!=''||msg9!=''||msg10!='')
		{
			alert(msg5+'\n'+msg6+'\n'+msg7+'\n'+msg8+'\n'+msg9+'\n'+msg10);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("prepayLockinPeriod").value=="" )
		{
		
			msg11=val11;
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("minimumGapPrepay").value=="" )
		{
		
			msg12=val12;
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("minimumPrepayPercent").value=="" )
		{
		
			msg13=val13;
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("maximumPrepayPercent").value=="" )
		{
		
			msg14=val14;
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("numberPrepayAllowedYear").value=="" )
		{
		
			msg15=val15;
		}
		if(document.getElementById("prepayAllowed").checked==true && document.getElementById("numberPrepayAllowedTotal").value=="" )
		{
		
			msg16=val16;
		}
		if(msg11!=''||msg12!=''||msg13!=''||msg14!=''||msg15!=''||msg16!='')
		{
			alert(msg11+'\n'+msg12+'\n'+msg13+'\n'+msg14+'\n'+msg15+'\n'+msg16);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}
		if(document.getElementById("terminationAllowed").checked==true && document.getElementById("terminationLockinPeriod").value=="" )
		{
		
			msg17=val17;
		}
		if(document.getElementById("terminationAllowed").checked==true && document.getElementById("minimumGapBetPrepayAndTer").value=="" )
		{
		
			msg18=val18;
		}
	
		if(msg17!='' ||msg18!='')
		{
			alert(msg17+"\n"+msg18);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}

		else if(productId.value != "" && schemeDesc.value != "" && customerExposureLimit.value != "" && minAmountFin.value != "" 
			&& maxAmountFin.value != "" && rateType.value!="" && minEffRate.value!="" && maxEffRate.value!=="" 
				&& minFlatRate.value!=="" && maxFlatRate.value!=="" && minTenure.value!=="" && maxTenure.value!=="" && validityDays !=""&& expiryDate !="")
		{
			if(rateType.value=='E'){
				var E="";
				 if(!(rateMethod.checked ||rateMethod1.checked)) 
				 {	
					 E += "* Rate Method is required \n";
					 if(baseRateType.value  == ""){
						 E +="* Base Rate Type is required \n";						
							} 
					 alert(E);
					 DisButClass.prototype.EnbButMethod();
					 return false;
		        }
				 if(((rateMethod.checked ||rateMethod1.checked))&& baseRateType.value  == ""){
					E += "* Base Rate Type is required \n";	
					alert(E);
					DisButClass.prototype.EnbButMethod();
					return false;
					 }		 
				 				
			}
			if(!isInt(calInsat))
			{
				var agree=confirm("The Combination of tenure and frequency are incorrect");
				if(!agree){
					document.getElementById("repaymentFreq").value='';
					document.getElementById("defTenure").value='';
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled","true");
				return false;
				}
			}
			if(minTenure.value==0)
			{
				alert("* Minimum Tenure should be greater than 0.");
				DisButClass.prototype.EnbButMethod();
				return false;
			}	
			if(validate("CrSchemeMasterForm"))
			{
				document.getElementById("CrSchemeMasterForm").action=sourcepath+"/crSchemeMasterAction.do?method=saveModifyDetails&schemeId="+b+"&allselection="+allselection;
				document.getElementById("processingImage").style.display = '';
				document.getElementById("CrSchemeMasterForm").submit();
				return true;
			}
		}else
			{
			var a = "";
			if(productId.value  == ""){
				a= "* Product Id is required \n";
				
			}
			if(schemeDesc.value == ""){
				a += "* Scheme Description is required \n";
		
			}
			if(customerExposureLimit.value == ""){
				a +="* Customer Exposure Limit is required \n";
				
			}
			if(minAmountFin.value  == ""){
				a +="* Min amount Fin is required \n";
				
			}
			if(maxAmountFin.value  == ""){
				a +="* Max amount Fin is required \n";
				
			}
			
			
			if(rateType.value  == ""){
				a +="* Rate Type is required \n";
				
			}
			
			 if(rateType.value=='E'){
			 if(!(rateMethod.checked ||rateMethod1.checked)) 
			 {	 
			 a +="* Rate Method is required \n";
			 if(baseRateType.value  == "")
			    {
			 a +="* Base Rate Type is required \n";						
				} 
			 }				 				
			                          }
			 
			if(minEffRate.value  == ""){
				a +="* Minimum EFF Rate is required \n";
				
			}
			if(maxEffRate.value  == ""){
				a +="* Maximum EFF Rate is required \n";
				
			}
			if(minFlatRate.value  == ""){
				a +="* Minimum Flat Rate is required \n";
				
			}
			if(maxFlatRate.value  == ""){
				a +="* Maximum Flat Rate is required \n";
				
			}
			if(minTenure.value  == ""){
				a +="* Minimum Tenure is required \n";
				
			}
			if(maxTenure.value  == ""){
				a +="* Maximum Tenure is required \n";
			}	
			if(validityDays  == ""){
				a +="* Validity Days is required \n";
			}
			if(expiryDate  == ""){
				a +="* Expiry Date is required \n";
			}
			
			if(a.match("Product")){
				document.getElementById("productButton").focus();
			}else if(a.match("Scheme")){
				schemeDesc.focus();
			}else if(a.match("Customer")){
				customerExposureLimit.focus();
			}else if(a.match("Min")){
				minAmountFin.focus();
			}else if(a.match("Max")){
				maxAmountFin.focus();
			}
			alert(a);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		
	
}


function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("crSchemeMasterSearch").action=sourcepath+"/crSchemeMasterSearch.do?method=addScemeCodeDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("crSchemeMasterSearch").submit();
	return true;
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}


function modifyDetails(b)
{
	
	var sourcepath=document.getElementById("path").value;
	document.getElementById("crSchemeMasterSearch").action=sourcepath+"/crSchemeMasterSearch.do?method=modifyDetails&schemeId="+b;
	document.getElementById("crSchemeMasterSearch").submit();
	
}

function fnSearch(val){

	DisButClass.prototype.DisButMethod();
	document.getElementById("crSchemeMasterSearch").action="crSchemeMasterSearch.do?method=searchSchemeCode";
	if(document.getElementById("schemeId").value=="" && document.getElementById("schemeDesc").value=="")
	{
     alert(val);
     DisButClass.prototype.EnbButMethod();
     //document.getElementById("save").removeAttribute("disabled","true");
 	 return false; 
	}else{
		
	document.getElementById("processingImage").style.display = '';
	document.getElementById("crSchemeMasterSearch").submit();
	return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}

function fnFlat()
{

if(document.getElementById("rateType").value=='F')
{

	document.getElementById("rateMethod").disabled=true;
	document.getElementById("rateMethod1").disabled=true;
	document.getElementById("baseRateType").disabled=true;
	//document.getElementById("minEffRate").disabled=true;
	//document.getElementById("maxEffRate").disabled=true;
	//document.getElementById("defEffRate").disabled=true;
	document.getElementById("lov").style.display = 'none';
	document.getElementById("disabledLov").style.display = '';
	document.getElementById("minFlatRate").disabled=false;
	document.getElementById("maxFlatRate").disabled=false;
	document.getElementById("defFlatRate").disabled=false;

	document.getElementById("fixPriod").disabled=true;
	document.getElementById("reviewEvnet").disabled=true;
	document.getElementById("gapReview").disabled=true;
	document.getElementById("reviewEvnet1").disabled=true;
	document.getElementById("increse").disabled=true;
	document.getElementById("decrese").disabled=true;
	
	//document.getElementById("rateMethod").value='';
	//document.getElementById("rateMethod1").value='';
	document.getElementById("baseRateType").value='';
	//document.getElementById("minEffRate").value='';
	//document.getElementById("defEffRate").value='';
	//document.getElementById("maxEffRate").value='';
	document.getElementById("fixPriod").value='';
	document.getElementById("reviewEvnet").value='';
	document.getElementById("gapReview").value='';
	document.getElementById("reviewEvnet1").value='';
	document.getElementById("increse").value='';
	document.getElementById("decrese").value='';
}

else if(document.getElementById("rateType").value=='E' )
{
	document.getElementById("rateMethod").disabled=false;
	document.getElementById("rateMethod1").disabled=false;
	document.getElementById("baseRateType").disabled=false;
	document.getElementById("minEffRate").disabled=false;
	document.getElementById("maxEffRate").disabled=false;
	document.getElementById("defEffRate").disabled=false;
	document.getElementById("lov").style.display = '';
	document.getElementById("disabledLov").style.display = 'none';
	//document.getElementById("minFlatRate").disabled=true;
	//document.getElementById("maxFlatRate").disabled=true;
	//document.getElementById("defFlatRate").disabled=true;

	document.getElementById("fixPriod").disabled=false;
	document.getElementById("reviewEvnet").disabled=false;
	document.getElementById("gapReview").disabled=false;
	document.getElementById("reviewEvnet1").disabled=false;
	document.getElementById("increse").disabled=false;
	document.getElementById("decrese").disabled=false;
}

else if (document.getElementById("rateType").value=='E' && document.getElementById("rateMethod").checked)
{
	document.getElementById("fixPriod").disabled=true;
	document.getElementById("reviewEvnet").disabled=true;
	document.getElementById("gapReview").disabled=true;
	document.getElementById("reviewEvnet1").disabled=true;
	document.getElementById("increse").disabled=true;
	document.getElementById("decrese").disabled=true;
	
	document.getElementById("fixPriod").value='';
	document.getElementById("reviewEvnet").value='';
	document.getElementById("gapReview").value='';
	document.getElementById("reviewEvnet1").value='';
	document.getElementById("increse").value='';
	document.getElementById("decrese").value='';
}
else 
{
	document.getElementById("rateMethod").disabled=false;
	document.getElementById("rateMethod1").disabled=false;
	document.getElementById("baseRateType").disabled=false;
	//document.getElementById("minEffRate").disabled=false;
	//document.getElementById("maxEffRate").disabled=false;
	//document.getElementById("defEffRate").disabled=false;
	//document.getElementById("minFlatRate").disabled=false;
	//document.getElementById("maxFlatRate").disabled=false;
	//document.getElementById("defFlatRate").disabled=false;
	document.getElementById("fixPriod").disabled=false;
	document.getElementById("reviewEvnet").disabled=false;
	document.getElementById("gapReview").disabled=false;
	document.getElementById("reviewEvnet1").disabled=false;
	document.getElementById("increse").disabled=false;
	document.getElementById("decrese").disabled=false;
}

}

function fndisableResch(){
	if (document.getElementById("reschAllowed").checked==true){
		document.getElementById("minPeriodResch").disabled=false;
		document.getElementById("reschLockinPeriod").disabled=false;
		document.getElementById("numberReschAllowedYear").disabled=false;
		document.getElementById("numberReschAllowedTotal").disabled=false;
		document.getElementById("minimumGapResch").disabled=false;
	
	}
	else{
		document.getElementById("minPeriodResch").disabled=true;
		document.getElementById("reschLockinPeriod").disabled=true;
		document.getElementById("numberReschAllowedYear").disabled=true;
		document.getElementById("numberReschAllowedTotal").disabled=true;
		document.getElementById("minimumGapResch").disabled=true;
	}
}
function fndisableDef(){
	if(document.getElementById("deferralAllowed").checked==true){
		document.getElementById("defrLockinPeriod").disabled=false;
		document.getElementById("maximumDefrMonthsAllowed").disabled=false;
		document.getElementById("maximumDefrMonthsTotal").disabled=false;
		document.getElementById("minimumGapDefr").disabled=false;
		document.getElementById("numberDefrAllowedYear").disabled=false;
		document.getElementById("numberDefrAllowedTotal").disabled=false;
	}
	else{
		document.getElementById("defrLockinPeriod").disabled=true;
		document.getElementById("maximumDefrMonthsAllowed").disabled=true;
		document.getElementById("maximumDefrMonthsTotal").disabled=true;
		document.getElementById("minimumGapDefr").disabled=true;
		document.getElementById("numberDefrAllowedYear").disabled=true;
		document.getElementById("numberDefrAllowedTotal").disabled=true;
	}
}
function fndisablePrepay(){
	if(document.getElementById("prepayAllowed").checked==true){
		document.getElementById("numberPrepayAllowedYear").disabled=false;
		document.getElementById("minimumGapPrepay").disabled=false;
		document.getElementById("minimumPrepayPercent").disabled=false;
		document.getElementById("maximumPrepayPercent").disabled=false;
		document.getElementById("prepayLockinPeriod").disabled=false;
		document.getElementById("numberPrepayAllowedTotal").disabled=false;
	}
	else{
		document.getElementById("numberPrepayAllowedYear").disabled=true;
		document.getElementById("minimumGapPrepay").disabled=true;
		document.getElementById("minimumPrepayPercent").disabled=true;
		document.getElementById("maximumPrepayPercent").disabled=true;
		document.getElementById("prepayLockinPeriod").disabled=true;
		document.getElementById("numberPrepayAllowedTotal").disabled=true;
	}
}
function fndisableTermi(){
	if(document.getElementById("terminationAllowed").checked==true){
		document.getElementById("minimumGapTermination").disabled=false;
		document.getElementById("terminationLockinPeriod").disabled=false;
		document.getElementById("minimumGapBetPrepayAndTer").disabled=false;
	}
	else{
		document.getElementById("minimumGapTermination").disabled=true;
		document.getElementById("terminationLockinPeriod").disabled=true;
		document.getElementById("minimumGapBetPrepayAndTer").disabled=true;
	}
}		

function detailMapping(){

	document.getElementById("saveDisbaled").style.display="none";
	document.getElementById("saveEnabled").style.display='block';
	var url=document.getElementById("CrSchemeMasterForm").action="crStageMapping.do?method=stageMapping";
	window.child=window.open(url,'Account_Detail','height=300,width=900,top=200,left=250,scrollbars=yes,toolbar=no,menubar=no,location=no' );
	return true;
	
//	document.getElementById("save").removeAttribute("disabled");
//	return false;
	
}

function accountMapping(b){
		
	var url=document.getElementById("CrSchemeMasterForm").action="crStageMapping.do?method=accountMapping&schemeId="+b;
	document.getElementById("CrSchemeMasterForm").method="post";
	window.child=window.open(url,'Account_Detail','height=300,width=900,top=200,left=250,scrollbars=yes,toolbar=no,menubar=no,location=no' );	
	return true;
//	document.getElementById("save").removeAttribute("disabled");
//	return false;
}

function saveAccountDetail(){
	
	DisButClass.prototype.DisButMethod();
	var sourcePath= document.getElementById("contextPath");
	
	var ch=document.getElementsByName('chk');
	var ptable = document.getElementById('acDetailTbl');
	var lastElement = ptable.rows.length;
	
	var accountFlagList="";
	var stagId="";

	for(i=1;i<lastElement;i++){
		
		 if(document.getElementById("chk"+i).checked){
			 accountFlagList=accountFlagList + "Y" + "/";
			 stagId=stagId +document.getElementById("chk"+i).value + "/";

		 }else{
			 accountFlagList=accountFlagList + "N" + "/";
			 stagId=stagId +document.getElementById("chk"+i).value + "/";
		 }
		
	}

	document.getElementById("CrSchemeAccountingForm").method="post";
	document.getElementById("CrSchemeAccountingForm").action="crStageMapping.do?method=saveAccountDetails&accountFlagList="+accountFlagList+"&stagId="+stagId;
	document.getElementById("CrSchemeAccountingForm").method="post";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("CrSchemeAccountingForm").submit();
	
	
	return true;

}

function modifyAccountDetail(b){
	DisButClass.prototype.DisButMethod();
	var sourcePath= document.getElementById("contextPath");
	
	var ptable = document.getElementById('acDetailTbl');
	var lastElement = ptable.rows.length;
	var accountFlagList="";
	var stagId="";

	for(i=1;i<lastElement;i++){
		
	
		 if(document.getElementById("chk"+i).checked){
			 accountFlagList=accountFlagList + "Y" + "/";
			 stagId=stagId +document.getElementById("chk"+i).value + "/";
				//alert(document.getElementById("chk"+i).value);
		 }else{
			 accountFlagList=accountFlagList + "N" + "/";
			 stagId=stagId +document.getElementById("chk"+i).value + "/";
		 }
		
	}
	//alert(stagId);
	document.getElementById("CrSchemeAccountingForm").method="post";
	document.getElementById("CrSchemeAccountingForm").action="crStageMapping.do?method=modifyAccountDetails&accountFlagList="+accountFlagList+"&stagId="+stagId+"&schemId="+b;
	document.getElementById("CrSchemeAccountingForm").method="post";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("CrSchemeAccountingForm").submit();
	
	return true;
}

function allChecked()
{

	var c = document.getElementById("allchkd").checked;

	var ch=document.getElementsByName('chk');

 	var zx=0;

	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}

function detailBeforeSave()
{
	DisButClass.prototype.DisButMethod();
	alert("Please Map the details before Saving");
	DisButClass.prototype.EnbButMethod();
	//document.getElementById("save").removeAttribute("disabled");
	return false;
}


function fnChagFix(){
//	alert("sdfasdf");
//	alert(document.getElementById("rateMethod").checked);
//	alert(document.getElementById("rateMethod1").checked);
if(document.getElementById("rateMethod").checked && document.getElementById("rateType").value=='F')
{
	
	document.getElementById("fixPriod").disabled=true;
	document.getElementById("reviewEvnet").disabled=true;
	document.getElementById("gapReview").disabled=true;
	document.getElementById("reviewEvnet1").disabled=true;
	document.getElementById("increse").disabled=true;
	document.getElementById("decrese").disabled=true;
	
	document.getElementById("fixPriod").value='';
	document.getElementById("reviewEvnet").value='';
	document.getElementById("gapReview").value='';
	document.getElementById("reviewEvnet1").value='';
	document.getElementById("increse").value='';
	document.getElementById("decrese").value='';
}
else if (document.getElementById("rateMethod").checked && document.getElementById("rateType").value=='E')
{
	document.getElementById("fixPriod").disabled=true;
	document.getElementById("reviewEvnet").disabled=true;
	document.getElementById("gapReview").disabled=true;
	document.getElementById("reviewEvnet1").disabled=true;
	document.getElementById("increse").disabled=true;
	document.getElementById("decrese").disabled=true;
	
	document.getElementById("fixPriod").value='';
	document.getElementById("reviewEvnet").value='';
	document.getElementById("gapReview").value='';
	document.getElementById("reviewEvnet1").value='';
	document.getElementById("increse").value='';
	document.getElementById("decrese").value='';
}
else if (document.getElementById("rateMethod1").checked && document.getElementById("rateType").value=='E')
{
	document.getElementById("fixPriod").disabled=false;
	document.getElementById("reviewEvnet").disabled=false;
	document.getElementById("gapReview").disabled=false;
	document.getElementById("reviewEvnet1").disabled=false;
	document.getElementById("increse").disabled=false;
	document.getElementById("decrese").disabled=false;
}
}



//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[A-Za-z0-9 _]{1,50}$/;
var ck_alphaNumeric = /^[A-Za-z0-9_,\._]{1,50}$/;



function validate(formName){
 var productId = document.getElementById("lbxProductID");
 var schemeDesc = document.getElementById("schemeDesc").value;
 var customerExposureLimit = document.getElementById("customerExposureLimit").value;
 var minAmountFin = document.getElementById("minAmountFin").value;
 var maxAmountFin = document.getElementById("maxAmountFin").value;
  
  
 var errors = [];
 
 if (productId.value =="") {
  errors[errors.length] = "* Product Id is required";
 }
  
 if (!ck_numeric.test(schemeDesc)) {
  errors[errors.length] = "* Scheme Description not valid.";
 }
 if (!ck_alphaNumeric.test(customerExposureLimit)) {
  errors[errors.length] = "* Customer Exposure Limit not valid.";
 }

 if (!ck_alphaNumeric.test(minAmountFin)) {
  errors[errors.length] = "* Min Amount Fin not valid.";
 }
 if (!ck_alphaNumeric.test(maxAmountFin)) {
	  errors[errors.length] = "* Max Amount Fin not valid.";
	 }
 
 
 if (errors.length > 0) {
  reportErrors(errors);
  DisButClass.prototype.EnbButMethod();
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
	ifInstructs();
	showRangeCounters();
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
function checkInstallment()
{

	var frequency= document.getElementById("repaymentFreq").value;
	var tenure= document.getElementById("defTenure").value;
	var parseTenure=0;
	var freqMonth=0;
	if(frequency=='M')
	{
		freqMonth=1;
	}
	else if(frequency=='B')
	{
		freqMonth=2;
	}
	else if(frequency=='Q')
	{
		freqMonth=3;
	}
	else if(frequency=='H')
	{
		freqMonth=6;
	}
	else if(frequency=='Y')
	{
		freqMonth=12;
	}
	
	if(frequency!='')
	{
		parsedFreq=parseInt(freqMonth);
		//alert("parsedFreq:----"+parsedFreq);
	}
	
	if(tenure!='')
	{
		parseTenure=parseInt(tenure);
		//alert("parseTenure:-----"+parseTenure);
	}
	
	//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
	calInsat=parseTenure/parsedFreq;
	alert("calInsat:---"+calInsat);
	if(isInt(calInsat))
	{
		return true;
	}
	else
	{
		var agree=confirm("The Combination of tenure and frequency are incorrect");
		if(agree){
		}else{
		document.getElementById("repaymentFreq").value='';
		document.getElementById("defTenure").value=''; 
		}
	}
		
	
	
}



