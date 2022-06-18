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
	   
	   
	   function deletePoolID(){		
			
			 var contextPath=document.getElementById("contextPath").value;
			 var poolIDList=document.getElementsByName('poolIDList');			
			 var loanIDList=document.getElementsByName('loanIDList');		 
			 var poolID = document.getElementById("poolIDList").value;
		
			 
			   	var flag=0;
			   	var poolIDLIST='';
			   	var loanIDLIST='';	
			
			   	 var ch=document.getElementsByName('checkId');
			   	var count=ch.length;
			   	 for(i=1;i<count;i++)
			   		{
			   		
			   		   if(ch[i].checked==true)
			   			{
			   		
			   		
			   		   id=ch[i].value;
			   		   
			   		poolIDLIST =poolIDLIST +poolID+ "/";
			   		loanIDLIST=loanIDLIST+ch[i].value+ "/";			 				 
					 
			   			  flag=1;		   			 
			   		
			   			}
			   		  
			   		}
			   	 if(flag==0)
			   	 {
			   		 alert("Please select one row");
			   		document.getElementById("Delete").removeAttribute("disabled");
			   		 return false;
			   	 }
			   	
			   		 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=deletePoolID&loanIDLIST="+loanIDLIST+"&poolIDLIST="+poolIDLIST+"&poolID="+poolID+"&actionType=maker";
			   	     document.getElementById("sourcingForm").submit();
			   	     return true;
			
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
	if(ext == "xls" || ext == "XLS")
	{
		return true;
	} 
	else
	{
		alert("Upload xls file only.");
		fup.focus();
		DisButClass.prototype.EnbButMethod();
		return false;
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
