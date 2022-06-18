function searchForNhbMapping(){
	
	DisButClass.prototype.DisButMethod();
	var dealNo=document.getElementById("dealNo").value;
	var nhbCategory=document.getElementById("nhbCategory").value;
	var sourcepath=document.getElementById("contextPath").value;
	  

		if(dealNo=='' && nhbCategory=='')
		{
		alert("Please Enter atleast one field");
		document.getElementById("searchButton").removeAttribute("disabled","true");
		DisButClass.prototype.EnbButMethod();
		return false;
		}
		else
		{
			document.getElementById("NhbMappingForm").action=sourcepath+"/searchNhbMapping.do?method=searchNhbMapping";
			document.getElementById("processingImage").style.display='block';
			document.getElementById("NhbMappingForm").submit();
			return true;
		}
		

}

function newNhbMapping()
{
	DisButClass.prototype.DisButMethod();
	// alert("ok");
	
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("NhbMappingForm").action=sourcepath+"/searchNhbMapping.do?method=openAddNhbMapping";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("NhbMappingForm").submit();
	// return true;
}


function saveNhbMapping()
{
	DisButClass.prototype.DisButMethod();
	var dealNo = document.getElementById("dealNo");
	var nhbCategoryId = document.getElementById("nhbCategoryId");
	
	 if(dealNo.value==''||	nhbCategoryId.value=='' ){
		 var msg= '';
 		
 		if(dealNo.value == '')
 			msg += '* dealNo is required.\n';
 		
 		if(nhbCategoryId.value == '')
 			msg += '* NHB Category is required.\n';

 		
 		alert(msg);
 		
 		if(msg.match("dealNo")){
 			dealNo.focus();
 		}
 		
 		else if(msg.match("Category")){
 			nhbCategoryId.focus();
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		
 		return false;
	 
	 }else {
	// alert("ok");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("NhbForm").action=sourcepath+"/NhbMapping.do?method=saveNhbMapping";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("NhbForm").submit();
	 return true;
}
}

function saveInEditMode()
{
	DisButClass.prototype.DisButMethod();
	var dealNo = document.getElementById("dealNo");
	var nhbCategoryId = document.getElementById("nhbCategoryId");
	
	 if(dealNo.value==''||	nhbCategoryId.value=='' ){
		 var msg= '';
 		
 		if(dealNo.value == '')
 			msg += '* dealNo is required.\n';
 		
 		if(nhbCategoryId.value == '')
 			msg += '* NHB Category is required.\n';

 		
 		alert(msg);
 		
 		if(msg.match("dealNo")){
 			dealNo.focus();
 		}
 		
 		else if(msg.match("Category")){
 			nhbCategoryId.focus();
 		}
 		
		DisButClass.prototype.EnbButMethod();
 		
 		return false;
	 
	 }else {
	// alert("ok");
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("NhbForm").action=sourcepath+"/NhbMapping.do?method=saveInEditMode";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("NhbForm").submit();
	 return true;
}
}
