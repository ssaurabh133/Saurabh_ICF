function newSearchDisbursalMakerLoan()
 {
 	DisButClass.prototype.DisButMethod();
 	var loanNo=document.getElementById("loanNo").value;
 	
 	var customerName=document.getElementById("customerName").value;
 	var product=document.getElementById("product").value;
 	var scheme=document.getElementById("scheme").value;
 	//var reportingToUserId=document.getElementById("reportingToUserId").value;
 	var contextPath= document.getElementById("contextPath").value;
 	
 	
 	if(loanNo!=''||customerName!=''||product!=''||scheme!='')
 	{
 		if(customerName!='' && customerName.length>=3)
 		{
 			document.getElementById("CreditForm").action=contextPath+"/disbursalPlanSearchMaker.do?method=disbursalPlanSearchMaker";
 			document.getElementById("processingImage").style.display = '';
 			document.getElementById("CreditForm").submit();
 			return true;
 		}
 		else if(customerName=='')
 		{
 			document.getElementById("CreditForm").action=contextPath+"/disbursalPlanSearchMaker.do?method=disbursalPlanSearchMaker";
 			document.getElementById("processingImage").style.display = '';
 			document.getElementById("CreditForm").submit();
 			return true;
 		}
 		else
 		{
 			alert("Please Enter atleast 3 characters of Customer Name ");
 			DisButClass.prototype.EnbButMethod();
 			//document.getElementById("searchButton").removeAttribute("disabled", "true");
 			return false;
 		}
 		
 	}
 	else
 	{
 		alert("Please Enter atleast one field");
 		DisButClass.prototype.EnbButMethod();
 		//document.getElementById("searchButton").removeAttribute("disabled", "true");
 		return false;
 	}
 }

function newSearchDisbursalAuthorLoan()
{
	DisButClass.prototype.DisButMethod();
	var loanNo=document.getElementById("loanNo").value;
	var customerName=document.getElementById("customerName").value;
	var product=document.getElementById("product").value;
	var scheme=document.getElementById("scheme").value;
	//var reportingToUserId=document.getElementById("reportingToUserId").value;
	var contextPath= document.getElementById("contextPath").value;
	
	
	if(loanNo!=''||customerName!=''||product!=''||scheme!='')
	{
		if(customerName!='' && customerName.length>=3)
		{
			document.getElementById("CreditForm").action=contextPath+"/disbursalPlanSearchMaker.do?method=disbursalPlanSearchAuthor";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditForm").submit();
			return true;
		}
		else if(customerName=='')
		{
			document.getElementById("CreditForm").action=contextPath+"/disbursalPlanSearchMaker.do?method=disbursalPlanSearchAuthor";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("CreditForm").submit();
			return true;
		}
		else
		{
			alert("Please Enter atleast 3 characters of Customer Name ");
			DisButClass.prototype.EnbButMethod();
			//document.getElementById("searchButton").removeAttribute("disabled", "true");
			return false;
		}
		
	}
	else
	{
		alert("Please Enter atleast one field");
		DisButClass.prototype.EnbButMethod();
		//document.getElementById("searchButton").removeAttribute("disabled", "true");
		return false;
	}
}
function allChecked()
{
	 //alert("ok");
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;

	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
 	//alert(rowCount);
	//alert(c);
 	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	if(c==true)
	{
		for(i=1;i<psize;i++)
		{
			var ch1=document.getElementById('chk'+i);
			if(ch1!=undefined||ch1!=null){
			document.getElementById('chk'+i).checked=true;
			}
			
		}
	}
	else
	{
		for(i=1;i<psize;i++)
		{
			var ch2=document.getElementById('chk'+i);
			if(ch2!=undefined||ch2!=null){
			document.getElementById('chk'+i).checked=false;
		}
		}
	}
	
}
function addROWAtDisbPlanCM(){
	


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

function removeRowDisbPlan() {
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
    //alert("rowCount:--"+rowCount);
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

function saveDisbPlanInCM()

{
 
DisButClass.prototype.DisButMethod();
 var contextPath = document.getElementById("contextPath").value;
 var formatD=document.getElementById("formatD").value;

 
 var agrementDate = document.getElementById("agrDateDisb").value;	
 var repayEffectiveDate = document.getElementById("repayEffDateDisb").value;	
 var dt1=getDateObject(agrementDate,formatD.substring(2, 3));	 
 var dt2=getDateObject(repayEffectiveDate,formatD.substring(2, 3));	
    
 var loanAmount=removeComma(document.getElementById("loanAmount").value);

 
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
		  alert("Disbursal Date should be greater than agrementDate");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
		 if (dt3>dt2)
		  {
		  alert("Disbursal Date should be less than repayEffectiveDate");
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
		  alert("Amount should be equal to LoanAmount");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
	
	 var rowCount1=rowCount-1;	
	 	 
	 document.getElementById("NoOfDisbForm").action = contextPath+"/loanNoOfDisbProcess.do?method=saveDisbursalPlanMakerMenu";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("NoOfDisbForm").submit();
}

function forwardDisbPlanInCM()

{
 
DisButClass.prototype.DisButMethod();
 var contextPath = document.getElementById("contextPath").value;
 var formatD=document.getElementById("formatD").value;

 
 var agrementDate = document.getElementById("agrDateDisb").value;	
 var repayEffectiveDate = document.getElementById("repayEffDateDisb").value;	
 var dt1=getDateObject(agrementDate,formatD.substring(2, 3));	 
 var dt2=getDateObject(repayEffectiveDate,formatD.substring(2, 3));	
    
 var loanAmount=removeComma(document.getElementById("loanAmount").value);

 
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
		  alert("Disbursal Date should be greater than agrementDate");
		  DisButClass.prototype.EnbButMethod();
		  return false;
		  }
		 if (dt3>dt2)
		  {
		  alert("Disbursal Date should be less than repayEffectiveDate");
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
		  alert("Amount should be equal to LoanAmount");
		  DisButClass.prototype.EnbButMethod();
		  return false;
	  }
	
	 var rowCount1=rowCount-1;	
	 	 
	 document.getElementById("NoOfDisbForm").action = contextPath+"/loanNoOfDisbProcess.do?method=forwardDisbursalPlanMakerMenu";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById("NoOfDisbForm").submit();
}

function saveDisbPlanAuthor()
{
	DisButClass.prototype.DisButMethod();
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
	    document.getElementById("disbursalPlanAuthorForm").action = contextPath+"/loanNoOfDisbProcess.do?method=authorDisbursalPlanMakerMenu";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("disbursalPlanAuthorForm").submit();
	    return true;
	}
}
