
function searchCaseMarkingAuthor(msg){
	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("searchLoanNo").value==""){
     alert(msg);
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("caseMarkingSearchForm").action="caseMarkingAuthorBehind.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("caseMarkingSearchForm").submit();
    return true;
    }
}

function insertCrCaseMarkingDetails(){
	alert('richa');
	
	var sourcepath=document.getElementById("contextPath").value;
var psize = document.getElementById("psize").value;
	
	var checkListIds="";
	var checkListIds1="";
	var checkListIds2="";
	var checkListIds3="";
	var checkListIds4="";
if(document.getElementById("lbxDealNo").value=='')
	alert('Please select Loan No.');
	gridtable
	 var msg= '';
	if(psize=="" || psize==null){
		psize=document.getElementById('gridtable').rows.length;
		psize=psize+1;
	}
	for(var i=1;i<psize;i++)
 	{
 		
	 	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null)
	 	{	 	
	 		
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
	 		
	 		if(document.getElementById("markingDate"+i).value=="")
 			{
	 			checkListIds3=checkListIds3+"$"+"|";
 			}
 			else
 			{
 				checkListIds3=checkListIds3+document.getElementById("markingDate"+i).value+"|";
 			}
	 		
	 		if(document.getElementById("agency"+i).value=="")
 			{
	 			checkListIds4=checkListIds4+"$"+"|";
 			}
 			else
 			{
 				checkListIds4=checkListIds4+document.getElementById("agency"+i).value+"|";
 			}
	 		
//				checkListIds=checkListIds+document.getElementById("otherDetails"+i).value+"|";
//	 			checkListIds1=checkListIds1+document.getElementById("remarks"+i).value+"|";
//	 			checkListIds3=checkListIds3+document.getElementById("markingDate"+i).value+"|";
//	 			checkListIds4=checkListIds4+document.getElementById("agency"+i).value+"|";
	
	 		
	 			if(document.getElementById("chk"+i).checked==true)
	 			{
	 				checkListIds2=checkListIds2+"Y"+"|";
	 			}
	 			else
	 			{
	 				checkListIds2=checkListIds2+"N"+"|";
	 			}
	 			alert("checkListIds4"+ checkListIds4);
	 	 	
	 	}
	 	
	 }
	alert("checkListIds4ssssssss"+ checkListIds4);
	document.getElementById("caseMarkingMakerForm").action=sourcepath+"/caseMarkingMakerDispatch.do?method=insertCrCaseMarkingDetails&checkListIds="+checkListIds+"&checkListIds1="+checkListIds1+"&checkListIds2="+checkListIds2+"&checkListIds3="+checkListIds3+"&checkListIds4="+checkListIds4;	
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

function checkBox_TextChanged(){
	
	
//	if(document.getElementById("legalFlag").checked==true){
//		
//		document.getElementById("legalDate").disabled =false;
//		document.getElementById("legalRemarks").disabled =false;
//		
//		}else{
//			document.getElementById("legalDate").disabled =true;
//			document.getElementById("legalRemarks").disabled =true;
//			
//		}
//if(document.getElementById("repoFlag").checked==true){
//		
//		document.getElementById("repoDate").disabled =false;
//		document.getElementById("repoRemarks").disabled =false;
//		document.getElementById("agencyButton").disabled =false;
//		document.getElementById("stockyardDetail").disabled =false;
//		
//		}else{
//			document.getElementById("repoDate").disabled =true;
//			document.getElementById("repoRemarks").disabled =true;
//			document.getElementById("agencyButton").disabled =true;
//			document.getElementById("stockyardDetail").disabled =true;
//		}
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

function saveCaseMarkingChecker(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	//shivesh
	var decision = document.getElementById("decision").value;
	//alert("decision==="+decision);
	//shivesh
	if ((document.getElementById("decision").value!="A") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	 
	}else{
		document.getElementById("caseCheckerAuthorForm").action="caseMarkingAuthorDispatch.do?method=saveCaseMarkingCheckerDetails&decision="+decision;;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("caseCheckerAuthorForm").submit();
		return true;	
	}
}


