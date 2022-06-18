var global=0;
var effDate='Y';
function searchLoanClosure(type,status)
{
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("loanAc").value=="" && document.getElementById("customerName").value=="" && document.getElementById("reportingToUserId").value=="")
	{
		alert("Please Enter Loan Account No. or Customer Name");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
		document.getElementById("loanAc").focus;
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		//alert("Type of Closure: "+type);
	    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=searchLoanData&type="+type+"&status="+status;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("closureSearchForm").submit();
	}
		
}

function openNewClosure(type)
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("closureSearchForm").action = contextPath+"/closureInitiation.do?method=openNewClosure&type="+type;
    document.getElementById("processingImage").style.display = '';
    document.getElementById("closureSearchForm").submit();
}

function parent_disable() {
	if( window.child && ! window.child.closed)
		 window.child.focus();
	}

function getDetails()
{
	
	//alert("get Details");
	var loanId = document.getElementById("lbxLoanNoHID").value;	
	var initiationDate = document.getElementById("initDate").value;	
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(initiationDate,formatD.substring(2, 3)); 
	//var calledFrom='ECM';
	
	if(loanId == "")
	{
		alert("Please Enter Loan Account First !");
		document.getElementById("loanAc").focus();
		return false;
	}
	
	else
	{
		global=1;
		//effDate='Y';
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=getDetails&loanId="+loanId;		 
		 var data = "lbxLoanNoHID=" + loanId;
		 sendDuesManualIntCalcid(address, data); 
		 return true;
	}
}
function sendDuesManualIntCalcid(address, data) {
	//alert("in sendClosureid ");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultDuesManualIntCalc(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultDuesManualIntCalc(request){
	//alert("In result Dues Refund");

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		  
		var s1 = str.split("$:");		
		document.getElementById('interestTillDate').value=trim(s1[0]);		
     window.close();
	}
	
}

function generateAdvice()
{
	DisButClass.prototype.DisButMethod();
	var initiationDate = document.getElementById("initDate").value;  
	var loanId = document.getElementById("lbxLoanNoHID").value;	
	var interestTillDate = document.getElementById("interestTillDate").value;		
    var calledFrom='MIC';
    if(loanId == "" )
	{
		alert("Please Enter Loan Account First !");
		document.getElementById("loanAc").focus();
		DisButClass.prototype.EnbButMethod();
		document.getElementById("getDetail").removeAttribute("disabled","true");
		document.getElementById("generate").removeAttribute("disabled","true");
		return false;
	}
    if(loanId != "" && interestTillDate=="")
    {
    	alert("Please Get Interest First !");
		document.getElementById("loanAc").focus();
		DisButClass.prototype.EnbButMethod();
		document.getElementById("generate").removeAttribute("disabled","true");
		return false;    	
    }
    else {
    	if (confirm("Do you want to Generate Advice ?")){
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("manualIntCalcForm").action = contextPath+"/manualInterestCalc.do?method=generateAdvice";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualIntCalcForm").submit();
		return true;
		}
    	else
    	{
    		DisButClass.prototype.EnbButMethod();
			return false;
    	}
    
	}
	
}

function viewPayable(alert1) 
{	
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		
		var loanId=document.getElementById('lbxLoanNoHID').value;		
		var basePath=document.getElementById("contextPath").value;
		otherWindows[curWin++] =window.open(basePath+'/viewPayableEarlyClousreAction.do?&loanId='+loanId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		return true;
	}
}


function viewReceivable(alert1) 
{	
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert(alert1);	
		document.getElementById("recieveButton").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		
		var loanId=document.getElementById('lbxLoanNoHID').value;	
		//alert("loanId",loanId);
		var basePath=document.getElementById("contextPath").value;
		otherWindows[curWin++] =window.open(basePath+'/viewReceivableEarlyClousreAction.do?&loanId='+loanId,'viewReceivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
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

function removeComma(id)
{
    var str = id;
    //alert(id);
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    var amount = parseFloat(stri);
    //alert(amount);
	return amount;
}