function addRepoAsset(){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	document.getElementById("repoAssetChecklistMasterForm").action=sourcepath+"/RepoAssetChecklistDispatch.do?method=addRepoAsset";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoAssetChecklistMasterForm").submit();
	return true;
	DisButClass.prototype.EnbButMethod();
	document.getElementById("add").removeAttribute("disabled","true");
	return false;
}



function saveRepoAsset(){
	
DisButClass.prototype.DisButMethod();
	var lbxProductSearchID = document.getElementById("lbxProductSearchID");
	var productButton = document.getElementById("productButton");
	var assetClass = document.getElementById("assetClass");
	var path=document.getElementById("contextPath").value;
	var psize = document.getElementById("psize").value;
	
	var checkListIds="";
	gridtable
	 var msg= '';
	if(psize=="" || psize==null){
		psize=document.getElementById('gridtable').rows.length;
		psize=psize+1;
	}
	
	 if(trim(lbxProductSearchID.value) == ''||trim(assetClass.value) == ''){
		 var msg='';
 		if(trim(lbxProductSearchID.value) == '')
 			msg += '* Product is required.\n';
 		if(trim(assetClass.value) == '')
 			msg += '* Asset Class is required.\n';
 		
 		
 		alert(msg);
 		
 		 		 		
 		if(msg.match("Product")){
 			productButton.focus();
 		}
 		else if(msg.match("Asset")){
 			assetClass.focus();
 		}
 		
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 }
		 
	 	for(var i=1;i<psize;i++)
	 	{
	 		
		 	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null)
		 	{
		 		if(document.getElementById("chk"+i).checked==true)
		 		{
		 			
		 			
		 			checkListIds=checkListIds+document.getElementById("checkList"+i).value+"|";
		 			
		 		}
		 	 	
		 	}
		 	
		 }	
		 		
			document.getElementById("repoAssetChecklistMasterForm").action=path+"/RepoAssetChecklistDispatch.do?method=saveRepoAsset&checkListIds="+checkListIds;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("repoAssetChecklistMasterForm").submit();
			return true;
}
	 

function searchRepoAsset(val){
	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;
	if(document.getElementById("repoProduct").value == ""  && document.getElementById("assetClass").value=="" )
	{
     alert(val);

     DisButClass.prototype.EnbButMethod();

	return false; 
	}
	else{
	document.getElementById("repoAssetChecklistMasterForm").action=sourcepath+"/RepoAssetChecklistDispatch.do?method=searchRepoAsset";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("repoAssetChecklistMasterForm").submit();
	return true;
	}
}

	
	
function updateRepoAsset(){
	
	DisButClass.prototype.DisButMethod();
	var lbxProductSearchID = document.getElementById("lbxProductSearchID");
	var assetClass = document.getElementById("assetClass");
	var path=document.getElementById("contextPath").value;
	var psize = document.getElementById("psize").value;
	
	var checkListIds="";
	gridtable
	 var msg= '';
	if(psize=="" || psize==null){
		psize=document.getElementById('gridtable').rows.length;
		psize=psize+1;
	}
	
	 if(trim(lbxProductSearchID.value) == ''||trim(assetClass.value) == ''){
		 var msg='';
 		if(trim(lbxProductSearchID.value) == '')
 			msg += '* product is required.\n';
 		if(trim(assetClass.value) == '')
 			msg += '* asset class Description is required.\n';
 		
 		
 		alert(msg);
 		
 		 		 		
 		if(msg.match("product")){
 			lbxProductSearchID.focus();
 		}
 		else if(msg.match("asset")){
 			assetClass.focus();
 		}
 		
 		DisButClass.prototype.EnbButMethod();
 		return false;
	 }
		 
	 	for(var i=1;i<psize;i++)
	 	{
	 		
		 	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null)
		 	{
		 		if(document.getElementById("chk"+i).checked==true)
		 		{
		 			checkListIds=checkListIds+document.getElementById("checkList"+i).value+"|";
		 			
		 		}
		 	 	
		 	}
		 	
		 }	
		 		
			document.getElementById("repoAssetChecklistMasterForm").action=path+"/RepoAssetChecklistDispatch.do?method=updateRepoAsset&checkListIds="+checkListIds;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("repoAssetChecklistMasterForm").submit();
			return true;
}
	 
 
function isNumberKey(evt) 
		{
		var charCode = (evt.which) ? evt.which : event.keyCode;

		if (charCode > 31 && (charCode < 48 || charCode > 57))
		{
			alert("Only Numeric Value Allowed Here");
			return false;
		}
			return true;
		}


function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}

var ck_numeric = /^[a-zA-Z_'0-9]{1,25}$/;

function validate(formName){
	
	var lbxProductSearchID = document.getElementById("lbxProductSearchID");
	

	var errors = [];
 
	if (!ck_numeric.test(lbxProductSearchID.value)) {
		 if(trim(lbxProductSearchID.value) != ''){
		 	errors[errors.length] = "* product is invalid.";
		 }
	 }


 
 if (errors.length > 0) {
  reportErrors(errors);
  return false;
 }
 
 return true;
}

function reportErrors(errors){
	 var msg = "";
	 for (var i = 0; i<errors.length; i++) {
	  var numError = i + 1;
	  msg += "\n" + errors[i];
	 }
	 	if(msg.match("lbxProductSearchID")){
	 		lbxProductSearchID.focus();
		}
	 
	 alert(msg);
	 document.getElementById("save").removeAttribute("disabled","true");
	}



function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();
}


function addRow(val)
{
 // alert(val);
  var ptable = document.getElementById('gridtable');
  var lastElement = ptable.rows.length;
  //var index=lastElement;
  //document.getElementById("psize").value=1;
 
  var pcheck = document.getElementById("pcheck").value; 
  var psize= document.getElementById("psize").value;	
if(psize==""){
	psize=lastElement;
}

 //  alert(index);
 var row =ptable.insertRow(lastElement);
 row.setAttribute('className','white1' );
 row.setAttribute('class','white1' );
		
  var cellCheck = row.insertCell(0);
	  var element = document.createElement('input');
	  element.type = 'checkbox';
	  element.name = 'chk';
	  element.id = 'chk'+psize;	 
 /* if(val=="Edit"){
  var ele=document.createElement('input');
   ele.type = 'hidden';
   ele.name = 'docCheckId';
   ele.id = 'docCheckId'+psize;
  }*/

  var docCodeSelect = row.insertCell(1);
  var docCode = document.createElement('input');
  docCode.type = 'text';
  //docCode.readOnly=true;
  docCode.name = 'checkList';
  docCode.id = 'checkList'+psize;
  docCode.setAttribute('className','text');
  docCode.setAttribute('class','text');


cellCheck.appendChild(element);	
/*if(val=="Edit"){	
cellCheck.appendChild(ele);	
}*/
docCodeSelect.appendChild(docCode);
  //index++;
  pcheck++;
  psize++;	  
  document.getElementById("psize").value=psize;
  document.getElementById("pcheck").value=pcheck;
	  
  }


function removeRow(val) {
  var table = document.getElementById("gridtable");
     var rowCount = table.rows.length;
     //alert("Row Count"+rowCount)
	var count=0;
     for(var i=1; i<rowCount; i++) {
//    	 alert("i: "+i);
//    	 alert("Row Count"+rowCount);
         var row = table.rows[i];
         var chkbox = row.cells[0].childNodes[0];
//         alert("chkbox.checked: "+chkbox.checked);
         if(null != chkbox && true == chkbox.checked) 
         {
         	 table.deleteRow(i);
             //rowCount--;
             count++;
             i--;
          }        

        }        

  if(count==0){
       alert(val);
     return false;
     }

  }

function allChecked(){ 
	
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	var c = document.getElementById("allchkd").checked;
	
		
	var psize=document.getElementById("psize").value;
	
	if(psize==""){
		psize=lastElement;
	}
	
	

	if(c==true){
		for(i=1;i<psize;i++)
		{
			
			var ch1=document.getElementById('chk'+i);
			if(ch1!=undefined||ch1!=null){
				document.getElementById('chk'+i).checked=true;
				}		
			
		}
	}else{
		for(i=1;i<psize;i++)
		{
			
			var ch2=document.getElementById('chk'+i);
			if(ch2!=undefined||ch2!=null){
				document.getElementById('chk'+i).checked=false;
			}
		}
	}
}
