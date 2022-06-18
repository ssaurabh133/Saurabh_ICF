function trim(str) {
	return ltrim(rtrim(str));
	}


function removeComma(id)
{
    var str = id;
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    var amount = parseFloat(stri);
	return amount;
}

function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("chargeMasterSearchForm").action=sourcepath+"/chargeMasterSearch.do?method=openAddCharge";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("chargeMasterSearchForm").submit();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;

}
function saveCharge()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var productId = document.getElementById("productId");
	var chargeCode = document.getElementById("chargeCode");
	var chargeBPType = document.getElementById("chargeBPType");
	var chargeAmount = document.getElementById("chargeAmount");
	var chargeMethod = document.getElementById("chargeMethod").value;
	var calculatedOn = document.getElementById("calculatedOn").value;
	var chargeType = document.getElementById("chargeType");
	if(trim(productId.value) == ''||trim(chargeCode.value) == ''||trim(chargeBPType.value) == ''||trim(chargeAmount.value) == '' ||trim(chargeType.value) == ''){
			var msg= '';
			if(trim(productId.value) == '')
				msg += '* Product is required.\n';
			if(trim(chargeCode.value) == '')
				msg += '* Charge Code is required.\n';
			if(trim(chargeBPType.value) == '')
				msg += '* Charge BP Type is required.\n';
			if(trim(chargeType.value) == '')
				msg += '* Charge Type is required.\n';
			if(trim(chargeAmount.value) == '')
				msg += '* Charge Amount is required.\n';
			
			if(msg.match("Product")){
				document.getElementById("productButton").focus();
			}else if(msg.match("Code")){
				document.getElementById("chargeButton").focus();
			}else if(msg.match("BP")){
				document.getElementById("chargeBPTypeButton").focus();
			}else if(msg.match("Type")){
				document.getElementById("chargeType").focus();
			}else if(msg.match("Amount")){
				chargeAmount.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}
		else if((trim(chargeMethod) == 'P') && trim(calculatedOn) == '')
		{
			alert("* Calculated On is required.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else{
			document.getElementById("chargeMasterForm").action=sourcepath+"/chargeMasterAdd.do?method=saveChargeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("chargeMasterForm").submit();
			return true;
		}

}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("chargeMasterForm").action=sourcepath+"/chargeMasterSearch.do?method=openAddCharge&chargeCode="+a;
	document.getElementById("chargeMasterForm").submit();
	
}
function editCharge(b)
{
	//alert(b);
	var sourcepath=document.getElementById("path").value;
	document.getElementById("chargeMasterSearchForm").action=sourcepath+"/chargeMasterSearch.do?method=openEditCharge&chargeSearchId="+b;
	document.getElementById("chargeMasterSearchForm").submit();
	
	
}
function fnEditCharge(b){
	//alert(b);
	DisButClass.prototype.DisButMethod();
	var productId = document.getElementById("productId");
	var chargeCode = document.getElementById("chargeCode");
	var chargeBPType = document.getElementById("chargeBPType");
	var chargeAmount = document.getElementById("chargeAmount");
	var chargeMethod = document.getElementById("chargeMethod").value;
	var calculatedOn = document.getElementById("calculatedOn").value;
	var chargeType = document.getElementById("chargeType");
	
	if(trim(productId.value) == ''||trim(chargeCode.value) == ''||trim(chargeBPType.value) == ''||trim(chargeAmount.value) == '' ||trim(chargeType.value) == ''){
			var msg= '';
			if(trim(productId.value) == '')
				msg += '* Product is required.\n';
			if(trim(chargeCode.value) == '')
				msg += '* Charge Code is required.\n';
			if(trim(chargeBPType.value) == '')
				msg += '* Charge BP Type is required.\n';
			if(trim(chargeType.value) == '')
				msg += '* Charge Type is required.\n';
			if(trim(chargeAmount.value) == '')
				msg += '* Charge Amount is required.\n';
			
			
			if(msg.match("Product")){
				document.getElementById("productButton").focus();
			}else if(msg.match("Code")){
				document.getElementById("chargeButton").focus();
			}else if(msg.match("BP")){
				document.getElementById("chargeBPTypeButton").focus();
			}else if(msg.match("Type")){
				document.getElementById("chargeType").focus();
			}else if(msg.match("Amount")){
				chargeAmount.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}
		else if((trim(chargeMethod) == 'P') && trim(calculatedOn) == '')
		{
			alert("* Calculated On is required.");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else{
		    document.getElementById("chargeMasterForm").action="chargeMasterAdd.do?method=updateCharge&chargeId="+b;
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("chargeMasterForm").submit();
		}

}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 

	DisButClass.prototype.DisButMethod();
	if(document.getElementById("chargeSearchCode").value=='' && document.getElementById("chargeSearchDes").value==''&&document.getElementById("productSearchId").value=='' && document.getElementById("schemeSearchId").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else
	{
		document.getElementById("chargeMasterSearchForm").action="chargeMasterBehind.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("chargeMasterSearchForm").submit();
		return true;
	}
}	

function fndisable(val,val1){
	if (document.getElementById("tdsStatus").checked==true){
		document.getElementById("tdsRate").disabled=false;
		//document.getElementById("tdsRate").value='';
	}
	else{
		document.getElementById("tdsRate").disabled=true;
		document.getElementById("tdsRate").value='';
	}
	
	if (document.getElementById("taxStatus").checked==true){
		document.getElementById("taxRate1").disabled=false;
		//document.getElementById("taxRate1").value=val;
	}
	else{
		document.getElementById("taxRate1").disabled=true;
		document.getElementById("taxRate1").value='';
		
	}
	
	if (document.getElementById("taxStatus").checked==true){
		document.getElementById("taxRate2").disabled=false;
		//document.getElementById("taxRate2").value=val1;
	}
	else{
		document.getElementById("taxRate2").disabled=true;
		document.getElementById("taxRate2").value='';
	}
}		

function fnApplicable(){
	if (document.getElementById("chargeMethod").value=='P'){
		//alert(document.getElementById("chargeMethod").value);
		  document.getElementById("lov").style.display = '';
		  document.getElementById("disabledLov").style.display = 'none';
		  document.getElementById("mincalculatedOn").disabled=false;
	}
	else 
	{
		//alert(document.getElementById("chargeMethod").value);
		 document.getElementById("lov").style.display = 'none';
		 document.getElementById("disabledLov").style.display = '';
		 document.getElementById("mincalculatedOn").disabled=true;
		 document.getElementById("mincalculatedOn").value='';
		 document.getElementById("calculatedOn").value='';
		 document.getElementById("lbxCalculatedOn").disabled=true;
	}
}


function fnApplicable1(val){
	//alert("in min charge method"+val);
	if (document.getElementById("chargeMethod").value=='P')
	{
		//alert(document.getElementById("chargeMethod").value);
	 var rate = document.getElementById(val).value;
	 //alert(rate);
	 if(rate=='')
		 	{
		 		rate=0;
		 	}
	  var intRate = removeComma(document.getElementById(val).value)
		 	 // var intRate = parseInt(rate);
		 	   // alert(intRate);
		 	    if(intRate>=0 && intRate<=100)
		 	      {
		 		    return true;
		 	       }
		 	        else
		 	           {
		 		        alert("Please Enter the value b/w 0 to 100");
		 		        document.getElementById(val).value="";
		 		     // alert(document.getElementById(val).value);
		 		        return false;
		 	         }
		 	
	}
	else
	{
		return true;
	}
}


function fnMinChargeMehtod(val){
	//alert("in min charge method"+val);
	if (document.getElementById("chargeMethod").value=='P')
	{
		//alert(document.getElementById("chargeMethod").value);
	 var rate = document.getElementById(val).value;
	 //alert(rate);
	 if(rate=='')
		 	{
		 		rate=0;
		 	}
	  var intRate = removeComma(document.getElementById(val).value)
		 	 // var intRate = parseInt(rate);
		 	   // alert(intRate);
		 	    if(intRate>=0 && intRate<=100)
		 	      {
		 		    return true;
		 	       }
		 	        else
		 	           {
		 		        alert("Please Enter the value b/w 0 to 100");
		 		        //document.getElementById(val).value="";
		 		       document.getElementById("chargeAmount").value="";
		 		      document.getElementById("minChargeAmount").value="";
		 		      
		 		     // alert(document.getElementById(val).value);
		 		        return false;
		 	         }
		 	
	}
	else
	{
		return true;
	}
}

function fnDisabled()
{
	if(document.getElementById("chargeMethod").value=='F')
	{
		document.getElementById("per").style.display = 'none';
		document.getElementById("flat").style.display = '';
	}
	else
	{
		document.getElementById("flat").style.display = 'none';
		document.getElementById("per").style.display = '';
	}
}
