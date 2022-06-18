function newAddCourtName(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	document.getElementById("courtNameMasterForm").action=sourcepath+"/courtNameMaster.do?method=openAddCourtName";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("courtNameMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}


function saveCourtName(){
	
	DisButClass.prototype.DisButMethod();
	var courtType = document.getElementById("courtType");
	var lbxCourtTypeCode = document.getElementById("lbxCourtTypeCode");
	var branch = document.getElementById("branch");
	var lbxBranchId = document.getElementById("lbxBranchId");
	var courtNameCode = document.getElementById("courtNameCode");
	var courtNameDesc = document.getElementById("courtNameDesc");
	
	if(trim(courtNameDesc.value) == ''||trim(courtNameCode.value) == '' ||trim(courtType.value) == '' ||trim(branch.value) == ''
		||trim(lbxCourtTypeCode.value) == ''||trim(lbxBranchId.value) == ''){
		 var msg= '';
 		
		 if(trim(courtType.value) == ''||trim(lbxCourtTypeCode.value) == '')
	 			msg += '* Court Type is required.\n';
		 
		 if(trim(branch.value) == ''||trim(lbxBranchId.value) == '')
	 			msg += '* Branch id is required.\n';
		 
		 if(trim(courtNameCode.value) == '')
	 			msg += '* Court Name Code is required.\n';
			 
		 if(trim(courtNameDesc.value) == '')
 			msg += '* Court Name Description is required.\n';

 		
 		

		 alert(msg);
	 		
		 if(msg.match("Type")){
	 			courtTypeButton.focus();
		 }
 		 
		else if(msg.match("id")){
	 	 			branchButton.focus();
	 	 		}
		else if(msg.match("Code")){
	 	 			courtNameCode.focus();
	 	 		}
		else if(msg.match("Description")){
			courtNameDesc.focus();
 		
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	
	 }else if(validate("courtNameMasterForm")){
		
			document.getElementById("courtNameMasterForm").action="courtNameMasterAdd.do?method=saveCourtNameDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("courtNameMasterForm").submit();
			return true;
	}
	
}


function fnSearchCourtName(val){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("courtNameCode").value==""&& document.getElementById("courtNameDesc").value=="" 
		&& document.getElementById("branch").value=="" && document.getElementById("lbxBranchId").value==""
			&& document.getElementById("courtType").value==""&& document.getElementById("lbxCourtTypeCode").value=="" )
	{
     alert(val);
     
     document.getElementById("courtNameCode").focus();

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
		document.getElementById("courtNameMasterForm").action=sourcepath+"/courtNameMasterBehind.do";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("courtNameMasterForm").submit();
	return true;
	}
}

function fnEditCourtName(){
	
	DisButClass.prototype.DisButMethod();
	var courtType = document.getElementById("courtType");
	var lbxCourtTypeCode = document.getElementById("lbxCourtTypeCode");
	var branch = document.getElementById("branch");
	var lbxBranchId = document.getElementById("lbxBranchId");
	var courtNameCode = document.getElementById("courtNameCode");
	var courtNameDesc = document.getElementById("courtNameDesc");
	
	if(trim(courtNameDesc.value) == ''||trim(courtNameCode.value) == '' ||trim(courtType.value) == '' ||trim(branch.value) == ''
		||trim(lbxCourtTypeCode.value) == ''||trim(lbxBranchId.value) == ''){
		 var msg= '';
 		
		 if(trim(courtType.value) == ''||trim(lbxCourtTypeCode.value) == '')
	 			msg += '* Court Type is required.\n';
		 
		 if(trim(branch.value) == ''||trim(lbxBranchId.value) == '')
	 			msg += '* Branch id is required.\n';
		 
		 if(trim(courtNameCode.value) == '')
	 			msg += '* Court Name Code is required.\n';
			 
		 if(trim(courtNameDesc.value) == '')
 			msg += '* Court Name Description is required.\n';

 		
 		

		 alert(msg);
	 		
		 if(msg.match("Type")){
	 			courtTypeButton.focus();
		 }
 		 
		else if(msg.match("id")){
	 	 			branchButton.focus();
	 	 		}
		else if(msg.match("Code")){
	 	 			courtNameCode.focus();
	 	 		}
		else if(msg.match("Description")){
			courtNameDesc.focus();
	 	 		
	 		}
 		
		DisButClass.prototype.EnbButMethod();
		return false;
	 
	 }else {
		
			document.getElementById("courtNameMasterForm").action="courtNameMasterAdd.do?method=updateCourt";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("courtNameMasterForm").submit();
			return true;
	}
	
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;

function validate(formName){
	
	var courtNameCode = document.getElementById("courtNameCode");
	

	var errors = [];
 
	if (!ck_numeric.test(courtNameCode.value)) {
		 if(trim(courtNameCode.value) != ''){
		 	errors[errors.length] = "* court name Code is invalid.";
			DisButClass.prototype.EnbButMethod();
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
	 	if(msg.match("courtNameCode")){
	 		courtNameCode.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}




function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}


