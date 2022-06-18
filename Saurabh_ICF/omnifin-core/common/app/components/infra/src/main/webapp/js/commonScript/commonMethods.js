function removeComma(id)
{
    var str = id;
    //alert(id);
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    if(stri=='')
    {
    	stri='0';
    }
    var amount = parseFloat(stri);
    //alert(amount);
	return amount;
}

//method added by neeraj
function isNumberKeyComma(evt) 
{
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var phNo=document.getElementById("phNo").value;
	var length=phNo.length;
	if(length==0 && charCode==44)
	{
		alert("First Character can't be comma(,).");
		return false;
	}	
	if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode !=44)
	{
		alert("Only Numeric Value Allowed Here");
		return false;
	}
	return true;
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate=0,cMonth=0,cYear=0;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	//alert(cDate);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);
	//alert(cMonth);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	//alert(cYear);
	//cmonth1 = parseInt(cMonth)-1;
	//alert(cMonth-1);
	//Create Date Object
	dtObject=new Date(cYear,cMonth-1,cDate);
	
	//alert(dtObject);
	
	return dtObject;
}

var windowObjectReference = null; 


function parent_disable() {
if( window.child && ! window.child.closed)
	 window.child.focus();
}
function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
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

 function checkBoxes()
	{
	    //alert("ok");
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

 function openLOV(id,textId)
 {
 	alert("ok");
 	var contextPath =document.getElementById('contextPath').value ;
 	alert("contextPath"+contextPath);
 			var url=contextPath+"/popup.do?method=CodeDetails&decision="+id+"&textId="+textId;
 		    window.open(url,'name','height=300,width=500,top=100,left=400,location=no,directories=no,menubar=no,toolbar=no,status=no,scrollbars=no,resizable=no,dependent=no,fullscreen=no');
         return false;
 }
 
 function saveRecord(val)
 {
	 
 	 /*alert("ok"+val);*/
 	 var targetFieldNameLOV = document.getElementById("targetFieldNameLOV").value;
 	 var targetHiddenLovId = document.getElementById("targetHiddenLovId").value;
 	 var secondTargetField = document.getElementById("secondTargetField").value;
 	 var thirdTargetField = document.getElementById("thirdTargetField").value;
 	 var fourthTargetField = document.getElementById("fourthTargetField").value;
 	 var recordId=document.getElementById("recordId").value;
 	 var noOfColumn=0;
 	 noOfColumn=document.getElementById("noOfColumn").value;
 	 //alert("noOfColumn..."+noOfColumn);
 	 var flag=0;
 	
 	 var ch=document.getElementsByName('selectRadioBtn');
 	// alert("length...."+ch.length);
 	 for(i=0;i<ch.length;i++)
 		{
 		   if(ch[i].checked==true)
 			{
 			
 			   id=ch[i].value;
 			  getId=document.getElementById(id).value;
 			 // alert(document.getElementById(id));
 			   if(noOfColumn==3)
 			 	{
 				   //alert("1111");
 				   var  otherComponent1Arr =document.getElementsByName('otherComponent1');
 				 // alert("2222");
 				   otherComponent1 =otherComponent1Arr[i].value;
 				  //alert("3333");
 			 	}
 			   	if(noOfColumn==4)
 			 	{
 			   	 var  otherComponent1Arr =document.getElementsByName('otherComponent1');
 			   		otherComponent1 =otherComponent1Arr[i].value;
 			   	var otherComponent2Arr =document.getElementsByName('otherComponent2');
 			   	otherComponent2 = otherComponent2Arr[i].value;
 			 	}
 			   	if(noOfColumn==5)
 			 	{
 			   		   		
 			   	var	otherComponent1Arr =document.getElementsByName('otherComponent1');
 					otherComponent1 = otherComponent1Arr[i].value;
 					
 			   	var	otherComponent2Arr =document.getElementsByName('otherComponent2');
 			   		otherComponent2 = otherComponent2Arr[i].value;
 			   	var	otherComponent3Arr =document.getElementsByName('otherComponent3');
 			   		otherComponent3 = otherComponent3Arr[i].value;
 			 	}
 			  
 			  flag=1;
 			  break;	
 			}
 		  
 		}
 	 
 	 if(flag==0)
 	 {
 		// alert("Please Select one Record!!!");
 		 window.opener.document.getElementById(targetFieldNameLOV).value=""; 
 		 window.opener.document.getElementById(targetHiddenLovId).value="";
 		 	if(noOfColumn==3)
 		 	{
 			 window.opener.document.getElementById(secondTargetField).value="";
 		 	}
 		 	else if(noOfColumn==4)
 		 	{
 		 		window.opener.document.getElementById(secondTargetField).value="";
 		 		window.opener.document.getElementById(thirdTargetField).value="";
 		 	}
 		 	else if(noOfColumn==5)
 		 	{
 		 		window.opener.document.getElementById(secondTargetField).value="";
 		 		window.opener.document.getElementById(thirdTargetField).value="";
 				window.opener.document.getElementById(fourthTargetField).value="";
 				
 		 	}
 		 	//alert("val : "+val);
 		 	 if(val!='' && val!=undefined)
 	 		 {
 	 			 window[val]();
 	 			 //window.close();
 	 		 }
 	 		 else{
 	 			 window.close();
 	 		 }
 		 return false;
 	 }
 	 else
 	 {
 		// alert(secondTargetField);
 		 window.opener.document.getElementById(targetFieldNameLOV).value=document.getElementById(id).value; 
 		 window.opener.document.getElementById(targetHiddenLovId).value=id;
 		 	if(noOfColumn==3)
 		 	{
 		 	 //alert(secondTargetField);
 			 window.opener.document.getElementById(secondTargetField).value=otherComponent1;
 		 	}
 		 	else if(noOfColumn==4)
 		 	{
 		 		window.opener.document.getElementById(secondTargetField).value=otherComponent1;
 		 		window.opener.document.getElementById(thirdTargetField).value=otherComponent2;
 		 	}
 		 	else if(noOfColumn==5)
 		 	{
 		 		window.opener.document.getElementById(secondTargetField).value=otherComponent1;
 		 		window.opener.document.getElementById(thirdTargetField).value=otherComponent2;
 				window.opener.document.getElementById(fourthTargetField).value=otherComponent3;
 		 	}
 			// window.close();
 		 
 		 	//alert("val : "+val);
 		 	//alert("val length: "+val.length);
		 	 if(val!='' && val!=undefined)
 	 		 {
 		 		// alert("ok"+val);
 	 			 window[val]();
 	 			// window.close();
 	 		 }
 	 		 else{
 	 			 window.close();
 	 		 }

 		 
 		// window[val]();
 		 //window.close();
 	 }
 	
 }
 
 function sendCustomerType(address) {
	  	var request = getRequestObject();
	  	request.onreadystatechange = function () {
	  		resultCustomerType(request);	
	  	};
	  	request.open("POST", address, true);
	  	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	  	request.send();	
	  }
	  function resultCustomerType(request){
	  	
	  	if ((request.readyState == 4) && (request.status == 200)) 
	  	{
	  		var str = request.responseText; 
	  		alert(trim(str));
	  		window.close();
	  	}
	  }
	  
	  
 /*function send_id(address, data) {
		//alert("send_countryDetail"+address);
		var request = getRequestObject();
		//alert("send_countryDetail"+request);
		request.onreadystatechange = function () {
			result_id(request);
		};
		//alert("send_countryDetail"+address);
		request.open("POST", address, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	function result_id(request){
	   //alert(status)
		if ((request.readyState == 4) && (request.status == 200)) {
			var str = request.responseText;
			//alert(str);
			var s1 = str.split("$:");
			//alert(s1);
		  //  if(str.length>0)
		  //  {
		    	//alert(trim(str[0]));
			//if(status=='p1')
			//{
			//	alert(s1);
			window.opener.document.getElementById('leadNo').value=trim(s1[0]);
			// window.opener.setText(trim(s1[0]),trim(s1[1]));
	         window.close();
				
			//}
			
		    	
		    	
		//	}
		}
	}*/
	function setText(text1,text2) {
		
       // alert("ok"+text1);
     
        document.getElementById('leadNo').value = text1;
       //document.getElementById('customerName').value = text2;
       
 }
 
	//function openLOVCommon(lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages,strMethod,strsecondField,strThirdField,strFourthField,childForEmptytxt,childForEmptyHtxt)
	function openLOVCommon(lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages,strMethod,strsecondField,strThirdField,strFourthField)
	{
		//closeLovCallonLov1();
//	   var pnanme=trim(strParentOption);
//	   if(strParentOption!=''){
//		  var parentLovId = document.getElementById(pnanme).value;
//	   }
		if(!strMessages){
			test();
		}
		
		if(windowObjectReference != null)
		{
			windowObjectReference = null;
			closeAllLovCallUnloadBodyNew();
		}
		if(windowObjectReference == null)
	    {
	  	var contextPath=document.getElementById('contextPath').value;
	    var arrParentGroup = strParentGroup.split('|');
	    var arrChildGroup = strChildGroup.split('|');
	    var arrParentOption = strParentOption.split('|');
	    var arrparentOptionValue = "";
	   // alert("arrParentOption length "+arrParentOption.length);
	   // alert("arrParentGroup "+arrParentGroup.length)
	   // alert("arrParentOption "+arrParentOption);
	   if(arrParentOption!='')
	   {
		   
	    for(i=0;i<arrParentOption.length;i++)
	    {
	    	var parents =arrParentOption[i]; 
	    	//alert("value : "+document.getElementById(parents).value);
	    	arrparentOptionValue=document.getElementById(arrParentOption[i]).value+","+arrparentOptionValue;
	    }
	   }
	    var arrMessages = strMessages.split('|');
	    
	    var validCount=0;
	    var strParentGroupValue = "";
	    if(strParentGroup=="")
	    {
	    	
	    	if(strChildGroup!="")
	    	{
	    		document.getElementById(strChildGroup).value="";
	    	}
	    	if(strParentOption!="")
	    	{
	    		document.getElementById(strParentOption).value="";
	    	}
	    }
	    for(i=0;i<arrParentGroup.length; i++)
	    {
	       if(arrParentGroup[i]!="")
	       {
	             if(document.forms[strFormName].elements[arrParentGroup[i]]	!=null && document.forms[strFormName].elements[arrParentGroup[i]].value=="")
	             {
	                  validCount=0;
	                  for(j=0;j<arrParentOption.length;j++)
	                  {
	                	// alert(arrParentOption[j]);
	                    if(arrParentOption[j]!="" && arrParentOption[j]==arrParentGroup[i]){
	                      validCount=1;
	                     // alert(arrParentOption[j]);
	                    }
	                  }
	                  if(validCount<1)
	                  {
	                      alert(arrMessages[i]);
	                      //document.forms[strFormName].elements['btn'+arrParentGroup[i]].focus();
	                      document.forms[strFormName].elements[arrParentGroup[i]].focus();
	                      return false;
	                  }
	                
	             }//end of if

	              if(strParentGroupValue==""){
	                  strParentGroupValue =	document.forms[strFormName].elements[arrParentGroup[i]].value;
	              }else{
	                  strParentGroupValue+="$~$~"+document.forms[strFormName].elements[arrParentGroup[i]].value;
	              }

	        }//end of if
	      }//end of for loop
	    
	    	//alert("windowObjectReference : "+windowObjectReference);
	    	window.child = null;
	 	    window.child = window.open("","lov","scrollbars=yes,status=0, alwaysraised=no,height=460,width=720,top=0,left=280");
	 	    openLOVWindows=window.open("","lov");
	 	    windowObjectReference = window.child;
	 	   openWins[curWin++]=window.child;
	 	    window.child.document.open("text/html");
	 	    window.child.document.writeln("<DIV	STYLE=\"position:absolute; left:10; font-Family:Sans-Serif; font-Size:18pt; visibility:show; \"><CENTER><IMG SRC=\""+contextPath+"/images/popup.gif\" alt=\"POPUP\" border=\"0\" align=bottom><br></CENTER></DIV>");
	 	    window.child.document.close();
	 	    window.child.focus();
	 	    //alert(strParentGroupValue);
	 	    var url=contextPath+"/popup.do?method=CodeDetails";
	 	    window.child.location.href=url+"&hdCurrPg=1&hdnLOVId="+lovID+"&targetFieldNameLOV="+strFieldName+"&frmNameLOV="+strFormName+"&pParentGroup="+arrParentGroup+"&pChildGroup="+strChildGroup+"&strParentOption="+arrparentOptionValue+"&strMethod="+strMethod+"&secondTargetField="+strsecondField+"&thirdTargetField="+strThirdField+"&fourthTargetField="+strFourthField;
	 	    
	    }
	    else
	    {
	    	 //windowObjectReference.focus(); 
	    	closeAllLovCallUnloadBodyNew();
	    	windowObjectReference = null;
	    	//alert("else "+windowObjectReference);
	    }
	   
	}
	function newSearchDeal(stage)
	{
		DisButClass.prototype.DisButMethod();
		var dealNo=document.getElementById("dealNo").value;
		var applicationno=document.getElementById("applicationno").value;
		var applicationdate=document.getElementById("applicationdate").value;
		var customername=document.getElementById("customername").value;
		var lbxProductID=document.getElementById("lbxProductID").value;
		var lbxscheme=document.getElementById("lbxscheme").value;
		var contextPath= document.getElementById("contextPath").value;
		 var reportingToUserId=document.getElementById("reportingToUserId").value;
		 if(document.getElementById("leadno")){
			 var leadno=document.getElementById("leadno").value;
			 }
				// alert(leadno);
		if(stage=='P')
		{
		  
		
			if(dealNo!=''||applicationno!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!=''||reportingToUserId!='' ||leadno!='')
			{
			
		
			if(customername!='' && customername.length>=3)
			{
				document.getElementById("commonForm").action=contextPath+"/commondeal.do?method=searchDealCapturing&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				return true;
			}
			else if(customername=='')
			{
				document.getElementById("commonForm").action=contextPath+"/commondeal.do?method=searchDealCapturing&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				
				return true;
			}
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				document.getElementById("searchButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	else
	{
		    var allBranches=document.getElementById("allBranches").checked;
		    //alert("allBranches "+allBranches);
			if(dealNo!=''||applicationno!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!=''||allBranches!=false||reportingToUserId!=''|| leadno!='')
			{
			
		
			if(customername!='' && customername.length>=3)
			{
				document.getElementById("commonForm").action=contextPath+"/commondeal.do?method=searchDealCapturing&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				return true;
			}
			else if(customername=='')
			{
				document.getElementById("commonForm").action=contextPath+"/commondeal.do?method=searchDealCapturing&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("commonForm").submit();
				
				return true;
			}
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				document.getElementById("searchButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field or select all branches");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
}

	function isDate(txtDate,format) {
		
		  //alert("ok"+format);
		
		    var objDate,  // date object initialized from the txtDate string
		        mSeconds, // txtDate in milliseconds
		        day,      // day
		        month,    // month
		        year;     // year
		    // date length should be 10 characters (no more no less)
		    if (txtDate.length !== 10) {
		        return false;
		    }
		    // third and sixth character should be '/'
		  if(txtDate.substring(2, 3)=='-')
		  {
		    if (txtDate.substring(2, 3) !== '-' || txtDate.substring(5, 6) !== '-') {
		        return false;
		    }
		  }
		  else  if(txtDate.substring(2, 3)=='/')
		  {
		     if (txtDate.substring(2, 3) !== '/' || txtDate.substring(5, 6) !== '/') {
		        return false;
		    }
		  }
		  else
		  {
			  return false;
		  }
		  // 	alert("ok");
		    // extract month, day and year from the txtDate (expected format is mm/dd/yyyy)
		    // subtraction will cast variables to integer implicitly (needed
		    // for !== comparing)
		   
		    if(format.substring(0, 2)=='dd')
		    {
		    	 day = txtDate.substring(0, 2)-0; 
				 month = txtDate.substring(3, 5) - 1; // because months in JS start from 0
				 year = txtDate.substring(6, 10) - 0;
		    }
		    else if(format.substring(0, 2)=='mm')
		    {
		    	 day = txtDate.substring(3, 5)-0; 
				 month = txtDate.substring(0, 2) - 1; // because months in JS start from 0
				 year = txtDate.substring(6, 10) - 0;
		    }
		  //  alert("ok");
		    // test year range
		    if (year < 1000 || year > 3000) {
		        return false;
		    }
		    // convert txtDate to milliseconds
		    mSeconds = (new Date(year, month, day)).getTime();
		    // initialize Date() object from calculated milliseconds
		    objDate = new Date();
		    objDate.setTime(mSeconds);
		    // compare input date and parts from Date() object
		    // if difference exists then date isn't valid
		    if (objDate.getFullYear() !== year ||
		        objDate.getMonth() !== month ||
		        objDate.getDate() !== day) {
		        return false;
		    }
		    // otherwise return true
		    return true;
		}
		function checkDate(txtDate)
		{
		    // define date string to test
		    var txtDateValue = document.getElementById(txtDate).value;		    
		    var formatD=document.getElementById("formatD").value;// must be take this format from jsp
		    if (isDate(txtDateValue,formatD) || txtDateValue == null || txtDateValue == '') 
		    {
		       return true;
		    }
		    else 
		    {
		        alert('Invalid date format!');
		        document.getElementById(txtDate).value='';
		        return false;
		    }
		}
		function clickText(textDate)
	  	{
	  	    //alert("ok");
	  		document.getElementById(textDate).value='';
	  	}
	  	
		function openMultiSelectLOVCommon(lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages,strMethod,LovListItemsIds)
		{
			//alert("ok"+document.getElementById('lbxProductID').value);
		  // var pnanme=trim(strParentOption);
		  // if(strParentOption!=''){
			//  var parentLovId = document.getElementById(pnanme).value;
		   //}
		  // document.getElementById(childForEmptytxt).value="";
		 //  document.getElementById(childForEmptyHtxt).value="";
			//alert("strParentOption "+strParentOption);
			if(!strMessages){
				test();
			}
			if(windowObjectReference != null)
			{
				windowObjectReference = null;
				closeAllLovCallUnloadBodyNew();
			}
			if(windowObjectReference == null)
			{
			var contextPath=document.getElementById('contextPath').value;
		    var arrParentGroup = strParentGroup.split('|');
		    var arrChildGroup = strChildGroup.split('|');
		    var arrParentOption = strParentOption.split('|');
		    var arrMessages = strMessages.split('|');
		    var LovListItemsIds = document.getElementById(LovListItemsIds).value;
		  
		    var validCount=0;
		    var strParentGroupValue = "";
		    var strParentOptionValue="";
		    if(strParentGroup=="")
		    {
		    	if(strChildGroup!="")
		    	{
		    		document.getElementById(strChildGroup).options.length = 0;
		    	}
		    	if(strParentOption!="")
		    	{
		    		document.getElementById(strParentOption).value="";
		    	}
		    	
		    }
		    
		    for(i=0;i<arrParentGroup.length; i++)
		    {
		       if(arrParentGroup[i]!="")
		       {
		             if(document.forms[strFormName].elements[arrParentGroup[i]]	!=null && document.forms[strFormName].elements[arrParentGroup[i]].value=="")
		             {
		                  validCount=0;
		                  for(j=0;j<arrParentOption.length;j++)
		                  {
		                	  //alert("document.getElementById(arrParentOption[j] "+document.getElementById(arrParentOption[j]).value);
		          		    //alert("document.getElementById(arrParentGroup[i]) "+document.getElementById(arrParentGroup[i]).value);
		                    if(document.getElementById(arrParentOption[j]).value !="" && document.getElementById(arrParentOption[j]).value==document.getElementById(arrParentGroup[i]).value){
		                      validCount=1;
		                     // alert(arrParentOption[j]);
		                    }
		                  }
		                  if(validCount!=1)
		                  {
		                      alert(arrMessages[i]);
		                      document.forms[strFormName].elements['btn'+arrParentGroup[i]].focus();
		                      return;
		                  }
		             }//end of if

		              if(strParentGroupValue==""){
		                  strParentGroupValue =	document.forms[strFormName].elements[arrParentGroup[i]].value;
		              }else{
		                  strParentGroupValue+="$~$~"+document.forms[strFormName].elements[arrParentGroup[i]].value;
		              }
		              
		              if(strParentOptionValue==""){
		            	  strParentOptionValue =	document.forms[strFormName].elements[arrParentOption[i]].value;
		              }else{
		            	  strParentOptionValue+="$~$~"+document.forms[strFormName].elements[arrParentOption[i]].value;
		              }

		        }//end of if
		      }//end of for loop
		   // alert("arrParentOption2222222222 "+arrParentOption);
		    window.child = null;
		   // window.child = window.open("","lov","scrollbars=yes,status=0, alwaysraised=no,height=460,width=650,top=0,left=280");
		    //openLOVWindows=window.open("","lov");
		    window.child =window.open("", "lov", "resizable=1, height=460, width=720, toolbar=0, scrollbars=1,top=0,left=280,status=0, alwaysraised=no");
		    windowObjectReference = window.child;
		 	openWins[curWin++]=window.child;
		    window.child.document.open("text/html");
		    window.child.document.writeln("<DIV	STYLE=\"position:absolute; left:10; font-Family:Sans-Serif; font-Size:18pt; visibility:show; \"><CENTER><IMG SRC=\"../images/popup.gif\" alt=\"POPUP\" border=\"0\" align=bottom><br></CENTER></DIV>");
		    window.child.document.close();
		    window.child.focus();
		    //alert(strParentGroupValue);
		    var url=contextPath+"/multiSelectPopup.do?method=MultiSelectCodeDetails";
		    window.child.location.href=url+"&hdCurrPg=1&hdnLOVId="+lovID+"&targetFieldNameLOV="+strFieldName+"&frmNameLOV="+strFormName+"&pParentGroup="+strParentGroupValue+"&pChildGroup="+strChildGroup+"&strParentOption="+strParentOptionValue+"&strMethod="+strMethod+"&LovListItemsIds="+LovListItemsIds;

			}
		    else
		    {
		    	 //windowObjectReference.focus(); 
		    	closeAllLovCallUnloadBodyNew();
		    	windowObjectReference = null;
		    	//alert("else "+windowObjectReference);
		    }
		}
		
		
		function multiSelectLovSaveRecord(val) 
		 {
		 	//alert("ok"+val);
		 	 var targetFieldNameLOV = document.getElementById("targetFieldNameLOV").value;
		 	// alert(targetFieldNameLOV);
		 	 var targetHiddenLovId = document.getElementById("targetHiddenLovId").value;
		 	// alert("targetHiddenLovId "+window.opener.document.getElementById(targetHiddenLovId).value);
		     var selectedItem = window.opener.document.getElementById(targetHiddenLovId).value;
		     var selectedItemIds="";
		     if(selectedItem !='')
		     {
		    	 selectedItemIds = selectedItem.split('|');
		     }
		     
		 	 var allIDs = "";
		 	 
		 	 var recordId=document.getElementById("recordId").value;
		 	 var noOfColumn=0;
		 	 noOfColumn=document.getElementById("noOfColumn").value;
		 	 
		 	 var flag=0;
		 	
		 	 var ch=document.getElementsByName('checkboxBtn');
//		 	if(ch.length>0)
//		 	{
//		 		window.opener.document.getElementById(targetFieldNameLOV).options.length = 0;
//		 		
//		 	}
		 	var z=window.opener.document.getElementById(targetFieldNameLOV);
		 	for(var j=0;j<ch.length;j++)
		 	{
		 		for(var i=0;i<selectedItemIds.length;i++)
		 		{
		 			if(ch[j].checked==false)
		 			{
		 				if(selectedItemIds[i] == ch[j].value)
		 				{
		 					for(var y = 0; y < z.options.length; y++)
		 				   {
		 				      if(z.options[y].value == ch[j].value)
		 				      {
		 				        break;
		 				      }
		 				   }
		 					var oOption = z.options[y]; 
		 					z.removeChild(oOption);
		 					selectedItem = selectedItem.replace(selectedItemIds[i],"");
		 					
		 					selectedItem = selectedItem.replace("||","|");
		 					
		 					if(selectedItem.charAt(selectedItem.length-1)=="|")
		 					{
		 						selectedItem = selectedItem.substring(0, selectedItem.length-1);
		 					}
		 					if(selectedItem.charAt(0)=="|")
		 					{
		 						selectedItem = selectedItem.substring(1, selectedItem.length);
		 					}
		 					window.opener.document.getElementById(targetHiddenLovId).value=selectedItem;
		 					
		 				}
		 			}
		 		}
		 			
		 	}
		 	 
		 	 
		 	 for(i=0;i<ch.length;i++)
		 		{
		 		  	 		
		 		   if(ch[i].checked==true)
		 			{
		 			   id=ch[i].value;
		 			 
		 			   strValues=document.getElementById(id).value;
		 			  // alert("strValues "+strValues);
		 			   // Add an Option object to Drop Down/List Box
		 			  // alert( window.opener.document.getElementById(targetFieldNameLOV).setAttribute("option", "true"));
		 			   var x=window.opener.document.getElementById(targetFieldNameLOV);
		 			   var option=window.opener.document.createElement("option");
		 			   
		 			   option.text = strValues;
		 			   option.value = id;
		 			  try
		 			  {
		 			 //  for IE earlier than version 8
		 				  if(selectedItemIds !='')
		 				  {
		 					 var j=0;
		 					 var currId ="";
		 					 IdFlag =false;
		 					  while(j<selectedItemIds.length)
			 				  {
			 					  if(selectedItemIds[j] != id)
			 					  {
			 						 currId = j;
			 						 IdFlag = true;
			 					  }
			 					  else
			 					  {
			 						 IdFlag = false;
			 						  break;
			 					  }        
			 					  j++;
			 				  }
		 					  if(IdFlag==true)
		 					  {
		 						 allIDs=allIDs+id+"|";
		 						 x.add(option,x.options[null]);
		 					  }
		 				  }
		 				  else
		 				  {
		 					 allIDs=allIDs+id+"|";
		 					 x.add(option,x.options[null]);
		 				  }
		 			  }
		 			catch (e)
		 			  {
		 				//alert("catch");
		 			    x.add(option,null);
		 			  }
		 			  // Assign text and value to Option object
		 			    if(noOfColumn==3)
		 			 	{
		 				   var  otherComponent1Arr =document.getElementsByName('otherComponent1');
		 				   otherComponent1 =otherComponent1Arr[i].value;
		 			 	}
		 			   
		 			  flag=1;
		 			}
		 		}
		 	
		 	 if(flag==0)
		 	 {
		 		 
		 		//window.opener.document.getElementById(targetFieldNameLOV).options.length = 0;
		 	//	window.opener.document.getElementById(targetHiddenLovId).value='';
	 		 	 if(val!='')
	 	 		 {
	 	 			 window[val]();
	 	 		 }
	 	 		 else{
	 	 			 window.close();
	 	 		 }
		 		 return false;
		 	 }
		 	 else
		 	 {
		 		allIDs = allIDs.substring(0, allIDs.length-1);
		 		if(window.opener.document.getElementById(targetHiddenLovId).value !='')
		 		{
		 			if(allIDs=='')
		 			{
		 				window.opener.document.getElementById(targetHiddenLovId).value=window.opener.document.getElementById(targetHiddenLovId).value+trim(allIDs);
		 			}
		 			else
		 			{
		 				window.opener.document.getElementById(targetHiddenLovId).value=window.opener.document.getElementById(targetHiddenLovId).value+"|"+trim(allIDs);
		 			}
		 		}
		 		else
		 		{
		 			window.opener.document.getElementById(targetHiddenLovId).value=trim(allIDs);
		 		}
		 		
		 		 	 if(val!='')
		 	 		 {
		 	 			 window[val]();
		 	 		 }
		 	 		 else{
		 	 			 window.close();
		 	 		 }
		 	 }
		 	 
		 }
		
		function isNumberKey(evt) 
		{
		var charCode = (evt.which) ? evt.which : event.keyCode;

		if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric Allowed Here");
			return false;
		}
			return true;
		}
		
		
		
		openWins = new Array();
		curWin = 0;
		otherWindows = new Array();
		curWinAnother = 0;
		aotherWindows = new Array();
		

		function closeLovCallonLov1() { 
					openWins[curWin++] = window.open("",'lov',"scrollbars=yes,status=0, alwaysraised=no,height=460,width=650,top=0,left=280"); 
			}
		
		function test() { 
					openWins[curWin++] = window.open("",'lov',"scrollbars=yes,status=0, alwaysraised=no,height=460,width=650,top=0,left=280"); 
			}
		
		function closeLovCallonLov(val) { 
				if(document.getElementById(val).value=='' || document.getElementById(val).value== null || document.getElementById(val).value == false){
				openWins[curWin++] = '';
				}else
				openWins[curWin++] = window.open("",'lov',"scrollbars=yes,status=0, alwaysraised=no,height=460,width=650,top=0,left=280"); 
				}
		
		function closeLovCallonLovBAB(val) { 
			if(document.getElementById(val).value=='' || document.getElementById(val).value== null || document.getElementById(val).value == false){
			openWins[curWin++] = '';
			}else
			openWins[curWin++] = window.open("",'lov',"scrollbars=yes,status=0, alwaysraised=no,height=460,width=650,top=0,left=280"); 
			}

		function callOnLinkOrButtonWindow(val) 
		{
			//alert("ok"+val);
			if(document.getElementById(val).value=='' || document.getElementById(val).value== null || document.getElementById(val).value == false)
				openWins[curWin++] = '';
			else
				openWins[curWin++] = window.open('','name',"height=300,width=1000,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		}
		
		
		
			function callOnInCreateCustomer(val1,val2,val3) {
			//alert("ok"+val);
			var v1= document.getElementById(val1).value;
			var v2= document.getElementById(val2).value;
			var v3= document.getElementById(val3).value;
			if(v1=='' || v1== null || v1 == false||v2=='' || v2== null || v2 == false||v3=='' || v3== null || v3 == false){
				openWins[curWin++] = '';
				}else
					otherWindows[curWin++] = window.open("",'name','height=400,width=1000,top=200,left=250');
				}
		

		function closeAllLovCallUnloadBody() {
			
			var len = openWins.length;
			
				for(i=0; i<len ;i++)
				{	if (openWins[i] && !openWins[i].closed)
					{
						openWins[i].close();
					}
				}
				windowObjectReference = null;
		}
		
		function closeAllLovCallUnloadBodyNew() {
		
			var len = openWins.length-1;
				for(i=0; i<len ;i++)
				{	if (openWins[i] && !openWins[i].closed)
					{
						openWins[i].close();
					}
				}
				
				windowObjectReference = null;
		}

		
		function closeAllWindowCallUnloadBody() {
			
			for(i=0; i<otherWindows.length; i++)
			{
				if (otherWindows[i] && !otherWindows[i].closed)
				{
					otherWindows[i].close();
				}
			}
		}
		function closeAllWindowCallUnloadBodyAn() {
			for(i=0; i<aotherWindows.length; i++)
			{
				if (aotherWindows[i] && !aotherWindows[i].closed)
				{
					aotherWindows[i].close();
				}
			}
		
		}
		
		function callOnLinkOrButtonWindowCollateral(val)
		{
			if(document.getElementById(val).value=='N'||document.getElementById(val).value==''){
				openWins[curWin++] = '';
				}else
				{
					otherWindows[curWin++] = window.open("",'name','height=400,width=1000,top=200,left=250');
				}
		}
		
		
		
		
		//--------------------------------Disable Button
		
		
		function DisButClass(){

		}

		var DisButClass = new DisButClass();

		DisButClass.prototype = 
		{ 
			DisButMethod: function(){
				if($("button")){
					$("button").attr("disabled", true);
					$("button").css({"opacity":"0.4","filter":"alpha(opacity=40)"});
				}else
					$("button").css({"opacity":"1","filter":"alpha(opacity=100)"});
			} ,
			EnbButMethod: function(){
				if($("button")){
					$("button").attr("disabled", false);
					$("button").css({"opacity":"1","filter":"alpha(opacity=100)"});
				}else
					$("button").css({"opacity":"0.4","filter":"alpha(opacity=40)"});
					
			} ,
			
			ForDiffDisEnb: function(san){
				//alert(document.getElementById(san).getAttribute("disabled")+"-san-"+san);
				if(document.getElementById(san)){
					$(document.getElementById(san)).attr("disabled", true);
					$(document.getElementById(san)).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
				}else{
					$("button").attr("disabled", false);
					$("button").css({"opacity":"1","filter":"alpha(opacity=100)"});
				}
			},
		    MyProperty: "My Property" 

		}
		
		
		
		function applyToolTip(id){
			
			var val =  document.getElementById(id).value;
			document.getElementById("tool").setAttribute('name', val);
			
		}	

		
		function upperMe(compId) { 
		    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
		    return true;
		}
		function numberswithminus(e, san, Max){
			var dynaVal = san;
			document.getElementById(dynaVal).maxLength = Max+3;
			var unicode=e.charCode? e.charCode : e.keyCode
			if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 )){ 
			//if the key isn't the backspace key (which we should allow)
		
			if(unicode==45){
				document.getElementById(dynaVal).value="";		
		
			}
			if (((unicode<45||unicode>57) && unicode!=46 && unicode!=47)|| unicode ==16 )//if not a number
			return false; //disable key press
			}
			}
		function truncate(iValue,iRoundPara)
		{
			var irp=0;
			
			if(iRoundPara!='')
			{
				irp=parseInt(iRoundPara);
			}
			
			var str = "" + iValue;
			var str = str.substring(0, str.length-irp);
			iValue = parseInt(str); 
			return iValue;
		}
		
         function getRoundedValue(iValue,iRoundType,iRoundPara,chargeMethod1)
         {
			//alert("iValue: "+iValue+"iRoundType: "+iRoundType);
        	
		//	var vFinalValue =  0.00;
		//	var irp=0;
			if(chargeMethod1=='PERCENTAGE')
        	{
        		var vFinalValue =  0.0000;
        		var irp=0.0000;
        	}
        	else
        	{
        		var vFinalValue =  0.00;
        		var irp=0;
        	}
			
			if(iRoundPara!='')
			{
				irp=parseInt(iRoundPara);
			}
			
			if(iRoundType=='D')
			{
				vFinalValue = truncate(iValue,irp);
			}
			else if(iRoundType=='U')
			{
				if(chargeMethod1=='PERCENTAGE')
				{
					vFinalValue = iValue * Math.pow(10, irp)/Math.pow(10, irp);
					vFinalValue =vFinalValue.toFixed(4);
				
				}
				else
				{
					vFinalValue = Math.ceil(iValue * Math.pow(10, irp))/Math.pow(10, irp);
				}

			//	vFinalValue = Math.ceil(iValue * Math.pow(10, irp))/Math.pow(10, irp);
				
			}
			else
			{
				if(irp==0)
				{
					vFinalValue=(Math.round(iValue)).toFixed(0);
				}
				else if(irp==1)
				{
					vFinalValue=(Math.round(10*iValue)/10).toFixed(1);
				}
				else if(irp==2)
				{
					vFinalValue=(Math.round(100*iValue)/100).toFixed(2);
				}
				else if(irp==3)
				{
					vFinalValue=(Math.round(1000*iValue)/1000).toFixed(3);
				}
				else if(irp==4)
				{
					vFinalValue=(Math.round(10000*iValue)/10000).toFixed(4);
				}
				
			}
			//alert("Rounded Value: "+vFinalValue);
			return vFinalValue;
			
		 }	
		
         
         function daysBetweenDates(startDate, endDate) {
        	  
        	    // Validate input
        	    if (endDate < startDate)
        	        return 0;
        	    
        	    // Calculate days between dates
        	    var millisecondsPerDay = 86400 * 1000; // Day in milliseconds
        	    startDate.setHours(0,0,0,1);  // Start just after midnight
        	    endDate.setHours(23,59,59,999);  // End just before midnight
        	    var diff = endDate - startDate;  // Milliseconds between datetime objects    
        	    var days = Math.ceil(diff / millisecondsPerDay);
        	    //alert("days: "+days);
        	    days=days-1;
        	    /*
        	    // Subtract two weekend days for every week in between
        	    var weeks = Math.floor(days / 7);
        	    //var days = days - (weeks * 2);

        	    // Handle special cases
        	    var startDay = startDate.getDay();
        	    var endDay = endDate.getDay();
        	    
        	    // Remove weekend not previously removed.   
        	    if (startDay - endDay > 1)         
        	        days = days - 2;      
        	    
        	    // Remove start day if span starts on Sunday but ends before Saturday
        	    if (startDay == 0 && endDay != 6)
        	        days = days - 1  
        	            
        	    // Remove end day if span ends on Saturday but starts after Sunday
        	    if (endDay == 6 && startDay != 0)
        	        days = days - 1  
        	        */
        	    
        	    //alert("days: "+days);
        	    
        	    return days;
        	}
         
  // Amit Quality Check Starts
 function qualityCheckSearch()
 {
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var applicationno=document.getElementById("applicationno").value;
	var customername=document.getElementById("customername").value;
	var lbxProductID=document.getElementById("lbxProductID").value;
	var lbxscheme=document.getElementById("lbxscheme").value;
	var contextPath= document.getElementById("contextPath").value;
	var stage = document.getElementById("stage").value;
	
		if(dealNo!='' || applicationno!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
		{
		
	
		if(customername!='' && customername.length>=3)
		{
			document.getElementById("commonForm").action=contextPath+"/qualityCheckAction.do?method=searchDealForQualityCheck";
			document.getElementById("processingImage").style.display='block';
			document.getElementById("commonForm").submit();
			return true;
		}
		else if(customername=='')
		{
			//document.getElementById("commonForm").action=contextPath+"/commondeal.do?method=searchDealCapturing&stage="+stage;
			document.getElementById("commonForm").action=contextPath+"/qualityCheckAction.do?method=searchDealForQualityCheck";
			document.getElementById("processingImage").style.display='block';
			document.getElementById("commonForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
     
 function qualityCheckSaveDeal()
 {
	 DisButClass.prototype.DisButMethod();
	 var chk = document.getElementsByName("chk");
	 var sanctionValidTill = document.getElementsByName("sanctionValidTill");
	 var count=0;
	 var remarks="";
	 var dealId="";
	 var decision="";
	 var path = document.getElementById("contextPath").value;
	 var stage = document.getElementById("stage").value;
	 for(var i=0; i<chk.length; i++)
	 {
		 if(document.getElementById("chk"+i).checked)
		 {	 count++;
			 if(document.getElementById("remarks"+i).value!="")
			 {
				 remarks = remarks + document.getElementById('remarks'+i).value+"@";
				 dealId = dealId + document.getElementById('chk'+i).value+"@";
				 decision = decision + document.getElementById('decision'+i).value+"@";
			 }
			 else
			 {
				 alert("Please enter the Remarks.");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(document.getElementById('functionId').value=='4000103'){
			 if(document.getElementById('decision'+i).value=='A')
			 {
				 var businessdate=document.getElementById("businessdate").value;
				 var dt1=getDateObject(sanctionValidTill[i].value,'-');
				 var dt2=getDateObject(businessdate,'-');
				 if(dt2>dt1)
				 {
					 alert("Sanction Valid Till Date is Expired.");
					 DisButClass.prototype.EnbButMethod();
					 return false;
				 }	
			 }}
		 }
	 }
	 if(count==0)
	 {
		 alert("Please Select atleast One Deal.");
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
	 else
	 {
		 document.getElementById("commonForm").action=path+"/qualityCheckAction.do?method=qualityCheckSaveDeal&remarks="+remarks+"&dealId="+dealId+"&decision="+decision+"&stage="+stage;
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("commonForm").submit();
		 return true;
	 }
 }
// Amit Quality Check Ends
 
// Ankit function for tooltip in gl bill grid
 function applyToolTipForGL(id,check){
		var val =  document.getElementById(id).value;
		document.getElementById("tool"+check).setAttribute('name', val);
		
	}	// End

 function enableAnchor(){

	//alert("enableAnchor ");
	var upperMaxSize=parent.parent.menu.document.commonFormMenu.upperMaxSize;
	//alert("enableAnchor "+upperMaxSize.value);
	var maxNum=parseInt(upperMaxSize.value);

	 for (var j=0;j<maxNum;j++ ) {
	     
	    
	     for ( var i=0;i<100;i++ ) {
	          	
	          
	     	     var anchorId="hrefIda"+j+"b"+i;
	     	
	     	 
	     	     var ab=parent.parent.menu.document.getElementById(anchorId);
	     	    
	     	     if(ab!=null && ab!='undefined')
	     	     {
	     	         if(ab.attributes['href_bak']!=null && ab.attributes['href_bak']!='undefined')
	     	         {
			     		 //alert("enableAnchor in atribute: "+ab.attributes['href_bak'].nodeValue);
			     		 if(ab.attributes['href_bak'].nodeValue!=null && ab.attributes['href_bak'].nodeValue!='undefined')
			     		 {
				     		 ab.setAttribute('href', ab.attributes['href_bak'].nodeValue);
				     		 ab.removeAttribute('href_bak');
				             ab.style.color="white";
	     	            }
		             }
		           
	             }
	     	 }
	   }


	}
 
//Manish changes start
 function lockButtonOnClick(value)
 {
 	alert("Please Wait for few seconds to Proceed the Next Process ");
 	DisButClass.prototype.ForDiffDisEnb(value);
	setInterval(function () { $(document.getElementById(value)).attr("disabled", false);
	$(document.getElementById(value)).css({"opacity":"1","filter":"alpha(opacity=100)"});}, 30000);
 }
 //Manish changes End
 
 // aditya start
 function imposeMaxLength(Object, MaxLen)
 {
   if(Object.value.length > MaxLen)
   {
 	  alert("Max Length can not be greater than "+MaxLen);
 	  return false;
   }
   return true;
 }
//aditya end
//Rohit Changes for security starts
	function toLogoutHome(val)
			{
		var counter = 1;
if(counter==1)
{



    if(val=='logout')
    { 
         counter=counter+1;
         window.parent.location="logoff.do";
		   window.parent.window.close(); 
	       return true;
    }
    else
    {
      counter=counter+1;
      return false;
    }
}

}
//Rohit Changes for security end

// shreyansh for customer linking at lead entry

	function saveCustomerSrcDesc()
	   {
	          
	           if(checkboxCheck(document.getElementsByName('dealstore')))
	           {
	           		var customerId="";
	           		var custId=document.getElementsByName('dealstore');
	           		for(i=0;i<custId.length;i++)
	           		{
	           			if(custId[i].checked==true)
	           			{
	           				customerId=custId[i].value;
	           				break;
	           			}
	           		}
	                var cname=document.getElementById("cust_name").value;
	                 if(customerId!='')
	                  {	
	                	 getCustomerSrcDesc(cname,customerId);
	                  }	            
		     	}
		     	else
		     	{
		     		alert("Select atleast one record");
		     	}
	   }
	function getCustomerSrcDesc(cusName,cusId)
	  {
	  		 var contextPath=window.opener.document.getElementById("contextPath").value;
	  		 var address = contextPath+"/approveCustomerAction.do?method=linkCusSrcDesc&codeId="+cusId+"&cname="+cusName;
			sendCustomerSrcDesc(address);
	  		 return true;
	  	 }
		 
	  function sendCustomerSrcDesc(address) {
	  	var request = getRequestObject();
	  	request.onreadystatechange = function () {
	  		resultCustomerSrcDesc(request);	
	  	};
	  	request.open("POST", address, true);
	  	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	  	request.send();	
	  }
	  function resultCustomerSrcDesc(request){
	  	
	  	if ((request.readyState == 4) && (request.status == 200)) 
	  	{
	  		var str = request.responseText;
	  		var s1 = str.split("$:");
	  		window.opener.document.getElementById('description').value=((trim(s1[1]))+" -- "+(trim(s1[0]))); 
	  		window.close();
	  	}
	  }
	  
	// shreyansh ends for linking customer 
	  
	  
	  // shreyans starts for mobile user mapping master
	  function getMobileNo() {
			var contextPath=window.opener.document.getElementById("contextPath").value;
				var address = contextPath+"/mobileUserMasterSearch.do?method=getMobileNo&id="+getId;
					sendMobileNo(address);
			  		 return true;
			}
			function sendMobileNo(address) {
			  	var request = getRequestObject();
			  	request.onreadystatechange = function () {
			  		resultMobileNo(request);	
			  	};
			  	request.open("POST", address, true);
			  	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			  	request.send();	
			  }
			  function resultMobileNo(request){
			  	
			  	if ((request.readyState == 4) && (request.status == 200)) 
			  	{
			  		var str = request.responseText;
			  		window.opener.document.getElementById('mobile').value=((trim(str))); 
			  		window.close();
			  	}
			  }
// shreyansh ends for mobile user mapping master
			  function openWindow(url)
			  {
			  return window.open(url, "_blank", "resizable=1, height=400,top=200,left=250,width=1100, toolbar=0, scrollbars=1");
			  }			  