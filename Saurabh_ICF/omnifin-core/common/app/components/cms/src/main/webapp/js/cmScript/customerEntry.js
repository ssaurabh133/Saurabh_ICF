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
 
function setText(text1,text2,text3) {
          
            document.getElementById('customerId').value = text1;
            document.getElementById('customerName').value = text2;
            var ch = document.getElementsByName("applicantCategory");
		
   	  
   	    for(i=0;i<ch.length;i++)
  		{
  			
  			if(ch[i].value==text3)
  			{
  			 
  			  ch[i].checked=true;
  			  document.getElementById("cat").value=ch[i].value;
  			 
  			}
  			 ch[i].disabled=true;
  	      }
      }
		
function createCustomer()
{

   if(IsExisting())
   {
   		var applicantCategory = document.getElementsByName("applicantCategory");
  
   
   		for(i=0;i<applicantCategory.length;i++)
  		 {
	   		if(applicantCategory[i].checked)
	   		{
	   			val = applicantCategory[i].value;
	   			flag=0;
	   			break;
	   		}
	   		else
	   		{
	   			flag=1;
	   			
	   		}
   		
   		}

       if(flag==1)
       {
       	alert("Please Select Type of Customer!!!");
       	document.getElementById("createCust").removeAttribute("disabled");
    	return false;
       }
       else
       {
             alert(val);
             document.getElementById("createCust").removeAttribute("disabled");
             if(val=='C')
             {
                var url="<%=request.getContextPath() %>/PageLevelAuthorization.do?contentpageid=5&pageStatuss=fromLeads";
             }
             if(val=='I')
             {
             	var url="<%=request.getContextPath() %>/PageLevelAuthorization.do?contentpageid=6&pageStatuss=fromLeads";
             }
       		
            window.open(url,'create_customer','height=400,width=1000,top=200,left=250');
            
            return true;
       }  	    
 		   
   }
 	    
	
}

function linkCustomer()
		{
		  if(IsExisting())
	       {
			
			
			var url="<%=request.getContextPath() %>/JSP/CPJSP/linkCustomer.jsp";
		
		    window.open(url,'existing_customer','height=400,width=1000,top=200,left=250');
	        return true;
	        
			}
			
		}
		
	
	
	function hideLink()
	{
		document.getElementById("linkCust").setAttribute("disabled", "true");
		document.getElementById("createCust").removeAttribute("disabled", "true");
		  var ch = document.getElementsByName("applicantCategory");
		
   	
   	    for(i=0;i<ch.length;i++)
  		{
  			 ch[i].disabled=false;
  			
  	      }
		return true;
	}
	
	function hideCreate()
	{
		document.getElementById("linkCust").removeAttribute("disabled", "true");
		document.getElementById("createCust").setAttribute("disabled", "true");
		return true;
	}
	
		function newRow()
		{
			
	    	if(confirm("Are you sure to insert a row into table ? ? ?"))
	    	{
	    		var tbl = document.getElementById("dealsTable");
	    		var lastElement = tbl.rows.length;
	    		var newRow = tbl.insertRow(lastElement);
	    		newRow.setAttribute('className','white');
	    		newRow.setAttribute('class','white');
	    		var newCell = newRow.insertCell(0);
	    		newCell.innerHTML = '<input type="checkbox" name="checkbox" value="checkbox" />'
	    		var newCell = newRow.insertCell(1);
	    		
	    		newCell.innerHTML = '<a href="#" onclick="corporate();">1234</a>'
	    		var newCell = newRow.insertCell(2);
	    		
	    		
	    		newCell.innerHTML =	'<label>Sachin Garg</label>'
	    		var newCell = newRow.insertCell(3);
	    		
	    		newCell.innerHTML = '<label>Primary Applicant</label>'
	    		var newCell = newRow.insertCell(4);
	    		
	    		newCell.innerHTML = '<label>Corporate</label>'
	    		var newCell = newRow.insertCell(5);
	    		
	    		newCell.innerHTML = '<label>Yes</label>'
	    		
	    		
	    		
	    		return true;
	    	}
	    	else
	    	{
	    		return false;
	    	}
		}
		
		
		function removeRow()
		{
   			if(chooseProductCheckboxCheck()==1)
  			{
  		  		 if(confirm("Are you sure to remove row from table ? ? ?"))
  		    	{
  		 			var table = document.getElementById("dealsTable");
            		var rowCount = table.rows.length;
            		for(var i=1; i<rowCount; i++) 
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
  		 		
	  		}
  			else
  			{
  			    alert("Select atleast one row!!!");
  				return false;
  			}
  			
	     }
     
    
     function chooseProductCheckboxCheck()
	{
		var ch = document.getElementsByName("existingCustomer");
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
	
	function IsExisting()
	{
		if(chooseProductCheckboxCheck()==1)
		{
			return true;
		}
		else
		{
				alert("Please Select Existing or Non-Existing Customer");
				return false;
		}
	}
	
	function isNumberKey(evt)
	{
        
		var charCode = (evt.which) ? evt.which : event.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric allowed here");
			return false;
		}
			return true;
	}
	
	function corporate()
		{
		    alert("go");
			var url="<%=request.getContextPath() %>/JSP/gcdJSP/firstFrame.jsp";
			alert(url);
		    window.open(url,'corporateFrame','height=400,width=1000,top=200,left=250');
	        return true;
		}
	
	function saveCustomerEntry()
	{
	     //alert("in saveCustomerEntryDetails");
	     if(validateApplicantDynavalidatorForm(document.getElementById("applicantForm")))
	     {
	     	document.getElementById("applicantForm").action="<%=request.getContextPath() %>/creditProcessing.do";
	 	    document.getElementById("applicantForm").submit();
	 	    return true;
	     }
	     return false;
    }
    
    function deleteApplicant()
   {
	   
		
	    if(check())
	    {
			document.getElementById("applicantForm").action="<%=request.getContextPath() %>/deleteCustomerEntryAction.do";
		 	document.getElementById("applicantForm").submit();
	    }
	    else
	    {
	    	alert("Please Select atleast one!!!");
	    }
}
function allChecked()
{
	
	var c = document.getElementById("allchk").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	
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
	
	
	function customerClear()
	{
		
		//alert("clear");
		
		document.getElementById("applicantType").value='';
		document.getElementById("existingCustomer").value='';
		document.getElementById("applicantCategory").value='';
		document.getElementById("customerId").value='';
		document.getElementById("customerName").value='';
	}


	
	function removeComma(id)
	{
	    var str = id;
	    //alert(id);
	    var arr = str.split(',');
	    var stri = '';
	    for(i=0; i<arr.length; i++){
	        stri = stri+arr[i];
	    }   
	    var amount = parseFloat(stri);
	    //alert(amount);
		return amount;
	}

    function saveSecurityDeposit()
    {
    	DisButClass.prototype.DisButMethod();
    	//alert("in saveSecurityDepositDetails"+calculateInterest());
    	var tenure= 0;
    	var internalTenure= 0;
    	if(document.getElementById("internalTenure").value!='')
    	{
    		internalTenure=removeComma(document.getElementById("internalTenure").value);
    	}
    	if(document.getElementById("tenure").value!='')
    	{
    		tenure=removeComma(document.getElementById("tenure").value);
    	}
    	var returnValue=calculateInterest();
    	var contextPath =document.getElementById("contextPath").value;
     	var relatedInterest=0;
     	if(document.getElementById("relatedInterest").value!='')
     	{
     		relatedInterest=removeComma(document.getElementById("relatedInterest").value);
     	}
	     if(checkValidateSecDep())
	     {
	       
	    	if(relatedInterest==returnValue)
	    	{
	    		if(document.getElementById("interestType3").checked==true && document.getElementById("compoundFrequency").value=='' )
	    		{
	    			alert("Please Select Compound Frequency");
	    			DisButClass.prototype.EnbButMethod();
	    			return false;
	    		}
	    		else
	    		{
	    			if(tenure<=internalTenure && tenure>0)
	    			{
		    			document.getElementById("securityForm").action=contextPath+"/securityDeposit.do";
		    			document.getElementById("processingImage").style.display = '';
				 	    document.getElementById("securityForm").submit();
				 	    return true;
	    			}
	    			else
	    			{
	    				alert("Tenure must be less than "+internalTenure+" and greater than 0");
	    				result=0;
	    				document.getElementById("tenure").value='';
	    			}
	    		}
	    		
	    	}
	    	else
	    	{
	    		alert("Please Refresh the Interest Payment Type");
	    		DisButClass.prototype.EnbButMethod();
	    		return false;
	    	}
	     
	     }
	     else
	     {
	    	 DisButClass.prototype.EnbButMethod();
	    	 return false;
	     }
    
    }
    

    function deleteSecurityDeposit()
    {
    	var contextPath =document.getElementById("contextPath").value;
   // alert("ok"); 
	
    if(check())
    {
		document.getElementById("securityForm").action=contextPath+"/securityBehindAction.do?method=deleteSecurity";
	 	document.getElementById("securityForm").submit();
    }
    else
    {
    	alert("Please Select atleast one!!!");
    }
}

    
    function checkValidateSecDep(){
    	
    	var securityAmount = document.getElementById("securityAmount");
    	var interestRate = document.getElementById("interestRate");
    	var tenure = document.getElementById("tenure");
    	var relatedInterest = document.getElementById("relatedInterest");
    	var sdAdjust = document.getElementById("sdAdjust");
    	
    	 if(trim(securityAmount.value) == ''||trim(interestRate.value) == ''||trim(tenure.value) == ''||trim(relatedInterest.value) == ''||trim(sdAdjust.value) == ''){
    		 var msg= '';
    		 
    		 	if(trim(securityAmount.value) == '')
    	 			msg += '* Security Deposit Amount is required.\n';
    	 		if(trim(interestRate.value) == '')
    	 			msg += '* Interest Rate is required.\n';
    	 		if(trim(tenure.value) == '')
    	 			msg += '* Tenure is required.\n';
    	 		if(trim(relatedInterest.value) == '')
    	 			msg += '* Total Interest is required.\n';
    	 		if(trim(sdAdjust.value) == '')
    	 			msg += '* SD Adjusted is required.\n';

    	 		 		
    	 		if(msg.match("Deposit")){
    	 			securityAmount.focus();
    	 		}else if(msg.match("Rate")){
    	 			interestRate.focus();
    	 		}else if(msg.match("Tenure")){
    	 			tenure.focus();
    	 		}else if(msg.match("Total")){
    	 			relatedInterest.focus();
    	 		}else if(msg.match("Adjusted")){
    	 			sdAdjust.focus();
    	 		}
     		
     		alert(msg);
     		document.getElementById("save").removeAttribute("disabled","true");
     		return false;
    	 
    	 }else
    		 return true;
    }
