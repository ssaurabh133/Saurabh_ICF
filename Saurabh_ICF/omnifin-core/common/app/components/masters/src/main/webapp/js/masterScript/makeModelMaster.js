function searchManufacturerRecords()
{
	DisButClass.prototype.DisButMethod();
	var productCategory=document.getElementById("productCategory").value;
	var make=document.getElementById("make").value;
	var model=document.getElementById("model").value;
	var usesTypeId=document.getElementById("usesTypeId").value;	
	if(productCategory=="" && make =="" && model =="" && usesTypeId=="")
	{
		alert(" Please select at least one field.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("MakeModelMaster").action=sourcepath+"/searchMakeModelMaster.do?method=searchMakeModelRecords";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("MakeModelMaster").submit();
}
function newMakeModelMaster()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("MakeModelMaster").action=sourcepath+"/addMakeModelMaster.do?method=addNewMakeModel";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("MakeModelMaster").submit();
}

//change by saurabh singh
function clearUsageType()
{	

 	document.getElementById("usesType").value=""; 	
 	document.getElementById("txtUsesType").value=""; 	
	document.getElementById("usesTypeId").value="";	
	document.getElementById("product").value="";	
	document.getElementById("lbxProductID").value="";	
	document.getElementById("scheme").value="";	
	document.getElementById("lbxSchemeId").value="";	
	document.getElementById("productCategoryHid").value=document.getElementById("productCategory").value;	
}

//end saurabh


// Changed by Abhimanyu on 18/07/2012 
function saveMakeModelRecord()
{
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='';
	var productCategory=document.getElementById("productCategory").value;
	var state=document.getElementById("state").value;	
	var make=document.getElementById("make").value;
	var model=document.getElementById("model").value;
	var type=document.getElementById("type").value;
	var ltv=document.getElementById("ltv").value;
	var usesType=document.getElementById("usesType").value;
	var manufact=document.getElementById("manufact").value; 
	var product=document.getElementById("product").value;
	
	if(productCategory=="")
	{
	   msg1="*Product Category is required.\n";		
	}
	if(usesType=="")
	{
		msg2="*Usage Type is required.\n";	
	}
	if(product=="")
	{
		msg3="*Product is required.\n";	
	}
	if(manufact=="")
	{
		msg4="*Manufacturer is required.\n";	
	}
	if(make=="")
	{
		msg5="*Make is required.\n";
	}
	if(model=="")
	{
		msg6="*Model is required.\n";
	}
	if(type=="")
	{
		msg7="*Type is required.\n";	
	}
	if(ltv=="")
	{
		msg8="*LTV is required.\n";	
	}
	if(state=="")
	{
	    msg9="*State is required.\n";		
	}
	
	if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!='' || msg8!='' || msg9!='')
	{
		alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9);
		if(msg1!='')
		{
			document.getElementById('productCategory').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('usesType').focus();
		}
		
		else if(msg3!='')
		{
			document.getElementById('product').focus();
		}
		else if(msg4!='')
		{
			document.getElementById('manufact').focus();
		}
		else if(msg5!='')
		{
			document.getElementById('make').focus();
		}
		
		else if(msg6!='')
		{
			document.getElementById('model').focus();
		}
		else if(msg7!='')
		{
			document.getElementById('type').focus();
		}
		else if(msg8!='')
		{
			document.getElementById('ltv').focus();
		}
		else if(msg9!='')
		{
			document.getElementById('state').focus();
		}
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
	if(state==null||state==''){
		state="";

		
	}else{
		state=state+"|";
		
	}
	
	
	if(ltv != null)
	{
		var no=parseFloat(ltv);
		if(no < 0)
		{
			alert("	'LTV (%)' can not less than 0");
			document.getElementById('ltv').value="";
			document.getElementById('ltv').focus();
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return null;
		}
		if(no >100)
		{
			alert("	'LTV (%)' can not greater than 100");
			document.getElementById('ltv').value="";
			document.getElementById('ltv').focus();
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return null;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("MakeModelMasterAdd").action=sourcepath+"/MakeModelMasterSave.do?method=saveMakeModelrecord&state="+state;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("MakeModelMasterAdd").submit();
}

//Changed by Abhimanyu on 18/07/2012 
function updateMakeModelRecord()
{	
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='';
	var productCategory=document.getElementById("productCategory").value;
	var state=document.getElementById("state").value;
	var stateId=document.getElementById('stateId');
	
	
	var make=document.getElementById("make").value;
	var DListValues ="";
	var model=document.getElementById("model").value;
	var type=document.getElementById("type").value;
	var ltv=document.getElementById("ltv").value;
	var usesType=document.getElementById("usesType").value;
	var manufact=document.getElementById("manufact").value; 
	var product=document.getElementById("product").value;
	
	if(productCategory=="")
	{
	   msg1="*Product Category is required.\n";		
	}
	if(usesType=="")
	{
		msg2="*Usage Type is required.\n";	
	}
	if(product=="")
	{
		msg3="*Product is required.\n";	
	}
	if(manufact=="")
	{
		msg4="*Manufacturer is required.\n";	
	}
	if(make=="")
	{
		msg5="*Make is required.\n";
	}
	if(model=="")
	{
		msg6="*Model is required.\n";
	}
	if(type=="")
	{
		msg7="*Type is required.\n";	
	}
	if(ltv=="")
	{
		msg8="*LTV is required.\n";	
	}
	if(state=="")
	{
	    msg9="*State is required.\n";		
	}
	if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!='' ||msg8!='' || msg9!='')
	{
		alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9);
		if(msg1!='')
		{
			document.getElementById('productCategory').focus();
		}
		else if(msg2!='')
		{
			document.getElementById('usesType').focus();
		}
		
		else if(msg3!='')
		{
			document.getElementById('product').focus();
		}
		else if(msg4!='')
		{
			document.getElementById('manufact').focus();
		}
		else if(msg5!='')
		{
			document.getElementById('make').focus();
		}
		
		else if(msg6!='')
		{
			document.getElementById('model').focus();
		}
		else if(msg7!='')
		{
			document.getElementById('type').focus();
		}
		else if(msg8!='')
		{
			document.getElementById('ltv').focus();
		}
		else if(msg9!='')
		{
			document.getElementById('state').focus();
		}
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
	if(stateId!=null ||stateId!="" ){
		
		
		var stateLength = document.getElementById('stateId').options.length;
		
		
		for (var iter =0 ; iter < stateLength; iter++)
	    {
			DListValues = DListValues+stateId.options[iter].value+"/";
			//alert("DListValues"+DListValues);
	    } 
		var strstate='';
		var count=0;
		
		if(DListValues.length>count){
			while ( count<DListValues.length) {
	    	
				strstate=strstate+DListValues[count];
				count =count+1;	
			}
	    }
		
		 document.getElementById("stateId").value=strstate;
		 
		    
		   
		    var stateArr = new Array();
		    
		    var count=0;
		  
		    stateArr=DListValues.split('/');
		    for(i=0;i<stateArr.length;i++)
		    {
		    	//alert(stateArr[i]);
		    	if(stateArr[i] == state)
		    	{
		    		count=count+1;
		    	}
		    	
		    }
		}
		
	
	
	if(ltv != null)
	{
		var no=parseFloat(ltv);
		if(no < 0)
		{
			alert("	'LTV (%)' can not less than 0");
			document.getElementById('ltv').value="";
			document.getElementById('ltv').focus();
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return null;
		}
		if(no >100)
		{
			alert("	'LTV (%)' can not greater than 100");
			document.getElementById('ltv').value="";
			document.getElementById('ltv').focus();
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return null;
		}
	}
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("MakeModelMasterAdd").action=sourcepath+"/MakeModelMasterUpdate.do?method=updateMakeModelRecord&stateArr="+DListValues;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("MakeModelMasterAdd").submit();
	
	
}
