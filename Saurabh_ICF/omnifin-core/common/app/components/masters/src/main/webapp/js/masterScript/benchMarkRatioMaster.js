function newAdd(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("benchmarkRatioSearchForm").action=sourcepath+"//benchMarkRatioAdd.do?method=addBenchMarkRatio";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("benchmarkRatioSearchForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod()
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}


function fnSaveBenchMark(){
	DisButClass.prototype.DisButMethod();
	var benchmarkRatioCode=document.getElementById('benchmarkRatioCode');
	var benchmarkIndustryId=document.getElementById('benchmarkIndustryId');
	var benchmarkRatio=document.getElementById('benchmarkRatio');
	var effectiveDate= document.getElementById("effectiveDate");
	if(trim(benchmarkRatioCode.value) == '' || trim(benchmarkIndustryId.value) == '' || trim(benchmarkRatio.value) == '' || trim(effectiveDate.value) == '' ){
		var msg= '';
		  	if(trim(benchmarkRatioCode.value) == '')
			msg += '* Ratio is required.\n';
			if(trim(benchmarkIndustryId.value) == '')
				msg += '* Industry is required.\n';
			if(trim(benchmarkRatio.value) == '')
				msg += '* BenchMark is required.\n';
			if(trim(effectiveDate.value) == '')
				msg += '* Effective Date is required.\n';
			if(msg.match("Ratio"))
				benchmarkRatioCode.focus();
			else if(msg.match("Industry")){
				benchmarkIndustryId.focus();
			}else if(msg.match("BenchMark")){
				benchmarkRatio.focus();
			}
			else if(msg.match("Effective")){
				effectiveDate.focus();
			}
			
	alert(msg);
	DisButClass.prototype.EnbButMethod();
	return false;
	
		}else{
				document.getElementById("benchmarkRatioAddForm").action="benchMarkRatioAdd.do?method=saveBenchMarkDetails";	
				document.getElementById("processingImage").style.display = '';
				document.getElementById("benchmarkRatioAddForm").submit();
				return true;
			}

     }

function ValidateEffectiveDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	var bDate=document.getElementById("bDate").value;
	var effectiveDate=document.getElementById("effectiveDate").value; 
    var dt1=getDateObject(effectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    if(dt1<dt3)
	{
		msg="Please Enter Effective from date Greater than or equal to bussiness Date";
		document.getElementById("effectiveDate").value='';
	}

	if(msg!='')
	{
		alert(msg);
		return false;
	}
	else
	{
		return true;
	}
	
}

function fnEditBenchMark(){
	var benchmarkRatioSeq=document.getElementById("benchmarkRatioSeq").value;
	DisButClass.prototype.DisButMethod()
	var benchmarkRatioCode=document.getElementById('benchmarkRatioCode');
	var benchmarkIndustryId=document.getElementById('benchmarkIndustryId');
	var benchMarkRatio=document.getElementById('benchmarkRatio');
	var effectiveDate= document.getElementById("effectiveDate");
	if(trim(benchMarkRatio.value) == '' || trim(effectiveDate.value) == ''){
		var msg= '';
		if(trim(benchmarkRatio.value) == '')
			msg += '* BenchMark is required.\n';
		if(trim(effectiveDate.value) == '')
			msg += '* Effective Date is required.\n';
		if(msg.match("BenchMark")){
			benchmarkRatio.focus();
		}
		else if(msg.match("Effective")){
			effectiveDate.focus();
		}
		alert(msg);
		DisButClass.prototype.EnbButMethod();
		return false;
		}else{
			document.getElementById("benchmarkRatioAddForm").action="benchMarkRatioAdd.do?method=updateBenchMarkRatio&benchmarkRatioSeq="+benchmarkRatioSeq;	
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("benchmarkRatioAddForm").submit();
			return true;
			}

}
function fnSearchBenchMark(val){
			
			DisButClass.prototype.DisButMethod();
			var sourcepath=document.getElementById("contextPath").value;
			if(document.getElementById("benchmarkRatioCode").value==""&& document.getElementById("benchmarkIndustryId").value=="")
			{
		     alert(val);

		     DisButClass.prototype.EnbButMethod();
//		    document.getElementById("save").removeAttribute("disabled","true");
			return false; 
			}
			else{
			document.getElementById("benchmarkRatioSearchForm").action=sourcepath+"/benchmarkRatioBehind.do";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("benchmarkRatioSearchForm").submit();
			return true;
			}
	}

function isNumberAndSpecialChar(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 64 &&((charCode > 64 && charCode < 91) || (charCode> 96 && charCode< 123)|| charCode>126))
{
	alert("Only Numeric And Special Charactor Allowed Here");
	return false;
}
	return true;
}
