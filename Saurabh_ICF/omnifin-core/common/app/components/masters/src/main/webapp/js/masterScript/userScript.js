function newAdd()
{
	//alert("in newAdd");
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userMasterSearchForm").action=sourcepath+"/userMasterSearch.do?method=openAddUser";
	document.getElementById("userMasterSearchForm").submit();
	
}


function chkAll()
{
document.getElementById("userButton1").style.display="none";
document.getElementById("branchDesc").options.length = 0;
document.getElementById("lbxBranchIds").value='';
return true;
}
function chkSelective()
{
//alert(document.getElementById("branchDesc").options.length);
//document.getElementById("lbxBranchIds").value=='';	
document.getElementById("userButton1").style.display="";
return true;
}
function chkAlllevels()
{
document.getElementById("levelButton").style.display="none";
document.getElementById("levelDesc").options.length = 0;
document.getElementById("levelIDs").value='';
return true;
}
function chkSelectivelevels()
{

document.getElementById("levelButton").style.display="";
return true;
}
function chkAllEdit()
{
document.getElementById("userButtonEdit").style.display="none";
document.getElementById("branchDesc").options.length = 0;
document.getElementById("lbxBranchIds").value='';

return true;
}

function chkAlllevelsEdit()
{
document.getElementById("levelButtonEdit").style.display="none";
document.getElementById("levelDesc").options.length = 0;
document.getElementById("levelIDs").value='';

return true;
}
function chkSelectiveEdit()
{
//alert(document.getElementById("branchDesc").options.length);
//document.getElementById("lbxBranchIds").value=='';	
document.getElementById("userButtonEdit").style.display="";
document.getElementById("branchDesc").options.length = '';
document.getElementById("lbxBranchIds").value='';
return true;
}

function chkSelectivelevelsEdit()
{
//alert(document.getElementById("branchDesc").options.length);
//document.getElementById("lbxBranchIds").value=='';	
document.getElementById("levelButtonEdit").style.display="";
document.getElementById("levelDesc").options.length = '';
document.getElementById("levelIDs").value='';
return true;
}
function saveUser(val)
{
	DisButClass.prototype.DisButMethod();
	var single=document.getElementById("singleselection").checked; 
	var allselection=document.getElementById("allselection").checked;  
	var singlelevel=document.getElementById("singleselectlevel").checked; 
	var alllevel=document.getElementById("allselectlevel").checked;  
	//alert("in saveUser allselect"+allselect+"\n in saveUser allselection "+allselection);	
	var status;
	var sourcepath=document.getElementById("path").value;
	
	var set=document.getElementById('branchDesc');
	var set1=document.getElementById('levelDesc');
	var branch = document.getElementById('branchDesc').options.length;
	var level = document.getElementById('levelDesc').options.length;
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
//		alert("DListValues"+DListValues);
    } 
	var DListValues1 ="";
	for (var iter1 =0 ; iter1 < level; iter1++)
    {
		DListValues1 = DListValues1+set1.options[iter1].value+"/";
//		alert("DListValues1"+DListValues1);
		
    } 
	
	 
	var strBranch='';
	var strlevel='';
	
	var count=0;
	var countt=0;
	if(DListValues.length>count){
		while ( count<DListValues.length) {
    	
			strBranch=strBranch+DListValues[count];
			count =count+1;	
		}
    }
	if(DListValues1.length>countt){
		while ( countt<DListValues1.length) {
    	
			strlevel=strlevel+DListValues1[countt];
			countt =countt+1;	
		}
    }
    document.getElementById("branchDesc").value=strBranch;
    var lbxBranchId = document.getElementById("lbxBranchId").value;
    
   
    var branch = new Array();
    var level = new Array();
    var count=0;

    branch=DListValues.split('/');
    level=DListValues1.split('/');
    if(validateUserMasterAddDynaValidatorForm(document.getElementById("userMasterForm"))){
    for(i=0;i<branch.length;i++)
    {
    	if(branch[i] == lbxBranchId)
    	{
    		count=count+1;
    	}
    }
   
//    if(allselect=="true" && count>0)
//    {    	
//		document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=saveUserDetails&branch="+DListValues;
//		document.getElementById("userMasterForm").submit();
//    }
//    else
//    {    	  	
//    	alert(val);
//    	document.getElementById("save").removeAttribute("disabled","true");
//    	return false;
//    }
// var userStatus=documenet.getElementById("userStatus");
//    if(!(userStatus.checked==true)){
//    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=checkLowerHeirarchy";
//  	  document.getElementById("userMasterForm").submit(); 	
//    }
  if(single==false && allselection==false)
  {
	alert("Select Branch Access!");
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("save").removeAttribute("disabled","true");
	return false;
  }
//  else if(singlelevel==false && alllevel==false)
//  {
//	alert("Select Level Access!"); 
//	DisButClass.prototype.EnbButMethod();
////	document.getElementById("save").removeAttribute("disabled","true");
//	return false;
//  }
 // else if((single==true && count>0)&&(singlelevel==true && countt>0))
  
  else if(single==true && count==0)
  {
    	  alert(val);
    	DisButClass.prototype.EnbButMethod();
//     	  document.getElementById("save").removeAttribute("disabled","true");
      	  return false;  
   }
  else if(singlelevel==true && countt==0)
  {
	  
	alert("Please select level access"); 
	document.getElementById("levelButton").focus();
	DisButClass.prototype.EnbButMethod();
//	document.getElementById("save").removeAttribute("disabled","true");

	return false;
  }
  
  //else if((single==true && count>0)&&(singlelevel==true && countt>0))
  else if((single==true && count>0))
  {
	  document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=saveUserDetails&branch="+DListValues+"&level="+DListValues1;
	  document.getElementById("processingImage").style.display = '';
	  document.getElementById("userMasterForm").submit(); 
  }
 
  //else if((allselection==true)&&(alllevel==true))
	  else if((allselection==true))
  {
	  document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=saveUserDetails&branch="+DListValues+"&level="+DListValues1;
	  document.getElementById("processingImage").style.display = '';
	  document.getElementById("userMasterForm").submit(); 
  }
 
 
	}
    else
    {
    	DisButClass.prototype.EnbButMethod();
//    	document.getElementById("save").removeAttribute("disabled","true");
    	return false;
    }
}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userMasterForm").action=sourcepath+"/userMasterSearch.do?method=openAddUser&userId="+a;
	document.getElementById("userMasterForm").submit();
	
}
function editUser(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userMasterSearchForm").action=sourcepath+"/userMasterSearch.do?method=openEditUser&userSearchId="+b;
	document.getElementById("userMasterSearchForm").submit();
	
	
}

function fnEditUser(b,val){
	DisButClass.prototype.DisButMethod();
	var status;
	var sourcepath=document.getElementById("path").value;
	var set=document.getElementById('branchDesc');
	var single=document.getElementById("singleselection").checked; 
	var allselection=document.getElementById("allselection").checked;
	var singlelevel=document.getElementById("singleselectlevel").checked; 
	var alllevel=document.getElementById("allselectlevel").checked;  
	var branch = document.getElementById('branchDesc').options.length;
	var level = document.getElementById('levelDesc').options.length;
	var set1=document.getElementById('levelDesc');
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
		
    } 
	var DListValues1 ="";
	for (var iter1 =0 ; iter1 < level; iter1++)
    {
		DListValues1 = DListValues1+set1.options[iter1].value+"/";
	
		
    } 
	 
	var strBranch='';
	var strlevel='';
	
	var count=0;
	var countt=0;
	if(DListValues.length>count){
    while ( count<DListValues.length) {
    	
					strBranch=strBranch+DListValues[count];
					count =count+1;	
					
    	}
    }
	if(DListValues1.length>countt){
		while ( countt<DListValues1.length) {
    	
			strlevel=strlevel+DListValues1[countt];
	
			countt =countt+1;	
		}
    }
    document.getElementById("branchDesc").value=strBranch;
    var lbxBranchId = document.getElementById("lbxBranchId").value;
    
    var branch = new Array();
    var level = new Array();
    var count=0;
    branch=DListValues.split('/');
    level=DListValues1.split('/');
    if(validateUserMasterAddDynaValidatorForm(document.getElementById("userMasterForm"))){
    for(i=0;i<branch.length;i++)
    {
    	if(branch[i] == lbxBranchId)
    	{
    		count=count+1;
    	}
    }
   
//    if(count>0)
//    {
//		
//    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=updateUser&userId="+b+"&branch="+DListValues;;
//    	document.getElementById("userMasterForm").submit();
//		
//    }
//    else
//    {
//    	alert(val);
//    	document.getElementById("save").removeAttribute("disabled","true");
//    	return false;
//    }
  
    
    if(single==false && allselection==false)
    {
  	alert("Select Branch Access!"); 
	DisButClass.prototype.EnbButMethod();
//  	document.getElementById("save").removeAttribute("disabled","true");
  	return false;
    }
    else if(singlelevel==true && countt==0)
    {
  	alert("Please select level access"); 
	document.getElementById("levelButtonEdit").focus();
	DisButClass.prototype.EnbButMethod();
//  	document.getElementById("save").removeAttribute("disabled","true");

  	return false;
    }
    
//    else if(singlelevel==false && alllevel==false)
//    {
//  	alert("Select Level Access!");
//	DisButClass.prototype.EnbButMethod();
////  	document.getElementById("save").removeAttribute("disabled","true");
//  	return false;
//    }
    //else if((single==true && count>0)&&(singlelevel==true && countt>0))
    	  else if((single==true && count>0))
    {
    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=updateUser&userId="+b+"&branch="+DListValues+"&level="+DListValues1;
  	  document.getElementById("processingImage").style.display = '';
    	document.getElementById("userMasterForm").submit(); 
    }
    //else if((allselection==true)&&(alllevel==true))
    	  else if((allselection==true))
    {
    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=updateUser&userId="+b+"&branch="+DListValues+"&level="+DListValues1;
  	  document.getElementById("processingImage").style.display = '';
    	document.getElementById("userMasterForm").submit(); 
    }
    	 // else if((allselection==true)&&(singlelevel==true && countt>0))
    	  else if((allselection==true))
    {
    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=updateUser&userId="+b+"&branch="+DListValues+"&level="+DListValues1;
  	  document.getElementById("processingImage").style.display = '';
    	document.getElementById("userMasterForm").submit(); 
    }
   // else if((alllevel==true)&&(single==true && count>0))
    	else if((single==true && count>0))
    {
    	document.getElementById("userMasterForm").action=sourcepath+"/userMasterAdd.do?method=updateUser&userId="+b+"&branch="+DListValues+"&level="+DListValues1;
  	  document.getElementById("processingImage").style.display = '';
    	document.getElementById("userMasterForm").submit(); 
    }
    else if(single==true && count==0)
    {
      	  alert(val);
      	DisButClass.prototype.EnbButMethod();
//       	  document.getElementById("save").removeAttribute("disabled","true");
        	  return false;  
     }
  
    
	}
    else
    {
    	DisButClass.prototype.EnbButMethod();
//    	 document.getElementById("save").removeAttribute("disabled","true");
    		return false;  
    }
	
}

function fnEditUserPass(b){
	//alert("ok"+b);
	if(validateUserMasterAddDynaValidatorForm(document.getElementById("userMasterForm"))){
		
    document.getElementById("userMasterForm").action="userMasterAdd.do?method=updateUserPassword&userId="+b;
	document.getElementById("userMasterForm").submit();
	return true;
	}
	else{
		document.getElementById("reInitialize").removeAttribute("disabled","true");
		return false;
	}
}
function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
	
	DisButClass.prototype.DisButMethod();
	    if(document.getElementById("userSearchId").value=='' && document.getElementById("userSearchName").value=='' && document.getElementById("branchSearchId").value=='' && document.getElementById("report").value=='')
		{
			alert(val);
//			document.getElementById("save").removeAttribute("disabled","true");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	    document.getElementById("userMasterSearchForm").action="userMasterBehind.do"; 
		  document.getElementById("processingImage").style.display = '';
	    document.getElementById("userMasterSearchForm").submit();
		
	}	

// Asesh Code Start Here
function validateValidityDate()
{
	//alert("In ValidateValidityDate");
	var msg='';
	var formatD=document.getElementById("formatD").value;
	//alert("formatD="+formatD);
	var bDate=document.getElementById("bDate").value;
	//alert("bDate="+bDate);
	var validityDate=document.getElementById("validityDate").value; 
	//alert("validityDate="+validityDate);
    var dt1=getDateObject(validityDate,formatD.substring(2, 3));
    //alert("dt1="+dt1);
    var dt3=getDateObject(bDate,formatD.substring(2, 3));
    //alert("dt3="+dt3);
    
    if(dt1<dt3)
	{
		msg="Please Enter Validity date Greater than or equal to bussiness Date";
		document.getElementById("validityDate").value='';
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
// Asesh Code End Here
		
	