function trim(str) {
	return ltrim(rtrim(str));
}

function deleteCreditRating()
{
	DisButClass.prototype.DisButMethod();
	var contextPath = document.getElementById("contextPath").value;
	    if(check())
	    {	  	
			document.getElementById("RatingForm").action=contextPath+"/deleteCreditRatingAction.do?method=deleteCreditRating";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("RatingForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one Record");
//	    	document.getElementById("delete").removeAttribute("disabled","true");
	    	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
}
  

	function check()
	{
	    //alert("ok");
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
	

	function updateRatingDetail()
	{

		DisButClass.prototype.DisButMethod();
		var contextPath = document.getElementById("contextPath").value;
		//alert("in updateRatingDetail");
		
		var institute = document.getElementById("institute");
    	var rating = document.getElementById("rating");
    	var creditDate = document.getElementById("creditDate");

    	if(trim(institute.value) == ''|| trim(rating.value) == ''|| trim(creditDate.value) == ''){
    		var msg= '';
    		
    		if(institute.value == '')
    			msg += '* Institution Name is required.\n';
    		if(rating.value == '')
    			msg += '* Rating is required.\n';
    		if(creditDate.value == '')
    			msg += '* Date is required.\n';
    		    		
    		if(msg.match("Institution")){
    			institute.focus();
    		}else if(msg.match("Rating")){
    			rating.focus();
    		}else if(msg.match("Date")){
    			creditDate.focus();
    		}
    		
    		alert(msg);
//    		document.getElementById("save").removeAttribute("disabled","true");
    		DisButClass.prototype.EnbButMethod();
    		return false;
    		
    	}else if(institute.value != ''|| rating.value != ''|| creditDate.value != ''){
			document.getElementById("RatingForm").action=contextPath+"/creditRatingPageAction.do?method=updateRatingDetails";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("RatingForm").submit();
			return true;    	
    	}else {
//   			document.getElementById("save").removeAttribute("disabled","true");
   			DisButClass.prototype.EnbButMethod();
			return false;
    	}
    }
   
	function saveRatingDetail()
	{
		DisButClass.prototype.DisButMethod();
		var contextPath = document.getElementById("contextPath").value;
    	
		var institute = document.getElementById("institute");
    	var rating = document.getElementById("rating");
    	var creditDate = document.getElementById("creditDate");

    	if(trim(institute.value) == ''|| trim(rating.value) == ''|| trim(creditDate.value) == ''){
    		var msg= '';
    		
    		if(institute.value == '')
    			msg += '* Institution Name is required.\n';
    		if(rating.value == '')
    			msg += '* Rating is required.\n';
    		if(creditDate.value == '')
    			msg += '* Date is required.\n';
    		    		
    		if(msg.match("Institution")){
    			institute.focus();
    		}else if(msg.match("Rating")){
    			rating.focus();
    		}else if(msg.match("Date")){
    			creditDate.focus();
    		}
    		
    		alert(msg);
//    		document.getElementById("save").removeAttribute("disabled","true");
    		DisButClass.prototype.EnbButMethod();
    		
    		return false;
    	}else if(institute.value != ''|| rating.value != ''|| creditDate.value != ''){
    		document.getElementById("RatingForm").action=contextPath+"/creditRatingPageAction.do?method=saveRatingDetails";
    		document.getElementById("processingImage").style.display = '';
 			document.getElementById("RatingForm").submit();
 			return true; 
    	}else {
//   			document.getElementById("save").removeAttribute("disabled","true");
   			DisButClass.prototype.EnbButMethod();
			return false;
    	}
   
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
	
	function modifyCreditRatingDetails(id)
	{
		var contextPath = document.getElementById("contextPath").value;

			document.getElementById("RatingForm").action=contextPath+"/fetchCreditRatingAction.do?method=fetchCreditRating&chk="+id;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("RatingForm").submit();

	  
	}
	
	function output()
	{  
		alert("ok");
		var checkStatus = document.getElementById("blackListed").value;
		if(checkStatus=='on')
		{
			alert(checkStatus);
		}
	
	}
	
	
	
	
	function creditClear()
	{
		document.getElementById("institute").value='';
		document.getElementById("rating").value='';
		document.getElementById("creditDate").value='';
		return false;
	}
	
	
	function upperMe(compId) { 
	    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
	    return true;
	}