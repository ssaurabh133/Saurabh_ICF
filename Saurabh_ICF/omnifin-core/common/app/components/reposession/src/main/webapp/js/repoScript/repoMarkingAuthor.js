
function onSaveRepoMarkingAuthorCase(a){
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	if ((document.getElementById("decision").value!="A") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	
	 
	 }else {

	document.getElementById("repoMarkingAuthorForm").action="repoMarkingAuthorDispatch.do?method=saveRepoMarkingAuthorCaseDetails";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoMarkingAuthorForm").submit();
	return true;
	
	 }
}


function fnSearchRepoMarkingAuthor(val){
	DisButClass.prototype.DisButMethod();
	document.getElementById("repoMarkingMakerForm").action="repoMarkingAuthorDispatch.do?method=searchRepoMarkingAuthor";
	if(document.getElementById("lbxLoanId").value=='' && document.getElementById("lbxProductSearchID").value=='')
	{
		alert(val);
		document.getElementById("loanNoButton").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}else{
		
		document.getElementById("processingImage").style.display = '';
	    document.getElementById("repoMarkingMakerForm").submit();
	return true;
	}
}

function hideAsterik(value){
	  
	  if(value!='A')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}


