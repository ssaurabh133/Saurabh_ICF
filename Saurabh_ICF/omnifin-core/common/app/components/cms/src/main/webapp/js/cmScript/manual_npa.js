  
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
function new_manual_npa()
{
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("manualNpaMovementForm").action = contextPath+"/manualNpaMovementMakerAdd.do?method=openNewManualNpa";
    document.getElementById("manualNpaMovementForm").submit();
}


function search_manual_npa()
{
	//alert(type);
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("searchCName").value;
	var contextPath=document.getElementById("contextPath").value;
	
	if((document.getElementById("loanNo").value!='') || (document.getElementById("searchCName").value!='')||(document.getElementById("userName").value!=''))
	{
		
			 document.getElementById("manualNpaMovementForm").action = contextPath+"/manualNpaMovementMakerSearch.do?method=searchManualNpa";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("manualNpaMovementForm").submit();
			return true;
		
	
	}else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function search_manual_npa_author(){

	//alert(type);
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("searchCName").value;
	var contextPath=document.getElementById("contextPath").value;
	
	if((document.getElementById("loanNo").value!='') || (document.getElementById("searchCName").value!='')||(document.getElementById("userName").value!=''))
	{
		
			 document.getElementById("manualNpaMovementForm").action = contextPath+"/manualNpaMovementAuthorSearch.do?method=searchManualNpaAuthor";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById("manualNpaMovementForm").submit();
			return true;
		
	
	}else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled", "true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function save_manual_npa()
{
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var lbxLoanNo= document.getElementById("lbxLoanNo").value;
	var customerName= document.getElementById("customerName").value;
	var product= document.getElementById("product").value;
	var scheme= document.getElementById("scheme").value;
	var currentStage=document.getElementById("currNpaStage").value;	
	var id =document.getElementById("getdetailsval").value;
	var contextPath=document.getElementById("contextPath").value;
    var lastNpaStage=document.getElementById("lastNpaStage").value;
	
	if((document.getElementById("loanNo").value!='' )&&(id != "" )&&(currentStage!="") && (lastNpaStage!="SALEOFF"))
	{
	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("manualNpaMovementAddForm").action = contextPath+"/manualNpaMovementMakerAdd.do?method=saveManualNPA";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualNpaMovementAddForm").submit();
		return true;
		
	}
	
	else if (loanNo=="")
	{   
		alert("Please select Loan Account First");
	     document.getElementById("loanbutton").focus();
		document.getElementById("save").removeAttribute("disabled","true");
		
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	else if(id == "" )
	{
		alert("Please get Details for the Loan");
		 
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		
	}
else if(currentStage=="" || currentStage==null){
		alert("Please select Current NPA Stage")
		document.getElementById("currNpaStage").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(lastNpaStage=="SALEOFF"){
		alert("You can not change NPA Stage as Last NPA Stage is SALEOFF");
		document.getElementById("currNpaStage").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
}

function delete_manual_npa()
{
	DisButClass.prototype.DisButMethod();
	var id = document.getElementById("manualNpaId").value;

	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("manualNpaMovementAddForm").action = contextPath+"/manualNpaMovementMakerAdd.do?method=deleteManualNPA&manualNpaId="+id;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualNpaMovementAddForm").submit();
		return true;
		
	
	
}

function saveManualNpaAuthor(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("comments").value;

	var contextPath=document.getElementById("contextPath").value;
	
	if((document.getElementById("comments").value=="") && !(document.getElementById("decision").value=="A" ))
	{
		alert("Comments is required.");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("authorForm").action = contextPath+"/manualNpaMovementAuthorProcess.do?method=saveManualNpaAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("authorForm").submit();
		return true;
		
	}
	
}
function valchange(){
	
	var npaStage = document.getElementById("currNpaStage").value;
	document.getElementById("forwardflag").value="";

	return false;
	
}
function forward_manual_npa(){
	DisButClass.prototype.DisButMethod();
	var flag = document.getElementById("statusflag").value;
	var contextPath=document.getElementById("contextPath").value;
	var id = document.getElementById("manualNpaId").value;
	var getdetailsvalid =document.getElementById("getdetailsval").value;
	
	if((document.getElementById("forwardflag").value!='' )&&(document.getElementById("getdetailsval").value!='' ))
	{
	
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("manualNpaMovementAddForm").action = contextPath+"/manualNpaMovementMakerAdd.do?method=forwardManualNPA&manualNpaId="+id;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualNpaMovementAddForm").submit();
		return true;
		
	}
	/*else if(getdetailsvalid == "" )
	{
		alert("Please get Details for the Loan");

		document.getElementById("forward").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		
	}*/
	else
	{
		alert("Please save the data first");
		document.getElementById("forward").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
}
function update_manual_npa()
{
	DisButClass.prototype.DisButMethod();
	var id = document.getElementById("manualNpaId").value;
	var currentStage=document.getElementById("currNpaStage").value;
		if(currentStage=="" || currentStage==null){
		alert("Please select Current NPA Stage")
		document.getElementById("currNpaStage").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
		var contextPath=document.getElementById("contextPath").value;
		document.getElementById("manualNpaMovementAddForm").action = contextPath+"/manualNpaMovementMakerAdd.do?method=updateManualNPA&manualNpaId="+id;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("manualNpaMovementAddForm").submit();
		return true;
		
	
	
}



function getDetailsfornew()
{
	
	//alert("get Details");
	var loanId = document.getElementById("lbxLoanNo").value;

	
	
	if(loanId == "")
	{
		alert("Please Enter Loan Account First");
		
		return false;
	}
	
	else{
		
	
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/manualNpaMovementMakerAdd.do?method=retriveGetDetailsValues&loanId="+loanId;
		 
		 var data = "lbxLoanNo=" + loanId;
		 sendloanid(address, data); 
		 return true;
	}
	
}
function sendloanid(address, data) {
	//alert("in sendClosureid ");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultmanualnpa(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultmanualnpa(request){
	//alert("In result Dues Refund");

	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		  
		var s1 = str.split("$:");
		document.getElementById('lbxLoanNo').value=trim(s1[0]);  
		document.getElementById('loanAmount').value=trim(s1[1]);
		document.getElementById('balancePrincipal').value=trim(s1[2]);
		document.getElementById('recievedPrincipal').value=trim(s1[3]);
		document.getElementById('overduePrincipal').value=trim(s1[4]);
		document.getElementById('totOverduePrinc').value=trim(s1[5]);
		document.getElementById('emiAmount').value=trim(s1[6]);
		document.getElementById('dpd').value=trim(s1[7]);
		document.getElementById('dpdString').value=trim(s1[8]);
		document.getElementById('lastNpaStage').value=trim(s1[9]);
		document.getElementById('loanNo').value=trim(s1[10]);
		document.getElementById('approvalDate').value=trim(s1[11]);
		document.getElementById('disbursalAmt').value=trim(s1[12]);
		document.getElementById('disbursalDate').value=trim(s1[13]);
		document.getElementById('instOverdue').value=trim(s1[14]);
		document.getElementById('getdetailsval').value="yes";
		window.close();
	}
	
}

function deletedisbursalDetails(){
	var sourcepath=document.getElementById("contextPath").value;
	var id=document.getElementById("loandisbursalid").value;
	agree=confirm("Are you sure,You want to delete this disbursal.Do you want to continue?");
	if (!(agree))
	{
    	document.getElementById("Save").removeAttribute("disabled","true");
		return false;
	}else{
		
		document.getElementById("disbursalMakerForm").action=sourcepath+"/disbursalSearch.do?method=deleteDisbursal&loandisbursalid"+id;
		document.getElementById("disbursalMakerForm").submit();
		return true;
}
}
function removeDataforGetDetails(){
	var getDetail=document.getElementById("getdetailsval");
		if(getDetail.value!=null && getDetail.value!=''){
			document.getElementById('getdetailsval').value='';
			document.getElementById('loanAmount').value='';
			document.getElementById('balancePrincipal').value='';
			document.getElementById('approvalDate').value='';
			document.getElementById('disbursalDate').value='';
			document.getElementById('disbursalAmt').value='';
			document.getElementById('instOverdue').value='';
			document.getElementById('overduePrincipal').value='';
			document.getElementById('recievedPrincipal').value='';
			document.getElementById('loanAmount').value='';
			document.getElementById('emiAmount').value='';
			document.getElementById('dpd').value='';
			
			document.getElementById('dpdString').value='';
			document.getElementById('lastNpaStage').value='';
			document.getElementById('currNpaStage').value='';
			document.getElementById('totOverduePrinc').value='';
		}
}
			