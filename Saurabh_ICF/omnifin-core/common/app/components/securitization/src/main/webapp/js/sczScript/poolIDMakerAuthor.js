	   function searchForPoolIdAuthor(){
			
		   DisButClass.prototype.DisButMethod();
		if((document.getElementById("poolID").value =='') && (document.getElementById("poolName").value =='') && (document.getElementById("poolCreationDate").value=='') && (document.getElementById("cutOffDate").value=='') && (document.getElementById("instituteID").value =='')){
		     
		   	 alert("Please select atleast one criteria");
		   	DisButClass.prototype.EnbButMethod();
//		   	document.getElementById("search").removeAttribute("disabled");
		   	return false;
		   }else{
			   	var contextPath=document.getElementById("contextPath").value;
			   	document.getElementById("sourcingForm").action = contextPath+"/poolIdAuthorProcessAction.do?method=searchPoolIdAuthor";
			   	document.getElementById("processingImage").style.display = '';
			   	document.getElementById("sourcingForm").submit();
			   	     return true;
		   }
			   }
	   
	   function errorLogPoolIdMaker(){
		   var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("sourcingForm").action=sourcepath+"/poolCreationProcessAction.do?method=errorLogPoolIdMaker";
		 	document.getElementById("sourcingForm").submit();
		 	return true;
		}
	   
	   
	   function searchForPoolIdMaker(){
		   
		   DisButClass.prototype.DisButMethod();
		   if((document.getElementById("poolID").value =='') && (document.getElementById("poolName").value =='') && (document.getElementById("poolCreationDate").value=='') && (document.getElementById("cutOffDate").value=='') && (document.getElementById("instituteID").value =='') && (document.getElementById("userName").value =='')){
			     
			   	 alert("Please select atleast one criteria");
			   	DisButClass.prototype.EnbButMethod();
//			   	document.getElementById("search").removeAttribute("disabled");
			   	return false;
			   }else {
		   	var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=searchPoolIdMaker";
		   	document.getElementById("processingImage").style.display = '';
		   	document.getElementById("sourcingForm").submit();
		   	     return true;
			   }
		   }
	   
	   
	   function newPoolIdMaker(){
		   	
		   	var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=newPoolIdMaker";
		   	document.getElementById("sourcingForm").submit();
		    return true;
		   }
	      function validateTab2(){
	    	  var instituteID = document.getElementById("instituteID").value;
			   	var investmentRatio = document.getElementById("investmentRatio").value;
			   	var interestRate = document.getElementById("interestRate").value;
			   	var distributionPriority = document.getElementById("distributionPriority").value;
			   	var msg="";
			   	if(instituteID==''){
			   		msg=msg+"Institute Id is Required \n";
			   	}
			   	if(investmentRatio==''){
			   		msg=msg+"Investment Ratio is Required \n";
			   	}
			   	if(interestRate==''){
			   		msg=msg+"Interest Rate is Required \n";
			   	}
			   	if(distributionPriority==''){
			   		msg=msg+"Distribution Priority is Required \n";
			   	}
			   	if(msg.length!=''){
			   		alert(msg);
			   		return false;
			   	}
			   	return true;
	      }
	   function saveTab2(){
		   	var contextPath=document.getElementById("contextPath").value;
		  if(validateTab2())
		 	{
		   	document.getElementById("sourcingForm").action = contextPath+"/Tab2ProcessAction.do?method=saveTab2Data";
		   	document.getElementById("sourcingForm").submit();
		   	return true;
		 	}
		   	return false;
	   }
	   
	   function updateTab2(){
		   var contextPath=document.getElementById("contextPath").value;
			if(validateTab2())
		 	{ 	
				document.getElementById("sourcingForm").action = contextPath+"/Tab2ProcessAction.do?method=updateTab2Data";
				document.getElementById("sourcingForm").submit();
				return true;
		 	}
			return false;
	   }
	   
	   function deleteTab2(){
		   var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/Tab2ProcessAction.do?method=deleteTab2Data";
		   	document.getElementById("sourcingForm").submit();
		   	return true;
	   }
	   
	   function saveTab1(){
		   	var contextPath=document.getElementById("contextPath").value;
		   	if(validate())
		 	{
		   		document.getElementById("sourcingForm").action = contextPath+"/Tab1ProcessAction.do?method=saveTab1Data";
			   	document.getElementById("sourcingForm").submit();
			   	return true;
			}
		   	return false;
	   }
	   
	   function validate(){
		    var creditEnhanceType = document.getElementById("creditEnhanceType").value;
			var creditEnhanceDocRefNo = document.getElementById("creditEnhanceDocRefNo").value;
			var creditEnhanceAmount = document.getElementById("creditEnhanceAmount").value;
		   	var msg="";
		   	if(creditEnhanceType==''){
		   		msg=msg+"Credit Enhancement Type is Required \n";
		   	}
		   	if(creditEnhanceDocRefNo==''){
		   		msg=msg+"Credit Enhancement DocRefNo is Required \n";
		   	}
		   	if(creditEnhanceAmount==''){
		   		msg=msg+"Credit Enhancement Amount is Required \n";
		   	}
		   	if(msg.length!=''){
		   		alert(msg);
		   		return false;
		   	}
		   	return true;
	   }
	   
	   function updateTab1(){
		   	var contextPath=document.getElementById("contextPath").value;
		   	if(validate())
		 	{
		   		document.getElementById("sourcingForm").action = contextPath+"/Tab1ProcessAction.do?method=updateTab1Data";
			   	document.getElementById("sourcingForm").submit();
			   	return true;
			}
		   	return false;
	   }
	   
	   function deleteTab1(){
		    var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/Tab1ProcessAction.do?method=deleteTab1Data";
		   	document.getElementById("sourcingForm").submit();
		   	return true;
	   }
	   
	   function forwardPoolIdMaker(){
		   
		   DisButClass.prototype.DisButMethod();
		    var contextPath=document.getElementById("contextPath").value;
		   	var poolID = document.getElementById("poolID").value;		
		   	document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=forwardPoolIdMaker&poolID="+poolID;
		   	document.getElementById("processingImage").style.display = '';
		   	document.getElementById("sourcingForm").submit();
		   	     return true;
		   	
		   }
	   function errorLogPoolIdMaker(){
		   
		   var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("sourcingForm").action=sourcepath+"/poolCreationProcessAction.do?method=errorLogPoolIdMaker";
		 	document.getElementById("sourcingForm").submit();
		 	return true;
		 		
		   
	   }
	   function forwardOnNew(){
		   DisButClass.prototype.DisButMethod();
    	   alert("Please save the data first");
    	   DisButClass.prototype.EnbButMethod();
    	   return false;
       }  
	   
	   function allChecked()
	   {
	   		// alert("ok");
	   	var c = document.getElementById("allchkd").checked;
	   	var ch=document.getElementsByName('checkId');
	   	

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
	   
	   function downloadBankUploadFormat(){
		  
			 var contextPath=document.getElementById("contextPath").value;
			 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=downloadBankUploadFormat";
			 document.getElementById("sourcingForm").submit();
			 return true;
		}
	   
	   function deletePoolID(){		
			var flag = confirm('Are you sure to delete this pool id permanently');
			if(flag){
			 var contextPath=document.getElementById("contextPath").value;
			 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=deletePoolID";
			 document.getElementById("sourcingForm").submit();
			 return true;
			}
			return false;
		}
	   
	   function addPoolID()
	   {  
		  var contextPath=document.getElementById("contextPath").value; 
	      var poolID= document.getElementById("poolID").value; 
	      if(!window.child || window.child.close){
	       window.child = window.open(""+contextPath+"/poolIdMakerProcessAction.do?method=openWindowForaddPoolID&poolID="+poolID+"",'PoolPopUP',"height=500,width=950,status=yes,toolbar=no,menubar=no,location=center");
	   }
	   }
	   
		function onSaveOfPoolIdAuthor(alert1){
			DisButClass.prototype.DisButMethod();
		 	var poolID = document.getElementById("poolID").value;
			//var LoanNoHID = document.getElementById("lbxLoanNoHID").value;
			if((document.getElementById("comments").value=="") || (document.getElementById("decision").value=="" ))
			   {
				alert(alert1);
				DisButClass.prototype.EnbButMethod();
//				 document.getElementById("save").removeAttribute("disabled");
				return false;
			   }
			else{	
			var basePath=document.getElementById("contextPath").value;
			document.getElementById("poolAuthorForm").action = basePath+"/poolIdAuthorProcessAction.do?method=onSavePoolIdAuthor&poolID="+poolID;
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("poolAuthorForm").submit();
			 return true;		 
			
			}
			   } 
		
		
function validateDocUpload()
{
	if(document.getElementById('docFile').value=="")
	{
		alert("Choose file to be uploaded.");
		document.getElementById("docFile").focus(); 
		DisButClass.prototype.EnbButMethod();
	    return false; 
	}
	var fup = document.getElementById('docFile');
	var file_Name = fup.value;
	var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
	if(ext == "csv" || ext == "CSV")
	{
		return true;
	} 
	else
	{
		alert("Upload CSV file only.");
		fup.focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}	

function bankUploadSave(){
	if(validateDocUpload())
 	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerUploadAction.do?method=bankUploadSave";
	   	document.getElementById("sourcingForm").submit();
	    return true;
 	}
}

function repaymentScheduleUpload(){
	if(validateDocUpload())
 	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerUploadAction.do?method=repaymentScheduleUploadSave";
	   	document.getElementById("sourcingForm").submit();
	    return true;
 	}
}

function poolUpload(){
	if(validateDocUpload())
 	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerUploadAction.do?method=poolUploadSave";
	   	document.getElementById("sourcingForm").submit();
	    return true;
 	}
}

function downloadPoolIdData(){
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("sourcingForm").action=sourcepath+"/DownloadPoolDataProcessAction.do?method=downloadPoolUploads";
	document.getElementById("sourcingForm").submit();
	return true;
	
}

function savePoolIdData(){
	//DisButClass.prototype.DisButMethod();
	var poolID=document.getElementById("poolID").value;
	var poolName=document.getElementById("poolName").value;
	var poolCreationDate=document.getElementById("poolCreationDate1").value;
	var cutOffDate=document.getElementById("cutOffDate").value;
	var poolType=document.getElementById("poolType").value;
	var instituteID=document.getElementById("instituteID").value;
	var assignType=document.getElementById("assignType").value;
	var assignDate=document.getElementById("assignDate").value;
	var dealAmount=document.getElementById("dealAmount").value;
	var formatD=document.getElementById("formatD").value;
	var interestRate=document.getElementById("interestRate").value;
	var msg="";
	if(poolID==''||poolName==''||poolCreationDate==''||cutOffDate==''||poolType==''||instituteID==''||assignType==''||assignDate==''||dealAmount==''||interestRate=='')
	{
		if(poolName=='')
			msg=msg+"*Pool Name is required.\n";
		if(poolCreationDate=='')
			msg=msg+"*Pool Creation Date is required.\n";
		if(cutOffDate=='')
			msg=msg+"*Cut Off Date is required.\n";
		if(poolType=='')
			msg=msg+"*Pool Type is required.\n";
		if(instituteID=='')
			msg=msg+"*InstituteID is required.\n";
		if(assignType=='')
			msg=msg+"*Assign Type is required.\n";
		if(assignDate=='')
			msg=msg+"*Assign Date is required.\n";
		if(dealAmount=='')
			msg=msg+"*Deal Amount is required.\n";
		if(interestRate=='')
			msg=msg+"*Interest Rate is required.\n";
		
		alert(msg); 
		
		if(msg.match("Pool Name"))
			document.getElementById("poolName").focus();
		else if(msg.match("Pool Creation Date"))
			document.getElementById("poolCreationDate").focus();
		else if(msg.match("Cut Off Date"))
			document.getElementById("cutOffDate").focus();
		else if(msg.match("Pool Type"))
			document.getElementById("poolType").focus();
		else if(msg.match("InstituteID"))
			document.getElementById("loanNoButton").focus();
		else if(msg.match("Assign Type"))
			document.getElementById("assignType").focus();
		else if(msg.match("Assign Date"))
			document.getElementById("assignDate").focus();
		else if(msg.match("Deal Amount"))
			document.getElementById("dealAmount").focus();
		else if(msg.match("Interest Rate"))
			document.getElementById("interestRate").focus();
		
		//DisButClass.prototype.EnbButMethod();
		return false;
	}else{

		var dt1=getDateObject(poolCreationDate,formatD.substring(2,3));
		var dt2=getDateObject(cutOffDate,formatD.substring(2,3));
		var dt3=getDateObject(assignDate,formatD.substring(2,3));
		//alert(dt1+"------"+dt2);
		if(dt1<dt2)
		{
	 		alert("Cut Off Date can't be Greater than Pool Creation Date.");
		 	//DisButClass.prototype.EnbButMethod();
		 	return false;
		}
		if(dt1>dt3)
		{
	 		alert("Assignment date can't be less than Pool Creation date.");
		 	//DisButClass.prototype.EnbButMethod();
		 	return false;
		}
		if(parseFloat(dealAmount)<= 0 || parseFloat(interestRate)<= 0)
		{
		if( parseFloat(dealAmount)<= 0)
			msg=msg+"*Deal Amount can't be equal to 0.\n";
		if(parseFloat(interestRate)<= 0)
			msg=msg+"*Interest Rate can't be equal to 0.\n";
			
		alert(msg);
		//DisButClass.prototype.EnbButMethod();	
			return false;
		}
		if(checkCutOffDate(cutOffDate)){
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("sourcingForm").action=sourcepath+"/poolIdMakerProcessAction.do?method=savePoolIdData";
		document.getElementById("sourcingForm").submit();
		return true;
	}
	
		/*var dt1=getDateObject(poolCreationDate,formatD.substring(2,3));
		var dt2=getDateObject(cutOffDate,formatD.substring(2,3));
		//alert(dt1+"------"+dt2);
		if(dt1<dt2)
		{
	 		alert("Cut Off Date can't be Greater than Pool Creation Date.");
		 	//DisButClass.prototype.EnbButMethod();
		 	return false;
		}
		if(checkCutOffDate(cutOffDate)){
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("sourcingForm").action=sourcepath+"/poolIdMakerProcessAction.do?method=savePoolIdData";
			document.getElementById("sourcingForm").submit();
			return true;
		}*/
	}
}

function checkCutOffDate(date){
	var dd=parseInt(date.substring(0, 2));
	var mm=parseInt(date.substring(3, 5));
	var year=parseInt(date.substring(6, 10));
	
	if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))){
		if(mm==2 && dd==29){
			return true;
		}else if((mm==1 || mm==3 || mm==5 || mm==7 || mm==8 || mm==10 || mm==12) && (dd==31) ){
			return true;
		}else if((mm==4 || mm==6 || mm==9|| mm==11) && (dd==30)){
			return true;
		}else{
			alert('CutOff Date is the Last Date of Month');
			return false;
		}
	}else{
		if(mm==2 && dd==28){
			return true;
		}else if((mm==1 || mm==3 || mm==5 || mm==7 || mm==8 || mm==10 || mm==12) && (dd==31) ){
			return true;
		}else if((mm==4 || mm==6 || mm==9|| mm==11) && (dd==30)){
			return true;
		}else{
			alert('CutOff Date is the Last Date of Month');
			return false;
		}
	}
	return false;
}

function updatePoolIdData(){
	DisButClass.prototype.DisButMethod();
	var poolID=document.getElementById("poolID").value;
	var poolName=document.getElementById("poolName").value;
	var poolCreationDate=document.getElementById("poolCreationDate1").value;
	var cutOffDate=document.getElementById("cutOffDate").value;
	var poolType=document.getElementById("poolType").value;
	var instituteID=document.getElementById("instituteID").value;
	var assignType=document.getElementById("assignType").value;
	var assignDate=document.getElementById("assignDate").value;
	var dealAmount=document.getElementById("dealAmount").value;
	var formatD=document.getElementById("formatD").value;
	var interestRate=document.getElementById("interestRate").value;
	var msg="";
	if(poolID==''||poolName==''||poolCreationDate==''||cutOffDate==''||poolType==''||instituteID==''||assignType==''||assignDate==''||dealAmount==''||interestRate=='')
	{
		if(poolName=='')
			msg=msg+"*Pool Name is required.\n";
		if(poolCreationDate=='')
			msg=msg+"*Pool Creation Date is required.\n";
		if(cutOffDate=='')
			msg=msg+"*Cut Off Date is required.\n";
		if(poolType=='')
			msg=msg+"*Pool Type is required.\n";
		if(instituteID=='')
			msg=msg+"*InstituteID is required.\n";
		if(assignType=='')
			msg=msg+"*Assign Type is required.\n";
		if(assignDate=='')
			msg=msg+"*Assign Date is required.\n";
		if(dealAmount=='')
			msg=msg+"*Deal Amount is required.\n";
		if(interestRate=='')
			msg=msg+"*Interest Rate is required.\n";
		
		alert(msg); 
		
		if(msg.match("Pool Name"))
			document.getElementById("poolName").focus();
		else if(msg.match("Pool Creation Date"))
			document.getElementById("poolCreationDate").focus();
		else if(msg.match("Cut Off Date"))
			document.getElementById("cutOffDate").focus();
		else if(msg.match("Pool Type"))
			document.getElementById("poolType").focus();
		else if(msg.match("InstituteID"))
			document.getElementById("loanNoButton").focus();
		else if(msg.match("Assign Type"))
			document.getElementById("assignType").focus();
		else if(msg.match("Assign Date"))
			document.getElementById("assignDate").focus();
		else if(msg.match("Deal Amount"))
			document.getElementById("dealAmount").focus();
		else if(msg.match("Interest Rate"))
			document.getElementById("interestRate").focus();
		
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		var dt1=getDateObject(poolCreationDate,formatD.substring(2,3));
		var dt2=getDateObject(cutOffDate,formatD.substring(2,3));
		//alert(dt1+"------"+dt2);
		if(dt1<dt2)
		{
	 		alert("Cut Off Date can't be Greater than Pool Creation Date.");
		 	DisButClass.prototype.EnbButMethod();
		 	return false;
		}
		if(checkCutOffDate(cutOffDate)){
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("sourcingForm").action=sourcepath+"/poolIdMakerProcessAction.do?method=updatePoolIdData";
			document.getElementById("sourcingForm").submit();
			return true;
		}
	}
}

function submitPoolIdUpload()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var formatD=document.getElementById("formatD").value;	
	var poolCreationDate = document.getElementById("poolCreationDate").value;	
	var cutOffDate = document.getElementById("cutOffDate").value;
	var businessdate = document.getElementById("businessdate").value;	
	var currDate = document.getElementById("businessDate").value;
	var poolName=document.getElementById("poolName").value;
	var poolType=document.getElementById("poolType").value;
	var instituteID=document.getElementById("instituteID").value;
	var msg="";
	if(poolName==''||poolCreationDate==''||cutOffDate==''||poolType==''||instituteID=='')
	{
		if(poolName=='')
			msg=msg+"*Pool Name is required.\n";
		if(poolCreationDate=='')
			msg=msg+"*Pool Creation Date is required.\n";
		if(cutOffDate=='')
			msg=msg+"*Cut Off Date is required.\n";
		if(poolType=='')
			msg=msg+"*Pool Type is required.\n";
		if(instituteID=='')
			msg=msg+"*InstituteID is required.\n";
		alert(msg); 
		if(msg.match("Pool Name"))
			document.getElementById("poolName").focus();
		else if(msg.match("Pool Creation Date"))
			document.getElementById("poolCreationDate").focus();
		else if(msg.match("Cut Off Date"))
			document.getElementById("cutOffDate").focus();
		else if(msg.match("Pool Type"))
			document.getElementById("poolType").focus();
		else if(msg.match("InstituteID"))
			document.getElementById("loanNoButton").focus();		
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var dt1=getDateObject(poolCreationDate,formatD.substring(2,3));
		var dt2=getDateObject(cutOffDate,formatD.substring(2,3));
		var dt3=getDateObject(currDate,formatD.substring(2,3));
		var dt4=getDateObject(businessdate,formatD.substring(2,3));
		if(dt1>dt2)
		{
	 		alert("Cut Off Date cannot be less than Pool Creation Date.");
		 	DisButClass.prototype.EnbButMethod();
		 	return false;
		}
		if(poolType=='S' && dt2<dt4)
	 	{
	 		alert("In the case of Securitized Cut Off Date should be greater than or equal to Business date.");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;
	 	}
	 	if(poolType=='R' && dt2>dt4)
	 	{
	 		alert("In the case of Re-Finance Cut Off Date should be less than or equal to Business date.");
	 		DisButClass.prototype.EnbButMethod();
	 		return false;
	 	}
	 	if(validateDocUpload())
	 	{
	 		document.getElementById("sourcingForm").action=sourcepath+"/poolIdMakerProcessAction.do?method=submitPoolIdUpload";
	 		document.getElementById("sourcingForm").submit();
	 		return true;
		}	
	}
}

function openNewWindow(val)
{
	var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/poolIdMakerUploadAction.do?method=getPoolId&type="+val;
		window.open(url,'name','height=300,width=500,top=100,left=400,location=no,directories=no,menubar=no,toolbar=no,status=no,scrollbars=no,resizable=no,dependent=no,fullscreen=no');
    	return true;
}

function checkInterestRate()
{
	var interestRate =document.getElementById('interestRate').value;
	if(interestRate > 100)
		{
			alert("Please Capture Valid interestRate")
			document.getElementById('interestRate').value = ''; 
			DisButClass.prototype.EnbButMethod();
	 		return false;
		}
}
