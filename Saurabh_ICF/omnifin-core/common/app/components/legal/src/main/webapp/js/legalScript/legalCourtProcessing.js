function saveLegalCourtProcessing(sfFlag,makerAuthorFlag){
	
	DisButClass.prototype.DisButMethod();
	
	document.getElementById("saveForwardFlag").value=sfFlag;
	var sourcepath=document.getElementById("contextPath").value;
	var grideSize = document.getElementById("listSize").value;
	var stages = "";
	var caseNo = "";
	var remarks = "";
	var hearingDate = "";
	var poa = "";
	var approvalFlag = "";
	var makerDate ="";
	var saveDeleteFlag = "S";

		 
		 	for(var i=0;i<grideSize;i++)
		 	{
		 		if(document.getElementById("caseNO"+i).value!="" && document.getElementById("remarks"+i).value!="" && document.getElementById("hearingDate"+i).value!="" && document.getElementById("lbxPoa"+i).value!="")
		 		{
		 			stages=stages+document.getElementById("stageCode"+i).value+"|";
		 			
		 			caseNo=caseNo+document.getElementById("caseNO"+i).value+"|";
		 			
		 			remarks=remarks+document.getElementById("remarks"+i).value+"|";
		 			
		 			hearingDate=hearingDate+document.getElementById("hearingDate"+i).value+"|";
		 			
		 			poa=poa+document.getElementById("lbxPoa"+i).value+"|";
		 			
		 			approvalFlag=approvalFlag+document.getElementById("approvalFlag"+i).value+"|";
		 			
		 			if(document.getElementById("makerDate"+i).value!='')
		 			{
		 				makerDate=makerDate+document.getElementById("makerDate"+i).value+"|";
		 			}
		 			else
		 			{
		 				makerDate=makerDate+"B"+"|";//blank date
		 			}
		 		}
		 		else
		 		{
		 			if(document.getElementById("caseNO"+i).value!="" || document.getElementById("remarks"+i).value!="" || document.getElementById("hearingDate"+i).value!="" || document.getElementById("lbxPoa"+i).value!="")
		 			{
		 		
			 			if(document.getElementById("caseNO"+i).value=="")
			 			{
			 					alert("* Case No is required.");
			 					document.getElementById("caseNO"+i).focus();
			 					DisButClass.prototype.EnbButMethod();
			 					return false;
			 			}
			 			if(document.getElementById("remarks"+i).value=="")
			 			{
			 					alert("* Remarks is required.");
			 					document.getElementById("remarks"+i).focus();
			 					DisButClass.prototype.EnbButMethod();
			 					return false;
			 			}	
			 			if(document.getElementById("hearingDate"+i).value=="")
			 			{
			 					alert("* Hearing Date is required.");
			 					document.getElementById("hearingDate"+i).focus();
			 					DisButClass.prototype.EnbButMethod();
			 					return false;
			 			}	
			 			if(document.getElementById("lbxPoa"+i).value=="")
			 			{
			 					alert("* POA is required.");
			 					document.getElementById("poaButton"+i).focus();
			 					DisButClass.prototype.EnbButMethod();
			 					return false;
			 			}	
		 			}
		 		}
		 	}	
		
			document.getElementById("legalCourtProcessingMakerForm").action=sourcepath+"/legalCourtProcessingMakerDispatch.do?method=saveCourtProcessingMakerDetails&stages="+stages+"&caseNo="+caseNo+"&remarks="+remarks+"&hearingDate="+hearingDate+"&poa="+poa+"&approvalFlag="+approvalFlag+"&makerDate="+makerDate+"&makerAuthorFlag="+makerAuthorFlag+"&saveDeleteFlag="+saveDeleteFlag;
			//document.getElementById("caseTypeMasterForm").action=sourcepath+"/caseTypeMaster.do?method=openAddCaseType";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalCourtProcessingMakerForm").submit();
			return true;
}

function deleteRow(rowId,makerAuthorFlag)
{
DisButClass.prototype.DisButMethod();
var sourcepath=document.getElementById("contextPath").value;
	if(!confirm("Please verify all the details before submitting request for deletion."))
	{
	   //document.getElementById('deleteButton'+id2).removeAttribute("disabled");
	   
	   return false;
	}
	if(makerAuthorFlag == 'A')
	{
		document.getElementById("saveForwardFlag").value='F';
	}
	else
	{
		document.getElementById("saveForwardFlag").value='S';
	}
	var grideSize = document.getElementById("listSize").value;
	var stages = "";
	var caseNo = "";
	var remarks = "";
	var hearingDate = "";
	var poa = "";
	var approvalFlag = "";
	var makerDate ="";
	var saveDeleteFlag = "D";

		 	for(var i=0;i<grideSize;i++)
		 	{
		 		if(i!=rowId)
		 		{
			 		if(document.getElementById("caseNO"+i).value!="")
			 		{
			 			stages=stages+document.getElementById("stageCode"+i).value+"|";
			 			
			 			caseNo=caseNo+document.getElementById("caseNO"+i).value+"|";
			 			
			 			remarks=remarks+document.getElementById("remarks"+i).value+"|";
			 			
			 			hearingDate=hearingDate+document.getElementById("hearingDate"+i).value+"|";
			 			
			 			poa=poa+document.getElementById("lbxPoa"+i).value+"|";
			 			
			 			approvalFlag=approvalFlag+document.getElementById("approvalFlag"+i).value+"|";
			 			
			 			makerDate=makerDate+document.getElementById("makerDate"+i).value+"|";
			 			
			 		}
		 		}
		 	}
		 	
		 	document.getElementById("listSize").value = grideSize-1;
		
			document.getElementById("legalCourtProcessingMakerForm").action=sourcepath+"/legalCourtProcessingMakerDispatch.do?method=saveCourtProcessingMakerDetails&stages="+stages+"&caseNo="+caseNo+"&remarks="+remarks+"&hearingDate="+hearingDate+"&poa="+poa+"&approvalFlag="+approvalFlag+"&makerDate="+makerDate+"&makerAuthorFlag="+makerAuthorFlag+"&saveDeleteFlag="+saveDeleteFlag;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("legalCourtProcessingMakerForm").submit();
			return true;
	
}

function addRow(rowId,makerAuthorFlag)
{
	var basePath=document.getElementById("contextPath").value;
	
	var legalId = document.getElementById("legalId").value;
	var loanId = document.getElementById("loanId").value;
	var stageDesc = document.getElementById("stageDesc"+rowId).value;
	var stageCode = document.getElementById("stageCode"+rowId).value;
	var caseNO = document.getElementById("caseNO"+rowId).value;
	var advocateDesc = document.getElementById("advocateDesc").value;
	var advocate = document.getElementById("advocate").value;
	var preHearingDate = document.getElementById("dateOfHearing").value;
	var lbxCaseTypeCode = document.getElementById("lbxCaseTypeCode").value;
	
	//alert(legalId+" "+loanId+" "+stageDesc+" "+stageCode+" "+stageCode+" "+" "+caseNO+" "+advocateDesc+" "+advocateDesc+" "+advocate);
	//alert("preHearingDate==> "+preHearingDate);
	
	//alert("lbxCaseTypeCode==> "+lbxCaseTypeCode);
	
	var url=basePath+"/legalCourtProcessingMakerDispatch.do?method=openAddStageDetail&legalId="+legalId+"&loanId="+loanId+"&stageDesc="+stageDesc+"&stageCode="+stageCode+"&caseNO="+caseNO+"&advocateDesc="+advocateDesc+"&advocate="+advocate+"&preHearingDate="+preHearingDate+"&lbxCaseTypeCode="+lbxCaseTypeCode+"&makerAuthorFlag="+makerAuthorFlag;
	
	newWindow = window.open(url,'create_customer','height=400,width=1000,top=200,left=250');
	
	if(window.focus)
        newWindow.focus();
    
}

function saveAddStageDetail()
{
	DisButClass.prototype.DisButMethod();
	var msg = "";
	var courtProcessingMakerRemarks = document.getElementById("courtProcessingMakerRemarks");
	var dateOfHearing = document.getElementById("dateOfHearing");
	var lbxPoa = document.getElementById("lbxPoa");
	//poaButton
	var poaDesc = document.getElementById("poaDesc");
	
	if(trim(courtProcessingMakerRemarks.value) == '' || trim(dateOfHearing.value) == '' || trim(lbxPoa.value) == '' || trim(poaDesc.value) == '')
	{
		if(trim(courtProcessingMakerRemarks.value) == '')
			msg += '* Remarks is required.\n';
		if(trim(dateOfHearing.value) == '')
			msg += '* Hearing Date is required.\n';
		if(trim(lbxPoa.value) == '' || trim(poaDesc.value) == '')
			msg += '* POA is required.\n';
		
		alert(msg);
 		
 		if(msg.match("Remarks")){
 			courtProcessingMakerRemarks.focus();
 		}else if(msg.match("Hearing")){
 			dateOfHearing.focus();
 		}else if(msg.match("POA")){
 			poaButton.focus();
 		}
 		
 		DisButClass.prototype.EnbButMethod();
 		return false
	}
	
	document.getElementById("legalCourtProcessingMakerForm").action="legalCourtProcessingMakerDispatch.do?method=addRowofCourtProcessingMakerDetail";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("legalCourtProcessingMakerForm").submit();
	return true;
}

function saveAuthorData(){
	
	   DisButClass.prototype.DisButMethod();
		if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="CPA" ))
		   {
			 alert("* comments is required");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("comments").focus();
			return false;
		   }
		else
		{
		var basePath=document.getElementById("contextPath").value;
		 //Ravi start
	    
	   
	    
	    //Ravi End
		document.getElementById("legalCourtProcessingAuthorForm").action = basePath+"/legalCourtProcessingAuthorDispatch.do?method=saveCourtProcessingAuthorData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalCourtProcessingAuthorForm").submit();
		return true;
		 
		}
}

function compareTwoDates(format,date1,date2,message)
{
	
	 // alert("date1 : "+date1);
	 // alert("date2 : "+date2);
	
	var formatD=document.getElementById(format).value;
	
	var firstDate =document.getElementById(date1).value;
	var secondDate=document.getElementById(date2).value;
    
    var dt1=getDateObject(firstDate,formatD.substring(2, 3));
    var dt2=getDateObject(secondDate,formatD.substring(2, 3));
    
    //alert("dt1 : "+dt1);
    //alert("dt2 : "+dt2);

    
    if(dt1>dt2)
	{
		alert(message);
		document.getElementById(date2).value='';
		return false;
	}
    else
	{
		return true;
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

function hideAsterik(value){
	  
	  if(value!='CPA')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}

function saveReopenClosedCaseData(){
	
	   DisButClass.prototype.DisButMethod();
		if(document.getElementById("comments").value=="")
		   {
			alert("* comments is required");
			document.getElementById("comments").focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		   }
		else
		{
		var basePath=document.getElementById("contextPath").value;
		document.getElementById("legalReopenClosedCaseForm").action = basePath+"/legalReopenClosedCaseDispatchAction.do?method=saveReopenClosedCaseData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalReopenClosedCaseForm").submit();
		return true;
		 
		}
}

	
