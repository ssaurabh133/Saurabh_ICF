function generateRepossessedReport(){
		
	document.getElementById("repossessedMisForm").action="repossessedMISDispatchAction.do?method=generateRepossessedMis";
	document.getElementById("repossessedMisForm").submit();
	
}

function generateRepoRjectCaseReport(){
	
	document.getElementById("repoRejectCaseMisForm").action="repossessedRejectCaseMISDispatchAction.do?method=generateRepoRejectedCaseMis";
	document.getElementById("repoRejectCaseMisForm").submit();
	
}



