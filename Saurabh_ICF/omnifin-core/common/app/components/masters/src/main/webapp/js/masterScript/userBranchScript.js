function newAdd()
{
	var sourcepath=document.getElementById("path").value;
//	var branch = document.getElementById('showlbxBranchR');
//	var branchlength=branch.options.length;
//
//	if (branchlength<=0)
//    {
//      alert("Please Select Branch");
//      return;
//    }
//
//	var strBranch='';
//    for(var count=0; count < branch.options.length; count++) {
//					strBranch=strBranch+branch.options[count].value+'#@#';
//					}
//     document.getElementById('showlbxBranchHid').value=strBranch;

	document.getElementById("userBranchMasterSearchForm").action=sourcepath+"/userBranchMasterSearch.do?method=openAddUserBranch";
	document.getElementById("userBranchMasterSearchForm").submit();
	
}
function saveUserBranch(val1,val,val2)
{
	

	var status;
	var sourcepath=document.getElementById("contextPath").value;
	
	var set=document.getElementById('branchDesc');
	
	var branch = document.getElementById('branchDesc').options.length;
	
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
		
    } 
	
	 if ((DListValues=='') && (document.getElementById("userId").value==''))
	 {
      alert(val2);
      document.getElementById("save").removeAttribute("disabled","true");
       return;
	 }
	
	  if (document.getElementById("userId").value=='')
    {
		 alert(val1);
		 document.getElementById("save").removeAttribute("disabled","true");
      return;
    }
	 
	 else if (DListValues=='')
			 {
		 		alert(val);
		 		document.getElementById("save").removeAttribute("disabled","true");
		 		return;
			 }
	 
	var strBranch='';
	var count=0;
	if(DListValues.length>count){
    while ( count<DListValues.length) {
    	
					strBranch=strBranch+DListValues[count];
					
					count =count+1;	}}
	
    document.getElementById("branchDesc").value=strBranch;
	
	document.getElementById("userBranchMasterForm").action=sourcepath+"/userBranchMasterAdd.do?method=saveUserBranchDetails&branch="+DListValues;
	document.getElementById("userBranchMasterForm").submit();
	
	return true;
	
}
function newpage(a)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userBranchMasterForm").action=sourcepath+"/userBranchMasterSearch.do?method=openAddUserBranch&userId="+a;
	document.getElementById("userBranchMasterForm").submit();
	
}
function editUserBranch(b)
{
	var sourcepath=document.getElementById("path").value;
	document.getElementById("userBranchMasterSearchForm").action=sourcepath+"/userBranchMasterSearch.do?method=openEditUserBranch&lbxUserSearchId="+b;
	document.getElementById("userBranchMasterSearchForm").submit();
	
	
}
function fnEditUserBranch(b,val){

	var status;
	var sourcepath=document.getElementById("path").value;
	var set=document.getElementById('branchDesc');
	//alert(document.getElementById('branchDesc').options.value);
	var branch = document.getElementById('branchDesc').options.length;
	//var branchlength=branch.length;
	//alert("branchlength.... "+branch);
	var DListValues ="";
	for (var iter =0 ; iter < branch; iter++)
    {
		DListValues = DListValues+set.options[iter].value+"/";
		
    } 
	
	 if (DListValues=='')
			 {
		 		alert(val);
		 		document.getElementById("save").removeAttribute("disabled","true");
		 		return;
			 }
	
	var strBranch='';
	var count=0;
	if(DListValues.length>count){
    while ( count<DListValues.length) {
    	
					strBranch=strBranch+DListValues[count];
					
					count =count+1;	}}
    document.getElementById("branchDesc").value=strBranch;
	
    document.getElementById("userBranchMasterForm").action=sourcepath+"/userBranchMasterAdd.do?method=updateUserBranch&userId="+b+"&branch="+DListValues;
	document.getElementById("userBranchMasterForm").submit();
	return true;
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){ 
	   
	   if(document.getElementById("userSearchId").value==''&& document.getElementById("branchSearchId").value=='')
	   {
		   alert(val);
		   document.getElementById("save").removeAttribute("disabled","true");
		   return false;
	   }
	    document.getElementById("userBranchMasterSearchForm").action="userBranchMasterBehind.do";
	    document.getElementById("userBranchMasterSearchForm").submit();
	}	
function listbox_moveacross(sourceID, destID) {
	  //alert("in the function listbox_moveacross");
     var src = document.getElementById(sourceID);
   //  alert(src);
       var dest = document.getElementById(destID);
    //   alert(dest);
			for(var count=0; count < src.options.length; count++) {

	 	if(src.options[count].selected == true) {
						var option = src.options[count];

						var newOption = document.createElement("option");
						newOption.value = option.value;
						newOption.text = option.text;
						newOption.selected = true;
						try {
								 dest.add(newOption, null); //Standard
								 src.remove(count, null);
						 }catch(error) {
								 dest.add(newOption); // IE only
								 src.remove(count);
						 }
						count--;
}
}
}		
	