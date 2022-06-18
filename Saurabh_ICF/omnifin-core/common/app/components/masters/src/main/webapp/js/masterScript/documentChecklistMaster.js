//Document Checklist Master


function getlist()
{
	var ven=document.getElementById("docEntity").value;	
		if(ven=='PRAPPL'||ven=='COAPPL'||ven=='GUARANTOR')
		{
			document.getElementById("assetText").style.display='none';
			document.getElementById("colleteralText").style.display='none';
			document.getElementById("docConstitution").disabled=false;
			document.getElementById("productButton").disabled=true;
			document.getElementById("schemeButton").disabled=true;
			document.getElementById("productId").value='';
			document.getElementById("lbxProductID").value='';
			document.getElementById("schemeId").value='';
			document.getElementById("lbxSchemeID").value='';
			
			document.getElementById("lbxAssetCollId").value='';
			document.getElementById("assetClass").value='';
			
		}
		else if(ven=='ASSET')
		{
			document.getElementById("colleteralText").style.display='none';
			document.getElementById("assetText").style.display='';
			document.getElementById("docConstitution").disabled=true;
			document.getElementById("docConstitution").value='';
			document.getElementById("productButton").disabled=true;
			document.getElementById("schemeButton").disabled=true;
			document.getElementById("productId").value='';
			document.getElementById("lbxProductID").value='';
			document.getElementById("schemeId").value='';
			document.getElementById("lbxSchemeID").value='';
			document.getElementById("assetButton").disabled=false;
			
		}
		else if(ven=='COLLATERAL')
		{
			document.getElementById("assetText").style.display='none';
			document.getElementById("colleteralText").style.display='';
			document.getElementById("docConstitution").disabled=true;
			document.getElementById("productButton").disabled=true;
			document.getElementById("schemeButton").disabled=true;
			document.getElementById("productId").value='';
			document.getElementById("lbxProductID").value='';
			document.getElementById("schemeId").value='';
			document.getElementById("lbxSchemeID").value='';
			document.getElementById("docConstitution").value='';
			document.getElementById("assetButton").disabled=false;

		}
		else if(ven=='APPL')
		{
			document.getElementById("assetText").style.display='none';
			document.getElementById("colleteralText").style.display='none';
			document.getElementById("docConstitution").disabled=true;
			document.getElementById("productButton").disabled=false;
			document.getElementById("schemeButton").disabled=false;
			document.getElementById("docConstitution").value='';
			document.getElementById("lbxAssetCollId").value='';
			document.getElementById("assetClass").value='';

		}
		else
		{
			document.getElementById("assetText").style.display='none';
			document.getElementById("colleteralText").style.display='none';
			document.getElementById("docConstitution").disabled=true;
			document.getElementById("productButton").disabled=true;
			document.getElementById("schemeButton").disabled=true;
			document.getElementById("productId").value='';
			document.getElementById("lbxProductID").value='';
			document.getElementById("schemeId").value='';
			document.getElementById("lbxSchemeID").value='';
			document.getElementById("lbxAssetCollId").value='';
			document.getElementById("assetClass").value='';
		}
	
}

function getChageClass()
{
	var ven=document.getElementById("docEntity").value;	
	if(ven=='COLLATERAL')
	{
		document.getElementById("assetText").style.display='none';
		document.getElementById("colleteralText").style.display='';
		document.getElementById("docConstitution").disabled=true;
		document.getElementById("productButton").disabled=true;
		document.getElementById("schemeButton").disabled=true;
		document.getElementById("productId").value='';
		document.getElementById("lbxProductID").value='';
		document.getElementById("schemeId").value='';
		document.getElementById("lbxSchemeID").value='';
		document.getElementById("docConstitution").value='';
		document.getElementById("assetButton").disabled=false;
		document.getElementById("lbxAssetCollId").value='';
		document.getElementById("assetClass").value='';
	}
	else if(ven=='ASSET')
	{
		document.getElementById("colleteralText").style.display='none';
		document.getElementById("assetText").style.display='';
		document.getElementById("docConstitution").disabled=true;
		document.getElementById("docConstitution").value='';
		document.getElementById("productButton").disabled=true;
		document.getElementById("schemeButton").disabled=true;
		document.getElementById("productId").value='';
		document.getElementById("lbxProductID").value='';
		document.getElementById("schemeId").value='';
		document.getElementById("lbxSchemeID").value='';
		document.getElementById("assetButton").disabled=false;
		document.getElementById("lbxAssetCollId").value='';
		document.getElementById("assetClass").value='';
	}
}

function fnSearch(){	
	
	DisButClass.prototype.DisButMethod();
	var ven=document.getElementById("docEntity").value;
	var docStage=document.getElementById("docStage").value;
	var productId=document.getElementById("productId").value;
	var assetClass=document.getElementById("lbxAssetCollId").value;
	var docConstitution=document.getElementById("docConstitution").value;
	if(ven == "" || docStage == ""){
		var a = "";
		if(ven == ""){
			a += "*  Entity Type is required.\n";
		}
		if(docStage == ""){
			a += "*  Stage is required.";
		}
		alert(a);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else if(document.getElementById("documentChecklistMasterSearch")){
		
		if(ven=='APPL' && productId=='')
		{
			alert("Please Select Product");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if((ven=='ASSET' || ven=='COLLATERAL' )&& assetClass=='')
		{
			alert("Please Select Asset/Collateral Class");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if((ven=='COAPPL' || ven=='GUARANTOR' || ven=='PRAPPL') && docConstitution=='')
		{
			alert("Please Select Constitution");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		else
		{
			document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearch.do?method=searchDocChekList";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("documentChecklistMasterSearch").submit();
			return true;
		}
		
	}
}

function allChecked()
{
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	var c = document.getElementById("allchkd").checked;
	
	var ch=document.getElementsByName('chk');
 	var zx=0; 	
	//alert(c);	
	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=lastElement;
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

function schemeDetailsSave(val){
	DisButClass.prototype.DisButMethod();
	var ven=document.getElementById("docEntity").value;
	var stage=document.getElementById("docStage").value;
	
	var a = "";
	if(ven == "" || stage == ""){
		if(ven == "")
			a +="* Entity Type is required. \n";
		if(stage == "")
			a +="* Stage is required.";
		
		if(ven == ""){
			document.getElementById("docEntity").focus();
		}else if(stage == ""){
			document.getElementById("docStage").focus();
		}
		
		alert(a);
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(document.getElementById("documentChecklistMasterSearch"))
	{
	var sourcepath=document.getElementById("path").value;
	
	var flag=0;
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	
	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=lastElement;
	}
	var ven=document.getElementById("docEntity").value;
	var productId=document.getElementById("productId").value;
	var assetClass=document.getElementById("lbxAssetCollId").value;
	var docConstitution=document.getElementById("docConstitution").value;
	
	var docIdList="";
	var docMandatoryList="";
	var docOriginalList="";
	var docExpiryFlagList ="";
	var statusList ="";
	
	if(ven=='APPL' && productId=='')
	{
		alert("Please Select Product");
		document.getElementById("productButton").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((ven=='ASSET' || ven=='COLLATERAL' )&& assetClass=='')
	{
		alert("Please Select Asset/Collateral Class");
		document.getElementById("assetTextButton").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((ven=='COAPPL' || ven=='GUARANTOR' || ven=='PRAPPL') && docConstitution=='')
	{
		alert("Please Select Constitution");
		document.getElementById("docConstitution").focus();
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	for(i=1;i<psize;i++){  		
     	if((document.getElementById("chk"+i))!=undefined||(document.getElementById("chk"+i))!=null){
		if(document.getElementById("chk"+i).checked==true){		
			if(document.getElementById("docId"+i).value==""){
				flag=0;
				alert('Please select Document Description.');
				DisButClass.prototype.EnbButMethod();
				return false;
			}
			 docIdList=docIdList +document.getElementById("docId"+i).value + "/";
					
				 if((document.getElementById("docMandatory"+i).checked)==true){
					 	docMandatoryList=docMandatoryList + "Y" + "/";
				 }else{
					 	docMandatoryList=docMandatoryList + "N" + "/";
				 }
												 	
				
				 if((document.getElementById("docOriginal"+i).checked)==true){
					 	docOriginalList = docOriginalList + "Y" + "/";
				 }else{
					 	docOriginalList = docOriginalList + "N" + "/";
				 }
									 
				 if((document.getElementById("docExpiryFlag"+i).checked)==true){
					 	docExpiryFlagList = docExpiryFlagList + "Y" + "/";
				 }else{
					 docExpiryFlagList = docExpiryFlagList + "N" + "/";
				 }
				
				 if((document.getElementById("status"+i).checked)==true){
					 statusList=statusList + "A" + "/"; 
				 }else{
					 statusList=statusList + "X" + "/"; 
				 }

				 flag=1;		
				}		
			 }
	}
	 if(flag==0)
   	 {		 
   		 alert(val);
   		 DisButClass.prototype.EnbButMethod();
   		 return false;
   	 }
   	var confrm = confirm("Do you want to continue?");
   	if(confrm){
		document.getElementById("documentChecklistMasterSearch").action=sourcepath+"/documentChecklistMasterAdd.do?method=savedocChekDetails&docIdList="+docIdList+"&docMandatoryList="+docMandatoryList+"&docOriginalList="+docOriginalList+"&docExpiryFlagList="+docExpiryFlagList+"&statusList="+statusList;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("documentChecklistMasterSearch").submit();
		return true;	
   	 }else{ 
   		 DisButClass.prototype.EnbButMethod();
  		return false;
  	 }
 }
}

function docChildModify(val){
	DisButClass.prototype.DisButMethod();
	var ch=document.getElementsByName('chk');
	var docCheckId=document.getElementsByName("docCheckId");
	var docId=document.getElementsByName("docId");
	var docMandatory=document.getElementsByName("docMandatory");
	var docOriginal=document.getElementsByName("docOriginal");
	var docExpiryFlag=document.getElementsByName("docExpiryFlag");
	var status=document.getElementsByName("status");
	var ven=document.getElementById("docEntity").value;
	var productId=document.getElementById("productId").value;
	var assetClass=document.getElementById("lbxAssetCollId").value;
	var docConstitution=document.getElementById("docConstitution").value;
	
	var docCheckIdForDel=document.getElementsByName("docCheckIdForDel");
	
	var docCheckAllId="";
	var docCheckIdList="";
	var docIdList="";
	var docMandatoryList="";
	var docOriginalList="";
	var docExpiryFlagList ="";
	var statusList ="";
	var flag=0;
	var ptable = document.getElementById('gridtable');
	
	if(ven=='APPL' && productId=='')
	{
		alert("Please Select Product");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((ven=='ASSET' || ven=='COLLATERAL' )&& assetClass=='')
	{
		alert("Please Select Asset/Collateral Class");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((ven=='COAPPL' || ven=='GUARANTOR' || ven=='PRAPPL') && docConstitution=='')
	{
		alert("Please Select Constitution");
		DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	for(var j=0;j<docCheckIdForDel.length;j++){
		
		if(docCheckIdForDel[j] == 'undefined' || docCheckIdForDel[j] == '' || docCheckIdForDel[j] == null){
			
		}else{
			docCheckAllId=docCheckAllId + docCheckIdForDel[j].value + "/";
			//alert(docCheckAllId);
		}
		
	}
	for(var i=0;i<ch.length;i++){
//		alert(i);	 
//		alert('ch '+ch[i].checked);
//		alert('chk '+document.getElementById("chk"+i).checked);
		
		
/*		alert("ch :-"+docCheckAllId);*/
		if(ch[i] == 'undefined' || ch[i] == '' || ch[i] == null){
			continue;
		}else{ 
			
			if(ch[i].checked==true){
					 
					 docIdList=docIdList + docId[i].value + "/";
		 
					 docCheckIdList=docCheckIdList + docCheckId[i].value + "/";
					 
					 if((docMandatory[i].checked)==true){
						 	docMandatoryList=docMandatoryList + "Y" + "/";
					 }else{
						 	docMandatoryList=docMandatoryList + "N" + "/";
						 //	alert("docMandatoryList"+docMandatoryList);
					 }
												 	
					 if((docOriginal[i].checked)==true){
						 	docOriginalList = docOriginalList + "Y" + "/";
					 }else{
						 	docOriginalList = docOriginalList + "N" + "/";
					 }
					 
					 if((docExpiryFlag[i].checked)==true){
						 	docExpiryFlagList = docExpiryFlagList + "Y" + "/";
					 }else{
						 docExpiryFlagList = docExpiryFlagList + "N" + "/";
					 }

					 if((status[i].checked)==true){
						 statusList=statusList + "A" + "/"; 
					 }else{
						 statusList=statusList + "X" + "/"; 
					 }
					 flag=1;		

				}else{
					/*alert("------------");*/
					continue;
				}
		}
			 }
	 if(flag==0)
   	 {
   		 alert(val);
   		 DisButClass.prototype.EnbButMethod();
   		 return false;
   	 }
   	var confrm = confirm("Do you want to continue ?");
   	 if(confrm){
//   		 alert("docCheckIdList:-------"+docCheckIdList);
	document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterUpdate.do?method=updateDcCheklist&docIdList="+docIdList+"&docCheckIdList="+docCheckIdList+"&docMandatoryList="+docMandatoryList+"&docOriginalList="+docOriginalList+"&docExpiryFlagList="+docExpiryFlagList+"&statusList="+statusList+"&docCheckAllId="+docCheckAllId;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("documentChecklistMasterSearch").submit();
	return true;
   	 }else{
   		 DisButClass.prototype.EnbButMethod();
  		 return false;
  	 }
 }
	
function fnClears()
{
	document.getElementById("documentChecklistMasterSearch").action="documentChecklistMasterSearchBehind.do";
    document.getElementById("documentChecklistMasterSearch").submit();
}

function fnChangeCase(formName,fieldName)
{
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}