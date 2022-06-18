function searchRepoConfirmation(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if((document.getElementById("lbxLoanIdSearch").value == "" || document.getElementById("loanNo").value == "" ) && document.getElementById("searchRepoId").value=="" )
	{
     alert(val);
     document.getElementById("loanNoButton").focus();
     DisButClass.prototype.EnbButMethod();
	return false; 
	}
	else{
	document.getElementById("repoConfirmationForm").action=sourcepath+"/RepoConfirmationDispatch.do?method=searchRepoConfirmation";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoConfirmationForm").submit();
	return true;
	}
}

function saveRepoConfirmation(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments");
	if ((document.getElementById("decision").value!="Y") && (trim(comments.value)=='') ){
		 alert("* comments is required");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 return false;	 
	}else{
		document.getElementById("repoConfirmationForm").action="RepoConfirmationDispatch.do?method=saveRepoConfirmationDetail";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("repoConfirmationForm").submit();
		return true;	
	}
}

function hideAsterik(value){
	  
	  if(value!='Y')
	  {
		  document.getElementById("hideAsterik").style.display ='';
	  }
	  else
	  {
		  document.getElementById("hideAsterik").style.display ='none';
	  }
		  
}

	
	
