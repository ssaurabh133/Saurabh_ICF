function saveBuyerDetails()
{	
	//alert("saveBuyerDetails");
	DisButClass.prototype.DisButMethod();
	
	var contextPath = document.getElementById("contextPath").value;
	
	var businessPartnerName = document.getElementById("businessPartnerName");
	var averagePurchaseSales = document.getElementById("averagePurchaseSales");
	var paymentTerms = document.getElementById("paymentTerms");
	var productType = document.getElementById("productType");
	var vintageOfRelationship = document.getElementById("vintageOfRelationship");
	var mobile = document.getElementById("mobile");
	
	
	if(trim(businessPartnerName.value) == ''||trim(averagePurchaseSales.value) == ''||trim(paymentTerms.value) == ''||trim(productType.value) == ''||trim(vintageOfRelationship.value) == ''){
		var msg= '';
		if(trim(businessPartnerName.value) == '')
			msg += '* Business Partner Name is required.\n';
		if(trim(averagePurchaseSales.value) == '')
			msg += '* Average Monthly Sales is required.\n';
		if(trim(paymentTerms.value) == '')
			msg += '* Payment Terms is required.\n';
		if(trim(productType.value) == '')
			msg += '* Product Type is required.\n';
		if(trim(vintageOfRelationship.value) == '')
			msg += '* Vintage Of Relationship is required.\n';
		
		
		if(msg.match("Business")){
			businessPartnerName.focus();
		}else if(msg.match("Average")){
			averagePurchaseSales.focus();
		}else if(msg.match("Payment")){
			paymentTerms.focus();
		}else if(msg.match("Product")){
			productType.focus();
		}else if(msg.match("Vintage")){
			vintageOfRelationship.focus();
		}
		
		alert(msg);
		document.getElementById("saveBuyer").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}else if(validate("BuyerForm")){	
	 
	  if(!(document.getElementById("chkvalue").value)=="")
	  {   
	      var primaryId = document.getElementById("chkvalue").value;
	      //alert(primaryId);    
		  document.getElementById("BuyerForm").action=contextPath+"/buyerProcessAction.do?method=updateBuyerDetails&primaryId="+primaryId;
		  document.getElementById("processingImage").style.display = '';
		  document.getElementById("BuyerForm").submit();
		  return true;
	  }
	 
	  else
	  {
		 // alert("Same here");
		  document.getElementById("BuyerForm").action=contextPath+"/buyerProcessAction.do?method=saveBuyerDetails";
		  document.getElementById("processingImage").style.display = '';
		  document.getElementById("BuyerForm").submit();
		  return true;
	  }
	 
	}

}



function saveNForBuyerDetails()
{	
	DisButClass.prototype.DisButMethod();
	//alert("saveBuyerDetails");
	  var contextPath = document.getElementById("contextPath").value;
	
	  document.getElementById("BuyerForm").action=contextPath+"/buyerSuppMainAction.do?method=saveNForBuyerDetails";
	  document.getElementById("processingImage").style.display = '';
	  document.getElementById("BuyerForm").submit();
	  return true;
	
   
	}



function saveSupplierDetails()
{
	DisButClass.prototype.DisButMethod();
	
	  var contextPath = document.getElementById("contextPath").value;
	  var businessPartnerName = document.getElementById("businessPartnerName");
		var averagePurchaseSales = document.getElementById("averagePurchaseSales");
		var paymentTerms = document.getElementById("paymentTerms");
		var productType = document.getElementById("productType");
		var vintageOfRelationship = document.getElementById("vintageOfRelationship");
		
		if(trim(businessPartnerName.value) == ''||trim(averagePurchaseSales.value) == ''||trim(paymentTerms.value) == ''||trim(productType.value) == ''||trim(vintageOfRelationship.value) == ''){
			var msg= '';
			if(trim(businessPartnerName.value) == '')
				msg += '* Business Partner Name is required.\n';
			if(trim(averagePurchaseSales.value) == '')
				msg += '* Average Monthly Sales is required.\n';
			if(trim(paymentTerms.value) == '')
				msg += '* Payment Terms is required.\n';
			if(trim(productType.value) == '')
				msg += '* Product Type is required.\n';
			if(trim(vintageOfRelationship.value) == '')
				msg += '* Vintage Of Relationship is required.\n';
			
			
			if(msg.match("Business")){
				businessPartnerName.focus();
			}else if(msg.match("Average")){
				averagePurchaseSales.focus();
			}else if(msg.match("Payment")){
				paymentTerms.focus();
			}else if(msg.match("Product")){
				productType.focus();
			}else if(msg.match("Vintage")){
				vintageOfRelationship.focus();
			}
			
			alert(msg);
			document.getElementById("saveSupplier").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else if(validate("SupplierForm")){	
		    
		  if(!(document.getElementById("chkvalue").value)=="")
		  { 
		      var primaryId = document.getElementById("chkvalue").value;
		    //  alert(primaryId);
			  document.getElementById("SupplierForm").action=contextPath+"/supplierProcessAction.do?method=updateSupplierDetails&primaryId="+primaryId;
			  document.getElementById("processingImage").style.display = '';
			  document.getElementById("SupplierForm").submit();
			  return true;
		  }
		  else
		  { 
			
			  document.getElementById("SupplierForm").action=contextPath+"/supplierProcessAction.do?method=saveSupplierDetails";
			  document.getElementById("processingImage").style.display = '';
			  document.getElementById("SupplierForm").submit();
		  }
		}
}	
//-----------------------------------------VALIDATION--------------------------

var ck_numeric = /^[A-Za-z0-9+-]{10,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
var san_url = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;
var ck_numeric1 = /^[0-9]*$/;
var ck_numeric2 = /^[0-9\.\,]*$/;

function validate(formName){
	var mobile = document.getElementById('mobile').value;
	var email = document.getElementById('email').value;
	var pincode = document.getElementById('pincode').value;
  var averagePurchaseSales = document.getElementById('averagePurchaseSales').value;
 var vintageOfRelationship = document.getElementById('vintageOfRelationship').value;
 var errors = [];

			
 if(!ck_numeric1.test(mobile)){
	 if( mobile!=""  ){
 		errors[errors.length] ="* Mobile Number is invalid";
		
	 }
}
 if(!ck_numeric1.test(vintageOfRelationship)){
	 if( vintageOfRelationship!=""  ){
 		errors[errors.length] ="* Vintage Of Relationship Number is invalid";
		
	 }
}
  if(!ck_numeric2.test(averagePurchaseSales)){
	 if( averagePurchaseSales!=""  ){
 		errors[errors.length] ="* Average Monthly Sales is invalid";
		
	 }
}
 if (!ck_numeric.test(mobile)) {
	 if(mobile != ''){
	 	errors[errors.length] = "* Mobile Number must be of 10 digits.";
	 }
 }
 if (!san_email.test(email)) {
	 if(email != ''){
		 errors[errors.length] = "* Email is not valid.";
	 }
 }
 if (!ck_din.test(pincode)) {
	 if(pincode != ''){
		 errors[errors.length] = "* Pincode must be of 6 digits.";
	 }
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
 if(msg.match("Mobile")){
	 document.getElementById('mobile').focus();
	}else if(msg.match("Email")){
		document.getElementById('email').focus();
	}else if(msg.match("Pincode")){
		document.getElementById('pincode').focus();
	}else if(msg.match("invalid")){
		document.getElementById('mobile').focus();
	}else if(msg.match("Average")){
		document.getElementById('averagePurchaseSales').focus();
	}else if(msg.match("Vintage")){
		document.getElementById('vintageOfRelationship').focus();
	}

 alert(msg);
 //document.getElementById("saveBuyer").removeAttribute("disabled","true");
}

function allOtherChecked()
{
	 //alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	// alert(c);
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


function updateOtherRelation()
{   
	DisButClass.prototype.DisButMethod();
	var otherName = document.getElementById('otherName');
    var knowingSince = document.getElementById('knowingSince');
	var relationships = document.getElementById('relationships');
	var primaryOtherMbNo  = document.getElementById('primaryOtherMbNo');
	var alternateOtherPhNo = document.getElementById('alternateOtherPhNo');
	
	
	if (trim(otherName.value) == ""  || trim(knowingSince.value) == "" || trim(relationships.value) == "" || primaryOtherMbNo.value == "") {
		var msg1 = "",msg3="",msg2="",msg4="",msg5="";
		if(trim(otherName.value)  == ""){
			msg1= "* Name is required \n";
			
		}
		
		if(trim(knowingSince.value)  == ""){
			msg3="* Knowing Since is required \n";
			
		}
		if(trim(relationships.value)  == ""){
			msg4="* Relationship is required \n";
			
		}
		if(trim(primaryOtherMbNo.value)  == ""){
			msg5="* Mobile No. is required \n";
			
		}
		
		if(msg1!=''||msg3!=''||msg4!=''|| msg5!='')
		{
			alert(msg1+msg3+msg4+msg5);
			if(msg1!='')
			{
				document.getElementById('otherName').focus();
			}
			
			else if(msg3!='')
			{
				document.getElementById('knowingSince').focus();
			}
			else if(msg4!='')
			{
				document.getElementById('relationships').focus();
			}
			else if(msg5!='')
			{
				document.getElementById('primaryOtherMbNo').focus();
			}

			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		
	}else{
		
		if(primaryOtherMbNo.value != '' || alternateOtherPhNo.value != ''){
			if(!validateOtherRelation("otherForm")){
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}			
		
		var contextPath = document.getElementById("contextPath").value;
		var otherUniqueId = document.getElementById("otherUniqueId").value;
		document.getElementById("otherForm").action=contextPath+"/otherRelationProcessAction.do?method=updateOtherRelation&otherUniqueId="+otherUniqueId;
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("otherForm").submit();
		
	 	return true;
	}

}
function check()
{
    // alert("ok");
	var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[zx].checked==false)
		{
			flag=0;
		}
		else
		{
			
			flag=1;
			break;
		}
		zx++;
	}
	return flag;
}
function deleteOtherRelation()
{
	    //alert("ok"); 
	 var contextPath = document.getElementById("contextPath").value;
		DisButClass.prototype.DisButMethod();
	    if(check())
	    {
			document.getElementById("otherForm").action=contextPath+"/otherRelationShipBehindAction.do?method=deleteOtherRelation";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("otherForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}

function insertOtherRelation()
{ 
	DisButClass.prototype.DisButMethod();
	var otherName = document.getElementById('otherName');
	var knowingSince = document.getElementById('knowingSince');
	var relationships = document.getElementById('relationships');
	var primaryOtherMbNo  = document.getElementById('primaryOtherMbNo');
	var alternateOtherPhNo= document.getElementById('alternateOtherPhNo');
	 var contextPath = document.getElementById("contextPath").value;
	
	
	if (trim(otherName.value) == "" || trim(knowingSince.value) == "" || trim(relationships.value) == "" || primaryOtherMbNo.value == "") {
		var msg1 = "",msg3="",msg2="",msg4="",msg5="";
		if(trim(otherName.value)  == ""){
			msg1= "* Name is required \n";
			
		}
		
		if(trim(knowingSince.value)  == ""){
			msg3="* Knowing Since is required \n";
			
		}
		if(trim(relationships.value)  == ""){
			msg4="* Relationship is required \n";
		}
		if(trim(primaryOtherMbNo.value)  == ""){
			msg5="* Mobile No. is required \n";
			
		}
		
		if(msg1!=''||msg3!=''||msg4!=''||msg5!='')
		{
			alert(msg1+msg3+msg4+msg5);
			if(msg1!='')
			{
				document.getElementById('otherName').focus();
			}
			
			else if(msg3!='')
			{
				document.getElementById('knowingSince').focus();
			}
			else if(msg4!='')
			{
				document.getElementById('relationships').focus();
			}
			else if(msg5!='')
			{
				document.getElementById('primaryOtherMbNo').focus();
			}

			DisButClass.prototype.EnbButMethod();
			return false;
		}
		 
	}else{
		
		
			if(alternateOtherPhNo.value != ''){
				if(!validateOtherRelation("otherForm")){
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			
			}
			
			document.getElementById("otherForm").action=contextPath+"/otherRelationProcessAction.do?method=saveOtherRelation";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("otherForm").submit();
			}
		return true;
}

function validateOtherRelation(formName)
{
	var primaryOtherMbNo  = document.getElementById('primaryOtherMbNo').value;
	var alternateOtherPhNo = document.getElementById('alternateOtherPhNo').value;
  
	var errors = [];
 

  
 if (!ck_numeric.test(primaryOtherMbNo)) {
	 if(primaryOtherMbNo != ''){
		 errors[errors.length] = "* Mobile No. not valid.";
	 }
 }
 if (!ck_numeric.test(alternateOtherPhNo)) {
	 if(alternateOtherPhNo != ''){
		 errors[errors.length] = "* Landline No. not valid.";
	 }
 }	 
 if (errors.length > 0) {
	 reportOtherErrors(errors);
  return false;
 }
 return true;
}
function reportOtherErrors(errors){
	 var msg = "";
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += "\n" + errors[i];
	 }
	 if(msg.match("Mobile")){
		 document.getElementById('primaryOtherMbNo').focus();
		}else if(msg.match("Landline")){
			document.getElementById('alternateOtherPhNo').focus()
		}
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}
function modifyOtherRelation(otherUniqueId)
{	
	
	    var contextPath = document.getElementById("contextPath").value;
   		location.href=contextPath+"/otherRelationShipBehindAction.do?method=fetchOtherRelation&otherUniqueId="+otherUniqueId;  
}