
function searchCaseMarkingMaker(msg){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchLoanNo").value=="" && document.getElementById("statusCase").value==''){
     alert(msg);
     DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}
	else{
	document.getElementById("caseMarkingSearchForm").action="caseMarkingMakerBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingSearchForm").submit();
    return true;
    }
}


function forwardCaseMarking(msg){

	DisButClass.prototype.DisButMethod();
	if((!document.getElementById("searchLoanNo").value=="")&&(!document.getElementById("canForward").value=="")){
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("caseMarkingMakerForm").action=sourcepath+"/caseMarkingMakerDispatch.do?method=forwardCaseMarkingMaker";

	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingMakerForm").submit();
    return true;
	}
	else
	{
		alert("You can't move without saving .");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
    }


function insertCrCaseMarkingDetails(){

	var sourcepath=document.getElementById("contextPath").value;

var caseStatusList=document.getElementsByName("caseStatus");

	var checkListIds="";
	var checkListIds1="";
	var checkListIds2="";
	var checkListIds3="";
	var checkListIds4="";
	var checkListIds5="";
	var bussinessDate=document.getElementById("businessdate").value;
	var chkFlag='N';
	if(document.getElementById("searchLoanNo").value==""){
	     alert('Please select Loan No. First');
	     DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
	}

	 var msg= '';

	for(var i=1;i<=caseStatusList.length;i++)
 	{
	
		var chked = document.getElementById("chk"+i).checked;
	 	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null)
	 	{	 	
	 	
	 	
	 		if(document.getElementById("chk"+i).checked)
 			{
	 			chkFlag='Y'	;
 			}
	 		
	 		if(document.getElementById("otherDetails"+i).value=="")
 			{
	 			checkListIds=checkListIds+"$"+"|";
 			}
 			else
 			{
 				checkListIds=checkListIds+document.getElementById("otherDetails"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("remarks"+i).value=="")
 			{
	 			checkListIds1=checkListIds1+"$"+"|";
 			}
 			else
 			{
 				checkListIds1=checkListIds1+document.getElementById("remarks"+i).value+"|";
 			}
	 		
	 		if((document.getElementById("markingDate"+i).value==""))
 			{
	 			if((document.getElementById("markingDate"+i).value=='') && (document.getElementById('chk'+i).checked))
	 			{
	 				alert('Please Mark the date for the checked case');
	 			DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 			}
	 			else
	 				
	 			checkListIds3=checkListIds3+"$"+"|";
 			}
 			else
 			{
 		//shivesh
 				/*if(!(document.getElementById("markingDate"+i).value=="")&&!(document.getElementById("markingDate"+i).value==bussinessDate))
 					{alert("Marking Date cannot be greater than or less than Bussiness Date");
 				DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 					}
 				else*/
 		//shivesh
 				checkListIds3=checkListIds3+document.getElementById("markingDate"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("agency"+i).value=="")
 			{
	 			if((document.getElementById("caseStatus"+i).value=='REPO') && (document.getElementById('chk'+i).checked))
	 			{
	 				alert('Agency is required');
	 			DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 			}
	 			else
	 			checkListIds4=checkListIds4+"$"+"|";
	 			
 			}
 			else
 			{
 				checkListIds4=checkListIds4+document.getElementById("agency"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("caseStatus"+i).value=="")
 			{
	 			checkListIds5=checkListIds5+"$"+"|";
 			}
 			else
 			{
 				checkListIds5=checkListIds5+document.getElementById("caseStatus"+i).value+"|";
 			}
 		
	 			if(document.getElementById("chk"+i).checked==true)
	 			{
	 				checkListIds2=checkListIds2+"Y"+"|";
	 			}
	 			else
	 			{
	 				checkListIds2=checkListIds2+"N"+"|";
	 				
	 			}
	 			
	 	 	
	 	}
		if((chked==false)&&(document.getElementById("otherDetails"+i).value!==""||document.getElementById("remarks"+i).value!==""))
	 		{
	 		alert("Please select the checkbox for the filled details ");	
	 		 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
 			
	 		}

	 }
	
	//shivesh
	var  npa = '';
	var  nonnpa = '';
	for(var j=1;j<=caseStatusList.length;j++)
 	{
	if((document.getElementById("caseStatus"+j).value=='RESTRUC') && (document.getElementById('chk'+j).checked)) {
		npa = 'RESTRUC';
		}
		if((document.getElementById("caseStatus"+j).value=='NONRESTRUC') && (document.getElementById('chk'+j).checked)) {
		nonnpa = 'NONRESTRUC';
		}
 	}
	if(npa!= '' && nonnpa!= ''){
	alert('Please select Only one in NPA or NON NPA');
		DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}
	//shivesh
	
	if(chkFlag=='N')
	{
		alert('Please select atleast one case status');
		DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}

	document.getElementById("caseMarkingMakerForm").action=sourcepath+"/caseMarkingMakerDispatch.do?method=insertCrCaseMarkingDetails&checkListIds="+escape(checkListIds)+"&checkListIds1="+escape(checkListIds1)+"&checkListIds2="+checkListIds2+"&checkListIds3="+checkListIds3+"&checkListIds4="+escape(checkListIds4)+"&checkListIds5="+checkListIds5;	
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingMakerForm").submit();
	return true;
	}




function updateCrCaseMarkingDetailsInsertCrLoanDtl(){

	//markCheckBox
	DisButClass.prototype.DisButMethod();
	var checkedChek=document.getElementsByName("markCheckBoxArr");
	var id="";
	for(var i=0;i<checkedChek.length;i++){
		if(checkedChek[i].checked==true){
			id=id+i+"|";
			
		}
	}

	if(checkedChek.length>0 && id!="" )
	{
		document.getElementById("chkHidden").value=id;
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("caseMarkingAuthorForm").action=sourcepath+"/caseMarkingAuthorDispatch.do?method=updateCrCaseMarkingDetailsInsertCrLoanDtl";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("caseMarkingAuthorForm").submit();
		return true;
	}else{
		
	   	alert("Please Select atleast one!!!");
	   	DisButClass.prototype.EnbButMethod();
	    return false;		
	}
}

function newAddCaseMarking(){
	
	
	DisButClass.prototype.DisButMethod();

	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("caseMarkingSearchForm").action=sourcepath+"/caseMarkingMakerDispatch.do?method=newAddCaseMarking";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingSearchForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();

	return false;

}

function disable()
{
	
	var caseStatusList=document.getElementsByName("caseStatus");
	for(var i=0; i< caseStatusList.length ; i++) 
	{		
		var val=caseStatusList[i].value;
		var agencyButton="agencyButton"+(i+1);		
		if(caseStatusList[i].value=='REPO')
			
			document.getElementById(agencyButton).style.display="";
		else
			document.getElementById(agencyButton).style.display="none";
	}	
}


function allChecked(){ 
	
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	var c = document.getElementById("allchkd").checked;
	var psize=document.getElementById("psize").value;
	
	if(psize==""){
		psize=lastElement;
	}
	if(c==true){
		for(i=1;i<psize;i++)
		{
			var ch1=document.getElementById('chk'+i);
			if(ch1!=undefined||ch1!=null){
				document.getElementById('chk'+i).checked=true;
				}		
	}
	}else{
		for(i=1;i<psize;i++)
		{
		var ch2=document.getElementById('chk'+i);
			if(ch2!=undefined||ch2!=null){
				document.getElementById('chk'+i).checked=false;
			}
		}
	}
}


function updateCrCaseMarkingDetails(){

	var sourcepath=document.getElementById("contextPath").value;

var caseStatusList=document.getElementsByName("caseStatus");

	var checkListIds="";
	var checkListIds1="";
	var checkListIds2="";
	var checkListIds3="";
	var checkListIds4="";
	var checkListIds5="";
	var checkListIds6="";				  
	var bussinessDate=document.getElementById("businessdate").value;
	var chkFlag='N';
	if(document.getElementById("searchLoanNo").value==""){
	     alert('Please select Loan No. First');
	     DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
	}

	 var msg= '';

 
	for(var i=1;i<=caseStatusList.length;i++)
 	{
	
		var chked = document.getElementById("chk"+i).checked;
   
	 	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null)
	 	{	 	
	 	
	 	
	 		if(document.getElementById("chk"+i).checked)
 			{
	 			chkFlag='Y'	;
 			}
	 		
	 		if(document.getElementById("otherDetails"+i).value=="")
 			{
	 			checkListIds=checkListIds+"$"+"|";
 			}
 			else
 			{
 				checkListIds=checkListIds+document.getElementById("otherDetails"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("remarks"+i).value=="")
 			{
	 			checkListIds1=checkListIds1+"$"+"|";
 			}
 			else
 			{
 				checkListIds1=checkListIds1+document.getElementById("remarks"+i).value+"|";
 			}
	 		
   
	 		if((document.getElementById("markingDate"+i).value==""))
 			{
	 
	 
	 			if((document.getElementById("markingDate"+i).value=='') && (document.getElementById('chk'+i).checked))
	 			{
	 				alert('Please Mark the date for the checked case');
	 			DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 			}
	 			else
	 				
	   
	 			checkListIds3=checkListIds3+"$"+"|";
 			}
	 
 			else
 			{
 		//shivesh
 				/*if(!(document.getElementById("markingDate"+i).value=="")&&!(document.getElementById("markingDate"+i).value==bussinessDate))
 					{alert("Marking Date cannot be greater than or less than Bussiness Date");
 				DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 					}
 				else*/
 		//shivesh
 				checkListIds3=checkListIds3+document.getElementById("markingDate"+i).value+"|";
 			}
	 		
	 
																																					
	  
									 
										   
																																								 
				   
	  
	 
	 		if(document.getElementById("agency"+i).value=="")
 			{
	 			if((document.getElementById("caseStatus"+i).value=='REPO') && (document.getElementById('chk'+i).checked))
	 			{
	 				alert('Agency is required');
	 			DisButClass.prototype.EnbButMethod();
				 document.getElementById("save").removeAttribute("disabled","true");
				 return false;
 			}
		
																																											 
																				 
										   
																											 
				   
		
	 			else
	 			checkListIds4=checkListIds4+"$"+"|";
	 			
 			}
 			else
 			{
 				checkListIds4=checkListIds4+document.getElementById("agency"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("caseStatus"+i).value=="")
 			{
	 			checkListIds5=checkListIds5+"$"+"|";
 			}
 			else
 			{
 				checkListIds5=checkListIds5+document.getElementById("caseStatus"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("caseId"+i).value=="")
 			{
	 			checkListIds6=checkListIds6+"$"+"|";
 			}
 			else
 			{
 				checkListIds6=checkListIds6+document.getElementById("caseId"+i).value+"|";
 			}
	 		 		
	 			if(document.getElementById("chk"+i).checked==true)
	 			{
	 				checkListIds2=checkListIds2+"Y"+"|";
	 			}
	 			else
	 			{
	 				checkListIds2=checkListIds2+"N"+"|";
	 				
	 			}
	 			
	 	 	
	 	}
   
   
		if((chked==false)&&(document.getElementById("otherDetails"+i).value!==""||document.getElementById("remarks"+i).value!==""))
	 		{
	 		alert("Please select the checkbox for the filled details ");	
										 
																																						  
				
   
	
																											 
   
												  
	 		 DisButClass.prototype.EnbButMethod();
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
 			
	 		}

	 }
	//shivesh
	var  npa = '';
	var  nonnpa = '';
	for(var j=1;j<=caseStatusList.length;j++)
 	{
	if((document.getElementById("caseStatus"+j).value=='NPA RESTRUCTURE') && (document.getElementById('chk'+j).checked)) {
		npa = 'NPA RESTRUCTURE';
		}
		if((document.getElementById("caseStatus"+j).value=='NON NPA RESTRUCTURE') && (document.getElementById('chk'+j).checked)) {
		nonnpa = 'NON NPA RESTRUCTURE';
		}
 	}
	if(npa!= '' && nonnpa!= ''){
	alert('Please select Only one in NPA or NON NPA');
		DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}
	//shivesh
	if(chkFlag=='N')
	{
		alert('Please select atleast one case status');
		DisButClass.prototype.EnbButMethod();
		 document.getElementById("save").removeAttribute("disabled","true");
		 return false;
	}

	document.getElementById("caseMarkingMakerForm").action=sourcepath+"/caseMarkingMakerDispatch.do?method=updateCrCaseMarkingDetails&checkListIds="+escape(checkListIds)+"&checkListIds1="+escape(checkListIds1)+"&checkListIds2="+checkListIds2+"&checkListIds3="+checkListIds3+"&checkListIds4="+escape(checkListIds4)+"&checkListIds5="+checkListIds5+"&checkListIds6="+checkListIds6;		
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingMakerForm").submit();
	return true;
	}

function deleteCaseMarking(){
		
  	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var caseMarkingLoanId=document.getElementById("lbxDealNo").value;
	var statusCase=document.getElementById("statusCase").value;
	// alert("loanId......"+loanId);
	if(caseMarkingLoanId=='')
	{
		alert("Loan No is required. ");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	agree=confirm("Are you sure,You want to delete this case marking.Do you want to continue?");
	if (!(agree))
	{	
    	document.getElementById("delete").removeAttribute("disabled","true");
    	DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		
		location.href=sourcepath+"/caseMarkingMakerDispatch.do?method=deleteCaseMarking&caseMarkingLoanId="+caseMarkingLoanId+"&statusCase="+statusCase;
		document.getElementById("processingImage").style.display = '';
	
		return true;
      }
	
}

function lovHideShow()
{
	
	var statusCase=document.getElementById("statusCase").value;
	document.getElementById("searchLoanNo").value="";
	document.getElementById("lbxDealNo").value="";
	document.getElementById("searchCustomerName").value="";
		if(statusCase=='A')
		{			
			document.getElementById("dealButton1").style.display='inline';
			document.getElementById("dealButton").style.display='none';
		}
		else
		{
			document.getElementById("dealButton1").style.display='none';
			document.getElementById("dealButton").style.display='';
		}
}