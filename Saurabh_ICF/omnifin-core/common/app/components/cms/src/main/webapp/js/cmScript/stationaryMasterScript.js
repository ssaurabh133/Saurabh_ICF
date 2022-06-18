
function addRow(val)
	{
	  var ptable = document.getElementById('gridtable');
	  var lastElement = ptable.rows.length;
      var pcheck = document.getElementById("pcheck").value; 
	  var psize= document.getElementById("psize").value;	
	  
	if(psize==""){
		psize=lastElement;
	}
	
     
	 var row =ptable.insertRow(lastElement);
	 row.setAttribute('className','white1' );
     row.setAttribute('class','white1' );
		
      var cellCheck = row.insertCell(0);
		var element = document.createElement("input");
		element.type = "checkbox";
		element.name = "chkd";
		element.id = "allchkd"+psize;
		element.value=psize;
		cellCheck.appendChild(element);
	  
	    var bookNo = row.insertCell(1);
			var element1 = document.createElement("input");		
			element1.type = "text";
			element1.setAttribute('className','text');
			element1.setAttribute('class','text');
			element1.name = "bookNo";
			element1.id = "bookNo"+psize;
			element1.setAttribute('maxLength','13');
			bookNo.appendChild(element1);
			
		var instruNo = row.insertCell(2);
			var element2 = document.createElement("input");		
			element2.type = "text";
			element2.setAttribute('className','text3');
			element2.setAttribute('class','text3');
			element2.name = "instruNo";
			element2.id = "instruNo"+psize;
			element2.setAttribute('maxLength','10');
			element2.setAttribute('onkeypress','return isNumberKey(event);');
			instruNo.appendChild(element2);
			
		var instruFrom = row.insertCell(3);
		var element3 = document.createElement("input");		
			element3.type = "text";
			element3.setAttribute('className','text3');
			element3.setAttribute('class','text3');
			element3.name = "instruFrom";
			element3.id = "instruFrom"+psize;
			element3.setAttribute('maxLength','10');
			element3.setAttribute('onkeypress','return checkInstruNo(this.id);');
			element3.setAttribute('onkeypress','return isNumberKey(event);');
		    element3.setAttribute('onchange','autoCalc(this.id)');
			instruFrom.appendChild(element3);
			
		var instruTo = row.insertCell(4);
			var element4 = document.createElement("input");		
			element4.type = "text";
			element4.setAttribute('className','text3');
			element4.setAttribute('class','text3');
			element4.name = "instruTo";
			element4.id = "instruTo"+psize;
			element4.setAttribute('maxLength','10');
			element4.setAttribute('readonly','readonly');
			instruTo.appendChild(element4);
			
	  pcheck++;
	  psize++;	  
	  document.getElementById("psize").value=psize;
	  document.getElementById("pcheck").value=pcheck;
		 }
	
function removeRow(val) {
      var table = document.getElementById("gridtable");
         var rowCount = table.rows.length;
        
		var count=0;
         for(var i=1; i<rowCount; i++) {

             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
               
                 count++;
                 i--;
              }        
            }        
      if(count==0){
           alert(val);
         return false;
         }
      }


function newAction1()
{
			 var contextPath=document.getElementById('contextPath').value;
			 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryAdditionMasterDispatchActionAtCM.do?method=stationarySaveMethod&page=new";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById('StationaryMasterForm').submit();
       }

function showAction()
{
	
	        
		     var contextPath=document.getElementById('contextPath').value;
		     document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryAdditionMasterDispatchActionAtCM.do?method=stationarySearchMethod";
             document.getElementById("processingImage").style.display = '';
             document.getElementById('StationaryMasterForm').submit();
}
function newIssue()
{	 		 
			 var contextPath=document.getElementById('contextPath').value;
			 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryIssuMethod&page=new&page1=";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById('StationaryMasterForm').submit();
       }
 

function searchAction()
		{
		     var contextPath=document.getElementById('contextPath').value;
			 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryIssuMethod&page=&page1=srch";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById('StationaryMasterForm').submit();
		}

  function allChecked(){
 	var chkbox=document.getElementsByName('chkd')
 		if(document.getElementById('allchkd').checked==true){
 			for(var i=0;i<chkbox.length;i++){
 					chkbox[i].checked=true;
 				}
 		}else{
 			 for(var i=0;i<chkbox.length;i++){
					 chkbox[i].checked=false;
 				}
 		}
  }
  
function changeAdvance(){
	
	    if(document.getElementById('typeR').checked==true)
	{
		   document.getElementById('bankShow').style.display='none';
		   document.getElementById('bankShow1').style.display='none';
	}else{
		   document.getElementById('bankShow').style.display='';
		   document.getElementById('bankShow1').style.display='';
	}
} 
function saveDataDtl(){ 
	
	var bankName="";
	   var bookNoU = document.getElementById("bookNoU").value;
	   if(bookNoU=="U")
		   var additionDate = document.getElementById("additionDate12").value;
	   else
		   var additionDate = document.getElementById("additionDate").value;
	  	if(document.getElementById("bankList")!=null && document.getElementById("bankList")!='undefined')
	  	{
	  		 bankName = document.getElementById("bankList").value;
	  	}
	   
	    
	    var checkType = document.getElementById('typeC').checked;
	 
	   	var a="";
	 
	   	if(checkType)
		 {
		 	if(bankName=="" || additionDate=="")
		 		{
		 			if(additionDate==""){
						a+=" * Addition Date is required \n";
					}if(bankName==""){
						a+=" * Bank Name is required \n";
					}
					
					alert(a);
		 			return false;
		 		}
		 	 }
	 	else
		 {
		 	if(additionDate=="")
		 		{
		 			if(additionDate==""){
						a+=" * Addition Date is required \n";
					}
					alert(a);
		 			return false;
		 		}
		 	 		
		 }  
	    var bookNo = document.getElementsByName('bookNo');
	    var instruNo=document.getElementsByName('instruNo');
	    var instruFrom=document.getElementsByName('instruFrom');
	    var instruTo=document.getElementsByName('instruTo');
	    var psize=document.getElementById('psize').value;
	   // alert(psize);
	   	for(var i=1;i<psize;i++)
	   	{
	   		
	  		if(document.getElementById('bookNo'+i)!=undefined && document.getElementById('bookNo'+i)!=null ){
	 			
	 			if(document.getElementById('bookNo'+i).value=="")
	 			  {
		 			alert("Either Book No must be fill or  delete remaining rows ");
		 			return false;
		 		   }
				/* if(!validateBookNo(document.getElementById('bookNo'+i).value))
				 {
					document.getElementById('bookNo'+i).value='';
					return false;
				 }*/
	 			var j=i+1;
		   		for(var k=1;k<=j;k++)
		   		{
	 			   if(document.getElementById('bookNo'+j)!=undefined && document.getElementById('bookNo'+j)!=null ){
	 				
	 				if(document.getElementById('bookNo'+j).value=="")
		 			  {
			 			alert("Either Book No must be fill or  delete remaining rows ");
			 			return false;
			 		   }
	 				//alert(document.getElementById('bookNo'+j).value+" \n "+document.getElementById('bookNo'+k).value);
	 				if(j!=k)
	 				{
		 				 if(document.getElementById('bookNo'+j).value==document.getElementById('bookNo'+k).value)
			 			  {
				 			alert("Book No must be Unique");
				 			return false;
				 		  }
	 				}
	 				
	 			}
	 			   
	 		  }
	   		}
              if(document.getElementById('instruNo'+i)!=undefined && document.getElementById('instruNo'+i)!=null ){
	 			
	 			if(document.getElementById('instruNo'+i).value=="")
	 			  {
		 			alert("No of Instrument/Receipt is required");
		 			return false;
		 		   }
              }
              
              
              if(document.getElementById('instruFrom'+i)!=undefined && document.getElementById('instruFrom'+i)!=null ){
  	 			
  	 			if(document.getElementById('instruFrom'+i).value=="")
  	 			  {
  		 			alert("Instrument From/Receipt From is required");
  		 			return false;
  		 		   }
                }
              
	  		if((document.getElementById("instruFrom"+i)!=undefined && document.getElementById("instruFrom"+i)!=null) && (document.getElementById("instruTo"+i)!=undefined && document.getElementById("instruTo"+i)!=null))
	  		{
	            if(parseInt(document.getElementById("instruFrom"+i).value) > parseInt(document.getElementById("instruTo"+i).value))
	      		{	    	
	      			alert("Instrument From/Receipt From should be less than or equal to Instrument To/Receipt To");
	      			document.getElementById("save").removeAttribute("disabled","true");
	      			DisButClass.prototype.EnbButMethod();
	      			return false;
	      		}
	  		}
            
            if(i<psize){	
    			var k=i+1;
    			
    			if((document.getElementById("instruTo"+i)!=undefined && document.getElementById("instruFrom"+k)!=null) && (document.getElementById("instruFrom"+k)!=undefined && document.getElementById("instruTo"+i)!=null))
    	  		{
			    	if(parseInt(document.getElementById("instruTo"+i).value) > parseInt(document.getElementById("instruFrom"+k).value))
			    		{
			    			
			    			alert("Next Instrument From/Receipt should be greater than previous to Instrument To/Receipt To");
			    			document.getElementById("save").removeAttribute("disabled","true");
			    			DisButClass.prototype.EnbButMethod();
			    			return false;
			    		}
    	  		
				    	if(parseInt(document.getElementById("instruTo"+i).value)+1 != parseInt(document.getElementById("instruFrom"+k).value))
				    	{
				    		
				    		alert("Next Instrument From/Receipt should be equal to previous to Instrument To/Receipt To");
				    		document.getElementById("save").removeAttribute("disabled","true");
				    		DisButClass.prototype.EnbButMethod();
				    		return false;
				    	}
    	  	  }

    		}
              
	 	}
		  var contextPath=document.getElementById('contextPath').value;
		  document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryAdditionMasterDispatchActionAtCM.do?method=stationarySaveMethod&page=";
		  document.getElementById('StationaryMasterForm').submit();
		
}

function saveIssue(){ 
	
		var desc = document.getElementById("desc").value;
	    var bookIssue = document.getElementById("bookIssue").value;
	    var branchid = document.getElementById("branchid").value;
	    var allBranch=document.getElementById('allBranch').checked;
	    var UserIssue = document.getElementById('UserIssue').value;
	    
	    var additionDate = document.getElementById('additionDate').value;
	    var hoIssueDate=document.getElementById('hoIssueDate').value;
	    var formatD=document.getElementById("formatD").value;
	    
	    var dtadditionDate=getDateObject(additionDate,formatD.substring(2, 3));
	    var dthoIssueDate=getDateObject(hoIssueDate,formatD.substring(2, 3));
	   
	    var a="";
	        if(allBranch)
	        {
	        	if(desc=="" || bookIssue=="" || additionDate=="" || UserIssue=="")
		 		{
		 			if(desc==""){
						a+=" * Stationary Type is required \n";
					}if(bookIssue==""){
						a+=" * Book No is required \n";
					}
					if(additionDate==""){
						a+=" * Issuing Date is required \n";
					}
					if(UserIssue==""){
						a+=" * Issuing User is required \n";
					}
					alert(a);
		 			return false;
		 		}
	        }
	        else
	        {
	        	
	   	 		if(desc=="" || bookIssue=="" || branchid=="" || additionDate=="" || UserIssue=="")
		 		{
		 			if(desc==""){
						a+=" * Stationary Type is required \n";
					}if(bookIssue==""){
						a+=" * Book No is required \n";
					}
					if(branchid==""){
						a+=" * Issue To Branch is required \n";
					}
					if(additionDate==""){
						a+=" * Issuing Date is required \n";
					}
					if(UserIssue==""){
						a+=" * Issuing User is required \n";
					}
					alert(a);
		 			return false;
		 		}
	        }
	
	        if(dtadditionDate<dthoIssueDate)
			{
				alert("Issuing Date cannot be less than HO Issuing Date ");
				document.getElementById("additionDate").value='';
				document.getElementById("additionDate").focus();
				return false;
			}
		
		  var contextPath=document.getElementById('contextPath').value;
		  document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryIssuMethod&page=&page1=";
		  document.getElementById('StationaryMasterForm').submit();
		
}

function checkInstruNo(id)
{
		var instruNo="instruNo";
	if(id.length>10)
		instruNo=instruNo+id.substring(10);
		var instruNoVal = document.getElementById(instruNo).value; 
	if(instruNoVal == "")
	{
	   alert(" * Instrument No is required");
	   return false;
   }
   else
  	   return true;
}

function autoCalc(id)
{	
	    var instruNo="instruNo";
		var instruTo="instruTo";
	if(id.length>10)
	{	
		instruNo=instruNo+id.substring(10);
		instruTo=instruTo+id.substring(10);		
	}
		var instruNoVal= document.getElementById(instruNo).value; 
		var instruFrom = document.getElementById(id).value; 
	    var insNo=0;
	    var insFrm=0;
	    total=0;
    if(instruNoVal!='')
		insNo=parseFloat(instruNoVal);
	if(instruFrom!='')
		insFrm=parseFloat(instruFrom);

   		total = (insNo+insFrm)-1
    if(insNo!="" && insFrm!="")
    {
    	document.getElementById(instruTo).value=total;
    	return false;
   	}
    else
     	return true;   
  }

 function removeInstruFrm()
 {
	 document.getElementById("instruFrom").value="";
	 return true;
 }

	function isNumberKey(evt) 
		{
		var charCode = (evt.which) ? evt.which : event.keyCode;

	if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric Allowed Here");
			return false;
		}
			return true;
		}
	
	function selectId()
	{
		document.getElementById("bookIssue").value="";
		document.getElementById("lbxBookNo").value="";
		document.getElementById("stationaryType").value=document.getElementById("desc").value
		return true;
	}
	 
	function newHoAllocation()
	{	 		 
				 var contextPath=document.getElementById('contextPath').value;
				 document.getElementById('StationaryMasterForm').action=contextPath+"/hoAllocationDispatchActionAtCM.do?method=stationaryIssuMethod&page=new&page1=";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById('StationaryMasterForm').submit();
	       }
	 

	function searchActionHoAllocation()
			{
			     var contextPath=document.getElementById('contextPath').value;
				 document.getElementById('StationaryMasterForm').action=contextPath+"/hoAllocationDispatchActionAtCM.do?method=stationaryIssuMethod&page=&page1=srch";
				 document.getElementById("processingImage").style.display = '';
				 document.getElementById('StationaryMasterForm').submit();
			}
	
	function saveHOAllocation(){ 
		
		var desc = document.getElementById("desc").value;
	    var bookIssue = document.getElementById("bookIssue").value;
	    var branchid = document.getElementById("branchid").value;
	    var additionDate = document.getElementById('additionDate').value;
	    var additionStationaryDate=document.getElementById('additionStationaryDate').value;
	    var formatD=document.getElementById("formatD").value;
	    
	    var dtadditionDate=getDateObject(additionDate,formatD.substring(2, 3));
	    var dtadditionStationaryDate=getDateObject(additionStationaryDate,formatD.substring(2, 3));
	   // var allBranch=document.getElementById('allBranch').checked;
	   
	    var msg="";
	        	
	   	 		if(desc=="" || bookIssue=="" || branchid=="" || additionDate=="")
		 		{
		 			if(desc==""){
		 				msg+=" * Stationary Type is required \n";
					}if(bookIssue==""){
						msg+=" * Book No is required \n";
					}
					if(branchid==""){
						msg+=" * Issue To Branch is required \n";
					}
					if(additionDate==""){
						msg+=" * Issuing Date is required \n";
					}
					alert(msg);
		 			return false;
		 		}
	       
	   	 	if(dtadditionDate<dtadditionStationaryDate)
			{
				alert("Issuing Date cannot be less than Addition Date of book ");
				document.getElementById("additionDate").value='';
				document.getElementById("additionDate").focus();
				return false;
			}
		
		  var contextPath=document.getElementById('contextPath').value;
		  document.getElementById('StationaryMasterForm').action=contextPath+"/hoAllocationDispatchActionAtCM.do?method=stationaryIssuMethod&page=&page1=";
		  document.getElementById('StationaryMasterForm').submit();
		
}
	
function saveForwaradCancel(){ 
		
		var receiptNo = document.getElementById("receiptNo").value;
	    var status = document.getElementById("status").value;
	    var remarks = document.getElementById("remarks").value;
	  
	 
	   
	    var msg="";
	        	
	   	 		if(receiptNo=="" || status=="" || remarks=="")
		 		{
		 			if(receiptNo==""){
		 				msg+=" * Receipt No is required \n";
					}if(status==""){
						msg+=" * Status is required \n";
					}
					if(remarks==""){
						msg+=" * Remarks is required \n";
					}
					alert(msg);
		 			return false;
		 		}
	       
	
		
		  var contextPath=document.getElementById('contextPath').value;
		  document.getElementById('StationaryMasterForm').action=contextPath+"/saveForwardCancelActionAtCM.do?method=saveForwardCancel";
		  document.getElementById('StationaryMasterForm').submit();
		
}

function saveForwaradUsed(){ 
	
	var receiptNo = document.getElementById("receiptNo").value;
    var status = document.getElementById("status").value;
    var remarks = document.getElementById("remarks").value;
  
 
   
    var msg="";
        	
   	 		if(receiptNo=="" || status=="" || remarks=="")
	 		{
	 			if(receiptNo==""){
	 				msg+=" * Receipt No is required \n";
				}if(status==""){
					msg+=" * Status is required \n";
				}
				if(remarks==""){
					msg+=" * Remarks is required \n";
				}
				alert(msg);
	 			return false;
	 		}
	
	  var contextPath=document.getElementById('contextPath').value;
	  document.getElementById('StationaryMasterForm').action=contextPath+"/saveForwardUsedActionAtCM.do?method=saveForwardUse";
	  document.getElementById('StationaryMasterForm').submit();
	
}
function saveAcknow()
{
	var formatD=document.getElementById("formatD").value;
	var bookIssue = document.getElementById("bookIssue").value;
    var additionDate = document.getElementById("additionDate").value;
	var dtinvoiceDate=getDateObject(additionDate,formatD.substring(2, 3));
    var issueDate = document.getElementById("issueDate").value;
    var dtBusiness = getDateObject(issueDate,formatD.substring(2, 3));
    var remarks = document.getElementById("remarks").value;     
    var msg="";
        	
   	 		if(additionDate=="" || remarks=="")
	 		{
	 			if(additionDate==""){
					msg+=" * Acknowledgement Date is required \n";
				}
				if(remarks==""){
					msg+=" * Remarks is required \n";
				}
				alert(msg);
	 			return false;
	 		}
   	 	if(dtBusiness>dtinvoiceDate)
		{
			alert("Acknowledgment Date cannot be less than Issue Date");
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		}
	  var contextPath=document.getElementById('contextPath').value;
	  document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=saveAcknow";
	  document.getElementById('StationaryMasterForm').submit();
}

function searchAcknow()
{
	var lbxBookNo = document.getElementById("lbxBookNo").value;
    var desc = document.getElementById("desc").value;  
    
   	 		if(lbxBookNo=="" && desc=="")
	 		{ 					
   	 		alert(" * Please Select Alteast One Criteria To Search ");
	 			return false;
	 		}
		     var contextPath=document.getElementById('contextPath').value;
			 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryAcknoSrch";
			 document.getElementById("processingImage").style.display = '';
			 document.getElementById('StationaryMasterForm').submit();
		}

function saveAcknowHo()
{
	var bookIssue = document.getElementById("bookIssue").value;
    var additionDate = document.getElementById("additionDate").value;
    var remarks = document.getElementById("remarks").value;
    var businessDate = document.getElementById("businessdate").value;
    var formatD=document.getElementById("formatD").value;
    var issueDate = document.getElementById("issueDate").value;
    var dtadditionDate=getDateObject(additionDate,formatD.substring(2, 3));
    var dtbusinessDate=getDateObject(businessDate,formatD.substring(2, 3));
    var dtissueDate=getDateObject(issueDate,formatD.substring(2, 3));
   // alert("dtissueDate::"+dtissueDate);
    //alert("dtadditionDate::"+dtadditionDate);
    var msg="";
        	
   	 		if(additionDate=="" || remarks=="")
	 		{
	 			if(additionDate==""){
					msg+=" * Acknowledgement Date is required \n";
				}
				if(remarks==""){
					msg+=" * Remarks is required \n";
				}
				alert(msg);
	 			return false;
	 		}
   	 	if(dtadditionDate>dtbusinessDate)
		{
			alert("Acknowledgement Date should not be greater than Business Date");
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		} 
   	 	if(dtissueDate>dtadditionDate)
		{
			alert("Acknowledgement Date should not be less than Last Receipt Issue date/Branch Stationary Acknowledgment date ");
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		} 
	
	  var contextPath=document.getElementById('contextPath').value;
	  document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=saveAcknowHo";
	  document.getElementById('StationaryMasterForm').submit();
}
function searchAcknowHo()
{
	var lbxBookNo = document.getElementById("lbxBookNo").value;
    var desc = document.getElementById("desc").value;  
    
	if(lbxBookNo=="" && desc=="")
		{ 					
		alert(" * Please Select Alteast One Criteria To Search ");
			return false;
		}
     var contextPath=document.getElementById('contextPath').value;
	 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryAcknoSrchHo";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById('StationaryMasterForm').submit();
}

function searchStationaryHoAckno()
{
	var lbxBookNo = document.getElementById("lbxBookNo").value;
    var desc = document.getElementById("desc").value;  
    
	if(lbxBookNo=="" && desc=="")
		{ 					
		alert(" * Please Select Alteast One Criteria To Search ");
			return false;
		}
     var contextPath=document.getElementById('contextPath').value;
	 document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=stationaryHoSearchAcknowledge";
	 document.getElementById("processingImage").style.display = '';
	 document.getElementById('StationaryMasterForm').submit();
}


function saveStationaryAcknowHo()
{
	//alert("Rohit");
	var bookIssue = document.getElementById("bookIssue").value;
    var additionDate = document.getElementById("additionDate").value;
    var remarks = document.getElementById("remarks").value;
    var businessDate = document.getElementById("businessdate").value;
    var returnToHODate = document.getElementById("returnToHODate").value;
    var issueDate = document.getElementById("issueDate").value;
    var formatD=document.getElementById("formatD").value;
    var dtadditionDate=getDateObject(additionDate,formatD.substring(2, 3));
    var dtbusinessDate=getDateObject(businessDate,formatD.substring(2, 3));
    var dtreturnToHODate = getDateObject(returnToHODate,formatD.substring(2,3));
    var dtissueDate = getDateObject(issueDate,formatD.substring(2,3));
    var msg="";
      	
   	 		if(additionDate=="" || remarks=="")
	 		{
	 			if(additionDate==""){
					msg+=" * Acknowledgement Date is required \n";
				}
				if(remarks==""){
					msg+=" * Remarks is required \n";
				}
				alert(msg);
	 			return false;
	 		}
   	 //	alert("dtadditionDate:::  "+dtadditionDate);
   	// 	alert("dtbusinessDate:::  "+dtbusinessDate);
   	// alert("dtreturnToHODate:::  "+dtreturnToHODate);
   	 	if(dtadditionDate>dtbusinessDate)
		{
			alert("Acknowledgement Date should be less than Business Date");
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		} 
   	/* if(dtadditionDate>dtreturnToHODate)
		{
			alert("Acknowledgement Date should be less than or equal to Return To HO Date i.e. "+returnToHODate);
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		}*/
	 if(dtadditionDate<dtreturnToHODate)
		{
			alert("Acknowledgement Date should be greater than or equal to Branch Stationary Return to HO  Date i.e. "+dtreturnToHODate);
			document.getElementById("additionDate").value='';
			document.getElementById("additionDate").focus();
			return false;
		}
	
	  var contextPath=document.getElementById('contextPath').value;
	  document.getElementById('StationaryMasterForm').action=contextPath+"/StationaryIssuanceDispatchActionAtCM.do?method=saveStationaryAcknowHo";
	  document.getElementById('StationaryMasterForm').submit();
}

function saveStationarBranchChange(){
	
	DisButClass.prototype.DisButMethod();
	
	var hoRemarks = document.getElementById("hoRemarks");
	var lbxBranchId = document.getElementById("lbxBranchId");
	
	 if(trim(lbxBranchId.value) =='' ){
		 var msg= '';
		 if(trim(lbxBranchId.value) == '')
	 			msg += '* New Branch is required.\n';
 		 		
 		if(msg.match("Branch")){
 			lbxBranchId.focus();
 		}
 		
 		alert(msg);
		DisButClass.prototype.EnbButMethod();

 		return false;
	 
	 }
	
 
		 document.getElementById("StationaryMasterForm1").action="StationaryBranchgChangeDispatchActionAtCM.do?method=saveStationarybranchChange";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("StationaryMasterForm1").submit();

	return true;

//	 else
//	 {
//		DisButClass.prototype.EnbButMethod();
//		//document.getElementById("save").removeAttribute("disabled","true");
//		return false;
//	 }

}

function searchStationaryBranchChange(val){
	
	DisButClass.prototype.DisButMethod();
	 
	if((document.getElementById("lbxBookNo").value=='')&&(document.getElementById("desc").value=='')){
     alert(val);
     	DisButClass.prototype.EnbButMethod();
	 return false; 
	}
	else{
	document.getElementById("StationaryBranchchange").action="StationaryBranchgChangeDispatchActionAtCM.do?method=searchBranchChange";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("StationaryBranchchange").submit();
    return true;
    }
}
//		Change started by Ashish
function clearUseReceipt()
{
	document.getElementById("receiptNo").value="";
	document.getElementById("status").value="";
	document.getElementById("remarks").value="";
}

function validateBookNo(bookNo)
{
	var filter = /^([0-9])+\-([a-zA-Z])+\-([0-9]{4})+/;
	if(!filter.test(bookNo))
    {
    	alert("Please insert Book No in format of 1111-AAA-1111 ");
    	return false;
    }
    else
    	return true;
}

//		Change ended by Ashish