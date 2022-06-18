function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}
function manageChildDoc(){
	var skillsSelect=document.getElementById("documentStatus");
	var selectedText=skillsSelect.options[skillsSelect.selectedIndex].text;
	var documentTypeName=document.getElementById("documentTypeName").value;
	if(documentTypeName!=''){
		documentTypeName=documentTypeName+"("+selectedText+")";
	}else{
		documentTypeName=selectedText;
	}
	document.getElementById("documentTypeName").value=documentTypeName;
	document.getElementById("childDocId").value=document.getElementById("documentStatus").value;
}
function trim(str) {
return ltrim(rtrim(str));
}

function isWhitespace(charToCheck) {
var whitespaceChars = " \t\n\r\f";
return (whitespaceChars.indexOf(charToCheck) != -1);
}

function ltrim(str) { 
for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
return str.substring(k, str.length);
}
function rtrim(str) {
for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
return str.substring(0,j+1);
}


function saveDocument(page,result)
{
	  DisButClass.prototype.DisButMethod();
	
	   var  formatDate=document.getElementById("formatD").value;
	   
	  //alert(formatDate);
		var  bDate=document.getElementById("bDate").value;
		 var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='';
		 
	// ***** Changes by Amit Starts
		 var addMsg1='', addMsg2='', addMsg3='', addMsg4='', addMsg5='', addMsg6='', addMsg7='', addMsg8='', addMsg9='',addMsg10='',addMsg11='',addMsg12='';
	//***** Changes by Amit Ends
 var msg10='',msg11='';		 
		 
	//	alert("R Date: "+rDate+"\nbDate: "+bDate);
		dt2=getDateObject(bDate,formatDate.substring(2, 3));
	    // alert(dt2);
	 var contextPath =document.getElementById('contextPath').value ;
	
	 if(page=='AD')
	 {
		
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 //alert("i");
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 var docType=document.getElementById("vDocType"+i).value;//By Virender
 			if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
			   		msg1="*Please Select Recieve Date of "+docName+"\n";
			   							
				 }
			   	 else
				 {
			   		
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Business Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
			   		
						
				  }
			   	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Business Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
				   								
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
			
			 }
			 else if(status[i].value=='D')
			 {
				 
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferral Date "+docName+"\n";
					 //alert("");
				 }
			  /* 	else
				 {
			   		
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		//alert("dt1 "+dt1);
			   		//alert("dt2 "+dt2);
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Business Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
			   		
						
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks of "+docName+"\n";
			   	 }
	
									
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
							
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 									
			 }
			 else if(status[i].value=='P')
			 {					
				   	msg4="*Document status can not be Pending \n"			 									
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
				//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }
 			
 			
		 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }		 }
// Changes By Amit Starts
		 var psize=document.getElementById("psize").value;
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 var count = 0;
		 if(rowCount>1)
		 {
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 document.getElementById("docNameAdditional"+index)
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Business Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferral Date of "+docNameAdditional+"\n";							 
						 }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }
					 
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {		
						 msg4="*Document status can not be Pending \n";							
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!='' ||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!='' || addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
			 if(checkStatus=='0')
			 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
				 document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=applicationDoc";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("DocumentForm").submit();
			 }
			// Changes By Amit Ends
	 }
	 if(page=='A')
	 {

		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;		
			 if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
					
				 }
			   	 else
				 {
			   		
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
			   		
						
				  }
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
				   								
					  }
				 }
			   	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
			   	 //break;					
			 }
			 else if(status[i].value=='D')
			 {
				 
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date of "+docName+"\n";
					 //alert("");
				 }
			   /*	 else
				 {
			   		
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
			   		
						
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 
//			   	 if(msg1!='' || msg2!=''||msg3!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2+msg3);
//			   	 }
			   	// break; 
									
			 }
			
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
				//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }
 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }		 }
		
		 // Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					   /*	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {	
						 msg4="*Document status can not be Pending \n";								
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
				 
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
			 if(checkStatus=='0')
			 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
				 document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=applicantDoc";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("DocumentForm").submit();
			 }
			// Changes By Amit Ends
	 }
	 if(page=='CA')
	 {
	
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
					
				 }
			   	 else
				 {
			   		
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
				 }
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
				   								
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
				 
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date of "+docName+"\n";
					 //alert("");
				 }
			/*   	else
				 {
			   		
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
			   		
						
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 
									
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
							
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 									
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 									
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

			 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }			
			
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					   /*	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {		
						 msg4="*Document status can not be Pending \n";								
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
		 if(checkStatus=='0')
		 {
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=coApplicantDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
		 //Changes By Amit Ends
	 }
	 if(page=='G')
	 {
		
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
					
				 }
			   	 else
				 {
			   		
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
				 }
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
				   								
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
				 
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			 /*  	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
				 msg4="*Document status can not be Pending \n";
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					 /*  	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {		
						 msg4="*Document status can not be Pending \n";							
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
			 if(checkStatus=='0')
			 {
				 document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=guarantorDoc";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("DocumentForm").submit();
			 }
		//Changes By Amit Ends
	 }
	 if(page=='C')
	 {
		
		 var checkStatus='0';
		 var status=document.getElementsByName("status");		 
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";;
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
				 }
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			/*   	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End 
			 }
			 
 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					 /*  	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {		
						 msg4="*Document status can not be Pending \n";							
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
					 
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
			 if(checkStatus=='0')
			 {
			   document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=collateralDoc";
			   document.getElementById("processingImage").style.display = '';
			   document.getElementById("DocumentForm").submit();
			 }
		//Changes By Amit Ends
	 }
	 if(page=='AS')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	 if(document.getElementById("recievedDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recievedDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recievedDate"+i).value='';
					   // return false;
			   		}
				 }
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expiryDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expiryDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (dt1<dt2)
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expiryDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			 /*  	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5=" *Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';	 
				 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg10="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					/*   	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {		
						 msg4="*Document status can not be Pending \n";							
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg11="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
			 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg10!=''||
					 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''|| addMsg11!='')
		   	 {
		   		 checkStatus='1';
		   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg10+addMsg11);
		   		DisButClass.prototype.EnbButMethod();
		   	 }
			 if( checkStatus=='0')
			 {
				 document.getElementById("DocumentForm").action = contextPath+"/documentProcessAction.do?method=assetDoc";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById("DocumentForm").submit();
			 }
		//Changes By Amit Ends
	 }
	 return false;
}

function check()
{
   // alert("Checked");
	var ch=document.getElementsByName('chk');
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

function allChecked()
{
	//alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	//alert(c);
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



function deleteUploadDoc()
{
	//alert("Delete row");
	DisButClass.prototype.DisButMethod();
	    if(check())
	    {
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=deleteUploadDocData";
		 	document.getElementById("processingImage").style.display = '';
			document.getElementById("underwritingDocUpload").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}

function submitDocUpload()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if(validateDocUpload())
	{
		document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=uploadUnderwritingDocData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("underwritingDocUpload").submit();
		return true;
	}
	else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function validateDocUpload()
{
var fup = document.getElementById('docFile');
var fileName = fup.value;
var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
var docType=document.getElementById("docTypeId");
if(docType!='undefined' && docType!=null && docType!=" "){
	docType=document.getElementById("docTypeId").value;
}
else{
	docType="";
}
ext=ext.toLowerCase();
if(document.getElementById("docDescription").value=="")
{
	alert("Please Enter Document Description");
	document.getElementById("docDescription").focus;
	return false;
}
if(document.getElementById('docFile').value=="")
{
	alert("Choose file to be uploaded.");
	document.getElementById("docFile").focus(); 
    return false; 
}
if(ext == 'xlsx'|| ext=='XLSX')
{
	alert("xlsx File Can not be Uploaded");
	return false;
}
if(fileName.indexOf('\'')!=-1)
{
	alert("File Name contains characrters(\') which are not allowed");
	fup.focus();
	return false;
}

if(docType=="GOLD_ORNAM"){

if(ext == "JPEG" || ext == "jpeg" || ext == "PNG" || ext == "png" || ext == "JPG" || ext == "jpg")
{
	return true;
} 
else
{
	alert("Upload JPG or JPEG or PNG File only.");
	fup.focus();
	return false;
}
}
else{
	if(ext == "doc" ||ext == "docx"|| ext == "JPEG" || ext == "jpeg" || ext == "JPG" 
		|| ext == "jpg" || ext == "xls" || ext == "xlsx" ||ext == "pdf" ||ext == "zip"||ext == "rar"||ext == "rtf"||ext == "pptx"||ext == "ppt"||ext=="tif"||ext=="TIF")
	{
		return true;
	} 
	else
	{
	alert("Upload DOC or XLS or PDF or JPEG or JPG  or ZIP or RAR or RTF or PPT or TIF File only");
	fup.focus();
	return false;
	}
	
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

function downloadFile(fileName)
{
	//alert("download File: "+removeSplChar(fileName));
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=downloadUnderwritingFile&fileName="+removeSplChar(fileName);
	document.getElementById("underwritingDocUpload").submit();
	return true;
}

function saveDocumentPOD(page,result)
{
	   //alert(page);
	   var  formatDate=document.getElementById("formatD").value;
	   
	  //alert(formatDate);
		var  bDate=document.getElementById("bDate").value;
		 var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='';
		 
	// ***** Changes by Amit Starts
		 var addMsg1='', addMsg2='', addMsg3='', addMsg4='', addMsg5='', addMsg6='', addMsg7='', addMsg8='',addMsg9='',addMsg10='';
	//***** Changes by Amit Ends
		 
		 
	//	alert("R Date: "+rDate+"\nbDate: "+bDate);
		dt2=getDateObject(bDate,formatDate.substring(2, 3));
	   // alert(dt2);
	 var contextPath =document.getElementById('contextPath').value ;
	// alert(page);
	 if(page=='AD')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
	//	 alert(status);
		 for(i=0;i<status.length;i++)
		 {
	//		 alert(status[i].value);
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
	//			 alert(document.getElementById("recivDate"+i).value);
			   	/* if(document.getElementById("recivDate"+i).value=='')
				 {
			   		msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				  }*/
			   	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
				if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			   /*	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks of "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
					 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
			 
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");			 
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					 /*  	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 
					 else if(additionalDocStatus[i].value=='P')
					 {
					   		msg4="*Document status can not be Pending \n";
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
					 
			 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''|| msg9 !='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!='' ||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=applicationDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
		// Changes By Amit Ends
	 }
	 if(page=='A')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	/* if(document.getElementById("recivDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				  }*/
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
			 	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
			   	 //break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date of "+docName+"\n";
					 //alert("");
				 }
			  /* 	 else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
//			   	 if(msg1!='' || msg2!=''||msg3!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2+msg3);
//			   	 }
			   	// break; 
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
				   		 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
				 
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
			 
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					   	/* else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(additionalDocStatus[i].value=='P')
					 {
					   		msg4="*Document status can not be Pending \n";
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
					 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
					 var Des = '';
					 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
						 if(additionalDocStatus=='R')
							 Des = 'Received';
						 if(additionalDocStatus=='W')
							 Des = 'Waived';
						 if(additionalDocStatus=='P')
							 Des = 'Pending';
						 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
					 }
					 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
						 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
					 }
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg9!='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=applicantDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
		// Changes By Amit Ends
	 }
	 if(page=='CA')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   /*	 if(document.getElementById("recivDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				 }*/
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
			 	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date of "+docName+"\n";
					 //alert("");
				 }
			  /* 	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
				   		 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					  /* 	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(additionalDocStatus[i].value=='P')
					 {
					   		msg4="*Document status can not be Pending \n";
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
				 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
				 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
				 var Des = '';
				 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
					 if(additionalDocStatus=='R')
						 Des = 'Received';
					 if(additionalDocStatus=='W')
						 Des = 'Waived';
					 if(additionalDocStatus=='P')
						 Des = 'Pending';
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
				 }
				 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''|| msg9!='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=coApplicantDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
		// Changes By Amit Ends
	 }
	 if(page=='G')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   /*	 if(document.getElementById("recivDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				 }*/
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
			 	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			   /*	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
				   		 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					  /* 	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 
					  else if(additionalDocStatus[i].value=='P')
						 {
						   		msg4="*Document status can not be Pending \n";
						 }
					  else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
						 {
						  addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
						 }
				 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
				 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
				 var Des = '';
				 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
					 if(additionalDocStatus=='R')
						 Des = 'Received';
					 if(additionalDocStatus=='W')
						 Des = 'Waived';
					 if(additionalDocStatus=='P')
						 Des = 'Pending';
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
				 }
				 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''|| msg9!='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=guarantorDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
		// Changes By Amit Ends
	 }
	 if(page=='C')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			  /* 	 if(document.getElementById("recivDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";;
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				 }*/
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
			 	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			  /* 	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5="*Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }			 
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
				   		 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
			 }

var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
				 if(status[i].value=='R')
					 Des = 'Received';
				 if(status[i].value=='W')
					 Des = 'Waived';
				 if(status[i].value=='P')
					 Des = 'Pending';
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
			 }
			 else if(status[i].value=='D' && vDocType == 'NA'){
				 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
			 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					 /*  	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }	
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(document.getElementById("additionalDocStatus"+index).value=='P')
					 {
					   		msg4="*Document status can not be Pending \n";
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y' && !document.getElementById("additionalDocStatus"+index).value=='L' )
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
				 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
				 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
				 var Des = '';
				 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
					 if(additionalDocStatus=='R')
						 Des = 'Received';
					 if(additionalDocStatus=='W')
						 Des = 'Waived';
					 if(additionalDocStatus=='P')
						 Des = 'Pending';
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
				 }
				 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''|| msg9!='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=collateralDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
	// Changes By Amit Ends
	 }
	 if(page=='AS')
	 {
		 var checkStatus='0';
		 var status=document.getElementsByName("status");
		 for(i=0;i<status.length;i++)
		 {
			 var docName=document.getElementById("docName"+i).value;
			 var mandOrNonMand=document.getElementById("mandOrNonMand"+i).value;
			 if(status[i].value=='R')
			 {
			   	/* if(document.getElementById("recivDate"+i).value=='')
				 {
					msg1="*Please Select Recieve Date of "+docName+"\n";
				 }
			   	 else
				 {
			   		var rDate=document.getElementById("recivDate"+i).value;
			   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
			   		if (dt1>dt2)
			   		{
			   			msg5="*Recieved Date can not be greater than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("recivDate"+i).value='';
					   // return false;
			   		}
				 }*/
			 	if(document.getElementById("flagExp"+i).value=='Y')
				 {
			   		if(document.getElementById("expirDate"+i).value=='')
			   		{
			   			msg2="*Please Select Expiry Date of "+docName+"\n";
			   		}
				   	 else
					 {
				   		var eDate=document.getElementById("expirDate"+i).value;
				   		dt1=getDateObject(eDate,formatDate.substring(2, 3));
				   		if (document.getElementById("docStatsCheck"+i).value!='R' && (dt1<dt2))
				   		{
				   			msg6="*Expiry Date can not be less than Bussiness Date of "+docName+"\n";
				   		    document.getElementById("expirDate"+i).value='';
						   // return false;
				   		}
					  }
				 }
			 	if(document.getElementById("docFlag"+i).value=='Y' && document.getElementById("childId"+i).value=='' && document.getElementById("docStatsCheck"+i).value!='R')
			   	{
			   		msg7="*Please select at least one child of "+docName+"\n";
			   	}
//			   	if(msg1!='' || msg2!='')
//			   	 {
//			   		 checkStatus='1';
//			   		 alert(msg1+msg2);
//			   	 }
//			   	 break;					
			 }
			 else if(status[i].value=='D')
			 {
			   	 if(document.getElementById("deferedDate"+i).value=='')
				 {
			   		 msg1="*Please Select Deferal Date "+docName+"\n";
					 //alert("");
				 }
			 /*  	else
				 {
			   		var dDate=document.getElementById("deferedDate"+i).value;
			   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
			   		if ((dt1 == dt2) || (dt1 < dt2))
			   		{
			   			msg5=" *Deffral Date can not be less than Bussiness Date of "+docName+"\n";
			   		    document.getElementById("deferedDate"+i).value='';
					   // return false;
			   		}
				  } */
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg2="*Please Select Remarks "+docName+"\n";
			   	 }
			 }
			 else if(status[i].value=='W' && mandOrNonMand!='MANDATORY')
			 {
			   	 if(document.getElementById("remarks"+i).value=='')
			   	 {
			   		msg3="*Please Select Remarks \n";
			   	 }
			 }
			 else if(status[i].value=='P')
			 {
					msg4="*Document status can not be Pending \n"	
			 }
			 else if(mandOrNonMand=='MANDATORY')
			 {				
				 if(status[i].value=='W')
				 {				   	
				   		 msg5="*"+docName+" Is Mandatory Document , It can not be waived \n";
				 }
					//Virender Start
				 else if(status[i].value=='N')
				 {
			 		msg3="*Mandatory Documents Can not be marked 'NA' \n";
			   	 }
	 			//Virender End
				 
			 }

 var vDocType = document.getElementById("vDocType"+i).value;
			 var Des = '';
			 if((status[i].value=='R' || status[i].value=='W' || status[i].value=='P') && vDocType != 'NA' ){
			 if(status[i].value=='R')
				 Des = 'Received';
			 if(status[i].value=='W')
				 Des = 'Waived';
			 if(status[i].value=='P')
				 Des = 'Pending';
			 msg9="* Document Type '"+vDocType+"' Is Not Applicable For "+Des+" Documents \n";
		 }
		 else if(status[i].value=='D' && vDocType == 'NA'){
			 msg9="* Document Type '"+vDocType+"' Is Not Applicable For Deferred Documents \n";
		 }
		 }
// Changes By Amit Starts
		 
		 var psize=document.getElementById("psize").value;
		 //alert("psize: "+psize);
		 //var additionalDocStatus=document.getElementsByName("additionalDocStatus");
		 var table = document.getElementById("gridtable");
		 var rowCount = table.rows.length;
		 //alert("rowCount: "+rowCount);
		 var count = 0;
		 if(rowCount>1)
		 {
			 //alert("Inside rowcount if");
			 var additionalDocStatus=document.getElementsByName("additionalDocStatus");
			 var mandatoryOrNonMandatory=document.getElementsByName("mandatoryOrNonMandatory");
			 var addDoc = document.getElementsByName("docNameAdditional");	
			 var arr = document.getElementsByName("chk");			
			 for(i=0;i<addDoc.length;i++)
			 {
				 document.getElementById("chk"+i).checked=true;				 
				 var index = arr[i].value;
				 if(addDoc[i].value!='')
				 {
					 count++;
					 var docNameAdditional='';
					 if(document.getElementById("docNameAdditional"+index).value=='')
					 {
						 addMsg8="*Please Enter Additional Document Name";
					 }
					 docNameAdditional=document.getElementById("docNameAdditional"+index).value;
					 //alert("docNameAdditional: "+docNameAdditional);
					 //alert("additionalDocStatus[i].value: "+document.getElementById("additionalDocStatus"+index).value);
					 if(document.getElementById("originalOrCopy"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Original or Photocopy\n";
					 }
					 if(document.getElementById("mandatoryOrNonMandatory"+index).value=='')
					 {
						 addMsg4="*Please Select "+docNameAdditional+" is Mandatory or Non-Mandatory\n";
					 }
					 if(document.getElementById("additionalDocStatus"+index).value=='R')
					 {
					   	 if(document.getElementById("additionalReceivedDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Recieve Date of "+docNameAdditional+"\n";
						 }
					   	 else
						 {
					   		var rDate=document.getElementById("additionalReceivedDate"+index).value;
					   		dt1=getDateObject(rDate,formatDate.substring(2, 3));
					   		if (dt1>dt2)
					   		{
					   			addMsg5="*Recieved Date can not be greater than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalReceivedDate"+index).value='';
							   // return false;
					   		}	
						  }
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='D')
					 {
					   	 if(document.getElementById("additionalDeferredDate"+index).value=='')
						 {
					   		addMsg1="*Please Select Deferal Date of "+docNameAdditional+"\n";
							 //alert("");
						 }
					 /*  	 else
						 {
					   		var dDate=document.getElementById("additionalDeferredDate"+index).value;
					   		dt1=getDateObject(dDate,formatDate.substring(2, 3));
					   		if ((dt1 == dt2) || (dt1 < dt2))
					   		{
					   			addMsg5="*Deffral Date can not be less than Bussiness Date of "+docNameAdditional+"\n";
					   		    document.getElementById("additionalDeferredDate"+index).value='';
							   // return false;
					   		}
						  } */
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg2="*Please Select Remarks for "+docNameAdditional+"\n";
					   	 }					
					 }
					 else if(document.getElementById("additionalDocStatus"+index).value=='W' && document.getElementById("mandatoryOrNonMandatory"+index).value=='N')
					 {		
					   	 if(document.getElementById("additionalRemarks"+index).value=='')
					   	 {
					   		addMsg3="*Please Select Remarks \n";
					   	 }								
					 }
					 //Virender Start
					 else if(document.getElementById("additionalDocStatus"+index).value=='N' && document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {		
				   		addMsg3="*Mandatory Documents Can not be marked 'NA' \n";
					 }
					 //Virender End
					 else if(additionalDocStatus[i].value=='P')
					 {
					   		msg4="*Document status can not be Pending \n";
					 }
					 else if(document.getElementById("mandatoryOrNonMandatory"+index).value=='Y')
					 {
						 addMsg5="*"+docNameAdditional+" Is Mandatory Document , It can not be waived \n";
					 }
				 }
 var vAdditionalDocType = document.getElementById("vAdditionalDocType"+index).value;
				 var additionalDocStatus = document.getElementById("additionalDocStatus"+index).value;
				 var Des = '';
				 if((additionalDocStatus=='R' || additionalDocStatus=='W' || additionalDocStatus=='P') && vAdditionalDocType != 'NA' ){
					 if(additionalDocStatus=='R')
						 Des = 'Received';
					 if(additionalDocStatus=='W')
						 Des = 'Waived';
					 if(additionalDocStatus=='P')
						 Des = 'Pending';
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For "+Des+" Documents \n";
				 }
				 else if(additionalDocStatus=='D' && vAdditionalDocType == 'NA'){
					 addMsg10="* Document Type '"+vAdditionalDocType+"' Is Not Applicable For Deferred Documents \n";
				 }
			 }
			 if(count<arr.length)
			 {
				 addMsg7="Please Select All Rows or Delete extra Rows \n";
			 }
//			 if(count==0)
//			 {
//				 addMsg7="Please Select atleast one Row \n";
//			 }
		 }
		 
		 if(rowCount==1 && psize<1 && result=='N')
		 {
				addMsg8="Please Add Atleast One Document \n";
          }
		 if(msg1!='' || msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''|| msg9!='' ||
				 addMsg1!='' || addMsg2!=''||addMsg3!=''||addMsg4!=''||addMsg5!=''||addMsg6!=''||addMsg7!=''||addMsg8!=''||addMsg10!='')
	   	 {
	   		 checkStatus='1';
	   		 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+addMsg1+addMsg2+addMsg3+addMsg4+addMsg5+addMsg6+addMsg7+addMsg8+msg9+addMsg10);
	   		DisButClass.prototype.EnbButMethod();
	   	 }
		 if(checkStatus=='0')
		 {
//				 alert("document saved successfully");
//				 DisButClass.prototype.EnbButMethod();
			 document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=assetDoc";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("DocumentForm").submit();
		 }
	// Changes By Amit Ends
	 }
	 return false;
}


function refreshChildId(rowCount)
{
	document.getElementById("childId"+rowCount).value='';
}

function makeChildDocPod(rowCount,docId,pageStatus,entityId)
{
	//alert("str: "+rowCount+"docId: "+docId+"pageStatus: "+pageStatus);
	var status = 'status'+rowCount;
	var entityType=document.getElementById("entityType").value;
	var childStatus=document.getElementById(status).value;
	//document.getElementById("childId"+rowCount).value='';
	 if(docId!='')
	 {
		if(childStatus=='R')
		{
			var sourcepath=document.getElementById("contextPath").value;
//			document.getElementById("DocumentForm").action=sourcepath+"/childDocAction.do?docId="+docId+"&rowCount="+rowCount;
//			document.getElementById("DocumentForm").submit();
//			return true;
			// Changes made by Amit Starts
			var address = sourcepath+"/childDocActionPOD.do";
			//Changes made by Amit Ends
			var data = "docId="+docId+"&rowCount="+rowCount+"&entityType="+entityType+"&entityId="+entityId;
			//alert("address: "+address+"data: "+data);
			send_child(address,data,pageStatus);
			return true;
		}
		else
		{
			 alert("Please Recieved the documents");	
			 return false;
		}
	 }
	 else
	 {
		 alert("Please Select List");	
		 return false;
	 }
}
//start by sachin 
function makeChild1(rowCount,docId,pageStatus,entityId,dealId)
{
	//alert("str: "+rowCount+"docId: "+docId+"pageStatus: "+pageStatus);
	var status = 'status'+rowCount;
	var entityType=document.getElementById("entityType").value;
	var childStatus=document.getElementById(status).value;
	document.getElementById("childId"+rowCount).value='';
	 if(docId!='')
	 {
		if(childStatus=='R')
		{
			
			var sourcepath=document.getElementById("contextPath").value;
//			document.getElementById("DocumentForm").action=sourcepath+"/childDocAction.do?docId="+docId+"&rowCount="+rowCount;
//			document.getElementById("DocumentForm").submit();
//			return true;
			var address = sourcepath+"/childDocDealAction.do";
			var data = "docId="+docId+"&rowCount="+rowCount+"&entityType="+entityType+"&entityId="+entityId+"&dealId="+dealId;
			//alert("address: "+address+"data: "+data);
			send_child(address,data,pageStatus);
			return true;
		}
		else
		{
			 alert("Please Recieved the documents");	
			 return false;
		}
	 }
	 else
	 {
		 alert("Please Select List");	
		 return false;
	 }
}
function send_child1(address, data,pageStatus)
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_child1(request,pageStatus);
	};
	// alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}

function result_child1(request,pageStatus){
	   // alert(status)
	var x=0;
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			// alert(str);
			//var s1 = str.split("$:");
			//alert(trim(str));
			if(trim(str)==1)
			{//alert("in if "+str);
				x=1;
			}
		}
		if(x==1)
		{
			
			var url="JSP/CPJSP/childDealDocs.jsp?pageStatus="+pageStatus;
			window.child=window.open(url,'under_writer','height=250,width=600,top=200,left=250,scrollbars=yes' );
		}
}

function viewDocument(methodname){
	
	 var contextPath =document.getElementById('contextPath').value ;    
  	var completeURL = contextPath+"/documentProcessAction.do?method="+methodname;
	
		//		var url= contextPath+"/searchCMBehindAction.do?method=chequeStatusForLoanViewer&loanId="+loanIDMain+"&loanNumber="+loan+"&customerName="+name;
		   mywindow =window.open(completeURL,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		   mywindow.moveTo(800,300);
		   if (window.focus) 
		   {
				mywindow.focus();
				return false;
		   }
		   return true;
	}	

//end by sachin


function makeChild(rowCount,docId,pageStatus,entityId)
{
	//alert("str: "+rowCount+"docId: "+docId+"pageStatus: "+pageStatus);
	var status = 'status'+rowCount;
	var entityType=document.getElementById("entityType").value;
	var childStatus=document.getElementById(status).value;
	document.getElementById("childId"+rowCount).value='';
	 if(docId!='')
	 {
		if(childStatus=='R')
		{
			var sourcepath=document.getElementById("contextPath").value;
//			document.getElementById("DocumentForm").action=sourcepath+"/childDocAction.do?docId="+docId+"&rowCount="+rowCount;
//			document.getElementById("DocumentForm").submit();
//			return true;
			var address = sourcepath+"/childDocAction.do";
			var data = "docId="+docId+"&rowCount="+rowCount+"&entityType="+entityType+"&entityId="+entityId;
			//alert("address: "+address+"data: "+data);
			send_child(address,data,pageStatus);
			return true;
		}
		else
		{
			 alert("Please Recieved the documents");	
			 return false;
		}
	 }
	 else
	 {
		 alert("Please Select List");	
		 return false;
	 }
}	

function send_child(address, data,pageStatus)
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_child(request,pageStatus);
	};
	// alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_child(request,pageStatus){
	   // alert(status)
	var x=0;
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			// alert(str);
			//var s1 = str.split("$:");
			//alert(trim(str));
			if(trim(str)==1)
			{//alert("in if "+str);
				x=1;
			}
		}
		if(x==1)
		{
			
			var url="JSP/CPJSP/childDocs.jsp?pageStatus="+pageStatus;
			window.child=window.open(url,'under_writer','height=250,width=600,top=200,left=250,scrollbars=yes' );
		}
}


function addChild()
{
	var flag=0;
	var strId="";
	var ch=document.getElementsByName('chk');
 	//alert("length...."+ch.length);
 	 for(i=0;i<ch.length;i++)
 		{
 		 
		   if(ch[i].checked==true)
			{
 			   id=ch[i].value;
 			   strId=strId+id+"|";
			   //alert(id);
			   flag=1;
 			}
 		  
 		}
 	 
 	 if(flag==0)
 	 {
 		 alert("Please select atleast one Record");
 	 }
 	 else
 	 {
 		var status=document.getElementById("rowNo").value;
// 		var def = 'def'+status;
// 		//alert("strId is "+strId);
// 		window.opener.document.getElementById(def).innerHTML = "<input name='deferedDate' readonly='readonly' value='' type='text' class='text4'>";
// 		window.opener.document.getElementById("status"+status).value='R';
 		window.opener.document.getElementById("childId"+status).value=strId;
 		window.close();
 		
 	 }
 	
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);	
	return dtObject;
}


//Start by sanjog

//function submitFVCDoc(formName,stage)
//{
//	DisButClass.prototype.DisButMethod();
//	var sourcepath=document.getElementById("contextPath").value;
//	if(validateDocUpload())
//	{
//		//alert(formName+"---"+stage);
//		document.getElementById(formName).action=sourcepath+"/fvcDocUploadBehind.do?method=uploadUnderwritingDocData&txntype="+stage;
//		document.getElementById("processingImage").style.display = '';
//		document.getElementById(formName).submit();
//		return true;
//	}
//	else{
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
//}
function downloadFVCDoc(fileName)
{
	//alert("download File: "+removeSplChar(fileName));
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("fieldVerificationForm").action=sourcepath+"/underwritingDocUpload.do?method=downloadUnderwritingFile&fileName="+removeSplChar(fileName)+"&txntype="+'FVC';
	document.getElementById("fieldVerificationForm").submit();
	return true;
}


//function submitFVCDoc(formName,stage){
//	// alert("In getSheme");
//	DisButClass.prototype.DisButMethod();
//	var docFile = document.getElementById("docFile").value;
//	//alert(docFile);
//	document.getElementById("docFileString").value = docFile;
//	var sourcepath=document.getElementById("contextPath").value;
//	if(validateDocUpload())
//	{
//		var address = sourcepath+"/fvcDocUploadBehind.do?method=uploadUnderwritingDocData&txntype="+stage+"&doc_File"+docFile;
//		var data = "docFile=" + docFile;
//		// alert("address: "+address+"data: "+data);
//		send_FVCDoc(address, data, docFile);
//		DisButClass.prototype.EnbButMethod();
//		return true;
//	}
//	else{
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
//}uploadFVCDoc
//
//function send_FVCDoc(address, data, docFile) {
//	// alert("send_countryDetail"+address);
//	var boundary=Math.random().toString().substr(2);
//	var contentType = "multipart/form-data; boundary=" + boundary;
//	var request = getRequestObject();
//	request.onreadystatechange = function () {
//		result_FVCDoc(request,data,docFile);
//	};
//	// alert("send_countryDetail"+address);
//	request.open("POST", address, true);
//	request.setRequestHeader("Content-Type", contentType);
//	//alert(docFile);
//	request.send(docFile);
//}
//function result_FVCDoc(request,data,docFile){
//   //alert(request.responseText)
//	if ((request.readyState == 4) && (request.status == 200)) {
//		var str = request.responseText;
//			document.getElementById("UploadDoc").innerHTML = str;
//
//	}
//}

/*function submitFVCDoc(id,stage){
	otherWindows = new Array();
	curWin = 0;
	if(id!='')
	{
	   	var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/fviDocUploadBehind.do?fieldVerificationUniqueId="+id+"&stage="+stage;
		//mywindow=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' ).focus();
		otherWindows[curWin++]=window.open(url,'name1','height=500,width=900,top=200,left=250,scrollbars=yes' );
		otherWindows[curWin++].focus();
		otherWindows[curWin++].moveTo(800,300);
		if (window.focus) 
			{
				otherWindows[curWin++].focus();
				return false;
			}
	}
	else
	{
		alert("Please first Save ");
	}
	
	return true;
	
}*/
function submitFVCDoc(id,stage,DealID,bpType){
	 
	otherWindows = new Array();
	curWin = 0;
	var docId="";
	if(id!='')
	{
	   	var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/agencyCapturingDispatchAction.do?method=openDocUpload&docId="+docId+"&bpId="+DealID+"&bpType="+bpType+"&DocType="+stage+"&entityId="+id;
		
		window.child =openWindow(url);
	   	
}
	else
	{
		alert("Please first Save ");
	}
	
	return true;
	
}
function uploadFVCDoc(formName){
// alert("In getSheme");
DisButClass.prototype.DisButMethod();
//var docFile = document.getElementById("docFile").value;

var sourcepath=document.getElementById("contextPath").value;
	if(validateDocUpload())
	{
		document.getElementById(formName).action=sourcepath+"/fvcDocUploadBehind.do?method=uploadUnderwritingDocData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById(formName).submit();
		return true;
	}
	else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}


function downloadFVIFile(fileName,formName,stage)
{
	//alert("download File: "+removeSplChar(fileName));
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById(formName).action=sourcepath+"/fvcDocUploadBehind.do?method=downloadFviUploadedFile&fileName="+removeSplChar(fileName)+"&stage="+stage;
	document.getElementById(formName).submit();
	return true;
}

function deleteFVIUploadDoc(stage)
{
	//alert("Delete row");
	DisButClass.prototype.DisButMethod();
	    if(check())
	    {
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("underwritingDocUpload").action=sourcepath+"/fvcDocUploadBehind.do?method=deleteUploadDocData&stage="+stage;
		 	document.getElementById("processingImage").style.display = '';
			document.getElementById("underwritingDocUpload").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    }
}

function notAllowToForwardDocumentDetails()
{
		DisButClass.prototype.DisButMethod();
		alert("Please Save Data First ");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("button").removeAttribute("disabled","true");
		document.getElementById("forwardButton").removeAttribute("disabled","true");
		return false;
	}

function forwardDocumentDetails(formName)
{
	var contextPath=document.getElementById("contextPath").value;
	document.getElementById("DocumentForm").action = contextPath+"/documentProcessActionPOD.do?method=forwardDocForAllDocTypeAtDocumentCollection";
 	document.getElementById("processingImage").style.display = '';
	document.getElementById(formName).submit();
	return true;	
}

function enableDisableDocField()
{

		 if(document.getElementById("photoUploadType").checked==true)
			 {
				document.getElementById("dis1").style.display="none";
				document.getElementById("dis2").style.display="none";
						var contextPath =document.getElementById('contextPath').value ;
						var url=contextPath+"/JSP/CPJSP/webCamDocUpload.jsp";
						mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
						mywindow.moveTo(800,300);
						if (window.focus) {
							mywindow.focus();
							return false;
						}
						return true;
				
			 }
		 if(document.getElementById("photoUploadType1").checked==true)
		 {
				document.getElementById("dis1").style.display="";
				document.getElementById("dis2").style.display="";
		 }
			

}
function docTypeChoice(i){
	var vDocType=document.getElementById("vDocType"+i).value;
	if(vDocType=="OTC" || vDocType=="PDD" || vDocType=="AW" ){//Virender
		document.getElementById("status"+i).value="D";
		makeChoice(i);
		if(vDocType=="AW"){
			var x="deferedDate"+i;
			document.getElementById(x).value="31-12-2099";
		}
	}
}
function OTCPDDMarking(i){
	var status=document.getElementById("status"+i).value;
	if(status=="R" || status=="W" ){
		document.getElementById("vDocType"+i).value="NA";
	}
}
function docTypeChoiceAdd(i){
	var vDocType=document.getElementById("vAdditionalDocType"+i).value;
	if(vDocType=="OTC" || vDocType=="PDD" || vDocType=="AW" ){//Virender
		document.getElementById("additionalDocStatus"+i).value="D";
		makeChoiceAdditional(i+1);
		if(vDocType=="AW"){
			var x="additionalDeferredDate"+i;
			document.getElementById(x).value="31-12-2099";
		}
	}
}
function OTCPDDMarkingAdd(i){
	var status=document.getElementById("additionalDocStatus"+i).value;
	if(status=="R" || status=="W" ){
		document.getElementById("vAdditionalDocType"+i).value="NA";
	}
}
////////dms code start///////////////////////


//shyam
function uploadDocuments(rowCount,docId,entityId,childDocFlag,DocType)
{
	
//		alert("str: "+rowCount+"docId: "+docId+"pageStatus: "+pageStatus);
		var status = 'status'+rowCount;
		var entityType=document.getElementById("entityType").value;
		var childStatus=document.getElementById(status).value;
		var sourcepath=document.getElementById("contextPath").value;
		var bpId = document.getElementById("bpId").value;
		var bpType = document.getElementById("bpType").value;
		if(bpId=='' || bpType=='LIM'){
			bpId = document.getElementById("loanbpId").value;
			bpType = 'LIM';
		}
		/*document.getElementById("childId"+rowCount).value='';*/
		if(childDocFlag=='Y'){
		var addedChildIds = 'childId'+rowCount;
			var ChildIds = document.getElementById(addedChildIds).value;
			if(ChildIds==''){
				alert("Please Add Atleast One Child!!!");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
		} 
		if(docId!='')
		 {
			if(childStatus=='R')
			{
				 anotherWindows = new Array();
				    curWinAnother = 0;
				
				var url=sourcepath+"/agencyCapturingDispatchAction.do?method=openDocUpload&docId="+docId+"&bpId="+bpId+"&bpType="+bpType+"&DocType="+DocType+"&entityId="+entityId;
				
				window.child =window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
				anotherWindows[curWinAnother++]= window.open(url,'nameCp','height=400,width=1000,top=200,left=250,scrollbars=yes');

				 return true;
				}
			else
			{
				 alert("Please Recieve the documents");	
				 return false;
			}
		 }
		 else
		{
			alert('Doc Details not present');
			return false;
		}
}
function docUpload(dmsUrl,source){
	
	 
	var isError=0;
	if(source!='global'){
		if(document.getElementById("documentStatus")){
			documentStatus = document.getElementById("documentStatus").value;
			if(documentStatus==''){
				isError=1;
				alert("Please Select Atleast One Child Document");
				return false;
			}
		}
	}
	var docSetName='=';
	
	
	
	var dmacVersion = document.getElementById("dmacVersion").value;
	var dmsDocSetParentName = document.getElementById("dmsDocSetParentName").value;
	var dmsDocSetChildName = document.getElementById("dmsDocSetChildName").value;
	var n = dmsDocSetChildName.includes("/");
  if(n){

     dmsDocSetChildName= str.replace("/", " or");


    }
	docSetName=dmsDocSetParentName+"#"+dmsDocSetChildName;
	
	
	if(isError==0 && dmacVersion!='' && dmacVersion=='1'){
		var data_json = '{'
		+' "applicationFormNo": "'+document.getElementById("applicationFormNo").value+'",'
		+' "branch": "'+document.getElementById("branch").value+'",'
		+' "product": "'+document.getElementById("product").value+'",'
		+' "scheme": "'+document.getElementById("scheme").value+'",'
		+' "dmsNumber": "'+document.getElementById("dmsNumber").value+'",'
		+' "entityType": "'+document.getElementById("entityType").value+'",'
		+' "entityId": "'+document.getElementById("entityId").value+'",'
		+' "entityDesc": "'+document.getElementById("entityDesc").value+'",'
		+' "panNumber": "'+document.getElementById("panNumber").value+'",'
		+' "aadharNumber": "'+document.getElementById("aadharNumber").value+'",'
		+' "email": "'+document.getElementById("email").value+'",'
		+' "mobileNumber": "'+document.getElementById("mobileNumber").value+'",'
		+' "dob": "'+document.getElementById("dob").value+'",'
		+' "txnType": "'+document.getElementById("txnType").value+'",'
		+' "txnId": "'+document.getElementById("txnId").value+'",'
		+' "docId": "'+document.getElementById("docId").value+'",'
		+' "childDocId": "'+document.getElementById("childDocId").value+'",'
		+' "uploadedBy": "'+document.getElementById("uploadedBy").value+'",'
		+' "documentTypeName": "'+document.getElementById("documentTypeName").value+'",'
		+' "dmsSystemUserId": "'+document.getElementById("dmsSystemUserId").value+'",'
		+' "dmsSystemUserPassword": "'+document.getElementById("dmsSystemUserPassword").value+'"'
		+' }';
		var encodeBase64=btoa(data_json);
		var dmsDocUrl=dmsUrl+encodeBase64;
			var childwindow=window.open(dmsDocUrl,'DMS_UPLOAD','height=450,width=900,top=200,left=250,scrollbars=yes');
		var timer = setInterval(function() {
			if(childwindow.closed){
				clearInterval(timer);
				if(source=='global'){
					refreshDMSParentPage();
					}
					if(source=='checklist'){
						refreshCheckListDMSParentPage();
				}		
					if(source=='verification'){
						refreshVerificationDMSParentPage();
			}
				}
			}, 1000);
	}
	else if(isError==0 && dmacVersion!='' && dmacVersion=='2'){
		var typeName=document.getElementById("documentTypeName").value;
		var childDocId=document.getElementById("childDocId").value;
		var docId=document.getElementById("docId").value;
		if(parseFloat(docId)==0 && parseFloat(childDocId)==0){
			docSetName=docSetName;
		}if(parseFloat(docId)>0 && parseFloat(childDocId)==0){
			docSetName=typeName+"#";
		}
	 
		if(parseFloat(docId)>0 && parseFloat(childDocId)>0){
			
			docSetName=typeName.substring(0,typeName.indexOf("("))+"#"+typeName.substring(typeName.indexOf("(")+1,typeName.indexOf(")"));
		 
		}
		
		docSetName=docSetName.replace("/"," or");
		var data_json ='{'
			+'"userCredentials": {'
				+'"userId": "'+document.getElementById("dmsSystemUserId").value+'",'
				+'"userPassword": "'+document.getElementById("dmsSystemUserPassword").value+'"'
			+'},'
			+'"metaDeta": {'
				+'"deal_Room": "'+document.getElementById("dmsDealRoom").value+'",'
				+'"im_Name": "'+document.getElementById("dmsImName").value+'",'
				+'"category_Name": "'+document.getElementById("dmsCategoryName").value+'",'
				+'"ig_Name": "'+document.getElementById("dmsIgName").value+'",'
				+'"imf_Name": "'+document.getElementById("dmsImfName").value+'",'
				+'"dmsNumber": "'+document.getElementById("dmsNumber").value+'",'
				+'"docSetName": "'+docSetName+'",'
				+'"entityId": "'+document.getElementById("entityId").value+'",'
				+'"entityType": "'+document.getElementById("entityType").value+'",'
                                  +' "applicationFormNo": "'+document.getElementById("applicationFormNo").value+'",'
				+'"txnType": "'+document.getElementById("txnType").value+'",'
				+'"txnId": "'+document.getElementById("txnId").value+'",'
				+'"docId": "'+document.getElementById("docId").value+'",'
				+'"childDocId": "'+document.getElementById("childDocId").value+'",'
				+'"uploadedBy": "'+document.getElementById("uploadedBy").value+'"'
			+'}'
		+'}';
		var encodeBase64=btoa(data_json);
		var dmsDocUrl=dmsUrl+encodeBase64;
		var childwindow=window.open(dmsDocUrl,'DMS_UPLOAD','height=450,width=900,top=200,left=250,scrollbars=yes');
		var timer = setInterval(function() {
			if(childwindow.closed){
				clearInterval(timer);
				if(source=='global'){
					refreshDMSParentPage();
					}
					if(source=='checklist'){
						refreshCheckListDMSParentPage();
				}		
					if(source=='verification'){
						refreshVerificationDMSParentPage();
			}
				}
			}, 1000);
		}
}

function refreshDMSParentPage()
{
	var sourcepath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=documentUpload&dealId="+dealId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("underwritingDocUpload").submit();
}
function refreshCheckListDMSParentPage()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var bpId=document.getElementById("bpId").value;
	var bpType=document.getElementById("bpType").value;
	var docId=document.getElementById("documentId").value;
	document.getElementById("underwritingDocUpload").action=sourcepath+"/agencyCapturingDispatchAction.do?method=refreshCheckListDMSParentPage&docId="+docId+"&bpId="+bpId+"&bpType="+bpType;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("underwritingDocUpload").submit();
}
function refreshVerificationDMSParentPage()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var stage=document.getElementById("stage").value;
	var dealId=document.getElementById("dealId").value;
	var fieldVerificationUniqueId=document.getElementById("entityId").value;
	document.getElementById("underwritingDocUpload").action=sourcepath+"/fviDocUploadBehind.do?method=refreshVerificationDMSParentPage&fieldVerificationUniqueId="+fieldVerificationUniqueId+"&stage="+stage+"&dealId="+dealId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("underwritingDocUpload").submit();
}
function downloadFile(fileName,documentId,rowId)
{
	//alert("download File: "+removeSplChar(fileName));
	/*if(dmsId && dmsId!='' && dmsId>0){
		var url = "http://10.23.235.16:8082/omnidocs/integration/foldView/viewFoldList.jsp?Application=A3SDocumentView&sessionIndexSet=false&cabinetName=ABHFL_UAT&S=S&Docid="+dmsId;
		window.open(url,'download','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	}else{*/
		var sourcepath=document.getElementById("contextPath").value;
		var bpId=document.getElementById("bpId").value;
		var docId=document.getElementById("documentId").value;
		var bpType=document.getElementById("bpType").value;
		//var fileName=document.getElementById("fileName").value;
	var dmsDocId=document.getElementById("dmsDocId_"+rowId).value;
	var dmsDocUrl=document.getElementById("dmsDocUrl_"+rowId).value;
	if(parseFloat(dmsDocId) == 0 || dmsDocId==""){
		document.getElementById("underwritingDocUpload").action=sourcepath+"/agencyCapturingDispatchAction.do?method=downloadFiledoc&docId="+docId+"&bpId="+bpId+"&bpType="+bpType+"&fileName="+fileName+"&documentId="+documentId;
		document.getElementById("underwritingDocUpload").submit();
	}else{
		window.child =window.open(dmsDocUrl,'dms_viewffffffff','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	}
	return true;

}
function downloadFileNew(fileName,rowId,source)
{
	//alert("download File: "+removeSplChar(fileName));
	var sourcepath=document.getElementById("contextPath").value;
	var lbxDocId="";
	var dmsDocId="";
	var dmsDocUrl="";
	if(source=="CP"){
		 lbxDocId=document.getElementById("lbxDocId_CP_"+rowId).value;
		 dmsDocId=document.getElementById("dmsDocId_CP_"+rowId).value;
		 dmsDocUrl=document.getElementById("dmsDocUrl_CP_"+rowId).value;
	}
	if(source=="SL"){
		 lbxDocId=document.getElementById("lbxDocId_SL_"+rowId).value;
		 dmsDocId=document.getElementById("dmsDocId_SL_"+rowId).value;
		 dmsDocUrl=document.getElementById("dmsDocUrl_SL_"+rowId).value;
	}
	if(source=="CM"){
	 lbxDocId=document.getElementById("lbxDocId_"+rowId).value;
	 dmsDocId=document.getElementById("dmsDocId_"+rowId).value;
	 dmsDocUrl=document.getElementById("dmsDocUrl_"+rowId).value;
	}
	if(parseFloat(dmsDocId) == 0  || dmsDocId==""){
		document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=downloadUnderwritingFile&lbxDocId="+lbxDocId;
	document.getElementById("underwritingDocUpload").submit();
	}else{
		window.child =window.open(dmsDocUrl,'dms_viewASDfDFGsd\''+lbxDocId+'\'','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
	}
	return true;
}
function downloadDocuments(docId)
{
	//alert("download File: "+removeSplChar(fileName));
	var sourcepath=document.getElementById("contextPath").value;
	var docType="Master";
	var bpId=document.getElementById("bpId").value;
	document.getElementById("DocumentForm").action=sourcepath+"/agencyCapturingDispatchAction.do?method=downloadUploadedFile&docId="+
	docId+"&bpId="+bpId+"&docType="+docType;
	document.getElementById("DocumentForm").submit();
	return true;
}
function submitDocUpload1()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	//var loanId=document.getElementById("loanId").value;
	
   //alert(dealId);
	
		document.getElementById("underwritingDocUpload").action=sourcepath+"/underwritingDocUpload.do?method=documentUpload&dealId="+dealId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("underwritingDocUpload").submit();
		return true;
	
}



////////dms code end /////////////////////// 
