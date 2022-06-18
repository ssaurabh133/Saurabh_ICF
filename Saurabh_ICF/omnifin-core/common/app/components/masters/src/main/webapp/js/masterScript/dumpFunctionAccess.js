function fnDumpSearch(val,val1,val2){ 
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
	    document.getElementById("dumpFunctioAccessForm").action=contextPath+"/dumpFunctionAccess.do?method=searchDumpFunctionAccess";
		document.getElementById("dumpFunctioAccessForm").submit();
	}

function saveDumpAccess()
{

	var ch=document.getElementsByName("chkCases");
	var dumpCheckedList="";
	var dumpUnCheckedList="";
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
			dumpCheckedList=dumpCheckedList+","+ch[i].value;
		else 
			dumpUnCheckedList=dumpUnCheckedList+","+ch[i].value;
	}
	dumpCheckedList=dumpCheckedList.substring(1);
	dumpUnCheckedList=dumpUnCheckedList.substring(1);
	var contextPath= document.getElementById("contextPath").value;
	document.getElementById("dumpFunctioAccessForm").action=contextPath+"/dumpFunctionAccess.do?method=saveDumpList&dumpUnCheckedList="+dumpUnCheckedList+"&dumpCheckedList="+dumpCheckedList;
	document.getElementById("dumpFunctioAccessForm").submit();	
}

