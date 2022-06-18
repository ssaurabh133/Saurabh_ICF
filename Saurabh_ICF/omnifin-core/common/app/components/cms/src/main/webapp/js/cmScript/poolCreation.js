//manas


$(function() {
	$("#disbursalDate1").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
	});
		});  
	
$(function() {
	$("#maturityDate1").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
	});
		});  
		
		$(function() {
	$("#disbursalDate2").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
	});
		});  
		$(function() {
			$("#cutOffDate").datepicker({
					changeMonth: true,
				changeYear: true,
		        yearRange: '1900:+10',
		        showOn: 'both',
		        buttonImage: document.getElementById("CalImage").value,
				buttonImageOnly: true,
		        dateFormat: document.getElementById("formatD").value,
				defaultDate: document.getElementById("businessdate").value
			});
				}); 
	
$(function() {
	$("#maturityDate2").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
		buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
	});
		});  

function getDateObject(dateString,dateSeperator)
    {
    	//This function return a date object after accepting 
    	//a date string ans dateseparator as arguments
    	var curValue=dateString;
    	var sepChar=dateSeperator;
    	var curPos=0;
    	var cDate,cMonth,cYear;

    	//extract day portion
    	curPos=dateString.indexOf(sepChar);
    	cDate=dateString.substring(0,curPos);
    	
    	//extract month portion				
    	endPos=dateString.indexOf(sepChar,curPos+1);			
    	cMonth=dateString.substring(curPos+1,endPos);

    	//extract year portion				
    	curPos=endPos;
    	endPos=curPos+5;			
    	cYear=curValue.substring(curPos+1,endPos);
    	
    	//Create Date Object
    	dtObject=new Date(cYear,cMonth,cDate);	
    	return dtObject;
    }
function addEditWindow()
{  
	if(document.getElementById("poolID").value=="")
		 
	{
		alert("Please select Pool Id Lov");
		return false;
	}
	else{
	 var basePath=document.getElementById("contextPath").value;
	 var poolID=document.getElementById("lbxPoolID").value;
	 var width  = 1000;
	 var height = 500;
	 var left   = (screen.width  - width)/2;
	 var top    = (screen.height - height)/2;
	 var params = 'width='+width+', height='+height;
	 params += ', top='+top+', left='+left;
	 params += ', directories=no';
	 params += ', location=no';
	 params += ', menubar=no';
	 params += ', resizable=no';
	 params += ', scrollbars=yes';
	 params += ', status=no';
	 params += ', toolbar=no';

	 
    var url=basePath+"/poolCreationProcessAction.do?method=addEditForPool&poolID="+poolID;

   window.open (url,"new",params).focus();
	}
}
function removeRowData(){	
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("poolCreationEditForm").action=basePath+"/poolCreationProcessAction.do?method=removePoolData";
	document.getElementById("poolCreationEditForm").submit();
	
}
function addRowData(alert)
{
	if(document.getElementById("loanAccountNumber").value== ""){
   		
   		alert(alert);   		
   		return false;
   	}
   
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("poolCreationEditForm").action=basePath+"/poolCreationProcessAction.do?method=addRowDataForPool";
	document.getElementById("poolCreationEditForm").submit();
	
}
function removeComma(id)
{
    var str = id;
    var arr = str.split(',');
    var stri = '';
    for(i=0; i<arr.length; i++){
        stri = stri+arr[i];
    }   
    var amount = parseFloat(stri);
	return amount;
}
function generate()
{
	var basePath=document.getElementById("contextPath").value;
	var formatD=document.getElementById("formatD").value;
	var cutOffDate=document.getElementById("cutOffDate").value;
	if(cutOffDate !='')
	{
		var cutOffDateList=document.getElementById("cutOffDateList").value;
		var dateList = cutOffDateList.split('|');
		var stri = '';
		var i=0;
		for(i; i<dateList.length; i++)
		{
			if(dateList[i]==cutOffDate)
				break;
		}
		if(i==dateList.length)
		{
			alert("Invalid Cut Off Date,Only last date of month is valid,which should be greater than or equal to "+dateList[0]);
			return false;
		}
		var businessdate=document.getElementById("businessdate").value;
		var dt1=getDateObject(cutOffDate,formatD.substring(2,3));
		var dt2=getDateObject(businessdate,formatD.substring(2,3));
		if(dt2<dt1)
		{
			alert("Cut Off Date can't be greater than Business Date.");
			return false;
		}	
	}
			
	
	var strconstitution='';
	 var constitution = document.getElementById('constitution');
     for(var count=0; count < constitution.options.length; count++) {
    	strconstitution=strconstitution+constitution.options[count].value+'/';
			}
		 
		 
		document.getElementById('lbxValue').value=strconstitution;
		 

		var strassetCategory='';
		 var assetCategory = document.getElementById('assetCategory');
	     for(var count=0; count < assetCategory.options.length; count++) {
	    	 strassetCategory=strassetCategory+assetCategory.options[count].value+'/';
				}
			 
			document.getElementById('lbxAssetCollId').value=strassetCategory;
			 
	
	
			var strindustry='';
			 var industry = document.getElementById('industry');
		     for(var count=0; count < industry.options.length; count++) {
		    	 strindustry=strindustry+industry.options[count].value+'/';
					}
				 
				document.getElementById('lbxIndustry').value=strindustry;
				 
	
	
				var strsubIndustry='';
				 var subIndustry = document.getElementById('subIndustry');
			     for(var count=0; count < subIndustry.options.length; count++) {
			    	 strsubIndustry=strsubIndustry+subIndustry.options[count].value+'/';
						}
					 
					document.getElementById('lbxSubIndustry').value=strsubIndustry;
					 
	
					
					var strproduct='';
					 var product = document.getElementById('product');
				     for(var count=0; count < product.options.length; count++) {
				    	 strproduct=strproduct+product.options[count].value+'/';
							}
						 
						document.getElementById('lbxProductID').value=strproduct;
						 
			
			
						var strscheme='';
						 var scheme = document.getElementById('scheme');
					     for(var count=0; count < scheme.options.length; count++) {
					    	 strscheme=strscheme+scheme.options[count].value+'/';
								}
							 
							document.getElementById('lbxScheme').value=strscheme;
							 
							var disbursalDate1 = document.getElementById("disbursalDate1").value;	
							var disbursalDate2 = document.getElementById("disbursalDate2").value;
						//	var maturityDate1 = document.getElementById("maturityDate1").value;
							var maturityDate1 = document.getElementById("maturityDate1").value;
							var maturityDate2 = document.getElementById("maturityDate2").value;
							var dt1=getDateObject(disbursalDate1,formatD.substring(2, 3));
						    var dt2=getDateObject(disbursalDate2,formatD.substring(2, 3));
						    var dt3=getDateObject(maturityDate1,formatD.substring(2, 3));
						    var dt4=getDateObject(maturityDate2,formatD.substring(2, 3));
						    var loanAmount1 = removeComma(document.getElementById("loanAmount1").value);
							var loanAmount2 = removeComma(document.getElementById("loanAmount2").value);
							var installmentAmount1 = removeComma(document.getElementById("installmentAmount1").value);   
							var installmentAmount2 = removeComma(document.getElementById("installmentAmount2").value);
							var interestRate1 = removeComma(document.getElementById("interestRate1").value);   
							var interestRate2 = removeComma(document.getElementById("interestRate2").value);
							var amountOutstanding1 = removeComma(document.getElementById("amountOutstanding1").value);   
							var amountOutstanding2 = removeComma(document.getElementById("amountOutstanding2").value);
							var totalInstallments1 = removeComma(document.getElementById("totalInstallments1").value);   
							var totalInstallments2 = removeComma(document.getElementById("totalInstallments2").value);
							var installmentReceived1 = removeComma(document.getElementById("installmentReceived1").value);   
							var installmentReceived2 = removeComma(document.getElementById("installmentReceived2").value);
							var overDueAmount1 = removeComma(document.getElementById("overDueAmount1").value);   
							var overDueAmount2 = removeComma(document.getElementById("overDueAmount2").value);
							var DPD1 = removeComma(document.getElementById("DPD1").value);   
							var DPD2 = removeComma(document.getElementById("DPD2").value);
							
							if(!(disbursalDate1=="")&& !(disbursalDate2=="")){								
							if (dt1>dt2)
							  {
							  alert("disbursalDate2 can not be smaller than disbursalDate1");
							  document.getElementById("save").removeAttribute("disabled");
							  return false;
							  }	
							}
							if(!(maturityDate1=="")&& !(maturityDate2=="")){	
						 if (dt3>dt4)
						  {
						  alert("maturityDate2 can not be smaller than maturityDate1");
						  document.getElementById("save").removeAttribute("disabled");
						  return false;
						  }
                       }
						 if(loanAmount1 > loanAmount2)
							{
								alert("loanAmount2 can not be smaller than loanAmount1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(installmentAmount1) > parseFloat(installmentAmount2))
							{
								alert("installmentAmount2 can not be smaller than installmentAmount1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 
						 if(parseFloat(interestRate1) > parseFloat(interestRate2))
							{
								alert("interestRate2 can not be smaller than interestRate1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(amountOutstanding1) > parseFloat(amountOutstanding2))
							{
								alert("amountOutstanding2 can not be smaller than amountOutstanding1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(totalInstallments1) > parseFloat(totalInstallments2))
							{
								alert("totalInstallments2 can not be smaller than totalInstallments1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(installmentReceived1) > parseFloat(installmentReceived2))
							{
								alert("installmentReceived2 can not be smaller than installmentReceived1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(overDueAmount1) > parseFloat(overDueAmount2))
							{
								alert("overDueAmount2 can not be smaller than overDueAmount1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 if(parseFloat(DPD1) > parseFloat(DPD2))
							{
								alert("DPD2 can not be smaller than DPD1");
								document.getElementById("save").removeAttribute("disabled");
								return false;
							}
						 document.getElementById("save").removeAttribute("disabled");
	document.getElementById("poolCreationSearchForm").action=basePath+"/poolCreationProcessAction.do?method=generatePoolReport";
	document.getElementById("poolCreationSearchForm").submit();
	
}

function search()
{
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("poolCreationSearchForm").action=basePath+"/poolCreationProcessAction.do?method=searchForPool";
	document.getElementById("poolCreationSearchForm").submit();
	
}

function getOK()
{
	var basePath=document.getElementById("contextPath").value;	
	var checkId=document.getElementsByName('checkId');	
    var id="";
    for(var i=0;i<checkId.length;i++){
    if( checkId[i].checked==true){
    id=checkId[i].value;
      }
    }
	//document.getElementById("poolCreationSearchForm").action=basePath+"/poolCreationProcessAction.do?method=showDataForPool&loanNo="+loanNo;
	//document.getElementById("poolCreationSearchForm").submit();
	 window.opener.location.href=basePath+"/poolCreationProcessAction.do?method=showDataForPool&checkId="+id;
     self.close();
	//window.close();
}
function removeRow()
 {
   
    var table = document.getElementById("gridtable");
    var rowCount = table.rows.length;
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

function onSaveOfPool(){

var basePath=document.getElementById("contextPath").value;
if(validatePoolCreationDynaValidatorForm(document.getElementById("poolCreationForm"))){
document.getElementById("poolCreationForm").action = basePath+"/poolCreationProcessAction.do?method=SavePoolData";

document.getElementById("poolCreationForm").submit();
return true;
}   
}

function changeView()
{
	var reportType=document.getElementById("reportType").value;
	if(reportType=='R')
	{
		document.getElementById("poolTypeL").style.display="none";
		document.getElementById("poolTypeV").style.display="none";
		document.getElementById("poolIDL").style.display="";
		document.getElementById("poolIDV").style.display="";
		document.getElementById("instituteIDL").style.display="none";
		document.getElementById("instituteIDV").style.display="none";		
	}
	else
	{
		document.getElementById("poolTypeL").style.display="";
		document.getElementById("poolTypeV").style.display="";
		document.getElementById("poolIDL").style.display="";
		document.getElementById("poolIDV").style.display="";
		document.getElementById("instituteIDL").style.display="";
		document.getElementById("instituteIDV").style.display="";
	}
}
function generatePoolReport()
{ 
 	var contextPath=document.getElementById("contextPath").value;
	var reportType=document.getElementById("reportType").value;
	if(reportType=='R')
	{
		if(document.getElementById("poolID").value== "")
		{	   		
	   		alert("Pool ID is Required.");
	   		document.getElementById("generatePool").removeAttribute("disabled");
	   		return false;
	   	}
	}
	else
	{
		if(document.getElementById("instituteID").value== "")
		{	   		
	   		alert("Institute ID is Required.");
	   		document.getElementById("generatePool").removeAttribute("disabled");
	   		return false;
	   	}
	}
   	document.getElementById("poolCreationForm").action = contextPath+"/poolCreationProcessAction.do?method=generatePool";
    document.getElementById("poolCreationForm").submit();
}
function startPoolProcess()
{	
	DisButClass.prototype.DisButMethod();
	if(document.getElementById("txnfile").value=="NoFile")
	{
		alert("Please upload file to start process.");
		document.getElementById("docFile").focus(); 
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("startProcessing").removeAttribute("disabled");
		return false;
	}
	else
	{
		    var poolID = document.getElementById("poolId").value;
	        alert(document.getElementById("txnfile").value+" In Process..");
	    	var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("poolCreationForm").action=sourcepath+"/poolCreationProcessAction.do?method=startPoolProcess&poolID="+poolID;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("poolCreationForm").submit();
		 	return true;
	}
}

function errorLogPoolDownload()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("poolCreationForm").action=sourcepath+"/poolCreationProcessAction.do?method=errorLogPoolDownload";
	DisButClass.prototype.EnbButMethod();
 	document.getElementById("poolCreationForm").submit();
 	return true;
 		
}

function uploadPoolSummary()
{

	var contextPath = document.getElementById("contextPath").value;
	var url=contextPath+"/poolCreationProcessAction.do?method=uploadPoolSummary";
	window.open(url,'selectCollateral','height=400,width=1000,top=200,left=250,scrollbars=yes');
	return true;

}




//manas