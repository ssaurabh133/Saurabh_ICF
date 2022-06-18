

function saveFleetDetails(formName)
{
	DisButClass.prototype.DisButMethod();
	
	 var financialInst = document.getElementById("financialInst").value;
	 var seasoning = document.getElementById("seasoning").value;
	 var vehicleNo = document.getElementById('vehicleNo').value;
	 var vehicleModel = document.getElementById('vehicleModel').value;
	 var loanNo = document.getElementById('loanNo').value;
	 var vehicleOwner = document.getElementById('vehicleOwner').value;
	 var other='';
     var dealFleetId=document.getElementById('dealFleetId').value;
	
	
				var contextPath =document.getElementById('contextPath').value ;
			
				  if (financialInst == "" || seasoning == "" || vehicleNo == "" || vehicleModel == "" || vehicleOwner == "") {
					var manFields = "financialInst / seasoning / vehicleNo / vehicleModel / vehicleOwner";
					validateForm(formName,manFields,other);
					document.getElementById("saveButton").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				  else
				  {
					  if(financialInst != "OTHER" && financialInst != '')
						{
							if(loanNo == '')
							{
								alert("Loan No. is required.");
								DisButClass.prototype.EnbButMethod();
								return false;
							}
						}
					     document.getElementById(formName).action = contextPath+"/saveFleetProcessAction.do?method=saveFleetDetail&dealFleetId="+dealFleetId;
					     document.getElementById("processingImage").style.display = '';
					     document.getElementById(formName).submit();
						 return true;
					  

				    }


			}


//-----------------------------------------VALIDATION--------------------------


function validateForm(formName,manFields,other){
	var ck_numeric = /^[0-9,.]{1,50}$/;
	var ck_alphaNumeric = /^[A-Za-z0-9,\. _]{1,50}$/;
	var ck_mail= /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	
	var errors = [];
	var reqFields = [];
	var matchField = "";
	var elem = document.getElementById(formName).elements;
	for(var i=0; i<= elem.length;i++){
		var str = '';
		if(!elem[i] || elem[i] == "undefined"  || elem[i].value == "undefined" || elem[i].name == (null || '')){
			continue;
		}
		if(manFields.match(elem[i].name)){
			matchField = manFields.match(elem[i].name);
				if(elem[i].value == null || elem[i].value == ""){
					//alert(elem[i].value);
					errors[errors.length]= elem[i].name;
				}
	 
		}else{
			continue;
		}
	}
	if (errors.length > 0) {
		  reportErrors(errors,matchField,other);
		  return false;
	 }
}

function reportErrors(errors,matchField,other){
	 var msg = "";
	 var errorMsg = other;
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += errors[i];
	 }
	  if(msg.match("financialInst")){
		  errorMsg +="* Financial institution is required \n";
	 }
	  if(msg.match("seasoning")){
		  errorMsg +="* Seasoning(in months) is required \n";
	 }
	  if(msg.match("vehicleOwner")){
		  errorMsg +="* Vehicle owner is required \n";
	 }
	  if(msg.match("vehicleNo")){
		  errorMsg +="* Vehicle no is required \n";
	 }
	  if(msg.match("vehicleModel")){
		  errorMsg +="* Vehicle model is required \n";
	 }
	  
	//  alert(msg);
	  if(msg.match("financialInst")){
		  document.getElementById("financialInst").focus();
	 }else if(msg.match("seasoning")){
		 document.getElementById("seasoning").focus();
	 }else if(msg.match("vehicleOwner")){
		 document.getElementById("vehicleOwner").focus();
	 }else if(msg.match("vehicleNo")){
		 document.getElementById("vehicleNo").focus();
	 }else if(msg.match("vehicleModel")){
		 document.getElementById("vehicleModel").focus();
	 }
	 alert(errorMsg);
	
}

function getAllFleetDetails(dealFleetId)
{
	//alert("dealFleetId: "+dealFleetId);
	var contextPath =document.getElementById('contextPath').value ;
	location.href=contextPath+"/fleetBehindAction.do?method=getFleetsDetail&dealFleetId="+dealFleetId;
}

function getAllAuthorFleetDetails(dealFleetId)
{
	//alert("dealFleetId: "+dealFleetId);
	var contextPath =document.getElementById('contextPath').value ;
	location.href=contextPath+"/fleetBehindAction.do?method=getAuthorFleetsDetail&dealFleetId="+dealFleetId;
}


function deleteFleetDetails()
{
	DisButClass.prototype.DisButMethod();
	    if(check())
	    {
	    	if(confirm("Are you sure to delete Fleet Details?"))
	    	{

			document.getElementById("fleetDetailForm").action="fleetBehindAction.do?method=deleteFleetDetail";
		 	document.getElementById("fleetDetailForm").submit();
		 	return true;
	    	}
	    	else
	    	{
	    		
	    		document.getElementById("deleteButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
	    	}
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	document.getElementById("deleteButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
	    }
}

function check()
{
    // alert("ok");
	var ch=document.getElementsByName('chk');
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


function allChecked()
{
	// alert("ok");
var c = document.getElementById("allchk").checked;
var ch=document.getElementsByName('chk');
var zx=0;
// alert(c);
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

function getFinancialInstName()
{
	var financialInst=document.getElementById('financialInst').value;
	var contextPath =document.getElementById('contextPath').value ;
	
	if(financialInst != "OTHER")
	{
		var address = contextPath+"/ajaxActionForCP.do?method=getFinancialInstName";
		var data = "financialInst="+financialInst;
		sendRequestFinancialInstName(address,data);
		return true;
	}
	else
	{
		document.getElementById('financialInstName').value='';
		document.getElementById("financialInstName").removeAttribute("readonly",true);
   	 	return false;
	}
}
function sendRequestFinancialInstName(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethodFinancialInstName(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethodFinancialInstName(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			if(trim(s1[1])!='')
			{
				document.getElementById('financialInstName').value=trim(s1[1]);
				document.getElementById("financialInstName").setAttribute("readonly",true);
			}
	    }
		
	}
}

function showHideLOV()
{
	var financialInst=document.getElementById("financialInst").value;
	document.getElementById("loanNo").value='';
	document.getElementById("financialInstName").value='';
	//document.getElementById("loanNo").readOnly = false;
	if(financialInst != "OTHER" && financialInst != '')
	{
		document.getElementById("loanNoButton").style.display="";
		document.getElementById("loanNo").readOnly = true;
		document.getElementById("financialInstName").setAttribute("readonly",true);
	}
	else
	{
		document.getElementById("loanNoButton").style.display="none";
		document.getElementById("loanNo").readOnly = false;
		document.getElementById("financialInstName").removeAttribute("readonly",true);
	}
}

function showHideLovOnLoad()
{
	var financialInst=document.getElementById("financialInst").value;
	if(financialInst != "OTHER" && financialInst != '')
	{
		document.getElementById("loanNoButton").style.display="";
		document.getElementById("loanNo").readOnly = true;
	}
	else
	{
		document.getElementById("loanNoButton").style.display="none";
		document.getElementById("loanNo").readOnly = false;
	}
}

function openFleetPopUp() {
	
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var loanId =document.getElementById('lbxLoanNoHid').value ;
	var loanNo =document.getElementById('loanNo').value ;
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fleetBehindAction.do?method=openFleetDetailsPopUp&loanId="+loanId+" ";
	if(loanId == '' && loanNo =='')
	{
		alert("Loan No. is required.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(loanId == '' && loanNo !='')
	{
		alert("Fleet details is available for existing loans only.");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		DisButClass.prototype.EnbButMethod();
		return false;
	}

}
