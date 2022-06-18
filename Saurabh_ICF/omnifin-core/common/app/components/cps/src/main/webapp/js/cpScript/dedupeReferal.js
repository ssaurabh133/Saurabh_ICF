function dedupeReferSearch()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	var custName=document.getElementById("customerName").value;
	var prod=document.getElementById("product").value!='';
	
	if(document.getElementById("dealNo").value!='' || document.getElementById("customerName").value!='' || document.getElementById("product").value!='' || document.getElementById("scheme").value!='')
	{
		if(custName!='' && custName.length<3)
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else
		{
			document.getElementById("dedupeReferalSearchForm").action=sourcepath+"/dedupeReferalSearch.do?method=searchCustomer";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("dedupeReferalSearchForm").submit();
			return true;
		} 
	}else{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function getDedupeCusData()
{
    DisButClass.prototype.DisButMethod();
	var ch=document.getElementsByName("chk");
	var customerID="";
	var noRecord=0;
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
		{
			customerID=ch[i].value;
			noRecord=1;
			break;
		}
	}
	if(noRecord==0)
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var contextPath = document.getElementById("contextPath").value;

	document.getElementById("dedupeReferalSearch").action=contextPath+"/dedupeReferalSearch.do?method=getDedupeCusData&tarCustID="+customerID;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("dedupeReferalSearch").submit();
		return true;
		}
function radioCheck()
{
	var ch=document.getElementsByName("chk");
	for(var i=0;i<ch.length;i++)
	{
		ch[0].checked == true;
		break;
	}
}


function linkDeal(id,cType,applType)
{
	var appType="";
	if(applType=="APPLICANT")
		appType="PRAPPL";
	else if(applType=="CO APPLICANT")
		appType="COAPPL";
	else if(applType=="GUARANTOR")
		appType="GUARANTOR";
aotherWindows = new Array();
curWinAnother = 0;

if(cType!=''&& cType=='CORPORATE')
{
		cType='C';
} 
else
{
	cType='I';
}     
var uFlag="";
 uFlag = document.getElementById("updateFlag").value;
 
if(cType=='I')
{

var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType;
window.child =window.open(url,'nameCM','height=400,width=1000,top=200,left=250').focus();
aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

return true;
}
else if(cType=='C')
{
var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&status=CE";
window.child =window.open(url,'nameCM','height=420,width=1130,top=200,left=250').focus();
aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

return true;
}
} 
function dealAuthorRemarks()
{
	DisButClass.prototype.DisButMethod();
	var remark=document.getElementById('dedupeRemarks').value;
	var remarklen=remark.length;
	//alert("remark "+remark);
	var decision =document.getElementById('decison').value;
//	alert("decision "+decision);
	var contextPath =document.getElementById('contextPath').value ;
	var ch=document.getElementsByName("chk");
	//alert("ch "+ch);
	var customerID="";
	var noRecord=0;
	var ch1=document.getElementsByName("chk1");
	//alert("ch1 "+ch1);
	var customerID1="";
	var noRecord1=0;
	var cust="";
	if(checkboxInvoice(document.getElementsByName('chk1')))
	    { 
	itable=document.getElementById('matchingtable');
	var len=itable.rows.length;
	for(var i=0;i<len;i++)
	{
		/*if (document.getElementById('chk1'+i).checked==true)
		{	
			customerID1 = customerID1 + document.getElementById('chk1'+i).value+",";
		// alert("c_value"+c_value);	
			noRecord1=i;			   
		}*/
		if(ch1[i].checked == true)
		{	//alert("ch1"+ch1[i]);
			customerID1=ch1[i].value;
			if(cust!="")
			cust=cust+","+customerID1;
			else
			cust=customerID1;
			noRecord1=i;
			//break;
		}
	}
	}

	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
		{	//alert("aass");
			customerID=ch[i].value;
			//alert("customerID "+customerID);
			noRecord=1;
			break;
		}
	}
	if(noRecord==0 )
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(remarklen>1300)
	{
	alert(" maximum length of Remarks is 1300 \n ");
	DisButClass.prototype.EnbButMethod();
	return false;
	}
	if(remark=="" || decision=="" )
	{
	if(remark=="")
	{
	alert("Remarks is Required \n ");
	DisButClass.prototype.EnbButMethod();
	return false;
	}
	if(decision=="")
		{
		alert("Decision is Required \n");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
	}
	else
		{
			//alert("customerID1 "+cust);
	document.getElementById("dedupeReferalSearch").action=contextPath+"/dedupeReferalSearch.do?method=saveDedupeCusData&tarCustID="+customerID+"&matCustId="+cust;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("dedupeReferalSearch").submit();
		return true;
		}
		
}

function checkboxInvoice(chk)
{

	   var zx=0;
	   var flag=0;
	   for(var i=0;i<chk.length;i++)
		{
			
			if(chk[zx].checked==false)
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
function exportData(){
	DisButClass.prototype.DisButMethod();
	var ch=document.getElementsByName("chk");
	var customerID="";
	var noRecord=0;
	for(var i=0;i<ch.length;i++)
	{
		if(ch[i].checked == true)
		{
			customerID=ch[i].value;
			noRecord=1;
			break;
		}
	}
	if(noRecord==0)
	{
		alert("Please select at least one recrod");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	var contextPath = document.getElementById("contextPath").value;
	document.getElementById("dedupeReferalSearch").action=contextPath+"/dedupeReferalSearch.do?method=exportDedupeData&matCustId="+customerID;
	document.getElementById("dedupeReferalSearch").submit();
	DisButClass.prototype.EnbButMethod();
		return true;

}
function dealAuthorforward()
{
	var remark= document.getElementById("dedupeRemark").value;
	if(remark=="0" )
		{
		var contextPath = document.getElementById("contextPath").value;
		document.getElementById("dedupeReferalSearch").action=contextPath+"/dedupeReferalSearch.do?method=ForwordCusData";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("dedupeReferalSearch").submit();
			return true;
		
		}
	else
		{
		alert("Please save all Record then forward!!!!");
		}
}