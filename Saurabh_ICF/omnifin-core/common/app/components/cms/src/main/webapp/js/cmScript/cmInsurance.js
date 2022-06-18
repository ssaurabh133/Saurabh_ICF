


function isInsuranceKey(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31  && (charCode < 46 || charCode > 57))
{
	alert("Only Numeric Allowed Here");
	return false;
}
	return true;
}




function calculateCmInsurance(formName)
{
	
	//DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("contextPath").value;
		var insuranceProvider= document.getElementById("insuranceProvider").value;
		var sumAssuPer=document.getElementById("sumAssuPer").value;
		var sumAssured= document.getElementById("sumAssured").value;
		var tenure= document.getElementById("tenure").value;
		var policyType= document.getElementById("policyType").value;
		var premiumFinanced=document.getElementById("premiumFinanced").value;
	    var table = document.getElementById("gridTable");	
	 	var nomineeName=document.getElementById("nomineeName").value;
		var dateOfbirth=document.getElementById("dateOfbirth").value;
		var policyTenure=document.getElementById("policyTenure").value;
		var gender= document.getElementById("gender").value;
		var relationshp=document.getElementById("relationshp").value;
		var insuranceProduct=document.getElementById("insuranceProduct").value;
		var propertyType=document.getElementById("propertyType").value;
		var sumAssuPer=parseFloat(document.getElementById("sumAssuPer").value);
	    var alchecking=	 document.getElementsByName('alchecking');
	    var sumAssuredMapping=parseFloat(document.getElementById("sumAssuredMapping").value);
		var count = 0;
		var flag=0;
		var ids="";
		var temp="";
		var id="";
		//alert("propertyType::"+propertyType);
			for(var i=0; i<alchecking.length;i++)
			{
				if(alchecking[i].checked==true)
					{
						id=alchecking[i].value;
						ids=ids+id+"/";
					   //alert(id);
		 			   	flag=1;
		 			   	count++;
					}
				
			}
			
			 if(flag==0)
		 	 {
				 if(policyType=='J')
					 {
						 alert("Please select more than one Record");
				 		 DisButClass.prototype.EnbButMethod();
				 		 document.getElementById("calc").removeAttribute("disabled","true");
				 		 return false;
					 }
				 if(policyType=='S')
					 {
						 alert("Please select one Record");
				 		 DisButClass.prototype.EnbButMethod();
				 		 document.getElementById("calc").removeAttribute("disabled","true");
				 		 return false;
					 }
		 		 
		 	 }
			 if(count>1 && policyType=='S')
				{
				alert("Please select 1 checkbox only.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("calc").removeAttribute("disabled","true");
				return false;
				}
			 else if(policyType=='J' && count==1 )
				 {
				 alert("Please select more than 1 records.");
				 DisButClass.prototype.EnbButMethod();
					document.getElementById("calc").removeAttribute("disabled","true");
				 return false;
				 }
			 else
				 {
				 temp=ids;
				 }
			/* if(sumAssuPer<1 || sumAssuPer>sumAssuredMapping)
			 {
			 	alert('* Sum Assured percent should be in between 1 and '+ sumAssuredMapping);
				DisButClass.prototype.EnbButMethod();
				document.getElementById("calc").removeAttribute("disabled","true");
				return false;
			 }*/
	// var age= document.getElementById("age").value;
	
	//if(trim(insuranceProvider) == '' || trim(sumAssured) =='' ||trim(tenure) == ''||trim(age) == '')
		if(trim(insuranceProvider) == '' || trim(sumAssured) =='' ||trim(tenure) == ''||trim(policyType) == ''||trim(insuranceProduct) == ''||trim(policyTenure) == ''||sumAssuPer == '')
		{
		var msg= '';
		
		if(trim(insuranceProduct) == ''){
			msg += '* Insurance Product  is required.\n';
		}
		
		if(sumAssuPer == ''){
			msg += '* Sum Assured % is required.\n';
		}
		
		if(trim(policyTenure) == ''){
			msg += '* Policy Tenure  is required.\n';
		}
		
		
		/*if(trim(propertyType) == ''){
			msg += '* Property Type  is required.\n';
		}*/
		
		
		
		if(trim(policyType) == ''){
			msg += '* Policy Type  is required.\n';
		}
		
		if(trim(insuranceProvider) == ''){
			msg += '* Insurance Provider  is required.\n';
		}
		if(trim(premiumFinanced) == ''){
			msg += '* Insurance Premium to be Financed  is required.\n';
		}
		if(trim(sumAssured) == ''){
			msg += '* Sum Assured  is required.\n';
		}
		if(trim(tenure) == ''){
			msg += '* Loan Tenure  is required.\n';
		}
		
		if(msg.match("Insurance")){
			document.getElementById("insuranceProvider").focus();
			
		}else if(msg.match("Sum")){
			document.getElementById("sumAssured").focus();
		}else if(msg.match("Tenure")){
			document.getElementById("tenure").focus();
		}
		
		else if(msg.match("insuranceProduct")){
			document.getElementById("insuranceProduct").focus();
		}
		
		else if(msg.match("propertyType")){
			document.getElementById("propertyType").focus();
		}
		
		else if(msg.match("sumAssuPer")){
			document.getElementById("sumAssuPer").focus();
		}
		
		else if(msg.match("policyType")){
			document.getElementById("policyType").focus();
		}
		
		else if(msg.match("policyTenure")){
			document.getElementById("policyTenure").focus();
		}
		
		else if(msg.match("sumAssuPer")){
			document.getElementById("sumAssuPer").focus();
		}
		
		else if(msg.match("premiumFinanced")){
			document.getElementById("premiumFinanced").focus();
		}
		
		alert(msg);
		
		
		
		DisButClass.prototype.EnbButMethod();
		document.getElementById("calc").removeAttribute("disabled","true");
		return false;	
		}	
	else
	{
		var address = sourcepath+"/cmInsurance.do?method=calculateCmInsurance";
		 var data = "ids="+temp+"&insuranceProvider="+insuranceProvider+"&sumAssured="+sumAssured+"&tenure="+tenure+"&insuranceProduct="+insuranceProduct+"&policyType="+policyType+"&policyTenure="+policyTenure+"&propertyType="+propertyType;
		//document.getElementById("cpInsuranceForm").action=sourcepath+"/cpInsurance.do?method=calculateCpInsurance&ids="+temp;
		 calculateCmInsuranceAjax(address, data);
		/*document.getElementById("processingImage").style.display = '';
		document.getElementById("cpInsuranceForm").submit();*/
		return true;
	}
}




function calculateCmInsuranceAjax(address, data) {
	//alert("in sendDisbursalloanId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultCmCalculateInsurance(request);	

	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}


function resultCmCalculateInsurance(request)
{
	
	var s1;
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		s1 = str.split("$:");
		//alert("s1:---"+s1);
		var value=trim(s1[4]);
		
		if( value.indexOf('ERROR') >= 0){
			alert(value);
			document.getElementById('sms1').value="notCal";
		}
		else if(value.indexOf('E01') >= 0){
			alert(value);
			document.getElementById('sms1').value="notCal";
		}
		
		else if(value=="notCal")
			{

			alert("No matching records found.");
		document.getElementById('sms1').value=trim(s1[4]);
			}


		else
			{
		document.getElementById('insurancePremium').value=trim(s1[0]);
		document.getElementById('otherChargeId').value=trim(s1[1]);
		//alert("filed "+document.getElementById('totalReceivableCustomer').value);
		//alert("customer amount "+trim(s1[2]));
		document.getElementById('chargesOnInsurance').value=trim(s1[2]);
		document.getElementById('lbxOtherChargeId').value=trim(s1[3]);
		document.getElementById('sms1').value=trim(s1[4]);

			}
		
		}

}

function saveCmInsurance()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var insuranceProvider= document.getElementById("insuranceProvider").value;
	//alert("insuranceProvider::"+insuranceProvider);
	var sumAssured= document.getElementById("sumAssured").value;
	var tenure= document.getElementById("tenure").value;
	//var age= document.getElementById("age").value;
	var insurancePremium= document.getElementById("insurancePremium").value;
	var chargesOnInsurance= document.getElementById("chargesOnInsurance").value;
	//for nominee
	var nomineeName= document.getElementById("nomineeName").value;	
	var gender= document.getElementById("gender").value;	
	var businessDate=document.getElementById("businessdate").value	
	 var policyType= document.getElementById("policyType").value;	
	var insuranceProduct=document.getElementById("insuranceProduct").value;
	var sumAssuPer=parseFloat(document.getElementById("sumAssuPer").value);
	var premiumFinanced=document.getElementById("premiumFinanced").value;
	var propertyType=document.getElementById("propertyType").value;
	var policyTenure=document.getElementById("policyTenure").value;
	var listSize=document.getElementById("listSize").value;	
	var alchecking=	 document.getElementsByName('alchecking');
	var sumAssuredMapping=parseFloat(document.getElementById("sumAssuredMapping").value);	
	 var dateOfbirth=document.getElementById("dateOfbirth").value;
     var dateOfbirth1=document.getElementById("dateOfbirth1").value;
     var dateOfbirth2=document.getElementById("dateOfbirth2").value;
      var dateOfbirth3=document.getElementById("dateOfbirth3").value;
    var dateOfbirth4=document.getElementById("dateOfbirth4").value;
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(businessDate,formatD.substring(2,3));
	var dt2=getDateObject(dateOfbirth,formatD.substring(2,3));
	var dt3=getDateObject(dateOfbirth1,formatD.substring(2,3));
	var dt4=getDateObject(dateOfbirth2,formatD.substring(2,3));
	var dt5=getDateObject(dateOfbirth3,formatD.substring(2,3));
	var dt6=getDateObject(dateOfbirth4,formatD.substring(2,3));
	var nomineeName1 = document.getElementById('nomineeName1').value;
	var nomineeName2 = document.getElementById('nomineeName2').value;
	var nomineeName3 = document.getElementById('nomineeName3').value;
	var nomineeName4 = document.getElementById('nomineeName4').value;
	var relationshp=document.getElementById("relationshp").value;
    var relationshp1=document.getElementById("relationshp1").value;
    var relationshp2=document.getElementById("relationshp2").value;
    var relationshp3=document.getElementById("relationshp3").value;
    var relationshp4=document.getElementById("relationshp4").value;
	var gender= document.getElementById("gender").value;
    var gender1= document.getElementById("gender1").value;
    var gender2= document.getElementById("gender2").value;
    var gender3= document.getElementById("gender3").value;
    var gender4= document.getElementById("gender4").value;
	var percentage = document.getElementById('percentage').value;
	var percentage1 = document.getElementById('percentage1').value;
	var percentage2 = document.getElementById('percentage2').value;
	var percentage3 = document.getElementById('percentage3').value;
	var percentage4 = document.getElementById('percentage4').value;
	var addr=document.getElementById('addr').value;	
	var addr1=document.getElementById('addr1').value;
	var addr2=document.getElementById('addr2').value;	
	var addr3=document.getElementById('addr3').value;	
	var addr4=document.getElementById('addr4').value;

	//Saurabh changes start here
	var sPrefix = document.getElementById('sPrefix').value;
	var nomineeMName = document.getElementById('nomineeMName').value;
	var nomineeLName = document.getElementById('nomineeLName').value;
	var smaritalStatus = document.getElementById('smaritalStatus').value;
	var snomineeArea = document.getElementById('snomineeArea').value;
	var snomineeCity = document.getElementById('snomineeCity').value;
	var snomineeState = document.getElementById('snomineeState').value;
	var snomineePin = document.getElementById('snomineePin').value;
	var sPrefix1 = document.getElementById('sPrefix1').value;
	var nomineeMName1 = document.getElementById('nomineeMName1').value;
	var nomineeLName1 = document.getElementById('nomineeLName1').value;
	var smaritalstatus1 = document.getElementById('smaritalstatus1').value;
	var sNomineeArea1 = document.getElementById('sNomineeArea1').value;
	var sNomineeCity1 = document.getElementById('sNomineeCity1').value;
	var sNomineeState1 = document.getElementById('sNomineeState1').value;
	var sNomineePin1 = document.getElementById('sNomineePin1').value;
	var sPrefix2 = document.getElementById('sPrefix2').value;
	var nomineeMName2 = document.getElementById('nomineeMName2').value;
	var nomineeLName2 = document.getElementById('nomineeLName2').value;
	var smaritalStatus2 = document.getElementById('smaritalStatus2').value;
	var sNomineeArea2 = document.getElementById('sNomineeArea2').value;
	var sNomineeCity2 = document.getElementById('sNomineeCity2').value;
	var sNomineeState2 = document.getElementById('sNomineeState2').value;
	var sNomineePin2 = document.getElementById('sNomineePin2').value;
	var sNomineePin2 = document.getElementById('sPrefix3').value;
	var nomineeMName3 = document.getElementById('nomineeMName3').value;
	var nomineeLName3 = document.getElementById('nomineeLName3').value;
	var smaritalStatus3 = document.getElementById('smaritalStatus3').value;
	var sNomineeArea3 = document.getElementById('sNomineeArea3').value;
	var sNomineeCity3 = document.getElementById('sNomineeCity3').value;
	var sNomineeState3 = document.getElementById('sNomineeState3').value;
	var sNomineePin3 = document.getElementById('sNomineePin3').value;
	var sPrefix4 = document.getElementById('sPrefix4').value;
	var nomineeMName4 = document.getElementById('nomineeMName4').value;
	var nomineeLName4 = document.getElementById('nomineeLName4').value;
	var smaritalStatus4 = document.getElementById('smaritalStatus4').value;
	var sNomineeArea4 = document.getElementById('sNomineeArea4').value;
	var sNomineeCity4 = document.getElementById('sNomineeCity4').value;
	var sNomineeState4 = document.getElementById('sNomineeState4').value;
	var sNomineePin4 = document.getElementById('sNomineePin4').value;
	var saddressType = document.getElementById('saddressType').value;
	var sinsuranceStreet = document.getElementById('sinsuranceStreet').value;
	
	var saddressType1 = document.getElementById('saddressType1').value;
	var sinsuranceStreet1 = document.getElementById('sinsuranceStreet1').value;
	var saddressType2 = document.getElementById('saddressType2').value;
	var sinsuranceStreet2 = document.getElementById('sinsuranceStreet2').value;
	var saddressType3 = document.getElementById('saddressType3').value;
	var sinsuranceStreet3 = document.getElementById('sinsuranceStreet3').value;
	var saddressType4 = document.getElementById('saddressType4').value;
	var sinsuranceStreet4 = document.getElementById('sinsuranceStreet4').value;
	if(sumAssured==0){
		alert("Sum Assured should be greater than 0");
		 DisButClass.prototype.EnbButMethod();
		 return false;
	}
	//Saurabh changes starts here
	if(percentage!="")
	{
		percentage=parseInt(percentage);
	}
	else percentage=parseInt(0);
	if(percentage1!="")
	{
		percentage1=parseInt(percentage1);
	}
	else percentage1=parseInt(0);
	if(percentage2!="")
	{
		percentage2=parseInt(percentage2);
	}
	else percentage2=parseInt(0);
	if(percentage3!="")
	{
		percentage3=parseInt(percentage3);
	}
	else percentage3=parseInt(0);
	if(percentage4!="")
	{
		percentage4=parseInt(percentage4);
	}
	else percentage4=parseInt(0);

	if (dateOfbirth!='' || dateOfbirth1!='' ||dateOfbirth2!='' ||dateOfbirth3!='' ||dateOfbirth4!=''){
		if(dt2>=dt1)
		{
			alert("First Nominee Date of birth must be less than Business Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		else if(dt3>=dt1)
		{
			alert("Second Nominee Date of birth must be less than Business Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;

		}
		else if(dt4>=dt1)
		{
			alert("Third Nominee Date of birth must be less than Business Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;

		}
		else if(dt5>=dt1)
		{
			alert("Fourth Nominee Date of birth must be less than Business Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;

		}
		if(dt6>=dt1)
		{
			alert("Fifth Nominee Date of birth must be less than Business Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;

		}}
		
		if (trim(nomineeName)!='' || trim(nomineeName1)!=''|| trim(nomineeName2)!='' || trim(nomineeName3)!='' || trim(nomineeName4)!='')
		{
			/*if((trim(nomineeName)!='' && trim(nomineeName1)!='') && (trim(nomineeName)==trim(nomineeName1)))
			{
				alert("Nominee Name Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName)!='' && trim(nomineeName2)!='') && (trim(nomineeName)==trim(nomineeName2)))
			{
				alert("Nominee Name Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName)!='' && trim(nomineeName3)!='') && (trim(nomineeName)==trim(nomineeName3)))
			{
				alert("Nominee Name Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName)!='' && trim(nomineeName4)!='') && (trim(nomineeName)==trim(nomineeName4)))
			{
				alert("Nominee Name Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName1)!='' && trim(nomineeName2)!='') && (trim(nomineeName1)==trim(nomineeName2)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName1)!='' && trim(nomineeName3)!='') && (trim(nomineeName1)==trim(nomineeName3)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName1)!='' && trim(nomineeName4)!='') && (trim(nomineeName1)==trim(nomineeName4)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName2)!='' && trim(nomineeName3)!='') && (trim(nomineeName2)==trim(nomineeName3)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName2)!='' && trim(nomineeName4)!='') && (trim(nomineeName2)==trim(nomineeName4)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((trim(nomineeName3)!='' && trim(nomineeName4)!='') && (trim(nomineeName3)==trim(nomineeName4)))
			{
				alert("Nominee Names Should be unique.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}*/
			var result=percentage+percentage1+percentage2+percentage3+percentage4;
			
			if(result!=100)
			{
				alert("sum of percentage must be 100.");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
		}
	//Saurabh changes ends here
	
	
	var count = 0;
	var flag=0;
	var ids="";
	var temp="";
	var id="";
	var sms1=document.getElementById('sms1').value;
	var msg= '';
	//alert("ssss:::"+alchecking.length);
	
		for(var i=0; i<alchecking.length;i++)
		{
			if(alchecking[i].checked==true)
				{
					id=alchecking[i].value;
					ids=ids+id+"/";
				   //alert(id);
	 			   	flag=1;
	 			   	count++;
				}
			
		}
			
		
		 if(flag==0)
	 	 {


	 		 alert("Please select atleast one Record");
	 		 DisButClass.prototype.EnbButMethod();
	 		 document.getElementById("save").removeAttribute("disabled","true");
	 		 return false;


	 	 }
		 if(count>1 && policyType=='S')
			{
			alert("Please select 1 checkbox only.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		 else if(policyType=='J' && count==1 )
			 {
			 alert("Please select more than 1 records.");
			 DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
			 return false;
			 }
		 else
			 {
			 temp=ids;
			 }
		 /*if(sumAssuPer<1 || sumAssuPer>sumAssuredMapping)
		 {
		 	alert('* Sum Assured percent should be in between 1 and '+sumAssuredMapping);
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		 }*/
		
		if (trim(nomineeName) != "" && percentage =='') {
		var msg1 = '';
		msg1 += '*percentage1 is  required.\n';

		if (msg1.match("percentage")) {
			document.getElementById("percentage").focus();
		}
		alert(msg1);
		
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}

	
	if (trim(nomineeName1) != '' && percentage1 =='') {
		var msg1 = '';
		msg1 += '*percentage2 is  required.\n';

		if (msg1.match("percentage1")) {
			document.getElementById("percentage1").focus();
		}
		alert(msg1);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}	
	
	if (trim(nomineeName2) != '' && percentage2 =='') {
		var msg1 = '';
		msg1 += '*percentage3 is  required.\n';

		if (msg1.match("percentage2")) {
			document.getElementById("percentage2").focus();
		}
		alert(msg1);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}
	
	if (trim(nomineeName3) != '' && percentage3 =='') {
		var msg1 = '';
		msg1 += '*percentage4 is  required.\n';

		if (msg1.match("percentage3")) {
			document.getElementById("percentage3").focus();
		}
		alert(msg1);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}
	
	if (trim(nomineeName4) != '' && percentage4 =='') {
		var msg1 = '';
		msg1 += '*percentage5 is  required.\n';

		if (msg1.match("percentage4")) {
			document.getElementById("percentage4").focus();
		}
		alert(msg1);
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled", "true");
		return false;
	}

	 if((trim(dateOfbirth)!='' || (percentage)!='' || trim(relationshp)!='' || trim(gender)!='' ||trim(addr)!='' || trim(sPrefix)!='' ||trim(nomineeMName)!='' ||trim(nomineeLName)!='' ||trim(smaritalStatus)!='' ||trim(saddressType)!='' ||trim(sinsuranceStreet)!='' ||trim(snomineeCity)!='' ||trim(snomineeState)!='' ||(snomineePin)!='') && trim(nomineeName)=='')
		{
			alert("Nominee Name1 is required.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		if((trim(dateOfbirth1)!='' ||(percentage1)!='' || trim(relationshp1)!='' || trim(gender1)!='' ||trim(addr1)!='' || trim(sPrefix1)!='' ||trim(nomineeMName1)!='' ||trim(nomineeLName1)!='' ||trim(smaritalstatus1)!='' ||trim(saddressType1)!='' ||trim(sinsuranceStreet1)!='' ||trim(sNomineeCity1)!='' ||trim(sNomineeState1)!='' ||(sNomineePin1)!='') && trim(nomineeName1)=='')
		{
			alert("Nominee Name2 is required.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		if((trim(dateOfbirth2)!='' ||(percentage2)!='' || trim(relationshp2)!='' || trim(gender2)!='' ||trim(addr2)!='' || trim(sPrefix2)!='' ||trim(nomineeMName2)!='' ||trim(nomineeLName2)!='' ||trim(smaritalStatus2)!='' ||trim(saddressType2)!='' ||trim(sinsuranceStreet2)!='' ||trim(sNomineeCity2)!='' ||trim(sNomineeState2)!='' ||(sNomineePin2)!='') && trim(nomineeName2)=='')
		{
			alert("Nominee Name3 is required.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		if((trim(dateOfbirth3)!='' ||(percentage3)!='' || trim(relationshp3)!='' || trim(gender3)!='' ||trim(addr3)!='' ||trim(nomineeMName3)!='' ||trim(nomineeLName3)!='' ||trim(smaritalStatus3)!='' ||trim(saddressType3)!='' ||trim(sinsuranceStreet3)!='' ||trim(sNomineeCity3)!='' ||trim(sNomineeState3)!='' ||(sNomineePin3)!='') && trim(nomineeName3)=='')
		{
			alert("Nominee Name4 is required.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		if((trim(dateOfbirth4)!='' ||(percentage4)!='' || trim(relationshp4)!='' || trim(gender4)!='' ||trim(addr4)!='' || (sPrefix4)!='' ||trim(nomineeMName4)!='' ||trim(nomineeLName4)!='' || (smaritalStatus4)!='' || (saddressType4)!='' ||trim(sinsuranceStreet4)!='' ||trim(sNomineeCity4)!='' ||trim(sNomineeState4)!='' ||(sNomineePin4)!='') && trim(nomineeName4)=='')
		{
			alert("Nominee Name5 is required.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	
		if(trim(nomineeName)=='' && (trim(nomineeName1)!='' || trim(nomineeName2)!='' || trim(nomineeName3)!='' || trim(nomineeName4)!=''))
			{
			alert("First fill nominee1 details. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(nomineeName1)=='' && (trim(nomineeName2)!='' || trim(nomineeName3)!='' || trim(nomineeName4)!=''))
		{
		alert("First fill nominee2 details. ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
		}
		if(trim(nomineeName2)=='' && (trim(nomineeName3)!='' || trim(nomineeName4)!=''))
		{
		alert("First fill nominee3 details. ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
		}
		if(trim(nomineeName3)=='' && (trim(nomineeName4)!=''))
		{
		alert("First fill nominee4 details. ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
		}
		
			if(trim(nomineeName) != '' ||trim(dateOfbirth) != ''||trim(gender) != ''||trim(relationshp) != '' || (percentage)!='' ||trim(addr)!='' || trim(sPrefix)!='' ||trim(nomineeLName)!='' ||trim(smaritalStatus)!='' ||trim(saddressType)!='' ||trim(sinsuranceStreet)!='' ||trim(snomineeCity)!='' ||trim(snomineeState)!='' ||(snomineePin)!=''){			
			if(trim(dateOfbirth) == ''){
				alert("*Date of birth of First Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender) == ''){
				alert("*Gender of First Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp) == ''){
				alert("*Relationship with policy header of First Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr)=='')
			{
				alert("*House No/Flat No of First Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(sPrefix)=='')
			{
			alert("*Prefix of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(nomineeLName)=='')
			
			{
			alert("*Last Name of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
				
			}
		if(trim(smaritalStatus)=='')
			{
			alert("*Marital Status of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(saddressType)=='')
			{
			alert("*Address Type of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(sinsuranceStreet)=='')
			{
			alert("*Street of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(snomineeCity)=='')
			{
			alert("*City of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(trim(snomineeState)=='')
			{
			alert("*State of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
		if(snomineePin=='')
			{
			alert("*Pincode of First Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
}
if(trim(nomineeName1) != '' ||trim(dateOfbirth1) != ''||trim(gender1) != ''||trim(relationshp1) != '' || (percentage1)!='' ||trim(addr1)!='' || trim(sPrefix1)!='' ||trim(nomineeLName1)!='' ||trim(smaritalstatus1)!='' ||trim(saddressType1)!='' ||trim(sinsuranceStreet1)!='' ||trim(sNomineeCity1)!='' ||trim(sNomineeState1)!='' ||(sNomineePin1)!=''){			
			if(trim(dateOfbirth1) == ''){
				alert("*Date of birth of Second Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender1) == ''){
				alert("*Gender of second Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp1) == ''){
				alert("*Relationship with policy header of second Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr1)=='')
			{
				alert("*House No/Flat No of second Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(sPrefix1)=='')
			{
			alert("*Prefix of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(nomineeLName1)=='')
			
			{
			alert("*Last Name of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
				
			}
			if(trim(smaritalstatus1)=='')
			{
			alert("*Marital Status of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(saddressType1)=='')
			{
			alert("*Address Type of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sinsuranceStreet1)=='')
			{
			alert("*Street of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeCity1)=='')
			{
			alert("*City of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeState1)=='')
			{
			alert("*State of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(sNomineePin1=='')
			{
			alert("*Pincode of second Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
}
	if(trim(nomineeName2) != '' ||trim(dateOfbirth2) != ''||trim(gender2) != ''||trim(relationshp2) != '' || (percentage2)!='' ||trim(addr2)!='' || trim(sPrefix2)!='' ||trim(nomineeLName2)!='' ||trim(smaritalStatus2)!='' ||trim(saddressType2)!='' ||trim(sinsuranceStreet2)!='' ||trim(sNomineeCity2)!='' ||trim(sNomineeState2)!='' ||(sNomineePin2)!=''){			
			if(trim(dateOfbirth2) == ''){
				alert("*Date of birth of third Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender2) == ''){
				alert("*Gender of third Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp2) == ''){
				alert("*Relationship with policy header of third Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr2)=='')
			{
				alert("*House No/Flat No of third Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(sPrefix2)=='')
			{
			alert("*Prefix of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(nomineeLName2)=='')
			
			{
			alert("*Last Name of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
				
			}
			if(trim(smaritalStatus2)=='')
			{
			alert("*Marital Status of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(saddressType2)=='')
			{
			alert("*House No/Flat No Type of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sinsuranceStreet2)=='')
			{
			alert("*Street of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeCity2)=='')
			{
			alert("*City of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeState2)=='')
			{
			alert("*State of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(sNomineePin2=='')
			{
			alert("*Pincode of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
}
if(trim(nomineeName3) != '' ||trim(dateOfbirth3) != ''||trim(gender3) != ''||trim(relationshp3) != '' || (percentage3)!='' ||trim(addr3)!='' ||trim(nomineeLName3)!='' || (smaritalStatus3)!='' || (saddressType3)!='' ||trim(sinsuranceStreet3)!='' ||trim(sNomineeCity3)!='' ||trim(sNomineeState3)!='' ||(sNomineePin3)!=''){			
			if(trim(dateOfbirth3) == ''){
				alert("*Date of birth of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender3) == ''){
				alert("*Gender of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp3) == ''){
				alert("*Relationship with policy header of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr3)=='')
			{
				alert("*House No/Flat No of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if((sPrefix3)=='')
			{
			alert("*Prefix of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(nomineeLName3)=='')
			
			{
			alert("*Last Name of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
				
			}
			if(trim(smaritalStatus3)=='')
			{
			alert("*Marital Status of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if((saddressType3)=='')
			{
			alert("*Address Type of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sinsuranceStreet3)=='')
			{
			alert("*Street of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeCity3)=='')
			{
			alert("*City of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeState3)=='')
			{
			alert("*State of third Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(sNomineePin3=='')
			{
			alert("*Pincode of fourth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
}
/*if(trim(nomineeName4) != '' ||trim(dateOfbirth4) != ''||trim(gender4) != ''||trim(relationshp4) != '' || (percentage4)!='' ||trim(addr4)!='' || trim(sPrefix4)!='' ||trim(nomineeLName4)!='' ||trim(smaritalStatus4)!='' ||trim(saddressType4)!='' ||trim(sinsuranceStreet4)!='' ||trim(sNomineeCity4)!='' ||trim(sNomineeState4)!='' ||(sNomineePin4)!=''){			
			if(trim(dateOfbirth3) == ''){
				alert("*Date of birth of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender3) == ''){
				alert("*Gender of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp3) == ''){
				alert("*Relationship with policy header of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr3)=='')
			{
				alert("*Address of fourth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
}*/
if(trim(nomineeName4) != '' ||trim(dateOfbirth4) != ''||trim(gender4) != ''||trim(relationshp4) != '' || (percentage4)!='' ||trim(addr4)!='' || trim(sPrefix4)!='' ||trim(nomineeLName4)!='' ||trim(smaritalStatus4)!='' ||trim(saddressType4)!='' ||trim(sinsuranceStreet4)!='' ||trim(sNomineeCity4)!='' ||trim(sNomineeState4)!='' ||(sNomineePin4)!=''){			
			if(trim(dateOfbirth4) == ''){
				alert("*Date of birth of Fifth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(gender4) == ''){
				alert("*Gender of Fifth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(relationshp4) == ''){
				alert("*Relationship with policy header of Fifth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(addr4)=='')
			{
				alert("*House No/Flat No of Fifth Nominee is required. ");
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(trim(sPrefix4)=='')
			{
			alert("*Prefix of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(nomineeLName4)=='')
			
			{
			alert("*Last Name of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
				
			}
			if(trim(smaritalStatus4)=='')
			{
			alert("*Marital Status of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(saddressType4)=='')
			{
			alert("*Address Type of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sinsuranceStreet4)=='')
			{
			alert("*Street of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeCity4)=='')
			{
			alert("*City of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(trim(sNomineeState4)=='')
			{
			alert("*State of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
			if(sNomineePin4=='')
			{
			alert("*Pincode of Fifth Nominee is required. ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
			}
}
	//if(trim(insuranceProvider) == '' || trim(sumAssured) =='' ||trim(tenure) == ''||trim(age) == ''||trim(insurancePremium) == ''||trim(chargesOnInsurance) == '')
		//  if(trim(insuranceProvider) == '' || trim(sumAssured) =='' ||trim(tenure) == ''||trim(insurancePremium) == ''||trim(chargesOnInsurance) == ''|| trim(policyType) == '' || trim(insuranceProduct) == '' || trim(premiumFinanced) == ''|| trim(propertyType) == ''|| trim(insuranceProduct) == ''|| trim(policyTenure)=='')	

		 if(trim(insuranceProvider) == '' || trim(sumAssured) =='' ||trim(tenure) == ''||trim(insurancePremium) == ''|| trim(policyType) == '' || trim(insuranceProduct) == '' || trim(premiumFinanced) == ''|| trim(insuranceProduct) == ''|| trim(policyTenure)=='' ||trim(chargesOnInsurance) == '' )
			{		
				
				
				/*if(trim(propertyType) == ''){
					msg += '* Property Type  is required.\n';
				}*/
				
				if(trim(policyTenure) == ''){
					msg += '* Policy Tenure  is required.\n';
				}
				
				
				if(trim(policyType) == ''){
					msg += '* Policy Type  is required.\n';
				}
				if(trim(insuranceProduct) == ''){
					msg += '* Insurance Product  is required.\n';
				}
				if(trim(premiumFinanced) == ''){
					msg += '* Insurance Premium to be Financed  is required.\n';
				}
				if(trim(insuranceProvider) == ''){
					msg += '* Insurance Provider  is required.\n';
				}
				if(trim(sumAssured) == ''){
					msg += '* Sum Assured  is required.\n';
				}
				if(trim(tenure) == ''){
					msg += '* Loan Tenure  is required.\n';
				}
				
				if(trim(insurancePremium) == ''){ 
					msg += '* Insurance Premium  is required.\n';
				}
				if(trim(chargesOnInsurance) == ''){
					msg += '* Charges On Premium  is required.\n';
				}
				
				if(msg.match("Insurance")){
					document.getElementById("insuranceProvider").focus();
					
				}else if(msg.match("Sum")){
					document.getElementById("sumAssured").focus();
				}else if(msg.match("Tenure")){
					document.getElementById("tenure").focus();
				}
				
				else if(msg.match("Premium")){
					document.getElementById("insurancePremium").focus();
				}
				/*else if(msg.match("charges")){
					document.getElementById("chargesOnInsurance").focus();
				}*/
				else if(msg.match("policyType")){
					document.getElementById("policyType").focus();
				}
				else if(msg.match("insuranceProduct")){
					document.getElementById("insuranceProduct").focus();
				}
				else if(msg.match("premiumFinanced")){
					document.getElementById("premiumFinanced").focus();
				}
				
				
				else if(msg.match("propertyType")){
					document.getElementById("propertyType").focus();
				}
				
				
				else if(msg.match("policyTenure")){
					document.getElementById("policyTenure").focus();
				}
				
				
				else if(msg.match("policyType")){
					document.getElementById("policyType").focus();
				}
				
				
				else if(msg.match("insuranceProduct")){
					document.getElementById("insuranceProduct").focus();
				}
				
				alert(msg);
				
				DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
				return false;	
				}
		
			 
		 
		 
	else
	{

		
		// if(sms1!='saveCal')
		if((sms1=="") || (sms1=='notCal' && insurancePremium==""))
		{
		alert("Please calculate the Data First");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
		}
		else if(sms1=='notCal' && insurancePremium!="")
		{
			
			document.getElementById("cmInsuranceForm").action=sourcepath+"/cmInsurance.do?method=saveCmInsurance&ids="+temp;
			document.getElementById("processingImage").style.display = '';

			document.getElementById("cmInsuranceForm").submit();
			return true;
			
		}

	else
		{
		document.getElementById("cmInsuranceForm").action=sourcepath+"/cmInsurance.do?method=saveCmInsurance&ids="+temp;
		document.getElementById("processingImage").style.display = '';

		document.getElementById("cmInsuranceForm").submit();
		return true;
	}


	}
}


function deleteCmInsurance()
{
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var insurancePremium = document.getElementById("insurancePremium").value;
	var sms = document.getElementById("sms").value;
	
	var alchecking=	 document.getElementsByName('chk');
	var count = 0;
	var flag=0;
	var ids="";
	var temp="";
	var id="";
	//alert("amandee:::"+alchecking.length);
	
		for(var i=0; i<alchecking.length;i++)
		{
			if(alchecking[i].checked==true)
				{
					id=alchecking[i].value;
					ids=ids+id+"/";
				   //alert(id);
	 			   	flag=1;
	 			   	count++;
				}
			
		}
			
		
		 if(flag==0)
	 	 {
			 
				 
					 alert("Please select Record");
			 		 DisButClass.prototype.EnbButMethod();
			 		 document.getElementById("delete").removeAttribute("disabled","true");
			 		 return false;
				 }
		 else
		 {
		 temp=ids;
		 }	 
	
	
	/*if(flag>0)






		{
		alert("Please Calculate a Insurance Premium");
		DisButClass.prototype.EnbButMethod();
		return false;
		}*/
		 
		 if(flag>0)
			{
		document.getElementById("cmInsuranceForm").action=sourcepath+"/cmInsurance.do?method=deleteCmInsurance&ids="+temp;
		document.getElementById("processingImage").style.display = '';
		

		document.getElementById("cmInsuranceForm").submit();
				return true;
			}
}


function sumAssuredPercent()
	{
		document.getElementById("sumAssuPer").value=100;
	}
function sumAssuredAmount()
{
//alert("amandeep");
var sumAssured=document.getElementById("sumAssured").value;
var sumAssuPer=document.getElementById("sumAssuPer").value;
var sum=document.getElementById("sum").value;
var amount=0;	
	/*alert("sumAssuPer"+sumAssuPer);
	alert("sumAssured"+sumAssured);
	alert("sum"+sum);*/
	amount=Math.round(sum*(sumAssuPer/100));
	//alert("amount"+amount);
	document.getElementById("sumAssured").value=amount;
	
}

$(function() {
	$("#dateOfbirth,#dateOfbirth1,#dateOfbirth2,#dateOfbirth3,#dateOfbirth4").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
	});
		}); 



function getInsuranceProduct()
{
	var insuranceProvider= document.getElementById("insuranceProvider").value;
	 //alert("insuranceProvider:::"+insuranceProvider);
	
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/cmInsurance.do?method=getInsuranceProduct";
		 var data = "insuranceProvider="+insuranceProvider;
		 sendForGetInsuranceProduct(address, data);
					 
}

function sendForGetInsuranceProduct(address, data) {
	//alert("in sendForgetCustomerName id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultInsuranceProduct(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultInsuranceProduct(request){
	
	//alert("in resultCustomerName id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert("String:--->"+str);
		document.getElementById('insuranceProductDiv').innerHTML=trim(str);
		
	}
}

function editInsuranceDetails(id){
	
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var insuranceId=document.getElementById("insuranceId");
	//alert("insuranceId::"+id);
	
	document.getElementById("cmInsuranceForm").action=sourcepath+"/cmInsurance.do?method=editInsuranceDetails&insuranceId="+id;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("cmInsuranceForm").submit();
	return true;

}


function relodInsuranceDataLoan(id)

{
     	
	var sourcepath = document.getElementById('contextPath').value;
	//var insuranceId=document.getElementById('insuranceId').value;
	//document.getElementById("cmInsuranceForm").action=contextPath+"/cmInsurance.do?method=relodInsuranceData&insuranceId="+id;
 	//document.getElementById("cmInsuranceForm").submit();
 	//return true;

	s2=document.getElementsByName("alchecking");
	var temp=s2.length;
	for(k=1;k<=temp;k++)
		{
		//alert('inside for loop');
		document.getElementById("allchk"+k).checked=false;
		}


var address = sourcepath+"/cmInsurance.do?method=relodInsuranceData";
var data = "insuranceId="+id;

//document.getElementById("cpInsuranceForm").action=sourcepath+"/cpInsurance.do?method=calculateCpInsurance&ids="+temp;
relodInsuranceDataLoanAjax(address, data);
/*document.getElementById("processingImage").style.display = '';
document.getElementById("cpInsuranceForm").submit();*/
return true;
	  
}



function relodInsuranceDataLoanAjax(address, data) {
	//alert("in sendDisbursalloanId id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultRelodInsuranceDataInsuranceLoan(request);	

	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
     	
}
     	
function resultRelodInsuranceDataInsuranceLoan(request)

{
     	
	var s1;
	var i;
        	
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		s1 = str.split("$:");
		var s2=document.getElementsByName("alchecking");
		var temp=s2.length;
		document.getElementById("insFlag").value=true;

		for(i = 1; i<= temp;i++)
		{
       
		var s3=document.getElementById("allchk"+i).value;
		var s5 =s1[8].split(",");
		var temp1=s5.length;
		//alert("temp1::"+temp1);
		// alert("s5::"+s5);
		 for( j=0;j<temp1;j++)
		 {
		//alert('s5[j]::'+s5[j]);
		//alert("s2::"+s2);
		//alert("temp::"+temp);
		
		//alert('Ram values :'+document.getElementById("allchk"+i).value);
		//alert('Shyam Value : '+trim(s5[j]));
		if(document.getElementById("allchk"+i).value==trim(s5[j]))
		{
			//alert("hello 1");
			//alert('amandeep :'+(document.getElementById("allchk"+i).value));
		document.getElementById("allchk"+i).checked=true;
		}
		/*else
		{
		alert("hello 22");
		alert('amandeep11 :'+(document.getElementById("allchk"+i).value));
		document.getElementById("allchk"+i).checked=false;
		}*/
		}
		}
		document.getElementById('insuranceProvider').value=trim(s1[0]);
		document.getElementById('sumAssured').value=trim(s1[1]);
		//alert("filed "+document.getElementById('totalReceivableCustomer').value);
		//alert("customer amount "+trim(s1[2]));
		document.getElementById('tenure').value=trim(s1[2]);
		document.getElementById('insurancePremium').value=trim(s1[3]);
		
		document.getElementById('chargesOnInsurance').value=trim(s1[4]);
		document.getElementById('otherChargeId').value=trim(s1[5]);
		document.getElementById('policyType').value=trim(s1[6]);
		document.getElementById('premiumFinanced').value=trim(s1[7]);
		//document.getElementById('allchk').value=trim(s1[8]);
		//document.getElementById('insuranceProduct').value=trim(s1[9]);
		//alert("trim(s1[9])::"+trim(s1[9]));
		//document.getElementById('id').value=trim(s1[9]);
		//alert("trim(s1[9])::"+trim(s1[9]));
		document.getElementById('nomineeName').value=trim(s1[10]);
		document.getElementById('dateOfbirth').value=trim(s1[11]);
		document.getElementById('gender').value=trim(s1[12]);
		document.getElementById('relationshp').value=trim(s1[13]);
		document.getElementById('policyTenure').value=trim(s1[14]);
		document.getElementById('sumAssuPer').value=trim(s1[15]);
		document.getElementById('propertyType').value=trim(s1[16]);

		document.getElementById('insuranceProductDiv').innerHTML=trim(s1[17]);
		document.getElementById('nomineeName1').value=trim(s1[18]);
		document.getElementById('gender1').value=trim(s1[19]);
		document.getElementById('dateOfbirth1').value=trim(s1[20]);
		document.getElementById('relationshp1').value=trim(s1[21]);
		document.getElementById('nomineeName2').value=trim(s1[22]);
		document.getElementById('gender2').value=trim(s1[23]);
		document.getElementById('dateOfbirth2').value=trim(s1[24]);
		document.getElementById('relationshp2').value=trim(s1[25]);
		document.getElementById('nomineeName3').value=trim(s1[26]);
		document.getElementById('gender3').value=trim(s1[27]);
		document.getElementById('dateOfbirth3').value=trim(s1[28]);
		document.getElementById('relationshp3').value=trim(s1[29]);		
		document.getElementById('nomineeName4').value=trim(s1[30]);		
		document.getElementById('gender4').value=trim(s1[31]);		
		document.getElementById('dateOfbirth4').value=trim(s1[32]);
		document.getElementById('relationshp4').value=trim(s1[33]);		
		document.getElementById('percentage').value=trim(s1[34]);
		document.getElementById('percentage1').value=trim(s1[35]);
		document.getElementById('percentage2').value=trim(s1[36]);
		document.getElementById('percentage3').value=trim(s1[37]);
		document.getElementById('percentage4').value=trim(s1[38]);
		document.getElementById('addr').value=trim(s1[39]);
		document.getElementById('addr1').value=trim(s1[40]);
		document.getElementById('addr2').value=trim(s1[41]);
		document.getElementById('addr3').value=trim(s1[42]);
		document.getElementById('addr4').value=trim(s1[43]);
		
		//Saurabh changes start here
		document.getElementById('sPrefix').value=trim(s1[44]);
		document.getElementById('nomineeMName').value=trim(s1[45]);
		document.getElementById('nomineeLName').value=trim(s1[46]);
		document.getElementById('smaritalStatus').value=trim(s1[47]);
		document.getElementById('snomineeArea').value=trim(s1[48]);
		document.getElementById('snomineeCity').value=trim(s1[49]);
		document.getElementById('snomineeState').value=trim(s1[50]);
		document.getElementById('snomineePin').value=trim(s1[51]);
		document.getElementById('sPrefix1').value=trim(s1[52]);
		document.getElementById('nomineeMName1').value=trim(s1[53]);
		document.getElementById('nomineeLName1').value=trim(s1[54]);
		document.getElementById('smaritalstatus1').value=trim(s1[55]);
		document.getElementById('sNomineeArea1').value=trim(s1[56]);
		document.getElementById('sNomineeCity1').value=trim(s1[57]);
		document.getElementById('sNomineeState1').value=trim(s1[58]);
		document.getElementById('sNomineePin1').value=trim(s1[59]);
		document.getElementById('sPrefix2').value=trim(s1[60]);
		document.getElementById('nomineeMName2').value=trim(s1[61]);
		document.getElementById('nomineeLName2').value=trim(s1[62]);
		document.getElementById('smaritalStatus2').value=trim(s1[63]);
		document.getElementById('sNomineeArea2').value=trim(s1[64]);
		document.getElementById('sNomineeCity2').value=trim(s1[65]);
		document.getElementById('sNomineeState2').value=trim(s1[66]);
		document.getElementById('sNomineePin2').value=trim(s1[67]);
		document.getElementById('sPrefix3').value=trim(s1[68]);
		document.getElementById('nomineeMName3').value=trim(s1[69]);
		document.getElementById('nomineeLName3').value=trim(s1[70]);
		document.getElementById('smaritalStatus3').value=trim(s1[71]);
		document.getElementById('sNomineeArea3').value=trim(s1[72]);
		document.getElementById('sNomineeCity3').value=trim(s1[73]);
		document.getElementById('sNomineeState3').value=trim(s1[74]);
		document.getElementById('sNomineePin3').value=trim(s1[75]);
		document.getElementById('sPrefix4').value=trim(s1[76]);
		document.getElementById('nomineeMName4').value=trim(s1[77]);
		document.getElementById('nomineeLName4').value=trim(s1[78]);
		document.getElementById('smaritalStatus4').value=trim(s1[79]);
		document.getElementById('sNomineeArea4').value=trim(s1[80]);
		document.getElementById('sNomineeCity4').value=trim(s1[81]);
		document.getElementById('sNomineeState4').value=trim(s1[82]);
		document.getElementById('sNomineePin4').value=trim(s1[83]);
		document.getElementById('saddressType').value=trim(s1[84]);
		document.getElementById('sinsuranceStreet').value=trim(s1[85]);
		
		document.getElementById('saddressType1').value=trim(s1[86]);
		document.getElementById('sinsuranceStreet1').value=trim(s1[87]);
		document.getElementById('saddressType2').value=trim(s1[88]);
		document.getElementById('sinsuranceStreet2').value=trim(s1[89]);
		document.getElementById('saddressType3').value=trim(s1[90]);
		document.getElementById('sinsuranceStreet3').value=trim(s1[91]);
		document.getElementById('saddressType4').value=trim(s1[92]);
		document.getElementById('sinsuranceStreet4').value=trim(s1[93]);
		
		document.getElementById('txtStateCode').value=trim(s1[94]);
		document.getElementById('txtStateCode1').value=trim(s1[95]);
		document.getElementById('txtStateCode2').value=trim(s1[96]);
		document.getElementById('txtStateCode3').value=trim(s1[97]);
		document.getElementById('txtStateCode4').value=trim(s1[98]);
		//Saurabh changes ends here
		document.getElementById('assetFlag').value=trim(s1[99]);
		
		
		insuranceServiceCalled();
		}

}   	
function allChecked()
{
		
	var c = document.getElementById("allchked").checked;
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

//CHANGE STARTED BY ASHISH
function insuranceServiceCalled()
{
	var insuranceProduct=document.getElementById('insuranceProduct').value;
	var str=insuranceProduct.split('|');
	var sumAssuredMapping="";
	insuranceProduct=str[0];
	var service=str[1];
	sumAssuredMapping=str[2];
	if(service=='E')
	{
		document.getElementById('insurancePremium').readOnly=false;
		document.getElementById('chargesOnInsurance').readOnly=false;
		document.getElementById('sms1').value='saveCal';
		document.getElementById('sumAssuredMapping').value=sumAssuredMapping;
	}
	else
	{
		document.getElementById('insurancePremium').readOnly=false;
		document.getElementById('chargesOnInsurance').readOnly=false;
		document.getElementById('sms1').value='';
		document.getElementById('sumAssuredMapping').value=sumAssuredMapping;
	}
}

function clearData()
{
	//document.getElementById("policyType").value='';
	//document.getElementById("premiumFinanced").value='';
	document.getElementById("insurancePremium").value='';
	document.getElementById("otherChargeId").value='';
	document.getElementById("chargesOnInsurance").value='';
}

//CHANGE ENDED BY ASHISH


function isNumberKey(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 45 || charCode > 57))
{
	alert("Only Numeric Allowed Here");
	return false;
}
	return true;
}

function sumAssuredAmountPercentage()
{
	//alert("amandeep");
	var sumAssured=document.getElementById("sumAssured").value;
	var sumAssuPer=document.getElementById("sumAssuPer").value;
	var sum=document.getElementById("sum").value;
	
	
	var amount=0;	
	
	amount = Math.round((sumAssured/sum)*100);	
	//alert("amount"+amount);
//	amount=Math.round(sum*(sumAssuPer/100));
		
	document.getElementById("sumAssuPer").value=amount;
		
	}
	function checkRate(val){
		var rate = document.getElementById(val).value;
		if(rate==''){
			rate=0;
		}
		var intRate = parseFloat(rate);
		if(intRate>=0 && intRate<=100) {
			return true;
		}else{
			alert("Please Enter the value b/w 0 to 100");
			document.getElementById(val).value="";
			return false;
		}
	}
	function isPercentKey(evt) 
	{
		var charCode = (evt.which) ? evt.which : event.keyCode;

		if (charCode > 31 && (charCode < 45 || charCode > 57 || charCode == 46))
		{
			alert("Only Numeric Allowed Here");
			return false;
		}
			return true;
	}
	
	
