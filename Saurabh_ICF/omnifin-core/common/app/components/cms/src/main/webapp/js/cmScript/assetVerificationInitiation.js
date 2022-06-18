function modifyAssetVerification()  {
	
	DisButClass.prototype.DisButMethod();
	if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
   		
		alert("*Internal Appraiser is required");
		document.getElementById("externalAppraiser").value="";
		document.getElementById("assetButton").removeAttribute("disabled",true);
		
		document.getElementById("internalButton").removeAttribute("disabled",true);
		document.getElementById("externalButton").setAttribute("disabled",true);
		DisButClass.prototype.EnbButMethod();
		return false;	
   	}
	else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
			   		
				alert("*External Appraiser is required");
				document.getElementById("internalAppraiser").value="";
				document.getElementById("assetButton").removeAttribute("disabled",true);
				document.getElementById("externalButton").removeAttribute("disabled",true);
				document.getElementById("internalButton").setAttribute("disabled",true);
				DisButClass.prototype.EnbButMethod();
				return false;	
		   	}
	if((document.getElementById("appraiser").value)=="Internal"){
		var appraisertype='I';
	}else{
		var appraisertype='EA';
	}
	
	   var loanNo=document.getElementById("loanNo").value;
	if(document.getElementById("internalAppraiser").value!=""){
		Appraiserid=document.getElementById("internalAppraiser").value;
	}else{
		Appraiserid=document.getElementById("externalAppraiser").value
	}
	if(document.getElementById("lbxUserId").value!=""){
		Appraiser=document.getElementById("lbxUserId").value;
	}else{
		Appraiser=document.getElementById("lbxextApprHID").value
	}
	
	   	var contextPath=document.getElementById("contextPath").value;
	   	var flag=0;
	   	
	   	 var ch=document.getElementsByName('chk');
	   	 
	   	 var loanAccNo1= document.getElementsByName('loanAccNo1');
	   	 var assetID1 = document.getElementsByName('assetID1');
	   	 var assetDescription1=document.getElementsByName('assetDescription1');	
	   	 
	    var loanid=document.getElementById('lbxLoanNo').value;
	    
	   	var loanIDList="";
	   	var loanAccNoList="";
	   	var assetIDList="";
	   	var assetDescriptionList ="";
	   	
	   	
	    for(i=0;i<ch.length;i++){
	   			 
	   			 if(ch[i].checked==true){
	   				 
	   				assetIDList = assetIDList + assetID1[i].value + "/";
	   			     
	   				flag=1;
	    		
	    			}
	   				 
	   		 }
	 
	   	 if(flag==0)
	   	 {
	   		 alert("Please select One record");
	   		 //document.getElementById("assetButton").removeAttribute("disabled",true);
	   		 DisButClass.prototype.EnbButMethod();
	   		 return false;
	   	 }
	   	var confrm = confirm("Do you want to continue ?");
	   	 if(confrm){
	   	 
	   		document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=modifyAssetVerification&loanId="+loanid+"&assetIDList="+assetIDList+"&appraisertype="+appraisertype+"&Appraiserid="+Appraiserid+"&Appraiser="+Appraiser+"&loanNo="+loanNo;
	   		document.getElementById("processingImage").style.display = '';
	   		document.getElementById("sourcingForm").submit();
	   		//document.getElementById("assetButton").setAttribute("disabled",true);
	   		return true;
   	     
	   	 }else{
	   		//document.getElementById("assetButton").removeAttribute("disabled",true);
	   		DisButClass.prototype.EnbButMethod();
	   		 return false;
	   	 }
	   
	   }
function modifyforwardAssetVerification(a,b,c,d)  {
	DisButClass.prototype.DisButMethod();
	var flag=0;
	var appraiserid="";
	var extappr=document.getElementById("externalAppraiser").value;
	var internalappr=document.getElementById("internalAppraiser").value;	
if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
	
	alert("*Internal Appraiser is required");
	//document.getElementById("assetForwardButton").removeAttribute("disabled",true);
	DisButClass.prototype.EnbButMethod();
	return false;	
}
else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
		   		
			alert("*External Appraiser is required");
			//document.getElementById("assetForwardButton").removeAttribute("disabled",true);
			DisButClass.prototype.EnbButMethod();
			return false;	
	   	}
	if(c=="I"||c=="Internal"){
	var appraiser="I";
	appr=internalappr;
	appraiserid=d;
}else{
	var appraiser="EA";
	appraiserid=b;
	appr=extappr;
}


	var contextPath=document.getElementById("contextPath").value;
	
	
	 var ch=document.getElementsByName('chk');
	 

	 var assetID1 = document.getElementsByName('assetID1');
	 var assetIDList="";
	 for(i=0;i<ch.length;i++){
			   			 
		 if(ch[i].checked==true){
			
			 assetIDList = assetIDList + assetID1[i].value + "/";
			
			flag=1;
		 }
	 }
	 if(flag==0)
	 {
		 alert("Please select One record");
		 //document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }


	if(document.getElementById("statusflag").value=='forward'){
	
		
		 document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=modifyForwardAssetVerification&loanId="+a+"&appraiserid="+appraiserid+"&appraiser="+appraiser+"&assetIDList="+assetIDList+"&appr="+appr;
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("sourcingForm").submit();
		// document.getElementById("assetForwardButton").setAttribute("disabled",true);
		 return true;
	}else if(document.getElementById("statusflag").value!='forward'){
		 alert("Please save the data first");
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
	 else{
		 //document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
}


function deleteassetverificationid(){
	DisButClass.prototype.DisButMethod();
	var basePath=document.getElementById("contextPath").value;
 	var ch=document.getElementsByName('chk');
	var assetID1 = document.getElementsByName('assetID1');
	 
	 if(document.getElementById('loanidval').value==""){
		 var loanid=document.getElementById('lbxLoanNo').value;
	 }else{
		 var loanid=document.getElementById('loanidval').value;
	 }
	
var flag=0;
	var loanIDList="";
	var loanAccNoList="";
	var assetIDList="";
	var assetDescriptionList ="";
	var loanNolist="";
if((document.getElementById("appraiser").value)=="Internal"){
	var appraisertype='I';
}else{
	var appraisertype='EA';
}

var contextPath=document.getElementById("contextPath").value;
if(document.getElementById("lbxUserId").value!=""){
	appraiserid=document.getElementById("lbxUserId").value;
}else{
	appraiserid=document.getElementById("lbxextApprHID").value
}
for(i=0;i<ch.length;i++){
			 
			 if(ch[i].checked==true){
				
				assetIDList = assetIDList + assetID1[i].value + "/";
				flag=1;
		}
				 
		 }

	 if(flag==0)
	 {
		 alert("Please select One record");
		 //document.getElementById("deleteButton").removeAttribute("disabled",true);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
	var confrm = confirm("Are you sure,You want to delete this assets for this Appraiser.Do you want to continue?");
	 if(confrm){
	 
		document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInitSearch.do?method=deleteasset&loanid="+loanid+"&assetIDList="+assetIDList+"&appraiserid="+appraiserid+"&apprtype="+appraisertype;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("sourcingForm").submit();
		//document.getElementById("deleteButton").setAttribute("disabled",true);
	    return true;
     
	 }else{
		//document.getElementById("deleteButton").removeAttribute("disabled",true);
		DisButClass.prototype.EnbButMethod();
		return false;
	 }
}
function disableIntExt()
{
//
//	if((document.getElementById("appType").value)==""){
//  		
//		alert("Please select Type.");
//		return false;
//  		
//  	}
//
//	if((document.getElementById("appraiser").value)==""){
//  		
//		alert("Please select Appraiser");
//		return false;
//  		
//  	}

	 	
	if((document.getElementById("appraiser").value)=="I"){
	//alert(document.getElementById("lovImg"));
		document.getElementById("internalAppraiser").disabled=false;
		document.getElementById("externalAppraiser").disabled=true;
		document.getElementById("lbxextApprHID").disabled=true;
		document.getElementById("externalAppraiser").value="";
		document.getElementById("lbxextApprHID").value="";
		document.getElementById("externalButton").disabled=true;	
		document.getElementById("internalButton").disabled=false;
		return true;
		
	}else{

		document.getElementById("internalAppraiser").disabled=true;
		document.getElementById("lbxextApprHID").disabled=false;
		document.getElementById("lbxUserId").value="";
		document.getElementById("internalAppraiser").value="";
		document.getElementById("externalAppraiser").disabled=false;
		document.getElementById("internalButton").disabled=true;
		document.getElementById("externalButton").style.display='';
		document.getElementById("externalButton").disabled=false;
	 //alert();
		return true;
	}
}

function saveAssetVerification()  {

    DisButClass.prototype.DisButMethod();
	if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
   		
		alert("*Internal Appraiser is required");

		//document.getElementById("assetButton").removeAttribute("disabled",true);
		
		DisButClass.prototype.EnbButMethod();
		return false;	
   	}
	else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
			   		
				alert("*External Appraiser is required");
				
				//document.getElementById("assetButton").removeAttribute("disabled",true);
				DisButClass.prototype.EnbButMethod();
				return false;	
		   	}
	   
	   	var contextPath=document.getElementById("contextPath").value;
	   	var flag=0;
	   	
	   	 var ch=document.getElementsByName('chk');
	   	 
	   	 var loanNo= document.getElementById('loanNo').value;
	   	 var assetID1 = document.getElementsByName('assetID1');
	   	 var assetDescription1=document.getElementsByName('assetDescription1');	
	   	 
	    var loanid=document.getElementById('loanidval');
	     
	   	var loanIDList="";
	   	var loanAccNoList="";
	   	var assetIDList="";
	   	var assetDescriptionList ="";
	   	var loanNolist="";
	   	
	    for(i=0;i<ch.length;i++){
	   			 
	   			 if(ch[i].checked==true){
	   				 
	   				loanIDList=loanIDList + loanid.value + "/";
	   			
	   				assetIDList = assetIDList + assetID1[i].value + "/";
	   			    assetDescriptionList = assetDescriptionList + assetDescription1[i].value + "/";
					  flag=1;
	    		
	    			}
	   				 
	   		 }
	 
	   	 if(flag==0)
	   	 {
	   		 alert("Please select One record");
	   		 //document.getElementById("assetButton").removeAttribute("disabled",true);
	   		 DisButClass.prototype.EnbButMethod();
	   		 return false;
	   	 }
	   	var confrm = confirm("Do you want to continue ?");
	   	 if(confrm){
	   	 
	   	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=saveAssetVerification&loanIDList="+loanIDList+"&assetIDList="+assetIDList+"&assetDescriptionList="+assetDescriptionList+"&loanNo="+loanNo;
	   	document.getElementById("processingImage").style.display = '';
	   	document.getElementById("sourcingForm").submit();
	  document.getElementById("assetButton").setAttribute("disabled",true);
	return true;
  
	   	 }else{
	   		 DisButClass.prototype.EnbButMethod();
	   		//document.getElementById("assetButton").removeAttribute("disabled",true);
	   		 return false;
	   	 }
	   
	   }


function forwardAssetVerification()  {


if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
		alert("*Internal Appraiser is required");
		document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		return false;	
		}
		else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){ 		
			alert("*External Appraiser is required");
			document.getElementById("assetForwardButton").removeAttribute("disabled",true);
			return false;	
	   	}
		else if(document.getElementById("recStatus").value=="")
		{
			alert("You can't move without saving before Asset Verification Initiation.");
			document.getElementById("assetForwardButton").removeAttribute("disabled",true);
			return false;	
		}

	var contextPath=document.getElementById("contextPath").value;
	var flag=0;
	
	 var ch=document.getElementsByName('chk');
	 
	 var loanAccNo1= document.getElementsByName('loanAccNo1');
	 var assetID1 = document.getElementsByName('assetID1');
	 var assetDescription1=document.getElementsByName('assetDescription1');	
	 
 
  
	var loanIDList="";
	var loanAccNoList="";
	var assetIDList="";
	var assetDescriptionList ="";
	
	
 for(i=0;i<ch.length;i++){
			 
			 if(ch[i].checked==true){
				 
				loanIDList=loanIDList + ch[i].value + "/";
  
	

				assetIDList = assetIDList + assetID1[i].value + "/";

				assetDescriptionList = assetDescriptionList + assetDescription1[i].value + "/";
				 
			 
 			  flag=1;
 		
 			}
				 
		 }

	 if(flag==0)
	 {
		 alert("Please select One record");
		 document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 return false;
	 }
	var confrm = confirm("Do you want to continue ?");
	 if(confrm){
	 
	document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=forwardAssetVerification&loanIDList="+loanIDList+"&assetIDList="+assetIDList+"&assetDescriptionList="+assetDescriptionList;
	document.getElementById("sourcingForm").submit();
	document.getElementById("assetForwardButton").setAttribute("disabled",true);
     return true;
     
	 }else{
		document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 return false;
	 }

}


/*function modifyforwardAssetVerification(a,b,c,d)  {
	 DisButClass.prototype.DisButMethod();
	 var flag=0;
     var extappr=document.getElementById("externalAppraiser").value;
	 var internalappr=document.getElementById("internalAppraiser").value;	
if((document.getElementById("appraiser").value)=="I" && (document.getElementById("internalAppraiser").value=="")){
	
	alert("*Internal Appraiser is required");
	DisButClass.prototype.EnbButMethod();
	//document.getElementById("assetForwardButton").removeAttribute("disabled",true);
	return false;	
}
else if((document.getElementById("appraiser").value)=="EA" && (document.getElementById("externalAppraiser").value=="")){
		   		
			alert("*External Appraiser is required");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("assetForwardButton").removeAttribute("disabled",true);
			return false;	
	   	}
	if((c=="Internal")||(c=="I")){
	var appraiser="I";
	appr=internalappr;
	var appraiserid=d;

}else{
	var appraiser="EA";
	var appraiserid=b;
	appr=extappr;
	
}


	var contextPath=document.getElementById("contextPath").value;
	
	
	 var ch=document.getElementsByName('chk');
	 

	 var assetID1 = document.getElementsByName('assetID1');
	 var assetIDList="";
	 for(i=0;i<ch.length;i++){
			   			 
		 if(ch[i].checked==true){
			
			 assetIDList = assetIDList + assetID1[i].value + "/";
			
			flag=1;
		 }
	 }
	 if(flag==0)
	 {
		 alert("Please select One record");
		 DisButClass.prototype.EnbButMethod();
		 //document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 return false;
	 }
	var confrm = confirm("Do you want to continue ?");
	 if(confrm){
		
		document.getElementById("sourcingForm").action = contextPath+"/assetVerificationInit.do?method=modifyForwardAssetVerification&loanId="+a+"&appraiserid="+appraiserid+"&appraiser="+appraiser+"&assetIDList="+assetIDList+"&appr="+appr;
	    document.getElementById("processingImage").style.display = '';
		document.getElementById("sourcingForm").submit();
		document.getElementById("assetForwardButton").setAttribute("disabled",true);
    	return true;

	 }else{
		 //document.getElementById("assetForwardButton").removeAttribute("disabled",true);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }

}*/
function assetVerificationInitSearch()
{
	DisButClass.prototype.DisButMethod();
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	var contextPath=document.getElementById("contextPath").value;
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	
	if(reportingToUserId!=''||loanNo!=''||customerName!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			//document.getElementById("save").removeAttribute("disabled", true);
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		//document.getElementById("save").removeAttribute("disabled", true);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function assetVerificationInitiation()
{
	
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	var contextPath=document.getElementById("contextPath").value;
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	
	if(reportingToUserId!=''||loanNo!=''||customerName!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInitSearch.do?method=assetVerificationSearch";
			document.getElementById("sourcingForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("save").removeAttribute("disabled", true);
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("save").removeAttribute("disabled", true);
		return false;
	}
}
function newAssetVerificationInit()
{
DisButClass.prototype.DisButMethod();	
var contextPath=document.getElementById("contextPath").value;
document.getElementById("sourcingForm").action =contextPath+"/assetVerificationInit.do?method=assetVerificationNew";
document.getElementById("processingImage").style.display = '';
document.getElementById("sourcingForm").submit();
return true;
}
function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchkd").checked;
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
