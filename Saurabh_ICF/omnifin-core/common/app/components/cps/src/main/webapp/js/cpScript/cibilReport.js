var ck_numeric = /^[A-Za-z0-9+-]{1,25}$/;
var ck_din = /^[0-9]{6}$/;
var san_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
var san_url = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;



function validate(formName){
	var cibilCodes = document.getElementById("cibilCodes").value;
	var totalOverdueac = document.getElementById("totalOverdueac").value;
	var totalAc = document.getElementById('totalAc').value;
	var noofEnquiry = document.getElementById('noofEnquiry').value;
  

 var errors = [];
 
 if (!ck_numeric.test(cibilCodes)) {
	 if(cibilCodes != ''){
	 	errors[errors.length] = "* CIBIL Score is invalid.";
	 }
 }
 if (!ck_numeric.test(totalAc)) {
	 if(totalAc != ''){
		 errors[errors.length] = "* Total Accounts is invalid.";
	 }
}
 if (!ck_numeric.test(totalOverdueac)) {
	 if(totalOverdueac != ''){
		 errors[errors.length] = "* Total Overdue Account is invalid.";
	 }
 }
 if (!ck_numeric.test(noofEnquiry)) {
	 if(noofEnquiry != ''){
		 errors[errors.length] = "* No.Of Enquiries is invalid.";
	 }
}
 
 if (errors.length > 0) {
  reportErrors(errors);
  return false;
 }
 
 return true;
}


function reportErrors(errors){
 var msg = "";
 for (var i = 0; i<errors.length; i++) {
  var numError = i + 1;
  msg += "\n" + errors[i];
 }
 if(msg.match("CIBIL")){
	 document.getElementById("cibilCodes").focus();
	}else if(msg.match("Accounts")){
		document.getElementById("totalAc").focus();
	}else if(msg.match("Overdue")){
		document.getElementById("totalOverdueac").focus();
	}else if(msg.match("Enquiries")){
		document.getElementById("noofEnquiry").focus();
	}
 
 alert(msg);
 DisButClass.prototype.EnbButMethod();
 document.getElementById("save").removeAttribute("disabled","true");
}



function textCounter( field, countfield, maxlimit ) {
  if ( field.value.length > maxlimit )
  {
    field.value = field.value.substring( 0, maxlimit );
    return false;
  }
  else
  {
    countfield.value = maxlimit - field.value.length;
  }
}

function searchCibilReportDeal(stage)
	{
		DisButClass.prototype.DisButMethod();
		var dealNo=document.getElementById("dealNo").value;
		var applicationno=document.getElementById("applicationno").value;
		var applicationdate=document.getElementById("applicationdate").value;
		var customername=document.getElementById("customername").value;
		var lbxProductID=document.getElementById("lbxProductID").value;
		var lbxscheme=document.getElementById("lbxscheme").value;
		var contextPath= document.getElementById("contextPath").value;
		
		var allBranches= document.getElementById("allBranches").value;
		if(stage=='P')
		{
		//alert("branches::"+allBranches);
		//var userName=document.getElementById("userName").value;
		if(dealNo!=''||applicationno!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!=''||allBranches=='on')
		{
			if(customername!='' && customername.length>=3)
			{
				document.getElementById("CibilReportForm").action=contextPath+"/cibilCustomer.do?method=cibilReportSearch&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("CibilReportForm").submit();
				
				return true;
			}
			else if(customername=='')
			{
				document.getElementById("CibilReportForm").action=contextPath+"/cibilCustomer.do?method=cibilReportSearch&stage="+stage;
				document.getElementById("processingImage").style.display='block';
				document.getElementById("CibilReportForm").submit();
				
				return true;
			}
			else
			{
				alert("Please Enter atleast 3 characters of Customer Name ");
				document.getElementById("cibilSearchButton").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("cibilSearchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
else
{
	var allBranches=document.getElementById("allBranches").checked;
	var selectedBranch=document.getElementById("lbxBranchId").value;
	if(allBranches==true && selectedBranch!='')
	{
		alert("Select either All Branch or Selective Branch.");
		document.getElementById("cibilSearchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else if(dealNo!=''||applicationno!=''||applicationdate!=''||customername!=''||lbxProductID!=''||lbxscheme!=''|| allBranches!= 'on'|| selectedBranch!='')
	{
		if(customername!='' && customername.length>=3)
		{
			document.getElementById("CibilReportForm").action=contextPath+"/cibilCustomer.do?method=cibilReportSearch&stage="+stage;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CibilReportForm").submit();
			return true;
		}
		else if(customername=='')
		{
			document.getElementById("CibilReportForm").action=contextPath+"/cibilCustomer.do?method=cibilReportSearch&stage="+stage;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CibilReportForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("cibilSearchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field or select all branches.");
		document.getElementById("cibilSearchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
}


function saveCIBIL(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	var consumername = document.getElementById("consumername");
	var leadDate = document.getElementById("leadDate");
	var cibilCodes = document.getElementById("cibilCodes");
	var totalOverdueac = document.getElementById("totalOverdueac");
	var decison = document.getElementById("decison");
	var comment = document.getElementById("comment");
	/*var control = document.getElementById("control");   sarvesh added*/
	
	 if(trim(consumername.value) == ''||trim(leadDate.value) == ''||trim(cibilCodes.value) == ''||trim(totalOverdueac.value) == ''||trim(decison.value) == ''||trim(comment.value) == '')
	 {
		 var msg= '';
 		if(trim(consumername.value) == '')
 			msg += '* Consumer Name is required.\n';
 		if(trim(leadDate.value) == '')
 			msg += '* Date & Time is required.\n';
 		if(trim(cibilCodes.value) == '')
 			msg += '* CIBIL Score is required.\n';
 		if(trim(totalOverdueac.value) == '')
 			msg += '* Total Overdue Accounts is required.\n';
 		if(trim(decison.value) == '')
 			msg += '* Decision is required.\n';
 		if(trim(comment.value) == '')
 			msg += '* Comment is required.\n';
 		 
 		if(msg.match("Consumer")){
 			consumername.focus();
 		}else if(msg.match("Date")){
 			leadDate.focus();
 		}else if(msg.match("CIBIL")){
 			cibilCodes.focus();
 		}else if(msg.match("Total")){
 			totalOverdueac.focus();
 		}else if(msg.match("Decision")){
 			decison.focus();
 		}else if(msg.match("Comment")){
 			comment.focus();
 		}
 		
 		alert(msg);
 		document.getElementById("save").removeAttribute("disabled","true");
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 
	 }else if(validate("consumermasterform") ){//&&  validateDocUploadForCibil()){
		document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerAdd.do?method=saveDocDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("consumermasterform").submit();
		return true;
	}
}
 

function validateDocUploadForCibil()
{
		var status1=1;
		var status2=1;
		var status3=1;
		var cibilReportFileOne = document.getElementById('cibilReportFileOne').value;
		var cibilReportFileTwo = document.getElementById('cibilReportFileTwo').value;
		var cibilReportFileThree = document.getElementById('cibilReportFileThree').value;
	if((cibilReportFileOne!='' && cibilReportFileTwo!='') || (cibilReportFileOne!='' && cibilReportFileThree!='')||(cibilReportFileTwo!='' && cibilReportFileOne!='') || (cibilReportFileTwo!='' && cibilReportFileThree!='') ||(cibilReportFileThree!='' && cibilReportFileOne!='') || (cibilReportFileThree!='' && cibilReportFileTwo!='')) 
	{
		if(cibilReportFileOne==cibilReportFileTwo||cibilReportFileOne==cibilReportFileThree||cibilReportFileTwo==cibilReportFileThree||cibilReportFileTwo==cibilReportFileOne||cibilReportFileThree==cibilReportFileOne||cibilReportFileThree==cibilReportFileTwo)
		{
			alert("Same Files Upload are not allowed");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
   
		var extOne = cibilReportFileOne.substring(cibilReportFileOne.lastIndexOf('.') + 1);
		var extTwo = cibilReportFileTwo.substring(cibilReportFileTwo.lastIndexOf('.') + 1);
		var extThree = cibilReportFileThree.substring(cibilReportFileThree.lastIndexOf('.') + 1);
		
		if(cibilReportFileOne.indexOf('\'')!=-1 ||cibilReportFileTwo.indexOf('\'')!=-1||cibilReportFileThree.indexOf('\'')!=-1)
		{
			alert("File Name contains characrters(\') which are not allowed");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(extOne == "" ||extOne == "txt" || extOne == "doc" ||extOne == "docx"|| extOne == "JPEG" || extOne == "jpeg" || extOne == "JPG" 
			|| extOne == "jpg" || extOne == "xls" || extOne == "xlsx" ||extOne == "pdf" ||extOne == "zip"||extOne == "rar"||extOne == "rtf"||extOne == "pptx"||extOne == "ppt")
		{
			status1=1;
		} 
		else
		{
			status1=0;
		
		}
		
		if(extTwo == "" ||extTwo == "txt" || extTwo == "doc" ||extTwo == "docx"|| extTwo == "JPEG" || extTwo == "jpeg" || extTwo == "JPG" 
			|| extTwo == "jpg" || extTwo == "xls" || extTwo == "xlsx" ||extTwo == "pdf" ||extTwo == "zip"||extTwo == "rar"||extTwo == "rtf"||extTwo == "pptx"||extTwo == "ppt")
		{
			status2=1;
		} 
		else
		{
			status2=0;
			
		}
		
		if(extThree == "" ||extThree == "txt" ||extThree == "doc" ||extThree == "docx"|| extOne == "JPEG" || extThree == "jpeg" || extThree == "JPG" 
			|| extThree == "jpg" || extThree == "xls" || extThree == "xlsx" ||extThree == "pdf" ||extThree == "zip"||extThree == "rar"||extThree == "rtf"||extThree == "pptx"||extThree == "ppt")
		{
			status3=1;
		} 
		else
		{
			status3=0;
			
		}
		if(status1==0||status2==0||status3==0)
		{
			alert("Upload DOC or XLS or PDF or JPEG or JPG  or ZIP or RAR or RTF OR TXT or PPT File only");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		//alert("ok");
		return true;
	}
}

function deleteCibilReport()
{
DisButClass.prototype.DisButMethod();
var ch = document.getElementsByName('chk');
var list="";
var flag=0;
var sourcepath=document.getElementById("path").value;

	
	for(var i=0;i<ch.length;i++)
	{
		
		if((ch[i].checked) == true)
		{
			list = list + ch[i].value + "/";
			flag=1;
		}
	}
		if(flag==0)
		 {
			 alert("Please Select atLeast one Record!!!");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
		 else
		 {
			 if(confirm("Are You Sure to Delete Cibil Report ?"))
			 {
				 
		        document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerDelete.do?method=DeleteDocDetails&list="+list;
		    	document.getElementById("processingImage").style.display = '';
		        document.getElementById("consumermasterform").submit();
		        return true;
			 }
			 else
			 {
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
		 }
	
}


function openDealDetails(val) {
	
	//alert(val);
	var contextPath =document.getElementById('path').value ;
	var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}

function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName("chk");
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

function removeSplChar(id)
{
    var str = id;
    var arr = str.split('+');
    //alert("arr length: "+arr.length);
    var stri = '';
    for(i=0; i<arr.length; i++){
    	if(i<arr.length-1)
    		stri = stri+escape(arr[i])+'%2B';
    	if(i==arr.length-1)
    		stri = stri+escape(arr[i]);
    }   
	return stri;
}

function downLoadCibilReport(fileName,cibilId)
{
	//alert("download File: "+removeSplChar(fileName)+" cibilId: "+cibilId);
	var sourcepath=document.getElementById("path").value;
	document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerDownLoad.do?method=downloadCibilReport&fileName="+removeSplChar(fileName)+"&cibilId="+cibilId;
	document.getElementById("consumermasterform").submit();
	return true;
}


function validateLeadTime()
{
//	 alert("ok");
	var timeStr=document.getElementById('leadTime').value;
	
	var timePat = /^(\d{1,2}):(\d{2})?$/;
	if(timeStr.length<5)
	{
		document.getElementById('leadTime').value='0'+timeStr;
	}
	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('leadTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('leadTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('leadTime').value='';
	return false;
	}

}