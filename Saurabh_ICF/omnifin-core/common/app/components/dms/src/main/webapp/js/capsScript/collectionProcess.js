function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}
function refreshClassificationProcess(timeoutPeriod)
{	
setTimeout("callBodClassificationProcess();",timeoutPeriod);
}

function callBodClassificationProcess()
{
		pathRefresh();
		refreshClassificationProcess(5000);
}
function pathRefresh()
{
    var refreshFlag=document.getElementById("refreshFlag").value;
    //alert("refreshFlag: "+refreshFlag);
	var contextPath=document.getElementById("contextPath").value;
	var address = contextPath+"/classificationProcessAction.do?method=refreshGrid&refreshFlag="+refreshFlag;	
	send_Process(address);
	return true;
		
}
function send_Process(address) {
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_Process(request);
	};

	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(null);
}
function result_Process(request){
   // alert(request)
	if ((request.readyState == 4) && (request.status == 200))
	{
		var str = request.responseText;
		document.getElementById('revisedcontainer').innerHTML =str;
	
	    }
}
function classificationStartprocess(){
	  var contextPath=document.getElementById("contextPath").value;
	  document.getElementById("classificationForm").action = contextPath+"/classificationProcessAction.do?method=startClassificationProcess";
	  document.getElementById("classificationForm").submit();
	  return true;
}