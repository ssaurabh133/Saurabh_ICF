function searchDeal()
{
	
	DisButClass.prototype.DisButMethod();
	//alert("parvez check")
	var dealID=document.getElementById("dealID").value;
	//var leadno = document.getElementById("leadno").value;
	if(dealID.trim()=='')//&& leadno.trim() =='' )
	{
		alert("Please select search criteria.");
		DisButClass.prototype.EnbButMethod();
		return false;		
	}	
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilVerificationSearch";
	document.getElementById("processingImage").style.display = '';
    document.getElementById("cibilVerification").submit();
}

//parvez
function searchLead()
{
	
	DisButClass.prototype.DisButMethod();
	
	var dealID=document.getElementById("leadID").value;
	if(leadID='')
	{
		alert("First select Lead-No.")
		DisButClass.prototype.EnbButMethod();
		return false;		
	}	
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilVerificationSearch";
	document.getElementById("processingImage").style.display = '';
    document.getElementById("cibilVerification").submit();
}
//end parvez

function searchCibilDeal()
{
	
	var dealID=document.getElementById("dealID").value;
	if(dealID='')
	{
		alert("First select Deal-No.")
		DisButClass.prototype.EnbButMethod();
		return false;		
	}	
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("cibilVerification").action = contextPath+"/viewCibilDispatchAction.do?method=viewCibil";
	document.getElementById("processingImage").style.display = '';
    document.getElementById("cibilVerification").submit();
}
function viewCibilReport()//viewGenerate()
{
	var id=document.getElementsByName("radioId");
	var cRes=document.getElementsByName("cibilResponse");
	var cibilResponse='';
	
	var cibilId="";
	for(var i=0; i< id.length ; i++) 
	{
		if(id[i].checked == true)
		{
			cibilId = id[i].value;	
			cibilResponse=cRes[i].value;	
		}
	}
	/*if(cibilId=='')
	{
		alert("Select at least one record.");
		return false;
	}*/
	if(cibilResponse=='INVALID RESPONSE'||cibilResponse=='NO RESPONSE')
	{
		alert("View is not possible for "+cibilResponse);
		return false;
	}
	else
	{
		var contextPath =document.getElementById('contextPath').value ;
		//document.getElementById("viewCibilDispatchAction").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilReportGenerate&cibilID="+cibilId+"&cibilResponse="+cibilResponse;
		document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilReportGenerate&cibilID="+cibilId+"&cibilResponse="+cibilResponse;
		document.getElementById("cibilVerification").submit();
		//alert("Cibil Report Successfully Downloaded");
	}
	
}
function viewCibilDone(){
	var id=document.getElementsByName("radioId");
	//alert('length : '+id.length);
	var deal=document.getElementsByName("dealId");
	var cust=document.getElementsByName("customerId");
	var cRes1=document.getElementsByName("cibilDone");
	var cbId=document.getElementsByName("lbxCibilId");
	var cibilDone='';
	var cibilId="";
	var cibilResponse="";
	var dealID="";
	var customerId='';
	for(var i=0; i< id.length ; i++) 
	{
	//alert('inside for loop');
		if(id[i].checked == true)
		{
	//	alert('inside for if');
			cibilId = cbId[i].value;
			cibilDone=cRes1[i].value;
			dealID=deal[i].value;
			customerId=cust[i].value;
			//alert("dealID"+dealID + "customerId -->" +customerId );
		cibilDone=document.getElementById('cibilDone'+i).value ;
		}
	}
	
	if(cibilDone=='N' || cibilDone=='')
	{
		alert("BUREAU NOT DONE ");
		return false;
	}
	else
	{

		var contextPath =document.getElementById('contextPath').value ;
		
		document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilReportGenerate&cibilID="+cibilId+"&cibilResponse="+cibilResponse+"&cibilDone="+cibilDone+"&dealID="+dealID+"&customerId="+customerId;
		document.getElementById("cibilVerification").submit();
		
	}
	
}
//parvez ends
function forwordCibilIni()
{
	//alert("Record Successfully forworded to Cibil");
	
	 
	DisButClass.prototype.DisButMethod();
	var dealID=document.getElementById("dealID").value;
	var id=document.getElementsByName("chkCases");
	var customerId ='';
	var contextPath=document.getElementById("contextPath").value;
	for(var i=0; i< id.length ; i++)
		{
	   if(id[i].checked == true)
		   {
		   var fname = document.getElementById("fname"+i).value;
	var dob = document.getElementById("dob"+i).value;
	var gender= document.getElementById("gender"+i).value;
	var state= document.getElementById("state"+i).value;
	var pincode= document.getElementById("pincode"+i).value;
	var pan= document.getElementById("pan"+i).value;
	var passport= document.getElementById("voterid"+i).value;
	var voterid= document.getElementById("passport"+i).value;
	//var rationcard= document.getElementById("rationcard").value;
	var drivinglicence= document.getElementById("drivinglicence"+i).value;
	var uid= document.getElementById("uid"+i).value;
	 if(fname==''){
		  alert("Consumer First Name is Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
	 if(dob==''){
		  alert("Consumer Date of Birth Name is Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
	 if(gender==''){
		  alert("Consumer Gender is Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
	 if(state==''){
		  alert("Consumer State is Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
	 if(pincode==''){
		  alert("Pincode is Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
	 if(pan=="" && voterid=="" && passport=="" && drivinglicence=="" && uid=="" ){
		  alert(" Consumer Identity Missing : Unable to Send Record");
		  DisButClass.prototype.EnbButMethod();
		return false;
		 }
 		  customerId =customerId+","+id[i].value+" ";
		   }
		}
	if(customerId=='')
		{
		if(confirm("Do you want to Continue without Bureau Initiate"))
	{
			customerId=customerId.substring(1);
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilVerificationSave&dealID="+dealID+"&customerId="+customerId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("cibilVerification").submit();
		}
		else
			{
			DisButClass.prototype.EnbButMethod();
			return false;
			}
		}
	else{
		customerId=customerId.substring(1);
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilVerificationSave&dealID="+dealID+"&customerId="+customerId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("cibilVerification").submit();
	}
	/*if(customerId=='')
	{
		alert("Please select atleast one.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	customerId=customerId.substring(1);
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilVerificationSave&dealID="+dealID+"&customerId="+customerId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("cibilVerification").submit();*/
}

/*  cibil report downlaod */
function cibilDownload(id){
	var dealID=document.getElementById("dealID").value;
	var cibilId='lbxCibilId'+id;
	var finalCibilId=document.getElementById(cibilId).value;
	//alert("finalCibilId"+finalCibilId);
	var cibilDone='cibilDone'+id;
	var cibilStatus=document.getElementById(cibilDone).value;
	var id=document.getElementsByName("chkCases");
	var customerId ='';
	count = 0;
		for(var i=0; i< id.length ; i++)
		{
	   if(id[i].checked == true)
		   {
			   customerId =customerId+",'"+id[i].value+"'";
			   count++;
		   }
		}
		if(count==0){
			   alert("Please select any case");
			   return false;
		   }
		 if(count==1){
		customerId=customerId.substring(1);
	
	//alert("cibilStatus"+cibilStatus );
		if(trim(cibilStatus)=='N' || parseFloat(finalCibilId)<=0 || cibilStatus==''){
			alert('Bureau is not initiated yet.');
		return false;
	}else{
		var contextPath =document.getElementById('contextPath').value ;	
		document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilReportGenerate&dealID="+dealID+"&cibilID="+finalCibilId+"&customerId="+customerId;
		document.getElementById("cibilVerification").submit();
	}
		 }
		 else{
			 alert("select correspondence deal");
			 return false;
		 }
}
function generateHistoricalData(){
	var dealID=document.getElementById("dealID").value;
	var LinkId=document.getElementById("LinkId").value;
	//alert("LinkId:"+LinkId);
	if(LinkId==''){
		alert("Please Select Report Name First");
	}
	else{
	var contextPath =document.getElementById('contextPath').value ;	
	document.getElementById("cibilVerification").action = contextPath+"/cibilVerificationDispatchAction.do?method=cibilHstReportGenerate&dealID="+dealID+"&LinkId="+LinkId;
	document.getElementById("cibilVerification").submit();
	}

}

