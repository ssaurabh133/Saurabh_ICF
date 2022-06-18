function fnSearchRepoDetilByAgency(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	if(document.getElementById("searchRepoId").value == ""  && document.getElementById("lbxLoanId").value=="" )
	{
     alert(val);
     document.getElementById("searchRepoId").focus();
     DisButClass.prototype.EnbButMethod();

	return false; 
	}
	else{
	document.getElementById("repoDetailByAgencyForm").action=sourcepath+"/repoDetailbyAgencyDispatchAction.do?method=searchRepoDetailbyAgency";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoDetailByAgencyForm").submit();
	return true;
	}
}

function fnSaveRepoDetailByAgency(ufFlag){
	
	DisButClass.prototype.DisButMethod();
	
	document.getElementById("saveForwardFlag").value=ufFlag;
	
	var sourcepath=document.getElementById("contextPath").value;
	var grideSize = document.getElementById("listSize").value;
	
	var repossessedBy = document.getElementById("repossessedBy");
	var repoAdd1 = document.getElementById("repoAdd1");
	var repoAdd2 = document.getElementById("repoAdd2");
	var stateDesc = document.getElementById("stateDesc");
	var lbxState = document.getElementById("lbxState");
	var stateButton = document.getElementById("stateButton");
	var districtDesc = document.getElementById("districtDesc");
	var lbxDistrict = document.getElementById("lbxDistrict");
	var districtButton = document.getElementById("districtButton");
	var repoFrom = document.getElementById("repoFrom");
	var relationToCustomer = document.getElementById("relationToCustomer");
	var repoDate = document.getElementById("repoDate");
	var repoTime = document.getElementById("repoTime");
	var stockYardDesc = document.getElementById("stockYardDesc");
	var lbxStockYard = document.getElementById("lbxStockYard");
	var stockyardButton = document.getElementById("stockyardButton");
	var agencyRemarks = document.getElementById("agencyRemarks");
	
	var assetCheckLists = "";
	var checkListStatus = "";
	var checkListRemarks = "";
	
	//alert(document.getElementById("saveForwardFlag").value);
	
	
	 if(trim(repossessedBy.value) == '' || trim(repoAdd1.value) == '' || trim(repoAdd2.value) == '' || trim(stateDesc.value) == '' || trim(lbxState.value) == '' || trim(districtDesc.value) == ''
		 || trim(lbxDistrict.value) == '' || trim(repoFrom.value) == '' || trim(relationToCustomer.value) == '' || trim(repoDate.value) == '' || trim(repoTime.value) == '' || trim(stockYardDesc.value) == '' || trim(lbxStockYard.value) == ''
			 || trim(agencyRemarks.value) == ''
	 ){
		 
		 var msg= '';
		 
 		if(trim(repossessedBy.value) == '')
 			msg += '* Repossessed By is required.\n';
 		if(trim(repoAdd1.value) == '')
 			msg += '* Repo Address1 is required.\n';
 		if(trim(repoAdd2.value) == '')
 			msg += '* Repo Address2 is required.\n';
 		if(trim(stateDesc.value) == '' || trim(lbxState.value) == '')
 			msg += '* State is required.\n';
 		if(trim(districtDesc.value) == '' || trim(lbxDistrict.value) == '')
 			msg += '* District is required.\n';
 		if(trim(repoFrom.value) == '')
 			msg += '* Repo From is required.\n';
 		if(trim(relationToCustomer.value) == '')
 			msg += '* Relation to Customer is required.\n';
 		if(trim(repoDate.value) == '')
 			msg += '* Repo Date is required.\n';
 		if(trim(repoTime.value) == '')
 			msg += '* Repo Time is required.\n';
 		if(trim(stockYardDesc.value) == '' || trim(lbxStockYard.value) == '')
 			msg += '* Stockyard is required.\n';
 		if(trim(agencyRemarks.value) == '')
 			msg += '* Agency Remarks is required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("Repossessed")){
 			repossessedBy.focus();
 		}else if(msg.match("Address1")){
 			repoAdd1.focus();
 		}else if(msg.match("Address2")){
 			repoAdd2.focus();
 		}else if(msg.match("State")){
 			stateButton.focus();
 		}else if(msg.match("District")){
 			districtButton.focus();
 		}else if(msg.match("From")){
 			repoFrom.focus();
 		}else if(msg.match("Relation")){
 			relationToCustomer.focus();
 		}else if(msg.match("Date")){
 			repoDate.focus();
 		}else if(msg.match("Time")){
 			repoTime.focus();
 		}else if(msg.match("Stockyard")){
 			stockyardButton.focus();
 		}else if(msg.match("Remarks")){
 			agencyRemarks.focus();
 		}
 		
 		
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }
	 
	 for(var i=0;i<grideSize;i++)
 	{
 		if(document.getElementById("checkListStatus"+i).value!="" && document.getElementById("checkListRemarks"+i).value!="")
 		{
 			assetCheckLists=assetCheckLists+document.getElementById("checkList"+i).value+"|";
 			
 			checkListStatus=checkListStatus+document.getElementById("checkListStatus"+i).value+"|";
 			
 			checkListRemarks=checkListRemarks+document.getElementById("checkListRemarks"+i).value+"|";
 			
 		}
 		else
 		{
			alert("* Please enter all Check List Details.");	
			
			if(document.getElementById("checkListStatus"+i).value=="")
			{
					//alert("* Status is required.");
					document.getElementById("checkListStatus"+i).focus();
					DisButClass.prototype.EnbButMethod();
					return false;
			}
			if(document.getElementById("checkListRemarks"+i).value=="")
			{
					//alert("* Remarks is required.");
					document.getElementById("checkListRemarks"+i).focus();
					DisButClass.prototype.EnbButMethod();
					return false;
			}	
 		}
 	}	
	
		document.getElementById("repoDetailByAgencyForm").action=sourcepath+"/repoDetailbyAgencyDispatchAction.do?method=saveRepoDetailsByAgency&assetCheckLists="+assetCheckLists+"&checkListStatus="+checkListStatus+"&checkListRemarks="+checkListRemarks;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("repoDetailByAgencyForm").submit();
		return true;
	 
	
}

function validateTime(a)
{
//	 alert("ok");
	var timeStr=document.getElementById(a).value;
	
	var timePat = /^(\d{1,2}):(\d{2})?$/;
	if(timeStr.length<5)
	{
		document.getElementById(a).value='0'+timeStr;
	}
	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById(a).value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById(a).value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById(a).value='';
	return false;
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
		document.getElementById(date1).value='';
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