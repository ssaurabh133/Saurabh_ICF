function allChecked()
	{
		
		var c = document.getElementById("allchk").checked;
		var ch=document.getElementsByName("chk");
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

	function checkBoxes()
	{
	    
		var ch=document.getElementsByName('chk');
		
		    var zx=0;
		    var flag=0;
		    for(i=0;i<ch.length;i++)
		{
			if(ch[i].checked==true)
			{
				zx++;
				
			}
			
		}
		if(zx>1)
		{
			alert("Please select one record to modify !!!");
			return false;
		}
		else if(zx==1)
		{
			return true;
		}
		
	}
	
	
function checkboxCheck(ch)
	{

	var zx=0;
	   var flag=0;
	   for(i=0;i<ch.length;i++)
		{
			
			if(ch[zx].checked==false)
			{
				flag=0;
			}
			else
			{
				flag=1;
				break;
			}
			zx++;
		}
		
		return flag;
	}

	function isNumberKey(evt) 
	{

	var charCode = (evt.which) ? evt.which : event.keyCode;

	if (charCode > 31 && (charCode < 48 || charCode > 57))
	{
		alert("Only Numeric and decimal allowed here");
		return false;
	}
		return true;
	}
	
	function removeComma(id)
	{
	    var str = id;
	    //alert(id);
	    var arr = str.split(',');
	    var stri = '';
	    for(i=0; i<arr.length; i++){
	        stri = stri+arr[i];
	    }   
	    var amount = parseFloat(stri);
	    //alert(amount);
		return amount;
	}

	
	function callKnockOffAmountReceivable()
	 {
//	 if(checkboxCheck(document.getElementsByName('chk')))
//	     { 
		  var sum=0;
		  var ptable=document.getElementById('dtable');
		  var len=ptable.rows.length-2;		
		  for (var i=1; i<=len; i++)
		     {
//			  if (document.getElementById('chk'+i).checked==true) 
//		      {			        
			        if(document.getElementById('knockOffAmountR'+i).value=='')
			         {
			          document.getElementById('knockOffAmountR'+i).value=0;
			          }
			        var sum = sum+removeComma(document.getElementById('knockOffAmountR'+i).value);    	    
//		      }
			  }    
	         document.getElementById('totalReveivable').value=sum.toFixed(2);		
//		}
	}
	
	function callKnockOffAmountPayable()
	{
//		if(checkboxCheck(document.getElementsByName('chk')))
//	     { 
		  var sum=0;
		  var ptable=document.getElementById('dtable1');
		  var len=ptable.rows.length-2;		
		  for (var i=1; i<=len; i++)
		     {
//			  if (document.getElementById('chk'+i).checked==true) 
//		      {				        	    
		        	    if(document.getElementById('knockOffAmountP'+i).value=='')
		        	       {
		        	    	document.getElementById('knockOffAmountP'+i).value=0;
		        	        }		        	    
		        	    var sum = sum+removeComma(document.getElementById('knockOffAmountP'+i).value);	        	    
	                 }
//	         }
		     document.getElementById('totalPayable').value=sum.toFixed(2); 				
//	}
	}
	
	function searchLoanDetails()
	{
		//document.getElementById("save").removeAttribute("disabled","true");
		//document.getElementById("saveFwd").removeAttribute("disabled","true");
		
		DisButClass.prototype.DisButMethod();
		var loanId = document.getElementById("lbxLoanNoHID").value;
		var lbxBusinessPartnearHID = document.getElementById("lbxBusinessPartnearHID").value;
		document.getElementById("adviceDtChngFlag").value='N';
		//alert("hi"+document.getElementById("hBPType").value);
		//alert("loanId "+loanId);
		if(loanId =='')
		{
			alert("Loan No. is Required");
			DisButClass.prototype.EnbButMethod();
			return false; 
		}
		if(document.getElementById("valueDate").value=='')
		{
			alert("Value Date Required !");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("valueDate").focus();
			return false; 
		}
		if(lbxBusinessPartnearHID =='')
		{
			alert("Business Partner Type is Required");
			DisButClass.prototype.EnbButMethod();
			return false; 
		}
		else
		{
			var basePath=document.getElementById("contextPath").value;
		    document.getElementById("KnockOffMaker").action=basePath+"/knockOffMakerProcess.do?method=searchLoanDetails&loanId="+loanId+"&bpId="+lbxBusinessPartnearHID;
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("KnockOffMaker").submit();		 
		    return true;
	     }
				
	}
	
	
	
	
function SaveKnockOffData(type)
{
	DisButClass.prototype.DisButMethod();
	var loanId = document.getElementById("lbxLoanNoHID").value;
	if(loanId=='')
	{
		alert("Loan Number is required");
		DisButClass.prototype.EnbButMethod();
		if(type=="P")
			document.getElementById("save").removeAttribute("disabled","true");
		if(type=="F")
			document.getElementById("saveFwd").removeAttribute("disabled","true");
		return false;
	}
	  var path = document.getElementById("contextPath").value;
	  var total_value = "";		
	  var values="";
	  var sum = removeComma(document.getElementById('totalReveivable').value);
	  var original_AmountR="";
	  var Balance_AmountR="";
	  var TxnAdviceIdR="";
	  var AmountInProcessR="";
	  var knockOffIdR="";
	  var ptable=document.getElementById('dtable');
	  var len=ptable.rows.length-2;
	  var ptable1=document.getElementById('dtable1');
	  var lenp=ptable1.rows.length-2;
	  var chkR=0;
	  var chkP=0;
	  var knockrecieve=0.00;
	  var knockpay=0.00;
	  var knockrecieve1=0;
	  var knockpay1=0;
	  var sumknockOffAmountR = 0;
		var sumknockOffAmountP =0;
	  //alert("Receivable LENGTH"+len);	

		if(undefined!=document.getElementById("valueDate") && document.getElementById("valueDate").value=='') 
	    {
		         alert("Please Select Value Date");
		         DisButClass.prototype.EnbButMethod();
			     document.getElementById("save").removeAttribute("disabled","true");
			     document.getElementById("valueDate").focus();
			     return false;
        }
		
		if(document.getElementById("adviceDtChngFlag").value=='N')
		{
		  for (var i=1; i<=lenp; i++)
		  {
		          knockpay= parseFloat(document.getElementById('knockOffAmountP'+i).value);
		          knockpay1= parseInt(document.getElementById('knockOffAmountP'+i).value);
		      if(( (!(knockpay)==0.00)||(!(knockpay1)==0))&& (document.getElementById('chkP'+i).checked==false)) 
		      {
			         alert("Please Select All Filled Amounts for Knock Off Payable");
			         DisButClass.prototype.EnbButMethod();
				     document.getElementById("save").removeAttribute("disabled","true");
			  return false;
	         }
		  
		  }
	  for (var i=1; i<=len; i++)
	     {

		//  knockpay= parseFloat(document.getElementById('knockOffAmountP'+i).value);
		//  knockpay1= parseInt(document.getElementById('knockOffAmountP'+i).value);
		  knockrecieve= parseFloat(document.getElementById('knockOffAmountR'+i).value);
		  knockrecieve1= parseInt(document.getElementById('knockOffAmountR'+i).value);
		 /* if((     (!(knockpay)==0.00)    ||    (!(knockpay1)==0)     )&&  (document.getElementById('chkP'+i).checked==false)   ) {
			  alert("Please Select All Filled Amounts for Knock Off Payable");
			  DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
			  return false;
	         }*/
		  if(((!(knockrecieve)==0.00)||(!(knockrecieve1)==0))&&  (document.getElementById('chkR'+i).checked==false))	{
			  alert("Please Select All Filled Amounts for Knock Off Recievables");
			  DisButClass.prototype.EnbButMethod();
				document.getElementById("save").removeAttribute("disabled","true");
			  return false;
	         }
		  //alert("For loop");
		  if (document.getElementById('chkR'+i).checked==true) 
	         {	
		        total_value = total_value + document.getElementById('chkR'+i).value+"@";
		        //alert("total_value"+total_value); 		        	    
		        if(document.getElementById('knockOffAmountR'+i).value=='')
		         {
		          document.getElementById('knockOffAmountR'+i).value=0;
		          }       	   	
		        	original_AmountR = original_AmountR + document.getElementById('originalAmountR'+i).value+"@";
		        	//alert("original_AmountRL: "+original_AmountR);		        	    
		        	Balance_AmountR = Balance_AmountR + document.getElementById('balanceAmountR'+i).value+"@"; 
		        	//alert("Balance_AmountR: "+Balance_AmountR);		        	    
		        	TxnAdviceIdR = TxnAdviceIdR + document.getElementById('txnAdviceIdR'+i).value+"@"; 
		        	//alert("TxnAdviceIdR: "+TxnAdviceIdR);	
		            AmountInProcessR = AmountInProcessR + document.getElementById('amountInProcessR'+i).value+"@"; 
	        	    //alert("AmountInProcessR: "+AmountInProcessR);	
	        	    knockOffIdR = knockOffIdR + document.getElementById('knockOffIdR'+i).value+"@";
/*	        	   alert("KnockOffIdR: : "+knockOffIdR);*/
		        	values = values + document.getElementById('knockOffAmountR'+i).value+"@";    
//		        	alert("knockOffAmountR: "+values);	 
		
		        	chkR=chkR+1;
		        	
	                }

	     }
	       var total_value1 = "";
		   var values1="";
		   var sum1 = removeComma(document.getElementById('totalPayable').value);
		   var original_AmountP="";
		   var Balance_AmountP="";
		   var TxnAdviceIdP="";
		   var AmountInProcessP="";
		   var knockOffIdP="";
		   var ptable1=document.getElementById('dtable1');
		   var len1=ptable1.rows.length-2;
		   //alert("Payable LENGTH"+len1);		
		     for (var j=1; j<=len1; j++)
	         {
		    	 //alert("For loop");
		         if (document.getElementById('chkP'+j).checked==true) 
	             {
		        	 total_value1 = total_value1 + document.getElementById('chkP'+j).value+"@";
		        	 //alert("total_value1"+total_value1);		        	    
		        	 if(document.getElementById('knockOffAmountP'+j).value=='')
		        	 {
		        		 document.getElementById('knockOffAmountP'+j).value=0;
		        	 }		        	    
		        	 original_AmountP = original_AmountP + document.getElementById('originalAmountP'+j).value+"@";
		        	 //alert("original_AmountP: "+original_AmountP);		        	    
		        	 Balance_AmountP = Balance_AmountP + document.getElementById('balanceAmountP'+j).value+"@";  
		        	 //alert("Balance_AmountP: "+Balance_AmountP);			        	    
		        	 TxnAdviceIdP = TxnAdviceIdP + document.getElementById('txnAdviceIdP'+j).value+"@";  
		        	 //alert("TxnAdviceIdP: "+TxnAdviceIdP);	
		        	 AmountInProcessP = AmountInProcessP + document.getElementById('amountInProcessP'+j).value+"@"; 
	        	     //alert("AmountInProcessP: "+AmountInProcessP);
	        	     knockOffIdP = knockOffIdP + document.getElementById('knockOffIdP'+j).value+"@";
//		        	 alert("knockOffIdP: "+knockOffIdP);
		        	 values1 = values1 + document.getElementById('knockOffAmountP'+j).value+"@";	        	   
//		        	alert("knockOffAmountP: "+values1);
		        	
		        	 chkP=chkP+1;
		          }
	         }	
		
		   if (sum == sum1)
	         {
	        	 var checkKnockoffAndBalanceStr = checkKnockoffAndBalance();
	        	 
//	        	 if(document.getElementById("remarks").value=='')
//	        	 {
//	        		 alert("Remarks is Required");
//	        		 DisButClass.prototype.EnbButMethod();
//	            	 if(type=="P")
//	            		document.getElementById("save").removeAttribute("disabled","true");
//	            	 if(type=="F")
//	            		document.getElementById("saveFwd").removeAttribute("disabled","true");
//	            		
//	            	 document.getElementById("remarks").focus();
//	            	 return false;
//	        	 }
	        	 if(document.getElementById("businessPartnerName").value=='')
	        	 {
	        		 alert("Business Partner Name is Required");
	        		 DisButClass.prototype.EnbButMethod();
	            		if(type=="P")
	            			document.getElementById("save").removeAttribute("disabled","true");
	            		if(type=="F")
	            			document.getElementById("saveFwd").removeAttribute("disabled","true");
	            		
	            		document.getElementById("businessPartnerName").focus();
	            		return false;
	        	 }
	        	 if(chkR==0)
	          	 {
	        		 alert("Please Select the Receivable Amount to be Knocked off");
	        		 DisButClass.prototype.EnbButMethod();
	          		 if(type=="P")
	          			document.getElementById("save").removeAttribute("disabled","true");
	          		 if(type=="F")
	          			document.getElementById("saveFwd").removeAttribute("disabled","true");
	          		 return false;
	          	 }
            	 if(chkP==0)
  			  	 {
  				  	 alert("Please Select the Payable Amount to be Knocked off");
  				  	DisButClass.prototype.EnbButMethod();
  					 if(type=="P")
  						 document.getElementById("save").removeAttribute("disabled","true");
  					 if(type=="F")
  						 document.getElementById("saveFwd").removeAttribute("disabled","true");
  					 return false;
  			  	 }
            	 if(checkKnockoffAndBalanceStr=="countR")
            	 {
            		 alert("Knock Off Amount Receivable cannot be greater than Balance Amount of Advice");
            		 DisButClass.prototype.EnbButMethod();
            		 if(type=="P")
  						 document.getElementById("save").removeAttribute("disabled","true");
  					 if(type=="F")
  						 document.getElementById("saveFwd").removeAttribute("disabled","true");
  					 return false;
            	 }
            	 if(checkKnockoffAndBalanceStr=="countP")
            	 {
            		 alert("Knock Off Amount Payable cannot be greater than Balance Amount of Advice");
            		 DisButClass.prototype.EnbButMethod();
            		 if(type=="P")
  						 document.getElementById("save").removeAttribute("disabled","true");
  					 if(type=="F")
  						 document.getElementById("saveFwd").removeAttribute("disabled","true");
  					 return false;
            	 }
            	 if(checkKnockoffAndBalanceStr=="ok")
            	 {
            		 if(checkKnockoffAmtZero())
            		 {
            			 //Ravi start
            			 DisButClass.prototype.EnbButMethod();
            			 
            			   if(type=='F') 
            			   {
            			    if(document.getElementById("loanRecStatus").value!='')
            			    {
            			    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
            			    	{
            			    		var status = confirm("Loan is on pending stage. Do you want to continue..");
            			    		//alert("status :"+ status);
            			    		if(!status)
            			    		{
            			    			document.getElementById("saveFwd").removeAttribute("disabled","true");
            							DisButClass.prototype.EnbButMethod();
            							return false;
            			    		}
            			    	}else if(document.getElementById("loanRecStatus").value=='C')
            			    	{
            			    		var status = confirm("Loan is close. Do you want to continue..");
            			    		//alert("status :"+ status);
            			    		if(!status)
            			    		{
            			    			document.getElementById("saveFwd").removeAttribute("disabled","true");
            							DisButClass.prototype.EnbButMethod();
            							return false;
            			    		}
            			    	}
            			    	
            			    }
            			   }
            			    //Ravi End
            			 
						 document.getElementById("KnockOffMaker").action=path+"/knockOffMakerProcess.do?method=saveKnockOffDetails&ReceiveValues="+values+"&PayableValues="+values1+"&OriginalAmountR="+original_AmountR+"&BalanceAmountR="+Balance_AmountR+"&OriginalAmountP="+original_AmountP+"&BalanceAmountP="+Balance_AmountP+"&TXNAdviceIdR="+TxnAdviceIdR+"&TXNAdviceIdP="+TxnAdviceIdP+"&AmountInProcess_R="+AmountInProcessR+"&AmountInProcess_P="+AmountInProcessP+"&knockOffIdR="+knockOffIdR+"&knockOffIdP="+knockOffIdP+"&RSum="+sum+"&PSum="+sum1+"&loanId="+loanId+"&recStatus="+type;
						 document.getElementById("processingImage").style.display = '';
						 document.getElementById("KnockOffMaker").submit();	
						 return true;
            		 }
            		 else
            		 {
            			 DisButClass.prototype.EnbButMethod();
            			 if(type=="P")
            				 document.getElementById("save").removeAttribute("disabled","true");
         	        	 if(type=="F")
         	        		 document.getElementById("saveFwd").removeAttribute("disabled","true");
         	             return false;
            		 }
            	 }
            	 
	         }
	         
	         else
	         {
	            alert("Sum of Knock Off Amount in Receivable Advices should be Equal to Payable Advices.");
	            DisButClass.prototype.EnbButMethod();
	            if(type=="P")
	        		document.getElementById("save").removeAttribute("disabled","true");
	        	if(type=="F")
	        		document.getElementById("saveFwd").removeAttribute("disabled","true");
	            return false;
	         }
		}
		else
		{
			 	alert("Please search again due to change in value date");
	            DisButClass.prototype.EnbButMethod();
	            if(type=="P")
	        		document.getElementById("save").removeAttribute("disabled","true");
	        	if(type=="F")
	        		document.getElementById("saveFwd").removeAttribute("disabled","true");
	            return false;
		}
}

function checkKnockoffAndBalance()
{
	var ptable=document.getElementById('dtable');
	var len=ptable.rows.length-2;
	var ptable1=document.getElementById('dtable1');
	var len1=ptable1.rows.length-2;
	var countR = 0;
	var countP = 0;
	var balanceAmountR = 0;
	var balanceAmountP = 0;
	var knockOffAmountR = 0;
	var knockOffAmountP = 0;

	for (var i=1; i<=len; i++)
    {
		//alert("For loop");
		if(document.getElementById('chkR'+i).checked==true)
	    {
			balanceAmountR = removeComma(document.getElementById('balanceAmountR'+i).value);
			knockOffAmountR = removeComma(document.getElementById('knockOffAmountR'+i).value);

			if(knockOffAmountR > balanceAmountR)
			{
				countR = countR+1;

			}
	    }
    }
	
	for (var j=1; j<=len1; j++)
    {
		//alert("For loop");
		 if(document.getElementById('chkP'+j).checked==true) 
         {
			 balanceAmountP = removeComma(document.getElementById('balanceAmountP'+j).value);
			 knockOffAmountP = removeComma(document.getElementById('knockOffAmountP'+j).value);
	
	
			 if(knockOffAmountP > balanceAmountP)
			 {
				 countP = countP+1;

			 }
         }
    }
	
	if(countR>0)
		return "countR";
	if(countP>0)
		return "countP";

	else
		return "ok";
}

function checkKnockoffAmtZero()
{
	var ptable=document.getElementById('dtable');
	var len=ptable.rows.length-2;
	var ptable1=document.getElementById('dtable1');
	var len1=ptable1.rows.length-2;
	var countR = 0;
	var countP = 0;
	var knockOffAmountR = 0;
	var knockOffAmountP = 0;
	
	for (var i=1; i<=len; i++)
    {
		//alert("For loop");
		if(document.getElementById('chkR'+i).checked==true)
	    {
			knockOffAmountR = removeComma(document.getElementById('knockOffAmountR'+i).value);
			if(knockOffAmountR == 0)
			{
				countR = countR+1;
			}
	    }
    }
	
	for (var j=1; j<=len1; j++)
    {
		//alert("For loop");
		 if(document.getElementById('chkP'+j).checked==true) 
         {
			 knockOffAmountP = removeComma(document.getElementById('knockOffAmountP'+j).value);
			 if(knockOffAmountP == 0)
			 {
				 countP = countP+1;
			 }
         }
    }
	if(countR>0 || countP>0)
	{
		alert("Knockoff Amount cannot be Zero for Receivable or Payable Advices");
		for (var i=1; i<=len; i++)
		{
			if(removeComma(document.getElementById('knockOffAmountR'+i).value)==0)
				document.getElementById('chkR'+i).checked=false;
		}
		for (var j=1; j<=len1; j++)
		{
			if(removeComma(document.getElementById('knockOffAmountP'+j).value)==0)
				document.getElementById('chkP'+j).checked=false;
		}
		return false;
	}
	
	else
	{
		return true;
	}
}

function forwardBeforeSave(alert1)
{
	DisButClass.prototype.DisButMethod();
	alert(alert1);
	DisButClass.prototype.EnbButMethod();
	return false;
}		
		
	function openNewKnockOff()
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("knockOffSearchForm").action = contextPath+"/knockOffSearch.do?method=openNew";
	    document.getElementById("knockOffSearchForm").submit();
	}
	
	function searchKnockOff(type,alert1)
	{
		DisButClass.prototype.DisButMethod();
		if(!(document.getElementById("loanNo").value)=='' || !(document.getElementById("customerName").value)=='' || !(document.getElementById("businessPartnerType").value)=='' || !(document.getElementById("businessPartnerName").value)==''|| !(document.getElementById("reportingToUserId").value)=='')
		{
			var contextPath=document.getElementById("contextPath").value;
			document.getElementById("knockOffSearchForm").action = contextPath+"/knockOffSearch.do?method=searchKnockOffData&type="+type;
			document.getElementById("processingImage").style.display = '';
		    document.getElementById("knockOffSearchForm").submit();
		    return true;
		}
		else
		{
			alert(alert1);
			DisButClass.prototype.EnbButMethod();
			document.getElementById("search").removeAttribute("disabled","true");
		}
	}
	
	 function hideAsterik(value){
   	  
   	  if(value!='A')
   	  {
   		  document.getElementById("hideAsterik").style.display ='';
   	  }
   	  else
   	  {
   		  document.getElementById("hideAsterik").style.display ='none';
   	  }
   		  
     }
	
	function saveKnockOffAuthor()
	{
		DisButClass.prototype.DisButMethod();
		//alert("save Author");
		if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
		{
			alert("Please Select the required field ");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("Save").removeAttribute("disabled","true");
			return false;
		}

		else
		{
			//Ravi start
			 DisButClass.prototype.EnbButMethod();
			 
			    if(document.getElementById("loanRecStatus").value!='')
			    {
			    	if(document.getElementById("loanRecStatus").value=='P' || document.getElementById("loanRecStatus").value=='F')
			    	{
			    		var status = confirm("Loan is on pending stage. Do you want to continue..");
			    		//alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}else if(document.getElementById("loanRecStatus").value=='C')
			    	{
			    		var status = confirm("Loan is close. Do you want to continue..");
			    		//alert("status :"+ status);
			    		if(!status)
			    		{
			    			document.getElementById("save").removeAttribute("disabled","true");
							DisButClass.prototype.EnbButMethod();
							return false;
			    		}
			    	}
			    	
			    }
			  
			    //Ravi End
			 
			
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("knockOffAuthorForm").action = contextPath+"/knockOffAuthor.do?method=saveKnockOffAuthor";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("knockOffAuthorForm").submit();
		    return true;
		}
	}
function deleteKnockOff(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	
	var knockOffId=document.getElementById("knockOffId").value;
	//alert("knockOffId......"+knockOffId);
	
	agree=confirm("Are you sure,You want to delete this KnockOff.Do you want to continue?");
	if (!(agree))
	{
    	document.getElementById("save").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("KnockOffMaker").action=sourcepath+"/knockOffMakerProcess.do?method=deleteKnockOff&knockOffId"+knockOffId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("KnockOffMaker").submit();
		return true;
}
	
	
}
function checkValueDate()
{
	var formatD=document.getElementById("formatD").value;
 	var valueDate = document.getElementById("valueDate").value;
 	var valDate=getDateObject(valueDate,formatD.substring(2, 3));
 	 var businessdate = document.getElementById("businessdate").value;
     var busDate = getDateObject(businessdate, formatD.substring(2, 3));
     
     //document.getElementById("adviceDtChngFlag").value='Y';
     
    if(valDate>busDate)
 		{
 			alert("Value Date should be less than equal to Business Date");
 			document.getElementById("valueDate").value='';
 			document.getElementById("valueDate").focus();
 			//document.getElementById("save").setAttribute("disabled","true");
 			//document.getElementById("saveFwd").setAttribute("disabled","true");
 			 return false;
 		}
     else
     { 
    	   // document.getElementById("save").setAttribute("disabled","true");
    		//document.getElementById("saveFwd").setAttribute("disabled","true");
      	return true;
     }
 }