	 function saveLoanInPoolIDEdit(){
				   
			   	 var contextPath=document.getElementById("contextPath").value;
			   	 var poolID=document.getElementById("poolID").value;	
			   	 var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value;
				 if((document.getElementById("loanAccNo").value =='')){
				   	   
			   		 alert("Please select Loan Number Lov");
			   		document.getElementById("save").removeAttribute("disabled");
			   		return false;
			   	 }else {
			   	 document.getElementById("sourcingForm").action = contextPath+"/poolIdAddEditProcessAction.do?method=saveLoanInPoolIDEdit&poolID="+poolID+"&lbxLoanNoHID="+lbxLoanNoHID;
			   
			   	 document.getElementById("sourcingForm").submit();
			   	 
			   	 return true;
			   	 }
			   }
	
	 
	   function saveLoanInPoolID(){
		    
		   	 var contextPath=document.getElementById("contextPath").value;
		   	 var poolID=document.getElementById("poolID").value;		   
			 if((document.getElementById("loanAccNo").value =='')){
			   	   
		   		 alert("Please select Loan Number Lov");
		   		document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   	 }else {
		   	 document.getElementById("sourcingForm").action = contextPath+"/poolIdMakerProcessAction.do?method=saveLoanInPoolID&poolID="+poolID;
		   	 document.getElementById("sourcingForm").submit();
		   	 
		   	 return true;
		   	 }
		   }