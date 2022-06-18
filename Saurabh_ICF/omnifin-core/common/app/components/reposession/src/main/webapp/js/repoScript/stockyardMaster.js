function newAddStockyard(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("stockyardMasterForm").action=sourcepath+"/repoStockyardMasterDispatch.do?method=openAddStockyard";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stockyardMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();

	

}


function saveStockyard(){
	
	DisButClass.prototype.DisButMethod();
	
	var lbxUserId = document.getElementById("lbxUserId");
	var stockyardDesc = document.getElementById("stockyardDesc");
	var stockyardButton= document.getElementById("stockyardButton");
	
	 if(trim(lbxUserId.value) == ''||trim(stockyardDesc.value) == ''){
		 var msg= '';
		 if(trim(lbxUserId.value) == '')
	 			msg += '* Stockyard Code is required.\n';
 		if(trim(stockyardDesc.value) == '')
 			msg += '* Stockyard Description is required.\n';
 		
 		 		 		
 		if(msg.match("Code")){
 			stockyardButton.focus();
 		}else if(msg.match("Description")){
 			stockyardDesc.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }
	 else if(validate("stockyardMasterAddForm")){
 
		 document.getElementById("stockyardMasterAddForm").action="repoStockyardMasterDispatch.do?method=saveStockyardDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("stockyardMasterAddForm").submit();

	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}
function fnUpdateStockyard(){
	
	DisButClass.prototype.DisButMethod();
	var lbxUserId = document.getElementById("lbxUserId");
	var stockyardDesc = document.getElementById("stockyardDesc");
	var stockyardButton= document.getElementById("stockyardButton");
	if(trim(lbxUserId.value) == ''||trim(stockyardDesc.value) == ''){
		 var msg= '';
		 if(trim(lbxUserId.value) == '')
	 			msg += '* Stockyard Code is required.\n';
		if(trim(stockyardDesc.value) == '')
			msg += '* Stockyard Description is required.\n';
		
 		 		 		
 		if(msg.match("Code")){
 			stockyardButton.focus();
 		}else if(msg.match("Description")){
 			stockyardDesc.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
	 else if(validate("stockyardMasterAddForm")){


			document.getElementById("stockyardMasterAddForm").action="repoStockyardMasterDispatch.do?method=updateStockyard";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("stockyardMasterAddForm").submit();

	return true;
}
	 else
	 {
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 }

}


function fnSearchStockyard(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("stockyardMasterForm").action="repoStockyardMasterDispatch.do?method=searchStockYard";
	if(document.getElementById("stockyardDesc").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("stockyardMasterForm").submit();
	return true;
	}
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;


function validate(formName){
	
	var lbxUserId = document.getElementById("lbxUserId");
	var stockyardButton= document.getElementById("stockyardButton");
	
  

 var errors = [];
 

 if (!ck_numeric.test(lbxUserId.value)) {
	 if(trim(lbxUserId.value) != ''){
	 	errors[errors.length] = "* Stockyard Code is invalid.";
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
	 	if(msg.match("Stockyard") ){
	 		stockyardButton.focus();
		
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}




