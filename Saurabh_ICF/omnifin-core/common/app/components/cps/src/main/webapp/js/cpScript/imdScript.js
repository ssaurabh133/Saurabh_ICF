  	function imdAuthorSearch(alert1){
  		DisButClass.prototype.DisButMethod();

  		//if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("reportingToUserId").value==""))

  		if ((document.getElementById("lbxDealNo").value=="")&&(document.getElementById("customerName").value=="")
  				&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")
  				&&(document.getElementById("receiptMode").value=="")&&(document.getElementById("reportingToUserId").value==""))


		{
			alert(alert1);
			document.getElementById("search").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
  		else{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("imdMakerSearch").action=basePath+"/imdAuthorProcessAction.do?method=imdAuthorSearchDetail";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("imdMakerSearch").submit();		
		return true;
  	}
  	}
  	
	function imdSearch(alert1){		
		DisButClass.prototype.DisButMethod();

		//if ((document.getElementById("lbxLoanNoHID").value=="")&&(document.getElementById("customerName").value=="")&&(document.getElementById("businessPartnerType").value=="")&&(document.getElementById("businessPartnerName").value=="")&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")&&(document.getElementById("reportingToUserId").value==""))

		if ((document.getElementById("lbxDealNo").value=="")&&(document.getElementById("customerName").value=="")
				&&(document.getElementById("receiptAmount").value=="")&&(document.getElementById("instrumentNumber").value=="")
				&&(document.getElementById("receiptMode").value=="")&&(document.getElementById("reportingToUserId").value==""))

        
		{
			alert(alert1);
			document.getElementById("search").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("imdMakerSearch").action=basePath+"/imdMakerSearch.do?search=Y";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("imdMakerSearch").submit();		
    return true;
	}
	}
	
        function newImdMaker(){ 
		
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("imdMakerSearch").action=basePath+"/imdMakerNewAction.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("imdMakerSearch").submit();
	    return true;
	}
        
        function onSaveImd(alert2,alert3)
        {
        	DisButClass.prototype.DisButMethod();        	
	    if (document.getElementById("instrumentID").value!="")
	     { 
	    	
		var formatD=document.getElementById("formatD").value;
		var basePath=document.getElementById("contextPath").value;
		var instrumentDate = document.getElementById("instrumentDate").value;
		var receiptDate = document.getElementById("receiptDate").value;
		var currDate = document.getElementById("businessDate").value;
		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
	    var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
		var tdsAmount = removeComma(document.getElementById('tdsAmount').value);
		var receiptNoFlag=document.getElementById("receiptNoFlag").value;
		var receiptNo=document.getElementById("receiptNo").value;
		if(tdsAmount==""){
			 tdsAmount=0.0000
			 }
		var amount=(parseFloat(receiptAmount) + parseFloat(tdsAmount))	
		var dealNo=document.getElementById("dealNo").value;
	   		if(dealNo=="")
	   		{
				alert("Deal Number is required");
				DisButClass.prototype.EnbButMethod();
				return false;
	   		}
	    if(validateImdMakerDynaValidatorForm(document.getElementById("imdMakerForm"))){
	    	
	    	if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
	    	{
		    	 if (dt1>dt2)
				  {
				  alert(alert2);
				  document.getElementById("save").removeAttribute("disabled");
				  DisButClass.prototype.EnbButMethod();
				  return false;
				  }
	    	}

		    if (dt2>dt3)
			  {
			  alert(alert3);
			  document.getElementById("save").removeAttribute("disabled");
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
		    
			/*if(receiptNoFlag=='Y' && receiptNo=='')
	    	{
    			alert("Please Enter the Receipt No.");
    			DisButClass.prototype.EnbButMethod();
    			document.getElementById("save").removeAttribute("disabled");
    			return false;
	    			
	    	}*/
			
			if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
		  	{
					alert("Receipt No. is required");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled");
					return false;
		  			
		  	}
	
		    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
			{
		    	
		    	 if((document.getElementById("instrumentNumber").value)==""){
			    		alert("Instrument Number / Ref no is required.");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}
		    	 if((document.getElementById("instrumentDate").value)==""){
			    		alert("Instrument Date is required.");
			    		document.getElementById("save").removeAttribute("disabled");
			    		DisButClass.prototype.EnbButMethod();
			    		return false;
			    		}
		    		   if((document.getElementById("bank").value)==""){
		    		alert("Please select bank ");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
		    		  
		    		 if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
		    		alert("Please select branch ");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
		    		  if((document.getElementById("bankAccount").value=="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
				    		alert("Bank Account is required.");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
			}
		document.getElementById("imdMakerForm").action = basePath+"/imdMakerProcessAction.do?method=updateReceiptSavedData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("imdMakerForm").submit();
	     return true;
	}
	    else
	    {
	    	document.getElementById("save").removeAttribute("disabled");
	    	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
	} else
		   { 
	
		var formatD=document.getElementById("formatD").value;
		var basePath=document.getElementById("contextPath").value;
		var instrumentDate = document.getElementById("instrumentDate").value;
		var receiptDate = document.getElementById("receiptDate").value;
		var currDate = document.getElementById("businessDate").value;
		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
	    var receiptNoFlag=document.getElementById("receiptNoFlag").value;
	    var receiptNo=document.getElementById("receiptNo").value;
		var dealNo=document.getElementById("dealNo").value;
   		if(dealNo=="")
   		{
			alert("Deal Number is required");
			DisButClass.prototype.EnbButMethod();
			return false;
   		}
		if(validateImdMakerDynaValidatorForm(document.getElementById("imdMakerForm"))){
			if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
	    	{
			 if (dt1>dt2)
			  {
			  alert(alert2);
			  document.getElementById("save").removeAttribute("disabled");
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
	    	}
		    if (dt2>dt3)
			  {
			  alert(alert3);
			  document.getElementById("save").removeAttribute("disabled");
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
			/*if(receiptNoFlag=='Y'&& receiptNo=='')
	    	{
    			alert("Receipt No is required.");
    			DisButClass.prototype.EnbButMethod();
    			document.getElementById("save").removeAttribute("disabled");
    			return false;
	    			
	    	}*/
			
			if(document.getElementById("statusReceipt").value=='M' && receiptNo=='')
		  	{
					alert("Receipt No. is required");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled");
					return false;
		  			
		  	}
	
			
		    if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
			{
		    	    if((document.getElementById("instrumentNumber").value)==""){
		    		alert("Instrument Number is required.");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
	    	       if((document.getElementById("instrumentDate").value)==""){
		    		alert("Instrument Date is required.");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
		    		   if((document.getElementById("bank").value)==""){
		    		alert("Please select bank ");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
		    		if((document.getElementById("branch").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
		    		alert("Please select branch ");
		    		document.getElementById("save").removeAttribute("disabled");
		    		DisButClass.prototype.EnbButMethod();
		    		return false;
		    		}
		    		if((document.getElementById("bankAccount").value =="") && ((document.getElementById("receiptMode").value!='N')&&(document.getElementById("receiptMode").value!='R'))){
		    		  
				    		alert("Bank Account is required.");
				    		document.getElementById("save").removeAttribute("disabled");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    		}
			}
		  
		 document.getElementById("imdMakerForm").action = basePath+"/imdMakerProcessAction.do?method=saveForReceipt";
		 document.getElementById("processingImage").style.display = '';		
		 document.getElementById("imdMakerForm").submit();
	     return true;
		}
		else
		{		
			document.getElementById("save").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		   }
   }
        
     	function  onImdForwardCheck(alert2,alert3,fwdMsg){
     		
     		DisButClass.prototype.DisButMethod();
     		if(!confirm(fwdMsg))	 
     	    {
     	       	DisButClass.prototype.EnbButMethod();
     	    	return false;
     	    }
     		var loanID=document.getElementById("lbxDealNo").value;
     		var instrumentID=document.getElementById("instrumentID").value;
     		var basePath=document.getElementById("contextPath").value;
   		    var formatD=document.getElementById("formatD").value;	 
   		 	
   	 		var instrumentDate = document.getElementById("instrumentDate").value;
   	 		var receiptDate = document.getElementById("receiptDate").value;
   	 		var currDate = document.getElementById("businessDate").value;
   	 		var dt1=getDateObject(instrumentDate,formatD.substring(2, 3));
   	 	    var dt2=getDateObject(receiptDate,formatD.substring(2, 3));
   	 	    var dt3=getDateObject(currDate,formatD.substring(2, 3));
   	 	    var receiptAmount=removeComma(document.getElementById('receiptAmount').value); 
   	 	    var  tdsAmount=document.getElementById('tdsAmount').value;
		    if(tdsAmount==""){
			 tdsAmount="0";			
			 }
		    else
		     {
		    tdsAmount=removeComma(document.getElementById('tdsAmount').value);		
		     }
		//alert("receiptAmount"+receiptAmount);
		//alert("tdsAmount"+tdsAmount);
		var amount = (receiptAmount + tdsAmount);
		
		 
      if (document.getElementById("canForward").value=="")	
       {	 
			 alert("Please save the data first");	
		     DisButClass.prototype.EnbButMethod();	  
		     return false;
       	}
 
	 
	 if(validateImdMakerDynaValidatorForm(document.getElementById("imdMakerForm"))){
		 if((document.getElementById("receiptMode").value!='C') && (document.getElementById("receiptMode").value!='S')&& (document.getElementById("receiptMode").value!='DIR'))
	    	{
			 if (dt1>dt2)
			  {
			  alert(alert2);
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
	    	}
			if (dt2>dt3)
			  {
			  alert(alert3);
			  DisButClass.prototype.EnbButMethod();
			  return false;
			  }
			
			 //Ravi start
		    var loanRecStatus="";
		      if (document.getElementById("loanBranch").value=='Y')	
		      {	 
		    	  var status = confirm("Login branch and loan branch is not same,Do you want to Forword.");
		    		if(!status)
		    		{
		    			document.getElementById("saveForward").removeAttribute("disabled");
						DisButClass.prototype.EnbButMethod();
						return false;
		    		}
		      }
			document.getElementById("imdMakerForm").action=basePath+'/imdMakerProcessAction.do?method=receiptForward&amount='+amount+"&loanID="+loanID+"&instrumentID="+instrumentID+"&loanRecStatus="+loanRecStatus;
			document.getElementById("processingImage").style.display = '';
	 	    document.getElementById("imdMakerForm").submit();
			return true;
	  
		  }
	 else{
		  DisButClass.prototype.EnbButMethod();
		  return false;
		 
	 	}
 	 }
     	
        function viewReceivable(alert1) 
      	{	
        	 
      		if ((document.getElementById("lbxLoanNoHID").value)=="")
      		{
      			 DisButClass.prototype.DisButMethod();
      			alert(alert1);	
      			DisButClass.prototype.EnbButMethod();
      			return false;
      		}
      		else
      		{
      			var loanId=document.getElementById('lbxLoanNoHID').value;
      			var bpType=document.getElementById('lbxBPType').value; 
      			var bpId=document.getElementById('lbxBPNID').value;	
      			var basePath=document.getElementById("contextPath").value;
      			window.open(basePath+'/imdMakerViewAction.do?method=viewReceivable&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
      			return true;
      		}
      	}
        
        function allocateImdReceivable(alert1){
		    	if (document.getElementById("canForward").value!="")
		  		{
		  			
		  		 var loanId=document.getElementById('lbxDealNo').value;	
		  		 var bpType="CS";
		  		 var bpId=document.getElementById('lbxBPNID').value;
		  		 var instrumentId=document.getElementById('instrumentID').value;
		  		 var receiptamount=document.getElementById('receiptAmount').value;
		  		 
		  		if(instrumentId==''){
		  			DisButClass.prototype.DisButMethod();
		  			alert(alert1);
		  			document.getElementById("allocateRec").removeAttribute("disabled");
		  			DisButClass.prototype.EnbButMethod();
		  			return false;
		  		}else{
		  		var basePath=document.getElementById("contextPath").value;
		  		window.open(basePath+'/imdMakerViewAction.do?method=allocateReceivableData&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId+'&instrumentId='+instrumentId+'&receiptamount='+receiptamount,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		  	 	return true;
		  		} 
		  		   }
		  		 else
		  		 {
		  			DisButClass.prototype.DisButMethod();
		  			alert(alert1);
		  			document.getElementById("allocateRec").removeAttribute("disabled");
		  			DisButClass.prototype.EnbButMethod();
		  			return false;
		  		 }
    	}
        function allocatedImdReceivable(){
	  		curWin = 0;
	  		otherWindows = new Array();
	  		
	  		 var loanId=document.getElementById('lbxDealNo').value;	
	  		 var bpType="CS";	
	  		 var instrumentID=document.getElementById('instrumentID').value;
	  		 var basePath=document.getElementById("contextPath").value;
	  		 otherWindows[curWin++] =window.open(basePath+'/imdMakerViewAction.do?method=viewAllocatedReceivable&loanId='+loanId+'&bpType='+bpType+'&instrumentID='+instrumentID,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");

	  		 return true;
        }
        
//        function allocateImdReceivable(alert1){
//      	  
//      	  
//    		var sum =0;
//    		var amount=0;
//    		var tdsAmount=document.getElementById('tdsAmount').value;
//    		var receiptamount=document.getElementById('receiptAmount').value;
//         	if(receiptamount!='' || receiptamount>0)
//         		var receiptamount=removeComma(document.getElementById('receiptAmount').value); 	 
//         	else
//         		var receiptamount=0;
//         	 if(tdsAmount!='' || tdsAmount>0)
//         		var tdsAmount=removeComma(document.getElementById('tdsAmount').value);
//        	else
//        		var tdsAmount=0;
//         	
//         	//alert("receiptamount"+amount);
//         //	alert("tdsAmount"+tdsAmount);
//         
//        	sum =parseFloat(receiptamount) + parseFloat(tdsAmount);
//        //	alert("sum"+sum);
//        	amount=sum;
//        	if (document.getElementById("canForward").value==""){
//        		DisButClass.prototype.DisButMethod();
//        		alert(alert1);
//      			document.getElementById("allocateRec").removeAttribute("disabled");
//      			DisButClass.prototype.EnbButMethod();
//      			return false;
//        	}
//        	else if(sum >0)
//            {
//            	if (document.getElementById("canForward").value!="")
//          		{
//          			
//          		 var loanId=document.getElementById('lbxDealNo').value;	
//          		 var bpType=document.getElementById('lbxBPType').value;
//          		 var bpId=document.getElementById('lbxBPNID').value;
//          		 var instrumentId=document.getElementById('instrumentID').value;
//         	
//          		var basePath=document.getElementById("contextPath").value;
//          		window.open(basePath+'/imdMakerViewAction.do?method=allocateReceivableData&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId+'&instrumentId='+instrumentId+'&receiptamount='+receiptamount+'&tdsAmount='+tdsAmount+'&amount='+amount,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
//          	 	return true;
//          	 	  		 
//          		   }
//          		 else
//          		 {
//          			DisButClass.prototype.DisButMethod();
//          			alert(alert1);
//          			document.getElementById("allocateRec").removeAttribute("disabled");
//          			DisButClass.prototype.EnbButMethod();
//          			return false;
//          		 }
//            }
//           else{
//            	alert("Receipt amount and Tds amount sum should be greater than zero");
//            	document.getElementById("allocateRec").setAttribute("disabled",true);
//            	
//            }
//        
//      	}
        
      	function deleteImd(){
      		
      	  	DisButClass.prototype.DisButMethod();
      		var sourcepath=document.getElementById("contextPath").value;
      		var instrumentID=document.getElementById("instrumentID").value;
      		if(instrumentID!=""){
      		//alert("loanId......"+loanId);
      		agree=confirm("Are you sure,You want to delete this Receipt.Do you want to continue?");
      		if (!(agree))
      		{	
      	    	document.getElementById("delete").removeAttribute("disabled","true");
      	    	DisButClass.prototype.EnbButMethod();
      			return false;
      		}else{
      			document.getElementById("imdMakerForm").action=sourcepath+"/imdMakerProcessAction.do?method=deleteReceipt&instrumentID"+instrumentID;
      			document.getElementById("processingImage").style.display = '';
      			document.getElementById("imdMakerForm").submit();
      			return true;
      	}
      		}else{
      			alert("No record found for deletion.");
      			document.getElementById("delete").removeAttribute("disabled","true");
      	    	DisButClass.prototype.EnbButMethod();
      			return false;
      		}
      		
      	}
      	
    	function fnReceiptNull(){
   	     
   		 var receiptMode =document.getElementById('receiptMode').value;
   	     document.getElementById('instrumentNumber').value="";
   	     if(receiptMode=='C'||receiptMode=='S')
   	     {
   	    	 document.getElementById('instrumentDate').value="";
   	     }
   	     else
   	     {
   	    	 document.getElementById('instrumentDate').value=document.getElementById('businessdate').value ;
   	     }
   	    
   		 document.getElementById('bankAccount').value="";	
   		 document.getElementById('lbxbankAccountID').value="";
   		 document.getElementById('bank').value="";
   		 document.getElementById('lbxBankID').value="";
   		 document.getElementById('branch').value="";
   		 document.getElementById('lbxBranchID').value="";
   		 document.getElementById('micr').value="";
   		 document.getElementById('ifsCode').value="";
   		 return true;		 
   }
    	
    	function cashAccountDisable()
    	{
    	
    		if((document.getElementById("receiptMode").value == "C")|| (document.getElementById("receiptMode").value == "S")|| (document.getElementById("receiptMode").value == "DIR"))
    		{   		
    			
    			document.getElementById("disId").style.display='none';
    			document.getElementById("disIdBranch").style.display='none';
    			document.getElementById("disIdInsD").style.display='none';
    			document.getElementById("disIdIns").style.display='inline';
    			
    			document.getElementById("bankAccount").disabled=true;
    			document.getElementById("lbxbankAccountID").disabled=true;
    			document.getElementById("instrumentNumber").disabled=true;
    			document.getElementById("instrumentDateDis").disabled=true;
    			document.getElementById("lbxBranchID").disabled=true;
    			document.getElementById("lbxBankID").disabled=true;
    		//	document.getElementById("loanBankButton").style.display='none';
    			////document.getElementById("loanBranchButton").style.display='none';
    		}
    		else if((document.getElementById("receiptMode").value == "N")|| (document.getElementById("receiptMode").value == "R"))
    		{
    			
    			document.getElementById("disIdBranch").style.display='none';
    			document.getElementById("bankAccount").disabled=true;
    			document.getElementById("lbxbankAccountID").disabled=true;
    			document.getElementById("bankAccount").disabled=true;
    			document.getElementById("lbxbankAccountID").disabled=true;
    			document.getElementById("lbxBranchID").disabled=true;
    			document.getElementById("instrumentNumber").disabled=false;
    			document.getElementById("lbxBankID").disabled=false;
    			document.getElementById("loanBankButton").style.display='';
    			document.getElementById("disId").style.display='inline';
    			document.getElementById("disIdInsD").style.display='inline';
    			document.getElementById("disIdIns").style.display='none';
    		}
    		else{
    			//disId
    			//document.getElementById("showId").disabled=false;
    			document.getElementById("bankAccount").disabled=false;
    			document.getElementById("lbxbankAccountID").disabled=false;
    			document.getElementById("instrumentNumber").disabled=false;
    			document.getElementById("instrumentDate").disabled=false;	
    			document.getElementById("lbxBranchID").disabled=false;
    			document.getElementById("lbxBankID").disabled=false;
    			document.getElementById("disId").style.display='inline';
    			document.getElementById("disIdBranch").style.display='inline';
    			document.getElementById("disIdInsD").style.display='inline';
    			document.getElementById("disIdIns").style.display='none';
    			document.getElementById("loanBankButton").style.display='';
    			document.getElementById("loanBranchButton").style.display='';
    		}
    		
    		
    	}
    	
        function blankcanforward(){
      	  
      	  document.getElementById("canForward").value="";
      	  
        }
        
     	 function onSaveImdAuthor(alert1){
      		 
       		
       		if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
     		   {
       			DisButClass.prototype.DisButMethod();
     			alert(alert1);
     			document.getElementById("save").removeAttribute("disabled");	
     			DisButClass.prototype.EnbButMethod();
     			return false;
     		   }
     		else
     		{
       		  var basePath=document.getElementById("contextPath").value;
       		  
//       		 //Ravi start
//     		    
//     		    if(document.getElementById("loanRecStatus").value!='')
//     		    {
//     		    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
//     		    	{
//     		    		var status = confirm("Loan is on pending stage. Do you want to continue..");
//     		    		//alert("status :"+ status);
//     		    		if(!status)
//     		    		{
//     		    			document.getElementById("save").removeAttribute("disabled");
//     						DisButClass.prototype.EnbButMethod();
//     						return false;
//     		    		}
//     		    	}else if(document.getElementById("loanRecStatus").value=='C')
//     		    	{
//     		    		var status = confirm("Loan is close. Do you want to continue..");
//     		    		//alert("status :"+ status);
//     		    		if(!status)
//     		    		{
//     		    			document.getElementById("save").removeAttribute("disabled");
//     						DisButClass.prototype.EnbButMethod();
//     						return false;
//     		    		}
//     		    	}
//     		    	
//     		    }
//     		    
//     		    //Ravi End
       		  
       		  document.getElementById("imdAuthorForm").action=basePath+"/imdAuthorProcessAction.do?method=onSaveImdAuthor";
       		  document.getElementById("processingImage").style.display = '';
       		  document.getElementById("imdAuthorForm").submit();

       		}
       	
       	 }
     	 
         function viewImdReceivable(alert1) 
       	{	
         	 
       		if ((document.getElementById("lbxDealNo").value)=="")
       		{
       			 DisButClass.prototype.DisButMethod();
       			alert(alert1);	
       			DisButClass.prototype.EnbButMethod();
       			return false;
       		}
       		else
       		{
       			var loanId=document.getElementById('lbxDealNo').value;
       			var bpType="CS"; 
       			var bpId=document.getElementById('lbxBPNID').value;	
       			var basePath=document.getElementById("contextPath").value;
       			window.open(basePath+'/imdMakerViewAction.do?method=viewReceivable&loanId='+loanId+'&bpType='+bpType+'&bpId='+bpId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
       			return true;
       		}
       	}
         
     	function onViewImdReceivableSave(alert1)  {
    		
    		DisButClass.prototype.DisButMethod();
	 //alert("alert1  : "+alert1);
	 var receiptAmount=removeComma(document.getElementById('receiptAmount').value);
		var  tdsAmount=0;
		var tdsSum=0;
		var sum=0;
		var allocatedSum=0;
		var i;
		var gridTable = document.getElementById('gridTable');
		var tableLength = gridTable.rows.length-1;
		for(i=1;i<=tableLength;i++)
		{

			var allotedAmount=document.getElementById('imdAllocatedAmount'+i).value;			 
		 if(allotedAmount=="")
		  allotedAmount=0;
	     else
		  allotedAmount=removeComma(document.getElementById('imdAllocatedAmount'+i).value);		    	 
	    
		 var balanceAmount=document.getElementById('balanceAmount'+i).value;			 
		 if(balanceAmount=="")
		 	 balanceAmount=0;
	   	 else
		  	  balanceAmount=removeComma(document.getElementById('balanceAmount'+i).value);		    	  
	    	
		// alert("allotedAmount: "+allotedAmount+"balanceAmount: "+balanceAmount);
		 allocatedSum = (allocatedSum + allotedAmount);  
			if(allotedAmount > balanceAmount )
			{ 				
				alert("Allocated Amount should not be greater than Balance Amount");
				document.getElementById("save").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				return false;
			}	
		}  		

		var totalamt=receiptAmount + tdsAmount;	
//		alert("allocatedSum::::::::::"+allocatedSum);
//		alert("receiptAmount::::::::::"+receiptAmount); 
		if(allocatedSum > (receiptAmount))
	{
		alert("Total Allocated Amount should be equal to or  less than IMD Amount");		
		document.getElementById("save").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	} 
					
		var basePath=document.getElementById("contextPath").value;  				
		document.getElementById("viewReceivableForm").action = basePath+'/imdMakerViewAction.do?method=onSaveViewReceivable';
	    document.getElementById("processingImage").style.display = ''; 
	    document.getElementById("viewReceivableForm").submit();  	   
	    return true;  	  			
	 
	}
     	
    	function receiptShowHide1()
	{
     		
		
		var statusReceipt=document.getElementById("statusReceipt").value;
				
		if(document.getElementById("statusReceipt").value=="M")
	  	{
			
			document.getElementById('receiptNo').removeAttribute("readOnly","true");
			document.getElementById('receiptNo').value="";	
		}
		else
		{
			
			 document.getElementById('receiptNo').setAttribute("readOnly","true");
			 document.getElementById('receiptNo').value="";	
		}
		
	}
