function allChecked()
{
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chkCases');
	var zx=0;
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
}

function fnSearch()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var dealNo = document.getElementById("dealNo").value;
	var rm = document.getElementById("rm").value;
	var ro = document.getElementById("ro").value;
	var maker = document.getElementById("maker").value;
	if(dealNo !='' || rm !=''|| ro !=''|| maker !=''){
		document.getElementById("rmChangeForm").action="rmChangeDispatchAction.do?method=searchRmChange";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rmChangeForm").submit();
		return true;
	}
	else{
		alert("* Please Select atleast one field");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveRmData()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var allocateRM = document.getElementById("relationshipManager").value;
	var allocateMaker = document.getElementById("showUserDescSearch").value;

	if(check() == 0)
	{
		alert("Please check atleast one record");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(check() == 1 && allocateRM == "" && allocateMaker=="")
	{
		alert("Please allocate RM");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		document.getElementById("rmChangeForm").action="rmChangeDispatchAction.do?method=saveRmChangeDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rmChangeForm").submit();
		return true;
	}
}
function check()
{
	var ch=document.getElementsByName('chkCases');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	    {
	    	if(ch[zx].checked==false)
	    	{
	    		flag=0;
	    	}
	    	else
			{
	    		flag=1;
	    		break;
			}
	    	zx++;
	    }
	    return flag;
}
//function forwardRm()
//{
//	DisButClass.prototype.DisButMethod();
//	if(check() == 0)
//	{
//		alert("Please check atleast one record");
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
//	else
//	{
//		document.getElementById("rmChangeForm").action="rmChangeDispatchAction.do?method=forwardRmData";
//		document.getElementById("processingImage").style.display = '';
//		document.getElementById("rmChangeForm").submit();
//		return true;
//	}
//}

function fnAuthorSearch()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	var rm = document.getElementById("rm").value;
	var dealNo = document.getElementById("dealNo").value;

	if(dealNo !='' || rm !=''){
		document.getElementById("rmChangeForm").action="rmChangeDispatchAction.do?method=searchRmAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rmChangeForm").submit();
		return true;
	}
	else{
		alert("* Please Select atleast one field");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function rmAuthorRemarks()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
	
	if(document.getElementById("decison").value=="" || document.getElementById("textArea").value=="")
	{
	var a="";
	if(document.getElementById("decison").value==""){
		a="* Decision is required \n";
	}
	if(document.getElementById("textArea").value==""){
		a +="* Author Remarks is required \n";
	}
	alert(a);
	document.getElementById("save").removeAttribute("disabled","true");
	DisButClass.prototype.EnbButMethod();
	return false;
	}else{
		document.getElementById("rmChangeForm").action=contextPath+"/rmChangeDispatchAction.do?method=saveRmAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("rmChangeForm").submit();
		return true;
	}
}

function showPopUp(val) {
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
	newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
	if (window.focus) {newwindow.focus()}
	return false;
}

//function rmForwardBeforeSave()
//{
//	DisButClass.prototype.DisButMethod();
//	alert("Please save before forward.");
//	DisButClass.prototype.EnbButMethod();
//	return false;
//}





