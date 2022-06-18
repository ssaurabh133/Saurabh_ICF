// vishal start

function newAddHoliday(){
	
	DisButClass.prototype.EnbButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("holidayMasterForm").action=sourcepath+"/holidayMaintenance.do?method=openAddHoliday";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("holidayMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	

}

function saveHoliday(txtDate,bussinessDate){

	DisButClass.prototype.DisButMethod();
	var msg="";
	var field1="";
	var field2="";
	
	
	var formatD=document.getElementById("formatD").value;
	   var txtDateValue = document.getElementById(txtDate).value;
	   
	     	
	if(document.getElementById("holidayDate").value=='')
	{   
		msg=" * Holiday Date is required.";
		field1="holidayDate";
		
		
	}
	if(document.getElementById("holidayDes").value=='')
	{   
		msg=msg+"\n * Holiday Discription is required.";
		field2="holidayDes";
		
		
	}
	if(document.getElementById("holidayDes").value=='' ||  document.getElementById("holidayDate").value=='' )
	{   
		alert(msg);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("saveButton").removeAttribute("disabled","true");
		if(field1=='holidayDate'){
		document.getElementById("holidayDate").focus();
		}else if(field2=='holidayDes'){
			document.getElementById("holidayDes").focus();
		 }
		return false;
		
		
	}
	 var msg1="";
	    var dt1=getDateObject(txtDateValue,formatD.substring(2, 3));
	    var dt3=getDateObject(bussinessDate,formatD.substring(2, 3));
	    
	    if(dt1<=dt3)
		{
			msg1="New Holiday Date should be greater then bussiness Date";
			alert(msg1);
			DisButClass.prototype.EnbButMethod();
//			document.getElementById("saveButton").removeAttribute("disabled","true");
			return false;
		}else{
		
		document.getElementById("holidayMaintenanceForm").action="holiDayMasterAdd.do?method=saveHolidayDetails";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("holidayMaintenanceForm").submit();
		return true;	
 }
}
function fnSearchHoliday(val){

	DisButClass.prototype.DisButMethod();
	document.getElementById("holidayMasterForm").action="holidayMaintenanceBehind.do";
	if(document.getElementById("holidaySearchDate").value=='' && document.getElementById("holidaySearchDes").value=='' && document.getElementById("holidayTypeSearch").value=='')
	{
		alert(val);
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("Search").removeAttribute("disabled","true");
		return false;
	}else{
		document.getElementById("processingImage").style.display = '';
	document.getElementById("holidayMasterForm").submit();
	return true;
	}
	}

function fnEditHoliday(txtDate,bussinessDate){
	
	var msg="";
	
	var formatD=document.getElementById("formatD").value;
	   var txtDateValue = document.getElementById(txtDate).value;
	   
	    var msg1="";
	    var dt1=getDateObject(txtDateValue,formatD.substring(2, 3));
	    var dt3=getDateObject(bussinessDate,formatD.substring(2, 3));
	    
	    if(dt1<=dt3)
		{
			msg1="New Holiday Date should be greater then bussiness Date";
			alert(msg1);
			return false;
		}
	
		
	if(document.getElementById("holidayDate").value=='')
	{   
		msg="Holiday Date is required.";
		field="holidayDate";
		
		
	}
	if(document.getElementById("holidayDes").value=='')
	{   
		msg=msg+"\n Holiday Discription is required.";
		field="holidayDes";
		
		
	}
	if(document.getElementById("holidayDate").value=='' ||  document.getElementById("holidayDes").value=='' )
	{   
		alert(msg);
		msg="";
		document.getElementById("saveButton").removeAttribute("disabled","true");
		document.getElementById(field).focus();
		
		
	}else{
		
	document.getElementById("holidayMaintenanceForm").action="holiDayMasterAdd.do?method=updateHoliday";
	document.getElementById("holidayMaintenanceForm").submit();
	return true;

}
}

function checkDate(txtDate){
    // define date string to test
    var txtDateValue = document.getElementById(txtDate).value;
   
    
    var formatD=document.getElementById("formatD").value;// must be take this format from jsp
  
    
   // alert("ok"+formatD);
    // check date and print message
    if (isDate(txtDateValue,formatD) || txtDateValue == null || txtDateValue == '') {
       return true;
    }
    else 
    {
        alert('Invalid date format!');
        document.getElementById(txtDate).value='';
        return false;
    }
    
    
   
}

function validateDate(txtDate,bussinessDate){
	var formatD=document.getElementById("formatD").value;
	   var txtDateValue = document.getElementById(txtDate).value;
	   
	    var msg="";
	    var dt1=getDateObject(txtDateValue,formatD.substring(2, 3));
	    var dt3=getDateObject(bussinessDate,formatD.substring(2, 3));
	    
	    if(dt1<=dt3)
		{
			msg="New Holiday Date should be greater then bussiness Date";
			
			//return false;
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

function changeHolidayType(){
	
	var id=document.getElementById("holidayType").value;
	//alert("id: "+id);
	if(id=='Weekly'){
		
	document.getElementById("weekly").style.display="";
	document.getElementById("daily").style.display="none";	
	document.getElementById("weekSave").style.display="";
	document.getElementById("daySave").style.display="none";
	
	  }	else {
		document.getElementById("weekly").style.display="none";
		document.getElementById("daily").style.display="";	
		document.getElementById("daySave").style.display="";
		document.getElementById("weekSave").style.display="none";
      }
}

function saveHolidayforWeekend(){
	

	DisButClass.prototype.DisButMethod();
	if(document.getElementById("holidayDay").value=='')
	{   
		msg="* Please select Holiday Day .";
		field="holidayDay";
	}
	if(document.getElementById("week").value=='')
	{   
		msg=msg+"* Please select Week of month.";
		field="holidayDay";
	}
	if(document.getElementById("holidayDay").value=='' ||  document.getElementById("week").value=='')
	{   
		
		alert(msg);
		msg="";
		DisButClass.prototype.EnbButMethod();
//		document.getElementById("saveWeeklyButton").removeAttribute("disabled","true");
		return false;
	}else{
		 document.getElementById("holidayMaintenanceForm").action="holiDayMasterAdd.do?method=saveHolidayDetails";
		 document.getElementById("processingImage").style.display = '';
         document.getElementById("holidayMaintenanceForm").submit();
         return true;
	}

}

function fnEditHolidayStatus(){
	DisButClass.prototype.DisButMethod();
	document.getElementById("holidayMaintenanceForm").action="holiDayMasterAdd.do?method=updateHoliday";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("holidayMaintenanceForm").submit();
	return true;
	
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}

// vishal end
