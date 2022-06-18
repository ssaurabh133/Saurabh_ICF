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

function newAddDealer(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("dealerMaster").action=sourcepath+"/dealerMasterSearch.do?method=openAddDealer";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("dealerMaster").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}

// Surendra Added fnSearchDealer()/newAddSubDealer() here 17-10-12...
function newAddSubDealer(){
	//alert("In Sub Dealer Add...")
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("subDealerMaster").action=sourcepath+"/subDealerMasterSearch.do?method=openAddSubDealer";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("subDealerMaster").submit();
	//alert(" Close...")
	return true;
	DisButClass.prototype.EnbButMethod();
	//alert("Close..")
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	//alert("Close.")
}
function fnSearchSubDealer(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("subDealerSearchCode").value==""&& document.getElementById("subDealerSearchDes").value==""&& document.getElementById("dealerSearchDesc").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
	document.getElementById("subDealerMaster").action=sourcepath+"/subDealerBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("subDealerMaster").submit();
	return true;
	}
	}
function fnEditSubDealer(){
	//alert("fnEditSubDealer");
	DisButClass.prototype.DisButMethod();
	var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	var subDealerDes = document.getElementById("subDealerDes");
	var dealerDes=document.getElementById('dealerDes');	
	if(trim(subDealerDes.value) == ''||trim(dealerDes.value) == '' ){
			var msg= '';
			if(trim(subDealerDes.value) == '')
				msg += '* Sub Dealer Description is required.\n';	
			if(trim(dealerDes.value) == ''){
				msg += '* Dealer is required.\n';
				document.getElementById("dealerDes").focus();
		}
			if(msg.match("Description")){
				document.getElementById("subDealerDes").focus();
			}			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
			if(trim(subDealerDes.value) != ''){
				if (!ck_alphaNumeric.test(subDealerDes.value)) {
					alert("* Sub Dealer Description is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }}
			document.getElementById("subDealerMaster").action="subDealerMasterAdd.do?method=updateSubDealer";	
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("subDealerMaster").submit();
			return true;
		}

}

function fnSaveSubDealer(){
	DisButClass.prototype.DisButMethod();
	var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	var subDealerDes = document.getElementById("subDealerDes");	
	// var dealerID = document.getElementById("dealerID");	
	//alert("dealerDes"+document.getElementById('dealerDes'));
	var dealerDes=document.getElementById('dealerDes');	
	if(trim(subDealerDes.value) == '' || trim(dealerDes.value) == '' ){
		var msg= '';
			if(trim(subDealerDes.value) == '')
				msg += '* Sub Dealer Description is required.\n';
	if(trim(dealerDes.value) == ''){
			msg += '* Dealer is required.\n';
			document.getElementById("dealerDes").focus();
	}
//	if(trim(dealerID.value) == '')
//			msg += '* Dealer is required.\n';
			
			if(msg.match("Description")){
				subDealerDes.focus();
			}
//			else if(msg.match("Bank")){
//				subDealerBankAC.focus();
//			}
			
	alert(msg);

	DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			if(trim(subDealerDes.value) != ''){
				if (!ck_alphaNumeric.test(subDealerDes.value)) {
					alert("* Sub Dealer Description is invalid.");
					DisButClass.prototype.EnbButMethod();
					return false;
				 }}
			    document.getElementById("subDealerMaster").action="subDealerMasterAdd.do?method=saveSubDealerDetails";	
				document.getElementById("processingImage").style.display = '';
				document.getElementById("subDealerMaster").submit();
				return true;
			}

}


function fnEditDealer(){
	DisButClass.prototype.DisButMethod();
	//var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	var ck_alphaNumeric = /^[A-Za-z0-9\-\/\\ ]{1,50}$/;
	var dealerDes = document.getElementById("dealerDes");
	var dealerType = document.getElementById("dealerType");
	var accountNo=document.getElementById('accountNo');
	var bankCode=document.getElementById('bankCode'); 			
	var bankBranchName=document.getElementById('bankBranchName');
	var userDesc=""
		var DListValues ="";
	var userText=document.getElementById('lbxUserSearchId');
	var userDesc=document.getElementById('userDesc');
	var phoneOff = document.getElementById('phoneOff');
	var pan= document.getElementById('pan');
	
	if(userDesc!=null ||userDesc!="" ){
		
	
	var userLength = document.getElementById('userDesc').options.length;
	
	
	for (var iter =0 ; iter < userLength; iter++)
    {
		DListValues = DListValues+userDesc.options[iter].value+"/";
		//alert("DListValues"+DListValues);
    } 
	var struser='';
	var count=0;
	
	if(DListValues.length>count){
		while ( count<DListValues.length) {
    	
			struser=struser+DListValues[count];
			count =count+1;	
		}
    }
	
	 document.getElementById("userDesc").value=struser;
	 var userId = document.getElementById("lbxUserSearchId").value;
	    
	   
	    var user = new Array();
	    
	    var count=0;
	  
	    user=DListValues.split('/');
	    for(i=0;i<user.length;i++)
	    {
	    	//alert(user[i]);
	    	if(user[i] == userId)
	    	{
	    		count=count+1;
	    	}
	    	//alert(count);
	    }
	}
	  	//alert(user);
	if(trim(dealerDes.value) == ''||trim(dealerType.value) == ''){
			var msg= '';
			if(trim(dealerDes.value) == '')
				msg += '* Dealer Description is required.\n';
			if(trim(dealerType.value) == '')
				msg += '* Dealer Type is required.\n';
			

          
			if(msg.match("Description")){
				dealerDes.focus();
			}else if(msg.match("Type")){
				document.getElementById("dealerButton").focus();
			}
			
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			if(phoneOff.value!='')
			{
			var mob=phoneOff.value;
			mob=mob.length;
			if(mob!=10)
			{
				alert("Mobile no should be 10 digit");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			}
			if(trim(dealerDes.value) != ''){
				
				if (!ck_alphaNumeric.test(dealerDes.value)) {
						alert("* Dealer Description is invalid.");
						DisButClass.prototype.EnbButMethod();
						return false;
					 }
				if(trim(accountNo.value) != ''){
					
					if (!ck_alphaNumeric.test(accountNo.value)) {
							alert("Account No  is invalid.");
							DisButClass.prototype.EnbButMethod();
							return false;}
						 }
				if( email.value!=""  ){
					
					var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
					if(!valid_email.test(email.value)){
						alert("Invalid Email Id");
						document.getElementById("email").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}
				
// Kumar Aman Changes Started
				
				if( pan.value!=""  ){
					
					var valid_pan=/^[A-Z]{5}\d{4}[A-Z]{1}$/;
					if(!valid_pan.test(pan.value) || pan.value.length != 10){
						alert("Invalid PAN NO");
						document.getElementById("pan").focus();
						DisButClass.prototype.EnbButMethod();
						return false;
					}
				}
					
// Kumar Aman Changes Ended
			}

			document.getElementById("dealerMaster").action="dealerMasterAdd.do?method=updateDealer&user="+DListValues;
			
			//alert(document.getElementById("dealerMaster").action);
			document.getElementById("processingImage").style.display = '';
			document.getElementById("dealerMaster").submit();
			return true;
		}

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

function fnSaveDealer(){
	DisButClass.prototype.DisButMethod();
	
	//var ck_alphaNumeric = /^[A-Za-z0-9\- ]{1,50}$/;
	  var ck_alphaNumeric = /^[A-Za-z0-9\-\/\\ ]{1,50}$/;
	var dealerDes = document.getElementById("dealerDes");
	var dealerType = document.getElementById("dealerType");
	var userText=document.getElementById('lbxUserSearchId');
	var accountNo=document.getElementById('accountNo');
	var bankCode=document.getElementById('bankCode'); 			
	var bankBranchName=document.getElementById('bankBranchName');
	var userDesc="";
	var phoneOff = document.getElementById('phoneOff');
	userDesc=document.getElementById('lbxUserSearchId').value;
	// add by saorabh
	var addressDealer1 = document.getElementById('addressDealer1').value;
	var country = document.getElementById('country').value;
	var state = document.getElementById('state').value;
	var dist =  document.getElementById('dist').value;
	var pincode	= document.getElementById('pincode').value;
	// end by saorabh
	
	// Changes started by Kumar Aman
	var pan = document.getElementById('pan').value;
	// Changes ended by Kumar Aman
	if(userDesc==null||userDesc==''){
		userDesc="";

		
	}else{
		userDesc=userDesc+"|";
	}
	
	if(trim(dealerDes.value) == '' || trim(dealerType.value) == ''){
			var msg= '';
			if(trim(dealerDes.value) == '')
				msg += '* Dealer Description is required.\n';
			if(trim(dealerType.value) == '')
				msg += '* Dealer Type is required.\n';
						
		/*	if((userText.value) == '')
				msg += '* User Name is required.\n';
			*/			
			if(msg.match("Description")){
				dealerDes.focus();
			}else if(msg.match("Type")){
				document.getElementById("dealerButton").focus();
			}
			
			
			
			/*else if(msg.match("User")){
				document.getElementById("levelButtonEdit").focus();
			}*/
          
			
			alert(msg);

			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			if(phoneOff.value!='')
			{
			var mob=phoneOff.value;
			mob=mob.length;
			if(mob!=10)
			{
				alert("Mobile no should be 10 digit");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			}
			if(trim(dealerDes.value) != ''){
				
				if (!ck_alphaNumeric.test(dealerDes.value)) {
						alert("* Dealer Description is invalid.");
						DisButClass.prototype.EnbButMethod();
						return false;
					 }
				
			}
			if(trim(accountNo.value) != ''){
				
				if (!ck_alphaNumeric.test(accountNo.value)) {
						alert("Account No  is invalid.");
						DisButClass.prototype.EnbButMethod();
						return false;}
					 }
			if( email.value!=""  ){
				
				var valid_email= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/  ;
				if(!valid_email.test(email.value)){
					alert("Invalid Email Id");
					document.getElementById("email").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			
			
			// add by saorabh
	
			if(trim(addressDealer1) =='')
				{
				alert("Address is required");
				document.getElementById("addressDealer1").focus();
				DisButClass.prototype.EnbButMethod();
				return false;
				}
			if(trim(country) =='')
			{
			alert("Country is required");
			document.getElementById("country").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			if(trim(state) =='')
			{
			alert("State is required");
			document.getElementById("state").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			if(trim(dist) =='')
			{
			alert("Dist is required");
			document.getElementById("dist").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			if(trim(pincode) =='')
			{
			alert("Pincode is required");
			document.getElementById("pincode").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
			}
			// end by saorabh
			
			
			    document.getElementById("dealerMaster").action="dealerMasterAdd.do?method=saveDealerDetails&userDesc="+userDesc;
	
				document.getElementById("processingImage").style.display = '';
				document.getElementById("dealerMaster").submit();
				return true;
			}
	
// changes started by Kumar Aman
	
	if( pan.value!=""  ){
		
		var valid_pan=/^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if(!valid_pan.test(pan) || pan.length != 10){
			alert("Invalid PAN NO");
			document.getElementById("pan").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
		
	// Changes Ended by Kumar Aman

}

function fnSearchDealer(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("dealerId").value==""&& document.getElementById("dealerSearchDes").value=="")
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();
//    document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
	document.getElementById("dealerMaster").action="dealerMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("dealerMaster").submit();
	return true;
	}
	}

function newpage( a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("dealerMaster").action=sourcepath+"/dealerMasterSearch.do?method=openAddDealer&dealerId="+a;
	document.getElementById("dealerMaster").submit();
	
}

function editpageDealer(b)
{
	var sourcepath=document.getElementById("path").value;
	
	document.getElementById("dealerMaster").action=sourcepath+"/dealerMasterSearch.do?method=openEditDealer&dealerId="+b;
	document.getElementById("dealerMaster").submit();
}



function newpage( a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("bankmaster").action=sourcepath+"/bankMasterSearch.do?method=openAddBank&bankCode="+a;
	document.getElementById("bankmaster").submit();
	
}

function editpage(b)
{
	var sourcepath=document.getElementById("path").value;
	
	document.getElementById("bankmaster").action=sourcepath+"/bankMasterSearch.do?method=openEditBank&bankSearchCode="+b;
	document.getElementById("bankmaster").submit();
}

//function for add
function newAdd(){
		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("path").value;
		document.getElementById("bankmaster").action=sourcepath+"/bankMasterSearch.do?method=openAddBank";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("bankmaster").submit();
		return true;
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}

//function for search
function fnSearch(val){

	
	            DisButClass.prototype.DisButMethod();


				if(document.getElementById("bankSearchCode").value=='' && document.getElementById("bankSearchName").value=='')
				{
					alert(val);

					DisButClass.prototype.EnbButMethod();
//					document.getElementById("save").removeAttribute("disabled","true");

					return false;
				}
				else{
			        document.getElementById("bankmaster").action="bankMasterBehind.do";
			        document.getElementById("processingImage").style.display = '';
			        document.getElementById("bankmaster").submit();
			        return true;
				}
			}
	

//function for save
function fnSave(){
	DisButClass.prototype.DisButMethod();
	var bankCode = document.getElementById("bankCode");
	var bankName = document.getElementById("bankName");

	
	if(trim(bankCode.value) == ''||trim(bankName.value) == ''){
			var msg= '';
			if(trim(bankCode.value) == '')
				msg += '* Bank Code is required.\n';
			if(trim(bankName.value) == '')
				msg += '* Bank Name is required.\n';
			
			if(msg.match("Code")){
				bankCode.focus();
			}else if(msg.match("Name")){
				bankName.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			var ck_numeric = /^[A-Za-z_0-9']{1,50}$/;
			var msg= '';
			if (!ck_numeric.test(bankCode.value)) {
				
		    	if(trim(bankCode.value) != ''){
			   	  msg += "* Bank Code is invalid.\n";
			   	  bankCode.focus();
		    	}		    	
		    	
		    	alert(msg);
		    	DisButClass.prototype.EnbButMethod();
		    	//document.getElementById("save").removeAttribute("disabled","true");
		    	return false;
		    	}
    		document.getElementById("bankmaster").action="bankMasterAdd.do?method=saveBankDetails";
    		document.getElementById("processingImage").style.display = '';
			document.getElementById("bankmaster").submit();
			return true;
		}

}

	

function fnEdit(){
	DisButClass.prototype.DisButMethod();
	var bankName = document.getElementById("bankName");

	if(trim(bankName.value) == ''){
			var msg= '';
			if(trim(bankName.value) == '')
				msg += '* Bank Name is required.\n';
			
			if(msg.match("Name")){
				bankName.focus();
			}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}else{
			
			document.getElementById("bankmaster").action="bankMasterAdd.do?method=updateBank";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("bankmaster").submit();
			return true;
		}

}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}



//Department Master .............

function newAddDept(){

	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("departmentMaster").action=sourcepath+"/departmentMasterSearch.do?method=openAddDept";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("departmentMaster").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}

function fndeptSave(){

	DisButClass.prototype.DisButMethod();
	var departmentDes = document.getElementById("departmentDes");
	
	 if(trim(departmentDes.value) == ''){
		 var msg= '';
		if(trim(departmentDes.value) == '')
			msg += '* Department Description is required.\n';
		 		 		
		if(msg.match("Department")){
			departmentDes.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 
	 }else{
		document.getElementById("departmentMaster").action="departmentMasterAdd.do?method=saveDepartmentDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("departmentMaster").submit();
		return true;
	}

}

function fnSearchDepartment(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("departmentId").value==""&&document.getElementById("departmentSearchDes").value=='')
	{
     alert(val);
     DisButClass.prototype.EnbButMethod();
//     document.getElementById("save").removeAttribute("disabled","true");
	 return false; 
	}else{
	document.getElementById("departmentMaster").action="departmentMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("departmentMaster").submit();
    return 
    }
	}

function newpage( a)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("departmentMaster").action=sourcepath+"/departmentMasterSearch.do?method=openAddDepartment&departmentId="+a;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("departmentMaster").submit();
	
}

function editpageDepartment(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("departmentMaster").action=sourcepath+"/departmentMasterSearch.do?method=openEditDepartment&DepartmentId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("departmentMaster").submit();
}

function fnEditDepartment(){
	
	DisButClass.prototype.DisButMethod();
	var departmentDes = document.getElementById("departmentDes");
	
	 if(trim(departmentDes.value) == ''){
		 var msg= '';
		if(trim(departmentDes.value) == '')
			msg += '* Department Description is required.\n';
		 		 		
		if(msg.match("Department")){
			departmentDes.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 
	 }else{
		document.getElementById("departmentMaster").action="departmentMasterAdd.do?method=updateDepartment";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("departmentMaster").submit();
		return true;
	}

}
//Reason Master 

function newAddReason(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/reasonMasterSearch.do?method=openAddReason";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reasonMaster").submit();
	return true;
}

function fnreasonSave(){
	
	DisButClass.prototype.DisButMethod();
	var reasonType = document.getElementById("reasonType");
	var reasonShortcode = document.getElementById("reasonShortcode");
	var reasonDes = document.getElementById("reasonDes");

	
	if(trim(reasonType.value) == ''||trim(reasonDes.value) == ''||trim(reasonShortcode.value) == ''){
		var msg= '';
		if(trim(reasonType.value) == '')
			msg += '* Reason Type is required.\n';
		if(trim(reasonShortcode.value) == '')
			msg += '* Reason Short Code is required.\n';
		if(trim(reasonDes.value) == '')
			msg += '* Reason Description is required.\n';
		
		if(msg.match("Type")){
			document.getElementById("reasonTypeButton").focus();
		}else if(msg.match("Description")){
			reasonDes.focus();
		}
		else if(msg.match("Short")){
			reasonShortcode.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("reasonMaster").action="reasonMasterAdd.do?method=saveReasonDetails";
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
	document.getElementById("reasonMaster").action="reasonMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("reasonMaster").submit();
	
}

function newpage( a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/reasonMasterSearch.do?method=openAddReason&ReasonId="+a;
	document.getElementById("reasonMaster").submit();
	
}

function editpageReason(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("reasonMaster").action=sourcepath+"/reasonMasterSearch.do?method=openEditReason&ReasonId="+b;
	document.getElementById("reasonMaster").submit();
}

function fnEditReason(){

	
	DisButClass.prototype.DisButMethod();
	var reasonType = document.getElementById("reasonType");
	var reasonShortcode = document.getElementById("reasonShortcode");
	var reasonDes = document.getElementById("reasonDes");

	
	if(trim(reasonType.value) == ''||trim(reasonDes.value) == ''||trim(reasonShortcode.value) == ''){
		var msg= '';
		if(trim(reasonType.value) == '')
			msg += '* Reason Type is required.\n';
		if(trim(reasonShortcode.value) == '')
			msg += '* Reason Short Code is required.\n';
		if(trim(reasonDes.value) == '')
			msg += '* Reason Description is required.\n';
		
		if(msg.match("Type")){
			document.getElementById("reasonTypeButton").focus();
		}else if(msg.match("Description")){
			reasonDes.focus();
		}
		else if(msg.match("Short")){
			reasonShortcode.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("reasonMaster").action="reasonMasterAdd.do?method=updateReason";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("reasonMaster").submit();
		return true;
	}
}


//Country Master .............

function newAddCountry(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("countryMasterForm").action=sourcepath+"/countryMaster.do?method=openAddCountry";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("countryMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}

function saveCountry(){
	
	DisButClass.prototype.DisButMethod();
	var countryDes = document.getElementById("countryDes");
	var currencyId = document.getElementById("currencyId");
	
	 if(trim(countryDes.value) == ''||trim(currencyId.value) == ''){
		 var msg= '';
 		if(trim(countryDes.value) == '')
 			msg += '* Country Description is required.\n';
 		if(trim(currencyId.value) == '')
 			msg += '* Currency is required.\n';
 		 		 		
 		if(msg.match("Country")){
 			countryDes.focus();
 		}else if(msg.match("Currency")){
 			currencyId.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else {
		var ck_alpha = /^[A-Za-z]{0,5}$/;
		if(!ck_alpha.test(currencyId.value)){
			if(trim(currencyId.value) != ''){
				alert("* Currency is invalid.");
			}
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
			document.getElementById("countryMasterForm").action="countryMasterAdd.do?method=saveCountryDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("countryMasterForm").submit();
			return true;
	}
	
}

function fnSearchCountry(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("countryMasterForm").action="countryMasterBehind.do";
	if(document.getElementById("countryId").value=='' && document.getElementById("countrySearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("countryMasterForm").submit();
	return true;
	}
	}


function editpageCountry(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("countryMasterForm").action=sourcepath+"/countryMaster.do?method=openEditCountry&CountryId="+b;
	document.getElementById("countryMasterForm").submit();
}

function fnEditCountry(){
	
	DisButClass.prototype.DisButMethod();
	var countryDes = document.getElementById("countryDes");
	var currencyId = document.getElementById("currencyId");
	
	 if(trim(countryDes.value) == ''||trim(currencyId.value) == ''){
		 var msg= '';
 		if(trim(countryDes.value) == '')
 			msg += '* Country Description is required.\n';
 		if(trim(currencyId.value) == '')
 			msg += '* Currency is required.\n';
 		 		 		
 		if(msg.match("Country")){
 			countryDes.focus();
 		}else if(msg.match("Currency")){
 			currencyId.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else {
		var ck_alpha = /^[A-Za-z]{0,5}$/;
		if(!ck_alpha.test(currencyId.value)){
			if(trim(currencyId.value) != ''){
				alert("* Currency is invalid.");
			}
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		document.getElementById("countryMasterForm").action="countryMasterAdd.do?method=updateCountry";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("countryMasterForm").submit();
	}

}

//Region Master .............

function newAddRegion(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("regionMaster").action=sourcepath+"/regionMasterSearch.do?method=openAddRegion";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("regionMaster").submit();
	return true;
}

function saveRegion(){
	
	DisButClass.prototype.DisButMethod();
	var regionDes = document.getElementById("regionDes");
	
	 if(trim(regionDes.value) == ''){
		 var msg= '';
 		if(trim(regionDes.value) == '')
 			msg += '* Region Description is required.\n';
 		 		 		
 		if(msg.match("Region")){
 			regionDes.focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else{
		document.getElementById("regionMaster").action="regionMasterAdd.do?method=saveRegionDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("regionMaster").submit();
		return true;
	}
}

function fnSearchRegion(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("regionId").value==''&& document.getElementById("regionSearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("regionMaster").action="regionMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("regionMaster").submit();
	
	}


function editpageRegion(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("regionMaster").action=sourcepath+"/regionMasterSearch.do?method=openEditRegion&RegionId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("regionMaster").submit();
}

function fnEditRegion(){
	DisButClass.prototype.DisButMethod();
	var regionDes = document.getElementById("regionDes");
	
	 if(trim(regionDes.value) == ''){
		 var msg= '';
		if(trim(regionDes.value) == '')
			msg += '* Region Description is required.\n';
		 		 		
		if(msg.match("Region")){
			regionDes.focus();
		}
		
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	 
	 }else{
		document.getElementById("regionMaster").action="regionMasterAdd.do?method=updateRegion";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("regionMaster").submit();
		return true;
	}
}



//District Master .............

function newAddDistrict(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("districtMaster").action=sourcepath+"/districtMasterSearch.do?method=openAddDistrict";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("districtMaster").submit();
	return true;

	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}

function saveDistrict(){
	DisButClass.prototype.DisButMethod();
	var stateId = document.getElementById("stateId");
	var districtDes = document.getElementById("districtDes");
	
	 if(trim(districtDes.value) == ''||trim(stateId.value) == ''){
		 var msg= '';
 		if(trim(districtDes.value) == '')
 			msg += '* District Description is required.\n';
 		if(trim(stateId.value) == '')
 			msg += '* State is required.\n';
 		 		 		
 		if(msg.match("District")){
 			districtDes.focus();
 		}else if(msg.match("State")){
 			document.getElementById("stateButton").focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else{
		document.getElementById("districtMaster").action="districtMasterAdd.do?method=saveDistrictDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("districtMaster").submit();
		return true;
		}

}

function fnSearchDistrict(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("districtId").value=='' && document.getElementById("districtSearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
	document.getElementById("districtMaster").action="districtMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("districtMaster").submit();
	return true;
	}
	}


function editpageDistrict(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("districtMaster").action=sourcepath+"/districtMasterSearch.do?method=openEditDistrict&DistrictId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("districtMaster").submit();
}

function fnEditDistrict(){
	
	DisButClass.prototype.DisButMethod();
	var stateId = document.getElementById("stateId");
	var districtDes = document.getElementById("districtDes");
	
	 if(trim(districtDes.value) == ''||trim(stateId.value) == ''){
		 var msg= '';
 		if(trim(districtDes.value) == '')
 			msg += '* District Description is required.\n';
 		if(trim(stateId.value) == '')
 			msg += '* State is required.\n';
 		 		 		
 		if(msg.match("District")){
 			districtDes.focus();
 		}else if(msg.match("State")){
 			document.getElementById("stateButton").focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else{
		document.getElementById("districtMaster").action="districtMasterAdd.do?method=updateDistrict";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("districtMaster").submit();
		return true;
	}

}


//state Master .............

function newAddState(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("stateMasterForm").action=sourcepath+"/stateMasterSearch.do?method=openAddState";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stateMasterForm").submit();
	return true;

}

function saveState(){

	DisButClass.prototype.DisButMethod();
	var stateDes = document.getElementById("stateDes");
	var countryId = document.getElementById("countryId");
	var vatPercent = document.getElementById("vatPercent");
	var serviceTax =  document.getElementById("serviceTax");
	
	
	
	 if(trim(stateDes.value) == ''||trim(countryId.value) == '' || trim(vatPercent.value)=='' || trim(serviceTax.value)==''){
		 var msg= '';
 		if(trim(stateDes.value) == '')
 			msg += '* State Description is required.\n';
 		if(trim(countryId.value) == '')
 			msg += '* Country is required.\n';
 		if(trim(vatPercent.value)=='') 		
 			msg += '* Vat Percentage is required.\n';
 		if(trim(serviceTax.value)=='') 		
 			msg += '* Service Tax is required.\n';
 		if(msg.match("State")){
 			stateDes.focus();
 		}else if(msg.match("Country")){
 			document.getElementById("countryButton").focus();
 		}else if (msg.match("Percentage")){
 			document.getElementById("vatPercent").focus();
 		}
 		else if (msg.match("Service")){
 			document.getElementById("serviceTax").focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
 		
		
	 }
	 else if(trim(vatPercent.value)>100.00 || trim(vatPercent.value)<0.00)
	 {
		 alert('Vat should be 0 to 100 ');
			document.getElementById("vatPercent").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
	 }	 
	 else{
		document.getElementById("stateMasterForm").action="stateMasterAdd.do?method=saveStateDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("stateMasterForm").submit();
		return true;
	}
}

function fnSearchState(val){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("stateId").value=='' && document.getElementById("stateSearchDes").value=='')
	{
		alert(val);
		document.getElementById("save").removeAttribute("disabled","true");
	}
	document.getElementById("stateMasterForm").action="stateMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stateMasterForm").submit();
	
	}


function editpageState(b)
{ 
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("stateMasterForm").action=sourcepath+"/stateMasterSearch.do?method=openEditState&StateId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stateMasterForm").submit();
}

function fnEditState(){

	DisButClass.prototype.DisButMethod();
	var stateDes = document.getElementById("stateDes");
	var countryId = document.getElementById("countryId");
	var vatPercent = document.getElementById("vatPercent");
	var serviceTax =  document.getElementById("serviceTax");
	
	 if(trim(stateDes.value) == ''||trim(countryId.value) == '' || trim(vatPercent.value)=='' || trim(serviceTax.value)==''){
		 var msg= '';
 		if(trim(stateDes.value) == '')
 			msg += '* State Description is required.\n';
 		if(trim(countryId.value) == '')
 			msg += '* Country is required.\n';
 		if(trim(vatPercent.value)=='') 		
 			msg += '* Vat Percentage is required.\n';
 		if(trim(serviceTax.value)=='') 		
 			msg += '* Service Tax is required.\n';	 		
 		if(msg.match("State")){
 			stateDes.focus();
 		}else if(msg.match("Country")){
 			document.getElementById("countryButton").focus();
 		}else if (msg.match("Percentage")){
 			document.getElementById("vatPercent").focus();
 		}
 		else if (msg.match("Service")){
 			document.getElementById("serviceTax").focus();
 		}
 		
 		alert(msg);
 		DisButClass.prototype.EnbButMethod();
// 		document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }else{
	document.getElementById("stateMasterForm").action="stateMasterAdd.do?method=updateState";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("stateMasterForm").submit();
		return true;
	}
}


//Product Master 

function newAddProduct(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("productMaster").action=sourcepath+"/productMasterSearch.do?method=openAddProduct";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("productMaster").submit();
	return true;
	
}



function fnproductSave(){

	DisButClass.prototype.DisButMethod();
	var productId = document.getElementById("productId");
	var productDes = document.getElementById("productDes");
	var productCategory = document.getElementById("productCategory");
	var repaymentType=document.getElementById("repaymentType").value;
	var productType=document.getElementById("assetFlag").value;
	var productLoanType=document.getElementById("productLoanType").value;/*add by saorabh*/
	if(repaymentType=='N' && productType=='A'){
				DisButClass.prototype.EnbButMethod();
				alert("Please select only NON ASSET BASED Product Type");
				return false;
			}
	if(trim(productId.value) == ''||trim(productDes.value) == ''||trim(productCategory.value) == '' || trim(productLoanType)==''){
			var msg= '';
			if(trim(productId.value) == '')
				msg += '* Product Id is required.\n';
			if(trim(productDes.value) == '')
				msg += '* Product Description is required.\n';
			if(trim(productCategory.value) == '')
				msg += '* Product Category is required.\n';
			// add by saorabh
			if(trim(productLoanType) == '')
				msg += '* Loan Product Type is required.\n';
			//end by saorabh
			
			if(msg.match("Id")){
				productId.focus();
			}else if(msg.match("Description")){
				productDes.focus();
			}else if(msg.match("Category")){
				productCategory.focus();
			}else if (msg.match("Loan Product"))// add by saorabh
				{
				document.getElementById("productLoanType").focus();
				}
				
			//end by saorabh
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}else{
			var ck_numeric = /^[A-Za-z_0-9']{1,50}$/;
			var msg= '';
			if (!ck_numeric.test(productId.value)) {
				
		    	if(trim(productId.value) != ''){
			   	  msg += "* Product Id is invalid.\n";
			   	  productId.focus();
		    	}		    	
		    	
		    	alert(msg);
		    	DisButClass.prototype.EnbButMethod();
		    	//document.getElementById("save").removeAttribute("disabled","true");
		    	return false;
		    	}
			
			document.getElementById("productMaster").action="productMasterAdd.do?method=saveproductDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("productMaster").submit();
			return true;
		}
}

function fnSearchProduct(val){
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("productSearchId").value==''&& document.getElementById("productSearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
	    //document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("productMaster").action="productMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("productMaster").submit();
	
}

function editpageProduct(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("productMaster").action=sourcepath+"/productMasterSearch.do?method=openEditProduct&ProductSearchId="+b;
	document.getElementById("productMaster").submit();
}

function fnEditProduct(){
	
	DisButClass.prototype.DisButMethod();
	var productId = document.getElementById("productId");
	var productDes = document.getElementById("productDes");
	var productCategory = document.getElementById("productCategory");
	var repaymentType=document.getElementById("repaymentType").value;
	var productType=document.getElementById("assetFlag").value;
	var productLoanType=document.getElementById("productLoanType").value;/*add by saorabh*/
	if(repaymentType=='N' && productType=='A'){
				DisButClass.prototype.EnbButMethod();
				alert("Please select only NON ASSET BASED Product Type");
				return false;
			}
	
	if(trim(productId.value) == ''||trim(productDes.value) == ''||trim(productCategory.value) == '' || trim(productLoanType)==''){
			var msg= '';
			if(trim(productId.value) == '')
				msg += '* Product Id is required.\n';
			if(trim(productDes.value) == '')
				msg += '* Product Description is required.\n';
			if(trim(productCategory.value) == '')
				msg += '* Product Category is required.\n';
			if(trim(productLoanType) == '')
				msg += '* Loan Product Type is required.\n';
			if(msg.match("Id")){
				productId.focus();
			}else if(msg.match("Description")){
				productDes.focus();
			}else if(msg.match("Category")){
				productCategory.focus();
			}else if (msg.match("Loan Product"))// add by saorabh
				{
				document.getElementById("productLoanType").focus();
				}
			
			alert(msg);
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("save").removeAttribute("disabled","true");
			return false;
			
		}else{
			var ck_numeric = /^[A-Za-z_0-9']{1,50}$/;
			var msg= '';
			if (!ck_numeric.test(productId.value)) {
				
		    	if(trim(productId.value) != ''){
			   	  msg += "* Product Id is invalid.\n";
			   	  productId.focus();
		    	}		    	
		    	
		    	alert(msg);
		    	DisButClass.prototype.EnbButMethod();
		    	//document.getElementById("save").removeAttribute("disabled","true");
		    	return false;
		    	}
			
			document.getElementById("productMaster").action="productMasterAdd.do?method=updateProduct";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("productMaster").submit();
		}
}

//Doc Child Master .............

function newAddDocChild(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("docChildMasterForm").action=sourcepath+"/docChildMasterSearch.do?method=openAddDoc";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("docChildMasterForm").submit();
	return true;
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}

function saveDoc(){
	DisButClass.prototype.DisButMethod();
	if(validateDocChildMasterAddDyanavalidatiorForm(document.getElementById("docChildMasterForm")))
			{
				document.getElementById("docChildMasterForm").action="docChildMasterAdd.do?method=saveDocDetails";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("docChildMasterForm").submit();
				return true;
			}
else
{
	DisButClass.prototype.EnbButMethod();
	//document.getElementById("save").removeAttribute("disabled","true");
	return false;
}

}

function fnSearchDocChild(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("docChildID").value=='' && document.getElementById("docChildSearchDes").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}else{
	document.getElementById("docChildMasterForm").action="documentChildMasterBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("docChildMasterForm").submit();
	return true;
	}
	}


function editpageDoc(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("docChildMasterForm").action=sourcepath+"/docChildMasterSearch.do?method=openEditDocChild&DocChildId="+b;
	document.getElementById("docChildMasterForm").submit();
}

function fnEditDoc(){
	DisButClass.prototype.DisButMethod();
	if(validateDocChildMasterAddDyanavalidatiorForm(document.getElementById("docChildMasterForm")))
	{
	document.getElementById("docChildMasterForm").action="docChildMasterAdd.do?method=updateDocChild";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("docChildMasterForm").submit();
	return true;
}
	else
	{	
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("save").removeAttribute("disabled","true");
		return false;
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
//Manish Shukla Starts
function empanelledHandel()
{
	 var dealerType=document.getElementById('lbxdealerType').value;
	 if(dealerType=='SU')
	  {
		  document.getElementById("empanel1").style.display="";
		  document.getElementById("empanel2").style.display="";
	  }
	  else
	  {
		  document.getElementById("empanel1").style.display="none";
		  document.getElementById("empanel2").style.display="none";
	  }
}



// Consortium Partner Master .............

function newAddConsortiumPartner(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("ConsortiumPartner").action=sourcepath+"/ConsortiumPartnerDispatchAction.do?method=openAddConsortiumPartner";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ConsortiumPartner").submit();
	return true;
}

function saveConsortiumPartner(){
	
	DisButClass.prototype.DisButMethod();
	var consortiumPartnerName = document.getElementById("consortiumPartnerName");
	var defaultPercentageLoan= document.getElementById("defaultPercentageLoan");
	var defaultAgreedLoan=document.getElementById("defaultAgreedLoan");
	
	var consortiumPartnerStatus = document.getElementById("consortiumPartnerStatus");
	var stat='X';
	if(consortiumPartnerStatus.checked==true)
	{
		stat='A';
	}
	 if(trim(consortiumPartnerName.value) == ''){
		 var msg= '';
		msg += '* Consortium Partner Name is required.\n';
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		 	return false;	
	 }
	 
		if(trim(defaultPercentageLoan.value) == ''){
			 var msg= '';
				msg += '* Default Percentage Loan is required.\n';
				alert(msg);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;		 		
			
		}
			if(trim(defaultAgreedLoan.value) == ''){
				 var msg= '';
					msg += '* Default Agreed Rate is required.\n';
					alert(msg);
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;	 		
			
	 
	 }else{
		document.getElementById("ConsortiumPartner").action="consortiumPartnerAdd.do?method=saveConsortiumPartnerDetails&consortiumPartnerStatus="+stat;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("ConsortiumPartner").submit();
		return true;
	}
}

function fnSearchConsortium(val){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("consortiumPartnerId").value==''&& document.getElementById("consortiumPartnerName").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("ConsortiumPartner").action="consortiumPartnerMasterBehindAction.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ConsortiumPartner").submit();
	
	}


function editpageConsortium(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("ConsortiumPartner").action=sourcepath+"/ConsortiumPartnerSearch.do?method=openEditConsortiumPartner&consortiumPartnerId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ConsortiumPartner").submit();
}

function fnEditConsortium(){
	DisButClass.prototype.DisButMethod();
	//var consortiumPartnerId = document.getElementById("consortiumPartnerId");
	var consortiumPartnerName = document.getElementById("consortiumPartnerName");
	var defaultPercentageLoan= document.getElementById("defaultPercentageLoan");
	var defaultAgreedLoan=document.getElementById("defaultAgreedLoan");
	var consortiumPartnerStatus = document.getElementById("consortiumPartnerStatus");
	var stat='X';
	
	if(consortiumPartnerStatus.checked==true)
	{
		stat='A';
	}
	
	 if(trim(consortiumPartnerName.value) == ''){
		 var msg= '';
		msg += '* Consortium Partner  Name is required.\n';
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		 	return false;	
	 }
	 
		if(trim(defaultPercentageLoan.value) == ''){
			 var msg= '';
				msg += '* Default Percentage Loan is required.\n';
				alert(msg);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;		 		
			
		}
			if(trim(defaultAgreedLoan.value) == ''){
				 var msg= '';
					msg += '* Default Agreed Rate is required.\n';
					alert(msg);
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;	 		
			}
		
		/*alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("save").removeAttribute("disabled","true");
		return false;*/
	 
	 else{
		  var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("ConsortiumPartner").action=sourcepath+"/ConsortiumPartnerDispatchAction.do?method=updateConsortiumPartner&consortiumPartnerStatus="+stat;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("ConsortiumPartner").submit();
		return true;
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
// Gold Ornament Master

function newAddGoldOrnament(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("goldOrnamentMaster").action=sourcepath+"/goldOrnamentMasterSearch.do?method=openAddGoldOrnament";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("goldOrnamentMaster").submit();
	return true;
	
}

function fnSearchGoldOrnament(val){
	DisButClass.prototype.DisButMethod();
	
	
	
	if(document.getElementById("ornamentTypeSearch").value==''&& document.getElementById("ornamentStandardSearch").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
	    //document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	document.getElementById("goldOrnamentMaster").action="goldOrnamentMasterBehind.do";
	//document.getElementById("goldOrnamentMaster").action="goldOrnamentMasterBehind.do&ornamentId="+ornamentId+"&ornamentType="+ornamentTypeSearch+"&ornamentStandard="+ornamentStandardSearch;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("goldOrnamentMaster").submit();
	
}

function fnEditGoldOrnament(){


	DisButClass.prototype.DisButMethod();
	
	var ornamentType = document.getElementById("ornamentType");
	var ornamentStandard= document.getElementById("ornamentStandard");
	var goldORnamentStatus= document.getElementById("goldORnamentStatus");
	var ornamentId= document.getElementById("ornamentId").value;
	
	
	var stat='X';
	
	if(goldORnamentStatus.checked==true)
	{
		stat='A';
	}
	
	
	 
		if(trim(ornamentType.value) == ''){
			 var msg= '';
				msg += '* Ornament Type is required.\n';
				alert(msg);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;		 		
			
		}
			if(ornamentStandard.value == ''){
				 var msg= '';
					msg += '* Ornament Standard is required.\n';
					alert(msg);
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;	 		
			}
			
			if(trim(goldOrnamentLTV.value) == ''){
				var msg= '';
					msg += '* LTV  is required.\n';
					alert(msg);
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;		 		
				
			}
			
	 else{
	 
		  var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("goldOrnamentMaster").action=sourcepath+"/goldOrnamentMasterAdd.do?method=updateGoldOrnament&ornamentId="+ornamentId+"&goldORnamentStatus="+stat;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("goldOrnamentMaster").submit();
		return true;
	}
}
function fngoldOrnamentSave(){

	DisButClass.prototype.DisButMethod();
	var ornamentType = document.getElementById("ornamentType");
	var ornamentStandard= document.getElementById("ornamentStandard");
	var goldOrnamentSearchId=document.getElementById("goldOrnamentSearchId");
	var goldOrnamentLTV=document.getElementById("goldOrnamentLTV");
	  
	  if(trim(ornamentType.value) == ''){
			 var msg= '';
				msg += '* Ornament Type is required.\n';
				alert(msg);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;		 		
			
		}
			if(trim(ornamentStandard.value) == ''){
				 var msg= '';
					msg += '* Ornament Standard is required.\n';
					alert(msg);
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					return false;	 		
			}
		 if(trim(goldOrnamentLTV.value) == ''){
			var msg= '';
				msg += '* LTV  is required.\n';
				alert(msg);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;		 		
			
		}
		
	/*	if(goldOrnamentLTV<1)
    {
	       alert("LTV Can not be less than or equal to Zero. ");		
		DisButClass.prototype.EnbButMethod();
		document.getElementById("Save").removeAttribute("disabled","true");
		    	return false;;
       }	*/
		
			else{
				  var sourcepath=document.getElementById("contextPath").value;
				//document.getElementById("goldOrnamentMaster").action=sourcepath+"/goldOrnamentMasterAdd.do?method=savegoldOrnamentDetails&goldOrnamentStatus="+stat;
				document.getElementById("goldOrnamentMaster").action="goldOrnamentMasterAdd.do?method=saveGoldOrnamentDetails";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("goldOrnamentMaster").submit();
				return true;
			}
		}
			
			
			
function editpageConsortium(b)
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("goldOrnamentMaster").action=sourcepath+"/goldOrnamentMasterSearch.do?method=openEditgoldOrnament&ornamentId="+b;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("goldOrnamentMaster").submit();
}


function disableFieldForDealMandatory()
{
	var flag=document.getElementById("assetFlag").value;
	if(flag=="A"){
	document.getElementById("dis1").style.display="";
	
	document.getElementById("dis2").style.display="";
	}
	else{
		document.getElementById("dis1").style.display="none";
		
		document.getElementById("dis2").style.display="none";
		document.getElementById("assetMandatoryAtDeal").value='';
		
	}
	}
 function newAddSBL(){
    	DisButClass.prototype.DisButMethod();
    	var sourcepath=document.getElementById("path").value;
    	document.getElementById("sblMaster").action=sourcepath+"/SBLGBLMasterSearch.do?method=openAddProduct";
    	document.getElementById("processingImage").style.display = '';
    	document.getElementById("sblMaster").submit();
    	return true;
    	
    }
  //pooja for SBL & GBL ADD
    function fnsblSave(){

    	DisButClass.prototype.DisButMethod();
    	var lbxProductID = document.getElementById("product");
    	var singleBorrowerLimit = document.getElementById("singleBorrowerLimit");
    	var groupBorrowerLimit = document.getElementById("groupBorrowerLimit");
    	if(trim(lbxProductID.value) == ''||trim(singleBorrowerLimit.value) == ''||trim(groupBorrowerLimit.value) == ''){
    			var msg= '';
    			if(trim(lbxProductID.value) == '')
    				msg += '* Product Name is required.\n';
    			if(trim(singleBorrowerLimit.value) == '')
    				msg += '* Single Borrower Limit is required.\n';
    			if(trim(groupBorrowerLimit.value) == '')
    				msg += '* Group Borrower Limit is required.\n';
    			    			
    			if(msg.match("product")){
    				productId.focus();
    			}else if(msg.match("single")){
    				productDes.focus();
    			}else if(msg.match("group")){
    				productCategory.focus();
    			}
    				
    			alert(msg);
    			DisButClass.prototype.EnbButMethod();
    			return false;
    			
    		}else{
    			document.getElementById("sblMaster").action="sblMasterAdd.do?method=savesblDetails";
    			document.getElementById("processingImage").style.display = '';
    			document.getElementById("sblMaster").submit();
    			return true;
    		}
    }
    //pooja code for SBL & GBL Search
    function fnSearchSBL(val){
    	DisButClass.prototype.DisButMethod();
    	if(document.getElementById("productSearchId").value==''&& document.getElementById("productSearchDes").value=='')
    	{
    		alert(val);
    		DisButClass.prototype.EnbButMethod();
    	    //document.getElementById("save").removeAttribute("disabled","true");
    		return false;
    	}
    	document.getElementById("sblMaster").action="SBLGBLMasterBehind.do";
    	document.getElementById("processingImage").style.display = '';
    	document.getElementById("sblMaster").submit();
    	
    }
    function fnEditSBL(){
    	
    	DisButClass.prototype.DisButMethod();
    	var lbxProductID = document.getElementById("product");
    	var singleBorrowerLimit = document.getElementById("singleBorrowerLimit");
    	var groupBorrowerLimit = document.getElementById("groupBorrowerLimit");
    	if(trim(lbxProductID.value) == ''||trim(singleBorrowerLimit.value) == ''||trim(groupBorrowerLimit.value) == ''){
    		var msg= '';
			if(trim(lbxProductID.value) == '')
				msg += '* Product Name is required.\n';
			if(trim(singleBorrowerLimit.value) == '')
				msg += '* Single Borrower Limit is required.\n';
			if(trim(groupBorrowerLimit.value) == '')
				msg += '* Group Borrower Limit is required.\n';
			    			
			if(msg.match("product")){
				productId.focus();
			}else if(msg.match("single")){
				productDes.focus();
			}else if(msg.match("group")){
				productCategory.focus();
			}
    			alert(msg);
    			DisButClass.prototype.EnbButMethod();
    			return false;
    			
    		}else{
    			document.getElementById("sblMaster").action="sblMasterAdd.do?method=updateSBL";
    			document.getElementById("processingImage").style.display = '';
    			document.getElementById("sblMaster").submit();
    		}
    }



