
			function checkValidation()
			{
				 if((document.getElementById("loanno").value =='') 
			    		 && (document.getElementById("customername").value =='') 
			    		 && (document.getElementById("product").value=='') 
			    		 && (document.getElementById("dpd1").value=='') 
			    		 && (document.getElementById("dpd2").value =='') 
			    		 && (document.getElementById("queue").value=='')
			    		 && (document.getElementById("pos1").value =='') 
			    		 && (document.getElementById("pos2").value=='')
			    		
			    	
			    		 && (document.getElementById("custype").value=='')
			    		 
			    		 && (document.getElementById("custcategory").value=='')
		    		 	 //&& (document.getElementById("fromdate").value=='')
		    		 	 && ((document.getElementById("branch").value=='Specific') && (document.getElementById("lbxBranchId").value=='')))
		    		 	 //&& (document.getElementById("todate").value==''))
						 { 
			    	alert("Please select at least one criteria");
				return false;
			 }else {
					//var assignFrom=document.getElementById("fromdate").value;
					//var assignto=document.getElementById("todate").value;
					//var formatD=document.getElementById("formatD").value;
					var branch=document.getElementById("branch").value;
					var branch_name=document.getElementById("lbxBranchId").value;	
					if(branch=="Specific" && branch_name=="")
					{	
						alert("Branch Name is Required");
						document.getElementById("dealButton").focus();
						return false;
					}	
					//assignFrom=getDateObject(assignFrom,formatD.substring(2, 3));
					//assignto=getDateObject(assignto,formatD.substring(2, 3));
					  
					 if(document.getElementById("dpd1").value=='.'){
						alert("DPD >= is not in correct Format!");
						document.getElementById("show").removeAttribute("disabled");
						return false;
					}else if(document.getElementById("dpd2").value=='.'){
						alert("DPD <= is not in correct Format!");
						document.getElementById("show").removeAttribute("disabled");
						return false;
					}else if(document.getElementById("pos1").value=='.'){
						alert("Over Due Amount <= is not in correct Format!");
						document.getElementById("show").removeAttribute("disabled");
						return false;
					}else if(document.getElementById("pos2").value=='.'){
						alert("Over Due Amount >= is not in correct Format!");
						document.getElementById("show").removeAttribute("disabled");
						return false;
					}else if(document.getElementById("balanceprincipal").value=='.'){
						alert("Balance Principal is not in correct Format!");
						document.getElementById("show").removeAttribute("disabled");
						return false;
					}
					//else if((document.getElementById("fromdate").value !="")&&(document.getElementById("todate").value !="")){
					//	if(assignFrom>assignto){
					//	alert(" Assign to date  must be greater than Assign from date ");
					 //   document.getElementById("show").removeAttribute("disabled");
					//	return false;
					 // }
					//	else {
					//		document.getElementById("unallocatedForm").submit();	
					//			Window.location.reload();
					//		}
					//}
					else {
					document.getElementById("unallocatedForm").submit();	
						Window.location.reload();
					}
			}
}
			function getDateObject(dateString,dateSeperator)
		    {
		    		var dateParts = dateString.split(dateSeperator);
					var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
					return dateObject;

		    }
			
			
			function hideLovofBranch()
			{
				var lovid=document.getElementById("branch").value;
				if(lovid=='All')
				{
					document.getElementById("dealButton").style.visibility="hidden";
					document.getElementById("branchid").value="";
					document.getElementById("lbxBranchId").value="";
					//document.getElementById("r10").style.display="none";	
					//document.getElementById("r11").style.display="";	
				}
				else
				{					
					document.getElementById("dealButton").style.visibility="visible";
					//document.getElementById("r10").style.display="";
					//document.getElementById("r11").style.display="none";
				}
					
			}