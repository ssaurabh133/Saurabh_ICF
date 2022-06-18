
function show_customer_detail()
{	
	DisButClass.prototype.DisButMethod();
	var codeId =document.getElementById('codeId').value ;
	var customerName =document.getElementById('customerName').value ;
	var recStatus = document.getElementById('recStatus').value ;
	var statusCase="";
	if(recStatus=="A")
		statusCase = document.getElementById("statusCase").value;
	if(recStatus=="PC" ||recStatus=="PI" || statusCase=="")
	{
		if(codeId== ''&& customerName=='')
		{
   	 		alert("Please select at least one field");	
//   	 		document.getElementById("search").removeAttribute("disabled","true");
   	 		DisButClass.prototype.EnbButMethod();
   	 		return false;
		}
		else
		{
			if(recStatus=="A"){

				document.getElementById("processingImage").style.display = '';
				document.location.href="showCustomerDetailAction.do?method=getCustomerDetail&recStatus=A&statusCase="+statusCase+"&codeId="+codeId+"&customerName="+escape(customerName);
			}else{

				document.getElementById("processingImage").style.display = '';
				document.location.href="showCustomerDetailAction.do?method=getCustomerDetail&recStatus="+recStatus+"&codeId="+codeId+"&customerName="+escape(customerName);
	 		 return true;
	 		 }
		}
	}
	if(recStatus=="A" && statusCase !="")
	{

		document.getElementById("processingImage").style.display = '';
		document.location.href="showCustomerDetailAction.do?method=getCustomerDetail&recStatus=A&statusCase="+statusCase+"&codeId="+codeId+"&customerName="+escape(customerName);
		return true;
	}		
}

function send_show_customer_detail(address, data) {
	//alert("send_show_customer_detail"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_show_customer_detail(request);
	};
	//alert("send_show_customer_detail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_show_customer_detail(request){

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert(str);
		//var s1 = str.split("$:");
		//alert(s1);
	    if(str.length>0)
	    {
	    	//alert(trim(str));
		    document.getElementById("showlist").style.display='';
	    	document.getElementById('custID').innerHTML  = str;
	    	
		}
	}
}


function openUpdate(hideId)
{
	var ctype=document.getElementById("ctype").value;
	var contextPath = document.getElementById("contextPath").value;
	if(ctype=='I')
	{
		document.getElementById("maker").action=contextPath+"/corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+hideId+"&cType=I";
 		document.getElementById("maker").submit();
 		return true;
	}
	else if(ctype=='C')
	{
		document.getElementById("maker").action=contextPath+"/corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+hideId+"&cType=C";
 		document.getElementById("maker").submit();
		return true;
    }
	
}
function getDateObject(dateString,dateSeperator)
{
	//alert("dateString  : "+dateString);
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
function calculateAgeOnLoad()
{
	var dobDate="";
	var approve=document.getElementById("approve").value;
	if(approve=="N")
	dobDate=document.getElementById("incorporationDate").value;
	if(approve=="Y")
		dobDate=document.getElementById("incorporation_Date").value;
	if(dobDate != "")
	{
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;		
		var dt1=getDateObject(curDate,formatD.substring(2, 3));
		var dt3=getDateObject(dobDate,formatD.substring(2, 3));
		if(dt1<=dt3)
		{
				alert("Date of Birth should be less than Business date.");
				document.getElementById("incorporationDate").value='';
				document.getElementById("currentAge").value='';
				return false;
	    }
		var curDay=parseFloat(curDate.substring(0,2));
		var dobDay=parseFloat(dobDate.substring(0,2));
		var curMonth=parseFloat(curDate.substring(3,5));
		var dobMonth=parseFloat(dobDate.substring(3,5));
		var curYear=parseFloat(curDate.substring(6));
		var dobYear=parseFloat(dobDate.substring(6));
		var resDay="";
		var resMonth="";
		var resYear="";
		if(curDay<dobDay)
		{
			curMonth=curMonth-1;
			curDay=curDay+30;			
		}
		resDay = curDay-dobDay;
		if(curMonth<dobMonth)
		{
			curYear=curYear-1;
			curMonth=curMonth+12;			
		}
		resMonth=curMonth-dobMonth;
		resYear=curYear-dobYear;
		var result="";
		if(resYear != 0)
			result=resYear+" Years ";
		if(resMonth  != 0)
			result=result+""+resMonth+" Months ";
		if(resDay != 0)
			result=result+""+resDay+" Days ";
		if(result=="")
			result="0 Days";
		document.getElementById("currentAge").value=result;
	}
}
function calculateAge()
{
	var dobDate=document.getElementById("incorporationDate").value;
	var flag=checkDate('incorporationDate');
	if(flag)
	{
		var curDate=document.getElementById("businessdate").value;
		var formatD=document.getElementById("formatD").value;
		var dt1=getDateObject(curDate,formatD.substring(2,3));
		var dt3=getDateObject(dobDate,formatD.substring(2,3));
		if(dt1<=dt3)
		{
			alert("Date of Birth should be less than Business date.");
			document.getElementById("incorporationDate").value='';
			document.getElementById("currentAge").value='';
			return false;
		}
		var curDay=parseFloat(curDate.substring(0,2));
		var dobDay=parseFloat(dobDate.substring(0,2));
		var curMonth=parseFloat(curDate.substring(3,5));
		var dobMonth=parseFloat(dobDate.substring(3,5));
		var curYear=parseFloat(curDate.substring(6));
		var dobYear=parseFloat(dobDate.substring(6));
		var resDay="";
		var resMonth="";
		var resYear="";
		if(curDay<dobDay)
		{
			curMonth=curMonth-1;
			curDay=curDay+30;			
		}
		resDay = curDay-dobDay;
		if(curMonth<dobMonth)
		{
			curYear=curYear-1;
			curMonth=curMonth+12;			
		}
		resMonth=curMonth-dobMonth;
		resYear=curYear-dobYear;
		
		var result="";
		if(resYear != 0)
			result=resYear+" Years ";
		if(resMonth  != 0)
			result=result+""+resMonth+" Months ";
		if(resDay != 0)
			result=result+""+resDay+" Days ";
		if(result=="")
			result="0 Days";
		//alert("result   : "+result);
		document.getElementById("currentAge").value=result;
		
	}
}
function approveCustomer()
{	DisButClass.prototype.DisButMethod();
	var code=document.getElementById("corporateCode").value;
	if(document.getElementById("corporateDetailForm") != null){
		var approve = document.getElementById("corporateDetailForm");
	}else{
		approve = document.getElementById("individualDetailForm");	
	}
   if(code!='')
   {	
	   	approve.action="approveCustomerAction.do?method=getApproval&codeId="+code;
	 	document.getElementById("processingImage").style.display = '';
	 	approve.submit();
   }
   else
   {
	   alert("You have lost Code Id!!!");
//	   document.getElementById("approve").removeAttribute("disabled","true");
	   DisButClass.prototype.EnbButMethod();
	   return false;
   }
   	
}

function send_approveCustomer(address, data) {
	alert("send_approveCustomer"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_approveCustomer(request);
	};
	//alert("send_show_customer_detail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_approveCustomer(request){

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		alert(str);
		//var s1 = str.split("$:");
		//alert(s1);
	  //  if(str.length>0)
	  //  {
	    	//alert(trim(str[0]));
		 
	    	document.getElementById('appr').innerHTML = str;
	//	}
	}
}

function newCust()
{
	
	document.location.href="corporateEntryForm.do?method=displayCorporateForms&new=new";
}
