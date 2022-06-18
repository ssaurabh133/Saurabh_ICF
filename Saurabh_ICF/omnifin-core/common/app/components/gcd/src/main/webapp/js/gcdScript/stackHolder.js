function updateStakeDetail()
	{
	DisButClass.prototype.DisButMethod();
		var sex = document.getElementById('sex');
		var holderName = document.getElementById('holderName');
		var holderType = document.getElementById('holderType');
		var dob = document.getElementById('dob');
		var panNo = document.getElementById('mgmtPAN').value;
		var mobileNo = document.getElementById('primaryPhone').value;
		var contextPath = document.getElementById("contextPath").value;
		var a= "";

		if(trim(sex.value) == '' || trim(holderName.value) == '' || trim(holderType.value) =='' || trim(dob.value) == '' || trim(panNo) == '' || trim(mobileNo) == ''){
			
				
			if(trim(sex.value) == ""){
				a= "* Salutation is required \n";
			}
			if(trim(holderName.value) == ""){
				a += "* Stake Holder Name is required \n";
			}
			
			if(trim(holderType.value) == ""){
				a += "* Management Type is required \n";
			}
			if(trim(dob.value) == ""){
				a +="* Date of Birth is required \n";
			}
			if(trim(panNo) == ""){
				a +="* PAN No. is required \n";
			}		
			if(trim(mobileNo) == ""){
				a +="* Mobile No. is required \n";
			}	
			if(a.match(/Salutation/gi)){
				sex.focus();
			}else if(a.match(/Name/gi)){
				holderName.focus();
			}else if(a.match(/Management/gi)){
				holderType.focus();
			}else if(a.match(/Birth/gi)){
				dob.focus();
			}else if(a.match(/PAN/gi)){
				mgmtPAN.focus();
			}else if(a.match(/Mobile/gi)){
				primaryPhone.focus();
			}
			
			
			alert(a);
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else if(validate("StakeForm"))
		{
			var percentage=document.getElementById("percentage").value;
			var holdingPerc=document.getElementById("holdingPerc").value;
			if(percentage=='')
				percentage='0';
			if(holdingPerc=='')
				holdingPerc='0';
			var total=parseFloat(percentage)+parseFloat(holdingPerc);
			if(total>100)
			{
				alert("Sum of all Stake Holder's Holding Percentage can't be greater than 100");
				DisButClass.prototype.EnbButMethod();
		   		return false;
			}
			
			document.getElementById("StakeForm").action=contextPath+"/corporatStackHolderPageAction.do?method=updateStakeDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("StakeForm").submit();
			return true;	
   		}
	//	document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
    }

	function saveStakeDetail()
	{
		DisButClass.prototype.DisButMethod();
		var sex = document.getElementById('sex');
		var holderName = document.getElementById('holderName');
		var holderType = document.getElementById('holderType');
		var dob = document.getElementById('dob');
		var panNo = document.getElementById('mgmtPAN').value;
		var mobileNo = document.getElementById('primaryPhone').value;
		var a= "";
		var contextPath = document.getElementById("contextPath").value;
		//alert("in updateStakeDetail");
		if(trim(sex.value) == '' || trim(holderName.value) == '' || trim(holderType.value) =='' || trim(dob.value) == '' || trim(panNo) == '' || trim(mobileNo) == ''){
			
			if(trim(sex.value) == ""){
				a= "* Salutation is required \n";
			}
			if(trim(holderName.value) == ""){
				a += "* Stake Holder Name is required \n";
			}
			if(trim(holderType.value) == ""){
				a += "* Management Type is required \n";
			}
			if(trim(dob.value) == ""){
				a +="* Date of Birth is required \n";
			}
			if(trim(panNo) == ""){
				a +="* PAN No. is required \n";
			}
			if(trim(mobileNo) == ""){
				a +="* Mobile No. is required \n";
			}
			if(a.match(/Salutation/gi)){
				sex.focus();
			}else if(a.match(/Name/gi)){
				holderName.focus();
			}else if(a.match(/Management/gi)){
				holderType.focus();
			}else if(a.match(/Birth/gi)){
				dob.focus();
			}else if(a.match(/PAN/gi)){
				mgmtPAN.focus();
			}else if(a.match(/Mobile/gi)){
				primaryPhone.focus();
			}
			
			alert(a);
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;

		}else if(validate("StakeForm"))
		{
			var percentage=document.getElementById("percentage").value;
			var holdingPerc=document.getElementById("holdingPerc").value;
			if(percentage=='')
				percentage='0';
			if(holdingPerc=='')
				holdingPerc='0';
			var total=parseFloat(percentage)+parseFloat(holdingPerc);
			if(total>100)
			{
				alert("Sum of all Stake Holder's Holding Percentage can't be greater than 100");
				DisButClass.prototype.EnbButMethod();
		   		return false;
			}
   			document.getElementById("StakeForm").action=contextPath+"/corporatStackHolderPageAction.do?method=saveStakeDetails";
   			document.getElementById("processingImage").style.display = '';
 			document.getElementById("StakeForm").submit();
 			return true;	
   		}
 //  		document.getElementById("save").removeAttribute("disabled","true");
   		DisButClass.prototype.EnbButMethod();
   		return false;
    
    }
	
	 
	function modifyStackHolerDetails(id)
	{
		var contextPath = document.getElementById("contextPath").value;
	   // if(checkBoxes())
	    //{	 
	    	 
			document.getElementById("StakeForm").action=contextPath+"/fetchStackHolderAction.do?method=fetchStackHolder&chk="+id;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("StakeForm").submit();
	    //}
	  
	}
	
	function setStackPerc(val)
	{
		//alert("ok");
		if(val=='PROPR')
		{
			document.getElementById("holdingPerc").value=100;
			return true;
		}
		return false;
			
	}
	
	
	
	
	//-----------------------------------------VALIDATION--------------------------

	var ck_numeric = /^[A-Za-z0-9+-]{10,25}$/;
	var ck_din = /^[0-9]{8}$/;
    var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var san_url = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;



	function validate(formName){
		var primaryPhone = document.getElementById('primaryPhone').value;
		var alternatePhone = document.getElementById('alternatePhone').value;
		var primaryEmail = document.getElementById('primaryEmail').value;
		var alternateEmail = document.getElementById('alternateEmail').value;
		var website = document.getElementById('website').value;
		var dinNo = document.getElementById('dinNo').value;
  

	 var errors = [];
	var pan = $("#mgmtPAN").val();
    if(pan != '')
    {
		var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
		if (!regex1.test(pan) || pan.length != 10) {
			alert("Invalid Pan No.");
			$("#mgmtPAN").attr("value","");
			$("#mgmtPAN").focus();
			return false;
		}
    }
	 if (!ck_din.test(dinNo)) {
		 if(dinNo != ''){
		 	errors[errors.length] = "* DIN Number must be 8 digit Numeric Value.";
		 }
	 }
	 if (!ck_numeric.test(primaryPhone)) {
		 if(primaryPhone != ''){
		 	errors[errors.length] = "* Mobile No. is not valid.";
		 }
	 }
	 if (!ck_numeric.test(alternatePhone)) {
		 if(alternatePhone != ''){
			 errors[errors.length] = "* Landline No. is not valid.";
		 }
	 }
	 if (!san_email.test(primaryEmail)) {
		 if(primaryEmail != ''){
			 errors[errors.length] = "* Primary Email is not valid.";
		 }
	 }
	 if (!san_email.test(alternateEmail)) {
		 if(alternateEmail != ''){
			 errors[errors.length] = "* Alternate Email is not valid.";
		 }
	 }
//	 if (!san_url.test(website)) {
//		 if(website != ''){
//			 errors[errors.length] = "* Website is not valid.";
//		 }
//	}
	 
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
	 	if(msg.match("DIN")){
	 		document.getElementById('dinNo').focus();
		}else if(msg.match("Mobile")){
			document.getElementById('primaryPhone').focus();
		}else if(msg.match("Landline")){
			document.getElementById('alternatePhone').focus();
		}else if(msg.match("Primary")){
			document.getElementById('primaryEmail').focus();
		}else if(msg.match("Alternate")){
			document.getElementById('alternateEmail').focus();
		}else if(msg.match("Website")){
			document.getElementById('website').focus();
		}
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}