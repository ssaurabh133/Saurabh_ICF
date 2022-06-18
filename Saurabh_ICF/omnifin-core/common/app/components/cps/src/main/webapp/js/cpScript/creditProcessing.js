var type="";


function checkDateLessThanBusDate()
{
	
	var formatD=document.getElementById("formatD").value;
	var invoiceDate=document.getElementById("invoiceDate").value;
	var dtinvoiceDate=getDateObject(invoiceDate,formatD.substring(2, 3));
	//alert(actualDate);
	var businessDate= document.getElementById("businessdate").value;
	var dtBusiness = getDateObject(businessDate,formatD.substring(2, 3));
	//alert(businessDate);
			if(dtinvoiceDate>dtBusiness)
				{
					alert("Invoice Date cannot be greater than Business Date");
					document.getElementById("invoiceDate").value='';
					document.getElementById("invoiceDate").focus();
					return false;
				}
}
function saveGuaranteeAmount()
{

	var contextPath = document.getElementById('contextPath').value;
	var id=document.getElementById("id").value;
	if(validateGuaranteeAmountDynaValidatorForm(document.getElementById("guaranteeAmountForm")))
	{
		document.getElementById("guaranteeAmountForm").action = contextPath+"/updateGuaranteeAmount.do?method=saveBankAccountAnalysisDetails&id="+id;
		document.getElementById("guaranteeAmountForm").submit();
		return true;
		
	}
	return false;
}

function linkGuaranteeAmount(id,amt)
{
	
		//alert("id"+id);
		//var url="updateGuaranteeAmount.do?method=updateGuaranteeAmount&operation=update&hideId="+id;
		var url="guaranteeAmountBehindAction.do?method=guaranteeAmountBehindAction&hideId="+id+"&amount="+amt;
		mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
		mywindow.moveTo(800,300);
		if (window.focus) {
			mywindow.focus();
			return false;
		}
		return true;
}  	

function checkSanctionBIRR()
{
	var minIRR= document.getElementById('minIRR').value;
	var maxIRR= document.getElementById('maxIRR').value;
	var busIrr =document.getElementById('busIrr').value;
	
	if(minIRR!='')
	{
		minIRR=removeComma(minIRR);
	}
	else
	{
		minIRR=0;
	}
	if(maxIRR!='')
	{
		maxIRR=removeComma(maxIRR);
	}
	else
	{
		maxIRR=0;
	}
	
	if(busIrr!='')
	{
		busIrr=removeComma(busIrr);
	}
	else
	{
		busIrr=0;
	}
	//alert("busIrr: "+busIrr+" minIRR "+minIRR+" maxIRR "+maxIRR);
	
	if(!(busIrr>=minIRR && busIrr<=maxIRR))
	{
		alert('Sanctioned Business IRR must be between'+ minIRR+ ' and '+maxIRR);
		//document.getElementById("busIrr").value='';
		//document.getElementById("busIrr").focus();
		return false;
	}
	else
	{
		return true;
	}
	
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
function parent_disable() {
if( window.child && ! window.child.closed)
	 window.child.focus();
}


function getRequestObject() {
	  if (window.ActiveXObject) { 
		return(new ActiveXObject("Microsoft.XMLHTTP"));
	  } else if (window.XMLHttpRequest) {
		return(new XMLHttpRequest());
	  } else {
		return(null);
	  }
	}

function trim(str) {
return ltrim(rtrim(str));
}
function isWhitespace(charToCheck) {
var whitespaceChars = " \t\n\r\f";
return (whitespaceChars.indexOf(charToCheck) != -1);
}

function ltrim(str) { 
for(var k = 0; k < str.length && isWhitespace(str.charAt(k)); k++);
return str.substring(k, str.length);
}
function rtrim(str) {
for(var j=str.length-1; j>=0 && isWhitespace(str.charAt(j)) ; j--) ;
return str.substring(0,j+1);
}

function getProduct(status){
	//alert("In getProduct"+status);
	if(status=='p1')
	{
		var productType =document.getElementById('productType1').value ;
	}
	if(status=='p2')
	{
		var productType =document.getElementById('productType2').value ;
	}
	if(status=='p3')
	{
		var productType =document.getElementById('productType3').value ;
	}
	var contextPath =document.getElementById('contextPath').value ;
	// alert("contextPath"+contextPath);
     if (productType!= '') {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getProductDetail";
		var data = "productType=" + productType;
		// alert("address: "+address+"data: "+data);
		send_product(address, data, status);
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function send_product(address, data, status) {
	// alert("send_countryDetail"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_product(request,status);
	};
	// alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_product(request,status){
   // alert(status)
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		// alert(str);
		var s1 = str.split("$:");
		// alert(s1);
	  // if(str.length>0)
	  // {
	    	// alert(trim(str[0]));
		if(status=='p1')
		{
		// alert(s1);
			document.getElementById('productDetails1').innerHTML = s1[0];
		}
		if(status=='p2')
		{
			// alert(s1);
			document.getElementById('productDetails2').innerHTML = s1[1];
		}
		if(status=='p3')
		{
			// alert(s1);
			document.getElementById('productDetails3').innerHTML = s1[2];
		}
	    	
	    	
	// }
	}
}


function getSheme(){
	// alert("In getSheme");
	
		var product =document.getElementById('product').value ;

	var contextPath =document.getElementById('contextPath').value ;
	// alert("contextPath"+contextPath);
     if (product!= '') {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getSchemeDetail";
		var data = "product=" + product;
		// alert("address: "+address+"data: "+data);
		send_Sheme(address, data);
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function send_Sheme(address, data) {
	// alert("send_countryDetail"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_Sheme(request);
	};
	// alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_Sheme(request){
   // alert(status)
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		// alert(str);
		// var s1 = str.split("$:");
		// alert(s1);
	  // if(str.length>0)
	  // {
	    	// alert(trim(str[0]));
		
	
			document.getElementById('schemeDetails').innerHTML = str;
		
		
	    	
	    	
	// }
	}
}

function infoCall(val)
{
	//alert("ok"+val);	
	document.getElementById("product_seq").value=val;
	document.getElementById("loanInfo").style.display='';
}
function saveLoanDetails()
{
	var contextPath =document.getElementById('contextPath').value ;
	//alert("ok");
	 if(validateLoanDetailsDynaValidatorForm(document.getElementById("loanDetailForm")))
     {
	     document.getElementById("loanDetailForm").action = contextPath+"/loandetails.do";
	     document.getElementById("loanDetailForm").submit();
		 return true;
	  }
	return false;
}


// To insert new row
function addnewRow()
{
	if(confirm("Are you sure to insert a row into table ? ? ?"))
	{
		var tbl = document.getElementById("requestLoanDetailTable");
		var lastElement = tbl.rows.length;
		var newRow = tbl.insertRow(lastElement);
		newRow.setAttribute('className','white');
		newRow.setAttribute('class','white');
		var newCell = newRow.insertCell(0);
		newCell.innerHTML = '<input type="checkbox" name="chooseProduct" id="chooseProduct" />'
		var newCell = newRow.insertCell(1);
		// alert("after first new cell");
		newCell.innerHTML = '<select name="productType" id="productType" class="sel_width" onclick="return getProduct();"><option value="" >--Select--</option><logic:present name="productTypeList"><logic:iterate name="productTypeList" id="subproductList"><option value="<bean:write name="subproductList" property="id" />" > <bean:write name="subproductList" property="name" /></option></logic:iterate></logic:present></select>' 
             	  	

		var newCell = newRow.insertCell(2);
		// alert("after second new cell");
		
		newCell.innerHTML =	'<div id="productDetails1"><select name="product" id="product" class="sel_width"><option value="">---Select---</option>	</select> </div>'
		var newCell = newRow.insertCell(3);
		// alert("after second new cell");
		newCell.innerHTML = '<select name="scheme" id="scheme" style="width: 90px"><option value="11">11</option></select>'
		var newCell = newRow.insertCell(4);
		// alert("after second new cell");
		newCell.innerHTML = '<select name="promotion" id="promotion" style="width: 90px"><option value="1">abc</option></select>'
		var newCell = newRow.insertCell(5);
		// alert("after second new cell");
		newCell.innerHTML = '<input class="text3" id="loanAmount" maxlength="100" size="5" name="loanAmount" value="5000000" onkeypress="return isNumberKey(event);" />'
		
		var newCell = newRow.insertCell(6);
		// alert("after second new cell");
		newCell.innerHTML = '<input class="text3" id="loanTenure" maxlength="100" size="5" name="loanTenure" value="30" onkeypress="return isNumberKey(event);" />'
		
		var newCell = newRow.insertCell(7);
		// alert("after second new cell");
		newCell.innerHTML = '<input name="loanPurpose" id="loanPurpose" type="text" size="5" class="text3" value="Machine Purchase" />'
		
		return true;
	}
	else
	{
		return false;
	}
}

// To remove a row
function removeaddedRow()
{
	
		if(chooseProductCheckboxCheck()==1)
		{
	  		if(confirm("Are you sure to remove row from table ? ? ?"))
	    	{
	 		var table = document.getElementById("requestLoanDetailTable");
    		var rowCount = table.rows.length;
    		for(var i=0; i<rowCount; i++) 
    		{
     			var row = table.rows[i];
     			var chkbox = row.cells[0].childNodes[0];
     			if(null != chkbox && true == chkbox.checked) 
     			{
         			table.deleteRow(i);
         			rowCount--;
         			i--;
     			}
         	}
   			return false;
	 		}
	 		else
	 		{
	 			return false;
	 		}

		}
		else
		{
		    alert("Select atleast one row!!!");
			return false;
		}
		
 }

// To check the checkbox, whether it is check or not
function checkboxCheck(ch)
{

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

function checkboxCollateral(ch,confirm)
{

	if(confirm=='N'||confirm=='')
	{
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
	}
	else
	{
		flag=1;
	}
	return flag;
}

function isNumberKey(evt) 
{

var charCode = (evt.which) ? evt.which : event.keyCode;

if (charCode > 31 && (charCode < 48 || charCode > 57))
{
	alert("Only numeric allowed here");
	return false;
}
	return true;
}


//function saveLeadEntryDetails()
//{
//	 
//	// alert("time: ");
//	 // IsValidTime(time);
//	
//	        var leadMValue=document.getElementById('leadMValue').value;
//			if(leadMValue=='Y')
//			{
//				  var leadNo=document.getElementById('leadNo').value;
//				  if(leadNo!='')
//				  {
//					  var contextPath =document.getElementById('contextPath').value ;
//					  
//						 if(validateLeadEntryProcessDynaValidator(document.getElementById("sourcingForm")))
//						 {
//							  if(document.getElementById('source').value=='VENDOR' && document.getElementById('vendorCode').value=='')
//							 {
//								  alert("Please Insert Vendor Code");
//								  document.getElementById("searchButton").removeAttribute("disabled","true");
//								  return false;
//							 }
//							  else
//							  {
//							     document.getElementById("sourcingForm").action = contextPath+"/leadProcessAction.do";
//							     //document.getElementById("processingImage").style.display='block';
//							     document.getElementById("sourcingForm").submit();
//								 return true;
//							  }
//							  
//						 }
//						 else
//						 {
//							 document.getElementById("searchButton").removeAttribute("disabled","true");
//							 return false;
//						 }
//				  }
//				  else
//				  {
//					  alert("Please select Lead No");
//					  document.getElementById("searchButton").removeAttribute("disabled","true");
//					  return false;
//				  }
//			}
//			else
//			{
//				var contextPath =document.getElementById('contextPath').value ;
//				  
//				if(validateLeadEntryProcessDynaValidator(document.getElementById("sourcingForm")))
//				  {
//					  if(document.getElementById('source').value=='VENDOR' && document.getElementById('vendorCode').value=='')
//					 {
//						  alert("Please Insert Vendor Code");
//						  document.getElementById("searchButton").removeAttribute("disabled","true");
//						  return false;
//					 }
//					  else
//					  {
//					     document.getElementById("sourcingForm").action = contextPath+"/leadProcessAction.do";
//					     //document.getElementById("processingImage").style.display='block';
//					     document.getElementById("sourcingForm").submit();
//						 return true;
//					  }
//					  
//
//				    }
//
//				 else
//				 {
//					 document.getElementById("searchButton").removeAttribute("disabled","true");
//					 return false;
//				 }
//			}
//		     
//
//}
//
//function saveForwardLeadEntryDetails()
//{
//	//alert("ok In saveForwardLeadEntryDetails");
//	var dealId =document.getElementById('dealId').value ;
//	if(dealId!='')
//	{
//		var contextPath =document.getElementById('contextPath').value ;
//		document.getElementById("sourcingForm").action = contextPath+"/stageMoveBehindAction.do";
//	    document.getElementById("sourcingForm").submit();
//	}
//	else
//	{
//		alert("You can't move without saving before Deal Details.");
//	}
//	
//}

function closeLOVWindow()
{
if (openLOVWindows && !openLOVWindows.closed) 
    	openLOVWindows.close();
}  

function setText(text1,text2) {
    // alert("ok"+text2);
    // document.getElementById('customerId').value = text1;
   // document.getElementById('customerName').value = text2;
  
}

//function saveLoanDetail()
//{
//
//	
//			  var marginAmt = 0.00;
//			  var loanAmt =0.00;
//			  var assetCost =0.00;
//	          var productTypeFlag = document.getElementById("productTypeFlag").value; 
//	          //alert("productTypeFlag: "+productTypeFlag);
//
//			  var productTypeFlag = document.getElementById("productTypeFlag").value; 
//
//	          var repaymentType = document.getElementById("repaymentType").value; 
//	          //alert("repaymentType  :  "+repaymentType);
//	          var rate = document.getElementById("rateType").value;
//	          
//	          	var repayEffectiveDate = document.getElementById("repayEffectiveDate").value;
//	      		var nextDueDate = document.getElementById("nextDueDate").value;
//	      		var formatD = document.getElementById("formatD").value;
//	      		var repayDate = getDateObject(repayEffectiveDate, formatD.substring(2, 3));
//	      		var nextDate = getDateObject(nextDueDate, formatD.substring(2, 3));
//		            	var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='';
//	            		
//	            		if((rate!='' && rate=='E')&&((document.getElementById("type1").checked)==false))
//	            		{
//	            			msg1="* Rate Method is Required \n";
//	            		}
//	            		if((rate!='' && rate=='E')&&((document.getElementById("baseRateType").value)==''))
//	            		{  
//	            			 msg2="* Base Rate Type is Required\n";
//	            		}
//	            		if((rate!='' && rate=='E')&&((document.getElementById("baseRate").value)==''))
//	            		{  
//	            			 msg3="* Base Rate is Required\n";
//	            		}
//	            		if((document.getElementById("markUp").value)=='')
//	            		{  
//	            			 msg4="* Mark Up is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&(productTypeFlag!='' && productTypeFlag!='N')&&((document.getElementById("assetCost").value)==''))
//	            		{  
//	            			 msg5="* Asset Cost is Required\n";
//	            			 
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&(productTypeFlag!='' && productTypeFlag!='N')&& ((document.getElementById("marginPerc").value)==''))
//	            		{ 
//	            			 msg6="* Margin Percentage is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&& (productTypeFlag!='' && productTypeFlag!='N')&& ((document.getElementById("marginAmount").value)==''))
//	            		{ 
//	            			 msg7="* Margin Amount is Required\n";
//	            		}
//	            	            		
//	            		if((repaymentType!='' && repaymentType=='I')&&((document.getElementById("repaymentType").value)==''))
//	            		{
//	            			msg8="* Repayment Type is Required \n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&((document.getElementById("installmentType").value)==''))
//	            		{  
//	            			 msg9="* Installment Type is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&((document.getElementById("frequency").value)==''))
//	            		{  
//	            			 msg10="* Frequency is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&((document.getElementById("noOfInstall").value)==''))
//	            		{  
//	            			 msg11="* No of Installments is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&&((document.getElementById("paymentMode").value)==''))
//	            		{  
//	            			 msg12="* Re-Payment Mode is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I')&& ((document.getElementById("cycleDate").value)==''))
//	            		{ 
//	            			 msg13="* Due Day is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I'))
//	            		{ 
//	            			if(document.getElementById("nextDueDate").value == '')
//	            			   msg14="* Next Due Date is Required\n";
//	            		}
//	            		if((repaymentType!='' && repaymentType=='I'))
//	            		{ 
//	            			if(document.getElementById("repayEffectiveDate").value == '')
//	            			  msg15="* Repayment Effective Date is Required\n";
//	            		}
//	            		
//	            		if(repayDate > nextDate)
//	            		{ 
//	            			msg16="* Repayment Effective Date should be less then Next Due Date\n";
//	            		}
//	            		     var contextPath =document.getElementById('contextPath').value ;	        			  
//	        				 if(validateLoanDetailsDynaValidatorForm(document.getElementById("LoanDetailsForm")))
//	        				 {
//	        					 if(msg1!=''||msg2!=''||msg3!=''||msg4!=''||msg5!=''||msg6!=''||msg7!=''||msg8!=''||msg9!=''||msg10!=''||msg11!=''||msg12!=''||msg13!=''||msg14!=''||msg15!=''||msg16!='')
//	     	            		 {
//	        						 alert(msg1+msg2+msg3+msg4+msg5+msg6+msg7+msg8+msg9+msg10+msg11+msg12+msg13+msg14+msg15+msg16);
//	        						 document.getElementById("Save").removeAttribute("disabled","true");
//	        					 }
//	        					 else
//	        					  {
//	        						 if((productTypeFlag!='' && productTypeFlag=='A')&&((document.getElementById("assetCost").value)!=''))
//	        		            	 {  
//	        							
//		        							marginAmt =removeComma(document.getElementById("marginAmount").value);
//		 	     	            			loanAmt =removeComma(document.getElementById("requestedLoanAmount").value);
//		 	     	            			assetCost =removeComma(document.getElementById("assetCost").value);
//		 	     	            			
//		 	     	            			if((marginAmt+loanAmt) > assetCost)
//		 	     	            			{
//		 	     	            				alert("Sum of Margin Amount and Loan Amount is greater than Asset Cost");
//		 	     	            				document.getElementById("Save").removeAttribute("disabled","true");
//		 	     	            				return false;
//		 	     	            			}
//	        		            			 
//	        		            	 }
//	        						 
//	        					     document.getElementById("LoanDetailsForm").action = contextPath+"/loanProcessAction.do?method=saveLoan";
//	        					     document.getElementById("LoanDetailsForm").submit();
//	        					     return true;
//	        					   }
//	        					
//	        				  }
//	        				 else
//	        				 {
//	        					 document.getElementById("Save").removeAttribute("disabled","true");
//	        					 return false;
//	        				 }
//
//}


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

function assetORcollat()
{
	var assetCollateralType=document.getElementById('assetCollateralType').value;

	if(assetCollateralType=='ASSET')
	{
        document.getElementById("assetCollateral").removeAttribute("disabled", "true");
		document.getElementById('assetClass').removeAttribute("disabled", "true");
		document.getElementById('collateralClass').setAttribute("disabled", "true");
		document.getElementById('collateralClass').value='';
		document.getElementById("forAsset").style.display='';
		document.getElementById("forAll").style.display='none';
		type="A";
	 
	}
	else if(assetCollateralType=='COLLATERAL')
	{
		
        document.getElementById("assetCollateral").removeAttribute("disabled", "true");
	//    document.getElementById('collateralClass').removeAttribute("disabled", "true");			
		document.getElementById('assetClass').setAttribute("disabled", "true");	
        document.getElementById('assetClass').value='';		
		document.getElementById("forAsset").style.display='none';
		document.getElementById("forAll").style.display='';
		type="C";
	 
	}
}

function existingAssetCollateral(type)
{
	var assetCollateral= document.getElementById("assetCollateral").value;
	var assetCollateralType= document.getElementById("assetCollateralType").value;
	//alert(assetCollateral);

	if(assetCollateral=='N')
	{
		//document.getElementById("assetCollateralType").removeAttribute("disabled", "true");
		if(assetCollateralType=='')
		{
			alert("First select the Asset/CollateralType.");
			document.getElementById("assetCollateral").value='';
			return false;
		}
		else
		{
			document.getElementById('assetClass').setAttribute("disabled", "true");
			document.getElementById('collateralClass').setAttribute("disabled", "true");
			document.getElementById('collateralClass').removeAttribute("disabled", "true");
		}
	
	}
	
	else if(assetCollateral=='E')
	{
		if(assetCollateralType=='')
		{
			alert("First select the Asset/CollateralType.");
			document.getElementById("assetCollateral").value='';
			return false;
		}
		else
		{
		document.getElementById("assetCollateralType").value=''
      //  document.getElementById("assetCollateralType").setAttribute("disabled", "true");
        document.getElementById('assetClass').value='';
		document.getElementById('assetClass').setAttribute("disabled", "true");
		document.getElementById('collateralClass').value='';
		document.getElementById('collateralClass').setAttribute("disabled", "true");
		var custID=document.getElementById("dealCustomerId").value;
		
		var contextPath =document.getElementById('contextPath').value ;
		
			
		var url=contextPath+"/existAssetsActionBehind.do?method=openExistingAsset&openTyp="+type+"&custID="+custID;
		//alert("url "+url);
//		    document.getElementById("dealNo").value=''
//			document.getElementById("lbxDealNo").value=''
//			document.getElementById("loanNo").value=''
//			document.getElementById("lbxLoanNoHID").value=''
//			document.getElementById("collateralId").value=''
//			document.getElementById("lbxCollateralId").value=''
//			document.getElementById("customerName").value=''
		 window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
	}   
	}
	
}

function assetsClass()
{
	  var contextPath =document.getElementById('contextPath').value ;
	  var assetCollateralType= document.getElementById("assetCollateralType").value;
//Prashant
	  var productCat=document.getElementById("productCat").value;
	//  alert(contextPath);
	if(assetCollateralType=='')
	{
		alert("First select the Asset/CollateralType.");
		document.getElementById("assetCollateral").value='';
		return false;
	}
	else
	{
	if(document.getElementById("assetClass").value!='')
	{
		assetClass=document.getElementById("assetClass").value;
		
		
	}
	else
	{
		assetClass=document.getElementById("collateralClass").value;
	}
	//alert("assetClass"+assetClass);
	var assetCollateralType= document.getElementById("assetCollateralType").value;
	
	//alert("assetClass: "+assetClass+"assetCollateralType: "+assetCollateralType);
	if(assetClass=='MACHINE')
	{
		
		var url=contextPath+"/JSP/CPJSP/collateralMachine.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
		window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );

	}
	else if(assetClass=='PROPERTY')
	{
//		var url=contextPath+"/JSP/CPJSP/collateralProperty.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
		var url=contextPath+"/collateralBehindAction.do?method=openPropertyPopup&assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	    window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );



	}
	else if(assetClass=='VEHICLE')
	{
		var lov="";
		if(type=="A")
		lov="Y";
		else
		lov="C";	
			
	   var basePath=document.getElementById("contextPath").value;
		otherWindows[curWin++] =window.open(basePath+"/collateralBehindAction.do?method=openACPopup&assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"&printLov="+lov+"&productCat="+productCat,'viewReceivable',"height=300,width=900,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");

	}
   else if(assetClass=='OTHERS')
    {
	 
		var url=contextPath+"/JSP/CPJSP/collateralAssetOther.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
		window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
	
    }
   else if(assetClass=='FD')
   {
	   var url=contextPath+"/JSP/CPJSP/collateralFD.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );

   }
   else if(assetClass=='SBLC')
   {
	   var url=contextPath+"/JSP/CPJSP/collateralSBLC.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
	   

   }
   else if(assetClass=='SECURITIES')
   {
	   var url=contextPath+"/JSP/CPJSP/collateralSecurity.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	  window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
	   
	   
   }
   else if(assetClass=='STOCK')
   {
	   var url=contextPath+"/JSP/CPJSP/collateralStock.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
   }
   else if(assetClass=='DEBTOR')
   {
	   var url=contextPath+"/JSP/CPJSP/collateralDebtor.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
	   
   }
   else if(assetClass=='BG')
   {
	   var url=contextPath+"/JSP/CPJSP/bankG.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
	   
   }
   else if(assetClass=='INSURANCE'){
	   var url=contextPath+"/collateralBehindAction.do?method=openInsurancePopup&assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	    window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );

//	   var url=contextPath+"/JSP/CPJSP/insurance.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
//	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
   }
   else if(assetClass=='INVOICE')
   {
	   var url=contextPath+"/JSP/CPJSP/invoiceAsset.jsp?assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	   window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );

   }
   else if(assetClass=='GOLD')
   {
	   	var url=contextPath+"/collateralBehindAction.do?method=openGoldPopup&assetClass="+assetClass+"&assetCollateralType="+assetCollateralType+"";
	    window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' ); 
	   
 }
 }
}

function openPopUpCollateral(collateralClass,collateralId,dealId,verificationId)
{
	var contextPath =document.getElementById('contextPath').value ;
	otherWindows = new Array();
	curWin = 0;
	//alert(contextPath);
//	alert("collateralClass: "+collateralClass);
//	alert("collateralId: "+collateralId);
//	alert("dealId: "+dealId);
//	alert("verificationId: "+verificationId);
	
	if(collateralClass=='MACHINE')
	{
		var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
		//alert(url);
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingMachine.jsp";
		otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
	else if(collateralClass=='PROPERTY')
	{
		var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingProperty.jsp";
		otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
	else if(collateralClass=='VEHICLE')
	{
		var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingVehicle.jsp";
		otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
   else if(collateralClass=='STOCK')
   {
	   var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
//	   var url=contextPath+"/JSP/CPJSP/collateralCapturingStock.jsp";
	   otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
   else if(collateralClass=='OTHERS' || collateralClass=='BG' || collateralClass=='DEBTOR'
	   || collateralClass=='FD' || collateralClass=='SBLC' || collateralClass=='SECURITIES')
   {
	   var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingAssetOther.jsp";
	   otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
else if(collateralClass=='GOLD')
   {
	   var url=contextPath+"/collateralCapturing.do?method=openCollateralPopup&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId;
//	   var url=contextPath+"/JSP/CPJSP/collateralCapturingStock.jsp";
	   otherWindows[curWin++] =window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
}

function openPopUpCollateralCompletion(collateralClass,collateralId,dealId,verificationId,Status)
{
	var contextPath =document.getElementById('contextPath').value ;
	//alert(contextPath);
//	alert("collateralClass: "+collateralClass);
//	alert("collateralId: "+collateralId);
//	alert("dealId: "+dealId);
//	alert("verificationId: "+verificationId);
	otherWindows = new Array();
	curWin = 0;
	
	if(collateralClass=='MACHINE')
	{
		var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
		//alert(url);
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionMachine.jsp";
		otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
	else if(collateralClass=='PROPERTY')
	{
		var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionProperty.jsp";
		otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
	else if(collateralClass=='VEHICLE')
	{
		var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
			+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionVehicle.jsp";
		otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
	}
   else if(collateralClass=='STOCK')
   {
	   var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
//	   var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionStock.jsp";
	   otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
   else if(collateralClass=='OTHERS' || collateralClass=='BG' || collateralClass=='DEBTOR'
	   || collateralClass=='FD' || collateralClass=='SBLC' || collateralClass=='SECURITIES')
   {
	   var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
//		var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionAssetOther.jsp";
	   otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
    else if(collateralClass=='GOLD')
   {
	   var url=contextPath+"/collateralCapturingCompletion.do?method=openCollateralPopupCompletion&collClass="+collateralClass
	   	+"&collId="+collateralId+"&dealId="+dealId+"&verificationId="+verificationId+"&Status="+Status;
//	   var url=contextPath+"/JSP/CPJSP/collateralCapturingCompletionStock.jsp";
	   otherWindows[curWin++]=window.open(url,'name'+collateralClass,'height=300,width=900,top=200,left=250,scrollbars=yes' );
   }
}




function saveMachineVerificationDetails()
{   
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	var appraisalDate = document.getElementById("appraisalDate").value;
	var businessDate = document.getElementById("reportDate").value;
	var formatD = document.getElementById("formatD").value;
	var appDt = getDateObject(appraisalDate, formatD.substring(2, 3));
	var busDt = getDateObject(businessDate, formatD.substring(2, 3));
	
	 if(validateCollateralCapturingMachineDynaValidatorForm(document.getElementById("machineForm")))
	 {
		 if(appDt>busDt)
		 {
			 alert("Appraisal cannot be greater than Business Date");
			 document.getElementById("appraisalDate").value='';
			 document.getElementById("appraisalDate").focus();
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else
		 {
		    document.getElementById("machineForm").action = contextPath+"/collateralMachineVerification.do?method=saveMachineVerificationDetails";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("machineForm").submit();
		 	return true;
		 }
	}
	else
	{	
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
 	    return false;
	}
}

function savePropertyVerificationDetails()
{   
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	var appraisalDate = document.getElementById("appraisalDate").value;
	var businessDate = document.getElementById("reportDate").value;
	var formatD = document.getElementById("formatD").value;
	var appDt = getDateObject(appraisalDate, formatD.substring(2, 3));
	var busDt = getDateObject(businessDate, formatD.substring(2, 3));
	
	 if(validateCollateralCapturingPropertyDynaValidatorForm(document.getElementById("propertyForm")))
	 {
		 if(appDt>busDt)
		 {
			 alert("Appraisal cannot be greater than Business Date");
			 document.getElementById("appraisalDate").value='';
			 document.getElementById("appraisalDate").focus();
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else
		 {
		    document.getElementById("propertyForm").action = contextPath+"/collateralPropertyVerification.do?method=savePropertyVerificationDetails";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("propertyForm").submit();
		 	return true;
		 }
	}
	else
	{	
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
 	    return false;
	}
}

function saveVehicleVerificationDetails()
{   
	//alert("ok");
		DisButClass.prototype.DisButMethod();
		
	 var contextPath= document.getElementById("contextPath").value;
	 var appraisalDate = document.getElementById("appraisalDate").value;
	 var businessDate = document.getElementById("reportDate").value;
	 var formatD = document.getElementById("formatD").value;
	 var appDt = getDateObject(appraisalDate, formatD.substring(2, 3));
	 var busDt = getDateObject(businessDate, formatD.substring(2, 3));
	 
	 if(validateCollateralCapturingVehicleDynaValidatorForm(document.getElementById("vehicleForm")))
	 {
		 if(appDt>busDt)
		 {
			 alert("Appraisal cannot be greater than Business Date");
			 document.getElementById("appraisalDate").value='';
			 document.getElementById("appraisalDate").focus();
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else
		 {
		    document.getElementById("vehicleForm").action = contextPath+"/collateralVehicleVerification.do?method=saveVehicleVerificationDetails";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("vehicleForm").submit();
		 	return true;
		 }
	}
	else
	{	
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
 	    return false;
	}
}

function saveStockVerificationDetails()
{   
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	 var contextPath= document.getElementById("contextPath").value;
	 var appraisalDate = document.getElementById("appraisalDate").value;
	 var businessDate = document.getElementById("reportDate").value;
	 var formatD = document.getElementById("formatD").value;
	 var appDt = getDateObject(appraisalDate, formatD.substring(2, 3));
	 var busDt = getDateObject(businessDate, formatD.substring(2, 3));
	 
	 if(validateCollateralCapturingStockDynaValidatorForm(document.getElementById("stockForm")))
	 {
		 if(appDt>busDt)
		 {
			 alert("Appraisal cannot be greater than Business Date");
			 document.getElementById("appraisalDate").value='';
			 document.getElementById("appraisalDate").focus();
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else
		 {
		    document.getElementById("stockForm").action = contextPath+"/collateralStockVerification.do?method=saveStockVerificationDetails";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("stockForm").submit();
		 	return true;
		 }
	}
	else
	{	
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
 	    return false;
	}
}

function upperMe(compId) { 
    document.getElementById(compId).value = document.getElementById(compId).value.toUpperCase(); 
    return true;
}	

function saveOtherVerificationDetails()
{   
	DisButClass.prototype.DisButMethod();
	//alert("ok");
	var contextPath= document.getElementById("contextPath").value;
	var appraisalDate = document.getElementById("appraisalDate").value;
	var businessDate = document.getElementById("reportDate").value;
	var formatD = document.getElementById("formatD").value;
	var appDt = getDateObject(appraisalDate, formatD.substring(2, 3));
	var busDt = getDateObject(businessDate, formatD.substring(2, 3));
	
	 if(validateCollateralCapturingOtherDynaValidatorForm(document.getElementById("othersForm")))
	 {
		 if(appDt>busDt)
		 {
			 alert("Appraisal cannot be greater than Business Date");
			 document.getElementById("appraisalDate").value='';
			 document.getElementById("appraisalDate").focus();
			 document.getElementById("save").removeAttribute("disabled","true");
			 DisButClass.prototype.EnbButMethod();
		 	 return false;
		 }
		 else
		 {
		    document.getElementById("othersForm").action = contextPath+"/collateralOtherVerification.do?method=saveOtherVerificationDetails";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("othersForm").submit();
		 	return true;
		 }
	}
	else
	{	
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
 	    return false;
	}
}

function forwardCollateralCapturingData(val)
{
	DisButClass.prototype.DisButMethod();
	//alert("value is:---"+val);
	var contextPath= document.getElementById("contextPath").value;
	var dealId = document.getElementById("dealId1").value;
	var verificationId = document.getElementById("verificationId1").value;
	document.getElementById("collateralForm").action = contextPath+"/collateralCapturing.do?method=forwardCollateralCapturingData&dealId="+dealId+"&verificatonId="+val;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("collateralForm").submit();
	DisButClass.prototype.EnbButMethod();
	return true;
}
	
//function saveMachineDetails()
//{   
//	//alert("ok");
//	var machineSupplier= document.getElementById("machineSupplier").value;
//	var supplierDesc=document.getElementById("supplierDesc").value
//	
//	var assetManufact= document.getElementById("assetManufact").value;
//	var assetManufactDesc=document.getElementById("assetManufactDesc").value
//	
//	var contextPath= document.getElementById("contextPath").value;
//	 if(validateCollateralMachineryDynaValidatorForm(document.getElementById("MachineForm")))
//	 {	
//		 if((machineSupplier==''&& supplierDesc!='')||(machineSupplier!=''&& supplierDesc!=''))
//		 {
//			 if((assetManufact==''&& assetManufactDesc!='')||(assetManufact!=''&& assetManufactDesc!=''))
//			 {
//				 if(document.getElementById("assetsIdMachine").value!='')
//				{
//					
//					var primaryId = document.getElementById("assetsIdMachine").value;	
//					
//				    var assetsType = document.getElementById("ass1").value;		
//				    //alert("primaryId: "+primaryId+"assetsType: "+assetsType);
//				    document.getElementById("MachineForm").action = contextPath+"/collateralMachineryProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
//				    document.getElementById("MachineForm").submit();
//				 	return true;
//				}
//				else
//				{	
//					document.getElementById("MachineForm").action = contextPath+"/collateralMachineryProcessAction.do?method=saveCollateralDetails";
//					document.getElementById("MachineForm").submit();
//			 	     return true;
//				}
//			 }
//			 else
//			 {
//				 alert("Please Select Lov or Enter the Manufacturer Name");
//			 }
//	   }
//		 else
//		 {
//			 alert("Please Select Lov or Enter the Supplier Name");
//		 }
//	 }
//}


//function savePropertyDetails()
//{  
//	var contextPath= document.getElementById("contextPath").value;
//	 if(validateCollateralPropertyDynaValidatorForm(document.getElementById("PropertyForm")))
//	 {
//	
//	if(!(document.getElementById("assetsIdProperty").value)=='')
//	{
//		var primaryId = document.getElementById("assetsIdProperty").value;	    
//	    var assetsType = document.getElementById("p2").value;	  
//		document.getElementById("PropertyForm").action=contextPath+"/collateralPropertyProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
//	 	document.getElementById("PropertyForm").submit();
//	 	return true;
//	}
//	else
//	{
//	
//	document.getElementById("PropertyForm").action=contextPath+"/collateralPropertyProcessAction.do?method=saveCollateralDetails";
// 	document.getElementById("PropertyForm").submit();
// 	return true;
//	}
//	}
//}


//function saveVehicleDetails()
//{ 
//	//alert("ok");
//
//	var contextPath= document.getElementById("contextPath").value;
//
//	if(validateCollateralVehicleDynaValidatorForm(document.getElementById("VehicleForm")))
//	{
//	
//       if(document.getElementById("assetsIdVehicle").value!='')
//       {
//		var primaryId = document.getElementById("assetsIdVehicle").value;
//	
//	    var assetsType = document.getElementById("v2").value;	
//		document.getElementById("VehicleForm").action=contextPath+"/collateralVehicleProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
//	 	document.getElementById("VehicleForm").submit();
//	 	return true;
//	  }
//	 else
//	  {		
//		document.getElementById("VehicleForm").action=contextPath+"/collateralVehicleProcessAction.do?method=saveCollateralDetails";
//	 	document.getElementById("VehicleForm").submit();	
//      }
//  }
//}
function saveBGDetails()
{ 
	DisButClass.prototype.DisButMethod();

	var contextPath= document.getElementById("contextPath").value;
	//alert("ok"+document.getElementById("BGForm")+"contextPath: "+contextPath);
	if(validateCollateralBGDynaValidatorForm(document.getElementById("BGForm")))
	{
		//alert("ok");
	        if(document.getElementById("assetsIdBG").value!='')
	        {
	
			var primaryId = document.getElementById("assetsIdBG").value;
		    //alert(primaryId);
		    var assetsType = document.getElementById("bg2").value;	
			document.getElementById("BGForm").action=contextPath+"/bankGProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("BGForm").submit();
		 	return true;
		}
		else
	  {	
			//alert("In else");
			document.getElementById("BGForm").action=contextPath+"/bankGProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
			//alert("In else"+document.getElementById("BGForm").action);
			document.getElementById("BGForm").submit();	
	   }
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}



//function saveOthersDetails()
//{
//	var contextPath= document.getElementById("contextPath").value;
//	if(validateCollateralAssetOtherDynaValidatorForm(document.getElementById("OthersForm")))
//	 {
//	if(!(document.getElementById("assetsIdOther").value)==''){
//		var primaryId = document.getElementById("assetsIdOther").value;
//	   // alert(primaryId);
//	    var assetsType = document.getElementById("oth1").value;	
//		document.getElementById("OthersForm").action=contextPath+"/collateralAssetProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
//	 	document.getElementById("OthersForm").submit();
//	 	return true;
//	}
//	else
//	{		
//	document.getElementById("OthersForm").action=contextPath+"/collateralAssetProcessAction.do?method=saveCollateralDetails";
// 	document.getElementById("OthersForm").submit();	
//}
//}
//}


function saveFDDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	if(validateCollateralFixedDepositDynaValidatorForm(document.getElementById("FDForm")))
	 {
	if(!(document.getElementById("assetsIdfd").value)==''){
		var primaryId = document.getElementById("assetsIdfd").value;
   // alert(primaryId);
	    var assetsType = document.getElementById("fd2").value;	
		document.getElementById("FDForm").action=contextPath+"/collateralFDProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("FDForm").submit();
	 	return true;
	}
	else
	{		
	
	document.getElementById("FDForm").action=contextPath+"/collateralFDProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("FDForm").submit();	
}
}else{
	DisButClass.prototype.EnbButMethod();
	return false;
}
}

function saveSBLCDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	var formatD= document.getElementById("formatD").value;
	if(validateCollateralSBLCDynaValidatorForm(document.getElementById("SBLCForm")))
	 {
		var sblcValidity = document.getElementById("sblcValidity").value;
		var sblcIssuingDate = document.getElementById("sblcIssuingDate").value;
				
		var dtSblcValidity = getDateObject(sblcValidity,formatD.substring(2, 3));
		var dtSblcIssuingDate = getDateObject(sblcIssuingDate,formatD.substring(2, 3));
				
		if(dtSblcValidity > dtSblcIssuingDate)
		{
			if(!(document.getElementById("assetsIdSBLC").value)==""){
				var primaryId = document.getElementById("assetsIdSBLC").value;
			    //alert(primaryId);
			    var assetsType = document.getElementById("sblc2").value;	
				document.getElementById("SBLCForm").action=contextPath+"/collateralSBLCProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
				document.getElementById("processingImage").style.display = '';
			 	document.getElementById("SBLCForm").submit();
			 	return true;
			}
			else
			{		
			
				document.getElementById("SBLCForm").action=contextPath+"/collateralSBLCProcessAction.do?method=saveCollateralDetails";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("SBLCForm").submit();	
			}
		}
		else
		{
			alert("SBLC Validity must be greater than SBLC Issuing Date");
			DisButClass.prototype.EnbButMethod();
			document.getElementById("sblcValidity").value='';
			document.getElementById("sblcValidity").focus();
		}
	 }else{
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
}

function saveSecurityDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	
	if(validateCollateralSecurityDynaValidatorForm(document.getElementById("SecurityForm")))
	 {
			
       if(!(document.getElementById("assetsIdSecurity").value)=="")
       {
		    var primaryId = document.getElementById("assetsIdSecurity").value;
		    //alert(primaryId);
		    var assetsType = document.getElementById("sec2").value;	
			document.getElementById("SecurityForm").action=contextPath+"/collateralSecurityProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("SecurityForm").submit();
		 	return true;
	   }
		 else
		{
			document.getElementById("SecurityForm").action=contextPath+"/collateralSecurityProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("SecurityForm").submit();	
		}
}else{
	DisButClass.prototype.EnbButMethod();
	return false;
}
}


function saveStockDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	if(validateCollateralStockDynaValidatorForm(document.getElementById("StockForm")))
	 {
		if(!(document.getElementById("assetsIdStock").value)==""){
		var primaryId = document.getElementById("assetsIdStock").value;
	    //alert(primaryId);
	    var assetsType = document.getElementById("stock2").value;	
		document.getElementById("StockForm").action=contextPath+"/collateralStockProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("StockForm").submit();
	 	return true;
	}
	else
	{		
	document.getElementById("StockForm").action=contextPath+"/collateralStockProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("StockForm").submit();	
}
}else{
	DisButClass.prototype.EnbButMethod();
	return false;
}
}

function saveDebtorDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	if(validateCollateralDebtorDynaValidatorForm(document.getElementById("DebtorForm")))
	 {
	
	if(!(document.getElementById("assetsIdDebtor").value)==""){
		var primaryId = document.getElementById("assetsIdDebtor").value;
	    //alert(primaryId);
	    var assetsType = document.getElementById("debtor2").value;	
		document.getElementById("DebtorForm").action=contextPath+"/collateralDebtorProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("DebtorForm").submit();
	 	return true;
	}
	else
	{		
	document.getElementById("DebtorForm").action=contextPath+"/collateralDebtorProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("DebtorForm").submit();	
}
}else{
	DisButClass.prototype.EnbButMethod();
	return false;
}
}

function deleteCollateralDetails(type,confirmStatus)
{	
	DisButClass.prototype.DisButMethod();
	
	if(checkboxCollateral(document.getElementsByName('chk'),confirmStatus))
	    { 
		   if(confirm("Do you want to Delete ?"))
		   {
	       var c_value = "";	
	       var ptable=document.getElementById('dtable');
	       var len=ptable.rows.length-1;
	       //alert("LENGTH"+len);
	       for (var i=1; i<=len; i++)
            {
	         if (document.getElementById('chk'+i).checked==true)
               {	
		        c_value = c_value + document.getElementById('chk'+i).value+",";
	           // alert("c_value"+c_value);				   
	            }
              }	      			   
	          document.getElementById("CollateralForm").action="collateraldeleteAction.do?method=deleteCollateralDetails&checkAssetValue="+c_value+"&type="+type+"&confirmStatus="+confirmStatus;
	          document.getElementById("processingImage").style.display = '';
	          document.getElementById("CollateralForm").submit();
		   }
		   else
		   {
			   document.getElementById("collateralDelet").removeAttribute("disabled","true");
			   DisButClass.prototype.EnbButMethod();
			   return false;
			   
		   }
	    }
	      else
		   {
		    	alert("You are Requested please select atleast one!!!");
		    	//document.getElementById("collateralDelet").removeAttribute("disabled","true");
		    	DisButClass.prototype.EnbButMethod();
		    return false;
	        }
}	   

function collateralUpdate(type,actype,primaryId)
{ 
	

	if(type!=''&& primaryId!='')
	{
        var contextPath =document.getElementById('contextPath').value ;
//Prashant
        var productCat =document.getElementById('productCat').value ;
        //alert(productCat);
        var winRef = '';
        var url='';
        if(type=='INVOICE')
        {
        	url=contextPath+"/collateralAssetInvoiceAction.do?method=fetchAssetInvoiceDtl&assetClass="+type+"&assetsId="+primaryId+"&assetCollateralType="+actype;
        }
        else
        {
        	url=contextPath+"/collateralFetchDataAction.do?method=fetchCollateralDetailsAll&propertyType="+type+"&primaryId="+primaryId+"&actype="+actype+"&productCat="+productCat;
        }
		//window.child=window.open(url,'name','height=300,width=900,top=200,left=250,scrollbars=no' );
		//return true;
		//mywindow =window.open(url,'showCollateralNew','height=400,width=1000,top=200,left=250,scrollbars=yes').focus(); 
		otherWindows[curWin++] = window.open(url,'name1','height=400,width=1000,top=200,left=250,scrollbars=yes');
//		winRef = otherWindows[curWin];
		//alert(otherWindows.length);
//		for(var i = 0 ; i< (otherWindows.length-1) ; i++){
//			
//			otherWindows[i].close();
//			
//		}
//		mywindow.moveTo(800,300);
//		if (window.focus) {
//			mywindow.focus();
//			return false;
//		}
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}


function searchAsset()
{
	//alert("In searchAsset");
	var dealNo=document.getElementById('lbxLoanNoHID').value;
	var customerName=document.getElementById('customerName').value;
	var collateralId=document.getElementById('lbxCollateralId').value;
	var assetDesc=document.getElementById('assetDesc').value;
	if(dealNo!=''||customerName!=''||collateralId!='' ||assetDesc!='')
	{
		// alert("ok");
		 var contextPath =document.getElementById('contextPath').value ;
		 document.getElementById("ExistAssetForm").action = contextPath+"/existAssetsAction.do?method=searchAsset";
		 document.getElementById("ExistAssetForm").submit();
	}
	else
	{
		alert("Select at least one");
		//document.getElementById("searchButton").removeAttribute("disabled","true");
		return false;
	}
	
	
}

function saveAssetCollateral()
{
	// alert("In saveAssetCollateral");
		if(checkboxCheck(document.getElementsByName("chk")))
		{
			
			 var contextPath =document.getElementById('contextPath').value ;
			 document.getElementById("ExistAssetForm").action = contextPath+"/existAssetsAction.do?method=saveAsset";
			 document.getElementById("ExistAssetForm").submit();
	   
		}
		else
		{
			alert("Please Select AtLeast one Record");
			document.getElementById("button").removeAttribute("disabled","true");
		}
}


function getBaseRate()
{
	//alert("In getBaseRate");
	
		var baseRateType =document.getElementById('baseRateType').value ;

	var contextPath =document.getElementById('contextPath').value ;
	//alert("contextPath"+contextPath);
     if (baseRateType!= '') {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getBaseRateDetail";
		var data = "baseRateType="+baseRateType;
		//alert("address: "+address+"data: "+data);
		send_Rate(address,data);
		
		//alert("ok");
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function send_Rate(address,data) 
{
	//alert("send_BaseRate"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_Rate(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_Rate(request){
   //alert("status")
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		//alert(str);
		// var s1 = str.split("$:");
		// alert(s1);
	  // if(str.length>0)
	  // {
	    	// alert(trim(str[0]));
		
		    
			document.getElementById('baseRate').value = trim(str);
			
			var markUp = document.getElementById("markUp").value;
			var baseRate = document.getElementById("baseRate").value;
			
			if(markUp=="")
			{
				markUp=0;
			}
			if(baseRate=="")
			{
				baseRate=0;
			}
			;
			effectiveRate=parseFloat(removeComma(markUp))+parseFloat(removeComma(baseRate));
			document.getElementById("effectiveRate").value=effectiveRate.toFixed(7);
		
	    	
	// }
	}
}

function getFinalRate()
{
	// alert("In getFinalRate");
	var rate = document.getElementById("rateType").value;
	var repaymentType=document.getElementById("repaymentType").value;
	// alert("rate type:"+rate);
	if(rate!=null && rate!='')
	{
		if(rate=='E')
		{
			
			document.getElementById("type1").removeAttribute("disabled",true);
			document.getElementById("type1").checked=true;
			document.getElementById("type2").removeAttribute("disabled",true);
			document.getElementById("baseRateType").removeAttribute("disabled",true);
			document.getElementById("baseRate").removeAttribute("disabled",true);
			//document.getElementById("markUp").removeAttribute("disabled",true);
			return true;
		}
		else
		{
			if(repaymentType=='N')
			{
				document.getElementById("rateType").value='E';
				alert("You can't Change Rate Type ");
				return false;
			}
			else
			{
				document.getElementById("type1").checked=true;
				//document.getElementById("type2").checked=false;
				document.getElementById("baseRateType").value='';
				document.getElementById("baseRate").value='';
				document.getElementById("markUp").value='';
				document.getElementById("effectiveRate").value='';
				
				document.getElementById("type1").setAttribute("disabled",true);
				document.getElementById("type2").setAttribute("disabled",true);
				document.getElementById("baseRateType").setAttribute("disabled",true);
				document.getElementById("baseRate").setAttribute("disabled",true);
				//document.getElementById("markUp").setAttribute("disabled",true);
				return false;
			}
		}
		
	}
}



/*function getDefaultLoanDetail()
{
	 alert("In getBaseRate");
	
		var scheme =document.getElementById('scheme').value ;

	var contextPath =document.getElementById('contextPath').value ;
	// alert("contextPath"+contextPath);
     if (scheme!= '') {
		var address = contextPath+"/relationalManagerAjaxAction.do?method=getDefaultLoanDetail";
		var data = "scheme=" + scheme;
		// alert("address: "+address+"data: "+data);
		send_BaseRate(address, data);
		return true;
	}
     else
     {
    	 alert("Please Select List");	
    	 return false;
     }
}

function send_BaseRate(address, data) {
	// alert("send_countryDetail"+address);
	var request = getRequestObject();
	request.onreadystatechange = function () {
		result_BaseRate(request);
	};
	// alert("send_countryDetail"+address);
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function result_BaseRate(request){
   // alert(status)
	if ((request.readyState == 4) && (request.status == 200)) {
		var str = request.responseText;
		// alert(str);
		var s1 = str.split("$:");
		// alert(s1);
	    if(s1.length>0)
	    {
	    	document.getElementById('requestedLoanTenure').value=trim(s1[7]);
	    	document.getElementById('marginPerc').value = trim(s1[0]);
	    	document.getElementById('rateType').value = trim(s1[1]);
	    	alert(trim(s1[1]));
	    if(trim(s1[1])=='E')	
	    {
            document.getElementById('baseRateType').value = trim(s1[3]);
	    	
	    	document.getElementById('baseRate').value = trim(s1[4]);
	    	
	    	document.getElementById('markUp').value=trim(s1[6])-trim(s1[4]);
	    	
	    	document.getElementById('effectiveRate').value = trim(s1[6]);
	    	
	    	
	    	
	    	if(trim(s1[2])=='F')
	    	{
	    		document.getElementById('type1').checked=true;
	    	}
	    	else
	    	{
	    		document.getElementById('type2').checked=true;
	    	}
	    }
	    else
	    {
	    	document.getElementById("type1").checked=false;
			document.getElementById("type2").checked=false;
			document.getElementById("baseRateType").value='';
			document.getElementById("baseRate").value='';
			document.getElementById("markUp").value='';
			
			document.getElementById("type1").setAttribute("disabled",true);
			document.getElementById("type2").setAttribute("disabled",true);
			document.getElementById("baseRateType").setAttribute("disabled",true);
			document.getElementById("baseRate").setAttribute("disabled",true);
			document.getElementById("markUp").setAttribute("disabled",true);
			
			document.getElementById('effectiveRate').value = trim(s1[5]);
	    }
	    	
	    	
			
	    	document.getElementById('repaymentType').value = trim(s1[12]);
	    	// alert("frequency:"+trim(s1[18]));
	    	document.getElementById('frequency').value = trim(s1[8]);
	    	
	    	document.getElementById('installmentType').value = trim(s1[9]);
	    	
	    	document.getElementById('paymentMode').value = trim(s1[10]);
	    	
	    	document.getElementById('instMode').value = trim(s1[11]);
	    	
	    	
            document.getElementById('minMarginRate').value = trim(s1[14]);
	    	
	    	document.getElementById('maxMarginRate').value = trim(s1[15]);
	    	
	    	document.getElementById('minTenure').value = trim(s1[16]);
	    	
	    	document.getElementById('maxTenure').value = trim(s1[17]);
	
	    	
		}
	}
}*/

function checkBoxes()
{
    
	var ch=document.getElementsByName('chk');
	
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[i].checked==true)
		{
			zx++;
			
		}
		
	}
	if(zx>1)
	{
		alert("Please select one record to modify !!!");
		return false;
	}
	else if(zx==1)
	{
		return true;
	}
	
}
function checkBoxes()
{
    // alert("ok");
	var ch=document.getElementsByName('chk');
	    var zx=0;
	    var flag=0;
	    for(i=0;i<ch.length;i++)
	{
		if(ch[i].checked==true)
		{
			zx++;
			flag=1;
		}
		
	}
	    if(flag==0)
		{
			alert("Please select at most one record to modify !!!");
			return false;
		}   
	if(zx>1)
	{
		alert("Please select one record to modify !!!");
		return false;
	}
	else if(zx==1)
	{
		return true;
	}
	
}
//function deleteLoanDetails()
//{
//        
//		 //alert("In deleteLoanDetail");
//		 if(checkboxCheck(document.getElementsByName('chk')))
//		 {
//		   if(confirm("Are you want to delete product ?"))
//		   {
//			 var contextPath =document.getElementById('contextPath').value ;
//			 document.getElementById("LoanDetailsForm").action = contextPath+"/loanBehindAction.do?method=deleteLoan";
//			 document.getElementById("LoanDetailsForm").submit();
//		   }
//		 }
//		 else
//		 {
//			 alert("Please Select atleast one Record");
//			 document.getElementById("Delete").removeAttribute("disabled","true");
//		 }
//		
//				
//	
//}

function modifyDetail(dealLoanId)
{
	
	//alert("modifyLoanDetail"+dealLoanId);
	
	var contextPath =document.getElementById('contextPath').value ;
	// alert(checkBoxes());
	// if(checkBoxes())
   // {
    	// alert("ok");
		
		document.getElementById("LoanDetailsForm").action=contextPath+"/loanBehindAction.do?method=fetchLoanDetail&dealLoanId="+dealLoanId;
	 	document.getElementById("LoanDetailsForm").submit();
	  // alert("After submit...........");
  // }
	// else
	// {
	  // alert("notrok");
		// return false;
	// }
	
}


function cpNotepadDisable()
{
	// alert("TEST");
	if(document.getElementById("followUp").value == "N")
	{
		document.getElementById("showDate").style.display='none';
		document.getElementById("notshowDate").style.display='block';
		document.getElementById("followupTime").disabled=true;
		document.getElementById("followUpPerson").disabled=true;
		document.getElementById("followUpLocation").disabled=true;
		document.getElementById("followupRemarks").disabled=true;
	}
	else if(document.getElementById("followUp").value == "Y")
	{
		document.getElementById("showDate").style.display='block';
		document.getElementById("notshowDate").style.display='none';
		document.getElementById("followupTime").disabled=false;
		document.getElementById("followUpPerson").disabled=false;
		document.getElementById("followUpLocation").disabled=false;
		document.getElementById("followupRemarks").disabled=false;
	}
}

function cpNotepadValidate()
{
		var alphaExp = /^[a-zA-Z]+$/;
		var msg1='',msg3='',msg4='',msg5='';
		
		if((document.getElementById("followupDate").value)=='')
		{
			msg1="* Follow Up Date is Required \n";
		}
		
		if((document.getElementById("followUpPerson").value)=='')
		{
			 msg3="* Follow Up Person is Required\n";
		}
		if((document.getElementById("followUpLocation").value)=='')
		{
			 msg4="* Follow Up Location is Required\n";
		}
		if((document.getElementById("followupRemarks").value)=='')
		{
			 msg5="* Follow Up Remarks is Required\n";
		}
		
		if(msg1!=''||msg3!=''||msg4!=''||msg5!='')
		{
			alert(msg1+msg3+msg4+msg5);
		}
		
		if(document.getElementById("followupDate").value=="")
		{
			document.getElementById("followupDate").focus();
			return false;
		}
		
		
		if(document.getElementById("followUpPerson").value =="")
		{
			document.getElementById("followUpPerson").focus();
			return false;
		}
		
		if(document.getElementById("followUpLocation").value =="")
		{
			document.getElementById("followUpLocation").focus();
			return false;
		}

		
		if(document.getElementById("followupRemarks").value =="")
		{
			document.getElementById("followupRemarks").focus();
			return false;
		}
		return true;
}

function notepadTime()
{
	var timeStr = document.getElementById("meetingTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('meetingTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('meetingTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('meetingTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('meetingTime').value='';
	return false;
	}
}

function followTime()
{
	var timeStr = document.getElementById("followupTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('followupTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('followupTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('followupTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('followupTime').value='';
	return false;
	}
}

function checkPdTime()
{
	var timeStr = document.getElementById("pdTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('pdTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('pdTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('pdTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('pdTime').value='';
	return false;
	}
}

function queryTime1()
{
	var timeStr = document.getElementById("queryTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('queryTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('queryTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('queryTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('queryTime').value='';
	return false;
	}
}

function resolutionTime1()
{
	var timeStr = document.getElementById("resolutionTime").value;
	if(timeStr.length<5)
	{
		document.getElementById('resolutionTime').value='0'+timeStr;
	}
	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		document.getElementById('resolutionTime').value='';
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	document.getElementById('resolutionTime').value='';
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	document.getElementById('resolutionTime').value='';
	return false;
	}
}


function IsValidTime2(timeStr) {

	var timePat = /^(\d{1,2}):(\d{2})?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) {
		alert("Time is not in a valid format.");
		return false;
		}
	hour = matchArray[1];
	minute = matchArray[2];
	
	if (hour < 0  || hour > 23) {
	alert("Hour must be between 0 and 23.");
	return false;
	}
	if (minute<0 || minute > 59) {
	alert ("Minute must be between 0 and 59.");
	return false;
	}
	return false;
}

function saveNotepadData(alert1)
{
//	alert("Save");

	DisButClass.prototype.DisButMethod();
	if(validateNotepadDynaValidatorForm(document.getElementById("notepadForm")))
	{
		if(document.getElementById("followUp").value=='N')
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("notepadForm").action = contextPath+"/notepad.do?method=saveNotepadData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("notepadForm").submit();
		}
		else if(document.getElementById("followUp").value=='Y')
		{
			if(cpNotepadValidate())
			{
				var formatD=document.getElementById("formatD").value;
				var meetingDate = document.getElementById("date").value;
				var followupDate = document.getElementById("followupDate").value;
				var dt1=getDateObject(meetingDate,formatD.substring(2, 3));
				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
				if (dt2<dt1)
				{
					alert(alert1);
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				var contextPath =document.getElementById('contextPath').value ;
				document.getElementById("notepadForm").action = contextPath+"/notepad.do?method=saveNotepadData";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("notepadForm").submit();
			}
		}
		return true;
	}
}

function openStatus(val) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}

function searchDeal(val)
{
	var contextPath =document.getElementById('contextPath').value ;
	location.href=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";
}

// Changes Start By Sanjog
function openLeadView(val)
{
	var contextPath =document.getElementById('contextPath').value ;
	location.href=contextPath+"/leadTrackingBehind.do?leadId="+val+"&LeadViewMode=LeadViewMode";
}
// Changes End By Sanjog

function updateCreditDecisionValues()
{
	DisButClass.prototype.DisButMethod();
	var msg1='',msg2='',msg3='',msg4='';
	var reqAmt=parseFloat(removeComma(document.getElementById("requestAmt").value));
	var sancAmt=parseFloat(removeComma(document.getElementById("sancAmt").value));
	var businessDate = getDateObject(document.getElementById("businessDate").value,document.getElementById("formatD").value.substring(2, 3));
	var sancDate = getDateObject(document.getElementById("sancDate").value,document.getElementById("formatD").value.substring(2, 3));
	var schemeMinAmt=removeComma(document.getElementById("schemeMinAmt").value);
	var schemeMaxAmt=removeComma(document.getElementById("schemeMaxAmt").value);
	if((document.getElementById("sancAmt").value)=='')
	{
		msg1="* Saction Amount is required \n";
	}
	if((document.getElementById("sancDate").value)=='')
	{
		 msg2="* Sanction Date is required\n";
	}
	/*if((document.getElementById("underTenure").value)=='')
	{
		 msg3="* Tenure is required\n";
	}
	if((document.getElementById("finalRate").value)=='')
	{
		 msg4="* Final Rate is required\n";
	}
	*/
	if(msg1!=''||msg2!=''||msg3!=''||msg4!='')
	{
		alert(msg1+msg2+msg3+msg4);
		document.getElementById("update").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(sancDate<businessDate)
	{
		alert("Sanctioned Till Date cannot be smaller than Business Date");
		document.getElementById("sancDate").focus();
		document.getElementById("update").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(reqAmt<sancAmt)
	{
		alert("Sanctioned Amount cannot be greater than Requested Amount");
		document.getElementById("sancAmt").value='';
		document.getElementById("sancAmt").focus();
		document.getElementById("update").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(sancAmt<schemeMinAmt || sancAmt>schemeMaxAmt)
	{
		alert("Please Insert Sanctioned Amount between "+schemeMinAmt+" and "+ schemeMaxAmt);
		document.getElementById("sancAmt").value='';
		document.getElementById("sancAmt").focus();
		document.getElementById("update").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else		
	{	
		//alert("Neeraj");
		var uwCustomerType =document.getElementById('uwCustomerType').value ;
		//alert("Neeraj uwCustomerType : "+uwCustomerType);
		
			var groupType  =document.getElementById('groupType').value ;
			var groupNameText  =document.getElementById('groupNameText').value ;
			var dealExposureAmount =document.getElementById('dealExposureAmount').value ;
			var dealExLoanAmount =document.getElementById('dealExLoanAmount').value ;
			var exposureLimit =document.getElementById('exposureLimit').value ;
			if(dealExposureAmount=='')
				dealExposureAmount='0';
			if(dealExLoanAmount=='')
				dealExLoanAmount='0';
			if(exposureLimit=='')
				exposureLimit='0';
			if((groupType=='N' && groupNameText !='' )||(groupType=='E' && groupNameText !='' ))
				{
				
			if(parseFloat(exposureLimit)=='0')
			{
				alert("Exposure Limit can't be less or equal to Zero(0.00)");
				document.getElementById("sancAmt").focus();
				document.getElementById("update").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			else
			{
				var expAmt=parseFloat(exposureLimit);
				var grpExpAmt=parseFloat(dealExposureAmount);
				var oldLoanAmt=parseFloat(dealExLoanAmount);
				grpExpAmt=grpExpAmt-oldLoanAmt+sancAmt;
				if(expAmt<grpExpAmt)
				{
					alert("Exposure Limit can't be less than Group Exposure Amount");
					document.getElementById("sancAmt").focus();
					document.getElementById("update").removeAttribute("disabled","true");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
				}
		var contextPath =document.getElementById('contextPath').value ;
		//alert(document.getElementById("repayType").value);
		if(document.getElementById("repayType").value=='N')
		{
			document.getElementById("underWriterForm").action = contextPath+"/underWriting.do?method=updateCreditDecision&sancAmt="+sancAmt;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("underWriterForm").submit();
			return true;
		}
		else if(checkSanctionBIRR())
		{
		
		document.getElementById("underWriterForm").action = contextPath+"/underWriting.do?method=updateCreditDecision&sancAmt="+sancAmt;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("underWriterForm").submit();
		return true;
		}
		else
		{
			document.getElementById("update").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	
	}

}


function submitQueryData()
{
	DisButClass.prototype.DisButMethod();
	var formatD=document.getElementById("formatD").value;
	var sourcepath=document.getElementById("contextPath").value;
	var resolutionStatus=document.getElementById("resolutionStatus").value;
	var queryDate = document.getElementById("date").value;
	var resolutionDate = document.getElementById("to_datepicker").value;
	
	var dt1=getDateObject(queryDate,formatD.substring(2, 3));
    var dt2=getDateObject(resolutionDate,formatD.substring(2, 3));
	if(validateQueryDynaValidatorForm(document.getElementById("queryForm")))
	{
		if((resolutionStatus)=='P')
		{
			document.getElementById("queryForm").action=sourcepath+"/query.do?method=submitQueryData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("queryForm").submit();
			return true;
		}
		else if((resolutionStatus)=='R')
		{
			if(queryValidate())
			{
				if(dt2>=dt1)
			    {
					document.getElementById("queryForm").action=sourcepath+"/query.do?method=submitQueryData";
					document.getElementById("processingImage").style.display = '';
					document.getElementById("queryForm").submit();
					return true;
			    }
				else
				{
					alert("Resolution Date cannot be smaller than Query Date.");
					document.getElementById("to_datepicker").value='';
					document.getElementById("to_datepicker").focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			else
				DisButClass.prototype.EnbButMethod();
				return false;
		}
	}	
}

function showQueryData(b)
{
	// alert("Test");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("queryForm").action=sourcepath+"/queryBehind.do?method=showQueryData&queryDate="+b;
	// alert("Action "+document.getElementById("queryForm").action);
	document.getElementById("queryForm").submit();
	
}

//function updateQueryData(b,c,d)
//{
//	var sourcepath=document.getElementById("contextPath").value;
//	var qyeryDate = document.getElementById("queryDate").value;
//	var resolutionDate = document.getElementById("to_datepicker").value;
//	var dt1=getDateObject(qyeryDate,"-");
//    var dt2=getDateObject(resolutionDate,"-");
//    
//	if(queryValidate())
//	{
//		if(dt2>=dt1)
//	    {
//			document.getElementById("queryForm").action=sourcepath+"/query.do?method=updateQueryData&queryDate="+b+"&queryTime="+c+"&queryRemarks="+d;
//			document.getElementById("queryForm").submit();
//			return true;
//	    }
//		else
//		{
//			alert("Resolution Date cannot be smaller than Query Date.");
//			document.getElementById("to_datepicker").value='';
//			document.getElementById("to_datepicker").focus();
//			return false;
//		}
//	}
//	else{
//		return false;
//	}
//    
//}


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

//function queryValidate()
//{
//	 alert("test");
//	
//	if(document.getElementById("to_datepicker").value=="")
//	{
//		alert("Resolution Date is Required \n");
//		document.getElementById("to_datepicker").focus();
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
//	
//	if(document.getElementById("resolutionRemarks").value=="")
//	{
//		alert("Resolution Remarks  is Required  \n");
//		document.getElementById("resolutionRemarks").focus();
//		DisButClass.prototype.EnbButMethod();
//		return false;
//	}
//
//
//	
//	
//	return true;
//}

function newEntryDeal()
{
	DisButClass.prototype.DisButMethod();
	// alert("ok");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("commonForm").action=sourcepath+"/dealCapturing.do?method=leadEntryCapturing&dealStatus=NEW";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("commonForm").submit();
	// return true;
}


//function saveBuyerDetails()
//{	
//	//alert("saveBuyerDetails");
//	var contextPath = document.getElementById("contextPath").value;
//  if(validateBuyerSupplierDynaValidatorForm(document.getElementById("BuyerForm")))
//	{	
//	 
//	  if(!(document.getElementById("chkvalue").value)=="")
//	  {   
//	      var primaryId = document.getElementById("chkvalue").value;
//	      //alert(primaryId);    
//		  document.getElementById("BuyerForm").action=contextPath+"/buyerProcessAction.do?method=updateBuyerDetails&primaryId="+primaryId;
//		  document.getElementById("BuyerForm").submit();
//		  return true;
//	  }
//	  else
//	  {
//		 // alert("Same here");
//		  document.getElementById("BuyerForm").action=contextPath+"/buyerProcessAction.do?method=saveBuyerDetails";
//		  document.getElementById("BuyerForm").submit();
//		  return true;
//	  }
//	 
//	}
//   else
//	  { 
//		  document.getElementById("saveBuyer").removeAttribute("disabled","true");
//		  return false;
//		  
//	  }
//	}


//function saveNForBuyerDetails()
//{	
//	//alert("saveBuyerDetails");
//	  var contextPath = document.getElementById("contextPath").value;
//	
//	  document.getElementById("BuyerForm").action=contextPath+"/buyerSuppMainAction.do?method=saveNForBuyerDetails";
//	  document.getElementById("BuyerForm").submit();
//	  return true;
//	
//   
//	}

function saveBuyerSupplierAuthor(){
	   //	alert("saveInstrumentAuthor");
	   		
	   	var contextPath=document.getElementById("contextPath").value;
	   //	alert("contextPath"+contextPath);
	   		  
		if((document.getElementById("comments").value)==""){
   			alert("Please enter Comments");
   			
   			document.getElementById("save").removeAttribute("disabled");
	   		return false;
   		}
	   	
		if((document.getElementById("decision").value)==""){
   			alert("Please select decision");
   			
   			document.getElementById("save").removeAttribute("disabled");
	   		return false;
   		}
	   	
	   
	   		if((document.getElementById("comments").value.length) > 1000){
	   			alert("You are requested to enter only 1000 characters.");
	   			
	   			document.getElementById("save").removeAttribute("disabled");
		   		return false;
	   		}
	   		
	   		document.getElementById("sourcingForm").action = contextPath+"/buyerSuppMainAction.do?method=saveBuyerSupplierAuthor";
	   		 document.getElementById("sourcingForm").submit();
	   		 return true;
	   
	   	 
	   }

function newSearchBuyerSupplier()
{
	DisButClass.prototype.DisButMethod();
	
	var sourcepath=document.getElementById("contextPath").value;
	var custName=document.getElementById("customerName").value;
	var prod=document.getElementById("product").value!='';
	var reportingToUserId=document.getElementById("reportingToUserId").value;
	if(reportingToUserId!='' || document.getElementById("dealNo").value!='' || document.getElementById("customerName").value!='' || document.getElementById("initiationDate").value!='' || document.getElementById("product").value!='' || document.getElementById("scheme").value!='')
	{
		if(custName!='' && custName.length<3)
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}else
		{
			document.getElementById("buyerSupplierSearchForm").action=sourcepath+"/buyerSuppMainAction.do?method=searchDealForBuyerSupp&userId="+reportingToUserId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("buyerSupplierSearchForm").submit();
			return true;
		} 
	}else{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}



function newSearchBuyerSupplierAuthor()
{
	var sourcepath=document.getElementById("contextPath").value;
	var custName=document.getElementById("customerName").value;
	var prod=document.getElementById("product").value!='';
	if(document.getElementById("dealNo").value!='' || document.getElementById("customerName").value!='' || document.getElementById("initiationDate").value!='' || document.getElementById("product").value!='' || document.getElementById("scheme").value!='')
	{
		if(custName!='' && custName.length<3)
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("searchButton").removeAttribute("disabled","true");
			return false;
		}else  
		{
			document.getElementById("buyerSupplierSearchForm").action=sourcepath+"/buyerSuppMainAction.do?method=searchDealForBuyerSuppAuthor";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("buyerSupplierSearchForm").submit();
			return true;
		} 
	}else{
		alert("Please Select atleast one criteria");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		return false;
	}
}


function modifyBuyer(v)
{	
	var contextPath = document.getElementById("contextPath").value;
  document.getElementById("BuyerForm").action=contextPath+"/buyerBehindAction.do?method=modifyBuyerDetails&primaryId="+v;
  document.getElementById("BuyerForm").submit();
}

function updateBuyer()
{		  
	var contextPath = document.getElementById("contextPath").value;	
  document.getElementById("BuyerForm").action=contextPath+"/buyerProcessAction.do?method=updateBuyerDetails";
  document.getElementById("BuyerForm").submit();
}    
 

  function deleteBuyer(confirmStatus)
  {
	  DisButClass.prototype.DisButMethod();
	  var contextPath = document.getElementById("contextPath").value;
  	if(checkBuyer(confirmStatus))
  	 { 
  		if(confirm("Are you sure to delete buyer"))
  		{
  			document.getElementById("BuyerForm").action=contextPath+"/buyerBehindAction.do?method=deleteBuyerDetails&confirmStatus="+confirmStatus;
  			document.getElementById("processingImage").style.display = '';
  	  		document.getElementById("BuyerForm").submit();
  	  		return true;
  		}
  		else
  		{
  			DisButClass.prototype.EnbButMethod();
  			return false;
  		}
  		
  	  }
  	   else
  	   {
  	   	alert("Please Select atleast one!!!");
  	   	DisButClass.prototype.EnbButMethod();
  	    return false;
  	   }
  }

//  function saveSupplierDetails()
//  {
//	  var contextPath = document.getElementById("contextPath").value;
//  	 if(validateBuyerSupplierDynaValidatorForm(document.getElementById("SupplierForm")))
//  		{	
//  		    
//  		  if(!(document.getElementById("chkvalue").value)=="")
//  		  { 
//  		      var primaryId = document.getElementById("chkvalue").value;
//  		    //  alert(primaryId);
//  			  document.getElementById("SupplierForm").action=contextPath+"/supplierProcessAction.do?method=updateSupplierDetails&primaryId="+primaryId;
//  			  document.getElementById("SupplierForm").submit();
//  			  return true;
//  		  }
//  		  else
//  		  { 
//  			
//  			  document.getElementById("SupplierForm").action=contextPath+"/supplierProcessAction.do?method=saveSupplierDetails";
//  			  document.getElementById("SupplierForm").submit();
//  		  }
//  		}
//  }	
function modifySupplier(v)
{	
	  var contextPath = document.getElementById("contextPath").value;
  document.getElementById("SupplierForm").action=contextPath+"/supplierBehindAction.do?method=modifySupplierDetails&primaryId="+v;
  document.getElementById("processingImage").style.display = '';
  document.getElementById("SupplierForm").submit();
}

function updateSupplier()
{		  
	var contextPath = document.getElementById("contextPath").value;	
  document.getElementById("SupplierForm").action=contextPath+"/supplierProcessAction.do?method=updateSupplierDetails";
  document.getElementById("SupplierForm").submit();
}

function deleteSupplier(confirmStatus)
{  
	DisButClass.prototype.DisButMethod();
	 var contextPath = document.getElementById("contextPath").value;
	    if(checkSupplier(confirmStatus))
	    {
	     if(confirm("Are you sure to delete suplier"))
	      {
			document.getElementById("SupplierForm").action=contextPath+"/supplierBehindAction.do?method=deleteSupplierDetails&confirmStatus="+confirmStatus;
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("SupplierForm").submit();
		 	return true;
	      }
	      else
	      {
	    	  DisButClass.prototype.EnbButMethod();
	    		return false;
	      }
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    	DisButClass.prototype.EnbButMethod();
	    	return false;
	    }
}

function calculateFinalRate()
{
	
	var markUp = document.getElementById("markUp").value;
	var baseRate = document.getElementById("baseRate").value;
	//alert("In calculateFinalRate markUp: "+markUp+"baseRate: "+baseRate);
	if(markUp=="")
	{
		markUp=0;
	}
	if(baseRate=="")
	{
		baseRate=0;
	}
	
	effectiveRate=parseFloat(markUp)+parseFloat(baseRate);
	markup=effectiveRate-parseFloat(baseRate);
	document.getElementById("effectiveRate").value=effectiveRate.toFixed(7);
	document.getElementById("markUp").value=markup.toFixed(7);
	
}

function checkRate(val)

{
	

	var rate = document.getElementById(val).value;
	 
//alert("Passed Value: "+rate);
if(rate=='')
	{
		rate=0;
		//alert("Please Enter the value");
	//	return false;
		
	}
	    var intRate = parseFloat(rate);
	  //  alert(intRate);
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }

	        else
	           {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById(val).value="";
		//document.getElementById(val).focus;
		        return false;
	         }
	
}

function getMarginAmount(rr){
	
	//alert("getMarginAmount"+rr);
	var minPerc=0;
	var maxPerc=0;
	var defper =0;
	
	if(document.getElementById("minMarginRate").value!='')
	{
		minPerc=parseFloat(removeComma(document.getElementById("minMarginRate").value));
	}
	if(document.getElementById("maxMarginRate").value!='')
	{
		maxPerc=parseFloat(removeComma(document.getElementById("maxMarginRate").value));
	}
	if(document.getElementById(rr).value!='')
	{
		defper =parseFloat(removeComma(document.getElementById(rr).value));
	}
	//alert("ok"+minPerc+" "+maxPerc);
	var scheme=document.getElementById("scheme").value;
if(scheme!='')
{
	  var amount=document.getElementById("assetCost").value;
		//alert(amount);
		if(amount=="")
		{
			if((repaymentType!='' && repaymentType=='I')){
				amount=0;
				document.getElementById(rr).value="";
				alert("Please Enter the Asset Cost");
				return false;
			}
		}
		else
		{
		   amount=removeComma(document.getElementById("assetCost").value);
		}
		
		//alert("defper "+defper+"minPerc "+minPerc+"maxPerc "+maxPerc);
		
	  if((defper>=minPerc)&&(defper<=maxPerc))	
	  {
		//alert("ok "+ rr);  
		if(checkRate(rr))
		{
			//alert("ok iffff "+ document.getElementById(rr).value);  
			var rate =0;
			var loanAm=parseFloat(amount);
			if(document.getElementById(rr).value!='')
			{
				rate= parseFloat(document.getElementById(rr).value);
			}
			
			document.getElementById("marginAmount").value=roundNumber((loanAm*rate)/100, 2)  ;
			 return true;
		}
	  }
	  else
	  {
		  alert("Please Enter the value from "+minPerc+" to "+maxPerc);
		  document.getElementById("marginAmount").value='';
		  document.getElementById(rr).value='';
		  document.getElementById(rr).focus();
		  return false;
	  }
	}
	else
	{
		  alert("Please Select the Scheme ");
		  document.getElementById("marginAmount").value='';
		  document.getElementById("assetCost").value='';
		  document.getElementById(rr).value='';
		  return false;
	}
	return true;
}
function calMarginAmount()
{
	
	//alert(iRate);
	var assetAmount=document.getElementById("assetCost").value;
	if(assetAmount!='' && document.getElementById("marginPerc").value!='')
	{
		//alert("In if "+assetAmount);
		var iLoanAmount=removeComma(assetAmount);
		var iRate=removeComma(document.getElementById("marginPerc").value);
		document.getElementById("marginAmount").value=roundNumber((iLoanAmount*iRate)/100, 2);
	}
	else
	{
	//	alert("In else "+assetAmount);
		document.getElementById("marginAmount").value='';
	}
	
}

function calMarginPerc()
{
	var marginAmt =0;
	var assetCost = 0;
	if(document.getElementById("marginAmount").value!='')
	{
		marginAmt = removeComma(document.getElementById("marginAmount").value);
	}
	
	if(document.getElementById("assetCost").value!='')
	{
		assetCost = removeComma(document.getElementById("assetCost").value);
	}
//	alert("marginAmt: "+marginAmt);
//	alert("assetCost: "+assetCost);
	var marginPerc = 0.0;
	if(marginAmt==0)
	{
		document.getElementById("marginPerc").value= 0;
	}
	else
	{
		marginPerc = roundNumber(((marginAmt*100)/assetCost),2);
		
//		alert("marginPerc: "+marginPerc);
		document.getElementById("marginPerc").value= marginPerc;
		var check = checkRate("marginPerc");
		if(!check )
		{
			document.getElementById("marginAmount").value='';
		}
	}
	var minPerc=0;
	var maxPerc=0;
	var defper =0;
	
	if(document.getElementById("minMarginRate").value!='')
	{
		minPerc=parseFloat(removeComma(document.getElementById("minMarginRate").value));
	}
	if(document.getElementById("maxMarginRate").value!='')
	{
		maxPerc=parseFloat(removeComma(document.getElementById("maxMarginRate").value));
	}
	if(document.getElementById("marginPerc").value!='')
	{
		defper =parseFloat(removeComma(document.getElementById("marginPerc").value));
	}
	//alert(defper+" "+minPerc+" "+maxPerc);
	 if((defper<=minPerc)||(defper>=maxPerc))	
	 {
		  alert("Please Enter the value from "+minPerc+" to "+maxPerc);
		  document.getElementById("marginAmount").value='';
		  document.getElementById("marginPerc").value='';
		  return false;
	  }
}

function saveApprovalData()
{
	DisButClass.prototype.DisButMethod();
	var leadStatus=document.getElementById("leadStatus").value;
	document.getElementById("remarks").value=trim(document.getElementById("remarks").value);
	var remarks = document.getElementById("remarks").value;
	var decision = document.getElementById("decision").value;
	var reversingStage = document.getElementById("reversingStage").value;
	if(leadStatus=='X'){
		if(decision!='' && decision=='P' && reversingStage!='DC'){
		    alert("This case was rejected from SINGLE PAGE BUREAU,Please Send Back to Deal Capturing.  ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(decision!='' && decision=='A'){
		    alert("This case was rejected from SINGLE PAGE BUREAU,Please Reject or Send Back to Deal Capturing.  ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(decision!='' && decision=='X')
		{
			var reasonDesc = document.getElementById("reasonDesc").value;
			if(reasonDesc=='')
			{
			    alert("REASON IS REQUIRED.");
				document.getElementById("saveApprove").removeAttribute("disabled","true");
	
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			var imdFlag =document.getElementById("imdFlag").value;
			if(imdFlag=='Y')
			{
			    alert("Some IMD are Pending.");
				document.getElementById("saveApprove").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
		}
		}
		if(remarks!='')
		{
			if(remarks.length>2000)
			{
				alert("Author Remarks can't be more than 2000 character.");
				document.getElementById("remarks").value='';
				document.getElementById("remarks").focus();
				document.getElementById("saveApprove").removeAttribute("disabled","true");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			else
			{
				var sourcepath=document.getElementById("contextPath").value;
				document.getElementById("approvalForm").action=sourcepath+"/underwritingApproval.do?method=saveApprovalData";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("approvalForm").submit();
				return true;
			}
		}
		else
		{
			alert("Author Remarks is Required.");
			document.getElementById("remarks").focus();
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}else{
	var checkContactCCount = document.getElementById("checkContactCCount").value;
	var checkTradeCCount = document.getElementById("checkTradeCCount").value;
	var checkCollateralCCount = document.getElementById("checkCollateralCCount").value;
	// Start By Prashant
	var checkDeviationCount=document.getElementById("checkDeviationCount").value;
	var businessDate = getDateObject(document.getElementById("businessdate").value,document.getElementById("formatD").value.substring(2, 3));
	var sancDate = getDateObject(document.getElementById("sactionValidDate").value,document.getElementById("formatD").value.substring(2, 3));
	// End By Prashant
	//alert("checkContactCCount: "+checkContactCCount+" checkTradeCCount: "+checkTradeCCount+" checkCollateralCCount: "+checkCollateralCCount+" checkDeviationCount: "+checkDeviationCount);
	var deviationRecStatus=document.getElementById("deviationRecStatus").value;
	if(decision=='A'){
		if(deviationRecStatus=='X' && deviationRecStatus!=''){
			alert("Please select Send Back as deviation had been rejected ");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	if(decision!='' && decision=='P')
	{
		if(reversingStage!='' && reversingStage=='CVC' && checkCollateralCCount=='0')
		{
			alert("COLLLATERAL VERIFICATION COMPLETION CAN'T SEND BACK, ALL COLLATERALS ARE NOT DEFINED OR WAVEOFF. ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(reversingStage!='' && reversingStage=='TCC' && checkTradeCCount=='0')
		{
			alert("TRADE CHECK CLOSURE CAN'T SEND BACK, ALL TRADES ARE NOT DEFINED OR WAVEOFF. ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(reversingStage!='' && reversingStage=='FVC' && checkContactCCount=='0')
		{
			alert("FIELD VERIFICATION COMPLETION CAN'T SEND BACK, ALL FIELDS ARE NOT DEFINED OR WAVEOFF. ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	// START BY PRASHANT
		if(reversingStage!='' && reversingStage=='POC' && checkDeviationCount=='0')
		{
			alert("DEAL POLICY CHECK COMPLETION CAN'T SEND BACK, BECAUSE THERE IS NO DEVIATION. ");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(reversingStage=='')
		{
			alert("STAGE IS REQUIRED.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	// END BY PRASHANT	
		
	}
// Start By Prashant	
	if(decision!='' && decision=='X')
	{
		var reasonDesc = document.getElementById("reasonDesc").value;
		if(reasonDesc=='')
		{
		    alert("REASON IS REQUIRED.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	if(decision!='' && decision=='A')
	{
		var expoLimit = document.getElementById("expoLimit").value;

		if(expoLimit=='EXPLMT')
		{
		    alert("PLEASE UPDATE GROUP EXPOSURE DETAIL FOR ALL CUSTOMERS.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(sancDate<businessDate)
		{
			alert("Sanctioned Till Valid Date can not be less than Business Date");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var specialCount = parseInt(document.getElementById("specialCount").value);
		if(specialCount==0)
		{
		    alert("PLEASE DEFINE SPECIAL CONDITION.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		var disbursalCount = parseInt(document.getElementById("disbursalCount").value);
		var countRepayType=parseInt(document.getElementById("countRepayType").value);
		if(disbursalCount==0 && countRepayType!=0)
		{
		    alert("PLEASE DEFINE DISBURSAL SCHEDULE");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//Nishant Starts
		var termSheetCount = parseInt(document.getElementById("termSheetCount").value);
		if(termSheetCount==0)
		{
		    alert("Please Capture Term Sheet.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//Nishant End
		//KK Starts
		var imdFlag =document.getElementById("imdFlag").value;
		if(imdFlag=='Y')
		{
		    alert("Some IMD are Pending.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//KK End
		
	}
	// END BY PRASHANT	
	if(remarks!='')
	{
		if(remarks.length>2000)
		{
			alert("Author Remarks can't be more than 2000 character.");
			document.getElementById("remarks").value='';
			document.getElementById("remarks").focus();
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("approvalForm").action=sourcepath+"/underwritingApproval.do?method=saveApprovalData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("approvalForm").submit();
			return true;
		}
	}
	else
	{
		alert("Author Remarks is Required.");
		document.getElementById("remarks").focus();
		document.getElementById("saveApprove").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
}

function rangeTenure()
{
	var iDefValue=parseInt(document.getElementById("requestedLoanTenure").value);
	var iMinValue=parseInt(document.getElementById("minTenure").value);
	var iMaxValue=parseInt(document.getElementById("maxTenure").value);
	//alert("iDefValue: "+iDefValue+"iMinValue: "+iMinValue+"iMaxValue: "+iMaxValue);
	if(((iDefValue<iMinValue)||(iDefValue>iMaxValue))&&(iDefValue!='' && iMinValue!='' && iMaxValue!=''))
	{
		alert("Please Insert Tenure between "+iMinValue+" and "+iMaxValue);
		document.getElementById("requestedLoanTenure").value='';
		return false;
	}
	else
	{
		return true;
	}

}
function rangeAmount()
{
	var iDefValue=0;
	
	if(document.getElementById("requestedLoanAmount").value!='')
	{
		iDefValue=parseFloat(removeComma(document.getElementById("requestedLoanAmount").value));
	}
	//alert(iDefValue);
	var iMinValue=0;
	
	if(document.getElementById("minFinance").value!='')
	{
		iMinValue=parseFloat(removeComma(document.getElementById("minFinance").value));
	}
	
	var iMaxValue=0;
	
	if(document.getElementById("maxFinance").value!='')
	{
		iMaxValue=parseFloat(removeComma(document.getElementById("maxFinance").value));
	}
	
	//alert("iDefValue: "+iDefValue+"iMinValue: "+iMinValue+"iMaxValue: "+iMaxValue);
	if(iDefValue=='0')
	{
		alert("Please Insert Requested Loan Amount between "+iMinValue+" and "+iMaxValue);
		document.getElementById("requestedLoanAmount").value='';
		return false;
	}
	else
	if(((iDefValue<iMinValue)||(iDefValue>iMaxValue))&&(iDefValue!='' && iMinValue!='' && iMaxValue!=''))
	{
		alert("Please Insert Requested Loan Amount between "+iMinValue+" and "+iMaxValue);
		document.getElementById("requestedLoanAmount").value='';
		return false;
	}
	else
	{
		return true;
	}
}

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


function validMaturity()
{
	//alert("ok+"+document.getElementById("fdBookDate").value);

	var fdBookDate = document.getElementById("fdBookDate").value;
	var fdMatureDate =document.getElementById("fdMatureDate").value;
	
	 dt1=getDateObject(fdBookDate,"-");
     dt2=getDateObject(fdMatureDate,"-");

	if (dt1>dt2)
	  {
	  alert("Book Date can not be less than Mature Date");
	  document.getElementById("fdMatureDate").value='';
	  return false;
	  }
	else
	{//alert("ok");
		return true;
	}
}


function resultofQuery()
{
	//alert("resultofQuery");
	var resolutionStatus=document.getElementById("resolutionStatus").value;
	if((resolutionStatus)=='P')
	{
//		 document.getElementById("showDate").style.display='none';
//		 document.getElementById("notshowDate").style.display='block';
//		 document.getElementById("to_datepicker12").setAttribute("disabled","true");
//		 document.getElementById("resolutionTime").setAttribute("disabled","true");
//		 document.getElementById("resolutionRemarks").setAttribute("disabled","true");
		 document.getElementById("showDate").style.display='none';
		 document.getElementById("notshowDate").style.display='block';
		 document.getElementById("resolutionTime").setAttribute('readonly','true');
		 document.getElementById("resolutionTime").value='';
		 document.getElementById("resolutionRemarks").setAttribute('readonly','true');
		 document.getElementById("resolutionRemarks").value='';
	}
	else
	{
		document.getElementById("showDate").style.display='block';
		document.getElementById("notshowDate").style.display='none';
		document.getElementById("resolutionTime").readOnly=false;
		document.getElementById("resolutionRemarks").readOnly=false;
	}
	
}
function calculateInstallment()
{

	var frequency= document.getElementById("frequency").value;
	var requestedLoanTenure= document.getElementById("requestedLoanTenure").value;
	var parseTenure=0;
	var freqMonth=0;
	if(frequency=='M')
	{
		freqMonth=1;
	}
	else if(frequency=='B')
	{
		freqMonth=2;
	}
	else if(frequency=='Q')
	{
		freqMonth=3;
	}
	else if(frequency=='H')
	{
		freqMonth=6;
	}
	else if(frequency=='Y')
	{
		freqMonth=12;
	}
	
	if(frequency!='')
	{
		parsedFreq=parseInt(freqMonth);
		//alert("parsedFreq:----"+parsedFreq);
	}
	
	if(requestedLoanTenure!='')
	{
		parseTenure=parseInt(requestedLoanTenure);
		//alert("parseTenure:-----"+parseTenure);
	}
	
	//alert("parsedFreq:----- "+parsedFreq+"parseTenure:----- "+parseTenure);
	calInsat=parseTenure/parsedFreq;
	//alert("calInsat:---"+calInsat);
	if(isInt(calInsat))
	{
		document.getElementById("noOfInstall").value=calInsat;
	}
	else
	{
		alert("The Combination of tenure and frequency are incorrect");
		document.getElementById("noOfInstall").value='';
	}
		
	
	
	
}
function isInt(n) {
	   return n % 1 == 0;
	}

//function saveCIBIL(){
//	
//	var sourcepath=document.getElementById("path").value;
//	if(validateCibilReportDynaValidatorForm(document.getElementById("consumermasterform")))
//	{
//	document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerAdd.do?method=saveDocDetails";
//	document.getElementById("consumermasterform").submit();
//	}
//}



function removeRow()
{
DisButClass.prototype.DisButMethod();
var ch = document.getElementsByName('chk');
var list="";
var flag=0;
var sourcepath=document.getElementById("path").value;

	
	for(var i=0;i<ch.length;i++){
		
		if((ch[i].checked) == true){
		
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
	        document.getElementById("consumermasterform").action=sourcepath+"/cibilCustomerDelete.do?method=DeleteDocDetails&list="+list;
	    	document.getElementById("processingImage").style.display = '';
	        document.getElementById("consumermasterform").submit();

		        return true;
		 }
	
}

function validateDisBussnessDate()
{
	var bDate=document.getElementById("bDate").value;
	var formatD=document.getElementById("formatD").value;
	//alert("Bussiness Date: "+bDate);
	var disbursalDate =document.getElementById("disbursalDate").value;
	
	 dt1=getDateObject(bDate,formatD.substring(2, 3));
     dt2=getDateObject(disbursalDate,formatD.substring(2, 3));

	if (dt2<dt1)
	  {
		  alert("Disbursal Date can not be less than Bussiness Date("+bDate+")");
		  document.getElementById("disbursalDate").value='';
		  return false;
	  }
	else
	{//alert("ok");
		return true;
	}
}


function calculateFinalCharge(chargeAmount,loanAmount,marginAmount,calCulatedOn,chargeMethod,finalAmount,minChargeCalculatedOn,minChargeMethod,taxsInclusive,taxtRat1,taxtRat2,dealChargeTaxApp,dealChargeTdsApp,dealChargeTaxAmount1,dealChargeTaxAmount2,dealChargeMinChargeAmount,dealChargeTdsRate,dealChargeTdsAmount,dealChargeNetAmount,hiddenChargeAmount,hiddenFinalAmount)
{
	
	var chargeAmount1=0;
	var loanAmount1=0;
	var marginAmount1=0;
	var minChargeCalculatedOn1=0;
	var calCulatedOn1 =0;
	var calFinalAmount=0;
	var finalAmount1=0;
	var dealChargeMinChargeAmount1=0;
	var finalAmountT=0;
	var roundType= document.getElementById("roundType").value;
	var roundPara= document.getElementById("roundPara").value;
	var hiddenChargeAmount1=0;
	if(document.getElementById(hiddenChargeAmount).value!='')
	{
		hiddenChargeAmount1 = parseFloat(removeComma(document.getElementById(hiddenChargeAmount).value));
	
	}
	if(document.getElementById(chargeAmount).value!='')
	{
		chargeAmount1 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
	
	}
	//alert("hiddenChargeAmount1: "+hiddenChargeAmount1+" chargeAmount1: "+chargeAmount1);
	if(hiddenChargeAmount1!=chargeAmount1)
	{
		    document.getElementById(hiddenChargeAmount).value=chargeAmount1;
			if(document.getElementById(loanAmount).value!='')
			{
				loanAmount1 = parseFloat(removeComma(document.getElementById(loanAmount).value));
			}
			if(document.getElementById(minChargeCalculatedOn).value!='')
			{
				minChargeCalculatedOn1 =  parseFloat(removeComma(document.getElementById(minChargeCalculatedOn).value));
			}
			
			if(document.getElementById(marginAmount).value!='')
			{
				marginAmount1 =  parseFloat(removeComma(document.getElementById(marginAmount).value));
			}
			
			if(document.getElementById(calCulatedOn).value!='')
			{
				calCulatedOn1 =  parseFloat(removeComma(document.getElementById(calCulatedOn).value));
			}
			if(document.getElementById(dealChargeMinChargeAmount).value!='')
			{
				dealChargeMinChargeAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeMinChargeAmount).value));
			}
			//var calCulatedOn1 = removeComma(document.getElementById(calCulatedOn).value);
			var chargeMethod1 = document.getElementById(chargeMethod).value;
			var minChargeMethod1=document.getElementById(minChargeMethod).value;
			//alert("chargeAmount1: "+chargeAmount1+"loanAmount1: "+loanAmount1+"marginAmount1: "+marginAmount1+" calCulatedOn1: "+calCulatedOn1+"chargeMethod1: "+chargeMethod1);
			
			if(chargeMethod1=='PERCENTAGE')
			{

				 if(minChargeCalculatedOn1>chargeAmount1)
					{
					alert("Minimum Charge Percentage can not be less than "+minChargeCalculatedOn1);
					document.getElementById(chargeAmount).value=0;
					document.getElementById(hiddenChargeAmount).value=0;
					document.getElementById(finalAmount).value=0;
					
				}
				else
				{
					 if(checkRate(chargeAmount))
					  {
						  //alert("ok");  
						if(calCulatedOn1=='2')
						{
							calFinalAmount=loanAmount1*chargeAmount1/100;
						}
						else if(calCulatedOn1=='3')
						{
							calFinalAmount=marginAmount1*chargeAmount1/100;
						}
						else
						{
							calFinalAmount=0;
						}
						//alert(cal);
						calFinalAmount=getRoundedValue(calFinalAmount,roundType,roundPara,"");
						document.getElementById(finalAmount).value=calFinalAmount;
						
						//alert("Final Amount1: "+calFinalAmount.toFixed(2));
					  }
					  else
					  {
						  document.getElementById(finalAmount).value=0;
						
					  }
				}
			 
			}
			else
			{
				//alert("minChargeCalculatedOn1 "+minChargeCalculatedOn1+" chargeAmount1: "+chargeAmount1);
				if(minChargeCalculatedOn1>chargeAmount1)
				{
					//alert(val7+" val1: "+val1);
					alert("Minimum charge amount can not be less than "+minChargeCalculatedOn1);
					document.getElementById(finalAmount).value='';
					document.getElementById(hiddenChargeAmount).value='';
					//alert(val7+" val1: "+document.getElementById(val1).value);
					document.getElementById(chargeAmount).value='';
					document.getElementById(finalAmount).value='';
					//return true;
				}
				else
				{
					//alert("Final amount2 "+calFinalAmount.toFixed(2));
					calFinalAmount=chargeAmount1;
					//alert("Charge amount: "+chargeAmount1);
					calFinalAmount=getRoundedValue(calFinalAmount,roundType,roundPara,"");
					document.getElementById(finalAmount).value=calFinalAmount;
					
					//return false;
				}
				
			}
			if(minChargeMethod1=='PERCENTAGE')
			{
				var calFinalAmount=calFinalAmount*dealChargeMinChargeAmount1/100;
				if(document.getElementById(finalAmount).value!='')
				{
					finalAmount1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
				}
				if(finalAmount1<calFinalAmount)
				{
					//alert("Final amount3 "+calFinalAmount);
					calFinalAmount=getRoundedValue(calFinalAmount,roundType,roundPara,"");
					document.getElementById(finalAmount).value=calFinalAmount;
					
				}
			}
			else
			{
				if(calFinalAmount<dealChargeMinChargeAmount1)
				{
					//alert("Final amount4 "+dealChargeMinChargeAmount1);
					dealChargeMinChargeAmount1=getRoundedValue(dealChargeMinChargeAmount1,roundType,roundPara,"");
					document.getElementById(finalAmount).value=dealChargeMinChargeAmount1;
					
				}
			}
			
			var dealChargeTaxApp1=document.getElementById(dealChargeTaxApp).value;
			//alert("dealChargeTaxApp1: "+dealChargeTaxApp1);
			//alert("taxsInclusive: "+document.getElementById(taxsInclusive).value);
			var dealChargeTaxAmount11=0;
			var dealChargeTaxAmount21=0;
			var taxtRat11=0;
			var taxtRat21=0;
			
			if(document.getElementById(dealChargeTaxAmount1).value!='')
			{
				dealChargeTaxAmount11 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount1).value));
			}
			if(document.getElementById(dealChargeTaxAmount2).value!='')
			{
				dealChargeTaxAmount21 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount2).value));
			}
			if(document.getElementById(taxtRat1).value!='')
			{
				taxtRat11 =  parseFloat(removeComma(document.getElementById(taxtRat1).value));
			}
			if(document.getElementById(taxtRat2).value!='')
			{
				taxtRat21 =  parseFloat(removeComma(document.getElementById(taxtRat2).value));
			}
			if(dealChargeTaxApp1=='Y')
			{
				var taxsInclusive1=document.getElementById(taxsInclusive).value;
				if(taxsInclusive1=='YES')
				{
					if(document.getElementById(finalAmount).value!='')
					{
						finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
						finalAmT2 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
						finalAmT3 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					}
					dealChargeTaxAmount11=((100*finalAmT1)/(100+taxtRat11+taxtRat21))*taxtRat11/100;
					dealChargeTaxAmount11=getRoundedValue(dealChargeTaxAmount11,'U',0,"");
					dealChargeTaxAmount21=((100*finalAmT2)/(100+taxtRat11+taxtRat21))*taxtRat21/100;
					dealChargeTaxAmount21=getRoundedValue(dealChargeTaxAmount21,'U',0,"");
					calFinalAmount=finalAmT3;
				}
				else
				{
					//alert(document.getElementById(finalAmount).value);
					if(document.getElementById(finalAmount).value!='')
					{
						finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
						finalAmT2 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
						finalAmT3 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					}
					dealChargeTaxAmount11=finalAmT1*taxtRat11/100;
					dealChargeTaxAmount11=getRoundedValue(dealChargeTaxAmount11,'U',0,"");
					dealChargeTaxAmount21=finalAmT2*taxtRat21/100;
					dealChargeTaxAmount21=getRoundedValue(dealChargeTaxAmount21,'U',0,"");
					calFinalAmount=finalAmT3+dealChargeTaxAmount11+dealChargeTaxAmount21;
					//alert("Final amount5 "+calFinalAmount);
				}
				
				
				document.getElementById(dealChargeTaxAmount1).value=dealChargeTaxAmount11;
				document.getElementById(dealChargeTaxAmount2).value=dealChargeTaxAmount21;
				document.getElementById(finalAmount).value=calFinalAmount;
				
				//alert("Final amount6 "+calFinalAmount.toFixed(2));
			}
			
			var dealChargeTdsApp1=document.getElementById(dealChargeTdsApp).value;
			var dealChargeTdsAmount1=0;
			var dealChargeTdsRate1=0;
			if(document.getElementById(dealChargeTdsAmount).value!='')
			{
				dealChargeTdsAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsAmount).value));
			}
			if(document.getElementById(dealChargeTdsRate).value!='')
			{
				dealChargeTdsRate1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsRate).value));
			}
			
			if(dealChargeTdsApp1=='Y')
			{
				if(document.getElementById(finalAmount).value!='')
				{
					finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					
				}
				dealChargeTdsAmount1=finalAmT1*dealChargeTdsRate1/100;
				dealChargeTdsAmount1=getRoundedValue(dealChargeTdsAmount1,'U',0,"");
				document.getElementById(dealChargeTdsAmount).value=dealChargeTdsAmount1;
			}
			if(document.getElementById(finalAmount).value!='')
			{
				finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
				
			}
			document.getElementById(dealChargeNetAmount).value=(finalAmT1-dealChargeTdsAmount1);
			document.getElementById(hiddenFinalAmount).value=document.getElementById(finalAmount).value;
  }
	
}

function calculateChargeAmount(chargeCode,chargeAmount,loanAmount,marginAmount,calCulatedOn,chargeMethod,finalAmount,minChargeCalculatedOn,minChargeMethod,taxsInclusive,taxtRat1,taxtRat2,dealChargeTaxApp,dealChargeTdsApp,dealChargeTaxAmount1,dealChargeTaxAmount2,dealChargeMinChargeAmount,dealChargeTdsRate,dealChargeTdsAmount,dealChargeNetAmount,hiddenChargeAmount,hiddenFinalAmount)
{	
	
	var chargeAmount1=0;
	var loanAmount1=0;
	var marginAmount1=0;
	var minChargeCalculatedOn1=0;
	var calCulatedOn1 =0;
	var calChargeAmount=0;
	var finalAmount1=0;
	var dealChargeMinChargeAmount1=0;
	var finalAmountT=0;
	var chCode=parseInt(document.getElementById(chargeCode).value);
	//alert("chargeCode  : "+chCode);
	var roundType= document.getElementById("roundType").value;
	var roundPara= document.getElementById("roundPara").value;
	var hiddenFinalAmount1=0
	//alert("aa");
	if(document.getElementById(finalAmount).value==null || document.getElementById(finalAmount).value==undefined || document.getElementById(finalAmount).value=='')
	{
		document.getElementById(finalAmount).value=0;
	}		
	
	if(document.getElementById(hiddenFinalAmount).value!='')
	{
		hiddenFinalAmount1 = parseFloat(removeComma(document.getElementById(hiddenFinalAmount).value));
	
	}
	if(document.getElementById(finalAmount).value!='')
	{
		finalAmount1 = parseFloat(removeComma(document.getElementById(finalAmount).value));
	
	}
	if(hiddenFinalAmount1!=finalAmount1)
	{
			document.getElementById(hiddenFinalAmount).value=finalAmount1;
			
			if(document.getElementById(loanAmount).value!='')
			{
				loanAmount1 = parseFloat(removeComma(document.getElementById(loanAmount).value));
			//	loanAmount2=document.getElementById(loanAmount).value;
			}
			if(document.getElementById(minChargeCalculatedOn).value!='')
			{
				minChargeCalculatedOn1 =  parseFloat(removeComma(document.getElementById(minChargeCalculatedOn).value));
			}
			
			if(document.getElementById(marginAmount).value!='')
			{
				marginAmount1 =  parseFloat(removeComma(document.getElementById(marginAmount).value));
			}
			
			if(document.getElementById(calCulatedOn).value!='')
			{
				calCulatedOn1 =  parseFloat(removeComma(document.getElementById(calCulatedOn).value));
			}
			if(document.getElementById(dealChargeMinChargeAmount).value!='')
			{
				dealChargeMinChargeAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeMinChargeAmount).value));
			}
			//var calCulatedOn1 = removeComma(document.getElementById(calCulatedOn).value);
			var chargeMethod1 = document.getElementById(chargeMethod).value;
			var minChargeMethod1=document.getElementById(minChargeMethod).value;
			//alert("chargeAmount1: "+chargeAmount1+"loanAmount1: "+loanAmount1+"marginAmount1: "+marginAmount1+" calCulatedOn1: "+calCulatedOn1+"chargeMethod1: "+chargeMethod1);
			
			if(chargeMethod1=='PERCENTAGE')
			{
				if(chCode==101 || chCode==102)
					loanAmount1=finalAmount1;
				if(finalAmount1 > loanAmount1)
				{
					alert("Final Amount should not be greater than requested loan amount : "+loanAmount1);
					document.getElementById(finalAmount).value=0;
					document.getElementById(chargeAmount).value=0;
					document.getElementById(hiddenFinalAmount).value=0;
				}
				else if(finalAmount1 < minChargeCalculatedOn1)
				{
					alert("Final Amount should not be less than minimum charge amount : "+minChargeCalculatedOn1);
					document.getElementById(finalAmount).value=0;
					document.getElementById(chargeAmount).value=0;
					document.getElementById(hiddenFinalAmount).value=0;
					
					
				}
				else
				{
					//alert("finalAmount: "+finalAmount1);
					 if(finalAmount1!=0)
					  {
						// alert("calCulatedOn1: "+calCulatedOn1);
						if(calCulatedOn1=='2')
						{
							calChargeAmount=finalAmount1*100/loanAmount1;
							
						}
						else if(calCulatedOn1=='3')
						{
							calChargeAmount=finalAmount1*100/marginAmount1;
						}
						else
						{
							calChargeAmount=0.0;
						}
						calChargeAmount=getRoundedValue(calChargeAmount,roundType,roundPara,chargeMethod1);
						document.getElementById(chargeAmount).value=calChargeAmount;
						//alert("Charge Amount1: "+calChargeAmount.toFixed(2));
					  }
					  else
					  {
						  document.getElementById(chargeAmount).value=0.0;
					  }
				}
			}
			else
			{
		//		if(minChargeCalculatedOn1>finalAmount1)
		//		{
		//			//alert(val7+" val1: "+val1);
		//			alert("Minimum Final amount can't be less than "+minChargeCalculatedOn1);
		//			document.getElementById(finalAmount).value='';
		//			//alert(val7+" val1: "+document.getElementById(val1).value);
		//			document.getElementById(chargeAmount).value='';
		//			return true;
		//		}
				if(chCode==101 || chCode==102)
					loanAmount1=finalAmount1;
				if(finalAmount1 > loanAmount1)
				{
					//alert("Minimum Final Percentage can't be less than "+minChargeCalculatedOn1);
					alert("Final Amount should not be greater than requested loan amount : "+loanAmount1);
					document.getElementById(finalAmount).value=0;
					document.getElementById(chargeAmount).value=0;
					document.getElementById(hiddenFinalAmount).value=0;
				}
				else if(finalAmount1 < minChargeCalculatedOn1)
				{
					alert("Final Amount should not be less than minimum charge amount : "+minChargeCalculatedOn1);
					document.getElementById(finalAmount).value=0;
					document.getElementById(chargeAmount).value=0;
					document.getElementById(hiddenFinalAmount).value=0;
				}
				else
				{
					
					              
					//alert("finalAmount1: "+finalAmount1+"roundType: "+roundType+"roundPara: "+roundPara);
					finalAmount1=getRoundedValue(finalAmount1,roundType,roundPara,chargeMethod1);
					calChargeAmount=finalAmount1; 
					//alert("calChargeAmount "+calChargeAmount);
					document.getElementById(chargeAmount).value=calChargeAmount;
					//return false;
				}
				
			}
			if(minChargeMethod1=='PERCENTAGE')
			{
				var calChargeAmount=calChargeAmount*dealChargeMinChargeAmount1/100;
				if(document.getElementById(chargeAmount).value!='')
				{
					chargeAmount1 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
				}
				if(chargeAmount1<calChargeAmount)
				{
					//alert("Final amount3 "+calFinalAmount.toFixed(2));
					calChargeAmount=getRoundedValue(calChargeAmount,roundType,roundPara,minChargeMethod1);
					document.getElementById(chargeAmount).value=calChargeAmount;
				}
			}
			else
			{
				if(calChargeAmount<dealChargeMinChargeAmount1)
				{
					//alert("Final amount4 "+dealChargeMinChargeAmount1.toFixed(2));
					dealChargeMinChargeAmount1=getRoundedValue(dealChargeMinChargeAmount1,roundType,roundPara,minChargeMethod1);
					document.getElementById(chargeAmount).value=dealChargeMinChargeAmount1;
				}
			}
			//alert(document.getElementById(dealChargeTaxApp).value);
			var dealChargeTaxApp1=document.getElementById(dealChargeTaxApp).value;
			var dealChargeTaxAmount11=0;
			var dealChargeTaxAmount21=0;
			var taxtRat11=0;
			var taxtRat21=0;
		//	alert(dealChargeTaxApp1);
		
			if(document.getElementById(dealChargeTaxAmount1).value!='')
			{
				dealChargeTaxAmount11 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount1).value));
			}
			if(document.getElementById(dealChargeTaxAmount2).value!='')
			{
				dealChargeTaxAmount21 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount2).value));
			}
			if(document.getElementById(taxtRat1).value!='')
			{
				taxtRat11 =  parseFloat(removeComma(document.getElementById(taxtRat1).value));
			}
			if(document.getElementById(taxtRat2).value!='')
			{
				taxtRat21 =  parseFloat(removeComma(document.getElementById(taxtRat2).value));
			}
			//alert("dealChargeTaxApp1 "+dealChargeTaxApp1);
			if(dealChargeTaxApp1=='Y')
			{
				
				var taxsInclusive1=document.getElementById(taxsInclusive).value;
				//alert("taxsInclusive1 "+taxsInclusive1);
				if(taxsInclusive1=='YES')
				{
					if(document.getElementById(chargeAmount).value!='')
					{
						chargeAmT1 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
						chargeAmT2 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
						chargeAmT3 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
					}
					dealChargeTaxAmount11=((100*chargeAmT1)/(100+taxtRat11+taxtRat21))*taxtRat11/100;
					dealChargeTaxAmount11=getRoundedValue(dealChargeTaxAmount11,'U',0,"");
					dealChargeTaxAmount21=((100*chargeAmT2)/(100+taxtRat11+taxtRat21))*taxtRat21/100;
					dealChargeTaxAmount21=getRoundedValue(dealChargeTaxAmount21,'U',0,"");
					calChargeAmount=chargeAmT3;
					// Changes by Himanshu to resolve core bug at charges
//					dealChargeTaxAmount11 = loanAmount1
		//			var orgChargeAmt=loanAmount1*parseFloat(removeComma(document.getElementById(chargeAmount).value))/100;
		//			dealChargeTaxAmount11 = orgChargeAmt*taxtRat11/(100+taxtRat11+taxtRat21);
		//			dealChargeTaxAmount21 = orgChargeAmt*taxtRat21/(100+taxtRat11+taxtRat21);
//					dealChargeTaxAmount11=((100*chargeAmT1)/(100+taxtRat11+taxtRat21))*taxtRat11/100;
		//			dealChargeTaxAmount11=getRoundedValue(dealChargeTaxAmount11,'U',0);
//					dealChargeTaxAmount21=((100*chargeAmT2)/(100+taxtRat11+taxtRat21))*taxtRat21/100;
		//			dealChargeTaxAmount21=getRoundedValue(dealChargeTaxAmount21,'U',0);

			//		calChargeAmount=chargeAmT3;
				}
				else
				{
					//alert("chargeAmount: "+document.getElementById(chargeAmount).value);
					//alert("finalAmount: "+document.getElementById(finalAmount).value);
					
					if(document.getElementById(finalAmount).value!=''){
						var finalAMt=parseFloat(removeComma(document.getElementById(finalAmount).value));
						var chrgeAmt1=finalAMt*100;
						var chrgeAmt2=100+taxtRat11+taxtRat21;		
						var chrgeAmt=chrgeAmt1/chrgeAmt2;
						chrgeAmt=Math.floor(chrgeAmt);
						calChargeAmount=chrgeAmt;
						document.getElementById(chargeAmount).value=calChargeAmount;


					}
					if(document.getElementById(chargeAmount).value!='')
					{
						chargeAmT1 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
						chargeAmT2 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
						chargeAmT3 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
					}
					dealChargeTaxAmount11=(chargeAmT1*taxtRat11/100);
					dealChargeTaxAmount11=getRoundedValue(dealChargeTaxAmount11,'U',0,"");
					dealChargeTaxAmount21=(chargeAmT2*taxtRat21/100);
					dealChargeTaxAmount21=getRoundedValue(dealChargeTaxAmount21,'U',0,"");
					
					var finalChargeAmt=0;
					finalChargeAmt=parseFloat(removeComma(document.getElementById(finalAmount).value));
					
					calChargeAmount=finalChargeAmt-(dealChargeTaxAmount11+dealChargeTaxAmount21);

				//	alert("Himanshu---");
					
					//	Changes started by Himanshu
					if (minChargeMethod1=='PERCENTAGE'){
						var finalChargeAmt = parseFloat(removeComma(document.getElementById(finalAmount).value));
				//		alert(finalChargeAmt+"::" + taxtRat11 + "::"+taxtRat21);
						var taxAmount1 = finalChargeAmt*taxtRat11/(100+taxtRat11+taxtRat21);
						var taxAmount2 = finalChargeAmt*taxtRat21/(100+taxtRat11+taxtRat21);
						var orgChargeAmount = finalChargeAmt-taxAmount1-taxAmount2;
						if(calCulatedOn1=='2')
						{

							calChargeAmount=Math.round((orgChargeAmount*100/loanAmount1)*100)/100;
							
						}

						else if(calCulatedOn1=='3')
						{
							calChargeAmount=Math.round((orgChargeAmount*100/marginAmount1)*100)/100;
						}
						else
						{
							orgChargeAmount=0.0;
						}
						
					dealChargeTaxAmount11=taxAmount1;
					dealChargeTaxAmount21=taxAmount2;
						
					}



					
					//	Changes ended by Himanshu
					
					//alert("chargeAmT3 "+chargeAmT3+"\ndealChargeTaxAmount11- "+dealChargeTaxAmount11+"\n dealChargeTaxAmount21 "+dealChargeTaxAmount21);
					//alert("calChargeAmount "+calChargeAmount);
				}
				
				
				document.getElementById(dealChargeTaxAmount1).value=dealChargeTaxAmount11;
				document.getElementById(dealChargeTaxAmount2).value=dealChargeTaxAmount21;
				document.getElementById(chargeAmount).value=calChargeAmount;
				//alert("Final amount6 "+calFinalAmount.toFixed(2));
			}
			
			var dealChargeTdsApp1=document.getElementById(dealChargeTdsApp).value;
			var dealChargeTdsAmount1=0;
			var dealChargeTdsRate1=0;
			if(document.getElementById(dealChargeTdsAmount).value!='')
			{
				dealChargeTdsAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsAmount).value));
			}
			if(document.getElementById(dealChargeTdsRate).value!='')
			{
				dealChargeTdsRate1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsRate).value));
			}
			
			if(dealChargeTdsApp1=='Y')
			{
				//alert("B");
				if(document.getElementById(chargeAmount).value!='')
				{
					chargeAmT1 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
					
				}
				dealChargeTdsAmount1=chargeAmT1*dealChargeTdsRate1/100;
				document.getElementById(dealChargeTdsAmount).value=dealChargeTdsAmount1;
			}
			if(document.getElementById(chargeAmount).value!='')
			{
				//alert(document.getElementById(chargeAmount).value);
				chargeAmT1 =  parseFloat(removeComma(document.getElementById(chargeAmount).value));
				//alert(chargeAmT1);
				
			}
			document.getElementById(dealChargeNetAmount).value=(chargeAmT1-dealChargeTdsAmount1);
			document.getElementById(hiddenChargeAmount).value=document.getElementById(chargeAmount).value;
	}
}



function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function saveCharges()
{	//alert("save");
	
	var source=document.getElementById('source').value;
	if(source=='N')
	{
		alert("First Refresh the charges");
		return false;
	}
	
	DisButClass.prototype.DisButMethod();	
	
	var chMethod = document.getElementsByName('minChargeMethod');
	var chargeValidationFlag=0;
	for(var i=0;i<chMethod.length;i++)
	{
		var chargeAmount="chargeAmount"+i;
		var loanAmount="loanAmount"+i;
		var marginAmount="marginAmount"+i;
		var calCulatedOn="calCulatedOn"+i;
		var chargeMethod="chargeMethod"+i;
		var finalAmount="finalAmount"+i;
		var minChargeCalculatedOn="minChargeCalculatedOn"+i;
		var minChargeMethod="minChargeMethod"+i;
		var taxsInclusive="taxsInclusive"+i;
		var taxtRat1="taxtRat1"+i;
		var taxtRat2="taxtRat2"+i;
		var dealChargeTaxApp="dealChargeTaxApp"+i;
		var dealChargeTdsApp="dealChargeTdsApp"+i;
		var dealChargeTaxAmount1="dealChargeTaxAmount1"+i;
		var dealChargeTaxAmount2="dealChargeTaxAmount2"+i;
		var dealChargeMinChargeAmount="dealChargeMinChargeAmount"+i;
		var dealChargeTdsRate="dealChargeTdsRate"+i;
		var dealChargeTdsAmount="dealChargeTdsAmount"+i;
		var dealChargeNetAmount="dealChargeNetAmount"+i;
		var chargeDesc="chargeDesc"+i;
		var description=document.getElementById(chargeDesc).value;
		
		
		//alert("description :"+description);
		if(document.getElementById(chargeAmount).value=='')
		{
			alert(description+"'s minimum charge amount can't be empty .");
			document.getElementById(finalAmount).value=0;
			document.getElementById(chargeAmount).value=0;
			chargeValidationFlag=1;
			document.getElementById("saveButton").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			break;
		}
		var chargeAmount1=0;
		var loanAmount1=0;
		var marginAmount1=0;
		var minChargeCalculatedOn1=0;
		var calCulatedOn1 =0;
		var calFinalAmount=0;
		var finalAmount1=0;
		var dealChargeMinChargeAmount1=0;
		var finalAmountT=0;
		
		if(document.getElementById(chargeAmount).value!='')
			chargeAmount1 = parseFloat(removeComma(document.getElementById(chargeAmount).value));
		if(document.getElementById(loanAmount).value!='')
			loanAmount1 = parseFloat(removeComma(document.getElementById(loanAmount).value));
		if(document.getElementById(minChargeCalculatedOn).value!='')
			minChargeCalculatedOn1 =  parseFloat(removeComma(document.getElementById(minChargeCalculatedOn).value));
		if(document.getElementById(marginAmount).value!='')
			marginAmount1 =  parseFloat(removeComma(document.getElementById(marginAmount).value));
		if(document.getElementById(calCulatedOn).value!='')
			calCulatedOn1 =  parseFloat(removeComma(document.getElementById(calCulatedOn).value));
		if(document.getElementById(dealChargeMinChargeAmount).value!='')
			dealChargeMinChargeAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeMinChargeAmount).value));
		
		var chargeMethod1 = document.getElementById(chargeMethod).value;
		var minChargeMethod1=document.getElementById(minChargeMethod).value;
		//alert(chargeMethod1);
		if(chargeMethod1=='PERCENTAGE')
		{
			if(chargeAmount1==null || chargeAmount1==undefined || chargeAmount1=='')
				{
					chargeAmount1=0;
				}
				//alert(val7+" val1: "+val1);
			if(chargeAmount1>100)
				{
					alert("Please Enter the value b/w 0 to 100");
					document.getElementById(chargeAmount).value=0;
				    document.getElementById(finalAmount).value=0;
					chargeValidationFlag=1;
					document.getElementById("saveButton").removeAttribute("disabled");
					DisButClass.prototype.EnbButMethod();
					break;
					
				}
			else if(minChargeCalculatedOn1>chargeAmount1)
			{
				alert(description+"'s Minimum Charge Percentage can't be less than "+minChargeCalculatedOn1);
				document.getElementById(chargeAmount).value=0;
				document.getElementById(finalAmount).value=0;
				chargeValidationFlag=1;
				document.getElementById("saveButton").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				break;
			}
//			else
//			{
//				 if(checkRate(chargeAmount))
//				 {
//					 	if(calCulatedOn1=='2')
//					 		calFinalAmount=loanAmount1*chargeAmount1/100;
//					 	else 
//					 		if(calCulatedOn1=='3')
//					 			calFinalAmount=marginAmount1*chargeAmount1/100;
//					 		else
//					 			calFinalAmount=0;
//					 	
//					 	document.getElementById(finalAmount).value=calFinalAmount.toFixed(2);
//				 }
//				 else
//					document.getElementById(finalAmount).value=0;				
//			}
		 
		}
		else
		{
			if(minChargeCalculatedOn1>chargeAmount1)
			{
				alert(description+"'s minimum charge amount can't be less than "+minChargeCalculatedOn1);
				document.getElementById(finalAmount).value='';
				document.getElementById(chargeAmount).value='';
				chargeValidationFlag=1;
				document.getElementById("saveButton").removeAttribute("disabled");
				DisButClass.prototype.EnbButMethod();
				break;
			}
//			else
//			{
//				calFinalAmount=chargeAmount1;
//				document.getElementById(finalAmount).value=calFinalAmount.toFixed(2);
//			}
			
		}
		/*
		if(minChargeMethod1=='PERCENTAGE')
		{
			var calFinalAmount=calFinalAmount*dealChargeMinChargeAmount1/100;
			if(document.getElementById(finalAmount).value!='')
				finalAmount1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
			if(finalAmount1<calFinalAmount)
				document.getElementById(finalAmount).value=calFinalAmount.toFixed(2);
		}
		else
		{
			if(calFinalAmount<dealChargeMinChargeAmount1)
				document.getElementById(finalAmount).value=dealChargeMinChargeAmount1.toFixed(2);
		}	
		var dealChargeTaxApp1=document.getElementById(dealChargeTaxApp).value;
		var dealChargeTaxAmount11=0;
		var dealChargeTaxAmount21=0;
		var taxtRat11=0;
		var taxtRat21=0;
		if(document.getElementById(dealChargeTaxAmount1).value!='')
			dealChargeTaxAmount11 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount1).value));
		if(document.getElementById(dealChargeTaxAmount2).value!='')
			dealChargeTaxAmount21 =  parseFloat(removeComma(document.getElementById(dealChargeTaxAmount2).value));
		if(document.getElementById(taxtRat1).value!='')
			taxtRat11 =  parseFloat(removeComma(document.getElementById(taxtRat1).value));
		if(document.getElementById(taxtRat2).value!='')
			taxtRat21 =  parseFloat(removeComma(document.getElementById(taxtRat2).value));
		if(dealChargeTaxApp1=='Y')
		{
			var taxsInclusive1=document.getElementById(taxsInclusive).value;
			if(taxsInclusive1=='Y')
			{
				if(document.getElementById(finalAmount).value!='')
				{
					finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					finalAmT2 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					finalAmT3 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
				}
				dealChargeTaxAmount11=((100*finalAmT1)/(100+taxtRat11+taxtRat21))*taxtRat11/100;
				dealChargeTaxAmount21=((100*finalAmT2)/(100+taxtRat11+taxtRat21))*taxtRat21/100;
				calFinalAmount=finalAmT3;
			}
			else
			{
				if(document.getElementById(finalAmount).value!='')
				{
					finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					finalAmT2 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
					finalAmT3 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
				}
				dealChargeTaxAmount11=finalAmT1*taxtRat11/100;
				dealChargeTaxAmount21=finalAmT2*taxtRat21/100;
				calFinalAmount=finalAmT3+dealChargeTaxAmount11+dealChargeTaxAmount21;
			}			
			document.getElementById(dealChargeTaxAmount1).value=dealChargeTaxAmount11.toFixed(2);
			document.getElementById(dealChargeTaxAmount2).value=dealChargeTaxAmount21.toFixed(2);
			document.getElementById(finalAmount).value=calFinalAmount.toFixed(2);
		}		
		var dealChargeTdsApp1=document.getElementById(dealChargeTdsApp).value;
		var dealChargeTdsAmount1=0;
		var dealChargeTdsRate1=0;
		if(document.getElementById(dealChargeTdsAmount).value!='')
			dealChargeTdsAmount1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsAmount).value));
		if(document.getElementById(dealChargeTdsRate).value!='')
			dealChargeTdsRate1 =  parseFloat(removeComma(document.getElementById(dealChargeTdsRate).value));
		if(dealChargeTdsApp1=='Y')
		{
			if(document.getElementById(finalAmount).value!='')
			{
				finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
				
			}
			dealChargeTdsAmount1=finalAmT1*dealChargeTdsRate1/100;
			document.getElementById(dealChargeTdsAmount).value=dealChargeTdsAmount1.toFixed(2);
		}
		if(document.getElementById(finalAmount).value!='')
			finalAmT1 =  parseFloat(removeComma(document.getElementById(finalAmount).value));
		document.getElementById(dealChargeNetAmount).value=(finalAmT1-dealChargeTdsAmount1).toFixed(2);	
		*/
	}
	//Start By Prashant
	//commented by neeraj tripathi
	
	if(chargeValidationFlag==0){
	var chargeC101=document.getElementsByName('chargeCode');
	var chargeC102=document.getElementsByName('chargeCode');
	var chargeC51=document.getElementsByName('chargeCode');
	var finalCharge101=0.00;
	var finalCharge102=0.00;
	var finalCharge51=0.00;
	var marginAmount101=0.00;
	var marginAmount102=0.00;
	var marginAmount51=0.00;
	
	var flag101=false;
	var flag102=false;
	var flag51=false;
	
	var finalChargeAmt=0.00;
	for(var i=0;i<chargeC101.length;i++)
	{
		var finalAmt=0.00;
		if(document.getElementById("finalAmount"+i).value!='')
		{
			finalAmt=document.getElementById("finalAmount"+i).value;
		}
		//alert("finalAmt: "+finalAmt);
		if(document.getElementById("chargeCode"+i).value=='101' && finalAmt!=0)
		{
			flag101=true;
			if(document.getElementById("finalAmount"+i).value!='')
				finalCharge101 =  parseFloat(removeComma(document.getElementById("finalAmount"+i).value));
			if(document.getElementById("marginAmount"+i).value!='')
				marginAmount101 =  parseFloat(removeComma(document.getElementById("marginAmount"+i).value));
			break;
		}
	}
	for(var i=0;i<chargeC102.length;i++)
	{
		var finalAmt=0.00;
		if(document.getElementById("finalAmount"+i).value!='')
		{
			finalAmt=document.getElementById("finalAmount"+i).value;
		}
		//alert("finalAmt: "+finalAmt);
		if(document.getElementById("chargeCode"+i).value=='102' && finalAmt!=0)
		{
			flag102=true;
			if(document.getElementById("finalAmount"+i).value!='')
				finalCharge102 =  parseFloat(removeComma(document.getElementById("finalAmount"+i).value));
			if(document.getElementById("marginAmount"+i).value!='')
				marginAmount102 =  parseFloat(removeComma(document.getElementById("marginAmount"+i).value));
			break;
		}
	}
	for(var i=0;i<chargeC51.length;i++)
	{
		var finalAmt=0.00;
		if(document.getElementById("finalAmount"+i).value!='')
		{
			finalAmt=document.getElementById("finalAmount"+i).value;
		}
		//alert("finalAmt: "+finalAmt);
		if(document.getElementById("chargeCode"+i).value=='51' && finalAmt!=0)
		{
			flag51=true;
			if(document.getElementById("finalAmount"+i).value!='')
				finalCharge51 =  parseFloat(removeComma(document.getElementById("finalAmount"+i).value));
			if(document.getElementById("marginAmount"+i).value!='')
				marginAmount51 =  parseFloat(removeComma(document.getElementById("marginAmount"+i).value));
			break;
		}
	}
	
/*	if(flag101==true && finalCharge101!=marginAmount101)
	{
		alert("Margin Money Received from customer is not equal to Margin Money at Loan details screen");
		document.getElementById("saveButton").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
	
		return false;
	}
	if(flag102==true && finalCharge102!=marginAmount102)
	{
		alert("Margin Money Payable to Dealer is not equal to Margin Money at Loan details screen");	
		document.getElementById("saveButton").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	*/
	//alert("flag51 : " + flag51);
	if(flag101==true && flag102==true && flag51==true)
    {
		finalChargeAmt=finalCharge102+finalCharge51;
		if(finalCharge101!=finalChargeAmt)
		{
			alert("Margin Money Received from customer is not equal to Margin Money Payable to Dealer/Manufacturer.");	
			document.getElementById("saveButton").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}		
	}
	else if(flag101==true && (flag102==true || flag51==true))
	{
		finalChargeAmt=finalCharge102+finalCharge51;
		//alert("finalChargeAmt : " + finalChargeAmt);
		if(finalCharge101!=finalChargeAmt)
		{
			alert("Margin Money Received from customer is not equal to Margin Money Payable to Dealer/Manufacturer.");	
			document.getElementById("saveButton").removeAttribute("disabled");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	else if(flag101==true || flag102==true || flag51==true)
	{
		alert("Margin Money Received from customer and Margin Money Payable to Dealer/Manufacturer are required.");	
		document.getElementById("saveButton").removeAttribute("disabled");
		DisButClass.prototype.EnbButMethod();
		return false;
	}

	//End By Prashant
	
	var contextPath =document.getElementById("contextPath").value;
	document.getElementById("ChrageForm").action=contextPath+"/chargesProcessAction.do";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ChrageForm").submit();
}
}

function refreshCharges()
{
	document.getElementById("refreshButton").removeAttribute("disabled","true");
	var contextPath =document.getElementById("contextPath").value;
	document.getElementById("ChrageForm").action=contextPath+"/chargeBehindAction.do?method=refreshCharge";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("ChrageForm").submit();
}
function refreshChargesInCm()
{
	var contextPath =document.getElementById("contextPath").value;
	document.getElementById("ChrageForm").action=contextPath+"/chargeInCMBehindAction.do?method=refreshChargeIncm";
	document.getElementById("ChrageForm").submit();
}

//function onSaveofPD(alert1)
//{
//	
//	var basePath=document.getElementById("contextPath").value;
//	
//	if(validatePersonalDiscussionDynaValidatorForm(document.getElementById("pdForm")))
//	{
//		if(document.getElementById("followUp").value=='N')
//		{
//			document.getElementById("pdForm").action=basePath+"/personalDiscussionForSaveAction.do";
//		
//			document.getElementById("pdForm").submit();
//		}
//		else if(document.getElementById("followUp").value=='Y')
//		{
//			if(cpNotepadValidate())
//			{
//				var formatD=document.getElementById("formatD").value;
//				var pdDate = document.getElementById("pdDate").value;
//				var followupDate = document.getElementById("followupDate").value;
//				var dt1=getDateObject(pdDate,formatD.substring(2, 3));
//				var dt2=getDateObject(followupDate,formatD.substring(2, 3));
//				if (dt2<dt1)
//				{
//					alert(alert1);
//					return false;
//				}
//				document.getElementById("pdForm").action=basePath+"/personalDiscussionForSaveAction.do";
//				document.getElementById("pdForm").submit();
//			}
//		}
//		return true;
//	}
//}
//function getVendor()
//{
//	//alert("In getVendor "+document.getElementById("vendorCodeButton"));
//	var ven=document.getElementById("source").value;
//	if(ven=='VENDOR')
//	{
//		document.getElementById("sourcedesc").value='';
//		document.getElementById("vendorCode").value='';
//		document.getElementById("lbxvendorCode").value='';
//		document.getElementById("sourcedesc").setAttribute("readOnly", "true");
//		document.getElementById("vendorCodeButton").removeAttribute("disabled", "true");
//		document.getElementById("vendorCode").setAttribute("readOnly", "true");
//				
//	}
//	else
//	{
//		document.getElementById("sourcedesc").value='';
//		document.getElementById("vendorCode").value='';
//		document.getElementById("lbxvendorCode").value='';
//		document.getElementById("sourcedesc").removeAttribute("readOnly", "true");
//		document.getElementById("vendorCodeButton").setAttribute("disabled", "true");
//		document.getElementById("vendorCode").setAttribute("readOnly", "true");
//	}
//	
//}

function validateAssetCost()
{
	var assetCost = 0;
    var reqLoanAmt = 0;
    if(document.getElementById("assetCost").value!='')
    {
    	assetCost = removeComma(document.getElementById("assetCost").value);
    }
    if(document.getElementById("requestedLoanAmount").value!='')
    {
    	reqLoanAmt = removeComma(document.getElementById("requestedLoanAmount").value);
    }
//    alert("assetCost: "+assetCost);
//    alert("reqLoanAmt: "+reqLoanAmt);
    if(assetCost<reqLoanAmt)
    {
    	alert("Asset Cost cannot be less than Loan Requested Amount");
    	document.getElementById("assetCost").value='';
    	document.getElementById("assetCost").focus();
    	return false;
    }
}

function newTradCap()
{
	//alert("ok");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("tradeCheckCapForm").action=sourcepath+"/tradeCheck.do?method=tradeCheckNew";
	document.getElementById("tradeCheckCapForm").submit();
	// return true;
}



function searchCollateralVarificationData()
{
	DisButClass.prototype.DisButMethod();
	//alert("Searching Data");
	var sourcepath=document.getElementById("contextPath").value;
	var dealNo=document.getElementById("dealNo").value;
	var customername=document.getElementById("customername").value;
	var lbxProductID=document.getElementById("lbxProductID").value;
	var initiationDate=document.getElementById("initiationDate").value;
	var lbxscheme=document.getElementById("lbxscheme").value;
	var userName=document.getElementById("reportingToUserId").value;
	if(userName!='' || dealNo!=''||customername!=''||lbxProductID!=''||lbxscheme!='' || initiationDate != '')
	{
		if(dealNo != '' && customername!='' && customername.length < 3){
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(customername !='' && customername.length >= 3)
		{
			document.getElementById("commonForm").action=sourcepath+"/collateralVerificationCapturingSearch.do?method=searchCollateralVerificationCapturing&userId="+userName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("commonForm").submit();
			return true;
		}else if(customername == ''){
			document.getElementById("commonForm").action=sourcepath+"/collateralVerificationCapturingSearch.do?method=searchCollateralVerificationCapturing&userId="+userName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("commonForm").submit();
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function searchCollateralCompletionData()
{
	//alert("Searching Data");
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var dealNo=document.getElementById("dealNo").value;
	var customername=document.getElementById("customername").value;
	var lbxProductID=document.getElementById("lbxProductID").value;
	var initiationDate=document.getElementById("initiationDate").value;
	var lbxscheme=document.getElementById("lbxscheme").value;
	if(dealNo!=''||customername!=''||lbxProductID!=''||lbxscheme!='' || initiationDate != '')
	{
		if(dealNo != '' && customername!='' && customername.length < 3){
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(customername !='' && customername.length >= 3)
		{
			document.getElementById("commonForm").action=sourcepath+"/collateralVerificationCompletionSearch.do?method=searchCollateralVerificationCompletion";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("commonForm").submit();
			return true;
		}else if(customername == ''){
			document.getElementById("commonForm").action=sourcepath+"/collateralVerificationCompletionSearch.do?method=searchCollateralVerificationCompletion";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("commonForm").submit();
			return true;
		}else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function saveCollateralCompletionDecision()
{
	//alert("save");
	DisButClass.prototype.DisButMethod();
	var remarks = document.getElementById("remarks").value;
	if(remarks=="")
	{
		alert("Remarks are Mandatory");
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	else
	{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("collateralRemarksForm").action=sourcepath+"/collateralCheck.do?method=saveCollateralCompletionDecision";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("collateralRemarksForm").submit();
		return true;
	}
}

function checkInstallmentsInDeal()
{
	var total=0; 
	var loanNoofInstall=document.getElementById("noOfInstall").value;
	var installments=document.getElementById("installments").value;
	if(document.getElementById("installments").value!='')
	{
		total=parseInt(loanNoofInstall);
	}
	if(document.getElementById("installments").value!='')
	{
		inst=parseInt(installments);
	}
	
	if(inst<total)
	{
		return true;
	}
	else
	{
		alert("You can't enter No of installment greater than Total installment");
		document.getElementById("installments").value='';
		return false;
	}
}

function validRepayDateInDeal()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	//var agreementDate=document.getElementById("agreementDate").value;
	//var sanctionedDate=document.getElementById("sanctionedValidtill").value;
	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("repayEffectiveDate").value;
//	var dt1=getDateObject(agreementDate,formatD.substring(2, 3));
//    var dt2=getDateObject(sanctionedDate,formatD.substring(2, 3));
//    var dt3=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    //alert("repayEffectiveDate: "+repayEffectiveDate+"Converted Bussiness Date: "+dt3+"formatD.substring(2, 3): "+formatD.substring(2, 3));
  //  alert("validAggrDate: agreementDate(dt1) "+dt1+"sanctionedDate(dt2): "+dt2+"bDate(dt3): "+dt3);
 
    
    if(dt1<dt3)
	{
		msg="Please enter Repay Effective date greater than bussiness Date ";
		document.getElementById("repayEffectiveDate").value='';
		//return false;
	}

//    if(dt1>dt3)
//	{
//		msg="Please enter Repay Effective date greater than Agrement Date ";
//		document.getElementById("repayEffectiveDate").value='';
//		//return false;
//	}
//
//	if(dt2<dt3)
//	{
//    	msg="Please enter Repay Effective less than sanctioned Date ";
//		document.getElementById("repayEffectiveDate").value='';
//		//return false;
//	}
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

//Ravi, Fund Flow Analysis java script functions start
function newfundFlow()
{ 
	//alert("ok");
	document.location.href="fundFlowAnalysisNew.do";
}

function saveBankAccountAnalysis()
{
	alert("ok");
	var contextPath =document.getElementById('contextPath').value ;
	 if(validateBankAccountAnalysisDynaValidatorForm(document.getElementById("bankAccountAnalysisForm")))
     { 
	     document.getElementById("bankAccountAnalysisForm").action =contextPath+"/bankAccountAnalysisAction.do?method=saveBankAccountAnalysisDetails";
	     document.getElementById("bankAccountAnalysisForm").submit();
		 return true;
	  }
	return false;
}
function saveObligation()
{
	var contextPath = document.getElementById('contextPath').value;

	if(validateObligationDynaValidatorForm(document.getElementById("obligationForm")))
	{
		document.getElementById("obligationForm").action = contextPath+"/saveobligationDetail.do?method=saveObligationDetails";
		document.getElementById("obligationForm").submit();
		return true;
	}
	return false;
}
function saveSalesAnalysis()
{

	var contextPath = document.getElementById('contextPath').value;
	if(validateSalesAnalysisDynaValidatorForm(document.getElementById("salesAnalysisForm")))
	{
		document.getElementById("salesAnalysisForm").action = contextPath+"/salesAnalysisAction.do?method=saveSalesAnalysisDetails";
		document.getElementById("salesAnalysisForm").submit();
		return true;
	}
	return false;
}

function deleteBankAcAnalysis()
{
	    if(check())
	    {
			document.getElementById("bankAccountAnalysisForm").action="deleteBankAccountAnalysis.do?method=deleteBankAccountAnalysis";
		 	document.getElementById("bankAccountAnalysisForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    }
}
function deleteSalesAnalysis()
{
	
	    if(check())
	    {
			document.getElementById("salesAnalysisForm").action="deleteSalesAnalysis.do?method=deleteSalesAnalysisDetails";
		 	document.getElementById("salesAnalysisForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    }
}

function deleteObligation()
{
	
	    if(check())
	    {
			document.getElementById("obligationForm").action="deleteObligation.do?method=deleteObligationDetails";
		 	document.getElementById("obligationForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    }
}

function getBankAcAnalysis(id)
{
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("bankAccountAnalysisForm").action=contextPath+"/getBankAcAnalysisDetails.do?method=getBankAcAnalysis&bankAcAnalysisId="+id;
 	document.getElementById("bankAccountAnalysisForm").submit();
}

function getSalesAnalysis(id)
{
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("salesAnalysisForm").action=contextPath+"/getSalesAnalysisDetails.do?method=getSalesAnalysis&salesId="+id;
 	document.getElementById("salesAnalysisForm").submit();
}

function getObligation(id)
{
	
	var contextPath = document.getElementById('contextPath').value;
	document.getElementById("obligationForm").action=contextPath+"/getObligationDetails.do?method=getObligationDetails&obligationId="+id;
 	document.getElementById("obligationForm").submit();
}


function updateBankAccountAnalysis(id)
{
	
	var contextPath = document.getElementById('contextPath').value;
	 if(validateBankAccountAnalysisDynaValidatorForm(document.getElementById("bankAccountAnalysisForm")))
     { 
	document.getElementById("bankAccountAnalysisForm").action=contextPath+"/updateBankAcAnalysisDetails.do?method=updateBankAcAnalysis&bankAcAnalysisId="+id;
 	document.getElementById("bankAccountAnalysisForm").submit();
 	return true;
 	}
 	return false;
}

function updateSalesAnalysis(id)
{
	
	var contextPath = document.getElementById('contextPath').value;
	
	if(validateSalesAnalysisDynaValidatorForm(document.getElementById("salesAnalysisForm")))
	{
	document.getElementById("salesAnalysisForm").action=contextPath+"/updateSalesAnalysisDetail.do?method=updateSalesAnalysis&salesId="+id;
 	document.getElementById("salesAnalysisForm").submit();
 	return true;
	}
	return false;
}

function updateObligation(id)
{
	var contextPath = document.getElementById('contextPath').value;
	if(validateObligationDynaValidatorForm(document.getElementById("obligationForm")))
	{
	document.getElementById("obligationForm").action=contextPath+"/saveobligationDetail.do?method=updateObligationDetail&obligationId="+id;
 	document.getElementById("obligationForm").submit();
	return true;
	}
	return false;
}

function searchFundFlowDetail()
{
	//alert("ok");
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var username=document.getElementById("reportingToUserId").value;
		
	if(username!='' ||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("fundFlowAnalysisSearchForm").action=sourcepath+"/fundFlowAnalysisSearchAction.do?method=fundFlowAnalysisSearch&userId="+username;
		document.getElementById("fundFlowAnalysisSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		return false;
	}
}

	function editFundFlowAnalysis(id,no,custName)
	{
	//	document.location.href="fundFlowAnalysisNew.do&dealId="+id;
		custName = escape(custName);
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("fundFlowAnalysisSearchForm").action=sourcepath+"/editFlowAnalysis.do?dealId="+id+"&dealNo="+no+"&customerName="+custName;
		//alert(document.getElementById("fundFlowAnalysisSearchForm").action);
		document.getElementById("fundFlowAnalysisSearchForm").submit();
	}

	function forwardFundFlowDetails()
	{
		
		var dealId = document.getElementById('dealId').value ;
		
		if(dealId!='')
		{
			//alert("ok");
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("bankAccountAnalysisForm").action = sourcepath+"/forwardFundFlow.do";
		    document.getElementById("bankAccountAnalysisForm").submit();
		}
		else
		{
			alert("You can't move without saving before Bank Account Analysis Details.");
		}
		
	}
	
	function fundFlowAuthor()
	{
		var basePath=document.getElementById("contextPath").value;
		if((document.getElementById("comments").value=="") || (document.getElementById("decision").value=="" ))
		   {
			alert("Please enter the required field ");
			return false;
		   }
		else
		{
			
			var decision = document.getElementById("decision").value;
				
			document.getElementById("fundFlowAuthorForm").action = basePath+"/fundFlowAuthorAction.do";
		     document.getElementById("fundFlowAuthorForm").submit();
		     return true;
		     
		   }
	}
	
	function bankAccountAnalysisClear()
	{
		document.getElementById("dealNo").value='';
		document.getElementById("customerName").value='';
		document.getElementById("lbxDealNo").value='';
		document.getElementById("lbxBankID").value='';
		document.getElementById("bankName").value='';
		document.getElementById("lbxBranchID").value='';
		document.getElementById("bankBranch").value='';
		document.getElementById("accountType").value='';
		document.getElementById("accountNo").value='';
		document.getElementById("totalEMI").value='';
		document.getElementById("credit").value='';
		document.getElementById("debit").value='';
		document.getElementById("highestBalance").value='';
		document.getElementById("lowestBalance").value='';
		document.getElementById("checkBounceAmount").value='';
		document.getElementById("checkBounceFrequency").value='';
		document.getElementById("overLimitAmount").value='';
		document.getElementById("overLimitFrequency").value='';
		document.getElementById("month").value='';
		document.getElementById("year").value='';
	
	}
	
	function salesAnalysisClear()
	{
		document.getElementById("year").value='';
		document.getElementById("month").value='';
		document.getElementById("netsales").value='';
	
	}
	
	function obligatoinClear()
	{
		document.getElementById("institutionName").value='';
		document.getElementById("emiAmount").value='';
		document.getElementById("tenure").value='';
		document.getElementById("maturityDate").value='';
		document.getElementById("purpose").value='';
		document.getElementById("outstandingLoanAmount").value='';
		
	}
	
//Ravi, Fund Flow Analysis java script functions end

//Ravi , Finacial Analysis function Start
	function searchFinancialDetail()
	{
		//alert("ok");
		DisButClass.prototype.DisButMethod();
		var dealNo=document.getElementById("dealNo").value;
		var lbxProductID=document.getElementById("product").value;
		var customername=document.getElementById("customername").value;
		var lbxscheme=document.getElementById("scheme").value;
		var username=document.getElementById("reportingToUserId").value;
		if(username!='' ||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
		{
			if(customername!='' && trim(customername).length<3)
			{
				alert("Please enter atleast three character");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("financialAnalysisSearchForm").action=sourcepath+"/financialAnalysisSearchAction.do?method=financialAnalysisSearch&reportingToUserId="+username;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("financialAnalysisSearchForm").submit();
		}
		else
		{
			alert("Please Enter atleast one field");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}

	function editFinancialAnalysis(id)
	{
		var sourcepath=document.getElementById("contextPath").value;
		
		//document.location.href="editFinancialAnalysis.do?method=openEditFinancialAnalysis&dealId="+id;
		document.location.href="editFinancialAnalysis.do?method=openEditFinancialAnalysis&dealId="+id;
	}
	
	function saveFinancialBalanceSheet()
	{
		//Sarvesh Added This Code Started
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		var reloadFlag=document.getElementById('reloadFlag').value; 
		if(reloadFlag!='Y')
			{
			
			alert('Please Reload First');
			return false;
			}
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var chkName = document.getElementsByName('chk');
		var chkLength = chkName.length;
		var chkValue = "";
		for(var i=0;i<chkLength;i++)
		{
		if(chkName[i].checked==true)
		{
		chkValue=chkValue+"Y"+"|";
		}
		else{
		chkValue=chkValue+"N"+"|";
		}
		}
		//Sarvesh Added This Code Ended
		
		var contextPath =document.getElementById('contextPath').value ;
		var year1 = document.getElementsByName("year1");
		var year2 = document.getElementsByName("year2");
		var year3 = document.getElementsByName("year3");
		var year4 = document.getElementsByName("year4");
		var year5 = document.getElementsByName("year5");
		var yr01 = document.getElementById("yr01");
		var yr02 = document.getElementById("yr02");
		var yr03 = document.getElementById("yr03");
		var yr04 = document.getElementById("yr04");
		var yr05 = document.getElementById("yr05");
		var subType=document.getElementsByName('subType') ;
		var allSumAsset1=0,allSumAsset2=0,allSumAsset3=0,allSumAsset4=0,allSumAsset5=0;
		var allSumLiability1=0,allSumLiability2=0,allSumLiability3=0,allSumLiability4=0,allSumLiability5=0;
		var msg="";
		var subTyAssetVal="";
		var subTyLiaVal="";
		//alert("length::"+year1.length);
		     for(var i=0;i< year1.length ; i++)
				{
					if(subType[i].value=='A'){
						subTyAssetVal=subType[i].value;
						//alert("subTyAssetVal::"+subTyAssetVal);
						allSumAsset1=(parseFloat(year1[i].value))+allSumAsset1;
						allSumAsset2=(parseFloat(year2[i].value))+allSumAsset2;
						allSumAsset3=(parseFloat(year3[i].value))+allSumAsset3;
						allSumAsset4=(parseFloat(year4[i].value))+allSumAsset4;
						allSumAsset5=(parseFloat(year5[i].value))+allSumAsset5;
					 }else{
						subTyLiaVal=subType[i].value;
						//alert("subTyLiaVal::"+subTyLiaVal);
						allSumLiability1=(parseFloat(year1[i].value))+allSumLiability1;
						allSumLiability2=(parseFloat(year2[i].value))+allSumLiability2;
						allSumLiability3=(parseFloat(year3[i].value))+allSumLiability3;
						allSumLiability4=(parseFloat(year4[i].value))+allSumLiability4;
						allSumLiability5=(parseFloat(year5[i].value))+allSumLiability5;
					}
				}
				 //alert("allSumAsset1:"+allSumAsset1+"::allSumAsset2::"+allSumAsset2+"::allSumAsset3::"+allSumAsset3+"::allSumAsset4::"+allSumAsset4+"::allSumAsset5::"+allSumAsset5);
				// alert("allSumLiability1:"+allSumLiability1+"::allSumLiability2::"+allSumLiability2+"::allSumLiability3::"+allSumLiability3+"::allSumLiability4::"+allSumLiability4+"::allSumLiability5::"+allSumLiability5);
				if((allSumAsset1!=allSumLiability1 ||allSumAsset2!=allSumLiability2 || 
					allSumAsset3!=allSumLiability3 || allSumAsset4!=allSumLiability4 || 
					allSumAsset5!=allSumLiability5) && (subTyLiaVal=='L' && subTyAssetVal=='A'))
				{
						if(allSumAsset1!=allSumLiability1)
						 {
						msg=yr01.value;
						 }
						if(allSumAsset2!=allSumLiability2)	
						 {
						msg=msg+"-"+yr02.value;
						 }
						 if(allSumAsset3!=allSumLiability3)
						 {
						msg=msg+"-"+yr03.value;
						 }
						if(allSumAsset4!=allSumLiability4)
						 {
						msg=msg+"-"+yr04.value;
						 }
						if(allSumAsset5!=allSumLiability5)
						 {
						msg=msg+"-"+yr05.value;
			             }
						if(msg.charAt(0)=="-")
						 {
						msg=msg.substring(1, msg.length);
						 }						
			}
				if(msg.length==0){
					document.getElementById("financialBalacnceSheetForm").action =contextPath+"/saveFinancialBalanceSheet.do?method=saveFinancialBalanceSheetDetails&source=S&chkValue="+chkValue;
				     document.getElementById("processingImage").style.display = '';
				     document.getElementById("financialBalacnceSheetForm").submit();
				}else{
				var r=confirm("sum of Sub Type Asset & Liability of year "+msg+" are not equal! Do you want to save... ");
				if (r==true)
				  {	
					 document.getElementById("financialBalacnceSheetForm").action =contextPath+"/saveFinancialBalanceSheet.do?method=saveFinancialBalanceSheetDetails&source=S&chkValue="+chkValue;
				     document.getElementById("processingImage").style.display = '';
				     document.getElementById("financialBalacnceSheetForm").submit();
				  }
				}
	}
	//method added by neeraj
	function uploadBalanceSheet()
	{
		//alert("uploadBalanceSheet");
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(validateDocUpload())
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("financialBalacnceSheetForm").action =contextPath+"/saveFinancialBalanceSheet.do?method=saveFinancialBalanceSheetDetails&source=U";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("financialBalacnceSheetForm").submit();
		}
	}
	//method added by neeraj
	function validateDocUpload()
	{
		if(document.getElementById('docFile').value=="")
		{
			alert("Choose file to be uploaded.");
			document.getElementById("docFile").focus(); 
		    return false; 
		}
		var fup = document.getElementById('docFile');
		var file_Name = fup.value;
		var ext = file_Name.substring(file_Name.lastIndexOf('.') + 1);
		if(ext == "xls" || ext == "XLS" || ext == "xlsx" || ext == "XLSX")
			return true;
		else
		{
			alert("Upload excel file only.");
			fup.value='';
			fup.focus();
			return false;
		}
	}
	//method added by neeraj
	function generateBalacnceSheetErrorLog()
	{
		//alert("generateBalacnceSheetErrorLog");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("financialBalacnceSheetForm").action =contextPath+"/saveFinancialBalanceSheet.do?method=generateErrorLog";
		document.getElementById("financialBalacnceSheetForm").submit();
	}
	function saveProfitAndLoss()
	{
		//Sarvesh Added This Code Started
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		var reloadFlag=document.getElementById('reloadFlag').value; 
		if(reloadFlag!='Y')
			{
			
			alert('Please Reload First');
			return false;
			}
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var chkName = document.getElementsByName('chk');
		var chkLength = chkName.length;
		var chkValue = "";
		for(var i=0;i<chkLength;i++)
		{
		if(chkName[i].checked==true)
		{
		chkValue=chkValue+"Y"+"|";
		}
		else{
		chkValue=chkValue+"N"+"|";
		}
		}
		//Sarvesh Added This Code Ended
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("profitAndLossForm").action =contextPath+"/saveFinancialProfitAndLoss.do?method=saveProfitAndLossDetails&source=S&chkValue="+chkValue;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("profitAndLossForm").submit();
	}
	//method added by neeraj
	function uploadProfitAndLoss()
	{
		//alert("uploadProfitAndLoss");
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		if(validateDocUpload())
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("profitAndLossForm").action =contextPath+"/saveFinancialProfitAndLoss.do?method=saveProfitAndLossDetails&source=U";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("profitAndLossForm").submit();
		}
	}
	//method addded by neeraj
	function generateProfitAndLossErrorLog()
	{
		//alert("generateProfitAndLossErrorLog");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("profitAndLossForm").action =contextPath+"/saveFinancialProfitAndLoss.do?method=generateErrorLog";
		document.getElementById("profitAndLossForm").submit();
	}
	
	function saveOthers()
	{
		//Sarvesh Added This Code Started
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		var reloadFlag=document.getElementById('reloadFlag').value; 
		if(reloadFlag!='Y')
			{
			
			alert('Please Reload First');
			return false;
			}
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var chkName = document.getElementsByName('chk');
		var chkLength = chkName.length;
		var chkValue = "";
		for(var i=0;i<chkLength;i++)
		{
		if(chkName[i].checked==true)
		{
		chkValue=chkValue+"Y"+"|";
		}
		else{
		chkValue=chkValue+"N"+"|";
		}
		}
		
		//Sarvesh Added This Code Ended
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("othersForm").action =contextPath+"/saveFinancialOthers.do?method=saveFinancialOthersDetails&source=S&chkValue="+chkValue;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("othersForm").submit();
	}
	//method added by neeraj
	function uploadOthers()
	{
		
		var lbxCustomerId=document.getElementById('lbxCustomerId').value; 
		if(lbxCustomerId==""){
			alert("Customer Name is Required");
			document.getElementById('lbxCustomerId').focus();
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		//alert("uploadOthers");
		if(validateDocUpload())
		{
			var contextPath =document.getElementById('contextPath').value ;
			document.getElementById("othersForm").action =contextPath+"/saveFinancialOthers.do?method=saveFinancialOthersDetails&source=U";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("othersForm").submit();
		}
	}
	//method addded by neeraj
	function generateOtherErrorLog()
	{
		//alert("generateOtherErrorLog");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("othersForm").action =contextPath+"/saveFinancialOthers.do?method=generateErrorLog";
		document.getElementById("othersForm").submit();
	}
	
	function saveFinancialRatioAnalysis()
	{
		//alert("ok");
		DisButClass.prototype.DisButMethod();
		var contextPath =document.getElementById('contextPath').value ;
		//if(validateFinancialAnalysisDynaValidatorForm(document.getElementById("othersForm")))
	   //  { 
		     document.getElementById("financialRatioAnalysisForm").action =contextPath+"/saveFinancialRatioAnalysis.do?method=saveFinancialRatioAnalysis";
		     //alert(document.getElementById("financialRatioAnalysisForm").action);
		     document.getElementById("processingImage").style.display = '';
		     document.getElementById("financialRatioAnalysisForm").submit();
		//	 return true;
		// }
		//return false;
	}
	
	function financialClear()
	{
		alert(" document.getElementsByName().value "+ document.getElementsByName("year1").value);
		var year1 = document.getElementsByName("year1").value;
		var year2 = document.getElementsByName("year2").value;
		var year3 = document.getElementsByName("year3").value;
		var year4= document.getElementsByName("year4").value;
		var year5 = document.getElementsByName("year5").value;
		
		alert("year1 "+year1.value);
		for(var i=0;i< year1.length ; i++)
		{
			alert("document.getElementById(year1[i]).value "+document.getElementById(year1[i]).value);
			document.getElementById(year1[i]).value='';
			document.getElementById(year2[i]).value='';
			document.getElementById(year3[i]).value='';
			document.getElementById(year4[i]).value='';
			document.getElementById(year5[i]).value='';
		}
//		document.getElementsByName("year1").value='';
//		document.getElementsByName("year2").value='';
//		document.getElementsByName("year3").value='';
//		document.getElementsByName("year4").value='';
//		document.getElementsByName("year5").value='';
//		
	}
	
	function financialAnalysisForward()
	{
		DisButClass.prototype.DisButMethod();
		var dealId = document.getElementById('dealId').value ;
	
		if(dealId!='')
		{
			//alert("ok");
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("financialBalacnceSheetForm").action = sourcepath+"/forwardFinancialAnalysis.do";
		    document.getElementById("processingImage").style.display = '';
			document.getElementById("financialBalacnceSheetForm").submit();
		}
		else
		{
			alert("You can't move without saving before Financial Details Analysis Details.");
			DisButClass.prototype.EnbButMethod();
		}
		
	}
	
	
//Ravi ,  Finacial Analysis function end
function calculateMaturityDateInDeal()
{
	
	var repaymentType = document.getElementById('repaymentType').value;
	//alert(repaymentType);
	if(repaymentType=='N')
	{
		document.getElementById('maturityDate1').value='';
	}
	else if(repaymentType=='I')
	{
		var x = parseInt(document.getElementById('requestedLoanTenure').value); //or whatever offset
		var formatD=document.getElementById("formatD").value;

		//alert(x);
		var currDate =   document.getElementById('repayEffectiveDate').value;
		//var currDate  = new Date(repayDate);
		  //alert(currDate);
		  var currDay   = currDate.substring(0,2);
		  //alert(currDay);
		  var currMonth = currDate.substring(3,5);
		 // alert(currMonth);
		  var currYear  = currDate.substring(6,10);
		 // alert(currYear);
		  var seprator = formatD.substring(2, 3);
		  if(seprator=='-')
			  seprator = '/';
		  else
			  seprator = '/';
		    UpdateDateStr = currMonth + seprator + currDay + seprator + currYear;
		   // alert(UpdateDateStr);
			var CurrentDate = new Date(UpdateDateStr);
			//alert(CurrentDate);
			CurrentDate.setMonth(CurrentDate.getMonth()+x);
		   // alert(CurrentDate.getMonth());
		   modMonth=CurrentDate.getMonth()+1;
		   if(modMonth<=9)
		   {
			   modMonth="0"+modMonth;
		   }
		   modDay=CurrentDate.getDate();
		   if(modDay<=9)
		   {
			   modDay="0"+modDay;
		   }
		  // alert(modMonth);
		  ModDateStr = modDay+ formatD.substring(2, 3) + modMonth + formatD.substring(2, 3) + CurrentDate.getFullYear();
		  //alert(ModDateStr);
		  
		  document.getElementById('maturityDate1').value=ModDateStr;
	}
	
}

function validateLeadDate()
{
	var msg='';
	var formatD=document.getElementById("formatD").value;
	//var agreementDate=document.getElementById("agreementDate").value;
	//var sanctionedDate=document.getElementById("sanctionedValidtill").value;
	var bDate=document.getElementById("bDate").value;
	var repayEffectiveDate=document.getElementById("leadDate").value;
//	var dt1=getDateObject(agreementDate,formatD.substring(2, 3));
//    var dt2=getDateObject(sanctionedDate,formatD.substring(2, 3));
//    var dt3=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    
    var dt1=getDateObject(repayEffectiveDate,formatD.substring(2, 3));
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    //alert("repayEffectiveDate: "+repayEffectiveDate+"Converted Bussiness Date: "+dt3+"formatD.substring(2, 3): "+formatD.substring(2, 3));
  //  alert("validAggrDate: agreementDate(dt1) "+dt1+"sanctionedDate(dt2): "+dt2+"bDate(dt3): "+dt3);
 
    
    if(dt1>dt3)
	{
		msg="Please Enter Lead date less than or equal to bussiness Date";
		document.getElementById("leadDate").value='';
		//return false;
	}

//    if(dt1>dt3)
//	{
//		msg="Please enter Repay Effective date greater than Agrement Date ";
//		document.getElementById("repayEffectiveDate").value='';
//		//return false;
//	}
//
//	if(dt2<dt3)
//	{
//    	msg="Please enter Repay Effective less than sanctioned Date ";
//		document.getElementById("repayEffectiveDate").value='';
//		//return false;
//	}
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

function onsearchCP(alert1)
{
	if (document.getElementById("dealNo").value=="")

	{
		alert(alert1);
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else{
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("searchForCPForm").action=basePath+"/searchCPBehindAction.do?method=getSearchData";
	    document.getElementById("searchForCPForm").submit();
	 
	    return true;
     	}
	}
//Sanjog changes start here
function onsearchForLead(leadNo)
{
	if (document.getElementById("leadNo").value=="")

	{
		alert("* Please select Lead No.");
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else{
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("searchForCPForm").action=basePath+"/searchCPBehindActionForLead.do?method=getSearchDataForLead&leadNo="+leadNo;
	    document.getElementById("searchForCPForm").submit();
	 
	    return true;
     	}
	}
//Sanjog Changes end here
function editDealDetail()
{
	var basePath=document.getElementById("contextPath").value;
	document.getElementById("searchForCPForm").action=basePath+"/commondeal.do";
	document.getElementById("searchForCPForm").submit();
	
}

function viewLeadDetails()
{
	
	//alert("Wait............");
	var basePath=document.getElementById("contextPath").value;
	var lbxLeadNo=document.getElementById("lbxLeadNo").value;
	if(lbxLeadNo=='')
	{
		alert("Please Select Lead No.");
		return false;
	}
	else
	{
		otherWindows = new Array();
		curWin = 0;
		//alert("ok: "+lbxLeadNo);
		var url=basePath+"/leadCapturingBehind.do?leadId="+lbxLeadNo+"&FromDealCap=V&LeadViewMode=LeadViewMode&forFirstTime=First&statusForViewLead=Y";
		otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//		alert(otherWindows[curWin++]);
		otherWindows[curWin++].moveTo(800,300);
		if (window.focus) {
			otherWindows[curWin++].focus();
			return false;
		}
		return true;
	}
}

function viewTradeCheck()
{
	var contextPath=document.getElementById("contextPath").value;
	//alert(contextPath);
	
	var url=contextPath+"/underWriting.do?method=tradeCheckSearch";
	//alert(url);
	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );

}
function viewContactVerification()
{
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/fieldVerComBody.do?method=viewCompletionTab&dealId="+dealId;
 	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
}
function viewCollateralVerification()
{
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	var url=contextPath+"/underWriting.do?method=viewCollforUR&dealId="+dealId;
	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
}
function viewScoring()
{
	//alert("Wait............");
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	var url=contextPath+"/scoringProcessAction.do?method=savedScoringData&lbxDealNo="+dealId+"&viewMode=VS";
	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
}
function viewDeviation()
{
	
	var contextPath=document.getElementById("contextPath").value;
	var dealId=document.getElementById("dealId").value;
	//alert("dealId............"+dealId);
	var url=contextPath+"/devationPageBehind.do?status=UWA";
	window.child=window.open(url,'name','height=350,width=900,top=200,left=250,scrollbars=yes' );
}

//ravi, deviation approval's functions

function searchDeviationApprovalDeals()
{
	//alert("ok");
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var username=document.getElementById("reportingToUserId").value;	
	if(username!=''||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("deviationApprovalSearchForm").action=sourcepath+"/deviationApprovalSearchBehindAction.do?userId="+username;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationApprovalSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function forwardDeviationApproval()
{
	//alert("Rohit");
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	var table1 = document.getElementById("gridTable1");	
	var rowCount1 = table1.rows.length;
	var chkValue='';
	var chkValueForAuto='';
	var status=false;
	var msg1='',msg2='',msg3='',msg4='';
	var checkFlag='N';
	var contextPath = document.getElementById('contextPath').value;
	if(rowCount > 1){		
		for(var i=1;i<rowCount;i++){
			var manualRemark="";
		//	alert("Rohit11"+rowCount);
			if(document.getElementById('manualRemark'+i)){
		 manualRemark = document.getElementById('manualRemark'+i).value;
	 	var manualPolicyId = document.getElementById('manualPolicyId'+i).value;
	 	var manRecStatus = document.getElementById('manRecStatus'+i).value;
	 //	alert(manualRemark);
	 //	alert(manRecStatus);
	 		if(manualRemark==""){
				msg2="Manual Deviation Remarks is required.";
				status= true;
			}
	 		if(manRecStatus=="" || manRecStatus=="I"){
				msg2="Manual Deviation Decision is required.";
				status= true;
			}
	 		 if(manRecStatus==""){
	 			manRecStatus="I";
				}
			   if(chkValue!=""){
				   chkValue=chkValue+','+manRecStatus;
			   }else{
				   chkValue=manRecStatus;
			   }
			}
			   if(manRecStatus!="I"){
				   checkFlag='Y';
			   }else{
				   checkFlag='N'; 
			   }
	   }	
      }
	
   if(rowCount1 > 1){

	for(var i=1;i<rowCount1;i++){	
		var remarks="";
		var recStatuses="";
		if(document.getElementById('recStatuses'+i)){
	    remarks = document.getElementById('remarks'+i).value;
		//alert(remarks);
	   var policyDecisionIds = document.getElementById('policyDecisionIds'+i).value;
	 	 recStatuses = document.getElementById('recStatuses'+i).value;
	 //	alert(recStatuses);
			if( remarks==""){
			msg3="Auto Deviation Remarks is required";
			status= true;
		}
			if( recStatuses=="" || recStatuses=="I"){
				msg3="Auto Deviation Decision is required";
				status= true;
			}
			   if(recStatuses==""){
				   recStatuses="I";
				}
			   if(chkValueForAuto!=""){
				   chkValueForAuto=chkValueForAuto+','+recStatuses;
			   }else{
				   chkValueForAuto=recStatuses;
			   }
		}
			   if(recStatuses!="I"){
				   checkFlag='Y';
			   }else{
				   checkFlag='N';
			   }
	}
   }
	if(checkFlag=='N')
	{
		msg4="No deviation decision can be kept pending for Approval/Rejection \n";
		status=true;
	}
		
	if(rowCount < 2 && rowCount1 < 2){
		msg1="There is nothing to save here\n";
	    status=true;
	}	
	if(status){
				alert(msg1+msg2+msg3+msg4);	
				if(msg2!=""){
					document.getElementById('manualRemark1').focus();
				}
				if(msg3!=""){
					document.getElementById('Remark1').focus();
				}
				return false;
				}else{

	var contextPath = document.getElementById('contextPath').value;
    document.getElementById("deviationApprovalForm").action = contextPath+"/saveDeviationApproval.do?method=forwardDeviationApproval&chkValue="+chkValue+"&chkValueForAuto="+chkValueForAuto;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationApprovalForm").submit();
		return true;
}
	}

function searchDocUploadDetail()
{
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var username=document.getElementById("reportingToUserId").value;
	var sourcepath=document.getElementById("contextPath").value;
	if(username!=''||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			document.getElementById("search").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("docUploadSearchForm").action=sourcepath+"/docUploadBehindSearchAction.do?userId="+username;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("docUploadSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function stageReverse()
{
	//alert("stageReverse");
	var decision=document.getElementById("decision").value;
	var reversingStage=document.getElementById("reversingStage");
	if(reversingStage){
		reversingStage = reversingStage.value;
	}
	var rpStageFlag=document.getElementById("rpStageFlag");
	if(rpStageFlag){
		rpStageFlag = rpStageFlag.value;
	}
	//alert("reversingStage: "+reversingStage);
// START BY PRASHANT	
	if(decision=='X')
	{
		document.getElementById("rejectDeal").style.display = '';
		document.getElementById("reasonDesc").value = '';
		document.getElementById("lbxReason").value = '';
		document.getElementById("reasonButton").removeAttribute("disabled","true");
		
	}
	else
	{
		document.getElementById("rejectDeal").style.display = 'none';
		document.getElementById("reasonDesc").value = '';
		document.getElementById("lbxReason").value = '';
		document.getElementById("reasonButton").setAttribute("disabled","true");
	}
	// END BY PRASHANT	
	if(decision=='P')
	{
		if(reversingStage=='DC')
		{
	
			document.getElementById("reversingStage").removeAttribute("disabled","true");
			document.getElementById("rpStageFlag").removeAttribute("disabled","true");
		}
		else
		{

			document.getElementById("reversingStage").removeAttribute("disabled","true");
			document.getElementById("rpStageFlag").setAttribute("disabled","true");
		}
		
	
	}
	else
	{
		document.getElementById("reversingStage").setAttribute("disabled","true");
		document.getElementById("rpStageFlag").setAttribute("disabled","true");
	
	}
		
}

function showCheckBox(){
	
	//alert("showCheckBox");
	var reversingStage=document.getElementById("reversingStage").value;
	
	if(reversingStage=='DC'||reversingStage=='QC')
	{
		document.getElementById("rpStageFlag").setAttribute("checked","true");
		document.getElementById("rpStageFlag").removeAttribute("disabled","true");
	}
	else
	{
		document.getElementById("rpStageFlag").removeAttribute("checked","true");
		document.getElementById("rpStageFlag").setAttribute("disabled","true");
	}
	
	
}
// Start by Prashant
function verificationDetailst()
{

	    var contextPath=document.getElementById("contextPath").value; 	
	 	otherWindows = new Array();
		curWin = 0;
		//alert("ok: "+lbxLeadNo);
		var url=contextPath+"/dealMovementBehind.do?method=trackVerificationAssigned";
		otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//		alert(otherWindows[curWin++]);
		otherWindows[curWin++].moveTo(800,300);
		if (window.focus) {
			otherWindows[curWin++].focus();
			return false;
		}
		return true;

}

function underwriterDeal()
{

	    var contextPath=document.getElementById("contextPath").value; 	
	 	otherWindows = new Array();
	     curWin = 0;	     
	//alert("ok: "+lbxLeadNo);
		var url=contextPath+"/dealMovementBehind.do?method=underwriterDealTracking";
		otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//		alert(otherWindows[curWin++]);
		otherWindows[curWin++].moveTo(800,300);
		if (window.focus) {
			otherWindows[curWin++].focus();
			return false;
		}
		return true;

}

//start by sachin
function deviationDeal()
{

	    var contextPath=document.getElementById("contextPath").value; 	
	 	otherWindows = new Array();
	     curWin = 0;	     
	//alert("ok: "+lbxLeadNo);
		var url=contextPath+"/dealMovementBehind.do?method=deviationDealTracking";
		otherWindows[curWin++] =window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
//		alert(otherWindows[curWin++]);
		otherWindows[curWin++].moveTo(800,300);
		if (window.focus) {
			otherWindows[curWin++].focus();
			return false;
		}
		return true;

}

//end by sachin
function closeVerificationDetails()
{
	self.close();
}
//End by Prashant
//Abhimanyu viability starts here
function saveViabilityDetail()
{
    var msg1='',msg2='',msg3='',msg4='',msg5='',msg6='',msg7='',msg8='',msg9='',msg10='',msg11='',msg12='',msg13='',msg14='',msg15='',msg16='',msg17='',msg18='';
    var status=false;
    if(document.getElementById("vonroad").value==''){
          status=true;
          msg1='* Number of Days Vehicle will be on Road is required \n';
    } 
    if(document.getElementById("vrunday").value==''){
          status=true;
          msg2='* Number of KM vehicle will run per Day is required \n';
    }
    if(document.getElementById("vrunmonth").value==''){
          status=true;
          msg3='* Number of KM vehicle will run per month is required \n';
    }
    if(document.getElementById("rpkm").value==''){
          status=true;
          msg4='* Rate Per KM is required \n';
    }
    if(document.getElementById("epday").value==''){
          status=true;
          msg5='* Earning Per Day is required \n ';
    }
    if(document.getElementById("epermonth").value==''){
          status=true;
          msg6='* Earning Per Month is required \n ';
    }
//    if(document.getElementById("exmonthly").value==''){
//          status=true;
//          msg7='* Expenses (Monthly) is required \n ';
//    }
    if(document.getElementById("drsalary").value==''){
          status=true;
          msg8='* Driver Salary is required \n ';
    }
    if(document.getElementById("fcost").value==''){
          status=true;
          msg9='* Fuel Cost is required \n ';
    }
    if(document.getElementById("tcost").value==''){
          status=true;
          msg10='* Tyre Cost is required \n ';
    }
    if(document.getElementById("permittax").value==''){
          status=true;
          msg11='* Permit & Tax is required \n ';
    }
    if(document.getElementById("insurance").value==''){
          status=true;
          msg12='* Insurance is required \n ';
    }
    if(document.getElementById("maintenance").value==''){
          status=true;
          msg13='* Maintenance is required \n ';
    }
    if(document.getElementById("others").value==''){
          status=true;
          msg14='* Others is required \n ';
    }
    if(document.getElementById("toexpenses").value==''){
          status=true;
          msg15='* Total Expenses is required \n ';
    }
    if(document.getElementById("nearning").value==''){
          status=true;
          msg16='* Net Earning is required \n ';
    }
    if(document.getElementById("epmonth").value==''){
        status=true;
        msg17='* EMI Per Month is required \n ';
  }
    if(document.getElementById("nsaving").value==''){
        status=true;
        msg18='* Net Saving is required \n ';
  }
if(status){
      alert(msg1+msg2+msg3+msg4+msg5+msg6+msg8+msg9+msg10+msg11+msg12+msg13+msg14+msg15+msg16+msg17+msg18);
      return false;}	
else
	{	
	var contextPath=document.getElementById("contextPath").value; 
	var dealid=document.getElementById("dealid").value; 	
	document.getElementById("viabilityForm").action=contextPath+"/viabilitySaveAction.do?method=saveViability&dealid="+dealid;
	document.getElementById("viabilityForm").submit();
	}
}

function calculateVib1()
{
	var vonroad=document.getElementById("vonroad").value;	
	var vrunday=document.getElementById("vrunday").value;	
	if(vonroad!='' && vrunday!='')
	{
	document.getElementById("vrunmonth").value=removeComma(vonroad)*removeComma(vrunday);
	}
	else{
		document.getElementById("vrunmonth").value='';
		}
}
function calculateVib5()
{
	var vrunday=document.getElementById("vrunday").value;	
	var rpkm=document.getElementById("rpkm").value;
	if(vrunday!='' && rpkm!='')
	{
	document.getElementById("epday").value=removeComma(vrunday)*removeComma(rpkm);
	}
	else{
		document.getElementById("epday").value='';
		}
}
function calculateVib6()
{
	var epday=document.getElementById("epday").value;	
	var vonroad=document.getElementById("vonroad").value;
	if(epday!='' && vonroad!='')
	{
	document.getElementById("epermonth").value=removeComma(epday)*removeComma(vonroad);
	}
	else{
		document.getElementById("epermonth").value='';
		}
}
function calculateVib15()
{
	var drsalary=document.getElementById("drsalary").value;	
	var fcost=document.getElementById("fcost").value;
	var tcost=document.getElementById("tcost").value;	
	var permittax=document.getElementById("permittax").value;
	var insurance=document.getElementById("insurance").value;	
	var maintenance=document.getElementById("maintenance").value;
	var others=document.getElementById("others").value;
	if(drsalary!='' && fcost!=''&& tcost!=''&& permittax!=''&& insurance!=''&& maintenance!=''&& others!='')
	{
	document.getElementById("toexpenses").value=(parseFloat(removeComma(drsalary))+parseFloat(removeComma(fcost))+parseFloat(removeComma(tcost))+parseFloat(removeComma(permittax))+parseFloat(removeComma(insurance))+parseFloat(removeComma(maintenance))+parseFloat(removeComma(others)));
	}
	else{
		document.getElementById("toexpenses").value='';
		}
}
function calculateVib16()
{
	var epermonth=document.getElementById("epermonth").value;	
	var toexpenses=document.getElementById("toexpenses").value;
	if(epermonth!='' && toexpenses!='')
	{
	document.getElementById("nearning").value=removeComma(epermonth)-removeComma(toexpenses);
	}
	else{
		document.getElementById("nearning").value='';
		}
}
function calculateVib18()
{
	var nearning=document.getElementById("nearning").value;	
	var epmonth=document.getElementById("epmonth").value;
	if(nearning!='' && epmonth!='')
	{
	document.getElementById("nsaving").value=removeComma(nearning)-removeComma(epmonth);
	}
	else{
		document.getElementById("nsaving").value='';
		}
}


function twoDecimalNumber(val, san){
	
	

	if(val != ''){

		var expAllVal = val.split('.');
		var firstVal = expAllVal[0];
		var strToArray = new Array();
		var secVal = '';
		var dynaVal = san;
		
		if(expAllVal.length == 2){
			var secVal = expAllVal[1];
			
		}
		var lengthOffirstVal = firstVal.length;
		var roundVal = lengthOffirstVal/3;
		var remind = lengthOffirstVal % 3;
		
		if(remind == 0){
			var findRoundOrNot = roundVal;
		}else{
			var findRoundOrNot = Math.ceil(roundVal);
		}
		if(findRoundOrNot > 0){
			var forLoopFindRoundOrNot = findRoundOrNot-1; 
			var jIndex = lengthOffirstVal;	              
			for(i=forLoopFindRoundOrNot; i >= 0 ; i--){
				if(jIndex > 2){
					var jIndex = jIndex-3;	
					var lastId = 3;
				}
				else{
					var lastId = jIndex;
					var jIndex = 0;							
				}				
				
				strToArray[i] = firstVal.substr(jIndex, lastId);
				
			}
			if(firstVal.indexOf(',') < 0){
				var finalValueForDisp = strToArray.join(',');
			}
			if(secVal != ''){
				finalValueForDisp = finalValueForDisp+'.'+secVal;
			}else{
				finalValueForDisp = finalValueForDisp+'.00';
			}
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = finalValueForDisp;
		}
		if(val.indexOf(',') > 0){
			  	var origString = val;
					var inChar = ',';
					var newString = origString.split(inChar);
					newString = newString.join('');
				
			document.getElementById(dynaVal).value = '';
			document.getElementById(dynaVal).value = newString;
			//alert(newString);
			}
	} 
}


function isNumberKeyDec(evt) 
{
var charCode = (evt.which) ? evt.which : event.keyCode;
if (charCode > 47 || charCode==8 && charCode < 58 || charCode==46)
{ 
	return true;	
}
else
{
	alert("Only Numeric and decimal allowed here");
	return false;	
}
	
}


function check()
{
 //   alert("check function");
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

function checkSupplier(confirmStatus)
{
 //   alert("check function");
	if(confirmStatus=='N')
	{
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
	}
	else
	{
		flag=1;
	}
	return flag;
}
function checkBuyer(confirmStatus)
{
 //   alert("check function");
	if(confirmStatus=='N')
	{
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
	}
	else
	{
		flag=1;
	}
	return flag;
}

function getEMIAmount()
{
	//alert("In getEMIAmount");
	var contextPath =document.getElementById('contextPath').value ;
		var address = contextPath+"/ajaxActionForCP.do?method=getEMIAmountPerMonth";
		var data = "";
		sendRequest(address,data);
		return true;
}
function sendRequest(address, data) 
{
	var request = getRequestObject();
	request.onreadystatechange = function () {
		resultMethod(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send(data);
}
function resultMethod(request)
{    
	if ((request.readyState == 4) && (request.status == 200)) 
	{
		var str = request.responseText;
		resultMethod
		var s1 = str.split("$:");
		if(s1.length>0)
	    {
			document.getElementById('epmonth').value = trim(s1[0]);
	    }
	}
}

//Manisha Manual Deviation Maker Search functions

function onSearchOfDeviationMaker()
{
	
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var lbxProductID=document.getElementById("product").value;
	var customername=document.getElementById("customername").value;
	var lbxscheme=document.getElementById("scheme").value;
	var username=document.getElementById("reportingToUserId").value;	
	if(username!=''||dealNo!='' || customername!='' || lbxProductID!='' || lbxscheme!='')
	{
		if(customername!='' && trim(customername).length<3)
		{
			alert("Please enter atleast three character");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("deviationMakerSearchForm").action=sourcepath+"/manualDeviationMakerSearchBehindAction.do?userId="+username;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationMakerSearchForm").submit();
	}
	else
	{
		alert("Please Enter atleast one field");
		document.getElementById("search").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function searchDeviationMakerDeals(val)
{
	
		var contextPath =document.getElementById('contextPath').value ;
		location.href=contextPath+"/manualDeviationMakerAction.do?method=manualDeviationM&dealId="+val;
		
	//	location.href=contextPath+"/dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA";

}


function saveManualDeviationMaker()
{
	//DisButClass.prototype.DisButMethod();
	
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	var chkValue='';
	var status=false;
	var msg1='',msg2='',msg3='';	
	var manualDevId = document.getElementsByName('manualDevId');
	var contextPath = document.getElementById('contextPath').value;
	if(rowCount<2){
		
		msg1="There is nothing to save \n";
		status=true;
	}else{
		
		for(var i=1;i<rowCount;i++){
			if(document.getElementById('chk'+i).checked==true){
				chkValue=chkValue+i+",";
				var remarks= document.getElementById('remarks'+i).value;				
				var manualId = document.getElementsByName('manualId'+i).value;
				var approvalLevel = document.getElementsByName('approvalLevel'+i).value;
			
				if(remarks==''){
					msg3="Remark is required for selected checkbox \n";
					status=true;
				}
			}
		}
			document.getElementById('chkValue').value=chkValue;
			
	}
	if(chkValue==''){
		msg2="Please Select Checkbox to save \n";
		status=true;
	}
		if(status){
		alert(msg1+msg2+msg3);	
		document.getElementById('remarks1').focus();
		return false;
		}else{
						
		document.getElementById("deviationMakerForm").action = contextPath+"/manualDeviationMakerAction.do?method=saveManualDeviationMaker";
        document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationMakerForm").submit();
		return true;
		 }
}
		
	
function forwardManualDeviationMaker(fwdMsg)
{
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	var chkValue='';
	var status=false;
	var msg1='',msg2='',msg3='';	;	
	
	var contextPath = document.getElementById('contextPath').value;
	if(rowCount<2){
		//alert("Please Select Checkbox to save");
		msg1="There is nothing to save \n";
		status=true;
	}else{
		
		for(var i=1;i<rowCount;i++){
			if(document.getElementById('chk'+i).checked==true){
				chkValue=chkValue+i+",";
				var remarks=	document.getElementById('remarks'+i).value;
				
				var manualId = document.getElementsByName('manualId'+i).value;
				
				if(remarks==''){
					msg3="Remark is required for selected checkbox \n";					
					status=true;
				}
			}
		}
			document.getElementById('chkValue').value=chkValue;
			
	}
	if(chkValue==''){
		msg2="Please Select Checkbox to forward \n";
		status=true;
	}
	
	
		if(status){
					alert(msg1+msg2+msg3);	
					document.getElementById('remarks1').focus();
					return false;
					}else{
						document.getElementById("deviationMakerForm").action = contextPath+"/manualDeviationMakerAction.do?method=forwardManualDeviationMaker";
				        document.getElementById("processingImage").style.display = '';
						document.getElementById("deviationMakerForm").submit();
						return true;
					 }
}	



function onSaveDeviationApproval()
{
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	var table1 = document.getElementById("gridTable1");	
	var rowCount1 = table1.rows.length;
	var chkValue='';
	var chkValueForAuto='';
	var checkFlag='N';
	var msg1='';
	var status=false;
	var stage="";
	if(document.getElementById("stage")){
	 stage=document.getElementById("stage").value;
	}
	var contextPath = document.getElementById('contextPath').value;
	if(rowCount > 1){		
		for(var i=1;i<rowCount;i++){
	 	/*var manRecStatus = document.getElementById('manRecStatus'+i);
	 		if( manRecStatus.checked==true){
	 			checkFlag='Y';
	 			chkValue=chkValue+',Y';
			}
	 		else
	 		{
	 			chkValue=chkValue+',N';
	 		}*/
			var manRecStatus="";
			//	alert("Rohit11"+rowCount);
				if(document.getElementById('manRecStatus'+i)){
			 manRecStatus = document.getElementById('manRecStatus'+i).value;
			 
			if(manRecStatus==''){
				manRecStatus="I";
			}
			if(chkValue!=""){
			chkValue=chkValue+','+manRecStatus;
			}else{
				chkValue=manRecStatus;
			}
			 if(manRecStatus!='I'){
				   checkFlag='Y';
			   }else{
				   checkFlag='N';
			   }
				}
	   }	
      }
   if(rowCount1 > 1){

	for(var i=1;i<rowCount1;i++)
	{	
		if(document.getElementById('remarks'+i)){
	   var remarks = document.getElementById('remarks'+i).value;
	   var policyDecisionIds = document.getElementById('policyDecisionIds'+i).value;
		}
	 	/*var recStatuses = document.getElementById('recStatuses'+i);
	 	if( recStatuses.checked==true){
	 		checkFlag='Y';
	 		chkValueForAuto=chkValueForAuto+',Y';
		}
 		else
 		{
 			chkValueForAuto=chkValueForAuto+',N';
 		}*/
		checkFlag='Y';
		if(document.getElementById('recStatuses'+i)){
	   var recStatuses = document.getElementById('recStatuses'+i).value;
	   if(recStatuses!="I"){
		   checkFlag='Y';
	   }else{
		   checkFlag='N';
	   }
	}
	   if(recStatuses==""){
		   recStatuses="I";
		}
	   if(chkValueForAuto!=""){
	   chkValueForAuto=chkValueForAuto+','+recStatuses;
	   }else{
		   chkValueForAuto=recStatuses;
	   }
	   
	   }
	}
	if(checkFlag=='N')
	{
		msg1="No deviation decision can be kept pending for Approval/Rejection \n";
		status=true;
	}
	if(status)
	{
		alert(msg1);
		return false;
	}
	else
	{
	var ch = document.getElementsByName('recStatuses');
	var checkedhold="";
		document.getElementById("deviationApprovalForm").action = contextPath+"/saveDeviationApproval.do?method=onSaveDeviationApproval&chkValue="+chkValue+"&chkValueForAuto="+chkValueForAuto+"&stage="+stage;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationApprovalForm").submit();
		return true;
	}
}
function allCheck()
{
	// alert("ok");
	var c = document.getElementById("chk").checked;
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

function openFundFlow(id) {
	
	//alert("DealId : " + id );
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/editFlowAnalysis.do?dealId="+id;
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}

function openFinancial(val,custType) {
	
	//alert(custType);
	var contextPath =document.getElementById('contextPath').value ;
	if(custType=='I')
	{
		var url=contextPath+"/editIndividualFinancialAnalysis.do?method=openEditIndividualFinancialAnalysis&dealId="+val;
	}
	else
	{
		var url=contextPath+"/editFinancialAnalysis.do?method=openEditFinancialAnalysis&dealId="+val;
		
	}
	//alert(url);
	newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
	if (window.focus) {newwindow.focus()}
	return false;
}
//start by sachin

function openSpecialCondition(val) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/specialConditionDispatch.do?method=openSpecialCondition&dealId="+val+" ";
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}

function openCustEntry(val) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/custEntryAction.do?dealId="+val+"&status=CE&show=Y";
	//alert(url);
newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
if (window.focus) {newwindow.focus()}
return false;
}

function openDisbursalSchedule(val) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/dealDisbursalScheduleBehind.do?method=openDisbursalSchedule&dealId="+val+" ";
	//alert(url);
		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		return false;

}
function saveSpecialCondition()
{
	 //alert("saveSpecialCondition:");
	   var specialCondition=document.getElementById("specialCondition").value;
	   DisButClass.prototype.DisButMethod();
	   if(specialCondition=='')
	   {
		   alert("Special Condition is Required");
		   DisButClass.prototype.EnbButMethod();
		   return false;
	   }
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("specialConditionForm").action=sourcepath+"/specialConditionSave.do?method=saveSpecialCondition";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("specialConditionForm").submit();
		return true;
			 
}

function updateSpecialCondition(val)
{
	var sourcepath=document.getElementById("contextPath").value;
    location.href=sourcepath+"/specialConditionDispatch.do?method=fetchSpecialCondition&specialConditionId="+val;
    return true;
}

function deleteSpecialCondition()
{
	  DisButClass.prototype.DisButMethod();
	  var contextPath = document.getElementById("contextPath").value;
	if(check())
	 { 
		if(confirm("Are You Sure to Delete Special Condition(s)?"))
		{
			document.getElementById("specialConditionForm").action=contextPath+"/specialConditionDispatch.do?method=deleteSpecialCondition";
			document.getElementById("processingImage").style.display = '';
	  		document.getElementById("specialConditionForm").submit();
	  		return true;
		}
		else
		{
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		
	  }
	   else
	   {
	   	alert("Please Select atleast one!!!");
	   	DisButClass.prototype.EnbButMethod();
	    return false;
	   }
}

/*function removeRowDisbSche() {   
	 var table = document.getElementById("gridTable");
	    var rowCount = table.rows.length;
	    //alert(rowCount);
	    if(rowCount>2)
	    {
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
	    else
	    {
	    	alert("One row is mandatory");
	    }
}*/

//By Prashant
function removeRowDisbSche() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTable");
    var rowCount = table.rows.length;
  	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	document.getElementById("psize").value=psize;
   // alert("rowCount:--"+rowCount);
	if(rowCount==2){
		
		alert("One row is mandatory");
		
	}
	else
	{
     for(var j=1;j<rowCount;j++){
    
       var row1 = table.rows[j];
      //  var chkbox1 = row1.cells[0].childNodes[0];
       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     
        if(chkbox1!=undefined ||chkbox1!=null) {
    	 if(null != chkbox1 && true == chkbox1.checked) {
		 count=count+1;
    	 }
		 }
    }
	
if(count==0){
alert("Please Select at least one row to delete");
}else{
    for(var i=1; i<rowCount; i++) {
      var row = table.rows[i];
  //  var chkbox = row.cells[0].childNodes[0];
      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
        if(chkbox!=undefined ||chkbox!=null){
        if(null != chkbox && true == chkbox.checked) {
     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
       if(document.getElementById("chk"+i).checked==true){*/
            table.deleteRow(i);
            rowCount--;
            i--;
        }
        }
   //   }
     }
     }
	}
}catch(e) {
        alert(e);
    }
   
}
//BY prashant

function checkDateAtDisbSch(txtDate)
{
    // define date string to test
    var txtDateValue = document.getElementById(txtDate).value;		    
    var formatD=document.getElementById("formatD").value;// must be take this format from jsp
    if (isDate(txtDateValue,formatD) || txtDateValue == null || txtDateValue == '') 
    {
       return true;
    }
    else 
    {
        alert('Invalid date format!');
        document.getElementById(txtDate).value='';
        return false;
    }
}

function addROWAtDeal(){
	


	// alert("addROW");

   				var table = document.getElementById("gridTable");

   				var rowCount = table.rows.length;
   				var row = table.insertRow(rowCount);
   				
   				var psize= document.getElementById("psize").value;

   				if(psize==""){
   					psize=rowCount;
   				}
   				
   				row.setAttribute('className','white1' );
   			    row.setAttribute('class','white1' );
   				var cell1 = row.insertCell(0);
   				var element1 = document.createElement("input");
   				element1.type = "checkbox";
   				element1.name = "chk";
   				element1.id = "chk"+psize;
   				cell1.appendChild(element1);

   				var cell2 = row.insertCell(1);
   				var element2 = document.createElement("input");
   				element2.type = "text";
   				element2.name = "noOfDisbursal";
   				element2.id = "noOfDisbursal"+psize;
   				element2.setAttribute('class','text');
   				element2.setAttribute('className','text' );
   				//element2.setAttribute('value',psize);
   				//element2.setAttribute('readOnly','true' );
   				cell2.appendChild(element2);
   				
   				var cell3 = row.insertCell(2);
   				var element3 = document.createElement("input");
   				element3.type = "text";
   				element3.name = "dateOfDisbursal";
   				element3.id = "dateOfDisbursal"+psize;
   				element3.setAttribute('class','text');
   				element3.setAttribute('className','text' );
   				element3.onchange= function(){
   					var e = psize -1;
   					checkDateAtDisbSch("dateOfDisbursal"+e);
   				};
   				cell3.appendChild(element3);
   				
   				var cell4 = row.insertCell(3);
   				var element4 = document.createElement("input");
   				element4.type = "text";
   				element4.name = "descOfDisbursal";
   				element4.id = "descOfDisbursal"+psize;
   				element4.setAttribute('class','text');
   				element4.setAttribute('className','text' );
   				cell4.appendChild(element4);
   				
   				
   				var cell5 = row.insertCell(4);
   				var element5 = document.createElement("input");
   				element5.type = "text";
   				element5.name = "amountOfDisbursal";
   				element5.id = "amountOfDisbursal"+psize;
   				element5.setAttribute('class','text');
   				element5.setAttribute('className','text' );
   			
   				element5.onblur= function(){
   					formatNumber(document.getElementById("amountOfDisbursal"+psize).value,"amountOfDisbursal"+psize);
   				};
   				element5.onfocus=function keyUpNumber(e){
   					   var val=this.value;
   					         if(val.indexOf(',') > 0){
   							var dynaVal = this.id;
   							var Max=18;
   							document.getElementById(dynaVal).maxLength = Max+3;
   							var origString = this.value ;
   							var inChar = ',';
   							var outChar = '.';
   							var newString = origString.split(outChar);
   							var newString = origString.split(inChar);
   							newString = newString.join('');
   							document.getElementById(dynaVal).value = '';
   							document.getElementById(dynaVal).value = newString;
   							}
   							};
   						element5.onkeypress = function numbersonly(e){
   						var dynaVal = this.id;
   						document.getElementById(dynaVal).maxLength = 21;
   						  var goods="0123456789.";
   							    var key, keychar;
   							    if (window.event)
   							        key=window.event.keyCode;
   							    else if (e)
   							        key=e.which;
   							    else
   							        return true;
   							    keychar = String.fromCharCode(key);
   							    keychar = keychar.toLowerCase();
   							    goods = goods.toLowerCase();
   							    if (goods.indexOf(keychar) != -1)
   							    {
   							        goods="0123456789.";
   							        return true;
   							    }
   							    if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
   							    {
   							        goods="0123456789.";
   							        return true;
   							    }
   							    return false;
   						};
   				element5.onkeyup= function(){
   					checkNumber(document.getElementById("amountOfDisbursal"+psize).value, this.event, 18,"amountOfDisbursal"+psize);
   				};
   				
   				
   				cell5.appendChild(element5);
   				psize++;
   				document.getElementById("psize").value=psize;
   
}

function saveNoDisb()

{
 
DisButClass.prototype.DisButMethod();
 var contextPath = document.getElementById("contextPath").value;
 var formatD=document.getElementById("formatD").value;

 var businessdate = document.getElementById("businessdate").value;	

 var dt1=getDateObject(businessdate,formatD.substring(2, 3));	 
    
 var loanAmount=removeComma(document.getElementById("dealLoanAmount").value);

 
 var table = document.getElementById("gridTable");	
 var rowCount = table.rows.length;
 var disbDate='';	
 var disDesc='';
 var amount='';
 var noDisb='';
 var nextNoDisb='';
 var diffNoDisb=0;
    var noOfDisbursal=document.getElementsByName("noOfDisbursal");
	var dateOfDisbursal=document.getElementsByName("dateOfDisbursal");
	var descOfDisbursal=document.getElementsByName("descOfDisbursal");
	var amountOfDisbursal=document.getElementsByName("amountOfDisbursal");
 var sum=0;
 if(rowCount > 1){
	
	
	 if(noOfDisbursal.length>1)
	 {
		 for(var i=0;i<noOfDisbursal.length-1;i++) {
			 
			 
			 noDisb=noOfDisbursal[i].value;	
			 nextNoDisb=noOfDisbursal[i+1].value;
			 diffNoDisb=parseInt(nextNoDisb)-parseInt(noDisb);
			
			 if(parseInt(nextNoDisb)==0)
			 {
				 alert("zero is not allowed ");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			 if(diffNoDisb!=1)
			 {
				 alert("No of disbursal must be in sequence ");
				 DisButClass.prototype.EnbButMethod();
				 return false;
			 }
			
		 }
	 }
	 else
	 {
		 if(parseInt(noOfDisbursal[0].value)==0)
		 {
			 alert("zero is not allowed ");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	 }
	 
	 
	 for(var k=0;k<dateOfDisbursal.length;k++) {
		 
	 disbDate = dateOfDisbursal[k].value;	
	 disDesc =descOfDisbursal[k].value;
	 amount =amountOfDisbursal[k].value;
	 
	 if(amount==""){
		 amount=0;
	      }else{
	     amount=removeComma(amountOfDisbursal[k].value);
	     }
	
	    sum = (sum) + (amount);
	    
	    if ((disbDate=="")||(disDesc=="")||(amount==""))
		 {
			 alert("Please fill all the fields ");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }
	    var dt3=getDateObject(disbDate,formatD.substring(2, 3));
	    
	       
		 if (dt1>dt3)
		  {
		  alert("Disbursal Date should be greater than Bussiness Date");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
		
	    if(k<dateOfDisbursal.length-1){
	    	
	    	 var DisbDate = dateOfDisbursal[k].value;	
			 var disbDate1 = dateOfDisbursal[k+1].value;	
			 var dt4=getDateObject(DisbDate,formatD.substring(2, 3));
			 var dt5=getDateObject(disbDate1,formatD.substring(2, 3));
			
			 if(dt5 < dt4){			 
			 alert("next Disbursal date should be greater than previous Disbursal date");
			 DisButClass.prototype.EnbButMethod();
			 return false;
		 }	
	    }
	}
 }
 

	 if (sum!=loanAmount)
	  {
		  alert("Amount should be equal to Sanctioned Amount");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
	
	 var rowCount1=rowCount-1;	
	 
 document.getElementById("NoOfDisbForm").action = contextPath+"/disbursalSaveProcess.do?method=saveDisbursalSchedule";
 document.getElementById("processingImage").style.display = '';
 document.getElementById("NoOfDisbForm").submit();
}

function updateSpecialConditionRemarks()
{
	 //alert("updateSpecialConditionRemarks:");
	var msg1='',msg2='';
	var status=false;
	DisButClass.prototype.DisButMethod();
	 var table = document.getElementById("gridTable");	
	 var rowCount = table.rows.length;
	 var specRemarksValues='';
	 var specStatusValues='';
	 var tempStatus='';
	 var tempRemarks='';
	if(check())
	{
		 if(rowCount>1)
		 {
			 for(var i=1;i<rowCount;i++){
					if(document.getElementById('chk'+i).checked==true){
							var specRemark=trim(document.getElementById('specRemark'+i).value);
							var specStatus=document.getElementById('specConditionStatus'+i).value;
							specRemarksValues=specRemarksValues+specRemark+"/";
							specStatusValues=specStatusValues+specStatus+"/";
						if(specRemark==''){
							msg1="Remark is required for selected row \n";					
						
						}
						if(specStatus=='')
						{
							msg2="Status is required for selected row \n";
						}
					}
			 }
		 }
	}
	else
	{
		 alert("Select at least one record");
		 DisButClass.prototype.EnbButMethod();
		 return false;
	}
	 if(msg1!=''||msg2!='')
	 {
		 alert(msg1+msg2);
		 DisButClass.prototype.EnbButMethod();
		 return false;
	 }
		tempStatus=specStatusValues;
		tempRemarks=specRemarksValues;
	    var sourcepath=document.getElementById("contextPath").value;
	    document.getElementById("specialConditionForm").action=sourcepath+"/updateSpecialConditionRemarks.do?method=updateSpecialConditionRemarks&specRemarksValues="+tempRemarks+" +&specStatusValues="+tempStatus+"";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("specialConditionForm").submit();
		return true;
			 
}

//Surendra Code

function openCamReport()
{
	var sourcepath=document.getElementById("contextPath").value;
	var undDealId=document.getElementById("dealId").value;	
	//lockButtonOnClick("camReport");	
	//alert("In open openCamReport"+undDealId);			
	document.getElementById("underWriterForm").action=sourcepath+"/CPReportsAction.do?undDealId="+undDealId+"&source=UND&reportName=credit_appraisal_memo";
	document.getElementById("underWriterForm").submit();	   	
}
function insValidMaturity()
{
	//alert("ok+"+document.getElementById("fdBookDate").value);

	var policyStartDate = document.getElementById("policyStartDate").value;
	var insMatureDate =document.getElementById("insMatureDate").value;
	
	 dt1=getDateObject(policyStartDate,"-");
     dt2=getDateObject(insMatureDate,"-");

	if (dt1>dt2)
	  {
	  alert("Maturity Date can not be less than Policy Start Date");
	  document.getElementById("insMatureDate").value='';
	  return false;
	  }
	else
	{//alert("ok");
		return true;
	}
}
function policyFirstDateValid()
{
	//alert("ok+"+document.getElementById("fdBookDate").value);

	var policyStartDate = document.getElementById("policyStartDate").value;
	var insMatureDate =document.getElementById("insMatureDate").value;
	
	 dt1=getDateObject(policyStartDate,"-");
     dt2=getDateObject(insMatureDate,"-");
    if(insMatureDate!=''){
	if (dt1>dt2)
	  {
	  alert("Maturity Date can not be less than Policy Start Date");
	  document.getElementById("policyStartDate").value='';
	  return false;
	  }
	else
	{//alert("ok");
		return true;
	}
    }
}



function saveInsuranceDetails()
{
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
	var insuranceAgency=document.getElementById("insuranceAgency");
	var sumAssured=document.getElementById("sumAssured");
	var assetsCollateralDesc=document.getElementById("assetsCollateralDesc");
	var policyNo=document.getElementById("policyNo");
	var premiumAmount=document.getElementById("premiumAmount");
	var policyStartDate=document.getElementById("policyStartDate");
	var insMatureDate=document.getElementById("insMatureDate");
	var surrenderValue=document.getElementById("assetsCollateralValue");
	if(insuranceAgency.value == ''|| sumAssured.value == ''|| assetsCollateralDesc.value == ''){
		 var msg= '';
		 
		 if(insuranceAgency.value == '')
				msg += '* Insurance Agency name is required.\n';
		 if(sumAssured.value == '')
				msg += '* Sum Assured is required.\n';
		 if(assetsCollateralDesc.value == '')
			msg += '* Insurance Description is required.\n';
		
		
		if(msg.match("Agency")){
 			insuranceAgency.focus();
 		}/*else if(msg.match("No.")){
			policyNo.focus();
 		}else if(msg.match("Amount")){
 			premiumAmount.focus();
 		}else if(msg.match("Start")){
 			policyStartDate.focus();
 		}else if(msg.match("Mature")){
 			insMatureDate.focus();
 		}else if(msg.match("Value")){
 			surrenderValue.focus();
 		}*/
 		else if(msg.match("Insurance")){
 			assetsCollateralDesc.focus();
 		}else if(msg.match("Agency")){
 			insuranceAgency.focus();
 		}else if(msg.match("Assured")){
 			sumAssured.focus();
 		}
		
		alert(msg);
		document.getElementById("save").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	 
	 }
	
	else{

	if(!(document.getElementById("assetsIdIns").value)==''){
		var primaryId = document.getElementById("assetsIdIns").value;
   // alert(primaryId);
	    var assetsType = document.getElementById("fd2").value;	
		document.getElementById("INSForm").action=contextPath+"/collateralInsuranceProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
		document.getElementById("processingImage").style.display = '';
	 	document.getElementById("INSForm").submit();
	 	return true;
	}
	else
	{		
	
	document.getElementById("INSForm").action=contextPath+"/collateralInsuranceProcessAction.do?method=saveCollateralDetails";
	document.getElementById("processingImage").style.display = '';
 	document.getElementById("INSForm").submit();
 	return true;
	}

}
}

// Added by Asesh
function onsearchDealMovement(alert1)
{
	if (document.getElementById("dealNo").value=="")

	{
		alert(alert1);
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else{
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("searchForCPForm").action=basePath+"/dealMovementDispatchAction.do?method=getSearchData";
	    document.getElementById("searchForCPForm").submit();
	 
	    return true;
     	}
	}

function financialAutoCalculation()
{
	
	alert("In Process");
	/*DisButClass.prototype.DisButMethod();
	var contextPath =document.getElementById('contextPath').value ;
	var financialFormula=document.getElementsByName("financialFormula");
	
	var pCode=document.getElementsByName("pCode");
	var year1=document.getElementsByName("year1");
	var year2=document.getElementsByName("year2");
	var year3=document.getElementsByName("year3");
	var year4=document.getElementsByName("year4");
	var year5=document.getElementsByName("year5");
		
	if(pCode!='' || pCode!='undefined'||pCode!=null)
	{	
		 for(var i=0;i<pCode.length;i++) 
         {
			 var pCode=pCode[i].value;	
			 var y1=year1[i].value;	
		
			if(financialFormula!='' || financialFormula!='undefined'||financialFormula!=null)
			{
		          for(var i=0;i<financialFormula.length;i++) 
		          {
					 var finFormula=financialFormula[i].value;	
					
					 alert("finFormula: "+finFormula);
					 var operands = finFormula.match(/[\/\*\+\-\^]|(?:[^[\/\*\+\-\^]]|[\/\*\+\-\^](?![\/\*\+\-\^]))+/g);
					 var nums = finFormula.split(/[\/\*\+\-\^]/);
					 alert("operands: "+operands);
					 alert("nums: "+nums);
					
		
				  }
			}
         }
	}*/
}

// End Asesh Code


function validateSactionedLoanAmt()
{

	var requestAmt=document.getElementById("requestAmt").value;
	if(requestAmt!='')
	{
		requestAmt = removeComma(requestAmt);
	   var sancAmt=document.getElementById("sancAmt").value;
	   if(sancAmt!='')
	   {
		   sancAmt = removeComma(sancAmt);
	   }
	   if(sancAmt>requestAmt)
	    {
	    	alert("Sanctioned Amount cannot be greter than Requested Loan Amount");
	    	document.getElementById("sancAmt").value = '';
	    	document.getElementById("sancAmt").focus();
	    	return false;
	    }
	   else
	   {
		   return true;
	   }
	}
}

function checkInsurerDate(bgInDate)
{
	var bgInDate=document.getElementById("bgInDate").value;
	var businessdate=document.getElementById("businessdate").value;
	var formatD=document.getElementById("formatD").value;
	var dt1=getDateObject(businessdate,formatD.substring(2,3));
	var dt3=getDateObject(bgInDate,formatD.substring(2,3));
	if(bgInDate!='' && businessdate!=''){
	if(dt1<dt3)
			{
				alert("BG Insurer Date should be less than or equal to  Business date.");
				document.getElementById("bgInDate").value='';
				return false;
			}
	}
}
		
function checkValidityDate(bgValidity)
{
			var bgValidity=document.getElementById("bgValidity").value;
			var bgInDate=document.getElementById("bgInDate").value;
			var businessdate=document.getElementById("businessdate").value;
			var formatD=document.getElementById("formatD").value;
			var dt1=getDateObject(businessdate,formatD.substring(2,3));
			var dt3=getDateObject(bgValidity,formatD.substring(2,3));
			var dt2=getDateObject(bgInDate,formatD.substring(2,3));
			if(businessdate!='' && bgValidity!=''){
			if(dt1>=dt3)
					{
						alert("BG Validity Date should be greater than Business date.");
						document.getElementById("bgValidity").value='';
						return false;
					}
			}
			if(bgInDate!='' && bgValidity!=''){
			if(dt2>=dt3)
			{
				alert("BG Validity Date should be greater than BG Insurer Date.");
				document.getElementById("bgValidity").value='';
				return false;
			}
			}
}		

function byPassManualDeviation(fwdMsg)
{
	if(!confirm(fwdMsg))	 
    {
       	DisButClass.prototype.EnbButMethod();
    	return false;
    }
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById("deviationMakerForm").action = contextPath+"/manualDeviationMakerAction.do?method=byPassManualDeviation";
    document.getElementById("processingImage").style.display = '';
	document.getElementById("deviationMakerForm").submit();
	return true;
					 
}	


function openDealLovUnderWriter()
{	var customerExposureRequestedLoanAmount =document.getElementById('customerExposureRequestedLoanAmount').value;
	//alert("customerExposureRequestedLoanAmount  : "+customerExposureRequestedLoanAmount);
var functionId=document.getElementById('functionId').value;
	if(functionId=="500000123"){
		openLOVCommon(421,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');
	}else{
	if(customerExposureRequestedLoanAmount=='Y')
		openLOVCommon(58,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');
	else
		openLOVCommon(2026,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');
}
}

function openCustomerExposure(type,txnId) {
	
	//alert(val);
	var contextPath =document.getElementById('contextPath').value ;
	var url=contextPath+"/viewCustomerExposureBehindAction.do?method=openViewCustomerExposure&txnId="+txnId+"&txnType="+type+" ";
	//alert(url);
		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		return false;

}

function openCamReports()
{
	var sourcepath=document.getElementById("contextPath").value;
	var undDealId=document.getElementById("lbxDealNomain").value;
	//alert("In open openCamReport"+undDealId);			
     //lockButtonOnClick("camReport");	
	document.getElementById("searchForCMForm").action=sourcepath+"/CPReportsAction.do?undDealId="+undDealId+"&source=UND&reportName=credit_appraisal_memo";
	document.getElementById("searchForCMForm").submit();	   	
}

function disbursementReport()
{
	var sourcepath=document.getElementById("contextPath").value;
	var loanId=document.getElementById("lbxLoanNoHIDmain").value;
	//alert("In open openCamReport"+undDealId);		
     //lockButtonOnClick("mybutton");	
	document.getElementById("searchForCMForm").action=sourcepath+"/CPReportsAction.do?loanId="+loanId+"&reportName=subsequentDisbursementVoucher";
	document.getElementById("searchForCMForm").submit();
 	
	// document.getElementById("searchForCMForm").action=sourcepath+"/AllReports.do?loanId="+loanId;
	// document.getElementById("searchForCMForm").submit();
}
//Manish Branwal on19/05/2014
function saveMISService()
{
	//alert("hello");
	var contextPath=document.getElementById("contextPath").value;
	var serviceBranch=document.getElementById("serviceBranch").value;
	var loanNo=document.getElementById("loanNo").value;
	if(serviceBranch=='')
		{
		alert("*Service Branch is required")
		return false;
		}
		{
		document.getElementById("serviceBranchForm").action=contextPath+"/MisServiceAction.do?serviceBranch="+serviceBranch+"&loanNo="+loanNo+"";
		//document.getElementById("INSForm").action=contextPath+"/collateralInsuranceProcessAction.do?method=saveCollateralDetails";
		document.getElementById("serviceBranchForm").submit();
		return true;
		}
	
	
	
}

function savegoldOrnamentDetails()
{ 
	//alert("I m In savegoldOrnamentDetails PAge");
	DisButClass.prototype.DisButMethod();
	var contextPath= document.getElementById("contextPath").value;
    var grossWeight =document.getElementById("grossWeight");
    var deduction = document.getElementById("deduction");
    var netWeight = document.getElementById("netWeight");
    var loanAmountEligible= document.getElementById("loanAmountEligible");
	var goldOrnamentLTV= document.getElementById("goldOrnamentLTV");
	var rateGoldOrnament= document.getElementById("rateGoldOrnament");
	var netOrnamentValue= document.getElementById("netOrnamentValue");
	var quantity= document.getElementById("quantity");
	var goldloanAmount= document.getElementById("goldloanAmount");
	

	if(grossWeight.value == ''||deduction.value == ''||netWeight.value == ''||deduction.value == ''||loanAmountEligible.value == ''||goldOrnamentLTV.value == ''||rateGoldOrnament.value == ''||netOrnamentValue.value == ''||quantity.value == '' ){
		
		var msg= '';
	if(grossWeight.value == '')
		msg += '* Gross Weight is required.\n';
	if(deduction.value == '')
		msg += '* Deduction is required.\n';
	if(netWeight.value == '')
		msg += '* Net Weight is required.\n';
	if(loanAmountEligible.value == '')
		msg += '* Loan Amount is required.\n';
	if(goldOrnamentLTV.value == '')
		msg += '* Ltv is required.\n';
	if(rateGoldOrnament.value == '')
		msg += '* Rate is required.\n';	
	if(netOrnamentValue.value == '')
		msg += '* Net Ornament Value is required.\n';
	if(quantity.value == '')
		msg += '* Quantity is required.\n';	
		
			alert(msg);
			DisButClass.prototype.EnbButMethod();
 		return false;
	}
if(document.getElementById("quantity").value<1)
    {
	       alert("Quantity Should greater than Zero");
		   DisButClass.prototype.EnbButMethod();
	     return false;
       }	

//if(document.getElementById("goldloanAmount").value>document.getElementById("loanAmountEligible").value)
var goldAmount=parseFloat(document.getElementById("goldloanAmount").value);
var loanAmountEligible=parseFloat(document.getElementById("loanAmountEligible").value);
//alert("goldloanamount"+goldAmount);
//alert("loanAmountEligible"+loanAmountEligible);
if(goldAmount>document.getElementById("loanAmountEligible").value)

{
       alert("Requested Loan Amount should be less than or equal to Loan Amount(Eligible)");
	   DisButClass.prototype.EnbButMethod();
     return false;
   }

	   if(deduction>grossWeight)
    {
	       alert("Deduction Weight should be less than Gross Weight. ");
	       document.getElementById("grossWeight").value='';
		   document.getElementById("deduction").value='';
		   DisButClass.prototype.EnbButMethod();
	     return false;
       }
	
	if (document.getElementById("GoldOrnamentForm"))
	{
		//alert("ok");
	        if(document.getElementById("assetsIdGoldOrnament").value!='')
	        {
	
			var primaryId = document.getElementById("assetsIdGoldOrnament").value;
		    //alert(primaryId);
		     var assetsType = document.getElementById("bg2").value;	
			document.getElementById("GoldOrnamentForm").action=contextPath+"/collateralGoldOrnamentProcessAction.do?method=updateCollateralDetails&primaryId="+primaryId+" +&assetsType="+assetsType+"";
			document.getElementById("processingImage").style.display = '';
		 	document.getElementById("GoldOrnamentForm").submit();
		 	return true;
		}
		else
	  {	
			//alert("In else");
			document.getElementById("GoldOrnamentForm").action=contextPath+"/collateralGoldOrnamentProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
			//alert("In else"+document.getElementById("BGForm").action);
			document.getElementById("GoldOrnamentForm").submit();	
	   }
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}

function savegoldOrnamentDetails1()
{ 
	DisButClass.prototype.DisButMethod();

	var contextPath= document.getElementById("contextPath").value;
	//alert("ok"+document.getElementById("BGForm")+"contextPath: "+contextPath);
	if(document.getElementById("GoldOrnamentForm"))
	{
			//alert("In else");
			document.getElementById("GoldOrnamentForm").action=contextPath+"/collateralGoldOrnamentProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
			//alert("In else"+document.getElementById("BGForm").action);
			document.getElementById("GoldOrnamentForm").submit();	
	   
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}

}
function searchDealReassignment(type)
{
	// alert(type);
	DisButClass.prototype.DisButMethod();
	var dealNo = document.getElementById("dealNo").value;
	var custName = document.getElementById("customername").value;
	if(dealNo=='' && custName=='')
	{
		alert("Please Enter One value to Search.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("dealReassignmentSearchForm").action = contextPath+"/dealReassignmentSearch.do?method=searchDealReassignment&type="+type;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("dealReassignmentSearchForm").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
	}	
}

function openNewDealReassignment()
{
	DisButClass.prototype.DisButMethod();
	var contextPath=document.getElementById("contextPath").value;
    document.getElementById("dealReassignmentSearchForm").action = contextPath+"/dealReassignmentSearch.do?method=openNewDealReassignment";
    document.getElementById("processingImage").style.display = '';
    document.getElementById("dealReassignmentSearchForm").submit();
}

function searchDealReassignmentMaker()
{
	DisButClass.prototype.DisButMethod();
	var dealNo = document.getElementById("dealNo").value;
	var dealId = document.getElementById("lbxDealNo").value;
	var custName = document.getElementById("customerName").value;
	if(dealNo=='' && custName=='')
	{
		alert("Please Enter One value to Search.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("search").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("dealReassignmentMakerForm").action = contextPath+"/dealReassignmentMaker.do?method=searchDealForReassignmentMaker&dealId="+dealId;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("dealReassignmentMakerForm").submit();
	    document.getElementById("search").removeAttribute("disabled","true");
	}
}

function editDealReassignmentMaker()
{
	DisButClass.prototype.DisButMethod();
	var checkId = document.getElementsByName("checkId");
	var dealId = document.getElementById("lbxDealNo").value;
	var id = "";
	for(var i=0;i<checkId.length;i++)
	{
		if( checkId[i].checked==true)
		{
			//alert(checkId[i].value);
			id=checkId[i].value;
		}
	} 
	if(id=="")
	{
		alert("Please Select One Record to Edit.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("edit").removeAttribute("disabled","true");
	}
	else
	{
		var contextPath=document.getElementById("contextPath").value;
		var url = contextPath+"/dealReassignmentMaker.do?method=openDealReassignmentEdit&dealId="+dealId+"&checkId="+id+"";
		newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
		if (window.focus) {newwindow.focus()}
		return false;
	}
}

function saveDealReassignmentEdit()
{
	var stage = document.getElementById("initiateStage").value;
	var stageAction = document.getElementById("stageAction").value;
	var assignedTo = document.getElementById("lbxUserId").value;
	var dealId = document.getElementById("lbxDealNo").value;
	if(stage=="")
	{
		alert("Stage is Required.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	else if(stageAction=="")
	{
		alert("Stage Action is Required.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
		return false;
	}
	if((stageAction=="UR") && (stage=="DC" || stage=="BSA" || stage=="FFC" || stage=="FAC" || stage=="MDV" || stage=="POC") ){
		var a='';
	}
	else if(stageAction=="UR"){
		document.getElementById("lbxUserId").value='';
		alert("USER REASSIGNMENT IS NOT ALLOWED FOR THIS STAGE.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
	}
	
	if((stageAction=="SC") && (stage=="DC" || stage=="BSA" || stage=="FFC" || stage=="FAC" || stage=="MDV" || stage=="POC" || stage=="FVI") ){
		var b='';
	}
	else if(stageAction=="SC"){
		alert("STAGE CORRECTION IS NOT ALLOWED FOR THIS STAGE.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
	}
	if(stageAction=="UR" && assignedTo==''){
		alert("USER NAME IS REQUIRED");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
	}
	
	else
	{
		var contextPath=document.getElementById("contextPath").value;
	    document.getElementById("dealReassignmentMakerForm").action = contextPath+"/dealReassignmentMaker.do?method=saveDealReassignmentEdit&dealId="+dealId;
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("dealReassignmentMakerForm").submit();
	}
}


function editEnableAndDisableField()
{
	var stage = document.getElementById("initiateStage").value;
	var stageAction = document.getElementById("stageAction").value;
	document.getElementById("lbxUserId").value='';
	if(stage=="")
	{
		alert("Stage is Required.");
		return false;

	}
	else if(stageAction=="")
	{
		alert("Stage Action is Required.");
		return false;
	}
	if((stageAction=="UR") && (stage=="DC" || stage=="BSA" || stage=="FFC" || stage=="FAC" || stage=="MDV" || stage=="POC") ){
		var a='';
	}
	else if(stageAction=="UR"){
		document.getElementById("lbxUserId").value='';
		alert("USER REASSIGNMENT IS NOT ALLOWED FOR THIS STAGE.");
		DisButClass.prototype.EnbButMethod();
		document.getElementById("save").removeAttribute("disabled","true");
	}
	
	if((stageAction=="SC") && (stage=="DC" || stage=="BSA" || stage=="FFC" || stage=="FAC" || stage=="MDV" || stage=="POC" || stage=="FVI") ){
		var b='';
	}
	else if(stageAction=="SC") {
		alert("STAGE CORRECTION IS NOT ALLOWED FOR THIS STAGE.");
		return false;
	}
	
	if(stageAction=='UR')
		{
		if(stage=='DC')
			{
			document.getElementById("1").style.display='';	
			document.getElementById("2").style.display='';
			document.getElementById("3").style.display='none';
			document.getElementById("6").style.display='none';
			document.getElementById("7").style.display='none';
			document.getElementById("8").style.display='none';
			document.getElementById("9").style.display='none';
			document.getElementById("10").style.display='none';
			}
		else if(stage=='BSA')
			{
			document.getElementById("1").style.display='';	
			document.getElementById("2").style.display='none';
			document.getElementById("3").style.display='';
			document.getElementById("6").style.display='none';
			document.getElementById("7").style.display='none';
			document.getElementById("8").style.display='none';
			document.getElementById("9").style.display='none';
			document.getElementById("10").style.display='none';

			}
//		else if(stage=='QC')
//		{
//		document.getElementById("1").style.display='';	
//		document.getElementById("2").style.display='none';
//		document.getElementById("3").style.display='none';
//		document.getElementById("4").style.display='';
//		document.getElementById("5").style.display='none';
//		document.getElementById("6").style.display='none';
//		document.getElementById("7").style.display='none';
//		document.getElementById("8").style.display='none';
//		document.getElementById("9").style.display='none';
//		document.getElementById("10").style.display='none';
//		}
//		else if(stage=='FVI')
//		{
//		document.getElementById("1").style.display='';	
//		document.getElementById("2").style.display='none';
//		document.getElementById("3").style.display='none';
//		document.getElementById("4").style.display='none';
//		document.getElementById("5").style.display='';
//		document.getElementById("6").style.display='none';
//		document.getElementById("7").style.display='none';
//		document.getElementById("8").style.display='none';
//		document.getElementById("9").style.display='none';
//		document.getElementById("10").style.display='none';
//		}
		else if(stage=='FAC')
		{
		document.getElementById("1").style.display='';	
		document.getElementById("2").style.display='none';
		document.getElementById("3").style.display='none';
		document.getElementById("6").style.display='';
		document.getElementById("7").style.display='none';
		document.getElementById("8").style.display='none';
		document.getElementById("9").style.display='none';
		document.getElementById("10").style.display='none';

		}
		else if(stage=='FFC')
		{
		document.getElementById("1").style.display='';	
		document.getElementById("2").style.display='none';
		document.getElementById("3").style.display='none';
		document.getElementById("6").style.display='none';
		document.getElementById("7").style.display='';
		document.getElementById("8").style.display='none';
		document.getElementById("9").style.display='none';
		document.getElementById("10").style.display='none';
		}
		else if(stage=='MDV')
		{
		document.getElementById("1").style.display='';	
		document.getElementById("2").style.display='none';
		document.getElementById("3").style.display='none';
		document.getElementById("6").style.display='none';
		document.getElementById("7").style.display='none';
		document.getElementById("8").style.display='';
		document.getElementById("9").style.display='none';
		document.getElementById("10").style.display='none';
		}
		else if(stage=='MDV')
		{
		document.getElementById("1").style.display='';	
		document.getElementById("2").style.display='none';
		document.getElementById("3").style.display='none';
		document.getElementById("6").style.display='none';
		document.getElementById("7").style.display='none';
		document.getElementById("8").style.display='none';
		document.getElementById("9").style.display='';
		document.getElementById("10").style.display='none';

		}


		}
	else if(stageAction=='SC'){
		if(stage=="DC" || stage=="BSA" || stage=="FVI" || stage=="FAC" || stage=="FFC" || stage=="POC" )
		{
			document.getElementById("1").style.display='none';	
			document.getElementById("2").style.display='none';
			document.getElementById("3").style.display='none';
			document.getElementById("6").style.display='none';
			document.getElementById("7").style.display='none';
			document.getElementById("8").style.display='none';
			document.getElementById("9").style.display='none';
			document.getElementById("10").style.display='';
			document.getElementById("11").style.display='';
		}
	}
	
	
	
	}


function saveReassignDealAuthor(){
	
	DisButClass.prototype.DisButMethod();
	var comments = document.getElementById("authorRemarks");
	var decisionValue=document.getElementById("decision").value;
	if ((document.getElementById("decision").value=='') || (trim(comments.value)=='') ){
		 alert("*Please fill required fields");
		 comments.focus();
		 DisButClass.prototype.EnbButMethod();
 		 document.getElementById("save").removeAttribute("disabled","true");
 		 return false;	 
	}else{
		document.getElementById("dealReassignAuthorForm").action="dealReassignmentAuthor.do?method=saveDealReassignAuthor";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("dealReassignAuthorForm").submit();
		return true;	
	}
}
function onsearchDealSendBack(alert1)
{
	var lbxDealNo=document.getElementById("dealNo").value;
	var customerName=document.getElementById("customerName").value;
	if (document.getElementById("lbxDealNo").value=="")

	{
		alert(alert1);
		document.getElementById("search").removeAttribute("disabled");
		return false;
	}else{
		var basePath=document.getElementById("contextPath").value;
	    document.getElementById("searchForCPForm").action=basePath+"/dealSendBackDispatchAction.do?method=getSearchData&dealId="+lbxDealNo+"&customerName="+customerName;
	    document.getElementById("searchForCPForm").submit();
	 
	    return true;
     	}
	}
	
function saveDealSendBack(alert1)


	
	{
		DisButClass.prototype.DisButMethod();
		var contextPath= document.getElementById("contextPath").value;
		var remarks=document.getElementById("remarks");
		var sendBackStage=document.getElementById("sendBackStage");
		var rpStageFlag=document.getElementById("rpStageFlag");
		
		if(remarks.value == ''|| sendBackStage.value == ''){
			 var msg= '';
			 
			 if(remarks.value == '')
					msg += '* Send Back Remarks is required.\n';
			 if(sendBackStage.value == '')
					msg += '* Send Back Stage is required.\n';
			 
			alert(msg);
			//document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		 
		 }
		
	
		else
		{		
		
			var basePath=document.getElementById("contextPath").value;
		    document.getElementById("searchForCPForm").action=basePath+"/dealSendBackDispatchAction.do?method=saveDealSendBack&dealId="+lbxDealNo;
		    document.getElementById("searchForCPForm").submit();
	 	return true;
		}

	}
	
	function showCheckBox1(){
	
	//alert("showCheckBox1");
	var sendBackStage=document.getElementById("sendBackStage").value;
	
	if(sendBackStage=='FAC'||sendBackStage=='FFC'||sendBackStage=='FVI')
	{
		document.getElementById("rpStageFlag").setAttribute("checked","true");
		document.getElementById("rpStageFlag").removeAttribute("disabled","true");
	}
	else
	{
		document.getElementById("rpStageFlag").removeAttribute("checked","true");
		document.getElementById("rpStageFlag").setAttribute("disabled","true");
	}

	}
	
function downloadFile(fileName)
	{
	//	alert("download File: "+fileName);
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("financialBalacnceSheetForm").action=sourcepath+"/saveFinancialBalanceSheet.do?method=downloadUnderwritingFile&fileName="+fileName;
		 document.getElementById("financialBalacnceSheetForm").submit();
		return true;
	}
	function downloadFileOthers(fileName)
	{
	//	alert("download File: "+fileName);
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("othersForm").action=sourcepath+"/saveFinancialBalanceSheet.do?method=downloadUnderwritingFile&fileName="+fileName;
		 document.getElementById("othersForm").submit();
		return true;
	}
	function downloadFileProfitLoss(fileName)
	{
	//	alert("download File: "+fileName);
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById("profitAndLossForm").action=sourcepath+"/saveFinancialBalanceSheet.do?method=downloadUnderwritingFile&fileName="+fileName;
		 document.getElementById("profitAndLossForm").submit();
		return true;
	}

function savegoldOrnamentDetails1()
{ 
	DisButClass.prototype.DisButMethod();

	var contextPath= document.getElementById("contextPath").value;
	//alert("ok"+document.getElementById("BGForm")+"contextPath: "+contextPath);
	if(document.getElementById("GoldOrnamentForm"))
	{
			//alert("In else");
			document.getElementById("GoldOrnamentForm").action=contextPath+"/collateralGoldOrnamentProcessAction.do?method=saveCollateralDetails";
			document.getElementById("processingImage").style.display = '';
			//alert("In else"+document.getElementById("BGForm").action);
			document.getElementById("GoldOrnamentForm").submit();	
	   
	}else{
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	}
	
	function reloadBalanceSheet(formName)
	{
		
		var entityCustomerType=document.getElementById("entityCustomerType").value;
		var lbxCustomerId=document.getElementById("lbxCustomerId").value;
		var sourceType=document.getElementById("sourceType").value;
		var entityCustomerName=document.getElementById("entityCustomerName").value;
		var lbxCustomerRoleType=document.getElementById("lbxCustomerRoleType").value;
		
		if(entityCustomerType=='')
		{
			alert('Please Select Applicant Type');
			return false;
		}
		else{
		var sourcepath=document.getElementById("contextPath").value;
		document.getElementById(formName).action=sourcepath+"/editFinancialAnalysis.do?method=openEditFinancialAnalysis&lbxCustomerId="+lbxCustomerId+"&sourceType="+sourceType+"&entityCustomerName="+entityCustomerName+"&entityCustomerType="+entityCustomerType+"&lbxCustomerRoleType="+lbxCustomerRoleType;
		 document.getElementById("processingImage").style.display = '';
		document.getElementById(formName).submit();
		return true;
		}
}

function checkEntityType()	
{
	
	var type = document.getElementById('entityCustomerType').value;
	if(type=='APPLICANT')
	{
		var c = document.getElementById("allchk").checked;
		document.getElementById("allchk").setAttribute("disabled","true");
			var ch=document.getElementsByName("chk");
			var zx=0;
			// alert(c);
			
				for(i=0;i<ch.length;i++)
				{
				ch[zx].checked=true;
				ch[zx].setAttribute("disabled","true");
				zx++;
				}
	}
	else if(type=='CO APPLICANT')
	{
	var c = document.getElementById("allchk").checked;
		document.getElementById("allchk").removeAttribute("disabled","true");
			var ch=document.getElementsByName("chk");
			var zx=0;
			// alert(c);
			
				for(i=0;i<ch.length;i++)
				{
				ch[zx].removeAttribute("disabled","true");
				//ch[zx].checked=true;
				zx++;
				}
	}
	else
		{
		var c = document.getElementById("allchk").checked;
		document.getElementById("allchk").removeAttribute("disabled","true");
			var ch=document.getElementsByName("chk");
			var zx=0;
			// alert(c);
			
				for(i=0;i<ch.length;i++)
				{
				ch[zx].removeAttribute("disabled","true");
				ch[zx].checked=true;
				zx++;
				}
		}
}

function changeReloadFlag()
{
	document.getElementById("reloadFlag").value='N';
}
function generateReportProcess(formName,dealId)
{
	var contextPath =document.getElementById('contextPath').value ;
	document.getElementById(formName).action =contextPath+"/generateCamReport.do?method=generateMDBProcess&dealId="+dealId;
	document.getElementById(formName).submit();
}
function reportRefresh(formName)
{
	var reportName=document.getElementById("reportName").value;
	
	if(reportName=='CAM' || reportName=='OBLIGATION' || reportName=='LOAN_SHEET')
	{
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById(formName).action =contextPath+"/generateCamReport.do?method=refreshForReportLink&reportName="+reportName;
		document.getElementById(formName).submit();	
		return true;
	}
	else if(reportName=='')
	{
		alert('Please Select Report');
		return false;
	}
	else
	{
		alert('Please Select CAM Report');
		return false;
	}
}

function generateBankAccAnalysisReport(formName,val)
{
	var contextPath =document.getElementById('contextPath').value ;
	
	var methodName='';
			 document.getElementById('reportForm').action =contextPath+"/generateCamReport.do?method=downloadFile&reportName="+val;
		     document.getElementById('reportForm').submit();
		
}

//Change start for scheme report on 16-10-2015
function generateSchemeReport(val)
{
	//lockButtonOnClick('SchemeReportButton');
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById(val).action=sourcepath+"/generateSchemeReport.do?method=generateSchemeReport";
	document.getElementById(val).submit();
	return true;
}
//Change End for scheme report
//Rohit Chnages for Dedupe Starts
function callDedupeReferral(type,txnId) {
		
		//alert(val);
		var contextPath =document.getElementById('contextPath').value ;
		var url=contextPath+"/dedupeReferalSearch.do?method=searchUnderwriterCustomer&txnId="+txnId+"&txnType="+type+" ";
		//alert(url);
			newwindow=window.open(url,'name','height=400,width=1100,toolbar=no,scrollbars=yes');
			if (window.focus) {newwindow.focus()}
			return false;

}
//Rohit Changes for Dedupe end
//ankita for facility details
function openFacilityDetails(val) {
	//alert("jjjjjjj");
	var contextPath = document.getElementById('contextPath').value;
	var url = contextPath
			+ "/facilityDetailsDispatch.do?method=fetchFacilityDetails&dealId="
			+ val + " ";
	newwindow = window.open(url, 'name',
			'height=400,width=1100,toolbar=no,scrollbars=yes');
	if (window.focus) {
		newwindow.focus()
	}
	return false;
}
function callOnLinkOrButtonWindow(val) 
		{
			//alert("ok"+val);
			if(document.getElementById(val).value=='' || document.getElementById(val).value== null || document.getElementById(val).value == false)
				openWins[curWin++] = '';
			else
				openWins[curWin++] = window.open('','name',"height=300,width=1000,status=yes,toolbar=no,menubar=no,location=center, scrollbars=yes");
		}
/*
function getbusinessloan()
{
	alert("getbusinessloan");
	var loanId=document.getElementById("loanId").value;
	if(loanId=="")
		{
		alert("Please save the loan first and then select it.");
		document.getElementById("businessType").value="";
		return false;
		}
	return true;
	}*/

function DisableDropdown(id1){
    var elementID;
    elementID = document.getElementById(id1);
    elementID.disabled = true;
}

//ankita end

//pooja code starts
function saveColandingApprovalData()
{
	DisButClass.prototype.DisButMethod();
	document.getElementById("remarks").value=trim(document.getElementById("remarks").value);
	var remarks = document.getElementById("remarks").value;
	var decision = document.getElementById("decision").value;
	if(decision!='' && decision=='X')
	{
		var reasonDesc = document.getElementById("reasonDesc").value;
		if(reasonDesc=='')
		{
		    alert("REASON IS REQUIRED.");
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}
	
	// END BY PRASHANT	
	if(remarks!='')
	{
		if(remarks.length>2000)
		{
			alert("Author Remarks can't be more than 2000 character.");
			document.getElementById("remarks").value='';
			document.getElementById("remarks").focus();
			document.getElementById("saveApprove").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			var sourcepath=document.getElementById("contextPath").value;
			document.getElementById("approvalForm").action=sourcepath+"/underwritingApproval.do?method=saveApprovalData";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("approvalForm").submit();
			return true;
		}
	}
	else
	{
		alert("Author Remarks is Required.");
		document.getElementById("remarks").focus();
		document.getElementById("saveApprove").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function deleteDeviation() {
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;
	var table1 = document.getElementById("gridTable1");
	var rowCount1 = table1.rows.length;
	var chkValue = '';
	var chkValueForAuto = '';
	var checkFlag = 'N';
	var msg1 = '';
	var status = false;
	var stage = "";
	var deleteId = "";
	if (document.getElementById("stage")) {
		stage = document.getElementById("stage").value;
	}
	var contextPath = document.getElementById('contextPath').value;

	if (rowCount > 1) {
		for (var i = 1; i < rowCount; i++) {

			var chk = document.getElementById("chk" + i);

			if (chk.checked == true) {
				var manRecStatus="I";
				if(document.getElementById('manRecStatus' + i)){
					manRecStatus = document.getElementById('manRecStatus' + i).value;
				}
				
				// Check if there is no decision taken on the deviation, as in this case only
				// we will allow to delete any deviation from screen else deletion will not be
				// allowed. 'I' means no decision is taken on screen.
				if(manRecStatus == 'I'){
					deleteId = deleteId + chk.value + "/";	
				}
			}

		/*	var manRecStatus = document.getElementById('manRecStatus' + i).value;

			if (manRecStatus == '') {
			manRecStatus = "I";
		}
			if (chkValue != "" && manRecStatus == 'I') {
			chkValue = chkValue + ',' + manRecStatus;
			} else {
				chkValue = manRecStatus;
			}
			if (manRecStatus != 'I') {
				checkFlag = 'N';
			} else {
				checkFlag = 'N';
		}*/
	
		}        // end of for loop

	}

	if(manRecStatus != 'I'){
		alert("Deviation with decision can't be deleted");
	}
else {
		//alert(deleteId);
		var ch = document.getElementsByName('recStatuses');
		var checkedhold = "";
		document.getElementById("deviationApprovalForm").action = contextPath
				+ "/saveDeviationApproval.do?method=deleteDeviation&deleteId="
				+ deleteId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("deviationApprovalForm").submit();
		return true;
}
}
