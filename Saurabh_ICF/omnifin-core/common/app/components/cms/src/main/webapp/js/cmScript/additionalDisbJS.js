function openNewDisbMaker()
{
			DisButClass.prototype.DisButMethod();
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("additionalDisbSearchForm").action = contextPath+"/additionalDisbursalMaker.do?method=openNewDisbMaker";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("additionalDisbSearchForm").submit();
}


function saveAdditionalDisbursal()
{
	//alert("saveAdditionalDisbursal");
	DisButClass.prototype.DisButMethod();
	var outstandingLoanAmount=0;
	var reschCharges=0;
	var disbursalAm=0;
	var msg1='', msg2='', msg3='',msg4='';
	var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;
	var reschReason = document.getElementById("reschReason").value;
	var disbursalAm = document.getElementById("disbursalAm").value;
	var rescheduleParameter = document.getElementById("rescheduleParameter").value;
	if(lbxLoanNoHID=='')
	{
		msg1="* Loan No is required.\n";
	}
	if(disbursalAm=='')
	{
		msg2="* Disbursal Amount is required.\n";
	}
	if(reschReason=='')
	{
		msg3="* Rescheduling Reason is required.\n";
	}
	if(rescheduleParameter=='')
	{
		msg4="* Reschedule Parameter is required.\n";
	}
	
	if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
	{
		alert(msg1+msg2+msg3+msg4);
		if(msg1!='')
		{
			document.getElementById('loanButton').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg2!='')
		{
			document.getElementById('disbursalAm').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg3!='')
		{
			document.getElementById('reschReason').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg4!='')
		{
			document.getElementById('rescheduleParameter').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		return false;
	}
	
	if(lbxLoanNoHID!='' && reschReason!='' && disbursalAm!='')
	{
		/*if(cal >0 )
		{
			alert("Receivable/s are pending for this loan. Do you want to continue?");
		}*/
		if(document.getElementById("outstandingLoanAmount").value!='')
		{
			outstandingLoanAmount = removeComma(document.getElementById("outstandingLoanAmount").value);
		}
		if(document.getElementById("reschCharges").value!='')
		{
			reschCharges = removeComma(document.getElementById("reschCharges").value);
		}
		if(document.getElementById("disbursalAm").value!='')
		{
			disbursalAm = removeComma(document.getElementById("disbursalAm").value);
		}
	
		if(disbursalAm<=0)
		{
			alert("Disbursal Amount must be greater than zero");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	/*	if(disbursalAm> outstandingLoanAmount)
		{
			alert("Part Payment Amount cannot be greater than Outstanding Loan Amount");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}*/
		if(reschCharges<0)
		{
			alert("Reschedule Charges cannot be less than zero");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
		else
		{
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("addDisbMakerForm").action = contextPath+"/addDisbursalMakerAction.do?method=saveAddDisbursalMaker";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("addDisbMakerForm").submit();
		    return true;
		}
	}
	else
	{
		//alert("Loan No is required.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
}

function forwardAdditionalDisbData()
{
	DisButClass.prototype.DisButMethod();
	var msg1='', msg2='', msg3='',msg4='';
	var reschId = document.getElementById("reschId").value;
	var lbxLoanNoHID = document.getElementById("lbxLoanNoHID").value;
	var disbursalAm = document.getElementById("disbursalAm").value;
	var reschReason = document.getElementById("reschReason").value;
	var rescheduleParameter = document.getElementById("rescheduleParameter").value;
	if(reschId==''||reschId==0)
	{
		alert("First Save Loan");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("saveFwd").removeAttribute("disabled","true");
		return false;
	}
	if(lbxLoanNoHID=='')
	{
		msg1="* Loan No is required.\n";
	}
	if(disbursalAm=='')
	{
		msg2="* Disbursal Amount is required.\n";
	}
	if(reschReason=='')
	{
		msg3="* Rescheduling Reason is required.\n";
	}
	if(rescheduleParameter=='')
	{
		msg4="* Reschedule Parameter is required.\n";
	}
	
	if(msg1!=''||msg2!=''||msg3!=''|| msg4!='')
	{
		alert(msg1+msg2+msg3+msg4);
		if(msg1!='')
		{
			document.getElementById('loanButton').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg2!='')
		{
			document.getElementById('disbursalAm').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg3!='')
		{
			document.getElementById('reschReason').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		else if(msg4!='')
		{
			document.getElementById('rescheduleParameter').focus();
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
		}
		return false;
	}
	if(lbxLoanNoHID!='' && reschReason!='' && disbursalAm!='' && reschId!='')
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("addDisbMakerForm").action = contextPath+"/addDisbursalMakerAction.do?method=forwardAddDisbursalData&reschId="+reschId;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("addDisbMakerForm").submit();
	    return true;
	}
	
}

function searchAdditionalMaker()
{
	//alert("searchAdditionalMaker");
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("customerName").value;
	var reportingToUserId = document.getElementById("reportingToUserId").value;
	if(loanNo=='' && custName==''&& reportingToUserId=='')
	{
		alert("Please Enter One value to Search.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("additionalDisbSearchForm").action = contextPath+"/additionalDisbursalMaker.do?method=searchAdditionalDisbursalMaker";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("additionalDisbSearchForm").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
	}
		
}

function additionalDisbursalBeforeSave()
{
	alert("Please save the data First");
	return false;
}
function searchAddDisbursalAuthor()
{
	//alert("searchAddDisbursalAuthor");
	DisButClass.prototype.DisButMethod();
	var loanNo = document.getElementById("loanNo").value;
	var custName = document.getElementById("customerName").value;
	var reportingToUserId = document.getElementById("reportingToUserId").value;
	if(loanNo=='' && custName==''&& reportingToUserId=='')
	{
		alert("Please Enter One value to Search.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("additionalDisbursalSearchAuthor").action = contextPath+"/additionalDisbursalAuthor.do?method=searchAdditionalDisbursalAuthor";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("additionalDisbursalSearchAuthor").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
	}
}

function saveAdditionalDisbAuthor()
{
	DisButClass.prototype.DisButMethod();
	var decision=document.getElementById("decision").value;
	if(decision=='A')
	{
		var businessDate=document.getElementById("businessDate").value;
		var makerDate=document.getElementById("makerDate").value;	
		var fromdate=businessDate;
		var todate=makerDate;
		var day1= fromdate.substring(0,2);
		var day2= todate.substring(0,2);
		var month1=fromdate.substring(3,5);
		var month2=todate.substring(3,5);
		var year1=fromdate.substring(6);
		var year2=todate.substring(6);
		if(parseFloat(year1) != parseFloat(year2))
		{	
			alert("Maker Date should be equal to Business Date.");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("comments").focus();
			return false;
		}
		else
		{
			 if(parseFloat(year1)==parseFloat(year2))
			 {
				if(parseFloat(month1) != parseFloat(month2))
				{	
					alert("Maker Date should be equal to Business Date.");
					DisButClass.prototype.EnbButMethod();
					document.getElementById("save").removeAttribute("disabled","true");
					document.getElementById("comments").focus();
	    			return false;
				}
				else 
				{
					if(parseFloat(month1)==parseFloat(month2))
					{
						if(parseFloat(day1) != parseFloat(day2))
						{	
							alert("Maker Date should be equal to Business Date.");
							DisButClass.prototype.EnbButMethod();
							document.getElementById("save").removeAttribute("disabled","true");
							document.getElementById("comments").focus();
			    			return false;
						}
					}
				}
			}
		}
	}
	if(document.getElementById("comments").value=='' && !(document.getElementById("decision").value=='A')) //Edited by Nishant

	{
		alert("Comments is Required");
		document.getElementById("save").removeAttribute("disabled","true");
		document.getElementById("comments").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}


	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("addDisbursalAuthorForm").action = contextPath+"/addDisbAuthorAction.do";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("addDisbursalAuthorForm").submit();
	    return true;
	}
}

function viewInstallmentPlanAddtionalDisbursal()
{
	
	var loanId = document.getElementById("lbxLoanNoHID").value;
	//alert("Inside viewInstallmentPlanAddtionalDisbursal "+loanId);
	if (loanId=='')
	{
		alert("Please Select Loan First");	
		return false;
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url= contextPath+"/installmentPlanAddtionDisbursal.do?method=viewOldInstallmentAdditionDisbursal&loanId="+loanId;
		//alert("url: "+url);
		//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}

function generateInstallmentPlanAdditionalDisbursal(type)
{
	
	var loanId = document.getElementById("lbxLoanNoHID").value;
	var reschId = document.getElementById("reschId").value;
	var installNo=document.getElementById("lbxInstlNo").value;
	//alert("Inside generateInstallmentPlanAdditionalDisbursal loanId "+loanId+" reschId: "+reschId+"installNo: "+installNo);
	if (loanId=='')
	{
		alert("Please Select Loan First");	
		return false;
	}
	else
	{
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url="";
		if(type=='P')
			url= contextPath+"/installmentPlanAddtionDisbursal.do?method=viewNewInstallmentPlanAdditionDisbursal&loanId="+loanId+"&reschId="+reschId+"&installNo="+installNo;
		if(type=='F')
			url= contextPath+"/installmentPlanAddtionDisbursal.do?method=viewNewInstallmentPlanAdditionDisbursalAuthor&loanId="+loanId+"&reschId="+reschId+"&installNo="+installNo;
		//alert("url: "+url);
		//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}

function saveNewInstallmentPlanAddtionalDisbursal()
{
	var contextPath=document.getElementById("contextPath").value;
	var loanId = document.getElementById("loanId").value;
	var reschId = document.getElementById("reschId").value;
	var recoveryType=document.getElementById("recoveryType").value;
	//alert("recoveryType: "+recoveryType);
	var loanAmount=document.getElementById("loanAmount").value;
	//alert("loanAmount: "+loanAmount);
	var installmentType=document.getElementById("installmentType").value;
	//alert("installmentType: "+installmentType);
	var totalInstallment=document.getElementById("totalInstallment").value;
	//alert("totalInstallment: "+totalInstallment);
	var gridTable = document.getElementById('gridTable');
	var tableLength = gridTable.rows.length-1;
	//alert("tableLength: "+tableLength);
	var sum=0;
	var psum=0;
	var isum=0;
	  
	for(var i=1;i<=tableLength;i++)
	{
		var fromInstallment=document.getElementById("fromInstallment"+i).value;
		//alert("fromInstallment: "+fromInstallment);
		var toInstallment=document.getElementById("toInstallment"+i).value;
		//alert("toInstallment: "+toInstallment);
		var recoveryPercen=document.getElementById("recoveryPercen"+i).value;
		//alert("recoveryPercen: "+recoveryPercen);
		var principalAmount=document.getElementById("principalAmount"+i).value;
		//alert("principalAmount: "+principalAmount);
		var installmentAmount=document.getElementById("installmentAmount"+i).value;
		//alert("installmentAmount: "+installmentAmount);
		if ((fromInstallment=="")||(toInstallment=="")||(recoveryPercen=="" ) ||(principalAmount=="")|| (installmentAmount==""))
		{
			 alert("Please fill all the fields ");
			 document.getElementById("save").removeAttribute("disabled","true");
			 return false;
		}
	    if(fromInstallment=="")
	    {
	    	fromInstallment=0;
		}
	    else
	    	fromInstallment=removeComma(fromInstallment);
	    if(toInstallment=="")
	    {
	    	toInstallment=0;
		}
	    else
	    	toInstallment=removeComma(toInstallment);
	    
	    if(recoveryPercen=="")
	    {
	    	recoveryPercen=0;
		}
	    else    	  
	    	recoveryPercen=removeComma(recoveryPercen);

	    sum= sum + recoveryPercen;
	    //alert("sum: "+sum);
	    
	    if(principalAmount=="")
	    {
	    	principalAmount=0;
		}
	    else	  
	    	principalAmount=removeComma(principalAmount);
	    
	    psum= psum + principalAmount * (toInstallment - fromInstallment + 1);
	    //alert("psum: "+psum);
	    
	    if(installmentAmount=="")
	    {
	    	installmentAmount=0;
		}
	    else    	  
	    	installmentAmount=removeComma(installmentAmount);
	    
	    isum= isum + installmentAmount* (toInstallment - fromInstallment + 1);
	    //alert("isum: "+isum);
	    
	   

	    if(parseInt(document.getElementById("fromInstallment"+i).value) > parseInt(document.getElementById("toInstallment"+i).value))
		{	    	
			alert("From Installment should be less than or equal to To Installment ");
			document.getElementById("save").removeAttribute("disabled","true");
			return false;
		}
	 
		
		if(i<tableLength)
		{	
			var k=i+1;
			
			if(parseInt(document.getElementById("toInstallment"+i).value) > parseInt(document.getElementById("fromInstallment"+k).value))
			{
				alert("Next From Installment should be greater than Previous To Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}
			if(parseInt(document.getElementById("toInstallment"+i).value)+1 != parseInt(document.getElementById("fromInstallment"+k).value))
			{
		
				alert("Next From Installment should be equal to Previous To Installment");
				document.getElementById("save").removeAttribute("disabled","true");
				return false;
			}

		}
	
	}
	
	var lastToInstall=document.getElementById("toInstallment"+tableLength).value;
	//alert("lastToInstall: "+lastToInstall);
	if(document.getElementById("fromInstallment1").value != '1')
	{
		alert("First Installment should start from 1");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
/*	if(parseInt(lastToInstall)> parseInt(totalInstallment)){
		alert("No. of  Installment should be equal to Total Installment "+totalInstallment);
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}*/
	if(parseInt(lastToInstall)< parseInt(totalInstallment)){
		alert("No. of  Installment should be equal to Total Installment "+totalInstallment);
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	if(sum!=100 && recoveryType=='P')
	{
		alert("Total Recovery Percentage should be  equal to 100 %");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	if((psum>removeComma(loanAmount) && recoveryType=='F') && (installmentType=='P'||installmentType=='Q'||installmentType=='R'))
	{
		alert("Principal Amount should be equal to Loan Amount");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	
	if((isum<removeComma(loanAmount) && recoveryType=='F') && (installmentType=='G'))
	{
		alert("Installment Amount should be equal to or greater than Loan Amount");
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	else
	{
		document.getElementById("installmentPlanForm").action = contextPath+"/addDisbInstallmentPlanActionProcess.do?method=saveNewInstallmentPlan&installmentType="+installmentType+"&loanId="+loanId+"&reschId="+reschId;
		document.getElementById("installmentPlanForm").submit();
	    return true;
	}
}

function viewRepaymentScheduleAdditionDisbursal()
{
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert("Please Select Loan First");	
		return false;
	}
	else
	{
		curWin = 0;
		otherWindows = new Array();
		var loanId=document.getElementById('lbxLoanNoHID').value;	
		var contextPath = document.getElementById("contextPath").value;
		var url= contextPath+"/repaymentScheduleAdditionalDisbursal.do?method=repaymentSchedule&loanId="+loanId; 
		myWindow=window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes").focus();
		otherWindows[curWin++] =window.open(url,'viewPayable',"height=300,width=1050,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		if(window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}

function newRepaymentScheduleAdditionalDisbursal()
{
	if (document.getElementById("lbxLoanNoHID").value=="")
	{
		alert("Please Select Loan First");	
		return false;
	}
	else
	{
		var loanId=document.getElementById('lbxLoanNoHID').value;
		var reschId=document.getElementById('reschId').value;
		var instlNo=document.getElementById('lbxInstlNo').value;
		//alert("loanId: "+loanId+"instlNo: "+instlNo+"reschId: "+reschId);
		var contextPath = document.getElementById("contextPath").value;
		//alert(contextPath);
		var url= contextPath+"/repaymentScheduleAdditionalDisbursal.do?method=newRepaymentScheduleAdditionalDisb&loanId="+loanId+"&reschId="+reschId+"&instlNo="+instlNo;
		//alert("url: "+url);
		//window.open(url,'View Receivable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
   }
}
//alert("neeraj tripathi");
function deleteAdditionalDisbDetails()
{
	DisButClass.prototype.DisButMethod();
	if(confirm("Are you sure to delete the record.")) 
    {
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("addDisbMakerForm").action = contextPath+"/addDisbursalMakerAction.do?method=deleteAddDisbursalMaker";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("addDisbMakerForm").submit();
	    return true;
    }
	else
	{
		DisButClass.prototype.EnbButMethod();
		document.getElementById("delete").removeAttribute("disabled","true");
		return false;
	}
	
}
function viewReceivableAdditionDisbursal(alert1) 
{	
	var loanId=document.getElementById('lbxLoanNoHID').value;
	
	if(loanId=="")
		{
			alert(alert1);
			document.getElementById("viewRec").removeAttribute("disabled");
			return false;
		}
	else
	{
		var loanId=document.getElementById('lbxLoanNoHID').value;
		var basePath=document.getElementById("contextPath").value;
		window.open(basePath+'/additionalDisbViewReceivable.do?method=viewReceivableAddDis&loanId='+loanId,'viewPayable',"height=300,width=800,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		return true;
	}
}



//To insert new row

function addROWInAdditionDisb(){
	
	
	var installmentType = document.getElementById("installmentType").value;
 	var recoveryType = document.getElementById("recoveryType").value;
   	var princAm =	 document.getElementsByName("principalAmount"); 
   	var installmentAmount =	 document.getElementsByName("installmentAmount"); 
	var recoveryPer =	 document.getElementsByName("recoveryPer"); 

	//alert("addROW");
	if(recoveryType=='F')
   	{
   		if(installmentType=='P'||installmentType=='Q')
   		{
   				var table = document.getElementById("gridTable");

   				var rowCount = table.rows.length;
   				var row = table.insertRow(rowCount);
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+rowCount;
   				cell1.appendChild(element1);

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "fromInstall";
   				element2.id = "fromInstallment"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				cell2.appendChild(element2);
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "toInstall";
   				element3.id = "toInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "recoveryPer";
   				element4.value = "0";
   				element4.id = "recoveryPercen"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				element4.setAttribute('readonly','true' );
   				cell4.appendChild(element4);
   				
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "principalAmount";
   				element5.id = "principalAmount"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				cell5.appendChild(element5);
   				
   				
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "installmentAmount";
   				element6.value = "0";
   				element6.id = "installmentAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				element6.setAttribute('readonly','true' );
   				cell6.appendChild(element6);
   				
   			}
	   		else
	   		{
	   			var table = document.getElementById("gridTable");

   				var rowCount = table.rows.length;
   				var row = table.insertRow(rowCount);
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+rowCount;
   				cell1.appendChild(element1);

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "fromInstall";
   				element2.id = "fromInstallment"+rowCount;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				cell2.appendChild(element2);
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "toInstall";
   				element3.id = "toInstallment"+rowCount;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "recoveryPer";
   				element4.value = "0";
   				element4.id = "recoveryPercen"+rowCount;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				element4.setAttribute('readonly','true' );
   				cell4.appendChild(element4);
   				
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "principalAmount";
   				element5.value = "0";
   				element5.id = "principalAmount"+rowCount;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   				element5.setAttribute('readonly','true' );
   				cell5.appendChild(element5);
   				
   				
   				var cell6 = row.insertCell(5);
   				var element6 = document.createElement("input");
   				element6.type = "text";
   				element6.name = "installmentAmount";
   				element6.id = "installmentAmount"+rowCount;
   				element6.setAttribute('class','text');
   				element6.setAttribute('className','text' );
   				cell6.appendChild(element6);
	   		}
   		}
	else
	{
		var table = document.getElementById("gridTable");

			var rowCount = table.rows.length;
			var row = table.insertRow(rowCount);
			row.setAttribute('className','white1' );
		    row.setAttribute('class','white1' );
			var cell1 = row.insertCell(0);
			var element1 = document.createElement("input");
			element1.type = "checkbox";
			element1.name = "chk";
			element1.id = "chk"+rowCount;
			cell1.appendChild(element1);

			var cell2 = row.insertCell(1);
			var element2 = document.createElement("input");
			element2.type = "text";
			element2.name = "fromInstall";
			element2.id = "fromInstallment"+rowCount;
			element2.setAttribute('class','text');
			element2.setAttribute('className','text' );
			cell2.appendChild(element2);
			
			var cell3 = row.insertCell(2);
			var element3 = document.createElement("input");
			element3.type = "text";
			element3.name = "toInstall";
			element3.id = "toInstallment"+rowCount;
			element3.setAttribute('class','text');
			element3.setAttribute('className','text' );
			cell3.appendChild(element3);
			
			var cell4 = row.insertCell(3);
			var element4 = document.createElement("input");
			element4.type = "text";
			element4.name = "recoveryPer";
			element4.value = "0";
			element4.id = "recoveryPercen"+rowCount;
			element4.setAttribute('class','text');
			element4.setAttribute('className','text' );
			
			cell4.appendChild(element4);
			
			
			var cell5 = row.insertCell(4);
			var element5 = document.createElement("input");
			element5.type = "text";
			element5.name = "principalAmount";
			element5.value = "0";
			element5.id = "principalAmount"+rowCount;
			element5.setAttribute('class','text');
			element5.setAttribute('className','text' );
			element5.setAttribute('readonly','true' );
			cell5.appendChild(element5);
			
			
			var cell6 = row.insertCell(5);
			var element6 = document.createElement("input");
			element6.type = "text";
			element6.name = "installmentAmount";
			element6.value = "0";
			element6.id = "installmentAmount"+rowCount;
			element6.setAttribute('class','text');
			element6.setAttribute('className','text' );
			element6.setAttribute('readonly','true' );
			cell6.appendChild(element6);
	}
   		
	

}
//To remove a row

function removeRowInAdditionDisb() {   
	 var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	 var chk =document.getElementsByName("chk"); 
	 var count=0;
	 for(var j=1;j<rowCount;j++){
		 if(document.getElementById("chk"+j).checked==true){
			 count=count+1;
			 }
	 }
	
if(count==0){
	alert("Please Select at least one row to delete");
}else{

 
  
  for(var i=1; i<rowCount; i++) {
  	var row = table.rows[i];
      var chkbox = row.cells[0].childNodes[0];
       if(null != chkbox && true == chkbox.checked) 
      	{
      	 table.deleteRow(i);
          rowCount--;
          i--;
      }               
  }
}
}

function generateReschCharges()
{
	// alert("hi");
	 var loanId=document.getElementById("lbxLoanNoHID").value;
	 var partPaymentAmt=removeComma(document.getElementById("disbursalAm").value);
	 var reschDate=document.getElementById("disbursalDate").value;
	 if(reschDate!='' && loanId!='')
	 {
		 var contextPath=document.getElementById("contextPath").value;
		 var address = contextPath+"/ajaxAction.do?method=generateReschCharges";
		 var data = "lbxLoanNoHID="+loanId+"&partPaymentAmt="+partPaymentAmt+"&reschDate="+reschDate;
		 sendGenerateReschChargesId(address, data);
		 return true;
	 }
	 else
    {
		 if(loanId=='')
		 {
				alert("Please Enter Loan Id");
			   	document.getElementById("loanNo").value="";
			   	document.getElementById("lbxLoanNoHID").value="";
			   	document.getElementById("disbursalAm").value="";
			   	document.getElementById("loanNo").focus();
			   	return false;
		 }
		 else if(reschDate=='')
		 {
				alert("Please Enter Disbursal Date");
			   	document.getElementById("disbursalDate").value="";
			   	document.getElementById("disbursalAm").value="";
			   	document.getElementById("disbursalDate").focus();
			   	return false;
		 }
	   
    }
	 
}


function sendGenerateReschChargesId(address, data) {
	// alert("in sendPartPaymentid id");
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultGenerateReschCharges(request);	
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
	
}

function resultGenerateReschCharges(request){
	
	// alert("in resultPartpayment id");
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		// alert("String:--->"+str);
		var s1 = str.split("$:");
		document.getElementById("reschCharges").value=trim(s1[0]);  
		document.getElementById("interestForGapPeriod").value=trim(s1[1]);
	}
}

