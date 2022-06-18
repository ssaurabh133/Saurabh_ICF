	function setText(text1,text2,text3) {
	        // alert("ok"+text3);
	document.getElementById('customerId').value = text1;
	document.getElementById('customerName').value = text2;
	var ch = document.getElementsByName("applicantCategory");
		
	  
	    for(i=0;i<ch.length;i++)
		{
			// alert("lengeth "+ch.length);
	if(ch[i].value==text3)
	{
	 
	  ch[i].checked=true;
	  document.getElementById("cat").value=ch[i].value;
	 // document.getElementById('applicantCategory').setAttribute("disabled",
	// "true");
			}
			 ch[i].disabled=true;
	      }
	  }
		
	function createCustomer()
		{
		DisButClass.prototype.DisButMethod();
		// alert("applicantCategory");
	   if(IsExisting())
	   {
	   		// var applicantCategory =
	// document.getElementById("applicantCategory").value;
	var applicantCategory = document.getElementsByName("applicantCategory");
	// //alert(applicantCategory);
	var applType = document.getElementById("applicantType").value;
	// alert(applType);
	   if(applType!='')
	   {
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
	        alert("Please select Applicant Category.");
	        DisButClass.prototype.EnbButMethod();
	        //DisButClass.prototype.ForDiffDisEnb(("linkCust"));
	        return false;
	   }
	   else
	   {
	        var path= document.getElementById("path").value;
	 if(val=='C')
	 {
	    var url=path+"/corporateEntryForm.do?method=displayCorporateForms&pageStatuss=fromLeads&applType="+applType+"&cType=C";
	 }
	 if(val=='I')
	 {
	 	var url=path+"/corporateEntryForm.do?method=displayCorporateForms&pageStatuss=fromLeads&applType="+applType+"&cType=I";
	 }
	
	 			window.child=window.open(url,'name','height=400,width=1000,top=200,left=250');
	 			DisButClass.prototype.EnbButMethod();
	 			DisButClass.prototype.ForDiffDisEnb(("linkCust"));
	            return true;
	       }  	    
	 		   
	   }
	   else
	   {
	   	  alert("Please Select Applicant Type");
	   	  DisButClass.prototype.EnbButMethod();
	   }
	  }
	   DisButClass.prototype.EnbButMethod();
	}
		
	function linkCustomer()
	  {
		DisButClass.prototype.DisButMethod();
		var path= document.getElementById("path").value;
	 if (document.getElementById("applicantType").value <= 0)
	{
	   alert("Please select Applicant Type.");
	   document.getElementById("applicantType").focus();
	   DisButClass.prototype.EnbButMethod();
	       return (false);
	    }
	    else if(IsExisting())
	  
	    {
	  // alert("ok");
	
	var applType = document.getElementById("applicantType").value;
	// alert(applType);
	
	
	
	var url=path+"/link.do?applType="+applType;
	
	window.child=window.open(url,'name','height=400,width=1000,top=200,left=250,scrollbars=yes');
	// window.child.moveTo(800,300);
	 // if (window.focus) {
	 // window.child.focus();
	 // return false;
	 // }
	DisButClass.prototype.EnbButMethod();
	DisButClass.prototype.ForDiffDisEnb(("createCust"));
	        return true;
	        
			}
			
		}
	
	function hideLink()
	{
	 if (document.getElementById("applicantType").value <= 0)
	  {

			DisButClass.prototype.DisButMethod();
	        alert("Please select Applicant Type.");
			document.getElementById("applicantType").focus();
			DisButClass.prototype.EnbButMethod();
			return (false);
	  }
	else
	{
	
	document.getElementById("linkCust").setAttribute("disabled", "true");
	document.getElementById("createCust").removeAttribute("disabled", "true");
	DisButClass.prototype.EnbButMethod();
	DisButClass.prototype.ForDiffDisEnb("linkCust");
	 
	var ch = document.getElementsByName("applicantCategory");
	
	for(i=0;i<ch.length;i++)
	{
		if(document.getElementById("applicantType").value=='PRAPPL')
	{
		document.getElementById("applicantCategory1").disabled=false;
	if(document.getElementById("optionIndv").value=='Y')
	{
		document.getElementById("applicantCategory").disabled=false;
				}
				
			}
			else
			{
			 	ch[i].disabled=false;
			}
	    }
		return true;
		}
	}
	
	function hideCreate()
	{
	   
   // alert("yes");
	document.getElementById("applicantCategory").setAttribute("disabled", "true");
	document.getElementById("applicantCategory1").setAttribute("disabled", "true");
	document.getElementById("linkCust").removeAttribute("disabled", "true");
	document.getElementById("createCust").setAttribute("disabled", "true");
	DisButClass.prototype.EnbButMethod();
	DisButClass.prototype.ForDiffDisEnb("createCust");
	
	return true;
	}
	
	// To insert new row
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
	// alert("after first new cell");
	newCell.innerHTML = '<a href="#" onclick="corporate();">1234</a>'
	var newCell = newRow.insertCell(2);
	// alert("after second new cell");
	
	newCell.innerHTML =	'<label>Sachin Garg</label>'
	var newCell = newRow.insertCell(3);
	// alert("after second new cell");
	newCell.innerHTML = '<label>Primary Applicant</label>'
	var newCell = newRow.insertCell(4);
	// alert("after second new cell");
	newCell.innerHTML = '<label>Corporate</label>'
	var newCell = newRow.insertCell(5);
	// alert("after second new cell");
	newCell.innerHTML = '<label>Yes</label>'
			
			
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// To remove a row
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
	 
	 // To check the checkbox, whether it is check or not
	function chooseProductCheckboxCheck()
	{
		var ch = document.getElementsByName("existingCustomer");
	var zx=0;
	var flag=0;
	for(i=0;i<ch.length;i++)
	{
		// alert("lengeth "+ch.length);
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
	// alert("value of flag "+ flag);
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
				alert("Please select Existing or Non-Existing Customer.");
				return false;
		}
	}
	
	function isNumberKey(evt)
	{
	     // alert(event.keyCode);
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
		 	var path= document.getElementById("path").value;
	var url=path+"/JSP/gcdJSP/firstFrame.jsp";
	alert(url);
	window.open(url,'name','height=400,width=1000,top=200,left=250');
	        return true;
		}
	
	function saveCustomerEntry()
	{
	    // alert("in saveCustomerEntryDetails");
	 if(validateApplicantDynavalidatorForm(document.getElementById("applicantForm")))
	 {
	 	document.getElementById("applicantForm").action="<%=request.getContextPath() %>/creditProcessing.do";
	 	document.getElementById("applicantForm").submit();
		 	    return true;
		     }
		     return false;
	    }
	    
	function deleteApplicant(confirmStatus)
	   {
		var contextPath =document.getElementById('path').value ;
		var cc = document.getElementById("createCust").getAttribute("disabled");
		var lc = document.getElementById("linkCust").getAttribute("disabled");
	 //	var ch=document.getElementsByName('chk');
	 //	var applType=document.getElementsByName('applType');
		//var roleId="";
		//var roleType="";
		DisButClass.prototype.DisButMethod();
		  // alert("In deleteApplicant");
	if(checkApplicant(confirmStatus))
	{
	  if(confirm("Are you sure to delete the record.")) 
	  {
		 
		  document.getElementById("applicantForm").action=contextPath+"/approveCustomerAction.do?method=deleteroleList&confirmStatus="+confirmStatus;
		  document.getElementById("processingImage").style.display = '';
		  document.getElementById("applicantForm").submit();
	 	  return true;
	 	  
	 }else{
		 if(cc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("createCust").setAttribute("disabled",true);
				$(document.getElementById("createCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else if(lc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("linkCust").setAttribute("disabled",true);
				$(document.getElementById("linkCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else {
				DisButClass.prototype.EnbButMethod();
			}
		 return false;
	 }
	}
	else{
			alert("Please Select atleast one!!!");

			if(cc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("createCust").setAttribute("disabled",true);
				$(document.getElementById("createCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else if(lc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("linkCust").setAttribute("disabled",true);
				$(document.getElementById("linkCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else {
				DisButClass.prototype.EnbButMethod();
			}
			return false;
		}
	}
	
	function checkApplicant(confirmStatus)
	{
	    // alert("ok");
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
	
	
	function customerClear()
	{
		
		// alert("clear");
		
		document.getElementById("applicantType").value='';
		document.getElementById("existingCustomer").value='';
		document.getElementById("applicantCategory").value='';
		
	}
	
	function forward() 
	{
		
		var type=document.getElementById("applicantType").value;
		var optionIndv=document.getElementById("optionIndv").value;
		
		// alert(optionIndv);
		if(type=='PRAPPL')
		{
			   if(optionIndv=='Y')
			   {
			   		if (document.getElementById("applicantType").selectedIndex <= 0)
			      {
			          alert("Please select Applicant Type.");
			         document.getElementById("applicantType").focus();
			         return (false);
			      }
			        document.getElementById("existingCustomer2").checked=true;
			        document.getElementById("applicantCategory").checked=true;
				    document.getElementById("applicantCategory1").checked=false;
				    document.getElementById("applicantCategory").removeAttribute("disabled", "true");
				    document.getElementById("applicantCategory1").removeAttribute("disabled", "true");
					document.getElementById("linkCust").setAttribute("disabled", "true");
					document.getElementById("createCust").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					DisButClass.prototype.ForDiffDisEnb("linkCust");
			   }
			   else
			   {
			   		if (document.getElementById("applicantType").selectedIndex <= 0)
			      {
			          alert("Please select Applicant Type.");
			         document.getElementById("applicantType").focus();
			         return (false);
			      }
			        document.getElementById("existingCustomer2").checked=true;
			        document.getElementById("applicantCategory").checked=false;
				    document.getElementById("applicantCategory1").checked=true;
				    document.getElementById("applicantCategory").setAttribute("disabled", "true");
				    document.getElementById("applicantCategory1").removeAttribute("disabled", "true");
					document.getElementById("linkCust").setAttribute("disabled", "true");
					document.getElementById("createCust").removeAttribute("disabled", "true");
					DisButClass.prototype.EnbButMethod();
					DisButClass.prototype.ForDiffDisEnb("linkCust");
			   }
			
			
				
			}
			else
			{
				if (document.getElementById("applicantType").selectedIndex <= 0)
		      {
		          alert("Please select Applicant Type.");
		         document.getElementById("applicantType").focus();
		         return (false);
		      }
		        document.getElementById("existingCustomer1").checked=true;
		        document.getElementById("applicantCategory").checked=false;
			    document.getElementById("applicantCategory1").checked=false;
			    document.getElementById("applicantCategory").setAttribute("disabled", "true");
			    document.getElementById("applicantCategory1").setAttribute("disabled", "true");
				document.getElementById("linkCust").removeAttribute("disabled", "true");
				document.getElementById("createCust").setAttribute("disabled", "true");
				DisButClass.prototype.EnbButMethod();
				DisButClass.prototype.ForDiffDisEnb("createCust");
			}
		
	}
function linkDeal(id,cType,applType,existingCustomer)
{
	//alert("linkDeal"+existingCustomer);
	var appType="";
	if(applType=="APPLICANT")
		appType="PRAPPL";
	else if(applType=="CO APPLICANT")
		appType="COAPPL";
	else if(applType=="GUARANTOR")
		appType="GUARANTOR";
		
	var applType = document.getElementById(id).value;
	otherWindows = new Array();
	curWin = 0;
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
	 // alert("updateFlag...111111111111.........."+uFlag);

	   if(cType=='I')
	    {
	    // alert(cType);
		curWin = 0;
		
		var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&existingCustomer="+existingCustomer;
		myWindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
		otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250');
		myWindow.moveTo(800,300);
       if (window.focus) {
            myWindow.focus();
            return false;
         }
	    return true;
	    }
	   else if(cType=='C')
	    {
	   	// alert(cType);
		curWin = 0;
		var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&existingCustomer="+existingCustomer;
		myWindow =window.open(url,'name','height=420,width=1130,top=200,left=250').focus();
		otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250');
		myWindow.moveTo(800,300);
       if (window.focus) {
            myWindow.focus();
            return false;
         }
		return true;
        }
       }  	

	function linkDealinCM(id,cType)
        {
        aotherWindows = new Array();
	    curWinAnother = 0;
          // alert("ok---"+cType+"---id"+id);
       
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
	 // alert("updateFlag...111111111111.........."+uFlag);

	   if(cType=='I')
	    {
	    // alert(cType);
		
		var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+applType;
		window.child =window.open(url,'nameCM','height=400,width=1000,top=200,left=250').focus();
		aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

	    return true;
	    }
	   else if(cType=='C')
	    {
	    // alert(cType);
		var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&status=CE";
		window.child =window.open(url,'nameCM','height=420,width=1130,top=200,left=250').focus();
		aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

		return true;
        }
       }  	
	//Rohit Changes for sarva Surksha starts
	function linkDealinCMnew(id,cType,applType,existingCustomer)
    {
   //alert("linkDealinCMnew"+existingCustomer);
    aotherWindows = new Array();
    curWinAnother = 0;
      // alert("ok---"+cType+"---id"+id);
  //  var applType=document.getElementById('applType').value;
  //  alert("applType::::"+applType);
	var appType="";
	if(applType=="APPLICANT")
		appType="PRAPPL";
	else if(applType=="CO APPLICANT")
		appType="COAPPL";
	else if(applType=="GUARANTOR")
		appType="GUARANTOR";
	
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
 // alert("updateFlag...111111111111.........."+uFlag);

   if(cType=='I')
    {
    // alert(cType);
	
	var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&existingCustomer="+existingCustomer;
	window.child =window.open(url,'nameCM','height=400,width=1000,top=200,left=250').focus();
	aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

    return true;
    }
   else if(cType=='C')
    {
    // alert(cType);
	var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&status=CE&existingCustomer="+existingCustomer;
	window.child =window.open(url,'nameCM','height=420,width=1130,top=200,left=250').focus();
	aotherWindows[curWinAnother++]= window.open(url,'nameCM','height=400,width=1000,top=200,left=250');

	return true;
    }
   }  
	//Rohit Changes for sarva Surksha end.....
	function deleteCustomerLoan(confirmStatus)
	   {
		var contextPath =document.getElementById('path').value ;
		var cc = document.getElementById("createCust").getAttribute("disabled");
		var lc = document.getElementById("linkCust").getAttribute("disabled");
	 	var ch=document.getElementsByName('chk');
	 	var applType=document.getElementsByName('applType');
		//var roleId="";
		//var roleType="";
		DisButClass.prototype.DisButMethod();
		  // alert("In deleteApplicant");
		
		for(i=0;i<ch.length;i++)
		{
			//alert("ch: "+ch[i].checked);
			if(ch[i].checked==true)
			{
				var roleType=applType[i].value;
				if(roleType=='APPLICANT')
				{
					alert("You can not delete APPLICANT");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			}
			
		}
		
	if(checkApplicant(confirmStatus))
	{
	  if(confirm("Are you sure to delete the record.")) 
	  {
		 
		  document.getElementById("applicantForm").action=contextPath+"/approveCustomerAction.do?method=deleteCustomerLoan&confirmStatus="+confirmStatus;
		  document.getElementById("processingImage").style.display = '';
		  document.getElementById("applicantForm").submit();
	 	  return true;
	 	  
	 }else{
		 if(cc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("createCust").setAttribute("disabled",true);
				$(document.getElementById("createCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else if(lc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("linkCust").setAttribute("disabled",true);
				$(document.getElementById("linkCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else {
				DisButClass.prototype.EnbButMethod();
			}
		 return false;
	 }
	}
	else{
			alert("Please Select atleast one!!!");

			if(cc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("createCust").setAttribute("disabled",true);
				$(document.getElementById("createCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else if(lc){
				DisButClass.prototype.EnbButMethod();
				document.getElementById("linkCust").setAttribute("disabled",true);
				$(document.getElementById("linkCust")).css({"opacity":"0.4","filter":"alpha(opacity=40)"});
			}else {
				DisButClass.prototype.EnbButMethod();
			}
			return false;
		}
	}
	
	function linkGuaranteeAmountAtCM(id,amt)
	{
		
			//alert("id"+id);
			//var url="updateGuaranteeAmount.do?method=updateGuaranteeAmount&operation=update&hideId="+id;
			var url="guaranteeAmountBehindAction.do?method=guaranteeAmountOpenCM&hideId="+id+"&amount="+amt;
			mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
			mywindow.moveTo(800,300);
			if (window.focus) {
				mywindow.focus();
				return false;
			}
			return true;
	} 
	
	function saveGuaranteeAmountAtCM()
	{

		var contextPath = document.getElementById('contextPath').value;
		var id=document.getElementById("id").value;
		if(validateGuaranteeAmountDynaValidatorForm(document.getElementById("guaranteeAmountForm")))
		{
			document.getElementById("guaranteeAmountForm").action = contextPath+"/updateGuaranteeAmount.do?method=saveGuaranteeAmountAtCM&id="+id;
			document.getElementById("guaranteeAmountForm").submit();
			return true;
			
		}
		return false;
	}
	
	function updateCustomerAtCM(id,cType,applType,existingCustomer)
	{
		var appType="";
		if(applType=="APPLICANT")
			appType="PRAPPL";
		else if(applType=="CO APPLICANT")
			appType="COAPPL";
		else if(applType=="GUARANTOR")
			appType="GUARANTOR";
			
		var applType = document.getElementById(id).value;
		otherWindows = new Array();
		curWin = 0;
	    if(cType!=''&& cType=='CORPORATE')
	       {
	       		cType='C';
	       } 
	       else
	       {
	       	cType='I';
	       }     
	       var uFlag="";
		    // uFlag = document.getElementById("updateFlag").value;
		 // alert("updateFlag...111111111111.........."+uFlag);

		   if(cType=='I')
		    {
		    // alert(cType);
			curWin = 0;
			
			var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&existingCustomer="+existingCustomer;
			myWindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
			otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250');
			myWindow.moveTo(800,300);
	       if (window.focus) {
	            myWindow.focus();
	            return false;
	         }
		    return true;
		    }
		   else if(cType=='C')
		    {
		   	// alert(cType);
			curWin = 0;
			var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag="+uFlag+"&applType="+appType+"&existingCustomer="+existingCustomer;
			myWindow =window.open(url,'name','height=420,width=1130,top=200,left=250').focus();
			otherWindows[curWin++]= window.open(url,'name','height=400,width=1000,top=200,left=250');
			myWindow.moveTo(800,300);
	       if (window.focus) {
	            myWindow.focus();
	            return false;
	         }
			return true;
	        }
	       }  
	
	
	function createCustomerAtCM()
	{
	DisButClass.prototype.DisButMethod();
	// alert("applicantCategory");
   if(IsExisting())
   {
   		// var applicantCategory =
// document.getElementById("applicantCategory").value;
var applicantCategory = document.getElementsByName("applicantCategory");
// //alert(applicantCategory);
var applType = document.getElementById("applicantType").value;
// alert(applType);
   if(applType!='')
   {
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
        alert("Please select Applicant Category.");
        DisButClass.prototype.EnbButMethod();
        //DisButClass.prototype.ForDiffDisEnb(("linkCust"));
        return false;
   }
   else
   {
        var path= document.getElementById("path").value;
 if(val=='C')
 {
    var url=path+"/corporateEntryForm.do?method=displayCorporateForms&pageStatuss=''&applType="+applType+"&cType=C";
 }
 if(val=='I')
 {
 	var url=path+"/corporateEntryForm.do?method=displayCorporateForms&pageStatuss=''&applType="+applType+"&cType=I";
 }

 			window.child=window.open(url,'name','height=400,width=1000,top=200,left=250');
 			DisButClass.prototype.EnbButMethod();
 			DisButClass.prototype.ForDiffDisEnb(("linkCust"));
            return true;
       }  	    
 		   
   }
   else
   {
   	  alert("Please Select Applicant Type");
   	  DisButClass.prototype.EnbButMethod();
   }
  }
   DisButClass.prototype.EnbButMethod();
}
	
	
	function linkPhotoUpload(id,mode)
	{
		
			var url="corporateEntryForm.do?method=displayPhotoUploadScreen&customerId="+id+"&mode="+mode;
			mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
			mywindow.moveTo(800,300);
			if (window.focus) {
				mywindow.focus();
				return false;
			}
			return true;
	}
	
	
 function submitPicUpload(){
	 DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("contextPath").value;
		var customerId=document.getElementById("customerId").value;
		if(document.getElementById('docFile').value=="")
		{
			alert("Choose file to be uploaded.");
			document.getElementById("docFile").focus(); 
 	    	DisButClass.prototype.EnbButMethod();
		    return false; 
		}
		else if(document.getElementById('docFile').value!=""){
			var fup = document.getElementById('docFile');
			var fileName = fup.value;
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			ext=ext.toLowerCase();
			if(ext == "JPEG" || ext == "jpeg" || ext == "PNG" || ext == "png" || ext == "JPG" || ext == "jpg" )
			{
				document.getElementById("photoUploadForm").action=sourcepath+"/customerDocUpload.do?method=customerPicUpload&customerId="+customerId;
				document.getElementById("photoUploadForm").submit();
				return true;
			} 
			else
			{
				alert("Upload JPG or JPEG or PNG File only");
	 	    	DisButClass.prototype.EnbButMethod();
				return false;
			}
		}


 }	
 
 
 function enableDisableField()
 {
	var ch=document.getElementsByName('chk');
	if(ch.length<1)
		{
	 if(document.getElementById("photoUploadType").checked==true)
		 {
			document.getElementById("photo").style.display="none";

					var customerId=document.getElementById("customerId").value;
					var url="customerDocUpload.do?method=displayPhotoUploadScreenWebScreen&customerId="+customerId;
					mywindow =window.open(url,'name','height=400,width=1000,top=200,left=250').focus();
					mywindow.moveTo(800,300);
					if (window.focus) {
						mywindow.focus();
						return false;
					}
					return true;
			
		 }
	 if(document.getElementById("photoUploadType1").checked==true)
	 {
			document.getElementById("photo").style.display="";
	 }
		}
	else{
		alert("Please first delete the existing document.");
		return false;
	}
	 
 }
 
 
 function customerDocDownloadFile(fileName,customerId)
 {
 	//alert("download File: "+removeSplChar(fileName));
 	var sourcepath=document.getElementById("contextPath").value;
 	document.getElementById("photoUploadForm").action=sourcepath+"/customerDocUpload.do?method=downloadUploadedFileForCustomer&docName="+fileName+"&customerId="+customerId;
 	document.getElementById("photoUploadForm").submit();
 	return true;
 }
 
 
 function deleteUploadDocForCustomer(customerId,fileName)
 {
 	//alert("Delete row");
 	DisButClass.prototype.DisButMethod();
 	    if(check())
 	    {
 	    	var sourcepath=document.getElementById("contextPath").value;
 			document.getElementById("photoUploadForm").action=sourcepath+"/customerDocUpload.do?method=deleteUploadCustomerPicData&docName="+fileName+"&customerId="+customerId;
 			document.getElementById("photoUploadForm").submit();
 	    }
 	    else
 	    {
 	    	alert("Please Select atleast one!!!");
 	    	DisButClass.prototype.EnbButMethod();
 	    }
 }
