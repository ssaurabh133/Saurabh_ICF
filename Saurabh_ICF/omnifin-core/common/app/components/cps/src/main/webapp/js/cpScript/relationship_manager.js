

function fetchBranch(branch){
	
	var cPath=document.getElementById("contextPath").value;
	
 if (branch!= '') 
 {
	
	
	var address = cPath+"/relationalManagerAjaxAction.do?method=displayRelationalManager";
	
	var data = "branch=" + branch;
	sendFetchedManagerName(address, data);
	return true;
 }
 else
 {
	 alert("Please Select List");	
	 return false;
 }
}
function sendFetchedManagerName(address, data) {
	
var request = getRequestObject();
request.onreadystatechange = function()
{
	
	resultFetchedManagerName(request);
};

request.open("POST", address, true);
request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
request.send(data);

}
function resultFetchedManagerName(request){

if ((request.readyState == 4) && (request.status == 200)) {
	
	var str = request.responseText;


   	document.getElementById('relationshipManagerDetails').innerHTML = str;

}
}


