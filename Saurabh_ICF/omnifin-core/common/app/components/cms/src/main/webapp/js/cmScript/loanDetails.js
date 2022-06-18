function isInt(n) {
	   return n % 1 == 0;
	}

function getKey(keyStroke) 
{
var t = window.event.srcElement.type;
var keyCode = (document.layers) ? keyStroke.which : event.keyCode;
var keyString = String.fromCharCode(keyCode).toLowerCase();
var leftArrowKey = 37;
var backSpaceKey = 8;
var escKey = 27;
if(t && (t =='text' || t =='textarea' || t =='file')){
//do not cancel the event
}else{
if( (window.event.altKey && window.event.keyCode==leftArrowKey) || (keyCode == escKey) || (keyCode == backSpaceKey)){
return false;
}
}
}
			
//     	
//     	$(function() {
//		$("#agreementDate").datepicker({
//		 changeMonth: true,
//		 changeYear: true,
//	     yearRange: '1900:+10',
//	     showOn: 'both',
//	     buttonImage: document.getElementById("CalImage").value,
//			buttonImageOnly: true,
//			dateFormat: document.getElementById("formatD").value,
//			//minDate: document.getElementById("checkNewRepay").value
//	
//	
//	});
//     	});
//     	
//     	$(function() {
//		$("#repayEffectiveDate").datepicker({
//		 changeMonth: true,
//		 changeYear: true,
//	     yearRange: '1900:+10',
//	     showOn: 'both',
//	     buttonImage: document.getElementById("CalImage").value,
//			buttonImageOnly: true,
//			dateFormat: document.getElementById("formatD").value,
//			//minDate: document.getElementById("checkNewRepay").value
//	
//	
//	});
//     	});
////       
//        	$(function() {
//		$("#repayEffectiveDateOneLoan").datepicker({
//		 changeMonth: true,
//		 changeYear: true,
//	     yearRange: '1900:+10',
//	     showOn: 'both',
//	     buttonImage: document.getElementById("CalImage").value,
//			buttonImageOnly: true,
//			dateFormat: document.getElementById("formatD").value,
//			//minDate: document.getElementById("checkNewRepay").value
//	
//	
//	});
//     	});
       function focusOnProduct()
		{
			var val= document.getElementById('dealButton').value;
			if(val==' ')
			{
				document.getElementById('sourcingForm').dealButton.focus();
			}
			if(val=='k')
			{
			}
			else
			{
				document.getElementById('sourcingForm').loanAmount.focus();
			}
			
		}
		
		
		function checkDueDate(san)
		{
	   		var date = new Date();
			var formatD=document.getElementById("formatD").value;
	   		var oneDealOneLoan = document.getElementById('oneDealOneLoan').value;
	   		var str = document.getElementById('nextDueDate').value;
			var nMonth = str.substring(3,5); //months from 1-12
			var nDay = str.substring(0,2);
			var nYear = str.substring(6);
	   		var dueDay="";
	   		if(oneDealOneLoan == 'Y')
	   	    	 dueDay=document.getElementById('dueDayOneLoan').value;
	   		else
	   	    	dueDay=document.getElementById('dueDay').value;
	   		var comp = str.substring(0,2);
	   		if(dueDay == '')
	   		{ 		
	   			alert("First select Due Day");
	   			document.getElementById('nextDueDate').value = '';
	   		}
	   		else
	   		{
				if(dueDay<=28)
				{
	   			if(parseFloat(comp) != parseFloat(dueDay))
	   			{
		   			alert("Next Due Date must be equal to Due Day ie. "+dueDay);
		   			document.getElementById('nextDueDate').value = '';	   		
		   		}
		   		else
		   		{
		   			checkDate('nextDueDate');
		   		}
				
	   		}
				else if (parseFloat(nMonth)==2 )
		{
							
			if(((parseFloat(nYear) % 4 == 0) && ((parseFloat(nYear) % 100 != 0))) || ((parseFloat(nYear) % 400 == 0)))
			{
			
				if((dueDay==29 ||dueDay==30 || dueDay==31) &&  comp==29)
				{
				checkDate('nextDueDate');
				}
	   		else
	   		{
				alert("Next Due Date Day must be equal  29");
	   			document.getElementById('nextDueDate').value = '';
				}
			}else
			{
				if((dueDay==29 ||dueDay==30 || dueDay==31) &&  comp==28)
				{
				checkDate('nextDueDate');
				}
				else
				{
				alert("Next Due Date Day must be equal  28");
				document.getElementById('nextDueDate').value = '';
				}
			}
		}
		else if ( parseFloat(nMonth)==4 || parseFloat(nMonth)==6 || parseFloat(nMonth)==9 || parseFloat(nMonth)==11 )
		{
			if(dueDay==29 ||dueDay==30)
			{
				if (comp != dueDay)
				{
				 alert("Next Due Date must be equal to Due Day ie. " + dueDay);
				 document.getElementById('nextDueDate').value = '';
				}
			}
			else if( dueDay==31 &&  comp==30)
			{
				checkDate('nextDueDate');
			}
			else
			{
			alert("Next Due Date Day must be equal  30");
			document.getElementById('nextDueDate').value = '';
			}
		}
		else if (parseFloat(nMonth)==1 || parseFloat(nMonth)==3 || parseFloat(nMonth)==5 || parseFloat(nMonth)==7 || parseFloat(nMonth)==8 || parseFloat(nMonth)==10  || parseFloat(nMonth)==12)
		{
			if (comp != dueDay)
			{
			 alert("Next Due Date must be equal to Due Day ie. " + dueDay);
			 document.getElementById('nextDueDate').value = '';
			} else 
			{
				checkDate('nextDueDate');
			}
			
		}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	   		}	   		
	   }
	   
	   
	   function nullNextDue(){
	   		document.getElementById('nextDueDate').value = '';
	   }
	   

	   $(function() {
			$("#nextDueDate").datepicker({
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
	   
	//metod added by neeraj    
	   function calculateMargin()
	   {
		   //alert("calculateMargin");
		   var repaymentType=document.getElementById("repaymentType").value; 
		   var assetFlag=document.getElementById("assetFlag").value; 
		   if(repaymentType=='I')	
		   if(assetFlag=='A')	
		   {
				   var astCost = document.getElementById('assetCost').value;
				   var lonAmt = document.getElementById('loanAmount').value;
				   var cost=0;
				   var amount=0;
				   if(astCost=="" || lonAmt=="")
				   {
					   var msg="";
					   if(astCost=="")
						   msg="*Asset Cost is required.\n";
					   if(lonAmt=="")
						   msg=msg+"*Loan Amount is required.";
					   alert(msg);
					   return false;
				   }
				   else
				   {
					   cost=parseFloat(removeComma(astCost));
					   amount=parseFloat(removeComma(lonAmt));
					   if(cost<amount)
					   {
						   alert("Asset Cost can't be less than Loan Amount.");
						   document.getElementById("margin").value = "";
						   document.getElementById("marginAmount").value = "";
						   return false;
					   }
				   }
				   var marginAm = cost - amount;
				   var marginPercentage = marginAm * (100/cost);
				   document.getElementById("margin").value = (marginPercentage).toFixed(2);
				   document.getElementById("marginAmount").value = marginAm;
		   }
	   }
	   //sachin
	   function checkRate(val)
	   {
	   	var rate = document.getElementById(val).value;
	   	 
	   // alert("Passed Value: "+rate);
	   if(rate=='')
	   	{
	   		rate=0;
	   		// alert("Please Enter the value");
	   	// return false;
	   		
	   	}
	   	    var intRate = parseFloat(rate);
	   	  // alert(intRate);
	   	    if(intRate>=0 && intRate<=100)
	   	      {
	   		    return true;
	   	       }

	   	        else
	   	           {
	   		        alert("Please Enter the value b/w 0 to 100");
	   		        document.getElementById(val).value="";
	   		// document.getElementById(val).focus;
	   		        return false;
	   	         }
	   	
	   }


	   //end by sachin
		//Strat by sachin 
		function calculateFinalRate()
		{
			var markUp = document.getElementById("markUp").value;
			var baseRate = document.getElementById("baseRate").value;
			var finalRate = document.getElementById("effectiveRate").value;
			//alert("In calculateFinalRate markUp: "+markUp+"baseRate: "+baseRate);
			if(finalRate=="")
			{
				finalRate=0;
			}
			if(baseRate=="")
			{
				baseRate=0;
			}
//			effectiveRate=parseFloat(markUp)+parseFloat(baseRate);
			markUp=parseFloat(finalRate)-parseFloat(baseRate);
			document.getElementById("markUp").value=markUp.toFixed(7);
			document.getElementById("effectiveRate").value=finalRate.toFixed(7);
			
		}
		//end by sachin
//	   function calculateFinalRate()
//	   {
//		   alert("test111111111");
//		   var markUp = document.getElementById('markUp').value;
//		   var baseRate=document.getElementById('baseRate').value;
//		   if(markUp=="")
//			   markUp="0";
//		   if(baseRate=="")
//			   baseRate="0";
//		   var effrat=(parseFloat(removeComma(markUp))+parseFloat(removeComma(baseRate)));
//           document.getElementById('effectiveRate').value=effrat.toFixed(7);		   
//	   }
//	   function saveLoanInCM()
//	   {
//		   //code added by neeraj tripathi
//		   //alert("saveLoanInCM");
//
//		   
//		   var val = document.getElementById('showRepaymentType').value;
//
//		   
//		   var lonAmt = document.getElementById('loanAmount').value;
//		   var repaymentType=document.getElementById("repaymentType").value; 
////Prashant start
//		   var assetFlag=document.getElementById("assetFlag").value; 
//		   if(repaymentType=='I')	
//
//			{
//			   if(assetFlag=='A')	
//
//				{
//				  var msg="";
//			      var astCost = document.getElementById('assetCost').value;
//
//			     
//			      if(astCost=="")
//
//			      {
//			    	  msg="*Asset Cost is required.\n";
//
//			      }
//			      //alert("ok");
//			      if(msg!="")
//
//			      {
//				      alert(msg);
//					  document.getElementById("saveButton").removeAttribute("disabled","true");
//					  return false;
//
//
//			      }
//				}
//			   if(lonAmt=="")
//
//			   {
//				   var msg="";
//				   if(lonAmt=="")
//					   msg=msg+"*Loan Amount is required.";
//				   alert(msg);
//				   document.getElementById("saveButton").removeAttribute("disabled","true");
//				   return false;
//
//			   }
//			   if(assetFlag=='A')
//
//			   {
//				   var cost=parseFloat(removeComma(astCost));
//				   var amount=parseFloat(removeComma(lonAmt));
//				   if(cost<amount)
//
//				   {
//					   alert("Asset Cost can't be less than Loan Amount.");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//
//
//
//				   }
//			   }
//			}
//// prashant end
//		   var rateType = document.getElementById('rateType').value;
//		   var minFlatRate = parseFloat(document.getElementById('minFlatRate').value);
//		   var maxFlatRate = parseFloat(document.getElementById('maxFlatRate').value);
//		   var minEffRate = parseFloat(document.getElementById('minEffRate').value);
//		   var maxEffRate = parseFloat(document.getElementById('maxEffRate').value);
//		   var minTenure = parseFloat(document.getElementById('minTenure').value);
//		   var maxTenure = parseFloat(document.getElementById('maxTenure').value);
//		   var tenureMonths = document.getElementById('tenureMonths').value;
//		   //var markUp = document.getElementById('markUp').value;
//		   var markUp=document.getElementById('effectiveRate').value;
//		   if(tenureMonths=="" || markUp=="")
//
//		   {
//			   var msg="";
//			   if(tenureMonths=="")
//				   msg="*Tenure Months is required.\n";
//			   if(markUp=="")
//				   msg=msg+"*Mark Up is required.";
//			   alert(msg);
//			   document.getElementById("saveButton").removeAttribute("disabled","true");
//			   return false;
//
//		   }
//		   else
//
//		   {
//			   var tanure=parseFloat(removeComma(tenureMonths));
//			   if(tanure<minTenure ||tanure>maxTenure)
//
//			   {
//				   alert("Tenure Months out of range,It should be greater than or equal to  '"+minTenure +"'  and less than or equal to '"+maxTenure+"'");
//				   document.getElementById("saveButton").removeAttribute("disabled","true");
//				   return false;
//
//			   }
//			   if(rateType=='E')
//
//			   {
//				   var mark=parseFloat(removeComma(markUp));
//				   if(mark<minEffRate ||mark>maxEffRate)
//
//				   {
//					   alert("Final Rate out of range,It should be greater than or equal to '"+minEffRate +"'  and less than or equal to '"+maxEffRate+"'");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//
//
//				   }
//			   }
//			   if(rateType=='F')
//
//			   {
//				   var mark=parseFloat(removeComma(markUp));
//				   if(mark<minFlatRate ||mark>maxFlatRate)
//
//				   {
//					   alert("Final Rate out of range,It should be greater than or equal to '"+minFlatRate +"' and less than or equal to  '"+maxFlatRate+"' ");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//
//
//
//				   }
//			   }			   
//		   }
//           //commented by neeraj tripathi
//		  /* if(repaymentType=='I')	
//
//			{
//				if(!isInt(calcInstallment()))
//
//
//				{
//					
//						alert("The Combination of tenure and frequency are incorrect");
//						document.getElementById("tenureMonths").value='';
//						document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//
//				}
//			}*/
//		  // neeraj tripathi's space end    	
//	   	if(val=='INSTALLMENT')
//
//	   	{
//	   		var nextDueDate=document.getElementById("nextDueDate").value;
//	   		var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
//	   		var nextDate="";
//	   		var repay="";
//	   		var dueDay="";
//	   		if(oneDealOneLoan=="N")
//
//	   		{
//	   			rePayDate=document.getElementById("repayEffectiveDate").value;
//	   			dueDay=document.getElementById("dueDay").value;
//
//	   		}
//	   		if(oneDealOneLoan=="Y")
//
//	   		{
//	   			rePayDate=document.getElementById("repayEffectiveDateOneLoan").value;
//	   			dueDay=document.getElementById("dueDayOneLoan").value;
//
//	   		}
//	   		if(nextDueDate=="")
//
//	   		{
//	   			alert("Next Due Date is required.");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//
//	   		}
//	   		var day2= nextDueDate.substring(0,2);
//	   		if(parseFloat(day2) != parseFloat(dueDay))
//
//	   		{
//	   			alert("Next Due Date must be equal to Due Day ie."+dueDay);
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//
//	   		}
//	   		if(rePayDate=="")
//
//	   		{
//	   			alert("Repay Effective Date is required.");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//
//	   		}
//	   		var formatD =document.getElementById("formatD").value;
//	   		//alert("rePayDate: "+rePayDate+"nextDueDate: "+nextDueDate+"formatD: "+formatD);
//	   		var diffDay=parseInt(daysBetweenDates(getDateObject(rePayDate, formatD.substring(2, 3)),getDateObject(nextDueDate, formatD.substring(2, 3))));
//			//alert("diffDay: "+diffDay);
//			var diffDayCount= parseInt(document.getElementById("diffDayCount").value);
//			//alert("diffDayCount: "+diffDayCount);
//			if(diffDay<diffDayCount)
//
//			{
//				    alert("Difference between Repay Effective and Next Due Date should not be less than "+diffDayCount+" days");		
//				    document.getElementById("saveButton").removeAttribute("disabled","true");
//		   			return false;
//
//			}
//			
//	   		var day1= rePayDate.substring(0,2);
//
//	   	
//	   		var month1=rePayDate.substring(3,5);
//	   		var month2=nextDueDate.substring(3,5);
//	   		var year1=rePayDate.substring(6);
//	   		var year2=nextDueDate.substring(6);	
//			if (val == 'INSTALLMENT') {
//	   		var noOfInstall=document.getElementById("loanNoofInstall").value
//			var installments=document.getElementById("installments").value;
//			if(document.getElementById("installments").value==''){
//				installments=0;
//			}
//			else{
//				installments=parseInt(installments);
//			}
//			
//			if(document.getElementById("dealInstallmentMode").value == 'A')
//			{
//				if(installments+1>noOfInstall){
//				alert(" No of Installment should not be greater than total No. of Advance Installment ");
//				document.getElementById("installments").value=0;
//				document.getElementById("Save").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//				}
//			}
//			if(document.getElementById("dealInstallmentMode").value == 'R')
//			{
//				if(installments>noOfInstall){
//				alert(" No of Installment should not be greater than total No. of Advance Installment ");
//				document.getElementById("installments").value=0;
//				document.getElementById("Save").removeAttribute("disabled","true");
//				DisButClass.prototype.EnbButMethod();
//				return false;
//				}
//			}
//			}
//	   		if(parseFloat(year1)>parseFloat(year2))
//
//	   		{	
//	   			alert("Next Due Date should be greater than Repay Effective Date");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//
//
//
//	   		}
//	   		else
//	   		{
//	   			if(parseFloat(year1)==parseFloat(year2))
//
//	   			{
//	   				if(parseFloat(month1)>parseFloat(month2))
//
//	   				{	
//	   					alert("Next Due Date should be greater than Repay Effective Date");
//	   					document.getElementById("saveButton").removeAttribute("disabled","true");
//	   					return false;
//
//
//
//	   				}
//	   				else 
//	   				{
//	   					if(parseFloat(month1)==parseFloat(month2))
//
//	   					{
//	   						if(parseFloat(day1)>=parseFloat(day2))
//
//	   						{	
//	   							alert("Next Due Date should be greater than Repay Effective Date");		
//	   							document.getElementById("saveButton").removeAttribute("disabled","true");
//	   							return false;
//	   						}
//	   					}
//	   				}
//	   			}
//	   		}
//	   	}
//	    
//	   	 var basePath=document.getElementById("basePath").value;
//
//	   	 
//	   	// var typeOfDisbursal = '';
//	   	 
////	   	 if(repaymentType=='I')
////	   	 {
////	   	 typeOfDisbursal =document.getElementById("typeOfDisbursal").value;
////	   	 }
//
//	   	 if(validateLaonInitForm())
//	   	 {   		
//	   		 if(checkLoanAmount())
//	   		 {
//	   			 if(repaymentType=='I')
//	   				 {
//	   					 var dueDay='';
//	   					 if(document.getElementById("oneDealOneLoan").value=='Y')
//	   					 dueDay=document.getElementById("dueDayOneLoan").value;  
//	   					 else
//	   					 dueDay=document.getElementById("dueDay").value; 
//	   					 	   					
//	   					 if(dueDay=='')
//	   					 {
//	   						 alert("Due day is required");
//	   						 document.getElementById("saveButton").removeAttribute("disabled","true");
//	   						 return false;
//	   					 }
//	   				 }
//	   			
//	   		 }
//	   		 //code added by neeraj tripathi
//	   		 if(repaymentType=='I')
//			 if(rateType=='F')
//			 {
//					var loanNoofInstall=document.getElementById("loanNoofInstall").value;
//					if(loanNoofInstall=="")
//					{
//						alert("Total Installment is required.");
//						document.getElementById("loanNoofInstall").focus();
//						 document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//					}
//					var instllment=parseInt(removeComma(loanNoofInstall));
//					if(instllment==0)
//					{
//						alert("Total Installment can't be zero(0).");
//						document.getElementById("loanNoofInstall").focus();
//						 document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//					}
//					var tenure=parseInt(removeComma(tenureMonths));
//					var frequency=document.getElementById("frequency").value;
//					var fre=1;
//					if(frequency=="M")
//						fre=1;
//					if(frequency=="B")
//						fre=2;
//					if(frequency=="Q")
//						fre=3;
//					if(frequency=="H")
//						fre=6;
//					if(frequency=="Y")
//						fre=12;
//					
//					//alert("tenure : "+tenure);
//					//alert("fre    : "+fre);
//					var noOfInstallDF=parseInt(tenure/fre);
//					//alert("noOfInstallDF  : "+noOfInstallDF);
//					//alert("instllment    : "+instllment);
//					if(noOfInstallDF>instllment)
//					{	
//						if(confirm("Entered No of Installment is less than from Actual No of Installment ("+noOfInstallDF+") as per selected frequency, Do you want to continue?")) 
//						{
//							document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//							document.getElementById("sourcingForm").submit();
//							return true;
//						}
//						else
//						{
//							DisButClass.prototype.EnbButMethod();
//							return false;
//						}
//					}
//					else if(noOfInstallDF<instllment)
//					{
//						alert("Entered No of Installment can’t be greater than Actual no of Installment ("+noOfInstallDF+") as per selected frequency");
//						DisButClass.prototype.EnbButMethod();
//						return false;
//					}
//				}
//			 //tripathi's space end
//	   		 document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//			 document.getElementById("sourcingForm").submit();
//			 return true;
//		 }
//	   	 else
//	   function saveLoanInCM()
//	   {
//		   //code added by neeraj tripathi
//		   //alert("saveLoanInCM");
//		   
//		   var val = document.getElementById('showRepaymentType').value;
//		   
//		   var lonAmt = document.getElementById('loanAmount').value;
//		   var repaymentType=document.getElementById("repaymentType").value; 
////Prashant start
//		   var assetFlag=document.getElementById("assetFlag").value; 
//		   if(repaymentType=='I')	
//			{
//			   if(assetFlag=='A')	
//				{
//				  var msg="";
//			      var astCost = document.getElementById('assetCost').value;
//			     
//			      if(astCost=="")
//			      {
//			    	  msg="*Asset Cost is required.\n";
//			      }
//			      //alert("ok");
//			      if(msg!="")
//			      {
//				      alert(msg);
//					  document.getElementById("saveButton").removeAttribute("disabled","true");
//					  return false;
//			      }
//				}
//			   if(lonAmt=="")
//			   {
//				   var msg="";
//				   if(lonAmt=="")
//					   msg=msg+"*Loan Amount is required.";
//				   alert(msg);
//				   document.getElementById("saveButton").removeAttribute("disabled","true");
//				   return false;
//			   }
//			   if(assetFlag=='A')
//			   {
//				   var cost=parseFloat(removeComma(astCost));
//				   var amount=parseFloat(removeComma(lonAmt));
//				   if(cost<amount)
//				   {
//					   alert("Asset Cost can't be less than Loan Amount.");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//				   }
//			   }
//			}
//// prashant end
//		   var rateType = document.getElementById('rateType').value;
//		   var minFlatRate = parseFloat(document.getElementById('minFlatRate').value);
//		   var maxFlatRate = parseFloat(document.getElementById('maxFlatRate').value);
//		   var minEffRate = parseFloat(document.getElementById('minEffRate').value);
//		   var maxEffRate = parseFloat(document.getElementById('maxEffRate').value);
//		   var minTenure = parseFloat(document.getElementById('minTenure').value);
//		   var maxTenure = parseFloat(document.getElementById('maxTenure').value);
//		   var tenureMonths = document.getElementById('tenureMonths').value;
//		   //var markUp = document.getElementById('markUp').value;
//		   var markUp=document.getElementById('effectiveRate').value;
//		   if(tenureMonths=="" || markUp=="")
//		   {
//			   var msg="";
//			   if(tenureMonths=="")
//				   msg="*Tenure Months is required.\n";
//			   if(markUp=="")
//				   msg=msg+"*Mark Up is required.";
//			   alert(msg);
//			   document.getElementById("saveButton").removeAttribute("disabled","true");
//			   return false;
//		   }
//		   else
//		   {
//			   var tanure=parseFloat(removeComma(tenureMonths));
//			   if(tanure<minTenure ||tanure>maxTenure)
//			   {
//				   alert("Tenure Months out of range,It should be greater than or equal to  '"+minTenure +"'  and less than or equal to '"+maxTenure+"'");
//				   document.getElementById("saveButton").removeAttribute("disabled","true");
//				   return false;
//			   }
//			   if(rateType=='E')
//			   {
//				   var mark=parseFloat(removeComma(markUp));
//				   if(mark<minEffRate ||mark>maxEffRate)
//				   {
//					   alert("Final Rate out of range,It should be greater than or equal to '"+minEffRate +"'  and less than or equal to '"+maxEffRate+"'");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//				   }
//			   }
//			   if(rateType=='F')
//			   {
//				   var mark=parseFloat(removeComma(markUp));
//				   if(mark<minFlatRate ||mark>maxFlatRate)
//				   {
//					   alert("Final Rate out of range,It should be greater than or equal to '"+minFlatRate +"' and less than or equal to  '"+maxFlatRate+"' ");
//					   document.getElementById("saveButton").removeAttribute("disabled","true");
//					   return false;
//				   }
//			   }			   
//		   }
//           //commented by neeraj tripathi
//		  /* if(repaymentType=='I')	
//			{
//				if(!isInt(calcInstallment()))
//				{
//					
//						alert("The Combination of tenure and frequency are incorrect");
//						document.getElementById("tenureMonths").value='';
//						document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//				}
//			}*/
//		  // neeraj tripathi's space end    	
//	   	if(val=='INSTALLMENT')
//	   	{
//	   		var nextDueDate=document.getElementById("nextDueDate").value;
//	   		var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
//	   		var nextDate="";
//	   		var repay="";
//	   		var dueDay="";
//	   		if(oneDealOneLoan=="N")
//	   		{
//	   			rePayDate=document.getElementById("repayEffectiveDate").value;
//	   			dueDay=document.getElementById("dueDay").value;
//	   		}
//	   		if(oneDealOneLoan=="Y")
//	   		{
//	   			rePayDate=document.getElementById("repayEffectiveDateOneLoan").value;
//	   			dueDay=document.getElementById("dueDayOneLoan").value;
//	   		}
//	   		if(nextDueDate=="")
//	   		{
//	   			alert("Next Due Date is required.");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//	   		}
//	   		var day2= nextDueDate.substring(0,2);
//	   		if(parseFloat(day2) != parseFloat(dueDay))
//	   		{
//	   			alert("Next Due Date must be equal to Due Day ie."+dueDay);
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//	   		}
//	   		if(rePayDate=="")
//	   		{
//	   			alert("Repay Effective Date is required.");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//	   		}
//	   		var formatD =document.getElementById("formatD").value;
//	   		//alert("rePayDate: "+rePayDate+"nextDueDate: "+nextDueDate+"formatD: "+formatD);
//	   		var diffDay=parseInt(daysBetweenDates(getDateObject(rePayDate, formatD.substring(2, 3)),getDateObject(nextDueDate, formatD.substring(2, 3))));
//			//alert("diffDay: "+diffDay);
//			var diffDayCount= parseInt(document.getElementById("diffDayCount").value);
//			//alert("diffDayCount: "+diffDayCount);
//			if(diffDay<diffDayCount)
//			{
//				    alert("Difference between Repay Effective and Next Due Date should not be less than "+diffDayCount+" days");		
//				    document.getElementById("saveButton").removeAttribute("disabled","true");
//		   			return false;
//			}
//	   		var day1= rePayDate.substring(0,2);
//	   	
//	   		var month1=rePayDate.substring(3,5);
//	   		var month2=nextDueDate.substring(3,5);
//	   		var year1=rePayDate.substring(6);
//	   		var year2=nextDueDate.substring(6);		
//	   		if(parseFloat(year1)>parseFloat(year2))
//	   		{	
//	   			alert("Next Due Date should be greater than Repay Effective Date");
//	   			document.getElementById("saveButton").removeAttribute("disabled","true");
//	   			return false;
//	   		}
//	   		else
//	   		{
//	   			if(parseFloat(year1)==parseFloat(year2))
//	   			{
//	   				if(parseFloat(month1)>parseFloat(month2))
//	   				{	
//	   					alert("Next Due Date should be greater than Repay Effective Date");
//	   					document.getElementById("saveButton").removeAttribute("disabled","true");
//	   					return false;
//	   				}
//	   				else 
//	   				{
//	   					if(parseFloat(month1)==parseFloat(month2))
//	   					{
//	   						if(parseFloat(day1)>=parseFloat(day2))
//	   						{	
//	   							alert("Next Due Date should be greater than Repay Effective Date");		
//	   							document.getElementById("saveButton").removeAttribute("disabled","true");
//	   							return false;
//	   						}
//	   					}
//	   				}
//	   			}
//	   		}
//	   	}
//	    
//	   	 var basePath=document.getElementById("basePath").value;
//	   	 
//	   	// var typeOfDisbursal = '';
//	   	 
////	   	 if(repaymentType=='I')
////	   	 {
////	   	 typeOfDisbursal =document.getElementById("typeOfDisbursal").value;
////	   	 }
//	   	
//	   	 if(validateLaonInitForm())
//	   	 {   		
//	   		 if(checkLoanAmount())
//	   		 {
//	   			 if(repaymentType=='I')
//	   				 {
//	   					 var dueDay='';
//	   					 if(document.getElementById("oneDealOneLoan").value=='Y')
//	   					 dueDay=document.getElementById("dueDayOneLoan").value;  
//	   					 else
//	   					 dueDay=document.getElementById("dueDay").value; 
//	   					 	   					
//	   					 if(dueDay=='')
//	   					 {
//	   						 alert("Due day is required");
//	   						 document.getElementById("saveButton").removeAttribute("disabled","true");
//	   						 return false;
//	   					 }
//	   				 }
//	   			
//	   		 }
//	   		 //code added by neeraj tripathi
//	   		 if(repaymentType=='I')
//			 if(rateType=='F')
//			 {
//					var loanNoofInstall=document.getElementById("loanNoofInstall").value;
//					if(loanNoofInstall=="")
//					{
//						alert("Total Installment is required.");
//						document.getElementById("loanNoofInstall").focus();
//						 document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//					}
//					var instllment=parseInt(removeComma(loanNoofInstall));
//					if(instllment==0)
//					{
//						alert("Total Installment can't be zero(0).");
//						document.getElementById("loanNoofInstall").focus();
//						 document.getElementById("saveButton").removeAttribute("disabled","true");
//						return false;
//					}
//					var tenure=parseInt(removeComma(tenureMonths));
//					var frequency=document.getElementById("frequency").value;
//					var fre=1;
//					if(frequency=="M")
//						fre=1;
//					if(frequency=="B")
//						fre=2;
//					if(frequency=="Q")
//						fre=3;
//					if(frequency=="H")
//						fre=6;
//					if(frequency=="Y")
//						fre=12;
//					
//					//alert("tenure : "+tenure);
//					//alert("fre    : "+fre);
//					var noOfInstallDF=parseInt(tenure/fre);
//					//alert("noOfInstallDF  : "+noOfInstallDF);
//					//alert("instllment    : "+instllment);
//					if(noOfInstallDF>instllment)
//					{	
//						if(confirm("Entered No of Installment is less than from Actual No of Installment ("+noOfInstallDF+") as per selected frequency, Do you want to continue?")) 
//						{
//							document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//							document.getElementById("sourcingForm").submit();
//							return true;
//						}
//						else
//						{
//							DisButClass.prototype.EnbButMethod();
//							return false;
//						}
//					}
//					else if(noOfInstallDF<instllment)
//					{
//						alert("Entered No of Installment can’t be greater than Actual no of Installment ("+noOfInstallDF+") as per selected frequency");
//						DisButClass.prototype.EnbButMethod();
//						return false;
//					}
//				}
//			 //tripathi's space end
//	   		 document.getElementById("sourcingForm").action = basePath+"/loanDetailCMProcessAction.do?method=updateLoanDetails";
//			 document.getElementById("sourcingForm").submit();
//			 return true;
//		 }
//	   	 else
//	   	 {
//	   		 document.getElementById("saveButton").removeAttribute("disabled","true");
//	   		 return false;
//	   	 }
//	   }
	 //method added by neeraj tripathi
	   function enableInstallNo()
	   {
	   	//alert("enableInstallNo");
	   	var rate = document.getElementById("rateType").value;
	   	var repaymentType=document.getElementById("repaymentType").value;
	   	//alert("rate  : "+rate);
	   	//alert("repaymentType  : "+repaymentType);
	   	if(rate!='' && repaymentType !='')
	   	if(repaymentType=='I')
	   	{
	   		if(rate=='F')
	   		{
	   			document.getElementById("loanNoofInstall").removeAttribute("readOnly");
	   			document.getElementById("loanNoofInstall").removeAttribute("tabindex",-1);
	   		}
	   		else
	   		{
	   			document.getElementById("loanNoofInstall").setAttribute("readonly",true);
	   			document.getElementById("loanNoofInstall").setAttribute("tabindex",-1);
	   		}
	   	}
	   }
	   
	   function validateLaonInitForm(){
			 var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='';
			 var loanAmount=document.getElementById("loanAmount").value;
			 var agreementDate=document.getElementById("agreementDate").value;
			 var dealNo=document.getElementById("dealNo").value;
			 var showProduct=document.getElementById("showProduct").value;
			 var repayEffectiveDate="";
			 var oneDealOneLoan=document.getElementById("oneDealOneLoan").value;
			 var remarks=document.getElementById("remarks").value;
			 var repaymentType=document.getElementById("repaymentType").value; 
			 var count=0;
			 
			 if(oneDealOneLoan=='Y'){
				 repayEffectiveDate=document.getElementById("repayEffectiveDateOneLoan").value;
			 }else{
				 repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
			 }
			 if(loanAmount==''){
				 msg1='* Loan Amount is required \n';
				 count=1;
			 }else{
				 msg1='';
				}
			 if(agreementDate==''){
				 msg2='* Agreement Date is required \n';
				 count=1;
			 }else{
				 msg2='';
			 }
			 if(repayEffectiveDate=='' && repaymentType=='I'){
				 msg3='* Repay Effective Date is required \n';
				 count=1;
			 }else{
				 msg3='';
				}
			 /*if(remarks==''){
				 msg4='* Remark is required \n';
				 count=1;
			 }else{
				 msg4='';
			 }*/	//Commented by Nishant
			if(dealNo==''){
		   		 msg5='* Deal No. is required \n';
		   		 count=1;
		   	 }else{
		   		 msg5='';
		   		}
		   	 if(showProduct==''){
		   		 msg6='* Product is required \n';
		   		 count=1;
		   	 }else{
		   		 msg6='';
		   	 }
		   	 
		   	 if(msg5 != ''){
		   		 document.getElementById("dealButton").focus();
		   	 }else if(msg6 != ''){
		   		 document.getElementById("pbutton").focus();
		   	 }
			 
			 if(count==1){
				 alert(msg5+ msg6+ msg1 +msg2 +msg3);
				 return false;
			 }else{
				// alert("Data Saved Successfully.");  //Added by Nishant
				 return true; 
				 
			 }
		 }
	   
	   
	   function calcInstallment()
	   {
		  
	   	var frequency= document.getElementById("frequency").value;
	   	var requestedLoanTenure= document.getElementById("tenureMonths").value;
	   	var parseTenure=0;
	   	var freqMonth=0;
	   	if(frequency=='M')
	   	{
	   		freqMonth=1;
	   	}
	   	else if(frequency=='B')
	   	{
	   		freqMonth=2;
	   	}
	   	else if(frequency=='Q')
	   	{
	   		freqMonth=3;
	   	}
	   	else if(frequency=='H')
	   	{
	   		freqMonth=6;
	   	}
	   	else if(frequency=='Y')
	   	{
	   		freqMonth=12;
	   	}
	   	
	   	if(frequency!='')
	   	{
	   		parsedFreq=parseInt(freqMonth);
	   		//alert("parsedFreq:----"+parsedFreq);
	   	}
	   	
	   	if(requestedLoanTenure!='')
	   	{
	   		parseTenure=parseInt(requestedLoanTenure);
	   		//alert("parseTenure:-----"+parseTenure);
	   	}
	   	
	   	//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
	   	var calInsat=parseInt(parseTenure/parsedFreq);
	   	if(calInsat==0)
	   		calInsat=1;
	   	//alert("calInsat:---"+calInsat);
	   	document.getElementById("loanNoofInstall").value=calInsat;
	   	
	   	return calInsat;
	   	
	   	
	   }
	   //change by sachin
	   
	   function calcDay(){
		   
		   var requestedLoanTenure= document.getElementById("tenureMonths").value;
		   var daysBasis= document.getElementById("daysBasis").value;
//		   alert("daysBasis"+daysBasis);
		   
		   	if(requestedLoanTenure==''){
		   		requestedLoanTenure=0;
		   	}
		   	if(requestedLoanTenure!='')
		   	{
		   		requestedLoanTenure=parseInt(requestedLoanTenure);
		   		//alert("parseTenure:-----"+parseTenure);
		   	}
		   	if(daysBasis=='A'){
		   	//	alert("under a"+daysBasis);
		   		var day=requestedLoanTenure*30.42;
		   		day=Math.floor(day);
			   	document.getElementById("tenureInDay").value=day.toFixed(0); 
			   }
		   	else{
		   	var day=requestedLoanTenure*30;
		   	document.getElementById("tenureInDay").value=day;
		   	}
	   }
//	   function changeMonthInDay(){
//		   alert("2222daysBasis"+daysBasis);
//		   var tenureInDay= document.getElementById("tenureInDay").value;
//		   var daysBasis= document.getElementById("daysBasis").value;
//		   alert("daysBasis"+daysBasis);
//		   
//		   	if(tenureInDay==''){
//		   		tenureInDay=0;
//		   	}
//		   	if(tenureInDay!='')
//		   	{
//		   		tenureInDay=parseInt(tenureInDay);
//		   		//alert("parseTenure:-----"+parseTenure);
//		   	}
//		   	if(daysBasis=='A'){
//		   		var month=tenureInDay/30.42;
//			   	document.getElementById("tenureMonths").value=month.toFixed(2);
//			   }
//		   	else{
//		   	var month=tenureInDay/30;
//		   	document.getElementById("tenureMonths").value=month.toFixed(2);
//		   	}
//	
//	   }
	   
	   //end by sachin
	   function getDueDayNextDueDate(value)
	   {
		    //	var repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
	   	var repayEffectiveDate=value;
	   	 
	   	var contextPath =document.getElementById('contextPath').value ;
	   	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	   	
	   	if(repayEffectiveDate!='')
	   	{
	   		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayNextDueDateDetail";
	   		var data = "repayEffectiveDate="+repayEffectiveDate;
	   		sendRequestGetDueDayNextDueDate(address,data);
	   		return true;
	   	}
	   	else
	   	{
	   		alert("Repay Effective Date is required.");	
	      	 	return false;
	   	}
	   }
	   function sendRequestGetDueDayNextDueDate(address, data) 
	   {
	   	
	   	var request = getRequestObject();
	   	request.onreadystatechange = function () {
	   		resultMethodGetDueDayNextDueDate(request);
	   	};
	   	request.open("POST", address, true);
	   	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   	request.send(data);
	   }
	   function resultMethodGetDueDayNextDueDate(request)
	   {    
	   	if ((request.readyState == 4) && (request.status == 200)) 
	   	{
	   		var str = request.responseText;
	   		var s1 = str.split("$:");
	   		//alert(s1.length);
	   		if(s1.length>0)
	   	    {
	   			//alert(trim(s1[1]));
	   			//document.getElementById('frequency').value='M';
	   			var freq=document.getElementById('frequency').value;
	   			document.getElementById('dueDay').value=trim(s1[0]);
	   			if(trim(s1[1])!='')
	   			{
	   				document.getElementById('nextDueDate').value=trim(s1[1]);
	   				calculateNextDueDate(freq);
	   			}
	   		   // getElementById('totalRecevable').value = trim(s1[0]);
	   			//window.close();
	   	    }
	   		
	   	}
	   }



	   function getDueDay(id)
	   {
		   var repayEffectiveDate="";
		   if(document.getElementById('repayEffectiveDate')!=null && document.getElementById('repayEffectiveDate')!='undefined')
		   {
			   repayEffectiveDate=document.getElementById('repayEffectiveDate').value;
		   }
		   else if(document.getElementById('repayEffectiveDateOneLoan')!=null && document.getElementById('repayEffectiveDateOneLoan')!='undefined')
		   {
			   repayEffectiveDate=document.getElementById('repayEffectiveDateOneLoan').value;
		   }
	   	
	   	//alert(repayEffectiveDate);
	   	var cycleDate=document.getElementById(id).value;
	   	var contextPath =document.getElementById('contextPath').value ;
	   	//alert("getDueDayNextDueDate repayEffectiveDate: "+repayEffectiveDate+" contextPath: "+contextPath);
	   	
	   	if(repayEffectiveDate!='')
	   	{
	   		var address = contextPath+"/ajaxActionForCP.do?method=getDueDayDetail";
	   		var data = "repayEffectiveDate="+repayEffectiveDate+"&cycleDate="+cycleDate;
	   		sendRequestDueDay(address,data);
	   		return true;
	   	}
	   	else
	   	{
	   		alert("Repay Effective Date is required.");	
	      	 	return false;
	   	}
	   }
	   function sendRequestDueDay(address, data) 
	   {
	   	
	   	var request = getRequestObject();
	   	request.onreadystatechange = function () {
	   		resultMethodDueDay(request);
	   	};
	   	request.open("POST", address, true);
	   	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   	request.send(data);
	   }
	   function resultMethodDueDay(request)
	   {    
	   	if ((request.readyState == 4) && (request.status == 200)) 
	   	{
	   		var str = request.responseText;
	   		var s1 = str.split("$:");
	   		//alert(s1.length);
	   		if(s1.length>0)
	   	    {
	   			//alert(trim(s1[1]));
	   			var freq=document.getElementById('frequency').value;
	   			if(trim(s1[1])!='')
	   			{
	   				document.getElementById('nextDueDate').value=trim(s1[1]);
	   				calculateNextDueDate(freq);
	   			}
	   			
	   		   // getElementById('totalRecevable').value = trim(s1[0]);
	   			//window.close();
	   	    }
	   		
	   	}
	   }

	   function calculateNextDueDate(frequency)
	   {

	   	var frequency= document.getElementById('frequency').value;
	   //	alert(frequency);
	   	var formatD=document.getElementById("formatD").value;
	   	var freqMonth=0;
//	   	if(frequency=='M')
//	   	{
//	   		freqMonth=1;
//	   	}
//	   	else 
	   	if(frequency=='B')
	   	{
	   		freqMonth=2;
	   	}
	   	else if(frequency=='Q')
	   	{
	   		freqMonth=3;
	   	}
	   	else if(frequency=='H')
	   	{
	   		freqMonth=6;
	   	}
	   	else if(frequency=='Y')
	   	{
	   		freqMonth=12;
	   	}
	   	
	   	if(frequency!='')
	   	{
	   		parsedFreq=parseInt(freqMonth);
	   		//alert("parsedFreq:----"+parsedFreq);
	   	}
	   	var repayEffectiveDate=document.getElementById("nextDueDate").value;
	   	var d1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
	   	d1.setMonth(d1.getMonth()+parsedFreq);
	   	var curr_date = d1.getDate();
	   	var curr_month = d1.getMonth();
	   	var curr_year = d1.getFullYear();
	   	if(curr_month==00)
		{
			curr_month=12;
			curr_year=curr_year-1;
		}
	   	//alert("curr_date "+padding(curr_date)+"curr_month: "+padding(curr_month)+"curr_year: "+curr_year);
	   	document.getElementById("nextDueDate").value=padding(curr_date)+formatD.substring(2, 3)+padding(curr_month)+formatD.substring(2, 3)+curr_year;

	   }

	   function padding(number){

	   	return number < 10 ? "0"+number : number;

	   	}
	   
	   function saveDeviationApproval() 
	   {
		   var chkCases=document.getElementsByName("chkCases");
		   var loanDeviationId=document.getElementsByName("loanDeviationId");
		   var manualRemarkList=document.getElementsByName("manualRemark");		   
		   var deviationId="";
		   var manualRemark="";
		   var remark="";
		   
		   for(var i=0; i< chkCases.length ; i++) 
		   {
			   if(chkCases[i].checked == true)
			   {
				   deviationId=deviationId+","+loanDeviationId[i].value;
				   remark=manualRemarkList[i].value;
				   if(remark=="")
				   {
					   alert("Remark(s) is/are required.");
					   return false;
				   }
				   else
				   manualRemark=manualRemark+","+manualRemarkList[i].value;
			   }
		   }		   
		  /* if(deviationId == "")
	   	   {
	   			alert("Select atleast one row");
	   			return false;
	   	   }*/
		   deviationId=deviationId.substring(1);
		   manualRemark=manualRemark.substring(1);
		   var contextPath = document.getElementById('contextPath').value;
		   document.getElementById("deviationApprovalForm").action=contextPath+"/saveLoanDeviationActionProcess.do?method=saveLoanDeviationDetails&deviationId="+deviationId+"&manualRemark="+manualRemark;
	   	   document.getElementById("processingImage").style.display='';
	   	   document.getElementById("deviationApprovalForm").submit();
	   	   return true;
	   }
			   
	   function loanDeviationApproval()
	   {

	   	var table = document.getElementById("gridTable");	
	   	var rowCount = table.rows.length;
	   
	   	var chkValue='';
	   	var status=false;
	   	var msg1='';	
	   	
	   	var contextPath = document.getElementById('contextPath').value;
	   	if(rowCount > 1 ){		
	   		for(var i=1;i<rowCount;i++){
	   		var manualRemark = document.getElementById('manualRemark'+i).value;
	   	 	//var recStatuses = document.getElementById('recStatuses'+i).checked;
	   	 		if(manualRemark=="" ){
	   				msg1="Remarks is required";
	   				status= true;
	   			}
	   	   }	
	         }
	   	
   	
	  	
	   	if(status){
	   				
	   		alert(msg1);	   				
	   		return false;
	   	}else
	   	{

	        var contextPath = document.getElementById('contextPath').value;
	        document.getElementById("deviationApprovalForm").action = contextPath+"/saveLoanDeviationActionProcess.do?method=forwardDeviationApproval";
	   		document.getElementById("processingImage").style.display = '';
	   		document.getElementById("deviationApprovalForm").submit();
	   		return true;
	   }
	   	}
	   
	   
	   function loanDeviationSendBack()
	   {

	   	var table = document.getElementById("gridTable");	
	   	var rowCount = table.rows.length;
	   
	   	var chkValue='';
	   	var status=false;
	   	var msg1='';	
	   	
	   	var contextPath = document.getElementById('contextPath').value;
	   	if(rowCount > 1 ){		
	   		for(var i=1;i<rowCount;i++){
	   		var manualRemark = document.getElementById('manualRemark'+i).value;
	   	 	//var recStatuses = document.getElementById('recStatuses'+i).checked;
	   	 		if(manualRemark=="" ){
	   				msg1="Remarks is required";
	   				status= true;
	   			}
	   	   }	
	         }
	   	
   	
	  	
	   	if(status){
	   				
	   		alert(msg1);	   				
	   		return false;
	   	}else
	   	{

	        var contextPath = document.getElementById('contextPath').value;
	        document.getElementById("deviationApprovalForm").action = contextPath+"/saveLoanDeviationActionProcess.do?method=sendBackDeviation";
	   		document.getElementById("processingImage").style.display = '';
	   		document.getElementById("deviationApprovalForm").submit();
	   		return true;
	   }
	   	}
//Start by sachin	   
	   function calLtvPerc()
	   {
	   	//alert("calLtvPerc: "+document.getElementById("repaymentType").value);
	   	var marginPerc=0.00;
	   	var hundred=removeComma('100.00');
	   	var ltvPerc=0.00;
	   	//alert(hundred);
	   	if(document.getElementById("margin").value!='')
	   	{
	   		marginPerc=removeComma(document.getElementById("margin").value);
	   		//alert(marginPerc);
	   		ltvPerc =hundred-marginPerc;
	   		//alert(ltvPerc);
	   		document.getElementById("ltvPerc").value=ltvPerc.toFixed(2);
	   		return true;
	   	}
	   	else if(document.getElementById("repaymentType").value=='N')
	   	{
	   		document.getElementById("ltvPerc").value='100.00';
	   		return true;
	   	}
	   	else
	   	{		
	   		document.getElementById("ltvPerc").value=ltvPerc.toFixed(2);
	   		return true;
	   	}
	   	
	   	
	   }
	   function removeSplChar(id)
	   {
	       var str = id;
	       var arr = str.split('+');
	       //alert("arr length: "+arr.length);
	       var stri = '';
	       for(i=0; i<arr.length; i++){
	       	if(i<arr.length-1)
	       		stri = stri+escape(arr[i])+'%2B';
	       	if(i==arr.length-1)
	       		stri = stri+escape(arr[i]);
	       }   
	   	return stri;
	   }
	   function downloadUploadedFileOmniFin(fileName,txnId,txnType)
	   {
	   	//alert("downloadUploadedFileOmniFin File: "+removeSplChar(fileName));
		   	var sourcepath=document.getElementById("contextPath").value;
		   	location.href=sourcepath+"/underwritingDocUpload.do?method=downloadUploadedFileOmniFin&fileName="+removeSplChar(fileName)+"&txnId="+txnId+"&txnType="+txnType;
		    return true;
	   }
	   
//end by sachin
//Richa changes starts
	 //Richa changes starts
	   function calcMonth(){
	   	   var tenureInDay= document.getElementById("tenureInDay").value;
	   var dayBasis=document.getElementById("daysBasis").value;
	   	  // alert("daysBasis"+daysBasis);
	   	   
	   	   	if(tenureInDay==''){
	   	   		tenureInDay=0;
	   	   	}
	   	   	if(tenureInDay!='')
	   	   	{
	   	   		tenureInDay=parseInt(tenureInDay);
	   	   		//alert("parseTenure:-----"+parseTenure);
	   	   	}

	   if(dayBasis=="A"){
	   	   var month=tenureInDay/30.42;
	   	   }
	   else{
	   	   var month=tenureInDay/30;
	   }
	   	  // var monthVal = month.toFixed(2);
	   	   var monthVal= Math.ceil(month);
	   	   	document.getElementById("requestedLoanTenure").value=monthVal;
	   	 	document.getElementById("noOfInstall").value=monthVal;
	   	   	
	   	  
	   }
  function editNoInstal()
  {
  	 var installmentType= document.getElementById("installmentType").value;
  	 var frequency= document.getElementById("frequency").value;
  	if(installmentType=='I')
  		{
  		document.getElementById("loanNoofInstall").removeAttribute("readonly",true);
  		document.getElementById("loanNoofInstall").value=document.getElementById("tenureMonths").value;
  		}
  	else
  		{
  		document.getElementById("loanNoofInstall").setAttribute("readonly",true);
  		}
  	if(installmentType=='S' )
  		{
		document.getElementById("interestCalculationMethod").removeAttribute("disabled",true);
  		document.getElementById("interestFrequency").removeAttribute("disabled",true);
		document.getElementById("interestDueDateHeader1").style.display='';
		document.getElementById("interestDueDateHeader2").style.display='';
		document.getElementById("SeparateInterestDate").style.display='';
  		}
	else
		{
		document.getElementById("interestCalculationMethod").value='D';
		document.getElementById("interestCalculationMethod").setAttribute("disabled",true);
		document.getElementById("interestFrequency").value=frequency;
		document.getElementById("interestFrequency").setAttribute("disabled",true);
		document.getElementById("interestDueDateHeader1").style.display='none';
		document.getElementById("interestDueDateHeader2").style.display='none';
		document.getElementById("SeparateInterestDate").style.display='none';
		}
  }
	   //Richa changes ends

 function validateIntCompoundingFrequency()
{
	
	var installmentType= document.getElementById("installmentType").value;
			if(installmentType=='E'|| installmentType=='G'  ||  installmentType=='P' ||installmentType=='Q' )
			{
			var interestCompoundingFrequency= document.getElementById("interestCompoundingFrequency").value;
			var frequency= document.getElementById("frequency").value;
			if((frequency=='M' && (interestCompoundingFrequency=='M' || interestCompoundingFrequency=='')) || (frequency=='B' && (interestCompoundingFrequency=='B' ||interestCompoundingFrequency=='M' || interestCompoundingFrequency=='')) || 
				(frequency=='Q' && (interestCompoundingFrequency=='Q' ||interestCompoundingFrequency=='M' || interestCompoundingFrequency=='B' || interestCompoundingFrequency=='')) || 
				(frequency=='H' && (interestCompoundingFrequency=='H'||interestCompoundingFrequency=='M'||interestCompoundingFrequency=='B'|| interestCompoundingFrequency=='Q' || interestCompoundingFrequency==''))
			||(frequency=='Y' && (interestCompoundingFrequency=='Y'||interestCompoundingFrequency=='M'||interestCompoundingFrequency=='B'|| interestCompoundingFrequency=='Q' || interestCompoundingFrequency=='H' || interestCompoundingFrequency==''))		
			)	
			return true;
			alert('Interest Compounding Frequency can not be grater than Loan Frequency');
			document.getElementById("interestCompoundingFrequency").value='';
			return false;
			}
}
  
  
  //Adding funtion AJAY
function datevalidate()
{
	var businessDate= document.getElementById("businessdate").value;
	var interestDueDate= document.getElementById("interestDueDate").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var iMonth = interestDueDate.substring(3,5); //months from 1-12
	var iDay = interestDueDate.substring(0,2);
	var iYear = interestDueDate.substring(6);
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var dateObj =  new Date(iYear,iMonth,iDay);
	// beging of day validation 

				//var cycleDate= document.getElementById("dueDay").value;
	var cycleDate= document.getElementsByName("dueDay")[0].value;
		if(interestCalculationMethod=='D')
		{
			if(cycleDate=='')
			{
				alert('First select  Due Day ');
				document.getElementById("interestDueDate").value="";
			return false;
		    }
			
			if(cycleDate!=parseFloat(iDay)  )
			{
			
			if((parseFloat(iMonth)==1 && parseFloat(iDay)!=31) ||
				(parseFloat(iMonth)==2 && (parseFloat(iDay)!=28 && parseFloat(iDay)!=29 )) ||	
				(parseFloat(iMonth)==3 && parseFloat(iDay)!=31) || 
				(parseFloat(iMonth)==4 && parseFloat(iDay)!=30) ||
				(parseFloat(iMonth)==5 && parseFloat(iDay)!=31) || 					
				(parseFloat(iMonth)==6 && parseFloat(iDay)!=30) || 
				(parseFloat(iMonth)==7 && parseFloat(iDay)!=31) || 
				(parseFloat(iMonth)==8 && parseFloat(iDay)!=31) || 
				(parseFloat(iMonth)==9 && parseFloat(iDay)!=30) || 
				(parseFloat(iMonth)==10 && parseFloat(iDay)!=31) || 
				(parseFloat(iMonth)==11 && parseFloat(iDay)!=30) || 
				(parseFloat(iMonth)==12 && parseFloat(iDay)!=31)  )
						{
		alert('Interest Due Date Must be same as Due Day ');
				document.getElementById("interestDueDate").value="";
		return false;
	}
				}
		
}
return true;

	
}



function dateValidate(interestMedCal,interestFrequency,interestDueDate,businessDate)
{
	var iMonth = interestDueDate.substring(3,5); //months from 1-12
	var iDay = interestDueDate.substring(0,2);
	var iYear = interestDueDate.substring(6);
	
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var div = '';
	if(interestFrequency=='Q')
	div=3;
	else if(interestFrequency=='H')
	div=6;
	else if(interestFrequency=='Y')
	div=12;
	if(interestMedCal=='E')
		{
			if(parseFloat(bYear)==parseFloat(iYear))
			{
				if(parseFloat(bMonth)>=parseFloat(iMonth))
					{
					var msg = 'Interest Due month  must  be greater then Business Date month ';
					return false;
					}
			}
		}
	else if(interestMedCal=='F')
		{
			if((parseFloat(iYear)>=parseFloat(bYear)) && ((parseFloat(bMonth)>=parseFloat(iMonth))&& (parseFloat(iYear)>parseFloat(bYear))))
			{
			var calMonth=parseFloat(bMonth)+parseFloat(iMonth);
			calMonth= parseFloat(calMonth)%parseFloat(div);
			
				if(calMonth!=0)
				{
					return false;
				}
				/*alert('calMonth--3    '+parseFloat(calMonth));
					if(parseFloat(bMonth)>=parseFloat(iMonth))
						{
						var msg = 'Interest Due month  must  be greater then Business Date month ';
						return msg;
						}*/
			}
			else
			{
				return false;
			}
		}
return true;			
}
//Ajay funtion end here	

function setInterestDueDate()
{
	var formatD=document.getElementById("formatD").value;
	var businessDate= document.getElementById("businessdate").value;
	var repayEffectiveDate= document.getElementsByName("repayEffectiveDate")[0].value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var iMonth='';
	var iDay='' ;
	var iYear='';
	var rMonth = repayEffectiveDate.substring(3,5); //months from 1-12
	var rDay = repayEffectiveDate.substring(0,2);
	var rYear = repayEffectiveDate.substring(6);
	var div = '';
	if(interestFrequency=='M')
	div=1;
	if(interestFrequency=='B')
	div=2;
	if(interestFrequency=='Q')
	div=3;
	else if(interestFrequency=='H')
	div=6;
	else if(interestFrequency=='Y')
	div=12;
	var  count=0;
//	var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
//    var dt3=getDateObject(businessDate,formatD.substring(2, 3));
// 
//    if(dt1<dt3)
//	{
//		document.getElementById("interestDueDate").value='';
//	}
//	 else 
	if(interestCalculationMethod=='E')
		{
			
			iDay='01';
			iMonth=parseFloat(rMonth)+div;
			iYear=parseFloat(rYear);	
				//alert(parseFloat(rYear));
			if(iMonth>'12')
				{
					var quotient= parseInt(iMonth/12);
					var rem=iMonth%12;
								if(rem==0)
								{
									iMonth='1';
								}
								else
								{
								iMonth=rem;	
								}
							
							iYear=iYear+quotient;
				}
			var iMonthStr = '';
			if (iMonth <= 9) {
				iMonthStr = iMonth.toString();
				iMonth = (0).toString() + iMonthStr;
				// alert('iMonth......'+iMonth);
			}
						
					var iDD=  iDay+"-"+iMonth+"-"+iYear;
					
					document.getElementById("interestDueDate").value=iDD;
					
		}
		
			else if(interestCalculationMethod=='F')
			{
			
			
				iDay='01';
				if(interestFrequency=='Q')
				{
					if(parseFloat(rMonth)<'04')
						{
							iMonth='4';
							iYear=parseFloat(rYear);
							//alert(iMonth);
						}
					else if(parseFloat(rMonth)<'07')
						{
						iMonth='7';
						iYear=parseFloat(rYear);
						//alert(iMonth);
					}
				else if(parseFloat(rMonth)<'10')
					{
						iMonth=10;
						iYear=parseFloat(rYear);
						//alert(iMonth);
					}
				else if(parseFloat(rMonth)<='12')
					{
						iMonth='1';
						iYear=parseFloat(rYear)+1;
						//alert(iMonth);
					}	
			}
			else if(interestFrequency=='H')
				{
					if(parseFloat(rMonth)<'04')
					{
						iMonth='4';
						iYear=parseFloat(rYear);
						//alert(iMonth);
					} else if (parseFloat(rMonth) < '10') {
						iMonth = '10';
						iYear = parseFloat(rYear);
						// alert(iMonth);
					} 	
					 else if(parseFloat(rMonth)<='12')
					{
						iMonth='4';
						iYear=parseFloat(rYear)+1;
						//alert(iMonth);
					}
				}
			else if(interestFrequency=='Y')
				{
						iMonth='4';
						iYear=parseFloat(rYear)+1;
						//alert(iMonth);
				}
			else if(interestFrequency=='M')
				{
						iMonth=parseFloat(rMonth)+1;
						iYear=parseFloat(rYear);
						if(iMonth>'12')
						{
							var quotient= parseInt(iMonth/12);
							var rem=iMonth%12;
										if(rem==0)
										{
											iMonth='1';
										}
										else
										{
										iMonth=rem;	
										}
									
									iYear=iYear+quotient;
						}
						
				}
			else if(interestFrequency=='B')
				{
						iMonth=parseFloat(rMonth)+2;
						iYear=parseFloat(rYear);
						if(iMonth>'12')
						{
							var quotient= parseInt(iMonth/12);
							var rem=iMonth%12;
										if(rem==0)
										{
											iMonth='1';
										}
										else
										{
										iMonth=rem;	
										}
									
									iYear=iYear+quotient;
						}
				}
				var iMonthStr = '';
				if (iMonth <= 9) {
					iMonthStr = iMonth.toString();
					iMonth = (0).toString() + iMonthStr;
					// alert('iMonth......'+iMonth);
				}
				
				
			var iDD=  iDay+"-"+iMonth+"-"+iYear;
			//alert(iDD);
			document.getElementById("interestDueDate").value=iDD;
		}
		
}
function showInterestDueDate(flag)
{
	var installmentType=document.getElementById("installmentType").value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	
	if(interestCalculationMethod=='D' && installmentType=='S')
	{
		document.getElementById("interestDueDate").style.display="";
		document.getElementById("interestDueDate").parentNode.childNodes[2].style.display="";
		if(flag)
		{
		document.getElementById("interestDueDate").value="";
		}
		//document.getElementById("interestDueDate").style.display="none";
		return true;
	}
	else if((interestCalculationMethod=='E'|| interestCalculationMethod =='F') && installmentType=='S')
	{
		//document.getElementById("interestDueDate").parentNode.childNodes[2].style.display="none";
		if(flag)
		{
		document.getElementById("interestDueDate").value="";
		}
		//document.getElementById("interestDueDate").style.display="none";
		//document.getElementById("interestDueDate").style.display="";
		return true;
	}
	
}

//add by AJay for interestFrequency
function validateInterestFrequency()
{
	
	var installmentType= document.getElementById("installmentType").value;
			if(installmentType=='S' )
			{
			var interestCompoundingFrequency= document.getElementById("interestCompoundingFrequency").value;
			var interestFrequency= document.getElementById("interestFrequency").value;
			if(
			(interestCompoundingFrequency=='Y' && (interestFrequency=='Y'))||
			(interestCompoundingFrequency=='H' && (interestFrequency=='Y' || interestFrequency=='H')) ||
			(interestCompoundingFrequency=='Q' && (interestFrequency=='Y' ||interestFrequency=='H' || interestFrequency=='Q')) || 
			(interestCompoundingFrequency=='B' && (interestFrequency=='Y' ||interestFrequency=='H' || interestFrequency=='Q' || interestFrequency=='B')) || 
			(interestCompoundingFrequency=='M' && (interestFrequency=='Y'||interestFrequency=='H'||interestFrequency=='Q'|| interestFrequency=='B' || interestFrequency=='M'))||
			(interestCompoundingFrequency=='' && (interestFrequency=='Y'||interestFrequency=='H'||interestFrequency=='Q'|| interestFrequency=='B' || interestFrequency=='M' ))		
			)	
			return true;
			alert(' Interest Frequency can not be smaller than  Interest Compounding Frequency');
			document.getElementById("interestFrequency").value='';
			return false;
			}
}
//end by ajay 

//added for valid Interest Due Date by AJAY
function validateInterestDueDate()
{
	//alert('.......................');	
	var formatD=document.getElementById("formatD").value;
	var businessDate= document.getElementById("businessdate").value;
	var repayEffectiveDate= document.getElementsByName("repayEffectiveDate")[0].value;
	var interestCalculationMethod= document.getElementById("interestCalculationMethod").value;
	var interestFrequency= document.getElementById("interestFrequency").value;
	var interestDueDate= document.getElementById("interestDueDate").value;
	var bMonth = businessDate.substring(3,5); //months from 1-12
	var bDay = businessDate.substring(0,2);
	var bYear = businessDate.substring(6);
	var iMonth=interestDueDate.substring(3,5);
	var iDay=interestDueDate.substring(0,2);
	var iYear=interestDueDate.substring(6);
	var rMonth = repayEffectiveDate.substring(3,5); //months from 1-12
	var rDay = repayEffectiveDate.substring(0,2);
	var rYear = repayEffectiveDate.substring(6);
	var msg="";
	var flag='';
		var iDD=  iDay+"-"+iMonth+"-"+iYear;
	//	alert('...interestDueDate...'+iDD+'   '+interestCalculationMethod);
		if(interestCalculationMethod!='D')
		{	
			if(interestCalculationMethod=='B' || interestCalculationMethod=='G')
			{
							if(parseFloat(iDay)>'1')
							{
								alert("Day must be First day of month.");
								var interestDueDate= document.getElementById("interestDueDate").value='';
								return false;
							}
			}
			if(interestCalculationMethod=='E' || interestCalculationMethod=='F')
			{
								if((parseFloat(iMonth)==1 && parseFloat(iDay)!=31) ||
							(parseFloat(iMonth)==2 && (parseFloat(iDay)!=28 && parseFloat(iDay)!=29 )) ||	
							(parseFloat(iMonth)==3 && parseFloat(iDay)!=31) || 
							(parseFloat(iMonth)==4 && parseFloat(iDay)!=30) ||
							(parseFloat(iMonth)==5 && parseFloat(iDay)!=31) || 					
							(parseFloat(iMonth)==6 && parseFloat(iDay)!=30) || 
							(parseFloat(iMonth)==7 && parseFloat(iDay)!=31) || 
							(parseFloat(iMonth)==8 && parseFloat(iDay)!=31) || 
							(parseFloat(iMonth)==9 && parseFloat(iDay)!=30) || 
							(parseFloat(iMonth)==10 && parseFloat(iDay)!=31) || 
							(parseFloat(iMonth)==11 && parseFloat(iDay)!=30) || 
							(parseFloat(iMonth)==12 && parseFloat(iDay)!=31)  )
							{
								alert("Day must be Last day of month.");
								var interestDueDate= document.getElementById("interestDueDate").value='';
								return false;
							}
			}
					if(parseFloat(iYear)<parseFloat(rYear))
					{
						alert("Interest Due Date can not be lesser then repay Effective Date.");
						var interestDueDate= document.getElementById("interestDueDate").value='';
						return false;
					}
				
				
				if(interestCalculationMethod=='F')
				{
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='M')
					{
						/*if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}*/
						
							
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='B')
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07' || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)==parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
									
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
							
					}
					//for idd greater year then red
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='B' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='07'   || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11' )
						{
							alert('You can only take financial Bi-Monthly month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					
					
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Q' )
					{
						
						if(parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  || parseFloat(iMonth)=='05'  ||  parseFloat(iMonth)=='06' || parseFloat(iMonth)=='08'  || parseFloat(iMonth)=='09'  || parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial quarter month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='H' )
					{
						
						if(parseFloat(iMonth)=='01'  || parseFloat(iMonth)=='02'  || parseFloat(iMonth)=='03'  ||  parseFloat(iMonth)=='05' || parseFloat(iMonth)=='06'  || parseFloat(iMonth)=='07'  || parseFloat(iMonth)=='08'  ||  parseFloat(iMonth)=='09'  ||  parseFloat(iMonth)=='11'  ||  parseFloat(iMonth)=='12')
						{
							alert('You can only take financial half year  month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}
							
					}
					if(parseFloat(iYear)>parseFloat(rYear)  &&  interestFrequency=='Y' )
					{
							if(parseFloat(iMonth)!='04')
							{
							alert('Please take  next  beginning  month of  financial   year ');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
							}
							
					}
				}	
				if(interestCalculationMethod=='E')
				{
					if(parseFloat(iYear)==parseFloat(rYear))
					{
						/*if(parseFloat(rMonth)>= parseFloat(iMonth))
						{
							alert('Please take  next  financial month');
							var interestDueDate= document.getElementById("interestDueDate").value='';
							return false;
						}*/
						
							
					}	
				}	
				return true;
			}
}

//End by AJAY
//Rohit Chnages Starts///
function showDueDay(){
	var calcMethod=document.getElementById("interestCalculationMethod").value;
//	alert("calcMethod::::"+calcMethod)
		document.getElementById("cycleDate").value='';
	if(calcMethod=='B' || calcMethod=='G' )
	{
		document.getElementById("cycleDate").value='1';
	}
	if(calcMethod=='E' || calcMethod=='F' )
	{
		document.getElementById("cycleDate").value='31';
	}
	return true;
	
}

//Rohit end
//Richa changes starts
//start here | Brijesh Pathak
function calcMonths()
{
	var tenureInDay= document.getElementById("tenureInDay").value;
    var dayBasis= document.getElementById("daysBasis").value;
    if(tenureInDay=='')
	{
		tenureInDay=0;
    }
    if(tenureInDay!='')
    {
        tenureInDay=parseInt(tenureInDay);
        //alert("parseTenure:-----"+parseTenure);
    }
    if(dayBasis=="A")
	{
        var month=tenureInDay/30.42;
    }
    else
	{
        var month=tenureInDay/30;
    }
    // var monthVal = month.toFixed(2);
    var monthVal= Math.ceil(month);
    //alert("monthVal "+monthVal);
    document.getElementById("tenureMonths").value=monthVal;
    document.getElementById("loanNoofInstall").value=monthVal;
}
//end here | Brijesh Pathak



