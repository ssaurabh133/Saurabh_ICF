function saveLeadEntryDetails(formName)
{
	DisButClass.prototype.DisButMethod();
	
	 var applicationFormNo = document.getElementById("applicationFormNo").value;
	 var leadDate = document.getElementById("leadDate").value;
	 var leadTime = document.getElementById('leadTime').value;
	 var branch = document.getElementById('branch').value;
	 var relationshipManager = document.getElementById('relationshipManager').value;
	 var source = document.getElementById('source').value;
	 var sourcedesc = document.getElementById('sourcedesc').value;
	 // var vendorCode = document.getElementById('vendorCode').value;
	 var other = "";
     var lbxLeadNo=document.getElementById('lbxLeadNo').value;
	
	        var leadMValue=document.getElementById('leadMValue').value;
              //alert("leadMValue: "+leadMValue);
				if(document.getElementById('leadNo').value == '' && leadMValue=='Y'){
					other = "* Lead No is required  \n";
					alert(other);
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				//alert("other: "+other);
				var contextPath =document.getElementById('contextPath').value ;
//				if(document.getElementById('source').value=='VENDOR' && document.getElementById('vendorCode').value=='')
//				 {
//					  alert("* Vendor Code is required");
//					  document.getElementById("searchButton").removeAttribute("disabled","true");
//					  DisButClass.prototype.EnbButMethod();
//					  this.disabled=false;
//					  return false;
//				 }else
					 if (applicationFormNo == "" || leadDate == "" || branch == "" || relationshipManager == "" || source == "" || sourcedesc == "" ) {
					var manFields = "applicationFormNo / leadDate / branch / relationshipManager / source / sourcedesc";
					validateForm(formName,manFields,other);
					document.getElementById("searchButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}else
				  {
					     document.getElementById("sourcingForm").action = contextPath+"/leadProcessAction.do?lbxLeadNo="+lbxLeadNo;
					     document.getElementById("processingImage").style.display = '';
					     document.getElementById("sourcingForm").submit();
						 return true;
					  

				    }


			}



function saveForwardLeadEntryDetails(fwdMsg)
{
	//alert("ok In saveForwardLeadEntryDetails");
	DisButClass.prototype.DisButMethod();
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var dealId =document.getElementById('dealId').value ;
	if(dealId!='')
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do";
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("sourcingForm").submit();
	}
	else
	{
		alert("You can't move without saving before Deal Details.");
		DisButClass.prototype.EnbButMethod();
	}
	
}

function checkVendor(){
	var ven=document.getElementById("source").value;
	var leadNo=document.getElementById("leadNo").value;
	if(leadNo!= ""){
		document.getElementById('leadButton1').style.display='none';
		document.getElementById('sourcedesc').setAttribute('readOnly','true');
	}
	if(ven=='VENDOR' && ven != null){
		document.getElementById("vendorCodeButton").style.display = "";
	}
}

function getVendor()
{
	//alert("In getVendor "+document.getElementById("vendorCodeButton"));
	var ven=document.getElementById("source").value;
	if(ven=='VENDOR' && ven != null)
	{
		document.getElementById("sourcedesc").value='';
		document.getElementById("vendorCode").value='';
		document.getElementById("lbxvendorCode").value='';
		document.getElementById("sourcedesc").setAttribute("readOnly", "true");
		document.getElementById("vendorCodeButton").style.display = "";
		document.getElementById("vendorCode").setAttribute("readOnly", "true");
				
	}
	else
	{
		document.getElementById("sourcedesc").value='';
		document.getElementById("vendorCode").value='';
		document.getElementById("lbxvendorCode").value='';
		document.getElementById("sourcedesc").removeAttribute("readOnly", "true");
		document.getElementById("vendorCodeButton").style.display = "none";
		document.getElementById("vendorCode").setAttribute("readOnly", "true");
	}
	
}

function validateLeadTime()
{
//	 alert("ok");
	var timeStr=document.getElementById('leadTime').value;
	
	var timePat = /^(\d{1,2}):(\d{2})?$/;
	if(document.getElementById('leadTime').value!=""){
	if(timeStr.length<5)
	{
		document.getElementById('leadTime').value='0'+timeStr;
	}
	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('leadTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('leadTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('leadTime').value='';
	return false;
	}
	}
}


function viewLeadDetails()
{
	
	var basePath=document.getElementById("contextPath").value;
	var lbxLeadNo=document.getElementById("lbxLeadNo").value;
	
	if(lbxLeadNo=='')
	{
		alert("Please Select Lead No.");
		return false;
	}
	else
	{
	
		 anotherWindows = new Array();
		    curWinAnother = 0;
		
		var url=basePath+"/leadCapturingBehind.do?leadId="+lbxLeadNo+"&FromDealCap=V";
		
		window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

		 return true;
	}
}

//-----------------------------------------VALIDATION--------------------------


function validateForm(formName,manFields,other){
	var ck_numeric = /^[0-9,.]{1,50}$/;
	var ck_alphaNumeric = /^[A-Za-z0-9,\. _]{1,50}$/;
	var ck_mail= /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	
	var errors = [];
	var reqFields = [];
	var matchField = "";
	var elem = document.getElementById(formName).elements;
	for(var i=0; i<= elem.length;i++){
		var str = '';
		if(!elem[i] || elem[i] == "undefined"  || elem[i].value == "undefined" || elem[i].name == (null || '')){
			continue;
		}
		if(manFields.match(elem[i].name)){
			matchField = manFields.match(elem[i].name);
				if(elem[i].value == null || elem[i].value == ""){
					//alert(elem[i].value);
					errors[errors.length]= elem[i].name;
				}
	 
		}else{
			continue;
		}
	}
	if (errors.length > 0) {
		  reportErrors(errors,matchField,other);
		  return false;
	 }
}

function reportErrors(errors,matchField,other){
	 var msg = "";
	 var errorMsg = other;
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += errors[i];
	 }
	  if(msg.match("applicationFormNo")){
		  errorMsg +="* Application form No. is required \n";
	 }
	  if(msg.match("leadDate")){
		  errorMsg +="* Lead Date is required \n";
	 }
	  if(msg.match("relationshipManager")){
		  errorMsg +="* Relationship Manager is required \n";
	 }
	  if(msg.match(/source(?!desc)/gi)){
		  errorMsg +="* Source Type Desc is required \n";
	 }
	  if(msg.match("sourcedesc")){
		  errorMsg +="* Source Description is required";
	 }
	  //alert(errorMsg);
	  //alert(other);
	  if(other != ''){
		  document.getElementById("leadButton").focus();
	  }else if(msg.match("applicationFormNo")){
		  document.getElementById("applicationFormNo").focus();
	 }else if(msg.match("leadDate")){
		 document.getElementById("leadDate").focus();
	 }else if(msg.match("relationshipManager")){
		 document.getElementById("lbxSubIndustryButton").focus();
	 }else if(msg.match(/source(?!desc)/gi)){
		 document.getElementById("source").focus();
	 }else if(msg.match("sourcedesc")){
		 document.getElementById("sourcedesc").focus();
	 }
	 alert(errorMsg);
	
}

function confirmDealDetails()
{
	//alert("In confirmDealDetails");
	DisButClass.prototype.DisButMethod();
	
	var dealId =document.getElementById('dealId').value ;
	if(dealId!='')
	{
		var contextPath =document.getElementById('contextPath').value ;
		location.href = contextPath+"/qualityCheckAction.do?method=confirmForwardDeal";
	}
	else
	{
		alert("You can't move without saving before Deal Details.");
		DisButClass.prototype.EnbButMethod();
	}
}

function showHideDescLovOnDeal()
{
	var source=document.getElementById("source").value;
	if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING" || source == "TELECALLER")
	{
		document.getElementById("srcLOV").style.display="";
		document.getElementById("sourcedesc").readOnly = true;
	}
	else
	{
		document.getElementById("srcLOV").style.display="none";
		document.getElementById("sourcedesc").readOnly = false;
	}
}

function showHideDescLovOnChange()
{
	var source=document.getElementById("source").value;
	document.getElementById("sourcedesc").value='';
	if(source == "CONNECTOR" || source == "DEALER" || source == "DSA" || source == "EXISTING" || source == "TELECALLER")
	{
		document.getElementById("srcLOV").style.display="";
		document.getElementById("sourcedesc").readOnly = true;
	}
	else
	{
		document.getElementById("srcLOV").style.display="none";
		document.getElementById("sourcedesc").readOnly = false;
	}
}

function openDescLovOnDeal()
{
	var source=document.getElementById("source").value;
	if(source == "DEALER")
	{
		openLOVCommon(531,'leadCapturingDetails','sourcedesc','','', '','','','lbxDescription');
	}
	else if(source == "CONNECTOR")
	{
		openLOVCommon(532,'leadCapturingDetails','sourcedesc','','', '','','','lbxDescription');
	}
	else if(source == "DSA")
	{
		openLOVCommon(533,'leadCapturingDetails','sourcedesc','','', '','','','lbxDescription');
	}
	else if(source == "EXISTING")
	{
		openLOVCommon(534,'leadCapturingDetails','lbxDescription','','', '','','','sourcedesc');
	}
	else if(source == "TELECALLER")
	{
		openLOVCommon(570,'leadCapturingDetails','sourcedesc','','', '','','','lbxDescription');
	}
}

function genrateByDealOrLead()
{
	var lbxLeadNo =document.getElementById('lbxLeadNo').value ;
	if(lbxLeadNo!=null && lbxLeadNo!='')
	{
		document.getElementById("leadGeneratorByDrop").setAttribute("disabled",true);
		document.getElementById("leadGeneratorByDrop").style.display='none';
		
		document.getElementById("leadGeneratorByBox").removeAttribute("disabled",true);
		document.getElementById("leadGeneratorByDesc").removeAttribute("disabled",true);			
		document.getElementById("leadGeneratorByBox").style.display='';
		document.getElementById("leadGeneratorByDesc").style.display='';	
	}
	else
	{
		document.getElementById("leadGeneratorByDrop").removeAttribute("disabled",true);
		document.getElementById("leadGeneratorByDrop").style.display='';
		
		document.getElementById("leadGeneratorByBox").setAttribute("disabled",true);
		document.getElementById("leadGeneratorByDesc").setAttribute("disabled",true);			
		document.getElementById("leadGeneratorByBox").style.display='none';
		document.getElementById("leadGeneratorByDesc").style.display='none';
	}
}
