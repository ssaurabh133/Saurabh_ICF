function saveAndGenerateReport()
{

	   var lbxDealNoHID=document.getElementById("lbxDealNoHID").value;
	   var marginMoneyRemarks=document.getElementById("marginMoneyRemarks").value;
	   if(lbxDealNoHID=='')
	   {
		   alert("Deal Number is Required");
		   return false;
	   }
	
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("loanSanctionLetterForm").action=sourcepath+"/dealSanctionLetterCP.do?method=reportSaveGenerateAction&lbxDealNoHID="+lbxDealNoHID;;
		document.getElementById("loanSanctionLetterForm").submit();
		Window.location.reload();
		return true;
			 
}

function checkRate(val)
{
	var rate = document.getElementById(val).value;
if(rate=='')
	{
		rate=0;	
	}
	    var intRate = parseFloat(rate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }

	        else
	           {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById(val).value="";
		        return false;
	         }
}