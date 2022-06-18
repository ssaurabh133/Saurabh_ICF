

function fnEditPassword(){
	
	var sourcepath=document.getElementById("contextPath").value;
 
	if(validateChangePasswordMasterAddDynaValidatorForm(document.getElementById("changePasswordMasterForm"))){
    document.getElementById("changePasswordMasterForm").action="changePasswordMaster.do?method=updateChangePassword";
    if((document.getElementById("newPassword").value)!=(document.getElementById("confirmPassword").value))
    {
    	alert("New Password and Confirm Password should be same.");
    }
	document.getElementById("changePasswordMasterForm").submit();
	return true;
	}
}

function validatePwd(newPassword) {

	
	var errorMsg = "";
	var space  = " ";

	var fieldvalue  = document.getElementById("newPassword").value;
	var oldPassword=document.getElementById("oldPassword").value;
	//alert("oldPassword"+oldPassword);
	
	var fieldlength = fieldvalue.length;

	if(validateChangePasswordMasterAddDynaValidatorForm(document.getElementById("changePasswordMasterForm"))){
	    
	//It must not contain a space
	if (fieldvalue.indexOf(space) > -1) {
	     alert("Password cannot include space.");
	     return false;
	}     
	 
	//It must contain at least one number character
	if (!(fieldvalue.match(/\d/))) {
		alert("Password must contain at least one numeric character.");
		return false;
	}
	//It must start with at least one letter     
	if (!(fieldvalue.match(/^[a-zA-Z]+/))) {
		alert("Password must start with alphabet.");
		return false;
	}
	
	//It must contain at least one special character
	if (!(fieldvalue.match(/\W+/))) {
		alert( "Password must contain at least one special character - #,@,%,!");
		return false;
	}
	//It must be at least 8 characters long.
	if (!(fieldlength >= 8)) {
		alert( "Password must be at least 8 characters long.");
		return false;
	}
    if((document.getElementById("newPassword").value)!=(document.getElementById("confirmPassword").value))
    {
    	alert("Password and Confirm Password should be same.");
    	return false;
    }
    	var flag = document.getElementById("flag").value;
    
    	if(flag == 'CL'){
    		 if((document.getElementById("ques1").value)==null)
    		 {
    			 
    			 alert("Please enter Question1.");
    	        	document.getElementById("ques1").focus();
    		 }else{ 
			 var question1=document.getElementById("ques1").value;
			 if(new RegExp("[^\\w\\s-]").test(question1))
			 {
			    alert("Special characters are not allowed in Question1.");
			    return false;
			 }
    		 }
    		 if((document.getElementById("ans1").value)==null)
             {
             	 alert("Please enter Answer1.");
             	document.getElementById("ans1").focus();
              }
        	if((document.getElementById("ques2").value)==null)
        	{
        		 alert("Please enter Question2.");
             	document.getElementById("ques2").focus();
        	}
        	else{ 
        		var question2=document.getElementById("ques2").value;
    			 if(new RegExp("[^\\w\\s-]").test(question1))
    			 {
    			    alert("Special characters are not allowed in Question2.");
    			    return false;
    			 }
    			 
    			 if(document.getElementById("ques1").value==""){
    				 alert("Please enter Question1.");
    	             document.getElementById("ques1").focus();
    	             return false;
    			 }
    			 if(document.getElementById("ans1").value==""){
    				 alert("Please enter Answer1.");
    	             document.getElementById("ans1").focus();
    	             return false;
    			 }
    			 if(document.getElementById("ques2").value==""){
    				 alert("Please enter Question2.");
    	             document.getElementById("ques2").focus();
    	             return false;
    			 }
    			 if(document.getElementById("ans2").value==""){
    				 alert("Please enter Answer2.");
    	             document.getElementById("ans2").focus();
    	             return false;
    			 }
    			
    			 if((document.getElementById("ques2").value)==(document.getElementById("ques1").value))		 
    			 {
    				 alert("Please enter unique Questions.");
    				    return false;
    			 }
    			 
        		 }
            if((document.getElementById("ans2").value)==null)
            {
            	 alert("Please enter Answer2.");
             	document.getElementById("ans2").focus();
            }
    	  var ques1= trim(document.getElementById("ques1").value);
          var ques2= trim(document.getElementById("ques2").value);
          var ans1= trim(document.getElementById("ans1").value);
          var ans2= trim(document.getElementById("ans2").value);
            if((ques1.length)<10)
            {
            	 alert("Please enter minimum 10 characters value in Question1.");
             	document.getElementById("ques1").focus();
            	return false;
            }
            if((ans1.length)<2)
            {
            	 alert("Please enter minimum 2 characters value in Answer1.");
             	document.getElementById("ans1").focus();
            	return false;
            }
            if((ques2.length)<10)
            {
            	 alert("Please enter minimum 10 characters value in Question2.");
             	document.getElementById("ques2").focus();
            	return false;
            }
          
            if((ans2.length)<2)
            {
            	 alert("Please enter minimum 2 characters value in Answer2.");
             	document.getElementById("ans2").focus();
            	return false;
            }
            
        		
    		//var name = document.getElementById("name").value;
    	
    		document.getElementById("changePasswordMasterForm").action="changePasswordMaster.do?method=updateChangePasswordforLogin";
    		calMD5('oldPassword');
    		calMD5('newPassword');
    		calMD5("confirmPassword");
    		calMD5("ans1");
    		calMD5("ans2");
            document.getElementById("changePasswordMasterForm").submit();
        	   return true;
    	}
    	else if(flag == 'CP'){
        	
        document.getElementById("changePasswordMasterForm").action="changePasswordMaster.do?method=updateChangePassword";
		calMD5('oldPassword');
		calMD5('newPassword');
		calMD5("confirmPassword");
        document.getElementById("changePasswordMasterForm").submit();
    	   return true;
    	}
    	
    	 
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
