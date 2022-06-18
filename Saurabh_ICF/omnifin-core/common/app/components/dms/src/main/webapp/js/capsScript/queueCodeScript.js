
function fnSearchQueueCode(val){
	//alert("In fnSearchQueueCode");	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("queueSearchCode").value=="" && document.getElementById("queueSearchDesc").value=='')
	{
     alert(val);
//     document.getElementById("save").removeAttribute("disabled","true");
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("queueCodeMaster").action="queueCodeMasterBehind.do?method=searchQueue";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("queueCodeMaster").submit();
    return true;
    }
	}


function newAddQueueCode(){
	//alert("In newAddQueueCode");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("queueCodeMaster").action=sourcepath+"/queueCodeMasterBehind.do?method=openAddQueueCode";	
	document.getElementById("queueCodeMaster").submit();
}


function fnEditQueueCode(queueCode){
	//alert("In fnEditQueueCode");
		DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var dpdGreater=document.getElementById("dpdGreater").value;
	var dpdLess=document.getElementById("dpdLess").value;
	var posGreater=document.getElementById("posGreater").value;
	var posLess=document.getElementById("posLess").value;
//alert(document.getElementById("lbxBranchId").value=='');
	if(validateQueueCodeMasterAddDyanavalidatiorForm(document.getElementById("queueCodeMasterForm")))
	{
		
	if((document.getElementById("dpdGreater").value=='')&& (document.getElementById("dpdLess").value=='')&&(document.getElementById("posGreater").value=='')
	  &&(document.getElementById("posLess").value=='')&& (document.getElementById("custCategory").value=='')&&(document.getElementById("custType").value=='')
	  &&(document.getElementById("lbxBranchId").value=='')&&(document.getElementById("lbxProductID").value=='')&&(document.getElementById("lbxscheme").value=='') )
	{
		//
		
		alert("* Please Select At Least One From DPD,Over Due Amount,Customer Category,Customer Type,Branch,Product and Scheme!");
//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(((dpdGreater !='')&& (dpdLess !='')) && ((parseInt(dpdGreater)) > (parseInt(dpdLess)))){
		alert("Dpd >= should be less than Dpd <=");
//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
		return false;
	
    }
	/*else if(((dpdGreater !='') && (parseInt(dpdGreater)) =='0')|| ((dpdLess !='') && (parseInt(dpdLess))=='0')){
		alert("Dpd cannot be 0");
//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
		return false;
	
    }*/
	else if(dpdGreater==''){
		alert("DPD greater than cannot be blank.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(dpdLess==''){
		alert("DPD less than cannot be blank.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(((posGreater !='')&& (posLess !='')) &&((removeComma(posGreater)) > (removeComma(posLess)))){
		alert("Over Due Amount >=  should be less than Over Due Amount <=");
//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
		return false;

    } else{
		document.getElementById("queueCodeMasterForm").action=sourcepath+"/queueCodeMasterAdd.do?method=updateQueueCodeData&queueCode="+queueCode;
	document.getElementById("processingImage").style.display = '';
		document.getElementById("queueCodeMasterForm").submit();
		return true;
    }
	}
	else{
		//alert(" In Else");
//		document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function removeComma(id)

{

    var str = id;

    //alert(id);

    var arr = str.split(',');

    var stri = '';

    for(i=0; i<arr.length; i++){

        stri = stri+arr[i];

    }  

    var amount = parseFloat(stri);

    //alert(amount);

      return amount;

}
function fnSaveQueueCode()
{
	//alert("In fnSaveQueueCode");
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	var dpdGreater=document.getElementById("dpdGreater").value;
	var dpdLess=document.getElementById("dpdLess").value;
	var posGreater=document.getElementById("posGreater").value;
	var posLess=document.getElementById("posLess").value;
	
	if(validateQueueCodeMasterAddDyanavalidatiorForm(document.getElementById("queueCodeMasterForm")))
	{
		
	if((document.getElementById("dpdGreater").value=='')&& (document.getElementById("dpdLess").value=='')&&(document.getElementById("posGreater").value=='')
	  &&(document.getElementById("posLess").value=='')&& (document.getElementById("custCategory").value=='')&&(document.getElementById("custType").value=='')
	  &&(document.getElementById("lbxBranchId").value=='')&&(document.getElementById("lbxProductID").value=='')&&(document.getElementById("lbxscheme").value==''))
	{
		alert("* Please Select At Least One From DPD,Over Due Amount,Customer Category,Customer Type,Branch,Product and Scheme!");
//		document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(((dpdGreater !='')&& (dpdLess !='')) && ((parseInt(dpdGreater)) > (parseInt(dpdLess)))){
		alert("Dpd >= should be less than Dpd <=");
//		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	
    }
	/*else if(((dpdGreater !='') && (parseInt(dpdGreater)) =='0')|| ((dpdLess !='') && (parseInt(dpdLess))=='0')){
		alert("Dpd cannot be 0");
//		document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
		return false;
	
    }*/
	else if(dpdGreater==''){
		alert("DPD greater than cannot be blank.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(dpdLess==''){
		alert("DPD less than cannot be blank.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(((posGreater !='')&& (posLess !='')) &&((removeComma(posGreater)) > (removeComma(posLess)))){
		alert("Over Due Amount >=  should be less than Over Due Amount <=");
//		document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
		return false;

    } else{
		
	document.getElementById("queueCodeMasterForm").action=sourcepath+"/queueCodeMasterAdd.do?method=saveQueueCodeDetails";
		document.getElementById("processingImage").style.display = '';
	document.getElementById("queueCodeMasterForm").submit();
	return true;
	}
	}
	else{
		//alert(" In Else");
//		document.getElementById("save").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
		return false;
	}
}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

function AllBranch(){
	if(document.getElementById("allBranch").checked==true)
	{
		document.getElementById("branch").disabled='true'	
		document.getElementById("branchButton").disabled='true'	
		document.getElementById("lbxBranchId").value=''
		document.getElementById("branch").value=''
	}
	else{
		document.getElementById("branch").disabled=''	
		document.getElementById("branchButton").disabled=''	
	}
}

function AllProduct(){
	if(document.getElementById("allProduct").checked==true )
	{
	document.getElementById("product").disabled='true'
	document.getElementById("productButton").disabled='true'	
	document.getElementById("lbxProductID").value=''
	document.getElementById("product").value=''	
	document.getElementById("scheme").disabled='true'
	document.getElementById("schemeButton").disabled='true'	
	document.getElementById("lbxscheme").value=''
	document.getElementById("scheme").value=''
	document.getElementById("allScheme").checked='true'
	}
	else{
	document.getElementById("product").disabled=''	
	document.getElementById("productButton").disabled=''	
	document.getElementById("scheme").disabled=''
	document.getElementById("schemeButton").disabled=''	
	document.getElementById("allScheme").checked=''
	}
}


function AllScheme(){
	if(document.getElementById("allScheme").checked==true)
	{
	document.getElementById("product").disabled='true'
	document.getElementById("productButton").disabled='true'	
	document.getElementById("lbxProductID").value=''
	document.getElementById("product").value=''
	document.getElementById("scheme").disabled='true'
	document.getElementById("schemeButton").disabled='true'	
	document.getElementById("lbxscheme").value=''
	document.getElementById("scheme").value=''
	document.getElementById("allProduct").checked='true' 
	}
	else{
	document.getElementById("product").disabled=''	
	document.getElementById("productButton").disabled=''	
	document.getElementById("scheme").disabled=''
	document.getElementById("schemeButton").disabled=''	
	document.getElementById("allProduct").checked=''
	}
}


function CheckBranchProScheme(){
	//alert("At Body Onload.... In CheckBranchProScheme");
	if(document.getElementById("allBranch").checked==true )	
	{
		document.getElementById("branch").disabled='true'	
		document.getElementById("branchButton").disabled='true'	
		document.getElementById("lbxBranchId").value=''
		document.getElementById("branch").value=''
	}
	if(document.getElementById("allProduct").checked==true || document.getElementById("allScheme").checked==true)
	{
		document.getElementById("product").disabled='true'
		document.getElementById("productButton").disabled='true'	
		document.getElementById("lbxProductID").value=''
		document.getElementById("product").value=''
		document.getElementById("scheme").disabled='true'
		document.getElementById("schemeButton").disabled='true'	
		document.getElementById("lbxscheme").value=''
		document.getElementById("scheme").value=''	
	}

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


