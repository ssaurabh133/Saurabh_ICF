function userAccessReport()
		{
		var userId=document.getElementById("userId").value;
		var Date=document.getElementById("BusinessDate").value;
				 if(Date=="")
					{	
						alert("Business Date is Required");
						return false;
					}
		 	document.getElementById("userAccessLog").submit(); 
 			Window.location.reload();
 			return false;
		}


function userFunctionAccessReport()
{
var userId=document.getElementById("userId").value;
if(userId=="")
{
alert("User Name is Required");
				return false;
}

 var roleDescription=document.getElementById("roleDescription").value;
 var moduleDesc=document.getElementById("moduleDesc").value;
 

		document.getElementById("userFunctionAccess").submit(); 
		Window.location.reload();
}


function userDetailsReport()
{
 	document.getElementById("userDetailsForm").submit(); 
		Window.location.reload();
		return false;
}

function viewReport(alert1) 
{	
	if (document.getElementById("generateBankingDate").value=="")
	{
		alert(alert1);	
		return false;
	}
	else
	{
		
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("generateReport").action = contextPath+"/generateReport.do?method=generateReport";
	    document.getElementById("generateReport").submit();
   }
}

/*Added by Pranaya - For Interest Working Report*/
function viewInterestWorking()
{
	//dateAlert
	if (document.getElementById("reportName").value=='')
	{
		alert("Plese select report name");	
		return false;
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("interestWorking").action = contextPath+"/interestWorking.do?method=generateInterestWorking";
	    document.getElementById("interestWorking").submit();
   }
}
/*End by Pranaya*/


/*Virender Start- For Cersai Report*/
function CersaiReportUpload() {
	var fileName = document.getElementById("fileName");
	if (fileName) {
		fileName = fileName.value;
		if (fileName == '') {
			alert("Please Select Upload First!");
			return false;
		} else {
			var contextPath = document.getElementById("contextPath").value;
			document.getElementById("CersaiReportUpld").action = contextPath
					+ "/CersaiReportUpld.do?method=cersaiUpload&fileName="
					+ fileName;

			document.getElementById("CersaiReportUpld").submit();
		}
	}
}

function CersaiReportDownload()
{
	var fileName=document.getElementById("fileName");
	if(fileName){
		fileName = fileName.value;
		if(fileName == ''){
			alert("Please Select Upload First!");	
			return false;
		}
		else{
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("CersaiReportUpld").action = contextPath+"/CersaiReportUpld.do?method=cersaiDownload&fileName="+fileName;
		document.getElementById("CersaiReportUpld").submit();
		}
	}
}