function fnReportSearch(val,val1,val2){ 
	    if(document.getElementById("roleId").value==''&& document.getElementById("moduleId").value=='')
		{
			alert(val);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("roleId").value=='')
		{
			alert(val1);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	    else if(document.getElementById("moduleId").value=='')
		{
			alert(val2);
			document.getElementById("search").removeAttribute("disabled","true");
			return false;
		}
	   
	    var contextPath = document.getElementById("contextPath").value;
	    document.getElementById("reportFunctioAccessForm").action=contextPath+"/reportFunctionAccess.do?method=searchReportFunctionAccess";
		document.getElementById("reportFunctioAccessForm").submit();
	}

function saveReportAccess()
{

	
	var ch=document.getElementsByName("chkCases");
	var reportCheckedList="";
	var reportUnCheckedList="";
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
			reportCheckedList=reportCheckedList+","+ch[i].value;
		else 
			reportUnCheckedList=reportUnCheckedList+","+ch[i].value;
	}
	reportCheckedList=reportCheckedList.substring(1);
	reportUnCheckedList=reportUnCheckedList.substring(1);
	var contextPath= document.getElementById("contextPath").value;
	document.getElementById("reportFunctioAccessForm").action=contextPath+"/reportFunctionAccess.do?method=saveReportList&reportUnCheckedList="+reportUnCheckedList+"&reportCheckedList="+reportCheckedList;
	document.getElementById("reportFunctioAccessForm").submit();	
}
