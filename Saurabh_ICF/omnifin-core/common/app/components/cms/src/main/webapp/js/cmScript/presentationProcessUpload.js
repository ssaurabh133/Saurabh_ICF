 // neerajgenerateReport()
	   function generateProcess()
	   {
		   var presentationDate=document.getElementById("presentationDate").value;
		   var bank=document.getElementById("bank").value;
		   var branch=document.getElementById("branch").value;
		   var bankAccount=document.getElementById("bankAccount").value;
		   var msg1="";
		   var msg2="";
		   var msg3="";
		   var msg4="";
		   if(presentationDate =="")
			   msg1="*Presentation Date is required \n";
		   if(bank =="")
			   msg2="*Deposit Bank ID is required \n";
		   if(branch =="")
			   msg3="*Deposit Branch ID is required \n";
		   if(bankAccount =="")
			   msg4="*Deposit Bank Account is required \n";
		  		   
		   if(presentationDate =="" ||bank =="" || branch =="" || bankAccount =="")
		   {
			   alert(msg1+""+msg2+""+msg3+""+msg4);
			   document.getElementById("save").removeAttribute("disabled");
		   		return false;
		   }
		   if(document.getElementById("txnfile").value=="NoFile")
		   {
			   alert("Please upload file to start process.");
			   document.getElementById("docFile").focus(); 
			   document.getElementById("save").removeAttribute("disabled");
			   return false;
		   }
		   var contextPath = document.getElementById("contextPath").value;
		   document.getElementById("sourcingForm").action = contextPath+"/presentaionProcessMain.do?method=generateProcess";
		   document.getElementById("sourcingForm").submit();
		   return true;
	   }
function  generateReport()
{
   var contextPath = document.getElementById("contextPath").value;
   document.getElementById("sourcingForm").action = contextPath+"/presentaionProcessMain.do?method=generateReport";
   document.getElementById("sourcingForm").submit();
}