
function searchInstrumentAuthor(){
		   	
			   DisButClass.prototype.DisButMethod();
		       var contextPath=document.getElementById("contextPath").value;
		       var reportingToUserId=document.getElementById("reportingToUserId").value;
		   //    alert("ok");
		   if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
		     
		   	 alert("Please select atleast one criteria");
		   	 DisButClass.prototype.EnbButMethod();
		   	//document.getElementById("save").removeAttribute("disabled");
		   	return false;
		    
		   }else {
		   document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchInstrumentAuthor";
		   document.getElementById("processingImage").style.display = '';
		   document.getElementById("sourcingForm").submit();
		   		 return true;
		   }
		   }
	   
	   function searchInstrument(){
		     DisButClass.prototype.DisButMethod();
	   	     var contextPath=document.getElementById("contextPath").value;
	   	     var reportingToUserId=document.getElementById("reportingToUserId").value;
	   //	     alert("ok");
	   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
	   	   
		   		 alert("Please select atleast one criteria");
		   		 DisButClass.prototype.EnbButMethod();
		   		 document.getElementById("save").removeAttribute("disabled");
		   		 return false;
	   	 }else {
			   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchInstrument";
			   	 document.getElementById("processingImage").style.display = '';
			   	 document.getElementById("sourcingForm").submit();
	   	 		 return true;
	   	 }
	   }
	   
	   function newInstrument(){
		   	DisButClass.prototype.DisButMethod();
		   	var contextPath=document.getElementById("contextPath").value;
		   	document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=newInstrument";
		   	document.getElementById("processingImage").style.display = '';
		   	document.getElementById("sourcingForm").submit();
		   	     return true;
		   	
		   }
	   
	   
		function ecsFunction(){
			
			if((document.getElementById("instrumentType").value) == "E"|| (document.getElementById("instrumentType").value) == "H"){
				document.getElementById("ecs").style.display="inline";
				document.getElementById("pdc").style.display="none";
				document.getElementById("A").style.display="none";
				document.getElementById("B").style.display="none";
				document.getElementById("C").style.display="";
				document.getElementById("D").style.display="";
//				document.getElementById("E").style.display="";
//				document.getElementById("F").style.display="";
				document.getElementById("G").style.display="";
				document.getElementById("H").style.display="";
				document.getElementById("I").style.display="";
				document.getElementById("J").style.display="";
				document.getElementById("Y").style.display="none";
				document.getElementById("Z").style.display="none";
				document.getElementById("SIN").style.display="none";
				
				document.getElementById("startingChequeNo").value="";
				document.getElementById("startingChequeNo").readOnly=true;
				document.getElementById("endingChequeNo").value="";
				document.getElementById("endingChequeNo").readOnly=true;
				document.getElementById("fromInstallment").readOnly=false;
				document.getElementById("toInstallment").readOnly=false;
				document.getElementById("fromInstallment").value="";
				document.getElementById("toInstallment").value="";
				document.getElementById("purpose").value="";
				
	    		   document.getElementById("s1").style.display="";
	    		   document.getElementById("s2").style.display="";
	    		   document.getElementById("s3").style.display="";
				
			}
	       if((document.getElementById("instrumentType").value) == "Q" || (document.getElementById("instrumentType").value) == "DIR" ||(document.getElementById("instrumentType").value) == ""){
	    	   //added by neeraj space start
	    	   if(document.getElementById("instrumentType").value=='Q')
	    	   {
	    		   document.getElementById("s1").style.display="";
	    		   document.getElementById("s2").style.display="";
	    		   document.getElementById("s3").style.display="";
	    	   }
	    	   else
	    	   {
	    		   document.getElementById("s1").style.display="none";
	    		   document.getElementById("s2").style.display="none";
	    		   document.getElementById("s3").style.display="none";
	    		   document.getElementById("pdcSubmitCustomerName").value="";
	    		   document.getElementById("submitBy").value="";
	    	   }
	    	   //neeraj space end
	    	   document.getElementById("ecs").style.display="none";
				document.getElementById("pdc").style.display="inline";
	    	   document.getElementById("A").style.display="";
	    	   document.getElementById("B").style.display="";
	    	   document.getElementById("Y").style.display="";
	    	   document.getElementById("Z").style.display="";
	    	   document.getElementById("SIN").style.display="";
	    		document.getElementById("C").style.display="none";
				document.getElementById("D").style.display="none";
//				document.getElementById("E").style.display="none";
//				document.getElementById("F").style.display="none";
				document.getElementById("G").style.display="none";
				document.getElementById("H").style.display="none";
				document.getElementById("I").style.display="none";
				document.getElementById("J").style.display="none";
				document.getElementById("startingChequeNo").readOnly=false;
				document.getElementById("endingChequeNo").readOnly=false;
				document.getElementById("fromInstallment").readOnly=true;
				document.getElementById("toInstallment").readOnly=true;
				document.getElementById("fromInstallment").value="";
				document.getElementById("toInstallment").value="";
			}
			
		}
		
		   function disableFromTo()
		   {
		   	
		   	
		   	if((document.getElementById("purpose").value)=="PDC"  || (document.getElementById("purpose").value)=="" ){
		   		
		   		
		   		document.getElementById("fromInstallment").readOnly=false;
		   		document.getElementById("fromInstallment").value='';
		   		document.getElementById("toInstallment").readOnly=false;
		   		document.getElementById("toInstallment").value='';
		   		
		   		
		   	}else{
		   		
		   		document.getElementById("fromInstallment").readOnly=true;
		   		document.getElementById("fromInstallment").value=0;
		   		document.getElementById("toInstallment").readOnly=true;
		   		document.getElementById("toInstallment").value=0;
		   		
		   	}
		   	
		   	if(document.getElementById("purpose").value=="PRE EMI")
		   	{
		   		//document.getElementById("instrumentPreEmiDate").value='';
		   		document.getElementById("preemiDateLebel").style.display='';
		   		document.getElementById("preemiDate").style.display='';
		   		
		   	}
		   	else
		   	{
		   		//document.getElementById("instrumentPreEmiDate").value='';
		   		document.getElementById("preemiDateLebel").style.display='none';
		   		document.getElementById("preemiDate").style.display='none';
		   	}
		   	
		   	
		  }  
		   
		   function closeLOVWindow()
		   {
		       if (openLOVWindows && !openLOVWindows.closed) 
		         openLOVWindows.close();
		   }
		   
		   
		   function isNumberKey(evt) 
		   {

		   var charCode = (evt.which) ? evt.which : event.keyCode;

		   if (charCode > 31 && (charCode < 48 || charCode > 57))
		   {
		   	alert("Only Numbers Allowed Here");
		   	return false;
		   }
		   	return true;
		   }
		   
		   function validate(){
			   	
			   	
			   	if((parseInt(document.getElementById("totalInstallments").value)) < (parseInt(document.getElementById("toInstallment").value)) ){
			   		alert("Installments should not be exceeded to total installments.");
			   		document.getElementById("toInstallment").value = "";
			   	
			   		return false;
			   	}
			   	
			   
			   	if((parseInt(document.getElementById("fromInstallment").value)) > (parseInt(document.getElementById("toInstallment").value ))){
			   		alert("From Installment should not be greater than To Installment.");
			   		document.getElementById("toInstallment").value ="";
			   		
			   		return false;
			   	}
			   	
			   	
			   }
		   
		   function viewRepaymentScheduleDisbursal()
		   {
		   	if (document.getElementById("lbxLoanNoHID").value=="")
		   	{
		   		alert("Please Select Loan First");	
		   		return false;
		   	}
		   	else
		   	{
		   		curWin = 0;
		   		otherWindows = new Array();
		   		var loanId=document.getElementById('lbxLoanNoHID').value;	
		   		var contextPath = document.getElementById("contextPath").value;
		   		var url= contextPath+"/repaymentScheduleDisbursal.do?method=repaymentSchedule&loanId="+loanId; 
		   		myWindow=window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
		   		otherWindows[curWin++] =window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		   		if(window.focus) {
		   			mywindow.focus();
		   			return false;
		   		}
		   		return true;
		      }
		   }
		   
			function saveInstrument(){		
				
				
				DisButClass.prototype.DisButMethod();
				var contextPath=document.getElementById("contextPath").value;
				var toInstallment = parseInt(document.getElementById("toInstallment").value);
				
				var fromInstallment = parseInt(document.getElementById("fromInstallment").value); 
				var instDiffCount=parseInt(document.getElementById("installmentDiff").value);
				var instrumentPreEmiDate=document.getElementById("instrumentPreEmiDate").value;
				var purpose=document.getElementById("purpose").value;
				var startingChequeNo = parseFloat(document.getElementById("startingChequeNo").value); 
				var endingChequeNo = parseFloat(document.getElementById("endingChequeNo").value);
				var installments =	 document.getElementsByName("installments");
				var instrumentAmountAll = document.getElementsByName("instrumentAmountAll");
				var installmentAmountAll = document.getElementsByName("installmentAmountAll");
				var customerAcType = parseInt(document.getElementById("customeracType").value);
				var alertStatusFromParameterMst = document.getElementById("alertStatusFromParameterMst").value;
				
				//var diffOfInstumentNo=endingChequeNo-startingChequeNo;
				if(endingChequeNo>startingChequeNo)
	 			{
	 				var diffOfInstumentNo=endingChequeNo-startingChequeNo;
	 			}
	 			else
	 			{
	 				var diffOfInstumentNo=startingChequeNo-endingChequeNo;
	 			}
				var a =toInstallment - fromInstallment;
			
				var totalInstallments = parseInt(document.getElementById("totalInstallments").value);
				
				if(validateInstrumentCapturingMakerValidatorForm(document.getElementById("sourcingForm")))
				{
					
					if(document.getElementById("instrumentType").value == "Q"  || document.getElementById("instrumentType").value == "E" || document.getElementById("instrumentType").value == "H")
					{
						if(document.getElementById("submitBy").value =="")
						{
							alert("Please Select PDC/ECS Given By.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(document.getElementById("pdcSubmitCustomerName").value == "" && document.getElementById("submitBy").value == "COAPPL" )
						{
							alert("CO APPLICANT does not exist for this loan.");
							DisButClass.prototype.EnbButMethod();
							return false;
							
						}
						
						if(document.getElementById("pdcSubmitCustomerName").value == "" && document.getElementById("submitBy").value == "GUARANTOR" )
						{
							alert("GUARANTOR does not exist for this loan.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						if(document.getElementById("pdcSubmitCustomerName1").value == "" && document.getElementById("submitBy").value == "OTHER" )
						{
							alert("Please Enter Name Of PDC/ECS Given By");
							document.getElementById("pdcSubmitCustomerName").focus();
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						if(document.getElementById("instrumentType").value =="E" && parseFloat(document.getElementById("instrumentAmount").value) == parseFloat("0"))
						{
							alert("Please Enter the valid Instrument Amount.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}	
						if(document.getElementById("instrumentType").value =="H" && parseFloat(document.getElementById("instrumentAmount").value) == parseFloat("0"))
						{
							alert("Please Enter the valid Instrument Amount.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}		
					}
					if(document.getElementById("instrumentType").value == "Q"  ||document.getElementById("instrumentType").value == "DIR" ){
						
				
						if(document.getElementById("purpose").value =="PDC" && parseFloat(document.getElementById("instrumentAmount").value) == parseFloat("0"))
						{
							alert("Please Enter the valid Instrument Amount.");
							DisButClass.prototype.EnbButMethod();
							return false;
						}
						
						 if((document.getElementById("purpose").value) == 'INSTALLMENT' ) {	
							 
							 
							
				   if(removeComma(document.getElementById("totalChargeInstallmentAmount").value) <  removeComma(document.getElementById("instrumentAmount").value))  //ravi 
				   {
						
						alert("You can disburse your amount to another loan as your PDC Amount is higher than Installment Amount");
						DisButClass.prototype.EnbButMethod();
					}
				 }
				   
				   	  //alert(installments.length);
				   //	  if(installments.length == 0){
				   		 //alert(fromInstallment);
				   		//alert(document.getElementById("loanInstallmentMode").value);
				   		 if((document.getElementById("loanInstallmentMode").value)=='A'){
				   			 //alert(document.getElementById("loanInstallmentMode").value);
				   			if(fromInstallment == 1){
				   				alert("Your first installment is already received.Please generate it from second.");
				   				document.getElementById("fromInstallment").value='';
				   				document.getElementById("fromInstallment").focus();
				   				DisButClass.prototype.EnbButMethod();
				   				//document.getElementById("save").removeAttribute("disabled");
								return false;
				   			}
				   			 
				   			var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
				   			//alert("loanAdvanceInstallment--"+loanAdvanceInstallment);
				   			
				   			if(loanAdvanceInstallment != 0){
				   			//	if(totalInstallments == toInstallment){
				   					
				   				if(totalInstallments < (toInstallment + loanAdvanceInstallment)){
				   					alert(+loanAdvanceInstallment+" Advance Installment/s has already been received.");
				   					DisButClass.prototype.EnbButMethod();
				   					//document.getElementById("save").removeAttribute("disabled");
									return false;
				   				//}
				   				}
				   				
				   			}
				   			
				   			
				   			
				   		 }
				   		// alert((document.getElementById("loanInstallmentMode").value));
				   		if((document.getElementById("loanInstallmentMode").value)=='R'){
				   			
				   			var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
				   		//	alert("loanAdvanceInstallment--"+loanAdvanceInstallment);
				   			
				   			if(loanAdvanceInstallment != 0){
				   				
				   			//	if(totalInstallments == toInstallment){
				   				
				   				if(totalInstallments < (toInstallment + loanAdvanceInstallment)){
				   					alert(+loanAdvanceInstallment+" Advance Installment/s has already been received.");
				   					DisButClass.prototype.EnbButMethod();
				   					//document.getElementById("save").removeAttribute("disabled");
									return false;
				   				}
				   			//	}
				   				
				   			}
				   			
				   		}
				   		 
				   	 // } 
					
						 
						 
				   
				   if((document.getElementById("purpose").value) == '' ) {
						
						alert("Enter Purpose");
						document.getElementById("purpose").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
				   
					if(document.getElementById("purpose").value == "PDC"){		
						
						
		              if(fromInstallment == 0 ) {
							
							alert("From Installment should be greater than 0");
							document.getElementById("fromInstallment").focus();
							document.getElementById("fromInstallment").value="";
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						
				        if((document.getElementById("fromInstallment").value) == '' ) {
							
							alert("Enter From Installment");
							document.getElementById("fromInstallment").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
				      
				        if((document.getElementById("toInstallment").value) == '' ) {
							
							alert("Enter To Installments");
							document.getElementById("toInstallment").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						
		               if((document.getElementById("startingChequeNo").value) == '' ) {
							
							alert("Enter Starting Instrument No");
							document.getElementById("startingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						
				        if((document.getElementById("endingChequeNo").value) == '' ) {
							
							alert("Enter Ending Instrument No");
							document.getElementById("endingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						} 
				        
		               if((document.getElementById("startingChequeNo").value.length) < 6 ) {
							
							alert("Minimum length of Starting Instrument No should be 6.");
							document.getElementById("startingChequeNo").value="";
							document.getElementById("startingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
		               
		               if((document.getElementById("endingChequeNo").value.length) < 6 ) {
							
							alert("Minimum length of Ending Instrument No should be 6.");
							document.getElementById("endingChequeNo").value="";
							document.getElementById("endingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
					/*if(endingChequeNo<startingChequeNo){
								
								alert(" Ending Instrument No must be greater than Starting Instrument No.");
								document.getElementById("endingChequeNo").value="";
								document.getElementById("endingChequeNo").focus();
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("save").removeAttribute("disabled");
								return false;
							}*/
				        
				        
				        
						if(endingChequeNo >= startingChequeNo )
						{
//							alert("Starting Cheque no. cannot be greater than Ending Cheque no.");
//							document.getElementById("endingChequeNo").value="";
//							document.getElementById("endingChequeNo").focus();
//							document.getElementById("save").removeAttribute("disabled");
//							return false;
							
							if((toInstallment - fromInstallment) != (endingChequeNo - startingChequeNo) )
							{
								
								alert("Cheques are not equivallent to Installments.");
								document.getElementById("endingChequeNo").value="";
								document.getElementById("startingChequeNo").value="";
								document.getElementById("startingChequeNo").focus();
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("save").removeAttribute("disabled");
								return false;
								
							}
						}
						
						if(diffOfInstumentNo > instDiffCount){
							alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
							document.getElementById("endingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;				
							}				
						
						if(endingChequeNo <= startingChequeNo )
						{
//							alert("Starting Cheque no. cannot be greater than Ending Cheque no.");
//							document.getElementById("endingChequeNo").value="";
//							document.getElementById("endingChequeNo").focus();
//							document.getElementById("save").removeAttribute("disabled");
//							return false;
							
							if((toInstallment - fromInstallment) != (startingChequeNo - endingChequeNo) )
							{
								
								alert("Cheques are not equivallent to Installments.");
								document.getElementById("endingChequeNo").value="";
								document.getElementById("startingChequeNo").value="";
								document.getElementById("startingChequeNo").focus();
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("save").removeAttribute("disabled");
								return false;
								
							}
						}
						if((document.getElementById("instrumentAmount").value) == '' ) {
							
							alert("Enter Instrument Amount");
							document.getElementById("instrumentAmount").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						if((document.getElementById("customeracType").value) == '' ) {
							
							alert("Select Customer A/C Type");
							document.getElementById("customeracType").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						if(customerAcType != 13 && alertStatusFromParameterMst=='Y') {
								
								if(!confirm("Warning ! Capturing of PDC allowed from CC/OD A/C type 13"))
								{
									DisButClass.prototype.EnbButMethod();
									//document.getElementById("save").removeAttribute("disabled");
									return false;
								}
								
				
								
							}
						
				
						var instrumentNumberAll = document.getElementsByName("startingChequeNoAll");
//						alert('instrumentNumberAll ' +instrumentNumberAll);
//						alert('startingChequeNo ' +startingChequeNo);
//						alert('endingChequeNo ' +endingChequeNo);
//						for(var i=startingChequeNo;i<=endingChequeNo;i++){	
////							alert('i ' +i + 'length ' +instrumentNumberAll.length);
//							for(var j=0;j< instrumentNumberAll.length;j++){
////								alert('j ' +j);
////								alert('(instrumentNumberAll[j].value) ' +instrumentNumberAll[j].value);
////								alert('(parseInt(instrumentNumberAll[j].value)) ' +(parseInt(instrumentNumberAll[j].value)));	
//								if((instrumentNumberAll[j].value) == i){
//									alert("Instrument No. "+instrumentNumberAll[j].value+" has already been Captured for this loan.");
//									DisButClass.prototype.EnbButMethod();
//									//document.getElementById("save").removeAttribute("disabled");
//									return false;
//								}
//								
//								
//							}
//							
//							
//							
//						}
						
					
						 var flag =0;
						 var add = 0;
						// alert("br for"+a);
						 var purposeListall = document.getElementsByName("purposeAll");
						 
					for(var i=0;i<installments.length;i++){	
							
						if((purposeListall[i].value) == 'INSTALLMENT')
						{
						
						if((installments[i].value) != (fromInstallment)){
							
							if(a > 0){										
								if((installments[i].value) == (fromInstallment + 1 )){
									
								flag = 0;
								add = 0;
								a = a-1;
								//alert("a"+a);
								fromInstallment = fromInstallment + 1;
								//alert("fromInstallment"+fromInstallment); 
								}
							}
						}
						
						
						if((installments[i].value) == (fromInstallment)  ){
							
							if(flag != 0){
//								alert("add------"+add);
//								alert(removeComma(instrumentAmountAll[i].value) + add );
								
								if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value) + add )){
									alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
									DisButClass.prototype.EnbButMethod();
									//document.getElementById("save").removeAttribute("disabled");
									return false;							
								}
								else{
									 
								//	  var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);
									
										//if(cnfrm){
											flag = flag + 1;
//											alert("flag---"+flag);
											 add = add + removeComma(instrumentAmountAll[i].value);
//											 alert("add----"+add);					
										//}else{
									//		document.getElementById("save").removeAttribute("disabled");
								//		return false;	
									//	}
									} 
							}else{
							
							if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value))){
								
								alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("save").removeAttribute("disabled");
								return false;
							}else{
								 
						  var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);
						
							if(cnfrm){
								flag = flag + 1;
//								alert("flag---"+flag);
								 add = add + removeComma(instrumentAmountAll[i].value);
//								 alert("add----"+add);					
							}else{
								DisButClass.prototype.EnbButMethod();
								//document.getElementById("save").removeAttribute("disabled");
							return false;	
							}
						} 
					}
							
					}
					}	
				}
					
			}else{
				
				
				 if((document.getElementById("startingChequeNo").value) == '' ) {
						
						alert("Enter Starting Instrument No");
						document.getElementById("startingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					}
					
			        if((document.getElementById("endingChequeNo").value) == '' ) {
						
						alert("Enter Ending Instrument No");
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					} 
			        
			        
			        if((document.getElementById("startingChequeNo").value.length) < 6 ) {
						
						alert("Minimum length of Starting Instrument No should be 6.");
						document.getElementById("startingChequeNo").value="";
						document.getElementById("startingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					}
	               
	               if((document.getElementById("endingChequeNo").value.length) < 6 ) {
						
						alert("Minimum length of Ending Instrument No should be 6.");
						document.getElementById("endingChequeNo").value="";
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					} 
			        
	       		
				/*	if(endingChequeNo<startingChequeNo){
						
						alert(" Ending Instrument No must be greater than Starting Instrument No.");
						document.getElementById("endingChequeNo").value="";
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					}*/
			        
			        
			        
			        
			        
			        
		        if((document.getElementById("instrumentAmount").value) == '' ) {
						
						alert("Enter Instrument Amount");
						document.getElementById("instrumentAmount").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;
					}
			        
			        
			        
				
				if((document.getElementById("customeracType").value) == '' ) {
					
					alert("Select Customer A/C Type");
					document.getElementById("customeracType").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
				
			if((document.getElementById("clearingType").value) == '' ) {
					
					alert("Select Clearing Type");
					document.getElementById("clearingType").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
				
				
				if((document.getElementById("purpose").value) == 'INDGUAR' ) {
					
				 	if(diffOfInstumentNo > instDiffCount){
						alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;				
						}

					if(customerAcType != 10 && alertStatusFromParameterMst=='Y') {
							
						if(!confirm("Warning ! Capturing of PDC allowed from Saving A/C type 10"))
						{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						
						
						}

				 
				
				
					
				}
				
				if((document.getElementById("purpose").value) == 'CORGUAR' ) {
					
				  	if(diffOfInstumentNo > instDiffCount){
						alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;				
						}

					if(customerAcType == 10 && alertStatusFromParameterMst=='Y') {
							
						if(!confirm("Warning ! Capturing of PDC allowed from Current or CC/OD A/C type 11 or 13"))
						{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
														
						}
				
					
				}
				
		         if((document.getElementById("purpose").value) == 'INS' ) {
								
				  	if(diffOfInstumentNo > instDiffCount){
						alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;				
						}

					if(customerAcType != 13 && alertStatusFromParameterMst=='Y') {
							
						
						if(!confirm("Warning ! Capturing of PDC allowed from CC/OD A/C type 13"))
						{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
						
						
							
						}
				
					
				}
				
		         if((document.getElementById("purpose").value) == 'SEC' ) {
		 			
		 			
		 		  	if(diffOfInstumentNo > instDiffCount){
						alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;				
						}

		 			if(customerAcType != 13 && alertStatusFromParameterMst=='Y') {
		 					
		 				
		 				if(!confirm("Warning ! Capturing of PDC allowed from CC/OD A/C type 13"))
						{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
		 				
		 					
		 				}
		 		
		 			
		 		}
				
		         if((document.getElementById("purpose").value) == 'WHLS' ) {
		  			
		  			
		  		  	if(diffOfInstumentNo > instDiffCount){
						alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
						document.getElementById("endingChequeNo").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
						return false;				
						}

		  			if(customerAcType != 13 && alertStatusFromParameterMst=='Y') {
		  					
		  				if(!confirm("Warning ! Capturing of PDC allowed from CC/OD A/C type 13"))
						{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;
						}
		  					
		  				}
		  		}
			}
		             }else{
			
			 if((document.getElementById("fromInstallment").value) == '' ) {
					
					alert("Enter From Installment");
					document.getElementById("fromInstallment").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
			 if((document.getElementById("fromInstallment").value) == 0 ) {
					
					alert("From Installment should be greater than 0");
					document.getElementById("fromInstallment").focus();
					document.getElementById("fromInstallment").value="";
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
		     if((document.getElementById("toInstallment").value) == '' ) {
					
					alert("Enter To Installments");
					document.getElementById("toInstallment").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}

		     if((document.getElementById("instrumentAmount").value) == '' ) {
					
					alert("Enter Instrument Amount");
					document.getElementById("instrumentAmount").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
		     if((document.getElementById("ecsTransactionCode").value) == '' ) {					
					
					alert("Select ECS Transaction Code");
					document.getElementById("ecsTransactionCode").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
				
		  if((document.getElementById("customeracType").value) == '' ) {
					
					alert("Select Customer A/C Type");
					document.getElementById("customeracType").focus();
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
		  
		 
		  
		  
		  if((document.getElementById("spnserBnkBrncCode").value) == '' ) {
				
				alert("Select Sponsor Bank Branch Code");
				document.getElementById("spnserBnkBrncCode").focus();
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			
		if((document.getElementById("utilityNo").value) == '' ) {
				
				alert("Select Utility no");
				document.getElementById("utilityNo").focus();
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			


		if(installments.length == 0){
				 //alert(fromInstallment);
				//alert(document.getElementById("loanInstallmentMode").value);
				 if((document.getElementById("loanInstallmentMode").value)=='A'){
					 //alert(document.getElementById("loanInstallmentMode").value);
					if(fromInstallment == 1){
						alert("Your first installment is already received.Please generate it from second.");
						document.getElementById("fromInstallment").value='';
						document.getElementById("fromInstallment").focus();
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("save").removeAttribute("disabled");
					return false;
					}
					 
				 }
			  } 
	
		if(customerAcType != 13 && alertStatusFromParameterMst=='Y') {
				
			if(!confirm("Warning ! Capturing of ECS allowed from CC/OD A/C type 13"))
			{
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
				return false;
			}
			}

		var flag =0;
		var add = 0;

		for(var i=0;i<installments.length;i++)
		{		

		if((installments[i].value) == (fromInstallment) ){
			
			if(flag != 0){
//				alert("add------"+add);
//				alert(removeComma(instrumentAmountAll[i].value) + add );
				
				if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value) + add )){
					alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;							
				}
				else{
					 
					  var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);
					
						if(cnfrm){
							flag = flag + 1;
//							alert("flag---"+flag);
							 add = add + removeComma(instrumentAmountAll[i].value);
//							 alert("add----"+add);					
						}else{
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
						return false;	
						}
					} 
			}else{
			  
			if((removeComma(installmentAmountAll[i].value)) <= (removeComma(instrumentAmountAll[i].value))){
				
				alert("You can not generate PDC for same Installment "+installments[i].value+" as your Installment amount is equal to Instrument Amount.");
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
				return false;
			}else{
				 
		 var cnfrm =confirm("Do you want to generate PDC for same installment----"+installments[i].value);

			if(cnfrm){
				flag = flag + 1;
//				alert("flag---"+flag);
				 add = add + removeComma(instrumentAmountAll[i].value);
//				 alert("add----"+add);					
			}else{
				DisButClass.prototype.EnbButMethod();
				//document.getElementById("save").removeAttribute("disabled");
			return false;	
			}
		} 
		}
			
		}

		}
			
			
		}
					if((document.getElementById("purpose").value) == 'PRE EMI' )
					{
			  		  	if(diffOfInstumentNo > instDiffCount){
							alert("Difference of starting instrument no and ending instrument no must be equal or less than " +instDiffCount);
							document.getElementById("endingChequeNo").focus();
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("save").removeAttribute("disabled");
							return false;				
							}				
//			  		  	if(purpose=='PRE EMI' && instrumentPreEmiDate =='' ) {
//							
//							alert("Instrument Date is required.");
//							document.getElementById("instrumentPreEmiDate").focus();
//							document.getElementById("instrumentPreEmiDate").value="";
//							DisButClass.prototype.EnbButMethod();
//							document.getElementById("save").removeAttribute("disabled");
//							return false;
//
//			  		  	}
					}

					 var id =document.getElementById("lbxLoanNoHID").value;
					 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessAction.do?method=generatePDC&id="+id;
				     document.getElementById("processingImage").style.display = '';
					 document.getElementById("sourcingForm").submit();
				     return true;
		 }
				else
				{
					DisButClass.prototype.EnbButMethod();
					//document.getElementById("save").removeAttribute("disabled");
					return false;
				}
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
			
			   function fnDetailsforLinkedlan(id,date,installmentAmount,instrumentAmount,instrumentID,installmentNo)
			   {  
			   	
			    	var contextPath=document.getElementById("contextPath").value; 
			      var loanAccNo= document.getElementById("loanAccNo").value; 
			    //  var totalInstallments=document.getElementById("totalInstallments").value;
			      var restAllocatedAmount = removeComma(instrumentAmount) - removeComma(installmentAmount);
			   	
			      if(!window.child || window.child.close){
			       window.child = window.open(""+contextPath+"/instrumentCapProcessActionforNew.do?method=openWindowForLnkedLoan&id="+id+"&installmentAmount="+installmentAmount+"&date="+date+"&loanAccNo="+loanAccNo+"&installmentNo="+installmentNo+"&instrumentAmount="+instrumentAmount+"&instrumentID="+instrumentID+"&restAllocatedAmount="+restAllocatedAmount+"",'ProductPopUP',"height=250,width=950,status=yes,toolbar=no,menubar=no,location=center");
			   }
			   }
			   function savenForPDCInstrument(alert1,fwdMsg){
				    DisButClass.prototype.DisButMethod();
				    if(!confirm(fwdMsg))	 
				    {
				       	DisButClass.prototype.EnbButMethod();
				    	return false;
				    }
				   	var contextPath=document.getElementById("contextPath").value;
		            var id=document.getElementById('lbxLoanNoHID').value;
				   
				   	var ch = document.getElementsByName("chk");
				   	var pdcDate = document.getElementsByName("pdcDate");
				   	
//				   	alert(pdcDate);
				   	var dateOne = document.getElementsByName("dateOne");
				   	var formatD = document.getElementById("formatD").value;
				   	var seprator = formatD.substring(2, 3);
				   	var checked ="";
				   	var checkedDate="";
				   	var purp=document.getElementsByName("purposeAll"); 
//				   	alert(ch.length);
				   	for(i=0;i<ch.length;i++){

//				   	alert("pdc---"+pdcDate[i].value);
//				   	alert("date1---"+dateOne[i].value);
				   		if((purp[i].value) == 'INSTALLMENT'){
				   			
					   		if(getDateObject(pdcDate[i].value, seprator) > getDateObject(dateOne[i].value, seprator)){
					   			
					   			alert("PDC Date should be equal to or less than Due Date.");
					   			DisButClass.prototype.EnbButMethod();
					   			//document.getElementById("savenfor").removeAttribute("disabled");
					   			return false;
					   			
					   		}
				   		}
				   		
				   				   checked =  checked + ch[i].value +"/";
	//			   				   alert("checked"+checked);
				   				  
				   				checkedDate = checkedDate + pdcDate[i].value +"/";
	//			   			 alert("checkedDate"+checkedDate);

				   	}
				   	
//				   	alert("---"+document.getElementById('lbxLoanNoHID').value);
		
				   	
				   	
				   
				   	
				   	var totalInstallmentsA = parseInt(document.getElementById("totalInstallments").value);
				   	var pdcPartialForward = document.getElementById("pdcPartialForward").value;
				   	var installments =	 document.getElementsByName("installments");
				   	var loanAdvanceInstallment = parseInt(document.getElementById("loanAdvanceInstallment").value);
				   	//alert("pdcPartialForward--"+pdcPartialForward);
				   
				   	var flag = 0;
				   	var flagg = 0;
				   	if(pdcPartialForward == 'N'){
				   		
				   		if((document.getElementById("loanInstallmentMode").value)=='A'){
				   			
				   		for(var i=0;i<installments.length;i++){
				   			
				   			var a = parseInt(installments[i].value);
//				   			alert("installments--"+a);
				   			//alert("aaaaaaaa--"+totalInstallmentsA);
				   			
				   			
				   			if( a <= totalInstallmentsA){
				   				
	                       if(a!=flag){
	                       	
	                         flag =parseInt(installments[i].value);
	                         
	                         flagg = flagg + 1;
				   				//flag = flag+ 1;
				   				//alert("flag==="+flag);
				   				//alert("flagg==="+flagg);
				   				}
				   				
				   			}
				   					   			
				   		}
				   		
				   		if((totalInstallmentsA) != (flagg + 1 + loanAdvanceInstallment )){
				   		
				   			alert("Please capture PDC/ECS for all installments");
				   			DisButClass.prototype.EnbButMethod();
				   			//document.getElementById("savenfor").removeAttribute("disabled");
				   			return false;
				   		}
				   		
				   		}else{
				   			
				   			for(var i=0;i<installments.length;i++){
					   			
					   			var a = parseInt(installments[i].value);
//					   			alert("installments--"+a);
//					   			alert("installments--"+totalInstallmentsA);
					   			
					   			
					   			if( a <= totalInstallmentsA){
					   				
		                        if(a!=flag){
		                        	
		                          flag =parseInt(installments[i].value);
		                          
		                          flagg = flagg + 1;
					   				//flag = flag+ 1;
//					   				alert("flag==="+flag);
//					   				alert("flagg==="+flagg);
					   				}
					   				
					   			}
					   					   			
					   		}
					   		
					   		if((totalInstallmentsA) != (flagg + loanAdvanceInstallment)){
					   		
					   			alert("Please capture PDC/ECS for all installments");
					   			DisButClass.prototype.EnbButMethod();
					   			//document.getElementById("savenfor").removeAttribute("disabled");
					   			return false;
					   		}
				   			
				   		}
				   	}
				   	
				   	
				   	
				   	
		if(pdcPartialForward == 'N'){
			
				   		
		        var installments =	 document.getElementsByName("installments");
				var instrumentAmountAll = document.getElementsByName("instrumentAmountAll");
				var installmentAmountAll = document.getElementsByName("installmentAmountAll");
				
				var instrumentAmountAdd=0;
				var installmentAmountAdd=0;			
				 var purposeListAll = document.getElementsByName("purposeAll");
				 var flag =0;
				 var add = 0;
				 var k=0;
				 
			for(var i=0;i<installments.length;i++){
				
				if((purposeListAll[i].value) == 'INSTALLMENT'){
				var j=installments[k].value;
				var intallAmmount=removeComma(installmentAmountAll[k].value);
				
				if(i<installments.length-1){
					//alert("value of i :-"+i);
				if(removeComma(installments[i].value)==removeComma(installments[i+1].value) ){
				//alert(installments[i].value +" and  "+ installments[i+1].value+ "Are equal and i is"+i);
				
					instrumentAmountAdd = instrumentAmountAdd + removeComma(instrumentAmountAll[i].value);
					//alert(i+"    instrumentAmountAdd--in if main -"+instrumentAmountAdd);
					
				}else{
					instrumentAmountAdd=instrumentAmountAdd + removeComma(instrumentAmountAll[i].value);
					//alert(i+"    instrumentAmountAdd--in else before if -"+instrumentAmountAdd);
					//alert(i+"    installmentAmountAll--in else before if -"+installmentAmountAll[i].value);
					if(removeComma(installmentAmountAll[i].value)<=instrumentAmountAdd){
					//	alert(" intallAmmount ids eqal to sum of insrtment ammont");
						
					}else{
						alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("savenfor").removeAttribute("disabled");
				   		return false;
					}
					k=i+1;
					//alert("Value of k:-"+k);
					instrumentAmountAdd=0;
				}
			
				}
				if(i == installments.length-1 ){
					//alert(i +" i is and length is:-" +installments.length);
					if(installments.length>1){
					if(removeComma(installments[i].value)!=removeComma(installments[i-1].value) ){
					if(removeComma(installmentAmountAll[i].value)>removeComma(instrumentAmountAll[i].value)){
						alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
						DisButClass.prototype.EnbButMethod();
						//document.getElementById("savenfor").removeAttribute("disabled");
				   		return false;
					}
				}
					}else{
						if(removeComma(installmentAmountAll[i].value)>removeComma(instrumentAmountAll[i].value)){
							alert(" Installment Amount is greater than Instrument amount for Installment no "+installments[i].value);
							DisButClass.prototype.EnbButMethod();
							//document.getElementById("savenfor").removeAttribute("disabled");
					   		return false;
						}
					}
				}
				
			}	
			}
			
	 	}
				   	
				  	
		        var purposeList = document.getElementsByName("purposeAll");
		        var instrumentTypeList = document.getElementsByName("instrumentTypeAll");
		        var ecsFlag=0;
		        var purposeFlag=0;
		        var wholeFlag=0;
		        var corGurantee=0;
		        var indGuarantee=0;
		        var insurance=0;
		        var installmenttt=0;
		        
		            for(var i =0;i<purposeList.length;i++){
		            	
		            	
		            	if((instrumentTypeList[i].value) == 'E'){
		            		ecsFlag=1;
		            		}
		            	
		            	if((purposeList[i].value) == 'SECURITY'){
		            		purposeFlag=1;
		            	}
		            	
		            	if((purposeList[i].value) == 'WHOLE AMOUNT'){
		            		wholeFlag=1;
		            	}
		            	
		            	if((purposeList[i].value) == 'COR GUARANTEE'){
		            		corGurantee=1;
		            	}
		            	
		            	if((purposeList[i].value) == 'IND GUARANTEE'){
		            		indGuarantee=1;
		            	}
		            	
		            	if((purposeList[i].value) == 'INSURANCE'){
		            		insurance=1;
		            	}
		            	if((purposeList[i].value) == 'INSTALLMENT'){
		            		installmenttt=1;
		            	}
		            	
		            	
		            }

		         
		            if(ecsFlag == 1){
		            	
		            	if(purposeFlag != 1){
		            		
		            		if(!confirm("Please Capture Security PDC. "))
		            		{
		            			DisButClass.prototype.EnbButMethod();
			            		//document.getElementById("savenfor").removeAttribute("disabled");
			            		return false;
		            		}
		            		
		            	}
		            	
		            	if(wholeFlag != 1){
		            		
		            		if(!confirm("Please Capture Whole Amount PDC. "))
		            		{
		            			DisButClass.prototype.EnbButMethod();
			            		//document.getElementById("savenfor").removeAttribute("disabled");
			            		return false;
		            		}
		            		
		            	}
		            	
		            }
		    
		            /*if(purposeFlag == 1){
		            	alert("installmenttt->"+installmenttt);
		            	if(installmenttt != 1){
		            		alert("--------security-------");
		            		alert("Please Capture PDC/ECS. ");
		            		document.getElementById("savenfor").removeAttribute("disabled");
		            		return false;
		            	}
		            	
		            	
		            }*/
		            
	               if(wholeFlag == 1){
	            	  
		            	if(installmenttt != 1){
		            		
		            		alert("Please Capture PDC/ECS. ");
		            		//DisButClass.prototype.EnbButMethod();
		            		//document.getElementById("savenfor").removeAttribute("disabled");
		            		//return false;
		            	}
		            	
		            	
		            }

	             if(corGurantee == 1){
	            	
		                if(installmenttt != 1){
		                	
			 		alert("Please Capture PDC/ECS. ");
			 		//DisButClass.prototype.EnbButMethod();
			 		//document.getElementById("savenfor").removeAttribute("disabled");
			 		//return false;
			 	}
			 	
			 	
	             }

				 if(indGuarantee == 1){
					 
				 	if(installmenttt != 1){
				 		
				 		alert("Please Capture PDC/ECS. ");
				 		//DisButClass.prototype.EnbButMethod();
				 		//document.getElementById("savenfor").removeAttribute("disabled");
				 		//return false;
				 	}
				 	
				 	
				 }

					 if(insurance == 1){
						
					 	if(installmenttt != 1){
					 		
					 		alert("Please Capture PDC/ECS. ");
					 		//DisButClass.prototype.EnbButMethod();
					 		//document.getElementById("savenfor").removeAttribute("disabled");
					 		//return false;
					 	}
					 	
					 	
					 }
					 
	            	
		
				   	if((document.getElementById('lbxLoanNoHID').value =='')){
				   		alert(alert1);
				   		DisButClass.prototype.EnbButMethod();
				   		//document.getElementById("savenfor").removeAttribute("disabled");
				   		return false;
				   	}
				   else{
				  // alert("checked"+checked);
				 //  alert("checkedDate"+checkedDate);
				 //  alert("id"+id);
				   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savenForPDCInstrument&id="+id+"&checkedDate="+checkedDate+"&checked="+checked;
				   	     document.getElementById("processingImage").style.display = '';
				   		 document.getElementById("sourcingForm").submit();
				   	     return true;
				   	}
				   }
			   function deletePDCInstrument(alert1){
				   DisButClass.prototype.DisButMethod();
				   	var contextPath=document.getElementById("contextPath").value;
				   //	alert("contextPath"+contextPath);
				   	var loanid = document.getElementById("lbxLoanNoHID").value;
				   
				   	var flag=0;
				   	 var ch=document.getElementsByName('chk');
				   	 for(i=0;i<ch.length;i++)
				   		{
				   		   if(ch[i].checked==true)
				   			{
				   			
				   			  id=ch[i].value;
				   			  flag=1;
				   			  break;	
				   			}
				   		  
				   		}
				   	 if(flag==0)
				   	 {
				   		 alert(alert1);
				   		 DisButClass.prototype.EnbButMethod();
				   		//document.getElementById("del").removeAttribute("disabled");
				   		 return false;
				   	 }
				   	 if(!confirm("Are You Sure to Delete Instrument ?"))
				   	 {
				   		 DisButClass.prototype.EnbButMethod();
					   		//document.getElementById("del").removeAttribute("disabled");
					   	 return false;
				   	 }
				   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=deleteInstrument&id="+loanid;
				   	     document.getElementById("processingImage").style.display = '';
				   		 document.getElementById("sourcingForm").submit();
				   	     return true;
				   	
				   }
			
		
				function generateValues(){
					//alert("hi");
					 var flag=0;
					 var ch=document.getElementsByName('lbxLoanNoHID');
					 for(i=0;i<ch.length;i++)
						{
						   if(ch[i].checked==true)
							{
							
							  id=ch[i].value;
							  flag=1;
							  break;	
							}
						  
						}
					 if(flag==0)
					 {
						 alert("Please Select one Record!!!");
						 return false;
					 }
					 else
					 {
						 var contextPath=document.getElementById("contextPath").value;
						 var address = contextPath+"/ajaxAction.do?method=retriveCutInsValues";
					   
						 var data = "lbxLoanNoHID=" + id;
						 sendid(address, data);
						 
					        return true;
					 }
					 
				}
				
				
				 function saveLinkedLoan()
				   {
				   	var allotedAmountAdd=0;
				   	var contextPath=document.getElementById("contextPath").value; 
				   	var instrumentAmount = removeComma(document.getElementById("instrumentAmount").value); 
				   	var instrumentID = document.getElementById("instrumentID").value; 
				   	
				   	var loanId = "";
				   	var date ="";
				   	var totalInstallments="";
				   	var installmentAmountMain="";
				   	var allotedAmount="";
				   	
				    var allotedAmount1 = document.getElementById("allotedAmount1").value;
				  	var allotedAmount2=document.getElementById("allotedAmount2").value;
					var allotedAmount3=document.getElementById("allotedAmount3").value;
				    var installmentNo="";
				   	
				   	
				   	
				   	
				  
				   if(document.getElementById("lbxLoanNoHID").value == ""){
					   alert("Please select atleast one loan.");
					   return false;
				   }
				   	
				  if(document.getElementById("allotedAmount1").value == ""){
					  alert("Please allocate the amount");
					  return false;
				  }
				   	
				   if(document.getElementById("lbxLoanNoHID1").value != ""){
					   
					   if(document.getElementById("allotedAmount2").value == ""){
						   alert("Please allocate the amount");
						   return false;
					   }
				   }
				   	
			       if(document.getElementById("lbxLoanNoHID2").value != ""){
					   
					   if(document.getElementById("allotedAmount3").value == ""){
						   alert("Please allocate the amount");
						   return false;
					   }
				   }
				       if(allotedAmount1){
				   		
				   	 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount1) ;
				   	}
				   	
				   	if(allotedAmount2){
				   			
				   		 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount2) ;
				   	}
				   
				   	if(allotedAmount3){
				   		
				   		 allotedAmountAdd = allotedAmountAdd + removeComma(allotedAmount3) ;
				   	}
				   	
				 
				  
				       if((document.getElementById("lbxLoanNoHID").value) != ''){
				   		
				   		loanId = loanId + document.getElementById("lbxLoanNoHID").value + "/";
				   		
				   	}
				     if((document.getElementById("lbxLoanNoHID1").value) != ''){
				   		
				   		loanId = loanId + document.getElementById("lbxLoanNoHID1").value + "/";
				   		
				   	}
				   	
				     if((document.getElementById("lbxLoanNoHID2").value) != ''){
				   		
				   		loanId = loanId + document.getElementById("lbxLoanNoHID2").value + "/";
				   		
				   	}
				   	
				  

				       if((document.getElementById("date").value) != ''){
				   		
				       	date = date + document.getElementById("date").value + "/";
				   		
				   	}
				     if((document.getElementById("date1").value) != ''){
				   		
				   	  date = date + document.getElementById("date1").value + "/";
				   		
				   	}
				   	
				     if((document.getElementById("date2").value) != ''){
				   		
				   	  date = date + document.getElementById("date2").value + "/";
				   		
				   	}
				
				   	
				   	
				       if((document.getElementById("installmentNo").value) != ''){
				   		
				       	installmentNo = installmentNo + document.getElementById("installmentNo").value + "/";
				   		
				   	}
				     if((document.getElementById("installmentNo1").value) != ''){
				   		
				   	  installmentNo = installmentNo + document.getElementById("installmentNo1").value + "/";
				   		
				   	}
				   	
				     if((document.getElementById("installmentNo2").value) != ''){
				   		
				   	  installmentNo = installmentNo + document.getElementById("installmentNo2").value + "/";
				   		
				   	}
				   	
				   	    if((document.getElementById("installmentAmount").value) != ''){
				   			
				   	    	installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount").value + "/";
				   			
				   		}
				   	  if((document.getElementById("installmentAmount1").value) != ''){
				   			
				   		  installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount1").value + "/";
				   			
				   		}
				   		
				   	  if((document.getElementById("installmentAmount2").value) != ''){
				   			
				   		  installmentAmountMain = installmentAmountMain + document.getElementById("installmentAmount2").value + "/";
				   			
				   		}
				   	

				   	    if((document.getElementById("allotedAmount1").value) != ''){
				   			
				   	    	allotedAmount = allotedAmount + document.getElementById("allotedAmount1").value + "/";
				   			
				   		}
				   	  if((document.getElementById("allotedAmount2").value) != ''){
				   			
				   		  allotedAmount = allotedAmount + document.getElementById("allotedAmount2").value + "/";
				   			
				   		}
				   		
				   	  if((document.getElementById("allotedAmount3").value) != ''){
				   			
				   		  allotedAmount = allotedAmount + document.getElementById("allotedAmount3").value + "/";
				   			
				   		}
				   	
				   	  
				   	 
				   	  
				   	  
				   	  
				   	 var restAllocatedAmount =  removeComma(document.getElementById("restAllocatedAmount").value);
				   	 
				   	 
				   	 
				   	 if( (document.getElementById("datemain").value) != (document.getElementById("date").value)){
				   		 alert("Date would be same");
				   		 return false;
				   		 
				   	 }
				   	 
				   	 if((document.getElementById("date1").value) != ""){
				   		 
				   	if( (document.getElementById("datemain").value) != (document.getElementById("date1").value)){
				   		 alert("Date would be same");
				   		 return false;
				   		 
				   	 }
				   	
				   	 }
				   	 if((document.getElementById("date2").value) != ""){
				   	
				   	if( (document.getElementById("datemain").value) != (document.getElementById("date2").value)){
				   		 alert("Date would be same");
				   		 return false;
				   	} 
				   	 }
				   	 
				   	 if((restAllocatedAmount) != (allotedAmountAdd)){
				   		 
				   		 alert("Allocated amount is not equal to amount left.");
				   		 return false;
				   		 
				   	 }
				   	
				 
				   	
				   	
				  document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=saveForLinkedLan&loanId="+loanId+"&date="+date+"&installmentNo="+installmentNo+"&installmentAmountMain="+installmentAmountMain+"&allotedAmount="+allotedAmount+"&instrumentID="+instrumentID;
				  document.getElementById("sourcingForm").submit();

				       
				       return true;
				 
				     
				   }
				 
				   function searchDeleteMaker(){
					    
				   	     var contextPath=document.getElementById("contextPath").value;

				   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')){
				   	   
				   		 alert("Please select atleast one criteria");
				   		document.getElementById("save").removeAttribute("disabled");
				   		return false;
				   	  
				   	 }else {
				   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchDeleteMaker";
				   	 document.getElementById("sourcingForm").submit();
				   	 		 return true;
				   	 }
				   }
				   
				   
				   function searchIndiDeleteInstrument(){
					   	 
				       var contextPath=document.getElementById("contextPath").value;
				       var id=document.getElementById('lbxLoanNoHID').value;
				   document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=searchIndiDeleteInstrument&loanID="+id;
				   document.getElementById("sourcingForm").submit();
				   		 return true;
				   
				   }
				   
				   function savenForPDCDeleteInstrument(alert1,alert2){
					   	var contextPath=document.getElementById("contextPath").value;
					   	var loanid = document.getElementById("lbxLoanNoHID").value;
					   	
					   
					   	var flag=0;
					   	var toggle=0;
					   	
					   	 var ch=document.getElementsByName('chk');
					   	 var holdReason= document.getElementsByName('holdReason');
					   	 var status = document.getElementsByName('status');
					   	 var instrumentID=document.getElementsByName('instrumentID');
					   	 var checked="";
					   	 var unchecked="";
					   	 var checkedhold="";
					   	 var uncheckedhold="";
					   	 var checkedStatus=""
					   	 var uncheckedStatus=""
					   	 var newStatus="";
					   	 var instrumentid="";
					   		
					   		 for(i=0;i<ch.length;i++){
					   			 
					   			 if(ch[i].checked==true){
					   				ch[i].value='X'; 
					   				 flag=1;
					   				instrumentid = instrumentid + instrumentID[i].value +"/";
					   				  
					   				  
					   				  newStatus=newStatus + ch[i].value +"/";
					   				
					   				  
					   				  if(holdReason[i].value==''){
					   					  alert("Please insert Delete Reason");
					   					  document.getElementById("savenfor").removeAttribute("disabled", "true");

					   				 return false;
					   				  }else{
					   				  checkedhold = checkedhold + holdReason[i].value +"/";
					   				
					   				  }
					   				  checkedStatus = checkedStatus + status[i].value +"/";
					   				 
					   			 }
					   			
					   				 
					   		 }
					   	 
					   	 if(flag==0)
					   	 {
					   		 alert(alert1);
					   		document.getElementById("savenfor").removeAttribute("disabled","true");
					   		 return false;
					   	 }
					   	 
					   	  
					   	
					   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savenForPDCDeleteInstrument&loanID="+loanid+"&checkedhold="+checkedhold+"&checkedStatus="+checkedStatus+"&instrumentid="+instrumentid+"&newStatus="+newStatus;
					   	     document.getElementById("sourcingForm").submit();
					   	     return true;
					   
					   }
				   
				   
				   function savedeleteInstrumentAuthor(comments,decision){

						
				  		var contextPath=document.getElementById("contextPath").value;
				  		
				  		if(document.getElementById("comments").value==""){
				  			   alert(comments);
				  			 document.getElementById("save").removeAttribute("disabled");
				  			   return false;
				  			   
				  		   }
				  		
				  		if((document.getElementById("comments").value.length) > 1000){
				   			alert("You are requested to enter only 1000 characters.");
				   			
				   			document.getElementById("save").removeAttribute("disabled");
					   		return false;
				   		}
				  	   if(document.getElementById("decision").value==""){
				  		   alert(decision);
				  		 document.getElementById("save").removeAttribute("disabled");
				  		   return false;
				  		   
				  	   }	
				  	   
				  	        document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=savedeleteInstrumentAuthor";
				  			 document.getElementById("sourcingForm").submit();
				  			 return true;
				  		
				  	}
				   
				   
				   function editInstrument(id){
					   
					   	var contextPath=document.getElementById("contextPath").value;
					   //	alert("contextPath"+contextPath);
					   //	alert("id"+id);
					   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=updateInstrument&loanID="+id;
					   	     document.getElementById("sourcingForm").submit();
					   	     return true;
					   	
					   }
				   function editInstrumentAuthor(id){
					   
					   	var contextPath=document.getElementById("contextPath").value;
					   //	alert("contextPath"+contextPath);
					   //	alert("id"+id);
					   		
					   	document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionforNew.do?method=instrumentAuthor&id="+id;
					       document.getElementById("sourcingForm").submit();
					       return true;
					   }
				   
				   
				   function saveInstrumentAuthor(){
					   //	alert("saveInstrumentAuthor");
					   	DisButClass.prototype.DisButMethod();	
					   	var contextPath=document.getElementById("contextPath").value;
					   //	alert("contextPath"+contextPath);
					   	var loanID=document.getElementById("loanID").value;
					   	
					   	if(validateInstrumentCapturingAuthorValidatorForm(document.getElementById("sourcingForm")))
					   	{
					   		if((document.getElementById("comments").value.length) > 1000){
					   			alert("You are requested to enter only 1000 characters.");
					   			DisButClass.prototype.EnbButMethod();
					   			//document.getElementById("save").removeAttribute("disabled");
						   		return false;
					   		}
					   		//Change start by Nishant Rai
					   		if(document.getElementById("comments").value==''&& !(document.getElementById("decision").value=='A')) //Edited by Nishant
						   	{
						   		alert("Comments is Required");
						   		document.getElementById("save").removeAttribute("disabled","true");
						   		document.getElementById("comments").focus();
						   		DisButClass.prototype.EnbButMethod();
						   		return false;
						   	}
					   		//Change end by Nishant Rai
					   		document.getElementById("sourcingForm").action = contextPath+"/instrumentCapProcessActionAuthor.do?method=saveAuthor&loanID="+loanID;
					   		document.getElementById("processingImage").style.display = '';
					   		document.getElementById("sourcingForm").submit();
					   		 return true;
					   	}
					   	else
					   	{
					   		DisButClass.prototype.EnbButMethod();
					   		//document.getElementById("save").removeAttribute("disabled");
					   		return false;
					   	}
					   	 
					   }   
				   
				   
				   
				   
				   //Ravindra
				   function editInstrumentForUpdate(id){
					   
					   	var contextPath=document.getElementById("contextPath").value;
					   //	alert("contextPath"+contextPath);
					   //	alert("id"+id);
					   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateMakerActions.do?method=updateInstrument&loanID="+id+"&fromIns=&toIns=&readyToForard=readyToForard";
					   	     document.getElementById("sourcingForm").submit();
					   	     return true;
					   	
					   }
				   
				   function searchInstrumentByInsNo(){
					   
					   	var contextPath=document.getElementById("contextPath").value;
					   //	alert("contextPath"+contextPath);
					   //	alert("id"+id);
					   	var id=document.getElementById("lbxLoanNoHID").value;
					   	var fromIns=document.getElementById("fromInstallment").value;
					   	var toIns=document.getElementById("toInstallment").value;
					   	var insMode=document.getElementById("instrumentType").value;
					   	var purpose = document.getElementById("purpose").value;
					   //	alert("purpose : "+purpose);
					   	if(id=='')
				   		{
				   			alert("Please select Loan no.")
				   			return false;
				   		}
					   	
					   		if(document.getElementById("instrumentType").value=='')
					   		{
					   			alert("Please select Instrument Type")
					   			return false;
					   		}
					   		if(purpose=='PRE EMI')
					   		{
					   			fromIns ='';
					   			toIns='';
					   		}
					   		else
					   		{
					   			purpose='';
					   		}
					   		
					
					   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateMakerActions.do?method=searchInstrument&loanID="+id+"&fromIns="+fromIns+"&toIns="+toIns+"&insMode="+insMode+"&purpose="+purpose;
					   	     document.getElementById("sourcingForm").submit();
					   	     return true;
					   	
					   }
				   
				   
				   

					function UpdateInstrument(alert1){					
						
						DisButClass.prototype.DisButMethod();
						
							//var id =document.getElementById("lbxLoanNoHID").value;
							var contextPath =document.getElementById("contextPath").value;	
							
							if(document.getElementById("instrumentType").value=='')
						   		{
						   			alert("Please select Instrument Type")
						   			DisButClass.prototype.EnbButMethod();
						   			document.getElementById("save").removeAttribute("disabled");
						   			return false;
						   		}
							
							var flag=0;
						   	 var ch=document.getElementsByName('chk');
						   	 for(i=0;i<ch.length;i++)
						   		{
						   		   if(ch[i].checked==true)
						   			{
						   			
						   			  id=ch[i].value;
						   			  flag=1;
						   			  break;	
						   			}
						   		  
						   		}
						   	 if(flag==0)
						   	 {
						   		 alert(alert1);
						   		 DisButClass.prototype.EnbButMethod();
						   		//document.getElementById("del").removeAttribute("disabled");
						   		 return false;
						   	 }
						   	 if(!confirm("Are You Sure to Update Instrument ?"))
						   	 {
						   		 DisButClass.prototype.EnbButMethod();
							   		//document.getElementById("del").removeAttribute("disabled");
							   	 return false;
						   	 }
							
							 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessAction.do?method=UpdatePDC&id="+id;	
							 document.getElementById("sourcingForm").submit();
						     return true;
				
					}
					
					   function newUpdateInstrument(){
						   	DisButClass.prototype.DisButMethod();
						   	var contextPath=document.getElementById("contextPath").value;
						   	document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessActionforNew.do?method=newUpdateInstrument";
						   	document.getElementById("processingImage").style.display = '';
						   	document.getElementById("sourcingForm").submit();
						   	     return true;
						   	
						   }
				   
				   
					   function insertInstrumentIntoTemp(alert1)
					   {
						   DisButClass.prototype.DisButMethod();
							
							//var id =document.getElementById("lbxLoanNoHID").value;
						   var id="";
							var contextPath =document.getElementById("contextPath").value;	
							
							if(document.getElementById("lbxLoanNoHID").value=='')
					   		{
					   			alert("Please select Loan")
					   			DisButClass.prototype.EnbButMethod();
					   			document.getElementById("save").removeAttribute("disabled");
					   			return false;
					   		}
							
							if(document.getElementById("instrumentType").value=='')
						   		{
						   			alert("Please select Instrument Type")
						   			DisButClass.prototype.EnbButMethod();
						   			document.getElementById("save").removeAttribute("disabled");
						   			return false;
						   		}
							//Nishant space starts
							var purpose = document.getElementById("purpose").value;
						//	var startingChequeNo = document.getElementById("startingChequeNo").value;
						//	var endingChequeNo = document.getElementById("endingChequeNo").value;
							//Nishant space ends
							if(document.getElementById("lbxBankID").value!='' && document.getElementById("lbxBranchID").value=='')
					   		{
					   			alert("Please select bank branch");
					   			DisButClass.prototype.EnbButMethod();
					   			document.getElementById("save").removeAttribute("disabled");
					   			return false;
					   		}
							
							var flag=0;
						   	 var ch=document.getElementsByName('chk');
						   	//alert("ch.length : "+ch.length);
						   	//alert("flag11 : "+flag);
						   	 for(i=0;i<ch.length;i++)
						   		{
						   		   if(ch[i].checked==true)
						   			{
						   			
						   			  id=id+"'"+ch[i].value+"',";
						   			   flag=1;
						   			//alert("id : "+id);
						   			}
						   		}
						   //	alert("flag12 : "+flag);
						   	 if(flag==0)
						   	 {
						   		 alert(alert1);
						   		 DisButClass.prototype.EnbButMethod();
						   		//document.getElementById("del").removeAttribute("disabled");
						   		 return false;
						   	 }
						   
							  	var pdcDate = document.getElementsByName("pdcDate");
								var checked ="";
								var checkedDate="";
							
					            for(i=0;i<ch.length;i++)
								{
					            
								    checked =  checked + ch[i].value +"/";
						   			checkedDate = checkedDate + pdcDate[i].value +"/";
								}
					
							 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessAction.do?method=insertInstrumentIntoTemp&id="+id+"&checkedDate="+checkedDate+"&checked="+checked;	
							 document.getElementById("sourcingForm").submit();
						     return true;
					   }
				   
				 
				 
					   function forwardPDCInstrument(alert1,fwdMsg){
						    DisButClass.prototype.DisButMethod();
						    if(!confirm(fwdMsg))	 
						    {
						       	DisButClass.prototype.EnbButMethod();
						    	return false;
						    }
						   	var contextPath=document.getElementById("contextPath").value;
				            var id=document.getElementById('lbxLoanNoHID').value;
						   
						   	var ch = document.getElementsByName("chk");
						 
						   	if(document.getElementById("readyToForard").value=='')
					   		{
					   			alert("Please Save First Then Forward");
					   			DisButClass.prototype.EnbButMethod();
					   			document.getElementById("savenfor").removeAttribute("disabled");
					   			return false;
					   		}
						   
						   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateMakerActions.do?method=forwardPDCInstrument&id="+id;
						   	     document.getElementById("processingImage").style.display = '';
						   		 document.getElementById("sourcingForm").submit();
						   	     return true;
						   	}
						

				 
					   function deleteInstrument(alert1){
						   DisButClass.prototype.DisButMethod();
						   	var contextPath=document.getElementById("contextPath").value;
						   //	alert("contextPath"+contextPath);
						   	var loanid = document.getElementById("lbxLoanNoHID").value;
						   
						   	var flag=0;
						   	 var ch=document.getElementsByName('chk');
						   	 for(i=0;i<ch.length;i++)
						   		{
						   		   if(ch[i].checked==true)
						   			{
						   			
						   			  id=ch[i].value;
						   			  flag=1;
						   			  break;	
						   			}
						   		  
						   		}
						   	 if(flag==0)
						   	 {
						   		 alert(alert1);
						   		 DisButClass.prototype.EnbButMethod();
						   		//document.getElementById("del").removeAttribute("disabled");
						   		 return false;
						   	 }
						   	 if(!confirm("Are You Sure to Delete Instrument ?"))
						   	 {
						   		 DisButClass.prototype.EnbButMethod();
							   		//document.getElementById("del").removeAttribute("disabled");
							   	 return false;
						   	 }
						   		 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateMakerActions.do?method=deleteInstrument&id="+loanid;
						   	     document.getElementById("processingImage").style.display = '';
						   		 document.getElementById("sourcingForm").submit();
						   	     return true;
						   	
						   }
				
					   function editUpdatedInstrumentAuthor(id){
						   
						   	var contextPath=document.getElementById("contextPath").value;
						   //	alert("contextPath"+contextPath);
						   //	alert("id"+id);
						   		
						   	document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessActionforNew.do?method=instrumentUpdatedAuthor&id="+id;
						       document.getElementById("sourcingForm").submit();
						       return true;
						   }
					   
					   
					   function saveUpdateInstrumentAuthor(){
						   //	alert("saveInstrumentAuthor");
						   	DisButClass.prototype.DisButMethod();	
						   	var contextPath=document.getElementById("contextPath").value;
						   //	alert("contextPath"+contextPath);
						   	var loanID=document.getElementById("loanID").value;
						   	
						   	if(validateInstrumentCapturingAuthorValidatorForm(document.getElementById("sourcingForm")))
						   	{
						   		if((document.getElementById("comments").value.length) > 1000){
						   			alert("You are requested to enter only 1000 characters.");
						   			DisButClass.prototype.EnbButMethod();
						   			//document.getElementById("save").removeAttribute("disabled");
							   		return false;
						   		}
						   		//Change start by Nishant Rai
						   		if(document.getElementById("comments").value==''&& !(document.getElementById("decision").value=='A')) //Edited by Nishant
							   	{
							   		alert("Comments is Required");
							   		document.getElementById("save").removeAttribute("disabled","true");
							   		document.getElementById("comments").focus();
							   		DisButClass.prototype.EnbButMethod();
							   		return false;
							   	}
						   		//Change end by Nishant Rai
						   		document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateActionAuthor.do?method=saveUpdateInstrumentAuthor&loanID="+loanID;
						   		document.getElementById("processingImage").style.display = '';
						   		document.getElementById("sourcingForm").submit();
						   		 return true;
						   	}
						   	else
						   	{
						   		DisButClass.prototype.EnbButMethod();
						   		//document.getElementById("save").removeAttribute("disabled");
						   		return false;
						   	}
						   	 
						   } 
					   
					   
					   function searchUpdateMakerInstrument(){
						     DisButClass.prototype.DisButMethod();
					   	     var contextPath=document.getElementById("contextPath").value;
					   	     var reportingToUserId=document.getElementById("reportingToUserId").value;
					   //	     alert("ok");
					   	 if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
					   	   
						   		 alert("Please select atleast one criteria");
						   		 DisButClass.prototype.EnbButMethod();
						   		 document.getElementById("save").removeAttribute("disabled");
						   		 return false;
					   	 }else {
							   	 document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessActionforNew.do?method=searchInstrumentAtUpdateMaker";
							   	 document.getElementById("processingImage").style.display = '';
							   	 document.getElementById("sourcingForm").submit();
					   	 		 return true;
					   	 }
					   }

	//Anil   
					   
		function getPDCSubmitCustomerName(){
//			alert("In getPDCSubmitCustomerName");
			var contextPath =document.getElementById('contextPath').value ;
			var submitBy= document.getElementById("submitBy").value;
			var loanAccNo=document.getElementById("loanAccNo").value;
			var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
	    
			if(loanAccNo!='' || lbxLoanNoHID!=''){
				var address = contextPath+"/instrumentCapProcessActionforNew.do?method=getNameOfPDCSubmitBy";
				var data = "lbxLoanNoHID="+lbxLoanNoHID+"&submitBy="+submitBy;
				send_PDCSubmitCustomerName(address,data);
						
			}
			else{
				alert("Please select Loan Number");
				
			}
	
			return true;		
		}
		function send_PDCSubmitCustomerName(address, data) {
			//alert(address+""+data);
			var request = getRequestObject();
			request.onreadystatechange = function () {
				result_PDCSubmitCustomerName(request);
			};
			request.open("POST", address, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(data);
		}


		function result_PDCSubmitCustomerName(request){
			if ((request.readyState == 4) && (request.status == 200)) {
				var str = request.responseText;	
			    var s1 = str.split("$:");
			   // alert("s1:::::"+s1.length)
			     document.getElementById('s4').innerHTML=str;
			     if(document.getElementById("submitBy").value=="OTHER")
			     {
			    	 document.getElementById("s5").style.display="";
			    	 document.getElementById("s4").style.display="none";
			     }
			     else
			     {
			    	 document.getElementById("s4").style.display="";
			    	 document.getElementById("s5").style.display="none";
			     }
//			    if(s1.length==1){
//			       document.getElementById('pdcSubmitCustomerName').value=""; 
//			    }else{
//			    	document.getElementById('pdcSubmitCustomerName').value=trim(s1[1]);  
//			    }
			}
			
		}
  						function disableFromToInUpdateInstrument()
					   {
					   	
					   	
					   	if((document.getElementById("purpose").value)=="PDC"  || (document.getElementById("purpose").value)=="" ){
					   		
					   		
					   		document.getElementById("fromInstallment").readOnly=false;
					   		document.getElementById("fromInstallment").value='';
					   		document.getElementById("toInstallment").readOnly=false;
					   		document.getElementById("toInstallment").value='';
					   		
					   		
					   	}else{
					   		
					   		document.getElementById("fromInstallment").readOnly=true;
					   		document.getElementById("fromInstallment").value=0;
					   		document.getElementById("toInstallment").readOnly=true;
					   		document.getElementById("toInstallment").value=0;
					   		
					   	}
					   	
					/*    if(document.getElementById("purpose").value=="PRE EMI" )
					   	{
					   		document.getElementById("instrumetAmountLabel").style.display='';
					   		document.getElementById("instrumetAmountField").style.display='';
					   		
					   	}else{
					   		
					   		document.getElementById("instrumetAmountLabel").style.display='none';
					   		document.getElementById("instrumetAmountField").style.display='none';
					   	}	*/
					   	
//					   	if(document.getElementById("purpose").value=="PRE EMI")
//					   	{
//					   		document.getElementById("instrumentPreEmiDate").value='';
//					   		document.getElementById("preemiDateLebel").style.display='';
//					   		document.getElementById("preemiDate").style.display='';
//					   		
//					   	}
//					   	else
//					   	{
//					   		document.getElementById("instrumentPreEmiDate").value='';
//					   		document.getElementById("preemiDateLebel").style.display='none';
//					   		document.getElementById("preemiDate").style.display='none';
//					   	}
//					   	
					   	
					  }  
		
  					function isSpecialChar(evt) 
  					{
  						var charCode = (evt.which) ? evt.which : event.keyCode;
  						if (charCode == 47)
  						{
  							alert("Special Charactor / not Allowed Here.");
  							return false;
  						}
  						return true;
  					}
		
		function select()
		{
			var select= document.getElementById("purpose").value
			var repayment= document.getElementById("repayment").value
			
			if(select=='PDC'&& repayment=='N')
			{
				document.getElementById("purpose").value = "";
				alert('You cannot take installment for this loan as it is non-installment Type');
			}
			
		}
		
		//Nishant space starts
function ecsFunctionForUpdate(){
				if((document.getElementById("instrumentType").value) == "E" || (document.getElementById("instrumentType").value) == "H"){
				document.getElementById("ecs").style.display="inline";
				document.getElementById("pdc").style.display="none";
				document.getElementById("A").style.display="none";
				document.getElementById("B").style.display="none";
				document.getElementById("C").style.display="";
				document.getElementById("D").style.display="";
//				document.getElementById("E").style.display="";
//				document.getElementById("F").style.display="";
				document.getElementById("G").style.display="";
				document.getElementById("H").style.display="";
				document.getElementById("I").style.display="";
				document.getElementById("J").style.display="";
				document.getElementById("Y").style.display="none";
				document.getElementById("Z").style.display="none";
				//document.getElementById("SIN").style.display="none";
				
			/*	document.getElementById("startingChequeNo").value="";
				document.getElementById("startingChequeNo").readOnly=true;
				document.getElementById("endingChequeNo").value="";
				document.getElementById("endingChequeNo").readOnly=true;*/
				document.getElementById("fromInstallment").readOnly=false;
				document.getElementById("toInstallment").readOnly=false;
				document.getElementById("fromInstallment").value="";
				document.getElementById("toInstallment").value="";
				document.getElementById("purpose").value="";
				
	    		   document.getElementById("s1").style.display="";
	    		   document.getElementById("s2").style.display="";
	    		   //document.getElementById("s3").style.display="";
				
			}
	       if((document.getElementById("instrumentType").value) == "Q" || (document.getElementById("instrumentType").value) == "DIR" ||(document.getElementById("instrumentType").value) == ""){
	    	   //added by neeraj space start
	    	   if(document.getElementById("instrumentType").value=='Q')
	    	   {
	    		   document.getElementById("s1").style.display="";
	    		   document.getElementById("s2").style.display="";
	    		   //document.getElementById("s3").style.display="";
	    	   }
	    	   else
	    	   {
	    		   document.getElementById("s1").style.display="none";
	    		   document.getElementById("s2").style.display="none";
	    		   //document.getElementById("s3").style.display="none";
	    		   //document.getElementById("pdcSubmitCustomerName").value="";
	    		   document.getElementById("submitBy").value="";
	    	   }
	    	   //neeraj space end
	    	   document.getElementById("ecs").style.display="none";
				document.getElementById("pdc").style.display="inline";
	    	   document.getElementById("A").style.display="";
	    	   document.getElementById("B").style.display="";
	    	   document.getElementById("Y").style.display="";
	    	   document.getElementById("Z").style.display="";
	    	   //document.getElementById("SIN").style.display="";
	    		document.getElementById("C").style.display="none";
				document.getElementById("D").style.display="none";
//				document.getElementById("E").style.display="none";
//				document.getElementById("F").style.display="none";
				document.getElementById("G").style.display="none";
				document.getElementById("H").style.display="none";
				document.getElementById("I").style.display="none";
				document.getElementById("J").style.display="none";
				/*document.getElementById("startingChequeNo").readOnly=false;
				document.getElementById("endingChequeNo").readOnly=false;
				document.getElementById("fromInstallment").readOnly=true;
				document.getElementById("toInstallment").readOnly=true;*/
				document.getElementById("fromInstallment").value="";
				document.getElementById("toInstallment").value="";
			}
		}
		//Nishant space ends
	function searchUpdateInstrumentAuthor(){
	   DisButClass.prototype.DisButMethod();
       var contextPath=document.getElementById("contextPath").value;
       var reportingToUserId=document.getElementById("reportingToUserId").value;
   //    alert("ok");
   if((document.getElementById("loanAccNo").value =='') && (document.getElementById("customerName").value =='') && (document.getElementById("instrumentType").value=='') && (document.getElementById("bank").value=='') && (document.getElementById("branch").value =='') && (document.getElementById("product").value=='')&& (document.getElementById("reportingToUserId").value=='')){
     
   	 alert("Please select atleast one criteria");
   	 DisButClass.prototype.EnbButMethod();
   	//document.getElementById("save").removeAttribute("disabled");
   	return false;
    
   }else {
   document.getElementById("sourcingForm").action = contextPath+"/instrumentUpdateProcessActionforNew.do?method=searchInstrumentAuthor";
   document.getElementById("processingImage").style.display = '';
   document.getElementById("sourcingForm").submit();
   		 return true;
   }
   }