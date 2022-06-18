function fnSaveLegalCaseFileMaker(ufFlag){
	
	DisButClass.prototype.DisButMethod();
	
	document.getElementById("saveForwardFlag").value=ufFlag;
	
	var caseNo = document.getElementById("caseNo");
	var courtFee = document.getElementById("courtFee");
	var caseFileDate = document.getElementById("caseFileDate");
	var dateOfHearing = document.getElementById("dateOfHearing");
	var lbxStage = document.getElementById("lbxStage");
	var stageDesc = document.getElementById("stageDesc");
	var section = document.getElementById("section");
	var courtTypeDesc = document.getElementById("courtTypeDesc");
	var lbxCourtType = document.getElementById("lbxCourtType");
	var courtNameDesc = document.getElementById("courtNameDesc");
	var lbxCourtName = document.getElementById("lbxCourtName");
	var courtNameButton = document.getElementById("courtNameButton");
	var judgeName = document.getElementById("judgeName");
	var poaDesc = document.getElementById("poaDesc");
	var lbxPoa = document.getElementById("lbxPoa");
	var recoveryAmount = document.getElementById("recoveryAmount");
	var fileMakerRemarks = document.getElementById("fileMakerRemarks");
	
	//alert(document.getElementById("saveForwardFlag").value);
	
	
	 if(trim(caseNo.value) == '' || trim(courtFee.value) == '' || trim(caseFileDate.value) == '' || trim(dateOfHearing.value) == '' || trim(lbxStage.value) == '' || trim(stageDesc.value) == ''
		 || trim(section.value) == '' || trim(courtTypeDesc.value) == '' || trim(lbxCourtType.value) == '' || trim(courtNameDesc.value) == '' || trim(lbxCourtName.value) == '' || trim(judgeName.value) == '' || trim(poaDesc.value) == ''
			 || trim(lbxPoa.value) == '' || trim(recoveryAmount.value) == '' || trim(fileMakerRemarks.value) == ''
	 ){
		 var msg= '';
 		if(trim(caseNo.value) == '')
 			msg += '* Case No is required.\n';
 		if(trim(courtFee.value) == '')
 			msg += '* Court Fee is required.\n';
 		if(trim(caseFileDate.value) == '')
 			msg += '* Case File Date is required.\n';
 		if(trim(dateOfHearing.value) == '')
 			msg += '* Hearing Date is required.\n';
 		if(trim(lbxStage.value) == '' || trim(stageDesc.value) == '')
 			msg += '* Stage is required.\n';
 		if(trim(section.value) == '')
 			msg += '* Section is required.\n';
 		if(trim(courtTypeDesc.value) == '' || trim(lbxCourtType.value) == '')
 			msg += '* Court Type is required.\n';
 		if(trim(courtNameDesc.value) == '' || trim(lbxCourtName.value) == '')
 			msg += '* Court Name is required.\n';
 		if(trim(judgeName.value) == '')
 			msg += '* Judge Name is required.\n';
 		if(trim(poaDesc.value) == '' || trim(lbxPoa.value) == '')
 			msg += '* POA is required.\n';
 		if(trim(recoveryAmount.value) == '')
 			msg += '* Recovery Amount is required.\n';
 		if(trim(fileMakerRemarks.value) == '')
 			msg += '* Remarks is required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("No")){
 			caseNo.focus();
 		}else if(msg.match("Fee")){
 			courtFee.focus();
 		}else if(msg.match("File")){
 			caseFileDate.focus();
 		}else if(msg.match("Hearing")){
 			dateOfHearing.focus();
 		}else if(msg.match("Stage")){
 			stageButton.focus();
 		}else if(msg.match("Section")){
 			section.focus();
 		}else if(msg.match("Type")){
 			courtTypeButton.focus();
 		}else if(msg.match("Court Name")){
 			courtNameButton.focus();
 		}else if(msg.match("Judge")){
 			judgeName.focus();
 		}else if(msg.match("POA")){
 			poaButton.focus();
 		}else if(msg.match("Recovery")){
 			recoveryAmount.focus();
 		}else if(msg.match("Remarks")){
 			fileMakerRemarks.focus();
 		}
 		
 		
 		DisButClass.prototype.EnbButMethod();
 		//document.getElementById("save").removeAttribute("disabled","true");
 		return false;
	 
	 }
	 if(!compareTwoDates('formatD','caseFileDate','dateOfHearing','Date of Hearing should be greater than Case File Date.'))
	 {
		 DisButClass.prototype.EnbButMethod();
		 return false
	 }
		document.getElementById("legalCaseFileMakerForm").action="legalCaseFileMakerDispatch.do?method=saveLegalCaseFileMaker";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalCaseFileMakerForm").submit();
}


function saveAuthorData(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	if ((document.getElementById("decision").value!="CFA") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 //document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	 
	}else{
		document.getElementById("legalCaseFileAuthorForm").action="legalCaseFileAuthorDispatch.do?method=saveCaseFileAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("legalCaseFileAuthorForm").submit();
		return true;	
	}
}

function compareTwoDates(format,date1,date2,message)
{
	
	var formatD=document.getElementById(format).value;
	
	var firstDate =document.getElementById(date1).value;
	var secondDate=document.getElementById(date2).value;
    
    var dt1=getDateObject(firstDate,formatD.substring(2, 3));
    var dt2=getDateObject(secondDate,formatD.substring(2, 3));

    
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
	  
	  if(value!='CFA')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}