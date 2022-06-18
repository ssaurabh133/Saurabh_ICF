function fnUpdateRepoDetailByStockyard(ufFlag){
	
	DisButClass.prototype.DisButMethod();
	var stockyardManager = document.getElementById("stockyardManager");
	var sEntryDate = document.getElementById("stockyardEntryDate");
	var sEntryTime = document.getElementById("stockyardEntryTime");
	var stockyardRemarks = document.getElementById("stockyardRemarks");
	var grideSize = document.getElementById("listSize").value;
	var assetCheckList="";
	var checkListStatus="";
	var checkListRemarks="";
	//for deciding update and forward
	document.getElementById("updateForwardFlag").value=ufFlag;
	
	 if( trim(stockyardManager.value)=='' || trim(sEntryDate.value)=='' || trim(sEntryTime.value)=='' || trim(stockyardRemarks.value)==''){
		 var msg= '';
 		
		 
		 if(trim(stockyardManager.value) == '' )
	 			msg += '* Stockyard Manager is required.\n';
		 
 		if(trim(sEntryDate.value) == '' )
 			msg += '* Stockyard Entry Date is required.\n';
 		
 		if(trim(sEntryTime.value) == '' )
 			msg += '* Stockyard Entry Time is required.\n';
 		
 		if(trim(stockyardRemarks.value) == '' )
 			msg += '* Stockyard Remarks is required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("Manager")){
 			stockyardManager.focus();
 		}else if(msg.match("Date")){
 			sEntryDate.focus();
 		}else if(msg.match("Time")){
 			sEntryTime.focus();
 		}else if(msg.match("Remarks")){
 			stockyardRemarks.focus();
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }
	 
	 for(var i=0;i<grideSize;i++)
	 	{
	 		if(document.getElementById("checkListStatus"+i).value!="" && document.getElementById("checkListRemarks"+i).value!="")
	 		{
	 			assetCheckList=assetCheckList+document.getElementById("assetCheckList"+i).value+"|";
	 			
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

		 
		 		
		document.getElementById("repoDetailStockyardAddForm").action="repoDetailByStockyardDispatch.do?method=updateRepoDetailByStockyard&assetCheckList="+assetCheckList+"&checkListStatus="+checkListStatus+"&checkListRemarks="+checkListRemarks;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("repoDetailStockyardAddForm").submit();
		return true;
		 
}

	 
	 
function fnSearchRepoDetailByStockyard(val){
	
	DisButClass.prototype.DisButMethod();
	var repoId = document.getElementById("searchRepoId");
	var loanId = document.getElementById("lbxLoanIdSearch");
	var loanNoButton = document.getElementById("loanNoButton");
	
	document.getElementById("repoDetailByStockyardForm").action="repoDetailByStockyardDispatch.do?method=searchRepoDetailByStockyard";
	
	if(repoId.value=='' && loanId.value=='')
	{
		alert(val);
		repoId.focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("repoDetailByStockyardForm").submit();
	return true;
	}
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

