
function savereAllocation()
{
	var total = 0;
	var sourcepath=document.getElementById("contextPath").value;
	var i=0;
	var checkid=document.getElementsByName("checkId");
	
	for(i=0;i < checkid.length;i++)
	{
	
				if(checkid[i].checked == true){
				total += 1;
				
					if(total>0 && (document.getElementById("remarks").value !="") && (document.getElementById("heirarchyuser").value !=""))
					{
						document.getElementById("reallocationCollForm").action=sourcepath+"/reallocation.do?method=updatereAllocation";
				
						document.getElementById("reallocationCollForm").submit();

						document.getElementById("save").removeAttribute("disabled","true");
						

					}
				}
	

	}
	if(total==0){
		alert ("Please select at least one checkbox")
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}

	 if((document.getElementById("heirarchyuser").value == "") ||(document.getElementById("heirarchyuser").value == null )){
	alert("Please select assigned to");
	document.getElementById("save").removeAttribute("disabled","true");
	return false;
	}
	if((document.getElementById("remarks").value == "") ||(document.getElementById("remarks").value == null)){
	alert("Please fill remarks");
	document.getElementById("save").removeAttribute("disabled","true");
	return false;
	}
	 
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function reAllocationSearch(val){ 
    
	     var contextPath=document.getElementById("contextPath").value;
//	     alert("ok");
	     if((document.getElementById("loanno").value =='') 
	    		 && (document.getElementById("customername").value =='') 
	    		 && (document.getElementById("product").value=='') 
	    		 && (document.getElementById("dpd1").value=='') 
	    		 && (document.getElementById("dpd2").value =='') 
	    		 && (document.getElementById("queue").value=='')
	    		 && (document.getElementById("pos1").value =='') 
	    		 && (document.getElementById("pos2").value=='')
	    		 && (document.getElementById("user").value=='')
	    		 && (document.getElementById("custype").value=='')
	    	 && (document.getElementById("balanceprincipal").value=='')
	    		 && (document.getElementById("custcategory").value=='')
	    		 && (document.getElementById("assignfrom").value=='')
	    		 && (document.getElementById("assignto").value=='')){ 
	    	   alert(val);
		document.getElementById("search").removeAttribute("disabled");
		return false;
	 }else {
			var assignFrom=document.getElementById("assignfrom").value;
			var assignto=document.getElementById("assignto").value;
			var formatD=document.getElementById("formatD").value;
			assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
			assignto=getDateObject(assignto,formatD.substring(2, 3));
			  
			 if(document.getElementById("dpd1").value=='.'){
				alert("DPD >= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("dpd2").value=='.'){
				alert("DPD <= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos1").value=='.'){
				alert("Over Due Amount <= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("pos2").value=='.'){
				alert("Over Due Amount >= is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if(document.getElementById("balanceprincipal").value=='.'){
				alert("Balance Principal is not in correct Format!");
				document.getElementById("search").removeAttribute("disabled");
				return false;
			}else if((document.getElementById("assignfrom").value !="")&&(document.getElementById("assignto").value !="")){
			if(assignFrom>assignto){
				alert(" Assign to date  must be greater than Assign from date ");
			    document.getElementById("search").removeAttribute("disabled");
				return false;
			  }
			}else{
	 document.getElementById("reallocationCollForm").action = contextPath+"/reallocationSearch.do";
	 document.getElementById("reallocationCollForm").submit();
	 		 return true;
	 }
 }
}  
	     function getDateObject(dateString,dateSeperator)
	     {
	     		var dateParts = dateString.split(dateSeperator);
				var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
				return dateObject;

	     }
		
function listbox_moveacross(sourceID, destID) {
	  //alert("in the function listbox_moveacross");
     var src = document.getElementById(sourceID);
   //  alert(src);
       var dest = document.getElementById(destID);
    //   alert(dest);
			for(var count=0; count < src.options.length; count++) {

	 	if(src.options[count].selected == true) {
						var option = src.options[count];

						var newOption = document.createElement("option");
						newOption.value = option.value;
						newOption.text = option.text;
						newOption.selected = true;
						try {
								 dest.add(newOption, null); //Standard
								 src.remove(count, null);
						 }catch(error) {
								 dest.add(newOption); // IE only
								 src.remove(count);
						 }
						count--;
}
}
}		

function openRecordingTrailCaseHistory(loanNo){
//	alert("Loan no :---"+loanNo);
	//alert("exclationFlag no :---"+exclationFlag);
	  var contextPath=document.getElementById("contextPath").value;
	var url = contextPath+"/contactRecordingDispatchAction.do?method=openContactRecordingTrail&loanId="+loanNo+"&caseHistory=caseHistory";
	newwindow= window.open(url,'trailwin','height=800,width=1000,top=0,left=250,scrollbars=yes ');
    if (window.focus) {newwindow.focus()}
         return true;
}
	