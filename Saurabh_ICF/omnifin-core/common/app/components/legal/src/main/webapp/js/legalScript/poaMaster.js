function newAddPOA(){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	
	document.getElementById("poaMasterForm").action=sourcepath+"/poaMaster.do?method=openAddPOA";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("poaMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("add").removeAttribute("disabled","true");
	return false;
	}


	function savePOA(){
	
	DisButClass.prototype.DisButMethod();
	var POA = document.getElementById("lbxUserId");
	var branch = document.getElementById("lbxBranchId");
	var courtName= document.getElementById("lbxCourtNameCode");
	var courName= document.getElementById("lbxCourtNameCode").value;
	var userButton= document.getElementById("userButton");
	var userButton1= document.getElementById("userButton1");
	var poabutton= document.getElementById("poabutton");
	
	 if(trim(POA.value)==''||trim(branch.value)=='' || trim(courtName.value)==''){
		 var msg= '';
 		
 		if(trim(branch.value) == '')
 			msg += '* Branch is required.\n';
 		
 		if(trim(courtName.value) == '')
 			msg += '* Court Name is required.\n';
 		
 		if(trim(POA.value) == '')
 			msg += '* POA is required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("Branch")){
 			userButton.focus();
 		}
 		
 		else if(msg.match("Court")){
 			userButton1.focus();
 		}
 		
 		else if(msg.match("POA")){
 			poabutton.focus();
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		
 		return false;
	 
	 }else {
	
			document.getElementById("poaMasterForm").action="poaMasterAdd.do?method=savePOADetails&lbxCourtNameCode="+courName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("poaMasterForm").submit();
			return true;
	}
	
}


function fnSearchPOA(val){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("userId").value =="" && document.getElementById("courtName").value=="" && document.getElementById("branch").value=="")
	{
     alert(val);
     document.getElementById("userbutton").focus();
     DisButClass.prototype.EnbButMethod();
 document.getElementById("save").removeAttribute("disabled","true");
	return false; 
	}
	else{
		document.getElementById("poaMasterForm").action=sourcepath+"/poaMasterBehind.do";
		document.getElementById("processingImage").style.display = '';
		document.getElementById("poaMasterForm").submit();
	return true;
	}
}

function openEditPOA(){
	
	
	
	DisButClass.prototype.DisButMethod();
	var POA = document.getElementById("lbxUserId");
	var branch = document.getElementById("lbxBranchId");
	var courtName= document.getElementById("lbxCourtNameCode");
	var courName= document.getElementById("lbxCourtNameCode").value;
	 if(trim(POA.value)==''||trim(branch.value)=='' || trim(courtName.value)==''){
		 var msg= '';
 		
 		if(trim(branch.value) == '')
 			msg += '* Branch is required.\n';
 		
 		if(trim(courtName.value) == '')
 			msg += '* Court Name is required.\n';
 		
 		if(trim(POA.value) == '')
 			msg += '* POA is required.\n';
 		
 		alert(msg);
 		
 		if(msg.match("Branch")){
 			branch.focus();
 		}
 		else if(msg.match("courtName")){
 			courtName.focus();
 		}
 		else if(msg.match("POA")){
 			courtName.focus();
 		}
 		
 		DisButClass.prototype.EnbButMethod();
 		
 		return false;
	 
		
	 
	 }else {
	
			document.getElementById("poaMasterForm").action="poaMasterAdd.do?method=updatePOA&lbxCourtNameCode="+courName;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("poaMasterForm").submit();
			return true;
	}
	
}


function clearCourtName()
{
  var src = document.getElementById("courtName");
  
  document.getElementById("lbxCourtNameCode").value = "";
  
  for(var count=0; count < src.options.length; count++) 
  {
    try 
    {
      //Standard
      src.remove(count, null);
    }
    catch(error) 
    {
      // IE only
      src.remove(count);
    }
  count--;
  
  }
}